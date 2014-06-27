package agabrown.fractalexplorer.util;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;

import com.itextpdf.awt.PdfGraphics2D;
import com.itextpdf.text.Document;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfWriter;

import agabrown.fractalexplorer.gui.ImageViewingPanel;

/**
 * Defines the different file formats that can be used to export images and also contains the
 * methods to do the actual exporting.
 * 
 * <pre>
 * Code cribbed from agabplot project.
 * </pre>
 * 
 * @author Anthony Brown Jul 2012
 * 
 */
public enum ExportFormat {

  /**
   * Export the image to a PDF file. The PDF is created using the <a
   * href="http://itextpdf.com/">iText</a> library. The code was taken and modified from the
   * Graphics2D example in the iText tutorial.
   */
  PDF("Adobe Portable Document Format | PDF") {
    @Override
    public void export(final ImageViewingPanel viewPanel, final String fileName, final int w, final int h)
        throws Exception {
      final Dimension pageFrame = new Dimension();
      pageFrame.setSize(w, h);
      /*
       * step 1: creation of a document-object. Use a custom page size (which fits around the
       * figure) making use of iText's Rectangle class to define the dimensions (note that it can
       * also be used for background colouring for example).
       */
      final Document document = new Document(new Rectangle(w, h));

      /*
       * step 2: create a writer that listens to the document and directs a PDF-stream to a file
       */
      final PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(fileName));

      /*
       * Add creation date and information on the application that created the document.
       */
      document.addCreationDate();
      document.addAuthor(System.getenv("USER"));
      document.addCreator("FractalExplorer version " + FEConstants.VERSION);

      /*
       * step 3: open the document
       */
      document.open();

      /*
       * step 4: create the ContentByte and a Graphics2D object that corresponds to it
       */
      final PdfContentByte canvas = writer.getDirectContent();
      final Graphics2D pdfg2d = new PdfGraphics2D(canvas, w, h);

      viewPanel.paintComponentOffScreen(pdfg2d);

      pdfg2d.dispose();

      /*
       * step 5: close the document
       */
      document.close();
    }

    @Override
    public String extension() {
      return "pdf";
    }

  },
  /**
   * Export the plot to a PNG file. Code taken from example in Image I/O API guide.
   */
  PNG("Portable Network Graphics | PNG") {
    @Override
    public void export(final ImageViewingPanel viewPanel, final String fileName, final int w, final int h)
        throws Exception {
      final Dimension pageFrame = new Dimension();
      pageFrame.setSize(w, h);

      /*
       * Find png image writer
       */
      final Iterator<ImageWriter> writers = ImageIO.getImageWritersByFormatName("png");
      final ImageWriter writer = writers.next();

      /*
       * Once an ImageWriter has been obtained, its destination must be set to an ImageOutputStream:
       */
      final File f = new File(fileName);
      final ImageOutputStream ios = ImageIO.createImageOutputStream(f);
      writer.setOutput(ios);

      /*
       * Finally, the image may be written to the output stream:
       */
      final BufferedImage bi = new BufferedImage(w, h, BufferedImage.TYPE_4BYTE_ABGR);
      final Graphics2D big2d = bi.createGraphics();

      final Color saveColor = big2d.getColor();
      big2d.fillRect(0, 0, w, h);
      big2d.setColor(saveColor);
      viewPanel.paintComponentOffScreen(big2d);
      writer.write(bi);

      ios.close();

      big2d.dispose();
    }

    @Override
    public String extension() {
      return "png";
    }

  },
  /**
   * Export the plot to a JPEG file. Code taken from example in Image I/O API guide.
   */
  JPEG("Joint Photographic Experts Group | JPEG") {
    @Override
    public void export(final ImageViewingPanel viewPanel, final String fileName, final int w, final int h)
        throws Exception {
      final Dimension pageFrame = new Dimension();
      pageFrame.setSize(w, h);

      /*
       * Find jpeg image writer
       */
      final Iterator<ImageWriter> writers = ImageIO.getImageWritersByFormatName("jpeg");
      final ImageWriter writer = writers.next();

      /*
       * Once an ImageWriter has been obtained, its destination must be set to an ImageOutputStream:
       */
      final File f = new File(fileName);
      final ImageOutputStream ios = ImageIO.createImageOutputStream(f);
      writer.setOutput(ios);

      /*
       * Finally, the image may be written to the output stream:
       */
      final BufferedImage bi = new BufferedImage(w, h, BufferedImage.TYPE_3BYTE_BGR);
      final Graphics2D big2d = bi.createGraphics();

      final Color saveColor = big2d.getColor();
      big2d.fillRect(0, 0, w, h);
      big2d.setColor(saveColor);
      viewPanel.paintComponentOffScreen(big2d);
      writer.write(bi);

      ios.close();

      big2d.dispose();
    }

    @Override
    public String extension() {
      return "jpg";
    }

  },
  ;

  /**
   * Descriptive string of the file format.
   */
  private String description;

  /**
   * Constructor
   * 
   * @param descr
   *          A descriptive string of the file format.
   */
  private ExportFormat(final String descr) {
    description = descr;
  }

  /**
   * @return A descriptive string of the file format.
   */
  @Override
  public String toString() {
    return description;
  }

  /**
   * Export the fractal set image contained in the given
   * {@link agabrown.fractalexplorer.gui.ImageViewingPanel} to the file format in question.
   * 
   * @param viewPanel
   *          The ImageViewingPanel that contains the Fractal set image for export.
   * @param fileName
   *          Name of the output file.
   * @param w
   *          Width of the plot in device units.
   * @param h
   *          Height of the plot in device units.
   * @throws Exception
   *           In case there is a problem creating or writing to the output file.
   */
  public abstract void export(final ImageViewingPanel viewPanel, final String fileName, final int w, final int h)
      throws Exception;

  /**
   * Obtain the filename extension for this format.
   * 
   * @return The filename extension string.
   */
  public abstract String extension();
}
