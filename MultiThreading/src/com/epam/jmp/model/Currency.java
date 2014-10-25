package com.epam.jmp.model;

public enum Currency {

	EURO(1f),
	USD(1.2654f),
	GPB(0.7895f),
	RUR(52.9065f),
	BYR(13565.7692f);
	
	private final int metric = 1;
	private float ratio;
	
	
	Currency(float ratio) {
		this.ratio = ratio;
	}
	
	public static Currency resolveByName(String name) {
		for(Currency c : values()){
			if(c.name().equals(name)){
				return c;
			}
		}
		return null;
	}
	
	public static float convert(Currency from, Currency to, float value) {
		
		return value/from.ratio*to.ratio;
				
	}
	
	public static float convert(String from, String to, float value) {
		
		float result = -1;
		Currency currFrom = resolveByName(from);
		Currency currTo = resolveByName(to);
		if(currFrom!=null && currTo!=null){
			result = value/currFrom.ratio*currTo.ratio;
		}
		return result;	
	}
}
