package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.viewport.FillViewport;

import levels.TitleScreen;
import soundandmusic.MusicPlayer;


public class MyGdxGame extends Game {
  public ShapeRenderer shapeRenderer;
  public SpriteBatch batch;
  public BitmapFont font, titlefont;
  public MusicPlayer t1;

  @Override
  public void create () {
	  batch = new SpriteBatch();
	  shapeRenderer = new ShapeRenderer();
	  font = new BitmapFont(Gdx.files.internal("Fonts/pixelfont.fnt"));
	  titlefont = new BitmapFont(Gdx.files.internal("Fonts/pixelfontgradient.fnt"));
	  titlefont.getData().setScale(1.5f, 1.5f);
	  setScreen(new TitleScreen(this));
  }
  
  public void resize(int width, int height, FillViewport viewport, OrthographicCamera camera) {
	  //viewport.update(width, height);
      //camera.update();
  }
  
 
  @Override
  public void dispose () {
    shapeRenderer.dispose();
    batch.dispose();
    font.dispose();
  }
    
}

//camera.translate(-moveSpeed * Gdx.graphics.getDeltaTime(), 0);
//