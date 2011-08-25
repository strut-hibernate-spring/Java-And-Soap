package javasoap.book.ch10.clients;

import java.net.*;
import java.util.*;
import org.apache.soap.*;
import org.apache.soap.rpc.*;
import org.apache.soap.encoding.*;
import org.apache.soap.encoding.soapenc.*;
import org.apache.soap.util.xml.*;
import org.w3c.dom.*;
import javax.xml.parsers.DocumentBuilder;

import javasoap.book.ch9.services.ProxyQuote;

public class HeaderClient {
   public static void main(String[] args) 
      throws Exception {
      
      URL url = new URL("http://georgetown:8080/soap/servlet/rpcrouter");

      SOAPMappingRegistry smr = new SOAPMappingRegistry();

      BeanSerializer beanSer = new BeanSerializer();

      smr.mapTypes(Constants.NS_URI_SOAP_ENC,
        new QName("urn:QuoteProxyService", "Quote"),
                 ProxyQuote.class, beanSer, beanSer);
    
      Call call = new Call();
      call.setSOAPMappingRegistry(smr);
      call.setTargetObjectURI("urn:DirectedQuoteProxyService");
      call.setEncodingStyleURI(Constants.NS_URI_SOAP_ENC);

      String stocks = args[0];

      Header header = new Header();
      String must = Constants.NS_PRE_SOAP_ENV + ":" + 
                                Constants.ATTR_MUST_UNDERSTAND;

      Vector entries = new Vector();
      DocumentBuilder builder = XMLParserUtils.getXMLDocBuilder();
      Document doc = builder.newDocument();
      Element elem = doc.createElement("targetAddress");
      elem.setAttribute(must, "1");
      Text txt = doc.createTextNode("");
      txt.setData("http://mindstrm.com:8004/glue/urn:CorpDataServices");
      elem.appendChild(txt);
      entries.addElement(elem);
      header.setHeaderEntries(entries);
      call.setHeader(header);

      Vector params = new Vector();
      params.addElement(new Parameter("stocks", String.class, stocks, null));
      call.setParams(params);
      
      try {
         call.setMethodName("getQuote");
         Response resp = call.invoke(url, "");

         if (resp.generatedFault()) {
            Fault fault = resp.getFault();
            String code = fault.getFaultCode();
            String desc = fault.getFaultString();
            System.out.println(code + ": " + desc);

            Vector v = fault.getDetailEntries();
            int cnt = v.size();
            for (int i = 0; i < cnt; i++) {
               Element n = (Element)v.elementAt(i);
               Node nd = n.getFirstChild();
               //System.out.println(n.getNodeName() + ": " + nd.getNodeValue());   
            }   
         }
         else { 
            Parameter ret = resp.getReturnValue();
            ProxyQuote value = (ProxyQuote)ret.getValue();
            System.out.println("Symbol:     " + value.getStockSymbol() +
                      "\nLast Price: " + value.getLast() +
                      "\nChange:     " + value.getDiff() +
                      "\nTime Stamp: " + value.getTime() +
                      "\nVolume:     " + value.getVol());
         }
      }
      catch (SOAPException e) {
         System.err.println("Caught SOAPException (" +
                         e.getFaultCode() + "): " +
                         e.getMessage());
      }
   }
}
