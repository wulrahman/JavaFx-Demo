package com.example.demo1;

public class Grades {
    protected String subject, grade;
    public Grades(String subject, String grade) {
        this.subject = subject;
        this.grade = grade;
    }

    @Override
    public String toString() {
        return subject + " -> " + grade;
    }

    public String getSubject() {
        return subject;
    }

    public String getGrade() {
        return grade;
    }
}
