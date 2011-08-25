package javasoap.book.ch7;
import java.net.*;
import java.util.*;
import org.apache.soap.*;
import org.apache.soap.rpc.*;
import org.apache.soap.encoding.*;
import org.apache.soap.util.xml.*;
public class SampleClient {
   public static void main(String[] args) 
      throws Exception {
      
      URL url = 
        new URL(
          "http://georgetown:8080/soap/servlet/rpcrouter");
    
      Call call = new Call();
      
      call.setTargetObjectURI("urn:SampleFaultService");
      call.setMethodName("generateFault");
      try {
         Response resp = call.invoke(url, "");
         if (resp.generatedFault()) {
            String code = resp.getFault().getFaultCode();
            String desc = resp.getFault().getFaultString();
            System.out.println(code + ": " + desc);         }
         else {
            Parameter ret = resp.getReturnValue();
            Object value = ret.getValue();
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
