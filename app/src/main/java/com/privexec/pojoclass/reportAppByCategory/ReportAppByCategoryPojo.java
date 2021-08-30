package com.privexec.pojoclass.reportAppByCategory;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ReportAppByCategoryPojo {

    @SerializedName("message")
    @Expose
    public String message;
    @SerializedName("status")
    @Expose
    public Integer status;
    @SerializedName("totalCount")
    @Expose
    public Integer totalCount;
    @SerializedName("appsCategoryCount")
    @Expose
    public List<AppsCategoryCount> appsCategoryCount = null;

    public class AppsCategoryCount {

        @SerializedName("categoriesName")
        @Expose
        public String categoriesName;
        @SerializedName("categoriesCount")
        @Expose
        public Integer categoriesCount;
        @SerializedName("categoriesPercent")
        @Expose
        public Float categoriesPercent;


        @SerializedName("hexaColor")
        @Expose
        public String hexaColor;

    }


}
