package eu.europa.cedefop.europass.jtool.util;

/*
* 	Copyright European Union 2002-2010
*
*
* 	Licensed under the EUPL, Version 1.1 or – as soon they
* 	will be approved by the European Commission - subsequent
* 	versions of the EUPL (the "Licence");
* 	You may not use this work except in compliance with the
* 	Licence.
* 	You may obtain a copy of the Licence at:
*
* 	http://ec.europa.eu/idabc/eupl.html
*
*
* 	Unless required by applicable law or agreed to in
* 	writing, software distributed under the Licence is
* 	distributed on an "AS IS" basis,
* 	WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
* 	express or implied.
* 	See the Licence for the specific language governing
* 	permissions and limitations under the Licence.
*
*/

import org.hibernate.cfg.ImprovedNamingStrategy;
import org.hibernate.cfg.NamingStrategy;

/**
 * This class is a helpful class for hibernate configuration
 * @author Eworx S.A.
 * @version %I%, %G%
 * @since 1.0
 */
public class InstoreNamingStrategy extends ImprovedNamingStrategy {
  
	private static final long serialVersionUID = -6216101184434965174L;

	/**
	 * The singleton instance
	 */
	public static final NamingStrategy INSTANCE = new InstoreNamingStrategy();
  
  	/**
	 * Public constructor needed for SchemaExportTask
	 */
	public InstoreNamingStrategy() {}
}