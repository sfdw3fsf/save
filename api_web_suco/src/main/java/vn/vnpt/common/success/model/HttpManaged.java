package vn.vnpt.common.success.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import vn.vnpt.common.callHttp;
import vn.vnpt.common.converter.InputStreamConverter;
import vn.vnpt.common.converter.StreamToStringConverter;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;

public class HttpManaged {

    private static Logger logger = LoggerFactory.getLogger(HttpManaged.class);
    private static AtomicLong seq = new AtomicLong(0);
    static String contentType = "Content-Type";
    static String jsonType = "application/json";
    static String contentLength = "Content-Length";
    static String utf8 = "UTF-8";

    public static String post(String url, String body) throws Exception {
        return post(url, new HashMap<>(), body);
    }

    public static String post(String url, Map<String, String> headers, String body) throws Exception {
        return post(url, headers, body, 270000);
    }

    public static String post(String url, Map<String, String> headers, String body, int timeout) throws Exception {
        return post(url, headers, new StreamToStringConverter(), body, timeout);
    }

    public static <T> T post(String url, Map<String, String> headers, InputStreamConverter<T> isc, String body, int timeout) throws Exception {
        return postOrPut(url, headers, isc, body, "POST", timeout);
    }

    public static <T> T postOrPut(String url, Map<String, String> headers, InputStreamConverter<T> isc, String body, String method, int timeout) throws Exception {
        long reqseq = seq.incrementAndGet();
        logger.debug("seq:{} {}.url={}", reqseq, method, url);
        URL u = new URL(url);

        HttpURLConnection conn = (HttpURLConnection) u.openConnection();
        conn.setDoOutput(true);
        conn.setRequestMethod(method);
        conn.setRequestProperty(contentType, jsonType);
        conn.setConnectTimeout(timeout);
        conn.setReadTimeout(timeout);

        if (headers != null) {
            headers.forEach((String key, String value) -> conn.setRequestProperty(key, value));
        }

        String data = body;

        logger.debug("seq:{} {}.body={}", reqseq, method, Objects.toString(data, "null"));
        conn.setRequestProperty(contentLength, String.valueOf(data.length()));
        OutputStream os = conn.getOutputStream();
        os.write(data.getBytes(utf8));
        os.flush();
        os.close();

        T out = null;
        logger.debug("seq:{} {}.resp.status={}", reqseq, method, conn.getResponseCode());
        if (conn.getResponseCode() >= 200 && conn.getResponseCode() < 300) {
            out = isc.convert(conn.getInputStream());
        } else {
            out = isc.convert(conn.getErrorStream());
        }
        conn.disconnect();

        logger.debug("seq:{} {}.resp.body={}", reqseq, method, Objects.toString(out, "null"));
        return out;
    }

}
