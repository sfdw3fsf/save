<template>
  <b-modal
    ref="popupVatTuThueBao"
    size="xl"
    hide-header
    hide-header-close
    hide-footer
    body-class="p-0"
    no-close-on-backdrop
    @show="handleShowModal"
  >
    <div class="modal-content popup-box">
      <div class="popup-header">
        <div class="title">
          <span class="icon one-notepad"></span> {{headerTitle}}
        </div>
        <div class="close -ap icon-close" @click="hideModal"></div>
      </div>
      <div class="list-actions-top">
        <ul class="list">
          <li v-for="item in actions" :key="item.id" @click="onActionClick(item)"
              :class="{disabled: !item.active}" v-show="item.visible">
            <a style="cursor: pointer;" :class="{disabled_color: !item.active}" :ref="item.id">
              <span class="icon" :class="item.icon_class"></span> {{ item.name }}
            </a>
          </li>
        </ul>
      </div>
      <div class="popup-body">
        <div class="box-form">
          <div class="legend-title">Thông tin thiết bị</div>
          <div class="row">
            <div class="col-sm-6 col-12">
              <div class="info-row">
                <div class="key w90">Trang bị</div>
                <div class="value">
                  <div class="select-custom">
                    <Select2 ref="trangbiId" :disabled='false' v-model="cboTrangBiADSL.value"
                             :options="cboTrangBiADSL.list" :settings="settingSelect2"/>
                  </div>
                </div>
              </div>
              <div class="info-row">
                <div class="key w90">Nhóm thiết bị</div>
                <div class="value">
                  <div class="select-custom">
                    <Select2 ref="nhomtbid" :disabled='false' v-model="cboNhomTBI.value"
                             :options="cboNhomTBI.list" :settings="settingSelect2"/>
                  </div>
                </div>
              </div>
              <div class="info-row">
                <div class="key w90">Thiết bị</div>
                <div class="value">
                  <div class="select-custom">
                    <Select2 ref="thietbiid" :disabled='false' v-model="cboThietBi.value"
                             :options="cboThietBi.list" :settings="settingSelect2"/>
                  </div>
                </div>
              </div>
              <div class="info-row">
                <div class="key w90">Kiểu thiết bị</div>
                <div class="value">
                  <div class="select-custom">
                    <Select2 ref="kieuthietbi" :disabled='false' v-model="cboKieuThietBi.value"
                             :options="cboKieuThietBi.list" :settings="settingSelect2"/>
                  </div>
                </div>
              </div>
              <div class="info-row">
                <div class="key w90">Tình trạng</div>
                <div class="value">
                  <div class="select-custom">
                    <Select2 ref="trinhtrangid" :disabled='false' v-model="cboTinhTrang.value"
                             :options="cboTinhTrang.list" :settings="settingSelect2"/>
                  </div>
                </div>
              </div>
            </div>
            <div class="col-sm-6 col-12">
              <div class="info-row">
                <div class="key w90">Đơn vị</div>
                <div class="value">
                  <div class="select-custom">
                    <Select2 ref="donvitbi" :disabled='false' v-model="cboDonvi_TBI.value"
                             :options="cboDonvi_TBI.list" :settings="settingSelect2"/>
                  </div>
                </div>
              </div>
              <div class="info-row">
                <div class="key w90">Số lượng</div>
                <div class="value">
                  <input type="number" ref="txtSoLuong" v-model="frmThongTinTbi.txtSoluong"
                         class="form-control highlight">
                </div>
              </div>
              <div class="info-row">
                <div class="key w90">Seri</div>
                <div class="value">
                  <input type="text" ref="txtSeri" v-model="frmThongTinTbi.txtSeri" class="form-control highlight">
                </div>
              </div>
              <div class="info-row">
                <div class="key w90">Ghi chú</div>
                <div class="value">
                <textarea ref="txtGhichu" class="form-control" v-model="frmThongTinTbi.txtGhiChu"
                          style="height: 70px;resize: none;"></textarea>
                </div>
              </div>
            </div>
          </div>
        </div>
        <div class="box-form">
          <div class="legend-title">Danh sách cấp/thu hồi vật tư</div>
          <DataGrid ref="gridDanhSachVatTu" v-bind:columns="gridviewVatTu.headers"
                    v-bind:dataSource="gridviewVatTu.list" :showColumnCheckbox="false"
                    :enable-paging-server="false" :allowPaging="true" :showFilter="true"
                    @selectedRowChanged="gridviewVatTu_FocusedRowChanged"
                    @rowDoubleClicked="gridviewVatTu_DoubleClick"
                    :enabled-select-first-row="true"
          ></DataGrid>
        </div>
        <div class="box-form">
          <div class="legend-title">Danh sách vật tư thuê bao đang sử dụng</div>
          <DataGrid ref="gridDanhSachVatTuDangSD" v-bind:columns="gridViewVatTuSD.headers"
                    v-bind:dataSource="gridViewVatTuSD.list" :showColumnCheckbox="false"
                    :enable-paging-server="false" :allowPaging="true" :showFilter="true"
                    :enabled-select-first-row="true"
          ></DataGrid>
        </div>
      </div>
    </div>
  </b-modal>
</template>

<script>
import VatTuThueBaoAPI from './VatTuThueBaoAPI'
import {TRANGBI} from '../../InstallNewSubs/Enums'
import Vue from 'vue'
import moment from 'moment'
import {DichVuVienThong} from '../../../../../const/enums'
import XLSX from 'xlsx'

export default {
  name: 'VatTuThueBao',
  props: {
    inputData: {
      type: Object,
      default: function () {
        return null
      }
    }
  },
  data () {
    return {
      headerTitle: 'Cấp vật tư/Thiết bị cho thuê bao',
      actions: [
        {
          id: 'tsbtnNhapMoi',
          name: 'Nhập mới',
          active: true,
          icon_class: 'one-file-plus',
          visible: true
        },
        {
          id: 'tsbtnGhiLai',
          name: 'Ghi lại',
          active: true,
          icon_class: 'one-save',
          visible: true
        },
        {
          id: 'tsbtnHuyBo',
          name: 'Hủy',
          active: true,
          icon_class: 'nc-icon-glyph ui-1_circle-remove',
          visible: true
        },
        {
          id: 'tsbtnXoa',
          name: 'Xóa',
          active: true,
          icon_class: 'one-trash',
          visible: true
        },
        {
          id: 'tsbtnExcel',
          name: 'Xuất Excel',
          active: true,
          icon_class: 'one-xlsx-import',
          visible: true
        }
      ],
      animationSettings: {effect: 'Zoom'},
      position: {X: 'center', Y: 'top'},
      gridviewVatTu: {
        list: [],
        headers: [
          {
            fieldName: 'STT',
            headerText: 'STT',
            allowFiltering: true,
            allowSorting: false
          },
          {
            fieldName: 'loai_tbi',
            headerText: 'Thiết bị',
            allowFiltering: true,
            allowSorting: false
          },
          {
            fieldName: 'kieu_tbi',
            headerText: 'Kiểu thiết bị',
            allowFiltering: true,
            allowSorting: false
          },
          {
            fieldName: 'tinhtrang_tbi',
            headerText: 'Tình trạng',
            allowFiltering: true,
            allowSorting: false
          },
          {
            fieldName: 'seri',
            headerText: 'Seri',
            allowFiltering: true,
            allowSorting: false
          },
          {
            fieldName: 'soluong',
            headerText: 'Số lượng',
            allowFiltering: true,
            allowSorting: false
          },
          {
            fieldName: 'ngay_th',
            headerText: 'Ngày cấp',
            format: {type: 'date', format: 'dd/MM/yyyy'},
            type: 'date',
            allowFiltering: true,
            allowSorting: false
          },
          {
            fieldName: 'trangbi',
            headerText: 'Trang bị',
            allowFiltering: true,
            allowSorting: false
          },
          {
            fieldName: 'nguoi_cn',
            headerText: 'Người CN',
            allowFiltering: true,
            allowSorting: false
          },
          {
            fieldName: 'ten_dv',
            headerText: 'Đơn vị',
            allowFiltering: true,
            allowSorting: false
          },
          {
            fieldName: 'ghichu',
            headerText: 'Ghi chú',
            allowFiltering: true,
            allowSorting: false
          }
        ],
        selected: 0,
        checked: [],
        selectItem: null
      },
      gridViewVatTuSD: {
        list: [],
        headers: [
          {
            fieldName: 'stt',
            headerText: 'STT',
            allowFiltering: true,
            allowSorting: false,
            textAlign: 'center',
            visible: true,
            width: 70
          },
          {
            fieldName: 'loai_tbi',
            headerText: 'Thiết bị',
            allowFiltering: true,
            allowSorting: false,
            textAlign: 'left',
            visible: true
          },
          {
            fieldName: 'tinhtrang_tbi',
            headerText: 'Tình trạng',
            allowFiltering: true,
            allowSorting: false,
            visible: true
          },
          {
            fieldName: 'seri',
            headerText: 'Seri',
            allowFiltering: true,
            allowSorting: false,
            textAlign: 'left',
            visible: true
          },
          {
            fieldName: 'soluong',
            headerText: 'Số lượng',
            allowFiltering: true,
            allowSorting: false,
            visible: true,
            textAlign: 'left'
          },
          {
            fieldName: 'ngay_th',
            headerText: 'Ngày cấp',
            allowFiltering: true,
            allowSorting: false,
            format: {type: 'date', format: 'dd/MM/yyyy'},
            type: 'date',
            visible: true,
            textAlign: 'left'
          },
          {
            fieldName: 'tentrangbi',
            headerText: 'Trang bị',
            allowFiltering: true,
            allowSorting: false,
            textAlign: 'left',
            visible: true
          },
          {
            fieldName: '',
            headerText: 'Thu hồi',
            allowFiltering: true,
            allowSorting: false,
            visible: true,
            textAlign: 'center',
            width: 90,
            template: function () {
              return {
                template: Vue.component('ThuHoiTemplate', {
                  template: `<a class="btn btn-thuhoivt lh14" @click="onThuHoiVatTu"
                                style="background-color: #3684e0 !important; padding: 5px 15px; color: red">
                  <span class="icon nc-icon-glyph ui-1_simple-remove"></span></a>`,
                  data () {
                    return {data: {}}
                  },
                  computed: {
                    parent () {
                      return this.$parent.$parent.$parent.$parent.$parent
                    }
                  },
                  methods: {
                    onThuHoiVatTu () {
                      this.parent.ThuHoiVatTu(this.data)
                    }
                  }
                })
              }
            }
          }
        ],
        selected: 0,
        selectItem: null,
        checked: [],
        showColumnCheckbox: true
      },
      cboDonvi_TBI: {
        list: [],
        disable: false,
        value: 0
      },
      cboTrangBiADSL: {
        list: [],
        disable: false,
        value: 0
      },
      cboNhomTBI: {
        list: [],
        disable: false,
        value: 0
      },
      cboThietBi: {
        list: [],
        disable: false,
        value: 0
      },
      cboKieuThietBi: {
        list: [],
        disable: false,
        value: 0
      },
      cboTinhTrang: {
        list: [],
        disable: false,
        value: 0
      },
      settingSelect2: {
        language: 'vi'
      },
      frmThongTinTbi: {
        txtSoluong: 0,
        txtSeri: '',
        txtGhiChu: ''
      },
      phieu_id: 0,
      loaitbi_id: 0,
      kieutbi_id: 0,
      vattu_id: 0,
      rvt_id: 0,
      donvi_id: 0,
      loaihd_id: 0,
      trangbi_id: 0,
      dichvuvt_id: 0,
      hdtb_id: 0,
      thuebao_id: 0,
      vtag: 1, // 1: Cấp, thu hồi vật tư khi giao phiếu; 2: Sửa vật tư sau khi hồ sơ đã được hoàn thiện.
      vsua_vattu: 0,
      vngay_ht: '', // Ngày hoàn thành của thuê bao.,
      vtthd_id: 0, // trạng thái của hợp đồng thuê bao.
      // trạng thái phiéu giao đến đơn vị phải = 4 thì mới cho sửa vật tư
      vttph_id: 0,
      vdonvi_nhan_id: 0,
      vdonvi_xuat_vt: 0,
      donviId: Number.parseInt(this.$root.token.getDonViID()),
      nhanvienId: Number.parseInt(this.$root.token.getNhanVienID()),
      dsGiaoPhieuTb: []
    }
  },
  watch: {
    'cboNhomTBI.value': function (newValue, oldValue) {
      this.SP_LAY_DS_LOAITBI_HDTB(this.hdtb_id, Number.parseInt(newValue))
    },
    'cboKieuThietBi.value': function (newValue, oldValue) {
      this.kieutbi_id = newValue
    },
    'cboThietBi.value': async function (newValue, oldValue) {
      this.donvi_id = await this.FN_LAY_DONVI_TBI_THEO_LOAITBI(newValue)
      this.cboDonvi_TBI.value = this.donvi_id
      this.loaitbi_id = newValue
    }
  },
  methods: {
    handleShowModal () {
      this.cboTinhTrang.list = []
      this.cboTrangBiADSL.list = []
      this.cboKieuThietBi.list = []
      this.cboDonvi_TBI.list = []
      this.cboNhomTBI.list = []
      this.cboKieuThietBi.list = []
      this.gridviewVatTu.list = []
      this.gridviewVatTu.selectItem = []
      setTimeout(() => {
        this.initData()
      }, 500)
    },
    hideModal () {
      this.$refs.popupVatTuThueBao.hide()
    },
    initData () {
      try {
        this.loading(true)
        this.setActiveActions(-1)
        this.LoadDataFromProps()
        this.CSS_TRANGBI()
        this.SP_LAY_DS_DONVI_TBI()
        this.CSS_KIEU_TBI()
        this.CSS_TINHTRANG_TBI()
        this.CSS_NHOM_TBI()
        this.HienThiDS()
        this.HienThiDS_vattu_tb()
        if (this.vsua_vattu === 1) {
          this.headerTitle = 'SỬA ĐỔI VẬT TƯ CHO THUÊ BAO'
        }
      } catch (error) {
        this.$root.$toast.error('Có lỗi: ' + error)
      } finally {
        this.loading(false)
      }
    },
    LoadDataFromProps () {
      if (this.inputData != null) {
        console.log(this.inputData)
        this.loaitb_id = this.inputData.loaitb_id ? this.inputData.loaitb_id : 0
        this.phieu_id = this.inputData.phieu_id ? this.inputData.phieu_id : 0
        this.loaihd_id = this.inputData.loaihd_id ? this.inputData.loaihd_id : 0
        this.hdtb_id = this.inputData.hdtb_id ? this.inputData.hdtb_id : 0
        this.thuebao_id = this.inputData.thuebao_id ? this.inputData.thuebao_id : 0
        this.vsua_vattu = this.inputData.vsua_vattu ? this.inputData.vsua_vattu : 0
        this.vdonvi_nhan_id = this.inputData.vdonvi_nhan_id ? this.inputData.vdonvi_nhan_id : 0
        this.vngay_ht = this.inputData.vngay_ht ? this.inputData.vngay_ht : ''
        this.vttph_id = this.inputData.vttph_id ? this.inputData.vttph_id : 0
      }
    },
    showModal () {
      this.initData()
      this.$refs.popupVatTuThueBao.show()
    },
    async Xoa_Vattu () {
      if (this.rvt_id == 0) {
        this.$root.$toast.warning('Chưa có thông tin phiếu để xóa')
        return
      }
      if (this.vsua_vattu == 1) {
        if (this.vdonvi_nhan_id != Number.parseInt(this.$root.token.getDonViID())) {
          this.$root.$toast.warning('Phiếu không cùng đơn vị với đơn vị của user. Bạn không được phép xóa.')
          return
        }

        if (this.vngay_ht) {
          let now = new Date()
          let ngayht = new Date(this.vngay_ht)
          if (moment(ngayht).format('MMYYYY') !== moment(now).format('MMYYYY')) {
            let lastmonth = now.setMonth(now.getMonth() - 1)
            if (moment(ngayht).format('YYYYMM') !== moment(lastmonth).format('YYYYMM')) {
              this.$root.$toast.warning('Bạn chỉ được phép thao tác phiếu thực hiện trong tháng hoặc tháng trước')
              return
            }
          }
        }
      }

      if (this.dichvuvt_id == DichVuVienThong.TSL || this.dichvuvt_id == DichVuVienThong.NOIBO_2DIEM) {
        if (this.vsua_vattu == 0) {
          if (this.vdonvi_xuat_vt !== Number.parseInt(this.$root.token.getDonViID())) {
            this.$root.$toast.warning('Bạn không được xóa vật tư của đơn vị khác !')
            return
          }
        }
      }

      let accept = await this.$confirm('Bạn thật sự muốn xóa dữ liệu không ?', 'Thông báo', {
        confirmButtonText: 'Có',
        cancelButtonText: 'Không'
      })
      if (accept) {
        let request = {
          'vkieu': 1,
          'vthuebao_id': this.thuebao_id,
          'vhdtb_id': this.hdtb_id,
          'vrvt_id': this.rvt_id,
          'vsua_vattu': this.vsua_vattu,
          'vngayht': this.vngay_ht ? moment(this.vngay_ht).format('DD/MM/YYYY HH:mm:ss') : moment(new Date()).format('DD/MM/YYYY HH:mm:ss'),
          'vdonvi_nhan_id': this.vdonvi_nhan_id,
          'vnhanvien_id': this.$root.token.getNhanVienID(),
          'vdonvi_id': this.donviId,
          'vdonvi_xuat_id': this.vdonvi_xuat_vt
        }
        await VatTuThueBaoAPI.FN_AN_XOA_VATTU(this.axios, request).then(response => {
          if (response && response.data && response.data.error_code === 'BSS-00000000') {
            this.$root.$toast.success('Xóa thành công')
            this.gridviewVatTu.selectItem = null
            this.HienThiDS()
            if (this.vsua_vattu == 1) {
              this.HienThiDS_vattu_tb()
            }
            if (this.gridviewVatTu.list.length <= 0) {
              this.setActiveButton(-1)
            }
          } else {
            this.$root.$toast.error('Xóa thất bại: ' + response.data.message_detail)
          }
        }).catch(error => {
          console.log(error)
          this.$root.$toast.error('Có lỗi: ' + error.data.message_detail)
        })
      }
    },
    async ThuHoiVatTu (item) {
      try {
        if (item != null) {
          if (this.phieu_id === 0 || this.hdtb_id === 0) {
            this.$root.$toast.warning('Chưa có thông tin phiếu. Hãy kiểm tra lại!')
            this.$refs.txtSoLuong.focus()
            return
          }

          let vLoaiTbiId = item.loaitbi_id
          let vkieuTbiId = item.kieutbi_id
          let vtrangbiid = item.trangbi_id
          let vvattuid = item.vattu_id
          let tinhtrangtbiid = item.tinhtrangtbi_id
          let vsoluong = item.sl
          let vseri = item.seri
          let check = await this.KIEMTRA_CAP_VATTU(this.phieu_id, this.thuebao_id, vLoaiTbiId, vkieuTbiId, vseri, vvattuid, 2)
          if (check) {
            this.$root.$toast.warning('Dữ liệu đã tồn tại!')
            return
          }
          this.$confirm('Bạn thật sự muốn thu hồi không ?', 'Thông báo', {
            confirmButtonText: 'Có',
            cancelButtonText: 'Không'
          }).then(async () => {
            await this.TaoDuLieu_ThuHoi(vLoaiTbiId, vkieuTbiId, vtrangbiid, vvattuid, tinhtrangtbiid, vsoluong, vseri)
            await VatTuThueBaoAPI.INSERT_VATTU(this.axios, {
              'dsgiaophieutbi': JSON.stringify(this.dsGiaoPhieuTb),
              'p_rvt_id': this.dsGiaoPhieuTb[0].RVT_ID,
              'p_sua_vattu': this.vsua_vattu,
              'p_thuebao_id': this.thuebao_id
            }).then(response => {
              // TODO check lai api
              console.log(response)
              this.HienThiDS()
              if (this.vsua_vattu === 1) {
                this.HienThiDS_vattu_tb()
              }
            })
          })
        }
      } catch (error) {
        console.log(error)
        this.$root.$toast.error('Có lỗi khi thu hồi vật tư:' + error)
      }
    },
    async TaoDuLieu_ThuHoi (loaitbiid, kieutbid, trangbiid, vattuid, tinhtrangtbiid, soluong, seri) {
      try {
        this.dsGiaoPhieuTb = []
        let row = {}
        row.RVT_ID = await this.POST_GET_KEYS('RVT_ID')
        row.VATTU_ID = vattuid.toString()
        row.TINHTRANGTBI_ID = tinhtrangtbiid.toString()
        row.LOAITBI_ID = loaitbiid.toString()
        row.KIEUTBI_ID = kieutbid.toString()
        row.TRANGBI_ID = trangbiid.toString()
        row.PHIEU_ID = this.phieu_id.toString()
        row.SL = soluong
        row.NGAY_TH = moment(new Date()).format('DD/MM/YYYY HH:mm:ss')
        row.SERI = seri
        row.NGUOIDUNG_ID = this.$root.token.getNguoiDungID().toString()
        row.NHANVIEN_ID = this.nhanvienId.toString()
        row.DONVI_ID = this.donviId.toString()
        row.NGUOI_CN = this.$root.token.getUserName()
        row.NGAY_CN = this.$root.token.getNguoiDungID().toString()
        row.NGAY_CN = moment(new Date()).format('DD/MM/YYYY HH:mm:ss')
        row.IP_CN = this.$root.token.getIP()
        row.GHICHU = this.frmThongTinTbi.txtGhiChu.trim()
        this.dsGiaoPhieuTb.push(row)
      } catch (error) {
        console.log(error)
        this.$root.$toast.error('Có lỗi khi tạo ')
      }
    },
    async HienThiDS () {
      let data = this.GetDataList(await VatTuThueBaoAPI.LAY_DS_VATTU_HDTB(this.axios, {
        hdtb_id: this.hdtb_id
      }))
      if (data.length > 0) {
        data.forEach((element, index) => {
          element.STT = index + 1
        })
        this.gridviewVatTu.list = data
        this.setActiveActions(3)
      } else {
        this.Clean()
        if (this.cboThietBi.list.length > 0) {
          this.donvi_id = await this.fn_tt_loai_tbi(this.loaitbi_id)
          this.cboDonvi_TBI.value = this.donvi_id
        }
      }
    },
    async fn_tt_loai_tbi (loaitbiid) {
      let req = {
        param: loaitbiid,
        type: 1
      }
      return this.GetData(await VatTuThueBaoAPI.fn_tt_loai_tbi(this.axios, req))
    },
    async HienThiDS_vattu_tb () {
      if (this.thuebao_id !== 0) {
        let data = this.GetDataList(await VatTuThueBaoAPI.LAY_DS_VATTU_DBTB(this.axios, {
          vthuebao_id: this.thuebao_id
        }))
        if (data.length > 0) {
          data.forEach((element, index) => {
            element.STT = index + 1
          })
          this.gridViewVatTuSD.list = data
        }
      }
    },
    gridviewVatTu_FocusedRowChanged (item) {
      if (item != null) {
        console.log('item: ', item)
        this.setActiveActions(3)
        if (item.nhom_tbi_id) {
          this.cboNhomTBI.value = Number.parseInt(item.nhom_tbi_id)
        }
        if (item.loaitbi_id) {
          this.cboThietBi.value = Number.parseInt(item.loaitbi_id)
        }

        if (item.donvi_tbi_id) {
          this.cboDonvi_TBI.value = Number.parseInt(item.donvi_tbi_id)
        }

        if (item.kieutbi_id) {
          this.cboKieuThietBi.value = item.kieutbi_id
        }

        if (item.tinhtrangtbi_id) {
          this.cboTinhTrang.value = item.tinhtrangtbi_id
        }

        if (item.trangbi_id) {
          this.cboTrangBiADSL.value = item.trangbi_id
        }
        this.frmThongTinTbi.txtSoluong = item.soluong
        this.frmThongTinTbi.txtSeri = item.seri
        this.frmThongTinTbi.txtGhiChu = item.ghichu
        this.kieutbi_id = item.kieutbi_id
        this.loaitbi_id = item.loaitbi_id ? item.loaitbi_id : 0
        this.vattu_id = item.vattu_id
        this.rvt_id = item.rvt_id ? item.rvt_id : 0
        this.vdonvi_xuat_vt = item.donvi_id
      }
    },
    gridviewVatTu_DoubleClick (index, item) {
      if (item !== null) {
        this.gridviewVatTu_FocusedRowChanged(item)
      }
    },
    async KIEMTRA_CAP_VATTU (phieuid, thuebaoid, loaitbiid, kieutbiid, seri, vattuid, kieu) {
      let data = this.GetDataList(await VatTuThueBaoAPI.KIEMTRA_CAP_VATTU(this.axios, {
        'vkieu': kieu.toString(),
        'vkieutbi_id': kieutbiid.toString(),
        'vloaitbi_id': loaitbiid.toString(),
        'vphieu_id': phieuid.toString(),
        'vseri': seri,
        'vthuebao_id': thuebaoid.toString(),
        'vvattu_id': vattuid.toString()
      }))
      return data.length > 0
    },
    async FN_LAY_DONVI_TBI_THEO_LOAITBI (loaitbiId) {
      let data = this.GetData(await VatTuThueBaoAPI.FN_LAY_DONVI_TBI_THEO_LOAITBI(this.axios, {
        loaitbi_id: loaitbiId
      }))
      if (data != null) {
        return Number.parseInt(data)
      }
      return 0
    },
    async SP_LAY_DS_LOAITBI_HDTB (hdtbId, nhomtbiId) {
      let data = this.GetDataList(await VatTuThueBaoAPI.SP_LAY_DS_LOAITBI_HDTB(this.axios, {
        'nhom_tbi_id': nhomtbiId,
        'hdtb_id': hdtbId
      }))
      if (data.length > 0) {
        data = data.filter(item => item.loai_tbi !== '0')
        if (data.length > 0) {
          this.cboThietBi.list = data.map(item => {
            return {
              id: item.loaitbi_id,
              text: item.loai_tbi
            }
          })
          this.cboThietBi.value = this.cboThietBi.list[0].id
        }
      }
    },
    async CSS_TRANGBI () {
      let data = this.GetDataList(await VatTuThueBaoAPI.CSS_TRANGBI(this.axios))
      if (data.length > 0) {
        this.cboTrangBiADSL.list = data.map(item => {
          return {
            id: item.TRANGBI_ID,
            text: item.TENTRANGBI
          }
        })
        this.cboTrangBiADSL.value = TRANGBI.VIENTHONG_TRANGBI
      }
    },
    async SP_LAY_DS_DONVI_TBI () {
      let data = this.GetDataList(await VatTuThueBaoAPI.SP_LAY_DS_DONVI_TBI(this.axios))
      if (data.length > 0) {
        this.cboDonvi_TBI.list = data.map(item => {
          return {
            id: item.donvi_tbi_id,
            text: item.donvi_tbi
          }
        })
        this.cboDonvi_TBI.value = this.cboDonvi_TBI.list[0].id
      }
    },
    async CSS_KIEU_TBI () {
      let data = this.GetDataList(await VatTuThueBaoAPI.CSS_KIEU_TBI(this.axios))
      if (data.length > 0) {
        this.cboKieuThietBi.list = data.map(item => {
          return {
            id: item.KIEUTBI_ID,
            text: item.KIEU_TBI
          }
        })
        this.cboKieuThietBi.value = this.cboKieuThietBi.list[0].id
      }
    },
    async CSS_TINHTRANG_TBI () {
      let data = this.GetDataList(await VatTuThueBaoAPI.CSS_TINHTRANG_TBI(this.axios))
      if (data.length > 0) {
        this.cboTinhTrang.list = data.map(item => {
          return {
            id: item.TINHTRANGTBI_ID,
            text: item.TINHTRANG_TBI
          }
        })
        this.cboTinhTrang.value = this.cboTinhTrang.list[0].id
      }
    },
    async CSS_NHOM_TBI () {
      let data = this.GetDataList(await VatTuThueBaoAPI.CSS_NHOM_TBI(this.axios))
      if (data.length > 0) {
        this.cboNhomTBI.list = data.map(item => {
          return {
            id: item.NHOM_TBI_ID,
            text: item.NHOM_TBI
          }
        })
        this.cboNhomTBI.value = this.cboNhomTBI.list[0].id
      }
    },
    GetDataList: function (response) {
      if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
        return response.data.data
      } else if (response && response.data && response.data.error_code === 'BSS-00000204') {
      } else {
        this.$root.$toast.error('Lấy dữ liệu không thành công')
      }
      return []
    },
    GetData: function (response) {
      if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
        return response.data.data
      } else if (response && response.data && response.data.error_code === 'BSS-00000204') {
        return null
      } else {
        this.$root.$toast.error('Lấy dữ liệu không thành công')
      }
      return null
    },
    async CapNhat () {
      try {
        if (!this.KiemTraDuLieuNhap()) {
          return
        }
        if (this.vsua_vattu === 1) {
          if (this.getStatusBtn('tsbtnNhapMoi')) {
            this.$root.$toast.warning('Bạn không được phép sửa đổi vật tư đã cấp cho khách hàng. Hãy thực hiện xóa vật tư và cấp mới lại.')
            return
          }
        }
        this.$confirm('Bạn chắc chắn muốn sửa đổi vật tư cho thuê bao ?', 'Thông báo', {
          confirmButtonText: 'Có',
          cancelButtonText: 'Không'
        }).then(async () => {
          if (this.getStatusBtn('tsbtnNhapMoi') === false) {
            await this.TaoDuLieu(true)
          } else {
            await this.TaoDuLieu(false)
          }
          let request = {
            'vphieu_id': this.phieu_id,
            'vloaihd_id': this.loaihd_id,
            'vhdtb_id': this.hdtb_id,
            'vthuebao_id': this.thuebao_id,
            'vkieu': 1,
            'vsua_vattu': this.vsua_vattu,
            'vdonvi_nhan_id': this.vdonvi_nhan_id,
            'vnhanvien_id': this.nhanvienId,
            'vdonvi_id': this.donviId,
            'vngayht': moment(new Date()).format('DD/MM/YYYY HH:mm:ss'),
            'vrow_vattu_json': JSON.stringify(this.dsGiaoPhieuTb),
            'vloaitbi_id': this.loaitbi_id,
            'vdonvi_tbi': this.donvi_id,
            'vsoluong': Number.parseInt(this.frmThongTinTbi.txtSoluong),
            'vkieutbi_id': this.kieutbi_id,
            'vserial': this.frmThongTinTbi.txtSeri.trim(),
            'vtrangbi_id': this.trangbi_id,
            'vtinhtrang_tbi_id': this.cboTinhTrang.value,
            'vghichu': this.frmThongTinTbi.txtGhiChu.trim()
          }

          let response = await VatTuThueBaoAPI.FN_AN_GHILAI_FRMVATTUTHUEBAO(this.axios, request)
          this.$root.$toast.info(response.data.data)
          await this.HienThiDS()
          if (this.vsua_vattu === 1) {
            await this.HienThiDS_vattu_tb()
          }
        })
      } catch (error) {
        console.log(error)
        this.$root.$toast.error('Có lỗi xảy ra: ', error)
      }
    },
    async POST_GET_KEYS (keyname) {
      let key = ''
      await VatTuThueBaoAPI.POST_GET_KEYS(this.axios, {
        keyname: keyname
      }).then(response => {
        if (response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000') {
          key = response.data.data
        }
      })
      return key
    },
    async TaoDuLieu (themmoi) {
      this.dsGiaoPhieuTb = []
      let row = {}
      if (themmoi) {
        this.loaitb_id = this.cboThietBi.value
        this.kieutbi_id = this.cboKieuThietBi.value
        if (this.cboKieuThietBi.value === 1) {
          this.vattu_id = await this.POST_GET_KEYS('VATTU_ID')
        } else {
          this.vattu_id = 0
        }
        this.rvt_id = await this.POST_GET_KEYS('RVT_ID')
      }
      row.RVT_ID = this.rvt_id
      row.VATTU_ID = this.vattu_id
      row.TINHTRANGTBI_ID = this.cboTinhTrang.value
      row.LOAITBI_ID = this.loaitbi_id
      row.KIEUTBI_ID = this.kieutbi_id
      row.TRANGBI_ID = this.cboTrangBiADSL.value
      row.PHIEU_ID = this.phieu_id
      row.SL = Number.parseInt(this.frmThongTinTbi.txtSoluong)
      row.NGAY_TH = moment(new Date()).format('DD/MM/YYYY HH:mm:ss')
      row.SERI = this.frmThongTinTbi.txtSeri.trim()
      row.NGUOIDUNG_ID = Number.parseInt(this.$root.token.getNguoiDungID())
      row.NHANVIEN_ID = this.nhanvienId
      row.DONVI_ID = this.donviId
      row.NGUOI_CN = this.$root.token.getUserName()
      row.NGAY_CN = moment(new Date()).format('DD/MM/YYYY HH:mm:ss')
      row.MAY_CN = await this.$root.token.getMachine()
      row.IP_CN = await this.$root.token.getIP()
      row.GHICHU = this.frmThongTinTbi.txtGhiChu
      if (themmoi === false) {
        row.LOAITBI_ID = this.loaitb_id
        row.KIEUTBI_ID = this.kieutbi_id
        row.PHIEU_ID = this.phieu_id
      }
      this.dsGiaoPhieuTb.push(row)
    },
    KiemTraDuLieuNhap () {
      if (this.phieu_id === 0) {
        this.$root.$toast.warning('Chưa có thông tin phiếu. Hãy kiểm tra lại!')
        this.$refs.txtSoLuong.focus()
        return false
      }

      if (this.vsua_vattu === 1) {
        if (this.vdonvi_nhan_id !== this.donviId) {
          this.$root.$toast.warning('Phiếu không cùng đơn vị với đơn vị của user. Bạn không được phép cập nhật.')
          return false
        }

        if (this.vngay_ht !== '') {
          let now = new Date()
          let ngayht = new Date(this.vngay_ht)
          if (moment(ngayht).format('MMYYYY') !== moment(now).format('MMYYYY')) {
            let lastmonth = now.setMonth(now.getMonth() - 1)
            if (moment(ngayht).format('YYYYMM') !== moment(lastmonth).format('YYYYMM')) {
              this.$root.$toast.warning('Bạn chỉ được phép thao tác phiếu thực hiện trong tháng hoặc tháng trước')
              return false
            }
          }
        }
      }

      if (this.dichvuvt_id === DichVuVienThong.TSL || this.dichvuvt_id == DichVuVienThong.NOIBO_2DIEM) {
        if (this.vsua_vattu === 0 && this.getStatusBtn('tsbtnNhapMoi') === true) {
          if (this.vdonvi_xuat_vt !== Number.parseInt(this.$root.token.getDonViID())) {
            this.$root.$toast.warning('Bạn không được cập nhật vật tư của đơn vị khác !')
            return false
          }
        }
      }

      if (this.frmThongTinTbi.txtSeri.trim() !== '') {
        if (Number.parseInt(this.frmThongTinTbi.txtSoluong) !== 1) {
          this.$root.$toast.warning('Vật tư này có seri, số lượng phải nhập = 1')
          this.$refs.txtSoLuong.focus()
          return false
        }
      }
      return true
    },
    getStatusBtn (id) {
      return this.actions[this.actions.findIndex(item => item.id === id)].active
    },
    tsbtnExcel_Click () {
      if (this.gridviewVatTu.list.length > 0) {
        let data = XLSX.utils.json_to_sheet(this.gridviewVatTu.list)
        let wb = XLSX.utils.book_new() // make Workbook of Excel
        XLSX.utils.book_append_sheet(wb, data, 'DanhSachVatTu') // sheetAName is name of Worksheet
        // export Excel file
        XLSX.writeFile(wb, 'DanhSachVatTu.xls')
      } else this.$root.$toast.warning('Không có dữ liệu!')
    },
    onActionClick (action) {
      if (action.id === 'tsbtnNhapMoi') {
        this.Clean()
        this.setActiveActions(1)
      } else if (action.id === 'tsbtnGhiLai') {
        this.CapNhat()
      } else if (action.id === 'tsbtnHuyBo') {
        this.setActiveActions(0)
      } else if (action.id === 'tsbtnXoa') {
        this.Xoa_Vattu()
      } else if (action.id === 'tsbtnExcel') {
        this.tsbtnExcel_Click()
      }
    },
    setActiveActions (kieu) {
      this.actions.forEach((item) => {
        if (item.id !== 'tsbtnExcel') {
          item.active = false
        }
      })
      this.cboKieuThietBi.disable = false
      this.cboThietBi.disable = false
      this.cboTinhTrang.disable = false
      if (kieu === -1) { // bat dau
        this.setActiveButton('tsbtnGhiLai', true)
        this.setActiveButton('tsbtnHuyBo', true)
      } else if (kieu === 0) { // bắt đầu
        this.setActiveButton('tsbtnNhapMoi', true)
      } else if (kieu === 1) { // thêm mới
        this.setActiveButton('tsbtnGhiLai', true)
        this.setActiveButton('tsbtnHuyBo', true)
      } else if (kieu === 2) { // Huỷ
        this.setActiveButton('tsbtnNhapMoi', true)
        this.setActiveButton('tsbtnXoa', true)
      } else if (kieu === 3) { // edit
        this.setActiveButton('tsbtnNhapMoi', true)
        this.setActiveButton('tsbtnXoa', true)
        this.setActiveButton('tsbtnGhiLai', true)
        this.setActiveButton('tsbtnHuyBo', true)
        this.cboKieuThietBi.disable = true
        this.cboThietBi.disable = true
      }
    },
    setActiveButton (id, status) {
      let index = this.actions.findIndex(item => item.id === id)
      if (index >= 0) {
        this.actions[index].active = status
      }
    },
    Clean () {
      this.frmThongTinTbi.txtSoluong = 0
      this.frmThongTinTbi.txtSeri = ''
      this.frmThongTinTbi.txtGhiChu = ''
      this.rvt_id = 0
      this.vattu_id = 0
      this.cboTrangBiADSL.value = TRANGBI.VIENTHONG_TRANGBI
    }
  }
}
</script>

<style scoped>
.disabled_color {
  color: gray !important;
}
</style>
