package javasoap.book.ch7;
import electric.registry.RegistryException;
import electric.registry.Registry;
import electric.net.soap.SOAPException;
import electric.xml.*;
public class FaultServiceClient {
   public static void main(String[] args) throws Exception 
   {
      try {
         IAnotherFaultService srv = 
           (IAnotherFaultService)Registry.bind(
             "http://georgetown:8004/glue/urn:AnotherFaultService.wsdl",
             IAnotherFaultService.class);
         srv.generateFault();
        
      }
      catch (SOAPException se) {
         System.out.println(se.getSOAPCode());
         System.out.println(se.getMessage());
         System.out.println(se.getSOAPActor());
         System.out.println(se.getSOAPDetailElement());
      }
      catch (RegistryException e) {
         System.out.println(e);
      }
  }
}
