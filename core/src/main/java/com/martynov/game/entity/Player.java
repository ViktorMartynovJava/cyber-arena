package com.martynov.game.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.martynov.game.world.Background;

public class Player {

    public enum State {
        RUNNING,
        ATTACKING
    }

    private float x, y;
    private float width, height;
    private float speed;

    private State currentState = State.RUNNING;
    private Animation<TextureRegion> runAnimation;
    private Animation<TextureRegion> attackAnimation;

    private boolean playerReverse = false;
    private float stateTime = 0f;
    private TextureRegion currentFrame;

    private Rectangle feetBounds;
    private static final float FEET_WIDTH = 30f;
    private static final float FEET_HEIGHT = 15f;
    private static final float FEET_OFFSET_X = 9f;

    public Player(float startX, float startY, float width, float height, float speed,
                  Texture runSheet, Texture attackSheet) {
        this.x = startX;
        this.y = startY;
        this.width = width;
        this.height = height;
        this.speed = speed;

        this.feetBounds = new Rectangle(x + FEET_OFFSET_X, y, FEET_WIDTH, FEET_HEIGHT);

        int runFrameWidth = runSheet.getWidth() / 4;
        int runFrameHeight = runSheet.getHeight();
        TextureRegion[][] runFrames = TextureRegion.split(runSheet, runFrameWidth, runFrameHeight);
        this.runAnimation = new Animation<TextureRegion>(0.1f, runFrames[0]);

        int attackFrameWidth = attackSheet.getWidth() / 4;
        int attackFrameHeight = attackSheet.getHeight();
        TextureRegion[][] attackFrames = TextureRegion.split(attackSheet, attackFrameWidth, attackFrameHeight);
        this.attackAnimation = new Animation<TextureRegion>(0.08f, attackFrames[0]);
    }

    public void update(float delta, Background background) {

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
            return;
        }

        boolean left = Gdx.input.isKeyPressed(Input.Keys.A) || Gdx.input.isKeyPressed(Input.Keys.LEFT);
        boolean right = Gdx.input.isKeyPressed(Input.Keys.D) || Gdx.input.isKeyPressed(Input.Keys.RIGHT);
        boolean up = Gdx.input.isKeyPressed(Input.Keys.W) || Gdx.input.isKeyPressed(Input.Keys.UP);
        boolean down = Gdx.input.isKeyPressed(Input.Keys.S) || Gdx.input.isKeyPressed(Input.Keys.DOWN);

        float oldX = x;
        float oldY = y;

        if (left) {
            x -= speed * delta;
            playerReverse = true;
        }
        if (right) {
            x += speed * delta;
            playerReverse = false;
        }

        float footLeftX = x + FEET_OFFSET_X;
        float footRightX = footLeftX + FEET_WIDTH;
        if (!background.isWalkable(footLeftX, oldY) || !background.isWalkable(footRightX, oldY)) {
            x = oldX;
        }

        if (up) {
            y += speed * delta;
        }
        if (down) {
            y -= speed * delta;
        }

        footLeftX = x + FEET_OFFSET_X;
        footRightX = footLeftX + FEET_WIDTH;
        if (!background.isWalkable(footLeftX, y) || !background.isWalkable(footRightX, y)) {
            y = oldY;
        }

        feetBounds.setPosition(x + FEET_OFFSET_X, y);

        boolean isMoving = left || right || up || down;
        if (isMoving) {
            stateTime += delta;
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

    public Rectangle getFeetBounds() {
        return feetBounds;
    }

    public State getCurrentState() {
        return currentState;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }
}
