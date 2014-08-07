package agabrown.fractalexplorer.generators;

import java.util.function.Function;

import org.apache.commons.math3.complex.Complex;

import agabrown.fractalexplorer.colours.EscapeTime;

/**
 * Provides static methods for obtaining pre-configured FractalGenerators.
 *
 * @author agabrown Aug 2014.
 *
 */
public final class FractalGeneratorFactory {

  /**
   * Default maximum number of iterations.
   */
  private final static int DEFAULT_ITERATIONS = 256;

  /**
   * Default stopping radius.
   */
  private static final double DEFAULT_STOPPING_RADIUS = 2.0;

  /**
   * Creates the classic Mandelbrot fractal generator using the escape time
   * algorithm for colouring.
   *
   * @return A pre-configured version of MandelbrotGenerator.
   */
  public static MandelbrotGenerator getMandelbrotEscapeTime() {
    final Function<Complex, Complex> f = z -> z.multiply(z);
    final MandelbrotGenerator result = new MandelbrotGenerator.Builder().colouringAlgorithm(new EscapeTime())
        .maximumIterations(DEFAULT_ITERATIONS).stoppingRadius(DEFAULT_STOPPING_RADIUS).generatingFunction(f).build();
    return result;
  }

  /**
   * Creates the modified Mandelbrot fractal generator using the escape time
   * algorithm for colouring.
   *
   * @return A pre-configured version of ModifiedMandelbrotGenerator.
   */
  public static ModifiedMandelbrotGenerator getModifiedMandelbrotEscapeTime() {
    final Function<Complex, Complex> f = z -> z.multiply(z);
    final ModifiedMandelbrotGenerator result = new ModifiedMandelbrotGenerator.Builder()
        .colouringAlgorithm(new EscapeTime()).maximumIterations(DEFAULT_ITERATIONS)
    .stoppingRadius(DEFAULT_STOPPING_RADIUS).generatingFunction(f).build();
    return result;
  }

  /**
   * Creates the modified Mandelbrot fractal generator using the escape time
   * algorithm for colouring.
   *
   * @return A pre-configured version of ModifiedMandelbrotGenerator.
   */
  public static ModifiedMandelbrotGenerator getRudyCubicMandelbrotEscapeTime() {
    final Complex d = Complex.valueOf(-0.7198, 0.9111);
    final Function<Complex, Complex> f = z -> z.multiply(z).multiply(z).add(z.multiply(d));
    final ModifiedMandelbrotGenerator result = new ModifiedMandelbrotGenerator.Builder()
        .colouringAlgorithm(new EscapeTime()).maximumIterations(DEFAULT_ITERATIONS).stoppingRadius(1.0e10)
    .generatingFunction(f).build();
    return result;
  }

  /**
   * Creates the classic Julia fractal generator using the escape time algorithm
   * for colouring. The function f(z)=z*z+mu is iterated.
   *
   * @param mu
   *          Value of fixed constant.
   * @return A pre-configured version of JuliaFatouGenerator.
   */
  public static JuliaFatouGenerator getJuliaClassicEscapeTime(final Complex mu) {
    final Function<Complex, Complex> f = z -> z.multiply(z);
    final JuliaFatouGenerator result = new JuliaFatouGenerator.Builder().colouringAlgorithm(new EscapeTime())
        .maximumIterations(DEFAULT_ITERATIONS).stoppingRadius(DEFAULT_STOPPING_RADIUS)
        .generatingFunction(f.andThen(z -> z.add(mu))).build();
    return result;
  }
}
