package application;

import javafx.fxml.FXML;
import javafx.scene.text.Text;
import javafx.scene.shape.Circle;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;


import java.util.Random;

public class SampleController {
    @FXML private Text scoreText;
    @FXML private Text timerText;
    @FXML private Circle mole00, mole01, mole02, mole10, mole11, mole12, mole20, mole21, mole22;

    private int score = 0;
    private int timeLeft = 30; // Game time
    private Timeline gameTimer;
    private Timeline moleMovement;

    @FXML
    private void initialize() {
        // Initialize score and timer
        scoreText.setText("Score: " + score);
        timerText.setText("Time: " + timeLeft);

        // Set up timeline for game timer
        gameTimer = new Timeline(new KeyFrame(Duration.seconds(1), e -> updateTimer()));
        gameTimer.setCycleCount(Timeline.INDEFINITE);
        gameTimer.play();

        // Set up timeline for mole movement
        moleMovement = new Timeline(new KeyFrame(Duration.seconds(1), e -> moveMole()));
        moleMovement.setCycleCount(Timeline.INDEFINITE);
        moleMovement.play();
        
    }

    
    private void updateTimer() {
        if (timeLeft > 0) {
            timeLeft--;
            timerText.setText("Time: " + timeLeft);
        } else {
            endGame();
        }
    }

    private void moveMole() {
        Random random = new Random();
        int row = random.nextInt(3);
        int col = random.nextInt(3);

        // Reset all moles to gray before setting a new one
        resetMoles();

        // Get a random mole and set its color to brown
        Circle mole = getMoleByCoordinates(row, col);
        if (mole != null) {
            mole.setFill(javafx.scene.paint.Color.BROWN);
        }
    }

    // Reset all moles to gray
    private void resetMoles() {
        mole00.setFill(javafx.scene.paint.Color.GRAY);
        mole01.setFill(javafx.scene.paint.Color.GRAY);
        mole02.setFill(javafx.scene.paint.Color.GRAY);
        mole10.setFill(javafx.scene.paint.Color.GRAY);
        mole11.setFill(javafx.scene.paint.Color.GRAY);
        mole12.setFill(javafx.scene.paint.Color.GRAY);
        mole20.setFill(javafx.scene.paint.Color.GRAY);
        mole21.setFill(javafx.scene.paint.Color.GRAY);
        mole22.setFill(javafx.scene.paint.Color.GRAY);
    }

    private Circle getMoleByCoordinates(int row, int col) {
        if (row == 0 && col == 0) return mole00;
        if (row == 0 && col == 1) return mole01;
        if (row == 0 && col == 2) return mole02;
        if (row == 1 && col == 0) return mole10;
        if (row == 1 && col == 1) return mole11;
        if (row == 1 && col == 2) return mole12;
        if (row == 2 && col == 0) return mole20;
        if (row == 2 && col == 1) return mole21;
        if (row == 2 && col == 2) return mole22;
        
        System.out.println("Invalid mole coordinates: " + row + ", " + col); // Debugging message
        return mole00; // Default to mole00 to prevent crashes
    }

    @FXML
    private void whackMole(javafx.scene.input.MouseEvent event) {
        Circle clickedMole = (Circle) event.getSource(); // Get clicked mole dynamically
        if (clickedMole.getFill().equals(javafx.scene.paint.Color.BROWN)) {
            score++;
            scoreText.setText("Score: " + score);
            clickedMole.setFill(javafx.scene.paint.Color.GRAY); // Hide mole after click
        }
    }

    private void endGame() {
        timerText.setText("Time's up!");
        gameTimer.stop();  // Stop the game timer
        moleMovement.stop();  // Stop moles from appearing
        resetMoles(); // Hide all moles
    }
}
