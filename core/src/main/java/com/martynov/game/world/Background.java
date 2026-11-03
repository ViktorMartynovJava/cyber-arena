package com.martynov.game.world;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
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
            60f,  30f,
            720f, 30f,
            725f, 130f,
            690f, 200f,
            650f, 235f,
            570f, 240f,
            570f, 245f,
            215f, 245f,
            175f, 225f,
            70f,  185f,
            55f,  115f
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

    public void renderDebug(ShapeRenderer renderer) {
        renderer.setColor(Color.GREEN);
        renderer.polygon(walkableArea.getTransformedVertices());
    }
}
