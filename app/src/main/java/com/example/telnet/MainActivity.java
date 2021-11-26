package com.example.telnet;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.telnet.Adapters.HomeRecyclerAdapter;
import com.example.telnet.Listeners.OnMovieClickListener;
import com.example.telnet.Listeners.onSearchApiListener;
import com.example.telnet.Models.SearchApiResponse;

public class MainActivity extends AppCompatActivity implements OnMovieClickListener {
    SearchView search_view;
    RecyclerView recycler_view_home;
    HomeRecyclerAdapter adapter;
    RequestManager manager;
    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        search_view = findViewById(R.id.search_view);
        recycler_view_home = findViewById(R.id.recycler_view_home);
        
        dialog = new ProgressDialog(this);
        manager = new RequestManager(this);
        
        search_view.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                dialog.setTitle("Loading...");
                dialog.show();
                manager.searchMovies(listener,query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

    }
    
    private final onSearchApiListener listener = new onSearchApiListener() {
        @Override
        public void onResponse(SearchApiResponse response) {
            dialog.dismiss();
            if(response==null){
                Toast.makeText(MainActivity.this, "OOPS! NO DATA AVAILABLE!", Toast.LENGTH_SHORT).show();
                return;
            }
            showResult(response);
        }

        @Override
        public void onError(String message) {
            dialog.dismiss();
            Toast.makeText(MainActivity.this, "OOPS! AN ERROR OCCURRED!", Toast.LENGTH_SHORT).show();

        }
    };

    private void showResult(SearchApiResponse response) {
            recycler_view_home.setHasFixedSize(true);
            recycler_view_home.setLayoutManager(new GridLayoutManager(MainActivity.this,2));
            adapter = new HomeRecyclerAdapter(this,response.getTitles(),this);
            recycler_view_home.setAdapter(adapter);
    }


    @Override
    public void onMovieClicked(String id) {
       startActivity(new Intent(MainActivity.this,DetailsActivity.class)
       .putExtra("data",id));
    }
}