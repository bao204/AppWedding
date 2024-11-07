
package com.example.weddingpackage;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.weddingpackage.modle.User;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class ChangePassActivity extends AppCompatActivity {
    TextView tvPW, tvNewPW, tvPWAgain, tvError;
    EditText edtPW, edtNewPW, edtPWAgain;
    Button btnUpdate;
    Toolbar toolbar;
    DatabaseReference  ref = FirebaseDatabase.getInstance().getReference("user");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_pass);

        toolbar = findViewById(R.id.toolbar_changepass);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Đổi mật khẩu");

        tvPW = (TextView) findViewById(R.id.tvPW);
        tvError = (TextView) findViewById(R.id.tvError);
        tvNewPW = (TextView) findViewById(R.id.tvNewPW);
        tvPWAgain = (TextView) findViewById(R.id.tvPWAgain);

        edtPW = (EditText) findViewById(R.id.edtPass_old);
        edtNewPW = (EditText) findViewById(R.id.edtPass_new);
        edtPWAgain = (EditText) findViewById(R.id.edtPass_newAgain);

        btnUpdate = (Button) findViewById(R.id.btnUpdate);

        btnUpdate.setOnClickListener(v ->{
            Change();
        });

    }
    public void Change(){
        Bundle i = getIntent().getExtras();
        User user = (User) i.get("user");
        String userKey = i.getString("userKey");

        String passOld = edtPW.getText().toString().trim();
        String passNew = edtNewPW.getText().toString().trim();
        String passNewAgain = edtPWAgain.getText().toString().trim();

        if(!user.getMatkhau().equals(passOld)){
            edtPW.setError("Mật khẩu cũ sai!");
        }
        else {
            if(passNew.trim().length() < 3){
                edtNewPW.setError("Dài hơn tí");
            }
            else{
                if(!passNewAgain.equals(passNew)){
                    edtPWAgain.setError("Nhập lại mật khẩu ở trên");
                }
                else{
                    FirebaseDatabase.getInstance().getReference().child("user");

                    user.setMatkhau(passNew);
                    ref.child(userKey).setValue(user)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Toast.makeText(ChangePassActivity.this, "Đổi mật khẩu thành công", Toast.LENGTH_SHORT).show();
                                    edtPW.setText("");
                                    edtNewPW.setText("");
                                    edtPWAgain.setText("");
                                }
                            });
                }
            }
        }
    }
    public void iconBack_click(View view){
        finish();
    }
}
