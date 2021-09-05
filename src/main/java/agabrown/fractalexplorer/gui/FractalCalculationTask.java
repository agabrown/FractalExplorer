package agabrown.fractalexplorer.gui;

import agabrown.fractalexplorer.dm.ComplexPlaneView;
import agabrown.fractalexplorer.generators.FractalGenerator;
import org.apache.commons.math3.complex.Complex;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.concurrent.ExecutionException;

/**
 * This class facilitates doing the fractal calculations in a background thread. The class should be used together with
 * a property change listener method as follows:
 *
 * <pre>
 * <code>
 * fcTask = new FractalCalculationTask();
 * fcTask.calculateFractalImage();
 *
 * ...
 *
 * &#64;Override
 * public void propertyChange(final PropertyChangeEvent evt) {
 *   if (SwingWorker.StateValue.DONE.equals(evt.getNewValue())) {
 *     fractalImage = fcTask.getFractalImage();
 *     // Do something with fractal image.
 *   }
 * }
 * </code>
 * </pre>
 *
 * @author agabrown Aug 2014 - sep 2021
 */
public final class FractalCalculationTask implements PropertyChangeListener {

    /**
     * Holds the reference to the FractalGenerator instance that calculates the fractal.
     */
    private final FractalGenerator generator;

    /**
     * Holds the reference to the ComplexPlaneView instance for which the calculation is to be done.
     */
    private final ComplexPlaneView theCpv;

    /**
     * Holds progress bar.
     */
    private final JProgressBar progressBar;

    /**
     * Holds the frame displaying the progress bar.
     */
    private final JFrame progressFrame;

    /**
     * Class that created this FractalCalculationTask.
     */
    private final PropertyChangeListener taskOwner;

    /**
     * Holds the result of the fractal image calculations.
     */
    private double[] fractalImage;

    /**
     * Create a new Fractal calculation task for a specific Fractal generator and complex plane view.
     *
     * @param owner The class that created this FractalCalculationTask.
     * @param fg    The FractalGenerator instance to use in the calculations.
     * @param cpv   The ComplexPlaneView instance for which the calculation is to be done.
     */
    public FractalCalculationTask(final PropertyChangeListener owner, final FractalGenerator fg,
                                  final ComplexPlaneView cpv) {
        progressFrame = new JFrame("Fractal calculation progress");
        generator = fg;
        theCpv = cpv;
        taskOwner = owner;

        final Border border = new EmptyBorder(new Insets(10, 10, 10, 10));
        progressBar = new JProgressBar(0, 100);
        progressBar.setValue(0);
        progressBar.setStringPainted(true);
        progressBar.setIndeterminate(true);
        // progressBar.setBorder(new EmptyBorder(new Insets(3, 3, 3, 3)));

        final JPanel frameContents = new JPanel(new GridBagLayout());
        frameContents.setBorder(border);
        final GridBagConstraints constraints = new GridBagConstraints();

        final JTextArea text = new JTextArea("Calculating fractal image" + System.getProperty("line.separator")
                + "Patience please");
        text.setEditable(false);
        text.setBorder(border);
        text.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 14));

        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.gridy = 0;
        frameContents.add(text, constraints);
        constraints.gridy = 1;
        frameContents.add(progressBar, constraints);
        frameContents.setBackground(Color.WHITE);
        frameContents.setOpaque(true);
        progressFrame.setContentPane(frameContents);
        progressFrame.pack();
    }

    /**
     * Invoke this method to start the calculation of the fractal image. Will also instantiate a progress bar.
     */
    void calculateFractalImage() {
        progressFrame.setVisible(true);
        progressFrame.toFront();
        Task task = new Task();
        task.addPropertyChangeListener(this);
        task.addPropertyChangeListener(taskOwner);
        task.execute();
    }

    /**
     * Use this method to obtain the result (after the SwingWorker is done, see above).
     *
     * @return The fractal image.
     */
    double[] getFractalImage() {
        return fractalImage;
    }

    @Override
    public void propertyChange(final PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals("progress")) {
            final int progress = (Integer) evt.getNewValue();
            progressBar.setIndeterminate(false);
            progressBar.setValue(progress);
        }
        if (SwingWorker.StateValue.DONE.equals(evt.getNewValue())) {
            progressFrame.dispose();
        }
    }

    /**
     * The SwingWorker sub-class that carries out the actual calculations.
     *
     * @author agabrown Aug 2014.
     */
    private class Task extends SwingWorker<double[], Integer> {

        @Override
        protected double[] doInBackground() {
            int i, j;
            double x, y;
            final int imWidth = theCpv.getSizeRealPixels();
            final int imHeight = theCpv.getSizeImaginaryPixels();
            setProgress(0);
            final double[] image = new double[imWidth * imHeight];
            Complex z;
            for (int k = 0; k < imWidth * imHeight; k++) {
                i = k % imWidth;
                j = k / imWidth;
                x = theCpv.getValueAtRealPixel(i);
                y = theCpv.getValueAtImaginaryPixel(j);
                z = Complex.valueOf(x, y);
                image[k] = generator.generatePixelValue(z);
                if (i == 0) {
                    setProgress((int) Math.round((double) k / image.length * 100.0));
                }
            }
            return image;
        }

        @Override
        protected void done() {
            try {
                fractalImage = get();
            } catch (InterruptedException | ExecutionException e) {
                System.out.println("Something went wrong in the calculation of the Fractal image.");
                e.printStackTrace();
                System.exit(-1);
            }
        }
    }

}
