package com.ssi.devicemonitor.controller;

import com.ssi.devicemonitor.entity.Device;
import com.ssi.devicemonitor.entity.DeviceMonitor;
import com.ssi.devicemonitor.entity.HardwareDevice;
import com.ssi.devicemonitor.entity.SoftwareDevice;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.Random;
import java.util.TimerTask;

public class DeviceMonitorController {
    @FXML
    private ListView<Device> deviceListView;

    @FXML
    private TextField deviceNameTextField;

    @FXML
    private Button addDeviceButton;

    @FXML
    private TextField deviceTypeTextField;

    @FXML
    private ToggleGroup typeToggleGroup;

    private DeviceMonitor deviceMonitor;

    private Device selectedDevice;

    @FXML
    private TextField menufactorerTextField;
    @FXML
    private TextField VersionTextField;
    @FXML
    private Label deviceRefreshTime;


    public void initialize() {
        deviceMonitor = new DeviceMonitor();

        deviceMonitor.addDevice(new HardwareDevice("Device 1" , "hardware","Sony","v1.01"));
        deviceMonitor.addDevice(new HardwareDevice("Device 2","hardware","Sony","v1.20"));
        deviceMonitor.addDevice(new HardwareDevice("Device 3","software","Sony","v2"));

        deviceListView.setItems(FXCollections.observableList(deviceMonitor.getDevices()));
        deviceListView.setCellFactory(deviceListView -> new DeviceListCell());

        // Add context menu to ListView
        ContextMenu contextMenu = new ContextMenu();
        MenuItem removeItem = new MenuItem("Remove");

        removeItem.setOnAction(event -> {
            Device selectedDevice = deviceListView.getSelectionModel().getSelectedItem();
            if (selectedDevice != null) {
                deviceMonitor.removeDevice(selectedDevice);
            }
        });
        refreshListView();
        contextMenu.getItems().addAll(removeItem);
        deviceListView.setContextMenu(contextMenu);

    }

    private class DataUpdateTask extends TimerTask {
        private Random random = new Random();

        @Override
        public void run() {
            refreshListView();
        }
    }

    @FXML
    private void addDevice() {
        refreshListView();
        String deviceName = deviceNameTextField.getText();
        if(deviceAlrdayExist(deviceName)){
            deviceNameTextField.setStyle("-fx-text-fill: red;");
            return;
        }
        String deviceType = deviceTypeTextField.getText().toLowerCase();
        Device newDevice;
        if(deviceType.equals("software")){
            newDevice= new SoftwareDevice(deviceName,deviceType,menufactorerTextField.getText(),VersionTextField.getText());

        }
        else if(deviceType.equals("hardware")){
            newDevice= new SoftwareDevice(deviceName,deviceType,menufactorerTextField.getText(),VersionTextField.getText());
        }
        else{
            deviceTypeTextField.setStyle("-fx-text-fill: red;");
            return;
        }

        deviceMonitor.addDevice(newDevice);
        clearChoises();
        refreshListView();
    }
    @FXML
    private void showDevice(){
        refreshListView();
        selectedDevice = deviceListView.getSelectionModel().getSelectedItem();
        deviceNameTextField.setText(selectedDevice.getName());
        deviceTypeTextField.setText(selectedDevice.getDeviceType());
        menufactorerTextField.setText(selectedDevice.getManufacturer());
        VersionTextField.setText(selectedDevice.getVersion());
        if(selectedDevice!=null)
            deviceRefreshTime.setText(deviceMonitor.showTimes());//selectedDevice.displayTimes());
    }
    @FXML
    private void updateDevice() {
        refreshListView();
        if(selectedDevice==null)
            return;
        String deviceName = deviceNameTextField.getText();
        if(deviceAlrdayExist(deviceName) && !selectedDevice.getName().equals(deviceName)){
            deviceNameTextField.setStyle("-fx-text-fill: red;");
            return;
        }
        String deviceType = deviceTypeTextField.getText().toLowerCase();
        Device newDevice;
        if(deviceType.equals("software")){
            newDevice= new SoftwareDevice(deviceName,deviceType,menufactorerTextField.getText(),VersionTextField.getText());

        }
        else if(deviceType.equals("hardware")){
            newDevice= new SoftwareDevice(deviceName,deviceType,menufactorerTextField.getText(),VersionTextField.getText());
        }
        else{
            deviceTypeTextField.setStyle("-fx-text-fill: red;");
            return;
        }
        deviceMonitor.replaceDevice(newDevice,selectedDevice);
        refreshListView();
    }

    @FXML
    private void clearChoises(){
        deviceNameTextField.clear();
        deviceTypeTextField.clear();
        menufactorerTextField.clear();
        VersionTextField.clear();
        deviceRefreshTime.setText("");
        refreshListView();
    }
    private boolean deviceAlrdayExist(String deviceName){
        for(Device temp: deviceMonitor.getDevices()){
            if(temp.getName().equals(deviceName))
                return true;
        }
        return false;
    }


    public void refreshListView() {
        deviceListView.refresh();
    }

    private class DeviceListCell extends ListCell<Device> {
        @Override
        protected void updateItem(Device device, boolean empty) {
            super.updateItem(device, empty);

            if (device == null || empty) {
                setText(null);
                setGraphic(null);
                setStyle(""); // Reset the cell style
            } else {
                setText(device.getName() + " - " + device.getStatus());

                // Set the cell style based on the device status
                if (device.getStatus().equals("Online")) {
                    setStyle("-fx-text-fill: green;");
                } else if (device.getStatus().equals("Offline")) {
                    setStyle("-fx-text-fill: red;");
                } else {
                    setStyle(""); // Reset the cell style
                }
            }
        }
    }
}
