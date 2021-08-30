package com.privexec.pojoclass.reportAppByCategory;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ReportLastTimeUsedPojo {
    @SerializedName("message")
    @Expose
    public String message;

    @SerializedName("totalCount")
    @Expose
    public int totalCount;

    @SerializedName("status")
    @Expose
    public Integer status;
    @SerializedName("3_months")
    @Expose
    public List<_3Month> _3Months = null;
    @SerializedName("6_months")
    @Expose
    public List<_6Month> _6Months = null;
    @SerializedName("notUsed_last6_month")
    @Expose
    public List<NotUsedLast6Month> notUsedLast6Month = null;


    public class NotUsedLast6Month {

        @SerializedName("id")
        @Expose
        public Integer id;
        @SerializedName("packagesName")
        @Expose
        public String packagesName;
        @SerializedName("user_id")
        @Expose
        public Integer userId;
        @SerializedName("lastTimeUsed")
        @Expose
        public String lastTimeUsed;
        @SerializedName("hexaColor")
        @Expose
        public String hexaColor;

    }

    public class _3Month {

        @SerializedName("id")
        @Expose
        public Integer id;
        @SerializedName("packagesName")
        @Expose
        public String packagesName;
        @SerializedName("user_id")
        @Expose
        public Integer userId;
        @SerializedName("lastTimeUsed")
        @Expose
        public String lastTimeUsed;

    }

    public class _6Month {

        @SerializedName("id")
        @Expose
        public Integer id;
        @SerializedName("packagesName")
        @Expose
        public String packagesName;
        @SerializedName("user_id")
        @Expose
        public Integer userId;
        @SerializedName("lastTimeUsed")
        @Expose
        public String lastTimeUsed;

    }


}
