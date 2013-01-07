package org.easyGoingCrawler.fetcher;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class HtmlUnitTest {

    private String webUrl;

    private WebClient webClient;

    @Before
    public void setUp() {
        webUrl = System.getProperty("integration.base.url");
        webClient = new WebClient();
    }

    @After
    public void tearDown() {
        webClient.closeAllWindows();
    }

    /**
     * Test /index.html
     */
    @Test
    public void testIndexHtml() throws Exception {
        System.out.println("Connecting to: " + webUrl);
        webUrl = "http://www.cnblogs.com/58top/archive/2012/12/28/how-to-generate-unique-promotion-discount-codes-in-php.html";
        HtmlPage page = webClient.getPage(webUrl);
        System.out.println(page.asXml());
    }
}