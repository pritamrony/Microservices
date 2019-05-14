package com.in28minutes.microservices.currencyexchangeservice;

import java.math.BigDecimal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CurrencyExchangeController {
	
	@Autowired
	private Environment environment;
	
	private Logger logger=LoggerFactory.getLogger(this.getClass());

	@GetMapping("/currency-exchange/from/{from}/to/{to}")
	public ExchangeValue retrieveExchangeValue(@PathVariable String from,@PathVariable String to)
	{
		int count=0;
		int total=0;
		count=total;
		ExchangeValue exchangeValue=new ExchangeValue();
		if(from.equals("USD") && to.equals("INR"))
		{
			count++;
			exchangeValue.setId(Long.valueOf(count));
			exchangeValue.setFrom(from);
			exchangeValue.setTo(to);
			exchangeValue.setConversionMultiple(BigDecimal.valueOf(65));
		}
		else {
			count++;
			exchangeValue.setId(Long.valueOf(count));
			exchangeValue.setFrom(from);
			exchangeValue.setTo(to);
			exchangeValue.setConversionMultiple(BigDecimal.valueOf(75));
		}
		exchangeValue.setPort(Integer.parseInt(environment.getProperty("local.server.port")));
		total=count;
		logger.info("CurrencyExchangeServiceSleuth"+exchangeValue);		
		return exchangeValue;
		
	}
}
