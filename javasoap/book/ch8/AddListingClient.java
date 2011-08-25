package javasoap.book.ch8;

import java.io.*;
import java.util.*;
import java.net.*;
import org.apache.soap.util.mime.*;
import org.apache.soap.*;
import org.apache.soap.encoding.*;
import org.apache.soap.encoding.soapenc.*;
import org.apache.soap.rpc.*;
import javax.activation.*;
import javax.mail.internet.*;
import org.apache.soap.util.xml.*;

public class AddListingClient {

   public static void main(String[] args) 
         throws Exception {

      URL url = new URL(
           "http://georgetown:8080/soap/servlet/rpcrouter");
      Call call = new Call();
      call.setTargetObjectURI("urn:UsedCarListingService");
      call.setMethodName("addListing");
      call.setEncodingStyleURI(Constants.NS_URI_SOAP_ENC);
      SOAPMappingRegistry smr = new SOAPMappingRegistry();
      BeanSerializer beanSer = new BeanSerializer();
      call.setSOAPMappingRegistry(smr);

      smr.mapTypes(Constants.NS_URI_SOAP_ENC,
         new QName("urn:UsedCarListingService", "Listing"),
                 javasoap.book.ch8.Listing.class, 
                 beanSer, beanSer);

      Vector params = new Vector();
      Listing listing = new Listing("Pontiac", "Firebird", 1968,  
             105000, "Rob Englander", "rob@mindstrm.com");
      params.addElement(new Parameter("listing", 
                javasoap.book.ch8.Listing.class, listing, null));

      DataSource ds = new 
             ByteArrayDataSource(new File("data.bin"), null);
      DataHandler dh = new DataHandler(ds);

      params.addElement(new Parameter("handler",
                javax.activation.DataHandler.class, dh, null));

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
         System.out.println("The listing number is " + 
                               ret.getValue());
      }
      else {
         Fault fault = resp.getFault();
         System.out.println ("Fault Code = " + 
                 fault.getFaultCode());
         System.out.println ("      String = " + 
                 fault.getFaultString());
      }
   }
}
