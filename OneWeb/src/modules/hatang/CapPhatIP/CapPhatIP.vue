<template src="./CapPhatIP.html"></template>
<style src="./CapPhatIP.scss" scoped></style>
<script>

import Vue from 'vue'
import { Query } from '@syncfusion/ej2-data'
import moment from 'moment'
import api from './api.js'
import Enum from "./enum"

export default Vue.extend({
  components : {
    appTimKiemIP: () => import('./components/TimKiemIP'),
    appChonDonViNhanVien: () => import('./components/ChonDonViNhanVien')
  },
  props: {
    p_auto: {
      type: Boolean,
      required: false,
      default: false
    },
    p_so_phieu: {
      required: false
    },
    p_phieu_yeucau: {
      required: false
    },
    p_so_card_ip: {
      required: false
    },
    p_donvi_cap: {
      required: false
    },
    p_capphat_id: {
      required: false
    },
    p_capphat_mayao_id: {
      required: false
    },
    p_hdtb_id: {
      required: false
    },
    p_ten_khachhang: {
      required: false
    }
  },
  data() {
    return {
      dateConfig: {
        value: new Date(),
        format: 'dd/MM/yyyy',
        maxDate: moment().endOf('month').toDate(),
        minDate: moment().startOf('month').toDate()
      },
      tsbtnThemMoi: true,
      tsbtnGhiLai: false,
      tsbtnHuyBo: false,
      tsbtnSua: false,
      tsbtnXoa: false,
      tsbtnXacNhan: false,
      tsbtnChonIP: false,
      dsSelected: [],
      tsbtnXacNhan: false,
      id: null,
      capphat_id: null,
      capphat_mayao_id: null,
      hdtb_id: null,
      so_phieu: null,
      ipOptions: [],
      ip: [],
      nguon_yeucau: 2,
      nguonyeucauOptions : [{ID: 1, TEN: 'Gói dịch vụ'}, {ID: 2, TEN: 'Không theo gói dịch vụ'}],
      phieu_yeucau: null,
      ip_store: {
        id: null,
        value: null
      },
      trangthai_capphat: 0,
      trangthaicapphatOptions: [{ID: Enum.TRANGTHAI_CAPHAT.CHO_CAPPHAT, TEN: 'Chờ cấp phát'}, {ID: Enum.TRANGTHAI_CAPHAT.DA_CAPPHAT, TEN: 'Đã cấp phát'}],
      phanloai_ip: {
        id: null,
        value: null
      },
      donvi_sudung: null, // tên đơn vị sử dụng
      donvi_sudung_id: null,
      donvisudungOptions: [],
      subnet: {
        id: null,
        value: null
      },
      nguoi_sudung: {
        id: null,
        value:  null
      },
      hatang_mang: {
        id: null,
        value: null
      },
      nguoi_cap: this.$auth.getUserName(),
      ngay_cap: new Date(),
      ngay_tao: new Date(),
      ngay_hethan: new Date(),
      ghi_chu: null,
      dsPhieuCapPhat: [],
      phanvung_id: Number(this.$auth.getPhanVungID()),
      tenDonVi: this.$auth.getDonViID(),
      chonNhanVien: false
    }
  },
  async created () {
    console.log('CapphatIP created')
  },
  async mounted () {
    // this.setButton(1)
    console.log('CapphatIP mounted')
    // this.nguon_yeucau = 1
    // this.so_phieu = 'ABC-123'
    // this.phieu_yeucau = '123456'
    // this.nguoi_sudung.value = 'Nguyễn Văn A'
    // this.nguoi_sudung.id = 123456
    // this.setPropsData()
    await this.loadDSPhieuCapPhat()
  },
  methods: {
    async setPropsData() {
      await this.loadDSPhieuCapPhat()
      this.setButton(1)
      this.nguon_yeucau = this.p_auto ? 1 : 2
      this.so_phieu = this.p_so_phieu
      this.phieu_yeucau = this.p_phieu_yeucau
      this.nguoi_sudung.value = this.p_ten_khachhang
      this.donvi_sudung = this.p_donvi_cap.ten_donvi
      this.donvi_sudung_id = this.p_donvi_cap.id_donvi
      this.capphat_id = this.p_capphat_id
      this.hdtb_id = this.p_hdtb_id
      this.capphat_mayao_id = this.p_capphat_mayao_id
      // console.log('setPropsData', this.p_so_phieu, this.p_phieu_yeucau, this.p_ten_khachhang)
    },
    async tsbtnThemMoi_Click () {
      this.setButton(1)
    },
    async tsbtnGhiLai_Click () {
      if (!this.validateInput()) {
        return
      }
      try {
        let f = await this.save2()
        if (f) {
          this.loadDSPhieuCapPhat()
          if (this.id)
          {
            this.$toast.success('Cập nhật thành công.')
          } else {
            this.$toast.success('Nhập mới thành công.')
          }
          this.setButton(0)
        } else {
          // this.$toast.error('Đã phát sinh lỗi khi lưu dữ liệu.')
        }
      } catch (err) {
        console.log(err)
      }

    },
    async tsbtnHuyBo_Click () {
      this.setButton(0)
      let dsSelected = this.$refs.dsPhieuCapPhat.$refs.grid.getSelectedRecords()
      if (dsSelected.length > 0) {
        this.setData(dsSelected[0])
        this.tsbtnSua = true
        this.tsbtnXoa = true
      } else {
        this.tsbtnSua = false
        this.tsbtnXoa = false
      }
    },
    tsbtnEdit_Click () {
      this.setButton(2)
    },
    async tsbtnXoa_Click () {
      let dsSelected = this.$refs.dsPhieuCapPhat.$refs.grid.getSelectedRecords()

      if (dsSelected.length <= 0) {
        this.$toast.error('Chưa chọn phiếu cần xoá.')
        return
      }

      let f = dsSelected.some(x => x.TRANGTHAI === Enum.TRANGTHAI_CAPHAT.DA_CAPPHAT)

      if (f) {
        this.$toast.error('Không thể xoá các phiếu đã cấp phát.')
        return
      }
      this.$confirm(
            'Bạn có muốn xoá các phiếu đã chọn ?',
            "Loại vật liệu kết nối mạng",
            {
              confirmButtonText: "Đồng ý",
              cancelButtonText: "Không",
            }
          )
            .then(async () => {
              await this.doDelete(dsSelected.map(x => x.ID))
            })
    },

    validateInput() {
      let errors = []
      if (this.ip.length <= 0) {
        errors.push('Vui lòng chọn IP.')
      }
      if (errors.length > 0) {
        this.$toast.error(errors.join('\r\n'))
        return false
      }
      return true
    },

    async save2() {

      let arrIP = this.ip.join(',')
      let postData = {
        ID: this.id,
        CAPPHAT_ID: this.capphat_id,
        CAPPHAT_MAYAO_ID: this.capphat_mayao_id,
        HDTB_ID: this.hdtb_id,
        SOPHIEU: this.so_phieu,
        NGUON_YC: this.nguon_yeucau,
        PHIEU_YC: this.phieu_yeucau,
        IP_ADDRESS_ID: arrIP,
        IP_PHANLOAI_ID: this.phanloai_ip.id,
        TRANGTHAI_CAPPHAT: this.trangthai_capphat,
        IP_STORE_ID: this.ip_store.id,
        HATANGMANG_ID: this.hatang_mang.id,
        SUBNET_ID: this.subnet.id,
        NGUOI_CAP:  this.$auth.getNhanVienID(),
        PHANVUNG_ID: this.$auth.getPhanVungID(),
        NGAY_CAP: moment(this.ngay_cap).format('DD/MM/YYYY'),
        NGAY_TAO: moment(this.ngay_tao).format('DD/MM/YYYY'),
        NGAY_HETHAN: moment(this.ngay_hethan).format('DD/MM/YYYY')
      }

      try {
        const res = await api.capnhat(this.axios, { p_ds: JSON.stringify(postData)})
        const data = await res.data

        if (data.error_code === 'BSS-00000000')  return true
        this.$toast.error(data.message_detail)
        return false
      } catch (error) {
        console.log(error)
        if (error.data && error.data.message_detail) {
          this.$toast.error(error.data.message_detail)
        }
        return false
      }

    },

    async save() {
      let postData = {
        ID: this.id,
        CAPPHAT_ID: this.capphat_id,
        HDTB_ID: this.hdtb_id,
        SOPHIEU: this.so_phieu,
        NGUON_YC: this.nguon_yeucau,
        PHIEU_YC: this.phieu_yeucau,
        IP_ADDRESS_ID: this.ip.id,
        IP_PHANLOAI_ID: this.phanloai_ip.id,
        TRANGTHAI_CAPPHAT: this.trangthai_capphat,
        IP_STORE_ID: this.ip_store.id,
        HATANGMANG_ID: this.hatang_mang.id,
        SUBNET_ID: this.subnet.id,
        NGUOI_CAP:  this.$auth.getNhanVienID(),
        PHANVUNG_ID: this.$auth.getPhanVungID(),
        NGAY_CAP: moment(this.ngay_cap).format('DD/MM/YYYY'),
        NGAY_TAO: moment(this.ngay_tao).format('DD/MM/YYYY'),
        NGAY_HETHAN: moment(this.ngay_hethan).format('DD/MM/YYYY')
      }

      try {
        const res = await api.capnhat(this.axios, { p_ds: JSON.stringify(postData)})
        const data = await res.data

        if (data.error_code === 'BSS-00000000')  return true
        this.$toast.error(data.message_detail)
        return false
      } catch (error) {
        console.log(error)
        if (error.data && error.data.message_detail) {
          this.$toast.error(error.data.message_detail)
        }
        return false
      }
    },

    doDelete: async function (ds) {
      let promises = []
      console.log('doDelete', ds)
      for (let item of ds) {
        promises.push(await this.deleteItem(item))
      }
      let res = await Promise.all(promises)
      let ok = res.filter(x => x === true)

      // console.log('OK', ok)

      if (ok.length > 0) {
        this.$toast.success(`Đã xoá thành công ${ok.length} / ${res.length} mục` )
      } else {
        this.$toast.error(`Đã phát sinh lỗi khi xoá phiếu.` )
      }
      await this.loadDSPhieuCapPhat();
    },

    async deleteItem (id) {
      try {
        const rs = await this.$root.context.post(
          `/web-ecms/hatang/capphat-ip/delete/` + id
        )
        if (rs?.data?.result)
          return true
        return false
      } catch (error) {
        console.log(error)
        return false
      }
    },
    async tsbtnXacNhan_Click () {

      console.log(this.id)
      if (!this.id) {
        this.$toast.error('Chưa chọn phiếu để xác nhận cấp phát.')
        return
      }
      if (this.trangthai_capphat === Enum.TRANGTHAI_CAPHAT.DA_CAPPHAT) {
        this.$toast.error('Phiếu này đã được cấp phát IP.')
        return
      }

      let ret = await this.xacnhanCapPhatIP()
      if (ret && this.p_auto) {
        let ds_ip = this.dsPhieuCapPhat.filter(x => x.SOPHIEU === this.p_so_phieu).map(x => ({ ID: x.IP_ADDRESS_ID, TEN: x.IP_TEN}))
        console.log(ds_ip)
        this.$emit('xacnhanIP', ds_ip)
        this.$toast.success('Đã cấp IP thành công cho phiếu ' + this.p_so_phieu)
      } else if (ret) {
        this.$toast.success('Đã xác nhận cấp phát IP cho phiếu ' + this.so_phieu)
        await this.loadDSPhieuCapPhat()
        this.setButton(0)
      } else {
        this.$toast.error('Đã phát sinh lỗi khi xác nhận cấp phát IP.')
      }
    },
    btnTimIP_Click () {
      this.$refs.refTimKiemIP.openDialog()
    },

    btnTimDonVi_Click (){
      this.$refs.refChonDonViNhanVien.dialogOpen()
    },

    onSearchIPHandler(ds) {

      console.log('ds', ds)
      let s = ds.map(x => ({ID: x.ID, TEN: x.IP}))
      this.ipOptions = s
      this.ip = ds.map(x => x.ID)
      console.log(s)
      // console.log(selected)

      let selected = ds[0] // tạm thời
      console.log(ds, selected)
      // this.ip.id = selected.ID
      // this.ip.value = selected.IP

      this.ip_store.id = selected.ID_STORE_IP
      this.ip_store.value = selected.TEN_IP_STORE

      this.subnet.id = selected.SUBNET_ID
      this.subnet.value = selected.TEN_SUBNET

      this.hatang_mang.id = selected.HATANGMANG_ID
      this.hatang_mang.value = selected.TEN_HATANGMANG

      this.phanloai_ip.id = selected.IP_PHANLOAI_ID
      this.phanloai_ip.value = selected.TEN_PHANLOAI_IP

    },

    xacNhanChonDVNV() {

    },

    async xacnhanCapPhatIP() {
      try {
        const postData = {
          ID: this.id,
          NGUOI_CAP:  this.$auth.getNguoiDungID()
        }
        const res = await api.confirm(this.axios, { p_params: JSON.stringify(postData)})
        const data = await res.data

        if (data.error_code === 'BSS-00000000')  return true
        this.$toast.error(data.message_detail)
        return false
      } catch (error) {
        console.log(error)
        return false
      }
    },

    async loadDSPhieuCapPhat () {
      try {
        const rs = await this.$root.context.get(
          `/web-ecms/hatang/capphat-ip/get-list`
        );
        if (rs.data && rs.data.length > 0) {
          this.dsPhieuCapPhat = rs.data
          this.isDisableSave = false
          this.isDisableNew = false
          this.isDisableUpdate = false
          this.isDisableDelete = false
        } else {
          this.dsPhieuCapPhat = []
          this.isDisableSave = true
          this.isDisableNew = false
          this.isDisableUpdate = true
          this.isDisableDelete = true
        }

      } catch (error) {
        this.datasource = []
      }
    },

    setButton(type) {
      switch (type) {
        case 0: // init
          this.tsbtnThemMoi = true
          this.tsbtnGhiLai = false
          this.tsbtnHuyBo = false
          this.tsbtnXoa = true
          this.tsbtnSua = true
          this.tsbtnChonIP = false
          this.enableControl(false)
          break;
        case 1: // add
          this.tsbtnThemMoi = false
          this.tsbtnGhiLai = true
          this.tsbtnHuyBo = true
          this.tsbtnXoa = false
          this.tsbtnSua = false
          this.tsbtnChonIP = true
          this.enableControl(true)
          this.clearForm()
          // this.$refs.ten_vatlieu_ketnoi.focus()
          break;
        case 2: // edit
          this.tsbtnThemMoi = false
          this.tsbtnGhiLai = true
          this.tsbtnHuyBo = true
          this.tsbtnSua = false
          this.tsbtnXoa = true
          this.tsbtnChonIP = true
          this.enableControl(true)
          // this.$refs.ten_vatlieu_ketnoi.focus()
      }
    },

    clearForm() {
      this.id = null
      this.so_phieu = null
      this.ipOptions = []
      this.ip = []
      this.nguon_yeucau = 2
      this.phieu_yeucau = null
      this.ip_store.value = null
      this.ip_store.id = null
      this.subnet.value = null
      this.subnet.id = null
      this.hatang_mang.value = null
      this.hatang_mang.id = null
      this.trangthai_capphat = 1
      this.phanloai_ip.value = null
      this.phanloai_ip.id = null
      this.donvi_sudung = null
      this.donvi_sudung_id = null
      this.nguoi_sudung.id = null
      this.nguoi_sudung.value = null
      this.ngay_cap = new Date()
      this.ngay_tao = new Date()
      this.ngay_hethan = new Date()
    },

    setData(data) {
      if (data && !Array.isArray(data)) {
        // console.log(data)
        this.id = data.ID
        let found = this.dsPhieuCapPhat.find(x => x.ID === this.id)
        this.id = found.ID;
        this.so_phieu = found.SOPHIEU;
        this.ip.value = found.IP_TEN
        this.ip.id = found.IP_ADDRESS_ID
        this.nguon_yeucau = found.NGUON_YC
        this.phieu_yeucau = found.PHIEU_YC
        this.ip_store.value = found.IP_STORE_TEN
        this.ip_store.id = found.IP_STORE_ID
        this.subnet.value = found.SUBNET_TEN
        this.subnet.id = found.SUBNET_ID
        this.hatang_mang.value = found.HATANGMANG_TEN
        this.hatang_mang.id = found.HATANGMANG_ID
        this.trangthai_capphat = found.TRANGTHAI
        this.phanloai_ip.value = found.LOAI_IP_TEN
        this.phanloai_ip.id = found.IP_PHANLOAI_ID
        this.donvi_sudung = found.DONVI_SUDUNG_TEN
        this.donvi_sudung_id = found.DONVI_SUDUNG_ID
        this.nguoi_sudung.id = found.NGUOI_SUDUNG_ID
        this.nguoi_sudung.value = found.NGUOI_SUDUNG_TEN
        this.ngay_cap = found.NGAY_CAP
        this.ngay_tao = found.NGAY_TAO
        this.ngay_hethan = found.NGAY_HETHAN

        this.setButton(0)
        console.log(this.trangthai_capphat)
        if (Number(this.trangthai_capphat) === 1) { // chờ cấp phát
          this.tsbtnXacNhan = true
          this.tsbtnXoa = true
          this.tsbtnSua = true
        } else {
          this.tsbtnXoa = false
          this.tsbtnSua = false
          this.tsbtnXacNhan = false
        }
      } else {
        this.clearForm()
        this.setButton(0)
      }
    },

    async onRecordClick (row) {
      // this.id = row.rowData.ID
      // let item = this.dsLoaiVatLieu.find(x => x.ID === this.id)
      // this.thongso_kythuat = item.THONGSO_KYTHUAT
      // this.loai_vatlieu = item.ID_LOAIVATLIEU
      // this.ten_vatlieu_ketnoi = item.TEN_VATLIEUKETNOI
      // this.ghichu = item.GHI_CHU

      // this.setButton(2)
    },

    onCheckBoxChange (args) {
      console.log(args)
      // let dsSelected = this.$refs.dsPhieuCapPhat.getSelectedRecords()
      // if (dsSelected != null && dsSelected.length > 0) {
      //   this.tsbtnXoa = true
      //   this.tsbtnSua = true
      // } else {
      //   this.tsbtnXoa = false
      //   this.tsbtnSua = false
      // }
    },

    onFilteringDonViSuDung (e) {
      var query = new Query()
      query = (e.text !== '') ? query.where('TEN', 'contains', e.text, true) : query
      e.updateData(this.donvisudungOptions, query)
    },
    onFilteringNguoiSuDung (e) {
      var query = new Query()
      query = (e.text !== '') ? query.where('TEN', 'contains', e.text, true) : query
      e.updateData(this.nguoisudungOptions, query)
    },

    onRowSelected (row) {
      this.setData(row?.data)
    },

    onRowDeselected() {
      this.clearForm()
      this.enableControl(false)
    },

    onSelectRowChanged(selected) {
      this.isDisableSave = true
      this.isDisableNew = false
      this.isDisableUpdate = false
      this.isDisableDelete = false

      let found = this.dsPhieuCapPhat.find(x => x.ID === selected.data.ID)

      if (!found)  return

      console.log(found)


    },

    async onRecordClick (row) {
      // this.setData(row?.rowData)
      // this.setButton(2)
    },

    enableControl (f_enable) {
    },

    xemChitietCapmayao () {
      window.open('/ha-tang/cap-mayao?phieu-yeucau=' + this.phieu_yeucau, "_blank")
    }

  }
})

</script>
