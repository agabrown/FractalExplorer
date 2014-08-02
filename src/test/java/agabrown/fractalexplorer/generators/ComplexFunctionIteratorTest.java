package agabrown.fractalexplorer.generators;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.apache.commons.math3.complex.Complex;
import org.junit.Before;
import org.junit.Test;

/**
 * Unit tests for
 * {@link agabrown.fractalexplorer.generators.ComplexFunctionIterator}.
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
  }

  /**
   * Test method for
   * {@link agabrown.fractalexplorer.generators.ComplexFunctionIterator#iterate(org.apache.commons.math3.complex.Complex)}
   * . Check that the iterator behaves as expected for simple functions.
   */
  @Test
  public void testIterate() {
    cfi = ComplexFunctionIterator.getInstance(10, 2.0, z -> z);
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

    cfi.setFunction(z -> z.multiply(z).add(Complex.valueOf(-0.78, 0.20)));
    result = cfi.iterate(zStart);
    assertTrue(result.size() < 5000);
  }
}
