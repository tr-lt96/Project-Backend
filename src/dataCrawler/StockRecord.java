package dataCrawler;

public class StockRecord {
	private String stockName;
	private String stockSymbol;
	private long timestamp;
	private double price;

	public  StockRecord() {
	}

	public StockRecord(String stockName, String stockSymbol, long timestamp, double price) {
		this.stockName = stockName;
		this.stockSymbol = stockSymbol;
		this.timestamp = timestamp;
		this.price = price;
	}

	public String getStockName() {
		return stockName;
	}

	public void setStockName(String stockName) {
		this.stockName = stockName;
	}

	public String getStockSymbol() {
		return stockSymbol;
	}

	public void setStockSymbol(String stockSymbol) {
		this.stockSymbol = stockSymbol;
	}

	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}
}
