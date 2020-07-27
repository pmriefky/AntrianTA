package com.example.splashscreen.model;

import java.util.List;

public class Profil {

    /**
     * status : 200
     * message : Success
     * data : [{"username":"jons","email":"jons@mail.com","no_hp":"0812345"},{"username":"ken","email":"kenedis@mail.com","no_hp":"081234543"},{"username":"mantap2","email":"mantap@mail.com","no_hp":"081234543"},{"username":"padil","email":"padil@mail.com","no_hp":"08546494"}]
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
         * username : jons
         * email : jons@mail.com
         * no_hp : 0812345
         */

        private String username;
        private String email;
        private String no_hp;

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getNo_hp() {
            return no_hp;
        }

        public void setNo_hp(String no_hp) {
            this.no_hp = no_hp;
        }
    }
}
