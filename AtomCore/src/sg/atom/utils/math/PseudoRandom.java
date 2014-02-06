package sg.atom.utils.math;

import java.util.Random;

/**
 * Apache Commons 없이 NormalDistribution만을 쓰고 싶을때 사용한다. Apache Commons Math의
 * NormalDistributionImpl을 사용하라. probability distribution function of normal
 * distribution. http://mathworld.wolfram.com/NormalDistribution.html
 *
 * @author mulova
 *
 */
public class PseudoRandom {

    private static final double NDConstant = 1.0 / Math.sqrt(2.0 * Math.PI);
    private double mean;
    private double variance;
    private double deviation;
    private final Random rand;

    public PseudoRandom() {
        this(0);
        rand.setSeed(hashCode());
    }

    public PseudoRandom(long seed) {
        this(0, 1, seed);
    }

    public PseudoRandom(double mean, double variance) {
        this(mean, variance, 0);
        rand.setSeed(hashCode());
    }

    /**
     * @param mean 평균
     * @param variance 분산
     */
    public PseudoRandom(double mean, double variance, long seed) {
        this.mean = mean;
        setVariance(variance);
        rand = new Random(seed);
    }

    /**
     * 정규분포 확률을 반환한다.
     *
     * @param x
     * @return
     * @author mulova
     */
    public double getNDProbability(double x) {
        return normalDistribution(mean, variance, deviation, x);
    }

    /**
     * @param mean
     * @param variance
     * @param x
     * @return
     * @author mulova
     */
    public static double normalDistribution(double mean, double variance, double x) {
        final double deviation = Math.sqrt(variance);
        return normalDistribution(mean, variance, deviation, x);
    }

    private static double normalDistribution(double mean, double variance, double deviation, double x) {
        final double d = x - mean;
        double exponent = -d * d / (2.0 * variance);
        return (NDConstant / deviation) * Math.exp(exponent);
    }

    /**
     * 지정된 x 값에 대해 random 값이 확률적으로 통과하는지 여부를 반환한다.
     *
     * @param x
     * @return
     * @author mulova
     */
    public boolean bool(double x) {
        return rand.nextDouble() <= getNDProbability(x);
    }

    /*	public double nextNDProbability() {
     return getNDProbability(nextDouble());
     }*/
    public double getMean() {
        return mean;
    }

    public void setMean(double mean) {
        this.mean = mean;
    }

    public double getVariance() {
        return variance;
    }

    public void setVariance(double variance) {
        this.variance = variance;
        this.deviation = Math.sqrt(variance);
    }

    public double nextDouble() {
        return mean + rand.nextGaussian() * variance;
    }

    public float nextFloat() {
        return (float) nextDouble();
    }

    public int nextInt() {
        return (int) nextDouble();
    }

    public int nextIntClamp(int min, int max) {
        double localMax = nextDouble();
        int v = (int) (rand.nextDouble() * localMax);
        return MathUtils.clamp(v, min, max);
    }

    public double nextDoubleClamp(int min, int max) {
        double localMax = nextDouble();
        double v = rand.nextDouble() * localMax;
        return MathUtils.clamp(v, min, max);
    }

    public int nextInt(int mean, int variance) {
        return (int) nextDouble(mean, variance);
    }

    public double nextDouble(double mean, double variance) {
        return mean + variance * rand.nextGaussian();
    }

    public float nextFloat(float mean, float variance) {
        return mean + variance * (float) rand.nextGaussian();
    }

    /*public static void main(String[] args) {
     PseudoRandom rand = new PseudoRandom(0, 1, System.currentTimeMillis());
     int count30 = 0;
     int count50 = 0;
     int count80 = 0;
     for (int i = 0; i < 100; i++) {
     double d = rand.nextNDProbability()*2;
     if (d < 0.3) {
     count30++;
     }
     if (d < 0.5) {
     count50++;
     }
     if (d < 0.8) {
     count80++;
     }
     System.out.println(d);
     }
     System.out.println(count30);
     System.out.println(count50);
     System.out.println(count80);
     }*/
    public static Random createRandom() {
        Random rand = new Random();
        rand.setSeed(rand.hashCode());
        return rand;
    }
}
