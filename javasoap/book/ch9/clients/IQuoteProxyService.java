package javasoap.book.ch9.clients;

public interface IQuoteProxyService {
  ProxyQuote[] getQuotes(String[] symbols);
}
