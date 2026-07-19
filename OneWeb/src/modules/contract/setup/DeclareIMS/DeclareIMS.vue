<template>
  <div>
    <div id="declare-ims" class="breadcrumb-top">
      <breadcrumb />
    </div>
    <action-header
      :danh-sach-thao-tac="thongTinHuongGiao.danhSachThaoTac"
      :status-button="statusButton"
      @chuyenTo="btnChuyenToClick"
      @dauNoi="btnDauNoiClick"
      @doKiem="btnDoKiemClick"
      @doiCong="btnDoiCongClick"
      @duAn="btnDuAnClick"
      @ganKhuVuc="btnGanKhuVucClick"
      @giaoPhieu="btnGiaoPhieuClick"
      @giaoViec="btnGiaoViecClick"
      @henLapDat="btnHenLapDatClick"
      @hoanCong="btnHoanCongClick"
      @hoanThien="btnHoanThienClick"
      @inPhieu="btnInPhieuClick"
      @giaoHostCu="btnGiaoHostCuClick"
      @khoiPhucSo="btnKhoiPhucSoClick"
      @layThongTin="btnLayThongTinClick"
      @lienHe="btnLienHeClick"
      @thongSoKyThuat="btnthongSoKyThuatClick"
      @tonPhieu="btnTonPhieuClick"
      @traPhieu="btnTraPhieuClick"
      @update="btnUpdateClick"
      @vatTu="btnVatTuClick"
      @xuatFile="btnXuatFileClick"
      @huyKichHoat="btnHuyKichHoatClick"
      @kichHoat="btnKichHoatClick"
    />

    <div class="popup-body page-content">
      <form-a-thong-tin-chuyen-doi :form-thong-tin-chuyen-doi="formThongTinChuyenDoi"></form-a-thong-tin-chuyen-doi>
      <form-b-thong-tin-khach-hang ref="formThongTinKhachHang" :form-thong-tin-khach-hang="formThongTinKhachHang" :hop-dong-theu-bao-id="hopDongThueBaoId" :tag-form="tagForm" :thong-tin-huong-giao="thongTinHuongGiao" @quyTrinhChange="quyTrinhChange"></form-b-thong-tin-khach-hang>
      <form-c-thong-tin-hoan-cong
        ref="formThongTinHoanCong"
        :thueBaoIms="gridThueBaoIms.value"
        :danh-sach-thao-tac="thongTinHuongGiao.danhSachThaoTac"
        :don-vi-id="selectedIMS ? selectedIMS.donvi_id : -1"
        :form-thong-tin-hoan-cong="formThongTinHoanCong"
        :hdtb-id="selectedIMS ? selectedIMS.hdtb_id : -1"
        :kieuld-id="selectedIMS ? selectedIMS.kieuld_id : -1"
        :loai-hop-dong-id="formThongTinKhachHang.selectedLoaiHDPL"
        :loaitb-id="selectedIMS ? selectedIMS.loaitb_id : -1"
        :ma-thue-bao="formThongTinKhachHang.maThueBao"
        :phieu-id="selectedIMS ? selectedIMS.phieu_id : -1"
        :thue-bao-id="selectedIMS ? selectedIMS.thuebao_id : 0"
        :quytrinh-id="formThongTinKhachHang.selectedQuyTrinh"
        @capNhatPort="updateInfomationPortFromHoanCong"
      ></form-c-thong-tin-hoan-cong>
      <table-nhan-vien-dich-vu :danhSachDichVuDangKy="danhSachDichVuDangKy" :danhSachNhanVienThucHien="danhSachNhanVienThucHien" />
      <div class="box-form">
        <div class="legend-title">
          <div class="pull-left">Danh sách thuê bao IMS</div>
          <div class="pull-right">
            <div class="list-checks red">
              <div class="item">
                <div class="check-action">
                  <input v-model="checkedLoaiPhieu" :value="0" class="check" type="radio" />
                  <span class="name">Phiếu mới</span>
                </div>
              </div>
              <div class="item">
                <div class="check-action">
                  <input v-model="checkedLoaiPhieu" :value="1" class="check" type="radio" />
                  <span class="name">Phiếu trả lại</span>
                </div>
              </div>
              <div class="item">Tổng số: {{ gridThueBaoIms.selected.length }}/{{ gridThueBaoIms.list.length }}</div>
            </div>
          </div>
          <div class="clearfix"></div>
        </div>
        <!-- @actionComplete="actionComplete" -->
        <DataGrid
          ref="gridThueBaoIms"
          :selectionSettings="{ checkboxMode: 'ResetOnRowClick' }"
          v-bind:columns="gridThueBaoIms.cols"
          :showColumnCheckbox="true"
          v-bind:dataSource="gridThueBaoIms.list"
          :showFilter="true"
          @selectedItemsChanged="onGridThuebaoImsSelectedItemsChanged"
          @rowSelected="onGridThuebaoImsRowSelected"
          :allowPaging="true"
          :enablePagingServer="false"
          :totalRecords="gridThueBaoIms.list.length"
        >
        </DataGrid>
      </div>
    </div>
    <ejs-dialog ref="giaoPhieuNhanVien" :enableResize="true" :header="'Giao phiếu nhân viên'" :isModal="true" :target="'.main-wrapper'" :visible="false" height="800" showCloseIcon="true" width="60%">
      <giaophieunhanvien ref="formGiaoPhieu" :ngaygiao="popupGiaoViecModel.ngayGV" :nguoigv_id="popupGiaoViecModel.nguoiGV" :phieu_id="popupGiaoViecModel.phieuId" @giaoviec_success="giaoViecSuccess" />
    </ejs-dialog>

    <ton-phieu ref="tonPhieuGiaoModal" :phieu_id="popupTonPhieuModel.phieuId" :tthd_id="popupTonPhieuModel.tthdId" :vthuhoi="0" />

    <ejs-dialog ref="modalTraPhieu" :enableResize="true" :header="'Trả Phiếu'" :isModal="true" :target="'.main-wrapper'" :visible="false" height="800" showCloseIcon="true" width="60%" @close="closeModalTraPhieu">
      <frmTraPhieuTC ref="frmTraPhieuTC" />
    </ejs-dialog>

    <ExportDataModal ref="popupExportDataModal" :data="gridThueBaoIms.selected"></ExportDataModal>

    <chuyen-to ref="popupChuyenTo"></chuyen-to>

    <ejs-dialog ref="modalDoiCong" :enableResize="true" :header="'Đổi Cổng'" :isModal="true" :target="'.main-wrapper'" :visible="false" height="800" showCloseIcon="true" width="60%">
      <thay-doi-thong-so-internet ref="formThayDoiThongSoInternet"></thay-doi-thong-so-internet>
    </ejs-dialog>

    <DuAnHDTB v-if="!!selectedIMS" ref="modalDuAn" :vhdtb_id="selectedIMS.hdtb_id"></DuAnHDTB>
    <ket-qua-do-kiem v-if="!!selectedIMS" ref="modalKetQuaDoKiem" :vhdtb_id="selectedIMS.hdtb_id"></ket-qua-do-kiem>
    <TraCuuThongSoKTModal v-if="!!selectedIMS" ref="modalTraCuuThongSoKyThuat" :dichvuvt_id="selectedIMS.dichvuvt_id" :hdtb_id="selectedIMS.hdtb_id" :kieu="1" :ma_tb="selectedIMS.ma_tb" :thuebao_id="selectedIMS.thuebao_id || 0"> </TraCuuThongSoKTModal>

    <ThayDoiKhuVucQuanLyThueBaoModal
      v-if="!!selectedIMS"
      ref="modalThayDoiKhuVucQuanLy"
      :data="{
        quan_id: 0,
        phuong_id: 0,
        pho_id: 0,
        ap_id: 0,
        khu_id: 0,
        dacdiem_id: 0,
        khuvuc_id: 0,
        donviql_id: 0,
        hdtb_id: selectedIMS.hdtb_id
      }"
    ></ThayDoiKhuVucQuanLyThueBaoModal>
    <ThongTinHenKhachHangModal v-if="!!selectedIMS" ref="modalHenLapDat" :hdkh_id="this.selectedIMS.hdkh_id" />

    <ejs-dialog ref="modalLienHe" :enableResize="true" :header="'Liên Hệ'" :isModal="true" :target="'.main-wrapper'" :visible="false" height="800" showCloseIcon="true" width="60%">
      <thong-tin-lien-he :hdkh_id="hdtbId"></thong-tin-lien-he>
    </ejs-dialog>
    <in-bien-ban ref="modalInBienBan" :params="modalInBienBanData"></in-bien-ban>
    <CapVatTuTB ref="modalCapVatTuTB" :input-data="modalCapVatTuTBData"></CapVatTuTB>
    <ejs-dialog :isModal="true" :enableResize="true" :resizeHandles="['All']" :visible="false" ref="modalDauNoiThueBao" :header="'Đấu nối thuê bao vào tuyến cáp'" showCloseIcon="true" target=".main-wrapper" height="800" width="60%" allowDragging="true" @close="DauNoiThueBao_OnClose">
      <div>
        <DauNoiThueBao v-if="dauNoiThueBaoDialog" :object="dataPopupDauNoiThueBao" @NHAP_CAP_THUEBAO="onSuccessNhapCapThueBao" />
      </div>
    </ejs-dialog>
    <ejs-dialog
      ref="ejsDialog"
      :visible="false"
      :isModal="true"
      target="#declare-ims"
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
        <frmHCIMSKickHoat v-if="ejsDialog.name == 'frmHCIMSKickHoat'" ref="frmHCIMSKickHoat" v-bind="frmHCIMSKickHoat.input" @tsbtnChapNhan_Clicked="frmHCIMSKickHoat_tsbtnChapNhan_Clicked" @tsbtnHuy_Clicked="$refs.ejsDialog.hide()" />
      </div>
    </ejs-dialog>
  </div>
</template>

<script>
import ActionHeader from './part/ActionHeader.vue'
import FormAThongTinChuyenDoi from './part/FormAThongTinChuyenDoi.vue'
import FormBThongTinKhachHang from './part/FormBThongTinKhachHang.vue'
import FormCThongTinHoanCong from './part/FormCThongTinHoanCong.vue'
import TableIms from './part/TableIms.vue'
import TableNhanVienDichVu from './part/TableNhanVienDichVu.vue'
import giaophieunhanvien from '../DeclareLandline/popup/giaophieunhanvien'
import TonPhieu from '../Payment/components/TonPhieu'
import frmTraPhieuTC from '../ReturnInstallTicket/frmTraPhieuTC'
import ExportDataModal from '@/modules/contract/profile/ExportData/ExportDataModal.vue'
import DauNoiThueBao from '@/modules/CABMAN/DauNoiThueBao'
import ChuyenTo from '@/modules/contract/profile/CompleteProfileChangeAccount/Popups/ChuyenTo.vue'
import ThayDoiThongSoInternet from '@/modules/EXTS/QUANTRI/ThayDoiThongSoInternet/index.vue'
import DuAnHDTB from '@/modules/contract/setup/ChangeServiceOMCVIPCM/popups/DuAnHDTB/DuAnHDTB.vue'
import KetQuaDoKiem from '@/modules/contract/setup/ChangeServiceOMCVIPCM/popups/KetQuaDoKiem/KetQuaDoKiem.vue'
import TraCuuThongSoKTModal from '../DeclareServiceSpeedITSecure/popups/TraCuuThongSoKT/TraCuuThongSoKTModal'
import ThayDoiKhuVucQuanLyThueBaoModal from '../SurveyRequest/popups/ThayDoiKhuVucQuanLyThueBao/ThayDoiKhuVucQuanLyThueBaoModal.vue'
import ThongTinHenKhachHangModal from '@/modules/contract/setup/SurveyRequest/popups/ThongTinHenKhachHang/ThongTinHenKhachHangModal.vue'
import ThongTinLienHe from '@/modules/contract/setup/ChangeContractOwner/popup/ThongTinLienHe.vue'
import InBienBan from '@/modules/print/InBienBan/InBienBan.vue'
import CapVatTuTB from '../../complete_profile/CompleteProfileChangeTSMyTV/popups/CapVatTuTB'
import breadcrumb from '@/components/breadcrumb'

import { DichVuVienThong, KieuHopDong, LoaiHopDong, TrangThaiDongBo, TrangThaiPH, TrangThaiPort, TrangThaiHD, LoaiHinhTB } from './consts'

import * as ims from './api/imsService'

import { getNhanVienTiepThi, getKenhThu, getTongTien } from './api/apiThongTinChuyenDoi'

import { getThongTinLapDatTheoYeuCau, getThongTinLapKem, getThongTinNhanVienYC } from './api/apiThongTinKhachHang'
import { getDanhSachThongTinKyThuat, getLyDoTra, getTenTramTheoIdDonVi, getThongTinIdDonVi, getThongTinLienHe, getDanhBaTheoMaTB, getDSHopDongTheoHDTBID } from './api/apiThongTinHoanCong'
import {
  getNhanVienTheoMaPhieu,
  getDanhSachDichVuDangKy,
  getDanhSachThamSoMacDinh,
  checkTramVatTu,
  checkPort,
  checkConditionPort,
  updateStatusPortNotUse,
  updateDanhSachMaThueBaoTheoPort,
  updateStatusPortIdNotUse,
  updateThongTinPortIMS,
  updateThongTinCapIMS,
  getDanhSachHuongGiaoKhoiPhucSo,
  updateKhoiPhucSo,
  getHuongGiaoTheoQuyTrinh,
  getHuongGiaoLan2,
  layThamSoMacDinh,
  fetchDanhSachIMS,
  getDanhSachPhieuKiemTraChuyenTo,
  queryData,
  hoanThienAPI,
  hoanCongAPI,
  kiemTraGiaoOCM,
  updateGiaoViecGopAPI,
  huyKichHoatIMS,
  capNhatThongTinPhieuIMS,
  giaophieu_td_host_thuhoi_1
} from './api'
import { formatDateToParam, getDateFromString, reformatDateTime, stringToDate, toDay } from './helper/dateHelper'
import {
  CAPNHAT_NGAYKH_STATUS_V2,
  capNhatNgayHoanThanh,
  capNhatNgayKichHoat,
  capNhatSoLuongCuocGoiIP,
  capNhatStatusDongBoHopDong,
  checkTaoDBThang,
  DatMoiThueBao,
  getDataTable,
  getLoaiHinhThueBao,
  getThongTinKichHoatLoaiHinhThueBao,
  getThongTinKichHoatThueBao,
  getThongTinLoaiHinhThueBao
} from './api/kichHoatIMS'
import { DongBoTestAPI, HuyThueBao, KhoiPhucThueBao, KhoiTaoThueBao_siptrunk, ThanhLyDuongTrunk, ThanhLyThueBao, thayDoiDVGT, ThayDoiDVGT_Danhba, ThayDoiDVGT_Siptrunk } from './api/imsService'
import getRandomText from './helper/getRandomText'
import frmHCIMSKickHoat from './components/frmHCIMSKickHoat'
import api from './api/new-api'

const PHIEU_MOI = 0
// const PHIEU_TRA_LAI = 1
export default {
  name: 'DeclareIMS',
  components: {
    breadcrumb,
    ActionHeader,
    FormAThongTinChuyenDoi,
    FormBThongTinKhachHang,
    FormCThongTinHoanCong,
    TableIms,
    TableNhanVienDichVu,
    giaophieunhanvien,
    TonPhieu,
    frmTraPhieuTC,
    ExportDataModal,
    DauNoiThueBao,
    ChuyenTo,
    ThayDoiThongSoInternet,
    DuAnHDTB,
    TraCuuThongSoKTModal,
    ThayDoiKhuVucQuanLyThueBaoModal,
    ThongTinHenKhachHangModal,
    ThongTinLienHe,
    InBienBan,
    KetQuaDoKiem,
    CapVatTuTB,
    frmHCIMSKickHoat
  },
  props: ['isPopup', 'Tag', '_matb_link', '_vquytrinh_id'],
  async created() {
    this.handleInput()

    console.log('tag')
    console.log(this.tag)
    // parse tagForm
    if (this.tag) {
      const words = this.tag.split('+')
      if (words.length >= 1) {
        this.tagForm.tthd_id = Number(words[0])
      }
      if (words.length >= 2) {
        this.tagForm.dsloaihd_id = words[1].replaceAll(';', ',')
      }
      if (words.length >= 3) {
        this.tagForm.dsdichvuvt_id = words[2].replaceAll(';', ',')
      }
      if (words.length >= 4) {
        this.tagForm.flagTSKT = words[3].replaceAll(';', ',')
      }
      if (words.length >= 5) {
        this.tagForm.flagTTTB_IMS = Number(words[4])
      }
    }
    this.danhSachThamSoMacDinh = await getDanhSachThamSoMacDinh(this.axios)
    console.log('this.thongTinHuongGiao.danhSachThaoTac')
    console.log(this.thongTinHuongGiao.danhSachThaoTac)
  },
  data: () => ({
    tag: '',
    tagForm: {
      tthd_id: TrangThaiHD.DANG_THI_CONG,
      dsloaihd_id: '1',
      dsdichvuvt_id: '11',
      flagTSKT: '',
      flagTTTB_IMS: 0
    },
    danhSachIMS: [],
    gridThueBaoIms: {
      cols: [
        { fieldName: 'ma_tb', headerText: 'Số máy', allowFiltering: true },
        // { fieldName: 'phieu_id', headerText: 'Mã TB', allowFiltering: true },
        { fieldName: 'ma_tb_cha', headerText: 'Số chính', allowFiltering: true },
        { fieldName: 'ten_tb', headerText: 'Tên thuê bao', allowFiltering: true },
        { fieldName: 'diachi_ld', headerText: 'Địa chỉ lắp đặt', allowFiltering: true },
        { fieldName: 'ma_kv', headerText: 'Mã khu vực', allowFiltering: true },
        { fieldName: 'ten_kv', headerText: 'Tên khu vực', allowFiltering: true },
        { fieldName: 'ngay_yc', headerText: 'Ngày YC', allowFiltering: true },
        { fieldName: 'dv_hen', headerText: 'Đơn vị hẹn', allowFiltering: true },
        { fieldName: 'giohen_tu', headerText: 'Hẹn từ', allowFiltering: true },
        { fieldName: 'giohen_den', headerText: 'Hẹn đến', allowFiltering: true },
        { fieldName: 'GOI_KT', headerText: 'Gói KT', allowFiltering: true },
        { fieldName: 'GIO_CT', headerText: 'Giờ CT', allowFiltering: true },
        { fieldName: 'GIO_CONLAI', headerText: 'Giờ còn lại', allowFiltering: true },
        { fieldName: 'ma_gd', headerText: 'Mã GĐ', allowFiltering: true },
        { fieldName: 'cl_luu_in', headerText: 'Trạng thái in', allowFiltering: true },
        { fieldName: 'ngay_ht', headerText: 'Ngày BG', allowFiltering: true },
        { fieldName: 'ngay_ht_gp', headerText: 'Ngày thực hiện', allowFiltering: true },
        { fieldName: 'ten_status', headerText: 'Trạng thái', allowFiltering: true },
        { fieldName: 'dv_giao', headerText: 'Đơn vi giao', allowFiltering: true },
        { fieldName: 'LYDOTON_TC', headerText: 'Lý do tồn', allowFiltering: true },
        { fieldName: 'GHICHU_TON', headerText: 'Ghi chú tồn', allowFiltering: true },
        { fieldName: 'ghichu', headerText: 'Ghi chú', allowFiltering: true }
      ],
      list: [],
      selected: [],
      value: null
    },
    countSelectedIMS: 0,
    countTotalIMS: 0,
    checkedLoaiPhieu: PHIEU_MOI,
    statusButton: {
      isEnableButtonHoanCong: false,
      isEnableButtonGiaoPhieu: true,
      isEnableButtonKichHoat: true,
      isShowButtonLienHe: false,
      isEnableButtonHuyKichHoat: false
    },
    formThongTinChuyenDoi: {
      selectedKenhThu: null,
      selectedKieuHopDong: null,
      nhanVienTiepThi: '',
      tongTien: ''
    },
    formThongTinKhachHang: {
      selectedQuyTrinh: 13,
      selectedLoaiHDPL: null,
      selectedLoaiHinh: null,
      maGiaoDich: '',
      maThueBao: '',
      tenThueBao: '',
      tenKhuVuc: '',
      loaiHinh: '',
      tenKieuLD: '',
      thueBaoLapKem: '',
      nhanVienYC: '',
      diaChiThueBao: '',
      diaChiLapDat: '',
      diaChiLapDatCu: '',
      checkedLapDatTheoYeuCau: false
    },
    formThongTinHoanCong: {
      selectedLoaiCap: null,
      selectedBras: null,
      selectedLAM: null,
      selectedThietBi: null,
      selectedNguoiGV: null,
      ngayHT: null,
      ngayGV: null,
      username: '',
      password: '',
      noiDungTra: '',
      lyDoTra: '',
      maTruyCap: '',
      noiDungTH: '',
      tuyenCap: '',
      lienTu: '',
      tuCapNgon: '',
      tuCapGoc: '',
      doiCapNgon: '',
      doiCapGoc: '',
      hopCapNgon: '',
      capGoc: '',
      capNgon: '',
      cuLy: 0,
      soLuongCuocGoi: '',
      selectedChiTietDichVu: 0,
      IPKhachHang: '',
      noiDungGiao: '',
      ONUPass: '',
      port: '',
      portMdf: '',
      slot: '',
      VPI: '',
      VCI: '',
      ADSLPort: '',
      SvLan: '',
      CvLan: '',
      system: '',
      rack: '',
      shelf: '',
      tramThietBi: '',
      SLID: '',
      enableButtonChonPortThuCong: false,
      enableButtonChonPortTuDong: false,
      enableNgayHT: false,
      enableNgayGV: false,
      enableCheckboxNgayHT: false,
      enableCheckboxNgayGV: false,
      checkedNgayHT: false,
      checkedNgayGV: false,
      // cập nhật lable Thông tin liên hệ
      thongTinLienHe: '',
      // nếu không dùng CAP MAN V2 (2.0) thì hiển thị thông tin cũ (hiển thị mã truy cập, tủ cáp gốc.....
      // nếu dùng CAP MAN V2 (2.0) thì hiển thị thông số lấy từ API (hiển thị raw html)
      isUsingCapMan: true,
      danhSachThongTinKyThuat: [],

      // các thông tin này được set lại khi chọn port hoặc chọn phiếu IMS khác
      portId: 0,
      vciVpiId: 0,
      tramThietBiId: 0,
      // Thông tin này dùng khi chọn port
      thongTinDonVi: 0
    },
    selectedIMS: null,
    thongTinHuongGiao: {
      luong_id: 0,
      huonggiao_id: 0,
      huonggiao: 'Hoàn công IMS',
      danhSachThaoTac: []
    },
    danhSachNhanVienThucHien: [],
    danhSachDichVuDangKy: [],
    danhSachThamSoMacDinh: [],
    isShowPopupGiaoViec: false,
    popupGiaoViecModel: {
      ngayGV: '',
      nguoiGV: 0,
      phieuId: 0
    },
    popupTonPhieuModel: {
      phieuId: 0,
      tthdId: 0
    },
    dataPopupDauNoiThueBao: {
      thueBaoId: 0,
      kieu: 1
    },
    modalInBienBanData: {
      n_phieu_id: '',
      ma_gd: '',
      n_hdkh_id: '',
      n_hdtb_id: '',
      nvth_id: -1,
      huonggiao_id: 0,
      tentram_tb: ''
    },
    modalCapVatTuTBData: {
      phieu_id: 0,
      loaihd_id: 0,
      hdtb_id: 0,
      thuebao_id: 0,
      vdvvt_id: 0,
      loaitb_id: 0,
      vma_tbcur: 0,
      baohong_id: 0
    },
    // dành riêng cho popup Thông tin liên hệ
    hdtbId: 0,
    dauNoiThueBaoDialog: false,
    kieuXacThuc: 'user_pass',
    //
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
        animationSettings: { effect: 'Fade' }
      }
    },
    frmHCIMSKickHoat: { visible: false, input: {} }
  }),
  computed: {
    hopDongThueBaoId() {
      return this.selectedIMS ? this.selectedIMS.hdtb_id : -1
    }
  },
  methods: {
    async handleInput() {
      // let props = {
      //   isPopup: false,
      //   Tag: '3+1;3;6;30+11',
      //   _matb_link: '',
      //   _vquytrinh_id: 11730
      // }
      if (this.isPopup) {
        this.tag = this.Tag
        this.formThongTinKhachHang.selectedQuyTrinh = this._vquytrinh_id
      } else {
        this.tag = this.$route.query.tag
      }
    },
    async quyTrinhChange() {
      if (!this.PHAILAM('GIAOVIEC')) {
        this.formThongTinHoanCong.enableCheckboxNgayHT = true
        this.formThongTinHoanCong.enableCheckboxNgayGV = true
        this.formThongTinHoanCong.ngayHT = new Date()
        this.formThongTinHoanCong.ngayGV = new Date()
      }
      await this.loadDataTableIMS()
      if (this.gridThueBaoIms.list.length > 0) {
        await this.selectedIMSChange(this.gridThueBaoIms.list[0])
      }
    },

    btnLayThongTinClick() {
      this.loadDataTableIMS()
    },

    async loadDataTableIMS(phieuId) {
      // reset 2 status button to false
      this.formThongTinHoanCong.enableButtonChonPortThuCong = false
      this.formThongTinHoanCong.enableButtonChonPortTuDong = false
      //

      this.loading(true)
      const param = {
        dichvuvt_id: this.tagForm.dsdichvuvt_id,
        tthd_id: this.tagForm.tthd_id,
        ma_gd: '', // fix = ""
        phieu_id: 0, // fixed 0
        loaihd_id: this.formThongTinKhachHang.selectedLoaiHDPL,
        huonggiao_id: this.thongTinHuongGiao.huonggiao_id,
        loai_id: this.checkedLoaiPhieu // check Phiếu mới = 0/ Phiếu trả lại = 1
      }
      const lst = await fetchDanhSachIMS(this.axios, param)
      if (lst.length === 0) {
        this.$toast.error('Không có dữ liệu danh sách thuê bao IMS.')
        this.selectedIMS = null
        this.gridThueBaoIms.list = []
        this.loading(false)
        return
      }
      this.gridThueBaoIms.list = lst.map((ims) => {
        ims.isSelected = false
        return ims
      })
      console.log('loadDataTableIMS')
      console.log(phieuId)

      //
      const ims = this.gridThueBaoIms.list.find((e) => e.phieu_id === phieuId)
      this.selectedIMS = null

      await this.hienThiThongTinThueBao(ims || this.gridThueBaoIms.list[0])
      console.log('selectRow')
      console.log(this.gridThueBaoIms.list)
      console.log(this.gridThueBaoIms.list.findIndex((e) => e.phieu_id == phieuId))
      var findIndex = this.gridThueBaoIms.list.findIndex((e) => e.phieu_id == phieuId)
      this.$refs.gridThueBaoIms.selectRow(findIndex)
    },

    selectedIMSChange(data) {
      // this.$nextTick(() => {
      //   this.countSelectedIMS = this.$refs.tableIMS.getSelectedRecords().length
      //   this.countTotalIMS = this.danhSachIMS.length
      // })
      const currentHdtbId = this.selectedIMS ? this.selectedIMS.hdtb_id : null
      if (currentHdtbId === data.hdtb_id) return
      this.hienThiThongTinThueBao(data)
    },
    async hienThiThongTinThueBao(data) {
      this.resetPage()
      const dateNow = Date.now()

      this.selectedIMS = JSON.parse(JSON.stringify(data))
      this.loading(true)
      // form thông tin chuyển đổi
      if (!this.selectedIMS.kieuhd_id) {
        this.formThongTinChuyenDoi.selectedKieuHopDong = KieuHopDong.TAI_NHA
      } else {
        this.formThongTinChuyenDoi.selectedKieuHopDong = this.selectedIMS.kieuhd_id
      }

      await Promise.all([
        /* 0 */ getNhanVienTiepThi(this.axios, this.selectedIMS.ctv_id),
        /* 1 */ getKenhThu(this.axios, this.selectedIMS.hdkh_id),
        /* 2 */ getTongTien(this.axios, this.selectedIMS.hdkh_id),
        // get api thông tin khách hàng
        /* 3 */ getThongTinLapKem(this.axios, {
          hdtb_id: this.selectedIMS.hdtb_id,
          madoicap: this.selectedIMS.madoicap
        }),
        /* 4 */ getThongTinNhanVienYC(this.axios, this.selectedIMS.nhanvien_id),
        /* 5 */ getThongTinLapDatTheoYeuCau(this.axios, this.selectedIMS.hdtb_id),
        // thông tin hoàn công
        /* 6 */ getLyDoTra(this.axios, this.selectedIMS.lydotra_con_id),
        /* 7 */ getThongTinLienHe(this.axios, this.selectedIMS.hdkh_id),
        /* 8 */ getTenTramTheoIdDonVi(this.axios, this.selectedIMS.tramtb_id),
        // table nhan viên thực hiện
        /* 9 */ getNhanVienTheoMaPhieu(this.axios, this.selectedIMS.phieu_id),
        // table dịch vụ đăng ký
        /* 10 */ getDanhSachDichVuDangKy(this.axios, this.selectedIMS.hdtb_id),
        // lấy donvi_id, dùng khi chọn port
        /* 11 */ getThongTinIdDonVi(this.axios, this.selectedIMS.hdtb_id)
      ]).then((response) => {
        this.formThongTinChuyenDoi.nhanVienTiepThi = response[0]
        this.formThongTinChuyenDoi.selectedKenhThu = response[1]
        this.formThongTinChuyenDoi.tongTien = response[2]

        // thông tin khách hàng
        this.formThongTinKhachHang.thueBaoLapKem = response[3] || ''
        this.formThongTinKhachHang.nhanVienYC = response[4]
        this.formThongTinKhachHang.checkedLapDatTheoYeuCau = response[5]

        // thông tin hoàn công
        this.formThongTinHoanCong.lyDoTra = response[6]
        this.formThongTinHoanCong.thongTinLienHe = response[7] || ''
        this.formThongTinHoanCong.tramThietBi = response[8] || ''
        this.danhSachNhanVienThucHien = response[9]
        // xử lý thông tin trong hàm HienThiDSNV(ds) của code C#
        if (this.danhSachNhanVienThucHien && this.danhSachNhanVienThucHien.length > 0) {
          this.formThongTinHoanCong.checkedNgayGV = true

          this.formThongTinHoanCong.ngayGV = stringToDate(this.danhSachNhanVienThucHien[0].ngaygiao)
          this.formThongTinHoanCong.selectedNguoiGV = Number(this.danhSachNhanVienThucHien[0].nhanvien_giao_id) || -1
          // TODO: 2 biến này dùng trong hàm in phiếu
          // nhanvien_tc_id = Convert.ToInt32(ds.Tables[0].Rows[0]["nhanvien_th_id"]);
          // congviec_th = ds.Tables[0].Rows[0]["nhiemvu"];
        } else {
          this.formThongTinHoanCong.checkedNgayGV = this.PHAILAM('GIAOVIEC')
          this.formThongTinHoanCong.ngayGV = new Date(dateNow)
          this.formThongTinHoanCong.selectedNguoiGV = Number(this.$root.token.getNhanVienID())
        }

        this.danhSachDichVuDangKy = response[10]
        if (response[11] && response[11].length > 0) {
          this.formThongTinHoanCong.thongTinDonVi = response[11][0].donvi_id
        }
      })

      // thông tin khách hàng
      this.formThongTinKhachHang.maGiaoDich = this.selectedIMS.ma_gd
      this.formThongTinKhachHang.maThueBao = this.selectedIMS.ma_tb || ''
      this.formThongTinKhachHang.tenThueBao = this.selectedIMS.ten_tb
      this.formThongTinKhachHang.tenKhuVuc = this.selectedIMS.ten_kv
      this.formThongTinKhachHang.selectedLoaiHinh = this.selectedIMS.loaitb_id
      this.formThongTinKhachHang.tenKieuLD = this.selectedIMS.ten_kieuld
      this.formThongTinKhachHang.diaChiLapDatCu = this.selectedIMS.diachild_cu
      this.formThongTinKhachHang.diaChiLapDat = this.selectedIMS.diachi_ld
      this.formThongTinKhachHang.diaChiThueBao = this.selectedIMS.diachi_tb

      // region <thông tin hoàn công>

      // region <Theo code C# thì phần sau đây gọi là dữ liệu mới>
      this.formThongTinHoanCong.maTruyCap = this.selectedIMS.matb_tn
      this.formThongTinHoanCong.noiDungTra = this.selectedIMS.nd_tra_con
      this.formThongTinHoanCong.noiDungTH = this.selectedIMS.nd_thuchien
      this.formThongTinHoanCong.tuyenCap = this.selectedIMS.tuyencap
      this.formThongTinHoanCong.lienTu = this.selectedIMS.lientu
      this.formThongTinHoanCong.tuCapGoc = this.selectedIMS.tucap_goc
      this.formThongTinHoanCong.tuCapNgon = this.selectedIMS.tucap_p
      this.formThongTinHoanCong.doiCapNgon = this.selectedIMS.doicap_p !== '-1' ? this.selectedIMS.doicap_p : ''
      this.formThongTinHoanCong.doiCapGoc = this.selectedIMS.doicap_goc !== '-1' ? this.selectedIMS.doicap_goc : ''
      this.formThongTinHoanCong.hopCapNgon = this.selectedIMS.hopcap_p
      this.formThongTinHoanCong.selectedLoaiCap = this.selectedIMS.loaicap_id || null
      this.formThongTinHoanCong.capGoc = this.selectedIMS.cap_goc
      this.formThongTinHoanCong.capNgon = this.selectedIMS.cap_p
      // endregion

      this.formThongTinHoanCong.cuLy = this.selectedIMS.culy ? Number(this.selectedIMS.culy) : 0
      this.formThongTinHoanCong.soLuongCuocGoi = this.selectedIMS.sl_cuocgoi || ''
      this.formThongTinHoanCong.selectedChiTietDichVu = this.selectedIMS.ctdv_id || 0
      this.formThongTinHoanCong.IPKhachHang = this.selectedIMS.ip || ''
      this.formThongTinHoanCong.noiDungGiao = this.selectedIMS.nd_giao || ''

      // region <Thông tin thiết bị MiniDSLam>
      console.log('this.selectedIMS')
      console.log(this.selectedIMS)
      this.formThongTinHoanCong.selectedBras = this.selectedIMS.bras_id || null
      this.formThongTinHoanCong.selectedLAM = this.selectedIMS.dslam_id || null
      this.formThongTinHoanCong.ONUPass = this.selectedIMS.onu_password || ''
      this.formThongTinHoanCong.port = this.selectedIMS.port || ''
      this.formThongTinHoanCong.portId = this.selectedIMS.port_id || 0
      this.formThongTinHoanCong.tramThietBiId = this.selectedIMS.tramtb_id || 0
      this.formThongTinHoanCong.portMdf = this.selectedIMS.port_mdf || ''
      this.formThongTinHoanCong.slot = this.selectedIMS.slot || ''
      this.formThongTinHoanCong.VPI = this.selectedIMS.vpi || ''
      this.formThongTinHoanCong.vciVpiId = this.selectedIMS.vci_vpi_id || 0
      this.formThongTinHoanCong.VCI = this.selectedIMS.vci || ''
      this.formThongTinHoanCong.ADSLPort = this.selectedIMS.adsl_port || ''
      this.formThongTinHoanCong.CvLan = this.selectedIMS.cvlan || ''
      this.formThongTinHoanCong.SvLan = this.selectedIMS.svlan || ''
      this.formThongTinHoanCong.system = this.selectedIMS.system || ''
      this.formThongTinHoanCong.rack = this.selectedIMS.rack || ''
      this.formThongTinHoanCong.shelf = this.selectedIMS.shelf || ''
      // endregion <thông tin thiết bị MiniDSLam>

      // region <thông tin cấu hình trên VTN>
      this.formThongTinHoanCong.username = this.selectedIMS.username
      this.formThongTinHoanCong.password = this.selectedIMS.password
      this.formThongTinHoanCong.selectedThietBi = this.selectedIMS.thietbi_id || null
      this.formThongTinHoanCong.SLID = this.selectedIMS.SLID || ''
      // this.formThongTinHoanCong.selectedNguoiGV = this.selectedIMS.nhanvien_giao_id || null
      // endtrgion <thông tin cấu hình trên VTN>

      // endregion <form thông tin hoàn công>
      if (!this.selectedIMS.ngay_ht_gp) {
        this.formThongTinHoanCong.ngayHT = new Date(dateNow + 30000)
      } else {
        this.formThongTinHoanCong.ngayHT = stringToDate(this.selectedIMS.ngay_ht_gp)
      }
      await this.showThongSoKyThuat()

      // Đoạn này xử lý trực tiếp trong ActionHeader
      // tsbtnHuyKH.Visible = false;
      // if ((PHAILAM(luong_id, "TSBTNHUYKH")))
      // {
      //   tsbtnHuyKH.Visible = true;
      // }

      this.statusButton.isShowButtonLienHe = this.PHAILAM('TSBTNTHONGTINLH')
      if (this.selectedIMS.status !== null || this.status !== undefined) {
        this.statusButton.isEnableButtonHuyKichHoat = this.selectedIMS.status === TrangThaiDongBo.DONGBO_CM
      } else {
        this.statusButton.isEnableButtonHuyKichHoat = false
      }

      this.formThongTinHoanCong.enableNgayGV = true
      this.formThongTinHoanCong.checkedNgayGV = true
      if (this.PHAILAM('KICHHOAT_TD')) {
        if (this.selectedIMS.ngay_ht_gp && this.selectedIMS.ngay_ht_gp.trim()) {
          if (this.selectedIMS.status === TrangThaiDongBo.DONGBO_CM) {
            this.statusButton.isEnableButtonGiaoPhieu = true
            this.statusButton.isEnableButtonKichHoat = false
            this.formThongTinHoanCong.enableButtonChonPortThuCong = false
            this.formThongTinHoanCong.enableButtonChonPortTuDong = false
          } else {
            this.statusButton.isEnableButtonGiaoPhieu = false
            this.statusButton.isEnableButtonKichHoat = true
            this.formThongTinHoanCong.enableButtonChonPortThuCong = true
            this.formThongTinHoanCong.enableButtonChonPortTuDong = true
          }
        } else {
          this.statusButton.isEnableButtonGiaoPhieu = false
          this.statusButton.isEnableButtonKichHoat = false
          this.formThongTinHoanCong.enableButtonChonPortThuCong = true
          this.formThongTinHoanCong.enableButtonChonPortTuDong = true
        }
      }
      if (this.PHAILAM('CAPNHAT_PORT')) {
        this.formThongTinHoanCong.enableButtonChonPortThuCong = true
      }
      if (this.PHAILAM('CAPNHAT_PORT_TUDONG')) {
        this.formThongTinHoanCong.enableButtonChonPortTuDong = true
      }

      if (this.PHAILAM('GIAOPHIEU')) {
        if (this.selectedIMS.ngay_ht_gp && this.selectedIMS.ngay_ht_gp.trim()) {
          if (this.PHAILAM('KICHHOAT_TD')) {
            if (Number(this.selectedIMS.status) === TrangThaiDongBo.DONGBO_CM) {
              this.statusButton.isEnableButtonGiaoPhieu = true
              this.statusButton.isEnableButtonKichHoat = false
            } else {
              this.statusButton.isEnableButtonGiaoPhieu = false
              this.statusButton.isEnableButtonKichHoat = true
            }
          } else {
            this.statusButton.isEnableButtonGiaoPhieu = true
          }
        } else {
          this.statusButton.isEnableButtonGiaoPhieu = false
          this.statusButton.isEnableButtonKichHoat = false
        }
      }

      if (this.PHAILAM('HOANCONG')) {
        this.statusButton.isEnableButtonHoanCong = this.selectedIMS.ngay_ht_gp && this.selectedIMS.ngay_ht_gp.trim()
        if (this.selectedIMS.dichvuvt_id !== DichVuVienThong.IMS_DOITAC) {
          this.statusButton.isEnableButtonHoanCong = this.statusButton.isEnableButtonHoanCong && this.selectedIMS.status === TrangThaiDongBo.DONGBO_CM
        }
      }

      if (!this.PHAILAM('GIAOVIEC')) {
        this.formThongTinHoanCong.checkedNgayHT = true
        this.formThongTinHoanCong.enableNgayHT = false
        this.formThongTinHoanCong.enableCheckboxNgayHT = false

        this.formThongTinHoanCong.checkedNgayGV = true
        this.formThongTinHoanCong.enableNgayGV = false
        this.formThongTinHoanCong.enableCheckboxNgayGV = false
      } else {
        this.formThongTinHoanCong.enableNgayHT = true
        this.formThongTinHoanCong.checkedNgayHT = true
        this.formThongTinHoanCong.enableCheckboxNgayHT = true

        this.formThongTinHoanCong.enableNgayGV = true
        this.formThongTinHoanCong.checkedNgayGV = true
        this.formThongTinHoanCong.enableCheckboxNgayGV = true
      }
      this.loading(false)
    },

    // region <Sự kiện click button>
    async btnUpdateClick() {
      const resultUpdate = await this.updateIms('cap_nhat')
      if (resultUpdate) {
        this.$toast.success('Cập nhật dữ liệu thành công')
        await this.loadDataTableIMS(this.selectedIMS.phieu_id)
      }
    },
    async updateIms(action) {
      this.loading(true)
      const isValid = await this.validateDateBeforeUpdate()
      if (!isValid) {
        this.loading(false)
        return false
      }
      if (action != null && action == 'kich_hoat') {
        this.formThongTinHoanCong.password = getRandomText(10)
      }
      if (!this.PHAILAM('GIAOVIEC')) {
        const data = [
          {
            LUONG_ID: this.thongTinHuongGiao.luong_id,
            NGAY_BG: formatDateToParam(this.formThongTinHoanCong.ngayHT),
            NGAY_GV: formatDateToParam(this.formThongTinHoanCong.ngayGV),
            PHIEU_ID: this.selectedIMS.phieu_id,
            NHANVIEN_ID: this.$root.token.getNhanVienID(),
            HDTB_ID: this.selectedIMS.hdtb_id,
            NGUOI_GV: this.formThongTinHoanCong.selectedNguoiGV
          }
        ]
        const statusUpdateGiaoViecGopAPI = await updateGiaoViecGopAPI(this.axios, {
          vds: JSON.stringify(data)
        }).catch((err) => {
          if (!err.data) {
            this.$toast.error('Cập nhật phiếu không thành công')
            return false
          }
          this.$toast.error(err.data.message_detail)
          return false
        })
        if (!statusUpdateGiaoViecGopAPI) {
          return false
        }
      }

      if (this.PHAILAM('CAPNHAT_PORT')) {
        const vpiId = this.selectedIMS.vci_vpi_id || 0
        const portId = this.selectedIMS.port_id || 0
        if (portId !== 0) {
          if (portId !== this.formThongTinHoanCong.portId || vpiId !== this.formThongTinHoanCong.vciVpiId) {
            await this.updateOldPort()
            await this.updateNewPort()
          }
        } else {
          await this.updateNewPort()
        }
        // if (action != null && action == 'cap_nhat') {
        // } else {
        //   this.formThongTinHoanCong.password = getRandomText(10);
        // }
        const paramUpdatePortIms = {
          hdtb_id: this.selectedIMS.hdtb_id,
          port_id: this.formThongTinHoanCong.portId,
          vci_vpi_id: this.formThongTinHoanCong.vciVpiId,
          bras_id: this.formThongTinHoanCong.selectedBras ? this.formThongTinHoanCong.selectedBras : 0,
          dslam_id: this.formThongTinHoanCong.selectedLAM ? this.formThongTinHoanCong.selectedLAM : 0,
          user_name: this.formThongTinHoanCong.username,
          password: this.formThongTinHoanCong.password,
          thietbi_id: this.formThongTinHoanCong.selectedThietBi ? this.formThongTinHoanCong.selectedThietBi : 0,
          tramtb_id: this.formThongTinHoanCong.tramThietBiId,
          svlan: this.formThongTinHoanCong.SvLan,
          cvlan: this.formThongTinHoanCong.CvLan
        }
        const statusUpdatePort = await updateThongTinPortIMS(this.axios, paramUpdatePortIms)
        if (statusUpdatePort !== true) {
          this.$toast.error(statusUpdatePort)
          return false
        }
      }

      if (this.PHAILAM('CAPNHAT_CAP')) {
        let doiCapNgon = this.formThongTinHoanCong.doiCapNgon != null ? Number(this.formThongTinHoanCong.doiCapNgon) || -1 : -1
        let doiCapGoc = this.formThongTinHoanCong.doiCapGoc != null ? Number(this.formThongTinHoanCong.doiCapGoc) || -1 : -1
        const paramThongTinCap = {
          hdtb_id: this.selectedIMS.hdtb_id,
          tuyencap: this.formThongTinHoanCong.tuyenCap,
          lientu: this.formThongTinHoanCong.lienTu,
          doicap_p: doiCapNgon,
          hopcap_p: this.formThongTinHoanCong.hopCapNgon,
          tucap_p: this.formThongTinHoanCong.tuCapNgon,
          loaicap_id: this.formThongTinHoanCong.selectedLoaiCap,
          cap_goc: this.formThongTinHoanCong.capGoc,
          cap_p: this.formThongTinHoanCong.capGoc,
          doicap_goc: doiCapGoc,
          tucap_goc: this.formThongTinHoanCong.tuCapGoc,
          culy: this.formThongTinHoanCong.cuLy
        }

        const kq = await this.axios.post('/web-ccdv/khaibaoims/capnhat_thongtin_thicong_ims', paramThongTinCap)
        if (kq.data.error_code != 'BSS-00000000') {
          this.$toast.error(`${kq.data.message}: ${kq.data.message_detail}`)
          this.loading(false)
          return false
        }

        // const statusUpdateCap = await updateThongTinCapIMS(this.axios, paramThongTinCap).catch((_) => {
        //   this.$toast.error('Cập nhật thông tin CAP không thành công');
        // });
        // console.log('statusUpdateCap')
        // console.log(statusUpdateCap)
        // if (!statusUpdateCap) {
        //   return false;
        // }
      }

      if (this.PHAILAM('CAPNHAT_SLID')) {
        // TODO: cập nhật slid
        // new BangTuDienFacade().THUCTHI_FUNCTION("CAPNHAT_SLID", DatabaseConstants.DB2, "vhdtb_id", hdtb_id, "vslid", txtSLID.Text.Trim());
      }

      const statusCapNhatPhieu = await capNhatThongTinPhieuIMS(this.axios, {
        phieu_id: this.selectedIMS.phieu_id || 0,
        nhanvien_gv_id: this.formThongTinHoanCong.selectedNguoiGV,
        nhanvien_th_id: this.$root.token.getNhanVienID(),
        ngay_th: formatDateToParam(this.formThongTinHoanCong.ngayHT),
        ngay_gv: formatDateToParam(this.formThongTinHoanCong.ngayGV)
      })

      if (statusCapNhatPhieu !== true) {
        this.$toast.error(statusCapNhatPhieu)
        return false
      }

      this.loading(false)
      return true
    },

    async updateNewPort() {
      console.log('updateNewPort')
      const lstCheckPort = await checkConditionPort(this.axios, {
        kieu: 1,
        ma_tb: this.formThongTinKhachHang.maThueBao.trim(),
        port_id: this.formThongTinHoanCong.portId,
        vci_vpi_id: this.formThongTinHoanCong.vciVpiId
      }).catch((err) => {
        this.$toast.error(err.data.message_detail)
        this.loading(false)
        return null
      })
      const lstCheckPortId = await checkConditionPort(this.axios, {
        kieu: 2,
        ma_tb: this.formThongTinKhachHang.maThueBao.trim(),
        port_id: this.formThongTinHoanCong.portId,
        vci_vpi_id: this.formThongTinHoanCong.vciVpiId
      }).catch((err) => {
        this.$toast.error(err.data.message_detail)
        this.loading(false)
        return null
      })
      if (!lstCheckPortId || !lstCheckPort) {
        this.loading(false)
        return
      }
      console.log('lstCheckPort')
      console.log(lstCheckPort)
      console.log('lstCheckPortId')
      console.log(lstCheckPortId)
      const lstMaThueBao = lstCheckPort
        .concat(lstCheckPortId)
        .concat([this.formThongTinKhachHang.maThueBao.trim()])
        .map((item) => item.ma_tb)
        .join('; ')
      console.log('lstMaThueBao')
      console.log(lstMaThueBao)
      const trangThaiPort = lstCheckPort.length === 0 ? TrangThaiPort.TAM_CAP : TrangThaiPort.DA_SD
      const trangThaiPortId = lstCheckPortId.length === 0 ? TrangThaiPort.DA_SD : TrangThaiPort.TAM_CAP
      await updateStatusPortNotUse(this.axios, {
        port_id: this.selectedIMS.port_id ? this.selectedIMS.port_id : 0,
        vci_vpi_id: this.selectedIMS.vci_vpi_id ? this.selectedIMS.vci_vpi_id : 0,
        trangthai: trangThaiPort
      })

      await updateStatusPortIdNotUse(this.axios, {
        port_id: this.selectedIMS.port_id ? this.selectedIMS.port_id : 0,
        tt_port_id: trangThaiPortId,
        trangthai: trangThaiPort,
        may_cn: 'admin',
        nguoi_cn: 'admin',
        vci_vpi_id: this.selectedIMS.vci_vpi_id ? this.selectedIMS.vci_vpi_id : 0
      })

      await updateDanhSachMaThueBaoTheoPort(this.axios, {
        ds_matb: lstMaThueBao || null,
        port_id: this.selectedIMS.port_id ? this.selectedIMS.port_id : 0,
        vci_vpi_id: this.selectedIMS.vci_vpi_id ? this.selectedIMS.vci_vpi_id : 0
      })
    },

    async updateOldPort() {
      console.log('updateOldPort')
      console.log(this.selectedIMS)
      let vport_id = 0
      let vvci_vpi_id = 0
      if (this.selectedIMS.port_id != '') vport_id = this.selectedIMS.port_id
      if (this.selectedIMS.vci_vpi_id != '') vvci_vpi_id = this.selectedIMS.vci_vpi_id
      //Kiểm tra xem có sự thay đổi port hay không
      let dsktradb = []
      let dsktrahd = []

      let lstCheckPort = []
      let lstCheckPortId = []
      await Promise.all([
        checkConditionPort(this.axios, {
          kieu: 1,
          ma_tb: this.formThongTinKhachHang.maThueBao.trim(),
          port_id: this.selectedIMS.port_id ? this.selectedIMS.port_id : 0,
          vci_vpi_id: this.selectedIMS.vci_vpi_id ? this.selectedIMS.vci_vpi_id : 0
        }),
        checkConditionPort(this.axios, {
          kieu: 2,
          ma_tb: this.formThongTinKhachHang.maThueBao.trim(),
          port_id: this.selectedIMS.port_id ? this.selectedIMS.port_id : 0,
          vci_vpi_id: this.selectedIMS.vci_vpi_id ? this.selectedIMS.vci_vpi_id : 0
        })
      ]).then((respone) => {
        lstCheckPort = respone[0]
        lstCheckPortId = respone[1]
      })

      console.log('lstCheckPort')
      console.log(lstCheckPort)
      console.log('lstCheckPortId')
      console.log(lstCheckPortId)
      const totalMaThueBao = lstCheckPort
        .concat(lstCheckPortId)
        .map((item) => item.ma_tb)
        .join('; ')
      console.log('totalMaThueBao')
      console.log(totalMaThueBao)
      await updateStatusPortNotUse(this.axios, {
        port_id: this.selectedIMS.port_id ? this.selectedIMS.port_id : 0,
        vci_vpi_id: this.selectedIMS.vci_vpi_id ? this.selectedIMS.vci_vpi_id : 0,
        trangthai: TrangThaiPort.CHUA_SD
      })

      await updateStatusPortIdNotUse(this.axios, {
        port_id: this.selectedIMS.port_id ? this.selectedIMS.port_id : 0,
        tt_port_id: TrangThaiPort.CHUA_SD,
        trangthai: TrangThaiPort.CHUA_SD,
        may_cn: 'admin',
        nguoi_cn: 'admin',
        vci_vpi_id: this.selectedIMS.vci_vpi_id ? this.selectedIMS.vci_vpi_id : 0
      })

      await updateDanhSachMaThueBaoTheoPort(this.axios, {
        ds_matb: totalMaThueBao || null,
        port_id: this.selectedIMS.port_id ? this.selectedIMS.port_id : 0,
        vci_vpi_id: this.selectedIMS.vci_vpi_id ? this.selectedIMS.vci_vpi_id : 0
      })
    },

    async updateInfomationPortFromHoanCong() {
      if (this.formThongTinHoanCong.selectedThietBi == null || this.formThongTinHoanCong.selectedThietBi == '') {
        this.$toast.warning('Vui lòng chọn thiết bị trước khi cấp port!')
        return
      }

      if (this.selectedIMS.port_id !== 0) {
        if (this.selectedIMS.port_id !== this.formThongTinHoanCong.portId || this.selectedIMS.vci_vpi_id !== this.formThongTinHoanCong.vciVpiId) {
          await this.updateOldPort()
          await this.updateNewPort()
        }
      } else {
        await this.updateNewPort()
      }
      const paramUpdatePortIms = {
        hdtb_id: this.selectedIMS.hdtb_id,
        port_id: this.formThongTinHoanCong.portId,
        vci_vpi_id: this.formThongTinHoanCong.vciVpiId,
        bras_id: this.formThongTinHoanCong.selectedBras,
        dslam_id: this.formThongTinHoanCong.selectedLAM,
        user_name: this.formThongTinHoanCong.username,
        password: this.formThongTinHoanCong.password,
        thietbi_id: this.formThongTinHoanCong.selectedThietBi || '',
        tramtb_id: this.formThongTinHoanCong.tramThietBiId,
        svlan: this.formThongTinHoanCong.SvLan,
        cvlan: this.formThongTinHoanCong.CvLan
      }
      const result = await updateThongTinPortIMS(this.axios, paramUpdatePortIms)
      // if(result.message == 'Success') {
      //   this.$toast.success("Thành công")
      // }
      if (result !== true) {
        this.$toast.error(result)
      } else {
        this.$toast.success('Thành công')
        await this.loadDataTableIMS(this.selectedIMS.phieu_id)
      }
    },
    async btnGiaoHostCuClick() {
      console.log('btnGiaoHostCuClick')
      try {
        if (this.gridThueBaoIms.selected.length > 0) {
          var count = 0
          for (var i = 0; i < this.gridThueBaoIms.selected.length; i++) {
            var data = await giaophieu_td_host_thuhoi_1(this.axios, {
              hdtb_id: this.gridThueBaoIms.selected[i].hdtb_id,
              phieu_id: this.gridThueBaoIms.selected[i].phieu_id
            })
            count++
          }
          //  await this.$root.token.getDonViID(),
          //   await this.$root.token.getNhanVienID()
          //  await this.$root.token.getMaNhanVien(),
          //   await this.$root.token.getMachine(),
          //   await this.$root.token.getIP()
          if (count > 0) {
            this.$toast.success('Đã giao phiếu hủy số cũ thành công')
            this.loadDataTableIMS()
          }
        } else {
          this.$toast.warning('Bạn phải tích chọn thuê bao trên lưới!')
        }
      } catch (e) {
        this.$toast.error(`Có lỗi khi giao phiếu ${e}`)
      }
    },
    async btnKhoiPhucSoClick() {
      if (!this.selectedIMS) return
      this.loading(true)
      const lstHuongGiao = await getDanhSachHuongGiaoKhoiPhucSo(this.axios, this.selectedIMS.hdtb_id)

      if (lstHuongGiao.findIndex((item) => item.huonggiao_id === 20012) >= 0) {
        this.$toast.error('Đã có phiếu giao khôi phục số cũ rồi !')
        this.loading(false)
        return
      }
      // Phiếu hủy số cố định phải hoàn thành mới cho giao
      const trangThaiPH = lstHuongGiao.find((item) => item.huonggiao_id === 3001)
      if (!trangThaiPH) {
        this.$toast.error('Chưa giao phiếu thu hồi port, không cần khôi phục lại port')
        this.loading(false)
      } else {
        const id = Number(trangThaiPH.ttph_id)
        switch (id) {
          case TrangThaiPH.MOI:
          case TrangThaiPH.DAGIAO:
            this.$toast.error('Host chưa thu hồi port, xin hãy hủy phiếu giao thu hồi')
            this.loading(false)
            break
          case TrangThaiPH.DATHUCHIEN:
            const param = {
              hdtb_id: this.selectedIMS.hdtb_id,
              phieu_id: this.selectedIMS.phieu_id,
              ma_tb_cu: this.selectedIMS.ma_tb_cu,
              kieu_ngay: 0,
              donvi_giao_id: this.$root.token.getDonViID(),
              nhanvien_id: this.$root.token.getNhanVienID(),
              nguoi_cn: this.$root.token.getUserName()
            }
            updateKhoiPhucSo(this.axios, param)
              .finally(() => {
                this.loading(false)
              })
              .then((response) => {
                if (response) {
                  this.$toast.success('Đã giao phiếu khôi phục số cũ thành công')
                } else {
                  this.$toast.error('Giao phiếu khôi phục số cũ không thành công!')
                }
              })
              .catch(() => {
                this.$toast.error('Giao phiếu khôi phục số cũ không thành công!')
              })
            break
          default:
            this.$toast.error('Host đã trả lại phiếu, không cần khôi phục lại port')
            this.loading(false)
            break
        }
      }
    },

    async btnTonPhieuClick() {
      if (!this.selectedIMS) return
      this.popupTonPhieuModel = {
        phieuId: this.selectedIMS.phieu_id,
        tthdId: this.selectedIMS.tthd_id
      }
      this.$nextTick(() => {
        this.$refs.tonPhieuGiaoModal.showPhieuTon(this.selectedIMS.phieu_id, this.selectedIMS.tthd_id, [this.selectedIMS.phieu_id])
      })
    },

    btnXuatFileClick() {
      this.$refs.popupExportDataModal.showModal()
    },

    async btnDauNoiClick() {
      // this.loading(true)
      let ts = await layThamSoMacDinh(this.axios, 'DAUNOI_KOCAN_CAP_PORT')
      if (this.formThongTinHoanCong.portId === 0 && (!ts || ts.length === 0)) {
        this.$toast.error('Chưa có thông tin về Port, bạn không được phép cấp cáp.')
        return
      }

      // let lattitude = 0
      // let longtitude = 0
      //
      // let s = await queryData(this.axios, {
      //   id_neo: 'kinhdo_ld',
      //   in_table: 'css.diachi_hdtb',
      //   in_dk: `where hdtb_id = ${this.hdtbId}`
      // })
      //
      // if (s !== '-1' && s !== '') {
      //   lattitude = Number(s)
      // }
      //
      // s = await queryData(this.axios, {
      //   id_neo: 'vido_ld',
      //   in_table: 'css.diachi_hdtb',
      //   in_dk: `where hdtb_id = ${this.hdtbId}`
      // })
      // if (s !== '-1' && s !== '') {
      //   longtitude = Number(s)
      // }
      //
      // let vto_ql = await queryData(this.axios, {
      //   id_neo: 'ten_dv',
      //   in_table: 'admin.donvi',
      //   in_dk: `where donvi_id = ${this.thongTinDonVi}`
      // })
      //
      // let vtram_tbi = await queryData(this.axios, {
      //   id_neo: 'ten_dv',
      //   in_table: 'admin.donvi',
      //   in_dk: `where donvi_id = ${this.formThongTinHoanCong.tramThietBiId}`
      // })
      //
      console.log('dau noi cap')
      console.log(this.selectedIMS.hdtb_id)
      this.dataPopupDauNoiThueBao.thueBaoId = this.selectedIMS.hdtb_id
      this.DauNoiThueBao_OnClick()
      // this.$nextTick(() => {
      //   this.$refs.modalDauNoiThueBao.show()
      // })
    },

    async btnChuyenToClick() {
      if (!this.selectedIMS && !this.selectedIMS.phieu_id) {
        return
      }
      if (this.PHAILAM('KIEMTRA_VT_CHUYENTO')) {
        const res = await getDanhSachPhieuKiemTraChuyenTo(this.axios, {
          phieu_id: this.selectedIMS.phieu_id,
          baohong_id: '0',
          hdtb_id: this.selectedIMS.hdtb_id,
          kieutbi: '0',
          status: '0'
        })
        if (res.length > 0) {
          this.$toast.error('Bạn phải xóa hết vật tư trước khi thực hiện chuyển tổ')
          return
        }
      }
      this.$refs.popupChuyenTo.openDialog(this.selectedIMS.phieu_id, this.selectedIMS.hdtb_id)
    },

    async btnTraPhieuClick() {
      //  this.selectedIMS = {
      //   hdtb_id: 1282963,
      //   ma_gd: 'BDH-LH/00009840',
      //   loaihd_id: 6,
      //   dichvuvt_id: 4
      //  }
      const check = await queryData(this.axios, {
        id_neo: 'ttph_id',
        in_table: 'css.giaophieu',
        in_dk: `where hdtb_id = ${this.selectedIMS.hdtb_id} AND huonggiao_id = 20012`
      })

      if (check && check === '1') {
        this.$toast.error('Hiện đã giao phiếu khôi phục cổng Cố định cho thuê bao này. \n' + 'Để tránh làm gián đoạn dịch vụ của khác hàng, vui lòng đợi Host khôi phục cổng hoàn tất sau đó trả phiếu')
        return
      }
      this.$refs.frmTraPhieuTC.frmTraPhieuTC(this.selectedIMS.ma_gd, this.selectedIMS.loaihd_id, this.selectedIMS.dichvuvt_id, this.$root.token.getDonViID(), this.selectedIMS.ma_tb, '0', '0', 3)
      this.$nextTick(() => {
        this.$refs.modalTraPhieu.show()
      })
    },

    btnDoiCongClick() {
      if (!this.selectedIMS || !this.selectedIMS.hdtb_id) {
        this.$toast.error('Không có dữ liệu. Bạn hãy kiểm tra lại!')
        return
      }

      if (this.selectedIMS.status !== TrangThaiDongBo.DONGBO_CM) {
        this.$toast.error('Thuê bao chưa được kích hoạt trên Visa/Vasc. \n Bạn không được thực hiện chức năng đổi cổng.')
        return
      }

      const propData = {
        in_trangthai_dl: 0,
        in_hdtb_id: this.selectedIMS.hdtb_id,
        in_ma_tb: this.selectedIMS.ma_tb,
        in_ten_tb: this.selectedIMS.ten_tb,
        in_diachi_ld: this.selectedIMS.diachi_ld,
        in_dichvuvt_id: this.selectedIMS.dichvuvt_id,
        in_loaitb_id: this.selectedIMS.loaitb_id,
        in_flagTSKT: this.tagForm.flagTSKT
      }
      this.$refs.formThayDoiThongSoInternet.setPropData(propData)
      this.$nextTick(() => {
        this.$refs.modalDoiCong.show()
      })
    },

    btnDuAnClick() {
      this.$refs.modalDuAn.showModal()
    },

    btnthongSoKyThuatClick() {
      this.$refs.modalTraCuuThongSoKyThuat.showModal()
    },

    btnGanKhuVucClick() {
      this.$refs.modalThayDoiKhuVucQuanLy.showModal()
    },

    btnHenLapDatClick() {
      this.$refs.modalHenLapDat.showModal()
    },

    btnLienHeClick() {
      if (this.selectedIMS) {
        this.hdtbId = this.selectedIMS.hdtb_id
        this.$nextTick(() => {
          this.$refs.modalLienHe.show()
        })
      }
    },

    async btnHoanThienClick() {
      const param = [
        {
          HDTB_ID: this.selectedIMS.hdtb_id,
          LUONG_ID: this.thongTinHuongGiao.luong_id,
          PHIEU_ID: this.selectedIMS.phieu_id,
          NGAY_BG: formatDateToParam(this.formThongTinHoanCong.ngayHT),
          LOAIHD_ID: this.formThongTinKhachHang.selectedLoaiHDPL,
          MAY_CN: await this.$root.token.getMachine(),
          NGUOI_CN: await this.$root.token.getUserName()
        }
      ]
      this.loading(true)
      const result = await hoanThienAPI(this.axios, JSON.stringify(param))
        .catch((err) => {
          this.$toast.error(err.data.message_detail)
        })
        .finally(() => {
          this.loading(false)
        })
      if (result === '1' || result === 'OK') {
        this.$toast.success('Cật nhật dữ liệu vào danh bạ thành công!')
        this.loadDataTableIMS()
      } else {
        this.$toast.error(result)
      }
    },

    async btnHoanCongClick() {
      if (Number(this.formThongTinKhachHang.selectedLoaiHDPL) != 1) {
        var ds_hopdong = await this.lay_ds_hopdong_theo_hdtbid()
        if (ds_hopdong && ds_hopdong.length > 0) {
          var thuebao_id_dbtb = Number(ds_hopdong[0]['thuebao_id'])
          var ma_tb_dbtb = ds_hopdong[0]['ma_tb']
          if (Number(this.selectedIMS.thuebao_id) != thuebao_id_dbtb) {
            this.$toast.error('Thuê bao ID không đúng với thuê bao ID lưu trong danh bạ thuê bao!')
            return
          }
          if (this.formThongTinKhachHang.maThueBao != ma_tb_dbtb) {
            this.$toast.error('Mã thuê bao không đúng với Mã thuê bao lưu trong danh bạ thuê bao!')
            return
          }
        }
      }

      const isValid = await this.validateDateBeforeUpdate()
      if (!isValid) return
      this.loading(true)
      const checkOCM = await kiemTraGiaoOCM(this.axios, {
        hdtb_id: this.selectedIMS.hdtb_id,
        loaihinh_tb: this.formThongTinKhachHang.selectedLoaiHinh
      }).catch((err) => {
        this.$toast.error(err.data.message_detail)
        this.loading(false)
      })
      if (checkOCM === '1' && !confirm('Phiếu sẽ được giao về OMC để thực hiện. Bạn có muốn tiếp tục không ?')) {
        this.loading(false)
        return
      }

      const tsHC = this.danhSachThamSoMacDinh.find((ele) => ele.ma_ts === 'KHONGHOANCONG_CHUA_THANHTOAN')
      let khong_hoancong_chua_thanhtoan = 0
      if (tsHC && tsHC.ten_ts && tsHC.ten_ts.trim() === '1') {
        khong_hoancong_chua_thanhtoan = 1
      }
      const param = {
        luong_id: this.thongTinHuongGiao.luong_id,
        hdtb_id: this.selectedIMS.hdtb_id,
        hdkh_id: this.selectedIMS.hdkh_id,
        phieu_id: this.selectedIMS.phieu_id,
        loaihinh_tb: this.formThongTinKhachHang.selectedLoaiHinh,
        huonggiao_id: this.thongTinHuongGiao.huonggiao_id,
        khong_hoancong_chua_thanhtoan,
        loaihd_id: this.formThongTinKhachHang.selectedLoaiHDPL,
        ngay_bg: formatDateToParam(this.formThongTinHoanCong.ngayHT),
        ngay_gv: formatDateToParam(this.formThongTinHoanCong.ngayGV),
        ma_tb: this.formThongTinKhachHang.maThueBao,
        nguoi_gv: this.formThongTinHoanCong.selectedNguoiGV,
        port_id: this.formThongTinHoanCong.portId,
        vci_vpi_id: this.formThongTinHoanCong.vciVpiId,
        bras_id: this.formThongTinHoanCong.selectedBras,
        dslam_id: this.formThongTinHoanCong.selectedLAM,
        username: this.formThongTinHoanCong.username,
        password: this.formThongTinHoanCong.password,
        thietbi_id: this.formThongTinHoanCong.selectedThietBi,
        tramtb_id: this.formThongTinHoanCong.tramThietBiId,
        svlan: this.formThongTinHoanCong.SvLan,
        cvlan: this.formThongTinHoanCong.CvLan,
        vport_id: this.selectedIMS.port_id,
        vvci_vpi_id: this.selectedIMS.vci_vpi_id,
        tuyencap: this.formThongTinHoanCong.tuyenCap,
        lientu: this.formThongTinHoanCong.lienTu,
        hopcap_ngon: this.formThongTinHoanCong.hopCapNgon,
        tucap_ngon: this.formThongTinHoanCong.tuCapNgon,
        tucap_goc: this.formThongTinHoanCong.tuCapGoc,
        loaicap_id: this.formThongTinHoanCong.selectedLoaiCap,
        cap_goc: this.formThongTinHoanCong.capGoc,
        cap_ngon: this.formThongTinHoanCong.capNgon,
        somet_day: this.formThongTinHoanCong.cuLy,
        doicap_ngon: this.formThongTinHoanCong.doiCapNgon,
        doicap_goc: this.formThongTinHoanCong.doiCapGoc,
        slid: this.formThongTinHoanCong.SLID,
        sdt_lh: this.selectedIMS.so_dt_lh
      }
      this.loading(true)
      await hoanCongAPI(this.axios, param)
        .finally(() => {
          this.loading(false)
        })
        .catch((err) => {
          this.$toast.error(err.data.message_detail)
        })
        .then((response) => {
          if (response && response.data && response.data.error_code && response.data.error_code === 'BSS-00000000') {
            this.$toast.success('Hoàn công thành công!')
          } else {
            this.$toast.error(response.data.message)
          }
        })

      this.loadDataTableIMS()
    },

    async btnInPhieuClick() {
      try {
        let vhdkhid = await queryData(this.axios, {
          id_neo: 'ttph_id',
          in_table: 'css.giaophieu',
          in_dk: `where hdtb_id = ${this.selectedIMS.hdtb_id}`
        })

        if (vhdkhid === '-1') {
          this.$root.$toast.error('Không tìm thấy thông tin khách hàng. Bạn hãy kiểm tra lại!')
          return
        }

        var lstChecked = this.gridThueBaoIms.selected
        if (lstChecked.length === 0) {
          this.$root.$toast.warning('Hãy chọn phiếu!')
          return
        }
        let nhdkhid = []
        let nhdtbid = []
        let nphieuid = []
        nhdkhid = lstChecked.map((item) => item.hdkh_id)
        nhdtbid = lstChecked.map((item) => item.hdtb_id)
        nphieuid = lstChecked.map((item) => item.phieu_id)

        this.modalInBienBanData.n_phieu_id = nphieuid.join(',')
        this.modalInBienBanData.ma_gd = this.formThongTinKhachHang.maGiaoDich
        this.modalInBienBanData.n_hdkh_id = nhdkhid.join(',')
        this.modalInBienBanData.n_hdtb_id = nhdtbid.join(',')
        this.modalInBienBanData.nvth_id = -1
        this.modalInBienBanData.huonggiao_id = this.thongTinHuongGiao.huonggiao_id
        this.modalInBienBanData.tentram_tb = this.formThongTinHoanCong.tramThietBi
        this.$nextTick(() => {
          this.$refs.modalInBienBan.showModal()
        })
      } catch (error) {
        this.$root.$toast.error('Có lỗi khi in phiếu: ' + error.message)
      }
    },

    onSuccessNhapCapThueBao() {
      // alert('Đấu nối thuê bao hoàn tất')
    },

    async closeModalDauNoiThueBao() {
      await this.showThongSoKyThuat()
    },

    closeModalTraPhieu() {
      this.loadDataTableIMS()
    },

    btnGiaoViecClick() {
      if (!this.selectedIMS) {
        this.$toast.error('Chưa có danh thuê bao. Bạn hãy kiểm tra lại! ')
        return
      }
      if (!this.formThongTinHoanCong.selectedNguoiGV) {
        this.$toast.error('Hãy nhập nhân viên giao việc!')
        return
      }
      if (!this.formThongTinHoanCong.checkedNgayGV) {
        this.$toast.error('Hãy nhập ngày giao việc!')
        return
      }
      if (this.formThongTinHoanCong.checkedNgayHT) {
        if (new Date(this.formThongTinHoanCong.ngayHT) < new Date(this.formThongTinHoanCong.ngayGV)) {
          this.$toast.error('Ngày giao việc không được lớn hơn ngày hoàn thành!')
          return
        }
      }
      this.popupGiaoViecModel.nguoiGV = this.formThongTinHoanCong.selectedNguoiGV
      this.popupGiaoViecModel.ngayGV = reformatDateTime(this.formThongTinHoanCong.ngayGV)
      this.popupGiaoViecModel.phieuId = this.selectedIMS.phieu_id
      this.$nextTick(() => {
        this.$refs.formGiaoPhieu.init()
        this.$refs.giaoPhieuNhanVien.show()
      })
    },

    async btnGiaoPhieuClick() {
      const resultUpdate = await this.updateIms()
      if (!resultUpdate) {
        return
      }
      if (this.formThongTinKhachHang.maGiaoDich && this.formThongTinKhachHang.maGiaoDich.trim()) {
        let huongGiao = 0
        if (this.PHAILAM('DIEUKIEN_GIAOPHIEU_BP_TIEPTHEO')) {
          huongGiao =
            (await getHuongGiaoTheoQuyTrinh(this.axios, {
              baohong_id: 0,
              hdtb_id: this.selectedIMS.hdtb_id,
              quytrinh_id: this.formThongTinKhachHang.selectedQuyTrinh
            })) || 0
        }

        if (huongGiao === 0) {
          huongGiao = await getHuongGiaoLan2(this.axios, this.thongTinHuongGiao.luong_id)
        }

        if (huongGiao === 0) {
          this.$toast.error('Chưa có hướng giao được gán !')
          return
        }
        window.open(`/#/contract/HandoverTicket?vquytrinh=${this.formThongTinKhachHang.selectedQuyTrinh}&vhuonggiao_id=${huongGiao}&vdichvuvt_id=${this.selectedIMS.dichvuvt_id}&vma_gd=${this.formThongTinKhachHang.maGiaoDich}&vloai_hd=${this.formThongTinKhachHang.selectedLoaiHDPL}`)
      }
    },

    btnDoKiemClick() {
      this.$refs.modalKetQuaDoKiem.showModal()
    },

    btnVatTuClick() {
      this.modalCapVatTuTBData = {
        phieu_id: this.selectedIMS.phieu_id,
        loaihd_id: this.selectedIMS.loaihd_id,
        hdtb_id: this.selectedIMS.hdtb_id,
        thuebao_id: this.selectedIMS.thuebao_id,
        vdvvt_id: 0,
        loaitb_id: this.formThongTinKhachHang.selectedLoaiHinh,
        vma_tbcur: 0,
        baohong_id: 0
      }
      this.$nextTick(() => {
        this.$refs.modalCapVatTuTB.openDialog()
      })
    },

    // Check công việc phải làm (chả hiểu làm gì) code theo code cũ
    PHAILAM(code) {
      return this.thongTinHuongGiao.danhSachThaoTac.findIndex((item) => item.enable === 1 && item.code === code) >= 0
    },

    // Hiện thông số kỹ thuật
    async showThongSoKyThuat() {
      if (!this.isExistThamSo('USING_CABMAN_2.0')) {
        this.formThongTinHoanCong.isUsingCapMan = false
        return
      }
      this.formThongTinHoanCong.danhSachThongTinKyThuat = await getDanhSachThongTinKyThuat(this.axios, this.selectedIMS.hdtb_id)
    },

    isExistThamSo(code) {
      return this.danhSachThamSoMacDinh.findIndex((item) => item.ma_ts === code) >= 0
    },
    async giaoViecSuccess() {
      this.danhSachNhanVienThucHien = await getNhanVienTheoMaPhieu(this.axios, this.selectedIMS.phieu_id)
    },

    async lay_danhba_theo_matb() {
      var data = {
        DICHVUVT_ID: this.selectedIMS.dichvuvt_id,
        MA_TB: this.formThongTinKhachHang.maThueBao
      }
      var data = await this.getDanhBaTheoMaTB(data)
      var danhba = data && data.returnd_danhba ? data.returnd_danhba : []
      console.log('danhba', danhba)
    },

    async lay_ds_hopdong_theo_hdtbid() {
      var ds_hopdong = await getDSHopDongTheoHDTBID(this.axios, this.selectedIMS.hdtb_id)
      console.log('ds_hopdong', ds_hopdong)
      return ds_hopdong
    },

    async validateDateBeforeUpdate() {
      if (!this.formThongTinHoanCong.checkedNgayGV) {
        this.$toast.error('Hãy chọn ngày giao việc !')
        return false
      }
      if (this.PHAILAM('GIAOVIEC')) {
        if (this.danhSachNhanVienThucHien.length <= 0) {
          this.$toast.error('Hãy nhập nhân viên thực hiện  !')
          return false
        }
      }

      if (!this.formThongTinHoanCong.checkedNgayHT) {
        this.$toast('Hãy nhập ngày hoàn thành!')
        return false
      }

      if (new Date(this.formThongTinHoanCong.ngayHT) < new Date(this.formThongTinHoanCong.ngayGV)) {
        this.$toast.error('Ngày giao việc không được lớn hơn ngày hoàn thành!')
        return false
      }
      const ngaySYS = getDateFromString(this.selectedIMS.thangnam_sys)
      const ngayThanhToan = getDateFromString(this.selectedIMS.thangnam_tt)
      const tempDate = new Date(this.formThongTinHoanCong.ngayHT)
      tempDate.setHours(0)
      tempDate.setMinutes(0)
      tempDate.setSeconds(0)
      tempDate.setMilliseconds(0)
      if (this.formThongTinKhachHang.selectedLoaiHDPL !== LoaiHopDong.KHOIPHUC_SD) {
        if (ngayThanhToan - tempDate > 0) {
          // Nếu ngày thanh toán > ngày hoàn thành
          this.$toast.error(`Thuê bao ${this.selectedIMS.ma_tb} có ngày thanh toán : ${this.selectedIMS.thangnam_tt} \nNgày hoàn thành không được phép nhỏ hơn ngày thanh toán`)
          return false
        }
      }
      if (tempDate - ngaySYS > 0) {
        // Nếu ngày hiện tại nhỏ hơn ngày hoàn thành
        this.$toast.error('Bạn không được phép chọn ngày hoàn thành lớn hơn ngày hiện tại')
        return false
      }
      const tempDate1 = new Date(this.formThongTinHoanCong.ngayGV)
      tempDate1.setHours(0)
      tempDate1.setMinutes(0)
      tempDate1.setSeconds(0)
      tempDate1.setMilliseconds(0)
      if (tempDate1 - ngaySYS > 0) {
        // Nếu ngày giao viêc > ngày hiện tại
        this.$toast.error('Bạn không được phép chọn ngày giao việc lớn hơn ngày hiện tại')
        return false
      }
      if (this.formThongTinHoanCong.ngayGV - this.formThongTinHoanCong.ngayHT > 0) {
        this.$toast.error('Bạn không được chọn ngày thực hiện nhỏ hơn ngày giao việc !')
        return false
      }
      if (this.PHAILAM('CAPNHAT_PORT')) {
        const loaiHopDong = this.formThongTinKhachHang.selectedLoaiHDPL
        if (loaiHopDong === LoaiHopDong.DAT_MOI || loaiHopDong === LoaiHopDong.CHUYENDOI_LH) {
          if (this.formThongTinHoanCong.portId === 0 || this.formThongTinHoanCong.vciVpiId === 0) {
            this.$toast.error('Hãy nhập thông tin về Port')
            return false
          }
          if (!this.formThongTinHoanCong.username || !this.formThongTinHoanCong.password) {
            this.$toast.error('Hãy nhập thông tin user hoặc mật khẩu')
            return false
          }
        } else {
          const hasTramVatTu = await checkTramVatTu(this.axios, this.selectedIMS.hdtb_id)
          if (hasTramVatTu || this.selectedIMS.ma_tb_cu !== this.selectedIMS.ma_tb) {
            if (this.selectedIMS.port_id === this.formThongTinHoanCong.portId && this.selectedIMS.vci_vpi_id === this.formThongTinHoanCong.vciVpiId) {
              // TODO: xử lại lại kiểm tra port update
              // const checkPortConst = await checkPort(
              //   this.axios,
              //   this.selectedIMS.hdtb_id
              // )
              // if (checkPortConst) {
              //   this.$toast.error('Hãy chọn port mới !')
              //   return false
              // }
            }
          }
        }
      }
      if (this.PHAILAM('CAPNHAT_CAP')) {
        if (this.PHAILAM('PHAINHAP_CAP')) {
          if (!this.formThongTinHoanCong.doiCapGoc.trim()) {
            this.$toast.error('Hãy nhập thông tin đôi cáp gốc !')
            return false
          }

          if (!this.formThongTinHoanCong.capGoc.trim()) {
            this.$toast.error('Hãy nhập thông tin cáp gốc !')
            return false
          }
          if (!this.formThongTinHoanCong.doiCapNgon.trim()) {
            this.$toast.error('Hãy nhập thông tin đôi cáp ngọn !')
            return false
          }

          if (this.formThongTinHoanCong.capNgon.trim()) {
            this.$toast.error('Hãy nhập thông tin cáp ngọn !')
            return false
          }
        }
        if (!this.formThongTinHoanCong.cuLy) {
          this.$toast.error('Hãy nhập thông tin số mét dây !')
          return false
        }
      }
      return true
    },

    // unselected all phiếu
    resetPage() {
      this.formThongTinChuyenDoi.selectedKenhThu = null
      this.formThongTinChuyenDoi.selectedKieuHopDong = null
      this.formThongTinChuyenDoi.nhanVienTiepThi = ''
      this.formThongTinChuyenDoi.tongTien = ''

      this.formThongTinKhachHang.selectedLoaiHinh = null
      this.formThongTinKhachHang.maGiaoDich = ''
      this.formThongTinKhachHang.maThueBao = ''
      this.formThongTinKhachHang.tenThueBao = ''
      this.formThongTinKhachHang.tenKhuVuc = ''
      this.formThongTinKhachHang.loaiHinh = ''
      this.formThongTinKhachHang.tenKieuLD = ''
      this.formThongTinKhachHang.thueBaoLapKem = ''
      this.formThongTinKhachHang.nhanVienYC = ''
      this.formThongTinKhachHang.diaChiThueBao = ''
      this.formThongTinKhachHang.diaChiLapDat = ''
      this.formThongTinKhachHang.diaChiLapDatCu = ''
      this.formThongTinKhachHang.checkedLapDatTheoYeuCau = false

      this.formThongTinHoanCong.selectedLoaiCap = null
      this.formThongTinHoanCong.selectedBras = null
      this.formThongTinHoanCong.selectedLAM = null
      this.formThongTinHoanCong.selectedThietBi = null
      this.formThongTinHoanCong.selectedNguoiGV = null
      this.formThongTinHoanCong.ngayHT = null
      this.formThongTinHoanCong.ngayGV = null
      this.formThongTinHoanCong.username = ''
      this.formThongTinHoanCong.password = ''
      this.formThongTinHoanCong.noiDungTra = ''
      this.formThongTinHoanCong.lyDoTra = ''
      this.formThongTinHoanCong.maTruyCap = ''
      this.formThongTinHoanCong.noiDungTH = ''
      this.formThongTinHoanCong.tuyenCap = ''
      this.formThongTinHoanCong.lienTu = ''
      this.formThongTinHoanCong.tuCapNgon = ''
      this.formThongTinHoanCong.tuCapGoc = ''
      this.formThongTinHoanCong.doiCapNgon = ''
      this.formThongTinHoanCong.doiCapGoc = ''
      this.formThongTinHoanCong.hopCapNgon = ''
      this.formThongTinHoanCong.capGoc = ''
      this.formThongTinHoanCong.capNgon = ''
      this.formThongTinHoanCong.cuLy = 0
      this.formThongTinHoanCong.soLuongCuocGoi = ''
      this.formThongTinHoanCong.selectedChiTietDichVu = 0
      this.formThongTinHoanCong.IPKhachHang = ''
      this.formThongTinHoanCong.noiDungGiao = ''
      this.formThongTinHoanCong.ONUPass = ''
      this.formThongTinHoanCong.port = ''
      this.formThongTinHoanCong.portMdf = ''
      this.formThongTinHoanCong.slot = ''
      this.formThongTinHoanCong.VPI = ''
      this.formThongTinHoanCong.VCI = ''
      this.formThongTinHoanCong.ADSLPort = ''
      this.formThongTinHoanCong.SvLan = ''
      this.formThongTinHoanCong.CvLan = ''
      this.formThongTinHoanCong.system = ''
      this.formThongTinHoanCong.rack = ''
      this.formThongTinHoanCong.shelf = ''
      this.formThongTinHoanCong.tramThietBi = ''
      this.formThongTinHoanCong.SLID = ''
      this.formThongTinHoanCong.enableButtonChonPortThuCong = false
      this.formThongTinHoanCong.enableButtonChonPortTuDong = false
      this.formThongTinHoanCong.enableNgayHT = false
      this.formThongTinHoanCong.enableNgayGV = false
      this.formThongTinHoanCong.enableCheckboxNgayHT = false
      this.formThongTinHoanCong.enableCheckboxNgayGV = false
      this.formThongTinHoanCong.checkedNgayHT = false
      this.formThongTinHoanCong.checkedNgayGV = false
      // cập nhật lable Thông tin liên hệ
      this.formThongTinHoanCong.thongTinLienHe = ''
      // nếu không dùng CAP MAN V2 (2.0) thì hiển thị thông tin cũ (hiển thị mã truy cập tủ cáp gốc.....
      // nếu dùng CAP MAN V2 (2.0) thì hiển thị thông số lấy từ API (hiển thị raw html)
      this.formThongTinHoanCong.isUsingCapMan = true
      this.formThongTinHoanCong.danhSachThongTinKyThuat = []

      // các thông tin này được set lại khi chọn port hoặc chọn phiếu IMS khác
      this.formThongTinHoanCong.portId = 0
      this.formThongTinHoanCong.vciVpiId = 0
      this.formThongTinHoanCong.tramThietBiId = 0
      // Thông tin này dùng khi chọn port
      this.formThongTinHoanCong.thongTinDonVi = 0

      this.selectedIMS = null
    },
    deselectedIMS(rows) {
      if (rows.length === 0) {
        return
      }
      const selected = this.gridThueBaoIms.selected
      if (selected.length === 0) {
        this.resetPage()
        return
      }
      if (rows.length === 1 && this.selectedIMS && this.selectedIMS.hdtb_id === rows[0].hdtb_id) {
        this.hienThiThongTinThueBao(selected[0])
      }
    },

    async btnHuyKichHoatClick() {
      // hàm huỷ kích hoạt
      this.loading(true)
      const DONGBOTEST = await DongBoTestAPI(this.axios)
      if (!DONGBOTEST) {
        this.$toast.error('Hủy kích hoạt không thành công, xảy ra lỗi :\n' + 'Đồng bộ test đang tắt')
        this.loading(false)
        return
      }
      const loaiHD = Number(this.formThongTinKhachHang.selectedLoaiHDPL)
      if (loaiHD === LoaiHopDong.DAT_MOI || loaiHD === LoaiHopDong.KHOIPHUC_SD) {
        await this.huyKichHoatLapMoiOrKhoiPhucSuDung()
        this.loading(false)
        return
      }

      if (loaiHD === LoaiHopDong.DI_CHUYEN) {
        await this.HuyKichHoatThueBao()
        this.loading(false)
        return
      }

      if (loaiHD === LoaiHopDong.CHUYENDOI_LH) {
        await this.huyKichHoatChuyenDoiLoaiHinh()
        this.loading(false)
        return
      }

      this.loading(false)
    },

    async huyKichHoatLapMoiOrKhoiPhucSuDung() {
      const loaiHinhId = this.formThongTinKhachHang.selectedLoaiHinh || -1

      const isSipPost = [LoaiHinhTB.IMS_SIP, LoaiHinhTB.IMS_POTS].includes(loaiHinhId)
      const isSipTrunk = LoaiHinhTB.IMS_SIPTRUNKING === loaiHinhId
      const IS_USING_SERVICE_SIPTRINK = this.isExistThamSo('USING_SERVICE_SIPTRINK_NEW')

      if (!confirm(`Bạn có chắc chắn hủy kích hoạt số máy ${this.formThongTinKhachHang.maThueBao} không?`)) {
        return
      }

      if (isSipPost) {
        console.log('this.selectedIMS')
        console.log(this.selectedIMS)
        const huyIMSAccount = await HuyThueBao(this.axios, this.selectedIMS.ma_tb)
        if (!huyIMSAccount === true) {
          this.$toast.error('Hủy kích hoạt không thành công, xảy ra lỗi :\n' + huyIMSAccount)
          this.loading(false)
          return
        }
      } else if (isSipTrunk && IS_USING_SERVICE_SIPTRINK) {
        const thanhLyDuongTrunkResponse = await ThanhLyDuongTrunk(this.axios, {
          level: 4,
          msisdn: '',
          matb: this.formThongTinKhachHang.maThueBao
        })
        if (!thanhLyDuongTrunkResponse === true) {
          this.$toast.error('Hủy kích hoạt không thành công, xảy ra lỗi :\n' + thanhLyDuongTrunkResponse)
          this.loading(false)
          return
        }
      } else {
        const thanhLyThueBaoResponse = await ThanhLyThueBao(this.axios, this.selectedIMS.ma_tb)
        if (thanhLyThueBaoResponse !== true) {
          this.$toast.error('Hủy kích hoạt không thành công, xảy ra lỗi :\n' + thanhLyThueBaoResponse)
          this.loading(false)
          return
        }
      }
      await this.HuyKichHoatThueBao()
    },

    async huyKichHoatChuyenDoiLoaiHinh() {
      const bd_dichvuvt_id = await getThongTinKichHoatThueBao(this.axios, this.selectedIMS.hdtb_id, 3)

      if (bd_dichvuvt_id.hasError || isNaN(bd_dichvuvt_id)) {
        this.$toast.error('Lỗi lấy dữ liệu dịch vụ viễn thông!\n' + bd_dichvuvt_id.message)
        return
      }

      let bd_loaitb_id = await getThongTinKichHoatThueBao(this.axios, this.selectedIMS.hdtb_id, 4)

      if (bd_loaitb_id.hasError || isNaN(bd_loaitb_id)) {
        this.$toast.error('Lỗi lấy dữ liệu loại thuê bao! \n' + bd_loaitb_id.message)
        return
      } else {
        bd_loaitb_id = Number(bd_loaitb_id)
      }

      const bd_loaitb_id_vtn = await getThongTinKichHoatLoaiHinhThueBao(this.axios, bd_loaitb_id, 3)

      if (bd_loaitb_id_vtn.hasError) {
        this.$toast.error('Lỗi lấy dữ liệu loại hình thuê bao!\n' + bd_loaitb_id_vtn.message)
        return
      }

      const loaiHinhTB = Number(this.formThongTinKhachHang.selectedLoaiHinh)
      let huyThueBaoIMShoacVTN = false

      if (Number(bd_dichvuvt_id) === DichVuVienThong.CO_DINH) {
        if (loaiHinhTB === LoaiHinhTB.IMS_POTS || loaiHinhTB === LoaiHinhTB.IMS_SIP) {
          huyThueBaoIMShoacVTN = await this.IMSHuyThueBao(this.formThongTinKhachHang.maThueBao)
        } else {
          huyThueBaoIMShoacVTN = await this.VTNThanhLyThueBao(this.formThongTinKhachHang.maThueBao)
        }
        if (huyThueBaoIMShoacVTN) {
          await this.HuyKichHoatThueBao()
          return
        }
      }

      if (Number(bd_dichvuvt_id) === DichVuVienThong.IMS) {
        if (loaiHinhTB === LoaiHinhTB.IMS_POTS || loaiHinhTB === LoaiHinhTB.IMS_SIP) {
          if (bd_loaitb_id !== LoaiHinhTB.IMS_POTS && bd_loaitb_id !== LoaiHinhTB.IMS_SIP) {
            if ((await this.IMSHuyThueBao(this.formThongTinKhachHang.maThueBao)) && (await this.VTNKhoiPhucThueBao(bd_loaitb_id_vtn))) {
              await this.HuyKichHoatThueBao()
            }
          } else {
            if (await this.IMSHuyThueBao(this.formThongTinKhachHang.maThueBao)) {
              await this.HuyKichHoat_LapMoiSipPost(bd_loaitb_id, bd_loaitb_id_vtn)
            }
          }
        } else {
          if (await this.VTNThanhLyThueBao(this.formThongTinKhachHang.maThueBao)) {
            await this.HuyKichHoat_LapMoiSipPost(bd_loaitb_id, bd_loaitb_id_vtn)
          }
        }
      }
    },

    //  ims.HuyThueBao
    async IMSHuyThueBao(maThueBao) {
      const resultHuyThueBao = await HuyThueBao(this.axios, maThueBao)
      if (resultHuyThueBao !== true) {
        this.$toast.error('Hủy kích hoạt không thành công, xảy ra lỗi: (Huỷ thuê bao)\n' + resultHuyThueBao)
        this.loading(false)
        return false
      }
      return true
    },

    // ims.KhoiTaoThueBao
    async IMSKhoiTaoThueBao(bd_loaitb_id_vtn) {
      const result = await ims.KhoiTaoThueBao(this.axios, this.$root.token.getMaTinh(), this.formThongTinKhachHang.maThueBao, this.formThongTinKhachHang.maThueBao, this.formThongTinHoanCong.password, bd_loaitb_id_vtn, 'FixOfflineCharging')

      if (result !== true) {
        this.$toast.error('Hủy kích hoạt không thành công, xảy ra lỗi: (Khởi tạo thuê bao IMS)\n' + result)
        this.loading(false)
        return false
      }
      return true
    },

    //  vtn_service.ThanhLyThueBao
    async VTNThanhLyThueBao(maThueBao) {
      const resultThanhLyThueBao = await ThanhLyThueBao(this.axios, maThueBao)
      if (resultThanhLyThueBao !== true) {
        this.$toast.error('Hủy kích hoạt không thành công, xảy ra lỗi: (Thanh lý thuê bao)\n' + resultThanhLyThueBao)
        this.loading(false)
        return false
      }
      return true
    },

    // vtn_service.KhoiPhucThueBao
    async VTNKhoiPhucThueBao(bd_loaitb_id_vtn) {
      const resultKhoiPhucThueBao = await KhoiPhucThueBao(this.axios, {
        hdtb_id: this.selectedIMS.hdtb_id,
        bd_loaitb_id_vtn
      })
      if (resultKhoiPhucThueBao !== true) {
        this.$toast.error('1941 - Có lỗi trong quá trình kích hoạt đặt mới trên VTN\n' + resultKhoiPhucThueBao)
        this.loading(false)
        return false
      }
      return true
    },

    // hàm nhỏ trong phần huỷ kích hoạt thuê bao
    async HuyKichHoat_LapMoiSipPost(bd_loaitb_id, bd_loaitb_id_vtn) {
      const resultKhoiTaoThueBao = await this.IMSKhoiTaoThueBao(bd_loaitb_id_vtn)
      if (!resultKhoiTaoThueBao) {
        return false
      }

      if (bd_loaitb_id === LoaiHinhTB.IMS_POTS || bd_loaitb_id === LoaiHinhTB.IMS_SIP) {
        const kt_sd_dvgt = await getDataTable(this.axios, {
          id_neo: 'dichvugt_id',
          in_table: 'css.sudung_dvgt',
          in_dk: `where thuebao_id = ${this.selectedIMS.hdtb_id}`
        })

        if (kt_sd_dvgt !== undefined && kt_sd_dvgt !== null && Number(kt_sd_dvgt) !== -1) {
          await ThayDoiDVGT_Danhba(this.axios, {
            phanvung_id: this.$root.token.getPhanVungID(),
            thuebao_id: this.selectedIMS.thuebao_id,
            type: bd_loaitb_id_vtn,
            vma_tb: this.formThongTinKhachHang.maThueBao
          })
        }
        await this.HuyKichHoatThueBao()
      } else {
        if (await this.VTNKhoiPhucThueBao(bd_loaitb_id_vtn)) {
          await this.HuyKichHoatThueBao()
        }
      }
    },

    async HuyKichHoatThueBao() {
      await huyKichHoatIMS(this.axios, {
        hdtb_id: this.selectedIMS.hdtb_id,
        ip_kh: '',
        ma_tb: this.formThongTinKhachHang.maThueBao,
        ngay_kh: toDay(),
        nguoi_kh: this.$root.context.user.getTenNhanVien(),
        status: 0 // TRANGTHAI_DONGBO.CHUADONGBO
      })
        .then(() => {
          this.$toast.success(`Hủy kích hoạt thuê bao ${this.formThongTinKhachHang.maThueBao} thành công!`)
        })
        .catch((err) => {
          this.$toast.error(err.data.message_detail)
        })
      this.$nextTick(() => {
        this.loadDataTableIMS(this.selectedIMS.phieu_id)
      })
    },
    // Kích hoạt ims
    async btnKichHoatClick() {
      this.frmHCIMSKickHoat.input = {
        vhdtb_id: this.selectedIMS.hdtb_id,
        vma_tb: this.formThongTinKhachHang.maThueBao,
        user: this.formThongTinHoanCong.username,
        pass: this.formThongTinHoanCong.password,
        systemID: this.formThongTinHoanCong.system,
        rack: this.formThongTinHoanCong.rack,
        shelf: this.formThongTinHoanCong.shelf,
        vslot: this.formThongTinHoanCong.slot,
        port: this.formThongTinHoanCong.port,
        ONT: this.formThongTinHoanCong.VPI,
        CVLAN: this.formThongTinHoanCong.VCI
      }

      this.ejsDialog.header = 'Kích hoạt IMS'
      this.ejsDialog.name = 'frmHCIMSKickHoat'
      this.$refs.ejsDialog.show()
    },
    async xuLyKichHoat() {
      const DONGBOTEST = await DongBoTestAPI(this.axios)
      const canUpdateIms = await this.updateIms('kich_hoat')
      if (!canUpdateIms) {
        return
      }
      this.loading(true)
      // try {
      const loaiHinhId = this.formThongTinKhachHang.selectedLoaiHinh || -1
      const isSipPost = [LoaiHinhTB.IMS_SIP, LoaiHinhTB.IMS_POTS].includes(loaiHinhId)
      const isSipTrunk = LoaiHinhTB.IMS_SIPTRUNKING === loaiHinhId
      const IS_USING_SERVICE_SIPTRINK = this.isExistThamSo('USING_SERVICE_SIPTRINK_NEW')

      if (DONGBOTEST) {
        const loaiHD = Number(this.formThongTinKhachHang.selectedLoaiHDPL)
        if (loaiHD === LoaiHopDong.DAT_MOI || loaiHD === LoaiHopDong.KHOIPHUC_SD) {
          if (confirm(`Bạn có chắc chắn kích hoạt số máy ${this.formThongTinKhachHang.maThueBao} hay không?`)) {
            if (isSipPost) {
              await this.kichHoat_LapMoi_IMS_V2(loaiHD) // hàm cũ KichHoat_DM_IMS_V2
            } else if (isSipTrunk && IS_USING_SERVICE_SIPTRINK) {
              await this.kichHoat_LapMoi_IMS_V3()
            } else {
              await this.kichHoat_LapMoi_IMS()
            }
          }
        }

        if (loaiHD === LoaiHopDong.DI_CHUYEN) {
          if (confirm(`Bạn có chắc chắn kích hoạt số máy ${this.formThongTinKhachHang.maThueBao} hay không?`)) {
            if (isSipPost) {
              await this.kichHoat_DichChuyen_IMS_V2(loaiHD) // hàm cũ KichHoat_DM_IMS_V2
            } else if (isSipTrunk && IS_USING_SERVICE_SIPTRINK) {
              await this.kichHoat_DichChuyen_IMS_V3()
            } else {
              await this.kichHoat_DichChuyen_IMS()
            }
          }
        }
        if (loaiHD === LoaiHopDong.CHUYENDOI_LH) {
          if (confirm(`Bạn có chắc chắn kích hoạt số máy ${this.formThongTinKhachHang.maThueBao} hay không?`)) {
            if (isSipPost) {
              await this.kichHoat_ChuyenDoiLoaiHinh_IMS_V2(loaiHD) // hàm cũ KichHoat_CDLH_IMS_V2
            } else if (isSipTrunk && IS_USING_SERVICE_SIPTRINK) {
              await this.kichHoat_ChuyenDoiLoaiHinh_IMS_V3() // hàm cũ KichHoat_CDLH_IMS_V3
            } else {
              await this.kichHoat_LapMoi_IMS() // hàm cũ KichHoat_CDLH_IMS
            }
          }
        }
      } else {
        var result = await capNhatStatusDongBoHopDong(this.axios, {
          p_status: TrangThaiDongBo.DONGBO_CM,
          p_hdtb_id: this.selectedIMS.hdtb_id
        })

        if (result !== true) {
          this.$toast.error('Có lỗi: ' + result)
          this.loading(false)
          return
        }

        if (this.PHAILAM('CAPNHAT_NGAYKH_KH')) {
          const now = new Date()
          const kyHoaDon = `${now.getFullYear()}${now.getMonth() + 2}`
          const checkKyHoaDon = await checkTaoDBThang(this.axios, this.$root.token.getPhanVungID(), kyHoaDon)
          if (checkKyHoaDon !== '0') {
            this.$toast.error(checkKyHoaDon)
            this.loading(false)
            return
          }
          const ngayHT = await capNhatNgayHoanThanh(this.axios, {
            p_hdtb_id: this.selectedIMS.hdtb_id,
            p_ngay_ht: formatDateToParam(this.formThongTinHoanCong.ngayHT)
          })
          if (ngayHT !== true) {
            this.$toast.error('Có lỗi: ' + ngayHT)
            this.loading(false)
            return
          }
          const ngayKichHoat = await capNhatNgayKichHoat(this.axios, {
            p_hdtb_id: this.selectedIMS.hdtb_id,
            p_ngay_kh: formatDateToParam(new Date())
          })
          if (ngayKichHoat !== true) {
            this.$toast.error('Có lỗi: ' + ngayKichHoat)
            this.loading(false)
            return
          }
        }
        this.$toast.success(`2087 - Kích hoạt số máy ${this.formThongTinKhachHang.maThueBao} lên VTN thành công!`)
      }
      this.loading(false)
      this.loadDataTableIMS(this.selectedIMS.phieu_id)
      // } catch (ex) {
      //   this.loading(false);
      //   this.$toast.error('Có lỗi: ' + ex);
      // }
    },

    // hàm cũ KichHoat_DM_IMS_V2
    async kichHoat_LapMoi_IMS_V2(loaihd_id) {
      const loaitb_id_vtn = await getLoaiHinhThueBao(this.axios, this.formThongTinKhachHang.selectedLoaiHinh)
      let statusKhoiTaoIMS = null
      if (this.kieuXacThuc == 'user_pass') {
        statusKhoiTaoIMS = await ims.KhoiTaoThueBao(this.axios, this.$root.token.getMaTinh(), this.formThongTinKhachHang.maThueBao, this.formThongTinKhachHang.maThueBao, this.formThongTinHoanCong.password, loaitb_id_vtn, 'FixOfflineCharging')
      }

      if (this.kieuXacThuc == 'location') {
        let kq_sp_lay_dauso_ims_theo_tinh = await api.sp_lay_dauso_ims_theo_tinh(this.axios, {
          p_tentat: this.$root.token.getMaTinh()
        })
        let dauSoIMS = ''
        if (kq_sp_lay_dauso_ims_theo_tinh.data.data != null && kq_sp_lay_dauso_ims_theo_tinh.data.data.length) {
          dauSoIMS = kq_sp_lay_dauso_ims_theo_tinh.data.data[0].dauso_ims
        }
        
        let kq = await api.sps_service_ims_7ts(this.axios, {
          msisdn: '84' + dauSoIMS + this.formThongTinKhachHang.maThueBao,
          imsi: '84' + dauSoIMS + this.formThongTinKhachHang.maThueBao,
          ism_res_3: '',
          subtype: loaitb_id_vtn,
          systemId: this.formThongTinHoanCong.system,
          rack: this.formThongTinHoanCong.rack,
          shelf: this.formThongTinHoanCong.shelf,
          slot: this.formThongTinHoanCong.slot,
          port: this.formThongTinHoanCong.slot,
          ont: this.formThongTinHoanCong.VPI,
          cvlan: this.formThongTinHoanCong.VCI
        })

        if (kq.data.data== 'ok') {
          statusKhoiTaoIMS = true
        } else {
          statusKhoiTaoIMS = kq.data.message
        }
      }

      if (statusKhoiTaoIMS === true) {
        let kq_sp_capnhat_kieuxt_ims = await api.sp_capnhat_kieuxt_ims(this.axios, { p_id: this.selectedIMS.hdtb_id, p_loai: 1, p_kieuxt: this.kieuXacThuc == 'user_pass' ? 1 : 2 })

        if (this.kieuXacThuc == 'user_pass') {
          try {
            var input = {
              vphanvung_id: await this.$root.token.getPhanVungID(),
              vthuebao_id: this.selectedIMS.thuebao_id,
              vhdtb_id: this.selectedIMS.hdtb_id,
              vloaihd_id: loaihd_id
            }
            var area = await ims.lay_thong_tin_diachi(this.axios, input)
            let dauso_ims = await ims.layDauSoIMSTheoTinh(this.axios, this.$root.token.getMaTinh())
            if (area) {
              input = {
                ma_tb: `84${dauso_ims}${this.formThongTinKhachHang.maThueBao}`,
                area: area
              }
              var change_location = await ims.CHANGE_LOCATION(this.axios, input)
              if (change_location && change_location.error_code === 'BSS-00000000') {
                this.$toast.success(change_location.message)
              } else {
                this.$toast.error(change_location.message)
              }
            }
          } catch (e) {
            this.$toast.error('Đổi location cho thuê bao ' + this.formThongTinKhachHang.maThueBao + ' không thành công !')
          }
        }

        await this.kichHoatDichVuGiaTang()
      } else {
        this.$toast.error('2106 - Có lỗi trong quá trình khởi tạo trên hệ thống IMS mới. Lỗi:\n ' + statusKhoiTaoIMS)
      }
    },

    async kichHoat_LapMoi_IMS_V3() {
      if (!this.formThongTinHoanCong.soLuongCuocGoi || this.formThongTinHoanCong.soLuongCuocGoi === '0' || isNaN(this.formThongTinHoanCong.soLuongCuocGoi)) {
        this.formThongTinHoanCong.soLuongCuocGoi = '20'
      }

      if (!this.formThongTinHoanCong.IPKhachHang) {
        this.$toast.error('IP không được để trống.')
        this.$refs.formThongTinHoanCong.focusToIPKhachHang()
        return
      }
      const regexIPV4 = new RegExp('^((25[0-5]|(2[0-4]|1\\d|[1-9]|)\\d)\\.?\\b){4}$')

      if (!regexIPV4.test(this.formThongTinHoanCong.IPKhachHang)) {
        this.$toast('IP không đúng định dạng.')
        this.$refs.formThongTinHoanCong.focusToIPKhachHang()
        return
      }

      const capnhatSL = capNhatSoLuongCuocGoiIP(this.axios, {
        vphanvung_id: this.$root.token.getPhanVungID(),
        vsl_goicuoc: this.formThongTinHoanCong.soLuongCuocGoi,
        vip_kh: this.formThongTinHoanCong.IPKhachHang,
        vhdtb_id: this.selectedIMS.hdtb_id
      })

      if (capnhatSL !== true) {
        this.$toast.error(capnhatSL)
        return
      }
      const khoiTaoThueBao = KhoiTaoThueBao_siptrunk(this.axios, {
        msisdn: this.formThongTinKhachHang.maThueBao,
        trunkId: '',
        ip: this.formThongTinHoanCong.IPKhachHang,
        tongdai: '',
        area: '',
        phanvung_id: this.$root.token.getPhanVungID(),
        sokenh: this.formThongTinHoanCong.soLuongCuocGoi
      })
      if (khoiTaoThueBao === true) {
        await this.kichHoatDichVuGiaTang()
      } else {
        this.$toast.error('2151 - Có lỗi trong quá trình khởi tạo trên hệ thống IMS mới. Lỗi: ' + khoiTaoThueBao)
      }
    },
    // hàm cũ KichHoat_DM_IMS
    async kichHoat_LapMoi_IMS() {
      const loaitb_id_vtn = getThongTinLoaiHinhThueBao(this.axios, {
        p_phanvung: this.$root.token.getPhanVungID(),
        p_param: this.formThongTinKhachHang.selectedLoaiHinh,
        p_type: 4
      })

      const datMoiThueBaoResult = DatMoiThueBao(this.axios, {
        hdtb_id: this.selectedIMS.hdtb_id,
        loaitb_id_vtn
      })

      if (datMoiThueBaoResult) {
        const kichHoat = await CAPNHAT_NGAYKH_STATUS_V2(this.axios, {
          hdtb_id: this.selectedIMS.hdtb_id,
          ngay_kh: formatDateToParam(new Date()),
          status: 5, // TRANGTHAI_DONGBO.DONGBO_CM,
          nguoi_kh: this.$root.token.getMaNhanVien(),
          ip_kh: ''
        })
        if (kichHoat === true) {
          this.$toast.success('Khởi tạo số máy ' + this.formThongTinKhachHang.maThueBao + ' lên hệ thống IMS mới thành công!')
        } else {
          this.$toast.error('2178 - Có lỗi trong quá trình khởi tạo trên hệ thống IMS mới. Lỗi: ' + kichHoat)
        }
      }
    },
    // hàm cũ KichHoat_DM_IMS_V2
    async kichHoat_DichChuyen_IMS_V2(loaiHD) {
      const ma_tb_cu = this.selectedIMS.ma_tb_cu
      const ma_tb = this.selectedIMS.ma_tb
      if (ma_tb_cu !== ma_tb) {
        const resultHuyThueBao = await HuyThueBao(this.axios, this.formThongTinKhachHang.maThueBao)
        if (resultHuyThueBao !== true) {
          this.$toast.error(resultHuyThueBao)
          return
        }
        await this.kichHoat_LapMoi_IMS_V2(loaiHD)
      } else {
        await this.kichHoatDichVuGiaTang()
      }
    },

    async kichHoat_DichChuyen_IMS_V3() {
      const ma_tb_cu = this.selectedIMS.ma_tb_cu
      const ma_tb = this.selectedIMS.ma_tb
      if (ma_tb_cu !== ma_tb) {
        const resultHuyThueBao = await HuyThueBao(this.axios, this.formThongTinKhachHang.maThueBao)
        if (resultHuyThueBao !== true) {
          this.$toast.error(resultHuyThueBao)
          return
        }
        await this.kichHoat_LapMoi_IMS_V3()
      } else {
        await this.kichHoatDichVuGiaTang()
      }
    },

    async kichHoat_DichChuyen_IMS() {
      const ma_tb_cu = this.selectedIMS.ma_tb_cu
      const ma_tb = this.selectedIMS.ma_tb
      if (ma_tb_cu !== ma_tb) {
        const resultThanhLyThueBao = await ThanhLyThueBao(this.axios, ma_tb_cu)
        if (resultThanhLyThueBao !== true) {
          this.$toast.error(resultThanhLyThueBao)
          return
        }
      }
      // Ở trong code cũ. Dù có kết quả thế nào vẫn phải kích hoạt IMS (code trong if vào else giống nhau 100%)
      await this.kichHoat_LapMoi_IMS()
    },

    async kichHoat_ChuyenDoiLoaiHinh_IMS_V2(loaiHD) {
      // const bd_dichvuvt_id = await getDataTable(this.axios, {
      //   id_neo: 'dichvugt_id',
      //   in_table: 'css.bd_thuebao',
      //   in_dk: `where hdtb_id = ${this.selectedIMS.hdtb_id}`
      // })
      // if (isNaN(bd_dichvuvt_id)) {
      //   this.$toast.error('Lỗi lấy dữ liệu dịch vụ gia tăng!')
      //   return
      // }

      // const bd_loaitb_id = await getDataTable(this.axios, {
      //   id_neo: 'loaitb_id',
      //   in_table: 'css.bd_thuebao',
      //   in_dk: `where hdtb_id = ${this.selectedIMS.hdtb_id}`
      // })
      // if (isNaN(bd_loaitb_id)) {
      //   this.$toast.error('Lỗi lấy dữ liệu loại thuê bao!')
      //   return
      // }

      let bd_dichvuvt_id = await getThongTinKichHoatThueBao(this.axios, this.selectedIMS.hdtb_id, 3)

      if (bd_dichvuvt_id.hasError || isNaN(bd_dichvuvt_id)) {
        this.$toast.error('Lỗi lấy dữ liệu dịch vụ viễn thông!\n' + bd_dichvuvt_id.message)
        return
      }

      let bd_loaitb_id = await getThongTinKichHoatThueBao(this.axios, this.selectedIMS.hdtb_id, 4)

      if (bd_loaitb_id.hasError || isNaN(bd_loaitb_id)) {
        this.$toast.error('Lỗi lấy dữ liệu loại thuê bao! \n' + bd_loaitb_id.message)
        return
      } else {
        bd_loaitb_id = Number(bd_loaitb_id)
      }

      const IS_USING_SERVICE_SIPTRINK = this.isExistThamSo('USING_SERVICE_SIPTRINK_NEW')

      if (Number(bd_dichvuvt_id) === DichVuVienThong.IMS) {
        if (Number(bd_loaitb_id) === LoaiHinhTB.IMS_POTS || Number(bd_loaitb_id) === LoaiHinhTB.IMS_SIP) {
          const resultHuyThueBao = await HuyThueBao(this.axios, this.selectedIMS.ma_tb_cu)
          if (resultHuyThueBao !== true) {
            this.$toast.error(resultHuyThueBao)
            return
          }
        } else if (Number(bd_loaitb_id) === LoaiHinhTB.IMS_SIPTRUNKING && IS_USING_SERVICE_SIPTRINK) {
          const thanhlyDuongTrunk = await ThanhLyDuongTrunk(this.axios, {
            level: 4,
            msisdn: '',
            matb: this.formThongTinKhachHang.maThueBao
          })
          if (thanhlyDuongTrunk !== true) {
            this.$toast.error(thanhlyDuongTrunk)
            return
          }
        }
        const resultThanhLyThueBao = await ThanhLyThueBao(this.axios, this.formThongTinKhachHang.maThueBao)
        if (resultThanhLyThueBao !== true) {
          this.$toast.error(resultThanhLyThueBao)
          return
        }
      }

      await this.kichHoat_LapMoi_IMS_V2(loaiHD)
    },

    // hàm cũ KichHoat_CDLH_IMS_V3
    async kichHoat_ChuyenDoiLoaiHinh_IMS_V3() {
      const bd_dichvuvt_id = await getThongTinKichHoatThueBao(this.axios, this.selectedIMS.hdtb_id, 3)

      if (bd_dichvuvt_id.hasError || isNaN(bd_dichvuvt_id)) {
        this.$toast.error('Lỗi lấy dữ liệu dịch vụ viễn thông!\n' + bd_dichvuvt_id.message)
        return
      }

      let bd_loaitb_id = await getThongTinKichHoatThueBao(this.axios, this.selectedIMS.hdtb_id, 4)

      if (bd_loaitb_id.hasError || isNaN(bd_loaitb_id)) {
        this.$toast.error('Lỗi lấy dữ liệu loại thuê bao! \n' + bd_loaitb_id.message)
        return
      } else {
        bd_loaitb_id = Number(bd_loaitb_id)
      }

      const IS_USING_SERVICE_SIPTRINK = this.isExistThamSo('USING_SERVICE_SIPTRINK_NEW')

      if (Number(bd_dichvuvt_id) === DichVuVienThong.IMS) {
        if (bd_loaitb_id === LoaiHinhTB.IMS_POTS || bd_loaitb_id === LoaiHinhTB.IMS_SIP) {
          const resultHuyThueBao = await HuyThueBao(this.axios, this.formThongTinKhachHang.maThueBao)
          if (resultHuyThueBao !== true) {
            this.$toast.error(resultHuyThueBao)
            return
          }
        } else if (Number(bd_loaitb_id) === LoaiHinhTB.IMS_SIPTRUNKING && IS_USING_SERVICE_SIPTRINK) {
          const thanhlyDuongTrunk = await ThanhLyDuongTrunk(this.axios, {
            level: 4,
            msisdn: '',
            matb: this.formThongTinKhachHang.maThueBao
          })
          if (thanhlyDuongTrunk !== true) {
            this.$toast.error(thanhlyDuongTrunk)
            return
          }
        }
        const resultThanhLyThueBao = await ThanhLyThueBao(this.axios, this.selectedIMS.ma_tb_cu)
        if (resultThanhLyThueBao !== true) {
          this.$toast.error(resultThanhLyThueBao)
          return
        }
      }

      await this.kichHoat_LapMoi_IMS_V3()
    },

    async kichHoatDichVuGiaTang(messageSuccess) {
      let kiemTraDK_DCGT = await getDataTable(this.axios, {
        id_neo: 'dichvugt_id',
        in_table: 'css.dangky_dvgt',
        in_dk: `where hdtb_id = ${this.selectedIMS.hdtb_id}`
      })
      kiemTraDK_DCGT = Number(kiemTraDK_DCGT)
      if (kiemTraDK_DCGT !== -1) {
        const kq = await thayDoiDVGT(this.axios, {
          hdtb_id: this.selectedIMS.hdtb_id,
          type: kiemTraDK_DCGT,
          vma_tb: this.formThongTinKhachHang.maThueBao
        })
        if (kq !== true) {
          this.$toast.error(kq)
          return
        }
      }
      const kichHoat = await CAPNHAT_NGAYKH_STATUS_V2(this.axios, {
        hdtb_id: this.selectedIMS.hdtb_id,
        ngay_kh: formatDateToParam(new Date()),
        status: 5, // TRANGTHAI_DONGBO.DONGBO_CM,
        nguoi_kh: this.$root.token.getMaNhanVien(),
        ip_kh: ''
      })
      if (kichHoat === true) {
        this.$toast.success(messageSuccess || 'Khởi tạo số máy ' + this.formThongTinKhachHang.maThueBao + ' lên hệ thống IMS mới thành công!')
      } else {
        this.$toast.error('2358 - Có lỗi trong quá trình khởi tạo trên hệ thống IMS mới. Lỗi: ' + kichHoat)
      }
    },
    onGridThuebaoImsSelectedItemsChanged(e) {
      console.log('onGridThuebaoImsSelectedItemsChanged')
      console.log(e)
      if (e.length > 0) {
        this.gridThueBaoIms.selected = e
      } else {
        // this.resetPage()
      }
    },
    onGridThuebaoImsRowSelected(e) {
      if (e.data instanceof Array) {
      } else {
        this.gridThueBaoIms.value = e.data
        console.log('this.gridThueBaoIms.value')
        console.log(this.gridThueBaoIms.value)
        this.selectedIMSChange(e.data)
      }
      console.log('onGridThuebaoImsRowSelected')
      console.log(e)
    },
    async DauNoiThueBao_OnClick() {
      this.dauNoiThueBaoDialog = true
      this.$refs.modalDauNoiThueBao.show()
    },
    async DauNoiThueBao_OnClose() {
      this.dauNoiThueBaoDialog = false
    },
    uuidv4() {
      return ([1e7] + -1e3 + -4e3 + -8e3 + -1e11).replace(/[018]/g, (c) => (c ^ (crypto.getRandomValues(new Uint8Array(1))[0] & (15 >> (c / 4)))).toString(16))
    },
    ejsDialogBeforeOpen() {
      let width = null
      if (this.ejsDialog.name == 'frmNhapText') {
        width = 'auto'
      } else {
        width = '95%'
      }
      this.ejsDialog.config = {
        ...this.ejsDialog.config,
        width: width,
        height: 'auto',
        position: { X: 'center', Y: 'center' }
      }
    },
    ejsDialogBeforeClose() {
      this.ejsDialog.name = null
      this.ejsDialog.config.width = '0'
      this.ejsDialog.config.height = '0'
      this.ejsDialog.config.position = { X: 'center', Y: 'center' }
    },
    async frmHCIMSKickHoat_tsbtnChapNhan_Clicked(data) {
      console.log('frmHCIMSKickHoat_tsbtnChapNhan_Clicked')
      console.log(data)
      this.kieuXacThuc = data.type
      await this.xuLyKichHoat()
      this.$refs.ejsDialog.hide()
    }
  },
  watch: {
    async checkedLoaiPhieu() {
      this.loading(true)
      await this.loadDataTableIMS()
      this.loading(false)
      F
    }
  }
}
</script>

<style scoped>
.modal-dau-noi-thue-bao {
  height: 900px;
  overflow-y: auto;
}

.traphieu-content {
  width: 1200px;
}

.popup-box {
  background: #fafafa38;
}

.traphieu .page-content {
  position: unset;
}

/deep/ #popupComponentChuyenTos__BV_modal_body .popup-box .page-content {
  position: inherit !important;
}

/deep/ .e-grid td.e-active {
  background: rgba(0, 0, 0, 0.17);
  color: #333;
}
#declare-ims {
  position: absolute;
  top: 0px;
  // top: 90px;
  // padding: 8px 16px 16px 16px;
  left: 0px;
  right: 0px;
  bottom: 0px;
  overflow: auto;
  overflow-x: hidden;
}
</style>
