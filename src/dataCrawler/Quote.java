package dataCrawler;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Quote {
	private List<Double> open;
	private List<Double> high;
	private List<Double> low;
	private List<Double> close;
	private List<Long> volume;

}
