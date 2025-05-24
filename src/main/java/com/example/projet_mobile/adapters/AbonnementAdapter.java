package com.example.projet_mobile.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.projet_mobile.R;
import com.example.projet_mobile.model.Abonnement;

import java.util.List;

public class AbonnementAdapter extends ArrayAdapter<Abonnement> {

    public AbonnementAdapter(Context context, List<Abonnement> abonnements) {
        super(context, 0, abonnements);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Abonnement abonnement = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_abonnement, parent, false);
        }

        TextView txtNom = convertView.findViewById(R.id.txtNom);
        TextView txtPrix = convertView.findViewById(R.id.txtPrix);
        TextView txtDate = convertView.findViewById(R.id.txtDate);

        txtNom.setText("Nom : " + abonnement.getNom());
        txtPrix.setText("Prix : " + abonnement.getPrix() + " DA");
         txtDate.setText("Date : " + abonnement.getDate());

        return convertView;
    }
}
