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

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.border.Border;

import eu.europa.cedefop.europass.jtool.util.SoftToolUtil;

/**
 * The Configuration dialog box
 * @author Eworx S.A.
 * @version %I%, %G%
 * @since 1.0
 */
public class SoftToolFrame_Config extends JPanel {
    
	private static final long serialVersionUID = 4565909729817881559L;

	private JLabel labelTitle = new JLabel("Fill in the following properties:"); 
    
    private JLabel labelURL = new JLabel("URL: ");
    private JLabel labelDatabasename = new JLabel("Database name: ");
    private JLabel labelUsername = new JLabel("Username: ");
    private JLabel labelPassword = new JLabel("Password: ");
    
    private JTextField fldURL = new JTextField("", 25);
    private JTextField fldDatabasename = new JTextField("", 25);
    private JTextField fldUsername = new JTextField("", 25);
    private JTextField fldPassword = new JTextField("", 25);
    
    private JRadioButton sqlServer = new JRadioButton("SQL Server");
    private JRadioButton mySQL = new JRadioButton("MySQL");
    private JRadioButton oracle = new JRadioButton("Oracle");
    
    private GridBagLayout layoutMain = new GridBagLayout();
    private Border border = BorderFactory.createEtchedBorder();        

    /**
     * Class constructor
     */
    public SoftToolFrame_Config() {    
        try {            
            jbInit();                    
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Initializing the content of the configuration dialog box.
     * @throws Exception
     */
    private void jbInit() throws Exception {

        final Font fnt = new Font("Arial", Font.BOLD, 12);
        
        this.setLayout( layoutMain );
        this.setBorder( border );
        this.setAutoscrolls(true);
                        
        labelTitle.setFont(fnt);        
        
        labelURL.setFont(fnt);        
        labelDatabasename.setFont(fnt);        
        labelUsername.setFont(fnt);        
        labelPassword.setFont(fnt);                
        
        fldURL.setFont(fnt);        
        fldURL.setText(SoftToolUtil.getProperty("url"));
        fldDatabasename.setFont(fnt);        
        fldDatabasename.setText(SoftToolUtil.getProperty("databasename"));
        fldUsername.setFont(fnt);        
        fldUsername.setText(SoftToolUtil.getProperty("username"));
        fldPassword.setFont(fnt);        
        fldPassword.setText(SoftToolUtil.getProperty("password"));
        
        sqlServer.setFont(fnt);
        sqlServer.addActionListener(new ActionListener() { public void actionPerformed(ActionEvent ae) { sqlServer_ActionPerformed(ae); }});
        if ("1".equals(SoftToolUtil.getProperty("default_database"))) {
            sqlServer.setSelected(true);
        }
        else {
            sqlServer.setSelected(false);
        }

        mySQL.setFont(fnt);
        mySQL.addActionListener(new ActionListener() { public void actionPerformed(ActionEvent ae) { mySQL_ActionPerformed(ae); }});
        if ("2".equals(SoftToolUtil.getProperty("default_database"))) {
            mySQL.setSelected(true);
        }
        else {
            mySQL.setSelected(false);
        }

        oracle.setFont(fnt);
        oracle.addActionListener(new ActionListener() { public void actionPerformed(ActionEvent ae) { oracle_ActionPerformed(ae); }});
        if ("3".equals(SoftToolUtil.getProperty("default_database"))) {
            oracle.setSelected(true);
        }
        else {
            oracle.setSelected(false);
        }
            
        this.add(labelTitle, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
                GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 15, 0, 15), 0, 0));
        
        this.add(labelURL, new GridBagConstraints(0, 20, 50, 15, 0.0, 0.0,
                GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 15, 0, 15), 0, 0));

        this.add(fldURL, new GridBagConstraints(50, 20, 50, 15, 0.0, 0.0,
                GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 15, 0, 15), 0, 0));

        this.add(labelDatabasename, new GridBagConstraints(0, 40, 50, 15, 0.0, 0.0,
                GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 15, 0, 15), 0, 0));

        this.add(fldDatabasename, new GridBagConstraints(50, 40, 50, 15, 0.0, 0.0,
                GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 15, 0, 15), 0, 0));

        this.add(labelUsername, new GridBagConstraints(0, 60, 50, 15, 0.0, 0.0,
                GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 15, 0, 15), 0, 0));

        this.add(fldUsername, new GridBagConstraints(50, 60, 50, 15, 0.0, 0.0,
                GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 15, 0, 15), 0, 0));

        this.add(labelPassword, new GridBagConstraints(0, 80, 50, 15, 0.0, 0.0,
                GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 15, 0, 15), 0, 0));

        this.add(fldPassword, new GridBagConstraints(50, 80, 50, 15, 0.0, 0.0,
                GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 15, 0, 15), 0, 0));

        this.add(sqlServer, new GridBagConstraints(0, 100, 30, 15, 0.0, 0.0,
                GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 15, 0, 15), 0, 0));

        this.add(mySQL, new GridBagConstraints(30, 100, 30, 15, 0.0, 0.0,
                GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 15, 0, 15), 0, 0));

        this.add(oracle, new GridBagConstraints(60, 100, 30, 15, 0.0, 0.0,
                GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 15, 0, 15), 0, 0));
    }
    
    /**
     * Returns the database url.
     * @return the database url
     */
    public String getFldURL() { return this.fldURL.getText(); }    
    
    /**
     * Returns the database name.
     * @return the database name
     */
    public String getFldDatabasename() { return this.fldDatabasename.getText(); }
    
    /**
     * Returns the database username.
     * @return the database username
     */
    public String getFldUsername() { return this.fldUsername.getText(); }
    
    /**
     * Returns the database password.
     * @return the database password
     */
    public String getFldPassword() { return this.fldPassword.getText(); }
    
    /**
     * Returns the sql server status.
     * @return the sql server status
     */
    public boolean getSqlServer() { return this.sqlServer.isSelected(); }
    
    /**
     * Returns the mySQL status.
     * @return the mySQL status
     */
    public boolean getMySQL() { return this.mySQL.isSelected(); }
    
    /**
     * Returns the Oracle status.
     * @return the Oracle status
     */
    public boolean getOracle() { return this.oracle.isSelected(); }
    
    /**
     * Fires when the user select the sql server status
     * @param e the action event
     */
    void sqlServer_ActionPerformed(ActionEvent e) {
        if (this.sqlServer.isSelected()==true) {           
            this.mySQL.setSelected(false);        
            this.oracle.setSelected(false);
        } else {
            this.sqlServer.setSelected(true);  
        }
    }
    
    /**
     * Fires when the user select the mySQL status
     * @param e the action event
     */
    void mySQL_ActionPerformed(ActionEvent e) {
        if (this.mySQL.isSelected() == true) {
            this.sqlServer.setSelected(false);
            this.oracle.setSelected(false);
        } else {
            this.mySQL.setSelected(true);  
        }
    }
    
    /**
     * Fires when the user select the Oracle status
     * @param e the action event
     */
    void oracle_ActionPerformed(ActionEvent e) {
        if (this.oracle.isSelected() == true) {
            this.mySQL.setSelected(false);
            this.sqlServer.setSelected(false);
        } else {
            this.oracle.setSelected(true);  
        }
    }
    
}
