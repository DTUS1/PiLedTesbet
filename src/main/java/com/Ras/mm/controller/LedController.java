package com.Ras.mm.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.PinState;
import com.pi4j.io.gpio.RaspiPin;

@RestController
public class LedController {
	
	private static GpioPinDigitalOutput pin;
	
	@RequestMapping("/")
	public String hi() {
		return "Hello World";
	}

	@RequestMapping("/light")
	public String light() {
		
		getPin().toggle();
		
		String s = String.format("Led State: %s",((getPin().getState()).toString()));
		
		return s;
	}
	
	@RequestMapping("/pulse")
	public String pulse() {
		
		getPin().pulse(500L);
		
		return "Pulsing for .5 seconds";
	}
	
	@RequestMapping("/blink")
	public String blink() {
		
		for(int c = 0; c < 10; c++) {
		getPin().blink(500L, 500L);
		}
		
		return "Blinking 10 times";
	}
	
	

	public GpioPinDigitalOutput getPin() {
		
		if(pin == null) {
			
			GpioController gpio = GpioFactory.getInstance();
			pin = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_01, "Led1", PinState.LOW);
			
			}
		return pin;
	}
	
}
