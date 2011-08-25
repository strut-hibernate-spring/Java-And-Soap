package javasoap.book.ch8;

import java.io.*;
import java.util.*;
import org.apache.soap.*;
import org.w3c.dom.*;
import org.apache.soap.util.xml.*;
import javax.xml.parsers.*;
import org.xml.sax.*;

public class CorporateDataService {

   public CorporateDataService() {
   }

   public Element getDataForSymbol(String symbol) 
         throws Exception {

      FileReader fr = new FileReader(symbol + ".xml");
      DocumentBuilder xdb = XMLParserUtils.getXMLDocBuilder();
      Document doc = xdb.parse(new InputSource (fr));
      if (doc == null) {
         throw new SOAPException(Constants.FAULT_CODE_SERVER, 
                           "Invalid Data");
      }

      return doc.getDocumentElement();
   }
}
