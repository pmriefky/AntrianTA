package com.example.splashscreen.model;

import java.util.List;

public class Promo {

    /**
     * status : 200
     * message : Success
     * data : [{"id":"1","images":"image/quiff.png","title":"quiff"},{"id":"2","images":"image/undercut.jpg","title":"123"}]
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
         * id : 1
         * images : image/quiff.png
         * title : quiff
         */

        private String id;
        private String images;
        private String title;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getImages() {
            return images;
        }

        public void setImages(String images) {
            this.images = images;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }
}
