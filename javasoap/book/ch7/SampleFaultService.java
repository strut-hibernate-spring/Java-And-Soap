package javasoap.book.ch7;
import org.apache.soap.SOAPException;
public class SampleFaultService {
   public SampleFaultService() {
   }
   public int generateFault() 
        throws Exception {
      // something bad must have happenend…
      throw new Exception();
   }
}
