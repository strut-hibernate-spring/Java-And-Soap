package javasoap.book.ch9.clients;

public class ProxyQuote {
   public String stockSymbol;
   public float last;
   public float diff;
   public String time;
   public long vol;

   public String toString() {
      String result = "Symbol:     " + stockSymbol +
                      "\nLast Price: " + last +
                      "\nChange:     " + diff +
                      "\nTime Stamp: " + time +
                      "\nVolume:     " + vol;
      return result;
   }
}
