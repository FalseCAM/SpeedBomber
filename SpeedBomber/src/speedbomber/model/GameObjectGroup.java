/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package speedbomber.model;

import com.jme3.bullet.collision.PhysicsCollisionObject;

/**
 *
 * @author FalseCAM
 */
public enum GameObjectGroup {

    NONE("None", PhysicsCollisionObject.COLLISION_GROUP_NONE),
    MAP("Map", PhysicsCollisionObject.COLLISION_GROUP_01),
    HAUNTER("Haunter", PhysicsCollisionObject.COLLISION_GROUP_02),
    WEAPON("Weapon", PhysicsCollisionObject.COLLISION_GROUP_03);
    
    private int physicsGroup;
    private String name;

    GameObjectGroup(String name, int physicsGroup) {
        this.name = name;
        this.physicsGroup = physicsGroup;
    }

    public String getName() {
        return name;
    }

    public int getPhysicsGroup() {
        return physicsGroup;
    }
}
