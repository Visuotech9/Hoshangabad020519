package com.visuotech.hoshangabad_election.Model;

public class Deaprtments_members {

    String dept_officer_name,dept_officer_designation,dept_officer_email,dept_officer_mobile,dept_officer_posting,
            dept_officer_duty,dept_officer_dept_id,user_id;

    public Deaprtments_members(String dept_officer_name, String dept_officer_designation, String dept_officer_email, String dept_officer_mobile, String dept_officer_posting, String dept_officer_duty, String dept_officer_dept_id, String user_id) {

        this.dept_officer_name = dept_officer_name;
        this.dept_officer_designation = dept_officer_designation;
        this.dept_officer_email = dept_officer_email;
        this.dept_officer_mobile = dept_officer_mobile;
        this.dept_officer_posting = dept_officer_posting;
        this.dept_officer_duty = dept_officer_duty;
        this.dept_officer_dept_id = dept_officer_dept_id;
        this.user_id = user_id;
    }

    public Deaprtments_members() {
    }

    public String getDept_officer_name() {
        return dept_officer_name;
    }

    public void setDept_officer_name(String dept_officer_name) {
        this.dept_officer_name = dept_officer_name;
    }

    public String getDept_officer_designation() {
        return dept_officer_designation;
    }

    public void setDept_officer_designation(String dept_officer_designation) {
        this.dept_officer_designation = dept_officer_designation;
    }

    public String getDept_officer_email() {
        return dept_officer_email;
    }

    public void setDept_officer_email(String dept_officer_email) {
        this.dept_officer_email = dept_officer_email;
    }

    public String getDept_officer_mobile() {
        return dept_officer_mobile;
    }

    public void setDept_officer_mobile(String dept_officer_mobile) {
        this.dept_officer_mobile = dept_officer_mobile;
    }

    public String getDept_officer_posting() {
        return dept_officer_posting;
    }

    public void setDept_officer_posting(String dept_officer_posting) {
        this.dept_officer_posting = dept_officer_posting;
    }

    public String getDept_officer_duty() {
        return dept_officer_duty;
    }

    public void setDept_officer_duty(String dept_officer_duty) {
        this.dept_officer_duty = dept_officer_duty;
    }

    public String getDept_officer_dept_id() {
        return dept_officer_dept_id;
    }

    public void setDept_officer_dept_id(String dept_officer_dept_id) {
        this.dept_officer_dept_id = dept_officer_dept_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }
}
