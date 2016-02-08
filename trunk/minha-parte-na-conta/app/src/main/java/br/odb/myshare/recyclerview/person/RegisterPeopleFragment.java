package br.odb.myshare.recyclerview.person;

import java.util.Observable;
import java.util.Observer;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

import br.odb.myshare.DividerItemDecoration;
import br.odb.myshare.R;
import br.odb.myshare.datamodel.BarAccount;
import br.odb.myshare.datamodel.Person;

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

		this.rclPeople = (RecyclerView) rootView.findViewById(R.id.person_recycler_view);
		rclPeople.setLayoutManager(new LinearLayoutManager(getActivity()));
		rclPeople.setAdapter(new Adapter(this));
		rclPeople.addItemDecoration( new DividerItemDecoration((int) (5 * getResources().getDisplayMetrics().density)));

		rootView.findViewById( R.id.add_person_fab ).setOnClickListener( this );

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

			case R.id.add_person_fab:
				AddPersonFragment currentlyShownDialog = new AddPersonFragment();
				currentlyShownDialog.show( this.getFragmentManager(), "add_person" );
				break;
			default:
				Person p = BarAccount.getCurrentBarAccount().peopleWithName( ((TextView) v.findViewById(R.id.tvPersonNameCard)).getText().toString() );
				ViewPerson.viewPerson(p, getActivity());
		}
	}

	@Override
	public void update(Observable observable, Object o) {
		updateUI();
	}
}
