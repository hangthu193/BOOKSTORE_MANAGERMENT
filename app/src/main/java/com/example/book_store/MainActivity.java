package com.example.book_store;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.example.book_store.dao.DaoTacGia;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private static final int REQUEST_CODE_ADD = 1;
    private ActivityResultLauncher<Intent> addActivityResultLauncher;

    DaoTacGia daoTacGia;
    ListView lv;
    ArrayList<TacGia> mylist;
    MyAdapter myadapter;

    Button btnthem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize DAO for database operations
        daoTacGia = new DaoTacGia(this);

        // Initialize ListView and adapter
        lv = findViewById(R.id.lvkho);
        mylist = new ArrayList<>();
        myadapter = new MyAdapter(MainActivity.this, mylist);
        lv.setAdapter(myadapter);

        // Button to open AddActivity
        btnthem = findViewById(R.id.btnthemkho);
        btnthem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddActivity.class);
                addActivityResultLauncher.launch(intent);
            }
        });

        // Load data from database and update ListView
        loadData();

        // Register ActivityResultLauncher for handling AddActivity result
        addActivityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK) {
                        // Reload data if AddActivity returns RESULT_OK
                        loadData();
                    }
                }
        );

        // ListView item click listener to handle updates (if needed)
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TacGia selectedTacGia = mylist.get(position);
                openUpdateActivity(selectedTacGia.getMaTG());
            }
        });
    }

    private void loadData() {
        mylist.clear();
        mylist.addAll(daoTacGia.getAllTacGia());
        myadapter.notifyDataSetChanged();
    }

    private void openUpdateActivity(String MaTG) {
        Intent intent = new Intent(MainActivity.this, updateAtivity.class);
        intent.putExtra("MaTG", MaTG);
        startActivity(intent);
    }
}
