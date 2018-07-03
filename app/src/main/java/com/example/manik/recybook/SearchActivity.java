package com.example.manik.recybook;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

public class SearchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
    }
    public void bunSearch(View view) {
        EditText ed1 = findViewById(R.id.editText);
        String p1 = ed1.getText().toString().trim();
//        if (ed1.getText().toString().isEmpty()) {
//            ed1.setText("player");
//        }

        if (TextUtils.isEmpty(p1)) {
            ed1.setError("Enter!!!");
        } else{
            Intent i = new Intent(SearchActivity.this, MainActivity.class);
            i.putExtra("search", p1);
            finish();
            startActivity(i);
        }




    }
}
