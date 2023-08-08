package com.ssi.devicemonitor.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class DeviceMonitor {
    private List<Device> devices;
    private Timer statusUpdateTimer;
    private long nextExecutionTime;

    public DeviceMonitor() {
        devices = new ArrayList<>();

        // Start the timer to simulate status updates every few seconds
        statusUpdateTimer = new Timer();
        statusUpdateTimer.schedule(new StatusUpdateTask(), 0, 5000); // Update every 5 seconds
        nextExecutionTime = System.currentTimeMillis() + 5000; // Store the next execution time
    }

    private long getRemainingTimeUntilNextUpdate() {
        return Math.max(0, nextExecutionTime - System.currentTimeMillis());
    }

    public List<Device> getDevices() {
        return devices;
    }

    public void addDevice(Device device) {
        this.devices.add(device);
    }
    public void replaceDevice(Device newDevice,Device oldDevice) {
        this.devices.remove(oldDevice);
        this.devices.add(newDevice);
    }

    public void removeDevice(Device device) {
        this.devices.remove(device);
    }

    public String showTimes()
    {
        long time = getRemainingTimeUntilNextUpdate()/1000;
        return "last update accured "+(5-time)+ "s - next in " + (time) +"s";
    }


    private class StatusUpdateTask extends TimerTask {
        private Random random = new Random();

        @Override
        public void run() {
            for (Device device : devices) {
                // Simulate random status updates
                boolean isOnline = random.nextBoolean();
                device.setStatus(isOnline ? "Online" : "Offline");
            }
            nextExecutionTime = System.currentTimeMillis() + 5000; // Store the next execution time
        }
    }
}
