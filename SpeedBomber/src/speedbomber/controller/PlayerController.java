/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package speedbomber.controller;

import com.jme3.font.BitmapFont;
import com.jme3.font.BitmapText;
import com.jme3.math.Vector3f;
import com.jme3.network.Message;
import speedbomber.Game;
import speedbomber.model.LevelAppState;
import speedbomber.model.network.GameClient;
import speedbomber.model.network.GameMessage;

/**
 *
 * @author FalseCAM
 */
public class PlayerController {

    float lastMove = 0;
    float lastBomb = 0;
    float lastGrenade = 0;
    private LevelAppState level;

    public PlayerController(LevelAppState level) {
        this.level = level;
    }

    public void update(float tpf) {
        lastMove += tpf;
        lastBomb += tpf;
        lastGrenade += tpf;
    }

    void bomb() {
        if (lastBomb > 20f) {
            lastBomb = 0;
            GameEvent event = new GameEvent(0f, 0, GameEvent.GameEventType.PLACEBOMB);
            GameMessage message = new GameMessage(event);
            GameClient.getClient().send(message);
        }
    }

    void move(Vector3f pick) {

        if (pick != null && lastMove > 0.2f) {
            lastMove = 0;
            GameEvent event = new GameEvent(0f, 0, GameEvent.GameEventType.MOVETO, pick);
            Message message = new GameMessage(event);
            GameClient.getClient().send(message);
        }
    }

    void grenade(Vector3f pick) {
        if (pick != null && lastGrenade > 0.5f) {
            lastGrenade = 0;
            GameEvent event = new GameEvent(0f, 0, GameEvent.GameEventType.THROWGRENADE, pick);
            Message message = new GameMessage(event);
            GameClient.getClient().send(message);
        }
    }

    void showStatistics(boolean isPressed) {
        level.getApp().getGuiNode().detachAllChildren();
        if (isPressed) {
            BitmapFont guiFont = Game.getAssetManager().loadFont("Interface/Fonts/Default.fnt");
            BitmapText text = new BitmapText(guiFont, false);
            text.setSize(guiFont.getCharSet().getRenderedSize());
            text.setText(level.getStatistics().toString());
            text.setLocalTranslation(100, text.getHeight() + 20, 0);
            level.getApp().getGuiNode().attachChild(text);
        }
    }

    void moveCamUp() {
        level.getApp().getCamera().setLocation(level.getApp().getCamera().getLocation().add(new Vector3f(0, 0, -Game.scale)));
    }

    void moveCamDown() {
        level.getApp().getCamera().setLocation(level.getApp().getCamera().getLocation().add(new Vector3f(0, 0, Game.scale)));
    }

    void moveCamLeft() {
        level.getApp().getCamera().setLocation(level.getApp().getCamera().getLocation().add(new Vector3f(-Game.scale, 0, 0)));
    }

    void moveCamRight() {
        level.getApp().getCamera().setLocation(level.getApp().getCamera().getLocation().add(new Vector3f(+Game.scale, 0, 0)));
    }

    void moveCamIn() {
        level.getApp().getCamera().setLocation(level.getApp().getCamera().getLocation().add(new Vector3f(0, Game.scale, 0)));
    }

    void moveCamOut() {
        level.getApp().getCamera().setLocation(level.getApp().getCamera().getLocation().add(new Vector3f(0, -Game.scale, 0)));
    }
}
