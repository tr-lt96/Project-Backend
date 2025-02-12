package CONNECT_DATABASE;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class StockRecord {
	private int id;
	private String name;
	private String symbol;
	private long timestamp;
	private double price;
}
