package location;

/**
 * A lock with three components.
 * If all components are equal to their default values (as initialized below),
 * then the lock is "unlocked" and the path is open.
 * @author davidclark
 * date: 09/03/2022
 */
public class PathLock {
	
	private boolean reqsActivityCompletion = false;	// true if the origin location's activity must be completed before unlocking this path
	private int numActAttemptsReqd = 0;				// # of times player must attempt the activity before unlocking this path
	private Location otherLocationReqd = null;		// a place player must visit before unlocking this path
	

	
	public PathLock(boolean reqsActivityCompletion, int numActAttemptsReqd, Location otherLocationReqd) {
		this.reqsActivityCompletion = reqsActivityCompletion;
		this.numActAttemptsReqd = numActAttemptsReqd;
		this.otherLocationReqd = otherLocationReqd;
	}
	
	// ACCESSORS
	public boolean isUnlocked() {
		return (!reqsActivityCompletion) 
				&& (numActAttemptsReqd == 0)
				&& (otherLocationReqd == null);
	}
	
	// MUTATORS
	public void noteActivityCompleted() {
		reqsActivityCompletion = false;
	}
	public void noteActivityAttempted() {
		if(numActAttemptsReqd > 0)
			numActAttemptsReqd--;	
	}
	public void noteLocationReached(Location loc) {
		if(otherLocationReqd == null)
			return;
		if(otherLocationReqd.equals(loc))
			otherLocationReqd = null;
	}
}
