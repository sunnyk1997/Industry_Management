package com.desirestodesigns.complexion;

public class Employee {
    private  String empName,empDesignation,empRole;
    private  int dWage,ot,leaves;
    private long monSal,phoneNum;
    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    public String getEmpDesignation() {
        return empDesignation;
    }

    public void setEmpDesignation(String empDesignation) {
        this.empDesignation = empDesignation;
    }

    public String getEmpRole() {
        return empRole;
    }

    public void setEmpRole(String empRole) {
        this.empRole = empRole;
    }

    public long getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(long phoneNum) {
        this.phoneNum = phoneNum;
    }

    public long getMonSal() {
        return monSal;
    }

    public void setMonSal(long monSal) {
        this.monSal = monSal;
    }

    public int getdWage() {
        return dWage;
    }

    public void setdWage(int dWage) {
        this.dWage = dWage;
    }

    public int getOt() {
        return ot;
    }

    public void setOt(int ot) {
        this.ot = ot;
    }

    public int getLeaves() {
        return leaves;
    }

    public void setLeaves(int leaves) {
        this.leaves = leaves;
    }

    public Employee(String empName, String empDesignation, String empRole, long phoneNum, int image, long monSal, int dWage, int ot, int leaves) {
        this.empName = empName;
        this.empDesignation = empDesignation;
        this.empRole = empRole;
        this.phoneNum = phoneNum;
        this.monSal = monSal;
        this.dWage = dWage;
        this.ot = ot;
        this.leaves = leaves;
    }

    public Employee() {
    }
}
