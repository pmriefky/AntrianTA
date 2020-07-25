package com.example.splashscreen.model;

import java.util.List;

public class HairCut {

    /**
     * status : 200
     * message : Success
     * data : [{"kode":"0","nama_service":"","keterangan":"","harga":"0"},{"kode":"1","nama_service":"full","keterangan":"","harga":"0"},{"kode":"2","nama_service":"full","keterangan":"qwertyyui","harga":"0"},{"kode":"3","nama_service":"cuci rambut","keterangan":"qwertyyui","harga":"10000"}]
     */

    private String status;
    private String message;
    private List<DataBean> data;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * kode : 0
         * nama_service :
         * keterangan :
         * harga : 0
         */

        private String kode;
        private String nama_service;
        private String keterangan;
        private String harga;

        public String getKode() {
            return kode;
        }

        public void setKode(String kode) {
            this.kode = kode;
        }

        public String getNama_service() {
            return nama_service;
        }

        public void setNama_service(String nama_service) {
            this.nama_service = nama_service;
        }

        public String getKeterangan() {
            return keterangan;
        }

        public void setKeterangan(String keterangan) {
            this.keterangan = keterangan;
        }

        public String getHarga() {
            return harga;
        }

        public void setHarga(String harga) {
            this.harga = harga;
        }
    }
}
