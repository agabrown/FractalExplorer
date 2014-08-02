package agabrown.fractalexplorer.colours;

import java.util.List;

import org.apache.commons.math3.complex.Complex;

/**
 * Implements the escape time algorithm for Fractal colouring. The pixels in the
 * Fractal image are simply coloured according to the number of iterations of
 * f(z) that was achieved before stopping the calculation of the series
 * f<sup>n</sup>(z).
 *
 * @author agabrown Aug 2014.
 *
 */
public final class EscapeTime implements ColouringAlgorithm {

  /*
   * (non-Javadoc)
   *
   * @see
   * agabrown.fractalexplorer.colours.ColouringAlgorithm#getPixelValue(java.
   * util.List)
   */
  @Override
  public double getPixelValue(final List<Complex> fnz) {
    return fnz.size();
  }

}
