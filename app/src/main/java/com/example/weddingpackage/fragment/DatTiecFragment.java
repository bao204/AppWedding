package com.example.weddingpackage.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.weddingpackage.DetailLobby;
import com.example.weddingpackage.R;
import com.example.weddingpackage.ThanhToanActivity;
import com.example.weddingpackage.adapter.CookAdapter;
import com.example.weddingpackage.adapter.DichVuAdapter;
import com.example.weddingpackage.adapter.LobbyAdapter;
import com.example.weddingpackage.admin.Selected_ItemsActivity;
import com.example.weddingpackage.admin.Selected_ItemsCookActivity;
import com.example.weddingpackage.admin.selectedLobby_itemActivity;
import com.example.weddingpackage.modle.Cook;
import com.example.weddingpackage.modle.DichVu;
import com.example.weddingpackage.modle.Lobby;
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

import java.util.ArrayList;
import java.util.Calendar;

import java.util.Date;

public class DatTiecFragment extends Fragment {

    private EditText number;
    private EditText time;
    private  EditText thoigiantochucbuatiecs;
    private EditText date;
    private EditText tenKH;
    private Button btn_DatSanh;
    ArrayList<DichVu> listServiceSelected;
    ArrayList<Cook> listKhaiVi;
    ArrayList<Cook> listMonChinh;
    ArrayList<Cook> listTrangMieng;
    ArrayList<Cook> listNuocUong;
    ArrayList<Cook> listCook = new ArrayList<>();
    Lobby lobby;
    boolean checkLobby = false;
    boolean checkListService = false;
    boolean checkKhaiVi = false;
    boolean checkMonChinh = false;
    boolean checkTrangMieng = false;
    boolean checkNuocUong = false;
    User user;
    Boolean checkCancleLobby = false;
    Boolean checkCancleService = false;
    Boolean checkCancleKhaiVi = false;
    Boolean checkCancleMonChinh = false;
    Boolean checkCancleTrangMieng = false;
    Boolean checkCancleNuocUong = false;
    Button btnSelectedLobby;
    TextView notiLobby;
    ImageView showLobby;

    CardView HideandShowImageLobbyDatTiec;
    ImageView clickCancelLobbyChooseDatTiec;
    TextView clickToDetailLobbyDatTiec;
    Button btnSelectedService;
    ImageView cancelListService;
    RecyclerView rcVService;
    TextView notiService;
    Button btnSelectedKhaiVi;
    TextView notiKhaiVi;
    ImageView CancelListKhaiViDatTiec;
    RecyclerView rcVKhaiVi;
    Button btnSelectedMonChinh;
    TextView notiMonChinh;
    ImageView CanceltMonChinhDatTiec;
    RecyclerView rcVMonChinhDatTiec;
    Button btnSelectedTrangMieng;
    TextView notiTrangMieng;
    ImageView CanceltTrangMiengDatTiec;
    RecyclerView rcVTrangMiengDatTiec;
    Button btnSelectedNuocUong;
    TextView notiNuocUong;
    ImageView CancleNuocUongDatTiec;
    RecyclerView rcVNuocUongDatTiec;
    EditText soLuongBan;
    RelativeLayout viewGrounddatTiec;
    ArrayList<datTiec_Cook> datTiec_CookList = new ArrayList<>();
    ArrayList<datTiec_dichVu> datTiec_dichVuList = new ArrayList<>();
    datTiec_Lobby datTiec_Lobby2;
    DichVuAdapter.Item itemDichVu = DichVuAdapter.Item.ITEM_1;
    CookAdapter.ItemCook itemCook = CookAdapter.ItemCook.ITEM_COOK_1;

    private ActivityResultLauncher<Intent> activityResultLauncher;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dattiec,container,false);

        ViewMapping(view);

        Bundle bundle = getArguments();
        if(bundle != null){
            user = (User) bundle.getSerializable("user");
            number.setText(user.getSodt());
            tenKH.setText(user.getTen());
        } else {
            Toast.makeText(getActivity(), "user null", Toast.LENGTH_SHORT).show();
        }

        // --------------------------------Date & Time----------------------------//
        date.setOnClickListener(v -> {
            final Calendar c = Calendar.getInstance();
            int mYear = c.get(Calendar.YEAR);
            int mMonth = c.get(Calendar.MONTH);
            int mDay = c.get(Calendar.DAY_OF_MONTH);
            DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(),
                    (view1, year, monthOfYear, dayOfMonth) -> date.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year), mYear, mMonth, mDay);
            datePickerDialog.show();
        });

        time.setOnClickListener(v -> {
            final Calendar c = Calendar.getInstance();
            int mHour = c.get(Calendar.HOUR_OF_DAY);
            int mMinute = c.get(Calendar.MINUTE);
            TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity(),
                    (view12, hourOfDay, minute) -> time.setText(hourOfDay + ":" + minute), mHour, mMinute, false);
            timePickerDialog.show();
        });

        getList();
        AddDatTiec(view);

        return view;
    }

    @SuppressLint("ResourceAsColor")
    void AddDatTiec(View view){

        btnSelectedLobby.setOnClickListener(v->{

            if(!checkLobby){
                Intent intent = new Intent(getActivity(), selectedLobby_itemActivity.class);
                activityResultLauncher.launch(intent);
                notiLobby.setVisibility(!checkLobby ? View.INVISIBLE : View.VISIBLE);
            }
            else {
                notiLobby.setText("Vui lòng hủy trước khi chọn lại");
            }
        });
        if(!checkCancleLobby){
            clickCancelLobbyChooseDatTiec.setOnClickListener(vi->{
                lobby = null;
                checkLobby = false;
                btnSelectedLobby.setBackgroundResource(R.drawable.background_radius_green);
                showLobby.setVisibility(View.GONE);
                notiLobby.setVisibility(View.INVISIBLE);
                clickCancelLobbyChooseDatTiec.setVisibility(View.INVISIBLE);
                HideandShowImageLobbyDatTiec.setVisibility(View.GONE);
                clickToDetailLobbyDatTiec.setVisibility(View.GONE);
            });
            clickToDetailLobbyDatTiec.setOnClickListener(vii->{
                Intent i = new Intent(getActivity(), DetailLobby.class);
                i.putExtra("ten", lobby.getTen());
                i.putExtra("quan", lobby.getQuan());
                i.putExtra("diachi", lobby.getDiachi());
                i.putExtra("soluongban", lobby.getSoluongban());
                i.putExtra("kichthuoc", lobby.getKichthuoc());
                i.putExtra("thongtinkhac", lobby.getThongtinkhac());
                i.putExtra("anh", lobby.getAnh());
                i.putExtra("gia", lobby.getGia());
                i.putExtra("key", lobby.getKey());
                startActivity(i);
            });
        }

        btnSelectedService.setOnClickListener(v->{
            if(!checkListService){
                Intent intent = new Intent(new Intent(getActivity(), Selected_ItemsActivity.class));
                activityResultLauncher.launch(intent);
            }
            else {
                notiService.setText("Vui lòng hủy trước khi chọn lại");
            }
        });
        if(!checkCancleService){
            cancelListService.setOnClickListener(vi->{
                btnSelectedService.setBackgroundResource(R.drawable.background_radius_green);
                listServiceSelected = null;
                rcVService.setVisibility(View.GONE);
                cancelListService.setVisibility(View.GONE);
                notiService.setVisibility(View.GONE);
                checkListService = false;
            });
        }

        btnSelectedKhaiVi.setOnClickListener(v->{
            if(!checkKhaiVi){
                Intent intent = new Intent(new Intent(getActivity(), Selected_ItemsCookActivity.class));
                intent.putExtra("idCook", "1");
                activityResultLauncher.launch(intent);
            }
            else {
                notiKhaiVi.setText("Vui lòng hủy trước khi chọn lại");
            }
        });
        if(!checkCancleKhaiVi){
            CancelListKhaiViDatTiec.setOnClickListener(vi->{
                btnSelectedKhaiVi.setBackgroundResource(R.drawable.background_radius_green);
                CancelListKhaiViDatTiec.setVisibility(View.GONE);
                notiKhaiVi.setVisibility(View.GONE);
                rcVKhaiVi.setVisibility(View.GONE);
                listKhaiVi = null;
                checkKhaiVi = false;
            });
        }

        btnSelectedMonChinh.setOnClickListener(v->{
            if(!checkMonChinh){
                Intent intent = new Intent(new Intent(getActivity(), Selected_ItemsCookActivity.class));
                intent.putExtra("idCook", "2");
                activityResultLauncher.launch(intent);
            }
            else {
                notiMonChinh.setText("Vui lòng hủy trước khi chọn lại");
            }
        });
        if(!checkCancleMonChinh){
            CanceltMonChinhDatTiec.setOnClickListener(vi->{
                btnSelectedMonChinh.setBackgroundResource(R.drawable.background_radius_green);
                CanceltMonChinhDatTiec.setVisibility(View.GONE);
                notiMonChinh.setVisibility(View.GONE);
                rcVMonChinhDatTiec.setVisibility(View.GONE);
                listMonChinh = null;
                checkMonChinh = false;
            });
        }

        btnSelectedTrangMieng.setOnClickListener(v->{
            if(!checkTrangMieng){
                Intent intent = new Intent(new Intent(getActivity(), Selected_ItemsCookActivity.class));
                intent.putExtra("idCook", "3");
                activityResultLauncher.launch(intent);
            }
            else {
                notiTrangMieng.setText("Vui lòng hủy trước khi chọn lại");
            }
        });
        if(!checkCancleTrangMieng){
            CanceltTrangMiengDatTiec.setOnClickListener(vi->{
                CanceltTrangMiengDatTiec.setVisibility(View.GONE);
                notiTrangMieng.setVisibility(View.GONE);
                rcVTrangMiengDatTiec.setVisibility(View.GONE);
                btnSelectedTrangMieng.setBackgroundResource(R.drawable.background_radius_green);
                checkTrangMieng = false;
            });
        }

        btnSelectedNuocUong.setOnClickListener(v->{
            if(!checkNuocUong){
                Intent intent = new Intent(new Intent(getActivity(), Selected_ItemsCookActivity.class));
                intent.putExtra("idCook", "4");
                activityResultLauncher.launch(intent);
            }
            else {
                notiNuocUong.setText("Vui lòng hủy trước khi chọn lại");
            }
        });
        if(!checkCancleNuocUong){
            CancleNuocUongDatTiec.setOnClickListener(vi->{
                CancleNuocUongDatTiec.setVisibility(View.GONE);
                notiNuocUong.setVisibility(View.GONE);
                rcVNuocUongDatTiec.setVisibility(View.GONE);
                btnSelectedNuocUong.setBackgroundResource(R.drawable.background_radius_green);
                checkNuocUong = false;
            });
        }

        btn_DatSanh.setOnClickListener(v->{
            String sodt = number.getText().toString().trim();
            String ngay = date.getText().toString().trim();
            String thoigian = time.getText().toString().trim();
            String thoigianthue = thoigiantochucbuatiecs.getText().toString().trim();
            String ten = tenKH.getText().toString().trim();
            String soluongban = soLuongBan.getText().toString().trim();

            if(sodt.isEmpty()){
                number.setError("Nhập số điện thoại");
                return;
            }
            if(sodt.length() != 10){
                number.setError("Số điện thoại phải 10 số.");
                return;
            }
            if(ten.isEmpty()){
                tenKH.setError("Nhập tên !!!");
                return;
            }
            if(ngay.isEmpty()){
                date.setError("Ngày tổ chức?");
                return;
            }
            String dayFormat = "dd-MM-yyyy";

            SimpleDateFormat sdf = new SimpleDateFormat(dayFormat);
            sdf.setLenient(false);
            try{
                Date inputDate = sdf.parse(ngay);
                Date dateNow = new Date();

                if(dateNow.equals(inputDate) || inputDate.before(dateNow)){
                    date.setError("Hãy chọn ngày cho tương lai!!");
                    return;
                }
            } catch (Exception e){
                date.setError("Vui lòng nhập đúng định dạng " + dayFormat);
                return;
            }
            if(thoigian.isEmpty()){
                time.setError("Chọn thời gian cái đã");
                return;
            }
            if(soluongban.isEmpty()){
                soLuongBan.setError("Vui lòng nhập số lượng bàn tổ chức tiệc");
                return;
            }

            if(lobby == null){
                Toast.makeText(getActivity(), "Vui lòng chọn sảnh", Toast.LENGTH_SHORT).show();
                notiLobby.setVisibility(View.VISIBLE);
                notiLobby.setText("Vui lòng chọn sảnh");
                notiLobby.setTextColor(Color.RED);
                notiLobby.setTextSize(17);
                return;
            }
            if(listServiceSelected == null){
                Toast.makeText(getActivity(), "Vui lòng chọn dịch vụ", Toast.LENGTH_SHORT).show();
                notiService.setVisibility(View.VISIBLE);
                notiService.setTextColor(Color.RED);
                notiService.setTextSize(17);
                notiService.setText("Vui lòng chọn dịch vụ");
                return;
            }
            if(listKhaiVi == null){
                Toast.makeText(getActivity(), "Vui lòng chọn khai vị", Toast.LENGTH_SHORT).show();

                notiKhaiVi.setVisibility(View.VISIBLE);
                notiKhaiVi.setTextColor(Color.RED);
                notiKhaiVi.setTextSize(17);
                notiKhaiVi.setText("Vui lòng chọn khai vị");
                return;
            }
            if(listMonChinh == null){
                Toast.makeText(getActivity(), "Vui lòng chọn món chính", Toast.LENGTH_SHORT).show();

                notiMonChinh.setVisibility(View.VISIBLE);
                notiMonChinh.setTextColor(Color.RED);
                notiMonChinh.setTextSize(17);
                notiMonChinh.setText("Vui lòng chọn món chính");
                return;
            }
            if(listTrangMieng == null){
                Toast.makeText(getActivity(), "Vui lòng chọn tráng miệng", Toast.LENGTH_SHORT).show();

                notiTrangMieng.setVisibility(View.VISIBLE);
                notiTrangMieng.setTextColor(Color.RED);
                notiTrangMieng.setTextSize(17);
                notiTrangMieng.setText("Vui lòng chọn món tráng miệng");
                return;
            }
            if(listNuocUong == null){
                Toast.makeText(getActivity(), "Vui lòng chọn nước uống", Toast.LENGTH_SHORT).show();

                notiNuocUong.setVisibility(View.VISIBLE);
                notiNuocUong.setTextColor(Color.RED);
                notiNuocUong.setTextSize(17);
                notiNuocUong.setText("Vui lòng chọn nước uống");
                return;
            }
            double gia = 0;
            gia += Double.parseDouble(lobby.getGia());
            DatabaseReference dattiecRef = FirebaseDatabase.getInstance().getReference("dattiec").push();
            
            datTiec_Lobby datTiecLobby = new datTiec_Lobby(lobby.getKey(), dattiecRef.getKey(), lobby.getTen(), lobby.getQuan(), lobby.getAnh(), lobby.getDiachi(), lobby.getKichthuoc(), lobby.getSoluongban(), lobby.getGiaban(), lobby.getThongtinkhac(), lobby.getGia());
            FirebaseDatabase.getInstance().getReference("dattiec_lobby").push().setValue(datTiecLobby);

            for(int i = 0;i < listServiceSelected.size();i++){
                DichVu dichVu = listServiceSelected.get(i);
                String key = dattiecRef.getKey();
                datTiec_dichVu datTiec_dichVu = new datTiec_dichVu(dichVu.getKey(), key, dichVu.getTen(), dichVu.getMota(), dichVu.getAnh(), dichVu.getGia());
                FirebaseDatabase.getInstance().getReference("dattiec_dichvu").push().setValue(datTiec_dichVu);
                datTiec_dichVuList.add(datTiec_dichVu);
                gia += Double.parseDouble(dichVu.getGia());
            }

            for(int i = 0; i < listKhaiVi.size(); i++){
                Cook cook = listKhaiVi.get(i);
                datTiec_Cook datTiecCook = new datTiec_Cook(dattiecRef.getKey(), cook.getKey(), cook.getAnh(), cook.getGia(), cook.getLoai(), cook.getMota(), cook.getTen(), cook.getId());
                FirebaseDatabase.getInstance().getReference("dattiec_cook").push().setValue(datTiecCook);
                datTiec_CookList.add(datTiecCook);
                listCook.add(cook);
                gia += Double.parseDouble(cook.getGia());
            }

            for(int i = 0; i < listMonChinh.size(); i++){
                Cook cook = listMonChinh.get(i);
                datTiec_Cook datTiecCook = new datTiec_Cook(dattiecRef.getKey(), cook.getKey(), cook.getAnh(), cook.getGia(), cook.getLoai(), cook.getMota(), cook.getTen(), cook.getId());
                FirebaseDatabase.getInstance().getReference("dattiec_cook").push().setValue(datTiecCook);
                datTiec_CookList.add(datTiecCook);
                listCook.add(cook);
                gia += Double.parseDouble(cook.getGia());
            }
            for(int i = 0;i < listTrangMieng.size();i++){
                Cook cook = listTrangMieng.get(i);
                String key = dattiecRef.getKey();
                datTiec_Cook datTiecCook = new datTiec_Cook(dattiecRef.getKey(), cook.getKey(), cook.getAnh(), cook.getGia(), cook.getLoai(), cook.getMota(), cook.getTen(), cook.getId());
                FirebaseDatabase.getInstance().getReference("dattiec_cook").push().setValue(datTiecCook);
                datTiec_CookList.add(datTiecCook);
                listCook.add(cook);
                gia += Double.parseDouble(cook.getGia());
            }

            for(int i = 0; i < listNuocUong.size(); i++){
                Cook cook = listNuocUong.get(i);
                datTiec_Cook datTiecCook = new datTiec_Cook(dattiecRef.getKey(), cook.getKey(), cook.getAnh(), cook.getGia(), cook.getLoai(), cook.getMota(), cook.getTen(), cook.getId());
                FirebaseDatabase.getInstance().getReference("dattiec_cook").push().setValue(datTiecCook);
                datTiec_CookList.add(datTiecCook);
                listCook.add(cook);
                gia += Double.parseDouble(cook.getGia());
            }
            datTiec datTiec = new datTiec(sodt, ten, ngay, thoigian, thoigianthue, soluongban, gia+"", dattiecRef.getKey(), "chua xac thuc", "chua thanh toan");
            dattiecRef.setValue(datTiec);

            Toast.makeText(getActivity(), "Đặt tiệc thành công", Toast.LENGTH_SHORT).show();

            if(datTiec_CookList == null && !datTiec_CookList.isEmpty()){
                return;
            }
            if(datTiec_dichVuList == null && !datTiec_dichVuList.isEmpty()){
                return;
            }
            if(datTiecLobby == null){
                return;
            }
            if(listServiceSelected == null && listServiceSelected.isEmpty()){
                return;
            }
            if(listCook == null && listCook.isEmpty()){
                return;
            }


            Intent intent = new Intent(getActivity(), ThanhToanActivity.class);
            intent.putExtra("datTiec_CookList", datTiec_CookList);
            intent.putExtra("datTiec_dichVuList", datTiec_dichVuList);
            intent.putExtra("datTiec_Lobby1", datTiecLobby);
            intent.putExtra("user", user);
            intent.putExtra("dattiec", datTiec);
            intent.putExtra("dichVuArrayList", listServiceSelected);
            intent.putExtra("cookArrayList", listCook);

            startActivity(intent);

            lobby = null;
            checkLobby = false;
            btnSelectedLobby.setBackgroundResource(R.drawable.background_radius_green);
            showLobby.setVisibility(View.GONE);
            notiLobby.setVisibility(View.INVISIBLE);
            clickCancelLobbyChooseDatTiec.setVisibility(View.INVISIBLE);
            HideandShowImageLobbyDatTiec.setVisibility(View.GONE);
            clickToDetailLobbyDatTiec.setVisibility(View.GONE);

            btnSelectedService.setBackgroundResource(R.drawable.background_radius_green);
            listServiceSelected = null;
            rcVService.setVisibility(View.GONE);
            cancelListService.setVisibility(View.GONE);
            notiService.setVisibility(View.GONE);
            checkListService = false;

            btnSelectedKhaiVi.setBackgroundResource(R.drawable.background_radius_green);
            CancelListKhaiViDatTiec.setVisibility(View.GONE);
            notiKhaiVi.setVisibility(View.GONE);
            rcVKhaiVi.setVisibility(View.GONE);
            listKhaiVi = null;
            checkKhaiVi = false;

            btnSelectedMonChinh.setBackgroundResource(R.drawable.background_radius_green);
            CanceltMonChinhDatTiec.setVisibility(View.GONE);
            notiMonChinh.setVisibility(View.GONE);
            rcVMonChinhDatTiec.setVisibility(View.GONE);
            listMonChinh = null;
            checkMonChinh = false;

            CanceltTrangMiengDatTiec.setVisibility(View.GONE);
            notiTrangMieng.setVisibility(View.GONE);
            rcVTrangMiengDatTiec.setVisibility(View.GONE);
            btnSelectedTrangMieng.setBackgroundResource(R.drawable.background_radius_green);
            checkTrangMieng = false;

            CancleNuocUongDatTiec.setVisibility(View.GONE);
            notiNuocUong.setVisibility(View.GONE);
            rcVNuocUongDatTiec.setVisibility(View.GONE);
            btnSelectedNuocUong.setBackgroundResource(R.drawable.background_radius_green);
            checkNuocUong = false;

            date.setText("");
            time.setText("");
            thoigiantochucbuatiecs.setText("");
            soLuongBan.setText("");
        });
    }
    void getList(){
        activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), o -> {
            if(o.getResultCode() == Activity.RESULT_OK){
                Intent intent = o.getData();

                if(intent.getSerializableExtra("lobbySelected") != null){
                    lobby = (Lobby) intent.getSerializableExtra("lobbySelected");
                    checkLobby = intent.getBooleanExtra("checkLobbySelected", false);
                }

                if(lobby == null){
                    btnSelectedLobby.setBackgroundResource(R.drawable.background_radius_green);
                    btnSelectedLobby.setTextColor(Color.WHITE);
                    checkCancleLobby = true;
                }
                else {
                    btnSelectedLobby.setBackgroundResource(R.drawable.background_radius_grey);
                    btnSelectedLobby.setTextColor(Color.WHITE);
                    HideandShowImageLobbyDatTiec.setVisibility(View.VISIBLE);
                    clickToDetailLobbyDatTiec.setVisibility(View.VISIBLE);
                    showLobby.setVisibility(View.VISIBLE);
                    notiLobby.setVisibility(View.VISIBLE);
                    notiLobby.setTextColor(Color.BLACK);
                    notiLobby.setTextSize(16);
                    notiLobby.setText("Nhấn X để bỏ chọn tất cả");
                    clickCancelLobbyChooseDatTiec.setVisibility(View.VISIBLE);
                    Glide.with(getActivity()).load(lobby.getAnh()).placeholder(R.drawable.ic_launcher_foreground).optionalCenterCrop().into(showLobby);
                    checkCancleLobby = false;
                }

                if(intent.getSerializableExtra("listSelected") != null){
                    listServiceSelected = (ArrayList<DichVu>) intent.getSerializableExtra("listSelected");
                    checkListService = intent.getBooleanExtra("checkListService", false);
                }

                if(listServiceSelected == null){
                    btnSelectedService.setBackgroundResource(R.drawable.background_radius_green);
                    btnSelectedService.setTextColor(Color.WHITE);
                    checkCancleService = true;
                }
                else {
                    cancelListService.setVisibility(View.VISIBLE);
                    notiService.setVisibility(View.VISIBLE);
                    rcVService.setVisibility(View.VISIBLE);
                    notiService.setTextColor(Color.BLACK);
                    notiService.setTextSize(16);
                    notiService.setText("Nhấn X để bỏ chọn tất cả");
                    btnSelectedService.setBackgroundResource(R.drawable.background_radius_grey);
                    rcVService.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
                    DichVuAdapter adapter = new DichVuAdapter(getActivity(), listServiceSelected, itemDichVu);
                    rcVService.setAdapter(adapter);
                    checkCancleService = false;
                }

                if(intent.getSerializableExtra("listKhaiViChoose") != null){
                    listKhaiVi = (ArrayList<Cook>) intent.getSerializableExtra("listKhaiViChoose");
                    checkKhaiVi = intent.getBooleanExtra("checkKhaiVi", false);
                }

                if(listKhaiVi == null){
                    btnSelectedKhaiVi.setBackgroundResource(R.drawable.background_radius_green);
                    btnSelectedKhaiVi.setTextColor(Color.WHITE);
                    checkCancleKhaiVi = true;
                }
                else {
                    rcVKhaiVi.setVisibility(View.VISIBLE);
                    CancelListKhaiViDatTiec.setVisibility(View.VISIBLE);
                    notiKhaiVi.setVisibility(View.VISIBLE);
                    notiKhaiVi.setTextColor(Color.BLACK);
                    notiKhaiVi.setTextSize(16);
                    notiKhaiVi.setText("Nhấn X để bỏ chọn tất cả");
                    btnSelectedKhaiVi.setBackgroundResource(R.drawable.background_radius_grey);
                    rcVKhaiVi.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
                    CookAdapter aadapter = new CookAdapter(getActivity(), listKhaiVi, itemCook);
                    rcVKhaiVi.setAdapter(aadapter);
                    checkCancleKhaiVi = false;
                }

                if(intent.getSerializableExtra("listMonChinhChoose") != null){
                    listMonChinh = (ArrayList<Cook>) intent.getSerializableExtra("listMonChinhChoose");
                    checkMonChinh = intent.getBooleanExtra("checkMonChinh", false);
                }

                if(listMonChinh != null){
                    rcVMonChinhDatTiec.setVisibility(View.VISIBLE);
                    CanceltMonChinhDatTiec.setVisibility(View.VISIBLE);
                    notiMonChinh.setVisibility(View.VISIBLE);
                    notiMonChinh.setTextColor(Color.BLACK);
                    notiMonChinh.setTextSize(16);
                    notiMonChinh.setText("Nhấn X để bỏ chọn tất cả");
                    btnSelectedMonChinh.setBackgroundResource(R.drawable.background_radius_grey);
                    rcVMonChinhDatTiec.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
                    CookAdapter adapter = new CookAdapter(getActivity(), listMonChinh, itemCook);
                    rcVMonChinhDatTiec.setAdapter(adapter);
                    checkCancleMonChinh = false;
                }
                else {
                    btnSelectedMonChinh.setBackgroundResource(R.drawable.background_radius_green);
                    btnSelectedMonChinh.setTextColor(Color.WHITE);
                    checkCancleMonChinh = true;
                }

                if(intent.getSerializableExtra("listTrangMiengChoose") != null){
                    listTrangMieng = (ArrayList<Cook>) intent.getSerializableExtra("listTrangMiengChoose");
                    checkTrangMieng = intent.getBooleanExtra("checkMonChinh", false);
                }
                if(listTrangMieng != null){
                    rcVTrangMiengDatTiec.setVisibility(View.VISIBLE);
                    CanceltTrangMiengDatTiec.setVisibility(View.VISIBLE);
                    notiTrangMieng.setVisibility(View.VISIBLE);
                    notiTrangMieng.setTextColor(Color.BLACK);
                    notiTrangMieng.setTextSize(16);
                    notiTrangMieng.setText("Nhấn X để bỏ chọn tất cả");
                    btnSelectedTrangMieng.setBackgroundResource(R.drawable.background_radius_grey);
                    rcVTrangMiengDatTiec.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
                    CookAdapter adapter = new CookAdapter(getActivity(), listTrangMieng, itemCook);
                    rcVTrangMiengDatTiec.setAdapter(adapter);
                    checkCancleTrangMieng = false;
                }
                else {
                    btnSelectedTrangMieng.setBackgroundResource(R.drawable.background_radius_green);
                    btnSelectedTrangMieng.setTextColor(Color.WHITE);
                    checkCancleTrangMieng = true;
                }

                if(intent.getSerializableExtra("listNuocUongChoose") != null){
                    listNuocUong = (ArrayList<Cook>) intent.getSerializableExtra("listNuocUongChoose");
                    checkTrangMieng = intent.getBooleanExtra("checkNuocUong", false);
                }
                if(listNuocUong != null){
                    rcVNuocUongDatTiec.setVisibility(View.VISIBLE);
                    CancleNuocUongDatTiec.setVisibility(View.VISIBLE);
                    notiNuocUong.setVisibility(View.VISIBLE);
                    notiNuocUong.setTextColor(Color.BLACK);
                    notiNuocUong.setTextSize(16);
                    notiNuocUong.setText("Nhấn X để bỏ chọn tất cả");
                    btnSelectedNuocUong.setBackgroundResource(R.drawable.background_radius_grey);
                    rcVNuocUongDatTiec.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
                    CookAdapter adapter = new CookAdapter(getActivity(), listNuocUong, itemCook);
                    rcVNuocUongDatTiec.setAdapter(adapter);
                    checkCancleNuocUong = false;
                }
                else {
                    btnSelectedNuocUong.setBackgroundResource(R.drawable.background_radius_green);
                    btnSelectedNuocUong.setTextColor(Color.WHITE);
                    checkCancleNuocUong = true;
                }
            }
        });
    }
    void ViewMapping(View view){
        btn_DatSanh = view.findViewById(R.id.btn_DatTiec);

        number = view.findViewById(R.id.editText_numberOfPeople);
        date = view.findViewById(R.id.editText_date);
        time = view.findViewById(R.id.editText_time);
        thoigiantochucbuatiecs = view.findViewById(R.id.thoigianthue);
        tenKH = view.findViewById(R.id.editText_hoTenKhachHang);
        soLuongBan = view.findViewById(R.id.editText_soLuongBanDatTiec);

        btnSelectedLobby = view.findViewById(R.id.ChooseLobbyDatTiec);
        notiLobby = view.findViewById(R.id.textWarningNotChooseLobbyDatTiec);
        showLobby = view.findViewById(R.id.showLoobySelectedDatTiec);
        HideandShowImageLobbyDatTiec = view.findViewById(R.id.HideandShowImageLobbyDatTiec);
        clickCancelLobbyChooseDatTiec = view.findViewById(R.id.clickCancelLobbyChooseDatTiec);
        clickToDetailLobbyDatTiec = view.findViewById(R.id.clickToDetailLobbyDatTiec);

        btnSelectedService = view.findViewById(R.id.ChooseDichVuDatTiec);
        cancelListService = view.findViewById(R.id.clickCancelServiceDatTiec);
        rcVService = view.findViewById(R.id.rcVServiceDatTiec);
        notiService = view.findViewById(R.id.notiServiceDatTiec);

        btnSelectedKhaiVi = view.findViewById(R.id.btnSelectedKhaiViDatTiec);
        notiKhaiVi = view.findViewById(R.id.notiKhaiViDatTiec);
        CancelListKhaiViDatTiec = view.findViewById(R.id.clickCancelListKhaiViDatTiec);
        rcVKhaiVi = view.findViewById(R.id.rcVKhaiViDatTiec);

        btnSelectedMonChinh = view.findViewById(R.id.btnSelectedMonChinhDatTiec);
        notiMonChinh = view.findViewById(R.id.notiMonChinhDatTiec);
        CanceltMonChinhDatTiec = view.findViewById(R.id.CancetMonChinhDatTiec);
        rcVMonChinhDatTiec = view.findViewById(R.id.rcVMonChinhDatTiec);

        btnSelectedTrangMieng = view.findViewById(R.id.btnSelectedTrangMiengDatTiec);
        notiTrangMieng = view.findViewById(R.id.notiTrangMiengDatTiec);
        CanceltTrangMiengDatTiec = view.findViewById(R.id.CancelTrangMiengDatTiec);
        rcVTrangMiengDatTiec = view.findViewById(R.id.rcVTrangMiengDatTiec);

        btnSelectedNuocUong = view.findViewById(R.id.btnSelectedNuocUongDatTiec);
        notiNuocUong = view.findViewById(R.id.notiNuocUongDatTiec);
        CancleNuocUongDatTiec = view.findViewById(R.id.CancelNuocUongDatTiec);
        rcVNuocUongDatTiec = view.findViewById(R.id.rcVNuocUonghDatTiec);
    }
}
