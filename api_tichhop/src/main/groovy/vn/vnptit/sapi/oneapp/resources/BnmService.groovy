package vn.vnptit.sapi.oneapp.resources

import groovy.json.JsonSlurper
import org.json.XML
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import vn.vnptit.sapi.oneapp.managed.HttpManaged
import vn.vnptit.sapi.oneapp.managed.LoggingManaged
import vn.vnptit.sapi.oneapp.managed.OracleManaged
import vn.vnptit.sapi.oneapp.models.CommonResponse
import vn.vnptit.sapi.oneapp.models.bnm.*
import vn.vnptit.sapi.oneapp.util.FakeSuccessCommonResponse

import javax.ws.rs.Consumes
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType

@RestController
@RequestMapping("bnm")
class BnmService {
    @Autowired
    LoggingManaged loggingManaged
    @Autowired
    OracleManaged oracleManaged
    @Autowired
    HttpManaged httpManaged

    @Value("\${spring.defaultconfigs.bnm-url}")
    String url

    @PostMapping("/checkAccountOnline")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    CommonResponse checkAccountOnline(@RequestBody CheckAccountOnlineInput body) {
        try {
            String reqBody = """
<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:tem="http://tempuri.org/">
   <soapenv:Header/>
   <soapenv:Body>
      <tem:CheckAccount_Online>
         <!--Optional:-->
         <tem:IP_Bras>${body.ipBras}</tem:IP_Bras>
         <!--Optional:-->
         <tem:UserName>${body.username}</tem:UserName>
      </tem:CheckAccount_Online>
   </soapenv:Body>
</soapenv:Envelope>
"""
            String xml = httpManaged.post(["url": url],
                    ["Content-Type": "text/xml;charset=UTF-8",
                     "Host"        : "10.70.53.129:6565",
                     "SOAPAction"  : "http://tempuri.org/CheckAccount_Online"],
                    reqBody)
            def Envelope = new XmlSlurper().parseText(xml)
            def result = Envelope.Body.CheckAccount_OnlineResponse.CheckAccount_OnlineResult.text()
            return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: result)
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    @PostMapping("/toolResetOnePort")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    @FakeSuccessCommonResponse
    CommonResponse toolResetOnePort(@RequestBody ToolResetOnePortInput body) {
        try {
            String reqBody = """
<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:tem="http://tempuri.org/">
   <soapenv:Header/>
   <soapenv:Body>
      <tem:Tool_ResetOnePort>
         <tem:linetestUser>${body.linetestUser}</tem:linetestUser>
         <tem:linetestPassword>${body.linetestPassword}</tem:linetestPassword>
         <tem:input>${body.input}</tem:input>
      </tem:Tool_ResetOnePort>
   </soapenv:Body>
</soapenv:Envelope>
"""
            String xml = httpManaged.post(["url": url],
                    ["Content-Type": "text/xml;charset=UTF-8",
                     "Host"        : "10.70.53.129:6565",
                     "SOAPAction"  : "http://tempuri.org/Tool_ResetOnePort"],
                    reqBody)
            def Envelope = new XmlSlurper().parseText(xml)
            def faultcode = Envelope.Body.Fault?.faultcode?.text() ?: ""
            def faultstring = Envelope.Body.Fault?.faultstring?.text() ?: ""

            if (faultcode != "" || faultstring != "") {
                return new CommonResponse(errorCode: 1, faultCode: faultcode, faultString: faultstring, data: null)
            } else {
                def result = Envelope.Body.Tool_ResetOnePortResponse.Tool_ResetOnePortResult.text()
                return new CommonResponse(errorCode: 0, faultCode: faultcode, faultString: faultstring, data: result)
            }
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    @PostMapping("/toolSetOnePortProfile")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    @FakeSuccessCommonResponse
    CommonResponse toolSetOnePortProfile(@RequestBody ToolSetOnePortProfileInput body) {
        try {
            String reqBody = """
<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:tem="http://tempuri.org/">
   <soapenv:Header/>
   <soapenv:Body>
      <tem:Tool_SetOnePortProfile>
         <tem:linetestUser>${body.linetestUser}</tem:linetestUser>
         <tem:linetestPassword>${body.linetestPassword}</tem:linetestPassword>
         <tem:input>${body.input}</tem:input>
         <tem:profile>${body.profile}</tem:profile>
      </tem:Tool_SetOnePortProfile>
   </soapenv:Body>
</soapenv:Envelope>
"""
            String xml = httpManaged.post(["url": url],
                    ["Content-Type": "text/xml;charset=UTF-8",
                     "Host"        : "10.70.53.129:6565",
                     "SOAPAction"  : "http://tempuri.org/Tool_SetOnePortProfile"],
                    reqBody)
            def Envelope = new XmlSlurper().parseText(xml)
            def faultcode = Envelope.Body.Fault?.faultcode?.text() ?: ""
            def faultstring = Envelope.Body.Fault?.faultstring?.text() ?: ""

            if (faultcode != "" || faultstring != "") {
                return new CommonResponse(errorCode: 1, faultCode: faultcode, faultString: faultstring, data: null)
            } else {
                def result = Envelope.Body.Tool_SetOnePortProfileResponse.Tool_SetOnePortProfileResult.text()
                return new CommonResponse(errorCode: 0, faultCode: faultcode, faultString: faultstring, data: result)
            }
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    @PostMapping("/resetPassOntIgate040H")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    @FakeSuccessCommonResponse
    CommonResponse resetPassOntIgate040H(@RequestBody ResetPassOntIgate040HInput body) {
        try {
            String reqBody = """
<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:tem="http://tempuri.org/">
   <soapenv:Header/>
   <soapenv:Body>
      <tem:Reset_Pass_Ont_Igate040H>
         <tem:vslid>${body.vslid}</tem:vslid>
         <tem:userOnt>${body.userOnt}</tem:userOnt>
         <tem:passOnt>${body.passOnt}</tem:passOnt>
      </tem:Reset_Pass_Ont_Igate040H>
   </soapenv:Body>
</soapenv:Envelope>
"""
            String xml = httpManaged.post(["url": url],
                    ["Content-Type": "text/xml;charset=UTF-8",
                     "Host"        : "10.70.53.129:6565",
                     "SOAPAction"  : "http://tempuri.org/Reset_Pass_Ont_Igate040H"],
                    reqBody)
            def Envelope = new XmlSlurper().parseText(xml)
            def result = Envelope.Body.Reset_Pass_Ont_Igate040HResponse.Reset_Pass_Ont_Igate040HResult.text()
            return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: result)
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    @PostMapping("/resetPassOnt")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    @FakeSuccessCommonResponse
    CommonResponse resetPassOnt(@RequestBody ResetPassOntIgate040HInput body) {
        try {
            String reqBody = """
<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:tem="http://tempuri.org/">
   <soapenv:Header/>
   <soapenv:Body>
      <tem:Reset_Pass_Ont>
         <!--Optional:-->
         <tem:vslid>${body.vslid}</tem:vslid>
         <!--Optional:-->
         <tem:userOnt>${body.userOnt}</tem:userOnt>
         <!--Optional:-->
         <tem:passOnt>${body.passOnt}</tem:passOnt>
      </tem:Reset_Pass_Ont>
   </soapenv:Body>
</soapenv:Envelope>
"""
            String xml = httpManaged.post(["url": url],
                    ["Content-Type": "text/xml;charset=UTF-8",
                     "Host"        : "10.70.53.129:6565",
                     "SOAPAction"  : "http://tempuri.org/Reset_Pass_Ont"],
                    reqBody)
            def Envelope = new XmlSlurper().parseText(xml)
            def result = Envelope.Body.Reset_Pass_OntResponse.Reset_Pass_OntResult.text()
            return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: result)
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    @PostMapping("/testPort")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
//    @FakeSuccessCommonResponse
    CommonResponse testPort(@RequestBody TestPortInput body) {
        try {
            String reqBody = """
<soap:Envelope xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/" xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
   <soap:Body>
      <TestPort xmlns="http://tempuri.org/">
         <linetestUser>${body.linetestUser}</linetestUser>
         <linetestPassword>${body.linetestPassword}</linetestPassword>
         <input>${body.input}</input>
         <getMac>${body.mac}</getMac>
      </TestPort>
   </soap:Body>
</soap:Envelope>
"""
            String xml = httpManaged.post(["url": url],
                    ["Content-Type": "text/xml;charset=UTF-8",
                     "Host"        : "10.70.53.129:6565",
                     "SOAPAction"  : "http://tempuri.org/TestPort"],
                    reqBody)
            def Envelope = new XmlSlurper().parseText(xml)
            def result = Envelope.Body.TestPortResponse.TestPortResult.text()
            return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: result)
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    @PostMapping("/testPortV2")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
//    @FakeSuccessCommonResponse
    CommonResponse testPortV2(@RequestBody TestPortInput body) {
        try {
            String reqBody = """
<soap:Envelope xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/" xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
   <soap:Body>
      <TestPort xmlns="http://tempuri.org/">
         <linetestUser>${body.linetestUser}</linetestUser>
         <linetestPassword>${body.linetestPassword}</linetestPassword>
         <input>${body.input}</input>
         <getMac>${body.mac}</getMac>
      </TestPort>
   </soap:Body>
</soap:Envelope>
"""
            String xml = httpManaged.post(["url": url],
                    ["Content-Type": "text/xml;charset=UTF-8",
                     "Host"        : "10.70.53.129:6565",
                     "SOAPAction"  : "http://tempuri.org/TestPort"],
                    reqBody)
            def Envelope = new XmlSlurper().parseText(xml)
            def faultcode = Envelope.Body.Fault?.faultcode?.text() ?: ""
            def faultstring = Envelope.Body.Fault?.faultstring?.text() ?: ""

            if (faultcode != "" || faultstring != "") {
                return new CommonResponse(errorCode: 1, faultCode: faultcode, faultString: faultstring, data: null)
            } else {
                def result = new JsonSlurper().parseText(XML.toJSONObject(xml).toString())
                def response = result["soap:Envelope"]["soap:Body"]["TestPortResponse"]["TestPortResult"]["diffgr:diffgram"]["NewDataSet"]["ketqua"]
                return new CommonResponse(errorCode: 0, faultCode: faultcode, faultString: faultstring, data: response)
            }
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    @PostMapping("/getPort_QuanlityV2")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
//    @FakeSuccessCommonResponse
    CommonResponse getPort_QuanlityV2(@RequestBody GetPort_QuanlityV2Input body) {
        try {
            String reqBody = """
<soap:Envelope xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/" xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
   <soap:Body>
      <GetPort_QuanlityV2 xmlns="http://tempuri.org/">
         <linetestUser>${body.linetestUser}</linetestUser>
         <linetestPassword>${body.linetestPassword}</linetestPassword>
         <input>${body.input}</input>
         <att_gpon>${body.att_gpon}</att_gpon>
         <att_adsl>${body.att_adsl}</att_adsl>
         <snr_adsl>${body.snr_adsl}</snr_adsl>
         <sip>${body.ip}</sip>
         <srack>${body.rack}</srack>
         <sshelf>${body.shelf}</sshelf>
         <sslot>${body.slot}</sslot>
         <sport>${body.port}</sport>
         <sonu_id>${body.onu_id}</sonu_id>
         <server_dk>${body.server_dk}</server_dk>
      </GetPort_QuanlityV2>
   </soap:Body>
</soap:Envelope>
"""
            String xml = httpManaged.post(["url": url],
                    ["Content-Type": "text/xml;charset=UTF-8",
                     "Host"        : "10.70.53.129:6565",
                     "SOAPAction"  : "http://tempuri.org/GetPort_QuanlityV2"],
                    reqBody)
            def Envelope = new XmlSlurper().parseText(xml)
            def result = Envelope.Body.GetPort_QuanlityV2Response.GetPort_QuanlityV2Result.text()
            return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: result)
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    @PostMapping("/getPortStatus")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
//    @FakeSuccessCommonResponse
    CommonResponse getPortStatus(@RequestBody GetPortStatusInput body) {
        try {
            String reqBody = """
<soap:Envelope xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/" xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
   <soap:Body>
      <GetPortStatus xmlns="http://tempuri.org/">
         <linetestUser>${body.linetestUser}</linetestUser>
         <linetestPassword>${body.linetestPassword}</linetestPassword>
         <input>${body.input}</input>
         <getMac>${body.mac}</getMac>
      </GetPortStatus>
   </soap:Body>
</soap:Envelope>
"""
            String xml = httpManaged.post(["url": url],
                    ["Content-Type": "text/xml;charset=UTF-8",
                     "Host"        : "10.70.53.129:6565",
                     "SOAPAction"  : "http://tempuri.org/GetPortStatus"],
                    reqBody)
            def Envelope = new XmlSlurper().parseText(xml)
            def result = Envelope.Body.GetPortStatusResponse.GetPortStatusResult.text()
            return new CommonResponse(errorCode: 0, faultCode: "", faultString: "", data: result)
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    @PostMapping("/xemTemp")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
//    @FakeSuccessCommonResponse
    CommonResponse xemTemp(@RequestBody XemTempInput body) {
        try {
            String reqBody = """
<soap:Envelope xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/">
  <soap:Body>
    <Xem_Temp xmlns="http://tempuri.org/">
      <param>
        <IDLenh>${body?.idLenh}</IDLenh>
        <VTT>${body?.vtt}</VTT>
        <ip>${body?.ip}</ip>
        <TelnetUser>${body?.telnetUser}</TelnetUser>
        <Tcon2_Profile>${body?.tcon2_Profile}</Tcon2_Profile>
        <cardONU>${body?.cardONU}</cardONU>
        <Queue0>${body?.queue0}</Queue0>
        <Queue1>${body?.queue1}</Queue1>
        <TelnetPass>${body?.telnetPass}</TelnetPass>
        <enable_pass>${body?.enable_pass}</enable_pass>
        <frame>${body?.frame}</frame>
        <ponslotid>${body?.ponslotid}</ponslotid>
        <PonPort>${body?.ponPort}</PonPort>
        <onuid>${body?.onuid}</onuid>
        <ma_men>${body?.ma_men}</ma_men>
        <acc_kh>${body?.acc_kh}</acc_kh>
        <pass_kh>${body?.pass_kh}</pass_kh>
        <ProfileName>${body?.profileName}</ProfileName>
        <traffic_Prf_Up>${body?.traffic_Prf_Up}</traffic_Prf_Up>
        <traffic_Prf_Dw>${body?.traffic_Prf_Dw}</traffic_Prf_Dw>
        <vlan>${body?.vlan}</vlan>
        <vlan_vod>${body?.vlan_vod}</vlan_vod>
        <Description>${body?.description}</Description>
        <onuport_mytv>${body?.onuport_mytv}</onuport_mytv>
        <loai>${body?.loai}</loai>
        <Brand>${body?.brand}</Brand>
        <onu_type>${body?.onu_type}</onu_type>
        <MYTVHD_UP>${body?.mytvhd_up}</MYTVHD_UP>
        <MYTVHD_DW>${body?.mytvhd_dw}</MYTVHD_DW>
        <Voip_TconProfile>${body?.voip_tconProfile}</Voip_TconProfile>
        <Gem_Ims_Profile_DWN>${body?.gem_ims_profile_dwn}</Gem_Ims_Profile_DWN>
        <Gem_Ims_profile_UP>${body?.gem_ims_profile_up}</Gem_Ims_profile_UP>
        <voip_vlan>${body?.voip_vlan}</voip_vlan>
        <voip_userid>${body?.voip_userid}</voip_userid>
        <voip_username>${body?.voip_username}</voip_username>
        <voip_password>${body?.voip_password}</voip_password>
        <BoSungMyTV>${body?.boSungMyTV}</BoSungMyTV>
        <svlan>${body?.svlan}</svlan>
        <svlan_tr069>${body?.svlan_tr069}</svlan_tr069>
        <SrvConProfile>${body?.srvConProfile}</SrvConProfile>
        <Outbound_IMS_Profile>${body?.outbound_ims_profile}</Outbound_IMS_Profile>
        <Inbound_IMS_Profile>${body?.inbound_ims_profile}</Inbound_IMS_Profile>
        <IMS_Name>${body?.ims_name}</IMS_Name>
        <IMS_ServiceName>${body?.ims_servicename}</IMS_ServiceName>
        <IMS_Description>${body?.ims_description}</IMS_Description>
        <MyTVDescription>${body?.myTVDescription}</MyTVDescription>
        <IPBras>${body?.ipBras}</IPBras>
        <IPVPN>${body?.ipVPN}</IPVPN>
      </param>
      <vLenh_BNM_ID>${body?.vlenh_bnm_id}</vLenh_BNM_ID>
    </Xem_Temp>
  </soap:Body>
</soap:Envelope>
"""
            String xml = httpManaged.post(["url": url],
                    ["Content-Type": "text/xml;charset=UTF-8",
                     "Host"        : "10.70.53.129",
                     "SOAPAction"  : "http://tempuri.org/Xem_Temp"],
                    reqBody)
            def Envelope = new XmlSlurper().parseText(xml)
            def faultcode = Envelope.Body.Fault?.faultcode?.text() ?: ""
            def faultstring = Envelope.Body.Fault?.faultstring?.text() ?: ""

            if (faultcode != "" || faultstring != "") {
                return new CommonResponse(errorCode: 1, faultCode: faultcode, faultString: faultstring, data: null)
            } else {
                def result = Envelope.Body.Xem_TempResponse.Xem_TempResult.text()
                return new CommonResponse(errorCode: 0, faultCode: faultcode, faultString: faultstring, data: result)
            }
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    @PostMapping("/cauHinh_FTTH_MYTV_GPON_Temp")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    @FakeSuccessCommonResponse
    CommonResponse cauHinh_FTTH_MYTV_GPON_Temp(@RequestBody CauHinh_FTTH_MYTV_GPON_Temp_Input body) {
        try {
            String reqBody = """
<soap:Envelope xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/">
  <soap:Body>
    <CauHinh_FTTH_MYTV_GPON_Temp xmlns="http://tempuri.org/">
      <param>
        <IDLenh>${body?.idLenh}</IDLenh>
        <VTT>${body?.vtt}</VTT>
        <ip>${body?.ip}</ip>
        <TelnetUser>${body?.telnetUser}</TelnetUser>
        <Tcon2_Profile>${body?.tcon2_Profile}</Tcon2_Profile>
        <cardONU>${body?.cardONU}</cardONU>
        <Queue0>${body?.queue0}</Queue0>
        <Queue1>${body?.queue1}</Queue1>
        <TelnetPass>${body?.telnetPass}</TelnetPass>
        <enable_pass>${body?.enable_pass}</enable_pass>
        <frame>${body?.frame}</frame>
        <ponslotid>${body?.ponslotid}</ponslotid>
        <PonPort>${body?.ponPort}</PonPort>
        <onuid>${body?.onuid}</onuid>
        <ma_men>${body?.ma_men}</ma_men>
        <acc_kh>${body?.acc_kh}</acc_kh>
        <pass_kh>${body?.pass_kh}</pass_kh>
        <ProfileName>${body?.profileName}</ProfileName>
        <traffic_Prf_Up>${body?.traffic_Prf_Up}</traffic_Prf_Up>
        <traffic_Prf_Dw>${body?.traffic_Prf_Dw}</traffic_Prf_Dw>
        <vlan>${body?.vlan}</vlan>
        <vlan_vod>${body?.vlan_vod}</vlan_vod>
        <Description>${body?.description}</Description>
        <onuport_mytv>${body?.onuport_mytv}</onuport_mytv>
        <loai>${body?.loai}</loai>
        <Brand>${body?.brand}</Brand>
        <onu_type>${body?.onu_type}</onu_type>
        <MYTVHD_UP>${body?.mytvhd_up}</MYTVHD_UP>
        <MYTVHD_DW>${body?.mytvhd_dw}</MYTVHD_DW>
        <Voip_TconProfile>${body?.voip_tconProfile}</Voip_TconProfile>
        <Gem_Ims_Profile_DWN>${body?.gem_ims_profile_dwn}</Gem_Ims_Profile_DWN>
        <Gem_Ims_profile_UP>${body?.gem_ims_profile_up}</Gem_Ims_profile_UP>
        <voip_vlan>${body?.voip_vlan}</voip_vlan>
        <voip_userid>${body?.voip_userid}</voip_userid>
        <voip_username>${body?.voip_username}</voip_username>
        <voip_password>${body?.voip_password}</voip_password>
        <BoSungMyTV>${body?.boSungMyTV}</BoSungMyTV>
        <svlan>${body?.svlan}</svlan>
        <svlan_tr069>${body?.svlan_tr069}</svlan_tr069>
        <SrvConProfile>${body?.srvConProfile}</SrvConProfile>
        <Outbound_IMS_Profile>${body?.outbound_ims_profile}</Outbound_IMS_Profile>
        <Inbound_IMS_Profile>${body?.inbound_ims_profile}</Inbound_IMS_Profile>
        <IMS_Name>${body?.ims_name}</IMS_Name>
        <IMS_ServiceName>${body?.ims_servicename}</IMS_ServiceName>
        <IMS_Description>${body?.ims_description}</IMS_Description>
        <MyTVDescription>${body?.myTVDescription}</MyTVDescription>
        <IPBras>${body?.ipBras}</IPBras>
        <IPVPN>${body?.ipVPN}</IPVPN>
      </param>
    </CauHinh_FTTH_MYTV_GPON_Temp>
  </soap:Body>
</soap:Envelope>
"""
            String xml = httpManaged.post(["url": url],
                    ["Content-Type": "text/xml;charset=UTF-8",
                     "Host"        : "10.70.53.129",
                     "SOAPAction"  : "http://tempuri.org/CauHinh_FTTH_MYTV_GPON_Temp"],
                    reqBody)
            def Envelope = new XmlSlurper().parseText(xml)
            def faultcode = Envelope.Body.Fault?.faultcode?.text() ?: ""
            def faultstring = Envelope.Body.Fault?.faultstring?.text() ?: ""

            if (faultcode != "" || faultstring != "") {
                return new CommonResponse(errorCode: 1, faultCode: faultcode, faultString: faultstring, data: null)
            } else {
                def result = Envelope.Body.CauHinh_FTTH_MYTV_GPON_TempResponse.CauHinh_FTTH_MYTV_GPON_TempResult.text()
                return new CommonResponse(errorCode: 0, faultCode: faultcode, faultString: faultstring, data: result)
            }
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    @PostMapping("/huyCauHinhPON_Temp")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    @FakeSuccessCommonResponse
    CommonResponse huyCauHinhPON_Temp(@RequestBody CauHinh_FTTH_MYTV_GPON_Temp_Input body) {
        try {
            String reqBody = """
<soap:Envelope xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/">
  <soap:Body>
    <HuyCauHinhPON_Temp xmlns="http://tempuri.org/">
      <param>
        <IDLenh>${body?.idLenh}</IDLenh>
        <VTT>${body?.vtt}</VTT>
        <ip>${body?.ip}</ip>
        <TelnetUser>${body?.telnetUser}</TelnetUser>
        <Tcon2_Profile>${body?.tcon2_Profile}</Tcon2_Profile>
        <cardONU>${body?.cardONU}</cardONU>
        <Queue0>${body?.queue0}</Queue0>
        <Queue1>${body?.queue1}</Queue1>
        <TelnetPass>${body?.telnetPass}</TelnetPass>
        <enable_pass>${body?.enable_pass}</enable_pass>
        <frame>${body?.frame}</frame>
        <ponslotid>${body?.ponslotid}</ponslotid>
        <PonPort>${body?.ponPort}</PonPort>
        <onuid>${body?.onuid}</onuid>
        <ma_men>${body?.ma_men}</ma_men>
        <acc_kh>${body?.acc_kh}</acc_kh>
        <pass_kh>${body?.pass_kh}</pass_kh>
        <ProfileName>${body?.profileName}</ProfileName>
        <traffic_Prf_Up>${body?.traffic_Prf_Up}</traffic_Prf_Up>
        <traffic_Prf_Dw>${body?.traffic_Prf_Dw}</traffic_Prf_Dw>
        <vlan>${body?.vlan}</vlan>
        <vlan_vod>${body?.vlan_vod}</vlan_vod>
        <Description>${body?.description}</Description>
        <onuport_mytv>${body?.onuport_mytv}</onuport_mytv>
        <loai>${body?.loai}</loai>
        <Brand>${body?.brand}</Brand>
        <onu_type>${body?.onu_type}</onu_type>
        <MYTVHD_UP>${body?.mytvhd_up}</MYTVHD_UP>
        <MYTVHD_DW>${body?.mytvhd_dw}</MYTVHD_DW>
        <Voip_TconProfile>${body?.voip_tconProfile}</Voip_TconProfile>
        <Gem_Ims_Profile_DWN>${body?.gem_ims_profile_dwn}</Gem_Ims_Profile_DWN>
        <Gem_Ims_profile_UP>${body?.gem_ims_profile_up}</Gem_Ims_profile_UP>
        <voip_vlan>${body?.voip_vlan}</voip_vlan>
        <voip_userid>${body?.voip_userid}</voip_userid>
        <voip_username>${body?.voip_username}</voip_username>
        <voip_password>${body?.voip_password}</voip_password>
        <BoSungMyTV>${body?.boSungMyTV}</BoSungMyTV>
        <svlan>${body?.svlan}</svlan>
        <svlan_tr069>${body?.svlan_tr069}</svlan_tr069>
        <SrvConProfile>${body?.srvConProfile}</SrvConProfile>
        <Outbound_IMS_Profile>${body?.outbound_ims_profile}</Outbound_IMS_Profile>
        <Inbound_IMS_Profile>${body?.inbound_ims_profile}</Inbound_IMS_Profile>
        <IMS_Name>${body?.ims_name}</IMS_Name>
        <IMS_ServiceName>${body?.ims_servicename}</IMS_ServiceName>
        <IMS_Description>${body?.ims_description}</IMS_Description>
        <MyTVDescription>${body?.myTVDescription}</MyTVDescription>
        <IPBras>${body?.ipBras}</IPBras>
        <IPVPN>${body?.ipVPN}</IPVPN>
      </param>
    </HuyCauHinhPON_Temp>
  </soap:Body>
</soap:Envelope>
"""
            String xml = httpManaged.post(["url": url],
                    ["Content-Type": "text/xml;charset=UTF-8",
                     "Host"        : "10.70.53.129",
                     "SOAPAction"  : "http://tempuri.org/HuyCauHinhPON_Temp"],
                    reqBody)
            def Envelope = new XmlSlurper().parseText(xml)
            def faultcode = Envelope.Body.Fault?.faultcode?.text() ?: ""
            def faultstring = Envelope.Body.Fault?.faultstring?.text() ?: ""

            if (faultcode != "" || faultstring != "") {
                return new CommonResponse(errorCode: 1, faultCode: faultcode, faultString: faultstring, data: null)
            } else {
                def result = Envelope.Body.HuyCauHinhPON_TempResponse.HuyCauHinhPON_TempResult.text()
                return new CommonResponse(errorCode: 0, faultCode: faultcode, faultString: faultstring, data: result)
            }
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    @PostMapping("/cauHinh_Internet_GPON_temp")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    @FakeSuccessCommonResponse
    CommonResponse cauHinh_Internet_GPON_temp(@RequestBody CauHinh_FTTH_MYTV_GPON_Temp_Input body) {
        try {
            String reqBody = """
<soap:Envelope xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/">
  <soap:Body>
    <CauHinh_Internet_GPON_temp xmlns="http://tempuri.org/">
      <param>
        <IDLenh>${body?.idLenh}</IDLenh>
        <VTT>${body?.vtt}</VTT>
        <ip>${body?.ip}</ip>
        <TelnetUser>${body?.telnetUser}</TelnetUser>
        <Tcon2_Profile>${body?.tcon2_Profile}</Tcon2_Profile>
        <cardONU>${body?.cardONU}</cardONU>
        <Queue0>${body?.queue0}</Queue0>
        <Queue1>${body?.queue1}</Queue1>
        <TelnetPass>${body?.telnetPass}</TelnetPass>
        <enable_pass>${body?.enable_pass}</enable_pass>
        <frame>${body?.frame}</frame>
        <ponslotid>${body?.ponslotid}</ponslotid>
        <PonPort>${body?.ponPort}</PonPort>
        <onuid>${body?.onuid}</onuid>
        <ma_men>${body?.ma_men}</ma_men>
        <acc_kh>${body?.acc_kh}</acc_kh>
        <pass_kh>${body?.pass_kh}</pass_kh>
        <ProfileName>${body?.profileName}</ProfileName>
        <traffic_Prf_Up>${body?.traffic_Prf_Up}</traffic_Prf_Up>
        <traffic_Prf_Dw>${body?.traffic_Prf_Dw}</traffic_Prf_Dw>
        <vlan>${body?.vlan}</vlan>
        <vlan_vod>${body?.vlan_vod}</vlan_vod>
        <Description>${body?.description}</Description>
        <onuport_mytv>${body?.onuport_mytv}</onuport_mytv>
        <loai>${body?.loai}</loai>
        <Brand>${body?.brand}</Brand>
        <onu_type>${body?.onu_type}</onu_type>
        <MYTVHD_UP>${body?.mytvhd_up}</MYTVHD_UP>
        <MYTVHD_DW>${body?.mytvhd_dw}</MYTVHD_DW>
        <Voip_TconProfile>${body?.voip_tconProfile}</Voip_TconProfile>
        <Gem_Ims_Profile_DWN>${body?.gem_ims_profile_dwn}</Gem_Ims_Profile_DWN>
        <Gem_Ims_profile_UP>${body?.gem_ims_profile_up}</Gem_Ims_profile_UP>
        <voip_vlan>${body?.voip_vlan}</voip_vlan>
        <voip_userid>${body?.voip_userid}</voip_userid>
        <voip_username>${body?.voip_username}</voip_username>
        <voip_password>${body?.voip_password}</voip_password>
        <BoSungMyTV>${body?.boSungMyTV}</BoSungMyTV>
        <svlan>${body?.svlan}</svlan>
        <svlan_tr069>${body?.svlan_tr069}</svlan_tr069>
        <SrvConProfile>${body?.srvConProfile}</SrvConProfile>
        <Outbound_IMS_Profile>${body?.outbound_ims_profile}</Outbound_IMS_Profile>
        <Inbound_IMS_Profile>${body?.inbound_ims_profile}</Inbound_IMS_Profile>
        <IMS_Name>${body?.ims_name}</IMS_Name>
        <IMS_ServiceName>${body?.ims_servicename}</IMS_ServiceName>
        <IMS_Description>${body?.ims_description}</IMS_Description>
        <MyTVDescription>${body?.myTVDescription}</MyTVDescription>
        <IPBras>${body?.ipBras}</IPBras>
        <IPVPN>${body?.ipVPN}</IPVPN>
      </param>
    </CauHinh_Internet_GPON_temp>
  </soap:Body>
</soap:Envelope>
"""
            String xml = httpManaged.post(["url": url],
                    ["Content-Type": "text/xml;charset=UTF-8",
                     "Host"        : "10.70.53.129",
                     "SOAPAction"  : "http://tempuri.org/CauHinh_Internet_GPON_temp"],
                    reqBody)
            def Envelope = new XmlSlurper().parseText(xml)
            def faultcode = Envelope.Body.Fault?.faultcode?.text() ?: ""
            def faultstring = Envelope.Body.Fault?.faultstring?.text() ?: ""

            if (faultcode != "" || faultstring != "") {
                return new CommonResponse(errorCode: 1, faultCode: faultcode, faultString: faultstring, data: null)
            } else {
                def result = Envelope.Body.CauHinh_Internet_GPON_tempResponse.CauHinh_Internet_GPON_tempResult.text()
                return new CommonResponse(errorCode: 0, faultCode: faultcode, faultString: faultstring, data: result)
            }
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    @PostMapping("/getPort_QuanlityV2_Obj")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
//    @FakeSuccessCommonResponse
    CommonResponse getPort_QuanlityV2_Obj(@RequestBody GetPort_QuanlityV2Input body) {
        try {
            String reqBody = """
<soap:Envelope xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/" xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
   <soap:Body>
      <GetPort_QuanlityV2 xmlns="http://tempuri.org/">
         <linetestUser>${body.linetestUser}</linetestUser>
         <linetestPassword>${body.linetestPassword}</linetestPassword>
         <input>${body.input}</input>
         <att_gpon>${body.att_gpon}</att_gpon>
         <att_adsl>${body.att_adsl}</att_adsl>
         <snr_adsl>${body.snr_adsl}</snr_adsl>
         <sip>${body.ip}</sip>
         <srack>${body.rack}</srack>
         <sshelf>${body.shelf}</sshelf>
         <sslot>${body.slot}</sslot>
         <sport>${body.port}</sport>
         <sonu_id>${body.onu_id}</sonu_id>
         <server_dk>${body.server_dk}</server_dk>
      </GetPort_QuanlityV2>
   </soap:Body>
</soap:Envelope>
"""
            String xml = httpManaged.post(["url": url],
                    ["Content-Type": "text/xml;charset=UTF-8",
                     "Host"        : "10.70.53.129:6565",
                     "SOAPAction"  : "http://tempuri.org/GetPort_QuanlityV2"],
                    reqBody)
            def Envelope = new XmlSlurper().parseText(xml)
            def faultcode = Envelope.Body.Fault?.faultcode?.text() ?: ""
            def faultstring = Envelope.Body.Fault?.faultstring?.text() ?: ""

            if (faultcode != "" || faultstring != "") {
                return new CommonResponse(errorCode: 1, faultCode: faultcode, faultString: faultstring, data: null)
            } else {
                def result = new JsonSlurper().parseText(XML.toJSONObject(xml).toString())
                def response = result["soap:Envelope"]["soap:Body"]["GetPort_QuanlityV2Response"]["GetPort_QuanlityV2Result"]["diffgr:diffgram"]["NewDataSet"]["ketqua"]
                return new CommonResponse(errorCode: 0, faultCode: faultcode, faultString: faultstring, data: response)
            }
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    @PostMapping("/cauHinh_MyTV_GPON_Temp")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    @FakeSuccessCommonResponse
    CommonResponse cauHinh_MyTV_GPON_Temp(@RequestBody CauHinh_FTTH_MYTV_GPON_Temp_Input body) {
        try {
            String reqBody = """
<soap:Envelope xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/">
  <soap:Body>
    <CauHinh_MyTV_GPON_Temp xmlns="http://tempuri.org/">
      <param>
        <IDLenh>${body?.idLenh}</IDLenh>
        <VTT>${body?.vtt}</VTT>
        <ip>${body?.ip}</ip>
        <TelnetUser>${body?.telnetUser}</TelnetUser>
        <Tcon2_Profile>${body?.tcon2_Profile}</Tcon2_Profile>
        <cardONU>${body?.cardONU}</cardONU>
        <Queue0>${body?.queue0}</Queue0>
        <Queue1>${body?.queue1}</Queue1>
        <TelnetPass>${body?.telnetPass}</TelnetPass>
        <enable_pass>${body?.enable_pass}</enable_pass>
        <frame>${body?.frame}</frame>
        <ponslotid>${body?.ponslotid}</ponslotid>
        <PonPort>${body?.ponPort}</PonPort>
        <onuid>${body?.onuid}</onuid>
        <ma_men>${body?.ma_men}</ma_men>
        <acc_kh>${body?.acc_kh}</acc_kh>
        <pass_kh>${body?.pass_kh}</pass_kh>
        <ProfileName>${body?.profileName}</ProfileName>
        <traffic_Prf_Up>${body?.traffic_Prf_Up}</traffic_Prf_Up>
        <traffic_Prf_Dw>${body?.traffic_Prf_Dw}</traffic_Prf_Dw>
        <vlan>${body?.vlan}</vlan>
        <vlan_vod>${body?.vlan_vod}</vlan_vod>
        <Description>${body?.description}</Description>
        <onuport_mytv>${body?.onuport_mytv}</onuport_mytv>
        <loai>${body?.loai}</loai>
        <Brand>${body?.brand}</Brand>
        <onu_type>${body?.onu_type}</onu_type>
        <MYTVHD_UP>${body?.mytvhd_up}</MYTVHD_UP>
        <MYTVHD_DW>${body?.mytvhd_dw}</MYTVHD_DW>
        <Voip_TconProfile>${body?.voip_tconProfile}</Voip_TconProfile>
        <Gem_Ims_Profile_DWN>${body?.gem_ims_profile_dwn}</Gem_Ims_Profile_DWN>
        <Gem_Ims_profile_UP>${body?.gem_ims_profile_up}</Gem_Ims_profile_UP>
        <voip_vlan>${body?.voip_vlan}</voip_vlan>
        <voip_userid>${body?.voip_userid}</voip_userid>
        <voip_username>${body?.voip_username}</voip_username>
        <voip_password>${body?.voip_password}</voip_password>
        <BoSungMyTV>${body?.boSungMyTV}</BoSungMyTV>
        <svlan>${body?.svlan}</svlan>
        <svlan_tr069>${body?.svlan_tr069}</svlan_tr069>
        <SrvConProfile>${body?.srvConProfile}</SrvConProfile>
        <Outbound_IMS_Profile>${body?.outbound_ims_profile}</Outbound_IMS_Profile>
        <Inbound_IMS_Profile>${body?.inbound_ims_profile}</Inbound_IMS_Profile>
        <IMS_Name>${body?.ims_name}</IMS_Name>
        <IMS_ServiceName>${body?.ims_servicename}</IMS_ServiceName>
        <IMS_Description>${body?.ims_description}</IMS_Description>
        <MyTVDescription>${body?.myTVDescription}</MyTVDescription>
        <IPBras>${body?.ipBras}</IPBras>
        <IPVPN>${body?.ipVPN}</IPVPN>
      </param>
    </CauHinh_MyTV_GPON_Temp>
  </soap:Body>
</soap:Envelope>
"""
            String xml = httpManaged.post(["url": url],
                    ["Content-Type": "text/xml;charset=UTF-8",
                     "Host"        : "10.70.53.129",
                     "SOAPAction"  : "http://tempuri.org/CauHinh_MyTV_GPON_Temp"],
                    reqBody)
            def Envelope = new XmlSlurper().parseText(xml)
            def faultcode = Envelope.Body.Fault?.faultcode?.text() ?: ""
            def faultstring = Envelope.Body.Fault?.faultstring?.text() ?: ""

            if (faultcode != "" || faultstring != "") {
                return new CommonResponse(errorCode: 1, faultCode: faultcode, faultString: faultstring, data: null)
            } else {
                def result = Envelope.Body.CauHinh_MyTV_GPON_TempResponse.CauHinh_MyTV_GPON_TempResult.text()
                return new CommonResponse(errorCode: 0, faultCode: faultcode, faultString: faultstring, data: result)
            }
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    @PostMapping("/cauHinh_IMS_GPON_Temp")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    @FakeSuccessCommonResponse
    CommonResponse cauHinh_IMS_GPON_Temp(@RequestBody CauHinh_FTTH_MYTV_GPON_Temp_Input body) {
        try {
            String reqBody = """
<soap:Envelope xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/">
  <soap:Body>
    <CauHinh_IMS_GPON_Temp xmlns="http://tempuri.org/">
      <param>
        <IDLenh>${body?.idLenh}</IDLenh>
        <VTT>${body?.vtt}</VTT>
        <ip>${body?.ip}</ip>
        <TelnetUser>${body?.telnetUser}</TelnetUser>
        <Tcon2_Profile>${body?.tcon2_Profile}</Tcon2_Profile>
        <cardONU>${body?.cardONU}</cardONU>
        <Queue0>${body?.queue0}</Queue0>
        <Queue1>${body?.queue1}</Queue1>
        <TelnetPass>${body?.telnetPass}</TelnetPass>
        <enable_pass>${body?.enable_pass}</enable_pass>
        <frame>${body?.frame}</frame>
        <ponslotid>${body?.ponslotid}</ponslotid>
        <PonPort>${body?.ponPort}</PonPort>
        <onuid>${body?.onuid}</onuid>
        <ma_men>${body?.ma_men}</ma_men>
        <acc_kh>${body?.acc_kh}</acc_kh>
        <pass_kh>${body?.pass_kh}</pass_kh>
        <ProfileName>${body?.profileName}</ProfileName>
        <traffic_Prf_Up>${body?.traffic_Prf_Up}</traffic_Prf_Up>
        <traffic_Prf_Dw>${body?.traffic_Prf_Dw}</traffic_Prf_Dw>
        <vlan>${body?.vlan}</vlan>
        <vlan_vod>${body?.vlan_vod}</vlan_vod>
        <Description>${body?.description}</Description>
        <onuport_mytv>${body?.onuport_mytv}</onuport_mytv>
        <loai>${body?.loai}</loai>
        <Brand>${body?.brand}</Brand>
        <onu_type>${body?.onu_type}</onu_type>
        <MYTVHD_UP>${body?.mytvhd_up}</MYTVHD_UP>
        <MYTVHD_DW>${body?.mytvhd_dw}</MYTVHD_DW>
        <Voip_TconProfile>${body?.voip_tconProfile}</Voip_TconProfile>
        <Gem_Ims_Profile_DWN>${body?.gem_ims_profile_dwn}</Gem_Ims_Profile_DWN>
        <Gem_Ims_profile_UP>${body?.gem_ims_profile_up}</Gem_Ims_profile_UP>
        <voip_vlan>${body?.voip_vlan}</voip_vlan>
        <voip_userid>${body?.voip_userid}</voip_userid>
        <voip_username>${body?.voip_username}</voip_username>
        <voip_password>${body?.voip_password}</voip_password>
        <BoSungMyTV>${body?.boSungMyTV}</BoSungMyTV>
        <svlan>${body?.svlan}</svlan>
        <svlan_tr069>${body?.svlan_tr069}</svlan_tr069>
        <SrvConProfile>${body?.srvConProfile}</SrvConProfile>
        <Outbound_IMS_Profile>${body?.outbound_ims_profile}</Outbound_IMS_Profile>
        <Inbound_IMS_Profile>${body?.inbound_ims_profile}</Inbound_IMS_Profile>
        <IMS_Name>${body?.ims_name}</IMS_Name>
        <IMS_ServiceName>${body?.ims_servicename}</IMS_ServiceName>
        <IMS_Description>${body?.ims_description}</IMS_Description>
        <MyTVDescription>${body?.myTVDescription}</MyTVDescription>
        <IPBras>${body?.ipBras}</IPBras>
        <IPVPN>${body?.ipVPN}</IPVPN>
      </param>
    </CauHinh_IMS_GPON_Temp>
  </soap:Body>
</soap:Envelope>
"""
            String xml = httpManaged.post(["url": url],
                    ["Content-Type": "text/xml;charset=UTF-8",
                     "Host"        : "10.70.53.129",
                     "SOAPAction"  : "http://tempuri.org/CauHinh_IMS_GPON_Temp"],
                    reqBody)
            def Envelope = new XmlSlurper().parseText(xml)
            def faultcode = Envelope.Body.Fault?.faultcode?.text() ?: ""
            def faultstring = Envelope.Body.Fault?.faultstring?.text() ?: ""

            if (faultcode != "" || faultstring != "") {
                return new CommonResponse(errorCode: 1, faultCode: faultcode, faultString: faultstring, data: null)
            } else {
                def result = Envelope.Body.CauHinh_IMS_GPON_TempResponse.CauHinh_IMS_GPON_TempResult.text()
                return new CommonResponse(errorCode: 0, faultCode: faultcode, faultString: faultstring, data: result)
            }
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    @PostMapping("/khoaMay_PON")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    @FakeSuccessCommonResponse
    CommonResponse khoaMay_PON(@RequestBody KhoaMay_PON_Input body) {
        try {
            String reqBody = """
<soap:Envelope xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/">
  <soap:Body>
    <KhoaMay_PON xmlns="http://tempuri.org/">
      <ipgpon>${body?.ipgpon}</ipgpon>
      <frame>${body?.frame}</frame>
      <slot>${body?.slot}</slot>
      <port>${body?.port}</port>
      <ponid>${body?.ponid}</ponid>
      <ontid>${body?.ontid}</ontid>
      <loaigpon>${body?.loaigpon}</loaigpon>
      <comm>${body?.comm}</comm>
    </KhoaMay_PON>
  </soap:Body>
</soap:Envelope>
"""
            String xml = httpManaged.post(["url": url],
                    ["Content-Type": "text/xml;charset=UTF-8",
                     "Host"        : "10.70.53.129",
                     "SOAPAction"  : "http://tempuri.org/KhoaMay_PON"],
                    reqBody)
            def Envelope = new XmlSlurper().parseText(xml)
            def faultcode = Envelope.Body.Fault?.faultcode?.text() ?: ""
            def faultstring = Envelope.Body.Fault?.faultstring?.text() ?: ""

            if (faultcode != "" || faultstring != "") {
                return new CommonResponse(errorCode: 1, faultCode: faultcode, faultString: faultstring, data: null)
            } else {
                def result = Envelope.Body.KhoaMay_PONResponse.KhoaMay_PONResult.text()
                return new CommonResponse(errorCode: 0, faultCode: faultcode, faultString: faultstring, data: result)
            }
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    @PostMapping("/moMay_PON")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    @FakeSuccessCommonResponse
    CommonResponse moMay_PON(@RequestBody KhoaMay_PON_Input body) {
        try {
            String reqBody = """
<soap:Envelope xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/">
  <soap:Body>
    <MoMay_PON xmlns="http://tempuri.org/">
      <ipgpon>${body?.ipgpon}</ipgpon>
      <frame>${body?.frame}</frame>
      <slot>${body?.slot}</slot>
      <port>${body?.port}</port>
      <ponid>${body?.ponid}</ponid>
      <ontid>${body?.ontid}</ontid>
      <loaigpon>${body?.loaigpon}</loaigpon>
      <comm>${body?.comm}</comm>
    </MoMay_PON>
  </soap:Body>
</soap:Envelope>
"""
            String xml = httpManaged.post(["url": url],
                    ["Content-Type": "text/xml;charset=UTF-8",
                     "Host"        : "10.70.53.129",
                     "SOAPAction"  : "http://tempuri.org/MoMay_PON"],
                    reqBody)
            def Envelope = new XmlSlurper().parseText(xml)
            def faultcode = Envelope.Body.Fault?.faultcode?.text() ?: ""
            def faultstring = Envelope.Body.Fault?.faultstring?.text() ?: ""

            if (faultcode != "" || faultstring != "") {
                return new CommonResponse(errorCode: 1, faultCode: faultcode, faultString: faultstring, data: null)
            } else {
                def result = Envelope.Body.MoMay_PONResponse.MoMay_PONResult.text()
                return new CommonResponse(errorCode: 0, faultCode: faultcode, faultString: faultstring, data: result)
            }
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    @PostMapping("/getStatusPort")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
//    @FakeSuccessCommonResponse
    CommonResponse getStatusPort(@RequestBody GetStatusPortInput body) {
        try {
            String reqBody = """
<soap:Envelope xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/">
  <soap:Body>
    <getStatusPort xmlns="http://tempuri.org/">
      <vinput>${body?.vinput}</vinput>
    </getStatusPort>
  </soap:Body>
</soap:Envelope>
"""
            String xml = httpManaged.post(["url": url],
                    ["Content-Type": "text/xml;charset=UTF-8",
                     "Host"        : "10.70.53.129",
                     "SOAPAction"  : "http://tempuri.org/getStatusPort"],
                    reqBody)
            def Envelope = new XmlSlurper().parseText(xml)
            def faultcode = Envelope.Body.Fault?.faultcode?.text() ?: ""
            def faultstring = Envelope.Body.Fault?.faultstring?.text() ?: ""

            if (faultcode != "" || faultstring != "") {
                return new CommonResponse(errorCode: 1, faultCode: faultcode, faultString: faultstring, data: null)
            } else {
                def result = Envelope.Body.getStatusPortResponse.getStatusPortResult.text()
                return new CommonResponse(errorCode: 0, faultCode: faultcode, faultString: faultstring, data: result)
            }
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    @GetMapping("/getListProfileDSL")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
//    @FakeSuccessCommonResponse
    CommonResponse getListProfileDSL() {
        try {
            String reqBody = """
<soap:Envelope xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/">
  <soap:Body>
    <getListProfileDSL xmlns="http://tempuri.org/" />
  </soap:Body>
</soap:Envelope>
"""
            String xml = httpManaged.post(["url": url],
                    ["Content-Type": "text/xml;charset=UTF-8",
                     "Host"        : "10.70.53.129",
                     "SOAPAction"  : "http://tempuri.org/getListProfileDSL"],
                    reqBody)
            def Envelope = new XmlSlurper().parseText(xml)
            def faultcode = Envelope.Body.Fault?.faultcode?.text() ?: ""
            def faultstring = Envelope.Body.Fault?.faultstring?.text() ?: ""

            if (faultcode != "" || faultstring != "") {
                return new CommonResponse(errorCode: 1, faultCode: faultcode, faultString: faultstring, data: null)
            } else {
//                def result = Envelope.Body.getListProfileDSLResponse.getListProfileDSLResult.text()
                def result = new JsonSlurper().parseText(XML.toJSONObject(xml).toString())
                def response = result["soap:Envelope"]["soap:Body"]["getListProfileDSLResponse"]["getListProfileDSLResult"]["diffgr:diffgram"]["DocumentElement"]["listProfile"]
                return new CommonResponse(errorCode: 0, faultCode: faultcode, faultString: faultstring, data: response)
            }
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }

    @PostMapping("/toolResetPort")
    @Consumes([MediaType.APPLICATION_JSON])
    @Produces([MediaType.APPLICATION_JSON])
    @FakeSuccessCommonResponse
    CommonResponse toolResetPort(@RequestBody ToolResetPortInput body) {
        try {
            String reqBody = """
<soap:Envelope xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/">
  <soap:Body>
    <Tool_ResetPort xmlns="http://tempuri.org/">
      <linetestUser>${body?.linetestUser}</linetestUser>
      <linetestPassword>${body?.linetestPassword}</linetestPassword>
      <ip>${body?.ip}</ip>
      <port>${body?.port}</port>
      <onuID>${body?.onuID}</onuID>
    </Tool_ResetPort>
  </soap:Body>
</soap:Envelope>
"""
            String xml = httpManaged.post(["url": url],
                    ["Content-Type": "text/xml;charset=UTF-8",
                     "Host"        : "10.70.53.129",
                     "SOAPAction"  : "http://tempuri.org/Tool_ResetPort"],
                    reqBody)
            def Envelope = new XmlSlurper().parseText(xml)
            def faultcode = Envelope.Body.Fault?.faultcode?.text() ?: ""
            def faultstring = Envelope.Body.Fault?.faultstring?.text() ?: ""

            if (faultcode != "" || faultstring != "") {
                return new CommonResponse(errorCode: 1, faultCode: faultcode, faultString: faultstring, data: null)
            } else {
                def result = Envelope.Body.Tool_ResetPortResponse.Tool_ResetPortResult.text()
                return new CommonResponse(errorCode: 0, faultCode: faultcode, faultString: faultstring, data: result)
            }
        } catch (Exception e) {
            return CommonResponse.resolveException(e, loggingManaged)
        }
    }


}
