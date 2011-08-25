package javasoap.book.ch7;
import org.apache.soap.*;
import org.apache.soap.server.*;
import java.util.*;
import org.w3c.dom.*;
import org.apache.soap.util.xml.*;
import javax.xml.parsers.DocumentBuilder;

public class FeedFaultListener
        implements SOAPFaultListener {
   public FeedFaultListener() {
   }
   public void fault(SOAPFaultEvent event) {
   
      FeedException ex = (FeedException)event.getSOAPException();
      Vector v = new Vector();
      DocumentBuilder builder = 
               XMLParserUtils.getXMLDocBuilder();
      Document doc = builder.newDocument();
      Hashtable detail = ex.getDetail();
      Enumeration e = detail.keys();
      while (e.hasMoreElements()) {
         String name = (String)e.nextElement();
         String value = (String)detail.get(name);
         Element elem = doc.createElement(name);
         Text txt = doc.createTextNode("");
         txt.setData(value);
         elem.appendChild(txt);
         v.addElement(elem);
      }
      
      event.getFault().setDetailEntries(v);
   }
}
