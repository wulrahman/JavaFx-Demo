package com.example.demo1;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;

public class HelloController {

    @FXML
    public ListView<Grades> Grades;
//    @FXML
//    public Button EditButton;

    @FXML
    private TextField Searchbar;

    @FXML
    private ListView<Students> listView;

    private ContextMenu contextMenu = new ContextMenu();
    protected ObservableList<Students> listInstance = FXCollections.observableArrayList();

    protected ObservableList<Grades> gradesObservableList = FXCollections.observableArrayList();

    @FXML
    public void initialize() throws Exception {
//        System.out.println(Data.getStudent("GK738569"));
//        for (Object object : Data.readData()) {
//            listInstance.add((Students) object);
//        }

        listInstance.addAll(Objects.requireNonNull(Data.getAllStudent()));
        MenuItem editContext = new MenuItem("Edit");
        editContext.setOnAction(this::editMenu);

        MenuItem addContext = new MenuItem("Add");
        addContext.setOnAction(this::addGrade);
        contextMenu.getItems().addAll(editContext, addContext);
        listView.setContextMenu(contextMenu);

//        listInstance.addListener((ListChangeListener<Students>) (c) -> {
//            try {
//                Data.saveData(listInstance.toArray());
//            } catch (IOException e) {
//                throw new RuntimeException(e);
//            }
//            while (c.next()) {
//                if (c.getAddedSize() > 0) {
//                    for (int i = c.getFrom(); i < c.getTo(); i++) {
//                        System.out.println("Element at position " + i + " was updated to: " + c.getList().get(i).getStudentName());
//                    }
//                }
//            }
//        });

//        Grades.getSelectionModel().selectedItemProperty().addListener(((observable, oldValue, newValue) -> {
//            try {
//                FXMLLoader fxmlLoader  = new FXMLLoader(HelloController.class.getResource("add-grades-view.fxml"));
//                Stage stage = new Stage();
//                stage.setTitle("My New Stage Title");
//                Scene scene = new Scene(fxmlLoader.load(), 450, 450);
//                scene.getStylesheets().add("style.css");
//                stage.setScene(scene);
//                stage.show();
//            } catch (IOException e) {
//                throw new RuntimeException(e);
//            }
//        }));

//        Grades.setCellFactory(callBack -> {
//            ListCell<Grades> cell = new ListCell<>() {
//                @Override
//                protected void updateItem(Grades item, boolean empty) {
//                    super.updateItem(item, empty);
//                    if(!empty) {
//                        setText(item.toString());
//                    }
//                }
//            };
//            cell.setOnMouseClicked(this::clickGradeCellItem);
//            return cell;
//        });

//        listView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
//            Searchbar.setText(newValue.studentName);
//            Grades.getItems().clear();
//            gradesObservableList.clear();
//            try {
//                if(newValue.getStudentGrades() != null) {
//                    gradesObservableList = FXCollections.observableArrayList(new ArrayList<>(newValue.getStudentGrades()));
//                    Grades.setItems(gradesObservableList);
//                }
//            } catch (SQLException | NullPointerException e) {
//                throw new RuntimeException(e);
//            }
//            Grades.refresh();
//        });


//        listView.setCellFactory((callBack) -> {
//            ListCell<Students> cell = new ListCell<>() {
//                @Override
//                protected void updateItem(Students student, boolean empty) {
//                    super.updateItem(student, empty);
//                    if(!empty) {
//                        setText(student.toString());
//                    }
//                }
//            };
//            callBack.setOnMouseClicked(this::showGrade);
//            return cell;
//        });

        listView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> showGrade());

        listView.setItems(listInstance);
        Searchbar.addEventFilter(KeyEvent.KEY_PRESSED, keyEvent -> {
            try {
                if (keyEvent.getCode() == KeyCode.ENTER) {
                    Students student = new Students(Searchbar.getText());
                    Data.insertStudent(student);
                    listInstance.add(student);
                    Searchbar.setText(null);
                }
                ArrayList<Students> students = Data.searchAllStudent(Searchbar.getText());
                listInstance = FXCollections.observableArrayList(new ArrayList<>());
                if(students != null) {
                    listInstance.addAll(students);
                }
                listView.setItems(listInstance);
            }
             catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });

    }

    public void editMenu(Event event) {
        var stage = (javafx.stage.Stage) listView.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(EditController.class.getResource("edit-view.fxml"));
        try {
            Scene scene = new Scene(fxmlLoader.load());
            scene.getStylesheets().add("style.css");
            EditController controller = fxmlLoader.getController();
            controller.setStudents(listView.getSelectionModel().getSelectedItem());
            if((stage != null)) {
                stage.setScene(scene);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void addGrade(Event event) {
        Students student = listView.getSelectionModel().getSelectedItem();
        FXMLLoader fxmlLoader  = new FXMLLoader(AddGradesController.class.getResource("add-grades-view.fxml"));
        try {
            Scene scene = new Scene(fxmlLoader.load(), 450, 450);
            scene.getStylesheets().add("style.css");

            Stage root = (Stage) Grades.getScene().getWindow();

            Stage stage = new Stage();
            stage.initOwner(root);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle(student.getStudentName() + " Grade");
            stage.setScene(scene);

            AddGradesController controller = fxmlLoader.getController();
            controller.setStudent(student);
            stage.showAndWait();
            this.showGrade();
        } catch (IOException e) {
                throw new RuntimeException(e);
        }
    }

    public void showGrade() {
        Students student = listView.getSelectionModel().getSelectedItem();
        try {
            if(student != null) {
                ArrayList<Grades> grades = student.getStudentGrades();
                gradesObservableList = FXCollections.observableArrayList(new ArrayList<>());
                if(grades!= null) {
                    gradesObservableList.addAll(grades);
                }
                Grades.setItems(gradesObservableList);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}