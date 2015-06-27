package br.odb.myshare.datamodel;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Observable;


import android.content.Context;

import br.odb.myshare.PeopleFragment;


public class BarAccount extends Observable {
	
	//------static stuff-----------------
	private static BarAccount currentAccount;
	
	public static void createNewBarAccount() {
		
		currentAccount = new BarAccount();
	}
	
	public static BarAccount getCurrentBarAccount() {

        if ( currentAccount == null ) {
            createNewBarAccount();
        }

		return currentAccount;
	}	

	//------------------instance stuff -----
	private ArrayList< Person > people = new ArrayList< Person >();
	private ArrayList< Item > itemRepository = new ArrayList< Item >();
	private HashMap< Item, ArrayList< Person > > checkList = new HashMap<Item, ArrayList<Person>>();
	
	
	public List< Person > getPeople() {
		
		return people;
	}
	
	public List< Item > getItems() {
		
		return itemRepository;
	}
	
	public void addNewPerson( Person newPerson ) {
		
		people.add( newPerson );
		updateObservers();
	}

	private void updateObservers() {
		setChanged();
		notifyObservers();
	}

	public void addNewItem( Item newItem ) {
		
		checkList.put( newItem, new ArrayList< Person >() );
		itemRepository.add( newItem );
		this.updateObservers();
	}

	public Person peopleWithName( String name ) {

		Person toReturn = null;

		for( Person person : people ) {

			if ( person.getName().equals( name ) ) {
				toReturn = person;
			}
		}

		return toReturn;
	}

	public void removePerson(Person person) {
		
		people.remove( person );
		this.updateObservers();
	}

	public void removeProduct(Item item) {

		itemRepository.remove( item );
		this.updateObservers();
	}

	public void purchase(Person person, Item item) {

		ArrayList< Person > costumers = checkList.get( item );
		
		if ( !costumers.contains( person ) ) {
			costumers.add( person );
		}
		
		if ( !person.itemsConsumed.contains( item ) ) {
			person.itemsConsumed.add( item );
		}

		this.updateObservers();
	}

	public void cancelPurchase(Person person, Item item) {

		ArrayList< Person > costumers = checkList.get( item );
		costumers.remove( person );		
		person.itemsConsumed.remove( item );

		this.updateObservers();
	}

	public float getShare(Person person) {

		float toReturn = 0;
		List< Item > items = person.itemsConsumed;
		
		for( Item item: items ) {
			
			toReturn += getSharePrice( item );
		}
		return toReturn;
	}

	private float getSharePrice(Item item) {

		return ( item.cost ) / ( this.checkList.get( item ).size() );
	}

	public ArrayList< Person > getCostumersForItem(Item item) {

		return checkList.get( item );
	}

	public void saveAccount( Context context ) {

	String FILENAME = "data.dat";


    	FileOutputStream fos;
    	DataOutputStream dos;
    	
		try {
			fos = context.openFileOutput(FILENAME, Context.MODE_PRIVATE);
			dos = new DataOutputStream( fos );
	    	
	    	dos.writeInt( itemRepository.size() );

	    	for ( int c = 0; c < itemRepository.size(); ++c ) {    		

	    		dos.writeUTF( itemRepository.get( c ).name );
	    		dos.writeFloat( itemRepository.get( c ).cost );
	    	}

	    	dos.writeInt( people.size() );

	    	for ( int c = 0; c < people.size(); ++c ) {    		

	    		dos.writeUTF( people.get( c ).name );
	    		dos.writeInt( people.get( c ).itemsConsumed.size() );
	    		
	    		for ( int d = 0; d < people.get( c ).itemsConsumed.size(); ++d ) {   
	    			dos.writeInt( itemRepository.indexOf( people.get( c ).itemsConsumed.get( d ) ) );
	    		}
	    	}
	    	
	    	dos.close();
	    	fos.close();
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}

	public void restoreAccount( Context context ) {
		
		String FILENAME = "data.dat";    	
    	int len = 0;
    	int len2 = 0;
    	int id;
    	float cost;
    	String str;
    	FileInputStream fis;
    	DataInputStream dis;
    	Person person;
    	Item item;
    	
		try {
			fis = context.openFileInput( FILENAME );
			dis = new DataInputStream( fis );

	    	len = dis.readInt();

	    	for ( int c = 0; c < len; ++c ) {    		

	    		str = dis.readUTF();
	    		cost = dis.readFloat();
	    		
	    		item = new Item( str, cost );
	    		addNewItem( item );
	    	}			
			
	    	len = dis.readInt();

	    	for ( int c = 0; c < len; ++c ) {	    		

	    		str = dis.readUTF();
	    		person = new Person( str );
	    		this.addNewPerson( person );
	    		len2 = dis.readInt();
	    		
	    		for ( int d = 0; d < len2; ++d ) {
		    		id = dis.readInt();
		    		item = itemRepository.get( id );
		    		purchase( person, item );
	    		}
	    	}
	    	
	    	dis.close();
	    	fis.close();
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
