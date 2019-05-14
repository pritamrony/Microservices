package com.in28minutes.microservices.currencyconversionservice;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class CurrencyConverterController {
	
	@Autowired
	CurrencyExchangeServiceProxy currencyExchangeServiceProxy;
	
	private Logger logger=LoggerFactory.getLogger(this.getClass());
	
	@GetMapping("/currency-converter/from/{from}/to/{to}/quantity/{quantity}")
	public CurrencyConversionBean convertcurrency(@PathVariable String from,@PathVariable String to,@PathVariable BigDecimal quantity)
	{
		
		  Map<String,String> values=new HashMap<>(); values.put("from", from);
		  values.put("to", to);
		  
		  ResponseEntity<CurrencyConversionBean> responseEntity=new
		  RestTemplate().getForEntity(
		  "http://localhost:8000/currency-exchange/from/{from}/to/{to}",
		  CurrencyConversionBean.class, values); 
		  CurrencyConversionBean  currencyConversionBean=responseEntity.getBody();
		 
		
		return new CurrencyConversionBean(currencyConversionBean.getId(),from,to,currencyConversionBean.getConversionMultiple(),quantity,quantity.multiply(currencyConversionBean.getConversionMultiple()),currencyConversionBean.getPort());
	}
	
	@GetMapping("/currency-converter-feign/from/{from}/to/{to}/quantity/{quantity}")
	public CurrencyConversionBean convertcurrencyFeign(@PathVariable String from,@PathVariable String to,@PathVariable BigDecimal quantity)
	{
		CurrencyConversionBean currencyConversionBean=currencyExchangeServiceProxy.retrieveExchangeValueFeign(from, to);
		
		logger.info("CurrencyConverterServiceSleuth"+currencyConversionBean);
		
		return new CurrencyConversionBean(currencyConversionBean.getId(),from,to,currencyConversionBean.getConversionMultiple(),quantity,quantity.multiply(currencyConversionBean.getConversionMultiple()),currencyConversionBean.getPort());
	}
}
