package br.odb.myshare.recyclerview.product;


import android.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import br.odb.myshare.R;
import br.odb.myshare.datamodel.BarAccount;
import br.odb.myshare.datamodel.Item;


/**
 * A simple {@link Fragment} subclass.
 */
public class ViewProduct extends DialogFragment implements View.OnClickListener {

    private ImageView ivPersonPhoto;
    private TextView tvPersonName;
    private Item product;

    public ViewProduct() {
    }

    public static void viewProduct( Item item, FragmentActivity fromActivity ) {
        ViewProduct thisDialog = new ViewProduct();
        thisDialog.show( fromActivity.getSupportFragmentManager(), "view_product" );

        //this is getting ridiculous
        Bundle bundle = new Bundle();
        bundle.putParcelable( "product", item );
        thisDialog.setArguments( bundle );
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.product = (Item) getArguments().getParcelable( "product");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_view_product, container, false);
        rootView.findViewById(R.id.btnDeleteProduct).setOnClickListener( this );
        this.ivPersonPhoto = (ImageView) rootView.findViewById(R.id.ivMainProductPhoto);
        this.tvPersonName = (TextView) rootView.findViewById(R.id.tvProductName);

        setProduct(product);
        return rootView;
    }

    private void setProduct( Item product ) {

        if ( product.getPhoto() != null ) {
            ivPersonPhoto.setImageBitmap( product.getPhoto() );
        }

        tvPersonName.setText( product.getName() );
    }

    @Override
    public void onClick(View view) {
        BarAccount.getCurrentBarAccount().removeProduct( product);

        this.dismiss();
    }
}
