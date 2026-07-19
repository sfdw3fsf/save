package vn.vnptit.sapi.oneapp.resources.gnms;

import vn.vnptit.sapi.oneapp.models.gnms.AccWebGuiListResponse;
import vn.vnptit.sapi.oneapp.models.gnms.AddInterfaceGroupingRequest;
import vn.vnptit.sapi.oneapp.models.gnms.BaseResponse;
import vn.vnptit.sapi.oneapp.models.gnms.BoundInterfacesListResponse;
import vn.vnptit.sapi.oneapp.models.gnms.ConnectedUserListResponse;
import vn.vnptit.sapi.oneapp.models.gnms.DeleteInterfaceGroupingRequest;
import vn.vnptit.sapi.oneapp.models.gnms.DeviceParamResponse;
import vn.vnptit.sapi.oneapp.models.gnms.DeviceWan;
import vn.vnptit.sapi.oneapp.models.gnms.DeviceWanListResponse;
import vn.vnptit.sapi.oneapp.models.gnms.DeviceWirelessAdv;
import vn.vnptit.sapi.oneapp.models.gnms.DeviceWirelessAdvResponse;
import vn.vnptit.sapi.oneapp.models.gnms.DeviceWlan;
import vn.vnptit.sapi.oneapp.models.gnms.DeviceWlanListResponse;
import vn.vnptit.sapi.oneapp.models.gnms.DiagnosticResponse;
import vn.vnptit.sapi.oneapp.models.gnms.GetVoiceListResponse;
import vn.vnptit.sapi.oneapp.models.gnms.InlineObject;
import vn.vnptit.sapi.oneapp.models.gnms.InterfaceGroupingListResponse;
import java.util.List;
import vn.vnptit.sapi.oneapp.models.gnms.ListAdslDeviceResponse;
import vn.vnptit.sapi.oneapp.models.gnms.OpticalInfoListResponse;
import vn.vnptit.sapi.oneapp.models.gnms.PingResultResponse;
import vn.vnptit.sapi.oneapp.models.gnms.WanLanInterfacesListResponse;
import io.swagger.annotations.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * A delegate to be called by the {@link DeviceApiController}}.
 * Implement this interface with a {@link org.springframework.stereotype.Service} annotated class.
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen")
public interface DeviceApiDelegate {

    default Optional<NativeWebRequest> getRequest() {
        return Optional.empty();
    }

    /**
     * GET /device/delete
     * Xóa thiết bị
     *
     * @param optical Mã Optical Serial number của thiết bị (required)
     * @return Kết quả trả về là một Json object (status code 200)
     * @see DeviceApi#delete
     */
    default ResponseEntity<BaseResponse> delete(String optical) {
        getRequest().ifPresent(request -> {
            for (MediaType mediaType: MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"result\" : \"result\", \"error_code\" : \"0\", \"message\" : \"success\" }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
            }
        });
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }

    /**
     * POST /device/addBridgeWan
     * API cài đặt Wan bridge
     *
     * @param serviceDescription Brigde name (Ví dụ: br_eth0) (required)
     * @param vlanMux8021Priority Vlan 802.1P Priority (required)
     * @param vlanMuxID Vlan ID (required)
     * @param enableWan Enable wan (required)
     * @param optical Số serial của thiết bị (required)
     * @return Kết quả trả về là object Json chứa kết quả (status code 200)
     * @see DeviceApi#deviceAddBridgeWan
     */
    default ResponseEntity<BaseResponse> deviceAddBridgeWan(String serviceDescription,
        Integer vlanMux8021Priority,
        Integer vlanMuxID,
        Boolean enableWan,
        String optical) {
        getRequest().ifPresent(request -> {
            for (MediaType mediaType: MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"result\" : \"result\", \"error_code\" : \"0\", \"message\" : \"success\" }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
            }
        });
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }

    /**
     * POST /device/addInterfaceGrouping
     * API add Interface Grouping
     *
     * @param serialNumber Mã Serial number của thiết bị (required)
     * @param body Object InterfaceGrouping (required)
     * @return OK (status code 200)
     * @see DeviceApi#deviceAddInterfaceGrouping
     */
    default ResponseEntity<BaseResponse> deviceAddInterfaceGrouping(String serialNumber,
        AddInterfaceGroupingRequest body) {
        getRequest().ifPresent(request -> {
            for (MediaType mediaType: MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"result\" : \"result\", \"error_code\" : \"0\", \"message\" : \"success\" }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
            }
        });
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }

    /**
     * POST /device/addWan
     * API add Wan
     *
     * @param optical Mã Optical Serial number của thiết bị (required)
     * @param body Object WAN (required)
     * @return OK (status code 200)
     * @see DeviceApi#deviceAddWan
     */
    default ResponseEntity<BaseResponse> deviceAddWan(String optical,
        DeviceWan body) {
        getRequest().ifPresent(request -> {
            for (MediaType mediaType: MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"result\" : \"result\", \"error_code\" : \"0\", \"message\" : \"success\" }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
            }
        });
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }

    /**
     * GET /device/configWebGuiPass
     * Lấy cấu hình account đăng nhập thiết bị
     *
     * @param optical Mã Optical Serial number của thiết bị (required)
     * @param username Username login (required)
     * @param password Password mới (required)
     * @return Kết quả trả về là object Json chứa message thông báo kết quả (status code 200)
     * @see DeviceApi#deviceConfigWebGuiPass
     */
    default ResponseEntity<BaseResponse> deviceConfigWebGuiPass(String optical,
        String username,
        String password) {
        getRequest().ifPresent(request -> {
            for (MediaType mediaType: MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"result\" : \"result\", \"error_code\" : \"0\", \"message\" : \"success\" }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
            }
        });
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }

    /**
     * POST /device/delWan
     * API xoá wan trên ONT
     *
     * @param optical Mã OpticalNo của thiết bị (required)
     * @param deviceWan  (optional)
     * @return Kết quả trả về là object Json chứa kết quả (status code 200)
     * @see DeviceApi#deviceDelWan
     */
    default ResponseEntity<BaseResponse> deviceDelWan(String optical,
        DeviceWan deviceWan) {
        getRequest().ifPresent(request -> {
            for (MediaType mediaType: MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"result\" : \"result\", \"error_code\" : \"0\", \"message\" : \"success\" }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
            }
        });
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }

    /**
     * GET /device/deleteDevice
     * API xoá ONT
     *
     * @param optical Danh sách thiết bị xoá (required)
     * @return Kết quả trả về là object Json chứa kết quả (status code 200)
     * @see DeviceApi#deviceDeleteDevice
     */
    default ResponseEntity<BaseResponse> deviceDeleteDevice(String optical) {
        getRequest().ifPresent(request -> {
            for (MediaType mediaType: MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"result\" : \"result\", \"error_code\" : \"0\", \"message\" : \"success\" }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
            }
        });
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }

    /**
     * POST /device/deleteInterfaceGrouping
     * API delete Interface Grouping
     *
     * @param serialNumber Mã Serial number của thiết bị (required)
     * @param body Object InterfaceGrouping (required)
     * @return OK (status code 200)
     * @see DeviceApi#deviceDeleteInterfaceGrouping
     */
    default ResponseEntity<BaseResponse> deviceDeleteInterfaceGrouping(String serialNumber,
        DeleteInterfaceGroupingRequest body) {
        getRequest().ifPresent(request -> {
            for (MediaType mediaType: MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"result\" : \"result\", \"error_code\" : \"0\", \"message\" : \"success\" }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
            }
        });
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }

    /**
     * GET /device/diagnostic
     * Đo kiểm kết nối của thiết bị
     *
     * @param optical Mã Optical Serial number của thiết bị (required)
     * @param pingto url hoặc ip của host cần kiểm tra kết nối (required)
     * @param downloadurl url test download file (required)
     * @param uploadurl url upload file test (kích thước 10Mb) (required)
     * @return Kết quả trả về là object Json chứa kết quả ping (status code 200)
     * @see DeviceApi#deviceDiagnostic
     */
    default ResponseEntity<DiagnosticResponse> deviceDiagnostic(String optical,
        String pingto,
        String downloadurl,
        String uploadurl) {
        getRequest().ifPresent(request -> {
            for (MediaType mediaType: MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"downloadResult\" : { \"tcpopenResInMiliseconds\" : 2.027123023002322, \"totalBytesReceived\" : 5.962133916683182, \"romTime\" : \"romTime\", \"bomTimeInMiliseconds\" : 7.061401241503109, \"tcpOpenRequestTime\" : \"tcpOpenRequestTime\", \"eomTimeInMiliseconds\" : 9.301444243932576, \"testInterface\" : \"testInterface\", \"downloadUrl\" : \"downloadUrl\", \"testBytesReceived\" : 1.4658129805029452, \"tcpOpenResponseTime\" : \"tcpOpenResponseTime\", \"romTimeInMiliseconds\" : 2.3021358869347655, \"deviceTime\" : \"deviceTime\", \"speed\" : 5.637376656633329, \"dscp\" : 0, \"eomTime\" : \"eomTime\", \"ethernetPriority\" : 6, \"bomTime\" : \"bomTime\", \"tcpopenReqInMiliseconds\" : 3.616076749251911, \"diagnosticState\" : \"diagnosticState\" }, \"uploadResult\" : { \"tcpopenResInMiliseconds\" : 5.025004791520295, \"totalBytesReceived\" : 1.0246457001441578, \"romTime\" : \"romTime\", \"bomTimeInMiliseconds\" : 7.457744773683766, \"tcpOpenRequestTime\" : \"tcpOpenRequestTime\", \"eomTimeInMiliseconds\" : 1.1730742509559433, \"testInterface\" : \"testInterface\", \"testBytesReceived\" : 1.2315135367772556, \"tcpOpenResponseTime\" : \"tcpOpenResponseTime\", \"romTimeInMiliseconds\" : 6.84685269835264, \"deviceTime\" : \"deviceTime\", \"speed\" : 1.4894159098541704, \"dscp\" : 4, \"uploadUrl\" : \"uploadUrl\", \"eomTime\" : \"eomTime\", \"ethernetPriority\" : 7, \"bomTime\" : \"bomTime\", \"tcpopenReqInMiliseconds\" : 4.965218492984954, \"diagnosticState\" : \"diagnosticState\" }, \"pingResult\" : { \"averageResponseTime\" : 7, \"dataBlockSize\" : 5, \"maximumResponseTime\" : 3, \"minimumResponseTime\" : 9, \"diagnosticsState\" : \"diagnosticsState\", \"numberOfRepetitions\" : 6, \"deviceTime\" : \"deviceTime\", \"timeout\" : 1, \"dscp\" : 0, \"jitter\" : 2, \"host\" : \"host\", \"successCount\" : 5, \"failureCount\" : 2 } }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
            }
        });
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }

    /**
     * GET /device/fetchConnectedUserInfor
     * API lấy số lượng người dùng trong mạng LAN
     *
     * @param serialNumber Mã OpticalNo của thiết bị(phân biệt hoa thường) (required)
     * @return Kết quả trả về là object Json chứa kết quả (status code 200)
     * @see DeviceApi#deviceFetchConnectedUserInfor
     */
    default ResponseEntity<ConnectedUserListResponse> deviceFetchConnectedUserInfor(String serialNumber) {
        getRequest().ifPresent(request -> {
            for (MediaType mediaType: MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"data\" : [ { \"hostName\" : \"hostName\", \"interfaceType\" : \"interfaceType\", \"macAddress\" : \"macAddress\", \"addressSource\" : \"addressSource\", \"ifName\" : \"ifName\", \"ipAddress\" : \"ipAddress\", \"leaseTimeRemaining\" : 0.8008281904610115, \"isActive\" : true }, { \"hostName\" : \"hostName\", \"interfaceType\" : \"interfaceType\", \"macAddress\" : \"macAddress\", \"addressSource\" : \"addressSource\", \"ifName\" : \"ifName\", \"ipAddress\" : \"ipAddress\", \"leaseTimeRemaining\" : 0.8008281904610115, \"isActive\" : true } ] }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
            }
        });
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }

    /**
     * GET /device/fetchOpticalInfo
     * API lấy thông tin nhiệt độ, công suất thu phát của module quang trên ONT
     *
     * @param serialNumber Mã OpticalNo của thiết bị(phân biệt hoa thường) (required)
     * @return Kết quả trả về là object Json chứa kết quả (status code 200)
     * @see DeviceApi#deviceFetchOpticalInfo
     */
    default ResponseEntity<OpticalInfoListResponse> deviceFetchOpticalInfo(String serialNumber) {
        getRequest().ifPresent(request -> {
            for (MediaType mediaType: MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"data\" : [ { \"txBias\" : 0, \"txPower\" : 5, \"rxPower\" : 5, \"bOSAtemperature\" : 1, \"rxVcc\" : 6 }, { \"txBias\" : 0, \"txPower\" : 5, \"rxPower\" : 5, \"bOSAtemperature\" : 1, \"rxVcc\" : 6 } ] }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
            }
        });
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }

    /**
     * GET /device/getAccWebGui
     * Lấy thông tin account đăng nhập WebGui của thiết bị
     *
     * @param optical Mã Optical Serial number của thiết bị (required)
     * @return Kết quả trả về là object Json chứa thông tin account đăng nhập thiết bị (status code 200)
     * @see DeviceApi#deviceGetAccWebGui
     */
    default ResponseEntity<AccWebGuiListResponse> deviceGetAccWebGui(String optical) {
        getRequest().ifPresent(request -> {
            for (MediaType mediaType: MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"data\" : [ { \"password\" : \"password\", \"username\" : \"username\" }, { \"password\" : \"password\", \"username\" : \"username\" } ] }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
            }
        });
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }

    /**
     * GET /device/getBoundInterfacesList
     * API get bound interfaces list
     *
     * @param optical Mã Optical Serial number của thiết bị (required)
     * @return OK (status code 200)
     * @see DeviceApi#deviceGetBoundInterfacesList
     */
    default ResponseEntity<BoundInterfacesListResponse> deviceGetBoundInterfacesList(String optical) {
        getRequest().ifPresent(request -> {
            for (MediaType mediaType: MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"boundInterfacesList\" : [ \"boundInterfacesList\", \"boundInterfacesList\" ] }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
            }
        });
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }

    /**
     * GET /device/getInfoDeviceConnect
     * Lấy danh sách thông tin thiết bị kết nối wifi
     *
     * @param optical Mã Optical Serial number của thiết bị (required)
     * @return Kết quả trả về là object Json chứa kết quả (status code 200)
     * @see DeviceApi#deviceGetInfoDeviceConnect
     */
    default ResponseEntity<BaseResponse> deviceGetInfoDeviceConnect(String optical) {
        getRequest().ifPresent(request -> {
            for (MediaType mediaType: MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"result\" : \"result\", \"error_code\" : \"0\", \"message\" : \"success\" }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
            }
        });
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }

    /**
     * GET /device/getInterfaceGroupingList
     * API get Interface Grouping list
     *
     * @param serialNumber Mã Serial number của thiết bị (required)
     * @return OK (status code 200)
     * @see DeviceApi#deviceGetInterfaceGroupingList
     */
    default ResponseEntity<InterfaceGroupingListResponse> deviceGetInterfaceGroupingList(String serialNumber) {
        getRequest().ifPresent(request -> {
            for (MediaType mediaType: MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"data\" : [ { \"groupName\" : \"groupName\", \"wanInterface\" : \"wanInterface\", \"index\" : \"index\", \"wanInterfaceName\" : \"wanInterfaceName\", \"lanInterfaceName\" : \"lanInterfaceName\", \"lanInterface\" : \"lanInterface\" }, { \"groupName\" : \"groupName\", \"wanInterface\" : \"wanInterface\", \"index\" : \"index\", \"wanInterfaceName\" : \"wanInterfaceName\", \"lanInterfaceName\" : \"lanInterfaceName\", \"lanInterface\" : \"lanInterface\" } ] }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
            }
        });
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }

    /**
     * GET /device/getLanInterfacesList
     * API get LAN Interfaces list
     *
     * @param serialNumber Mã Serial number của thiết bị (required)
     * @return OK (status code 200)
     * @see DeviceApi#deviceGetLanInterfacesList
     */
    default ResponseEntity<WanLanInterfacesListResponse> deviceGetLanInterfacesList(String serialNumber) {
        getRequest().ifPresent(request -> {
            for (MediaType mediaType: MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"data\" : [ { \"name\" : \"name\", \"id\" : 0, \"type\" : \"type\" }, { \"name\" : \"name\", \"id\" : 0, \"type\" : \"type\" } ] }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
            }
        });
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }

    /**
     * GET /device/getListWan
     * Lấy thông tin các WAN của thiết bị
     *
     * @param optical Mã Optical Serial number của thiết bị (required)
     * @return Kết quả trả về là object Json chứa danh sách các WAN của thiết bị (status code 200)
     * @see DeviceApi#deviceGetListWan
     */
    default ResponseEntity<DeviceWanListResponse> deviceGetListWan(String optical) {
        getRequest().ifPresent(request -> {
            for (MediaType mediaType: MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"data\" : [ { \"addressingType\" : \"addressingType\", \"natEnable\" : true, \"ipv6DnsServer\" : \"ipv6DnsServer\", \"igmpEnabled\" : true, \"connectionType\" : \"connectionType\", \"dhcp6cPrefixDelegationEnable\" : \"dhcp6cPrefixDelegationEnable\", \"ipv4Enable\" : true, \"fullConeNatEnable\" : true, \"igmpSourceEnable\" : true, \"password\" : \"password\", \"enable\" : true, \"unnumberedModel\" : true, \"mldSourceEnable\" : true, \"connectionID\" : 5, \"vpiValue\" : 2, \"vlanPriority\" : 0, \"wanIndex\" : 5, \"dnsServers\" : \"dnsServers\", \"externalIPv6Address\" : \"externalIPv6Address\", \"wanIfname\" : \"wanIfname\", \"vlanTPID\" : 1, \"vciValue\" : 7, \"dhcp6cForAddress\" : \"dhcp6cForAddress\", \"ipv6Status\" : \"ipv6Status\", \"vlanMuxID\" : 6, \"userName\" : \"userName\", \"subnetMask\" : \"subnetMask\", \"dnsIfName\" : \"dnsIfName\", \"defaultGateway\" : \"defaultGateway\", \"ipv6Enable\" : true, \"wanName\" : \"wanName\", \"defaultIPv6Gateway\" : \"defaultIPv6Gateway\", \"ipv6AddressingType\" : \"ipv6AddressingType\", \"wanInstancePath\" : \"wanInstancePath\", \"connectionStatus\" : \"connectionStatus\", \"dhcp6cForPrefixDelegation\" : \"dhcp6cForPrefixDelegation\", \"externalIPAdress\" : \"externalIPAdress\", \"mldEnabled\" : true, \"firewallEnable\" : true, \"wanType\" : \"wanType\" }, { \"addressingType\" : \"addressingType\", \"natEnable\" : true, \"ipv6DnsServer\" : \"ipv6DnsServer\", \"igmpEnabled\" : true, \"connectionType\" : \"connectionType\", \"dhcp6cPrefixDelegationEnable\" : \"dhcp6cPrefixDelegationEnable\", \"ipv4Enable\" : true, \"fullConeNatEnable\" : true, \"igmpSourceEnable\" : true, \"password\" : \"password\", \"enable\" : true, \"unnumberedModel\" : true, \"mldSourceEnable\" : true, \"connectionID\" : 5, \"vpiValue\" : 2, \"vlanPriority\" : 0, \"wanIndex\" : 5, \"dnsServers\" : \"dnsServers\", \"externalIPv6Address\" : \"externalIPv6Address\", \"wanIfname\" : \"wanIfname\", \"vlanTPID\" : 1, \"vciValue\" : 7, \"dhcp6cForAddress\" : \"dhcp6cForAddress\", \"ipv6Status\" : \"ipv6Status\", \"vlanMuxID\" : 6, \"userName\" : \"userName\", \"subnetMask\" : \"subnetMask\", \"dnsIfName\" : \"dnsIfName\", \"defaultGateway\" : \"defaultGateway\", \"ipv6Enable\" : true, \"wanName\" : \"wanName\", \"defaultIPv6Gateway\" : \"defaultIPv6Gateway\", \"ipv6AddressingType\" : \"ipv6AddressingType\", \"wanInstancePath\" : \"wanInstancePath\", \"connectionStatus\" : \"connectionStatus\", \"dhcp6cForPrefixDelegation\" : \"dhcp6cForPrefixDelegation\", \"externalIPAdress\" : \"externalIPAdress\", \"mldEnabled\" : true, \"firewallEnable\" : true, \"wanType\" : \"wanType\" } ] }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
            }
        });
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }

    /**
     * GET /device/getListWlan
     * Lấy thông tin wifi
     *
     * @param optical Mã Optical Serial number của thiết bị (required)
     * @return Kết quả trả về là một Json List object các Wlan của thiết bị (status code 200)
     * @see DeviceApi#deviceGetListWlan
     */
    default ResponseEntity<DeviceWlanListResponse> deviceGetListWlan(String optical) {
        getRequest().ifPresent(request -> {
            for (MediaType mediaType: MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"data\" : [ { \"authMode\" : \"authMode\", \"password\" : \"password\", \"enable\" : true, \"encrypt\" : \"encrypt\", \"index\" : 0, \"type\" : \"type\", \"ssid\" : \"ssid\" }, { \"authMode\" : \"authMode\", \"password\" : \"password\", \"enable\" : true, \"encrypt\" : \"encrypt\", \"index\" : 0, \"type\" : \"type\", \"ssid\" : \"ssid\" } ] }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
            }
        });
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }

    /**
     * GET /device/getResultFactoryReset
     * API xoá ONT
     *
     * @param serialNumber Mã OpticalNo của thiết bị (required)
     * @return Kết quả trả về là object Json chứa kết quả (status code 200)
     * @see DeviceApi#deviceGetResultFactoryReset
     */
    default ResponseEntity<BaseResponse> deviceGetResultFactoryReset(String serialNumber) {
        getRequest().ifPresent(request -> {
            for (MediaType mediaType: MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"result\" : \"result\", \"error_code\" : \"0\", \"message\" : \"success\" }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
            }
        });
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }

    /**
     * GET /device/getVoice
     * API get voice configuration
     *
     * @param optical Mã Optical Serial number của thiết bị (required)
     * @return OK (status code 200)
     * @see DeviceApi#deviceGetVoice
     */
    default ResponseEntity<GetVoiceListResponse> deviceGetVoice(String optical) {
        getRequest().ifPresent(request -> {
            for (MediaType mediaType: MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"SIPObject\" : \"{}\", \"LineObject\" : [ { \"callIDNamer\" : \"callIDNamer\", \"callWaitingEnable\" : \"callWaitingEnable\", \"enable\" : \"enable\", \"callStatus\" : \"callStatus\", \"sipAuthUserPass\" : \"sipAuthUserPass\", \"id\" : 0, \"sipUri\" : \"sipUri\", \"sipAuthUserName\" : \"sipAuthUserName\", \"callState\" : \"callState\" }, { \"callIDNamer\" : \"callIDNamer\", \"callWaitingEnable\" : \"callWaitingEnable\", \"enable\" : \"enable\", \"callStatus\" : \"callStatus\", \"sipAuthUserPass\" : \"sipAuthUserPass\", \"id\" : 0, \"sipUri\" : \"sipUri\", \"sipAuthUserName\" : \"sipAuthUserName\", \"callState\" : \"callState\" } ] }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
            }
        });
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }

    /**
     * GET /device/getWanInterfacesList
     * API get WAN Interfaces list
     *
     * @param serialNumber Mã Serial number của thiết bị (required)
     * @return OK (status code 200)
     * @see DeviceApi#deviceGetWanInterfacesList
     */
    default ResponseEntity<WanLanInterfacesListResponse> deviceGetWanInterfacesList(String serialNumber) {
        getRequest().ifPresent(request -> {
            for (MediaType mediaType: MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"data\" : [ { \"name\" : \"name\", \"id\" : 0, \"type\" : \"type\" }, { \"name\" : \"name\", \"id\" : 0, \"type\" : \"type\" } ] }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
            }
        });
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }

    /**
     * GET /device/getWirelessAdvance
     * Lấy thông tin cấu hình advance của wifi (channel, công suất phát, …)
     *
     * @param optical Mã Optical Serial number của thiết bị (required)
     * @return Kết quả trả về là object Json chứa thông tin cấu hình wif (status code 200)
     * @see DeviceApi#deviceGetWirelessAdvance
     */
    default ResponseEntity<DeviceWirelessAdvResponse> deviceGetWirelessAdvance(String optical) {
        getRequest().ifPresent(request -> {
            for (MediaType mediaType: MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"data\" : { \"autoChannelTimer\" : 1.4658129805029452, \"country\" : \"country\", \"bandwidth\" : 6.027456183070403, \"channel\" : 0.8008281904610115, \"transmitPower\" : 5.962133916683182 } }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
            }
        });
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }

    /**
     * POST /device/onOffWifi
     * Cấu hình on/off wifi
     *
     * @param optical Mã Optical Serial number của thiết bị (required)
     * @param wifis List các object wifi cần bật/tắt (required)
     * @return OK (status code 200)
     * @see DeviceApi#deviceOnOffWifi
     */
    default ResponseEntity<BaseResponse> deviceOnOffWifi(String optical,
        List<DeviceWlan> wifis) {
        getRequest().ifPresent(request -> {
            for (MediaType mediaType: MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"result\" : \"result\", \"error_code\" : \"0\", \"message\" : \"success\" }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
            }
        });
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }

    /**
     * GET /device/ping
     * Kiểm tra kết nối của thiết bị
     *
     * @param optical Mã Optical Serial number của thiết bị (required)
     * @param host url hoặc ip của host cần kiểm tra kết nối (required)
     * @return Kết quả trả về là object Json chứa kết quả ping (status code 200)
     * @see DeviceApi#devicePing
     */
    default ResponseEntity<PingResultResponse> devicePing(String optical,
        String host) {
        getRequest().ifPresent(request -> {
            for (MediaType mediaType: MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"data\" : { \"averageResponseTime\" : 7, \"dataBlockSize\" : 5, \"maximumResponseTime\" : 3, \"minimumResponseTime\" : 9, \"diagnosticsState\" : \"diagnosticsState\", \"numberOfRepetitions\" : 6, \"deviceTime\" : \"deviceTime\", \"timeout\" : 1, \"dscp\" : 0, \"jitter\" : 2, \"host\" : \"host\", \"successCount\" : 5, \"failureCount\" : 2 } }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
            }
        });
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }

    /**
     * GET /device/rebootDevice
     * API reboot ONT
     *
     * @param serialNumber Mã OpticalNo của thiết bị (required)
     * @return Kết quả trả về là object Json chứa kết quả (status code 200)
     * @see DeviceApi#deviceRebootDevice
     */
    default ResponseEntity<BaseResponse> deviceRebootDevice(String serialNumber) {
        getRequest().ifPresent(request -> {
            for (MediaType mediaType: MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"result\" : \"result\", \"error_code\" : \"0\", \"message\" : \"success\" }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
            }
        });
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }

    /**
     * GET /device/reset
     * Reset lại thiết bị
     *
     * @param optical Mã Optical Serial number của thiết bị (required)
     * @return OK (status code 200)
     * @see DeviceApi#deviceReset
     */
    default ResponseEntity<BaseResponse> deviceReset(String optical) {
        getRequest().ifPresent(request -> {
            for (MediaType mediaType: MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"result\" : \"result\", \"error_code\" : \"0\", \"message\" : \"success\" }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
            }
        });
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }

    /**
     * GET /device/searchAdslDeviceAdslFirmware
     * API thống kê ONT
     *
     * @param start  (required)
     * @param limit  (required)
     * @param areaName  (required)
     * @param areaId  (optional)
     * @return Kết quả trả về là object Json chứa kết quả (status code 200)
     * @see DeviceApi#deviceSearchAdslDeviceAdslFirmware
     */
    default ResponseEntity<ListAdslDeviceResponse> deviceSearchAdslDeviceAdslFirmware(String start,
        String limit,
        String areaName,
        String areaId) {
        getRequest().ifPresent(request -> {
            for (MediaType mediaType: MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"data\" : { \"totalCount\" : 0, \"adslDeviceList\" : [ { \"oui\" : \"oui\", \"serialNumber\" : \"serialNumber\", \"productClass\" : \"productClass\", \"ipAddress\" : \"ipAddress\", \"provinceId\" : \"provinceId\", \"productTypeID\" : \"productTypeID\", \"manufacturer\" : \"manufacturer\", \"connectionRequest\" : \"connectionRequest\", \"adslName\" : \"adslName\", \"areaID\" : \"areaID\", \"opticalSerial\" : \"opticalSerial\", \"areaName\" : \"areaName\", \"lastInformTime\" : 5.637376656633329, \"hardwareVersion\" : \"hardwareVersion\", \"id\" : 6, \"provinceName\" : \"provinceName\", \"softwareVersion\" : \"softwareVersion\", \"productType\" : \"productType\", \"menID\" : \"menID\", \"validate\" : 1, \"status\" : 5 }, { \"oui\" : \"oui\", \"serialNumber\" : \"serialNumber\", \"productClass\" : \"productClass\", \"ipAddress\" : \"ipAddress\", \"provinceId\" : \"provinceId\", \"productTypeID\" : \"productTypeID\", \"manufacturer\" : \"manufacturer\", \"connectionRequest\" : \"connectionRequest\", \"adslName\" : \"adslName\", \"areaID\" : \"areaID\", \"opticalSerial\" : \"opticalSerial\", \"areaName\" : \"areaName\", \"lastInformTime\" : 5.637376656633329, \"hardwareVersion\" : \"hardwareVersion\", \"id\" : 6, \"provinceName\" : \"provinceName\", \"softwareVersion\" : \"softwareVersion\", \"productType\" : \"productType\", \"menID\" : \"menID\", \"validate\" : 1, \"status\" : 5 } ] } }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
            }
        });
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }

    /**
     * POST /device/setVoice
     * API set voice configuration
     *
     * @param optical Mã Optical Serial number của thiết bị (required)
     * @param body  (required)
     * @return OK (status code 200)
     * @see DeviceApi#deviceSetVoice
     */
    default ResponseEntity<BaseResponse> deviceSetVoice(String optical,
        InlineObject body) {
        getRequest().ifPresent(request -> {
            for (MediaType mediaType: MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"result\" : \"result\", \"error_code\" : \"0\", \"message\" : \"success\" }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
            }
        });
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }

    /**
     * POST /device/setWirelessAdvance
     * Cấu hình thông tin wifi advance
     *
     * @param optical Mã Optical Serial number của thiết bị (required)
     * @param newConfig Object cấu hình wifi advance (required)
     * @return Kết quả trả về thành công là chuỗi json (status code 200)
     * @see DeviceApi#deviceSetWirelessAdvance
     */
    default ResponseEntity<BaseResponse> deviceSetWirelessAdvance(String optical,
        DeviceWirelessAdv newConfig) {
        getRequest().ifPresent(request -> {
            for (MediaType mediaType: MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"result\" : \"result\", \"error_code\" : \"0\", \"message\" : \"success\" }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
            }
        });
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }

    /**
     * POST /device/updateWifi
     * Cấu hình thông tin wifi (wlan)
     *
     * @param optical Mã Optical Serial number của thiết bị (required)
     * @param wifis List các object wifi cần bật/tắt (required)
     * @return OK (status code 200)
     * @see DeviceApi#deviceUpdateWifi
     */
    default ResponseEntity<BaseResponse> deviceUpdateWifi(String optical,
        List<DeviceWlan> wifis) {
        getRequest().ifPresent(request -> {
            for (MediaType mediaType: MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"result\" : \"result\", \"error_code\" : \"0\", \"message\" : \"success\" }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
            }
        });
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }

    /**
     * POST /device/updateWifiType
     * API cấu hình Wifi
     *
     * @param optical Mã Optical Serial number của thiết bị (required)
     * @param wifis List các object wifi cần bật/tắt (required)
     * @return OK (status code 200)
     * @see DeviceApi#deviceUpdateWifiType
     */
    default ResponseEntity<BaseResponse> deviceUpdateWifiType(String optical,
        List<DeviceWlan> wifis) {
        getRequest().ifPresent(request -> {
            for (MediaType mediaType: MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"result\" : \"result\", \"error_code\" : \"0\", \"message\" : \"success\" }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
            }
        });
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }

    /**
     * GET /device/getUptime
     * Lấy thông tin uptime thiết bị
     *
     * @param optical Mã Optical Serial number của thiết bị (required)
     * @return Thời gian uptime tính theo giây (status code 200)
     * @see DeviceApi#getUptime
     */
    default ResponseEntity<BaseResponse> getUptime(String optical) {
        getRequest().ifPresent(request -> {
            for (MediaType mediaType: MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"result\" : \"result\", \"error_code\" : \"0\", \"message\" : \"success\" }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
            }
        });
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }

    /**
     * GET /device/reboot
     * Khởi động lại thiết bị
     *
     * @param optical Mã Optical Serial number của thiết bị (required)
     * @return OK (status code 200)
     * @see DeviceApi#reboot
     */
    default ResponseEntity<BaseResponse> reboot(String optical) {
        getRequest().ifPresent(request -> {
            for (MediaType mediaType: MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"result\" : \"result\", \"error_code\" : \"0\", \"message\" : \"success\" }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
            }
        });
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }

    /**
     * GET /device/getByOptical
     * Lấy thông tin chi tiết thiết bị
     *
     * @param optical Mã Optical Serial number của thiết bị (required)
     * @return OK (status code 200)
     * @see DeviceApi#searchDevice
     */
    default ResponseEntity<DeviceParamResponse> searchDevice(String optical) {
        getRequest().ifPresent(request -> {
            for (MediaType mediaType: MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"data\" : { \"oui\" : \"oui\", \"serialNumber\" : \"serialNumber\", \"productClass\" : \"productClass\", \"productID\" : 0, \"joinTime\" : 1.4658129805029452, \"ipAddress\" : \"ipAddress\", \"manufacturer\" : \"manufacturer\", \"connectionRequest\" : \"connectionRequest\", \"provisioningCode\" : \"provisioningCode\", \"modelName\" : \"modelName\", \"adslName\" : \"adslName\", \"areaID\" : \"areaID\", \"areaName\" : \"areaName\", \"opticalSerial\" : \"opticalSerial\", \"lastInformTime\" : 6.027456183070403, \"hardwareVersion\" : \"hardwareVersion\", \"softwareVersion\" : \"softwareVersion\", \"menID\" : \"menID\" } }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
            }
        });
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }

}
