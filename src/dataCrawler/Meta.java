package dataCrawler;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Meta {
	private String currency;
	private String symbol;
	private String exchangeName;
	private String fullExchangeName;
	private String instrumentType;
	private long firstTradeDate;
	private long regularMarketTime;
	private boolean hasPrePostMarketData;
	private int gmtoffset;
	private String timezone;
	private String exchangeTimezoneName;
	private double regularMarketPrice;
	private double fiftyTwoWeekHigh;
	private double fiftyTwoWeekLow;
	private double regularMarketDayHigh;
	private double regularMarketDayLow;
	private long regularMarketVolume;
	private String longName;
	private String shortName;
	private double chartPreviousClose;
	private double previousClose;
	private int scale;
	private int priceHint;
	private CurrentTradingPeriod currentTradingPeriod;
	private List<List<TradingPeriod>> tradingPeriods;
	private String dataGranularity;
	private String range;
	private List<String> validRanges;

}
