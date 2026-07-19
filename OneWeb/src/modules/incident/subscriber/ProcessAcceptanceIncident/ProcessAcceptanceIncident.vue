<template>
<div id="ProcessAcceptanceIncident" class="main-wrapper">
    <breadcrumb :header="header" />
    <div class="list-actions-top">
        <ul class="list">
            <li>
                <a @click="tsbtnLayTTMoi_Click">
                    <span class="icon one-file-attach1"></span>Lấy TT
                </a>
            </li>
            <li v-if="tsbtnCapNhat.visible">
                <a @click="tsbtnCapNhatKQ_Click">
                    <span class="icon one-reload1" title="Cập nhật."></span>Cập nhật
                </a>
            </li>
            <li v-if="tsbtnCapNhatNhieuPhieu.visible">
                <a @click="tsbtnCapNhatNhieuPhieu_Click">
                    <span class="icon one-reload1"></span>Cập nhật
                </a>
            </li>
            <li v-if="tsbtnGiaoViecNhieu.visible">
                <a :class="tsbtnGiaoViecNhieu.enabled ? '' : 'disabled'" @click.prevent="tsbtnGiaoViecNhieu_Click">
                    <span class="icon one-file-edit2"></span>Giao việc
                </a>
            </li>
            <li v-if="tsbtnGiaoViec.visible">
                <a :class="tsbtnGiaoViec.enabled ? '' : 'disabled'" @click.prevent="tsbtnGiaoViec_Click">
                    <span class="icon one-file-edit2"></span>Giao việc
                </a>
            </li>
            <li v-if="tsbtnHoanCong.visible">
                <a :class="tsbtnHoanCong.enabled ? '' : 'disabled'" @click.prevent="tsbtnHoanCong_Click">
                    <span class="icon one-hoancong"></span> Hoàn công
                </a>
            </li>
            <li>
                <a @click="tsbtnExcel_Click">
                    <span class="icon one-excel"></span>Xuất Excel
                </a>
            </li>
            <li v-if="tsbtnChuyenTheoDoi.visible">
                <a :class="tsbtnChuyenTheoDoi.enabled ? '' : 'disabled'" @click="tsbtnChuyenTheoDoi_Click">
                    <span class="icon one-list-arrow-right"></span>Chuyển theo dõi
                </a>
            </li>
            <li v-if="tsbtnChuyenTTDH.visible">
                <a :class="tsbtnChuyenTTDH.enabled ? '' : 'disabled'" @click="tsbtnChuyenTTDH_Click">
                    <span class="icon one-list-arrow-right"></span>{{ tsbtnChuyenTTDH.text }}
                </a>
            </li>
            <li v-if="tsbtnBaoTon_BH.visible">
                <a :class="tsbtnBaoTon_BH.enabled ? '' : 'disabled'" @click="tsbtnBaoTon_Click">
                    <span class="icon nc-icon-outline ui-1_circle-remove"></span>Báo tồn
                </a>
            </li>
            <li v-if="tsbtnNghiemThuNhieuPhieu.visible">
                <a :class="tsbtnNghiemThuNhieuPhieu.enabled ? '' : 'disabled'" @click.prevent="tsbtnNghiemThuNhieuPhieu_Click">
                    <span class="icon nc-icon-glyph ui-1_check-circle-08"></span>Nghiệm
                    thu
                </a>
            </li>
            <li v-if="tsbtnNghiemThu.visible">
                <a :class="tsbtnNghiemThu.enabled ? '' : 'disabled'" @click="tsbtnNghiemThu_Click">
                    <span class="icon nc-icon-glyph ui-1_check-circle-08"></span>Nghiệm
                    thu
                </a>
            </li>
            <li v-if="false">
                <!-- <li v-if="btnGiaoPhieuTG.visible"> -->
                <a :class="btnGiaoPhieuTG.enabled ? '' : 'disabled'" @click="btnGiaoPhieuTG_Click">
                    <span class="icon one-clipart-arrow-right"></span>Giao trạm TG
                </a>
            </li>
            <li v-if="tsbtnInPhieuTC.visible">
                <a @click.prevent="tsbtnInPhieuTC_Click">
                    <span class="icon one-print"></span>Xuất phiếu TC
                </a>
            </li>
            <li v-if="tsbtnChuyenTo.visible">
                <a :class="tsbtnChuyenTo.enabled ? '' : 'disabled'" @click.prevent="tsbtnChuyenTo_Click">
                    <span class="icon one-change"></span>Chuyển tổ
                </a>
            </li>
            <li v-if="tsbtnTraPhieu.visible">
                <a :class="tsbtnTraPhieu.enabled ? '' : 'disabled'" @click="btnTraPhieu_Click">
                    <span class="icon nc-icon-glyph ui-1_circle-remove"></span> Trả
                    phiếu
                </a>
            </li>
            <li v-if="tsbtnGianDo.visible">
                <a :class="tsbtnGianDo.enabled ? '' : 'disabled'" @click="tsbtnGianDo_Click">
                    <span class="icon one-search"></span>Tra cứu cáp
                </a>
            </li>
        </ul>
        <div class="more dropdown">
            <a href="#" data-toggle="dropdown">
                <span class="icon -ap icon-dots-three-vertical"></span>
            </a>
            <div class="dropdown-menu">
                <a v-if="tsbtnChuyenTTVT.visible" :class="tsbtnChuyenTTVT.enabled ? '' : 'disabled'" @click="tsbtnChuyenTTVT_Click">
                    <span class="dropdown-icon nc-icon-glyph arrows-1_simple-right"></span>
                    Chuyển TTVT
                </a>
                <a v-if="tsbtnDoKiem.visible" :class="tsbtnDoKiem.enabled ? '' : 'disabled'" @click="tsbtnDoKiem_Click">
                    <span class="dropdown-icon one-file-arrow-right"></span> Show Port
                </a>
                <a v-if="tsbtnKhaoSat.visible" :class="tsbtnKhaoSat.enabled ? '' : 'disabled'" @click="tsbtnKhaoSat_Click">
                    <span class="dropdown-icon nc-icon-glyph business_chart-pie-36"></span>
                    Khảo sát
                </a>
                <a v-if="tsbtnVatTuMoi.visible" :class="tsbtnVatTuMoi.enabled ? '' : 'disabled'" @click="tsbtnVatTuMoi_Click">
                    <span class="dropdown-icon one-vt"></span> Vật tư
                </a>
                <a v-if="tsbtnThayDoiTT_BH.visible" :class="tsbtnThayDoiTT_BH.enabled ? '' : 'disabled'" @click="tsbtnThayDoiTT_Click">
                    <span class="dropdown-icon one-sync"></span> Thay đổi TT
                </a>
                <a @click="toolStripButton1_Click">
                    <span class="dropdown-icon one-circle-question"></span> Trợ giúp
                </a>
                <a v-if="tsbtnKQ_DoKiem.visible" :class="tsbtnKQ_DoKiem.enabled ? '' : 'disabled'" @click="tsbtnKQ_DoKiem_Click">
                    <span class="dropdown-icon nc-icon-glyph design_design"></span> Đo
                    kiểm
                </a>
                <a @click="tsbtnNhacViec_Click">
                    <span class="dropdown-icon one-file-noti"></span> Nhắc việc
                </a>
                <a @click="tsddbIn_Click">
                    <span class="dropdown-icon one-print"></span> In phiếu
                </a>
                <a @click="tsbtnLichSuHen_Click">
                    <span class="dropdown-icon one-calendar-history"></span> Lịch sử hẹn
                </a>
                <a @click="tsbtnHenKH_Click">
                    <span class="dropdown-icon one-calendar-check"></span> Hẹn KH
                </a>
                  <a ref="" @click="tsbtnTTLoainha_Click" ref="tsbtnTTLoainha">
                    <span class="dropdown-icon one-book1"></span> Thông tin loại nhà KH
                </a>
            </div>
        </div>
    </div>
    <div class="page-content">
        <div class="box-form">
            <div class="red fw6" @dblclick="lblSuCoTTVT_DoubleClick">
                {{ lblSuCoTTVT.value }}
            </div>
        </div>
        <div class="grid-stack-box">
            <Split :gutterSize="12">
                <SplitArea :size="50" :minSize="100">
                    <div class="box-col box-form" id="boxLeft" style="height: 96%">
                        <div class="legend-title">
                            <span class="icon fa fa-angle-up"></span> Thông tin thuê bao
                        </div>
                        <div class="row">
                            <div class="col-sm-6 col-12">
                                <div class="info-row">
                                    <div class="key w100">Dịch vụ</div>
                                    <div class="value">
                                        <div class="select-custom">
                                            <ejs-dropdownlist class="combobox" :enabled="DichVuVT.enabled" :dataSource="cboDichVuVT" :fields="fields.DichVuVT" v-model="DichVuVT.value" :change="cboDichVuVT_SelectedValueChanged" :allowFiltering="true" :filtering="onFilteringDichVuVT"></ejs-dropdownlist>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="col-sm-6 col-12">
                                <div class="info-row">
                                    <div class="key w100">Loại hình</div>
                                    <div class="value">
                                        <input type="text" class="form-control" v-model="LoaiTB" />
                                    </div>
                                </div>
                            </div>

                        </div>
                        <div class="row">
                            <div class="col-sm-6 col-12">
                                <div class="info-row">
                                    <div class="key w100">Quy trình</div>
                                    <div class="value">
                                        <div class="select-custom">
                                            <ejs-dropdownlist id="QuyTrinh" class="combobox" :enabled="QuyTrinh.enabled" :dataSource="cboQuyTrinh" :fields="fields.QuyTrinh" v-model="QuyTrinh.value" :change="cboQuyTrinh_SelectedValueChanged"></ejs-dropdownlist>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="col-sm-6 col-12">
                                <div class="info-row">
                                    <div class="key w100">Số máy/Acc</div>
                                    <div class="value">
                                        <input id="txtMaTB" type="text" class="form-control highlight" @keydown="txtMaTB_KeyPress" v-model="MaTB" />
                                    </div>
                                </div>
                            </div>
                            <div class="col-sm-6 col-12">
                                <div class="info-row">
                                    <div class="key w100">Người BH (*)</div>
                                    <div class="value">
                                        <input type="text" class="form-control" v-model="NguoiBH" />
                                    </div>
                                </div>
                            </div>
                            <div class="col-sm-6 col-12">
                                <div class="info-row">
                                    <div class="key w100">Ngày BH</div>
                                    <div class="value">
                                        <div class="input-icon-right">
                                            <date-picker disabled id="dtpNgayBH" format="DD/MM/YYYY HH:mm A" :value-type="'format'" type="datetime" v-model="dtpNgayBH" />
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-sm-6 col-12">
                                <div class="info-row">
                                    <div class="key w100">Đặc trưng KH</div>
                                    <div class="value">
                                        <input type="text" class="form-control" v-model="Dactrung" />
                                    </div>
                                </div>
                            </div>
                            <div class="col-sm-6 col-12">
                                <div class="info-row">
                                    <div class="key w100">Điện thoại LH</div>
                                    <div class="value">
                                        <input type="text" class="form-control" v-model="DienThoaiLH" />
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-sm-6 col-12">
                                <div class="info-row">
                                    <div class="key w100">Tình trạng hỏng</div>
                                    <div class="value">
                                        <div class="select-custom">
                                            <ejs-dropdownlist class="combobox" :allowFiltering="true" :dataSource="cboTinhTrang" :fields="fields.TinhTrang" v-model="TinhTrang.value" :filtering="onFilteringTinhTrang"></ejs-dropdownlist>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="col-sm-6 col-12">
                                <div class="info-row">
                                    <div class="key w100">Tên thuê bao</div>
                                    <div class="value">
                                        <input type="text" class="form-control" v-model="TenTB" />
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="info-row" v-if="DiaChiLD.visible">
                            <div class="key w100">Địa chỉ LĐ</div>
                            <div class="value">
                                <div class="input-more-button">
                                    <button class="btn" @click="btnChonToaDo_Click">
                                        <span class="one-map-location f20"></span>
                                    </button>
                                    <input type="text" class="form-control" v-model="DiaChiLD.value" />
                                </div>
                            </div>
                        </div>
                        <div class="info-row">
                            <div class="key w100">Trạng thái TB</div>
                            <div class="value">
                                <input type="text" class="form-control" v-model="TrangThaiTB" />
                            </div>
                        </div>
                        <div class="info-row">
                            <div class="key w100">Ghi chú</div>
                            <div class="value">
                                <input type="text" class="form-control" v-model="GhiChuHong" />
                            </div>
                        </div>

                        <div class="row">
                            <div class="col-sm-4 col-12" v-if="hangSx.visible">
                                <div class="info-row">
                                    <div class="key w100">Hãng SX</div>
                                    <div class="value">
                                        <input type="text" class="form-control" v-model="hangSx.value" />
                                    </div>
                                </div>
                            </div>
                            <div class="col-sm-4 col-12" v-if="soNgay.visible">
                                <div class="info-row">
                                    <div class="key w100">Số ngày</div>
                                    <div class="value">
                                        <input type="text" class="form-control" v-model="soNgay.value" />
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </SplitArea>
                <SplitArea :size="50" :minSize="100">
                    <div class="box-col box-form" id="boxRight" style="height: 96%">
                        <div class="legend-title">Thông số kỹ thuật</div>
                        <div v-if="ds_thongtin_kt">
                            <div v-for="(thongtin_kt, index) in ds_thongtin_kt" :key="index">
                                <span class="font-weight-bold">{{ thongtin_kt.ten_truong }}:</span>
                                <span>{{ thongtin_kt.giatri }}</span>
                            </div>
                        </div>
                        <div v-else class="desc">Chưa có dữ liệu</div>
                    </div>
                </SplitArea>
            </Split>
        </div>
        <div class="grid-stack-box">
            <Split :gutterSize="12">
                <SplitArea :size="50" :minSize="100">
                    <div class="box-col box-form" id="boxLeft1" style="height: 96%">
                        <div class="legend-title">
                            <div class="pull-left">
                                <span class="icon fa fa-angle-up"></span>Thông tin xử lý
                            </div>
                            <div class="pull-right">
                                <div class="list-checks red">
                                    <div class="item">
                                        <div>
                                            <ejs-checkbox label=" " v-model="chkPhieuTra.value" :checked="chkPhieuTra.value"></ejs-checkbox>
                                            <span class="name">Phiếu trả</span>
                                        </div>
                                    </div>
                                    <div class="item">
                                        <ejs-checkbox label=" " v-model="chkChoXL.value" :checked="chkChoXL.value" :change="chkChoXL_CheckedChanged"></ejs-checkbox>
                                        <span class="name">Chờ xử lý</span>
                                    </div>
                                </div>
                            </div>
                            <div class="clearfix"></div>
                        </div>
                        <div class="row">
                            <div class="col-sm-6 col-12">
                                <div class="info-row">
                                    <div class="key w100">Kết quả XL</div>
                                    <div class="value">
                                        <div class="select-custom">
                                            <ejs-dropdownlist class="combobox" :change="cboKQXL_SelectedIndexChanged_1" :enabled="KQXL.enabled" :dataSource="cboKQXL" :fields="fields.KQXL" v-model="KQXL.value"></ejs-dropdownlist>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="col-sm-6 col-12">
                                <div class="info-row">
                                    <div class="key w70">Người GV</div>
                                    <div class="value">
                                        <div class="select-custom">
                                            <ejs-dropdownlist ref="NguoiGV" class="combobox" :allowFiltering="true" :dataSource="cboNguoiGV" :fields="fields.NguoiGV" v-model="NguoiGV" :filtering="onFilteringNguoiGV" :change="cboNguoiGV_EditValueChanged"></ejs-dropdownlist>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-sm-6 col-12">
                                <div class="info-row">
                                    <div class="key w100">Ngày giao việc</div>
                                    <div class="value w20 padt7">
                                        <div class="check-action">
                                            <ejs-checkbox label=" " v-model="chkNgayGV.value" :checked="chkNgayGV.value"></ejs-checkbox>
                                        </div>
                                    </div>
                                    <div class="value">
                                        <div class="input-icon-right">
                                            <date-picker disabled input-class="form-control" format="DD/MM/YYYY HH:mm A" :value-type="'format'" type="datetime" v-model="dtpNgayGV" />
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-sm-6 col-12">
                                <div class="info-row">
                                    <div class="key w100">Ngày xử lý</div>
                                    <div class="value w20 padt7">
                                        <div class="check-action">
                                            <ejs-checkbox disabled="true" label=" " v-model="chkNgayBG.value" :checked="chkNgayBG.value"></ejs-checkbox>
                                        </div>
                                    </div>
                                    <div class="value">
                                        <div class="input-icon-right">
                                            <date-picker disabled input-class="form-control" format="DD/MM/YYYY HH:mm A" :value-type="'format'" type="datetime" v-model="dtpNgayTH" />
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="col-sm-6 col-12">
                              <div class="info-row">
                                  <div style="padding-left:5px" class="key w70">Ngày XN</div>
                                  <div class="value">
                                      <div class="input-icon-right">
                                          <date-picker :disabled="!dtpNgayXN.enabled" input-class="form-control" format="DD/MM/YYYY HH:mm A" :value-type="'format'" type="datetime" v-model="dtpNgayXN.value" />
                                      </div>
                                  </div>
                              </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-sm-12 col-12">
                                <div class="info-row">
                                    <div class="key w100" :hidden="KQXL.value == 1||KQXL.value == 2">Nhóm N.N</div>
                                    <div class="value" :hidden="KQXL.value == 1||KQXL.value == 2">
                                        <div class="select-custom">
                                            <ejs-dropdownlist class="combobox" :tooltip="true" :allowFiltering="true" :dataSource="cboNhomNN" :fields="fields.NhomNN" v-model="NhomNN.value" :change="cboNhomNN_Changed" :filtering="onFilteringNhomNN"></ejs-dropdownlist>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="col-sm-12 col-12">
                                <div class="info-row">
                                    <div class="key w100" :hidden="KQXL.value == 1||KQXL.value == 2">N.nhân</div>
                                    <div class="value select-custom"  :hidden="KQXL.value == 1||KQXL.value == 2">
                                        <ejs-multiselect v-model="ds_nguyennhan_id" 
                                        id="ds_nguyennhan_id" 
                                        :dataSource="ds_nguyennhan" 
                                        placeholder="Chọn nguyên nhân" 
                                        selectAllText="Chọn tất cả" 
                                        :showSelectAll="true"
                                         locale='vi-VN'
                                        :showDropDownIcon="true"
                                        unSelectAllText="Bỏ chọn tất cả" 
                                        mode="CheckBox" 
                                        :fields="{ text: 'ct_hong', value: 'cthong_id' }">
                                        </ejs-multiselect>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-sm-12 col-12">
                                <div class="info-row">
                                    <div class="key w100" v-if="NhomTon.visible">Nhóm tồn</div>
                                    <div class="value" v-if="NhomTon.visible">
                                        <div class="select-custom">
                                            <ejs-combobox class="custom-combobox" :change="cboNhomTon_Changed" v-model="nhomton_id" :dataSource="cboNhomTon" :allowFiltering="true" :filtering="onFilterNhomTon" :fields="{ value: 'NHOMTON_ID', text: 'NHOMTON' }"></ejs-combobox>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="col-sm-12 col-12">
                                <div class="info-row">
                                    <div class="key w100" v-show="pnlTon1.visible">
                                        Lý do tồn
                                    </div>
                                    <div class="value" v-show="pnlTon1.visible">
                                        <ejs-multiselect v-model="ds_lydoton_id" 
                                        :dataSource="ds_lydoton" 
                                        :allowFiltering="true" 
                                        :showSelectAll="true"
                                         locale='vi-VN'
                                        :showDropDownIcon="true"
                                        :filtering="onFilterLyDoTon" 
                                         selectAllText="Chọn tất cả" 
                                         unSelectAllText="Bỏ chọn tất cả" 
                                        placeholder="Chọn lý do tồn" 
                                        mode="CheckBox" 
                                        :fields="{ value: 'lydotonbh_id', text: 'baohong_ton' }">
                                        </ejs-multiselect>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="info-row">
                            <div class="key w100">Ghi chú</div>
                            <div class="value">
                                <input type="text" class="form-control" v-model="GhiChuTH" />
                            </div>
                        </div>
                    </div>
                </SplitArea>
                <SplitArea :size="50" :minSize="100" style="height: 100%">
                    <div class="box-col box-form" id="boxRight1" style="height: 96%; margin-left: 0">
                        <div class="legend-title">Danh sách nhân viên xử lý</div>
                        <div class="table-content">
                            <DSNhanVien :danhSach="ds_NhanVien" />
                        </div>
                    </div>
                </SplitArea>
            </Split>
        </div>
        <div class="box-form">
            <div class="legend-title">Danh sách phiếu báo hỏng</div>
            <div class="row">
                <div class="col-sm-3 col-12" v-if="lblDenNgay.visible">
                    <div class="info-row">
                        <div class="key w90">
                            <ejs-checkbox label=" " v-model="chkTuNgay.value" :checked="chkTuNgay.value"></ejs-checkbox>
                            <span class="name">Từ ngày</span>
                        </div>
                        <div class="value">
                            <div class="input-icon-right">
                                <date-picker input-class="form-control" format="DD/MM/YYYY" :value-type="'format'" v-model="dtpTuNgay" />
                            </div>
                        </div>
                    </div>
                </div>
                <div class="col-sm-3 col-12" v-if="lblDenNgay.visible">
                    <div class="info-row">
                        <div class="key w90">Đến ngày</div>
                        <div class="value">
                            <div class="input-icon-right">
                                <date-picker input-class="form-control" format="DD/MM/YYYY" :value-type="'format'" v-model="dtpDenNgay" />
                            </div>
                        </div>
                    </div>
                </div>
                <div class="col-sm-4 col-12">
                    <div class="info-row">
                        <div class="key w90">
                            {{ TramVT.text }}
                        </div>
                        <div class="value">
                            <div class="row">
                                <div class="col-sm-6 col-12" v-if="TramVT.visible">
                                    <div class="select-custom">
                                        <ejs-dropdownlist :allowFiltering="true" :dataSource="cboTramVT" :change="cboTramVT_EditValueChanged" :fields="fields.TramVT" v-model="TramVT.value" :filtering="onFilteringTramVT"></ejs-dropdownlist>
                                    </div>
                                </div>
                                <div class="col-sm-6 col-12">
                                    <div class="select-custom">
                                        <ejs-dropdownlist :dataSource="cboDaGiaoViec" :fields="fields.DaGiaoViec" v-model="DaGiaoViec.value" :change="cboDaGiaoViec_SelectedIndexChanged"></ejs-dropdownlist>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="col col-12 tright">
                    <button class="btn btn-second" @click="showMap">
                        <span class="nc-icon-outline location_map-pin f20 lh20"></span>
                    </button>
                </div>
            </div>
            <div class="table-content">
              <DataGrid
                    v-if="isShowDSBH"
                    v-bind:columns="collumnsDSBH"
                    ref="dsbaohong"
                    v-bind:dataSource="dtList"
                    :showFilter="true"
                    :allowPaging="true"
                    :panelDataHeight= 250
                    :enablePagingServer="false"
                    :showColumnCheckbox="true"
                    :enabledSelectFirstRow="false"
                    :contextMenuItems="menuItems"
                    @contextMenuClick="contextMenuClick"
                    @selectedItemsChanged="getCurrentSelectedItems"
                    @rowClicked="gridviewDanhSach_FocusedRowChanged"
                    :selectionSettings="{ checkboxMode: 'ResetOnRowClick' }"
                    @queryCellInfo="gridviewDanhSach_RowCellStyle"
                >
                </DataGrid>
                <!--<DSPhieuBaoHong v-if="isShowDSBH" :danhSach="dtList" @onFocusRowDS_BaoHong="gridviewDanhSach_FocusedRowChanged" @getCurrentSelectedItems="getCurrentSelectedItems" @queryCellInfo="gridviewDanhSach_RowCellStyle" :collumnsInvisible="collumnsInvisible" /> -->
                <div v-else class="map-canvas-box">
                    <BanDo :position="map" :diachi="map_info.dia_chi" :ten_kc="map_info.title" :zoomDefault="5" :mapTypeId="mapTypeId"></BanDo>
                    <div class="action-right">
                        <a href="javascript:void(0)" class="change-type" @click="changetypeMap">
                            <img src="@/modules/CABMAN/XetDuyetPhanTuMangHoDanThuThap/ChonToaDo/image3.png" alt="" />
                        </a>
                    </div>
                </div>
            </div>
            <ejs-dialog :enableResize="true" :visible="false" ref="dlg_giaophieunhanvien" :header="'Giao phiếu nhân viên'" showCloseIcon="true" target=".main-wrapper" width="80%" height="900">
                <GiaoPhieuNhanVien ref="ppGiaoPhieuNhanVien" @giaophieunv_success="giaophieunv_success" />
            </ejs-dialog>

            <ejs-dialog :enableResize="true" :visible="false" ref="dlg_giaophieunhanvien_v2" :header="'Giao phiếu nhân viên'" showCloseIcon="true" target=".main-wrapper" width="80%" height="900">
                <GiaoPhieuNhanVien_v2 ref="ppGiaoPhieuNhanVien_v2" @giaophieunv_success="giaophieunv_success" />
            </ejs-dialog>
            <DoKiemModal ref="doKiem" :baohong_id="Number(baohong_id)" :hdtb_id="0" />
            <ChuyenTheoDoi ref="chuyenTheoDoi" v-model="soNgayCTD" @OK="selected_so_ngay" />
            <frmHenKHV3 ref="reffrmHenKH" :params="frmHenKHInput" @CapnhatSuccess="CloseHenKH"></frmHenKHV3>
            <frmHenKHV2 ref="reffrmHenKHV2" :params="frmHenKHV2Input" @CapnhatSuccess="CloseHenKH"></frmHenKHV2>
            <CapVatTuTB ref="popupVatTuTB" :input-data="modelCapVatTu" />
            <ejs-dialog :enableResize="true" :visible="false" ref="dlg_vattubaohong" :header="'Cấp vật tư/thiết bị cho thuê bao'" showCloseIcon="false" target=".main-wrapper" width="60%" height="800">
                <VatTuBaoHong ref="ppVatTuBaoHong" :phieu_id="PHIEU_ID" :baohong_id="baohong_id" :thuebao_id="thuebao_id" />
            </ejs-dialog>
            <ModalChonToaDo idModalChonToaDo="ModalChonToaDo" @XacNhanToaDo="XacNhanToaDo" :position="map" :diachi="map_info.dia_chi" :ten_kc="map_info.title"></ModalChonToaDo>
            <!-- <ejs-dialog
          :enableResize="true"
          :visible="false"
          ref="dlg_giaophieutramtg"
          :header="'Giao phiếu điểm chia Trung gian TSL'"
          showCloseIcon="true"
          target=".main-wrapper"
          width="60%"
          height="800"
        >
          <DeliveryIntermediate
            :MA_TB="MaTB"
            :BAOHONG_ID="baohong_id"
            :PHIEU_ID="PHIEU_ID"
          />
        </ejs-dialog> -->
            <ThayDoiTTBH ref="popupThayDoiTTBH" :matb_t="MaTB" :trangthaitb_t="TrangThaiTB" :tentb_t="TenTB" :ngaybh_t="dtpNgayBH" :nguoibh_t="NguoiBH" :dienthoailh_t="DienThoaiLH" :baohong_id_t="baohong_id" @updateSuccess="HienThiDanhSach" />
            <ejs-dialog :enableResize="true" :visible="false" ref="dlg_khaosatkh" :header="'Khảo sát khách hàng báo hỏng'" showCloseIcon="false" target=".main-wrapper" width="80%" height="800">
                <KhaoSatKH ref="ppKhaoSatKH" />
            </ejs-dialog>
            <ejs-dialog :enableResize="true" :visible="false" ref="dlg_traphieubaohong" :header="'Trả phiếu báo hỏng'" showCloseIcon="true" target=".main-wrapper" width="90%" height="900">
                <TraPhieuBaoHong ref="ppTraPhieuBaoHong" />
            </ejs-dialog>
            <ejs-dialog :enableResize="true" :visible="false" ref="dlg_tracuumdf" :header="'Tra cứu thuê bao - phiến dọc'" showCloseIcon="false" target=".main-wrapper" width="60%" height="800">
                <TraCuuMDF ref="ppTraCuuMDF" />
            </ejs-dialog>
            <LichSu_HenBH ref="popupLichSu_HenBH" :baohong_id="baohong_id" :kieu="2" />
            <daunoithuebao id="dandocap" :ma_thuebao="MaTB" />
            <PopupLoaiToanha ref="popupLoaiToanha" />
            <frmNhacViecBH ref="dialogfrmNhacViecBH" :params="frmNhacViecBHInput"></frmNhacViecBH>
            <ejs-dialog :enableResize="true" :visible="false" ref="dlg_ndgiao" :header="'Nội dung giao'" showCloseIcon="false" target=".main-wrapper" width="50%" height="200">
                <NoiDungGiao @giao_phieu="dlg_ndgiao_giao_phieu" ref="ppNoiDungGiao" />
            </ejs-dialog>
            <InBienBan ref="popupInBB" :params="inBB" />

            <ejs-dialog :isModal="false" :enableResize="true" :visible="false" ref="checkPort" :header='"Kiểm tra Port thuê bao"' showCloseIcon="true" target=".main-wrapper" width="60%" height="500" allowDragging="true" @close="checkPortOnClose" :animationSettings="animationSettings">
                <div>
                    <CheckPort v-if="checkPortDialog.isVisiable" type="modal" :vma_tb="MaTB" :vkieu="0" />
                </div>
            </ejs-dialog>
            <frmXemThongBaoSuCo ref="frmXemThongBaoSuCo" />

            <ejs-dialog :enableResize="true" :visible="false" ref="dlg_trogiup" :header="'Hướng dẫn sử dụng chức năng nghiệm thu báo hỏng'" showCloseIcon="false" target=".main-wrapper" width="60%" height="700">
                <div class="modal-content popup-box">
                    <div id="includedContent"></div>
                </div>
            </ejs-dialog>

            <XemBaoCao ref="XemBaoCao" ma_bc="PHIEU_BH" :ds-gia-tri="dsGiaTri"></XemBaoCao>

            <ChuyenTo ref="popupChuyenTo" :ttbhId="ttbh_id" :bhId="baohong_id" @closeChuyenTo="HienThiDSNV" :tag="Tag"></ChuyenTo>
 
            <img id="frmNghiemThuBH892" src="@/assets/img/frmNghiemThuBH.files/frmNghiemThuBH892.png" style="display: none" />
            <img id="frmNghiemThuBH1883" src="@/assets/img/frmNghiemThuBH.files/frmNghiemThuBH1883.png" style="display: none" />
            <img id="frmNghiemThuBH2028" src="@/assets/img/frmNghiemThuBH.files/frmNghiemThuBH2028.png" style="display: none" />
            <img id="frmNghiemThuBH3027" src="@/assets/img/frmNghiemThuBH.files/frmNghiemThuBH3027.png" style="display: none" />
            <img id="frmNghiemThuBH3729" src="@/assets/img/frmNghiemThuBH.files/frmNghiemThuBH3729.png" style="display: none" />
            <img id="frmNghiemThuBH4203" src="@/assets/img/frmNghiemThuBH.files/frmNghiemThuBH4203.png" style="display: none" />
            <img id="frmNghiemThuBH4436" src="@/assets/img/frmNghiemThuBH.files/frmNghiemThuBH4436.png" style="display: none" />
            <img id="frmNghiemThuBH4525" src="@/assets/img/frmNghiemThuBH.files/frmNghiemThuBH4525.png" style="display: none" />
            <img id="frmNghiemThuBH5549" src="@/assets/img/frmNghiemThuBH.files/frmNghiemThuBH5549.png" style="display: none" />
            <img id="frmNghiemThuBH7147" src="@/assets/img/frmNghiemThuBH.files/frmNghiemThuBH7147.png" style="display: none" />
        </div>
    </div>
    <footer class="footer">VNPT-IT &copy; 2021</footer>
</div> 
</template>

<script src="./ProcessAcceptanceIncident.js"></script>

<style src="./ProcessAcceptanceIncident.scss"></style>
