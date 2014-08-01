package agabrown.fractalexplorer.gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.util.Arrays;

import javax.swing.JPanel;

import agabrown.fractalexplorer.colours.ColourLuts;
import agabrown.fractalexplorer.colours.ImageScaling;

/**
 * Panel that displays the image of the fractal set.
 *
 * @author agabrown 21 Jul 2012
 *
 */
public final class ImageViewingPanel extends JPanel {

  /**
   * Required for serializable classes.
   */
  private static final long serialVersionUID = 8393847386755173477L;

  /**
   * Holds the {@code java.awt.image.BufferedImage} instance that represents the
   * false colour image to be displayed. This image is built from the input data
   * and is of the {@code BufferedImage.TYPE_INT_RGB} type.
   */
  private BufferedImage bimg;

  /**
   * Holds a copy of the original image array.
   */
  private double[] image;

  /**
   * Holds the fractal image array pixel values (already scaled between 0 and
   * 1).
   */
  private double[] scaledImage;

  /**
   * Width of the image array (length along horizontal screen direction).
   */
  private int imWidth;

  /**
   * Height of the image array (length along vertical screen direction).
   */
  private int imHeight;

  /**
   * Holds the colour look-up table.
   */
  private ColourLuts colourLut;

  /**
   * Provides the image scaling methods.
   */
  private ImageScaling imageScaling;

  /**
   * If true use the reversed colour scale.
   */
  private boolean reverseLut;

  /**
   * Constructor.
   */
  public ImageViewingPanel() {
    super();
    setOpaque(true);
    colourLut = ColourLuts.GREYSCALE;
    imageScaling = ImageScaling.LOGARITHMIC;
    reverseLut = true;
  }

  /**
   * Set the image of the Fractal set to display.
   *
   * @param imArr
   *          Image of the fractal set.
   * @param width
   *          Width of the image in pixels.
   * @param height
   *          Height of the image in pixels.
   */
  public void setImage(final double[] imArr, final int width, final int height) {
    image = Arrays.copyOf(imArr, imArr.length);
    scaledImage = imageScaling.scaleData(image);
    imWidth = width;
    imHeight = height;
    createBufferedImage();
    repaint();
  }

  /**
   * Set the scaling to be applied to the Fractal set image.
   *
   * @param imScaling
   *          Scaling to be applied before colouring the image.
   */
  public void setImageScaling(final ImageScaling imScaling) {
    imageScaling = imScaling;
    if (image != null) {
      scaledImage = imageScaling.scaleData(image);
      createBufferedImage();
      repaint();
    }
  }

  /**
   * Set whether or not the colour scale should be reversed.
   *
   * @param rev
   *          If true reverse the colour scale.
   */
  public void setReverseColourLut(final boolean rev) {
    reverseLut = rev;
  }

  /**
   * Set the colours to use for the Fractal set image.
   *
   * @param cLut
   *          Colour LUT to use.
   */
  public void setColourLut(final ColourLuts cLut) {
    colourLut = cLut;
    if (image != null) {
      createBufferedImage();
      repaint();
    }
  }

  /**
   * Create the BufferedImage instance, which is what will actually be
   * displayed.
   */
  private void createBufferedImage() {
    bimg = new BufferedImage(imWidth, imHeight, BufferedImage.TYPE_INT_RGB);
    final WritableRaster raster = bimg.getRaster();

    final int[] red = new int[imWidth];
    final int[] green = new int[imWidth];
    final int[] blue = new int[imWidth];

    Color col;

    for (int j = 0; j < imHeight; j++) {
      for (int i = 0; i < imWidth; i++) {
        if (reverseLut) {
          col = colourLut.getReverseColour(scaledImage[i + j * imWidth]);
        } else {
          col = colourLut.getColour(scaledImage[i + j * imWidth]);
        }
        red[i] = col.getRed();
        green[i] = col.getGreen();
        blue[i] = col.getBlue();
      }
      raster.setSamples(0, j, imWidth, 1, 0, red);
      raster.setSamples(0, j, imWidth, 1, 1, green);
      raster.setSamples(0, j, imWidth, 1, 2, blue);
    }
  }

  /**
   * Override the paintComponent() method of JPanel in order to draw the image
   * on screen.
   */
  @Override
  public void paintComponent(final Graphics g) {
    super.paintComponent(g);
    final Graphics2D g2 = (Graphics2D) g;
    if (bimg == null) {
      return;
    }
    if (bimg.getWidth() < 0 || bimg.getHeight() < 0) {
      return;
    }
    g2.drawImage(bimg, 0, 0, null);
    g2.dispose();
  }

  /**
   * Paint method for off screen painting (used for exporting to files).
   *
   * @param g2
   *          Instance of Graphics2D supplied by class creating the export file.
   */
  public void paintComponentOffScreen(final Graphics2D g2) {
    g2.drawImage(bimg, 0, 0, null);
  }

}
