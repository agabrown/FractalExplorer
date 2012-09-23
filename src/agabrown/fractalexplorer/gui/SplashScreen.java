package agabrown.fractalexplorer.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Toolkit;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JWindow;

import agabrown.fractalexplorer.util.FEConstants;

/**
 * Implements a simple splash screen for the Fractal Explorer GUI.
 * 
 * <pre>
 * Code idea from:
 * http://www.java-tips.org/java-se-tips/javax.swing/how-to-implement-a-splash-screen-for-an-applic-2.html
 * </pre>
 * 
 * @author agabrown 25 Jul 2012
 * 
 */
public final class SplashScreen extends JWindow {

  /**
   * Required for serializable classes.
   */
  private static final long serialVersionUID = -6783272139647153927L;

  /**
   * Duration of splash screen in milli-seconds.
   */
  private final int duration;

  /**
   * Constructor.
   * 
   * @param d
   *          Duration of splash screen in milli-seconds.
   */
  public SplashScreen(final int d) {
    duration = d;
  }

  /**
   * A simple little method to show a title screen in the centre of the screen for the amount of
   * time given in the constructor
   */
  public void showSplash() {

    final JPanel container = (JPanel) getContentPane();
    container.setBackground(Color.black);

    /*
     * Build the splash screen
     */
    final ImageIcon icon = new ImageIcon(getClass().getResource("resources/FractalExplorer-splash.png"));
    final JLabel image = new JLabel(icon);
    final JLabel name = new JLabel(FEConstants.PROJECT_NAME + " version " + FEConstants.VERSION, JLabel.CENTER);
    name.setFont(new Font("Sans-Serif", Font.BOLD, 12));
    name.setForeground(Color.green);
    final JLabel author = new JLabel(FEConstants.AUTHOR_NAME + " - " + FEConstants.YEARS, JLabel.CENTER);
    author.setFont(new Font("Sans-Serif", Font.PLAIN, 12));
    author.setForeground(new Color(0, 255, 0, 192));
    final JLabel help = new JLabel("Type 'h' for help", JLabel.CENTER);
    help.setFont(new Font("Sans-Serif", Font.PLAIN, 12));
    help.setForeground(new Color(0, 255, 0, 192));

    /*
     * Set the window's bounds, centering the window
     */
    final int width = icon.getIconWidth();
    final int height = icon.getIconHeight() + name.getPreferredSize().height + author.getPreferredSize().height
        + help.getPreferredSize().height;
    final Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
    final int x = (screen.width - width) / 2;
    final int y = (screen.height - height) / 2;
    setBounds(x, y, width, height);

    container.setLayout(new GridBagLayout());
    final GridBagConstraints constraints = new GridBagConstraints();

    constraints.fill = GridBagConstraints.NONE;
    constraints.gridy = 0;
    container.add(image, constraints);
    constraints.gridy = 1;
    container.add(name, constraints);
    constraints.gridy = 2;
    container.add(author, constraints);
    constraints.gridy = 3;
    container.add(help, constraints);

    /*
     * Display it
     */
    pack();
    setVisible(true);

    /*
     * Wait a little while, maybe while loading resources
     */
    try {
      Thread.sleep(duration);
    } catch (final Exception e) {
    }

    setVisible(false);

  }
}
