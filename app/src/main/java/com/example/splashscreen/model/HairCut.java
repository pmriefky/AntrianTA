package com.example.splashscreen.model;

import java.util.List;

public class HairCut {


    /**
     * status : 200
     * message : Success
     * data : [{"kode":"3","nama_service":"cuci rambut","keterangan":"qwertyyui","harga":"10000","gambar":"/db_antrian/image/undercut.jpg"},{"kode":"14","nama_service":"cuci rambut","keterangan":"qwertyyui","harga":"10000","gambar":"/db_antrian/image/undercut.jpg"},{"kode":"1001","nama_service":"Kanan Basic Hair Cut","keterangan":"Cutting base with modern style","harga":"45000","gambar":""},{"kode":"1002","nama_service":"Kanan Enjoy Treatment","keterangan":"Cutting by choosing the desired model plus the use","harga":"50000","gambar":""},{"kode":"1003","nama_service":"Kanan Full Service","keterangan":"Cutting by selecting the desired model plus the us","harga":"65000","gambar":""}]
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
         * kode : 3
         * nama_service : cuci rambut
         * keterangan : qwertyyui
         * harga : 10000
         * gambar : /db_antrian/image/undercut.jpg
         */

        private String kode;
        private String nama_service;
        private String keterangan;
        private String harga;
        private String gambar;

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

        public String getGambar() {
            return gambar;
        }

        public void setGambar(String gambar) {
            this.gambar = gambar;
        }
    }
}

