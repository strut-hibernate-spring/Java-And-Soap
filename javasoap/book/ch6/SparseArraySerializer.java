package javasoap.book.ch6;
import org.apache.soap.encoding.soapenc.*;
import java.beans.*;
import java.io.*;
import java.util.*;
import java.lang.reflect.*;
import org.w3c.dom.*;
import org.apache.soap.util.*;
import org.apache.soap.util.xml.*;
import org.apache.soap.*;
import org.apache.soap.rpc.*;
public class SparseArraySerializer implements Serializer, Deserializer
{
   public SparseArraySerializer() {
   }
   public void marshall(String inScopeEncStyle, 
               Class javaType, Object src,
               Object context, Writer sink, NSStack nsStack,
               XMLJavaMappingRegistry xjmr, SOAPContext ctx)
       throws IllegalArgumentException, IOException {
      nsStack.pushScope();
      String lengthStr = src != null
                       ? Array.getLength(src) + ""
                       : "";
      Class componentType = javaType.getComponentType();
      QName elementType = xjmr.queryElementType(componentType,
                               Constants.NS_URI_SOAP_ENC);
      if (src == null) {
      
         SoapEncUtils.generateNullArray(inScopeEncStyle,
                                     javaType,
                                     context,
                                     sink,
                                     nsStack,
                                     xjmr,
                                     elementType,
                                     lengthStr);
      }
      else {
      
         SoapEncUtils.generateArrayHeader(inScopeEncStyle,
                                     javaType,
                                     context,
                                     sink,
                                     nsStack,
                                     xjmr,
                                     elementType,
                                     lengthStr);
        sink.write(StringUtils.lineSeparator);
        int length = Array.getLength(src);
        for (int i = 0; i < length; i++) {
        
           nsStack.pushScope();
           Object value = Array.get(src, i);
           // we only want to serialize if the 
           // element exists, since
           // this is a sparse array serializer
           if (value != null) {
            Class actualComponentType = value.getClass();
             // use a temporary sink so that we can 
             // modify it before writing it out
             StringWriter sw = new StringWriter();
             xjmr.marshall(inScopeEncStyle, 
                      actualComponentType, value, "item",
                sw, nsStack, ctx);
            
            sink.write("<item ");
             sink.write(nsStack.getPrefixFromURI(
                 Constants.NS_URI_SOAP_ENC) +
                ":position=\"[" + i + "]\" ");
            sink.write(sw.toString().substring(6));
            sink.write(StringUtils.lineSeparator);
          }
          nsStack.popScope();
        }
        sink.write("</" + context + '>');
      }
      nsStack.popScope();
   }
   public Bean unmarshall(String inScopeEncStyle, 
                   QName elementType, Node src,
                   XMLJavaMappingRegistry xjmr, SOAPContext ctx)
              throws IllegalArgumentException {
       
      Element root = (Element)src;
      String name = root.getTagName();
      QName arrayItemType = new QName("", "");
      Object array = getNewArray(inScopeEncStyle, root,    
                              arrayItemType, xjmr);
      if (SoapEncUtils.isNull(root)) {
         return new Bean(array.getClass(), null);
      }
      Element tempEl = DOMUtils.getFirstChildElement(root);
      while (tempEl != null) {
         String declEncStyle = DOMUtils.getAttributeNS(tempEl,
                Constants.NS_URI_SOAP_ENV, 
                Constants.ATTR_ENCODING_STYLE);
         String actualEncStyle = declEncStyle != null
                              ? declEncStyle
                              : inScopeEncStyle;
         QName declItemType = 
                SoapEncUtils.getAttributeValue(tempEl,
                Constants.NS_URI_CURRENT_SCHEMA_XSI, 
                Constants.ATTR_TYPE, 
                "array item", false);
         QName actualItemType = declItemType != null
                             ? declItemType
                             : arrayItemType;
         Bean itemBean = xjmr.unmarshall(actualEncStyle, 
                             actualItemType,
                             tempEl, ctx);
         // get the position in the array
         String pos = DOMUtils.getAttributeNS(tempEl, 
                              inScopeEncStyle, "position");
      
         int right = pos.indexOf(']');
         String substr = pos.substring(1, right);
         int idx = Integer.parseInt(substr);
         Array.set(array, idx, itemBean.value);
         tempEl = DOMUtils.getNextSiblingElement(tempEl);
      }
      return new Bean(array.getClass(), array);
   }
   public static Object getNewArray(String inScopeEncStyle, 
                    Element arrayEl,
                    QName arrayItemType,
                    XMLJavaMappingRegistry xjmr)
              throws IllegalArgumentException {
      QName arrayTypeValue = 
             SoapEncUtils.getAttributeValue(arrayEl,
             Constants.NS_URI_SOAP_ENC, 
             Constants.ATTR_ARRAY_TYPE, 
             "array", true);
      String arrayTypeValueNamespaceURI = 
             arrayTypeValue.getNamespaceURI();
      String arrayTypeValueLocalPart = 
             arrayTypeValue.getLocalPart();
      int leftBracketIndex = 
             arrayTypeValueLocalPart.lastIndexOf('[');
      int rightBracketIndex = 
             arrayTypeValueLocalPart.lastIndexOf(']');
      if (leftBracketIndex == -1
        || rightBracketIndex == -1
        || rightBracketIndex < leftBracketIndex) {
          throw new IllegalArgumentException(
                  "Malformed arrayTypeValue '" +
                   arrayTypeValue + "'.");
      }
      String componentTypeName =
            arrayTypeValueLocalPart.substring(0, 
            leftBracketIndex);
      if (componentTypeName.endsWith("]")) {
         throw new IllegalArgumentException(
            "Arrays of arrays are not " +
            "supported '" + arrayTypeValue + "'.");
      }
      arrayItemType.setNamespaceURI(arrayTypeValueNamespaceURI);
      arrayItemType.setLocalPart(componentTypeName);
      int length = DOMUtils.countKids(arrayEl, 
          Node.ELEMENT_NODE);
      String lengthStr =
          arrayTypeValueLocalPart.substring(leftBracketIndex + 1,
                                        rightBracketIndex);
      if (lengthStr.length() > 0) {
         if (lengthStr.indexOf(',') != -1) {
            throw new IllegalArgumentException(
                 "Multi-dimensional arrays are " +
                 "not supported '" + lengthStr + "'.");
         }
         try {
            int explicitLength = Integer.parseInt(lengthStr);
            length = explicitLength;
         }
         catch (NumberFormatException e) {
            throw new IllegalArgumentException(
                   "Explicit array length is not a " +
                   "valid integer '" + lengthStr + "'.");
         }
      }
      Class componentType = xjmr.queryJavaType(arrayItemType, 
                    inScopeEncStyle);
      return Array.newInstance(componentType, length);
   }
}
