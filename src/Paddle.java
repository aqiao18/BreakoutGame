import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Paddle class: Creates the paddle and is important for paddle dependent power-ups
 * @author Alex Qiao
 */
public class Paddle {
    public static final int PADDLE_WIDTH = 60;
    private ImageView iv;

    /**
     * Creates the ImageView of the paddle
     * @param file
     * @param windowWidth
     * @param windowHeight
     * @return
     */
    public ImageView spawnPaddle (String file, int windowWidth, int windowHeight){
        Image paddle = new Image(this.getClass().getClassLoader().getResourceAsStream(file));
        iv = new ImageView(paddle);
        iv.setX(windowWidth/2 - 31);
        iv.setY(windowHeight - 12);
        iv.setFitWidth(PADDLE_WIDTH);
        return iv;
    }

    /**
     * Getter method for paddle ImageView
     * @return
     */
    public ImageView getPaddleIV () {
        return this.iv;
    }

    /**
     * Increase paddle size, used by power-ups
     */
    public void increasePaddleSize() {
        this.iv.setFitWidth(this.getPaddleIV().getFitWidth() * 2);
    }

    /**
     * Decrease paddle size, used by power-ups
     */
    public void decreasePaddleSize() {
        this.iv.setFitWidth(this.getPaddleIV().getFitWidth() / 2);
    }

    /**
     * Returns the paddle width, used by the Breakout class
     */
    public double getPaddleWidth() {
        return this.iv.getFitWidth();
    }
}
