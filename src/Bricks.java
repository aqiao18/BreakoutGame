import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Bricks class: Responsible for the attributes of each independent brick
 * @author Alex Qiao
 */
public class Bricks {
    public static final int BRICK_WIDTH = 60, BRICK_HEIGHT = 15;

    private ImageView brickIV;
    private int lives, brickPoints;
    private Boolean hasPowerUp;
    private Boolean visible = false;

    /**
     * Bricks Constructor: Determines the display attributes and whether the brick has a power-up, used by the
     * BrickLayout class.
     * @param imageFile
     * @param xLoc
     * @param yLoc
     * @param powerUp
     */
    public Bricks(String imageFile, int xLoc, int yLoc, Boolean powerUp) {
        hasPowerUp = powerUp;
        brickIV = new ImageView(new Image(this.getClass().getClassLoader().getResourceAsStream(imageFile)));
        this.setLivesPoints(imageFile);
        this.brickIV.setX(xLoc);
        this.brickIV.setY(yLoc);
        this.brickIV.setFitWidth(BRICK_WIDTH);
        this.brickIV.setFitHeight(BRICK_HEIGHT);
    }

    /**
     * Sets the number of lives attribute of the brick
     */
    public void setLivesPoints(String imageFile) {
        if (imageFile.equals("brick3.gif")) {
            lives = 1;
            brickPoints = 100;
        } else if (imageFile.equals("brick1.gif")) {
            lives = 2;
            brickPoints = 200;
        } else if (imageFile.equals("brick4.gif")) {
            lives = 3;
            brickPoints = 300;
        }
    }

    /**
     * Returns the ImageView of the brick
     * @return
     */
    public ImageView getBrickIV () {
        return this.brickIV;
    }

    /**
     * Decreases the life of a brick after collision
     */
    public void decreaseLife() {
        this.lives--;
    }

    /**
     * Returns the remaining lives of the brick
     * @return
     */
    public int checkBrickLife() {
        return this.lives;
    }

    /**
     * Returns the number of points the brick is worth
     * @return
     */
    public int getBrickPoints() { return this.brickPoints; }

    /**
     * Returns whether the brick has a power-up
     * @return
     */
    public boolean checkForPowerUp() { return this.hasPowerUp; }

    /**
     * Determines if the brick is visible
     * @return
     */
    public boolean checkForVisibility() { return this.visible; }

    /**
     * Sets the visibility attribute to true
     */
    public void setVisibility() {
        this.visible = true;
    }
}
