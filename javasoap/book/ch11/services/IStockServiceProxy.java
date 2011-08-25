package javasoap.book.ch11.services;

import java.rmi.Remote;
import java.rmi.RemoteException;
import javasoap.book.ch9.services.ProxyQuote;

public interface IStockServiceProxy extends Remote {
    public ProxyQuote getStockQuote(String symbol) throws RemoteException;
}
