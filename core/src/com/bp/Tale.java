package com.bp;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton.ImageButtonStyle;

import java.util.Date;


public class Tale implements ApplicationListener {

    private float width;
    private float height;
    private float margin = 70f;
    float scale_w;
    float scale_h;

    private SpriteBatch batch;
    private TextureAtlas textureAtlas;
    private Animation animation;
    private float elapsedTime = 0;
    Texture texture;
    TextureRegion region;

    @Override
    public void create() {
        width = Gdx.graphics.getWidth();
        height = Gdx.graphics.getHeight();

        // animation
        batch = new SpriteBatch();
        textureAtlas = new TextureAtlas(Gdx.files.internal("tale/tale.atlas"));
        animation = new Animation(1/15f, textureAtlas.getRegions());

        // background
        texture = new Texture(Gdx.files.internal("backgrounds/light_sky.jpg"));
        int h = new Date().getHours();
        if (h < 7 || h >= 21) {
            texture = new Texture(Gdx.files.internal("backgrounds/dark_sky.jpg"));
        }
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
        batch.draw(animation.getKeyFrame(elapsedTime, true), width/12, 0);
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
