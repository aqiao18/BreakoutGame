import javafx.animation.Timeline;
import javafx.scene.text.Text;

/**
 * StageLevel class: Manages the level transitions of the game, used by the Breakout class
 * @author Alex Qiao
 */
public class StageLevel {
    public static final int MAX_LEVEL = 3;

    private int currentLevel;

    /**
     * StageLevel Constructor: Declares the first level of the game
     */
    public StageLevel() {
        currentLevel = 1;
    }

    /**
     * Determines if it is time to transition to the next stage
     * @param animation
     * @param winText
     * @param started
     * @param myStage
     */
    public boolean nextStage(Timeline animation, Text winText,
                             boolean started, int myStage) {
        if (myStage == 0) {
            currentLevel++;
        }
        else {
            currentLevel = myStage;
        }
        if (currentLevel > MAX_LEVEL) {
            winText.setVisible(true);
            return false;
        }
        if (started) {
            animation.stop();
        }
        return true;
    }

    /**
     * Returns the current level of the game
     * @return
     */
    public int getCurrentLevel() {
        return this.currentLevel;
    }
}
