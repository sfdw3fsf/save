package vn.vnptit.sapi.oneapp.resources.gnms.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.NativeWebRequest;
import reactor.core.publisher.Flux;
import vn.vnptit.sapi.oneapp.models.GnmsException;
import vn.vnptit.sapi.oneapp.models.gnms.*;
import vn.vnptit.sapi.oneapp.resources.gnms.Gw040HApiDelegate;

import vn.vnptit.sapi.oneapp.resources.gnms.client.Gw040HApi;

import java.util.List;
import java.util.Optional;
@Service
public class Gw040HApiDelegateImpl implements Gw040HApiDelegate {

    @Autowired
    private NativeWebRequest nRequest;


    private final Gw040HApi client = new Gw040HApi();

    @Override
    public Optional<NativeWebRequest> getRequest() {
        return Optional.of(nRequest);
    }

    @Override
    public ResponseEntity<DeviceWlanListResponse> deviceGetListWlanV2(String optical) {
        DeviceWlanListResponse ret =  new DeviceWlanListResponse();

        try {
            Flux<DeviceWlan> flux = client.deviceGetListWlanV2(optical);
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
    public ResponseEntity<DeviceWirelessAdvV2ListResponse> deviceGetWirelessAdvanceV2(String optical) {
        DeviceWirelessAdvV2ListResponse ret =  new DeviceWirelessAdvV2ListResponse();

        try {
            Flux<DeviceWirelessAdvV2> flux = client.deviceGetWirelessAdvanceV2(optical);
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
    public ResponseEntity<BaseResponse> deviceSetWirelessAdvanceV2(String optical, DeviceWirelessAdvV2 newConfig) {
        return ResponseEntity.ok(client.deviceSetWirelessAdvanceV2(optical, newConfig).block());
    }

    @Override
    public ResponseEntity<BaseResponse> deviceUpdateWifiV2(String optical, List<DeviceWlan> wifis) {
        return ResponseEntity.ok(client.deviceUpdateWifiV2(optical, wifis).block());
    }
}
