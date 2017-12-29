package eu.europa.cedefop.europass.jtool.util;

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
            System.out.println(e);
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
