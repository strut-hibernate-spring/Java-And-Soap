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

public class SearchClient {

   public static void main(String[] args) 
         throws Exception {

      URL url = new URL(
          "http://georgetown:8080/soap/servlet/rpcrouter");
      Call call = new Call();
      call.setTargetObjectURI("urn:UsedCarListingService");
      call.setMethodName("search");
      call.setEncodingStyleURI(Constants.NS_URI_SOAP_ENC);
      SOAPMappingRegistry smr = new SOAPMappingRegistry();
      BeanSerializer beanSer = new BeanSerializer();
      call.setSOAPMappingRegistry(smr);

      smr.mapTypes(Constants.NS_URI_SOAP_ENC,
          new QName("urn:UsedCarListingService", "Listing"),
          javasoap.book.ch8.Listing.class, beanSer, beanSer);

      Vector params = new Vector();
      String make = "Pontiac";
      String model = "Firebird";
      params.addElement(new Parameter("make", 
                java.lang.String.class, make, null));
      params.addElement(new Parameter("model", 
                java.lang.String.class, model, null));
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
         int[] ids = (int[])ret.getValue();

         int cnt = ids.length;
         for (int i = 0; i < cnt; i++) {
            call.setMethodName("getListing");
            params = new Vector();
            params.addElement(new Parameter("id", 
                java.lang.Integer.class, new Integer(ids[i]),  
                null));
            call.setParams(params);

            try {
               resp = call.invoke(url, "");
            }
            catch (SOAPException e) {
               System.out.println(e.getMessage());
               return;
            }

            ret = resp.getReturnValue();
            Listing listing = (Listing)ret.getValue();
            System.out.println(listing);

            call.setMethodName("getImage");

            try {
               resp = call.invoke(url, "");
            }
            catch (SOAPException e) {
               System.out.println(e.getMessage());
               return;
            }

            ret = resp.getReturnValue();
            DataHandler handler = (DataHandler)ret.getValue();
            DataSource ds = handler.getDataSource();
            String fname = "EX17." + 
                     String.valueOf(ids[i]) + ".bin";
            ByteArrayDataSource bsource = 
                  new ByteArrayDataSource(ds.getInputStream(), 
                                       handler.getContentType());
            bsource.writeTo(new FileOutputStream(fname));
            System.out.println(
                  "   Image File Stored In " + fname);
         }
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
