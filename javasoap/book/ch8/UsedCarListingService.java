package javasoap.book.ch8;

import java.util.*;
import java.io.*;
import javax.activation.*;
import org.apache.soap.util.mime.*;

public class UsedCarListingService {

   Hashtable _listings = new Hashtable();
   int _nextId = 100;

   public int addListing(Listing listing, DataHandler handler) 
                throws Exception {
      _listings.put(new Integer(_nextId), listing);
      int listno = _nextId;
      _nextId++;

      if (handler != null) {
         String fname = String.valueOf(listno) + ".bin";
         DataSource ds = handler.getDataSource();
         ByteArrayDataSource bsource = 
               new ByteArrayDataSource(ds.getInputStream(), 
                                       handler.getContentType());
         bsource.writeTo(new FileOutputStream(fname));
      }      

      return listno;
   }

   public Integer[] search(String make, String model) {
      Vector v = findListingIds(make, model);
      int cnt = v.size();
      if (cnt == 0)
         return null;

      Integer ids[] = new Integer[cnt];
      for (int i = 0; i < cnt; i++) {
         ids[i] = (Integer)v.elementAt(i);
      }

      return ids;
   }

   protected Vector findListingIds(String make, String model) {
      
      Vector result = new Vector();
      for (Enumeration e = _listings.keys() ; 
                             e.hasMoreElements() ;) {
         Integer i = (Integer)e.nextElement();
         Listing listing = (Listing)_listings.get(i);
         if (make.equals(listing.getMake()) &&
             model.equals(listing.getModel())) {
            result.add(i);
         }
      }
      
      return result; 
   }

   public Listing getListing(int id) {
      Listing listing = (Listing)_listings.get(new Integer(id));
      return listing;
   }

   public DataHandler getImage(int id) 
        throws Exception {
      String fname = String.valueOf(id) + ".bin";
      DataSource ds = 
           new ByteArrayDataSource(new File(fname), null);
      DataHandler dh = new DataHandler(ds);
      return dh;
   }
}
