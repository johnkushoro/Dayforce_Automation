//package Utils;
//
//import org.apache.http.HttpResponse;
//import org.apache.http.client.methods.HttpPost;
//import org.apache.http.impl.client.CloseableHttpClient;
//import org.apache.http.impl.client.HttpClients;
//import org.apache.http.util.EntityUtils;
//import org.apache.http.entity.StringEntity;
//import org.json.JSONObject;
//
//import java.io.IOException;
//
//public class ApiLogin {
//
//    public static String login(String url, String companyName, String userName, String password) {
//        String loginUrl = url + "/api/login";  // Ensure this is the correct endpoint
//
//        try (CloseableHttpClient client = HttpClients.createDefault()) {
//            HttpPost post = new HttpPost(loginUrl);
//            post.setHeader("Content-Type", "application/json");
//            post.setEntity(new StringEntity(String.format(
//                    "{\"company\":\"%s\", \"username\":\"%s\", \"password\":\"%s\"}",
//                    companyName, userName, password
//            )));
//
//            HttpResponse response = client.execute(post);
//            int statusCode = response.getStatusLine().getStatusCode();
//
//            if (statusCode == 200) {  // Adjust for your API's success code
//                String responseBody = EntityUtils.toString(response.getEntity());
//                JSONObject jsonResponse = new JSONObject(responseBody);
//
//                // Assuming the token is returned in the response as {"token": "your_token"}
//                return jsonResponse.optString("token", "");  // Adjust the key as per your API response
//            } else {
//                throw new IOException("Failed to login: HTTP status " + statusCode);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            return null;  // Or handle this more appropriately
//        }
//    }
//}