package com.example.weddingpackage;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.weddingpackage.modle.User;
import com.google.android.gms.tasks.OnCanceledListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class RegisActivity extends AppCompatActivity {
    private EditText sodt, matkhau, nhaplai, ten;
    private Button regis;
    private ImageButton login;
    private DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
    private DatabaseReference userRef = mDatabase.child("user");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regis);

        /*DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child("user");
        mDatabase.removeValue();*/

        sodt = findViewById(R.id.edt_sdtRegis);
        matkhau = findViewById(R.id.edt_matKhauRegis);
        nhaplai = findViewById(R.id.edt_nhapLaiMatKhauRegis);
        ten = findViewById(R.id.edt_tenRegis);

        regis = findViewById(R.id.btnClickRegis);
        login = findViewById(R.id.btnClick_regisToLogin);

        regis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sdts = sodt.getText().toString().trim();
                String matkhaus = matkhau.getText().toString().trim();
                String nhaplaimatkhaus = nhaplai.getText().toString().trim();
                String tens = ten.getText().toString().trim();

                if (tens.isEmpty()) {
                    ten.setError("Nhập tên");
                    return;
                }

                if (sdts.length() != 10) {
                    sodt.setError("Số điện thoại chỉ có 10 số");
                    return;
                }

                if (matkhaus.length() < 3) {
                    matkhau.setError("Mật khẩu phải có ít nhất 3 ký tự");
                    return;
                }

                if (!nhaplaimatkhaus.equals(matkhaus)) {
                    nhaplai.setError("Nhập đúng mật khẩu ở trên");
                    return;
                }

                Query query = userRef.orderByChild("sodt").equalTo(sdts);
                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            sodt.setError("Số điện thoại này đã được đăng ký");
                        } else {
                            User user = new User(tens, sdts, "10000000", matkhaus, "mo");
                            userRef.push().setValue(user)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {
                                            Toast.makeText(RegisActivity.this, "Tạo tài khoản thành công", Toast.LENGTH_SHORT).show();
                                            finish();
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(RegisActivity.this, "Tạo tài khoản không thành công", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(RegisActivity.this, "Đã xảy ra lỗi", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisActivity.this, LoginActivity.class));
            }
        });
    }
}