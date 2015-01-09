package br.odb.myshare;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import br.odb.myshare.datamodel.BarAccount;
import br.odb.myshare.datamodel.Item;
import br.odb.myshare.datamodel.Person;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class ShowFinalCheckoutActivity extends Activity {

	TextView tvSubTotalGeral;
	ListView lstTotals;
	
	@Override
	protected void onPause() {
		if ( BarAccount.getCurrentBarAccount() != null )
			BarAccount.getCurrentBarAccount().saveAccount( this );

		super.onPause();
	}


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_show_final_checkout);

		List<Person> people = BarAccount.getCurrentBarAccount().getPeople();
		ArrayList<String> totals = new ArrayList<String>();
		float total = 0;
		float currentTotal = 0;
		float accountTotal = 0;
		String missing = "";
		NumberFormat format = NumberFormat.getCurrencyInstance();

		for (Person p : people) {
			currentTotal = BarAccount.getCurrentBarAccount().getShare(p);
			total += currentTotal;
			totals.add(p.name + " : " + format.format(currentTotal * 1.1f));
		}

		for (Item i : BarAccount.getCurrentBarAccount().getItems()) {
			accountTotal += i.cost;
		}

		tvSubTotalGeral = (TextView) findViewById(R.id.tvSubTotalGeral);
		lstTotals = (ListView) findViewById(R.id.lstTotals);
		lstTotals.setAdapter(new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, totals));

		if (((int) 100 * (total - accountTotal)) < 0) {
			missing = " ( falta: "
					+ format.format(1.1 * Math.abs(total - accountTotal))
					+ " )";
			tvSubTotalGeral.setText("Total pago/Total da conta: "
					+ format.format(total * 1.1f) + "/ "
					+ format.format(accountTotal * 1.1f) + missing);
			tvSubTotalGeral.setTextColor(Color.RED);
		} else {
			tvSubTotalGeral.setText("Tudo pago!");
			tvSubTotalGeral.setTextColor(Color.GREEN);
		}
	}
}
