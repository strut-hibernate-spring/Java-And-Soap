package javasoap.book.ch9.clients;

import electric.registry.Registry;

public class Glue2Apache {
   public static void main( String[] args )
             throws Exception {

       IQuoteProxyService service = (IQuoteProxyService)Registry.bind(
              "http://georgetown:8080/soap/QuoteProxyService.wsdl",
              IQuoteProxyService.class);

       ProxyQuote[] quotes = service.getQuotes(args);
       int cnt = quotes.length;
       for (int i = 0; i < cnt; i++) {
          System.out.println(quotes[i]);
          System.out.println("");
       }
   }
}
