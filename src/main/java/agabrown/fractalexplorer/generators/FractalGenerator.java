package agabrown.fractalexplorer.generators;

import agabrown.fractalexplorer.dm.ComplexPlaneView;
import org.apache.commons.math3.complex.Complex;

import java.util.List;

/**
 * All classes that represent a Fractal generator (capable of iterating a
 * complex function f(z) and generating a Fractal image from the resulting
 * sequence f<sup>n</sup>(z)) should implement this interface.
 *
 * @author agabrown Aug 2014 - Sep 2021
 */
public interface FractalGenerator {

    /**
     * Generate the Fractal image (pixel values only, no colouring or scaling)
     * corresponding to the input view of the Complex Plane.
     *
     * @param cpv The input complex plane view.
     * @return The fractal image (which is to be scaled and colour coded before
     * displaying).
     */
    double[] generateImage(ComplexPlaneView cpv);

    /**
     * Generate the value of the Fractal Image pixel for the specified point in
     * the complex plane.
     *
     * @param z Point in complex plane at which to generate pixel value.
     * @return The pixel value (which is to be scaled and colour coded before
     * displaying).
     */
    double generatePixelValue(Complex z);

    /**
     * Obtain a string containing the name of the fractal generator.
     *
     * @return The name of the fractal generator.
     */
    String getName();

    /**
     * Provide information on the fractal generator parameters as a list of
     * strings.
     *
     * @return List of strings describing the fractal generator parameters (can be
     * empty).
     */
    List<String> getInfoLines();
}
