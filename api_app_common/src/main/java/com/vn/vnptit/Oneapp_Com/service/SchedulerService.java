package com.vn.vnptit.Oneapp_Com.service;

import java.util.ArrayList;

import com.vn.vnptit.Oneapp_Com.common.ConstantString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.vn.vnptit.Oneapp_Com.common.DbHelper;
import com.vn.vnptit.Oneapp_Com.model.DataSource;
import com.vn.vnptit.Oneapp_Com.model.DataSourceProperties;
import com.vn.vnptit.Oneapp_Com.model.KeyEKYCModel;
import com.vn.vnptit.Oneapp_Com.model.OracleParam;

/**
 * @author Danhnq Các hàm chạy theo lịch đưa hết vào đây và tạo lịch
 */

@Service
public class SchedulerService {
	@Autowired
	DataSourceProperties dataSourceProperties;

	public void CapnhatKeyEkyc(KeyEKYCModel noidung ) {

		DataSource oneApp = dataSourceProperties.getDataSource_oneApp();
		String commandText = "common.pkg_app_config.sp_update_token_ekyc";
		
		ArrayList<OracleParam> parameters = new ArrayList<>();
	
		parameters.add(new OracleParam("p_rs", ConstantString.OUTPUT, null, OracleParam.OracleDirect.OUPUT));
		parameters.add(new OracleParam("p_access_token_ekyc", ConstantString.VARCHAR2, noidung.getP_access_token_ekyc(), OracleParam.OracleDirect.INPUT));
		parameters.add(new OracleParam("p_token_id_ekyc", ConstantString.VARCHAR2, noidung.getP_token_id_ekyc(), OracleParam.OracleDirect.INPUT));
		parameters.add(new OracleParam("p_token_key_ekyc", ConstantString.VARCHAR2, noidung.getP_token_key_ekyc(), OracleParam.OracleDirect.INPUT));
		DbHelper dbHelper = new DbHelper();
		dbHelper.openRS(commandText, parameters, oneApp, true);

	}

}
