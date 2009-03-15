/*
 * Process.java
 *
 * Created on January 26th by Fabricio Reis.
 */

package bean;

/**
 *
 * @author Fabricio Reis
 */
public class Process {
    private int creationTime;
    private int fathersId;
    private int id;
    private int lifeTime;
    private int priority;
    private int state; // 0 == Ready; 1 == Executing;
    private int size;
    private int turnAround;
    private int waitingTime;
    
    public Process() {        
    }

    public Process(int creationTime, int fathersId, int id,int lifeTime, int priority, int state, int size, int turnAround, int waitingTime) {
        this.creationTime = creationTime;
        this.fathersId = fathersId;
        this.id = id;
        this.lifeTime = lifeTime;
        this.priority = priority;
        this.state = state;
        this.size = size;
        this.turnAround = turnAround;
        this.waitingTime = waitingTime;
    }

    public int getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(int creationTime) {
        this.creationTime = creationTime;
    }    
    
    public int getFathersId() {
        return fathersId;
    }

    public void setFathersId(int fathersId) {
        this.fathersId = fathersId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getLifeTime() {
        return lifeTime;
    }

    public void setLifeTime(int likeTime) {
        this.lifeTime = likeTime;
    }    

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getTurnAround() {
        return turnAround;
    }

    public void setTurnAround(int turnAround) {
        this.turnAround = turnAround;
    }
    
    public int getWaitingTime() {
        return waitingTime;
    }

    public void setWaitingTime(int waitingTime) {
        this.waitingTime = waitingTime;
    }
}