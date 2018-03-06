
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONArray;
import org.json.JSONObject;



public class PJ {
    Promo code = new Promo();

    static String asdf;


    public static void main(String[] args) {

        String query = "https://www.papajohns.ru/api/stock/codes";

        HttpURLConnection connection = null;
        try {
            connection = (HttpURLConnection) new URL(query).openConnection();
            connection.setRequestMethod("GET");
            connection.setUseCaches(false);
            connection.setConnectTimeout(250);
            connection.setReadTimeout(0);

            connection.connect();

            StringBuilder sb = new StringBuilder();

            if (HttpURLConnection.HTTP_OK == connection.getResponseCode()) {
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String json;
                while ((json = in.readLine()) != null) {
                    sb.append(json);
                    sb.append("\n");

                }
                asdf = sb.toString();
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
            JSONObject objectInArray = jsonArray.getJSONObject(i);
            String name = objectInArray.getString("name");
            String code = objectInArray.getString("code");
            Pattern pattern = Pattern.compile("\\s–\\s");
            Matcher matcher = pattern.matcher(name);
            String asd = matcher.replaceAll(" - ");
            String[] subStr = asd.split("-", 5);
            Promo promo = new Promo();
            promo.setCode(code);
            if (subStr.length>1 &&subStr.length>4){
                promo.setName(subStr[1]);
                promo.setGift(subStr[2]);
                promo.setConditionString(subStr[3]);
                promo.setCitiesString(subStr[4]);
                Pattern pattern1 = Pattern.compile("-?\\d+");
                Matcher matcher1 = pattern1.matcher(promo.getConditionString());
                while (matcher1.find()){
                    String minSumm = matcher1.group();
                    double minSum = Double.parseDouble(minSumm);
                    promo.setMinSumm(minSum);
                    System.out.println("minSumm           "+String.format("%.0f",promo.getMinSumm())+ " рубасов");
                }


                System.out.println("Name:            " + promo.getName());
                System.out.println("Gift:            " + promo.getGift());
                System.out.println("ConditionString: " + promo.getConditionString());
            }
            for (int j = 0; j < subStr.length; j++) {

            }
            System.out.println("Code:             "+promo.getCode());


        }


    }
}


