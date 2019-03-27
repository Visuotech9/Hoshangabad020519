package com.visuotech.hoshangabad.Model;

public class ServiceList {
    String other_services_type,other_services_name,other_services_mobile,other_services_lat,other_services_long,user_id;

    public ServiceList(String other_services_type, String other_services_name, String other_services_mobile, String other_services_lat, String other_services_long, String user_id) {
        this.other_services_type = other_services_type;
        this.other_services_name = other_services_name;
        this.other_services_mobile = other_services_mobile;
        this.other_services_lat = other_services_lat;
        this.other_services_long = other_services_long;
        this.user_id = user_id;
    }

    public ServiceList() {
    }

    public String getOther_services_type() {
        return other_services_type;
    }

    public void setOther_services_type(String other_services_type) {
        this.other_services_type = other_services_type;
    }

    public String getOther_services_name() {
        return other_services_name;
    }

    public void setOther_services_name(String other_services_name) {
        this.other_services_name = other_services_name;
    }

    public String getOther_services_mobile() {
        return other_services_mobile;
    }

    public void setOther_services_mobile(String other_services_mobile) {
        this.other_services_mobile = other_services_mobile;
    }

    public String getOther_services_lat() {
        return other_services_lat;
    }

    public void setOther_services_lat(String other_services_lat) {
        this.other_services_lat = other_services_lat;
    }

    public String getOther_services_long() {
        return other_services_long;
    }

    public void setOther_services_long(String other_services_long) {
        this.other_services_long = other_services_long;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }
}
