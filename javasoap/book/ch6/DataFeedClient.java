import java.net.*;
import java.util.*;
import org.apache.soap.*;
import org.apache.soap.rpc.*;
import org.apache.soap.encoding.*;
import org.apache.soap.util.xml.*;
public class DataFeedClient {
   public static void main(String[] args) 
      throws Exception {
      
      URL url = 
        new URL(
          "http://georgetown:8080/soap/servlet/rpcrouter");
    
      Call call = new Call();
      
      SOAPMappingRegistry smr = new SOAPMappingRegistry();
      call.setTargetObjectURI("urn:DataFeedService");
      call.setMethodName("sendMessage");
      call.setEncodingStyleURI(Constants.NS_URI_SOAP_ENC);
      call.setSOAPMappingRegistry(smr);
      DataFeedMessageSerializer msgSer = 
                   new DataFeedMessageSerializer();
      // Map the data feed message type
      smr.mapTypes(Constants.NS_URI_SOAP_ENC,
            new QName("urn:DataFeedService", "DataFeedMessage"),
            DataFeedMessage.class, msgSer, msgSer);
      DataFeedMessage msg = new DataFeedMessage();
      msg.addField("SYMBOL", "XYZ");
      msg.addField("REQUEST", "PRICE");
      Vector params = new Vector();
      params.addElement(new Parameter("msg", 
                   DataFeedMessage.class, msg, null));
      call.setParams(params);
      
      try {
         Response resp = call.invoke(url, "");
         Parameter ret = resp.getReturnValue();
         Object value = ret.getValue();
         System.out.println(value);
      }
      catch (SOAPException e) {
         System.err.println("Caught SOAPException (" +
                         e.getFaultCode() + "): " +
                         e.getMessage());
      }
   }
}
