package javasoap.book.ch11.client;

import javasoap.book.ch11.services.*;
import javasoap.book.ch9.services.*;
	
public class StockQuoteApp {	
   public static void main(String[] args) {	
      try {	
         CorpDataServicesProxyImpl proxy = 
             new CorpDataServicesProxyImpl();
         IStockServiceProxy_Stub stub =                                         
             (IStockServiceProxy_Stub)proxy.getIStockServiceProxy();	
         stub._setTargetEndpoint(args[0]);	
         ProxyQuote quote = stub.getStockQuote("IBM");
         System.out.println("Price is " + quote.getLast());	
         System.out.println("Volume is " + quote.getVol());	
         System.out.println("Timestamp is " + quote.getTime());	
         System.out.println("Diff is " + quote.getDiff());	
      } catch (Exception ex) {	
        ex.printStackTrace();	
      }	
   }	
} 
