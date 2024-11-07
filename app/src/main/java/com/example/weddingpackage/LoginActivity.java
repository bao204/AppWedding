/*
package com.example.weddingpackage;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.weddingpackage.admin.AdminActivity;

import com.example.weddingpackage.modle.Admin;
import com.example.weddingpackage.modle.User;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.tasks.Task;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.logging.Logger;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.weddingpackage.admin.AdminActivity;

import com.example.weddingpackage.modle.Admin;
import com.example.weddingpackage.modle.User;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;
import java.util.logging.Logger;

import com.google.android.gms.tasks.Task;

public class LoginActivity extends AppCompatActivity {
    TextView tvClick_quenMatKhau;
    EditText edt_soDienThoai, edt_matKhau;
    Button btnClickLogin_login, btnClick_resgis;
    DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();

    DatabaseReference userRef = mDatabase.child("user");
    DatabaseReference adminRef = mDatabase.child("admin");
    FirebaseAuth auth;
    GoogleSignInClient googleSignInClient;
    ShapeableImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        tvClick_quenMatKhau = (TextView) findViewById(R.id.tvClick_quenMatKhau);
        edt_soDienThoai = (EditText) findViewById(R.id.edt_sdtLogin);
        edt_matKhau = (EditText) findViewById(R.id.edt_matKhauLogin);
        imageView = findViewById(R.id.profileImage);

        FirebaseApp.initializeApp(this);
        imageView = findViewById(R.id.profileImage);

        btnClickLogin_login = (Button) findViewById(R.id.btnClickLogin_login);
        btnClick_resgis = findViewById(R.id.btnClick_regis);


        KiemTraDinhDanhEditText();
        btnClickLogin_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkTaiKhoanExist();
            }
        });

        btnClick_resgis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RegisActivity.class));
            }
        });
        tvClick_quenMatKhau.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RegisActivity.class));
            }
        });

        GoogleSignInOptions options = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.client_id))
                .requestEmail()
                .build();
        googleSignInClient = GoogleSignIn.getClient(LoginActivity.this, options);

        auth = FirebaseAuth.getInstance();

        SignInButton signInButton = findViewById(R.id.signIn);
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = googleSignInClient.getSignInIntent();
                activityResultLauncher.launch(intent);
            }
        });
//        MaterialButton signOut = findViewById(R.id.signout);
//        signOut.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                FirebaseAuth.getInstance().addAuthStateListener(new FirebaseAuth.AuthStateListener() {
//                    @Override
//                    public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
//                        if (firebaseAuth.getCurrentUser() == null) {
//                            googleSignInClient.signOut().addOnSuccessListener(new OnSuccessListener<Void>() {
//                                @Override
//                                public void onSuccess(Void unused) {
//                                    Toast.makeText(LoginActivity.this, "Signed out successfully", Toast.LENGTH_SHORT).show();
//                                    startActivity(new Intent(LoginActivity.this, MainHomeActivity.class));
//                                }
//                            });
//                        }
//                    }
//                });
//                FirebaseAuth.getInstance().signOut();
//            }
//        });
//        if(auth.getCurrentUser() != null) {
//            Glide.with(LoginActivity.this).load(Objects.requireNonNull(auth.getCurrentUser()).getPhotoUrl()).into(imageView);
//            name.setText(auth.getCurrentUser().getDisplayName());
//            mail.setText(auth.getCurrentUser().getEmail());
//        }

    }
    private final ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
            if (result.getResultCode() == RESULT_OK) {
                Task<GoogleSignInAccount> accountTask = GoogleSignIn.getSignedInAccountFromIntent(result.getData());
                try {
                    GoogleSignInAccount signInAccount = accountTask.getResult(ApiException.class);
                    AuthCredential authCredential = GoogleAuthProvider.getCredential(signInAccount.getIdToken(), null);
                    auth.signInWithCredential(authCredential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                auth = FirebaseAuth.getInstance();
                                Glide.with(LoginActivity.this).load(Objects.requireNonNull(auth.getCurrentUser()).getPhotoUrl()).into(imageView);
//                                name.setText(auth.getCurrentUser().getDisplayName());
//                                mail.setText(auth.getCurrentUser().getEmail());
                                Intent intent = new Intent(LoginActivity.this, MainHomeActivity.class);
                                Bundle bundle = new Bundle();
                                Toast.makeText(LoginActivity.this, "Signed in successfully!", Toast.LENGTH_SHORT).show();
                                intent.putExtras(bundle);
                                startActivity(intent);
                            } else {
                                Toast.makeText(LoginActivity.this, "Failed to sign in: " + task.getException(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                } catch (ApiException e) {
                    e.printStackTrace();
                }
            }
        }
    });
    void checkTaiKhoanExist() {
        String sodtI = edt_soDienThoai.getText().toString();
        String mkI = edt_matKhau.getText().toString();

        adminRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    Admin admin = snapshot.getValue(Admin.class);
                    if (admin.getSodt().equals(sodtI) && admin.getMatkhau().equals(mkI)) {
                        startActivity(new Intent(LoginActivity.this, AdminActivity.class));
                    }
                    else {
                        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                if (snapshot.exists()) {
                                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                        User user = dataSnapshot.getValue(User.class);
                                        String userKey = dataSnapshot.getKey();

                                        if (user.getSodt().equals(sodtI) && user.getMatkhau().equals(mkI)) {
                                            if (!user.getTrangthai().equals("mo")) {
                                                edt_soDienThoai.setText("Số điện thoại đã bị khóa");

                                            } else {
                                                Toast.makeText(LoginActivity.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                                                Intent intent = new Intent(LoginActivity.this, MainHomeActivity.class);
                                                Bundle bundle = new Bundle();
                                                bundle.putSerializable("user", user);
                                                bundle.putString("userKey", userKey);
                                                intent.putExtras(bundle);
                                                startActivity(intent);
                                            }
                                        } else if (user.getSodt().equals(sodtI) && !user.getMatkhau().equals(mkI)) {
                                            edt_matKhau.setError("Sai mật khẩu");

                                        } else {
                                            edt_soDienThoai.setError("Số điện thoại chưa đăng ký");

                                        }
                                    }
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });
    }
    void KiemTraDinhDanhEditText() {

        edt_soDienThoai.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String phone = s.toString();
                if (phone.trim().length() < 10) {
                    edt_soDienThoai.setError("Độ dài số điện thoại là 10 số");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

//        edt_matKhau.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                String mk = s.toString();
//                if (mk.trim().length() < 8) {
//                    textNotifyPass.setText("Độ dài mật khẩu tối thiểu là 8");
//                    textNotifyPass.setTextColor(Color.RED);
//                }
//
//                unLockLoginBtn();
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//                String mk = s.toString();
//                if (mk.trim().length() >= 8) {
//                    textNotifyPass.setText("Mật khẩu");
//                    textNotifyPass.setTextColor(Color.WHITE);
//                }
//            }
//        });
    }
}*/
package com.example.weddingpackage;

import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.weddingpackage.admin.AdminActivity;

import com.example.weddingpackage.modle.Admin;
import com.example.weddingpackage.modle.User;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.tasks.Task;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.logging.Logger;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.weddingpackage.admin.AdminActivity;

import com.example.weddingpackage.modle.Admin;
import com.example.weddingpackage.modle.User;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;
import java.util.logging.Logger;

import com.google.android.gms.tasks.Task;

public class LoginActivity extends AppCompatActivity implements  InternetNotify.InternetNotifyListener {
    AlertDialog dialog;
    InternetNotify internetNotify = new InternetNotify(this);
    EditText edt_soDienThoai, edt_matKhau;
    Button btnClickLogin_login, btnClick_resgis;
    DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();

    DatabaseReference userRef = mDatabase.child("user");
    DatabaseReference adminRef = mDatabase.child("admin");
    FirebaseAuth auth;
    GoogleSignInClient googleSignInClient;
    ShapeableImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edt_soDienThoai = (EditText) findViewById(R.id.edt_sdtLogin);
        edt_matKhau = (EditText) findViewById(R.id.edt_matKhauLogin);
        imageView = findViewById(R.id.profileImage);

        FirebaseApp.initializeApp(this);
        imageView = findViewById(R.id.profileImage);

        btnClickLogin_login = (Button) findViewById(R.id.btnClickLogin_login);
        btnClick_resgis = findViewById(R.id.btnClick_regis);


        KiemTraDinhDanhEditText();
        btnClickLogin_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkTaiKhoanExist();
            }
        });

        btnClick_resgis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RegisActivity.class));
            }
        });

        GoogleSignInOptions options = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.client_id))
                .requestEmail()
                .build();
        googleSignInClient = GoogleSignIn.getClient(LoginActivity.this, options);

        auth = FirebaseAuth.getInstance();

        SignInButton signInButton = findViewById(R.id.signIn);
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = googleSignInClient.getSignInIntent();
                activityResultLauncher.launch(intent);
            }
        });
//        MaterialButton signOut = findViewById(R.id.signout);
//        signOut.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                FirebaseAuth.getInstance().addAuthStateListener(new FirebaseAuth.AuthStateListener() {
//                    @Override
//                    public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
//                        if (firebaseAuth.getCurrentUser() == null) {
//                            googleSignInClient.signOut().addOnSuccessListener(new OnSuccessListener<Void>() {
//                                @Override
//                                public void onSuccess(Void unused) {
//                                    Toast.makeText(LoginActivity.this, "Signed out successfully", Toast.LENGTH_SHORT).show();
//                                    startActivity(new Intent(LoginActivity.this, MainHomeActivity.class));
//                                }
//                            });
//                        }
//                    }
//                });
//                FirebaseAuth.getInstance().signOut();
//            }
//        });
//        if(auth.getCurrentUser() != null) {
//            Glide.with(LoginActivity.this).load(Objects.requireNonNull(auth.getCurrentUser()).getPhotoUrl()).into(imageView);
//            name.setText(auth.getCurrentUser().getDisplayName());
//            mail.setText(auth.getCurrentUser().getEmail());
//        }

    }
    private final ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
            if (result.getResultCode() == RESULT_OK) {
                Task<GoogleSignInAccount> accountTask = GoogleSignIn.getSignedInAccountFromIntent(result.getData());
                try {
                    GoogleSignInAccount signInAccount = accountTask.getResult(ApiException.class);
                    AuthCredential authCredential = GoogleAuthProvider.getCredential(signInAccount.getIdToken(), null);
                    auth.signInWithCredential(authCredential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                auth = FirebaseAuth.getInstance();
                                Glide.with(LoginActivity.this).load(Objects.requireNonNull(auth.getCurrentUser()).getPhotoUrl()).into(imageView);
//                                name.setText(auth.getCurrentUser().getDisplayName());
//                                mail.setText(auth.getCurrentUser().getEmail());
                                Intent intent = new Intent(LoginActivity.this, MainHomeActivity.class);
                                Toast.makeText(LoginActivity.this, "Signed in successfully!", Toast.LENGTH_SHORT).show();
                                startActivity(intent);
                            } else {
                                Toast.makeText(LoginActivity.this, "Failed to sign in: " + task.getException(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                } catch (ApiException e) {
                    e.printStackTrace();
                }
            }
        }
    });
    void checkTaiKhoanExist() {
        String sodtI = edt_soDienThoai.getText().toString();
        String mkI = edt_matKhau.getText().toString();

        adminRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    Admin admin = snapshot.getValue(Admin.class);
                    if (admin.getSodt().equals(sodtI) && admin.getMatkhau().equals(mkI)) {
                        startActivity(new Intent(LoginActivity.this, AdminActivity.class));
                    }
                    else {
                        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                if (snapshot.exists()) {
                                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                        User user = dataSnapshot.getValue(User.class);
                                        String userKey = dataSnapshot.getKey();

                                        if (user.getSodt().equals(sodtI) && user.getMatkhau().equals(mkI)) {
                                            if (!user.getTrangthai().equals("mo")) {
                                                edt_soDienThoai.setText("Số điện thoại đã bị khóa");

                                            } else {
                                                Toast.makeText(LoginActivity.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                                                Intent intent = new Intent(LoginActivity.this, MainHomeActivity.class);
                                                Bundle bundle = new Bundle();
                                                if(sodtI == null && mkI == null || user.getSodt().isEmpty() || user.getMatkhau().isEmpty()){
                                                    return;
                                                }
                                                bundle.putSerializable("user", user);
                                                bundle.putString("userKey", userKey);
                                                intent.putExtras(bundle);
                                                startActivity(intent);
                                            }
                                        } else if (user.getSodt().equals(sodtI) && !user.getMatkhau().equals(mkI)) {
                                            edt_matKhau.setError("Sai mật khẩu");

                                        } else {
                                            edt_soDienThoai.setError("Số điện thoại chưa đăng ký");

                                        }
                                    }
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });
    }
    void KiemTraDinhDanhEditText() {

        edt_soDienThoai.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String phone = s.toString();
                if (phone.trim().length() < 10) {
                    edt_soDienThoai.setError("Độ dài số điện thoại là 10 số");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

//        edt_matKhau.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                String mk = s.toString();
//                if (mk.trim().length() < 8) {
//                    textNotifyPass.setText("Độ dài mật khẩu tối thiểu là 8");
//                    textNotifyPass.setTextColor(Color.RED);
//                }
//
//                unLockLoginBtn();
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//                String mk = s.toString();
//                if (mk.trim().length() >= 8) {
//                    textNotifyPass.setText("Mật khẩu");
//                    textNotifyPass.setTextColor(Color.WHITE);
//                }
//            }
//        });
    }
    @Override
    protected void onStart() {
        super.onStart();
        registerReceiver(internetNotify, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
    }
    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(internetNotify);
    }
    @Override
    public void onNetworkChanged(boolean isConnected) {
        if (isConnected) {
            if (dialog != null && dialog.isShowing()) {
                dialog.dismiss();
                dialog = null;
            }
        } else {
            if (dialog == null || !dialog.isShowing()) {
                ShowDialog();
            }
        }
    }
    private void ShowDialog() {

        dialog = new AlertDialog.Builder(LoginActivity.this)
                .setView(R.layout.internet_dialog_layout)
                .setCancelable(false)
                .create();
        dialog.show();

        TextView playButton = dialog.findViewById(R.id.tvOK);

        playButton.setOnClickListener(view->{

            Toast.makeText(this, "Vui Lòng Kết Nối Wifi", Toast.LENGTH_SHORT).show();
        });
    }
}