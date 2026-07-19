package vn.vnpt.common.converter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

/**
 * Created by User on 7/12/2018.
 */
public class StreamToStringConverter implements InputStreamConverter<String> {
	Logger logger = LoggerFactory.getLogger(StreamToStringConverter.class);

	@Override
	public String convert(InputStream t) throws Exception {
		try (Reader r = new InputStreamReader(t, "UTF-8"); BufferedReader in = new BufferedReader(r)) {
			StringBuffer out = new StringBuffer();
			String inputLine;
			int row = 0;
			while ((inputLine = in.readLine()) != null) {
				if (row++ > 0)
					out.append('\n');
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
