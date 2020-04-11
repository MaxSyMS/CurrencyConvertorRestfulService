package telran.currency.dto;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class RequestDTO {
	public String currencyFrom;
	public String currencyTo;
	public Double amount;
}
