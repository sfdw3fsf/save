package vn.vnptit.sapi.oneapp.resources.subman


import com.google.gson.Gson
import com.google.gson.GsonBuilder
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.beans.factory.annotation.Value
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.context.request.NativeWebRequest
import vn.vnptit.sapi.oneapp.managed.HttpManaged
import vn.vnptit.sapi.oneapp.managed.LoggingManaged
import vn.vnptit.sapi.oneapp.managed.OracleManaged
import vn.vnptit.sapi.oneapp.models.CommonResponse
import vn.vnptit.sapi.oneapp.models.subman.DangKyDichVu
import vn.vnptit.sapi.oneapp.models.subman.HuyDichVu
import vn.vnptit.sapi.oneapp.models.subman.KhoaGoiDi
import vn.vnptit.sapi.oneapp.models.subman.KiemTraThongTin
import vn.vnptit.sapi.oneapp.models.subman.TaoMayMoi
import vn.vnptit.sapi.oneapp.models.subman.XoaMay
import vn.vnptit.sapi.oneapp.models.subman.ThayDoiCauHinh
import vn.vnptit.sapi.oneapp.models.subman.ThayDoiCauHinhResp 
import vn.vnptit.sapi.oneapp.models.visacom.ChangeMacAddressReg
import vn.vnptit.sapi.oneapp.util.FakeSuccessCommonResponse
import vn.vnptit.sapi.oneapp.util.GenericApi
import ws.client.subman.EwsdproLocator
import ws.client.subman.EwsdproSoap12Stub
import ws.client.subman.SubInfo

import javax.net.ssl.KeyManager
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager
import javax.validation.Valid
import javax.ws.rs.Consumes
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType
import javax.xml.rpc.holders.StringHolder
import javax.xml.rpc.holders.BooleanHolder
import java.security.SecureRandom
import java.security.cert.CertificateException
import java.security.cert.X509Certificate

@RestController
@RequestMapping("subman")
class SubmanService {
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

    @Autowired
    @Qualifier("skey")
    String skey

    static Map<String, String> cachedKey = null

    void loadSkeysMap() {
        cachedKey = [:]
        skey.split(',').each {
            String[] keys = it.split(":", 2)
            cachedKey.put(keys[0], keys[1])
        }
    }

    String getSKey(Map<String, Object> session) {
        String phanvungId = "0"
        try {
            phanvungId = session[GenericApi.ID_TINHTHANH]
        } catch (Exception e) {
            // ignored
        }

        if (cachedKey == null) {
            synchronized (skey) {
                if (cachedKey == null) loadSkeysMap()
            }
        }

        if (cachedKey) {
            if (cachedKey.containsKey(phanvungId)) {
                return cachedKey.get(phanvungId)
            } else {
                return cachedKey.get("0")
            }
        }
    }

    @Autowired
    @Qualifier("submanServicesStubThreadLocal")
    ThreadLocal<EwsdproSoap12Stub> servicesStubThreadLocal

    @PostMapping("/kiemTraThongTin")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    CommonResponse kiemTraThongTin(@Valid @RequestBody KiemTraThongTin body) {
        EwsdproSoap12Stub service = servicesStubThreadLocal.get()
        try {
            def session = genericApi.getSessionData(request)
            String skey = getSKey(session)
            SubInfo data = service.kiemTraThongTin(skey, body.tongDai, body.soMay)
            return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: data)
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    @PostMapping("/taoMayMoi")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    @FakeSuccessCommonResponse
    CommonResponse taoMayMoi(@Valid @RequestBody TaoMayMoi body) {
        EwsdproSoap12Stub service = servicesStubThreadLocal.get()
        try {
            def session = genericApi.getSessionData(request)
            String skey = getSKey(session)
            StringHolder msg = new StringHolder()
            boolean data = service.taoMayMoi(skey, body.tongDai, body.soMay, body.viTri, msg)
            return new CommonResponse(errorCode: 0, faultCode: "", faultString: msg.value, data: data)
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    @PostMapping("/dangKyDichVu")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    @FakeSuccessCommonResponse
    CommonResponse dangKyDichVu(@Valid @RequestBody DangKyDichVu body) {
        EwsdproSoap12Stub service = servicesStubThreadLocal.get()
        try {
            def session = genericApi.getSessionData(request)
            String skey = getSKey(session)
            StringHolder msg = new StringHolder()
            boolean data = service.dangKyDichVu(skey, body.tongDai, body.soMay, body.viTri, body.maDichVu, body.thamSo, msg)
            return new CommonResponse(errorCode: 0, faultCode: "", faultString: msg.value, data: data)
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    @PostMapping("/huyDichVu")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    @FakeSuccessCommonResponse
    CommonResponse huyDichVu(@Valid @RequestBody HuyDichVu body) {
        EwsdproSoap12Stub service = servicesStubThreadLocal.get()
        try {
            def session = genericApi.getSessionData(request)
            String skey = getSKey(session)
            StringHolder msg = new StringHolder()
            boolean data = service.huyDichVu(skey, body.tongDai, body.soMay, body.viTri, body.maDichVu, body.thamSo, msg)
            return new CommonResponse(errorCode: 0, faultCode: "", faultString: msg.value, data: data)
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    @PostMapping("/khoaGoiDi")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    @FakeSuccessCommonResponse
    CommonResponse khoaGoiDi(@Valid @RequestBody KhoaGoiDi body) {
        EwsdproSoap12Stub service = servicesStubThreadLocal.get()
        try {
            def session = genericApi.getSessionData(request)
            String skey = getSKey(session)
            StringHolder msg = new StringHolder()
            boolean data = service.khoaGoiDi(skey, body.tongDai, body.soMay, body.viTri, msg)
            return new CommonResponse(errorCode: 0, faultCode: "", faultString: msg.value, data: data)
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    @PostMapping("/moGoiDi")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    @FakeSuccessCommonResponse
    CommonResponse moGoiDi(@Valid @RequestBody KhoaGoiDi body) {
        EwsdproSoap12Stub service = servicesStubThreadLocal.get()
        try {
            def session = genericApi.getSessionData(request)
            String skey = getSKey(session)
            StringHolder msg = new StringHolder()
            boolean data = service.moGoiDi(skey, body.tongDai, body.soMay, body.viTri, msg)
            return new CommonResponse(errorCode: 0, faultCode: "", faultString: msg.value, data: data)
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    @PostMapping("/khoaCuoc")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    @FakeSuccessCommonResponse
    CommonResponse khoaCuoc(@Valid @RequestBody KhoaGoiDi body) {
        EwsdproSoap12Stub service = servicesStubThreadLocal.get()
        try {
            def session = genericApi.getSessionData(request)
            String skey = getSKey(session)
            StringHolder msg = new StringHolder()
            boolean data = service.khoaCuoc(skey, body.tongDai, body.soMay, body.viTri, msg)
            return new CommonResponse(errorCode: 0, faultCode: "", faultString: msg.value, data: data)
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    @PostMapping("/moCuoc")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    @FakeSuccessCommonResponse
    CommonResponse moCuoc(@Valid @RequestBody KhoaGoiDi body) {
        EwsdproSoap12Stub service = servicesStubThreadLocal.get()
        try {
            def session = genericApi.getSessionData(request)
            String skey = getSKey(session)
            StringHolder msg = new StringHolder()
            boolean data = service.moCuoc(skey, body.tongDai, body.soMay, body.viTri, msg)
            return new CommonResponse(errorCode: 0, faultCode: "", faultString: msg.value, data: data)
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    @PostMapping("/xoaMay")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    @FakeSuccessCommonResponse
    CommonResponse xoaMay(@Valid @RequestBody XoaMay body) {
        EwsdproSoap12Stub service = servicesStubThreadLocal.get()
        try {
            def session = genericApi.getSessionData(request)
            String skey = getSKey(session)
            StringHolder msg = new StringHolder()
            boolean data = service.xoaMay(skey, body.tongDai, body.soMay, body.viTri, msg)
            return new CommonResponse(errorCode: 0, faultCode: "", faultString: msg.value, data: data)
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    @PostMapping("/thayDoiCauHinh")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    @FakeSuccessCommonResponse
    CommonResponse thayDoiCauHinh(@Valid @RequestBody ThayDoiCauHinh body) {
        EwsdproSoap12Stub service = servicesStubThreadLocal.get()
        try {
            def session = genericApi.getSessionData(request)
            String skey = getSKey(session)
            BooleanHolder serviceLoss = new BooleanHolder()
            StringHolder msg = new StringHolder()
            boolean data = service.thayDoiCauHinh(skey, body.tongDaiCu, body.soMayCu, body.tongDaiMoi, body.soMayMoi, body.viTriMoi, serviceLoss, msg)
            ThayDoiCauHinhResp resp = new ThayDoiCauHinhResp()
            resp.msg = msg.value
            resp.serviceLoss = serviceLoss.value
            return new CommonResponse(errorCode: 0, faultCode: "", faultString: new Gson().toJson(resp), data: data)
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }
}
