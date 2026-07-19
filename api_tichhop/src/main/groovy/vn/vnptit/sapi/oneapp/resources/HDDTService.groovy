package vn.vnptit.sapi.oneapp.resources

import groovy.json.JsonBuilder
import groovy.json.JsonSlurper
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.enums.ParameterIn
import io.swagger.v3.oas.annotations.media.Schema
// import net.sf.saxon.s9api.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.web.bind.annotation.*
import vn.vnptit.sapi.oneapp.managed.HttpManaged
import vn.vnptit.sapi.oneapp.managed.LoggingManaged
import vn.vnptit.sapi.oneapp.managed.OracleManaged
import vn.vnptit.sapi.oneapp.models.CommonResponse
import vn.vnptit.sapi.oneapp.models.hddt.*

import javax.validation.Valid
import javax.ws.rs.Consumes
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType
import javax.xml.transform.stream.StreamSource

@RestController
@RequestMapping("hddt")
class HDDTService {
    @Autowired
    LoggingManaged loggingManaged
    @Autowired
    OracleManaged oracleManaged
    @Autowired
    HttpManaged httpManaged

    @Value("\${spring.httpservices.hddt.url}")
    String url

    @Value("\${spring.httpservices.hddt.username}")
    String username

    @Value("\${spring.httpservices.hddt.password}")
    String password

    String accessToken
    int retryReq = 0
    private final int MAX_REQ = 10


    @GetMapping("/LayDanhMucLoaiHoaDon")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    CommonResponse LayDanhMucLoaiHoaDon() {
        try {
            def res = get("category/gets", null)
            def ret = new JsonSlurper().parseText(res)
            if (ret instanceof String) {
                ret = new JsonSlurper().parseText(ret?.toString())
            }
            boolean success = ret["success"]?.asBoolean()
            String faultCode = ret["errorcode"]
            String faultString = ret["returnvalue"]
            return new CommonResponse(errorCode: (success ? 0 : 1), faultCode: faultCode, faultString: faultString, data: ret["data"])
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    } 

    @GetMapping("/LayDanhMucMauHoaDon")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    CommonResponse LayDanhMucMauHoaDon(@Parameter(in = ParameterIn.QUERY, description = "Id danh mục hóa đơn (format Guid)", required = true, schema = @Schema()) @Valid @RequestParam(value = "category_id", required = false) String category_id) {
        try {
            Map<String, String> params = [:]
            params.put("category_id", category_id)
            def res = get("invoicetemplate/gets", params)
            def ret = new JsonSlurper().parseText(res)
            if (ret instanceof String) {
                ret = new JsonSlurper().parseText(ret?.toString())
            }
            boolean success = ret["success"]?.asBoolean()
            String faultCode = ret["errorcode"]
            String faultString = ret["returnvalue"]
            return new CommonResponse(errorCode: (success ? 0 : 1), faultCode: faultCode, faultString: faultString, data: ret["data"])
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    @GetMapping("/LayChiTietMauHoaDon")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    CommonResponse LayChiTietMauHoaDon(@Parameter(in = ParameterIn.QUERY, description = "Id mẫu hóa đơn (format Guid)", required = true, schema = @Schema()) @Valid @RequestParam(value = "inv_id", required = false) String inv_id) {
        try {
            Map<String, String> params = [:]
            params.put("inv_id", inv_id)
            def res = get("invoicetemplate/detail", params)
            def ret = new JsonSlurper().parseText(res)
            if (ret instanceof String) {
                ret = new JsonSlurper().parseText(ret?.toString())
            }
            boolean success = ret["success"]?.asBoolean()
            String faultCode = ret["errorcode"]
            String faultString = ret["returnvalue"]
            return new CommonResponse(errorCode: (success ? 0 : 1), faultCode: faultCode, faultString: faultString, data: ret["data"])
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    @PostMapping("/CapNhatKhachhang")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    CommonResponse CapNhatKhachhang(@RequestBody CapNhatKhachhangRequest input) {
        try {
            String body = ""
            if (input != null) {
                body = new JsonBuilder(input)
            }
            def res = requestPost("customer/update", body)
            def ret = new JsonSlurper().parseText(res)
            if (ret instanceof String) {
                ret = new JsonSlurper().parseText(ret?.toString())
            }
            boolean success = ret["success"]?.asBoolean()
            String faultCode = ret["errorcode"]?.toString()
            String faultString = ret["returnvalue"]?.toString()
            return new CommonResponse(errorCode: (success ? 0 : 1), faultCode: faultCode, faultString: faultString, data: ret["data"])
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    @PostMapping("/LayDSMauHoaDonDaDangKy")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    CommonResponse LayDSMauHoaDonDaDangKy(@RequestBody LayDSMauHoaDonDaDangKyRequest input) {
        try {
            String body = ""
            if (input != null) {
                body = new JsonBuilder(input)
            }
            def res = requestPost("customer/gets-invtemp", body)
            def ret = new JsonSlurper().parseText(res)
            if (ret instanceof String) {
                ret = new JsonSlurper().parseText(ret?.toString())
            }
            boolean success = ret["success"]?.asBoolean()
            String faultCode = ret["errorcode"]?.toString()
            String faultString = ret["returnvalue"]?.toString()
            return new CommonResponse(errorCode: (success ? 0 : 1), faultCode: faultCode, faultString: faultString, data: ret["data"])
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    @GetMapping("/LaySoLuongHDDTDaDK")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    CommonResponse LaySoLuongHDDTDaDK(@Parameter(in = ParameterIn.QUERY, description = "Cus id", required = true, schema = @Schema()) @Valid @RequestParam(value = "cus_id", required = false) String cus_id,
                                      @Parameter(in = ParameterIn.QUERY, description = "Mã số thuế", required = true, schema = @Schema()) @Valid @RequestParam(value = "taxcode", required = false) String taxcode) {
        try {
            Map<String, String> params = [:]
            params.put("cus_id", cus_id)
            params.put("taxcode", taxcode)
            def res = get("customer/get-publish-invoice", params)
            def ret = new JsonSlurper().parseText(res)
            if (ret instanceof String) {
                ret = new JsonSlurper().parseText(ret?.toString())
            }
            boolean success = ret["success"]?.asBoolean()
            String faultCode = ret["errorcode"]
            String faultString = ret["returnvalue"]
            return new CommonResponse(errorCode: (success ? 0 : 1), faultCode: faultCode, faultString: faultString, data: ret["data"])
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    @PostMapping("/DangKyKhachHang")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    CommonResponse DangKyKhachHang(@RequestBody CapNhatKhachhangRequest input) {
        try {
            String body = ""
            if (input != null) {
                body = new JsonBuilder(input)
            }
            def res = requestPost("customer/add", body)
            def ret = new JsonSlurper().parseText(res)
            if (ret instanceof String) {
                ret = new JsonSlurper().parseText(ret?.toString())
            }
            boolean success = ret["success"]?.asBoolean()
            String faultCode = ret["errorcode"]?.toString()
            String faultString = ret["returnvalue"]?.toString()
            return new CommonResponse(errorCode: (success ? 0 : 1), faultCode: faultCode, faultString: faultString, data: ret["data"])
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    @PostMapping("/DangKyMauHoaDon")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    CommonResponse DangKyMauHoaDon(@RequestBody DangKyMauHoaDonRequest input) {
        try {
            String body = ""
            if (input != null) {
                body = new JsonBuilder(input)
            }
            def res = requestPost("register/invtemp", body)
            def ret = new JsonSlurper().parseText(res)
            if (ret instanceof String) {
                ret = new JsonSlurper().parseText(ret?.toString())
            }
            boolean success = ret["success"]?.asBoolean()
            String faultCode = ret["errorcode"]?.toString()
            String faultString = ret["returnvalue"]?.toString()
            return new CommonResponse(errorCode: (success ? 0 : 1), faultCode: faultCode, faultString: faultString, data: ret["data"])
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }
    @PostMapping("/DangKyMauHoaDon/BienLaiDienTu")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    CommonResponse DangKyMauHoaDonBienLaiDienTu(@RequestBody DangKyMauHoaDonRequest input) {
        try {
            String body = ""
            if (input != null) {
                body = new JsonBuilder(input)
            }
            def res = requestPost("customerrecipt/registerinvtemp", body)
            def ret = new JsonSlurper().parseText(res)
            if (ret instanceof String) {
                ret = new JsonSlurper().parseText(ret?.toString())
            }
            boolean success = ret["success"]?.asBoolean()
            String faultCode = ret["errorcode"]?.toString()
            String faultString = ret["returnvalue"]?.toString()
            return new CommonResponse(errorCode: (success ? 0 : 1), faultCode: faultCode, faultString: faultString, data: ret["data"])
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    @PostMapping("/DangKyMauHoaDonUpload")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    CommonResponse DangKyMauHoaDonUpload(@RequestBody DangKyMauHoaDonUploadRequest input) {
        try {
            String body = ""
            if (input != null) {
                body = new JsonBuilder(input)
            }
            def res = requestPost("register/invtemp/upload", body)
            def ret = new JsonSlurper().parseText(res)
            if (ret instanceof String) {
                ret = new JsonSlurper().parseText(ret?.toString())
            }
            boolean success = ret["success"]?.asBoolean()
            String faultCode = ret["errorcode"]?.toString()
            String faultString = ret["returnvalue"]?.toString()
            return new CommonResponse(errorCode: (success ? 0 : 1), faultCode: faultCode, faultString: faultString, data: ret["data"])
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    @PostMapping("/UpdateMauHoaDon")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    CommonResponse UpdateMauHoaDon(@RequestBody UpdateMauHoaDonRequest input) {
        try {
            String body = ""
            if (input != null) {
                body = new JsonBuilder(input)
            }
            def res = requestPost("register/invtemp/update", body)
            def ret = new JsonSlurper().parseText(res)
            if (ret instanceof String) {
                ret = new JsonSlurper().parseText(ret?.toString())
            }
            boolean success = ret["success"]?.asBoolean()
            String faultCode = ret["errorcode"]?.toString()
            String faultString = ret["returnvalue"]?.toString()
            return new CommonResponse(errorCode: (success ? 0 : 1), faultCode: faultCode, faultString: faultString, data: ret["data"])
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    @PostMapping("/XoaMauHoaDon")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    CommonResponse XoaMauHoaDon(@RequestBody DangKyMauHoaDonRequest input) {
        try {
            String body = ""
            if (input != null) {
                body = new JsonBuilder(input)
            }
            def res = requestPost("register/invtemp/delete", body)
            def ret = new JsonSlurper().parseText(res)
            if (ret instanceof String) {
                ret = new JsonSlurper().parseText(ret?.toString())
            }
            boolean success = ret["success"]?.asBoolean()
            String faultCode = ret["errorcode"]?.toString()
            String faultString = ret["returnvalue"]?.toString()
            return new CommonResponse(errorCode: (success ? 0 : 1), faultCode: faultCode, faultString: faultString, data: ret["data"])
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }
    @PostMapping("/XoaMauHoaDon/BienLaiDienTu")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    CommonResponse XoaMauHoaDonBienLaiDienTu(@RequestBody DangKyMauHoaDonRequest input) {
        try {
            String body = ""
            if (input != null) {
                body = new JsonBuilder(input)
            }
            def res = requestPost("customerrecipt/invtemp/delete", body)
            def ret = new JsonSlurper().parseText(res)
            if (ret instanceof String) {
                ret = new JsonSlurper().parseText(ret?.toString())
            }
            boolean success = ret["success"]?.asBoolean()
            String faultCode = ret["errorcode"]?.toString()
            String faultString = ret["returnvalue"]?.toString()
            return new CommonResponse(errorCode: (success ? 0 : 1), faultCode: faultCode, faultString: faultString, data: ret["data"])
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    @GetMapping("/HTMLMauHoaDon")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    CommonResponse HTMLMauHoaDon(@Parameter(in = ParameterIn.QUERY, description = "Id mẫu hóa đơn (format Guid)", required = true, schema = @Schema()) @Valid @RequestParam(value = "inv_id", required = false) String inv_id) {
        try {
            Map<String, String> params = [:]
            params.put("inv_id", inv_id)
            def res = get("invoicetemplate/preview", params)
            def ret = new JsonSlurper().parseText(res)
            if (ret instanceof String) {
                ret = new JsonSlurper().parseText(ret?.toString())
            }
            boolean success = ret["success"]?.asBoolean()
            String faultCode = ret["errorcode"]
            String faultString = ret["returnvalue"]
            return new CommonResponse(errorCode: (success ? 0 : 1), faultCode: faultCode, faultString: faultString, data: ret["data"])
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    // @GetMapping("/HTMLMauHoaDonPreview")
    // @Consumes([MediaType.APPLICATION_JSON])
    // @Produces([MediaType.APPLICATION_JSON])
    // String HTMLMauHoaDonPreview(@Parameter(in = ParameterIn.QUERY, description = "Id mẫu hóa đơn (format Guid)", required = true, schema = @Schema()) @Valid @RequestParam(value = "inv_id", required = false) String inv_id) {
    //     String result = "Có lỗi xảy ra"
    //     try {
    //         Map<String, String> params = [:]
    //         params.put("inv_id", inv_id)
    //         def res = get("invoicetemplate/detail", params)
    //         def ret = new JsonSlurper().parseText(res)
    //         if (ret instanceof String) {
    //             ret = new JsonSlurper().parseText(ret?.toString())
    //         }

    //         boolean success = ret["success"]?.asBoolean()
    //         if (success) {
    //             result = TransformXMLToHTML(ret["data"]["XmlFile"], ret["data"]["XsltFile"])
    //         }    
    //     } catch (Exception e) {
    //     }
    //     return result
    // }

    // String TransformXMLToHTML(String inputXml, String xsltString) {
    //     String result = ""
    //     try {
    //         Processor proc = new Processor(false)
    //         XsltCompiler comp = proc.newXsltCompiler()
    //         XsltExecutable exp = comp.compile(new StreamSource(new StringReader(xsltString)))
    //         XdmNode source = proc.newDocumentBuilder().build(new StreamSource(new StringReader(inputXml)))
    //         StringWriter writer = new StringWriter()
    //         Serializer out = new Serializer(proc)
    //         out.setOutputProperty(Serializer.Property.METHOD, "html")
    //         out.setOutputProperty(Serializer.Property.INDENT, "no")
    //         out.setOutputWriter(writer)
    //         XsltTransformer trans = exp.load()
    //         trans.setInitialContextNode(source)
    //         trans.setDestination(out)
    //         trans.transform()
    //         result = writer.toString()
    //     } catch (Exception ex) {
    //         result = ex.message
    //     }
    //     return result
    // }

    @PostMapping("/KhoaDichVuHDDT")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    CommonResponse KhoaDichVuHDDT(@RequestBody KhoaDichVuHDDTRequest input) {
        try {
            String body = ""
            if (input != null) {
                body = new JsonBuilder(input)
            }
            def res = requestPost("customer/system/lock", body)
            def ret = new JsonSlurper().parseText(res)
            if (ret instanceof String) {
                ret = new JsonSlurper().parseText(ret?.toString())
            }
            boolean success = ret["success"]?.asBoolean()
            String faultCode = ret["errorcode"]?.toString()
            String faultString = ret["returnvalue"]?.toString()
            return new CommonResponse(errorCode: (success ? 0 : 1), faultCode: faultCode, faultString: faultString, data: ret["data"])
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    @PostMapping("/MoDichVuHDDT")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    CommonResponse MoDichVuHDDT(@RequestBody KhoaDichVuHDDTRequest input) {
        try {
            String body = ""
            if (input != null) {
                body = new JsonBuilder(input)
            }
            def res = requestPost("customer/system/unlock", body)
            def ret = new JsonSlurper().parseText(res)
            if (ret instanceof String) {
                ret = new JsonSlurper().parseText(ret?.toString())
            }
            boolean success = ret["success"]?.asBoolean()
            String faultCode = ret["errorcode"]?.toString()
            String faultString = ret["returnvalue"]?.toString()
            return new CommonResponse(errorCode: (success ? 0 : 1), faultCode: faultCode, faultString: faultString, data: ret["data"])
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    @PostMapping("/TamNgungDichVuHDDT")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    CommonResponse TamNgungDichVuHDDT(@RequestBody KhoaDichVuHDDTRequest input) {
        try {
            String body = ""
            if (input != null) {
                body = new JsonBuilder(input)
            }
            def res = requestPost("customer/system/cancel", body)
            def ret = new JsonSlurper().parseText(res)
            if (ret instanceof String) {
                ret = new JsonSlurper().parseText(ret?.toString())
            }
            boolean success = ret["success"]?.asBoolean()
            String faultCode = ret["errorcode"]?.toString()
            String faultString = ret["returnvalue"]?.toString()
            return new CommonResponse(errorCode: (success ? 0 : 1), faultCode: faultCode, faultString: faultString, data: ret["data"])
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    @PostMapping("/KhoiPhucDichVuHDDT")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    CommonResponse KhoiPhucDichVuHDDT(@RequestBody KhoaDichVuHDDTRequest input) {
        try {
            String body = ""
            if (input != null) {
                body = new JsonBuilder(input)
            }
            def res = requestPost("customer/system/uncancel", body)
            def ret = new JsonSlurper().parseText(res)
            if (ret instanceof String) {
                ret = new JsonSlurper().parseText(ret?.toString())
            }
            boolean success = ret["success"]?.asBoolean()
            String faultCode = ret["errorcode"]?.toString()
            String faultString = ret["returnvalue"]?.toString()
            return new CommonResponse(errorCode: (success ? 0 : 1), faultCode: faultCode, faultString: faultString, data: ret["data"])
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    @GetMapping("/LayThongTinKHHDDT")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    CommonResponse LayThongTinKHHDDT(@Parameter(in = ParameterIn.QUERY, description = "Mã số thuế", required = true, schema = @Schema()) @Valid @RequestParam(value = "taxcode", required = false) String taxcode) {
        try {
            Map<String, String> params = [:]
            params.put("taxcode", taxcode)
            def res = get("customer/get-by-taxcode", params)
            def ret = new JsonSlurper().parseText(res)
            return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: ret)
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    @PostMapping("/CapNhatSerial")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    CommonResponse CapNhatSerial(@RequestBody CapNhatSerialRequest input) {
        try {
            String body = ""
            if (input != null) {
                body = new JsonBuilder(input)
            }
            def res = requestPost("customer/update-serial", body)
            def ret = new JsonSlurper().parseText(res)
            if (ret instanceof String) {
                ret = new JsonSlurper().parseText(ret?.toString())
            }
            boolean success = ret["success"]?.asBoolean()
            String faultCode = ret["errorcode"]?.toString()
            String faultString = ret["returnvalue"]?.toString()
            return new CommonResponse(errorCode: (success ? 0 : 1), faultCode: faultCode, faultString: faultString, data: ret["data"])
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    @PostMapping("/KhoiTaoDemo")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    CommonResponse KhoiTaoDemo(@RequestBody KhoaDichVuHDDTRequest input) {
        try {
            String body = "" 
            if (input != null) {
                body = new JsonBuilder(input)
            }
            def res = requestPost("customer/initsystem/demo", body)
            def ret = new JsonSlurper().parseText(res)
            if (ret instanceof String) {
                ret = new JsonSlurper().parseText(ret?.toString())
            }
            boolean success = ret["success"]?.asBoolean()
            String faultCode = ret["errorcode"]?.toString()
            String faultString = ret["returnvalue"]?.toString()
            return new CommonResponse(errorCode: (success ? 0 : 1), faultCode: faultCode, faultString: faultString, data: ret["data"])
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    @PostMapping("/KhoiTaoHeThongThat")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    CommonResponse KhoiTaoHeThongThat(@RequestBody KhoiTaoHeThongThatRequest input) {
        try {
            String body = ""
            if (input != null) {
                body = new JsonBuilder(input)
            }
            def res = requestPost("customer/initsystem/real", body)
            def ret = new JsonSlurper().parseText(res)
            if (ret instanceof String) {
                ret = new JsonSlurper().parseText(ret?.toString())
            }
            boolean success = ret["success"]?.asBoolean()
            String faultCode = ret["errorcode"]?.toString()
            String faultString = ret["returnvalue"]?.toString()
            return new CommonResponse(errorCode: (success ? 0 : 1), faultCode: faultCode, faultString: faultString, data: ret["data"])
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    @PostMapping("/MuaThemHoaDon")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    CommonResponse MuaThemHoaDon(@RequestBody KhoiTaoHeThongThatRequest input) {
        try {
            String body = ""
            if (input != null) {
                body = new JsonBuilder(input)
            }
            def res = requestPost("register/invtemp/addpackage", body)
            def ret = new JsonSlurper().parseText(res)
            if (ret instanceof String) {
                ret = new JsonSlurper().parseText(ret?.toString())
            }
            boolean success = ret["success"]?.asBoolean()
            String faultCode = ret["errorcode"]?.toString()
            String faultString = ret["returnvalue"]?.toString()
            return new CommonResponse(errorCode: (success ? 0 : 1), faultCode: faultCode, faultString: faultString, data: ret["data"])
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    @PostMapping("/resetParam")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    CommonResponse resetParam() {
        try {
            accessToken = ""
            retryReq = 0
            return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: "ok")
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    // @PostMapping("/getAccessToken")
    // @Consumes([MediaType.APPLICATION_JSON])
    // @Produces([MediaType.APPLICATION_JSON])
    // CommonResponse getAccessToken_v2() {
    //     try {
    //         return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: getAccessToken())
    //     } catch (Exception e) {
    //         return CommonResponse.resolveException(e, loggingManaged)
    //     }
    // }

    private String getAccessToken() {
        // if (accessToken != null && !accessToken?.isEmpty()) {
        //     return null
        // }
        String path = "auth/token/create"
        String body = """{
                      "username": "$username",
                      "password": "$password"
                    }"""
        String ret = post(path, body)
        def response = new JsonSlurper().parseText(ret)
        if (response != null && response["success"]?.asBoolean()) {
            accessToken = response["data"]["access_token"]
        }
        return ret
    }

    String get(String path, Map<String, String> param) {
        if (accessToken == null || accessToken?.isEmpty()) {
            String response = getAccessToken()
            if (accessToken == null || accessToken?.isEmpty()) {
                return response
            }
        }
        String ls
        if (param != null && param?.size() > 0) {
            ls = param.collect {key, value -> key + "=" + value}.join("&")
        }
        Map<String, String> headers = [:]
        String url = url + path + (ls != null ? ("?" + ls) : "")
        headers.put("Authorization", "Bearer " + accessToken)
        headers.put("Content-Type", "application/json")
        String resp = httpManaged.get(url, headers)

        def ret = new JsonSlurper().parseText(resp)
        if (ret instanceof String) {
            ret = new JsonSlurper().parseText(ret?.toString())
        }
        boolean success = ret["success"]?.asBoolean()
        String faultCode = ret["errorcode"]?.toString()
        if (!success) {
            // Kiểm tra token có hết hạn hay không? Nếu hết hạn thì lấy lại thông tin token
            if (retryReq < MAX_REQ && (faultCode?.trim()?.toUpperCase() == "ERR:1.5" || faultCode?.trim()?.toUpperCase() == "ERR:1.6")) {
                getAccessToken()
                retryReq++
                if (accessToken != null && !accessToken?.isEmpty()) {
                    // lấy lại dữ liệu, sau khi lấy lại thông tin token
                    return get(path, param)
                }
            }
        } else {
            retryReq = 0
        }
        return resp
    }

    String requestPost(String path, String body) {
        if (accessToken == null || accessToken?.isEmpty()) {
            String response = getAccessToken()
            if (accessToken == null || accessToken?.isEmpty()) {
                return response
            }
        }
        String url = url + path
        Map<String, String> headers = [:]
        headers.put("Authorization", "Bearer " + accessToken)
        headers.put("Content-Type", "application/json")
        String resp = httpManaged.post(["url": url], headers, body)

        def ret = new JsonSlurper().parseText(resp)
        if (ret instanceof String) {
            ret = new JsonSlurper().parseText(ret?.toString())
        }
        boolean success = ret["success"]?.asBoolean()
        String faultCode = ret["errorcode"]?.toString()
        if (!success) {
            // Kiểm tra token có hết hạn hay không? Nếu hết hạn thì lấy lại thông tin token
            if (retryReq < MAX_REQ && (faultCode?.trim()?.toUpperCase() == "ERR:1.5" || faultCode?.trim()?.toUpperCase() == "ERR:1.6")) {
                getAccessToken()
                retryReq++
                if (accessToken != null && !accessToken?.isEmpty()) {
                    // lấy lại dữ liệu, sau khi lấy lại thông tin token
                    return requestPost(path, body)
                }
            }
        } else {
            retryReq = 0
        }
        return resp
    }

    String post(String path, String body) {
        String url = url + path
        Map<String, String> headers = [:]
        headers.put("Content-Type", "application/json")
        return httpManaged.post(["url": url], headers, body)
    }
}
