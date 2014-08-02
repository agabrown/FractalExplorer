package agabrown.fractalexplorer.generators;

import java.util.List;
import java.util.function.UnaryOperator;

import org.apache.commons.math3.complex.Complex;

import agabrown.fractalexplorer.colours.ColouringAlgorithm;
import agabrown.fractalexplorer.dm.ComplexPlaneView;

/**
 * This class implements a general version of the Julia/Fatou fractal image
 * generators. It iterates a complex function f(z) and generates the image by
 * varying the starting value z0 of the iteration over the complex plane.
 *
 * @author agabrown Aug 2014
 *
 */
public final class JuliaFatouGenerator extends ComplexDynamicsBased {

  /**
   * Inner class used for implementing the builder pattern.
   *
   * @author agabrown Aug 2014
   *
   */
  public static class Builder {
    private ColouringAlgorithm colouringAlgorithm;
    private UnaryOperator<Complex> generatingFunction;
    private double stoppingRadius;
    private int maxIterations;

    /**
     * Set the stopping radius for the Fractal generator.
     *
     * @param r
     *          Value of the stopping radius.
     * @return The builder.
     */
    public Builder stoppingRadius(final double r) {
      if (r < 0.0 || Double.isInfinite(r) || Double.isNaN(r)) {
        throw new IllegalArgumentException("Value of stopping radius should be postive and finite.");
      }
      stoppingRadius = r;
      return this;
    }

    /**
     * Set the maximum number of generating function iterations for the Fractal
     * generator.
     *
     * @param m
     *          Maximum number of iterations.
     * @return The builder.
     */
    public Builder maximumIterations(final int m) {
      if (m < 1) {
        throw new IllegalArgumentException("At least one iteration is required.");
      }
      maxIterations = m;
      return this;
    }

    /**
     * Set the colouring algorithm used for creating the fractal image.
     *
     * @param c
     *          The colouring algorithm.
     * @return The builder.
     */
    public Builder colouringAlgorithm(final ColouringAlgorithm c) {
      colouringAlgorithm = c;
      return this;
    }

    /**
     * Set the generating function for the Fractal.
     *
     * @param g
     *          The generating function.
     * @return The builder.
     */
    public Builder generatingFunction(final UnaryOperator<Complex> g) {
      generatingFunction = g;
      return this;
    }

    /**
     * Call the constructor for the JuliaFatouGenerator class.
     *
     * @return A new instance of JuliaFatouGenerator.
     */
    public JuliaFatouGenerator build() {
      return new JuliaFatouGenerator(this);
    }
  }

  /**
   * Private constructor which takes the builder inner class to create a
   * properly configured instance of JuliaFatouGenerator.
   *
   * @param builder
   *          The Builder object that contains the information to construct a
   *          JuliaFatouGenerator.
   */
  private JuliaFatouGenerator(final Builder builder) {
    this.colouringAlgorithm = builder.colouringAlgorithm;
    this.theIterator = ComplexFunctionIterator.getInstance(builder.maxIterations, builder.stoppingRadius,
        builder.generatingFunction);
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * agabrown.fractalexplorer.generators.FractalGenerator#generateImage(agabrown
   * .fractalexplorer.dm.ComplexPlaneView)
   */
  @Override
  public double[] generateImage(final ComplexPlaneView cpv) {
    int i, j;
    double x, y;
    final int imWidth = cpv.getSizeRealPixels();
    final int imHeight = cpv.getSizeImaginaryPixels();
    final double[] fractalImage = new double[imWidth * imHeight];
    Complex zStart;
    List<Complex> iterates;
    for (int k = 0; k < imWidth * imHeight; k++) {
      i = k % imWidth;
      j = k / imWidth;
      x = cpv.getValueAtRealPixel(i);
      y = cpv.getValueAtImaginaryPixel(j);
      zStart = Complex.valueOf(x, y);
      if (iterateConjugate) {
        iterates = theIterator.iterateConjugate(zStart);
      } else {
        iterates = theIterator.iterate(zStart);
      }
      fractalImage[k] = colouringAlgorithm.getPixelValue(iterates);
    }
    return fractalImage;
  }

}
