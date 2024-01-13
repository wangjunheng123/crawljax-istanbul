import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import okhttp3.*;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CodeCoverageHandler {

    private static final String COVERAGE_SERVER = "http://localhost:6969/";
    private long coverageCountTime;

    private static OkHttpClient httpClient;

    private static final Logger LOG = LoggerFactory.getLogger(CodeCoverageHandler.class);

    public static void resetCoverage() {
        Request request = new Request.Builder()
                .url("http://localhost:6969/coverage/reset")
                .post(RequestBody.create(null, new byte[0]))  // 使用空的请求体
                .build();
        OkHttpClient client = new OkHttpClient();

        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful()) {
                int statusCode = response.code();
                LOG.info("Status Code: " + statusCode);

                // 获取响应体的内容
                String responseBody = response.body().string();
                LOG.info("Response Body: " + responseBody);

                LOG.info("Reset request was successful.");
            } else {
                LOG.info("Reset request failed. Response code: " + response.code());
                LOG.info("Response body: " + response.body().string());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    public static void getCodeCoverageStatus() {
        try {
            String url = "http://localhost:6969/coverage";
            LOG.info("timestamp is :"+System.currentTimeMillis());
            Document doc = Jsoup.connect(url).get();
            Elements divs = doc.select("div.fl.pad1y.space-right2");
            Map<String, String> coverage = new HashMap<>();
            for (Element div : divs) {
                Elements spans = div.select("span");
                StringBuilder structureCoverage = new StringBuilder();
                for (Element span : spans) {
                    structureCoverage.append(span.text().trim()).append(",");
                }
                coverage.put(spans.get(1).text().trim(), structureCoverage.toString());
            }
            for (Map.Entry<String, String> entry : coverage.entrySet()) {
                LOG.info(entry.getKey() + ": " + entry.getValue());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) throws IOException {
         CodeCoverageHandler.resetCoverage();


    }
}