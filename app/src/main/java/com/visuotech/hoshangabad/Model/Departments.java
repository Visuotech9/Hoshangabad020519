
package com.visuotech.hoshangabad.Model;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class Departments {

    @SerializedName("creation_date")
    private String mCreationDate;
    @SerializedName("department_id")
    private String mDepartmentId;
    @SerializedName("department_name")
    private String mDepartmentName;
    @SerializedName("user_id")
    private String mUserId;

    public String getCreationDate() {
        return mCreationDate;
    }

    public void setCreationDate(String creationDate) {
        mCreationDate = creationDate;
    }

    public String getDepartmentId() {
        return mDepartmentId;
    }

    public void setDepartmentId(String departmentId) {
        mDepartmentId = departmentId;
    }

    public String getDepartmentName() {
        return mDepartmentName;
    }

    public void setDepartmentName(String departmentName) {
        mDepartmentName = departmentName;
    }

    public String getUserId() {
        return mUserId;
    }

    public void setUserId(String userId) {
        mUserId = userId;
    }

}
