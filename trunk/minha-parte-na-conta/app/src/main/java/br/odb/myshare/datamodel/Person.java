package br.odb.myshare.datamodel;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class Person implements Parcelable {
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

	public static final Creator<Person> CREATOR = new Creator<Person>() {
		@Override
		public Person createFromParcel(Parcel in) {
			return new Person(in);
		}

		@Override
		public Person[] newArray(int size) {
			return new Person[size];
		}
	};

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

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel parcel, int i) {
		parcel.writeString( id );
		parcel.writeString( name );
		parcel.writeParcelable( photo, i );
	}

	protected Person(Parcel in) {
		id = in.readString();
		name = in.readString();
		photo = in.readParcelable(Bitmap.class.getClassLoader());
	}
}