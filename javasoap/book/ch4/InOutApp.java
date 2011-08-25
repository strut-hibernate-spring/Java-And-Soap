package javasoap.book.ch4;
import electric.registry.Registry;
import electric.registry.RegistryException;
import electric.util.holder.*;
import electric.util.holder.java.lang.*;
public class InOutApp {
   public static void main(String[] args) throws Exception 
   {
      try {
         IStockPriceService ctr = 
           (IStockPriceService)Registry.bind(
             "http://georgetown:8004/glue/urn:StockPriceService.wsdl",
             IStockPriceService.class);
         StringInOut stock = new StringInOut("MiNdStRm");
         boolean ok = ctr.correctSymbol(stock);
         if (ok) {
           System.out.println("Symbol converted to: " 
                        + stock.value);
         }
         String currency = "USD";
         floatOut price = new floatOut();
         ok = ctr.getOutPrice(stock.value, currency, price);
         System.out.println("Result is " + price.value);
      }
      catch (RegistryException e)
      {
        System.out.println(e);
      }
   }
}
