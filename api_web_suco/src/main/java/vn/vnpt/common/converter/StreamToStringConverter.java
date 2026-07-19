package vn.vnpt.common.converter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by User on 7/12/2018.
 */
public class StreamToStringConverter implements InputStreamConverter<String> {
    Logger logger = LoggerFactory.getLogger(StreamToStringConverter.class);

    @Override
    public String convert(InputStream t) throws Exception {
        try {
            StringBuffer out = new StringBuffer();
            BufferedReader in = new BufferedReader(new InputStreamReader(t, "UTF-8"));
            String inputLine;
            int row = 0;
            while ((inputLine = in.readLine()) != null) {
                if (row++ > 0) out.append('\n');
                out.append(inputLine);
            }
            return out.toString();
        } catch (Exception e) {
            throw e;
        } finally {
            t.close();
        }
    }
}
