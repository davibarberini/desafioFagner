package entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.mygdx.game.MyGdxGame;

import levels.EndScreen;


public class Player extends Sprite {
	public Rectangle rect;
	public double gravity, velX, velY, aceX;
	public int numColunas = 6;
	public int numLinhas = 2;
	public int jumpCount = 0;
	public int spriteLargura = 45;
	public int spriteAltura = 45;
	public int spriteAdjustmentY = -5;
	public boolean getSmall = false;
	public boolean getLarge = false;
	public int spriteAdjustmentX = -8;
	public String animState = "parado";
	public float stateTime;
	public String layer = "layer1";
	public String facing = "direita";
	public boolean canCollide = true;
	public boolean isAttacking = false;
	public int attackCount = 0;
	public int vida = 100;
	public int collideCount = 0;
	public int transitionCount = 0;
	
	Animation<TextureRegion> correndoAnim;
	Animation<TextureRegion> paradoAnim;
	Animation<TextureRegion> jumpingAnim;
	Animation<TextureRegion> attackingAnim;
	
	TextureRegion currentFrame;
	Texture robo = new Texture(Gdx.files.internal("Player/robo.png"));
	//robo.getWidth() / numColunas
	TextureRegion[][] roboSheet = TextureRegion.split(robo, 40, 40);
	TextureRegion[] correndo = new TextureRegion[6];
	TextureRegion[] parado = new TextureRegion[1];
	TextureRegion[] jumping = new TextureRegion[4];
	TextureRegion[] attacking = new TextureRegion[2];

	public Player(float x, float y, float w, float h, double g, double vX, double vY) {
		rect = new Rectangle(x, y, w, h);
		gravity = g;
		velX = vX;
		velY = vY;
		for(int e=0; e < 6; e++) {
			correndo[e] = roboSheet[0][e];

		}
		for(int i=0; i < 4; i++) {
			jumping[i] = roboSheet[1][i];
		}

		parado[0] = roboSheet[0][1];
		attacking[0] = roboSheet[1][0];
		attacking[1] = roboSheet[1][3];
		
		correndoAnim = new Animation<TextureRegion>(0.09f, correndo);
		paradoAnim = new Animation<TextureRegion>(0.06f, parado);
		jumpingAnim = new Animation<TextureRegion>(0.1f, jumping);
		attackingAnim = new Animation<TextureRegion>(0.5f, attacking);
		
	}
 
	public void update(MyGdxGame game) {
		if(getSmall) {
			rect.height -= 1;
        	spriteLargura -= 1;
        	rect.height -= 1;
        	spriteAltura -= 1;
			transitionCount += 1;
			if(transitionCount >= 10) {
				getSmall = false;
				transitionCount = 0;
			}
			
		}
		else if(getLarge) {
			rect.height += 1;
        	spriteLargura += 1;
        	rect.height += 1;
        	spriteAltura += 1;
			transitionCount += 1;
			if(transitionCount >= 10) {
				getLarge = false;
				transitionCount = 0;
			}
			
		}
		if(!canCollide) {
			collideCount += 1;
			if(collideCount > 30) {
				canCollide = true;
				collideCount = 0;
			}
		}
		
		if (vida <= 0) game.setScreen(new EndScreen(game));
		gravity += -2000 * Gdx.graphics.getDeltaTime();
		velX += aceX * Gdx.graphics.getDeltaTime();
		if(velX > 300) velX = 300;
		else if(velX < -300) velX = -300;
		if(isAttacking) {
			animState = "attacking";
			attackCount++;
			if(attackCount > 50) {
				isAttacking = false;
				attackCount = 0;
			}
		}
		
	}
	
	
	public void draw(SpriteBatch sb) {
		if(animState == "parado") {
			if(facing == "direita") {
				stateTime += Gdx.graphics.getDeltaTime();
				currentFrame = paradoAnim.getKeyFrame(stateTime, true);
				sb.draw(currentFrame, this.rect.x, this.rect.y + spriteAdjustmentY, spriteLargura, spriteAltura);
			}
			else {
				stateTime += Gdx.graphics.getDeltaTime();
				currentFrame = paradoAnim.getKeyFrame(stateTime, true);
				sb.draw(currentFrame, this.rect.x + this.rect.width, this.rect.y + spriteAdjustmentY, -spriteLargura, spriteAltura);
			}
			
		}		
		else if(animState == "jumping") {
			if(facing == "direita") {
				stateTime += Gdx.graphics.getDeltaTime();
				currentFrame = jumpingAnim.getKeyFrame(stateTime, false);
				sb.draw(currentFrame, this.rect.x, this.rect.y + spriteAdjustmentY, spriteLargura, spriteAltura);
			}
			else {
				stateTime += Gdx.graphics.getDeltaTime();
				currentFrame = jumpingAnim.getKeyFrame(stateTime, false);
				sb.draw(currentFrame, this.rect.x + this.rect.width, this.rect.y + spriteAdjustmentY, -spriteLargura, spriteAltura);
			}
			
		}
		else if(animState == "running") {
			if(facing == "direita") {
				stateTime += Gdx.graphics.getDeltaTime();
				currentFrame = correndoAnim.getKeyFrame(stateTime, true);
				sb.draw(currentFrame, this.rect.x, this.rect.y + spriteAdjustmentY, spriteLargura, spriteAltura);
			}
			else{
				stateTime += Gdx.graphics.getDeltaTime();
				currentFrame = correndoAnim.getKeyFrame(stateTime, true);
				sb.draw(currentFrame, this.rect.x + this.rect.width, this.rect.y + spriteAdjustmentY, -spriteLargura, spriteAltura);
			}
		}
		else if(animState == "attacking") {
			if(facing == "direita") {
				stateTime += Gdx.graphics.getDeltaTime();
				currentFrame = attackingAnim.getKeyFrame(stateTime, true);
				sb.draw(currentFrame, this.rect.x, this.rect.y + spriteAdjustmentY, spriteLargura, spriteAltura);
			}
			else{
				stateTime += Gdx.graphics.getDeltaTime();
				currentFrame = attackingAnim.getKeyFrame(stateTime, true);
				sb.draw(currentFrame, this.rect.x + this.rect.width, this.rect.y + spriteAdjustmentY, -spriteLargura, spriteAltura);
			}
		}
		else {
			stateTime = 0;
		}
	}
	
	public void keyDown(int keyCode) {
		if(keyCode == Input.Keys.W && jumpCount < 2) {
			System.out.println("here");
			stateTime = 0;
            this.gravity = 600;
            animState = "jumping";
            jumpCount += 1;
        }
        else if(keyCode == Input.Keys.D) {
        	if(this.velX < 0) velX = 0;
        	this.aceX = 1000;
        	if(animState != "jumping") animState = "running";
        	facing = "direita";
        }
        else if(keyCode == Input.Keys.A) {
        	if(this.velX > 0) velX = 0;
        	this.aceX = -1000;
        	if(animState != "jumping") animState = "running";
        	facing = "esquerda";
        }
        else if(keyCode == Input.Keys.F) {
        	this.stateTime = 0;
        	this.isAttacking = true;
        }
        else if(keyCode == Input.Keys.UP) {
        	if(layer == "layer1" && !getLarge) {
        		getSmall = true;
        		canCollide = false;
        		layer = "layer2";
            	stateTime = 0;
                this.gravity = 600;
                animState = "jumping";
        	}
        	//else if(layer == "layer1") layer = "layer3";
        }
        else if(keyCode == Input.Keys.DOWN) {
        	if(layer == "layer2" && !getSmall) {
        		getLarge = true;
        		canCollide = false;
        		layer = "layer1";
            	stateTime = 0;
                this.gravity = 500;
                animState = "jumping";
        	}
        	//else if(layer == "layer3") layer = "layer2";
        }
	}
	
	public void keyUp(int keyCode) {
	  if(keyCode == Input.Keys.D && this.aceX > 0) {
        	this.aceX = 0;
        	this.velX = 0;
        	if(animState != "jumping") animState = "parado";
      }
	  else if(keyCode == Input.Keys.A && this.aceX < 0) {
        	this.aceX = 0;
        	this.velX = 0;
        	if(animState != "jumping") animState = "parado";
      }
	}
}