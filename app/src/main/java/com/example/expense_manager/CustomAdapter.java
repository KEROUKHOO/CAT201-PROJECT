package com.example.expense_manager;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {

    private Context context;
    private ArrayList inc_id, inc_name, inc_amount, inc_date, inc_category;

    CustomAdapter(Context context, ArrayList inc_id, ArrayList inc_name, ArrayList inc_amount,
                  ArrayList inc_date, ArrayList inc_category){
        this.context = context;
        this.inc_id = inc_id;
        this.inc_name = inc_name;
        this.inc_amount = inc_amount;
        this.inc_date = inc_date;
        this.inc_category = inc_category;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.my_row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.inc_id_txt.setText(String.valueOf(inc_id.get(position)));
        holder.inc_name_txt.setText(String.valueOf(inc_name.get(position)));
        holder.inc_amount_txt.setText(String.valueOf(inc_amount.get(position)));
        holder.inc_date_txt.setText(String.valueOf(inc_date.get(position)));
        holder.inc_category_txt.setText(String.valueOf(inc_category.get(position)));
    }

    @Override
    public int getItemCount() {
        return inc_id.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView inc_id_txt, inc_name_txt, inc_amount_txt, inc_date_txt, inc_category_txt;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            inc_id_txt = itemView.findViewById(R.id.inc_id_txt);
            inc_name_txt = itemView.findViewById(R.id.inc_name_txt);
            inc_amount_txt = itemView.findViewById(R.id.inc_amount_txt);
            inc_date_txt = itemView.findViewById(R.id.inc_date_txt);
            inc_category_txt = itemView.findViewById(R.id.inc_category_txt);
        }
    }
}
