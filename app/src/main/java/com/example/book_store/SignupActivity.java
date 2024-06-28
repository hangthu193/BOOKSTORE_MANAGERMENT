package com.example.book_store;

import android.content.Intent;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class SignupActivity extends AppCompatActivity {

    EditText inputName, inputPhoneNumber, inputAddress, inputUsername, inputPassword;
    RadioGroup genderRadioGroup;
    Button btnSignup;
    Database dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        inputName = findViewById(R.id.inputName);
        genderRadioGroup = findViewById(R.id.genderRadioGroup);
        inputPhoneNumber = findViewById(R.id.inputPhoneNumber);
        inputAddress = findViewById(R.id.inputAddress);
        inputUsername = findViewById(R.id.inputUsername);
        inputPassword = findViewById(R.id.inputPassword);
        btnSignup = findViewById(R.id.btnSignup);
        dbHelper = new Database(this);
        dbHelper.initializeDatabaseFromAssets();

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        TextView btn = findViewById(R.id.LoginClick);
        btn.setOnClickListener(v -> startActivity(new Intent(SignupActivity.this, LoginActivity.class)));

        btnSignup.setOnClickListener(v -> signup());
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

        // Kiểm tra các trường không được để trống
        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(phoneNumber) || TextUtils.isEmpty(address)
                || TextUtils.isEmpty(username) || TextUtils.isEmpty(password) || TextUtils.isEmpty(gender)) {
            Toast.makeText(this, "Vui lòng điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            return;
        }

        // Kiểm tra tên đăng nhập đã tồn tại hay chưa
        if (dbHelper.checkUsernameExist(username)) {
            Toast.makeText(this, "Tên đăng nhập đã tồn tại", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!isValidPassword(password)) {
            Toast.makeText(this, "Mật khẩu phải có ít nhất 6 ký tự, bao gồm cả chữ hoa, chữ thường và số.", Toast.LENGTH_SHORT).show();
        } else {
            // Đăng ký người dùng
            if (dbHelper.addUser(id, name, gender, phoneNumber, address, username, password)) {
                Toast.makeText(this, "Đăng ký thành công", Toast.LENGTH_SHORT).show();
                // Chuyển sang màn hình đăng nhập
                startActivity(new Intent(SignupActivity.this, LoginActivity.class));
                resetFields();
            } else {
                Toast.makeText(this, "Đăng ký thất bại", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private boolean isValidPassword(String password) {
        // Kiểm tra mật khẩu có ít nhất 6 ký tự, có chữ hoa, chữ thường và số
        String passwordPattern = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{6,}$";
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
