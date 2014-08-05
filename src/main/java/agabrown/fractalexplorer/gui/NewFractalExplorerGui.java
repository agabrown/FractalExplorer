package agabrown.fractalexplorer.gui;

import java.awt.Dimension;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLayer;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.UnsupportedLookAndFeelException;

import org.apache.commons.math3.complex.Complex;

import agabrown.fractalexplorer.colours.ColourLuts;
import agabrown.fractalexplorer.colours.ImageScaling;
import agabrown.fractalexplorer.dm.ComplexPlaneView;
import agabrown.fractalexplorer.generators.FractalGenerator;
import agabrown.fractalexplorer.generators.FractalGeneratorFactory;
import agabrown.fractalexplorer.generators.JuliaFatouGenerator;
import agabrown.fractalexplorer.generators.MandelbrotGenerator;

/**
 * FractalExplorer GUI which makes use of new classes in the
 * {@link agabrown.fractalexplorer.generators} package.
 *
 * @author agabrown Aug 2014
 *
 */
public final class NewFractalExplorerGui extends JFrame implements KeyListener, MouseListener, PropertyChangeListener {

  /**
   * Required for serializable classes.
   */
  private static final long serialVersionUID = 1984171807859617860L;

  /**
   * Default maximum on iterations in calculating whether a point is in the
   * fractal set or not.
   */
  private static final int DEFAULT_MAX_ITERATIONS = 256;

  /**
   * Holds the fractal set to be explored.
   */
  private FractalGenerator fractalSet;

  /**
   * Holds the Fractal viewing panel.
   */
  private ImageViewingPanel viewingPanel;

  /**
   * Holds the Fractal image array pixel values
   */
  private double[] fractalImage;

  /**
   * Width of the image array (length along horizontal screen direction).
   */
  private final int imWidth;

  /**
   * Height of the image array (length along vertical screen direction).
   */
  private final int imHeight;

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
   * If true use logarithmic scaling of the images. Linear otherwise.
   */
  private boolean logScaling;

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
   * Contains the array of colour LUTs available to be applied to the fractal
   * image.
   */
  private final ColourLuts[] COLOUR_LUTS = ColourLuts.toArray();

  /**
   * The index of the LUT currently in use.
   */
  private int lutIndex;

  /**
   * If true colour scale should be inverted.
   */
  private boolean reverseLut;

  /**
   * Holds the MandelBrotSet instance.
   */
  private final MandelbrotGenerator mandelbrot = FractalGeneratorFactory.getMandelbrotEscapeTime();

  /**
   * Holds the JuliaSet instance.
   */
  private JuliaFatouGenerator julia;

  /**
   * Holds the InfoLayerUI instance.
   */
  private final InfoLayerUI infoLayerUI = new InfoLayerUI();

  /**
   * Holds the form which can be used to specify the centre of the complex plane
   * view and the zoom factor.
   */
  private CenterPointForm cpvForm;

  /**
   * Holds the instance of the SwingWorker that invokes the calculation of the
   * Fractal image.
   */
  private FractalCalculationTask fcTask;

  /**
   * Holds the instance of the GraphicsDevice which the FractalExplorer is
   * using.
   */
  private static final GraphicsDevice GRAPHICS_DEVICE = GraphicsEnvironment.getLocalGraphicsEnvironment()
      .getDefaultScreenDevice();

  /**
   * Constructor. Contains code to detect whether or not full screen mode is
   * supported in the graphics environment from which FractalExplorer is
   * invoked.
   *
   * <p>
   * NOTE: Exits with {@link java.awt.HeadlessException} if the graphics
   * environment is found to be headless. For example, the programme will not
   * run on the Linux console.
   * </p>
   */
  public NewFractalExplorerGui() {
    super("FractalExplorer");

    selectLookAndFeel();
    fractalSet = mandelbrot;
    imWidth = GRAPHICS_DEVICE.getDefaultConfiguration().getBounds().width;
    imHeight = GRAPHICS_DEVICE.getDefaultConfiguration().getBounds().height;
    initializeFields();
    addComponentsToFrame();
    setUndecorated(true);
    // final boolean isFullScreen = GRAPHICS_DEVICE.isFullScreenSupported();
    // setUndecorated(isFullScreen);
    // if (isFullScreen) {
    // GRAPHICS_DEVICE.setFullScreenWindow(this);
    // validate();
    // } else {
    pack();
    setSize(new Dimension(imWidth, imHeight));
    // }
    setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
  }

  /**
   * Main method.
   *
   * @param args
   *          Command line arguments.
   */
  public static void main(final String[] args) {
    new SplashScreen(3000).showSplash();

    final NewFractalExplorerGui fractalExplorer = new NewFractalExplorerGui();
    SwingUtilities.invokeLater(new Runnable() {
      @Override
      public void run() {
        fractalExplorer.setVisible(true);
        fractalExplorer.setResizable(false);
        fractalExplorer.toFront();
      }
    });
    fractalExplorer.showFractal();
  }

  /**
   * Get a reference to the viewing panel.
   *
   * @return The instance of ImageViewingPanel used by this explorer.
   */
  protected ImageViewingPanel getViewPanel() {
    return viewingPanel;
  }

  /**
   * Obtain the number of iterations used in calculating the image of the
   * Fractal set.
   *
   * @return The maximum number of iterations.
   */
  protected int getMaxIterations() {
    return maxIterations;
  }

  /**
   * Get a reference to the active FractalSet instance.
   *
   * @return The reference to the FractalSet instance used by the explorer.
   */
  protected FractalGenerator getFractalSet() {
    return fractalSet;
  }

  /**
   * Get the current view on the complex plane.
   *
   * @return The active complex plane view.
   */
  protected ComplexPlaneView getComplexPlaneView() {
    return (ComplexPlaneView) activeCpv.clone();
  }

  /**
   * Set the complex plane view.
   *
   * @param cpv
   *          New complex plane view data.
   */
  protected void setComplexPlaneView(final ComplexPlaneView cpv) {
    activeCpv = (ComplexPlaneView) cpv.clone();
    showFractal();
  }

  /**
   * Check whether the help info layer should be visible or not.
   *
   * @return True if the help info layer should be shown.
   */
  protected boolean showHelp() {
    return helpVisible;
  }

  /**
   * Check whether the Julia set is displayed.
   *
   * @return True if the Julia set is displayed.
   */
  protected boolean showJulia() {
    return showJuliaSet;
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
    logScaling = true;
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
    viewingPanel.setImageScaling(ImageScaling.LOGARITHMIC);
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
   *         the Fractal Explorer state.
   */
  private JLayer<JComponent> createInfoLayer() {
    final JLayer<JComponent> theInfoLayer = new JLayer<JComponent>(viewingPanel, infoLayerUI);
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
    calculateFractalSet();
  }

  /**
   * Calculate the fractal image.
   */
  private void calculateFractalSet() {
    fcTask = new FractalCalculationTask(this, fractalSet, activeCpv);
    fcTask.calculateFractalImage();
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
      case KeyEvent.VK_L:
        logScaling = !logScaling;
        if (logScaling) {
          viewingPanel.setImageScaling(ImageScaling.LOGARITHMIC);
        } else {
          viewingPanel.setImageScaling(ImageScaling.LINEAR);
        }
        break;
      case KeyEvent.VK_D:
        reverseLut = !reverseLut;
        viewingPanel.setReverseColourLut(reverseLut);
        break;
      case KeyEvent.VK_PAGE_DOWN:
        lutIndex = Math.min(lutIndex + 1, COLOUR_LUTS.length - 1);
        viewingPanel.setColourLut(COLOUR_LUTS[lutIndex]);
        break;
      case KeyEvent.VK_PAGE_UP:
        lutIndex = Math.max(0, lutIndex - 1);
        viewingPanel.setColourLut(COLOUR_LUTS[lutIndex]);
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
          julia = FractalGeneratorFactory.getJuliaClassicEscapeTime(Complex.valueOf(activeCpv.getCentreReal(),
              activeCpv.getCentreImaginary()));
          activeCpv.reset();
          activeCpv.setCentre(0.0, 0.0);
          fractalSet = julia;
        } else {
          activeCpv = mandelbrotCpv;
          fractalSet = mandelbrot;
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
          mandelbrot.useConjugate(true);
        } else {
          activeCpv = mandelbrotCpv;
          mandelbrot.useConjugate(false);
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

  @Override
  public void propertyChange(final PropertyChangeEvent evt) {
    if (SwingWorker.StateValue.DONE.equals(evt.getNewValue())) {
      fractalImage = fcTask.getFractalImage();
      viewingPanel.setImage(fractalImage, imWidth, imHeight);
    }
  }

}
