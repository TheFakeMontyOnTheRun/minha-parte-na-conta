package br.odb.myshare;

import java.text.NumberFormat;
import java.util.List;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.Spinner;
import android.widget.TextView;
import br.odb.myshare.datamodel.BarAccount;
import br.odb.myshare.datamodel.Item;
import br.odb.myshare.datamodel.Person;

public class CheckoutFragment extends Fragment implements OnItemSelectedListener, OnCheckedChangeListener  {


    View rootView;

	Spinner spnProducts;
	
	Spinner spnPeople;
	
	CheckBox chkBought;
	
	TextView tvflatTotal;
	
	TextView tvfinalTotal;
	
	TextView tvSharedWith;
	
	public class PersonSpinAdapter extends ArrayAdapter<Person> {

		public PersonSpinAdapter(Context context, int textViewResourceId,
				Person[] values) {
			super(context, textViewResourceId, values);

		}
	}
	
	public class ProductSpinAdapter extends ArrayAdapter<Item> {

		public ProductSpinAdapter(Context context, int textViewResourceId,
				Item[] values) {
			super(context, textViewResourceId, values);

		}
	}

	public void updateUI() {

		List<Item> products = BarAccount.getCurrentBarAccount().getItems();

        if ( products == null ) {
            return;
        }

		Item[] items = new Item[ products.size()];
		items = products.toArray(items);

		spnProducts.setAdapter(new ProductSpinAdapter( getActivity(),
				android.R.layout.simple_spinner_dropdown_item, items ));
		
		List<Person> people = BarAccount.getCurrentBarAccount().getPeople();
		Person[] persons = new Person[people.size()];
		persons = people.toArray(persons);

		spnPeople.setAdapter(new PersonSpinAdapter( getActivity(),
				android.R.layout.simple_spinner_dropdown_item, persons));


	}

    public static Fragment newInstance() {
        CheckoutFragment fragment = new CheckoutFragment();
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.activity_checkout, container, false);

        spnPeople = (Spinner) rootView.findViewById(R.id.spnConsumingPerson);
        spnProducts = (Spinner) rootView.findViewById(R.id.spnBougtProduct);
        tvflatTotal = (TextView) rootView.findViewById(R.id.tvFlatTotal);
        tvfinalTotal = (TextView) rootView.findViewById(R.id.tvTotal);
        tvSharedWith = (TextView) rootView.findViewById(R.id.tvSharedWith);
        chkBought = (CheckBox) rootView.findViewById(R.id.chkBought);

        spnPeople.setOnItemSelectedListener( this );
        spnProducts.setOnItemSelectedListener( this );
        chkBought.setOnCheckedChangeListener( this );


        updateUI();
        updateTexts();


        return rootView;
    }

	public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
		
		Person person = (Person) spnPeople.getSelectedItem();
		Item item = (Item) spnProducts.getSelectedItem();

		if ( chkBought.isChecked() ) { 		
			
			BarAccount.getCurrentBarAccount().purchase( person, item );		
		} else {
			BarAccount.getCurrentBarAccount().cancelPurchase( person, item );
		}
		
		updateTexts();
	}

	public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
			long arg3) {


		Person person = (Person) spnPeople.getSelectedItem();
		Item item = (Item) spnProducts.getSelectedItem();

		
		chkBought.setChecked( person.itemsConsumed.contains( item ) );
		updateTexts();
	}
	
	public void updateTexts() {
		
		if ( spnPeople.getChildCount() <= 0 ) {
            return;
        }

        Person person = (Person) spnPeople.getSelectedItem();
		Item item = (Item) spnProducts.getSelectedItem();
		float share = BarAccount.getCurrentBarAccount().getShare( person );
		NumberFormat format = NumberFormat.getCurrencyInstance();
		
		tvflatTotal.setText( format.format( share ) );
		tvfinalTotal.setText( format.format( share * 1.1f ) + " (+10%  Taxa de serviço comum)" );
		
		if ( chkBought.isChecked() ) {
			
			String sharedWith = "Compartilhado com ";
			
			for ( Person costumer : BarAccount.getCurrentBarAccount().getCostumersForItem( item ) ) {
				
				if ( costumer != person )
					sharedWith += costumer.getName() + ", ";
			}
			
			if ( BarAccount.getCurrentBarAccount().getCostumersForItem( item ).size() > 1 )
				tvSharedWith.setText( sharedWith.substring( 0, sharedWith.length() - 2 ) );
			else
				tvSharedWith.setText( "" );
		} else {
			
			String sharedWith = "Consumido por ";
			
			for ( Person costumer : BarAccount.getCurrentBarAccount().getCostumersForItem( item ) ) {

				sharedWith += costumer.getName() + ", ";
			}
			
			if ( BarAccount.getCurrentBarAccount().getCostumersForItem( item ).size() > 0 )
				tvSharedWith.setText( sharedWith.substring( 0, sharedWith.length() - 2 ) );
			else
				tvSharedWith.setText( "" );
		}
		
	}

	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub
		
	}
}
