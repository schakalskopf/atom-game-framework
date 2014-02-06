package sg.atom.utils.monitor;


public interface IProgress {
	
	public float getProgress();
	
	public void addProgress(float rate);
	
	public void addProgress(float rate, String activity);
	
	public void setProgress(float progress);
	
	public void setProgress(float progress, String activity);
	
	public float increment();
	
	public float increment(int steps);
	
	public float increment(String activity);
	
	public float increment(int steps, String activity);
	
}
