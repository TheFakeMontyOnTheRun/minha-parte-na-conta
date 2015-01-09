package br.odb.myshare;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import br.odb.myshare.datamodel.BarAccount;
import br.odb.myshare.datamodel.Item;

public class RegisterProductActivity extends Activity implements OnClickListener {

	Spinner spnProducts;
	EditText edtProduct;
	EditText edtCost;
	private Button btnAdd;
	private Button btnDelete;
	private Button btnNext;
	private Button btnCopy;
	
	
	public class ProductSpinAdapter extends ArrayAdapter<Item> {

		public ProductSpinAdapter(Context context, int textViewResourceId,
				Item[] values) {
			super(context, textViewResourceId, values);

		}
	}
	
	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		
		Intent intent = new Intent(this, ShowCreditsActivity.class);
		startActivity(intent);
		return true;
	}

	@Override
	protected void onDestroy() {
	
		BarAccount.getCurrentBarAccount().saveAccount( this );
		
		super.onDestroy();
	}

	
	public void updateUI() {

		List<Item> products = BarAccount.getCurrentBarAccount().getItems();
		Item[] items = new Item[ products.size()];
		items = products.toArray(items);

		spnProducts.setAdapter(new ProductSpinAdapter(this,
				android.R.layout.simple_spinner_dropdown_item, items ));
		
		
		btnDelete.setEnabled(  ( spnProducts.getCount() > 0 )  );
		btnCopy.setEnabled(  ( spnProducts.getCount() > 0 )  );
		btnNext.setEnabled(  ( spnProducts.getCount() > 0 )  );
		
		if ( spnProducts.getCount() > 0 ) {
			
			spnProducts.setSelection( spnProducts.getCount() - 1, true );
		}

	}
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register_product);
		
		
		spnProducts = (Spinner) findViewById( R.id.spnProducts );
		edtProduct = (EditText) findViewById( R.id.edtProduct );
		edtCost = (EditText) findViewById( R.id.edtCost );
		
		
		
		btnAdd = (Button) findViewById( R.id.btnAddProduct );
		btnAdd.setOnClickListener( this );

		btnDelete = (Button) findViewById( R.id.btnDeleteProduct );
		btnDelete.setOnClickListener( this );
		
		
		btnNext = (Button) findViewById( R.id.btnGoCheckout );
		btnNext.setOnClickListener( this );
		
		btnCopy = (Button) findViewById( R.id.btnCopy );
		btnCopy.setOnClickListener( this );		
		
		updateUI();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.register_product, menu);
		return true;
	}
	
	@Override
	protected void onPause() {
		if ( BarAccount.getCurrentBarAccount() != null )
			BarAccount.getCurrentBarAccount().saveAccount( this );

		super.onPause();
	}


	public void onClick(View v) {
		
		float cost = 0;
		String name = null;
		Item item = null;
		

		Button btn = (Button) findViewById( R.id.btnDeleteProduct );
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
				
				
			case R.id.btnGoCheckout:
				
				Intent intent = new Intent( this, CheckoutActivity.class );
				startActivity( intent );	
				break;
		}		
	}
}
