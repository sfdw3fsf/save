package vn.vnptit.sapi.oneapp.resources.gnms.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.NativeWebRequest;
import reactor.core.publisher.Flux;
import vn.vnptit.sapi.oneapp.models.GnmsException;
import vn.vnptit.sapi.oneapp.models.gnms.*;

import vn.vnptit.sapi.oneapp.resources.gnms.BatchApiDelegate;
import vn.vnptit.sapi.oneapp.resources.gnms.client.BatchApi;


import java.util.List;
import java.util.Optional;

@Service
public class BatchApiDelegateImpl implements BatchApiDelegate {
    @Autowired
    private NativeWebRequest nRequest;


    private final BatchApi client = new BatchApi();

    @Override
    public Optional<NativeWebRequest> getRequest() {
        return Optional.of(nRequest);
    }

    @Override
    public ResponseEntity<BaseResponse> batchAction(BatchInput body) {
        return ResponseEntity.ok(client.batchAction(body).block());
    }

    @Override
    public ResponseEntity<BatchLogListResponse> batchResult(Integer batchID) {

        BatchLogListResponse ret =  new BatchLogListResponse();

        try {
            Flux<BatchLog> flux = client.batchResult(batchID);
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
//        return ResponseEntity.ok(client.batchResult(batchID).collectList().block());
    }

}
