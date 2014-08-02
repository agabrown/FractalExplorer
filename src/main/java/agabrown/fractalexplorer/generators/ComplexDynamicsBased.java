package agabrown.fractalexplorer.generators;

import agabrown.fractalexplorer.colours.ColouringAlgorithm;
import agabrown.fractalexplorer.dm.ComplexPlaneView;

/**
 * Base class for fractal generating classes that are based on complex dynamics
 * (such as the Mandelbrot and Julia/Fatou fractals). These classes make use of
 * straightforward complex function iterations to generate fractals.
 *
 * @author agabrown Aug 2014.
 *
 */
public abstract class ComplexDynamicsBased implements FractalGenerator {

  /**
   * The algorithm used for assigning values to the pixels of the Fractal image.
   */
  protected ColouringAlgorithm colouringAlgorithm;

  /**
   * The object that does the iteration of the fractal generating function.
   */
  protected ComplexFunctionIterator theIterator;

  /**
   * If true iterate over the conjugate of z rather than z (i.e. z ->
   * f(conjugate(z))).
   */
  protected boolean iterateConjugate = false;

  /*
   * (non-Javadoc)
   *
   * @see
   * agabrown.fractalexplorer.generators.FractalGenerator#generateImage(agabrown
   * .fractalexplorer.dm.ComplexPlaneView)
   */
  @Override
  public abstract double[] generateImage(final ComplexPlaneView cpv);

  /**
   * Set whether or not to iterate the generating function over conjugate(z)
   * instead of z.
   *
   * @param u
   *          If true iterate over conjugate(z).
   */
  public void useConjugate(final boolean u) {
    iterateConjugate = u;
  }

  /**
   * Set the maximum number of iterations to use when generating the fractal.
   *
   * @param m
   *          Maximum number of iterations.
   */
  public void setMaximumIterations(final int m) {
    theIterator.setMaximumIterations(m);
  }

  /**
   * Set the stopping radius.
   *
   * @param r
   *          Value of stopping radius.
   */
  public void setStoppingRadius(final double r) {
    theIterator.setStoppingRadius(r);
  }

  /**
   * Set the colouring algorithm for the fractal image.
   * 
   * @param ca
   *          The colouring algorithm.
   */
  public void setColouringAlgorithm(final ColouringAlgorithm ca) {
    colouringAlgorithm = ca;
  }
}
