package br.odb.myshare.recyclerview.product;

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
import br.odb.myshare.datamodel.Item;

public class RegisterProductFragment extends Fragment implements OnClickListener, Observer {

	View rootView;

	RecyclerView rclProduct;

	public static Fragment newInstance() {
		return new RegisterProductFragment();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		rootView = inflater.inflate(R.layout.activity_register_product, container, false);

		this.rclProduct = (RecyclerView) rootView.findViewById(R.id.product_recycler_view);
		rclProduct.setLayoutManager(new LinearLayoutManager(getActivity()));
		rclProduct.setAdapter(new Adapter(this));
		rclProduct.addItemDecoration( new DividerItemDecoration((int) (5 * getResources().getDisplayMetrics().density)));

		rootView.findViewById( R.id.add_product_fab ).setOnClickListener( this );

		BarAccount.getCurrentBarAccount().deleteObservers();
		BarAccount.getCurrentBarAccount().addObserver(this);

		updateUI();

		return rootView;
	}

	public void updateUI() {
		( (Adapter ) rclProduct.getAdapter() ).setProducts(BarAccount.getCurrentBarAccount().getItems());
	}

	public void onClick(View v) {

		switch (v.getId()) {

			case R.id.add_product_fab:

				AddProductFragment currentlyShownDialog = new AddProductFragment();
				currentlyShownDialog.show( this.getFragmentManager(), "add_product" );
				break;
			default:
				Item i = BarAccount.getCurrentBarAccount().productWithName(((TextView) v.findViewById(R.id.tvProductNameCard)).getText().toString());
				ViewProduct.viewProduct(i, getActivity());
		}
	}

	@Override
	public void update(Observable observable, Object o) {
		updateUI();
	}
}
