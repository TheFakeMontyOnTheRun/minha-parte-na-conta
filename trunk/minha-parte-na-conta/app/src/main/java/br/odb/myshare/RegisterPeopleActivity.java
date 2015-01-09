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
import br.odb.myshare.datamodel.Person;

public class RegisterPeopleActivity extends Activity implements OnClickListener {

	public class PersonSpinAdapter extends ArrayAdapter<Person> {

		public PersonSpinAdapter(Context context, int textViewResourceId,
				Person[] values) {
			super(context, textViewResourceId, values);

		}
	}

	Spinner spnPeople;
	private Button btnAdd;
	private Button btnDelete;
	private Button btnNext;

	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		
		Intent intent = new Intent(this, ShowCreditsActivity.class);
		startActivity(intent);
		return true;
	}

	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_register_people);

		spnPeople = (Spinner) findViewById(R.id.spnPerson);



		btnAdd = (Button) findViewById(R.id.btnAddPerson);
		btnAdd.setOnClickListener(this);

		btnDelete = (Button) findViewById(R.id.btnDeletePerson);
		btnDelete.setOnClickListener(this);


		btnNext = (Button) findViewById(R.id.btnGoProducts);
		btnNext.setOnClickListener(this);

		updateUI();
	}

	public void updateUI() {

		List<Person> people = BarAccount.getCurrentBarAccount().getPeople();
		Person[] persons = new Person[people.size()];
		persons = people.toArray(persons);

		spnPeople.setAdapter(new PersonSpinAdapter(this,
				android.R.layout.simple_spinner_dropdown_item, persons));
		
		btnDelete.setEnabled( ( spnPeople.getCount() > 0 ) );
		btnNext.setEnabled(  ( spnPeople.getCount() > 0 )  );
		
		if ( spnPeople.getCount() > 0 ) {
			
			spnPeople.setSelection( spnPeople.getCount() - 1, true );
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		getMenuInflater().inflate(R.menu.register_people, menu);
		return true;
	}

	@Override
	protected void onDestroy() {
	
		BarAccount.getCurrentBarAccount().saveAccount( this );
		
		super.onDestroy();
	}
	
	@Override
	protected void onPause() {
		if ( BarAccount.getCurrentBarAccount() != null )
			BarAccount.getCurrentBarAccount().saveAccount( this );

		super.onPause();
	}


	
	public void onClick(View v) {

		Person person;
		EditText edtPerson;
				
		edtPerson = (EditText) findViewById(R.id.edtPerson);

		switch (v.getId()) {

			case R.id.btnAddPerson:
	
				if ( edtPerson.getText().toString().length() > 0 ) {
					
					person = new Person(edtPerson.getText().toString());
					BarAccount.getCurrentBarAccount().addNewPerson(person);
					edtPerson.setText("");
					
					updateUI();
					
				}
				break;

			case R.id.btnDeletePerson:
	
				person = (Person) spnPeople.getSelectedItem();
				BarAccount.getCurrentBarAccount().removePerson(person);
				
				updateUI();
				break;
	
			case R.id.btnGoProducts:
	
				Intent intent = new Intent(this, RegisterProductActivity.class);
				startActivity(intent);
				break;
		}

	}
}
