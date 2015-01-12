package br.odb.myshare;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

import br.odb.myshare.datamodel.BarAccount;

public class StartNewBarAccountFragment extends Fragment implements OnClickListener {

    View rootView;


    public static Fragment newInstance() {
        return new StartNewBarAccountFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        rootView = inflater.inflate(R.layout.activity_start_new_bar_account, container, false);
        super.onCreate(savedInstanceState);

        Button btn;

        btn = (Button) rootView.findViewById(R.id.btnContinueAccount);
        btn.setOnClickListener(this);

        btn = (Button) rootView.findViewById(R.id.btnNewBarAccount);
        btn.setOnClickListener(this);

        return rootView;
    }


    public void onClick(View btn) {


        BarAccount.createNewBarAccount();

        switch (btn.getId()) {

            case R.id.btnNewBarAccount:
                BarAccount.getCurrentBarAccount().saveAccount(getActivity());
                break;
            case R.id.btnContinueAccount:
                BarAccount.getCurrentBarAccount().restoreAccount(getActivity());
                break;
        }
    }
}
