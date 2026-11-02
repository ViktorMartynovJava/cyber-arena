package com.martynov.game.world;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Background {

    private Texture texture;
    private float x,y;
    private float worldWidth;
    private float worldHeight;


    public Background(float worldWidth, float worldHeight) {
        this.worldWidth = worldWidth;
        this.worldHeight = worldHeight;
        this.texture = new Texture("GameArena.jpg");
    }

    public void render(SpriteBatch batch) {
        batch.draw(texture, x, y, worldWidth, worldHeight);
    }

    public void dispose() {
        texture.dispose();
    }

}
