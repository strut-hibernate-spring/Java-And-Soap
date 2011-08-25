package javasoap.book.ch4;
import java.net.*;
import org.apache.soap.*;
import org.apache.soap.rpc.*;
public class GetCountApp {
   public static void main(String[] args) 
      throws Exception {
      
      URL url = 
        new URL(
          "http://georgetown:8080/soap/servlet/rpcrouter");
    
      Call call = new Call();
      call.setTargetObjectURI("urn:CallCounterService");
      call.setMethodName("getCount");
      try {
         Response resp = call.invoke(url, "");
         Parameter ret = resp.getReturnValue();
         Object value = ret.getValue();
         System.out.println("Result is " + value);
      }
      catch (SOAPException e) {
         System.err.println("Caught SOAPException (" +
                         e.getFaultCode() + "): " +
                         e.getMessage());
      }
   }
}
