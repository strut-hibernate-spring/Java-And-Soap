package javasoap.book.ch9.services;

import java.io.*;
import java.util.*;
import java.net.*;

public class Quote {

   String _symbol;
   float  _lastPrice;
   float  _change;
   String _timeStamp;
   long   _volume;

   public String getSymbol() {
      return _symbol;
   }

   public void setSymbol(String symbol) {
      _symbol = symbol;
   }

   public float getLastPrice() {
      return _lastPrice;
   }

   public void setLastPrice(float lastPrice) {
      _lastPrice = lastPrice;
   }

   public float getChange() {
      return _change;
   }

   public void setChange(float change) {
      _change = change;
   }

   public String getTimeStamp() {
      return _timeStamp;
   }

   public void setTimeStamp(String timeStamp) {
      _timeStamp = timeStamp;
   }

   public long getVolume() {
      return _volume;
   }

   public void setVolume(long volume) {
      _volume = volume;
   }

}
