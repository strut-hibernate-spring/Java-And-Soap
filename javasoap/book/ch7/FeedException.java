package javasoap.book.ch7;
import org.apache.soap.*;
import java.util.Hashtable;
public class FeedException extends SOAPException {
   Hashtable _detail;
   public FeedException(String fcode, String fstring) {
      super(fcode, fstring);
   }
   public void setDetail(Hashtable detail) {  
      _detail = detail;
   }
   public Hashtable getDetail() {
      return _detail;
   }
}
