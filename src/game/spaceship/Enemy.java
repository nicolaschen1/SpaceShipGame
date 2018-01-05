/***
 * Author: Nicolas Chen
 * SpaceShipGame
 * Version 1.0
***/

package game.spaceship;

public class Enemy extends Sprite{
	private final int INITIAL_X = 400;

    public Enemy(int x, int y) {
        super(x, y);

        initAlien();
    }

    private void initAlien() {
        loadImage("enemy.png");
        getImageDimensions();
    }

    public void move() {
        if (x < 0) {
            x = INITIAL_X;
        }
        x -= 1;
    }
}
