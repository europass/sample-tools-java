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

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;

/**
 * The Help About dialog box
 * @author Eworx S.A.
 * @version %I%, %G%
 * @since 1.0
 */
public class SoftToolFrame_AboutBoxPanel1 extends JPanel {
    
	private static final long serialVersionUID = 2679528720330909032L;
	
	private JLabel labelTitle = new JLabel();
    private JLabel labelAuthor = new JLabel();
    private JLabel labelDeveloper = new JLabel();
    private JLabel labelCopyright = new JLabel();
    private JLabel labelCompany = new JLabel();
    private JLabel labelVersion = new JLabel();
    private GridBagLayout layoutMain = new GridBagLayout();
    private Border border = BorderFactory.createEtchedBorder();

/**
 * Class constructor
 */
    public SoftToolFrame_AboutBoxPanel1() {
        try {
            jbInit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

/**
     * Initializing the data for the Help About Box
     * @throws Exception
     */
    private void jbInit() throws Exception {

        this.setLayout( layoutMain );
        this.setBorder( border );
        labelTitle.setText("Europass Java Standalone Application");
        labelAuthor.setText("Author: Eworx S.A.");
        labelDeveloper.setText("Developed by: Eworx S.A.");
        labelCopyright.setText("Copyright 2017");
        labelCompany.setText("Company: Eworx S.A.");
        labelVersion.setText("Version: 1.0");

        this.add( labelTitle, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
                GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 15, 0, 15), 0, 0) );

        this.add( labelAuthor, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
                GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 15, 0, 15), 0, 0) );

        this.add( labelCompany, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0,
                GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 15, 5, 15), 0, 0) );

        this.add( labelDeveloper, new GridBagConstraints(0, 3, 1, 1, 0.0, 0.0,
                GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 15, 0, 15), 0, 0) );

        this.add( labelVersion, new GridBagConstraints(0, 4, 1, 1, 0.0, 0.0,
                GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 15, 0, 15), 0, 0) );

        this.add( labelCopyright, new GridBagConstraints(0, 5, 1, 1, 0.0, 0.0,
                GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 15, 0, 15), 0, 0) );
    }
}
