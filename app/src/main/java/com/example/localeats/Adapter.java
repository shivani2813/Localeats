package com.example.localeats;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class Adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    callBack callBackInterface;
    ArrayList<modal>arrayList=new ArrayList<>();
    Adapter(callBack callBackInterface){
        this.callBackInterface=callBackInterface;
    }
    public void setArrayList(ArrayList<modal> arrayList){
        this.arrayList=arrayList;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v1= LayoutInflater.from(parent.getContext()).inflate(R.layout.xyz,parent,false);
        return new viewholder(v1);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        if(holder instanceof viewholder){
            modal obj=arrayList.get(position);

            ((viewholder)holder).textView.setText(obj.getRestruent());
            Picasso.get().load(obj.getImage()).placeholder(R.drawable.placeholder).into(((viewholder) holder).imageView);
            ((viewholder) holder).cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    callBackInterface.cardItemClick(obj);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    static class viewholder extends RecyclerView.ViewHolder{
        TextView textView;
        CardView cardView;
        ImageView imageView;
        public viewholder(@NonNull View itemView) {
            super(itemView);
            cardView=itemView.findViewById(R.id.cardview);
            textView=itemView.findViewById(R.id.textView9);
            imageView=itemView.findViewById(R.id.imageView);
        }
    }
    public interface callBack{
        void cardItemClick(modal menu);
    }
}
