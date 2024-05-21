package com.example.book_store;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    String DB_PATH_SUFFIX = "/databases/";
    SQLiteDatabase database=null;
    String DATABASE_NAME="qlSach.db";
    //Khai báo ListView
    ListView lv;
    ArrayList<TacGia> mylist;
    MyAdapter myadapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lv = (ListView) findViewById(R.id.lv);
        mylist = new ArrayList<>();
        myadapter = new MyAdapter(MainActivity.this, mylist);
        lv.setAdapter(myadapter);
        processCopy();

        database = openOrCreateDatabase("qlSach.db",MODE_PRIVATE, null);

// Truy vấn CSDL và cập nhật hiển thị lên Listview
        Cursor c = database.query("TacGia",null,null,null,null,null,null);
        mylist.clear();
        for(int i = 0; i<c.getCount();i++){
            c.moveToPosition(i);
            String id = c.getString(0);
            String ten = c.getString(1);
            String gioitinh = c.getString(2);
            mylist.add(new TacGia(id, ten, gioitinh));

        }

        myadapter.notifyDataSetChanged();
    }
    private void processCopy() {
//private app
        File dbFile = getDatabasePath(DATABASE_NAME);
        if (!dbFile.exists())
        {
            try{CopyDataBaseFromAsset();
                Toast.makeText(this, "Copying sucess from Assets folder",
                        Toast.LENGTH_LONG).show();
            }
            catch (Exception e){
                Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show();
            }
        }
    }
    private String getDatabasePath() {
        return getApplicationInfo().dataDir + DB_PATH_SUFFIX + DATABASE_NAME;
    }
    public void CopyDataBaseFromAsset(){
        try {
            InputStream myInput;
            myInput = getAssets().open(DATABASE_NAME);
// Path to the just created empty db
            String outFileName = getDatabasePath();
// if the path doesn't exist first, create it
            File f = new File(getApplicationInfo().dataDir + DB_PATH_SUFFIX);
            if (!f.exists())
                f.mkdir();
// Open the empty db as the output stream
            OutputStream myOutput = new FileOutputStream(outFileName);
            int size = myInput.available();
            byte[] buffer = new byte[size];
            myInput.read(buffer);
            myOutput.write(buffer);
// Close the streams
            myOutput.flush();
            myOutput.close();
            myInput.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}