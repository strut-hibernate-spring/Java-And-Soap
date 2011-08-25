package javasoap.book.ch5;
import java.net.*;
import java.util.*;
import org.apache.soap.*;
import org.apache.soap.rpc.*;
import org.apache.soap.encoding.*;
import org.apache.soap.encoding.soapenc.*;
import org.apache.soap.util.xml.*;
public class StockTradeClient
{
  public static void main(String[] args) throws Exception 
  {
    URL url = new  
       URL("http://georgetown:8080/soap/servlet/rpcrouter");
    Call call = new Call();
    SOAPMappingRegistry smr = new SOAPMappingRegistry();
    call.setSOAPMappingRegistry(smr);
    call.setEncodingStyleURI(Constants.NS_URI_SOAP_ENC);
    call.setTargetObjectURI("urn:BasicTradingService");
    call.setMethodName("executeStockTrade");
    BeanSerializer beanSer = new BeanSerializer();
    // Map the Stock Trade type
    smr.mapTypes(Constants.NS_URI_SOAP_ENC,
       new QName("urn:BasicTradingService", "StockTrade"),
       StockTrade_ClientSide.class, beanSer, beanSer);
    // create an instance of the stock trade
    StockTrade_ClientSide trade = 
           new StockTrade_ClientSide("XYZ", false, 350);
    Vector params = new Vector();
    params.addElement(new Parameter("trade", 
                           StockTrade_ClientSide.class, trade, null));
    call.setParams(params);
    Response resp;
    try {
      resp = call.invoke(url, "");
      Parameter ret = resp.getReturnValue();
      Object desc = ret.getValue();
      System.out.println("Trade Description: " + desc);
    }
    catch (SOAPException e) {
      System.err.println("Caught SOAPException (" +
                         e.getFaultCode() + "): " +
                         e.getMessage());
    }
  }
}
