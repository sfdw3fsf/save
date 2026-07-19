<template>
  <div id="frmGiaoViecVIP">
    <div
      v-show="popupContainerControl1.visible || puceNDTH_drop.visible"
      class="popupContainerControl1Drop"
      @click="
        () => {
          if (puceNDTH_drop.visible) {
            puceNDTH_drop.visible = false
            puceNDTH_edit.visible = false
          }
          if (popupContainerControl1.visible) {
            popupContainerControl1.visible = false
            popupContainerEdit1_CloseUp()
          }
        }
      "
    ></div>
    <breadcrumb :header="header" />
    <div class="list-actions-top">
      <ul class="list">
        <li v-if="tsbtnLayTT.visible" :class="{ disabledInput: !tsbtnLayTT.enabled }" @click="tsbtnLayTT_Click">
          <a> <span class="icon one-reload1"></span>Lấy thông tin</a>
        </li>
        <li v-if="tsbtnGiaoViec.visible" :class="{ disabledInput: !tsbtnGiaoViec.enabled }" @click="tsbtnGiaoViec_Click">
          <a> <span class="icon one-file-edit2"></span>Giao việc</a>
        </li>
        <li v-if="tsbtnHoanThanh.visible" :class="{ disabledInput: !tsbtnHoanThanh.enabled }" @click="tsbtnHoanThanh_Click">
          <a> <span class="icon one-file-check"></span>Hoàn thành</a>
        </li>
        <li v-if="tsbtnCreatIssue.visible" :class="{ disabledInput: !tsbtnCreatIssue.enabled }" @click="tsbtnCreatIssue_Click">
          <a> <span class="icon one-file-arrow-right"></span>Gửi IT360</a>
        </li>
        <li v-if="tsbtnGiaoPhieu.visible" :class="{ disabledInput: !tsbtnGiaoPhieu.enabled }" @click="tsbtnGiaoPhieu_Click">
          <a> <span class="icon one-file-arrow-right"></span>Giao phiếu</a>
        </li>
        <li v-if="tsbtnTraPhieuIT.visible" :class="{ disabledInput: !tsbtnTraPhieuIT.enabled }" @click="tsbtnTraPhieuIT_Click">
          <a> <span class="icon one-file-arrow-right"></span>Trả phiếu IT</a>
        </li>
        <li v-if="tsbtnXemIT360.visible" :class="{ disabledInput: !tsbtnXemIT360.enabled }" @click="tsbtnXemIT360_Click">
          <a> <span class="icon one-reload1"></span>Xem IT360</a>
        </li>
        <li v-if="tsbtnTraPhieuTinh.visible" :class="{ disabledInput: !tsbtnTraPhieuTinh.enabled }" @click="tsbtnTraPhieuTinh_Click">
          <a> <span class="icon one-file-back"></span>Trả phiếu tỉnh</a>
        </li>
        <li v-if="tsbtnTraPhieuKHDN.visible" :class="{ disabledInput: !tsbtnTraPhieuKHDN.enabled }" @click="tsbtnTraPhieuKHDN_Click">
          <a> <span class="icon one-file-back"></span>Trả phiếu KHDN</a>
        </li>
        <li v-if="tsbtnChuyenVip.visible" :class="{ disabledInput: !tsbtnChuyenVip.enabled }" @click="tsbtnChuyenVip_Click">
          <a> <span class="icon one-file-arrow-right"></span>Chuyển Vip</a>
        </li>
        <li v-if="tsbtnXemHS.visible" :class="{ disabledInput: !tsbtnXemHS.enabled }" @click="tsbtnXemHS_Click">
          <a> <span class="icon one-file-search"></span>Xem hồ sơ</a>
        </li>
        <!-- A Dương bảo bỏ -->
        <li v-if="tsbXemCMS.visible" :class="{ disabledInput: !tsbXemCMS.enabled }" @click="tsbXemCMS_Click">
          <a> <span class="icon one-file-search"></span>{{ tsbXemCMS.value }}</a>
        </li>
        <li v-if="tsbtnNhapMoiTT.visible" :class="{ disabledInput: !tsbtnNhapMoiTT.enabled }" @click="tsbtnNhapMoiTT_Click">
          <a> <span class="icon one-clipart-edit"></span>Nhập mới TT</a>
        </li>
        <li v-if="tsbtnTC_CTTC.visible" :class="{ disabledInput: !tsbtnTC_CTTC.enabled }" @click="tsbtnTC_CTTC_Click">
          <a> <span class="icon one-file-search"></span>Tra cứu phiếu TC</a>
        </li>

        <li v-if="tsbXemHd_thuebao_ct.visible" :class="{ disabledInput: !tsbXemHd_thuebao_ct.enabled }" @click="tsbtnXemHd_thuebao_ct_Click">
          <a> <span class="icon one-file-search"></span>{{ tsbXemHd_thuebao_ct.value }}</a>
        </li>

        <li v-if="btnUploadFileOneBussiness.visible" :class="{ disabledInput: !btnUploadFileOneBussiness.enabled }" @click="btnUploadFileOneBussiness_Click">
          <input
            style="display: none"
            type="file"
            accept="audio/*, video/*, image/* ,application/pdf, application/vnd.ms-excel, application/vnd.ms-excel.sheet.macroEnabled.1, application/vnd.openxmlformats-officedocument.spreadsheetml.sheet, text/xml"
            multiple="multiple"
            class="file"
            ref="files"
            id="upload-file"
            @change="btnUploadFileOneBussiness_FileHandler"
          />
          <a> <span class="icon fa fa-cloud-upload"></span>UploadFile</a>
        </li>
        <li v-if="tsbtnImportIDC.visible" :class="{ disabledInput: !tsbtnImportIDC.enabled }" @click="tsbtnImportIDC_Click">
          <a> <span class="icon one-reload1"></span>Import file IDC</a>
        </li>
        <li v-if="tsbXemTbi.visible" :class="{ disabledInput: !tsbXemTbi.enabled }" @click="tsbXemTbi_Click">
          <a> <span class="icon one-file-search"></span>Xem TBI</a>
        </li>
        <!-- <li v-if="tsbXemTbi.visible" :class="{ disabledInput: !tsbXemTbi.enabled }" @click="tsbtnTest_Click">
          <a> <span class="icon one-file-search"></span>test</a>
        </li> -->
      </ul>
    </div>
    <div class="page-content" style="margin: 20px 0 0 0">
      <div class="box-form row-1-height">
        <!-- <div class="section-title-wrapper" style="margin: 0 0 10px 0;"> -->
<!--        <div class="legend-title">-->
<!--          <span class="fa icon fa-angle-up" :class="{ 'fa-angle-up': sectionThongTinThueBao.isVisible, 'fa-angle-down': !sectionThongTinThueBao.isVisible }" @click="sectionThongTinThueBao.isVisible = !sectionThongTinThueBao.isVisible"></span>-->
<!--          Thông tin thuê bao-->
<!--        </div>-->
        <div class="legend-title" style="display: flex; justify-content: space-between; align-items: center;">
          <div>
            <span class="fa icon fa-angle-up"
            :class="{ 'fa-angle-up': sectionThongTinThueBao.isVisible, 'fa-angle-down': !sectionThongTinThueBao.isVisible }"
            @click="sectionThongTinThueBao.isVisible = !sectionThongTinThueBao.isVisible"></span>
            Thông tin thuê bao
          </div>

          <!-- Phần mới thêm: đoạn text và button ở góc phải -->
          <div>
            <div style="display: flex; align-items: center;" v-if="dtSLAs.error_code == 200">
              <span style="margin-right: 10px;">
                Thời gian còn:
                {{ dtSLAs.duration.hours }}:{{ dtSLAs.duration.minutes }}:{{ dtSLAs.duration.seconds }}
                <i class="nc-icon-glyph ui-2_time"
                   :style="{ color: dtSLAs.status === 'open' ? '#bae7ff' : '#da3545' }"></i>
              </span>

              <span style="margin-right: 10px;">SLA - Thời hạn xử lý phiếu: {{dtSLAs.sla_time / 60}}h</span>
              <button class="btn btn-primary"  @click="show_popupSLAs">Chi tiết</button>
            </div>
          </div>
        </div>

        <!-- </div> -->
        <div class="row" v-show="sectionThongTinThueBao.isVisible">
          <div class="col-sm-6 col-12">
            <div class=" ">
              <div class="row">
                <div class="col-sm-6 col-12">
                  <div class="info-row">
                    <div class="key w100">Mã GD bán</div>
                    <div class="value">
                      <input :disabled="!txtMaGD.enabled" v-model="txtMaGD.value" type="text" class="form-control bold" />
                    </div>
                  </div>
                </div>
                <div class="col-sm-6 col-12">
                  <div class="info-row">
                    <div class="key w100">Mã GD thi công</div>
                    <div class="value">
                      <input :disabled="!txtMaGDTC.enabled" v-model="txtMaGDTC.value" type="text" class="form-control bold" />
                    </div>
                  </div>
                </div>
                <div class="col-sm-6 col-12">
                  <div class="info-row">
                    <div class="key w100">Mã KH</div>
                    <div class="value">
                      <input :disabled="!txtMaKH.enabled" v-model="txtMaKH.value" type="text" class="form-control bold" />
                    </div>
                  </div>
                </div>
                <div class="col-sm-6 col-12">
                  <div class="info-row">
                    <div class="key w100">NV Lập HĐ</div>
                    <div class="value">
                      <input :disabled="!txtNVLapHD.enabled" v-model="txtNVLapHD.value" type="text" class="form-control bold" />
                    </div>
                  </div>
                </div>
                <div class="col-sm-12 col-12">
                  <div class="info-row">
                    <div class="key w100">Tên KH</div>
                    <div class="value">
                      <input :disabled="!txtTenKH.enabled" v-model="txtTenKH.value" type="text" class="form-control bold" />
                    </div>
                  </div>
                </div>
                <div class="col-sm-12 col-12">
                  <div class="info-row">
                    <div class="key w100">Địa chỉ KH</div>
                    <div class="value">
                      <input :disabled="!txtDiaChiKH.enabled" v-model="txtDiaChiKH.value" type="text" class="form-control bold" />
                    </div>
                  </div>
                </div>
                <div class="col-sm-6 col-12">
                  <div class="info-row">
                    <div class="key w100">Người ĐD</div>
                    <div class="value">
                      <input :disabled="!txtNguoiDD.enabled" v-model="txtNguoiDD.value" type="text" class="form-control bold" />
                    </div>
                  </div>
                </div>
                <div class="col-sm-6 col-12">
                  <div class="info-row"></div>
                </div>
                <div class="col-sm-6 col-12">
                  <div class="info-row">
                    <div class="key w100">SĐT KH</div>
                    <div class="value">
                      <input :disabled="!txtTelKH.enabled" v-model="txtTelKH.value" type="number" class="form-control bold" />
                    </div>
                  </div>
                </div>
                <div class="col-sm-6 col-12">
                  <div class="info-row">
                    <div class="key w100">Ngày lập HĐ</div>
                    <div class="value">
                      <vue-date :disable="!dtpNgayLapHD.enabled" v-model="dtpNgayLapHD.value" format="DD/MM/YYYY" type="datetime"></vue-date>
                    </div>
                  </div>
                </div>
                <div class="col-sm-6 col-12">
                  <div class="info-row">
                    <div class="key w100">Ngày YC</div>
                    <div class="value">
                      <vue-date :disable="!dtpNgayYeuCau.enabled" v-model="dtpNgayYeuCau.value" format="DD/MM/YYYY" type="datetime"></vue-date>
                    </div>
                  </div>
                </div>
                <div class="col-sm-6 col-12">
                  <div class="info-row">
                    <div class="key w100">Chức vụ</div>
                    <div class="value">
                      <input :disabled="!txtChucVu.enabled" v-model="txtChucVu.value" type="text" class="form-control bold" />
                    </div>
                  </div>
                </div>
                <div class="col-sm-6 col-12">
                  <div class="info-row">
                    <div class="key w100">MST KH</div>
                    <div class="value">
                      <input :disabled="!txtMST_KH.enabled" v-model="txtMST_KH.value" type="text" class="form-control bold" />
                    </div>
                  </div>
                </div>
                <div class="col-sm-6 col-12">
                  <div class="info-row">
                    <div class="key w100">Email KH</div>
                    <div class="value">
                      <input :disabled="!txtEmailKH.enabled" v-model="txtEmailKH.value" type="text" class="form-control bold" />
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>
          <div class="col-sm-6 col-12">
            <div class="">
              <div class="row">
                <div class="col-sm-6 col-12">
                  <div class="info-row">
                    <div class="key w100">Dịch vụ</div>
                    <div class="value">
                      <SelectExt :disabled="!cboDichVuVT.enabled" class="" v-model="cboDichVuVT.value" :dataSource="cboDichVuVT.list" dataTextField="TEN_DVVT" dataValueField="DICHVUVT_ID" />
                    </div>
                  </div>
                </div>
                <div class="col-sm-6 col-12">
                  <div class="info-row">
                    <div class="key w100">Loại hình</div>
                    <div class="value">
                      <SelectExt :disabled="!cboLoaihinhTB.enabled" class="" v-model="cboLoaihinhTB.value" :dataSource="cboLoaihinhTB.list" dataTextField="MA_LHTB" dataValueField="LOAITB_ID"></SelectExt>
                    </div>
                  </div>
                </div>
                <div class="col-sm-6 col-12">
                  <div class="info-row">
                    <div class="key w100">Kiểu LĐ</div>
                    <div class="value">
                      <input :disabled="!txtKieuLD.enabled" v-model="txtKieuLD.value" type="text" class="form-control bold" />
                    </div>
                  </div>
                </div>
                <div class="col-sm-6 col-12">
                  <div class="info-row">
                    <div class="key w100">Mã TB thi công</div>
                    <div class="value">
                      <input :disabled="!txtMaTC.enabled" v-model="txtMaTC.value" type="text" class="form-control bold" />
                    </div>
                  </div>
                </div>
                <div class="col-sm-6 col-12">
                  <div class="info-row">
                    <div class="key w100">Tên TB</div>
                    <div class="value">
                      <input :disabled="!txtTenTB.enabled" v-model="txtTenTB.value" type="text" class="form-control bold" />
                    </div>
                  </div>
                </div>
                <div class="col-sm-6 col-12">
                  <div class="info-row">
                    <div class="key w100">Ngày sinh</div>
                    <div class="value">
                      <vue-date :disable="!txtNgaySNTB.enabled" v-model="txtNgaySNTB.value" format="DD/MM/YYYY" type="datetime"></vue-date>
                    </div>
                  </div>
                </div>
                <div class="col-sm-12 col-12">
                  <div class="info-row">
                    <div class="key w100">Địa chỉ TB</div>
                    <div class="value">
                      <input :disabled="!txtDiaChiTB.enabled" v-model="txtDiaChiTB.value" type="text" class="form-control bold" />
                    </div>
                  </div>
                </div>
                <div class="col-sm-12 col-12">
                  <div class="info-row">
                    <div class="key w100">Địa chỉ LĐ</div>
                    <div class="value">
                      <input :disabled="!txtDiaChiLD.enabled" v-model="txtDiaChiLD.value" type="text" class="form-control bold" />
                    </div>
                  </div>
                </div>
                <div class="col-sm-6 col-12">
                  <div class="info-row">
                    <div class="key w100">Đối tượng</div>
                    <div class="value">
                      <input :disabled="!txtDoituongTB.enabled" v-model="txtDoituongTB.value" type="text" class="form-control bold" />
                    </div>
                  </div>
                </div>
                <div class="col-sm-6 col-12">
                  <div class="info-row">
                    <div class="key w100">Ngày hết hạn TD</div>
                    <div class="value">
                      <vue-date :disable="!txtNgayHHTD.enabled" v-model="txtNgayHHTD.value" format="DD/MM/YYYY" type="datetime" />
                    </div>
                  </div>
                </div>
                <div class="col-sm-12 col-12">
                  <div class="info-row">
                    <div class="key w100">Ghi chú</div>
                    <div class="value">
                      <input :disabled="!txtGhiChuTB.enabled" v-model="txtGhiChuTB.value" type="text" class="form-control bold" />
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
      <div class="box-form">
        <div class="legend-title">
          <span class="fa icon fa-angle-up" :class="{ 'fa-angle-up': sectionDanhSachThueBao.isVisible, 'fa-angle-down': !sectionDanhSachThueBao.isVisible }" @click="sectionDanhSachThueBao.isVisible = !sectionDanhSachThueBao.isVisible"></span>Danh sách thuê bao + thông số kỹ thuật
        </div>
        <!-- popupContainerControl1.visible = !popupContainerControl1.visible -->
        <div v-show="sectionDanhSachThueBao.isVisible" class="row">
          <div class="col-sm-9 col-12">
            <div class="my-4">
              <div :disabled="!popupContainerEdit1.enabled" type="text" style="white-space: nowrap; overflow: hidden; text-overflow: ellipsis" class="form-control bold" @click="popupContainerControl1.visible = !popupContainerControl1.visible">{{ popupContainerEdit1.value }}</div>
              <div style="position: relative">
                <div v-show="popupContainerControl1.visible" class="box-form popupContainerControl1">
                  <div class="row">
                    <div class="col-sm-12 col-12">
                      <div class="info-row">
                        <div class="key w100">
                          <input id="chkLoc" :disabled="!chkLoc.enabled" v-model="chkLoc.value" type="checkbox" class="check" @change="chkLoc_CheckedChanged" />
                        </div>
                        <div class="value">
                          <label for="chkLoc">Lọc theo nhân viên được giao việc</label>
                        </div>
                      </div>
                    </div>
                    <div class="col-sm-12 col-12">
                      <div class="info-row">
                        <div class="key w100">Trạng thái HĐ</div>
                        <div class="value">
                          <SelectExt :disabled="!cboTrangThaiHD.enabled" class="" v-model="cboTrangThaiHD.value" :dataSource="cboTrangThaiHD.list" dataTextField="name" dataValueField="id" @select="cboTrangThaiHD_SelectedValueChanged" />
                        </div>
                      </div>
                    </div>
                    <div class="col-sm-12 col-12">
                      <div class="info-row">
                        <div class="key w100">Giao việc</div>
                        <div class="value">
                          <SelectExt :disabled="!cboGV.enabled" class="" v-model="cboGV.value" :dataSource="cboGV.list" dataTextField="name" dataValueField="id" @select="cboGV_SelectedValueChanged" />
                        </div>
                      </div>
                    </div>
                    <div class="col-sm-12 col-12">
                      <div class="info-row">
                        <div class="key w100">Trạng thái phiếu</div>
                        <div class="value">
                          <SelectExt :disabled="!cboTrangThaiP.enabled" class="" v-model="cboTrangThaiP.value" :dataSource="cboTrangThaiP.list" dataTextField="name" dataValueField="id" @select="cboTrangThaiP_SelectedValueChanged" />
                        </div>
                      </div>
                    </div>
                    <div class="col-sm-12 col-12">
                      <div class="info-row">
                        <div class="key w100">Giao VIP</div>
                        <div class="value">
                          <SelectExt :disabled="!cboVip.enabled" class="" v-model="cboVip.value" :dataSource="cboVip.list" dataTextField="ten_dv" dataValueField="donvi_id" @select="cboVip_SelectedValueChanged" />
                        </div>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
            </div>

            <ejs-grid
              ref="gridDanhSach"
              locale="vi-VN"
              :dataSource="gridDanhSach.list"
              :allowFiltering="true"
              :allowSorting="true"
              :allowPaging="true"
              :allowResizing="true"
              :filterSettings="{ showFilterBarOperator: false, mode: 'Immediate', immediateModeDelay: 500 }"
              :height="300"
              :pageSettings="pageSettings"
              gridLines="Both"
              :allowSelection="true"
              :selectionSettings="{ mode: 'Both', type: 'Mutiple', enableToggle: false, checkboxOnly: false, persistSelection: false, checkboxMode: 'ResetOnRowClick' }"
              @rowSelected="gvDanhSach_FocusedRowChanged"
              @selectedItemsChanged="gridDanhSachSelectedItemsChanged"
            >
              <e-columns>
                <e-column type="checkbox" width="39" maxWidth="39" minWidth="39" headerTextAlign="center" textAlign="center" :customAttributes="{ class: 'column-check-selected' }" :freeze="true" :allowResizing="false" clipMode="Ellipsis"></e-column>
                <!-- <e-column width="30" field="index" headerText="" :allowFiltering="false" :allowEditing="false"></e-column> -->
                <e-column field="ma_gd" headerText="Mã giao dịch bán" :filter="{ operator: 'contains' }"></e-column>
                <e-column field="ma_tb" headerText="Mã thuê bao bán" :filter="{ operator: 'contains' }"></e-column>
                <e-column field="ten_tb" headerText="Tên thuê bao" :filter="{ operator: 'contains' }"></e-column>
                <e-column field="ten_kieuld" headerText="Kiểu lắp đặt" :filter="{ operator: 'contains' }"></e-column>
                <!-- <e-column field="ngaygiao" headerText="Ngày giao" format="dd/MM/yyyy hh:mm:ss" type="date" :filter="{ operator: 'equal' }" :sortComparer="gridDanhSachNgayGiaoSortComparer" :filterBarTemplate="filterBarTemplate"></e-column> -->
                <e-column field="ngaygiao" headerText="Ngày giao" format="dd/MM/yyyy hh:mm:ss" :filter="{ operator: 'contains' }" :sortComparer="gridDanhSachNgayGiaoSortComparer"></e-column>
                <e-column field="vipxl" headerText="Vip xử lý" :filter="{ operator: 'contains' }"></e-column>
                <e-column field="ten_tinh" headerText="Tỉnh quản lý HĐ" :filter="{ operator: 'contains' }"></e-column>
                <e-column field="tinh_ld" headerText="Tỉnh LĐ" :filter="{ operator: 'contains' }"></e-column>
                <e-column field="ten_loaihd" headerText="Loại HĐ" :filter="{ operator: 'contains' }"></e-column>
                <e-column field="sohd_khdn" headerText="Số hợp đồng KHDN" :filter="{ operator: 'contains' }"></e-column>
                <e-column field="ten_dvvt" headerText="Dịch vụ" :filter="{ operator: 'contains' }"></e-column>
                <e-column field="loaihinh_tb" headerText="Loại hình" :filter="{ operator: 'contains' }"></e-column>
                <e-column field="ma_vnpt" headerText="Mã VNPT" :filter="{ operator: 'contains' }"></e-column>
                <e-column field="diachi_kh" headerText="Địa chỉ khách hàng" :filter="{ operator: 'contains' }"></e-column>
                <e-column field="diachi_ld" headerText="Địa chỉ lắp đặt" :filter="{ operator: 'contains' }"></e-column>
                <e-column field="ngay_cn" headerText="Ngày yêu cầu" format="dd/MM/yyyy hh:mm:ss" type="date" :filter="{ operator: 'contains' }"></e-column>
                <e-column field="so_dt_giao" headerText="ĐT NV giao" :filter="{ operator: 'contains' }"></e-column>
                <e-column field="ten_donvi_nhan" headerText="Đơn vị nhận" :filter="{ operator: 'contains' }"></e-column>
                <e-column field="huong_kn" headerText="Hướng KN" :filter="{ operator: 'contains' }"></e-column>
                <e-column field="ten_donvi_giao" headerText="Đơn vị giao" :filter="{ operator: 'contains' }"></e-column>
              </e-columns>
            </ejs-grid>
            <!-- <DDataGrid
              ref="gridDanhSach"
              panelDataHeight="350"
              v-bind:columns="gridDanhSach.cols"
              :enabledSelectFirstRow="false"
              :showColumnCheckbox="false"
              v-bind:dataSource="gridDanhSach.list"
              :showFilter="true"
              :allowPaging="true"
              :enablePagingServer="false"
              :totalRecords="gridDanhSach.list.length"
              :selectionSettings="{ enableToggle: false, checkboxOnly: false, checkboxMode: 'ResetOnRowClick' }"
              @rowSelected="gvDanhSach_FocusedRowChanged"
              @actionComplete="gridDanhSachActionComplete"
            /> -->
          </div>
          <div class="col-sm-3 col-12">
            <div class="section-title-through-wrapper" style="margin: 0 0 0 0">
              <div style="display: flex; justify-content: space-between; align-items: center">
                <div class="section-title-through-text" style="margin-right: 15px">Thông số kỹ thuật</div>
                <div v-if="tsbtnHoanThanh.visible" class="btn" @click="btnSuaTSKT_Clicked" v-b-modal.id-modal-capnhat-cntt><span class="-ap icon-settings"></span></div>
              </div>
              <div class="line-through"></div>
            </div>
            <div style="flex-grow: 1; background: white; margin: 0 0 10px 0;">
              <div ref="wbrTSKT" v-html="wbrTSKT.value" class="info-wrapper" @click="handleLineClick"></div>
            </div>
          </div>
        </div>
      </div>
      <div class="box-form">
        <div class="row">
          <div class="col-sm-8 col-12">
            <section style="margin-top: 15px; flex-grow: 1; background: transparent">
              <ejs-tab ref="tabObj" id="tab_wizard" heightAdjustMode="None" height="auto" @selected="onTabSelected">
                <e-tabitems>
                  <e-tabitem v-for="(item, index) in tabList" :key="index" :header="item.header" :content="item.content"></e-tabitem>
                </e-tabitems>
              </ejs-tab>
              <div id="gridGiaoviec" style="display: none">
                <DataGrid panelDataHeight="120" v-bind:columns="gridGiaoviec.cols" :showColumnCheckbox="false" v-bind:dataSource="gridGiaoviec.list" :showFilter="true" :allowPaging="true" :enablePagingServer="false" :totalRecords="gridGiaoviec.list.length"> </DataGrid>
              </div>
              <div id="gridDangkyDV" style="display: none">
                <DataGrid v-bind:columns="gridDangkyDV.cols" :showColumnCheckbox="false" v-bind:dataSource="gridDangkyDV.list" :showFilter="true" :allowPaging="true" :enablePagingServer="false" :totalRecords="gridDangkyDV.list.length" />
              </div>
              <div id="gridDanhSachThuocTinh" style="display: none">
                <DataGrid v-bind:columns="gridDanhSachThuocTinh.cols" :showColumnCheckbox="false" v-bind:dataSource="gridDanhSachThuocTinh.list" :showFilter="true" :allowPaging="true" :enablePagingServer="false" :totalRecords="gridDanhSachThuocTinh.list.length" />
              </div>
              <div id="gridGiaoviec2" style="display: none">
                <DataGrid v-bind:columns="gridGiaoviec2.cols" :showColumnCheckbox="false" v-bind:dataSource="gridGiaoviec2.list" :showFilter="true" :allowPaging="true" :enablePagingServer="false" :totalRecords="gridGiaoviec2.list.length" />
              </div>
              <div id="gridTienTrinh" style="display: none">
                <DataGrid v-bind:columns="gridTienTrinh.cols" :showColumnCheckbox="false" v-bind:dataSource="gridTienTrinh.list" :showFilter="true" :allowPaging="true" :enablePagingServer="false" :totalRecords="gridTienTrinh.list.length" @rowSelected="gridTienTrinhRowSelected" />
              </div>
              <div id="gridDanhSachThietBi" style="display: none">
                <DataGrid v-bind:columns="grcThietBiThuMotLan.cols" :showColumnCheckbox="false" v-bind:dataSource="grcThietBiThuMotLan.list" :showFilter="true" :allowPaging="true" :enablePagingServer="false" :totalRecords="grcThietBiThuMotLan.list.length" />
              </div>
              <div id="gridThongTinNhanVienQuanLy" style="display: none">
                <DataGrid v-bind:columns="gridThongTinNhanVienQuanLy.cols" :showColumnCheckbox="false" v-bind:dataSource="gridThongTinNhanVienQuanLy.list" :showFilter="true" :allowPaging="true" :enablePagingServer="false" :totalRecords="gridThongTinNhanVienQuanLy.list.length" />
              </div>
            </section>
          </div>
          <div class="col-sm-4 col-12">
            <div>
              <div class="legend-title">Thông tin giao việc nhân viên</div>
              <section>
                <div class="row">
                  <div class="col-sm-6 col-12">
                    <div class="info-row">
                      <div class="key w80">Người GV</div>
                      <div class="value">
                        <div class="select-custom">
                          <SelectExt :disabled="!cboNguoiGV.enabled" class="" v-model="cboNguoiGV.value" :dataSource="cboNguoiGV.list" dataTextField="ten_nv" dataValueField="nhanvien_id" />
                        </div>
                      </div>
                    </div>
                  </div>
                  <div class="col-sm-6 col-12">
                    <div class="info-row">
                      <div class="key w80">ND giao</div>
                      <div class="value">
                        <input :disabled="!txtNDGiao.enabled" v-model="txtNDGiao.value" type="text" class="form-control bold" />
                      </div>
                    </div>
                  </div>
                  <div class="col-sm-6 col-12">
                    <div class="info-row">
                      <div class="key w80">Nội dung TH</div>
                      <div class="value">
                        <div class="input-more-button">
                          <div
                            class="form-control bold"
                            @click="
                              () => {
                                puceNDTH_drop.visible = !puceNDTH_drop.visible
                                puceNDTH_edit.visible = !puceNDTH_edit.visible
                              }
                            "
                          >
                            <div :disabled="!puceNDTH.enabled" type="text" style="max-width: 100px; overflow: hidden; text-overflow: ellipsis; white-space: nowrap">
                              {{ puceNDTH_edit.value }}
                            </div>
                          </div>
                          <div style="position: relative">
                            <div v-show="puceNDTH_edit.visible" style="width: 200px" class="box-form popupContainerControl1">
                              <div class="row">
                                <div class="col-sm-12 col-12">
                                  <div class="info-row">
                                    <textarea :disabled="!puceNDTH_edit.enabled" v-model="puceNDTH_edit.value" name="" class="form-control" style="height: 100px; resize: none"></textarea>
                                  </div>
                                </div>
                              </div>
                            </div>
                          </div>
                          <button class="btn" @click="btnNDTH_Click">
                            <span class="-ap icon-more_horiz"></span>
                          </button>
                        </div>
                      </div>
                    </div>
                  </div>
                  <div class="col-sm-6 col-12">
                    <div class="info-row">
                      <div class="key w80">ND trả</div>
                      <div class="value">
                        <input :disabled="!txtThongTinTra.enabled" v-model="txtThongTinTra.value" type="text" class="form-control bold" />
                      </div>
                    </div>
                  </div>
                  <div class="col-sm-6 col-12">
                    <div class="info-row">
                      <div class="key w80">Lý do chậm</div>
                      <div class="value">
                        <input :disabled="!txtLyDoTra.enabled" v-model="txtLyDoTra.value" type="text" class="form-control bold" />
                      </div>
                    </div>
                  </div>
                  <div class="col-sm-6 col-12">
                    <div class="info-row">
                      <div class="key w80" style="display: flex; justify-content: center; align-items: center">
                        Ngày GV
                        <div class="check-action" style="margin: 0 0 0 5px">
                          <input :disabled="!chkNgayGV.enabled" v-model="chkNgayGV.value" type="checkbox" class="check" />
                          <span class="name"></span>
                        </div>
                      </div>
                      <div class="value">
                        <vue-date :disable="!dtpNgayGV.enabled" v-model="dtpNgayGV.value" format="DD/MM/YYYY" type="datetime" />
                      </div>
                    </div>
                  </div>
                  <div class="col-sm-6 col-12">
                    <div class="info-row">
                      <div class="key w80">Nhân viên</div>
                      <div class="value">
                        <input :disabled="!txtNhanVien.enabled" v-model="txtNhanVien.value" type="text" class="form-control bold" />
                      </div>
                    </div>
                  </div>
                  <div class="col-sm-6 col-12">
                    <div class="info-row">
                      <div class="key w80" style="display: flex; justify-content: center; align-items: center">
                        Ngày HT
                        <div class="check-action" style="margin: 0 0 0 5px">
                          <input :disabled="!chkNgayBG.enabled" v-model="chkNgayBG.value" type="checkbox" class="check" />
                          <span class="name"></span>
                        </div>
                      </div>
                      <div class="value">
                        <vue-date :disable="!dtpNgayBG.enabled" v-model="dtpNgayBG.value" format="DD/MM/YYYY" type="datetime" />
                      </div>
                    </div>
                  </div>
                  <div class="col-sm-12 col-12">
                    <div class="info-row">
                      <div class="key w80" style="color: red">Liên hệ</div>
                      <div class="value">
                        <textarea :disabled="!lblLienHe.enabled" v-model="lblLienHe.value" name="" class="form-control" style="height: 100px; resize: none"></textarea>
                      </div>
                    </div>
                  </div>
                </div>
              </section>
            </div>
          </div>
        </div>
      </div>
    </div>
    <ejs-dialog :enableResize="true" :visible="false" ref="popupfrnGiaoPhieuNV" :header="'Giao phiếu nhân viên'" showCloseIcon="true" target="#frmGiaoViecVIP" width="90%" height="800px" :allowDragging="true">
      <frnGiaoPhieuNV ref="frnGiaoPhieuNV" @giaoviec_success="giaoviec_success" />
    </ejs-dialog>
    <frmCreateIssue ref="frmCreateIssue" />
    <!-- <frmDanhSachSoThanhVien ref="frmDanhSachSoThanhVien" v-bind="frmDanhSachSoThanhVien.input" /> -->
    <ejs-dialog
      ref="ejsDialog"
      :visible="false"
      :isModal="true"
      target="#frmGiaoViecVIP"
      :enableResize="true"
      :resizeHandles="['All']"
      :header="ejsDialog.header"
      :showCloseIcon="true"
      :width="ejsDialog.config.width"
      :height="ejsDialog.config.height"
      :minHeight="ejsDialog.config.minHeight"
      :allowDragging="true"
      :position="ejsDialog.config.position"
      :animationSettings="ejsDialog.config.animationSettings"
      @beforeOpen="ejsDialogBeforeOpen"
      @beforeClose="ejsDialogBeforeClose"
    >
      <div v-if="ejsDialog.name" :key="uuidv4()" style="overflow-y: scroll">
        <frmTraPhieuTC v-if="ejsDialog.name == 'frmTraPhieuTC'" ref="frmTraPhieuTC" v-bind="frmTraPhieuTC.input" />
        <frmNoiDung v-if="ejsDialog.name == 'frmNoiDung'" ref="frmNoiDung" v-bind="frmNoiDung.input" @btnGhiLai_Clicked="frmNoiDung_btnGhiLai_Clicked" />
        <frmTraCuuPhieuTCBC v-if="ejsDialog.name == 'frmTraCuuPhieuTCBC'" ref="frmTraCuuPhieuTCBC" v-bind="frmTraCuuPhieuTCBC.input" />
        <frmDanhSachThueBaoCaiTaoHaTang_IDC v-if="ejsDialog.name == 'frmDanhSachThueBaoCaiTaoHaTang_IDC'" ref="frmDanhSachThueBaoCaiTaoHaTang_IDC" v-bind="frmDanhSachThueBaoCaiTaoHaTang_IDC.input" />
        <frmDanhSachSoThanhVien v-if="ejsDialog.name == 'frmDanhSachSoThanhVien'" ref="frmDanhSachSoThanhVien" v-bind="frmDanhSachSoThanhVien.input" />
        <frmNhapText v-if="ejsDialog.name == 'frmNhapText'" ref="frmNhapText" v-bind="frmNhapText.input" @tsbtnXacNhan_Clicked="frmNhapText_tsbtnXacNhan_Clicked" @tsbtnHuy_Clicked="frmNhapText_tsbtnHuy_Clicked" />
        <frmTraPhieuVIP v-if="ejsDialog.name == 'frmTraPhieuVIP'" ref="frmTraPhieuVIP" v-bind="frmTraPhieuVIP.input" @onBtnTraphieuClicked="frmTraPhieuVIP_onBtnTraphieuClicked" />
        <frmTraPhieuCNTT v-if="ejsDialog.name == 'frmTraPhieuCNTT'" @traphieuit_close="traphieuit_close" @reload_grid="tsbtnLayTT_Click" ref="frmTraPhieuCNTT_IT" v-bind="frmTraPhieuCNTT.input"></frmTraPhieuCNTT>
        <frmDanhSachThietBI_IDC v-if="ejsDialog.name == 'frmDanhSachThietBI_IDC'" ref="frmDanhSachThietBI_IDC" v-bind="frmDanhSachThietBI_IDC.input" />
        <frmImportThietBi_IDC v-if="ejsDialog.name == 'frmImportThietBi_IDC'" ref="frmImportThietBi_IDC" v-bind="frmImportThietBi_IDC.input" />
        <frmGanSimVaoKenh v-if="ejsDialog.name == 'frmGanSimVaoKenh'" ref="frmGanSimVaoKenh" :dulieu="popupComponentData" />
        <frmGiaoPhieuNV v-if="ejsDialog.name == 'frmGiaoPhieuNV'" ref="frmGiaoPhieuNV" v-bind="frmGiaoPhieuNV.input" />
      </div>
    </ejs-dialog>
    <frmTraCuuHoSoHDTB ref="frmTraCuuHoSoHDTB" />
    <frmDSHoSoBC ref="frmDSHoSoBC" v-bind="frmDSHoSoBC.input" />
    <frmDoiSoatCA ref="frmDoiSoatCA" />
    <frmCapnhatCNTT
      ref="frmCapnhatCNTT"
      modal-id="id-modal-capnhat-cntt"
      :tinh-id="gridDanhSach && gridDanhSach.value ? gridDanhSach.value.tinh_id : 0"
      :hdtb-id="gridDanhSach && gridDanhSach.value ? gridDanhSach.value.hdtb_id : 0"
      :huong-giao-id="huonggiao_id"
      :parent="this"
      @xacnhan="frmCapnhatCNTT_XacNhan"
    />
    <frmThongTinYeuCauVIP ref="frmThongTinYeuCauVIP" />
    <frmNhapDSThanhVienNgoaiMang ref="frmNhapDSThanhVienNgoaiMang" />
    <div><frmGiaoPhieuCNTT @emit_function="emit_function" @reload_grid="tsbtnLayTT_Click" ref="frmGiaoPhieuCNTT_it"></frmGiaoPhieuCNTT></div>

    <b-modal id="popupComponents" ref="popupComponents" size="xl" hide-footer :no-close-on-backdrop="true" body-class="modal-body p-0">
      <component @emit_func="emit_func" :is="popupComponent" ref="generalPopup" :dulieu="popupComponentData" v-bind="popupComponentAttr" isPopup="true" v-on="popupComponentEvts"></component>
      <!--v-dynamic-events="popupComponentEvents"-->
    </b-modal>

    <ejs-dialog :enableResize="true" :visible="false" ref="popupSLAs" :header="'SLAs'"
                showCloseIcon="true" target="#frmGiaoViecVIP" width="50%" height="600px"
                :position="{X: 'right', Y: 'center'}"
                @close="close_popupSLAs"
                :allowDragging="true">
      <div>
        <frmSLAs v-if="isShow.frmSLAs" :dulieu="ppData.frmSLAs"/>
      </div>
    </ejs-dialog>
  </div>
</template>
<script>
import Vue from 'vue'
import moment from 'moment'
import api from './api'
import XLSX from 'xlsx'
import { DichVuVienThong, LoaiHopDong, TrangThaiHD, LoaiHinhTB } from './constants'
import breadcrumb from '@/components/breadcrumb'
import frnGiaoPhieuNV from '@/modules/contract/setup/DeclareLandline/popups/frnGiaoPhieuNV.vue'
import frmGiaoPhieuCNTT from './components/popupGiaoPhieuCNTT/PopupGiaoPhieuCNTT.vue'
import { TabPlugin } from '@syncfusion/ej2-vue-navigations'
//import another modules
Vue.use(TabPlugin)
import DateTimeHelper from '@/modules/QLVT/NhapXuatVT_v2/helpers/dateHelper'
import { onebssIdIsValid, convertToUpcaseKey, isNullOrEmpty, getDateStringOrDefault, generateComponentKey } from '@/modules/QLVT/NhapXuatVT_v2/helpers'
import CreateIssue from '@/modules/contract/complete_profile/CompleteProfileITVAS/popups/popupCreateIssue/CreateIssue'
import { DDataGrid, DDatePicker } from '@/modules/QLVT/NhapXuatVT_v2/components/common'

import { Page, Sort, Filter, Resize, Group, Freeze, CommandColumn, ExcelExport, Edit, Aggregate, ContextMenu, DetailRow, Toolbar, Selection } from '@syncfusion/ej2-vue-grids'
import { defineAsyncComponent } from '@vue/composition-api'

const frmNhapDSThanhVienNgoaiMang = () => import('@/modules/contract/setup/ChangeContractOwner/popup/frmNhapDSThanhVienNgoaiMang.vue')
export default {
  provide: {
    grid: [Page, Sort, Filter, Resize, Group, Freeze, CommandColumn, ExcelExport, Edit, Aggregate, ContextMenu, DetailRow, Toolbar, Selection]
  },
  components: {
    breadcrumb,
    DDataGrid,
    frnGiaoPhieuNV,
    frmGiaoPhieuCNTT,
    frmCreateIssue: () => import('@/modules/contract/complete_profile/CompleteProfileITVAS/popups/popupCreateIssue/CreateIssue.vue'),
    frmTraPhieuTC: () => import('@/modules/contract/setup/ReturnInstallTicket/frmTraPhieuTC.vue'),
    frmTraCuuHoSoHDTB: () => import('@/modules/contract/complete_profile/CompleteProfileITVAS/popups/popupTraCuuHoSoHDTB/TraCuuHoSoHDTB'),
    frmDSHoSoBC: () => import('@/modules/contract/setup/LookupNavigateContract/popup/FrmXemHS'),
    frmDoiSoatCA: () => import('@/modules/contract/complete_profile/CompleteProfileITVAS/popups/popupDoiSoatCA/DoiSoatCA'),
    frmNoiDung: () => import('./components/frmNoiDung'),
    frmTraCuuPhieuTCBC: () => import('@/modules/contract/setup/LookupDetailConstructionCrossSale/LookupDetailConstructionCrossSale'),
    frmDanhSachThueBaoCaiTaoHaTang_IDC: () => import('./components/frmDanhSachThueBaoCaiTaoHaTang_IDC'),
    frmDanhSachSoThanhVien: () => import('./components/frmDanhSachSoThanhVien_NET'),
    frmNhapText: () => import('./components/frmNhapText'),
    frmDanhSachThietBI_IDC: () => import('./components/frmDanhSachThietBI_IDC'),
    frmTraPhieuVIP: () => import('@/modules/admin/category/frmHoanCongIMS_Net/components/frmTraPhieuVIP'),
    frmThongTinYeuCauVIP: () => import('./components/frmThongTinYeuCauVIP'),
    frmImportThietBi_IDC: () => import('./components/frmImportThietBi_IDC'),
    frmTraPhieuCNTT: () => import('./components/popupGiaoPhieuCNTT/frmTraPhieuCntt.vue'),
    frmCapnhatCNTT: () => import('./components/frmCapnhatCNTT'),
    frmGiaoPhieuNV: () => import('./components/frmGiaoPhieuNV'),
    frmCapNhatTSKT_CNTT: () => import('@/modules/contract/complete_profile/CompleteProfileITVAS/popups/popupTraCuuHoSoHDTB/TraCuuHoSoHDTB'),
    frmNhapDSThanhVienNgoaiMang,
    frmGanSimVaoKenh: defineAsyncComponent(() => import('./components/frmGanSimVaoKenh/frmGanSimVAoKenhDisable.vue')),
    frmSLAs: defineAsyncComponent(() => import('./components/frmSLAs/index.vue')),
  },
  name: 'frmGiaoViecVIP',
  data: () => ({
    //grid
    pageSettings: { currentPage: 1, pageSize: 50, pageSizes: [10, 50, 100, 200, 500], pageCount: 5 },
    //
    localLoading: 0,
    header: {
      title: 'VIP giám sát thi công',
      list: [
        { name: 'Trang chủ', link: { name: 'Ui.cards' } },
        { name: 'Lập hợp đồng', link: { name: 'Ui.cards' } },
        { name: 'Lắp đặt mới', link: { name: 'Ui.cards' } }
      ]
    },
    tsbtnLayTT: { enabled: true, visible: true },
    tsbtnGiaoViec: { enabled: true, visible: true },
    tsbtnHoanThanh: { enabled: false, visible: true },
    tsbtnCreatIssue: { enabled: false, visible: true },
    tsbtnGiaoPhieu: { enabled: true, visible: false },
    tsbtnTraPhieuIT: { enabled: true, visible: false },
    tsbtnXemIT360: { enabled: true, visible: true },
    tsbtnTraPhieuTinh: { enabled: false, visible: true },
    tsbtnTraPhieuKHDN: { enabled: false, visible: true },
    tsbtnChuyenVip: { enabled: true, visible: true },
    tsbtnXemHS: { enabled: true, visible: true },
    tsbXemCMS: { enabled: true, visible: true, value: 'Xem CMS' },
    tsbXemHd_thuebao_ct: { enabled: true, visible: false, value: 'Xem chi tiết hợp đồng' },
    tsbtnNhapMoiTT: { enabled: true, visible: true },
    tsbtnTC_CTTC: { enabled: true, visible: true },
    btnSuaTSKT: { enabled: true, visible: true },
    btnUploadFileOneBussiness: { enabled: true, visible: true },
    tsbtnImportIDC: { enabled: true, visible: false },
    tsbXemTbi: { enabled: true, visible: true },
    sectionThongTinThueBao: { isVisible: true },
    sectionDanhSachThueBao: { isVisible: true },
    gridDanhSach: {
      list: [],
      cols: [
        { fieldName: 'ma_tb', headerText: 'Mã thuê bao bán', allowFiltering: true },
        { fieldName: 'ma_gd', headerText: 'Mã giao dịch bán', allowFiltering: true },
        { fieldName: 'vipxl', headerText: 'Vip xử lý', allowFiltering: true },
        { fieldName: 'ten_tinh', headerText: 'Tỉnh quản lý HĐ', allowFiltering: true },
        { fieldName: 'tinh_ld', headerText: 'Tỉnh LĐ', allowFiltering: true },
        { fieldName: 'ten_loaihd', headerText: 'Loại HĐ', allowFiltering: true },
        { fieldName: 'sohd_khdn', headerText: 'Số hợp đồng KHDN', allowFiltering: true },
        { fieldName: 'ten_dvvt', headerText: 'Dịch vụ', allowFiltering: true },
        { fieldName: 'loaihinh_tb', headerText: 'Loại hình', allowFiltering: true },
        { fieldName: 'ma_vnpt', headerText: 'Mã VNPT', allowFiltering: true }, //thieu
        { fieldName: 'ten_kieuld', headerText: 'Kiểu lắp đặt', allowFiltering: true },
        { fieldName: 'ten_tb', headerText: 'Tên thuê bao', allowFiltering: true },
        { fieldName: 'ngaygiao', headerText: 'Ngày giao', allowFiltering: true },
        { fieldName: 'diachi_kh', headerText: 'Địa chỉ khách hàng', allowFiltering: true },
        { fieldName: 'diachi_ld', headerText: 'Địa chỉ lắp đặt', allowFiltering: true },
        { fieldName: 'ngay_cn', headerText: 'Ngày yêu cầu', allowFiltering: true },
        { fieldName: 'ngaygiao', headerText: 'Ngày giao', allowFiltering: true },
        { fieldName: 'so_dt_giao', headerText: 'ĐT NV giao', allowFiltering: true },
        { fieldName: 'ten_donvi_nhan', headerText: 'Đơn vị nhận', allowFiltering: true },
        // { fieldName: 'diachi_ld', headerText: 'Hẹn từ', allowFiltering: true },
        // { fieldName: 'diachi_ld', headerText: 'Hẹn đến', allowFiltering: true },
        // { fieldName: 'diachi_ld', headerText: 'Nội dung hẹn', allowFiltering: true },
        { fieldName: 'huong_kn', headerText: 'Hướng KN', allowFiltering: true }, //thieu
        { fieldName: 'ten_donvi_giao', headerText: 'Đơn vị giao', allowFiltering: true }
      ],
      checked: []
    },
    gridDanhSachThuocTinh: {
      list: [],
      cols: [
        { fieldName: 'srv', headerText: 'STT', allowFiltering: true },
        { fieldName: 'ma_tb', headerText: 'Thuộc tính', allowFiltering: true }
      ],
      checked: []
    },
    gridGiaoviec2: {
      list: [],
      cols: [
        { fieldName: 'ten_nv', headerText: 'Tên nhân viên', allowFiltering: true },
        { fieldName: 'so_dt', headerText: 'Số điện thoại', allowFiltering: true },
        { fieldName: 'nhiemvu', headerText: 'Nhiệm vụ', allowFiltering: true },
        { fieldName: 'ghichu', headerText: 'Ghi chú', allowFiltering: true }
      ],
      checked: []
    },
    gridTienTrinh: {
      list: [],
      cols: [
        { width: 100, fieldName: 'nguoi_cn', headerText: 'Người CN', allowFiltering: true },
        { width: 100, fieldName: 'ngay_cn', headerText: 'Ngày CN', allowFiltering: true },
        { fieldName: 'noidung', headerText: 'Nội dung', allowFiltering: true },
        { fieldName: 'donvi_cn', headerText: 'Đơn vị', allowFiltering: true },
        { fieldName: 'sms', headerText: 'SMS', allowFiltering: true },
        { fieldName: 'sua', headerText: 'Sửa', allowFiltering: true },
        { fieldName: 'xoa', headerText: 'Xóa', allowFiltering: true }
      ],
      checked: []
    },
    gridDanhSachThietBi: {
      list: [],
      cols: [
        { fieldName: 'srv', headerText: 'Tên thiết bị/gói dịch vụ/thu một lần', allowFiltering: true },
        { fieldName: 'ma_tb', headerText: 'Số lượng', allowFiltering: true }
      ],
      checked: []
    },
    gridThongTinNhanVienQuanLy: {
      list: [],
      cols: [
        { fieldName: 'srv', headerText: 'Mã nhân viên', allowFiltering: true },
        { fieldName: 'ma_tb', headerText: 'Tên NV - SĐT', allowFiltering: true },
        { fieldName: 'ma_kh', headerText: 'Loại', allowFiltering: true },
        { fieldName: 'ten_tb', headerText: 'Loại NV', allowFiltering: true },
        { fieldName: 'ten_tb', headerText: 'Đơn vị', allowFiltering: true }
      ],
      checked: []
    },
    grcMacdinhTC: {
      visible: false,
      list: [],
      cols: [
        { fieldName: 'srv', headerText: 'Mã nhân viên', allowFiltering: true },
        { fieldName: 'ma_tb', headerText: 'Tên NV - SĐT', allowFiltering: true },
        { fieldName: 'ma_kh', headerText: 'Loại', allowFiltering: true },
        { fieldName: 'ten_tb', headerText: 'Loại NV', allowFiltering: true },
        { fieldName: 'ten_tb', headerText: 'Đơn vị', allowFiltering: true }
      ],
      checked: []
    },
    grcDS_NVQL: {
      visible: false,
      list: [],
      cols: [
        { fieldName: 'srv', headerText: 'Mã nhân viên', allowFiltering: true },
        { fieldName: 'ma_tb', headerText: 'Tên NV - SĐT', allowFiltering: true },
        { fieldName: 'ma_kh', headerText: 'Loại', allowFiltering: true },
        { fieldName: 'ten_tb', headerText: 'Loại NV', allowFiltering: true },
        { fieldName: 'ten_tb', headerText: 'Đơn vị', allowFiltering: true }
      ],
      checked: []
    },
    gridGiaoviec: {
      visible: false,
      list: [],
      cols: [
        { fieldName: 'ten_nv', headerText: 'Tên nhân viên', allowFiltering: true },
        { fieldName: 'so_dt', headerText: 'Số điện thoại', allowFiltering: true },
        { fieldName: 'nhiemvu', headerText: 'Nhiệm vụ', allowFiltering: true },
        { fieldName: 'ghichu', headerText: 'Ghi chú', allowFiltering: true }
      ],
      checked: []
    },
    gridDangkyDV: {
      cols: [
        { fieldName: 'ma_dvgt', headerText: 'Mã DVGT', allowFiltering: true },
        { fieldName: 'ten_dvgt', headerText: 'Tên dịch vụ', allowFiltering: true },
        { fieldName: 'kieu', headerText: 'Kiểu YC', allowFiltering: true },
        { fieldName: 'noidung', headerText: 'Nội dung/SL', allowFiltering: true }
      ],
      list: [],
      selected: [],
      value: null
    },
    grcThietBiThuMotLan: {
      visible: false,
      list: [],
      cols: [
        { fieldName: 'loai_tbi', headerText: 'Thiết bị', allowFiltering: true },
        { fieldName: 'soluong', headerText: 'Số lượng', allowFiltering: true },
        { fieldName: 'tien', headerText: 'Tiền', allowFiltering: true },
        { fieldName: 'vat', headerText: 'VAT', allowFiltering: true },
        { fieldName: 'tong_tien', headerText: 'Tổng tiền', allowFiltering: true }
      ],
      checked: []
    },
    grThuocTinh: {
      visible: false,
      list: [],
      cols: [
        { fieldName: 'srv', headerText: 'Mã nhân viên', allowFiltering: true },
        { fieldName: 'ma_tb', headerText: 'Tên NV - SĐT', allowFiltering: true },
        { fieldName: 'ma_kh', headerText: 'Loại', allowFiltering: true },
        { fieldName: 'ten_tb', headerText: 'Loại NV', allowFiltering: true },
        { fieldName: 'ten_tb', headerText: 'Đơn vị', allowFiltering: true }
      ],
      checked: []
    },
    tabList: [
      {
        header: {
          text: 'Danh sách nhân viên thực hiện'
        },
        content: '#gridGiaoviec'
      },
      {
        header: {
          text: 'Danh sách dịch vụ gia tăng'
        },
        content: '#gridDangkyDV'
      },
      {
        header: {
          text: 'Danh sách thuộc tính gói cước CNTT/GTGT'
        },
        content: '#gridDanhSachThuocTinh'
      },
      {
        header: {
          text: 'Danh sách nhân viên cùng thực hiện'
        },
        content: '#gridGiaoviec2'
      },
      {
        header: {
          text: 'Tiến trình thi công'
        },
        content: '#gridTienTrinh'
      },
      {
        header: {
          text: 'Thiết bị/gói dịch vụ/thu một lân'
        },
        content: '#gridDanhSachThietBi'
      },
      {
        header: {
          text: 'Thông tin nhân viên quản lý HĐ'
        },
        content: '#gridThongTinNhanVienQuanLy'
      }
    ],
    txtMaGD: { enabled: true, value: null },
    txtMaGDTC: { enabled: true, value: null },
    txtMaKH: { enabled: true, value: null },
    txtNVLapHD: { enabled: true, value: null },
    txtTenKH: { enabled: true, value: null },
    txtDiaChiKH: { enabled: true, value: null },
    txtNguoiDD: { enabled: false, value: null },
    txtLoaiKH: { enabled: false, value: null },
    txtTelKH: { enabled: false, value: null },
    dtpNgayLapHD: { enabled: false, value: null },
    dtpNgayYeuCau: { enabled: false, value: null },
    txtChucVu: { enabled: false, value: null },
    txtMST_KH: { enabled: true, value: null },
    txtEmailKH: { enabled: true, value: null },
    cboDichVuVT: { enabled: false, list: [], value: null },
    cboLoaihinhTB: { enabled: false, list: [], value: null },
    txtKieuLD: { enabled: true, value: null },
    txtMaTC: { enabled: true, value: null },
    txtTenTB: { enabled: true, value: null },
    txtNgaySNTB: { enabled: false, value: null },
    txtDiaChiTB: { enabled: true, value: null },
    txtDiaChiLD: { enabled: true, value: null },
    txtDoituongTB: { enabled: true, value: null },
    txtNgayHHTD: { enabled: false, value: null },
    txtGhiChuTB: { enabled: false, value: null },
    cboNguoiGV: { enabled: true, list: [], value: null },
    txtNDGiao: { enabled: true, value: null },
    puceNDTH: { enabled: true, list: [], value: null },
    puceNDTH_edit: { visible: false, enabled: true, list: [], value: null },
    puceNDTH_drop: { visible: false, enabled: true },
    txtThongTinTra: { enabled: true, value: null },
    txtLyDoTra: { enabled: true, value: null },
    chkNgayGV: { enabled: true, value: null },
    dtpNgayGV: { enabled: true, value: null },
    txtNhanVien: { enabled: true, value: null },
    chkNgayBG: { enabled: true, value: null },
    dtpNgayBG: { enabled: true, value: null },
    txtNoiDungTH: { enabled: true, value: null },
    chkLoc: { enabled: true, list: [], value: true },
    cboTrangThaiHD: { enabled: true, list: [], value: null },
    cboTrangThaiP: { enabled: true, list: [], value: null },
    cboGV: { enabled: true, list: [], value: null },
    popupContainerEdit1: { visible: false, enabled: true, list: [], value: null },
    popupContainerControl1: { visible: false, enabled: true, list: [], value: null },
    cboVip: { enabled: true, list: [], value: null },
    wbrTSKT: { enabled: true, list: [], value: null },
    lblLienHe: { enabled: true, value: null },
    //ejsDialog
    ejsDialog: {
      header: 'Tiêu đề',
      name: null,
      visible: false,
      config: {
        width: '95%',
        height: 'auto',
        minHeight: '150',
        enableResize: true,
        allowDragging: true,
        position: { X: 'center', Y: 'center' },
        // position: { X: 420, Y: 14 },
        animationSettings: { effect: 'Fade' }
      }
    },
    frmTraPhieuTC: { visible: false, input: {} },
    frmDSHoSoBC: { visible: false, input: { vhdtb_ban: null } },
    frmNoiDung: { visible: false, input: {} },
    frmTraCuuPhieuTCBC: { visible: false, input: {} },
    frmDanhSachThueBaoCaiTaoHaTang_IDC: { visible: false, input: {} },
    frmDanhSachSoThanhVien: { visible: false, input: {} },
    frmNhapText: { visible: false, input: {} },
    frmTraPhieuVIP: { visible: false, input: {} },
    frmDanhSachThietBI_IDC: { visible: false, input: {} },
    frmImportThietBi_IDC: { visible: false, input: {} },
    frmGiaoPhieuNV: { visible: false, input: {} },
    frmTraPhieuCNTT: { visible: false, input: {} },
    frmCapnhatCNTT: { visible: false, input: {} },
    // variables
    tinh_khdn: [97, 100],
    kieu_lapdat_onebusiness: null,
    dt: [],
    dsDanhMuc: [],
    dichvuvt_id: 0,
    macdinh_vmp: 0,
    huonggiao_id: 7777,
    vdonvinhan_id: 0,
    input_vhdtb_id: 0,
    render_giaophieucntt: false,
    popupComponentData: {},
    popupComponent: null,
    popupComponentAttr: {},
    popupComponentEvts: {},
    ppData: {
      frmSLAs: null
    },
    isShow: {
      frmSLAs: false
    },
    dtSLAs: {
      error_code: 400,
      sla_time: null,
      ngaygiao: null,
      diff_in_minutes: null,
      duration: {
        seconds: null,
        minutes: null,
        hours: null,
        total: null,
      },
      status_list: ['open', 'resolved', 'outdate'],
      status: 'open',
      interval_CountDown: null
    },
  }),
  computed: {
    btnSuaTSKT_Visible() {
      return this.tsbtnHoanThanh.visible
    },
    v_phanvung_id() {
      return this.$root.token.getPhanVungID()
    }
  },
  async mounted() {
    this.cboTrangThaiHD.list = [
      { id: 0, name: 'Chưa hoàn thành' },
      { id: 1, name: 'Thoái trả' },
      { id: 2, name: 'Đã hoàn thành' }
    ]
    this.cboGV.list = [
      { id: 0, name: 'Tất cả' },
      { id: 1, name: 'Chưa giao' },
      { id: 2, name: 'Đã giao' }
    ]
    this.cboTrangThaiP.list = [
      { id: 0, name: 'Chưa hoàn thành' },
      { id: 1, name: 'Đã hoàn thành' },
      { id: 2, name: 'Phiếu của HD hủy giao' },
      { id: 3, name: 'Phiếu chuyển Vip khác' },
      { id: 4, name: 'Đã trả lại' }
    ]
    await this.frmGiaoViecVIP_Load()
    await this.HT_POPUP()
  },
  watch: {
    localLoading(val) {
      if (val > 0) this.loading(true)
      else this.loading(false)
    },
    'chkNgayGV.value'(val) {
      this.dtpNgayGV.enabled = val
      if (!val) {
        this.dtpNgayGV.value = null
      }
    },
    'chkNgayBG.value'(val) {
      this.dtpNgayBG.enabled = val
      if (!val) {
        this.dtpNgayBG.value = null
      }
    },
    async 'gridDanhSach.list'(val) {
      await this.gridDanhSach_DataSourceChanged()
    }
  },
  methods: {
    async traphieuit_close() {
      console.log('Nhảy vào traphieuit_close rồi đấy')
      console.log(this.$refs)
      this.$refs.frmTraPhieuCNTT_IT.traphieuit_close()
      await this.tsbtnLayTT_Click()
    },
    async emit_function() {
      //console.log("Call vào hàm emit function rồi nè")
      //this.$refs.frmGiaoPhieu_IT.close()
      this.$refs.frmGiaoPhieuCNTT_it.emit_function()
      await this.tsbtnLayTT_Click()
    },
    async callApiWrapper(action, body, options = { showError: true }) {
      let result = null
      try {
        this.localLoading++
        result = await action(this.axios, body)
        return result.data.data != null ? result.data.data : null
      } catch (e) {
        if (options.showError) {
          if (e.data != null) {
            this.$toast.error(`${e.data.message}`)
          } else {
            console.log('Có lỗi gọi API')
            console.log(`${e}`)
            this.$toast.error(`Có lỗi gọi API`)
          }
        }
        return 'error'
      } finally {
        setTimeout(() => {
          this.localLoading--
        }, 500)
      }
      return null
    },
    gridDanhSachNgayGiaoSortComparer(reference, comparer) {
      let referenceDate = moment(reference, 'DD/MM/YYYY hh:mm:ss').toDate()
      let comparerDate = moment(comparer, 'DD/MM/YYYY hh:mm:ss').toDate()
      if (referenceDate < comparerDate) {
        return -1
      }
      if (referenceDate > comparerDate) {
        return 1
      }
      return 0
    },
    onTabSelected(e) {},
    async giaoviec_success() {
      await this.tsbtnLayTT_Click()
    },
    async HienThiDVGT(dt) {
      if (dt.length > 0) {
        this.gridDangkyDV.list = dt
      } else {
        this.gridDangkyDV.list = []
      }
    },
    async HienThiMuaTbi(dt) {
      if (dt.length > 0) {
        this.grcThietBiThuMotLan.list = dt
      } else {
        this.grcThietBiThuMotLan.list = []
      }
    },
    async HienThiThuocTinh(dt) {
      if (dt.length > 0) {
        this.grThuocTinh.list = dt
      } else this.grThuocTinh.list = []
    },
    async GiaoDien_DichVuVT(dichvuvt_id) {
      this.txtMaGDTC.enabled = false
    },
    async ClearTextBox(controls) {
      for (let c in controls.Controls) {
        // if (c is TextBox) ((TextBox)c).value = "";
        // ClearTextBox(c);
      }
    },
    async LayPhieu() {
      // try {
      let ds = await this.callApiWrapper(api.sp_khdn_lay_phieu_vip, {
        vphanvung_id: this.$root.token.getPhanVungID(),
        vdonvi_id: this.$root.token.getDonViID(),
        vtt_hd: this.cboTrangThaiHD.value,
        vtt_phieu: this.cboTrangThaiP.value,
        vnhanvien_id: this.$root.token.getNhanVienID(),
        vloc: this.chkLoc.value ? 1 : 0,
        vgiaophieu: this.cboGV.value,
        vhuonggiao_id: this.huonggiao_id * 1,
        vtthd_id: this.input_vhdtb_id * 1
      })
      this.gridDanhSach.list = ds
      // this.gridDanhSach.list = ds.map((e) => {
      //   return {
      //     ...e,
      //     ngaygiao: moment(e.ngaygiao, 'DD/MM/YYYY hh:mm:ss').toDate(),
      //     ngay_cn: moment(e.ngay_cn, 'YYYY-MM-DD hh:mm:ss').toDate()
      //   }
      // })
    },
    async GiaoViec() {
      if (this.gridDanhSach.list.length <= 0) {
        this.$toast.warning('Chưa có danh phiếu. Bạn hãy kiểm tra lại! ')
        return
      }
      if (this.cboNguoiGV.list.length <= 0 || this.cboNguoiGV.value == '') {
        this.$toast.warning('Hãy nhập nhân viên giao việc!')
        return
      }
      if (!this.chkNgayGV.value) {
        this.$toast.warning('Hãy nhập ngày giao việc!')
        return
      }
      if (this.gridDanhSach.value.loaitb_id != LoaiHinhTB.INTERNET_ADSL_TINH_KHAC && this.gridDanhSach.value.loaitb_id != LoaiHinhTB.INTERNET_FIBER_TINH_KHAC) {
        if (this.chkNgayBG.value) {
          let diffResult = this.dtpNgayBG.value.Subtract(this.dtpNgayGV.value)
          if (diffResult.Seconds < 0) {
            this.$toast.warning('Ngày giao việc không được lớn hơn ngày hoàn thành!')
            return
          }
        }
      }
      // let vnhanvien_giao_id = 0
      // let vngaygiao
      // vnhanvien_giao_id = this.cboNguoiGV.value
      // vngaygiao = this.dtpNgayGV.value

      // this.$refs.frnGiaoPhieuNV.frnGiaoPhieuNV(this.gridDanhSach.value.phieu_id, this.$root.token.getNhanVienID(), vnhanvien_giao_id, vngaygiao)
      // this.$refs.frnGiaoPhieuNV.vma_tb = this.gridDanhSach.value.ma_tb
      // this.$refs.frnGiaoPhieuNV.vhdtb_id = this.gridDanhSach.value.hdtb_id
      // this.$refs.frnGiaoPhieuNV.huonggiao_id = 7777


      //wtf

      let ds = this.$refs.gridDanhSach.getSelectedRecords()
      console.log('GiaoViec')
      console.log(ds)
      this.frmGiaoPhieuNV.input = {
        vdsphieu: ds,
        vnhanvien_giao_id: this.cboNguoiGV.value,
        vngaygiao: this.dtpNgayGV.value,
        vma_tb:  this.gridDanhSach.value.ma_tb,
        vhdtb_id: this.gridDanhSach.value.hdtb_id,
        vhuonggiao_id: 7777
      }
      this.ejsDialog.name = 'frmGiaoPhieuNV'
      this.ejsDialog.header = 'Giao phiếu nhân viên'
      this.$refs.ejsDialog.show()


      // this.$nextTick(() => {
      //   this.$refs.frnGiaoPhieuNV.load()
      //   this.$refs.popupfrnGiaoPhieuNV.show()
      // })

      // let ds = []
      // ds = await this.callApiWrapper(api.lay_ds_nhanvien_theo_phieu_id, {
      //   phanvung_id: this.$root.token.getPhanVungID(),
      //   phieu_id: this.gridDanhSach.value.phieu_id,
      //   nhanvien_id: this.$root.token.getNhanVienID(),
      //   kieu_id: 2
      // })
      // await this.HienThiDSNV(ds)
    },
    async HienThiDSNV(ds) {
      let myDataTables = ds
      if (myDataTables.length > 0) {
        this.chkNgayGV.value = true
        this.dtpNgayGV.value = ds[0].ngaygiao
        this.cboNguoiGV.value = ds[0].nhanvien_giao_id
        this.gridGiaoviec.list = ds
      } else {
        this.gridGiaoviec.list = []
        // hoangpn : 10/08/2017 sửa theo yc DNI
        //if (!PHAILAM(luong_id, "GIAOVIEC"))
        //{
        //    chkNgayGV.value = true;
        //    dtpNgayGV.value = DateTimeHelper.NgayCN();
        //}
        //else
        //{
        //    chkNgayGV.value = false;
        //    dtpNgayGV.value = DateTimeHelper.NgayCN();
        //}
        this.chkNgayGV.value = true
        this.dtpNgayGV.value = DateTimeHelper.NgayCN()
        // end hoang
        this.cboNguoiGV.value = this.$root.token.getNhanVienID()
      }
    },
    async HT_THONGSO_KYTHUAT(tinh_id) {
      try {
        this.wbrTSKT.value = ''
        let dt = await this.callApiWrapper(api.bancheo_lay_ds_thongtin_kythuat_v2, {
          vphanvung_id: this.$root.token.getPhanVungID(),
          vtinh_id: tinh_id,
          vhdtb_id: this.hdtb_id
        })
        this.wbrTSKT.value = ''
        this.wbrTSKT.value = dt
      } catch (e) {
        this.wbrTSKT.value = 'Chưa có dữ liệu'
      }
    },
    async HT_POPUP() {
      let txt = ''
      txt += 'Lọc theo nhân viên: ' + (this.chkLoc.value == true ? 'Có' : 'Không')
      txt += ', Trạng thái HĐ:  ' + this.cboTrangThaiHD.list.find((e) => e.id == this.cboTrangThaiHD.value)?.name
      txt += ', Giao việc:  ' + this.cboGV.list.find((e) => e.id == this.cboGV.value)?.name
      txt += ', Trạng thái phiếu:  ' + this.cboTrangThaiP.list.find((e) => e.id == this.cboTrangThaiP.value)?.name
      txt += ', Chuyển VIP:  ' + this.cboVip.list.find((e) => e.donvi_id == this.cboVip.value)?.ten_dv
      this.popupContainerEdit1.value = txt
    },
    // async KiemTraXemHoSo() {
    //   return true
    //   if (this.HoSoDaXem.length == 0) {
    //     this.$toast.error('Bạn chưa xem hồ sơ')
    //     return false
    //   }
    //   let kt = true
    //   for (let item in this.HoSoDaXem) {
    //     if (item.value == false) {
    //       kt = false
    //       this.$toast.error('Bạn chưa xem file: ' + item.Key)
    //       break
    //     }
    //   }
    //   return kt
    // },
    async HT_DLG_NHAP_NOIDUNG(tientrinhtc_id) {
      this.frmNoiDung.input = {
        row: this.gridDanhSach.value,
        tientrinhtc_id: tientrinhtc_id
      }
      this.ejsDialog.name = 'frmNoiDung'
      this.ejsDialog.header = 'Nhập nội dung tiến trình thi công'
      this.$refs.ejsDialog.show()
    },
    async HT_DLG_XOA_TIENTRINH(vtientrinhtc_id) {
      let _tinh_id = this.gridDanhSach.value.TINH_ID
      let vhdtb_id
      if (this.tinh_khdn.includes(_tinh_id) && this.gridDanhSach.value.HDTB_TC == '' && this.gridDanhSach.value.TINH_TC == '') {
        vhdtb_id = hdtb_id
      } else vhdtb_id = this.gridDanhSach.value.HDTB_TC
      let tinh_tc_id = 0
      if (_tinh_id == 100) {
        if (this.gridDanhSach.value.HDTB_TC == '' && this.gridDanhSach.value.TINH_TC == '') {
          tinh_tc_id = _tinh_id
        } else tinh_tc_id = this.gridDanhSach.value.TINH_TC
      } else tinh_tc_id = _tinh_id
      let _tinh_tc = new BanCheoBase().tinh_thicong(tinh_tc_id)

      let str = ''
      if (isNullOrEmpty(_tinh_tc.onebss_ip)) str += 'select * from {?DB2}.tientrinh_sms WHERE tientrinhtc_id = ' + vtientrinhtc_id
      else str += 'select * from {?DB2}.tientrinh_sms WHERE tientrinhtc_id = ' + vtientrinhtc_id + ' and phanvung_id = ' + _tinh_tc.tinhthicong_id
      let sdt = '-1'
      let dt = bancheo.GET_DATA_SQL(_tinh_tc, str)
      if (dt.length > 0) sdt = dt[0].so_dt
      if (sdt != '-1') {
        this.$toast.warning('Bạn không thể xóa tiến trình này\r\nTiến trình đã được gửi tin nhắn đến ' + sdt)
        return
      }
      if (!confirm('Bạn có thực sự muốn xóa tiến trình được chọn không ?')) return
      {
        try {
          str = ''

          if (isNullOrEmpty(_tinh_tc.onebss_ip)) str += ' DELETE FROM {?DB2}.tientrinh_tc' + ' WHERE tientrinhtc_id = ' + vtientrinhtc_id
          else str += ' DELETE FROM {?DB2}.tientrinh_tc' + ' WHERE tientrinhtc_id = ' + vtientrinhtc_id + ' and phanvung_id = ' + _tinh_tc.tinhthicong_id
          bancheo.EXECUTE_SQL(_tinh_tc, str)

          //str = "";
          //str += "select * from css_" + tinh_tc.matinh + ".tientrinh_tc where tientrinhtc_id = " + vtientrinhtc_id;
          //let dt = bancheo.GET_DATA_SQL(tinh_tc.matinh, str);
          //if (dt.length > 0)
          //{
          //    str = "";
          //    str += " UPDATE " + DatabaseConstants.DB2 + ".baohong";
          //    str += " SET tientrinhtc_id = (select max(tientrinhtc_id) from css_" + tinh_tc.matinh + ".tientrinh_tc where hdtb_id = " + dt[0].hdtb_id + ")";
          //    str += " WHERE hdtb_id = " + dt[0].hdtb_id;
          //}
          await this.NAP_GRD_TIENTRINH()
        } catch (e) {
          this.$toast.error(ex)
        }
      }
    },

    async HT_DLG_GUI_SMS(vtientrinhtc_id) {
      let _tinh_id = this.gridDanhSach.value.TINH_ID
      let vhdtb_id
      if (this.tinh_khdn.includes(_tinh_id) && gridDanhSach.value.HDTB_TC == '' && gridDanhSach.value.TINH_TC == '') {
        vhdtb_id = hdtb_id
      } else vhdtb_id = this.gridDanhSach.value.HDTB_TC
      let tinh_tc_id = 0
      if (this.tinh_khdn.includes(_tinh_id)) {
        if (this.gridDanhSach.value.HDTB_TC == '' && this.gridDanhSach.value.TINH_TC == '') {
          tinh_tc_id = _tinh_id
        } else tinh_tc_id = this.gridDanhSach.value.TINH_TC
      } else tinh_tc_id = _tinh_id
      let _tinh_tc = new BanCheoBase().tinh_thicong(tinh_tc_id)

      let bhi = this.gridDanhSach.value.hdtb_id
      let tti = vtientrinhtc_id

      if (bhi != null && tti != null) {
        let frm = new WinUI.WinUIQLDHXLSuCo.frmGuiSMS(_tinh_tc)
        frm.value = 'Gửi SMS tiến trình thi công'
        frm.vhdtb_id = vhdtb_id
        frm.vnhom_id = 1
        frm.vtientrinhtc_id = tti
        frm.vma_tb = ma_tb
        frm.txtNoiDung.value = gridTienTrinh.GetRowCellValue(gridTienTrinh.FocusedRowHandle, 'NOIDUNG')
        frm.ShowDialog()
      }
    },
    async NAP_GRD_TIENTRINH() {
      let kq = await this.callApiWrapper(api.sp_khdn_lay_ds_tientrinh_tc, {
        vphanvung_id: this.tinh_ph,
        vhdtb_id: this.gridDanhSach.value.hdtb_id,
        vtinh_id: this.gridDanhSach.value.tinh_id,
        vhdtb_tc: this.gridDanhSach.value.hdtb_tc,
        vtinh_tc: this.gridDanhSach.value.tinh_tc
      })
      this.gridTienTrinh.list = kq
      // if (gridDanhSach.FocusedRowHandle >= 0) {
      //   try {
      //     let _tinh_id = this.gridDanhSach.value.TINH_ID
      //     let vhdtb_id
      //     if (this.tinh_khdn.includes(_tinh_id) && gridDanhSach.value.HDTB_TC == '' && gridDanhSach.value.TINH_TC == '') {
      //       vhdtb_id = hdtb_id
      //     } else vhdtb_id = this.gridDanhSach.value.HDTB_TC
      //     let tinh_tc_id = 0
      //     if (this.tinh_khdn.includes(_tinh_id)) {
      //       if (this.gridDanhSach.value.HDTB_TC == '' && gridDanhSach.value.TINH_TC == '') {
      //         tinh_tc_id = _tinh_id
      //       } else tinh_tc_id = this.gridDanhSach.value.TINH_TC
      //     } else tinh_tc_id = _tinh_id
      //     let _tinh_tc = new BanCheoBase().tinh_thicong(tinh_tc_id)
      //     if (thrTienTrinh != null) {
      //       if (thrTienTrinh.IsAlive) thrTienTrinh.Abort()
      //       gridTienTrinh.visible = true
      //     }
      //     peLoading.BringToFront()
      //     thrTienTrinh = new Thread(() => {
      //       Controls_SetVisible(peLoading, true)
      //       let str = ''
      //       // Lấy tiến trình thi công
      //       str += ' select a.*, b.ten_dv donvi_cn'
      //       str += ' from {?DB2}.tientrinh_tc a, {?DB1}.donvi b'
      //       str += ' where a.donvi_id = b.donvi_id and a.hdtb_id = ' + vhdtb_id
      //       if (!isNullOrEmpty(_tinh_tc.onebss_ip)) str += ' and a.phanvung_id = ' + _tinh_tc.tinhthicong_id
      //       str += ' order by a.ngay_cn desc'
      //       SetDataSource(gridTienTrinh, bancheo.GET_DATA_SQL(_tinh_tc, str))
      //       Controls_SetVisible(peLoading, false)
      //     })
      //     {
      //       IsBackground = true
      //     }
      //     thrTienTrinh.Start()

      //     //gridTienTrinh.BestFitColumns();
      //     //btnNhapMoi.enabled = vtrangthai_id != "6";
      //   } catch { }
      // } else {
      //   this.gridTienTrinh.list = null
      //   this.txtMaGD.value = ''
      //   this.txtTenTB.value = ''
      //   this.txtDiaChiLD.value = ''
      // }
    },
    async cboDichVuVT_SelectedValueChanged() {
      try {
        if (this.cboDichVuVT.list.length > 0) {
          if (this.cboDichVuVT.value == null) return
          // if (this.cboDichVuVT.value == 'System.Data.DataRowView') return
          this.dichvuvt_id = this.cboDichVuVT.value
          let ngaythue = DateTimeHelper.NgayCN()
          // this.cboLoaihinhTB.SelectedValueChanged -= new System.EventHandler(this.cboLoaihinhTB_SelectedValueChanged)
          this.cboLoaihinhTB.list = await this.callApiWrapper(api.get_loai_hinh_tb_by_dichvuvt_id, { dichvuvt_id: this.dichvuvt_id })
          // CheckDataFacade checkdata = new CheckDataFacade();
          // this.cboLoaihinhTB.SelectedValueChanged += new System.EventHandler(this.cboLoaihinhTB_SelectedValueChanged)
          // await this.cboLoaihinhTB_SelectedValueChanged(cboLoaihinhTB, null)
        }
      } catch (ex) {
        this.$toast.error(`Có lỗi: ${ex}`)
      }
    },
    async cboLoaihinhTB_SelectedValueChanged() {
      try {
        if (this.cboLoaihinhTB.list.length > 0) {
          if (this.cboLoaihinhTB.value == null) return
          if (this.cboLoaihinhTB.value == 'System.Data.DataRowView') return
          let loaitb_id = this.cboLoaihinhTB.value
          KT_GOI_CBO_LOAIHINH = true
          KT_GOI_CBO_LOAIHINH = false
          loaitb_id = this.cboLoaihinhTB.value
        }
      } catch (ex) {
        this.$toast.error(`Có lỗi: ${ex}`)
      }
    },
    async gridDanhSach_DataSourceChanged() {
      this.tsbtnHoanThanh.enabled = this.gridDanhSach.list.length > 0

      this.tsbtnGiaoViec.enabled = this.gridDanhSach.list.length > 0
      if (this.gridDanhSach.list.length == 0) {
        // this.ClearTextBox(this)
        this.lblLienHe.value = 'Liên hệ : '
      }
    },
    async frmGiaoViecVIP_Load() {
      let arrTableName = ['DICHVU_VT', 'LOAIHINH_TB']
      let args = ['vchuoi', 'DICHVU_VT|LOAIHINH_TB']
      // dsDanhMuc = new BangTuDienFacade().THUCTHI_PROCEDURE('Lay_danhmuc_lapmoi_tt_chung_v5', DatabaseConstants.DB2, 'bangtudien_common', arrTableName, args)

      this.cboDichVuVT.list = await this.callApiWrapper(api.CSS_DICHVU_VT, {})
      this.cboLoaihinhTB.list = await this.callApiWrapper(api.get_loai_hinh_tb_by_dichvuvt_id, { dichvuvt_id: this.dichvuvt_id })
      this.cboVip.list = await this.callApiWrapper(api.sp_ht_tatca_vetinh_combobox1, { loaidv_id: 890 })
      if (this.cboVip.list && this.cboVip.list.length) {
        this.cboVip.value = this.cboVip.list[0].donvi_id
      }
      let ds = await this.callApiWrapper(api.sp_lay_ds_thamso_md, {
        p_kieu_id: '0'
      })
      if (ds.length > 0) {
        let str_vtemp = 'MACDINH_VMP'
        let det = ds.filter((x) => x.ma_ts == str_vtemp)
        if (det.length > 0) {
          let row = det[0]
          if (row.ten_ts.trim() == '1') this.macdinh_vmp = 1
        }
      }
      let arrTableName1 = ['NHANVIEN', 'QUYTRINH']
      // let args1 = new object[] { "vchuoi", "NHANVIEN-donvi_id = " + tt_nd.donvi_id + "|QUYTRINH" };
      // let dsDanhMuc1 = new BangTuDienFacade().THUCTHI_PROCEDURE('Lay_danhmuc_thicong_internet', DatabaseConstants.DB2, 'bangtudien_common', arrTableName1, args1)
      // let dsDanhMuc1 = await this.callApiWrapper(api.lay_danhmuc_thicong_internet, )

      let bigData = await this.callApiWrapper(api.lay_danhmuc_thicong_internet, {
        vchuoi: `NHANVIEN-donvi_id = ${this.$root.token.getDonViID()}|QUYTRINH||NHANVIEN`
      })
      this.cboNguoiGV.list = bigData.nhanvien
      this.cboNguoiGV.value = this.$root.token.getNhanVienID()
      let ngay_hientai = DateTimeHelper.NgayCN()
      this.dtpNgayBG.value = ngay_hientai
      this.dtpNgayGV.value = ngay_hientai
      this.dtpNgayGV.enabled = false
      // this.tinh = bancheo.tinh_thicong(100)
      this.cboTrangThaiHD.value = 0
      this.cboTrangThaiP.value = 0
      this.cboGV.value = 0
      await this.LayPhieu()
      // try {
      let tag_form = this.$route.query.tag
      if (tag_form.includes(';')) {
        let tag_arr = tag_form.split(';')
        this.Tag = tag_arr[0]
        this.input_vhdtb_id = tag_arr[1] * 1
        console.log(this.input_vhdtb_id)
      } else {
        this.Tag = this.$route.query.tag
      }

      if (this.Tag != 0) this.huonggiao_id = this.Tag
      this.tsbtnTraPhieuIT.visible = false
      this.tsbtnGiaoPhieu.visible = false
      if (this.huonggiao_id == 3333) {
        this.tsbtnChuyenVip.visible = false
        this.cboVip.enabled = false
        // gridColumn19.visible = false;
        this.header.title = 'IDC - Khai báo dịch vụ'
        // layoutControlItem39.Visibility = DevExpress.XtraLayout.Utils.LayoutVisibility.Never;
        // popupContainerControl1.Size = new Size(439, 112);
        this.tsbtnImportIDC.visible = true
      } else if (this.huonggiao_id == 2204) {
        this.tsbtnChuyenVip.visible = false
        this.cboVip.enabled = false
        // gridColumn19.visible = false;
        this.header.title = 'VNPT I - Khai báo dịch vụ'
        // layoutControlItem39.Visibility = DevExpress.XtraLayout.Utils.LayoutVisibility.Never;
        // popupContainerControl1.Size = new Size(439, 112);
      } else if (this.huonggiao_id == 44444) {
        this.tsbtnChuyenVip.visible = false
        this.tsbtnHoanThanh.visible = false
        this.tsbtnTraPhieuIT.visible = false
        this.tsbtnGiaoPhieu.visible = false
        this.tsbtnChuyenVip.visible = false
        this.tsbtnCreatIssue.visible = false
        this.tsbtnImportIDC.visible = false
        this.tsbtnTC_CTTC.visible = false
        this.tsbtnTraPhieuTinh.visible = false
        this.tsbXemCMS.visible = false
        this.tsbXemTbi.visible = false
        this.cboVip.enabled = false
        // gridColumn19.visible = false;
        // this.header.title = 'IT - Khai báo dịch vụ'
        // layoutControlItem39.Visibility = DevExpress.XtraLayout.Utils.LayoutVisibility.Never;
        // popupContainerControl1.Size = new Size(439, 112);
      } else if (this.huonggiao_id == 55555) {
        this.tsbtnChuyenVip.visible = false
        this.cboVip.enabled = false
        // gridColumn19.visible = false;
        this.header.title = 'MEDIA - Khai báo dịch vụ'
        // layoutControlItem39.Visibility = DevExpress.XtraLayout.Utils.LayoutVisibility.Never;
        // popupContainerControl1.Size = new Size(439, 112);
      } else if (this.huonggiao_id == 69696) {
        this.tsbtnChuyenVip.visible = this.cboVip.enabled = false
        // gridColumn19.visible = false;
        this.header.title = 'Chuyên viên cung ứng phối hợp thực hiện'
        // layoutControlItem39.Visibility = DevExpress.XtraLayout.Utils.LayoutVisibility.Never;
        // popupContainerControl1.Size = new Size(439, 112);
      } else if (this.huonggiao_id == 96969) {
        this.tsbtnChuyenVip.visible = false
        this.cboVip.enabled = false
        // gridColumn19.visible = false;
        this.header.title = 'Chuyên viên vận hành triển khai phối hợp thực hiện'
        // layoutControlItem39.Visibility = DevExpress.XtraLayout.Utils.LayoutVisibility.Never;
        // popupContainerControl1.Size = new Size(439, 112);
      } else if (this.huonggiao_id == 98686) {
        this.tsbtnChuyenVip.visible = false
        this.cboVip.enabled = false
        // gridColumn19.visible = false;
        this.header.title = 'Chuyên viên SNOC phối hợp thực hiện'
        // layoutControlItem39.Visibility = DevExpress.XtraLayout.Utils.LayoutVisibility.Never;
        // popupContainerControl1.Size = new Size(439, 112);
      } else if (this.huonggiao_id == 41014) {
        this.tsbtnChuyenVip.visible = false
        this.cboVip.enabled = false
        // gridColumn19.visible = false;
        this.header.title = 'Chuyên viên TCĐS phối hợp thực hiện'
        // layoutControlItem39.Visibility = DevExpress.XtraLayout.Utils.LayoutVisibility.Never;
        // popupContainerControl1.Size = new Size(439, 112);
      }
      // } catch {}
    },

    async hienthiNutThaoTacTheoLuongQuyTrinh() {
      try {
        if (this.huonggiao_id == 44444) {
          let kq = await api.lay_luong_thaotac_cntt_v2(this.axios, {
            phanvung_id: this.tinh_ph,
            hdtb_id: this.hdtb_id
          })
          console.log(kq)
          let ds_thaotac = JSON.parse(kq.data.data)
          for (let i in ds_thaotac) {
            if (this[ds_thaotac[i].control_name]) {
              this[ds_thaotac[i].control_name].enabled = true
              this[ds_thaotac[i].control_name].visible = true
            }
          }
        }
      } catch (e) {
        throw e
      }
    },

    async tsbtnLayTT_Click() {
      await this.LayPhieu()
    },
    async gvDanhSach_FocusedRowChanged(row) {
      console.log('gvDanhSach_FocusedRowChanged')
      console.log(row)
      if (typeof row.data === 'object' && !Array.isArray(row.data)) {
        this.gridDanhSach.value = row.data
        // this.HoSoDaXem.Clear()
        if (this.gridDanhSach.value) {
          // try {
          this.hdtb_id = this.gridDanhSach.value.hdtb_id
          this.dichvuvt_id = this.gridDanhSach.value.dichvuvt_id
          this.ma_tb = this.gridDanhSach.value.ma_tb
          this.vloaihd_id = this.gridDanhSach.value.loaihd_id
          this.cboDichVuVT.value = this.dichvuvt_id
          await this.cboDichVuVT_SelectedValueChanged()
          this.cboLoaihinhTB.value = this.gridDanhSach.value.loaitb_id
          if (this.cboLoaihinhTB.value == 405) {
            this.tsbXemHd_thuebao_ct.visible = true
            this.tsbXemHd_thuebao_ct.value = 'Cấu hình node'
          } else if (this.cboLoaihinhTB.value == 399) {
            this.tsbXemHd_thuebao_ct.visible = true
            // this.tsbXemHd_thuebao_ct.value = 'Cấu hình Worker'
          }
          if (this.cboLoaihinhTB.value == LoaiHinhTB.VNPT_COLOCATION) {
            this.tsbXemCMS.value = 'DS CTHT IDC'
            // this.tsbXemCMS.ToolTipText = 'Danh sách thuê bao cải tạo hạ tầng Colocation'
          } else {
            this.tsbXemCMS.value = 'Xem CMS'
            // this.tsbXemCMS.ToolTipText = 'Xem CMS'
          }
          //END IDC COLOCATION
          this.txtKieuLD.value = this.gridDanhSach.value.ten_kieuld
          this.txtDoituongTB.value = this.gridDanhSach.value.doituong_tb
          this.txtDiaChiTB.value = this.gridDanhSach.value.diachi_tb
          this.txtTenTB.value = this.gridDanhSach.value.ten_tb
          this.txtDiaChiLD.value = this.gridDanhSach.value.diachi_ld
          this.tinh_ph = this.gridDanhSach.value.tinh_id
          this.xuly = this.gridDanhSach.value.xuly
          this.vdonvinhan_id = this.gridDanhSach.value.donvi_nhan_id
          this.vttph_id = this.gridDanhSach.value.ttph_id
          this.tinh = this.tinh_ph
          // if (this.dichvuvt_id == DichVuVienThong.ANTOAN_BAOMAT_TT || this.dichvuvt_id == DichVuVienThong.HOINGHI_TRUYENHINH || this.dichvuvt_id == DichVuVienThong.TRUNGTAM_DULIEU || this.dichvuvt_id == DichVuVienThong.DICHVU_CNTT) await this.HienthiCNTT(1)
          // else await this.HienthiCNTT(2)
          if (this.vttph_id == 1) this.tsbtnChuyenVip.enabled = true
          else this.tsbtnChuyenVip.enabled = false

          if (!this.tinh_khdn.includes(this.tinh_ph) && this.xuly == 1) {
            this.tsbtnTraPhieuTinh.enabled = true
          } else this.tsbtnTraPhieuTinh.enabled = false

          if (this.tinh_khdn.includes(this.tinh_ph)) {
            let kq_kt_hienthi = await this.callApiWrapper(api.fn_loaihinhtb_hienthi_nut_traphieutinh, {
              vphanvung_id: this.$root.token.getPhanVungID(),
              vhuonggiao_id: this.huonggiao_id,
              vloaitb_id: this.gridDanhSach.value.loaitb_id
            })

            if (kq_kt_hienthi != 'OK') this.tsbtnTraPhieuKHDN.enabled = false
            else this.tsbtnTraPhieuKHDN.enabled = true
          } else this.tsbtnTraPhieuKHDN.enabled = false

          if (this.dichvuvt_id == DichVuVienThong.HOINGHI_TRUYENHINH || this.dichvuvt_id == DichVuVienThong.ANTOAN_BAOMAT_TT || this.dichvuvt_id == DichVuVienThong.DICHVU_CNTT || this.dichvuvt_id == DichVuVienThong.TRUNGTAM_DULIEU) {
            this.tsbtnCreatIssue.enabled = true
            this.tsbtnXemIT360.enabled = true
          }
          let thuebao_id
          if (this.gridDanhSach.value.thuebao_id != '') thuebao_id = this.gridDanhSach.value.thuebao_id
          else thuebao_id = 0

          this.txtGhiChuTB.value = this.gridDanhSach.value.ghichutb
          this.txtNDGiao.value = this.gridDanhSach.value.nd_giao
          this.puceNDTH_edit.value = this.gridDanhSach.value.nd_thuchien
          let dt_kh = []

          this.txtMaTC.value = this.gridDanhSach.value.ma_tbtc
          this.txtMaGDTC.value = this.gridDanhSach.value.ma_gdtc
          this.txtGhiChuTB.value = this.gridDanhSach.value.ghichu_tc
          this.txtNVLapHD.value = this.gridDanhSach.value.nv_laphd

          await this.NAP_GRD_TIENTRINH()

          let dt_kt = await this.callApiWrapper(api.sp_lay_tt_kh_bc, {
            vphanvung_id: this.$root.token.getPhanVungID(),
            vtinh_id: this.gridDanhSach.value.tinh_id,
            vhdtb_id: this.gridDanhSach.value.hdtb_id
          })
          if (dt_kt.length > 0) {
            if (dt_kt[0].ngay_sn != '') this.txtNgaySNTB.value = dt_kt[0].ngay_sn
            else this.txtNgaySNTB.value = ''
            // await this.HienThiTTMoRong(hdtb_id, dichvuvt_id)
            //thong tin khach hang
            this.lblLienHe.value = 'Liên hệ : ' + dt_kt[0].hdkh_id
            if (this.lblLienHe.value == '') this.lblLienHe.value = 'Liên hệ : '
            this.txtMaGD.value = dt_kt[0].ma_gd
            this.txtNguoiDD.value = dt_kt[0].nguoi_dd
            this.txtMaKH.value = dt_kt[0].ma_kh
            this.txtTenKH.value = dt_kt[0].ten_kh
            this.txtDiaChiKH.value = dt_kt[0].diachi_kh
            this.txtTelKH.value = dt_kt[0].SO_DT
            if (dt_kt[0].NGAY_HH_TD != '') this.txtNgayHHTD.value = dt_kt[0].ngay_hh_td
            if (dt_kt[0].thoigian_ck != '') this.dtpNgayYeuCau.value = dt_kt[0].thoigian_ck
            this.dtpNgayLapHD.value = dt_kt[0].ngaylap_hd
          }

          await this.HienThiDSNV(
            await this.callApiWrapper(api.lay_ds_nhanvien_theo_phieu_id, {
              phanvung_id: this.$root.token.getPhanVungID(),
              phieu_id: this.gridDanhSach.value.phieu_id,
              nhanvien_id: this.$root.token.getNhanVienID(),
              kieu_id: 2
            })
          )
          await this.HienThiDSNVCungTH(this.gridDanhSach.value.phieu_id)
          this.txtNhanVien.value = this.gridDanhSach.value.nv_laphd
          // if (this.dichvuvt_id != DichVuVienThong.ANTOAN_BAOMAT_TT && this.dichvuvt_id != DichVuVienThong.HOINGHI_TRUYENHINH && this.dichvuvt_id != DichVuVienThong.TRUNGTAM_DULIEU && this.dichvuvt_id != DichVuVienThong.DICHVU_CNTT) await this.HT_THONGSO_KYTHUAT(this.gridDanhSach.value.tinh_id)
          await this.HT_THONGSO_KYTHUAT(this.gridDanhSach.value.tinh_id)
          // }
          this.grcDS_NVQL.list = await this.callApiWrapper(api.lay_ds_nvql_tb_theo_hdtb_bc_v2, {
            vphanvung_id: this.tinh_ph,
            vtinh_id: this.tinh_ph,
            vhdtb_id: this.hdtb_id
          })
          if (this.tinh_khdn.includes(this.tinh_ph)) {
            await this.HienThiDVGT(
              await this.callApiWrapper(api.lay_ds_dangky_dvgt, {
                vphanvung_id: this.tinh_ph,
                vhdtb_id: this.hdtb_id
              })
            )
            await this.HienThiMuaTbi(
              await this.callApiWrapper(api.lay_ds_tbi_hdtb, {
                vphanvung_id: this.tinh_ph,
                vhdtb_id: this.hdtb_id
              })
            )
            // if (this.dichvuvt_id == DichVuVienThong.ANTOAN_BAOMAT_TT || this.dichvuvt_id == DichVuVienThong.DICHVU_CNTT || this.dichvuvt_id == DichVuVienThong.HOINGHI_TRUYENHINH || this.dichvuvt_id == DichVuVienThong.TRUNGTAM_DULIEU) {
            //   str = ''
            //   str += ' select rownum stt, tenthuoctinh '
            //   str += ' from {?DB2}.hd_thuebao a, {?DB2}.hdtb_cntt b, {?DB2}.tocdo_thuoctinh c '
            //   str += ' where a.hdtb_id = b.hdtb_id '
            //   str += '     and b.tocdo_id = c.tocdo_id '
            //   str += '     and a.hdtb_id = ' + hdtb_id
            //   str += ' order by c.thuoctinh_id '
            //   HienThiThuocTinh(new BanCheoBase().GET_DATA_SQL(str))
            // } else {
            //   this.grThuocTinh.list = []
            // }
          } else {
            await this.HienThiDVGT(
              await this.callApiWrapper(api.lay_ds_dangky_dvgt, {
                vphanvung_id: this.tinh,
                vhdtb_id: this.hdtb_id
              })
            )
            await this.HienThiMuaTbi(
              await this.callApiWrapper(api.lay_ds_tbi_hdtb, {
                vphanvung_id: this.tinh,
                vhdtb_id: this.hdtb_id
              })
            )
            await this.hienthiNutThaoTacTheoLuongQuyTrinh()
            await this.lay_thoigian_sla_theo_hdtbid()
            // if (this.dichvuvt_id == DichVuVienThong.ANTOAN_BAOMAT_TT || this.dichvuvt_id == DichVuVienThong.DICHVU_CNTT || this.dichvuvt_id == DichVuVienThong.HOINGHI_TRUYENHINH || this.dichvuvt_id == DichVuVienThong.TRUNGTAM_DULIEU) {
            //   str = ''
            //   str += ' select rownum stt, tenthuoctinh '
            //   str += ' from {?DB2}.hd_thuebao a, {?DB2}.hdtb_cntt b, LAY_THONGTIN_NHANVIEN{?DB2}.tocdo_thuoctinh c '
            //   str += ' where a.hdtb_id = b.hdtb_id '
            //   str += '     and b.tocdo_id = c.tocdo_id '
            //   str += '     and a.hdtb_id = ' + hdtb_id
            //   if (!isNullOrEmpty(tinh.onebss_ip)) {
            //     str += '     and a.phanvung_id = ' + tinh.tinhthicong_id
            //     str += '     and b.phanvung_id = ' + tinh.tinhthicong_id
            //     str += '     and c.phanvung_id = ' + tinh.tinhthicong_id
            //   }
            //   str += ' order by c.thuoctinh_id '
            //   HienThiThuocTinh(new BanCheoBase().GET_DATA_SQL(tinh, str))
            // } else {
            //   this.grThuocTinh.list = []
            // }
          }
          // } catch (exp) {
          //   this.$toast.error(`Có lỗi khi hiển thị phiếu: ${exp}`)
          // }
        }
      }
    },
    async gridDanhSachSelectedItemsChanged(e) {
      console.log('gridDanhSachSelectedItemsChanged')
      console.log(e)
    },
    async tsbtnGiaoViec_Click() {
      try {
        await this.GiaoViec()
      } catch (ex) {
        this.$toast.error(`${ex}`)
      }
    },
    async btnNDTH_Click() {
      let ds = this.$refs.gridDanhSach.getSelectedRecords()
      if (!confirm(`Bạn có chắc chắn muốn thay đổi nội dung thực hiện ${ds.length} phiếu không?`)) return
      console.log(ds)
      let count = 0
      for (let i = 0; i < ds.length; i++) {
        let data = ds[i]
        try {
          this.loading(true)
          let kq = await api.sp_bancheo_update_nd_thuchien(this.axios, {
            vphieu_id: data.phieu_id,
            vtinh_id: data.tinh_id,
            vnd_thuchien: `${this.puceNDTH_edit.value}`
          })
          if (kq.data.error_code == 'BSS-00000000') {
            count += 1
          } else {
            this.$toast.error(`Không thành công!`)
          }
        } catch (e) {
          this.$toast.error(`${e}`)
        } finally {
          this.loading(false)
        }
      }
      this.$toast.success(`Cập nhật thành công ${count} phiếu!`)
      await this.tsbtnLayTT_Click()
    },
    async chkNgayGV_CheckedChanged() {
      this.dtpNgayGV.enabled = this.chkNgayGV.value
    },
    async chkNgayBG_CheckedChanged() {
      this.dtpNgayBG.enabled = this.chkNgayBG.Checked
    },
    async tsbtnHoanThanh_Click() {
      let ds = this.$refs.gridDanhSach.getSelectedRecords()
      console.log(ds)

      if (this.gridDanhSach.value == null) {
        this.$toast.warning('Vui lòng chọn phiếu!')
        return
      }

      if (!confirm(`Bạn có chắc chắn muốn hoàn thành ${ds.length} phiếu đã chọn!`)) return
      let count = 0
      for (let i = 0; i < ds.length; i++) {
        try {
          this.loading(true)
          let kq = await api.hoanthanh(this.axios, {
            tinh_ph: this.tinh_ph,
            hdtb_id: this.hdtb_id,
            cboLoaihinhTB: this.cboLoaihinhTB.value,
            txtKieuLD: this.txtKieuLD.value,
            ma_tb: this.ma_tb,
            cboNguoiGV: this.cboNguoiGV.value,
            phieu_id: this.gridDanhSach.value.phieu_id,
            dtpNgayBG: this.dtpNgayBG.value,
            xuly: this.gridDanhSach.value.xuly,
            dt_vip_update: []
          })
          if (kq.data.error_code == 'BSS-00000000') {
            count += 1
          } else {
            this.$toast.error(`${kq.data.message}`)
            break
          }
        } catch (e) {
          this.$toast.error(`${e}`)
          break
        } finally {
          this.loading(false)
        }
      }
      this.$toast.success(`Hoàn thành thành công ${count} phiếu!`)
      await this.tsbtnLayTT_Click()
    },
    async chkLoc_CheckedChanged() {
      await this.HT_POPUP()
      await this.tsbtnLayTT_Click()
    },
    async cboTrangThaiP_SelectedValueChanged() {
      if (this.cboTrangThaiP.value == null) {
        this.tsbtnHoanThanh.visible = true
        this.tsbtnTraPhieuTinh.visible = true
      } else {
        this.tsbtnHoanThanh.visible = false
        this.tsbtnTraPhieuTinh.visible = false
      }
      await this.HT_POPUP()
      await this.LayPhieu()
    },
    async tsbtnCreatIssue_Click() {
      let vphieu_id = this.gridDanhSach.value.phieu_id
      let tinh_id = this.tinh_ph
      let loai = 1
      let kieu = null
      let data = {
        vphieu_id: vphieu_id,
        tinh_id: tinh_id,
        loai: loai,
        kieu: kieu
      }
      if (!this.tinh_khdn.includes(this.tinh_ph)) data.kieu = 3
      this.$refs.frmCreateIssue.openDialog(data)
    },
    async tsbtnGiaoPhieu_Click() {
      let vphieu_id = this.gridDanhSach.value.phieu_id
      let tinh_id = this.tinh_ph
      let loai = 1
      let kieu = null
      let data = {
        tinh_id: tinh_id,
        hdtb_id: this.hdtb_id
      }

      this.render_giaophieucntt = true
      this.$refs.frmGiaoPhieuCNTT_it.openDialog(data)
    },
    re_render_gpcntt() {
      // this.$forceUpdate()
      this.render_giaophieucntt = false
    },
    async tsbtnTraPhieuIT_Click() {
      /*try {
        let vphieu_id = this.gridDanhSach.value.phieu_id
        let tinh_id = this.tinh_ph

        let data = {
          tinh_id: tinh_id,
          hdtb_id: this.hdtb_id
        }
        let kq =await api.tudong_tra_phieu_it_cntt(this.axios, data);
        if (kq != 'ok')
        {
           // this.$toast.error(kq.data.data);
          throw new Error(kq.data.data);
        }
        else
        {
            this.$toast.success(kq.data.data);
        }
      }
      catch (e)
      {
        console.log(e)
        let loi = e.message|| JSON.stringify(e.data) ||"lỗi";

        this.$toast.error(loi);
      }*/
      if (this.gridDanhSach.value == null) {
        this.$toast.warning('Vui lòng chọn phiếu!')
        return
      }
      this.frmTraPhieuCNTT.input = {
        vhdtb_id: this.hdtb_id,
        loaitb_id: this.gridDanhSach.value.loaitb_id,
        ma_gd: this.txtMaGD.value,
        ma_tb: this.ma_tb,
        tinh_tc: this.tinh_ph
      }
      this.ejsDialog.name = 'frmTraPhieuCNTT'
      this.ejsDialog.header = 'Trả phiếu'
      this.$refs.ejsDialog.show()
    },
    async tsbtnTraPhieuTinh_Click() {
      if (this.tinh_khdn.includes(this.tinh_id)) {
        this.$toast.success('Tính năng này chỉ áp dụng với các phiếu được chuyển từ 63 TTP.')
        return
      }
      let kq = await this.callApiWrapper(api.kiemtra_thoaitra_hoanthanh_net, {
        vphanvung_id: this.tinh_ph,
        vhdtb_id: this.hdtb_id,
        vphieu_tinh_id: this.gridDanhSach.value.phieu_tinh_id,
        vloai_check: 1
      })
      if (kq != 'OK') {
        this.$toast.success(`${kq}`)
        return
      }
      this.frmTraPhieuVIP.input = {
        vds_phieu: [
          {
            TINH_ID: this.tinh_ph,
            PHIEU_ID: this.gridDanhSach.value.phieu_id,
            PHIEU_TINH_ID: this.gridDanhSach.value.phieu_tinh_id,
            HDTB_ID: this.hdtb_id,
            vma_tb: this.gridDanhSach.value.ma_gd
          }
        ]
      }
      this.ejsDialog.name = 'frmTraPhieuVIP'
      this.ejsDialog.header = 'Trả phiếu'
      this.$refs.ejsDialog.show()
      await this.tsbtnLayTT_Click()
      // tsbtnLayTT.PerformClick()
    },
    async tsbtnChuyenVip_Click() {
      if (this.gridDanhSach.value == null) {
        this.$toast.warning('Vui lòng chọn phiếu!')
        return
      }
      try {
        if (this.cboVip.value == this.vdonvinhan_id) {
          this.$toast.warning('Đơn vị Vip được chuyển phải khác Vip hiện tại!')
          return
        }
        this.ejsDialog.name = 'frmNhapText'
        this.ejsDialog.header = 'Nhập text'
        this.$refs.ejsDialog.show()
      } catch (e) {
        this.$toast.error(`Có lỗi : ${e}`)
      }
    },
    async HienThiDSNVCungTH(phieu_id) {
      if (this.tinh_khdn.includes(this.tinh_ph)) {
        let dt = await this.callApiWrapper(api.lay_ds_nhanvien_cung_th_theo_phieu_id, {
          vphanvung_id: this.$root.token.getPhanVungID(),
          vphieu_id: phieu_id
        })
        if (dt.length > 0) this.gridGiaoviec2.list = dt
        else this.gridGiaoviec2.list = []
      }
    },
    async tsbtnXemIT360_Click() {
      console.log(this.gridDanhSach.value)
      this.$refs.frmThongTinYeuCauVIP.phanvung_id = this.gridDanhSach.value.tinh_id
      this.$refs.frmThongTinYeuCauVIP.hdtb_id = this.hdtb_id
      this.$refs.frmThongTinYeuCauVIP.showModal()
    },
    async tsbtnXemHS_Click() {
      if (this.gridDanhSach.value == null) {
        this.$toast.warning('Vui lòng chọn phiếu!')
        return
      }
      if (this.tinh_khdn.includes(this.tinh_ph)) {
        //let loaihinh = new BanCheoBase().GET_STRING_FUNCTION("{?DB2}.HIENTHI_HOSO_KHDN", "vhdtb_id", hdtb_id);
        let loaihinh = await this.callApiWrapper(api.fn_bancheo_hienthi_hoso_khdn, {
          vphanvung_id: this.$root.token.getPhanVungID(),
          vtinh_id: this.tinh_ph,
          vhdtb_id: this.hdtb_id
        })
        let data = {}
        // new BanCheoBase().GET_STRING_FUNCTION(new BanCheoBase().tinh_thicong(tinh_ph), '{?DB2}.HIENTHI_HOSO_KHDN', 'vhdtb_id', hdtb_id)
        if (loaihinh == 'ok') {
          data.hdtb_id = this.gridDanhSach.value.hdtb_id
          this.$refs.frmTraCuuHoSoHDTB.openDialog(data)
        } else {
          // data.hdtb_ban = this.hdtb_id
          // data.hdtb_tc = 0
          // data.tinh_id = this.tinh_ph
          // this.$refs.frmDSHoSoBC.openDialog(data)
          this.frmDSHoSoBC.input.visible = true
          this.frmDSHoSoBC.input.vhdtb_ban = this.gridDanhSach.value.hdtb_id
          setTimeout(() => {
            this.$refs.frmDSHoSoBC.showModal()
            this.$refs.frmDSHoSoBC.hdtb_tc = this.gridDanhSach.value.hdtb_id
            this.$refs.frmDSHoSoBC.tinh_id = this.tinh_ph
            console.log('✅', this.$refs.frmDSHoSoBC.tinh_id)
            this.$refs.frmDSHoSoBC.khoiTao()
          }, 500)
        }
      } else {
        // frm.hdtb_ban = 0
        // frm.hdtb_tc = this.hdtb_id
        // frm.tinh_id = this.tinh_ph
        // this.$refs.frmDSHoSoBC.openDialog(data)
        this.frmDSHoSoBC.input.visible = true
        this.frmDSHoSoBC.input.vhdtb_ban = 0
        setTimeout(() => {
          this.$refs.frmDSHoSoBC.showModal()
          this.$refs.frmDSHoSoBC.hdtb_tc = this.gridDanhSach.value.hdtb_id
          this.$refs.frmDSHoSoBC.tinh_id = this.tinh_ph
          this.$refs.frmDSHoSoBC.khoiTao()
        }, 500)
      }

      // if (!this.state.phieu_id || this.state.phieu_id == 0) {
      //   this.$toast.warning('Bạn chưa chọn phiếu')
      //   return
      // }
      // let data = {
      //   hdtb_id: this.state.hdtb_id,
      //   loaitb_id: this.state.loaitb_id,
      //   onClose: false
      // }
      // if (this.state.vloaitb_id == 116) {
      //   if (await this.PHAILAM(this.state.luong_id, 'CA_GUIYC')) {
      //     data.isToanTrinh = true
      //   }
      //   data.batbuocXemToanBohoSo = true
      // }
      // this.$refs.frmDSHoSoBC.openDialog(data)
    },
    async popupContainerEdit1_CloseUp() {
      await this.HT_POPUP()
    },
    async cboTrangThaiHD_SelectedValueChanged() {
      await this.HT_POPUP()
    },
    async cboGV_SelectedValueChanged() {
      await this.HT_POPUP()
    },
    async cboVip_SelectedValueChanged() {
      await this.HT_POPUP()
    },
    async tsbXemCMS_Click() {
      if (this.gridDanhSach.value == null) {
        this.$toast.warning('Vui lòng chọn phiếu!')
        return
      }
      // try {
      let data = {}
      if (this.gridDanhSach.value.loaitb_id == 116) {
        data.isKHDN = true
        data.tinh_id = this.tinh_ph
        data.in_ma_gd = this.txtMaGD.value
        data.TopMost = true
        this.$refs.frmDoiSoatCA.openDialog(data)
      } else if (this.gridDanhSach.value.loaitb_id == LoaiHinhTB.VNPT_COLOCATION) {
        this.frmDanhSachThueBaoCaiTaoHaTang_IDC.input = {
          hdtb_id: this.hdtb_id,
          tinh_id: this.tinh_ph
        }
        this.ejsDialog.name = 'frmDanhSachThueBaoCaiTaoHaTang_IDC'
        this.ejsDialog.header = 'Danh sách thuê bao cải tạo hạ tầng IDL'
        this.$refs.ejsDialog.show()
      } else {
        this.$toast.info('Phiếu không có thông tin CMS!')
        return
      }
      // } catch (e) {
      //   this.$toast.error(`${e}`)
      // }
    },

    async tsbtnXemHd_thuebao_ct_Click() {
      if (this.gridDanhSach.value == null) {
        this.$toast.warning('Vui lòng chọn phiếu!')
        return
      }
      let loaitb_id = this.gridDanhSach.value.loaitb_id
      if (loaitb_id == 1) {
      } else {
        //nếu không có gì thì sử dụng dynamic hd_thuebao_ct
        let vfield_detail = {
          vloaitb_id: loaitb_id,
          vopt_list: '00000',
          vfield_name_cha: 'THONGTIN_XT'
        }
        this.popupComponentData = {
          vloaitb_id: loaitb_id,
          hdtb_id: this.hdtb_id,
          vappend_name: 'THONGTIN_XT',
          enableEdit: false,
          vfield_detail: vfield_detail,
          vtinh_id: this.tinh_ph
        }

        this.popupComponent = () => import('./components/frmShowHD_THUEBAO_CT_DYNAMIC/frmShowHD_THUEBAO_CT_DYNAMIC.vue')
        this.popupComponentName = 'frmDynamic_Hd_thuebao_ct'
        this.Popup('popupComponents')
      }
    },
    Popup(modalId) {
      this.loading(false)
      if (this.$refs[modalId]) this.$refs[modalId].show()
      else this.$bvModal.show(modalId)
    },
    async tsbtnNhapMoiTT_Click() {
      if (this.gridDanhSach.value == null) {
        this.$toast.warning('Vui lòng chọn phiếu!')
        return
      }
      await this.HT_DLG_NHAP_NOIDUNG(null)
    },
    async gridviewTienTrinh_ShownEditor() {
      if (gridTienTrinh.FocusedRowHandle >= 0) {
        let vnguoi_cn = this.gridTienTrinh.value.NGUOI_CN
        let vtientrinhtc_id = this.gridTienTrinh.value.TIENTRINHTC_ID
        if (vnguoi_cn == tt_nd.ma_nd) {
          if (this.gridTienTrinh.value == SMS_1) {
            await this.HT_DLG_GUI_SMS(vtientrinhtc_id)
          }
          if (this.gridTienTrinh.value == SUA_1) {
            await this.HT_DLG_NHAP_NOIDUNG(vtientrinhtc_id)
          }
          if (this.gridTienTrinh.value == XOA_1) {
            await this.HT_DLG_XOA_TIENTRINH(vtientrinhtc_id)
          }
        } else this.$toast.warning('Bạn không thể thao tác trên tiến trình của người khác')
        this.gridTienTrinh.value = TIENTRINHBH_ID
      }
    },
    async gridTienTrinhRowSelected(e) {
      if (e) {
        this.gridTienTrinh.value = e.data
      }
    },
    async tsbtnTC_CTTC_Click() {
      // let frm = new frmTraCuuPhieuTCBC()
      // frm.vma_tb = txtMaGD.value
      // frm.ShowDialog()
      // this.frmTraCuuPhieuTCBC.input = {
      //   isPopup: true
      // }
      this.ejsDialog.name = 'frmTraCuuPhieuTCBC'
      this.ejsDialog.header = 'TRẢ CỨU CHI TIẾT ĐIỀU HÀNH THI CÔNG TỈNH KHÁC'

      this.$refs.ejsDialog.show()

      setTimeout(() => {
        this.$refs.frmTraCuuPhieuTCBC.isPopup = true
        // this.$refs.frmTraCuuPhieuTCBC.formThongTinPhieuYeuCau.maTBTinh = this.txtMaGD.value
        this.$refs.frmTraCuuPhieuTCBC.formThongTinPhieuYeuCau.maGiaoDich = this.txtMaGD.value
        this.$refs.frmTraCuuPhieuTCBC.onClickSearch()
        // this.$refs.frmTraCuuPhieuTCBC.formThongTinPhieuYeuCau.tinhId
      }, 500)
      // this.$toast.info('frmTraCuuPhieuTCBC')
    },
    async btnUploadFileOneBussiness_FileHandler() {
      let formData = new FormData()
      for (var file of this.$refs.files.files) {
        formData.append('files', file)
      }
      let fileUrls = await this.callApiWrapper(api.upload_file, formData, { headers: { 'Content-Type': 'multipart/form-data' } })
      if (fileUrls.length) {
        let fileName = fileUrls[0].substring(fileUrls[0].lastIndexOf('/') + 1, fileUrls[0].lastIndexOf('.'))
        let k = await this.callApiWrapper(api.sp_bancheo_capnhat_bbnt_dv_onebusiness, {
          vphanvung_id: this.$root.token.getPhanVungID(),
          vtinh_id: this.tinh_ph,
          vurl: fileUrls[0],
          vmay_cn: await this.$root.token.getMachine(),
          vnguoi_cn: this.$root.token.getUserName(),
          vip_cn: await this.$root.token.getIP(),
          vfile_name: fileName,
          vma_tb: this.ma_tb,
          vhdtb_id: this.hdtb_id,
          vma_kh: this.txtMaKH.value,
          vma_gd: this.txtMaGD.value
        })
        this.$toast.success('Cập nhật thành công!')
      } else {
        this.$toast.error('Upload chưa thành công!')
      }
      this.$refs.files.reset()
    },
    async btnUploadFileOneBussiness_Click() {
      if (this.gridDanhSach.value == null) {
        this.$toast.warning('Vui lòng chọn phiếu!')
        return
      }
      this.$refs.files.click()
    },
    async tsbtnImportIDC_Click() {
      if (this.gridDanhSach.value == null) {
        this.$toast.warning('Vui lòng chọn phiếu!')
        return
      }
      this.frmImportThietBi_IDC.input = {
        vhdtb_id: this.hdtb_id,
        loaitb_id: this.gridDanhSach.value.loaitb_id,
        ma_gd: this.txtMaGD.value,
        ma_tb: this.ma_tb,
        tinh_tc: this.tinh_ph
      }
      this.ejsDialog.name = 'frmImportThietBi_IDC'
      this.ejsDialog.header = 'Import Thiết Bị IDC'
      this.$refs.ejsDialog.show()
    },

    async tsbXemTbi_Click() {
      if (this.gridDanhSach.value == null) {
        this.$toast.warning('Vui lòng chọn phiếu!')
        return
      }
      if (this.gridDanhSach.value.loaitb_id == LoaiHinhTB.VNPT_COLOCATION) {
        this.frmDanhSachThietBI_IDC.input = {
          hdtb_id: this.hdtb_id,
          thuebao_id: 0,
          tinh_id: this.tinh_ph
        }
        this.ejsDialog.name = 'frmDanhSachThietBI_IDC'
        this.ejsDialog.header = 'Danh sách thiết bị đã đăng ký'
        this.$refs.ejsDialog.show()
      }
      if (this.cboLoaihinhTB.value == LoaiHinhTB.Voice_Brandname) {
        if (this.txtKieuLD.value == 'Đăng ký ngoại mạng Voice Brandname' || this.txtKieuLD.value == 'Hủy ngoại mạng Voice Brandname' || this.txtKieuLD.value == 'Hủy Brandname ngoại mạng - DV Voice Brandname') {
          console.log('Nhập danh sách số ngoại mạng', this.hdtb_id)
          // this.popup.frmNhapDSThanhVienNgoaiMang = true
          this.$refs.frmNhapDSThanhVienNgoaiMang.hdtb_id = this.hdtb_id
          this.$refs.frmNhapDSThanhVienNgoaiMang.tinh_id = this.tinh_ph
          this.$refs.frmNhapDSThanhVienNgoaiMang.ShowDialog()
        } else {
          this.frmDanhSachSoThanhVien.input = {
            hdtb_id: this.hdtb_id,
            thuebao_id: 0,
            tinh_id: this.tinh_ph
          }
          this.ejsDialog.name = 'frmDanhSachSoThanhVien'
          this.ejsDialog.header = 'Danh sách số thành viên'
          this.$refs.ejsDialog.show()
        }
      } else if (this.gridDanhSach.value.loaitb_id == 404) {
        this.popupComponentData = {
          hdtb_id: this.hdtb_id,
          thuebao_id: 0,
          tinh_id: this.tinh_ph,
          enableEdit: false
        }
        this.ejsDialog.name = 'frmGanSimVaoKenh'
        this.ejsDialog.header = 'Danh sách gán sim vào kênh'
        this.$refs.ejsDialog.show()
      }
    },

    handleLineClick(e) {
      let clickedElId = e.target.id
      if (clickedElId === 'btnXemThongTinChiTiet') {
        this.tsbXemTbi_Click();
      }
    },

    // XemThongTinChiTiet() {
    //   console.log('ok')
    //   alert("OK");
    // },
    async tsbtnTraPhieuKHDN_Click() {
      // let frm = new frmTraPhieuTC(ma_tb, vloaihd_id, dichvuvt_id, tt_nd.donvi_id, ma_tb, '0', '0', 3)
      // frm.ShowDialog()
      // if (frm.kt_thuchien) {
      //   tsbtnLayTT.PerformClick()
      // }

      this.frmTraPhieuTC.input = {
        isPopup: true
      }
      this.ejsDialog.name = 'frmTraPhieuTC'
      setTimeout(() => {
        this.$refs.frmTraPhieuTC.frmTraPhieuTC(this.txtMaGD.value, this.vloaihd_id, this.dichvuvt_id, this.$root.token.getDonViID(), this.ma_tb, '0', '0', 3)
      }, 300)
      this.ejsDialog.header = 'TRẢ PHIẾU THI CÔNG'
      this.$refs.ejsDialog.show()
    },
    async btnSuaTSKT_Clicked() {
      // v-b-modal.id-modal-capnhat-cntt
    },
    async frmCapnhatCNTT_XacNhan() {
      await this.HT_THONGSO_KYTHUAT(this.gridDanhSach.value.tinh_id)
    },
    async frmNoiDung_btnGhiLai_Clicked() {
      await this.NAP_GRD_TIENTRINH()
    },
    async frmNhapText_tsbtnXacNhan_Clicked(frm) {
      let cmt = frm.Output
      let vkieu = 0
      if (this.tinh_khdn.includes(this.tinh_ph)) vkieu = 1
      else vkieu = 2
      let kq = await this.callApiWrapper(api.sp_chuyen_vip_giamsat, {
        vphanvung_id: this.$root.token.getPhanVungID(),
        vkieu: vkieu,
        vhdtb_id: this.hdtb_id,
        vphieu_id: this.gridDanhSach.value.phieu_id,
        vdonvi_nhan_id: this.cboVip.value,
        vnhanvien_id: this.$root.token.getNhanVienID(),
        vnoidung: cmt,
        vmay_cn: await this.$root.token.getMachine(),
        vnguoi_cn: this.$root.token.getUserName()
      })
      if (kq == 'OK') {
        this.$toast.success('Chuyển Vip thành công !')
        await this.LayPhieu()
        this.$refs.ejsDialog.hide()
      } else {
        this.$toast.success('Chuyển Vip chưa thành công !')
      }
    },
    async frmNhapText_tsbtnHuy_Clicked() {
      this.$refs.ejsDialog.hide()
    },
    async frmTraPhieuVIP_onBtnTraphieuClicked() {
      await this.tsbtnLayTT_Click()
      this.$refs.ejsDialog.hide()
    },
    async tsbtnTest_Click() {
      this.frmCapnhatCNTT.input = {
        isPopup: true
      }
      this.ejsDialog.name = 'frmCapnhatCNTT'
      this.ejsDialog.header = 'TRẢ PHIẾU THI CÔNG'
      this.$refs.ejsDialog.show()
    },

    async show_popupSLAs() {
      this.ppData.frmSLAs = {
        hdtb_id: this.hdtb_id,
        tinh_id: this.tinh_ph,
        total: this.dtSLAs.duration.total,
      }
      console.log(this.ppData.frmSLAs)
      this.isShow.frmSLAs = true
      this.$refs.popupSLAs.show()
    },
    async close_popupSLAs() {

      this.$refs.popupSLAs.hide()
      this.isShow.frmSLAs = false
    },

    ejsDialogBeforeOpen() {
      let width = null
      if (this.ejsDialog.name == 'frmNhapText') {
        width = '50%'
      } else {
        width = '95%'
      }
      if (this.ejsDialog.name == 'frmTraCuuHopDong') {
        this.ejsDialog.config.height = 800
      } else {
        this.ejsDialog.config.height = 'auto'
      }
      this.ejsDialog.config = {
        ...this.ejsDialog.config,
        width: width,
        // position: { X: 100, Y: 100 }
        position: { X: 'center', Y: 'center' }
      }
      // this.ejsDialog.config.height = 'auto'
      // this.$refs.ejsDialog.refreshPosition()
      // this.ejsDialog.config.enableResize = false
      // this.ejsDialog.config.allowDragging = false
      // this.ejsDialog.config.position = { X: 'center', Y: 'center' }
      // this.ejsDialog.config.position = { X: 420, Y: 14 }
      // }, 3000)
    },
    ejsDialogBeforeClose() {
      this.ejsDialog.name = null
      // this.$refs.ejsDialog.refreshPosition()
      this.ejsDialog.config.width = '0'
      this.ejsDialog.config.height = '0'
      this.ejsDialog.config.position = { X: 'center', Y: 'center' }
      if (this.ejsDialog.name == 'frmTraPhieuCNTT') {
        this.tsbtnLayTT_Click()
      }
    },
    uuidv4() {
      return ([1e7] + -1e3 + -4e3 + -8e3 + -1e11).replace(/[018]/g, (c) => (c ^ (crypto.getRandomValues(new Uint8Array(1))[0] & (15 >> (c / 4)))).toString(16))
    },
    popupClosed(val) {
      // this.loading(true)
      // this.ClosePopup('popupComponents')
      // this.loading(false)
    },
    ClosePopup(modalId) {
      this.$bvModal.hide(modalId)
      this.popupComponent = null
    },
    emit_func(data) {},
    async lay_thoigian_sla_theo_hdtbid() {
      this.stopCountDown()
      this.close_popupSLAs()
      // this.show_popupSLAs()
      // console.log(123)
      // console.log(this.dulieu)
      try {
        let rs = await api.lay_thoigian_sla_theo_hdtbid({
          "hdtb_id": this.hdtb_id,
          "phanvung_id": this.tinh_ph
        })
        let data = JSON.parse(rs.data.data)
        this.dtSLAs.error_code = data.error_code
        if(data.error_code == 200) {
          this.dtSLAs.sla_time = data.sla_time
          this.dtSLAs.ngaygiao = data.ngaygiao
          let sla_time = Math.floor(Number(data.sla_time));               // Chuyển đổi và làm tròn xuống
          let diff_in_second = Math.floor(Number(data.diff_in_second));
          let duration = (sla_time * 60)  - diff_in_second;
          this.countDown(duration)
        }

      }
      catch (e) {
        console.log(e)
      }
      finally {

      }
    },
    formatTime(total) {
      let absSeconds = Math.abs(total);
      this.dtSLAs.duration.total = total;
      let hours = Math.floor(absSeconds / 3600);
      let minutes = Math.floor((absSeconds % 3600) / 60);
      let secs = absSeconds % 60;

      this.dtSLAs.duration.hours = total < 0 ? -hours : hours;  // Hiển thị giá trị âm nếu là thời gian âm
      this.dtSLAs.duration.minutes = minutes; // minutes: minutes,
      this.dtSLAs.duration.seconds = secs; // seconds: secs

      if(total < 0) {
        this.dtSLAs.status = this.dtSLAs.status_list[2]
      }
      else {
        this.dtSLAs.status = this.dtSLAs.status_list[0]
      }
    },

    countDown(total) {
      this.dtSLAs.interval_CountDown = setInterval(() => {
        // Chuyển đổi số giây còn lại thành giờ, phút, giây
        this.formatTime(total);
        // console.log(`${this.dtSLAs.duration.hours}:${this.dtSLAs.duration.minutes}:${this.dtSLAs.duration.seconds}`);

        // Cập nhật giao diện người dùng (nếu cần)
        // document.getElementById('timer-display').textContent = `${time.hours}:${time.minutes}:${time.seconds}`;

        // Nếu total là 0 hoặc nhỏ hơn, tiếp tục trừ để chuyển sang âm
        total--;

      }, 1000); // Cập nhật mỗi giây (1000 ms)
    },
    stopCountDown() {
      // Dừng interval nếu nó đang chạy
      if (this.dtSLAs.interval_CountDown) {
        clearInterval(this.dtSLAs.interval_CountDown);
        console.log('Interval đã ngắt');
      }
    },

  }
}
</script>
<style scoped>

* {
  font-size: 12px;
  /* padding: 0;
    margin: 0; */
}

.disabledInput {
  pointer-events: none;
  color: #666 !important;
  border-color: #9999 !important;
}

.disabledInput > a {
  color: #666 !important;
}

.section-title-wrapper {
  padding: 8px;
  border-radius: 5px;
  background: #c2e6fc;
  display: flex;
}

.section-title-wrapper > .section-title {
  font-size: 14px;
  font-weight: bold;
}

.row-1-height {
  /* height: 250px; */
  /* background: red; */
  overflow: scroll;
}

.popupContainerControl1 {
  width: 900px;
  position: absolute;
  left: 0px;
  z-index: 100;
  margin: 10px 0 0 0;
}

.popupContainerControl1Drop {
  min-width: 100%;
  min-height: 100%;
  position: absolute;
  background: #41464e2a;
  z-index: 99;
}

#frmGiaoViecVIP {
  position: absolute;
  top: 0px;
  left: 0px;
  right: 0px;
  bottom: 0px;
  overflow: auto;
  overflow-x: hidden;
}
.e-dialog {
  overflow-y: auto;
}
.e-dlg-header-content {
  display: none;
}
.modal-lg {
  max-width: auto !important;
}
</style>
