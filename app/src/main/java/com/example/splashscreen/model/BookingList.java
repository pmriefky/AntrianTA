package com.example.splashscreen.model;

import java.util.List;

public class BookingList {


    /**
     * status : 200
     * message : Success
     * data : [{"nama":"Farhan","nama_service":"Kanan Basic Hair Cut","date":"2020-07-26 21:15:00"},{"nama":"Fadil","nama_service":"Kanan Enjoy Treatment","date":"2020-07-26 18:11:00"},{"nama":"PM Riefky","nama_service":"Kanan Full Service","date":"2020-07-26 18:11:00"}]
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
         * nama : Farhan
         * nama_service : Kanan Basic Hair Cut
         * date : 2020-07-26 21:15:00
         */

        private String nama;
        private String nama_service;
        private String date;

        public String getNama() {
            return nama;
        }

        public void setNama(String nama) {
            this.nama = nama;
        }

        public String getNama_service() {
            return nama_service;
        }

        public void setNama_service(String nama_service) {
            this.nama_service = nama_service;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }
    }
}
