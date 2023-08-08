package com.ssi.devicemonitor.entity;

public class HardwareDevice extends Device {

    private String location;
    private String macAdd;

    public HardwareDevice(String name,String type,String menuf,String version) {
        super(name,type,menuf,version);
        this.location= "ISRAEL";
        this.macAdd ="ABCDEFG123";
    }
}
