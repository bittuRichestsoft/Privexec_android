
package com.privexec.pojoclass.piiTotalApps;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Result {

    @SerializedName("loc")
    @Expose
    private String loc;
    @SerializedName("y")
    @Expose
    private Integer y;
    @SerializedName("x")
    @Expose
    private Integer x;

    public String getLoc() {
        return loc;
    }

    public void setLoc(String loc) {
        this.loc = loc;
    }

    public Integer getY() {
        return y;
    }

    public void setY(Integer y) {
        this.y = y;
    }

    public Integer getX() {
        return x;
    }

    public void setX(Integer x) {
        this.x = x;
    }

}
