package javasoap.book.ch11.services;

import javasoap.book.ch9.services.ProxyQuote;

public class StockServiceProxy {

        public ProxyQuote getStockQuote(String symbol) {
    
        ProxyQuote pq = new ProxyQuote();
        pq.setStockSymbol(symbol);

        // set dummy data for now
        pq.setLast((float)0.0);
        pq.setDiff((float)0.0);
        pq.setTime("NA");
        pq.setVol(0);
        
        return pq;
    }
}
