package edu.vt.cs5044;

/**
 *  This class is called MinivanSlidingDoor and it describes 
 *  all the properties the ways that the door can open and close.
 *  The door is governed entirely by the operational rules.
 *  Operational rules such open or close, locked or unlocked, 
 *  child safe engaged or disengaged, Park or non-Park
 * 
 *  @author  Andy Kim
 *  @version 2021.09.27
 */

public class MinivanSlidingDoor {
    
    // Cls/Opn: Closed or Opened
    // Unl/Lck: Unlocked or Locked
    // CSE/CSD: Childsafe is engaged or Disengagaed
    // Prk/Rel: Gear is PARK or non-PARK
    
    // setup[Cls,Unl,CSE,Rel]
    // 1 open door
    // 2 engage CS
    // 3 Release gear (any non-PARK gear)
    // 4 close door
    
    // open door via ___
    // Result incorrect OR status isOpen()
    // close door (same mechanism)
    
    private boolean open;
    private boolean locked;
    private boolean childSafe;
    private Gear gear;
    /**
     * This method sets all the initial values of the private fields
     */
    public MinivanSlidingDoor() {
        open = false;
        locked = false;
        childSafe = false;
        gear = Gear.PARK;
    }
    /**
     * get the current state of the door (open or closed).
     * 
     * @return whether the door is open or closed
     */
    public boolean isOpen() {
        return open;
    }
    /**
     * get the current state of the door (locked or unlocked).
     * 
     * @return whether the door is locked or unlocked
     */
    public boolean isLocked() {
        return locked;
    }
    /**
     * get the current state of the childSafe (enabled or disabled).
     * 
     * @return whether the childSafe is activated or not
     */
    public boolean isChildSafe() {
        return childSafe;
    }
    /**
     * get the current state of the gear (PARK, REVERSE, NEUTRAL, DRIVE).
     * 
     * @return the state of the gear
     */
    public Gear getGear() {
        return gear;
    }
    /**
     * This method sets the gear to PARK, REVERSE, NEUTRAL, or DRIVE.
     * The gear is released when it goes from parked to non-parked.
     * The gear is parked when it goes from non-parked to parked.
     * The gear is changed when it goes from non-parked to non-parked.
     * Nothing happens when the requested gear is same as the current gear.
     *
     * @param requestedGear is parameter for requesting a gear
     * @return Result of the gear state.
     */
    public Result setGear(Gear requestedGear) {
        if (requestedGear == gear) {
            return Result.NO_ACTION;
        } 
        else if (requestedGear == Gear.PARK) {
            gear = Gear.PARK;
            return Result.GEAR_PARKED;
        }
        else if (gear == Gear.PARK) {
            return processGearEqualsPark(requestedGear);
        }
        else {
            return processGearNotEqualsPark(requestedGear);
        }
    }
    /**
     * This method allows child safe to be engage or disengage
     * The child safe can only be accessed when the door is open.
     * If the child safe is already engaged, the user can not re-engage it.
     *
     * @param requestedEngage is a parameter for whether to engage child safe or not
     * @return Result the state of the child safe or whether if it is inaccessible.
     */
    public Result setChildSafe(boolean requestedEngage) {
        if (!open) {
            return Result.CHILDSAFE_INACCESSIBLE;
        }
        else if (requestedEngage == childSafe) {
            return Result.NO_ACTION;
        }
        else if (requestedEngage) {
            childSafe = true;
            return Result.CHILDSAFE_ENGAGED;
        } 
        else {
            childSafe = false;
            return Result.CHILDSAFE_DISENGAGED;
        }
    }
    /**
     * This method pushes the locked button.
     * Nothing will happen if the door is already locked.
     * The door will be locked once the button is pushed. 
     *
     * @return Result that the door is locked or no action if it is already locked.
     */
    public Result pushLockButton() {
        if (locked) {
            return Result.NO_ACTION;
        }
        locked = true;
        return Result.DOOR_LOCKED;
    }
    /**
     * This method pushes the unlocked button.
     * Nothing will happen if the door is already unlocked.
     * The door will be unlocked once the button is pushed. 
     *
     * @return Result that the door is unlocked or no action if it is already unlocked.
     */
    public Result pushUnlockButton() {
        if (locked) {
            locked = false;
            return Result.DOOR_UNLOCKED;
        }
        return Result.NO_ACTION;
    }
    /**
     * This method pushes the dash board button.
     * 
     * The user can choose the door to be opened or closed by using the dash board button.
     *
     * @param direction is a parameter that decides the door to be opened or closed
     * @return the door request whether they want it opened or closed
     */
    public Result pushDashboardButton(Direction direction) {
        return processDoorRequest(direction);
    }
    /**
     * This method pushes the inside handle button.
     * 
     * The user can choose the door to be opened or closed by using the inside handle button.
     * The door can not be opened if the child safe is engaged.
     *
     * @param direction is a parameter that decides the door to be opened or closed
     * @return the process to be opened or closed from inside
     */
    public Result pushInsideHandle(Direction direction) {
        if (direction == Direction.OPEN) {
            return processOpenInside();
        }
        if (direction == Direction.CLOSE) {
            return processClose();
        }
        return Result.INVALID_PARAMETER;
    }

    /**
     * This method pushes the outside handle button.
     * 
     * The user can choose the door to be opened or closed by using the outside handle button.
     *
     * @param direction is a parameter that decides the door to be opened or closed
     * @return processDoorRequest method.
     */
    public Result pushOutsideHandle(Direction direction) {
        return processDoorRequest(direction);
    }
    /**
     * This helper method removes redundancy on pushDashboardButton and pushOutsideHandle methods.
     * User chooses whether they want the door to be opened or closed.
     * 
     * @param direction is a parameter that decides the door to be opened or closed
     * @return The process to be opened or closed.
     */
    private Result processDoorRequest(Direction direction) {
        if (direction == Direction.OPEN) {
            return processOpen();
        }
        if (direction == Direction.CLOSE) {
            return processClose();
        }
        return Result.INVALID_PARAMETER;
    }
    /**
     * This is a helper method that decides whether the door can be opened or not.
     * The door can not be opened if the door is locked or gear set on parked.
     * The door can be opened if it just closed and nothing else.
     * 
     * @return Result saying the why the door can not be opened or if it will be opened.
     */
    private Result processOpen() {
        if (gear != Gear.PARK) {
            return Result.OPEN_REFUSED_GEAR;
        }
        else if (locked) {
            return Result.OPEN_REFUSED_LOCK;
        }
        else if (!open) {
            open = true;
            return Result.DOOR_OPENED;
        }
        else {
            return Result.NO_ACTION;
        }
    }
    /**
     * This is a helper method that decides whether the door can be closed or not.
     * The door can not be closed if the door is already closed, which will result in no action.
     * The door can be closed if it is opened.
     * 
     * @return Result that door will be closed and no action is done if the door is already closed.
     */
    private Result processClose() {
        if (!open) {
            return Result.NO_ACTION;
        }
        open = false;
        return Result.DOOR_CLOSED;
    } 
    /**
     * This is a helper method that decides whether the door can be opened or not from the inside.
     * The door can be opened the child safe is disengaged. 
     * The door can not be opened if the child safe is engaged.
     * 
     * @return The processOpen method and open refused child safe statement.
     */
    private Result processOpenInside() {
        if (!childSafe) {
            return processOpen();
        }
        return Result.OPEN_REFUSED_CHILDSAFE;
    }
    /**
     * This is a helper method for PARK gear to be released.
     * 
     * @param requestedGear is a gear that is requested either PARK, REVERSE, NEUTRAL, and DRIVe
     * @return Result the state of the gear
     */
    private Result processGearEqualsPark(Gear requestedGear) {
        if (requestedGear == Gear.REVERSE) {
            gear = Gear.REVERSE;
            return Result.GEAR_RELEASED;
        }
        else if (requestedGear == Gear.NEUTRAL) {
            gear = Gear.NEUTRAL;
            return Result.GEAR_RELEASED;
        }
        else if (requestedGear == Gear.DRIVE) {
            gear = Gear.DRIVE;
            return Result.GEAR_RELEASED;
        } 
        else {
            return Result.INVALID_PARAMETER;
        }
    }
    /**
     * This is a helper method for Non PARK gear to be changed.
     * 
     * @param requestedGear is a gear that is requested either PARK, REVERSE, NEUTRAL, and DRIVe
     * @return Result the state of the gear
     */
    private Result processGearNotEqualsPark(Gear requestedGear) {
        if (requestedGear == Gear.REVERSE) {
            gear = Gear.REVERSE;
            return Result.GEAR_CHANGED;
        }
        else if (requestedGear == Gear.NEUTRAL) {
            gear = Gear.NEUTRAL;
            return Result.GEAR_CHANGED;
        }
        else if (requestedGear == Gear.DRIVE) {
            gear = Gear.DRIVE;
            return Result.GEAR_CHANGED;
        } 
        else {
            return Result.INVALID_PARAMETER;
        }
    }
}
