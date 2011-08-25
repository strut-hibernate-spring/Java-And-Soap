package javasoap.book.ch5;
import java.net.*;
import org.apache.soap.*;
import org.apache.soap.rpc.*;
public class MostActiveClient
{
  public static void main(String[] args) throws Exception 
  {
    URL url = new   
      URL("http://georgetown:8080/soap/servlet/rpcrouter");
    Call call = new Call();
    call.setTargetObjectURI("urn:BasicTradingService");
    call.setMethodName("getMostActive");
    Response resp;
    try {
      resp = call.invoke(url, "");
      Parameter ret = resp.getReturnValue();
      String[] value = (String[])ret.getValue();
      int cnt = value.length;
      for (int i = 0; i < cnt; i++) {
         System.out.println(value[i]);
      }
    }
    catch (SOAPException e) {
      System.err.println("Caught SOAPException (" +
                         e.getFaultCode() + "): " +
                         e.getMessage());
    }
  }
}
