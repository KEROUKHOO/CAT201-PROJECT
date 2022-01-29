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

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {

    private Context context;
    Activity activity;
    private ArrayList inc_id, inc_name, inc_amount, inc_date, inc_category;


    CustomAdapter(Activity activity, Context context, ArrayList inc_id, ArrayList inc_name, ArrayList inc_amount,
                  ArrayList inc_date, ArrayList inc_category){
        this.activity = activity;
        this.context = context;
        this.inc_id = inc_id;
        this.inc_name = inc_name;
        this.inc_amount = inc_amount;
        this.inc_date = inc_date;
        this.inc_category = inc_category;
    }

    public CustomAdapter(MainActivity activity, ArrayList<String> inc_id, ArrayList<String> inc_name, ArrayList<String> inc_amount, ArrayList<String> inc_date, ArrayList<String> inc_category) {
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.my_row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.inc_id_txt.setText(String.valueOf(inc_id.get(position)));
        holder.inc_name_txt.setText(String.valueOf(inc_name.get(position)));
        holder.inc_amount_txt.setText(String.valueOf(inc_amount.get(position)));
        holder.inc_date_txt.setText(String.valueOf(inc_date.get(position)));
        holder.inc_category_txt.setText(String.valueOf(inc_category.get(position)));

        // Update Income
        holder.mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, UpdateActivity.class);
                intent.putExtra("income_id", String.valueOf(inc_id.get(position)));
                intent.putExtra("income_name", String.valueOf(inc_name.get(position)));
                intent.putExtra("income_amount", String.valueOf(inc_amount.get(position)));
                intent.putExtra("income_date", String.valueOf(inc_date.get(position)));
                intent.putExtra("income_category", String.valueOf(inc_category.get(position)));
                activity.startActivityForResult(intent, 1);
            }
        });
    }

    @Override
    public int getItemCount() {
        return inc_id.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView inc_id_txt, inc_name_txt, inc_amount_txt, inc_date_txt, inc_category_txt;
        LinearLayout mainLayout;    // Update Income

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            inc_id_txt = itemView.findViewById(R.id.inc_id_txt);
            inc_name_txt = itemView.findViewById(R.id.inc_name_txt);
            inc_amount_txt = itemView.findViewById(R.id.inc_amount_txt);
            inc_date_txt = itemView.findViewById(R.id.inc_date_txt);
            inc_category_txt = itemView.findViewById(R.id.inc_category_txt);

            // Update Income
            mainLayout = itemView.findViewById(R.id.mainLayout);
        }
    }
}
