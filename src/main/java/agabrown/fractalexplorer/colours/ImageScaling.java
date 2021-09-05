// ImageScaling.java

package agabrown.fractalexplorer.colours;

import java.util.Arrays;

/**
 * Class of enum type that can be used to apply a certain scaling to image data (in vector or array form). The data are
 * scaled to the range [0,1] in various ways.
 *
 * <pre>
 *   Code cribbed from agabplot package.
 * </pre>
 *
 * @author Anthony Brown Jul 2012 - Sep 2021
 */
public enum ImageScaling {

    /**
     * Scale the data linearly
     */
    LINEAR("Linear scaling") {
        @Override
        public double[] scaleData(final double[] data) {
            final double[] dataCopy = Arrays.copyOf(data, data.length);
            final double[] minMaxVal = minMax(dataCopy);
            double valRange = minMaxVal[1] - minMaxVal[0];
            if (valRange <= 0.0) {
                valRange = 1.0;
            }
            for (int i = 0; i < dataCopy.length; i++) {
                dataCopy[i] = (dataCopy[i] - minMaxVal[0]) / valRange;
            }
            return dataCopy;
        }
    },
    /**
     * Scale the data by first taking the square root of the values (and properly account for negative data values).
     */
    SQUAREROOT("Sqrt scaling") {
        @Override
        public double[] scaleData(final double[] data) {
            final double[] dataCopy = Arrays.copyOf(data, data.length);
            final double[] minMaxVal = minMax(dataCopy);
            for (int i = 0; i < dataCopy.length; i++) {
                dataCopy[i] = Math.sqrt(dataCopy[i] - minMaxVal[0]);
            }
            double valRange = Math.sqrt(minMaxVal[1] - minMaxVal[0]);
            if (valRange <= 0.0) {
                valRange = 1.0;
            }
            for (int i = 0; i < dataCopy.length; i++) {
                dataCopy[i] = dataCopy[i] / valRange;
            }
            return dataCopy;
        }
    },
    /**
     * Scale the data by first taking the base-10 logarithm of the values (and properly account for negative data
     * values).
     */
    LOGARITHMIC("Log scaling") {
        @Override
        public double[] scaleData(final double[] data) {
            final double[] dataCopy = Arrays.copyOf(data, data.length);
            final double[] minMaxVal = minMax(dataCopy);
            double minLog = Double.MAX_VALUE, maxLog = -Double.MAX_VALUE;
            for (int i = 0; i < dataCopy.length; i++) {
                if ((dataCopy[i] - minMaxVal[0]) > 0.0) {
                    dataCopy[i] = Math.log10(dataCopy[i] - minMaxVal[0]);
                    if (minLog > dataCopy[i]) minLog = dataCopy[i];
                    if (maxLog < dataCopy[i]) maxLog = dataCopy[i];
                } else {
                    dataCopy[i] = 0.0;
                }
            }
            double valRange = maxLog - minLog;
            if (valRange <= 0.0) {
                valRange = 1.0;
            }
            for (int i = 0; i < dataCopy.length; i++) {
                if (dataCopy[i] > 0.0) {
                    dataCopy[i] = (255.0 * (dataCopy[i] - minLog) / valRange + 1.0) / 256.0;
                } else {
                    dataCopy[i] = 0.0;
                }
            }
            return dataCopy;
        }
    },
    ;

    /**
     * Descriptive string for enum.
     */
    private final String label;

    /**
     * Constructor.
     *
     * @param lab Descriptive string for enum.
     */
    ImageScaling(final String lab) {
        this.label = lab;
    }

    /**
     * Determine the minimum and maximum value of the input data vector.
     *
     * @param dataVec The data vector.
     * @return Two-element array containing minimum and maximum as the first and second entry, respectively.
     */
    private static double[] minMax(final double[] dataVec) {
        final double[] copyOfDataVec = Arrays.copyOf(dataVec, dataVec.length);
        Arrays.sort(copyOfDataVec);
        return new double[]{copyOfDataVec[0], copyOfDataVec[copyOfDataVec.length - 1]};
    }

    /**
     * Provide a descriptive string of the enum.
     *
     * @return Descriptive string for enum.
     */
    @Override
    public String toString() {
        return label;
    }

    /**
     * Provide a scaling method for a double[] array. Scale the data to the interval [0,1] for use with the ColourtTable
     * class.
     *
     * @param data The input data array to be scaled.
     * @return The ImageScaling enum instance.
     */
    public abstract double[] scaleData(double[] data);

}
