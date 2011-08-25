package javasoap.book.ch9.services;

import electric.util.Context;
import electric.server.http.HTTP;
import electric.registry.Registry;

public class CorpDataServices {
   
   public static void main( String[] args ) 
                 throws Exception {

      String ns = "urn:CorpDataServices";

      HTTP.startup("http://mindstrm.com:8004/glue");

      Context context = new Context();
      context.addProperty("activation", "application");
      context.addProperty("namespace", ns);
      context.addProperty("description", "Corporate Services Demo");
      Registry.publish(ns,
         Javasoap.book.ch9.services.CorpDataService.class, context);
   }
}
