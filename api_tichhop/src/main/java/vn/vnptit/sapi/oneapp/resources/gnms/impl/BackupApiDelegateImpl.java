package vn.vnptit.sapi.oneapp.resources.gnms.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import vn.vnptit.sapi.oneapp.models.GnmsException;
import vn.vnptit.sapi.oneapp.models.gnms.*;
import vn.vnptit.sapi.oneapp.resources.gnms.BackupApiDelegate;
import vn.vnptit.sapi.oneapp.resources.gnms.client.BackupApi;

import java.io.*;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class BackupApiDelegateImpl implements BackupApiDelegate {
    @Autowired
    private NativeWebRequest nRequest;


    private final BackupApi client = new BackupApi();

    @Override
    public Optional<NativeWebRequest> getRequest() {
        return Optional.of(nRequest);
    }

    @Override
    public ResponseEntity<BaseResponse> backup(String optical, String backupName) {
        return ResponseEntity.ok(client.backup(optical, backupName).block());
    }

    @Override
    public ResponseEntity<BaseResponse> backupDelAll(String optical) {
        return ResponseEntity.ok(client.backupDelAll(optical).block());
    }

    @Override
    public ResponseEntity<ListReportWifiResponse> backupGetJsonReportWifi(BigDecimal areaId, String listSerialNumber, BigDecimal start, BigDecimal limit, String startDate, String endDate) {

        ListReportWifiResponse ret =  new ListReportWifiResponse();

        try {
            Mono<ListReportWifi> mono = client.backupGetJsonReportWifi(areaId, listSerialNumber, start, limit,startDate,endDate);
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
    public ResponseEntity<TemplateInfResponse> backupGetTemplate(String templateCode) {

        TemplateInfResponse ret =  new TemplateInfResponse();

        try {
            Mono<TemplateInf> mono = client.backupGetTemplate(templateCode);
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
    public ResponseEntity<Resource> backupGetTemplateImport(String templateCode) {

        InputStreamResource isr = null;
        try {
            isr = new InputStreamResource(new FileInputStream(client.backupGetTemplateImport(templateCode).block()));
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return ResponseEntity.ok(isr);
    }

    @Override
    public ResponseEntity<BaseResponse> backupImportZerotouch(String templateCode, TemplateInf objectConfig) {
        return ResponseEntity.ok(client.backupImportZerotouch(templateCode, objectConfig).block());
    }

    @Override
    public ResponseEntity<TemplateZerotouchListResponse> backupSearchTemplate(String templateName, String modelName, String templateCode) {

        TemplateZerotouchListResponse ret =  new TemplateZerotouchListResponse();

        try {
            Flux<TemplateZerotouch> flux = client.backupSearchTemplate(templateName,modelName, templateCode);
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
    public ResponseEntity<Resource> backupTemplateUpload(MultipartFile fileExcel) {
        File file = new File(nRequest.getSessionId() + fileExcel.getName());
        InputStreamResource ret=null;
        try (OutputStream os = new FileOutputStream(file)) {
            os.write(fileExcel.getBytes());
            InputStream is = new FileInputStream(client.backupTemplateUpload(file).block());
            ret=new InputStreamResource(is);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return ResponseEntity.ok(ret);
    }

    @Override
    public ResponseEntity<BaseResponse> delxml(String optical, List<FileBackup> body) {
        return ResponseEntity.ok(client.delxml(optical, body).block());
    }

    @Override
    public ResponseEntity<FileBackupListResponse> getListBackup(String optical) {
        FileBackupListResponse ret =  new FileBackupListResponse();

        try {
            Flux<FileBackup> flux = client.getListBackup(optical);
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
    public ResponseEntity<BaseResponse> restore(String optical, FileBackup body) {
        return ResponseEntity.ok(client.restore(optical, body).block());
    }


}
