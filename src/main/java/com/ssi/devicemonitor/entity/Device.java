package com.ssi.devicemonitor.entity;

enum DeviceTypes {SOFTWARE,HARDWARE};
public abstract class Device {
    private String name;
    private String status;
    private String manufacturer;
    private DeviceTypes DeviceType;
    private String version;

    public Device(String name,String type,String menuf,String version) {
        this.name = name;
        this.status = "Offline"; // Set initial status to Offline
        this.manufacturer = menuf;
        this.DeviceType=type.equals("software")? DeviceTypes.SOFTWARE :  DeviceTypes.HARDWARE ;
        this.version=version;
    }
    public Device(String fromString){
        String[] propertiesArray = fromString.split(",");
        this.name = propertiesArray[0];
        this.status = "Offline"; // Set initial status to Offline
        this.manufacturer = propertiesArray[2];
        this.DeviceType=propertiesArray[1].equals("software")? DeviceTypes.SOFTWARE :  DeviceTypes.HARDWARE ;
        this.version=propertiesArray[3];
    }

    public String getName() {
        return name;
    }
    public String getDeviceType(){return DeviceType== DeviceTypes.SOFTWARE? "software":"hardware";}

    public String getManufacturer() {
        return manufacturer;
    }

    public String getVersion() {
        return version;
    }
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return name+ "," +(DeviceType== DeviceTypes.SOFTWARE? "software":"hardware")+ "," +manufacturer + "," +version;
    }
}
