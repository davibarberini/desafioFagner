package levels;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.mygdx.game.MyGdxGame;

import entities.Player;
import platforms.Platform;
import soundandmusic.MusicPlayer;


public class Level1 extends ScreenAdapter {
  public Player p1;
  public static int WIDTH;
  public static int HEIGHT;
  
  
  FillViewport view;
  
	
  public Platform [] platforms;
  public Platform [] platforms2;
  public Platform [] platforms3;
  Platform [] toUsePlatform;
  
  OrthographicCamera camera;
  MyGdxGame game;
  
  Texture fundo;

  public Level1(MyGdxGame game) {
	  this.game = game;
	  //mapWriter = new MapFileWriter(mapLin, mapCol);
	  //mapWriter.writeMap(map, "Level1");
	  try {
		  System.out.println("Tamanho atual" + platforms.length);
		  for(int e=0; e < platforms.length; e++) {
			  platforms[e] = null;
		  }
		  
		  for(int e=0; e < platforms2.length; e++) {
			  platforms2[e] = null;
		  }

		  for(int e=0; e < platforms3.length; e++) {
			  platforms3[e] = null;
		  }
	  } catch(Exception e){
		  
	  }
	  Color color = new Color(1, 0, 0, 1);
	  Color color2 = new Color(0, 1, 0, 1);
	  Color color3 = new Color(0, 0, 1, 1);
	  platforms = new Platform[5];
	  platforms2 = new Platform[5];
	  platforms3 = new Platform[4];
	  platforms3[0] = new Platform(0, -200, 1000, 30, 0, color3);
	  platforms3[1] = new Platform(0, -250, 30, 1050, 0, color3);
	  platforms3[2] = new Platform(0, 600, 1000, 30, 0, color3);
	  platforms3[3] = new Platform(1000, -200, 30, 1050, 0, color3);
	  
	  for(int e=0; e < platforms.length; e++) {
		  platforms[e] = new Platform(100 * e, -100, 80, 30, 0, color);
		  platforms2[e] = new Platform(100 * e, -50, 40, 15, 0, color2);
	  }
	  

  }
  
  @Override
  public void show() {
	  //Parando a thread anterior se existir.
	  toUsePlatform = platforms;
	  if(game.t1 != null && game.t1.isAlive()) {
  		game.t1.toStop = true;
  		try {
  			System.out.println("Join");
			game.t1.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
  	  }
	  
	  
	  //Iniciando a thread da musica
	  game.t1 = new MusicPlayer("Level1/music.mp3"); // Crio a thread passando o caminho da musica como argumento.
      game.t1.start(); 
      
	  p1 = new Player(0, 0, 35, 35, 0.0, 0.0, 0.0);
	  fundo = new Texture("Level1/lab.png");
	  
	  WIDTH = Gdx.graphics.getWidth();
	  HEIGHT = Gdx.graphics.getHeight();
	  
	  p1.rect.x = 50;
	  p1.rect.y = 20;
	    
	  camera = new OrthographicCamera(WIDTH, HEIGHT);
	  camera.position.set(p1.rect.x + (p1.rect.width / 2), p1.rect.y  + (p1.rect.width / 2), 0);
	  view = new FillViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
	  camera.update();
	  
	  Gdx.input.setInputProcessor(new InputAdapter() {
          @Override
          public boolean keyDown(int keyCode) {
              if (keyCode == Input.Keys.SPACE) {
            	  camera.position.set(0, 0, 0);
                  game.setScreen(new TitleScreen(game));
              }
              else if(keyCode == Input.Keys.K) {
              	camera.zoom = 2;
              }
              else if(keyCode == Input.Keys.L) {
              }
              else if(keyCode == Input.Keys.R) {
            	  game.setScreen(new Level1(game));
              }
              else if(keyCode == Input.Keys.ESCAPE) {
            	  game.setScreen(new TitleScreen(game));
              }
              else if(keyCode == Input.Keys.L) {
            	  if(camera.zoom == 0.5f) {
            		  camera.zoom = 1;
            	  }
            	  else if(camera.zoom == 1) {
            		  camera.zoom = 0.5f;
            	  }
              }
              p1.keyDown(keyCode);
              game.t1.keysDown(keyCode);
              return true;
          }
          public boolean keyUp(int keyCode) {
        	  if(keyCode == Input.Keys.K) {
                	camera.zoom = 1;
              }
        	  p1.keyUp(keyCode);
        	  return true;
          }
      });
	  
	 
  }
  
  @Override
  public void render(float delta) {
	game.resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), view, camera);
	
	if(p1.layer == "layer2") {
		toUsePlatform = platforms2;
	}
	else if(p1.layer == "layer1") {
		toUsePlatform = platforms;
	}
	
	p1.rect.y += p1.gravity * delta;
	for(int k=0; k < toUsePlatform.length; k++) {     //Colisao após a movimentação Y
		  if(toUsePlatform[k] != null) {
			  Platform plat = toUsePlatform[k];
			  plat.platCollisionY(p1.gravity, p1, true);
		  }  
	}
	
	for(int j=0; j < platforms3.length; j++) {
		Platform plat = platforms3[j];
		plat.platCollisionY(p1.gravity, p1, false);
	}
	
	
	p1.rect.x += p1.velX * delta;
	for(int k=0; k < toUsePlatform.length; k++) {   //Colisao após a movimentação X
		  if(toUsePlatform[k] != null) {
			  Platform plat = toUsePlatform[k];
			  plat.platCollisionX(p1.velX, p1, true);
		  }
		  
	}
	
	for(int j=0; j < platforms3.length; j++) {
		Platform plat = platforms3[j];
		plat.platCollisionX(p1.velX, p1, false);
	}
	//if(p1.rect.overlaps()
	p1.update(game);
	
	camera.position.set(p1.rect.x + (p1.rect.width / 2), p1.rect.y  + (p1.rect.width / 2), 0);
	camera.update();
	
	this.draw();
  
  }
  
  public void draw() {
	  Gdx.gl.glClearColor(0, 0, 0, 1);
	  Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
	  
	 
	  
	  
	  game.shapeRenderer.setProjectionMatrix(camera.combined);
	  game.shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
	  //game.shapeRenderer.setColor(0, 1, 0, 1);
	  //game.shapeRenderer.rect(p1.rect.x, p1.rect.y, p1.rect.width, p1.rect.height);
	  
	  for(int k=0; k < platforms.length; k++) {
		  if(platforms[k] != null) {
			  game.shapeRenderer.setColor(platforms[k].color);
			  Platform plat = platforms[k];
			  game.shapeRenderer.rect(plat.rect.x, plat.rect.y, plat.rect.width, plat.rect.height);  
		  }
		  
	  }
	  for(int k=0; k < platforms2.length; k++) {
		  if(platforms2[k] != null) {
			  game.shapeRenderer.setColor(platforms2[k].color);
			  Platform plat = platforms2[k];
			  game.shapeRenderer.rect(plat.rect.x, plat.rect.y, plat.rect.width, plat.rect.height);  
		  }
		  
	  }
	  for(int j=0; j < platforms3.length; j++) {
		  game.shapeRenderer.setColor(platforms3[j].color);
		  Platform plat = platforms3[j];
		  game.shapeRenderer.rect(plat.rect.x, plat.rect.y, plat.rect.width, plat.rect.height);
		}
	  game.shapeRenderer.end();
	  
	  game.batch.setProjectionMatrix(camera.combined);
	  game.batch.begin();
	  //game.batch.draw(fundo, 3 , 20);
	  p1.draw(game.batch);
	  //game.batch.draw(idle,  p1.rect.x, p1.rect.y, 35, 35);
	  game.batch.end();
	  
	  
	  
	 
	  
  }
  
  public void dispose() {
	  Gdx.input.setInputProcessor(null);
	  game.shapeRenderer.setProjectionMatrix(null);
	  this.game.shapeRenderer.dispose();
	  
  }
  
  
}
	

//camera.translate(-moveSpeed * Gdx.graphics.getDeltaTime(), 0);
