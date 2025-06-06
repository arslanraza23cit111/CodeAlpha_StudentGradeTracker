import javafx.animation.*;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.util.ArrayList;

public class StudentGradeTracker extends Application {
    
    private ArrayList<Student> students = new ArrayList<>();
    private ObservableList<Student> studentData = FXCollections.observableArrayList();
    
    private TextField nameField, gradeField;
    private TableView<Student> studentTable;
    private Label avgLabel, highestLabel, lowestLabel, totalStudentsLabel;
    private VBox summaryBox;
    
    public static void main(String[] args) {
        launch(args);
    }
    
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("✨ Student Grade Tracker");
        
        // Create main layout with gradient background
        StackPane root = new StackPane();
        root.setStyle("""
            -fx-background: linear-gradient(135deg, 
                #667eea 0%, 
                #764ba2 100%);
        """);
        
        // Create glass morphism container
        VBox mainContainer = createMainContainer();
        root.getChildren().add(mainContainer);
        
        Scene scene = new Scene(root, 1000, 700);
        scene.setFill(Color.TRANSPARENT);
        
        primaryStage.setScene(scene);
        primaryStage.show();
        
        // Add entrance animation
        addEntranceAnimation(mainContainer);
        
        // Initialize with sample data
        addSampleData();
    }
    
    private VBox createMainContainer() {
        VBox container = new VBox(20);
        container.setPadding(new Insets(30));
        container.setAlignment(Pos.CENTER);
        container.setMaxWidth(900);
        container.setStyle("""
            -fx-background: linear-gradient(135deg, 
                rgba(255, 255, 255, 0.15) 0%, 
                rgba(255, 255, 255, 0.08) 100%);
            -fx-background-radius: 20;
            -fx-border-radius: 20;
            -fx-border-color: rgba(255, 255, 255, 0.3);
            -fx-border-width: 1;
            -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.3), 20, 0, 0, 10);
        """);
        
        // Title with improved readability
        Label titleLabel = new Label("🎓 Student Grade Tracker");
        titleLabel.setStyle("""
            -fx-font-size: 36px;
            -fx-font-weight: bold;
            -fx-text-fill: #ffffff;
            -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.8), 8, 0, 0, 3);
        """);
        
        // Create sections
        HBox topSection = new HBox(30);
        topSection.setAlignment(Pos.CENTER);
        
        VBox inputSection = createInputSection();
        summaryBox = createSummarySection();
        
        topSection.getChildren().addAll(inputSection, summaryBox);
        
        VBox tableSection = createTableSection();
        
        container.getChildren().addAll(titleLabel, topSection, tableSection);
        return container;
    }
    
    private VBox createInputSection() {
        VBox inputBox = new VBox(15);
        inputBox.setPadding(new Insets(25));
        inputBox.setAlignment(Pos.TOP_LEFT);
        inputBox.setPrefWidth(350);
        inputBox.setStyle("""
            -fx-background: linear-gradient(145deg, 
                rgba(255, 255, 255, 0.2) 0%, 
                rgba(255, 255, 255, 0.1) 100%);
            -fx-background-radius: 15;
            -fx-border-radius: 15;
            -fx-border-color: rgba(255, 255, 255, 0.4);
            -fx-border-width: 1;
            -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.2), 10, 0, 0, 5);
        """);
        
        Label titleLabel = new Label("➕ Add New Student");
        titleLabel.setStyle("""
            -fx-font-size: 20px;
            -fx-font-weight: bold;
            -fx-text-fill: #ffffff;
            -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.8), 5, 0, 0, 2);
        """);
        
        // Name input with improved readability
        Label nameLabel = new Label("Student Name");
        nameLabel.setStyle("""
            -fx-text-fill: #ffffff;
            -fx-font-weight: bold;
            -fx-font-size: 15px;
            -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.6), 3, 0, 0, 1);
        """);
        
        nameField = new TextField();
        nameField.setPromptText("Enter student name...");
        styleTextField(nameField);
        
        Label gradeLabel = new Label("Grade (0-100)");
        gradeLabel.setStyle("""
            -fx-text-fill: #ffffff;
            -fx-font-weight: bold;
            -fx-font-size: 15px;
            -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.6), 3, 0, 0, 1);
        """);
        
        gradeField = new TextField();
        gradeField.setPromptText("Enter grade...");
        styleTextField(gradeField);
        
        // Modern buttons
        HBox buttonBox = new HBox(15);
        buttonBox.setAlignment(Pos.CENTER);
        
        Button addButton = createStyledButton("Add Student", "#4CAF50", "#45a049");
        addButton.setOnAction(e -> addStudentWithAnimation());
        
        Button clearButton = createStyledButton("Clear", "#f44336", "#da372c");
        clearButton.setOnAction(e -> clearFieldsWithAnimation());
        
        buttonBox.getChildren().addAll(addButton, clearButton);
        
        inputBox.getChildren().addAll(titleLabel, nameLabel, nameField, gradeLabel, gradeField, buttonBox);
        return inputBox;
    }
    
    private void styleTextField(TextField field) {
        field.setStyle("""
            -fx-background-color: rgba(255, 255, 255, 0.25);
            -fx-border-color: rgba(255, 255, 255, 0.5);
            -fx-border-radius: 8;
            -fx-background-radius: 8;
            -fx-text-fill: #ffffff;
            -fx-prompt-text-fill: rgba(255, 255, 255, 0.8);
            -fx-font-size: 15px;
            -fx-font-weight: bold;
            -fx-padding: 12;
            -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.3), 3, 0, 0, 1);
        """);
        
        // Add focus effects
        field.focusedProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal) {
                field.setStyle(field.getStyle() + """
                    -fx-border-color: #64B5F6;
                    -fx-border-width: 2;
                    -fx-effect: dropshadow(gaussian, #64B5F6, 8, 0, 0, 0);
                """);
            } else {
                styleTextField(field);
            }
        });
    }
    
    private Button createStyledButton(String text, String baseColor, String hoverColor) {
        Button button = new Button(text);
        button.setPrefWidth(120);
        button.setPrefHeight(45);
        button.setStyle(String.format("""
            -fx-background-color: %s;
            -fx-text-fill: #ffffff;
            -fx-font-weight: bold;
            -fx-font-size: 15px;
            -fx-background-radius: 20;
            -fx-border-radius: 20;
            -fx-cursor: hand;
            -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.4), 8, 0, 0, 3);
        """, baseColor));
        
        // Hover effects
        button.setOnMouseEntered(e -> {
            button.setStyle(button.getStyle().replace(baseColor, hoverColor));
            ScaleTransition st = new ScaleTransition(Duration.millis(100), button);
            st.setToX(1.05);
            st.setToY(1.05);
            st.play();
        });
        
        button.setOnMouseExited(e -> {
            button.setStyle(button.getStyle().replace(hoverColor, baseColor));
            ScaleTransition st = new ScaleTransition(Duration.millis(100), button);
            st.setToX(1.0);
            st.setToY(1.0);
            st.play();
        });
        
        return button;
    }
    
    private VBox createSummarySection() {
        VBox summaryBox = new VBox(15);
        summaryBox.setPadding(new Insets(25));
        summaryBox.setPrefWidth(350);
        summaryBox.setStyle("""
            -fx-background: linear-gradient(145deg, 
                rgba(76, 175, 80, 0.3) 0%, 
                rgba(139, 195, 74, 0.2) 100%);
            -fx-background-radius: 15;
            -fx-border-radius: 15;
            -fx-border-color: rgba(76, 175, 80, 0.6);
            -fx-border-width: 1;
            -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.2), 10, 0, 0, 5);
        """);
        
        Label summaryTitle = new Label("📊 Statistics");
        summaryTitle.setStyle("""
            -fx-font-size: 20px;
            -fx-font-weight: bold;
            -fx-text-fill: #ffffff;
            -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.8), 5, 0, 0, 2);
        """);
        
        totalStudentsLabel = createStatLabel("👥 Total Students: 0");
        avgLabel = createStatLabel("📈 Average: 0.0");
        highestLabel = createStatLabel("🏆 Highest: 0.0");
        lowestLabel = createStatLabel("📉 Lowest: 0.0");
        
        Button refreshButton = createStyledButton("Refresh", "#2196F3", "#1976D2");
        refreshButton.setPrefWidth(150);
        refreshButton.setOnAction(e -> updateSummaryWithAnimation());
        
        summaryBox.getChildren().addAll(summaryTitle, totalStudentsLabel, avgLabel, 
                                       highestLabel, lowestLabel, refreshButton);
        return summaryBox;
    }
    
    private Label createStatLabel(String text) {
        Label label = new Label(text);
        label.setStyle("""
            -fx-text-fill: #ffffff;
            -fx-font-size: 16px;
            -fx-font-weight: bold;
            -fx-padding: 10;
            -fx-background-color: rgba(255, 255, 255, 0.2);
            -fx-background-radius: 8;
            -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.4), 5, 0, 0, 2);
        """);
        return label;
    }
    
    private VBox createTableSection() {
        VBox tableBox = new VBox(15);
        tableBox.setPadding(new Insets(20, 0, 0, 0));
        
        Label tableTitle = new Label("📋 Student Records");
        tableTitle.setStyle("""
            -fx-font-size: 22px;
            -fx-font-weight: bold;
            -fx-text-fill: #ffffff;
            -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.8), 5, 0, 0, 2);
        """);
        
        studentTable = new TableView<>();
        studentTable.setItems(studentData);
        studentTable.setPrefHeight(300);
        
        // Style the table with better readability
        studentTable.setStyle("""
            -fx-background-color: rgba(255, 255, 255, 0.2);
            -fx-background-radius: 10;
            -fx-border-radius: 10;
            -fx-border-color: rgba(255, 255, 255, 0.4);
            -fx-border-width: 1;
            -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.2), 10, 0, 0, 5);
            -fx-font-size: 14px;
            -fx-font-weight: bold;
        """);
        
        // Create styled columns with better text contrast
        TableColumn<Student, String> nameColumn = new TableColumn<>("👤 Name");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        nameColumn.setPrefWidth(250);
        styleColumn(nameColumn);
        
        TableColumn<Student, Double> gradeColumn = new TableColumn<>("📝 Grade");
        gradeColumn.setCellValueFactory(new PropertyValueFactory<>("grade"));
        gradeColumn.setPrefWidth(150);
        styleColumn(gradeColumn);
        
        TableColumn<Student, String> letterGradeColumn = new TableColumn<>("🎯 Letter");
        letterGradeColumn.setCellValueFactory(new PropertyValueFactory<>("letterGrade"));
        letterGradeColumn.setPrefWidth(150);
        styleColumn(letterGradeColumn);
        
        // Custom cell factory for better text readability in table cells
        nameColumn.setCellFactory(column -> {
            return new TableCell<Student, String>() {
                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty || item == null) {
                        setText(null);
                        setStyle("");
                    } else {
                        setText(item);
                        setStyle("""
                            -fx-text-fill: #ffffff;
                            -fx-font-weight: bold;
                            -fx-font-size: 14px;
                            -fx-background-color: transparent;
                        """);
                    }
                }
            };
        });
        
        gradeColumn.setCellFactory(column -> {
            return new TableCell<Student, Double>() {
                @Override
                protected void updateItem(Double item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty || item == null) {
                        setText(null);
                        setStyle("");
                    } else {
                        setText(String.format("%.1f", item));
                        setStyle("""
                            -fx-text-fill: #ffffff;
                            -fx-font-weight: bold;
                            -fx-font-size: 14px;
                            -fx-background-color: transparent;
                            -fx-alignment: CENTER;
                        """);
                    }
                }
            };
        });
        
        letterGradeColumn.setCellFactory(column -> {
            return new TableCell<Student, String>() {
                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty || item == null) {
                        setText(null);
                        setStyle("");
                    } else {
                        setText(item);
                        String color = getGradeColor(item);
                        setStyle(String.format("""
                            -fx-text-fill: %s;
                            -fx-font-weight: bold;
                            -fx-font-size: 16px;
                            -fx-background-color: transparent;
                            -fx-alignment: CENTER;
                        """, color));
                    }
                }
            };
        });
        
        studentTable.getColumns().addAll(nameColumn, gradeColumn, letterGradeColumn);
        
        Button deleteButton = createStyledButton("Delete Selected", "#FF5722", "#E64A19");
        deleteButton.setPrefWidth(200);
        deleteButton.setOnAction(e -> deleteSelectedWithAnimation());
        
        HBox buttonContainer = new HBox();
        buttonContainer.setAlignment(Pos.CENTER);
        buttonContainer.getChildren().add(deleteButton);
        
        tableBox.getChildren().addAll(tableTitle, studentTable, buttonContainer);
        return tableBox;
    }
    
    private String getGradeColor(String letterGrade) {
        return switch (letterGrade) {
            case "A" -> "#4CAF50"; // Green
            case "B" -> "#8BC34A"; // Light Green
            case "C" -> "#FFC107"; // Yellow
            case "D" -> "#FF9800"; // Orange
            case "F" -> "#F44336"; // Red
            default -> "#ffffff";
        };
    }
    
    private void styleColumn(TableColumn<Student, ?> column) {
        column.setStyle("""
            -fx-text-fill: #ffffff;
            -fx-font-weight: bold;
            -fx-font-size: 16px;
            -fx-alignment: CENTER;
            -fx-background-color: rgba(255, 255, 255, 0.1);
        """);
    }
    
    private void addStudentWithAnimation() {
        try {
            String name = nameField.getText().trim();
            String gradeText = gradeField.getText().trim();
            
            if (name.isEmpty() || gradeText.isEmpty()) {
                showStyledAlert("Error", "Please fill in all fields!", Alert.AlertType.ERROR);
                return;
            }
            
            double grade = Double.parseDouble(gradeText);
            
            if (grade < 0 || grade > 100) {
                showStyledAlert("Error", "Grade must be between 0 and 100!", Alert.AlertType.ERROR);
                return;
            }
            
            Student student = new Student(name, grade);
            students.add(student);
            studentData.add(student);
            
            clearFieldsWithAnimation();
            updateSummaryWithAnimation();
            
            showStyledAlert("Success", "Student added successfully! ✨", Alert.AlertType.INFORMATION);
            
            // Add row animation
            animateTableRow();
            
        } catch (NumberFormatException e) {
            showStyledAlert("Error", "Please enter a valid number for grade!", Alert.AlertType.ERROR);
        }
    }
    
    private void deleteSelectedWithAnimation() {
        Student selected = studentTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            // Fade out animation
            FadeTransition fade = new FadeTransition(Duration.millis(300), studentTable);
            fade.setToValue(0.3);
            fade.setOnFinished(e -> {
                students.remove(selected);
                studentData.remove(selected);
                updateSummaryWithAnimation();
                
                FadeTransition fadeIn = new FadeTransition(Duration.millis(300), studentTable);
                fadeIn.setToValue(1.0);
                fadeIn.play();
            });
            fade.play();
        } else {
            showStyledAlert("Warning", "Please select a student to delete!", Alert.AlertType.WARNING);
        }
    }
    
    private void clearFieldsWithAnimation() {
        // Animate field clearing
        Timeline timeline = new Timeline();
        timeline.getKeyFrames().addAll(
            new KeyFrame(Duration.ZERO, new KeyValue(nameField.opacityProperty(), 1.0)),
            new KeyFrame(Duration.millis(150), new KeyValue(nameField.opacityProperty(), 0.0)),
            new KeyFrame(Duration.millis(300), new KeyValue(nameField.opacityProperty(), 1.0))
        );
        timeline.setOnFinished(e -> {
            nameField.clear();
            gradeField.clear();
        });
        timeline.play();
        
        // Same for grade field
        Timeline timeline2 = new Timeline();
        timeline2.getKeyFrames().addAll(
            new KeyFrame(Duration.ZERO, new KeyValue(gradeField.opacityProperty(), 1.0)),
            new KeyFrame(Duration.millis(150), new KeyValue(gradeField.opacityProperty(), 0.0)),
            new KeyFrame(Duration.millis(300), new KeyValue(gradeField.opacityProperty(), 1.0))
        );
        timeline2.play();
    }
    
    private void updateSummaryWithAnimation() {
        // Pulse animation for summary box
        ScaleTransition pulse = new ScaleTransition(Duration.millis(200), summaryBox);
        pulse.setToX(1.05);
        pulse.setToY(1.05);
        pulse.setAutoReverse(true);
        pulse.setCycleCount(2);
        pulse.play();
        
        if (students.isEmpty()) {
            totalStudentsLabel.setText("👥 Total Students: 0");
            avgLabel.setText("📈 Average: 0.0");
            highestLabel.setText("🏆 Highest: 0.0");
            lowestLabel.setText("📉 Lowest: 0.0");
            return;
        }
        
        double sum = 0;
        double highest = students.get(0).getGrade();
        double lowest = students.get(0).getGrade();
        
        for (Student student : students) {
            double grade = student.getGrade();
            sum += grade;
            if (grade > highest) highest = grade;
            if (grade < lowest) lowest = grade;
        }
        
        double average = sum / students.size();
        
        totalStudentsLabel.setText("👥 Total Students: " + students.size());
        avgLabel.setText(String.format("📈 Average: %.2f", average));
        highestLabel.setText(String.format("🏆 Highest: %.2f", highest));
        lowestLabel.setText(String.format("📉 Lowest: %.2f", lowest));
    }
    
    private void animateTableRow() {
        if (!studentTable.getItems().isEmpty()) {
            studentTable.getSelectionModel().selectLast();
            studentTable.scrollTo(studentTable.getItems().size() - 1);
        }
    }
    
    private void addEntranceAnimation(VBox container) {
        container.setOpacity(0);
        container.setTranslateY(50);
        
        FadeTransition fade = new FadeTransition(Duration.millis(800), container);
        fade.setToValue(1.0);
        
        TranslateTransition slide = new TranslateTransition(Duration.millis(800), container);
        slide.setToY(0);
        
        ParallelTransition entrance = new ParallelTransition(fade, slide);
        entrance.play();
    }
    
    private void addSampleData() {
        students.add(new Student("Alice Johnson", 92.5));
        students.add(new Student("Bob Smith", 87.0));
        students.add(new Student("Carol Brown", 94.5));
        students.add(new Student("David Wilson", 78.5));
        students.add(new Student("Emma Davis", 89.0));
        
        studentData.addAll(students);
        updateSummaryWithAnimation();
    }
    
    private void showStyledAlert(String title, String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        
        // Style the alert with better readability
        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.setStyle("""
            -fx-background-color: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            -fx-text-fill: #ffffff;
            -fx-font-size: 14px;
            -fx-font-weight: bold;
        """);
        
        // Style alert text
        dialogPane.lookup(".content.label").setStyle("""
            -fx-text-fill: #ffffff;
            -fx-font-size: 15px;
            -fx-font-weight: bold;
        """);
        
        alert.showAndWait();
    }
    
    public static class Student {
        private String name;
        private double grade;
        private String letterGrade;
        
        public Student(String name, double grade) {
            this.name = name;
            this.grade = grade;
            this.letterGrade = calculateLetterGrade(grade);
        }
        
        private String calculateLetterGrade(double grade) {
            if (grade >= 90) return "A";
            else if (grade >= 80) return "B";
            else if (grade >= 70) return "C";
            else if (grade >= 60) return "D";
            else return "F";
        }
        
        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
        
        public double getGrade() { return grade; }
        public void setGrade(double grade) { 
            this.grade = grade; 
            this.letterGrade = calculateLetterGrade(grade);
        }
        
        public String getLetterGrade() { return letterGrade; }
        
        @Override
        public String toString() {
            return String.format("%s: %.2f (%s)", name, grade, letterGrade);
        }
    }
}