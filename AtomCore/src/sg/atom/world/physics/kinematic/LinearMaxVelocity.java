package sg.atom.world.physics.kinematic;

/**
 * 속도가 선형적으로 증가하다가 Vmax에서 더이상 증가하지 않는 운동.
 *
 *    /------------<br> /<br> /<br> /<br>
 *
 * Tip = (Vmax-V0)/C if t < Tip d = d0 + 1/2 c t^2 + V0 t else d = d0 +
 * (Vmax-V0)^2 / 2C + V0 (Vmax-V0) / c + Vmax (t - (Vmax-V0)/C) = d0 -
 * (Vmax+V0)^2/2C + Vmax*t
 *

 *
 * @author mulova
 *
 */
public class LinearMaxVelocity implements IKinetics1D {

    /**
     * inflection point 변곡점<br> Tip = Vmax / C
     */
    private double Tip;
    private double Dip;
    private double Vmax;
    private double v0;
    private double c;
    private double d0;

    public LinearMaxVelocity(double maxVelocity) {
        this.Vmax = maxVelocity;
    }

    /**
     * a0는 기울기 c
     *
     * @see mulova.common.physics.IKinetics1D#init(double, double, double)
     */
    @Override
    public void init(double d0_, double v0_, double a0_) {
        init(d0_, v0_, a0_, this.Vmax);
    }

    /**
     * a0는 기울기 c
     *
     * @see mulova.common.physics.IKinetics1D#init(double, double, double)
     */
    public void init(double d0_, double v0_, double a0_, double Vmax_) {
        if (a0_ == 0) {
            throw new RuntimeException("acceleration must not be zero");
        }
        this.d0 = d0_;
        this.v0 = v0_;
        this.c = a0_;
        this.Vmax = Vmax_;
        setInflectionPoint();
    }

    @SuppressWarnings("unqualified-field-access")
    private void setInflectionPoint() {
        Tip = (Vmax - v0) / c;
        Dip = distanceBeforeInflection(Tip);
    }

    public double getMaxVelocity() {
        return this.Vmax;
    }

    public double getInflectionTime() {
        return this.Tip;
    }

    public double getInflectionDistance() {
        return this.Dip;
    }

    @Override
    public double getAccel(double time) {
        if (time < this.Tip) {
            return this.c;
        }
        return 0;
    }

    /**
     * if t < Vmax/C d = d0 + 1/2 c t^2 + v0 t else d = d0 + Vmax^2 / 2c + v0
     * Vmax / c + Vmax (t - Vmax/c)
     *

     *
     * @see mulova.common.physics.IKinetics1D#getDistance(double)
     */
    @SuppressWarnings("unqualified-field-access")
    @Override
    public double getDistance(double time) {
        if (time < this.Tip) {
            return distanceBeforeInflection(time);
        }
        return Dip + (time - Tip) * Vmax;
    }

    @SuppressWarnings("unqualified-field-access")
    @Override
    public double getTime(double d) {
        if (d < Dip) {
            return (double) Math.sqrt((d - d0 + v0 * v0 / (2 * c)) * 2 / c) - v0 / c;
        }
        return (d - Dip) / Vmax + Tip;
    }

    @Override
    public double[] getTimes(double d) {
        double t = getTime(d);
        return new double[]{t};
    }

    @SuppressWarnings("unqualified-field-access")
    @Override
    public double getVelocity(double time) {
        if (time < Tip) {
            return c * time;
        }
        return Vmax;
    }

    /**
     * Get distance from time t1 to time t2
     *
     * @param t1 start time
     * @param t2 end time
     * @return
     */
    @SuppressWarnings("unqualified-field-access")
    public double distanceDiff(double t1, double t2) {
        if (t1 > t2) {
            throw new IllegalArgumentException("t1 should be smaller than t2");
        }
        if (t2 <= Tip) {
            return distanceBeforeInflection(t1, t2);
        } else if (Tip <= t1) {
            return Vmax * (t2 - t1);
        } else {
            return (Tip - t1) * (0.5f * c * (Tip + t1) + v0) + Vmax * (t2 - Tip);
        }
    }

    /**
     * 속도가 증가하는 부분에서의 거리 증가분.
     *
     * @param t1
     * @param t2
     * @return
     */
    @SuppressWarnings("unqualified-field-access")
    protected double distanceBeforeInflection(double t1, double t2) {
        return (t2 - t1) * (0.5f * c * (t2 + t1) + v0);
    }

    /**
     * 속도가 증가하는 시점에서의 거리.
     *
     * @param t
     * @return
     */
    @SuppressWarnings("unqualified-field-access")
    protected double distanceBeforeInflection(double t) {
        return d0 + (0.5f * c * t + v0) * t;
    }

    /**
     * 속도가 일정한 시점에서의 거리.
     *
     * @param t
     * @return
     */
    @SuppressWarnings("unqualified-field-access")
    protected double distanceAfterInflection(double t) {
        return Dip + Vmax * (t - Tip);
    }

    @Override
    public double getCoefficient() {
        assert false;
        return 0;
    }

    @Override
    public void setCoefficient(double coeff) {
        assert false;
    }
}
