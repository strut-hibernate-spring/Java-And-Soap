
package javasoap.book.ch9.services;

import java.io.*;
import java.util.*;
import java.net.*;

public class QuoteParser
{
   public Quote getQuote(String symbol) 
            throws Exception {

      System.out.println("Got Quote Request For: " + symbol);
      Quote quote = new Quote();
      quote.setSymbol(symbol);

      try
      {
         int symbolLen = symbol.length();

         String path = 
           "http://finance.yahoo.com/q?s=" + symbol + "&d=v1";
         URL url = new URL(path);
         DataInputStream pis = new DataInputStream(url.openStream());

         StringBuffer sb = new StringBuffer();
         String some;
         while ((some = pis.readLine()) != null) 
         {
            sb.append(some + "\n");
         }
         String contents = new String(sb);

//         Vector v = new Vector();

         int idx = contents.indexOf("q?s=" + symbol + "&d=t");
         idx++;
         contents = contents.substring(idx);
         idx = contents.indexOf("q?s=" + symbol + "&d=t");
         if (idx == -1) {
            throw new Exception("Invalid Stock Symbol (" +
                        symbol + ")");
         }

         contents = contents.substring(idx);
         
         String str = "<td nowrap align=center><font face=arial size=-1>";
         idx = contents.indexOf(str) + str.length();
         contents = contents.substring(idx);
         idx = 0;
         int idx2 = contents.indexOf("</font></td>");
         String tstamp = contents.substring(idx, idx2);
         quote.setTimeStamp(tstamp);
//         System.out.println("Timestamp is " + tstamp);

         str = "<td nowrap><font face=arial size=-1><b>";
         idx = contents.indexOf(str) + str.length();
         contents = contents.substring(idx);
         idx = 0;
         idx2 = contents.indexOf("</b></font></td>");
         String price = contents.substring(idx, idx2);
         quote.setLastPrice(new Float(price).floatValue());
//         System.out.println("Price is " + price);

         str = "<td nowrap>";
         idx = contents.indexOf(str) + str.length();
         contents = contents.substring(idx);
         idx = 0;

         if (contents.startsWith("<font"))
         {
            str = ">";
            idx = contents.indexOf(str) + str.length();
            contents = contents.substring(idx);
            idx = 0;
            idx2 = contents.indexOf("</font></td>");
         }
         else
         {
            idx2 = contents.indexOf("</td>");
         }

         String change = contents.substring(idx, idx2);
         quote.setChange(new Float(change).floatValue());
//         System.out.println("Change is " + change);

         str = "<td nowrap><font face=arial size=-1>";
         idx = contents.indexOf(str) + str.length();
         contents = contents.substring(idx);
         str = "<td nowrap><font face=arial size=-1>";
         idx = contents.indexOf(str) + str.length();
         contents = contents.substring(idx);
         idx = 0;
         idx2 = contents.indexOf("</font></td>");
         String volume = contents.substring(idx, idx2);
         volume = volume.trim();
         if (!volume.equals("N/A")) {
            sb = new StringBuffer(volume);
            int len = volume.length() - 1;
            for (int j = len; j >= 0; j--) {
               if (volume.charAt(j) == ',') {
                 sb.deleteCharAt(j);
               }
            }
            volume = sb.toString();
            quote.setVolume(Long.decode(volume).longValue());
         }
//         System.out.println("Volume is " + volume);

      }
      catch (Exception e)
      {
         throw e;
      }

      return quote;
   }

}

