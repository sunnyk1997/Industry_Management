package com.desirestodesigns.complexion;

public class EmployeeAttendance extends Employee {
    private int present,absent,two;
    private double one_and_half;
    public int getPresent() {
        return present;
    }

    public void setPresent(int present) {
        this.present = present;
    }

    public int getAbsent() {
        return absent;
    }

    public void setAbsent(int absent) {
        this.absent = absent;
    }

    public double getOne_and_half() {
        return one_and_half;
    }

    public void setOne_and_half(double one_and_half) {
        this.one_and_half = one_and_half;
    }

    public int getTwo() {
        return two;
    }

    public void setTwo(int two) {
        this.two = two;
    }

    public EmployeeAttendance(String empName, String empDesignation, String empRole, long phoneNum, int image, long monSal, int dWage, int ot, int leaves, int present, int absent, double one_and_half, int two) {
        super(empName, empDesignation, empRole, phoneNum, image, monSal, dWage, ot, leaves);
        this.present = present;
        this.absent = absent;
        this.one_and_half = one_and_half;
        this.two = two;
    }

    public EmployeeAttendance(int present, int absent, double one_and_half, int two) {
        this.present = present;
        this.absent = absent;
        this.one_and_half = one_and_half;
        this.two = two;
    }

    public EmployeeAttendance(String empName, String empDesignation, String empRole, long phoneNum, int image, long monSal, int dWage, int ot, int leaves) {
        super(empName, empDesignation, empRole, phoneNum, image, monSal, dWage, ot, leaves);
    }

    public EmployeeAttendance() {
    }
}
