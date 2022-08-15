package location;

public abstract class StoryArea extends Location {

	public StoryArea() {
		this("Story Area");
	}
	// base constructor
	public StoryArea(String nm) {
		super(nm);
		this.setMapDescription("A place for plot things.");
		this.setLocalDescription("Pay attention.");
	}

}
