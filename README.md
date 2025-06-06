# Student Grade Tracker 🎓✨

A modern JavaFX application for tracking student grades with beautiful UI animations and visual effects.

![Application Screenshot](screenshot.png) *(Replace with actual screenshot)*

## Features 🌟

- **Glass Morphism UI**: Sleek, modern interface with translucent panels and gradient effects
- **Interactive Animations**: Smooth transitions and visual feedback for all actions
- **Student Management**:
  - Add new students with name and grade
  - Delete selected students
  - Clear input fields
- **Grade Statistics**:
  - Real-time calculation of average grade
  - Highest and lowest grade tracking
  - Total student count
- **Visual Grade Representation**:
  - Color-coded letter grades (A-F)
  - Responsive table with animated updates
- **Error Handling**: User-friendly alerts for invalid inputs

## Technologies Used 💻

- Java 17+
- JavaFX 17+
- CSS styling for modern UI effects
- JavaFX animations for interactive elements

## Installation & Setup ⚙️

### Prerequisites
- JDK 17 or later
- JavaFX SDK 17 or later

### Running the Application
1. Clone this repository
2. Ensure JavaFX is properly configured in your IDE or build tool
3. Run the `StudentGradeTracker` class

#### Command Line (with JavaFX modules):
```bash
javac --module-path path/to/javafx-sdk/lib --add-modules javafx.controls,javafx.fxml StudentGradeTracker.java
java --module-path path/to/javafx-sdk/lib --add-modules javafx.controls,javafx.fxml StudentGradeTracker
