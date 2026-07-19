<template>
  <div class="box-form">
    <div class="legend-title">
      <div class="pull-left"><span class="icon fa fa-angle-up" :class="{ 'fa-angle-down': !expanded }" @click.prevent="expanded = !expanded"></span> Thông tin hoàn công</div>
      <div class="pull-right">
        <div class="list-checks">
          <div class="item">
            <div class="check-action red">
              <input v-model="checkboxLayThongSoKyThuat" type="checkbox" class="check" @change="checkboxLayThongSoKyThuatChange" />
              <span class="name">Lấy thông số thi công</span>
            </div>
          </div>
          <div class="item red">Liên hệ: {{ formThongTinHoanCong.thongTinLienHe }}</div>
        </div>
      </div>
      <div class="clearfix"></div>
    </div>
    <b-collapse class="row" v-model="expanded">
      <div class="col-sm-3 col-12">
        <div class="info-row">
          <div class="key w70">Ngày GV</div>
          <div class="value w20 padt7">
            <div class="check-action">
              <input type="checkbox" class="check" v-model="formThongTinHoanCong.checkedNgayGV" :disabled="!formThongTinHoanCong.enableCheckboxNgayGV" @change="toggleNgayGV" />
              <span class="name"></span>
            </div>
          </div>
          <div class="value">
            <div class="input-icon-right">
              <ejs-datetimepicker id="ngayGV" :enabled="formThongTinHoanCong.enableNgayGV" v-model="formThongTinHoanCong.ngayGV" format="dd/MM/yyyy HH:mm:ss"></ejs-datetimepicker>
            </div>
          </div>
        </div>
        <div class="info-row">
          <div class="key w70">Ngày HT</div>
          <div class="value w20 padt7">
            <div class="check-action">
              <input type="checkbox" class="check" @change="toggleNgayHT" v-model="formThongTinHoanCong.checkedNgayHT" :disabled="!formThongTinHoanCong.enableCheckboxNgayHT" />
              <span class="name"></span>
            </div>
          </div>
          <div class="value">
            <div class="input-icon-right">
              <ejs-datetimepicker id="ngayHT" :enabled="formThongTinHoanCong.enableNgayHT" v-model="formThongTinHoanCong.ngayHT" format="dd/MM/yyyy HH:mm:ss"></ejs-datetimepicker>
            </div>
          </div>
        </div>
        <div class="info-row">
          <div class="key w70">Bras</div>
          <div class="value">
            <div class="select-custom">
              <ejs-dropdownlist
                ref="danhSachBras"
                :dataSource="danhSachBras"
                :enabled="false"
                :allowFiltering="true"
                :fields="{ text: 'tenbras', value: 'bras_id' }"
                @change="brasChange"
                v-model="formThongTinHoanCong.selectedBras"
                @filtering="onFilteringDropDownList($event, danhSachBras, 'tenbras')"
              ></ejs-dropdownlist>
            </div>
          </div>
        </div>
        <div class="info-row">
          <div class="key w70">Dslam</div>
          <div class="value">
            <div class="select-custom">
              <ejs-dropdownlist :enabled="false" :dataSource="danhSachLAM" :allowFiltering="true" :fields="{ text: 'tendslam', value: 'dslam_id' }" v-model="formThongTinHoanCong.selectedLAM" @filtering="onFilteringDropDownList($event, danhSachLAM, 'tendslam')"></ejs-dropdownlist>
            </div>
          </div>
        </div>
        <div class="row">
          <div class="col-sm-6 col-12">
            <div class="info-row">
              <div class="key w70">Username</div>
              <div class="value">
                <ejs-textbox v-model="formThongTinHoanCong.username" :readonly="true"></ejs-textbox>
              </div>
            </div>
          </div>
          <div class="col-sm-6 col-12">
            <div class="info-row">
              <div class="key w70">Password</div>
              <div class="value">
                <ejs-textbox v-model="formThongTinHoanCong.password" :readonly="true"></ejs-textbox>
              </div>
            </div>
          </div>
        </div>
        <div class="row">
          <div class="col-sm-6 col-12">
            <div class="info-row">
              <div class="key w70">System</div>
              <div class="value">
                <ejs-textbox v-model="formThongTinHoanCong.system" :readonly="true"></ejs-textbox>
              </div>
            </div>
          </div>
          <div class="col-sm-6 col-12">
            <div class="info-row">
              <div class="key w70">Rack</div>
              <div class="value">
                <ejs-textbox v-model="formThongTinHoanCong.rack" :readonly="true"></ejs-textbox>
              </div>
            </div>
          </div>
        </div>
        <div class="info-row">
          <div class="key w70">Adsl port</div>
          <div class="value">
            <ejs-textbox v-model="formThongTinHoanCong.ADSLPort"></ejs-textbox>
          </div>
        </div>
        <div class="info-row">
          <div class="key w70">Ghi chú</div>
          <div class="value">
            <input type="text" class="form-control" />
          </div>
        </div>
      </div>
      <div class="col-sm-4 col-12">
        <div class="info-row">
          <div class="key w70">Người GV</div>
          <div class="value">
            <div class="select-custom">
              <ejs-dropdownlist :dataSource="danhSachNguoiGV" :allowFiltering="true" :fields="{ text: 'ten_nv', value: 'nhanvien_id' }" v-model="formThongTinHoanCong.selectedNguoiGV" @filtering="onFilteringDropDownList($event, danhSachNguoiGV, 'ten_nv')"></ejs-dropdownlist>
            </div>
          </div>
        </div>
        <div class="row">
          <div class="col-sm-5 col-12">
            <div class="info-row">
              <div class="key w70">Trạm T.Bị</div>
              <div class="value">
                <ejs-textbox v-model="formThongTinHoanCong.tramThietBi"></ejs-textbox>
              </div>
            </div>
          </div>
          <div class="col-sm-7 col-12">
            <div class="info-row">
              <div class="key w90">SL cuộc gọi</div>
              <div class="value">
                <ejs-textbox v-model="formThongTinHoanCong.soLuongCuocGoi"></ejs-textbox>
              </div>
            </div>
          </div>
        </div>
        <div class="row">
          <div class="col-sm-5 col-12">
            <div class="info-row">
              <div class="key w70">Chi tiết DV</div>
              <div class="value">
                <div class="select-custom">
                  <ejs-dropdownlist
                    :dataSource="danhSachChiTietDichVu"
                    :enabled="false"
                    :allowFiltering="true"
                    :fields="{ text: 'chitiet_dv', value: 'ctdv_id' }"
                    v-model="formThongTinHoanCong.selectedChiTietDichVu"
                    @filtering="onFilteringDropDownList($event, danhSachChiTietDichVu, 'chitiet_dv')"
                  ></ejs-dropdownlist>
                </div>
              </div>
            </div>
          </div>
          <div class="col-sm-7 col-12">
            <div class="info-row">
              <div class="key w110">IP khách hàng</div>
              <div class="value">
                <ejs-textbox ref="ipKhachHang" v-model="formThongTinHoanCong.IPKhachHang"></ejs-textbox>
              </div>
            </div>
          </div>
        </div>
        <div class="row">
          <div class="col-sm-5 col-12">
            <div class="info-row">
              <div class="key w70">Thiết bị</div>
              <div class="value">
                <div class="select-custom">
                  <ejs-dropdownlist :dataSource="danhSachThietBi" :allowFiltering="true" :fields="{ text: 'ten_tbi', value: 'thietbi_id' }" v-model="formThongTinHoanCong.selectedThietBi" @filtering="onFilteringDropDownList($event, danhSachThietBi, 'ten_tbi')"></ejs-dropdownlist>
                </div>
              </div>
            </div>
          </div>
          <div class="col-sm-7 col-12">
            <div class="info-row">
              <div class="key w90">Chọn port</div>
              <div class="value">
                <div class="list-checks">
                  <div class="item" :class="{ disabled: !formThongTinHoanCong.enableButtonChonPortTuDong }">
                    <button class="btn btn-second pad0 h31 lh31 w31 border-btn-port" @click="btnChonPort_TuDong_Click">
                      <span style="line-height: inherit" class="one-check f20 text-warning"></span>
                    </button>
                    T.động
                  </div>
                  <div class="item" :class="{ disabled: !formThongTinHoanCong.enableButtonChonPortThuCong }">
                    <button class="btn btn-second pad0 h31 lh31 w31 border-btn-port" @click="btnChonPort_Click">
                      <span style="line-height: inherit" class="one-check f20"></span>
                    </button>
                    N.công
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
        <div class="row">
          <div class="col-sm-5 col-12">
            <div class="info-row">
              <div class="key w70">Slot</div>
              <div class="value">
                <ejs-textbox v-model="formThongTinHoanCong.slot" :readonly="true"></ejs-textbox>
              </div>
            </div>
          </div>
          <div class="col-sm-7 col-12">
            <div class="info-row">
              <div class="key w90">Port</div>
              <div class="value">
                <ejs-textbox v-model="formThongTinHoanCong.port" :readonly="true"></ejs-textbox>
              </div>
            </div>
          </div>
        </div>
        <div class="row">
          <div class="col-sm-5 col-12">
            <div class="info-row">
              <div class="key w70">Vpi</div>
              <div class="value">
                <ejs-textbox v-model="formThongTinHoanCong.VPI" :readonly="true"></ejs-textbox>
              </div>
            </div>
          </div>
          <div class="col-sm-7 col-12">
            <div class="info-row">
              <div class="key w90">Vci</div>
              <div class="value">
                <ejs-textbox v-model="formThongTinHoanCong.VCI" :readonly="true"></ejs-textbox>
              </div>
            </div>
          </div>
        </div>
        <div class="row">
          <div class="col-sm-5 col-12">
            <div class="info-row">
              <div class="key w70">Shelf</div>
              <div class="value">
                <ejs-textbox v-model="formThongTinHoanCong.shelf" :readonly="true"></ejs-textbox>
              </div>
            </div>
          </div>
          <div class="col-sm-7 col-12">
            <div class="info-row">
              <div class="key w90">Port Mdf</div>
              <div class="value">
                <ejs-textbox v-model="formThongTinHoanCong.portMdf" :readonly="true"></ejs-textbox>
              </div>
            </div>
          </div>
        </div>
        <div class="row">
          <div class="col-sm-5 col-12">
            <div class="info-row">
              <div class="key w70">SvLan</div>
              <div class="value">
                <ejs-textbox v-model="formThongTinHoanCong.SvLan" :readonly="!checkEnabledElement('CAPNHAT_PORT')"></ejs-textbox>
              </div>
            </div>
          </div>
          <div class="col-sm-7 col-12">
            <div class="info-row">
              <div class="key w90">CvLan</div>
              <div class="value">
                <ejs-textbox v-model="formThongTinHoanCong.CvLan" :readonly="!checkEnabledElement('CAPNHAT_PORT')"></ejs-textbox>
              </div>
            </div>
          </div>
        </div>
      </div>
      <div class="col-sm-5 col-12">
        <!--        Nếu sử dụng cap man 2.0, thì hiển thị thông tin mới, lấy từ database-->

        <div v-if="formThongTinHoanCong.isUsingCapMan">
          <div class="col raw-html" v-html="rawHTML"></div>
        </div>
        <!--        Nếu không sử dụng cap man 2.0 thì hiển thị thông tin mã truy cập... như bình thường-->
        <template v-else>
          <div class="row">
            <div class="col-sm-4 col-12">
              <div class="info-row">
                <div class="key w90">Mã truy nhập</div>
                <div class="value">
                  <ejs-textbox style="background-color: #FFF9EB;" v-model="formThongTinHoanCong.maTruyCap" :readonly="true"></ejs-textbox>
                </div>
              </div>
            </div>
            <div class="col-sm-4 col-12">
              <div class="info-row">
                <div class="key w90">Loại cáp</div>
                <div class="value">
                  <div class="select-custom">
                    <ejs-dropdownlist :dataSource="danhSachLoaiCap" :allowFiltering="true" :fields="{ text: 'loaicap', value: 'loaicap_id' }" v-model="formThongTinHoanCong.selectedLoaiCap" @filtering="onFilteringDropDownList($event, danhSachLoaiCap, 'loaicap')"></ejs-dropdownlist>
                  </div>
                </div>
              </div>
            </div>
            <div class="col-sm-4 col-12">
              <div class="info-row">
                <div class="key w120">Hộp cáp ngọn</div>
                <div class="value">
                  <ejs-textbox v-model="formThongTinHoanCong.hopCapNgon"></ejs-textbox>
                </div>
              </div>
            </div>
          </div>
          <div class="row">
            <div class="col-sm-4 col-12">
              <div class="info-row">
                <div class="key w90">Tủ cáp gốc</div>
                <div class="value">
                  <ejs-textbox v-model="formThongTinHoanCong.tuCapGoc"></ejs-textbox>
                </div>
              </div>
            </div>
            <div class="col-sm-4 col-12">
              <div class="info-row">
                <div class="key w90">Tủ cáp ngọn</div>
                <div class="value">
                  <ejs-textbox v-model="formThongTinHoanCong.tuCapNgon"></ejs-textbox>
                </div>
              </div>
            </div>
            <div class="col-sm-4 col-12">
              <div class="info-row">
                <div class="key w90">Tuyến cáp</div>
                <div class="value">
                  <ejs-textbox v-model="formThongTinHoanCong.tuyenCap"></ejs-textbox>
                </div>
              </div>
            </div>
          </div>
          <div class="row">
            <div class="col-sm-4 col-12">
              <div class="info-row">
                <div class="key w90">Đôi cáp gốc</div>
                <div class="value">
                  <ejs-textbox v-model="formThongTinHoanCong.doiCapGoc"></ejs-textbox>
                </div>
              </div>
            </div>
            <div class="col-sm-4 col-12">
              <div class="info-row">
                <div class="key w90">Đôi cáp ngọn</div>
                <div class="value">
                  <ejs-textbox v-model="formThongTinHoanCong.doiCapNgon"></ejs-textbox>
                </div>
              </div>
            </div>
            <div class="col-sm-4 col-12">
              <div class="info-row">
                <div class="key w90">Liên tủ</div>
                <div class="value">
                  <ejs-textbox v-model="formThongTinHoanCong.lienTu"></ejs-textbox>
                </div>
              </div>
            </div>
          </div>
          <div class="row">
            <div class="col-sm-4 col-12">
              <div class="info-row">
                <div class="key w90">Cáp gốc</div>
                <div class="value">
                  <ejs-textbox v-model="formThongTinHoanCong.capGoc"></ejs-textbox>
                </div>
              </div>
            </div>
            <div class="col-sm-4 col-12">
              <div class="info-row">
                <div class="key w90">Cáp ngọn</div>
                <div class="value">
                  <ejs-textbox v-model="formThongTinHoanCong.capNgon"></ejs-textbox>
                </div>
              </div>
            </div>
            <div class="col-sm-4 col-12">
              <div class="info-row">
                <div class="key w90">Cự ly</div>
                <div class="value">
                  <ejs-numerictextbox v-model="formThongTinHoanCong.cuLy"></ejs-numerictextbox>
                </div>
              </div>
            </div>
          </div>
        </template>
        <div class="row">
          <div class="col-sm-4 col-12">
            <div class="info-row">
              <div class="key w90">ONU Pass</div>
              <div class="value">
                <ejs-textbox v-model="formThongTinHoanCong.ONUPass"></ejs-textbox>
              </div>
            </div>
          </div>
          <div class="col-sm-8 col-12">
            <div class="info-row">
              <div class="key w90">SLID</div>
              <div class="value">
                <ejs-textbox v-model="formThongTinHoanCong.SLID"></ejs-textbox>
              </div>
            </div>
          </div>
        </div>
        <div class="info-row">
          <div class="key w90">Nội dung TH</div>
          <div class="value">
            <div class="input-more-button">
              <button class="btn" @click="updateNoiDungTHClick">
                <span class="nc-icon-glyph ui-1_edit-76"></span>
              </button>
              <ejs-textbox v-model="formThongTinHoanCong.noiDungTH"></ejs-textbox>
            </div>
          </div>
        </div>
        <div class="info-row">
          <div class="key w90">Nội dung giao</div>
          <div class="value">
            <ejs-textbox v-model="formThongTinHoanCong.noiDungGiao"></ejs-textbox>
          </div>
        </div>
        <div class="row">
          <div class="col-sm-6 col-12">
            <div class="info-row">
              <div class="key w90">Lý do trả</div>
              <div class="value">
                <input type="text" class="form-control" v-model="formThongTinHoanCong.lyDoTra" />
              </div>
            </div>
          </div>
          <div class="col-sm-6 col-12">
            <div class="info-row">
              <div class="key w90">Nội dung trả</div>
              <div class="value">
                <input type="text" class="form-control red" v-model="formThongTinHoanCong.noiDungTra" />
              </div>
            </div>
          </div>
        </div>
      </div>
    </b-collapse>
    <chon-port ref="modalChonPort" @select="capNhatPortNhanCong"></chon-port>
  </div>
</template>

<script>
import {
  fetchDanhSachLoaiCap,
  fetchDanhSachBras,
  getDanhSachChiTietDichVu,
  getDanhSachThietBi,
  getDanhSachNhanVien,
  getKieuLapDat,
  checkLapKemCoSan,
  getDanhSachPortTheoMaThueBao,
  getDanhSachPortTheoHopDongID,
  updateNoiDungTH,
  getDSLAMTheoPortMoi,
  getDSLAMTheoDiaChi,
  getDanhSachPortTuDong,
  getDanhSachPortTuDongHNI,
  fetchDanhSachLAM
} from '../api/apiThongTinHoanCong'
import api from '../api/new-api'

import ChonPort from '@/modules/EXTS/QUANTRI/ThayDoiThongSoInternet/chon-port'

import { pagingAndFilter } from '@/modules/admin/category/Staff/mixins/pagingAndFilter.js'
// import { KieuLap, TrangThaiPort, LoaiHopDong } from '../consts'
import { TRANGTHAI_PORT, KIEULAP, LoaiHopDong, LOAI_DV } from '../consts/consts'
import { getDanhSachDiaChi, getDiaChiIDByHDTBID, getDiaChiIDByThueBaoID, queryData, queryTable } from '../api'
import Vue from 'vue'
import { DateTimePickerPlugin } from '@syncfusion/ej2-vue-calendars'
Vue.use(DateTimePickerPlugin)

export default {
  name: 'FormCThongTinHoanCong',
  mixins: [pagingAndFilter],
  props: {
    formThongTinHoanCong: Object,
    thueBaoIms: Object,
    kieuldId: {
      type: Number,
      default: undefined
    },
    loaitbId: {
      type: Number,
      default: undefined
    },
    maThueBao: {
      type: String,
      default: ''
    },
    hdtbId: {
      type: Number,
      default: null
    },
    phieuId: {
      type: Number,
      default: null
    },
    donViId: {
      type: Number,
      default: null
    },
    loaiHopDongId: {
      type: Number | String,
      default: 0
    },
    thueBaoId: {
      type: Number,
      default: 0
    },
    danhSachThaoTac: {
      type: Array,
      default: () => []
    },
    quytrinhId: {
      type: [Number, String],
      default: 0
    },
  },
  components: {
    ChonPort
  },
  data: () => ({
    danhSachLoaiCap: [],
    danhSachBras: [],
    danhSachLAM: [],
    danhSachNguoiGV: [],
    danhSachChiTietDichVu: [],
    danhSachThietBi: [],
    checkboxLayThongSoKyThuat: false,
    expanded: true
  }),
  computed: {
    rawHTML() {
      if (!this.formThongTinHoanCong.danhSachThongTinKyThuat || this.formThongTinHoanCong.danhSachThongTinKyThuat.length === 0) {
        return ''
      }
      return this.formThongTinHoanCong.danhSachThongTinKyThuat
        .map((item) => {
          return item.format_tt.replace('{0}', item.ten_truong) + item.format_gt.replace('{0}', item.giatri || '')
        })
        .join('')
    }
  },
  async created() {
    await this.loadDataCombobox()
  },
  methods: {
    checkEnabledElement(code) {
      if (!this.danhSachThaoTac || this.danhSachThaoTac.length === 0) {
        return true
      }
      const thaoTac = this.danhSachThaoTac.find((element) => element.code === code)
      if (!thaoTac) {
        return false
      }
      return Number(thaoTac.enable) === 1
    },
    loadDataCombobox() {
      Promise.all([fetchDanhSachLoaiCap(this.axios), fetchDanhSachBras(this.axios), getDanhSachChiTietDichVu(this.axios), getDanhSachThietBi(this.axios), getDanhSachNhanVien(this.axios)]).then((response) => {
        this.danhSachLoaiCap = response[0]
        this.danhSachBras = response[1]
        this.danhSachChiTietDichVu = response[2]
        this.danhSachThietBi = response[3]
        this.danhSachNguoiGV = response[4]
      })
    },
    toggleNgayGV() {
      this.formThongTinHoanCong.enableNgayGV = this.formThongTinHoanCong.checkedNgayGV
    },

    toggleNgayHT() {
      this.formThongTinHoanCong.enableNgayHT = this.formThongTinHoanCong.checkedNgayHT
    },

    // async choosePortAutomatic() {
    //   if (!this.kieuldId) {
    //     this.$toast.error('Không tìm thấy thông tin!')
    //     return
    //   }
    //   let trangThaiPort = 0
    //   let newPortId = 0
    //   let newVciVpiId = 0
    //   let thamSo = '-1'

    //   // int vloaitbid = Convert.ToInt32(dsIn.Tables[0].Rows[nIndex][loaitb_id].ToString().Trim());
    //   // use this.loaitbId
    //   if (!this.formThongTinHoanCong.maTruyCap || !this.formThongTinHoanCong.maTruyCap.trim()) {
    //     trangThaiPort = TrangThaiPort.CHUA_SD
    //     thamSo = '4, 7, 9'
    //   } else {
    //     const kieuLapDat = await getKieuLapDat(this.axios, this.kieuldId)
    //     let kieuLapAPI1 = 0 // gọi api check port lắp kèm có sẵn
    //     let kieuLapAPI2 = 0 // gọi api lấy danh sách port lắp kèm
    //     let message = '' // Hiển thị thông báo kèm hoặc có sẵn

    //     switch (kieuLapDat) {
    //       case KieuLap.LAP_LINE_SAN:
    //       case KieuLap.PORT_THEO_CAP:
    //         kieuLapAPI1 = kieuLapDat
    //         kieuLapAPI2 = c.LAP_LINE_SAN
    //         message = 'có sẵn'
    //         break
    //       case KieuLap.LAP_KEM:
    //         kieuLapAPI1 = KieuLap.LAP_KEM
    //         kieuLapAPI2 = KieuLap.LAP_KEM
    //         message = 'kèm'
    //         break
    //       default:
    //         break
    //     }

    //     const isLapKemCoSan = await checkLapKemCoSan(this.axios, {
    //       kieuld_id: this.kieuldId,
    //       kieu: kieuLapAPI1
    //     })

    //     if (isLapKemCoSan) {
    //       // Kiểm tra xem mã truy nhập đã được cấp port hay chưa, chưa cấp thì không được chọn port.
    //       const pr = {
    //         ma_tb: this.formThongTinHoanCong.maTruyCap,
    //         kieu_id: kieuLapAPI2
    //       }
    //       const lstPort = await getDanhSachPortTheoMaThueBao(this.axios, pr)
    //       if (!lstPort || lstPort.length <= 0 || !lstPort[0] || !lstPort[0].port_id) {
    //         this.$toast.error(`Account ${this.maThueBao.trim()} đang lắp kèm với thuê bao ${this.formThongTinHoanCong.maTruyCap.trim()}
    //             Bạn hãy thực hiện cấp port cho account ${this.formThongTinHoanCong.maTruyCap.trim()} trước`)
    //         return
    //       }

    //       newPortId = this.formThongTinHoanCong.portId || lstPort[0].port_id

    //       if (this.$root.token.getMaCCBS() === 'HNI') {
    //         let lstPortByHDID = await getDanhSachPortTheoHopDongID(this.axios, this.hdtbId, this.formThongTinHoanCong.maThueBao.trim())
    //         if (lstPortByHDID && lstPortByHDID.length > 0) {
    //           newPortId = lstPortByHDID[0].port_id
    //           newVciVpiId = lstPortByHDID[0].vci_vpi_id
    //           if (newPortId === 0) {
    //             this.$toast.error(`Chưa có thông tin port cho thuê bao ${message}: ${this.formThongTinHoanCong.maTruyCap}`)
    //             return
    //           }
    //         }
    //       }
    //       trangThaiPort = 0
    //     } else {
    //       newPortId = 0
    //       trangThaiPort = TrangThaiPort.CHUA_SD
    //     }
    //   }

    //   let diaChiId = -1
    //   const loaiHD_ID = Number(this.loaiHopDongId)
    //   if (loaiHD_ID === LoaiHopDong.DAT_MOI || loaiHD_ID === LoaiHopDong.DI_CHUYEN) {
    //     diaChiId = await getDiaChiIDByHDTBID(this.axios, this.hdtbId)
    //   } else {
    //     diaChiId = await getDiaChiIDByThueBaoID(this.axios, this.thueBaoId)
    //   }
    //   let phuongId = 0
    //   let phoId = 0
    //   let apId = 0
    //   let khuId = 0

    //   if (diaChiId !== -1 && diaChiId !== 0) {
    //     let danhSachDiaChi = await getDanhSachDiaChi(this.axios, diaChiId)
    //     if (danhSachDiaChi.length > 0) {
    //       phuongId = danhSachDiaChi[0].phuong_id ? Number(danhSachDiaChi[0].phuong_id) : 0
    //       phoId = danhSachDiaChi[0].pho_id ? Number(danhSachDiaChi[0].pho_id) : 0
    //       apId = danhSachDiaChi[0].ap_id ? Number(danhSachDiaChi[0].ap_id) : 0
    //       khuId = danhSachDiaChi[0].khu_id ? Number(danhSachDiaChi[0].khu_id) : 0
    //     }
    //   } else {
    //     this.$toast.error('Không có thông tin về địa chỉ lắp đặt để chọn port. Bạn hãy chọn nhân công hoặc liên hệ admin để xử lý !')
    //     return
    //   }
    //   let danhSachDSLAM = []
    //   if (this.formThongTinHoanCong.maTruyCap && this.formThongTinHoanCong.maTruyCap.trim()) {
    //     danhSachDSLAM = await getDSLAMTheoPortMoi(this.axios, newPortId)
    //   } else {
    //     danhSachDSLAM = await getDSLAMTheoDiaChi(this.axios, {
    //       loaitb_id: this.loaitbId,
    //       thamso: thamSo,
    //       loaidv_id: 39, // LOAI_DV.TRAM_PORT.ToString()
    //       congnghe_id: 0,
    //       phuong_id: phuongId,
    //       pho_id: phoId,
    //       ap_id: apId,
    //       khu_id: khuId,
    //       donvi_id: this.formThongTinHoanCong.thongTinDonVi
    //     })
    //   }

    //   if (danhSachDSLAM && danhSachDSLAM.length > 0) {
    //     let flagCheck = false
    //     for (const element of danhSachDSLAM) {
    //       const dslam = element
    //       if (dslam && dslam.dslam_id) {
    //         this.formThongTinHoanCong.tramThietBiId = dslam.donvi_id

    //         this.formThongTinHoanCong.tramThietBi = await queryData(this.axios, {
    //           id_neo: 'ten_dv',
    //           in_table: 'admin.donvi',
    //           in_dk: `where donvi_id = ${dslam.donvi_id}`
    //         })

    //         let danhSachPort = []
    //         if (this.$root.token.getMaCCBS() !== 'HNI') {
    // danhSachPort = await getDanhSachPortTuDong(this.axios, {
    //   dslam_id: dslam.dslam_id,
    //   port_id: newPortId,
    //   trangthai: trangThaiPort,
    //   loaitb_id: this.loaitbId
    // })
    //         } else {
    //           danhSachPort = await getDanhSachPortTuDongHNI(this.axios, {
    //             dslam_id: dslam.dslam_id,
    //             port_id: newPortId,
    //             vci_vpi_id: newVciVpiId
    //           })
    //         }

    //         if (danhSachPort.length > 0) {
    //           const firstPort = danhSachPort[0]
    //           console.log('firstPort')
    //           console.log(firstPort)

    //           // this.formThongTinHoanCong.port = firstPort.port || ''
    //           // this.formThongTinHoanCong.VPI = firstPort.vpi || ''
    //           // this.formThongTinHoanCong.VCI = firstPort.vci || ''
    //           // this.formThongTinHoanCong.slot = firstPort.slot || ''
    //           // this.formThongTinHoanCong.rack = firstPort.rack || ''
    //           // this.formThongTinHoanCong.shelf = firstPort.shelf || ''
    //           // this.formThongTinHoanCong.system = firstPort.system || ''
    //           // this.formThongTinHoanCong.portMdf = firstPort.port_mdf || ''
    //           // this.formThongTinHoanCong.portId = firstPort.port_id
    //           // this.formThongTinHoanCong.vciVpiId = firstPort.vci_vpi_id
    //           // this.formThongTinHoanCong.selectedBras = firstPort.bras_id
    //           // this.formThongTinHoanCong.selectedLAM = firstPort.dslam_cha_id
    //           // CapNhat_port();
    //           this.capNhatPortTuDong(firstPort)
    //           flagCheck = true
    //           break
    //         }
    //       }
    //     }
    //     if (!flagCheck) {
    //       this.$toast.error('Không tìm thấy thông tin port !')
    //     }
    //   } else {
    //     this.$toast.error('Không tìm thấy thông tin port !')
    //   }
    // },

    // async choosePortManual() {
    //   if (!this.kieuldId) {
    //     this.$toast.error('Không tìm thấy thông tin!')
    //     return
    //   }
    //   let trangThaiPort = 0
    //   let newPortId = this.formThongTinHoanCong.portId
    //   let newVciVpiId = this.formThongTinHoanCong.vciVpiId
    //   let kieuLapAPI1 = 0 // gọi api check port lắp kèm có sẵn
    //   let kieuLapAPI2 = 0 // gọi api lấy danh sách port lắp kèm

    //   // int vloaitbid = Convert.ToInt32(dsIn.Tables[0].Rows[nIndex][loaitb_id].ToString().Trim());
    //   // use this.loaitbId
    //   if (!this.formThongTinHoanCong.maTruyCap || !this.formThongTinHoanCong.maTruyCap.trim()) {
    //     trangThaiPort = TrangThaiPort.CHUA_SD
    //   } else {
    //     const kieuLapDat = await getKieuLapDat(this.axios, this.kieuldId)
    //     let message = '' // Hiển thị thông báo kèm hoặc có sẵn

    //     switch (kieuLapDat) {
    //       case KieuLap.LAP_LINE_SAN:
    //         kieuLapAPI1 = kieuLapDat
    //         kieuLapAPI2 = KieuLap.LAP_LINE_SAN
    //         message = 'có sẵn'
    //         break
    //       case KieuLap.LAP_KEM:
    //         kieuLapAPI1 = KieuLap.LAP_KEM
    //         kieuLapAPI2 = KieuLap.LAP_KEM
    //         message = 'kèm'
    //         break
    //       default:
    //         break
    //     }

    //     const isLapKemCoSan = await checkLapKemCoSan(this.axios, {
    //       kieuld_id: this.kieuldId,
    //       kieu: kieuLapAPI1
    //     })
    //     // if (vkieu_lap == 2 || vkieu_lap == 4)  // hoangpkn : 24/03/2020. Thêm lắp trên đường của KH khác
    //     // {
    //     // }

    //     if (isLapKemCoSan) {
    //       // Kiểm tra xem mã truy nhập đã được cấp port hay chưa, chưa cấp thì không được chọn port.
    //       const lstPort = await getDanhSachPortTheoMaThueBao(this.axios, {
    //         ma_tb: this.formThongTinHoanCong.maTruyCap,
    //         kieu_id: kieuLapAPI2
    //       })
    //       if (!lstPort || lstPort.length <= 0 || !lstPort[0] || !lstPort[0].port_id) {
    //         this.$toast.error(`Account ${this.maThueBao.trim()} đang lắp kèm với thuê bao ${this.formThongTinHoanCong.maTruyCap.trim()}
    //             Bạn hãy thực hiện cấp port cho account ${this.formThongTinHoanCong.maTruyCap.trim()} trước`)
    //         return
    //       }
    //       newPortId = this.formThongTinHoanCong.portId || lstPort[0].port_id
    //     } else {
    //       newPortId = this.formThongTinHoanCong.portId
    //     }
    //   }
    //   let diaChiId = -1
    //   const loaiHD_ID = Number(this.loaiHopDongId)
    //   if (loaiHD_ID === LoaiHopDong.DAT_MOI || loaiHD_ID === LoaiHopDong.DI_CHUYEN || loaiHD_ID === LoaiHopDong.CHUYENDOI_LH) {
    //     diaChiId = await getDiaChiIDByHDTBID(this.axios, this.hdtbId)
    //   } else {
    //     diaChiId = await getDiaChiIDByThueBaoID(this.axios, this.thueBaoId)
    //   }

    //   this.$refs.modalChonPort.frmChonPort(
    // this.formThongTinHoanCong.thongTinDonVi,
    // this.formThongTinHoanCong.tramThietBiId,
    //     newPortId,
    //     kieuLapAPI1,
    //     11, //   dichvuvt_id,  Tạm thời để = 11 (giá trị defaut)
    //     this.loaitbId,
    //     diaChiId,
    //     '-1',
    //     this.hdtbId,
    //     this.thueBaoId
    //   )
    //   this.$nextTick(() => {
    //     this.$refs.modalChonPort.show()
    //   })
    // },

    capNhatPortTuDong(port) {
      console.log('capNhatPortTuDong')
      console.log(port)
      this.formThongTinHoanCong.port = port.port ? port.port.toString() : ''
      this.formThongTinHoanCong.VPI = port.vpi ? port.vpi.toString() : ''
      this.formThongTinHoanCong.VCI = port.vci ? port.vci.toString() : ''
      this.formThongTinHoanCong.slot = port.slot ? port.slot.toString() : ''
      this.formThongTinHoanCong.rack = port.rack ? port.rack.toString() : ''
      this.formThongTinHoanCong.shelf = port.shelf ? port.shelf.toString() : ''
      this.formThongTinHoanCong.system = port.system ? port.system.toString() : ''
      this.formThongTinHoanCong.portMdf = port.port_mdf ? port.port_mdf.toString() : ''
      this.formThongTinHoanCong.portId = port.port_id ? port.port_id : 0
      this.formThongTinHoanCong.vciVpiId = port.vci_vpi_id != null ? port.vci_vpi_id : 0
      this.formThongTinHoanCong.selectedBras = port.bras_id
      this.formThongTinHoanCong.selectedLAM = port.dslam_cha_id
      this.$emit('capNhatPort')
    },
    capNhatPortNhanCong(port) {
      console.log('capNhatPortNhanCong')
      console.log(port)
      this.formThongTinHoanCong.port = port.port ? port.port.toString() : ''
      this.formThongTinHoanCong.VPI = port.vpi ? port.vpi.toString() : ''
      this.formThongTinHoanCong.VCI = port.vci ? port.vci.toString() : ''
      this.formThongTinHoanCong.slot = port.slot ? port.slot.toString() : ''
      this.formThongTinHoanCong.rack = port.rack ? port.rack.toString() : ''
      this.formThongTinHoanCong.shelf = port.shelf ? port.shelf.toString() : ''
      this.formThongTinHoanCong.system = port.system ? port.system.toString() : ''
      this.formThongTinHoanCong.portMdf = port.port_mdf ? port.port_mdf.toString() : ''
      this.formThongTinHoanCong.portId = port.port_id ? port.port_id : 0
      this.formThongTinHoanCong.vciVpiId = port.vci_vpi_id != null ? port.vci_vpi_id : 0
      this.formThongTinHoanCong.selectedBras = port.bras_id_vdc
      this.formThongTinHoanCong.selectedLAM = port.dslam_id_vdc
      this.$emit('capNhatPort')
    },
    async btnChonPort_Click() {
      console.log('thueBaoIms')
      console.log(this.thueBaoIms)
      // try {
      // if (dtgDanhSach.Rows.Count <= 0)
      // {
      //     this.$toast.warning("Không có thuê bao để chọn Port");
      //     return;
      // }
      // CheckDataFacade obj = new CheckDataFacade();
      if (this.thueBaoIms.kieuld_id == null) {
        this.$toast.warning('Không tìm thấy thông tin!')
        return
      }

      var tt_port = ''
      var vport_id = 0
      var vkieu = 0

      if (this.thueBaoIms.matb_tn == '') {
        tt_port = TRANGTHAI_PORT.CHUA_SD
      } else {
        this.loading(true)
        var vkieu_lap = (
          await api.fn_tt_kieu_ld(this.axios, {
            type: 1,
            param: this.thueBaoIms.kieuld_id
          })
        ).data.data
        this.loading(false)
        vkieu_lap = 1
        if (vkieu_lap == 2) {
          vkieu = KIEULAP.LAP_LINE_SAN
          this.loading(true)
          var kiemTraLapKemCoSan = (
            await api.fn_kt_lap_kem_co_san(this.axios, {
              kieuld_id: this.thueBaoIms.kieuld_id,
              kieu: vkieu
            })
          ).data.data
          this.loading(false)
          if (kiemTraLapKemCoSan) {
            this.loading(true)
            var ds = (
              await api.sp_lay_ds_port_theo_ma_tb(this.axios, {
                ma_tb: this.thueBaoIms.matb_tn,
                kieu_id: vkieu
              })
            ).data.data
            this.loading(false)
            if (ds.length <= 0 || ds[0].port_id == null || ds[0].port_id == '') {
              this.$toast.warning(`Account ${this.thueBaoIms.ma_tb} đang lắp kèm với thuê bao ${this.thueBaoIms.matb_tn}\n Bạn hãy thực hiện cấp port cho account ${this.thueBaoIms.matb_tn} trước !`)
              return
            }

            if (this.thueBaoIms.port_id == 0) vport_id = ds[0].port_id
            else vport_id = this.thueBaoIms.port_id
          } else vport_id = this.thueBaoIms.port_id
        } else if (vkieu_lap == 1) {
          vkieu = KIEULAP.LAP_KEM
          this.loading(true)
          var kiemTraLapKemCoSan = (
            await api.fn_kt_lap_kem_co_san(this.axios, {
              kieuld_id: this.thueBaoIms.kieuld_id,
              kieu: vkieu
            })
          ).data.data
          this.loading(false)
          if (kiemTraLapKemCoSan) {
            this.loading(true)
            var ds = (
              await api.sp_lay_ds_port_theo_ma_tb(this.axios, {
                ma_tb: this.thueBaoIms.matb_tn,
                kieu_id: vkieu
              })
            ).data.data
            this.loading(false)
            if (ds.length <= 0 || ds[0].port_id == null || ds[0].port_id == '') {
              this.$toast.warning(`Account ${this.thueBaoIms.ma_tb} đang lắp kèm với thuê bao ${this.thueBaoIms.matb_tn}\n Bạn hãy thực hiện cấp port cho account ${this.thueBaoIms.matb_tn} trước !`)
              return
            }
            if (this.thueBaoIms.port_id == 0) vport_id = ds[0].port_id
            else vport_id = this.thueBaoIms.port_id
          } else vport_id = this.thueBaoIms.port_id
        }
      }

      // end hoang
      var vdonvi_id = this.formThongTinHoanCong.thongTinDonVi
      var vtramtb_id = this.formThongTinHoanCong.tramThietBiId
      var diachi_id = -1
      const loaiHD_ID = Number(this.loaiHopDongId)
      if (loaiHD_ID == LoaiHopDong.DAT_MOI || loaiHD_ID == LoaiHopDong.DI_CHUYEN || loaiHD_ID == LoaiHopDong.CHUYENDOI_LH) {
        this.loading(true)
        diachi_id = (
          await api.fn_tt_diachi_hdtb(this.axios, {
            type: 3,
            param: this.thueBaoIms.hdtb_id
          })
        ).data.data
        this.loading(false)
      } else {
        this.loading(true)
        diachi_id = (
          await api.fn_tt_diachi_hdtb(this.axios, {
            type: 6,
            param: this.thueBaoIms.thuebao_id
          })
        ).data.data
        this.loading(false)
      }
      console.log('990 - this.thueBaoIms')
      console.log(this.quytrinhId)
      this.$refs.modalChonPort.frmChonPort(
        vdonvi_id,
        vtramtb_id,
        vport_id,
        vkieu,
        11, //   dichvuvt_id,  Tạm thời để = 11 (giá trị defaut)
        this.thueBaoIms.loaitb_id,
        diachi_id,
        '-1',
        this.thueBaoIms.hdtb_id,
        this.thueBaoIms.thuebao_id,
        null,
        null,
        this.quytrinhId
      )
      this.$nextTick(() => {
        this.$refs.modalChonPort.show()
      })
      // //frmChonPort f = new frmChonPort(vdonvi_id, vtramtb_id, vport_id, vkieu, DichVuVienThong.IMS, Convert.ToInt32(dsIn.Tables[0].Rows[nIndex]["loaitb_id"].ToString().Trim()), diachi_id, "-1", hdtb_id, thuebao_id);
      // frmChonPort f = new frmChonPort(vdonvi_id, vtramtb_id, vport_id, vkieu, dichvuvt_id, Convert.ToInt32(dsIn.Tables[0].Rows[nIndex]["loaitb_id"].ToString().Trim()), diachi_id, "-1", hdtb_id, thuebao_id);

      // if (PHAILAM(luong_id, "AON_CHI_CHON_DSLAM_TINH"))
      // {//Chỉ chọn dslam tỉnh, ko chọn port đối với thuê bao AON
      //     f.ChiCanChonDSLAM = true;
      // }
      // if (dsIn.Tables[0].Columns.Contains("DSLAM_TINH_ID"))
      // {
      //     f.view_dslam_tinh_id = Convert.ToInt64(gvDanhSach.GetRowCellValue(gvDanhSach.FocusedRowHandle, "DSLAM_TINH_ID"));
      // }

      // f.ShowDialog();
      // if (f.chapnhan)
      // {
      //     if (f.ChiCanChonDSLAM && f.IsAONTinh)
      //     {//Chỉ cập nhật lại dslam tỉnh
      //         var sql = "update {?DB2}.hdtb_ims a set a.dslam_tinh_id={?dslam_tinh_id},port_id=null where hdtb_id={?hdtb_id}";
      //         sql = sql.ReplaceSchema()
      //             .Replace("{?dslam_tinh_id}", f.dslam_tinh_id + "")
      //             .Replace("{?hdtb_id}", hdtb_id + "");
      //         bangts.ExeSQL(sql);
      //     }
      //     else
      //     {
      //         txtPort.Text = f.port.ToString();
      //         txtVPI.Text = f.vpi.ToString();
      //         txtVCI.Text = f.vci.ToString();
      //         txtAdsl_Port.Text = f.vadsl_port.ToString();
      //         txtSlot.Text = f.slot.ToString();
      //         txtRack.Text = f.rack.ToString();
      //         txtShelf.Text = f.shelf.ToString();
      //         txtSystem.Text = f.system.ToString();
      //         txtPortMdf.Text = f.port_mdf.ToString();
      //         txtShelf.Text = f.shelf.ToString();
      //         tramtb_id = f.tramtb_id;
      //         txtTramTB.Text = checkdata.MAP_ID("ten_dv", DatabaseConstants.DB1 + ".donvi", "where donvi_id = " + tramtb_id);
      //         port_id = f.port_id;
      //         vci_vpi_id = f.vci_vpi_id;
      //         cboBras.SelectedValue = f.bras_id_vdc;
      //         cboDSLAM.SelectedValue = f.dslam_id_vdc;
      //         //congnghe_id = f.congnghe_id;
      //         txtPort.Focus();
      //         CapNhat_port();
      //         HienThiDanhSachHDTB();
      //         if (inDex_load != 0)
      //         {
      //             /*if (dtgDanhSach.Rows.Count == 1)
      //                 inDex_load = 0;*/
      //             dtgDanhSach.CurrentCell = dtgDanhSach.Rows[inDex_load].Cells[0];
      //             dtgDanhSach.CurrentRow.Selected = false;
      //             dtgDanhSach.Rows[inDex_load].Selected = true;
      //         }
      // } catch (e) {
      //   this.$toast.warning(`${e}`)
      // }
    },
    async btnChonPort_TuDong_Click() {
      console.log('this.thueBaoIms')
      console.log(this.thueBaoIms)
      // try {
      // if (dtgDanhSach.Rows.Count <= 0)
      // {
      //     Message_Box.ShowWarning("Không có thuê bao để chọn Port");
      //     return;
      // }
      if (!this.thueBaoIms.kieuld_id) {
        this.$toast.error('Không tìm thấy thông tin!')
        return
      }

      var kieuld_id = 0 //1<->Lap kem; 2<->Lap tren duong co san
      var tt_port = 0
      var vport_id = 0
      var vvci_vpi_id = 0
      var vkieu = 0

      var vthamso = '-1'
      // var nIndex = LayIndexTheoPhieuID(CURRENT_PHIEU_ID, dsIn);
      // var vloaitbid = Convert.ToInt32(dsIn.Tables[0].Rows[nIndex]["loaitb_id"].ToString().Trim());

      if (this.thueBaoIms.matb_tn == '') {
        tt_port = TRANGTHAI_PORT.CHUA_SD
        vthamso = '4,7,9'
      } else {
        var vkieu_lap = (
          await api.fn_tt_kieu_ld(this.axios, {
            type: 1,
            param: this.thueBaoIms.kieuld_id
          })
        ).data.data
        if (vkieu_lap == 2 || vkieu_lap == 4) {
          // hoangpkn : 24/03/2020. Thêm lắp trên đường của KH khác
          vkieu = KIEULAP.LAP_LINE_SAN
          var kiemTraLapKemCoSan = (
            await api.fn_kt_lap_kem_co_san(this.axios, {
              kieuld_id: this.thueBaoIms.kieuld_id,
              kieu: vkieu_lap
            })
          ).data.data
          if (kiemTraLapKemCoSan) {
            var ds = (
              await api.sp_lay_ds_port_theo_ma_tb(this.axios, {
                ma_tb: this.thueBaoIms.matb_tn,
                kieu_id: vkieu
              })
            ).data.data
            if (ds.length <= 0 || ds[0].port_id == null || ds[0].port_id == '') {
              this.$toast.warning(`Account ${this.thueBaoIms.ma_tb} đang lắp kèm với thuê bao ${this.thueBaoIms.matb_tn}\n Bạn hãy thực hiện cấp port cho account ${this.thueBaoIms.matb_tn} trước !`)
              return
            }

            vport_id = ds[0].port_id?ds[0].port_id:0

            // if (this.thueBaoIms.port_id == 0) vport_id = ds[0].port_id
            // else vport_id = this.thueBaoIms.port_id

            if (this.$root.token.getMaTinh() == 'HNI') {
              let _dt = (await api.lay_ds_port_matb_tn(this.axios, {
                hdtb_id: this.thueBaoIms.hdtb_id,
                matb_tn: this.thueBaoIms.matb_tn
              })).data.data
              if (_dt != null && _dt.length > 0) {
                vport_id = _dt[0].port_id
                vvci_vpi_id = _dt[0].vci_vpi_id
                if (vport_id == 0) {
                  this.$toast.warning(`Chưa có thông tin port cho thuê bao có sẵn: ${this.thueBaoIms.matb_tn}`)
                  return
                }
              }
            }

            tt_port = 0
          } else {
            vport_id = 0
            tt_port = TRANGTHAI_PORT.CHUA_SD
          }
        } else if (vkieu_lap == 1) {
          vkieu = KIEULAP.LAP_KEM
          var kiemTraLapKemCoSan = (
            await api.fn_kt_lap_kem_co_san(this.axios, {
              kieuld_id: this.thueBaoIms.kieuld_id,
              kieu: KIEULAP.LAP_KEM
            })
          ).data.data
          if (kiemTraLapKemCoSan) {
            var ds = (
              await api.sp_lay_ds_port_theo_ma_tb(this.axios, {
                ma_tb: this.thueBaoIms.matb_tn,
                kieu_id: vkieu
              })
            ).data.data
            if (ds.length <= 0 || ds[0].port_id == null || ds[0].port_id == '') {
              this.$toast.warning(`Account ${this.thueBaoIms.ma_tb} đang lắp kèm với thuê bao ${this.thueBaoIms.matb_tn}\n Bạn hãy thực hiện cấp port cho account ${this.thueBaoIms.matb_tn} trước !`)
              return
            }
            vport_id = ds[0].port_id?ds[0].port_id:0
            // if (!this.thueBaoIms.port_id||this.thueBaoIms.port_id == 0) vport_id = ds[0].port_id
            // else vport_id = this.thueBaoIms.port_id

            if (this.$root.token.getMaTinh() == 'HNI') {
              let _dt = (await api.lay_ds_port_matb_tn(this.axios, {
                hdtb_id: this.thueBaoIms.hdtb_id,
                matb_tn: this.thueBaoIms.matb_tn
              })).data.data
              if (_dt != null && _dt.length > 0) {
                vport_id = _dt[0].port_id
                vvci_vpi_id = _dt[0].vci_vpi_id

                if (vport_id == 0) {
                  this.$toast.error(`Chưa có thông tin port cho thuê bao kèm: " + ${this.thueBaoIms.matb_tn}`)
                  return
                }
              }
            }
            tt_port = 0
          } else {
            vport_id = 0
            tt_port = TRANGTHAI_PORT.CHUA_SD
          }
        }
      }

      var vdonvi_id = this.formThongTinHoanCong.thongTinDonVi
      var vtramtb_id = this.formThongTinHoanCong.tramThietBiId

      var diachi_id = -1

      const loaiHD_ID = Number(this.loaiHopDongId)
      if (loaiHD_ID === LoaiHopDong.DAT_MOI || loaiHD_ID === LoaiHopDong.DI_CHUYEN) {
        diachi_id = (
          await api.fn_tt_diachi_hdtb(this.axios, {
            type: 3,
            param: this.thueBaoIms.hdtb_id
          })
        ).data.data
        console.log('diachi_id')
        console.log(diachi_id)
      } else {
        diachi_id = (
          await api.fn_tt_diachi_hdtb(this.axios, {
            type: 6,
            param: this.thueBaoIms.thuebao_id
          })
        ).data.data
      }
      var phuongid = 0
      var phoid = 0
      var apid = 0
      var khuid = 0

      if (diachi_id != -1 && diachi_id != 0) {
        var ds_diachi = (
          await api.sp_tt_diachi(this.axios, {
            p_type: 1,
            p_param: diachi_id
          })
        ).data.data

        if (ds_diachi != null && ds_diachi.length > 0) {
          phuongid = ds_diachi[0].phuong_id
          if (ds_diachi[0].pho_id != '' && ds_diachi[0].pho_id != null) phoid = ds_diachi[0].pho_id
          if (ds_diachi[0].ap_id != '' && ds_diachi[0].ap_id != null) apid = ds_diachi[0].ap_id
          if (ds_diachi[0].khu_id != '' && ds_diachi[0].khu_id != null) khuid = ds_diachi[0].khu_id
        }
      } else {
        this.$toast.error('Không có thông tin về địa chỉ lắp đặt để chọn port. Bạn hãy chọn nhân công hoặc liên hệ admin để xử lý !')
        return
      }

      var ds_dslam = []
      if (this.thueBaoIms.matb_tn == '') {
        ds_dslam = (
          await api.sp_lay_ds_dslam_tt(this.axios, {
            donvi_id: vdonvi_id,
            loaidv_id: LOAI_DV.TRAM_PORT,
            phuong_id: phuongid,
            pho_id: phoid,
            ap_id: apid,
            khu_id: khuid,
            loaitb_id: this.thueBaoIms.loaitb_id,
            thamso: vthamso,
            congnghe_id: 0
          })
        ).data.data
      } else {
        ds_dslam = (
          await api.lay_dslam_theo_port_id(this.axios, {
            portId: vport_id
          })
        ).data.data
      }
      if (ds_dslam != null && ds_dslam.length > 0) {
        var k_tra = false
        for (var i = 0; i < ds_dslam.length; i++) {
          if (ds_dslam[i].dslam_id != '') {
            // tramtb_id = 0
            var dslam_id = 0
            var ip_dslam_tinh = ''
            // tramtb_id = ds_dslam[i].donvi_id
            dslam_id = ds_dslam[i].dslam_id
            var dsport = []
            if (this.$root.token.getMaTinh() != 'HNI') {
              dsport = (
                await api.lay_ds_port_tudong(this.axios, {
                  dslam_id: dslam_id,
                  port_id: vport_id,
                  trangthai: tt_port,
                  loaitb_id: this.thueBaoIms.loaitb_id
                })
              ).data.data
            } else {
              dsport = (
                await api.lay_ds_port_tudong_hni(this.axios, {
                  dslam_id: dslam_id,
                  port_id: vport_id,
                  vci_vpi_id: vvci_vpi_id
                })
              ).data.data
            }

            if (dsport != null && dsport.length > 0) {
              this.capNhatPortTuDong(dsport[0])
              k_tra = true
              break
            }
          }
        }
        if (k_tra == false) {
          this.$toast.warning('Không tìm thấy thông tin port !')
          return
        }
      } else {
        this.$toast.warning('Không tìm thấy thông tin port !')
        return
      }
      // } catch (e) {
      //   this.$toast.error(`${e}`)
      // }
    },
    async updateNoiDungTHClick() {
      this.loading(true)
      await updateNoiDungTH(this.axios, {
        phieu_id: this.phieuId,
        noidung_th: this.formThongTinHoanCong.noiDungTH.trim()
      })
        .then(() => {
          this.$toast.success('Cập nhật thành công!')
        })
        .finally(() => {
          this.loading(false)
        })
        .catch(() => {
          this.$toast.error('Cập nhật không thành công. Vui lòng kiểm tra lại!')
        })
    },

    brasChange() {
      const keep = this.formThongTinHoanCong.selectedLAM
      this.formThongTinHoanCong.selectedLAM = null
      this.$nextTick(() => {
        if (this.formThongTinHoanCong.selectedBras) {
          fetchDanhSachLAM(this.axios, {
            bras_id: this.formThongTinHoanCong.selectedBras,
            kieu: 1 // chưa rõ
          })
            .catch((err) => {
              this.$toast.error(err.data.message_detail)
            })
            .then((response) => {
              this.danhSachLAM = response
              this.formThongTinHoanCong.selectedLAM = keep
            })
        }
      })
    },

    checkboxLayThongSoKyThuatChange() {},
    focusToIPKhachHang() {
      this.$nextTick(() => {
        this.$refs.ipKhachHang.focus()
      })
    }
  }
}
</script>

<style scoped>
.raw-html {
  height: 126px;
  overflow-y: auto;
  padding: 10px;
  border: 1px solid gray;
  margin-bottom: 3px;
}

.border-btn-port {
  border: 1px solid #0176ff;
  color: #0176ff;
}
</style>
