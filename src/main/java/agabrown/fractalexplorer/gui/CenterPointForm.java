package agabrown.fractalexplorer.gui;

import agabrown.fractalexplorer.dm.ComplexPlaneView;

import javax.swing.*;
import java.awt.Container;
import java.awt.Dialog;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Provides the simple form that is used to enter the point that the Fractal
 * explorer should be centred on.
 *
 * @author agabrown Jul 2012 - Sep 2021
 */
public final class CenterPointForm implements ActionListener {

    /**
     * Holds the complex plane view of the Fractal set.
     */
    private ComplexPlaneView cpvData;

    /**
     * Holds the object that represents the dialog requesting user input.
     */
    private JDialog dialog;

    /**
     * Text field for entering the real part of the centre point.
     */
    private JTextField realPartText;

    /**
     * Text field for entering the imaginary part of the centre point.
     */
    private JTextField imagPartText;

    /**
     * Text field for entering the zoom factor.
     */
    private JTextField zoomText;

    /**
     * Use the input complex plane view to initialize the dialog and show the
     * dialog. After the dialog is closed the new complex plane view data is
     * returned.
     *
     * @param cpv The input complex plane view.
     * @return The new complex plane view.
     */
    ComplexPlaneView showFormAndGetCpvData(final ComplexPlaneView cpv) {
        cpvData = (ComplexPlaneView) cpv.clone();
        dialog = createDialog();
        /*
         * Set dialog visible on the same thread as the main application to make
         * sure it blocks other actions until use input has been received.
         */
        dialog.setVisible(true);
        return (ComplexPlaneView) cpvData.clone();
    }

    /**
     * Create a JDialog instance for requesting user input on the complex plane
     * view.
     *
     * @return Fresh JDialog instance.
     */
    private JDialog createDialog() {
        final JDialog dialog = new JDialog();
        dialog.setTitle("Set centre and zoom factor");

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
        final Container container = dialog.getContentPane();
        container.setLayout(new GridBagLayout());
        final GridBagConstraints constraints = new GridBagConstraints();

        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.gridy = 0;
        container.add(textFieldsBox, constraints);
        constraints.gridy = 1;
        container.add(buttonsPanel, constraints);

        dialog.pack();
        dialog.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        return dialog;
    }

    @Override
    public void actionPerformed(final ActionEvent e) {
        final String command = e.getActionCommand();
        if (command.equals(ActionCommands.CANCEL.command)) {
            dialog.dispose();
        }
        if (command.equals(ActionCommands.DONE.command)) {
            if (readFields()) {
                dialog.dispose();
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
            JOptionPane.showMessageDialog(dialog, "Number format exception occured. Check your input",
                    "Zoom point input error.", JOptionPane.WARNING_MESSAGE);
            return false;
        }
    }

    /**
     * Defines the action commands associated with this GUI element.
     */
    private enum ActionCommands {
        DONE("Click this button to indicate that you're done.", "Done"),
        CANCEL("Click this button to cancel the zoom data input.", "Cancel"),
        ;

        /**
         * Tooltip for this command.
         */
        private final String tooltip;

        /**
         * Short string for use in setting action commands.
         */
        private final String command;

        /**
         * Constructor.
         *
         * @param tip     Tooltip for this command.
         * @param command Short string for use in setting action commands.
         */
        ActionCommands(final String tip, final String command) {
            tooltip = tip;
            this.command = command;
        }
    }
}
