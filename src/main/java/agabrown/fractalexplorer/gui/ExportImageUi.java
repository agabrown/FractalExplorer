// ExportImageUi.java

package agabrown.fractalexplorer.gui;

import agabrown.fractalexplorer.util.FEConstants;
import agabrown.fractalexplorer.util.ImageExportFormat;

import javax.swing.*;
import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.Objects;

/**
 * Export the plot in the FractalExplorerGui to a file in a user-chosen format
 * (such as postscript, jpeg, pdf, svg etc.). Fire up a GUI to take user input.
 *
 * <pre>
 * Code cribbed from agabplot project.
 * </pre>
 *
 * @author Anthony Brown Jul 2012 - Sep 2021
 */
public class ExportImageUi extends JFrame implements ActionListener {

    /**
     * required for any serializable class
     */
    public static final long serialVersionUID = 323157L;
    /**
     * Text field in GUI containing filename
     */
    private final JTextField whichFile;
    /**
     * Contains command string for combo box with file type list
     */
    private final String comboBoxActionCommand;
    /**
     * Reference to the JComboBox used for selecting export file formats.
     */
    private final JComboBox<ImageExportFormat> fileFormatBox;
    /**
     * Holds buffered image to be exported.
     */
    private final BufferedImage bimg;
    /**
     * Directory in which the file of the exported plot is to be stored
     */
    private String targetDir;
    /**
     * stores the ImageExportFormat enum to be used for the exported image.
     */
    private ImageExportFormat theFileType;

    /**
     * Create the GUI and show it to the user.
     *
     * @param image The image to be exported.
     */
    protected ExportImageUi(final BufferedImage image) {

        super("Export image to file");

        bimg = image;

        final JPanel buttonsPanel = createButtonsPanel();

        targetDir = System.getProperty("user.dir");
        final String initFileName = targetDir + "/" + FEConstants.PROJECT_NAME + "-image."
                + ImageExportFormat.PNG.extension();

        final Box fileSelect = new Box(BoxLayout.Y_AXIS);
        fileSelect.setBorder(BorderFactory.createTitledBorder("Image file name"));
        whichFile = new JTextField(initFileName, 40);
        fileSelect.add(whichFile);

        fileFormatBox = new JComboBox<>(ImageExportFormat.values());
        fileFormatBox.setSelectedItem(ImageExportFormat.PNG);
        theFileType = (ImageExportFormat) fileFormatBox.getSelectedItem();
        fileFormatBox.addActionListener(this);
        fileFormatBox.setToolTipText("Select the file format here");

        comboBoxActionCommand = fileFormatBox.getActionCommand();

        final Container container = getContentPane();
        container.setLayout(new GridBagLayout());
        final GridBagConstraints constraints = new GridBagConstraints();

        constraints.fill = GridBagConstraints.NONE;
        constraints.gridy = 0;
        container.add(fileSelect, constraints);
        constraints.gridy = 1;
        container.add(fileFormatBox, constraints);
        constraints.gridy = 2;
        container.add(buttonsPanel, constraints);

        pack();
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);
        toFront();

    }

    /**
     * Create the JPanel with the clickable buttons for the image export GUI.
     *
     * @return A JPanel instance with the buttons defined.
     */
    protected JPanel createButtonsPanel() {
        final JPanel buttonsPanel = new JPanel();
        final JButton saveButton = new JButton(ActionCommands.SAVE.command);
        saveButton.addActionListener(this);
        saveButton.setActionCommand(ActionCommands.SAVE.command);
        saveButton.setToolTipText(ActionCommands.SAVE.tooltip);
        final JButton cancelButton = new JButton(ActionCommands.CANCEL.command);
        cancelButton.addActionListener(this);
        cancelButton.setActionCommand(ActionCommands.CANCEL.command);
        cancelButton.setToolTipText(ActionCommands.CANCEL.tooltip);
        final JButton browseButton = new JButton(ActionCommands.BROWSE.command);
        browseButton.addActionListener(this);
        browseButton.setActionCommand(ActionCommands.BROWSE.command);
        browseButton.setToolTipText(ActionCommands.BROWSE.tooltip);
        buttonsPanel.add(saveButton);
        buttonsPanel.add(cancelButton);
        buttonsPanel.add(browseButton);
        return buttonsPanel;
    }

    /**
     * Handle received ActionEvents
     *
     * @param actionEvent ActionEvent object
     */
    @Override
    public void actionPerformed(final ActionEvent actionEvent) {

        final String command = actionEvent.getActionCommand();

        if (command.equals(ActionCommands.CANCEL.command)) {
            dispose();
        }

        String outFileName;
        if (command.equals(ActionCommands.BROWSE.command)) {
            final JFileChooser chooser = new JFileChooser(targetDir);
            chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
                targetDir = chooser.getSelectedFile().getPath();
                outFileName = targetDir + "/" + FEConstants.PROJECT_NAME + "-image." + theFileType.extension();
                whichFile.setText(outFileName);
            }
        }

        if (command.equals(comboBoxActionCommand)) {
            theFileType = (ImageExportFormat) fileFormatBox.getSelectedItem();
            outFileName = targetDir + "/" + FEConstants.PROJECT_NAME + "-image." + Objects.requireNonNull(theFileType).extension();
            whichFile.setText(outFileName);
        }

        if (command.equals(ActionCommands.SAVE.command)) {
            outFileName = whichFile.getText();
            try {
                theFileType.export(bimg, outFileName);
            } catch (final Exception e) {
                e.printStackTrace();
            }
            dispose();
        }

    }

    /**
     * Defines the action commands associated with this GUI element.
     */
    private enum ActionCommands {
        SAVE("Click this button to save the image to file.", "Save"),
        CANCEL("Click this button to cancel the image export.", "Cancel"),
        BROWSE("Click this button to browse the directory tree for a different destination folder", "Browse"),
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
