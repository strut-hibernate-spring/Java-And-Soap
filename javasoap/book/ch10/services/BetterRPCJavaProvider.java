package javasoap.book.ch10.services;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import org.apache.soap.*;
import org.apache.soap.rpc.*;
import org.apache.soap.server.*;
import org.apache.soap.server.http.*;
import org.apache.soap.util.*;

public class BetterRPCJavaProvider 
          extends org.apache.soap.providers.RPCJavaProvider {

   public void invoke(SOAPContext reqContext, SOAPContext resContext)
               throws SOAPException {
      try {
         Response resp = BetterRPCRouter.invoke(envelope.getHeader(), dd, 
               call, targetObject, reqContext, resContext);
         Envelope env = resp.buildEnvelope();
         StringWriter sw = new StringWriter();
         env.marshall(sw, call.getSOAPMappingRegistry(), resContext);
         resContext.setRootPart(sw.toString(), 
                       Constants.HEADERVAL_CONTENT_TYPE_UTF8);
      }
      catch(Exception e) {
         if (e instanceof SOAPException) 
            throw (SOAPException )e;

         throw new SOAPException(Constants.FAULT_CODE_SERVER, e.toString());
      }
   }
}
