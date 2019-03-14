package com.yusufalicezik.nuevoproje;

import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.yusufalicezik.nuevoproje.Model.PostsResponse;
import com.yusufalicezik.nuevoproje.Retrofit.ApiClient;
import com.yusufalicezik.nuevoproje.Retrofit.RestInterface;


import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SecondActivity extends AppCompatActivity {

    // bi önceki aktivitede listviewdan herhangi bir item tıklandığında buraya id,resim ve title gönderir.
    //o id yi ve resmi alırız. id ye göre o resme ait olan postu buluruz.
    private int cagirilacakID;
    private String gelenResim;
    private String gelenTitle;
    private String gelenKucukResim;
    //

    RestInterface restInterface;
    PostsResponse postsResponse=new PostsResponse();

    private ImageView imageView;//üstteki banner
    private TextView postsText;//body yazısı için
    private ProgressBar progressBar; //resimler yüklenenene kadar gösterilecek
    private TextView titleText;//baslik yazısı
    private TextView eMail; //ekstra eklediğim mail bilgisi için
    private CircleImageView profilResmi; //ekstra eklediğim profil bilgisi için


    private Toolbar mTolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        imageView=findViewById(R.id.siListImage);
        postsText=findViewById(R.id.textViewPosts);
        progressBar=findViewById(R.id.progressBar2Activity);
        titleText =findViewById(R.id.textViewBaslik);
        eMail=findViewById(R.id.paylasanEmail);
        profilResmi=findViewById(R.id.circleImage);

        mTolbar=findViewById(R.id.toolbar);
        setSupportActionBar(mTolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //geri aktiviteye dönebilmesi için
        getSupportActionBar().setTitle("");



        //ilk aktiviteden gelen id ve resimleri çektim.
        cagirilacakID=getIntent().getIntExtra("cagirilacakID",-1); //hangi id ye göre postları getirmeliyim onu aldım.
        gelenResim=getIntent().getStringExtra("resim");
        gelenTitle=getIntent().getStringExtra("title");
        gelenKucukResim=getIntent().getStringExtra("kucukResim");

        restInterface=ApiClient.getClient().create(RestInterface.class);
        Call<List<PostsResponse>> call=restInterface.getPosts(cagirilacakID);
        call.enqueue(new Callback<List<PostsResponse>>() {
            @Override
            public void onResponse(Call<List<PostsResponse>> call, Response<List<PostsResponse>> response) {

                if(response.body().size()>0){
                    //ilk aktivitedeki tıklanan item ın id sini aldım ve ona eşit olan comment in body sini çektim.
                    postsResponse.setBody(response.body().get(0).getBody());
                    postsResponse.setId(response.body().get(0).getId());
                    postsResponse.setEmail(response.body().get(0).getEmail());

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
        //Glide kütüphanesi ile ikinci aktivitedeki imageview ı değiştirdim.
        Glide.with(SecondActivity.this).load(gelenResim)
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
        titleText.setText(gelenTitle);
        eMail.setText(postsResponse.getEmail());
        Glide.with(SecondActivity.this).load(gelenKucukResim).into(profilResmi);



    }


    //detay eklenmesi gerekirse açılır menü;
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_yeniPost: {
                Toast.makeText(this, "Yeni Post Ekleme Aktif Değil", Toast.LENGTH_SHORT).show();

                return true;
            }
            case R.id.item_postuSil: {
                Toast.makeText(this, "Postu Sil Aktif Değil", Toast.LENGTH_SHORT).show();
            }
            case R.id.item_postuGuncelle: {
                Toast.makeText(this, "Postu Güncelleme Aktif Değil", Toast.LENGTH_SHORT).show();
            }

            default: return super.onOptionsItemSelected(item);
        }
    }
}
