package javasoap.book.ch7;

import electric.registry.RegistryException;
import electric.registry.Registry;
import java.util.*;

public class FeedServiceClient {
   public static void main(String[] args) throws Exception 
   {
      try {

        IAnotherFeedService srv = (IAnotherFeedService)Registry.bind(
         "http://georgetown:8004/glue/urn:AnotherFeedService.wsdl",
          IAnotherFeedService.class);

        Hashtable msg = new Hashtable();
        msg.put("SYMBOL", "XYZ");
        msg.put("REQUEST", "PRICE");
       
        Hashtable result = srv.sendMessage(msg);

        for (Enumeration e = result.keys(); e.hasMoreElements(); ) {
           String key = (String)e.nextElement();
           String value = (String)result.get(key);
           System.out.println(key + ": " + value);
        }
    }
    catch (RegistryException e) {
       System.out.println(e);
    }
  }
}




