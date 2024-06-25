package com.example.book_store.book;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.book_store.DataBase;
import com.example.book_store.MainActivity;
import com.example.book_store.R;
import com.example.book_store.adapter.SelectedAdapter;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public class Book extends AppCompatActivity {
    private DataBase dbHelper;
    private SQLiteDatabase db;
    private Spinner spnTacgia;
    private Spinner spnTheLoai;
    private Spinner spnNhaXB;
    private SelectedAdapter selectedAdapter;
    private String maTG;
    private String maTL;
    private String maNXB;
    EditText ngayxb;
    private  ImageButton btnImg;
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int REQUEST_IMAGE_PICK = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ImageButton btn_quaylai2;
        ImageButton btn_themsach;
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_book);
        dbHelper = new DataBase();
        db = dbHelper.initDatabase(this, "qlSach.db");
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        List<BookProperty> booksList = dbHelper.getAllBooks(db);
        populateTable(booksList);


        btn_quaylai2 = findViewById(R.id.btn_quaylai2);
        btn_quaylai2.setOnClickListener(v -> {
            Intent myIntent = new Intent(Book.this, MainActivity.class);
            startActivity(myIntent);
        });

        btn_themsach = findViewById(R.id.btn_themsach);
        btn_themsach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAddbook(Gravity.CENTER, db);
            }
        });

    }
    private void ChonNgay(){
        Calendar calendar = Calendar.getInstance();
        int ngay = calendar.get(Calendar.DATE);
        int thang = calendar.get(Calendar.MONTH);
        int nam = calendar.get(Calendar.YEAR);
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                calendar.set(year, month, day);
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd");
                ngayxb.setText(simpleDateFormat.format(calendar.getTime()));
            }
        }, nam, thang, ngay);
        datePickerDialog.show();
    }

    private void openAddbook(int gravity, SQLiteDatabase db){
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.layout_dialog_addbook);

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
        btnImg= dialog.findViewById(R.id.btnImg);;
        EditText masach = dialog.findViewById(R.id.masach);
        EditText tensach = dialog.findViewById(R.id.tensach);
        EditText giaban = dialog.findViewById(R.id.giaban);
        EditText soluong = dialog.findViewById(R.id.soluong);
        Button btn_addbook = dialog.findViewById(R.id.btn_addbook);
        Button btn_back = dialog.findViewById(R.id.btn_back);

        ngayxb = dialog.findViewById(R.id.ngayxb);
        ngayxb.setFocusable(false);
        ngayxb.setClickable(true);
        ngayxb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChonNgay();
            }
        });
        btnImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Book.this);
                builder.setTitle("Chọn hành động");
                builder.setItems(new CharSequence[]{"Chụp ảnh", "Chọn từ thư viện"}, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0: // Chụp ảnh
                                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
                                }
                                break;
                            case 1: // Chọn từ thư viện
                                Intent pickPhotoIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                                startActivityForResult(pickPhotoIntent, REQUEST_IMAGE_PICK);
                                break;
                        }
                    }
                });
                builder.show();
            }
        });

        spnTacgia = dialog.findViewById(R.id.tacgia);
        dbHelper=new DataBase();
        List<Selected> authorsList = dbHelper.getAllAuthors(db);

        SelectedAdapter adapter = new SelectedAdapter(this, R.layout.item_selected, authorsList);
        spnTacgia.setAdapter(adapter);
        spnTacgia.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Selected author=authorsList.get(position);
              maTG=author.getId();
//                Toast.makeText(Book.this, maTG, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }

        });


        spnTheLoai = dialog.findViewById(R.id.theloai);
        dbHelper=new DataBase();
        List<Selected> categoryList = dbHelper.getAllCategories(db);

        SelectedAdapter adapterCategory = new SelectedAdapter(this, R.layout.item_selected, categoryList);
        spnTheLoai.setAdapter(adapterCategory);
        spnTheLoai.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Selected category=categoryList.get(position);
                maTL=category.getId();
//                Toast.makeText(Book.this, maTL, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        spnNhaXB = dialog.findViewById(R.id.nhaxb);
        dbHelper=new DataBase();
        List<Selected> publisherList = dbHelper.getAllPublishers(db);

        SelectedAdapter adapterPublisher = new SelectedAdapter(this, R.layout.item_selected, publisherList);
        spnNhaXB.setAdapter(adapterPublisher);
        spnNhaXB.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Selected publisher=publisherList.get(position);
                maNXB=publisher.getId();
//                Toast.makeText(Book.this, maNXB, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });


        btn_addbook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbHelper = new DataBase();

                boolean insertBook= dbHelper.insertBook(db,masach.getText().toString(),tensach.getText().toString(),maTG,maTL,maNXB,ngayxb.getText().toString(),Integer.parseInt(giaban.getText().toString()),Integer.parseInt(soluong.getText().toString()),ImageButton_To_Byte(btnImg));
//               = addBook.themVaoCSDL("1","1","1","1","1","1","1");
            if (insertBook){
                Toast.makeText(getApplicationContext(), "Thêm sách thành công!", Toast.LENGTH_SHORT).show();
                Intent myIntent = new Intent(Book.this, Book.class);
                startActivity(myIntent);
            }
            else    {
                Toast.makeText(Book.this, "Thêm sách không thành công!", Toast.LENGTH_SHORT).show();
            }

            }
        });



        dialog.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_IMAGE_CAPTURE && data != null) {
                Bundle extras = data.getExtras();
                Bitmap imageBitmap = (Bitmap) extras.get("data");
                btnImg.setImageBitmap(imageBitmap);
            } else if (requestCode == REQUEST_IMAGE_PICK && data != null) {
                Uri selectedImageUri = data.getData();
                try {
                    Bitmap imageBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImageUri);
                    btnImg.setImageBitmap(imageBitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    public byte[] ImageButton_To_Byte(ImageButton img){
        BitmapDrawable drawable= (BitmapDrawable) img.getDrawable();
        Bitmap bmp=drawable.getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG,100,stream);
        byte[] byteArray=stream.toByteArray();
        return byteArray;
    }
    //tableLayout
    private void populateTable(List<BookProperty> books) {
        TableLayout tableLayout = findViewById(R.id.tbly_book);
        tableLayout.removeAllViews();

        // Add table header
        TableRow headerRow = new TableRow(this);
        headerRow.addView(createTextView("STT",true));
        headerRow.addView(createTextView("Mã sách", true));
        headerRow.addView(createTextView("Tên sách", true));
        headerRow.addView(createTextView("Thao tác", true));
        // Thêm tiêu đề cho các cột khác nếu cần
        tableLayout.addView(headerRow);

        // Add table rows
        for (BookProperty book : books) {
            TableRow row = new TableRow(this);
            row.addView(createTextView(book.getStt(), false));
            row.addView(createTextView(book.getMaSach(), false));
            row.addView(createTextView(book.getTenSach(), false));
            row.addView(createButton("Chi tiết", false, book.getMaSach()));
            tableLayout.addView(row);
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
                    Intent intent = new Intent(Book.this, Bookdetail.class);
                    intent.putExtra("BOOK_ID", id);

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