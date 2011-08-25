package javasoap.book.ch5;
public interface IBasicTradingService {
  int getTotalVolume(String[] symbols);
  String executeTrade(Object[] params);
}
