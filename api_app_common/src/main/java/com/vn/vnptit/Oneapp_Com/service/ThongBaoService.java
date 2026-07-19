package com.vn.vnptit.Oneapp_Com.service;

import java.util.ArrayList;

import com.vn.vnptit.Oneapp_Com.common.ConstantString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.vn.vnptit.Oneapp_Com.common.DbHelper;
import com.vn.vnptit.Oneapp_Com.common.JwtService;
import com.vn.vnptit.Oneapp_Com.common.Utils;
import com.vn.vnptit.Oneapp_Com.model.DataSource;
import com.vn.vnptit.Oneapp_Com.model.DataSourceProperties;
import com.vn.vnptit.Oneapp_Com.model.OracleParam;
import com.vn.vnptit.Oneapp_Com.model.Response;
import com.vn.vnptit.Oneapp_Com.model.ResponseObject;

/**
 * @author DanhNQ
 *
 */
@Service
public class ThongBaoService {
	@Autowired
	DataSourceProperties dataSourceProperties;
	public ResponseEntity<Object> danhsachThongbao(String token, String noidung,			String uuid) {
		ResponseObject modelResponse = new ResponseObject();
		DataSource oneApp = dataSourceProperties.getDataSource_oneApp(token);
		String commandText = "common.pkg_thong_bao.sp_ds_thongbao";

		String idenficode = JwtService
				.getIdentifyFromToken(token.split(" ")[1]);
		ArrayList<OracleParam> parameters = new ArrayList<>();
		parameters.add(new OracleParam("p_identify_code", ConstantString.VARCHAR2,
				idenficode, OracleParam.OracleDirect.INPUT));
		parameters.add(new OracleParam("p_noidung", ConstantString.VARCHAR2,
				noidung, OracleParam.OracleDirect.INPUT));
		parameters.add(new OracleParam("p_rs", ConstantString.OUTPUT, null,
				OracleParam.OracleDirect.OUPUT));
		DbHelper dbHelper = new DbHelper();
		Response result = dbHelper.openRS(commandText, parameters, oneApp,
				true);
		modelResponse.setData(result.getData());

		if (result.getError() != null) {
			modelResponse = Utils.badRequest(uuid);
			modelResponse.setMessage(result.getMessage());

		} else {
			if (result.getData().isEmpty()) {
				modelResponse = Utils.notFound(uuid);

			} else {

				modelResponse.setData(result.getData());
				modelResponse.setRequest_id(uuid);
				return ResponseEntity.ok().body(modelResponse);
			}
		}
		return ResponseEntity
				.status(Integer.parseInt(modelResponse.getError()))
				.body(modelResponse);
	}
	public ResponseEntity<Object> chitietThongbao(String token, Integer p_id_thongbao,			String uuid) {
		ResponseObject modelResponse = new ResponseObject();
		DataSource oneApp = dataSourceProperties.getDataSource_oneApp(token);
		String commandText = "common.pkg_thong_bao.sp_chitiet_thongbao";

		String idenficode = JwtService
				.getIdentifyFromToken(token.split(" ")[1]);
		ArrayList<OracleParam> parameters = new ArrayList<>();
		parameters.add(new OracleParam("p_identify_code", ConstantString.VARCHAR2,
				idenficode, OracleParam.OracleDirect.INPUT));
		parameters.add(new OracleParam("p_id_thongbao", ConstantString.VARCHAR2,
				p_id_thongbao==null?null:p_id_thongbao.toString(), OracleParam.OracleDirect.INPUT));
		parameters.add(new OracleParam("p_rs", ConstantString.OUTPUT, null,
				OracleParam.OracleDirect.OUPUT));
		DbHelper dbHelper = new DbHelper();
		Response result = dbHelper.openRS(commandText, parameters, oneApp,
				true);
		modelResponse.setData(result.getData());

		if (result.getError() != null) {
			modelResponse = Utils.badRequest(uuid);
			modelResponse.setMessage(result.getMessage());

		} else {
			if (result.getData().isEmpty()) {
				modelResponse = Utils.notFound(uuid);

			} else {

				modelResponse.setData(result.getData());
				modelResponse.setRequest_id(uuid);
				return ResponseEntity.ok().body(modelResponse);
			}
		}
		return ResponseEntity
				.status(Integer.parseInt(modelResponse.getError()))
				.body(modelResponse);
	}
}
