package com.example.book_store;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    EditText inputName, inputPhoneNumber, inputAddress, inputUsername, inputPassword;
    RadioGroup genderRadioGroup;
    Button btnSignup;
    Database dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        inputName = findViewById(R.id.inputName);
        genderRadioGroup = findViewById(R.id.genderRadioGroup);
        inputPhoneNumber = findViewById(R.id.inputPhoneNumber);
        inputAddress = findViewById(R.id.inputAddress);
        inputUsername = findViewById(R.id.inputUsername);
        inputPassword = findViewById(R.id.inputPassword);
        btnSignup = findViewById(R.id.btnSignup);
        dbHelper = new Database(this);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        TextView btn = findViewById(R.id.LoginClick);
        btn.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, LoginActivity.class)));

        findViewById(R.id.btnSignup).setOnClickListener(v -> signup());
    }

    private void signup() {
        String id = dbHelper.generateID();
        String name = inputName.getText().toString();
        String phoneNumber = inputPhoneNumber.getText().toString();
        String address = inputAddress.getText().toString();
        String username = inputUsername.getText().toString();
        String password = inputPassword.getText().toString();

        int selectedGenderId = genderRadioGroup.getCheckedRadioButtonId();
        RadioButton selectedGenderButton = findViewById(selectedGenderId);
        String gender = selectedGenderButton == null ? "" : selectedGenderButton.getText().toString();

        if (isEmpty(name, phoneNumber, address, username, password, gender)) {
            Toast.makeText(this, "Bạn phải điền đầy đủ thông tin để đăng ký!", Toast.LENGTH_SHORT).show();
        } else if (dbHelper.isUsernameTaken(username)) {
            Toast.makeText(this, "Tên đăng nhập đã tồn tại!", Toast.LENGTH_SHORT).show();
        } else if (!isValidPassword(password)) {
            Toast.makeText(this, "Mật khẩu phải có ít nhất 8 ký tự, bao gồm cả chữ hoa, chữ thường và số.", Toast.LENGTH_SHORT).show();
        } else {
            // Đăng ký thành công
            dbHelper.insertEmployee(id, name, gender, phoneNumber, address, username, password);
            Toast.makeText(this, "Đăng ký thành công!", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
            resetFields();
        }
    }

    private boolean isEmpty(String... strings) {
        for (String str : strings) {
            if (str.isEmpty()) {
                return true;
            }
        }
        return false;
    }

    private boolean isValidPassword(String password) {
        // Kiểm tra mật khẩu có ít nhất 8 ký tự, có chữ hoa, chữ thường và số
        String passwordPattern = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{8,}$";
        return password.matches(passwordPattern);
    }

    private void resetFields() {
        inputName.setText("");
        genderRadioGroup.clearCheck();
        inputPhoneNumber.setText("");
        inputAddress.setText("");
        inputUsername.setText("");
        inputPassword.setText("");
    }
}
