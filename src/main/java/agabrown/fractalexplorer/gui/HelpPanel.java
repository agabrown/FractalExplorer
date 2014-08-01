/**
 *
 */
package agabrown.fractalexplorer.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.font.FontRenderContext;
import java.awt.font.TextLayout;

import javax.swing.JPanel;

/**
 * Provides the simple help panel that is displayed in the GlassPane of
 * {@link agabrown.fractalexplorer.gui.FractalExplorerGui}. The help information
 * is displayed as a table with entries for the keys/mouse-buttons and their
 * functions.
 *
 * @author agabrown 24 Jul 2012
 *
 */
public final class HelpPanel extends JPanel {

  /**
   * Required for serializable classes.
   */
  private static final long serialVersionUID = 5113151088643002585L;

  /**
   * Height of a line of text in pixels.
   */
  private static int lineHeight;

  /**
   * Names of help table columns.
   */
  private static final String[] COLUMN_NAMES = { "Key/Mouse-button", "Function" };

  /**
   * Help table entries.
   */
  private static final String[][] HELP_DATA = { { "h", "Toggle help screen visible" },
    { "ESC", "Exit FractalExplorer" }, { "z", "Set complex number at centre of image and the zoom factor" },
    { "+", "Zoom in by factor 2" }, { "-", "Zoom out by factor two" },
    { "j", "Toggle between Julia and Mandelbrot sets" }, { "t", "Toggle between Tricorn and Mandelbrot sets" },
    { "i", "Toggle information layer" }, { "Left mouse-button/Enter", "Centre on complex number at mouse pointer" },
    { "1 ... 5", "Set the maximum number of iterations (256 ... 4096)" },
    { "r", "Reset image to initial centre point and zoom factor" }, { "s", "Save current image to file" },
    { "c", "Toggle use of colour LUT" }, { "Page-up/page-down", "Cycle through colour LUTs" },
      { "d", "Invert colour scale" } };

  /**
   * Constructor.
   */
  public HelpPanel() {
    super();
    this.setBackground(new Color(0, 0, 0, 128));
    this.setForeground(Color.green);
    lineHeight = 20;
  }

  @Override
  public void paintComponent(final Graphics g) {
    final Graphics2D g2 = (Graphics2D) g.create();
    super.paintComponent(g2);
    final FontRenderContext frc = g2.getFontRenderContext();
    final Font textFont = new Font(Font.SANS_SERIF, Font.BOLD, 20);
    final TextLayout textBox = new TextLayout("How to use the Fractal Explorer", textFont, frc);
    textBox.draw(g2, (float) (this.getWidth() / 2 - textBox.getBounds().getWidth() / 2), lineHeight);
    drawHelpTable(g2, frc);
    g2.dispose();
  }

  /**
   * Draw the help table by simply placing the text on the GlassPane by absolute
   * positioning.
   *
   * @param g2d
   *          Graphics context used for drawing.
   * @param frc
   *          Font render context for the text.
   */
  private void drawHelpTable(final Graphics2D g2d, final FontRenderContext frc) {
    Font textFont = new Font(Font.SANS_SERIF, Font.BOLD, 20);
    TextLayout textBox = new TextLayout(COLUMN_NAMES[0], textFont, frc);
    textBox.draw(g2d, this.getWidth() / 6, 3 * lineHeight);
    textBox = new TextLayout(COLUMN_NAMES[1], textFont, frc);
    textBox.draw(g2d, this.getWidth() / 9 * 4, 3 * lineHeight);
    final int currentLine = 4;
    lineHeight = 28;
    textFont = new Font(Font.DIALOG, Font.PLAIN, 18);
    for (int i = 0; i < HELP_DATA.length; i++) {
      textBox = new TextLayout(HELP_DATA[i][0], textFont, frc);
      textBox.draw(g2d, this.getWidth() / 6, (currentLine + i) * lineHeight);
      textBox = new TextLayout(HELP_DATA[i][1], textFont, frc);
      textBox.draw(g2d, this.getWidth() / 9 * 4, (currentLine + i) * lineHeight);
    }
  }

}
