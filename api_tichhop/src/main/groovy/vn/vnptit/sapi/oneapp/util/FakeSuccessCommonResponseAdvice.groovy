package vn.vnptit.sapi.oneapp.util

import org.apache.commons.lang3.ClassUtils
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.reflect.MethodSignature
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Component
import vn.vnptit.sapi.oneapp.models.CommonResponse

import java.lang.reflect.Method
import java.lang.reflect.Parameter
import java.util.concurrent.atomic.AtomicBoolean

@Aspect
@Component
class FakeSuccessCommonResponseAdvice {

    @Autowired
    @Qualifier("fakeSuccessCommonResponseValue")
    AtomicBoolean fakeSuccessCommonResponseValue

    @Around("@annotation(vn.vnptit.sapi.oneapp.util.FakeSuccessCommonResponse)")
    Object logging(ProceedingJoinPoint pjp) {
        Method method = MethodSignature.class.cast(pjp.signature).method
        Object[] args = pjp.getArgs()
        Parameter[] parammeters = method.getParameters()
        Map<String, Object> inputLogData = new HashMap<>()
        inputLogData.put("action", method.getName())
        inputLogData.put("clazz", pjp.getThis().getClass().getSimpleName())

        args.eachWithIndex { arg, idx ->
            if (arg.getClass().getName().startsWith("vn.vnptit.sapi.oneapp.model")) {
                inputLogData.put(parammeters[idx].getName(), arg.toString())
            } else if (ClassUtils.isPrimitiveOrWrapper(arg.getClass())) {
                inputLogData.put(parammeters[idx].getName(), arg.toString())
            }
        }
        if (fakeSuccessCommonResponseValue.get()) {
            List<String> lstIMSAPI = new ArrayList<String>(Arrays.asList("traCuuThongTinMTAS",
                    "sps_service_ims",
                    "sps_service_ims_deactive",
                    "ts_mtas_delete_subscriber",
                    "ts_ism_delete_subscriber",
                    "ts_ipw_delete_subscriber",
                    "ts_mtas_modify_subscriber",
                    "ts_ism_modify_subscriber",
                    "change_location",
                    "traCuuThongTinMTAS_test",
                    "get_by_prn_only",
                    "get_by_prn_sva",
                    "get_by_prn_sva_res",
                    "get_by_prn_sva_abbr",
                    "get_by_prn_sva_cfu",
                    "get_by_prn_sva_cfb",
                    "get_by_prn_sva_cfnry",
                    "get_by_prn_sva_sub",
                    "create_siptrunk_mtas",
                    "get_by_prn_subtype_extend",
                    "cancel_siptrunk_mtas",
                    "get_by_prn_routing_table",
                    "get_by_prn_rule_sets",
                    "aaranet_create_profile",
                    "aaranet_delete_profile",
                    "aaranet_create_account",
                    "aaranet_delete_account",
                    "aaranet_mapping_profile_account",
                    "aaranet_add_address",
                    "aaranet_remove_address",
                    "aaranet_tamngung",
                    "aaranet_khoa_goidi_quocte",
                    "aaranet_khoa_goi_acc_rulesets",
                    "aaranet_chan_goiden_khongmongmuon",
                    "aaranet_mo_chan_goiden_khongmongmuon_theo_tb",
                    "aaranet_thaydoi_sokenh_cuocgoi_dongthoi",
                    "aaranet_dangky_hanmuccuoc",
                    "aaranet_dieuchinh_hanmuccuoc",
                    "delete_siptrunk_ism"));
            if (lstIMSAPI.contains(method.getName())) {
                List resp = new ArrayList()
                def resp1 = [:]
                resp1.Status = 9
                resp1.StatusMessage = "success"
                resp.add(resp1)
                return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: resp)
            }
            return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: "ok")
        } else {
            Object out = pjp.proceed()

            out
        }
    }
}
