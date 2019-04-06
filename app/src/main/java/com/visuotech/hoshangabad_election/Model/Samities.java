
package com.visuotech.hoshangabad_election.Model;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class Samities {

    @SerializedName("creation_date")
    private String mCreationDate;
    @SerializedName("samiti_id")
    private String mSamitiId;
    @SerializedName("samiti_name")
    private String mSamitiName;
    @SerializedName("user_id")
    private String mUserId;

    public String getCreationDate() {
        return mCreationDate;
    }

    public void setCreationDate(String creationDate) {
        mCreationDate = creationDate;
    }

    public String getSamitiId() {
        return mSamitiId;
    }

    public void setSamitiId(String samitiId) {
        mSamitiId = samitiId;
    }

    public String getSamitiName() {
        return mSamitiName;
    }

    public void setSamitiName(String samitiName) {
        mSamitiName = samitiName;
    }

    public String getUserId() {
        return mUserId;
    }

    public void setUserId(String userId) {
        mUserId = userId;
    }

}
