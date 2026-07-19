<template src="./index.html"></template>
<style src="./style.scss" scoped>

</style>
<style>
.overlay-common .loading {
  display: flex;
  justify-content: center;
  flex-direction: column;
}

.overlay-common.show {
  display: flex;
  justify-content: center;
}

.overlay-common {
  background-color: rgba(0, 0, 0, 0.7);
}

.overlay-common .loading-panel {
  display: flex;
  text-align: center;
  background-color: #fff;
  padding: 10px 15px;
  align-items: center;
  color: #035fcc;
  border: 1px solid #035fcc;
  border-radius: 7px;
}

.overlay-common .loading-panel .loading-content {
  flex-grow: 1;
  padding-left: 10px;
}
</style>
<script>

import Vue from 'vue'
import moment from 'moment'
// import MultiSelectDemo from "./components/multi-select";
import ModalExport from './components/modal-export'
import { Query } from '@syncfusion/ej2-data'

import { MultiSelectPlugin, CheckBoxSelection } from '@syncfusion/ej2-vue-dropdowns'
import { CheckBoxPlugin } from '@syncfusion/ej2-vue-buttons'

Vue.use(MultiSelectPlugin)
Vue.use(CheckBoxPlugin)

export default {
  components: { ModalExport },
  data: function () {
    return {
      tag: '',
      header: {
        title: 'XÁC NHẬN NGUYÊN NHÂN NỢ CƯỚC',
        list: [
          { name: 'Quản lý đại lý', link: { name: 'Ui.cards' } },
          {
            name: 'Xác nhận nguyên nhân nợ cước',
            link: { name: 'Ui.buttons' }
          }
        ]
      },
      configs: {
        nvtc: {
          fields: { text: 'TEN_HIENTHI', value: 'NHANVIEN_ID' },
          mode: 'CheckBox',
          showSelectAll: true,
          showDropDownIcon: true,
          filterBarPlaceholder: 'Tìm kiếm nhanh'
        }
      },
      columns: [
        {
          fieldName: 'MA_TT',
          headerText: 'Mã thanh toán',
          allowFiltering: true,
          allowSorting: false
        },
        {
          fieldName: 'MATB_DD',
          headerText: 'Mã đại diện',
          allowFiltering: true,
          allowSorting: false
        },
        {
          fieldName: 'TEN_TT',
          headerText: 'Tên thanh toán',
          allowFiltering: true,
          allowSorting: false
        },
        {
          fieldName: 'SO_SERI',
          headerText: 'Số seri',
          allowFiltering: true,
          allowSorting: false
        },
        {
          fieldName: 'TIEN',
          headerText: 'Tiền nợ',
          allowFiltering: true,
          allowSorting: false
        },
        {
          fieldName: 'TIEN_HD',
          headerText: 'Tiền hóa đơn',
          allowFiltering: true,
          allowSorting: false
        },
        {
          fieldName: 'SO_THUNG',
          headerText: 'Số thùng',
          allowFiltering: true,
          allowSorting: false
        },
        {
          fieldName: 'HINHTHUC_TT',
          headerText: 'HTTT',
          allowFiltering: true,
          allowSorting: false
        },
        {
          fieldName: 'SERI',
          headerText: 'Seri',
          allowFiltering: true,
          allowSorting: false
        },
        {
          fieldName: 'THUTU_IN',
          headerText: 'Thứ tự in',
          allowFiltering: true,
          allowSorting: false
        },
        {
          fieldName: 'NGAYHEN',
          headerText: 'Ngày hẹn',
          allowFiltering: true,
          allowSorting: false
        },
        {
          fieldName: 'NGAYNHAN_HD',
          headerText: 'Ngày nhận',
          allowFiltering: true,
          allowSorting: false
        },
        {
          fieldName: 'LYDOTRA_ID',
          headerText: 'Lý do',
          allowFiltering: true,
          allowSorting: false,
          allowHtml: true
        },
        {
          fieldName: 'GHICHU',
          headerText: 'Ghi chú',
          allowFiltering: true,
          allowSorting: false,
          allowHtml: true
        },
        {
          fieldName: 'DIACHI_TT',
          headerText: 'Địa chỉ thanh toán',
          allowFiltering: true,
          allowSorting: false
        },
        {
          fieldName: 'MA_TUYEN',
          headerText: 'Tuyến thu',
          allowFiltering: true,
          allowSorting: false
        },
        {
          fieldName: 'MAIN_GHEP',
          headerText: 'Mã in ghép',
          allowFiltering: true,
          allowSorting: false
        },
        {
          fieldName: 'THUNO',
          headerText: 'Thu nợ',
          allowFiltering: true,
          allowSorting: false
        },
        {
          fieldName: 'MANV_TC',
          headerText: 'Mã NV/HTTT',
          allowFiltering: true,
          allowSorting: false
        },
        {
          fieldName: 'GIAOPHIEU_ID',
          headerText: 'Lượt giao',
          allowFiltering: true,
          allowSorting: false
        },
        {
          fieldName: 'TEN_LKHL',
          headerText: 'Loại KHL',
          allowFiltering: true,
          allowSorting: false
        },
        {
          fieldName: 'TENHT_GIAO',
          headerText: 'HT giao',
          allowFiltering: true,
          allowSorting: false
        },
        {
          fieldName: 'TEN_PLKH',
          headerText: 'Phân loại KH',
          allowFiltering: true,
          allowSorting: false
        }
      ],
      khongXn: true,
      dsNVTCTen: '',
      dsNVTCId: [],
      dsNVTC: [],
      dsLuotGiao: [],
      idLuotGiao: '',
      dsLoaiPhieu: [],
      idLoaiPhieu: '',
      hideNVTC: true,
      showTable: true,
      xacnhan: true,
      ghiChu: '',
      txtSearch: '',
      dsPhieu: [],
      dsPhieuFixed: [],
      dsLyDoTra: [],
      dsHoaDonIdChose: [],
      keyDSLuotGiao: 0
    }
  },
  validations: {},
  watch: {},
  created: async function () {
    this.tag = this.$route.query.tag
    this.dsLoaiPhieuDropDown()
    this.getDsNVTC()
    this.getTSMD()
    this.dsLyDoDropDown()
  },
  async mounted () { },
  methods: {
    onChangeNVTC () {
      this.hideNVTC = !this.hideNVTC
    },
    onOk (value) {
      // this.hideNVTC = true;
      // console.log(value);
      // const arrName = [];
      // this.dsNVTC.forEach(element => {
      //   if (value.includes(element.NHANVIEN_ID)) {
      //     arrName.push(element.TEN_HIENTHI);
      //   }
      // });
      // this.dsNVTCTen = arrName.join();
      // this.dsNVTCId = value.length > 0 ? value.join() : "";
      this.dsLuotGiaoDropDown()
    },
    onCancel () {
      this.hideNVTC = true
    },
    onSelectRowCheckbox (value) {
      if (value.length > 0) {
        this.idLuotGiao = value.join()
      } else {
        this.idLuotGiao = ''
      }
      // this.$refs.table.rowClick(0, null);
    },
    onChangeLuotGiao () {
      this.showTable = !this.showTable
    },
    onChangeLoaiPhieu () {
      this.onClickDs()
    },
    onClickDs () {
      if (this.idLuotGiao) {
        if (this.tag == 1) {
          this.getDsHoaDon02()
        } else {
          this.getDsHoaDon01()
        }
      }
    },
    doSearch () {
      let cloneData = JSON.parse(JSON.stringify(this.dsPhieuFixed))
      if (this.txtSearch) {
        this.dsPhieu = cloneData.filter(item =>
          item.MA_TT.includes(this.txtSearch)
        )
      } else {
        this.dsPhieu = this.dsPhieuFixed
      }
    },
    checkRowItem (value) {
      this.dsHoaDonIdChose = value
    },
    selectRowItem (value) {
      if (value) {
        this.khongXn = false
      }
    },
    onShowModalExport () {
      this.$bvModal.show('modal-export')
    },
    onClickCapNhat () {
      if (this.dsHoaDonIdChose.length == 0) return false
      const dsMap = this.dsPhieu.filter(item =>
        this.dsHoaDonIdChose.includes(item.HOADON_ID)
      )
      if (this.checkData(dsMap)) {
        const dsXn = dsMap.map(item => ({
          hoadon_id: item.HOADON_ID,
          lydotra_id: document.getElementById(`combo${item.HOADON_ID}`).value,
          ghichu: this.ghiChu
        }))
        const data = {
          pXacNhan: this.xacnhan ? 1 : 0,
          pKyCuoc: `${moment(new Date()).subtract(1, 'months').endOf('month').format('YYYYMM')}01`,
          pNguoiCN: '-',
          pMayCN: '-',
          pIPCN: '-',
          pDSXacNhan: dsXn
        }
        this.update(data)
      }
    },
    checkData (dsMap) {
      let rst = true
      for (let index = 0; index < dsMap.length; index++) {
        const item = dsMap[index]
        const liDoTb = document.getElementById(`combo${item.HOADON_ID}`).value
        if (!liDoTb) {
          this.$toast.error('Bạn chưa nhập lý do cho hóa đơn đã chọn!')
          rst = false
          break;
        }
      }
      if (rst) {
        for (let index = 0; index < dsMap.length; index++) {
          const item = dsMap[index]
          if (!this.checkNhatKy(item.THANHTOAN_ID)) {
            this.$toast.error(`Bạn chưa nhập nhật ký cho Mã TT: ${item.MA_TT}`)
            rst = false
            break;
          }
        }
      }
      return rst
    },
    getDsNVTC: async function () {
      try {
        const { data } = await this.$root.context.get(
          '/web-thuno/api/thu-no/sp-lay-danh-sach-nhan-vien-tinh-cuoc'
        )
        if (data !== undefined && data.data !== undefined && data.data.length > 0) this.dsNVTC = data.data
        else this.dsNVTC = []
      } catch (error) { }
    },
    dsLuotGiaoDropDown: async function () {
      const body = {
        pChuKyNo: `${moment(new Date()).subtract(1, 'months').endOf('month').format('YYYYMM')}01`,
        pNVTCId: this.dsNVTCId.join(','), // "3789,12901",
        pKieu: 5, // this.$root.token.getPhanVungID() == 26 ? 5 : 1, // HP 5 else 1
        pLanTaoId: null
      }
      try {
        this.loading(true)
        var rs = await this.$root.context.post(
          '/web-thuno/api/thu-no/nhan-phieu/lay-danh-sach-luot-giao',
          body
        )
        this.dsLuotGiao = rs.data
        this.keyDSLuotGiao++
      } catch (error) {
      } finally {
        this.loading(false)
      }
    },
    dsLoaiPhieuDropDown: async function () {
      try {
        var rs = await this.$root.context.get(
          '/web-thuno/api/thu-no/nguyen-nhan-no/lay-danh-sach-loai-phieu'
        )
        this.dsLoaiPhieu = rs.data
        if (rs.data) {
          this.idLoaiPhieu = rs.data[0].LOAIPHIEU_ID
        }
      } catch (error) { }
    },
    getTSMD: async function () {
      try {
        var rs = await this.$root.context.get(
          '/web-thuno/api/thu-no/thong-tin-khach-hang/lay-danh-sach-tham-so-mac-dinh',
          {
            pPhanVungid: this.$root.token.getPhanVungID(),
            pMaThamSo: 'KHONG_XN_LYDO_NOCUOC_NVTC'
          }
        )
        if (rs.data && rs.data.length > 0 && rs.data[0].TEN_TS == '1') {
          this.khongXn = true
        }
      } catch (error) { }
    },
    dsLyDoDropDown: async function () {
      try {
        var rs = await this.$root.context.get(
          '/web-thuno/api/thu-no/nhan-phieu/lay-danh-sach-ly-do-tra'
        )
        this.dsLyDoTra = rs.data
      } catch (error) { }
    },
    checkNhatKy: async function (thanhToanId) {
      try {
        var rs = await this.$root.context.get(
          '/web-thuno/api/thu-no/nguyen-nhan-no/kiem-tra-nhat-ky-thu-no',
          { pThanhToanId: thanhToanId }
        )
        return rs.data.length > 0 && rs.data[0] == 'ok'
      } catch (error) { }
    },
    getDsHoaDon01: async function () {
      this.$root.showLoading(true)
      const body = {
        pKyHoaDon: `${moment(new Date()).subtract(1, 'months').endOf('month').format('YYYYMM')}01`,
        pLoaiPhieuId: this.idLoaiPhieu,
        pKieu: 1,
        pGiaoPhieuId: this.idLuotGiao// "669885,669937,669938,669890,669864,669426" //
      }
      console.log(body)
      try {
        var rs = await this.$root.context.post(
          '/web-thuno/api/thu-no/nguyen-nhan-no/lay-danh-sach-hoa-don-khong-thu-duoc-1',
          body
        )
        console.log(rs)
        const data = rs.data.map(item => {
          let opt = `<select id="combo${item.HOADON_ID}" class="form-control">`
          this.dsLyDoTra.forEach(el => {
            if (el.LYDOTRA_ID == item.LYDOTRA_ID) {
              opt += `<option selected="selected" value="${el.LYDOTRA_ID}">${el.NOIDUNG}</option>`
            } else {
              opt += `<option value="${el.LYDOTRA_ID}">${el.NOIDUNG}</option>`
            }
          })
          opt += `</select>`
          return {
            ...item,
            NGAYHEN: item.NGAYHEN ? moment(item.NGAYHEN).format('DD/MM/YYYY') : null,
            NGAYNHAN_HD: item.NGAYNHAN_HD ? moment(item.NGAYNHAN_HD).format('DD/MM/YYYY') : null,
            TIEN: Number(item.TIEN).toLocaleString('en-En'),
            TIEN_HD: Number(item.TIEN_HD).toLocaleString('en-En'),
            LYDOTRA_ID: opt,
            GHICHU: `<input type="text" id="ghichu${item.HOADON_ID}" class="form-control" value="${item.GHICHU != null ? item.GHICHU : ''}">`
          }
        })
        this.dsPhieuFixed = data
        this.dsPhieu = data
        this.$root.showLoading(false)
      } catch (error) {
        this.$root.showLoading(false)
      }
    },
    getDsHoaDon02: async function () {
      this.$root.showLoading(true)
      const body = {
        pKyHoaDon: `${moment(new Date()).subtract(1, 'months').endOf('month').format('YYYYMM')}01`,
        pLoaiPhieuId: this.idLoaiPhieu,
        pKieu: 1,
        pNhanVienId: this.$auth.getNhanVienID(), // 4535,
        pGiaoPhieuId: this.idLuotGiao// "669885,669937,669938,669890,669864,669426" //
      }
      // console.log(body);
      try {
        var rs = await this.$root.context.post(
          '/web-thuno/api/thu-no/nguyen-nhan-no/lay-danh-sach-hoa-don-khong-thu-duoc-2',
          body
        )
        const data = rs.data.map(item => {
          let opt = `<select id="combo${item.HOADON_ID}" class="form-control">`
          this.dsLyDoTra.forEach(el => {
            if (el.LYDOTRA_ID == item.LYDOTRA_ID) {
              opt += `<option selected="selected" value="${el.LYDOTRA_ID}">${el.NOIDUNG}</option>`
            } else {
              opt += `<option value="${el.LYDOTRA_ID}">${el.NOIDUNG}</option>`
            }
          })
          opt += `</select>`
          return {
            ...item,
            NGAYHEN: item.NGAYHEN.split(' ')[0].split('-').reverse().join('/'),
            NGAYNHAN_HD: item.NGAYNHAN_HD.split(' ')[0].split('-').reverse().join('/'),
            TIEN: Number(item.TIEN).toLocaleString('en-En'),
            TIEN_HD: Number(item.TIEN_HD).toLocaleString('en-En'),
            LYDOTRA_ID: opt,
            GHICHU: `<input type="text" id="ghichu${item.HOADON_ID}" class="form-control" value="${item.GHICHU}">`
          }
        })
        this.dsPhieuFixed = data
        this.dsPhieu = data
        this.$root.showLoading(false)
      } catch (error) {
        this.$root.showLoading(false)
      }
    },
    update: async function (body) {
      // console.log(body)
      try {
        const { data } = await this.axios.put(
          'web-thuno/api/thu-no/nguyen-nhan-no/cap-nhat-xac-nhan-nguyen-nhan-no-cuoc',
          body
        )
        if (data && data.error === '200') {
          this.$toast.success('Cập nhật thông tin thành công!')
          this.onClickDs()
          // this.$refs.table.rowClick(0, null)
        } else {
          this.$toast.error('Cập nhật thông tin thất bại!')
        }
      } catch (error) {
        console.log(error)
        // this.$toast.error('Cập nhật thông tin thất bại!')
      }
    },
    onFilteringNVTC (e) {
      var query = new Query()
      query = (e.text !== '') ? query.where('TEN_HIENTHI', 'contains', e.text, true) : query
      e.updateData(this.dsNVTC, query)
    }
  },
  provide: {
    multiselect: [CheckBoxSelection]
  }
}
</script>
