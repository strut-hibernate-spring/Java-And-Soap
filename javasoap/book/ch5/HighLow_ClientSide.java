package javasoap.book.ch5;
public class HighLow_ClientSide {
   public float _high;
   public float _low;
   public String toString() {
      return "High: " + _high +
        " Low: " + _low;
   }
   public HighLow_ClientSide() {
   }
   public float getHigh() {
      return _high;
   }
   public void setHigh(float high) {
      _high = high;
   }
   public float getLow() {
      return _low;
   }
   public void setLow(float low) {
      _low = low;
   }
}
