
package com.visuotech.hoshangabad_election.Model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

@SuppressWarnings("unused")
public class PollingBooth implements Serializable {

    @SerializedName("creation_date")
    private String mCreationDate;
    @SerializedName("ele_ac")
    private String mEleAc;
    @SerializedName("ele_aww")
    private String mEleAww;
    @SerializedName("ele_aww_mobile")
    private String mEleAwwMobile;
    @SerializedName("ele_blo_mobile")
    private String mEleBloMobile;
    @SerializedName("ele_blo_name")
    private String mEleBloName;
    @SerializedName("ele_block")
    private String mEleBlock;
    @SerializedName("ele_booth_name")
    private String mEleBoothName;
    @SerializedName("ele_boothno")
    private String mEleBoothno;
    @SerializedName("ele_gpanchayat")
    private String mEleGpanchayat;
    @SerializedName("ele_gram")
    private String mEleGram;
    @SerializedName("ele_grs")
    private String mEleGrs;
    @SerializedName("ele_grs_mobile")
    private String mEleGrsMobile;
    @SerializedName("ele_latitude")
    private String mEleLatitude;
    @SerializedName("ele_local1_mobile")
    private String mEleLocal1Mobile;
    @SerializedName("ele_local1_name")
    private String mEleLocal1Name;
    @SerializedName("ele_local2_mobile")
    private String mEleLocal2Mobile;
    @SerializedName("ele_local2_name")
    private String mEleLocal2Name;
    @SerializedName("ele_longitude")
    private String mEleLongitude;
    @SerializedName("ele_nearest_thana")
    private String mEleNearestThana;
    @SerializedName("ele_p_one")
    private String mElePOne;
    @SerializedName("ele_p_one_mobile")
    private String mElePOneMobile;
    @SerializedName("ele_p_zero")
    private String mElePZero;
    @SerializedName("ele_p_zero_mobile")
    private String mElePZeroMobile;
    @SerializedName("ele_sachiv")
    private String mEleSachiv;
    @SerializedName("ele_sachiv_mobile")
    private String mEleSachivMobile;
    @SerializedName("ele_sector_mobile")
    private String mEleSectorMobile;
    @SerializedName("ele_sector_no")
    private String mEleSectorNo;
    @SerializedName("ele_sector_officer")
    private String mEleSectorOfficer;
    @SerializedName("ele_tehsil")
    private String mEleTehsil;
    @SerializedName("ele_thana_mobile")
    private String mEleThanaMobile;
    @SerializedName("ele_thana_phone")
    private String mEleThanaPhone;
    @SerializedName("election_id")
    private String mElectionId;
    @SerializedName("user_id")
    private String mUserId;
    String ele_wc_cctv,ele_wc_cctv_name,ele_wc_cctv_mobile;

    public String getEle_wc_cctv() {
        return ele_wc_cctv;
    }

    public void setEle_wc_cctv(String ele_wc_cctv) {
        this.ele_wc_cctv = ele_wc_cctv;
    }

    public String getEle_wc_cctv_name() {
        return ele_wc_cctv_name;
    }

    public void setEle_wc_cctv_name(String ele_wc_cctv_name) {
        this.ele_wc_cctv_name = ele_wc_cctv_name;
    }

    public String getEle_wc_cctv_mobile() {
        return ele_wc_cctv_mobile;
    }

    public void setEle_wc_cctv_mobile(String ele_wc_cctv_mobile) {
        this.ele_wc_cctv_mobile = ele_wc_cctv_mobile;
    }

    public String getCreationDate() {
        return mCreationDate;
    }

    public void setCreationDate(String creationDate) {
        mCreationDate = creationDate;
    }

    public String getEleAc() {
        return mEleAc;
    }

    public void setEleAc(String eleAc) {
        mEleAc = eleAc;
    }

    public String getEleAww() {
        return mEleAww;
    }

    public void setEleAww(String eleAww) {
        mEleAww = eleAww;
    }

    public String getEleAwwMobile() {
        return mEleAwwMobile;
    }

    public void setEleAwwMobile(String eleAwwMobile) {
        mEleAwwMobile = eleAwwMobile;
    }

    public String getEleBloMobile() {
        return mEleBloMobile;
    }

    public void setEleBloMobile(String eleBloMobile) {
        mEleBloMobile = eleBloMobile;
    }

    public String getEleBloName() {
        return mEleBloName;
    }

    public void setEleBloName(String eleBloName) {
        mEleBloName = eleBloName;
    }

    public String getEleBlock() {
        return mEleBlock;
    }

    public void setEleBlock(String eleBlock) {
        mEleBlock = eleBlock;
    }

    public String getEleBoothName() {
        return mEleBoothName;
    }

    public void setEleBoothName(String eleBoothName) {
        mEleBoothName = eleBoothName;
    }

    public String getEleBoothno() {
        return mEleBoothno;
    }

    public void setEleBoothno(String eleBoothno) {
        mEleBoothno = eleBoothno;
    }

    public String getEleGpanchayat() {
        return mEleGpanchayat;
    }

    public void setEleGpanchayat(String eleGpanchayat) {
        mEleGpanchayat = eleGpanchayat;
    }

    public String getEleGram() {
        return mEleGram;
    }

    public void setEleGram(String eleGram) {
        mEleGram = eleGram;
    }

    public String getEleGrs() {
        return mEleGrs;
    }

    public void setEleGrs(String eleGrs) {
        mEleGrs = eleGrs;
    }

    public String getEleGrsMobile() {
        return mEleGrsMobile;
    }

    public void setEleGrsMobile(String eleGrsMobile) {
        mEleGrsMobile = eleGrsMobile;
    }

    public String getEleLatitude() {
        return mEleLatitude;
    }

    public void setEleLatitude(String eleLatitude) {
        mEleLatitude = eleLatitude;
    }

    public String getEleLocal1Mobile() {
        return mEleLocal1Mobile;
    }

    public void setEleLocal1Mobile(String eleLocal1Mobile) {
        mEleLocal1Mobile = eleLocal1Mobile;
    }

    public String getEleLocal1Name() {
        return mEleLocal1Name;
    }

    public void setEleLocal1Name(String eleLocal1Name) {
        mEleLocal1Name = eleLocal1Name;
    }

    public String getEleLocal2Mobile() {
        return mEleLocal2Mobile;
    }

    public void setEleLocal2Mobile(String eleLocal2Mobile) {
        mEleLocal2Mobile = eleLocal2Mobile;
    }

    public String getEleLocal2Name() {
        return mEleLocal2Name;
    }

    public void setEleLocal2Name(String eleLocal2Name) {
        mEleLocal2Name = eleLocal2Name;
    }

    public String getEleLongitude() {
        return mEleLongitude;
    }

    public void setEleLongitude(String eleLongitude) {
        mEleLongitude = eleLongitude;
    }

    public String getEleNearestThana() {
        return mEleNearestThana;
    }

    public void setEleNearestThana(String eleNearestThana) {
        mEleNearestThana = eleNearestThana;
    }

    public String getElePOne() {
        return mElePOne;
    }

    public void setElePOne(String elePOne) {
        mElePOne = elePOne;
    }

    public String getElePOneMobile() {
        return mElePOneMobile;
    }

    public void setElePOneMobile(String elePOneMobile) {
        mElePOneMobile = elePOneMobile;
    }

    public String getElePZero() {
        return mElePZero;
    }

    public void setElePZero(String elePZero) {
        mElePZero = elePZero;
    }

    public String getElePZeroMobile() {
        return mElePZeroMobile;
    }

    public void setElePZeroMobile(String elePZeroMobile) {
        mElePZeroMobile = elePZeroMobile;
    }

    public String getEleSachiv() {
        return mEleSachiv;
    }

    public void setEleSachiv(String eleSachiv) {
        mEleSachiv = eleSachiv;
    }

    public String getEleSachivMobile() {
        return mEleSachivMobile;
    }

    public void setEleSachivMobile(String eleSachivMobile) {
        mEleSachivMobile = eleSachivMobile;
    }

    public String getEleSectorMobile() {
        return mEleSectorMobile;
    }

    public void setEleSectorMobile(String eleSectorMobile) {
        mEleSectorMobile = eleSectorMobile;
    }

    public String getEleSectorNo() {
        return mEleSectorNo;
    }

    public void setEleSectorNo(String eleSectorNo) {
        mEleSectorNo = eleSectorNo;
    }

    public String getEleSectorOfficer() {
        return mEleSectorOfficer;
    }

    public void setEleSectorOfficer(String eleSectorOfficer) {
        mEleSectorOfficer = eleSectorOfficer;
    }

    public String getEleTehsil() {
        return mEleTehsil;
    }

    public void setEleTehsil(String eleTehsil) {
        mEleTehsil = eleTehsil;
    }

    public String getEleThanaMobile() {
        return mEleThanaMobile;
    }

    public void setEleThanaMobile(String eleThanaMobile) {
        mEleThanaMobile = eleThanaMobile;
    }

    public String getEleThanaPhone() {
        return mEleThanaPhone;
    }

    public void setEleThanaPhone(String eleThanaPhone) {
        mEleThanaPhone = eleThanaPhone;
    }

    public String getElectionId() {
        return mElectionId;
    }

    public void setElectionId(String electionId) {
        mElectionId = electionId;
    }

    public String getUserId() {
        return mUserId;
    }

    public void setUserId(String userId) {
        mUserId = userId;
    }

}
