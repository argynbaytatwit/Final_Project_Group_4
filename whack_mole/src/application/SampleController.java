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

    @FXML
    private void initialize() {
        // Set initial score and timer
        scoreText.setText("Score: " + score);
        timerText.setText("Time: " + timeLeft);

        // Set up timeline for the timer
        Timeline gameTimer = new Timeline(new KeyFrame(Duration.seconds(1), e -> updateTimer()));
        gameTimer.setCycleCount(Timeline.INDEFINITE);
        gameTimer.play();

        // Set up timeline for mole movement
        Timeline moleMovement = new Timeline(new KeyFrame(Duration.seconds(1), e -> moveMole()));
        moleMovement.setCycleCount(Timeline.INDEFINITE);
        moleMovement.play();
        
        addClickHandlers();
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
        Circle mole = getMoleByCoordinates(row, col);
        mole.setFill(javafx.scene.paint.Color.GREEN);
    }

    private Circle getMoleByCoordinates(int row, int col) {
        switch (row) {
            case 0:
                switch (col) {
                    case 0: return mole00;
                    case 1: return mole01;
                    case 2: return mole02;
                }
                break;
            case 1:
                switch (col) {
                    case 0: return mole10;
                    case 1: return mole11;
                    case 2: return mole12;
                }
                break;
            case 2:
                switch (col) {
                    case 0: return mole20;
                    case 1: return mole21;
                    case 2: return mole22;
                }
                break;
        }
        return null;
    }

    @FXML
    private void whackMole(Circle mole) {
        if (mole.getFill() == javafx.scene.paint.Color.GREEN) {
            score++;
            scoreText.setText("Score: " + score);
            mole.setFill(javafx.scene.paint.Color.GRAY);
        }
    }
    private void addClickHandlers() {
    	// Add click event listeners for all moles
    	mole00.setOnMouseClicked(e -> whackMole(mole00));
    	mole01.setOnMouseClicked(e -> whackMole(mole01));
    	mole02.setOnMouseClicked(e -> whackMole(mole02));
    	mole10.setOnMouseClicked(e -> whackMole(mole10));
    	mole11.setOnMouseClicked(e -> whackMole(mole11));
    	mole12.setOnMouseClicked(e -> whackMole(mole12));
    	mole20.setOnMouseClicked(e -> whackMole(mole20));
    	mole21.setOnMouseClicked(e -> whackMole(mole21));
    	mole22.setOnMouseClicked(e -> whackMole(mole22));
    	}

    private void endGame() {
        // Display a message and reset the game
    }
}