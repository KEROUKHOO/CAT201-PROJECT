package com.example.expense_manager;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CustomExpensesAdapter extends RecyclerView.Adapter<CustomExpensesAdapter.MyViewHolder> {

    private Context context;
    Activity activity;
    private ArrayList exp_id, exp_name, exp_amount, exp_date, exp_category;

    CustomExpensesAdapter(Activity activity, Context context, ArrayList exp_id, ArrayList exp_name, ArrayList exp_amount,
                          ArrayList exp_date, ArrayList exp_category){
        this.activity = activity;
        this.context = context;
        this.exp_id = exp_id;
        this.exp_name = exp_name;
        this.exp_amount = exp_amount;
        this.exp_date = exp_date;
        this.exp_category = exp_category;
    }

    //public CustomExpensesAdapter(ArrayList<String> exp_id, ArrayList<String> exp_name,
    //                             ArrayList<String> exp_amount, ArrayList<String> exp_date, ArrayList<String> exp_category) {
    //}

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.my_row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") final int position) {

        holder.exp_id_txt.setText(String.valueOf(exp_id.get(position)));
        holder.exp_name_txt.setText(String.valueOf(exp_name.get(position)));
        holder.exp_amount_txt.setText(String.valueOf(exp_amount.get(position)));
        holder.exp_date_txt.setText(String.valueOf(exp_date.get(position)));
        holder.exp_category_txt.setText(String.valueOf(exp_category.get(position)));
        holder.mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override                                        // Transfer Information to UpdateExpensesActivity
            public void onClick(View view) {
                Intent intent = new Intent(context, UpdateExpensesActivity.class);
                intent.putExtra("expenses_id", String.valueOf(exp_id.get(position)));
                intent.putExtra("expenses_name", String.valueOf(exp_name.get(position)));
                intent.putExtra("expenses_amount", String.valueOf(exp_amount.get(position)));
                intent.putExtra("expenses_date", String.valueOf(exp_date.get(position)));
                intent.putExtra("expenses_category", String.valueOf(exp_category.get(position)));
                activity.startActivityForResult(intent, 1);
            }
        });
    }

    @Override
    public int getItemCount() { return exp_id.size(); }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView exp_id_txt, exp_name_txt, exp_amount_txt, exp_date_txt, exp_category_txt;
        LinearLayout mainLayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            exp_id_txt = itemView.findViewById(R.id.inc_id_txt);
            exp_name_txt = itemView.findViewById(R.id.inc_name_txt);
            exp_amount_txt = itemView.findViewById(R.id.inc_amount_txt);
            exp_date_txt = itemView.findViewById(R.id.inc_date_txt);
            exp_category_txt = itemView.findViewById(R.id.inc_category_txt);
            mainLayout = itemView.findViewById(R.id.mainLayout);
        }
    }
}
