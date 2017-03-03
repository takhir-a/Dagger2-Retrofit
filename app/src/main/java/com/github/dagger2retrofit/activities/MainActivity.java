package com.github.dagger2retrofit.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.github.dagger2retrofit.R;
import com.github.dagger2retrofit.api.ApiService;
import com.github.dagger2retrofit.dependencyInjection.DaggerNetComponent;
import com.github.dagger2retrofit.dependencyInjection.NetModule;
import com.github.dagger2retrofit.dto.Post;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {

    @Inject
    Retrofit retrofit;

    ListView listView;
    ArrayList<String> list;
    ArrayAdapter<String> adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView) findViewById(R.id.my_list);
        list = new ArrayList<>();

        injector(this);
        getPostListFromApi();
    }

    //This is a injector method, as a standard architecture this method should be in Application class
    //but I coding this method in this activity for easy understanding!
    public void injector(MainActivity mainActivity){
        DaggerNetComponent.builder()
                .netModule(new NetModule())
                .build().inject(mainActivity);
    }

    public void getPostListFromApi(){
        retrofit.create(ApiService.class).getPostList().enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                if (response.body()!=null){
                    showPosts(response.body());
                }else {
                    showError("Error");
                }
            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {
                showError("Error");
            }
        });
    }

    public void showPosts(List<Post> posts) {
        for (int i = 0; i < posts.size(); i++) {
            list.add(posts.get(i).getTitle());
        }
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, list);
        listView.setAdapter(adapter);
    }

    public void showError(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }
}
