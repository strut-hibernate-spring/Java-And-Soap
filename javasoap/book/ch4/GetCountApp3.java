package javasoap.book.ch4;
import electric.registry.Registry;
import electric.registry.RegistryException;
public class GetCountApp3 {
   public static void main(String[] args) throws Exception 
   {
      try {
         ICallCounterService ctr = 
           (ICallCounterService)Registry.bind(
             "http://georgetown:8004/glue/urn:CallCounterService.wsdl",
              ICallCounterService.class);
       System.out.println("Result is " + ctr.getCount());
    }
    catch (RegistryException e)
    {
       System.out.println(e);
    }
  }
}
