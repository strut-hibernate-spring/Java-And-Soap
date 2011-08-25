package javasoap.book.ch8;

import java.io.*;
import java.util.*;
import java.net.*;
import org.w3c.dom.*;
import org.apache.soap.util.xml.*;
import org.apache.soap.*;
import org.apache.soap.encoding.*;
import org.apache.soap.encoding.soapenc.*;
import org.apache.soap.rpc.*;

public class GetDataClient {

   public static void main(String[] args) 
         throws Exception {

      URL url = new URL(
          "http://georgetown:8080/soap/servlet/rpcrouter");
      Call call = new Call();
      call.setTargetObjectURI("urn:CorporateDataService");
      call.setMethodName("getDataForSymbol");
      call.setEncodingStyleURI(Constants.NS_URI_LITERAL_XML);

      String symbol = "MINDSTRM";
      Vector params = new Vector();
      params.addElement(new Parameter("symbol", String.class, 
                  symbol, Constants.NS_URI_SOAP_ENC));
      call.setParams(params);

      Response resp;

      try {
         resp = call.invoke(url, "");
      }
      catch (SOAPException e) {
         System.out.println(e.getMessage());
         return;
      }

      if (!resp.generatedFault()) {
         Parameter ret = resp.getReturnValue();
         Element bookEl = (Element)ret.getValue();
         System.out.println(DOM2Writer.nodeToString(bookEl));
      }
   }
}
