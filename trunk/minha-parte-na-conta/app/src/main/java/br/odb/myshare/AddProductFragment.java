package br.odb.myshare;


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

import br.odb.myshare.datamodel.BarAccount;
import br.odb.myshare.datamodel.Item;
import br.odb.myshare.datamodel.Person;

public class AddProductFragment extends DialogFragment implements View.OnClickListener {
    private EditText edtProductName;
    private EditText edtProductPrice;
    private ImageButton ibtProductPhoto;
    private Bitmap currentPhoto;

    public AddProductFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_add_product, container, false);

        this.edtProductName = (EditText) rootView.findViewById( R.id.edtAddProductName );
        this.edtProductPrice = (EditText) rootView.findViewById( R.id.edtProductPrice );
        this.ibtProductPhoto = (ImageButton) rootView.findViewById( R.id.ibtProductPhoto );

        rootView.findViewById( R.id.ibtProductPhoto ).setOnClickListener( this );
        rootView.findViewById( R.id.btnOkAddProduct ).setOnClickListener( this );
        return rootView;
    }

    @Override
    public void onClick(View view) {
        switch ( view.getId() ) {
            case R.id.btnOkAddProduct:
                Item item = new Item(edtProductName.getText().toString(), Float.parseFloat( edtProductPrice.getText().toString() ));
                item.addBitmap( currentPhoto );
				BarAccount.getCurrentBarAccount().addNewItem(item);
                this.dismiss();
                break;

            case R.id.ibtProductPhoto:
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
            ibtProductPhoto.setImageBitmap(currentPhoto);
        }
    }
}
