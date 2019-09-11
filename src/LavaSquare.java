import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

import java.util.ArrayList;

/**
 * LavaSquare class: Handles individual lava squares
 * @author Alex Qiao
 */
public class LavaSquare {

    public static final int SQUARE_SPEED = 45;
    public static final String LAVA_SQUARE_IMAGE = "brick10.gif";
    private int X_DIRECTION;
    private int Y_DIRECTION;
    private ImageView iv;
    private boolean releaseNow;
    private int collisionTracker;

    /**
     * Lava square constructor
     * @param windowWidth
     * @param windowHeight
     * @param X
     * @param Y
     */
    public LavaSquare(int windowWidth, int windowHeight, int X, int Y) {
        X_DIRECTION = X;
        Y_DIRECTION = Y;
        iv = new ImageView(
                new Image(this.getClass().getClassLoader().getResourceAsStream(LAVA_SQUARE_IMAGE)));
        iv.setFitWidth(15);
        iv.setFitHeight(15);
        iv.setX(windowWidth - 97.5);
        iv.setY(windowHeight -97.5);
        releaseNow = false;
        collisionTracker = 0;
    }

    /**
     * Returns ImageView of lava square
     * @return
     */
    public ImageView getLavaIV() {
        return this.iv;
    }

    public LavaSquare updateLavaSquares(double elapsedTime, int windowWidth, int windowHeight, Paddle paddle,
                                        Text lose, Timeline animations, ArrayList<Bricks> brickList) {
        this.updatePosition(elapsedTime);
        this.checkLavaWallCollisions(windowWidth, windowHeight);
        this.checkLavaPaddleCollision(paddle, lose, animations);
        this.checkLavaBrickCollisions(brickList);
        return this;
    }

    /**
     * Checks lava square and brick collisions
     * @param brickList
     */
    public void checkLavaBrickCollisions(ArrayList<Bricks> brickList) {
        for (int i = 0; i < brickList.size(); i++) {
            if (brickList.get(i).getBrickIV().getBoundsInParent()
                    .intersects(this.iv.getBoundsInParent())) {
                this.changeXLava();
                this.changeYLava();
                this.collisionTracker ++;
                if (this.collisionTracker >= 4) {
                    releaseNow = true;
                }
            }
        }
    }

    /**
     * Checks lava square and paddle collisions
     * @param paddle
     * @param lose
     * @param animations
     */
    private void checkLavaPaddleCollision(Paddle paddle, Text lose, Timeline animations) {
        if (this.iv.getBoundsInParent().intersects(paddle.getPaddleIV().getBoundsInParent())) {
            lose.setVisible(true);
            animations.stop();
        }
    }

    /**
     * Updates the position of the lava square
     * @param elapsedTime
     */
    public void updatePosition(double elapsedTime){
        this.iv.setX(this.iv.getX() + X_DIRECTION * SQUARE_SPEED * elapsedTime);
        this.iv.setY(this.iv.getY() - Y_DIRECTION * SQUARE_SPEED * elapsedTime);
    }

    /**
     * Checks lava brick and wall collisions.
     * @param windowWidth
     * @param windowHeight
     */
    public void checkLavaWallCollisions(int windowWidth, int windowHeight){
        if (this.iv.getX() <= 0 || this.iv.getX() >= (windowWidth - 12)) {
            this.changeXLava();
        }
        if (this.iv.getY() <= 0 || this.iv.getY() >= windowHeight - 12) {
            this.changeYLava();
        }
    }

    /**
     * Alters Y direction of lava square
     */
    private void changeYLava() {
        Y_DIRECTION *= -1;
    }

    /**
     * Alters X direction of lava square
     */
    private void changeXLava() {
        X_DIRECTION *= -1;
    }

    /**
     * Informs Breakout class when to release lava square
     * @return
     */
    public boolean checkCollision() {
        return this.releaseNow;
    }
}
