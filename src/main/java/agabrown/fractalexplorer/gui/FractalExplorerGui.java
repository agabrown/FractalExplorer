package agabrown.fractalexplorer.gui;

import agabrown.fractalexplorer.colours.ColourLuts;
import agabrown.fractalexplorer.colours.ImageScaling;
import agabrown.fractalexplorer.dm.ComplexPlaneView;
import agabrown.fractalexplorer.sets.FractalSet;
import agabrown.fractalexplorer.sets.JuliaSet;
import agabrown.fractalexplorer.sets.MandelbrotSet;
import agabrown.fractalexplorer.sets.TricornSet;

import javax.swing.*;
import javax.swing.UIManager.LookAndFeelInfo;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

/**
 * GUI for visually exploring Fractal sets.
 *
 * @author agabrown Jul 2012 - Sep 2021
 */
public class FractalExplorerGui extends JFrame implements KeyListener, MouseListener {

    /**
     * Required for serializable classes.
     */
    private static final long serialVersionUID = -5925852599513426751L;

    /**
     * Default maximum on iterations in calculating whether a point is in the
     * fractal set or not.
     */
    private static final int DEFAULT_MAX_ITERATIONS = 256;
    /**
     * Holds the instance of the GraphicsDevice which the FractalExplorer is
     * using.
     */
    private static final GraphicsDevice GRAPHICS_DEVICE = GraphicsEnvironment.getLocalGraphicsEnvironment()
            .getDefaultScreenDevice();
    /**
     * Width of the image array (length along horizontal screen direction).
     */
    private final int imWidth;
    /**
     * Height of the image array (length along vertical screen direction).
     */
    private final int imHeight;
    /**
     * Contains the array of colour LUTs available to be applied to the fractal
     * image.
     */
    private final ColourLuts[] COLOUR_LUTS = ColourLuts.toArray();
    /**
     * Holds the MandelBrotSet instance.
     */
    private final MandelbrotSet mandelbrotSet = new MandelbrotSet();
    /**
     * Holds the InfoLayerUI instance.
     */
    private final InfoLayerUI infoLayerUI = new InfoLayerUI();
    /**
     * Holds the fractal set to be explored.
     */
    private FractalSet fractalSet;
    /**
     * Holds the Fractal viewing panel.
     */
    private ImageViewingPanel viewingPanel;
    /**
     * Holds the Fractal image array pixel values
     */
    private double[] fractalImage;
    /**
     * Maximum number of iterations for calculating whether a point is in the
     * fractal set or not.
     */
    private int maxIterations;
    /**
     * The current view of the complex plane.
     */
    private ComplexPlaneView activeCpv;
    /**
     * Saves the complex plane view for the Mandelbrot set.
     */
    private ComplexPlaneView mandelbrotCpv;
    /**
     * If true depict the fractal set in black (point in set) and white (point not
     * in set) only.
     */
    private boolean blackAndWhite;
    /**
     * If true the fractal info layer is visible.
     */
    private boolean infoVisible;
    /**
     * If true the help info layer is visible.
     */
    private boolean helpVisible;
    /**
     * True if Julia set should be shown instead of Mandelbrot set.
     */
    private boolean showJuliaSet;
    /**
     * True if Tricorn set should be shown instead of Mandelbrot set.
     */
    private boolean showTricornSet;
    /**
     * The index of the LUT currently in use.
     */
    private int lutIndex;
    /**
     * If true colour scale should be inverted.
     */
    private boolean reverseLut;
    /**
     * Holds the form which can be used to specify the centre of the complex plane
     * view and the zoom factor.
     */
    private CenterPointForm cpvForm;

    /**
     * Constructor. Contains code to detect whether full screen mode is
     * supported in the graphics environment from which FractalExplorer is
     * invoked.
     *
     * <p>
     * NOTE: Exits with {@link java.awt.HeadlessException} if the graphics
     * environment is found to be headless. For example, the programme will not
     * run on the Linux console.
     * </p>
     */
    public FractalExplorerGui() {
        super("FractalExplorer");

        final SplashScreen splash = new SplashScreen(3000);
        splash.showSplash();

        selectLookAndFeel();
        fractalSet = mandelbrotSet;
        imWidth = GRAPHICS_DEVICE.getDefaultConfiguration().getBounds().width;
        imHeight = GRAPHICS_DEVICE.getDefaultConfiguration().getBounds().height;
        initializeFields();
        addComponentsToFrame();
        setUndecorated(true);
        pack();
        setSize(new Dimension(imWidth, imHeight));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    /**
     * Main method.
     *
     * @param args Command line arguments.
     */
    public static void main(final String[] args) {
        final FractalExplorerGui fractalExplorer = new FractalExplorerGui();
        SwingUtilities.invokeLater(() -> {
            fractalExplorer.setVisible(true);
            fractalExplorer.setResizable(false);
            fractalExplorer.toFront();
        });
        fractalExplorer.showFractal();
    }

    /**
     * Select the look and feel for this GUI.
     */
    private void selectLookAndFeel() {
        try {
            for (final LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (final UnsupportedLookAndFeelException e) {
            System.out.println("Nimbus look and feel not supported!");
        } catch (final ClassNotFoundException e) {
            System.out.println("Cannot find Nimbus LookAndFeel class!");
        } catch (final InstantiationException e) {
            System.out.println("Cannot create new instance of Nimbus LookAndFeel class");
        } catch (final IllegalAccessException e) {
            System.out.println("Cannot access or initialize Nimbus LookAndFeel class");
        }
    }

    /**
     * Initialize the class fields.
     */
    private void initializeFields() {
        fractalImage = new double[imWidth * imHeight];
        maxIterations = DEFAULT_MAX_ITERATIONS;
        activeCpv = new ComplexPlaneView(imWidth, imHeight);
        mandelbrotCpv = (ComplexPlaneView) activeCpv.clone();
        cpvForm = new CenterPointForm();
        blackAndWhite = true;
        infoVisible = true;
        helpVisible = false;
        showJuliaSet = false;
        showTricornSet = false;
        lutIndex = 0;
        reverseLut = true;
    }

    /**
     * Add the necessary components to the JFrame implementing the GUI.
     */
    private void addComponentsToFrame() {
        viewingPanel = new ImageViewingPanel();
        viewingPanel.setImageScaling(ImageScaling.LINEAR);
        viewingPanel.setColourLut(ColourLuts.GREYSCALE);
        viewingPanel.setReverseColourLut(reverseLut);
        viewingPanel.setFocusable(true);
        viewingPanel.addKeyListener(this);
        viewingPanel.addMouseListener(this);
        this.add(viewingPanel);
        this.add(createInfoLayer());
        this.setGlassPane(new HelpPanel());
        this.getGlassPane().setVisible(helpVisible);
    }

    /**
     * Create the fractal information layer for the viewing panel.
     *
     * @return Instance of JLayer that takes care of presenting the information on
     * the Fractal Explorer state.
     */
    private JLayer<JComponent> createInfoLayer() {
        final JLayer<JComponent> theInfoLayer = new JLayer<>(viewingPanel, infoLayerUI);
        theInfoLayer.setFocusable(false);
        updateInfoLayer();
        return theInfoLayer;
    }

    /**
     * Update the information to be displayed.
     */
    private void updateInfoLayer() {
        infoLayerUI.setFractalName(fractalSet.getName());
        infoLayerUI.setInfoLines(fractalSet.getInfoLines());
        infoLayerUI.setCpvData(activeCpv);
        infoLayerUI.setMaxIterations(maxIterations);
    }

    /**
     * Display the Fractal set by setting the image array in the
     * {@link ImageViewingPanel}.
     */
    private void showFractal() {
        final Instant start = Instant.now();
        calculateFractalSet();
        final Instant stop = Instant.now();
        System.out.println(ChronoUnit.MILLIS.between(start, stop));
        viewingPanel.setImage(fractalImage, imWidth, imHeight);
    }

    /**
     * Calculate the fractal set by using the algorithm implemented in instances
     * of {@link agabrown.fractalexplorer.sets.FractalSet}.
     */
    private void calculateFractalSet() {
        int i, j;
        double x, y;
        for (int k = 0; k < imWidth * imHeight; k++) {
            i = k % imWidth;
            j = k / imWidth;
            x = activeCpv.getValueAtRealPixel(i);
            y = activeCpv.getValueAtImaginaryPixel(j);
            if (blackAndWhite) {
                fractalImage[k] = fractalSet.isPointInSet(x, y, maxIterations) ? 1.0 : 0.0;
            } else {
                fractalImage[k] = fractalSet.numberOfIterationsForPoint(x, y, maxIterations);
            }
        }
    }

    /**
     * Toggle the visibility of the help screen.
     */
    private void toggleHelp() {
        helpVisible = !helpVisible;
        this.getGlassPane().setVisible(helpVisible);
        this.repaint();
    }

    @Override
    public void keyTyped(final KeyEvent e) {
        switch (e.getKeyChar()) {
            case '+':
                activeCpv.doubleZoomFactor();
                showFractal();
                break;
            case '-':
                activeCpv.halveZoomFactor();
                showFractal();
                break;
            default:
        }
        updateInfoLayer();
    }

    @Override
    public void keyPressed(final KeyEvent e) {
        if (helpVisible) {
            if (e.getKeyCode() == KeyEvent.VK_H) {
                toggleHelp();
            }
            return;
        }
        switch (e.getKeyCode()) {
            case KeyEvent.VK_ESCAPE:
                System.exit(0);
                break;
            case KeyEvent.VK_C:
                blackAndWhite = !blackAndWhite;
                if (blackAndWhite) {
                    viewingPanel.setImageScaling(ImageScaling.LINEAR);
                    viewingPanel.setReverseColourLut(reverseLut);
                    viewingPanel.setColourLut(ColourLuts.GREYSCALE);
                } else {
                    viewingPanel.setImageScaling(ImageScaling.LOGARITHMIC);
                    viewingPanel.setReverseColourLut(reverseLut);
                    viewingPanel.setColourLut(COLOUR_LUTS[lutIndex]);
                }
                showFractal();
                break;
            case KeyEvent.VK_D:
                reverseLut = !reverseLut;
                viewingPanel.setReverseColourLut(reverseLut);
                showFractal();
                break;
            case KeyEvent.VK_PAGE_DOWN:
                lutIndex = Math.min(lutIndex + 1, COLOUR_LUTS.length - 1);
                if (!blackAndWhite) {
                    viewingPanel.setColourLut(COLOUR_LUTS[lutIndex]);
                }
                break;
            case KeyEvent.VK_PAGE_UP:
                lutIndex = Math.max(0, lutIndex - 1);
                if (!blackAndWhite) {
                    viewingPanel.setColourLut(COLOUR_LUTS[lutIndex]);
                }
                break;
            case KeyEvent.VK_1:
                maxIterations = 256;
                showFractal();
                break;
            case KeyEvent.VK_2:
                maxIterations = 512;
                showFractal();
                break;
            case KeyEvent.VK_3:
                maxIterations = 1024;
                showFractal();
                break;
            case KeyEvent.VK_4:
                maxIterations = 2048;
                showFractal();
                break;
            case KeyEvent.VK_5:
                maxIterations = 4096;
                showFractal();
                break;
            case KeyEvent.VK_R:
                activeCpv.reset();
                if (showJuliaSet) {
                    activeCpv.setCentre(0.0, 0.0);
                }
                showFractal();
                break;
            case KeyEvent.VK_Z:
                final ComplexPlaneView newCpv = cpvForm.showFormAndGetCpvData(activeCpv);
                if (!activeCpv.equals(newCpv)) {
                    activeCpv = newCpv;
                    showFractal();
                }
                break;
            case KeyEvent.VK_S:
                new ExportImageUi(viewingPanel.getBufferedImage());
                break;
            case KeyEvent.VK_I:
                infoVisible = !infoVisible;
                infoLayerUI.setVisible(infoVisible);
                this.repaint();
                break;
            case KeyEvent.VK_H:
                toggleHelp();
                break;
            case KeyEvent.VK_ENTER:
                final Point mousePosition = MouseInfo.getPointerInfo().getLocation();
                final double newCentreRe = activeCpv.getValueAtRealPixel(mousePosition.getX());
                final double newCentreIm = activeCpv.getValueAtImaginaryPixel(mousePosition.getY());
                activeCpv.setCentre(newCentreRe, newCentreIm);
                showFractal();
                break;
            case KeyEvent.VK_J:
                if (showTricornSet) {
                    break;
                }
                showJuliaSet = !showJuliaSet;
                if (showJuliaSet) {
                    mandelbrotCpv = (ComplexPlaneView) activeCpv.clone();
                    JuliaSet juliaSet = new JuliaSet(activeCpv.getCentreReal(), activeCpv.getCentreImaginary());
                    activeCpv.reset();
                    activeCpv.setCentre(0.0, 0.0);
                    fractalSet = juliaSet;
                } else {
                    activeCpv = mandelbrotCpv;
                    fractalSet = mandelbrotSet;
                }
                showFractal();
                break;
            case KeyEvent.VK_T:
                if (showJuliaSet) {
                    break;
                }
                showTricornSet = !showTricornSet;
                if (showTricornSet) {
                    mandelbrotCpv = (ComplexPlaneView) activeCpv.clone();
                    fractalSet = new TricornSet();
                } else {
                    activeCpv = mandelbrotCpv;
                    fractalSet = mandelbrotSet;
                }
                showFractal();
                break;
            default:
        }
        updateInfoLayer();
    }

    @Override
    public void keyReleased(final KeyEvent e) {
    }

    @Override
    public void mouseClicked(final MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) {
            final double newCentreRe = activeCpv.getValueAtRealPixel(e.getX());
            final double newCentreIm = activeCpv.getValueAtImaginaryPixel(e.getY());
            activeCpv.setCentre(newCentreRe, newCentreIm);
            showFractal();
        }
        updateInfoLayer();
    }

    @Override
    public void mousePressed(final MouseEvent e) {
    }

    @Override
    public void mouseReleased(final MouseEvent e) {
    }

    @Override
    public void mouseEntered(final MouseEvent e) {
    }

    @Override
    public void mouseExited(final MouseEvent e) {
    }

}
