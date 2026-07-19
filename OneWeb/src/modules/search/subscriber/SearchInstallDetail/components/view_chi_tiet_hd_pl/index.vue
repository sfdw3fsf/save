<template>
  <div>
    <div class="modal-content popup-box">
      <!-- <div class="popup-header">
        <div class="title"><span class="icon one-notepad"></span> Chi tiết hợp đồng - Phụ lục</div>
        <div class="close -ap icon-close" data-dismiss="modal" @click="$bvModal.hide('pop_chitiethopdong')"></div>
      </div> -->

      <div class="popup-body">
        <div class="nav tabs tab-over">
          <a data-toggle="tab" class="active" href="#hopdongthuebao"> Hợp đồng thuê bao</a>
          <a data-toggle="tab" href="#hopdongkhachhang"> Hợp đồng khách hàng</a>
          <a data-toggle="tab" href="#hopdongthanhtoan">Hợp đồng thanh toán</a>
          <a data-toggle="tab" href="#phieuthanhtoan">Phiếu thanh toán</a>
        </div>
        <div class="tab-content">
          <div id="hopdongthuebao" class="tab-pane in active">
            <div class="box-form">
              <div class="legend-title"><span @click="thongTinChinh.visible = !thongTinChinh.visible" class="" :class="{ 'icon fa fa-angle-up': thongTinChinh.visible, 'icon fa fa-angle-down': !thongTinChinh.visible }"></span> Thông tin chính</div>
              <div v-show="thongTinChinh.visible">
                <div class="row">
                  <div class="col-sm-6 col-12 info-row">
                    <div class="key w140">Mã thuê bao</div>
                    <div class="value">
                      <input :disabled="!txtMaTB.enabled" type="text" class="form-control" v-model="txtMaTB.value" />
                    </div>
                  </div>
                  <div class="col-sm-6 col-12 info-row">
                    <div class="key w140">Tên thuê bao</div>
                    <div class="value">
                      <input :disabled="!txtTenTB.enabled" type="text" class="form-control" v-model="txtTenTB.value" />
                    </div>
                  </div>
                  <div class="col-sm-6 col-12 info-row">
                    <div class="key w140">Địa chỉ thuê bao</div>
                    <div class="value">
                      <input :disabled="!txtDiaChiTB.enabled" type="text" class="form-control" v-model="txtDiaChiTB.value" />
                    </div>
                  </div>
                  <div class="col-sm-6 col-12 info-row">
                    <div class="key w140">Địa chỉ lắp đặt</div>
                    <div class="value">
                      <input :disabled="!txtDiaChiLD.enabled" type="text" class="form-control" v-model="txtDiaChiLD.value" />
                    </div>
                  </div>
                  <div class="col-sm-6 col-12 info-row">
                    <div class="key w140">Mã tb cha</div>
                    <div class="value">
                      <input :disabled="!txtMaTBCha.enabled" type="text" class="form-control" v-model="txtMaTBCha.value" />
                    </div>
                  </div>
                  <div class="col-sm-6 col-12 info-row">
                    <div class="key w140">Mã thanh toán</div>
                    <div class="value">
                      <input :disabled="!txtMaThanhToan.enabled" type="text" class="form-control" v-model="txtMaThanhToan.value" />
                    </div>
                  </div>
                  <div class="col-sm-6 col-12 info-row">
                    <div class="key w140">Kiểu lắp đặt</div>
                    <div class="value">
                      <input :disabled="!txtKieuLD.enabled" type="text" class="form-control" v-model="txtKieuLD.value" />
                    </div>
                  </div>
                  <div class="col-sm-6 col-12 info-row">
                    <div class="key w140">Dịch vụ</div>
                    <div class="value">
                      <input :disabled="!txtDichVu.enabled" type="text" class="form-control" v-model="txtDichVu.value" />
                    </div>
                  </div>
                  <div class="col-sm-6 col-12 info-row">
                    <div class="key w140">Loại hình</div>
                    <div class="value">
                      <input :disabled="!txtLoaiHinh.enabled" type="text" class="form-control" v-model="txtLoaiHinh.value" />
                    </div>
                  </div>
                  <div class="col-sm-6 col-12 info-row">
                    <div class="key w140">Đối tượng</div>
                    <div class="value">
                      <input :disabled="!txtDoiTuong.enabled" type="text" class="form-control" v-model="txtDoiTuong.value" />
                    </div>
                  </div>
                  <div class="col-sm-6 col-12 info-row">
                    <div class="key w140">Trung tâm viễn thông</div>
                    <div class="value">
                      <input :disabled="!txtTrungTamVT.enabled" type="text" class="form-control" v-model="txtTrungTamVT.value" />
                    </div>
                  </div>
                  <div class="col-sm-6 col-12 info-row">
                    <div class="key w140">Ngày thanh toán</div>
                    <div class="value">
                      <input :disabled="!txtNgayThanhToan.enabled" type="text" class="form-control" v-model="txtNgayThanhToan.value" />
                    </div>
                  </div>
                  <div class="col-sm-6 col-12 info-row">
                    <div class="key w140">Ngày sinh nhật</div>
                    <div class="value">
                      <input :disabled="!txtNgaySinhNhat.enabled" type="text" class="form-control" v-model="txtNgaySinhNhat.value" />
                    </div>
                  </div>
                  <div class="col-sm-6 col-12 info-row">
                    <div class="key w140">Ngày kích hoạt</div>
                    <div class="value">
                      <input :disabled="!txtNgayKichHoat.enabled" type="text" class="form-control" v-model="txtNgayKichHoat.value" />
                    </div>
                  </div>
                  <div class="col-sm-6 col-12 info-row">
                    <div class="key w140">Người kích hoạt</div>
                    <div class="value">
                      <input :disabled="!txtNguoiKichHoat.enabled" type="text" class="form-control" v-model="txtNguoiKichHoat.value" />
                    </div>
                  </div>
                  <div class="col-sm-6 col-12 info-row">
                    <div class="key w140">IP kích hoạt</div>
                    <div class="value">
                      <input :disabled="!txtIPKichHoat.enabled" type="text" class="form-control" v-model="txtIPKichHoat.value" />
                    </div>
                  </div>
                  <div class="col-sm-6 col-12 info-row">
                    <div class="key w140">Ngày hoàn thành</div>
                    <div class="value">
                      <input :disabled="!txtNgayHoanThanh.enabled" type="text" class="form-control" v-model="txtNgayHoanThanh.value" />
                    </div>
                  </div>
                  <div class="col-sm-6 col-12 info-row">
                    <div class="key w140">Ngày nghiệm thu</div>
                    <div class="value">
                      <input :disabled="!txtNgayNghiemThu.enabled" type="text" class="form-control" v-model="txtNgayNghiemThu.value" />
                    </div>
                  </div>
                  <div class="col-sm-6 col-12 info-row">
                    <div class="key w140">Chi tiêu TG</div>
                    <div class="value">
                      <input :disabled="!txtChiTieuTG.enabled" type="text" class="form-control" v-model="txtChiTieuTG.value" />
                    </div>
                  </div>
                  <div class="col-sm-6 col-12 info-row">
                    <div class="key w140">Thời gian CK</div>
                    <div class="value">
                      <input :disabled="!txtThoiGianCK.enabled" type="text" class="form-control" v-model="txtThoiGianCK.value" />
                    </div>
                  </div>
                  <div class="col-sm-12 col-12 info-row">
                    <div class="key w140">Số tháng</div>
                    <div class="value">
                      <input :disabled="!txtSoThang.enabled" type="text" class="form-control" v-model="txtSoThang.value" />
                    </div>
                  </div>
                  <div class="col-sm-6 col-12 info-row">
                    <div class="key w140">Ngày bắt đầu khóa/mở</div>
                    <div class="value">
                      <input :disabled="!txtNgayBDTD.enabled" type="text" class="form-control" v-model="txtNgayBDTD.value" />
                    </div>
                  </div>
                  <div class="col-sm-6 col-12 info-row">
                    <div class="key w140">Ngày hết hạn tạm dừng</div>
                    <div class="value">
                      <input :disabled="!txtNgayHHTD.enabled" type="text" class="form-control" v-model="txtNgayHHTD.value" />
                    </div>
                  </div>
                </div>
              </div>
            </div>
            <div class="box-form">
              <div class="legend-title"><span @click="thongTinThem.visible = !thongTinThem.visible" class="" :class="{ 'icon fa fa-angle-up': thongTinThem.visible, 'icon fa fa-angle-down': !thongTinThem.visible }"></span> Thông tin thêm</div>
              <div v-show="thongTinThem.visible" id="collapse-ttt">
                <div class="row">
                  <div class="col-sm-6 col-12 info-row">
                    <div class="key w140">Cam kết CLG</div>
                    <div class="value">
                      <input :disabled="!txtCamKetCLG.enabled" type="text" class="form-control" v-model="txtCamKetCLG.value" />
                    </div>
                  </div>
                  <div class="col-sm-6 col-12 info-row">
                    <div class="key w140">Loại giấy tờ</div>
                    <div class="value">
                      <input :disabled="!txtLoaiGiayTo.enabled" type="text" class="form-control" v-model="txtLoaiGiayTo.value" />
                    </div>
                  </div>
                  <div class="col-sm-6 col-12 info-row">
                    <div class="key w140">Số giấy tờ</div>
                    <div class="value">
                      <input :disabled="!txtSoGiayTo.enabled" type="text" class="form-control" v-model="txtSoGiayTo.value" />
                    </div>
                  </div>
                  <div class="col-sm-6 col-12 info-row">
                    <div class="key w140">Chức danh</div>
                    <div class="value">
                      <input :disabled="!txtChucDanh.enabled" type="text" class="form-control" v-model="txtChucDanh.value" />
                    </div>
                  </div>
                  <div class="col-sm-12 col-12 info-row">
                    <div class="key w140">Ngày hết hạn GT</div>
                    <div class="value">
                      <input :disabled="!txtNgayHetHanGT.enabled" type="text" class="form-control" v-model="txtNgayHetHanGT.value" />
                    </div>
                  </div>
                </div>
              </div>
            </div>
            <div class="box-form">
              <div class="legend-title"><span @click="thongTinKyThuat.visible = !thongTinKyThuat.visible" class="" :class="{ 'icon fa fa-angle-up': thongTinKyThuat.visible, 'icon fa fa-angle-down': !thongTinKyThuat.visible }"></span>Thông tin kỹ thuật</div>
              <div v-show="thongTinKyThuat.visible">
                <div class="row">
                  <div class="col-sm-6 col-12 info-row">
                    <div class="key w140">Số ảo</div>
                    <div class="value">
                      <input :disabled="!txtSoAo.enabled" type="text" class="form-control" v-model="txtSoAo.value" />
                    </div>
                  </div>
                  <div class="col-sm-6 col-12 info-row">
                    <div class="key w140">Mã đôi cáp</div>
                    <div class="value">
                      <input :disabled="!txtMaDoiCap.enabled" type="text" class="form-control" v-model="txtMaDoiCap.value" />
                    </div>
                  </div>
                  <div class="col-sm-6 col-12 info-row">
                    <div class="key w140">Tốc độ</div>
                    <div class="value">
                      <input :disabled="!txtTocDo.enabled" type="text" class="form-control" v-model="txtTocDo.value" />
                    </div>
                  </div>
                  <div class="col-sm-6 col-12 info-row">
                    <div class="key w140">Mức cước</div>
                    <div class="value">
                      <input :disabled="!txtMucDo.enabled" type="text" class="form-control" v-model="txtMucDo.value" />
                    </div>
                  </div>
                  <div class="col-sm-6 col-12 info-row">
                    <div class="key w140">Thiết bị</div>
                    <div class="value">
                      <input :disabled="!txtThietBi.enabled" type="text" class="form-control" v-model="txtThietBi.value" />
                    </div>
                  </div>
                  <div class="col-sm-6 col-12 info-row">
                    <div class="key w140">Mã TN</div>
                    <div class="value">
                      <input :disabled="!txtMaTN.enabled" type="text" class="form-control" v-model="txtMaTN.value" />
                    </div>
                  </div>
                  <div class="col-sm-6 col-12 info-row">
                    <div class="key w140">Trang bị</div>
                    <div class="value">
                      <input :disabled="!txtTrangBi.enabled" type="text" class="form-control" v-model="txtTrangBi.value" />
                    </div>
                  </div>
                  <div class="col-sm-6 col-12 info-row">
                    <div class="key w140">Thông tin TC</div>
                    <div class="value">
                      <input :disabled="!txtThongTinTC.enabled" type="text" class="form-control" v-model="txtThongTinTC.value" />
                    </div>
                  </div>
                  <div class="col-sm-6 col-12 info-row">
                    <div class="key w140">Chủ quản</div>
                    <div class="value">
                      <input :disabled="!txtChuQuan.enabled" type="text" class="form-control" v-model="txtChuQuan.value" />
                    </div>
                  </div>
                  <div class="col-sm-6 col-12 info-row">
                    <div class="key w140">Loại TB MyTV</div>
                    <div class="value">
                      <input :disabled="!txtLoaiTBMyTV.enabled" type="text" class="form-control" v-model="txtLoaiTBMyTV.value" />
                    </div>
                  </div>
                  <div class="col-sm-12 col-12 info-row">
                    <div class="key w140">Công nghệ</div>
                    <div class="value">
                      <input :disabled="!txtCongNghe.enabled" type="text" class="form-control" v-model="txtCongNghe.value" />
                    </div>
                  </div>
                  <div class="col-sm-6 col-12 info-row">
                    <div class="key w140">Số ảo</div>
                    <div class="value">
                      <input :disabled="!txtSoAo.enabled" type="text" class="form-control" v-model="txtSoAo.value" />
                    </div>
                  </div>
                  <div class="col-sm-6 col-12 info-row">
                    <div class="key w140">Mã TN</div>
                    <div class="value">
                      <input :disabled="!txtMaTN.enabled" type="text" class="form-control" v-model="txtMaTN.value" />
                    </div>
                  </div>
                  <div class="col-sm-6 col-12 info-row">
                    <div class="key w140">Loại Node</div>
                    <div class="value">
                      <input :disabled="!txtLoaiNode.enabled" type="text" class="form-control" v-model="txtLoaiNode.value" />
                    </div>
                  </div>
                  <div class="col-sm-6 col-12 info-row">
                    <div class="key w140">IP WAN</div>
                    <div class="value">
                      <input :disabled="!txtIPWan.enabled" type="text" class="form-control" v-model="txtIPWan.value" />
                    </div>
                  </div>
                  <div class="col-sm-6 col-12 info-row">
                    <div class="key w140">IP LAN</div>
                    <div class="value">
                      <input :disabled="!txtIPLAN.enabled" type="text" class="form-control" v-model="txtIPLAN.value" />
                    </div>
                  </div>
                  <div class="col-sm-6 col-12 info-row">
                    <div class="key w140">Site ID</div>
                    <div class="value">
                      <input :disabled="!txtSiteID.enabled" type="text" class="form-control" v-model="txtSiteID.value" />
                    </div>
                  </div>
                  <div class="col-sm-6 col-12 info-row">
                    <div class="key w140">Mã HĐ - TTKD</div>
                    <div class="value">
                      <input :disabled="!txtMaHDTTKD.enabled" type="text" class="form-control" v-model="txtMaHDTTKD.value" />
                    </div>
                  </div>
                  <div class="col-sm-6 col-12 info-row">
                    <div class="key w140">Hướng KN</div>
                    <div class="value">
                      <input :disabled="!txtHuongKN.enabled" type="text" class="form-control" v-model="txtHuongKN.value" />
                    </div>
                  </div>
                  <div class="col-sm-6 col-12 info-row">
                    <div class="key w140">Tỉnh KN</div>
                    <div class="value">
                      <input :disabled="!txtTinhKN.enabled" type="text" class="form-control" v-model="txtTinhKN.value" />
                    </div>
                  </div>
                  <div class="col-sm-6 col-12 info-row">
                    <div class="key w140">Modem</div>
                    <div class="value">
                      <input :disabled="!txtModem.enabled" type="text" class="form-control" v-model="txtModem.value" />
                    </div>
                  </div>
                  <div class="col-sm-6 col-12 info-row">
                    <div class="key w140">Địa chỉ KN</div>
                    <div class="value">
                      <input :disabled="!txtDiaChiKN.enabled" type="text" class="form-control" v-model="txtDiaChiKN.value" />
                    </div>
                  </div>
                  <div class="col-sm-6 col-12 info-row">
                    <div class="key w140">Loại thiết bị</div>
                    <div class="value">
                      <input :disabled="!txtLoaiThietBi.enabled" type="text" class="form-control" v-model="txtLoaiThietBi.value" />
                    </div>
                  </div>
                  <div class="col-sm-6 col-12 info-row">
                    <div class="key w140">Loại kênh</div>
                    <div class="value">
                      <input :disabled="!txtLoaiKenh.enabled" type="text" class="form-control" v-model="txtLoaiKenh.value" />
                    </div>
                  </div>
                  <div class="col-sm-6 col-12 info-row">
                    <div class="key w140">Chủ quản</div>
                    <div class="value">
                      <input :disabled="!txtChuQuan.enabled" type="text" class="form-control" v-model="txtChuQuan.value" />
                    </div>
                  </div>
                  <div class="col-sm-6 col-12 info-row">
                    <div class="key w140">Trang bị</div>
                    <div class="value">
                      <input :disabled="!txtTrangBi.enabled" type="text" class="form-control" v-model="txtTrangBi.value" />
                    </div>
                  </div>
                  <div class="col-sm-6 col-12 info-row">
                    <div class="key w140">Tốc độ CIR</div>
                    <div class="value">
                      <input :disabled="!txtTocDoCIR.enabled" type="text" class="form-control" v-model="txtTocDoCIR.value" />
                    </div>
                  </div>
                  <div class="col-sm-6 col-12 info-row">
                    <div class="key w140">Luồng giữ</div>
                    <div class="value">
                      <input :disabled="!txtLuongGiu.enabled" type="text" class="form-control" v-model="txtLuongGiu.value" />
                    </div>
                  </div>
                  <div class="col-sm-6 col-12 info-row">
                    <div class="key w140">Tốc độ PIR</div>
                    <div class="value">
                      <input :disabled="!txtTocDoPIR.enabled" type="text" class="form-control" v-model="txtTocDoPIR.value" />
                    </div>
                  </div>
                  <div class="col-sm-6 col-12 info-row">
                    <div class="key w140">Thời hạn</div>
                    <div class="value">
                      <input :disabled="!txtThoiHan.enabled" type="text" class="form-control" v-model="txtThoiHan.value" />
                    </div>
                  </div>
                  <div class="col-sm-6 col-12 info-row">
                    <div class="key w140">Số lượng IP</div>
                    <div class="value">
                      <input :disabled="!txtSoLuongIP.enabled" type="text" class="form-control" v-model="txtSoLuongIP.value" />
                    </div>
                  </div>
                  <div class="col-sm-6 col-12 info-row">
                    <div class="key w140">Tốc độ NIX</div>
                    <div class="value">
                      <input :disabled="!txtTocDoNIX.enabled" type="text" class="form-control" v-model="txtTocDoNIX.value" />
                    </div>
                  </div>
                  <div class="col-sm-6 col-12 info-row">
                    <div class="key w140">Cước NIX</div>
                    <div class="value">
                      <input :disabled="!txtCuocNIX.enabled" type="text" class="form-control" v-model="txtCuocNIX.value" />
                    </div>
                  </div>
                  <div class="col-sm-6 col-12 info-row">
                    <div class="key w140">Cước IP</div>
                    <div class="value">
                      <input :disabled="!txtCuocIP.enabled" type="text" class="form-control" v-model="txtCuocIP.value" />
                    </div>
                  </div>
                  <div class="col-sm-6 col-12 info-row">
                    <div class="key w140">Tốc độ ISP</div>
                    <div class="value">
                      <input :disabled="!txtTocDoISP.enabled" type="text" class="form-control" v-model="txtTocDoISP.value" />
                    </div>
                  </div>
                  <div class="col-sm-6 col-12 info-row">
                    <div class="key w140">Cước ISP</div>
                    <div class="value">
                      <input :disabled="!txtCuocISP.enabled" type="text" class="form-control" v-model="txtCuocISP.value" />
                    </div>
                  </div>
                  <div class="col-sm-6 col-12 info-row">
                    <div class="key w140">Cước thuê cổng(no VAT)</div>
                    <div class="value">
                      <input :disabled="!txtCuocThueCong.enabled" type="text" class="form-control" v-model="txtCuocThueCong.value" />
                    </div>
                  </div>
                  <div class="col-sm-6 col-12 info-row">
                    <div class="key w140">Cước thuê kênh(no VAT)</div>
                    <div class="value">
                      <input :disabled="!txtCuocThueKenh.enabled" type="text" class="form-control" v-model="txtCuocThueKenh.value" />
                    </div>
                  </div>
                  <div class="col-sm-6 col-12 info-row">
                    <div class="key w140">Cước thiết bị(no VAT)</div>
                    <div class="value">
                      <input :disabled="!txtCuocThietBi.enabled" type="text" class="form-control" v-model="txtCuocThietBi.value" />
                    </div>
                  </div>
                  <div class="col-sm-6 col-12 info-row">
                    <div class="key w140">Cước HT(no VAT)</div>
                    <div class="value">
                      <input :disabled="!txtCuocHT.enabled" type="text" class="form-control" v-model="txtCuocHT.value" />
                    </div>
                  </div>
                  <div class="col-sm-6 col-12 info-row">
                    <div class="key w140">Chi tiết DV</div>
                    <div class="value">
                      <input :disabled="!txtChiTietDV.enabled" type="text" class="form-control" v-model="txtChiTietDV.value" />
                    </div>
                  </div>
                  <div class="col-sm-6 col-12 info-row">
                    <div class="key w140">IP khách hàng</div>
                    <div class="value">
                      <input :disabled="!txtIPKhachHang.enabled" type="text" class="form-control" v-model="txtIPKhachHang.value" />
                    </div>
                  </div>
                  <div class="col-sm-12 col-12 info-row">
                    <div class="key w140">SL cuộc gọi</div>
                    <div class="value">
                      <input :disabled="!txtSLCuocGoi.enabled" type="text" class="form-control" v-model="txtSLCuocGoi.value" />
                    </div>
                  </div>
                  <div class="col-sm-6 col-12 info-row">
                    <div class="key w140">Thông tin WF</div>
                    <div class="value">
                      <input :disabled="!txtThongTinWF.enabled" type="text" class="form-control" v-model="txtThongTinWF.value" />
                    </div>
                  </div>
                  <div class="col-sm-6 col-12 info-row">
                    <div class="key w140">Vị trí WF</div>
                    <div class="value">
                      <input :disabled="!txtViTriWF.enabled" type="text" class="form-control" v-model="txtViTriWF.value" />
                    </div>
                  </div>
                  <div class="col-sm-6 col-12 info-row">
                    <div class="key w140">WF 3M</div>
                    <div class="value">
                      <input :disabled="!txtWF3M.enabled" type="text" class="form-control" v-model="txtWF3M.value" />
                    </div>
                  </div>
                  <div class="col-sm-6 col-12 info-row">
                    <div class="key w140">WF 1T</div>
                    <div class="value">
                      <input :disabled="!txtWF1T.enabled" type="text" class="form-control" v-model="txtWF1T.value" />
                    </div>
                  </div>
                  <div class="col-sm-6 col-12 info-row">
                    <div class="key w140">WF 1TR</div>
                    <div class="value">
                      <input :disabled="!txtWF1TR.enabled" type="text" class="form-control" v-model="txtWF1TR.value" />
                    </div>
                  </div>
                  <div class="col-sm-6 col-12 info-row">
                    <div class="key w140">WF2T</div>
                    <div class="value">
                      <input :disabled="!txtWF2T.enabled" type="text" class="form-control" v-model="txtWF2T.value" />
                    </div>
                  </div>
                  <div class="col-sm-12 col-12 info-row">
                    <div class="key w140">WF 2TR</div>
                    <div class="value">
                      <input :disabled="!txtWF2TR.enabled" type="text" class="form-control" v-model="txtWF2TR.value" />
                    </div>
                  </div>
                </div>
              </div>
            </div>
            <div class="box-form">
              <div class="legend-title"><span @click="thongTinKhac.visible = !thongTinKhac.visible" class="" :class="{ 'icon fa fa-angle-up': thongTinKhac.visible, 'icon fa fa-angle-down': !thongTinKhac.visible }"></span>Thông tin khác</div>
              <div v-show="thongTinKhac.visible">
                <div class="row">
                  <div class="col-sm-12 col-12 info-row">
                    <div class="key w140">Khu vực thuê bao</div>
                    <div class="value">
                      <input :disabled="!txtKhuVucThueBao.enabled" type="text" class="form-control" v-model="txtKhuVucThueBao.value" />
                    </div>
                  </div>
                  <div class="col-sm-12 col-12 info-row">
                    <div class="key w140">Thay đổi CTV</div>
                    <div class="value">
                      <input :disabled="!txtThayDoiCTV.enabled" type="text" class="form-control" v-model="txtThayDoiCTV.value" />
                    </div>
                  </div>
                  <div class="col-sm-12 col-12 info-row">
                    <div class="key w140">Khu vực</div>
                    <div class="value">
                      <input :disabled="!txtKhuVuc.enabled" type="text" class="form-control" v-model="txtKhuVuc.value" />
                    </div>
                  </div>
                  <div>
                    <div class="legend-title"><span @click="thongTinKhac.dsThueBaoDV.visible = !thongTinKhac.dsThueBaoDV.visible" :class="{ 'icon fa fa-angle-up': thongTinKhac.dsThueBaoDV.visible, 'icon fa fa-angle-down': !thongTinKhac.dsThueBaoDV.visible }"></span>Danh sách thuê bao đơn vị</div>
                    <div v-show="thongTinKhac.dsThueBaoDV.visible">
                      <DataGrid v-bind:columns="gridThueBaoDonVi.header" v-bind:dataSource="gridThueBaoDonVi.list" :enable-paging-server="false" :allowPaging="true" :showFilter="true" :toolbar=null />
                    </div>
                  </div>
                  <!-- <div class="col-sm-12 col-12 info-row">
                    <div class="key w140">Hẹn lắp đặt</div>
                    <div class="value">
                      <input :disabled="!txtHenLapDat.enabled" type="text" class="form-control" v-model="txtHenLapDat.value" />
                    </div>
                  </div> -->
                  <!-- <div class="col-sm-12 col-12 info-row">
                    <div class="key w140">Vật tư thuê bao</div>
                    <div class="value">
                      <input :disabled="!txtVatTuThueBao.enabled" type="text" class="form-control" v-model="txtVatTuThueBao.value" />
                    </div>
                  </div> -->
                  <!-- <div class="col-sm-12 col-12 info-row">
                    <div class="key w140">Tiến trình thi công</div>
                    <div class="value">
                      <input :disabled="!txtTienTrinhThiCong.enabled" type="text" class="form-control" v-model="txtTienTrinhThiCong.value" />
                    </div>
                  </div> -->
                  <!-- <div class="col-sm-12 col-12 info-row">
                    <div class="key w140">Thông tin biến động</div>
                    <div class="value">
                      <input :disabled="!txtThongTinBienDong.enabled" type="text" class="form-control" v-model="txtThongTinBienDong.value" />
                    </div>
                  </div> -->
                </div>
              </div>
            </div>
            <div class="box-form">
              <div class="legend-title"><span @click="thongTinTienHD.visible = !thongTinTienHD.visible" class="" :class="{ 'icon fa fa-angle-up': thongTinTienHD.visible, 'icon fa fa-angle-down': !thongTinTienHD.visible }"></span>Thông tin tiền hợp đồng</div>
              <div v-show="thongTinTienHD.visible">
                <div class="title-bg-main">
                  <div class="title"><span class="fa fa-angle-up text-main marr5"></span> Tiền hợp đồng và tiền thanh toán</div>
                </div>
                <DataGrid ref="gridTienHopDong" v-bind:columns="gridTienHopDong.header" v-bind:dataSource="gridTienHopDong.list" :enable-paging-server="false" @queryCellInfo="gridTienHopDongQueryCellInfo" :allowPaging="true" :showFilter="false" :toolbar=null />
              </div>
            </div>
            <div class="box-form">
              <div class="legend-title"><span @click="thongTinKhuyenMai.visible = !thongTinKhuyenMai.visible" class="" :class="{ 'icon fa fa-angle-up': thongTinTienHD.visible, 'icon fa fa-angle-down': !thongTinTienHD.visible }"></span>Thông tin khuyến mại trả trước</div>
              <div v-show="thongTinKhuyenMai.visible">
                <DataGrid ref="gridKhuyenMai" v-bind:columns="gridKhuyenMai.header" v-bind:dataSource="gridKhuyenMai.list" :enable-paging-server="false" @queryCellInfo="gridKhuyenMaiQueryCellInfo" :allowPaging="true" :showFilter="false" :toolbar=null />
              </div>
            </div>
            <div class="box-form">
              <div class="legend-title"><span @click="thongTinNhomGoi.visible = !thongTinNhomGoi.visible" class="" :class="{ 'icon fa fa-angle-up': thongTinNhomGoi.visible, 'icon fa fa-angle-down': !thongTinNhomGoi.visible }"></span>Thông tin nhóm/gói đa dịch vụ</div>
              <div v-show="thongTinNhomGoi.visible">
                <DataGrid ref="gridNhomGoi" v-bind:columns="gridDanhSachNhomGoi.header" v-bind:dataSource="gridDanhSachNhomGoi.list" :enable-paging-server="false" @queryCellInfo="gridDanhSachNhomGoiQueryCellInfo" :allowPaging="true" :showFilter="false" :toolbar=null />
              </div>
            </div>
          </div>
          <div id="hopdongkhachhang" class="tab-pane fade">
            <div class="box-form">
              <div class="row">
                <div class="col-sm-12 col-12 info-row">
                  <div class="key w140">Kiểu hợp đồng</div>
                  <div class="value">
                    <input :disabled="!txtKieuHD.enabled" type="text" class="form-control" v-model="txtKieuHD.value" />
                  </div>
                </div>
                <div class="col-sm-6 col-12 info-row">
                  <div class="key w140">Mã khách hàng</div>
                  <div class="value">
                    <input :disabled="!txtMaKhachHang.enabled" type="text" class="form-control" v-model="txtMaKhachHang.value" />
                  </div>
                </div>
                <div class="col-sm-6 col-12 info-row">
                  <div class="key w140">Tên khách hàng</div>
                  <div class="value">
                    <input :disabled="!txtTenKhachHang.enabled" type="text" class="form-control" v-model="txtTenKhachHang.value" />
                  </div>
                </div>
                <div class="col-sm-6 col-12 info-row">
                  <div class="key w140">Địa chỉ khách hàng</div>
                  <div class="value">
                    <input :disabled="!txtDiaChiKhachHang.enabled" type="text" class="form-control" v-model="txtDiaChiKhachHang.value" />
                  </div>
                </div>
                <div class="col-sm-6 col-12 info-row">
                  <div class="key w140">Người đại diện</div>
                  <div class="value">
                    <input :disabled="!txtNguoiDaiDien.enabled" type="text" class="form-control" v-model="txtNguoiDaiDien.value" />
                  </div>
                </div>
                <div class="col-sm-6 col-12 info-row">
                  <div class="key w140">Số điện thoại</div>
                  <div class="value">
                    <input :disabled="!txtSoDienThoai.enabled" type="text" class="form-control" v-model="txtSoDienThoai.value" />
                  </div>
                </div>
                <div class="col-sm-6 col-12 info-row">
                  <div class="key w140">Số fax</div>
                  <div class="value">
                    <input :disabled="!txtSoFax.enabled" type="text" class="form-control" v-model="txtSoFax.value" />
                  </div>
                </div>
                <div class="col-sm-6 col-12 info-row">
                  <div class="key w140">Email</div>
                  <div class="value">
                    <input :disabled="!txtEmail.enabled" type="text" class="form-control" v-model="txtEmail.value" />
                  </div>
                </div>
                <div class="col-sm-6 col-12 info-row">
                  <div class="key w140">Website</div>
                  <div class="value">
                    <input :disabled="!txtWebsite.enabled" type="text" class="form-control" v-model="txtWebsite.value" />
                  </div>
                </div>
                <div class="col-sm-6 col-12 info-row">
                  <div class="key w140">Ngày SN</div>
                  <div class="value">
                    <input :disabled="!txtNgaySN.enabled" type="text" class="form-control" v-model="txtNgaySN.value" />
                  </div>
                </div>
                <div class="col-sm-6 col-12 info-row">
                  <div class="key w140">Ngày lập HĐ</div>
                  <div class="value">
                    <input :disabled="!txtNgayLapHD.enabled" type="text" class="form-control" v-model="txtNgayLapHD.value" />
                  </div>
                </div>
                <div class="col-sm-6 col-12 info-row">
                  <div class="key w140">Số giấy tờ</div>
                  <div class="value">
                    <input :disabled="!txtSoGiayTO.enabled" type="text" class="form-control" v-model="txtSoGiayTO.value" />
                  </div>
                </div>
                <div class="col-sm-6 col-12 info-row">
                  <div class="key w140">Ngày cấp</div>
                  <div class="value">
                    <input :disabled="!txtNgayCap.enabled" type="text" class="form-control" v-model="txtNgayCap.value" />
                  </div>
                </div>
                <div class="col-sm-6 col-12 info-row">
                  <div class="key w140">Nơi cấp</div>
                  <div class="value">
                    <input :disabled="!txtNoiCap.enabled" type="text" class="form-control" v-model="txtNoiCap.value" />
                  </div>
                </div>
                <div class="col-sm-6 col-12 info-row">
                  <div class="key w140">Ghi chú</div>
                  <div class="value">
                    <input :disabled="!txtGhiChu.enabled" type="text" class="form-control" v-model="txtGhiChu.value" />
                  </div>
                </div>
                <div class="col-sm-6 col-12 info-row">
                  <div class="key w140">Đơn vị bán</div>
                  <div class="value">
                    <input :disabled="!txtDonViBan.enabled" type="text" class="form-control" v-model="txtDonViBan.value" />
                  </div>
                </div>
                <div class="col-sm-6 col-12 info-row">
                  <div class="key w140">Loại giấy tờ</div>
                  <div class="value">
                    <input :disabled="!txtLoaiGiayTo.enabled" type="text" class="form-control" v-model="txtLoaiGiayTo.value" />
                  </div>
                </div>
                <div class="col-sm-6 col-12 info-row">
                  <div class="key w140">Nhân viên bán</div>
                  <div class="value">
                    <input :disabled="!txtNhanVienBan.enabled" type="text" class="form-control" v-model="txtNhanVienBan.value" />
                  </div>
                </div>
                <div class="col-sm-6 col-12 info-row">
                  <div class="key w140">Loại HĐ</div>
                  <div class="value">
                    <input :disabled="!txtLoaiHD.enabled" type="text" class="form-control" v-model="txtLoaiHD.value" />
                  </div>
                </div>
                <div class="col-sm-6 col-12 info-row">
                  <div class="key w140">Loại KH</div>
                  <div class="value">
                    <input :disabled="!txtLoaiKH.enabled" type="text" class="form-control" v-model="txtLoaiKH.value" />
                  </div>
                </div>
                <div class="col-sm-6 col-12 info-row">
                  <div class="key w140">Mã số thuế</div>
                  <div class="value">
                    <input :disabled="!txtMaSoThue.enabled" type="text" class="form-control" v-model="txtMaSoThue.value" />
                  </div>
                </div>
                <div class="col-sm-6 col-12 info-row">
                  <div class="key w140">Số tài khoản</div>
                  <div class="value">
                    <input :disabled="!txtSoTaiKhoan.enabled" type="text" class="form-control" v-model="txtSoTaiKhoan.value" />
                  </div>
                </div>
                <div class="col-sm-6 col-12 info-row">
                  <div class="key w140">Ngân hàng</div>
                  <div class="value">
                    <input :disabled="!txtNganHang.enabled" type="text" class="form-control" v-model="txtNganHang.value" />
                  </div>
                </div>
                <div class="col-sm-6 col-12 info-row">
                  <div class="key w140">Ngày YC</div>
                  <div class="value">
                    <input :disabled="!txtNgayYC.enabled" type="text" class="form-control" v-model="txtNgayYC.value" />
                  </div>
                </div>
                <div class="col-sm-6 col-12 info-row">
                  <div class="key w140">Cộng tác viên</div>
                  <div class="value">
                    <input :disabled="!txtCongTacVien.enabled" type="text" class="form-control" v-model="txtCongTacVien.value" />
                  </div>
                </div>
                <div class="col-sm-6 col-12 info-row">
                  <div class="key w140">Kiểu hợp đồng</div>
                  <div class="value">
                    <input :disabled="!txtKieuHD.enabled" type="text" class="form-control" v-model="txtKieuHD.value" />
                  </div>
                </div>
                <div class="col-sm-6 col-12 info-row">
                  <div class="key w140">Phân loại khách hàng</div>
                  <div class="value">
                    <input :disabled="!txtPhanLoaiKH.enabled" type="text" class="form-control" v-model="txtPhanLoaiKH.value" />
                  </div>
                </div>
                <div class="col-sm-6 col-12 info-row">
                  <div class="key w140">Người lập hợp đồng</div>
                  <div class="value">
                    <input :disabled="!txtNguoiLapHD.enabled" type="text" class="form-control" v-model="txtNguoiLapHD.value" />
                  </div>
                </div>
                <div class="col-sm-6 col-12 info-row">
                  <div class="key w140">NV giới thiệu</div>
                  <div class="value">
                    <input :disabled="!txtNVGioiThieu.enabled" type="text" class="form-control" v-model="txtNVGioiThieu.value" />
                  </div>
                </div>
                <div class="col-sm-12 col-12 info-row">
                  <div class="key w140">Tổng tiền hợp đồng</div>
                  <div class="value">
                    <input :disabled="!txtTongTienHD.enabled" type="text" class="form-control" v-model="txtTongTienHD.value" />
                  </div>
                </div>
                <div class="col-sm-6 col-12 info-row">
                  <div class="key w140">Email khách hàng</div>
                  <div class="value">
                    <input :disabled="!txtEmailKhach.enabled" type="text" class="form-control" v-model="txtEmailKhach.value" />
                  </div>
                </div>
                <div class="col-sm-6 col-12 info-row">
                  <div class="key w140">Kiểu yêu cầu</div>
                  <div class="value">
                    <input :disabled="!txtKieuYeuCau.enabled" type="text" class="form-control" v-model="txtKieuYeuCau.value" />
                  </div>
                </div>
                <div class="col-sm-6 col-12 info-row">
                  <div class="key w140">Trạng thái email</div>
                  <div class="value">
                    <input :disabled="!txtTrangThaiEmail.enabled" type="text" class="form-control" v-model="txtTrangThaiEmail.value" />
                  </div>
                </div>
                <div class="col-sm-6 col-12 info-row">
                  <div class="key w140">Mục đích email</div>
                  <div class="value">
                    <input :disabled="!txtMucDichEmail.enabled" type="text" class="form-control" v-model="txtMucDichEmail.value" />
                  </div>
                </div>
              </div>
            </div>

            <div class="box-form">
              <div class="legend-title"><span @click="thongTinTienHDKH.visible = !thongTinTienHDKH.visible" class="" :class="{ 'icon fa fa-angle-up': thongTinTienHDKH.visible, 'icon fa fa-angle-down': !thongTinTienHDKH.visible }"></span>Thông tin tiền hợp đồng</div>
              <div v-show="thongTinTienHDKH.visible">
                <div class="title-bg-main">
                  <div class="title"><span class="fa fa-angle-up text-main marr5"></span> Tiền hợp đồng và tiền thanh toán</div>
                </div>
                <DataGrid ref="gridTienHopDong" v-bind:columns="gridTienHopDong.header" v-bind:dataSource="gridTienHopDong.list" :enable-paging-server="false" @queryCellInfo="gridTienHopDongQueryCellInfo" :allowPaging="true" :showFilter="false" :toolbar=null />
              </div>
            </div>
            <div class="box-form">
              <div class="legend-title"><span @click="thongTinLSOB.visible = !thongTinLSOB.visible" class="" :class="{ 'icon fa fa-angle-up': thongTinLSOB.visible, 'icon fa fa-angle-down': !thongTinLSOB.visible }"></span>Thông tin lịch sử CSKH</div>
              <div v-show="thongTinLSOB.visible">
                <div class="title-bg-main">
                  <div class="title"><span class="fa fa-angle-up text-main marr5"></span> Lịch sử CSKH</div>
                </div>
                <DataGrid ref="gridLichSuOB" v-bind:columns="gridLichSuOB.header" v-bind:dataSource="gridLichSuOB.list" :enable-paging-server="false" @queryCellInfo="gridTienHopDongQueryCellInfo" :allowPaging="true" :showFilter="false" :toolbar=null />
              </div>
            </div>
          </div>
          <div id="hopdongthanhtoan" class="tab-pane fade">
            <div class="box-form">
              <div class="row">
                <div class="col-sm-6 col-12">
                  <div class="info-row">
                    <div class="key w140">Mã thanh toán</div>
                    <div class="value">
                      <input :disabled="!txtMaThanhToan.enabled" type="text" class="form-control" v-model="txtMaThanhToan.value" />
                    </div>
                  </div>
                  <div class="info-row">
                    <div class="key w140">Địa chỉ thanh toán</div>
                    <div class="value">
                      <input :disabled="!txtDiaChiThanhToan.enabled" type="text" class="form-control" v-model="txtDiaChiThanhToan.value" />
                    </div>
                  </div>

                  <div class="info-row">
                    <div class="key w140">Mã số thuế</div>
                    <div class="value">
                      <input :disabled="!txtMaSoThue.enabled" type="text" class="form-control" v-model="txtMaSoThue.value" />
                    </div>
                  </div>
                  <div class="info-row">
                    <div class="key w140">Đơn vị quản lý</div>
                    <div class="value">
                      <input :disabled="!txtDonViQL.enabled" type="text" class="form-control" v-model="txtDonViQL.value" />
                    </div>
                  </div>
                  <div class="info-row">
                    <div class="key w140">Ngân hàng</div>
                    <div class="value">
                      <input :disabled="!txtNganHang.enabled" type="text" class="form-control" v-model="txtNganHang.value" />
                    </div>
                  </div>
                  <div class="info-row">
                    <div class="key w140">Email</div>
                    <div class="value">
                      <input :disabled="!txtEmail.enabled" type="text" class="form-control" v-model="txtEmail.value" />
                    </div>
                  </div>
                  <div class="info-row">
                    <div class="key w140">Tuyến thu</div>
                    <div class="value">
                      <input :disabled="!txtTuyenThu.enabled" type="text" class="form-control" v-model="txtTuyenThu.value" />
                    </div>
                  </div>
                </div>
                <div class="col-sm-6 col-12">
                  <div class="info-row">
                    <div class="key w140">Tên thanh toán</div>
                    <div class="value">
                      <input :disabled="!txtTenThanhToan.enabled" type="text" class="form-control" v-model="txtTenThanhToan.value" />
                    </div>
                  </div>
                  <div class="info-row">
                    <div class="key w140">Mã TB ĐD</div>
                    <div class="value">
                      <input :disabled="!txtMaTBDD.enabled" type="text" class="form-control" v-model="txtMaTBDD.value" />
                    </div>
                  </div>
                  <div class="info-row">
                    <div class="key w140">Số tài khoản</div>
                    <div class="value">
                      <input :disabled="!txtSoTaiKhoan.enabled" type="text" class="form-control" v-model="txtSoTaiKhoan.value" />
                    </div>
                  </div>
                  <div class="info-row">
                    <div class="key w140">Hình thức thanh toán</div>
                    <div class="value">
                      <input :disabled="!txtHinhThucThanhToan.enabled" type="text" class="form-control" v-model="txtHinhThucThanhToan.value" />
                    </div>
                  </div>
                  <div class="info-row">
                    <div class="key w140">Số điện thoại</div>
                    <div class="value">
                      <input :disabled="!txtSoDienThoai.enabled" type="text" class="form-control" v-model="txtSoDienThoai.value" />
                    </div>
                  </div>
                  <div class="info-row">
                    <div class="key w140">Địa chỉ chứng từ</div>
                    <div class="value">
                      <input :disabled="!txtDiaChiChungTu.enabled" type="text" class="form-control" v-model="txtDiaChiChungTu.value" />
                    </div>
                  </div>
                  <div class="info-row">
                    <div class="key w140">Ghi chú</div>
                    <div class="value">
                      <input :disabled="!txtGhiChu.enabled" type="text" class="form-control" v-model="txtGhiChu.value" />
                    </div>
                  </div>
                </div>
              </div>
              <div class="info-row">
                <div class="key w140">Địa chỉ báo cước</div>
                <div class="value">
                  <input :disabled="!txtDiaChiBaoCuoc.enabled" type="text" class="form-control" v-model="txtDiaChiBaoCuoc.value" />
                </div>
              </div>
              <div class="row">
                <div class="col-sm-6 col-12">
                  <div class="info-row">
                    <div class="key w140">Email thanh toán</div>
                    <div class="value">
                      <input :disabled="!txtEmailThanhToan.enabled" type="text" class="form-control" v-model="txtEmailThanhToan.value" />
                    </div>
                  </div>
                  <div class="info-row">
                    <div class="key w140">Trạng thái email</div>
                    <div class="value">
                      <input :disabled="!txtTrangThaiEmail.enabled" type="text" class="form-control" v-model="txtTrangThaiEmail.value" />
                    </div>
                  </div>
                </div>
                <div class="col-sm-6 col-12">
                  <div class="info-row">
                    <div class="key w140">Kiểu yêu cầu</div>
                    <div class="value">
                      <input :disabled="!txtKieuYeuCau.enabled" type="text" class="form-control" v-model="txtKieuYeuCau.value" />
                    </div>
                  </div>
                  <div class="info-row">
                    <div class="key w140">Mục đích email</div>
                    <div class="value">
                      <input :disabled="!txtMucDichEmail.enabled" type="text" class="form-control" v-model="txtMucDichEmail.value" />
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>
          <div id="phieuthanhtoan" class="tab-pane fade">
            <div class="row">
              <div class="box-form">
                <div class="legend-title"><span @click="thongTinPhieuTT.visible = !thongTinKhoanMucTT.visible" class="" :class="{ 'icon fa fa-angle-up': thongTinPhieuTT.visible, 'icon fa fa-angle-down': !thongTinPhieuTT.visible }"></span>Danh sách phiếu thanh toán</div>
                <div v-show="thongTinPhieuTT.visible">
                  <DataGrid v-bind:columns="gridPhieuhopdong.header" v-bind:dataSource="gridPhieuhopdong.list" :enable-paging-server="false" :allowPaging="true" :showFilter="false" @queryCellInfo="dsThanhToan_QueryCellInfo" @rowSelected="onRowSelected" :toolbar=null  />
                </div>
              </div>
              <div class="box-form">
                <div class="legend-title"><span @click="thongTinKhoanMucTT.visible = !thongTinKhoanMucTT.visible" class="" :class="{ 'icon fa fa-angle-up': thongTinKhoanMucTT.visible, 'icon fa fa-angle-down': !thongTinKhoanMucTT.visible }"></span>Danh sách khoản mục thanh toán</div>
                <div v-show="thongTinKhoanMucTT.visible">
                  <DataGrid v-bind:columns="gridPhieuthanhtoan.header" v-bind:dataSource="gridPhieuthanhtoan.list" :enable-paging-server="false" @queryCellInfo="dsThanhToan_QueryCellInfo" :allowPaging="true" :showFilter="false" :toolbar=null />
                </div>
              </div>
              <div class="box-form">
                <div class="legend-title"><span @click="thongTinLichSuHenTT.visible = !thongTinLichSuHenTT.visible" class="" :class="{ 'icon fa fa-angle-up': thongTinLichSuHenTT.visible, 'icon fa fa-angle-down': !thongTinLichSuHenTT.visible }"></span>Lịch sử hẹn thanh toán</div>
                <div v-show="thongTinLichSuHenTT.visible">
                  <DataGrid v-bind:columns="gridLichSuHenTT.header" v-bind:dataSource="gridLichSuHenTT.list" :enable-paging-server="false" :allowPaging="true" :showFilter="false" :toolbar=null />
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
import api from './api'
export default {
  props: {
    vhdtb_id: {
      type: [String, Number],
      required: true
    },
    vhdkh_id: {
      type: [String, Number],
      required: true
    }
  },
  data() {
    return {
      thongTinChinh: { visible: true },
      thongTinThem: { visible: true },
      thongTinKyThuat: { visible: true },
      thongTinKhac: { visible: true, dsThueBaoDV: { visible: false } },
      thongTinTienHD: { visible: false },
      thongTinLichSuHenTT: { visible: false},
      thongTinKhoanMucTT: { visible: false},
      thongTinPhieuTT: { visible: true},
      thongTinTienHDKH: { visible: true },
      thongTinLSOB: { visible: true },
      thongTinKhuyenMai: { visible: false},
      thongTinNhomGoi: {visible: false},
      txtMaTB: { enabled: true, value: null },
      txtTenTB: { enabled: true, value: null },
      txtDiaChiTB: { enabled: true, value: null },
      txtDiaChiLD: { enabled: true, value: null },
      txtMaTBCha: { enabled: true, value: null },
      txtMaThanhToan: { enabled: true, value: null },
      txtKieuLD: { enabled: true, value: null },
      txtDichVu: { enabled: true, value: null },
      txtLoaiHinh: { enabled: true, value: null },
      txtDoiTuong: { enabled: true, value: null },
      txtTrungTamVT: { enabled: true, value: null },
      txtNgayThanhToan: { enabled: true, value: null },
      txtNgaySinhNhat: { enabled: true, value: null },
      txtNgayKichHoat: { enabled: true, value: null },
      txtNguoiKichHoat: { enabled: true, value: null },
      txtIPKichHoat: { enabled: true, value: null },
      txtNgayHoanThanh: { enabled: true, value: null },
      txtNgayNghiemThu: { enabled: true, value: null },
      txtChiTieuTG: { enabled: true, value: null },
      txtThoiGianCK: { enabled: true, value: null },
      txtSoThang: { enabled: true, value: null },
      txtNgayBDTD: { enabled: true, value: null },
      txtNgayHHTD: { enabled: true, value: null },
      txtCamKetCLG: { enabled: true, value: null },
      txtLoaiGiayTo: { enabled: true, value: null },
      txtSoGiayTo: { enabled: true, value: null },
      txtChucDanh: { enabled: true, value: null },
      txtNgayHetHanGT: { enabled: true, value: null },
      txtSoAo: { enabled: true, value: null },
      txtMaDoiCap: { enabled: true, value: null },
      txtTocDo: { enabled: true, value: null },
      txtMucDo: { enabled: true, value: null },
      txtThietBi: { enabled: true, value: null },
      txtMaTN: { enabled: true, value: null },
      txtTrangBi: { enabled: true, value: null },
      txtThongTinTC: { enabled: true, value: null },
      txtChuQuan: { enabled: true, value: null },
      txtLoaiTBMyTV: { enabled: true, value: null },
      txtCongNghe: { enabled: true, value: null },
      txtSoAo: { enabled: true, value: null },
      // txtCamKetCLG: {
      //   enabled: true,
      //   value: null
      // },
      txtLoaiNode: { enabled: true, value: null },
      txtIPWan: { enabled: true, value: null },
      txtIPLAN: { enabled: true, value: null },
      txtSiteID: { enabled: true, value: null },
      txtMaHDTTKD: { enabled: true, value: null },
      txtHuongKN: { enabled: true, value: null },
      txtTinhKN: { enabled: true, value: null },
      txtModem: { enabled: true, value: null },
      txtDiaChiKN: { enabled: true, value: null },
      txtLoaiThietBi: { enabled: true, value: null },
      txtLoaiKenh: { enabled: true, value: null },
      // txtChuQuan: {
      //   enabled: true,
      //   value: null
      // },
      // txtTrangBi: {
      //   enabled: true,
      //   value: null
      // },
      txtTocDoCIR: { enabled: true, value: null },
      txtLuongGiu: { enabled: true, value: null },
      txtTocDoPIR: { enabled: true, value: null },
      txtThoiHan: { enabled: true, value: null },
      txtSoLuongIP: { enabled: true, value: null },
      txtTocDoNIX: { enabled: true, value: null },
      txtCuocNIX: { enabled: true, value: null },
      txtCuocIP: { enabled: true, value: null },
      txtTocDoISP: { enabled: true, value: null },
      txtCuocISP: { enabled: true, value: null },
      txtCuocThueCong: { enabled: true, value: null },
      txtCuocThueKenh: { enabled: true, value: null },
      txtCuocThietBi: { enabled: true, value: null },
      txtCuocHT: { enabled: true, value: null },
      txtChiTietDV: { enabled: true, value: null },
      txtIPKhachHang: { enabled: true, value: null },
      txtSLCuocGoi: { enabled: true, value: null },
      txtThongTinWF: { enabled: true, value: null },
      txtViTriWF: { enabled: true, value: null },
      txtWF3M: { enabled: true, value: null },
      txtWF1T: { enabled: true, value: null },
      txtWF1TR: { enabled: true, value: null },
      txtWF2T: { enabled: true, value: null },
      txtWF2TR: { enabled: true, value: null },
      txtKhuVucThueBao: { enabled: true, value: null },
      txtThayDoiCTV: { enabled: true, value: null },
      txtKhuVuc: { enabled: true, value: null },
      txtHenLapDat: { enabled: true, value: null },
      txtVatTuThueBao: { enabled: true, value: null },
      txtTienTrinhThiCong: { enabled: true, value: null },
      txtThongTinBienDong: { enabled: true, value: null },
      txtMaKhachHang: { enabled: true, value: null },
      txtTenKhachHang: { enabled: true, value: null },
      txtDiaChiKhachHang: { enabled: true, value: null },
      txtNguoiDaiDien: { enabled: true, value: null },
      txtSoDienThoai: { enabled: true, value: null },
      txtSoFax: { enabled: true, value: null },
      txtEmail: { enabled: true, value: null },
      txtWebsite: { enabled: true, value: null },
      txtNgaySN: { enabled: true, value: null },
      txtNgayLapHD: { enabled: true, value: null },
      txtSoGiayTO: { enabled: true, value: null },
      txtNgayCap: { enabled: true, value: null },
      txtNoiCap: { enabled: true, value: null },
      txtGhiChu: { enabled: true, value: null },
      txtDonViBan: { enabled: true, value: null },
      // txtLoaiGiayTo: {
      //   enabled: true,
      //   value: null
      // },
      txtNhanVienBan: { enabled: true, value: null },
      txtLoaiHD: { enabled: true, value: null },
      txtLoaiKH: { enabled: true, value: null },
      txtMaSoThue: { enabled: true, value: null },
      txtSoTaiKhoan: { enabled: true, value: null },
      txtNganHang: { enabled: true, value: null },
      txtNgayYC: { enabled: true, value: null },
      txtCongTacVien: { enabled: true, value: null },
      txtKieuHD: { enabled: true, value: null },
      txtPhanLoaiKH: { enabled: true, value: null },
      txtNguoiLapHD: { enabled: true, value: null },
      txtNVGioiThieu: { enabled: true, value: null },
      txtTongTienHD: { enabled: true, value: null },
      txtEmailKhach: { enabled: true, value: null },
      txtKieuYeuCau: { enabled: true, value: null },
      txtTrangThaiEmail: { enabled: true, value: null },
      txtMucDichEmail: { enabled: true, value: null },
      txtMaThanhToan: { enabled: true, value: null },
      txtDiaChiThanhToan: { enabled: true, value: null },
      // txtMaSoThue: {
      //   enabled: true,
      //   value: null
      // },
      txtDonViQL: { enabled: true, value: null },
      // txtNganHang: {
      //   enabled: true,
      //   value: null
      // },
      // txtEmail: {
      //   enabled: true,
      //   value: null
      // },
      txtTuyenThu: { enabled: true, value: null },
      txtTenThanhToan: { enabled: true, value: null },
      txtMaTBDD: { enabled: true, value: null },
      // txtSoTaiKhoan: {
      //   enabled: true,
      //   value: null
      // },
      txtHinhThucThanhToan: { enabled: true, value: null },
      txtSoDienThoai: { enabled: true, value: null },
      txtDiaChiChungTu: { enabled: true, value: null },
      txtGhiChu: { enabled: true, value: null },
      txtDiaChiBaoCuoc: { enabled: true, value: null },
      txtEmailThanhToan: { enabled: true, value: null },
      txtTrangThaiEmail: { enabled: true, value: null },
      txtMucDichEmail: { enabled: true, value: null },
      gridTienHopDong: {
        header: [
          { fieldName: 'ma_kmtt', headerText: 'Mã KMTT', allowfiltering: true },
          { fieldName: 'ten_kmtt', headerText: 'Tên KMTT', allowfiltering: true },
          { fieldName: 'tyle_vat', headerText: 'Tỷ lệ VAT', allowfiltering: true },
          { fieldName: 'tong_tien', headerText: 'Tổng tiền', allowfiltering: true, format: 'N0', type: 'number' },
          { fieldName: 'tien', width: 100, headerText: 'Tiền', allowfiltering: true, format: 'N0', type: 'number' },
          { fieldName: 'vat', width: 100, headerText: 'VAT', allowfiltering: true, format: 'N0', type: 'number' }
        ],
        list: []
      },
      gridLichSuOB: {
        header: [
          { fieldName: 'ketqua_ob', headerText: 'KQ OB', allowfiltering: true },
          { fieldName: 'ten_nv', width: 120, headerText: 'Tên NV', allowfiltering: true },
          { fieldName: 'ten_dv', width: 120, headerText: 'Tên DV', allowfiltering: true },
          { fieldName: 'ghi_chu', headerText: 'Ghi chú', allowfiltering: true },
          { fieldName: 'ngay_hen', width: 100, headerText: 'Ngày hẹn', allowfiltering: true },
          { fieldName: 'ngayhen_den', width: 170, headerText: 'Ngày hẹn đến', allowfiltering: true },
          { fieldName: 'ngay_cn', width: 150, headerText: 'Ngày CN', allowfiltering: true },
        ],
        list: []
      },
      gridThueBaoDonVi: {
        header: [
          { fieldName: 'donvi_id', headerText: 'ID đơn vị', allowfiltering: true },
          { fieldName: 'ten_loaidv', headerText: 'Loại đơn vị', allowfiltering: true },
          { fieldName: 'ten_dv', headerText: 'Tên đơn vị', allowfiltering: true },
          { fieldName: 'ma_dv', headerText: 'Mã ĐV', allowfiltering: true },
          { fieldName: 'kieu_dv', headerText: 'Kiểu ĐV', allowfiltering: true }
        ],
        list: []
      },
      gridPhieuhopdong: {
        header: [
          { fieldName: 'ma_gd', headerText: 'Mã giao dịch', allowfiltering: true },
          { fieldName: 'tong_tien', headerText: 'Tổng tiền', allowfiltering: true, width: 100 },
          { fieldName: 'tien', width: 100, headerText: 'Tiền', allowfiltering: true, format: 'N0', type: 'number' },
          { fieldName: 'vat', width: 100, headerText: 'VAT', allowfiltering: true, format: 'N0', type: 'number' },
          { fieldName: 'ht_tra', headerText: 'Hình thức trả', allowfiltering: true },
          { fieldName: 'kenhthu', headerText: 'Kênh thu', allowfiltering: true },
          // { fieldName: 'ten_kh', headerText: 'Tên khách hàng', allowfiltering: true },
          // { fieldName: 'diachi_kh', headerText: 'Địa chỉ KH', allowfiltering: true },
          { fieldName: 'ngay_tt', headerText: 'Ngày thanh toán', allowfiltering: true },
          { fieldName: 'trangthai', headerText: 'Trạng thái', allowfiltering: true },
          { fieldName: 'tygia', width: 100, headerText: 'Tỷ giá', allowfiltering: true },
          { fieldName: 'sophieu', width: 100, headerText: 'Số phiếu', allowfiltering: true },
          { fieldName: 'so_ct', headerText: 'Số chứng từ', allowfiltering: true },
          { fieldName: 'stk', headerText: 'Số tài khoản', allowfiltering: true },
          { fieldName: 'ngay_hen', headerText: 'Ngày hẹn', allowfiltering: true },
          { fieldName: 'seri', width: 100, headerText: 'Seri', allowfiltering: true },
          { fieldName: 'soseri', width: 100, headerText: 'Số Seri', allowfiltering: true },
          { fieldName: 'ngay_cn', headerText: 'Ngày cập nhật', allowfiltering: true },
          { fieldName: 'nguoi_cn', headerText: 'Người cập nhật', allowfiltering: true },
          { fieldName: 'ghichu', headerText: 'Ghi chú', allowfiltering: true },
          { fieldName: 'ten_loaitien', headerText: 'Loại tiền', allowfiltering: true },
          { fieldName: 'ten_nh', headerText: 'Ngân hàng', allowfiltering: true },
          { fieldName: 'thungan_hd', headerText: 'Thu ngân HD', allowfiltering: true },
          { fieldName: 'thungan_tt', headerText: 'Thu ngân TT', allowfiltering: true },
          { fieldName: 'ten_dv', headerText: 'Đơn vị', allowfiltering: true },
          { fieldName: 'ten_ungdung', headerText: 'Ứng dụng', allowfiltering: true },
          { fieldName: 'madoitac', headerText: 'Mã đối tác', allowfiltering: true }
        ],
        list: []
      },
      gridPhieuthanhtoan: {
        header: [
          { fieldName: 'stt', headerText: 'STT', allowfiltering: true, width: 70 },
          { fieldName: 'tenkhoanmuc', headerText: 'Khoản mục', allowfiltering: true },
          { fieldName: 'tong_tien', headerText: 'Tổng tiền', allowfiltering: true, width: 100 },
          { fieldName: 'tien', headerText: 'Tiền', allowfiltering: true, width: 100, format: 'N0', type: 'number' },
          { fieldName: 'vat', headerText: 'VAT', allowfiltering: true, format: 'N0', type: 'number', width: 100 }
        ],
        list: []
      },
      gridLichSuHenTT: {
        header: [
          { fieldName: 'ngay_hen', headerText: 'Ngày hẹn TT', allowfiltering: true },
          { fieldName: 'nguoi_hen', headerText: 'Người hẹn', allowfiltering: true },
          { fieldName: 'nguoi_duyet', headerText: 'Người duyệt', allowfiltering: true },
          { fieldName: 'ngay_duyet', headerText: 'Ngày duyệt', allowfiltering: true },
          { fieldName: 'kq_duyet', headerText: 'Kết quả duyệt', allowfiltering: true},
          { fieldName: 'ngay_cn', headerText: 'Ngày cập nhật', allowfiltering: true},
          { fieldName: 'may_cn', headerText: 'Máy cập nhật', allowfiltering: true},
          { fieldName: 'nguoi_cn', headerText: 'Người cập nhật', allowfiltering: true },
          { fieldName: 'ghichu', headerText: 'Ghi chú', allowfiltering: true},
        ],
        list: []
      },
      gridKhuyenMai: {
        header: [
          { fieldName: 'loai_km', headerText: "Loại khuyến mại", allowfiltering: true,},
          { fieldName: 'ten_km', headerText: "Tên khuyến mại", allowfiltering: true,},
          { fieldName: 'ten_chitietkm', headerText: "Chi tiết KM", allowfiltering: true,},
          { fieldName: 'ngay_bd', headerText: "Ngày BĐ", allowfiltering: true,},
          { fieldName: 'ngay_kt', headerText: "Ngày KT", allowfiltering: true,},
          { fieldName: 'ngay_bddc', headerText: "Ngày BĐĐC", allowfiltering: true,},
          { fieldName: 'ngay_ktdc', headerText: "Ngày KTĐC", allowfiltering: true,},
          { fieldName: 'ngay_thoai', headerText: "Ngày thoái", allowfiltering: true,},
          { fieldName: 'tyle_sd', headerText: "Tỷ lệ SD", allowfiltering: true,},
          { fieldName: 'tien_sd', headerText: "Tiền SD", allowfiltering: true,},
          { fieldName: 'cuoc_sd', headerText: "Giảm cước SD", allowfiltering: true,},
          { fieldName: 'tien_td', headerText: "Tiền TD", allowfiltering: true,},
          { fieldName: 'tyle_tb', headerText: "Tỷ lệ TB", allowfiltering: true,},
          { fieldName: 'tien_tb', headerText: "Tiền TB", allowfiltering: true,},
          { fieldName: 'cuoc_tb', headerText: "Giảm cước TB", allowfiltering: true,},
          { fieldName: 'tyle_tk', headerText: "Tỷ lệ TK", allowfiltering: true,},
          { fieldName: 'tyle_tc', headerText: "Tỷ lệ TC", allowfiltering: true,},
          { fieldName: 'noidung', headerText: "Nội dung", allowfiltering: true,},
        ],
        list: []
      },
      gridDanhSachNhomGoi: {
        header: [
          { fieldName: 'ten_goi', headerText: "Tên nhóm/gói", allowfiltering: true,},
          { fieldName: 'chunhom', headerText: "Chủ nhóm", allowfiltering: true,},
          { fieldName: 'ma_tb', headerText: "Số máy/Acc", allowfiltering: true,},
          { fieldName: 'loaihinh_tb', headerText: "Loại thuê bao", allowfiltering: true,},
        ],
        list: []
      },
    }
  },
  async mounted() {
    await this.init()
    await this.sp_lay_phieuhd_tt_theo_hdkh()
    console.log('vhdkh_id', this.vhdkh_id)
  },
  methods: {
    async onRowSelected(e) {
      console.log('dataE', e)
      await this.sp_lay_phieu_tt_theo_phieutt(e.data.phieutt_id)
      await this.sp_lay_tt_hentt_theo_phieuttid(e.data.phieutt_id)
    },
    async sp_lay_phieuhd_tt_theo_hdkh() {
      try {
        let kq = await api.sp_lay_phieuhd_tt_theo_hdkh(this.axios, {
          p_hdkh_id: this.vhdkh_id
        })
        console.log('sp_lay_phieuhd_tt_theo_hdkh', kq.data)
        if (kq['data'] && kq['data']['error_code'] == 'BSS-00000000' && kq.data.data.length > 0) {
          this.gridPhieuhopdong.list = kq['data']['data']
        } else {
          this.gridPhieuhopdong.list = []
        }
      } catch (err) {
        this.$toast.warning(err)
      }
    },
    async sp_lay_phieu_tt_theo_phieutt(phieutt_id) {
      this.loading(true)
      try {
        let kq = await api.sp_lay_phieu_tt_theo_phieutt(this.axios, {
          p_phieutt_id: phieutt_id
        })
        if (kq['data'] && kq['data']['error_code'] == 'BSS-00000000' && kq['data']['data'].length > 0) {
          this.gridPhieuthanhtoan.list = kq['data']['data']
        } else {
          this.gridPhieuthanhtoan.list = []
        }
      } catch (e) {
      } finally {
        this.loading(false)
      }
    },
    async sp_lay_tt_hentt_theo_phieuttid(phieutt_id) {
      this.loading(true)
      try {
        let kq = await api.sp_lay_tt_hentt_theo_phieuttid(this.axios, {
          p_phieutt_id: phieutt_id
        })
        if (kq['data'] && kq['data']['error_code'] == 'BSS-00000000' && kq['data']['data'].length > 0) {
          this.gridLichSuHenTT.list = kq['data']['data']
        } else {
          this.gridLichSuHenTT.list = []
        }
      } catch (e) {
      } finally {
        this.loading(false)
      }
    },
    async init() {
      //THONG TIN CHINH
      this.loading(true)
      api
        .sp_lay_tt_thuebao_chinh_by_hdtb_id(this.axios, { hdtb_id: this.vhdtb_id })
        .then((data) => {
          console.log('sp_lay_tt_thuebao_chinh_by_hdtb_id')
          console.log(data)

          if (data.data.data && data.data.data.length > 0) {
            let duLieu = data.data.data[0]
            this.txtMaTB.value = duLieu.ma_tb
            this.txtDiaChiTB.value = duLieu.diachi_tb
            this.txtMaTBCha.value = duLieu.ma_tb_cha
            this.txtKieuLD.value = duLieu.kieu_ld
            this.txtLoaiHinh.value = duLieu.loaihinh
            this.txtTrungTamVT.value = duLieu.ttvt
            this.txtNgaySinhNhat.value = duLieu.ngay_sn
            this.txtNguoiKichHoat.value = duLieu.nguoi_kh
            this.txtNgayKichHoat.value = duLieu.ngay_kh
            this.txtIPKichHoat.value = duLieu.ip_kh
            this.txtNgayHoanThanh.value = duLieu.ngay_ht
            this.txtChiTieuTG.value = duLieu.chitieu_tg
            this.txtTenTB.value = duLieu.ten_tb
            this.txtDiaChiLD.value = duLieu.diachi_ld
            this.txtMaThanhToan.value = duLieu.ma_tt
            this.txtDichVu.value = duLieu.dichvu
            this.txtDoiTuong.value = duLieu.doituong
            this.txtNgayThanhToan.value = duLieu.ngay_tt
            this.txtNgayKichHoat.value = duLieu.ngay_kh
            this.txtIPKhachHang.value = duLieu.ip_kh
            this.txtNgayNghiemThu.value = duLieu.ngay_ins
            this.txtThoiGianCK.value = duLieu.thoigian_ck
            this.txtSoThang.value = duLieu.sothang_ck
            this.txtNgayBDTD.value = duLieu.ngay_bd_td
            this.txtNgayHHTD.value = duLieu.ngay_hh_td
          }
        })
        .catch((err) => {
          this.$toast.error(`${err}`)
        })
        .finally(() => {
          this.loading(false)
        })

      //THONG TIN THEM
      this.loading(true)
      api
        .sp_lay_tt_thuebao_them_by_hdtb_id(this.axios, { hdtb_id: this.vhdtb_id })
        .then((data) => {
          console.log('sp_lay_tt_thuebao_them_by_hdtb_id')
          console.log(data)

          if (data.data.data && data.data.data.length > 0) {
            let duLieu = data.data.data[0]
            this.txtCamKetCLG.value = duLieu.camket_clg
            this.txtLoaiGiayTo.value = duLieu.loai_gt
            this.txtSoGiayTo.value = duLieu.so_gt
            this.txtChucDanh.value = duLieu.chucdanh
            this.txtNgayHetHanGT.value = duLieu.ngayhethan_gt
          }
        })
        .catch((err) => {
          this.$toast.error(`${err}`)
        })
        .finally(() => {
          this.loading(false)
        })

      //THONG TIN KY THUAT
      this.loading(true)
      api
        .sp_lay_tt_thuebao_kythuat_by_hdtb_id(this.axios, { hdtb_id: this.vhdtb_id })
        .then((data) => {
          console.log('sp_lay_tt_thuebao_kythuat_by_hdtb_id')
          console.log(data)

          if (data.data.data && data.data.data.out_adsl && data.data.data.out_adsl.length > 0) {
            let duLieu = data.data.data.out_adsl[0]
            this.txtCongNghe.value = duLieu.congnghe
            this.txtLoaiTBMyTV.value = duLieu.loaimytv
            // this.txtSoGiayTo.value = duLieu.ma_lt
            this.txtMaDoiCap.value = duLieu.madoicap
            // this.txtMaTB.value = duLieu.matb_tn
            // this.txtCuoc.value = muccuoc
            this.txtThietBi.value = duLieu.tbi
            this.txtChuQuan.value = duLieu.tenchuquan
            this.txtThongTinTC.value = duLieu.thongtin_tc
            this.txtTocDo.value = duLieu.tocdo
            this.txtTrangBi.value = duLieu.trangbi
          } else if (data.data.data && data.data.data.out_mgwan && data.data.data.out_mgwan.length > 0) {
            //wtf
            let duLieu = data.data.data.out_mgwan[0]
            this.txtCuocHT.value = duLieu.cuoc_ht
            this.txtCuocIP.value = duLieu.cuoc_ip
            this.txtCuocISP.value = duLieu.cuoc_isp
            this.txtCuocNIX.value = duLieu.cuoc_nix
            this.txtCuocThietBi.value = duLieu.cuoc_tbi
            this.txtCuocThueCong.value = duLieu.cuoc_tc
            this.txtCuocThueKenh.value = duLieu.cuoc_tk
            this.txtDiaChiKN.value = duLieu.diachi_kn
            this.txtHuongKN.value = duLieu.huong_kn
            this.txtIPLAN.value = duLieu.lan_ip
            this.txtLoaiKenh.value = duLieu.loai_kenh
            this.txtLoaiNode.value = duLieu.loai_node
            this.txtLoaiThietBi.value = duLieu.loai_thietbi
            this.txtLuongGiu.value = duLieu.luong_giu
            this.txtSoGiayTo.value = duLieu.ma_lt
            this.txtModem.value = duLieu.modem
            this.txtSiteID.value = duLieu.site_id
            this.txtThoiHan.value = duLieu.thoi_han
            this.txtTinhKN.value = duLieu.tinh_kn
            this.txtTocDoCIR.value = duLieu.tocdo_cir
            this.txtTocDoISP.value = duLieu.tocdo_isp
            this.txtTocDoNIX.value = duLieu.tocdo_nix
            this.txtTocDoPIR.value = duLieu.tocdo_pir
            this.txtTrangBi.value = duLieu.trang_bi
            this.txtIPWan.value = duLieu.wan_ip
          }
        })
        .catch((err) => {
          this.$toast.error(`${err}`)
        })
        .finally(() => {
          this.loading(false)
        })

      //THONG TIN KY KHAC
      this.loading(true)
      api
        .sp_lay_tt_thuebao_khac_by_hdtb_id(this.axios, { hdtb_id: this.vhdtb_id })
        .then((data) => {
          console.log('sp_lay_tt_thuebao_khac_by_hdtb_id')
          console.log(data)

          if (data.data.data) {
            let duLieu = data.data.data
            this.txtHenLapDat.value = duLieu.out_hen
            this.txtKhuVucThueBao.value = duLieu.out_khuvuc_tb
            this.txtThayDoiCTV.value = duLieu.out_thaydoi
            this.txtKhuVuc.value = duLieu.out_khuvuc
            this.txtTienTrinhThiCong.value = duLieu.out_thicong
            this.txtVatTuThueBao.value = duLieu.out_vattu
          }
        })
        .catch((err) => {
          this.$toast.error(`${err}`)
        })
        .finally(() => {
          this.loading(false)
        })

      //THONG TIN KY KHAC - THUE BAO DON VI
      this.loading(true)
      api
        .sp_lay_ds_thuebao_donvi_by_hdtb_id(this.axios, { hdtb_id: this.vhdtb_id })
        .then((data) => {
          console.log('sp_lay_ds_thuebao_donvi_by_hdtb_id')
          console.log(data)

          if (data.data.data) {
            let duLieu = data.data.data
            this.gridThueBaoDonVi.list = duLieu
          }
        })
        .catch((err) => {
          this.$toast.error(`${err}`)
        })
        .finally(() => {
          this.loading(false)
        })

      //THONG TIN TIEN HOP DONG
      this.loading(true)
      api
        .sp_lay_tien_hd_theo_hdkh_id(this.axios, { p_hdkh_id: this.vhdkh_id })
        .then((data) => {
          console.log('sp_lay_tien_hd_theo_hdkh_id')
          console.log(data)
          if (data.data.data) {
            let duLieu = data.data.data
            if(duLieu.length > 0){
              console.log(duLieu)
              this.gridTienHopDong.list = duLieu.map((e) => {
                e.tien = parseInt(e.tien)
                e.tyle_vat = parseInt(e.tyle_vat)
                e.vat = parseInt(e.vat)
                e.tong_tien = parseInt(e.tong_tien)
  
                return e
              })
  
              console.log(this.gridTienHopDong.list)
              this.thongTinTienHDKH.visible = true
            }
            else{
              this.thongTinTienHDKH.visible = false
            }
          }
        })
        .catch((err) => {
          this.$toast.error(`${err}`)
        })
        .finally(() => {
          this.loading(false)
        })
      // LẤY LỊCH SỬ OB
      this.loading(true)
      api
        .lay_ls_ob_ghtt_theo_hdkh(this.axios, { vhdkh_id: this.vhdkh_id })
        .then((data) => {
          console.log('lay_ls_ob_ghtt_theo_hdkh')
          console.log(data)
          if (data.data.data) {
            let duLieu = data.data.data
            if(duLieu.length > 0){
              this.gridLichSuOB.list = duLieu
  
              console.log(this.gridLichSuOB.list)
              this.thongTinLSOB.visible = true
            }
            else{
              this.thongTinLSOB.visible = false
            }
          }
        })
        .catch((err) => {
          this.$toast.error(`${err}`)
        })
        .finally(() => {
          this.loading(false)
        })
      //THONG TIN KHACH HANG HOP DONG
      this.loading(true)
      api
        .sp_lay_tt_khachhang_by_hdtb_id(this.axios, { hdtb_id: this.vhdtb_id })
        .then((data) => {
          console.log('sp_lay_tt_khachhang_by_hdtb_id')
          console.log(data)
          if (data.data.data && data.data.data.out_kh && data.data.data.out_kh.length > 0) {
            let duLieu = data.data.data.out_kh[0]
            this.txtKieuHD.value = duLieu.kieu_hd
            this.txtMaKhachHang.value = duLieu.ma_kh
            this.txtTenKhachHang.value = duLieu.ten_kh
            this.txtDiaChiKhachHang.value = duLieu.diachi_kh
            this.txtNguoiDaiDien.value = duLieu.nguoi_dd
            this.txtSoDienThoai.value = duLieu.so_dt
            this.txtSoFax.value = duLieu.so_fax
            this.txtEmail.value = duLieu.email
            this.txtWebsite.value = duLieu.website
            this.txtNgaySN.value = duLieu.ngay_sn
            this.txtNgayLapHD.value = duLieu.ngaylap_hd
            this.txtSoGiayTo.value = duLieu.so_gt
            this.txtNgayCap.value = duLieu.ngaycap
            this.txtNoiCap.value = duLieu.noicap
            this.txtGhiChu.value = duLieu.ghichu
            this.txtDonViBan.value = duLieu.donvi_ban
            this.txtLoaiGiayTo.value = duLieu.loai_gt
            this.txtNhanVienBan.value = duLieu.nvban
            this.txtLoaiHD.value = duLieu.loaihd
            this.txtLoaiKH.value = duLieu.loaikh
            this.txtMaSoThue.value = duLieu.mst
            this.txtSoTaiKhoan.value = duLieu.stk
            this.txtNganHang.value = duLieu.nganhang
            this.txtNgayYC.value = duLieu.ngay_yc
            this.txtCongTacVien.value = duLieu.congtacvien
            this.txtNVGioiThieu.value = duLieu.nv_gioithieu
            this.txtTongTienHD.value = ''
            this.txtEmailKhach.value = ''
            this.txtKieuYeuCau.value = ''
            this.txtTrangThaiEmail.value = ''
            this.txtMucDichEmail.value = ''
          }
        })
        .catch((err) => {
          this.$toast.error(`${err}`)
        })
        .finally(() => {
          this.loading(false)
        })

      //THONG TIN THANH TOAN
      this.loading(true)
      api
        .sp_lay_tt_thanhtoan_by_hdtb_id(this.axios, { hdtb_id: this.vhdtb_id })
        .then((data) => {
          console.log('sp_lay_tt_thanhtoan_by_hdtb_id')
          console.log(data)
          if (data.data.data && data.data.data.out_tt && data.data.data.out_tt.length > 0) {
            let duLieu = data.data.data.out_tt[0]
            this.txtMaThanhToan.value = duLieu.ma_tt
            this.txtTenThanhToan.value = duLieu.ten_tt
            this.txtDiaChiThanhToan.value = duLieu.diachi_tt
            this.txtMaTBDD.value = duLieu.matb_dd
            this.txtMaSoThue.value = duLieu.mst
            this.txtSoTaiKhoan.value = duLieu.stk
            this.txtDonViQL.value = duLieu.donvi_ql
            this.txtHinhThucThanhToan.value = duLieu.hinhthuc_tt
            this.txtNganHang.value = duLieu.nganhang
            this.txtSoDienThoai.value = duLieu.so_dt
            this.txtEmail.value = duLieu.email
            this.txtDiaChiChungTu.value = duLieu.diachi_ct
            this.txtTuyenThu.value = duLieu.tuyenthu
            this.txtGhiChu.value = duLieu.ghichu
            this.txtDiaChiBaoCuoc.value = duLieu.diachi_bc
            this.txtEmailThanhToan.value = ''
            this.txtKieuYeuCau.value = ''
            this.txtTrangThaiEmail.value = ''
            this.txtMucDichEmail.value = ''
          }
        })
        .catch((err) => {
          this.$toast.error(`${err}`)
        })
        .finally(() => {
          this.loading(false)
        })

      //KHUYEN MAI
      this.loading(true)
      api
        .sp_lay_tt_km_tratruoc(this.axios, { p_hdtb_id: this.vhdtb_id })
        .then((data) => {
          console.log('sp_lay_tt_km_tratruoc')
          console.log(data)
          if (data.data && data.data.data && data.data.data.length > 0) {
            this.gridKhuyenMai.list = data.data.data
          }
        })
        .catch((err) => {
          this.$toast.error(`${err}`)
        })
        .finally(() => {
          this.loading(false)
        })
      
      //DANH SACH NHOM/GOI
      this.loading(true)
      api
        .sp_lay_tt_goi_dadv_hd(this.axios, { p_hdtb_id: this.vhdtb_id })
        .then((data) => {
          console.log('sp_lay_tt_goi_dadv_hd')
          console.log(data)
          if(data.data && data.data.data && data.data.data.length > 0){
            this.gridDanhSachNhomGoi.list = data.data.data
          }
        })
        .catch((err) => {
          this.$toast.error(`${err}`)
        })
        .finally(() => {
          this.loading(false)
        })
    },
    dsThanhToan_QueryCellInfo(args) {
      let header = args.column.headerText
      if (header === 'Tổng tiền') {
        args.data.tong_tien = args.data.tong_tien.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ',')
        args.cell.style.fontWeight = 'bold'
        args.cell.style.textAlign = 'right'
      }
      if (header === 'Tiền') {
        args.cell.style.color = 'red'
        args.cell.style.textAlign = 'right'
      }
    },
    gridTienHopDongQueryCellInfo(args) {
      let field = args.column.field
      if (field == 'tong_tien') {
        // args.data.tong_tien = args.data.tong_tien.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ',')
        args.cell.style.fontWeight = 'bold'
        args.cell.style.textAlign = 'right'
      }
      if (field == 'tien') {
        args.cell.style.color = 'red'
        args.cell.style.textAlign = 'right'
      }
    }
  }
}
</script>
<style scoped>
</style>
