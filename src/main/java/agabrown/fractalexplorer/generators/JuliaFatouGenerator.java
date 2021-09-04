package agabrown.fractalexplorer.generators;

import agabrown.fractalexplorer.colours.ColouringAlgorithm;
import org.apache.commons.math3.complex.Complex;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

/**
 * This class implements a general version of the Julia/Fatou fractal image
 * generators. It iterates a complex function f(z) and generates the image by
 * varying the starting value z0 of the iteration over the complex plane.
 *
 * @author agabrown Aug 2014 - Sep 2021
 */
public final class JuliaFatouGenerator extends ComplexDynamicsBased {

    /**
     * Name of fractal generator.
     */
    private static final String NAME = "Julia-Fatou";

    /**
     * The unicode value for the letter &mu;.
     */
    private static final int MU_UNICODE = 0x03BC;

    /**
     * Contains the info text lines.
     */
    private ArrayList<String> infoLines;

    /**
     * Real part of Julia set parameter &mu;.
     */
    private double muReal;

    /**
     * Imaginary part of Julia set parameter &mu;.
     */
    private double muImaginary;

    /**
     * Private constructor which takes the builder inner class to create a
     * properly configured instance of JuliaFatouGenerator.
     *
     * @param builder The Builder object that contains the information to construct a
     *                JuliaFatouGenerator.
     */
    private JuliaFatouGenerator(final Builder builder) {
        this.colouringAlgorithm = builder.colouringAlgorithm;
        this.theIterator = ComplexFunctionIterator.getInstance(builder.maxIterations, builder.stoppingRadius,
                builder.generatingFunction);
        initializeInfoLines();
    }

    /**
     * Initialize the constant parts of the lines with information on the Julia
     * Set parameters.
     */
    private void initializeInfoLines() {
        infoLines = new ArrayList<>();
        StringBuilder line = new StringBuilder("Re(");
        line.appendCodePoint(MU_UNICODE);
        line.append(") = ");
        line.append(muReal);
        infoLines.add(line.toString());
        line = new StringBuilder("Im(");
        line.appendCodePoint(MU_UNICODE);
        line.append(") = ");
        line.append(muImaginary);
        infoLines.add(line.toString());
        infoLines.trimToSize();
    }

    @Override
    public double generatePixelValue(final Complex z) {
        List<Complex> iterates;
        if (iterateConjugate) {
            iterates = theIterator.iterateConjugate(z);
        } else {
            iterates = theIterator.iterate(z);
        }
        return colouringAlgorithm.getPixelValue(iterates);
    }

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public List<String> getInfoLines() {
        return infoLines;
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
         * Call the constructor for the JuliaFatouGenerator class.
         *
         * @return A new instance of JuliaFatouGenerator.
         */
        public JuliaFatouGenerator build() {
            return new JuliaFatouGenerator(this);
        }
    }

}
