package com.yusufalicezik.nuevoproje.Retrofit;

import com.yusufalicezik.nuevoproje.Model.PhotosResponse;
import com.yusufalicezik.nuevoproje.Model.PostsResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RestInterface {
   @GET("photos")
    Call<List<PhotosResponse>> getPhotos();

   @GET("comments")
    Call<List<PostsResponse>> getPosts(@Query("id") int id);
   //baseurl+comments den seçilen id'ye göre olan veriyi getir.


}



