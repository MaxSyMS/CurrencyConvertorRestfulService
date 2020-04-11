package telran.currency.service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Set;

import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import telran.currency.dto.CurrencyRates;
import telran.currency.interfaces.ICurrencyConverter;

@Service
public class CurrencyConverterImpl implements ICurrencyConverter {

	static private final String url = "http://data.fixer.io/api/latest?access_key=53ce63adc508e94144574ea8c794aaf8";
	private CurrencyRates currencyRates;
	int counter = 0;

	public CurrencyConverterImpl() {

		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<CurrencyRates> responce = restTemplate.exchange(url, HttpMethod.GET, null, CurrencyRates.class);
		if (checkTimeStamp(responce.getBody().timestamp, counter)) {
			currencyRates = responce.getBody();
		}

	}

	private boolean checkTimeStamp(int timestamp, int counter) {
		LocalDateTime localDate = getLocalDate(timestamp);
		if (counter == 0 || LocalDateTime.now().plusHours(1).isAfter(localDate)) {
			this.counter++;
			return true;
		}
		return false;
	}

	@Override
	public String lastDateTimePresentation() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
		LocalDateTime localDate = getLocalDate(currencyRates.timestamp);
		return localDate.format(formatter);
	}

	private LocalDateTime getLocalDate(int timestamp) {
		Instant date = Instant.ofEpochSecond(timestamp);
		return LocalDateTime.ofInstant(date, ZoneId.of("Asia/Jerusalem"));
	}

	@Override
	public Set<String> getCodes() {
		return currencyRates.rates.keySet();
	}

	@Override
	public double convert(String from, String to, double amount) {
		if (from == null || to == null) {
			throw new IllegalArgumentException();
		}
		double fromCur = currencyRates.rates.get(from);
		double toCur = currencyRates.rates.get(to);
		return (toCur / fromCur * amount);
	}

}
