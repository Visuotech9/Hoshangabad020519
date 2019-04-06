
package com.visuotech.hoshangabad_election.Model;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class Voter {

    @SerializedName("creation_date")
    private String mCreationDate;
    @SerializedName("voter_id")
    private String mVoterId;
    @SerializedName("voter_name")
    private String mVoterName;
    @SerializedName("voter_url")
    private String mVoterUrl;

    public String getCreationDate() {
        return mCreationDate;
    }

    public void setCreationDate(String creationDate) {
        mCreationDate = creationDate;
    }

    public String getVoterId() {
        return mVoterId;
    }

    public void setVoterId(String voterId) {
        mVoterId = voterId;
    }

    public String getVoterName() {
        return mVoterName;
    }

    public void setVoterName(String voterName) {
        mVoterName = voterName;
    }

    public String getVoterUrl() {
        return mVoterUrl;
    }

    public void setVoterUrl(String voterUrl) {
        mVoterUrl = voterUrl;
    }

}
