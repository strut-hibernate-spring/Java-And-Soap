package javasoap.book.ch4;
import electric.util.Context;
import electric.registry.Registry;
import electric.server.http.HTTP;
public class StockPriceApp3 {
   public static void main( String[] args )
      throws Exception {
    
      HTTP.startup("http://georgetown:8004/glue");
      Context context = new Context();
      context.addProperty("activation", "application");
      context.addProperty("namespace", "urn:StockPriceService");
      Registry.publish("urn:StockPriceService", 
         javasoap.book.ch4.StockPrice.class, context );
   }
}
