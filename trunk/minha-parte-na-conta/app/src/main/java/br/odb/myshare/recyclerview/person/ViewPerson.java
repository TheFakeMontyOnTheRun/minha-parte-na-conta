package br.odb.myshare.recyclerview.person;


import android.os.Bundle;
import android.app.Fragment;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import br.odb.myshare.R;
import br.odb.myshare.datamodel.BarAccount;
import br.odb.myshare.datamodel.Person;


/**
 * A simple {@link Fragment} subclass.
 */
public class ViewPerson extends DialogFragment implements View.OnClickListener {

    private ImageView ivPersonPhoto;
    private TextView tvPersonName;
    private Person person;

    public ViewPerson() {
    }

    public static void viewPerson( Person person, FragmentActivity fromActivity ) {
        ViewPerson thisDialog = new ViewPerson();
        thisDialog.show( fromActivity.getSupportFragmentManager(), "view_person" );

        //this is getting ridiculous
        Bundle bundle = new Bundle();
        bundle.putParcelable( "person", person );
        thisDialog.setArguments( bundle );
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.person = (Person) getArguments().getParcelable( "person");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_view_person, container, false);
        rootView.findViewById(R.id.btnDeletePerson).setOnClickListener( this );
        this.ivPersonPhoto = (ImageView) rootView.findViewById(R.id.ivMainPhoto);
        this.tvPersonName = (TextView) rootView.findViewById(R.id.tvPersonName);

        setPerson( person );
        return rootView;
    }

    private void setPerson( Person person ) {

        if ( person.getPhoto() != null ) {
            ivPersonPhoto.setImageBitmap( person.getPhoto() );
        }

        tvPersonName.setText( person.getName() );
    }

    @Override
    public void onClick(View view) {
        BarAccount.getCurrentBarAccount().removePerson( person );

        this.dismiss();
    }
}
