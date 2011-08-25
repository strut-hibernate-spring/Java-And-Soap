package javasoap.book.ch9.clients;

import java.io.*;
import java.util.*;
import java.net.*;

public class Quote {

   String _symbol;
   float  _lastPrice;
   float  _change;
   String _timeStamp;
   long   _volume;

   public String get_symbol() {
      return _symbol;
   }

   public void set_symbol(String symbol) {
      _symbol = symbol;
   }

   public float get_lastPrice() {
      return _lastPrice;
   }

   public void set_lastPrice(float lastPrice) {
      _lastPrice = lastPrice;
   }

   public float get_change() {
      return _change;
   }

   public void set_change(float change) {
      _change = change;
   }

   public String get_timeStamp() {
      return _timeStamp;
   }

   public void set_timeStamp(String timeStamp) {
      _timeStamp = timeStamp;
   }

   public long get_volume() {
      return _volume;
   }

   public void set_volume(long volume) {
      _volume = volume;
   }

   public String toString() {
      String result = "Symbol:     " + get_symbol() +
                      "\nLast Price: " + get_lastPrice() +
                      "\nChange:     " + get_change() +
                      "\nTime Stamp: " + get_timeStamp() +
                      "\nVolume:     " + get_volume();
      return result;
   }
} 
