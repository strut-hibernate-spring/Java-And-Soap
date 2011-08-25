package javasoap.book.ch4;
public class MethodCounter {
   int _counter;
   public MethodCounter() {
      _counter = 0;
   }
   public int getCount() {
      return _counter;
   }
   public boolean doSomething() {
      _counter += 1;
      return true;
   }
}
