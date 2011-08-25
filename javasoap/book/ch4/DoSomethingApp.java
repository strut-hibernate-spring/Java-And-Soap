package javasoap.book.ch4;
import electric.registry.RegistryException;
public class DoSomethingApp {
   public static void main(String[] args) throws Exception 
   {
      try {
       IMethodCounter ctr = MethodCounterHelper.bind();
       ctr.doSomething();
       ctr.doSomething();
       ctr.doSomething();
       System.out.println("Result is " + ctr.getCount());
    }
    catch (RegistryException e)
    {
       System.out.println(e);
    }
  }
}
