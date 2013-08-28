/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package speedbomber.controller;

import com.jme3.math.Vector3f;
import com.jme3.network.serializing.Serializable;

/**
 *
 * @author FalseCAM
 */
@Serializable
public class GameEvent {

    @Serializable
    public enum GameEventType {

        THROWGRENADE, PLACEBOMB, MOVETO;
    }
    private Float timeStamp;
    private Integer playerID;
    private GameEventType type;
    private Vector3f position = new Vector3f();

    public GameEvent() {
    }

    public GameEvent(Float timeStamp, Integer playerID, GameEventType type) {
        this.timeStamp = timeStamp;
        this.playerID = playerID;
        this.type = type;
    }

    public GameEvent(Float timeStamp, Integer playerID, GameEventType type, Vector3f position) {
        this.timeStamp = timeStamp;
        this.playerID = playerID;
        this.type = type;
        this.position = position;
    }

    public GameEvent(Float timeStamp, Integer playerID, GameEvent oldEvent) {
        this.timeStamp = timeStamp;
        this.playerID = playerID;
        this.type = oldEvent.type;
        this.position = oldEvent.position;
    }

    public Float getTimeStamp() {
        return timeStamp;
    }

    public Integer getPlayerID() {
        return playerID;
    }

    public GameEventType getType() {
        return type;
    }

    public Vector3f getPosition() {
        return position;
    }
}
