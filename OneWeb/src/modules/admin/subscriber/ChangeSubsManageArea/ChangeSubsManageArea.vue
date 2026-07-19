<template src="./ChangeSubsManageArea.html"></template>
<style src="./ChangeSubsManageArea.scss"></style>
<script>
import gridView from '@/components/Shared/gridview.vue'
import treegridView from '@/modules/complaint/ResovleComplaintAfterSales/components/treegridview.vue'
import layDuLieuBC from '@/modules/contract/complete_profile/CompleteProfileITVAS/popups/popupLayDuLieuBC/LayDuLieuBC.vue'
import ExportDataModal from '@/modules/contract/profile/ExportData/ExportDataModal.vue'
import breadcrumb from '@/components/breadcrumb'
import 'flatpickr/dist/plugins/monthSelect/style.css'
import bssFlatPicker from '@/components/BssDatePicker'
import moment from 'moment'
import XLSX from 'xlsx'
import api from './Api'
import { async } from 'pdfmake/build/pdfmake'
import apiHelper from '../../../Payment/eInvoice/api.helper'

export default {
  components: {
    breadcrumb,
    gridView,
    treegridView,
    bssFlatPicker,
    moment,
    XLSX,
    layDuLieuBC,
    ExportDataModal
  },
  name: 'ChangeSubsManageArea',
  data() {
    return {
      header: {
        title: 'Tra cứu',
        list: [
          { name: ' Thay đổi khu vực quản lý thuê bao', link: { name: 'Ui.cards' } },
          {
            name: 'Thay đổi khu vực quản lý thuê bao',
            link: { name: 'Ui.buttons' }
          }
        ]
      },
      Loading: false,
      LOAI_KV: {
        KHUVUC_CHINHSACH: 1, //
        KHUVUC_KHAC: 2,
        KHUVUC_THULAO_DAILY: 3,
        KHUVUC_DIABAN: 4,
        KHUVUC_DIABAN_VT: 5,
        KHUVUC_DIABAN_TT: 6,
        KHUVUC_DIABAN_TC: 7
      },
      /*
      KhuVuc1: 0,
      KhuVuc2: 0,
      DmKhuVuc: [],
      */
      DmKhuVuc1: {
        valueField: 'KHUVUC_ID',
        textField: 'TEN_KV',
        panelDataHeight: 'auto',
        KhuVucId: null,
        data: [],
        headers: [
          {
            fieldName: 'MA_KV',
            headerText: 'Mã khu vực',
            allowFiltering: true,
            allowSorting: false
          },
          {
            fieldName: 'TEN_KV',
            headerText: 'Tên khu vực',
            allowFiltering: true,
            allowSorting: false
          }
        ]
      },
      DmKhuVuc2: {
        valueField: 'KHUVUC_ID',
        textField: 'TEN_KV',
        panelDataHeight: 'auto',
        data: [],
        KhuVucId: null,
        headers: [
          {
            fieldName: 'MA_KV',
            headerText: 'Mã khu vực',
            allowFiltering: true,
            allowSorting: false
          },
          {
            fieldName: 'TEN_KV',
            headerText: 'Tên khu vực',
            allowFiltering: true,
            allowSorting: false
          }
        ]
      },
      selIndex: [],
      movedList2: [],
      DsThueBao1: {
        headers: [
          { name: 'MA_TB', text: 'Số máy/Acc', value: '' },
          { name: 'MA_LT', text: 'Số ảo', value: '' },
          { name: 'MATB_DD', text: 'Mã đại diện', value: '' },
          { name: 'ACCOUNT', text: 'Account', value: '' },
          { name: 'DIACHI_TB', text: 'Địa chỉ', value: '' },
          { name: 'TEN_TB', text: 'Tên thuê bao', value: '' },
          { name: 'TEN_DVVT', text: 'Loại hình/DV', value: '' },
          { name: 'TRANGTHAI_TB', text: 'Trạng thái', value: '' },
          { name: 'CAP_GOC', text: 'Cáp gốc', value: '' },
          { name: 'DOICAP_GOC', text: 'Đổi cáp gốc', value: '' },
          { name: 'TEN_DT', text: 'Đối tượng', value: '' },
          { name: 'TEN_LKHL', text: 'Loại KHL', value: '' },
          { name: 'NGAY_SD', text: 'Ngày SD', value: '' },
          { name: 'TO_KT', text: 'Tổ KT', value: '' },
          { name: 'MA_TT', text: 'Mã TT', value: '' },
          { name: 'MA_KV', text: 'Mã KV', value: '' },
          { name: 'TEN_KV', text: 'Tên KV', value: '' },
          { name: 'DIEM', text: 'Điểm', value: '' },
          { name: 'KIEUKV_ID', text: 'Kiểu KV', value: '' }
        ],
        headers2: [
          { fieldName: 'MA_TB', headerText: 'Số máy/Acc', allowFiltering: true, allowSorting: false, width: 90 },
          { fieldName: 'MA_LT', headerText: 'Số ảo', allowFiltering: true, allowSorting: false, width: 90 },
          { fieldName: 'MATB_DD', headerText: 'Mã đại diện', allowFiltering: true, allowSorting: false, width: 90 },
          { fieldName: 'ACCOUNT', headerText: 'Account', allowFiltering: true, allowSorting: false, width: 90 },
          { fieldName: 'DIACHI_TB', headerText: 'Địa chỉ', allowFiltering: true, allowSorting: false, width: 90 },
          { fieldName: 'TEN_TB', headerText: 'Tên thuê bao', allowFiltering: true, allowSorting: false, width: 90 },
          { fieldName: 'TEN_DVVT', headerText: 'Loại hình/DV', allowFiltering: true, allowSorting: false, width: 90 },
          { fieldName: 'TRANGTHAI_TB', headerText: 'Trạng thái', allowFiltering: true, allowSorting: false, width: 90 },
          { fieldName: 'CAP_GOC', headerText: 'Cáp gốc', allowFiltering: true, allowSorting: false, width: 90 },
          { fieldName: 'DOICAP_GOC', headerText: 'Đổi cáp gốc', allowFiltering: true, allowSorting: false, width: 90 },
          { fieldName: 'TEN_DT', headerText: 'Đối tượng', allowFiltering: true, allowSorting: false, width: 90 },
          { fieldName: 'TEN_LKHL', headerText: 'Loại KHL', allowFiltering: true, allowSorting: false, width: 90 },
          { fieldName: 'NGAY_SD', headerText: 'Ngày SD', allowFiltering: true, allowSorting: false, width: 90 },
          { fieldName: 'TO_KT', headerText: 'Tổ KT', allowFiltering: true, allowSorting: false, width: 90 },
          { fieldName: 'MA_TT', headerText: 'Mã TT', value: '' },
          { fieldName: 'MA_KV', headerText: 'Mã KV', value: '' },
          { fieldName: 'TEN_KV', headerText: 'Tên KV', value: '' },
          { fieldName: 'DIEM', headerText: 'Điểm', value: '' },
          { fieldName: 'KIEUKV_ID', headerText: 'Kiểu KV', allowFiltering: true, allowSorting: false, width: 90 }
        ],
        data: [],
        selectedItems: []
      },
      DsThueBao2: {
        data: [],
        selectedItems: []
      },
      txtSoMayTimKiem: {
        text: "Số máy/Acc",
        value: ""
      },
      ThongTinLichSuBienDong: {
        columns: [
            {fieldName: 'ma_tb', headerText : 'Mã TB', allowFiltering: true, allowSorting: false, width: 90},
            {fieldName: 'ten_kv_cu', headerText : 'Khu vực cũ', allowFiltering: true, allowSorting: false, width: 90},
            {fieldName: 'ten_kv_moi', headerText : 'Khu vực mới', allowFiltering: true, allowSorting: false, width: 90},
            {fieldName: 'kieu_khuvuc', headerText : 'Kiểu khu vực', allowFiltering: true, allowSorting: false, width: 90},
            {fieldName: 'ten_dv_nv', headerText : 'Đơn vị (Nhân viên)', allowFiltering: true, allowSorting: false, width: 90},
            {fieldName: 'nguoi_cn', headerText : 'Người cập nhật', allowFiltering: true, allowSorting: false, width: 90},
            {fieldName: 'ngay_cn', headerText : 'Ngày cập nhật', allowFiltering: true, allowSorting: false, width: 90},
            {fieldName: 'may_cn', headerText : 'Máy cập nhật', allowFiltering: true, allowSorting: false, width: 90},
            {fieldName: 'ten_nv', headerText : 'Nhân viên cập nhật', allowFiltering: true, allowSorting: false, width: 90},
        ],
        data: [],
        selectedItems: []
      },
      btnSearchSelected: false,
      searchHisToryDetail: false,
    }
  },
  watch: {
    'DmKhuVuc1.KhuVucId': async function(val) {
      try {
        if (val == '') return
        this.Loading = true
        this.selIndex = []
        await this.getDSThueBaoTheoKhuVuc(val)
        this.Loading = false
      } catch (ex) {
        this.Loading = false
      }
    },
    'DmKhuVuc2.KhuVucId': async function(val) {
      try {
        if (val == '') return
        this.Loading = true
        this.selIndex = []
        await this.getDSThueBaoTheoKhuVuc2(val)
        this.Loading = false
      } catch (ex) {
        this.Loading = false
      }
    },
    Loading: function(val) {
      this.$root.showLoading(val)
    }
  },
  created() {
    this.DsThueBao1.selectedItems = []
    this.DsThueBao2.selectedItems = []
    this.Loading = true
    Promise.all([this.getDmKhuVuc()])
      .then((responses) => {
        // this.Loading = true
      })
      .finally((x) => {
        this.Loading = false
      })
    // let nhanvienId = this.$root.token.getNhanVienID()
    // alert(nhanvienId)
    // this.DmKhuVuc = this.getDmKhuVuc()
  },
  mounted() {
    this.DmKhuVuc1.KhuVucId = null
    this.DmKhuVuc2.KhuVucId = null
  },
  methods: {
    getDmKhuVuc: async function() {
      var input = { p_loaikv_id: this.LOAI_KV.KHUVUC_DIABAN }
      let data = this.GetData(await api.getDmKhuVuc(this.axios, input))
      this.DmKhuVuc1.data = data.slice(0)
      this.DmKhuVuc2.data = data.slice(0)
    },
    getDSThueBaoTheoKhuVuc: async function(kvid) {
      var input = { p_khuvuc_id: kvid }
      this.Loading = true
      try {
        this.DsThueBao1.data = this.GetData(await api.getDSThueBaoTheoKhuVuc(this.axios, input))
        this.Loading = false
      } catch (ex) {
        this.Loading = false
        this.DsThueBao1.data = []
      } finally {
        this.Loading = false
      }
    },
    getDSThueBaoTheoKhuVuc2: async function(kvid) {
      var input = { p_khuvuc_id: kvid }
      this.Loading = true
      try {
        this.DsThueBao2.data = this.GetData(await api.getDSThueBaoTheoKhuVuc(this.axios, input))
        this.Loading = false
      } catch (ex) {
        this.Loading = false
        this.DsThueBao2.data = []
      } finally {
        this.Loading = false
      }
    },
    chuyenThueBaoTheoKhuVuc: async function() {
      this.$bvModal
        .msgBoxConfirm('Bạn có thật sự muốn cập nhật thông tin?', {
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
          var input = {
            danhsach: [],
            p_khuvuc_id_cu: this.DmKhuVuc1.KhuVucId,
            p_khuvuc_id_moi: this.DmKhuVuc2.KhuVucId,
            p_loaikv_id: this.LOAI_KV.KHUVUC_DIABAN
          }
          let selectedItems = this.DsThueBao2.data.filter((x) => x.NEW == 1)
          if (selectedItems.length <= 0) {
            this.ShowAlert('Chưa có thuê bao cần chuyển đến. Hãy kiểm tra lại!')
            return
          }
          if (this.DmKhuVuc2.KhuVucId == '' || this.DmKhuVuc2.KhuVucId == null) {
            this.ShowAlert('Chưa chọn khu vực ')
            return
          }
          if (!this.KiemTra()) return
          selectedItems.forEach(function(item) {
            input.danhsach.push({ dichvutv_id: item.DICHVUVT_ID, kieukv_id: item.KIEUKV_ID, thuebao_id: item.THUEBAO_ID })
          })
          try {
            this.Loading = true
            let response = await api.chuyenThueBaoTheoKhuVuc(this.axios, input)
            if (response.data.error == 200 && response.data.error_code == 'BSS-00000000' && response.data.data == '1') {
              this.Loading = false
              this.ShowSuccess(`Thay đổi khu vực quản lý thuê bao thành công!`)
            } else {
              this.Loading = false
              this.ShowError(`Có lỗi: ${response.data.message}`)
            }
          } catch (err) {
            this.ShowError(`${err}`)
            console.log('🚀 ~ file: ChangeSubsManageArea.vue:274 ~ .then ~ err:', err)
            this.Loading = false
          }
        })
    },
    getThongTinLichSuBienDong: async function(){
      var input = {
        "p_phanvung_id": this.$root.token.getPhanVungID(),
        "p_ma_tb": this.txtSoMayTimKiem.value
      }
      this.Loading = true
      try {
        this.ThongTinLichSuBienDong.data = this.GetData(await api.getLsTraoDoiBienDong(this.axios, input))
        if(this.ThongTinLichSuBienDong.data.length > 0){
          this.ShowSuccess("Lấy thông tin thành công")
        }
        else{
          this.ShowError("Không có dữ liệu")
        }
        this.Loading = false
      } catch (ex) {
        this.Loading = false
        this.ThongTinLichSuBienDong.data = []
      } finally {
        this.Loading = false
      }
    },
    gridThueBao2_DataBound(args) {
      if (this.selIndex.length > 0) {
        // this.$refs.refDsThueBao2.grid.selectRows(this.selIndex)
        // this.$nextTick(() => {
        //   this.selIndex = []
        // })
      }
    },
    gridThueBao2_RowDeselected(args) {
      let items = this.$refs.refDsThueBao2.getSelectedRecords()
      console.log('gridThueBao2_RowDeselected', args, items)
    },
    gridThueBao2_RowDataBound(args) {
      try {
        if (args.data['CHON'] == 1) {
          this.selIndex.push(parseInt(args.row.getAttribute('aria-rowindex')))
        }
        if (args.data['NEW'] == 1) {
          args.row.classList.add('new-red-item')
        }
      } catch (err) {
        console.log('gridThueBao2_RowDataBound', err)
      }
    },
    select_selectedItemsChangedChuagan: function(selectedItems) {
      this.DsThueBao1.selectedItems = selectedItems
    },
    select_selectedItemsChangedDagan: function(selectedItems) {
      this.DsThueBao2.selectedItems = selectedItems
    },
    RightOne: function() {
      if (!this.KiemTra()) return
      var self = this
      if (this.DsThueBao1.data) {
        // let selectedItems = this.DsThueBao1.selectedItems
        let selectedItems = this.$refs.refDsThueBao1.getSelectedRecords()
        if (selectedItems.length === 0) {
          self.ShowAlert(`Chưa chọn thuê bao !`)
          return
        }
        let movedList = []
        for (const item of selectedItems) {
          if (item.DICHVUVT_ID != '9' && self.DsThueBao2.data.filter((x) => x.THUEBAO_ID === item.THUEBAO_ID).length > 0) {
            self.ShowError(`Mã TB: ${item.MA_TB}  đã có trên lưới 'Danh sách thuê bao được chuyển'. Bạn không được chuyển tiếp!`)
            return
          } else {
            item.NEW = 1
            item.CHON = 1
            self.DsThueBao2.data.unshift(item)
            movedList.push(item)
          }
        }

        // selectedItems.forEach(function (item) {
        //   if (item.DICHVUVT_ID != '9' && self.DsThueBao2.data.filter((x) => x.THUEBAO_ID === item.THUEBAO_ID).length > 0) {
        //     self.ShowError(`Mã TB: ${item.MA_TB}  đã có trên lưới 'Danh sách thuê bao được chuyển'. Bạn không được chuyển tiếp!`)
        //   } else {
        //     item.NEW = 1
        //     item.CHON = 1
        //     self.DsThueBao2.data.unshift(item)
        //     movedList.push(item)
        //   }
        // })
        this.DsThueBao1.data = this.DsThueBao1.data.filter((x) => movedList.filter((y) => y.THUEBAO_ID == x.THUEBAO_ID).length == 0)
        this.DsThueBao1.selectedItems = []
      }
    },
    RightAll: function() {
      if (!this.KiemTra()) return
      var self = this
      if (this.DsThueBao1.data) {
        let selectedItems = this.DsThueBao1.data
        //let selectedItems = this.$refs.refDsThueBao1.getSelectedRecords()
        if (selectedItems.length === 0) {
          self.ShowAlert(`Chưa chọn thuê bao !`)
          return
        }
        for (const item of selectedItems) {
          if (item.DICHVUVT_ID != '9' && self.DsThueBao2.data.filter((x) => x.THUEBAO_ID === item.THUEBAO_ID).length > 0) {
            self.ShowError(`Mã TB: ${item.MA_TB}  đã có trên lưới 'Danh sách thuê bao được chuyển'. Bạn không được chuyển tiếp!`)
            return
          } else {
            item.NEW = 1
            item.CHON = 1
            self.DsThueBao2.data.unshift(item)
          }
        }
        // selectedItems.forEach(function (item) {
        //   if (item.DICHVUVT_ID != '9' && self.DsThueBao2.data.filter((x) => x.THUEBAO_ID === item.THUEBAO_ID).length == 0) {
        //     self.ShowError(`Mã TB: ${item.MA_TB}  đã có trên lưới 'Danh sách thuê bao được chuyển'. Bạn không được chuyển tiếp!`)
        //   } else {
        //     item.NEW = 1
        //     item.CHON = 1
        //     self.DsThueBao2.data.unshift(item)
        //   }
        // })
        this.DsThueBao1.data = []
      }
    },
    LeftOne: function() {
      if (!this.KiemTra()) return
      if (this.DsThueBao2.data) {
        var self = this
        let selectedItems = this.$refs.refDsThueBao2.getSelectedRecords() // this.DsThueBao2.selectedItems
        if (selectedItems.length === 0) {
          self.ShowAlert(`Chưa chọn thuê bao hủy thay đổi !`)
          return
        }
        let movedList = []
        selectedItems.forEach(function(item) {
          if (self.DsThueBao1.data.filter((x) => x.THUEBAO_ID === item.THUEBAO_ID).length == 0) {
            // self.DsThueBao1.data.push(item)
            self.DsThueBao1.data.unshift(item)
            movedList.push(item)
          }
        })
        this.DsThueBao2.data = this.DsThueBao2.data.filter((x) => movedList.filter((y) => y.THUEBAO_ID == x.THUEBAO_ID).length == 0)
        this.DsThueBao2.selectedItems = []
      }
    },
    LeftAll: async function() {
      if (!this.KiemTra()) return
      var self = this
      let selectedItems = self.DsThueBao2.data
      if (selectedItems.length === 0) {
        self.ShowAlert(`Chưa chọn thuê bao hủy thay đổi !`)
        return
      }
      selectedItems.forEach(function(item) {
        if (self.DsThueBao1.data.filter((x) => x.THUEBAO_ID === item.THUEBAO_ID).length == 0) {
          self.DsThueBao1.data.unshift(item)
          // self.DsThueBao1.data.push(item)
        }
      })
      this.DsThueBao2.data = []
      this.Loading = true
      try {
        await this.getDSThueBaoTheoKhuVuc(this.DmKhuVuc1.KhuVucId)
        this.Loading = false
      } catch (ex) {
        this.Loading = false
      }
    },
    KiemTra() {
      if (this.DmKhuVuc1.KhuVucId == '' || this.DmKhuVuc1.KhuVucId == null) {
        this.ShowError(`Chưa chọn khu vực cũ!`)
        return false
      }
      if (this.DmKhuVuc2.KhuVucId == '' || this.DmKhuVuc2.KhuVucId == null) {
        this.ShowError(`Chưa chọn khu vực mới!`)
        return false
      }
      if (this.DmKhuVuc1.KhuVucId == this.DmKhuVuc2.KhuVucId) {
        this.ShowError(`Khu vực cũ và mới trùng nhau!`)
        return false
      }
      return true
    },
    Huy: async function() {
      this.$bvModal
        .msgBoxConfirm(`Bạn muốn hủy thay đổi khu vực các thuê bao đã chọn`, {
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
          this.DsThueBao1.data = []
          this.DsThueBao1.selectedItems = []
          this.DsThueBao2.data = []
          this.DsThueBao2.selectedItems = []
          this.DmKhuVuc1.KhuVucId = null
          this.DmKhuVuc2.KhuVucId = null
          this.selIndex = []
        })
    },
    XuatFile: function() {
      if (this.DsThueBao1.data != null && this.DsThueBao1.data.length > 0) {
        // this.$refs.dialogLayDuLieuBC.openDialog(this.DsThueBao1.data)
        this.$refs.exportDataModal.showModal()
        /*
        let data = XLSX.utils.json_to_sheet(this.DsThueBao1.data)
        let wb = XLSX.utils.book_new() // make Workbook of Excel
        XLSX.utils.book_append_sheet(wb, data, 'ds-thuebao') // sheetAName is name of Worksheet
        // export Excel file
        XLSX.writeFile(wb, 'ds-thuebao.xlsx')
        */
      } else this.$toast.error('Không có dữ liệu!')
    },

    TraCuu: async function(){
      this.btnSearchSelected = true;
      this.searchHisToryDetail = true;
    },
    TraCuuThoat: async function(){
      this.btnSearchSelected = false;
      this.searchHisToryDetail = false;
    },
    LayThongTin: async function(){
      if(this.txtSoMayTimKiem.value == ""){
        this.ShowError(`Nhập Số máy/Acc cần tra cứu`);
      }
      else{
        await this.getThongTinLichSuBienDong();
        // console.log(this.$root.token.getPhanVungID());
      }
    },
    async HienThiDanhSachD() {
      let kvid = this.DmKhuVuc2.KhuVucId
      await this.getDSThueBaoTheoKhuVuc2(kvid)
    },

    GetData: function(response) {
      if (response.data.error === 200 || response.data.error === '200') {
        return response.data.data
      } else {
        return []
      }
    },
    ShowAlert: function(message) {
      // alert(message)
      this.$toast.error(message)
    },
    ShowError: function(message) {
      // alert(message)
      this.$toast.error(message)
    },
    ShowSuccess: function(message) {
      // alert(message)
      this.$toast.success(message)
    },
    
  }
}
</script>

<style scoped>
.modal-search {
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  display: none;
  z-index: 999;
}

.modal-search.open{
  display: flex;
}
.modal__overlay-search{
  position: absolute;
  background-color: rgba(0,0,0,0.2);
  width: 100%;
  height: 100%;
  animation: fadeIn linear 0.4s;
}

@keyframes fadeIn {
  from{
      opacity: 0;
  }
  to{
      opacity: 1;
  }
}

.search-history{
    margin: auto;
    background-color: #fff;
    position: relative;
    z-index: 1;
    border-radius: 5px;
    width: calc(100% - 5%);
    height: 600px;
    padding: 20px;
    display: none;
    animation: fadeIn linear 0.1s;
}

.search-history.open{
  display: block;
}

.search-history__heading{
  margin-bottom: 20px;
}

.search-history__heading-name{
  font-size: 20px;
  text-transform: uppercase;
}

/* .search-history__body{

} */

.search-history__body-button{
  border-top: 1px solid #035fcc;
  padding-top: 8px;
}

.search-history__body-button .btn{
  background-color: #035fcc;
  color: #fff;
}
.search-history-btn{
  position: relative;
}
.search-history-btn:nth-child(2){
  margin-left: 10px;
}
.search-history-btn:not(:last-child)::after{
  content: "";
    position: absolute;
    border-right: 1px solid #035fcc;
    height: 20px;
    top: 6px;
    right: -7px;
}

.search-history__body-button .btn:hover{
  background-color: #e5f1ff;
  color: #035fcc;
}

.search-history__body-list-input{
  padding: 8px 0;
  border-top: 1px solid #035fcc;
  margin: 10px 0;
}

.search-history__body-list-input-label{
  font-weight: 400;
  font-size: 17px;

}

.search-history__body-list-input-box{
  border-color: #E0E0E0;
  border-top: 1px solid #E0E0E0;
  border-left: 1px solid #E0E0E0;
  border-radius: 4px;
  color: #444444;
  padding: 2px 6px;
  font-size: 16px;
  width: 400px;
}

.search-history__body-list-input-box:focus{
  outline:none; 
  border:1px solid #4D90FE;
  -webkit-box-shadow: 0px 0px 5px  #4D90FE;
  box-shadow: 0px 0px 5px  #4D90FE;
}

/* .search-history__body-list{

} */

</style>