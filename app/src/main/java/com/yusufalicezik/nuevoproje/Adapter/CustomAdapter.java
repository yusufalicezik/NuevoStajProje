package com.yusufalicezik.nuevoproje.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yusufalicezik.nuevoproje.Model.PhotosResponse;
import com.yusufalicezik.nuevoproje.R;
import com.yusufalicezik.nuevoproje.SecondActivity;

import java.util.ArrayList;

public class CustomAdapter extends  RecyclerView.Adapter<CustomAdapter.CustomAdapterViewHolder>  {


    private ArrayList<PhotosResponse> tumFotolar;
    Context mContext;


    public CustomAdapter(ArrayList<PhotosResponse> tumFotolar, Context context)
    {
        this.tumFotolar = tumFotolar;
        this.mContext=context;
    }



    @NonNull
    @Override
    public CustomAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {


        View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_list,parent,false);
        return new CustomAdapterViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull final CustomAdapterViewHolder holder, final int i) {

        holder.title.setText(tumFotolar.get(i).getTitle());
        Glide.with(mContext).load(tumFotolar.get(i).getThumbnailUrl())
                .into(holder.imageView);


        //herhangi bir resmim tıklandığında; id sini al ve aktivite 2 ye git. ona göre uygun postu getir.
        holder.allLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id= tumFotolar.get(i).getId();
                Intent intent=new Intent(mContext,SecondActivity.class);
                intent.putExtra("cagirilacakID",id);
                intent.putExtra("resim", tumFotolar.get(i).getUrl());
                intent.putExtra("title", tumFotolar.get(i).getTitle());
                intent.putExtra("kucukResim", tumFotolar.get(i).getThumbnailUrl());
                mContext.startActivity(intent);


            }
        });




    }



    @Override
    public int getItemCount() {
        return tumFotolar.size();
    }

    public class CustomAdapterViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public ImageView imageView;

        public ConstraintLayout allLayout;

        public ProgressBar progressBar;

        public CustomAdapterViewHolder(View itemView){
            super(itemView);
            title=itemView.findViewById(R.id.customTextTitle);
            allLayout=itemView.findViewById(R.id.allLayout);
            imageView=itemView.findViewById(R.id.customImageview);

            //allLayout u almamın sebebi listede item tıklandığında resme ya da yazıya değil, listview daki
            //bir cell in herhangi bir yerine tıklanınca ikinci aktiviteye geçmesini istemem.
            //aksi halde hem resim için hem de yazı için ayrı ayrı onclick vermem gerekebilirdi.

        }
    }
}