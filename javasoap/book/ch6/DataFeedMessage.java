package javasoap.book.ch6;
import java.util.*;
public class DataFeedMessage {
   Hashtable _fields = new Hashtable();
   public DataFeedMessage() {
   }
   public void addField(String name, String value) {
      _fields.put(name, value);
   }
   public Enumeration getFieldNames() {
      return _fields.keys();
   }
   public String getFieldValue(String name) {
      return (String)_fields.get(name);
   }
   public String toString() {
      String cnt = String.valueOf(_fields.size());
      StringBuffer msg = new StringBuffer();
      msg.append(cnt);
      msg.append(":");
      for (Enumeration e =_fields.keys(); e.hasMoreElements();) {
         String name = (String)e.nextElement();
         String value = (String)_fields.get(name);
         String section = name + ":" + value + ":";
         msg.append(section);
      }
      return msg.toString();  
   }
   public void parseFormattedMessage(String msg) {
      _fields.clear();
      StringTokenizer st = new StringTokenizer(msg, ":");
      String token = st.nextToken();
      int cnt = new Integer(token).intValue();
      for (int i = 0; i < cnt; i++) {
         String name = st.nextToken();
         String value = st.nextToken();
         addField(name, value);
      }     
   }
