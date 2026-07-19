<template>
  <div class="main-wrapper" @click="hideDropdownMenu">
    <vue-bread-crumb :header="header" />
    <vue-nav>
      <ul class="list">
        <li @click="clickButton('laytt')" id="btnLayTTMoi" style=" float: left" :class="this.button.laytt ? 'active' : 'non-active'">
          <a> <span class="icon one-file-attach"></span>Lấy TT</a>
        </li>

        <li @click="clickButton('capnhat')" id="tsbtnChapNhan" style=" float: left" :class="this.button.capnhat ? 'active' : 'non-active'">
          <a> <span class="icon one-reload1"></span>Cập nhật</a>
        </li>


        <li @click="clickButton('giaoviec')" id="tsbtnGiaoViec" style=" float: left" :class="this.button.giaoviec ? 'active' : 'non-active'">
          <a> <span class="icon one-file-edit2"></span>Giao việc</a>
        </li>


        <li @click="clickButton('giaophieu')" id="tsbtnGiaoPhieu" style=" float: left" :class="this.button.giaophieu ? 'active' : 'non-active'">
          <a> <span class="icon one-file-arrow-right1"></span>Giao phiếu</a>
        </li>

        <li @click="clickButton('kichhoat')" id="tsbtnKichHoat" style=" float: left" :class="this.button.kichhoat ? 'active' : 'non-active'">
          <a> <span class="icon one-file-arrow-up"></span>Kích hoạt</a>
        </li>

        <li @click="clickButton('daunoi')" id="tsbtnDauNoiCap" style=" float: left" :class="this.button.daunoi ? 'active' : 'non-active'">
          <a> <span class="icon one-connect"></span>Đầu nối</a>
        </li>

        <li @click="clickButton('hoancong')" id="tsbtnHoanCong" style=" float: left" :class="this.button.hoancong ? 'active' : 'non-active'">
          <a> <span class="icon one-hoancong"></span>Hoàn công</a>
        </li>


        <li @click="clickButton('traphieu')" id="tsbtnTraPhieu" style=" float: left" :class="this.button.traphieu ? 'active' : 'non-active'">
          <a> <span class="icon one-file-back"></span>Trả phiếu</a>
        </li>

        <li @click="clickButton('phieutc')" id="btnPhieuTC" style=" float: left" :class="this.button.phieutc ? 'active' : 'non-active'">
          <a> <span class="icon one-file-back"></span>Phiếu thi công</a>
        </li>

        <li @click="clickButton('inphieu')" id="btnBienBanNT" style=" float: left" :class="this.button.inphieu ? 'active' : 'non-active'">
          <a> <span class="icon one-file-back"></span>In phiếu</a>
        </li>

        <li @click="clickButton('vattu')" id="tsbtnVatTu" style=" float: left" :class="this.button.vattu ? 'active' : 'non-active'">
          <a> <span class="icon one-vt"></span>Vật tư</a>
        </li>

        <li @click="clickButton('vattumoi')" id="tsbtnVatTuMoi" style=" float: left" :class="this.button.vattumoi ? 'active' : 'non-active'">
          <a> <span class="icon one-vt"></span>Vật tư</a>
        </li>

        <li @click="clickButton('hoanthienhs')" id="tsbtnHoanThienHS" style=" float: left" :class="this.button.hoanthienhs ? 'active' : 'non-active'">
          <a> <span class="icon one-vt"></span>Hoàn thiện</a>
        </li>
      </ul>

      <div class="more dropdown">
        <a href="#" data-toggle="dropdown" @click="setDisplayDropDownMenu">
          <span ref="dropdownRefDots" class="icon -ap  icon-dots-three-vertical"></span>
        </a>
        <div :class="isDisplayMenu ? 'dropdown-menu ovauto d-block' : 'dropdown-menu ovauto' " style="max-height: 300px;">
          <a @click="clickButton('henld')" id="tsbtnHen" :class="this.button.henld ? '' : 'disabled'">
            <span class="dropdown-icon one-henld"></span> Hẹn LĐ
          </a>
          <a @click="clickButton('nvkt')" id="tsbtnNVKT" :class="this.button.nvkt ? '' : 'disabled'">
            <span class="dropdown-icon one-users"></span> NVKT
          </a>
          <a @click="clickButton('tonphieu')" id="tsbtnGiaoPhieu_Ton" :class="this.button.tonphieu ? '' : 'disabled'">
            <span class="dropdown-icon one-calendar-number"></span> Tồn phiếu
          </a>
          <a @click="clickButton('duan')" id="tsbtnDuAn" :class="this.button.duan ? '' : 'disabled'">
            <span class="dropdown-icon -ap  icon-books"></span> Dự án
          </a>
          <a @click="clickButton('chuyento')" id="tsbtnChuyenTo" :class="this.button.chuyento ? '' : 'disabled'">
            <span class="dropdown-icon one-change"></span> Chuyển tổ
          </a>
        </div>
      </div>

    </vue-nav>
    <div class="page-content">
      <div class="box-form padt8 padb8">
        <div class="row">
          <div class="col-sm-2 col-12">

          </div>
          <div class="col-sm-10 col-12">
            <div class="row">

              <div class="col-sm-3 col-12">
                <div class="info-row marb0">
                  <div class="key w70">NV tiếp thị</div>
                  <div class="value">
                    <input type="text" id="txtNV_TiepThi" v-model="txtNVTiepThi" class="form-control">
                  </div>
                </div>
              </div>
              <div class="col-sm-3 col-12">
                <div class="info-row marb0">
                  <div class="key w70">Kiểu HĐ</div>
                  <div class="value">
                    <div class="select-custom">
                      <SelectExt id="cboKieu_HD" v-model="ddlKieuHD" :options="this.options.kieuHD" :disabled="true">
                        <option disabled value="0">Select one</option>
                      </SelectExt>
                    </div>
                  </div>
                </div>
              </div>
              <div class="col-sm-3 col-12">
                <div class="info-row marb0">
                  <div class="key w70">Kênh thu</div>
                  <div class="value">
                    <div class="select-custom">
                      <SelectExt id="cboKenhThu" v-model="ddlKenhThu" :options="this.options.kenhThu" :disabled="true">
                        <option disabled value="0">Select one</option>
                      </SelectExt>
                    </div>
                  </div>
                </div>
              </div>
              <div class="col-sm-3 col-12">
                <div class="info-row marb0">
                  <div class="key w70">Tổng tiền</div>
                  <div class="value">
                    <input type="text" id="txtTongTien" v-model="txtTongTien" class="form-control tright">
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
      <div class="box-form">
        <div class="legend-title">
          <div class="pull-left">Thông tin khách hàng</div>
          <div class="pull-right text-main" style="display:none">
            <div class="check-action normal">
              <input type="checkbox" id="chkHen" v-model="ckbLDTheoYC" class="check">
              <span class="name">Lắp đặt theo yêu cầu</span>
            </div>
          </div>
          <div class="clearfix"></div>
        </div>
        <div class="row">
          <div class="col-sm-6 col-12">
            <div class="row">
              <div class="col-sm-6 col-12">
                <div class="info-row">
                  <div class="key w90">Mã giao dịch</div>
                  <div class="value">
                    <input type="text" id="txtMaGD" v-on:keyup="txtMaGD_KeyPress" v-model="txtMaGD" class="form-control highlight">
                  </div>
                </div>
              </div>
              <div class="col-sm-6 col-12">
                <div class="info-row">
                  <div class="key w90">Loại HĐ/PL</div>
                  <div class="value">
                    <div class="select-custom">
                      <SelectExt id="cboLoaiHD" v-model="ddlLoaiHD" :options="this.options.loaiHD">
                        <option disabled value="0">Select one</option>
                      </SelectExt>
                    </div>
                  </div>
                </div>
              </div>
            </div>
            <div class="info-row">
              <div class="key w90">Quy trình</div>
              <div class="value">
                <div class="select-custom">
                  <!--<SelectExt id="cboQuyTrinh" v-model="ddlQuyTrinh" :options="this.options.QuyTrinh" @change="onChange_ddlQuyTrinh($event)">-->
                  <SelectExt id="cboQuyTrinh" v-model="ddlQuyTrinh" :options="this.options.QuyTrinh">
                    <option disabled value="0">Select one</option>
                  </SelectExt>
                </div>
              </div>
            </div>
            <div class="info-row">
              <div class="key w90">Nhân viên YC</div>
              <div class="value">
                <input type="text" id="txtNhanVien" v-model="txtNVYC" class="form-control">
              </div>
            </div>
          </div>
          <div class="col-sm-6 col-12">
            <div class="row">
              <div class="col-sm-8 col-12">
                <div class="info-row">
                  <div class="key w100">Tên thuê bao</div>
                  <div class="value">
                    <input type="text" id="txtTenTB" v-model="txtTenTB" class="form-control">
                  </div>
                </div>
              </div>

              <div class="col-sm-4 col-12">
                <div class="info-row">
                  <div class="key w70">Khu vực</div>
                  <div class="value">
                    <input type="text" id="txtKhuVuc" v-model="txtKhuVuc" class="form-control">
                  </div>
                </div>
              </div>

              <!--<div class="col-sm-4 col-12">
              <div class="info-row">
                <div class="key w70">Khu vực</div>
                <div class="value">
                  <div class="select-custom">
                    <select name="" id="" class="form-control">
                      <option value=""></option>
                    </select>
                  </div>
                </div>
              </div>
            </div>-->
            </div>
            <div class="info-row">
              <div class="key w100">Địa chỉ LĐ cũ</div>
              <div class="value">
                <input type="text" id="txtDiachiCu" v-model="txtDiaChiLDCu" class="form-control">
              </div>
            </div>

            <div class="info-row">
              <div class="key w100">Địa chỉ LĐ mới</div>
              <div class="value">
                <input type="text" id="txtDiaChiLD" v-model="txtDiaChiLDMoi" class="form-control">
              </div>
            </div>

          </div>
        </div>
      </div>
      <div class="box-form">
        <div class="legend-title">
          Thông tin hoàn công
        </div>
        <div class="row">
          <div class="col-sm-6 col-12">
            <div class="row">
              <div class="col-sm-6 col-12">
                <div class="info-row">
                  <div class="key w90">Ngày GV</div>
                  <div class="value w32 center padt7">
                    <div class="check-action">
                      <input type="checkbox" id="chkNgayGV" v-model="ckbNgayGV" class="check" :disabled="this.disCkbNgayGV">
                      <span class="name"></span>
                    </div>
                  </div>
                  <div class="value">
                    <div class="input-icon-right">
                      <flat-pickr class="form-control" id="dtpNgayGV" v-model="dtpNgayGV" :disabled="!this.ckbNgayGV" :readonly="!this.ckbNgayGV" :config="searchDate.dateconfig" />
                      <!--<input type="text" class="form-control">-->
                      <span class="icon one-calendar"></span>
                    </div>
                  </div>
                </div>
              </div>
              <div class="col-sm-6 col-12">
                <div class="info-row">
                  <div class="key w100">Người giao việc</div>
                  <div class="value">
                    <div class="select-custom">
                      <SelectExt id="cboNguoiGV" v-model="ddlNguoiGiaoViec" :options="this.options.nguoigiaoviec">
                        <option disabled value="0">Select one</option>
                      </SelectExt>
                    </div>
                  </div>
                </div>
              </div>
            </div>
            <div class="row">
              <div class="col-sm-6 col-12">
                <div class="info-row">
                  <div class="key w90">Ngày HT</div>
                  <div class="value w32 center padt7">
                    <div class="check-action">
                      <input type="checkbox" id="chkNgayBG" v-model="ckbNgayHT" class="check" :disabled="this.disCkbNgayHT">
                      <span class="name"></span>
                    </div>
                  </div>
                  <div class="value">
                    <div class="input-icon-right">
                      <flat-pickr class="form-control" id="dtpNgayBG" v-model="dtpNgayHT" :disabled="!this.ckbNgayHT" :readonly="!this.ckbNgayHT" :config="searchDate.dateconfig" />
                      <!--<input type="text" class="form-control">-->
                      <span class="icon one-calendar"></span>
                    </div>
                  </div>
                </div>
              </div>
              <div class="col-sm-6 col-12">
                <div class="info-row">
                  <div class="key w100">Nội dung giao</div>
                  <div class="value">
                    <input type="text" v-model="txtNoiDungGiao" class="form-control">
                  </div>
                </div>
              </div>
            </div>
            <div class="info-row">
              <div class="key w90">Nội dung TH</div>
              <div class="value">
                <div class="input-more-button">
                  <button class="btn" @click="clickButton('noidungth')">
                    <span class="nc-icon-glyph ui-1_edit-74 f16"></span>
                  </button>
                  <input type="text" id="txtNoiDungTH" v-model="txtNoiDungTH" class="form-control ">
                </div>
              </div>
            </div>
          </div>
          <div class="col-sm-6 col-12">
            <div class="info-row">
              <div class="key w100">Số máy</div>
              <div class="value">
                <input type="text" id="txtMaTB" v-model="txtSoMay" v-on:keyup="txtMaTB_KeyPress" class="form-control highlight">
              </div>
            </div>
            <div class="row">
              <div class="col-sm-8 col-12">
                <div class="info-row">
                  <div class="key w100">Cell 1</div>
                  <div class="value">
                    <input type="text" id="txtCell1" v-model="txtCell1" class="form-control highlight">
                  </div>
                </div>
              </div>
              <div class="col-sm-4 col-12">
                <div class="info-row">
                  <div class="key w70">Simcard</div>
                  <div class="value">
                    <input type="text" id="txtSimcard" v-model="txtSimcard" class="form-control">
                  </div>
                </div>
              </div>
            </div>
            <div class="row">
              <div class="col-sm-8 col-12">
                <div class="info-row">
                  <div class="key w100">Cell 2</div>
                  <div class="value">
                    <input type="text" id="txtCell2" v-model="txtCell2" class="form-control highlight">
                  </div>
                </div>
              </div>
              <div class="col-sm-4 col-12">
                <div class="info-row">
                  <div class="key w70">Toạ độ</div>
                  <div class="value">
                    <input type="text" id="txtToaDo" v-model="txtToaDo" class="form-control highlight">
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
      <div class="row">
        <div class="col-sm-6 col-12">
          <div class="box-form">
            <div class="legend-title">Danh sách nhân viên thực hiện</div>
            <div class="table-content" style=" height: 220px;">
              <DataGrid :columns="this.options.colsNVTH_grid" id="dgvNhanVien"
                        v-bind:dataSource="this.options.danhsachNVTH_grid"
                        :showColumnCheckbox="false"
                        :enable-paging-server="false"
                        :allowPaging="true"
                        :showFilter="true"
                        :enabledSelectFirstRow="true"
                        @rowSelected="NVTH_grid_rowSelected">
              </DataGrid>
            </div>
          </div>
        </div>
        <div class="col-sm-6 col-12">
          <div class="box-form">
            <div class="legend-title">Dịch vụ đăng ký</div>
            <div class="table-content" style=" height: 220px;">
              <DataGrid :columns="this.options.colsDVDK_grid" id="dgvdangkyDV"
                        v-bind:dataSource="this.options.danhsachDVDK_grid"
                        :showColumnCheckbox="false"
                        :enable-paging-server="false"
                        :allowPaging="true"
                        :showFilter="true"
                        :enabledSelectFirstRow="true"
                        @rowSelected="DVDK_grid_rowSelected">
              </DataGrid>
            </div>

            <!--<div class="tc-pagination">
            <a href="#" class="btn  one-arrow-left-forward"></a>
            <a href="#" class="btn fa fa-angle-double-left"></a>
            <a href="#" class="btn fa fa-angle-left"></a>
            <span class="text">Bản ghi</span>
            <div class="dropdown inline">
              <div class="fw6 input-number" data-toggle="dropdown">
                <span class="text-main">10</span>/1033
              </div>
              <div class="dropdown-menu">
                <a href="#" class="dropdown-item">10</a>
                <a href="#" class="dropdown-item">20</a>
              </div>
            </div>
            <a href="#" class="btn fa fa-angle-right"></a>
            <a href="#" class="btn fa fa-angle-double-right"></a>
            <a href="#" class="btn one-arrow-right-forward"></a>
          </div>-->
          </div>
        </div>
      </div>
      <div class="info-row">
        <div class="key w70">Ghi chú</div>
        <div class="value">
          <input type="text" id="txtGhiChu_TB" v-model="txtGhiChu" class="form-control">
        </div>
      </div>
      <div class="box-form">
        <div class="legend-title">
          <div class="pull-left">Danh sách thuê bao</div>
          <div class="pull-right red normal">
            <div class="list-checks">
              <div class="item">
                <div class="check-action">
                  <input type="radio" id="rdoPhieuMoi" checked class="check" :value="0" v-model="optionPhieuMoi_TraLai">
                  <span class="name">Phiếu mới</span>
                </div>
              </div>
              <div class="item">
                <div class="check-action">
                  <input type="radio" id="rdoPhieuTra" class="check" :value="1" v-model="optionPhieuMoi_TraLai">
                  <span class="name">Phiếu trả lại</span>
                </div>
              </div>
            </div>
          </div>
          <div class="clearfix"></div>
        </div>
        <div class="table-content">
          <DataGrid ref="dsphieu"
                    v-bind:columns="gview_DanhSach.header"
                    v-bind:dataSource="gview_DanhSach.list"
                    :enable-paging-server="false"
                    :allowPaging="true"
                    :showFilter="true"
                    :showColumnCheckbox="false"
                    @selectedRowChanged="dtgDanhSach_SelectionChanged"
                    :enabledSelectFirstRow="false"
                    @rowClicked="focusItem"
                    @actionComplete="gview_DanhSach_actionComplete">
          </DataGrid>


          <!--<DataGrid :columns="this.options.colsHDTB_grid" id="dtgDanhSach"
                  v-bind:dataSource="this.options.danhsachHDTB_grid"
                  :showColumnCheckbox="false"
                  :enable-paging-server="false"
                  :allowPaging="true"
                  :showFilter="true"
                  :enabledSelectFirstRow="true"
                  @rowSelected="dtgDanhSach_SelectionChanged"
                  :selectionSettings="{ checkboxMode: 'ResetOnRowClick'}"
                  @rowClicked="focusItem"
                  ref="gridDanhSach_HDTB">
        </DataGrid>-->
        </div>
      </div>
    </div>


    <VatTuThueBao ref="popupVatTuThueBao" :phieu_id="this.phieu_id" :loaihd_id="this.loaihd_id" :hdtb_id="this.hdtb_id" :thuebao_id="0"></VatTuThueBao>
    <DuAnHDTB ref="popupDuAnHDTB" :hdtb_id="this.hdtb_id"></DuAnHDTB>
    <ChuyenTo ref="popupChuyenTo"></ChuyenTo>
    <tonPhieu ref="popupTonPhieu" @close="closePopupTonPhieu" :trangthaiId="this.tthd_id" :phieuId="this.phieu_id" />
    <b-modal id="gankhuvuc"
             modal-class="popup-box"
             header-class="popup-header"
             body-class="none-padding"
             title="Gán khu vực quản lý thuê bao"
             size="xl">
      <Gankhuvuc @chapnhan="this.chapnhan_gankhuvuc" :dulieu="{
        donvi_id: 0,
        hdtb_id: this.hdtb_id
        }" />
    </b-modal>

    <ejs-dialog :enableResize='true' :allowDragging='true' :visible='false'
                ref="popupGiaoPhieuNV" :header='"Giao phiếu Nhân viên"' showCloseIcon=true width='95%' height='100%' target='.main-wrapper'>
      <giaophieunhanvien @giaoviec_success="giaoviec_success()" ref="formgiaphieu" :phieu_id="this.phieu_id" :nguoigv_id="this.ddlNguoiGiaoViec" :ngaygiao="this.dtpNgayGV" />
    </ejs-dialog>


    <ThongTinHenKhachHangModal ref="ThongTinHenKhachHangModal" :hdkh_id="this.hdkh_id"
                               :quyenduoc_hen="this.quyen_duoc_hen" />

    <ThayDoiKhuVucQuanLyThueBaoModal ref="thayDoiKhuVucQuanLyThueBaoModal"
                                     :data="dataPopupKhuVuc"
                                     @accept="onAcceptKhuVuc"
                                     @success_update="successUpdateThayDoiKhuVucQuanLy" />


    <InBienBan ref="popupInBB" :params="modelIn" />

    <CapVatTuTB ref="popupVatTuTB" :input-data="modelVatTu" />

    <b-modal id="popupComponents" size="xl"
             hide-footer
             hide-header
             hide-header-close
             body-class="modal-body p-0">
      <component :is="popupComponent" ref="popupComponent" :dulieu="popupComponentData" isPopup="true" @form-close="popupClosed" @acceptChangeCTV="popupClosed" @acceptChangeNGT="popupClosed"></component>
    </b-modal>

    <b-modal id="popupComponents_traphieu" size="xl"
             hide-footer
             hide-header
             hide-header-close
             body-class="modal-body p-0">
      <component :is="popupComponent_traphieu"
                 ref="popupComponent_traphieu"
                 :ngaytra="this.dataTraPhieu.ngaytra"
                 :ngaygiao="this.dataTraPhieu.ngaygiao"
                 :ma_gd="this.dataTraPhieu.ma_gd"
                 :donvi_id="this.dataTraPhieu.donvi_id"
                 :ma_tb="this.dataTraPhieu.ma_tb"
                 :kieu="this.dataTraPhieu.kieu"
                 :loaihd_id="this.dataTraPhieu.loaihd_id"
                 :dichvuvt_id="this.dataTraPhieu.dichvuvt_id" isPopup="true" @form-close="popupClosed" @acceptChangeCTV="popupClosed" @acceptChangeNGT="popupClosed"></component>
    </b-modal>

  </div>
</template>

<style src="./InstallGphone_KTVT.scss"></style>

<style src="../../../../assets/vendor/font-awesome/css/font-awesome.min.css"></style>
<style src="../../../../assets/vendor/ap8/css/style.css"></style>
<style src="../../../../assets/vendor/nucleo/outline/css/style.css"></style>
<style src="../../../../assets/vendor/nucleo/glyph/css/style.css"></style>
<style src="../../../../assets/vendor/bootstrap/css/bootstrap.min.css"></style>
<style src="../../../../assets/vendor/bootstrap-table/bootstrap-table.min.css"></style>
<style src="../../../../assets/vendor/bootstrap-table/extensions/fixed-columns/bootstrap-table-fixed-columns.css"></style>
<style src="../../../../assets/vendor/select2/css/select2.min.css"></style>
<style src="../../../../assets/vendor/oneicon/style.css"></style>
<style src="../../../../assets/css/style.css"></style>

<script src="../../../../assets/vendor/jquery/jquery-3.5.1.slim.min.js"></script>
<script src="../../../../assets/vendor/jquery/moment.min.js"></script>
<script src="../../../../assets/vendor/jquery/popper.min.js"></script>
<script src="../../../../assets/vendor/select2/js/select2.min.js"></script>
<script src="../../../../assets/vendor/bootstrap/js/bootstrap.min.js"></script>
<script src="../../../../assets/vendor/highchart/highcharts.js"></script>
<script src="../../../../assets/vendor/bootstrap-table/bootstrap-table.min.js"></script>
<script src="../../../../assets/vendor/bootstrap-table/extensions/fixed-columns/bootstrap-table-fixed-columns.min.js"></script>
<script src="../../../../assets/vendor/jquery/split.js"></script>
<script src="../../../../assets/js/custom.js"></script>

<script>
  import {
    DichVuVienThong
  } from "@/const/enums";
  import VueInput from "../../../search/subscriber/components/form/VueInput.vue";
  import api from "./api";
  import define from "./define";
  import VueCheckbox from '../../../search/subscriber/components/form/VueCheckbox.vue';
  import XLSX from "xlsx";
  import { LoaiHopDong, NHOM_KQXL, TRANGTHAI_DONGBO, TrangThaiHD } from '../../../../utils/Enum'
  import Enum from '@/modules/EXTS/HOPDONG/DongBoVisaVasc/enum';
  import token_var from '../../../../plugins/token.js';

  import VatTuThueBao from '@/modules/contract/setup/ChangeServiceOMCVIPCM/popups/VatTuThueBao.vue';
  import DuAnHDTB from '@/modules/contract/setup/ChangeServiceOMCVIPCM/popups/DuAnHDTB/DuAnHDTB.vue';
  import ChuyenTo from '@/modules/contract/profile/CompleteProfileChangeAccount/Popups/ChuyenTo.vue';

  import Gankhuvuc from '@/modules/contract/setup/InstallNewSubs/Popups/popupGanKhuVucQuanLyThueBao'
  import TonPhieu from '@/modules/contract/setup/Payment/components/TonPhieu.vue';

  import giaophieunhanvien from "@/modules/contract/setup/DeclareLandline/popup/giaophieunhanvien.vue";
  import ThongTinHenKhachHangModal from "@/modules/contract/setup/SurveyRequest/popups/ThongTinHenKhachHang/ThongTinHenKhachHangModal"
  import ThayDoiKhuVucQuanLyThueBaoModal from "@/modules/contract/setup/SurveyRequest/popups/ThayDoiKhuVucQuanLyThueBao/ThayDoiKhuVucQuanLyThueBaoModal.vue"
  import Vue from 'vue'
  import moment from "moment";
  import InBienBan from "../../../print/InBienBan/InBienBan"
  import CapVatTuTB from "@/modules/contract/complete_profile/CompleteProfileChangeTSMyTV/popups/CapVatTuTB.vue";

  import commonFn from "@/modules/contract/setup/ChangeLandlineIMSType/CommonFuntions";
  export default {
    props: ['dulieu'],
    //components: { VueInput, VueCheckbox, VatTuThueBao, DuAnHDTB, ChuyenTo, Gankhuvuc, TonPhieu, giaophieunhanvien, HandoverTicket, ThongTinHenKhachHangModal, ThayDoiKhuVucQuanLyThueBaoModal, InBienBan, CapVatTuTB },
    components: { VueInput, VueCheckbox, VatTuThueBao, DuAnHDTB, ChuyenTo, Gankhuvuc, TonPhieu, giaophieunhanvien, ThongTinHenKhachHangModal, ThayDoiKhuVucQuanLyThueBaoModal, InBienBan, CapVatTuTB },
    data() {
      return {
        popupComponent: null,
        popupComponentData: null,
        popupComponentName: '',
        popupComponentEvents: {
          'form-close': 'popupClosed',
          'acceptChangeCTV': 'popupClosed',
          'acceptChangeNGT': 'popupClosed',
        },

        ...define,
        target: '.main-wrapper',
        options: {
          doituong: [],
          nhantin: [],
          loaiHD: [],
          kenhThu: [],
          kieuHD: [],
          QuyTrinh: [],

          danhsachHDTB_grid: [],
          danhsachHDTB_grid_checked: [],


          danhsachNVTH_grid: [],
          danhsachDVDK_grid: [],

          colsHDTB_grid: [

            {
              fieldName: "thischeck",
              type: "checkbox",
              headerText: "Check",
              width: "50",
            },


            {
              fieldName: "ma_tb",
              headerText: "Số máy",
              allowFiltering: true,
              textAlign: "left",
              allowSorting: true,
            },
            {
              fieldName: "ten_tb",
              headerText: "Tên thuê bao",
              allowFiltering: true,
              textAlign: "left",
              allowSorting: true,
            },
            {
              fieldName: "diachi_ld",
              headerText: "Địa chỉ LĐ",
              allowFiltering: true,
              textAlign: "left",
              allowSorting: true,
            },
            {
              fieldName: "ngay_dk",
              headerText: "Mã khu vực",
              allowFiltering: true,
              textAlign: "left",
              allowSorting: true,
            },
            {
              fieldName: "ten_kv",
              headerText: "Tên khu vực",
              allowFiltering: true,
              textAlign: "left",
              allowSorting: true,
            },
            {
              fieldName: "ten_loaihd",
              headerText: "Loại HĐ/PL",
              allowFiltering: true,
              textAlign: "left",
              allowSorting: true,
            },
            {
              fieldName: "luu_in",
              headerText: "Trạng thái in",
              allowFiltering: true,
              textAlign: "left",
              allowSorting: true,
            },
            {
              fieldName: "ngaygiao",
              headerText: "Ngày BG",
              allowFiltering: true,
              textAlign: "left",
              allowSorting: true,
            },
            {
              fieldName: "ghichu",
              headerText: "Ghi chú",
              allowFiltering: true,
              textAlign: "left",
              allowSorting: true,
            }
          ],

          colsNVTH_grid: [
            {
              fieldName: "ten_nv",
              headerText: "Tên nhân viên",
              allowFiltering: true,
              textAlign: "left",
              allowSorting: true,
            },
            {
              fieldName: "so_dt",
              headerText: "Điện thoại",
              allowFiltering: true,
              textAlign: "left",
              allowSorting: true,
            },
            {
              fieldName: "nhiemvu",
              headerText: "Nhiệm vụ",
              allowFiltering: true,
              textAlign: "left",
              allowSorting: true,
            },
            {
              fieldName: "ghichu",
              headerText: "Ghi chú",
              allowFiltering: true,
              textAlign: "left",
              allowSorting: true,
            }
          ],

          colsDVDK_grid: [
            {
              fieldName: "ma_dvgt",
              headerText: "Mã DVGT",
              allowFiltering: true,
              textAlign: "left",
              allowSorting: true,
            },
            {
              fieldName: "ten_dvgt",
              headerText: "Tên dịch vụ",
              allowFiltering: true,
              textAlign: "left",
              allowSorting: true,
            },
            {
              fieldName: "kieu_yc",
              headerText: "Kiểu YC",
              allowFiltering: true,
              textAlign: "left",
              allowSorting: true,
            }
          ],

          nguoigiaoviec: []
        },
        ddlDoituong: "",
        ddlNhantin: "",
        ddlLoaiHD: "",
        ddlKenhThu: "",
        ddlKieuHD: "",
        ddlNguoiGiaoViec: "",
        ddlQuyTrinh: "",
        loaitbid: 0,
        userId: "",
        nhanvien_id: 0,
        vma_tinh: "",
        donvi_id: 0,
        may_cn: 0,
        ma_nd: "",

        tthd_id: 0,
        dsloaihd_id: [],
        dsdichvuvt_id: [],
        txtMaGD: "",
        optionPhieuMoi_TraLai: 0,
        txtTongTien: "",
        txtNVTiepThi: "",
        txtNVYC: "",
        dtpNgayGV: "",
        dtpNgayHT: "",
        ckbNgayGV: false,
        ckbNgayHT: false,
        txtSimcard: "",
        ckbLDTheoYC: false,
        luong_id: 0,
        huonggiao_id: 0,
        _vquytrinh_id: 0,
        VatTuThueBao,
        DuAnHDTB,
        ChuyenTo,
        Gankhuvuc,
        TonPhieu,


        phieu_id: 0,
        thuebao_id: 0,
        hdtb_id: 0,
        loaihd_id: 0,
        loaitb_id: 0,
        hdkh_id: 0,
        txtSoMay: "",
        txtTenTB: "",
        txtKhuVuc: "",
        txtCell1: "",
        txtCell2: "",
        txtToaDo: "",
        txtNoiDungTH: "",
        txtGhiChu: "",
        kt_status: "",
        txtDiaChiLDCu: "",
        txtDiaChiLDMoi: "",
        nhanvien_id: 0,
        nhanvien_th_id: 0,
        txtNoiDungGiao: "",

        dtThaoTac: [],
        disCkbNgayHT: false,
        disCkbNgayGV: false,

        searchDate: {
          isEnabled: false,
          fromDate: new Date(),
          toDate: new Date(),
          dateconfig: {
            altFormat: 'd/m/Y H:i:S',
            altInput: true,
            dateFormat: 'd/m/Y H:i:S',
            allowInput: true,
          },
        },

        ngay_tt_select: null,
        ngay_sys_select: null,
        ma_tb_select: "",
        ngay_sys_select: null,
        quyen_duoc_hen: 0,
        isDisplayMenu: false,




        dataPopupKhuVuc: {
          quan_id: 0,
          phuong_id: 0,
          pho_id: 0,
          ap_id: 0,
          khu_id: 0,
          dacdiem_id: 0,
          khuvuc_id: 0,
          donviql_id: 0,
          hdtb_id: 0
        },
        dataDiaChiLD: {
          diachi_id: 0,
          tinh_id: 0,
          quan_id: 0,
          phuong_id: 0,
          pho_id: 0,
          ap_id: 0,
          khu_id: 0,
          dacdiem_id: 0,
          sonha: '',
          //
          block: '',
          tang: '',
          sophong: '',
          motathem: '',
          diachi: ''
        },
        khuvuc_selected: null,
        donvi_xyly_selected: null,


        modelIn: {},

        data_thuebao: {},
        modelVatTu: {},

        current: 0,


        formProps_txtMaTB: '',


        gview_DanhSach: {
          list: [],
          isCheckAll: false,
          clickOnGrid: false,
          hdtbChecked: [],
          header: [
            {
              fieldName: '', headerText: '', visible: true, headerAlign: 'Center', textAlign: 'Center', allowFiltering: false, width: '70',
              headerTemplate: () => {
                return {
                  template: Vue.component("action-check-all", {
                    template: `
                                <div class="check-action">
                                    <input type="checkbox" v-model="parent.gview_DanhSach.isCheckAll" class="check"
                                        @change="onChangeProcessed(data)" />
                                    <span class="name"></span>
                                </div>
                            `,
                    data: function () {
                      return { data: {} };
                    },
                    computed: {
                      parent() {
                        return this.$parent.$parent.$parent
                      },
                    },
                    methods: {
                      onChangeProcessed(value) {
                        this.parent.clickOnGrid = false
                      },
                    }
                  }),
                };
              },
              template: function () {
                return {
                  template: Vue.component('action-check', {
                    template: `
                        <div class="check-action">
                          <input
                            type="checkbox"
                            class="check"
                            v-model="parent.gview_DanhSach.hdtbChecked"
                            :value="data.hdtb_id"/>
                          <span class="name"></span>
                        </div>
                      `,
                    data() {
                      return { data: {} }
                    },
                    computed: {
                      parent() {
                        return this.$parent.$parent.$parent
                      }
                    },
                  })
                }
              }
            },
            {
              fieldName: "ma_tb",
              headerText: "Số máy",
              allowFiltering: true,
              textAlign: "left",
              allowSorting: true,
            },
            {
              fieldName: "ten_tb",
              headerText: "Tên thuê bao",
              allowFiltering: true,
              textAlign: "left",
              allowSorting: true,
            },
            {
              fieldName: "diachi_ld",
              headerText: "Địa chỉ LĐ",
              allowFiltering: true,
              textAlign: "left",
              allowSorting: true,
            },
            {
              fieldName: "ngay_dk",
              headerText: "Mã khu vực",
              allowFiltering: true,
              textAlign: "left",
              allowSorting: true,
            },
            {
              fieldName: "ten_kv",
              headerText: "Tên khu vực",
              allowFiltering: true,
              textAlign: "left",
              allowSorting: true,
            },
            {
              fieldName: "ten_loaihd",
              headerText: "Loại HĐ/PL",
              allowFiltering: true,
              textAlign: "left",
              allowSorting: true,
            },
            {
              fieldName: "luu_in",
              headerText: "Trạng thái in",
              allowFiltering: true,
              textAlign: "left",
              allowSorting: true,
            },
            {
              fieldName: "ngaygiao",
              headerText: "Ngày BG",
              allowFiltering: true,
              textAlign: "left",
              allowSorting: true,
            },
            {
              fieldName: "ghichu",
              headerText: "Ghi chú",
              allowFiltering: true,
              textAlign: "left",
              allowSorting: true,
            }
          ],
          selectedItem: null,
          checked: []
        },

        dataTraPhieu: {
          ma_gd: "",
          loaihd_id: "",
          dichvuvt_id: "",
          donvi_id: "",
          khu_id: "",
          ma_tb: "",
          ngaygiao: "0",
          ngaytra: "0",
          kieu: 3
        },
      };
    },
    created() {
      //this.CapNhat()
      this.form_load()
    },
    watch: {
      optionPhieuMoi_TraLai: function (newRole) {
        this.clearAll()
        this.HienThiDanhSachHDTB()
      },

      //ckbNgayGV: function (newRole) {
      //  if (this.ckbNgayGV == false)
      //    this.dtpNgayGV = ""
      //},

      //ckbNgayHT: function (newRole) {
      //  if (this.ckbNgayHT == false)
      //    this.dtpNgayHT = ""
      //},

      "ddlLoaiHD"(val) {
        this.onChange_ddlLoaiHD()
      },

      "ddlQuyTrinh"(val) {
        this.onChange_ddlQuyTrinh()
      },

      "current"(val) {

      },

      "gview_DanhSach.isCheckAll": {
        handler: function (value) {
          if (value) {
            this.gview_DanhSach.list.forEach((f) => {
              f.checked = true;
            });
            this.gview_DanhSach.hdtbChecked = this.gview_DanhSach.list.map(
              (f) => f.hdtb_id
            );

            this.gview_DanhSach.checked = []
            this.gview_DanhSach.hdtbChecked.forEach(item => {
              let oFind = this.gview_DanhSach.list.find(ix => ix.hdtb_id == item)
              if (oFind != undefined && oFind != null) {
                this.gview_DanhSach.checked.push(oFind)
              }
            })
            return;
          } else {
            if (!this.clickOnGrid) {
              this.gview_DanhSach.list.forEach((f) => {
                f.checked = false;
              });
              this.gview_DanhSach.hdtbChecked = [];
              this.gview_DanhSach.checked = [];
              return;
            }
          }
        },
        deep: true,
      },
      "gview_DanhSach.hdtbChecked": {
        handler: function (value) {
          this.clickOnGrid = true
          this.gview_DanhSach.isCheckAll = value.length > 0 && value.length === this.gview_DanhSach.list.length;

          this.gview_DanhSach.checked = []
          value.forEach(item => {
            let oFind = this.gview_DanhSach.list.find(ix => ix.hdtb_id == item)
            if (oFind != undefined && oFind != null) {
              this.gview_DanhSach.checked.push(oFind)
            }
          })
        },
        deep: true,
      },

    },
    methods: {
      async form_load() {
        try {
          this.loading(true)


          try {
            if (this.$route.query.tag != null && this.$route.params.tag != '' && this.$route.query.tag.length > 0) {
              this.Tag = this.$route.query.tag
              if (this.Tag != null && this.Tag.length > 0) {
                let arr = this.Tag.split('+')
                if (arr.length >= 1) this.tthd_id = parseInt(arr[0])
                if (arr.length >= 2) this.dsloaihd_id = arr[1].split(';')
                if (arr.length >= 3) this.dsdichvuvt_id = arr[2].split(';')
              }
            }
            else if (this.dulieu) {
              if (this.dulieu.tthd_id)
                this.tthd_id = this.dulieu.tthd_id
              if (this.dulieu.dsloaihd_id)
                this.dsloaihd_id = this.dulieu.dsloaihd_id
              if (this.dulieu.dsdichvuvt_id)
                this.dsdichvuvt_id = this.dulieu.dsdichvuvt_id
            }
            else {
              this.tthd_id = TrangThaiHD.DANG_THI_CONG
              this.dsloaihd_id = "1"
              this.dsdichvuvt_id = "10"
            }

            //if (this.$route.query.tag != null && this.$route.params.tag != '' && this.$route.query.tag.length > 0) {
            //  this.Tag = this.$route.query.tag
            //}
            ///*debugger*/
            //if (this.Tag != null && this.Tag.length > 0) {
            //  let arr = this.Tag.split('+')
            //  if (arr.length >= 1) this.tthd_id = parseInt(arr[0])
            //  if (arr.length >= 2) this.dsloaihd_id = arr[1].split(';')
            //  if (arr.length >= 3) this.dsdichvuvt_id = arr[2].split(';')
            //}
            //else {
            //  this.tthd_id = TrangThaiHD.DANG_THI_CONG
            //  this.dsloaihd_id = "1"
            //  this.dsdichvuvt_id = "10"
            //}
          }
          catch (ex) {
            this.tthd_id = TrangThaiHD.DANG_THI_CONG
            this.dsloaihd_id = "1"
            this.dsdichvuvt_id = "10"
          }

          this.vma_tinh = token_var.getMaTinh();
          this.userId = token_var.getNguoiDungID();
          this.nhanvienID = token_var.getNhanVienID();
          this.donvi_id = token_var.getDonViID();
          this.may_cn = token_var.getMaNhanVien()
          this.nhanvien_id = token_var.getNhanVienID()
          this.ma_nd = token_var.getUserName()


          this.getComboLoaiHD();
          this.getComboKenhThu();
          this.getComboKieuHD();
          this.getComboNguoiGiaoViec();
          //await Promise.all([this.getComboLoaiHD(), this.getComboKenhThu(), this.getComboKieuHD(), this.getComboNguoiGiaoViec()]);
          this.dtpNgayHT = moment(new Date()).format("DD/MM/YYYY HH:mm")
          this.disableButton("tsbtnVatTu")

          if (this.$route.name == 'InstallGphone_KTVT') {
            this.header.list[1].name = 'Thi công mới'
            this.header.list[2].name = 'Tổ KTVT - Thi công Gphone'
          }
          else {
            this.header.list[1].name = 'Hoàn thiện hồ sơ'
            this.header.list[2].name = 'HTHS Khôi phục Gphone'
          }



          this.loading(false)
        }
        catch (err) {
          this.$toast.error(err.message);
        }
      },

      async getComboLoaiHD() {
        let items = [];
        const files = await this.axios.get("/web-quantri/danhmuc-chung/CSS_LOAI_HD", {
          headers: {
            'Content-Type': 'application/json'
          }
        });
        if (files.data.data.length > 0) {
          await Promise.all(files.data.data.map(async (file) => {
            if (this.isExistArr(file.LOAIHD_ID, this.dsloaihd_id)) {
              items.push({ id: file.LOAIHD_ID, text: file.TEN_LOAIHD });
            }
          }));
          this.options.loaiHD = items;


          //this.ddlLoaiHD = items[0].id;
          if (this.$route.name == 'InstallGphone_KTVT')
            this.ddlLoaiHD = 1
          else
            this.ddlLoaiHD = items[0].id
        }
      },

      async getComboKenhThu() {
        let items = [];
        const files = await this.axios.get("web-quantri/danhmuc-chung/CSS_KENHTHU", {
          headers: {
            'Content-Type': 'application/json'
          }
        });
        if (files.data.data.length > 0) {
          await Promise.all(files.data.data.map(async (file) => {
            items.push({ id: file.KENHTHU_ID, text: file.KENHTHU });
          }));
          this.options.kenhThu = items;
          this.ddlKenhThu = items[0].id;
        }

      },

      async getComboKieuHD() {
        let items = [];
        const files = await this.axios.get("web-quantri/danhmuc-chung/CSS_KIEU_HD", {
          headers: {
            'Content-Type': 'application/json'
          }
        });
        if (files.data.data.length > 0) {
          await Promise.all(files.data.data.map(async (file) => {
            items.push({ id: file.KIEUHD_ID, text: file.KIEU_HD });
          }));
          this.options.kieuHD = items;
          this.ddlKieuHD = items[0].id;
        }
      },

      async getComboNguoiGiaoViec() {
        let items = [];
        var json_data = JSON.stringify({
          "nhanvien_id": this.nhanvien_id,
          "loainv_id": 0
        });
        const files = await this.axios.post("/web-ccdv/thicong_ghone/sp_ds_nguoi_giaoviec", json_data, {
          headers: {
            'Content-Type': 'application/json'
          }
        });
        debugger
        if (files.data.data.length > 0) {
          await Promise.all(files.data.data.map(async (file) => {
            items.push({ id: file.nhanvien_id, text: file.ten_nv });
          }));
          this.options.nguoigiaoviec = items;
          this.ddlNguoiGiaoViec = items[0].id;
        }
      },

      async getComboQuyTrinh() {
        let items = [];
        var json_data = JSON.stringify({
          "p_dsloaihd_id": this.ddlLoaiHD,
          "p_dichvuvt_id": this.dsdichvuvt_id.join(),
          "p_tthd_id": this.tthd_id,
          "p_kieu": 0
        });
        //console.log(json_data)
        const files = await this.axios.post("/web-ccdv/HoanCongThuTienPhatSinh/SP_LAY_DS_QUYTRINH_V2", json_data, {
          headers: {
            'Content-Type': 'application/json'
          }
        });
        //console.log(files)
        if (files.data.data.length > 0) {
          await Promise.all(files.data.data.map(async (file) => {
            items.push({ id: file.quytrinh_id, text: file.quytrinh });
          }));
          this.options.QuyTrinh = items;
          this.ddlQuyTrinh = items[0].id;

          if (this.dulieu && this.dulieu._vquytrinh_id != 0)
            this.ddlQuyTrinh = this.dulieu._vquytrinh_id
        }
      },

      async getTongTien() {
        var json_data = JSON.stringify({
          "hdkh_id": this.hdkh_id
        });
        const files = await this.axios.post("/web-thicong/hths-thaydoi-tocdo/lay_tongtien_theo_hdkh_id", json_data, {
          headers: {
            'Content-Type': 'application/json'
          }
        });
        if (files.data.data.length > 0) {
          await Promise.all(files.data.data.map(async (file) => {
            this.txtTongTien = file.tongtien
          }));
        }

      },

      async getNVTiepThi() {
        if (this.nhanvien_id != 0) {
          const files = await this.axios.get("/web-hopdong/danhmuc/lay_thongtin_nhanvien/" + this.nhanvien_id, {
            headers: {
              'Content-Type': 'application/json'
            }
          });
          if (files.data.data.length > 0) {
            await Promise.all(files.data.data.map(async (file) => {
              this.txtNVTiepThi = file.NAME
            }));
          }
        }
      },

      async getNVYC() {
        if (this.nhanvien_id != 0) {
          const files = await this.axios.get("/web-hopdong/danhmuc/lay_thongtin_nhanvien/" + this.nhanvien_th_id, {
            headers: {
              'Content-Type': 'application/json'
            }
          });
          if (files.data.data.length > 0) {
            await Promise.all(files.data.data.map(async (file) => {
              this.txtNVYC = file.NAME
            }));
          }
        }
      },

      async getKenhThu() {
        var json_data = JSON.stringify({
          "hdkh_id": this.hdkh_id
        });
        const files = await this.axios.post("/web-ccdv/khaibaotsl/sp_lay_kenhthu_theo_hdkh", {
          headers: {
            'Content-Type': 'application/json'
          }
        });
        if (files.data.data.length > 0) {
          await Promise.all(files.data.data.map(async (file) => {
            this.ddlKenhThu = file.kenhthu_id
          }));
        }
      },

      async getDataDVDK() {
        var link = "/web-ccdv/dongbo-visa-vasc/lay_ds_dangky_dvgt/" + this.hdtb_id
        const files = await this.axios.get(link, {
          headers: {
            'Content-Type': 'application/json'
          }
        });
        this.options.danhsachDVDK_grid = files.data.data ? files.data.data : [];
        //if (this.options.danhsachDVDK_grid.length < 1) {
        //  this.$toast.error("Không có dữ liệu hiển thị DỊCH VỤ ĐĂNG KÝ!");
        //}
      },

      async getDataDSNV() {
        var json_data = JSON.stringify({
          "phieu_id": this.phieu_id,
          "kieu_id": 2
        });
        const files = await this.axios.post("/web-ccdv/HoanThanhKhaoSat/lay_ds_nhanvien_theo_phieu_id", json_data, {
          headers: {
            'Content-Type': 'application/json'
          }
        });

        if (files.data.data.length > 0) {
          this.ckbNgayGV = true
          this.dtpNgayGV = files.data.data[0].ngaygiao
          this.ddlNguoiGiaoViec = files.data.data[0].nhanvien_giao_id
          this.options.danhsachNVTH_grid = files.data.data ? files.data.data : []
        }
        else {
          if (!this.PHAILAM(this.luong_id, "GIAOVIEC")) {
            this.chkNgayGV = true
          }
          else {
            this.chkNgayGV = false
          }
          this.dtpNgayGV = moment(new Date()).format("DD/MM/YYYY HH:mm")
          this.ddlNguoiGiaoViec = this.nhanvien_id
        }
      },

      async checkLDTheoYC() {
        var json_data = JSON.stringify({
          "hdkh_id": this.hdkh_id
        });
        const files = await this.axios.post("/web-ccdv/khaibaotsl/sp_kiemtra_hen_hdtb", json_data, {
          headers: {
            'Content-Type': 'application/json'
          }
        });
        await Promise.all(files.data.data.map(async (file) => {
          if (file.c != 0) {
            this.ckbLDTheoYC = true;
          }
          else {
            this.ckbLDTheoYC = false;
          }
        }));
      },

      async GetHuongGiaoTheoQuyTrinh() {
        var link = "/web-ccdv/HoanCongThuTienPhatSinh/SP_LAY_HUONGGIAO_THEO_QUYTRINH?P_QUYTRINH_ID=" + this.ddlQuyTrinh + "&P_TTHD_ID=" + this.tthd_id + ""
        const files = await this.axios.get(link, {
          headers: {
            'Content-Type': 'application/json'
          }
        });
        if (files.data.data.length == 0) {
          this.$toast.error("Không tìm thấy luồng quy trình nghiệp vụ này.\nBạn hãy liên hệ Admin kiểm tra lại");
          return false
        }
        await Promise.all(files.data.data.map(async (file) => {
          this.luong_id = file.luong_id
          this.huonggiao_id = file.huonggiao_id
          this.header.title = file.huonggiao
        }));
        this.HienThiGiaoDien()
        return true
      },

      async dtgDanhSach_SelectionChanged(args) {
        await this.loading(true)
        console.log(args)
        this.disableButton("tsbtnChapNhan")
        this.disableButton("tsbtnHoanCong")
        this.disableButton("tsbtnTraPhieu")


        try {
          this.enableButton("tsbtnChapNhan")
          this.enableButton("tsbtnVatTu")

          this.data_thuebao = args;

          this.ngay_tt_select = args.thangnam_tt != null ? args.thangnam_tt : ""
          this.ngay_sys_select = args.thangnam_sys != null ? args.thangnam_sys : ""
          this.ma_tb_select = args.ma_tb != null ? args.ma_tb : ""

          this.formProps_txtMaTB = this.ma_tb_select

          this.phieu_id = args.phieu_id != null ? args.phieu_id : 0
          this.thuebao_id = args.thuebao_id != null ? args.thuebao_id : 0
          this.hdtb_id = args.hdtb_id != null ? args.hdtb_id : 0
          this.loaihd_id = args.loaihd_id != null ? args.loaihd_id : 0
          this.loaitb_id = args.loaitb_id != null ? args.loaitb_id : 0
          this.hdkh_id = args.hdkh_id != null ? args.hdkh_id : 0
          this.ddlKieuHD = args.kieuhd_id != null ? args.kieuhd_id : ""
          this.txtMaGD = args.ma_gd != null ? args.ma_gd : ""
          this.txtSoMay = args.ma_tb != null ? args.ma_tb : ""
          this.txtTenTB = args.ten_tb != null ? args.ten_tb : ""
          this.txtKhuVuc = args.ten_kv != null ? args.ten_kv : ""
          this.txtCell1 = args.cell1 != null ? args.cell1 : ""
          this.txtCell2 = args.cell2 != null ? args.cell2 : ""
          this.txtSimcard = args.simcard != null ? args.simcard : ""
          this.txtToaDo = args.toado != null ? args.toado : ""
          this.txtNoiDungTH = args.nd_thuchien != null ? args.nd_thuchien : ""
          this.txtGhiChu = args.ghichu != null ? args.ghichu : ""
          this.kt_status = args.status != null ? args.status : ""
          this.txtDiaChiLDCu = args.diachi_ldcu != null ? args.diachi_ldcu : ""
          this.txtDiaChiLDMoi = args.diachi_ld != null ? args.diachi_ld : ""
          this.nhanvien_id = args.nhanvien_id != null ? args.nhanvien_id : 0
          this.nhanvien_th_id = args.nhanvien_th_id != null ? args.nhanvien_th_id : 0

          //await Promise.all([this.getTongTien(), this.getNVTiepThi(), this.getNVYC(), this.getKenhThu(), this.checkLDTheoYC(), this.getDataDVDK(), this.getDataDSNV()]);

          this.ddlKieuHD = args.kieuhd_id != null ? args.kieuhd_id : 0

          await this.getTongTien()
          await this.getNVTiepThi()
          await this.getNVYC()
          await this.getKenhThu()
          await this.checkLDTheoYC()
          await this.getDataDVDK()


          if (this.PHAILAM(this.luong_id, "GIAOPHIEU")) {
            if (args.ngay_ht_gp != "" && args.ngay_ht_gp != null) {
              this.ckbNgayHT = true;
              //this.dtpNgayHT = args.ngay_ht_gp.format("DD/MM/YYYY")
              this.dtpNgayHT = moment(new Date(args.ngay_ht_gp)).format("DD/MM/YYYY HH:mm")

              this.enableButton("tsbtnGiaoPhieu")
            }
            else {
              if (!this.PHAILAM(this.luong_id, "GIAOVIEC")) {
                this.ckbNgayHT = true;
              }
              else {
                this.ckbNgayHT = false;
              }
              this.dtpNgayHT = moment(new Date()).format("DD/MM/YYYY HH:mm")


              this.disableButton("tsbtnGiaoPhieu")
            }
          }
          if (this.PHAILAM(this.luong_id, "KICHHOAT_TD")) {
            if (args.ngay_ht_gp != "" && args.ngay_ht_gp != null)
              if (this.kt_status == TRANGTHAI_DONGBO.DONGBO_CM) {

                this.disableButton("tsbtnKichHoat")
              }
              else {

                this.enableButton("tsbtnKichHoat")
              }

            else {
              if (!this.PHAILAM(this.luong_id, "GIAOVIEC")) {
                this.ckbNgayHT = true
              }
              else {
                this.ckbNgayHT = false
              }
              this.dtpNgayHT = moment(new Date()).format("DD/MM/YYYY HH:mm")


              this.disableButton("tsbtnKichHoat")
            }
          }
          else {
            this.disableButton("tsbtnKichHoat")
          }

          if (this.PHAILAM(this.luong_id, "HOANCONG")) {
            if (args.ngay_ht_gp != "" && args.ngay_ht_gp != null && this.kt_status == TRANGTHAI_DONGBO.DONGBO_CM) {
              this.ckbNgayHT = true
              //this.dtpNgayHT = args.ngay_ht_gp
              this.dtpNgayHT = moment(new Date(args.ngay_ht_gp)).format("DD/MM/YYYY HH:mm")

              this.enableButton("tsbtnHoanCong")
            }
            else {
              if (!this.PHAILAM(this.luong_id, "GIAOVIEC")) {
                this.ckbNgayHT = true;
              }
              else {
                this.ckbNgayHT = false;
              }
              this.dtpNgayHT = moment(new Date()).format("DD/MM/YYYY HH:mm")

              this.disableButton("tsbtnHoanCong")
            }
          }

          if (this.PHAILAM(this.luong_id, "HOANTHIENHOSO")) {
            if (args.ngay_ht_gp != "" && args.ngay_ht_gp != null && this.kt_status == TRANGTHAI_DONGBO.DONGBO_CM) {
              this.ckbNgayHT = true;
              //this.dtpNgayHT = args.ngay_ht_gp
              this.dtpNgayHT = moment(new Date(args.ngay_ht_gp)).format("DD/MM/YYYY HH:mm")

              this.enableButton("tsbtnHoanThienHS")
            }
            else {
              if (!this.PHAILAM(this.luong_id, "GIAOVIEC")) {
                this.ckbNgayHT = true
              }
              else {
                this.ckbNgayHT = false
              }
              this.dtpNgayHT = moment(new Date()).format("DD/MM/YYYY HH:mm")

              this.disableButton("tsbtnHoanThienHS")
            }

            if (!this.PHAILAM(this.luong_id, "GIAOVIEC")) {
              this.ckbNgayHT = true
              this.disCkbNgayHT = true

              this.ckbNgayGV = true
              this.disCkbNgayGV = true
            }
          }
          await this.getDataDSNV()
          this.enableButton("tsbtnTraPhieu")

        }
        catch (err) {
          this.$toast.error(err.message)
        }

        await this.loading(false)
      },

      enableButton(ele) {
        let control_name = document.getElementById(ele)
        if (control_name != null) {
          if (control_name.classList.contains('non-active')) {
            control_name.classList.remove("non-active");
            control_name.classList.add("active");
          }
          document.getElementById(ele).style.pointerEvents = "auto"
        }
      },

      disableButton(ele) {
        let control_name = document.getElementById(ele)
        if (control_name != null) {
          if (control_name.classList.contains('active')) {
            control_name.classList.remove("active");
            control_name.classList.add("non-active");
          }
          document.getElementById(ele).style.pointerEvents = "none"
        }
      },

      DVDK_grid_rowSelected(args) {

      },

      NVTH_grid_rowSelected(args) {

      },


      async onChange_ddlLoaiHD(event) {
        try {
          await this.getComboQuyTrinh();
        }
        catch (err) {
          this.$toast.error(err.message);
        }
      },

      async onChange_ddlQuyTrinh(event) {

        if (await this.GetHuongGiaoTheoQuyTrinh() == true) {
          this.HienThiGiaoDien()
          this.clearAll()
          this.HienThiDanhSachHDTB()
          if (!this.PHAILAM(this.luong_id, "GIAOVIEC")) {
            this.ckbNgayGV = true
            this.ckbNgayHT = true
            this.dtpNgayGV = moment(new Date()).format("DD/MM/YYYY HH:mm");
            this.dtpNgayHT = moment(new Date()).format("DD/MM/YYYY HH:mm");
          }
        }
        else {
          return
        }
      },

      isExistArr(a, b) {
        for (var i = 0; i < b.length; i++) {
          if (b[i] == a) {
            return true
          }
        }
        return false
      },

      async HienThiGiaoDien() {
        document.getElementById("tsbtnDuAn").style.display = "none";
        document.getElementById("tsbtnGiaoViec").style.display = "none";
        document.getElementById("tsbtnGiaoPhieu").style.display = "none";
        document.getElementById("tsbtnHoanCong").style.display = "none";
        document.getElementById("btnPhieuTC").style.display = "none";
        document.getElementById("tsbtnTraPhieu").style.display = "none";
        document.getElementById("tsbtnVatTu").style.display = "none";
        document.getElementById("tsbtnVatTuMoi").style.display = "none";
        if (this.vma_tinh == "HCM")
          document.getElementById("tsbtnDauNoiCap").style.display = "block";
        else
          document.getElementById("tsbtnDauNoiCap").style.display = "none";

        try {
          var json_data = JSON.stringify({
            "luong_id": this.luong_id
          });
          var link = "/web-ccdv/khaibaoims/sp_lay_luong_thaotac"
          const files = await this.axios.post(link, json_data, {
            headers: {
              'Content-Type': 'application/json'
            }
          });
          if (files.data.data.length == 0) {
            return
          }
          this.dtThaoTac = files.data.data

          const files_thaotac = await this.axios.get("/web-quantri/danhmuc-chung/CSS_THAOTAC_CONTROL", {
            headers: {
              'Content-Type': 'application/json'
            }
          });

          for (const file of files.data.data) {

            let filter = files_thaotac.data.data.filter(item => (item.thaotac_id === file.thaotac_id))
            if (filter.length == 0) {
              continue
            }
            for (const file_control of filter) {

              var control_name = document.getElementById(file_control.control_name);

              if (control_name == null) {
                continue
              }
              if (control_name.tagName == 'LI' || control_name.tagName == 'A') {
                if (file.enable == "1")
                  control_name.style.display = "block"
                else
                  control_name.style.display = "none"
              }
              else if (control_name.tagName == 'INPUT' && control_name.getAttribute('type') == 'text') {
                if (file.enable == "1") {
                  if (control_name.classList.contains('non-active')) {
                    control_name.classList.remove("non-active");
                    control_name.classList.add("active");
                  }
                  control_name.style.pointerEvents = "auto"
                  control_name.style.backgroundColor = "cornsilk"
                  control_name.style.color = "maroon";
                }
                else {
                  if (control_name.classList.contains('active')) {
                    control_name.classList.remove("active")
                    control_name.classList.add("non-active")
                  }
                  control_name.style.pointerEvents = "none"
                }
              }
            }
          }
        }
        catch (err) {
          this.$toast.error(err.message);
        }
      },

      clearAll() {
        this.phieu_id = 0
        this.thuebao_id = 0
        this.hdtb_id = 0
        this.loaihd_id = 0
        this.loaitb_id = 0
        this.hdkh_id = 0
        this.txtMaGD = ""
        this.txtSoMay = ""
        this.txtTenTB = ""
        this.txtKhuVuc = ""
        this.txtCell1 = ""
        this.txtCell2 = ""
        this.txtSimcard = ""
        this.txtToaDo = ""
        this.txtNoiDungTH = ""
        this.txtGhiChu = ""
        this.kt_status = ""
        this.txtDiaChiLDCu = ""
        this.txtDiaChiLDMoi = ""
        this.txtNVTiepThi = ""
        this.txtNVYC = ""
        this.txtTongTien = ""
        this.nhanvien_id = 0
        this.nhanvien_th_id = 0

        this.txtNoiDungGiao = ""
        this.ckbNgayGV = false
        this.ckbNgayHT = false
        this.dtpNgayGV = ""
        this.dtpNgayHT = ""
        this.options.danhsachHDTB_grid = null
        this.options.danhsachDVDK_grid = null
        this.options.danhsachNVTH_grid = null

        this.gview_DanhSach.list = null
      },

      PHAILAM(luong_id_var, code_var) {
        if (this.dtThaoTac != null && this.dtThaoTac.length > 0) {
          if (this.dtThaoTac.find(e => e.enable == '1' && e.code == code_var)) {
            return true
          }
        }
        return false
      },

      async HienThiDanhSachHDTB() {
        this.loading(true);

        this.disableButton("tsbtnChapNhan")
        this.disableButton("tsbtnHoanCong")
        this.disableButton("tsbtnHoanThienHS")

        var json_data_lay_ds_hoancong_gphone_119 = JSON.stringify({
          "huonggiao_id": this.huonggiao_id,
          "loai_id": this.optionPhieuMoi_TraLai,
          "loaihd_id": this.ddlLoaiHD,
          "ma_gd": "0",
          "nhanvien_id": parseInt(this.nhanvienID),
          "tthd_id": this.tthd_id
        });
        //"ma_gd": this.txtMaGD == "" ? "0" : this.txtMaGD,
        var link_lay_ds_hoancong_gphone_119 = "/web-ccdv/thicong_ghone/lay_ds_hoancong_gphone_119"
        const files_lay_ds_hoancong_gphone_119 = await this.axios.post(link_lay_ds_hoancong_gphone_119, json_data_lay_ds_hoancong_gphone_119, {
          headers: {
            'Content-Type': 'application/json'
          }
        });
        if (files_lay_ds_hoancong_gphone_119.data.data) {

          for (let i = 0; i < files_lay_ds_hoancong_gphone_119.data.data.length; i++) {
            files_lay_ds_hoancong_gphone_119.data.data[i].ngaygiao = this.formatDate(files_lay_ds_hoancong_gphone_119.data.data[i].ngaygiao, 'DD/MM/YYYY HH:mm:ss')

          }

          this.gview_DanhSach.list = files_lay_ds_hoancong_gphone_119.data.data ? files_lay_ds_hoancong_gphone_119.data.data : [];
          if (this.gview_DanhSach.list.length < 1)
            this.clearAll()
          this.loading(false);
        }
      },

      async clickButton(key) {
        if (key == "laytt") {
          this.clearAll()
          await this.HienThiDanhSachHDTB()


        }
        else if (key == "capnhat") {
          this.CapNhat()
        }
        else if (key == "kichhoat") {
          this.KichHoat()
        }
        else if (key == "daunoi") {
          //chưa có form
        }
        else if (key == "hoancong") {
          this.HoanCong()
        }
        else if (key == "traphieu") {
          this.popupComponent_traphieu = () => import('@/modules/contract/setup/ReturnInstallTicket/components/traphieu');
          this.dataTraPhieu.ma_gd = this.txtSoMay
          this.dataTraPhieu.loaihd_id = this.loaihd_id
          this.dataTraPhieu.dichvuvt_id = DichVuVienThong.GPHONE
          this.dataTraPhieu.donvi_id = this.donvi_id
          this.dataTraPhieu.ma_tb = this.txtSoMay
          this.Popup('popupComponents_traphieu');
          this.clearAll()
          await this.HienThiDanhSachHDTB()
        }
        else if (key == "giaoviec") {
          this.GiaoViec()
        }
        else if (key == "vattu") {
          if (this.phieu_id != 0)
            this.$refs.popupVatTuThueBao.showModal();
          else
            this.$toast.error("Bạn chưa chọn phiếu!");
        }
        else if (key == "vattumoi") {
          if (this.phieu_id != 0) {
            this.modelVatTu.phieu_id = this.phieu_id
            this.modelVatTu.loaihd_id = this.loaihd_id
            this.modelVatTu.hdtb_id = this.hdtb_id
            this.modelVatTu.thuebao_id = this.thuebao_id
            this.modelVatTu.vdvvt_id = this.dsdichvuvt_id.join()
            this.modelVatTu.loaitb_id = this.loaitb_id
            this.modelVatTu.vma_tbcur = this.txtSoMay
            this.modelVatTu.baohong_id = 0;
            this.$refs.popupVatTuTB.openDialog();
          } else {
            this.$toast.error("Bạn chưa chọn phiếu!");
          }
        }
        else if (key == "chuyento") {
          this.$refs.popupChuyenTo.openDialog(this.phieu_id, this.hdtb_id)
        }

        else if (key == "tonphieu") {
          if (this.phieu_id == 0) {
            this.$toast.error("Hãy chọn phiếu trước để nhập lý do tồn!");
            return;
          }
          let arrPhieu = []
          this.$refs.popupTonPhieu.showPhieuTon(this.phieu_id, this.tthd_id, arrPhieu)
        }
        else if (key == "henld") {
          if (this.hdkh_id !== 0) {
            this.quyen_duoc_hen = 1
            this.$refs.ThongTinHenKhachHangModal.showModal()
          } else {
            this.$root.$toast.warning('Bạn chưa chọn phiếu')
          }
        }
        else if (key == "nvkt") {
          this.dataPopupKhuVuc.quan_id = this.dataDiaChiLD.quan_id
          this.dataPopupKhuVuc.phuong_id = this.dataDiaChiLD.phuong_id
          this.dataPopupKhuVuc.pho_id = this.dataDiaChiLD.pho_id
          this.dataPopupKhuVuc.ap_id = this.dataDiaChiLD.ap_id
          this.dataPopupKhuVuc.khu_id = this.dataDiaChiLD.khu_id
          this.dataPopupKhuVuc.dacdiem_id = this.dataDiaChiLD.dacdiem_id

          this.dataPopupKhuVuc.khuvuc_id = this.khuvuc_selected != null ? this.khuvuc_selected.khuvuc_id : 0
          this.dataPopupKhuVuc.donviql_id = this.donvi_xyly_selected != null ? this.donvi_xyly_selected : 0

          this.dataPopupKhuVuc.hdtb_id = (this.hdtb_id != undefined && this.hdtb_id != null) ? this.hdtb_id : 0



          this.$refs.thayDoiKhuVucQuanLyThueBaoModal.showModal()
        }
        else if (key == "duan") {
          this.$refs.popupDuAnHDTB.showModal();
        }
        else if (key == "giaophieu") {
          this.tsbtnGiaoPhieu_Click()
        }
        else if (key == "phieutc") {
          //show bao cao
        }
        else if (key == "inphieu") {
          this.InPhieu();
        }
        else if (key == "noidungth") {
          await this.loading(true)
          var json_data = JSON.stringify({
            "noidung": this.txtNoiDungTH,
            "phieu_id": this.phieu_id
          });
          var link = "/web-ccdv/thicong_ghone/sp_upd_noidung_thuchien"
          const files = await this.axios.post(link, json_data, {
            headers: {
              'Content-Type': 'application/json'
            }
          });
          debugger
          if (files.data.error_code == "BSS-00000000") {
            this.$toast.success("Cập nhật thành công!");

            this.clearAll()
            await this.HienThiDanhSachHDTB()
            //Chọn lại row vừa cập nhật
          }
          else if (files.data.error_code == "BSS-00000500") {
            this.$toast.error("Quá ký tự cho phép! Tối đa: 4000 ký tự.");
          }
          else {
            this.$toast.error("Cập nhật lỗi");
            this.clearAll()
            this.HienThiDanhSachHDTB()
          }

          await this.sleep(1000);
          await this.$refs.dsphieu.setCurrentSelectedRow(this.current);
          this.$forceUpdate()

          await this.loading(false)
        }
        else if (key == "hoanthienhs") {
          this.tsbtnChuyenDB_Click()
        }
      },

      async tsbtnChuyenDB_Click() {
        await this.loading(true)

        try {
          var json_data_fn_hcgphone_chuyendb = JSON.stringify({
            "donvi_id": this.donvi_id,
            "hdtb_id": this.hdtb_id,
            "loaihd": this.ddlLoaiHD,
            "may_cn": this.may_cn,
            "ngay_ht": this.dtpNgayHT,
            "nguoi_cn": this.ma_nd,
            "nhanvien_th": this.nhanvien_id,
            "phieu_id": this.phieu_id
          });
          var link_fn_hcgphone_chuyendb = "/web-ccdv/thicong_ghone/fn_hcgphone_chuyendb"
          const files_fn_hcgphone_chuyendb = await this.axios.post(link_fn_hcgphone_chuyendb, json_data_fn_hcgphone_chuyendb, {
            headers: {
              'Content-Type': 'application/json'
            }
          });
          if (files_fn_hcgphone_chuyendb.data.data && files_fn_hcgphone_chuyendb.data.error_code == 'BSS-00000000') {
            this.$toast.success("Cập nhật dữ liệu vào danh bạ thành công!")
            this.HienThiDanhSachHDTB()
          }
          else
            this.$toast.error("Có lỗi: " + files_fn_hcgphone_chuyendb.data.data)
        }
        catch (err) {
          this.$toast.error("Có lỗi : " + err.message)
        }

        await this.loading(false)
      },


      async tsbtnGiaoPhieu_Click() {
        var link = "/web-ccdv/HoanCongThuTienPhatSinh/LAY_HUONGGIAO_THEO_LUONG_ID?VLUONG_ID=" + this.luong_id + ""
        const files = await this.axios.get(link, {
          headers: {
            'Content-Type': 'application/json'
          }
        });
        if (files.data.data.length > 0) {
          let huonggiaoid = Number.parseInt(files.data.data[0].huonggiao_id)


          //await this.$router.push({
          //  path: '/contract/HandoverTicket',
          //  query: {
          //    huonggiao: huonggiaoid,
          //    magd: this.txtMaGD.trim()
          //  }
          //})


          this.popupComponent = () => import('@/modules/contract/setup/HandoverTicket/HandoverTicket');
          this.popupComponentName = "HandoverTicket";
          this.popupComponentData = {
            huonggiao: huonggiaoid,
            magd: this.txtMaGD.trim()
          }
          this.Popup('popupComponents');


        } else {
          this.$root.$toast.warning('Chưa có hướng giao được gán !')
        }
        await this.HienThiDanhSach()
      },

      closePopupTonPhieu() {
        this.$refs.popupTonPhieu.$refs.dialogObjPhieuTon.hide()
      },

      async TuDongGiaoViec() {
        var nhiemvu = "";
        if (this.tthd_id == TrangThaiHD.DIEUHANH_THI_CONG)
          nhiemvu = "Điều hành thi công";
        else if (this.tthd_id == TrangThaiHD.KHAIBAO_TONGDAI)
          nhiemvu = "Khai báo thông số";
        else if (this.tthd_id == TrangThaiHD.DANG_THI_CONG)
          nhiemvu = "Thi công";
        else if (this.tthd_id == TrangThaiHD.DOISOAT_HOSO)
          nhiemvu = "Đối soát hồ sơ";
        else if (this.tthd_id == TrangThaiHD.DANGCHO_HOANCONG)
          nhiemvu = "Đang chờ hoàn công";
        else nhiemvu = "";
        //var json_data_string = "[ { \"PHIEU_ID\": " + this.phieu_id + ", \"NHANVIEN_GIAO_ID\": " + this.ddlNguoiGiaoViec + ", \"NGAYGIAO\": '" + this.dtpNgayGV + "', \"NHANVIEN_TH_ID\": " + this.nhanvien_id + ", \"NHIEMVU\": '" + nhiemvu + "', \"GHICHU\": '" + this.txtGhiChu + "' } ]"
        var json_data_string = "[ { \"PHIEU_ID\": " + this.phieu_id + ", \"NHANVIEN_GIAO_ID\": " + this.ddlNguoiGiaoViec + ", \"NGAYGIAO\": '" + this.dtpNgayGV + "', \"NHANVIEN_TH_ID\": " + this.nhanvien_id + ", \"NHIEMVU\": '" + nhiemvu + "', \"GHICHU\": '' } ]"
        var json_data = JSON.stringify({
          "js_giaophieu_nv": json_data_string,
          "phieu_id": this.phieu_id
        });

        var link = "/web-ccdv/thicong_ghone/sp_tudong_giaoviec"
        const files = await this.axios.post(link, json_data, {
          headers: {
            'Content-Type': 'application/json'
          }
        });
        //if (files.data.error_code == "BSS-00000000") {
        //  this.$toast.success("Tự động giao việc thành công!");
        //}
        //else {
        //  this.$toast.error("Tự động giao việc lỗi");
        //}
      },

      getFullDate(date_var) {
        var fullYear = date_var.split("/")[2].toString()
        var fullMonth = date_var.split("/")[1].toString()
        if (Number(fullMonth) < 10)
          fullMonth = "0" + Number(fullMonth)
        var fullDay = date_var.split("/")[0].toString()
        if (Number(fullDay) < 10)
          fullDay = "0" + Number(fullDay)

        var ret = fullYear + fullMonth + fullDay
        debugger
        return ret
      },

      async CapNhat() {
        //debugger
        if (!this.KT_DuLieuNhap(this.luong_id))
          return
        if (this.PHAILAM(this.luong_id, "GIAOVIEC")) {
          if (this.options.danhsachNVTH_grid.length <= 0) {
            this.$toast.warning("Hãy nhập nhân viên thực hiện  !")
            return
          }
        }
        else {
          this.TuDongGiaoViec();
        }

        var ngay_sys = this.ngay_sys_select.toString().trim()
        var ngay_tt = this.ngay_tt_select.toString().trim()
        var ngay_ht = this.dtpNgayHT.split(" ")[0].toString()

        debugger
        if (this.loaihd_id != LoaiHopDong.KHOIPHUC_SD) {
          if (this.getFullDate(ngay_tt) > this.getFullDate(ngay_ht)) {//Nếu ngày thanh toán > ngày hoàn thành
            this.$toast.warning(" Thuê bao " + this.ma_tb_select + " có ngày thanh toán : " + ngay_tt + " \n Ngày hoàn thành không được phép nhỏ hơn ngày thanh toán")
            return
          }
        }
        debugger
        if (this.getFullDate(ngay_ht) > this.getFullDate(ngay_sys)) {//Nếu ngày thanh toán > ngày hoàn thành
          this.$toast.warning("Bạn không được phép chọn ngày hoàn thành lớn hơn ngày hiện tại")
          return
        }

        var json_data = JSON.stringify({
          "capnhat_gphone": 1,
          "cell1": this.txtCell1,
          "cell2": this.txtCell2,
          "giaoviec_td": 0,
          "hdtb_id": this.hdtb_id,
          "json_giaophieu_nv": "",
          "ngay_gv": this.dtpNgayGV,
          "ngay_th": this.dtpNgayHT,
          "nhanvien_gv_id": this.ddlNguoiGiaoViec,
          "nhanvien_th_id": this.nhanvien_id,
          "phieu_id": this.phieu_id,
          "simcard": this.txtSimcard,
          "toado": this.txtToaDo
        });
        var link = "/web-ccdv/thicong_ghone/fn_frm_hcgphone_capnhat"
        const files = await this.axios.post(link, json_data, {
          headers: {
            'Content-Type': 'application/json'
          }
        });
        if (files.data.data == "OK") {
          this.$toast.success("Cập nhật thành công");
        }
        else {
          this.$toast.error("Cập nhật lỗi");
        }

        if (this.ckbNgayHT == true)
          this.enableButton("tsbtnHoanCong")
        else
          this.disableButton("tsbtnHoanCong")

        //this.clearAll()
        await this.HienThiDanhSachHDTB()
        //bước này chọn lại row cũ

        await this.sleep(1000);
        await this.$refs.dsphieu.setCurrentSelectedRow(this.current);
        this.$forceUpdate()
      },

      KT_DuLieuNhap() {
        if (this.ckbNgayGV == false || this.dtpNgayGV == "") {
          this.$toast.error('Hãy chọn ngày giao việc!');
          return false
        }

        if (this.PHAILAM(this.luong_id, "GIAOVIEC")) {
          if (this.options.danhsachNVTH_grid.length <= 0) {
            this.$toast.warning("Hãy nhập nhân viên thực hiện  !")
            return false
          }
        }

        if (this.ckbNgayHT == false || this.dtpNgayHT == "") {
          this.$toast.error('Hãy nhập ngày thực hiện!');
          return false
        }

        if (this.PHAILAM(this.luong_id, "CAPNHAT_THONGSO_GPHONE")) {
          if (this.txtSimcard == "") {
            this.$toast.error("Bạn chưa nhập Sim Card");
            return false
          }
        }

        return true
      },

      KT_GiaoViec() {
        if (this.gview_DanhSach.list.length < 1) {
          this.$toast.error("Chưa có danh thuê bao. Bạn hãy kiểm tra lại! ");
          return false
        }
        if (this.ddlNguoiGiaoViec == "") {
          this.$toast.error("Hãy nhập nhân viên giao việc!");
          return false
        }
        if (this.ckbNgayGV == false || this.dtpNgayGV == "") {
          this.$toast.error("Hãy nhập ngày giao việc!");
          return false
        }
        return true
      },

      txtMaGD_KeyPress: function (event) {
        if (event.key == "Enter") {
          if (this.txtMaGD.trim() == "")
            return
          api.getDanhSachHDTBGrid(this.axios, {
            huonggiao_id: this.huonggiao_id,
            loai_id: this.optionPhieuMoi_TraLai,
            loaihd_id: this.ddlLoaiHD,
            ma_gd: this.txtMaGD,
            nhanvien_id: parseInt(this.nhanvien_id),
            tthd_id: 4
          })
            .then((res) => {
              this.gview_DanhSach.list = res.data.data ? res.data.data : [];
              if (this.gview_DanhSach.list.length < 1) {
                this.$toast.error("Không có dữ liệu hiển thị!");
              }
              this.loading(false);
            });
        }
      },

      txtMaTB_KeyPress: function (event) {
        if (event.key == "Enter") {
          if (this.txtMaTB.trim() == "")
            return
          api.getDanhSachHDTBGrid(this.axios, {
            huonggiao_id: this.huonggiao_id,
            loai_id: this.optionPhieuMoi_TraLai,
            loaihd_id: this.ddlLoaiHD,
            ma_gd: this.txtSoMay,
            nhanvien_id: parseInt(this.nhanvien_id),
            tthd_id: 4
          })
            .then((res) => {
              this.gview_DanhSach.list = res.data.data ? res.data.data : [];
              if (this.gview_DanhSach.list.length < 1) {
                this.$toast.error("Không có dữ liệu hiển thị!");
              }
              this.loading(false);
            });
        }
      },

      GiaoViec() {
        if (this.KT_GiaoViec == false) {
          return
        }
        this.$refs.formgiaphieu.init()
        this.$refs.popupGiaoPhieuNV.show();
      },

      giaoviec_success() {
        this.getDataDSNV()
        //this.getDanhsachThuebao();
      },

      chapnhan_gankhuvuc() {
        this.getDanhsachThuebao();
        this.$bvModal.hide('gankhuvuc')
      },
      async gankhuvuc() {
        this.$bvModal.show('gankhuvuc')
      },
      //Enum.DONGBO_GPHONE.DONGBO
      async KichHoat() {

        var json_data_get_app_config = JSON.stringify({
          "keyname": "DONGBO_GPHONE"
        });
        var link_get_app_config = "/web-hopdong/hopdong/get_app_config"
        const files_get_app_config = await this.axios.post(link_get_app_config, json_data_get_app_config, {
          headers: {
            'Content-Type': 'application/json'
          }
        });
        var DONGBO_GPHONE = false
        if (files_get_app_config.data.error_code != "BSS-00000000") {
          DONGBO_GPHONE = false
        }
        else {
          if (files_get_app_config.data.data.dongbo == "1" && files_get_app_config.data.data.keyvalue == "TRUE")
            DONGBO_GPHONE = true
        }


        if (DONGBO_GPHONE == true) {
          if (this.loaihd_id == LoaiHopDong.DAT_MOI || this.loaihd_id == LoaiHopDong.KHOIPHUC_SD) {
            this.$confirm("Bạn có chắc chắn kích hoạt số máy " + this.txtSoMay + " lên hệ thống CCBS Gphone ?", {
              confirmButtonText: "Có",
              cancelButtonText: "Không",
              type: "warning",
            }).then(async () => {
              var json_data_sp_hcgphone_laythongtin_kh = JSON.stringify({
                "loaitb_id": this.loaitb_id,
                "nguoidung_id": this.userId
              });
              var link_sp_hcgphone_laythongtin_kh = "/web-ccdv/thicong_ghone/sp_hcgphone_laythongtin_kh"
              const files_sp_hcgphone_laythongtin_kh = await this.axios.post(link_sp_hcgphone_laythongtin_kh, json_data_sp_hcgphone_laythongtin_kh, {
                headers: {
                  'Content-Type': 'application/json'
                }
              });
              if (files_sp_hcgphone_laythongtin_kh.data.data.length > 0) {
                let mavung = files_sp_hcgphone_laythongtin_kh.data.data[0].mavung_cd
                let userid = files_sp_hcgphone_laythongtin_kh.data.data[0].user_gphone
                let loaitb = files_sp_hcgphone_laythongtin_kh.data.data[0].ma_lhtb_vdc
                if (userid == "-1") {
                  this.$toast.warning("Người dùng chưa được gán user tác động hệ thống gphone.");
                  return;
                }

                var json_data_khoitao_tb = JSON.stringify(
                  {
                    vinput : {
                      "ghi_chu": this.txtGhiChu,
                      "loai_tb": loaitb,
                      "so_sim": this.txtSimcard,
                      "so_tb": mavung + this.txtSoMay
                    },
                    vtype: 7,
                    vhdtb_id: this.hdtb_id
                  }
                );
                var link_khoitao_tb = "web-hopdong/hopdong/fn_didong"
                const files_khoitao_tb = await this.axios.post(link_khoitao_tb, json_data_khoitao_tb, {
                  headers: {
                    'Content-Type': 'application/json'
                  }
                });
                debugger
                if (files_khoitao_tb.data.error_code == "BSS-00000000") {
                  var json_data_capnhat_ngaykh_status_v2_1 = JSON.stringify({
                    "hdtb_id": this.hdtb_id,
                    "ip_kh": "10.0.0.1",
                    "ngay_kh": this.dtpNgayGV,
                    "nguoi_kh": this.ma_nd,
                    "status": TRANGTHAI_DONGBO.DONGBO_CM
                  });
                  var link_capnhat_ngaykh_status_v2_1 = "/web-ccdv/khaibaoims/capnhat_ngaykh_status_v2"
                  const files_capnhat_ngaykh_status_v2_1 = await this.axios.post(link_capnhat_ngaykh_status_v2_1, json_data_capnhat_ngaykh_status_v2_1, {
                    headers: {
                      'Content-Type': 'application/json'
                    }
                  });

                  var json_data_khoamo_ic_oc_1 = JSON.stringify(
                    {
                      vinput : {
                        "ma_ldo": "",
                        "note": "Mo khi khoi tao moi",
                        "product": "SPS_PRODUCT_HLR_OC",
                        "so_tb": mavung + this.txtSoMay
                      },
                      vtype: 9,
                      vhdtb_id: this.hdtb_id
                    });
                  var link_khoamo_ic_oc_1 = "/web-hopdong/hopdong/fn_didong"
                  const files_khoamo_ic_oc_1 = await this.axios.post(link_khoamo_ic_oc_1, json_data_khoamo_ic_oc_1, {
                    headers: {
                      'Content-Type': 'application/json'
                    }
                  });
                  if (files_khoamo_ic_oc_1.data.error_code == "BSS-00000000") {
                    var json_data_khoamo_ic_oc_2 = JSON.stringify(
                    {
                      vinput : {
                      "ma_ldo": "",
                      "note": "Mo khi khoi tao moi",
                      "product": "SPS_PRODUCT_HLR_IC",
                      "so_tb": mavung + this.txtSoMay
                    },
                      vtype: 9,
                      vhdtb_id: this.hdtb_id
                    })

                    var link_khoamo_ic_oc_2 = "/web-hopdong/hopdong/fn_didong"
                    const files_khoamo_ic_oc_2 = await this.axios.post(link_khoamo_ic_oc_2, json_data_khoamo_ic_oc_2 , {
                      headers: {
                        'Content-Type': 'application/json'
                      }
                    });
                  }
                  this.$toast.success("Kích hoạt thành công !");
                }
                else {
                  this.$toast.error("Kích hoạt lỗi: " + files_khoitao_tb.data.message);
                  return
                }



              }
            })
          }
          if (this.loaihd_id == LoaiHopDong.DI_CHUYEN) {
            var json_data_capnhat_ngaykh_status_v2_2 = JSON.stringify({
              "hdtb_id": this.hdtb_id,
              "ip_kh": "10.0.0.1",
              "ngay_kh": this.dtpNgayGV,
              "nguoi_kh": this.ma_nd,
              "status": TRANGTHAI_DONGBO.DONGBO_CM
            });
            var link_capnhat_ngaykh_status_v2_2 = "/web-ccdv/khaibaoims/capnhat_ngaykh_status_v2"
            const files_capnhat_ngaykh_status_v2_2 = await this.axios.post(link_capnhat_ngaykh_status_v2_2, json_data_capnhat_ngaykh_status_v2_2, {
              headers: {
                'Content-Type': 'application/json'
              }
            });
            if (files_capnhat_ngaykh_status_v2_2.data.error_code == "BSS-00000000") {
              this.$toast.success("Kích hoạt thành công !");
              //Chọn lại row vừa cập nhật
            }
            else {
              this.$toast.error("Kích hoạt lỗi");
            }
          }
        }
        else {
          //this.dtpNgayGV.format("DD/MM/YYYY HH:mm:ss")
          var json_data_capnhat_ngaykh_status_v2_2 = JSON.stringify({
            "hdtb_id": this.hdtb_id,
            "ip_kh": "10.0.0.1",
            "ngay_kh": this.dtpNgayGV,
            "nguoi_kh": this.ma_nd,
            "status": TRANGTHAI_DONGBO.DONGBO_CM
          });
          var link_capnhat_ngaykh_status_v2_2 = "/web-ccdv/khaibaoims/capnhat_ngaykh_status_v2"
          const files_capnhat_ngaykh_status_v2_2 = await this.axios.post(link_capnhat_ngaykh_status_v2_2, json_data_capnhat_ngaykh_status_v2_2, {
            headers: {
              'Content-Type': 'application/json'
            }
          });
          if (files_capnhat_ngaykh_status_v2_2.data.error_code == "BSS-00000000") {
            this.$toast.success("Kích hoạt thành công !");
            //Chọn lại row vừa cập nhật
          }
          else {
            this.$toast.error("Kích hoạt lỗi");
          }
        }
        this.HienThiDanhSachHDTB()
      },

      async HoanCong() {
        if (this.ckbNgayHT == false) {
          this.$toast.warning("Phải cập nhật ngày hoà mạng !");
          return;
        }
        this.$confirm(`Bạn thật sự muốn hoàn công không ?`, {
          confirmButtonText: "Có",
          cancelButtonText: "Không",
          type: "warning",
        }).then(async () => {
          this.CapNhat()

          var ngay_sys = this.ngay_sys_select.toString().trim()
          var ngay_tt = this.ngay_tt_select.toString().trim()
          var ngay_ht = this.dtpNgayHT.split(" ")[0].toString()

          debugger
          if (this.loaihd_id != LoaiHopDong.KHOIPHUC_SD) {
            if (this.getFullDate(ngay_tt) > this.getFullDate(ngay_ht)) {//Nếu ngày thanh toán > ngày hoàn thành
              this.$toast.warning(" Thuê bao " + this.ma_tb_select + " có ngày thanh toán : " + ngay_tt + " \n Ngày hoàn thành không được phép nhỏ hơn ngày thanh toán")
              return
            }
          }
          debugger
          if (this.getFullDate(ngay_ht) > this.getFullDate(ngay_sys)) {//Nếu ngày thanh toán > ngày hoàn thành
            this.$toast.warning("Bạn không được phép chọn ngày hoàn thành lớn hơn ngày hiện tại")
            return
          }

          try {
            if (this.loaihd_id == LoaiHopDong.DAT_MOI) {

              var json_data_get_app_config = JSON.stringify({
                "keyname": "DONGBO_GPHONE"
              });
              var link_get_app_config = "/web-hopdong/hopdong/get_app_config"
              const files_get_app_config = await this.axios.post(link_get_app_config, json_data_get_app_config, {
                headers: {
                  'Content-Type': 'application/json'
                }
              });
              var DONGBO_GPHONE = false
              if (files_get_app_config.data.error_code != "BSS-00000000") {
                DONGBO_GPHONE = false
              }
              else {
                if (files_get_app_config.data.data.dongbo == "1" && files_get_app_config.data.data.keyvalue == "TRUE")
                  DONGBO_GPHONE = true
              }

              if (DONGBO_GPHONE == true) {
                var json_data_sp_hcgphone_laythongtin_kh = JSON.stringify({
                  "loaitb_id": this.loaitb_id,
                  "nguoidung_id": this.userId
                });
                var link_sp_hcgphone_laythongtin_kh = "/web-ccdv/thicong_ghone/sp_hcgphone_laythongtin_kh"
                const files_sp_hcgphone_laythongtin_kh = await this.axios.post(link_sp_hcgphone_laythongtin_kh, json_data_sp_hcgphone_laythongtin_kh, {
                  headers: {
                    'Content-Type': 'application/json'
                  }
                });
                if (files_sp_hcgphone_laythongtin_kh.data.data.length > 0) {
                  let mavung = files_sp_hcgphone_laythongtin_kh.data.data[0].mavung_cd
                  let userid = files_sp_hcgphone_laythongtin_kh.data.data[0].user_gphone
                  if (userid == "-1") {
                    this.$toast.warning("Người dùng chưa được gán user kết nối hệ thống gphone. Liên hệ Admin để gán quyền.");
                    return;
                  }

                  var json_data_gphone_reset_huy_laytt_tb = JSON.stringify({
                    "so_tb": mavung + this.txtSoMay
                  });
                  var link_gphone_reset_huy_laytt_tb = "/ccbs/gphone/gphone_reset_huy_laytt_tb"
                  const files_gphone_reset_huy_laytt_tb = await this.axios.post(link_gphone_reset_huy_laytt_tb, json_data_gphone_reset_huy_laytt_tb, {
                    headers: {
                      'Content-Type': 'application/json'
                    }
                  });
                  debugger
                  if (files_gphone_reset_huy_laytt_tb.data.data == null) {
                    this.$toast.error("Lỗi kết nối hệ thống CCBS !");
                    return;
                  }
                  if (files_gphone_reset_huy_laytt_tb.data.data.length <= 0) {
                    this.$toast.warning("Thuê bao gphone chưa có trên hệ thống Neo. Đề nghị kiểm tra lại.");
                    return;
                  }


                }
              }
            }

            var json_data = JSON.stringify({
              "capnhat_gphone": 1,
              "cell1": this.txtCell1,
              "cell2": this.txtCell2,
              "donvi_id": this.donvi_id,
              "hdtb_id": this.hdtb_id,
              "huonggiao_id": this.huonggiao_id,
              "ip_cn": "",
              "loaihd": this.ddlLoaiHD,
              "luong_id": this.luong_id,
              "may_cn": this.may_cn,
              "ngay_gv": this.dtpNgayGV,
              "ngay_th": this.dtpNgayHT,
              "nguoi_cn": this.userId,
              "nhanvien_gv_id": this.ddlNguoiGiaoViec,
              "nhanvien_th_id": this.nhanvien_th_id,
              "phieu_id": this.phieu_id,
              "simcard": this.txtSimcard,
              "toado": this.txtToaDo
            });
            //console.log(json_data)
            var link = "/web-ccdv/thicong_ghone/fn_hcgphone_hoancong"
            const files = await this.axios.post(link, json_data, {
              headers: {
                'Content-Type': 'application/json'
              }
            });
            if (files.data.data == "OK") {
              this.$toast.success("Hoàn công thành công!");
            }
            else {
              this.$toast.error("Hoàn công lỗi: " + files.data.data);
            }
            this.clearAll()
            this.HienThiDanhSachHDTB()
          }
          catch (err) {
            this.$toast.error(err.message);
          }
        })
      },

      setDisplayDropDownMenu() {
        this.isDisplayMenu = !this.isDisplayMenu
      },
      hideDropdownMenu(e) {
        if (e.target !== this.$refs.dropdownRefDots) {
          this.isDisplayMenu = false;
        }
      },

      successUpdateThayDoiKhuVucQuanLy() {
        this.$emit('successUpdateThayDoiKhuVucQuanLy')
      },

      async onAcceptKhuVuc(data) {

      },

      async InPhieu() {
        try {
          let vhdkhid = commonFn.MAP_ID(this.axios, 'hdkh_id', 'css.hd_thuebao', 'where hdtb_id=' + this.hdtb_id)
          if (vhdkhid == '-1') {
            this.$root.$toast.warning('Không tìm thấy thông tin khách hàng. Bạn hãy kiểm tra lại!')
            return
          }
          var seletedRows = this.gview_DanhSach.checked
          if (seletedRows.length == 0) {
            this.$toast.error("Bạn chưa chọn phiếu! ");
            return false;
          }

          let nhdkhid = []
          let nhdtbid = []
          let nphieuid = []
          nhdkhid = seletedRows.map((item) => item.hdkh_id)
          nhdtbid = seletedRows.map((item) => item.hdtb_id)
          nphieuid = seletedRows.map((item) => item.phieu_id)

          this.modelIn.n_phieu_id = nphieuid.join(',')
          this.modelIn.ma_gd = this.txtMaGD
          this.modelIn.n_hdkh_id = nhdkhid.join(',')
          this.modelIn.n_hdtb_id = nhdtbid.join(',')
          this.modelIn.nvth_id = -1
          this.modelIn.huonggiao_id = this.huonggiao_id
          this.modelIn.tentram_tb = ''
          console.log(this.modelIn)
          this.$refs.popupInBB.showModal()


        } catch (ex) {
          commonFn.showException(this, ex);
        }
      },

      focusItem(index, data) {
        this.current = index
      },

      sleep(ms) {
        return new Promise(resolve => setTimeout(resolve, ms));
      },






      getDate(strDate, format) {
        if (format) {
          return moment(strDate, format).toDate()
        } else {
          return moment(strDate, 'DD/MM/YYYY HH:mm:ss').toDate()
        }
      },

      formatDate(date, format) {
        if (format) {
          return moment(date).format(format)
        } else {
          return moment(date).format('DD/MM/YYYY HH:mm:ss')
        }
      },

      gview_DanhSach_actionComplete() {
        if (this.gview_DanhSach.list != null) {
          if (this.gview_DanhSach.list.length > 0) {
            let index = this.gview_DanhSach.list.findIndex(item => item.ma_tb === this.formProps_txtMaTB.trim())
            if (index >= 0) {
              this.$refs.dsphieu.$refs.grid.selectRow(index)
            } else {
              this.$refs.dsphieu.$refs.grid.selectRow(0)
            }
          }
        }

      },

      Popup(modalId) {
        this.$bvModal.show(modalId);
      },

      ClosePopup(modalId) {
        this.$bvModal.hide(modalId);
      },

      async popupClosed(val) {

      }

    }
  };

</script>
<style>
  .none-padding {
    padding: 0 !important;
  }

  .modal-dialog {
    border-radius: 0px !important;
    width: 100% !important;
    max-width: 1220px !important;
  }

  li.non-active a {
    color: #d3d3d3 !important;
  }

  .traphieu .page-content {
    position: unset;
  }

  .traphieu-content {
    width: 1200px;
  }
</style>


