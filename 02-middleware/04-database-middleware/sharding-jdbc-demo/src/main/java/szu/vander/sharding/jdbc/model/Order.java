package szu.vander.sharding.jdbc.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Setter
@Getter
public class Order {

	private Long orderId;
	
	@DateTimeFormat(pattern="yyyy-MM-dd") 
	private Date orderTime;
	
	private Long customerId;

}
