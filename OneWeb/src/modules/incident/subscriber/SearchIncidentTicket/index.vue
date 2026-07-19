
<template>
  <div class="main-wrapper main-wrapper-tskt">
    <div class="breadcrumb-top">
      <div class="main-title">Tra cứu phiếu giao đến báo hỏng</div>
      <ul class="breadcrumb">
        <li class="breadcrumb-item">
          <a href="#"><span class="nc-icon-outline ui-1_home-simple"></span> Trang chủ </a>
        </li>
        <li class="breadcrumb-item"><a href="#">Tra cứu</a></li>
        <li class="breadcrumb-item"><a href="#">Báo hỏng 119</a></li>
        <li class="breadcrumb-item active">Tra cứu phiếu giao đến báo hỏng</li>
      </ul>
    </div>
    <div class="list-actions-top">
      <ul class="list">
        <li>
          <a href="#" @click.prevent="getDsPhieuBaoHong"> <span class="icon one-search"></span>Tìm kiếm </a>
        </li>

        <li>
          <a href="#" @click.prevent="exportFile"> <span class="icon one-download"></span>Xuất file </a>
        </li>

        <li>
          <a href="#" @click.prevent="exportExcel"> <span class="icon one-excel"></span>Xuất Excel </a>
        </li>

        <li>
          <a href="#" @click.prevent="ShowLsHen"> <span class="icon one-sign"></span>LS hẹn </a>
        </li>
        <li>
          <a href="#" @click.prevent="ShowCapNhatTienTrinh"> <span class="icon nc-icon-glyph business_chart-bar-32"></span>Cập nhật tiến trình </a>
        </li>
        <li>
          <a href="#" @click.prevent="ShowThongSoKT"> <span class="icon nc-icon-glyph ui-1_settings"></span>Thông số KT </a>

        </li>
        <li>
          <a href="#" @click.prevent="show_DSTienTrinh">
            <span class="icon one-tc"></span>TC Tiến trình
          </a>
        </li>
      </ul>
    </div>
    <div class="page-content">
      <div class="box-form">
        <div class="legend-title">Thông tin phiếu báo hỏng</div>
        <div class="row">
          <div class="col-sm-6 col-12">
            <div class="row">
              <div class="col-md-12 col-12">
                <div class="info-row">
                  <div class="key w90">
                    <div class="check-action">
                      <input type="checkbox" class="check" v-model="fCheck.DichVuVT" />
                      <span class="name">Dịch vụ</span>
                    </div>
                  </div>
                  <div class="value">
                    <SelectExt :disabled="!fCheck.DichVuVT" v-bind:class="{ disabled: !fCheck.DichVuVT }" v-model.trim="DichVuVTId" :dataSource="DmDichVu" dataTextField="ten_dvvt" dataValueField="dichvuvt_id"> </SelectExt>
                  </div>
                </div>
              </div>
            </div>

            <div class="row">
              <div class="col-sm-6 col-12">
                <div class="info-row">
                  <div class="key w90">
                    <div class="check-action">
                      <input type="checkbox" class="check" v-model="fCheck.Ngay" />
                      <span class="name">Từ ngày</span>
                    </div>
                  </div>
                  <div class="value">
                    <div class="input-icon-right">
                      <date-picker v-bind:class="{ disabled: !fCheck.Ngay }" :format="dateFormat" :value-type="dateFormat" :time-title-format="dateFormat" v-model="txtFromDate" :type="typeFormat">
                        <template #icon-calendar>
                          <span class="icon one-calendar"></span>
                        </template>
                      </date-picker>
                    </div>
                  </div>
                </div>
              </div>
              <div class="col-sm-6 col-12">
                <div class="info-row">
                  <div class="key w60">Đến ngày</div>
                  <div class="value">
                    <div class="input-icon-right">
                      <date-picker v-bind:class="{ disabled: !fCheck.Ngay }" :format="dateFormat" :value-type="dateFormat" :time-title-format="dateFormat" v-model="txtToDate" :type="typeFormat">
                        <template #icon-calendar>
                          <span class="icon one-calendar"></span>
                        </template>
                      </date-picker>
                    </div>
                  </div>
                </div>
              </div>
            </div>
            <div class="info-row">
              <div class="key w90">
                <div class="check-action">
                  <input type="checkbox" class="check" v-model="fCheck.TTPhieu" />
                  <span class="name">TT Phiếu</span>
                </div>
              </div>
              <div class="value">
                <SelectExt :disabled="!fCheck.TTPhieu" v-bind:class="{ disabled: !fCheck.TTPhieu }" v-model.trim="TrangThaiPhieuId" :dataSource="DmTrangThaiPhieu" dataTextField="trangthai_hd" dataValueField="tthd_id"> </SelectExt>
              </div>
            </div>
            <div class="info-row">
              <div class="key w90">Trạng thái</div>
              <div class="value">
                <!-- :disabled="!fCheck.TrangThai" -->
                <Select2 :disabled="!fCheck.TrangThai || !fCheck.TTPhieu" v-model.trim="TrangThaiId" :options="DmTrangThai"> </Select2>
              </div>
            </div>
            <div class="info-row">
              <div class="key w90">
                <div class="check-action">
                  <input type="checkbox" class="check" v-model="fCheck.TTBaoHanh" />
                  <span class="name">TTBH</span>
                </div>
              </div>
              <div class="value">
                <SelectExt :disabled="!fCheck.TTBaoHanh" v-bind:class="{ disabled: !fCheck.TTBaoHanh }" v-model.trim="TrangThaiBhId" :dataSource="DmTrangThaiBaoHong" dataTextField="trangthai_bh" dataValueField="ttbh_id"> </SelectExt>
              </div>
            </div>
            <div class="info-row">
              <div class="key w90">
                <div class="check-action">
                  <input type="checkbox" class="check" v-model="fCheck.TramVT" />
                  <span class="name">Trạm VT</span>
                </div>
              </div>
              <div class="value">
                <SelectExt :disabled="!fCheck.TramVT" v-bind:class="{ disabled: !fCheck.TramVT }" v-model.trim="DonViId" :dataSource="DsDonVi" dataTextField="ten_dv" dataValueField="donvi_id"> </SelectExt>
              </div>
            </div>
          </div>
          <div class="col-sm-6 col-12">
            <div class="row">
              <div class="col-sm-6 col-12">
                <div class="info-row">
                  <div class="key w100">Số máy/Acc</div>
                  <div class="value">
                    <input type="text" class="form-control highlight" v-model.trim="txtSoMay" />
                  </div>
                </div>
              </div>
              <div class="col-sm-6 col-12">
                <div class="info-row">
                  <div class="key w60">Số ảo</div>
                  <div class="value">
                    <input type="text" class="form-control" v-model.trim="txtSoAo" />
                  </div>
                </div>
              </div>
            </div>
            <div class="info-row">
              <div class="key w100">Tên thuê bao</div>
              <div class="value">
                <input type="text" class="form-control" v-model="txtTenThueBao" />
              </div>
            </div>
            <div class="info-row">
              <div class="key w100">Địa chỉ LĐ</div>
              <div class="value">
                <input type="text" class="form-control" v-model="txtDiaChiLD" />
              </div>
            </div>
            <div class="info-row">
              <div class="key w100">Địa chỉ LĐ đầu</div>
              <div class="value">
                <input type="text" :disabled="!fCheck.DiaChiDau" class="form-control" v-model="txtDiaChiLDDau" />
              </div>
            </div>
            <div class="info-row">
              <div class="key w100">Địa chỉ LĐ cuối</div>
              <div class="value">
                <input type="text" :disabled="!fCheck.DiaChiCuoi" class="form-control" v-model="txtDiaChiLDCuoi" />
              </div>
            </div>
          </div>
        </div>
      </div>
      <div class="box-form">
        <div class="legend-title">
          <div class="pull-left">Danh sách phiếu giao đến</div>
          <div class="pull-right red">Tổng số : {{ TongSoPhieu }} - Phiếu mới : {{ PhieuMoi }} - Phiếu đã thực hiện : {{ PhieuThucHien }} - Phiếu trả lại : {{ PhieuTraLai }}</div>
          <div class="clearfix"></div>
        </div>
        <DataGrid
          v-bind:columns="DsPhieuBaoHong.headers2"
          :enabledSelectFirstRow="true"
          v-bind:dataSource="DsPhieuBaoHong.data"
          :showColumnCheckbox="false"
          :enable-paging-server="false"
          @rowClicked="ChangedRow"
          @rowDoubleClicked="gridDsPhieuBaoHong_rowDoubleClicked"
          @rowDataBound="gridDsPhieuBaoHong_rowDataBound"
          @queryCellInfo="gridDsPhieuBaoHong_queryCellInfo"
          :allowPaging="true"
          :showFilter="true"
          ref="gridDsPhieuBaoHong"
        >
        </DataGrid>
      </div>
    </div>

    <b-modal id="my-modal-ls-baohong" class="modal-dialog" size="lg" hide-footer :no-close-on-backdrop="true">
      <div slot="modal-title" class="popup-header">
        <div class="title"><span class="icon one-notepad"></span> Lịch sử hẹn báo hỏng</div>
      </div>
      <!-- <DataGrid v-bind:columns="DsLsPhieuBaoHong.headers2" :enabledSelectFirstRow="true" v-bind:dataSource="DsLsPhieuBaoHong.data" :showColumnCheckbox="false" :enable-paging-server="false" :allowPaging="false" :showFilter="true" ref="gridDsLsPhieuBaoHong"> </DataGrid> -->
    </b-modal>
    <b-modal id="my-modal-ts-kt" class="modal-dialog" size="lg" hide-footer :no-close-on-backdrop="true">
      <div slot="modal-title" class="popup-header">
        <div class="title"><span class="icon one-notepad"></span> Thông số kỹ thuật</div>
      </div>
      <div class="article">
        <p><span class="fw6">Thuê bao: </span>CÔNG TY TNHH DỊCH VỤ LAHU HĐ - Hoạt động bình thường - Cty TNHH, Cty CP, DNTN,... - Loai KH: Công ty TNHH</p>
        <p><span class="fw6">Liên hệ: </span>0941661155</p>
        <p><span class="fw6">Tốc độ: </span>Fiber50_GD3 - F500T500P0_d0 - IP dong - 100Mbps/100Mbps - Trọn gói</p>
        <p><span class="fw6">Mã truy cập: </span></p>
        <p><span class="fw6">Kết cuối: </span>SB01/O-LLQ/0503 - Vị trí 1: 7 - Vị trí 2:</p>
        <p><span class="fw6">Vị trí kết cuối: </span>Đầu ngõ 565, Lạc Long Quân, Phường Xuân La, Quận Tây Hồ, Hà Nội</p>
        <p><span class="fw6">Cáp gốc: </span></p>
        <p><span class="fw6">Ip tĩnh:</span></p>
        <p><span class="fw6">BRAS:</span></p>
        <p><span class="fw6">Dslam VDC: </span></p>
        <p><span class="fw6">Dslam tỉnh:</span></p>
        <p><span class="fw6">System:</span></p>
        <p><span class="fw6">Slot/Port/Rack/Sheft:</span></p>
      </div>
    </b-modal>
    <CapNhatTienTrinh ref="refCapNhatTienTrinh"></CapNhatTienTrinh>
    <popupLayDuLieuBC ref="dialogLayDuLieuBC"></popupLayDuLieuBC>
    <TraCuuThongSoKTModal :kieu="1" :thuebao_id="ThueBaoId" :dichvuvt_id="DichVuVTId" :ma_tb="txtSoMay" ref="traCuuThongSoKTModal" />
    <thongSoKyThuat ref="frmThongSoKT"></thongSoKyThuat>
    <ejs-dialog :enableResize="true" :allowDragging="true" :visible="false" :animationSettings="animationSettings" ref="dialogObjLsHenBH" :position="position" :close="closeDialog" :header="'Lịch sử hẹn báo hỏng'" showCloseIcon="true" width="60%" target="#app .main-wrapper">
      <div>
        <div class="list-actions-top">
          <ul class="list">
            <li>
              <a href="#" @click.prevent="ThoatLsBH"> <span class="icon ui-1_circle-remove nc-icon-glyph"></span> Thoát</a>
            </li>
          </ul>
        </div>
        <div class="popup-body">
          <div class="box-form">
            <!-- <div class="legend-title">Thông số kỹ thuật thuê bao</div> -->
            <div class="row">
              <DataGrid v-bind:columns="DsLsPhieuBaoHong.headers2" :enabledSelectFirstRow="true" v-bind:dataSource="DsLsPhieuBaoHong.data" :showColumnCheckbox="false" :enable-paging-server="false" :allowPaging="false" :showFilter="true" ref="gridDsLsPhieuBaoHong"> </DataGrid>
              <div class="col-sm-12 col-12"></div>
            </div>
          </div>
        </div>
      </div>
    </ejs-dialog>
    <ejs-dialog :enableResize="true" :allowDragging="true" :visible="false" :animationSettings="animationSettings" ref="dialogObjNghiemThuBH" :header="header_popup" :position="position" :close="closeDialog" showCloseIcon="true" width="90%" target="#app .main-wrapper">
      <div class="popup-body">
        <acceptanceIncident :tag="baohongTag" ref="refAcceptanceIncident"></acceptanceIncident>
      </div>
    </ejs-dialog>
    <ExportDataModal ref="exportDataModal" :data="this.DsPhieuBaoHong.data"></ExportDataModal>
    <ejs-dialog
      :isModal="true"
      :enableResize="true"
      :resizeHandles="['All']"
      :visible="false"
      ref="dlg_DanhSachTienTrinh"
      :header='"Danh sách tiến trình"'
      showCloseIcon="true"
      target=".main-wrapper"
      width="60%"
      allowDragging="true"
      :animationSettings="animationSettings"
      @close='close_DSTienTrinh'
    >
      <div>
        <popdstientrinh v-if="show_popdstientrinh" :BaoHongId="BaoHongId"/>
      </div>
    </ejs-dialog>
  </div>
</template>
<style scoped  src='./index.scss'></style>
<script>
// import gridView from '@/modules/complaint/ResovleComplaintAfterSales/components/gridview.vue'
// import gridView from '@/components/Shared/gridview.vue'
import breadcrumb from '@/components/breadcrumb'
import XLSX from 'xlsx'
import moment from 'moment'
import DatePicker from 'vue2-datepicker'
import 'vue2-datepicker/locale/vi.js'
import 'vue2-datepicker/index.css'
import api from './Api'
import popdstientrinh from './PopUps/DanhSachTienTrinh.vue'
import TRANGTHAI_PH from '@/modules/contract/profile/CompleteProfileChangeAccount/Enum/TRANGTHAI_PH.js'
import TRANGTHAI_BH from '@/modules/contract/profile/CompleteProfileChangeAccount/Enum/TRANGTHAI_BH.js'
import CapNhatTienTrinh from './PopUps/CapNhatTienTrinh.vue'
import thongSoKyThuat from './PopUps/ThongSoKyThuat.vue'
import updateProcessIncident from '@/modules/incident/subscriber/UpdateProcessIncident/UpdateProcessIncident.vue'
import popupLayDuLieuBC from '@/modules/contract/complete_profile/popups/popupLayDuLieuBC/LayDuLieuBC.vue'
import acceptanceIncident from '@/modules/incident/subscriber/ProcessAcceptanceIncident/ProcessAcceptanceIncident.vue'
import TraCuuThongSoKTModal from '@/modules/contract/setup/DeclareServiceSpeedITSecure/popups/TraCuuThongSoKT/TraCuuThongSoKTModal.vue'
import ExportDataModal from '@/modules/contract/profile/ExportData/ExportDataModal.vue'
import { DichVuVienThong, LOAI_DV } from '@/const/enums/index.js'
export default {
  components: {
    breadcrumb,
    XLSX,
    moment,
    api,
    DatePicker,
    TRANGTHAI_PH,
    CapNhatTienTrinh,
    popupLayDuLieuBC,
    // frmThongSoKT,
    updateProcessIncident,
    TraCuuThongSoKTModal,
    thongSoKyThuat,
    acceptanceIncident,
    ExportDataModal,
    popdstientrinh
  },
  name: 'SearchIncidentTicket',
  created() {
    this.Loading = true
    try {
      Promise.all([this.getDmDichVu(), this.getDmTrangThaiPhieu(), this.getDmTrangThaiBaoHong(), this.getDsDonVi()])
        .then((responses) => {
          console.log(responses)
        })
        .finaly(() => {
          this.Loading = false
        })

      this.TrangThaiId = 0
      this.Loading = false
    } catch (ex) {
      this.Loading = false
    }
  },
  mounted() {
    this.txtFromDate = moment().format('DD/MM/YYYY')
    this.txtToDate = moment().format('DD/MM/YYYY')
  },
  watch: {
    DichVuVTId: async function (val, oldval) {
      this.Loading = true
      try {
        // await this.getDsDonVi()
        this.Loading = false
      } catch (ex) {
        this.Loading = false
      }
    },
    TrangThaiPhieuId: function (val) {
      if (val == TRANGTHAI_PH.MOI) {
        this.fCheck.TrangThai = true
      } else {
        this.fCheck.TrangThai = false
      }
    },
    Loading: function (val, oldval) {
      this.$root.showLoading(val)
    }
  },
  data() {
    return {
      show_popdstientrinh: false,
      header: {
        title: 'Tra cứu',
        list: [
          { name: ' TRA CỨU KHÁCH HÀNG QUAN TÂM OA', link: { name: 'Ui.cards' } },
          {
            name: 'TRA CỨU KHÁCH HÀNG QUAN TÂM OA',
            link: { name: 'Ui.buttons' }
          }
        ]
      },
      header_popup: 'NGHIỆM THU BÁO HỎNG',
      Loading: false,
      target: '#app', //  .main-wrapper-payment
      targetTop: '#app', // document.querySelector('body'),.main-wrapper
      animationSettings: { effect: 'Zoom' },
      position: { X: 'center', Y: 'top' },
      dateFormat: 'DD/MM/YYYY',
      baohongTag: '',
      typeFormat: 'date',
      txtFromDate: '',
      txtToDate: '',
      DmDichVu: [],
      DmTrangThaiPhieu: [],
      DmTrangThaiBaoHong: [],
      DsDonVi: [],
      fCheck: {
        DichVuVT: false,
        Ngay: false,
        TTPhieu: true,
        TTBaoHanh: false,
        TramVT: false,
        TrangThai: false,
        DiaChiDau: false,
        DiaChiCuoi: false
      },
      txtSoMay: '',
      txtSoAo: '',
      txtTenThueBao: '',
      txtTienThueBao: '',
      txtDiaChiLD: '',
      txtDiaChiLDDau: '',
      txtDiaChiLDCuoi: '',
      TongSoPhieu: 0,
      PhieuMoi: 0,
      PhieuThucHien: 0,
      PhieuTraLai: 0,
      TrangThaiId: 0,
      TrangThaiPhieuId: 0,
      TrangThaiBhId: 0,
      DonViId: 0,
      PhieuId: 0,
      BaoHongId: 0,
      QuyTrinhId: 0,
      DichVuVTId: 0,
      ThueBaoId: 0,
      selectedRow: {},
      fConfig: {
        formatDate: 'DD/MM/YYYY'
      },
      DsPhieuBaoHong: {
        headers: [
          { name: 'baohong_id', text: 'ID', value: '' },
          { name: 'loaihinh_tb', text: 'Loại hình TB', value: '' },
          { name: 'nhom_tt', text: 'Nhóm TT', value: '' },
          { name: 'ma_tb', text: 'Số máy/Acc', value: '' },
          { name: 'huonggiao', text: 'Hướng giao', value: '' },
          { name: 'gio_conlai', text: 'Giờ còn lại', value: '' },
          { name: 'giohen_tu', text: 'Hẹn từ', value: '' },
          { name: 'GIOHEN_DEN', text: 'Hẹn đến', value: '' },
          { name: 'KIEU_HEN', text: 'Kiểu hẹn', value: '' },
          { name: 'KQ_XACMINH', text: 'KQ xác minh', value: '' },
          { name: 'LYDOHEN', text: 'Lý do hẹn', value: '' },
          { name: 'MA_LT', text: 'Số ảo', value: '' },
          { name: 'TEN_PLKH', text: 'Phân loại KH', value: '' },
          { name: 'chitieu_tg', text: 'Chỉ tiêu TG', value: '' },
          { name: 'NGAY_BH', text: 'Ngày BH', value: '' },
          { name: 'GHICHU_HONG', text: 'Ghi chú BH', value: '' },
          { name: 'ten_tb', text: 'Tên thuê bao', value: '' }
        ],
        headers2: [
          { fieldName: 'phieu_id', headerText: 'ID', value: '', width: 90 },
          { fieldName: 'loaihinh_tb', headerText: 'Loại hình TB', value: '' },
          { fieldName: 'nhom_tt', headerText: 'Nhóm TT', value: '' },
          { fieldName: 'ma_tb', headerText: 'Số máy/Acc', value: '' },
          { fieldName: 'huonggiao', headerText: 'Hướng giao', value: '' },
          { fieldName: 'gio_conlai_t', headerText: 'Giờ còn lại', value: '', width: 120 },
          { fieldName: 'giohen_tu', headerText: 'Hẹn từ', value: '' },
          { fieldName: 'giohen_den', headerText: 'Hẹn đến', value: '' },
          { fieldName: 'kieu_hen', headerText: 'Kiểu hẹn', value: '' },
          { fieldName: 'kq_xacminh', headerText: 'KQ xác minh', value: '' },
          { fieldName: 'lydohen', headerText: 'Lý do hẹn', value: '' },
          { fieldName: 'ma_lt', headerText: 'Số ảo', value: '' },
          { fieldName: 'ten_plkh', headerText: 'Phân loại KH', value: '' },
          { fieldName: 'chitieu_tg', headerText: 'Chỉ tiêu TG', value: '', width: 120 },
          { fieldName: 'ngay_bh', headerText: 'Ngày BH', value: '' },
          { fieldName: 'ghichu_hong', headerText: 'Ghi chú BH', value: '' },
          { fieldName: 'ten_tb', headerText: 'Tên thuê bao', value: '' },
          { fieldName: 'diachi_ld', headerText: 'Địa chỉ LĐ', value: '' },
          { fieldName: 'tinh_ld', headerText: 'Tỉnh LĐ', value: '' },
          { fieldName: 'tinhtrang', headerText: 'Tình trạng', value: '' },
          { fieldName: 'nd_giao', headerText: 'Nội dung giao', value: '' },
          { fieldName: 'ghichu_hong', headerText: 'Ghi chú hỏng', value: '' },
          { fieldName: 'ghichu_th', headerText: 'Ghi chú TH', value: '' },
          { fieldName: 'nguoi_bh', headerText: 'Người BH', value: '' },
          { fieldName: 'so_dt', headerText: 'Số ĐT', value: '' },
          { fieldName: 'dienthoai_bh', headerText: 'Số ĐT BH', value: '' },
          { fieldName: 'ngaytra', headerText: 'Ngày trả', value: '' },
          { fieldName: 'ngaygiao', headerText: 'Ngày giao', value: '' },
          { fieldName: 'trangthai_ph', headerText: 'Trạng thái phiếu', value: '' },
          { fieldName: 'trangthai_bh', headerText: 'Trạng thái BH', value: '' },
          { fieldName: 'donvi_giao', headerText: 'Đơn vị giao', value: '' },
          { fieldName: 'donvi_nhan', headerText: 'Đơn vị nhận', value: '' },
          { fieldName: 'nv_giu', headerText: 'NV giữ', value: '' },
          { fieldName: 'nhanvien_kt', headerText: 'Nhân viên KT', value: '' },
          { fieldName: 'nguoi_cn', headerText: 'Người CN', value: '' },
          { fieldName: '', headerText: 'ND thực hiện', value: '' }
        ],
        data: []
      },
      DmTrangThai: [
        { id: 0, text: 'Tất cả' },
        { id: 1, text: 'Mới giao đến' },
        { id: 2, text: 'Mới trả lại' }
      ],
      DsLsPhieuBaoHong: {
        headers: [
          { name: 'giohen_tu', text: 'Ngày hẹn từ', value: '' },
          { name: 'giohen_den', text: 'Ngày hẹn đến', value: '' },
          { name: 'ngay_cn', text: 'Ngày CN', value: '' },
          { name: 'nguoi_cn', text: 'Người CN', value: '' },
          { name: 'noidung', text: 'Nội dung', value: '' },
          { name: 'lydohen', text: 'Lý do hẹn', value: '' },
          { name: 'dv_hen', text: 'ĐV hẹn', value: '' },
          { name: 'kieu_hen', text: 'Kiểu hẹn', value: '' },
          { name: 'lydo_xm', text: 'KQ xác minh', value: '' },
          { name: 'nguoi_xm', text: 'NV xác minh', value: '' }
        ],
        headers2: [
          { fieldName: 'giohen_tu', headerText: 'Ngày hẹn từ', value: '' },
          { fieldName: 'giohen_den', headerText: 'Ngày hẹn đến', value: '' },
          { fieldName: 'ngay_cn', headerText: 'Ngày CN', value: '' },
          { fieldName: 'nguoi_cn', headerText: 'Người CN', value: '' },
          { fieldName: 'noidung', headerText: 'Nội dung', value: '' },
          { fieldName: 'lydohen', headerText: 'Lý do hẹn', value: '' },
          { fieldName: 'dv_hen', headerText: 'ĐV hẹn', value: '' },
          { fieldName: 'kieu_hen', headerText: 'Kiểu hẹn', value: '' },
          { fieldName: 'ketqua_xm', headerText: 'KQ xác minh', value: '' },
          { fieldName: 'nguoi_xm', headerText: 'NV xác minh', value: '' }
        ],
        data: []
      },
      DsLsHenThiCong: [],
      NET_BAOHONG: {
        TTBH_DANG_XL: 15,
        LOAI_HONG: 26,
        TBH_XL_XONG: 16
      }
    }
  },
  methods: {
    getDmDichVu: async function () {
      var input = {}
      this.DmDichVu = this.GetData(await api.getDmDichVu(this.axios, input))
      if (this.DmDichVu != null && this.DmDichVu.length > 0) {
        this.DichVuVTId = this.DmDichVu[0].dichvuvt_id
      }
    },
    getDmTrangThaiPhieu: async function () {
      var input = {}
      this.DmTrangThaiPhieu = this.GetData(await api.getDmTrangThaiPhieu(this.axios, input))
      this.TrangThaiPhieuId = 1
    },
    getDmTrangThaiBaoHong: async function () {
      var input = {}
      this.DmTrangThaiBaoHong = this.GetData(await api.getDmTrangThaiBaoHong(this.axios, input))
    },
    getDsDonVi: async function () {
      var input = { p_loaidv_id: LOAI_DV.TRAM_VT }
      this.DsDonVi = this.GetData(await api.getDsDonVi(this.axios, input))
    },
    lay_ds_danhba_tsl: async function (thueBaoId) {
      var input = { thueBaoId: thueBaoId }
      return this.GetData(await api.lay_ds_danhba_tsl(this.axios, input))
    },
    getDsPhieuBaoHong: async function () {
      var input = {
        p_denngay: this.fCheck.Ngay ? this.txtToDate : '',
        p_dichvuvt_id: this.fCheck.DichVuVT ? this.DichVuVTId : 0,
        p_ma_tb: this.txtSoMay,
        p_tramvt_id: this.fCheck.TramVT ? this.DonViId : 0,
        p_trangthai_id: this.TrangThaiId,
        p_ttbh_id: this.fCheck.TTBaoHanh ? this.TrangThaiBhId : 0,
        p_ttph_id: this.fCheck.TTPhieu ? this.TrangThaiPhieuId : 0,
        p_tungay: this.fCheck.Ngay ? this.txtFromDate : ''
      }
      /*
      input = {
        p_denngay: '01/06/2021',
        p_dichvuvt_id: 0,
        p_ma_tb: '',
        p_tramvt_id: 0,
        p_trangthai_id: 0,
        p_ttbh_id: 0,
        p_ttph_id: 0,
        p_tungay: '01/06/2020'
      }
      */
      try {
        this.Loading = true
        let data = this.GetData(await api.getDsPhieuBaoHong(this.axios, input))
        if (data && data.length >= 0) {
          this.TongSoPhieu = data.length
          this.PhieuMoi = data.filter((x) => x.ttph_id == TRANGTHAI_PH.MOI).length
          this.PhieuThucHien = data.filter((x) => x.ttph_id == TRANGTHAI_PH.DAGIAO || x.ttph_id == TRANGTHAI_PH.DATHUCHIEN).length
          this.PhieuTraLai = data.filter((x) => x.ttph_id == TRANGTHAI_PH.DATRALAI).length
        }
        this.DsPhieuBaoHong.data = data
        this.Loading = false
      } catch (ex) {
        this.Loading = false
      }
    },
    ChangedRow: async function (i, item) {
      // console.log('Select row:', item)
      this.selectedRow = item
      this.txtSoMay = item.ma_tb
      this.txtTenThueBao = item.ten_tb
      this.txtDiaChiLD = item.diachi_ld
      this.PhieuId = item.phieu_id
      this.BaoHongId = item.baohong_id
      this.QuyTrinhId = item.quytrinh_id
      this.DichVuVTId = item.dichvuvt_id
      this.ThueBaoId = item.thuebao_id
      this.txtSoAo = item.ma_lt
      if (item.dichvuvt_id == DichVuVienThong.TSL) {
        let data = await this.lay_ds_danhba_tsl(item.thuebao_id)
        // let data = await this.lay_ds_danhba_tsl(503815)
        if (data != null && data.length > 1) {
          if (data[0].daucuoi_id == 1) {
            this.txtDiaChiLDDau = data[0].diachi
            this.txtDiaChiLDCuoi = data[1].diachi
          } else {
            this.txtDiaChiLDDau = data[1].diachi
            this.txtDiaChiLDCuoi = data[0].diachi
          }
        } else {
          this.txtDiaChiLDDau = ''
          this.txtDiaChiLDCuoi = ''
        }
      } else {
        this.txtDiaChiLDDau = ''
        this.txtDiaChiLDCuoi = ''
      }
    },
    gridDsPhieuBaoHong_rowDoubleClicked: function (i, item) {
      console.log('gridDsPhieuBaoHong_rowDoubleClicked')
      console.log(item.ttbh_id)
      let ttbh_id = item.ttbh_id
      let Tag_frm = ''
      if (ttbh_id != TRANGTHAI_BH.HOANTHANH && ttbh_id != TRANGTHAI_BH.MOI_TIEPNHAN) {
        if (ttbh_id == TRANGTHAI_BH.DANG_GIAO_XULY) Tag_frm += '3'
        if (ttbh_id == TRANGTHAI_BH.DANG_DIEUHANH_XU_LY) Tag_frm += '4'
        if (ttbh_id == this.NET_BAOHONG.TTBH_DANG_XL || ttbh_id == this.NET_BAOHONG.TTBH_XL_XONG || ttbh_id == 8 || ttbh_id == 5 || ttbh_id == 9) Tag_frm += ttbh_id
        Tag_frm += '+' + item.huonggiao_id
        this.baohongTag = Tag_frm
        let Tag = Tag_frm
        let matb_tk = item.ma_tb
        let dichvt_tk_id = item.dichvuvt_id
        let quytrinh_tk_id = item.quytrinh_id
        let kieu_Load = 1
        console.log(ttbh_id)
        if (ttbh_id == this.NET_BAOHONG.TTBH_DANG_XL || ttbh_id == this.NET_BAOHONG.TTBH_XL_XONG || ttbh_id == 8 || ttbh_id == 5 || ttbh_id == 9) {
          this.header_popup = 'CẬP NHẬT KẾT QUẢ MẠNG VT LỚP TRÊN'
        }
        // show form nghiem thu
        try {
          this.$refs.refAcceptanceIncident.Tag = Tag
          this.$refs.refAcceptanceIncident.matb_tk = matb_tk
          this.$refs.refAcceptanceIncident.dichvt_tk_id = dichvt_tk_id
          this.$refs.refAcceptanceIncident.quytrinh_tk_id = quytrinh_tk_id
          this.$refs.refAcceptanceIncident.kieu_Load = kieu_Load
          console.log(this.$refs.refAcceptanceIncident)
          this.$refs.refAcceptanceIncident.frmNghiemThuBH_FormLoad()

        } catch (err) {
          console.log('gridDsPhieuBaoHong_rowDoubleClicked', err)
        } finally {
          this.$refs.dialogObjNghiemThuBH.show()
        }

        /*
        https://cntt.vnpt.vn/browse/BSS-1687  [119->Điểm chia] - Xử lý/nghiệm thu báo hỏng
        frmNghiemThuBH obj = new frmNghiemThuBH();
                    obj.Tag = Tag_frm;
                    obj.kieu_Load = 1;
                    obj.matb_tk = gridview_DsPhieu.GetRowCellValue(index, "MA_TB").ToString();
                    obj.dichvt_tk_id = Convert.ToInt32(gridview_DsPhieu.GetRowCellValue(index, "DICHVUVT_ID").ToString());
                    obj.quytrinh_tk_id = Convert.ToInt32(gridview_DsPhieu.GetRowCellValue(index, "QUYTRINH_ID").ToString());
                    obj.MdiParent = this.ParentForm;
                    obj.Show();
        */
      }
    },
    getDsLsPhieuBaoHong: async function () {
      // var input = { p_baohong_id: 4516625 }
      var input = { p_baohong_id: this.BaoHongId }
      this.DsLsPhieuBaoHong.data = this.GetData(await api.getDsLsPhieuBaoHong(this.axios, input))
    },
    getDsLsHenThiCong: async function () {
      var input = { p_hdtb_id: 2731636 }
      this.DsLsHenThiCong = this.GetData(await api.getDsLsHenThiCong(this.axios, input))
    },
    ShowLsHen: async function () {
      this.Loading = true
      try {
        await this.getDsLsPhieuBaoHong()
        this.Loading = false
        this.$refs.dialogObjLsHenBH.show()
        // this.$bvModal.show('my-modal-ls-baohong')
      } catch (ex) {
        this.Loading = false
      }
    },
    ThoatLsBH: function () {
      this.$refs.dialogObjLsHenBH.hide()
    },
    ShowThongSoKT: function () {
      this.$refs.frmThongSoKT.openDialog(this.ThueBaoId)
      // this.$refs.traCuuThongSoKTModal.showModal()
      // alert(`Chưa có api & khảo sát, tham chiếu tới Lê Kỳ đang làm`)
      // return
      /*
      console.log('ShowThongSoKT',this.ThueBaoId,this.DichVuVTId)
      this.$refs.refFrmThongSoKT.form.thuebao_id=this.ThueBaoId
      this.$refs.refFrmThongSoKT.form.dichvuvt_id=this.DichVuVTId
      this.$refs.dialogObjThongSoKyThuat.show()
      */
    },
    ShowPopUp: function () {
      this.$bvModal.show('my-modal1')
    },
    closeDialog() {
      this.$refs.refAcceptanceIncident.kieugoi_id = 0;
    },
    ShowCapNhatTienTrinh: function () {
      // alert(`Bỏ theo khảo sát`)
      if (this.PhieuId > 0) {
        let matinh = this.$root.token.getMaTinh()
        let item = this.selectedRow
        let tag = '1'
        let vtientrinhbh_id = null
        let vnhom_id = 2
        let vbaohong_id = this.selectedRow.baohong_id
        let vnoidung_goc = this.selectedRow.ma_tb
        let vnd_baohong = `VNPT ${matinh} :[${item.ma_tb};${item.loaihinh_tb} ]: ${item.ten_plkh},${item.ngay_bh},${item.ten_tb} ,LH ${item.so_dt},${item.diachi_ld}`
        this.$refs.refCapNhatTienTrinh.openDialog(tag, vtientrinhbh_id, vnhom_id, vbaohong_id, vnoidung_goc, vnd_baohong)
      }
    },
    async show_DSTienTrinh() {
      this.show_popdstientrinh = true;
      this.$refs.dlg_DanhSachTienTrinh.show();
    },

    async close_DSTienTrinh() {
      this.show_popdstientrinh = false;
    },
    // async tsbtnTCTienTrinhBH_Click() {
    //   if (this.fInput.MaThueBao == '') {
    //     this.ShowError(`Bạn chưa chọn phiếu !`)
    //     return
    //   }
    //   try {
    //     this.$refs.refUpdateProcessIncident.data.txtMaTB = this.fInput.MaThueBao
    //   } catch (err) {
    //     console.log('tsbtnTCTienTrinhBH_Click', err)
    //   } finally {
    //     this.$refs.dialogObjTcTienTrinhXLBH.show()
    //   }
    // },
    TroGiup: function () {
      this.$bvModal.hide('my-modal1')
    },
    exportFile: function () {
      if (this.DsPhieuBaoHong.data != null && this.DsPhieuBaoHong.data.length > 0) {
        //this.$refs.dialogLayDuLieuBC.openDialog(this.DsPhieuBaoHong.data)
        if (this.DsPhieuBaoHong.data.length > 0) {
          this.$refs.exportDataModal.showModal()
        } else {
          this.$root.$toast.warning('Không có dữ liệu. Bạn hãy kiểm tra lại điều kiện lấy báo cáo !')
        }
      } else this.$toast.error('Không có dữ liệu!')
    },
    exportExcel: function () {
      if (this.DsPhieuBaoHong.data != null && this.DsPhieuBaoHong.data.length > 0) {
        let data = XLSX.utils.json_to_sheet(this.DsPhieuBaoHong.data)
        let wb = XLSX.utils.book_new() // make Workbook of Excel
        XLSX.utils.book_append_sheet(wb, data, 'DsPhieuBaoHong') // sheetAName is name of Worksheet
        // export Excel file
        XLSX.writeFile(wb, 'DsPhieuBaoHong.xlsx')
      } else this.$toast.error('Không có dữ liệu!')
    },
    async gridDsPhieuBaoHong_dataBound(args) {
      console.log('gridDsPhieuBaoHong_dataBound', args)
    },
    async gridDsPhieuBaoHong_rowDataBound(args) {
      console.log('gridDsPhieuBaoHong_rowDataBound', args)
    },
    async gridDsPhieuBaoHong_queryCellInfo(args) {
      try {
        console.log('gridDsPhieuBaoHong_queryCellInfo', args)
        const { data, column } = args
        const rgb = (color) => {
          return `rgb(${color})`
        }
        console.log('0 Vô hcm')
        if (args.column.field === 'GOI_KT'.toLowerCase()) {
          let mau_ht = args.data['MAU_HT'.toLowerCase()]
          if (mau_ht != null && mau_ht != '') {
            let s = mau_ht.split(',')
            if (s != null && s.length == 3) {
              args.cell.style.backgroundColor = `rgba(${s[0]}, ${s[1]}, ${s[2]})`
            } else {
              args.cell.style.backgroundColor = `rgba(255, 255, 255)`
            }
          }
        }
        if (args.column.field === 'MUCDO'.toLowerCase()) {
          let mau_ht = args.data['MAU_HT_MUCDO'.toLowerCase()]
          if (mau_ht != null && mau_ht != '') {
            let s = mau_ht.split(',')
            if (s != null && s.length == 3) {
              args.cell.style.backgroundColor = `rgba(${s[0]}, ${s[1]}, ${s[2]})`
            } else {
              args.cell.style.backgroundColor = `rgba(255, 255, 255)`
            }
          }
        }
        if (args.column.field === 'TEN_PLKH'.toLowerCase()) {
          let mau_ht = args.data['MAU_HTPL'.toLowerCase()]
          if (mau_ht != null && mau_ht != '') {
            let s = mau_ht.split(',')
            if (s != null && s.length == 3) {
              args.cell.style.backgroundColor = `rgba(${s[0]}, ${s[1]}, ${s[2]})`
            } else {
              args.cell.style.backgroundColor = `rgba(255, 255, 255)`
            }
          }
        }
        if (column.field == 'ma_tb') {
          if (data.hasOwnProperty('kenhtn_id')) {
            const kenhtn = data.kenhtn_id
            if (kenhtn) {
              if (kenhtn == KENHTN_BAOHONG.BH_GPON_SUYHAO_CAO || kenhtn == KENHTN_BAOHONG.BH_LOICAP_QUANG) {
                args.cell.style.backgroundColor = 'RoyalBlue'
              } else {
                args.cell.style.backgroundColor = rgb(255, 255, 255)
                args.cell.style.color = 'Black'
              }

              const GIO_CONLAI = data.gio_conlai
              if (kenhtn != KENHTN_BAOHONG.BH_GPON_SUYHAO_CAO && kenhtn != KENHTN_BAOHONG.BH_XUAT_THEO_FILE) {
                if (GIO_CONLAI < 0) {
                  args.cell.style.backgroundColor = 'Red'
                } else if (GIO_CONLAI < 10) {
                  args.cell.style.backgroundColor = 'Aqua'
                }
              }
            }
          }
        }

        if (column.field == 'ten_tb') {
          if (data.hasOwnProperty('bh_lai')) {
            const bh_lai = data.bh_lai
            if (bh_lai == 1) args.cell.style.backgroundColor = rgb(0, 191, 255)
            else {
              args.cell.style.backgroundColor = rgb(255, 255, 255)
              args.cell.style.color = 'Black'
            }
          }
        }

        if (this.$root.token.getMaTinh() == 'HCM') {
          console.log('Vô hcm')
          console.log(column.field)
          //DatabaseConstants.DB1 == "ADMIN_HCM"
          if (column.field == 'gio_conlai_t') {
            if (Number(data.gio_gio_conlaiconlai_t) > 0 && Number(data.gio_conlai) < 1) {
              args.cell.style.backgroundColor = 'Aqua'
            }
            if (Number(data.gio_conlai) < 0) {
              args.cell.style.backgroundColor = 'Red'
            }
            if (data.dichvuvt_id == DichVuVienThong.TSL || data.dichvuvt_id == DichVuVienThong.MEGAWAN || data.dichvuvt_id == DichVuVienThong.METRONET) args.cell.style.backgroundColor = 'Violet'
          }

          if (column.field == 'ma_tb') {
            if (Number(data.gio_conlai) > 0 && Number(data.gio_conlai) < 1) {
              args.cell.style.backgroundColor = 'Aqua'
            }
            if (Number(data.gio_conlai) < 0) {
              args.cell.style.backgroundColor = 'Red'
            }
            if (data.dichvuvt_id == DichVuVienThong.TSL || data.dichvuvt_id == DichVuVienThong.MEGAWAN || data.dichvuvt_id == DichVuVienThong.METRONET) args.cell.style.backgroundColor = 'Violet'
          }
        }

      } catch (err) {
        console.log('gridDsPhieuBaoHong_queryCellInfo', err)
      }
    },
    GetData: function (response) {
      if (response.data.error === 200 || response.data.error === '200') {
        return response.data.data
      } else {
        console.log(response.data.error_code)
      }
      return []
    },
    ShowAlert: function (msg) {
      this.$toast.warning(msg)
    },
    ShowSuccess: function (msg) {
      this.$toast.success(msg)
    },
    ShowError: function (msg) {
      this.$toast.error(msg)
    }
  }
}
</script>
