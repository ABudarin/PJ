
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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

        ArrayList<Promo> Codes = new ArrayList<Promo>();
        ArrayList<Promo> Moscow = new ArrayList<Promo>();


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

            if (subStr.length>4){
                promo.setName(subStr[1]);
                promo.setGift(subStr[2]);
                promo.setConditionString(subStr[3]);
                String text =subStr[4].toUpperCase();
                promo.setCitiesString(text);
                // boolean asdv  = text.contains("МОСКВА");
                Pattern pattern1 = Pattern.compile("-?\\d+");
                Matcher matcher1 = pattern1.matcher(promo.getConditionString());
                while (matcher1.find()){
                    String minSumm = matcher1.group();
                    double minSum = Double.parseDouble(minSumm);
                    promo.setMinSumm(minSum);
                }
                if (text.contains("МОСКВА")==true ||text.contains("МО")==true||text.contains("ВСЕ ГОРОДА")==true)
                    Moscow.add(promo);


                Codes.add(promo);

            }

        }


      /*  for (int i = 0; i < Codes.size(); i++)
        {

            Promo currentCode = Codes.get(i);
            System.out.println("Name:            "+currentCode.getName());
            System.out.println("Condition:       "+currentCode.getConditionString());
            System.out.println("Gift:            "+currentCode.getGift());
            System.out.println("minSum:           "+String.format("%.0f",currentCode.getMinSumm()));
            System.out.println("Cities:          "+currentCode.getCitiesString());
            System.out.println("Code:             "+currentCode.getCode());

       }*/
        for (int i = 0; i < Moscow.size(); i++)
        {

            Promo currentCode = Moscow.get(i);
            Collections.sort(Moscow, new Comparator<Promo>() {
                public int compare(Promo o1, Promo o2) {
                    return o1.getMinSumm().compareTo(o2.getMinSumm());
                }
            });

            System.out.println("Name:            "+currentCode.getName());
            System.out.println("Condition:       "+currentCode.getConditionString());
            System.out.println("Gift:            "+currentCode.getGift());
            System.out.println("minSum:           "+String.format("%.0f",currentCode.getMinSumm()));
            System.out.println("Cities:          "+currentCode.getCitiesString());
            System.out.println("Code:             "+currentCode.getCode());
        }

    }
}


