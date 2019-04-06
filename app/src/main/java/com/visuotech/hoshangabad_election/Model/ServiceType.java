
package com.visuotech.hoshangabad_election.Model;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class ServiceType {

    @SerializedName("type")
    private String mType;

    public String getType() {
        return mType;
    }

    public void setType(String type) {
        mType = type;
    }

}
