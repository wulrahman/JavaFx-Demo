package com.example.demo1;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Random;

public class Students implements Serializable {
    protected String studentName = null;
    protected String studentId = null;

    protected ArrayList<Grades> studentGrades = new ArrayList<>();
    Students(String studentName) throws SQLException {
        this.studentName = studentName;
        this.studentId = "GK" + String.valueOf((new Random()).nextInt(10000000));
    }

    Students(String studentName, String studentId) {
        this.studentName = studentName;
        this.studentId = studentId;
    }

    public String getStudentName(){
        return studentName;
    }

    public String getStudentId() {
        return studentId.toString();
    }

    public void addGrade(Grades grade){
        studentGrades.add(grade);
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public ArrayList<Grades> getStudentGrades() throws SQLException {
        return Data.getStudentGrades(studentId);
    }

    @Override
    public String toString() {
        return studentName;
    }
}
