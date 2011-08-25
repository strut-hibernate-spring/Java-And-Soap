package javasoap.book.ch5;
import java.net.*;
import java.util.*;
import org.apache.soap.*;
import org.apache.soap.rpc.*;
import org.apache.soap.encoding.*;
import org.apache.soap.encoding.soapenc.*;
import org.apache.soap.util.xml.*;
public class HighLowClient
{
  public static void main(String[] args) throws Exception 
  {
    URL url = new  
       URL("http://georgetown:8080/soap/servlet/rpcrouter");
    Call call = new Call();
    SOAPMappingRegistry smr = new SOAPMappingRegistry();
    call.setTargetObjectURI("urn:BasicTradingService");
    call.setMethodName("getHighLow");
    call.setEncodingStyleURI(Constants.NS_URI_SOAP_ENC);
    call.setSOAPMappingRegistry(smr);
    BeanSerializer beanSer = new BeanSerializer();
    // Map the High/Low type
    smr.mapTypes(Constants.NS_URI_SOAP_ENC,
             new QName("urn:BasicTradingService", "HighLow"),
             HighLow_ClientSide.class, beanSer, beanSer);
    String stock = "XYZ";
    Vector params = new Vector();
    params.addElement(new Parameter("stock", 
                           String.class, stock, null));
    call.setParams(params);
    Response resp;
    try {
      resp = call.invoke(url, "");
      Parameter ret = resp.getReturnValue();
      HighLow_ClientSide hilo = 
             (HighLow_ClientSide)ret.getValue();
      System.out.println(hilo);
    }
    catch (SOAPException e) {
      System.err.println("Caught SOAPException (" +
                         e.getFaultCode() + "): " +
                         e.getMessage());
    }
  }
}
