package vn.vnptit.sapi.oneapp.resources.gnms.impl;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.NativeWebRequest;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import vn.vnptit.sapi.oneapp.models.GnmsException;
import vn.vnptit.sapi.oneapp.models.gnms.*;
import vn.vnptit.sapi.oneapp.resources.gnms.DeviceApiDelegate;
import vn.vnptit.sapi.oneapp.resources.gnms.client.DeviceApi;


import java.util.List;
import java.util.Optional;

@Service
public class DeviceApiDelegateImpl implements DeviceApiDelegate {
    @Autowired
    private NativeWebRequest nRequest;


    private final DeviceApi client = new DeviceApi();

    @Override
    public Optional<NativeWebRequest> getRequest() {
        return Optional.of(nRequest);
    }

    @Override
    public ResponseEntity<BaseResponse> delete(String optical) {
        return ResponseEntity.ok(client.delete(optical).block());
    }

    @Override
    public ResponseEntity<BaseResponse> deviceConfigWebGuiPass(String optical, String username, String password) {
        return ResponseEntity.ok(client.deviceConfigWebGuiPass(optical,username,password).block());
    }

    @Override
    public ResponseEntity<DiagnosticResponse> deviceDiagnostic(String optical, String pingto, String downloadurl, String uploadurl) {
        return ResponseEntity.ok(client.deviceDiagnostic(optical,pingto,downloadurl,uploadurl).block());
    }

    @Override
    public ResponseEntity<AccWebGuiListResponse> deviceGetAccWebGui(String optical) {

        AccWebGuiListResponse ret =  new AccWebGuiListResponse();

        try {
            Flux<AccWebGui> flux = client.deviceGetAccWebGui(optical);
            ret.setData(flux.collectList().block());
        }catch (Exception ex){
            if(ex.getCause() instanceof GnmsException){
                GnmsException gnmsEx = (GnmsException)ex.getCause();
                ret.setErrorCode(String.valueOf(gnmsEx.getCode()));
                ret.setErrorCode(String.valueOf(gnmsEx.getCode()));
                ret.setMessage(gnmsEx.getMessage());
            }
        }

        return ResponseEntity.ok(ret);
    }

    @Override
    public ResponseEntity<BaseResponse> deviceGetInfoDeviceConnect(String optical) {
        return ResponseEntity.ok(client.deviceGetInfoDeviceConnect(optical).block());
    }

    @Override
    public ResponseEntity<DeviceWanListResponse> deviceGetListWan(String optical) {
        DeviceWanListResponse ret =  new DeviceWanListResponse();

        try {
            Flux<DeviceWan> flux = client.deviceGetListWan(optical);
            ret.setData(flux.collectList().block());
        }catch (Exception ex){
            if(ex.getCause() instanceof GnmsException){
                GnmsException gnmsEx = (GnmsException)ex.getCause();
                ret.setErrorCode(String.valueOf(gnmsEx.getCode()));
                ret.setErrorCode(String.valueOf(gnmsEx.getCode()));
                ret.setMessage(gnmsEx.getMessage());
            }
        }

        return ResponseEntity.ok(ret);


    }

    @Override
    public ResponseEntity<DeviceWlanListResponse> deviceGetListWlan(String optical) {
        DeviceWlanListResponse ret =  new DeviceWlanListResponse();

        try {
            Flux<DeviceWlan> flux = client.deviceGetListWlan(optical);
            ret.setData(flux.collectList().block());
        }catch (Exception ex){
            if(ex.getCause() instanceof GnmsException){
                GnmsException gnmsEx = (GnmsException)ex.getCause();
                ret.setErrorCode(String.valueOf(gnmsEx.getCode()));
                ret.setErrorCode(String.valueOf(gnmsEx.getCode()));
                ret.setMessage(gnmsEx.getMessage());
            }
        }

        return ResponseEntity.ok(ret);


    }

    @Override
    public ResponseEntity<DeviceWirelessAdvResponse> deviceGetWirelessAdvance(String optical) {
        DeviceWirelessAdvResponse ret =  new DeviceWirelessAdvResponse();

        try {
            Mono<DeviceWirelessAdv> mono = client.deviceGetWirelessAdvance(optical);
            ret.setData(mono.block());
        }catch (Exception ex){
            if(ex.getCause() instanceof GnmsException){
                GnmsException gnmsEx = (GnmsException)ex.getCause();
                ret.setErrorCode(String.valueOf(gnmsEx.getCode()));
                ret.setErrorCode(String.valueOf(gnmsEx.getCode()));
                ret.setMessage(gnmsEx.getMessage());
            }
        }

        return ResponseEntity.ok(ret);


    }

    @Override
    public ResponseEntity<BaseResponse> deviceOnOffWifi(String optical, List<DeviceWlan> wifis) {
        return ResponseEntity.ok(client.deviceOnOffWifi(optical, wifis).block());
    }

    @Override
    public ResponseEntity<PingResultResponse> devicePing(String optical, String host) {
        PingResultResponse ret =  new PingResultResponse();

        try {
            Mono<PingResult> mono = client.devicePing(optical, host);
            ret.setData(mono.block());
        }catch (Exception ex){
            if(ex.getCause() instanceof GnmsException){
                GnmsException gnmsEx = (GnmsException)ex.getCause();
                ret.setErrorCode(String.valueOf(gnmsEx.getCode()));
                ret.setErrorCode(String.valueOf(gnmsEx.getCode()));
                ret.setMessage(gnmsEx.getMessage());
            }
        }

        return ResponseEntity.ok(ret);

    }


    @Override
    public ResponseEntity<BaseResponse> deviceUpdateWifi(String optical, List<DeviceWlan> wifis) {
        return ResponseEntity.ok(client.deviceUpdateWifi(optical, wifis).block());
    }

    @Override
    public ResponseEntity<BaseResponse> getUptime(String optical) {
        return ResponseEntity.ok(client.getUptime(optical).block());
    }

    @Override
    public ResponseEntity<BaseResponse> reboot(String optical) {
        return ResponseEntity.ok(client.reboot(optical).block());
    }

    @Override
    public ResponseEntity<DeviceParamResponse> searchDevice(String optical) {

        DeviceParamResponse ret =  new DeviceParamResponse();

        try {
            Mono<DeviceParam> mono = client.searchDevice(optical);
            ret.setData(mono.block());
        }catch (Exception ex){
            if(ex.getCause() instanceof GnmsException){
                GnmsException gnmsEx = (GnmsException)ex.getCause();
                ret.setErrorCode(String.valueOf(gnmsEx.getCode()));
                ret.setErrorCode(String.valueOf(gnmsEx.getCode()));
                ret.setMessage(gnmsEx.getMessage());
            }
        }

        return ResponseEntity.ok(ret);
    }

    @Override
    public ResponseEntity<BaseResponse> deviceAddBridgeWan(String serviceDescription, Integer vlanMux8021Priority, Integer vlanMuxID, Boolean enableWan, String optical) {
        try {
            Mono<BaseResponse> mono = client.deviceAddBridgeWan(serviceDescription, vlanMux8021Priority, vlanMuxID, enableWan, optical);
            return ResponseEntity.ok(mono.block());
        } catch (Exception ex){
            BaseResponse ret = new BaseResponse();
            if(ex.getCause() instanceof GnmsException){
                GnmsException gnmsEx = (GnmsException)ex.getCause();
                ret.setErrorCode(String.valueOf(gnmsEx.getCode()));
                ret.setMessage(gnmsEx.getMessage());

            } else {
                ret.setMessage(ex.getMessage());
            }
            return ResponseEntity.ok(ret);
        }
    }

    @Override
    public ResponseEntity<BaseResponse> deviceDelWan(String optical, DeviceWan deviceWan) {
        return ResponseEntity.ok(client.deviceDelWan(optical,deviceWan).block());
    }

    @Override
    public ResponseEntity<BaseResponse> deviceDeleteDevice(String optical) {
        return ResponseEntity.ok(client.deviceDeleteDevice(optical).block());
    }

    @Override
    public ResponseEntity<ConnectedUserListResponse> deviceFetchConnectedUserInfor(String serialNumber) {
        ConnectedUserListResponse ret =  new ConnectedUserListResponse();

        try {
            Flux<ConnectedUser> flux = client.deviceFetchConnectedUserInfor(serialNumber);
            ret.setData(flux.collectList().block());
        }catch (Exception ex){
            if(ex.getCause() instanceof GnmsException){
                GnmsException gnmsEx = (GnmsException)ex.getCause();
                ret.setErrorCode(String.valueOf(gnmsEx.getCode()));
                ret.setErrorCode(String.valueOf(gnmsEx.getCode()));
                ret.setMessage(gnmsEx.getMessage());
            }
        }

        return ResponseEntity.ok(ret);


    }

    @Override
    public ResponseEntity<OpticalInfoListResponse> deviceFetchOpticalInfo(String serialNumber) {
        OpticalInfoListResponse ret =  new OpticalInfoListResponse();

        try {
            Flux<OpticalInfo> flux = client.deviceFetchOpticalInfo(serialNumber);
            ret.setData(flux.collectList().block());
        }catch (Exception ex){
            if(ex.getCause() instanceof GnmsException){
                GnmsException gnmsEx = (GnmsException)ex.getCause();
                ret.setErrorCode(String.valueOf(gnmsEx.getCode()));
                ret.setErrorCode(String.valueOf(gnmsEx.getCode()));
                ret.setMessage(gnmsEx.getMessage());
            }
        }

        return ResponseEntity.ok(ret);
    }

    @Override
    public ResponseEntity<BaseResponse> deviceGetResultFactoryReset(String serialNumber) {
        return ResponseEntity.ok(client.deviceGetResultFactoryReset(serialNumber).block());
    }

    @Override
    public ResponseEntity<BaseResponse> deviceRebootDevice(String serialNumber) {
        return ResponseEntity.ok(client.deviceRebootDevice(serialNumber).block());
    }

    @Override
    public ResponseEntity<ListAdslDeviceResponse> deviceSearchAdslDeviceAdslFirmware(String start, String limit, String areaName, String areaId) {
        ListAdslDeviceResponse ret =  new ListAdslDeviceResponse();

        try {
            Mono<ListAdslDevice> mono = client.deviceSearchAdslDeviceAdslFirmware(start, limit, areaName, areaId);
            ret.setData(mono.block());
        }catch (Exception ex){
            if(ex.getCause() instanceof GnmsException){
                GnmsException gnmsEx = (GnmsException)ex.getCause();
                ret.setErrorCode(String.valueOf(gnmsEx.getCode()));
                ret.setErrorCode(String.valueOf(gnmsEx.getCode()));
                ret.setMessage(gnmsEx.getMessage());
            }
        }

        return ResponseEntity.ok(ret);
    }

    @Override
    public ResponseEntity<BaseResponse> deviceSetWirelessAdvance(String optical, DeviceWirelessAdv newConfig) {
        return ResponseEntity.ok(client.deviceSetWirelessAdvance(optical, newConfig).block());
    }

    @Override
    public ResponseEntity<BaseResponse> deviceUpdateWifiType(String optical, List<DeviceWlan> wifis) {
        return ResponseEntity.ok(client.deviceUpdateWifiType(optical, wifis).block());
    }

    @Override
    public ResponseEntity<BaseResponse> deviceReset(String optical) {
        try {
            Mono<BaseResponse> mono = client.deviceReset(optical);
            return ResponseEntity.ok(mono.block());
        } catch (Exception ex){
            BaseResponse ret = new BaseResponse();
            if(ex.getCause() instanceof GnmsException){
                GnmsException gnmsEx = (GnmsException)ex.getCause();
                ret.setErrorCode(String.valueOf(gnmsEx.getCode()));
                ret.setMessage(gnmsEx.getMessage());

            } else {
                ret.setMessage(ex.getMessage());
            }
            return ResponseEntity.ok(ret);
        }
    }

    @Override
    public ResponseEntity<BaseResponse> deviceAddInterfaceGrouping(String serialNumber, AddInterfaceGroupingRequest body) {
        BaseResponse response = new BaseResponse();
        try {
            Mono<GNMSResponse> mono = client.deviceAddInterfaceGrouping(serialNumber, body);
            String error_code = "0";
            String message = "";
            if (mono != null && mono.block() != null) {
                GNMSResponse data = mono.block();
                if (data != null) {
                    if (!data.getErrorCode().equals("200")) {
                        error_code = data.getErrorCode();
                    }
                    message = data.getErrorMessage();
                }
            }
            response.setErrorCode(error_code);
            response.setMessage(message);
            return ResponseEntity.ok(response);
        } catch (Exception ex){
            if(ex.getCause() instanceof GnmsException){
                GnmsException gnmsEx = (GnmsException)ex.getCause();
                response.setErrorCode(String.valueOf(gnmsEx.getCode()));
                response.setMessage(gnmsEx.getMessage());

            } else {
                response.setMessage(ex.getMessage());
            }
            return ResponseEntity.ok(response);
        }
    }

    @Override
    public ResponseEntity<BaseResponse> deviceAddWan(String optical, DeviceWan body) {
        BaseResponse response = new BaseResponse();
        try {
            Mono<GNMSResponse> mono = client.deviceAddWan(optical, body);
            String error_code = "0";
            String message = "";
            if (mono != null && mono.block() != null) {
                GNMSResponse data = mono.block();
                if (data != null) {
                    if (!data.getErrorCode().equals("200")) {
                        error_code = data.getErrorCode();
                    }
                    message = data.getErrorMessage();
                }
            }
            response.setErrorCode(error_code);
            response.setMessage(message);
            return ResponseEntity.ok(response);
        } catch (Exception ex){
            if(ex.getCause() instanceof GnmsException){
                GnmsException gnmsEx = (GnmsException)ex.getCause();
                response.setErrorCode(String.valueOf(gnmsEx.getCode()));
                response.setMessage(gnmsEx.getMessage());

            } else {
                response.setMessage(ex.getMessage());
            }
            return ResponseEntity.ok(response);
        }
    }

    @Override
    public ResponseEntity<BaseResponse> deviceDeleteInterfaceGrouping(String serialNumber, DeleteInterfaceGroupingRequest body) {
        BaseResponse response = new BaseResponse();
        try {
            Mono<GNMSResponse> mono = client.deviceDeleteInterfaceGrouping(serialNumber, body);
            String error_code = "0";
            String message = "";
            if (mono != null && mono.block() != null) {
                GNMSResponse data = mono.block();
                if (data != null) {
                    if (!data.getErrorCode().equals("200")) {
                        error_code = data.getErrorCode();
                    }
                    message = data.getErrorMessage();
                }
            }
            response.setErrorCode(error_code);
            response.setMessage(message);
            return ResponseEntity.ok(response);
        } catch (Exception ex){
            if(ex.getCause() instanceof GnmsException){
                GnmsException gnmsEx = (GnmsException)ex.getCause();
                response.setErrorCode(String.valueOf(gnmsEx.getCode()));
                response.setMessage(gnmsEx.getMessage());

            } else {
                response.setMessage(ex.getMessage());
            }
            return ResponseEntity.ok(response);
        }
    }

    @Override
    public ResponseEntity<BoundInterfacesListResponse> deviceGetBoundInterfacesList(String optical) {
        BoundInterfacesListResponse response = new BoundInterfacesListResponse();
        try {
            Mono<BoundInterfacesList> mono = client.deviceGetBoundInterfacesList(optical);
            String error_code = "0";
            String message = "";
            List<String> boundInterfacesList = null;
            if (mono != null && mono.block() != null) {
                BoundInterfacesList data = mono.block();
                if (data != null) {
                    if (!data.getErrorCode().equals("200")) {
                        error_code = data.getErrorCode();
                    }
                    message = data.getErrorMessage();
                    boundInterfacesList = data.getBoundInterfacesList();
                }
            }
            response.setErrorCode(error_code);
            response.setMessage(message);
            response.setBoundInterfacesList(boundInterfacesList);
            return ResponseEntity.ok(response);
        } catch (Exception ex){
            if(ex.getCause() instanceof GnmsException){
                GnmsException gnmsEx = (GnmsException)ex.getCause();
                response.setErrorCode(String.valueOf(gnmsEx.getCode()));
                response.setMessage(gnmsEx.getMessage());
            } else {
                response.setMessage(ex.getMessage());
            }
            return ResponseEntity.ok(response);
        }
    }

    @Override
    public ResponseEntity<InterfaceGroupingListResponse> deviceGetInterfaceGroupingList(String serialNumber) {
        InterfaceGroupingListResponse response = new InterfaceGroupingListResponse();
        try {
            Mono<InterfaceGroupingList> mono = client.deviceGetInterfaceGroupingList(serialNumber);
            String error_code = "0";
            String message = "";
            List<InterfaceGrouping> interfaceGroupingList = null;
            if (mono != null && mono.block() != null) {
                InterfaceGroupingList data = mono.block();
                if (data != null) {
                    if (!data.getErrorCode().equals("200")) {
                        error_code = data.getErrorCode();
                    }
                    message = data.getErrorMessage();
                    interfaceGroupingList = data.getInterfaceGroupingList();
                }
            }
            response.setErrorCode(error_code);
            response.setMessage(message);
            response.setData(interfaceGroupingList);
            return ResponseEntity.ok(response);
        } catch (Exception ex){
            if(ex.getCause() instanceof GnmsException){
                GnmsException gnmsEx = (GnmsException)ex.getCause();
                response.setErrorCode(String.valueOf(gnmsEx.getCode()));
                response.setMessage(gnmsEx.getMessage());
            } else {
                response.setMessage(ex.getMessage());
            }
            return ResponseEntity.ok(response);
        }
    }

    @Override
    public ResponseEntity<WanLanInterfacesListResponse> deviceGetLanInterfacesList(String serialNumber) {
        WanLanInterfacesListResponse response = new WanLanInterfacesListResponse();
        try {
            Mono<LanInterfacesResponse> mono = client.deviceGetLanInterfacesList(serialNumber);
            String error_code = "0";
            String message = "";
            List<WanLanInterfaces> wanLanInterfacesList = null;
            if (mono != null && mono.block() != null) {
                LanInterfacesResponse data = mono.block();
                if (data != null) {
                    if (!data.getErrorCode().equals("200")) {
                        error_code = data.getErrorCode();
                    }
                    message = data.getErrorMessage();
                    wanLanInterfacesList = data.getLanInterfacesList();
                }
            }
            response.setErrorCode(error_code);
            response.setMessage(message);
            response.setData(wanLanInterfacesList);
            return ResponseEntity.ok(response);
        } catch (Exception ex){
            if(ex.getCause() instanceof GnmsException){
                GnmsException gnmsEx = (GnmsException)ex.getCause();
                response.setErrorCode(String.valueOf(gnmsEx.getCode()));
                response.setMessage(gnmsEx.getMessage());
            } else {
                response.setMessage(ex.getMessage());
            }
            return ResponseEntity.ok(response);
        }
    }

    @Override
    public ResponseEntity<GetVoiceListResponse> deviceGetVoice(String optical) {
        GetVoiceListResponse response = new GetVoiceListResponse();
        try {
            Mono<GetVoiceList> mono = client.deviceGetVoice(optical);
            String error_code = "0";
            String message = "";
            Object sipObject = null;
            List<LineObject> lineObject = null;
            if (mono != null && mono.block() != null) {
                GetVoiceList data = mono.block();
                if (data != null) {
                    if (!data.getErrorCode().equals("200")) {
                        error_code = data.getErrorCode();
                    }
                    message = data.getErrorMessage();
                    sipObject = data.getSiPObject();
                    lineObject = data.getLineObject();
                }
            }
            response.setErrorCode(error_code);
            response.setMessage(message);
            response.setSiPObject(sipObject);
            response.setLineObject(lineObject);
            return ResponseEntity.ok(response);
        } catch (Exception ex){
            if(ex.getCause() instanceof GnmsException){
                GnmsException gnmsEx = (GnmsException)ex.getCause();
                response.setErrorCode(String.valueOf(gnmsEx.getCode()));
                response.setMessage(gnmsEx.getMessage());
            } else {
                response.setMessage(ex.getMessage());
            }
            return ResponseEntity.ok(response);
        }
    }

    @Override
    public ResponseEntity<WanLanInterfacesListResponse> deviceGetWanInterfacesList(String serialNumber) {
        WanLanInterfacesListResponse response = new WanLanInterfacesListResponse();
        try {
            Mono<WanInterfacesResponse> mono = client.deviceGetWanInterfacesList(serialNumber);
            String error_code = "0";
            String message = "";
            List<WanLanInterfaces> wanLanInterfacesList = null;
            if (mono != null && mono.block() != null) {
                WanInterfacesResponse data = mono.block();
                if (data != null) {
                    if (!data.getErrorCode().equals("200")) {
                        error_code = data.getErrorCode();
                    }
                    message = data.getErrorMessage();
                    wanLanInterfacesList = data.getWanInterfacesList();
                }
            }
            response.setErrorCode(error_code);
            response.setMessage(message);
            response.setData(wanLanInterfacesList);
            return ResponseEntity.ok(response);
        } catch (Exception ex){
            if(ex.getCause() instanceof GnmsException){
                GnmsException gnmsEx = (GnmsException)ex.getCause();
                response.setErrorCode(String.valueOf(gnmsEx.getCode()));
                response.setMessage(gnmsEx.getMessage());
            } else {
                response.setMessage(ex.getMessage());
            }
            return ResponseEntity.ok(response);
        }
    }

    @Override
    public ResponseEntity<BaseResponse> deviceSetVoice(String optical, InlineObject body) {
        BaseResponse response = new BaseResponse();
        try {
            Mono<GNMSResponse> mono = client.deviceSetVoice(optical, body);
            String error_code = "0";
            String message = "";
            if (mono != null && mono.block() != null) {
                GNMSResponse data = mono.block();
                if (data != null) {
                    if (!data.getErrorCode().equals("200")) {
                        error_code = data.getErrorCode();
                    }
                    message = data.getErrorMessage();
                }
            }
            response.setErrorCode(error_code);
            response.setMessage(message);
            return ResponseEntity.ok(response);
        } catch (Exception ex){
            if(ex.getCause() instanceof GnmsException){
                GnmsException gnmsEx = (GnmsException)ex.getCause();
                response.setErrorCode(String.valueOf(gnmsEx.getCode()));
                response.setMessage(gnmsEx.getMessage());

            } else {
                response.setMessage(ex.getMessage());
            }
            return ResponseEntity.ok(response);
        }
    }
}
