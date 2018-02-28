
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONArray;
import org.json.JSONObject;



public class PJ {
    static  String asdf;


    public static void main(String[] args) {

        String query = "https://www.papajohns.ru/api/stock/codes";

        HttpURLConnection connection = null;
        try {
            connection = (HttpURLConnection) new URL(query).openConnection();
            connection.setRequestMethod("GET");
            connection.setUseCaches(false);
            connection.setConnectTimeout(250);
            connection.setReadTimeout(250);

            connection.connect();

            StringBuilder sb = new StringBuilder();

            if (HttpURLConnection.HTTP_OK == connection.getResponseCode()) {
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String json;
                while ((json = in.readLine()) != null) {
                    sb.append(json);
                    sb.append("\n");

                }
                System.out.println(sb.toString());
                asdf = sb.toString();
                System.out.println(asdf);
            } else {
                System.out.println("fail" + connection.getResponseCode() + "," + connection.getResponseMessage());
            }

        } catch (Throwable cause) {
            cause.printStackTrace();
        } finally {
            if (connection != null)
                connection.disconnect();
        }
        JSONObject outerObject = new JSONObject(asdf);
        JSONArray jsonArray = outerObject.getJSONArray("codes");
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject   objectInArray = jsonArray.getJSONObject(i);
            String name = objectInArray.getString("name");
            String code = objectInArray.getString("code");

            System.out.println(name);
            System.out.println(code);

        }
    }

}






