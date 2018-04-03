package com.apeny.druid.domain;

/**
 * Created by apeny on 2018/4/3.
 */
public class ResponseCategory {
    private String code;
    private String description;
    private String parentCode;
    private Integer type;
    private String responseCode;
    private String creator;
    private String createTime;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getParentCode() {
        return parentCode;
    }

    public void setParentCode(String parentCode) {
        this.parentCode = parentCode;
    }

    public Integer getTyper() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("ResponseCategory{");
        sb.append("code='").append(code).append('\'');
        sb.append(", description='").append(description).append('\'');
        sb.append(", parentCode='").append(parentCode).append('\'');
        sb.append(", type=").append(type);
        sb.append(", responseCode='").append(responseCode).append('\'');
        sb.append(", creator='").append(creator).append('\'');
        sb.append(", createTime='").append(createTime).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
