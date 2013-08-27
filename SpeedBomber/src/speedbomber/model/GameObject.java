/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package speedbomber.model;

import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.scene.Node;

/**
 *
 * @author FalseCAM
 */
public abstract class GameObject {

    protected Node node;
    protected RigidBodyControl physics;
    protected GameObjectGroup group = GameObjectGroup.NONE;

    abstract public void simpleUpdate(float tpf);

    public Node getNode() {
        return node;
    }

    public RigidBodyControl getPhysics() {
        return physics;
    }
}
