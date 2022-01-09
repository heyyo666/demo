package com.my.demo.domain;

public class FileEntity {
    private  String fileName;
    private  String filePath;
    private  Integer Count;
    private String changeTime;
    private String publicKey;
    private String permissions;

    public String getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }

    public String getPermissions() {
        return permissions;
    }

    public void setPermissions(String permissions) {
        this.permissions = permissions;
    }

    public String getChangeTime() {
        return changeTime;
    }

    public void setChangeTime(String changeTime) {
        this.changeTime = changeTime;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public Integer getCount() {
        return Count;
    }

    public void setCount(Integer count) {
        Count = count;
    }


    @Override
    public String toString() {
        return "FileEntity{" +
                "fileName='" + fileName + '\'' +
                ", filePath='" + filePath + '\'' +
                ", Count=" + Count +
                ", changeTime='" + changeTime + '\'' +
                ", publicKey='" + publicKey + '\'' +
                ", permissions='" + permissions + '\'' +
                '}';
    }
}
