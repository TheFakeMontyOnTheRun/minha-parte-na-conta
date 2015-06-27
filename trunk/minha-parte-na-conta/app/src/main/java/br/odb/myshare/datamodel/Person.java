package br.odb.myshare.datamodel;

import android.graphics.Bitmap;

import java.io.Serializable;
import java.util.ArrayList;

public class Person implements Serializable {
	public String name;
	public ArrayList< Item > itemsConsumed;
	private String id;
	private Bitmap photo;

	public Person( String id, String name, ArrayList<Item> itemsConsumed) {
		this.id = id;
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

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void addBitmap(Bitmap photo) {
		this.photo = photo;
	}

	public Bitmap getPhoto() {
		return photo;
	}
}