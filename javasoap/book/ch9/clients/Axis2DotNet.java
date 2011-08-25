package javasoap.book.ch9.clients;

import org.apache.axis.AxisFault;
import org.apache.axis.client.Call;
import org.apache.axis.client.Service;
import org.apache.axis.client.Transport;
import org.apache.axis.encoding.XMLType;
import org.apache.axis.transport.http.HTTPConstants;
import org.apache.axis.utils.Options;
import java.net.URL;
import java.util.*;

public class Axis2DotNet {

   public static void main(String args[]) {
      try {
         URL url = 
          new URL("http://mindstrm.com:8199/CorpDataService/Proxy.asmx");
         Service  service = new Service();
         Call call = (Call) service.createCall();
         call.setTargetEndpointAddress( url );
         call.setOperationName("getHeadlines");
         call.setProperty(Call.NAMESPACE, 
                  "http://mindstrm.com/CorpDataService");
         call.setProperty(HTTPConstants.MC_HTTP_SOAPACTION,
              "http://mindstrm.com/CorpDataService/getHeadlines");

         call.addParameter("symbol", XMLType.XSD_STRING, Call.PARAM_MODE_IN);
         call.setReturnType(XMLType.SOAP_ARRAY);

         ArrayList ret = (ArrayList)call.invoke(new Object[] {args[0]});
         Object[] res = ret.toArray();
         int cnt = res.length;
         for (int i = 0; i < cnt; i++) {
            System.out.println((String)res[i]);
         }
      }
      catch (Exception e) {
         System.out.println(e);
      }
   }
}
