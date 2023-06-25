package com.example.gotomalls.Model;

import java.util.List;

public class ModelResponse {
    private String kode,pesan;
    private List<ModelMall> data;

    public String getKode() {
        return kode;
    }

    public String getPesan() {
        return pesan;
    }

    public List<ModelMall> getData() {
        return data;
    }
}
