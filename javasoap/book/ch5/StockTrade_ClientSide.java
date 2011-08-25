package javasoap.book.ch5;
public class StockTrade_ClientSide {
   String   _symbol;
   boolean  _buy;
   int      _numShares;
   public StockTrade_ClientSide() {
   }
   public StockTrade_ClientSide(String symbol, 
                      boolean buy, int numShares) {
      _symbol = symbol;
      _buy = buy;
      _numShares = numShares;
   }
   public String getSymbol() {
      return _symbol;
   }
   public void setSymbol(String symbol) {
      _symbol = symbol;
   }
   public boolean isBuy() {
      return _buy;
   }
   public void setBuy(boolean buy) {
      _buy = buy;
   }
   public int getNumShares() {
      return _numShares;
   }
   public void setNumShares(int numShares) {
      _numShares = numShares;
   }
}

