package javasoap.book.ch7;
import electric.net.soap.SOAPException;
public interface IAnotherFaultService {
  int generateFault() throws SOAPException;
}
