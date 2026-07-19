<template>
  <div id="frmHoanCongIMS_Net">
    <breadcrumb :header="header" />
    <div class="list-actions-top">
      <ul class="list">
        <li @click="btnLayTTMoi_Click">
          <a> <span class="icon one-reload1"></span>Lấy TT (F5)</a>
        </li>
        <li v-if="tsbtnChapNhan.visible" :class="{ disabledInput: !tsbtnChapNhan.enabled }" @click="tsbtnChapNhan_Click">
          <a> <span class="icon one-file-plus"></span>Cập Nhật (F9)</a>
        </li>
        <li v-if="tsbtnHuyKH.visible" :class="{ disabledInput: !tsbtnHuyKH.enabled }" @click="tsbtnHuyKH_Click">
          <a> <span class="icon one-save"></span>Hủy kích hoạt</a>
        </li>
        <li v-if="tsbtnKichHoat.visible" :class="{ disabledInput: !tsbtnKichHoat.enabled }" @click="tsbtnKichHoat_Click">
          <a> <span class="icon one-save"></span>Kích hoạt</a>
        </li>
        <li v-if="tsbtnGiaoViec.visible" :class="{ disabledInput: !tsbtnGiaoViec.enabled }" @click="tsbtnGiaoViec_Click">
          <a> <span class="icon one-file-edit2"></span>Giao việc</a>
        </li>
        <li v-if="tsbtnGiaoPhoiHop.visible" :class="{ disabledInput: !tsbtnGiaoPhoiHop.enabled }" @click="toolStripButton1_Click">
          <a> <span class="icon one-file-edit2"></span>Giao P/H</a>
        </li>
        <li v-if="tsbtnHoanThanh.visible" :class="{ disabledInput: !tsbtnHoanThanh.enabled }" @click="tsbtnHoanThanh_Click">
          <a> <span class="icon nc-icon-glyph ui-1_circle-add"></span>Hoàn thành</a>
        </li>
        <li v-if="tsbtnUploadFileNT.visible" :class="{ disabledInput: !tsbtnUploadFileNT.enabled }" @click="$refs.files.click()">
          <a> <span class="icon fa fa-cloud-upload"></span>Upload File N/T</a>
          <input style="display: none" type="file" multiple="multiple" class="file" ref="files" id="upload-file" @change="changeFileUpload" />
        </li>
        <li v-if="tsbtnDownloadFileNT.visible" :class="{ disabledInput: !tsbtnDownloadFileNT.enabled }" @click="tsbtnDownloadFileNT_Click">
          <a> <span class="icon one-file-download"></span>Download File N/T</a>
        </li>
        <li v-if="tsbtnTraPhieuTinh.visible" :class="{ disabledInput: !tsbtnTraPhieuTinh.enabled }" @click="tsbtnTraPhieuTinh_Click">
          <a> <span class="icon one-file-back1"></span>Trả phiếu Tỉnh</a>
        </li>
        <!-- <li v-if="tsbtnNhapMoiTT.visible" :class="{ disabledInput: !tsbtnNhapMoiTT.enabled }" @click="one - back1">
          <a> <span class="icon one-file-plus "></span>Nhập mới TT</a>
        </li> -->
      </ul>
    </div>
    <div class="page-content" style="margin: 0 0 0 0">
      <div class="box-form">
        <div class="row">
          <div class="col-sm-3 col-12 info-row">
            <div class="key w80">NV tiếp thị</div>
            <div class="value">
              <input :disabled="!txtNV_TiepThi.enabled" v-model="txtNV_TiepThi.value" type="text" class="form-control" />
            </div>
          </div>
          <div class="col-sm-3 col-12 info-row">
            <div class="key w80">Kiểu HĐ</div>
            <div class="value">
              <div class="select-custom">
                <SelectExt :disabled="!cboKieu_HD.enabled" v-model="cboKieu_HD.value" :dataSource="cboKieu_HD.list" dataTextField="KIEU_HD" dataValueField="KIEUHD_ID" />
              </div>
            </div>
          </div>
          <div class="col-sm-3 col-12 info-row">
            <div class="key w80">Kênh thu</div>
            <div class="value">
              <div class="select-custom">
                <SelectExt :disabled="!cboKenhThu.enabled" v-model="cboKenhThu.value" :dataSource="cboKenhThu.list" dataTextField="KENHTHU" dataValueField="KENHTHU_ID" />
              </div>
            </div>
          </div>
          <div class="col-sm-3 col-12 info-row">
            <div class="key w80">Tổng tiền</div>
            <div class="value">
              <input :disabled="!txtTongTien.enabled" v-model="txtTongTien.value" type="text" class="form-control tright red bold" value="0" />
            </div>
          </div>
        </div>
      </div>
      <div class="box-form">
        <div class="legend-title" style="display: flex; align-items: center">
          <div class="pull-left">Thông tin khách hàng</div>
          <div class="col-sm-2 col-12 pull-left">
            <div class="check-action normal bold" style="color: rgb(108 104 104)">
              <input :disabled="!chkHen.enabled" v-model="chkHen.value" type="checkbox" class="check" id="chkHen" />
              <span class="name">Lắp đặt theo yêu cầu</span>
            </div>
          </div>
          <div class="col-sm-2 col-12 pull-left">
            <input :disabled="!txtSoDTLH.enabled" v-model="txtSoDTLH.value" type="text" class="form-control" />
          </div>
          <div class="col-sm-3 col-12 pull-left">
            <span v-if="lblNhanVien.enabled" class="item bold red">{{ lblNhanVien.value }}</span>
            <!-- <span class="item bold red">ThongTinKH.txtTenNV_YC</span>-->
          </div>
          <div class="clearfix"></div>
        </div>
        <div class="row">
          <div class="col-sm-7 col-12">
            <div class="row">
              <div class="col-sm-4 col-12 info-row">
                <div class="key w80">Loại HĐ/PL</div>
                <div class="value">
                  <SelectExt :disabled="!cboLoaiHD.enabled" v-model="cboLoaiHD.value" :dataSource="cboLoaiHD.list" dataTextField="TEN_LOAIHD" dataValueField="LOAIHD_ID" @select="cboLoaiHD_SelectedValueChanged" />
                </div>
              </div>
              <div class="col-sm-8 col-12 info-row">
                <div class="key w80">Quy trình</div>
                <div class="value">
                  <SelectExt :disabled="!cboQuyTrinh.enabled" class="" v-model="cboQuyTrinh.value" :dataSource="cboQuyTrinh.list" dataTextField="quytrinh" dataValueField="quytrinh_id" @select="cboQuyTrinh_SelectedValueChanged" />
                </div>
              </div>
              <div class="col-sm-4 col-12 info-row">
                <div class="key w80">Mã GD</div>
                <div class="value">
                  <input :disabled="!txtMaGD.enabled" v-model="txtMaGD.value" type="text" class="form-control highlight bold" v-on:keyup.enter="MaGD_KeyUp" id="txtMaGD" />
                </div>
              </div>
              <div class="col-sm-4 col-12 info-row">
                <div class="key w80">Account <span class="red">(*)</span></div>
                <div class="value">
                  <input :disabled="!txtMaTB.enabled" v-model="txtMaTB.value" type="text" class="form-control highlight bold" v-on:keyup.enter="MaTB_KeyUp" />
                </div>
              </div>
              <div class="col-sm-4 col-12 info-row">
                <div class="key w80">Mã TC <span class="red">(*)</span></div>
                <div class="value">
                  <input :disabled="!txtMaTC.enabled" v-model="txtMaTC.value" type="text" class="form-control highlight" />
                </div>
              </div>
              <div class="col-sm-4 col-12 info-row">
                <div class="key w80">Kiểu LĐ</div>
                <div class="value">
                  <input :disabled="!txtTenKieuLD.enabled" v-model="txtTenKieuLD.value" type="text" class="form-control bold" />
                </div>
              </div>
              <div class="col-sm-8 col-12 info-row">
                <div class="key w80">Loại hình <span class="red">(*)</span></div>
                <div class="value">
                  <input :disabled="!txtLoaiHinh.enabled" v-model="txtLoaiHinh.value" type="text" class="form-control highlight bold" />
                </div>
              </div>
            </div>
          </div>
          <div class="col-sm-5 col-12">
            <div class="row">
              <div class="col-sm-8 col-12 info-row">
                <div class="key w100">Tên thuê bao</div>
                <div class="value">
                  <input :disabled="!txtTenTB.enabled" v-model="txtTenTB.value" type="text" class="form-control bold" />
                </div>
              </div>
              <div class="col-sm-4 col-12 info-row">
                <div class="key w60">Khu vực</div>
                <div class="value">
                  <input :disabled="!txtKhuvuc.enabled" v-model="txtKhuvuc.value" type="text" class="form-control bold" />
                </div>
              </div>
              <div class="info-row">
                <div class="key w100">Địa chỉ LĐ</div>
                <div class="value">
                  <input :disabled="!txtDiaChiLD.enabled" v-model="txtDiaChiLD.value" type="text" class="form-control bold" />
                </div>
              </div>
              <div class="info-row">
                <div class="key w100">Địa chỉ LĐ cũ</div>
                <div class="value">
                  <input :disabled="!txtDiaChiLD_cu.enabled" v-model="txtDiaChiLD_cu.value" type="text" class="form-control bold" />
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
      <div class="row">
        <div class="col-sm-7 col-12">
          <div class="box-form bottom-container" style="display: flex; flex-direction: column">
            <section>
              <div class="section-title-wrapper" style="margin: 0 0 10px 0">
                <div class="section-title">Danh sách khách hàng</div>
              </div>
              <div class="row">
                <div class="col-sm-12 col-12 info-row">
                  <div class="key w100">Tổng đài</div>
                  <div class="value">
                    <SelectExt :disabled="!cboTongDaiIMS.enabled" v-model="cboTongDaiIMS.value" :dataSource="cboTongDaiIMS.list" dataTextField="name" dataValueField="id"></SelectExt>
                  </div>
                </div>
                <div class="col-sm-6 col-12 info-row">
                  <div class="key w100">Endpoint</div>
                  <div class="value">
                    <SelectExt :disabled="!cboEndPoint.enabled" v-model="cboEndPoint.value" :dataSource="cboEndPoint.list" dataTextField="name" dataValueField="id"></SelectExt>
                  </div>
                </div>
                <div class="col-sm-6 col-12">
                  <div class="row">
                    <div class="col-sm-8 col-8 info-row">
                      <div class="key w100">IP</div>
                      <div class="value">
                        <input :disabled="!txtIP.enabled" v-model="txtIP.value" type="text" class="form-control highlight bold" />
                      </div>
                    </div>
                    <div class="col-sm-4 col-4 info-row">
                      <div class="key w20">PORT</div>
                      <div class="value">
                        <input v-model="port" type="text" class="form-control highlight bold" />
                      </div>
                    </div>
                  </div>
                </div>
                <div class="col-sm-6 col-12 info-row">
                  <div class="key w100">Profile</div>
                  <div class="value">
                    <input :disabled="!txtProfile.enabled" v-model="txtProfile.value" type="text" class="form-control bold" />
                  </div>
                </div>
                <div class="col-sm-6 col-12 info-row">
                  <div class="key w100">IP Phụ</div>
                  <div class="value">
                    <input :disabled="!txtIPPhu.enabled" v-model="txtIPPhu.value" type="text" class="form-control bold" />
                  </div>
                </div>
                <div class="col-sm-6 col-12 info-row">
                  <div class="key w100">Trunk ID</div>
                  <div class="value">
                    <input :disabled="!txtTrunkID.enabled" v-model="txtTrunkID.value" type="text" class="form-control bold" />
                  </div>
                </div>
                <div class="col-sm-6 col-12 info-row">
                  <div class="key w100">Số kênh đồng thời</div>
                  <div class="value">
                    <input :disabled="!txtSoKenh.enabled" v-model="txtSoKenh.value" type="text" class="form-control bold" />
                  </div>
                </div>
                <div class="col-sm-6 col-12 info-row">
                  <div class="key w100">Username</div>
                  <div class="value">
                    <input :disabled="!txtUserName.enabled" v-model="txtUserName.value" type="text" class="form-control bold" />
                  </div>
                </div>
                <div class="col-sm-6 col-12 info-row">
                  <div class="key w100">Password</div>
                  <div class="value">
                    <input :disabled="!txtPassword.enabled" v-model="txtPassword.value" type="text" class="form-control bold" />
                  </div>
                </div>
                <div class="col-sm-12 col-12 info-row">
                  <div class="key w100">Profile Info</div>
                  <div class="value">
                    <input :disabled="!txtProfileInfo.enabled" v-model="txtProfileInfo.value" type="text" class="form-control bold" />
                  </div>
                </div>
              </div>
            </section>
            <div style="flex-grow: 1; display: flex; flex-direction: column">
              <section style="margin-top: 15px; flex-grow: 1; background: transparent">
                <ejs-tab ref="tabObj" id="tab_wizard" heightAdjustMode="None" height="100%" @selected="onTabSelected">
                  <e-tabitems>
                    <e-tabitem v-for="(item, index) in ttkttdTabs" :key="index" :header="item.header" :content="item.content"></e-tabitem>
                  </e-tabitems>
                </ejs-tab>
                <div id="dgvChiTiet" style="display: none; height: 100%; background: transparent">
                  <DataGrid :columns="dgvChiTiet.cols" :showColumnCheckbox="true" :dataSource="dgvChiTiet.list" :showFilter="true" :allowPaging="true" :enablePagingServer="false" :totalRecords="dgvChiTiet.list.length"> </DataGrid>
                </div>
                <div id="gridTienTrinh" style="display: none">
                  <DataGrid :columns="gridTienTrinh.cols" :showColumnCheckbox="true" :dataSource="gridTienTrinh.list" :showFilter="true" :allowPaging="true" :enablePagingServer="false" :totalRecords="gridTienTrinh.list.length"> </DataGrid>
                </div>
                <div id="gridDangkyDV" style="display: none">
                  <DataGrid :columns="gridDangkyDV.cols" :showColumnCheckbox="true" :dataSource="gridDangkyDV.list" :showFilter="true" :allowPaging="true" :enablePagingServer="false" :totalRecords="gridDangkyDV.list.length"> </DataGrid>
                </div>
                <div id="gridNhanVien" style="display: none">
                  <DataGrid :columns="gridNhanVien.cols" :showColumnCheckbox="true" :dataSource="gridNhanVien.list" :showFilter="true" :allowPaging="true" :enablePagingServer="false" :totalRecords="gridNhanVien.list.length"> </DataGrid>
                </div>
              </section>
              <section style="flex-grow: 1">
                <div class="section-title-wrapper" style="margin: 10px 0">
                  <div class="section-title" style="margin-right: 15px">Danh sách thuê bao</div>
                  <div class="item section-radio-menu">
                    <div class="check-action">
                      <input type="radio" name="chon_phieu" class="check" v-model="rdoPhieu.value" value="0" @change="rdoPhieuMoi_CheckedChanged" />
                      <span class="name">Phiếu mới</span>
                    </div>
                  </div>
                  <div class="item section-radio-menu">
                    <div class="check-action">
                      <input type="radio" name="chon_phieu" class="check" v-model="rdoPhieu.value" value="1" @change="rdoPhieuTra_CheckedChanged" />
                      <span class="name">Phiếu trả lại</span>
                    </div>
                  </div>
                  <div class="check-action section-radio-menu">
                    <input type="radio" name="chon_phieu" class="check" v-model="rdoPhieu.value" value="2" @change="rdoPhieuDaHoanThanh_CheckedChanged" />
                    <span class="name">Phiếu đã hoàn thành</span>
                  </div>
                  <div v-if="lblTongS.enabled" style="margin-left: 15px; font-weight: bold; color: maroon">
                    {{ `Tổng số: ${gridDanhSach.selected.length}/${gridDanhSach.list.length}` }}
                  </div>
                </div>
                <!-- @selectedItemsChanged="gvDanhSach_SelectionChanged" -->
                <DataGrid
                  ref="gridDanhSach"
                  :columns="gridDanhSach.cols"
                  :dataSource="gridDanhSach.list"
                  :selectionSettings="{ type: 'Multiple', enableToggle: false, checkboxOnly: false, checkboxMode: 'ResetOnRowClick' }"
                  @rowSelected="gvDanhSach_SelectionChanged"
                  @selectedItemsChanged="gridDanhSach_selectedItemsChanged"
                  :showFilter="true"
                  :allowPaging="true"
                  :enablePagingServer="false"
                  :showColumnCheckbox="true"
                  :totalRecords="gridDanhSach.list.length"
                />
              </section>
            </div>
          </div>
        </div>
        <div class="col-sm-5 col-12">
          <div class="box-form bottom-container">
            <div class="section-title-wrapper" style="margin: 0 0 5px 0">
              <div class="section-title" style="margin: 0 15px 0 0">Thông tin hoàn công</div>
              <div class="item section-radio-menu">
                <div class="check-action">
                  <input :disabled="!chkLayTSo.enabled" v-model="chkLayTSo.value" type="checkbox" class="check" value="1" />
                  <span class="name" style="color: maroon">Lấy thông số thi công</span>
                </div>
              </div>
              <div>
                <span v-if="txtProfileInfo.enabled" style="color: red; font-weight: bold; font: size 18px">{{ lblLienHe.value }}</span>
              </div>
            </div>
            <section>
              <div class="section-title-through-wrapper" style="margin: 0 0 0 0">
                <div class="section-title-through-text" style="margin-right: 15px">Thông số kỹ thuật</div>
                <div class="line-through"></div>
              </div>
              <div style="flex-grow: 1; background: white; margin: 0 0 10px 0">
                <div ref="wbrTSKT" v-html="wbrTSKT.value" class="info-wrapper"></div>
              </div>
            </section>
            <div class="row">
              <div class="col-sm-6 col-12 info-row">
                <div class="key w80">Người GV</div>
                <div class="value">
                  <SelectExt :disabled="!cboNguoiGV.enabled" v-model="cboNguoiGV.value" :dataSource="cboNguoiGV.list" dataTextField="TEN_NV" dataValueField="NHANVIEN_ID"></SelectExt>
                </div>
              </div>
              <div class="col-sm-6 col-12 info-row">
                <div class="key w80">ND giao</div>
                <div class="value">
                  <input :disabled="!txtNDGiao.enabled" v-model="txtNDGiao.value" type="text" class="form-control highlight bold" />
                </div>
              </div>
              <div class="col-sm-6 col-12 info-row">
                <div class="key w80">Nội dung TH</div>
                <div class="value">
                  <div class="input-more-button">
                    <input :disabled="!txtNoiDungTH.enabled" v-model="txtNoiDungTH.value" type="text" class="form-control highlight bold" v-on:keyup.enter="MaGD_KeyUp" id="txtMaGD" />
                    <button class="btn" @click="btnNDTH_Click">
                      <span class="-ap icon-more_horiz"></span>
                    </button>
                  </div>
                </div>
              </div>
              <div class="col-sm-6 col-12 info-row">
                <div class="key w80">ND trả</div>
                <div class="value">
                  <input :disabled="!txtThongTinTra.enabled" v-model="txtThongTinTra.value" type="text" class="form-control bold" />
                </div>
              </div>
              <div class="col-sm-6 col-12 info-row">
                <div class="key w80">Lý do trả</div>
                <div class="value">
                  <input :disabled="!txtLyDoTra.enabled" v-model="txtLyDoTra.value" type="text" class="form-control highlight bold" />
                </div>
              </div>
              <div class="col-sm-6 col-12 info-row">
                <div class="key w80" style="display: flex; justify-content: center; align-items: center">
                  Ngày GV
                  <div class="check-action" style="margin: 0 0 0 5px">
                    <input :disabled="!chkNgayGV.enabled" v-model="chkNgayGV.value" type="checkbox" class="check" />
                    <span class="name"></span>
                  </div>
                </div>
                <div class="value">
                  <vue-date :disable="!dtpNgayGV.enabled" v-model="dtpNgayGV.value" format="DD/MM/YYYY" type="datetime"></vue-date>
                </div>
              </div>
              <div class="col-sm-6 col-12 info-row">
                <div class="key w80">Liên hệ</div>
                <div class="value">
                  <input :disabled="!txtLienHe.enabled" v-model="txtLienHe.value" type="text" class="form-control highlight bold" />
                </div>
              </div>
              <div class="col-sm-6 col-12 info-row">
                <div class="key w80" style="display: flex; justify-content: center; align-items: center">
                  Ngày HT
                  <div class="check-action" style="margin: 0 0 0 5px">
                    <input :disabled="!chkNgayBG.enabled" v-model="chkNgayBG.value" type="checkbox" class="check" />
                    <span class="name"></span>
                  </div>
                </div>
                <div class="value">
                  <vue-date :disable="!dtpNgayBG.enabled" v-model="dtpNgayBG.value" format="DD/MM/YYYY" type="datetime"></vue-date>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>

    <!-- <SearchContractModal ref="popupSearchContract" @accept="acceptSearchContract" :donvi_id="0" :nhanvien_id="0" :donvi_dl_id="donvi_dl_id" /> -->
    <frmGiaoPhieu_Net ref="frmGiaoPhieu_Net" />
    <frmDSHoSoBC ref="frmDSHoSoBC" v-bind="frmDSHoSoBC.input" />
    <ejs-dialog :enableResize="true" :visible="false" ref="popupfrnGiaoPhieuNV" :header="'Giao phiếu nhân viên'" showCloseIcon="true" target="#frmHoanCongIMS_Net" width="90%" height="800px" :allowDragging="true">
      <div style="height: 600px"><frnGiaoPhieuNV ref="frnGiaoPhieuNV" @giaoviec_success="giaoviec_success" /></div>
    </ejs-dialog>
    <ejs-dialog
      ref="ejsDialog"
      :visible="false"
      :isModal="true"
      target="#frmHoanCongIMS_Net"
      :enableResize="ejsDialog.config.enableResize"
      :resizeHandles="['All']"
      :header="ejsDialog.header"
      showCloseIcon="true"
      :width="ejsDialog.config.width"
      :height="ejsDialog.config.height"
      :minHeight="ejsDialog.config.minHeight"
      :allowDragging="ejsDialog.config.allowDragging"
      :position="ejsDialog.config.position"
      :animationSettings="ejsDialog.config.animationSettings"
      @beforeOpen="ejsDialogBeforeOpen"
      @beforeClose="ejsDialogBeforeClose"
    >
      <div v-if="ejsDialog.name" :key="uuidv4()" style="overflow-y: scroll">
        <frmNoiDung v-if="ejsDialog.name == 'frmNoiDung'" ref="frmNoiDung" v-bind="frmNoiDung.input" />
        <!-- <frmGiaoPhieu_NET v-if="ejsDialog.name == 'frmGiaoPhieu_NET'" ref="frmGiaoPhieu_NET" v-bind="frmGiaoPhieu_NET.input" @giaoviec_success="giaoviec_success" /> -->
        <frmTraPhieuVIP v-if="ejsDialog.name == 'frmTraPhieuVIP'" ref="frmTraPhieuVIP" v-bind="frmTraPhieuVIP.input" @onBtnTraphieuClicked="frmTraPhieuVIP_onBtnTraphieuClicked" />
      </div>
    </ejs-dialog>
  </div>
</template>
<script>
import Vue from 'vue'
import { enableRipple, createElement } from '@syncfusion/ej2-base'
import { ContextMenuPlugin } from '@syncfusion/ej2-vue-navigations'
Vue.use(ContextMenuPlugin)
import api from './api'
import moment from 'moment'
import XLSX from 'xlsx'
import breadcrumb from '@/components/breadcrumb'
import SearchContractModal from '@/modules/contract/setup/HandoverTicket/components/SearchContractModal.vue'
import { TabPlugin } from '@syncfusion/ej2-vue-navigations'
Vue.use(TabPlugin)
import { DichVuVienThong, LoaiHopDong, TrangThaiHD } from './constants'
import { FORM_CONFIG, DS_QUYEN_VT, LOAI_KHO, MUCDICH_VT, LOAIPHIEU_DENGHI_VATTU } from '@/modules/QLVT/NhapXuatVT_v2/const'
import DateTimeHelper from '@/modules/QLVT/NhapXuatVT_v2/helpers/dateHelper'
import { onebssIdIsValid, convertToUpcaseKey, isNullOrEmpty, getDateStringOrDefault, generateComponentKey } from '@/modules/QLVT/NhapXuatVT_v2/helpers'

export default {
  name: 'frmHoanCongIMS_Net',
  components: {
    breadcrumb,
    SearchContractModal,
    // frmGiaoPhieu_NET: () => import('@/modules/QLSC/frmGiaoPhieu_NET'),
    frmGiaoPhieu_Net: () => import('@/modules/contract/complete_profile/CompleteProfileITVAS/popups/popupGiaoPhieuNet/GiaoPhieuNet'),
    frmNoiDung: () => import('./components/frmNoiDung'),
    frnGiaoPhieuNV: () => import('@/modules/contract/setup/DeclareLandline/popups/frnGiaoPhieuNV'),
    frmTraPhieuVIP: () => import('./components/frmTraPhieuVIP'),
    frmDSHoSoBC: () => import('@/modules/contract/setup/LookupNavigateContract/popup/FrmXemHS')
  },
  data() {
    return {
      localLoading: 0,
      header: {
        title: 'Hoàn công IMS NET',
        list: [
          { name: 'Trang chủ', link: { name: 'Ui.cards' } },
          { name: 'Lập hợp đồng', link: { name: 'Ui.cards' } },
          { name: 'Lắp đặt mới', link: { name: 'Ui.cards' } }
        ]
      },
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
      frnGiaoPhieuNV: { visible: false, input: {} },
      tinh_khdn: [97, 100],
      cmenuMoreItems: [
        { id: 'hoan_thanh', child_text: 'Hoàn thành', child_icon: 'one-save1' }
        // {
        //   id: 'in_phieu',
        //   child_text: 'In phiếu',
        //   child_icon: 'one-print',
        //   items: [
        //     { id: 'phieu_nhap', child_text: 'Phiếu nhập', child_icon: 'one-print' },
        //     { id: 'phieu_xuat', child_text: 'Phiếu xuất', child_icon: 'one-print' },
        //     { id: 'phieu_chuyen', child_text: 'Phiếu chuyển', child_icon: 'one-print' },
        //     { id: 'chi_tiet_sr', child_text: 'Chi tiết SR', child_icon: 'one-print' },
        //     { id: 'phieu_tam_ung_vt', child_text: 'Phiếu tạm ứng VT', child_icon: 'one-print' },
        //     { id: 'bbgn_nhap_kho', child_text: 'BBGN Nhập kho', child_icon: 'one-print' },
        //     { id: 'bbgn_xuat_kho', child_text: 'BBGN Xuất kho', child_icon: 'one-print' },
        //     { id: 'phieu_xuat_dvtc', child_text: 'Phiếu xuất ĐVTC', child_icon: 'one-print' },
        //     { id: 'in_tem_serial', child_text: 'In tem Serial', child_icon: 'one-print' },
        //     { id: 'phieu_nhap_kho_khong_thue', child_text: 'Phiếu nhập kho (không thuế)', child_icon: 'one-print' },
        //     { id: 'phieu_giao_hoan_tra_vt', child_text: 'Phiếu giao hoàn trả vật tư', child_icon: 'one-print' }
        //   ]
        // },
        // { id: 'sua_ct', child_text: 'Sửa CT', child_icon: 'one-file-edit3' },
        // {
        //   id: 'vt_excel',
        //   child_text: 'VT Excel',
        //   child_icon: 'one-excel',
        //   items: [
        //     { id: 'dc_loai_kho', child_text: 'Điều chỉnh loại kho', child_icon: 'one-excel' },
        //     { id: 'dc_chat_luong', child_text: 'Điều chỉnh chất lượng', child_icon: 'one-excel' },
        //     { id: 'dc_ghi_chu', child_text: 'Điều chỉnh ghi chú', child_icon: 'one-excel' },
        //     { id: 'xuat_ims', child_text: 'Xuất IMS', child_icon: 'one-excel' }
        //   ]
        // },
        // { id: 'gui_sms', child_text: 'Gửi SMS', child_icon: 'one-user-mess' }
      ],
      btnLayTTMoi: { enabled: true, visible: true },
      tsbtnChapNhan: { enabled: true, visible: true },
      tsbtnHuyKH: { enabled: false, visible: true },
      tsbtnKichHoat: { enabled: true, visible: true },
      tsbtnGiaoViec: { enabled: true, visible: true },
      tsbtnGiaoPhieu: { enabled: true, visible: true },
      tsbtnGiaoPhoiHop: { enabled: true, visible: true },
      tsddbIn: { enabled: true, visible: true },
      tsbtnHoanThanh: { enabled: true, visible: true },
      tsbtnHoanCong: { enabled: true, visible: true },
      tsbtnUploadFileNT: { enabled: true, visible: true },
      tsbtnDownloadFileNT: { enabled: true, visible: true },
      tsbtnChuyenTo: { enabled: true, visible: true },
      tsbtnTraPhieu: { enabled: true, visible: true },
      tsbtnTraPhieuTinh: { enabled: true, visible: true },
      tsbtnVatTu: { enabled: true, visible: true },
      tsbtnGiaoViec_KBB: { enabled: true, visible: true },
      tsbtnVatTuMoi: { enabled: true, visible: true },
      tsbtnXuatFile: { enabled: true, visible: true },
      tsbtnHen: { enabled: true, visible: true },
      tsbtnThoat: { enabled: true, visible: true },
      tsbtnGiaoPhieu_Ton: { enabled: true, visible: true },
      tsbtnThongTinKT: { enabled: true, visible: true },
      tsbtnNVKT: { enabled: true, visible: true },
      tsbtnDuAn: { enabled: true, visible: true },
      tsbtnHoanThienHS: { enabled: true, visible: true },
      tsbtnKQ_DoKiem: { enabled: true, visible: true },
      tsbtnSua_KenhThu: { enabled: true, visible: true },
      tsbtnThongTinVRF: { enabled: true, visible: true },
      tsbtnNhapMoiTT: { enabled: true, visible: true },

      txtNV_TiepThi: { enabled: true, value: '' },
      cboKieu_HD: { enabled: true, list: null, value: null },
      cboKenhThu: { enabled: true, list: null, value: null },
      txtTongTien: { enabled: true, value: '' },
      txtSoDTLH: { enabled: true, value: '' },
      chkHen: { enabled: true, value: null },
      lblNhanVien: { enabled: true, value: '' },
      cboLoaiHD: { enabled: false, list: [], value: null },
      cboQuyTrinh: { enabled: false, list: [], value: null },
      txtMaGD: { enabled: true, value: null },
      txtMaTB: { enabled: true, value: null },
      txtMaTC: { enabled: true, value: null },
      txtTenKieuLD: { enabled: true, value: null },
      txtLoaiHinh: { enabled: true, value: null },
      txtTenTB: { enabled: true, value: null },
      txtKhuvuc: { enabled: true, value: null },
      txtDiaChiLD: { enabled: true, value: null },
      txtDiaChiLD_cu: { enabled: true, value: null },
      cboTongDaiIMS: { enabled: true, list: null, value: null },
      cboEndPoint: { enabled: false, list: [], value: null },
      chkLayTSo: { enabled: true, value: false },
      btnPhieuTC: { enabled: true, value: false },
      lblLienHe: { enabled: true, value: '' },
      cboNguoiGV: { enabled: false, list: [], value: null },
      txtNoiDungTH: { enabled: true, value: '' },
      txtLyDoTra: { enabled: true, value: null },
      txtLienHe: { enabled: true, value: null },
      txtNDGiao: { enabled: true, value: null },
      txtThongTinTra: { enabled: true, value: null },
      chkNgayGV: { enabled: true, value: false },
      dtpNgayGV: { enabled: true, value: null },
      chkNgayBG: { enabled: true, value: null },
      dtpNgayBG: { enabled: true, value: null },
      rdoPhieu: { enabled: true, value: 0 },
      rdoPhieuMoi: { enabled: true, value: true },
      rdoPhieuTra: { enabled: true, value: false },
      rdoPhieuDaHoanThanh: { enabled: true, value: false },
      txtProfile: { enabled: true, value: null },
      txtIPPhu: { enabled: true, value: null },
      vtinh_kn_id: 0,
      vhuongkn: '',
      loaisochinh: 0,
      thuebao_id_cha: 0,
      congnghe_id: 0,
      hienthi_mau_tgcamket: 0,
      dichvuvt_id: 0,
      vurl_luoi: '',
      vname: '',

      vma_gd: '',
      hdtb_id: null,
      phieu_id: null,
      loaihinhtb_id: 0,

      host_id: null,
      strerrorVinaphone: '',
      khachhang_id: null,

      diachikh_id: null,
      apkh_id: null,
      phokh_id: null,
      phuongkh_id: null,
      quankh_id: null,
      tinhkh_id: null,
      sonhakh: null,

      diachitt_id: null,
      phott_id: null,
      aptt_id: null,
      phuongtt_id: null,
      quantt_id: null,
      tinhtt_id: null,
      sonhatt: null,
      hdkh_id: null,
      hdtt_id: null,
      thanhtoan_id: null,
      kt_load: false,

      txtIP: {
        value: null,
        enabled: true
      },
      port: null,
      txtTrunkID: {
        value: null,
        enabled: true
      },
      txtSoKenh: {
        value: null,
        enabled: true
      },
      txtUserName: {
        value: null,
        enabled: true
      },
      txtPassword: {
        value: null,
        enabled: true
      },
      txtProfileInfo: {
        value: null,
        enabled: true
      },
      loaiPhieu: 1,
      tthd_id: 0,
      quytrinh_id: 0,
      huonggiao_id: 0,
      luong_id: 0,
      dsloaihd_id: null,
      dsdichvuvt_id: null,

      loaihd_id: null,
      _vquytrinh_id: 0,

      port_id: null,
      vci_vpi_id: null,
      donvi_id: null,
      thuebao_id: null,

      n_hdkh_id: '',
      n_hdtb_id: '',
      n_phieu_id: '',
      kt_thaydoi: false,

      in_hdtb_id_cha: 0,
      in_thuebao_id_cha: 0,
      str_ma_tb_cha: '',

      matb_link: '',
      tramtb_id: 0,
      lay_phieu_con: false,
      tinh_id: 97,
      _IS_USING_CABMAN_V2: -1,

      nhanvien_tc_id: -1,
      congviec_th: '',

      donvi_dl_id: 0,

      TrangThaiHD: {
        DANG_THI_CONG: 4
      },
      LoaiHopDong: {
        DAT_MOI: 1,
        DI_CHUYEN: 3
      },
      dsIn: [],
      wbrTSKT: {
        list: [],
        value: '',
        enabled: true
      },
      lblTongS: {
        value: '',
        enabled: true
      },
      ttkttdTabs: [
        {
          header: {
            text: 'Chi tiết điều hành thi công'
          },
          content: '#dgvChiTiet'
        },
        { header: { text: 'Tiến trình thi công' }, content: '#gridTienTrinh' },
        { header: { text: 'Danh sách dịch vụ gia tăng' }, content: '#gridDangkyDV' },
        { header: { text: 'Danh sách nhân viên thực hiện' }, content: '#gridNhanVien' }
      ],
      dgvChiTiet: {
        list: [],
        cols: [
          { fieldName: 'donvi_nhan', headerText: 'Đơn vị giao', allowFiltering: true },
          { fieldName: 'ngay_giao', headerText: 'Ngày giao', allowFiltering: true },
          { fieldName: 'nguoi_giao', headerText: 'Người giao', allowFiltering: true },
          { fieldName: 'noi_dung_giao', headerText: 'Nội dung giao', allowFiltering: true },
          { fieldName: 'don_vi_giao', headerText: 'Đơn vị nhận', allowFiltering: true },
          { fieldName: 'ngay_tra', headerText: 'Ngày trả', allowFiltering: true },
          { fieldName: 'noi_dung_tra', headerText: 'Nội dung trả', allowFiltering: true },
          { fieldName: 'ly_do_tra', headerText: 'Lý do trả', allowFiltering: true },
          { fieldName: 'ngay_cn', headerText: 'Ngày CN', allowFiltering: true },
          { fieldName: 'nguoi_cn', headerText: 'Người CN', allowFiltering: true },
          { fieldName: 'dtlh', headerText: 'Điện thoại liên hệ', allowFiltering: true },
          { fieldName: 'trang_thai_phieu', headerText: 'Trạng thái phiếu', allowFiltering: true }
        ]
      },
      gridTienTrinh: {
        list: [],
        cols: [
          { fieldName: 'nguoi_cn', headerText: 'Người CN', allowFiltering: true },
          { fieldName: 'ngay_cn', headerText: 'Ngày CN', allowFiltering: true },
          { fieldName: 'noidung', headerText: 'Nội dung', allowFiltering: true }
        ]
      },
      gridDangkyDV: {
        list: [],
        cols: [
          { fieldName: 'ma_dvgt', headerText: 'Mã DVGT', allowFiltering: true },
          { fieldName: 'ten_dvgt', headerText: 'Tên dịch vụ', allowFiltering: true },
          { fieldName: 'kieu', headerText: 'Kiểu YC', allowFiltering: true }
        ]
      },
      gridNhanVien: {
        list: [],
        cols: [
          { fieldName: 'ten_nv', headerText: 'Tên nhân viên', allowFiltering: true },
          { fieldName: 'so_dt', headerText: 'Số điện thoại', allowFiltering: true },
          { fieldName: 'nhiemvu', headerText: 'Nhiệm vụ', allowFiltering: true },
          { fieldName: 'ghichu', headerText: 'Ghi chú', allowFiltering: true }
        ]
      },
      gridDanhSach: {
        list: [],
        cols: [
          { width: 100, fieldName: 'MA_GD', headerText: 'Mã GD', allowFiltering: true, freeze: 'Left' },
          { width: 100, fieldName: 'MA_TB', headerText: 'Số máy/Acc', allowFiltering: true, freeze: 'Left' },
          { width: 100, fieldName: 'MA_LT', headerText: 'Số ảo', allowFiltering: true, freeze: 'Left' },
          { fieldName: 'VRF_VFI', headerText: 'VRF/VFI', allowFiltering: true },
          { fieldName: 'TEN_TB', headerText: 'Tên thuê bao', allowFiltering: true },
          { fieldName: 'TEN_LOAIHD', headerText: 'Loại HĐ', allowFiltering: true },
          { fieldName: 'LOAIHINH_TB', headerText: 'Loại hình TB', allowFiltering: true },
          { fieldName: 'HUONGGIAO', headerText: 'Hướng giao', allowFiltering: true },
          { fieldName: 'TINH_LD', headerText: 'Tỉnh LĐ', allowFiltering: true },
          { fieldName: 'DIACHI_LD', headerText: 'Địa chỉ lắp đặt', allowFiltering: true },
          { fieldName: 'TINH_CQ', headerText: 'Tỉnh bán', allowFiltering: true },
          { fieldName: 'NGAY_YC', headerText: 'Ngày YC', allowFiltering: true },
          { fieldName: 'NGAYGIAO', headerText: 'Ngày giao', allowFiltering: true },
          { fieldName: 'DV_GIAO', headerText: 'Đơn vị hẹn', allowFiltering: true },
          { fieldName: 'GIOHEN_TU', headerText: 'Hẹn từ', allowFiltering: true },
          { fieldName: 'GIOHEN_DEN', headerText: 'Hẹn đến', allowFiltering: true },
          { fieldName: 'NOIDUNG_HEN', headerText: 'Nội dung hẹn', allowFiltering: true },
          { fieldName: 'GOI_KT', headerText: 'Gói KT', allowFiltering: true },
          { fieldName: 'CHITIEU_TG', headerText: 'Chi tiêu TG', allowFiltering: true },
          { fieldName: 'THOIGIAN_CK', headerText: 'Thời gian cam kết', allowFiltering: true },
          { fieldName: 'GIO_CONLAI', headerText: 'Giờ còn lại', allowFiltering: true },
          { fieldName: 'DV_GIAO', headerText: 'Đơn vị giao', allowFiltering: true },
          { fieldName: '---', headerText: 'Trạng thái in', allowFiltering: true },
          { fieldName: 'TT_BC', headerText: 'Trạng thái', allowFiltering: true },
          { fieldName: '---', headerText: 'Ngày BG', allowFiltering: true },
          { fieldName: '---', headerText: 'Ngày KH', allowFiltering: true },
          { fieldName: '---', headerText: 'Lý do tồn', allowFiltering: true },
          { fieldName: '---', headerText: 'Ghi chú tồn', allowFiltering: true },
          { fieldName: '---', headerText: 'Ghi chú', allowFiltering: true },
          { fieldName: '---', headerText: 'File NT', allowFiltering: true },
          { fieldName: 'SO_DT_GIAO', headerText: 'ĐT NV giao', allowFiltering: true }
        ],
        selected: [],
        value: null
      },
      dtKenhThu: [],
      dt: [],
      tinh_khdn: [97, 100],
      //custom dialog
      frmNoiDung: { visible: false, input: {} },
      frmGiaoPhieu_NET: { visible: false, input: {} },
      frmTraPhieuVIP: { visible: false, input: {} },
      frmDSHoSoBC: { visible: false, input: {} }
    }
  },
  computed: {},
  async mounted() {
    this.donvi_dl_id = Number(this.$root.token.getDonViDuLieuID())
    this.initData()
  },
  watch: {
    localLoading(val) {
      if (val > 0) this.loading(true)
      else this.loading(false)
    },
    async 'customerModel.cboLoaiHD.value'(val) {
      await this.cboLoaiHDValueChanged()
    },
    overallModel: {
      handler(val) {},
      deep: true
    },
    assignmentModel: {
      handler(val) {},
      deep: true
    },
    'cboTongDaiIMS.value'(val) {
      // this.cboTongDaiIMSValueChanged()
    },
    'chkNgayGV.value'(val) {
      this.dtpNgayGV.enabled = val
    },
    'chkNgayBG.value'(val) {
      this.dtpNgayBG.enabled = val
    }
  },
  methods: {
    uuidv4() {
      return ([1e7] + -1e3 + -4e3 + -8e3 + -1e11).replace(/[018]/g, (c) => (c ^ (crypto.getRandomValues(new Uint8Array(1))[0] & (15 >> (c / 4)))).toString(16))
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
            this.$toast.error(`Có lỗi gọi API`)
          }
        }
        let mess = `${action} + error`
        console.log(mess)
        return 'error'
      } finally {
        setTimeout(() => {
          this.localLoading--
        }, 500)
      }
      return null
    },
    async initData() {
      this.cboTongDaiIMS.list = [
        { name: 'Hà Nội 10.201.12.6:5060', id: 0 },
        { name: 'HCM 10.202.12.6:5060', id: 1 },
        { name: 'Aaranet', id: 2 }
      ]
      this.cboEndPoint.list = [
        { name: 'Private UDP IMS 5060', id: 0 },
        { name: 'Private UDP Vinaphone 5060', id: 1 },
        { name: 'Public UDP 5060', id: 2 }
      ]
      await this.frmHoanCongMegaWan_FormLoad()
    },
    onTabSelected(e) {
      console.log(e)
    },
    onKeyDown(key) {
      console.log(key)
    },
    getMenu(id) {
      return this.menus.find((e) => e.id == id)
    },
    showPopupSearchContract() {
      this.$refs.popupSearchContract.showModal()
    },
    acceptSearchContract(item) {
      this.customerModel.txtMaGD.value = item.ma_gd
    },
    async frmHoanCongMegaWan_FormLoad() {
      // try {
      //frmHoanCongMegaWan_Load
      this.tsbtnGiaoPhieu.visible = !this.lay_phieu_con
      let caption = 'VNPT Net - Khai báo dịch vụ'
      this.header.title = caption.toUpperCase()

      console.log('frmHoanCongMegaWan_FormLoad')
      this.Tag = this.$route.query.tag
      console.log('this.Tag')
      console.log(this.Tag)
      let str = ''
      // try {
      if (this.Tag != '') {
        str = this.Tag
        let words = str.split('+')
        if (words.length >= 1) this.tthd_id = words[0]
        if (words.length >= 2) {
          this.dsloaihd_id = words[1]
          this.dsloaihd_id = this.dsloaihd_id.replaceAll(';', ',')
        }
        if (words.length >= 3) {
          this.dsdichvuvt_id = words[2]
          this.dsdichvuvt_id = this.dsdichvuvt_id.replaceAll(';', ',')
        }
        if (words.length >= 4) {
          if (words[3] == '1') this.lay_phieu_con = true
        }
      } else {
        this.tthd_id = TrangThaiHD.DANG_THI_CONG
        this.dsloaihd_id = '1'
        this.dsdichvuvt_id = '8'
      }
      console.log('this.dsloaihd_id')
      console.log(this.dsloaihd_id)
      console.log('this.tthd_id')
      console.log(this.tthd_id)
      // } catch (e) {
      //   console.log('vao catch')
      //   this.tthd_id = TrangThaiHD.DANG_THI_CONG
      //   this.dsloaihd_id = '1'
      //   this.dsdichvuvt_id = '8'
      // }
      let data = await this.callApiWrapper(api.fn_load_hoancongims_net, { p_ds_para: `{"DSLOAIHD_ID": "${this.dsloaihd_id}","DONVI_ID": "${this.$root.token.getDonViID()}"}` })
      data = JSON.parse(data)
      this.dtKenhThu = data.RESULT.DTKENHTHU
      this.cboKenhThu.value = this.dtKenhThu.length > 0 ? this.dtKenhThu[0].KENHTHU_ID : null
      this.cboKenhThu.list = this.dtKenhThu.length > 0 ? this.dtKenhThu : []
      this.cboKieu_HD.list = data.RESULT.CBOKIEU_HD
      this.cboKieu_HD.value = this.cboKieu_HD.list.length > 0 ? this.cboKieu_HD.list[0].KIEUHD_ID : null
      this.cboLoaiHD.list = data.RESULT.CBOLOAIHD
      this.cboLoaiHD.value = this.cboLoaiHD.list.length > 0 ? this.cboLoaiHD.list[0].LOAIHD_ID : null

      this.dtpNgayBG.value = DateTimeHelper.NgayCN()
      this.dtpNgayGV.value = DateTimeHelper.NgayCN()
      this.dtpNgayGV.enabled = false

      this.cboNguoiGV.list = data.RESULT.CBONGUOIGV
      this.cboNguoiGV.value = await this.$root.token.getNhanVienID()
      await this.HienThiDanhSachHDTB()
      this.kt_load = true
      // }
      // catch (exp) {
      //     this.$toast.error(`${exp}`);
      // }
    },
    // async inDex_load
    // {
    //     get
    //     {
    //         try
    //         {
    //             if (matb_link == "") return 0;
    //             if (gridDanhSach.DataRowCount <= 1) return 0;
    //             for (let i = 0; i < gridDanhSach.DataRowCount; i++)
    //             {
    //                 if (gridDanhSach.GetRowCellValue(i, "MA_TB") == matb_link)
    //                     return i;
    //             }
    //         }
    //         catch
    //         {
    //             return 0;
    //         }
    //         return 0;
    //     }
    // }
    async HienThiGiaoDien(luong_id) {
      console.log('HienThiGiaoDien')
      this.tsbtnKQ_DoKiem.visible = false
      this.tsbtnDuAn.visible = false
      this.tsbtnHoanThienHS.visible = false
      this.tsbtnHoanThienHS.enabled = false
      this.tsbtnTraPhieu.visible = false
      this.tsbtnHoanCong.visible = false
      this.tsbtnGiaoViec_KBB.visible = false
      this.tsbtnSua_KenhThu.visible = false
      this.tsbtnGiaoPhieu.visible = false
      this.tsbtnVatTu.visible = false
      this.tsbtnVatTuMoi.visible = false
      this.cboQuyTrinh.enabled = false
      let dtThaoTac = await this.sp_lay_luong_thaotac(this.axios, {
        luong_id: luong_id
      })
      console.log('sp_lay_luong_thaotac')
      console.log(dtThaoTac)
      // try {
      //   let str = ''
      //   str += ' select a.thaotac_id, b.thaotac, a.enable, b.code'
      //   str += ' from ' + DatabaseConstants.DB2 + '.luong_thaotac a, ' + DatabaseConstants.DB2 + '.thaotac b'
      //   str += ' where a.thaotac_id = b.thaotac_id'
      //   str += ' and a.luong_id = ' + luong_id

      //   dtThaoTac = bangts.GetDataSql(str)
      //   if (dtThaoTac.length > 0) {
      //     for (let i = 0; i < dtThaoTac.length; i++) {
      //       str = ''
      //       str += ' select * from ' + DatabaseConstants.DB2 + ".thaotac_control where thaotac_id = '" + dtThaoTac.Rows[i].thaotac_id + "'"
      //       dtControl = bangts.GetDataSql(str)
      //       if (dtControl.length > 0) {
      //         for (let j = 0; j < dtControl.length; j++) {
      //           let control_name = dtControl.Rows[j].control_name
      //           // Control[] ctrl = this.Controls.Find(control_name, true);
      //           if (ctrl.length > 0) {
      //             switch (ctrl[0].GetType().FullName) {
      //               case 'WinUI.WinUIUserControls.KFakeDevControls.FakeSimpleButton':
      //               case 'System.Windows.Forms.Button':
      //                 if (dtThaoTac.Rows[i].enable == '1') ctrl[0].enabled = true
      //                 else ctrl[0].enabled = false
      //                 break
      //               case 'System.Windows.Forms.GroupBox':
      //                 if (dtThaoTac.Rows[i].enable == '1') ctrl[0].enabled = true
      //                 else ctrl[0].enabled = false
      //                 break
      //               case 'System.Windows.Forms.CheckBox':
      //                 if (dtThaoTac.Rows[i].enable == '1') ctrl[0].enabled = true
      //                 else ctrl[0].enabled = false
      //                 break
      //               case 'WinUI.WinUIUserControls.KFakeDevControls.FakeTextEdit':
      //               case 'System.Windows.Forms.TextBox':
      //                 if (dtThaoTac.Rows[i].enable == '1') {
      //                   ctrl[0].enabled = true
      //                   ctrl[0].BackColor = Color.Cornsilk
      //                   ctrl[0].ForeColor = Color.Maroon
      //                 } else ctrl[0].enabled = false
      //                 break
      //               default:
      //                 break
      //             }
      //           } else {
      //             // ToolStripItem[] toolStrip = tlstpTop.Items.Find(control_name, true);
      //             if (toolStrip.length > 0) {
      //               switch (toolStrip[0].GetType().FullName) {
      //                 case 'System.Windows.Forms.ToolStripButton':
      //                   if (dtThaoTac.Rows[i].enable == '1') toolStrip[0].visible = true
      //                   else toolStrip[0].visible = false
      //                   break
      //                 default:
      //                   break
      //               }
      //             }
      //           }
      //         }
      //       }
      //     }
      //   }
      //   this.tsddbIn.visible = false
      // } catch (ex) {}
    },

    async HienThiControl(loaitb_id) {},

    async Clear() {
      // this.wbrTSKT.Document.Body.InnerHtml = ''
      this.txtLoaiHinh.value = ''
      // txtSoLuong_IP.value = "";
      this.tramtb_id = 0
      this.txtSoDTLH.value = ''
      // txtDiaChi_KN.value = "";
      this.txtTenKieuLD.value = ''
      // txtSiteID.value = "";
      this.lblLienHe.value = ''
      this.txtMaTB.value = ''
      this.lblNhanVien.value = 'Nhân viên YC : '
      this.txtNV_TiepThi.value = ''
      this.txtMaTC.value = ''
      this.host_id = 0
      this.vma_gd = ''
      this.txtMaGD.value = ''
      this.txtTenTB.value = ''
      this.txtKhuvuc.value = ''
      this.txtDiaChiLD.value = ''
      this.txtDiaChiLD_cu.value = ''
      this.txtNDGiao.value = ''
      this.txtThongTinTra.value = ''
      this.txtLyDoTra.value = ''

      this.txtTongTien.value = '0'
      //txtghichu.value = "";
      this.cboNguoiGV.value = ''
      this.chkNgayBG.value = false
      this.chkNgayGV.value = false
      this.dtpNgayGV.value = DateTimeHelper.NgayCN()
      this.gridNhanVien.list = []
      this.khachhang_id = 0
      this.thanhtoan_id = 0

      this.diachikh_id = 0
      this.apkh_id = 0
      this.phokh_id = 0
      this.phuongkh_id = 0
      this.quankh_id = 0
      this.tinhkh_id = 0
      this.sonhakh = ''

      this.diachitt_id = 0
      this.phott_id = 0
      this.aptt_id = 0
      this.phuongtt_id = 0
      this.quantt_id = 0
      this.tinhtt_id = 0
      this.sonhatt = ''
      this.hdkh_id = 0
      this.hdtt_id = 0
      this.lblTongS.value = 'Tổng số: 0/0'
      this.kt_thaydoi = false
      this.in_hdtb_id_cha = 0
      this.in_thuebao_id_cha = 0
      this.str_ma_tb_cha = ''
    },
    async HienThiDanhSachHDTB() {
      console.log('HienThiDanhSachHDTB')
      console.log(this.rdoPhieuMoi.value)
      console.log(this.rdoPhieuTra.value)
      // try {
      this.dsIn = []
      let vloai_id = 0 // 0: lay danh sach moi giao ve, 1: lay danh sach phieu duoc tra lai
      if (this.rdoPhieuMoi.value) vloai_id = 0
      else if (this.rdoPhieuTra.value) vloai_id = 1
      else vloai_id = 2
      let kq = await this.callApiWrapper(api.fn_hienthidanhsachhdtb_hoancongims_net, {
        p_phanvung_id: this.$root.token.getPhanVungID(),
        p_ds_para: JSON.stringify({ TTHD_ID: this.tthd_id, VLOAI_ID: vloai_id, CBOLOAIHD: this.cboLoaiHD.value }),
        p_ds_ttnd: JSON.stringify({ NHANVIEN_ID: await this.$root.token.getNhanVienID(), DONVI_ID: this.$root.token.getDonViID(), NGUOI_CN: await this.$root.token.getUserName() })
      })
      kq = JSON.parse(kq)
      console.log('1165 - kq')
      console.log(kq)
      this.dt = kq.RESULT.LAY_DS_PHIEU_GIAODEN_NET_V2
      this.dsIn = this.dt
      console.log('LAY_DS_PHIEU_GIAODEN_NET_V2')
      console.log(this.dt)
      this.gridDanhSach.list = this.dt ? this.dt : null
      this.gridDanhSach.value = null
      if (this.dt.length == 0) await this.HienThiDS_PhieuTH(0)
      if (this.dt.length <= 0) await this.Clear()
      // this.lblTongS.value = `Tổng số: ${this.gridDanhSach.selected.length}/${this.gridDanhSach.list.length}`
      // bangts.closeLoading(this)
      // } catch (ex) {
      //   // bangts.closeLoading(this)
      //   this.$toast.error('' + ex)
      // }
    },
    async HienThiTTHopDongTB(ds) {
      console.log('HienThiTTHopDongTB')
      if (ds.length > 0) {
        let nIndex = ds.findIndex((e) => e.PHIEU_ID == this.gridDanhSach.value.PHIEU_ID)
        console.log(ds[nIndex])
        this.hdtb_id = ds[nIndex].HDTB_ID
        this.phieu_id = ds[nIndex].PHIEU_ID
        let ma_tb = ds[nIndex].MA_TB
        let vhuonggiao_id = ds[nIndex].HUONGGIAO_ID
        if (vhuonggiao_id == 8888) this.lay_phieu_con = true
        this.phieu_tinh_id = ds[nIndex].PHIEU_TINH_ID
        this.hdkh_id = ds[nIndex].HDKH_ID
        this.cboLoaiHD.value = ds[nIndex].LOAIHD_ID
        this.cboQuyTrinh.value = ds[nIndex].QUYTRINH_ID

        this.txtSoDTLH.value = ds[nIndex].SO_DT
        this.txtLienHe.value = await this.callApiWrapper(api.fn_lay_thongtin_lh_lapdat, {
          hdkh_id: this.hdkh_id
        })
        if (this.lblLienHe.value == '') this.lblLienHe.value = 'Liên hệ:'
        this.tinh_id = ds[nIndex].TINH_ID
        this.tinh_tc = await this.tinh_thicong(this.tinh_id)
        if (this.tinh_khdn.includes(this.tinh_id)) {
          let tinh_id_thicong = ds[nIndex].TINH_THICONG
          let tinh_thicong = await this.tinh_thicong(tinh_id_thicong)
          console.log('tinh_thicong')
          console.log(tinh_thicong)
          let dtTTHD = await this.callApiWrapper(api.bancheo_lay_thongtin_hd_thicong, {
            vphanvung_id: this.$root.token.getPhanVungID(),
            vhdtb_ban: this.hdtb_id,
            vtinh_id: this.tinh_id
          })
          if (dtTTHD.length > 0) this.txtMaTC.value = dtTTHD[0].ma_tb
          else this.txtMaTC.value = ma_tb
          this.tsbtnTraPhieuTinh.visible = false

          //Lấy thêm thông tin kích hoạt tổng đài của thuê bao IMS
          dtTTHD = await this.callApiWrapper(api.bancheo_lay_thongtin_kichhoat_ims, {
            vphanvung_id: this.tinh_id,
            vhdtb_ban: this.hdtb_id,
            vtinh_id: this.tinh_id
          })
          console.log('dtTTHD')
          console.log(dtTTHD)
          if (dtTTHD.length > 0) {
            if (!(dtTTHD[0].tongdai_id == null && dtTTHD[0].tongdai_id == '')) this.cboTongDaiIMS.value = dtTTHD[0].tongdai_id
            this.txtSoKenh.value = dtTTHD[0].sl_cuocgoi
            this.txtTrunkID.value = dtTTHD[0].trunkid
            this.txtIP.value = dtTTHD[0].ip
            this.txtIPPhu.value = dtTTHD[0].ip_phu

            this.txtUserName.value = dtTTHD[0].username
            this.txtPassword.value = dtTTHD[0].password
            if (this.cboTongDaiIMS.value == 2) {
              //Lấy những thông tin của Aaranet
              if (!(dtTTHD[0].endpoint_id != null && dtTTHD[0].endpoint_id != '')) this.cboEndPoint.value = dtTTHD[0].endpoint_id
              this.txtProfile.value = dtTTHD[0].profile
              this.txtProfileInfo.value = dtTTHD[0].profile_info
            }
          }
        } else {
          let dtTTHD = await this.callApiWrapper(api.sp_tt_hd_bancheo, {
            vphanvung_id: null,
            vhdtb_ban: this.hdtb_id
          })
          //new BanCheoBase().GET_DATA_SQL(tinh_tc, "select * from css_" + tinh_tc.matinh + ".hd_bancheo where hdtb_ban=" + hdtb_id);
          if (dtTTHD.length > 0) {
            let tinh_tc_id = dtTTHD[0].tinh_thicong
            // Tinh_TC _tinh_tc = new BanCheoBase().tinh_thicong(tinh_tc_id);
            dtTTHD = await this.callApiWrapper(api.bancheo_lay_thongtin_hd_thicong, {
              vphanvung_id: this.$root.token.getPhanVungID(),
              vhdtb_ban: this.hdtb_id,
              vtinh_id: this.tinh_id
            })
            if (dtTTHD.length > 0) this.txtMaTC.value = dtTTHD[0].ma_tb
            else this.txtMaTC.value = ma_tb
          } else this.txtMaTC.value = ma_tb

          //Lấy thêm thông tin kích hoạt tổng đài của thuê bao IMS
          dtTTHD = await this.callApiWrapper(api.bancheo_lay_thongtin_kichhoat_ims, {
            vphanvung_id: this.tinh_id,
            vhdtb_ban: this.hdtb_id,
            vtinh_id: this.tinh_id
          })
          if (dtTTHD.length > 0) {
            if (!(dtTTHD[0].tongdai_id == null || dtTTHD[0].tongdai_id == '')) this.cboTongDaiIMS.value = dtTTHD[0].tongdai_id
            this.txtSoKenh.value = dtTTHD[0].sl_cuocgoi
            this.txtTrunkID.value = dtTTHD[0].trunkid
            this.txtIP.value = dtTTHD[0].ip
            this.txtIPPhu.value = dtTTHD[0].ip_phu
            this.txtUserName.value = dtTTHD[0].username
            this.txtPassword.value = dtTTHD[0].password
            if (this.cboTongDaiIMS.value == 2) {
              //Lấy những thông tin của Aaranet
              if (!isNullOrEmpty(dtTTHD[0].endpoint_id)) this.cboEndPoint.value = dtTTHD[0].endpoint_id
              this.txtProfile.value = dtTTHD[0].profile
              this.txtProfileInfo.value = dtTTHD[0].profile_info
            }
          }

          this.tsbtnTraPhieu.visible = false
          this.tsbtnTraPhieuTinh.visible = true
        }
        await this.HT_THONGSO_KYTHUAT(ds[nIndex].TINH_ID)
        if (ds[nIndex].kieuhd_id != '') this.cboKieu_HD.value = ds[nIndex].kieuhd_id
        else this.cboKieu_HD.value = KIEU_HD.TAI_GD
        this.congnghe_id = 0
        this.txtTenKieuLD.value = ds[nIndex].ten_kieuld
        this.hdkh_id = ds[nIndex].hdkh_id
        this.hdtt_id = 0
        console.log('ROI NHA')
        console.log(ds[nIndex])

        if (!isNullOrEmpty(ds[nIndex].KHACHHANG_ID)) this.khachhang_id == ds[nIndex].KHACHHANG_ID
        else this.khachhang_id = 0
        this.thanhtoan_id = 0
        this.vma_gd = ds[nIndex].MA_GD
        console.log(ds[nIndex].MA_GD)
        console.log(this.vma_gd)
        this.txtMaGD.value = this.vma_gd
        this.txtMaTB.value = ma_tb
        this.txtTenTB.value = this.ChuanHoaTen(ds[nIndex].TEN_TB)
        this.txtKhuvuc.value = ds[nIndex].TEN_KV
        this.loaihinhtb_id = ds[nIndex].LOAITB_ID
        this.txtLoaiHinh.value = ds[nIndex].LOAIHINH_TB
        this.thuebao_id = 0
        if (this.cboLoaiHD.value != LoaiHopDong.DAT_MOI)
          //Lấy thuê bao ID đối với loại hợp đồng != đặt mới
          this.thuebao_id = ds[nIndex].thuebao_id
        this.txtDiaChiLD.value = this.ChuanHoaTen(ds[nIndex].DIACHI_LD)
        //nhapt 24/08/2015
        //Thực hiện địa chỉ cũ
        //if (loaihd_id != LoaiHopDong.DAT_MOI)
        //    txtDiaChiLD_cu.value = ds[nIndex].diachild_cu; //nếu lỗi ở đây thì chưa có trường diachi_cu trong PL
        //else
        this.txtDiaChiLD_cu.value = ''
        this.txtNoiDungTH.value = ds[nIndex].ND_THUCHIEN
        await this.HienThiControl(this.loaihinhtb_id)
        this.txtNDGiao.value = ds[nIndex].nd_giao
        this.port_id = 0
        this.tramtb_id = 0
        await this.HienThiDSNV(
          await this.callApiWrapper(api.lay_ds_nhanvien_theo_phieu_id, {
            vphanvung_id: this.$root.token.getPhanVungID(),
            vphieu_id: this.phieu_id,
            vnhanvien_id: this.$root.token.getNhanVienID(),
            vkieu_id: 2
          })
        )
        this.dtpNgayBG.enabled = true
        this.chkNgayBG.value = true
        if (ds[nIndex].ngay_ht_gp != '') this.dtpNgayBG.value = ds[nIndex].ngay_ht_gp
        else this.dtpNgayBG.value = DateTimeHelper.NgayCN().AddSeconds(30)

        //if (PHAILAM(luong_id, "GIAOPHIEU"))
        {
          if (ds[nIndex].ngay_ht_gp != '' && !this.rdoPhieuDaHoanThanh.value) {
            //anhnt bỏ kích hoạt CM
            this.tsbtnHoanThanh.enabled = true

            let dtTTHD = this.callApiWrapper(api.sp_tt_hd_bancheo, {
              vphanvung_id: null,
              vhdtb_id: this.hdtb_id
            })
            if (dtTTHD.length > 0) {
              this.hdtb_cha_id = isNullOrEmpty(dtTTHD[0].hdtb_cha_id) ? 0 : dtTTHD[0].hdtb_cha_id
              this.thuebao_cha_id = isNullOrEmpty(dtTTHD[0].thuebao_cha_id) ? 0 : dtTTHD[0].thuebao_cha_id

              //Neu la thue bao them vao duong trunk thi khong duoc phep nhap lai trunkID
              if (this.hdtb_cha_id != 0 || this.thuebao_cha_id != 0) {
                this.txtTrunkID.enabled = false
              } else this.txtTrunkID.enabled = true
            }
            this.tsbtnGiaoPhieu.enabled = true
          } else {
            this.tsbtnGiaoPhieu.enabled = false
            this.tsbtnHoanThanh.enabled = false
          }
        }

        if (await this.PHAILAM(this.luong_id, 'HOANTHIENHOSO')) {
          if (ds[nIndex].ngay_ht_gp != '') this.tsbtnHoanThienHS.enabled = true
          else this.tsbtnHoanThienHS.enabled = false
        }

        if (await this.PHAILAM(this.luong_id, 'HOANCONG')) {
          if (ds[nIndex].ngay_ht_gp != '') this.tsbtnHoanCong.enabled = true
          else this.tsbtnHoanCong.enabled = false
        }

        this.tsbtnHoanThanh.visible = true
        this.tsbtnKichHoat.visible = false

        if (!this.PHAILAM(this.luong_id, 'GIAOVIEC')) {
          this.chkNgayBG.value = true
          this.dtpNgayBG.enabled = true
          this.chkNgayBG.enabled = true

          this.chkNgayGV.value = true
          this.dtpNgayGV.enabled = false
          this.chkNgayGV.enabled = false
        }

        this.dtDVGT = await this.callApiWrapper(api.lay_ds_dangky_dvgt, {
          vphanvung_id: this.tinh_id,
          vhdtb_id: this.hdtb_id
        })

        await this.HienThiDVGT(this.dtDVGT)
        await this.HienThiDS_PhieuTH(this.phieu_id)
        await this.HIEN_THONGSO_KYTHUAT()
        await this.NAP_GRD_TIENTRINH()
      } else await this.HienThiDS_PhieuTH(0)
    },
    async HienThiTTHopDongTB_Index(ds, nIndex) {
      console.log('HienThiTTHopDongTB')
      console.log(ds)
      console.log(nIndex)
      console.log(ds[nIndex])
      if (ds.length > 0) {
        this.hdtb_id = ds[nIndex].HDTB_ID
        this.phieu_id = ds[nIndex].PHIEU_ID
        this.hdkh_id = ds[nIndex].HDKH_ID
        this.hdkh_id = ds[nIndex].HDKH_ID
        if (!isNullOrEmpty(ds[nIndex].hdtt_id.trim())) this.hdtt_id = ds[nIndex].hdtt_id
        else this.hdtt_id = 0
        if (!isNullOrEmpty(ds[nIndex].khachhang_id.trim())) this.khachhang_id = ds[nIndex].KHACHHANG_ID
        else this.khachhang_id = 0
        if (!isNullOrEmpty(ds[nIndex].thanhtoan_id.trim())) this.thanhtoan_id = ds[nIndex].KHACHHANG_ID
        else this.thanhtoan_id = 0
        this.vma_gd = ds[nIndex].MA_GD
        this.txtMaGD.value = this.vma_gd
        this.txtMaTB.value = ds[nIndex].MA_TB
        this.txtTenTB.value = this.ChuanHoaTen(ds[nIndex].TEN_TB)
        this.txtKhuvuc.value = ds[nIndex].TEN_KV
        this.loaihinhtb_id = ds[nIndex].LOAITB_ID
        this.thuebao_id = 0
        if (this.cboLoaiHD.value != LoaiHopDong.DAT_MOI)
          //Lấy thuê bao ID đối với loại hợp đồng != đặt mới
          this.thuebao_id = ds[nIndex].THUEBAO_ID
        if (ds[nIndex].giukenh == '-1') this.chkLuongGiuKenh.enabled = false
        else {
          this.chkLuongGiuKenh.enabled = true
          this.chkLuongGiuKenh.value = ds[nIndex].giukenh == '1'
        }
        this.txtDiaChiLD.value = this.ChuanHoaTen(ds[nIndex].DIACHI_LD)
        this.txtNoiDungTH.value = ds[nIndex].ND_THUCHIEN

        await this.HienThiControl(loaihinhtb_id)

        //let str = "select * from " + DatabaseConstants.DB2 + ".hdtb_dv where hdtb_id = " + hdtb_id + " and loaidv_id = " + LOAI_DV.TRAM_VT + " and kieudv_id = 2";
        //let dtTemp = bangts.GetDataSql(str);
        //if (dtTemp.length > 0)
        //    donvi_id = dtTemp[0].donvi_id);

        await this.HienThiDSNV(
          await this.callApiWrapper(api.lay_ds_nhanvien_theo_phieu_id, {
            vphanvung_id: this.$root.token.getPhanVungID(),
            vphieu_id: this.phieu_id,
            vnhanvien_id: await this.$root.token.getNhanVienID(),
            vkieu_id: 2
          })
        )

        this.dtpNgayBG.enabled = true
        this.chkNgayBG.value = true
        if (ds[nIndex].ngay_ht_gp != '') this.dtpNgayBG.value = ds[nIndex].ngay_ht_gp
        else this.dtpNgayBG.value = DateTimeHelper.NgayCN().AddSeconds(30)

        if (PHAILAM(luong_id, 'GIAOPHIEU')) {
          if (ds[nIndex].ngay_ht_gp != '') this.tsbtnGiaoPhieu.enabled = true
          else this.tsbtnGiaoPhieu.enabled = false
        }

        if (PHAILAM(luong_id, 'HOANCONG')) {
          if (ds[nIndex].ngay_ht_gp != '') this.tsbtnHoanCong.enabled = true
          else this.tsbtnHoanCong.enabled = false
        }

        await this.HienThiDVGT(
          await this.callApiWrapper(api.lay_ds_dangky_dvgt, {
            vphanvung_id: this.tinh_id,
            vhdtb_id: this.hdtb_id
          })
        )
        await this.HIEN_THONGSO_KYTHUAT()
      }
    },
    async HIEN_THONGSO_KYTHUAT() {},
    async txtMaGD_KeyPress() {},
    async txtMaTB_KeyPress() {},
    async HT_THONGSO_KYTHUAT(tinh_id) {
      console.log('HT_THONGSO_KYTHUAT')
      console.log(tinh_id)
      try {
        let dt
        if (this.tinh_khdn.includes(tinh_id)) {
          dt = await this.callApiWrapper(api.bancheo_lay_ds_thongtin_kythuat, {
            vphanvung_id: this.$root.token.getPhanVungID(),
            vhdtb_id: this.hdtb_id
          })
        } else {
          dt = await this.callApiWrapper(api.bancheo_lay_ds_thongtin_kythuat_2, {
            vphanvung_id: tinh_id,
            vhdtb_id: this.hdtb_id
          })
        }
        this.wbrTSKT.value = ''
        for (let dr of dt) {
          this.wbrTSKT.value += `${dr.format_tt.replace('{0}', dr.ten_truong)}${dr.format_gt.replace('{0}', dr.giatri)}`
        }
      } catch (ex) {
        this.wbrTSKT.value = 'Chưa có dữ liệu'
      }
    },
    async HienThiDS_PhieuTH(phieu_id) {
      try {
        let kq = await this.callApiWrapper(api.fn_hienthids_phieuth_hoancongims_net, {
          p_phanvung_id: this.$root.token.getPhanVungID(),
          p_ds_para: `{\"tinh_id\":${this.tinh_id},\"phieu_id\":${phieu_id}}`
        })
        let json = JSON.parse(kq)
        this.dgvChiTiet.list = json.RESULT.SP_LAY_DANHSACH_PHIEU_NET_CON
      } catch (e) {
        this.$toast.error(`${e}`)
      }
    },
    async tsbtnHoanCong_Click() {
      await this.Hoan_Cong()
    },
    async Kiemtra_vattu() {
      return false
    },
    async Hoan_Cong() {
      this.$toast.info('Không có chức năng này!')
    },
    async KiemTra_DL(luong_id) {
      if (this.gridDanhSach.list.length <= 0) {
        this.$toast.warning('Không có thuê bao trên lưới !')
        return false
      }
      if (this.cboNguoiGV.value == '') {
        this.$toast.warning('Hãy nhập người giao việc')
        // this.cboNguoiGV.Focus()
        return false
      }
      if (!this.chkNgayGV.value) {
        this.$toast.warning('Hãy nhập ngày giao việc!')
        // this.chkNgayGV.Focus()
        return false
      }
      if (!this.chkNgayBG.value) {
        this.$toast.warning('Hãy nhập ngày hoàn thành!')
        // chkNgayBG.this.Focus()
        return false
      }

      if (this.PHAILAM(this.luong_id, 'GIAOVIEC')) {
        if (this.gridNhanVien.list.length <= 0) {
          this.$toast.warning('Hãy nhập nhân viên thực hiện  !')
          return false
        }
      }
      if (isNullOrEmpty(this.txtIP.value)) {
        this.$toast.warning('Chưa nhập IP !')
        // txtIP.Focus()
        return false
      }

      if (isNullOrEmpty(this.txtSoKenh.value)) {
        this.$toast.warning('Chưa nhập số kênh đồng thời !')
        // txtSoKenh.Focus()
        return false
      }

      if (isNullOrEmpty(this.txtTrunkID.value)) {
        this.$toast.warning('Chưa nhập TrunkID !')
        // txtTrunkID.Focus()
        return false
      }

      if (this.txtTrunkID.value.length > 10) {
        this.$toast.warning('TrunkID không được quá 10 ký tự !')
        // txtTrunkID.Focus()
        return false
      }

      if (this.cboTongDaiIMS.value == -1) {
        this.$toast.warning('Chưa chọn tổng đài kích hoạt !')
        // cboTongDaiIMS.Focus()
        return false
      }

      if (this.cboTongDaiIMS.value == 2) {
        //Kiểm tra các tham số của Aaranet
        if (this.cboEndPoint.value == -1) {
          this.$toast.warning('Chưa chọn endpoint !')
          // cboEndPoint.Focus()
          return false
        }
        if (isNullOrEmpty(this.txtProfile.value)) {
          this.$toast.warning('Chưa nhập Profile !')
          // txtProfile.Focus()
          return false
        }
        if (isNullOrEmpty(this.txtProfileInfo.value)) {
          this.$toast.warning('Chưa nhập Profile Info !')
          // txtProfileInfo.Focus()
          return false
        }
      }
      return true
    },
    async tsbtnChapNhan_Click() {
      // try {
      if (!(await this.Cap_Nhat())) return
      this.$toast.success('Cập nhật dữ liệu lên viễn thông tỉnh thành công')
      let vma_tb = this.txtMaTB.value.trim()
      await this.HienThiDanhSachHDTB()
      for (let i = 0; i < this.gridDanhSach.list.length; i++) {
        if (this.gridDanhSach.value.MA_TB == vma_tb) {
          this.gridDanhSach.value = i
          break
        }
      }
      // } catch (ex) {
      //   this.$toast.error(`${ex}`)
      // }
    },
    async Cap_Nhat() {
      if (!(await this.KiemTra_DL(this.luong_id))) return false
      //Kiểm tra ngày hoàn thành có >= ngày thanh toán , <= ngay hien tai ko
      let ngay_sys
      let ngay_ht
      let nIndex = this.dt.findIndex((e) => e.PHIEU_ID == this.gridDanhSach.value.PHIEU_ID)
      console.log('Cap_Nhat')
      console.log(this.dt[nIndex])
      ngay_sys = this.dt[nIndex].THANGNAM_SYS.trim()
      // moment(new Date().setDate(new Date().getDate() - 7)).format('DD/MM/YYYY')
      ngay_ht = getDateStringOrDefault(this.dtpNgayBG.value, FORM_CONFIG.DEFAULT_SHORT_DATE_FORMAT, FORM_CONFIG.DEFAULT_SHORT_DATE_FORMAT)
      if (this.cboLoaiHD.value != LoaiHopDong.KHOIPHUC_SD) {
        if (DateTimeHelper.ToDate(this.dtpNgayBG.value, FORM_CONFIG.DEFAULT_SHORT_DATE_FORMAT) < DateTimeHelper.ToDate(DateTimeHelper.NgayCN(), FORM_CONFIG.DEFAULT_SHORT_DATE_FORMAT)) {
          this.$toast.warning('Tháng hoàn thành không được nhỏ hơn tháng hiện tại!')
          return false
        }
      }
      if (!(await this.KiemTraDK_HoanThanh(ngay_ht, ngay_sys))) {
        //Nếu ngày thanh toán > ngày hoàn thành
        this.$toast.warning('Bạn không được phép chọn ngày hoàn thành lớn hơn ngày hiện tại')
        return false
      }

      //Cập nhật thông tin phiếu
      await this.callApiWrapper(api.capnhat_thongtin_phieu, {
        vphanvung_id: this.$root.token.getPhanVungID(),
        vphieu_id: this.phieu_id,
        vnhanvien_gv_id: this.cboNguoiGV.value,
        vnhanvien_th_id: this.$root.token.getNhanVienID(),
        vngay_th: getDateStringOrDefault(this.dtpNgayBG.value, FORM_CONFIG.DEFAULT_SHORT_DATE_FORMAT, FORM_CONFIG.DEFAULT_DATE_FORMAT),
        vngay_gv: getDateStringOrDefault(this.dtpNgayGV.value, FORM_CONFIG.DEFAULT_SHORT_DATE_FORMAT, FORM_CONFIG.DEFAULT_DATE_FORMAT)
      })

      if (this.tinh_khdn.includes(this.tinh_id)) {
        let tinh_id_thicong = this.dt[nIndex].TINH_THICONG
        let tinh_thicong = await this.tinh_thicong(tinh_id_thicong)
        await this.callApiWrapper(api.bancheo_capnhat_thongtin_kichhoat_ims, {
          vphanvung_id: this.$root.token.getPhanVungID(),
          vhdtb_ban: this.hdtb_id,
          vtinh_id: this.tinh_id,
          vtrunk_id: this.txtTrunkID.value,
          vip: this.txtIP.value,
          vsokenh: this.txtSoKenh.value,
          vtongdai: this.cboTongDaiIMS.value,
          vendpoint_id: this.cboEndPoint.value,
          vprofile: this.txtProfile.value,
          vprofile_info: this.txtProfileInfo.value,
          vip_phu: this.txtIPPhu.value
        })
      } else {
        await this.callApiWrapper(api.bancheo_capnhat_thongtin_kichhoat_ims, {
          vphanvung_id: this.$root.token.getPhanVungID(),
          vhdtb_ban: this.hdtb_id,
          vtinh_id: this.tinh_id,
          vtrunk_id: this.txtTrunkID.value,
          vip: this.txtIP.value,
          vsokenh: this.txtSoKenh.value,
          vtongdai: this.cboTongDaiIMS.value,
          vendpoint_id: this.cboEndPoint.value,
          vprofile: this.txtProfile.value,
          vprofile_info: this.txtProfileInfo.value,
          vip_phu: this.txtIPPhu.value
        })
      }
      return true
    },
    async cboLoaiHD_SelectedValueChanged() {
      console.log('cboLoaiHD_SelectedValueChanged')
      try {
        if (this.cboLoaiHD.value.length > 0) {
          if (this.cboLoaiHD.value == null) return
          this.loaihd_id = this.cboLoaiHD.value
          let ds = await this.callApiWrapper(api.sp_lay_ds_quytrinh_v2, {
            dsloaihd_id: this.dsloaihd_id,
            dichvuvt_id: this.dsdichvuvt_id,
            tthd_id: this.tthd_id,
            kieu: 0
          })
          console.log(ds)
          if (ds.length > 0) {
            this.cboQuyTrinh.list = ds
            if (this._vquytrinh_id != 0) {
              this.cboQuyTrinh.value = this._vquytrinh_id
            }
          } else {
            this.quytrinh_id = -1
            this.cboQuyTrinh.list = null
          }
          if (this.cboLoaiHD.value == LoaiHopDong.DAT_MOI || this.cboLoaiHD.value == LoaiHopDong.DI_CHUYEN) {
            this.chkLayTSo.enabled = true
            this.btnPhieuTC.enabled = true
          } else {
            this.chkLayTSo.enabled = false
            this.chkLayTSo.value = false
            this.btnPhieuTC.enabled = false
          }
        }
      } catch (exp) {
        this.$toast.error(`${exp}`)
      }
    },
    async cboThoiHanMEGAWAN_SelectedIndexChanged() {},
    async tsbtnGiaoPhieu_Click() {
      await this.GiaoPhieu()
    },
    async GiaoPhieu() {},
    async btnLayTTMoi_Click() {
      await this.HienThiDanhSachHDTB()
    },
    async ShowBaoCao(tenfile) {},
    async KiemTraDK_HoanThanh(
      thangnam_tt,
      thangnam_sys //thangnam_sys= ngay_ht
    ) {
      let result = false
      if (thangnam_tt.substring(6, thangnam_tt.length - 6) < thangnam_sys.substring(6, thangnam_sys.length - 6))
        //So sánh năm
        return true
      //Nếu năm thanh toán < năm hoàn thành thì true;
      else if (thangnam_tt.substring(6, thangnam_tt.length - 6) == thangnam_sys.substring(6, thangnam_sys.length - 6)) {
        //So sánh năm
        //Nêu năm = nhau thì kiểm tra tiếp
        //Nếu tháng tt < thang ht -> true
        if (thangnam_tt.substring(3, 2) < thangnam_sys.substring(3, 2)) result = true
        else if (thangnam_tt.substring(3, 2) == thangnam_sys.substring(3, 2))
          if (thangnam_tt.substring(0, 2) <= thangnam_sys.substring(0, 2))
            //Nếu tháng & năm = nhau thì kiểm tra ngày
            return true
          else return false
        else result = false //tháng tt lớn hơn thang ht
      } else result = false
      return result
    },
    async txtMaTN_KeyPress() {},
    async btnPhieuGiaoSai_Click() {
      await this.TraPhieu()
    },
    async TraPhieu() {
      this.$toast.info('Không có chức năng này')
    },
    async tsbtnGiaoViec_Click() {
      await this.GiaoViec()
    },
    async GiaoViec() {
      if (this.gridDanhSach.list.length <= 0) {
        this.$toast.warning('Chưa có danh thuê bao. Bạn hãy kiểm tra lại!')
        return
      }
      if (this.cboNguoiGV.list.length <= 0 || this.cboNguoiGV.value == '') {
        this.$toast.warning('Hãy nhập nhân viên giao việc!')
        // cboNguoiGV.Focus()
        return
      }
      if (!this.chkNgayGV.value) {
        this.$toast.warning('Hãy nhập ngày giao việc!')
        // chkNgayGV.Focus()
        return
      }
      let vnhanvien_giao_id = 0
      let vngaygiao
      vnhanvien_giao_id = this.cboNguoiGV.value
      this.vngaygiao = this.dtpNgayGV.value
      console.log('GiaoViec')
      console.log('vnhanvien_giao_id')
      console.log(this.vnhanvien_giao_id)

      this.$refs.frnGiaoPhieuNV.frnGiaoPhieuNV(this.phieu_id, this.$root.token.getDonViID(), vnhanvien_giao_id, vngaygiao)
      this.$refs.frnGiaoPhieuNV.vma_tb = this.txtMaTB.value?.trim()
      this.$refs.frnGiaoPhieuNV.vhdtb_id = this.hdtb_id
      this.$refs.frnGiaoPhieuNV.huonggiao_id = this.huonggiao_id
      this.$nextTick(() => {
        this.$refs.frnGiaoPhieuNV.load()
        this.$refs.popupfrnGiaoPhieuNV.show()
      })
      //Hiển thị danh sách nhân viên
      let ds = []
      ds = await this.callApiWrapper(api.lay_ds_nhanvien_theo_phieu_id, {
        vphanvung_id: this.$root.token.getPhanVungID(),
        vphieu_id: this.phieu_id,
        vnhanvien_id: this.$root.token.getNhanVienID(),
        vkieu_id: 2
      })
      await this.HienThiDSNV(ds)
    },
    async btnLayMaGD_Click() {},
    async tsbtnHoanThanh_Click() {
      console.log('tsbtnHoanThanh_Click')
      if (this.gridDanhSach.selected.length) {
        console.log(this.gridDanhSach.selected)
        await this.Hoan_Thanh()
      } else this.$toast.warning('Bạn chưa chọn phiếu')
    },
    async Hoan_Thanh() {
      console.log('Hoan_Thanh')
      console.log(this.gridDanhSach.value)

      let list = this.gridDanhSach.selected.map((e) => this.ConvertKeysToLowerCase(e))

      console.log(list)
      if (!confirm(`Bạn thật sự muốn hoàn thành thi công ${this.gridDanhSach.selected.length} phiếu không?`)) return
      let data = await api.hoanthanh(this.axios, {
        items: list
      })
      if (data.data.error_code == 'BSS-00000000') {
        await this.HienThiDanhSachHDTB()
        await this.HienThiDS_PhieuTH(0)
        this.tsbtnHoanThanh.enabled = false
        this.$toast.success('Cập nhật dữ liệu thành công!')
      } else {
        this.$toast.warning(`${data.data.message}`)
      }
      console.log('Hoan_Thanh')
      console.log(data)
      if (data == 'Cập nhật dữ liệu thành công!') {
        await this.HienThiDanhSachHDTB()
        await this.HienThiDS_PhieuTH(0)
        this.tsbtnHoanThanh.enabled = false
        this.$toast.success('Cập nhật dữ liệu thành công!')
      } else {
      }

      // }catch(e) {

      // }
    },
    async HienThiDSNV(ds) {
      console.log('HienThiDSNV')
      console.log(ds)
      //lvwNhanVien.Items.Clear();
      let myDataTables = ds
      if (myDataTables.length > 0) {
        this.chkNgayGV.value = true
        this.dtpNgayGV.value = ds[0].ngaygiao
        this.cboNguoiGV.value = ds[0].nhanvien_giao_id
        this.gridNhanVien.list = ds
        this.nhanvien_tc_id = ds[0].nhanvien_th_id
        this.congviec_th = ds[0].nhiemvu
      } else {
        this.gridNhanVien.list = []
        if (!this.PHAILAM(this.luong_id, 'GIAOVIEC')) this.chkNgayGV.value = true
        else this.chkNgayGV.value = false
        this.dtpNgayGV.value = DateTimeHelper.NgayCN()
        this.cboNguoiGV.value = ''
        this.cboNguoiGV.value = await this.$root.token.getNhanVienID()
        this.nhanvien_tc_id = -1
        this.congviec_th = ''
      }
    },
    async KiemTra_PhieuCon_DaHoanThanh() {
      let str = ''
      let dtTemp = await this.callApiWrapper(api.fn_kiemtra_phieucon_dahoanthanh, {
        p_phanvung_id: this.$root.token.getPhanVungID(),
        p_phieu_id: this.phieu_id,
        p_tinh_id: this.tinh_id
      })
      if (dtTemp && dtTemp.length > 0) {
        this.$toast.warning('Các phiếu con chưa hoàn thành!')
        return false
      }
      return true
    },
    async HienThiTTDiaChi(ds, kieu) {
      if (ds.length > 0) {
        if (kieu == 1) {
          this.diachikh_id = ds[0].diachi_id
          this.tinhkh_id = ds[0].tinh_id
          this.quankh_id = ds[0].quan_id
          this.phuongkh_id = ds[0].phuong_id
          if (ds[0].pho_id != '') this.phokh_id = ds[0].pho_id
          else this.phokh_id = 0
          if (ds[0].ap_id != '') this.apkh_id = ds[0].ap_id
          else this.apkh_id = 0
          this.sonhakh = this.ChuanHoaTen(ds[0].sonha)
        }
        if (kieu == 2) {
          this.diachitt_id = ds[0].diachi_id
          this.tinhtt_id = ds[0].tinh_id
          this.quantt_id = ds[0].quan_id
          this.phuongtt_id = ds[0].phuong_id
          if (ds[0].pho_id != '') this.phott_id = ds[0].pho_id
          else this.phott_id = 0
          if (ds[0].ap_id != '') this.aptt_id = ds[0].ap_id
          else this.aptt_id = 0
          this.sonhatt = this.ChuanHoaTen(ds[0].sonha)
        }
      }
    },
    async chkNgayGV_CheckedChanged() {},
    async chkNgayBG_CheckedChanged_1() {},
    async tsbtnVatTu_Click() {
      if (phieu_id != 0) {
        // frmVatTuThueBao frm = new frmVatTuThueBao();
        frm.phieu_id = this.phieu_id
        frm.loaihd_id = this.cboLoaiHD.value
        frm.hdtb_id = this.hdtb_id
        frm.thuebao_id = this.thuebao_id
        frm.ShowDialog()
      } else {
        this.$toast.warning('Bạn chưa chọn phiếu!')
      }
    },

    async btnPhieuTC_Click() {
      try {
        if (this.gridDanhSach.list.length <= 0) {
          this.$toast.success('Chọn thuê bao cần xuất phiếu !')
          return
        }
        if (this.cboLoaiHD.value == LoaiHopDong.DAT_MOI) {
          // ShowBaoCao('INPHIEU_TC_MGWAN.rst')
        }
        if (this.cboLoaiHD.value == LoaiHopDong.DI_CHUYEN) {
          // ShowBaoCao('INPHIEU_TC_DC_MGWAN.rst')
        }
      } catch (exp) {
        this.$toast.error(`${exp}`)
      }
    },
    async btnBienBanNT_Click() {
      try {
        await this.InPhieu()
        if (this.kt_thaydoi) {
          //Nếu có sự đôi port mới load lại danh sachs
          let vma_tb = this.txtMaTB.value.trim()
          await this.HienThiDanhSachHDTB()

          // Tìm kiếm row vừa cập nhật
          // this.gridDanhSach.value = FindGridIndexByValue('MA_TB', vma_tb)
        }
      } catch (exp) {
        this.$toast.error(`${exp}`)
      }
    },
    // async nhanvien_tc_id = -1;
    // let congviec_th = "";

    async InPhieu() {
      try {
        let ds_phieu = []
        let vhdkh_id = checkdata.MAP_ID('hdkh_id', DatabaseConstants.DB2 + '.hd_thuebao', 'where hdtb_id = ' + hdtb_id)

        if (vhdkh_id == '-1') {
          this.$toast.success('Không tìm thấy thông tin khách hàng. Bạn hãy kiểm tra lại!')
          return
        }
        let vphieu_id = [] //nhapt test 20/6/2015
        // frmInBienBan obj = new frmInBienBan();
        //-----------------------------------------------------------------------------
        //nhapt sửa 10072015
        n_hdkh_id = ''
        n_hdtb_id = ''
        n_phieu_id = ''
        for (let k = 0; k < dsIn.length; k++) {
          // let
          if (dsIn[k].CHON == '1') {
            n_hdkh_id += dsIn[k].hdkh_id + ','
            n_hdtb_id += dsIn[k].hdtb_id + ','
            n_phieu_id += dsIn[k].phieu_id + ','
          }
        }
        if (!isNullOrEmpty(n_hdkh_id) && !isNullOrEmpty(n_phieu_id) && !isNullOrEmpty(n_hdtb_id)) {
          //nhapt thêm
          obj.n_hdkh_id = n_hdkh_id.substring(0, n_hdkh_id.length - 1) //vhdkh_id);
          obj.nvth_id = nhanvien_tc_id
          obj.cv_thuchien = congviec_th
          obj.n_phieu_id = n_phieu_id.substring(0, n_phieu_id.length - 1) //phieu_id;
          obj.n_hdtb_id = n_hdtb_id.substring(0, n_hdtb_id.length - 1) //hdtb_id;
          obj.huonggiao_id = huonggiao_id
          obj.tentram_tb = ''

          //if (obj.liBienBan.length > 0)
          obj.ShowDialog()
        }
        kt_thaydoi = obj.kt_thaydoi
      } catch (ex) {
        this.$toast.error(`${ex}`)
      }
    },
    async cboQuyTrinh_SelectedValueChanged() {
      // console.log('cboQuyTrinh_SelectedValueChanged')
      // console.log(this.cboQuyTrinh.list)
      // if (this.cboQuyTrinh.list.length > 0) {
      //   if (this.cboQuyTrinh.value == null) return
      //   this.quytrinh_id = this.cboQuyTrinh.value
      //   // this.dichvuvt_id = checkdata.MAP_ID('dichvuvt_id', DatabaseConstants.DB2 + '.quytrinh', 'where quytrinh_id = ' + quytrinh_id)
      //   // let table, result, where, groupby
      //   // table = '' + DatabaseConstants.DB2 + '.huonggiao a,' + DatabaseConstants.DB2 + '.huonggiao_ldv b'
      //   // result = ' b.luong_id, a.huonggiao_id, a.huonggiao '
      //   // where = '  a.quytrinh_id = b.quytrinh_id and a.huonggiao_id = b.huonggiao_id '
      //   // where += '  and a.quytrinh_id = ' + quytrinh_id + ' and  a.tthd_id = ' + tthd_id
      //   // groupby = ''
      //   // ds = bangts.Laythongtin_table(table, where, result, groupby)
      //   let ds = []
      //   if (ds.length > 0) {
      //     this.luong_id = ds[0].luong_id
      //     this.huonggiao_id = ds[0].huonggiao_id
      //   } else {
      //     this.$toast.warning('Không tìm thấy luồng quy trình nghiệp vụ này.\nBạn hãy liên hệ Admin kiểm tra lại')
      //     return
      //   }
      //   await this.HienThiGiaoDien(this.luong_id)
      //   this.hienthi_mau_tgcamket = 0
      //   if (this.PHAILAM(this.luong_id, 'HIENTHI_MAU_THICONG')) this.hienthi_mau_tgcamket = 1
      //   if (!this.PHAILAM(this.luong_id, 'GIAOVIEC')) {
      //     this.chkNgayBG.value = true
      //     this.chkNgayGV.value = true
      //     this.dtpNgayBG.value = DateTimeHelper.NgayCN().AddSeconds(30)
      //     this.dtpNgayGV.value = DateTimeHelper.NgayCN()
      //   }
      // }
    },
    xuLyRdoPhieuMoi() {
      this.rdoPhieuMoi.value = false
      this.rdoPhieuTra.value = false
      this.rdoPhieuDaHoanThanh.value = false
      this.tsbtnTraPhieuTinh.enabled = true
      if (this.rdoPhieu.value == 0) this.rdoPhieuMoi.value = true
      if (this.rdoPhieu.value == 1) this.rdoPhieuTra.value = true
      if (this.rdoPhieu.value == 2) {
        this.rdoPhieuDaHoanThanh.value = true
        this.tsbtnTraPhieuTinh.enabled = false
      }
    },
    async rdoPhieuMoi_CheckedChanged() {
      console.log('rdoPhieuMoi_CheckedChanged')
      console.log(this.rdoPhieu.value)
      this.xuLyRdoPhieuMoi()
      if (this.rdoPhieuMoi.value) await this.HienThiDanhSachHDTB()
    },
    async rdoPhieuTra_CheckedChanged() {
      this.xuLyRdoPhieuMoi()
      if (this.rdoPhieuTra.value) await this.HienThiDanhSachHDTB()
    },
    async rdoPhieuDaHoanThanh_CheckedChanged() {
      console.log('rdoPhieuDaHoanThanh_CheckedChanged')
      console.log(this.rdoPhieu.value)
      this.xuLyRdoPhieuMoi()
      if (this.rdoPhieuDaHoanThanh.value) await this.HienThiDanhSachHDTB()
    },
    async btnNDTH_Click() {
      if (this.gridDanhSach.selected.length) {
        if (!confirm(`Bạn chắc chắn cập nhật Nội dung TH ${this.gridDanhSach.selected.length} phiếu?`)) return
        console.log('btnNDTH_Click')
        console.log(this.gridDanhSach.selected)
        let count = 0
        for (let i = 0; i < this.gridDanhSach.selected.length; i++) {
          try {
            let p_ds_para = {}
            p_ds_para.TINH_ID = this.gridDanhSach.selected[i].TINH_ID
            p_ds_para.PHIEU_ID = this.gridDanhSach.selected[i].PHIEU_ID
            p_ds_para.TXTNOIDUNGTH = this.txtNoiDungTH.value?.trim()
            let kq = await api.fn_btnndth_click_hoancongims_net(this.axios, {
              p_phanvung_id: this.$root.token.getPhanVungID(),
              p_ds_para: JSON.stringify(p_ds_para)
              // p_ds_para: p_ds_para
            })
            console.log(kq)
            if (kq.data.error_code == 'BSS-00000000') {
              count++
            } else {
              this.$toast.error(`Có lỗi : ${kq.data.message_detail}`)
              break
            }
          } catch (ex) {
            this.$toast.error('Có lỗi : ' + ex)
            break
          }
          
        }
        if(count == this.gridDanhSach.selected.length) {
          this.$toast.success('Cập nhật thành công!')
          await this.HienThiDanhSachHDTB()
        }
      } else {
        this.$toast.warning('Vui lòng chọn phiếu để thao tác')
      }
      return
    },
    async tsbtnCTKetNoi_Click() {
      try {
        // frmCTKetNoi obj = new frmCTKetNoi();
        obj.vma_tk = txtMaGD.value.trim()
        obj.vma_tb = txtMaTB.value.trim()
        obj.vhuong_kn = vhuongkn
        obj.vhdtb_id = hdtb_id
        obj.ShowDialog()
      } catch (ex) {
        this.$toast.error('Có lỗi: ' + ex)
      }
    },
    async HienThiDVGT(dt) {
      console.log('HienThiDVGT')
      console.log(dt)
      if (dt.length > 0) {
        this.gridDangkyDV.list = dt
      } else {
        this.gridDangkyDV.list = []
      }
    },
    async tsbtnHen_Click() {
      if (this.hdkh_id != 0) {
        // frmHenKH f = new frmHenKH();
        f.vhdkh_id = this.hdkh_id
        f.Quyen_Duoc_hen = 1 //Megawan chưa khóa
        f.ShowDialog()

        let vma_tb = this.txtMaTB.value.trim()
        await this.HienThiDanhSachHDTB()

        // this.gridDanhSach.value = FindGridIndexByValue('MA_TB', vma_tb)
      }
    },
    async checkAll_CheckedChanged() {
      for (let k = 0; k < dsIn.length; k++) {
        if (checkAll.value) dsIn[k].CHON = 1
        else dsIn[k].CHON = 0
      }
    },
    async toolStripButton1_Click() {
      console.log('toolStripButton1_Click')
      if (!this.gridDanhSach.value) return
      let donvi_nhan_id = 0
      donvi_nhan_id = await this.gridDanhSach.value.donvi_nhan_id
      // frmGiaoPhieu_Net f;
      let input = {
        phieu_cha_id: this.phieu_id,
        phieu_tinh_id: 0,
        donvi_ql_id: donvi_nhan_id,
        vtinh_id: this.$root.token.getPhanVungID()
      }
      if (this.tinh_khdn.includes(this.tinh_id))
        input = {
          phieu_cha_id: this.phieu_id,
          phieu_tinh_id: 0,
          donvi_ql_id: donvi_nhan_id,
          vtinh_id: this.tinh_id
        }
      else
        input = {
          phieu_cha_id: this.phieu_id,
          phieu_tinh_id: this.phieu_tinh_id,
          donvi_ql_id: donvi_nhan_id,
          vtinh_id: this.tinh_id
        }
      this.$refs.frmGiaoPhieu_Net.openDialog(input)
      await this.HienThiDS_PhieuTH(this.phieu_id)
    },
    async gvDanhSach_KeyDown() {
      if (e.KeyCode == Keys.Space) {
        if (this.gridDanhSach.length == 0) return
        if (this.gridDanhSach.length == 0) return
        let selectRows = this.$refs.gridDanhSach.getSelectedRecords()
        let fState = this.gridDanhSach.value[0].CHON
        let State = fState == '0' ? 1 : 0
        for (let i = 0; i < selectRows.length; i++) {
          // gridDanhSach.SetRowCellValue(selectRows[i], 'CHON', State)
        }
        // gridDanhSach.Update()
      }
    },
    async gvDanhSach_DataSourceChanged() {
      if (this.gridDanhSach.list != null) {
        await this.gvDanhSach_SelectionChanged()
      }
    },
    async tsbtnFileNT_Click() {
      let formData = new FormData()
      for (var file of this.$refs.files.files) {
        formData.append('files', file)
      }
      let file_id = await this.callApiWrapper(api.get_keys, {
        keyname: 'FILEHOSO_ID'
      })
      let kq = await this.callApiWrapper(api.upload_file, formData, { headers: { 'Content-Type': 'multipart/form-data' } })
      let rs = await this.callApiWrapper(api.tsbtnDownloadFileNT_Click, {
        vphanvung_id: this.gridDanhSach.value.TINH_ID,
        vthuebao_id: this.thuebao_id,
        vfile_id: file_id,
        vthanhtoan_id: this.thanhtoan_id,
        vma_tb: this.txtMaTB.value,
        vhdtb_id: this.hdtb_id
      })
      this.$toast.success('Cập nhật thành công!')
      await this.HienThiDanhSachHDTB()
      console.log('api.upload_file')
      console.log(kq)
    },
    async changeFileUpload() {
      console.log('changeFileUpload')
      console.log(this.$refs.files.files[0].name)
      console.log(this.$refs.files.files)
      await this.tsbtnFileNT_Click()
      // if (this.$refs.files.files[0].name.length > 0) {
      //   let result = this.$refs.files.files[0].name.substring(this.$refs.files.files[0].name.indexOf('.') + 1, this.$refs.files.files[0].name.length)
      //   if (result != 'txt') {
      //     this.ShowAlert('File bạn chọn phải có định dạng .txt!', 2)
      //     return
      //   }
      // }
      // this.listbox.ds_file.value.url = this.$refs.files.files[0].name
    },
    async tsbtnDownloadFileNT_Click() {
      console.log('tsbtnDownloadFileNT_Click')
      console.log(this.gridDanhSach.value)
      if (this.gridDanhSach.value == null) {
        this.$toast.warning('Vui lòng chọn phiếu!')
        return
      }
      if (this.tinh_khdn.includes(this.tinh_ph)) {
        //let loaihinh = new BanCheoBase().GET_STRING_FUNCTION("{?DB2}.HIENTHI_HOSO_KHDN", "vhdtb_id", hdtb_id);
        let loaihinh = await this.callApiWrapper(api.fn_bancheo_hienthi_hoso_khdn, {
          vphanvung_id: this.$root.token.getPhanVungID(),
          vtinh_id: this.gridDanhSach.value.TINH_ID,
          vhdtb_id: this.hdtb_id
        })
        let data = {}
        // new BanCheoBase().GET_STRING_FUNCTION(new BanCheoBase().tinh_thicong(tinh_ph), '{?DB2}.HIENTHI_HOSO_KHDN', 'vhdtb_id', hdtb_id)
        if (loaihinh == 'ok') {
          data.vhdtb_id = this.hdtb_id
          console.log('frmTraCuuHoSoHDTB')
          this.$refs.frmTraCuuHoSoHDTB.openDialog(data)
        } else {
          // data.hdtb_ban = this.hdtb_id
          // data.hdtb_tc = 0
          // data.tinh_id = this.tinh_ph
          // this.$refs.frmDSHoSoBC.openDialog(data)
          console.log('frmDSHoSoBC')
          console.log(this.gridDanhSach.value)
          this.frmDSHoSoBC.input.visible = true
          this.frmDSHoSoBC.input.vhdtb_ban = this.gridDanhSach.value.HDTB_ID
          console.log('this.frmDSHoSoBC.input.vhdtb_ban')
          setTimeout(() => {
            this.$refs.frmDSHoSoBC.showModal()
            this.$refs.frmDSHoSoBC.hdtb_tc = 0
            this.$refs.frmDSHoSoBC.tinh_id = this.gridDanhSach.value.TINH_ID
            this.$refs.frmDSHoSoBC.khoiTao()
          }, 500)
        }
      } else {
        // frm.hdtb_ban = 0
        // frm.hdtb_tc = this.hdtb_id
        // frm.tinh_id = this.tinh_ph
        // this.$refs.frmDSHoSoBC.openDialog(data)
        console.log('else frmDSHoSoBC')
        console.log()
        this.frmDSHoSoBC.input.visible = true
        this.frmDSHoSoBC.input.vhdtb_ban = 0
        setTimeout(() => {
          this.$refs.frmDSHoSoBC.showModal()
          this.$refs.frmDSHoSoBC.hdtb_tc = this.gridDanhSach.value.HDTB_ID
          this.$refs.frmDSHoSoBC.tinh_id = this.gridDanhSach.value.TINH_ID
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
      // console.log('tsbXemHS_Click', data)
      // this.$refs.frmDSHoSoBC.openDialog(data)
    },
    async tsbtnNhapMoiTT_Click() {
      if (this.gridDanhSach.value == null) {
        this.$toast.warning('Vui lòng chọn phiếu!')
        return
      }
      await this.HT_DLG_NHAP_NOIDUNG(null)
    },
    async HT_DLG_NHAP_NOIDUNG(tientrinhtc_id) {
      this.frmNoiDung.input = {
        row: this.gridDanhSach.value
      }
      this.ejsDialog.name = 'frmNoiDung'
      this.ejsDialog.header = 'Nhập nội dung tiến trình thi công'
      console.log('HT_DLG_NHAP_NOIDUNG')
      console.log(this.$refs.ejsDialog)
      this.$refs.ejsDialog.show()
    },
    async gridviewTienTrinh_ShownEditor() {
      if (gridTienTrinh.FocusedRowHandle >= 0) {
        let vnguoi_cn = this.gridTienTrinh.value.NGUOI_CN
        let vtientrinhtc_id = this.gridTienTrinh.value.TIENTRINHTC_ID

        if (vnguoi_cn == this.$root.token.getUserName()) {
          if (gridTienTrinh.FocusedColumn == SMS_1) {
            await this.HT_DLG_GUI_SMS(vtientrinhtc_id)
          }

          if (gridTienTrinh.FocusedColumn == SUA_1) {
            await this.HT_DLG_NHAP_NOIDUNG(vtientrinhtc_id)
          }

          if (gridTienTrinh.FocusedColumn == XOA_1) {
            await this.HT_DLG_XOA_TIENTRINH(vtientrinhtc_id)
          }
        } else this.$toast.warning('Bạn không thể thao tác trên tiến trình của người khác')

        gridTienTrinh.FocusedColumn = TIENTRINHBH_ID
      }
    },
    async frmTraPhieuVIP_onBtnTraphieuClicked() {
      await this.btnLayTTMoi_Click()
      this.$refs.ejsDialog.hide()
    },
    async HT_DLG_GUI_SMS(vtientrinhtc_id) {
      let bhi = this.gridDanhSach.value.HDTB_ID
      let tti = vtientrinhtc_id

      if (bhi != null && tti != null) {
        // let frm = new WinUI.WinUIQLDHXLSuCo.frmGuiSMS(tinh_tc);
        frm.value = 'Gửi SMS tiến trình thi công'
        frm.vhdtb_id = bhi
        frm.vnhom_id = 1
        frm.vtientrinhtc_id = tti
        frm.vma_tb = await this.txtMaTB.value
        frm.txtNoiDung.value = await this.gridTienTrinh.value.NOIDUNG
        frm.ShowDialog()
      }
    },
    async HT_DLG_TIENTRINH_SMS(vtientrinhtc_id) {
      // let frmTienTrinhSMS = new Form()
      // {
      //     Name = "frmTienTrinhSMS",
      //     Text = "Danh sách tin nhắn đã gửi",
      //     Size = new System.Drawing.Size(600, 300),
      //     StartPosition = FormStartPosition.CenterScreen,
      //     FormBorderStyle = FormBorderStyle.FixedDialog,
      //     ShowIcon = false,
      //     ShowInTaskbar = false,
      //     MaximizeBox = false,
      //     MinimizeBox = false,
      //     AutoScaleMode = AutoScaleMode.None
      // };

      let tbrChucNang = new ToolStrip()
      {
        ;(Name = 'tbrChucNang'), (Dock = DockStyle.Top)
      }

      let btnHuyBo = new ToolStripButton()
      {
        ;(Name = 'btnHuyBo'), (Text = 'Đóng'), (Image = Properties.Resources.closeform)
      }
      btnHuyBo.Click += new EventHandler(btnHuyBo_Click)

      let dgvTienTrinhSMS = new DataGridView()
      {
        ;(Name = 'dgvTienTrinhSMS'),
          (Dock = DockStyle.Fill),
          (AllowUserToAddRows = false),
          (AllowUserToDeleteRows = false),
          (AllowUserToOrderColumns = false),
          (AllowUserToResizeColumns = false),
          (AllowUserToResizeRows = false),
          (AutoSizeColumnsMode = DataGridViewAutoSizeColumnsMode.Fill),
          (RowHeadersVisible = false),
          (SelectionMode = DataGridViewSelectionMode.FullRowSelect),
          (AutoGenerateColumns = false),
          (EditMode = DataGridViewEditMode.EditProgrammatically),
          (ColumnHeadersHeightSizeMode = DataGridViewColumnHeadersHeightSizeMode.DisableResizing)
      }
      // DataGridViewColumn colNguoiGui, colNgayGui, colSoDT, colNoiDung;
      // dgvTienTrinhSMS.Columns.AddRange(
      //     colNguoiGui = new DataGridViewTextBoxColumn
      //     {
      //         Name = "colNguoiGui",
      //         DataPropertyName = "NGUOI_CN",
      //         HeaderText = "Người gửi",
      //         FillWeight = 15
      //     },
      //     colNgayGui = new DataGridViewTextBoxColumn
      //     {
      //         Name = "colNgayGui",
      //         DataPropertyName = "NGAY_CN",
      //         HeaderText = "Ngày gửi",
      //         FillWeight = 30
      //     },
      //     colSoDT = new DataGridViewTextBoxColumn
      //     {
      //         Name = "colSoDT",
      //         HeaderText = "Số điện thoại",
      //         DataPropertyName = "SO_DT",
      //         FillWeight = 15
      //     },
      //     colNoiDung = new DataGridViewTextBoxColumn
      //     {
      //         Name = "colNoiDung",
      //         HeaderText = "Nội dung",
      //         DataPropertyName = "NOIDUNG",
      //         FillWeight = 40
      //     }
      // );
      colNgayGui.DefaultCellStyle.Format = 'dd/MM/yyyy HH:mm:ss'
      //let str = "";
      //str += " select nguoi_cn, ngay_cn, so_dt, noidung";
      //str += " from css_" + tinh_tc.matinh + ".tientrinh_sms";
      //str += " where tientrinhtc_id = " + vtientrinhtc_id;
      //str += " order by ngay_cn desc";
      dgvTienTrinhSMS.list = bancheo.GET_DATA_OBJECT_PL(tinh_tc, '{?DB2}.SP_TT_TIENTRINH_SMS', 'vreturnds', 'vtientrinhtc_id', vtientrinhtc_id)
      //bancheo.GET_DATA_SQL(tinh_tc, str);
      dgvTienTrinhSMS.CellDoubleClick += new DataGridViewCellEventHandler(dgvTienTrinhSMS_CellDoubleClick)

      // tbrChucNang.Items.AddRange(new ToolStripItem[] { btnHuyBo });
      // frmTienTrinhSMS.Controls.AddRange(new Control[] { dgvTienTrinhSMS, tbrChucNang });
      frmTienTrinhSMS.ShowDialog()
    },
    async dgvTienTrinhSMS_CellDoubleClick() {
      // let dgvTienTrinhSMS = sender as DataGridView;
      let vnguoi_cn = dgvTienTrinhSMS.Rows[e.RowIndex].Cells.colNguoiGui.value
      // let vngay_cn = let.Format("{0:dd/MM/yyyy HH:mm:ss}", dgvTienTrinhSMS.Rows[e.RowIndex].Cells.colNgayGui.value);
      let vso_dt = dgvTienTrinhSMS.Rows[e.RowIndex].Cells.colSoDT.value
      let vnoidung = dgvTienTrinhSMS.Rows[e.RowIndex].Cells.colNoiDung.value
      // MessageBox.Show(let.Format("Người gửi: {0}\r\nNgày gửi: {1}\r\nSĐT nhận: {2}\r\nNội dung:\r\n{3}", vnguoi_cn, vngay_cn, vso_dt, vnoidung), "Xem tin nhắn");
    },
    // Thread thrTienTrinh;
    async NAP_GRD_TIENTRINH() {
      console.log('NAP_GRD_TIENTRINH')
      console.log(this.gridDanhSach.value)
      if (this.gridDanhSach.value) {
        try {
          let vhdtb_id = this.gridDanhSach.value.HDTB_TC
          this.gridTienTrinh.visible = true
          // Controls_SetVisible(peLoading, true)
          let str = ''
          // this.gridTienTrinh.list = await this.callApiWrapper(api.sp_tt_tientrinh_tc, {
          //   vphanvung_id: this.$root.token.getPhanVungID(),
          //   vhdtb_id: this.vhdtb_id
          // })
          // wtf
          this.gridTienTrinh.list = await this.callApiWrapper(api.sp_khdn_lay_ds_tientrinh_tc, {
            vphanvung_id: this.tinh_ph,
            vhdtb_id: this.gridDanhSach.value.hdtb_id,
            vtinh_id: this.gridDanhSach.value.tinh_id,
            vhdtb_tc: this.gridDanhSach.value.hdtb_tc,
            vtinh_tc: this.gridDanhSach.value.tinh_tc
          })
        } catch {}
      } else {
        this.gridTienTrinh.list = []
        this.txtMaGD.value = ''
        this.txtMaTB.value = ''
        this.txtTenTB.value = ''
        this.txtDiaChiLD.value = ''
      }
    },
    async HT_DLG_XOA_TIENTRINH(vtientrinhtc_id) {
      let str = ''
      str += 'select * from {?DB2}.tientrinh_sms WHERE tientrinhtc_id = ' + vtientrinhtc_id
      let sdt = '-1'
      let dt = await this.callApiWrapper(api.sp_tt_tientrinh_sms, {
        vphanvung_id: this.$root.token.getPhanVungID(),
        vtientrinhtc_id: vtientrinhtc_id
      })
      if (dt.length > 0) sdt = dt[0].so_dt
      if (sdt != '-1') {
        this.$toast.warning('Bạn không thể xóa tiến trình này\r\nTiến trình đã được gửi tin nhắn đến ' + sdt)
        return
      }
      if (Message_Box.ShowQuestion('Bạn có thực sự muốn xóa tiến trình được chọn không ?') == DialogResult.Yes) {
        try {
          await this.callApiWrapper(api.sp_delete_tientrinh_tc, {
            vphanvung_id: this.$root.token.getPhanVungID(),
            vtientrinhtc_id: vtientrinhtc_id
          })
          await this.NAP_GRD_TIENTRINH()
        } catch (ex) {
          this.$toast.error(ex)
        }
      }
    },

    async tsbtnTraPhieuTinh_Click() {
      console.log('tsbtnTraPhieuTinh_Click')
      console.log(this.gridDanhSach.value)
      if (this.gridDanhSach.value == null) {
        this.$toast.warning('Vui lòng chọn phiếu!')
        return
      }
      if (this.tinh_khdn.includes(this.tinh_id)) {
        this.$toast.error('Tính năng này chỉ áp dụng với các phiếu được chuyển từ 63 TTP.')
        return
      }
      this.frmTraPhieuVIP.input = {
        vds_phieu: this.gridDanhSach.selected,
      }
      this.ejsDialog.name = 'frmTraPhieuVIP'
      this.ejsDialog.header = 'Trả phiếu'
      this.$refs.ejsDialog.show()
      // if (frm.chapnhan) {
      //   await this.HienThiDanhSachHDTB()
      // }
      //tsbtnLayTT.PerformClick();
    },
    async tsbtnKichHoat_Click() {
      if (this.gridDanhSach.value == null) {
        this.$toast.warning('Vui lòng chọn phiếu!')
        return
      }
      try {
        await this.KichHoat_IMS()
      } catch (ex) {
        this.$toast.error('' + ex)
      }
    },
    async KichHoat_IMS() {
      try {
        if (!(await this.Cap_Nhat())) return
        // LapHopDongFacade objLHD = new LapHopDongFacade();

        if (DONGBO_TEST.TEST == false) {
          if (this.cboLoaiHD.value == LoaiHopDong.DAT_MOI || this.cboLoaiHD.value == LoaiHopDong.KHOIPHUC_SD) {
            if (!confirm('Bạn có chắc chắn kích hoạt số máy ' + this.txtMaTB.value.trim() + ' hay không?')) return
            if (this.thuebao_cha_id == 0 && this.hdtb_cha_id == 0) await this.KichHoat_DM_IMS()
            else KickHoat_ThemTB_IMS()
          }
          if (cboLoaiHD.value == LoaiHopDong.CHUYENDOI_LH) {
            await this.KichHoat_CDLH_IMS()
          }
        }
        await this.HienThiDanhSachHDTB()
      } catch (ex) {
        this.$toast.error(`Có lỗi: ${ex}`)
      }
    },
    async KichHoat_DM_IMS() {
      try {
        // let service = new IMSService();
        let errMsg = ''
        if (this.txtSoKenh.value || this.txtSoKenh.value == '0') this.txtSoKenh.value = '20'
        let SoKenh = 20
        this.txtSoKenh.value = SoKenh + ''

        if (isNullOrEmpty(this.txtIP.value)) {
          // txtIP.Focus()
          this.$toast.warning('IP không được để trống.')
          return
        }

        try {
          this.txtIP.value
        } catch {
          // txtIP.Focus()
          this.$toast.warning('IP không đúng định dạng.')
          return
        }

        if (isNullOrEmpty(this.txtPort.value)) {
          txtPort.Focus()
          this.$toast.warning('Port không được để trống.')
          return
        }
        let kq = false
        if (this.cboTongDaiIMS.value != 2) {
          // kq = service.KhoiTaoThueBao_siptrunk(tinh_tc, txtMaTB.value, txtTrunkID.value, SoKenh, txtIP.value + ":" + txtPort.value, (cboTongDaiIMS.value == 0 ? "10.201.12.6:5060" : "10.202.12.6:5060"), (cboTongDaiIMS.value == 0 ? "HNI" : "HCM"), ref errMsg);
          if (kq) {
            //Kích hoạt dịch vụ GT (nếu có)
            if (this.gridViewDangkyDV.list.length > 0) {
              // kq = service.ThayDoiDVGT_Siptrunk(txtTrunkID.value, dtDVGT, ref errMsg);
              if (kq) {
                new BanCheoBase().EXECUTE_NON_QUERY(tinh_tc, '{?DB2}.CAPNHAT_NGAYKH_STATUS_V2', 'vhdtb_id', hdtb_id, 'vngay_kh', DateTimeHelper.NgayCN(), 'vstatus', TRANGTHAI_DONGBO.DONGBO_CM, 'vnguoi_kh', this.$root.token.getUserName(), 'vip_kh', tt_nd.ip)
                this.$toast.success('Khởi tạo số máy ' + txtMaTB.value.trim() + ' lên hệ thống IMS mới thành công!')
              } else {
                //Đăng ký DVGT lỗi
                // service.ThanhLy_DuongTrunk(tinh_tc, txtTrunkID.value, txtMaTB.value, ref errMsg, 4);
                this.$toast.error('Có lỗi trong quá trình đăng ký dịch vụ gia tăng trên hệ thống IMS mới. Lỗi: ' + errMsg)
              }
            } else {
              new BanCheoBase().EXECUTE_NON_QUERY(tinh_tc, '{?DB2}.CAPNHAT_NGAYKH_STATUS_V2', 'vhdtb_id', hdtb_id, 'vngay_kh', DateTimeHelper.NgayCN(), 'vstatus', TRANGTHAI_DONGBO.DONGBO_CM, 'vnguoi_kh', this.$root.token.getUserName(), 'vip_kh', tt_nd.ip)
              this.$toast.success('Khởi tạo số máy ' + txtMaTB.value.trim() + ' lên hệ thống IMS mới thành công!')
            }
          } else this.$toast.error('Có lỗi trong quá trình khởi tạo trên hệ thống IMS mới. Lỗi: ' + errMsg)
        } else {
          //Xử lý cho TH Aaranet
          if (isNullOrEmpty(this.txtProfile.value)) {
            // txtProfile.Focus()
            this.$toast.warning('Chưa nhập tên profile.')
            return
          }
          if (isNullOrEmpty(this.txtProfileInfo.value)) {
            // txtProfileInfo.Focus()
            this.$toast.warning('Profile info không được để trống.')
            return
          }

          // kq = service.KhoiTaoThueBao_siptrunk_aaranet(tinh_tc, txtMaTB.value, txtProfile.value, txtProfileInfo.value, txtIP.value + ":" + txtPort.value, cboEndPoint.value.trim(),
          //     txtTrunkID.value, txtTrunkID.value, SoKenh, txtUserName.value, txtPassword.value, ref errMsg);
          if (kq) {
            //Kích hoạt dịch vụ GT (nếu có)
            if (gridViewDangkyDV.list.length > 0) {
              // kq = service.ThayDoiDVGT_Siptrunk_AARANET(txtTrunkID.value, dtDVGT, ref errMsg);
              if (kq) {
                // new BanCheoBase().EXECUTE_NON_QUERY(tinh_tc, "{?DB2}.CAPNHAT_NGAYKH_STATUS_V2",
                // "vhdtb_id", hdtb_id,
                // "vngay_kh", DateTimeHelper.NgayCN(),
                // "vstatus", TRANGTHAI_DONGBO.DONGBO_CM,
                // "vnguoi_kh", this.$root.token.getUserName(),
                // "vip_kh", tt_nd.ip);
                this.$toast.success('Khởi tạo số máy ' + txtMaTB.value.trim() + ' lên hệ thống IMS mới thành công!')
              } else {
                //Đăng ký DVGT lỗi, hiển thị lên thông báo, cần quy định lại xem khi đăng ký dịch vụ GTGT lỗi thì gọi gì tiếp theo
                //service.ThanhLy_DuongTrunk(tinh_tc, txtTrunkID.value, txtMaTB.value, ref errMsg, 4);
                this.$toast.error('Có lỗi trong quá trình đăng ký dịch vụ gia tăng trên hệ thống IMS mới. Lỗi: ' + errMsg)
              }
            } else {
              // new BanCheoBase().EXECUTE_NON_QUERY(tinh_tc, "{?DB2}.CAPNHAT_NGAYKH_STATUS_V2",
              //     "vhdtb_id", hdtb_id,
              //     "vngay_kh", DateTimeHelper.NgayCN(),
              //     "vstatus", TRANGTHAI_DONGBO.DONGBO_CM,
              //     "vnguoi_kh", this.$root.token.getUserName(),
              //     "vip_kh", tt_nd.ip);
              this.$toast.success('Khởi tạo số máy ' + txtMaTB.value.trim() + ' lên hệ thống IMS mới thành công!')
            }
          } else this.$toast.error('Có lỗi trong quá trình khởi tạo trên hệ thống IMS mới. Lỗi: ' + errMsg)
        }
      } catch (ex) {
        this.$toast.error('Có lỗi: ' + ex.Message)
      }
    },

    async KickHoat_ThemTB_IMS() {
      try {
        // let service = new IMSService();
        let errMsg = ''

        let kq = false
        if (this.cboTongDaiIMS.value != 2) {
          // kq = service.ThemThueBao_siptrunk(tinh_tc, txtMaTB.value, txtTrunkID.value, ref errMsg);
          if (kq) {
            // new BanCheoBase().EXECUTE_NON_QUERY(tinh_tc, "{?DB2}.CAPNHAT_NGAYKH_STATUS_V2",
            // "vhdtb_id", hdtb_id,
            // "vngay_kh", DateTimeHelper.NgayCN(),
            // "vstatus", TRANGTHAI_DONGBO.DONGBO_CM,
            // "vnguoi_kh", this.$root.token.getUserName(),
            // "vip_kh", tt_nd.ip);
            this.$toast.success('Khởi tạo số máy ' + this.txtMaTB.value.trim() + ' lên hệ thống IMS mới thành công!')
          } else this.$toast.error('Có lỗi trong quá trình khởi tạo trên hệ thống IMS mới. Lỗi: ' + errMsg)
        } else {
          // kq = service.ThemThueBao_siptrunk_aaranet(tinh_tc, txtMaTB.value, txtIP.value + ":" + txtPort.value, txtTrunkID.value, ref errMsg);
          if (kq) {
            // new BanCheoBase().EXECUTE_NON_QUERY(tinh_tc, "{?DB2}.CAPNHAT_NGAYKH_STATUS_V2",
            // "vhdtb_id", hdtb_id,
            // "vngay_kh", DateTimeHelper.NgayCN(),
            // "vstatus", TRANGTHAI_DONGBO.DONGBO_CM,
            // "vnguoi_kh", this.$root.token.getUserName(),
            // "vip_kh", tt_nd.ip);
            this.$toast.success('Thêm số máy ' + this.txtMaTB.value.trim() + ' vào đường trunk thành công !')
          } else this.$toast.error('Có lỗi trong quá trình thêm thuê bao vào đường trunk. Lỗi: ' + errMsg)
        }
      } catch (ex) {
        this.$toast.error('Có lỗi: ' + ex.Message)
      }
    },

    async KichHoat_CDLH_IMS() {
      try {
        // let dtBienDong = bancheo.GET_DATA_OBJECT_PL(tinh_tc, "{?DB2}.SP_TT_BD_THUEBAO", "vreturnds", "vhdtb_id", hdtb_id);
        //new BanCheoBase().GET_DATA_SQL(tinh_tc, "select * from css_" + tinh_tc.matinh + ".bd_thuebao where hdtb_id=" + hdtb_id);
        if (dtBienDong.length > 0) {
          let bd_dichvuvt_id = dtBienDong[0].dichvuvt_id
          let bd_loaitb_id = dtBienDong[0].loaitb_id

          if (bd_dichvuvt_id == DichVuVienThong.CO_DINH) {
            if (confirm('Bạn có chắc chắn kích hoạt số máy ' + this.txtMaTB.value.trim() + ' hay không?')) {
              if (this.thuebao_cha_id == 0 && this.hdtb_cha_id == 0) await this.KichHoat_DM_IMS()
              else await this.KickHoat_ThemTB_IMS()
            }
          }
          if (bd_dichvuvt_id == DichVuVienThong.IMS) {
            if (confirm('Bạn có chắc chắn kích hoạt số máy ' + this.txtMaTB.value.trim() + ' hay không?')) {
              if (bd_loaitb_id == LoaiHinhTB.IMS_POTS || bd_loaitb_id == LoaiHinhTB.IMS_SIP) {
                let message = ''
                //Goi lenh huy tren tong dai IMS
                // let ims = new IMSService();
                // let kq = ims.HuyThueBao_v2(tinh_tc.mavung + txtMaTB.value.trim(), ref message);

                if (kq)
                  if (this.thuebao_cha_id == 0 && this.hdtb_cha_id == 0) await this.KichHoat_DM_IMS()
                  else await this.KickHoat_ThemTB_IMS()
                else this.$toast.success(`Lỗi khi hủy thuê bao Sip, Pots. Chi tiết: ${message}`)
              }
            }
          }
        }
      } catch (ex) {
        this.$toast.error('Có lỗi: ' + ex.Message)
      }
    },
    async cboTongDaiIMS_SelectedIndexChanged() {
      if (this.cboTongDaiIMS.value == 2) {
        //Enable cho những control của Aaranet
        this.cboEndPoint.enabled = true
        this.txtProfile.enabled = true
        this.txtProfile.value = ''
        this.txtProfileInfo.value = ''
        this.txtProfileInfo.enabled = true
      } else {
        this.cboEndPoint.enabled = false
        this.txtProfile.enabled = false
        this.txtProfile.value = ''
        this.txtProfileInfo.value = ''
        this.txtProfileInfo.enabled = false
      }
    },
    PHAILAM(luong_id, CODE) {
      if (this.dtThaoTac != null && this.dtThaoTac.length > 0) {
        if (this.dtThaoTac.find((e) => e.enable == '1' && e.code == CODE)) {
          return true
        }
      }
      return false
    },
    async tsbtnHuyKH_Click() {
      this.$toast.info('Không làm')
    },
    cmenuMore_BeforeItemRender(args) {
      console.log('cmenuMore_BeforeItemRender')
      console.log(args.item.id)
      let container = createElement('div', { className: 'drop-container' })
      if (args.item.items != null) {
      }
      if (args.item.child_icon) {
        let icon = createElement('span', { className: `icon ${args.item.child_icon} drop-icon` })
        icon.style.color = args.item.items.length > 0 ? '#C0C0C0' : '#3277F5'
        icon.style.margin = '0 10px 0 0'
        container.appendChild(icon)
      }

      let text = createElement('span')

      text.style.color = args.item.items.length > 0 ? '#C0C0C0' : '#3277F5'
      text.textContent = args.item.child_text
      container.style.padding = '0px 5px !important'
      container.appendChild(text)
      args.element.appendChild(container)
      args.element.style.height = '35px'
      args.element.style.borderBottom = '.5px solid #3277F5'
      args.element.style.display = 'flex'
      args.element.style.justifyContent = 'flex-start'
      args.element.style.alignItems = 'center'
    },
    contextMenuOnClicked(targetId) {
      document.getElementById(targetId).ej2_instances[0].open(event.clientY, event.clientX)
    },
    async contextMenuSelected(menu) {
      console.log('contextMenuSelected')
      console.log(menu)
      switch (menu.item.id) {
        case 'hoan_thanh':
          break
        case 'phieu_nhap':
          break
        case 'phieu_xuat':
          break
        case 'phieu_chuyen':
          break
        case 'chi_tiet_sr':
          break
        case 'phieu_tam_ung_vt':
          break
        case 'bbgn_nhap_kho':
          break
        case 'bbgn_xuat_kho':
          break
        case 'phieu_xuat_dvtc':
          break
        case 'in_tem_serial':
          break
        case 'phieu_nhap_kho_khong_thue':
          break
        case 'phieu_giao_hoan_tra_vt':
          break
        case 'sua_ct':
          break
        case 'dc_loai_kho':
          break
        case 'dc_chat_luong':
          break
        case 'dc_ghi_chu':
          break
        case 'xuat_ims':
          break
        case 'gui_sms':
          break
        case 'tao_serial':
          break
        case 'in_serial':
          break
      }
    },
    ejsDialogBeforeOpen() {
      let width = null
      let height = 'auto'
      if (this.ejsDialog.name == 'frmNhapText') {
        width = 'auto'
      } else {
        width = '95%'
      }
      if (this.ejsDialog.name == 'frmGiaoPhieu_NET') {
        height = '800px'
      }
      this.ejsDialog.config = {
        ...this.ejsDialog.config,
        width: width,
        height: height,
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
    },
    //gridDanhSach
    async gvDanhSach_SelectionChanged(e) {
      console.log('gvDanhSach_SelectionChanged')
      console.log(e)
      let row = e.data
      if (row == null) {
        await this.Clear()
        return
      }
      this.gridDanhSach.value = row
      if (this.dt.length > 0) {
        // try {
        await this.HienThiTTHopDongTB(this.dsIn)
        // } catch (ex) {
        //   this.$toast.error(`Có lỗi: ${ex}`)
        // }
      }
    },
    async gridDanhSach_selectedItemsChanged(e) {
      console.log('gridDanhSach_selectedItemsChanged')
      console.log(e)
      this.gridDanhSach.selected = e
    },
    //file khac o day
    async tinh_thicong(tinh_id) {
      console.log('tinh_thicong')
      console.log(tinh_id)
      let tinh = {}
      let rs = await this.callApiWrapper(api.post_lay_tinh_thi_cong, { tinhtc_id: tinh_id })
      if (rs.length > 0) {
        let dr = rs[0]
        tinh.user = dr.ten_truycap
        tinh.pass = dr.mat_khau
        tinh.nhanvien_id = dr.nhanvien_id
        tinh.donvi_id = dr.donvi_id
        tinh.matinh = dr.matinh
        tinh.TNS = dr.tns_string
        tinh.tinhthicong_id = dr.tinh_id
        tinh.mavung = dr.ma_vung
        // if (AppDomain.CurrentDomain.FriendlyName == "Shard.exe")
        //     tinh.ip_dll = string.Format("tcp://localhost:{0}/", 7900 + (tinh_id > 70 ? tinh_id - 30 : tinh_id));
        // else if (!isNullOrEmpty(dr.ONEBSS_IP))
        //     tinh.ip_dll = dr.ONEBSS_IP;
        // else tinh.ip_dll = dr.IP_DLL;
        tinh.onebss_ip = dr.ONEBSS_IP
      } else {
        tinh.user = ''
        tinh.pass = ''
        tinh.nhanvien_id = 0
        tinh.donvi_id = 0
        tinh.matinh = ''
        tinh.TNS = ''
        tinh.tinhthicong_id = 0
        tinh.ip_dll = ''
        tinh.onebss_ip = ''
      }
      return tinh
    },
    // utils
    ChuanHoaTen(ten) {
      if (!ten) return ''
      let tenNew = ''
      let strCon = ten.trim()
      let strXL = ''
      let k = 0
      let found = true
      if (strCon.length < 1) return
      strCon = strCon.replaceAll(/\s+/g, ' ')
      try {
        while (found) {
          k = strCon.indexOf(' ')
          if (k > 0) {
            strXL = strCon.substring(0, k)
            strCon = strCon.substring(k + 1)
            strXL = this.ChuyenChuHoa(strXL)
            if (tenNew !== '') {
              tenNew += ' ' + strXL
            } else tenNew = strXL
            found = true
          } else found = false
        }
        if (strCon !== '') {
          strXL = strCon
          strXL = this.ChuyenChuHoa(strXL)
          if (tenNew !== '') {
            tenNew = tenNew + ' ' + strXL
          } else {
            tenNew = strXL
          }
        }
        return tenNew
      } catch (e) {
        return ten
      }
    },
    ChuyenChuHoa(str) {
      let kq = ''
      let regex = /[A-Z][A-Z]/g
      if (regex.test(str)) kq = str.toUpperCase()
      else {
        if (str[0] === '(') {
          kq = str[0] + str[1].toUpperCase() + str.substring(2).toLowerCase()
        } else {
          kq = str[0].toUpperCase() + str.substring(1).toLowerCase()
        }
      }
      return kq
    },
    async ChuanHoa_XML(Xau) {
      let result = ''
      result = Xau.replace('&', '&amp;')
      result = result.replace('<', '&lt;')
      result = result.replace('>', '&gt;')
      result = result.replace("'", '&apos;')
      result = result.replace('"', '&quot;')
      return result
    },
    ConvertKeysToLowerCase(obj) {
      var output = {}
      for (let i in obj) {
        if (Object.prototype.toString.apply(obj[i]) === '[object Object]') {
          output[i.toLowerCase()] = this.ConvertKeysToLowerCase(obj[i])
        } else if (Object.prototype.toString.apply(obj[i]) === '[object Array]') {
          output[i.toLowerCase()] = []
          output[i.toLowerCase()].push(this.ConvertKeysToLowerCase(obj[i][0]))
        } else {
          output[i.toLowerCase()] = obj[i]
        }
      }
      return output
    },
    async giaoviec_success() {
      console.log('giaoviec_success')
    }
  }
}
</script>
<style scoped>
* {
  font-size: 12px;
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

.section-radio-menu {
  margin: 0 5px 0 0;
  color: red;
  font-weight: bold;
}

.bottom-container {
  height: 1000px;
  background: #ebf3fe;
  display: flex;
  flex-direction: column;
}

@media (max-width: 575.98px) {
  .bottom-container {
    height: auto;
    background: #ebf3fe;
    display: flex;
    flex-direction: column;
  }
}

.section-title-through-wrapper {
  width: 100%;
  display: flex;
  flex-direction: row;
  justify-content: center;
  align-items: center;
  padding: 10px 0;
}
.line-through {
  flex-grow: 1;
  height: 1px;
  margin: 0 0 0 3px;
  background: #c3c3cb;
  z-index: 1;
}
.section-title-through-text {
  font-size: 14px;
  font-weight: bold;
  color: #0d2097;
}
.info-wrapper {
  min-height: 700px;
  max-height: 700px;
  overflow-y: scroll;
  padding: 10px 10px;
}

.info-title {
  font-size: 16px;
  font-weight: 900;
  color: black;
}

.info-value {
  font-size: 16px;
  font-weight: 100;
  color: black;
}

.drop-container {
  color: #255fc5;
  padding: 50px 60px;
  background: red !important;
  display: flex;
}
.drop-icon {
  color: #255fc5 !important;
  margin: 0 40px 0 0;
}

.disabledInput {
  pointer-events: none;
  color: #666 !important;
  border-color: #9999 !important;
}

.disabledInput > a {
  color: #666 !important;
}

#frmHoanCongIMS_Net {
  position: absolute;
  top: 0px;
  left: 0px;
  right: 0px;
  bottom: 0px;
  overflow: auto;
  overflow-x: hidden;
}
</style>
