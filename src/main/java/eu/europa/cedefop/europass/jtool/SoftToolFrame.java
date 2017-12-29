package eu.europa.cedefop.europass.jtool;

/*
*   Copyright European Union 2002-2010
*
*
*   Licensed under the EUPL, Version 1.1 or – as soon they
*   will be approved by the European Commission - subsequent
*   versions of the EUPL (the "Licence");
*   You may not use this work except in compliance with the
*   Licence.
*   You may obtain a copy of the Licence at:
*
*   http://ec.europa.eu/idabc/eupl.html
*
*
*   Unless required by applicable law or agreed to in
*   writing, software distributed under the Licence is
*   distributed on an "AS IS" basis,
*   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
*   express or implied.
*   See the Licence for the specific language governing
*   permissions and limitations under the Licence.
*
*/

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JToolBar;

import eu.europa.cedefop.europass.jtool.util.SoftToolUtil;

/**
 * This is the main frame of the application
 * @author Eworx S.A.
 * @version %I%, %G%
 * @since 1.0
 */
public class SoftToolFrame extends JFrame {
    
	private static final long serialVersionUID = 5319049348594589357L;
	
	private BorderLayout layoutMain = new BorderLayout();
    private JPanel panelCenter = new JPanel();
    private JMenuBar menuBar = new JMenuBar();
    private JMenu menuFile = new JMenu();
    private JMenuItem menuFileOpenPDFXML = new JMenuItem();
    private JMenuItem menuFileOpenXML = new JMenuItem();
    private JMenuItem menuFileConfiguration = new JMenuItem();
    private JMenuItem menuFileExit = new JMenuItem();
    private JMenu menuHelp = new JMenu();
    private JMenuItem menuHelpAbout = new JMenuItem();
    private JLabel statusBar = new JLabel();
    private JLabel logoIcon = new JLabel();
    private JToolBar toolBar = new JToolBar();
    private JButton buttonOpenPDFXML = new JButton();
    private JButton buttonOpenXML = new JButton();
    private JButton buttonConfiguration = new JButton();
    private JButton buttonHelp = new JButton();    
    private ImageIcon imageOpenPDFXML = new ImageIcon(SoftToolFrame.class.getClassLoader().getResource("img/pdf.gif"));    
    private ImageIcon imageOpenXML = new ImageIcon(SoftToolFrame.class.getClassLoader().getResource("img/xml.gif"));    
    private ImageIcon imageConfig = new ImageIcon(SoftToolFrame.class.getClassLoader().getResource("img/config.gif"));
    private ImageIcon imageLogo = new ImageIcon(SoftToolFrame.class.getClassLoader().getResource("img/cv_top_banner1.gif"));
    private ImageIcon imageHelp = new ImageIcon(SoftToolFrame.class.getClassLoader().getResource("img/help.gif"));
    private ImageIcon imageIconLogo = new ImageIcon(SoftToolFrame.class.getClassLoader().getResource("img/europass_logo.gif"));

    /**
    * Class constructor
    */
    public SoftToolFrame() {
        try {
            jbInit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
    * Initialize the frame
    * @throws Exception
    */
    private void jbInit() throws Exception {
        this.setJMenuBar(menuBar);
        this.getContentPane().setBackground(Color.WHITE);
        this.getContentPane().setLayout( layoutMain );
        panelCenter.setLayout(null);
        this.setSize(new Dimension(400, 300));
        this.setTitle("Europass Java Standalone Application");
        this.setIconImage(imageIconLogo.getImage());

        menuFile.setText("File");
        menuFileOpenPDFXML.setText("Open PDF+XML file");
        menuFileOpenPDFXML.addActionListener(new ActionListener() { public void actionPerformed(ActionEvent ae) { fileOpenPDFXML_ActionPerformed(ae); }});
        menuFileOpenXML.setText("Open XML file");
        menuFileOpenXML.addActionListener(new ActionListener() { public void actionPerformed(ActionEvent ae) { fileOpenXML_ActionPerformed(ae); }});
        menuFileConfiguration.setText("Configuration");
        menuFileConfiguration.addActionListener(new ActionListener() { public void actionPerformed(ActionEvent ae) { fileConfiguration_ActionPerformed(ae); }});
        menuFileExit.setText("Exit");
        menuFileExit.addActionListener(new ActionListener() { public void actionPerformed(ActionEvent ae) { fileExit_ActionPerformed(ae); }});
        menuHelp.setText("Help");
        menuHelpAbout.setText("About");
        menuHelpAbout.addActionListener(new ActionListener() { public void actionPerformed(ActionEvent ae) { helpAbout_ActionPerformed(ae); }});
        statusBar.setText("Welcome");

        buttonOpenPDFXML.setToolTipText("Open PDF+XML file");
        buttonOpenPDFXML.addActionListener(new ActionListener() { public void actionPerformed(ActionEvent ae) { fileOpenPDFXML_ActionPerformed(ae); }});
        buttonOpenPDFXML.setIcon( imageOpenPDFXML );
        buttonOpenXML.setToolTipText("Open XML file");
        buttonOpenXML.addActionListener(new ActionListener() { public void actionPerformed(ActionEvent ae) { fileOpenXML_ActionPerformed(ae); }});
        buttonOpenXML.setIcon( imageOpenXML );
        buttonConfiguration.setToolTipText("Configuration");
        buttonConfiguration.addActionListener(new ActionListener() { public void actionPerformed(ActionEvent ae) { fileConfiguration_ActionPerformed(ae); }});
        buttonConfiguration.setIcon( imageConfig );
        buttonHelp.setToolTipText("About");
        buttonHelp.addActionListener(new ActionListener() { public void actionPerformed(ActionEvent ae) { helpAbout_ActionPerformed(ae); }});
        buttonHelp.setIcon(imageHelp);

        logoIcon.setIcon(imageLogo);
        menuFile.add(menuFileOpenPDFXML);
        menuFile.add(menuFileOpenXML);
        menuFile.add(menuFileConfiguration);
        menuFile.add(menuFileExit);
        menuBar.add(menuFile);
        menuHelp.add(menuHelpAbout);
        menuBar.add(menuHelp);

        this.getContentPane().add( statusBar, BorderLayout.SOUTH );
        toolBar.add(buttonOpenPDFXML);
        toolBar.add(buttonOpenXML);
        toolBar.add(buttonConfiguration);
        toolBar.add(buttonHelp);

        this.getContentPane().add(toolBar, BorderLayout.NORTH);
        this.getContentPane().add(logoIcon, BorderLayout.CENTER);
    }

    /**
    * Fires when the user exit the application.
    * @param e the action event
    */
    void fileExit_ActionPerformed(ActionEvent e) {
        System.exit(0);
    }

    /**
    * Fires when the user hit the help about button or help about menu option.
    * @param e the action event
    */
    void helpAbout_ActionPerformed(ActionEvent e) {
        JOptionPane.showMessageDialog(this, new SoftToolFrame_AboutBoxPanel1(), "About", JOptionPane.PLAIN_MESSAGE);        
    }
    
    /**
    * Fires when the user hit the PDF button or PDF menu option.
    * @param e the action event
    */
    void fileOpenPDFXML_ActionPerformed(ActionEvent e) {

        final String filename = File.separator + "tmp";
        String logs = null;
        final JFileChooser fc = new JFileChooser(new File(filename));
        fc.showOpenDialog(this);
        final File selFile = fc.getSelectedFile();

        if (selFile==null || !selFile.getName().toUpperCase().endsWith(".PDF")) {
            if (selFile != null) JOptionPane.showMessageDialog(this, "Wrong file type (must be .pdf)");
        } else {            
            int option = JOptionPane.showOptionDialog(this, new SoftToolFrame_XMLViewer(selFile),
                                                 "XML Viewer", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE,
                                                 this.imageOpenXML, new String[]{"Database Store", "CANCEL"}, null);
            switch(option) {
                case 0: 
                        this.statusBar.setForeground(Color.BLACK);
                        this.statusBar.setText("Saving data...");

                        logs = SoftToolUtil.savePDFXML(selFile, SoftToolUtil.getProperty("temp_path_folder"));

                        if (logs != null && logs.startsWith("Error:")) {
                            this.statusBar.setForeground(Color.RED);
                        } else {
                            this.statusBar.setForeground(Color.BLUE);
                        }
                        this.statusBar.setText( logs );
                        break;
                default: 
                        break;                        
            }            
        }
    }
    
    /**
    * Fires when the user hit the XML button or XML menu option.
    * @param e the action event
    */
    void fileOpenXML_ActionPerformed(ActionEvent e) {

        final String filename = File.separator + "tmp";
        String logs = null;
        final JFileChooser fc = new JFileChooser(new File(filename));
        fc.showOpenDialog(this);
        final File selFile = fc.getSelectedFile();

        if (selFile==null || !selFile.getName().toUpperCase().endsWith(".XML")) {
            if (selFile != null) JOptionPane.showMessageDialog(this, "Wrong file type (must be .xml)");
        } else {
            final int option = JOptionPane.showOptionDialog(this, new SoftToolFrame_XMLViewer(selFile),
                                                "XML Viewer", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE,
                                                this.imageOpenXML, new String[]{"Database Store", "CANCEL"}, null);
            switch(option) {
                case 0:
                    this.statusBar.setForeground(Color.BLACK);
                    this.statusBar.setText("Saving data...");

                    logs = SoftToolUtil.saveFile(selFile);

                    if (logs != null && logs.startsWith("Error:")) {
                    this.statusBar.setForeground(Color.RED);
                    } else {
                    this.statusBar.setForeground(Color.BLUE);
                    }
                    this.statusBar.setText( logs );
                    break;
                default:
                    break;
            }
        }
    }
    
    /**
    * Fires when the user hit the Configuration button or Configuration menu option.
    * @param e the action event
    */
    void fileConfiguration_ActionPerformed(ActionEvent e) {

        final SoftToolFrame_Config confPanel = new SoftToolFrame_Config();
        String logs = null;
        final int option = JOptionPane.showOptionDialog(this, confPanel,
                                                    "Configuration", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE,
                                                    this.imageConfig, new String[]{"Save", "CANCEL"}, null);
        switch(option) {
            case 0: // SAVE INTO SOFTTOOL.PROPERTIES
                this.statusBar.setText("Saving data...");
                logs = SoftToolUtil.saveConfig(confPanel.getFldDatabasename(), confPanel.getFldPassword(), confPanel.getFldURL(),
                                                confPanel.getFldUsername(), confPanel.getSqlServer(), confPanel.getMySQL(), confPanel.getOracle());
                this.statusBar.setText(logs);
                break;
            default:
                break;
        }
    }
}
