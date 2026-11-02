package com.martynov.game.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Player {

    private float x, y;
    private float width, height;
    private float speed = 300f;
    private Texture texture;

    private Animation<TextureRegion> runAnimation;
    private float stateTime = 0f;
    private TextureRegion currentFrame;

    public Player(float startX, float startY, float width, float height, float speed, Texture sheet) {
        this.x = startX;
        this.y = startY;
        this.width = width;
        this.height = height;
        this.speed = speed;

        int frameWidth = sheet.getWidth() / 4;
        int frameHeight = sheet.getHeight();

        TextureRegion[][] frames = TextureRegion.split(sheet, frameWidth, frameHeight);
        runAnimation = new Animation<TextureRegion>(0.1f, frames[0]);
    }

    public void update(float delta) {

        boolean left = Gdx.input.isKeyPressed(Input.Keys.A) || Gdx.input.isKeyPressed(Input.Keys.LEFT);
        boolean right = Gdx.input.isKeyPressed(Input.Keys.D) || Gdx.input.isKeyPressed(Input.Keys.RIGHT);
        boolean up = Gdx.input.isKeyPressed(Input.Keys.W) || Gdx.input.isKeyPressed(Input.Keys.UP);
        boolean down = Gdx.input.isKeyPressed(Input.Keys.S) || Gdx.input.isKeyPressed(Input.Keys.DOWN);

        if (left) {
            x -= speed * delta;
        }
        if (right) {
            x += speed * delta;
        }
        if (up) {
            y += speed * delta;
        }
        if (down) {
            y -= speed * delta;
        }

        if (x < 0) {
            x = 0;
        } else if (x > 800 - width) {
            x = 800 - width;
        }

        if (y < 0) {
            y = 0;
        } else if (y > 400 - height) {
            y = 400 - height;
        }

        boolean isMoving = left || right || up || down;
        if (isMoving) {
            stateTime += delta;
        }
    }

    public void render(SpriteBatch batch) {
            currentFrame = runAnimation.getKeyFrame(stateTime, true);
            batch.draw(currentFrame, x, y, width, height);
    }

    public void dispose() {
        texture.dispose();
    }
}
