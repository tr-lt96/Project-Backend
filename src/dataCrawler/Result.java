package dataCrawler;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Result {
	private Meta meta;
	private List<Long> timestamp;
	private Indicator indicators;

}
