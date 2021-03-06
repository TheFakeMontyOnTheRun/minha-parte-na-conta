package br.odb.myshare;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import br.odb.myshare.datamodel.BarAccount;
import br.odb.myshare.recyclerview.person.PeopleFragment;
import br.odb.myshare.recyclerview.person.RegisterPeopleFragment;
import br.odb.myshare.recyclerview.product.RegisterProductFragment;


public class CalculateShareActivity extends AppCompatActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks {

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculate_share);

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putParcelable( "state", BarAccount.getCurrentBarAccount());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        BarAccount.createFrom( savedInstanceState.getParcelable( "state" ) );
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments
        FragmentManager fragmentManager = getSupportFragmentManager();


        switch( position ) {
            case 0:
                fragmentManager.beginTransaction()
                        .replace(R.id.container, StartNewBarAccountFragment.newInstance() )
                        .commit();

                break;

            case 1:
                fragmentManager.beginTransaction()
                        .replace(R.id.container, RegisterProductFragment.newInstance() )
                        .commit();

                break;
            case 2:
                fragmentManager.beginTransaction()
                        .replace(R.id.container, RegisterPeopleFragment.newInstance() )
                        .commit();

                break;
            case 3:

                fragmentManager.beginTransaction()
                        .replace(R.id.container, CheckoutFragment.newInstance() )
                        .commit();

                break;

            case 4:

                fragmentManager.beginTransaction()
                        .replace(R.id.container, ShowCreditsFragment.newInstance() )
                        .commit();

                break;

            case 5:

                fragmentManager.beginTransaction()
                        .replace(R.id.container, PeopleFragment.newInstance() )
                        .commit();

                break;

        }
    }


    /*
    <string name="title_section0">Nova conta</string>
    <string name="title_section1">Produtos consumidos</string>
    <string name="title_section2">Pessoas na mesa</string>
    <string name="title_section3">Minha parte</string>
    * **/

    public void onSectionAttached(int number) {
        switch (number) {
            case 0:
                mTitle = getString(R.string.title_section0);
                break;
            case 1:
                mTitle = getString(R.string.title_section1);
                break;
            case 2:
                mTitle = getString(R.string.title_section2);

            case 3:
                mTitle = getString(R.string.title_section3);
                break;

            case 4:
                mTitle = getString(R.string.title_section4);
                break;

            case 5:
                mTitle = getString(R.string.title_section5);
                break;


        }
    }

    public void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.calculate_share, menu);
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();


        return super.onOptionsItemSelected(item);
    }
}
