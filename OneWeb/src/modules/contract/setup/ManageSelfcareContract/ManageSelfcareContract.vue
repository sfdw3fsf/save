<template src="./ManageSelfcareContract.html"></template>
<style  src="./ManageSelfcareContract.scss"></style>
<script src="@/static/vendor/jquery/split.js"></script>
<script>
import gridView from '@/modules/prepaid/ChoosePreapaidNumber/components/gridview.vue'
// import treegridView from '@/modules/complaint/ResovleComplaintAfterSales/components/treegridview.vue'
import treegridView from '@/modules/contract/setup/ManageSelfcareContract/components/treegridview.vue'
import breadcrumb from '@/components/breadcrumb'
// import 'flatpickr/dist/plugins/monthSelect/style.css'
//import bssFlatPicker from '@/components/BssDatePicker'
// import flatPickr from 'vue-flatpickr-component'
import moment from 'moment'
import DatePicker from 'vue2-datepicker'
import 'vue2-datepicker/locale/vi.js'
import 'vue2-datepicker/index.css'
import api from './Api'
export default {
  components: {
    breadcrumb,
    gridView,
    treegridView,
    moment,
    DatePicker
  },
  name: 'ManageSelfcareContract',
  data() {
    return {
      header: {
        title: 'QUẢN LÝ HỢP ĐỒNG SELFCARE',
        list: [
          { name: 'Hợp đồng', link: { name: 'Ui.cards' } },
          {
            name: ' QUẢN LÝ HỢP ĐỒNG SELFCARE',
            link: { name: 'Ui.buttons' }
          }
        ]
      },
      Loading: false,
      Expand:true,
      Loaded: false,
      dateFormat: 'DD/MM/YYYY',
      typeFormat: 'date',
      fCheckDate: false,
      fShowGiaoPhieu: true,
      fSplit: false,
      TrangThaiHdId: 1,
      DonViNhanId: -1,
      DonViPbhId: -1,
      TuNgay: '',
      DenNgay: '',
      MaKhachHang: '',
      TenKhachHang: '',
      DonViNhan: '',
      MaGiaoDich: '',
      NgayLapHd: '',
      MaGiaoDichSearch: '',
      HdKhId: '',
      NhanVienNhanId: '',
      DmTtHopDong: [],
      DsDonViQLTT: [],
      DsDonViPBH: [],
      DsNhanVienNhan: [],
      DsNhanVienTheoDonVi: [],
      dateConfig: {
        dateFormat: 'd/m/Y',
        altFormat: 'd/m/Y'
      },
      formatDate: 'DD/MM/YYYY',
      ma_tb_col: {class: 'ma_tb_col_css'},
      DsThueBao: {
        headers: [
          { fieldName: 'ma_tb', headerText: 'Số máy/Acc',freeze:'Left',allowResizing:false,maxWidth:150, minWidth:150, width:150,customAttributes:{class: 'ma_tb_col_css'}},
          { fieldName: 'ten_tb', headerText: 'Tên TB' },
          { fieldName: 'loaihinh_tb', headerText: 'Loại hình' },
          { fieldName: 'ghichu_hd', headerText: 'Ghi chú' },
          { fieldName: 'trangthai_hd', headerText: 'Trạng thái' }
        ],
        data: []
      },
      DsHopDong: {
        headers: [
          { fieldName: 'ngay_yc', headerText: 'Ngày yêu cầu', isGroupedColumn: true,captionTemplate:'Ngày yêu cầu: ${key}' },
          { fieldName: 'ma_gd', headerText: 'Mã GD', width:150, maxWidth:150, minWidth:150 , customAttributes:{class: 'ma_gd_col_css'}},
          { fieldName: 'ngaylap_hd', headerText: 'Ngày lập' },
          { fieldName: 'ma_kh', headerText: 'Mã KH',  width:150, customAttributes:{class: 'ma_kh_col_css'}},
          { fieldName: 'ten_kh', headerText: 'Tên KH' },
          { fieldName: 'so_dt', headerText: 'Số ĐT' },
          { fieldName: 'ten_dv', headerText: 'Đơn vị' },
          { fieldName: 'diachi_kh', headerText: 'Địa chỉ KH' },
          { fieldName: 'ghichu_kh', headerText: 'Ghi chú' },
          { fieldName: 'ghichu_hd', headerText: 'Mã ND' },
          { fieldName: 'ten_nv', headerText: 'NV tiếp nhận', width:150,maxWidth:150, minWidth:150 }
        ],
        data: [],
        currentRow:{},
        selectedItems: []
      },
      DsLogXuLy: {
        headers: [
          { fieldName: 'ngay_th_1', headerText: 'Ngày thực hiện', isGroupedColumn: true ,captionTemplate:'Ngày thực hiện: ${key}' },
          { fieldName: 'gio_th', headerText: 'Giờ TH', width:90 , customAttributes:{class: 'gio_th_col_css'} },
          { fieldName: 'user_giao', headerText: 'Người TH', customAttributes:{class: 'user_giao_col_css'} },
          { fieldName: 'donvi_giao', headerText: 'Từ đơn vị' },
          { fieldName: 'donvi_nhan', headerText: 'Đơn vị nhận' },
          { fieldName: 'nhanvien_giao', headerText: 'Từ nhân viên' },
          { fieldName: 'nhanvien_nhan', headerText: 'Nhân viên nhận' },
        ],
        data: [],
        selectedItems: []
      }
    }
  },
  created() {
    this.fShowGiaoPhieu = true
  },
  mounted() {
    this.Loading = true
    Promise.all([this.GetDmttHopDong(), this.GetDsDonViQLTT(), this.GetDsDonViPBH()])
      .then((responses) => {
        this.Loading = false
      })
      .catch((err) => {
        console.log(err)
        this.Loading = false
      })
      .finally((result) => {
        this.Loading = false
    })
    setTimeout(function () {
      $(function () {
        Split(['#boxLeft', '#boxRight'], {
          sizes: [50, 50],
          gutterSize: 16,
          onDragEnd: function (sizes) {}
        })
        Split(['#boxTop', '#boxBottom'], {
          sizes: [50, 50],
          direction: 'vertical'
        })
      })
    }, 500)
    this.DenNgay=moment().format('DD/MM/YYYY')
    this.TuNgay = moment().subtract(7, "days").format('DD/MM/YYYY');
  },
  computed: {
    showGiaoPhieu: function () {
      return this.DsThueBao.data && this.DsThueBao.data.length > 0
    }
  },
  watch: {
    DonViPbhId: function (val, newval) {
      this.GetDsNhanVienTheoDonVi()
    },
    TrangThaiHdId: async function (val, oldval) {
      if (val == 4) {
        this.fCheckDate = true
        this.fShowGiaoPhieu = false
      } else {
        this.fCheckDate = false
        this.fShowGiaoPhieu = true
      }
      await this.GetDsHopDong()
    },
    fCheckDate: function (val, oldval) {
      /*
      if (!val) {
        this.TuNgay =''
        this.DenNgay = ''
      }
      */
    },
    Loading: function (val, oldval) {
      this.$root.showLoading(val)
    }
  },
  methods: {
    GetDmttHopDong: async function () {
      var input = {}
      let data = this.GetData(await api.getDmTtHDSeflCare(this.axios, input))
      this.DmTtHopDong = data.map((x) => ({ id: x.TRANGTHAI_ID, text: x.TRANGTHAI == null ? '' : x.TRANGTHAI })) // this.GetData(await api.getDmTtHDSeflCare(this.axios, input))
    },
    GetDsDonViQLTT: async function () {
      var input = {}
      let data = this.GetData(await api.getDsDonViQLTT(this.axios, input))
      this.DsDonViQLTT = data.map((x) => ({ id: x.DONVI_ID, text: x.TEN_DV == null ? '' : x.TEN_DV })) // this.GetData(await api.getDsDonViQLTT(this.axios, input))
    },
    GetDsDonViPBH: async function () {
      var input = {}
      let data = this.GetData(await api.getDsDonViPBH(this.axios, input))
      this.DsDonViPBH = data.map((x) => ({ id: x.DONVI_ID, text: x.TEN_DV == null ? '' : x.TEN_DV }))
    },
    GetDsHopDong: async function () {
      this.DsThueBao.data = []
      this.DsHopDong.data=[]
      this.DsLogXuLy.data=[]
      var input = {
        vdenngay: '',
        vdonvi_id: this.DonViNhanId,
        vma_gd: this.MaGiaoDichSearch,
        vtheongay: this.fCheckDate ? 1 : 0,
        vttph_id: this.TrangThaiHdId,
        vtungay: ''
      }
      var self = this
      if (this.TuNgay.length > 0 && self.fCheckDate) {
        input.vtungay = this.TuNgay
      }
      if (this.DenNgay.length > 0 && self.fCheckDate) {
        input.vdenngay = this.DenNgay
      }
      try {
        this.Loading = true
        this.DsHopDong.data = this.GetData(await api.getDsHopDong(this.axios, input))
        this.Loading = false
      } catch (ex) {
        this.DsHopDong.data =[]
        this.Loading = false
      }
      this.DsThueBao.data = []
      if (this.DsHopDong.data && this.DsHopDong.data.length > 0) {
        this.DsHopDong.data.forEach(function (item) {
          item.ngay_yc = item.ngay_yc.substr(0, 10)
        })
        this.DsHopDong.data = this.DsHopDong.data.sort((a, b) => a.ngay_yc - b.ngay_yc)
        // this.$refs.refDsHopDong.grid.autoFitColumns()
      }
    },
    GetDsLogXuLy: async function (hdkhid) {
      if (hdkhid > 0) {
        var input = {
          p_hdkh_id: hdkhid
        }
        try {
          this.DsLogXuLy.data = this.GetData(await api.getDsLogXuLy(this.axios, input))
          if(this.DsLogXuLy.data!=null && this.DsLogXuLy.data.length > 0){
             this.DsLogXuLy.data.forEach(function (item) {
               try{
                 item.ngay_th_1 =item.ngay_th.substr(0, 10)
                 //item.ngay_th_1 =moment(item.ngay_th.substr(0, 10),'YYYY-MM-DD').format('DD/MM/YYYY')
               }
               catch(err){
                  console.log(err)
               }
          
          
          //this.$refs.gridDsLogXuLy.grid.autoFitColumns()
        })
          }
        } catch (ex) {
          this.DsLogXuLy.data=[]
          console.log(`Lỗi lấy thông tin log xử lý ${hdkhid} ${ex}`)
        }
      }
    },
    GetDsNhanVienTheoDonVi: async function () {
      var input = {
        vdonvi_id: this.DonViPbhId,
        vnlv_id: 2
      }
      this.Loading = true
      try {
        let data = this.GetData(await api.layDsNhanVienTheoDonVi(this.axios, input))
        this.DsNhanVienNhan = data.map((x) => ({ id: x.nhanvien_id, text: x.ten_nv == null ? '' : x.ten_nv }))
      } catch (ex) {
        this.Loading = false
        this.DsNhanVienNhan=[]
      }
      this.Loading = false
    },
    GetData: function (response) {
      if (response.data.error === 200 || response.data.error === '200') {
        return response.data.data
      } else {
        console.log(response.data.error_code)
      }
      return []
    },
    LayThongTin: async function () {
      this.Loading = true
      try {
        this.Clear()
        await this.GetDsHopDong()
        this.Loading = false
        if (this.DsHopDong.data == null || this.DsHopDong.data.length == 0) {
          this.ShowAlert(`Không tìm thấy thông tin hợp đồng !`)
        }
      } catch (ex) {
        this.Loading = false
      }
    },
    Clear(){
      this.MaGiaoDich=''
      this.NgayLapHd=''
      this.MaKhachHang=''
      this.TenKhachHang=''
      this.DonViNhan=''
    },
    select_selectedItemsChangedDagan: function (selectedItems) {
      this.DsHopDong.selectedItems = selectedItems
      /*
      this.DsHopDong.data.forEach(function (item) {
        if (dataKeys.filter((x) => x === item.hdtb_id).length > 0) {
          item.ischecked = true
        } else {
          item.ischecked = false
        }
      })
      */
    },

    GiaoNhanVien: async function () {
      if (this.DsHopDong.data.length == 0) {
        this.ShowAlert('Chưa có hợp động thuê bao')
        return
      }

      if (this.DonViPbhId == -1) {
        this.ShowAlert('Bạn chưa chọn phòng bán hàng')
        this.$refs.DonViPbhId.focus()
        return
      }
      //NhanVienNhanId
      if (this.NhanVienNhanId == '') {
        this.ShowAlert('Bạn chưa chọn nhân viên xử lý')
        this.$refs.NhanVienNhanId.focus()
        return
      }
      var hopDongSelecteds = this.DsHopDong.selectedItems
      if (hopDongSelecteds.length == 0) {
        this.ShowAlert('Bạn chưa chọn danh sách hợp đồng cần giao đi')
        return
      }
      if (hopDongSelecteds.filter((x) => x.nhanvien_id === this.NhanVienNhanId).length > 0) {
        var nhanvien = this.DsNhanVienNhan.filter((x) => x.nhanvien_id === this.NhanVienNhanId)[0]
        this.ShowAlert(`Trong hợp đồng cần giao đã có nhân viên tiếp nhận là  ${nhanvien.ten_nv} . Bạn hãy kiểm tra lại.`)
        return
      }
      let phanvungId = this.$root.token.getPhanVungID()
      var input = {
        vdonvi_nhan_id: this.DonViPbhId,
        vds_hd: [],
        vnhanvien_nhan_id: this.NhanVienNhanId,
        phanvung_id: phanvungId // không bắt buộc, hiện tại chỉ có phân vùng 28 có dữ liệu, để biến này để test lấy dữ liệu
      }
      /*
       {
            'HDKH_ID': this.HdKhId,
            'MA_GD': this.MaGiaoDich,
            'MA_KH': this.MaKhachHang,
            'NHANVIEN_ID': this.NhanVienId
          }
      */
      hopDongSelecteds.forEach(function (item) {
        input.vds_hd.push({ HDKH_ID: item.hdkh_id, MA_GD: item.ma_gd, MA_KH: item.ma_kh, NHANVIEN_ID: item.nhanvien_id })
      })
      this.$bvModal
        .msgBoxConfirm('Bạn có muốn giao danh sách hợp đồng đã chọn cho nhân viên?', {
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
          this.$root.showLoading(true)
          try {
            var response = await api.giaoPhieu(this.axios, input)
            this.$root.showLoading(false)
            if ((response.data.error === 200 || response.data.error === '200') && response.data.error_code === 'BSS-00000000') {
              if (response.data.data == '1') this.ShowSuccess('Thực hiện giao hợp đồng cho nhân viên thành công ')
              else this.ShowAlert(`Lỗi thực hiện ${response.data.data}`)
            } else {
              this.ShowAlert(`Lỗi thực hiện ${response.data.message}`)
            }
          } catch (ex) {
            this.$root.showLoading(false)
          }
        })

      // if(confirm(`Bạn có muốn giao danh sách hợp đồng đã chọn cho nhân viên`)){

      // }
    },
    ChangeHopDongKH: async function (i,item) {
      var self = this
      if (item != null) {
        this.HdKhId = item.hdkh_id
        this.MaKhachHang = item.ma_kh
        this.TenKhachHang = item.ten_kh
        this.NgayLapHd = item.ngaylap_hd
        this.DonViNhan = item.ten_dv
        this.MaGiaoDich = item.ma_gd
        var hds = this.DsHopDong.data.filter((x) => x.hdkh_id === item.hdkh_id)
        console.log(hds)
        self.DsThueBao.data = hds
        //this.$refs.gridDsThueBao.grid.autoFitColumns()
        /*
        hds.forEach(function (item2) {
          self.DsThueBao.data.push({
            ISCHECK: true,
            HDKH_ID: item2.hdkh_id,
            MA_GD: item2.ma_gd,
            MA_KH: item2.ma_kh,
            TEN_KH: item2.ten_kh,
            SO_DT: item2.so_dt,
            NGAYLAP_HD: item2.ngaylap_hd,
            DIACHI_KH: item2.diachi_kh,
            GHICHU_KH: item2.ghichu_kh,
            TEN_NV: item2.ten_nv,
            TEN_DV: item2.loaihinh_tb,
            NGAY_YC: item2.ngay_yc,
            MA_ND: item2.ma_nd,
            NHANVIEN_ID: item2.nhanvien_id
          })
        })
        */
        try {
          await this.GetDsLogXuLy(item.hdkh_id)
        } catch (ex) {
          console.log(er.message)
        }
      }
    },
    ShowAlert: function (message) {
      this.$toast.error(message)
    },
    ShowSuccess: function (message) {
      this.$toast.success(message)
    },
    ShowHide(){
      this.Expand=!this.Expand
    },
    UISplit: function () {
      if (!this.fSplit) {
        Split(['#boxLeft', '#boxRight'], {
          sizes: [50, 50],
          gutterSize: 16,
          onDragEnd: function (sizes) {}
        })
        Split(['#boxTop', '#boxBottom'], {
          sizes: [50, 50],
          direction: 'vertical'
        })
        this.fSplit = true
      }
    }
  }
}
</script>
