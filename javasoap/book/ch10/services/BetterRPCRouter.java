package javasoap.book.ch10.services;

import org.apache.soap.server.*;
import java.io.*;
import java.util.*;
import java.lang.reflect.*;
import org.w3c.dom.*;
import org.apache.soap.util.Bean;
import org.apache.soap.util.MethodUtils;
import org.apache.soap.util.IOUtils;
import org.apache.soap.*;
import org.apache.soap.rpc.*;
import org.apache.soap.util.StringUtils;

public class BetterRPCRouter extends org.apache.soap.server.RPCRouter {  

   public static Response invoke(Header hdr, DeploymentDescriptor dd, 
                     Call call, Object targetObject, SOAPContext reqCtx, 
                     SOAPContext resCtx)
          throws SOAPException {
      byte providerType = dd.getProviderType();
      Vector params = call.getParams();
      String respEncStyle = call.getEncodingStyleURI();
      Object[] args = null;
      Class[] argTypes = null;
      if (params != null) {
         int paramsCount = params.size();
         args = new Object[paramsCount];
         argTypes = new Class[paramsCount];
         for (int i = 0; i < paramsCount; i++) {
            Parameter param = (Parameter)params.elementAt(i);
            args[i] = param.getValue();
            argTypes[i] = param.getType();
            if (respEncStyle == null) {
               respEncStyle = param.getEncodingStyleURI();
            }
         }
      }

      if (respEncStyle == null) {
         respEncStyle = Constants.NS_URI_SOAP_ENC;
      }
      
      Bean result = null;
      try {
         if (providerType == DeploymentDescriptor.PROVIDER_JAVA ||
             providerType == DeploymentDescriptor.PROVIDER_USER_DEFINED) {
            Method m = null ;
            try {
               m = MethodUtils.getMethod (targetObject,
                      call.getMethodName(), argTypes);
            } catch(NoSuchMethodException e) {
               try {
                  int paramsCount = 0 ;
                  if (params != null) paramsCount = params.size();
                  Class[] tmpArgTypes = new Class[paramsCount+1];
                  Object[] tmpArgs = new Object[paramsCount+1];
                  for (int i = 0 ; i < paramsCount ; i++)
                     tmpArgTypes[i+1] = argTypes[i] ;
                  
                  argTypes = tmpArgTypes;
                  argTypes[0] = Header.class;
                  m = MethodUtils.getMethod(targetObject,
                               call.getMethodName(),argTypes);
                  for (int i = 0 ; i < paramsCount ; i++)
                     tmpArgs[i+1] = args[i];

                  tmpArgs[0] = hdr;
                  args = tmpArgs;
               } 
               catch (NoSuchMethodException e2) {
                  throw e;
               } 
            catch (Exception e2) {
               throw e2;
            }
         }          catch (Exception e) {
            throw e;
         }

         result = new Bean(m.getReturnType(), 
                             m.invoke(targetObject, args));
      } 
      else {
         Class bc = Class.forName("org.apache.soap.server.InvokeBSF"); 
         Class[] sig = {DeploymentDescriptor.class,
                          Object.class, 
                          String.class,
                          Object[].class};
         Method m = MethodUtils.getMethod(bc, "service", sig, true);
         result = (Bean) m.invoke (null, 
                    new Object[] {dd, targetObject, 
                                    call.getMethodName (), args});
      }
   } 
   catch (InvocationTargetException e) {
      Throwable t = e.getTargetException();
      if (t instanceof SOAPException) {
         throw (SOAPException)t;
      } 
      else {
         throw new SOAPException(Constants.FAULT_CODE_SERVER,
             "Exception from service object: " + t.getMessage(), t);
      }
   } 
   catch (ClassNotFoundException e) {
      throw new SOAPException (Constants.FAULT_CODE_SERVER,
              "Unable to load BSF: script services " +
              "unsupported without BSF", e);
   } 
   catch (Throwable t) {
      throw new SOAPException (Constants.FAULT_CODE_SERVER, 
        "Exception while handling service request: " + t.getMessage(), t);
   }

   Parameter ret = null;
   if (result.type != void.class) {
      ret = new Parameter (RPCConstants.ELEM_RETURN, result.type, 
                              result.value, null);
   }

   return new Response(call.getTargetObjectURI(), call.getMethodName (),
                             ret, null, null, respEncStyle, resCtx);
   }
}
