package telran.currency.controller;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import telran.currency.dto.RequestDTO;
import telran.currency.interfaces.ICurrencyConverter;

@RestController
public class CurrencyController {

	@Autowired
	ICurrencyConverter currency;

	@GetMapping(value = "/currencies")
	Set<String> getCodes() {
		return currency.getCodes();
	}
	
	@GetMapping(value = "/currencies/datetime")
	String lastDateTimePresentation() {
		return currency.lastDateTimePresentation();
	}
	
	@PostMapping(value = "/currencies/convert")
	double convert(@RequestBody RequestDTO requestDTO) {
		return currency.convert(requestDTO.currencyFrom, requestDTO.currencyTo, requestDTO.amount);
	}
	
}
