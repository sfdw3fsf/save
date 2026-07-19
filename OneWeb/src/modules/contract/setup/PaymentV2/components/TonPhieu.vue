<template>
  <ejs-dialog :enableResize="true" :allowDragging="true" :visible="false" :animationSettings="animationSettings" ref="dialogObjPhieuTon" :position="position" :header="'Danh sách phiếu tồn'" showCloseIcon="true" width="80%" :target="targetTop" :close="phieuTonModalDlgClose">
    <div class="main-wrapper-popup">
      <div class="list-actions-top">
        <ul class="list">
          <li>
            <a href="#" @click.prevent="nhapMoi" v-bind:class="{ disabled: !tsbtnNhapMoi.Enabled }"> <span class="icon one-search"></span>Nhập mới </a>
          </li>
          <li>
            <a href="#" @click.prevent="tsbtnGhiLai_Click" v-bind:class="{ disabled: !tsbtnGhiLai.Enabled }"> <span class="icon one-save"></span>Ghi lại </a>
            <!-- <a href="#" @click.prevent="updatePhieuTon" v-bind:class="{ disabled: !tsbtnGhiLai.Enabled }"> <span class="icon one-save"></span>Ghi lại </a> -->
          </li>
          <li>
            <a href="#" @click.prevent="xoaPhieuTon" v-bind:class="{ disabled: !tsbtnXoa.Enabled }"> <span class="icon one-trash"></span>Xoá phiếu </a>
          </li>
          <li>
            <a href="#" @click.prevent="thoat"> <span class="icon ui-1_circle-remove nc-icon-glyph"></span> Thoát</a>
          </li>
        </ul>
      </div>
      <div class="popup-body">
        <div class="box-form">
          <div class="legend-title">Thông tin tìm kiếm</div>
          <div class="row">
            <div class="col-sm-12 col-12">
              <div class="info-row">
                <div class="key w90">Nhóm tồn</div>
                <div class="value">
                  <Select2 v-model="nhomTonId" :options="DmNhomTon" />
                </div>
              </div>
            </div>
          </div>
          <div class="row">
            <div class="col-sm-12 col-12">
              <div class="info-row">
                <div class="key w90">Lý do tồn</div>
                <div class="value">
                  <Select2 v-model="lydoTonId" :options="DmLyDoTon" ref="reflydoTonId" />
                </div>
              </div>
            </div>
          </div>
          <div class="row">
            <div class="col-sm-12 col-12">
              <div class="info-row">
                <div class="key w90">Ghi chú</div>
                <div class="value">
                  <input type="text" class="form-control" v-model="txtGhiChu" ref="reftxtGhiChu" maxlength="250" />
                </div>
              </div>
            </div>
          </div>
          <div class="legend-title mart20">
            <div class="pull-left">Danh sách tồn</div>
            <div class="clearfix"></div>
          </div>
          <DataGrid
            ref="gridDsTon"
            :height="500"
            v-bind:columns="DsTon.headers"
            v-bind:dataSource="DsTon.data"
            :showColumnCheckbox="false"
            :enable-paging-server="false"
            :allowPaging="true"
            :showFilter="true"
            :enabledSelectFirstRow="false"
            @rowClicked="SelectPhieuTonItem"
            @selectedItemsChanged="grid_selectedItemsChanged"
            @selectedRowChanged="grid_selectedRowChanged"
          >
          </DataGrid>
        </div>
      </div>
    </div>
  </ejs-dialog>
</template>
<script>
import { TrangThaiHD } from '@/const/enums/index.js'
import moment from 'moment'
import api from './ApiPhieuTon'
export default {
  name: 'TonPhieu',
  components: {
    api,
    moment
  },
  props: {
    phieuId: {
      default: 0,
      type: Number
    },
    trangthaiId: {
      default: 0,
      type: Number
    },
    kieu: {
      default: 0,
      type: Number
    },
    vthuhoi: {
      default: 0,
      type: Number
    }
  },
  data() {
    return {
      DsTon: {
        headers: [
          {
            fieldName: 'lydoton_tc',
            headerText: 'Lý do tồn',
            allowFiltering: true,
            allowSorting: false
          },
          {
            fieldName: 'ngay_bt',
            headerText: 'Ngày báo tồn',
            allowFiltering: true,
            allowSorting: false
          },
          {
            fieldName: 'ten_nv',
            headerText: 'Nhân viên báo tồn',
            allowFiltering: true,
            allowSorting: false
          }
        ],
        data: [],
        selectItem: {},
        selectedItems: [],
        currentIndex: 0
      },
      PhieuId: 0,
      TrangThaiHdId: 0,
      Loading: false,
      targetTop: '#app .main-wrapper', // .main-wrapper-payment
      animationSettings: { effect: 'Zoom' },
      position: { X: 'center', Y: 'top' },
      dai_700: false,
      dsPhieu: [],
      Fn: {
        Save: true,
        AddNew: false,
        Delete: false
      },
      tsbtnGhiLai: {
        Enabled: true
      },
      tsbtnNhapMoi: {
        Enabled: true
      },
      tsbtnXoa: {
        Enabled: true
      },
      DmNhomTon: [],
      DmLyDoTon: [],
      LyDoTons: [],
      nhomTonId: '',
      lydoTonId: '',
      txtGhiChu: '',
      loaded: false
    }
  },
  created() {
    this.Loading = true
    this.PhieuId = this.phieuId
    this.TrangThaiHdId = this.trangthaiId
    if (this.vkieu != null && this.vkieu >= 0) {
      this.kieu = this.vkieu
    }
    if (this.phieuId > 0 && this.dsPhieu.filter((x) => x == this.phieuId).length == 0) this.dsPhieu.push(this.phieuId)
    this.SetButton(-1)
    if (this.vthuhoi == 0) {
      if (this.TrangThaiHdId == TrangThaiHD.TTVT_XACMINH_NO) {
      } else {
      }
    } else {
    }
    try {
      Promise.all([this.getDsNhomTon() /*, this.sp_ds_giaophieu_ton_load()*/])
        .then((values) => {
          this.Loading = false
        })
        .catch((error) => {
          this.Loading = false
          console.log(`Error in promises ${error}`)
        })
      this.Loading = false
    } catch (ex) {
      this.Loading = false
    }
    if (this.kieu == '1') {
      this.tsbtnGhiLai.Enabled = false
      this.tsbtnNhapMoi.Enabled = false
      this.tsbtnXoa.Enabled = false
    }
  },
  mounted() {},
  watch: {
    nhomTonId: async function (val) {
      try {
        this.Loading = true
        //await this.getDsLyDoTon(val)
        this.DmLyDoTon = this.LyDoTons.filter((x) => x.nhomton_id == this.nhomTonId).map((x) => ({
          id: x.lydotontc_id,
          text: x.lydoton_tc == null ? '' : x.lydoton_tc
        }))

        this.Loading = false
      } catch (ex) {
        this.Loading = false
      }
    },
    Loading: function (val, oldval) {
      this.$root.showLoading(val)
    }
  },
  computed: {},
  methods: {
    phieuTonModalDlgClose: function () {
      this.DsTon.data = []
      this.loaded = false
      this.$emit('close')
    },
    thoat() {
      this.$refs.dialogObjPhieuTon.hide()
    },
    showPhieuTon: function (phieuId, tthdId, arrPhieuId) {
      console.log('showPhieuTon', phieuId, tthdId, arrPhieuId)
      this.loaded = true
      this.PhieuId = phieuId
      this.dsPhieu = []
      this.txtGhiChu = ''
      if (arrPhieuId != null && arrPhieuId.length > 0) {
        this.dsPhieu = arrPhieuId
      }
      if (phieuId > 0 && this.dsPhieu.filter((x) => x == phieuId).length == 0) this.dsPhieu.push(phieuId)
      this.TrangThaiHdId = tthdId
      this.DsTon.data = []
      try {
        Promise.all([this.sp_ds_giaophieu_ton_load(), this.getDsNhomTon(), this.getDsPhieuTon()])
          .then((values) => {
            this.Loading = false
            this.$refs.dialogObjPhieuTon.show()
          })
          .catch((error) => {
            this.Loading = false
            console.log(`Error in promises ${error}`)
          })
          .finally(async () => {
            if (this.DsTon.data != null && this.DsTon.data.length > 0) {
              await this.SelectPhieuTonItem(0, this.DsTon.data[0])
              this.$refs.gridDsTon.grid.selectRow(0)
            }
          })
        this.Loading = false
      } catch (ex) {
        this.Loading = false
      }
    },
    closePhieuTon: function () {
      this.loaded = false
      this.$refs.dialogObjPhieuTon.hide()
    },
    async tsbtnGhiLai_Click() {
      try {
        let nhanvienbtid = this.DsTon.selectItem.nhanvien_bt_id
        let nhanvienId = parseInt(this.$root.token.getNhanVienID())
        if (this.tsbtnNhapMoi.Enabled == true) {
          if (nhanvienbtid != nhanvienId) {
            this.ShowError(`Lý do tồn này không phải do bạn tạo. Không được sửa! `)
            return
          }
          // await this.xoaPhieu()
        }
        if (this.txtGhiChu.length <= 0) {
          this.ShowError(`Hãy nhập nội dung ghi chú tồn`)
          this.$refs.reftxtGhiChu.focus()
          return
        }
        if (this.lydoTonId == null || this.lydoTonId == '') {
          this.ShowError(`Chưa có lý do tồn ! `)
          this.$refs.reflydoTonId.focus()
          return
        }

        this.Loading = true
        let successMsg1 = `Cập nhật lý do tồn phiếu thành công!`
        let successMsg2 = `Thêm mới lý do tồn phiếu thành công!`
        let data = await this.sp_ghilai_giaophieu_ton()
        if (data == 0) {
          await this.getDsPhieuTon()
          if (this.tsbtnNhapMoi.Enabled) this.ShowSuccess(successMsg1)
          else this.ShowSuccess(successMsg2)
          if (this.tsbtnNhapMoi.Enabled == false) {
            this.DsTon.currentIndex = 0
          }
          this.SetButton(3)
        } else {
          this.ShowError(`Lỗi cập nhật lý do tồn phiếu: ${data}`)
        }
      } catch (err) {
        this.ShowError(`Lỗi cập nhật lý do tồn phiếu: ${err.message}`)
      } finally {
        this.Loading = false
      }
    },
    sp_ghilai_giaophieu_ton: async function () {
      let donviId = parseInt(this.$root.token.getDonViID())
      let nhanvienId = parseInt(this.$root.token.getNhanVienID())
      let userName = this.$root.token.getUserName()
      let chk_new = this.tsbtnNhapMoi.Enabled ? 1 : 0 //1 update:0 them moi
      let ngay_cn = chk_new == 1 ? this.DsTon.selectItem.ngay_bt : moment().format('DD/MM/YYYY HH:mm:ss')
      if (chk_new == 1) await this.xoaPhieu()
      let input = { chk_new: chk_new, phieu_id_arr: this.dsPhieu, lydo_id: this.lydoTonId, nhanvien_id: nhanvienId, donvi_id: donviId, ngay_cn: ngay_cn, nguoi_cn: userName, may_cn: 'web', ip_cn: '127.0.0.1', ghichu: this.txtGhiChu }
      var data = this.GetData(await api.sp_ghilai_giaophieu_ton(this.axios, input))
      return data
    },
    sp_ds_giaophieu_ton_load: async function () {
      var input = { chk_thuhoi: this.vthuhoi, tthd_id: this.TrangThaiHdId }
      var data = this.GetData(await api.sp_ds_giaophieu_ton_load(this.axios, input))
      console.log('sp_ds_giaophieu_ton_load', data)
      if (data.out_ds_nhom != null) {
        this.DmNhomTon = data.out_ds_nhom.map((x) => ({
          id: x.nhomton_id,
          text: x.nhom_ton == null ? '' : x.nhom_ton
        }))
        this.nhomTonId = this.DmNhomTon[0].id
      } else {
        this.DmNhomTon = []
      }
      if (data.out_ds_lydo != null) {
        this.LyDoTons = data.out_ds_lydo
        this.DmLyDoTon = this.LyDoTons.filter((x) => x.nhomton_id == this.nhomTonId).map((x) => ({
          id: x.lydotontc_id,
          text: x.lydoton_tc == null ? '' : x.lydoton_tc
        }))
      } else {
        this.DmLyDoTon = []
      }
    },
    getDsNhomTon: async function () {
      return
      var input = {}
      var data = this.GetData(await api.getDsNhomTon(this.axios, input))
      this.DmNhomTon = data.map((x) => ({
        id: x.NHOMTON_ID,
        text: x.NHOM_TON == null ? '' : x.NHOM_TON
      }))
      if (this.DmNhomTon != null && this.DmNhomTon.length > 0) {
        this.nhomTonId = this.DmNhomTon[0].id
        await this.getDsLyDoTon(this.nhomTonId)
      }
    },
    getDsLyDoTon: async function (nhomId) {
      if (this.TrangThaiHdId == 3 || this.TrangThaiHdId == 4) {
        this.DmLyDoTon = this.LyDoTons.filter((x) => x.nhomton_id == nhomId && (x.tthd_id == 3 || x.tthd_id == 4)).map((x) => ({
          id: x.lydotontc_id,
          text: x.lydoton_tc == null ? '' : x.lydoton_tc
        }))
      } else
        this.DmLyDoTon = this.LyDoTons.filter((x) => x.nhomton_id == nhomId).map((x) => ({
          id: x.lydotontc_id,
          text: x.lydoton_tc == null ? '' : x.lydoton_tc
        }))

      return
      try {
        var input = { nhomton_id: nhomId, tthd_id: this.TrangThaiHdId }
        var data = this.GetData(await api.getDsLyDoTon(this.axios, input))
        if (data == null || data.length == 0) {
          // this.ShowError(`Không lấy được danh sách lý do tồn`)
          return
        }
        this.DmLyDoTon = data.map((x) => ({
          id: x.lydotontc_id,
          text: x.lydoton_tc == null ? '' : x.lydoton_tc
        }))
      } catch (err) {
        console.log('getDsLyDoTon-Err:', err)
      } finally {
        if (this.loaded) {
          if (this.DmLyDoTon == null || this.DmLyDoTon.length == 0) {
            this.ShowError(`Không lấy được danh sách lý do tồn, chưa được khai báo `)
          }
        }
      }
    },
    getDsPhieuTon: async function () {
      var input = { phieu_id: this.PhieuId }
      this.DsTon.data = this.GetData(await api.getDsPhieuTon(this.axios, input))
      if (this.DsTon.data != null && this.DsTon.data.length > 0) {
        this.$refs.gridDsTon.grid.selectRow(this.DsTon.currentIndex)
        await this.SelectPhieuTonItem(this.DsTon.currentIndex, this.DsTon.data[0])
      }
    },
    SelectPhieuTonItem: async function (i, item) {
      //alert(i)
      this.DsTon.selectItem = item
      this.DsTon.currentIndex = i
      this.nhomTonId = item.nhomton_id
      await this.getDsLyDoTon(this.nhomTonId)
      this.lydoTonId = item.lydotontc_id
      this.txtGhiChu = item.ghichu
      this.SetButton(3)
    },
    nhapMoi: async function () {
      this.SetButton(1)
    },
    updatePhieuTon: async function () {
      if (this.txtGhiChu.length <= 0) {
        this.ShowAlert(`Hãy nhập nội dung ghi chú tồn`)
        this.$refs.reftxtGhiChu.focus()
        return
      }
      if (this.lydoTonId == null || this.lydoTonId == '') {
        this.ShowError(`Chưa có lý do tồn ! `)
        this.$refs.reflydoTonId.focus()
        return
      }
      let donviId = parseInt(this.$root.token.getDonViID())
      let nhanvienId = parseInt(this.$root.token.getNhanVienID())
      var input = {
        vdonvi_bt_id: donviId,
        vdsphieu: this.dsPhieu,
        vghichu: this.txtGhiChu,
        vkieu: this.kieu,
        vlydoton_id: parseInt(this.lydoTonId),
        vngay_bt: moment(new Date()).format('DD/MM/YYYY HH:mm:ss'),
        vnhanvien_bt_id: nhanvienId,
        vnhanvien_id: nhanvienId,
        vphieu_id: this.PhieuId,
        vthuhoi: 0
      }
      let successMsg = `Cập nhật lý do tồn phiếu thành công!`
      let successMsg1 = `Cập nhật lý do tồn phiếu thành công!`
      let successMsg2 = `Thêm mới lý do tồn phiếu thành công!`
      if (this.tsbtnNhapMoi.Enabled == false) {
        successMsg = `Thêm mới lý do tồn phiếu thành công!`
      }
      if (this.dsPhieu.length > 1) {
        this.$bvModal
          .msgBoxConfirm(`Trên giao diện thi công đang chọn ${this.dsPhieu.data.length} thuê bao. Thêm mới hàng loạt phiếu tồn ?`, {
            title: '',
            size: 'sm',
            okTitle: 'Đồng ý',
            cancelTitle: 'Hủy'
          })
          .then(async (v) => {
            if (!v) {
              return
            }
            // Xử lý code ở đây
            try {
              let nhanvienbtid = this.DsTon.selectItem.nhanvien_bt_id
              if (this.tsbtnNhapMoi.Enabled == true) {
                if (nhanvienbtid != nhanvienId) {
                  this.ShowError(`Lý do tồn này không phải do bạn tạo. Không được sửa!`)
                  return
                }
                await this.xoaPhieu()
              }
              let response = await api.updatePhieuTon(this.axios, input)
              if (response.data.error == 200 || response.data.error == '200') {
                if (response.data.data == '1' || response.data.data == successMsg || response.data.data == successMsg1 || response.data.data == successMsg2) {
                  this.ShowSuccess(successMsg)
                } else {
                  this.ShowError(response.data.data)
                }
                if (this.tsbtnNhapMoi.Enabled == false) {
                  this.DsTon.currentIndex = 0
                }
                await this.getDsPhieuTon()
              } else {
                console.log(response.data.error_code)
                this.ShowError(`Lỗi cập nhật lý do tồn phiếu ! ${response.data.error_code}`)
              }
            } catch (err) {
              this.ShowError(`${err.message}`)
            }
          })
      } else {
        try {
          let nhanvienbtid = this.DsTon.selectItem.nhanvien_bt_id
          if (this.tsbtnNhapMoi.Enabled == true) {
            if (nhanvienbtid != nhanvienId) {
              this.ShowError(`Lý do tồn này không phải do bạn tạo. Không được sửa! ${nhanvienbtid} ${nhanvienId}`)
              return
            }
            await this.xoaPhieu()
          }
          let response = await api.updatePhieuTon(this.axios, input)
          if (response.data.error === 200 || response.data.error === '200') {
            if (response.data.data == '1' || response.data.data == successMsg) {
              this.ShowSuccess(successMsg)
            } else {
              if (this.tsbtnNhapMoi.Enabled == false) this.ShowSuccess(successMsg2)
              else this.ShowSuccess(response.data.data)
            }
            if (this.tsbtnNhapMoi.Enabled == false) {
              this.DsTon.currentIndex = 0
            }
            await this.getDsPhieuTon()
          } else {
            console.log(response.data.error_code)
            this.ShowError(`Lỗi cập nhật lý do tồn phiếu ! ${response.data.error_code}`)
          }
        } catch (err) {
          this.ShowError(`${err.message}`)
        }
      }
      if (this.tsbtnNhapMoi.Enabled == false) {
        this.DsTon.currentIndex = 0
      }
      this.$refs.gridDsTon.grid.selectRow(this.DsTon.currentIndex)
      this.SetButton(3)
    },
    SetButton: function (kieu) {
      //
      this.tsbtnNhapMoi.Enabled = false
      this.tsbtnGhiLai.Enabled = false
      this.tsbtnXoa.Enabled = false

      this.Fn.AddNew = false
      this.Fn.Save = false
      this.Fn.Delete = false

      if (kieu == -1) {
        this.tsbtnGhiLai.Enabled = true
        this.Fn.Save = true
      }
      if (kieu === 0) {
        this.tsbtnNhapMoi.Enabled = true
        this.Fn.AddNew = true
      }
      if (kieu == 1) {
        this.tsbtnGhiLai.Enabled = true
        this.Fn.Save = true
      }
      if (kieu == 2) {
        this.tsbtnNhapMoi.Enabled = true
        this.tsbtnXoa.Enabled = true
        this.Fn.AddNew = true
        this.Fn.Delete = true
      }
      if (kieu == 3) {
        this.tsbtnNhapMoi.Enabled = true
        this.tsbtnXoa.Enabled = true
        this.tsbtnGhiLai.Enabled = true
        this.Fn.AddNew = true
        this.Fn.Save = true
        this.Fn.Delete = true
      }
    },
    async xoaPhieu() {
      try {
        var ngay_cn = moment(this.DsTon.selectItem.ngay_bt, 'DD/MM/YYYY HH:mm:ss')
        let donviId = this.$root.token.getDonViID()
        let nhanvienId = this.$root.token.getNhanVienID()
        var input = {
          vlydoton_id: this.lydoTonId,
          vnhanvien_bt_id: nhanvienId,
          vngay_cn: moment(ngay_cn).format('DD/MM/YYYY HH:mm:ss'),
          vphieu_id: this.PhieuId
        }
        let response = await api.xoaPhieuTon(this.axios, input)
        return true
      } catch (err) {
        return false
      }
    },
    xoaPhieuTon: async function () {
      let donviId = this.$root.token.getDonViID()
      let nhanvienId = this.$root.token.getNhanVienID()
      if (this.DsTon.selectItem.nhanvien_bt_id != nhanvienId) {
        this.ShowError(`Lý do tồn này không phải do bạn tạo. Không được xóa!`)
      }
      /*
       "vlydoton_id": 0,
      "vnhanvien_bt_id": 0,
      "vphieu_id": 0,
      "vngay_cn": "31/12/2021 15:59:59"
      */
      var ngay_cn = moment(this.DsTon.selectItem.ngaycn, 'YYYY-MM-DD HH:mm:ss')
      var input = {
        vlydoton_id: this.lydoTonId,
        vnhanvien_bt_id: nhanvienId,
        vngay_cn: moment(ngay_cn).format('DD/MM/YYYY HH:mm:ss'),
        vphieu_id: this.PhieuId
      }
      this.$bvModal
        .msgBoxConfirm(`Bạn có chắc chắn muốn xóa lý do tồn giao phiếu không?`, {
          title: '',
          size: 'sm',
          okTitle: 'Đồng ý',
          cancelTitle: 'Hủy'
        })
        .then(async (v) => {
          if (!v) {
            return
          }
          // Xử lý code ở đây
          let response = await api.xoaPhieuTon(this.axios, input)
          if (response.data.error === 200 || response.data.error === '200') {
            this.DsTon.currentIndex = 0
            await this.getDsPhieuTon()
            this.ShowSuccess(`Xóa lý do tồn giao phiếu thành công!`)
            this.SetButton(1)
          } else {
            console.log(response.data.error_code)
            this.ShowError(`Lỗi xóa lý do tồn giao phiếu ! ${response.data.error_code}`)
          }
        })
    },
    grid_selectedItemsChanged(items) {
      this.DsTon.selectedItems = items
    },
    grid_selectedRowChanged(item) {
      this.DsTon.selectItem = item
    },
    GetData: function (response) {
      if (response.data.error === 200 || response.data.error === '200') {
        return response.data.data
      } else {
        console.log(response.data.error_code)
      }
      return []
    },
    ShowAlert: function (message) {
      this.$toast.warning(message)
    },
    ShowError: function (message) {
      this.$toast.error(message)
    },
    ShowSuccess: function (message) {
      this.$toast.success(message)
    }
  }
}
</script>
