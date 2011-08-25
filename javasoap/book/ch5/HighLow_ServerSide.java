package javasoap.book.ch5;
public class HighLow_ServerSide {
   public float _high;
   public float _low;
   public HighLow_ServerSide() {
   }
   public HighLow_ServerSide (float high, float low) {
      setHigh(high);
      setLow(low);
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
