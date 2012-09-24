package org.easyGoingCrawler.fetcher;

import java.io.IOException;

import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.UsernamePasswordCredentials;
import org.apache.commons.httpclient.auth.AuthScope;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;

public class HttpProxy {

     /**
     * @param args
    */
     public static void main(String[] args) {
         HttpClient httpclient = new HttpClient();
        
        // ����HTTP����IP�Ͷ˿�
        httpclient.getHostConfiguration().setProxy("218.201.21.176", 80);
        // ������֤
      //  UsernamePasswordCredentials creds = new UsernamePasswordCredentials("root", "123456");
       // httpclient.getState().setProxyCredentials(AuthScope.ANY, creds);
        
        // get����
        GetMethod method = new GetMethod("http://www.baidu.com");
        method.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,
                new DefaultHttpMethodRetryHandler(3, false));
        try {
            int statusCode = httpclient.executeMethod(method);
            
            if (statusCode != HttpStatus.SC_OK) {
                System.out.println(statusCode + ": " + method.getStatusLine());
            } else {
                System.out.println(new String(method.getResponseBody(),"GBK"));
            }
        } catch (IOException e) {
             e.printStackTrace();
        } finally {
             method.releaseConnection();
        }

    }

}