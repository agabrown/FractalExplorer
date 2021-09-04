package agabrown.fractalexplorer.colours;

import org.apache.commons.math3.complex.Complex;

import java.util.List;

/**
 * Implements the smooth iteration count colouring algorithm from the master's
 * thesis by Harkonen
 * (http://jussiharkonen.com/files/on_fractal_coloring_techniques(lo-res).pdf).
 * Intended for Fractals based on the dynamic system z<sup>p</sup>+c.
 *
 * @author agabrown Aug 2014 - Sep 2021
 */
public final class SmoothIterationCount implements ColouringAlgorithm {

    /**
     * One over the natural logarithm of the power of z used in the iteration
     * function z<sup>p</sup>+c.
     */
    private final double invLnPower;

    /**
     * The natural logarithm of the bailout value used for the iteration function
     * z<sup>p</sup>+c.
     */
    private final double lnBailout;

    /**
     * Constructor. initializes value of p that was used in the fractal generating
     * function.
     *
     * @param p       Power of z used in the iteration function z<sup>p</sup>+c.
     * @param bailout The bailout value used for the iteration function z<sup>p</sup>+c.
     */
    public SmoothIterationCount(final double p, final double bailout) {
        invLnPower = 1.0 / Math.log(p);
        lnBailout = Math.log(bailout);
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * agabrown.fractalexplorer.colours.ColouringAlgorithm#getPixelValue(java.
     * util.List)
     */
    @Override
    public double getPixelValue(final List<Complex> fnz) {
        return fnz.size() + 1 + invLnPower * Math.log(lnBailout / Math.log(fnz.get(fnz.size() - 1).abs()));
    }

}
