package br.odb.myshare.datamodel;

import java.util.ArrayList;

public class Person {
	public String name;
	public ArrayList< Item > itemsConsumed;

	public Person( String name, ArrayList<Item> itemsConsumed) {
		
		this.name = name;
		this.itemsConsumed = itemsConsumed;
	}

	public Person(String name) {

		this.name = name;
		itemsConsumed = new ArrayList<Item>();
	}

	public CharSequence getName() {
		
		return name;
	}
	
	@Override
	public String toString() {
	
		return name;
	}
}