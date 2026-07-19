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
        </div>
        <div class="tab-content">
          <div id="hopdongthuebao" class="tab-pane  in active">
            <div class="box-form">
              <!-- <div class="legend-title"><span class="icon fa fa-angle-up"></span> Thông tin chính</div> -->
              <div class="legend-title">
                <span @click="ttc = !ttc" class="" :class="{"icon fa fa-angle-up": ttc, "icon fa fa-angle-down": !ttc }"></span> Thông tin chính
                <!-- <span v-b-toggle.collapse-ttc class="icon fa fa-angle-down"></span>Thông tin chính -->
              </div>
              <div v-show="ttc">
                <div class="row">
                  <div class="col-sm-6 col-12 info-row">
                    <div class="key w140">
                      Mã thuê bao
                    </div>
                    <div class="value">
                      <input type="text" class="form-control" v-model="a" />
                    </div>
                  </div>
                  <div class="col-sm-6 col-12 info-row">
                    <div class="key w140">
                      Tên thuê bao
                    </div>
                    <div class="value">
                      <input type="text" class="form-control" v-model="a" />
                    </div>
                  </div>
                  <div class="col-sm-6 col-12 info-row">
                    <div class="key w140">
                      Địa chỉ thuê bao
                    </div>
                    <div class="value">
                      <input type="text" class="form-control" v-model="a" />
                    </div>
                  </div>
                  <div class="col-sm-6 col-12 info-row">
                    <div class="key w140">
                      Địa chỉ lắp đặt
                    </div>
                    <div class="value">
                      <input type="text" class="form-control" v-model="a" />
                    </div>
                  </div>
                  <div class="col-sm-6 col-12 info-row">
                    <div class="key w140">
                      Mã tb cha
                    </div>
                    <div class="value">
                      <input type="text" class="form-control" v-model="a" />
                    </div>
                  </div>
                  <div class="col-sm-6 col-12 info-row">
                    <div class="key w140">
                      Mã thanh toán
                    </div>
                    <div class="value">
                      <input type="text" class="form-control" v-model="a" />
                    </div>
                  </div>
                  <div class="col-sm-6 col-12 info-row">
                    <div class="key w140">
                      Kiểu lắp đặt
                    </div>
                    <div class="value">
                      <input type="text" class="form-control" v-model="a" />
                    </div>
                  </div>
                  <div class="col-sm-6 col-12 info-row">
                    <div class="key w140">
                      Dịch vụ
                    </div>
                    <div class="value">
                      <input type="text" class="form-control" v-model="a" />
                    </div>
                  </div>
                  <div class="col-sm-6 col-12 info-row">
                    <div class="key w140">
                      Loại hình
                    </div>
                    <div class="value">
                      <input type="text" class="form-control" v-model="a" />
                    </div>
                  </div>
                  <div class="col-sm-6 col-12 info-row">
                    <div class="key w140">
                      Đối tượng
                    </div>
                    <div class="value">
                      <input type="text" class="form-control" v-model="a" />
                    </div>
                  </div>
                  <div class="col-sm-6 col-12 info-row">
                    <div class="key w140">
                      Trung tâm viễn thông
                    </div>
                    <div class="value">
                      <input type="text" class="form-control" v-model="a" />
                    </div>
                  </div>
                  <div class="col-sm-6 col-12 info-row">
                    <div class="key w140">
                      Ngày thanh toán
                    </div>
                    <div class="value">
                      <input type="text" class="form-control" v-model="a" />
                    </div>
                  </div>
                  <div class="col-sm-6 col-12 info-row">
                    <div class="key w140">
                      Ngày sinh nhật
                    </div>
                    <div class="value">
                      <input type="text" class="form-control" v-model="a" />
                    </div>
                  </div>
                  <div class="col-sm-6 col-12 info-row">
                    <div class="key w140">
                      Ngày kích hoạt
                    </div>
                    <div class="value">
                      <input type="text" class="form-control" v-model="a" />
                    </div>
                  </div>
                  <div class="col-sm-6 col-12 info-row">
                    <div class="key w140">
                      Người kích hoạt
                    </div>
                    <div class="value">
                      <input type="text" class="form-control" v-model="a" />
                    </div>
                  </div>
                  <div class="col-sm-6 col-12 info-row">
                    <div class="key w140">
                      IP kích hoạt
                    </div>
                    <div class="value">
                      <input type="text" class="form-control" v-model="a" />
                    </div>
                  </div>
                  <div class="col-sm-6 col-12 info-row">
                    <div class="key w140">
                      Ngày hoàn thành
                    </div>
                    <div class="value">
                      <input type="text" class="form-control" v-model="a" />
                    </div>
                  </div>
                  <div class="col-sm-6 col-12 info-row">
                    <div class="key w140">
                      Ngày nghiệm thu
                    </div>
                    <div class="value">
                      <input type="text" class="form-control" v-model="a" />
                    </div>
                  </div>
                  <div class="col-sm-6 col-12 info-row">
                    <div class="key w140">
                      Chi tiêu TG
                    </div>
                    <div class="value">
                      <input type="text" class="form-control" v-model="a" />
                    </div>
                  </div>
                  <div class="col-sm-6 col-12 info-row">
                    <div class="key w140">
                      Thời gian CK
                    </div>
                    <div class="value">
                      <input type="text" class="form-control" v-model="a" />
                    </div>
                  </div>
                  <div class="col-sm-12 col-12 info-row">
                    <div class="key w140">
                      Số tháng
                    </div>
                    <div class="value">
                      <input type="text" class="form-control" v-model="a" />
                    </div>
                  </div>
                </div>
              </div>
            </div>
            <div class="box-form">
              <div class="legend-title">
                <span v-b-toggle.collapse-ttt class="icon fa fa-angle-up"></span>
                <span v-b-toggle.collapse-ttt class="icon fa fa-angle-down"></span>Thông tin thêm
              </div>
              <b-collapse visible id="collapse-ttt">
                <div class="row">
                  <div class="col-sm-6 col-12 info-row">
                    <div class="key w140">
                      Cam kết CLG
                    </div>
                    <div class="value">
                      <input type="text" class="form-control" v-model="a" />
                    </div>
                  </div>
                  <div class="col-sm-6 col-12 info-row">
                    <div class="key w140">
                      Loại giấy tờ
                    </div>
                    <div class="value">
                      <input type="text" class="form-control" v-model="a" />
                    </div>
                  </div>
                  <div class="col-sm-6 col-12 info-row">
                    <div class="key w140">
                      Số giấy tờ
                    </div>
                    <div class="value">
                      <input type="text" class="form-control" v-model="a" />
                    </div>
                  </div>
                  <div class="col-sm-6 col-12 info-row">
                    <div class="key w140">
                      Chức danh
                    </div>
                    <div class="value">
                      <input type="text" class="form-control" v-model="a" />
                    </div>
                  </div>
                  <div class="col-sm-12 col-12 info-row">
                    <div class="key w140">
                      Ngày hết hạn GT
                    </div>
                    <div class="value">
                      <input type="text" class="form-control" v-model="a" />
                    </div>
                  </div>
                </div>
              </b-collapse>
            </div>
            <div class="box-form">
              <div class="legend-title">
                <span v-b-toggle.collapse-ttkt class="icon fa fa-angle-up"></span>
                <span v-b-toggle.collapse-ttkt class="icon fa fa-angle-down"></span>Thông tin kỹ thuật
              </div>
              <b-collapse visible id="collapse-ttkt">
                <div class="row">
                  <div class="col-sm-6 col-12 info-row">
                    <div class="key w140">
                      Số ảo
                    </div>
                    <div class="value">
                      <input type="text" class="form-control" v-model="a" />
                    </div>
                  </div>
                  <div class="col-sm-6 col-12 info-row">
                    <div class="key w140">
                      Mã đôi cáp
                    </div>
                    <div class="value">
                      <input type="text" class="form-control" v-model="a" />
                    </div>
                  </div>
                  <div class="col-sm-6 col-12 info-row">
                    <div class="key w140">
                      Tốc độ
                    </div>
                    <div class="value">
                      <input type="text" class="form-control" v-model="a" />
                    </div>
                  </div>
                  <div class="col-sm-6 col-12 info-row">
                    <div class="key w140">
                      Mức cước
                    </div>
                    <div class="value">
                      <input type="text" class="form-control" v-model="a" />
                    </div>
                  </div>
                  <div class="col-sm-6 col-12 info-row">
                    <div class="key w140">
                      Thiết bị
                    </div>
                    <div class="value">
                      <input type="text" class="form-control" v-model="a" />
                    </div>
                  </div>
                  <div class="col-sm-6 col-12 info-row">
                    <div class="key w140">
                      Mã TN
                    </div>
                    <div class="value">
                      <input type="text" class="form-control" v-model="a" />
                    </div>
                  </div>
                  <div class="col-sm-6 col-12 info-row">
                    <div class="key w140">
                      Trang bị
                    </div>
                    <div class="value">
                      <input type="text" class="form-control" v-model="a" />
                    </div>
                  </div>
                  <div class="col-sm-6 col-12 info-row">
                    <div class="key w140">
                      Thông tin TC
                    </div>
                    <div class="value">
                      <input type="text" class="form-control" v-model="a" />
                    </div>
                  </div>
                  <div class="col-sm-6 col-12 info-row">
                    <div class="key w140">
                      Chủ quản
                    </div>
                    <div class="value">
                      <input type="text" class="form-control" v-model="a" />
                    </div>
                  </div>
                  <div class="col-sm-6 col-12 info-row">
                    <div class="key w140">
                      Loại TB MyTV
                    </div>
                    <div class="value">
                      <input type="text" class="form-control" v-model="a" />
                    </div>
                  </div>
                  <div class="col-sm-12 col-12 info-row">
                    <div class="key w140">
                      Công nghệ
                    </div>
                    <div class="value">
                      <input type="text" class="form-control" v-model="a" />
                    </div>
                  </div>
                  <div class="col-sm-6 col-12 info-row">
                    <div class="key w140">
                      Số ảo
                    </div>
                    <div class="value">
                      <input type="text" class="form-control" v-model="a" />
                    </div>
                  </div>
                  <div class="col-sm-6 col-12 info-row">
                    <div class="key w140">
                      Mã TN
                    </div>
                    <div class="value">
                      <input type="text" class="form-control" v-model="a" />
                    </div>
                  </div>
                  <div class="col-sm-6 col-12 info-row">
                    <div class="key w140">
                      Loại Node
                    </div>
                    <div class="value">
                      <input type="text" class="form-control" v-model="a" />
                    </div>
                  </div>
                  <div class="col-sm-6 col-12 info-row">
                    <div class="key w140">
                      IP WAN
                    </div>
                    <div class="value">
                      <input type="text" class="form-control" v-model="a" />
                    </div>
                  </div>
                  <div class="col-sm-6 col-12 info-row">
                    <div class="key w140">
                      IP LAN
                    </div>
                    <div class="value">
                      <input type="text" class="form-control" v-model="a" />
                    </div>
                  </div>
                  <div class="col-sm-6 col-12 info-row">
                    <div class="key w140">
                      Site ID
                    </div>
                    <div class="value">
                      <input type="text" class="form-control" v-model="a" />
                    </div>
                  </div>
                  <div class="col-sm-6 col-12 info-row">
                    <div class="key w140">
                      Mã HĐ - TTKD
                    </div>
                    <div class="value">
                      <input type="text" class="form-control" v-model="a" />
                    </div>
                  </div>
                  <div class="col-sm-6 col-12 info-row">
                    <div class="key w140">
                      Hướng KN
                    </div>
                    <div class="value">
                      <input type="text" class="form-control" v-model="a" />
                    </div>
                  </div>
                  <div class="col-sm-6 col-12 info-row">
                    <div class="key w140">
                      Tỉnh KN
                    </div>
                    <div class="value">
                      <input type="text" class="form-control" v-model="a" />
                    </div>
                  </div>
                  <div class="col-sm-6 col-12 info-row">
                    <div class="key w140">
                      Modem
                    </div>
                    <div class="value">
                      <input type="text" class="form-control" v-model="a" />
                    </div>
                  </div>
                  <div class="col-sm-6 col-12 info-row">
                    <div class="key w140">
                      Địa chỉ KN
                    </div>
                    <div class="value">
                      <input type="text" class="form-control" v-model="a" />
                    </div>
                  </div>
                  <div class="col-sm-6 col-12 info-row">
                    <div class="key w140">
                      Loại thiết bị
                    </div>
                    <div class="value">
                      <input type="text" class="form-control" v-model="a" />
                    </div>
                  </div>
                  <div class="col-sm-6 col-12 info-row">
                    <div class="key w140">
                      Loại kênh
                    </div>
                    <div class="value">
                      <input type="text" class="form-control" v-model="a" />
                    </div>
                  </div>
                  <div class="col-sm-6 col-12 info-row">
                    <div class="key w140">
                      Chủ quản
                    </div>
                    <div class="value">
                      <input type="text" class="form-control" v-model="a" />
                    </div>
                  </div>
                  <div class="col-sm-6 col-12 info-row">
                    <div class="key w140">
                      Trang bị
                    </div>
                    <div class="value">
                      <input type="text" class="form-control" v-model="a" />
                    </div>
                  </div>
                  <div class="col-sm-6 col-12 info-row">
                    <div class="key w140">
                      Tốc độ CIR
                    </div>
                    <div class="value">
                      <input type="text" class="form-control" v-model="a" />
                    </div>
                  </div>
                  <div class="col-sm-6 col-12 info-row">
                    <div class="key w140">
                      Luồng giữ
                    </div>
                    <div class="value">
                      <input type="text" class="form-control" v-model="a" />
                    </div>
                  </div>
                  <div class="col-sm-6 col-12 info-row">
                    <div class="key w140">
                      Tốc độ PIR
                    </div>
                    <div class="value">
                      <input type="text" class="form-control" v-model="a" />
                    </div>
                  </div>
                  <div class="col-sm-6 col-12 info-row">
                    <div class="key w140">
                      Thời hạn
                    </div>
                    <div class="value">
                      <input type="text" class="form-control" v-model="a" />
                    </div>
                  </div>
                  <div class="col-sm-6 col-12 info-row">
                    <div class="key w140">
                      Số lượng IP
                    </div>
                    <div class="value">
                      <input type="text" class="form-control" v-model="a" />
                    </div>
                  </div>
                  <div class="col-sm-6 col-12 info-row">
                    <div class="key w140">
                      Tốc độ NIX
                    </div>
                    <div class="value">
                      <input type="text" class="form-control" v-model="a" />
                    </div>
                  </div>
                  <div class="col-sm-6 col-12 info-row">
                    <div class="key w140">
                      Cước NIX
                    </div>
                    <div class="value">
                      <input type="text" class="form-control" v-model="a" />
                    </div>
                  </div>
                  <div class="col-sm-6 col-12 info-row">
                    <div class="key w140">
                      Cước IP
                    </div>
                    <div class="value">
                      <input type="text" class="form-control" v-model="a" />
                    </div>
                  </div>
                  <div class="col-sm-6 col-12 info-row">
                    <div class="key w140">
                      Tốc độ ISP
                    </div>
                    <div class="value">
                      <input type="text" class="form-control" v-model="a" />
                    </div>
                  </div>
                  <div class="col-sm-6 col-12 info-row">
                    <div class="key w140">
                      Cước ISP
                    </div>
                    <div class="value">
                      <input type="text" class="form-control" v-model="a" />
                    </div>
                  </div>
                  <div class="col-sm-6 col-12 info-row">
                    <div class="key w140">
                      Cước thuê cổng(no VAT)
                    </div>
                    <div class="value">
                      <input type="text" class="form-control" v-model="a" />
                    </div>
                  </div>
                  <div class="col-sm-6 col-12 info-row">
                    <div class="key w140">
                      Cước thuể kênh(no VAT)
                    </div>
                    <div class="value">
                      <input type="text" class="form-control" v-model="a" />
                    </div>
                  </div>
                  <div class="col-sm-6 col-12 info-row">
                    <div class="key w140">
                      Cước thiết bị(no VAT)
                    </div>
                    <div class="value">
                      <input type="text" class="form-control" v-model="a" />
                    </div>
                  </div>
                  <div class="col-sm-6 col-12 info-row">
                    <div class="key w140">
                      Cước HT(no VAT)
                    </div>
                    <div class="value">
                      <input type="text" class="form-control" v-model="a" />
                    </div>
                  </div>
                  <div class="col-sm-6 col-12 info-row">
                    <div class="key w140">
                      Chi tiết DV
                    </div>
                    <div class="value">
                      <input type="text" class="form-control" v-model="a" />
                    </div>
                  </div>
                  <div class="col-sm-6 col-12 info-row">
                    <div class="key w140">
                      IP khách hàng
                    </div>
                    <div class="value">
                      <input type="text" class="form-control" v-model="a" />
                    </div>
                  </div>
                  <div class="col-sm-12 col-12 info-row">
                    <div class="key w140">
                      SL cuộc gọi
                    </div>
                    <div class="value">
                      <input type="text" class="form-control" v-model="a" />
                    </div>
                  </div>
                  <div class="col-sm-6 col-12 info-row">
                    <div class="key w140">
                      Thông tin WF
                    </div>
                    <div class="value">
                      <input type="text" class="form-control" v-model="a" />
                    </div>
                  </div>
                  <div class="col-sm-6 col-12 info-row">
                    <div class="key w140">
                      Vị trí WF
                    </div>
                    <div class="value">
                      <input type="text" class="form-control" v-model="a" />
                    </div>
                  </div>
                  <div class="col-sm-6 col-12 info-row">
                    <div class="key w140">
                      WF 3M
                    </div>
                    <div class="value">
                      <input type="text" class="form-control" v-model="a" />
                    </div>
                  </div>
                  <div class="col-sm-6 col-12 info-row">
                    <div class="key w140">
                      WF 1T
                    </div>
                    <div class="value">
                      <input type="text" class="form-control" v-model="a" />
                    </div>
                  </div>
                  <div class="col-sm-6 col-12 info-row">
                    <div class="key w140">
                      WF 1TR
                    </div>
                    <div class="value">
                      <input type="text" class="form-control" v-model="a" />
                    </div>
                  </div>
                  <div class="col-sm-6 col-12 info-row">
                    <div class="key w140">
                      WF2T
                    </div>
                    <div class="value">
                      <input type="text" class="form-control" v-model="a" />
                    </div>
                  </div>
                  <div class="col-sm-12 col-12 info-row">
                    <div class="key w140">
                      WF 2TR
                    </div>
                    <div class="value">
                      <input type="text" class="form-control" v-model="a" />
                    </div>
                  </div>
                </div>
              </b-collapse>
            </div>
            <div class="box-form">
              <div class="legend-title">
                <span v-b-toggle.collapse-ttk class="icon fa fa-angle-up"></span>
                <span v-b-toggle.collapse-ttk class="icon fa fa-angle-down"></span>Thông tin khác
              </div>
              <b-collapse visible id="collapse-ttk">
                <div class="row">
                  <div class="col-sm-12 col-12 info-row">
                    <div class="key w140">
                      Khu vực thuê bao
                    </div>
                    <div class="value">
                      <input type="text" class="form-control" v-model="a" />
                    </div>
                  </div>
                  <div class="col-sm-12 col-12 info-row">
                    <div class="key w140">
                      Thay đổi CTV
                    </div>
                    <div class="value">
                      <input type="text" class="form-control" v-model="a" />
                    </div>
                  </div>
                  <div class="col-sm-12 col-12 info-row">
                    <div class="key w140">
                      Khu vực
                    </div>
                    <div class="value">
                      <input type="text" class="form-control" v-model="a" />
                    </div>
                  </div>
                  <div>
                    <div class="legend-title">
                      <span v-b-toggle.collapse-dstbdv class="icon fa fa-angle-up"></span>
                      <span v-b-toggle.collapse-dstbdv class="icon fa fa-angle-down"></span>Danh sách thuê bảo đơn vị
                    </div>
                    <b-collapse visible id="collapse-dstbdv">
                      <div class="row">
                        <div class="col-sm-12 col-12 info-row">
                          <div class="key w140">
                            Hẹn lắp đặt
                          </div>
                          <div class="value">
                            <input type="text" class="form-control" v-model="a" />
                          </div>
                        </div>
                        <div class="col-sm-12 col-12 info-row">
                          <div class="key w140">
                            Vật tư thuê bao
                          </div>
                          <div class="value">
                            <input type="text" class="form-control" v-model="a" />
                          </div>
                        </div>
                        <div class="col-sm-12 col-12 info-row">
                          <div class="key w140">
                            Tiến trình thi công
                          </div>
                          <div class="value">
                            <input type="text" class="form-control" v-model="a" />
                          </div>
                        </div>
                        <div class="col-sm-12 col-12 info-row">
                          <div class="key w140">
                            Thông tin biến động
                          </div>
                          <div class="value">
                            <input type="text" class="form-control" v-model="a" />
                          </div>
                        </div>
                      </div>
                    </b-collapse>
                  </div>
                </div>
              </b-collapse>
            </div>
            <div class="box-form">
              <div class="legend-title">
                <span v-b-toggle.collapse-dstb class="icon fa fa-angle-up"></span>
                <span v-b-toggle.collapse-dstb class="icon fa fa-angle-down"></span>Thông tin tiền hợp đồng
              </div>
              <b-collapse visible id="collapse-dstb">
                <div class="title-bg-main">
                  <div class="title"><span class="fa fa-angle-up text-main marr5"></span> Tiền hợp đồng và tiền thanh toán</div>
                </div>
                <DataGrid v-bind:columns="a" v-bind:dataSource="a" :enable-paging-server="false" :allowPaging="true" :showFilter="true"> </DataGrid>
              </b-collapse>
            </div>
          </div>
          <div id="hopdongkhachhang" class="tab-pane fade">
            <div class="box-form">
              <div class="row">
                <div class="col-sm-12 col-12 info-row">
                <div class="key w140">
                  Kiểu hợp đồng
                </div>
                <div class="value">
                  <input type="text" class="form-control" v-model="a" />
                </div>
              </div>
                <div class="col-sm-6 col-12 info-row">
                  <div class="key w140">
                    Mã khách hàng
                  </div>
                  <div class="value">
                    <input type="text" class="form-control" v-model="a" />
                  </div>
                </div>
                <div class="col-sm-6 col-12 info-row">
                  <div class="key w140">
                    Tên khách hàng
                  </div>
                  <div class="value">
                    <input type="text" class="form-control" v-model="a" />
                  </div>
                </div>
                <div class="col-sm-6 col-12 info-row">
                  <div class="key w140">
                    Địa chỉ khách hàng
                  </div>
                  <div class="value">
                    <input type="text" class="form-control" v-model="a" />
                  </div>
                </div>
                <div class="col-sm-6 col-12 info-row">
                  <div class="key w140">
                    Người đại diện
                  </div>
                  <div class="value">
                    <input type="text" class="form-control" v-model="a" />
                  </div>
                </div>
                <div class="col-sm-6 col-12 info-row">
                  <div class="key w140">
                    Số điện thoại
                  </div>
                  <div class="value">
                    <input type="text" class="form-control" v-model="a" />
                  </div>
                </div>
                <div class="col-sm-6 col-12 info-row">
                  <div class="key w140">
                    Số fax
                  </div>
                  <div class="value">
                    <input type="text" class="form-control" v-model="a" />
                  </div>
                </div>
                <div class="col-sm-6 col-12 info-row">
                  <div class="key w140">
                    Email
                  </div>
                  <div class="value">
                    <input type="text" class="form-control" v-model="a" />
                  </div>
                </div>
                <div class="col-sm-6 col-12 info-row">
                  <div class="key w140">
                    Website
                  </div>
                  <div class="value">
                    <input type="text" class="form-control" v-model="a" />
                  </div>
                </div>
                <div class="col-sm-6 col-12 info-row">
                  <div class="key w140">
                    Ngày SN
                  </div>
                  <div class="value">
                    <input type="text" class="form-control" v-model="a" />
                  </div>
                </div>
                <div class="col-sm-6 col-12 info-row">
                  <div class="key w140">
                    Ngày lập HĐ
                  </div>
                  <div class="value">
                    <input type="text" class="form-control" v-model="a" />
                  </div>
                </div>
                <div class="col-sm-6 col-12 info-row">
                  <div class="key w140">
                    Số giấy tờ
                  </div>
                  <div class="value">
                    <input type="text" class="form-control" v-model="a" />
                  </div>
                </div>
                <div class="col-sm-6 col-12 info-row">
                  <div class="key w140">
                    Ngày cấp
                  </div>
                  <div class="value">
                    <input type="text" class="form-control" v-model="a" />
                  </div>
                </div>
                <div class="col-sm-6 col-12 info-row">
                  <div class="key w140">
                    Nơi cấp
                  </div>
                  <div class="value">
                    <input type="text" class="form-control" v-model="a" />
                  </div>
                </div>
                <div class="col-sm-6 col-12 info-row">
                  <div class="key w140">
                    Ghi chú
                  </div>
                  <div class="value">
                    <input type="text" class="form-control" v-model="a" />
                  </div>
                </div>
                <div class="col-sm-6 col-12 info-row">
                  <div class="key w140">
                    Đơn vị bán
                  </div>
                  <div class="value">
                    <input type="text" class="form-control" v-model="a" />
                  </div>
                </div>
                <div class="col-sm-6 col-12 info-row">
                  <div class="key w140">
                    Loại giấy tờ
                  </div>
                  <div class="value">
                    <input type="text" class="form-control" v-model="a" />
                  </div>
                </div>
                <div class="col-sm-6 col-12 info-row">
                  <div class="key w140">
                    Nhân viên bán
                  </div>
                  <div class="value">
                    <input type="text" class="form-control" v-model="a" />
                  </div>
                </div>
                <div class="col-sm-6 col-12 info-row">
                  <div class="key w140">
                    Loại HĐ
                  </div>
                  <div class="value">
                    <input type="text" class="form-control" v-model="a" />
                  </div>
                </div>
                <div class="col-sm-6 col-12 info-row">
                  <div class="key w140">
                    Loại KH
                  </div>
                  <div class="value">
                    <input type="text" class="form-control" v-model="a" />
                  </div>
                </div>
                <div class="col-sm-6 col-12 info-row">
                  <div class="key w140">
                    Mã số thuế
                  </div>
                  <div class="value">
                    <input type="text" class="form-control" v-model="a" />
                  </div>
                </div>
                <div class="col-sm-6 col-12 info-row">
                  <div class="key w140">
                    Số tài khoản
                  </div>
                  <div class="value">
                    <input type="text" class="form-control" v-model="a" />
                  </div>
                </div>
                <div class="col-sm-6 col-12 info-row">
                  <div class="key w140">
                    Ngân hàng
                  </div>
                  <div class="value">
                    <input type="text" class="form-control" v-model="a" />
                  </div>
                </div>
                <div class="col-sm-6 col-12 info-row">
                  <div class="key w140">
                    Ngày YC
                  </div>
                  <div class="value">
                    <input type="text" class="form-control" v-model="a" />
                  </div>
                </div>
                <div class="col-sm-6 col-12 info-row">
                  <div class="key w140">
                    Cộng tác viên
                  </div>
                  <div class="value">
                    <input type="text" class="form-control" v-model="a" />
                  </div>
                </div>
                <div class="col-sm-6 col-12 info-row">
                  <div class="key w140">
                    Kiểu hợp đồng
                  </div>
                  <div class="value">
                    <input type="text" class="form-control" v-model="a" />
                  </div>
                </div>
                <div class="col-sm-6 col-12 info-row">
                  <div class="key w140">
                    Phân loại khách hàng
                  </div>
                  <div class="value">
                    <input type="text" class="form-control" v-model="a" />
                  </div>
                </div>
                <div class="col-sm-6 col-12 info-row">
                  <div class="key w140">
                    Người lập hợp đồng
                  </div>
                  <div class="value">
                    <input type="text" class="form-control" v-model="a" />
                  </div>
                </div>
                <div class="col-sm-6 col-12 info-row">
                  <div class="key w140">
                    NV giới thiệu
                  </div>
                  <div class="value">
                    <input type="text" class="form-control" v-model="a" />
                  </div>
                </div>
                <div class="col-sm-12 col-12 info-row">
                  <div class="key w140">
                    Tổng tiền hợp đồng
                  </div>
                  <div class="value">
                    <input type="text" class="form-control" />
                  </div>
                </div>
                <div class="col-sm-6 col-12 info-row">
                  <div class="key w140">
                    Email khách hàng
                  </div>
                  <div class="value">
                    <input type="text" class="form-control" v-model="a" />
                  </div>
                </div>
                <div class="col-sm-6 col-12 info-row">
                  <div class="key w140">
                    Kiểu yêu cầu
                  </div>
                  <div class="value">
                    <input type="text" class="form-control" v-model="a" />
                  </div>
                </div>
                <div class="col-sm-6 col-12 info-row">
                  <div class="key w140">
                    Trạng thái email
                  </div>
                  <div class="value">
                    <input type="text" class="form-control" v-model="a" />
                  </div>
                </div>
                <div class="col-sm-6 col-12 info-row">
                  <div class="key w140">
                    Mục đích email
                  </div>
                  <div class="value">
                    <input type="text" class="form-control" v-model="a" />
                  </div>
                </div>
              </div>
            </div>

            <div class="box-form">
              <div class="title-bg-main">
                <div class="title"><span class="fa fa-angle-up text-main marr5"></span> Tiền hợp đồng và tiền thanh toán</div>
              </div>
              <DataGrid v-bind:columns="a" v-bind:dataSource="a" :enable-paging-server="false" :allowPaging="true" :showFilter="true"> </DataGrid>
            </div>
          </div>
          <div id="hopdongthanhtoan" class="tab-pane fade">
            <div class="box-form">
              <div class="row">
                <div class="col-sm-6 col-12">
                  <div class="info-row">
                    <div class="key w140">
                      Mã thanh toán
                    </div>
                    <div class="value">
                      <input type="text" class="form-control" v-model="a" />
                    </div>
                  </div>
                  <div class="info-row">
                    <div class="key w140">
                      Địa chỉ thanh toán
                    </div>
                    <div class="value">
                      <input type="text" class="form-control" v-model="a" />
                    </div>
                  </div>

                  <div class="info-row">
                    <div class="key w140">
                      Mã số thuế
                    </div>
                    <div class="value">
                      <input type="text" class="form-control" v-model="a" />
                    </div>
                  </div>
                  <div class="info-row">
                    <div class="key w140">
                      Đơn vị quản lý
                    </div>
                    <div class="value">
                      <input type="text" class="form-control" v-model="a" />
                    </div>
                  </div>
                  <div class="info-row">
                    <div class="key w140">
                      Ngân hàng
                    </div>
                    <div class="value">
                      <input type="text" class="form-control" v-model="a" />
                    </div>
                  </div>
                  <div class="info-row">
                    <div class="key w140">
                      Email
                    </div>
                    <div class="value">
                      <input type="text" class="form-control" v-model="a" />
                    </div>
                  </div>
                  <div class="info-row">
                    <div class="key w140">
                      Tuyến thu
                    </div>
                    <div class="value">
                      <input type="text" class="form-control" v-model="a" />
                    </div>
                  </div>
                </div>
                <div class="col-sm-6 col-12">
                  <div class="info-row">
                    <div class="key w140">
                      Tên thanh toán
                    </div>
                    <div class="value">
                      <input type="text" class="form-control" v-model="a" />
                    </div>
                  </div>
                  <div class="info-row">
                    <div class="key w140">
                      Mã TB ĐD
                    </div>
                    <div class="value">
                      <input type="text" class="form-control" v-model="a" />
                    </div>
                  </div>
                  <div class="info-row">
                    <div class="key w140">
                      Số tài khoản
                    </div>
                    <div class="value">
                      <input type="text" class="form-control" v-model="a" />
                    </div>
                  </div>
                  <div class="info-row">
                    <div class="key w140">
                      Hình thức thanh toán
                    </div>
                    <div class="value">
                      <input type="text" class="form-control" v-model="a" />
                    </div>
                  </div>
                  <div class="info-row">
                    <div class="key w140">
                      Số điện thoại
                    </div>
                    <div class="value">
                      <input type="text" class="form-control" v-model="a" />
                    </div>
                  </div>
                  <div class="info-row">
                    <div class="key w140">
                      Địa chỉ chứng từ
                    </div>
                    <div class="value">
                      <input type="text" class="form-control" v-model="a" />
                    </div>
                  </div>
                  <div class="info-row">
                    <div class="key w140">
                      Ghi chú
                    </div>
                    <div class="value">
                      <input type="text" class="form-control" v-model="a" />
                    </div>
                  </div>
                </div>
              </div>
              <div class="info-row">
                <div class="key w140">
                  Địa chỉ báo cước
                </div>
                <div class="value">
                  <input type="text" class="form-control" v-model="a" />
                </div>
              </div>
              <div class="row">
                <div class="col-sm-6 col-12">
                  <div class="info-row">
                    <div class="key w140">
                      Email thanh toán
                    </div>
                    <div class="value">
                      <input type="text" class="form-control" v-model="a" />
                    </div>
                  </div>
                  <div class="info-row">
                    <div class="key w140">
                      Trạng thái email
                    </div>
                    <div class="value">
                      <input type="text" class="form-control" v-model="a" />
                    </div>
                  </div>
                </div>
                <div class="col-sm-6 col-12">
                  <div class="info-row">
                    <div class="key w140">
                      Kiểu yêu cầu
                    </div>
                    <div class="value">
                      <input type="text" class="form-control" v-model="a" />
                    </div>
                  </div>
                  <div class="info-row">
                    <div class="key w140">
                      Mục đích email
                    </div>
                    <div class="value">
                      <input type="text" class="form-control" v-model="a" />
                    </div>
                  </div>
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
export default {
  data() {
    return {}
  }
}
</script>
<style scoped></style>
