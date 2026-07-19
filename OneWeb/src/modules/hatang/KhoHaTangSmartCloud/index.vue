<template src="./index.html"></template>

<script>
import DialogThietBi from '../ThietBiHinhThanhKhoHaTang/index.vue'

export default {
  components: {
    DialogThietBi
  },
  props: {
    modalItemID: 0
  },
  data: function() {
    return {
      user_info: {
        nhanvien_id: 0,
        nhanvien_may: '',
        nhanvien_ten: '',
        nhanvien_ip: '',
        phanvung_id: 0,
        donvi_id: 0,
        donvi_ten: '',
        donvi_cha_id: 0,
        donvi_cha_ten: ''
      },
      image_item: '',
      current_item: {
        id: 0,
        mahatang_infra: '',
        ten: '',
        hinhthucdautu: 0,
        loaihatang_id: 0,
        hatang_phanloai_id: 0,
        hatang_congnghe_id: 0,
        idc_id: 0,
        hatangmang_id: null,
        thietbi_tudia_id: null,
        nangluc_luutru: [],
        admin_link: '',
        avatar: '',
        ghi_chu: '',
        hieu_luc: 1,
        donviqly_id: 0,
        donviqly_name: '',
        phongbanqly_id: 0,
        phongbanqly_name: '',
        nguoiqly_id: 0,
        nguoiqly_name: '',
        hatang_capdo_id: 0,
        vcpu_nangluc: 0,
        vcpu_cap: 0,
        vcpu_total: 0,
        vram_nangluc: 0,
        vram_cap: 0,
        vram_total: 0,
        gpu_nangluc: 0,
        gpu_cap: 0,
        gpu_total: 0,
        nhanvien_id: 0,
        may_capnhap: '',
        nguoi_capnhap: '',
        ip_capnhap: ''
      },
      smart_cloud_items: [],
      may_ao_items: [],
      ha_tang_idg_items: [],
      san_pham_dich_vu_items: [],
      tu_dia_items: [],
      thietbi_daunoi_items: [],
      lichsu_thaydoi_items: [],
      select_nangluc_luutru_tudia: [],
      select_hinhthuc_dautu: [],
      select_loai_hatang: [],
      select_phanloai_hatang: [],
      select_congnghe_trienkhai: [],
      select_idc: [],
      select_thuoc_hatang_mang: [],
      select_capdo_hantang: [],
      controls: {
        text: {
          enabled: false,
          invalid: false
        },
        selection: {
          enabled: false,
          invalid: false
        },
        checkbox: {
          enabled: false
        },
        btn: {
          enabled: false
        }
      },
      buttons: {
        btnAddNew: {
          enabled: true
        },
        btnSave: {
          enabled: false
        },
        btnCancel: {
          enabled: false
        },
        btnEdit: {
          enabled: false
        },
        btnDelete: {
          enabled: false
        },
        btnAddImage: '',
        imgAvatar: 'hidden'
      },
      stt_columns: [
        {
          field: 'sothutu',
          headerText: 'STT',
          headerAlign: 'Center',
          cssClass: 'text-center',
          textAlign: 'Center',
          width: 130,
          allowFiltering: true
        }
      ],
      ten_tudia_columns: [
        {
          field: 'ten',
          headerText: 'Tên tủ đĩa',
          headerAlign: 'Center',
          textAlign: 'Center',
          cssClass: 'text-center',
          allowFiltering: true
        }
      ],
      sdd_columns: [
        {
          field: 'ssd_tong',
          headerText: 'Tổng',
          headerAlign: 'Center',
          textAlign: 'Center',
          cssClass: 'text-center',
          allowFiltering: false
        },
        {
          field: 'ssd_nangluc',
          headerText: 'Khả năng cấp',
          textAlign: 'Center',
          headerAlign: 'Center',
          cssClass: 'text-center',
          allowFiltering: false
        },
        {
          field: 'ssd_capphat',
          headerText: 'Đã cấp',
          textAlign: 'Center',
          headerAlign: 'Center',
          cssClass: 'text-center',
          allowFiltering: false
        }
      ],
      hdd_columns: [
        {
          field: 'hdd_tong',
          headerText: 'Tổng',
          textAlign: 'Center',
          headerAlign: 'Center',
          cssClass: 'text-center',
          allowFiltering: false
        },
        {
          field: 'hdd_nangluc',
          headerText: 'Khả năng cấp',
          headerAlign: 'Center',
          textAlign: 'Center',
          cssClass: 'text-center',
          allowFiltering: false
        },
        {
          field: 'hdd_capphat',
          headerText: 'Đã cấp',
          textAlign: 'Center',
          headerAlign: 'Center',
          cssClass: 'text-center',
          allowFiltering: false
        }
      ],
      iops_columns: [
        {
          field: 'iops_tong',
          headerText: 'Tổng',
          width: '30%',
          textAlign: 'Center',
          headerAlign: 'Center',
          cssClass: 'text-center',
          allowFiltering: false
        },
        {
          field: 'iops_nangluc',
          headerText: 'Khả năng cấp',
          width: '40%',
          textAlign: 'Center',
          headerAlign: 'Center',
          cssClass: 'text-center',
          allowFiltering: false
        },
        {
          field: 'iops_capphat',
          headerText: 'Đã cấp',
          width: '30%',
          textAlign: 'Center',
          headerAlign: 'Center',
          cssClass: 'text-center',
          allowFiltering: false
        }
      ]
    }
  },
  computed: {
    grdItems: function() {
      return this.$refs.grdItems
    }
  },
  watch: {
    'current_item.id': function(value) {
      this.buttons.btnEdit.enabled = value > 0
      this.buttons.btnDelete.enabled = value > 0
    },
    'current_item.vcpu_nangluc': function(value) {
      if (value < 0 || value > this.current_item.vcpu_total) {
        this.$root.toastError('Giá trị vCPU khả năng cấp phải bé hơn tổng và lớn hơn hoặc bằng 0.')
        this.current_item.vcpu_nangluc = value < 0 ? 0 : this.current_item.vcpu_total
      }
    },
    'current_item.vcpu_cap': function(value) {
      if (value < 0) {
        this.$root.toastError('Giá trị vCPU đã cấp phải lớn hơn hoặc bằng 0.')
        this.current_item.vcpu_cap = 0
      }
    },
    'current_item.vcpu_total': function(value) {
      if (value < 0) {
        this.$root.toastError('Giá trị vCPU tổng phải lớn hơn hoặc bằng 0.')
        this.current_item.vcpu_total = 0
      }
    },
    'current_item.vram_nangluc': function(value) {
      if (value < 0 || value > this.current_item.vram_total) {
        this.$root.toastError('Giá trị vRAM khả năng cấp phải bé hơn tổng và lớn hơn hoặc bằng 0.')
        this.current_item.vram_nangluc = value < 0 ? 0 : this.current_item.vram_total
      }
    },
    'current_item.vram_cap': function(value) {
      if (value < 0) {
        this.$root.toastError('Giá trị vRAM đã cấp phải lớn hơn hoặc bằng 0.')
        this.current_item.vram_cap = 0
      }
    },
    'current_item.vram_total': function(value) {
      if (value < 0) {
        this.$root.toastError('Giá trị vRAM tổng phải lớn hơn hoặc bằng 0.')
        this.current_item.vram_total = 0
      }
    },
    'current_item.gpu_nangluc': function(value) {
      if (value < 0 || value > this.current_item.gpu_total) {
        this.$root.toastError('Giá trị vGPU khả năng cấp phải bé hơn tổng và lớn hơn hoặc bằng 0.')
        this.current_item.gpu_nangluc = value < 0 ? 0 : this.current_item.gpu_total
      }
    },
    'current_item.gpu_cap': function(value) {
      if (value < 0) {
        this.$root.toastError('Giá trị vGPU đã cấp phải lớn hơn hoặc bằng 0.')
        this.current_item.gpu_cap = 0
      }
    },
    'current_item.gpu_total': function(value) {
      if (value < 0) {
        this.$root.toastError('Giá trị vGPU tổng phải lớn hơn hoặc bằng 0.')
        this.current_item.gpu_total = 0
      }
    }
  },
  mounted: async function() {
    // init current user Info
    this.user_info.nhanvien_id = await this.$root.token.getNhanVienID()
    this.user_info.nhanvien_ten = await this.$root.context.user.getTenNhanVien()
    this.user_info.nhanvien_may = await this.$root.token.getMachine()
    this.user_info.nhanvien_ip = await this.$root.token.getIP()
    this.user_info.phanvung_id = await this.$root.token.getPhanVungID()
    this.user_info.donvi_id = await this.$root.token.getDonViID()
    this.user_info.donvi_ten = await this.$root.context.user.getTenDonVi()

    this.user_info.donvi_cha_id = this.user_info.donvi_id
    this.user_info.donvi_cha_ten = this.user_info.donvi_ten
    let ds = await this.apiGetDonViCha(this.user_info.donvi_id)
    if (ds != null && ds.length > 0) {
      this.user_info.donvi_cha_id = ds[0].DONVI_ID
      this.user_info.donvi_cha_ten = ds[0].TEN_DV
    }

    // get selection info
    this.select_hinhthuc_dautu = [
      { ID: 1, TEN: 'Hợp tác' },
      { ID: 2, TEN: 'Đầu tư nội bộ' },
      { ID: 3, TEN: 'Thuê quản trị' },
      { ID: 4, TEN: 'Khác' }
    ]
  },
  created: async function() {
    try {
      this.$root.showLoading(true)
      // all items smart cloud
      this.smart_cloud_items = await this.apiListItems()

      this.select_idc = await this.apiListIDC()
      this.select_loai_hatang = await this.apiListLoaiHaTang()
      this.select_phanloai_hatang = await this.apiListPhanLoaiHaTang()
      this.select_congnghe_trienkhai = await this.apiListCongNgheTrienKhai()
      this.select_capdo_hantang = await this.apiListHaTangCapDo()
    } finally {
      this.$root.showLoading(false)
    }
  },
  methods: {
    apiGetDonViCha: async function(pId) {
      let result = []
      if (pId == null || pId < 1) {
        return
      }
      try {
        let rs = await this.$root.context.get('/web-ecms/danhmuc/don-vi-cha', {
          donViId: pId
        })
        result = rs.data
      } catch (e) {
        this.$toast.error(e.response.data.message_detail)
      }
      return result
    },
    apiListItems: async function() {
      let result = []
      try {
        if (this.modalItemID > 0) {
          let rs = await this.$root.context.post('/web-ecms/smart-cloud/get-item', {
            id: this.modalItemID
          })
          result = rs.data
        } else {
          let rs = await this.$root.context.get('/web-ecms/smart-cloud/get-all')
          result = rs.data
        }
        result.forEach((element) => {
          let HATANGMANG_ID = []
          let HATANGMANG_NAME = []
          if (element.hatangmang) {
            JSON.parse(element.hatangmang).forEach((item) => {
              HATANGMANG_ID.push(item.HATANGMANG_ID)
              HATANGMANG_NAME.push(item.HATANGMANG_NAME)
            })
            element.hatangmang_id = HATANGMANG_ID
            element.hatangmang_name = HATANGMANG_NAME.join(', ')
          }
          if (element.nangluc_luutru) {
            let thietbi_tudia_id = []
            JSON.parse(element.nangluc_luutru).forEach((item, index) => {
              thietbi_tudia_id.push(item.thietbi_id)
              // this.select_nangluc_luutru_tudia.push(item)
            })
            element.thietbi_tudia_id = thietbi_tudia_id
            element.nangluc_luutru = JSON.parse(element.nangluc_luutru)
          }
          console.log('element', element)
        })
      } catch (e) {
        this.$toast.error(e.response.data.message_detail)
      }
      return result
    },
    apiListHistory: async function(id) {
      let result = []
      try {
        let rs = await this.$root.context.get('/web-ecms/smart-cloud/change-history', {
          id: id
        })
        result = rs.data
      } catch (e) {
        this.$toast.error(e.response.data.message_detail)
      }
      return result
    },
    apiListMayao: async function(id) {
      let result = []
      try {
        let rs = await this.$root.context.get('/web-ecms/smart-cloud/may-ao', { id: id })
        result = rs.data
      } catch (e) {
        this.$toast.error(e.response.data.message_detail)
      }
      return result
    },
    apiListHaTangIdg: async function(id) {
      let result = []
      try {
        let rs = await this.$root.context.get('/web-ecms/smart-cloud/hatang-idg', {
          id: id
        })
        result = rs.data
      } catch (e) {
        this.$toast.error(e.response.data.message_detail)
      }
      return result
    },
    apiListSanPhamDichVu: async function(id) {
      let result = []
      try {
        let rs = await this.$root.context.get('/web-ecms/smart-cloud/sanpham-dichvu', {
          id: id
        })
        result = rs.data
      } catch (e) {
        this.$toast.error(e.response.data.message_detail)
      }
      return result
    },
    apiListTuDia: async function(id) {
      let result = []
      try {
        let rs = await this.$root.context.get('/web-ecms/smart-cloud/tu-dia', { id: id })
        result = rs.data
      } catch (e) {
        this.$toast.error(e.response.data.message_detail)
      }
      return result
    },
    apiListThietBiDauNoi: async function(id) {
      let result = []
      try {
        let rs = await this.$root.context.get('/web-ecms/smart-cloud/thietbi-daunoi', {
          id: id
        })
        result = rs.data
      } catch (e) {
        this.$toast.error(e.response.data.message_detail)
      }
      return result
    },
    apiListLoaiHaTang: async function() {
      let result = []
      try {
        let rs = await this.$root.context.get('/web-ecms/danhmuc/loai-ha-tang')
        result = rs.data
      } catch (e) {
        this.$toast.error(e.response.data.message_detail)
      }
      return result
    },
    apiListPhanLoaiHaTang: async function() {
      let result = []
      try {
        let rs = await this.$root.context.get('/web-ecms/danhmuc/ha-tang-phan-loai')
        result = rs.data
      } catch (e) {
        this.$toast.error(e.response.data.message_detail)
      }
      return result
    },
    apiListCongNgheTrienKhai: async function() {
      let result = []
      try {
        let rs = await this.$root.context.get('/web-ecms/danhmuc/ha-tang-cong-nghe')
        result = rs.data
      } catch (e) {
        this.$toast.error(e.response.data.message_detail)
      }
      return result
    },
    apiListIDC: async function() {
      let result = []
      try {
        let rs = await this.$root.context.get('/web-ecms/danhmuc/ha-tang-idc')
        result = rs.data
      } catch (e) {
        this.$toast.error(e.response.data.message_detail)
      }
      return result
    },
    apiListHaTangMangByIDCId: async function(id) {
      let result = []
      try {
        let rs = await this.$root.context.get('/web-ecms/danhmuc/ha-tang-mang-by-idc-id', { id: id })
        rs.data.forEach((element) => {
          result.push({ id: element.ID, label: element.TEN })
        })
      } catch (e) {
        this.$toast.error(e.response.data.message_detail)
      }
      return result
    },
    apiListThieBiTuDiaByIDCId: async function(id) {
      let result = []
      try {
        let rs = await this.$root.context.get('/web-ecms/kho-hatang-backup/thietbi-hinhthanh-khohatang', {
          idc_id: id,
          hatang: 'SMARTCOUD_TUDIA',
          old_thietbi_ids: ''
        })
        result = rs.data
        result.forEach((element) => {
          element.id = element.THIETBI_ID
          element.label = element.TEN
        })
      } catch (e) {
        this.$toast.error(e.response.data.message_detail)
      }
      return result
    },
    apiListHaTangCapDo: async function() {
      let result = []
      try {
        let rs = await this.$root.context.post('/web-ecms/hatang/hatang_capdo/getall')
        result = rs.data
      } catch (e) {
        this.$toast.error(e.response.data.message_detail)
      }
      return result
    },
    apiSave: async function(args, thietbi_list) {
      let result
      try {
        result = await this.$root.context.post('/web-ecms/smart-cloud/create-or-update', {
          id: Number(args.id),
          mahatang_infra: args.mahatang_infra,
          ten: args.ten,
          hinhthucdautu: Number(args.hinhthucdautu),
          loaihatang_id: Number(args.loaihatang_id),
          hatang_phanloai_id: Number(args.hatang_phanloai_id),
          hatang_congnghe_id: Number(args.hatang_congnghe_id),
          idc_id: Number(args.idc_id),
          hatangmang_id: args.hatangmang_id.map((item) => item),
          thietbi_tudia_id: args.nangluc_luutru.map((item) => item.thietbi_id),
          admin_link: args.admin_link,
          avatar: args.avatar,
          ghi_chu: args.ghi_chu,
          hieu_luc: Number(args.hieu_luc),
          donviqly_id: Number(args.donviqly_id),
          phongbanqly_id: Number(args.phongbanqly_id),
          nguoiqly_id: Number(args.nguoiqly_id),
          hatang_capdo_id: Number(args.hatang_capdo_id),
          vcpu_nangluc: Number(args.vcpu_nangluc),
          vcpu_cap: Number(args.vcpu_cap),
          vcpu_total: Number(args.vcpu_total),
          vram_nangluc: Number(args.vram_nangluc),
          vram_cap: Number(args.vram_cap),
          vram_total: Number(args.vram_total),
          gpu_nangluc: Number(args.gpu_nangluc),
          gpu_cap: Number(args.gpu_cap),
          gpu_total: Number(args.gpu_total),
          nhanvien_id: Number(args.nhanvien_id),
          may_capnhap: args.may_capnhap,
          nguoi_capnhap: args.nguoi_capnhap,
          ip_capnhap: args.ip_capnhap,
          thietBiHinhThanhList: thietbi_list.map((item) => {
            return {
              id: item.id,
              thietbi_id: item.thietbi_id,
              ngay_cn: item.f_ngay_cn
            }
          })
        })
      } catch (e) {
        result = false
      }
      return result
    },
    apiDelete: async function(id) {
      let result = false
      try {
        let rs = await this.$root.context.get('/web-ecms/smart-cloud/delete', { id: id })
        result = rs.error_code == 'BSS-00000000'
      } catch (e) {
        this.$root.toastError(e.response.data.message)
        result = false
      }
      return result
    },
    enableControls: function(args) {
      for (const key in this.controls) {
        if (this.controls.hasOwnProperty(key)) {
          this.controls[key].enabled = args
        }
      }

      this.buttons.btnSave.enabled = args
      this.buttons.btnAddNew.enabled = !args
      this.buttons.btnCancel.enabled = this.buttons.btnSave.enabled
      this.buttons.btnDelete.enabled = !args

      if (this.current_item.id > 0) this.buttons.btnEdit.enabled = !this.buttons.btnSave.enabled
    },
    setCurrentItem: async function(item) {
      this.current_item.id = item.id
      this.current_item.mahatang_infra = item.mahatang_infra
      this.current_item.ten = item.ten
      this.current_item.hinhthucdautu = item.hinhthucdautu
      this.current_item.loaihatang_id = item.loaihatang_id
      this.current_item.hatang_phanloai_id = item.hatang_phanloai_id
      this.current_item.hatang_congnghe_id = item.hatang_congnghe_id
      this.current_item.idc_id = item.idc_id
      this.current_item.hatangmang_id = item.hatangmang_id
      this.current_item.nangluc_luutru = item.nangluc_luutru
      this.current_item.thietbi_tudia_id = item.thietbi_tudia_id
      this.current_item.admin_link = item.admin_link
      this.current_item.avatar = item.avatar
      this.current_item.ghi_chu = item.ghi_chu
      this.current_item.hieu_luc = item.hieu_luc
      this.current_item.donviqly_id = item.donviqly_id
      this.current_item.donviqly_name = item.donviqly_name
      this.current_item.phongbanqly_id = item.phongbanqly_id
      this.current_item.phongbanqly_name = item.phongbanqly_name
      this.current_item.nguoiqly_id = item.nguoiqly_id
      this.current_item.nguoiqly_name = item.nguoiqly_name
      this.current_item.hatang_capdo_id = item.hatang_capdo_id
      this.current_item.vcpu_nangluc = item.vcpu_nangluc
      this.current_item.vcpu_cap = item.vcpu_cap
      this.current_item.vcpu_total = item.vcpu_total
      this.current_item.vram_nangluc = item.vram_nangluc
      this.current_item.vram_cap = item.vram_cap
      this.current_item.vram_total = item.vram_total
      this.current_item.gpu_nangluc = item.gpu_nangluc
      this.current_item.gpu_cap = item.gpu_cap
      this.current_item.gpu_total = item.gpu_total

      if (item.avatar != null && item.avatar != '') {
        this.image_item = 'data:image/*;base64,' + item.avatar
        this.buttons.btnAddImage = 'hidden'
        this.buttons.imgAvatar = 'img-fluid'
      } else {
        this.buttons.btnAddImage = ''
        this.buttons.imgAvatar = 'hidden'
      }

      this.current_item.nhanvien_id = this.user_info.nhanvien_id
      this.current_item.may_capnhap = this.user_info.nhanvien_may
      this.current_item.nguoi_capnhap = this.user_info.nhanvien_ten
      this.current_item.ip_capnhap = this.user_info.nhanvien_ip
    },
    setCurrentItemDefault: function() {
      this.setCurrentItem({
        id: 0,
        mahatang_infra: '',
        ten: '',
        hinhthucdautu: 0,
        loaihatang_id: 0,
        hatang_phanloai_id: 0,
        hatang_congnghe_id: 0,
        idc_id: 0,
        hatangmang_id: null,
        thietbi_tudia_id: null,
        nangluc_luutru: [],
        admin_link: '',
        avatar: '',
        ghi_chu: '',
        hieu_luc: 1,
        donviqly_id: this.user_info.donvi_cha_id,
        donviqly_name: this.user_info.donvi_cha_ten,
        phongbanqly_id: this.user_info.donvi_id,
        phongbanqly_name: this.user_info.donvi_ten,
        nguoiqly_id: this.user_info.nhanvien_id,
        nguoiqly_name: this.user_info.nhanvien_ten,
        hatang_capdo_id: 0,
        vcpu_nangluc: 0,
        vcpu_cap: 0,
        vcpu_total: 0,
        vram_nangluc: 0,
        vram_cap: 0,
        vram_total: 0,
        gpu_nangluc: 0,
        gpu_cap: 0,
        gpu_total: 0
      })
      this.image_item = ''
    },
    validateControls: function() {
      let flg = true
      if (this.current_item.mahatang_infra == '') {
        this.$root.toastError('Mã hạ tầng ảo hóa bên CĐS-ĐHIT không được để trống.')
        flg = false
      }
      if (this.current_item.ten == '') {
        this.$root.toastError('Tên hạ tầng không được để trống.')
        flg = false
      }
      if (this.current_item.hinhthucdautu == 0) {
        this.$root.toastError('Hình thức đầu tư không được để trống.')
        flg = false
      }
      if (this.current_item.loaihatang_id == 0) {
        this.$root.toastError('Loại hạ tầng không được để trống.')
        flg = false
      }
      if (this.current_item.hatang_phanloai_id == 0 || this.current_item.hatang_phanloai_id == null) {
        this.$root.toastError('Phân loại hạ tầng không được để trống.')
        flg = false
      }
      if (this.current_item.idc_id == 0) {
        this.$root.toastError('IDC không được để trống không được để trống.')
        flg = false
      }
      if (this.current_item.hatangmang_id == null || this.current_item.hatangmang_id.length <= 0) {
        this.$root.toastError('Thuộc hạ tầng mạng không được để trống.')
        flg = false
      }
      if (!this.current_item.hatang_capdo_id != 0) {
        this.$root.toastError('Cấp độ hạ tầng không được để trống.')
        flg = false
      }
      if (this.current_item.nangluc_luutru == null || this.current_item.nangluc_luutru.length <= 0) {
        this.$root.toastError('Năng lực lưu trữ tủ đĩa không được để trống.')
        flg = false
      }
      return flg
    },
    grdItems_RowSelected: async function(args) {
      try {
        this.$root.showLoading(true)
        this.setCurrentItem(args.data)
        this.lichsu_thaydoi_items = await this.apiListHistory(args.data.id)
        this.may_ao_items = await this.apiListMayao(args.data.id)
        this.ha_tang_idg_items = await this.apiListHaTangIdg(args.data.id)
        this.tu_dia_items = await this.apiListTuDia(args.data.id)
        this.thietbi_daunoi_items = await this.apiListThietBiDauNoi(args.data.id)
        this.san_pham_dich_vu_items = await this.apiListSanPhamDichVu(args.data.id)

        this.select_thuoc_hatang_mang = await this.apiListHaTangMangByIDCId(this.current_item.idc_id)
        this.select_nangluc_luutru_tudia = await this.apiListThieBiTuDiaByIDCId(this.current_item.idc_id)
      } finally {
        this.$root.showLoading(false)
      }
    },
    grdItems_RowDeselected: async function() {
      let items = this.grdItems.getSelectedRecords()
      if (!(items == null || items.length == 0)) {
        try {
          this.$root.showLoading(true)
          this.setCurrentItem(items[0])
          this.lichsu_thaydoi_items = await this.apiListHistory(items[0].id)
          this.may_ao_items = await this.apiListMayao(items[0].id)
          this.ha_tang_idg_items = await this.apiListHaTangIdg(items[0].id)
          this.tu_dia_items = await this.apiListTuDia(items[0].id)
          this.thietbi_daunoi_items = await this.apiListThietBiDauNoi(items[0].id)
          this.san_pham_dich_vu_items = await this.apiListSanPhamDichVu(items[0].id)

          this.select_thuoc_hatang_mang = await this.apiListHaTangMangByIDCId(this.current_item.idc_id)
          this.select_nangluc_luutru_tudia = await this.apiListThieBiTuDiaByIDCId(this.current_item.idc_id)
        } finally {
          this.$root.showLoading(false)
        }
      } else {
        this.setCurrentItemDefault()
        this.lichsu_thaydoi_items = []
        this.may_ao_items = []
        this.ha_tang_idg_items = []
        this.tu_dia_items = []
        this.thietbi_daunoi_items = []

        this.select_thuoc_hatang_mang = []
        this.select_nangluc_luutru_tudia = []
      }
    },
    onAddNew: async function() {
      this.setCurrentItemDefault()
      this.lichsu_thaydoi_items = []
      this.may_ao_items = []
      this.ha_tang_idg_items = []
      this.tu_dia_items = []
      this.thietbi_daunoi_items = []
      this.san_pham_dich_vu_items = []

      this.select_thuoc_hatang_mang = []
      this.select_nangluc_luutru_tudia = []

      this.enableControls(true)
      this.buttons.btnAddImage = ''
      this.buttons.imgAvatar = 'hidden'
      this.haTangMangSelectionList = []
    },
    onSave: async function() {
      console.log('current_item', this.current_item)
      if (this.validateControls()) {
        let flag = await this.$confirm('Bạn có muốn lưu thông tin này không ?', 'Xác nhận', {
          confirmButtonText: 'Có',
          cancelButtonText: 'Không',
          type: 'info',
          dangerouslyUseHTMLString: true
        })
          .then((s) => true)
          .catch((e) => {
            return false
          })
        if (flag) {
          try {
            let mess = this.current_item.id == 0 ? 'Thêm mới thông tin thành công.' : 'Cập nhật thông tin thành công.'
            this.$root.showLoading(true)
            let rs = await this.apiSave(this.current_item, this.thietbi_daunoi_items)
            if (rs.data > 0) {
              this.smart_cloud_items = await this.apiListItems()
              this.enableControls(!rs)
              this.$root.toastSuccess(mess)
            } else if (rs.data == 0) {
              this.$root.toastError('Đối tượng thao tác không tồn tại.')
            } else if (rs.data == -1) {
              this.$root.toastError('Tên hạ tầng SmartCloud đã tồn tại.')
            } else if (rs.data == -2) {
              this.$root.toastError('Mã hạ tầng SmartCloud đã tồn tại.')
            } else if (rs.data == -3) {
              this.$root.toastError('Tên và Mã hạ tầng SmartCloud đã tồn tại.')
            } else {
              this.$root.toastError('Chức năng lỗi.')
            }
          } finally {
            this.$root.showLoading(false)
          }
        }
      }
    },
    onCancel: async function(args) {
      let items = this.grdItems.getSelectedRecords()
      let item = null
      if (!(items == null || items.length == 0)) {
        if (this.current_item.id == 0) {
          item = items[0]
        } else {
          item = items.find((x) => x.id == this.current_item.id)
        }
      }
      if (item != null) {
        this.setCurrentItem(item)
        this.lichsu_thaydoi_items = await this.apiListHistory(item.id)
        this.may_ao_items = await this.apiListMayao(item.id)
        this.ha_tang_idg_items = await this.apiListHaTangIdg(item.id)
        this.tu_dia_items = await this.apiListTuDia(item.id)
        this.thietbi_daunoi_items = await this.apiListThietBiDauNoi(item.id)
        this.san_pham_dich_vu_items = await this.apiListSanPhamDichVu(item.id)

        this.select_thuoc_hatang_mang = await this.apiListHaTangMangByIDCId(this.current_item.idc_id)
        this.select_nangluc_luutru_tudia = await this.apiListThieBiTuDiaByIDCId(this.current_item.idc_id)
      } else {
        this.setCurrentItemDefault()
        this.lichsu_thaydoi_items = []
        this.may_ao_items = []
        this.ha_tang_idg_items = []
        this.tu_dia_items = []
        this.thietbi_daunoi_items = []
        this.san_pham_dich_vu_items = []

        this.select_thuoc_hatang_mang = []
        this.select_nangluc_luutru_tudia = []
      }
      this.enableControls(false)
    },
    onEdit: async function(args) {
      this.current_item.donviqly_id = this.user_info.donvi_cha_id
      this.current_item.donviqly_name = this.user_info.donvi_cha_ten
      this.current_item.phongbanqly_id = this.user_info.donvi_id
      this.current_item.phongbanqly_name = this.user_info.donvi_ten
      this.current_item.nguoiqly_id = this.user_info.nhanvien_id
      this.current_item.nguoiqly_name = this.user_info.nhanvien_ten
      this.enableControls(true)
    },
    onDelete: async function(args) {
      let items = this.grdItems.getSelectedRecords()
      let msg = `Bạn có muốn xóa ${items.length} mẫu tin đã chọn không?`
      let flag = await this.$confirm(msg, 'Xác nhận', {
        confirmButtonText: 'Có',
        cancelButtonText: 'Không',
        type: 'warning',
        dangerouslyUseHTMLString: true
      })
        .then((s) => true)
        .catch((e) => {
          return false
        })
      if (flag) {
        try {
          this.$root.showLoading(true)
          let isSuccess = false
          for (const item of items) {
            isSuccess = await this.apiDelete(item.id)
            if (!isSuccess) {
              break
            }
          }
          if (isSuccess) {
            this.setCurrentItemDefault()
            this.smart_cloud_items = await this.apiListItems()
            this.enableControls(false)
            this.$root.toastSuccess('Xóa thông tin thành công')
          }
        } finally {
          this.$root.showLoading(false)
        }
      }
    },
    chooseFiles(event) {
      if (this.buttons.btnAddNew.enabled && this.buttons.btnEdit.enabled) {
        return
      }
      event.preventDefault()
      this.$refs.fileUpload.click()
    },
    onFileChanged: function(event) {
      const file = event.target.files[0]
      if (file) {
        const reader = new FileReader()
        reader.onload = () => {
          const fullBase64 = reader.result
          this.current_item.avatar = fullBase64.split(',')[1]
          this.image_item = fullBase64
          this.buttons.btnAddImage = 'hidden'
          this.buttons.imgAvatar = 'img-fluid'
        }
        reader.readAsDataURL(file)
      }
    },
    onChangeIDC: async function() {
      try {
        this.$root.showLoading(true)
        this.current_item.hatangmang_id = null
        this.select_thuoc_hatang_mang = await this.apiListHaTangMangByIDCId(this.current_item.idc_id)
        this.select_nangluc_luutru_tudia = await this.apiListThieBiTuDiaByIDCId(this.current_item.idc_id)

        this.current_item.thietbi_tudia_id = null
        this.current_item.nangluc_luutru = []
      } finally {
        this.$root.showLoading(false)
      }
    },
    themThietBi: async function() {
      const values = []
      this.current_item.thietbi_tudia_id.forEach((value) => values.push(value))
      console.log('themThietBi: ', values)
      if (values.length > 0) {
        console.log(1)
        this.current_item.nangluc_luutru = this.select_nangluc_luutru_tudia.filter((obj) =>
          values.includes(obj.THIETBI_ID)
        )

        this.current_item.nangluc_luutru.forEach((obj, index) => {
          obj.sothutu = index + 1
          for (const key in obj) {
            const uppercaseKey = key.toLowerCase()
            if (uppercaseKey !== key) {
              obj[uppercaseKey] = obj[key]
            }
          }
        })
      } else {
        console.log(2)
        this.$toast.error('Danh sách năng lực tủ đĩa không được để trống.')
      }
      return
    },
    themThietBiHinhThanhKho: async function(loai_hatang) {
      if (this.current_item.idc_id && this.current_item.idc_id > 0) {
        let refs = this.$refs.grdItems_thietbi
        if (loai_hatang === 'SMARTCOUD_THIETBI') {
          refs.dialogOpen(
            loai_hatang,
            this.thietbi_daunoi_items.map((item) => item.thietbi_id)
          )
        }
      } else {
        this.$toast.error('Thuộc IDC không được để trống!')
        return
      }
    },
    xacNhanThemThietBi: function(data) {
      console.log('xacNhanThemThietBi: ', data)
      let loai_hatang = data.loai_hatang
      if (loai_hatang === 'SMARTCOUD_THIETBI') {
        data.data.forEach((item) => {
          this.thietbi_daunoi_items.push({
            id: item.ID,
            mathietbi: item.MATHIETBI,
            thietbi_id: item.THIETBI_ID,
            f_ngay_cn: item.F_NGAY_CN,
            nguoi_cn: this.user_info.nhanvien_ten
          })
        })
      }
      this.$refs.grdItems_thietbi.hideModal()
      return
    },
    grid_onCommandClicked_thietBi: function(name, dataItem) {
      if (!this.controls.text.enabled) {
        return
      }
      if (name === 'XOA') {
        console.log(12)
        this.thietbi_daunoi_items = this.thietbi_daunoi_items.filter(
          (item) => !(item.id === dataItem.id && item.thietbi_id === dataItem.thietbi_id)
        )
      }
    }
  }
}
</script>
<style>
.check-action {
  top: 3px;
}

.e-rowcell .check-action input[type='checkbox']:checked:disabled + .name:before {
  background: #fff;
  border-color: #0176ff;
  color: #0176ff;
}

.e-rowcell .check-action input[type='checkbox']:disabled + .name:before {
  background: #fff;
  border-color: #0176ff;
}
.e-grid * {
  font-size: 13px !important;
}
.e-grid.e-default.e-bothlines .e-headercell,
.e-grid.e-default.e-bothlines .e-detailheadercell {
  border-width: 1px 1px 0px 0px !important;
}
.info-row .key.w180 {
  width: 180px;
}
.info-row .key.w50 {
  width: 50px;
}
@media (min-width: 1200px) {
  .modal-xl {
    max-width: 80% !important;
  }
}
</style>
