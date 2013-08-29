package sg.atom.gameplay.actor;

import com.jme3.math.Vector3f;
import sg.atom.gameplay.GameAction;

public abstract class Actor {
    //public abstract void simpleUpdate(float tpf);

    //public abstract Vector3f getLocation();
    public abstract int getId();

    public abstract void doAction(GameAction a);
    //public abstract void hurt(int amount);
    //public abstract boolean isDead();
}