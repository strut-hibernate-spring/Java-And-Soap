package javasoap.book.ch5;
public class StockTrade_ServerSide {
   int      _shares;
   boolean  _buyOrder;
   String   _stock;
   public StockTrade_ServerSide() {
   }
   public String getSymbol() {
      return _stock;
   }
   public void setSymbol(String stock) {
      _stock = stock;
   }
   public boolean isBuy() {
      return _buyOrder;
   }
   public void setBuy(boolean buyOrder) {
      _buyOrder = buyOrder;
   }
   public int getNumShares() {
      return _shares;
   }
   public void setNumShares(int shares) {
      _shares = shares;
   }
}
