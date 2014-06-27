package agabrown.fractalexplorer.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.font.FontRenderContext;
import java.awt.font.TextLayout;

import javax.swing.JComponent;
import javax.swing.plaf.LayerUI;

import agabrown.fractalexplorer.dm.ComplexPlaneView;

/**
 * Provides a JLayer version of an informative layer shown on top of the Fractal
 * Explorer main image. Shows where in the focal plane we are, at what zoom
 * factor and how many iterations are used for the Fractal set escape time
 * algorithm.
 * 
 * @author agabrown 24 Jul 2012
 * 
 */
public final class InfoLayerUI extends LayerUI<JComponent> {

  /**
   * Required for serializable classes.
   */
  private static final long serialVersionUID = 1058838429214094654L;

  /**
   * The parent of this LayerUI.
   */
  private final FractalExplorerGui explorer;

  /**
   * Height of text line in pixels.
   */
  private static final int LINE_HEIGHT = 15;

  /**
   * If true display the information layer.
   */
  private boolean showInfo = true;

  /**
   * Constructor. Sets the parent explorer GUI, which is polled for the relevant
   * data.
   * 
   * @param feGui
   *          The parent explorer GUI.
   */
  public InfoLayerUI(final FractalExplorerGui feGui) {
    super();
    explorer = feGui;
  }

  @Override
  public void paint(final Graphics g, final JComponent c) {
    final Graphics2D g2 = (Graphics2D) g.create();
    super.paint(g2, c);
    if (!showInfo) {
      g2.dispose();
      return;
    }
    final FontRenderContext frc = g2.getFontRenderContext();
    final Font textFont = new Font(Font.SANS_SERIF, Font.BOLD, 10);
    final ComplexPlaneView cpvData = explorer.getComplexPlaneView();
    final int numLines = 5 + explorer.getFractalSet().getInfoLines().size();
    int lineNumber = 0;
    final int lineZeroY = 10 + LINE_HEIGHT;
    g2.setColor(new Color(255, 255, 255, 192));
    g2.fillRect(10, 10, 250, numLines * LINE_HEIGHT + LINE_HEIGHT);
    g2.setColor(Color.black);
    TextLayout textBox = new TextLayout(explorer.getFractalSet().getName(), textFont, frc);
    textBox.draw(g2, 20, lineZeroY);
    for (final String line : explorer.getFractalSet().getInfoLines()) {
      lineNumber++;
      textBox = new TextLayout(line, textFont, frc);
      textBox.draw(g2, 20, lineZeroY + lineNumber * LINE_HEIGHT);
    }
    lineNumber++;
    textBox = new TextLayout("Re: " + cpvData.getCentreReal(), textFont, frc);
    textBox.draw(g2, 20, lineZeroY + lineNumber * LINE_HEIGHT);
    lineNumber++;
    textBox = new TextLayout("Im: " + cpvData.getCentreImaginary(), textFont, frc);
    textBox.draw(g2, 20, lineZeroY + lineNumber * LINE_HEIGHT);
    lineNumber++;
    textBox = new TextLayout("Zoom: " + cpvData.getZoomFactor(), textFont, frc);
    textBox.draw(g2, 20, lineZeroY + lineNumber * LINE_HEIGHT);
    lineNumber++;
    textBox = new TextLayout("Iterations: " + explorer.getMaxIterations(), textFont, frc);
    textBox.draw(g2, 20, lineZeroY + lineNumber * LINE_HEIGHT);
    g2.dispose();
  }

  /**
   * Set whether or not the information layer is visible.
   * 
   * @param show
   *          If true show the information layer.
   */
  public void setVisible(final boolean show) {
    showInfo = show;
  }

}
