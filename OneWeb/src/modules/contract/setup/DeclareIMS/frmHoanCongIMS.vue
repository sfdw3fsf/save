<template>
  <div class="main-wrapper">
    <breadcrumb v-if="type == 'view'" :header="header" />
    <div class="list-actions-top">
      <ul class="list">
        <li v-for="(item, index) in menus.slice(0, menus.length > totalMenuShow ? totalMenuShow : menus.length)" :class="{ disable: !item.enabled, 'btn-action': item.enabled }">
          <a @click.prevent="onMenuClickedListener(item)"><span :class="`${item.icon} icon`"></span>{{ item.name }}</a>
        </li>
      </ul>
      <!-- <div v-if="menus.length > totalMenuShow" class="more dropdown">
      <a data-toggle="dropdown">
        <span class="icon -ap  icon-dots-three-vertical"></span>
      </a>
      <div class="dropdown-menu ovauto" style="max-height: 300px;">
        <li v-for="(item, index) in menus.slice(totalMenuShow, menus.length)" v-if="item.visible && item.view.includes(form)" :class="{ disable: !item.enabled, 'btn-action': item.enabled }">
          <a @click.prevent="handlerMenuOnClicked(item)" style="cursor:pointer"> <span class="icon one-connect"></span>{{ item.name }}</a>
        </li>
      </div>
    </div> -->
    </div>
    <!-- <div class="page-content" style="margin: 20px 0 0 0;">
      <div class="box-form  row-1-height">
        <div class="legend-title"><span class="fa icon fa-angle-up" :class="{ 'fa-angle-up': sectionThongTinThueBao.isVisible, 'fa-angle-down': !sectionThongTinThueBao.isVisible }" @click="sectionThongTinThueBao.isVisible = !sectionThongTinThueBao.isVisible"></span>Thông tin thuê bao</div>
        <div class="row" v-show="sectionThongTinThueBao.isVisible">
          <div class="col-sm-6 col-12">
            <div class=" ">
              <div class="row">
                <div class="col-sm-6 col-12">
                  <div class="info-row">
                    <div class="key w100">
                      Mã GD bán
                    </div>
                    <div class="value">
                      <input :disabled="!txtMaGD.enabled" v-model="txtMaGD.value" type="text" class="form-control bold" />
                    </div>
                  </div>
                </div>
                <div class="col-sm-6 col-12">
                  <div class="info-row">
                    <div class="key w100">
                      Mã GD thi công
                    </div>
                    <div class="value">
                      <input :disabled="!txtMaGDTC.enabled" v-model="txtMaGDTC.value" type="text" class="form-control bold" />
                    </div>
                  </div>
                </div>
                <div class="col-sm-6 col-12">
                  <div class="info-row">
                    <div class="key w100">
                      Mã KH
                    </div>
                    <div class="value">
                      <input :disabled="!txtMaKH.enabled" v-model="txtMaKH.value" type="text" class="form-control bold" />
                    </div>
                  </div>
                </div>
                <div class="col-sm-6 col-12">
                  <div class="info-row">
                    <div class="key w100">
                      NV Lập HĐ
                    </div>
                    <div class="value">
                      <input :disabled="!txtNVLapHD.enabled" v-model="txtNVLapHD.value" type="text" class="form-control bold" />
                    </div>
                  </div>
                </div>
                <div class="col-sm-12 col-12">
                  <div class="info-row">
                    <div class="key w100">
                      Tên KH
                    </div>
                    <div class="value">
                      <input :disabled="!txtTenKH.enabled" v-model="txtTenKH.value" type="text" class="form-control bold" />
                    </div>
                  </div>
                </div>
                <div class="col-sm-12 col-12">
                  <div class="info-row">
                    <div class="key w100">
                      Địa chỉ KH
                    </div>
                    <div class="value">
                      <input :disabled="!txtDiaChiKH.enabled" v-model="txtDiaChiKH.value" type="text" class="form-control bold" />
                    </div>
                  </div>
                </div>
                <div class="col-sm-6 col-12">
                  <div class="info-row">
                    <div class="key w100">
                      Người ĐD
                    </div>
                    <div class="value">
                      <input :disabled="!txtNguoiDD.enabled" v-model="txtNguoiDD.value" type="text" class="form-control bold" />
                    </div>
                  </div>
                </div>
                <div class="col-sm-6 col-12">
                  <div class="info-row">
                    <div class="key w100">
                      Loại KH
                    </div>
                    <div class="value">
                      <input :disabled="!txtLoaiKH.enabled" v-model="txtLoaiKH.value" type="text" class="form-control bold" />
                    </div>
                  </div>
                </div>
                <div class="col-sm-6 col-12">
                  <div class="info-row">
                    <div class="key w100">
                      SĐT KH
                    </div>
                    <div class="value">
                      <input :disabled="!txtTelKH.enabled" v-model="txtTelKH.value" type="number" class="form-control bold" />
                    </div>
                  </div>
                </div>
                <div class="col-sm-6 col-12">
                  <div class="info-row">
                    <div class="key w100">
                      Ngày lập HĐ
                    </div>
                    <div class="value">
                      <vue-date :disabled="!dtpNgayLapHD.enabled" v-model="dtpNgayLapHD.value" format="DD/MM/YYYY" type="datetime"></vue-date>
                    </div>
                  </div>
                </div>
                <div class="col-sm-6 col-12">
                  <div class="info-row">
                    <div class="key w100">
                      Ngày YC
                    </div>
                    <div class="value">
                      <vue-date :disabled="!dtpNgayYeuCau.enabled" v-model="dtpNgayYeuCau.value" format="DD/MM/YYYY" type="datetime"></vue-date>
                    </div>
                  </div>
                </div>
                <div class="col-sm-6 col-12">
                  <div class="info-row">
                    <div class="key w100">
                      Chức vụ
                    </div>
                    <div class="value">
                      <input :disabled="!txtChucVu.enabled" v-model="txtChucVu.value" type="text" class="form-control bold" />
                    </div>
                  </div>
                </div>
                <div class="col-sm-6 col-12">
                  <div class="info-row">
                    <div class="key w100">
                      MST KH
                    </div>
                    <div class="value">
                      <input :disabled="!txtMST_KH.enabled" v-model="txtMST_KH.value" type="text" class="form-control bold" />
                    </div>
                  </div>
                </div>
                <div class="col-sm-6 col-12">
                  <div class="info-row">
                    <div class="key w100">
                      Email KH
                    </div>
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
                    <div class="key w100">
                      Dịch vụ
                    </div>
                    <div class="value">
                      <SelectExt :disabled="!cboDichVuVT.enabled" class="" v-model="cboDichVuVT.value" :dataSource="cboDichVuVT.list" dataTextField="ten_dv" dataValueField="donvi_id" />
                    </div>
                  </div>
                </div>
                <div class="col-sm-6 col-12">
                  <div class="info-row">
                    <div class="key w100">
                      Loại hình
                    </div>
                    <div class="value">
                      <SelectExt :disabled="!cboLoaihinhTB.enabled" class="" v-model="cboLoaihinhTB.value" :dataSource="cboLoaihinhTB.list" dataTextField="ten_dv" dataValueField="donvi_id"></SelectExt>
                    </div>
                  </div>
                </div>
                <div class="col-sm-6 col-12">
                  <div class="info-row">
                    <div class="key w100">
                      Kiểu LĐ
                    </div>
                    <div class="value">
                      <input :disabled="!txtKieuLD.enabled" v-model="txtKieuLD.value" type="text" class="form-control bold" />
                    </div>
                  </div>
                </div>
                <div class="col-sm-6 col-12">
                  <div class="info-row">
                    <div class="key w100">
                      Mã TB thi công
                    </div>
                    <div class="value">
                      <input :disabled="!txtMaTC.enabled" v-model="txtMaTC.value" type="text" class="form-control bold" />
                    </div>
                  </div>
                </div>
                <div class="col-sm-6 col-12">
                  <div class="info-row">
                    <div class="key w100">
                      Tên TB
                    </div>
                    <div class="value">
                      <input :disabled="!txtTenTB.enabled" v-model="txtTenTB.value" type="text" class="form-control bold" />
                    </div>
                  </div>
                </div>
                <div class="col-sm-6 col-12">
                  <div class="info-row">
                    <div class="key w100">
                      Ngày sinh
                    </div>
                    <div class="value">
                      <vue-date :disabled="!txtNgaySNTB.enabled" v-model="txtNgaySNTB.value" format="DD/MM/YYYY" type="datetime"></vue-date>
                    </div>
                  </div>
                </div>
                <div class="col-sm-12 col-12">
                  <div class="info-row">
                    <div class="key w100">
                      Địa chỉ TB
                    </div>
                    <div class="value">
                      <input :disabled="!txtDiaChiTB.enabled" v-model="txtDiaChiTB.value" type="text" class="form-control bold" />
                    </div>
                  </div>
                </div>
                <div class="col-sm-12 col-12">
                  <div class="info-row">
                    <div class="key w100">
                      Địa chỉ LĐ
                    </div>
                    <div class="value">
                      <input :disabled="!txtDiaChiLD.enabled" v-model="txtDiaChiLD.value" type="text" class="form-control bold" />
                    </div>
                  </div>
                </div>
                <div class="col-sm-6 col-12">
                  <div class="info-row">
                    <div class="key w100">
                      Đối tượng
                    </div>
                    <div class="value">
                      <input :disabled="!txtDoituongTB.enabled" v-model="txtDoituongTB.value" type="text" class="form-control bold" />
                    </div>
                  </div>
                </div>
                <div class="col-sm-6 col-12">
                  <div class="info-row">
                    <div class="key w100">
                      Ngày hết hạn TD
                    </div>
                    <div class="value">
                      <vue-date :disabled="!txtNgayHHTD.enabled" v-model="txtNgayHHTD.value" format="DD/MM/YYYY" type="datetime"></vue-date>
                    </div>
                  </div>
                </div>
                <div class="col-sm-12 col-12">
                  <div class="info-row">
                    <div class="key w100">
                      Ghi chú
                    </div>
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
        <div v-show="sectionDanhSachThueBao.isVisible" class="row">
          <div class="col-sm-8 col-12">
            <DataGrid panelDataHeight="150" v-bind:columns="gridDanhSachThueBao.cols" :showColumnCheckbox="true" v-bind:dataSource="gridDanhSachThueBao.list" :showFilter="true" :allowPaging="true" :enablePagingServer="false" :totalRecords="gridDanhSachThueBao.list.length"> </DataGrid>
          </div>
          <div class="col-sm-4 col-12">
            <DanhSachThueBaoDetailDescription />
          </div>
        </div>
      </div>
      <div class="box-form">
        <div class="row">
          <div class="col-sm-8 col-12">
            <section style="margin-top: 15px;flex-grow: 1;background: transparent;">
              <ejs-tab ref="tabObj" id="tab_wizard" heightAdjustMode="None" height="auto" @selected="onTabSelected">
                <e-tabitems>
                  <e-tabitem v-for="(item, index) in tabList" :key="index" :header="item.header" :content="item.content"></e-tabitem>
                </e-tabitems>
              </ejs-tab>
              <div id="gridDanhSachNhanVienThucHien" style="display: none">
                <DataGrid
                  panelDataHeight="120"
                  v-bind:columns="gridDanhSachNhanVienThucHien.cols"
                  :showColumnCheckbox="true"
                  v-bind:dataSource="gridDanhSachNhanVienThucHien.list"
                  :showFilter="true"
                  :allowPaging="true"
                  :enablePagingServer="false"
                  :totalRecords="gridDanhSachNhanVienThucHien.list.length"
                >
                </DataGrid>
              </div>
              <div id="gridDanhSachDichVuGiaTang" style="display: none">
                <DataGrid v-bind:columns="gridDanhSachDichVuGiaTang.cols" :showColumnCheckbox="true" v-bind:dataSource="gridDanhSachDichVuGiaTang.list" :showFilter="true" :allowPaging="true" :enablePagingServer="false" :totalRecords="gridDanhSachDichVuGiaTang.list.length"> </DataGrid>
              </div>
              <div id="gridDanhSachThuocTinh" style="display: none">
                <DataGrid v-bind:columns="gridDanhSachThuocTinh.cols" :showColumnCheckbox="true" v-bind:dataSource="gridDanhSachThuocTinh.list" :showFilter="true" :allowPaging="true" :enablePagingServer="false" :totalRecords="gridDanhSachThuocTinh.list.length"> </DataGrid>
              </div>
              <div id="gridDanhSachNhanVienCungThucHien" style="display: none">
                <DataGrid v-bind:columns="gridDanhSachNhanVienCungThucHien.cols" :showColumnCheckbox="true" v-bind:dataSource="gridDanhSachNhanVienCungThucHien.list" :showFilter="true" :allowPaging="true" :enablePagingServer="false" :totalRecords="gridDanhSachNhanVienCungThucHien.list.length">
                </DataGrid>
              </div>
              <div id="gridTienTrinhThiCong" style="display: none">
                <DataGrid v-bind:columns="gridTienTrinhThiCong.cols" :showColumnCheckbox="true" v-bind:dataSource="gridTienTrinhThiCong.list" :showFilter="true" :allowPaging="true" :enablePagingServer="false" :totalRecords="gridTienTrinhThiCong.list.length"> </DataGrid>
              </div>
              <div id="gridDanhSachThietBi" style="display: none">
                <DataGrid v-bind:columns="gridDanhSachThietBi.cols" :showColumnCheckbox="true" v-bind:dataSource="gridDanhSachThietBi.list" :showFilter="true" :allowPaging="true" :enablePagingServer="false" :totalRecords="gridDanhSachThietBi.list.length"> </DataGrid>
              </div>
              <div id="gridThongTinNhanVienQuanLy" style="display: none">
                <DataGrid v-bind:columns="gridThongTinNhanVienQuanLy.cols" :showColumnCheckbox="true" v-bind:dataSource="gridThongTinNhanVienQuanLy.list" :showFilter="true" :allowPaging="true" :enablePagingServer="false" :totalRecords="gridThongTinNhanVienQuanLy.list.length"> </DataGrid>
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
                      <div class="key w80">
                        Người GV
                      </div>
                      <div class="value">
                        <div class="select-custom">
                          <SelectExt :disabled="!cboNguoiGV.enabled" class="" v-model="cboNguoiGV.value" :dataSource="cboNguoiGV.list" dataTextField="ten_dv" dataValueField="donvi_id" />
                        </div>
                      </div>
                    </div>
                  </div>
                  <div class="col-sm-6 col-12">
                    <div class="info-row">
                      <div class="key w80">
                        ND giao
                      </div>
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
                          <SelectExt :disabled="!puceNDTH.enabled" class="" v-model="puceNDTH.value" :dataSource="puceNDTH.list" dataTextField="ten_dv" dataValueField="donvi_id" />
                          <button class="btn">
                            <span class="-ap icon-more_horiz"></span>
                          </button>
                        </div>
                      </div>
                    </div>
                  </div>
                  <div class="col-sm-6 col-12">
                    <div class="info-row">
                      <div class="key w80">
                        ND trả
                      </div>
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
                      <div class="key w80" style="display: flex; justify-content: center; align-items: center;">
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
                      <div class="key w80" style="display: flex; justify-content: center; align-items: center;">
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
                        <textarea :disabled="!txtNoiDungTH.enabled" v-model="txtNoiDungTH.value" name="" class="form-control" style="height: 100px;resize: none;"></textarea>
                      </div>
                    </div>
                  </div>
                </div>
              </section>
            </div>
          </div>
        </div>
      </div>
    </div> -->
  </div>
</template>
<script>
import Vue from 'vue'
import api from './api'
import XLSX from 'xlsx'
import breadcrumb from '@/components/breadcrumb'
import { TabPlugin } from '@syncfusion/ej2-vue-navigations'
Vue.use(TabPlugin)

export default {
  components: {
    breadcrumb
  },
  name: 'frmHoanCongIMS',
  data: () => ({
    a: 'abc',
    customerSearchType: 'pbh',
    header: {
      title: 'title',
      list: [
        { name: '', link: { name: 'Ui.cards' } },
        { name: '', link: { name: 'Ui.cards' } },
        { name: '', link: { name: 'Ui.cards' } }
      ]
    },
    totalMenuShow: 12,
    menus: [
      { id: 'lay_tt', name: 'Lấy TT (F5)', icon: 'one-file-attach', visible: true, enabled: true },
      { id: 'cap_nhat', name: 'Cập nhật (F9)', icon: 'one-file-attach', visible: true, enabled: true },
      { id: 'giao_viec', name: 'Giao việc', icon: 'one-file-attach', visible: true, enabled: true },
      { id: 'giao_phieu', name: 'Giao phiếu', icon: 'one-file-attach', visible: true, enabled: true },
      { id: 'giao_phieu_ph', name: 'Giao phiếu P/H', icon: 'one-file-attach', visible: true, enabled: true },
      { id: 'giao_host_cu', name: 'Giao host cũ', icon: 'one-file-attach', visible: true, enabled: true },
      { id: 'khoi_phuc_so', name: 'Khôi phục số', icon: 'one-file-attach', visible: true, enabled: true },
      { id: 'kich_hoat', name: 'Kích hoạt', icon: 'one-file-attach', visible: true, enabled: true },
      { id: 'huy_kich_hoat', name: 'Hủy kích hoạt', icon: 'one-file-attach', visible: true, enabled: true },
      { id: 'dau_noi', name: 'Đấu nối', icon: 'one-file-attach', visible: true, enabled: true },
      { id: 'hoan_cong', name: 'Hoàn công', icon: 'one-file-attach', visible: true, enabled: true },
      { id: 'xuat_phieu', name: 'Xuất phiếu', icon: 'one-file-attach', visible: true, enabled: true },
      { id: 'chuyen_to', name: 'Chuyển tổ', icon: 'one-file-attach', visible: true, enabled: true },
      { id: 'tra_phieu', name: 'Trả phiếu', icon: 'one-file-attach', visible: true, enabled: true },
      { id: 'doi_cong', name: 'Đổi cổng', icon: 'one-file-attach', visible: true, enabled: true },
      { id: 'vat_tu', name: 'Vật tư', icon: 'one-file-attach', visible: true, enabled: true },
      { id: 'xuat_file', name: 'Xuất file', icon: 'one-file-attach', visible: true, enabled: true },
      { id: 'hen_ld', name: 'Hẹn LĐ', icon: 'one-file-attach', visible: true, enabled: true },
      { id: 'thong_so_kt', name: 'Thông số KT', icon: 'one-file-attach', visible: true, enabled: true },
      { id: 'gan_kv', name: 'Gán KV', icon: 'one-file-attach', visible: true, enabled: true },
      { id: 'du_an', name: 'Dự án', icon: 'one-file-attach', visible: true, enabled: true },
      { id: 'ton_phieu', name: 'Tồn phiếu', icon: 'one-file-attach', visible: true, enabled: true },
      { id: 'hoan_thien', name: 'Hoàn thiện', icon: 'one-file-attach', visible: true, enabled: true },
      { id: 'do_kiem', name: 'Đo kiểm', icon: 'one-file-attach', visible: true, enabled: true },
      { id: 'kich_hoat_ao', name: 'Kích hoạt ảo', icon: 'one-file-attach', visible: true, enabled: true },
      { id: 'xuat_file_db', name: 'Xuất file ĐB', icon: 'one-file-attach', visible: true, enabled: true },
      { id: 'lien_he', name: 'Liên hệ', icon: 'one-file-attach', visible: true, enabled: true },
      { id: 'sua_kenh_thu', name: 'Sửa kênh thu', icon: 'one-file-attach', visible: true, enabled: true }
    ],
    gridDanhSachNhanVien: {
      cols: [
        { fieldName: 'ma_tb', headerText: 'Tên nhân viên', allowFiltering: true },
        { fieldName: 'ma_kh', headerText: 'Số điện thoại', allowFiltering: true },
        { fieldName: 'ten_tb', headerText: 'Nhiệm vụ', allowFiltering: true },
        { fieldName: 'so_dt', headerText: 'Ghi chú', allowFiltering: true }
      ],
      list: [],
      checked: [],
      value: null
    },
    gridDanhSachDichVu: {
      cols: [
        { fieldName: 'srv', headerText: 'Mã DVGT', allowFiltering: true },
        { fieldName: 'ma_tb', headerText: 'Tên dịch vụ', allowFiltering: true },
        { fieldName: 'ma_kh', headerText: 'Kiểu YC', allowFiltering: true },
        { fieldName: 'ten_tb', headerText: 'Nội dung', allowFiltering: true }
      ],
      list: [],
      checked: [],
      value: null
    },
    gridDanhSachThueBao: {
      cols: [
        { fieldName: 'srv', headerText: 'Số máy', allowFiltering: true },
        { fieldName: 'ma_tb', headerText: 'Mã TB', allowFiltering: true },
        { fieldName: 'ma_kh', headerText: 'Số chính', allowFiltering: true },
        { fieldName: 'ten_tb', headerText: 'Tên thuê bao', allowFiltering: true },
        { fieldName: 'ten_tb', headerText: 'Địa chỉ lắp đặt', allowFiltering: true },
        { fieldName: 'ten_tb', headerText: 'Mã khu vực', allowFiltering: true },
        { fieldName: 'ten_tb', headerText: 'Tên khu vực', allowFiltering: true },
        { fieldName: 'ten_tb', headerText: 'Ngày YC', allowFiltering: true },
        { fieldName: 'ten_tb', headerText: 'Đơn vị hẹn', allowFiltering: true },
        { fieldName: 'ten_tb', headerText: 'Hẹn từ', allowFiltering: true },
        { fieldName: 'ten_tb', headerText: 'Hẹn đến', allowFiltering: true },
        { fieldName: 'ten_tb', headerText: 'Gói KT', allowFiltering: true },
        { fieldName: 'ten_tb', headerText: 'Giờ CT', allowFiltering: true },
        { fieldName: 'ten_tb', headerText: 'Giờ còn lại', allowFiltering: true },
        { fieldName: 'ten_tb', headerText: 'Mã GD', allowFiltering: true },
        { fieldName: 'ten_tb', headerText: 'Trạng thái in', allowFiltering: true },
        { fieldName: 'ten_tb', headerText: 'Ngày BG', allowFiltering: true },
        { fieldName: 'ten_tb', headerText: 'Ngày thực hiện', allowFiltering: true },
        { fieldName: 'ten_tb', headerText: 'Trạng thái', allowFiltering: true },
        { fieldName: 'ten_tb', headerText: 'Đơn vị giao', allowFiltering: true },
        { fieldName: 'ten_tb', headerText: 'Lý do tồn', allowFiltering: true },
        { fieldName: 'ten_tb', headerText: 'Ghi chú tồn', allowFiltering: true },
        { fieldName: 'ten_tb', headerText: 'Ghi chú', allowFiltering: true }
      ],
      checked: [],
      list: [],
      value: null
    },
    trangThaiPhieu: [
      {
        id: 1,
        text: 'Phiếu mới'
      },
      {
        id: 2,
        text: 'Phiếu trả lại'
      }
    ],
    txtMaGD: {
      enabled: true,
      value: null
    },
    txtMaGDTC: {
      enabled: true,
      value: null
    },
    txtMaKH: {
      enabled: true,
      value: null
    },
    txtNVLapHD: {
      enabled: true,
      value: null
    },
    txtTenKH: {
      enabled: true,
      value: null
    },
    txtDiaChiKH: {
      enabled: true,
      value: null
    },
    txtNguoiDD: {
      enabled: true,
      value: null
    },
    txtLoaiKH: {
      enabled: true,
      value: null
    },
    txtTelKH: {
      enabled: true,
      value: null
    },
    dtpNgayLapHD: {
      enabled: true,
      value: null
    },
    dtpNgayYeuCau: {
      enabled: true,
      value: null
    },
    txtChucVu: {
      enabled: true,
      value: null
    },
    txtMST_KH: {
      enabled: true,
      value: null
    },
    txtEmailKH: {
      enabled: true,
      value: null
    },
    cboDichVuVT: {
      enabled: true,
      list: [],
      value: null
    },
    cboLoaihinhTB: {
      enabled: true,
      list: [],
      value: null
    },
    txtKieuLD: {
      enabled: true,
      value: null
    },
    txtMaTC: {
      enabled: true,
      value: null
    },
    txtTenTB: {
      enabled: true,
      value: null
    },
    txtNgaySNTB: {
      enabled: true,
      value: null
    },
    txtDiaChiTB: {
      enabled: true,
      value: null
    },
    txtDiaChiLD: {
      enabled: true,
      value: null
    },
    txtDoituongTB: {
      enabled: true,
      value: null
    },
    txtNgayHHTD: {
      enabled: true,
      value: null
    },
    txtGhiChuTB: {
      enabled: true,
      value: null
    },
    cboNguoiGV: {
      enabled: true,
      list: [],
      value: null
    },
    txtNDGiao: {
      enabled: true,
      value: null
    },
    puceNDTH: {
      enabled: true,
      list: [],
      value: null
    },
    txtThongTinTra: {
      enabled: true,
      value: null
    },
    txtLyDoTra: {
      enabled: true,
      value: null
    },
    chkNgayGV: {
      enabled: true,
      value: null
    },
    dtpNgayGV: {
      enabled: false,
      value: null
    },
    txtNhanVien: {
      enabled: true,
      value: null
    },
    chkNgayBG: {
      enabled: true,
      value: null
    },
    dtpNgayBG: {
      enabled: true,
      value: null
    },
    txtNoiDungTH: {
      enabled: true,
      value: null
    }
  }),
  computed: {},
  async mounted() {
    // this.initData()
  },
  watch: {
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
    }
  },
  methods: {
    onMenuClickedListener(action) {
      console.log(action)
      switch (action.id) {
        case 'lay_tt':
          this.LayPhieu()
          break
        case 'giao_phieu':
          break
        case 'tra_phieu':
          break
        case 'giao_viec':
          this.GiaoViec()
          break
        case 'hoan_thanh':
          break
        case 'gui_it360':
          this.tsbtnCreatIssue_Click()
          break
        case 'xem_it360':
          this.tsbtnXemIT360_Click()
          break
        case 'tra_phieu_tinh':
          this.tsbtnTraPhieuTinh_Click()
          break
        case 'chuyen_vip':
          this.tsbtnChuyenVip_Click()
          break
        case 'xem_ho_so':
          this.tsbtnXemHS_Click()
          break
        case 'tra_cuu_phieu_tc':
          this.tsbtnTC_CTTC_Click()
          break
        case 'upload_file':
          this.btnUploadFileOneBussiness_Click()
          break
        case 'import_file_idc':
          this.tsbtnImportIDC_Click()
          break
        case 'xem_tbi':
          this.tsbXemTbi_Click()
          break
        case 'vat_tu':
          break
        case 'hen_ld':
          break
        case 'gan_kv': //tsbtnNVKT_Click
          // KO CÓ GÌ
          break

        case 'thong_so_kt':
          // KO CÓ GÌ
          break

        case 'xuat_file':
          // KO CÓ GÌ
          break
        case 'hoan_thien':
          // KO CÓ GÌ
          break
        case 'ton_phieu':
          // KO CÓ GÌ
          break
        case 'chuyen_to':
          // KO CÓ GÌ
          break
        case 'du_an':
          // KO CÓ GÌ
          break
        case 'do_kiem':
          // KO CÓ GÌ
          break
      }
      // this.menus.find((e) => e.id == 'lay_tt').enabled = false
    },
    onTabSelected(e) {
      console.log(e)
    },
    async LayPhieu() {
      try {
        let data = await api.faker_lay_phieu_vip(this.axios, {})
        if (data.data.data != null && data.data.data.length > 0) {
          gridDanhSachThueBao.list = data.data.data
        } else {
          gridDanhSachThueBao.list = []
        }
      } catch (e) {
        this.$toast.error(`Có lỗi trong quá trình lấy phiếu: ${e}`)
      }
    },
    async GiaoViec() {
      // if (this.gridDanhSachThueBao.list.length <= 0) {
      //   this.$toast.warning('Chưa có danh phiếu. Bạn hãy kiểm tra lại! ')
      //   return
      // }
      // if (this.cboNguoiGV.list.length <= 0 || cboNguoiGV.value == '') {
      //   this.$toast.warning('Hãy nhập nhân viên giao việc!')
      //   return
      // }
      // if (!this.chkNgayGV.Checked) {
      //   this.$toast.warning('Hãy nhập ngày giao việc!')
      //   return
      // }
      // if (CURRENT_LOAITB_ID != LoaiHinhTB.INTERNET_ADSL_TINH_KHAC && CURRENT_LOAITB_ID != LoaiHinhTB.INTERNET_FIBER_TINH_KHAC)
      // {
      // if (this.chkNgayBG.value)
      // {
      //     System.TimeSpan diffResult = dtpNgayBG.Value.Subtract(dtpNgayGV.Value);
      //     if (diffResult.Seconds < 0)
      //     {
      //         this.$toast.warning("Ngày giao việc không được lớn hơn ngày hoàn thành!");
      //         return;
      //     }
      // }
      // }
      let vnhanvien_giao_id = 0
      let vngaygiao
      vnhanvien_giao_id = this.cboNguoiGV.value
      vngaygiao = this.dtpNgayGV.value
      // frnGiaoPhieuNV gp = new frnGiaoPhieuNV(CURRENT_PHIEU_ID, tt_nd.donvi_id, vnhanvien_giao_id, vngaygiao);
      // gp.vma_tb = CURRENT_MA_TB;
      // gp.vhdtb_id = CURRENT_HDTB_ID;
      // gp.huonggiao_id = 7777;
      // gp.ShowDialog();

      this.$refs.frnGiaoPhieuNV.frnGiaoPhieuNV(this.phieu_id, this.$root.token.getDonViID(), vnhanvien_giao_id, vngaygiao)
      // this.$refs.frnGiaoPhieuNV.vma_tb = this.$refs.txtMaTB.value;
      // this.$refs.frnGiaoPhieuNV.vhdtb_id = this.hdtb_id;
      // this.$refs.frnGiaoPhieuNV.huonggiao_id = this.huonggiao_id;
      // this.$nextTick(() => {
      this.$refs.frnGiaoPhieuNV.load()
      this.$refs.popupfrnGiaoPhieuNV.show()
      // });

      //Hiển thị danh sách nhân viên
      // let data = new TCHopDongFacade().LAY_DS_NHANVIEN_THEO_PHIEU_ID(CURRENT_PHIEU_ID, tt_nd.nhanvien_id, 2);
      let data = await api.faker_lay_ds_nhanvien_theo_phieu_id(this.axios, {})
      if (data.data.data && data.data.data.length > 0) {
      } else {
      }
      // HienThiDSNV(ds);
    },
    async tsbtnHoanThanh_Click() {
      try {
        // về tỉnh lấy loại hình thuê bao, dịch vụ Mega Vmeeting bị null cboLoaiTB_ID
        let loaitb_id = await api.faker_fn_get_loaihinhtb_cdn(this.axios, { vhdtb_id: this.hdtb_id })
        if (this.tinh_ph == 100) {
          if (loaitb_id == LoaiHinhTB.BaoHieuQuocTe || loaitb_id == LoaiHinhTB.THOAIQT) {
            let data = await api.faker_giaophieu(this.axios, { vphieu_id: this.gridDanhSachThueBao.value.phieu_id })
            if (data[0].nd_thuchien == '') {
              this.$toast.error('Chưa nhập nội dung thực hiện')
              return
            }
          }
        }
        if (!confirm(`Bạn có chắc muốn hoàn thành phiếu này không?`)) {
          return
        }
        let kt_file_nghiemthu = await api.faker_kiemtra_bienbannghiemthu_vip_idc_media(this.axios, { vhdtb_id: this.hdtb_id })
        if (kt_file_nghiemthu != 'ok') {
          this.$toast.error(kt_file_nghiemthu)
          return
        }
        if (this.cboLoaihinhTB.value != null && this.cboLoaihinhTB.value == 292) {
          if (
            (await api.faker_fn_kiemtra_kieuld_hoso_onebusiness(this.axios, {
              vkieuld: this.txtKieuLD.value,
              vhdtb_id: CURRENT_HDTB_ID
            })) == 'OK'
          ) {
            if ((await api.faker_fn_kiemtra_file_hoso_onebusiness(this.axios, { vma_tb: this.ma_tb })) == 'NOK') {
              this.$toast.error('Bạn cần phải upload file hồ sơ nghiệm thu')
              return
            }
          }

          let kq = await api.faker_kiemtra_ngay_ketthuc_hopdong(this.axios, { vhdtb_id: CURRENT_HDTB_ID, vma_tb: this.ma_tb })
          if (kq == 1) {
            this.$toast.error('Hợp đồng cũng chưa hết hiệu lực, bạn không thể gia hạn. Hãy liên hệ admin để xử lý')
            return
          }
        }
        if (loaitb_id == LoaiHinhTB.CDN) {
          // for (let index = 0; index < this.gridEditors_TC.Count; ++index)
          // {
          //     ItemList_TC[index].sValue = this.gridEditors_TC[index].Value.ToString().Trim();
          // }
          // string sql = Tao_SQL_VIP_Update_CNTT(hdtb_id, ItemList_TC, false);
          // if (sql == "")
          // {
          //     this.$toast.error("IP, Tên miền, Alias, User name không được để trống");
          //     return;
          // }
        }

        if (this.cboNguoiGV.value == 0) {
          this.$toast.error('Chọn người giao việc')
          return
        }
        if (this.tinh_ph == 100) {
          let data = await api.faker_vip_giaophieu_hoanthien(this.axios, {
            vphieu_id: CURRENT_PHIEU_ID,
            vnhanvien_hc_id: tt_nd.nhanvien_id,
            vngay_ht: dtpNgayBG.Value,
            vhdtb_id: CURRENT_HDTB_ID,
            vnhanvien_gv_id: this.cboNguoiGV.value
          })
        } else {
          let dt_temp = await api.faker_giaophieu_ph(this.axios, { phieu_id: gvDanhSach.value.phieu_id })
          if (dt_temp[0].nd_thuchien == '') {
            this.$toast.error('Chưa nhập nội dung thực hiện')
            return
          }
          await api.faker_vip_hoanthien_giao_ph_bancheo(this.axios, {
            vtinh: 100,
            vphieu_id: this.gvDanhSach.value.phieu_id,
            vngay_cn: tt_nd.ngay_cn,
            vnguoi_cn: tt_nd.ma_nd,
            vmay_cn: tt_nd.may_cn,
            vip_cn: tt_nd.ip
          })
          await api.faker_vip_hoanthien_giao_ph_bancheo(this.axios, {
            vtinh: tinh_ph,
            vphieu_id: this.gvDanhSach.value.phieu_tinh_id,
            vngay_cn: tt_nd.ngay_cn,
            vnguoi_cn: tt_nd.ma_nd,
            vmay_cn: tt_nd.may_cn,
            vip_cn: tt_nd.ip,
            vxuly: this.gvDanhSach.value.xuly,
            vkq: 1,
            vnoidung: dt_temp[0].nd_thuchien
          })
          if (loaitb_id == LoaiHinhTB.Voice_Brandname) {
            let dt = await api.faker_lay_tt_giao_vip_net_khdn(this.axios, { vhdtb_id: this.hdtb_id })
            await api.faker_tinh_insert_vip_net(this.axios, 'vjson_khdn', dt)
          }
        }

        // if (dt_vip_update.length > 0) {
        //   grvMacdinhTC.CloseEditor();
        //   if (CURRENT_LOAITB_ID == LoaiHinhTB.TTDL_HDDT)
        //   {
        //       for (let index = 0; index < this.gridEditors_TC.Count; ++index)
        //       {
        //           if (this.gridEditors_TC[index].Value != null)
        //               ItemList_TC[index].sValue = this.gridEditors_TC[index].Value
        //           else
        //               ItemList_TC[index].sValue = "";
        //       }
        //   }
        //   else
        //   {
        //       for (let index = 0; index < this.gridEditors_TC.Count; ++index)
        //       {
        //           if (ItemList_TC[index].iAtt == 2)
        //           {
        //               if (this.gridEditors_TC[index].Value == null)
        //               {
        //                   this.$toast.warning("Bạn hãy kiểm tra lại dữ liệu " + ItemList_TC[index].sCaption);
        //                   return;
        //               }
        //           }
        //           ItemList_TC[index].sValue = this.gridEditors_TC[index].Value
        //       }
        //   }
        //   string sql = Tao_SQL_VIP_Update_CNTT(hdtb_id, ItemList_TC, false);
        //   bancheo.EXECUTE_SQL(tinh, sql);
        // }
        // this.HoSoDaXem.Clear();
        // this.$toast.success("Cập nhật thành công");
        // LayPhieu();
      } catch (e) {
        this.$toast.error(`Có lỗi : ${e}`)
      }
    },
    // async HienThiDSNV(ds)
    //     {
    //         let myDataTables = ds[0];
    //         if (myDataTables.length > 0)
    //         {
    //             chkNgayGV.Checked = true;
    //             this.dtpNgayGV.value = ds[0].ngaygiao
    //             this.cboNguoiGV.value = ds[0].nhanvien_giao_id
    //             this.gridGiaoviec.DataSource = ds[0];
    //         }
    //         else
    //         {
    //             // this.gridGiaoviec.DataSource = null
    //             this.chkNgayGV.value = true;
    //             this.dtpNgayGV.value = tt_nd.ngay_cn;
    //             this.cboNguoiGV.value = tt_nd.nhanvien_id;

    //         }
    //     },
    tsbtnCreatIssue_Click() {
      let vkieu = 0
      let vbaohong_id = 0
      let vphieu_baohong_id = 0
      // phieu_baohong_id = CURRENT_PHIEU_ID
      // f.tinh_id = tinh_ph;
      // f.loai = 1;
      if (this.tinh_ph != 100) vkieu = 3
      let data = {
        kieu: vkieu,
        baohong_id: vbaohong_id,
        phieu_baohong_id: vphieu_baohong_id
      }

      this.$refs.frmCreateIssue.openDialog(data)
    },
    async tsbtnXemIT360_Click() {
      // Tinh_TC tinh = new BanCheoBase().tinh_thicong(tinh_ph);
      // frmThongTinYeuCauVIP f = new frmThongTinYeuCauVIP(tinh, hdtb_id, 0);
      // f.ShowDialog();
    },
    async tsbtnTraPhieuTinh_Click() {
      // frmTraPhieuVIP frm = new frmTraPhieuVIP();
      // frm.tinh = new BanCheoBase().tinh_thicong(tinh_ph);
      // frm.phieu_id = long.Parse(gvDanhSach.GetDataRow(gvDanhSach.FocusedRowHandle)["PHIEU_ID"].ToString());
      // frm.phieu_tinh_id = long.Parse(gvDanhSach.GetDataRow(gvDanhSach.FocusedRowHandle)["PHIEU_TINH_ID"].ToString());
      // frm.hdtb_id = hdtb_id;
      // frm.vloaitb_id = CURRENT_LOAITB_ID;
      // frm.vma_tb = ma_tb;
      // frm.vso_dt_giao = gvDanhSach.GetDataRow(gvDanhSach.FocusedRowHandle)["SO_DT_GIAO"].ToString();
      // frm.ShowDialog();
      // tsbtnLayTT.PerformClick();
    },
    async tsbtnChuyenVip_Click() {
      try {
        if (this.cboVip.value == vdonvinhan_id) {
          this.$toast.warning('Đơn vị Vip được chuyển phải khác Vip hiện tại !')
          return
        }
        let cmt = ''
        let noidung = 'Nhập nội dung chuyển Vip'
        // let frm = new frmNhapText(noidung);
        // frm.btnXacNhan.Text = "Cập nhật";
        // frm.btnHuyBo.Text = "Hủy";
        // frm.btnHuyThat.Visible = false;
        // let tb = frm.ShowDialog();

        // if (tb == DialogResult.OK) // nếu là lý do khác
        //     cmt = frm.Output;
        // else if (tb == DialogResult.Cancel)// BỊ hỏng
        //     return;
        // else if (tb == DialogResult.Abort)
        //     return;
        // else
        //     return;

        let vkieu = 0
        if (tinh_ph == 100) vkieu = 1
        else vkieu = 2
        await api.faker_chuyen_vip_giamsat(this.axios, {
          vkieu: vkieu,
          vhdtb_id: hdtb_id,
          vphieu_id: this.gvDanhSach.value.phieu_id,
          vdonvi_nhan_id: Convert.ToInt64(cboVip.SelectedValue),
          vnhanvien_id: tt_nd.nhanvien_id,
          vnoidung: cmt,
          vmay_cn: tt_nd.may_cn,
          vnguoi_cn: tt_nd.ma_nd
        })
        this.$toast.success('Chuyển Vip thành công !')
        LayPhieu()
      } catch (e) {
        this.$toast.error(`Có lỗi : ${e}`)
      }
    },
    async tsbtnXemHS_Click() {
      if (this.gvDanhSach.list.length == 0) {
        this.$toast.success('Không tìm thấy phiếu trên lưới.')
        return
      }
      // if (this.tinh_ph == 100)
      // {
      //     let loaihinh = new BanCheoBase().GET_STRING_FUNCTION(new BanCheoBase().tinh_thicong(tinh_ph), "{?DB2}.HIENTHI_HOSO_KHDN", "vhdtb_id", hdtb_id).ToString();
      //     if (loaihinh == "ok")
      //     {
      //         frmTraCuuHoSoHDTB f = new frmTraCuuHoSoHDTB();
      //         f.vhdtb_id = hdtb_id;
      //         f.ShowDialog();
      //     }
      //     else
      //     {
      //         frmDSHoSoBC frm = new frmDSHoSoBC();
      //         frm.hdtb_ban = hdtb_id;
      //         frm.hdtb_tc = 0;
      //         frm.tinh_id = tinh_ph;
      //         frm.ShowDialog();
      //     }
      // }
      // else
      // {
      //     frmDSHoSoBC frm = new frmDSHoSoBC();

      //     frm.hdtb_ban = 0;
      //     frm.hdtb_tc = hdtb_id;
      //     frm.tinh_id = tinh_ph;
      //     frm.ShowDialog();
      //     //Đối với CA
      //     //if (Convert.ToInt32(cboLoaihinhTB.SelectedValue) == 116)
      //     //{
      //     //    if (this.HoSoDaXem.Count == 0)
      //     //    {
      //     //        this.HoSoDaXem.Clear();
      //     //        foreach (KeyValuePair<string, bool> item in frm.HoSoDaXem)
      //     //        {
      //     //            this.HoSoDaXem.Add(item.Key, item.Value);
      //     //        }
      //     //    }
      //     //    else
      //     //    {
      //     //        for (int i = 0; i < this.HoSoDaXem.Count; i++)
      //     //        {
      //     //            if (this.HoSoDaXem.Values.ElementAt(i) == false)
      //     //            {
      //     //                string dic_key = this.HoSoDaXem.Keys.ElementAt(i);
      //     //                this.HoSoDaXem[dic_key] = frm.HoSoDaXem[dic_key];
      //     //            }
      //     //        }
      //     //    }
      //     //}
      // }
    },
    async tsbtnTC_CTTC_Click() {
      // frmTraCuuPhieuTCBC frm = new frmTraCuuPhieuTCBC();
      // frm.vma_tb = txtMaGD.Text;
      // frm.ShowDialog();
    },
    async btnUploadFileOneBussiness_Click() {
      try {
        if (openFileDialog1.ShowDialog() == DialogResult.OK) {
          if (openFileDialog1.FileName != '') {
            let url = UPLOAD_FILE()
            if (url == '') {
              this.$toast.success('Upload file thất bại.')
              return
            }
            let file_name = openFileDialog1.SafeFileName
            let vhdtb_id = hdtb_id
            let vma_kh = this.txtMaKH.valuÒe
            let vma_gd = this.txtMaGD.value
            await api.faker_capnhat_bbnt_dv_onebusiness(this.axios, {
              vurl: url,
              vmay_cn: tt_nd.may_cn,
              vnguoi_cn: tt_nd.ma_nd,
              vip_cn: tt_nd.ip,
              vfile_name: file_name,
              vhdtb_id: vhdtb_id,
              vma_kh: vma_kh,
              vma_gd: vma_gd,
              vma_tb: ma_tb
            })
            this.$toast.success(`Cập nhật thành công file : ${file_name}!`)
          }
        } else return
      } catch (e) {
        this.$toast.warning(`${e}`)
      }
    },
    async tsbtnImportIDC_Click() {
      // frmImportThietBi_IDC frm = new frmImportThietBi_IDC();
      // frm.vhdtb_id = hdtb_id;
      // frm.loaitb_id = CURRENT_LOAITB_ID;
      // frm.ma_gd = txtMaGD.Text;
      // frm.ma_tb = ma_tb;
      // frm.tinh_tc = new BanCheoBase().tinh_thicong(tinh_ph);
      // frm.ShowDialog();
    },
    async tsbXemTbi_Click() {
      // if (Convert.ToInt32(gvDanhSach.getCurrentRowCellValue("LOAITB_ID")) == LoaiHinhTB.VNPT_COLOCATION)
      // {
      //     frmDanhSachThietBI_IDC f = new frmDanhSachThietBI_IDC();
      //     f.hdtb_id = hdtb_id;
      //     f.thuebao_id = 0;
      //     f.tinh_id = tinh_ph;
      //     f.ShowDialog();
      // }
      // if (cboLoaihinhTB.SelectedValue.ToString() == LoaiHinhTB.Voice_Brandname.ToString())
      // {
      //     WinUILapHopDong.PopupVBN.frmDanhSachSoThanhVien f = new WinUILapHopDong.PopupVBN.frmDanhSachSoThanhVien();
      //     f.hdtb_id = hdtb_id;
      //     f.thuebao_id = 0;
      //     f.tinh_id = tinh_ph;
      //     f.ShowDialog();
      // }
    },
    giaoviec_success() {
      console.log('giaoviec_success')
    }
  }
}
</script>
<style scoped>
* {
  font-size: 12px;
  // padding: 0;
  // margin: 0;
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
</style>
