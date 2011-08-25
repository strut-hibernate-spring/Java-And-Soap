package javasoap.book.ch10.services;

import java.net.*;
import java.util.*;
import org.apache.soap.*;
import org.apache.soap.rpc.*;
import org.apache.soap.encoding.*;
import org.apache.soap.encoding.soapenc.*;
import org.apache.soap.util.xml.*;
import org.w3c.dom.*;

import javasoap.book.ch9.services.RemoteQuote;
import javasoap.book.ch9.services.ProxyQuote;

public class DirectedQuoteProxyService {

   static final String TARGETADDRESS = "targetAddress";
   static final String DEFAULTTARGET = 
             "http://mindstrm.com:8899/glue/urn:CorpDataServices";

   public DirectedQuoteProxyService() {
   }

   String getTargetAddress(Header header) 
               throws SOAPException  {

      String target = DEFAULTTARGET;

      Vector entries = header.getHeaderEntries();
      for (int i = 0; i < entries.size(); i++) {
         Element el = (Element)entries.elementAt(i);
         String name = el.getLocalName();
         String val = el.getAttributeNS(Constants.NS_URI_SOAP_ENV,
            Constants.ATTR_MUST_UNDERSTAND);

         if (name.equals(TARGETADDRESS)) {
            target = el.getFirstChild().getNodeValue();
         }
         else if (val != null && val.equals("1")) {
            if (!name.equals(TARGETADDRESS))
            throw new SOAPException("MustUnderstand",
              "Service Doesn't Understand Header Element: " + name);
         }
      }

      return target;
   }

   public ProxyQuote getQuote(Header hdr, String symbol) 
                     throws SOAPException, Exception {
      
      String target = getTargetAddress(hdr);

      URL url = new URL(target);

      SOAPMappingRegistry smr = new SOAPMappingRegistry();

      BeanSerializer beanSer = new BeanSerializer();

      smr.mapTypes(Constants.NS_URI_SOAP_ENC,
        new QName("http://www.themindelectric.com/package/javasoap.book.ch9.services/", 
                 "Quote"),
                 RemoteQuote.class, beanSer, beanSer);
    
      Call call = new Call();
      call.setSOAPMappingRegistry(smr);
      call.setTargetObjectURI("XYZ");
      call.setEncodingStyleURI(Constants.NS_URI_SOAP_ENC);

      String stock = symbol;

      Vector params = new Vector();
      params.addElement(new Parameter("stock", String.class, stock, null));
      call.setParams(params);
      
      call.setMethodName("getQuote");
      Response resp = call.invoke(url, "");
      ProxyQuote quote = new ProxyQuote();
      if (resp.generatedFault()) {
         throw new Exception("Service Call Failed");
      }
      else { 
         Parameter ret = resp.getReturnValue();
         RemoteQuote value = (RemoteQuote)ret.getValue();
         quote.setStockSymbol(value.get_symbol());
         quote.setLast(value.get_lastPrice());
         quote.setDiff(value.get_change());
         quote.setTime(value.get_timeStamp());
         quote.setVol(value.get_volume());
      }

      return quote;
   }
}
