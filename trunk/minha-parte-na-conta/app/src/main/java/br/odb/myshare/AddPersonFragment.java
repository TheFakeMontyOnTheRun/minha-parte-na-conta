package br.odb.myshare;


import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.app.Fragment;
import android.provider.MediaStore;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import br.odb.myshare.datamodel.BarAccount;
import br.odb.myshare.datamodel.Person;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddPersonFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddPersonFragment extends DialogFragment implements View.OnClickListener {
    private EditText edtPersonName;
    private ImageView ivPersonPhoto;
    private Bitmap currentPhoto;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddPersonFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AddPersonFragment newInstance(String param1, String param2) {
        AddPersonFragment fragment = new AddPersonFragment();
        return fragment;
    }

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
        this.ivPersonPhoto = (ImageView) rootView.findViewById( R.id.ivPersonPhoto );

        rootView.findViewById( R.id.ibtTakePicture ).setOnClickListener( this );
        rootView.findViewById( R.id.btnOkAddPerson ).setOnClickListener( this );
        return rootView;
    }

    @Override
    public void onClick(View view) {
        switch ( view.getId() ) {
            case R.id.btnOkAddPerson:
                Person person = new Person(edtPersonName.getText().toString());
                person.addBitmap( currentPhoto );
				BarAccount.getCurrentBarAccount().addNewPerson(person);
                this.dismiss();
                break;

            case R.id.ibtTakePicture:
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
            currentPhoto = (Bitmap) extras.get("data");
            ivPersonPhoto.setImageBitmap(currentPhoto);
        }
    }
}
