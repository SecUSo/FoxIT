package com.foxyourprivacy.f0x1t.Animation;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public class Hide implements ApplicationListener {

    private float width;
    private float height;
    private float margin = 70f;

    private SpriteBatch batch;
    private TextureAtlas textureAtlas;
    private Animation animation;
    private float elapsedTime = 0;

    @Override
    public void create() {
        width = Gdx.graphics.getWidth();
        height = Gdx.graphics.getHeight();

        // animation
        batch = new SpriteBatch();
        textureAtlas = new TextureAtlas(Gdx.files.internal("hide/atlas_tale.atlas"));
        animation = new Animation(1/15f, textureAtlas.getRegions());
    }

    @Override
    public void dispose() {
        batch.dispose();
        textureAtlas.dispose();

    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Animation
        batch.begin();
//        stage.draw();
        elapsedTime += Gdx.graphics.getDeltaTime();
        batch.draw(animation.getKeyFrame(elapsedTime, true), 0, 0);
        batch.end();
    }
    @Override
    public void resize(int width, int height) {
    }
    @Override
    public void pause() {
    }
    @Override
    public void resume() {
    }
}
