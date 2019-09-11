import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.control.Button;
import java.util.ArrayList;

/**
 * Breakout Class: Manages the main game and all the updates within a game, main class.
 * @author Alex Qiao
 */
public class Breakout extends Application {
    public static final int WINDOW_HEIGHT = 475, WINDOW_WIDTH = 540, FRAMES_PER_SECOND = 60,
            MILLISECOND_DELAY = 1000 / FRAMES_PER_SECOND, PADDLE_SPEED = 5;
    public static final double SECOND_DELAY = 1.0 / FRAMES_PER_SECOND;
    public static final Paint BACKGROUND = Color.AZURE;
    public static final String NORMAL_BALL_IMAGE = "ball.gif", PADDLE_IMAGE = "paddle.gif",
            ONE_HIT_BRICK_IMAGE = "brick3.gif", TWO_HIT_BRICK_IMAGE = "brick1.gif",
            THREE_HIT_BRICK_IMAGE = "brick4.gif", GAME_TITLE = "Breakout Game";

    private Scene mySplashScreen;
    private NormalBall myNormalBall;
    private Paddle myPaddle;
    private BrickLayout myBrickFormat;
    private PowerUpLayout myPowerUpLayout;
    private ArrayList<Bricks> myBrickList, myDestroyedList;
    private ArrayList<PowerUps> myPowerUpList;
    private boolean startedGame;
    private LavaSquareSpawner mySpawner;
    private Text livesLeft;
    private Text win, lose, score;
    private StageLevel currStage = new StageLevel();
    private Timeline animation;
    private Stage primaryStage;

    /**
     * Function called by main in JavaFX
     * @param s
     */
    @Override
    public void start(Stage s) throws Exception {
        primaryStage = s;
        mySplashScreen = setupMenu(WINDOW_WIDTH, WINDOW_HEIGHT, BACKGROUND, primaryStage, 1);
        primaryStage.setTitle(GAME_TITLE);
        primaryStage.setResizable(false);
        primaryStage.setScene(mySplashScreen);
        primaryStage.show();
    }

    /**
     * Animates the game window
     * @param primaryStage
     */
    private void animateScene(Stage primaryStage) {
        startedGame = true;
        var frame = new KeyFrame(Duration.millis(MILLISECOND_DELAY), e -> step(SECOND_DELAY, primaryStage));
        animation = new Timeline();
        animation.setCycleCount(Timeline.INDEFINITE);
        animation.getKeyFrames().add(frame);
        animation.play();
    }

    /**
     * Sets up the opening menu screen with directions
     * @param windowWidth
     * @param windowHeight
     * @param background
     * @param primaryStage
     * @param currentLevel
     * @return
     */
    private Scene setupMenu(int windowWidth, int windowHeight, Paint background, Stage primaryStage, int currentLevel) {
        Group menuGroup = new Group();
        Button startGame = new Button("Click to Start");
        Text welcome = new Text();
        welcome.setText("Welcome to My Version of Breakout!");
        welcome.setX(14);
        welcome.setY(40);
        welcome.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 25));
        Text directions = new Text();
        directions.setText("\n\nPress SPACE to launch the ball from the paddle. \n\nPress A/LEFT and D/RIGHT" +
                " to move paddle left and right respectively. \n\nPressing W/UP and S/DOWN will work to move the " +
                "paddle \n\nup and down respectively after level 1. " +
                "\n\nLava squares spawn from the spawner on the bottom right of the screen, \n\navoid hitting them" +
                " at all cost! \n\nAfter all, it will melt your paddle away. \n\nThe lava squares can bounce on all " +
                "four walls with their smooth sides. \n\nThey can even bounce off of bricks, but all bricks are " +
                "tough enough to withstand \n\nthe heat and take no damage from the lava squares!" +
                "\n\nFinally, good powerups are small and fall quickly, \n\nbad ones are big" +
                " and fall slowly. \n\nGood luck!");
        directions.setX(14);
        directions.setY(60);
        directions.setFont(Font.font("verdana", FontWeight.NORMAL, FontPosture.REGULAR, 12));
        startGame.setLayoutX(windowWidth/2 - 40);
        startGame.setLayoutY(windowHeight - 40);
        startGame.setOnAction(e->primaryStage.setScene(setupGame(windowWidth, windowHeight, background,
                primaryStage, currentLevel)));
        Scene menu = new Scene(menuGroup, windowWidth, windowHeight, background);
        menuGroup.getChildren().addAll(startGame, welcome, directions);
        return menu;
    }

    /**
     * Sets up the game screens for each level
     * @param windowWidth
     * @param windowHeight
     * @param background
     * @param primaryStage
     * @param currentLevel
     * @return
     */
    public Scene setupGame(int windowWidth, int windowHeight, Paint background, Stage primaryStage, int currentLevel) {
        startedGame = false;
        Group root = new Group();
        myNormalBall = new NormalBall();
        myNormalBall.spawnNormalBall(NORMAL_BALL_IMAGE, windowWidth, windowHeight);
        myPaddle = new Paddle();
        myPaddle.spawnPaddle(PADDLE_IMAGE, WINDOW_WIDTH, WINDOW_HEIGHT);
        myBrickFormat = new BrickLayout();
        myBrickList = myBrickFormat.brickFormation(myBrickFormat.setStage(currentLevel),
                ONE_HIT_BRICK_IMAGE, TWO_HIT_BRICK_IMAGE, THREE_HIT_BRICK_IMAGE);
        myDestroyedList = new ArrayList<>();
        myBrickFormat.generateBricks(myBrickList);
        myPowerUpLayout = new PowerUpLayout();
        myPowerUpList = myPowerUpLayout.powerUpFormation(myBrickFormat.setStage(currentLevel));
        myPowerUpLayout.generatePowerUps(myPowerUpList);
        mySpawner = new LavaSquareSpawner(windowWidth, windowHeight);
        for (int i = 0; i < currentLevel; i++) {
            if (i == 0) {
                mySpawner.getLavaSquareList().add(new LavaSquare(WINDOW_WIDTH, WINDOW_HEIGHT, 1, 1));
            } else if (i == 1) {
                mySpawner.getLavaSquareList().add(new LavaSquare(WINDOW_WIDTH, WINDOW_HEIGHT, -1, 1));
            } else if (i == 2) {
                mySpawner.getLavaSquareList().add(new LavaSquare(WINDOW_WIDTH, WINDOW_HEIGHT, -1, -1));
            }
        }
        livesLeft = writeText(15, 25,
                12, "verdana","Lives: " + myNormalBall.checkBallLives());
        score = writeText(windowWidth - 90, 25, 12,
                "verdana", "Score: " + myBrickFormat.getTotalPoints());
        win = writeText(180, 50, 12, "verdana", "You win! Press ESC to exit.");
        lose = writeText(180, 50, 12, "verdana", "You lost! Press ESC to exit.");
        rootAddAll(root);
        Scene game = new Scene(root, windowWidth, windowHeight, background);
        game.setOnKeyPressed(e -> handleKeyInput(e.getCode(), primaryStage));
        return game;
    }

    /**
     * Helper method to create simple text objects
     * @param xPos
     * @param yPos
     * @param fontSize
     * @param font
     * @param message
     * @return
     */
    private Text writeText(int xPos, int yPos, int fontSize, String font, String message) {
        Text text = new Text();
        text.setText(message);
        text.setX(xPos);
        text.setY(yPos);
        text.setFont(Font.font("verdana", FontWeight.NORMAL, FontPosture.REGULAR, fontSize));
        return text;
    }

    /**
     * Helper method to add all to root
     *
     * @param root
     */
    public void rootAddAll(Group root) {
        root.getChildren().add(myNormalBall.getNormalBallIV());
        root.getChildren().add(myPaddle.getPaddleIV());
        for (int i = 0; i < myBrickList.size(); i++) {
            root.getChildren().add(myBrickList.get(i).getBrickIV());
        }
        for (int i = 0; i < myPowerUpList.size(); i++) {
            root.getChildren().add(myPowerUpList.get(i).getPowerUpIV());
        }
        for (int i = 0; i < mySpawner.getLavaSquareList().size(); i++) {
            root.getChildren().add(mySpawner.getLavaSquareList().get(i).getLavaIV());
        }
        root.getChildren().add(mySpawner.getSpawnerIV());
        root.getChildren().addAll(livesLeft, score, win, lose);
        win.setVisible(false);
        lose.setVisible(false);
    }

    /**
     * Updates game window continuously and checks for collisions/game screen changes.
     * @param secondDelay
     * @param s
     */
    public void step(double secondDelay, Stage s) {
        if (myBrickFormat.checkFinishedStage()) {
            if (currStage.nextStage(animation, win, startedGame, 0)) {
                setNewScene();
            };
        }
        if (myNormalBall.checkBallLives() == 0) {
            lose.setVisible(true);
            animation.stop();
        }
        for (int i = 0; i < mySpawner.getLavaSquareList().size(); i++) {
            if (i >= 0) {
                mySpawner.getLavaSquareList().get(0).updateLavaSquares(secondDelay, WINDOW_WIDTH, WINDOW_HEIGHT,
                        myPaddle, lose, animation, myBrickList);
            }
            if (mySpawner.getLavaSquareList().size() >= 2 && mySpawner.getLavaSquareList().get(0).checkCollision()) {
                mySpawner.getLavaSquareList().get(1).updateLavaSquares(secondDelay, WINDOW_WIDTH, WINDOW_HEIGHT,
                        myPaddle, lose, animation, myBrickList);
            }
            if (mySpawner.getLavaSquareList().size() >= 3 && mySpawner.getLavaSquareList().get(1).checkCollision()) {
                mySpawner.getLavaSquareList().get(2).updateLavaSquares(secondDelay, WINDOW_WIDTH, WINDOW_HEIGHT,
                        myPaddle, lose, animation, myBrickList);
            }
        }
        myNormalBall.stepUpdateNormalBall(secondDelay, WINDOW_WIDTH, WINDOW_HEIGHT, myPaddle.getPaddleIV().getX(),
                myBrickFormat, myPaddle, animation, lose);
        myDestroyedList = myBrickFormat.getDestroyedBrickList();
        myPowerUpLayout = myPowerUpLayout.checkRelease(myDestroyedList);
        myPowerUpLayout = myPowerUpLayout.updatePowerUpPos(secondDelay, myDestroyedList);
        myPowerUpLayout = myPowerUpLayout.checkPaddleCatch(myNormalBall, myPaddle, myBrickFormat);
        livesLeft.setText("Lives: " + myNormalBall.checkBallLives());
        score.setText("Score: " + myBrickFormat.getTotalPoints());
    }

    /**
     * Sets up a new scene for level changes.
     */
    public void setNewScene() {
        Scene newScene = setupGame(WINDOW_WIDTH, WINDOW_HEIGHT, BACKGROUND, primaryStage, currStage.getCurrentLevel());
        primaryStage.setScene(newScene);
    }

    /**
     * Handles all key inputs entered by the player.
     * @param primaryStage
     * @param code
     */
    private void handleKeyInput (KeyCode code, Stage primaryStage) {
        if ((code == KeyCode.RIGHT || code == KeyCode.D)) {
            if ((myPaddle.getPaddleIV().getX() < (WINDOW_WIDTH - myPaddle.getPaddleIV().getFitWidth()))) {
                if (myNormalBall.checkAnimationsStatus() || !startedGame) {
                    myNormalBall.getNormalBallIV().setX(myNormalBall.getNormalBallIV().getX() + PADDLE_SPEED);
                }
                myPaddle.getPaddleIV().setX(myPaddle.getPaddleIV().getX() + PADDLE_SPEED);
            }
            else {
                if (myNormalBall.checkAnimationsStatus() || !startedGame) {
                    myNormalBall.getNormalBallIV().setX((myPaddle.getPaddleIV().getFitWidth()/2) - 8);
                }
                myPaddle.getPaddleIV().setX(0);
            }
        } else if ((code == KeyCode.LEFT || code == KeyCode.A)) {
            if ((myPaddle.getPaddleIV().getX() > 0)) {
                if (myNormalBall.checkAnimationsStatus() || !startedGame) {
                    myNormalBall.getNormalBallIV().setX(myNormalBall.getNormalBallIV().getX() - PADDLE_SPEED);
                }
                myPaddle.getPaddleIV().setX(myPaddle.getPaddleIV().getX() - PADDLE_SPEED);
            }
            else {
                if (myNormalBall.checkAnimationsStatus() || !startedGame) {
                    myNormalBall.getNormalBallIV().setX((WINDOW_WIDTH - myPaddle.getPaddleWidth()/2) - 8);
                }
                myPaddle.getPaddleIV().setX(WINDOW_WIDTH - myPaddle.getPaddleWidth());

            }
        } else if ((code == KeyCode.SPACE)) {
            if (!startedGame) {
                startedGame = true;
                animateScene(primaryStage);
            }
            else{
                animation.play();
                myNormalBall.playAnimations();
            }
        } else if ((code == KeyCode.L)) {
            myNormalBall.increaseBallLives();
        } else if ((code == KeyCode.R)) {
            animation.pause();
            myNormalBall.pauseAnimations();
            myNormalBall.getNormalBallIV().setX(WINDOW_WIDTH/2 - 10);
            myNormalBall.getNormalBallIV().setY(WINDOW_HEIGHT - 27);
            myNormalBall.resetBallSize();
            myNormalBall.setY_DIRECTION();
            myPaddle.getPaddleIV().setX(WINDOW_WIDTH/2 - 31);
            myPaddle.getPaddleIV().setY(WINDOW_HEIGHT - 12);
            if (myPaddle.getPaddleIV().getFitWidth() == WINDOW_WIDTH) {
                myPaddle.getPaddleIV().setFitWidth(60);
            }
        } else if ((code == KeyCode.DIGIT1)) {
            if (currStage.nextStage(
                    animation, win, startedGame, 1)) {
                setNewScene();
            }
        } else if ((code == KeyCode.DIGIT2)) {
            if (currStage.nextStage(
                    animation, win, startedGame, 2)) {
                setNewScene();
            }
        } else if ((code == KeyCode.DIGIT3)) {
            if (currStage.nextStage(
                    animation, win, startedGame, 3)) {
                setNewScene();
            }
        } else if ((code == KeyCode.ESCAPE)) {
            Platform.exit();
            System.exit(0);
        } else if ((code == KeyCode.E)) {
            if (myPaddle.getPaddleIV().getFitWidth() == WINDOW_WIDTH) {
                myPaddle.getPaddleIV().setX(WINDOW_WIDTH/2 - 31);
                myPaddle.getPaddleIV().setFitWidth(60);
            }
            else {
                myPaddle.getPaddleIV().setX(0);
                myPaddle.getPaddleIV().setFitWidth(WINDOW_WIDTH);
            }
        } else if (((code == KeyCode.W || code == KeyCode.UP) && currStage.getCurrentLevel() >= 2)) {
            if (myNormalBall.checkAnimationsStatus() || !startedGame) {
                myNormalBall.getNormalBallIV().setY(myNormalBall.getNormalBallIV().getY() - PADDLE_SPEED);
            }
            myPaddle.getPaddleIV().setY(myPaddle.getPaddleIV().getY() - PADDLE_SPEED);
        } else if (((code == KeyCode.S || code == KeyCode.DOWN)) && currStage.getCurrentLevel() >= 2) {
            if (myNormalBall.checkAnimationsStatus() || !startedGame) {
                myNormalBall.getNormalBallIV().setY(myNormalBall.getNormalBallIV().getY() + PADDLE_SPEED);
            }
            myPaddle.getPaddleIV().setY(myPaddle.getPaddleIV().getY() + PADDLE_SPEED);
        }
    }
    /**
     * Main method
     *
     * @param args
     */
    public static void main(String args[]){
        launch(args);
    }
}