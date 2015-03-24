package com.FoodChain.LevelEditor;

import com.badlogic.gdx.Game;

public class LevelEditorRoot extends Game {
    @Override
    public void create() {
        // TODO Auto-generated method stub
        GameCanvas canvas = new GameCanvas();
        EditorMode eMode = new EditorMode(canvas);
        setScreen(eMode);
    }
}
