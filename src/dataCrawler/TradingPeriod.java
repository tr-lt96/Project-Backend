package dataCrawler;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TradingPeriod {
	private String timezone;
	private long start;
	private long end;
	private int gmtoffset;

}
