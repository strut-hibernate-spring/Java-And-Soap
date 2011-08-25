package javasoap.book.ch4;
import java.net.*;
import java.util.*;
import org.apache.soap.*;
import org.apache.soap.rpc.*;
public class StockPriceApp {
   public static void main(String[] args) 
      throws Exception {
      
      URL url = 
        new URL(
          "http://georgetown:8080/soap/servlet/rpcrouter");
    
      Call call = new Call();
      call.setTargetObjectURI("urn:StockPriceService");
      
      call.setEncodingStyleURI(Constants.NS_URI_SOAP_ENC);
      String stock = "MINDSTRM";
      String currency = "USD";
      Vector params = new Vector();
      params.addElement(new Parameter("stock", 
                         String.class, stock, null));
      params.addElement(new Parameter("currency", 
                         String.class, currency, null));
      call.setParams(params);
      
      try {
         call.setMethodName("getPrice");
         Response resp = call.invoke(url, "");
         Parameter ret = resp.getReturnValue();
         Object value = ret.getValue();
         System.out.println("Price is " + value);
      }
      catch (SOAPException e) {
         System.err.println("Caught SOAPException (" +
                         e.getFaultCode() + "): " +
                         e.getMessage());
      }
   }
}
