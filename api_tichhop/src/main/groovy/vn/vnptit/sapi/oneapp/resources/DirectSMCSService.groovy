package vn.vnptit.sapi.oneapp.resources

import com.google.gson.Gson
import groovy.json.JsonSlurper
import groovy.util.slurpersupport.GPathResult
import io.swagger.model.BaseResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.context.request.NativeWebRequest
import vn.vnptit.sapi.oneapp.managed.FtpsManaged
import vn.vnptit.sapi.oneapp.managed.HttpManaged
import vn.vnptit.sapi.oneapp.managed.LoggingManaged
import vn.vnptit.sapi.oneapp.managed.ObjectStoragesManaged
import vn.vnptit.sapi.oneapp.models.CommonResponse
import vn.vnptit.sapi.oneapp.models.smcs.request.SMCS_CreateAgent_Req
import vn.vnptit.sapi.oneapp.models.smcs.request.SMCS_GetListAgentCareRequest_Req
import vn.vnptit.sapi.oneapp.models.smcs.request.SMCS_UpdateAgentCareRequest_Req
import vn.vnptit.sapi.oneapp.models.smcs.request.SMCS_UpdateAgent_Req
import vn.vnptit.sapi.oneapp.util.BaseResponseBuilder
import vn.vnptit.sapi.oneapp.util.GenericApi
import vn.vnptit.sapi.oneapp.util.LoggingInOut

@RestController
@RequestMapping("smcs")
class DirectSMCSService {

    @Autowired
    HttpManaged httpManaged

    @Autowired
    LoggingManaged loggingManaged

    @Autowired
    GenericApi genericApi

    @Autowired
    NativeWebRequest request

    @Value("\${spring.httpservices.smcs.durl}")
    String smcsUrl

    @Value("\${spring.httpservices.smcs.dusr}")
    String smcsUsr

    @Value("\${spring.httpservices.smcs.dpwd}")
    String smcsPwd

    @Value("\${spring.ftp.vnp.host}")
    String ftpHost
    @Value("\${spring.ftp.vnp.port}")
    int ftpPort
    @Value("\${spring.ftp.vnp.username}")
    String ftpUsername
    @Value("\${spring.ftp.vnp.password}")
    String ftpPassword
    @Value("\${spring.ftp.vnp.folder}")
    String ftpFolder

    @Value("\${spring.httpservices.smcs.rest_url}")
    String smcsRestUrl

    @Value("\${spring.httpservices.smcs.rest_session}")
    String smcsRestSession

    @Autowired
    ObjectStoragesManaged objectStoragesManaged

    @Autowired
    FtpsManaged ftpsManaged

    CommonResponse getToken() {
        try {
            def env = post1(smcsUrl,
                    """<soapenv:Envelope xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:urn="urn:VNPServices">
                               <soapenv:Header/>
                               <soapenv:Body>
                                  <urn:login soapenv:encodingStyle="http://schemas.xmlsoap.org/soap/encoding/">
                                     <login xsi:type="xsd:string">$smcsUsr</login>
                                     <password xsi:type="xsd:string">$smcsPwd</password>
                                  </urn:login>
                               </soapenv:Body>
                            </soapenv:Envelope>""")

            def fault = env.Body.Fault
            if (!fault.isEmpty()) {
                return new CommonResponse(errorCode: 1, faultCode: fault.Code.text(), faultString: fault.Reason.text(), data: "Lỗi")
            }

            String description = env.Body.loginResponse.description.text()
            String responseStatus = env.Body.loginResponse.responseStatus.text()
            String sessionID = env.Body.loginResponse.sessionID.text()
            if (description == "OK" && responseStatus == "1" && sessionID != null && sessionID != "") {
                return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: sessionID)
            } else {
                return new CommonResponse(errorCode: 1, faultCode: responseStatus, faultString: description, data: sessionID)
            }
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    CommonResponse keepAlive(String sessionIDK) {
        try {
            def env = post1(smcsUrl,
                    """<soapenv:Envelope xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:urn="urn:VNPServices">
                               <soapenv:Header/>
                               <soapenv:Body>
                                  <urn:keepAlive soapenv:encodingStyle="http://schemas.xmlsoap.org/soap/encoding/">
                                     <sessionID xsi:type="xsd:string">$sessionIDK</sessionID>
                                  </urn:keepAlive>
                               </soapenv:Body>
                            </soapenv:Envelope>""")

            def fault = env.Body.Fault
            if (!fault.isEmpty()) {
                return new CommonResponse(errorCode: 1, faultCode: fault.Code.text(), faultString: fault.Reason.text(), data: "Lỗi")
            }

            String description = env.Body.keepAliveResponse.description.text()
            String responseStatus = env.Body.keepAliveResponse.responseStatus.text()
            String sessionID = env.Body.keepAliveResponse.sessionID.text()
            if (responseStatus == "1") {
                return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: sessionID)
            } else {
                return new CommonResponse(errorCode: 1, faultCode: responseStatus, faultString: description, data: sessionID)
            }
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    String sessionId = "123456"

    String getSessionId() {
        CommonResponse result = keepAlive(sessionId)
        if (result["errorCode"].toString() != "0") {
            CommonResponse result2 = getToken()
            if (result2["errorCode"].toString() == "0") {
                sessionId = result2["data"].toString()
            }
        }
        sessionId
    }

    @GetMapping("/getApDomainList")
    ResponseEntity<BaseResponse<?>> getApDomainList(@RequestParam String type) {
        try {
            def env = post1(smcsUrl,
                    """<soapenv:Envelope xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:urn="urn:VNPServices">
                                <soapenv:Header/>
                                <soapenv:Body>
                                    <urn:getApDomainList soapenv:encodingStyle="http://schemas.xmlsoap.org/soap/encoding/">
                                        <sessionID xsi:type="xsd:string">${getSessionId()}</sessionID>
                                        <p_Type xsi:type="xsd:string">$type</p_Type>
                                    </urn:getApDomainList>
                                </soapenv:Body>
                            </soapenv:Envelope>""")

            String error_code = env.Body.getApDomainListResponse.p_errCode.text()
            String responseStatus = env.Body.getApDomainListResponse.responseStatus.text()
            String errContent = env.Body.getApDomainListResponse.p_errContent.text()
            String description = env.Body.getApDomainListResponse.description.text()
            if (responseStatus == "1" && error_code == "0") {
                def items = env.Body.getApDomainListResponse.p_result.item
                def itemList = items.collect {
                    def parts = it.text().split("\\|")
                    [code: parts[0], name: parts[1]]
                }

                return ResponseEntity.ok().body(BaseResponseBuilder.of(itemList))
            } else {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, null, errContent, HttpStatus.INTERNAL_SERVER_ERROR))
            }
        } catch (Exception e) {
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, null, e.message, HttpStatus.INTERNAL_SERVER_ERROR))
        }
    }

    @GetMapping("/getApDomainGroupType")
    ResponseEntity<BaseResponse<?>> getApDomainGroupType(@RequestParam String type, @RequestParam String groupType) {
        try {
            def env = post1(smcsUrl,
                    """<soapenv:Envelope xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:urn="urn:VNPServices">
                                <soapenv:Header/>
                                <soapenv:Body>
                                    <urn:getApDomainGroupType soapenv:encodingStyle="http://schemas.xmlsoap.org/soap/encoding/">
                                        <sessionID xsi:type="xsd:string">${getSessionId()}</sessionID>
                                        <p_Type xsi:type="xsd:string">$type</p_Type>
                                        <p_GroupType xsi:type="xsd:string">$groupType</p_GroupType>
                                    </urn:getApDomainGroupType>
                                </soapenv:Body>
                            </soapenv:Envelope>""")

            String error_code = env.Body.getApDomainGroupTypeResponse.p_errCode.text()
            String responseStatus = env.Body.getApDomainGroupTypeResponse.responseStatus.text()
            String errContent = env.Body.getApDomainGroupTypeResponse.p_errContent.text()
            String description = env.Body.getApDomainGroupTypeResponse.description.text()
            if (responseStatus == "1" && error_code == "0") {
                def items = env.Body.getApDomainGroupTypeResponse.p_result.item
                def itemList = items.collect {
                    def parts = it.text().split("\\|")
                    [code: parts[0], name: parts[1]]
                }

                return ResponseEntity.ok().body(BaseResponseBuilder.of(itemList))
            } else {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, null, errContent, HttpStatus.INTERNAL_SERVER_ERROR))
            }
        } catch (Exception e) {
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, null, e.message, HttpStatus.INTERNAL_SERVER_ERROR))
        }
    }

    @GetMapping("/getProvince")
    ResponseEntity<BaseResponse<?>> getProvince() {
        try {
            def env = post1(smcsUrl,
                    """<soapenv:Envelope xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:urn="urn:VNPServices">
                                <soapenv:Header/>
                                <soapenv:Body>
                                    <urn:getProvince soapenv:encodingStyle="http://schemas.xmlsoap.org/soap/encoding/">
                                        <sessionID xsi:type="xsd:string">${getSessionId()}</sessionID>
                                    </urn:getProvince>
                                </soapenv:Body>
                            </soapenv:Envelope>""")

            String error_code = env.Body.getProvinceResponse.p_errCode.text()
            String responseStatus = env.Body.getProvinceResponse.responseStatus.text()
            String errContent = env.Body.getProvinceResponse.p_errContent.text()
            String description = env.Body.getProvinceResponse.description.text()
            if (responseStatus == "1" && error_code == "0") {
                def items = env.Body.getProvinceResponse.p_ProvinceList.item
                def itemList = items.collect {
                    def parts = it.text().split("\\|")
                    [id: parts[0], code: parts[1], name: parts[2]]
                }
                return ResponseEntity.ok().body(BaseResponseBuilder.of(itemList))
            } else {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, null, errContent, HttpStatus.INTERNAL_SERVER_ERROR))
            }
        } catch (Exception e) {
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, null, e.message, HttpStatus.INTERNAL_SERVER_ERROR))
        }
    }

    @GetMapping("/getState")
    ResponseEntity<BaseResponse<?>> getState(@RequestParam String province) {
        try {
            def env = post1(smcsUrl,
                    """<soapenv:Envelope xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:urn="urn:VNPServices">
                                <soapenv:Header/>
                                <soapenv:Body>
                                    <urn:getState soapenv:encodingStyle="http://schemas.xmlsoap.org/soap/encoding/">
                                        <sessionID xsi:type="xsd:string">${getSessionId()}</sessionID>
                                        <p_area_id xsi:type="xsd:string">$province</p_area_id>
                                    </urn:getState>
                                </soapenv:Body>
                            </soapenv:Envelope>""")

            String error_code = env.Body.getStateResponse.p_errCode.text()
            String responseStatus = env.Body.getStateResponse.responseStatus.text()
            String errContent = env.Body.getStateResponse.p_errContent.text()
            String description = env.Body.getStateResponse.description.text()
            if (responseStatus == "1" && error_code == "0") {
                def items = env.Body.getStateResponse.p_StateList.item
                def itemList = items.collect {
                    def parts = it.text().split("\\|")
                    [id: parts[0], code: parts[1], name: parts[2]]
                }
                return ResponseEntity.ok().body(BaseResponseBuilder.of(itemList))
            } else {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, null, errContent, HttpStatus.INTERNAL_SERVER_ERROR))
            }
        } catch (Exception e) {
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, null, e.message, HttpStatus.INTERNAL_SERVER_ERROR))
        }
    }

    @GetMapping("/getCounty")
    ResponseEntity<BaseResponse<?>> getCounty(@RequestParam String state) {
        try {
            def env = post1(smcsUrl,
                    """<soapenv:Envelope xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:urn="urn:VNPServices">
                                <soapenv:Header/>
                                <soapenv:Body>
                                    <urn:getCounty soapenv:encodingStyle="http://schemas.xmlsoap.org/soap/encoding/">
                                        <sessionID xsi:type="xsd:string">${getSessionId()}</sessionID>
                                        <p_state_id xsi:type="xsd:string">$state</p_state_id>
                                    </urn:getCounty>
                                </soapenv:Body>
                            </soapenv:Envelope>""")

            String error_code = env.Body.getCountyResponse.p_errCode.text()
            String responseStatus = env.Body.getCountyResponse.responseStatus.text()
            String errContent = env.Body.getCountyResponse.p_errContent.text()
            String description = env.Body.getCountyResponse.description.text()
            if (responseStatus == "1" && error_code == "0") {
                def items = env.Body.getCountyResponse.p_CountyList.item
                def itemList = items.collect {
                    def parts = it.text().split("\\|")
                    [id: parts[0], code: parts[1], name: parts[2]]
                }
                return ResponseEntity.ok().body(BaseResponseBuilder.of(itemList))
            } else {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, null, errContent, HttpStatus.INTERNAL_SERVER_ERROR))
            }
        } catch (Exception e) {
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, null, e.message, HttpStatus.INTERNAL_SERVER_ERROR))
        }
    }

    @GetMapping("/getListAgent")
    ResponseEntity<BaseResponse<?>> getListAgent(@RequestParam String staffCode) {
        try {
            def env = post1(smcsUrl,
                    """<soapenv:Envelope xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:urn="urn:VNPServices">
                                <soapenv:Header/>
                                <soapenv:Body>
                                    <urn:agentListByStaffCode soapenv:encodingStyle="http://schemas.xmlsoap.org/soap/encoding/">
                                        <sessionID xsi:type="xsd:string">${getSessionId()}</sessionID>
                                        <p_StaffCode xsi:type="xsd:string">$staffCode</p_StaffCode>
                                    </urn:agentListByStaffCode>
                                </soapenv:Body>
                            </soapenv:Envelope>""")

            String error_code = env.Body.agentListByStaffCodeResponse.p_errCode.text()
            String responseStatus = env.Body.agentListByStaffCodeResponse.responseStatus.text()
            String errContent = env.Body.agentListByStaffCodeResponse.p_errContent.text()
            String description = env.Body.agentListByStaffCodeResponse.description.text()
            if (responseStatus == "1" && error_code == "0") {
                def items = env.Body.agentListByStaffCodeResponse.p_AgentList.item
                def itemList = items.collect {
                    def parts = it.text().split("\\|")
                    [agent_id         : parts[0],
                     agent_code       : parts[1],
                     contact_name     : parts[2],
                     type_code        : parts[3],
                     type_name        : parts[4],
                     agent_type_code  : parts[5],
                     agent_type_name  : parts[6],
                     agent_city_id    : parts[7],
                     agent_city_name  : parts[8],
                     agent_state_id   : parts[9],
                     agent_state_name : parts[10],
                     agent_county_id  : parts[11],
                     agent_county_name: parts[12],
                     hrm_code         : parts[13],
                     status           : parts[14],
                     is_approve       : parts[15],
                     approve_time     : parts.size() > 16 ? parts[16] : "",
                     reject_reason    : parts.size() > 17 ? parts[17] : "",
                     address          : parts.size() > 18 ? parts[18] : "",
                     create_date      : parts.size() > 19 ? parts[19] : "",
                     ma_dvqhns        : parts.size() > 20 ? parts[20] : "",
                     so_cccd          : parts.size() > 21 ? parts[21] : ""]
                }
                return ResponseEntity.ok().body(BaseResponseBuilder.of(itemList))
            } else {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, null, errContent, HttpStatus.INTERNAL_SERVER_ERROR))
            }
        } catch (Exception e) {
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, null, e.message, HttpStatus.INTERNAL_SERVER_ERROR))
        }
    }

    @GetMapping("/getAgentDetail")
    ResponseEntity<BaseResponse<?>> getAgentDetail(@RequestParam String agentCode) {
        try {
            def session = genericApi.getSessionData(request)
            String maTinh = session[GenericApi.MA_TINH]

            def env = post1(smcsUrl,
                    """<soapenv:Envelope xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:urn="urn:VNPServices">
                                <soapenv:Header/>
                                <soapenv:Body>
                                    <urn:agentDetailByCode soapenv:encodingStyle="http://schemas.xmlsoap.org/soap/encoding/">
                                        <sessionID xsi:type="xsd:string">${getSessionId()}</sessionID>
                                        <p_AgentCode xsi:type="xsd:string">$agentCode</p_AgentCode>
                                    </urn:agentDetailByCode>
                                </soapenv:Body>
                            </soapenv:Envelope>""")

            String error_code = env.Body.agentDetailByCodeResponse.p_errCode.text()
            String responseStatus = env.Body.agentDetailByCodeResponse.responseStatus.text()
            String errContent = env.Body.agentDetailByCodeResponse.p_errContent.text()
            String description = env.Body.agentDetailByCodeResponse.description.text()
            if (responseStatus == "1" && error_code == "0") {
                String result = env.Body.agentDetailByCodeResponse.result
                def parts = result.split("\\|")

                // Xu ly anh
                List lstImgPaths = []
                if (parts.length > 22 && parts[22] && parts[22].toString().trim() != "") {
                    String[] lstImgPath = parts[22].toString().split(";")
                    for (String path : lstImgPath) {
                        def row = [:]
                        String fileName = path.substring(path.lastIndexOf("/") + 1, path.length())
                        String minioPath = "SMCS/AGENT/" + maTinh.toUpperCase() + "/" + fileName
                        row["img_path"] = minioPath
                        if (objectStoragesManaged.checkExists(minioPath)) {
                            row["full_url"] = objectStoragesManaged.getUrl(minioPath)
                        } else {
                            byte[] data = ftpsManaged.getFile(ftpHost, ftpPort, ftpUsername, ftpPassword, ftpFolder, path)
                            boolean uploadMinio = objectStoragesManaged.uploadObject(minioPath, data)
                            if (uploadMinio) {
                                row["full_url"] = objectStoragesManaged.getUrl(minioPath)
                            }
                        }
                        lstImgPaths.add(row)
                    }
                }

                def data = [
                        agent_id         : parts[0],
                        agent_code       : parts[1],
                        contact_name     : parts[2],
                        contact_phone    : parts[3],
                        mst              : parts[4],
                        loai_gt          : parts[5],
                        so_gt            : parts[6],
                        type_code        : parts[7],
                        type_name        : parts[8],
                        agent_type_code  : parts[9],
                        agent_type_name  : parts[10],
                        partner_type_code: parts[11],
                        partner_type_name: parts[12],
                        agent_city_id    : parts[13],
                        agent_city_name  : parts[14],
                        agent_state_id   : parts[15],
                        agent_state_name : parts[16],
                        agent_county_id  : parts[17],
                        agent_county_name: parts[18],
                        hrm_code         : parts[19],
                        longitude        : parts[20],
                        latitude         : parts[21],
                        img_path         : lstImgPaths,
                        vas_sales        : parts[23],
                        is_reg_info      : parts[24],
                        status           : parts.size() > 25 ? parts[25] : "",
                        is_approve       : parts.size() > 26 ? parts[26] : "",
                        approve_time     : parts.size() > 27 ? parts[27] : "",
                        reject_reason    : parts.size() > 28 ? parts[28] : "",
                        agent_name       : parts.size() > 29 ? parts[29] : "",
                        address          : parts.size() > 30 ? parts[30] : "",
                        ma_dvqhns        : parts.size() > 31 ? parts[31] : "",
                        new_province_id  : parts.size() > 32 ? parts[32] : "",
                        new_province_code: parts.size() > 33 ? parts[33] : "",
                        new_county_id    : parts.size() > 34 ? parts[34] : "",
                        new_county_code  : parts.size() > 35 ? parts[35] : ""]
                return ResponseEntity.ok().body(BaseResponseBuilder.of(data))
            } else {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, null, errContent, HttpStatus.INTERNAL_SERVER_ERROR))
            }
        } catch (Exception e) {
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, null, e.message, HttpStatus.INTERNAL_SERVER_ERROR))
        }
    }

    @PostMapping("/createAgent")
    @LoggingInOut
    ResponseEntity<BaseResponse<?>> createAgent(@RequestBody SMCS_CreateAgent_Req body) {
        try {
            String imgPath = "1"
            // if (body.imgPath && body.imgPath.trim() != "") {
            //     String[] lstImgPath = body.imgPath.split(";")
            //     for (String path : lstImgPath) {
            //         InputStream inp = null
            //         try {
            //             String fileName = path.substring(path.lastIndexOf("/") + 1, path.length())
            //             inp = objectStoragesManaged.getInputStream(path)
            //             if (inp != null) {
            //                 Map<String, Object> kq = ftpsManaged.upload(ftpHost, ftpPort, ftpUsername, ftpPassword, ftpFolder, fileName, inp)
            //                 if (kq["status"] != "1") {
            //                     return ResponseEntity.ok().body(BaseResponseBuilder.of(null, null, kq["error"], HttpStatus.INTERNAL_SERVER_ERROR))
            //                 }

            //                 imgPath += (kq["path"] + ";")
            //             } else {
            //                 return ResponseEntity.ok().body(BaseResponseBuilder.of(null, null, "Không tìm thấy file: " + body.imgPath, HttpStatus.INTERNAL_SERVER_ERROR))
            //             }
            //         } catch (Exception e) {
            //             return ResponseEntity.ok().body(BaseResponseBuilder.of(null, null, e.message, HttpStatus.INTERNAL_SERVER_ERROR))
            //         } finally {
            //             if (inp != null) {
            //                 try {
            //                     inp.close()
            //                 } catch (Exception ee) {
            //                 }
            //             }
            //         }
            //     }
            // }

            // if (imgPath == "") {
            //    return ResponseEntity.ok().body(BaseResponseBuilder.of(null, null, "Có lỗi khi upload ảnh lên FTP", HttpStatus.INTERNAL_SERVER_ERROR))
            // }

            def env = post1(smcsUrl,
                    """<soapenv:Envelope xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:urn="urn:VNPServices">
                                <soapenv:Header/>
                                <soapenv:Body>
                                    <urn:createAgentByOneApp soapenv:encodingStyle="http://schemas.xmlsoap.org/soap/encoding/">
                                         <sessionID xsi:type="xsd:string">${getSessionId()}</sessionID>
                                         <p_AgentName xsi:type="xsd:string"><![CDATA[${body.agentName}]]></p_AgentName>
                                         <p_ContactName xsi:type="xsd:string"><![CDATA[${body.contactName}]]></p_ContactName>
                                         <p_HrmCode xsi:type="xsd:string">${body.hrmCode}</p_HrmCode>
                                         <p_ContactPhone xsi:type="xsd:string">${body.contactPhone}</p_ContactPhone>
                                         <p_Tin xsi:type="xsd:string">${body.tin}</p_Tin>
                                         <p_IdentificationType xsi:type="xsd:string">${body.identificationType}</p_IdentificationType>
                                         <p_IdentificationNo xsi:type="xsd:string">${body.identificationNo}</p_IdentificationNo>
                                         <p_Type xsi:type="xsd:string">${body.type}</p_Type>
                                         <p_AgentType xsi:type="xsd:string">${body.agentType}</p_AgentType>
                                         <p_Partner xsi:type="xsd:string">${body.partner != null ? body.partner : ""}</p_Partner>
                                         <p_CityId xsi:type="xsd:string">${body.cityId}</p_CityId>
                                         <p_StateId xsi:type="xsd:string">${body.stateId != null ? body.stateId : ""}</p_StateId>
                                         <p_CountyId xsi:type="xsd:string">${body.countyId}</p_CountyId>
                                         <p_Address xsi:type="xsd:string"><![CDATA[${body.address}]]></p_Address>
                                         <p_CareType xsi:type="xsd:string">${body.careType}</p_CareType>
                                         <p_Longitude xsi:type="xsd:string">${body.longitude}</p_Longitude>
                                         <p_Latitude xsi:type="xsd:string">${body.latitude}</p_Latitude>
                                         <p_ImgPath xsi:type="xsd:string">$imgPath</p_ImgPath>
                                         <p_VasSales xsi:type="xsd:string">${body.vasSales}</p_VasSales>
                                         <p_IsRegInfo xsi:type="xsd:string">${body.isRegInfo}</p_IsRegInfo>
                                         <p_MDVQHNS xsi:type="xsd:string">${body.maDvqhns != null ? body.maDvqhns : ""}</p_MDVQHNS>
                                         <p_CCCDan xsi:type="xsd:string">${body.soCccd != null ? body.soCccd : ""}</p_CCCDan>
                                         <p_Province2025 xsi:type="xsd:string">${body.province2025 != null ? body.province2025 : ""}</p_Province2025>
                                         <p_County2025 xsi:type="xsd:string">${body.county2025 != null ? body.county2025 : ""}</p_County2025>
                                         <p_IdCreateReq xsi:type="xsd:string">${body.idCreateReq != null ? body.idCreateReq : ""}</p_IdCreateReq>
                                         <p_Birthday xsi:type="xsd:string">${body.birthday != null ? body.birthday : ""}</p_Birthday>
                                         <p_Email xsi:type="xsd:string">${body.email != null ? body.email : ""}</p_Email>
                                    </urn:createAgentByOneApp>
                                </soapenv:Body>
                            </soapenv:Envelope>""")

            String error_code = env.Body.createAgentByOneAppResponse.p_errCode.text()
            String responseStatus = env.Body.createAgentByOneAppResponse.responseStatus.text()
            String errContent = env.Body.createAgentByOneAppResponse.p_errContent.text()
            String description = env.Body.createAgentByOneAppResponse.description.text()
            if (responseStatus == "1" && error_code == "0") {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(env.Body.createAgentByOneAppResponse.p_AgentId.text()))
            } else {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, null, errContent, HttpStatus.INTERNAL_SERVER_ERROR))
            }
        } catch (Exception e) {
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, null, e.message, HttpStatus.INTERNAL_SERVER_ERROR))
        }
    }

    @PostMapping("/updateAgent")
    @LoggingInOut
    ResponseEntity<BaseResponse<?>> updateAgent(@RequestBody SMCS_UpdateAgent_Req body) {
        try {
            String imgPath = "1"
            // if (body.imgPath && body.imgPath.trim() != "") {
            //     String[] lstImgPath = body.imgPath.split(";")
            //     for (String path : lstImgPath) {
            //         InputStream inp = null
            //         try {
            //             String fileName = path.substring(path.lastIndexOf("/") + 1, path.length())
            //             inp = objectStoragesManaged.getInputStream(path)
            //             if (inp != null) {
            //                 Map<String, Object> kq = ftpsManaged.upload(ftpHost, ftpPort, ftpUsername, ftpPassword, ftpFolder, fileName, inp)
            //                 if (kq["status"] != "1") {
            //                     return ResponseEntity.ok().body(BaseResponseBuilder.of(null, null, kq["error"], HttpStatus.INTERNAL_SERVER_ERROR))
            //                 }

            //                 imgPath += (kq["path"] + ";")
            //             } else {
            //                 return ResponseEntity.ok().body(BaseResponseBuilder.of(null, null, "Không tìm thấy file: " + path, HttpStatus.INTERNAL_SERVER_ERROR))
            //             }
            //         } catch (Exception e) {
            //             return ResponseEntity.ok().body(BaseResponseBuilder.of(null, null, e.message, HttpStatus.INTERNAL_SERVER_ERROR))
            //         } finally {
            //             if (inp != null) {
            //                 try {
            //                     inp.close()
            //                 } catch (Exception ee) {
            //                 }
            //             }
            //         }
            //     }
            // }

            // if (imgPath == "") {
            //    return ResponseEntity.ok().body(BaseResponseBuilder.of(null, null, "Có lỗi khi upload ảnh lên FTP", HttpStatus.INTERNAL_SERVER_ERROR))
            // }

            def env = post1(smcsUrl,
                    """<soapenv:Envelope xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:urn="urn:VNPServices">
                                <soapenv:Header/>
                                <soapenv:Body>
                                    <urn:updateAgentByOneApp soapenv:encodingStyle="http://schemas.xmlsoap.org/soap/encoding/">
                                         <sessionID xsi:type="xsd:string">${getSessionId()}</sessionID>
                                         <p_AgentName xsi:type="xsd:string"><![CDATA[${body.agentName}]]></p_AgentName>
                                         <p_ContactName xsi:type="xsd:string"><![CDATA[${body.contactName}]]></p_ContactName>
                                         <p_HrmCode xsi:type="xsd:string">${body.hrmCode}</p_HrmCode>
                                         <p_ContactPhone xsi:type="xsd:string">${body.contactPhone}</p_ContactPhone>
                                         <p_Tin xsi:type="xsd:string">${body.tin}</p_Tin>
                                         <p_IdentificationType xsi:type="xsd:string">${body.identificationType}</p_IdentificationType>
                                         <p_IdentificationNo xsi:type="xsd:string">${body.identificationNo}</p_IdentificationNo>
                                         <p_CityId xsi:type="xsd:string">${body.cityId}</p_CityId>
                                         <p_StateId xsi:type="xsd:string">${body.stateId != null ? body.stateId : ""}</p_StateId>
                                         <p_CountyId xsi:type="xsd:string">${body.countyId}</p_CountyId>
                                         <p_Address xsi:type="xsd:string"><![CDATA[${body.address}]]></p_Address>
                                         <p_CareType xsi:type="xsd:string">${body.careType}</p_CareType>
                                         <p_Longitude xsi:type="xsd:string">${body.longitude}</p_Longitude>
                                         <p_Latitude xsi:type="xsd:string">${body.latitude}</p_Latitude>
                                         <p_ImgPath xsi:type="xsd:string">$imgPath</p_ImgPath>
                                         <p_VasSales xsi:type="xsd:string">${body.vasSales}</p_VasSales>
                                         <p_IsRegInfo xsi:type="xsd:string">${body.isRegInfo}</p_IsRegInfo>
                                         <p_Province2025 xsi:type="xsd:string">${body.province2025 != null ? body.province2025 : ""}</p_Province2025>
                                         <p_County2025 xsi:type="xsd:string">${body.county2025 != null ? body.county2025 : ""}</p_County2025>
                                         <p_IdCreateReq xsi:type="xsd:string">${body.idCreateReq != null ? body.idCreateReq : ""}</p_IdCreateReq>
                                         <p_Birthday xsi:type="xsd:string">${body.birthday != null ? body.birthday : ""}</p_Birthday>
                                         <p_Email xsi:type="xsd:string">${body.email != null ? body.email : ""}</p_Email>
                                    </urn:updateAgentByOneApp>
                                </soapenv:Body>
                            </soapenv:Envelope>""")

            String error_code = env.Body.updateAgentByOneAppResponse.p_errCode.text()
            String responseStatus = env.Body.updateAgentByOneAppResponse.responseStatus.text()
            String errContent = env.Body.updateAgentByOneAppResponse.p_errContent.text()
            String description = env.Body.updateAgentByOneAppResponse.description.text()
            if (responseStatus == "1" && error_code == "0") {
                return ResponseEntity.ok().body(BaseResponseBuilder.of("ok"))
            } else {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, null, errContent, HttpStatus.INTERNAL_SERVER_ERROR))
            }
        } catch (Exception e) {
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, null, e.message, HttpStatus.INTERNAL_SERVER_ERROR))
        }
    }

    GPathResult post1(String url, String body) {
        Map<String, String> headers = [:]
        headers.put("SOAPAction", "")
        headers.put("Content-Type", "text/xml; charset=utf-8")
        String resp = httpManaged.post(["url": url], headers, body)
        return new XmlSlurper().parseText(resp)
    }

    @PostMapping("/getListAgentCareRequest")
    ResponseEntity<BaseResponse<?>> getListAgentCareRequest(@RequestBody SMCS_GetListAgentCareRequest_Req body) {
        try {
            def session = genericApi.getSessionData(request)
            String maTinh = session[GenericApi.MA_TINH]

            Map<String, String> headers = [:]
            headers.put("Content-Type", "application/json; charset=utf-8")
            body.service = "getListAgentCareRequest"
            String resp = httpManaged.post(["url": smcsRestUrl], headers, new Gson().toJson(body))
            def res = new JsonSlurper().parseText(resp)
            if (res["error_code"].toString() == "0") {
                for (def item : res["result"]) {
//                    if (item["ANH_ROI_DI"]) {
//                        String path = item["ANH_ROI_DI"].toString()
//                        def row = [:]
//                        String fileName = path.substring(path.lastIndexOf("/") + 1, path.length())
//                        String minioPath = "DHSXKD/APP-BANHANG/" + maTinh.toUpperCase() + "/" + fileName
//                        if (objectStoragesManaged.checkExists(minioPath)) {
//                            item["ANH_ROI_DI"] = objectStoragesManaged.getUrl(minioPath)
//                        } else {
//                            byte[] data = ftpsManaged.getFile(ftpHost, ftpPort, ftpUsername, ftpPassword, ftpFolder, path)
//                            boolean uploadMinio = objectStoragesManaged.uploadObject(minioPath, data)
//                            if (uploadMinio) {
//                                item["ANH_ROI_DI"] = objectStoragesManaged.getUrl(minioPath)
//                            }
//                        }
//                    }

                    try {
                        if (item["ANH_ROI_DI"]) item["ANH_ROI_DI"] = objectStoragesManaged.getUrl(item["ANH_ROI_DI"].toString())
                        if (item["ANH_BIEN_VAY_6"]) item["ANH_BIEN_VAY_6"] = objectStoragesManaged.getUrl(item["ANH_BIEN_VAY_6"].toString())
                        if (item["ANH_BIEN_NGANG_6"]) item["ANH_BIEN_NGANG_6"] = objectStoragesManaged.getUrl(item["ANH_BIEN_NGANG_6"].toString())
                        if (item["ANH_BIEN_DOC_6"]) item["ANH_BIEN_DOC_6"] = objectStoragesManaged.getUrl(item["ANH_BIEN_DOC_6"].toString())
                        if (item["ANH_BIEN_TREO_6"]) item["ANH_BIEN_TREO_6"] = objectStoragesManaged.getUrl(item["ANH_BIEN_TREO_6"].toString())
                        if (item["ANH_BIEN_DUNG_6"]) item["ANH_BIEN_DUNG_6"] = objectStoragesManaged.getUrl(item["ANH_BIEN_DUNG_6"].toString())
                        if (item["ANH_BIEN_VAY_9"]) item["ANH_BIEN_VAY_9"] = objectStoragesManaged.getUrl(item["ANH_BIEN_VAY_9"].toString())
                        if (item["ANH_BIEN_NGANG_9"]) item["ANH_BIEN_NGANG_9"] = objectStoragesManaged.getUrl(item["ANH_BIEN_NGANG_9"].toString())
                        if (item["ANH_BIEN_DOC_9"]) item["ANH_BIEN_DOC_9"] = objectStoragesManaged.getUrl(item["ANH_BIEN_DOC_9"].toString())
                        if (item["ANH_BIEN_TREO_9"]) item["ANH_BIEN_TREO_9"] = objectStoragesManaged.getUrl(item["ANH_BIEN_TREO_9"].toString())
                        if (item["ANH_BIEN_DUNG_9"]) item["ANH_BIEN_DUNG_9"] = objectStoragesManaged.getUrl(item["ANH_BIEN_DUNG_9"].toString())
                    } catch (Exception ignored) {
                    }
                }
                return ResponseEntity.ok().body(BaseResponseBuilder.of(res["result"]))
            } else {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, null, res["result"].toString(), HttpStatus.INTERNAL_SERVER_ERROR))
            }
        } catch (Exception e) {
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, null, e.message, HttpStatus.INTERNAL_SERVER_ERROR))
        }
    }

    @PostMapping("/updateAgentCareRequest")
    @LoggingInOut
    ResponseEntity<BaseResponse<?>> updateAgentCareRequest(@RequestBody SMCS_UpdateAgentCareRequest_Req body) {
        try {
            String imgPath = ""
            // if (body.image && body.image.trim() != "") {
            //     InputStream inp = null
            //     try {
            //         String fileName = body.image.substring(body.image.lastIndexOf("/") + 1, body.image.length())
            //         inp = objectStoragesManaged.getInputStream(body.image)
            //         if (inp != null) {
            //             String folder = "/images/csdg_ghetham_imgs";
            //             Map<String, Object> kq = ftpsManaged.uploadCSGT(ftpHost, ftpPort, ftpUsername, ftpPassword, folder, fileName, inp)
            //             if (kq["status"] != "1") {
            //                 return ResponseEntity.ok().body(BaseResponseBuilder.of(null, null, kq["error"], HttpStatus.INTERNAL_SERVER_ERROR))
            //             }

            //             imgPath = fileName
            //         } else {
            //             return ResponseEntity.ok().body(BaseResponseBuilder.of(null, null, "Không tìm thấy file: " + body.image, HttpStatus.INTERNAL_SERVER_ERROR))
            //         }
            //     } catch (Exception e) {
            //         return ResponseEntity.ok().body(BaseResponseBuilder.of(null, null, e.message, HttpStatus.INTERNAL_SERVER_ERROR))
            //     } finally {
            //         if (inp != null) {
            //             try {
            //                 inp.close()
            //             } catch (Exception ee) {
            //             }
            //         }
            //     }
            // }

            Map<String, String> headers = [:]
            headers.put("Content-Type", "application/json; charset=utf-8")

            body.service = "updateAgentCareRequest"
            // body.image = imgPath
            body.timeCSDB = (body.timeCSDB ? body.timeCSDB.trim() : "")
            if (body.anh_bien_vay_6 == null) body.anh_bien_vay_6 = ""
            if (body.anh_bien_ngang_6 == null) body.anh_bien_ngang_6 = ""
            if (body.anh_bien_doc_6 == null) body.anh_bien_doc_6 = ""
            if (body.anh_bien_treo_6 == null) body.anh_bien_treo_6 = ""
            if (body.anh_bien_dung_6 == null) body.anh_bien_dung_6 = ""
            if (body.anh_bien_vay_9 == null) body.anh_bien_vay_9 = ""
            if (body.anh_bien_ngang_9 == null) body.anh_bien_ngang_9 = ""
            if (body.anh_bien_doc_9 == null) body.anh_bien_doc_9 = ""
            if (body.anh_bien_treo_9 == null) body.anh_bien_treo_9 = ""
            if (body.anh_bien_dung_9 == null) body.anh_bien_dung_9 = ""
            if (body.nam_bien_vay_9 == null) body.nam_bien_vay_9 = ""
            if (body.nam_bien_ngang_9 == null) body.nam_bien_ngang_9 = ""
            if (body.nam_bien_doc_9 == null) body.nam_bien_doc_9 = ""
            if (body.nam_bien_treo_9 == null) body.nam_bien_treo_9 = ""
            if (body.nam_bien_dung_9 == null) body.nam_bien_dung_9 = ""
            String resp = httpManaged.post(["url": smcsRestUrl], headers, new Gson().toJson(body))
            def res = new JsonSlurper().parseText(resp)
            if (res["error_code"].toString() == "0") {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(res["result"]))
            } else {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, null, res["result"].toString(), HttpStatus.INTERNAL_SERVER_ERROR))
            }
        } catch (Exception e) {
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, null, e.message, HttpStatus.INTERNAL_SERVER_ERROR))
        }
    }

    @GetMapping("/getProvince2025")
    ResponseEntity<BaseResponse<?>> getProvince2025() {
        try {
            def session = genericApi.getSessionData(request)
            String maTinh = session[GenericApi.MA_TINH]

            def env = post1(smcsUrl,
                    """<soapenv:Envelope xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:urn="urn:VNPServices">
                                <soapenv:Header/>
                                <soapenv:Body>
                                    <urn:getProvince2025 soapenv:encodingStyle="http://schemas.xmlsoap.org/soap/encoding/">
                                        <sessionID xsi:type="xsd:string">${getSessionId()}</sessionID>
                                    </urn:getProvince2025>
                                </soapenv:Body>
                            </soapenv:Envelope>""")

            String error_code = env.Body.getProvince2025Response.p_errCode.text()
            String responseStatus = env.Body.getProvince2025Response.responseStatus.text()
            String errContent = env.Body.getProvince2025Response.p_errContent.text()
            if (responseStatus == "1" && error_code == "0") {
                def items = env.Body.getProvince2025Response.p_ProvinceList.item
                def itemList = items.collect {
                    def parts = it.text().split("\\|")
                    [province_id  : parts[0],
                     province_code: parts[1],
                     province_name: parts[2]]
                }
                return ResponseEntity.ok().body(BaseResponseBuilder.of(itemList))
            } else {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, null, errContent, HttpStatus.INTERNAL_SERVER_ERROR))
            }
        } catch (Exception e) {
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, null, e.message, HttpStatus.INTERNAL_SERVER_ERROR))
        }
    }

    @GetMapping("/getCounty2025")
    ResponseEntity<BaseResponse<?>> getCounty2025(@RequestParam("province_id") String province_id) {
        try {
            def session = genericApi.getSessionData(request)
            String maTinh = session[GenericApi.MA_TINH]

            def env = post1(smcsUrl,
                    """<soapenv:Envelope xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:urn="urn:VNPServices">
                                <soapenv:Header/>
                                <soapenv:Body>
                                    <urn:getCounty2025 soapenv:encodingStyle="http://schemas.xmlsoap.org/soap/encoding/">
                                        <sessionID xsi:type="xsd:string">${getSessionId()}</sessionID>
                                        <p_province_id xsi:type="xsd:string">${province_id}</p_province_id>
                                    </urn:getCounty2025>
                                </soapenv:Body>
                            </soapenv:Envelope>""")

            String error_code = env.Body.getCounty2025Response.p_errCode.text()
            String responseStatus = env.Body.getCounty2025Response.responseStatus.text()
            String errContent = env.Body.getCounty2025Response.p_errContent.text()
            if (responseStatus == "1" && error_code == "0") {
                def items = env.Body.getCounty2025Response.p_CountyList.item
                def itemList = items.collect {
                    def parts = it.text().split("\\|")
                    [county_id  : parts[0],
                     county_code: parts[1],
                     county_name: parts[2]]
                }
                return ResponseEntity.ok().body(BaseResponseBuilder.of(itemList))
            } else {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, null, errContent, HttpStatus.INTERNAL_SERVER_ERROR))
            }
        } catch (Exception e) {
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, null, e.message, HttpStatus.INTERNAL_SERVER_ERROR))
        }
    }

    // MHDDM
    @PostMapping("/mhddm/themmoithuebao")
    @LoggingInOut
    ResponseEntity<BaseResponse<?>> themMoiThueBao(@RequestBody Map body) {
        try {
            Map<String, String> headers = [:]
            headers.put("Content-Type", "application/json; charset=utf-8")
            body.service = "MHDDM_ThemMoiThueBao"
            body.session = smcsRestSession

            String resp = httpManaged.post(["url": smcsRestUrl], headers, new Gson().toJson(body))
            def res = new JsonSlurper().parseText(resp)
            if (res["error_code"].toString() == "0") {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(res["result"]))
            } else {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, null, res["error_content"].toString(), HttpStatus.INTERNAL_SERVER_ERROR))
            }
        } catch (Exception e) {
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, null, e.message, HttpStatus.INTERNAL_SERVER_ERROR))
        }
    }

    @PostMapping("/mhddm/checkthuebao")
    ResponseEntity<BaseResponse<?>> checkThueBao(@RequestBody Map body) {
        try {
            Map<String, String> headers = [:]
            headers.put("Content-Type", "application/json; charset=utf-8")
            body.service = "MHDDM_CheckThueBao"
            body.session = smcsRestSession

            String resp = httpManaged.post(["url": smcsRestUrl], headers, new Gson().toJson(body))
            def res = new JsonSlurper().parseText(resp)
            if (res["error_code"].toString() == "0") {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(res["result"]))
            } else {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, null, res["error_content"].toString(), HttpStatus.INTERNAL_SERVER_ERROR))
            }
        } catch (Exception e) {
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, null, e.message, HttpStatus.INTERNAL_SERVER_ERROR))
        }
    }

    @PostMapping("/mhddm/thongtinthuebao")
    ResponseEntity<BaseResponse<?>> thongTinThueBao(@RequestBody Map body) {
        try {
            Map<String, String> headers = [:]
            headers.put("Content-Type", "application/json; charset=utf-8")
            body.service = "MHDDM_ThongTinThueBao"
            body.session = smcsRestSession

            String resp = httpManaged.post(["url": smcsRestUrl], headers, new Gson().toJson(body))
            def res = new JsonSlurper().parseText(resp)
            if (res["error_code"].toString() == "0") {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(res["result"]))
            } else {
                return ResponseEntity.ok().body(BaseResponseBuilder.of(null, null, res["error_content"].toString(), HttpStatus.INTERNAL_SERVER_ERROR))
            }
        } catch (Exception e) {
            return ResponseEntity.ok().body(BaseResponseBuilder.of(null, null, e.message, HttpStatus.INTERNAL_SERVER_ERROR))
        }
    }

}
