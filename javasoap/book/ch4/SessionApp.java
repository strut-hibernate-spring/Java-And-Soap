package javasoap.book.ch4;
import java.net.*;
import org.apache.soap.*;
import org.apache.soap.rpc.*;
import org.apache.soap.transport.http.*;
public class SessionApp {
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
         SOAPHTTPConnection st = 
          (SOAPHTTPConnection)call.getSOAPTransport();
         st.setMaintainSession(false);
         resp = call.invoke(url, "");
         st.setMaintainSession(true);
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
