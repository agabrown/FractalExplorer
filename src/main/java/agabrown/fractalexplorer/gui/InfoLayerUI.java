package agabrown.fractalexplorer.gui;

import agabrown.fractalexplorer.dm.ComplexPlaneView;

import javax.swing.JComponent;
import javax.swing.plaf.LayerUI;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.font.FontRenderContext;
import java.awt.font.TextLayout;
import java.util.List;

/**
 * Provides a JLayer version of an informative layer shown on top of the Fractal
 * Explorer main image. Shows where in the focal plane we are, at what zoom
 * factor and how many iterations are used for the Fractal set escape time
 * algorithm.
 *
 * @author agabrown Jul 2012 - Sep 2021
 */
public final class InfoLayerUI extends LayerUI<JComponent> {

    /**
     * Required for serializable classes.
     */
    private static final long serialVersionUID = 1058838429214094654L;

    /**
     * Height of text line in pixels.
     */
    private static final int LINE_HEIGHT = 15;

    /**
     * If true display the information layer.
     */
    private boolean showInfo = true;

    private String fractalName;

    private List<String> infoLines;

    private ComplexPlaneView cpvData;

    private int maxIterations;

    /**
     * Constructor which initializes the relevant data for drawing the info layer.
     */
    public InfoLayerUI() {
        super();
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
        final int numLines = 5 + infoLines.size();
        int lineNumber = 0;
        final int lineZeroY = 10 + LINE_HEIGHT;
        g2.setColor(new Color(255, 255, 255, 192));
        g2.fillRect(10, 10, 250, numLines * LINE_HEIGHT + LINE_HEIGHT);
        g2.setColor(Color.black);
        TextLayout textBox = new TextLayout(fractalName, textFont, frc);
        textBox.draw(g2, 20, lineZeroY);
        for (final String line : infoLines) {
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
        textBox = new TextLayout("Iterations: " + maxIterations, textFont, frc);
        textBox.draw(g2, 20, lineZeroY + lineNumber * LINE_HEIGHT);
        g2.dispose();
    }

    /**
     * Set whether the information layer is visible.
     *
     * @param show If true show the information layer.
     */
    public void setVisible(final boolean show) {
        showInfo = show;
    }

    /**
     * Set the name of the fractal being displayed.
     *
     * @param fractalName The name of the fractal.
     */
    public void setFractalName(final String fractalName) {
        this.fractalName = fractalName;
    }

    /**
     * Set the information lines corresponding to the fractal being displayed.
     *
     * @param infoLines The information lines.
     */
    public void setInfoLines(final List<String> infoLines) {
        this.infoLines = infoLines;
    }

    /**
     * Set the active complex plane view.
     *
     * @param cpvData The complex plane view.
     */
    public void setCpvData(final ComplexPlaneView cpvData) {
        this.cpvData = cpvData;
    }

    /**
     * Set maximum number of iterations currently used.
     *
     * @param maxIterations Maximum number of iterations.
     */
    public void setMaxIterations(final int maxIterations) {
        this.maxIterations = maxIterations;
    }

}
