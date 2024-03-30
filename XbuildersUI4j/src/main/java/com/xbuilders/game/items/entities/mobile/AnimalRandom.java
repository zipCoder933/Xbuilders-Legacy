/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.xbuilders.game.items.entities.mobile;

import com.xbuilders.engine.items.entity.Entity;
import com.xbuilders.engine.utils.math.MathUtils;
import com.xbuilders.engine.utils.random.FastNoise;
import java.util.Random;

/**
 *
 * @author zipCoder933
 */
public class AnimalRandom {

    /**
     * @return the noise
     */
    public FastNoise getNoise() {
        return noise;
    }

    /**
     * @return the random
     */
    public Random getRandom() {
        return random;
    }

    /**
     * @return the seed
     */
    public int getSeed() {
        return seed;
    }

    public int nextInt(int lowerBound, int upperBound) {
        return getRandom().nextInt(upperBound - lowerBound) + lowerBound;
    }

    public float nextFloat(float lowerBound, float upperBound) {
        return (getRandom().nextFloat() * upperBound - lowerBound) + lowerBound;
    }

    public long nextLong(long lowerBound, long upperBound) {
        return (long) ((getRandom().nextFloat() * upperBound - lowerBound) + lowerBound);
    }

    /**
     * Returns the next pseudorandom, uniformly distributed {@code boolean}
     * value from this random number generator's sequence. The general contract
     * of {@code nextBoolean} is that one {@code boolean} value is
     * pseudorandomly generated and returned. The values {@code true} and
     * {@code false} are produced with (approximately) equal probability.
     *
     * <p>
     * The method {@code nextBoolean} is implemented by class {@code Random} as
     * if by:
     * <pre> {@code
     * public boolean nextBoolean() {
     *   return next(1) != 0;
     * }}</pre>
     *
     * @return the next pseudorandom, uniformly distributed {@code boolean}
     * value from this random number generator's sequence
     * @since 1.2
     */
    public boolean nextBoolean() {
        return getRandom().nextBoolean();
    }

    /**
     * Returns the next pseudorandom, uniformly distributed {@code float} value
     * between {@code 0.0} and {@code 1.0} from this random number generator's
     * sequence.
     *
     * <p>
     * The general contract of {@code nextFloat} is that one {@code float}
     * value, chosen (approximately) uniformly from the range {@code 0.0f}
     * (inclusive) to {@code 1.0f} (exclusive), is pseudorandomly generated and
     * returned. All 2<sup>24</sup> possible {@code float} values of the form
     * <i>m&nbsp;x&nbsp;</i>2<sup>-24</sup>, where <i>m</i> is a positive
     * integer less than 2<sup>24</sup>, are produced with (approximately) equal
     * probability.
     *
     * <p>
     * The method {@code nextFloat} is implemented by class {@code Random} as if
     * by:
     * <pre> {@code
     * public float nextFloat() {
     *   return next(24) / ((float)(1 << 24));
     * }}</pre>
     *
     * <p>
     * The hedge "approximately" is used in the foregoing description only
     * because the next method is only approximately an unbiased source of
     * independently chosen bits. If it were a perfect source of randomly chosen
     * bits, then the algorithm shown would choose {@code float} values from the
     * stated range with perfect uniformity.<p>
     * [In early versions of Java, the result was incorrectly calculated as:
     * <pre> {@code
     *   return next(30) / ((float)(1 << 30));}</pre> This might seem to be
     * equivalent, if not better, but in fact it introduced a slight
     * nonuniformity because of the bias in the rounding of floating-point
     * numbers: it was slightly more likely that the low-order bit of the
     * significand would be 0 than that it would be 1.]
     *
     * @return the next pseudorandom, uniformly distributed {@code float} value
     * between {@code 0.0} and {@code 1.0} from this random number generator's
     * sequence
     */
    public float nextFloat() {
        return getRandom().nextFloat();
    }

    /**
     * Returns the next pseudorandom, uniformly distributed {@code int} value
     * from this random number generator's sequence. The general contract of
     * {@code nextInt} is that one {@code int} value is pseudorandomly generated
     * and returned. All 2<sup>32</sup> possible {@code int} values are produced
     * with (approximately) equal probability.
     *
     * <p>
     * The method {@code nextInt} is implemented by class {@code Random} as if
     * by:
     * <pre> {@code
     * public int nextInt() {
     *   return next(32);
     * }}</pre>
     *
     * @return the next pseudorandom, uniformly distributed {@code int} value
     * from this random number generator's sequence
     */
    public int nextInt() {
        return getRandom().nextInt();
    }

    /**
     * Returns a pseudorandom, uniformly distributed {@code int} value between 0
     * (inclusive) and the specified value (exclusive), drawn from this random
     * number generator's sequence. The general contract of {@code nextInt} is
     * that one {@code int} value in the specified range is pseudorandomly
     * generated and returned. All {@code bound} possible {@code int} values are
     * produced with (approximately) equal probability. The method
     * {@code nextInt(int bound)} is implemented by class {@code Random} as if
     * by:
     * <pre> {@code
     * public int nextInt(int bound) {
     *   if (bound <= 0)
     *     throw new IllegalArgumentException("bound must be positive");
     *
     *   if ((bound & -bound) == bound)  // i.e., bound is a power of 2
     *     return (int)((bound * (long)next(31)) >> 31);
     *
     *   int bits, val;
     *   do {
     *       bits = next(31);
     *       val = bits % bound;
     *   } while (bits - val + (bound-1) < 0);
     *   return val;
     * }}</pre>
     *
     * <p>The hedge "approx
     * imately" is used in the foregoing description only because the next
     * method is only approximately an unbiased source of independently chosen
     * bits. If it were a perfect source of randomly chosen bits, then the
     * algorithm shown would choose {@code int} values from the stated range
     * with perfect uniformity.
     * <p>
     * The algorithm is slightly tricky. It rejects values that would result in
     * an uneven distribution (due to the fact that 2^31 is not divisible by n).
     * The probability of a value being rejected depends on n. The worst case is
     * n=2^30+1, for which the probability of a reject is 1/2, and the expected
     * number of iterations before the loop terminates is 2.
     * <p>
     * The algorithm treats the case where n is a power of two specially: it
     * returns the correct number of high-order bits from the underlying
     * pseudo-random number generator. In the absence of special treatment, the
     * correct number of <i>low-order</i> bits would be returned. Linear
     * congruential pseudo-random number generators such as the one implemented
     * by this class are known to have short periods in the sequence of values
     * of their low-order bits. Thus, this special case greatly increases the
     * length of the sequence of values returned by successive calls to this
     * method if n is a small power of two.
     *
     * @param bound the upper bound (exclusive). Must be positive.
     * @return the next pseudorandom, uniformly distributed {@code int} value
     * between zero (inclusive) and {@code bound} (exclusive) from this random
     * number generator's sequence
     * @throws IllegalArgumentException if bound is not positive
     * @since 1.2
     */
    public int nextInt(int val) {
        return getRandom().nextInt(val);
    }

    private FastNoise noise;
    private Random random;
    private Entity e;

    public AnimalRandom(Entity e) {
        super();
        noiseInt = 0;
        this.e = e;
        noise = new FastNoise();
        random = new Random();
    }

    public synchronized void setSeed(int seed) {
        noiseInt = 0;
        this.seed = seed;
        getRandom().setSeed(seed);
        getNoise().SetSeed((int) seed);
    }

    int noiseInt = 0;
    private int seed = 0;

    protected void updateNoiseSeed() {
        noiseInt += 1;
        if (noiseInt > 100000) {
            noiseInt = 0;
        }
    }

    public float noise(float frequency) {
        return getNoise().GetValueFractal(seed, (noiseInt * frequency) - getSeed());
    }

    public float noise(float frequency, float min, float max) {
        return MathUtils.map(noise(frequency), -1, 1, min, max);
    }

}
