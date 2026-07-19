<template>
  <ejs-dialog :enableResize="true" :allowDragging="true" :visible="false" :animationSettings="animationSettings" ref="dialogObjChuyenTo" :position="position" :close="closeDialogChuyenTo" :header="'Chuyển tổ'" showCloseIcon="true" width="80%" target="#app .main-wrapper">
    <div class="">
      <div class="list-actions-top">
        <ul class="list">
          <li>
            <a v-show="btnLayTTMoi.Visible" href="#" @click.prevent="btnLayTTMoi_Click"> <span class="icon one-file-attach"></span> Lấy TT </a>
          </li>
          <li>
            <a href="#" v-bind:class="{ disabled: !tsbtnCapNhat }" :disabled="!tsbtnCapNhat" @click.prevent="tsbtnCapNhat_Click"> <span class="icon one-reload1"></span> Cập nhật </a>
          </li>
        </ul>
      </div>
      <div class="popup-body">
        <div class="box-form">
          <div class="legend-title">{{ labelFunctionTitle }}</div>
          <div class="row">
            <div class="col-sm-6 col-12">
              <div class="row">
                <div class="col-sm-12 col-12">
                  <div class="info-row">
                    <div class="key">Số máy/account</div>
                    <div class="value">
                      <div class="input-more-button">
                        <button class="btn" @click="SearchDanhBa">
                          <span class="-ap icon-more_horiz"></span>
                        </button>
                        <input type="text" v-on:keyup.enter="txtMaTB_KeyPress" v-model="txtSoMay" ref="txtSoMay" class="form-control" />
                      </div>
                    </div>
                  </div>
                </div>
              </div>
              <div class="row">
                <div class="col-sm-12 col-12">
                  <div class="info-row">
                    <div class="key">Ngày BH</div>
                    <div class="value">
                      <date-picker :format="dateFormat" :value-type="dateFormat" ref="txtNgayBH" :time-title-format="dateFormat" :type="typeFormat" :showTimePanel="showTimePanel" v-model="txtNgayBH" :disabled-date="disabledRange" @close="handleOpenChange" id="txtNgayBH">
                        <template #icon-calendar>
                          <span class="icon one-calendar"></span>
                        </template>
                        <template v-slot:footer>
                          <button class="mx-btn mx-btn-text" @click="toggleTimePanel">
                            {{ showTimePanel ? 'Chọn ngày' : 'Chọn giờ' }}
                          </button>
                        </template>
                      </date-picker>
                    </div>
                  </div>
                </div>
              </div>
              <div class="row">
                <div class="col-sm-6 col-12">
                  <div class="info-row">
                    <div class="key">Dịch vụ</div>
                    <div class="value">
                      <input type="text" v-model="txtDichVuVT" class="form-control" />
                    </div>
                  </div>
                </div>
                <div class="col-sm-6 col-12">
                  <div class="info-row">
                    <div class="key">Loại hình</div>
                    <div class="value">
                      <input type="text" v-model="txtLoaiTB" class="form-control" />
                    </div>
                  </div>
                </div>
              </div>
              <div class="row">
                <div class="col-sm-12 col-12">
                  <div class="info-row">
                    <div class="key">Tên thuê bao</div>
                    <div class="value">
                      <input type="text" v-model="txtTenTB" class="form-control" />
                    </div>
                  </div>
                </div>
              </div>
              <div class="row">
                <div class="col-sm-12 col-12">
                  <div class="info-row">
                    <div class="key">Địa chỉ TB</div>
                    <div class="value">
                      <input type="text" v-model="txtDiachiLD" class="form-control" />
                    </div>
                  </div>
                </div>
              </div>
              <div class="row">
                <div class="col-sm-12 col-12">
                  <div class="info-row">
                    <div class="key">Ghi chú</div>
                    <div class="value">
                      <input type="text" v-model="txtGhiChu" class="form-control" />
                    </div>
                  </div>
                </div>
              </div>
            </div>
            <div class="col-sm-6 col-12">
              <div class="info-row">
                <div class="key">Người giao</div>
                <div class="value">
                  <input type="text" v-model="txtNguoiGiao" class="form-control" />
                </div>
              </div>
              <div class="info-row">
                <div class="key">Người nhận</div>
                <div class="value">
                  <input type="text" v-model="txtNguoiNhan" class="form-control" />
                </div>
              </div>
              <div class="info-row">
                <div class="key">Trạng thái BH</div>
                <div class="value">
                  <input type="text" v-model="txtTrangThaiBH" class="form-control" />
                </div>
              </div>
              <div class="legend-title">Thông tin thay đổi đơn vị</div>
              <div class="info-row">
                <div class="key">Đơn vị QL</div>
                <div class="value">
                  <Select2 ref="donviQLId" v-model="donviQLId" :options="DmDvQL" />
                </div>
              </div>
              <div class="info-row">
                <div class="key">Đơn vị</div>
                <div class="value">
                  <Select2 ref="tramVtId" v-model="tramVtId" :options="DmTramVT" />
                </div>
              </div>
              <div class="info-row">
                <div class="key">Nội dung</div>
                <div class="value">
                  <input ref="txtNoiDung" type="text" v-model="txtNoiDung" class="form-control" />
                </div>
              </div>
            </div>
          </div>
        </div>
        <div class="boxForm">
          <b-tabs class="nav tabs tab-over relative" style="z-index: 2" v-model="tabIndex">
            <b-tab title="Chi tiết điều hành xử lý sự cố">
              <DataGrid v-bind:columns="DhSuCo.headers" v-bind:dataSource="DhSuCo.data" :showColumnCheckbox="false" :enable-paging-server="false" :allowPaging="true" :showFilter="true" :enabledSelectFirstRow="false" @selectedItemsChanged="gridChiTiet_RowsSelected" @rowClicked="gridChiTiet_Selected">
              </DataGrid>
            </b-tab>
            <b-tab title="Danh sách phiếu báo hỏng lạc hướng">
              <DataGrid
                v-bind:columns="DsPhieuBaoHong.headers"
                v-bind:dataSource="DsPhieuBaoHong.data"
                :showColumnCheckbox="true"
                :enable-paging-server="false"
                :allowPaging="true"
                :showFilter="true"
                :enabledSelectFirstRow="false"
                @selectedItemsChanged="gridBaoHong_RowsSelected"
                @rowClicked="NAP_DS_LUOI"
              >
              </DataGrid>
            </b-tab>
          </b-tabs>
        </div>
      </div>
      <ejs-dialog :enableResize="true" :visible="false" :position="position" ref="searchAccountDialog" :header="'Tra cứu danh bạ'" showCloseIcon="true" target="#app" width="80%" height="800">
        <div class="chuyenTo popupBody chuyenToPopupBody">
          <searchAccount :isPopup="true" @form-close="searchAccountDialogClose" />
        </div>
      </ejs-dialog>
    </div>
  </ejs-dialog>
</template>
<script>
import XLSX from 'xlsx'
import DatePicker from 'vue2-datepicker'
import 'vue2-datepicker/locale/vi.js'
import 'vue2-datepicker/index.css'
import moment from 'moment'
import searchAccount from '@/modules/search/subscriber/SearchAccount'
import api2 from '@/modules/contract/profile/CompleteProfileChangeAccount/Popups/PopUpApi'
import LOAI_DV from '@/modules/contract/profile/CompleteProfileChangeAccount/Enum/LOAI_DV.js'
export default {
  components: {
    XLSX,
    moment,
    DatePicker,
    searchAccount,
    api2,
    LOAI_DV
  },
  name: 'ChuyenTo',
  props: {
    tag: {
      default: ''
    },
    tthdId: {
      default: -1,
      type: Number
    },
    ttbhId: {
      default: -1,
      type: Number
    },
    bhId: {
      default: 0,
      type: Number
    }
  },
  data() {
    return {
      Loading: false,
      dateBEFormat: 'YYYY-MM-DD HH:mm:ss',
      dateFormat: 'DD/MM/YYYY HH:mm:ss',
      dateFormatMonth: 'MM/YYYY',
      typeFormat: 'datetime',
      typeFormatMonth: 'month',
      dateConfig: {
        dateFormat: 'd/m/Y',
        altFormat: 'd/m/Y'
      },
      target: '#app', //  .main-wrapper-payment
      targetTop: '#app', // document.querySelector('body'),.main-wrapper
      animationSettings: { effect: 'Zoom' },
      position: { X: 'center', Y: 'top' },
      showTimePanel: false,
      tabIndex: 0,
      txtSoMay: '',
      txtNgayBH: '',
      txtDichVuVT: '',
      txtLoaiTB: '',
      txtTenTB: '',
      txtDiachiLD: '',
      txtGhiChu: '',
      txtNguoiGiao: '',
      txtNguoiNhan: '',
      txtTrangThaiBH: '',
      donviQLId: '',
      txtNoiDung: '',
      DmDvQL: [],
      tramVtId: 0,
      tthd_id: 0,
      DmDv: [],
      DmTramVT: [],
      tsbtnCapNhat: false,
      IS_USING_LACHUONG_BH: false,
      DhSuCo: {
        headers: [
          {
            fieldName: 'huonggiao',
            headerText: 'Hướng giao',
            allowFiltering: true,
            allowSorting: false,
            textAlign: 'left',
            isGroupedColumn: true
          },
          {
            fieldName: 'dvg',
            headerText: 'Đơn vị giao',
            allowFiltering: true,
            allowSorting: false,
            width: 90,
            textAlign: 'left'
          },
          {
            fieldName: 'nv_giao',
            headerText: 'Nhân viên giao',
            allowFiltering: true,
            allowSorting: false,
            width: 120
          },
          {
            fieldName: 'ngay_giao',
            headerText: 'Ngày giao',
            allowFiltering: true,
            allowSorting: false
          },
          {
            fieldName: 'nd_giao',
            headerText: 'Nội dung giao',
            allowFiltering: true,
            allowSorting: false
          },
          {
            fieldName: 'dvn',
            headerText: 'Đơn vị nhận',
            allowFiltering: true,
            allowSorting: false
          },
          {
            fieldName: 'ngay_tra',
            headerText: 'Ngày trả',
            allowFiltering: true,
            allowSorting: false
          },
          {
            fieldName: 'nd_tra',
            headerText: 'Nội dung trả',
            allowFiltering: true,
            allowSorting: false
          },
          {
            fieldName: 'trangthai_ph',
            headerText: 'Trạng thái phiếu',
            allowFiltering: true,
            allowSorting: false
          },
          {
            fieldName: 'nv_th',
            headerText: 'Nhân viên TH',
            allowFiltering: true,
            allowSorting: false
          },
          {
            fieldName: 'ngay_th',
            headerText: 'Ngày TH',
            allowFiltering: true,
            allowSorting: false
          },
          {
            fieldName: 'ghichu_th',
            headerText: 'Nội dung TH',
            allowFiltering: true,
            allowSorting: false
          }
        ],
        gridGroupSettings: {
          columns: ['huonggiao']
        },
        data: [],
        selectedItems: []
      },
      DsPhieuBaoHong: {
        headers: [
          {
            fieldName: 'ma_tb',
            headerText: 'Số máy/Acc',
            allowFiltering: true,
            allowSorting: false
          },
          {
            fieldName: 'ma_tb',
            headerText: 'Số ảo',
            allowFiltering: true,
            allowSorting: false
          },
          {
            fieldName: 'loaihinh_tb',
            headerText: 'Loại hình',
            allowFiltering: true,
            allowSorting: false
          },
          {
            fieldName: 'ten_tb',
            headerText: 'Tên thuê bao',
            allowFiltering: true,
            allowSorting: false
          },
          {
            fieldName: 'diachi_ld',
            headerText: 'Địa chỉ LĐ',
            allowFiltering: true,
            allowSorting: false
          },
          {
            fieldName: 'donvinhan',
            headerText: 'Đơn vị nhận',
            allowFiltering: true,
            allowSorting: false
          },
          {
            fieldName: 'diachi_dau',
            headerText: 'Địa chỉ LĐ (Điểm đầu)',
            allowFiltering: true,
            allowSorting: false
          },
          {
            fieldName: 'diachi_cuoi',
            headerText: 'Địa chỉ LĐ (Điểm cuối)',
            allowFiltering: true,
            allowSorting: false
          },
          {
            fieldName: 'ngay_bh',
            headerText: 'Ngày BH',
            allowFiltering: true,
            allowSorting: false
          },
          {
            fieldName: 'goi_kt',
            headerText: 'Gói KT',
            allowFiltering: true,
            allowSorting: false
          },
          {
            fieldName: 'kenh_tn',
            headerText: 'Kênh tiếp nhận',
            allowFiltering: true,
            allowSorting: false
          },
          {
            fieldName: 'chitieu_tg',
            headerText: 'Chỉ tiêu TG',
            allowFiltering: true,
            allowSorting: false
          },
          {
            fieldName: 'tgxl',
            headerText: 'Giờ XL',
            allowFiltering: true,
            allowSorting: false
          },
          {
            fieldName: 'gio_ck',
            headerText: 'Giờ CK',
            allowFiltering: true,
            allowSorting: false
          },
          {
            fieldName: 'gio_conlai',
            headerText: 'Giờ còn lại',
            allowFiltering: true,
            allowSorting: false
          },
          {
            fieldName: 'mucdo',
            headerText: 'Mức độ',
            allowFiltering: true,
            allowSorting: false
          },
          {
            fieldName: 'ds_nhanvien_th',
            headerText: 'Nhân viên TH',
            allowFiltering: true,
            allowSorting: false
          },
          {
            fieldName: 'tinhtrang',
            headerText: 'Tình trạng',
            allowFiltering: true,
            allowSorting: false
          },
          {
            fieldName: 'ten_plkh',
            headerText: 'Phân loại KH',
            allowFiltering: true,
            allowSorting: false
          },
          {
            fieldName: 'dienthoai_lh',
            headerText: 'Số liên hệ',
            allowFiltering: true,
            allowSorting: false
          },
          {
            fieldName: 'luot_bh',
            headerText: 'Lượt BH',
            allowFiltering: true,
            allowSorting: false
          },
          {
            fieldName: 'lan_pa',
            headerText: 'KH gọi',
            allowFiltering: true,
            allowSorting: false
          },
          {
            fieldName: 'nguyennhan',
            headerText: 'Nguyên nhân',
            allowFiltering: true,
            allowSorting: false
          },
          {
            fieldName: 'trangthai_bh',
            headerText: 'Trạng thái',
            allowFiltering: true,
            allowSorting: false
          },
          {
            fieldName: 'ngaygiao',
            headerText: 'Ngày giao',
            allowFiltering: true,
            allowSorting: false
          },
          {
            fieldName: 'nd_giao',
            headerText: 'Nội dung giao',
            allowFiltering: true,
            allowSorting: false
          },
          {
            fieldName: 'lan_ks',
            headerText: 'Lần KS',
            allowFiltering: true,
            allowSorting: false
          },
          {
            fieldName: 'ngay_sd',
            headerText: 'Ngày SD',
            allowFiltering: true,
            allowSorting: false
          },
          {
            fieldName: 'ghichu_hong',
            headerText: 'Ghi chú hỏng',
            allowFiltering: true,
            allowSorting: false
          },
          {
            fieldName: 'ghichu_th',
            headerText: 'Ghi chú TH',
            allowFiltering: true,
            allowSorting: false
          },
          {
            fieldName: 'lydoton',
            headerText: 'Lý do tồn',
            allowFiltering: true,
            allowSorting: false
          },
          {
            fieldName: 'ghichuton',
            headerText: 'Ghi chú tồn',
            allowFiltering: true,
            allowSorting: false
          },
          {
            fieldName: 'ten_dv',
            headerText: 'Đơn vị giao',
            allowFiltering: true,
            allowSorting: false
          },
          {
            fieldName: 'nhanvienql',
            headerText: 'Nhân viên QL',
            allowFiltering: true,
            allowSorting: false
          },
          {
            fieldName: 'ten_cap',
            headerText: 'Cáp gốc',
            allowFiltering: true,
            allowSorting: false
          },
          {
            fieldName: 'doi_cap',
            headerText: 'Đôi số',
            allowFiltering: true,
            allowSorting: false
          },
          {
            fieldName: 'ten_kc',
            headerText: 'Kết cuối',
            allowFiltering: true,
            allowSorting: false
          },
          {
            fieldName: 'vitri',
            headerText: 'Vị trí',
            allowFiltering: true,
            allowSorting: false
          },
          {
            fieldName: 'tientrinh',
            headerText: 'Tiến trình',
            allowFiltering: true,
            allowSorting: false
          },
          {
            fieldName: 'hensuatu',
            headerText: 'Hẹn sửa từ',
            allowFiltering: true,
            allowSorting: false
          },
          {
            fieldName: 'hensuaden',
            headerText: 'Hẹn sửa đến',
            allowFiltering: true,
            allowSorting: false
          },
          {
            fieldName: 'ten_kv',
            headerText: 'Khu vực',
            allowFiltering: true,
            allowSorting: false
          },
          {
            fieldName: 'nvkt_db',
            headerText: 'Nhân viên kỹ thuật ',
            allowFiltering: true,
            allowSorting: false
          },
          {
            fieldName: 'nvkd_db',
            headerText: 'Nhân viên kinh doanh',
            allowFiltering: true,
            allowSorting: false
          },
          {
            fieldName: 'nd_tra_con',
            headerText: 'Nội dung trả',
            allowFiltering: true,
            allowSorting: false
          },
          {
            fieldName: 'ngaytra_con',
            headerText: 'Ngày trả',
            allowFiltering: true,
            allowSorting: false
          },
          {
            fieldName: 'chuyenmang',
            headerText: 'NCC khác',
            allowFiltering: true,
            allowSorting: false
          }
        ],
        data: [],
        selectedItems: []
      },
      PhieuId: 0,
      HdTbId: 0,
      ThueBaoId: 0,
      BhId: 0,
      tag_hdbh: -1,
      Tag: '',
      labelFunctionTitle: 'THAY ĐỔI ĐƠN VỊ XỬ LÝ SỰ CỐ',
      layoutCT_DHXL: {
        Text: 'Chi tiết điều hành xử lý sự cố',
        Visibility: true
      },
      btnLayTTMoi: {
        Visible: false
      },
      vtthd_id: -1,
      vttbh_id: -1
    }
  },
  created: async function () {
    this.Loading = true
    if (this.tag != null && this.tag != '') {
      this.Tag = this.tag
    }
    if (this.bhId != null && this.bhId > 0) this.BhId = this.bhId
    let thamso = await this.lay_ds_thamso_md()
    if (thamso != null && thamso.length > 0) {
      if (thamso.filter((x) => x.ma_ts == 'BAOHONG_LH').length > 0) {
        this.IS_USING_LACHUONG_BH = true
      } else {
        this.IS_USING_LACHUONG_BH = false
      }
    }
    if (this.Tag != '' && this.IS_USING_LACHUONG_BH) {
      this.tag_hdbh = parseInt(this.Tag)
    }
    if (this.tag_hdbh != -1) {
      Promise.all([this.lay_ds_donvi_ql(LOAI_DV.DONVIQL_LD), this.NAP_DS_LACHUONG(0)])
        .then((x) => {
          console.log(x)
        })
        .finally((e) => {
          this.Loading = false
        })
      this.btnLayTTMoi.Visible = true
    } else {
      let loaidvIds = LOAI_DV.DONVIQL_LD
      if (this.baohong_id > 0) {
        loaidvIds = LOAI_DV.DONVIQL_LD + ',' + LOAI_DV.BH_QLDH
      }
      Promise.all([this.lay_ds_donvi_ql(loaidvIds), this.lay_tt_baohong_chuyento_chung()])
        .then((reponse) => {
          this.Loading = false
        })
        .finally((e) => {
          this.Loading = false
        })
    }
  },
  mounted: function () {
    this.$refs.txtSoMay.focus()
  },
  watch: {
    donviQLId: async function (val) {
      await this.lay_ds_tramvt(val, 5) // VETINH = 5;
    }
  },
  methods: {
    disabledRange: function (date) {
      return date > new Date()
    },
    toggleTimePanel() {
      this.showTimePanel = !this.showTimePanel
    },
    handleOpenChange() {
      this.showTimePanel = false
    },
    handleRangeClose() {
      this.showTimeRangePanel = false
    },
    dialogClose() {
      this.$emit('close', true)
      this.$emit('closeDialog', true)
    },
    openDialog(phieu_id, hdtb_id) {
      this.PhieuId = phieu_id
      this.HdTbId = hdtb_id
      this.Loading = true
      Promise.all([this.lay_tt_baohong_chuyento_chung()])
        .then((values) => {
          console.log('Promise.all', values)
        })
        .catch((error) => {
          console.log(`Error in promises ${error}`)
        })
        .finally((e) => {
          this.Loading = false
          this.$refs.dialogObjChuyenTo.show()
        })
      // this.$refs.dialogObjChuyenTo.show()
    },
    lay_ds_donvi_ql: async function (loaidv_id) {
      var input = {
        loaidv_id: loaidv_id
      }
      let data = this.GetData(await api2.lay_ds_donvi_ql(this.axios, input))

      this.DmDvQL = data.map((x) => ({ id: x.donvi_id, text: x.ten_ht }))
      return data
    },
    lay_ds_donvi_chuyento_baohong: async function (phieu_id) {
      var input = {
        phieu_id: phieu_id
      }
      let data = this.GetData(await api2.lay_ds_donvi_chuyento_baohong(this.axios, input))
      return data
    },
    lay_ds_phieu_bh_lachuong: async function (vdichvuvt_id, vthuebao_id) {
      var input = {
        vdichvuvt_id: vdichvuvt_id,
        vthuebao_id: vthuebao_id
      }
      let data = this.GetData(await api2.lay_ds_phieu_bh_lachuong(this.axios, input))
      this.DsPhieuBaoHong.data = data
      return data
    },
    lay_tt_baohong_chuyento_chung: async function () {
      var input = {
        vbaohong_id: this.BhId == null ? 0 : this.BhId,
        vhdtb_id: this.HdTbId == null ? 0 : this.HdTbId,
        vphieu_id: this.PhieuId == null ? 0 : this.PhieuId,
        vtthd_id: this.tthdId
      }
      /* test
      input.vhdtb_id = 4310449
      input.vphieu_id = 8292795
      */
      let data = this.GetData(await api2.lay_tt_baohong_chuyento_chung(this.axios, input))
      await this.HienThi_Thongtin_bh(data)
      return data
    },
    lay_ds_thamso_md: async function (mathamso) {
      var input = {}
      let data = this.GetData(await api2.lay_ds_thamso_md(this.axios, input))
      return data
    },
    lay_danhsach_phieuth_bh: async function (baohong_id) {
      // var input = {"baohong_id":4471646}
      var input = { baohong_id: baohong_id }
      let data = this.GetData(await api2.lay_danhsach_phieuth_bh(this.axios, input))
      return data
    },
    hienthi_ds_phieuth_chuyento: async function (vbaohong_id, vhdtb_id) {
      var input = {
        vbaohong_id: vbaohong_id == null ? 0 : vbaohong_id,
        vhdtb_id: vhdtb_id == null ? 0 : vhdtb_id
      }
      let data = this.GetData(await api2.hienthi_ds_phieuth_chuyento(this.axios, input))

      return data
    },
    lay_ds_tramvt: async function (donvi_id, loaidv_id) {
      var input = { donvi_id: donvi_id, loaidv_id: loaidv_id }
      let data = this.GetData(await api2.lay_ds_tramvt(this.axios, input))
      this.DmTramVT = data.map((x) => ({ id: x.donvi_id, text: x.ten_dv }))
      return data
    },
    lay_ds_baohong_theo_ma_tb: async function (ma_tb, dichvuvt_id) {
      var input = { ma_tb: ma_tb, dichvuvt_id: dichvuvt_id }
      let data = this.GetData(await api2.lay_ds_baohong_theo_ma_tb(this.axios, input))
      return data
    },
    lay_dvcha_theo_dvcon: async function (donvinhanId, loaidvId) {
      var input = { vdonvi_id: donvinhanId, vloaidv_id: loaidvId }
      let data = this.GetData(await api2.lay_dvcha_theo_dvcon(this.axios, input))
      return data
    },
    HienThi_DS_PhieuTH: async function () {
      let mytable = await this.hienthi_ds_phieuth_chuyento(this.BhId, this.HdTbId)
      this.DhSuCo.data = mytable
    },
    NAP_DS_LACHUONG: async function (thuebaoId) {
      try {
        await this.lay_ds_phieu_bh_lachuong(0, thuebaoId)
      } catch (ex) {
        this.ShowError(`Có lỗi khi nạp danh sách lạc hướng  ${ex}`)
      }
    },
    HienThi_Thongtin_bh: async function (dtBH) {
      if (dtBH == null || dtBH.length == 0) return
      console.log('HienThi_Thongtin_bh', dtBH)
      let ngayYc = moment(dtBH[0].NGAY_YC, this.dateBEFormat)
      if (this.BhId > 0) {
        this.txtNgayBH = moment(ngayYc).format(this.dateFormat)
        this.txtSoMay = dtBH[0].MA_TB
        this.txtLoaiTB = dtBH[0].LOAIHINH_TB
        this.txtDichVuVT = dtBH[0].TEN_DVVT
        this.txtTenTB = dtBH[0].TEN_TB
        this.txtDiachiLD = dtBH[0].DIACHI_LD
        this.txtGhiChu = dtBH[0].GHICHU
        this.txtTrangThaiBH = dtBH[0].TRANGTHAI_HD
        this.tthd_id = dtBH[0].TTHD_ID
        let baohong_id = dtBH[0].BAOHONG_ID
        this.BhId = baohong_id
        let vdvn_id = dtBH[0].DONVI_NHAN_ID
        let vdonvi_ql_id = 0
        await this.HienThi_DS_PhieuTH(baohong_id)
      }
      if (this.HdTbId > 0) {
        this.txtNgayBH = moment(ngayYc).format(this.dateFormat)
        this.txtSoMay = dtBH[0].MA_TB
        this.txtLoaiTB = dtBH[0].LOAIHINH_TB
        this.txtDichVuVT = dtBH[0].TEN_DVVT
        this.txtTenTB = dtBH[0].TEN_TB
        this.txtDiachiLD = dtBH[0].DIACHI_LD
        this.txtGhiChu = dtBH[0].GHICHU
        this.txtTrangThaiBH = dtBH[0].TRANGTHAI_HD
        this.tthd_id = dtBH[0].TTHD_ID
        //let baohong_id = dtBH[0].BAOHONG_ID
        //this.BhId = baohong_id

        await this.HienThi_DS_PhieuTH(this.BhId)
        let vdvn_id = dtBH[0].DONVI_NHAN_ID
        let vdonvi_ql_id = 0
        let donvicha = this.lay_dvcha_theo_dvcon(vdvn_id, LOAI_DV.DONVIQL_LD)
        if (donvicha != null && donvicha.length > 0) {
          vdonvi_ql_id = donvicha[0].donvi_id
        }
        if (vdonvi_ql_id == 0) {
          this.ShowAlert('Không tìm thấy đơn vị quản lý!')
          return
        }
        this.donviQLId = vdonvi_ql_id
        this.tramVtId = vdvn_id
      }
    },
    NHAN_PHIEU_LACHUONG: async function () {
      if (this.txtNoiDung == '') {
        this.ShowError('Bạn chưa nhập nội dung chuyển tổ!')
        this.$refs.txtNoiDung.focus()
        return
      }
      if (!(this.tramVtId > 0)) {
        this.ShowError('Bạn chưa chọn tổ thi công!')
        return
      }
      if (this.DsPhieuBaoHong.selectedItems == null || this.DsPhieuBaoHong.selectedItems.length == 0) {
        this.ShowError('Không có danh sách để lựa chọn ')
        return
      }
      if (this.tag_hdbh == 1) {
        this.ShowError('Thi công hiện đang trong quá trình xây dựng')
        return
      }
      if (this.tag_hdbh == 2) {
      }
    },

    MapCheck: async function (phieuId) {
      let data = this.GetData(await api2.fn_tt_giaophieu(this.axios, {param: phieuId, type: 5, param1: ''}))
      return data
    },
    gridChiTiet_Selected: async function (i, item) {
      console.log('i, item', i, item)
      this.txtNguoiGiao = item.nv_giao
      this.txtNguoiNhan = item.dvn
      try {
        this.tramVtId = item.donvi_nhan_id
        let phieuId = item.phieu_id
        console.log('tramVtId,phieuId', this.tramVtId, phieuId)
        let _check = 0
        if (this.HdTbId == 0 && this.BhId != 0) {
          _check = 1
        } else if (this.HdTbId != 0 && this.BhId == 0) {
          _check = await this.MapCheck(phieuId)
          console.log('MapCheck', phieuId, 'result:', _check)
        }
        this.tsbtnCapNhat = !(_check == 0)
      } catch (ex) {}
    },
    gridChiTiet_RowsSelected: function (items) {
      this.DhSuCo.selectedItems = items
    },
    gridBaoHong_RowsSelected: function (items) {
      this.DsPhieuBaoHong.selectedItems = items
    },
    GetData: function (response) {
      if (response.data.error === 200 || response.data.error === '200') {
        return response.data.data
      } else {
        console.log(response.data.error_code)
      }
      return []
    },
    capnhat_chuyento: async function () {
      let nhanvienId = this.$root.token.getNhanVienID()
      let donviId = this.$root.token.getDonViID()
      let userName = this.$root.token.getUserName()
      /*
      let jsondata = JSON.stringify(this.DhSuCo.selectedItems)
      jsondata.forEach((x) => {
        x.ISCHECK = 1
      })
      */
      /*
      let jsondata = JSON.stringify(
        this.DhSuCo.selectedItems.map((x) => ({
          ISCHECK: 1,
          HUONGGIAO: x.huonggiao,
          DVG: x.dvg,
          NGAY_GIAO: x.ngay_giao,
          DONVI_NHAN_ID: x.donvi_nhan_id,
          ND_GIAO: x.nd_giao,
          DVN: x.dvn,
          NGAY_TRA: x.ngay_tra,
          ND_TRA: x.nd_tra,
          LYDOTRA: x.lydotra,
          TRANGTHAI_PH: x.trangthai_ph,
          PHIEU_ID: x.phieu_id,
          TTPH_ID: x.ttph_id,
          HUONGGIAO_ID: x.huonggiao_id,
          NV_GIAO: x.nv_giao,
          NGAY_TH: x.ngay_th,
          NV_TH: x.nv_th,
          TEN_NV_TH_ID: x.ten_nv_th_id,
          NHANVIEN_TH_ID: x.nhanvien_th_id,
          NHIEMVU: x.nhiemvu,
          DONVI_ID: x.donvi_id,
          SO_DT: x.so_dt,
          TEN_NV_GIAO_ID: x.ten_nv_giao_id,
          NV_GIAO1: x.nv_giao1,
          GHICHU: x.ghichu,
          KETQUA_XL: x.ketqua_xl,
          CHUONGTRINH: 'chuong trinh pttb',
          NGUOI_CN: userName,
          DVI_GIAO_ID: x.dvi_giao_id,
          DVI_NHAN_ID: x.dvi_nhan_id
        }))
      )
      */
      let jsondata = JSON.stringify(this.DsPhieuBaoHong.selectedItems.map((x) => ({ ischeck: 1, PHIEU_ID: x.phieu_id, BAOHONG_ID: x.baohong_id })))
      /*
      vtag_hdbh in number,
        vbaohong_id in number,
        vhdtb_id in number,
        vphieu_id in number,
        vttbh_id in number,
        vdonvinhan_id number,
        vdonviql_id number,
        vnhanvien_id number,
        vnoidung in varchar2,
        vmaycn in varchar2,
        vnguoicn in varchar2,
        vngaycn in date,
        vdanhsach_json in clob
      */
      var input = {
        vtag_hdbh: this.tag_hdbh,
        vbaohong_id: this.BhId,
        vhdtb_id: this.HdTbId,
        vphieu_id: this.PhieuId,
        vttbh_id: this.ttbhId,
        vdonvinhan_id: this.donviId,
        vdonviql_id: this.donviQLId,
        vnhanvien_id: nhanvienId,
        vnoidung: this.txtNoiDung,
        vmaycn: 'web',
        vnguoicn: userName,
        vngaycn: moment(new Date()).format('DD/MM/YYYY'),
        vdanhsach_json: jsondata
      }
      /*
      [
          {
            ISCHECK: 1,
            HUONGGIAO: '6 - Ti?p nh?n chuy?n d?i di d?ng tr? sau-> tr? tru?c',
            DVG: 'Kh�ch h�ng QLVT - Vi?n th�ng H?i Ph�ng',
            NGAY_GIAO: '25/05/2020 11:04',
            DONVI_NHAN_ID: '232',
            ND_GIAO: 'null',
            DVN: 'Giao d?ch s? 1 Ho�ng Van Th? - Ph�ng B�n h�ng KV1',
            NGAY_TRA: 'null',
            ND_TRA: 'null',
            LYDOTRA: 'null',
            TRANGTHAI_PH: 'M?i',
            PHIEU_ID: '9264850',
            TTPH_ID: '1',
            HUONGGIAO_ID: '20014',
            NV_GIAO: 'T? Th? Thu Hu?ng',
            NGAY_TH: '25/05/2020 11:04',
            NV_TH: 'ttthuong1.hpg - T? Th? Thu Hu?ng',
            TEN_NV_TH_ID: '6893 - ttthuong1.hpg - T? Th? Thu Hu?ng',
            NHANVIEN_TH_ID: '6893',
            NHIEMVU: 'null',
            DONVI_ID: '232.0',
            SO_DT: 'null',
            TEN_NV_GIAO_ID: '6893 - T? Th? Thu Hu?ng',
            NV_GIAO1: 'T? Th? Thu Hu?ng',
            GHICHU: 'null',
            KETQUA_XL: 'null',
            CHUONGTRINH: 'chuong tr?nh pttb',
            NGUOI_CN: userName,
            DVI_GIAO_ID: '1123',
            DVI_NHAN_ID: '232'
          }
        ]
      */
      let data = this.GetData(await api2.capnhat_chuyento(this.axios, input))
      return data
    },
    txtMaTB_KeyPress: async function () {
      let dsBaoHong = await this.lay_ds_baohong_theo_ma_tb(this.txtSoMay, 0)
      if (dsBaoHong != null && dsBaoHong.length > 0) {
        if (dsBaoHong.length == 1) {
        } else {
          //
          alert(`Mở form frmTraCuu_ThueBao!`)
        }
      } else {
        this.ShowError(`Không tìm thấy thông tin!`)
        return
      }
      if (this.tag_hdbh != -1) {
        if (this.tag_hdbh == 2) {
          // Chua lam
          // NAP_DS_LACHUONG(Convert.ToInt64(ds.Tables[0].Rows[index]["thuebao_id"]));
          this.ThueBaoId = dsBaoHong[0].thuebao_id
          await this.NAP_DS_LACHUONG(this.ThueBaoId)
        }
      } else {
        if (this.BhId > 0) {
          let dtBH = this.lay_tt_baohong_chuyento_chung()
          this.HienThi_Thongtin_bh(dtBH)
        } else if (this.HdTbId > 0) {
          let dtBH = this.lay_tt_baohong_chuyento_chung()
          this.HienThi_Thongtin_bh(dtBH)
        }
      }
    },
    NAP_DS_LUOI: function (i, item) {
      this.txtNgayBH = item.ngay_bh
      this.txtSoMay = item.ma_tb
      //txtDichVuVT.Text = cboDichVuVT.Text;
      this.txtLoaiTB = item.loaihinh_tb
      this.txtTenTB = item.ten_tb
      this.txtDiachiLD = item.diachi_ld
      this.txtGhiChu = item.ghichu
      this.txtTrangThaiBH = item.trangthai_bh
    },
    btnLayTTMoi_Click: async function () {
      try {
        this.$refs.txtSoMay.focus()
        this.Loading = true
        await this.NAP_DS_LACHUONG(0)
        this.Loading = false
      } catch (ex) {
        this.Loading = false
      }
    },
    tsbtnCapNhat_Click: async function () {
      if (this.txtNoiDung == '') {
        this.ShowAlert('Bạn chưa nhập nội dung chuyển tổ!')
        this.$refs.txtNoiDung.focus()
        return
      }
      if (!(this.tramVtId > 0)) {
        this.ShowAlert('Bạn chưa chọn tổ thi công!')
        this.$refs.tramVtId.focus()
        return
      }
      try {
        this.Loading = true
        await this.capnhat_chuyento()
        /* ham da gop 
        if (this.tag_hdbh == -1) {
          await this.capnhat_chuyento()
        } else {
          await this.NHAN_PHIEU_LACHUONG()
        }
        */
        this.Loading = false
      } catch (ex) {
        this.Loading = false
      }
    },
    SearchDanhBa: function () {
      this.$refs.searchAccountDialog.show()
    },
    searchAccountDialogClose(data) {
      if (data != null) {
        this.txtSoMay = data.ma_tb
        this.txtMaTB_KeyPress()
      }
      this.$refs.searchAccountDialog.hide()
      this.closeDialogChuyenTo()
    },
    closeDialogChuyenTo: function () {
      this.$emit('close', this.PhieuId)
      this.$emit('closeChuyenTo', this.PhieuId)
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

<style>
.chuyenTo .page-content {
  top: 130px !important;
}
.chuyenToPopupBody {
  min-height: 800px;
}
</style>
