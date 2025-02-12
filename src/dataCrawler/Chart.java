package dataCrawler;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Chart {
	private List<Result> result;
	private Object error;

}
