package agabrown.fractalexplorer.sets;

import java.util.ArrayList;
import java.util.List;

/**
 * Provides methods for evaluating whether a point z<sub>0</sub> belongs to a Julia set <strong>according to the escape
 * time algorithm</strong>. The polynomial f(z)=z<sup>2</sup>+&mu; is iterated with starting point z<sub>0</sub>. The
 * value of &mu; is kept fixed, while z<sub>0</sub> is varied over the complex plane.
 *
 * @author agabrown Jul 2012 - Sep 2021
 */
public final class JuliaSet implements FractalSet {

    /**
     * If the complex number resulting from iterating the Julia set grows beyond this limit in magnitude, it is assumed
     * to go off to infinity (and then thus not belong to the Julia set).
     */
    private static final double BOUND = 2.0;

    /**
     * Square of BOUND.
     */
    private static final double BOUND_SQUARE = BOUND * BOUND;

    /**
     * Name of this fractal set.
     */
    private static final String NAME = "Julia set";

    /**
     * The unicode value for the letter &mu;.
     */
    private static final int MU_UNICODE = 0x03BC;

    /**
     * Real part of Julia set parameter &mu;.
     */
    private final double muReal;

    /**
     * Imaginary part of Julia set parameter &mu;.
     */
    private final double muImaginary;

    /**
     * Contains the info text lines.
     */
    private ArrayList<String> infoLines;

    /**
     * Constructor. Sets the value for &mu;.
     *
     * @param muRe Real part of &mu;.
     * @param muIm Imaginary part of &mu;.
     */
    public JuliaSet(final double muRe, final double muIm) {
        muReal = muRe;
        muImaginary = muIm;
        initializeInfoLines();
    }

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
        return infoLines;
    }

    /**
     * Initialize the constant parts of the lines with information on the Julia Set parameters.
     */
    private void initializeInfoLines() {
        infoLines = new ArrayList<>();
        StringBuilder line = new StringBuilder("Re(");
        line.appendCodePoint(MU_UNICODE);
        line.append(") = ");
        line.append(muReal);
        infoLines.add(line.toString());
        line = new StringBuilder("Im(");
        line.appendCodePoint(MU_UNICODE);
        line.append(") = ");
        line.append(muImaginary);
        infoLines.add(line.toString());
        infoLines.trimToSize();
    }

    /**
     * Check if the complex input number is in the Julia set given the parameter &mu;.
     *
     * @param real      Real part of complex number (z<sub>0</sub>) to check
     * @param imaginary Imaginary part of complex number (z<sub>0</sub>) to check
     * @param maxIter   Maximum number of iterations to decide on whether the number is in the set. Numbers for which
     *                  maxIter is exceeded are considered to be part of the set.
     * @return Number of iterations used.
     */
    private int iterateSeries(final double real, final double imaginary, final int maxIter) {
        int iter = 0;
        double zReal = real;
        double zImaginary = imaginary;
        double zRealTemp;
        while (zReal * zReal + zImaginary * zImaginary <= BOUND_SQUARE && iter < maxIter) {
            zRealTemp = zReal * zReal - zImaginary * zImaginary + muReal;
            zImaginary = 2.0 * zReal * zImaginary + muImaginary;
            zReal = zRealTemp;
            iter = iter + 1;
        }
        return iter;
    }

}
