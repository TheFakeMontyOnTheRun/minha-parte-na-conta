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
import br.odb.myshare.datamodel.Person;

public class RegisterPeopleFragment extends Fragment implements OnClickListener {

	public class PersonSpinAdapter extends ArrayAdapter<Person> {

		public PersonSpinAdapter(Context context, int textViewResourceId,
				Person[] values) {
			super(context, textViewResourceId, values);

		}
	}

    View rootView;

    Spinner spnPeople;
	private Button btnAdd;
	private Button btnDelete;
	private Button btnNext;




    public static Fragment newInstance() {
        return new RegisterPeopleFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        rootView = inflater.inflate(R.layout.activity_register_people, container, false);

		spnPeople = (Spinner) rootView.findViewById(R.id.spnPerson);



		btnAdd = (Button) rootView.findViewById(R.id.btnAddPerson);
		btnAdd.setOnClickListener(this);

		btnDelete = (Button) rootView.findViewById(R.id.btnDeletePerson);
		btnDelete.setOnClickListener(this);


		btnNext = (Button) rootView.findViewById(R.id.btnGoProducts);
		btnNext.setOnClickListener(this);

		updateUI();

        return rootView;
	}

	public void updateUI() {

		List<Person> people = BarAccount.getCurrentBarAccount().getPeople();
		Person[] persons = new Person[people.size()];
		persons = people.toArray(persons);

		spnPeople.setAdapter(new PersonSpinAdapter( getActivity(),
				android.R.layout.simple_spinner_dropdown_item, persons));
		
		btnDelete.setEnabled( ( spnPeople.getCount() > 0 ) );
		btnNext.setEnabled(  ( spnPeople.getCount() > 0 )  );
		
		if ( spnPeople.getCount() > 0 ) {
			
			spnPeople.setSelection( spnPeople.getCount() - 1, true );
		}
	}

	public void onClick(View v) {

		Person person;
		EditText edtPerson;
				
		edtPerson = (EditText) rootView.findViewById(R.id.edtPerson);

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
		}
	}
}
