package com.example.demo1;

import java.sql.*;
import java.util.ArrayList;

public class Data {

    static final String DB_URL = "jdbc:mysql://localhost/students";
    static final String USER = "waheed";
    static final String PASS = "password";

    protected static Connection connection = null;
//    Data () throws SQLException {
//        try {
//            Connection connection = DriverManager.getConnection(DB_URL, USER, PASS);
//        }
//        catch (SQLException e) {
//            System.out.println(e.toString());
//        }
//    }

    public static Students getStudent(String studentId) throws SQLException {
        Connection connection = DriverManager.getConnection(DB_URL, USER, PASS);
        final PreparedStatement prepareStatement = connection.prepareStatement("SELECT `studentName`, `studentId` FROM `student` WHERE `studentId` = ? LIMIT 0, 1");
        prepareStatement.setString(1, studentId);
        ResultSet result = prepareStatement.executeQuery();
        if(result.next()) {
            return new Students(result.getString("studentName"), result.getString("studentId"));
        }
        return null;

    }

    public static ArrayList<Grades> getStudentGrades(String studentId) throws SQLException {
        ArrayList<Grades> grades = new ArrayList<>();
        Connection connection = DriverManager.getConnection(DB_URL, USER, PASS);
        final PreparedStatement prepareStatement = connection.prepareStatement("SELECT `gradeSubject`, `grade` FROM `grades` WHERE `studentId` = ?");
        prepareStatement.setString(1, studentId);
        ResultSet result = prepareStatement.executeQuery();
        while (result.next()) {
            grades.add(new Grades(result.getString("gradeSubject"), result.getString("grade")));
        }
        if(grades.size() > 0) {
            return grades;
        }
        return null;

    }

    public static ArrayList<Students> searchAllStudent(String query) throws SQLException {
        ArrayList<Students> students = new ArrayList<>();
        Connection connection = DriverManager.getConnection(DB_URL, USER, PASS);
        final PreparedStatement prepareStatement = connection.prepareStatement("SELECT `studentName`, `studentId` FROM `student` WHERE `studentName` LIKE ?");
        prepareStatement.setString(1, "%" + query + "%");
        ResultSet result = prepareStatement.executeQuery();
        while(result.next()) {
            students.add(new Students(result.getString("studentName"), result.getString("studentId")));
        }
        if(students.size() > 0) {
            return students;
        }
        return null;

    }

    public static void addStudentGrades(Grades grade, String studentId) throws SQLException {
        ArrayList<Grades> grades = new ArrayList<>();
        Connection connection = DriverManager.getConnection(DB_URL, USER, PASS);
        final PreparedStatement prepareStatement = connection.prepareStatement("INSERT INTO `grades` (`gradeSubject`, `grade`, `studentId`) VALUES(?, ?, ?)");
        prepareStatement.setString(1, grade.getSubject());
        prepareStatement.setString(2, grade.getGrade());
        prepareStatement.setString(3, studentId);
        int result = prepareStatement.executeUpdate();
    }

    public static ArrayList<Students> getAllStudent() throws SQLException {
        ArrayList<Students> students = new ArrayList<>();
        Connection connection = DriverManager.getConnection(DB_URL, USER, PASS);
        final Statement statement = connection.createStatement();
        ResultSet result = statement.executeQuery("SELECT `studentName`, `studentId` FROM `student`");
        while(result.next()) {
            students.add(new Students(result.getString("studentName"), result.getString("studentId")));
        }
        if(students.size() > 0) {
            return students;
        }
        return null;

    }

    public static void updateStudent(Students student, String studentId) throws SQLException {
        Connection connection = DriverManager.getConnection(DB_URL, USER, PASS);
        final PreparedStatement preparedStatement = connection.prepareStatement("UPDATE `student` SET `studentName` = ?, `studentId` = ? WHERE `studentId` = ?");
        preparedStatement.setString(1, student.getStudentName());
        preparedStatement.setString(2, student.getStudentId());
        preparedStatement.setString(3, studentId);
        int result = preparedStatement.executeUpdate();
    }


    public static void insertStudent(Students student) throws SQLException {
        Connection connection = DriverManager.getConnection(DB_URL, USER, PASS);
        final PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO `student` (`studentName`, `studentId`)  VALUES(?, ?)");
        preparedStatement.setString(1, student.getStudentName());
        preparedStatement.setString(2, student.getStudentId());
        int result = preparedStatement.executeUpdate();
    }

    //    File path = new File("dataStudents.ser");
//    public static void saveData(Object[] data) throws IOException {
//        try {
//            FileOutputStream fileOutputStream = new FileOutputStream("dataStudents.ser");
//            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
//            objectOutputStream.writeObject(data);
//            objectOutputStream.close();
//        }
//        catch (IOException e) {
//            System.out.println(e.toString());
//        }
//    }
//
//    public static Object[] readData() throws ClassNotFoundException {
//        try {
//            FileInputStream fileInputStream = new FileInputStream("dataStudents.ser");
//            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
//            Object[] data = (Object[]) objectInputStream.readObject();
//            objectInputStream.close();
//            return data;
//        }
//        catch (IOException e) {
//            System.out.println(e.toString());
//        }
//        return new Object[0];
//    }

}
