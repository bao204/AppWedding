package com.example.weddingpackage.admin;

import com.example.weddingpackage.modle.Cook;
import com.example.weddingpackage.modle.DichVu;
import com.example.weddingpackage.modle.Lobby;

import java.util.ArrayList;

public interface InterfaceSelectedService {
    void ArrayListServiceSelected(ArrayList<DichVu> SelectedService);
    void LobbySelected(Lobby lobby);
    void ArrayListCookSelected(ArrayList<Cook> SelectedCook);
}
