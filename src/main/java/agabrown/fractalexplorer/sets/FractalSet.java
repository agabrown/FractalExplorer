package agabrown.fractalexplorer.sets;

import java.util.List;

/**
 * Must be implemented by all classes representing Fractal sets.
 * 
 * @author agabrown 26 Jul 2012
 * 
 */
public interface FractalSet {

  /**
   * Check if the complex input number is in the Fractal set.
   * 
   * @param real
   *          Real part of complex number to check
   * @param imaginary
   *          Imaginary part of complex number to check
   * @param maxIter
   *          Maximum number of iterations to decide on whether or not the
   *          number is in the set. Numbers for which maxIter is exceeded are
   *          considered to be part of the set.
   * 
   * @return True if the number is in the Fractal set.
   */
  public boolean isPointInSet(final double real, final double imaginary, final int maxIter);

  /**
   * Check if the complex input number is in the Fractal set. In this case
   * return the number of iterations used before a decision was taken on whether
   * or not the number is in the set.
   * 
   * @param real
   *          Real part of complex number to check
   * @param imaginary
   *          Imaginary part of complex number to check
   * @param maxIter
   *          Maximum number of iterations to decide on whether or not the
   *          number is in the set. Numbers for which maxIter is exceeded are
   *          considered to be part of the set.
   * 
   * @return Number of iterations reached before the Fractal set iterations were
   *         terminated.
   */
  public int numberOfIterationsForPoint(final double real, final double imaginary, final int maxIter);

  /**
   * Obtain a string containing the name of the fractal set.
   * 
   * @return The name of the fractal set.
   */
  public String getName();

  /**
   * Provide information on the fractal set parameters as a list of strings.
   * 
   * @return List of strings describing the fractal set parameters (can be
   *         empty).
   */
  public List<String> getInfoLines();

}
