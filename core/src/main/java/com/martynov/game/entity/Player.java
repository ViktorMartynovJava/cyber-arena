package com.martynov.game.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Player {

    public enum State {
        RUNNING,
        ATTACKING
    }

    private float x, y;
    private float width, height;
    private float speed = 300f;

    private State currentState = State.RUNNING;
    private Animation<TextureRegion> runAnimation;
    private Animation<TextureRegion> attackAnimation;

    private boolean playerReverse = false;
    private float stateTime = 0f;
    private TextureRegion currentFrame;

    public Player(float startX, float startY, float width, float height, float speed,
                  Texture runSheet, Texture attackSheet) {
        this.x = startX;
        this.y = startY;
        this.width = width;
        this.height = height;
        this.speed = speed;


        int runFrameWidth = runSheet.getWidth() / 4;
        int runFrameHeight = runSheet.getHeight();
        TextureRegion[][] runFrames = TextureRegion.split(runSheet, runFrameWidth, runFrameHeight);
        runAnimation = new Animation<TextureRegion>(0.1f, runFrames[0]);

        int attackFrameWidth = attackSheet.getWidth() / 4;
        int attackFrameHeight = attackSheet.getHeight();
        TextureRegion[][] attackFrames = TextureRegion.split(attackSheet, attackFrameWidth, attackFrameHeight);
        attackAnimation = new Animation<TextureRegion>(0.08f, attackFrames[0]);
    }

    public void update(float delta) {

        if (currentState != State.ATTACKING && Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            currentState = State.ATTACKING;
            stateTime = 0f;
        }

        if (currentState == State.ATTACKING) {
            stateTime += delta;

            if (attackAnimation.isAnimationFinished(stateTime)) {
                currentState = State.RUNNING;
                stateTime = 0f;
            }
        } else {

            boolean left = Gdx.input.isKeyPressed(Input.Keys.A) || Gdx.input.isKeyPressed(Input.Keys.LEFT);
            boolean right = Gdx.input.isKeyPressed(Input.Keys.D) || Gdx.input.isKeyPressed(Input.Keys.RIGHT);
            boolean up = Gdx.input.isKeyPressed(Input.Keys.W) || Gdx.input.isKeyPressed(Input.Keys.UP);
            boolean down = Gdx.input.isKeyPressed(Input.Keys.S) || Gdx.input.isKeyPressed(Input.Keys.DOWN);

            if (left) {
                x -= speed * delta;
                playerReverse = true;
            }
            if (right) {
                x += speed * delta;
                playerReverse = false;
            }
            if (up) {
                y += speed * delta;
            }
            if (down) {
                y -= speed * delta;
            }

            boolean isMoving = left || right || up || down;
            if (isMoving) {
                stateTime += delta;
            }
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
    }

    public void render(SpriteBatch batch) {

        if (currentState == State.ATTACKING) {
            currentFrame = attackAnimation.getKeyFrame(stateTime, false);
        } else {
            currentFrame = runAnimation.getKeyFrame(stateTime, true);
        }

        if (playerReverse && !currentFrame.isFlipX()) {
            currentFrame.flip(true, false);
        } else if (!playerReverse && currentFrame.isFlipX()) {
            currentFrame.flip(true, false);
        }

        batch.draw(currentFrame, x, y, width, height);
    }
}
