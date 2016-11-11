package com.akshay.nearbycartsimulator.util;

import android.text.TextUtils;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Akshay
 * This class is used for the Restful API call to RANDROM.org
 */
public class RestUtils {

    private static final int RESPONSE_SUCCESS = 200;
    private static final int CART_GENERATE_STRENGTH_COUNT = 1;
    private static final int CART_MINIMUM_STRENGTH = 1;
    private static final int CART_MAXIMUM_STRENGTH = 100;

    private final String baseURL = "https://www.random.org/integers/?";
    private final String cartStrengthParams = "num="+CART_GENERATE_STRENGTH_COUNT +"&min="+CART_MINIMUM_STRENGTH+"&max="+CART_MAXIMUM_STRENGTH+"&col=1&base=10&format=plain&rnd=new";
    private final String cartStrengthURL = baseURL + cartStrengthParams;

    private static  boolean mRequestRetried = false;


    public class Result {
        public int code;
        public String text;
    }

    public Integer generateCartStrength() {
        Integer cartStrength = 0;
        try {
            Result response = makeGetRequest(new URL(cartStrengthURL));
            if ( (response.code != 200 || TextUtils.isEmpty(response.text)) && !mRequestRetried) {
                mRequestRetried = true;
                generateCartStrength();
            } else {
                Log.d("CartRange", response.text);
               return Integer.parseInt(response.text);
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return cartStrength;
    }


    /**
     * Makes a HTTP Get Reqest
     * @param url
     * @return the result with return code and value
     */
    private Result makeGetRequest(final URL url) {

        final Result result = new Result();

        InputStream inputStream = null;
        try {
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.setRequestMethod("GET");
            connection.setConnectTimeout(5000); // 5 sec
            connection.setReadTimeout(10000); // 10 sec
            connection.setDoInput(true);
            connection.connect();

            result.code = connection.getResponseCode();

            if (result.code == RESPONSE_SUCCESS) {
                inputStream = connection.getInputStream();
                BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
                String temp;
                StringBuilder response = new StringBuilder();

                while ((temp = br.readLine()) != null) {
                    response.append(temp);
                }
                result.text = response.toString();
            } else {
                Log.d("HTTPCALLS", "error");

                throw new Exception("Failure Response Code : " + result.code);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (Exception ex) {
                    // ignore
                }
            }
        }
        return result;
    }

}
