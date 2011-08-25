package javasoap.book.ch5.client;
import electric.registry.RegistryException;
import electric.registry.Registry;
import electric.xml.io.Mappings;
public class StockTradeClient2 {
   public static void main(String[] args) throws Exception 
   {
      try {
        Mappings.readMappings("BasicTradingService.map");
        IBasicTradingService srv = 
          (IBasicTradingService)Registry.bind(
          "http://georgetown:8004/glue/urn:BasicTradingService.wsdl",
          IBasicTradingService.class);
        StockTrade_ServerSide trade =   
                       new StockTrade_ServerSide();
        trade._stock = "MINDSTRM";
        trade._buyOrder = true;
        trade._shares = 500;
        String desc = srv.executeStockTrade(trade);
        System.out.println("Trade Description is: " + desc);
    }
    catch (RegistryException e)
    {
       System.out.println(e);
    }
  }
}
