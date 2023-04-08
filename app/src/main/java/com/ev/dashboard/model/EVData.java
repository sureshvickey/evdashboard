package com.ev.dashboard.model;

public class EVData {
    private String vehicleIdentifier;
    private String soc;
    private String speed;
    private String driveMode;
    private String packCurrent;
    private String packVoltage;

    public String getVehicleIdentifier() {
        return vehicleIdentifier;
    }

    public void setVehicleIdentifier(String vehicleIdentifier) {
        this.vehicleIdentifier = vehicleIdentifier;
    }

    public String getSoc() {
        return soc;
    }

    public void setSoc(String soc) {
        this.soc = soc;
    }

    public String getSpeed() {
        return speed;
    }

    public void setSpeed(String speed) {
        this.speed = speed;
    }

    public String getDriveMode() {
        return driveMode;
    }

    public void setDriveMode(String driveMode) {
        this.driveMode = driveMode;
    }

    public String getPackCurrent() {
        return packCurrent;
    }

    public void setPackCurrent(String packCurrent) {
        this.packCurrent = packCurrent;
    }

    public String getPackVoltage() {
        return packVoltage;
    }

    public void setPackVoltage(String packVoltage) {
        this.packVoltage = packVoltage;
    }
}
