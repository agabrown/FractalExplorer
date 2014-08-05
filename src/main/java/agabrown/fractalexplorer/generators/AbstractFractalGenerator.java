package agabrown.fractalexplorer.generators;

import java.util.List;

import org.apache.commons.math3.complex.Complex;

import agabrown.fractalexplorer.dm.ComplexPlaneView;

/**
 * Base implementation of the {@link FractalGenerator}. Contains the code for
 * generating the entire fractal image based in the
 * {@link FractalGenerator#generatePixelValue(Complex)} method.
 *
 * @author agabrown Aug 2014.
 *
 */
public abstract class AbstractFractalGenerator implements FractalGenerator {

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
    Complex z;
    for (int k = 0; k < imWidth * imHeight; k++) {
      i = k % imWidth;
      j = k / imWidth;
      x = cpv.getValueAtRealPixel(i);
      y = cpv.getValueAtImaginaryPixel(j);
      z = Complex.valueOf(x, y);
      fractalImage[k] = generatePixelValue(z);
    }
    return fractalImage;
  }

  /*
   * (non-Javadoc)
   *
   * @see
   * agabrown.fractalexplorer.generators.FractalGenerator#generatePixelValue
   * (org.apache.commons.math3.complex.Complex)
   */
  @Override
  public abstract double generatePixelValue(final Complex z);

  /*
   * (non-Javadoc)
   *
   * @see agabrown.fractalexplorer.generators.FractalGenerator#getName()
   */
  @Override
  public abstract String getName();

  /*
   * (non-Javadoc)
   *
   * @see agabrown.fractalexplorer.generators.FractalGenerator#getInfoLines()
   */
  @Override
  public abstract List<String> getInfoLines();

}
