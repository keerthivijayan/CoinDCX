import com.jayway.restassured.response.ExtractableResponse;
import com.jayway.restassured.response.Response;
import org.apache.log4j.Logger;

import java.util.Map;

import static com.jayway.restassured.RestAssured.given;

public class ApiRequest {

    static Logger logger;

    /**
     *Post Api to make request  with the body params
     * @param apiBaseUrl - Application's base api url
     * @param apiEndPoint - cloud function endpoint
     * @param headerValue - Map formatted list of header values
     * @param bodyValue - Map formatted list of body values
     * @return response
     */
    public static ExtractableResponse<Response> POST(String apiBaseUrl, String apiEndPoint, Map<String, Object> headerValue, Map<String, String> bodyValue) {
        if(apiBaseUrl.isEmpty() || apiEndPoint.isEmpty() || headerValue.isEmpty() || bodyValue.isEmpty())
            return null;

        try {
            return given().headers(headerValue)
                    .body(bodyValue)
                    .when()
                    .post(apiBaseUrl + apiEndPoint)
                    .then().log().all().extract();
        }

        catch(Exception e) {
            logger.error("Exception occurred while performing post: " + e.getMessage());
            return null;
        }
    }

}
