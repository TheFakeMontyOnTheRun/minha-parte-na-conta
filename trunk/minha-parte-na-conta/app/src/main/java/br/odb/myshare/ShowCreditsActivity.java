package br.odb.myshare;

import br.odb.myshare.datamodel.BarAccount;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class ShowCreditsActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_show_credits);
	}

	@Override
	protected void onPause() {
		if ( BarAccount.getCurrentBarAccount() != null )
			BarAccount.getCurrentBarAccount().saveAccount( this );

		super.onPause();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.show_credits, menu);
		return true;
	}

}
