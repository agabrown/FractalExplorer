package agabrown.fractalexplorer.gui;

import java.awt.Dimension;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLayer;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.UnsupportedLookAndFeelException;

import agabrown.fractalexplorer.colours.ColourLuts;
import agabrown.fractalexplorer.colours.ImageScaling;
import agabrown.fractalexplorer.dm.ComplexPlaneView;
import agabrown.fractalexplorer.sets.FractalSet;
import agabrown.fractalexplorer.sets.JuliaSet;
import agabrown.fractalexplorer.sets.MandelbrotSet;

/**
 * GUI for visually exploring Fractal sets.
 * 
 * @author agabrown 21 Jul 2012
 * 
 */
public class FractalExplorerGui extends JFrame implements KeyListener, MouseListener {

  /**
   * Required for serializable classes.
   */
  private static final long serialVersionUID = -5925852599513426751L;

  /**
   * Scaling factor for determining the size of the image.
   */
  private static final int IMAGE_SCALE_FACTOR = 180;

  /**
   * Default maximum on iterations in calculating whether a point is in the
   * fractal set or not.
   */
  private static final int DEFAULT_MAX_ITERATIONS = 256;

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
   * Width of the image array (length along horizontal screen direction).
   */
  private int imWidth;

  /**
   * Height of the image array (length along vertical screen direction).
   */
  private int imHeight;

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
   * Contains the array of colour LUTs available to be applied to the fractal
   * image.
   */
  private final ColourLuts[] COLOUR_LUTS = ColourLuts.toArray();

  /**
   * The index of the LUT currently in use.
   */
  private int lutIndex;

  /**
   * Holds the MandelBrotSet instance.
   */
  private final MandelbrotSet mandelbrotSet = new MandelbrotSet();

  /**
   * Holds the JuliaSet instance.
   */
  private final JuliaSet juliaSet = new JuliaSet();

  /**
   * Holds the InfoLayerUI instance.
   */
  private final InfoLayerUI infoLayerUI = new InfoLayerUI(this);

  /**
   * Constructor.
   */
  public FractalExplorerGui() {
    super("FractalExplorer");
    selectLookAndFeel();
    fractalSet = mandelbrotSet;
    initializeFields();
    addComponentsToFrame();
    pack();
    setSize(new Dimension(imWidth, imHeight));
    setResizable(false);
    setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

    final SplashScreen splash = new SplashScreen(3000);
    splash.showSplash();
    setVisible(true);
    toFront();
  }

  /**
   * Main method.
   * 
   * @param args
   *          Command line arguments.
   */
  public static void main(final String[] args) {
    final FractalExplorerGui fractalExplorer = new FractalExplorerGui();
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
  protected FractalSet getFractalSet() {
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
    imWidth = 6 * IMAGE_SCALE_FACTOR;
    imHeight = 4 * IMAGE_SCALE_FACTOR;
    fractalImage = new double[imWidth * imHeight];
    maxIterations = DEFAULT_MAX_ITERATIONS;
    activeCpv = new ComplexPlaneView(imWidth, imHeight);
    mandelbrotCpv = (ComplexPlaneView) activeCpv.clone();
    blackAndWhite = true;
    infoVisible = true;
    helpVisible = false;
    showJuliaSet = false;
    lutIndex = 0;
  }

  /**
   * Add the necessary components to the JFrame implementing the GUI.
   */
  private void addComponentsToFrame() {
    viewingPanel = new ImageViewingPanel();
    viewingPanel.setImageScaling(ImageScaling.LINEAR);
    viewingPanel.setColourLut(ColourLuts.GREYSCALE);
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
    return theInfoLayer;
  }

  /**
   * Display the Fractal set by setting the image array in the
   * {@link ImageViewingPanel}.
   */
  private void showFractal() {
    calculateFractalSet();
    viewingPanel.setImage(fractalImage, imWidth, imHeight);
  }

  /**
   * Calculate the fractal set by using the "escape time algorithm" implemented
   * in {@link agabrown.fractalexplorer.sets.MandelbrotSet}.
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
        viewingPanel.setImageScaling(blackAndWhite ? ImageScaling.LINEAR : ImageScaling.LOGARITHMIC);
        viewingPanel.setColourLut(blackAndWhite ? ColourLuts.GREYSCALE : COLOUR_LUTS[lutIndex]);
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
      case KeyEvent.VK_Z:
        new CenterPointForm(this, activeCpv);
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
      case KeyEvent.VK_S:
        new ExportImageUi(this);
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
        showJuliaSet = !showJuliaSet;
        if (showJuliaSet) {
          mandelbrotCpv = (ComplexPlaneView) activeCpv.clone();
          juliaSet.setMuParameter(activeCpv.getCentreReal(), activeCpv.getCentreImaginary());
          activeCpv.reset();
          activeCpv.setCentre(0.0, 0.0);
          fractalSet = juliaSet;
        } else {
          activeCpv = mandelbrotCpv;
          fractalSet = mandelbrotSet;
        }
        showFractal();
        break;
      default:
    }
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
