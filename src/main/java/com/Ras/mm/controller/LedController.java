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
		String hi = String.format("\n \n CMDS: \n /on \n /off \n /toggle \n /pulse \n /blink \n /seiz25 \n /seiz50");
		
		return "Running" + hi;
	}

	@RequestMapping("/toggle")
	public String light() {
		
		getPin().toggle();
		
		String s = String.format("Led State: %s",((getPin().getState()).toString()));
		
		return s;
	}
	
	@RequestMapping("/on")
	public String on() {
		
		if(getPin().getState() == PinState.LOW) {
		getPin().setState(PinState.HIGH);
		}
		
		return "Led State: HIGH";
	}
	
	@RequestMapping("/off")
	public String off() {
		
		if(getPin().getState() == PinState.HIGH) {
		getPin().setState(PinState.LOW);
		}
		
		return "Led State: LOW";
	}
	
	@RequestMapping("/pulse")
	public String pulse() {
		
		getPin().pulse(500L);
		
		return "Pulsing for .5 seconds";
	}
	
	@RequestMapping("/blink")
	public String blink() {
		
	
		getPin().blink(25L, 10000L);
		
		if (getPin().getState() == PinState.HIGH) {
			getPin().setState(PinState.LOW);
		}
		
		return  "Blinking";
	}
	
	@RequestMapping("/seiz50")
	public String seiz() {
		
		getPin().blink(25L, 10000L);
		
		return "Seizing at 50 Hz";		
	}
	
	
	@RequestMapping("/seiz25")
	public String seiz1() {
		
		getPin().blink(12L, 10000L);
		
		return "Seizing at 25 Hz";		
	}
	
	
	public GpioPinDigitalOutput getPin() {
		
		if(pin == null) {
			
			GpioController gpio = GpioFactory.getInstance();
			pin = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_01, "Led1", PinState.LOW);
			
			}
		return pin;
	}
	
}
