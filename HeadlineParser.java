
package javasoap.book.ch9.services;

import java.io.*;
import java.util.*;
import java.net.*;

public class HeadlineParser {

   public Vector getHeadlines(String symbol)
                   throws Exception {

      System.out.println("Got Headline Request For: " + symbol);
      Vector v = new Vector();

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

         // ok, now I want to start parsing
         int idx = contents.indexOf("<!-- Yahoo TimeStamp");
         idx++;
         contents = contents.substring(idx);
         while (true) {
            idx = contents.indexOf("<a");
            contents = contents.substring(idx);
            idx = contents.indexOf(">") + 1;
            contents = contents.substring(idx);
            idx = 0;
         
            int idx2 = contents.indexOf("</a>");
            String hline = contents.substring(idx, idx2);
            String endit = "<font face=arial size=-1>" + symbol
                                 + "</font>";
            if (hline.equals(endit))
               break;

            // is the symbol crap?
            if (hline.startsWith("<img")) {
               throw new Exception("Invalid Stock Symbol (" 
                          + symbol + ")");
            }

            // get rid of stuff in brackets
            int br1 = hline.indexOf("[");
            while (br1 != -1) {
               int br2 = hline.indexOf("]");
               if (br2 != -1) {
                  sb = new StringBuffer(hline);
                  sb.delete(br1, br2 + 1);
                  hline = sb.toString();
               }
               br1 = hline.indexOf("[");
            }

            hline = hline.trim();
            //System.out.println("Headline:" + hline);
            v.add(hline);
         }
      }
      catch (Exception e)
      {
         throw e;
         //System.out.println(e);
      }

      return v;
   }

}

