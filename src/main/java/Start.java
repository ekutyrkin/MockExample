import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.ResponseDefinitionBuilder;
import com.github.tomakehurst.wiremock.http.Request;

import java.text.SimpleDateFormat;
import java.util.Date;

import static com.github.tomakehurst.wiremock.client.WireMock.any;
import static com.github.tomakehurst.wiremock.client.WireMock.anyUrl;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;


public class Start {

    private static WireMockServer wireMockServer = new WireMockServer(wireMockConfig().port(80));
    //    private static WireMockServer wireMockServer = new WireMockServer(wireMockConfig().httpsPort(443));

    public static void main(String[] args) {
        startWireMock();
    }

    private static void startWireMock() {
        wireMockServer.start();
        wireMockServer.addMockServiceRequestListener((request, response) -> saveRequests(request));
        ResponseDefinitionBuilder responseDefinitionBuilder = new ResponseDefinitionBuilder().withBody("ok").withStatus(200);
        wireMockServer.stubFor(any(anyUrl()).willReturn(responseDefinitionBuilder));
        System.out.println("Mock started");
    }

    private static void saveRequests(Request request) {
        String date = new SimpleDateFormat("dd.MM.YY HH:mm:ss.S").format(new Date());
        System.out.println(date + "\n " + request.getAbsoluteUrl() +
                "\n[BODY]: \n " + request.getBodyAsString() +
                "\n[HEADER]: \n " + request.getHeaders() +
                "---------------------------------------------"
        );
    }
}
