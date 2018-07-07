package com.example.manik.recybook;

import android.content.Intent;
import android.net.Uri;
import android.support.customtabs.CustomTabsIntent;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "HEREEEEEEEEEEEEEEE";
    private TextView mEmptyStateTextView;
    RecyclerView recyclerView;
    List<BookObject> list;
    View loadingIndicator;

    RecAdap recAdap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.all_books);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        loadingIndicator = findViewById(R.id.loading_spinner);
        loadingIndicator.setVisibility(View.VISIBLE);

        list = new ArrayList<>();

        loadData();


    }


    private void loadData() {

        StringRequest stringRequest = new StringRequest(Request.Method.GET, addSearch(),
                new Response.Listener<String>() {


                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject base = new JSONObject(response);

                            JSONArray items = base.getJSONArray("items");
                            for (int i = 0; i < items.length(); i++) {

                                JSONObject curJ = items.getJSONObject(i);


                                JSONObject volumeInfo = curJ.getJSONObject("volumeInfo");
                                String jTitle = volumeInfo.getString("title");

                                String jDispriction = "";
                                if (volumeInfo.has("description")) {
                                    jDispriction = volumeInfo.getString("description");
                                }

                                int jPages = 0;
                                if (volumeInfo.has("pageCount")) {
                                    jPages = volumeInfo.getInt("pageCount");
                                }

                                double jRating = 0.0;
                                if (volumeInfo.has("averageRating")) {
                                    jRating = volumeInfo.getDouble("averageRating");
                                }
                                String jUrl = "";
                                if (volumeInfo.has("previewLink")) {
                                    jUrl = volumeInfo.getString("previewLink");
                                }

                                String jAuthor = "";
                                if (volumeInfo.has("authors")) {
                                    JSONArray authorArray = volumeInfo.getJSONArray("authors");
                                    jAuthor = authorArray.getString(0);
                                }

                                String jCategories = "";
                                if (volumeInfo.has("categories")) {
                                    JSONArray catArray = volumeInfo.getJSONArray("categories");


                                    if (catArray.length() != 0) {
                                        jCategories = catArray.getString(0);
                                    } else {
                                        jCategories = ".....";
                                    }
                                }


                                String iurl = null;
                                if (volumeInfo.has("imageLinks")) {
                                    JSONObject img = volumeInfo.getJSONObject("imageLinks");
                                    iurl = img.getString("smallThumbnail");
                                }

                                BookObject book = new BookObject(jTitle, jAuthor, jDispriction, jCategories, jUrl, jRating, jPages, iurl);
                                list.add(book);
                            }
                            recAdap = new RecAdap(list, getApplicationContext());
                            recyclerView.setAdapter(recAdap);
                            loadingIndicator.setVisibility(View.GONE);

                            new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
                                @Override
                                public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                                    return false;
                                }

                                @Override
                                public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                                    int book =viewHolder.getAdapterPosition();
                                    List <BookObject> b = recAdap.getMembersEntries();
                                    BookObject currentBook = b.get(book);
                                    String u =  currentBook.getmUrl();
                                    openSite(u);
                                    recAdap.notifyDataSetChanged();
                                }
                            }).attachToRecyclerView(recyclerView);

                        } catch (JSONException e) {
                            Log.e("QueryUtils", "Problem parsing the JSON results", e);
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private String addSearch() {
        Bundle bu = getIntent().getExtras();
        String a = bu.getString("search");
        setTitle(a);
        String b = "https://www.googleapis.com/books/v1/volumes?q=" + a + "&maxResults=40";
        return b;
    }

    public void onBackPressed() {
        Intent intent = new Intent(MainActivity.this, SearchActivity.class);
        finish();
        startActivity(intent);
    }

    public void openSite(String u) {
        Uri url = Uri.parse(u);

        CustomTabsIntent.Builder intentBuilder = new CustomTabsIntent.Builder();
        intentBuilder.setToolbarColor(ContextCompat.getColor(MainActivity.this, R.color.colorPrimary));
        intentBuilder.setSecondaryToolbarColor(ContextCompat.getColor(MainActivity.this, R.color.colorPrimaryDark));
        intentBuilder.setStartAnimations(MainActivity.this, R.anim.slide_in_right, R.anim.slide_out_left);
        intentBuilder.setExitAnimations(MainActivity.this, android.R.anim.slide_in_left,
                android.R.anim.slide_out_right);
        CustomTabsIntent customTabsIntent = intentBuilder.build();

        customTabsIntent.launchUrl(MainActivity.this, url);
    }
}



