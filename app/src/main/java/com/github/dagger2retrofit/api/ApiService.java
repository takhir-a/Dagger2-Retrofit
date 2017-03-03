package com.github.dagger2retrofit.api;

import com.github.dagger2retrofit.dto.Post;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiService {
    @GET("/posts")
    Call<List<Post>> getPostList();
}