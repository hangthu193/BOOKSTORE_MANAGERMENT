package com.example.book_store.book;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
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
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.book_store.DAO.DaoSach;

import com.example.book_store.Database;
import com.example.book_store.R;
import com.example.book_store.adapter.SelectedAdapter;
import com.example.book_store.model.BookDetailProperty;
import com.example.book_store.model.Selected;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public class Bookdetail extends AppCompatActivity {
    private Database dbHelper;
    private SQLiteDatabase db;
    private DaoSach daosach;
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
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_bookdetail);


        String bookId = getIntent().getStringExtra("BOOK_ID");

        dbHelper = new Database(this);
        db = dbHelper.initDatabase(this, "qlSach.db");
        daosach =new DaoSach(this);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });



        BookDetailProperty book = daosach.getBookDetail(db,bookId);
        TextView masach = findViewById(R.id.bdt_masach);
        TextView tensach = findViewById(R.id.bdt_tensach);
        TextView tacgia = findViewById(R.id.bdt_tacgia);
        TextView theloai = findViewById(R.id.bdt_theloai);
        TextView nxb = findViewById(R.id.bdt_nxb);
        TextView ngayxb = findViewById(R.id.bdt_ngayxb);
        TextView giaban = findViewById(R.id.bdt_giaban);
        TextView soluong = findViewById(R.id.bdt_soluong);
        ImageView img = findViewById(R.id.bdt_img);
            masach.setText("Mã sách: " + book.getMaSach());
            tensach.setText("Tên sách: " + book.getTenSach());
            tacgia.setText("Tác giả: " + book.getTacGia());
            theloai.setText("Thể loại: " + book.getTheLoai());
            nxb.setText("Nhà xuất bản: " + book.getNxb());
            ngayxb.setText("Ngày xuất bản: " + book.getNgayXB());
            giaban.setText("Giá bán: " + book.getGiaBan());
            soluong.setText("Số lượng: " + book.getSoLuong());
        Bitmap bitmap= BitmapFactory.decodeByteArray(book.getAnh(), 0,book.getAnh().length);
        img.setImageBitmap(bitmap);
        Button btnDelete= findViewById(R.id.btn_delete);
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDeleteBook(Gravity.CENTER, daosach.getBookDetail(db,bookId));
            }
        });

//



        ImageButton btn_backdetail = findViewById(R.id.btn_backdetail);
        btn_backdetail.setOnClickListener(v -> {
            Intent myIntent = new Intent(Bookdetail.this, Book.class);
            startActivity(myIntent);
        });

        Button btn_updatebook = findViewById(R.id.btn_update);
        btn_updatebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openUpdateBook(Gravity.CENTER, daosach.getBookDetail(db,bookId));
            }
        });


    }

    private void openDeleteBook(int gravity, BookDetailProperty bookDetailProperty){
        String bookId = getIntent().getStringExtra("BOOK_ID");
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.layout_dialog_deletebook);

        Window window = dialog.getWindow();
        if(window == null){
            return;
        }

        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//
        WindowManager.LayoutParams windowAttributes = window.getAttributes();
        windowAttributes.gravity = gravity;
        window.setAttributes(windowAttributes);

        if(Gravity.CENTER == gravity){
            dialog.setCancelable(true);
        }else{
            dialog.setCancelable(false);
        }

        ImageButton imgbtn_back= dialog.findViewById(R.id.imgbtn_back);
        imgbtn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        Button btn_xacnhan= dialog.findViewById(R.id.btn_xacnhan);
        btn_xacnhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean checkDel= daosach.deleteBook(db,bookId);
                if(checkDel){
                    Toast.makeText(Bookdetail.this, "Xoá sách thành công!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Bookdetail.this, Book.class);
                    startActivity(intent);
                }
                else {
                    Toast.makeText(Bookdetail.this, "Xoá sách không thành công!", Toast.LENGTH_SHORT).show();
                }

            }
        });
        dialog.show();
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
    private boolean validateInputs(EditText tensach, EditText giaban, EditText soluong) {

        String tenSachValue = tensach.getText().toString().trim();
        String giaBanValue = giaban.getText().toString().trim();
        String soLuongValue = soluong.getText().toString().trim();



        if (tenSachValue.isEmpty()) {
            tensach.setError("Tên sách không được để trống");
            tensach.requestFocus();
            return false;
        }

        if (giaBanValue.isEmpty()) {
            giaban.setError("Giá bán không được để trống");
            giaban.requestFocus();
            return false;
        }
        if (soLuongValue.isEmpty()) {
            soluong.setError("Số lượng không được để trống");
            soluong.requestFocus();
            return false;
        }

        int giaBanInt;
        try {
            giaBanInt = Integer.parseInt(giaBanValue);
        } catch (NumberFormatException e) {
            giaban.setError("Giá bán phải là số nguyên");
            giaban.requestFocus();
            return false;
        }

        if (giaBanInt > 100000000) {
            giaban.setError("Giá bán không được vượt quá 100.000.000");
            giaban.requestFocus();
            return false;
        }

        int soLuongInt;
        try {
            soLuongInt = Integer.parseInt(soLuongValue);
        } catch (NumberFormatException e) {
            soluong.setError("Số lượng phải là số nguyên");
            soluong.requestFocus();
            return false;
        }

        if (soLuongInt > 10000) {
            soluong.setError("Số lượng không được vượt quá 10.000");
            soluong.requestFocus();
            return false;
        }

        return true;
    }
    private void openUpdateBook(int gravity, BookDetailProperty bookDetailProperty){
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.layout_dialog_updatebook);

        Window window = dialog.getWindow();
        if(window == null){
            return;
        }

        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//
        WindowManager.LayoutParams windowAttributes = window.getAttributes();
        windowAttributes.gravity = gravity;
        window.setAttributes(windowAttributes);

        if(Gravity.CENTER == gravity){
            dialog.setCancelable(true);
        }else{
            dialog.setCancelable(false);
        }

        TextView masach = dialog.findViewById(R.id.masach);
        EditText tensach = dialog.findViewById(R.id.tensach);
        EditText giaban = dialog.findViewById(R.id.giaban);
        EditText soluong = dialog.findViewById(R.id.soluong);
        EditText ngayxb= dialog.findViewById(R.id.ngayxb);
        btnImg= dialog.findViewById(R.id.btnImg);
        Bitmap bitmap= BitmapFactory.decodeByteArray(bookDetailProperty.getAnh(), 0,bookDetailProperty.getAnh().length);
        btnImg.setImageBitmap(bitmap);
        btnImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Bookdetail.this);
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

        masach.setText(bookDetailProperty.getMaSach());
        tensach.setText(bookDetailProperty.getTenSach());
//        tacgia.setText("Tác giả: " + bookDetailProperty.getTacGia());
//        theloai.setText("Thể loại: " + bookDetailProperty.getTheLoai());
//        nxb.setText("Nhà xuất bản: " + bookDetailProperty.getNxb());
        ngayxb.setText( bookDetailProperty.getNgayXB());
        giaban.setText(bookDetailProperty.getGiaBan().toString());
        soluong.setText(bookDetailProperty.getSoLuong().toString());

        ngayxb.setFocusable(false);
        ngayxb.setClickable(true);
        ngayxb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChonNgay();
            }
        });

        Button btn_updatebook = dialog.findViewById(R.id.btn_capnhattheloai);
        ImageButton imgbtn_back = dialog.findViewById(R.id.imgbtn_back);

        spnTacgia = dialog.findViewById(R.id.tacgia);
        dbHelper=new Database(this);
        daosach =new DaoSach(this);
        List<Selected> authorsList = daosach.getAllAuthors(db);

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
        dbHelper=new Database(this);
        daosach =new DaoSach(this);
        List<Selected> categoryList = daosach.getAllCategories(db);

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
        dbHelper=new Database(this);
        daosach =new DaoSach(this);
        List<Selected> publisherList = daosach.getAllPublishers(db);

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
        imgbtn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });



        btn_updatebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateInputs( tensach, giaban, soluong)) {
                dbHelper = new Database(Bookdetail.this);
                daosach =new DaoSach(Bookdetail.this);
                boolean updateBook= daosach.updateBook(db,tensach.getText().toString(),masach.getText().toString(),maTG,maTL,maNXB,ngayxb.getText().toString(),Integer.parseInt(giaban.getText().toString()),Integer.parseInt(soluong.getText().toString()),ImageButton_To_Byte(btnImg));
                if (updateBook){

                    Toast.makeText(getApplicationContext(), "Cập nhật sách thành công!", Toast.LENGTH_SHORT).show();
                    try {
                        Intent myIntent = new Intent(Bookdetail.this, Bookdetail.class);
                        myIntent.putExtra("BOOK_ID",masach.getText().toString());
                        startActivity(myIntent);
                    }
                    catch (Exception e){

                    }

                }
                else    {
                    Toast.makeText(Bookdetail.this, "Cập nhật sách không thành công!", Toast.LENGTH_SHORT).show();
                }}
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
    public byte[] ImageButton_To_Byte(ImageButton img) {
        BitmapDrawable drawable = (BitmapDrawable) img.getDrawable();
        Bitmap bmp = drawable.getBitmap();
        bmp = getResizedBitmap(bmp, 300); // Giảm kích thước ảnh xuống 500x500 hoặc tùy chỉnh
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
        return stream.toByteArray();
    }
    public Bitmap getResizedBitmap(Bitmap image, int maxSize) {
        int width = image.getWidth();
        int height = image.getHeight();

        float bitmapRatio = (float) width / (float) height;
        if (bitmapRatio > 1) {
            width = maxSize;
            height = (int) (width / bitmapRatio);
        } else {
            height = maxSize;
            width = (int) (height * bitmapRatio);
        }
        return Bitmap.createScaledBitmap(image, width, height, true);
    }

}