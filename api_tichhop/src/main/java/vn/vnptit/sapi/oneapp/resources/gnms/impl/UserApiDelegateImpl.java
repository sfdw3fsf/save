package vn.vnptit.sapi.oneapp.resources.gnms.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.NativeWebRequest;
import vn.vnptit.sapi.oneapp.models.gnms.AccConfig;
import vn.vnptit.sapi.oneapp.models.gnms.BaseResponse;
import vn.vnptit.sapi.oneapp.resources.gnms.UserApiDelegate;

import vn.vnptit.sapi.oneapp.resources.gnms.client.UserApi;

import java.util.Optional;
@Service
public class UserApiDelegateImpl implements UserApiDelegate {
    @Autowired
    private NativeWebRequest nRequest;


    private final UserApi client = new UserApi();

    @Override
    public Optional<NativeWebRequest> getRequest() {
        return Optional.of(nRequest);
    }

    @Override
    public ResponseEntity<BaseResponse> userConfigNewPassWord(AccConfig accConfig) {
        return ResponseEntity.ok(client.userConfigNewPassWord(accConfig).block());
    }
}
