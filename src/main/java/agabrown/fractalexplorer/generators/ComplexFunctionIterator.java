package agabrown.fractalexplorer.generators;

import java.util.ArrayList;
import java.util.List;
import java.util.function.UnaryOperator;

import org.apache.commons.math3.complex.Complex;

/**
 * Implements a complex function iterator. This class defines a maximum number
 * of iterations N<sub>max</sub> and a stopping radius R<sub>max</sub>. The
 * iterations f<sup>n</sup>(z) stop if n>N<sub>max</sub> or if
 * |f<sup>n</sup>(z)|>R<sub>max</sub>.
 *
 * @author agabrown Aug 2014
 *
 */
public final class ComplexFunctionIterator {

  /**
   * Maximum number of iterations N<sub>max</sub>.
   */
  private int maximumIterations;

  /**
   * Value of R<sub>max</sub>.
   */
  private double stoppingRadius;

  /**
   * Function to be iterated.
   */
  private UnaryOperator<Complex> function;

  /**
   * Private default constructor to enforce use of factory method for getting
   * instance.
   */
  private ComplexFunctionIterator() {

  }

  /**
   * Constructor.
   *
   * @param maxIter
   *          Maximum number of iterations.
   * @param r
   *          Stopping radius.
   * @param f
   *          Function to iterate.
   */
  public static ComplexFunctionIterator getInstance(final int maxIter, final double r, final UnaryOperator<Complex> f) {
    final ComplexFunctionIterator cfi = new ComplexFunctionIterator();
    cfi.setMaximumIterations(maxIter);
    cfi.setStoppingRadius(r);
    cfi.setFunction(f);
    return cfi;
  }

  /**
   * Set the maximum number of iterations.
   *
   * @param maxIter
   *          Maximum number of iterations.
   * @throws IllegalArgumentException
   *           If maxIter<1.
   */
  public void setMaximumIterations(final int maxIter) {
    if (maxIter < 1) {
      throw new IllegalArgumentException("At least one iteration is required.");
    }
    maximumIterations = maxIter;
  }

  /**
   * Set the stopping radius.
   *
   * @param r
   *          Value of stopping radius.
   * @throws IllegalArgumentException
   *           If the stopping radius is not positive and finite.
   */
  public void setStoppingRadius(final double r) {
    if (r < 0.0 || Double.isInfinite(r) || Double.isNaN(r)) {
      throw new IllegalArgumentException("Value of stopping radius should be postive and finite.");
    }
    stoppingRadius = r;
  }

  /**
   * Set the complex function to be iterated.
   *
   * @param f
   *          Function to be iterated (as Java8 Lambda expression).
   */
  public void setFunction(final UnaryOperator<Complex> f) {
    function = f;
  }

  /**
   * Iterate the function until one of the stopping criteria is reached and
   * return the list of iterates.
   *
   * @param zStart
   *          Starting value of z.
   *
   * @return List of iterates f<sup>n</sup>(z).
   */
  public List<Complex> iterate(final Complex zStart) {
    final ArrayList<Complex> zn = new ArrayList<>();
    Complex zNext = Complex.valueOf(zStart.getReal(), zStart.getImaginary());
    int iter = 0;
    while (zNext.abs() <= stoppingRadius && iter < maximumIterations) {
      zn.add(zNext);
      zNext = function.apply(zNext);
      iter++;
    }
    zn.trimToSize();
    return zn;
  }

  /**
   * Iterate the function for the complex conjugate of z until one of the
   * stopping criteria is reached and return the list of iterates.
   *
   * @param zStart
   *          Starting value of z.
   *
   * @return List of iterates f<sup>n</sup>(conjugate(z)).
   */
  public List<Complex> iterateConjugate(final Complex zStart) {
    final ArrayList<Complex> zn = new ArrayList<>();
    Complex zNext = Complex.valueOf(zStart.getReal(), zStart.getImaginary());
    int iter = 0;
    while (zNext.abs() <= stoppingRadius && iter < maximumIterations) {
      zn.add(zNext);
      zNext = function.apply(zNext.conjugate());
      iter++;
    }
    zn.trimToSize();
    return zn;
  }
}
