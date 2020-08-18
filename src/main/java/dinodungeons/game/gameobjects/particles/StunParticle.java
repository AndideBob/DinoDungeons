package dinodungeons.game.gameobjects.particles;

import dinodungeons.game.data.gameplay.InputInformation;
import dinodungeons.gfx.sprites.SpriteID;
import dinodungeons.gfx.sprites.SpriteManager;

public class StunParticle extends BaseParticle {

    private boolean finished;
    private long particleLifeTime;
    private int frame;

    public StunParticle(float startPositionX, float startPositionY) {
        super(startPositionX, startPositionY);
        finished = false;
        particleLifeTime = 0;
        frame = 0;
    }

    public void stopStun(){
        finished = true;
    }

    @Override
    public void update(long deltaTimeInMs, InputInformation inputInformation) {
        particleLifeTime += deltaTimeInMs;
        frame = (int)Math.floor(particleLifeTime / 250);
        frame = frame % 4;
    }

    @Override
    public void draw(int anchorX, int anchorY) {
        int x = (int)Math.round(positionX);
        int y = (int)Math.round(positionY);
        SpriteManager.getInstance().getSprite(SpriteID.PARTICLES_C).draw(frame, anchorX + x, anchorY + y);
    }

    @Override
    public boolean shouldBeDeleted() {
        return finished;
    }
}
