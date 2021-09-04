package agabrown.fractalexplorer.dm;

import agabrown.fractalexplorer.util.FEConstants;

/**
 * Data type that implements the view on the complex plane. That is, it
 * maintains the state of the centre point, the intervals along the real and
 * imaginary axes, the pixel spacing, and the `zoom' factor. The latter controls
 * by how much one zooms in on a certain fractal set.
 *
 * @author agabrown Jul 2012 - Sep 2021
 */
public final class ComplexPlaneView {

    /**
     * Size of interval along real axis in pixels.
     */
    private final int sizeRealPixels;
    /**
     * Size of interval along imaginary axis in pixels.
     */
    private final int sizeImaginaryPixels;
    /**
     * If true recalculate the length of the imaginary axis of the complex plane
     * view in order to get the correct aspect ratio on the image.
     */
    private final boolean resizeImaginary;
    /**
     * Factor by which complex plane (i.e. Fractal set image) is zoomed.
     */
    private double zoomFactor;
    /**
     * Centre of complex plane view along real axis.
     */
    private double centreReal;
    /**
     * Centre of complex plane view along imaginary axis.
     */
    private double centreImaginary;
    /**
     * Size of interval along real axis.
     */
    private double sizeReal;
    /**
     * Size of interval along imaginary axis
     */
    private double sizeImaginary;
    /**
     * Pixel size along real axis.
     */
    private double deltaRe;

    /**
     * Pixel size along imaginary axis.
     */
    private double deltaIm;

    /**
     * Minimum values along real axis.
     */
    private double reMin;

    /**
     * Minimum value along imaginary axis.
     */
    private double imMin;

    /**
     * Constructor. Sets the image size in pixels and initializes to the default
     * view of the complex plane.
     *
     * @param sizeRePix Size of real axis in pixels.
     * @param sizeImPix Size of imaginary axis in pixels.
     */
    public ComplexPlaneView(final int sizeRePix, final int sizeImPix) {
        sizeRealPixels = sizeRePix;
        sizeImaginaryPixels = sizeImPix;
        resizeImaginary = FEConstants.PREFERRED_ASPECT_RATIO > (double) sizeImPix / sizeRePix;
        initialize();
    }

    /**
     * Initialize the complex plane view to the default state.
     */
    private void initialize() {
        centreReal = FEConstants.DEFAULT_CENTRE_REAL;
        centreImaginary = FEConstants.DEFAULT_CENTRE_IMAGINARY;
        if (resizeImaginary) {
            sizeReal = FEConstants.DEFAULT_SIZE_REAL;
            sizeImaginary = sizeReal * sizeImaginaryPixels / sizeRealPixels;
        } else {
            sizeImaginary = FEConstants.DEFAULT_SIZE_IMAGINARY;
            sizeReal = sizeImaginary * sizeRealPixels / sizeImaginaryPixels;
        }
        zoomFactor = 1.0;
        reMin = centreReal - FEConstants.HALF * sizeReal;
        imMin = centreImaginary - FEConstants.HALF * sizeImaginary;
        deltaRe = sizeReal / (sizeRealPixels - 1);
        deltaIm = sizeImaginary / (sizeImaginaryPixels - 1);
    }

    /**
     * Reconfigure the complex plane view following a change in zoom factor or a
     * re-centring.
     */
    private void reConfigure() {
        reMin = centreReal - FEConstants.HALF * sizeReal;
        imMin = centreImaginary - FEConstants.HALF * sizeImaginary;
        deltaRe = sizeReal / (sizeRealPixels - 1);
        deltaIm = sizeImaginary / (sizeImaginaryPixels - 1);
    }

    /**
     * Double the zoom factor.
     */
    public void doubleZoomFactor() {
        setZoomFactor(zoomFactor * 2);
    }

    /**
     * Halve the zoom factor.
     */
    public void halveZoomFactor() {
        setZoomFactor(zoomFactor * FEConstants.HALF);
    }

    /**
     * Change the centre of the complex plane view.
     *
     * @param centreRe New centre along real axis.
     * @param centreIm New centre along imaginary axis.
     */
    public void setCentre(final double centreRe, final double centreIm) {
        centreReal = centreRe;
        centreImaginary = centreIm;
        reConfigure();
    }

    /**
     * Obtain zoom factor.
     *
     * @return Value of zoom factor.
     */
    public double getZoomFactor() {
        return zoomFactor;
    }

    /**
     * Change the zoom factor.
     *
     * @param zoom New value of the zoom factor.
     */
    public void setZoomFactor(final double zoom) {
        if (zoom <= 0.0) {
            throw new IllegalArgumentException("Zoom factor should be larger than 0.");
        }
        final double change = zoomFactor / zoom;
        sizeReal = sizeReal * change;
        sizeImaginary = sizeImaginary * change;
        zoomFactor = zoom;
        reConfigure();
    }

    /**
     * Obtain real part of centre point of complex plane view.
     *
     * @return Value of Re(centre).
     */
    public double getCentreReal() {
        return centreReal;
    }

    /**
     * Obtain imaginary part of centre point of complex plane view.
     *
     * @return Value of Im(centre).
     */
    public double getCentreImaginary() {
        return centreImaginary;
    }

    /**
     * Reset the complex plane view to the default state.
     */
    public void reset() {
        initialize();
    }

    /**
     * Obtain the number of pixels along the real axis for this complex plane
     * view.
     *
     * @return Number of pixels along real axis.
     */
    public int getSizeRealPixels() {
        return sizeRealPixels;
    }

    /**
     * Obtain the number of pixels along the imaginary axis for this complex plane
     * view.
     *
     * @return Number of pixels along imaginary axis.
     */
    public int getSizeImaginaryPixels() {
        return sizeImaginaryPixels;
    }

    /**
     * Obtain the length of the interval along the real axis.
     *
     * @return Value of interval length.
     */
    public double getSizeReal() {
        return sizeReal;
    }

    /**
     * Obtain the length of the interval along the imaginary axis.
     *
     * @return Value of interval length.
     */
    public double getSizeImaginary() {
        return sizeImaginary;
    }

    /**
     * Obtain the real coordinate at pixel i along the real axis.
     *
     * @param i Pixel position along real axis.
     * @return Value of the coordinate along the real axis.
     */
    public double getValueAtRealPixel(final int i) {
        return reMin + i * deltaRe;
    }

    /**
     * Obtain the real coordinate at the continuous pixel coordinate x along the
     * real axis.
     *
     * @param x Pixel coordinate along real axis.
     * @return Value of the coordinate along the real axis.
     */
    public double getValueAtRealPixel(final double x) {
        return reMin + x * deltaRe;
    }

    /**
     * Obtain the imaginary coordinate at pixel j along the imaginary axis. Takes
     * into account that the screen pixel indices run from top to bottom.
     *
     * @param j Pixel position along imaginary axis.
     * @return Value of the coordinate along the imaginary axis.
     */
    public double getValueAtImaginaryPixel(final int j) {
        return imMin + (sizeImaginaryPixels - j - 1) * deltaIm;
    }

    /**
     * Obtain the real coordinate at the continuous pixel coordinate y along the
     * imaginary axis. Takes into account that the screen pixel coordinates run
     * from top to bottom.
     *
     * @param y Pixel coordinate along imaginary axis.
     * @return Value of the coordinate along the imaginary axis.
     */
    public double getValueAtImaginaryPixel(final double y) {
        return imMin + (sizeImaginaryPixels - y - 1) * deltaIm;
    }

    @Override
    public Object clone() {
      final ComplexPlaneView newCPV = new ComplexPlaneView(sizeRealPixels, sizeImaginaryPixels);
        newCPV.setCentre(centreReal, centreImaginary);
        newCPV.setZoomFactor(zoomFactor);
        return newCPV;
    }

    @Override
    public boolean equals(final Object otherCPV) {
        if (otherCPV == null) {
            return false;
        }
        final ComplexPlaneView other = (ComplexPlaneView) otherCPV;
        return this.sizeRealPixels == other.sizeRealPixels && this.sizeImaginaryPixels == other.sizeImaginaryPixels
                && this.centreReal == other.centreReal && this.centreImaginary == other.centreImaginary
                && this.zoomFactor == other.zoomFactor;
    }
}
