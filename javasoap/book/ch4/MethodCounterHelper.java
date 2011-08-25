package javasoap.book.ch4;

import electric.registry.Registry;
import electric.registry.RegistryException;

public class MethodCounterHelper
  {
  public static IMethodCounter bind() throws RegistryException
    {
    return bind( "http://georgetown:8004/glue/urn:CallCounterService.wsdl" );
    }

  public static IMethodCounter bind( String url ) throws RegistryException
    {
    return (IMethodCounter) Registry.bind( url, IMethodCounter.class );
    }
  }
