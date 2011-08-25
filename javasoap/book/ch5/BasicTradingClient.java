package javasoap.book.ch5;
import electric.registry.RegistryException;
import electric.registry.Registry;
public class BasicTradingClient {
   public static void main(String[] args) throws Exception 
   {
      try {
        IBasicTradingService srv = 
          (IBasicTradingService)Registry.bind(
            "http://georgetown:8004/glue/urn:BasicTradingService.wsdl",
            IBasicTradingService.class);
        String[] stocks = { "MINDSTRM", "MSFT", "SUN" };
        int total = srv.getTotalVolume(stocks);
        System.out.println("Total Volume is " + total);
        Object[] multiParams = { "MINDSTRM", new Integer(100), 
                                    new Boolean(true) };
        String desc = srv.executeTrade(multiParams);
        System.out.println("Trade Description: " + desc);
    }
    catch (RegistryException e)
    {
       System.out.println(e);
    }
  }
}
