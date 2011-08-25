package javasoap.book.ch4;
import electric.util.Context;
import electric.registry.Registry;
import electric.server.http.HTTP;
public class CallCounterApp {
   public static void main( String[] args )
      throws Exception {
    
      HTTP.startup("http://georgetown:8004/glue");
      Context context = new Context();
      context.addProperty("activation", "application");
      Registry.publish( "urn:CallCounterService", 
         javasoap.book.ch4.MethodCounter.class, context );
   }
}
