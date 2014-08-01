package agabrown.fractalexplorer.iterators;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.apache.commons.math3.complex.Complex;
import org.junit.Before;
import org.junit.Test;

/**
 * Unit tests for
 * {@link agabrown.fractalexplorer.iterators.ComplexFunctionIterator}.
 *
 * @author agabrown
 *
 */
public class ComplexFunctionIteratorTest {

  ComplexFunctionIterator cfi;
  List<Complex> expected;
  List<Complex> result;
  Complex zStart;

  /**
   * @throws java.lang.Exception
   */
  @Before
  public void setUp() throws Exception {
    cfi = new ComplexFunctionIterator();
  }

  /**
   * Test method for
   * {@link agabrown.fractalexplorer.iterators.ComplexFunctionIterator#iterate(org.apache.commons.math3.complex.Complex)}
   * .
   */
  @Test
  public void testIterate() {
    cfi.setMaximumIterations(10);
    cfi.setStoppingRadius(2.0);
    cfi.setFunction(z -> z);
    zStart = Complex.valueOf(2.1, 0.0);
    result = cfi.iterate(zStart);
    assertEquals(0, result.size());

    zStart = Complex.valueOf(2.0, 0.0);
    result = cfi.iterate(zStart);
    assertEquals(10, result.size());
    for (final Complex z : result) {
      assertTrue(Complex.equals(z, zStart, 1.0e-8));
    }

    cfi.setFunction(z -> z.multiply(z).add(Complex.valueOf(0.1, 0.1)));
    zStart = Complex.ZERO;
    result = cfi.iterate(zStart);
    assertEquals(10, result.size());
    cfi.setMaximumIterations(5000);
    result = cfi.iterate(zStart);
    assertEquals(5000, result.size());
  }
}
