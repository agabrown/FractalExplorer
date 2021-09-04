package agabrown.fractalexplorer.generators;

import org.apache.commons.math3.complex.Complex;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Unit tests for
 * {@link agabrown.fractalexplorer.generators.ComplexFunctionIterator}.
 *
 * @author agabrown Aug 2014 - Sep 2021
 */
public class ComplexFunctionIteratorTest {

    ComplexFunctionIterator cfi;
    List<Complex> result;
    Complex zStart;

    @Before
    public void setUp() {
    }

    /**
     * Test method for
     * {@link agabrown.fractalexplorer.generators.ComplexFunctionIterator#iterate(org.apache.commons.math3.complex.Complex)}
     * . Check that the iterator behaves as expected for simple functions.
     */
    @Test
    public void testIterate() {
        int nmax = 10;
        final double bailoutValue = 2.0;
        cfi = ComplexFunctionIterator.getInstance(nmax, bailoutValue, z -> z);
        zStart = Complex.valueOf(2.1, 0.0);
        result = cfi.iterate(zStart);
        assertEquals(1, result.size());

        zStart = Complex.valueOf(2.0, 0.0);
        result = cfi.iterate(zStart);
        assertEquals(nmax + 1, result.size());
        for (final Complex z : result) {
            assertTrue(Complex.equals(z, zStart, 1.0e-8));
        }

        cfi.setFunction(z -> z.multiply(z).add(Complex.valueOf(0.1, 0.1)));
        zStart = Complex.ZERO;
        result = cfi.iterate(zStart);
        assertEquals(nmax + 1, result.size());
        nmax = 5000;
        cfi.setMaximumIterations(nmax);
        result = cfi.iterate(zStart);
        assertEquals(nmax + 1, result.size());
        assertTrue(result.get(result.size() - 2).abs() <= bailoutValue);

        cfi.setFunction(z -> z.multiply(z).add(Complex.valueOf(-0.78, 0.20)));
        result = cfi.iterate(zStart);
        assertTrue(result.size() < nmax + 1);
        assertTrue(result.get(result.size() - 2).abs() <= bailoutValue);
        assertTrue(result.get(result.size() - 1).abs() > bailoutValue);
    }
}
