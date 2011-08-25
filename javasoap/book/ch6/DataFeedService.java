package javasoap.book.ch6;
public class DataFeedService {
   public DataFeedService() {
   }
   public DataFeedMessage sendMessage(DataFeedMessage msg) {
      // we could pull the formatted message here
      // by calling msg.toString();
      String stock = msg.getFieldValue("SYMBOL");
      String request = msg.getFieldValue("REQUEST");
      DataFeedMessage res = new DataFeedMessage();
      res.addField("SYMBOL", stock);
      if (request.equals("PRICE"))
         res.addField("PRICE", "123.5");
      return res;
   }
}
