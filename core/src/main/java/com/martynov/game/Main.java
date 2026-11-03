package com.martynov.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.martynov.game.entity.Player;
import com.martynov.game.world.Background;

/**
 * {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms.
 */
public class Main extends ApplicationAdapter {

    public static final float WORLD_WIDTH = 800;
    public static final float WORLD_HEIGHT = 400;

    private Background background;

    private SpriteBatch batch;
    private OrthographicCamera orthographicCamera;
    private Viewport viewport;

    private Texture playerTexture;
    private Player player;


    @Override
    public void create() {
        orthographicCamera = new OrthographicCamera();
        viewport = new FitViewport(WORLD_WIDTH, WORLD_HEIGHT, orthographicCamera);
        batch = new SpriteBatch();
        playerTexture = new Texture("HeroVer1.png");
        playerTexture = new Texture("AnimationHeroVer1.png");

        background = new Background(WORLD_WIDTH,WORLD_HEIGHT);

        player = new Player(100f, 160f, 48f, 105f, 300f, playerTexture);
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }

    @Override
    public void render() {
        float delta = Gdx.graphics.getDeltaTime();

        player.update(delta);

        ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);

        orthographicCamera.update();
        batch.setProjectionMatrix(orthographicCamera.combined);

        batch.begin();
        background.render(batch);
        player.render(batch);
        batch.end();
    }

    @Override
    public void dispose() {
        player.dispose();
        batch.dispose();
        background.dispose();
    }
}
