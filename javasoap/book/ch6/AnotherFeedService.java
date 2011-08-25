package javasoap.book.ch6;
import java.util.Hashtable;
public class AnotherFeedService {
   public AnotherFeedService() {
   }
   public Hashtable sendMessage(Hashtable msg) {
      String stock = (String)msg.get("SYMBOL");
      String request = (String)msg.get("REQUEST");
      Hashtable res = new Hashtable();
      res.put("SYMBOL", stock);
      if (request.equals("PRICE"))
         res.put("PRICE", "123.5");
      return res;
   }
}
