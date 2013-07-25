package eu.europa.cedefop.europass.jtool.util;

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

import com.lowagie.text.pdf.PRStream;
import com.lowagie.text.pdf.PdfArray;
import com.lowagie.text.pdf.PdfDictionary;
import com.lowagie.text.pdf.PdfName;
import com.lowagie.text.pdf.PdfNameTree;
import com.lowagie.text.pdf.PdfObject;
import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.PdfString;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import java.util.HashMap;
import java.util.Iterator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 * This class extract the attachment files from a PDF file.
 * @author Gomosidis Apostolos, Quality & Reliability S.A.
 * @version %I%, %G%
 * @since 1.0
 */
public class ExtractAttachments {
  private static final Log log = LogFactory.getLog(ExtractAttachments.class);
	private InputStream in;
	private File trgfile = null;        

    /**
         * Class constructor
         * @param in the InputStream of the PDF file
         * @param trgfile the extract (XML) file path
         * @throws IOException
         */
	public ExtractAttachments(InputStream in, File trgfile) throws IOException {
		this.in = in;
		this.trgfile = trgfile;
	}
	
	/**
     * Extract the attachment file
     * @throws Exception
     */
	public void execute() throws Exception{
	  boolean hasAttachment = false;
		try {
			PdfReader reader = new PdfReader(in);
			PdfDictionary catalog = reader.getCatalog();
			PdfDictionary names = (PdfDictionary) PdfReader
					.getPdfObject(catalog.get(PdfName.NAMES));
			if (names != null) {
				PdfDictionary embFiles = (PdfDictionary) PdfReader
						.getPdfObject(names.get(new PdfName("EmbeddedFiles")));
				if (embFiles != null) {
					HashMap embMap = PdfNameTree.readTree(embFiles);
					for (Iterator i = embMap.values().iterator(); i.hasNext();) {
						PdfDictionary filespec = (PdfDictionary) PdfReader
								.getPdfObject((PdfObject) i.next());
						unpackFile(filespec);
					}
				}
			}
			for (int k = 1; k <= reader.getNumberOfPages(); ++k) {
				PdfArray annots = (PdfArray) PdfReader.getPdfObject(reader
						.getPageN(k).get(PdfName.ANNOTS));
				if (annots == null)
					continue;
				for (Iterator i = annots.listIterator(); i.hasNext();) {
					PdfDictionary annot = (PdfDictionary) PdfReader
							.getPdfObject((PdfObject) i.next());
					PdfName subType = (PdfName) PdfReader.getPdfObject(annot
							.get(PdfName.SUBTYPE));
					if (!PdfName.FILEATTACHMENT.equals(subType))
						continue;
					PdfDictionary filespec = (PdfDictionary) PdfReader
							.getPdfObject(annot.get(PdfName.FS));
				  hasAttachment = true;
                                  unpackFile(filespec);
				}
			}
		} catch (Exception e) {
			log.error("Error while extracting PDF attachements: "+e);
		}
	  if (!hasAttachment)
	    throw new Exception("PDF file does not have attachment.");
	}
        
        /**
     * Get the attachment file     
     * @param filespec 
     * @throws IOException
     */
	public void unpackFile(PdfDictionary filespec) throws IOException {
		if (filespec == null) return;
		
		PdfName type = (PdfName) PdfReader.getPdfObject(filespec.get(PdfName.TYPE));
		if (!PdfName.F.equals(type) && !PdfName.FILESPEC.equals(type)) return;
		
		PdfDictionary ef = (PdfDictionary) PdfReader.getPdfObject(filespec.get(PdfName.EF));
		if (ef == null)	return;
		
		PdfString fn = (PdfString) PdfReader.getPdfObject(filespec.get(PdfName.F));
		if (fn == null)	return;
		
		PRStream prs = (PRStream) PdfReader.getPdfObject(ef.get(PdfName.F));
		if (prs == null) return;
		byte b[] = PdfReader.getStreamBytes(prs);
		FileOutputStream fout = new FileOutputStream(trgfile);
		fout.write(b);
		fout.close();
	}

}