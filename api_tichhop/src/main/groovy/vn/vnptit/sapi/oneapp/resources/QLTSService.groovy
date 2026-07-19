package vn.vnptit.sapi.oneapp.resources

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import groovy.json.JsonBuilder
import groovy.json.JsonSlurper
import io.swagger.model.BaseResponse
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.context.request.NativeWebRequest
import vn.vnptit.sapi.oneapp.managed.HttpManaged
import vn.vnptit.sapi.oneapp.managed.LoggingManaged
import vn.vnptit.sapi.oneapp.managed.OracleManaged
import vn.vnptit.sapi.oneapp.models.CommonResponse
import vn.vnptit.sapi.oneapp.models.qlts.ASSET_CONSUMPTION_STATION
import vn.vnptit.sapi.oneapp.models.qlts.ASSET_GET_STATION_BY_CODE
import vn.vnptit.sapi.oneapp.models.qlts.ASSET_PROJECT_INFO
import vn.vnptit.sapi.oneapp.models.qlts.ASSET_SCAN_INFO
import vn.vnptit.sapi.oneapp.models.qlts.ASSET_SUMMARY_STATION
import vn.vnptit.sapi.oneapp.models.qlts.ASSET_SUPPLIES_CATEGORY_INFO
import vn.vnptit.sapi.oneapp.models.qlts.ASSET_SUPPLIES_CATEGORY_ITEM
import vn.vnptit.sapi.oneapp.models.qlts.FORMING_NEW_ASSET
import vn.vnptit.sapi.oneapp.models.qlts.QLTS_GET_ASSET_BY_CODE_SCAN_REQ
import vn.vnptit.sapi.oneapp.models.qlts.QLTS_GET_ASSET_BY_DATA_INPUT_REQ
import vn.vnptit.sapi.oneapp.models.qlts.QLTS_GET_DATA_CONFIG_REQ
import vn.vnptit.sapi.oneapp.models.qlts.QLTS_GET_INVENTORY_FIXED_REQ
import vn.vnptit.sapi.oneapp.models.qlts.QLTS_GET_INVENTORY_OFFLINE_REQ
import vn.vnptit.sapi.oneapp.models.qlts.QLTS_GET_INVENTORY_ROUND_REQ
import vn.vnptit.sapi.oneapp.models.qlts.QLTS_GET_INVENTORY_SCAN_REQ
import vn.vnptit.sapi.oneapp.models.qlts.QLTS_GET_INVENTORY_SEARCH_REQ
import vn.vnptit.sapi.oneapp.models.qlts.QLTS_GET_INVENTORY_UPDATE_ONLINE_REQ
import vn.vnptit.sapi.oneapp.models.qlts.QLTS_ONE_CLICK_REQ
import vn.vnptit.sapi.oneapp.models.qlts.QLTS_SEARCH_ASSET_BY_CSHT_REQ
import vn.vnptit.sapi.oneapp.models.qlts.QLTS_SEARCH_COMPANY_BY_KEY_REQ
import vn.vnptit.sapi.oneapp.models.qlts.QLTS_SEARCH_CSHT_BY_KEY_REQ
import vn.vnptit.sapi.oneapp.models.qlts.QLTS_UPDATE_ASSET_BROKEN_BY_ID_REQ
import vn.vnptit.sapi.oneapp.models.qlts.QLTS_UPDATE_ASSET_SERIAL_BY_ID_REQ
import vn.vnptit.sapi.oneapp.models.qlts.UPDATE_ASSET_SCAN_INFO
import vn.vnptit.sapi.oneapp.models.qlts.GET_LIST_IDENTITY_BY_TYPE
import vn.vnptit.sapi.oneapp.models.qlts.GET_ASSET_WAREHOUSE_INFO
import vn.vnptit.sapi.oneapp.models.sps.SPS_SERVICE_IMS
import vn.vnptit.sapi.oneapp.models.sps.TS_ISM_MODIFY_SUBSCRIBER
import vn.vnptit.sapi.oneapp.models.sps.TS_MTAS_MODIFY_SUBSCRIBER
import vn.vnptit.sapi.oneapp.models.sps.TraCuuThongTin
import vn.vnptit.sapi.oneapp.util.BaseResponseBuilder
import vn.vnptit.sapi.oneapp.util.ErrorCodeDefine
import vn.vnptit.sapi.oneapp.util.FakeSuccessCommonResponse
import vn.vnptit.sapi.oneapp.util.GenericApi

import javax.validation.Valid
import javax.ws.rs.Consumes
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType

import static vn.vnptit.sapi.oneapp.util.GenericApi.ID_TINHTHANH

@RestController
@RequestMapping("qlts")
class QLTSService {
    static String attrType = "@xsi:type"
    static String attrItemType = "@soapenc:arrayType"
    static String attrHref = "@href"
    static String attrNull = "@xsi:nil"

    static ThreadLocal<Gson> gson = ThreadLocal.withInitial { new GsonBuilder().serializeNulls().create() }

    @Autowired
    LoggingManaged loggingManaged
    @Autowired
    OracleManaged oracleManaged
    @Autowired
    HttpManaged httpManaged

    @Autowired
    GenericApi genericApi
    @Autowired
    NativeWebRequest request

    @Value("\${spring.defaultconfigs.qlts-url}")
    String qlts_url

    @Value("\${spring.defaultconfigs.qlts-url-old}")
    String qlts_url_old

    @Value("\${spring.defaultconfigs.qlts-token}")
    String qlts_token

    @PostMapping("/asset_get_station_by_code")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    ResponseEntity<BaseResponse> asset_get_station_by_code(@Valid @RequestBody ASSET_GET_STATION_BY_CODE body) {
        try {
            def action = "asset_get_station_by_code"
            def session = genericApi.getSessionData(request)
            def ma_tinh = session[genericApi.MA_TINH]

            if (body.ma_tinh == "['BCN']") {
                ma_tinh = "['BKN']";
            } else if (body.ma_tinh == "['HAG']") {
                ma_tinh = "['HGI']";
            } else if (body.ma_tinh == "['DAN']") {
                ma_tinh = "['DNO']";
            } else {
                ma_tinh = body.ma_tinh
            }

            Map<String, Object> params = [:]
            params.put("access_token", qlts_token);
            params.put("ma_tinh", ma_tinh);
            params.put("ma_csht", body.ma_csht);
            if (body.rims_status != null) params.put("rims_status", body.rims_status);
            if (body.name != null) params.put("name", body.name);
            if (body.page_index != null) params.put("page_index", body.page_index);
            if (body.page_size != null) params.put("page_size", body.page_size);
            def resp = post(qlts_url + action, null, params)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(resp))
        } catch (Exception e) {
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, null, e.getMessage(), ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @PostMapping("/asset_summary_station")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    ResponseEntity<BaseResponse> asset_summary_station(@Valid @RequestBody ASSET_SUMMARY_STATION body) {
        try {
            def action = "asset_summary_station"
            Map<String, Object> params = [:]
            params.put("access_token", qlts_token);
            params.put("ma_dvi", body.ma_dvi);
            if (body.den_ngay != null) params.put("den_ngay", body.den_ngay);
            if (body.ma_csht != null) params.put("ma_csht", body.ma_csht);
            if (body.loai_ts != null) params.put("loai_ts", body.loai_ts);
            if (body.page_index != null) params.put("page_index", body.page_index);
            if (body.page_size != null) params.put("page_size", body.page_size);
            def resp = post(qlts_url_old + action, null, params)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(resp))
        } catch (Exception e) {
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, null, e.getMessage(), ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @PostMapping("/asset_consumption_station")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    ResponseEntity<BaseResponse> asset_summary_station(@Valid @RequestBody ASSET_CONSUMPTION_STATION body) {
        try {
            def action = "asset_consumption_station"
            Map<String, Object> params = [:]
            params.put("access_token", qlts_token);
            params.put("ma_dvi", body.ma_dvi);
            if (body.den_ngay != null) params.put("den_ngay", body.den_ngay);
            if (body.ma_csht != null) params.put("ma_csht", body.ma_csht);
            if (body.loai_bien_dong != null) params.put("loai_bien_dong", body.loai_bien_dong);
            if (body.trang_thai != null) params.put("trang_thai", body.loai_bien_dong);
            if (body.page_index != null) params.put("page_index", body.page_index);
            if (body.page_size != null) params.put("page_size", body.page_size);
            def resp = post(qlts_url_old + action, null, params)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(resp))
        } catch (Exception e) {
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, null, e.getMessage(), ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @PostMapping("/get-asset-supplies-category-item")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    ResponseEntity<BaseResponse> get_asset_supplies_category_item(@Valid @RequestBody ASSET_SUPPLIES_CATEGORY_ITEM body) {
        try {
            def action = "get-asset-supplies-category-item"
            Map<String, Object> params = [:]
            params.put("access_token", qlts_token);
            params.put("code", body.code);
            def resp = post(qlts_url + action, null, params)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(resp))
        } catch (Exception e) {
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, null, e.getMessage(), ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @PostMapping("/get-asset-supplies-category-info")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    ResponseEntity<BaseResponse> get_asset_supplies_category_info(@Valid @RequestBody ASSET_SUPPLIES_CATEGORY_INFO body) {
        try {
            def action = "get-asset-supplies-category-info"
            Map<String, Object> params = [:]
            params.put("access_token", qlts_token);
            params.put("code", body.code);
            if (body.apply_date != null) params.put("apply_date", body.apply_date);
            if (body.apply_status != null) params.put("apply_status", body.apply_status);
            if (body.write_date != null) params.put("write_date", body.write_date);
            if (body.page_index != null) params.put("page_index", body.page_index);
            if (body.page_size != null) params.put("page_size", body.page_size);
            def resp = post(qlts_url + action, null, params)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(resp))
        } catch (Exception e) {
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, null, e.getMessage(), ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @PostMapping("/forming_new_asset")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    ResponseEntity<BaseResponse> forming_new_asset(@Valid @RequestBody FORMING_NEW_ASSET body) {
        try {
            def action = "forming_new_asset"
            Map<String, Object> params = [:]
            params.put("access_token", qlts_token);
            params.put("ma_du_an", body.ma_du_an);
            if (body.from_date != null) params.put("apply_date", body.from_date);
            def resp = post(qlts_url_old + action, null, params)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(resp))
        } catch (Exception e) {
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, null, e.getMessage(), ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @PostMapping("/get_asset_project_info")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    ResponseEntity<BaseResponse> get_asset_project_info(@Valid @RequestBody ASSET_PROJECT_INFO body) {
        try {
            def action = "get_asset_project_info"
            Map<String, Object> params = [:]
            params.put("access_token", qlts_token);
            params.put("company_ktbd_code", body.company_ktbd_code);
            if (body.project_code != null) params.put("project_code", body.project_code);
            if (body.so_the != null) params.put("so_the", body.so_the);
            def resp = post(qlts_url + action, null, params)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(resp))
        } catch (Exception e) {
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, null, e.getMessage(), ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @PostMapping("/get_asset_scan_info")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    ResponseEntity<BaseResponse> get_asset_scan_info(@Valid @RequestBody ASSET_SCAN_INFO body) {
        try {
            def action = "get_asset_scan_info"
            Map<String, Object> params = [:]
            params.put("access_token", qlts_token);
            params.put("type", body.type);
            params.put("value", body.value);
            def resp = post(qlts_url + action, null, params)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(resp))
        } catch (Exception e) {
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, null, e.getMessage(), ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @PostMapping("/update_asset_scan_info")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    ResponseEntity<BaseResponse> update_asset_scan_info(@Valid @RequestBody UPDATE_ASSET_SCAN_INFO body) {
        try {
            def action = "update_asset_scan_info"
            Map<String, Object> params = [:]
            params.put("access_token", qlts_token);
            params.put("list", new JsonBuilder(body.list).toPrettyString());
            def resp = post(qlts_url + action, null, params)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(resp))
        } catch (Exception e) {
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, null, e.getMessage(), ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }
    @PostMapping("/qlts_get_data_config")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    ResponseEntity<BaseResponse> qlts_get_data_config(@Valid @RequestBody QLTS_GET_DATA_CONFIG_REQ body) {
        try {
            def action = "qlts_get_data_config"
            def session = genericApi.getSessionData(request)
            def ma_tinh = session[genericApi.MA_TINH]
            Map<String, Object> params = [:]
            params.put("token", body.token)
            def resp = post(qlts_url + action, null, params)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(resp))
        } catch (Exception e) {
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, null, e.getMessage(), ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }
    @PostMapping("/qlts_get_asset_by_code_scan")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    ResponseEntity<BaseResponse> qlts_get_asset_by_code_scan(@Valid @RequestBody QLTS_GET_ASSET_BY_CODE_SCAN_REQ body) {
        try {
            def action = "qlts_get_asset_by_code_scan"
            def session = genericApi.getSessionData(request)
            def ma_tinh = session[genericApi.MA_TINH]
            Map<String, Object> params = [:]
            params.put("token", body.token)
            params.put("type", body.type)
            params.put("value", body.value)
            def resp = post(qlts_url + action, null, params)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(resp))
        } catch (Exception e) {
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, null, e.getMessage(), ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }
    @PostMapping("/qlts_get_asset_by_data_input")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    ResponseEntity<BaseResponse> qlts_get_asset_by_data_input(@Valid @RequestBody QLTS_GET_ASSET_BY_DATA_INPUT_REQ body) {
        try {
            def action = "qlts_get_asset_by_data_input"
            def session = genericApi.getSessionData(request)
            def ma_tinh = session[genericApi.MA_TINH]
            Map<String, Object> params = [:]
            params.put("token", body.token)
            params.put("so_the", body.so_the)
            params.put("asset_code_number", body.asset_code_number)
            params.put("serial", body.serial)
            def resp = post(qlts_url + action, null, params)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(resp))
        } catch (Exception e) {
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, null, e.getMessage(), ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }
    @PostMapping("/qlts_get_inventory_round")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    ResponseEntity<BaseResponse> qlts_get_inventory_round(@Valid @RequestBody QLTS_GET_INVENTORY_ROUND_REQ body) {
        try {
            def action = "qlts_get_inventory_round"
            def session = genericApi.getSessionData(request)
            def ma_tinh = session[genericApi.MA_TINH]
            Map<String, Object> params = [:]
            params.put("token", body.token)
            def resp = post(qlts_url + action, null, params)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(resp))
        } catch (Exception e) {
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, null, e.getMessage(), ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }
    @PostMapping("/qlts_get_inventory_fixed")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    ResponseEntity<BaseResponse> qlts_get_inventory_fixed(@Valid @RequestBody QLTS_GET_INVENTORY_FIXED_REQ body) {
        try {
            def action = "qlts_get_inventory_fixed"
            def session = genericApi.getSessionData(request)
            def ma_tinh = session[genericApi.MA_TINH]
            Map<String, Object> params = [:]
            params.put("token", body.token)
            params.put("inventory_id", body.inventory_id)
            def resp = post(qlts_url + action, null, params)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(resp))
        } catch (Exception e) {
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, null, e.getMessage(), ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }
    @PostMapping("/qlts_get_inventory_scan")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    ResponseEntity<BaseResponse> qlts_get_inventory_scan(@Valid @RequestBody QLTS_GET_INVENTORY_SCAN_REQ body) {
        try {
            def action = "qlts_get_inventory_scan"
            def session = genericApi.getSessionData(request)
            def ma_tinh = session[genericApi.MA_TINH]
            Map<String, Object> params = [:]
            params.put("token", body.token)
            params.put("inventory_id", body.inventory_id)
            params.put("type_code", body.type_code)
            params.put("value", body.value)
            def resp = post(qlts_url + action, null, params)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(resp))
        } catch (Exception e) {
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, null, e.getMessage(), ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }
    @PostMapping("/qlts_get_inventory_search")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    ResponseEntity<BaseResponse> qlts_get_inventory_search(@Valid @RequestBody QLTS_GET_INVENTORY_SEARCH_REQ body) {
        try {
            def action = "qlts_get_inventory_search"
            def session = genericApi.getSessionData(request)
            def ma_tinh = session[genericApi.MA_TINH]
            Map<String, Object> params = [:]
            params.put("token", body.token)
            params.put("inventory_id", body.inventory_id)
            params.put("so_the", body.so_the)
            params.put("asset_code_number", body.asset_code_number)
            params.put("serial", body.serial)
            def resp = post(qlts_url + action, null, params)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(resp))
        } catch (Exception e) {
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, null, e.getMessage(), ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }
    @PostMapping("/qlts_get_inventory_update_online")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    @FakeSuccessCommonResponse
    ResponseEntity<BaseResponse> qlts_get_inventory_update_online(@Valid @RequestBody QLTS_GET_INVENTORY_UPDATE_ONLINE_REQ body) {
        try {
            def action = "qlts_get_inventory_update_online"
            def session = genericApi.getSessionData(request)
            def ma_tinh = session[genericApi.MA_TINH]
            Map<String, Object> params = [:]
            params.put("token", body.token)
            params.put("inventory_id", body.inventory_id)
            params.put("asset_id", body.asset_id)
            params.put("status_code", body.status_code)
            params.put("data_update", body.data_update)
            params.put("scene_photo", body.scene_photo)
            params.put("description", body.description)
            def resp = post(qlts_url + action, null, params)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(resp))
        } catch (Exception e) {
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, null, e.getMessage(), ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }
    @PostMapping("/qlts_get_inventory_offline")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    ResponseEntity<BaseResponse> qlts_get_inventory_offline(@Valid @RequestBody QLTS_GET_INVENTORY_OFFLINE_REQ body) {
        try {
            def action = "qlts_get_inventory_offline"
            def session = genericApi.getSessionData(request)
            def ma_tinh = session[genericApi.MA_TINH]
            Map<String, Object> params = [:]
            params.put("token", body.token)
            params.put("inventory_id", body.inventory_id)
            params.put("fixed_id", body.fixed_id)
            def resp = post(qlts_url + action, null, params)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(resp))
        } catch (Exception e) {
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, null, e.getMessage(), ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }
    @PostMapping("/qlts_search_asset_by_csht")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    ResponseEntity<BaseResponse> qlts_search_asset_by_csht(@Valid @RequestBody QLTS_SEARCH_ASSET_BY_CSHT_REQ body) {
        try {
            def action = "qlts_search_asset_by_csht"
            def session = genericApi.getSessionData(request)
            def ma_tinh = session[genericApi.MA_TINH]
            Map<String, Object> params = [:]
            params.put("token", body.token)
            params.put("MaCSHT", body.MaCSHT)
            params.put("PartNumber", body.PartNumber)
            params.put("TenVietTat", body.TenVietTat)
            params.put("MaDonViQuanLy", body.MaDonViQuanLy)
            params.put("MaDonViSuDung", body.MaDonViSuDung)
            params.put("page_index", body.page_index)
            params.put("page_size", body.page_size)
            params.put("show_total", body.show_total)
            def resp = post(qlts_url + action, null, params)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(resp))
        } catch (Exception e) {
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, null, e.getMessage(), ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }
    @PostMapping("/qlts_update_asset_broken_by_id")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    @FakeSuccessCommonResponse
    ResponseEntity<BaseResponse> qlts_update_asset_broken_by_id(@Valid @RequestBody QLTS_UPDATE_ASSET_BROKEN_BY_ID_REQ body) {
        try {
            def action = "qlts_update_asset_broken_by_id"
            def session = genericApi.getSessionData(request)
            def ma_tinh = session[genericApi.MA_TINH]
            Map<String, Object> params = [:]
            params.put("token", body.token)
            params.put("IDChiTiet", body.IDChiTiet)
            params.put("SoThe", body.SoThe)
            params.put("MaDonViQuanLy", body.MaDonViQuanLy)
            params.put("MaTinhTrang", body.MaTinhTrang)
            params.put("NoiDung", body.NoiDung)
            def resp = post(qlts_url + action, null, params)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(resp))
        } catch (Exception e) {
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, null, e.getMessage(), ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }
    @PostMapping("/qlts_update_asset_serial_by_id")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    @FakeSuccessCommonResponse
    ResponseEntity<BaseResponse> qlts_update_asset_serial_by_id(@Valid @RequestBody QLTS_UPDATE_ASSET_SERIAL_BY_ID_REQ body) {
        try {
            def action = "qlts_update_asset_serial_by_id"
            def session = genericApi.getSessionData(request)
            def ma_tinh = session[genericApi.MA_TINH]
            Map<String, Object> params = [:]
            params.put("token", body.token)
            params.put("IDChiTiet", body.IDChiTiet)
            params.put("SoThe", body.SoThe)
            params.put("MaDonViQuanLy", body.MaDonViQuanLy)
            params.put("Serial", body.Serial)
            def resp = post(qlts_url + action, null, params)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(resp))
        } catch (Exception e) {
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, null, e.getMessage(), ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }
    @PostMapping("/qlts_search_company_by_key")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    ResponseEntity<BaseResponse> qlts_search_company_by_key(@Valid @RequestBody QLTS_SEARCH_COMPANY_BY_KEY_REQ body) {
        try {
            def action = "qlts_search_company_by_key"
            def session = genericApi.getSessionData(request)
            def ma_tinh = session[genericApi.MA_TINH]
            Map<String, Object> params = [:]
            params.put("token", body.token)
            params.put("TuKhoa", body.TuKhoa)
            params.put("MaDonVi", body.MaDonVi)
            params.put("page_index", body.page_index)
            params.put("page_size", body.page_size)
            params.put("show_total", body.show_total)
            def resp = post(qlts_url + action, null, params)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(resp))
        } catch (Exception e) {
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, null, e.getMessage(), ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }
    @PostMapping("/qlts_search_csht_by_key")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    ResponseEntity<BaseResponse> qlts_search_csht_by_key(@Valid @RequestBody QLTS_SEARCH_CSHT_BY_KEY_REQ body) {
        try {
            def action = "qlts_search_csht_by_key"
            def session = genericApi.getSessionData(request)
            def ma_tinh = session[genericApi.MA_TINH]
            Map<String, Object> params = [:]
            params.put("token", body.token)
            params.put("TuKhoa", body.TuKhoa)
            params.put("page_index", body.page_index)
            params.put("page_size", body.page_size)
            params.put("show_total", body.show_total)
            def resp = post(qlts_url + action, null, params)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(resp))
        } catch (Exception e) {
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, null, e.getMessage(), ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @PostMapping("/qlts_one_click")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    ResponseEntity<BaseResponse> qlts_one_click(@Valid @RequestBody QLTS_ONE_CLICK_REQ body) {
        try {
            def action = "qlts_one_click"
            Map<String, Object> params = [:]
            params.put("access_token", qlts_token)
            params.put("email_user", body.email_user)
            params.put("may_cn", body.may_cn)
            def resp = post(qlts_url + action, null, params)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(resp))
        } catch (Exception e) {
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, null, e.getMessage(), ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @PostMapping("/get_list_identity_by_type")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    ResponseEntity<BaseResponse> get_list_identity_by_type(@Valid @RequestBody GET_LIST_IDENTITY_BY_TYPE body) {
        try {
            def action = "get_list_identity_by_type"
            def session = genericApi.getSessionData(request)
            def ma_tinh = session[genericApi.MA_TINH]

            String code = body.company_code + "000000"
            String type_id = ""
            if (body.nghiepvu == "NV.THUHOI")
                type_id = "9";
            if (body.nghiepvu == "NV.THAYTHE")
                type_id = "8";
            if (body.nghiepvu == "NV.DIEUCHUYEN")
                type_id = "0";

            //tạm thời nghiệp vụ thu hồi => biến type_in_out ko dùng
            Map<String, Object> params = [:]
            params.put("access_token", qlts_token);
            params.put("company_code", code);
            params.put("type_id", type_id);
            params.put("type_in_out", "N");

            def resp = post(qlts_url + action, null, params)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(resp))
        } catch (Exception e) {
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, null, e.getMessage(), ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    @PostMapping("/get_asset_warehouse_info")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    ResponseEntity<BaseResponse> get_asset_warehouse_info(@Valid @RequestBody GET_ASSET_WAREHOUSE_INFO body) {
        try {
            def action = "get_asset_warehouse_info"
            def session = genericApi.getSessionData(request)
            def ma_tinh = session[genericApi.MA_TINH]

            String type_id = ""
            if (body.nghiepvu == "NV.THUHOI")
                type_id = "9";
            if (body.nghiepvu == "NV.THAYTHE")
                type_id = "8";
            if (body.nghiepvu == "NV.DIEUCHUYEN")
                type_id = "0";

            Map<String, Object> params = [:]
            params.put("access_token", qlts_token);
            params.put("identity_name", body.identity_name);
            params.put("type_id", type_id);
            params.put("company_code", body.company_code);
            if (body.type_in_out != "NOT") {
                 params.put("type_in_out", body.type_in_out);
            }
           
            def resp = post(qlts_url + action, null, params)
            return ResponseEntity.ok().body(BaseResponseBuilder.of(resp))
        } catch (Exception e) {
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, null, e.getMessage(), ErrorCodeDefine.INTERNAL_SERVER_ERROR))
        }
    }

    def post(String url, Map<String, String> headers, Map<String, Object> params) {
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        okhttp3.MediaType mediaType = okhttp3.MediaType.parse("text/plain");

        def builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
        params.each { k, v -> builder.addFormDataPart(k, v.toString()) }
        def body = builder.build();
        Request request = new Request.Builder()
                .url(url).method("POST", body)
                .build();
        Response response = client.newCall(request).execute();
        return new JsonSlurper().parseText(response.body().string())
    }
}
