package agabrown.fractalexplorer.generators;

import agabrown.fractalexplorer.colours.ColouringAlgorithm;
import org.apache.commons.math3.complex.Complex;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

/**
 * This class implements a general version of the Mandelbrot fractal image
 * generators. It iterates a complex function f(z)+c and generates the image by
 * varying the value of c over the complex plane. The starting value of the
 * iteration is fixed (at zero).
 *
 * @author agabrown Aug 2014 - Sep 2021
 */
public final class MandelbrotGenerator extends ComplexDynamicsBased {

    /**
     * Name of fractal generator.
     */
    private static final String NAME = "Mandelbrot";

    /**
     * The function f(z) used for generating the Fractal image. Note that the
     * iterations are over f(z)+c.
     */
    private final Function<Complex, Complex> baseGeneratingFunction;

    /**
     * Private constructor which takes the builder inner class to create a
     * properly configured instance of MandelbrotGenerator.
     *
     * @param builder The Builder object that contains the information to construct a
     *                MandelbrotGenerator.
     */
    private MandelbrotGenerator(final Builder builder) {
        this.colouringAlgorithm = builder.colouringAlgorithm;
        this.baseGeneratingFunction = builder.generatingFunction;
        this.theIterator = ComplexFunctionIterator.getInstance(builder.maxIterations, builder.stoppingRadius,
                builder.generatingFunction);
    }

    @Override
    public double generatePixelValue(final Complex z) {
        final Complex zStart = Complex.ZERO;
        List<Complex> iterates;
        theIterator.setFunction(baseGeneratingFunction.andThen(y -> y.add(z)));
        if (iterateConjugate) {
            iterates = theIterator.iterateConjugate(zStart);
        } else {
            iterates = theIterator.iterate(zStart);
        }
        return colouringAlgorithm.getPixelValue(iterates);
    }

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public List<String> getInfoLines() {
        return new ArrayList<>(0);
    }

    /**
     * Inner class used for implementing the builder pattern.
     *
     * @author agabrown Aug 2014
     */
    public static class Builder {
        private ColouringAlgorithm colouringAlgorithm;
        private Function<Complex, Complex> generatingFunction;
        private double stoppingRadius;
        private int maxIterations;

        /**
         * Set the stopping radius for the Fractal generator.
         *
         * @param r Value of the stopping radius.
         * @return The builder.
         */
        public Builder stoppingRadius(final double r) {
            if (r < 0.0 || Double.isInfinite(r) || Double.isNaN(r)) {
                throw new IllegalArgumentException("Value of stopping radius should be positive and finite.");
            }
            stoppingRadius = r;
            return this;
        }

        /**
         * Set the maximum number of generating function iterations for the Fractal
         * generator.
         *
         * @param m Maximum number of iterations.
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
         * @param c The colouring algorithm.
         * @return The builder.
         */
        public Builder colouringAlgorithm(final ColouringAlgorithm c) {
            colouringAlgorithm = c;
            return this;
        }

        /**
         * Set the generating function for the Fractal.
         *
         * @param g The generating function.
         * @return The builder.
         */
        public Builder generatingFunction(final Function<Complex, Complex> g) {
            generatingFunction = g;
            return this;
        }

        /**
         * Call the constructor for the MandelbrotGenerator class.
         *
         * @return A new instance of MandelbrotGenerator.
         */
        public MandelbrotGenerator build() {
            return new MandelbrotGenerator(this);
        }
    }
}
