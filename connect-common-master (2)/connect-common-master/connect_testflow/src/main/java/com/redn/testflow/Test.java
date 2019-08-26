package com.redn.testflow;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Test {
	
	public static void main(String[] args) {
		Double sumOfAllPrices = 9338.4000000000014551915228366851806640625;
		
		System.out.println(new BigDecimal(sumOfAllPrices).setScale(5,RoundingMode.CEILING));
	}

}
