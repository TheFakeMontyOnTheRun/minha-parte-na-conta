package br.odb.myshare.datamodel;

import android.graphics.Bitmap;

import java.io.Serializable;
import java.text.NumberFormat;

public class Item implements Serializable {
	
	public String name;
	public float cost;
	private Bitmap photo;

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

	public Bitmap getPhoto() {
		return photo;
	}

	public void addBitmap(Bitmap currentPhoto) {
		photo = currentPhoto;
	}
}