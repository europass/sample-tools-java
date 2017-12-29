package eu.europa.cedefop.europass.jtool.util;

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

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;

public class WebServiceExtractXML {

    private static final String EXTRACT_ENDPOINT_URL = "https://europass.cedefop.europa.eu/rest/v1/document/extraction";

    private String inputFile;
    private String outputFile;

    public WebServiceExtractXML(final String inputFile, final String outputFile) {
        this.inputFile = inputFile;
        this.outputFile = outputFile;
    }

    public void execute() {

        try {
            final InputStream is = new FileInputStream(new File(this.inputFile));
            final HttpClient client = HttpClientBuilder.create().setSSLSocketFactory(createSSLConnectionFactory()).build();

            final HttpPost postRequest = new HttpPost(EXTRACT_ENDPOINT_URL);
            final HttpEntity entity = new ByteArrayEntity(IOUtils.toByteArray(is));

            postRequest.setEntity(entity);
            postRequest.addHeader("Content-Type", "application/pdf");

            final HttpResponse response = client.execute(postRequest);
            response.getEntity().writeTo(new FileOutputStream(this.outputFile));
        }
        catch (final Exception e) {
            System.out.println("Error during executing REST request : " + e.getMessage());
        }
    }

    private SSLConnectionSocketFactory createSSLConnectionFactory() throws KeyManagementException, NoSuchAlgorithmException {
        final SSLContext sslContext = SSLContext.getInstance("SSL");
        sslContext.init(null,
                new TrustManager[]{ new X509TrustManager() {
                    public X509Certificate[] getAcceptedIssuers() { return null; }
                    public void checkClientTrusted(X509Certificate[] certs, String authType) {}
                    public void checkServerTrusted(X509Certificate[] certs, String authType) {}
                }}, new SecureRandom());

        return new SSLConnectionSocketFactory(sslContext);
    }
}
