package com.ssi.devicemonitor.entity;

import java.time.Instant;

public class SoftwareDevice extends Device{
    private final Instant creationTime;

    public SoftwareDevice(String name,String type,String menuf,String version) {
        super(name,type,menuf,version);
        creationTime = Instant.now();
    }
    public Instant getCreationTime() {
        return creationTime;
    }
}
