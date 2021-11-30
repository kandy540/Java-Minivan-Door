package edu.vt.cs5044;

/**
 * Values represent the result of an action requested of a minivan sliding door. 
 *
 * Please carefully review the comments below for more details.
 *
 * IMPORTANT: Please DO NOT modify this file in any way!
 *
 * @author Prof. Oliva
 * @version Fall 2021
 */
public enum Result {

    /**
     * An invalid parameter was provided to a mutator.
     * This applies only when a null enum value is received for a Gear or a Direction.
     */
    INVALID_PARAMETER,
    
    /**
     * Door was closed; is now open.
     */
    DOOR_OPENED,

    /**
     *  Door was open; is now closed.
     */
    DOOR_CLOSED,

    /**
     * Door was unlocked; is now locked.
     */
    DOOR_LOCKED,

    /**
     * Door was locked; is now unlocked.
     */
    DOOR_UNLOCKED,

    /**
     * Child-safety was disengaged; is now engaged.
     */
    CHILDSAFE_ENGAGED,

    /**
     * Child-safety was engaged; is now disengaged.
     */
    CHILDSAFE_DISENGAGED,

    /**
     * Gear was any gear other than park; is now in park.
     */
    GEAR_PARKED,

    /**
     * Gear was in park; is now in any other gear.
     */
    GEAR_RELEASED,
    
    /**
     * Gear was any gear other than park; is now in a different non-park gear.
     * For example, this applies to shifting from neutral to drive.
     */
    GEAR_CHANGED,

    // -----------------------------------------------------------------------
    // Values defined below here indicate a change request performed no action
    // -----------------------------------------------------------------------
    //
    // IMPORTANT: If multiple values apply, you must use the FIRST one listed below.
    //
    // For example, if the door is locked, the gear is released,
    // and the outside handle is activated toward OPEN, then the applicable value returned
    // should be OPEN_REFUSED_GEAR rather than OPEN_REFUSED_LOCKED, even though both apply.
    // Note that this is true regardless of whether the door was already open or not.
    //
    // As another example, if the door is already locked when a lock request is
    // made, the return value should be NO_ACTION.
    //
    // -----------------------------------------------------------------------
    /**
     * Open refused; inner handle activated while child-safety engaged.
     */
    OPEN_REFUSED_CHILDSAFE,

    /**
     * Open refused; gear is not in park.
     */
    OPEN_REFUSED_GEAR,

    /**
     * Open refused; door is locked.
     */
    OPEN_REFUSED_LOCK,

    /**
     * Child-safe setting is inaccessible, because the door is currently closed.
     */
    CHILDSAFE_INACCESSIBLE,

    /**
     * Any request that results in no state change, such as locking an already-locked door.
     * This does NOT apply to opening an already-open door, UNLESS the door would open otherwise.
     * For example, if the door is already open but the gear is in neutral, and the outside handle
     * is pulled toward open, the correct event is OPEN_REFUSED_GEAR, not NO_ACTION. If the door is
     * already open and unlocked, with the gear in park, then the outside handle is pulled toward
     * open, then the correct event would be NO_ACTION.  
     */
    NO_ACTION;
}
