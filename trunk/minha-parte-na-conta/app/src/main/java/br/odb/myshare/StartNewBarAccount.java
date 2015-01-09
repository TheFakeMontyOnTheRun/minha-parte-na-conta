package br.odb.myshare;

import br.odb.myshare.datamodel.BarAccount;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class StartNewBarAccount extends Activity implements OnClickListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_start_new_bar_account);
		
		Button btn;
		
		btn = (Button) findViewById( R.id.btnContinueAccount );
		btn.setOnClickListener( this );

		btn = (Button) findViewById( R.id.btnNewBarAccount );
		btn.setOnClickListener( this );
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.start_new_bar_account, menu);
		return true;
	}

	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		
		Intent intent = new Intent(this, ShowCreditsActivity.class);
		startActivity(intent);
		return true;
	}

	
	
	@Override
	protected void onPause() {
		if ( BarAccount.getCurrentBarAccount() != null )
			BarAccount.getCurrentBarAccount().saveAccount( this );

		super.onPause();
	}

	@Override
	protected void onDestroy() {
		
		if ( BarAccount.getCurrentBarAccount() != null )
			BarAccount.getCurrentBarAccount().saveAccount( this );
		
		super.onDestroy();
	}

	public void onClick( View btn ) {
	
		
		BarAccount.createNewBarAccount();

		switch ( btn.getId() ) {
		
		case R.id.btnNewBarAccount:
				BarAccount.getCurrentBarAccount().saveAccount( this );
				
			case R.id.btnContinueAccount:
				
				BarAccount.getCurrentBarAccount().restoreAccount( this );
				Intent intent = new Intent( this, RegisterPeopleActivity.class );
				startActivity( intent );				
				break;
		}		
	}
}
