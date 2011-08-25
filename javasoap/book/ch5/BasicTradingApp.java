package javasoap.book.ch5;
import electric.util.Context;
import electric.registry.Registry;
import electric.server.http.HTTP;
public class BasicTradingApp {
   public static void main( String[] args )
      throws Exception {
    
      HTTP.startup("http://georgetown:8004/glue");
      Context context = new Context();
      context.addProperty("activation", "application");
      context.addProperty("namespace", 
                               "urn:BasicTradingService");
      Registry.publish("urn:BasicTradingService",
         javasoap.book.ch5.BasicTradingService.class, context );
   }
}
