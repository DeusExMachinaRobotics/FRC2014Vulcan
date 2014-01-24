/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dem.world;

import dem.world.Peripheral;
import edu.wpi.first.wpilibj.Servo;
/**
 *
 * @author Evan Coleman
 */
public class Scoop extends Peripheral {
        public final int READY = 1;
        public final int CARRY = 2;
        public final int DUMP = 3;
        public final int STOW = 4;
    Servo endServo = null;
    Servo mainServo = null;
    
    public Scoop(int mainChannel, int endChannel) {
        this.mainServo = new Servo(mainChannel);
        this.endServo = new Servo(endChannel);
    }
    
    public void setHealth(boolean h) {
        this.healthy = h;
    }

    public void setState(int s) {
        if(s > 0 && s <= 4) {
            this.STATE = s;
            switch(s) {
                case READY:
                    endServo.set(0);
                    break;
                case CARRY:
                    endServo.set(.33);
                    break;
                case DUMP:
                    endServo.set(.66);
                    break;
                case STOW:
                    endServo.set(1);
                    break;
            }
        }
    }
    
    public void setPosition(double main, double end) {
        if(main != mainServo.get()) {
            mainServo.set(main);
        }
        
        if(end != endServo.get()) {
            endServo.set(end);
        }
    }
    
    public double getMainPosition() {
        return mainServo.get();
    }

    public double getEndPosition() {
        return endServo.get();
    }
    
}
