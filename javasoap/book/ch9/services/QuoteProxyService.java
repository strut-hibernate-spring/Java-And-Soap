package javasoap.book.ch9.services;

import java.net.*;
import java.util.*;
import org.apache.soap.*;
import org.apache.soap.rpc.*;
import org.apache.soap.encoding.*;
import org.apache.soap.encoding.soapenc.*;
import org.apache.soap.util.xml.*;
import org.w3c.dom.*;

public class QuoteProxyService {

   public QuoteProxyService() {
   }

   public ProxyQuote[] getQuotes(String[] symbols) 
                         throws Exception {
      Vector v = new Vector();
      int cnt = symbols.length;
      for (int i = 0; i < cnt; i++) {
         v.add(getStockQuote(symbols[i]));
      }
      cnt = v.size();
      ProxyQuote[] quotes = new ProxyQuote[cnt];
      for (int i = 0; i < cnt; i++) {
         quotes[i] = (ProxyQuote)v.elementAt(i);
      }

      return quotes;
   }

   ProxyQuote getStockQuote(String symbol) 
                     throws Exception {
URL url = new URL("http://mindstrm.com:8004/glue/urn:CorpDataServices");

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
      params.addElement(new Parameter("stock", String.class, 
                                stock, null));
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
