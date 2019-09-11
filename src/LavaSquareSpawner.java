import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.util.ArrayList;

/**
 * Lava square spawner class: displaying the spawner on the game screen. Collects all squares
 * into a list for easy access.
 * @author Alex Qiao
 */
public class LavaSquareSpawner {
    public static final String SPAWNER_IMAGE = "brick7.gif";

    private ArrayList<LavaSquare> lavaSquareList;
    private ImageView spawner;

    /**
     * Lava square spawner constructor
     * @param windowWidth
     * @param windowHeight
     */
    public LavaSquareSpawner(int windowWidth, int windowHeight) {
        lavaSquareList = new ArrayList<>();
        spawner = new ImageView(new Image(this.getClass().getClassLoader().getResourceAsStream(SPAWNER_IMAGE)));
        spawner.setX(windowWidth - 100);
        spawner.setY(windowHeight - 100);
        spawner.setFitWidth(20);
        spawner.setFitHeight(20);
    }
    /**
     * Returns the ImageView of the spawner
     * @return
     */
    public ImageView getSpawnerIV() {
        return this.spawner;
    }
    /**
     * Returns the list of lava squares
     * @return
     */
    public ArrayList<LavaSquare> getLavaSquareList() {
        return lavaSquareList;
    }

}
