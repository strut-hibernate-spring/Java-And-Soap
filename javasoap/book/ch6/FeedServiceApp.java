package javasoap.book.ch6;
import electric.util.Context;
import electric.registry.Registry;
import electric.server.http.HTTP;
import java.util.Hashtable;
public class FeedServiceApp {
   
   public static void main( String[] args ) 
                 throws Exception {
      String ns = "urn:AnotherFeedService";
      HTTP.startup("http://georgetown:8004/glue");
      Context context = new Context();
      context.addProperty("activation", "application");
      context.addProperty("namespace", ns);
      Registry.publish(ns,
         javasoap.book.ch6.AnotherFeedService.class, context );
   }
}
