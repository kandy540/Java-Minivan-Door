package edu.vt.cs5044;

public class MinivanSlidingDoorTester {
    
    public static void main(String[] args) {
        
        MinivanSlidingDoor door = new MinivanSlidingDoor();
        
        System.out.println("open? expect: false; actual: " + door.isOpen());
        System.out.println("locked? expect: false; actual: " + door.isLocked());
        System.out.println("childsafe? expect: false; actual: " + door.isChildSafe());
        System.out.println("gear: expect: PARK; actual: " + door.getGear());
        
        System.out.println();
        System.out.println("Lock the door");
        
        Result result = door.pushLockButton(); // mutator
        
        System.out.println("result: expect: DOOR_LOCKED; actual: " + result);
        System.out.println("locked? expect: true; actual: " + door.isLocked());
        
        System.out.println();
        System.out.println("Lock the door AGAIN");
        
        result = door.pushLockButton(); // mutator
        
        System.out.println("result: expect:  + NO_ACTION; actual: " + result);
        System.out.println("locked? expect: true; actual: " + door.isLocked());
       
        
        
        System.out.println();
        System.out.println("Unlock the LOCKED door");
        
        result = door.pushUnlockButton(); // mutator
        
        System.out.println("result: expect: DOOR_UNLOCKED; actual: " + result);
        System.out.println("locked? expect: false; actual: " + door.isLocked());
        
        System.out.println();
        System.out.println("Unlock the UNLOCKED door");
        
        result = door.pushUnlockButton(); // mutator
        
        System.out.println("result: expect:  + NO_ACTION; actual: " + result);
        System.out.println("locked? expect: false; actual: " + door.isLocked());
        
        
        
        System.out.println();
        System.out.println("Open door by pushing the dashboard button while it is on gear PARK");
        
        door.setGear(Gear.PARK);
        result = door.pushDashboardButton(Direction.OPEN);
       
        System.out.println("result: expect: true; actual: " + door.isOpen());
        
        
        MinivanSlidingDoor doorGear = new MinivanSlidingDoor();
        System.out.println();
        System.out.println("Open door by pushing the dashboard button while it is on gear DRIVE");
        
        doorGear.setGear(Gear.DRIVE);
        result = doorGear.pushDashboardButton(Direction.OPEN);
       
        System.out.println("result: expect: false; actual: " + doorGear.isOpen());
        
        
        System.out.println();
        System.out.println("setup[Cls,Lck,CSD,Rel] then open by INSIDE");
        MinivanSlidingDoor msd_inside = new MinivanSlidingDoor();
        msd_inside.pushLockButton();
        msd_inside.setGear(Gear.DRIVE);
        result = msd_inside.pushInsideHandle(Direction.OPEN);
        System.out.println("Result Statement    expected: " + Result.OPEN_REFUSED_GEAR +    "       actual: " + result);
        
        System.out.println();
        System.out.println("setup[Cls,Lck,CSD,Rel] then open by INSIDE, then close; event incorrect");
        msd_inside.pushLockButton();
        msd_inside.setGear(Gear.DRIVE);
        msd_inside.pushInsideHandle(Direction.OPEN);
        result = msd_inside.pushInsideHandle(Direction.CLOSE);
        System.out.println("Result Statement    expected: " + Result.NO_ACTION +    "       actual: " + result);
        
        
        System.out.println();
        System.out.println("Open the inside door while child safe is engaged");
        MinivanSlidingDoor eChildSafe = new MinivanSlidingDoor();
        eChildSafe.pushInsideHandle(Direction.OPEN);
        eChildSafe.setChildSafe(true);
        result = eChildSafe.pushInsideHandle(Direction.OPEN);
        System.out.println("Result: expect: true; actual: " + eChildSafe.isChildSafe());
        System.out.println("Result: expect: OPEN_REFUSED_CHILDSAFE ; actual: " + result);
        
        
        System.out.println();
        System.out.println("Disengage child safe");
        result = eChildSafe.setChildSafe(false);
        System.out.println("Result: expect: CHILDSAFE_DISENGAGED ; actual: " + result);
        
    }

}
