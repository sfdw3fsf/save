package com.vn.vnptit.Oneapp_Com.managed;

import com.vn.vnptit.Oneapp_Com.service.CshtSyncService;
import com.vn.vnptit.Oneapp_Com.service.DonHangSimKitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.vn.vnptit.Oneapp_Com.service.FCMService;

@Component
public class ScheduledTasks {
	@Autowired
	private FCMService fcmService;
	@Autowired
	private DonHangSimKitService donHangSimKitService;
	@Autowired
	private CshtSyncService cshtSyncService;

	// lập lịch sau 30 phút quét ds thông báo cần push -> push lên firebase
	@Scheduled(fixedRate = 1800000)  //30 phút chạy 1 lần
	@Async
	public void reportCurrentTime() {
		Thread t = new Thread(() -> {
			fcmService.layDanhSachThongBao();
		});
		t.start();
	}

	// Job chạy lúc 3h sáng (AM) mỗi ngày
	@Async
	@Scheduled(cron = "0 0 3 * * ?")
	public void huyDonHangRunAt3AM() {
		donHangSimKitService.layDanhSachDonHangCanHuy();
	}

	// Job chạy lúc 12h trưa (PM) mỗi ngày
	@Async
	@Scheduled(cron = "0 0 12 * * ?")
	public void huyDonHangRunAt12PM() {
		donHangSimKitService.layDanhSachDonHangCanHuy();
	}

	// Job đồng bộ user CSHT từ ONEBSS, chạy lúc 21h hàng ngày, lặp qua từng tỉnh
	@Async
	@Scheduled(cron = "0 0 21 * * ?")
	public void syncCshtRunAt9PM() {
		cshtSyncService.syncAllProvinces();
	}

}
