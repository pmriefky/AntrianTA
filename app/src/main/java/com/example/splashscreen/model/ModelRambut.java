package com.example.splashscreen.model;

import java.util.List;

public class ModelRambut {

    /**
     * status : 200
     * message : Success
     * data : [{"kode":"2","nama_rambut":"Undercut","keterangan":"aasfasfasd","gambar":"image/undercut.jpg"}]
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
         * kode : 2
         * nama_rambut : Undercut
         * keterangan : aasfasfasd
         * gambar : image/undercut.jpg
         */

        private String kode;
        private String nama_rambut;
        private String keterangan;
        private String gambar;

        public String getKode() {
            return kode;
        }

        public void setKode(String kode) {
            this.kode = kode;
        }

        public String getNama_rambut() {
            return nama_rambut;
        }

        public void setNama_rambut(String nama_rambut) {
            this.nama_rambut = nama_rambut;
        }

        public String getKeterangan() {
            return keterangan;
        }

        public void setKeterangan(String keterangan) {
            this.keterangan = keterangan;
        }

        public String getGambar() {
            return gambar;
        }

        public void setGambar(String gambar) {
            this.gambar = gambar;
        }


    }
}
