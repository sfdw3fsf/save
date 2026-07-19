package vn.vnptit.sapi.oneapp.resources

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.web.bind.annotation.*
import vn.vnptit.sapi.oneapp.managed.HttpManaged
import vn.vnptit.sapi.oneapp.managed.LoggingManaged
import vn.vnptit.sapi.oneapp.models.CommonResponse

@RestController
@RequestMapping("/hh")
class HomeHubService {

    static Gson gson = new GsonBuilder().serializeNulls().create()

    @Autowired
    HttpManaged httpManaged

    @Autowired
    LoggingManaged loggingManaged

    @Value("\${spring.httpservices.onebss.url}")
    private String onebssUrl;

    @Value("\${spring.httpservices.onebss.api-key}")
    private String apiKey;

    @Value("\${spring.httpservices.onebss.api-secret}")
    private String apiSecret;

    @Value("\${spring.httpservices.onebss.access-token}")
    private String accessToken;

    String postToHomeHub(String path, String jsonBody) {

        long timestamp = System.currentTimeMillis() / 1000
        Map<String, String> headers = ["Content-Type"  : "application/json",
                                       "X-Api-Key"     : apiKey,
                                       "X-Api-Secret"  : apiSecret,
                                       "X-Access-Token": accessToken,
                                       "X-Timestamp"   : timestamp.toString()]

        String fullUrl = onebssUrl + path

        return httpManaged.post(["url": fullUrl],
                headers,
                jsonBody)
    }

    @PostMapping("/subscribe")
    Map subscribe(@RequestBody Map body) {
        try {
            Map payload = [ma_gd        : body.ma_gd,
                           so_tt        : body.so_tt,
                           so_lienhe    : body.so_lienhe,
                           so_dt        : body.so_dt,
                           ma_tb        : body.ma_tb,
                           ma_goi       : body.ma_goi,
                           ds_dich_vu   : body.ds_dich_vu,
                           tien_trc_thue: body.tien_trc_thue as Integer,
                           tien_sau_thue: body.tien_sau_thue as Integer,
                           thao_tac     : body.thao_tac as Integer,
                           ten_kh       : body.ten_kh,
                           email        : body.email,
                           tinh_kh      : body.tinh_kh,
                           phuong_kh    : body.phuong_kh,
                           dia_chi_kh   : body.dia_chi_kh]
            String response = postToHomeHub("/v1/homehub/subscribe", gson.toJson(payload))
            Map data = gson.fromJson(response, Map)
            return [ma_loi  : 0,
                    chi_tiet: "Thành công"]

        } catch (Exception e) {
            loggingManaged.error("HomeHub subscribe error", e)
            return [ma_loi: 1, chi_tiet: e.message]
        }
    }


    @PostMapping("/getHisSub")
    Map getHisSub(@RequestBody Map body) {
        try {
            Map payload = [ma_gd    : body.ma_gd,
                           so_tb    : body.so_tb,
                           ma_tb    : body.ma_tb,
                           stt_trang: body.stt_trang as Integer,
                           kt_trang : body.kt_trang as Integer]
            String response = postToHomeHub("/v1/homehub/getHisSub", gson.toJson(payload))
            Map data = gson.fromJson(response, Map)
            return [ma_loi  : 0,
                    chi_tiet: "SUCCESS",
                    ma_tb   : data.ma_tb,
                    ds_goi  : data.ds_goi ?: []]
        } catch (Exception e) {
            loggingManaged.error("HomeHub getHisSub error", e)
            return [ma_loi: 1, chi_tiet: e.message]
        }
    }

    @PostMapping("/getServices")
    Map getServices(@RequestBody Map body) {
        try {
            Map payload = [ma_gd: body.ma_gd]
            String response = postToHomeHub("/v1/homehub/getServices", gson.toJson(payload))
            Map data = gson.fromJson(response, Map)
            return [ma_loi    : 0,
                    chi_tiet  : "SUCCESS",
                    ds_dich_vu: data.ds_dich_vu ?: []]
        } catch (Exception e) {
            loggingManaged.error("HomeHub getServices error", e)
            return [ma_loi: 1, chi_tiet: e.message]
        }
    }

}
