package com.vn.vnptit.Oneapp_Com.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.vn.vnptit.Oneapp_Com.common.*;
import com.vn.vnptit.Oneapp_Com.managed.LoggingManaged;
import com.vn.vnptit.Oneapp_Com.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Logger;

@Service
@Transactional
public class DonHangSimKitService {
    @Autowired
    DataSourceProperties dataSourceProperties;
    @Autowired
    LoggingManaged loggingManaged;
    private final JsonMapper mapper	= new JsonMapper();
    private Logger logger	= Logger.getLogger(getClass().getName());

    public void layDanhSachDonHangCanHuy() {
        DataSource oneApp = dataSourceProperties.getDataSource_oneApp_By_Id_Phanvung(0);
        String commandText = "banhang.pkg_donhang_simkit_2.sp_lay_ds_donhang_can_huy";

        //lấy ds thông báo cần push
        ArrayList<OracleParam> parameters = new ArrayList<>();
        parameters.add(new OracleParam("p_rsout", ConstantString.OUTPUT, null, OracleParam.OracleDirect.OUPUT));
        DbHelper dbHelper = new DbHelper();
        Response result = dbHelper.openRS(commandText, parameters, oneApp, true);

        if (result.getError() == null && !result.getData().isEmpty()) {
            ObjectMapper oMapper = new ObjectMapper();
            for (int i = 0; i < result.getData().size(); i++) {
                Object item = result.getData().get(i);
                DonHangSimKitHuy itemDH = oMapper.convertValue(item, DonHangSimKitHuy.class);

                if (!StringUtils.isEmpty(itemDH.getSo_dt())) {
                    UUID uuid = UUID.randomUUID();
                    if (checkIsdnStatus(uuid.toString(), itemDH.getSo_dt()) == 1) {
                        capnhatHuyDonHang(itemDH.getPhanvung_id(), itemDH.getId_donhang());
                    }
                }
                else {
                    capnhatHuyDonHang(itemDH.getPhanvung_id(), itemDH.getId_donhang());
                }
            }
        }
    }

    private int checkIsdnStatus(String uuid, String soTB) {
      String url = "http://ccbs/oneBss/check_isdn_status";
//		String url = "https://api-dev-onebss.vnpt.vn/ccbs/oneBss/check_isdn_status";
//		String token = "Bearer eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX3R0X2NjYnMiOiJodW9uZ2x0X3ZucCIsIm1hX3RpbmgiOiJITkkiLCJ1c2VyX25hbWUiOiJodW9uZ2x0LmhuaSIsImdyb3VwSWQiOiIwIiwiaXNzIjoiaHR0cDovL3F1YW50cmkvb2F1dGgvdG9rZW5fa2V5IiwidXNlcl9ncGhvbmUiOiJ0dHRoLnB0cG0yX2huaSIsImlkX2RvbnZpIjoiOTIzMDgzIiwiY2xpZW50X2lkIjoiY2xpZW50YXBwIiwiaXNfYWRtaW4iOiIxIiwic2NvcGUiOlsicmVhZCIsIndyaXRlIl0sIm1hX25oYW52aWVuX2NjYnMiOiJodW9uZ2x0X3ZucCIsInNlbGxpbmdEZXBhcnRtZW50SWQiOiIwIiwiY3NzX3NjaCI6IkNTU19ITkkuIiwiZXhwIjoxNzY1MjYyMjEzLCJ1c2VyX3ZpIjoiMDk0ODE0NjkwNyIsImVtYWlsIjoiaHVvbmdsdC5obmlAdm5wdC52biIsImRzX3F1eWVuX2NodXF1YW4iOiJudWxsIiwiaWRfbmhhbnZpZW5fZGhzeCI6Ijk1MTk3IiwiaWRlbnRpZnkiOiI2NT82NDYxNTY0PTU1NzIwNjczMjU2Nzc1PTUwMDU9MTE9MTU9Mz89MTU9Mz89Njc0PDc_Nz8_NTUzMzwwPzQ9MDw1MDI9NDM_IiwiYXV0aG9yaXRpZXMiOlsiUFJFX0FVVEgiXSwiYXVkIjpbInJlc3RzZXJ2aWNlIl0sImRvbnZpX2RsX2lkIjoiMjg0NDEyIiwibWFfbnYiOiJITkkwMDQwNTIiLCJpZF90aW5odGhhbmgiOiIyMSIsIm5ndW9pZHVuZ19pZCI6IjExNzc4NCJ9.nuwDXsp_odQO4jHMf-kQa4qT3T4-KFf-w1DRiAHvdzErboNWJyTmxGOTdJ-kJKF4Po8xvilShgXaUrlkl1fByN8TbCRiCryCx7181L10IQnb-Ry5JM_WQRR0Zp9PZUzTPVnuSD3SRDktgrcKaDyi7gcZeTEf793ZqDkDhARbORTl3yXaagoor215KOmyUto_bnF9RTdkeb5Re2LdheQNfV_sqNztUdQ_HSSGCwLuO_UUJPlqRM2FkLCBUnC6yjQ5fwzGBovrH0zxG64IydMS0WROJod5MkamV_7OFTp3D--OvGkQUCYlQ-ZW6QPDpSp1UkDFnCvpAGG9ZwSrV-o1Gw";
//        String idenficode = JwtService.getIdentifyFromToken(token.split(" ")[1]);
//        String username = JwtService.getUsernameFromToken(token.split(" ")[1]);
        Map<String, String> header = new HashMap<>();
//        header.put("Authorization", token);
        Map<String, String> body = new HashMap<>();
        body.put("so_tb", soTB);

        ResponseObject response = CallHttp.httpPostBody(header, body, url);

        Map<String, String> log = new HashMap<>();
        log.put(ConstantString.REQUEST_ID, uuid);
        log.put(ConstantString.ACTION, "ccbs/oneBss/check_isdn_status");
        try {
            log.put(ConstantString.INPUT, mapper.writerWithDefaultPrettyPrinter().writeValueAsString(body));
            log.put(ConstantString.LOG_RESPONE, mapper.writerWithDefaultPrettyPrinter().writeValueAsString(response));
        } catch (JsonProcessingException e) {
            logger.info(e.getMessage());
        }
        loggingManaged.info(log.toString());

        if (StringUtils.isEmpty(response.getError())) {
            Map<String, String> ccbs = (Map<String, String>) response.getData();
            //0 là không cho hủy; 1 là cho hủy
            return Integer.parseInt(ccbs.get("data"));
//            return 1;
        }
        else {
            return 0;
        }
    }

    public void capnhatHuyDonHang(Integer phanVungId, Number donHangId) {
        DataSource oneApp = dataSourceProperties.getDataSource_oneApp_By_Id_Phanvung(0);
        String commandText = "banhang.pkg_donhang_simkit_2.sp_capnhat_huy_donhang";

        ArrayList<OracleParam> parameters = new ArrayList<>();
        parameters.add(new OracleParam("p_rsout", ConstantString.OUTPUT, null, OracleParam.OracleDirect.OUPUT));
        parameters.add(new OracleParam("p_phanvung_id", ConstantString.NUMBER, phanVungId == null ? null : String.valueOf(phanVungId),
                OracleParam.OracleDirect.INPUT));
        parameters.add(new OracleParam("p_id_donhang", ConstantString.NUMBER,
                donHangId == null ? null : String.valueOf(donHangId), OracleParam.OracleDirect.INPUT));

        DbHelper dbHelper = new DbHelper();
        dbHelper.openRS(commandText, parameters, oneApp, true);
    }
}
