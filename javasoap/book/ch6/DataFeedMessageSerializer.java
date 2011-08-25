package javasoap.book.ch6;
import org.apache.soap.encoding.soapenc.*;
import org.apache.soap.util.xml.*;
import org.apache.soap.util.*;
import org.apache.soap.rpc.SOAPContext;
import org.w3c.dom.*;
import java.io.*;
import java.util.*;
public class DataFeedMessageSerializer 
                 implements Serializer, Deserializer {
  
  public DataFeedMessageSerializer() {
  }
  public void marshall(String inScopeEncStyle, Class javaType,  
                Object src, Object context, Writer sink, 
                NSStack nsStack, XMLJavaMappingRegistry xjmr, 
                SOAPContext ctx)
        throws IllegalArgumentException, IOException  {
    
    if(!javaType.equals(DataFeedMessage.class)) {
      throw new IllegalArgumentException(
        "Can only serialize javasoap.book.ch6.DataFeedMessage instances");
    }
    
    nsStack.pushScope();
    
    if (src != null)
    {
      SoapEncUtils.generateStructureHeader(inScopeEncStyle,
                                           javaType, context,
                                           sink, nsStack, xjmr);
      DataFeedMessage msg = (DataFeedMessage)src;
      String data = msg.toString();
      sink.write(data);
      sink.write("</" + context + '>');
    }
    else
    {
      SoapEncUtils.generateNullStructure(inScopeEncStyle,
                                           javaType, context,
                                           sink, nsStack, xjmr);
    }
    nsStack.popScope();
  }
  public Bean unmarshall(String inScopeEncStyle, QName 
                elementType, Node src,
                XMLJavaMappingRegistry xjmr, SOAPContext ctx)
          throws IllegalArgumentException {
    
    Element elem = (Element)src;
    String value = DOMUtils.getChildCharacterData(elem);
    
    DataFeedMessage msg = null;
    if(value!=null && !((value=value.trim()).equals("")) {
       msg = new DataFeedMessage();
       msg.parseFormattedMessage(value);
    }
    return new Bean(DataFeedMessage.class, msg);
  }
}
