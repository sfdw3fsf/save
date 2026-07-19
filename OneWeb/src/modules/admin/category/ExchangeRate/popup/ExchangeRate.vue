<template>
  <b-modal
    ref="popupExchangeRate"
    size="xl"
    body-class="p-0"
    hide-header
    hide-header-close
    @show="frmTyGiaQuyDoi_Load"
    @hide="handleHide"
    hide-footer>
    <div class="modal-content popup-box">
      <div class="popup-header">
        <div class="title"><span class="icon one-notepad"></span>Thay đổi tỷ giá</div>
        <div class="close -ap icon-close" @click="hideModal"></div>
      </div>
      <ActionTop :actions="actions" @onActionClick="onActionClick"/>
      <div class="popup-body">
        <div class="box-form">
          <div class="legend-title">Thông tin quy đổi</div>
          <div class="row">
            <div class="col-sm-6 col-12">
              <div class="info-row">
                <div class="key w80">Kỳ hóa đơn</div>
                <div class="value">
                  <div class="input-icon-right">
                    <k-date-picker format="MM/YYYY" type="month" v-model="frmThongTinQuyDoi.dtpKyHD"/>
                  </div>
                </div>
              </div>
            </div>
            <div class="col-sm-6 col-12">
              <div class="info-row">
                <div class="key w60">Kỳ cước</div>
                <div class="value">
                  <div class="select-custom">
                    <Select2 ref="cboChuKy" v-model="cboChuKy.value"
                             :options="cboChuKy.list" :settings="settingSelect2"/>
                  </div>
                </div>
              </div>
            </div>
          </div>
          <div class="row">
            <div class="col-sm-6 col-12">
              <div class="info-row">
                <div class="key w80">Loại tiền <span style="color: #dc3545"> (*)</span></div>
                <div class="value">
                  <div class="select-custom">
                    <Select2 ref="cboLoaiTien" v-model="cboLoaiTien.value"
                             :options="cboLoaiTien.list" :settings="settingSelect2"/>
                  </div>
                </div>
              </div>
            </div>
            <div class="col-sm-6 col-12">
              <div class="info-row">
                <div class="key w60">Tỷ giá <span style="color: #dc3545"> (*)</span></div>
                <div class="value">
                  <vue-numeric :disabled="disabledBox.txtTyGia" class="form-control tright" ref="txtTyGia"
                               separator="," v-model="frmThongTinQuyDoi.txtTyGia"
                               placeholder="0" output-type="Number"></vue-numeric>
                </div>
              </div>
            </div>
          </div>
        </div>
        <div class="box-form">
          <div class="legend-title">Danh sách tỷ giá quy đổi</div>
          <div class="table-content">
            <DataGrid
              ref="dtDanhSachTyGia"
              :columns="dgrdsTyGia.headers"
              :data-source="dgrdsTyGia.data"
              :showColumnCheckbox="false"
              :showFilter="true"
              :enabledSelectFirstRow="true"
              @selectedRowChanged="dgrdsTyGia_SelectionChanged"
              :allowPaging="true" :enablePagingServer="false"
            ></DataGrid>
          </div>
        </div>
      </div>
    </div>
  </b-modal>
</template>

<script>
import ActionTop from '@/components/kylq_components/ActionTop.vue'
import 'vue2-datepicker/locale/vi.js'
import 'vue2-datepicker/index.css'
import KDatePicker from '@/components/kylq_components/KDatePicker.vue'
import moment from 'moment'
import ExchangeRateAPI from './ExchangeRateAPI'

export default {
  name: 'ExchangeRate',
  data () {
    return {
      header: {
        title: 'Tỷ giá quy đổi'
      },
      actions: [
        {
          id: 'tsbtnNhapMoi',
          name: 'Nhập mới',
          active: true,
          icon_class: 'nc-icon-glyph ui-1_circle-add'
        },
        {
          id: 'tsbtnGhiLai',
          name: 'Ghi lại',
          active: true,
          icon_class: 'one-save'
        },
        {
          id: 'tsbtnHuyBo',
          name: 'Hủy',
          active: true,
          icon_class: 'nc-icon-glyph ui-1_circle-remove'
        },
        {
          id: 'tsbtnXoa',
          name: 'Xóa',
          active: true,
          icon_class: 'one-trash'
        }
      ],
      datasource: [],
      waterMaskConfig: {
        waterMarkLoaiTien: 'Chọn loại tiền',
        waterMarkKyCuoc: 'Chọn kỳ cước'
      },
      frmThongTinQuyDoi: {
        dtpKyHD: moment(new Date()).format('MM/YYYY'),
        selectKyCuoc: '0',
        selectLoaiTien: 0,
        txtTyGia: 0
      },
      disabledBox: {
        txtTyGia: false
      },
      options: {
        optionsLoaiTien: [],
        optionsKyCuoc: []
      },
      settingSelect2: {
        language: 'vi'
      },
      cboLoaiTien: {
        list: [],
        disable: false,
        value: 0
      },
      cboChuKy: {
        list: [],
        disable: false,
        value: 0
      },
      kyhoadon: '',
      thangnam: 0,
      dsTyGIA: [],
      maycn: '',
      nguoicn: '',
      dgrdsTyGia: {
        data: [],
        headers: [
          {
            fieldName: 'ky_hd',
            headerText: 'Kỳ hóa đơn',
            allowFiltering: true,
            allowSorting: false,
            visible: true
          },
          {
            fieldName: 'kyhoadon',
            headerText: 'Kỳ hóa đơn ck',
            allowFiltering: true,
            allowSorting: false,
            visible: false
          },
          {
            fieldName: 'thangnam',
            headerText: 'aw',
            allowFiltering: true,
            allowSorting: false,
            visible: false
          },
          {
            fieldName: 'viettat',
            headerText: 'Tiền',
            allowFiltering: true,
            allowSorting: false,
            visible: true
          },
          {
            fieldName: 'tenloai',
            headerText: 'Loại tiền',
            allowFiltering: true,
            allowSorting: false,
            visible: true
          },
          {
            fieldName: 'tygia',
            headerText: 'Quy đổi VND',
            allowFiltering: true,
            textAlign: 'left',
            allowSorting: false,
            visible: true
          },
          {
            fieldName: 'loaitien_id',
            headerText: 'Mã loại tiền',
            allowFiltering: true,
            allowSorting: false,
            textAlign: 'left',
            visible: false
          }
        ],
        selected: 0,
        checked: [],
        selectItem: null
      }
    }
  },
  components: {
    ActionTop,
    KDatePicker
  },
  watch: {
    'frmThongTinQuyDoi.dtpKyHD': async function (newValue) {
      let param = this.formatDate(this.getDate(newValue, 'MM/YYYY'))
      console.log(param)
      await this.HT_Cbo_ChuKyNo_Theo_kyhoadon(param)
    },
    'cboLoaiTien.value': function (newValue) {
      if (newValue == 1) {
        this.disabledBox.txtTyGia = false
      }
    }
  },
  mounted () {
  },
  methods: {
    async dgrdsTyGia_SelectionChanged (item) {
      try {
        if (item != null) {
          console.log(item)
          this.dgrdsTyGia.selectItem = item
          this.frmThongTinQuyDoi.dtpKyHD = item.ky_hd.trim()
          this.cboLoaiTien.value = item.loaitien_id
          this.cboChuKy.value = item.kyhoadon.toString().substr(4, 2).toString()
          this.frmThongTinQuyDoi.txtTyGia = item.tygia
          if (item.thangnam) {
            this.thangnam = Number.parseInt(item.thangnam)
          }
          this.setActiveActions(3)
        }
      } catch (error) {
        console.log(error)
        this.$root.$toast.error('Có lỗi: ', error.message)
      }
    },
    tsbtnXoa_Click () {
      this.$confirm('Bạn có chắc chắn muốn xóa tỷ giá không ?', 'Thông báo', {
        confirmButtonText: 'Có',
        cancelButtonText: 'Không'
      }).then(async () => {
        let loaitienId = this.dgrdsTyGia.selectItem.loaitien_id
        await ExchangeRateAPI.FN_TYGIA_DELETE(this.axios, {
          'loaitien_id': loaitienId,
          'thangnam': this.thangnam
        }).then(response => {
          if (response && response.data && response.data.error_code === 'BSS-00000000') {
            this.$root.$toast.success('Xóa tỷ giá trên dữ liệu viễn thông tỉnh thành công !')
            this.frmTyGiaQuyDoi_Load()
            this.setActiveActions(1)
          } else {
            this.$root.$toast.error('Xóa tỷ giá thất bại: ', response.data.message)
          }
        }).catch(error => {
          console.log(error)
          this.$root.$toast.error('Không thể xóa bản ghi này ' + error.data.message_detail)
        })
      })
    },
    async tsbtnGhiLai_Click () {
      try {
        if (this.KiemTraKyHd(this.kyhoadon.trim(), this.frmThongTinQuyDoi.dtpKyHD.trim())) {
          this.$root.$toast.warning('Kỳ hóa đơn nhập vào không được lớn hơn kỳ hóa đơn hiện tại :' + this.kyhoadon)
          return
        }

        if (this.frmThongTinQuyDoi.txtTyGia === 0) {
          this.$root.$toast.warning('Tỷ giá không được để trống !')
          return
        }

        if (this.cboLoaiTien.value === 0) {
          this.$root.$toast.warning('Loại tiền không được để trống !')
          return
        }
        if (this.actions[this.actions.findIndex(x => x.id === 'tsbtnNhapMoi')].active === false) {
          let ds = await this.SP_DS_TYGIA()
          if (ds.length > 0) {
            for (let i = 0; i < ds.length; i++) {
              if ((this.frmThongTinQuyDoi.dtpKyHD.trim() === ds[i].ky_hd.trim()) && (this.cboLoaiTien.value == ds[i].loaitien_id)) {
                this.$root.$toast.warning('Nhập trùng dữ liệu !')
                return
              }
            }
          }
          this.TaoDuLieu(true)
          await this.SP_TYGIA_UPDATE(1)
          await this.frmTyGiaQuyDoi_Load()
          this.setActiveActions(3)
        } else {
          this.TaoDuLieu(false)
          let ds = await this.SP_DS_TYGIA()
          if (ds.length > 0) {
            let currentRowIdx = this.dgrdsTyGia.data.findIndex(item => item.tygia == this.dgrdsTyGia.selectItem.tygia)
            for (let i = 0; i < currentRowIdx; i++) {
              if ((this.dgrdsTyGia.selectItem.ky_hd.trim() === ds[i].ky_hd.toString().trim()) && (this.cboLoaiTien.value == ds[i].loaitien_id)) {
                this.$root.$toast.warning('Nhập trùng dữ liệu')
                return
              }
            }
            for (let j = currentRowIdx + 1; j < ds.length; j++) {
              if ((this.frmThongTinQuyDoi.dtpKyHD.trim() === ds[j].ky_hd.trim()) && (this.cboLoaiTien.value == ds[j].loaitien_id)) {
                this.$root.$toast.warning('Nhập trùng dữ liệu !')
                return
              }
            }
          }
          await this.SP_TYGIA_UPDATE(0)
          await this.frmTyGiaQuyDoi_Load()
          this.setActiveActions(3)
        }
      } catch (error) {
        console.log(error)
        this.$root.$toast.error('Có lỗi xảy ra khi thực hiện cập nhật tỷ giá', error.message)
      }
    },
    async LAY_KY_CUOC_HIEN_HANH () {
      await ExchangeRateAPI.LAY_KY_CUOC_HIEN_HANHN_HANH(this.axios).then(response => {
        if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
          this.kyhoadon = response.data.data[0].KYCUOC.toString()
        }
      })
    },
    KiemTraKyHd (kyhd, newKyHd) {
      const newkyhdstr = '01/' + newKyHd
      const date1 = this.getDate(kyhd, 'YYYYMMDD')
      const date2 = this.getDate(newkyhdstr, 'DD/MM/YYYY')
      return date1 < date2
    },
    TaoDuLieu (themmoi) {
      this.dsTyGIA = []
      const row = {}
      let nguoiCn = this.$root.token.getUserName()
      let kyhd = this.frmThongTinQuyDoi.dtpKyHD.split('/')
      row.THANGNAM = Number.parseInt(kyhd[1]) * 13 + Number.parseInt(kyhd[0])
      row.TYGIA = this.frmThongTinQuyDoi.txtTyGia
      row.LOAITIEN_ID = Number(this.cboLoaiTien.value)
      row.NGUOINHAP = nguoiCn
      row.NGUOISUA = nguoiCn
      row.CNTUMAY = this.maycn
      let kyhoadon
      let ck = this.cboChuKy.value
      if (ck.length < 2) {
        ck = '0' + ck
      }
      kyhoadon = Number.parseInt(kyhd[1] + kyhd[0] + ck)
      row.KYHOADON = kyhoadon
      this.dsTyGIA.push(row)
    },
    async SP_DS_KYCUOC (thangnam) {
      let ds = []
      await ExchangeRateAPI.SP_DS_KYCUOC(this.axios, {
        namthang: thangnam
      }).then(response => {
        if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
          ds = response.data.data
        }
      })
      return ds
    },
    async SP_TYGIA_UPDATE (kieu) {
      await ExchangeRateAPI.SP_TYGIA_UPDATE(this.axios, {
        phanvung_id: this.$root.token.getPhanVungID(),
        is_insert: kieu,
        data: JSON.stringify(this.dsTyGIA[0])
      }).then(response => {
        if (response && response.data && response.data.error_code === 'BSS-00000000') {
          if (kieu === 1) {
            this.$root.$toast.success('Thêm mới tỷ giá quy đổi vào cơ sở dữ liệu tỉnh thành công !')
          } else {
            this.$root.$toast.success('Cập nhật tỷ giá quy đổi vào cơ sở dữ liệu tỉnh thành công !')
          }
        } else {
          this.$root.$toast.error('Có lỗi xảy ra khi lưu thông tin tỷ giá:', response.data.message_detail)
        }
      }).catch(error => {
        console.log(error)
        this.$root.$toast.error('Có lỗi xảy ra khi lưu thông tin tỷ giá: ', error.data.message_detail)
      })
    },
    async SP_DS_LOAITIEN () {
      let ds = []
      try {
        this.loading(true)
        await ExchangeRateAPI.SP_DS_LOAITIEN(this.axios, {}).then(response => {
          if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data && response.data.data.length > 0) {
            ds = response.data.data
          }
        })
      } catch (error) {
        this.$root.$toast.error('Có lỗi xảy ra khi load danh sách loại tiền')
      } finally {
        this.loading(false)
      }
      return ds
    },
    async HT_Cbo_ChuKyNo_Theo_kyhoadon (dtpKyHD) {
      let ds = await this.SP_DS_KYCUOC(dtpKyHD)
      if (ds.length > 0) {
        this.cboChuKy.list = ds.map(item => {
          return {
            id: item.chuky,
            text: item.chuky
          }
        })
        this.cboChuKy.value = this.cboChuKy.list[0].id
      } else {
        this.cboChuKy.list = []
      }
    },
    async SP_DS_TYGIA () {
      let ds = []
      try {
        this.loading(true)
        await ExchangeRateAPI.SP_DS_TYGIA(this.axios, {}).then(response => {
          if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data && response.data.data.length > 0) {
            ds = response.data.data
          }
        })
      } catch (error) {
        this.$root.$toast.error('Có lỗi xảy ra khi load danh sách tỷ giá')
      } finally {
        this.loading(false)
      }
      return ds
    },
    showModal () {
      this.$refs['popupExchangeRate'].show()
    },
    async frmTyGiaQuyDoi_Load () {
      try {
        this.loading(true)
        await this.LAY_KY_CUOC_HIEN_HANH()
        if (!this.kyhoadon) {
          this.$root.$toast.warning('Tham số: kỳ hóa đơn chưa được thiết lập trong hệ thống. Bạn hãy liên hệ admin để cấu hình trước khi làm việc ! !')
          this.hideModal()
          return
        }
        let dsLoaiTien = await this.SP_DS_LOAITIEN()
        dsLoaiTien = dsLoaiTien.filter(item => item.loaitien_id.toString() !== '1')
        if (dsLoaiTien.length > 0) {
          this.cboLoaiTien.list = dsLoaiTien.map(item => {
            return {
              id: item.loaitien_id,
              text: item.tenloai
            }
          })
        } else {
          this.cboLoaiTien.list = []
        }
        let dsTyGia = await this.SP_DS_TYGIA()
        if (dsTyGia.length > 0) {
          this.dgrdsTyGia.data = dsTyGia
        } else {
          this.dgrdsTyGia.data = []
          this.$root.$toast.warning('Không có dữ liệu về tỷ giá quy đổi')
        }
        this.maycn = await this.$root.token.getMachine()
      } catch (error) {
        console.log(error)
        this.$root.$toast.error('Có lỗi: ', error.message)
      } finally {
        this.loading(false)
      }
    },
    hideModal () {
      this.$refs['popupExchangeRate'].hide()
    },
    handleHide () {
      this.currentTyGia = {}
      this.dgrdsTyGia.data = []
      this.frmThongTinQuyDoi.dtpKyHD = moment(new Date()).format('MM/YYYY')
      this.frmThongTinQuyDoi.selectKyCuoc = null
      this.frmThongTinQuyDoi.selectLoaiTien = null
      this.frmThongTinQuyDoi.txtTyGia = 0
      this.options.optionsKyCuoc = []
      this.options.optionsLoaiTien = []
    },
    tsbtnNhapMoi_Click () {
      this.setActiveActions(1)
      this.cboLoaiTien.value = 0
      this.frmThongTinQuyDoi.txtTyGia = 0
    },
    tsbtnHuyBo_Click () {
      this.setActiveActions(2)
      this.cboLoaiTien.value = 0
      this.frmThongTinQuyDoi.txtTyGia = 0
    },
    onActionClick (action) {
      if (action.id === 'tsbtnNhapMoi') {
        this.tsbtnNhapMoi_Click()
      } else if (action.id === 'tsbtnGhiLai') {
        this.tsbtnGhiLai_Click()
      } else if (action.id === 'tsbtnHuyBo') {
      } else if (action.id === 'tsbtnXoa') {
        this.tsbtnXoa_Click()
      }
    },
    setActiveActions (kieu) {
      this.actions.forEach((item) => {
        item.active = false
      })
      if (kieu === -1) { // bat dau
        this.actions[this.actions.findIndex(x => x.id === 'tsbtnGhiLai')].active = true
        this.actions[this.actions.findIndex(x => x.id === 'tsbtnHuyBo')].active = true
      } else if (kieu === 0) { // bắt đầu
        this.actions[this.actions.findIndex(x => x.id === 'tsbtnNhapMoi')].active = true
      } else if (kieu === 1) { // thêm mới
        this.actions[this.actions.findIndex(x => x.id === 'tsbtnGhiLai')].active = true
        this.actions[this.actions.findIndex(x => x.id === 'tsbtnHuyBo')].active = true
      } else if (kieu === 2) { // Huỷ
        this.actions[this.actions.findIndex(x => x.id === 'tsbtnNhapMoi')].active = true
        this.actions[this.actions.findIndex(x => x.id === 'tsbtnXoa')].active = true
      } else if (kieu === 3) { // edit
        this.actions.forEach((item) => {
          item.active = true
        })
      }
    },
    formatDate (date, format) {
      if (format) {
        return moment(date).format(format)
      } else {
        return moment(date).format('YYYYMM')
      }
    },
    getDate (dateStr, format) {
      if (format) {
        return moment(dateStr, format).toDate()
      } else {
        return moment(dateStr, 'DDMMYYYY').toDate()
      }
    }
  }
}
</script>

<style scoped>
.disabled_color {
  color: gray !important;
}
</style>
