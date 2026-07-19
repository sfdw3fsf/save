package vn.vnpt.vnptit.ecms.service;

import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringEscapeUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.select.Elements;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import vn.vnpt.context.AppRequestContext;
import vn.vnpt.database.AppSqlException;
import vn.vnpt.database.DbContext;
import vn.vnpt.database.SqlParameter;

@Service
public class TestService {
    private final DbContext dbContext;
    private final String QLTS_URL = "http://10.165.7.65:8081/MainSVR.asmx";
    
    
    public TestService(DbContext dbContext) {
        this.dbContext = dbContext;
    }

    public Object testIMS() {
		JSONArray jsonArray = new JSONArray();
        String requestXml = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
                "<soap12:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" " +
                "xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" " +
                "xmlns:soap12=\"http://www.w3.org/2003/05/soap-envelope\">\n" +
                "  <soap12:Body>\n" +
                "    <Get_BiddingPackge xmlns=\"http://tempuri.org/\">\n" +
                "      <sUserID>ctkt</sUserID>\n" +
                "      <sPass>pm1#2017</sPass>\n" +
                "      <sStartDate>2023/01/01</sStartDate>\n" +
                "      <sEndDate>2025/12/31</sEndDate>\n" +
                "      <sDepartmentCode>010</sDepartmentCode>\n" +
                "      <sProjectCode></sProjectCode>\n" +
                "    </Get_BiddingPackge>\n" +
                "  </soap12:Body>\n" +
                "</soap12:Envelope>";

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType("application/soap+xml; charset=utf-8"));
        headers.add("Accept", "application/soap+xml");
        HttpEntity<String> request = new HttpEntity<>(requestXml, headers);
        ResponseEntity<String> response = restTemplate.exchange(QLTS_URL, HttpMethod.POST, request, String.class);
        String xmlString = StringEscapeUtils.unescapeXml(StringEscapeUtils.unescapeJson(response.getBody()).replace("\\r\\n", "").replace("\r\n", ""));
		try {
			org.jsoup.nodes.Document doc = Jsoup.parse(xmlString, "", org.jsoup.parser.Parser.xmlParser());
			Elements tables = doc.select("Table");
	        for (org.jsoup.nodes.Element table : tables) {
	            JSONObject jsonObject = new JSONObject();
	            for (org.jsoup.nodes.Element child : table.children()) {
	                jsonObject.put(child.tagName(), child.text());
	            }
	            jsonArray.put(jsonObject);
	        }
			return jsonArray.toList();
		} catch (Exception e) {
		}
		return jsonArray.toList();
    }

    public Object getHopDongTrenIMS() {
		JSONArray jsonArray = new JSONArray();
        String requestXml = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
                "<soap12:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" " +
                "xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" " +
                "xmlns:soap12=\"http://www.w3.org/2003/05/soap-envelope\">\n" +
                "  <soap12:Body>\n" +
                "    <Get_ContractInfo xmlns=\"http://tempuri.org/\">\n" +
                "      <sUserID>ctkt</sUserID>\n" +
                "      <sPass>pm1#2017</sPass>\n" +
                "      <sDepartmentCode>010</sDepartmentCode>\n" +
                "      <sSubDepartmentCode></sSubDepartmentCode>\n" +
                "      <sProjectCode></sProjectCode>\n" +
                "    </Get_ContractInfo>\n" +
                "  </soap12:Body>\n" +
                "</soap12:Envelope>";

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType("application/soap+xml; charset=utf-8"));
        headers.add("Accept", "application/soap+xml");
        HttpEntity<String> request = new HttpEntity<>(requestXml, headers);
        ResponseEntity<String> response = restTemplate.exchange(QLTS_URL, HttpMethod.POST, request, String.class);
        String xmlString = StringEscapeUtils.unescapeXml(StringEscapeUtils.unescapeJson(response.getBody()).replace("\\r\\n", "").replace("\r\n", ""));
		try {
			org.jsoup.nodes.Document doc = Jsoup.parse(xmlString, "", org.jsoup.parser.Parser.xmlParser());
			Elements tables = doc.select("Table");
	        for (org.jsoup.nodes.Element table : tables) {
	            JSONObject jsonObject = new JSONObject();
	            for (org.jsoup.nodes.Element child : table.children()) {
	                jsonObject.put(child.tagName(), child.text());
	            }
	            jsonArray.put(jsonObject);
	        }
			return jsonArray.toList();
		} catch (Exception e) {
		}
		return jsonArray.toList();
    }

    public Object getGoiThauTrenIMS() {
		JSONArray jsonArray = new JSONArray();
        String requestXml = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
                "<soap12:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" " +
                "xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" " +
                "xmlns:soap12=\"http://www.w3.org/2003/05/soap-envelope\">\n" +
                "  <soap12:Body>\n" +
                "    <Get_BiddingPackge xmlns=\"http://tempuri.org/\">\n" +
                "      <sUserID>ctkt</sUserID>\n" +
                "      <sPass>pm1#2017</sPass>\n" +
                "      <sStartDate>2023/01/01</sStartDate>\n" +
                "      <sEndDate>2025/12/31</sEndDate>\n" +
                "      <sDepartmentCode>010</sDepartmentCode>\n" +
                "      <sProjectCode></sProjectCode>\n" +
                "    </Get_BiddingPackge>\n" +
                "  </soap12:Body>\n" +
                "</soap12:Envelope>";

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType("application/soap+xml; charset=utf-8"));
        headers.add("Accept", "application/soap+xml");
        HttpEntity<String> request = new HttpEntity<>(requestXml, headers);
        ResponseEntity<String> response = restTemplate.exchange(QLTS_URL, HttpMethod.POST, request, String.class);
        String xmlString = StringEscapeUtils.unescapeXml(StringEscapeUtils.unescapeJson(response.getBody()).replace("\\r\\n", "").replace("\r\n", ""));
		try {
			org.jsoup.nodes.Document doc = Jsoup.parse(xmlString, "", org.jsoup.parser.Parser.xmlParser());
			Elements tables = doc.select("Table");
	        for (org.jsoup.nodes.Element table : tables) {
	            JSONObject jsonObject = new JSONObject();
	            for (org.jsoup.nodes.Element child : table.children()) {
	                jsonObject.put(child.tagName(), child.text());
	            }
	            jsonArray.put(jsonObject);
	        }
			return jsonArray.toList();
		} catch (Exception e) {
		}
		return jsonArray.toList();
    }

    public Object lay_ds_hopdong_theo_matb(String ma_tb, int loaihd_id, int donvi_id, int tthd_id, int nhanvien_id, int donvi_dl_id, int dichvuvt_id, int loaitb_id) throws AppSqlException {
        List<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("vphanvung_id", AppRequestContext.getCurrent().getPhanVungID(), Types.NUMERIC));
        params.add(new SqlParameter("in_ma_tb", ma_tb, Types.VARCHAR));
        params.add(new SqlParameter("in_loaihd_id", loaihd_id, Types.NUMERIC));
        params.add(new SqlParameter("in_donvi_id", donvi_id, Types.NUMERIC));
        params.add(new SqlParameter("in_tthd_id", tthd_id, Types.NUMERIC));
        params.add(new SqlParameter("in_nhanvien_id", nhanvien_id, Types.NUMERIC));
        params.add(new SqlParameter("in_donvi_dl_id", donvi_dl_id, Types.NUMERIC));
        params.add(new SqlParameter("in_dichvuvt_id", dichvuvt_id, Types.NUMERIC));
        params.add(new SqlParameter("in_loaitb_id", loaitb_id, Types.NUMERIC));
        params.add(new SqlParameter("returnds", Types.REF_CURSOR));
        return dbContext.executeSpWithCursorToListMap("CSS.TRACUU_COMMON.LAY_DS_HOPDONG_THEO_MA_TB", params);
    }
}
