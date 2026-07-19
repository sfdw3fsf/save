package vn.vnptit.sapi.oneapp.resources

import com.fasterxml.jackson.databind.ObjectMapper
import groovy.json.JsonSlurper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import vn.vnptit.sapi.oneapp.managed.HttpManaged
import vn.vnptit.sapi.oneapp.managed.LoggingManaged
import vn.vnptit.sapi.oneapp.models.CommonResponse

@RestController
@RequestMapping("bnm")
class BNMNewService {

    @Autowired
    HttpManaged httpManaged

    @Autowired
    LoggingManaged loggingManaged

    @Value("\${spring.httpservices.bnm.url}")
    String domain

    @GetMapping("/getListProvince")
    CommonResponse getListProvince() {
        try {
            String url = domain + "/GetListProvince"
            return execute(url, "GET", null)
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    @GetMapping("/getListConfig")
    CommonResponse getListConfig() {
        try {
            String url = domain + "/GetListConfig"
            return execute(url, "GET", null)
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    @GetMapping("/getListVendor")
    CommonResponse getListVendor() {
        try {
            String url = domain + "/GetListVendor"
            return execute(url, "GET", null)
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    @GetMapping("/getListService")
    CommonResponse getListService() {
        try {
            String url = domain + "/GetListService"
            return execute(url, "GET", null)
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    @GetMapping("/getListParamName")
    CommonResponse getListParamName() {
        try {
            String url = domain + "/GetListParamName"
            return execute(url, "GET", null)
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    @GetMapping("/getListScriptTempBNM")
    CommonResponse getListScriptTempBNM(@RequestParam(required = false) Integer province_id, @RequestParam(required = false) Integer vendor_id,
                    @RequestParam(required = false) Integer service_id, @RequestParam(required = false) Integer config_type) {
        try {
            String url = domain + "/GetListScriptTempBNM?"
            if (province_id && province_id != null) {
                url += ("&province_id=" + province_id)
            }
            if (vendor_id && vendor_id != null) {
                url += ("&vendor_id=" + vendor_id)
            }
            if (service_id && service_id != null) {
                url += ("&service_id=" + service_id)
            }
            if (config_type && config_type != null) {
                url += ("&config_type=" + config_type)
            }
            return execute(url.replace("?&", "?"), "GET", null)
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    @PostMapping("/addScriptTempBnm")
    CommonResponse addScriptTempBnm(@RequestBody Map<String, Object> body) {
        try {
            String url = domain + "/AddScriptTempBnm"
            return execute(url, "POST", body)
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    @PostMapping("/updateScriptTempBnm")
    CommonResponse updateScriptTempBnm(@RequestBody Map<String, Object> body) {
        try {
            String url = domain + "/UpdateScriptTempBnm"
            return execute(url, "POST", body)
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    @PostMapping("/updateScriptTempBnmV2")
    CommonResponse updateScriptTempBnmV2(@RequestBody Map<String, Object> body) {
        try {
            String url = domain + "/UpdateScriptTempBnmV2"
            return execute(url, "POST", body)
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    @PostMapping("/testScriptV2")
    CommonResponse testScriptV2(@RequestBody Map<String, Object> body) {
        try {
            String url = domain + "/TestScriptV2"
            return execute(url, "POST", body)
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    @PostMapping("/testScript")
    CommonResponse testScript(@RequestBody Map<String, Object> body) {
        try {
            String url = domain + "/testScript"
            return execute(url, "POST", body)
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    @PostMapping("/saveScript")
    CommonResponse saveScript(@RequestBody Map<String, Object> body) {
        try {
            String url = domain + "/saveScript"
            return execute(url, "POST", body)
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    @GetMapping("/getInfoScriptTempBnm")
    CommonResponse getInfoScriptTempBnm(@RequestParam Integer script_bnm_id) {
        try {
            String url = domain + "/GetInfoScriptTempBnm?script_bnm_id=" + script_bnm_id
            return execute(url, "GET", null)
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    @GetMapping("/getListLogScript")
    CommonResponse getListLogScript(@RequestParam Integer script_bnm_id, @RequestParam(required = false) String from_date,
                    @RequestParam(required = false) String to_date) {
        try {
            String url = domain + "/getListLogScript?script_bnm_id=" + script_bnm_id
            if (from_date && from_date != null && from_date != "") {
                url += ("&from_date=" + from_date)
            }
            if (to_date && to_date != null && to_date != "") {
                url += ("&to_date=" + to_date)
            }
            return execute(url, "GET", null)
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    @GetMapping("/getListLogUpdateScript")
    CommonResponse getListLogUpdateScript(@RequestParam Integer script_bnm_id, @RequestParam(required = false) String from_date,
                    @RequestParam(required = false) String to_date) {
        try {
            String url = domain + "/getListLogUpdateScript?script_bnm_id=" + script_bnm_id
            if (from_date && from_date != null && from_date != "") {
                url += ("&from_date=" + from_date)
            }
            if (to_date && to_date != null && to_date != "") {
                url += ("&to_date=" + to_date)
            }
            return execute(url, "GET", null)
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    @GetMapping("/getDetailLogScript")
    CommonResponse getDetailLogScript(@RequestParam Integer log_script_id) {
        try {
            String url = domain + "/getDetailLogScript?log_script_id=" + log_script_id
            return execute(url, "GET", null)
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    @GetMapping("/getListMessageQueue")
    CommonResponse getListMessageQueue(@RequestParam Integer province_id, @RequestParam Integer vendor_id, 
            @RequestParam Integer service_id, @RequestParam Integer config_type,
            @RequestParam String from_date, @RequestParam String to_date) {
        try {
            String url = domain + "/GetListMessageQueue?province_id=" + province_id + "&vendor_id=" + vendor_id + "&service_id=" + service_id + "&config_type=" + config_type + "&from_date=" + from_date + "&to_date=" + to_date
            return execute(url, "GET", null)
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    @GetMapping("/getDetailMessageQueue")
    CommonResponse getDetailMessageQueue(@RequestParam Integer message_id) {
        try {
            String url = domain + "/GetDetailMessageQueue?message_id=" + message_id
            return execute(url, "GET", null)
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    @PostMapping("/updateApprovedScript")
    CommonResponse updateApprovedScript(@RequestBody Map<String, Object> body) {
        try {
            String url = domain + "/UpdateApprovedScript"
            return execute(url, "POST", body)
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    CommonResponse execute(String url, String method, def body) {
        Map<String, String> headers = [:]
        headers.put("Content-Type", "application/json")
        String resp = ""
        if (method == "GET") {
            resp = httpManaged.get(url, headers)
        } else {
            resp = httpManaged.post([url: url], headers, new ObjectMapper().writeValueAsString(body))
        }
        return xlResp(resp)
    }

    CommonResponse xlResp(String resp) {
        def res = new JsonSlurper().parseText(resp)
        return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: res)
    } 

}
