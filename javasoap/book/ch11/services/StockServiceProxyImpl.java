package javasoap.book.ch11.services;

import javasoap.book.ch9.services.ProxyQuote;

public class StockServiceProxyImpl implements IStockServiceProxy {

    public ProxyQuote getStockQuote(String symbol) {
        StockServiceProxy sp = new StockServiceProxy();
        return sp.getStockQuote(symbol);
    }
}
