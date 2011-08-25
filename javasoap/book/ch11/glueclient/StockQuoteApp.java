package javasoap.book.ch11.glueclient;

public class StockQuoteApp {	
   public static void main(String[] args) {	
      try {	
         CorpDataServiceImpl proxy = 
             new CorpDataServiceImpl();
         CorpDataServiceSoap_Stub stub =
             (CorpDataServiceSoap_Stub)proxy.getCorpDataServiceSoap();
         stub._setTargetEndpoint(args[0]);	
         Quote quote = stub.getQuote("IBM");
         System.out.println("Price is " + quote.get_lastPrice());	
         System.out.println("Volume is " + quote.get_volume());	
         System.out.println("Timestamp is " + quote.get_timeStamp());	
         System.out.println("Change is " + quote.get_change());	
      } catch (Exception ex) {	
        ex.printStackTrace();	
      }	
   }	
}
