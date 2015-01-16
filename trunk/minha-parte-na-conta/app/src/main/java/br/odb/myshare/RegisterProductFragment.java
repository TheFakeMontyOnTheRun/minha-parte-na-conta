package br.odb.myshare;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import br.odb.myshare.datamodel.BarAccount;
import br.odb.myshare.datamodel.Item;

public class RegisterProductFragment extends Fragment implements OnClickListener {

    View rootView;
	Spinner spnProducts;
	EditText edtProduct;
	EditText edtCost;
	private Button btnAdd;
	private Button btnDelete;
	private Button btnCopy;
	
	
	public class ProductSpinAdapter extends ArrayAdapter<Item> {

		public ProductSpinAdapter(Context context, int textViewResourceId,
				Item[] values) {
			super(context, textViewResourceId, values);

		}
	}

	
	public void updateUI() {

		List<Item> products = BarAccount.getCurrentBarAccount().getItems();
		Item[] items = new Item[ products.size()];
		items = products.toArray(items);

		spnProducts.setAdapter(new ProductSpinAdapter( getActivity(),
				android.R.layout.simple_spinner_dropdown_item, items ));
		
		
		btnDelete.setEnabled(  ( spnProducts.getCount() > 0 )  );
		btnCopy.setEnabled(  ( spnProducts.getCount() > 0 )  );

		if ( spnProducts.getCount() > 0 ) {
			
			spnProducts.setSelection( spnProducts.getCount() - 1, true );
		}

	}


    public static Fragment newInstance() {
        return new RegisterProductFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

        rootView = inflater.inflate(R.layout.activity_register_product, container, false);

		spnProducts = (Spinner) rootView.findViewById( R.id.spnProducts );
		edtProduct = (EditText) rootView.findViewById( R.id.edtProduct );
		edtCost = (EditText) rootView.findViewById( R.id.edtCost );
		
		
		
		btnAdd = (Button) rootView.findViewById( R.id.btnAddProduct );
		btnAdd.setOnClickListener( this );

		btnDelete = (Button) rootView.findViewById( R.id.btnDeleteProduct );
		btnDelete.setOnClickListener( this );
		

		btnCopy = (Button) rootView.findViewById( R.id.btnCopy );
		btnCopy.setOnClickListener( this );		
		
		updateUI();

        return rootView;
	}


	public void onClick(View v) {
		
		float cost = 0;
		String name = null;
		Item item = null;
		

		Button btn = (Button) rootView.findViewById( R.id.btnDeleteProduct );
		btn.setOnClickListener( this );
		
		switch ( v.getId() ) {
		
			case R.id.btnCopy:

				if ( spnProducts.getChildCount() > 0 ) {
					
					item = (Item) spnProducts.getSelectedItem();
					edtProduct.setText( item.getName() );
					edtCost.setText( Float.toString( item.getCost() ) );				
				}
		
			case R.id.btnAddProduct:
				
				if ( edtProduct.getText().toString().length() > 0 && edtCost.getText().toString().length() > 0 ) {
					
					name = edtProduct.getText().toString();
					cost = Float.parseFloat( edtCost.getText().toString() );
					
					item = new Item( name, cost );
					BarAccount.getCurrentBarAccount().addNewItem( item );
					edtProduct.setText( "" );
					edtCost.setText( "" );				
					
					updateUI();
				}
				break;
				
			case R.id.btnDeleteProduct:
				
				item = (Item) spnProducts.getSelectedItem();
				BarAccount.getCurrentBarAccount().removeProduct( item );
				updateUI();
				
				
				break;			

		}		
	}
}
