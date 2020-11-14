package ru.geekbrains.dungeon;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

public class Hero {
    private ProjectileController projectileController;
    private int width;
    private int height;
    private Vector2 position;
    private float velocity, angle;
    private TextureRegion texture;
    private boolean isDoubleShot;

    public Hero(TextureAtlas atlas, ProjectileController projectileController) {
        this.position = new Vector2(100, 100);
        this.velocity = 150.0f;
        this.texture = atlas.findRegion("tank");
        this.projectileController = projectileController;
        this.width = texture.getRegionWidth();
        this.height = texture.getRegionHeight();
        //this.isDoubleShot = false;
    }

    public void update(float dt) {
        if (Gdx.input.isKeyJustPressed(Input.Keys.Q)) isDoubleShot = !isDoubleShot;//boolean переменная, которая обозначает одиночные - false и двойные - true выстрелы
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) Shot(isDoubleShot);//здесь вызывается метод выстрела, в которую передается значение isDoubleShot
        //projectileController.activate(position.x, position.y, 200, 0);
    }

    //метод, отвечающий за движение танка и проверку граничных условий его зоны передвижения
    public void Move(float dt) {
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            position.x -= velocity * dt;
            angle = 180.0f;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            position.x += velocity * dt;
            angle = 0.0f;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            position.y += velocity * dt;
            angle = 90.0f;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            position.y -= velocity * dt;
            angle = 270.0f;
        }

        if (position.x < width / 2.0f) position.x = width / 2.0f;
        if (position.x > GameMap.CELLS_X * 40 - width / 2.0f) position.x = GameMap.CELLS_X * 40 - width / 2.0f;
        if (position.y < 0.0f + height / 2.0f) position.y = 0.0f + height / 2.0f;
        if (position.y > Gdx.graphics.getHeight() - height / 2.0f)
            position.y = Gdx.graphics.getHeight() - height / 2.0f;
    }

    //метод, отвечающий за стрельбу
    public void Shot(boolean isDoubleShot) {
        projectileController.activate(position.x, position.y, 200 * MathUtils.cos(MathUtils.degRad * angle), 200 * MathUtils.sin(MathUtils.degRad * angle));
        if (isDoubleShot)
            projectileController.activate(position.x - 20 * MathUtils.cos(MathUtils.degRad * angle), position.y - 20 * MathUtils.sin(MathUtils.degRad * angle), 200 * MathUtils.cos(MathUtils.degRad * angle), 200 * MathUtils.sin(MathUtils.degRad * angle));
    }

    public void render(SpriteBatch batch) {
        batch.draw(texture, position.x - width/2.0f, position.y - height/2.0f, width/2.0f, height/2.0f,width,height,1,1,angle);
    }

}
