package sg.atom.world.physics.kinematic;



/**
 * 가속도가 시간에 따라 지수함수로 변화하는 함수.<br>
 * v = v0 - k + k*e^(ct)<br>
 * a = k*c*e^(ct)<br>
 * d = (v0-k)*t + k/c * (e^(ct)-1)+ d0<br>
 * 
 * @author mulova
 */
public class ExponentialAccel1D implements IKinetics1D {

	private double d0;
	private double v0;
	private double a0;
	private double c;
	private double k;
	
	// internal variable to compute faster
	private double kc;
	private double kDivC;
	private double v0MinusK;
	
	private Exp	exp;


	public ExponentialAccel1D() {
		this.exp = new Exp(3);
	}


	/**
	 * d = (v0-k)*t + k/c * (e^(ct)-1)+ d0 
	 * @see mulova.common.physics.IKinetics1D#getDistance(double)
	 */
	@Override
	public double getDistance(final double t) {
		return v0MinusK*t + kDivC * ( exp.get(t) - 1) + d0;
	}


	/**
	 * v = v0 - k + k*e^(ct)<br>
	 * @see mulova.common.physics.IKinetics1D#getVelocity(double)
	 */
	@Override
	public double getVelocity(final double t) {
		return v0MinusK + k * exp.get(t);
	}


	/**
	 * init(double, double, double, double); 을 사용할 것을 권장한다.
	 * c값이 바뀌지 않는 경우라면 사용한다. 
	 * @see mulova.common.physics.IKinetics1D#init(double, double, double)
	 */
	@Override
	public void init(final double d0, final double v0, final double k) {
		if (c == 0)
			throw new RuntimeException("use init(d0, v0, k, c)");
		this.d0 = d0;
		this.v0 = v0;
		setAccel(k);
	}
	
	
	public void init(final double d0, final double v0, final double k, double c) {
		if (c == 0)
			throw new IllegalArgumentException("c can't be zero");
		setCoefficient(c);
		init(d0, v0, k);
	}
	
	
	/**
	 * 목적지까지 가는 시간이 정해져 있을때 coefficient를 자동으로 설정한다.
	 * @param d0
	 * @param v0
	 * @param c
	 * @param d distance
	 * @param t time
	 */
	public void initBydDistance(final double d0, final double v0, double c, final double d, double t) {
		double k = (d - d0 - v0 * t) / ((Math.exp(c*t)-1)/c - t);
		init(d0, v0, k, c);
	}
	

	public void setAccel(double k) {
		this.k = k;
		this.kc = k*c;
		this.kDivC = k / c;
		this.v0MinusK = v0 - k;
	}
	
	/**
	 * @param k 방향과 배수를 지정한다.
	 * @param c 값이 커질수록 시간에 따라 속도가 지수적으로 증가한다. 0 이어서는 안된다.  
	 */
	@Override
	public void setCoefficient(double c) {
		this.c = c;
		this.exp.setCoefficient(c);
	}
	

	/**
	 * a = k*c*e^(ct)<br>
	 * @see mulova.common.physics.IKinetics1D#getAccel(double)
	 */
	@Override
	public double getAccel(double time) {
		return kc*exp.get(time);
	}


	@Override
	public double[] getTimes(double d) {
		throw new RuntimeException("not supported");
	}


	/** 
	 * 단순 증가/감소 구간일 경우에만 제대로 작동한다.
	 * @see mulova.common.physics.IKinetics1D#getTime(double)
	 */
	public double getTime(double d) {
		return getTime(d, 0);
	}
	
	public double getTime(double d, double startTime) {
		if (d == d0)
			return 0;
		boolean inc = false;
		// 무한 증가, 혹은 무한 감소
		if (c > 0) {
			inc = k*c > 0;
			if (inc ^ d > d0) {
				return Float.NaN;
			}
		} else {
			// d0-v0/c 로 수렴할 경우
			if (v0 == k) {
				inc = v0*c < 0;
				if ((d-d0)*(d-(d0 - v0/c)) > 0) {
					return Float.NaN;
				}
			} else {
				inc = v0-k > 0;
				if (inc ^ d > d0) {
					return Float.NaN;
				}
			}
		}
		
		double tStep = 1;
		double t1 = 0;
		double t2 = tStep;
		double d1 = d0;
		double d2 = getDistance(t2);
		while (inc ^ d < d2) {
			tStep *= 2;
			t1 = t2;
			d1 = d2;
			t2 = t1 + tStep;
			d2 = getDistance(t2);
		}
		while (Math.abs(d2 - d) > 0.01f) {
			double midTime = (t1 + t2)*0.5f;
			double mid = getDistance(midTime);
			if (inc ^ d < mid) {
				t1 = midTime;
				d1 = mid;
			} else {
				t2 = midTime;
				d2 = mid;
			}
		}
		return t2;
	}


	@Override
	public double getCoefficient() {
		return this.c;
	}

}
