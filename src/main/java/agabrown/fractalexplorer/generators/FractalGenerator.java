package agabrown.fractalexplorer.generators;

import java.util.List;

import agabrown.fractalexplorer.dm.ComplexPlaneView;

/**
 * All classes that represent a Fractal generator (capable of iterating a
 * complex function f(z) and generating a Fractal image from the resulting
 * sequence f<sup>n</sup>(z)) should implement this interface.
 *
 * @author agabrown Aug 2014
 *
 */
public interface FractalGenerator {

  /**
   * Generate the Fractal image (pixel values only, no colouring or scaling)
   * corresponding to the input view of the Complex Plane.
   *
   * @param cpv
   *          The input complex plane view.
   * @return The fractal image (which is to be scaled and colour coded before
   *         displaying).
   */
  public double[] generateImage(ComplexPlaneView cpv);

  /**
   * Obtain a string containing the name of the fractal generator.
   *
   * @return The name of the fractal generator.
   */
  public String getName();

  /**
   * Provide information on the fractal generator parameters as a list of
   * strings.
   *
   * @return List of strings describing the fractal generator parameters (can be
   *         empty).
   */
  public List<String> getInfoLines();
}
