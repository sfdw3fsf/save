<template>
  <div class="modal-content popup-box">
    <div class="list-actions-top">
      <ul class="list">
        <li>
          <a v-on:click="btnTaoMoi_OnClick()" style="cursor: pointer" v-bind:class="{ disabled: !setButom.st_nhapmoi }" :disable="setButom.st_nhapmoi"><span class="icon ui-1_circle-add nc-icon-glyph"></span>Tạo mới </a>
        </li>
        <li>
          <a v-on:click="btnCapNhat_OnClick()" style="cursor: pointer" v-bind:class="{ disabled: !setButom.st_ghilai }" :disable="setButom.st_ghilai"><span class="icon one-reload1"></span>Cập nhật</a>
        </li>
        <li>
          <a v-on:click="btnHuy_OnClick()" style="cursor: pointer" v-bind:class="{ disabled: !setButom.st_huy }" :disable="setButom.st_huy"><span class="icon ui-1_circle-remove nc-icon-glyph"></span>Huỷ thao tác</a>
        </li>
        <li>
          <a v-on:click="tbtnXoaTTLH_OnClick()" style="cursor: pointer" v-bind:class="{ disabled: !setButom.st_xoa }" :disable="setButom.st_xoa"><span class="icon one-trash"></span>Xoá </a>
        </li>
      </ul>
    </div>
    <div class="popup-body">
      <div class="row">
        <div class="col-sm-6 col-12">
          <div class="box-form">
            <div class="row">
              <div class="col-sm-12 col-12">
                <div class="info-row">
                  <div class="key w100">Tên liên hệ</div>
                  <div class="value">
                    <input type="text" v-model="txtTenLH" ref="txtTenLH" class="form-control" />
                  </div>
                </div>
              </div>
            </div>
            <div class="row">
              <div class="col-sm-12 col-12">
                <div class="info-row">
                  <div class="key w100">Địa chỉ</div>
                  <div class="value">
                    <div class="input-more-button">
                      <button class="btn">
                        <span class="-ap icon-more_horiz" v-b-modal.popupDiaChiLH></span>
                      </button>
                      <ModalChonDiaChiLH modalId="popupDiaChiLH" :data="diaChiLH" @xacnhan="getDiaChiLH" />
                      <input type="text" v-model="txtDiaChiLH" ref="txtDiaChiLH" class="form-control" :disabled="true" />
                    </div>
                  </div>
                </div>
              </div>
            </div>
            <div class="row">
              <div class="col-sm-6 col-12">
                <div class="info-row">
                  <div class="key w100">Điện thoại</div>
                  <div class="value">
                    <input type="number" v-model="txtDienThoai" ref="txtDienThoai" class="form-control" title="Số điện thoại không được ít hơn 10 số và phải bắt đầu bằng 0" />
                  </div>
                </div>
              </div>
              <div class="col-sm-6 col-12">
                <div class="info-row">
                  <div class="key w100">Fax</div>
                  <div class="value">
                    <div class="input-icon-right">
                      <input type="number" v-model="txtFax" ref="txtFax" pattern="^0\d{9,}$" class="form-control" />
                    </div>
                  </div>
                </div>
              </div>
            </div>
            <div class="row">
              <div class="col-sm-6 col-12">
                <div class="info-row">
                  <div class="key w100">Email</div>
                  <div class="value">
                    <input type="text" v-model="txtEmail" ref="txtEmail" class="form-control" />
                  </div>
                </div>
              </div>
              <div class="col-sm-6 col-12">
                <div class="info-row">
                  <div class="key w100">Đơn vị</div>
                  <div class="value">
                    <div class="input-icon-right">
                      <input type="text" v-model="txtDonVi" class="form-control" />
                    </div>
                  </div>
                </div>
              </div>
            </div>
            <div class="row">
              <div v-show="isZalo" class="col-sm-6 col-12">
                <div class="info-row marb0">
                  <div class="key w100">
                    <div class="check-action">
                      <input type="checkbox" class="check" v-model="chkZalo" />
                      <span class="name">Zalo</span>
                    </div>
                  </div>
                  <div class="value">
                    <input type="text" v-model="txtZalo" class="form-control" />
                  </div>
                </div>
              </div>
              <div class="col-sm-6 col-12">
                <div class="info-row marb0">
                  <div class="key w100">
                    <div class="check-action">
                      <input type="checkbox" class="check" v-model="chkDaQT" />
                      <span class="name">Đã quan tâm</span>
                    </div>
                  </div>
                </div>
              </div>
            </div>
            <div class="row"></div>
          </div>
          <div class="box-move-select-table mart20">
            <div class="box-col">
              <div class="legend-title">Mục đích chưa gán</div>
              <DataGrid v-bind:columns="DsMucDichCG.headers" v-bind:dataSource="DsMucDichCG.data" :showColumnCheckbox="true" :enabledSelectFirstRow="false" :allowPaging="false" dataKeyField="mucdich_id" :showFilter="true" @selectedItemsChanged="select_selectedItemsChangedCG" panelDataHeight="200">
              </DataGrid>
            </div>
            <div class="actions">
              <button class="btn" @click="MoveRight">
                <span class="fa fa-angle-right"></span>
              </button>
              <button class="btn" @click="MoveRightAll">
                <span class="fa fa-angle-double-right"></span>
              </button>
              <button class="btn" @click="MoveLeft">
                <span class="fa fa-angle-left"></span>
              </button>
              <button class="btn" @click="MoveLeftAll">
                <span class="fa fa-angle-double-left"></span>
              </button>
            </div>
            <div class="box-col">
              <div class="legend-title">Mục đích đã gán</div>
              <DataGrid v-bind:columns="DsMucDichDG.headers" v-bind:dataSource="DsMucDichDG.data" :showColumnCheckbox="true" :enabledSelectFirstRow="false" :allowPaging="false" dataKeyField="mucdich_id" :showFilter="true" @selectedItemsChanged="select_selectedItemsChangedDG" panelDataHeight="200">
              </DataGrid>
            </div>
          </div>
          <div class="box-form">
            <div class="legend-title">Thông tin liên hệ</div>
            <DataGrid v-bind:columns="DsLienHe.headers" v-bind:dataSource="DsLienHe.data" :showColumnCheckbox="false" :enabledSelectFirstRow="true" :allowPaging="true"  :enable-paging-server="false" :showFilter="false" @rowSelected="grid_DsLienHeSelected"> </DataGrid>
          </div>
        </div>
        <div class="col-sm-6 col-12">
          <div class="box-form">
            <div class="legend-title">Thông tin liên hệ khách hàng</div>
            <DataGrid v-bind:columns="DsLienHeKH.headers" v-bind:dataSource="DsLienHeKH.data" :showColumnCheckbox="false" :enabledSelectFirstRow="false" :allowPaging="false"  :showFilter="false"> </DataGrid>
            <div class="legend-title mart20">Thông tin liên hệ thanh toán</div>
            <DataGrid v-bind:columns="DsLienHeTT.headers" v-bind:dataSource="DsLienHeTT.data" :showColumnCheckbox="false" :enabledSelectFirstRow="false" :allowPaging="true" :enable-paging-server="false"  :showFilter="false"> </DataGrid>
          </div>
          <div class="box-form">
            <div class="legend-title">Thông tin liên hệ thuê bao</div>
            <DataGrid v-bind:columns="DsLienHeTB.headers" v-bind:dataSource="DsLienHeTB.data" :showColumnCheckbox="false" :enabledSelectFirstRow="false" :allowPaging="true" :enable-paging-server="false"  :showFilter="true"> </DataGrid>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
import gridView from '@/modules/prepaid/ChoosePreapaidNumber/components/gridview.vue'
import treegridView from '@/modules/contract/setup/ManageSelfcareContract/components/treegridview.vue'
import breadcrumb from '@/components/breadcrumb'
import moment from 'moment'
import DatePicker from 'vue2-datepicker'
import 'vue2-datepicker/locale/vi.js'
import 'vue2-datepicker/index.css'
import ModalChonDiaChiLH from '@/modules/contract/setup/InstallNewSubs/Popups/popupChonDiaChi'
import api from '../Api'
import { drawSymbols } from '@syncfusion/ej2-maps'
export default {
  name: 'ThongTinLienHe',
  props: {
    hdkh_id: {
      type: Number,
      default: 0
    },
    //Dịch vụ
    khachhang_id: {
      type: Number,
      default: 0
    },
    ten_kh: {
      type: String,
      default: ''
    },
    diachi: {
      type: String,
      default: ''
    }
  },
  components: {
    breadcrumb,
    gridView,
    treegridView,
    moment,
    DatePicker,
    ModalChonDiaChiLH
  },
  data() {
    return {
      filterOptions: {
        enableCaseSensitivity: false,
        mode: 'Immediate',
        ignoreAccent: true
      },
      hdemail_id: 0,
      email_id: 0,
      txtDiaChiLH: '',
      isZalo : false,
      txtTenLH: '',
      txtDienThoai: '',
      txtFax: '',
      txtEmail: '',
      txtDonVi: '',
      lienHeSelected: {
        ttlh_id: 0,
        hdlh_id: 0,
        kieu_yc: 0
      },

      chkZalo: false,
      txtZalo: '',
      chkDaQT: false,
      isZalo: 0,
      isMucDich: 0,

      hdlh_id: 0,
      hd_id: 0,
      lh_id: 0,
      daucuoi_id: 0,
      ttkh_id: 0,
      loaimxh_id: 0,

      dsLienHe: [],
      dsThanhToan: [],
      dsKhachHang: [],
      dsMucDich: [],
      dsMXH: [],

      setButom: {
        st_nhapmoi: false,
        st_ghilai: true,
        st_huy: false,
        st_xoa: false,
        p_action: 0
      },

      diaChiLH: {
        DIACHI: null,
        TINH_ID: null,
        QUAN_ID: null,
        PHUONG_ID: null,
        PHO_ID: null,
        AP_ID: null,
        KHU_ID: null,
        DAC_DIEM_ID: null,
        SO_NHA: null,
        Lng: 0,
        Lat: 0,
        BLOCK: null,
        TANG: null,
        PHONG: null,
        MOTA: null
      },

      DsMucDichCG: {
        headers: [
          {
            fieldName: 'mucdich_sd',
            headerText: 'Mục đích',
            allowFiltering: true,
            width: 150
          }
        ],
        data: [],
        selected: 0,
        checked: []
      },

      DsMucDichDG: {
        headers: [
          {
            fieldName: 'mucdich_sd',
            headerText: 'Mục đích',
            allowFiltering: true,
            width: 150
          }
        ],
        data: [],
        selected: 0,
        checked: []
      },

      DsEmail: {
        headers: [
          {
            fieldName: 'stt',
            headerText: 'STT',
            allowFiltering: true,
            textAlign: 'Center',
            allowSorting: false,
            width: 50
          },
          {
            fieldName: 'email',
            headerText: 'Email',
            allowFiltering: true,
            allowSorting: false,
            width: 100
          }
        ],
        data: [],
        selected: 0,
        checked: []
      },

      DsLienHeKH: {
        headers: [
          {
            fieldName: 'stt',
            headerText: 'STT',
            allowFiltering: true,
            textAlign: 'Center',
            allowSorting: false,
            width: 60
          },
          {
            fieldName: 'ma_kh',
            headerText: 'Mã KH',
            allowFiltering: true,
            allowSorting: false,
            width: 100
          },
          {
            fieldName: 'ten_kh',
            headerText: 'Tên KH',
            allowFiltering: true,
            allowSorting: false,
            width: 150
          },
          {
            fieldName: 'ttsd',
            headerText: 'Trạng thái',
            allowFiltering: true,
            allowSorting: false,
            width: 100
          },
          {
            fieldName: 'dangky',
            headerText: 'Đăng ký',
            textAlign: 'Center',
            template: this.getCheckboxTemplate(this, 'dangky', 'huy', 'stt', 'DsLienHeKH'),
            allowFiltering: true,
            allowSorting: false,
            width: 100
          },
          {
            fieldName: 'huy',
            headerText: 'hủy',
            textAlign: 'Center',
            template: this.getCheckboxTemplate(this, 'huy', 'dangky', 'stt', 'DsLienHeKH'),
            allowFiltering: true,
            allowSorting: false,
            width: 100
          }
        ],
        data: [],
        selected: 0,
        checked: []
      },

      DsLienHeTT: {
        headers: [
          {
            fieldName: 'stt',
            headerText: 'STT',
            allowFiltering: true,
            textAlign: 'Center',
            allowSorting: false,
            width: 60
          },
          {
            fieldName: 'ma_tt',
            headerText: 'Mã TT',
            allowFiltering: true,
            allowSorting: false,
            width: 100
          },
          {
            fieldName: 'ten_tt',
            headerText: 'Tên TT',
            allowFiltering: true,
            allowSorting: false,
            width: 150
          },
          {
            fieldName: 'ttsd',
            headerText: 'Trạng thái',
            allowFiltering: true,
            allowSorting: false,
            width: 100
          },
          {
            fieldName: 'dangky',
            headerText: 'Đăng ký',
            textAlign: 'Center',
            template: this.getCheckboxTemplate(this, 'dangky', 'huy', 'stt', 'DsLienHeTT'),
            allowFiltering: true,
            allowSorting: false,
            width: 100
          },
          {
            fieldName: 'huy',
            headerText: 'hủy',
            textAlign: 'Center',
            template: this.getCheckboxTemplate(this, 'huy', 'dangky', 'stt', 'DsLienHeTT'),
            allowFiltering: true,
            allowSorting: false,
            width: 100
          }
        ],
        data: [],
        selected: 0,
        checked: []
      },
      vkhachhang_id: 0,
      vhdkh_id: 0,
      vMucDich: 1,

      DsLienHeTB: {
        headers: [
          {
            fieldName: 'stt',
            headerText: 'STT',
            allowFiltering: true,
            textAlign: 'Center',
            allowSorting: false,
            width: 60
          },
          {
            fieldName: 'ma_tt',
            headerText: 'Mã TT',
            allowFiltering: true,
            allowSorting: false,
            width: 100
          },
          {
            fieldName: 'ma_tb',
            headerText: 'Mã TB',
            allowFiltering: true,
            allowSorting: false,
            width: 100
          },
          {
            fieldName: 'ten_tb',
            headerText: 'Tên TB',
            allowFiltering: true,
            allowSorting: false,
            width: 150
          },
          {
            fieldName: 'ttsd',
            headerText: 'Trạng thái',
            allowFiltering: true,
            allowSorting: false,
            width: 100
          },
          {
            fieldName: 'dangky',
            headerText: 'Đăng ký',
            textAlign: 'Center',
            template: this.getCheckboxTemplate(this, 'dangky', 'huy', 'stt', 'DsLienHeTB'),
            allowFiltering: true,
            allowSorting: false,
            width: 100
          },
          {
            fieldName: 'huy',
            headerText: 'hủy',
            textAlign: 'Center',
            template: this.getCheckboxTemplate(this, 'huy', 'dangky', 'stt', 'DsLienHeTB'),
            allowFiltering: true,
            allowSorting: false,
            width: 100
          },
          {
            fieldName: 'diem_dau',
            headerText: 'Điểm đầu',
            textAlign: 'Center',
            template: '<div class="template_checkbox"> ${if(diem_dau == 1)} <input type="checkbox" checked> ${else} <input type="checkbox"> ${/if} </div>',
            allowFiltering: true,
            allowSorting: false,
            width: 100
          },
          {
            fieldName: 'diem_cuoi',
            headerText: 'Điểm cuối',
            textAlign: 'Center',
            template: '<div class="template_checkbox"> ${if(diem_cuoi == 1)} <input type="checkbox" checked> ${else} <input type="checkbox"> ${/if} </div>',
            allowFiltering: true,
            allowSorting: false,
            width: 100
          }
        ],
        data: [],
        selected: 0,
        checked: []
      },

      DsLienHe: {
        headers: [
          {
            fieldName: 'stt',
            headerText: 'STT',
            allowFiltering: true,
            textAlign: 'Center',
            allowSorting: false,
            width: 60
          },
          {
            fieldName: 'ten_lh',
            headerText: 'Tên liên hệ',
            allowFiltering: true,
            allowSorting: false,
            width: 100
          },
          {
            fieldName: 'diachi',
            headerText: 'Địa chỉ',
            allowFiltering: true,
            allowSorting: false,
            width: 200
          },
          {
            fieldName: 'dienthoai',
            headerText: 'Điện thoại',
            allowFiltering: true,
            allowSorting: false,
            width: 120
          },
          {
            fieldName: 'fax',
            headerText: 'Fax',
            allowFiltering: true,
            allowSorting: false,
            width: 100
          },
          {
            fieldName: 'email',
            headerText: 'Email',
            allowFiltering: true,
            allowSorting: false,
            width: 100
          }
        ],
        data: [],
        selected: 0,
        checked: [],
        ttlh_id: 0,
        hdlh_id: 0,
        kieu_yc: 0,
        rlh_id: 0
      }
    }
  },
  watch: {
    chkDaQT(value) {
      if (value) this.vMucDich = 12
      else this.vMucDich = 1
    },
    khachhang_id() {
      this.vkhachhang_id = this.khachhang_id
    },
    hdkh_id() {
      this.vhdkh_id = this.hdkh_id
    },
    vkhachhang_id(newValue) {
      if (newValue) {
        this.onModalShow()
      }
    },
    vhdkh_id(newValue) {
      if (newValue) {
        this.onModalShow()
      }
    }
  },
  created() {},
  computed: {},
  async mounted() {
    this.Loading = true
    this.vhdkh_id = this.hdkh_id
    this.vkhachhang_id = this.khachhang_id
    let rsKho = await this.$root.context.post("web-quantri/diaban-nvkt/sp_lay_ds_thamso_md_mats", {
                    ma_ts: 'CAPNHAT_LH_ZALO'
                })
                if(rsKho.data && rsKho.data.length > 0) {
                    this.isZalo = true;
                }
  },
  methods: {
    showPopup({ hdkh_id, khachhang_id } = {}) {
      this.vhdkh_id = hdkh_id
      this.vkhachhang_id = khachhang_id
    },
    getCheckboxTemplate(parent, field, field2, uniq_field, grid) {
      return function () {
        return {
          template: {
            template: `<div class="template_checkbox"> <input type="checkbox" @change="change" :checked="data[field] == 1" :id="uniq_field_computed" > </div>`,
            data() {
              return {
                field: field,
                field2: field2,
                uniq_field: uniq_field
              }
            },
            computed: {
              uniq_field_computed() {
                return grid + '_' + this.data[uniq_field] + `_` + field.toUpperCase()
              },
              uniq_field2_computed() {
                return grid + '_' + this.data[uniq_field] + `_` + field2.toUpperCase()
              }
            },
            methods: {
              change(event) {
                console.log(field, this.data)
                if (field == 'huy') {
                  if (!this.data.sudung) {
                    parent[grid]['data'][this.data.index]['huy'] = 0
                    document.getElementById(this.uniq_field_computed).checked = false
                    return
                  }
                }
                parent[grid]['data'][this.data.index][field] = event.target.checked ? '1' : '0'

                parent[grid]['data'][this.data.index][field2] = event.target.checked ? '0' : '1'
              }
            }
          }
        }
      }
    },
    onModalShow() {
      // this.SetButton(1);
      Promise.all([this.getThongTinLienHe(), 
      // this.getThongTinMD_CG(),
      //  this.getThongTinMD_DG()
       this.getThongTinMD()
      ])
    },
    SetButton(kieu) {
      if (kieu == -1) {
        // bat dau
        this.setButom.st_ghilai = true
        this.setButom.st_huy = true
        this.setButom.st_lienhe = true
        this.setButom.st_email = true
      }
      if (kieu == 0) {
        // bat dau
        this.setButom.st_nhapmoi = true
        this.setButom.st_ghilai = true
        this.setButom.st_huy = true
        this.setButom.st_xoa = true
        this.setButom.p_action = 1
      }
      if (kieu == 1) {
        // nhap moi
        this.setButom.st_nhapmoi = false
        this.setButom.st_ghilai = true
        this.setButom.st_huy = true
        this.setButom.st_xoa = true
        this.setButom.p_action = 0
        this.Clean()
      }
      if (kieu == 3) {
        // edit lai
        this.setButom.st_nhapmoi = true
        this.setButom.st_xoa = false
        this.setButom.st_ghilai = false
        this.setButom.st_huy = true
        this.setButom.p_action = 1
      }
    },

    getDiaChi_LH: function (data, datatype) {
      this[datatype].DIACHI = data.diachimoi
      this[datatype].TINH_ID = data.tinh_id
      this[datatype].QUAN_ID = data.quan_id
      this[datatype].PHUONG_ID = data.phuong_id
      this[datatype].PHO_ID = data.pho_id
      this[datatype].AP_ID = data.ap_id
      this[datatype].KHU_ID = data.khu_id
      this[datatype].DAC_DIEM_ID = data.dacdiem_id
      this[datatype].SO_NHA = data.so_nha
    },

    getDiaChiLH: async function (data) {
      this.getDiaChi_LH(data, 'diaChiLH')
      this.txtDiaChiLH = this.diaChiLH.DIACHI
    },

    getThongTinLienHe: async function () {
      console.log('vao day: ', this.vhdkh_id + '' + this.vkhachhang_id)
      this.loading(true)
      try {
        var pdata = {
          hdkh_id: this.vhdkh_id,
          khachhang_id: this.vkhachhang_id
        }
        const response = await api.getThongTinLienHe(this.axios, pdata)
        console.log(response)
        if (response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000') {
          console.log(response.data.data, 'Vào đây')
          if (response.data.data && response.data.data.length > 0) {
            this.DsLienHe.data = response.data.data
            this.DsLienHe.data.map((item, key) => (item.stt = key + 1))
            this.SetButton(0) //Neu co da ta la cap nhat
          } else {
            await this.getThongTinKH()
            await this.getThongTinTT()
            await this.getThongTinTB()
            this.SetButton(1) // Neu ko co data thi nhap moi
          }
        } else {
          this.txtTenLH = this.ten_kh ? this.ten_kh : ''
          this.txtDiaChiLH = this.diachi ? this.diachi : ''
          await this.getThongTinKH()
          await this.getThongTinTT()
          await this.getThongTinTB()
          this.SetButton(1) // Neu ko co data thi nhap moi
        }

        this.loading(false)
      } catch (ex) {
        this.loading(false)
        this.$toast.error('' + ex)
      }
    },

    grid_DsLienHeSelected: async function (args) {
      this.hdlh_id = Number(args.data.hdlh_id) //todo
      this.ttlh_id = Number(args.data.ttlh_id)
      this.lienHeSelected = args.data

      this.DsLienHe.hdlh_id = args.data.hdlh_id != null ? args.data.hdlh_id : 0
      this.DsLienHe.ttlh_id = args.data.ttlh_id != null ? args.data.ttlh_id : 0
      this.DsLienHe.kieu_yc = args.data.kieu_yc != null ? args.data.kieu_yc : 0
      this.txtTenLH = args.data.ten_lh != null ? args.data.ten_lh : ''
      this.txtDiaChiLH = args.data.diachi != null ? args.data.diachi : ''

      this.txtDienThoai = args.data.dienthoai != null ? args.data.dienthoai : ''
      this.txtFax = args.data.fax != null ? args.data.fax : ''
      this.txtEmail = args.data.email != null ? args.data.email : ''
      this.txtDonVi = args.data.donvi != null ? args.data.donvi : ''

      try {
        var pdata = {
          hdlh_id: this.DsLienHe.hdlh_id,
          ttlh_id: this.DsLienHe.ttlh_id
        }
        api.getThongTin_LHMXH(this.axios, pdata).then((response) => {
          if (response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000') {
            var data_api = response.data.data
            //this.lh_id = data_api.lh_id
            if (data_api[0].kieu_yc == 1) {
              this.chkZalo = true
              this.txtZalo = data_api[0].mang_xh
            } else {
              this.chkDaQT = true
            }
          } else {
            this.chkZalo = false
            this.chkDaQT = false
            this.txtZalo = ''
          }
        })
        this.getThongTinKH()
        this.getThongTinTT()
        this.getThongTinTB()
        this.getThongTinMD()
        // this.getThongTinMD_CG()
        // this.getThongTinMD_DG()
      } catch (ex) {
        this.$toast.error('' + ex)
      }
    },

    getThongTinKH: async function () {
      return new Promise(async (resolve, reject) => {
        try {
          var pdata = {
            khachhang_id: this.khachhang_id,
            hdkh_id: this.vhdkh_id,
            loai_id: 1,
            hdlh_id: this.DsLienHe.hdlh_id,
            ttlh_id: this.DsLienHe.ttlh_id
          }
          api.getThongTinKHTTTB(this.axios, pdata).then((response) => {
            if (response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000') {
              this.DsLienHeKH.data = response.data.data
              this.DsLienHeKH.data.map((item) => (item.ttsd = item.sudung ? 'Đang SD' : ''))
            } else {
              this.DsLienHeKH.data = null
            }
          })
        } catch (ex) {
          this.$toast.error('' + ex)
        }
        resolve(true)
      })
    },

    getThongTinTT: async function () {
      return new Promise(async (resolve, reject) => {
        try {
          var pdata = {
            khachhang_id: this.khachhang_id,
            hdkh_id: this.vhdkh_id,
            loai_id: 2,
            hdlh_id: this.DsLienHe.hdlh_id,
            ttlh_id: this.DsLienHe.ttlh_id
          }
          api.getThongTinKHTTTB(this.axios, pdata).then((response) => {
            if (response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000') {
              this.DsLienHeTT.data = response.data.data
              this.DsLienHeTT.data.map((item) => (item.ttsd = item.sudung ? 'Đang SD' : ''))
            } else {
              this.DsLienHeTT.data = null
            }
          })
        } catch (ex) {
          this.$toast.error('' + ex)
        }
        resolve(true)
      })
    },

    getThongTinTB: async function () {
      return new Promise(async (resolve, reject) => {
        try {
          var pdata = {
            khachhang_id: this.khachhang_id,
            hdkh_id: this.vhdkh_id,
            loai_id: 3,
            hdlh_id: this.DsLienHe.hdlh_id,
            ttlh_id: this.DsLienHe.ttlh_id
          }
          api.getThongTinKHTTTB(this.axios, pdata).then((response) => {
            if (response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000') {
              this.DsLienHeTB.data = response.data.data
              this.DsLienHeTB.data.map((item) => (item.ttsd = item.sudung ? 'Đang SD' : ''))
            } else {
              // this.$root.toastError(response.data.message);
              this.DsLienHeTB.data = null
            }
          })
        } catch (ex) {
          this.$toast.error('' + ex)
        }
        resolve(true)
      })
    },
    //xoá
    getThongTinMD_CG: async function () {
      try {
        debugger
        if (this.hdlh_id) {
          const pdata = { hdlh_id: this.hdlh_id, kieu: 0 }
          api.getThongTinLHMD(this.axios, pdata).then((response) => {
            if (response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000') {
              this.DsMucDichCG.data = response.data.data
            } else {
              this.DsMucDichCG.data = []
            }
          })
        } else if(this.ttlh_id) {
          const pdata = { ttlh_id: this.ttlh_id, kieu: 0 }
          api.getThongTinLHMD_DB(this.axios, pdata).then((response) => {
            if (response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000') {
              this.DsMucDichCG.data = response.data.data
            } else {
              this.DsMucDichCG.data = []
            }
          })
        }else{
          const pdata = { hdlh_id: this.hdlh_id, kieu: 0 }
          api.getThongTinLHMD(this.axios, pdata).then((response) => {
            if (response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000') {
              this.DsMucDichCG.data = response.data.data
            } else {
              this.DsMucDichCG.data = []
            }
          })
        }
      } catch (ex) {
        this.$toast.error('' + ex)
      }
    },
    //xoá
    getThongTinMD_DG: async function () {
      try {
        if (this.hdlh_id ) {
          const pdata = { hdlh_id: this.hdlh_id, kieu: 1 }
          api.getThongTinLHMD(this.axios, pdata).then((response) => {
            if (response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000') {
              this.DsMucDichDG.data = response.data.data
            } else {
              this.DsMucDichDG.data = []
            }
          })
        }else if(this.ttlh_id){
          const pdata = { ttlh_id: this.ttlh_id, kieu: 1 }
          api.getThongTinLHMD_DB(this.axios, pdata).then((response) => {
            if (response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000') {
              this.DsMucDichDG.data = response.data.data
            } else {
              this.DsMucDichDG.data = []
            }
          })
        }
        else {
          const pdata = { hdlh_id: this.hdlh_id, kieu: 1 }
          api.getThongTinLHMD(this.axios, pdata).then((response) => {
            if (response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000') {
              this.DsMucDichDG.data = response.data.data
            } else {
              this.DsMucDichDG.data = []
            }
          })
        }
      } catch (ex) {
        this.$toast.error('' + ex)
      }
    },
    //Thay func
    getThongTinMD: async function (){
      if(this.hdlh_id){
        try {
            //Muc Dich chua gan
            const responseMDCG = await api.getThongTinLHMD(this.axios, { hdlh_id: this.hdlh_id, kieu: 0 });
            this.DsMucDichCG.data  = this.GetData(responseMDCG);

            //Muc Dich da gan
            const ressponseMDG = await api.getThongTinLHMD(this.axios, { hdlh_id: this.hdlh_id, kieu: 1 });
            this.DsMucDichDG.data = this.GetData(ressponseMDG);
        }
        catch(err){
          this.$toast.error('' + err)
        }
      }else if(this.ttlh_id){
        try{
          const responseMDCG = await api.getThongTinLHMD_DB(this.axios, { ttlh_id: this.ttlh_id, kieu: 0 });
          this.DsMucDichCG.data  = this.GetData(responseMDCG);

          //Muc Dich da gan
          const ressponseMDG = await api.getThongTinLHMD_DB(this.axios, {  ttlh_id: this.ttlh_id, kieu: 1 } );
          this.DsMucDichDG.data = this.GetData(ressponseMDG);
        }
        catch(err){
          this.$toast.error('' + err)
        }
      }else{
        const responseMDCG = await api.getThongTinLHMD_DB(this.axios, { ttlh_id: 0, kieu: 0 });
        this.DsMucDichCG.data  = this.GetData(responseMDCG);

        this.DsMucDichDG.data = [];
      }
    },
    select_selectedItemsChangedCG: function (dataKeys) {
      this.DsMucDichCG.checked = dataKeys
      this.DsMucDichCG.data.forEach(function (item) {
        if (dataKeys.filter((x) => x === item.mucdich_id).length > 0) {
          item.ischecked = true
        } else {
          item.ischecked = false
        }
      })
    },
    select_selectedItemsChangedDG: function (dataKeys) {
      this.DsMucDichDG.checked = dataKeys
      this.DsMucDichDG.data.forEach(function (item) {
        if (dataKeys.filter((x) => x === item.mucdich_id).length > 0) {
          item.ischecked = true
        } else {
          item.ischecked = false
        }
      })
    },
    // chuyen ds muc dich da gan, chua gan
    MoveRight: function () {
      var tempData = this.DsMucDichDG.data
      this.DsMucDichDG.data = []
      var data = this.DsMucDichCG.data.filter((x) => x.ischecked)
      if (data.length > 0) {
        data.forEach((item) => {
          tempData.push(item)
        })
      }
      this.DsMucDichDG.data = tempData.slice(0)
      this.DsMucDichCG.data = this.DsMucDichCG.data.filter((x) => !x.ischecked)
    },
    MoveRightAll: function () {
      var tempData = this.DsMucDichDG.data
      var data = this.DsMucDichCG.data
      if (data.length > 0) {
        data.forEach((item) => {
          tempData.push(item)
        })
        this.DsMucDichDG.data = tempData.slice(0)
      }
      this.DsMucDichCG.data = []
    },
    MoveLeft: function () {
      var tempData = this.DsMucDichCG.data
      var data = this.DsMucDichDG.data.filter((x) => x.ischecked)
      if (data.length > 0) {
        data.forEach((item) => {
          tempData.push(item)
        })
        this.DsMucDichCG.data = tempData.slice(0)
      } else {
        this.$root.toastError('Bạn chưa chọn thông tin để gán!')
      }
      this.DsMucDichDG.data = this.DsMucDichDG.data.filter((x) => !x.ischecked)
    },
    MoveLeftAll: function () {
      var tempData = this.DsMucDichCG.data
      var data = this.DsMucDichDG.data
      if (data.length > 0) {
        data.forEach((item) => {
          tempData.push(item)
        })
        this.DsMucDichCG.data = tempData.slice(0)
      }
      this.DsMucDichDG.data = []
    },

    btnTaoMoi_OnClick: async function () {
      this.SetButton(1)
    },

    toUpperCase(object) {
      return Object.fromEntries(Object.entries(object).map(([k, v]) => [k.toUpperCase(), v]))
    },

    btnCapNhat_OnClick: async function () {
      if (this.txtTenLH == '') {
        this.$refs.txtTenLH.focus()
        this.$toast.error('Bạn chưa nhập thông tin Người liên hệ !')
        return
      }
      if (this.txtDiaChiLH == '') {
        this.$refs.txtDiaChiLH.focus()
        this.$toast.error('Bạn chưa nhập thông tin Địa chỉ !')
        return
      }
      if (this.txtDienThoai == '') {
        this.$refs.txtDienThoai.focus()
        this.$toast.error('Bạn chưa nhập thông tin Điện thoại !')
        return
      }
      if(this.$root.token.getPhanVungID()!=98) {
        if (this.txtDienThoai.substring(0, 1) != 0) {
          this.$refs.txtDienThoai.focus()
          this.$toast.error('Số điện thoại phải bắt đầu bằng 0 !')
          return
        }

        if (this.txtDienThoai.length > 11) {
          this.$refs.txtDienThoai.focus()
          this.$toast.error('Số điện thoại tối đa là 11 ký tự !')
          return
        }

        if (!(this.txtDienThoai + '').match(/^0\d{9,10}$/g)) {
          this.$toast.error('Số điện thoại không được ít hơn 10 số và phải bắt đầu bằng 0')
          return false
        }
      }
      else {
        if (!(this.txtDienThoai + '').match(/^[\d-_\s]+$/g)) {
          this.$toast.error('Số điện thoại phải là số!')
          return false
        }
      }

      if (this.txtEmail && !this.txtEmail.match(/^[^\s@]+@[^\s@]+\.[^\s@]+$/)) {
        this.$refs.txtEmail.focus()
        this.$toast.error('Email không hợp lệ !')
        return
      }
      if (this.setButom.p_action === 0 || !this.hdlh_id) {
        this.hdlh_id = this.GetData(await api.getKey(this.axios, { keyname: 'HDLH_ID' }))
        this.rlh_id = this.GetData(await api.getKey(this.axios, { keyname: 'HDLH' }))
        // this.lh_id = this.getData(await api.getKey(this.axios,  { keyname: 'HDLH' }))
      }

      //dsLienH e
      var dr_lh = {}
      dr_lh.hdlh_id = this.hdlh_id
      dr_lh.ten_lh = this.txtTenLH
      dr_lh.diachi = this.txtDiaChiLH
      dr_lh.dienthoai = this.txtDienThoai
      dr_lh.fax = this.txtFax

      // dr_lh.hd_id = this.lienHeSelected.hdtb_id;
      dr_lh.ttlh_id = this.ttlh_id

      dr_lh.email = this.txtEmail
      dr_lh.donvi = this.txtDonVi

      dr_lh.hdkh_id = this.vhdkh_id
      // dr_lh.kieu_yc = this.lienHeSelected.kieu_yc ? ;
      // this.dsLienHe.push(dr_lh)
      dr_lh = this.toUpperCase(dr_lh)

      //dsThanhToan
      let dr_tt = []
      if (this.DsLienHeKH.data) {
        for (var item of this.DsLienHeKH.data) {
          var dr = {}
          dr.hdlh_id = this.hdlh_id
          dr.hd_id = item.hdkh_id ? item.hdkh_id : this.vhdkh_id
          dr.ttkh_id = 1 // THONGTIN_KH.KHACHHANG = 1
          dr.kieu_yc = item.dangky ? 1 : 0 // DANGKY_BOOL = 1 HUY_BOOL = 0
          dr.daucuoi_id = 0
           console.log('dr DsLienHeKH', dr)
          dr_tt.push(this.toUpperCase(dr))
        }
      }
     

      if (this.DsLienHeTT.data) {
        for (var item of this.DsLienHeTT.data) {
          var dr = {}
          dr.hdlh_id = this.hdlh_id
          dr.hd_id = item.hdtt_id?item.hdtt_id:0
          dr.ttkh_id = 2 // THONGTIN_KH.KHACHHANG = 1
          dr.kieu_yc = item.dangky ? 1 : 0 // DANGKY_BOOL = 1 HUY_BOOL = 0
          dr.daucuoi_id = 0
          console.log('dr DsLienHeTT', dr)
          dr_tt.push(this.toUpperCase(dr))
          // this.dsThanhToan.push(dr)
        }
      }

      if (this.DsLienHeTB.data) {
        for (var item of this.DsLienHeTB.data) {
          var dr = {}
          dr.hdlh_id = this.hdlh_id
          dr.hd_id = item.hdtb_id?item.hdtb_id:0
          dr.ttkh_id = 3 // THONGTIN_KH.KHACHHANG = 1
          dr.kieu_yc = item.dangky ? 1 : 0 // DANGKY_BOOL = 1 HUY_BOOL = 0
          dr.daucuoi_id = 0 // DIEMDAU_BOOL = 1  DIEMCUOI_BOOL = 2
          console.log('dr DsLienHeTB', dr)
          dr_tt.push(this.toUpperCase(dr))
          // this.dsThanhToan.push(dr)
        }
      }
     

      //dsMXH
      let dr_mxh = {}
      dr_mxh.rlh_id = this.rlh_id ? this.rlh_id: 0;
      dr_mxh.hdlh_id = this.hdlh_id
      dr_mxh.mang_xh = this.txtZalo
      dr_mxh.kieu = 1
      if (this.chkZalo) {
        dr_mxh.kieu_yc = 1
        this.isZalo = 1
      } else {
        dr_mxh.kieu_yc = 0
        this.isZalo = 0
      }
      //chkZalo check 1 else 0
      dr_mxh.loaimxh_id = 2
      dr_mxh = this.toUpperCase(dr_mxh)
      // this.dsMXH.push(dr_mxh)

      //dsMucDich
      let dsMD = []
      for (var item of this.DsMucDichDG.data) {
        // if (item.ischecked == true) {
        let dr_md = {}
        dr_md.mucdich_id = item.mucdich_id
        dr_md.hdlh_id = this.hdlh_id
        dsMD.push(this.toUpperCase(dr_md))
      }

      if (dsMD.length === 0) {
        return this.$toast.error('Bạn chưa chọn mục đích sử dụng nào !')
      }

      const pdata = {
        ds_hdkh_md: JSON.stringify(dsMD),
        ds_hdlh_mxh: JSON.stringify(dr_mxh),
        ds_hdttlh: JSON.stringify(dr_tt),
        hd_lienhe: JSON.stringify(dr_lh),
        mucdich_id: this.vMucDich,
        zalo: this.isZalo
      }
      this.$root.loading(true)
      api
        .postLienHe(this.axios, JSON.stringify(pdata))
        .then((response) => {
          this.$root.loading(false)
          if (response && response.data && response.data.error_code && response.data.error_code === 'BSS-00000000') {
            this.$toast.success('Cập nhật thông tin liên hệ thành công!')
            this.SetButton(3)
            this.getThongTinLienHe()
          } else {
            this.$toast.error(response.data.message)
          }
        })
        .catch((e) => {
          console.log(e)
          this.$root.loading(false)
        })
    },

    toUpperCase(object) {
      return Object.fromEntries(Object.entries(object).map(([k, v]) => [k.toUpperCase(), v]))
    },

    btnHuy_OnClick: async function () {
      this.$bvModal
        .msgBoxConfirm('Xác nhận hành động', {
          title: 'Bạn thật sự muốn xóa hợp đồng liên hệ không ?',
          size: 'sm',
          okTitle: 'Đồng ý',
          cancelTitle: 'Hủy'
        })
        .then(async (v) => {
          if (v) {
            this.$root.$loading = true
            try {
              var pdata = { hdlh_id: this.DsLienHe.hdlh_id }
              api.DeleteLienHe(this.axios, pdata).then((response) => {
                if (response && response.data && response.data.error_code && response.data.error_code === 'BSS-00000000') {
                  this.$toast.success('Xóa hợp đồng liên hệ thành công!')
                  this.getThongTinLienHe()
                  this.SetButton(0)
                } else {
                  this.$toast.error(response.data.message)
                }
                this.$root.$loading = false
              })
            } catch (ex) {
              this.$toast.error('' + ex)
            }
          }
        })
    },

    tbtnXoaTTLH_OnClick: async function () {
      //dsLienHe
      if (this.hdlh_id != 0) {
        this.$toast.error('Bạn không thực hiện đc thao tác này!')
        return
      }

      this.hdlh_id = this.GetData(await api.getKey(this.axios, { keyname: 'HDLH_ID' }))
      var dr_lh = {}
      dr_lh.hdlh_id = this.hdlh_id
      dr_lh.ten_lh = this.txtTenLH
      dr_lh.diachi = this.txtDiaChiLH
      dr_lh.dienthoai = this.txtDienThoai
      dr_lh.fax = this.txtFax
      dr_lh.email = this.txtEmail
      dr_lh.donvi = this.txtDonVi
      dr_lh.ttlh_id = this.ttlh_id
      dr_lh.hdkh_id = this.vhdkh_id
      dr_lh.kieu_yc = 0 //Xoá
      dr_lh = this.toUpperCase(dr_lh)
      // this.dsLienHe.push(dr_lh)

      //Khach Hang
      let dsTT = []
      if (this.DsLienHeKH.data) {
        for (var item of this.DsLienHeKH.data) {
          if (item.sudung === 'Đang SD') {
            let dr = {}
            dr.hdlh_id = this.hdlh_id
            // dr.hd_id = item.vhdkh_id;
            dr.ttkh_id = 1 // THONGTIN_KH.KHACHHANG = 1
            dr.kieu_yc = 0 // DANGKY_BOOL = 1 HUY_BOOL = 0
            dr.daucuoi_id = 0
            dr = this.toUpperCase(dr)
            dsTT.push(dr)
          }
        }
      }

      //Thanh Toan
      if (this.DsLienHeTT.data) {
        for (var item of this.DsLienHeTT.data) {
          if (item.sudung === 'Đang SD') {
            let dr = {}
            dr.hdlh_id = this.hdlh_id
            dr.hd_id = item.hdtt_id
            dr.ttkh_id = 1 // THONGTIN_KH.KHACHHANG = 1
            dr.kieu_yc = 0 // DANGKY_BOOL = 1 HUY_BOOL = 0
            dr.daucuoi_id = 0
            dr = this.toUpperCase(dr)
            dsTT.push(dr)
          }
        }
      }

      //Thue Bao
      if (this.DsLienHeTB.data) {
        for (var item of this.DsLienHeTB.data) {
          if (item.sudung === 'Đang SD') {
            var dr = {}
            dr.hdlh_id = this.hdlh_id
            dr.hd_id = item.hdtb_id
            dr.ttkh_id = 1 // THONGTIN_KH.KHACHHANG = 1
            dr.kieu_yc = 0 // DANGKY_BOOL = 1 HUY_BOOL = 0
            dr.daucuoi_id = 0 // DIEMDAU_BOOL = 1  DIEMCUOI_BOOL = 2
            dr = this.toUpperCase(dr)
            dsTT.push(dr)
          }
        }
      }

      //dsMXH
      let dr_mxh = {}
      dr_mxh.rlh_id = null
      dr_mxh.hdlh_id = null
      dr_mxh.mang_xh = null
      dr_mxh.kieu = null
      dr_mxh.kieu_yc = 0 //chkZalo check 1 else 0
      dr_mxh.loaimxh_id = null
      dr_mxh = this.toUpperCase(dr_mxh)
      // this.dsMXH.push(dr_mxh)

      //dsMucDich
      let dsMD = []
      for (var item of this.DsMucDichDG.data) {
        var dr = {}
        dr.mucdich_id = item.mucdich_id
        dr.hdlh_id = this.lienHeSelected.hdlh_id
        dr = this.toUpperCase(dr)
        dsMD.push(dr)
      }

      this.$bvModal
        .msgBoxConfirm('Xác nhận hành động', {
          title: 'Bạn thật sự muốn xóa hợp đồng liên hệ không ?',
          size: 'sm',
          okTitle: 'Đồng ý',
          cancelTitle: 'Hủy'
        })
        .then(async (v) => {
          if (v) {
            this.$root.$loading = true

            var pdata = {
              ds_hdkh_md: JSON.stringify(dsMD),
              ds_hdlh_mxh: JSON.stringify([dr_mxh]),
              ds_hdttlh: JSON.stringify(dsTT),
              hd_lienhe: JSON.stringify(dr_lh),
              mucdich_id: 0,
              zalo: 0
            }
            api
              .postLienHe(this.axios, JSON.stringify(pdata))
              .then((response) => {
                if (response && response.data && response.data.error_code && response.data.error_code === 'BSS-00000000') {
                  this.$toast.success('Xoá thông tin thành công!')
                  this.getThongTinLienHe()
                  this.SetButton(3)
                } else {
                  this.$toast.error(response.data.message)
                }
                this.$root.$loading = false
              })
              .catch((ex) => {
                this.$root.$loading = false
                this.$toast.error('' + ex)
              })
          }
        })
    },

    GetData: function (response) {
      console.log(response)
      if (response.data.error === 200 || response.data.error === '200') {
        return response.data.data
      } else {
        console.log(response.data.error_code)
      }
      return []
    },
    Clean() {
      this.txtDienThoai = ''
      this.txtFax = ''
      this.txtEmail = ''
      this.txtDonVi = ''
      this.hdlh_id = 0
      this.ttlh_id = 0
    }
  }
}
</script>