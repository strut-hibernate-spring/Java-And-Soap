package javasoap.book.ch8;

import java.io.*;
import java.util.*;
import org.apache.soap.*;
import org.apache.soap.rpc.SOAPContext;
import javax.mail.MessagingException;
import org.w3c.dom.*;

public class WeatherDiary {

   Hashtable _diary = new Hashtable();

   public void recordTemperature (Envelope env, SOAPContext  
                     reqCtx, SOAPContext resCtx)
            throws Exception {
   
      Vector v = env.getBody().getBodyEntries();      
      int cnt = v.size();
      String zipcode = null;
      String temperature = null;
      for (int i = 0; i < cnt; i++) {
         Element e = (Element)v.elementAt(i);
         String name = e.getTagName();
         if (name.equals("zipcode")) {
            zipcode = e.getFirstChild().getNodeValue();
         }
         else if (name.equals("temperature")) {
            temperature = e.getFirstChild().getNodeValue();
         }
      }

      if (zipcode == null || temperature == null) {
         throw new IllegalArgumentException(
               "ZIPCODE and/or TEMPERATURE Not Specified");
      }

      _diary.put(zipcode, temperature);

      resCtx.setRootPart("OK", "text/xml");
   }
}
