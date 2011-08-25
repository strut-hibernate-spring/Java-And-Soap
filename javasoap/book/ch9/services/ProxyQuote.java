package javasoap.book.ch9.services;

import java.io.*;
import java.util.*;
import java.net.*;

public class ProxyQuote {

   String _sym;
   float  _last;
   float  _diff;
   String _time;
   long   _vol;

   public String getStockSymbol() {
      return _sym;
   }

   public void setStockSymbol(String symbol) {
      _sym = symbol;
   }

   public float getLast() {
      return _last;
   }

   public void setLast(float last) {
      _last = last;
   }

   public float getDiff() {
      return _diff;
   }

   public void setDiff(float diff) {
      _diff = diff;
   }

   public String getTime() {
      return _time;
   }

   public void setTime(String time) {
      _time = time;
   }

   public long getVol() {
      return _vol;
   }

   public void setVol(long vol) {
      _vol = vol;
   }
}
