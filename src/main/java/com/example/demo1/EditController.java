package com.example.demo1;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class EditController {

    @FXML
    public TextField StudentName;
    @FXML
    public TextField StudentId;
    public Students student;
    public Button SubmitButton;

    @FXML
    public void initialize() {
        SubmitButton.addEventHandler(MouseEvent.MOUSE_CLICKED, this::onSubmit);
    }

    protected ObservableList<Students> listInstance = FXCollections.observableArrayList();
    private void onSubmit(Event event) {
        student.setStudentId(StudentId.getText());
        student.setStudentName(StudentName.getText());
        Node node = (Node) event.getSource();
        Stage stage = (javafx.stage.Stage) node.getScene().getWindow();

//        try {
////            for (Object object : Data.readData()) {
////                Students student1 = (Students) object;
////                if (student1.getStudentId().equals(students.getStudentId())) {
////                    student1.setStudentName(StudentName.getText());
////                    student1.setStudentId(StudentId.getText());
////                }
////                listInstance.add(student1);
////            }
////            Data.saveData(listInstance.toArray());
//        }
//        catch (ClassNotFoundException e) {
//            System.out.println(e.toString());
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }

        String oldStudentId = student.getStudentId();
        student.setStudentName(StudentName.getText());
        student.setStudentId(StudentId.getText());
        try {
            Data.updateStudent(student, oldStudentId);
        }
        catch (SQLException e) {
            System.out.println(e.toString());
        }
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        try {
            Scene scene = new Scene(fxmlLoader.load());
            stage.setTitle("Hello!");
            scene.getStylesheets().add("style.css");
            stage.setScene(scene);
            stage.fullScreenProperty();
            stage.show();
        }
        catch (IOException e) {
            System.out.println(e);
        }

    }
    protected void setStudents(Students student) {
        this.student = student;
        StudentId.setText(student.getStudentId());
        StudentName.setText(student.getStudentName());
    }
}