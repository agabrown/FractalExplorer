package agabrown.fractalexplorer.gui;

import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import agabrown.fractalexplorer.dm.ComplexPlaneView;

/**
 * Provides the simple form that is used to enter the point that the Fractal
 * explorer should be centred on.
 * 
 * @author agabrown 23 Jul 2012
 * 
 */
public final class CenterPointForm extends JFrame implements ActionListener {

  /**
   * Required for serializable classes.
   */
  private static final long serialVersionUID = 5365486744309424317L;

  /**
   * Holds the complex plane view of the Fractal set.
   */
  private final ComplexPlaneView cpvData;

  /**
   * Text field for entering the real part of the centre point.
   */
  private final JTextField realPartText;

  /**
   * Text field for entering the imaginary part of the centre point.
   */
  private final JTextField imagPartText;

  /**
   * Text field for entering the zoom factor.
   */
  private final JTextField zoomText;

  /**
   * FractalExplorerGui that is parent of this form.
   */
  private final FractalExplorerGui explorer;

  /**
   * Defines the action commands associated with this GUI element.
   */
  private enum ActionCommands {
    DONE("Click this button to indicate that you're done.", "Done"),
    CANCEL("Click this button to cancel the zoom data input.", "Cancel"), ;

    /**
     * Tooltip for this command.
     */
    private String tooltip;

    /**
     * Short string for use in setting action commands.
     */
    private String command;

    /**
     * Constructor.
     * 
     * @param tip
     *          Tooltip for this command.
     * @param command
     *          Short string for use in setting action commands.
     */
    private ActionCommands(final String tip, final String command) {
      tooltip = tip;
      this.command = command;
    }
  }

  /**
   * Constructor. Creates the form and puts it up on the screen.
   * 
   * @param feGui
   *          FractalExplorerGui instance that is the parent of this form.
   * @param cpv
   *          Current zoom data.
   */
  protected CenterPointForm(final FractalExplorerGui feGui, final ComplexPlaneView cpv) {
    super("Centre point form");
    explorer = feGui;
    cpvData = (ComplexPlaneView) cpv.clone();

    /*
     * Define the main buttons
     */
    final JPanel buttonsPanel = new JPanel();
    final JButton doneButton = new JButton("Done");
    doneButton.addActionListener(this);
    doneButton.setActionCommand(ActionCommands.DONE.command);
    doneButton.setToolTipText(ActionCommands.DONE.tooltip);
    final JButton cancelButton = new JButton("Cancel");
    cancelButton.addActionListener(this);
    cancelButton.setActionCommand(ActionCommands.CANCEL.command);
    cancelButton.setToolTipText(ActionCommands.CANCEL.tooltip);
    buttonsPanel.add(cancelButton);
    buttonsPanel.add(doneButton);

    /*
     * Text fields area
     */
    final Box textFieldsBox = new Box(BoxLayout.Y_AXIS);
    textFieldsBox.setBorder(BorderFactory.createTitledBorder("Set zoom point and zoom factor"));

    final Box realPart = new Box(BoxLayout.X_AXIS);
    realPart.add(new JLabel("Re: "));
    realPartText = new JTextField(Double.toString(cpvData.getCentreReal()), 20);
    realPart.add(realPartText);

    final Box imaginaryPart = new Box(BoxLayout.X_AXIS);
    imaginaryPart.add(new JLabel("Im: "));
    imagPartText = new JTextField(Double.toString(cpvData.getCentreImaginary()), 20);
    imaginaryPart.add(imagPartText);

    final Box zoom = new Box(BoxLayout.X_AXIS);
    zoom.add(new JLabel("Zoom: "));
    zoomText = new JTextField(Double.toString(cpvData.getZoomFactor()), 20);
    zoom.add(zoomText);

    textFieldsBox.add(realPart);
    textFieldsBox.add(imaginaryPart);
    textFieldsBox.add(zoom);

    /*
     * Put it all together.
     */
    final Container container = getContentPane();
    container.setLayout(new GridBagLayout());
    final GridBagConstraints constraints = new GridBagConstraints();

    constraints.fill = GridBagConstraints.HORIZONTAL;
    constraints.gridy = 0;
    container.add(textFieldsBox, constraints);
    constraints.gridy = 1;
    container.add(buttonsPanel, constraints);

    pack();
    setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    setVisible(true);
  }

  @Override
  public void actionPerformed(final ActionEvent e) {
    final String command = e.getActionCommand();
    if (command.equals(ActionCommands.CANCEL.command)) {
      dispose();
    }
    if (command.equals(ActionCommands.DONE.command)) {
      if (readFields()) {
        explorer.setComplexPlaneView(cpvData);
        dispose();
      }
    }
  }

  /**
   * Read the edited text fields in the form.
   * 
   * @return True if the parsing of the data was successful.
   */
  private boolean readFields() {
    try {
      final double x = Double.parseDouble(realPartText.getText().trim());
      final double y = Double.parseDouble(imagPartText.getText().trim());
      final double z = Double.parseDouble(zoomText.getText().trim());
      cpvData.setCentre(x, y);
      cpvData.setZoomFactor(z);
      return true;
    } catch (final NumberFormatException nfe) {
      JOptionPane.showMessageDialog(this, "Number format exception occured. Check your input",
          "Zoom point input error.", JOptionPane.WARNING_MESSAGE);
      return false;
    }
  }
}
