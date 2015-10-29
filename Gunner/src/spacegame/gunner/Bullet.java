package spacegame.gunner;

public class Bullet extends Sprite {

    private final int BOARD_WIDTH = 390;
    private final int BULLET_SPEED = 2;

    public Bullet(int x, int y) {
        super(x, y);
        initBullet();
    }
    
    private void initBullet() {
        loadImage("missile.png");
        getImageDimensions();        
    }

    public void move() {
        
        x += BULLET_SPEED;
        
        if (x > BOARD_WIDTH)
            vis = false;
    }
}