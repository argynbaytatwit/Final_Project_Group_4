package application;

import javafx.fxml.FXML;
import javafx.scene.text.Text;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.Button;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;
import java.util.Random;
import javafx.scene.layout.StackPane;

public class SampleController {

    @FXML private Text scoreText;
    @FXML private Text timerText;
    @FXML private Text highScoreText; // High score display
    @FXML private ImageView mole00, mole01, mole02, mole10, mole11, mole12, mole20, mole21, mole22;
    @FXML private Button startButton; // Start button
    @FXML private StackPane gamePane;

    private int score = 0;
    private int timeLeft = 30; // Game duration
    private int highScore = 0;
    private Timeline gameTimer;
    private Timeline moleMovement;

    private final Image moleImage = new Image(getClass().getResource("/application/resources/mole.png").toExternalForm());

    @FXML
    private void initialize() {
        // Set background image
        String imagePath = getClass().getResource("/application/resources/Grass.png").toExternalForm();
        gamePane.setStyle("-fx-background-image: url('" + imagePath + "'); -fx-background-size: cover;");

        // Initialize UI
        scoreText.setText("Score: " + score);
        timerText.setText("Time: " + timeLeft);
        highScoreText.setText("High Score: " + highScore);
        
        scoreText.setStyle("-fx-fill: white; -fx-font-weight: bold;");
        timerText.setStyle("-fx-fill: white; -fx-font-weight: bold;");
        highScoreText.setStyle("-fx-fill: white; -fx-font-weight: bold;");
        
        startButton.setVisible(true);

        // Set mole images initially
        setMoleImages();
        resetMoles(); // Hide all moles
    }

    private void setMoleImages() {
        mole00.setImage(moleImage);
        mole01.setImage(moleImage);
        mole02.setImage(moleImage);
        mole10.setImage(moleImage);
        mole11.setImage(moleImage);
        mole12.setImage(moleImage);
        mole20.setImage(moleImage);
        mole21.setImage(moleImage);
        mole22.setImage(moleImage);
    }

    @FXML
    private void startGame() {
        // Hide the start button and reset game variables
        startButton.setVisible(false);
        score = 0;
        timeLeft = 30;

        scoreText.setText("Score: " + score);
        timerText.setText("Time: " + timeLeft);

        // Start game timer
        gameTimer = new Timeline(new KeyFrame(Duration.seconds(1), e -> updateTimer()));
        gameTimer.setCycleCount(Timeline.INDEFINITE);
        gameTimer.play();

        // Start mole movement
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

        // Hide all moles before setting a new one
        resetMoles();

        // Get a random mole and make it visible
        ImageView mole = getMoleByCoordinates(row, col);
        if (mole != null) {
            mole.setVisible(true);
        }
    }

    private void resetMoles() {
        mole00.setVisible(false);
        mole01.setVisible(false);
        mole02.setVisible(false);
        mole10.setVisible(false);
        mole11.setVisible(false);
        mole12.setVisible(false);
        mole20.setVisible(false);
        mole21.setVisible(false);
        mole22.setVisible(false);
    }

    private ImageView getMoleByCoordinates(int row, int col) {
        if (row == 0 && col == 0) return mole00;
        if (row == 0 && col == 1) return mole01;
        if (row == 0 && col == 2) return mole02;
        if (row == 1 && col == 0) return mole10;
        if (row == 1 && col == 1) return mole11;
        if (row == 1 && col == 2) return mole12;
        if (row == 2 && col == 0) return mole20;
        if (row == 2 && col == 1) return mole21;
        if (row == 2 && col == 2) return mole22;

        System.out.println("Invalid mole coordinates: " + row + ", " + col);
        return mole00; // Default to prevent crashes
    }

    @FXML
    private void whackMole(javafx.scene.input.MouseEvent event) {
        ImageView clickedMole = (ImageView) event.getSource(); // Get clicked mole dynamically

        if (clickedMole.isVisible()) {
            score++;
            scoreText.setText("Score: " + score);
            clickedMole.setVisible(false); // Hide mole after clicking
        }
    }

    private void endGame() {
        // Check if current score is the highest score
        if (score > highScore) {
            highScore = score;
            highScoreText.setText("High Score: " + highScore);
        }

        timerText.setText("Time's up!");

        gameTimer.stop(); // Stop game timer
        moleMovement.stop(); // Stop mole movement
        resetMoles(); // Hide all moles

        startButton.setVisible(true); // Show start button again
    }
}