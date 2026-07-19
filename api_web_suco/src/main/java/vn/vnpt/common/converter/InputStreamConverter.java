package vn.vnpt.common.converter;

import java.io.InputStream;

/**
 * Created by User on 7/12/2018.
 */
public interface InputStreamConverter<T> {
    public T convert(InputStream t) throws Exception;
}
