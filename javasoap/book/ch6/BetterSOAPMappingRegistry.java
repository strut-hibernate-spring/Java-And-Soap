package javasoap.book.ch6;
import org.apache.soap.util.xml.*;
import org.apache.soap.*;
import org.apache.soap.encoding.soapenc.*;
public class BetterSOAPMappingRegistry 
       extends org.apache.soap.encoding.SOAPMappingRegistry {
   SparseArraySerializer sparseSer = new SparseArraySerializer();
   public BetterSOAPMappingRegistry() {
      super();
   }
   public Serializer querySerializer(Class javaType, 
                  String encodingStyleURI)
        throws IllegalArgumentException {
    
     Serializer s;
     try {
        s = super.querySerializer(javaType, encodingStyleURI);
        if (s instanceof ArraySerializer)
          return sparseSer;
     }
     catch (IllegalArgumentException e) {
        if (javaType != null
         && encodingStyleURI != null
         && encodingStyleURI.equals(Constants.NS_URI_SOAP_ENC)) {
            if (javaType.isArray()) {
              return sparseSer;
            }
        }
        throw e;
     }
     return s;
   }
}
