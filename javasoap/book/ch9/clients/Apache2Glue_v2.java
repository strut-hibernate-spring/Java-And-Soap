package javasoap.book.ch9.clients;

import java.net.*;
import java.util.*;
import org.apache.soap.*;
import org.apache.soap.rpc.*;
import org.apache.soap.encoding.*;
import org.apache.soap.encoding.soapenc.*;
import org.apache.soap.util.xml.*;
import org.w3c.dom.*;

public class Apache2Glue_v2 {
   public static void main(String[] args) 
      throws Exception {
      
      URL url = 
          new URL("http://mindstrm.com:8004/glue/urn:CorpDataServices");

      SOAPMappingRegistry smr = new SOAPMappingRegistry();

      BeanSerializer beanSer = new BeanSerializer();

      smr.mapTypes(Constants.NS_URI_SOAP_ENC,
new QName("http://www.themindelectric.com/package/javasoap.book.ch9.services/", 
                 "Quote"),
                 Quote.class, beanSer, beanSer);
    
      Call call = new Call();
      call.setSOAPMappingRegistry(smr);
      call.setTargetObjectURI("XYZ");
      call.setEncodingStyleURI(Constants.NS_URI_SOAP_ENC);

      String stock = args[0];

      Vector params = new Vector();
      params.addElement(new Parameter("stock", String.class, 
                                stock, null));
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
               System.out.println(n.getNodeName() + ": " 
                                          + nd.getNodeValue());   
            }   
         }
         else { 
            Parameter ret = resp.getReturnValue();
            Quote value = (Quote)ret.getValue();
            System.out.println(value);
         }
      }
      catch (SOAPException e) {
         System.err.println("Caught SOAPException (" +
                         e.getFaultCode() + "): " +
                         e.getMessage());
      }
   }
}
