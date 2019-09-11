import javafx.scene.image.ImageView;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * BrickLayout class: manages the entirety of the brick structure
 * @author Alex Qiao
 */
public class BrickLayout {
    public static final int BRICK_WIDTH = 60, BRICK_HEIGHT = 21;
    public static final String STAGE_ONE = "stage1.txt", STAGE_TWO = "stage2.txt", STAGE_THREE = "stage3.txt";

    private ArrayList<ImageView> brickListIV;
    private ArrayList<Bricks> brickList, destroyedList;
    private ArrayList<PowerUps> powerUpsList;
    private String currentStageFormat, oneBrickImage, twoBrickImage, threeBrickImage;
    private int currBrickXPos, currBrickYPos, newLineCounter, totalPoints;

    /**
     * Creates a list for the brick formation
     * @param stageFormat
     * @param OneHitBrick
     * @param TwoHitBrick
     * @param ThreeHitBrick
     * @return
     */
    public ArrayList<Bricks> brickFormation(String stageFormat,
                       String OneHitBrick, String TwoHitBrick, String ThreeHitBrick) {
            brickListIV = new ArrayList<>();
            brickList = new ArrayList<>();
            destroyedList = new ArrayList<>();
            powerUpsList = new ArrayList<>();
            currentStageFormat = stageFormat;
            oneBrickImage = OneHitBrick;
            twoBrickImage = TwoHitBrick;
            threeBrickImage = ThreeHitBrick;
            currBrickXPos = 0;
            currBrickYPos = 0;
            newLineCounter = 0;
            totalPoints = 0;
            return brickList;
    }

    /**
     * Creates the bricks and places into the list
     * @param bricks
     */
    public void generateBricks (ArrayList<Bricks> bricks) {
        try {
            Scanner input = new Scanner(this.getClass().getClassLoader().getResourceAsStream(currentStageFormat));
            while (input.hasNext()) {
                String brickType = input.next();
                if (brickType.charAt(0) == '1') {
                    Bricks newBrick = new Bricks(oneBrickImage, currBrickXPos, currBrickYPos, false);
                    bricks.add(newBrick);
                    destroyedList.add(newBrick);
                    //brickList.add(newBrick);
                } else if (brickType.charAt(0) == '2') {
                    Bricks newBrick = new Bricks(twoBrickImage, currBrickXPos, currBrickYPos, false);
                    bricks.add(newBrick);
                    destroyedList.add(newBrick);
                    //brickList.add(newBrick);
                } else if (brickType.charAt(0) == '3') {
                    Bricks newBrick = new Bricks(threeBrickImage, currBrickXPos, currBrickYPos, false);
                    bricks.add(newBrick);
                    destroyedList.add(newBrick);
                    //brickList.add(newBrick);
                } else if (brickType.charAt(1) != '.') {
                    Bricks newBrick = new Bricks(spawnPowerUpBrickStrength(brickType),
                            currBrickXPos, currBrickYPos, true);
                    bricks.add(newBrick);
                    destroyedList.add(newBrick);
                }
                currBrickXPos += BRICK_WIDTH;
                newLineCounter++;
                if (newLineCounter == 9) {
                    newLineCounter = 0;
                    currBrickXPos = 0;
                    currBrickYPos += BRICK_HEIGHT;
                }
            }
            input.close();
            } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Spawns the correct strength of a power-up brick
     * @param brickType
     * @return
     */
    private String spawnPowerUpBrickStrength (String brickType) {
        char strength = brickType.charAt(1);
        if (strength == '1') {
            return oneBrickImage;
        }
        if (strength == '2') {
            return twoBrickImage;
        }
        if (strength == '3') {
            return threeBrickImage;
        }
        return "";
    }

    /**
     * Check ball and brick collisions
     * @param ball
     */
    public void checkBrickCollision(NormalBall ball) {
        for (int i = 0; i < brickList.size(); i++) {
            if (ball.getNormalBallIV().getBoundsInParent()
                    .intersects(brickList.get(i).getBrickIV().getBoundsInParent())) {
                ball.changeYDirection();
                brickList.get(i).decreaseLife();
                if(brickList.get(i).checkBrickLife() <= 0) {
                    destroyBrick(brickList.get(i), i);
                }
                break;
            }
        }
    }

    /**
     * Returns the list of destroyed bricks
     * @return
     */
    public ArrayList<Bricks> getDestroyedBrickList() { return this.destroyedList; }

    /**
     * Deletes the brick properly and adds to score
     * @param brick
     * @param index
     */
    private void destroyBrick(Bricks brick, int index) {
        totalPoints += brick.getBrickPoints();
        brickList.remove(index);
        brick.getBrickIV().setImage(null);
    }

    /**
     * Returns total points
     * @return
     */
    public int getTotalPoints() {
        return this.totalPoints;
    }

    /**
     * Sets the right stage structure
     * @param level
     * @return
     */
    public String setStage (int level) {
        if (level == 1) {
            return STAGE_ONE;
        } else if (level == 2) {
            return STAGE_TWO;
        } else if (level == 3) {
            return STAGE_THREE;
        }
        return "Invalid :)";
    }

    /**
     * Add to total points
     */
    public void addPoints() { this.totalPoints *= 2;}

    /**
     * Removes from total points
     */
    public void removePoints() {this.totalPoints /= 2;}

    /**
     * Checks if the stage is complete
     * @return
     */
    public boolean checkFinishedStage() {
        if (brickList.size() == 0) {
            return true;
        }
        return false;
    }
}
