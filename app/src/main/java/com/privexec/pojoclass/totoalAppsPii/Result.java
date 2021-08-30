
package com.privexec.pojoclass.totoalAppsPii;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Result {

    @SerializedName("total_apps")
    @Expose
    private Integer totalApps;
    @SerializedName("pii_shared_apps")
    @Expose
    private Integer piiSharedApps;
    @SerializedName("percentage")
    @Expose
    private Float percentage;

    public Integer getTotalApps() {
        return totalApps;
    }

    public void setTotalApps(Integer totalApps) {
        this.totalApps = totalApps;
    }

    public Integer getPiiSharedApps() {
        return piiSharedApps;
    }

    public void setPiiSharedApps(Integer piiSharedApps) {
        this.piiSharedApps = piiSharedApps;
    }

    public Float getPercentage() {
        return percentage;
    }

    public void setPercentage(Float percentage) {
        this.percentage = percentage;
    }

}
