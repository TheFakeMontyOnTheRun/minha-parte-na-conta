package br.odb.myshare.datamodel;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.text.NumberFormat;

public class Item implements Parcelable {
	
	public String name;
	public float cost;
	private Bitmap photo;

	public Item( String name, float cost ) {
		
		this.name = name;
		this.cost = cost;
	}

	protected Item(Parcel in) {
		name = in.readString();
		cost = in.readFloat();
		photo = in.readParcelable(Bitmap.class.getClassLoader());
	}

	public static final Creator<Item> CREATOR = new Creator<Item>() {
		@Override
		public Item createFromParcel(Parcel in) {
			return new Item(in);
		}

		@Override
		public Item[] newArray(int size) {
			return new Item[size];
		}
	};

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

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel parcel, int i) {
		parcel.writeString(name);
		parcel.writeFloat(cost);
		parcel.writeParcelable(photo, i);
	}
}