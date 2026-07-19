<template src="./TraCuuPhieuTraTheoNgay.html"></template>
<style scoped src="./TraCuuPhieuTraTheoNgay.scss"></style>
<script>
import Calculator from '@/components/Calculator'
import KyCuoc from '@/components/KyCuoc'
// import SuaNgayTT from '../TraCuuPhieuThuTrongNgayCuaNVTC/components/SuaNgayTT'
// import PopupThayDoiThongTinThanhToan from '../components/PopupInXoaSuaPhieuThanhToan/PopupThayDoiThongTinThanhToan'
// import PopupInPhieuThu from '../components/PopupInXoaSuaPhieuThanhToan/PopupInPhieuThu'
// import XoaPhieu from '../TraCuuPhieuThuTrongNgayCuaNVTC/components/XoaPhieu'
import API from '../api/TraCuuPhieuTraTheoNgayAPI.js'
import GachNoAPI from '../api/GachNoAPI'
// import CAPI from '../api/Commons.js'
import DAPI from '../api/DSThueBaoKhongKhoaMoAPI.js'
import XLAPI from '../api/XuLyPhieuThanhToanAPI.js'
import CommonsAPI from '../api/Commons'
import { currency } from '@/filters/currency'
import moment from 'moment'
import EventBus from '@/utils/eventBus'
import Vue from 'vue'
import { SplitterPlugin } from '@syncfusion/ej2-vue-layouts'
import {Splitter} from '@syncfusion/ej2-layouts'
// import { previewPrint } from '../../../utils/util'
import { cloneArray, previewPrint } from '@/utils/util'
Vue.use(SplitterPlugin)

export default {
  name: 'TraCuuPhieuTraTheoNgay',
  components: {
    Calculator,
    SuaNgayTT: () => import('../TraCuuPhieuThuTrongNgayCuaNVTC/components/SuaNgayTT'),
    PopupThayDoiThongTinThanhToan: () => import('../components/PopupInXoaSuaPhieuThanhToan/PopupThayDoiThongTinThanhToan'),
    PopupInPhieuThu: () => import('../components/PopupInXoaSuaPhieuThanhToan/PopupInPhieuThu'),
    PopupXacNhanXoaPhieu: () => import('../components/PopupInXoaSuaPhieuThanhToan/PopupXacNhanXoaPhieu'),
    // XoaPhieu,
    appKyCuoc: KyCuoc
  },
  async created () {
    EventBus.$on('chukycuocChange', this.chukycuocChangeHandler)
    EventBus.$on('onPhieuCheckboxChanged', this.onPhieuCheckboxChangedHandler)
    this.loading(true)
    await Promise.all([
      this.getKyCuocHienHanh(),
      this.getDanhSachThamSoMD(0),
      this.getKiemTraMatKhau()
    ])
    this.loading(false)
  },

  destroyed () {
    EventBus.$off('chukycuocChange', this.chukycuocChangeHandler)
    EventBus.$off('onPhieuCheckboxChanged', this.onPhieuCheckboxChangedHandler)
  },

  mounted () {
    this.bindMouseEvent()
    this.$refs.maTT.focus()
  },

  computed: {
    kyCuoc () {
      return moment(this.thongtinKyHoaDon.kyHoaDonSelected, 'YYYYMMDD').format('MM/YYYY')
    },
    TNAggregatesColumns () {
      return [
        { field: 'THUNGAN', type: 'Count', footerTemplate: this.countTemplate },
        { field: 'TONGTIEN', type: 'Sum', format: 'N0', footerTemplate: this.sumTemplate },
        { field: 'TONGPHIEU', type: 'Sum', format: 'N0', footerTemplate: this.sumTemplate }
      ]
    }
    // PTAggregatesColumns () {
    //   return [{ field: 'TIEN_TRA', type: 'Custom', footerTemplate: this.customTemplate, customAggregateFn: this.customAggregate }]
    // },
  },

  data () {
    return {
      idx: -1,
      mouseStartIndex: -1,
      mouseEndIndex: -1,
      splitterObj: null,
      phieuSelected: null,
      params: {
        today: new Date(),
        ngayTT: new Date(),
        denNgay_Checked: true,
        denNgay: new Date(),
        maTT: '',
        tongTra: 0,
        xoaPhieuBtn_Enable: false,
        isCalculatorShow: false,
        p_kiemtra_mk: true
      },
      selectionSettings: { checkboxOnly: true, type: 'Multiple' },
      thongtinKyHoaDon: {
        kyHoaDonSelected: null,
        chuKySelected: null
      },
      common_params: {
        kyHoaDonHienTai: moment(new Date()).subtract(1, 'months').startOf('month').format('YYYYMMDD').toString(),
        ngay_BD: '',
        ngay_KT: '',
        ngayXoa_LuiTT: -1,
        ngayXoa_Lui: -1,
        kiemTra_MK: true,
        quyenGN_Ngay: false,
        tienTra_Sum: 0,
        tienTra_Checked: 0,
        ds_Quyen: []
      },
      thuNgan_grid: {
        items: [],
        selected: {},
        key: 0
      },
      hinhThucTT_grid: {
        items: [],
        selected: {},
        key: 0
      },
      phieuTT_grid: {
        items: [],
        selected: {},
        checked: [],
        key: 0,
        pageInfo: {
          page: 0,
          maxSize: 10,
          totalElement: 0,
          totalPages: 0,
          currentRow: 0,
          sort: null,
          propertiesSort: null
        }
      },
      phieuTT_grid_temp: {},
      sumTemplate: function () {
        return { template: Vue.component('sumTemplate', {
          template: `<input type="text" class="form-control tright" :value="data.Sum" :disabled="true" style="margin: 7px 0px 7px 0px;" />`,
          data: function () {return {data: {data: {}}}}
        })
        }
      },
      countTemplate: function () {
        return { template: Vue.component('countTemplate', {
          template: `<input type="text" class="form-control tright" :value="data.Count" :disabled="true" style="margin: 7px 0px 7px 0px;" />`,
          data: function () {return {data: {data: {}}}}
        })
        }
      },
      customTemplate: function () {
        return  { template: Vue.component('footerTemplate', {
          template: `<input type="text" class="form-control tright" v-model="data.Custom" :disabled="true" style="margin: 7px 0px 7px 0px;" />`,
          data () { return { data: { data: {} } } }
        })
        }
      },
      checkboxColumnTTTemplate: function () {
        return {
          template: {
            template: `<div class="check-action" style="vertical-align: middle;">
                        <input type="checkbox" class="check" @change="onChanged" v-model="data.CHON"/>
                        <span class="name"></span>
                      </div>`,
            data () { return { data: {} } },
            methods: {
              onChanged () {
                EventBus.$emit('onPhieuCheckboxChanged', this.data)
              }
            }
          }}},
      statusTemplate () {
        return { template: Vue.component('columnTemplate', {
          template: `<div>
                        <span v-if="data.TRANGTHAI === '0'" class="one-alert f20 text-warning"></span>
                        <span v-else-if="data.TRANGTHAI === '1'" class="one-check f20 text-success"></span>
                      </div>`,
          data () { return { data: {} } }})
        }
      }
    }
  },

  methods: {
    bindMouseEvent () {
      this.$refs.phieuTraGrid.$refs.grid.ej2Instances.element.addEventListener(
        'mousedown',
        this.mousedown
      )
      this.$refs.phieuTraGrid.$refs.grid.ej2Instances.element.addEventListener(
        'mouseup',
        this.mouseup
      )
    },
    mouseup (e) {
      // console.log(e)
      if (
        e.target.classList.contains('e-rowcell')
      ) {
        let index = parseInt(e.target.getAttribute('Index'), 10)
        if (index < this.mouseStartIndex) {
          let t = this.mouseStartIndex
          this.mouseStartIndex = index
          this.mouseEndIndex = t
        } else this.mouseEndIndex = index

        let obj = this.$refs.phieuTraGrid.$refs.grid.ej2Instances
        if (obj.currentViewData.length > 0) {
          let data = obj.currentViewData
          let selectedCurrentPage = []
          // console.log(this.mouseStartIndex, this.mouseEndIndex)
          data.forEach((val, idx) => {
            if (idx >= this.mouseStartIndex && idx <= this.mouseEndIndex) {
              let f = this.phieuTT_grid.items.find(x => x.PHIEU_ID === val.PHIEU_ID)
              if (f._CHECK) f._CHECK = false
              else {
                f._CHECK = true
                selectedCurrentPage.push(idx)
              }              
            } else {
              if (val._CHECK) {
                selectedCurrentPage.push(idx)
              }
            }
          })
          obj.selectRows(selectedCurrentPage)
          this.refreshAggPhieu()
        }
      }
    },
    mousedown (e) {
      // console.log(e)
      if (
        e.target.classList.contains('e-rowcell')
      ) {
        let index = parseInt(e.target.getAttribute('Index'), 10)
        this.mouseStartIndex = index
      }
    },
    onCreate (e) {
      this.splitterObj = new Splitter({
        paneSettings: [
          { size: '50%', min: '20%' },
          { size: '50%', min: '20%' }
        ],
        orientation: 'Vertical'
      })
      this.splitterObj.appendTo('#vertical_splitter')
    },
    aggCountPhieu () {
      // return currency(this.phieuTT_grid.checked.length) + '/' + currency(this.phieuTT_grid.items.length)
      let selected = this.phieuTT_grid.items.filter(x => x._CHECK === true).length
      return `${selected}/${this.phieuTT_grid.items.length}`
    },
    aggSumTIENTRA () {
      // const checked = this.phieuTT_grid.checked.reduce((acc, curr) => {
      //   return acc = Number(curr.TIEN_TRA) + acc
      // }, 0)
      // const tongtien = this.phieuTT_grid.items.reduce((acc, curr) => {
      //   return acc = Number(curr.TIEN_TRA) + acc
      // }, 0)
      // return currency(checked) + '/' + currency(tongtien)
      const selected = this.phieuTT_grid.items.filter(x => x._CHECK === true).reduce((a, b) => +a + +b.TIEN_TRA, 0)
      const total = this.phieuTT_grid.items.reduce((a, b) => +a + +b.TIEN_TRA, 0)
      return currency(selected) + '/' + currency(total)
    },

    customAggregate () {
      return currency(this.common_params.tienTra_Checked) + '/' + currency(this.common_params.tienTra_Sum)
    },

    onMaTTKeyPress () {
      this.params.maTT = this.params.maTT.trim()
      // this.phieuTT_grid.key++

      if (this.params.maTT !== '') {
        let idx = this.phieuTT_grid_temp.items.findIndex(e => e.MA_TT === this.params.maTT)
        if (idx === -1) {
          this.phieuTT_grid.items = []
          // this.phieuTT_grid.checked = []
          this.resetPaging()
        } else {
          // this.phieuTT_grid_temp.items[idx]._CHECK = true
          // this.phieuTT_grid.items =  [...this.phieuTT_grid_temp.items]
          // this.phieuTT_grid.pageInfo =  {...this.phieuTT_grid_temp.pageInfo}
          // this.onPhieuCheckboxChangedHandler(this.phieuTT_grid.items[idx])

          // // Select row:
          // let timeOut = setInterval(() => {
          //   this.$refs.phieuTraGrid.setCurrentSelectedRow(idx)
          //   clearInterval(timeOut)
          // }, 200)
        }
      } else {
        this.phieuTT_grid.items =  [...this.phieuTT_grid_temp.items]
        this.phieuTT_grid.pageInfo =  {...this.phieuTT_grid_temp.pageInfo}
        this.phieuTT_grid.checked = []
        this.phieuTT_grid.items.forEach(item => {
          if (item.CHON) {
            this.onPhieuCheckboxChangedHandler(item)
          }
        })
      }
    },

    onPhieuCheckboxChangedHandler (args) {
      let index = this.phieuTT_grid.items.findIndex(e => e.PHIEU_ID === args.PHIEU_ID)
      if (index !== -1) {
        // CHECKED
        if (args.CHON) {
          this.phieuTT_grid.items[index].CHON = true
          this.common_params.tienTra_Checked += this.phieuTT_grid.items[index].TIEN_TRA
          this.phieuTT_grid.checked.push(args)
        } else {
        // UN-CHECKED
          this.phieuTT_grid.items[index].CHON = false
          this.common_params.tienTra_Checked -= this.phieuTT_grid.items[index].TIEN_TRA
          let indexChecked = this.phieuTT_grid.checked.findIndex(e => e.PHIEU_ID === args.PHIEU_ID)
          if (indexChecked !== -1)
            this.phieuTT_grid.checked.splice(indexChecked, 1)
        }

        // REFRESH DATASOURCE & FOOTER
        let dataGridObj = this.$refs.phieuTraGrid.$refs.grid.ej2Instances
        dataGridObj.aggregateModule.refresh()
        this.phieuTT_grid_temp = {...this.phieuTT_grid}
      }
    },

    async onDanhSach () {
      await this.getDSThuNgan()
      if (this.thuNgan_grid.items.length <= 0) {
        this.params.xoaPhieuBtn_Enable = false
        this.clear()
      } else this.params.xoaPhieuBtn_Enable = true
    },

    clear () {
      this.params.tongTra = 0
      this.thuNgan_grid.items = []
      this.hinhThucTT_grid.items = []
      this.phieuTT_grid.items = []
    },

    async onThuNganSelect (item) {
      this.thuNgan_grid.selected = item
      if (item) {
        this.params.tongTra = (item.TONGTIEN !== null && item.TONGTIEN !== undefined) ? item.TONGTIEN : 0
        await this.getDSHTTT(item.THUNGAN_ID)
      }
    },

    async onHTTTSelect (item) {
      this.hinhThucTT_grid.selected = item
      if (item != null && item != {}) {
        this.resetPaging()
        await this.getDSPhieu(item.HTTT_ID, this.thuNgan_grid.selected.THUNGAN_ID, this.thuNgan_grid.selected.LOAITIEN_ID, item.LANTRA)
      }
    },

    async phieuTraGridpageChangeHandler (e) {
      this.phieuTT_grid.pageInfo.page = e.pageIndex
      this.phieuTT_grid.pageInfo.maxSize = e.pageSize
      await this.getDSPhieu(this.hinhThucTT_grid.selected.HTTT_ID, this.thuNgan_grid.selected.THUNGAN_ID, this.thuNgan_grid.selected.LOAITIEN_ID, this.hinhThucTT_grid.selected.LANTRA)
    },

    resetPaging () {
      this.phieuTT_grid.pageInfo = {
        page: 0,
        maxSize: 10,
        totalElement: 0,
        totalPages: 0,
        currentRow: 0,
        sort: null,
        propertiesSort: null
      }
    },

    async onDelete () {
      if (this.checkDataOnGrid(1)) {
        const selectedRows = this.phieuTT_grid.items.filter(x => x._CHECK === true)
        if (this.common_params.kiemTra_MK) {
          this.phieuSelected = [...selectedRows]            
          if (selectedRows.length > 0) this.$refs.refXoaPhieuTT.openDialog()
        } else {
          try {
            const selected = selectedRows.length
            await this.$bvModal.msgBoxConfirm(`Bạn chắc chắn muốn xóa ${selected} phiếu thanh toán đã chọn không ?`, {
              'title': 'Thông báo',
              'size': 'lg',
              'buttonSize': 'lg',
              'okVariant': 'primary',
              'okTitle': 'Đồng ý',
              'cancelTitle': 'Hủy bỏ',
              'hideHeaderClose': false,
              'centered': true,
              'modal-class': ['f18', 'text-center'],
            })
              .then(async (value) => {
                if (value) {
                  await this.deleteProcess()
                }
              })
              .catch(err => { console.log(err) })
          } catch (error) {
            this.$toast.error('Có lỗi xảy ra, vui lòng thử lại!')
          }
        }
      }
    },

    async deletePhieuThuHandler (args) {
      if (args) await this.onDanhSach()
    },

    async deleteProcess () {
      let promises = []
      let apiBody = {
        pKyCuoc: this.thongtinKyHoaDon.kyHoaDonSelected,
        pMayHuy: '-',
        pNguoiHuy: this.$auth.getUserName(),
        pPhieuId: 0,
        pPassword: 'XXX'
      }
      const selectedRows = this.phieuTT_grid.items.filter(x => x._CHECK === true)
      for (let item of selectedRows) {
        if (!this.checkDate(item, 1)) continue


        // let ngayTT = moment(item.NGAY_TT, 'DD/MM/YYYY').toDate()
        // let ngayHT = new Date()
        // // Cùng tháng:
        // if (ngayTT.getFullYear() === ngayHT.getFullYear() && ngayTT.getMonth() === ngayHT.getMonth()) {
        //   if (!this.checkQuyenGach('XOAGACH', '0', ngayTT)) return
        // } else {
        //   // Khác tháng:
        //   if (!this.checkQuyenGach('XOAGACH', '1', ngayTT)) return
        // }

        if (item.PHIEU_ID_NEO != null && item.PHIEU_ID_NEO > 0) {
          if (item.NGUOI_CN !== this.$auth.getUserName()) {
            return this.$toast.error('Không được xóa phiếu thanh toán đã đồng bộ lên VNP của người khác!')
          }
        }
        // Check quyen_dd
        // {}

        // let result = await this.deletePhieuThu(item.PHIEU_ID)
        // if (result) count++
        apiBody.pPhieuId = item.PHIEU_ID
        promises.push(this.deletePhieuThu(apiBody))
      };

      this.loading(true)
      const res = await Promise.all(promises)
      this.loading(false)

      const ok = res.filter(x => x === true).length
      const total = promises.length

      if (ok === total) this.$toast.success(`Xóa thành công ${ok} / ${total}`)
      else this.$toast.error(`Đã phát sinh lỗi khi xoá phiếu. Xóa thành công ${ok} / ${total}`)
      await this.onDanhSach()
    },

    onChangeDate () {
      if (this.checkDataOnGrid(0)) {
        const selectedRows = this.phieuTT_grid.items.filter(x => x._CHECK === true)
        let ngayTT = moment(selectedRows[0].NGAY_TT, 'DD/MM/YYYY').toDate()

        if (this.common_params.ngayXoa_LuiTT > 0) {
          let today = new Date()
          let ngayGach_Max = new Date(today.getFullYear(), today.getMonth(), this.common_params.ngayXoa_LuiTT)
          if (today > ngayGach_Max && this.common_params.kyHoaDonHienTai !== this.thongtinKyHoaDon.kyHoaDonSelected)
            return this.$toast.error('Bạn không được thay đổi ngày thanh toán do ngày hiện tại không thuộc khoảng thời gian cho phép!')
          if (this.common_params.kyHoaDonHienTai !== this.thongtinKyHoaDon.kyHoaDonSelected) {
            let thang_TT = new Date(today.getFullYear(), today.getMonth() - 1, this.common_params.ngayXoa_LuiTT)
            if (thang_TT.getFullYear() !== ngayTT.getFullYear() || thang_TT.getMonth() !== ngayTT.getMonth())
              return this.$toast.error(`Bạn chỉ được phép sửa ngày thanh toán của tháng ${thang_TT.getMonth() + 1}`)
          }
        }

        this.phieuSelected = [...selectedRows]

        this.$refs.dlgThayDoiNgayTT.openDialog()
      }
    },

    async updateDatePhieuThuHandler (args) {
      if (args)
        await this.onDanhSach()
    },

    onChangeDetail () {
      const selectedRows = this.phieuTT_grid.items.filter(x => x._CHECK === true)
      if (selectedRows.length <= 0) {
        this.$toast.error('Chưa chọn phiếu để điều chỉnh.')
        return
      }
      let f = this.phieuTT_grid.items.filter(x => x.NHOM_HTTT_ID === 133 && x._CHECK === true)
      if (f.length > 0) {
        this.$toast.error('Bạn không được sửa thông tin phiếu của hình thức thanh toán gạch nợ POS!')
        return false
      }
      f = this.phieuTT_grid.items.filter(x => x.PHIEU_ID_NEO > 0 && x._CHECK === true)
      if (f.length > 0) {
        this.$toast.error(`Bạn không được sửa thông tin với dịch vụ di động. Do đã được gạch trên VNP!`)
        return false
      }
      this.phieuSelected = selectedRows
      this.$refs.dlgThayDoiThongTinTT.openDialog()
    },

    async changeDetailProcess (args) {
      // console.log(args, this.phieuTT_grid.checked[0])
      if (args) {
        // for (let item of this.phieuTT_grid.checked) {
        //   let apiBody = {
        //     thuNganId: args.thanhToanChecked ? args.thuNganId : this.thuNgan_grid.THUNGAN_ID,
        //     htttId: args.thanhToanChecked ? args.HTTTId : this.hinhThucTT_grid.HTTT_ID,
        //     lanTra: args.thanhToanChecked ? args.lanTra : item.LANTRA,
        //     ghiChu: args.ghiChuChecked ? args.ghiChu : null,
        //     chiPhi: args.chuyenKhoanChecked ? args.chiPhi : 0,
        //     chungTu: args.chuyenKhoanChecked ? args.chungTu : null,
        //     nganHangId: args.chuyenKhoanChecked ? args.nganHangId : null,
        //     ngayNganHang: args.chuyenKhoanChecked ? moment(args.ngayNganHang).format('DD/MM/YYYY HH:mm:ss') : null,
        //     kyCuoc: this.thongtinKyHoaDon.kyHoaDonSelected,
        //     matKhau: this.params.p_kiemtra_mk ? args.matKhau : '',
        //     phieuId: item.PHIEU_ID
        //   }
        //   let rs = await this.updateThongTinThanhToanV2(apiBody)
        //   if (rs) count++
        // };
        // if (count === this.phieuTT_grid.checked.length) this.$toast.success('Sửa thông tin thanh toán thành công!')

        let promises = []

        const selectedRows = this.phieuTT_grid.items.filter(x => x._CHECK === true)

        for (let i = 0; i < selectedRows.length; i++) {
          let apiBody = {
            thuNganId: args.thanhToanChecked ? args.thuNganId : selectedRows[i].THUNGAN_ID,
            htttId: args.thanhToanChecked ? args.HTTTId : selectedRows[i].HTTT_ID,
            lanTra: args.thanhToanChecked ? args.lanTra : selectedRows[i].LANTRA,
            ghiChu: args.ghiChuChecked ? args.ghiChu : selectedRows[i].GHICHU,
            chiPhi: args.chuyenKhoanChecked ? args.chiPhi : 0,
            chungTu: args.chuyenKhoanChecked ? args.chungTu : selectedRows[i].CHUNGTU,
            nganHangId: args.chuyenKhoanChecked ? args.nganHangId : selectedRows[i].NGANHANG_ID,
            ngayNganHang: args.chuyenKhoanChecked ? moment(args.ngayNganHang).format('DD/MM/YYYY HH:mm:ss') : selectedRows[i].NGAY_NH,
            kyCuoc: this.thongtinKyHoaDon.kyHoaDonSelected,
            matKhau: this.params.p_kiemtra_mk ? args.matKhau : '',
            phieuId: selectedRows[i].PHIEU_ID
          }
          promises.push(this.updateThongTinThanhToanV2(apiBody))
        }

        this.loading(true)
        const res = await Promise.all(promises)
        this.loading(false)

        const ok = res.filter(x => x === true).length
        const failed = res.filter(x => x === false).length
        if (ok === res.length) {
          this.$toast.success('Cập nhật thành công ' + ok + ' phiếu')
        } else {
          this.$toast.error('Cập nhật lỗi ' + failed + ' phiếu')
        }
        await this.onDanhSach()
      }
    },

    onXuatExcel () {
      if (this.phieuTT_grid.items.length <= 0) this.$toast.error('Chưa có dữ liệu để xuất excel!')
      else {
        this.$refs.phieuTraGrid.$refs.grid.getColumns()[0].visible = false
        this.$refs.phieuTraGrid.$refs.grid.getColumns()[1].visible = false
        this.$refs.phieuTraGrid.$refs.grid.getColumns()[29].visible = true
        this.$refs.phieuTraGrid.$refs.grid.getColumns()[30].visible = false

        let dataGridObj = this.$refs.phieuTraGrid.$refs.grid.ej2Instances
        let columns = dataGridObj.columnModel
        let excelExportProperties = {
          fileName: 'DSPhieuThanhToan.xlsx',
          dataSource: this.phieuTT_grid.items,
          columns: columns
        }
        this.loading(true)
        this.$refs.phieuTraGrid.$refs.grid.excelExport(excelExportProperties)
      }
    },

    onPrint () {
      if (this.phieuTT_grid.items.length <= 0) {
        this.$toast.error('Chưa có danh sách phiếu thanh toán để in!')
      } else if (this.phieuTT_grid.items.filter(x => x._CHECK === true).length <= 0) {
        this.$toast.error('Chưa chọn phiếu thanh toán để in!')
      } else this.$refs.dlgInPhieuThu.openDialog()
    },

    async acceptPrintPhieuThuHandler (e) {
      let phieuCount = 0
      let seri = parseInt(e.so_seri)

      if (e.is_print === 1) {
        this.loading(true)
        let dsPhieuCanIn = this.phieuTT_grid.items.filter(x => x._CHECK === true).map(e => e.PHIEU_ID)
        let postData = {
          kyCuoc: this.thongtinKyHoaDon.kyHoaDonSelected,
          dsPhieu: dsPhieuCanIn
        }
        try {
          const baseUrl = process.env.API
          this.axios.post(baseUrl + '/web-thuno/api/thu-no/gach-no/bien-nhan', postData, {responseType: 'blob'})
            .then ((response) => {
              previewPrint(response.data)
            })
        } catch (err) {
          this.$toast.error(err.data.message_detail)
        } finally {
          this.loading(false)
        }
      } else {
        const selectedRows = this.phieuTT_grid.items.filter(x => x._CHECK === true)
        for (let item of selectedRows) {
          let result = await this.updateSeriPhieuThu(e.seri, e.thung, seri + phieuCount, item.PHIEU_ID)
          if (result) phieuCount ++
          else this.$toast.error('Không thể cập nhật phiếu thanh toán có mã TT:' + item.MA_TT)
        };

        if (phieuCount === selectedRows.length)
          this.$toast.success('Cập nhật phiếu thanh toán thành công!')

        await this.onDanhSach()
      }
    },

    async onSynVNP () {
      if (this.phieuTT_grid.items.length <= 0) {
        return this.$toast.error(`Không có dữ liệu phiếu trả đồng bộ!`)
      }
      if (this.phieuTT_grid.items.filter(x => x._CHECK === true).length <= 0) {
        return this.$toast.error(`Bạn chưa check chọn phiếu trả để đồng bộ VNP!`)
      }
      const selectedRows = this.phieuTT_grid.items.filter(x => x._CHECK === true)
      let ds = selectedRows.filter(e => (e.PHIEU_ID_NEO == null || e.PHIEU_ID_NEO === 0) && (e.TRANGTHAI === '-1' || e.TRANGTHAI === '1'))
      if (ds.length > 0) {
        return this.$toast.error(`Bạn không được chọn phiếu đã đồng bộ hoặc phiếu không phải dịch vụ di động!`)
      }
      /*
      let count = 0;
      for (let item of this.phieuTT_grid.checked) {
        let dt = [];
        dt.push({
          kyCuoc: this.thongtinKyHoaDon.kyHoaDonSelected,
          httt: item.HTTT_ID,
          maTT: item.MA_TT,
          nguoiCN: item.NGUOI_CN,
          phieuId: item.PHIEU_ID,
          phieuIdNeo: item.PHIEU_ID_NEO,
          trangThai: item.TRANGTHAI
        });
        let rs = await this.updateDongBoCCBS(dt, this.hinhThucTT_grid.selected.NHOM_HTTT_ID);
        if (rs) count++;
      }

      if (count === this.phieuTT_grid.checked.length)
        this.$toast.success("Đồng bộ VNP thành công!");

      await this.onDanhSach();
      */
      let promises = []
      selectedRows.forEach((item) => {
        let postData = {
          'kieu': 1,
          'kyCuoc': this.thongtinKyHoaDon.kyHoaDonSelected,
          'phieuId': item.PHIEU_ID
        }
        promises.push(this.doSyncCCBS(postData))
      })
      this.loading(true)
      const res = await Promise.all(promises)
      this.loading(false)
      let ok = res.filter(x => x === true)
      let failed = res.filter(x => x === false)

      if (ok.length === promises.length) {
        this.$toast.success('Đã đồng bộ thành công ' + ok.length + ' phiếu.')
        this.doSearch()
      } else this.$toast.error('Đã phát sinh lỗi khi đồng bộ. Tổng số phiếu lỗi: ' + failed.length)
    },

    async doSyncCCBS (postData) {
      try {
        const res = await GachNoAPI.syncCCBS(this.axios, postData)
        const data = await res.data

        if (data.error_code === 'BSS-00000000') {
          if (data.data !== undefined && data.data[0].phieuIdNeo !== null) return true
        }
        return false
      } catch (error) {
        return false
      } finally {}
    },

    async onSynHDDT () {
      if (this.phieuTT_grid.items.length <= 0)
        return this.$toast.error(`Chưa có danh sách phiếu thanh toán để đồng bộ!`)
      if (this.phieuTT_grid.checked.length <= 0)
        return this.$toast.error(`Chưa chọn phiếu thanh toán để đồng bộ!`)
      let ds = this.phieuTT_grid.checked.filter(e => e.STATUS.toString().toLowerCase() === 'ok:')
      if (ds.length > 0)
        return this.$toast.error(`Phiếu thanh toán ${ds[0].MA_TT} đã đồng bộ rồi. Hãy kiểm tra lại!`)

      try {
        await this.$bvModal.msgBoxConfirm('Bạn thật sự muốn đồng bộ hóa đơn điện tử không?', {
          'title': 'Thông báo',
          'size': 'lg',
          'buttonSize': 'lg',
          'okVariant': 'primary',
          'okTitle': 'Đồng ý',
          'cancelTitle': 'Hủy bỏ',
          'hideHeaderClose': false,
          'centered': true,
          'modal-class': ['f18', 'text-center'],
        })
          .then(async (value) => {
            if (value) {
              let ds = this.phieuTT_grid.checked.map(e => e.PHIEU_ID)
              let rs = await this.updateDongBoHDDT(ds)

              if (rs)
                this.$toast.success('Đồng bộ phiếu trả lên trang hóa đơn điện tử thành công!')

              this.resetPaging()
              await this.getDSPhieu(this.hinhThucTT_grid.selected.HTTT_ID, this.thuNgan_grid.selected.THUNGAN_ID, this.thuNgan_grid.selected.LOAITIEN_ID, this.hinhThucTT_grid.selected.LANTRA)
            }
          })
          .catch(err => {
          // An error occurred
            console.log(err)
          })
      } catch (error) {
        this.$toast.error('Có lỗi xảy ra, vui lòng thử lại!')
      }
    },

    checkDataOnGrid (type) {
      let action = ''
      if (type === 0) action = 'cập nhật'
      else if (type === 1) action = 'xóa'

      const selectedRows = this.phieuTT_grid.items.filter(x => x._CHECK === true)

      if (selectedRows.length <= 0) {
        this.$toast.error(`Chưa có danh sách phiếu thanh toán để ${action}!`)
        return false
      }
      if (selectedRows.length <= 0) {
        this.$toast.error(`Chưa chọn phiếu thanh toán để ${action}!`)
        return false
      }
      if (this.common_params.kyHoaDonHienTai !== this.thongtinKyHoaDon.kyHoaDonSelected
        && this.common_params.ngayXoa_LuiTT === -1) {
        this.$toast.error(`Bạn không được ${action} phiếu của kỳ hóa đơn khác!`)
        return false
      }
      return true
    },

    /* params[1]: phieu - Thông tin phiếu trả
     * params[2]: kieu - Kiểu thực hiện: 0 - Cập nhật ghi chú; 1 - Xóa phiếu
     * return: true/false
     */
    checkDate (phieu, kieu) {
      let ngayTT = moment(phieu.NGAY_TT, 'DD/MM/YYYY').toDate()
      let ngayHT = new Date()

      let action = ''
      if (kieu === 0) action = 'cập nhật thông tin'
      else if (kieu === 1) action = 'xóa'

      // Khác tháng
      if (ngayTT.getFullYear() !== ngayHT.getFullYear() || ngayTT.getMonth() !== ngayHT.getMonth()) {
        if (this.common_params.ngayXoa_LuiTT === -1) {
          this.$toast.error(`Bạn không được phép ${action} phiếu của của mã TT: ${phieu.MA_TT}. Do chưa được thiết lập ngày cho phép xóa lùi`)
          return false
        }

        let ngayGach_Max = new Date(ngayHT.getFullYear(), ngayHT.getMonth(), this.common_params.ngayXoa_LuiTT)
        if (ngayHT > ngayGach_Max) {
          this.$toast.error(`Bạn không được phép ${action} phiếu của của mã TT: ${phieu.MA_TT}. Do ngày hiện tại không thuộc khoảng thời gian cho phép`)
          return false
        }

        let thangTT = new Date(ngayHT.getFullYear(), ngayHT.getMonth() - 1, this.common_params.ngayXoa_LuiTT)
        let firstDateOfMonth = new Date(thangTT.getFullYear(), thangTT.getMonth(), 1)
        let lastDateOfMonth = new Date(thangTT.getFullYear(), thangTT.getMonth() + 1, 0)
        if (ngayTT < firstDateOfMonth || ngayTT > lastDateOfMonth) {
          this.$toast.error(`Bạn không được phép ${action} phiếu của của mã TT: ${phieu.MA_TT} do ngày thanh toán khác tháng ${thangTT.getMonth() + 1}`)
          return false
        }
      } else {
        // Cùng tháng
        let days = this.differenceDates(ngayTT, ngayHT)
        if (ngayTT < ngayHT) {
          if (this.common_params.ngayXoa_Lui > -1 && this.common_params.ngayXoa_Lui < days) {
            this.$toast.error(`Bạn không được phép ${action} phiếu của của mã TT: ${phieu.MA_TT}. Thời điểm cho phép cập nhật là cách ngày hiện tại ${days} ngày!`)
            return false
          }
        }
      }
      return true
    },

    /* params[1]: kieuGach - '0': trong tháng; '1': tháng trước
     * params[2]: kieuGach - 'GACHNO'; 'XOALUI'
     * params[3]: ngayTT - Ngày thành toán của phiếu
     * return: true/false
     */
    checkQuyenGach (kieuGach, kieu, ngayTT) {
      if (this.common_params.quyenGN_Ngay) {
        let kieuQuyen = 0  // 1 - quyenGN_Thang; 2 - quyenXoa_Thang; 3 - quyenGN_Lui_Thang; 4 - quyenXoa_Lui_Thang
        // Get
        // lay_ds_quyen_gachno
        //
        if (kieu === 'GACHNO')
          kieuQuyen = kieuGach === '0' ? 1 : 3
        else // 'XOAGACH'
          kieuQuyen = kieuGach === '0' ? 2 : 4
        let quyen = this.getQuyenGach(kieuQuyen)
        return this.onCheckQuyen(kieuGach, kieu, kieuQuyen, quyen, ngayTT)
      } else return true
    },

    onCheckQuyen (kieuGach, kieu, kieuQuyen, quyen, ngayTT) {
      let action = kieu === 'XOAGACH' ? 'xóa phiếu' : 'gạch lùi'
      let time = kieuGach === '0' ? 'của tháng hiện tại' : 'của tháng trước'
      let today = new Date()

      if (ngayTT.getDate() < today.getDate()) {
        if (this.common_params.ds_Quyen.length <= 0) {
          this.$toast.error(`Bạn chưa có quyền được ${action} ${time}!`)
          return false
        }

        let day = ngayTT.getDate().toString().padStart(2, '0')
        if (quyen === -1) {
          this.$toast.error(`Bạn chưa có quyền được ${action} ${time}!`)
          return false
        } else if (quyen === 1) {
          if (this.common_params.ds_Quyen.filter(e => e.KIEU === kieuQuyen && e.ND === 0 && e.NGAY===day).length <= 0) {
            this.$toast.error(`Bạn không có quyền ${action} vào ngày ${day} ${time}!`)
            return false
          }
        } else {  // === 2
          if (this.common_params.ds_Quyen.filter(e => e.KIEU === kieuQuyen && e.ND === 1 && e.NGAY===day).length <= 0) {
            this.$toast.error(`Bạn không có quyền ${action} vào ngày ${day} ${time}!`)
            return false
          }
        }
        return true
      }
    },

    /* kieu: 1 - quyenGN_Thang; 2 - quyenXoa_Thang; 3 - quyenGN_Lui_Thang; 4 - quyenXoa_Lui_Thang */
    getQuyenGach (kieuQuyen) {
      if (this.common_params.ds_Quyen !== undefined && this.common_params.ds_Quyen.length > 0) {
        // Lấy quyền ND trước:
        let dsND_DayOfMonth = this.common_params.ds_Quyen.filter(e => e.KIEU === kieuQuyen && e.ND === 1)
        if (dsND_DayOfMonth.length <= 0) {
          // Nếu không có quyền ND thì lấy quyền nhóm ND:
          let dsNND_DayOfMonth = this.common_params.ds_Quyen.filter(e => e.KIEU === kieuQuyen && e.ND === 0)
          return dsNND_DayOfMonth.length <= 0 ? -1 : 1
        } else return 2
      } else return -1
    },

    /* params[1]: date1 - Date object
     * params[2]: date1 - Date object
     * return: Number
     */
    differenceDates (date1, date2) {
      let diff = Math.abs(date1 - date2)
      return diff / (1000 * 3600 * 24)
    },

    async getKyCuocHienHanh () {
      try {
        this.common_params.kyHoaDonHienTai = moment(new Date()).subtract(1, 'months').startOf('month').format('YYYYMMDD').toString()
        let response = await CommonsAPI.getKyCuocHienHanh(this.axios)
        if (response.data.error_code === 'BSS-00000000' && response.data.data !== undefined) {
          this.common_params.kyHoaDonHienTai = response.data.data[0].KYCUOC.toString()
        }
        return null
      } catch (error) {
        console.log(error)
        return null
      } finally { }
    },

    async getThongTinChuKyNo () {
      try {
        this.loading(true)
        let response = await CommonsAPI.getThongTinChuKyNo(this.axios, this.thongtinKyHoaDon.kyHoaDonSelected)
        if (response.data.error_code === 'BSS-00000000' && response.data.data !== undefined) {
          this.common_params.ngay_BD = response.data.data[0].NGAY_BD
          this.common_params.ngay_KT = response.data.data[0].NGAY_KT
        }
      }
      catch (error) {
        console.log(error)
      }
      finally {
        this.loading(false)
      }
    },

    async getDanhSachThamSoMD (id) {
      try {
        let response = await DAPI.getDanhSachThamSoMD(this.axios, id)
        if (response.data.error_code === 'BSS-00000000' && response.data.data !== undefined) {
          response.data.data.forEach(item => {
            if (item.MA_TS === 'KHONG_KT_MATKHAU_XOA_GACHNO' && item.TEN_TS.trim() === '1') {
              this.common_params.kiemTra_MK = false
            }              
            if (item.MA_TS === 'QUYENGN_NGAY' && item.TEN_TS.trim() === '1') {
              this.common_params.quyenGN_Ngay = true
            }
          })
        }
        return null
      }
      catch (error) {
        console.log(error)
        return null
      } finally { }
    },

    async getDSThuNgan () {
      try {
        this.loading(true)
        this.thuNgan_grid.key++
        this.thuNgan_grid.items = []
        this.thuNgan_grid.checked = []
        let apiParams = {
          pKyCuoc: this.thongtinKyHoaDon.kyHoaDonSelected,
          pNgayThanhToan: moment(this.params.ngayTT).format('DD/MM/YYYY'),
          pDenNgay: this.params.denNgay_Checked ? moment(this.params.denNgay).format('DD/MM/YYYY') : null
        }
        let response = await API.getDSThuNgan(this.axios, apiParams)
        if (response.data.error_code === 'BSS-00000000' && response.data.data !== undefined) {
          this.thuNgan_grid.items = response.data.data
        }
      } catch (error) {
        console.log(error)
      } finally {
        this.loading(false)
      }
    },

    async getDSHTTT (thuNganId) {
      try {
        this.loading(true)
        this.hinhThucTT_grid.key++
        this.hinhThucTT_grid.items = []
        let apiParams = {
          pKyCuoc: this.thongtinKyHoaDon.kyHoaDonSelected,
          pNgayThanhToan: moment(this.params.ngayTT).format('DD/MM/YYYY'),
          pDenNgay: this.params.denNgay_Checked ? moment(this.params.denNgay).format('DD/MM/YYYY') : null,
          pThuNganId: thuNganId
        }
        let response = await API.getDSHinhThucTT(this.axios, apiParams)
        if (response.data.error_code === 'BSS-00000000' && response.data.data !== undefined) {
          this.hinhThucTT_grid.items = response.data.data
        }
      } catch (error) {
        console.log(error)
      } finally {
        this.loading(false)
      }
    },

    async getDSPhieu (htttId, thuNganId, loaiTienId, lanTra) {
      try {
        this.loading(true)
        // this.phieuTT_grid.key++
        this.phieuTT_grid.items = []
        this.phieuTT_grid_temp = {}
        this.phieuTT_grid.checked = []
        this.common_params.tienTra_Sum = 0
        this.common_params.tienTra_Checked = 0
        let apiParams = {
          pKyCuoc: this.thongtinKyHoaDon.kyHoaDonSelected,
          pHTTTId: htttId,
          pThuNganId: thuNganId,
          pLoaiTien: loaiTienId,
          pNgayThanhToan: moment(this.params.ngayTT).format('DD/MM/YYYY'),
          pDenNgay: this.params.denNgay_Checked ? moment(this.params.denNgay).format('DD/MM/YYYY') : null,
          pLanTra: lanTra,
          pTinhId: '',
          pMaTT: '',
          pMaTB: '',
          pageNo: this.phieuTT_grid.pageInfo.page + 1,
          pageSize: this.phieuTT_grid.pageInfo.maxSize,
          totalRow: 1
        }
        let response = await API.getDSPhieuThanhToan(this.axios, apiParams)
        if (response.data.error_code === 'BSS-00000000' && response.data.data !== undefined) {
          response.data.data.data.forEach(function (element) {
            element._CHECK = false
            element.TIEN_TRA_ = currency(element.TIEN_TRA)
          })
          this.phieuTT_grid.items = response.data.data.data

          // console.log(this.phieuTT_grid.items)
          // this.phieuTT_grid.key++
          // this.bindMouseEvent()

          // console.log(this.phieuTT_grid)
          // this.common_params.tienTra_Sum = 0
          // this.phieuTT_grid.items = response.data.data.data
          this.phieuTT_grid.pageInfo = {
            page: response.data.data.page,
            maxSize: response.data.data.maxSize,
            totalElement: response.data.data.totalElement,
            currentRow: 0            
          }
          // response.data.data.data.forEach(item => {
          //   this.common_params.tienTra_Sum += item.TIEN_TRA
          // })
        } else this.resetPaging()
        this.phieuTT_grid_temp = {...this.phieuTT_grid}
      } catch (error) {
        console.log(error)
        this.resetPaging()
        this.$toast.error(error.data.message_detail)
      } finally {
        this.loading(false)
      }
    },

    /* Return: true/false = successful/failed */
    async deletePhieuThu (postData) {
      try {
        const response = await XLAPI.deletePhieuThanhToan(this.axios, postData)
        const data = response.data
        return data.error_code === 'BSS-00000000'
      } catch (error) {
        this.$toast.error(error.data.message_detail)
        return false
      } finally {}
    },

    /* Params: apiBody: { pKyCuoc: Number, pPhanVungId: Number, pPhieuId: Number, pNgayThanhToan: String DD/MM/YYYY } */
    /* Return: true/false = successful/failed */
    async updateNgayThanhToan (apiBody) {
      try {
        let response = await XLAPI.updateNgayThanhToan(this.axios, apiBody)
        return response.data.error_code === 'BSS-00000000' ? true : false
      }
      catch (error) {
        console.log(error)
        return false
      }
      finally {}
    },

    /* Return: true/false = successful/failed */
    async updateThongTinThanhToanV2 (apiBody) {
      try {
        const response = await XLAPI.updateThongTinThanhToanV2(this.axios, apiBody)
        const data = await response.data
        return data.error_code === 'BSS-00000000'
      } catch (error) {
        // console.log(error)
        return false
      } finally {
      }
    },

    async updateSeriPhieuThu (seri, thung, soSeri, phieuId) {
      try {
        this.loading(true)
        let apiBody = {
          pKyCuoc: this.thongtinKyHoaDon.kyHoaDonSelected,
          pSeri: seri,
          pQuyen: thung,
          pSoSeri: soSeri,
          pPhieuId: phieuId
        }
        let response = await GachNoAPI.updateSeriThanhToan(this.axios, apiBody)
        return response.data.error_code === 'BSS-00000000' ? true : false
      }
      catch (error) {
        this.$toast.error(error.data.message_detail)
        return false
      }
      finally {
        this.loading(false)
      }
    },

    async updateDongBoCCBS (data, nhomHTTT) {
      try {
        this.loading(true)
        let apiBody = {
          data: data,
          nhomHTTT: nhomHTTT
        }
        let response = await API.updateDongBoCCBS(this.axios, apiBody)
        return response.data.error_code === 'BSS-00000000' ? true : false
      }
      catch (error) {
        this.$toast.error(error.data.message_detail)
        return false
      }
      finally {
        this.loading(false)
      }
    },

    async getKiemTraMatKhau () {
      try {
        const res = await CommonsAPI.getThamSoMacDinh(this.axios, {pMaThamSo: 'KHONG_KT_MATKHAU_XOA_GACHNO'})
        const data = await res.data

        if (data.error_code === 'BSS-00000000' && data.data !== undefined && data.data.length > 0) {
          let ts = Number(data.data[0].TEN_TS)
          if (ts === 1) {
            this.params.p_kiemtra_mk = false
          }
        }
      } catch (error) {
        if (error.data.message_detail !== undefined) {
          this.$toast.error(error.data.message_detail)
        } else console.log(error)
      }
    },

    async updateDongBoHDDT (dsPhieu) {
      try {
        this.loading(true)
        let apiBody = {
          kyCuoc: this.thongtinKyHoaDon.kyHoaDonSelected,
          dsPhieu: dsPhieu
        }
        let response = await API.updateDongBoHDDT(this.axios, apiBody)
        return response.data.error_code === 'BSS-00000000' ? true : false
      }
      catch (error) {
        console.log(error)
        this.$toast.error(error.data.message_detail)
        return false
      }
      finally {
        this.loading(false)
      }
    },

    async chukycuocChangeHandler (e) {
      this.thongtinKyHoaDon.kyHoaDonSelected =moment(e.p_kyhoadon).format('YYYYMM') + '01'
      this.thongtinKyHoaDon.chuKySelected = e.p_chuky_no
      await this.getThongTinChuKyNo()
    },
    checkBoxChange (args) {
      // console.log(args)
      if (args.target.classList.contains('e-checkselectall')) { // click check/uncheck all
        // if (args.checked) this.phieuTT_grid.checked = cloneArray(this.phieuTT_grid.items)
        // else this.phieuTT_grid.checked = []
        // console.log(this.phieuTT_grid.checked)
        for (let i = 0; i < this.phieuTT_grid.items.length; i++) {
          this.phieuTT_grid.items[i]._CHECK = args.checked
        }
        this.refreshAggPhieu()
      }
    },
    refreshAggPhieu () {
      this.$refs.phieuTraGrid.$refs.grid.ej2Instances.aggregateModule.refresh()
    },
    refreshGrid () {
      let obj = this.$refs.phieuTraGrid.$refs.grid.ej2Instances
      if (obj.currentViewData.length > 0) {
        let data = obj.currentViewData
        let selectedCurrentPage = []
        data.forEach((value, index) => {
          if (value._CHECK) {
            selectedCurrentPage.push(index)
          }
        })
        obj.selectRows(selectedCurrentPage)
      }
    },
    dsPhieuRowSelected (args) {
      if (!args.isHeaderCheckboxClicked && args.isInteracted) { // không phải call by click checkbox header
        // this.updatePhieuSelected(args.data)
        let f = this.phieuTT_grid.items.find(x => x.PHIEU_ID === args.data.PHIEU_ID)
        if (f !== undefined) f._CHECK = true
        this.refreshAggPhieu()
      }
    },
    dsPhieuRowDeselected (args) {
      if (!args.isHeaderCheckboxClicked && args.isInteracted) { // không phải call by click checkbox header & paging
        // this.updatePhieuSelected(args.data)
        let f = this.phieuTT_grid.items.find(x => x.PHIEU_ID === args.data.PHIEU_ID)
        if (f !== undefined) f._CHECK = false
        this.refreshAggPhieu()
      }
    },
    updatePhieuSelected (data) {
      if (Object.prototype.toString.apply(data) === '[object Array]') {
        data.forEach((item) => {
          const index = this.phieuTT_grid.checked.findIndex(x => x.PHIEU_ID === item.PHIEU_ID)
          if (index > -1) { // found
            this.phieuTT_grid.checked.splice(index, 1)
          } else {
            this.phieuTT_grid.checked.push(item)
          }
        })
      } else {
        const index = this.phieuTT_grid.checked.length > 0 ? this.phieuTT_grid.checked.findIndex(x => x.PHIEU_ID === data.PHIEU_ID) : -1
        if (index > -1) {
          this.phieuTT_grid.checked.splice(index, 1)
        } else {
          this.phieuTT_grid.checked.push(data)
        }
      }
    },
    dsPhieuDatabound (args) {
      this.refreshGrid()
      // console.log('databound')
      // let obj = this.$refs.phieuTraGrid.$refs.grid.ej2Instances
      // if (obj.currentViewData.length > 0 && this.phieuTT_grid.checked.length > 0) {
      //   let data = obj.currentViewData
      //   let selectedCurrentPage = []
      //   data.forEach((value, index) => {
      //     if (this.phieuTT_grid.checked.find(x => x.PHIEU_ID === value.PHIEU_ID) !== undefined) {
      //       selectedCurrentPage.push(index)
      //     }
      //   })
      //   obj.selectRows(selectedCurrentPage)
      // }
    },
    keyPressedHandler (args) {
      if (args.key === 'Insert') {
        this.checkAll(true)
      } else if (args.key === 'Delete') {
        this.checkAll(false)
      }
    },
    checkAll (f) {
      let temp = [...this.phieuTT_grid.items]
      if (f) { // true
        let filter = this.$refs.phieuTraGrid.getCurrentFilter()
        if (filter !== undefined && filter.fieldName !== undefined && filter.value !== '') {
          let checked = this.$refs.phieuTraGrid.filteredDataSource.map(x => x.PHIEU_ID)
          for (let i = 0; i < temp.length; i++) {
            if (checked.includes(temp[i].PHIEU_ID)) {
              temp[i]._CHECK = true
            } else {
              temp[i]._CHECK = false
            }
          }        
        } else {
          for (let i = 0; i < temp.length; i++) temp[i]._CHECK = true
        }
      } else {
        for (let i = 0; i < temp.length; i++) temp[i]._CHECK = false
      }
      this.phieuTT_grid.items = [...temp]
    },
    queryCellInfoHandler (args) {
      if (args.column.field === 'TIEN_TRA') {
        args.column.value = currency(args.column.value)
      }
    },
    excelExportCompleteHandler (args) {
      this.loading(false)
    }
  },

  watch: {
  }
}
</script>
