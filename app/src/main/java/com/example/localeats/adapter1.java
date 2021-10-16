package com.example.localeats;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class adapter1 extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    RecyclerView.ViewHolder viewHolder;
    //Adapter.callBack callBackInterface;

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v1 = LayoutInflater.from(parent.getContext()).inflate(R.layout.xyz, parent, false);
        return new Adapter.viewholder(v1);

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof Adapter.viewholder) {
            ((Adapter.viewholder) holder).cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });
        }


    }

    @Override
    public int getItemCount() {
        return 10;
    }


    static class viewholder extends RecyclerView.ViewHolder {

        CardView cardView;

        public viewholder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.cardview);
        }
    }

}
