package sg.atom.world.kinematic;


/**
 * 초기 속도 이외에 외부에서 직접적으로 힘을 가하지 않는 상황에서,<br>
 * 공기에 의해 물체가 속도에 따라 저항값이 변하는 경우에 사용한다.<br>
 * v = v0*e^(ct)<br>
 * a = v0*c*e^(ct)<br>
 * d = d0 + v0/c * (e^(ct) - 1)<br> 
 * k = c*v0 <br>
 * 
 * 
 * a = c*v 인 가속도 운동 으로써, lim t->무한 일때 a = 0, v = 0 이 되는 경우이다.
 * 
 * @author mulova
 */
public final class AirResistance1D implements IKinetics1D {

	private double	d0;
	private double	v0;				// c == 0 일 때만 사용된다.

	/**
	 * coefficient
	 */
	private double	c;
	private double	k;

	// cache
	private double	cSquare;
	private double	kInverse;
	private double	cInverse;
	private double	cInverseSquared;
	private Exp		expCache;



	public AirResistance1D() {
		this.expCache = new Exp(3);
	}


	/**
	 * 초기 가속도는 속도에 의해 결정된다.<br>
	 * 이후 속도, 가속도는 c 값에 의해 결정된다. c = 1/v0
	 * 
	 * @param d0
	 *            공의 시작 위치
	 * @param v0
	 *            공의 시작 속도
	 * @param a0
	 *            공의 가속, 감속에 사용되는 계수.
	 */
	public void init(final double d0_, final double v0_, final double a0_) {
		setCoefficient(a0_);
		this.d0 = d0_;
		this.v0 = v0_;
		this.k = this.c * v0_;
		this.kInverse = this.k == 0 ? 0 : 1/this.k;
	}


	/**
	 * 값은 음수이어야 한다. 절대값이 클수록 빠르게 감속된다. 0일 경우 등속도 운동이 된다.
	 * 
	 * @param c_
	 */
	@Override
	public void setCoefficient(final double c_) {
		this.c = c_;
		this.expCache.setCoefficient(c_);
		if (this.c != 0) {
			if (this.c > 0)
				this.c *= -1f;
			this.cInverse = 1f / c_;
			this.cInverseSquared = this.cInverse * this.cInverse;
			this.cSquare = c_ * c_;
		} else {
			this.cInverse = 0;
			this.cInverseSquared = 0;
			this.cSquare = 0;
			this.k = 0;
			this.kInverse = 0;
		}
	}


	public double getCoefficient() {
		return this.c;
	}


	/**
	 * a= k e^(ct) (c < 0)
	 * 
	 */
	public double getAccel(final double time) {
		final double exp = this.expCache.get(time);
		return this.k * exp;
	}


	/**
	 * v = k a/c 혹은 v = K (e^(ct))/c
	 * 
	 */
	public double getVelocity(final double time) {
		if (this.c == 0)
			return this.v0;
		final double exp = this.expCache.get(time);
		return this.k * this.cInverse * exp;
	}


	/*
	 * (non-Javadoc)
	 * d = k { a/c^2 - 1/c^2 } 혹은 d = k { (e^ct)/c^2 - 1/c^2 } 혹은
	 * d = d0 + k { v/c - 1/c^2 }
	 * 
	 */
	public double getDistance(final double time) {
		final double exp = this.expCache.get(time);
		if (this.k == 0) {
			return this.d0 + this.v0 * time;
		}
		return this.d0 + this.k * this.cInverseSquared * (exp - 1);
	}
	
	/**
	 * t = 1/c * ((d-d0)*c^2/k + 1)
	 * @param d
	 * @return
	 */
	public double[] getTimes(double d) {
		double time = getTime(d);
		if (Double.isInfinite(time))
			return null;
		return new double[]{time};
		
	}
	
	/**
	 * t = 1/c * log((d-d0)*c^2/k + 1)
	 * @param d
	 * @return
	 */
	@Override
	public double getTime(double d) {
		if (d == this.d0)
			return Float.POSITIVE_INFINITY;
		if (this.c == 0)
			return (d - this.d0)/this.v0;
		double argument = (d - this.d0) * this.cSquare*this.kInverse + 1;
		if (argument >= 1 || argument <= 0)
			return Float.POSITIVE_INFINITY;
		return this.cInverse * Math.log(argument);
	}

}
