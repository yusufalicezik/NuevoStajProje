package com.yusufalicezik.nuevoproje;

import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

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

public class SecondActivity extends AppCompatActivity {

    // bi önceki aktivitede listviewdan herhangi bir item tıklandığında buraya id ve resim gönderir.
    //o id yi ve resmi alırız. id ye göre o resme ait olan postu buluruz.
    private int cagirilacakID;
    private String gelenResim;
    //

    RestInterface restInterface;
    PostsResponse postsResponse=new PostsResponse();

    private ImageView imageView;
    private TextView postsText;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        imageView=findViewById(R.id.imageViewPosts);
        postsText=findViewById(R.id.textViewPosts);
        progressBar=findViewById(R.id.progressBar2Activity);


        cagirilacakID=getIntent().getIntExtra("cagirilacakID",-1); //hangi id ye göre postları getirmeliyim onu aldım.
        gelenResim=getIntent().getStringExtra("resim");

        restInterface=ApiClient.getClient().create(RestInterface.class);
        Call<List<PostsResponse>> call=restInterface.getPosts(cagirilacakID);
        call.enqueue(new Callback<List<PostsResponse>>() {
            @Override
            public void onResponse(Call<List<PostsResponse>> call, Response<List<PostsResponse>> response) {
                Log.e("deneme","basarili");
                Log.e("deneme","url: "+response.toString());

                if(response.body().size()>0){
                    postsResponse.setBody(response.body().get(0).getBody());
                    postsResponse.setId(response.body().get(0).getId());
                }

                tanimlamalariYap();






            }
            @Override
            public void onFailure(Call<List<PostsResponse>> call, Throwable t) {
                Log.e("hata", t.getMessage() + "\n" + t.getLocalizedMessage().toString() + "\n");
            }
        });

    }

    private void tanimlamalariYap() {
        Glide.with(getApplicationContext()).load(gelenResim)
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
                .into(imageView);
        postsText.setText(postsResponse.getBody().toString());



    }
}
