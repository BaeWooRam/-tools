package com.example.bwtools.android.tools.base.dto;

import com.google.gson.annotations.SerializedName;

public class NaverRegion {
    @SerializedName("num")
    private int num;

    @SerializedName("title")
    private String title;

    @SerializedName("address")
    private String address;

    @SerializedName("category")
    private String category;

    @SerializedName("link")
    private String internetURL;

    @SerializedName("description")
    private String description;

    @SerializedName("telephone")
    private String telephone;

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone==null || telephone.equals("") ? "없음":telephone;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description==null || description.equals("") ? "없음":android.text.Html.fromHtml(description).toString();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = android.text.Html.fromHtml(title).toString();
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = android.text.Html.fromHtml(category).toString();
    }

    public String getInternetURL() {
        return internetURL;
    }

    public void setInternetURL(String internetURL) {
        this.internetURL = internetURL;
    }

    public boolean isContainAddress(String contain) {
        if (contain == null)
            return true;
        else
            return getAddress().contains(contain) ? true : false;
    }

    public boolean isContainDescription(String contain) {
        if (contain == null)
            return true;
        else
            return getDescription().contains(contain) ? true : false;
    }

    public boolean isContainCategory(String contain) {
        if (contain == null)
            return true;
        else
            return getCategory().contains(contain) ? true : false;
    }

    public boolean isContainCategoryOrDescription(String contain) {
        if (contain == null)
            return true;
        else
            return (isContainDescription(contain) || isContainCategory(contain) ? true : false);
    }
}
