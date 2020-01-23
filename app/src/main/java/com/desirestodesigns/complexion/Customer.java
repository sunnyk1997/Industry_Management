package com.desirestodesigns.complexion;

public class Customer {
    String date,mCustomerName,mCustomerId,mCustomerGeolocation,mCustomerManager,mSupervisorName,mSupervisorNumber,mManagerNumber,mCustomerDescription;

    public String getmCustomerName() {
        return mCustomerName;
    }

    public void setmCustomerName(String mCustomerName) {
        this.mCustomerName = mCustomerName;
    }

    public String getmCustomerId() {
        return mCustomerId;
    }

    public void setmCustomerId(String mCustomerId) {
        this.mCustomerId = mCustomerId;
    }

    public String getmCustomerGeolocation() {
        return mCustomerGeolocation;
    }

    public void setmCustomerGeolocation(String mCustomerGeolocation) {
        this.mCustomerGeolocation = mCustomerGeolocation;
    }

    public String getmCustomerManager() {
        return mCustomerManager;
    }

    public void setmCustomerManager(String mCustomerManager) {
        this.mCustomerManager = mCustomerManager;
    }

    public String getmSupervisorName() {
        return mSupervisorName;
    }

    public void setmSupervisorName(String mSupervisorName) {
        this.mSupervisorName = mSupervisorName;
    }

    public String getmSupervisorNumber() {
        return mSupervisorNumber;
    }

    public void setmSupervisorNumber(String mSupervisorNumber) {
        this.mSupervisorNumber = mSupervisorNumber;
    }

    public String getmManagerNumber() {
        return mManagerNumber;
    }

    public void setmManagerNumber(String mManagerNumber) {
        this.mManagerNumber = mManagerNumber;
    }

    public String getmCustomerDescription() {
        return mCustomerDescription;
    }

    public void setmCustomerDescription(String mCustomerDescription) {
        this.mCustomerDescription = mCustomerDescription;
    }

    public Customer(String mCustomerName, String mCustomerId, String mCustomerGeolocation, String mCustomerManager, String mSupervisorName, String mSupervisorNumber, String mManagerNumber, String mCustomerDescription) {
        this.mCustomerName = mCustomerName;
        this.mCustomerId = mCustomerId;
        this.mCustomerGeolocation = mCustomerGeolocation;
        this.mCustomerManager = mCustomerManager;
        this.mSupervisorName = mSupervisorName;
        this.mSupervisorNumber = mSupervisorNumber;
        this.mManagerNumber = mManagerNumber;
        this.mCustomerDescription = mCustomerDescription;
    }

    public Customer() {
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDate() {
        return date;
    }
}
