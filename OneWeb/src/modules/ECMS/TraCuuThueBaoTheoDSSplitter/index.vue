<template src="./index.html"></template>
<script>
import {actionName, statePropertyName} from './TraCuuThueBaoTheoDSSplitter'
import {mapActions, mapState} from 'vuex'
import XLSX from 'xlsx'

export default {
  name: 'TraCuuThueBaoTheoDSSplitter',
  computed: {
    ...mapState('ecms/traCuuThueBaoTheoDSSplitter', statePropertyName)
  },
  methods: {
    ...mapActions('ecms/traCuuThueBaoTheoDSSplitter', actionName),
    clear () {
    },
    checkfile: function () {
      if (this.file == null) {
        this.$toast.error('Chưa chọn file nào!')
        return false
      }
      if (this.file.type !== 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet' && this.file.type !== 'application/vnd.ms-excel') {
        this.$toast.error('File không đúng định dạng cho phép(xls, xlsx) hoặc không đúng biểu mẫu, vui lòng thử lại.')
        return false
      }
      return true
    },
    grid_dataGridCellCreated(e) {
      if (e.cellDataItem.TRANGTHAI_TB == 'Đang thi công') {
        $(e.cellElement).css({background: '#219653'})
      } else if (e.cellDataItem.TRANGTHAI_TB == 'Tạm dừng') {
        $(e.cellElement).css({background: '#ED5050'})
      } else if (e.cellDataItem.TRANGTHAI_TB == 'Thanh lý theo yêu cầu') {
        $(e.cellElement).css({background: '#ADBCC3'})
      } else {
        $(e.cellElement).css({background: '#FFFFFF'})
      }
    },
    getDsFilemau: async function () {
      this.$root.showLoading(true)
      try {
        var rs = await this.getDsFilemauAction()
        if (rs != null) {
          // const dataItem = rs.data.MA_TB.split(',')
          rs.forEach((i) => {
            this.dataExcel.push({KYHIEU_SPLITTER: i.KYHIEU})
          })
          var maThueBao = XLSX.utils.json_to_sheet(this.dataExcel)
          var wb = XLSX.utils.book_new() // make Workbook of Excel
          XLSX.utils.book_append_sheet(wb, maThueBao, 'TB') // sheetAName is name of Worksheet
          // export Excel file
          XLSX.writeFile(wb, 'mau.xlsx')
        }
      } catch (error) {
      } finally {
        this.$root.showLoading(false)
      }
    },
    xuatExcel: async function () {
      if (this.isDisableBtnXuatExcel === true) return
      this.$root.showLoading(true)
      let thueBaoExcel = []
      try {
        // const dataItem = rs.data.MA_TB.split(',')
        // console.log(this.thueBao)
        if (this.thueBao.length === 0) {
          let thueBaoTmp = {
            'Stt': null,
            'Kiểu đấu nối': null,
            'Account (Mã thuê bao)': null,
            'Mã dịch vụ (Mã ảo)': null,
            'Splitter': null,
            'Port Splitter': null,
            'Tên TB': null,
            'Trạng thái thuê bao': null,
            'Tốc độ': null,
            'Dịch vụ VT': null,
            'Số liên hệ': null,
            'Loạt hình': null,
            'Địa chỉ LĐ': null
          }
          thueBaoExcel.push(thueBaoTmp)
        } else {
          this.thueBao.forEach(function (thueBao) {
            thueBaoExcel.push({
              'Stt': thueBao.STT,
              'Kiểu đấu nối': thueBao.KIEU,
              'Account (Mã thuê bao)': thueBao.ACCOUNT,
              'Mã dịch vụ (Mã ảo)': thueBao.MA_DV,
              'Splitter': thueBao.KYHIEU_SPLITTER,
              'Port Splitter': thueBao.PORT_SP,
              'Tên TB': thueBao.TEN_TB,
              'Trạng thái thuê bao': thueBao.TRANGTHAI_TB,
              'Tốc độ': thueBao.TOCDO,
              'Dịch vụ VT': thueBao.TEN_DVVT,
              'Số liên hệ': thueBao.SDT_LIENHE,
              'Loạt hình': thueBao.LOAIHINH_TB,
              'Địa chỉ LĐ': thueBao.DIACHI_LD
            })
          })
        }
        var maThueBao = XLSX.utils.json_to_sheet(thueBaoExcel)
        var wb = XLSX.utils.book_new() // make Workbook of Excel
        XLSX.utils.book_append_sheet(wb, maThueBao, 'TB') // sheetAName is name of Worksheet
        // export Excel file
        XLSX.writeFile(wb, 'mau.xlsx')
      } catch (error) {
      } finally {
        this.$root.showLoading(false)
      }
    },
    tracuu: async function () {
      this.isDisableBtnXuatExcel = true
      this.loading(true)
      let kyHieuKetCuoi = null
      this.thueBao = null
      try {
        //  console.log(this.file);
        if (this.checkfile()) {
          const reader = new FileReader()
          reader.onload = async (e) => {
            /* Parse data */
            const bstr = e.target.result
            const wb = XLSX.read(bstr, {type: 'binary'})
            /* Get first worksheet */
            const wsname = wb.SheetNames[0]
            const ws = wb.Sheets[wsname]
            /* Convert array of arrays */
            const data = XLSX.utils.sheet_to_json(ws, {header: 1})
            let maxRow = 100
            if (data.length < 100) {
              maxRow = data.length
            }
            if (data[0][0] !== 'KYHIEU_SPLITTER') {
              this.$toast.error('File không đúng định dạng cho phép(xls, xlsx) hoặc không đúng biểu mẫu, vui lòng thử lại.')
              return
            }
            for (var i = 1; i < maxRow; i++) {
              if (kyHieuKetCuoi !== null) {
                if (data[i].join('').trim() !== '') {
                  kyHieuKetCuoi += ',' + data[i].join('').trim()
                }
              } else {
                kyHieuKetCuoi = data[i].join('').trim()
              }
            }
            this.loading(true)
            let response = await this.tracuuThueBaoAction(kyHieuKetCuoi)
            this.thueBao = response
            this.isDisableBtnXuatExcel = false
            this.loading(false)
            // } else {
            //   this.$toast.warning('Tối đa 100 splitter!')
            // }
          }
          reader.readAsBinaryString(this.file)
        }
      } catch (error) {
        this.$toast.error(error)
      } finally {
        this.loading(false)
      }
    }
  },
  async mounted () {
    this.$store.dispatch('setHeader', {
      title: 'Cập nhật prefix cho bras',
      list: [
        {
          name: 'Lập hợp đồng',
          link: {name: 'Ui.cards'}
        },
        {
          name: 'Lắp đặt mới',
          link: {name: 'Ui.buttons'}
        }
      ]
    })
    this.clearState()
    this.createApiManager()
    try {
      this.loading(true)
    } catch (err) {
      console.log(err)
    } finally {
      this.loading(false)
    }
  },
  data () {
    return {
      file: null,
      dataExcel: [],
      thueBao: [],
      json_name_tc: 'dsChiTietThueBao',
      isDisableBtnXuatExcel: true,
    }
  },
  created () {
    this.clearState()
    this.createApiManager()
  },
  destroyed () {
    this.clearState()
  }
}
</script>
<style scoped src="./index.scss">
</style>
