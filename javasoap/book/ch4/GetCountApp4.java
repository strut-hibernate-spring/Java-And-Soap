package javasoap.book.ch4;
import electric.registry.RegistryException;
public class GetCountApp4 {
   public static void main(String[] args) throws Exception 
   {
      try {
       IMethodCounter ctr = MethodCounterHelper.bind();
       System.out.println("Result is " + ctr.getCount());
    }
    catch (RegistryException e)
    {
       System.out.println(e);
    }
  }
}
