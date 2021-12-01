package com.everis.bootcamp.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.everis.bootcamp.service.TemperatureServiceI;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;

@RestController
public class TemperatureController {
	
	private final static Logger logger= LoggerFactory.getLogger(TemperatureController.class);
	
	private Counter counterConsulta;
	private Counter counterConversion;
	
	@Autowired
	public TemperatureServiceI service;
	
	public TemperatureController(MeterRegistry registry) {
		this.counterConsulta = Counter.builder("invocaciones.consulta").description("Invocaciones totales").register(registry);
		this.counterConversion = Counter.builder("invocaciones.conversion").description("Invocaciones totales").register(registry);
	}
	
	@GetMapping("consulta/{unidad}")
	public String consulta(@PathVariable char unidad) {
		counterConsulta.increment();
		logger.info("Llamada a consulta número: " + counterConsulta.count());
		double tempCelsius = 10;
		double tempFarenheit = service.convertirCelsiusAFarenheit(tempCelsius);
		if(unidad=='C') {
			return ("La temperatura es: " + tempCelsius + "º" + unidad);
		}
		if (unidad=='F') {
			return ("La temperatura es: " + tempFarenheit + "º" + unidad);
		}
		
		return ("Llamada incorrecta. Termine la llamada en C para Celsius o F para Farenheit.");
		
	}
	
	@GetMapping("/conversor/{temperature}/{unidad}")
	public String conversor(@PathVariable int temperature, @PathVariable char unidad) {
		counterConversion.increment();
		logger.info("Llamada a conversion número: " + counterConversion.count());
		double result;
		double originalTemp = temperature;
		if(unidad=='C') {
			result = service.convertirFarenheitACelsius(temperature);
			return(originalTemp + "C son " + result + "F");
		}
		if(unidad=='F') {
			result = service.convertirCelsiusAFarenheit(temperature);
			return(originalTemp + "F son " + result + "C");
		}
		
		return("Llamada incorrecta. Termine la llamada en C para convertir de Farenheit a Celsius o F para convertir de Celsius a Farenheit.");
	}
	
	

}
