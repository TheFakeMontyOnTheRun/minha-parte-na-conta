package br.odb.myshare.recyclerview.person;


import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;

import br.odb.myshare.R;
import br.odb.myshare.datamodel.BarAccount;
import br.odb.myshare.datamodel.Person;

public class AddPersonFragment extends DialogFragment implements View.OnClickListener {
    private EditText edtPersonName;
    private ImageButton ibtPersonPhoto;
    private Bitmap currentPhoto;

    public AddPersonFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_add_person, container, false);

        this.edtPersonName = (EditText) rootView.findViewById( R.id.edtAddPersonName );
        this.ibtPersonPhoto = (ImageButton) rootView.findViewById( R.id.ibtPersonPhoto );

        rootView.findViewById( R.id.ibtPersonPhoto ).setOnClickListener( this );
        rootView.findViewById( R.id.btnOkAddPerson ).setOnClickListener(this);
        return rootView;
    }

    @Override
    public void onClick(View view) {
        switch ( view.getId() ) {
            case R.id.btnOkAddPerson:
                Person person = new Person(edtPersonName.getText().toString());

                if ( currentPhoto != null ) {
                    person.addBitmap(currentPhoto);
                }

                BarAccount.getCurrentBarAccount().addNewPerson(person);
                this.dismiss();
                break;

            case R.id.ibtPersonPhoto:
                dispatchTakePictureIntent();
                break;
        }
    }

    static final int REQUEST_IMAGE_CAPTURE = 1;

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity( this.getActivity().getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE  ) { //&& resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            if ( extras != null && extras.get("data") != null ) {
                currentPhoto = (Bitmap) extras.get("data");
                ibtPersonPhoto.setImageBitmap(currentPhoto);
            }
        }
    }
}
