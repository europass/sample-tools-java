package eu.europa.cedefop.europass.jtool;

   /*
		* Copyright European Union 2002-2010
		*
		*
		* Licensed under the EUPL, Version 1.1 or – as soon they 
		* will be approved by the European Commission - subsequent  
		* versions of the EUPL (the "Licence"); 
		* You may not use this work except in compliance with the 
		* Licence. 
		* You may obtain a copy of the Licence at: 
		*
		* http://ec.europa.eu/idabc/eupl.html
		*
		*  
		* Unless required by applicable law or agreed to in 
		* writing, software distributed under the Licence is 
		* distributed on an "AS IS" basis, 
		* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either 
		* express or implied. 
		* See the Licence for the specific language governing 
		* permissions and limitations under the Licence. 
		*
		*/

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.Border;

import eu.europa.cedefop.europass.jtool.util.SoftToolUtil;


/**
 * The XML viewer
 * @author Eworx S.A.
 * @version %I%, %G%
 * @since 1.0
 */
public class SoftToolFrame_XMLViewer extends JPanel {
    
	private static final long serialVersionUID = -4864153632285077512L;
	
	private JLabel labelTitle = new JLabel();     
    private JTextArea fileContent = new JTextArea("", 25, 50);
    private GridBagLayout layoutMain = new GridBagLayout();
    private Border border = BorderFactory.createEtchedBorder();    
    private File selFile;    

/**
     * Class constructor
     * @param selFile the file that will be shown
     */
    public SoftToolFrame_XMLViewer(File selFile) {    
        try {
            this.selFile=selFile;   
            jbInit();                    
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

/**
     * Initializing the XML viewer dialog box
     * @throws Exception
     */
    private void jbInit() throws Exception {
        Font fnt = new Font( "Arial", Font.BOLD, 12); 
        FileInputStream fis = null;
        BufferedReader bis = null;
        BufferedReader dis = null;
            
        this.setLayout( layoutMain );
        this.setBorder( border );
        this.setAutoscrolls(true);
        fileContent.setAutoscrolls(true);
        fileContent.setSelectedTextColor(Color.GREEN);
        fileContent.setFont(fnt);
        fileContent.setLineWrap(true);
        fileContent.setWrapStyleWord(true);
        fileContent.setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));
        
        labelTitle.setText( "File: "+this.selFile.getName() );
        String fileline = null;
        // Get the file content
         try {
               fis = new FileInputStream(this.selFile);
               if (this.selFile.getName().toUpperCase().endsWith(".PDF")) {
            	    dis = new BufferedReader(new InputStreamReader(SoftToolUtil.getXML(this.selFile)));
                   while ((fileline = dis.readLine()) != null) {
                      fileContent.append(fileline+ "\n"); 
                   }
               } else {
                   bis = new BufferedReader(new InputStreamReader(fis));          
                   while ((fileline = bis.readLine()) != null) {
                      fileContent.append(fileline+ "\n"); 
                   }
               }               
               fis.close();
               if (bis!= null) {
                   bis.close();
               }
               if (dis!= null) {
                   dis.close();
               }
         } catch (Exception e) {
             System.out.println("Error: " + "(init xml viewer)" + e.getMessage());
         }
        //                                
        JScrollPane scrollingResult = new JScrollPane(fileContent, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);        
        fileContent.setCaretPosition(0);                
        this.add( labelTitle, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 15, 0, 15), 0, 0) );        
        this.add( scrollingResult, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 15, 0, 15), 0, 0) );                                             
    }
}
