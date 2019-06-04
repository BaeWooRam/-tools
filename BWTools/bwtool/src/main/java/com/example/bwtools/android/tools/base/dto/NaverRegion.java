package com.example.bwtools.android.tools.base.dto;

<<<<<<< HEAD
public class NaverRegion extends Region {
=======
public class NaverRegion extends Location {
>>>>>>> parent of cd21003... Revert "06-04"
    private String internetURL;

    private String description;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description==null || description.equals("") ? "없음":description;
    }

    public String getInternetURL() {
        return internetURL;
    }

    public void setInternetURL(String internetURL) {
        this.internetURL = internetURL;
    }

    public String parserHTML(String stringHTML){
        return android.text.Html.fromHtml(stringHTML).toString();
    }

}
