package javasoap.book.ch7;
import electric.util.Context;
import electric.server.http.HTTP;
import electric.registry.Registry;
public class FaultServiceApp {
   
   public static void main( String[] args ) 
                 throws Exception {
      String ns = "urn:AnotherFaultService";
      HTTP.startup("http://georgetown:8004/glue");
      Context context = new Context();
      context.addProperty("activation", "application");
      context.addProperty("namespace", ns);
      Registry.publish(ns,
         javasoap.book.ch7.AnotherFaultService.class, context );
   }
}
