package com.privexec.pojoclass.reportAppByCategory;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ReportPotentiallyDataPojo {
    @SerializedName("message")
    @Expose
    public String message;
    @SerializedName("status")
    @Expose
    public Integer status;
    @SerializedName("personalDataType")
    @Expose
    public List<PersonalDataType> personalDataType = null;

    public class PersonalDataType {

        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("typeName")
        @Expose
        private String typeName;
        @SerializedName("typeAbout")
        @Expose
        private String typeAbout;
        @SerializedName("typeDetail")
        @Expose
        private String typeDetail;
        @SerializedName("personal_data_type_display")
        @Expose
        private String personalDataTypeDisplay;
        @SerializedName("group_id")
        @Expose
        private Integer groupId;
        @SerializedName("created_at")
        @Expose
        private String createdAt;
        @SerializedName("updated_at")
        @Expose
        private String updatedAt;
        @SerializedName("hexaColor")
        @Expose
        private String hexaColor;
        @SerializedName("count")
        @Expose
        private Integer count;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getTypeName() {
            return typeName;
        }

        public void setTypeName(String typeName) {
            this.typeName = typeName;
        }

        public String getTypeAbout() {
            return typeAbout;
        }

        public void setTypeAbout(String typeAbout) {
            this.typeAbout = typeAbout;
        }

        public String getTypeDetail() {
            return typeDetail;
        }

        public void setTypeDetail(String typeDetail) {
            this.typeDetail = typeDetail;
        }

        public String getPersonalDataTypeDisplay() {
            return personalDataTypeDisplay;
        }

        public void setPersonalDataTypeDisplay(String personalDataTypeDisplay) {
            this.personalDataTypeDisplay = personalDataTypeDisplay;
        }

        public Integer getGroupId() {
            return groupId;
        }

        public void setGroupId(Integer groupId) {
            this.groupId = groupId;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }

        public String getUpdatedAt() {
            return updatedAt;
        }

        public void setUpdatedAt(String updatedAt) {
            this.updatedAt = updatedAt;
        }

        public String getHexaColor() {
            return hexaColor;
        }

        public void setHexaColor(String hexaColor) {
            this.hexaColor = hexaColor;
        }

        public Integer getCount() {
            return count;
        }

        public void setCount(Integer count) {
            this.count = count;
        }
    }
}