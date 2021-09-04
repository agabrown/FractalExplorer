package agabrown.fractalexplorer.sets;

import java.util.ArrayList;
import java.util.List;

/**
 * Provides methods for evaluating whether a point c belongs to the
 * Mandelbrot set. The set is generated by iterating z<sub>n+1</sub>=z
 * <sup>2</sup><sub>n</sub>+c, with z<sub>0</sub>=0. The value of c is varied
 * over the complex plane.
 *
 * @author agabrown Jul 2012 - Sep 2021
 */
public final class MandelbrotSet implements FractalSet {

    /**
     * Upper limit on magnitude of the complex numbers that can belong to the
     * Mandelbrot set.
     */
    private static final double BOUND = 2.0;

    /**
     * Upper limit on square of the magnitude of the complex numbers that can
     * belong to the Mandelbrot set.
     */
    private static final double BOUND_SQUARE = BOUND * BOUND;

    /**
     * The name of this fractal set.
     */
    private static final String NAME = "Mandelbrot set";

    /*
     * (non-Javadoc)
     *
     * @see agabrown.fractalexplorer.sets.FractalSet#isPointInSet(double, double,
     * int)
     */
    @Override
    public boolean isPointInSet(final double real, final double imaginary, final int maxIter) {
      return iterateSeries(real, imaginary, maxIter) >= maxIter;
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * agabrown.fractalexplorer.sets.FractalSet#numberOfIterationsForPoint(double,
     * double, int)
     */
    @Override
    public int numberOfIterationsForPoint(final double real, final double imaginary, final int maxIter) {
        return iterateSeries(real, imaginary, maxIter);
    }

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public List<String> getInfoLines() {
        return new ArrayList<>(0);
    }

    /**
     * Check if the complex input number is in the Mandelbrot set. Use the fast
     * approach using doubles and the worked out formula for updating the complex
     * number series.
     *
     * @param real      Real part of complex number to check
     * @param imaginary Imaginary part of complex number to check
     * @param maxIter   Maximum number of iterations to decide on whether the
     *                  number is in the set. Numbers for which maxIter is exceeded are
     *                  considered to be part of the set.
     * @return Number of iterations used.
     */
    private int iterateSeries(final double real, final double imaginary, final int maxIter) {
        int iter = 0;
        double zReal = real;
        double zImaginary = imaginary;
        double zRealTemp;
        while (zReal * zReal + zImaginary * zImaginary <= BOUND_SQUARE && iter < maxIter) {
            zRealTemp = zReal * zReal - zImaginary * zImaginary + real;
            zImaginary = 2.0 * zReal * zImaginary + imaginary;
            zReal = zRealTemp;
            iter = iter + 1;
        }
        return iter;
    }

}
