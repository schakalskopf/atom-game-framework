package sg.atom.world.kinematic;


/**
 * e^(ct) 값을 계산한다.<br>
 * 동일한 time이 여러번 불린다는 가정하에, 단순 caching을 수행한다.<br> 
 * @author mulova
 *
 */
public class Exp {
	
	private double[]	time;
	private double[]	exp;
	private double	c;

	public Exp(int cacheSize) {
		this.time = new double[cacheSize];
		this.exp = new double[cacheSize];
	}
	
	public void setCoefficient(double c) {
		this.c = c;
		for (int i = 0; i < this.time.length; i++) {
			this.time[i] = Float.NaN;
		}
	}
	
	public double get(final double time) {
		if (this.c == 0)
			return 0;
		final int size = this.time.length;

		for (int i = 0; i < size; i++) {
			if (time == this.time[i])
				return this.exp[i];
		}
		for (int i = 0; i < size-2; i++) {
			this.time[i] = this.time[i+1];
			this.exp[i] = this.exp[i+1];
		}
		
		this.time[size-1] = time;
		this.exp[size-1] = Math.exp(this.c * time); 
		return this.exp[size-1];
	}

}
