package com.vn.vnptit.Oneapp_Com.service;

import java.util.ArrayList;

import com.vn.vnptit.Oneapp_Com.common.ConstantString;
import org.apache.http.util.TextUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import com.vn.vnptit.Oneapp_Com.common.DbHelper;
import com.vn.vnptit.Oneapp_Com.model.DataSource;
import com.vn.vnptit.Oneapp_Com.model.DataSourceProperties;
import com.vn.vnptit.Oneapp_Com.model.NotificationRequest;
import com.vn.vnptit.Oneapp_Com.model.OracleParam;
import com.vn.vnptit.Oneapp_Com.model.Response;
import com.vn.vnptit.Oneapp_Com.model.ThongBaoItem;

/**
 * @author DanhNQ
 *
 */
@Service
public class FCMService {

	@Autowired
	DataSourceProperties dataSourceProperties;

	@SuppressWarnings("deprecation")
	public String sendPnsToDevice(NotificationRequest notificationRequestDto) {
		Message message = Message.builder().setToken(notificationRequestDto.getTarget())
				.setNotification(new Notification(notificationRequestDto.getTitle(), notificationRequestDto.getBody()))
				.putData("content", notificationRequestDto.getTitle()).putData("body", notificationRequestDto.getBody())
				.build();

		String response = null;
		try {
			response = FirebaseMessaging.getInstance().send(message);
		} catch (FirebaseMessagingException e) {

		}

		return response;
	}
	

	public void layDanhSachThongBao() {
		DataSource oneApp = dataSourceProperties.getDataSource_oneApp_By_Id_Phanvung(0);
		String commandText = "common.pkg_thong_bao.sp_ds_thongbao_push";

		//lấy ds thông báo cần push
		ArrayList<OracleParam> parameters = new ArrayList<>();
		parameters.add(new OracleParam("p_rs", ConstantString.OUTPUT, null, OracleParam.OracleDirect.OUPUT));
		DbHelper dbHelper = new DbHelper();
		Response result = dbHelper.openRS(commandText, parameters, oneApp, true);

		if (result.getError() == null && !result.getData().isEmpty()) {
				ObjectMapper oMapper = new ObjectMapper();				

				for (int i = 0; i < result.getData().size(); i++) {
					Object item = result.getData().get(i);
					ThongBaoItem itemTB = oMapper.convertValue(item, ThongBaoItem.class);
					// push notification thành công trả về định dạng:
					// projects/oneapp-2b910/messages/0:1639627714698522%668d982c668d982c
					// push lên firebase
					String message = sendPnsToDevice(new NotificationRequest(itemTB.getMobile_id(),
							itemTB.getTieu_de(), itemTB.getNoi_dung()));
					
					updateThongBaoItem(itemTB.getPhanvung_id(), itemTB.getId_nhanvien(), itemTB.getId_thongbao(),
							(!TextUtils.isEmpty(message) && message.contains("messages")) ? 0 : 1); // 0: ko lỗi					
					
				}
		}
	}

	public void updateThongBaoItem(int p_phanvung_id, Number p_id_nhanvien, Number p_id_thongbao, Number p_goi_loi) {
		DataSource oneApp = dataSourceProperties.getDataSource_oneApp_By_Id_Phanvung(0);
		String commandText = "common.pkg_thong_bao.sp_update_thongbao_sau_push_item";

		ArrayList<OracleParam> parameters = new ArrayList<>();
		parameters.add(new OracleParam("p_phanvung_id", ConstantString.NUMBER, String.valueOf(p_phanvung_id),
				OracleParam.OracleDirect.INPUT));
		parameters.add(new OracleParam("p_id_nhanvien", ConstantString.NUMBER,
				p_id_nhanvien == null ? null : p_id_nhanvien.toString(), OracleParam.OracleDirect.INPUT));
		parameters.add(new OracleParam("p_id_thongbao", ConstantString.NUMBER,
				p_id_thongbao == null ? null : p_id_thongbao.toString(), OracleParam.OracleDirect.INPUT));
		parameters.add(new OracleParam("p_goi_loi", ConstantString.NUMBER, p_goi_loi == null ? null : p_goi_loi.toString(),
				OracleParam.OracleDirect.INPUT));

		parameters.add(new OracleParam("p_rs", ConstantString.OUTPUT, null, OracleParam.OracleDirect.OUPUT));
		DbHelper dbHelper = new DbHelper();
		dbHelper.openRS(commandText, parameters, oneApp, true);
		
	}

}
