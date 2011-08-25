package javasoap.book.ch8;

import java.io.*;
import java.net.*;
import javax.xml.parsers.*;
import org.w3c.dom.*;
import org.xml.sax.*;
import org.apache.soap.*;
import org.apache.soap.messaging.*;
import org.apache.soap.transport.*;
import org.apache.soap.util.xml.*;

public class TemperatureClient {
   public static void main (String[] args) throws Exception {

      DocumentBuilder xdb = XMLParserUtils.getXMLDocBuilder();
      Document doc = xdb.newDocument();
      
      Element elem = 
                doc.createElementNS(Constants.NS_URI_SOAP_ENV,
                "SOAP-ENV:Envelope");
      doc.appendChild(elem);
      
      Element sub = 
                doc.createElementNS(Constants.NS_URI_SOAP_ENV,
                "SOAP-ENV:Body");

      elem.appendChild(sub);
      elem = sub;

      sub = doc.createElement("getTemperature");
      sub.setAttribute("xmlns", "urn:WeatherDiary");
      elem.appendChild(sub);

      sub = doc.createElement("zipcode");
      Text txt = doc.createTextNode("");
      txt.setData("12345");
      sub.appendChild(txt);
      elem.appendChild(sub);

      Envelope msgEnv = Envelope.unmarshall 
                           (doc.getDocumentElement ());

      URL url = new URL(
          "http://georgetown:8080/soap/servlet/messagerouter");

      Message msg = new Message ();
      msg.send (url, "", msgEnv);
      
      SOAPTransport st = msg.getSOAPTransport ();
      BufferedReader br = st.receive ();
      String line;
      while ((line = br.readLine ()) != null) {
         System.out.println (line);
      }
   }
}
