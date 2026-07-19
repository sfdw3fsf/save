<template src="./GiaoKHChamSocKH.html"></template>

<script>
import Vue from 'vue'
import moment from 'moment'
import { mapActions, mapState } from "vuex"
import GiaoKHChamSocKHAPI from '../api/GiaoKHChamSocKH.js'
import GanGDV from './components/GanGDV'
import GanDV from './components/GanDV'
import ChiTietGiaoCPS from './components/ChiTietGiaoCPS'
import Calculator from '@/components/Calculator'

import { DialogUtility } from '@syncfusion/ej2-popups'
Vue.use(DialogUtility)
let DialogObj = undefined

export default Vue.extend({
  components: {
    appCalculator: Calculator,
    appGanGDV: GanGDV,
    appGanDV: GanDV,
    appChiTietGiaoCPS: ChiTietGiaoCPS
  },
  computed : {
    ...mapState("qltnCommon", [
      "kyHoaDonHienTai"
    ]),
    chk_doanhthu_den()
    {
      return this.params.p_chk_doanhthu_den === '1'
    }
  },
  async created () {
    // load data
    this.loadDSLoaiNV()
    this.setButtons(-1)

    await this.getKyHoaDonHienTai()
    if (this.kyHoaDonHienTai !== undefined) {
      this.params.p_kyhoadon = this.kyHoaDonHienTai.KYCUOC
    }

    this.HienThiDanhSach()
  },
  data () {
    return {
      configs : {
        dsKeHoach: [
          {fieldName: 'LOAINV_ID', visible: false},
          {fieldName: 'TEN_LOAINV', headerText: 'Loại NV', headerTextAlign: 'Center', textAlign: 'Left', allowFiltering: true, width: 200},
          {fieldName: 'THUTU', headerText: 'Thứ tự', headerTextAlign: 'Center', textAlign: 'Center', allowFiltering: true, width: 80},
          {fieldName: 'NGAYCONG', headerText: 'Ngày công', headerTextAlign: 'Center', textAlign: 'Right', allowFiltering: true, width: 120, allowEditing: false},
          {fieldName: 'SOLUONG', headerText: 'Số lượng', headerTextAlign: 'Center', textAlign: 'Right', format: 'N0', type: 'number', allowFiltering: true, width: 120},
          {fieldName: 'SL_GIAO', headerText: 'SL giao', headerTextAlign: 'Center', textAlign: 'Right', format: 'N0', type: 'number', allowFiltering: true, width: 120},
          {fieldName: 'DOANHTHU_TU', headerText: 'Doanh thu từ', headerTextAlign: 'Center', textAlign: 'Right', format: 'N0', type: 'number', allowFiltering: true, width: 120},
          {fieldName: 'DOANHTHU_DEN', headerText: 'Doanh thu đến', headerTextAlign: 'Center', textAlign: 'Right', format: 'N0', type: 'number', allowFiltering: true, width: 120},
        ],
        fieldsLoaiNV: {text: 'TEN_LNV', value: 'LOAINV_ID' },
        isDTTuCalShow: false,
        isDTDenCalShow: false,
        f_addnew_enabled: false,
        f_save_enabled: false,
        f_delete_enabled: false,
        f_cancel_enabled: false,
        f_gan_gdv_enabled: false
      },
      params: {
        p_KEHOACH_ID: -1,
        p_kyhoadon: null,
        p_tim_kiem: null,
        p_loai_nv: null,
        p_loai_nv_options: [],
        p_thu_tu: 0,
        p_so_luong: 0,
        p_sl_giao: 0,
        p_ngay_cong: 0,
        p_doanhthu_tu: 0,
        p_doanhthu_den: 0,
        p_chk_doanhthu_den: '0',
        p_loainv_cu_id: null,
      },
      dsKeHoach: [],
      keyDSKeHoach: 0,
      kehoachPageInfo: {
        pageIndex: 0,
        pageSize: 10,
        totalElement: 0
      },
    }
  },  // end of data
  methods: {
    ...mapActions("qltnCommon", [
        "getKyHoaDonHienTai",
    ]),
    setButtons(kieu)
    {
      this.configs.f_addnew_enabled = false
      this.configs.f_save_enabled = false
      this.configs.f_delete_enabled = false
      this.configs.f_cancel_enabled = false

      if(kieu === -1) // bắt đầu
      {
        this.configs.f_save_enabled = true
        this.configs.f_cancel_enabled = true
      } else if (kieu === 0)  // bắt đầu
      {
        this.configs.f_addnew_enabled = true
        this.Clear()
      } else if (kieu === 1)  // thêm mới
      {
        this.configs.f_save_enabled = true
        this.configs.f_cancel_enabled = true
        this.Clear()
      } else if (kieu === 2)  // hủy
      {
        this.configs.f_addnew_enabled = true
        this.configs.f_delete_enabled = true
        this.Clear()
      } else if (kieu === 3)  // edit
      {
        this.configs.f_addnew_enabled = true
        this.configs.f_save_enabled = true
        this.configs.f_delete_enabled = true
        this.configs.f_cancel_enabled = true
      }

    },
    Clear()
    {
        this.params.p_KEHOACH_ID = -1
        this.params.p_thu_tu = 0
        this.params.p_so_luong = 0
        this.params.p_sl_giao = 0
        this.params.p_ngay_cong = 0
        this.params.p_doanhthu_tu = 0
        this.params.p_doanhthu_den = 0
    },
    loadDSLoaiNV()
    {
      this.loading(true)
      GiaoKHChamSocKHAPI.getDSLoaiNVThuCuoc(this.axios)
        .then( (res) => {
          if(res.data.error_code === 'BSS-00000000'
            && res.data.data !== undefined
            && res.data.data.length > 0
          )
          {
            this.params.p_loai_nv_options = res.data.data
            this.params.p_loai_nv = res.data.data[0].LOAINV_ID
          }
        })
        .catch(error => {
          this.$toast.error(error.data.message_detail)
        })
        .finally(() => {this.loading(false)})
    },

    HienThiDanhSach()
    {
      let postData = {
        kyCuoc: this.params.p_kyhoadon,
        donViId: this.$auth.getDonViID()
      }

      this.dsKeHoach = []



      this.loading(true)
      GiaoKHChamSocKHAPI.getKeHoachThuCuocPS(this.axios, postData)
        .then((res) => {
          if(res.data.error_code === 'BSS-00000000'
            && res.data.data !== undefined
            && res.data.data.length > 0
          )
          {
            this.dsKeHoach = res.data.data
            this.keyDSKeHoach++
          }
        })
        .catch(error => {
          this.$toast.error(error.data.message_detail)
        })
        .finally(() => { this.loading(false) })
    },

    /// button actions
    doAddNew()
    {
      this.setButtons(1)
    },
    doSave()
    {
      if(!this.KiemTra_DuLieu())  return false

      if(!this.configs.f_addnew_enabled)
        this.saveAddNew()
      else
        this.saveUpdate()
    },
    saveAddNew()
    {
      let postData = {
        "donViId": this.$auth.getDonViID(),
        "ds": [
          {
            "keHoachId": null,
            "kyCuoc": this.params.p_kyhoadon,
            "loaiNVId": this.params.p_loai_nv,
            "thuTu": this.params.p_thu_tu,
            "ngayCong": this.params.p_ngay_cong,
            "soLuong": this.params.p_so_luong,
            "slGiao": this.params.p_sl_giao,
            "doanhThuTu": this.params.p_doanhthu_tu,
            "doanhThuDen": this.params.p_chk_doanhthu_den === '1' ? this.params.p_doanhthu_den : 0
          }
        ]
      }

      this.loading(true)

      GiaoKHChamSocKHAPI.capnhatGiaoPhieu(this.axios, postData)
        .then((res) => {
          if(res.data.error_code === 'BSS-00000000')
          {
            this.$toast.success('Cập nhật dữ liệu thành công!')
            this.Clear()
            this.HienThiDanhSach()
          }
        })
        .catch(error => {
          this.$toast.error(error.data.message_detail)
        })
        .finally(() => {
          this.loading(false)
        })
    },
    saveUpdate()
    {
      let postData = {
        "donViId": this.$auth.getDonViID(),
        "ds": [
          {
            "keHoachId": this.params.p_KEHOACH_ID,
            "kyCuoc": this.params.p_kyhoadon,
            "loaiNVId": this.params.p_loai_nv,
            "thuTu": this.params.p_thu_tu,
            "ngayCong": this.params.p_ngay_cong,
            "soLuong": this.params.p_so_luong,
            "slGiao": this.params.p_sl_giao,
            "doanhThuTu": this.params.p_doanhthu_tu,
            "doanhThuDen": this.params.p_chk_doanhthu_den === '1' ? this.params.p_doanhthu_den : 0
          }
        ]
      }

      this.loading(true)

      GiaoKHChamSocKHAPI.capnhatGiaoPhieu(this.axios, postData)
        .then((res) => {
          if(res.data.error_code === 'BSS-00000000')
          {
            this.$toast.success('Cập nhật dữ liệu thành công!')
            this.Clear()
            this.HienThiDanhSach()
          }
        })
        .catch(error => {
          this.$toast.error(error.data.message_detail)
        })
        .finally(() => {
          this.loading(false)
        })
    },
    KiemTra_DuLieu()
    {
      let errors = []

      if(this.params.p_so_luong <= 0)
      {
        errors.push('Số lượng nhân viên phải > 0')
      }
      if(this.params.p_sl_giao <= 0)
      {
        errors.push('Số lượng giao phải > 0')
      }
      if(this.params.p_ngay_cong <= 0)
      {
        errors.push('Ngày công phải > 0')
      }
      if(this.params.p_doanhthu_tu <= 0)
      {
        errors.push('Doanh thu từ phải > 0')
      }
      if(this.params.p_chk_doanhthu_den === '1' && this.params.p_doanhthu_den <= 0)
      {
        errors.push('Doanh thu đến phải > 0')
      }

      if(!this.configs.f_addnew_enabled) // thêm mới
      {
        // check loại NV
        let f = this.dsKeHoach.filter(x => x.LOAINV_ID === this.params.p_loai_nv)

        if(f.length > 0)
        {
          errors.push('Đã giao phát sinh với Loại nhân viên này. Không thể thêm mới!')
        }
      }

      if(errors.length > 0)
      {
        this.$toast.error(errors.join('\r\n'))
        return false
      }

      return true
    },
    doDelete()
    {
      DialogObj = DialogUtility.confirm({
          title: 'Thông báo',
          content: "<div style='padding:10px'>Bạn chắc chắn muốn xóa không?</div>",
          okButton: {  text: 'Đồng ý', click: this.confirmDelete },
          cancelButton: {  text: 'Hủy', click: function(){ DialogObj.hide(); return false}},
          showCloseIcon: true,
          closeOnEscape: true,
          animationSettings: { effect: 'Zoom' }
      });
    },
    confirmDelete()
    {
      DialogObj.hide()
      this.loading(true)
      GiaoKHChamSocKHAPI.xoaGiaoKeHoach(this.axios, this.params.p_KEHOACH_ID)
        .then((res) => {
          if(res.data.error_code === 'BSS-00000000')
          {
            this.$toast.success('Xóa dữ liệu thành công!')
            this.HienThiDanhSach()
          }

        })
        .catch(error => {
          this.$toast.error(error.data.message_detail)
        })
        .finally(() => {
          this.loading(false)
        })
    },
    doGanGDV()
    {
      this.$refs.refGanGDV.openDialog()
    },
    doGanDichVu()
    {
      this.$refs.refGanDV.openDialog()
    },
    doCancel()
    {
      this.setButtons(0)
    },
    doThucHienGiao()
    {
      this.loading(true)
      GiaoKHChamSocKHAPI.capnhatCTkeHoachCPS(this.axios, this.params.p_kyhoadon)
        .then((res) => {
          if(res.data.error_code === 'BSS-00000000')
          {
            this.$toast.success('Thực hiện giao thành công!')
          }
        })
        .catch(error => {
          this.$toast.error(error.data.message_detail)
        })
        .finally(() => {
          this.loading(false)
        })
    },
    doChiTietGiao()
    {
      if(this.params.p_KEHOACH_ID < 0)
      {
        this.$toast.error('Bạn chưa chọn kế hoạch')
        return
      }
      this.$refs.refChiTietGiaoCPS.openDialog()
    },
    /* Handlers*/

    dsKeHoachRowChanged(args)
    {
      if(args === null) return

      this.params.p_KEHOACH_ID = args.KEHOACH_ID
      this.params.p_loainv_cu_id = args.LOAINV_ID
      this.params.p_loai_nv = args.LOAINV_ID
      this.params.p_so_luong = args.SOLUONG
      this.params.p_sl_giao = args.SL_GIAO
      this.params.p_ngay_cong = args.NGAYCONG
      this.params.p_thu_tu = args.THUTU

      if(args.DOANHTHU_THU !== null && args.DOANHTHU_TU !== '' && args.DOANHTHU_TU != '0')
      {
        this.params.p_doanhthu_tu = args.DOANHTHU_TU
      }

      if(args.DOANHTHU_DEN !== null && args.DOANHTHU_DEN !== '' && args.DOANHTHU_DEN != '0')
      {
        this.params.p_chk_doanhthu_den = '1'
        this.params.p_doanhthu_den = args.DOANHTHU_DEN
      } else
      {
        this.params.p_chk_doanhthu_den = '0'
      }

      if(this.params.p_loainv_cu_id === 84) // Chỉ với loại nv gdv mới hiện để gán
        this.configs.f_gan_gdv_enabled = true
      else
        this.configs.f_gan_gdv_enabled = false

      this.setButtons(3)
    },

    onFilteringLoaiNV(e){
      var query = new Query();
      let keyword = e.text.trim()
      query = (e.text !== '') ? query.where('TEN_LNV', 'contains', keyword, true) : query;
      e.updateData(this.params.p_loai_nv_options, query);
    },
  },  // end of methods
})
</script>
