package javasoap.book.ch9.services;

import java.util.Vector;

public class CorpDataService {

   public Quote getQuote(String symbol) 
            throws Exception {

      QuoteParser qp = new QuoteParser();
      return qp.getQuote(symbol);
   }   

   public String[] getHeadlines(String symbol)
            throws Exception {

      HeadlineParser hp = new HeadlineParser();
      Vector v = hp.getHeadlines(symbol);
      int len = v.size();
      String[] result = new String[len];
      for (int i = 0; i < len; i++) {
         result[i] = (String)v.elementAt(i);
      }

      return result;
   }
}
