package sg.atom.world.physics.kinematic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * 
 * @author mulova
 */
public class UniformAccel1D implements IKinetics1D {
	private static final Logger log = LoggerFactory.getLogger(UniformAccel1D.class);

	private double	d0;
	private double	v0;
	private double	a0;
	
	// internal variable to compute faster
	private double aInverse;


	public UniformAccel1D() {
	}


	public double getDistance(final double time) {
		return this.d0 + time * (this.a0 * time * 0.5f + this.v0);
	}


	public double getVelocity(final double time) {
		return this.v0 + this.a0 * time;
	}


	public void init(final double d0_, final double v0_, final double a0_) {
		this.d0 = d0_;
		this.v0 = v0_;
		setAccel(a0_);
	}


	public void setAccel(double a) {
		this.a0 = a;
		if (a != 0)
			this.aInverse = 1/a;
		else
			this.aInverse = 0;
	}


	@Override
	public double getAccel(double time) {
		return this.a0;
	}


	/**
	 * ���� �ð� ���Ŀ� �������� �����ϴ� �ð��� ���Ѵ�.
	 * �ذ� ������ null �� ��ȯ�Ѵ�.
	 * h = 1/2*a*t*t + v0*t + d0
	 * t = +sqrt(2/a*(v0*v0/2a - d0 + h)) - v0/a
	 * t = -sqrt(2/a*(v0*v0/2a - d0 + h)) - v0/a
	 * @see mulova.common.physics.IKinetics1D#getTimes(double)
	 */
	@SuppressWarnings("unqualified-field-access")
	@Override
	public double[] getTimes(double d) {
		if (this.a0 == 0f) {
			if (v0 == 0)
				return null;
			double time = (d - d0)/v0;
			if (time <= 0)
				return null;
			return new double[] {time};
		}
		
		double temp = v0 * aInverse * -1;
		final double c = temp * temp + 2 * aInverse * (d - d0);
		if (c > 0) {
			double sqrt = Math.sqrt(c);
			double first = temp-sqrt;
			if (first <= 0)
				return new double[] { temp + sqrt };
			else 
				return new double[] { first, temp + sqrt}; 
		} else if (c == 0) {
			return new double[] { temp };
		} else
			return null;
	}


	/**
	 * getTimes() �� �����ϴ�. ������ ���� ȣ������ �ʴ´�.<br>
	 * �ذ� �ΰ��� ��� ������ ���� ��ȯ�Ѵ�.
	 * @see mulova.common.physics.IKinetics1D#getTime(double)
	 */
	@SuppressWarnings("unqualified-field-access")
	@Override
	public double getTime(double d) {
		if (this.a0 == 0f) {
			if (v0 != 0)
				return (d - d0)/v0;
			return Float.POSITIVE_INFINITY;
		}
		
		double temp = v0 * aInverse * -1;
		final double c = temp * temp + 2 * aInverse * (d - d0);
		if (c > 0) {
			return temp + Math.sqrt(c); 
		} else if (c == 0) {
			return -temp;
		} else
			return Float.POSITIVE_INFINITY;
	}


	@Override
	public double getCoefficient() {
		log.warn("Improper call");
		return 0;
	}


	@Override
	public void setCoefficient(double coeff) {
		log.warn("Improper call");
	}

}
