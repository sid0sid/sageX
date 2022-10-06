import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public class StupidRESTDownloader {

    final static String API_KEY = "put your key here";
    final static String OUT_FILENAME = "d:/temp/out.txt";
    final static String API_URL = "https://newsapi.org/v2/top-headlines?country=pl&category=business&apiKey=" + API_KEY;


    //***
    // Kod pisany po najmniejszej linii oporu, bez obsługi błędów, parametryzacji, refactoringu itp. Celem była
    // realizacja zadania możliwie jak najmniejszym kosztem czasowym
    //***

    public static void main(String[] args) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(OUT_FILENAME));

            URL url = new URL(API_URL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-Type", "text/plain");

            //if (conn.getResponseCode() != 200) { } //tworze rozwiazanie jak najprostsze by nie marnowac czasu

            BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
            String output;
            while ((output = br.readLine()) != null) {
                String json = output;
                JsonArray jArr = new JsonParser().parse(json).getAsJsonObject().getAsJsonArray("articles");
                for (JsonElement el : jArr) {
                    writer.write(String.valueOf(((JsonObject) el).get("author")));
                    writer.write(" : ");
                    writer.write(String.valueOf(((JsonObject) el).get("title")));
                    writer.write(" : ");
                    writer.write(String.valueOf(((JsonObject) el).get("description")));
                    writer.write("\n");
                }
            }
            writer.close();
            conn.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}