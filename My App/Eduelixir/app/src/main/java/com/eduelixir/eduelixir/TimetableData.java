package com.eduelixir.eduelixir;

/**
 * Created by Sandesh on 1/18/2017.
 */
public class TimetableData {

    private String subName;
    private String staffName;

    public TimetableData(String subName, String staffName) {
        this.subName = subName;
        this.staffName = staffName;
    }

    public String getSubName() {
        return subName;
    }

    public void setSubName(String subName) {
        this.subName = subName;
    }

    public String getStaffName() {
        return staffName;
    }

    public void setStaffName(String staffName) {
        this.staffName = staffName;
    }

}
