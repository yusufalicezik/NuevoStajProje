package com.yusufalicezik.nuevoproje;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CustomAdapter extends  RecyclerView.Adapter<CustomAdapter.CustomAdapterViewHolder>  {


    private ArrayList<PhotosResponse> tumPhotos;
    Context mContext;


    public CustomAdapter(ArrayList<PhotosResponse> tumPhotos, Context context)
    {
        this.tumPhotos = tumPhotos;
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

        holder.title.setText(tumPhotos.get(i).getTitle());
        Glide.with(mContext).load(tumPhotos.get(i).getThumbnailUrl())
                .into(holder.imageView);


        //herhangi bir resmim tıklandığında; id sini al ve aktivite 2 ye git. ona göre uygun postu getir.
        holder.allLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id=tumPhotos.get(i).getId();
                Intent intent=new Intent(mContext,SecondActivity.class);
                intent.putExtra("cagirilacakID",id);
                intent.putExtra("resim",tumPhotos.get(i).getUrl());
                mContext.startActivity(intent);


            }
        });




    }



    @Override
    public int getItemCount() {
        return tumPhotos.size();
    }

    public class CustomAdapterViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public ImageView imageView;

        public ConstraintLayout allLayout;

        public ProgressBar progressBar;

        public CustomAdapterViewHolder(View itemView){
            super(itemView);
            title=itemView.findViewById(R.id.customTextGun);
            allLayout=itemView.findViewById(R.id.allLayout);
            imageView=itemView.findViewById(R.id.customImageview);

        }
    }
}