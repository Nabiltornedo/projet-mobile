package com.example.projet_mobile.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.projet_mobile.R;
import com.example.projet_mobile.model.Budget;

import java.util.List;

public class BudgetAdapter extends ArrayAdapter<Budget> {

    public BudgetAdapter(Context context, List<Budget> budgets) {
        super(context, 0, budgets);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Budget budget = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_budget, parent, false);
        }

        TextView txtCategorie = convertView.findViewById(R.id.txtCategorie);
        TextView txtMontant = convertView.findViewById(R.id.txtMontant);
        TextView txtDate = convertView.findViewById(R.id.txtDate);

        txtCategorie.setText("Catégorie : " + budget.getCategorie());
        txtMontant.setText("Montant : " + budget.getMontant() + " DA");
        txtDate.setText("Date : " + budget.getDate());

        return convertView;
    }
}
