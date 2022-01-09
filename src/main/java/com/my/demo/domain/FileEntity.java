package com.my.demo.domain;

public class FileEntity {
    private  String fileName;
    private  String filePath;
    private  Integer Count;
    private String changeTime;

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
        return "File{" +
                "fileName='" + fileName + '\'' +
                ", filePath='" + filePath + '\'' +
                ", Count=" + Count +
                ", changeDate='" + changeTime + '\'' +
                '}';
    }
}
