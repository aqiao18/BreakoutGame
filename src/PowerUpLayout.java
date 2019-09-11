import java.util.ArrayList;
import java.util.Scanner;

/**
 * PowerUpLayout class: Manages the overall layout of power-ups
 * @author Alex Qiao
 */
public class PowerUpLayout {
    public static final String
            ADDITIONAL_LIFE_BUFF = "extraballpower.gif", ADDITIONAL_LIFE_NERF = "extraballpowerNERF.gif",
            SIZE_BUFF = "sizepower.gif", SIZE_NERF = "sizepowerNERF.gif",
            POINTS_BUFF = "pointspower.gif", POINTS_NERF = "pointspowerNERF.gif",
            PADDLE_BUFF = "laserpower.gif", PADDLE_NERF = "laserpowerNERF.gif";
    public static final int POWERUP_WIDTH = 60, POWERUP_HEIGHT = 21, NERF_FALL_SPEED = 30, BUFF_FALL_SPEED = 90;

    private String currentStage;
    private int currPowerUpXPos, currPowerUpYPos, newLineCounter;
    private ArrayList<PowerUps> powerUpsList;

    /**
     * Creates the list of power-ups in order
     * @param stageFormat
     * @return
     */
    public ArrayList<PowerUps> powerUpFormation(String stageFormat) {
        powerUpsList = new ArrayList<>();
        currentStage = stageFormat;
        currPowerUpXPos = 0;
        currPowerUpYPos = 0;
        newLineCounter = 0;
        return powerUpsList;
    }

    /**
     * Create the power-ups based on the txt file
     * @param powerUpsList
     */
    public void generatePowerUps(ArrayList<PowerUps> powerUpsList) {
        try {
            Scanner input = new Scanner(this.getClass().getClassLoader().getResourceAsStream(currentStage));
            while (input.hasNext()) {
                String brickType = input.next();
                if (brickType.charAt(0) == '1') {
                    PowerUps newPowerUp = new PowerUps("", currPowerUpXPos, currPowerUpYPos);
                    powerUpsList.add(newPowerUp);
                } else if (brickType.charAt(0) == '2') {
                    PowerUps newPowerUp = new PowerUps("", currPowerUpXPos, currPowerUpYPos);
                    powerUpsList.add(newPowerUp);
                } else if (brickType.charAt(0) == '3') {
                    PowerUps newPowerUp = new PowerUps("", currPowerUpXPos, currPowerUpYPos);
                    powerUpsList.add(newPowerUp);
                } else if (brickType.charAt(0) == 'L') {
                    PowerUps newPowerUp = new PowerUps(ADDITIONAL_LIFE_NERF, currPowerUpXPos, currPowerUpYPos);
                    powerUpsList.add(newPowerUp);
                } else if (brickType.charAt(0) == 'l') {
                    PowerUps newPowerUp = new PowerUps(ADDITIONAL_LIFE_BUFF, currPowerUpXPos, currPowerUpYPos);
                    powerUpsList.add(newPowerUp);
                }  else if (brickType.charAt(0) == 'P') {
                    PowerUps newPowerUp = new PowerUps(POINTS_NERF, currPowerUpXPos, currPowerUpYPos);
                    powerUpsList.add(newPowerUp);
                } else if (brickType.charAt(0) == 'p') {
                    PowerUps newPowerUp = new PowerUps(ADDITIONAL_LIFE_BUFF, currPowerUpXPos, currPowerUpYPos);
                    powerUpsList.add(newPowerUp);
                } else if (brickType.charAt(0) == 'S') {
                    PowerUps newPowerUp = new PowerUps(SIZE_NERF, currPowerUpXPos, currPowerUpYPos);
                    powerUpsList.add(newPowerUp);
                } else if (brickType.charAt(0) == 's') {
                    PowerUps newPowerUp = new PowerUps(SIZE_BUFF, currPowerUpXPos, currPowerUpYPos);
                    powerUpsList.add(newPowerUp);
                } else if (brickType.charAt(0) == 'D') {
                    PowerUps newPowerUp = new PowerUps(PADDLE_NERF, currPowerUpXPos, currPowerUpYPos);
                    powerUpsList.add(newPowerUp);
                } else if (brickType.charAt(0) == 'd') {
                    PowerUps newPowerUp = new PowerUps(PADDLE_BUFF, currPowerUpXPos, currPowerUpYPos);
                    powerUpsList.add(newPowerUp);
                }

                currPowerUpXPos += POWERUP_WIDTH;
                newLineCounter++;
                if (newLineCounter == 9) {
                    newLineCounter = 0;
                    currPowerUpXPos = 0;
                    currPowerUpYPos += POWERUP_HEIGHT;
                }
            }
            input.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Updates the location of each power-up
     * @param elapsedTime
     * @param brickList
     * @return
     */
    public PowerUpLayout updatePowerUpPos(double elapsedTime, ArrayList<Bricks> brickList) {
        for (int i = 0; i < brickList.size(); i++) {
            if (brickList.get(i).checkForVisibility()) {
                if (powerUpsList.get(i).checkBuff()) {
                    powerUpsList.get(i).getPowerUpIV().setY(
                            powerUpsList.get(i).getPowerUpIV().getY() + BUFF_FALL_SPEED * elapsedTime);
                } else if (!powerUpsList.get(i).checkBuff()) {
                    powerUpsList.get(i).getPowerUpIV().setY(
                            powerUpsList.get(i).getPowerUpIV().getY() + NERF_FALL_SPEED * elapsedTime);
                }
        }
    }
        return this;
    }

    /**
     * Check to see if the power-up should be released
     * @param brickList
     * @return
     */
    public PowerUpLayout checkRelease(ArrayList<Bricks> brickList) {
        for (int i = 0; i < brickList.size(); i++) {
            if (brickList.get(i).checkForPowerUp() && brickList.get(i).checkBrickLife() <= 0) {
                powerUpsList.get(i).getPowerUpIV().setVisible(true);
                brickList.get(i).setVisibility();
            }
        }
        return this;
    }

    /**
     * Check if the power-up is caught by the paddle
     * @param ball
     * @param paddle
     * @param brickFormation
     * @return
     */
    public PowerUpLayout checkPaddleCatch(NormalBall ball, Paddle paddle, BrickLayout brickFormation) {
        for(int i = 0; i < powerUpsList.size(); i++) {
            if (paddle.getPaddleIV().getBoundsInParent()
                    .intersects(powerUpsList.get(i).getPowerUpIV().getBoundsInParent())) {
                if (powerUpsList.get(i).checkPowerUp().equals(ADDITIONAL_LIFE_BUFF)) {
                    ball.increaseBallLives();
                } else if (powerUpsList.get(i).checkPowerUp().equals(ADDITIONAL_LIFE_NERF)) {
                    ball.decreaseBallLives();
                } else if (powerUpsList.get(i).checkPowerUp().equals(SIZE_BUFF)) {
                    ball.increaseBallSize();
                } else if (powerUpsList.get(i).checkPowerUp().equals(SIZE_NERF)) {
                    ball.decreaseBallSize();
                } else if (powerUpsList.get(i).checkPowerUp().equals(POINTS_BUFF)) {
                    brickFormation.addPoints();
                } else if (powerUpsList.get(i).checkPowerUp().equals(POINTS_NERF)) {
                    brickFormation.removePoints();
                } else if (powerUpsList.get(i).checkPowerUp().equals(PADDLE_BUFF)) {
                    paddle.increasePaddleSize();
                } else if (powerUpsList.get(i).checkPowerUp().equals(PADDLE_NERF)) {
                    paddle.decreasePaddleSize();
                }
                powerUpsList.get(i).getPowerUpIV().setVisible(false);
                powerUpsList.get(i).removePower();
            }
        }
        return this;
    }
}
