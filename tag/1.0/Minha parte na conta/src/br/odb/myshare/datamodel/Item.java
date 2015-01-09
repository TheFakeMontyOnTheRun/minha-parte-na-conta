package br.odb.myshare.datamodel;

import java.text.NumberFormat;

public class Item {
	
	public String name;
	public float cost;

	public Item( String name, float cost ) {
		
		this.name = name;
		this.cost = cost;
	}
	
	@Override
	public String toString() {
		NumberFormat format = NumberFormat.getCurrencyInstance();
		return name + " - " + format.format(  cost );  
	}

	public String getName() {

		return name;
	}

	public float getCost() {

		return cost;
	}
}