package com.example.book_store.category;

import android.app.Dialog;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.book_store.DAO.DaoTheLoai;
import com.example.book_store.DataBase;
import com.example.book_store.MainActivity;
import com.example.book_store.R;
import com.example.book_store.model.Selected;

import java.util.List;

public class Category extends AppCompatActivity {
    private DataBase dbHelper;
    private SQLiteDatabase db;
    private DaoTheLoai daoTheLoai;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ImageButton btn_themtheloai;
        ImageButton btn_quaylai3;

        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_category);

        dbHelper = new DataBase();
        db = dbHelper.initDatabase(this, "qlSach.db");
        daoTheLoai= new DaoTheLoai(this);
        btn_quaylai3 = findViewById(R.id.btn_quaylai3);
        btn_quaylai3.setOnClickListener(v -> {
            Intent myIntent = new Intent(Category.this, MainActivity.class);
            startActivity(myIntent);
        });

        btn_themtheloai = findViewById(R.id.btn_themtheloai);
        btn_themtheloai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAddCategory(Gravity.CENTER, db);
            }
        });

        List<Selected> categoryList = dbHelper.getAllCategories(db);
        populateTable(categoryList);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

    }
    private boolean validateCategoryInputs(EditText matheloai, EditText tentheloai) {
        String maTheLoaiValue = matheloai.getText().toString().trim();
        String tenTheLoaiValue = tentheloai.getText().toString().trim();

        if (maTheLoaiValue.isEmpty()) {
            matheloai.setError("Mã thể loại không được để trống");
            matheloai.requestFocus();
            return false;
        }

        if (tenTheLoaiValue.isEmpty()) {
            tentheloai.setError("Tên thể loại không được để trống");
            tentheloai.requestFocus();
            return false;
        }

        return true;
    }

    private void openAddCategory(int gravity, SQLiteDatabase db){
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.layout_dialog_addcategory);

        Window window = dialog.getWindow();
        if(window == null){
            return;
        }

        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        WindowManager.LayoutParams windowAttributes = window.getAttributes();
        windowAttributes.gravity = gravity;
        window.setAttributes(windowAttributes);

        if(Gravity.CENTER == gravity){
            dialog.setCancelable(true);
        }else{
            dialog.setCancelable(false);
        }

        EditText matheloai = dialog.findViewById(R.id.matheloai);
        EditText tentheloai = dialog.findViewById(R.id.tentheloai);
        Button btn_addtheloai = dialog.findViewById(R.id.btn_addtheloai);
        ImageButton imgbtn_back = dialog.findViewById(R.id.imgbtn_back);
        imgbtn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });


        btn_addtheloai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateCategoryInputs(matheloai,tentheloai)) {
                dbHelper = new DataBase();
                daoTheLoai= new DaoTheLoai(Category.this);
                boolean insertCategory= daoTheLoai.insertCategory(db,matheloai.getText().toString(),tentheloai.getText().toString());
//               = addBook.themVaoCSDL("1","1","1","1","1","1","1");
                if (insertCategory){
                    Toast.makeText(getApplicationContext(), "Thêm thể loại thành công!", Toast.LENGTH_SHORT).show();
                    Intent myIntent = new Intent(Category.this, Category.class);
                    startActivity(myIntent);
                }
                else    {
                    Toast.makeText(Category.this, "Thêm thể loại không thành công!", Toast.LENGTH_SHORT).show();
                }}

            }
        });

        dialog.show();
    }
    private void populateTable(List<Selected> categories) {
        TableLayout tableLayout = findViewById(R.id.tbly_category);
        tableLayout.removeAllViews();

        // Add table header
        TableRow headerRow = new TableRow(this);
        headerRow.addView(createTextView("STT",true));
        headerRow.addView(createTextView("Mã thể loại", true));
        headerRow.addView(createTextView("Tên thể loại", true));
        headerRow.addView(createTextView("Thao tác", true));
        // Thêm tiêu đề cho các cột khác nếu cần
        tableLayout.addView(headerRow);
        int i=1;
        // Add table rows
        for (Selected category : categories) {
            TableRow row = new TableRow(this);
            row.addView(createTextView(Integer.toString(i), false));
            row.addView(createTextView(category.getId(), false));
            row.addView(createTextView(category.getName(), false));
            row.addView(createButton("Chi tiết", false, category.getId()));
            tableLayout.addView(row);
            i++;
        }
    }
    private Button createButton(String text, boolean isHeader,String id) {
        Button btn = new Button(this);
        btn.setText(text);
        btn.setTextColor(Color.parseColor("#0094FF"));
        btn.setBackgroundColor(Color.TRANSPARENT);
        btn.setAllCaps(false);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Category.this, CategoryDetail.class);
                intent.putExtra("CATEGORY_ID", id);
                startActivity(intent);
            }
        });
        return btn;
    }

    private TextView createTextView(String text, boolean isHeader) {

        TextView textView = new TextView(this);
        textView.setText(text);
        textView.setPadding(8, 16, 8, 16);
        textView.setGravity(Gravity.CENTER);
        if (isHeader) {
            textView.setTypeface(null, Typeface.BOLD);
            textView.setBackgroundColor(Color.parseColor("#9BB7FF"));
            textView.setPadding(8, 24, 8, 24);
        }
        return textView;
    }
}