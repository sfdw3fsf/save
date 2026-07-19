<template src="./index.html">
  
</template>
<script>
import Vue from 'vue'
import BssRequiredMarker from '@/components/BssRequiredMarker'
import { DatePickerPlugin } from '@syncfusion/ej2-vue-calendars'
import { maxLength, required } from 'vuelidate/lib/validators'
import Modal from '../QuanLyTaiSan/Modal'
Vue.use(DatePickerPlugin)
export default {
  components: { BssRequiredMarker, Modal },
  name: 'DuAn',
  computed: {
  },
  methods: {
    xoa: function () {
      if (!this.buttonState.btnXoaEnabled) return
      if (!this.isDisableBtnXoa) {
        this.modalXoa = !this.modalXoa
      }
    },
    setDisableBtn: function (isNhapmoi, isGhilai, isHuy, isXoa, isDongBoIMS) {
      this.buttonState.btnNhapMoiEnabled = isNhapmoi
      this.buttonState.btnGhiLaiEnabled = isGhilai
      this.buttonState.btnHuyBoEnabled = isHuy
      this.buttonState.btnXoaEnabled = isXoa
      this.buttonState.btnDongBoIMSEnabled = isDongBoIMS
    },
    onClickButtonHuy: function () {
      if (!this.buttonState.btnHuyBoEnabled) return
      this.setDisableBtn(true, true, true, true, true)
      this.duAn = this.oldDuAn
      this.loadData()
      this.selectedRowChanged = true
      this.isNgayNtInvalid = false
      this.isNhapMoi = false
    },
    validateData(data) {
      this.$v.$touch()
      let errorMessage = []
      if (this.$v.congnang.$error) {
        errorMessage.unshift('Công năng: Dữ liệu không hợp lệ')
      }
      if (this.$v.soqd_qt.$error) {
        errorMessage.unshift('Số QĐ quyết toán: Dữ liệu không hợp lệ')
      }
      if (this.$v.soqd_dt.$error) {
        errorMessage.unshift('Số QĐ đầu tư: Dữ liệu không hợp lệ')
      }
      if (this.$v.ngay_dt.$error) {
        this.$refs.ngay_nt.focusIn()
        errorMessage.unshift('Ngày QĐ đầu tư không hợp lệ!')
        // if (!data.ngay_dt) {
        //   errorMessage.unshift('Ngày QĐ đầu tư không được để trống!')
        // }
        // else {
        //   errorMessage.unshift('Ngày QĐ đầu tư không đúng định dạng!')
        // }
      }
      if (this.$v.ngay_nt.$error) {
        this.$refs.ngay_nt.focusIn()
        errorMessage.unshift('Ngày nghiệm thu không hợp lệ!')
        // if (data.ngay_nt == null) {
        //   errorMessage.unshift('Ngày nghiệm thu không được để trống!')
        // }
        // else {
        //   errorMessage.unshift('Ngày nghiệm thu không đúng định dạng!')
        // }
      } else {
        // let yesterday = new Date(new Date().setDate(new Date().getDate() - 1))
        // let yesterday2 = ('0' + yesterday.getDate()).slice(-2) + '/' + ('0' + (yesterday.getMonth() + 1)).slice(-2) + '/' + yesterday.getFullYear()
        // if (this.ngay_nt < yesterday || this.ngay_nt < yesterday2) {
        //   this.isNgayNtInvalid = true
        //   errorMessage.unshift('Ngày nghiệm thu không được nhỏ hơn ngày hiện tại!')
        // } else {
        //   this.isNgayNtInvalid = false
        // }
        this.isNgayNtInvalid = false
      }
      if (this.$v.ma_ct.$error) {
        this.$refs.ma_ct.focus()
        if (!data.ma_ct || !data.ma_ct.toString().trim()) {
          errorMessage.unshift('Tên công trình không được để trống!')
        } else if (data.ma_ct.toString().length > 500) {
          errorMessage.unshift('Tên công trình: Dữ liệu không hợp lệ')
        }
      }

      if (this.$v.sohieu_ct.$error) {
        this.$refs.sohieu_ct.focus()

        if (!data.sohieu_ct || !data.sohieu_ct.toString().trim()) {
          errorMessage.unshift('Số hiệu công trình không được để trống!')
        } else if (data.sohieu_ct.toString().length > 500) {
          errorMessage.unshift('Số hiệu công trình: Dữ liệu không hợp lệ')
        }
      }
      if (errorMessage.length > 0) {
        this.$toast.error(errorMessage.join('\n'))
        return true
      } else {
        return false
      }
    },

    selectSth: async function (data, index) {
      this.sohieu_ct = data.SOHIEU_CT
      this.ngay_nt = data.NGAY_NT
      this.soqd_dt = data.SOQD_DT
      this.soqd_qt = data.SOQD_QT
      this.congnang = data.CONGNANG
      this.ngay_dt = data.NGAY_DT
      this.ma_ct = data.MA_CT
      this.ttda_id = data.TTDA_ID
      this.duan_id = data.DUAN_ID

      await this.layDonViDaGanAction(this.duan_id)
      await this.layDonViChuaGanAction(this.duan_id)
      this.listChuaGan = this.donViChuaGan
      this.listChuaGan = this.donViDaGan
    },
    onClickButtonNhapMoi: function () {
      if (!this.buttonState.btnNhapMoiEnabled) return
      this.$v.$reset()
      this.isNhapMoi = true
      this.buttonState.btnNhapMoiEnabled = false
      this.buttonState.btnXoaEnabled = false
      this.buttonState.btnHuyBoEnabled = true
      this.buttonState.btnDongBoIMSEnabled = false
      let data = {
        sohieu_ct: this.sohieu_ct,
        ngay_nt: this.ngay_nt,
        soqd_dt: this.soqd_dt,
        soqd_qt: this.soqd_qt,
        congnang: this.congnang,
        chutruong_id: 0,
        ma_ct: this.ma_ct,
        dongbo: this.duAn.dongbo,
        ghichu: this.duAn.ghichu,
        ip_cn: this.duAn.ip_cn,
        may_cn: this.duAn.may_cn,
        ngay_cn: this.duAn.ngay_cn,
        nguoi_cn: this.duAn.nguoi_cn,
        ngay_dt: this.ngay_dt,
        duan_id: this.duan_id
      }
      this.oldDuAn = data
      this.oldDuAn.listDuAn = this.listDuAn
      this.oldDuAn.listDaGan = this.listDaGan
      this.oldDuAn.listChuaGan = this.listChuaGan
      this.sohieu_ct = null
      this.ngay_nt = new Date()
      this.ma_ct = null
      this.selectedChuTruong = null
      this.soqd_dt = null
      this.ngay_dt = new Date()
      this.soqd_qt = null
      this.congnang = null
      this.listDaGan = []
      this.listChuaGan = []
      this.$refs['grid'].grid.clearSelection()
    },
    onClickButtonDongBoIMS: async function () {
      try {
        this.loading(true)
        let response = await this.$root.context.post('/web-cabman/duan/dongboDuAnIMS')
        if (response.error_code === 'BSS-00000000') {
          return this.$root.toastSuccess('Gửi yêu cầu đồng bộ thành công, cần 5-10s để quá trình đồng bộ hoàn tất.')
        }
        else {
          return this.$root.toastInfo(response.data.message_detail)
        }
      }
      catch (error) {
        this.$root.toastWarning(error.response.data.message_detail);
      }
      finally {
        this.loading(false)
      }
    },
    gridDaGan_selectedRowChanged(dataItem) {
      this.dataDaGanSelected = dataItem
    },
    gridDaGan_rowSelectedOrDeselected(dataItem) {
      this.gridDaGan_rowsIndexes = this.$refs.dataGridDaGan.getSelectedRecords()
    },
    gridChuaGan_rowSelectedOrDeselected(dataItem) {
      this.gridChuaGan_rowsIndexes = this.$refs.dataGridChuaGan.getSelectedRecords()
    },
    gridChuaGan_selectedRowChanged(dataItem) {
      this.dataChuaGanSelected = dataItem
    },
    grid_onRowClicked1: async function (args) {
      let dataItem = args.rowData
      console.log(this.currentSelectedRow)
      console.log(args)
      if (!dataItem) {
        return
      }
      this.selectedRowChanged = true
      this.isNgayNtInvalid = false
      this.sohieu_ct = dataItem.SOHIEU_CT
      this.ma_ct = dataItem.MA_CT
      this.ngay_nt = dataItem.NGAY_NT
      this.soqd_dt = dataItem.SOQD_DT
      this.soqd_qt = dataItem.SOQD_QT
      this.congnang = dataItem.CONGNANG
      this.ngay_dt = dataItem.NGAY_DT
      this.ttda_id = dataItem.TTDA_ID
      this.duan_id = dataItem.DUAN_ID
      this.selectedChuTruong = dataItem.CHUTRUONG
      this.tableKey++
      this.loading(true)
      var rs = await this.$root.context.post(
        '/web-cabman/duan/don-vi-chua-gan', { duan_id: this.duan_id }
      )
      this.listChuaGan = []
      var resArr = []
      rs.data.forEach(function (item) {
        item.TEN_LOAIDVAndMA_DV = item.TEN_LOAIDV + item.MA_DV
        var i = resArr.findIndex(x => x.MA_DV == item.MA_DV)
        if (i <= -1) {
          resArr.push(item)
        }
      })
      this.listChuaGan = rs.data
      this.listChuaGan.sort(function (a, b) {
        return (a.TEN_LOAIDVAndMA_DV > b.TEN_LOAIDVAndMA_DV) - (a.TEN_LOAIDVAndMA_DV < b.TEN_LOAIDVAndMA_DV)
      })
      rs = await this.$root.context.post(
        '/web-cabman/duan/don-vi-da-gan', { duan_id: this.duan_id }
      )
      this.listDaGan = []
      resArr = []
      rs.data.forEach(function (item) {
        item.TEN_LOAIDVAndMA_DV = item.TEN_LOAIDV + item.MA_DV
        var i = resArr.findIndex(x => x.MA_DV == item.MA_DV)
        if (i <= -1) {
          resArr.push(item)
        }
      })
      this.listDaGan = rs.data
      this.listDaGan.sort(function (a, b) {
        return (a.TEN_LOAIDVAndMA_DV > b.TEN_LOAIDVAndMA_DV) - (a.TEN_LOAIDVAndMA_DV < b.TEN_LOAIDVAndMA_DV)
      })
    },
    grid_selectedRowChanged: async function (args) {
      if (!this.isGridSelected) {
        this.grid_onRowClicked(-2, args)
      }
      this.isGridSelected = false
    },
    grid_onRowClicked: async function (rowIndex, dataItem) {
      console.log('🚀 ~ file: index.vue ~ line 246 ~ dataItem', dataItem)
      this.isGridSelected = true
      if (this.currentSelectedRow === rowIndex && this.buttonState.btnNhapMoiEnabled === true) {
        return
      }
      if (rowIndex === -2) this.currentSelectedRow = 0
      else this.currentSelectedRow = rowIndex
      if (!dataItem) {
        return
      }
      this.selectedRowChanged = true
      this.isNgayNtInvalid = false
      this.sohieu_ct = dataItem.SOHIEU_CT
      this.ma_ct = dataItem.MA_CT
      this.ngay_nt = dataItem.NGAY_NT
      this.soqd_dt = dataItem.SOQD_DT
      this.soqd_qt = dataItem.SOQD_QT
      this.congnang = dataItem.CONGNANG
      this.ngay_dt = dataItem.NGAY_DT
      this.duan_id = dataItem.DUAN_ID
      this.selectedChuTruong = dataItem.CHUTRUONG
      this.ttda_id = dataItem.TTDA_ID
      this.loading(true)
      var rs = await this.$root.context.post(
        '/web-cabman/duan/don-vi-chua-gan', { duan_id: this.duan_id }
      )
      this.listChuaGan = []
      var resArr = []
      rs.data.forEach(function (item) {
        item.TEN_LOAIDVAndMA_DV = item.TEN_LOAIDV + item.MA_DV
        var i = resArr.findIndex(x => x.MA_DV == item.MA_DV)
        if (i <= -1) {
          resArr.push(item)
        }
      })
      this.listChuaGan = rs.data
      this.listChuaGan.sort(function (a, b) {
        return (a.TEN_LOAIDVAndMA_DV > b.TEN_LOAIDVAndMA_DV) - (a.TEN_LOAIDVAndMA_DV < b.TEN_LOAIDVAndMA_DV)
      })
      rs = await this.$root.context.post(
        '/web-cabman/duan/don-vi-da-gan', { duan_id: this.duan_id }
      )
      this.listDaGan = []
      resArr = []
      rs.data.forEach(function (item) {
        item.TEN_LOAIDVAndMA_DV = item.TEN_LOAIDV + item.MA_DV
        var i = resArr.findIndex(x => x.MA_DV == item.MA_DV)
        if (i <= -1) {
          resArr.push(item)
        }
      })
      this.listDaGan = rs.data
      this.listDaGan.sort(function (a, b) {
        return (a.TEN_LOAIDVAndMA_DV > b.TEN_LOAIDVAndMA_DV) - (a.TEN_LOAIDVAndMA_DV < b.TEN_LOAIDVAndMA_DV)
      })
      if (this.buttonState.btnNhapMoiEnabled === false) {
        this.isNhapMoi = false
        this.setDisableBtn(true, true, true, true, true)
      }
      this.loading(false)
    },

    xacNhanXoa: async function () {
      try {
        this.loading(true)
        var rs1 = await this.$root.context.post(
          '/web-cabman/duan/xoa', { duan_id: this.duan_id }
        ).catch(error => {
          throw error.response.data.message_detail.substring(1, error.response.data.message_detail.length - 1)
        })
        var rs = await this.$root.context.post(
          '/web-cabman/duan/danhsach'
        )
        this.listDuAn = rs.data
        this.listDuAn.forEach(item => {
          if (item.NGAY_DT) {
            let date = new Date(item.NGAY_DT)
            item.NGAY_DT = ('0' + date.getDate()).slice(-2) + '/' +
              ('0' + (date.getMonth() + 1)).slice(-2) + '/' +
              date.getFullYear()
          }
          if (item.NGAY_NT) {
            let date = new Date(item.NGAY_NT)
            item.NGAY_NT = ('0' + date.getDate()).slice(-2) + '/' +
              ('0' + (date.getMonth() + 1)).slice(-2) + '/' +
              date.getFullYear()
          }
        })
        this.$toast.success('Xóa thành công')
        this.duan_id = null
      } catch (err) {
        this.$toast.error(err)
        return
      } finally {
        this.loading(false)
      }
    },
    onClickButtonGhiLai: async function () {
      this.selectedRowChanged = false
      let ngayNt = null
      let ngayDt = null
      if (this.ngay_nt) {
        if (this.ngay_nt.toString().length !== 10) {
          ngayNt = this.ngay_nt.getFullYear() + '-' + ('0' + (this.ngay_nt.getMonth() + 1)).slice(-2) +
            '-' + ('0' + this.ngay_nt.getDate()).slice(-2)
        } else {
          var parts = this.ngay_nt.split('/')
          if (parts.length == 0) ngayNt = 'Wrong format'
          ngayNt = parts[2] + '-' + parts[1] + '-' + parts[0]
        }
      }
      if (this.ngay_dt) {
        if (this.ngay_dt.toString().length !== 10) {
          ngayDt = this.ngay_dt.getFullYear() + '-' + ('0' + (this.ngay_dt.getMonth() + 1)).slice(-2) +
            '-' + ('0' + this.ngay_dt.getDate()).slice(-2)
        } else {
          parts = this.ngay_dt.split('/')
          if (parts.length === 0) ngayDt = 'Wrong format'
          ngayDt = parts[2] + '-' + parts[1] + '-' + parts[0]
        }
      }
      let chuTruongIdTmp = this.chuTruongMap.get(this.selectedChuTruong)
      if (!chuTruongIdTmp) chuTruongIdTmp = 0
      if (this.sohieu_ct) this.sohieu_ct = this.sohieu_ct.split(' ').filter(s => s).join(' ')
      if (this.soqd_dt) this.soqd_dt = this.soqd_dt.split(' ').filter(s => s).join(' ')
      if (this.soqd_qt) this.soqd_qt = this.soqd_qt.split(' ').filter(s => s).join(' ')
      if (this.ma_ct) this.ma_ct = this.ma_ct.split(' ').filter(s => s).join(' ')
      if (this.congnang) this.congnang = this.congnang.split(' ').filter(s => s).join(' ')
      let data = {
        sohieu_ct: this.sohieu_ct,
        ngay_nt: ngayNt,
        soqd_dt: this.soqd_dt,
        soqd_qt: this.soqd_qt,
        congnang: this.congnang,
        chutruong_id: chuTruongIdTmp,
        ttda_id: this.ttda_id,
        ma_ct: this.ma_ct,
        dongbo: this.duAn.dongbo,
        ghichu: this.duAn.ghichu,
        ip_cn: this.duAn.ip_cn,
        may_cn: this.duAn.may_cn,
        ngay_cn: this.duAn.ngay_cn,
        nguoi_cn: this.duAn.nguoi_cn,
        ngay_dt: ngayDt,
        duan_id: this.duan_id
      }
      if (this.validateData(data)) {
        return
      }
      try {
        this.loading(true)
        if (!this.isNhapMoi) {
          try {
            var rs = await this.$root.context.post(
              '/web-cabman/duan/cap-nhat', data
            ).catch(e => {
              this.$toast.error(e.response.data.message_detail)
            })
            this.listDaGan.forEach(element => {
              this.$root.context.post(
                '/web-cabman/duan/gan-don-vi', {
                duanId: this.duan_id,
                loaidvId: element.LOAIDV_ID,
                donviId: element.DONVI_ID
              }
              )
            })
            this.listDaGan.sort(function (a, b) {
              return (a.TEN_LOAIDVAndMA_DV > b.TEN_LOAIDVAndMA_DV) - (a.TEN_LOAIDVAndMA_DV < b.TEN_LOAIDVAndMA_DV)
            })
            this.listChuaGan.forEach(element => {
              this.$root.context.post(
                '/web-cabman/duan/huy-gan-don-vi', {
                duanId: this.duan_id,
                loaidvId: element.LOAIDV_ID,
                donviId: element.DONVI_ID
              }
              )
            })
            this.listChuaGan.sort(function (a, b) {
              return (a.TEN_LOAIDVAndMA_DV > b.TEN_LOAIDVAndMA_DV) - (a.TEN_LOAIDVAndMA_DV < b.TEN_LOAIDVAndMA_DV)
            })
            if (!rs) return
            let index
            this.listDuAn.forEach(element => {
              if (element.DUAN_ID === data.duan_id) {
                index = this.listDuAn.indexOf(element)
              }
            })
            let dataTmp
            if (index > -1) {
              dataTmp = this.listDuAn[index]
              this.listDuAn.splice(index, 1)
            }
            dataTmp = rs.data[0]
            if (dataTmp.NGAY_DT) {
              let date = new Date(dataTmp.NGAY_DT)
              dataTmp.NGAY_DT = ('0' + date.getDate()).slice(-2) + '/' +
                ('0' + (date.getMonth() + 1)).slice(-2) + '/' +
                date.getFullYear()
            }
            if (dataTmp.NGAY_NT) {
              let date = new Date(dataTmp.NGAY_NT)
              dataTmp.NGAY_NT = ('0' + date.getDate()).slice(-2) + '/' +
                ('0' + (date.getMonth() + 1)).slice(-2) + '/' +
                date.getFullYear()
            }
            this.listDuAn.unshift(dataTmp)
            rs = await this.$root.context.post(
              '/web-cabman/duan/danhsach'
            )
            this.$toast.success('Cập nhật dự án thành công')
          } catch (err) {
            this.$toast.error('Cập nhật dự án lỗi')
          }
        } else {
          try {
            rs = await this.$root.context.post(
              '/web-cabman/duan/themDuAn', data
            ).catch(error => {
              console.log('🚀 ~ file: index.vue ~ line 488 ~ error', error)
              this.$toast.error(error.response.data.message_detail)
            })
            if (!rs) return
            console.log('🚀 ~ file: index.vue ~ line 492 ~ rs', rs)
            let newObj = rs.data
            // api key lower case => convert upperCase
            for (const [key, value] of Object.entries(newObj)) {
              newObj[key.toUpperCase()] = value;
            }
            console.log(rs)
            let date = new Date(newObj.NGAY_DT || newObj.ngay_dt || new Date())
            newObj.NGAY_DT = ('0' + date.getDate()).slice(-2) + '/' + ('0' + (date.getMonth() + 1)).slice(-2) + '/' + date.getFullYear()
            date = new Date(newObj.NGAY_NT || newObj.ngay_nt || new Date())
            newObj.NGAY_NT = ('0' + date.getDate()).slice(-2) + '/' + ('0' + (date.getMonth() + 1)).slice(-2) + '/' + date.getFullYear()
            this.oldDuAn.listDuAn.unshift(newObj)
            this.listDuAn = this.oldDuAn.listDuAn
            this.tableKey++
            this.listDaGan = this.oldDuAn.listDaGan
            this.listChuaGan = this.oldDuAn.listChuaGan
            this.$toast.success('Thêm mới dự án thành công')
            this.isNhapMoi = false
            this.buttonState.btnNhapMoiEnabled = true
            this.buttonState.btnXoaEnabled = true
          } catch (err) {
            console.log('🚀 ~ file: index.vue ~ line 509 ~ err', err)
            this.$toast.error('Thêm mới dự án lỗi')
          }
        }
      } catch (err) {

      } finally {
        this.loading(false)
      }
    },
    loadData: async function () {
      this.tableKey++
      this.loading(true)
      this.setDisableBtn(true, true, true, true, true)
      var rs1 = await this.$root.context.post(
        '/web-cabman/duan/danhsach'
      )
      this.listDuAn = rs1.data
      this.chutruongIdSet = new Set()
      this.listDuAn.forEach(item => {
        if (item.NGAY_DT) {
          let date = new Date(item.NGAY_DT)
          item.NGAY_DT = ('0' + date.getDate()).slice(-2) + '/' +
            ('0' + (date.getMonth() + 1)).slice(-2) + '/' +
            date.getFullYear()
        }
        if (item.NGAY_NT) {
          let date = new Date(item.NGAY_NT)
          item.NGAY_NT = ('0' + date.getDate()).slice(-2) + '/' +
            ('0' + (date.getMonth() + 1)).slice(-2) + '/' +
            date.getFullYear()
        }
        if (item.CHUTRUONG) {
          this.chutruongIdSet.add(item.CHUTRUONG)
        }
      })
      if (this.listDuAn && this.listDuAn.length > 0) {
        this.duan_id = this.listDuAn[0].DUAN_ID
        this.sohieu_ct = this.listDuAn[0].SOHIEU_CT
        var parts = this.listDuAn[0].NGAY_NT ? this.listDuAn[0].NGAY_NT.split('/') : []
        if (parts.length > 0) {
          this.ngay_nt = new Date(parts[2], parts[1] - 1, parts[0])
        }
        this.soqd_dt = this.listDuAn[0].SOQD_DT
        this.soqd_qt = this.listDuAn[0].SOQD_QT
        this.congnang = this.listDuAn[0].CONGNANG
        parts = this.listDuAn[0].NGAY_DT ? this.listDuAn[0].NGAY_DT.split('/') : []
        if (parts.length > 0) {
          this.ngay_dt = new Date(parts[2], parts[1] - 1, parts[0])
        }
        this.ma_ct = this.listDuAn[0].MA_CT
        var rs = await this.$root.context.post(
          '/web-cabman/duan/don-vi-chua-gan', { duan_id: this.duan_id }
        )
        this.listChuaGan = []
        var resArr = []
        rs.data.forEach(function (item) {
          item.TEN_LOAIDVAndMA_DV = item.TEN_LOAIDV + item.MA_DV
          var i = resArr.findIndex(x => x.MA_DV == item.MA_DV && x.LOAIDV_ID == item.LOAIDV_ID)
          if (i <= -1) {
            resArr.push(item)
          }
        })
        this.listChuaGan = rs.data
        rs = await this.$root.context.post(
          '/web-cabman/duan/don-vi-da-gan', { duan_id: this.duan_id }
        )
        this.listDaGan = []
        resArr = []
        rs.data.forEach(function (item) {
          item.TEN_LOAIDVAndMA_DV = item.TEN_LOAIDV + item.MA_DV
          resArr.push(item)
        })
        this.listDaGan = rs.data
      }
      this.loading(false)
    },
    onFocus: function (args) {
    },
    grid_onRowDoubleClicked: function (rowIndex, dataItem) {
      this.isDoubleClick = true
      this.$refs['popupTCTTTB'].setDuAnId(dataItem.DUAN_ID)
      this.$bvModal.show('popupTCTTTB')
    },
    onClickGanDonVi: async function (isFromLeftToRight) {
      if (isFromLeftToRight === true) {
        for (const element of this.gridDaGan_rowsIndexes) {
          var index = this.listDaGan.findIndex(x => x.TEN_LOAIDVAndMA_DV === element.TEN_LOAIDVAndMA_DV)
          if (index > -1) {
            let tmp = this.listDaGan[index]
            this.listChuaGan.push(tmp)
            this.listDaGan.splice(index, 1)
            this.dataDaGanSelected = null
          }
        }
      } else {
        for (const element of this.gridChuaGan_rowsIndexes) {
          index = this.listChuaGan.findIndex(x => x.TEN_LOAIDVAndMA_DV === element.TEN_LOAIDVAndMA_DV)
          if (index > -1) {
            let tmp = this.listChuaGan[index]
            this.listDaGan.push(tmp)
            this.listChuaGan.splice(index, 1)
            this.dataChuaGanSelected = null
          }
        }
      }
    },
    actionComplete: function (args) {
      if (args.requestType === 'filtering' || args.requestType === 'refresh') {
        if (args.rows) this.buttonState.btnXoaEnabled = true
        else this.buttonState.btnXoaEnabled = false
      }
    }
  },
  async mounted() {
    try {
      await this.$store.dispatch('setHeader', {
        title: 'QUẢN LÝ DỰ ÁN',
        list: [
          {
            name: 'Lập hợp đồng',
            link: { name: 'Ui.cards' }
          },
          {
            name: 'Lắp đặt mới',
            link: { name: 'Ui.buttons' }
          }
        ]
      })
      this.loading(true)
      this.setDisableBtn(true, true, true, true, true)
      this.trangthaiIdSet = [
        {
          "ttda_id": 1,
          "trangthai_da": "Chính thức"
        },
        {
          "ttda_id": 2,
          "trangthai_da": "Đề xuất"
        },
        {
          "ttda_id": 3,
          "trangthai_da": "Phê duyệt cấu hình"
        },
        {
          "ttda_id": 4,
          "trangthai_da": "Hoàn thành"
        }
      ];
      this.ttda_id = this.trangthaiIdSet[0].TTDA_ID
      var rs1 = await this.$root.context.post(
        '/web-cabman/duan/danhsach'
      )
      this.listDuAn = rs1.data
      this.chutruongIdSet = new Set()
      this.listDuAn.forEach(item => {
        if (item.NGAY_DT) {
          let date = new Date(item.NGAY_DT)
          item.NGAY_DT = ('0' + date.getDate()).slice(-2) + '/' +
            ('0' + (date.getMonth() + 1)).slice(-2) + '/' +
            date.getFullYear()
        }
        if (item.NGAY_NT) {
          let date = new Date(item.NGAY_NT)
          item.NGAY_NT = ('0' + date.getDate()).slice(-2) + '/' +
            ('0' + (date.getMonth() + 1)).slice(-2) + '/' +
            date.getFullYear()
        }
        if (item.CHUTRUONG) { this.chutruongIdSet.add(item.CHUTRUONG) }
        this.chuTruongMap.set(item.CHUTRUONG, item.CHUTRUONG_ID)
      })
      if (this.listDuAn && this.listDuAn.length > 0) {
        this.selectedChuTruong = this.listDuAn[0].CHUTRUONG
        this.duan_id = this.listDuAn[0].DUAN_ID
        this.sohieu_ct = this.listDuAn[0].SOHIEU_CT
        var parts = this.listDuAn[0].NGAY_NT ? this.listDuAn[0].NGAY_NT.split('/') : []
        if (parts.length > 0) {
          this.ngay_nt = new Date(parts[2], parts[1] - 1, parts[0])
        }
        this.soqd_dt = this.listDuAn[0].SOQD_DT
        this.soqd_qt = this.listDuAn[0].SOQD_QT
        this.congnang = this.listDuAn[0].CONGNANG
        parts = this.listDuAn[0].NGAY_DT ? this.listDuAn[0].NGAY_DT.split('/') : []
        if (parts.length > 0) {
          this.ngay_dt = new Date(parts[2], parts[1] - 1, parts[0])
        }

        this.ma_ct = this.listDuAn[0].MA_CT
        var rs = await this.$root.context.post(
          '/web-cabman/duan/don-vi-chua-gan', { duan_id: this.duan_id }
        )
        this.listChuaGan = []
        var resArr = []
        rs.data.forEach(function (item) {
          item.TEN_LOAIDVAndMA_DV = item.TEN_LOAIDV + item.MA_DV
          var i = resArr.findIndex(x => x.MA_DV == item.MA_DV)
          if (i <= -1) {
            resArr.push(item)
          }
        })
        this.listChuaGan = rs.data
        rs = await this.$root.context.post(
          '/web-cabman/duan/don-vi-da-gan', { duan_id: this.duan_id }
        )
        this.listDaGan = []
        resArr = []
        rs.data.forEach(function (item) {
          item.TEN_LOAIDVAndMA_DV = item.TEN_LOAIDV + item.MA_DV
          var i = resArr.findIndex(x => x.MA_DV == item.MA_DV)
          if (i <= -1) {
            resArr.push(item)
          }
        })
        this.listDaGan = rs.data
        this.listDaGan.sort(function (a, b) {
          return (a.TEN_LOAIDVAndMA_DV > b.TEN_LOAIDVAndMA_DV) - (a.TEN_LOAIDVAndMA_DV < b.TEN_LOAIDVAndMA_DV)
        })
      }
    } catch (err) {
      console.log(err)
    } finally {
      this.loading(false)
    }
  },
  data() {
    return {
      isNhapMoi: false,
      buttonState: {
        btnNhapMoiEnabled: true,
        btnGhiLaiEnabled: true,
        btnHuyBoEnabled: true,
        btnXoaEnabled: true
      },
      selectedChuTruong: null,
      modalXoa: false,
      duAn: {
        chuTruongId: null,
        dongbo: 0,
        duan_ims_id: 0,
        ghichu: null,
        ip_cn: null,
        may_cn: null,
        ngay_cn: null,
        nguoi_cn: null
      },
      soqd_dt: null,
      soqd_qt: null,
      congnang: null,
      ngay_dt: null,
      ngay_nt: null,
      sohieu_ct: null,
      ma_ct: null,
      chutruongIdSet: new Set(),
      oldDuAn: {
        sohieu_ct: null,
        ngay_nt: null,
        soqd_dt: null,
        soqd_qt: null,
        congnang: null,
        ngay_dt: null,
        chutruong_id: 0,
        ma_ct: null,
        dongbo: 0,
        duan_ims_id: 0,
        ghichu: null,
        ip_cn: null,
        may_cn: null,
        ngay_cn: null,
        nguoi_cn: null,
        listDuAn: [],
        listChuaGan: null,
        listDaGan: null
      },
      duan_id: null,
      listDuAn: null,
      listChuaGan: null,
      listDaGan: null,
      dataChuaGanSelected: null,
      dataDaGanSelected: null,
      chuTruongMap: new Map(),
      tableKey: 1,
      isNgayNtInvalid: false,
      selectedRowChanged: false,
      currentSelectedRow: -1,
      gridDaGan_rowsIndexes: [],
      gridChuaGan_rowsIndexes: [],
      isGridSelected: false
    }
  },
  watch: {
    ngay_nt: function (newValue) {
      // if (!this.isNhapMoi && this.selectedRowChanged) return
      // let yesterday = new Date(new Date().setDate(new Date().getDate() - 1))
      // if (this.ngay_nt < yesterday) {
      //   this.isNgayNtInvalid = true
      // } else {
      //   this.isNgayNtInvalid = false
      // }
    }
  },
  validations: {
    sohieu_ct: {
      required,
      maxlength: maxLength(500)
    },
    soqd_dt: {
      maxlength: maxLength(100)
    },
    soqd_qt: {
      maxlength: maxLength(100)
    },
    congnang: {
      maxlength: maxLength(100)
    },
    ma_ct: {
      required,
      maxlength: maxLength(2000)
    },
    ngay_nt: {
      required
    },
    ngay_dt: {
      required
    }
  },
  created() {

  },
  destroyed() {

  }
}
</script>
<style scoped src="./index.scss">

</style>
