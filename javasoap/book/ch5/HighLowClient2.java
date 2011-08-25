package javasoap.book.ch5.client;
import electric.registry.RegistryException;
import electric.registry.Registry;
import electric.xml.io.Mappings;
public class HighLowClient2 {
   public static void main(String[] args) throws Exception 
   {
      try {
        Mappings.readMappings("BasicTradingService.map");
        IBasicTradingService srv = (IBasicTradingService)Registry.bind(
          "http://georgetown:8004/glue/urn:BasicTradingService.wsdl",
          IBasicTradingService.class);
        HighLow_ServerSide hilo = srv.getHighLow("ANY");
        System.out.println(hilo);
    }
    catch (RegistryException e)
    {
       System.out.println(e);
    }
  }
}
