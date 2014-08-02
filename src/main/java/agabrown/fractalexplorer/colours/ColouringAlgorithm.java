package agabrown.fractalexplorer.colours;

import java.util.List;

import org.apache.commons.math3.complex.Complex;

/**
 * This interface should be implemented by classes that represent a Fractal
 * colouring algorithm. Such algorithms assign values to pixels in a Fractal
 * image based on the list of iterates f<sup>n</sup>(z) of the complex function
 * f(z) that is used to generate the Fractal. The values can subsequently be
 * scaled (linear, logarithmic, etc) to a standard range and colour coded
 * according to some colour LUT.
 *
 * <p>
 * So note that the classes implementing this interface do not actually assign
 * colours to the Fractal image pixels, but values.
 * </p>
 *
 * @author agabrown Aug 2014
 *
 */
public interface ColouringAlgorithm {

  /**
   * From the input list of iterates f<sup>n</sup>(z) derive a pixel value for
   * the Fractal image.
   *
   * @param fnz
   *          List of input iterates.
   * @return The value to assign to the pixel of the Fractal image.
   */
  public double getPixelValue(List<Complex> fnz);

}
