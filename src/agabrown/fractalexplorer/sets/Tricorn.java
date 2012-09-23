/**
 * 
 */
package agabrown.fractalexplorer.sets;

import java.util.List;

/**
 * Represents the Tricorn or Mandelbar set in which the set is generated by
 * iterating the conjugate of z in the Mandelbrot equation.
 * 
 * @author agabrown
 * 
 */
public final class Tricorn implements FractalSet {

  /*
   * (non-Javadoc)
   * 
   * @see agabrown.fractalexplorer.sets.FractalSet#isPointInSet(double, double,
   * int)
   */
  @Override
  public boolean isPointInSet(final double real, final double imaginary, final int maxIter) {
    // TODO Auto-generated method stub
    return false;
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * agabrown.fractalexplorer.sets.FractalSet#numberOfIterationsForPoint(double,
   * double, int)
   */
  @Override
  public int numberOfIterationsForPoint(final double real, final double imaginary, final int maxIter) {
    // TODO Auto-generated method stub
    return 0;
  }

  /*
   * (non-Javadoc)
   * 
   * @see agabrown.fractalexplorer.sets.FractalSet#getName()
   */
  @Override
  public String getName() {
    // TODO Auto-generated method stub
    return null;
  }

  /*
   * (non-Javadoc)
   * 
   * @see agabrown.fractalexplorer.sets.FractalSet#getInfoLines()
   */
  @Override
  public List<String> getInfoLines() {
    // TODO Auto-generated method stub
    return null;
  }

}
