//package vn.vnpt.common.converter;
//
//import org.json.JSONObject;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import java.io.InputStream;
//
///**
// * Created by User on 7/12/2018.
// */
//public class XmlRPCStreamToJSONObjectConverter implements InputStreamConverter<JSONObject> {
//    Logger logger = LoggerFactory.getLogger(XmlRPCStreamToJSONObjectConverter.class);
//    @Override
//    public JSONObject convert(InputStream t) throws Exception {
//        try {
//            return new RPCParser().readMethodResponse(t).getJSONObject(0);
//        } catch (Exception e) {
//            throw e;
//        } finally {
//            t.close();
//        }
//    }
//}
