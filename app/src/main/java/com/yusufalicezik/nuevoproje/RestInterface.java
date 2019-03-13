package com.yusufalicezik.nuevoproje;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RestInterface {
   @GET("photos")
    Call<List<PhotosResponse>> getPhotos();

   @GET("comments")
    Call<List<PostsResponse>> getPosts(@Query("id") int postId);


}



