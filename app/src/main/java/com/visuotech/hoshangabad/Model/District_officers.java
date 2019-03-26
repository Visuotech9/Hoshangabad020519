package com.visuotech.hoshangabad.Model;

public class District_officers {
    String officer_name,officer_responsibility,officer_mobile,officer_phone,officer_email,officer_post;

    public  District_officers(){

    }

    public District_officers(String officer_name, String officer_responsibility, String officer_mobile, String officer_phone, String officer_email, String officer_post) {
        this.officer_name = officer_name;
        this.officer_responsibility = officer_responsibility;
        this.officer_mobile = officer_mobile;
        this.officer_phone = officer_phone;
        this.officer_email = officer_email;
        this.officer_post = officer_post;
    }

    public String getOfficer_name() {
        return officer_name;
    }

    public void setOfficer_name(String officer_name) {
        this.officer_name = officer_name;
    }

    public String getOfficer_responsibility() {
        return officer_responsibility;
    }

    public void setOfficer_responsibility(String officer_responsibility) {
        this.officer_responsibility = officer_responsibility;
    }

    public String getOfficer_mobile() {
        return officer_mobile;
    }

    public void setOfficer_mobile(String officer_mobile) {
        this.officer_mobile = officer_mobile;
    }

    public String getOfficer_phone() {
        return officer_phone;
    }

    public void setOfficer_phone(String officer_phone) {
        this.officer_phone = officer_phone;
    }

    public String getOfficer_email() {
        return officer_email;
    }

    public void setOfficer_email(String officer_email) {
        this.officer_email = officer_email;
    }

    public String getOfficer_post() {
        return officer_post;
    }

    public void setOfficer_post(String officer_post) {
        this.officer_post = officer_post;
    }
}
