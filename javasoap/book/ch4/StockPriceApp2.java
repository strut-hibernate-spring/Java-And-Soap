package javasoap.book.ch4;
import electric.registry.Registry;
import electric.registry.RegistryException;
public class StockPriceApp2 {
   public static void main(String[] args) throws Exception 
   {
      try {
         IStockPriceService ctr = 
           (IStockPriceService)Registry.bind(
              "http://georgetown:8004/glue/urn:StockPriceService.wsdl",
              IStockPriceService.class);
         String stock = "MINDSTRM";
         String currency = "USD";
         System.out.println("Price is " + 
           ctr.getPrice(stock, currency));
      }
      catch (RegistryException e)
      {
        System.out.println(e);
      }
   }
}
