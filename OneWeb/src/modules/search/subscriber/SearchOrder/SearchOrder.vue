<template src="./template.html"> </template>

<script>
import breadcrumb from '@/modules/contract/setup/DeclareLandline/components/breadcrumb.vue'
import Vue from 'vue'
import ThongTinDonHang from './popups/ThongTinDonHang/ThongTinDonHang.vue'
import api from './API.js'
import moment from 'moment'
import xlsx from 'xlsx'
import FileSaver from 'file-saver'
import StatusButton from './popups/StatusButton.vue'
import MultiSelect from '@/components/Controls/QT_Components/MultiSelect/index.vue'

export default {
  components: {
    breadcrumb,
    ThongTinDonHang,
    StatusButton,
    MultiSelect
  },
  data() {
    return {
      listSapQuaHan: [],
      listDaQuaHan: [],
      filter_sapquahan: false,
      filter_daquahan: false,
      tbDSDonHang: {
        data: [],
        columns: [
          { fieldName: 'ma_gd', headerText: 'Mã đơn hàng', template: this.colMaDonHang(this) },
          { fieldName: 'ngay_yc', headerText: 'Ngày yêu cầu', allowFiltering: true, allowSorting: true },
          { fieldName: 'ten_kh', headerText: 'Tên khách hàng', allowFiltering: true, allowSorting: true },
          { fieldName: 'diachi_kh', headerText: 'Địa chỉ khách hàng', allowFiltering: true, allowSorting: true },
          { fieldName: 'so_dt', headerText: 'Số điện thoại', allowFiltering: true, allowSorting: true },
          { fieldName: 'loaihinh_tb', headerText: 'Loại hình', allowFiltering: true, allowSorting: true },
          { fieldName: 'goicuoc', headerText: 'Gói cước', allowFiltering: true, allowSorting: true },
          { fieldName: 'trangthai_hd', headerText: 'Trạng thái đơn', allowFiltering: true, allowSorting: true },
          { fieldName: 'statusName', headerText: 'Trạng thái sim', allowFiltering: true, allowSorting: true }
        ]
      },
      tbChiTietPhanGiao: {
        data: [],
        columns: [
          { fieldName: 'huonggiao', headerText: 'Hướng giao', isGroupedColumn: true },
          { fieldName: 'dvg', headerText: 'Đơn vị giao', allowFiltering: true, allowSorting: true },
          { fieldName: 'ngay_giao', headerText: 'Ngày giao', allowFiltering: true, allowSorting: true },
          { fieldName: 'nv_giao', headerText: 'Người giao', allowFiltering: true, allowSorting: true },
          { fieldName: 'nd_giao', headerText: 'Nội dung', allowFiltering: true, allowSorting: true },
          { fieldName: 'dvn', headerText: 'Đơn vị nhận', allowFiltering: true, allowSorting: true },
          { fieldName: 'nv_th', headerText: 'Người xử lý', allowFiltering: true, allowSorting: true },
          { fieldName: 'trangthai_ph', headerText: 'Trạng thái phiếu', allowFiltering: true, allowSorting: true }
        ]
      },
      dateFormat: 'dd/MM/yyyy',
      cbbNguonDon: {
        options: [],
        value: []
      },
      cbbLoaiKH: {
        options: [],
        value: null
      },
      cbbDichVuVT: {
        options: [{id: 0, text: 'Tất cả'}],
        value: null
      },
      cbbLoaiHinh: {
        options: [{id: 0, text: 'Tất cả'}],
        value: null,
        disable: false
      },
      cbbDonVi: {
        options: [],
        value: [],
        enabled: false
      },
      cbbLoaiNV: {
        options: [
          { id: 68, text: 'Nhân viên xử lý đơn hàng' },
          { id: 71, text: 'SC tỉnh' }
        ],
        value: 68,
        enabled: false
      },
      cbbTTSim: {
        options: [],
        value: null,
        enabled: false
      },
      cbbNhanVien: {
        options: [],
        value: null,
        enabled: false,
        disableChk: true
      },
      cbbTrangThai: {
        options: [],
        value: null,
        enabled: false
      },
      colorList: ['#D3C1FA', '#B9E5FE', '#66B2EC', '#D29211', '#82EB37', '#DB64B5', '#F4653B', '#FFD04B', '#04AE6D'],
      dtpTuNgay: {
        value: new Date(new Date().setDate(new Date().getDate() - 6)),
        enabled: true
      },
      dtpDenNgay: {
        value: new Date(),
        enabled: true
      },
      phanvung_id: null,
      txtMaKenhBan: {
        value: null,
        enabled: false
      },
      txtDienThoaiKenh: {
        value: null,
        enabled: false
      },
      txtMaDonHang: {
        value: null,
        enabled: false
      },
      ma_nv: null,
      currentDH: null,
      ttd_filter: '-1',
      tts_filter: '-1'
    }
  },
  computed: {
    cbbNguonDoncomputed() {
      return [
        {
          label: 'Tất cả',
          children: this.cbbNguonDon.options,
          id: '-1'
        }
      ]
    },
    cbbDonVicomputed() {
      return this.cbbDonVi.options
    },
    tbDSDonHang_computedData() {
      if (this.ttd_filter == '-1' && this.tts_filter == '-1') return this.tbDSDonHang.data
      return this.tbDSDonHang.data.filter((item) => {
        return (this.ttd_filter == '-1' || item.tthd_id == this.ttd_filter) && (this.tts_filter == '-1' || item.status == this.tts_filter)
      })
    }
  },
  methods: {
    filterbyTTD(value) {
      this.filter_sapquahan = false;
      this.filter_daquahan = false;
      if (this.ttd_filter == value) this.ttd_filter = '-1'
      else this.ttd_filter = value
    },
    filterbyTTS(value) {
      this.filter_sapquahan = false;
      this.filter_daquahan = false;
      if (this.tts_filter == value) this.tts_filter = '-1'
      else this.tts_filter = value
    },
    LamTronGio(time,format){
      let t = moment(time,format);
      // 14h15 => 15h
      let h = moment(time,format).hour()
      let m = moment(time,format).minute()
      if(m > 0){
        h++
      }
      return t.hour(h).minute(0).second(0).format(format);
    },
    KhoangCachGio(time1,time2,format){
      let t1 = moment(time1,format);
      let t2 = moment(time2,format);
      let diff = t2.diff(t1,'minutes');
      return diff/60;
    },
    toggleFilter_Sapquahan(){
      this.filter_sapquahan = !this.filter_sapquahan;
      this.tts_filter = '-1';
      this.ttd_filter = '-1';
    },
    toggleFilter_Daquahan(){
      this.filter_daquahan = !this.filter_daquahan;
      this.tts_filter = '-1';
      this.ttd_filter = '-1';
    },
    colMaDonHang(parent) {
      return function() {
        return {
          template: Vue.component('tbDSDonHang_colMaDonHang', {
            template: `<a href="javascript:void(0)" @click="openThongTin">{{madonhang}}</a>`,
            mounted() {
              this.madonhang = this.data.ma_gd
            },
            data() {
              return {
                madonhang: ''
              }
            },
            methods: {
              openThongTin() {
                parent.$refs.ThongTinDonHangModal.hdkh_id = this.data.hdkh_id
                parent.$refs.ThongTinDonHangModal.phanvung_id = parent.phanvung_id
                parent.$refs.ThongTinDonHangModal.showModal()
              }
            }
          })
        }
      }
    },
    async LOAD_COMBO() {
      try {
        this.loading(true)
        let res = await api.getDSNguonDon(this.axios, {})
        if (res.data.error_code == 'BSS-00000000') {
          this.cbbNguonDon.options = res.data.data.map((item) => {
            return {
              id: item.ungdung_id,
              label: item.ten_ungdung
            }
          })
        }
        // this.cbbNguonDon.options = [{id: -1, text: '---Không chọn---'}].concat(this.cbbNguonDon.options)

        res = await api.getDSLoaiKH(this.axios, {})
        if (res.data.error_code == 'BSS-00000000') {
          this.cbbLoaiKH.options = res.data.data.map((item) => {
            return {
              id: item.loaikh_id,
              text: item.loai_kh
            }
          })
          this.cbbLoaiKH.value = this.cbbLoaiKH.options[0].id
        }
        // this.cbbLoaiKH.options = [{id: -1, text: '---Không chọn---'}].concat(this.cbbLoaiKH.options)
        res = await api.getDSDichVuVT(this.axios, {})
        if (res.data.error_code == 'BSS-00000000') {
          res.data.data.forEach((item) => {
            this.cbbDichVuVT.options.push({
              id: item.dichvuvt_id,
              text: item.ten_dvvt
            })
          })
          this.cbbDichVuVT.value = 2
        }
        // this.cbbDichVuVT.options = [{id: -1, text: '---Không chọn---'}].concat(this.cbbDichVuVT.options)
        res = await api.getDSDonVi(this.axios)
        this.cbbDonVi.value = []
        if (res.data.error_code == 'BSS-00000000') {
          this.cbbDonVi.options = res.data.data.map((item) => {
            // this.cbbDonVi.value.push(item.donvi_id)
            return {
              id: item.donvi_id,
              label: item.ten_dv
            }
          })
        }
        this.cbbDonVi.value = ['-1']

        res = await api.getTrangThaiHD(this.axios)
        if (res.data.error_code == 'BSS-00000000') {
          this.cbbTrangThai.options = res.data.data.map((item) => {
            return {
              id: item.ID,
              text: item.NAME
            }
          })
          this.cbbTrangThai.value = this.cbbTrangThai.options[0].id
        }

        let ttsim = await this.axios.get('/web-tracuu/quanlydonhang/sp_tracuu_lay_tinhtrang_sim')
        if (ttsim.data.error_code == 'BSS-00000000') {
          this.cbbTTSim.options = ttsim.data.data.map((item, index) => {
            return {
              id: item.trangthai_id,
              text: item.trangthai_kh,
              color: this.colorList[index]
            }
          })
          this.cbbTTSim.value = this.cbbTTSim.options[0].id
        }
      } catch (error) {
      } finally {
        this.loading(false)
      }
    },
    async LOAD_ORDERS() {
      if (!this.cbbNguonDon.value || this.cbbNguonDon.value.length == 0 && (!this.txtMaDonHang.value || this.txtMaDonHang.value.trim() == '')) {
        this.$toast.error('Vui lòng chọn nguồn đơn!')
        return
      }
      try {
        this.loading(true)
        this.tbDSDonHang.data = []
        this.tbChiTietPhanGiao.data = []
        this.listDaQuaHan = [];
        this.listSapQuaHan = [];
        this.cbbTrangThai.options.forEach((item) => {
          item.number = 0
        })
        this.cbbTTSim.options.forEach((item) => {
          item.number = 0
        })
        let searchInput = {
          js_ungdung:
            this.cbbNguonDon.value && this.cbbNguonDon.value[0] != -1
              ? JSON.stringify(
                  this.cbbNguonDon.value.map((item) => {
                    return { ungdung_id: item * 1 }
                  })
                )
              : JSON.stringify(
                  this.cbbNguonDon.options.map((item) => {
                    return { ungdung_id: item.id }
                  })
                ),
          loainv: this.cbbLoaiNV.value * 1,
          khdn: this.cbbLoaiKH.value * 1 != -1 ? this.cbbLoaiKH.value * 1 : -1,
          ds_nhanvien: JSON.stringify(
            this.cbbNhanVien.value.map((item) => {
              return { nhanvien_id: item }
            })
          ),
          dichvuvt_id: this.cbbDichVuVT.value * 1 != -1 ? this.cbbDichVuVT.value * 1 : -1,
          loaitb_id: this.cbbLoaiHinh.value * 1 != -1 ? this.cbbLoaiHinh.value * 1 : -1,
          ma_nv: this.txtMaKenhBan.enabled ? this.txtMaKenhBan.value : '-1',
          so_dt: this.txtDienThoaiKenh.enabled ? this.txtDienThoaiKenh.value : '-1',
          tungay: this.dtpTuNgay.enabled ? moment(this.dtpTuNgay.value).format('DD/MM/yyyy') : '-1',
          denngay: this.dtpDenNgay.enabled ? moment(this.dtpDenNgay.value).format('DD/MM/yyyy') : '-1',
          ma_gd: '-1',
          tthd_id: this.cbbTrangThai.enabled ? this.cbbTrangThai.value : -1,
          status: this.cbbTTSim.enabled ? this.cbbTTSim.value : -1
        }
        if (this.txtMaDonHang.value && this.txtMaDonHang.value.trim() != '') {
          searchInput = {
            ma_gd: this.txtMaDonHang.value.trim(),
            js_ungdung: JSON.stringify([]),
            loainv: this.cbbLoaiNV.value * 1,
            khdn: -1,
            ds_nhanvien: JSON.stringify([]),
            dichvuvt_id:  -1,
            loaitb_id: -1,
            ma_nv: '-1',
            so_dt: '-1',
            tungay: '-1',
            denngay: '-1',
            tthd_id:-1,
            status: -1
          }
        }
        let res = await api.loadDonHang(this.axios, searchInput)

        if (res.data.error_code == 'BSS-00000000') {
          this.tbDSDonHang.data = res.data.data.map((item) => {
            let hientai = moment().format('DD/MM/YYYY HH:mm:ss');
            let chitieu;
            if(item.goikt_id == 20){
              chitieu = 1.5;
            }
            else if(item.goikt_id == 21){
              chitieu = 5.5;
            }
            else if(item.goikt_id == 22){
              chitieu = 21.5;
            }
            else {
              chitieu = 0;
            }
            if(chitieu != 0){
              let ngayyc = this.LamTronGio(item.ngay_yc,'DD/MM/YYYY HH:mm:ss');
              let khoangcach = this.KhoangCachGio(ngayyc,hientai,'DD/MM/YYYY HH:mm:ss');
              if(chitieu <= khoangcach && khoangcach <= item.chitieu_tg){
                this.listSapQuaHan.push(item);
              }
              else if(khoangcach > item.chitieu_tg){
                this.listDaQuaHan.push(item);
              }
            }
            // const status = [0,1,2,3,4,5,7,8,9][Math.floor(Math.random() * 9)] //giả lập dữ liệu
            let idex
            if (item.status) {
              idex = this.cbbTTSim.options.findIndex((x) => x.id == item.status)
              if (!this.cbbTTSim.options[idex].number) this.cbbTTSim.options[idex].number = 1
              else this.cbbTTSim.options[idex].number++
            }
            const tthd_id = item.tthd_id
            const i = this.cbbTrangThai.options.findIndex((item) => item.id == tthd_id)
            if (!this.cbbTrangThai.options[i].number) this.cbbTrangThai.options[i].number = 1
            else this.cbbTrangThai.options[i].number++
            return {
              ...item,
              ngay_yc: moment(item.ngay_yc, 'DD/MM/yyyy hh:mm:ss').format('DD/MM/yyyy'),
              statusName: idex ? this.cbbTTSim.options[idex].text : ''
            }
          })
        } else if (res.data.error_code == 'BSS-00000204') {
          this.$toast.info('Không tìm thấy dữ liệu đơn hàng!')
          this.tbDSDonHang.data = []
        } else {
          this.$root.toastError(res.data.message)
          this.tbDSDonHang.data = []
        }
      } catch (error) {
      } finally {
        this.loading(false)
        // console.log(this.listSapQuaHan);
        // console.log(this.listDaQuaHan);
        
      }
    },
    async tbDSDonHang_rowSelected(row) {
      this.tbChiTietPhanGiao.data = []
      try {
        this.loading(true)
        const dh = row.data
        this.currentDH = dh
        let res = await api.getDSChiTietPhanGiao(this.axios, {
          vphanvung_id: this.phanvung_id,
          vhdtb_id: dh.hdtb_id
        })

        if (res.data.error_code == 'BSS-00000000') {
          this.tbChiTietPhanGiao.data = res.data.data
        } else {
          this.$root.toastError(res.data.message)
          this.tbChiTietPhanGiao.data = []
        }
      } catch (error) {
      } finally {
        this.loading(false)
      }
    },
    tbDSDonHang_rowDataBound(args) {
      const status = args.data.status
      if (status || status == 0) {
        const idex = this.cbbTTSim.options.findIndex((item) => item.id == status)
        args.row.setAttribute('style', `background-color: ${this.cbbTTSim.options[idex].color} !important`)
      } else {
        args.row.setAttribute('style', `background-color: #fff !important`)
      }
    },
    async excelExport() {
      try {
        this.loading(true)
        const wookbook = xlsx.utils.book_new()
        const dt = []
        for (let i = 0; i < this.tbDSDonHang.data.length; i++) {
          const result = {
            'Mã đơn hàng': this.tbDSDonHang.data[i].ma_gd,
            'Ngày yêu cầu': this.tbDSDonHang.data[i].ngay_yc,
            'Tên khách hàng': this.tbDSDonHang.data[i].ten_kh,
            'Địa chỉ khách hàng': this.tbDSDonHang.data[i].diachi_kh,
            'Số điện thoại': this.tbDSDonHang.data[i].so_dt,
            'Loại hình': this.tbDSDonHang.data[i].loaihinh_tb,
            'Loại thuê bao': '',
            'Gói cước': this.tbDSDonHang.data[i].goicuoc,
            'Tổng giá': 0,
            'Nội dung': '',
            'Trạng thái đơn': this.tbDSDonHang.data[i].trangthai_hd,
            'Trạng thái sim': this.tbDSDonHang.data[i].statusName
          }
          if (this.tbDSDonHang.data[i].loaihinh_tb.toLowerCase().includes('di động')) {
            result['Loại thuê bao'] = this.tbDSDonHang.data[i].loaihinh_tb.toLowerCase().includes('trả sau') ? 'Trả sau' : 'Trả trước'
          }
          const res = await api.getChiTietDonHang(this.axios, {
            p_hdkh_id: this.tbDSDonHang.data[i].hdkh_id,
            p_phanvung_id: this.phanvung_id
          })

          result['Tổng giá'] = res.data.data[0].tongtien
          result['Nội dung'] = res.data.data[0].noidung
          dt.push(result)
        }
        // console.log(dt);
        const wooksheet = xlsx.utils.json_to_sheet(dt)
        xlsx.utils.book_append_sheet(wookbook, wooksheet, 'Danh sách đơn hàng')
        xlsx.writeFile(wookbook, 'OrderList.xlsx')

        const blob = new Blob([wookbook], { type: 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8' })
      } catch (error) {
        console.log(error.message)
      } finally {
        this.loading(false)
      }
    }
  },
  async mounted() {
    await this.LOAD_COMBO()
    this.phanvung_id = (await this.$root.token.getPhanVungID()) * 1
    this.ma_nv = (await this.$root.token.getNhanVienID()) * 1
    this.$refs.ThongTinDonHangModal.parent = this
    // await this.LOAD_ORDERS()
  },
  watch: {
    'txtDienThoaiKenh.value': {
      handler: function(val, oldVal) {
        //just allow numberic value
        this.txtDienThoaiKenh.value = this.txtDienThoaiKenh.value.replace(/[^0-9]/g, '')
        if (this.txtDienThoaiKenh.value.length > 50) this.txtDienThoaiKenh.value = this.txtDienThoaiKenh.value.substring(0, 50)
      },
      deep: true
    },
    'cbbDichVuVT.value': {
      handler: async function(val, oldVal) {
        // if(val == -1){
        //   this.cbbLoaiHinh.options = [{id: -1, text: '---Không chọn---'}]
        //   this.cbbLoaiHinh.value = -1
        //   this.cbbLoaiHinh.disable = true
        //   return
        // }
        // else
        //   this.cbbLoaiHinh.disable = false
        try {
          this.loading(true)
          this.cbbLoaiHinh.options = [{id: 0, text: 'Tất cả'}]
          let res = await api.getDSLoaiHinh(this.axios, this.cbbDichVuVT.value)
          if (res.data.error_code == 'BSS-00000000') {
            res.data.data.forEach((item) => {
              this.cbbLoaiHinh.options.push({
                id: item.loaitb_id,
                text: item.loaihinh_tb
              })
            })
            this.cbbLoaiHinh.value = this.cbbLoaiHinh.options[0].id
          }
          // this.cbbLoaiHinh.options = [{id: -1, text: '---Không chọn---'}].concat(this.cbbLoaiHinh.options)
        } catch (error) {
        } finally {
          this.loading(false)
        }
      },
      deep: true
    },
    'cbbDonVi.value': {
      handler: async function(val, oldVal) {
        try {
          this.loading(true)
          let v = val
          if (val.indexOf('-1') != -1) v = this.cbbDonVi.options.map((item) => item.id)
          this.cbbNhanVien.value = []
          let res = await api.getDSNhanVienTheoDonVi(this.axios, {
            p_json_donvi: JSON.stringify(
              v.map((item) => {
                return {
                  donvi_id: item
                }
              })
            ),
            loai_nv: this.cbbLoaiNV.value
          })
          if (res.data.error_code == 'BSS-00000000') {
            this.cbbNhanVien.options = res.data.data.map((item) => {
              this.cbbNhanVien.value.push(item.nhanvien_id)
              return {
                id: item.nhanvien_id,
                label: item.ten_nv
              }
            })
          }
        } catch (error) {
        } finally {
          this.loading(false)
        }
      },
      deep: true
    },
    'cbbLoaiNV.value': {
      handler: async function(val, oldVal) {
        try {
          this.loading(true)
          let v = this.cbbDonVi.value
          if (this.cbbDonVi.value.indexOf('-1') != -1) v = this.cbbDonVi.options.map((item) => item.id)
          this.cbbNhanVien.value = []
          let res = await api.getDSNhanVienTheoDonVi(this.axios, {
            p_json_donvi: JSON.stringify(
              v.map((item) => {
                return {
                  donvi_id: item
                }
              })
            ),
            loai_nv: val
          })
          if (res.data.error_code == 'BSS-00000000') {
            this.cbbNhanVien.options = res.data.data.map((item) => {
              this.cbbNhanVien.value.push(item.nhanvien_id)
              return {
                id: item.nhanvien_id,
                label: item.ten_nv
              }
            })
          }
        } catch (error) {
        } finally {
          this.loading(false)
        }
      },
      deep: true
    },
    'cbbDonVi.enabled': {
      handler: function(val, oldVal) {
        if (val && this.cbbLoaiNV.enabled) this.cbbNhanVien.disableChk = false
        else {
          this.cbbNhanVien.disableChk = true
          this.cbbNhanVien.enabled = false
        }
      },
      deep: true
    },
    'cbbLoaiNV.enabled': {
      handler: function(val, oldVal) {
        if (val && this.cbbDonVi.enabled) this.cbbNhanVien.disableChk = false
        else {
          this.cbbNhanVien.disableChk = true
          this.cbbNhanVien.enabled = false
        }
      },
      deep: true
    }
  }
}
</script>

<style lang="scss" src="./style.scss"></style>
