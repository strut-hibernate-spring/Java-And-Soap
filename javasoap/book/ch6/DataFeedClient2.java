package javasoap.book.ch6;
import java.net.*;
import java.util.*;
import org.apache.soap.*;
import org.apache.soap.rpc.*;
import org.apache.soap.encoding.*;
import org.apache.soap.util.xml.*;
public class DataFeedClient2 {
   public static void main(String[] args) 
      throws Exception {
      
      URL url = 
        new URL(
          "http://georgetown:8080/soap/servlet/rpcrouter");
    
      Call call = new Call();
      
      BetterSOAPMappingRegistry smr = 
                        new BetterSOAPMappingRegistry();
      call.setTargetObjectURI("urn:DataFeedService");
      call.setMethodName("echoStocks");
      call.setEncodingStyleURI(Constants.NS_URI_SOAP_ENC);
      call.setSOAPMappingRegistry(smr);
      SparseArraySerializer arraySer = 
                        new SparseArraySerializer();
      // Map the array
      smr.mapTypes(Constants.NS_URI_SOAP_ENC,
                 new QName(Constants.NS_URI_SOAP_ENC, "Array"),
                 java.lang.reflect.Array.class, arraySer, 
                 arraySer);
      String[] stocks = new String[10];
      stocks[2] = "XYZ";
      stocks[5] = "ABC";
      Vector params = new Vector();
      params.addElement(new Parameter("msg", String[].class, 
                               stocks, null));
      call.setParams(params);
      
      try {
         Response resp = call.invoke(url, "");
         Parameter ret = resp.getReturnValue();
         String[] value = (String[])ret.getValue();
         int cnt = value.length;
         for (int i = 0; i < cnt; i++) {
            if (value[i] != null) {
               System.out.println("Item " + i + ": " + value[i]);
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
