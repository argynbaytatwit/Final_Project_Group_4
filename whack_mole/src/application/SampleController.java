package application;
 
import javafx.fxml.FXML;
import javafx.scene.text.Text;
import javafx.scene.shape.Circle;
import javafx.scene.control.Button;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;
import java.util.Random;
import javafx.scene.layout.StackPane;
 
public class SampleController {
 
    @FXML private Text scoreText;
    @FXML private Text timerText;
    @FXML private Text highScoreText; // New text field for highest score
    @FXML private Circle mole00, mole01, mole02, mole10, mole11, mole12, mole20, mole21, mole22;
    @FXML private Button startButton; // Button to start the game
    @FXML private StackPane gamePane;
 
    private int score = 0;
    private int timeLeft = 30; // Game time
    private int highScore = 0; // Track highest score
    private Timeline gameTimer;
    private Timeline moleMovement;
 
    @FXML
    private void initialize() {
        // Initialize score and timer
        String imagePath = getClass().getResource("/application/resources/Grass.jpg").toExternalForm();
        gamePane.setStyle("-fx-background-image: url('" + imagePath + "'); -fx-background-size: cover;");
       
        scoreText.setText("Score: " + score);
        timerText.setText("Time: " + timeLeft);
        highScoreText.setText("High Score: " + highScore); // Display high score
        startButton.setVisible(true); // Ensure the start button is visible initially
    }
 
    @FXML
    private void startGame() {
        // Hide the start button and reset game variables
        startButton.setVisible(false);
        score = 0;
        timeLeft = 30;
       
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
            mole.setFill(javafx.scene.paint.Color.SADDLEBROWN);
        }
    }
 
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
       
        if (clickedMole.getFill().equals(javafx.scene.paint.Color.SADDLEBROWN)) {
            score++;
            scoreText.setText("Score: " + score);
            clickedMole.setFill(javafx.scene.paint.Color.GRAY); // Hide mole after click
        }
    }
 
    private void endGame() {
        // Check if current score is the highest score
        if (score > highScore) {
            highScore = score;
            highScoreText.setText("High Score: " + highScore);
        }
 
        timerText.setText("Time's up! Score: ");
       
        gameTimer.stop(); // Stop the game timer
        moleMovement.stop(); // Stop moles from appearing
        resetMoles(); // Hide all moles
       
        startButton.setVisible(true); // Show the start button again to restart the game
    }
}