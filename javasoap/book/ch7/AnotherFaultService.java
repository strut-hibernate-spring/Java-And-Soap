package javasoap.book.ch7;
import electric.net.soap.SOAPException;
import electric.xml.*;
public class AnotherFaultService {
   public int generateFault() 
        throws SOAPException {
      Element elem = new Element("detail");
      Element sub = elem.addElement("reasonCode");
      sub.addText("199");
      sub = elem.addElement("reasonDescription");
      sub.addText("Power Outage");
      sub = elem.addElement("alternateProvider");
      sub.addText("www.mindstrm.com");
      sub = elem.addElement("alternateContact");
      sub.addText("rob@mindstrm.com");
      SOAPException e = new SOAPException(
                           "Data Feed Unavailable", 
                           SOAPException.SERVER, 
                           "urn:AnotherFaultService", 
                           elem);       
      throw e;
   }
}
