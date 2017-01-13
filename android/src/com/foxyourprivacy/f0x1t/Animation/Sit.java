package com.foxyourprivacy.f0x1t.Animation;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.viewport.ExtendViewport;

import java.util.Date;


public class Sit implements ApplicationListener {

    float scale_w;
    float scale_h;
    Texture texture;
    TextureRegion region;
    private float width;
    private float height;
    private float margin = 70f;
    private SpriteBatch batch;
    private TextureAtlas textureAtlas;
    private Animation animation;
    private float elapsedTime = 0;
    OrthographicCamera camera;
    ExtendViewport viewport;

    @Override
    public void create() {
        width = Gdx.graphics.getWidth();
        height = Gdx.graphics.getHeight();

        // animation
        batch = new SpriteBatch();
        textureAtlas = new TextureAtlas(Gdx.files.internal("sit/Sit.atlas"));
        animation = new Animation(1/15f, textureAtlas.getRegions());

        // background
        texture = new Texture(Gdx.files.internal("backgrounds/light_sky.jpg"));
        int h = new Date().getHours();
        if (h < 7 || h >= 21) {
            texture = new Texture(Gdx.files.internal("backgrounds/dark_sky.jpg"));
        }
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void dispose() {
        batch.dispose();
        textureAtlas.dispose();
        texture.dispose();
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Animation
        batch.begin();
        elapsedTime += Gdx.graphics.getDeltaTime();

        batch.draw(texture, 0,0, width, height);
        batch.draw(animation.getKeyFrame(elapsedTime, true), width/10, 0);
        batch.end();
    }

    @Override
    public void pause() {
    }
    @Override
    public void resume() {
    }
}
