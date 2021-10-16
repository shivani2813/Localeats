package com.example.localeats;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MenuAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    MenuAdapter.callBack callBackInterface;
    ArrayList<additem> arrayList = new ArrayList<>();

    MenuAdapter(Adapter.callBack callBackInterface) {
        this.callBackInterface = (callBack) callBackInterface;
    }

    public void setArrayList(ArrayList<additem> arrayList) {
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v1 = LayoutInflater.from(parent.getContext()).inflate(R.layout.samplelayout, parent, false);
        return new viewholder(v1);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        viewholder obk = (viewholder) holder;

        obk.textView1.setText(arrayList.get(position).getName());
        obk.textView3.setText(arrayList.get(position).getDescription());
        obk.textView2.setText(arrayList.get(position).getPrice());
        obk.textView4.setText("" + arrayList.get(position).getRating());
        obk.textView.setText(arrayList.get(position).getaddBtn());
        if(arrayList.get(position).getImageURL()!=null) {
            Log.e("ima url:",arrayList.get(position).getImageURL());
            Picasso.get().load(arrayList.get(position).getImageURL()).placeholder(R.drawable.placeholder).into(obk.imageView2);
        }
        else{
            Log.e("image :","not found");
        }
        obk.add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (arrayList.get(position).getAddTAg() == null) {
                    arrayList.get(position).setAddTAg("Remove");
                    obk.add.setText("Remove");
                } else {
                    arrayList.get(position).setAddTAg("Add");
                    obk.add.setText("Add");
                }
                callBackInterface.addBtnClick(arrayList.get(position).getAddTAg());
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    static class viewholder extends RecyclerView.ViewHolder {

        Button add;
        CardView cardView;
        TextView textView;
        ImageView imageView;
        ImageView imageView1;
        ImageView imageView2, imageView3;
        TextView textView1, textView2, textView3, textView4;

        public viewholder(@NonNull View itemView) {
            super(itemView);
            add = itemView.findViewById(R.id.add);
            cardView = itemView.findViewById(R.id.cardview);
            textView = itemView.findViewById(R.id.add);
            imageView = itemView.findViewById(R.id.minus);
            imageView1 = itemView.findViewById(R.id.plus);
            imageView2 = itemView.findViewById(R.id.imageView);
            textView1 = itemView.findViewById(R.id.textvi);
            textView2 = itemView.findViewById(R.id.tview);
            textView3 = itemView.findViewById(R.id.textvi2);
            imageView3 = itemView.findViewById(R.id.star);
            textView4 = itemView.findViewById(R.id.Rating);



        }
    }

    public interface callBack {
        void cardItemClick(additem menu);

        void addBtnClick(String tag);
    }

}

