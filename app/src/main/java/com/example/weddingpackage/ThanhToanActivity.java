package com.example.weddingpackage;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.weddingpackage.adapter.CookAdapter;
import com.example.weddingpackage.adapter.DichVuAdapter;
import com.example.weddingpackage.modle.ChiTietDonDatTiec;
import com.example.weddingpackage.modle.Cook;
import com.example.weddingpackage.modle.DichVu;
import com.example.weddingpackage.modle.User;
import com.example.weddingpackage.modle.datTiec;
import com.example.weddingpackage.modle.datTiec_Cook;
import com.example.weddingpackage.modle.datTiec_Lobby;
import com.example.weddingpackage.modle.datTiec_dichVu;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import vn.momo.momo_partner.AppMoMoLib;
import vn.momo.momo_partner.MoMoParameterNameMap;

public class ThanhToanActivity extends AppCompatActivity {

    TextView ten, sodt, soluongbandat, ngaytochuc, gio, thoigianthue, tensanh, giasanh, giaban, diachisanh, clickToDetailService, soLuongService, tongGiaCacDichVu,
            toDetailLobby, soLuongKhaiVi, soLuongMonChinh, soLuongTrangMieng, soLuongNuocUong, tongGiaCacMonAn, tongtienthanhtoanTanhToanActivity,
            clickToDetailCook, clickPayThanhToan;
    double giaService = 0;
    double giaCook = 0;
    double giaLobby = 0;
    double tonggia = 0;
    ArrayList<datTiec_Cook> datTiec_Cook = new ArrayList<>();
    ArrayList<datTiec_dichVu> datTiec_dichVu = new ArrayList<>();
    ArrayList<DichVu> listServiceSelected = new ArrayList<>();
    ArrayList<Cook> listCook = new ArrayList<>();
    datTiec_Lobby datTiecLobby1;
    User user;
    datTiec datTiec1;
    Toolbar toolbar;
    String keyuser = "";
    User user1;


    Button btnMoMo;
    ImageView qrcode;

    //////////////////////////

    private String amount = "10000";
    private String fee = "0";
    int environment = 0;//developer default
    private String merchantName = "Demo SDK";
    private String merchantCode = "123432112332";
    private String merchantNameLabel = "Nhà cung cấp";
    private String description = "Thanh toán dịch vụ ABC";

    //////////////////////////

    DatabaseReference ref = FirebaseDatabase.getInstance().getReference("user");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thanh_toan);

        toolbar = findViewById(R.id.toolbar_thanhtoan);

        qrcode = findViewById(R.id.qrcode);

        btnMoMo = findViewById(R.id.btnMoMo);
        btnMoMo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* requestPayment();*/
                Display display = getWindowManager().getDefaultDisplay();
                Point size = new Point();
                display.getSize(size);
                int width = size.x;
                int height = size.y;
                int smallerDimension = width < height ? width : height;
                smallerDimension = smallerDimension * 3 / 4;

                QRCodeWriter qrCodeWriter = new QRCodeWriter();
                try {
                    BitMatrix bitMatrix = qrCodeWriter.encode("https://me-uat.mservice.com.vn/me/ZKIpuBs3FZTKTaTAT5T7", BarcodeFormat.QR_CODE, smallerDimension, smallerDimension);
                    int matrixWidth = bitMatrix.getWidth();
                    int matrixHeight = bitMatrix.getHeight();
                    int[] pixels = new int[matrixWidth * matrixHeight];
                    for (int y = 0; y < matrixHeight; y++) {
                        int offset = y * matrixWidth;
                        for (int x = 0; x < matrixWidth; x++) {
                            pixels[offset + x] = bitMatrix.get(x, y) ? getResources().getColor(R.color.black) : getResources().getColor(R.color.white);
                        }
                    }
                    Bitmap bitmap = Bitmap.createBitmap(matrixWidth, matrixHeight, Bitmap.Config.ARGB_8888);
                    bitmap.setPixels(pixels, 0, matrixWidth, 0, 0, matrixWidth, matrixHeight);
                    qrcode.setImageBitmap(bitmap);
                } catch (Exception e) {}
            }
        });

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Chi tiết đơn đặt tiệc");

        findViewById(R.id.iconBackThanhToan).setOnClickListener(v->{
            finish();
        });

        ViewMapping();

        Bundle bundle =  getIntent().getExtras();
        if(bundle == null){
            return;
        }
        user1 = (User) bundle.get("user");

        Intent intent = getIntent();
        if(intent != null){
            datTiec_Cook = (ArrayList<datTiec_Cook>) intent.getSerializableExtra("datTiec_CookList");
            datTiec_dichVu = (ArrayList<datTiec_dichVu>) intent.getSerializableExtra("datTiec_dichVuList");
            datTiecLobby1 = (datTiec_Lobby)  intent.getSerializableExtra("datTiec_Lobby1");
            user = (User)  intent.getSerializableExtra("user");
            datTiec1 = (datTiec) intent.getSerializableExtra("dattiec");
            listServiceSelected = (ArrayList<DichVu>) intent.getSerializableExtra("dichVuArrayList");
            listCook = (ArrayList<Cook>) intent.getSerializableExtra("cookArrayList");

        }
        else {
            Toast.makeText(this, "BUG_ThanhToan", Toast.LENGTH_SHORT).show();
            return;
        }

        ten.setText(user.getTen());
        sodt.setText(user.getSodt());
        soluongbandat.setText(datTiec1.getSoluongbandat());
        ngaytochuc.setText(datTiec1.getNgay());
        gio.setText(datTiec1.getGio());
        thoigianthue.setText(datTiec1.getThoigianthue());
        tensanh.setText(datTiecLobby1.getTen());
        giasanh.setText(datTiecLobby1.getGia());
        giaban.setText(datTiecLobby1.getGiaban());
        diachisanh.setText(datTiecLobby1.getDiachi());
        soLuongService.setText(datTiec_dichVu.size()+"");

        for(int i = 0; i < datTiec_dichVu.size(); i++){
            giaService += Double.parseDouble(datTiec_dichVu.get(i).getGia());
        }
        tongGiaCacDichVu.setText(giaService+"");

        int soluongkhaivi = 0;
        int soluongmonchinh = 0;
        int soluongtrangmieng = 0;
        int soluongnuocuong = 0;
        for(int i = 0; i < datTiec_Cook.size(); i++)
        {
            if(datTiec_Cook.get(i).getId().equals("1")){
                soluongkhaivi++;
            }
            if(datTiec_Cook.get(i).getId().equals("2")){
                soluongmonchinh++;
            }
            if(datTiec_Cook.get(i).getId().equals("3")){
                soluongtrangmieng++;
            }
            if(datTiec_Cook.get(i).getId().equals("4")){
                soluongnuocuong++;
            }
            giaCook += Double.parseDouble(datTiec_Cook.get(i).getGia()) * Double.parseDouble(datTiec1.getSoluongbandat());
        }
        soLuongKhaiVi.setText(soluongkhaivi+"");
        soLuongMonChinh.setText(soluongmonchinh+"");
        soLuongTrangMieng.setText(soluongtrangmieng+"");
        soLuongNuocUong.setText(soluongnuocuong+"");
        tongGiaCacMonAn.setText(giaCook+"");

        tonggia = Double.parseDouble(datTiecLobby1.getGia()) + giaService + giaCook;
        tongtienthanhtoanTanhToanActivity.setText(tonggia+"");

        toDetailLobby.setOnClickListener(v->{
            Intent i = new Intent(ThanhToanActivity.this, DetailLobby.class);
            i.putExtra("ten", datTiecLobby1.getTen());
            i.putExtra("quan", datTiecLobby1.getQuan());
            i.putExtra("diachi", datTiecLobby1.getDiachi());
            i.putExtra("soluongban", datTiecLobby1.getSoluongban());
            i.putExtra("giaban", datTiecLobby1.getGiaban());
            i.putExtra("kichthuoc", datTiecLobby1.getKichthuoc());
            i.putExtra("thongtinkhac", datTiecLobby1.getThongtinkhac());
            i.putExtra("anh", datTiecLobby1.getAnh());
            i.putExtra("gia", datTiecLobby1.getGia());
            startActivity(i);
        });

        FirebaseDatabase.getInstance().getReference("dichvu")
                        .addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                            }
                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {}});
        clickToDetailCook.setOnClickListener(v->{
            Intent intent1 = new Intent(ThanhToanActivity.this, ShowListCookThanhToanActivity.class);
            intent1.putExtra("listCook", listCook);
            startActivity(intent1);
        });

        clickToDetailService.setOnClickListener(v->{
            Intent intent1 = new Intent(ThanhToanActivity.this, ShowListDichVuThanhToanActivity.class);
            intent1.putExtra("listServiceSelected", listServiceSelected);
            startActivity(intent1);
        });

        FirebaseDatabase.getInstance().getReference("user")
                        .addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                if(dataSnapshot.exists()){
                                    for(DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                                        User user2 = dataSnapshot1.getValue(User.class);
                                        user2.setKey(dataSnapshot1.getKey());

                                        if(user2.getSodt().equals(user.getSodt())){
                                            keyuser = user2.getKey();
                                        }
                                    }
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });

        clickPayThanhToan.setOnClickListener(v->{
            DialogPlus dialogPlus = DialogPlus.newDialog(ThanhToanActivity.this)
                    .setContentHolder(new ViewHolder(R.layout.dialog_noti_thanhtoan))
                    .setGravity(Gravity.CENTER)
                    .setExpanded(true, 700)
                    .setCancelable(false)
                    .create();

            View view = dialogPlus.getHolderView();
            TextView clickHuyThanhToan_notiDialogThanhToan = view.findViewById(R.id.clickHuyThanhToan_notiDialogThanhToan);
            TextView clickPayThanhToan_notiDialogThanhToan = view.findViewById(R.id.clickPayThanhToan_notiDialogThanhToan);
            TextView soTienThanhToan_notiThanhToanPay = view.findViewById(R.id.soTienThanhToan_notiThanhToanPay);
            TextView soTienPayTrongAccount_notiThanhToanPay = view.findViewById(R.id.soTienPayTrongAccount_notiThanhToanPay);
            TextView notisodukhonghople = view.findViewById(R.id.notisodukhonghople);

            dialogPlus.show();

            soTienThanhToan_notiThanhToanPay.setText(giaService+"");
            soTienPayTrongAccount_notiThanhToanPay.setText(user.getPay());

            clickPayThanhToan_notiDialogThanhToan.setOnClickListener(vii->{
                if(datTiec1.getTinhTrangThanhToan().equals("da thanh toan (PAY)")){
                    Toast.makeText(this, "Bạn đã thanh toán", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(Double.parseDouble(user.getPay()) < giaService){
                    notisodukhonghople.setVisibility(View.VISIBLE);
                }
                else {
                    double a = Double.parseDouble(user.getPay()) - giaService;
                    FirebaseDatabase.getInstance().getReference().child("user");
                    String b = String.valueOf(a);

                    user.setPay(b);
                    ref.child(keyuser).setValue(user);

                    Toast.makeText(this, "Thanh toán thành công", Toast.LENGTH_SHORT).show();

                    datTiec1.setTinhTrangThanhToan("da thanh toan (PAY)");
                    FirebaseDatabase.getInstance().getReference("dattiec").child(datTiec1.getMa()).setValue(datTiec1);

                    Date date = new Date();
                    String dates = String.valueOf(date);

                    DatabaseReference ref = FirebaseDatabase.getInstance().getReference("hoadon").push();
                    ChiTietDonDatTiec chiTietDonDatTiec = new ChiTietDonDatTiec(user1.getTen(), user1.getSodt(), datTiec1.getSoluongbandat(), datTiec1.getNgay(), datTiec1.getGio(), datTiec1.getThoigianthue(), dates, datTiecLobby1.getGia(), giaCook+"", giaService+"", tonggia+"", datTiec1.getTinhTrangThanhToan(), datTiec1.getTrangthai(), datTiec1.getMa(), ref.getKey(), datTiecLobby1.getMaLobby());
                    ref.setValue(chiTietDonDatTiec);
                    dialogPlus.dismiss();
                }
            });
            clickHuyThanhToan_notiDialogThanhToan.setOnClickListener(vi->{
                dialogPlus.dismiss();
            });
        });

    }
    void ViewMapping(){
        ten = findViewById(R.id.tenThanhToan);
        sodt = findViewById(R.id.sodtThanhToan);
        soluongbandat = findViewById(R.id.soLuongBanThanhToan);
        ngaytochuc = findViewById(R.id.ngayToChucThanhToan);
        gio = findViewById(R.id.gioToChucTiecThanhToan);
        thoigianthue = findViewById(R.id.thoigianthueThanhToan);
        tensanh = findViewById(R.id.tenSanhThanhToan);
        giasanh = findViewById(R.id.giaSanhThanhToan);
        giaban = findViewById(R.id.giaBanThanhToan);
        diachisanh = findViewById(R.id.diaChiSanhThanhToan);
        soLuongKhaiVi = findViewById(R.id.soLuongKhaiViThanhToan);
        soLuongMonChinh = findViewById(R.id.soLuongMonChinhThanhToan);
        soLuongTrangMieng = findViewById(R.id.soLuongTrangMiengThanhToan);
        soLuongNuocUong = findViewById(R.id.soLuongNuocUongThanhToan);
        tongGiaCacMonAn = findViewById(R.id.tongGiaCacMonAnThanhToan);
        soLuongService = findViewById(R.id.soLuongServiceThanhToan);
        tongGiaCacDichVu = findViewById(R.id.tongGiaCacDichVu);
        tongtienthanhtoanTanhToanActivity = findViewById(R.id.tongtienthanhtoanTanhToanActivity);

        toDetailLobby = findViewById(R.id.clickToDetailLobbyThanhToan);
        clickToDetailCook = findViewById(R.id.clickToDetailCookThanhToan);
        clickToDetailService = findViewById(R.id.clickToDetailServiceThanhToan);
        clickPayThanhToan = findViewById(R.id.clickPayThanhToan);
    }



    //Get token through MoMo app
    private void requestPayment() {
        AppMoMoLib.getInstance().setAction(AppMoMoLib.ACTION.PAYMENT);
        AppMoMoLib.getInstance().setActionType(AppMoMoLib.ACTION_TYPE.GET_TOKEN);
        /*if (tongTien.getText().toString() != null && tongTien.getText().toString().trim().length() != 0)
            amount = tongTien.getText().toString().trim();*/

        Map<String, Object> eventValue = new HashMap<>();
        //client Required
        eventValue.put("merchantname", merchantName); //Tên đối tác. được đăng ký tại https://business.momo.vn. VD: Google, Apple, Tiki , CGV Cinemas
        eventValue.put("merchantcode", merchantCode); //Mã đối tác, được cung cấp bởi MoMo tại https://business.momo.vn
        eventValue.put("amount", amount); //Kiểu integer
        eventValue.put("orderId", "orderId123456789"); //uniqueue id cho Bill order, giá trị duy nhất cho mỗi đơn hàng
        eventValue.put("orderLabel", "Mã đơn hàng"); //gán nhãn

        //client Optional - bill info
        eventValue.put("merchantnamelabel", "Dịch vụ");//gán nhãn
        eventValue.put("fee", "0"); //Kiểu integer
        eventValue.put("description", description); //mô tả đơn hàng - short description

        //client extra data
        eventValue.put("requestId",  merchantCode+"merchant_billId_"+System.currentTimeMillis());
        eventValue.put("partnerCode", merchantCode);
        //Example extra data
        JSONObject objExtraData = new JSONObject();
        try {
            objExtraData.put("site_code", "008");
            objExtraData.put("site_name", "CGV Cresent Mall");
            objExtraData.put("screen_code", 0);
            objExtraData.put("screen_name", "Special");
            objExtraData.put("movie_name", "Kẻ Trộm Mặt Trăng 3");
            objExtraData.put("movie_format", "2D");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        eventValue.put("extraData", objExtraData.toString());

        eventValue.put("extra", "");
        AppMoMoLib.getInstance().requestMoMoCallBack(this, eventValue);
    }

    //Get token callback from MoMo app an submit to server side
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == AppMoMoLib.getInstance().REQUEST_CODE_MOMO && resultCode == -1) {
            if(data != null) {
                if(data.getIntExtra("status", -1) == 0) {
                    //TOKEN IS AVAILABLE
                    Log.e("thanhcong","message: " + "Get token " + data.getStringExtra("message"));
                    String token = data.getStringExtra("data"); //Token response
                    String phoneNumber = data.getStringExtra("phonenumber");
                    String env = data.getStringExtra("env");
                    if(env == null){
                        env = "app";
                    }

                    if(token != null && !token.equals("")) {
                        // TODO: send phoneNumber & token to your server side to process payment with MoMo server
                        // IF Momo topup success, continue to process your order
                    } else {
                        Log.e("thanhcong", "message: not_receive_info");
                    }
                } else if(data.getIntExtra("status", -1) == 1) {
                    //TOKEN FAIL
                    String message = data.getStringExtra("message") != null?data.getStringExtra("message"):"Thất bại";
                    Log.e("thanhcong","message: " + message);
                } else if(data.getIntExtra("status", -1) == 2) {
                    //TOKEN FAIL
                    Log.e("thanhcong","message: not_receive_info ");
                } else {
                    //TOKEN FAIL
                    Log.e("thanhcong","message: not_receive_info" );
                }
            } else {
                Log.e("thanhcong","message: not_receive_info" );
            }
        } else {
            Log.e("thanhcong","message: not_receive_info" );
        }
    }

}