package vn.vnptit.sapi.oneapp.managed;

import okhttp3.Headers;
import okhttp3.OkHttpClient;
import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;
import vn.vnptit.sapi.oneapp.converter.InputStreamConverter;
import vn.vnptit.sapi.oneapp.converter.StreamToStringConverter;

import java.io.*;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by User on 6/22/2018.
 */
public class HttpManaged {
    private static Logger logger = LoggerFactory.getLogger(HttpManaged.class);
    private static Logger slowLog = LoggerFactory.getLogger("slow");
    //private static HttpClientConfiguration httpClientConfiguration;
    private static ConcurrentHashMap<String, String> tplRequest = new ConcurrentHashMap<>();
    private static ConcurrentHashMap<String, Long> tplRequestLastModifiedTimes = new ConcurrentHashMap<>();
    private ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);
    private boolean reloadTemplate = true;
    private static AtomicLong seq = new AtomicLong(0);

    public HttpManaged() {
    }

    public static String put(Map<String, Object> params) throws Exception {
        return put(params, null);
    }

    public static String put(Map<String, Object> params, Map<String, String> headers, String body) throws Exception {
        return put(params, headers, new StreamToStringConverter(), body);
    }

    public static String put(Map<String, Object> params, Map<String, String> headers) throws Exception {
        return put(params, headers, new StreamToStringConverter(), null);
    }

    public static <T> T put(Map<String, Object> params, Map<String, String> headers, InputStreamConverter<T> isc)
            throws Exception {
        return put(params, headers, isc, null);
    }

    public static <T> T put(Map<String, Object> params, Map<String, String> headers, InputStreamConverter<T> isc,
                            String body) throws Exception {
        return postOrPut(params, headers, isc, body, "PUT");
    }

    public static String post(Map<String, Object> params) throws Exception {
        return post(params, null);
    }

    public static String post(Map<String, Object> params, Map<String, String> headers, String body) throws Exception {
        return post(params, headers, new StreamToStringConverter(), body);
    }

    public static ResponseWithTraceId postWTraceId(Map<String, Object> params, Map<String, String> headers, String body, int timeout) throws Exception {
        return postWTraceId(params, headers, new StreamToStringConverter(), body, timeout);
    }

    public static String postWithTimeout(Map<String, Object> params, Map<String, String> headers, String body, int timeout) throws Exception {
        return post(params, headers, new StreamToStringConverter(), body, timeout);
    }

    public static String post(Map<String, Object> params, Map<String, String> headers) throws Exception {
        return post(params, headers, new StreamToStringConverter(), null);
    }

    public static <T> T post(Map<String, Object> params, Map<String, String> headers, InputStreamConverter<T> isc)
            throws Exception {
        return post(params, headers, isc, null);
    }

    public static <T> T post(Map<String, Object> params, Map<String, String> headers, InputStreamConverter<T> isc,
                             String body) throws Exception {
        return postOrPut(params, headers, isc, body, "POST");
    }
    public static <T> ResponseWithTraceId<T> postWTraceId(Map<String, Object> params, Map<String, String> headers, InputStreamConverter<T> isc,
                             String body, int timeout) throws Exception {
        return postOrPutWTraceId(params, headers, isc, body, "POST", timeout);
    }

    public static <T> T post(Map<String, Object> params, Map<String, String> headers, InputStreamConverter<T> isc,
                             String body, int timeout) throws Exception {
        return postOrPutWithTimeout(params, headers, isc, body, "POST", timeout);
    }

    public static <T> T postOrPut(Map<String, Object> params, Map<String, String> headers, InputStreamConverter<T> isc,
                                  String body, String method) throws Exception {
        long t1 = System.currentTimeMillis();
        long reqseq = seq.incrementAndGet();
        logger.debug("seq:{} {}.url={}", reqseq, method, params.get("url").toString());
        URL u = new URL(params.get("url").toString());

        HttpURLConnection conn = (HttpURLConnection) u.openConnection();
        /*SimpleSSLSocketFactory sssf = new SimpleSSLSocketFactory();
        conn.setSSLSocketFactory(sssf);
        conn.setHostnameVerifier(new AllHostnameVerifier());*/
        conn.setDoOutput(true);
        conn.setRequestMethod(method);
        conn.setRequestProperty("Content-Type", "text/xml; charset=utf-8");
        //if (httpClientConfiguration == null) {
        conn.setConnectTimeout(300000);
        conn.setReadTimeout(300000);
        /*} else {
            conn.setConnectTimeout(httpClientConfiguration.getCallTimeoutMiliSecond());
            conn.setReadTimeout(httpClientConfiguration.getCallTimeoutMiliSecond());
        }*/

        if (headers != null) {
            headers.forEach((String key, String value) -> conn.setRequestProperty(key, value));
        }

        /*
         * if (params.containsKey("SOAPAction")) { conn.setRequestProperty("SOAPAction",
         * params.get("SOAPAction").toString()); }
         *
         * if (params.containsKey("Authorization")) {
         * conn.setRequestProperty("Authorization",
         * params.get("Authorization").toString()); }
         */

        String data = body;

        logger.debug("seq:{} {}.body={}", reqseq, method, Objects.toString(data, "null"));
        conn.setRequestProperty("Content-Length", String.valueOf(data.length()));
        OutputStream os = conn.getOutputStream();
        os.write(data.getBytes("UTF-8"));
        os.flush();
        os.close();

        T out = null;
        logger.debug("seq:{} {}.resp.status={}", reqseq, method, conn.getResponseCode());
        if (conn.getResponseCode() >= 200 && conn.getResponseCode() < 300) {
            out = isc.convert(conn.getInputStream());
        } else {
            if (conn.getErrorStream() != null)
                out = isc.convert(conn.getErrorStream());
            else
                out = isc.convert(conn.getInputStream());
        }
        conn.disconnect();

        long t2 = System.currentTimeMillis();
        if (t2 - t1 >= 500) {
            slowLog.info("seq:{} http.{}|{}|{}|{}", reqseq, method, params.get("url"), (t2 - t1), params.get("service"));
        }
        logger.debug("seq:{} {}.resp.body={}", reqseq, method, Objects.toString(out, "null"));
        return out;
    }

    public static <T> ResponseWithTraceId<T> postOrPutWTraceId(Map<String, Object> params, Map<String, String> headers, InputStreamConverter<T> isc,
                                  String body, String method, int timeout) throws Exception {
        long t1 = System.currentTimeMillis();
        long reqseq = seq.incrementAndGet();
        logger.debug("seq:{} {}.url={}", reqseq, method, params.get("url").toString());
        URL u = new URL(params.get("url").toString());

        HttpURLConnection conn = (HttpURLConnection) u.openConnection();
        conn.setDoOutput(true);
        conn.setRequestMethod(method);
        conn.setRequestProperty("Content-Type", "text/xml; charset=utf-8");
        conn.setConnectTimeout(timeout);
        conn.setReadTimeout(timeout);

        if (headers != null) {
            headers.forEach((String key, String value) -> conn.setRequestProperty(key, value));
        }


        String data = body;

        logger.debug("seq:{} {}.body={}", reqseq, method, Objects.toString(data, "null"));
        conn.setRequestProperty("Content-Length", String.valueOf(data.length()));
        OutputStream os = conn.getOutputStream();
        os.write(data.getBytes("UTF-8"));
        os.flush();
        os.close();

        T out = null;
        logger.debug("seq:{} {}.resp.status={}", reqseq, method, conn.getResponseCode());
        if (conn.getResponseCode() >= 200 && conn.getResponseCode() < 300) {
            out = isc.convert(conn.getInputStream());
        } else {
            if (conn.getErrorStream() != null)
                out = isc.convert(conn.getErrorStream());
            else
                out = isc.convert(conn.getInputStream());
        }
        conn.disconnect();

        String xTraceId = "-9999";
        try {
            Map<String, List<String>> headerFields = conn.getHeaderFields();
            xTraceId = headerFields.get("X-TraceId").get(0);
        } catch (Exception ex) {
        }

        long t2 = System.currentTimeMillis();
        if (t2 - t1 >= 500) {
            slowLog.info("seq:{} http.{}|{}|{}|{}", reqseq, method, params.get("url"), (t2 - t1), params.get("service"));
        }
        logger.debug("seq:{} {}.resp.body={}", reqseq, method, Objects.toString(out, "null"));
        return new ResponseWithTraceId<>(out, xTraceId);
    }

    public static <T> T postOrPutWithTimeout(Map<String, Object> params, Map<String, String> headers, InputStreamConverter<T> isc,
                                  String body, String method, int timeout) throws Exception {
        long t1 = System.currentTimeMillis();
        long reqseq = seq.incrementAndGet();
        logger.debug("seq:{} {}.url={}", reqseq, method, params.get("url").toString());
        URL u = new URL(params.get("url").toString());

        HttpURLConnection conn = (HttpURLConnection) u.openConnection();
        /*SimpleSSLSocketFactory sssf = new SimpleSSLSocketFactory();
        conn.setSSLSocketFactory(sssf);
        conn.setHostnameVerifier(new AllHostnameVerifier());*/
        conn.setDoOutput(true);
        conn.setRequestMethod(method);
        conn.setRequestProperty("Content-Type", "text/xml; charset=utf-8");
        //if (httpClientConfiguration == null) {
        conn.setConnectTimeout(timeout);
        conn.setReadTimeout(timeout);
        /*} else {
            conn.setConnectTimeout(httpClientConfiguration.getCallTimeoutMiliSecond());
            conn.setReadTimeout(httpClientConfiguration.getCallTimeoutMiliSecond());
        }*/

        if (headers != null) {
            headers.forEach((String key, String value) -> conn.setRequestProperty(key, value));
        }

        /*
         * if (params.containsKey("SOAPAction")) { conn.setRequestProperty("SOAPAction",
         * params.get("SOAPAction").toString()); }
         *
         * if (params.containsKey("Authorization")) {
         * conn.setRequestProperty("Authorization",
         * params.get("Authorization").toString()); }
         */

        String data = body;

        logger.debug("seq:{} {}.body={}", reqseq, method, Objects.toString(data, "null"));
        conn.setRequestProperty("Content-Length", String.valueOf(data.length()));
        OutputStream os = conn.getOutputStream();
        os.write(data.getBytes("UTF-8"));
        os.flush();
        os.close();

        T out = null;
        logger.debug("seq:{} {}.resp.status={}", reqseq, method, conn.getResponseCode());
        if (conn.getResponseCode() >= 200 && conn.getResponseCode() < 300) {
            out = isc.convert(conn.getInputStream());
        } else {
            if (conn.getErrorStream() != null)
                out = isc.convert(conn.getErrorStream());
            else
                out = isc.convert(conn.getInputStream());
        }
        conn.disconnect();

        long t2 = System.currentTimeMillis();
        if (t2 - t1 >= 500) {
            slowLog.info("seq:{} http.{}|{}|{}|{}", reqseq, method, params.get("url"), (t2 - t1), params.get("service"));
        }
        logger.debug("seq:{} {}.resp.body={}", reqseq, method, Objects.toString(out, "null"));
        return out;
    }

    public static Pair<Integer, String> postGetHttpStatus(Map<String, Object> params) throws Exception {
        return postGetHttpStatus(params, null);
    }

    public static Pair<Integer, String> postGetHttpStatus(Map<String, Object> params, Map<String, String> headers,
                                                          String body) throws Exception {
        return postGetHttpStatus(params, headers, new StreamToStringConverter(), body);
    }

    public static Pair<Integer, String> postGetHttpStatus(Map<String, Object> params, Map<String, String> headers)
            throws Exception {
        return postGetHttpStatus(params, headers, new StreamToStringConverter(), null);
    }

    public static <T> Pair<Integer, T> postGetHttpStatus(Map<String, Object> params, Map<String, String> headers,
                                                         InputStreamConverter<T> isc) throws Exception {
        return postGetHttpStatus(params, headers, isc, null);
    }

    public static <T> Pair<Integer, T> postGetHttpStatus(Map<String, Object> params, Map<String, String> headers,
                                                         InputStreamConverter<T> isc, String body) throws Exception {
        long t1 = System.currentTimeMillis();
        long reqseq = seq.incrementAndGet();

        logger.debug("seq:{} post.url={}", reqseq, params.get("url").toString());
        URL u = new URL(params.get("url").toString());

        HttpURLConnection conn = (HttpURLConnection) u.openConnection();
        conn.setDoOutput(true);
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "text/xml; charset=utf-8");
        //if (httpClientConfiguration == null) {
        conn.setConnectTimeout(30000);
        conn.setReadTimeout(30000);
        /*} else {
            conn.setConnectTimeout(httpClientConfiguration.getCallTimeoutMiliSecond());
            conn.setReadTimeout(httpClientConfiguration.getCallTimeoutMiliSecond());
        }*/

        if (headers != null) {
            headers.forEach((String key, String value) -> conn.setRequestProperty(key, value));
        }

        /*
         * if (params.containsKey("SOAPAction")) { conn.setRequestProperty("SOAPAction",
         * params.get("SOAPAction").toString()); }
         *
         * if (params.containsKey("Authorization")) {
         * conn.setRequestProperty("Authorization",
         * params.get("Authorization").toString()); }
         */

        String data = body;

        logger.debug("seq:{} post.body={}", reqseq, Objects.toString(data, "null"));
        conn.setRequestProperty("Content-Length", String.valueOf(data.length()));
        OutputStream os = conn.getOutputStream();
        os.write(data.getBytes("UTF-8"));
        os.flush();
        os.close();

        T respBody = null;
        int respStatus = conn.getResponseCode();
        logger.debug("seq:{} post.resp.status={}", reqseq, conn.getResponseCode());
        if (respStatus >= 200 && respStatus < 300) {
            respBody = isc.convert(conn.getInputStream());
        } else {
            respBody = isc.convert(conn.getErrorStream());
        }
        conn.disconnect();

        long t2 = System.currentTimeMillis();
        if (t2 - t1 >= 500) {
            slowLog.info("seq:{} http.post|{}|{}|{}", reqseq, params.get("url"), (t2 - t1), params.get("service"));
        }
        logger.debug("seq:{} post.resp.body={}", reqseq, Objects.toString(respBody, "null"));
        return Pair.of(respStatus, respBody);
    }

    public static String postGetCookie(Map<String, Object> params, Map<String, String> headers, String data) throws IOException {
        long t1 = System.currentTimeMillis();

        URL u = new URL(params.get("url").toString());

        HttpURLConnection conn = (HttpURLConnection) u.openConnection();
        conn.setDoOutput(true);
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "text/xml; charset=utf-8");
        //if (httpClientConfiguration == null) {
        conn.setConnectTimeout(30000);
        conn.setReadTimeout(30000);
        /*} else {
            conn.setConnectTimeout(httpClientConfiguration.getCallTimeoutMiliSecond());
            conn.setReadTimeout(httpClientConfiguration.getCallTimeoutMiliSecond());
        }*/

        if (headers != null) {
            headers.forEach((String key, String value) -> conn.setRequestProperty(key, value));
        }

        if (params.containsKey("SOAPAction")) {
            conn.setRequestProperty("SOAPAction", params.get("SOAPAction").toString());
        }

        if (params.containsKey("Authorization")) {
            conn.setRequestProperty("Authorization", params.get("Authorization").toString());
        }

        conn.setRequestProperty("Content-Length", String.valueOf(data.length()));
        OutputStream os = conn.getOutputStream();
        os.write(data.getBytes("UTF-8"));
        os.flush();
        os.close();

        String result = "";
        for (int i = 0; ; i++) {
            String headerName = conn.getHeaderFieldKey(i);
            String headerValue = conn.getHeaderField(i);

            if (headerName == null && headerValue == null) {
                break;
            }
            if ("Set-Cookie".equalsIgnoreCase(headerName)) {
                result = headerValue;
                break;
            }
        }
        conn.disconnect();

        long t2 = System.currentTimeMillis();
        if (t2 - t1 >= 500) {
            slowLog.info("http.post|{}|{}|{}", params.get("url"), (t2 - t1), params.get("service"));
        }

        return result;
    }

    public static String delete(String url) throws Exception {
        return delete(url, null);
    }

    public static String delete(String url, Map<String, String> headers) throws Exception {
        return getOrDelete(url, headers, "DELETE");
    }

    public static String get(String url) throws Exception {
        return get(url, null);
    }

    public static String get(String url, Map<String, String> headers) throws Exception {
        return getOrDelete(url, headers, "GET");
    }

    public static String getWithTimeout(String url, Map<String, String> headers, int timeout) throws Exception {
        return getOrDeleteWithTimeout(url, headers, "GET", timeout);
    }

    public static String getWTraceId(String url, Map<String, String> headers, int timeout) throws Exception {
        return getOrDeleteWTraceId(url, headers, "GET", timeout);
    }

    public static String getOrDelete(String url, Map<String, String> headers, String method) throws Exception {
        long reqseq = seq.incrementAndGet();
        logger.debug("seq:{} {}.req.url={}", reqseq, method, url);
        long t1 = System.currentTimeMillis();
        HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
        conn.setRequestMethod(method);
        //if (httpClientConfiguration == null) {
        conn.setConnectTimeout(30000);
        conn.setReadTimeout(30000);
        /*} else {
            conn.setConnectTimeout(httpClientConfiguration.getCallTimeoutMiliSecond());
            conn.setReadTimeout(httpClientConfiguration.getCallTimeoutMiliSecond());
        }*/

        if (headers != null) {
            headers.forEach((String key, String value) -> conn.setRequestProperty(key, value));
        }

        StringBuffer out = new StringBuffer();
        if (conn.getResponseCode() >= 200 && conn.getResponseCode() < 300) {
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                out.append(inputLine + "\n");
            }
            in.close();
        } else {
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getErrorStream(), "UTF-8"));
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                out.append(inputLine + "\n");
            }
            in.close();
        }
        conn.disconnect();
        String sout = out.toString();
        logger.debug("seq:{} {}.resp.status={}", reqseq, method, conn.getResponseCode());
        logger.debug("seq:{} {}.resp.body={}", reqseq, method, Objects.toString(sout, "null"));
        long t2 = System.currentTimeMillis();
        if (t2 - t1 >= 500) {
            slowLog.info("seq:{} http.{}|{}|{}|{}", reqseq, method, url, (t2 - t1), sout);
        }
        return sout;

    }

    public static String getOrDeleteWithTimeout(String url, Map<String, String> headers, String method, int timeout) throws Exception {
        long reqseq = seq.incrementAndGet();
        logger.debug("seq:{} {}.req.url={}", reqseq, method, url);
        long t1 = System.currentTimeMillis();
        HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
        conn.setRequestMethod(method);
        //if (httpClientConfiguration == null) {
        conn.setConnectTimeout(timeout);
        conn.setReadTimeout(timeout);
        /*} else {
            conn.setConnectTimeout(httpClientConfiguration.getCallTimeoutMiliSecond());
            conn.setReadTimeout(httpClientConfiguration.getCallTimeoutMiliSecond());
        }*/

        if (headers != null) {
            headers.forEach((String key, String value) -> conn.setRequestProperty(key, value));
        }

        StringBuffer out = new StringBuffer();
        if (conn.getResponseCode() >= 200 && conn.getResponseCode() < 300) {
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                out.append(inputLine + "\n");
            }
            in.close();
        } else {
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getErrorStream(), "UTF-8"));
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                out.append(inputLine + "\n");
            }
            in.close();
        }
        conn.disconnect();
        String sout = out.toString();
        logger.debug("seq:{} {}.resp.status={}", reqseq, method, conn.getResponseCode());
        logger.debug("seq:{} {}.resp.body={}", reqseq, method, Objects.toString(sout, "null"));
        long t2 = System.currentTimeMillis();
        if (t2 - t1 >= 500) {
            slowLog.info("seq:{} http.{}|{}|{}|{}", reqseq, method, url, (t2 - t1), sout);
        }
        return sout;

    }

    public static String getOrDeleteWTraceId(String url, Map<String, String> headers, String method, int timeout) throws Exception {
        long reqseq = seq.incrementAndGet();
        logger.debug("seq:{} {}.req.url={}", reqseq, method, url);
        long t1 = System.currentTimeMillis();
        HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
        conn.setRequestMethod(method);
        conn.setConnectTimeout(timeout);
        conn.setReadTimeout(timeout);

        if (headers != null) {
            headers.forEach((String key, String value) -> conn.setRequestProperty(key, value));
        }

        StringBuffer out = new StringBuffer();
        if (conn.getResponseCode() >= 200 && conn.getResponseCode() < 300) {
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                out.append(inputLine + "\n");
            }
            in.close();
        } else {
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getErrorStream(), "UTF-8"));
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                out.append(inputLine + "\n");
            }
            in.close();
        }
        conn.disconnect();

        String xTraceId = "-9999";
        try {
            Map<String, List<String>> headerFields = conn.getHeaderFields();
            xTraceId = headerFields.get("X-TraceId").get(0);
        } catch (Exception ex) {
        }

        String sout = out.toString();
        logger.debug("seq:{} {}.resp.status={}", reqseq, method, conn.getResponseCode());
        logger.debug("seq:{} {}.resp.body={}", reqseq, method, Objects.toString(sout, "null"));
        long t2 = System.currentTimeMillis();
        if (t2 - t1 >= 500) {
            slowLog.info("seq:{} http.{}|{}|{}|{}", reqseq, method, url, (t2 - t1), sout);
        }
        return xTraceId + "|---|" + sout;
    }

    public static String okpost(String url, Map<String, String> headers, String input, int timeout) {
        long reqseq = seq.incrementAndGet();
        logger.debug("seq:{} {}.url={}", reqseq, "POST", url);

        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(timeout, TimeUnit.SECONDS)
                .readTimeout(timeout, TimeUnit.SECONDS)
                .writeTimeout(timeout, TimeUnit.SECONDS)
                .build();

        okhttp3.RequestBody body = okhttp3.RequestBody.create(input, okhttp3.MediaType.parse("application/json"));
        okhttp3.Request.Builder requestBuilder = new okhttp3.Request.Builder()
                .url(url)
                .post(body);

        for (Map.Entry<String, String> entry : headers.entrySet()) {
            requestBuilder.addHeader(entry.getKey(), entry.getValue());
        }

        okhttp3.Request request = requestBuilder.build();

        try (okhttp3.Response response = client.newCall(request).execute()) {
            logger.debug("seq:{} {}.resp.status={}", reqseq, "POST", response.code());
            if (response.isSuccessful() && response.body() != null) {
                String resp = response.body().string();
                logger.debug("seq:{} {}.resp.body={}", reqseq, "POST", resp);
                return resp;
            } else {
                return String.valueOf(response.code() + " - " + response.body().string());
            }
        } catch (IOException e) {
            logger.debug("seq:{} {}.error={}", reqseq, "POST", e.getMessage());
            return e.getMessage();
        }
    }

    public static String uploadFile(String url, Map<String, String> headers, MultipartFile file, Map<String, Object> formData) throws IOException {
        OkHttpClient client = new OkHttpClient();

        okhttp3.RequestBody fileBody = okhttp3.RequestBody.create(
                file.getBytes(),
                okhttp3.MediaType.parse(file.getContentType() != null ? file.getContentType() : "application/octet-stream")
        );
        okhttp3.MultipartBody.Builder requestBodyBuilder = new okhttp3.MultipartBody.Builder()
                .setType(okhttp3.MultipartBody.FORM)
                .addFormDataPart("file", file.getOriginalFilename(), fileBody);
        if (formData != null) {
            for (Map.Entry<String, Object> entry : formData.entrySet()) {
                requestBodyBuilder.addFormDataPart(entry.getKey(), entry.getValue().toString());
            }
        }
        okhttp3.MultipartBody requestBody = requestBodyBuilder.build();
        okhttp3.Request.Builder requestBuilder = new okhttp3.Request.Builder()
                .url(url)
                .post(requestBody);
        if (headers != null && !headers.isEmpty()) {
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                requestBuilder.addHeader(entry.getKey(), entry.getValue());
            }
        }

        okhttp3.Request request = requestBuilder.build();

        try (okhttp3.Response response = client.newCall(request).execute()) {
            if (response.body() != null) {
                return response.body().string();
            } else {
                return String.valueOf(response.code());
            }
        } catch (IOException e) {
            return e.getMessage();
        }
    }
    public static String uploadMultipartFile(String url, Map<String, String> headers, String filePartName, MultipartFile file, Map<String, Object> formData) throws IOException {
        OkHttpClient client = new OkHttpClient();

        okhttp3.MultipartBody.Builder requestBodyBuilder = new okhttp3.MultipartBody.Builder()
                .setType(okhttp3.MultipartBody.FORM);

        if (file != null && !file.isEmpty()) {
            okhttp3.RequestBody fileBody = okhttp3.RequestBody.create(
                    file.getBytes(),
                    okhttp3.MediaType.parse(file.getContentType() != null ? file.getContentType() : "application/octet-stream")
            );
            requestBodyBuilder.addFormDataPart(
                    filePartName != null && !filePartName.trim().isEmpty() ? filePartName.trim() : "file",
                    file.getOriginalFilename(),
                    fileBody
            );
        }

        if (formData != null) {
            for (Map.Entry<String, Object> entry : formData.entrySet()) {
                Object value = entry.getValue();
                if (value == null) {
                    continue;
                }

                if (value instanceof Iterable) {
                    for (Object item : (Iterable<?>) value) {
                        if (item != null) {
                            requestBodyBuilder.addFormDataPart(entry.getKey(), item.toString());
                        }
                    }
                } else if (value.getClass().isArray()) {
                    int length = Array.getLength(value);
                    for (int i = 0; i < length; i++) {
                        Object item = Array.get(value, i);
                        if (item != null) {
                            requestBodyBuilder.addFormDataPart(entry.getKey(), item.toString());
                        }
                    }
                } else {
                    requestBodyBuilder.addFormDataPart(entry.getKey(), value.toString());
                }
            }
        }

        okhttp3.MultipartBody requestBody = requestBodyBuilder.build();
        okhttp3.Request.Builder requestBuilder = new okhttp3.Request.Builder()
                .url(url)
                .post(requestBody);
        if (headers != null && !headers.isEmpty()) {
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                requestBuilder.addHeader(entry.getKey(), entry.getValue());
            }
        }

        okhttp3.Request request = requestBuilder.build();

        try (okhttp3.Response response = client.newCall(request).execute()) {
            if (response.body() != null) {
                return response.body().string();
            } else {
                return String.valueOf(response.code());
            }
        } catch (IOException e) {
            return e.getMessage();
        }
    }

}