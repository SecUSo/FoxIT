package com.foxyourprivacy.f0x1t.animation;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import java.util.Calendar;


class Vanish implements ApplicationListener {

    private Texture texture;
    private float width;
    private float height;
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
        textureAtlas = new TextureAtlas(Gdx.files.internal("vanish/vanish.atlas"));
        animation = new Animation(1 / 15f, textureAtlas.getRegions());

        // background
        texture = new Texture(Gdx.files.internal("backgrounds/light_sky.jpg"));
        int h = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
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

        batch.draw(texture, 0, 0, width, height);
        batch.draw(animation.getKeyFrame(elapsedTime, true), -width / 6f, -height / 2, width * 1.3f, height * 1.7f);
        batch.end();
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }
}
