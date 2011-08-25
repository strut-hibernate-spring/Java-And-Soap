package javasoap.book.ch9.clients;

import java.net.*;
import java.util.*;
import org.apache.soap.*;
import org.apache.soap.rpc.*;
import org.apache.soap.encoding.*;
import org.apache.soap.util.xml.*;
import org.w3c.dom.*;

public class Apache2Glue {
   public static void main(String[] args) 
      throws Exception {
      
      URL url = 
         new URL("http://mindstrm.com:8004/glue/urn:CorpDataServices");
    
      Call call = new Call();
      call.setTargetObjectURI("XYZ");
      
      call.setEncodingStyleURI(Constants.NS_URI_SOAP_ENC);
      String stock = args[0];

      Vector params = new Vector();
      params.addElement(new Parameter("stock", String.class, 
                               stock, null));
      call.setParams(params);
      
      try {
         call.setMethodName("getHeadlines");
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
               System.out.println(n.getNodeName() + ": " + 
                                           nd.getNodeValue());   
            }   
         }
         else { 
            Parameter ret = resp.getReturnValue();
            String[] value = (String[])ret.getValue();
            int cnt = value.length;
            for (int i = 0; i < cnt; i++) {
               System.out.println(value[i]);
            }
         }
      }
      catch (SOAPException e) {
         System.err.println("Caught SOAPException (" +
                         e.getFaultCode() + "): " +
                         e.getMessage());
      }
   }
}
