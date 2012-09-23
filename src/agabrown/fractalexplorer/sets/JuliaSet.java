package agabrown.fractalexplorer.sets;

import java.util.ArrayList;
import java.util.List;

/**
 * Provides methods for evaluating whether or not a point belongs to the Julia
 * set. The polynomial f(z)=z<sup>2</sup>+&mu; is iterated with starting point
 * z<sub>0</sub>.
 * 
 * @author agabrown 26 Jul 2012
 * 
 */
public final class JuliaSet implements FractalSet {

  /**
   * If the complex number resulting from iterating the Julia set grows beyond
   * this limit in magnitude, it is assumed to go off to infinity (and then thus
   * not belong to the Julia set).
   */
  private static final double BOUND = 2;

  /**
   * Square of BOUND.
   */
  private static final double BOUND_SQUARE = BOUND * BOUND;

  /**
   * Name of this fractal set.
   */
  private static final String NAME = "Julia set";

  /**
   * Unicode code for the letter &mu;.
   */
  private static final int MU_UNICODE = 0x03BC;

  /**
   * Contains the constant parts of the info text lines.
   */
  private ArrayList<StringBuilder> infoLineParts;

  /**
   * Real part of Julia set parameter &mu;.
   */
  private double muReal;

  /**
   * Imaginary part of Julia set parameter &mu;.
   */
  private double muImaginary;

  /**
   * Default constructor. Sets the &mu; parameter to 0+i*0.
   */
  public JuliaSet() {
    this(0.0, 0.0);
  }

  /**
   * Constructor. Sets the value for &mu;.
   * 
   * @param muRe
   *          Real part of &mu;.
   * @param muIm
   *          Imaginary part of &mu;.
   */
  public JuliaSet(final double muRe, final double muIm) {
    muReal = muRe;
    muImaginary = muIm;
    intializeInfoLines();
  }

  /**
   * Initialize the constant parts of the lines with information on the Julia
   * Set parameters.
   */
  private void intializeInfoLines() {
    infoLineParts = new ArrayList<StringBuilder>();
    StringBuilder line = new StringBuilder("Re(");
    line.appendCodePoint(MU_UNICODE);
    line.append(") = ");
    infoLineParts.add(line);
    line = new StringBuilder("Im(");
    line.appendCodePoint(MU_UNICODE);
    line.append(") = ");
    infoLineParts.add(line);
    infoLineParts.trimToSize();
  }

  /**
   * Set the value of &mu;.
   * 
   * @param muRe
   *          Real part of &mu;.
   * @param muIm
   *          Imaginary part of &mu;.
   */
  public void setMuParameter(final double muRe, final double muIm) {
    muReal = muRe;
    muImaginary = muIm;
  }

  /**
   * Obtain the real part of &mu;.
   * 
   * @return Value of Re(&mu;).
   */
  public double getMuReal() {
    return muReal;
  }

  /**
   * Obtain the imaginary part of &mu;.
   * 
   * @return Value of Im(&mu;).
   */
  public double getMuImaginary() {
    return muImaginary;
  }

  /*
   * (non-Javadoc)
   * 
   * @see agabrown.fractalexplorer.sets.FractalSet#isPointInSet(double, double,
   * int)
   */
  @Override
  public boolean isPointInSet(final double real, final double imaginary, final int maxIter) {
    if (iterateSeries(real, imaginary, maxIter) >= maxIter) {
      return true;
    } else {
      return false;
    }
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
    final ArrayList<String> infoLines = new ArrayList<>();
    StringBuilder line = new StringBuilder(infoLineParts.get(0));
    line.append(muReal);
    infoLines.add(line.toString());
    line = new StringBuilder(infoLineParts.get(1));
    line.append(muImaginary);
    infoLines.add(line.toString());
    infoLines.trimToSize();
    return infoLines;
  }

  /**
   * Check if the complex input number is in the Julia set given the parameter
   * &mu;.
   * 
   * @param real
   *          Real part of complex number (z<sub>0</sub>) to check
   * @param imaginary
   *          Imaginary part of complex number (z<sub>0</sub>) to check
   * @param maxIter
   *          Maximum number of iterations to decide on whether or not the
   *          number is in the set. Numbers for which maxIter is exceeded are
   *          considered to be part of the set.
   * 
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
