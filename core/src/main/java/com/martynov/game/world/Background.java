package com.martynov.game.world;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Polygon;

public class Background {

    private Texture texture;
    private float x, y;
    private float worldWidth;
    private float worldHeight;

    private Polygon walkableArea;

    public Background(float worldWidth, float worldHeight) {
        this.worldWidth = worldWidth;
        this.worldHeight = worldHeight;
        this.texture = new Texture("GameArena.jpg");

        float[] vertices = new float[] {
            95f,  45f,
            705f, 45f,
            705f, 150f,
            605f, 280f,
            145f, 280f,
            95f,  180f
        };

        this.walkableArea = new Polygon(vertices);
    }

    public boolean isWalkable(float feetX, float feetY) {
        return walkableArea.contains(feetX, feetY);
    }

    public Polygon getWalkableArea() {
        return walkableArea;
    }

    public void render(SpriteBatch batch) {
        batch.draw(texture, x, y, worldWidth, worldHeight);
    }

    public void dispose() {
        texture.dispose();
    }
}
