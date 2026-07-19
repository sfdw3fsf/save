package vn.vnptit.sapi.oneapp.managed;

import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import vn.vnptit.sapi.oneapp.converter.InputStreamConverter;
import vn.vnptit.sapi.oneapp.converter.StreamToStringConverter;

import javax.net.ssl.*;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
//import java.util.Collections;
//import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
//import java.util.concurrent.TimeUnit;
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

    public static <T> T postOrPut(Map<String, Object> params, Map<String, String> headers, InputStreamConverter<T> isc,
                                  String body, String method) throws Exception {
        long t1 = System.currentTimeMillis();
        long reqseq = seq.incrementAndGet();
        logger.debug("seq:{} {}.url={}", reqseq, method, params.get("url").toString());
        URL u = new URL(params.get("url").toString());

        HttpURLConnection conn = (HttpURLConnection) u.openConnection();
        conn.setDoOutput(true);
        conn.setRequestMethod(method);
        conn.setRequestProperty("Content-Type", "text/xml; charset=utf-8");
        //if (httpClientConfiguration == null) {
        conn.setConnectTimeout(120000);
        conn.setReadTimeout(120000);
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
            if (conn.getErrorStream() != null)
                respBody = isc.convert(conn.getErrorStream());
            else
                respBody = isc.convert(conn.getInputStream());
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
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getErrorStream() == null ? conn.getInputStream() : conn.getErrorStream(), "UTF-8"));
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
}
