package br.odb.myshare;

import java.util.List;
import java.util.Observable;
import java.util.Observer;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import br.odb.myshare.datamodel.BarAccount;
import br.odb.myshare.datamodel.Person;
import br.odb.myshare.recyclerview.person.Adapter;

public class RegisterPeopleFragment extends Fragment implements OnClickListener, Observer {

    View rootView;

	RecyclerView rclPeople;


    public static Fragment newInstance() {
        return new RegisterPeopleFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        rootView = inflater.inflate(R.layout.activity_register_people, container, false);

		this.rclPeople = (RecyclerView) rootView.findViewById(R.id.my_recycler_view);
		rclPeople.setLayoutManager( new LinearLayoutManager( getActivity()) );
		rclPeople.setAdapter( new Adapter( this ) );


		rootView.findViewById( R.id.add_person_fab ).setOnClickListener( this );
		rootView.findViewById( R.id.deleteIt ).setOnClickListener( this );

		BarAccount.getCurrentBarAccount().deleteObservers();
		BarAccount.getCurrentBarAccount().addObserver( this );

		updateUI();

        return rootView;
	}


	public void updateUI() {
		( (Adapter )rclPeople.getAdapter() ).setPeople( BarAccount.getCurrentBarAccount().getPeople() );
	}




	public void onClick(View v) {

		switch (v.getId()) {

			case R.id.deleteIt:
				ViewPerson.viewPerson( BarAccount.getCurrentBarAccount().getPeople().get( 0 ), getActivity() );
				break;

			case R.id.add_person_fab:

				AddPersonFragment currentlyShownDialog = new AddPersonFragment();
				currentlyShownDialog.show( this.getFragmentManager(), "add_person" );
				break;
			default:
				Person p = BarAccount.getCurrentBarAccount().peopleWithName( ((TextView) v.findViewById(R.id.tvPersonNameCard)).getText().toString() );
				ViewPerson.viewPerson( p, getActivity() );
		}
	}

	@Override
	public void update(Observable observable, Object o) {
		updateUI();
	}
}
