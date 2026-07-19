<template>
  <div class="modal-dialog" style="width: 100%; max-width: 1220px;">
    <div class="modal-content popup-box">
      <div class="list-actions-top">
        <ul class="list">
          <li>
            <a @click="onSendSMS">
              <span class="icon one-email"></span> Gửi SMS
            </a>
          </li>
          <li>
            <a @click="onClose()">
              <span class="icon nc-icon-glyph ui-1_circle-remove"></span> Hủy bỏ
            </a>
          </li>
        </ul>
      </div>
      <div class="popup-body">
        <div class="row">
          <div class="col-sm-6 col-12">
            <div class="box-form">
              <div class="info-row">
                <div class="key w90">Số điện thoại</div>
                <div class="value">
                  <bss-error-marker :has-error="$v.filterForm.soDienThoai.$error">
                    <input type="text" class="form-control" ref="soDienThoaiRef" v-model.trim="filterForm.soDienThoai">
                  </bss-error-marker>
                </div>
              </div>
              <div class="relative form-group">
                <bss-error-marker :has-error="$v.filterForm.noiDung.$error">
                  <textarea
                    class="form-control"
                    style="height:150px;resize:none"
                    cols="30"
                    rows="10"
                    ref="noiDungRef"
                    :maxlength="noiDungMaxLength"
                    v-model.trim="filterForm.noiDung"
                  />
                </bss-error-marker>
                <div class="form-control h-auto absolute" style="width: auto;bottom: 5px;right: 5px;">
                  <span class="text-main">{{ noiDungRemainingLength }}</span> / {{ noiDungMaxLength }}
                </div>
              </div>
              <div class="info-row">
                <div class="key w30 padt0">
                  <span class="-ap icon-info f20 text-main"></span>
                </div>
                <div class="value red fw6 lh24">
                  <p>Lưu ý: <br/>
                    Có thể gửi tin nhắn tới nhiều số cùng lúc, để gửi tin nhập các số điện thoại phân cách nhau bởi dấu chấm phẩy “;”<br />
                    Nháy đúp vào một dòng trên danh bạ để thêm số điện thoại vào danh sách
                    cần gửi</p>
                </div>
              </div>
            </div>
          </div>
          <div class="col-sm-6 col-12">
            <div class="box-form">
              <div class="legend-title">Danh bạ</div>
              <div class="table-content">
                <DataGrid
                  :columns="danhBa.headers"
                  :dataSource="danhBa.dataSources"
                  :showColumnCheckbox="false"
                  :enable-paging-server="false"
                  :allowPaging="true"
                  :showFilter="true"
                  @rowDoubleClicked="rowDoubleClicked"
                />
              </div>
            </div>
          </div>
        </div>
        <div class="box-form">
          <div class="legend-title">Danh sách tin nhắn đã gửi</div>
          <div class="table-content">
            <DataGrid
              :columns="tinNhanDaGui.headers"
              :dataSource="tinNhanDaGui.dataSources"
              :showColumnCheckbox="false"
              :enable-paging-server="false"
              :allowPaging="true"
              :showFilter="true"
              @selectedRowChanged="onSelectedRowChanged"
            />
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { required } from 'vuelidate/lib/validators'
import BssErrorMarker from '@/components/BssErrorMarker'
import api from '../api'

const tinhThiCongDefault = {
  tns: '',
  user: '',
  pass: '',
  tenTinh: '',
  maTinh: '',
  nhanVienId: null,
  donViId: null,
  tinhThiCongId: 0,
  maVung: '',
  ipDll: ''
}

export default {
  name: 'PopupGuiSms',
  components: { BssErrorMarker },
  props: {
    hdtbId: {
      type: Number,
      required: false,
      default: 0
    },
    nhomId: {
      type: Number,
      required: false,
      default: 0
    },
    tienTrinhTCId: {
      type: Number,
      required: false,
      default: 0
    },
    maTB: {
      type: String,
      required: false,
      default: ''
    },
    noiDung: {
      type: String,
      required: false,
      default: ''
    },
    baoHongId: {
      type: Number,
      required: false,
      default: 0
    },
    tienTrinhBHId: {
      type: Number,
      required: false,
      default: 0
    },
    tinhThiCong: {
      type: Object,
      required: false,
      default: () => tinhThiCongDefault
    },
    baoHongRow: {
      type: Object,
      required: false,
      default: () => {}
    }
  },
  data () {
    return {
      filterForm: {
        soDienThoai: '',
        noiDung: '',
        hdtbId: null,
        nhomId: null,
        tienTrinhTCId: null,
        maTB: '',
        baoHongId: null,
        tienTrinhBHId: null
      },
      danhBa: {
        headers: [
          { fieldName: 'so_dt', headerText: 'Số điện thoại', allowFiltering: true, visible: true },
          { fieldName: 'ten_nv', headerText: 'Tên người nhận', allowFiltering: true, visible: true },
          { fieldName: 'cap_tn', headerText: 'Cấp TN', allowFiltering: true, visible: false },
          { fieldName: 'chucdanh', headerText: 'Chức danh', allowFiltering: true, visible: false }
        ],
        dataSources: []
      },
      tinNhanDaGui: {
        headers: [
          { fieldName: 'nguoi_cn', headerText: 'Người gửi', allowFiltering: true },
          { fieldName: 'ngay_cn', headerText: 'Ngày gửi', allowFiltering: true },
          { fieldName: 'so_dt', headerText: 'Số điện thoại', allowFiltering: true },
          { fieldName: 'noidung', headerText: 'Nội dung', allowFiltering: true }
        ],
        dataSources: []
      },
      vNhomId: null
    }
  },
  validations: {
    filterForm: {
      soDienThoai: { required },
      noiDung: { required }
    }
  },
  computed: {
    noiDungMaxLength () {
      return 158 - this.filterForm.maTB.length
    },
    noiDungRemainingLength () {
      return this.noiDungMaxLength - this.filterForm.noiDung.length
    }
  },
  methods: {
    async onInit () {
      this.$v.$reset()
      this.setData()
      await this.getSoDienThoaiGuiSMS()
      await this.onGetDanhBa()
      await this.getDanhSachTinNhanDaGui()
    },
    setData () {
      this.$nextTick(() => {
        this.filterForm.noiDung = this.noiDung || ''
        this.filterForm.hdtbId = this.hdtbId
        this.filterForm.nhomId = this.nhomId
        this.filterForm.tienTrinhTCId = this.tienTrinhTCId
        this.filterForm.maTB = (this.isExistObject(this.baoHongRow) && this.baoHongRow.ma_tb) || this.maTB
        this.filterForm.baoHongId = this.baoHongId
        this.filterForm.tienTrinhBHId = this.tienTrinhBHId
      })
      if (this.filterForm.hdtbId === 0 && this.filterForm.nhomId === 0) this.vNhomId = 2
      else if (this.filterForm.hdtbId === 0 && this.filterForm.nhomId === 2) this.vNhomId = 2
      else this.vNhomId = 1
      console.log(this.filterForm, this.tinhThiCong, this.baoHongRow)
    },
    isExistObject (object) {
      return object && Object.getPrototypeOf(object) === Object.prototype && Object.keys(object).length > 0
    },
    async getSoDienThoaiGuiSMS () {
      this.loading(true)
      try {
        const response = await api.getSoDienThoaiGuiSMS(
          this.axios,
          { nhom_id: this.vNhomId, tinhthicong_id: this.tinhThiCong.tinhThiCongId }
        )
        const data = await response.data
        if (data.error_code === 'BSS-00000000' && data.error === '200') {
          const danhSachSoDT = data.data.map(item => item.so_dt)
          this.filterForm.soDienThoai = danhSachSoDT.join(';')
        } else {
          this.filterForm.soDienThoai = ''
        }
      } catch (e) {
        this.$root.$toast.error('Không thể lấy thông tin số điện thoại')
      } finally {
        this.loading(false)
      }
    },
    async onGetDanhBa () {
      this.onSetColumnVisible('cap_tn', false)
      const danhBa = await this.getDanhSachDanhBa()
      if (danhBa) {
        if (danhBa.rs2.some(item => item.hasOwnProperty('error'))) {
          this.$root.$toast.warning(danhBa.rs2[0].error)
          this.danhBa.dataSources = []
          return false
        }
        const rs2 = danhBa.rs2.map(rs => ({ ten_nv: rs.ten_nv, so_dt: rs.so_dt, ten_dv: rs.ten_dv }))
        this.danhBa.dataSources = [...danhBa.rs1, ...rs2]
      }
      if (this.tinhThiCong.tinhThiCongId === 0 && this.isExistObject(this.baoHongRow) && this.baoHongRow.baohong_id) {
        let dataSources = [...this.danhBa.dataSources]
        dataSources = dataSources.map(danhBa => ({ ...danhBa, cap_tn: 'Cấp thường' }))
        this.onSetColumnVisible('cap_tn', true)
        const danhSachDanhBaBaoHong = await this.getDanhSachDanhBaBaoHong()
        if (danhSachDanhBaBaoHong.length > 0) {
          const keys = Object.keys(dataSources[0])
          const keysDanhBaBaoHong = Object.keys(danhSachDanhBaBaoHong[0])
          const duplicates = keysDanhBaBaoHong.filter(val => keys.includes(val))
          for (const dataSource of dataSources) {
            const data = {}
            for (const key of duplicates) {
              data[key] = dataSource[key]
            }
            danhSachDanhBaBaoHong.push(data)
          }
        }
        this.danhBa.dataSources = danhSachDanhBaBaoHong
        this.onSetColumnVisible('chucdanh', danhSachDanhBaBaoHong.some(item => item.hasOwnProperty('chucdanh')))
      }
    },
    async getDanhSachDanhBa () {
      this.loading(true)
      try {
        const response = await api.getDanhSachDanhBa(
          this.axios,
          {
            baohong_id: this.filterForm.baoHongId,
            hdtb_id: this.filterForm.hdtbId,
            nhom_id: this.vNhomId,
            tinhthicong_id: this.tinhThiCong.tinhThiCongId
          }
        )
        const data = await response.data
        if (data.error_code === 'BSS-00000000' && data.error === '200') {
          return data.data
        } else {
          return null
        }
      } catch (e) {
        this.$root.$toast.error('Không thể lấy danh sách danh bạ')
      } finally {
        this.loading(false)
      }
    },
    onSetColumnVisible (fieldName, isVisible) {
      this.danhBa.headers.find(header => header.fieldName === fieldName).visible = isVisible
    },
    async getDanhSachDanhBaBaoHong () {
      this.loading(true)
      try {
        const response = await api.getDanhSachDanhBaBaoHong(
          this.axios,
          { baoHongId: this.baoHongRow.baohong_id, loaiTN: 24 }
        )
        const data = await response.data
        if (data.error_code === 'BSS-00000000' && data.error === '200') {
          return data.data
        } else {
          return []
        }
      } catch (e) {
        this.$root.$toast.error('Không thể lấy danh sách danh bạ')
      } finally {
        this.loading(false)
      }
    },
    async getDanhSachTinNhanDaGui () {
      this.loading(true)
      try {
        const response = await api.getDanhSachTinNhanDaGui(
          this.axios,
          {
            nhom_id: this.vNhomId,
            tientrinhbh_id: this.filterForm.tienTrinhBHId,
            tientrinhtc_id: this.filterForm.tienTrinhTCId,
            tinhthicong_id: this.tinhThiCong.tinhThiCongId
          }
        )
        const data = await response.data
        if (data.error_code === 'BSS-00000000' && data.error === '200') {
          this.tinNhanDaGui.dataSources = data.data
        } else {
          this.tinNhanDaGui.dataSources = []
        }
      } catch (e) {
        this.$root.$toast.error('Không thể lấy danh sách danh bạ')
      } finally {
        this.loading(false)
      }
    },
    async onSendSMS () {
      if (this.isValid()) {
        if (this.filterForm.noiDung.length > this.noiDungMaxLength) {
          this.$root.$toast.warning(`Nội dung không được vượt quá ${this.noiDungMaxLength} ký tự`)
          this.$refs.noiDungRef.focus()
          return false
        }
        const danhSachSDT = this.filterForm.soDienThoai.split(';')
        if (danhSachSDT.some(soDt => !this.isNumeric(soDt))) {
          this.$root.$toast.warning('Số điện thoại nhập vào không đúng')
          this.$refs.soDienThoaiRef.focus()
          return false
        }
        await this.sendSMS()
      }
    },
    isNumeric (value) {
      return !isNaN(value) && !isNaN(parseFloat(value))
    },
    isValid () {
      this.$v.$touch()
      if (this.$v.$invalid && !this.$v.filterForm.soDienThoai.required) {
        this.$root.$toast.warning('Số điện thoại đang bị trống')
        this.$refs.soDienThoaiRef.focus()
        return false
      }
      if (this.$v.$invalid && !this.$v.filterForm.noiDung.required) {
        this.$root.$toast.warning('Nội dung đang bị trống')
        this.$refs.noiDungRef.focus()
        return false
      }
      return true
    },
    async sendSMS () {
      this.loading(true)
      try {
        const response = await api.sendSMS(
          this.axios,
          {
            ma_tb: this.filterForm.maTB,
            nhom_id: this.vNhomId,
            noidung: this.filterForm.noiDung,
            sdt: this.filterForm.soDienThoai,
            tientrinhbh_id: this.filterForm.tienTrinhBHId,
            tientrinhtc_id: this.filterForm.tienTrinhTCId,
            tinhthicong_id: this.tinhThiCong.tinhThiCongId
          }
        )
        const data = await response.data
        if (data.error_code === 'BSS-00000000' && data.error === '200') {
          this.$root.$toast.success('Gửi tin nhắn thành công')
          this.onClose()
        } else {
          this.$root.$toast.error('Gửi tin nhắn không thành công')
        }
      } catch (e) {
        this.$root.$toast.error('Không thể gửi tin nhắn')
      } finally {
        this.loading(false)
      }
    },
    onClose () {
      this.$emit('onClose')
    },
    rowDoubleClicked (index) {
      if (this.danhBa.dataSources.length > 0) {
        const soDienThoai = this.danhBa.dataSources[index].so_dt
        if (this.filterForm.soDienThoai) {
          const danhSachSDT = this.filterForm.soDienThoai.split(';')
          if (!danhSachSDT.includes(soDienThoai)) this.filterForm.soDienThoai += `;${soDienThoai}`
        } else {
          this.filterForm.soDienThoai = soDienThoai
        }
      }
    },
    onSelectedRowChanged (data) {
      if (data) {
        this.filterForm.soDienThoai = data.so_dt
        const idx = data.noidung.indexOf(': ')
        if (idx > -1) this.filterForm.noiDung = data.noidung.substring(idx + 2)
      }
    }
  }
}
</script>
