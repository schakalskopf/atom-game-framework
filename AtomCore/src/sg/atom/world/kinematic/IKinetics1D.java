package sg.atom.world.kinematic;

/**
 * In addition to the initial rate does not apply pressure to the outside of the
 * situation directly, <br> By air, the object is changed, depending on the
 * speed used when the resistance value.
 *
 * <br> A = ct of acceleration movement as, lim t-> infinity when a = 0, v = 0
 * that is the case.
 *
 * @author mulova
 */
public interface IKinetics1D {

    /**
     * 초기 값들을 설정한다.
     *
     * @param d0
     * @param v0
     * @param a0
     */
    public void init(final double d0, final double v0, final double a0);

    /**
     * 가속도를 얻는다.
     *
     * @param time
     * @return
     */
    public double getAccel(final double time);

    public double getCoefficient();

    public void setCoefficient(double coeff);

    /**
     * 속도를 얻는다.
     *
     * @param time
     * @return
     */
    public double getVelocity(final double time);

    /**
     * 위치를 얻는다.
     *
     * @param time
     * @return
     */
    public double getDistance(final double time);

    /**
     * 거리 d에 이르는 시간을 구한다.
     *
     * @param d
     * @return	해가 없으면 null
     */
    public double[] getTimes(double d);

    /**
     *
     * 구현마다 반환하는 값이 다르다.
     *
     * @param d 없을 경우 Float.POSITIVE_INFINITY
     * @return store중 의미있는 element를 반환한다.
     */
    public double getTime(double d);
}
