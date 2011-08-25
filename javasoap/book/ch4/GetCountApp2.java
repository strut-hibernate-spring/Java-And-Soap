package javasoap.book.ch4;
import java.net.*;
import org.apache.soap.*;
import org.apache.soap.rpc.*;
public class GetCountApp2 {
   public static void main(String[] args) 
      throws Exception {
      
      URL url = 
        new URL(
          "http://georgetown:8080/soap/servlet/rpcrouter");
    
      Call call = new Call();
      call.setTargetObjectURI("urn:CallCounterService");
      try {
         call.setMethodName("doSomething");
         Response resp = call.invoke(url, "");
         resp = call.invoke(url, "");
         resp = call.invoke(url, "");
         call.setMethodName("getCount");
         resp = call.invoke(url, "");
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
