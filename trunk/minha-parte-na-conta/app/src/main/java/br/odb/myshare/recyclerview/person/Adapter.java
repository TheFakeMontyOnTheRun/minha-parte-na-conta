package br.odb.myshare.recyclerview.person;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import br.odb.myshare.R;
import br.odb.myshare.datamodel.Person;

/**
 * Created by monty on 6/27/15.
 */
public class Adapter extends RecyclerView.Adapter {

    private final View.OnClickListener itemClickListener;

    public Adapter(View.OnClickListener listener) {
        this.itemClickListener = listener;
    }

    private class PersonViewHolder extends RecyclerView.ViewHolder {

        private final View view;

        public PersonViewHolder(View itemView) {
            super(itemView);

            this.view = itemView;
        }
    }

    final private List<Person> people = new ArrayList<>();

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.person_layout, parent, false);


        v.setOnClickListener( this.itemClickListener );

        PersonViewHolder vh = new PersonViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        Person person = people.get(position);

        ( (TextView) (( PersonViewHolder) holder ).view.findViewById( R.id.tvPersonNameCard)).setText( person.getName() );

        if ( person.getPhoto() != null ) {
            ( (ImageView) (( PersonViewHolder) holder ).view.findViewById( R.id.ivPersonMainPhoto ) ).setImageBitmap(person.getPhoto());
        }
    }

    @Override
    public int getItemCount() {
        return people.size();
    }

    public void setPeople( List< Person > people ) {
        this.people.clear();
        this.people.addAll( people );
        this.notifyDataSetChanged();
    }
}
