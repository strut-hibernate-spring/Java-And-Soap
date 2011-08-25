package javasoap.book.ch5;
import java.net.*;
import java.util.*;
import org.apache.soap.*;
import org.apache.soap.rpc.*;
public class VolumeClient {
   public static void main(String[] args) throws Exception {
      
      URL url = new URL("http://georgetown:8080/soap/servlet/rpcrouter");
    
      Call call = new Call();
      call.setTargetObjectURI("urn:BasicTradingService");
      
      call.setEncodingStyleURI(Constants.NS_URI_SOAP_ENC);
      String[] stocks = { "MINDSTRM", "MSFT", "SUN" };
      Vector params = new Vector();
      params.addElement(new Parameter("stocks", 
                            String[].class, stocks, null));
      call.setParams(params);
      
      try {
         call.setMethodName("getTotalVolume");
         Response resp = call.invoke(url, "");
         Parameter ret = resp.getReturnValue();
         Object value = ret.getValue();
         System.out.println("Total Volume is " + value);
      }
      catch (SOAPException e) {
         System.err.println("Caught SOAPException (" +
                         e.getFaultCode() + "): " +
                         e.getMessage());
      }
   }
}
