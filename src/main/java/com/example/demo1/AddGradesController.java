package com.example.demo1;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.sql.SQLException;

public class AddGradesController {
    @FXML
    public TextField GradeSubject;
    @FXML
    public Button SubmitButton;
    @FXML
    public TextField Grade;

    protected Students student;

    @FXML
    public void initialize() throws Exception {
        SubmitButton.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            SubmitButton.getScene().getWindow().hide();
//            Stage stage = (Stage) SubmitButton.getScene().getWindow();
//
//            Stage root = (Stage) stage.getOwner().getScene().getWindow();
//            root.setScene(stage.getOwner().getScene());

            try {
                Data.addStudentGrades(new Grades(GradeSubject.getText(), Grade.getText()), student.getStudentId());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }
    public void setStudent(Students student) {
        this.student = student;
    }
}
