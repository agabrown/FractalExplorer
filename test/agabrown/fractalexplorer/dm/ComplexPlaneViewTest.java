/**
 * 
 */
package agabrown.fractalexplorer.dm;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;

import agabrown.fractalexplorer.util.FEConstants;

/**
 * @author agabrown
 * 
 */
public class ComplexPlaneViewTest {

  ComplexPlaneView viewA, viewB;

  private final static double TOLERANCE = 1.0e-8;

  private final int sizeRe = 70;
  private final int sizeIm = 40;

  /**
   * @throws java.lang.Exception
   */
  @Before
  public void setUp() throws Exception {
    viewA = new ComplexPlaneView(sizeRe, sizeIm);
  }

  /**
   * Test method for
   * {@link agabrown.fractalexplorer.dm.ComplexPlaneView#ComplexPlaneView(int, int)}
   * . Check that default constructor behaves as expected.
   */
  @Test
  public void testComplexPlaneView() {
    assertEquals(FEConstants.DEFAULT_CENTRE_REAL, viewA.getCentreReal(), TOLERANCE);
    assertEquals(FEConstants.DEFAULT_CENTRE_IMAGINARY, viewA.getCentreImaginary(), TOLERANCE);
    assertEquals(FEConstants.DEFAULT_SIZE_REAL, viewA.getSizeReal(), TOLERANCE);
    assertEquals(FEConstants.DEFAULT_SIZE_IMAGINARY, viewA.getSizeImaginary(), TOLERANCE);
    assertEquals(sizeRe, viewA.getSizeRealPixels());
    assertEquals(sizeIm, viewA.getSizeImaginaryPixels());
    assertEquals(1.0, viewA.getZoomFactor(), TOLERANCE);
  }

  /**
   * Test method for
   * {@link agabrown.fractalexplorer.dm.ComplexPlaneView#setZoomFactor(double)}.
   * Check that changing the zoom factor works as expected.
   */
  @Test
  public void testSetZoomFactor() {
    viewA.setZoomFactor(9.5);
    assertEquals(9.5, viewA.getZoomFactor(), TOLERANCE);
    assertEquals(FEConstants.DEFAULT_SIZE_REAL / 9.5, viewA.getSizeReal(), TOLERANCE);
    assertEquals(FEConstants.DEFAULT_SIZE_IMAGINARY / 9.5, viewA.getSizeImaginary(), TOLERANCE);
    try {
      viewA.setZoomFactor(-1.0);
      fail("IllegalArgumentException expected.");
    } catch (final Exception e) {
      assertTrue(e instanceof IllegalArgumentException);
    }
  }

  /**
   * Test method for
   * {@link agabrown.fractalexplorer.dm.ComplexPlaneView#doubleZoomFactor()}.
   * Check that doubling the zoom factor works as expected.
   */
  @Test
  public void testDoubleZoomFactor() {
    viewA.doubleZoomFactor();
    assertEquals(2.0, viewA.getZoomFactor(), TOLERANCE);
    assertEquals(FEConstants.DEFAULT_SIZE_REAL / 2.0, viewA.getSizeReal(), TOLERANCE);
    assertEquals(FEConstants.DEFAULT_SIZE_IMAGINARY / 2.0, viewA.getSizeImaginary(), TOLERANCE);
    viewA.doubleZoomFactor();
    viewA.doubleZoomFactor();
    assertEquals(8.0, viewA.getZoomFactor(), TOLERANCE);
    assertEquals(FEConstants.DEFAULT_SIZE_REAL / 8.0, viewA.getSizeReal(), TOLERANCE);
    assertEquals(FEConstants.DEFAULT_SIZE_IMAGINARY / 8.0, viewA.getSizeImaginary(), TOLERANCE);
  }

  /**
   * Test method for
   * {@link agabrown.fractalexplorer.dm.ComplexPlaneView#halveZoomFactor()}.
   * Check that halving the zoom factor works as expected.
   */
  @Test
  public void testHalveZoomFactor() {
    viewA.halveZoomFactor();
    assertEquals(0.5, viewA.getZoomFactor(), TOLERANCE);
    assertEquals(FEConstants.DEFAULT_SIZE_REAL / 0.5, viewA.getSizeReal(), TOLERANCE);
    assertEquals(FEConstants.DEFAULT_SIZE_IMAGINARY / 0.5, viewA.getSizeImaginary(), TOLERANCE);
    viewA.halveZoomFactor();
    viewA.halveZoomFactor();
    assertEquals(0.125, viewA.getZoomFactor(), TOLERANCE);
    assertEquals(FEConstants.DEFAULT_SIZE_REAL / 0.125, viewA.getSizeReal(), TOLERANCE);
    assertEquals(FEConstants.DEFAULT_SIZE_IMAGINARY / 0.125, viewA.getSizeImaginary(), TOLERANCE);
  }

  /**
   * Test method for
   * {@link agabrown.fractalexplorer.dm.ComplexPlaneView#setCentre(double, double)}
   * . Verify that setting the centre works as expected.
   */
  @Test
  public void testSetCentre() {
    viewA.setCentre(3.14, -2.71);
    assertEquals(3.14, viewA.getCentreReal(), TOLERANCE);
    assertEquals(-2.71, viewA.getCentreImaginary(), TOLERANCE);
    double expected = 3.14 - FEConstants.HALF * FEConstants.DEFAULT_SIZE_REAL;
    assertEquals(expected, viewA.getValueAtRealPixel(0), TOLERANCE);
    expected = -2.71 - FEConstants.HALF * FEConstants.DEFAULT_SIZE_IMAGINARY;
    assertEquals(expected, viewA.getValueAtImaginaryPixel(0), TOLERANCE);
  }

  /**
   * Test method for
   * {@link agabrown.fractalexplorer.dm.ComplexPlaneView#reset()}. Check that
   * the reset of the class works as expected.
   */
  @Test
  public void testReset() {
    viewA.doubleZoomFactor();
    viewA.setCentre(3.14, -2.71);
    viewA.reset();
    assertEquals(FEConstants.DEFAULT_CENTRE_REAL, viewA.getCentreReal(), TOLERANCE);
    assertEquals(FEConstants.DEFAULT_CENTRE_IMAGINARY, viewA.getCentreImaginary(), TOLERANCE);
    assertEquals(FEConstants.DEFAULT_SIZE_REAL, viewA.getSizeReal(), TOLERANCE);
    assertEquals(FEConstants.DEFAULT_SIZE_IMAGINARY, viewA.getSizeImaginary(), TOLERANCE);
    assertEquals(sizeRe, viewA.getSizeRealPixels());
    assertEquals(sizeIm, viewA.getSizeImaginaryPixels());
    assertEquals(1.0, viewA.getZoomFactor(), TOLERANCE);
  }

  /**
   * Test method for
   * {@link agabrown.fractalexplorer.dm.ComplexPlaneView#getValueAtRealPixel(int)}
   * . Check that the correct value for a given real-axis pixel coordinate is
   * obtained.
   */
  @Test
  public void testGetValueAtRealPixel() {
    double expected = FEConstants.DEFAULT_CENTRE_REAL - FEConstants.HALF * FEConstants.DEFAULT_SIZE_REAL;
    assertEquals(expected, viewA.getValueAtRealPixel(0), TOLERANCE);
    expected = FEConstants.DEFAULT_CENTRE_REAL + FEConstants.HALF * FEConstants.DEFAULT_SIZE_REAL;
    assertEquals(expected, viewA.getValueAtRealPixel(sizeRe - 1), TOLERANCE);
    expected = FEConstants.DEFAULT_CENTRE_REAL - FEConstants.HALF * FEConstants.DEFAULT_SIZE_REAL + 10.0 / (sizeRe - 1)
        * FEConstants.DEFAULT_SIZE_REAL;
    assertEquals(expected, viewA.getValueAtRealPixel(10), TOLERANCE);
  }

  /**
   * Test method for
   * {@link agabrown.fractalexplorer.dm.ComplexPlaneView#getValueAtImaginaryPixel(int)}
   * . Check that the correct value for a given imaginary-axis pixel coordinate
   * is obtained.
   */
  @Test
  public void testGetValueAtImaginaryPixel() {
    double expected = FEConstants.DEFAULT_CENTRE_IMAGINARY - FEConstants.HALF * FEConstants.DEFAULT_SIZE_IMAGINARY;
    assertEquals(expected, viewA.getValueAtImaginaryPixel(0), TOLERANCE);
    expected = FEConstants.DEFAULT_CENTRE_IMAGINARY + FEConstants.HALF * FEConstants.DEFAULT_SIZE_IMAGINARY;
    assertEquals(expected, viewA.getValueAtImaginaryPixel(sizeIm - 1), TOLERANCE);
    expected = FEConstants.DEFAULT_CENTRE_IMAGINARY - FEConstants.HALF * FEConstants.DEFAULT_SIZE_IMAGINARY + 37.0
        / (sizeIm - 1) * FEConstants.DEFAULT_SIZE_IMAGINARY;
    assertEquals(expected, viewA.getValueAtImaginaryPixel(37), TOLERANCE);
  }

  /**
   * Test method for
   * {@link agabrown.fractalexplorer.dm.ComplexPlaneView#clone()}. Verify
   * correct behaviour of the clone() method.
   */
  @Test
  public void testClone() {
    viewA.doubleZoomFactor();
    viewA.setCentre(3.14, -2.71);
    viewB = (ComplexPlaneView) viewA.clone();
    assertFalse(viewB == viewA);
    assertEquals(viewA.getSizeRealPixels(), viewB.getSizeRealPixels());
    assertEquals(viewA.getSizeImaginaryPixels(), viewB.getSizeImaginaryPixels());
    assertEquals(viewA.getZoomFactor(), viewB.getZoomFactor(), TOLERANCE);
    assertEquals(viewA.getCentreReal(), viewB.getCentreReal(), TOLERANCE);
    assertEquals(viewA.getCentreImaginary(), viewB.getCentreImaginary(), TOLERANCE);
    assertEquals(viewA.getSizeReal(), viewB.getSizeReal(), TOLERANCE);
    assertEquals(viewA.getSizeImaginary(), viewB.getSizeImaginary(), TOLERANCE);
  }

  /**
   * Test method for
   * {@link agabrown.fractalexplorer.dm.ComplexPlaneView#equals(java.lang.Object)}
   * . Check that equals() behaves as expected.
   */
  @Test
  public void testEqualsObject() {
    viewB = new ComplexPlaneView(7, 4);
    assertFalse(viewA.equals(null));
    assertFalse(viewB.equals(viewA));
    viewB = new ComplexPlaneView(sizeRe, sizeIm);
    assertTrue(viewB.equals(viewA));
    viewA.halveZoomFactor();
    viewA.setCentre(0.1, 0.3);
    viewB = (ComplexPlaneView) viewA.clone();
    final ComplexPlaneView viewC = (ComplexPlaneView) viewB.clone();
    assertTrue(viewB.equals(viewA));
    assertTrue(viewC.equals(viewA));
  }

}
