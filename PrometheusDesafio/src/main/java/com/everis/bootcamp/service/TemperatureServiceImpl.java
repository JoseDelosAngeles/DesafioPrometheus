package com.everis.bootcamp.service;

import org.springframework.stereotype.Service;

@Service
public class TemperatureServiceImpl implements TemperatureServiceI {

	@Override
	public double convertirCelsiusAFarenheit(double temperatura) {
		temperatura = (temperatura*9/5)+32;
		return temperatura;
	}

	@Override
	public double convertirFarenheitACelsius(double temperatura) {
		temperatura = (temperatura - 32)*5/9;
		return temperatura;
	}

}
