import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * PowerUps class: Responsible for the individual power-ups of a brick.
 * @author Alex Qiao
 */
public class PowerUps {
    public static final int BUFF_SIZE = 15, NERF_SIZE = 20;
    private String powerUpType;
    private boolean buff;
    private ImageView powerUpIV;

    /**
     * PowerUps constructor: Holds the specific power-up and where it's located. Used by the PowerUpLayout class.
     * @param powerUp
     * @param xLoc
     * @param yLoc
     */
    public PowerUps(String powerUp, int xLoc, int yLoc) {
        powerUpType = powerUp;
        setBuff(powerUpType);
        if (!powerUpType.equals("")) {
            powerUpIV = new ImageView(
                    new Image(this.getClass().getClassLoader().getResourceAsStream(powerUpType)));
            powerUpIV.setVisible(false);
        }
        else {
            powerUpIV = new ImageView(
                    new Image(this.getClass().getClassLoader().getResourceAsStream("brick5.gif")));
            powerUpIV.setVisible(false);
        }
        this.powerUpIV.setX(xLoc + 23);
        this.powerUpIV.setY(yLoc);
        if (buff) {
            this.powerUpIV.setFitHeight(BUFF_SIZE);
            this.powerUpIV.setFitWidth(BUFF_SIZE);
        }
        else {
            this.powerUpIV.setFitHeight(NERF_SIZE);
            this.powerUpIV.setFitWidth(NERF_SIZE);
        }
    }

    /**
     * Returns the ImageView of the power-up
     * @return
     */
    public ImageView getPowerUpIV () {
        return this.powerUpIV;
    }

    /**
     * Sets the buff boolean
     * @return
     */
    public boolean setBuff(String powerUpType) {
        if (powerUpType.equals("extraballpower.gif") || powerUpType.equals("sizepower.gif") ||
                powerUpType.equals("pointspower.gif") || powerUpType.equals("laserpower.gif")) {
            buff = true;
        }
        else {
            buff = false;
        }
        return buff;
    }

    /**
     * Checks the buff boolean
     * @return
     */
    public boolean checkBuff() {
        return buff;
    }

    /**
     * Checks for the power-up type
     * @return
     */
    public String checkPowerUp() {
        return powerUpType;
    }

    /**
     * Removes the brick's power-up after it is caught by the paddle
     * @return
     */
    public String removePower() {
        return powerUpType = "";
    }

}
