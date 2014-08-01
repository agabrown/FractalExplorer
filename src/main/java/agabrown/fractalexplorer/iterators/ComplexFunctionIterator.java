package agabrown.fractalexplorer.iterators;

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
public class ComplexFunctionIterator {

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
   * Set the maximum number of iterations.
   *
   * @param maxIter
   *          Maximum number of iterations.
   */
  public void setMaximumIterations(final int maxIter) {
    maximumIterations = maxIter;
  }

  /**
   * Set the stopping radius.
   *
   * @param r
   *          Value of stopping radius.
   */
  public void setStoppingRadius(final double r) {
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
}
