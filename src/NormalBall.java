import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

/**
 * NormalBall class: Manages everything that happens to the ball including collisions and lives.
 * @author Alex Qiao
 */
public class NormalBall {
    public static final int BALL_SPEED = 120;

    private ImageView iv;
    private int X_DIRECTION;
    private int Y_DIRECTION;
    private int ballLives;
    private boolean animationsPaused;

    /**
     * NormalBall constructor
     */
    public NormalBall () {
        X_DIRECTION = 1;
        Y_DIRECTION = 1;
        ballLives = 3;
        animationsPaused = false;
    }

    /**
     * Returns the ImageView of the ball
     * @param file
     * @param windowWidth
     * @param windowHeight
     */
    public ImageView spawnNormalBall (String file, int windowWidth, int windowHeight){
        Image nBall = new Image(this.getClass().getClassLoader().getResourceAsStream(file));
        iv = new ImageView(nBall);
        iv.setX(windowWidth/2 - 10);
        iv.setY(windowHeight - 27);
        iv.setFitWidth(15);
        iv.setFitHeight(15);
        return iv;
    }

    /**
     * Getter method for the ball
     * @return
     */
    public ImageView getNormalBallIV () {
        return this.iv;
    }

    /**
     * Updates everything that happens to the ball, including position and collisions
     * @param elapsedTime
     * @param windowWidth
     * @param windowHeight
     * @param paddleX
     * @param bricks
     * @param myPaddle
     * @param animations
     * @param lose
     */
    public NormalBall stepUpdateNormalBall(double elapsedTime, int windowWidth, int windowHeight,
                                           double paddleX, BrickLayout bricks,
                                           Paddle myPaddle, Timeline animations, Text lose) {
        this.updatePosition(elapsedTime);
        this.checkFallOff(windowWidth, windowHeight, myPaddle, animations, lose);
        this.checkWallCollision(windowWidth);
        this.checkPaddleCollision(paddleX, myPaddle);
        bricks.checkBrickCollision(this);

        return this;
    }

    /**
     * Resets the ball size
     */
    public void resetBallSize() {
        this.getNormalBallIV().setFitWidth(15);
        this.getNormalBallIV().setFitHeight(15);
    }

    /**
     * Check if the ball fell off the bottom side
     * @param windowWidth
     * @param windowHeight
     * @param myPaddle
     * @param animations
     * @param lose
     */
    private void checkFallOff(int windowWidth, int windowHeight, Paddle myPaddle,
                              Timeline animations, Text lose) {
        if (this.getNormalBallIV().getY() >= myPaddle.getPaddleIV().getY()) {
            this.decreaseBallLives();
            this.getNormalBallIV().setX(windowWidth/2 - 10);
            this.getNormalBallIV().setY(windowHeight - 27);
            myPaddle.getPaddleIV().setX(windowWidth/2 - 31);
            myPaddle.getPaddleIV().setY(windowHeight - 12);
            this.resetBallSize();
            Y_DIRECTION = -1;
            if (this.ballLives <= 0) {
                lose.setVisible(true);
                animations.stop();
            }
            else {
                animationsPaused = true;
                animations.pause();
            }
            animationsPaused = true;
            animations.pause();
        }
    }

    /**
     * Check paddle and ball collisions
     * @param paddleX
     * @param paddle
     */
    private void checkPaddleCollision(double paddleX, Paddle paddle) {
        if (this.iv.getBoundsInParent().intersects(paddle.getPaddleIV().getBoundsInParent())){
            if (this.iv.getX() >= (paddleX - 30) && this.iv.getX() <= (paddleX + paddle.getPaddleIV().getFitWidth()/2)){
                this.changeXDirection();
            }
            this.changeYDirection();
        }

    }

    /**
     * Updates the position of the ball
     * @param elapsedTime
     */
    public void updatePosition(double elapsedTime){
        this.iv.setX(this.iv.getX() + X_DIRECTION * BALL_SPEED * elapsedTime);
        this.iv.setY(this.iv.getY() - Y_DIRECTION * BALL_SPEED * elapsedTime);
    }

    /**
     * Check ball and wall collisions
     * @param windowWidth
     */
    public void checkWallCollision(int windowWidth){
        if (this.iv.getX() <= 0 || this.iv.getX() >= (windowWidth - 12)) {
            this.changeXDirection();
        }
        if (this.iv.getY() <= 0) {
            this.changeYDirection();
        }
    }

    /**
     * Changes the X direction
     */
    public void changeXDirection () { this.X_DIRECTION *= -1; }

    /**
     * Changes the Y direction
     */
    public void changeYDirection () { this.Y_DIRECTION *= -1; }

    /**
     * Decreases the number of ball lives by 1
     */
    public void decreaseBallLives () { this.ballLives--; }

    /**
     * Increases the number of ball lives by 1
     */
    public void increaseBallLives () { this.ballLives++; }

    /**
     * Reset Y direction to be positive 1
     */
    public void setY_DIRECTION() { this.Y_DIRECTION = 1; }

    /**
     * Returns the number of ball lives
     * @return
     */
    public int checkBallLives () { return this.ballLives; }

    /**
     * Check if the animations are paused
     * @return
     */
    public boolean checkAnimationsStatus() { return this.animationsPaused; }

    /**
     * Changes pause boolean to true, used by Breakout class
     */
    public void pauseAnimations() {
        this.animationsPaused = true;
    }

    /**
     * Changes pause boolean to false, used by Breakout class
     */
    public void playAnimations() {
        this.animationsPaused = false;
    }

    /**
     * Increase ball size, used by power-ups
     */
    public void increaseBallSize() {
        this.getNormalBallIV().setFitWidth((this.getNormalBallIV().getFitWidth()) * 2);
        this.getNormalBallIV().setFitHeight((this.getNormalBallIV().getFitHeight() * 2));
    }

    /**
     * Decrese ball size, used by power-ups
     */
    public void decreaseBallSize() {
        this.getNormalBallIV().setFitWidth(this.getNormalBallIV().getFitWidth() / 2);
        this.getNormalBallIV().setFitHeight(this.getNormalBallIV().getFitHeight() / 2);
    }
}
