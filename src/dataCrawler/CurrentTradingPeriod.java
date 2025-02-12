package dataCrawler;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CurrentTradingPeriod {
	private TradingPeriod pre;
	private TradingPeriod regular;
	private TradingPeriod post;
}
