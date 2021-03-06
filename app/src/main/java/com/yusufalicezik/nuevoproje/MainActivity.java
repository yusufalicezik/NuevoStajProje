package com.yusufalicezik.nuevoproje;

import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.yusufalicezik.nuevoproje.Adapter.CustomAdapter;
import com.yusufalicezik.nuevoproje.Model.PhotosResponse;
import com.yusufalicezik.nuevoproje.Retrofit.ApiClient;
import com.yusufalicezik.nuevoproje.Retrofit.RestInterface;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    RestInterface restInterface;
    RecyclerView liste;
    ArrayList<PhotosResponse> tumFotolar=new ArrayList<>();
    CustomAdapter adapter;
    ImageView firstPhotoBanner;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        liste=findViewById(R.id.listem);
        firstPhotoBanner =findViewById(R.id.imageView);
        progressBar=findViewById(R.id.progressBar);

        restInterface=ApiClient.getClient().create(RestInterface.class);
        Call<List<PhotosResponse>> call=restInterface.getPhotos();
        call.enqueue(new Callback<List<PhotosResponse>>() {
           @Override
           public void onResponse(Call<List<PhotosResponse>> call, Response<List<PhotosResponse>> response) {
              // Log.e("deneme","basarili");
               // Log.e("deneme","url: "+response.toString());


               //verileri çektim, modelime atadım ve tek tek arrayliste ekledim.
               for(int i=0;i<response.body().size();i++){
                   PhotosResponse p=new PhotosResponse();
                   p.setAlbumId(response.body().get(i).getAlbumId());
                   p.setId(response.body().get(i).getId());
                   p.setThumbnailUrl(response.body().get(i).getThumbnailUrl());
                   p.setTitle(response.body().get(i).getTitle());
                   p.setUrl(response.body().get(i).getUrl());
                   tumFotolar.add(p);
               }

               //listview da kullanılacak olan arraylistime tüm verileri ekledim ve adapter ımı atamak için metodu çağırdım.
               adapterTanimla();



           }
           @Override
           public void onFailure(Call<List<PhotosResponse>> call, Throwable t) {
               Log.e("hata", t.getMessage() + "\n" + t.getLocalizedMessage() + "\n");
           }
       });



    }

    private void adapterTanimla() {
        //İlk fotoğraf için(üstteki banner için)---/// first Index e göre.

        Glide.with(MainActivity.this).load(tumFotolar.get(0).getUrl())
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        progressBar.setVisibility(View.GONE);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        progressBar.setVisibility(View.GONE);
                        return false;
                    }
                })
                .into(firstPhotoBanner);
        //---------///



        adapter=new CustomAdapter(tumFotolar,MainActivity.this);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(MainActivity.this);
        linearLayoutManager.setStackFromEnd(false);
        liste.setHasFixedSize(true);
        liste.setLayoutManager(linearLayoutManager);
        liste.setAdapter(adapter);
    }
}
