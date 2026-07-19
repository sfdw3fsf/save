<template src="./IDG.html"></template>
<style src="./styles.scss" scoped></style>

<script>
import Vue from 'vue'
import api from './api.js'
import { Query } from '@syncfusion/ej2-data'
import DialogThietBi from '../ThietBiHinhThanhKhoHaTang/index.vue'

export default Vue.extend({
  components: {
    DialogThietBi
  },
  computed: {
    modalThietBiRef: function () {
      return this.$refs.grdItems_thietbi
    }
  },
  data() {
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
      thietbi_daunoi_items: [],
      thietbi_remove_ids: [],
      tsbtnThemMoi: true,
      tsbtnGhiLai: false,
      tsbtnHuyBo: false,
      tsbtnSua: false,
      tsbtnXoa: false,
      id: null,
      ma_hatang: {
        value: null,
        enable: true
      },
      ten_hatang: {
        value: null,
        enable: true
      },
      loai_hatang: {
        value: null,
        options: [{ ID: 1, TEN: 'Ảo hoá' }, { ID: 2, TEN: 'Cloud' }, { ID: 3, TEN: 'SmartCloud' }],
        enable: true
      },
      chkHieuLuc: 1,
      idc: {
        value: null,
        options: [],
        enable: true
      },
      congnghe_sudung: {
        value: null,
        options: [],
        enable: true
      },
      hatang_aohoa: {
        value: null,
        options: [],
        enable: true
      },
      dichvu: {
        value: null,
        options: [],
        enable: true
      },
      hatang_mang: {
        value: null,
        options: [],
        enable: true
      },
      link_quantri: {
        value: null,
        enable: true
      },
      ghi_chu: {
        value: null,
        enable: true
      },
      avatar: null,
      p_file_path: {
        value: null,
        enable: true
      },
      donviqly_id: null,
      donviqlyOptions: [],
      nguoiqly_id: null,
      nguoiqlyOptions: [],
      phongbanqly_id: null,
      phongbanqlyOptions: null,

      tong_cpu: {
        value: 0,
        enable: false
      },
      tong_ram: {
        value: 0,
        enable: false
      },
      tong_storage: {
        value: 0,
        enable: false
      },
      tong_pod: {
        value: 0,
        enable: false
      },

      khanang_cpu: {
        value: 0,
        enable: false
      },
      khanang_ram: {
        value: 0,
        enable: false
      },
      khanang_storage: {
        value: 0,
        enable: false
      },
      khanang_pod: {
        value: 0,
        enable: false
      },
      dacap_cpu: {
        value: 0,
        enable: false
      },
      dacap_ram: {
        value: 0,
        enable: false
      },
      dacap_storage: {
        value: 0,
        enable: false
      },
      dacap_pod: {
        value: 0,
        enable: false
      },
      dsThietBiDauNoi: [],
      dsLichSuThayDoi: [],
      dsHaTang: [],
      selectedRow: null
    }
  },
  created: async function () {
    try {
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
    } catch (error) {
      console.error('Error fetching user info:', error)
    }
  },
  mounted: async function () {
    // this.setButton(1)
    await this.loadDanhMuc()
    await this.loadDanhSach()
  },
  methods: {
    apiGetDonViCha: async function (pId) {
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
    apiListThietBiDauNoi: async function (id) {
      let result = []
      try {
        let rs = await this.$root.context.post('/web-ecms/hatang/thietbi-khohatang-idg/thiet-bi', {
          p_hatang_id: id
        })
        rs.data.forEach(item => {
          result.push({
            id: item.ID,
            thietbi_id: item.THIETBI_ID,
            mathietbi: item.MATHIETBI,
            ngay_cn: item.NGAY_CN,
            nhanvien_name: item.NHANVIEN_NAME
          })
        })
      } catch (e) {
        this.$toast.error(e.response.data.message_detail)
      }
      return result
    },
    async tsbtnThemMoi_Click() {
      this.setButton(1)
    },
    async tsbtnGhiLai_Click() {
      if (!this.validateInput()) {
        return
      }
      let f = await this.save(this.thietbi_daunoi_items)
      if (f) {
        if (this.id)
          this.$toast.success('Cập nhật dữ liệu thành công.')
        else
          this.$toast.success('Nhập mới dữ liệu thành công.')
        this.loadDanhSach()
      } else {
        // this.$toast.error('Đã phát sinh lỗi khi lưu dữ liệu.')
      }
    },
    async tsbtnHuyBo_Click() {
      this.setButton(0)
      let dsSelected = this.$refs.dsHaTang.$refs.grid.getSelectedRecords()
      if (dsSelected.length > 0) {
        this.setIDgInfo(dsSelected[0])
        this.tsbtnSua = true
        this.tsbtnXoa = true
      } else {
        this.tsbtnSua = false
        this.tsbtnXoa = false
      }
    },
    async tsbtnXoa_Click() {
      let dsSelected = this.$refs.dsHaTang.getSelectedRecords()
      console.log(dsSelected)
      if (dsSelected.length <= 0) {
        this.$toast.error('Chưa chọn dữ liệu cần xoá.')
        return
      }
      this.$confirm(
        'Bạn có muốn xoá dữ liệu đã chọn ?',
        "Kho hạ tầng IDG",
        {
          confirmButtonText: "Đồng ý",
          cancelButtonText: "Không",
        }
      )
        .then(async () => {
          await this.doDelete(dsSelected.map(x => x.ID))
        })
    },

    doDelete: async function (ds) {
      let promises = []
      for (let item of ds) {
        promises.push(await this.deleteItem(item))
      }
      let res = await Promise.all(promises)
      let ok = res.filter(x => x === true)

      // console.log('OK', ok)

      if (ok.length > 0) {
        this.$toast.success(`Đã xoá thành công ${ok.length} / ${res.length} mục`)
      } 
      await this.loadDanhSach();
    },

    async deleteItem(id) {
      try {
        const rs = await this.$root.context.post(
          `/web-ecms/khohatang-idg/delete-idg/` + id
        )
        if (rs?.data?.result)
          return true
        return false
      } catch (error) {
        this.$toast.error((error.response?.data?.message_detail || error.message))
        console.log(error)
        return false
      }
    },

    validateInput() {
      let errors = []
      if (!this.ma_hatang.value) {
        errors.push('Vui lòng nhập mã hạ tầng CĐS-ĐHIT.')
      }
      if (!this.ten_hatang.value) {
        errors.push('Vui lòng nhập tên hạ tầng.')
      }
      if (!this.idc.value) {
        errors.push('Vui lòng chọn IDC.')
      }
      if (!this.loai_hatang.value) {
        errors.push('Vui lòng chọn loại hạ tầng.')
      }
      if (!this.congnghe_sudung.value) {
        errors.push('Vui lòng chọn công nghệ sử dụng.')
      }
      /*
      if (this.tong_cpu === null) {
        errors.push('Vui lòng chọn nhập tổng CPU.')
      }
      if (this.tong_ram === null) {
        errors.push('Vui lòng chọn nhập tổng RAM.')
      }
      if (this.tong_storage === null) {
        errors.push('Vui lòng chọn nhập tổng Storage.')
      }
      if (this.tong_pod === null) {
        errors.push('Vui lòng chọn nhập tổng Pod.')
      }

      if (!this.khanang_cpu) {
        errors.push('Vui lòng chọn nhập khả năng cấp CPU.')
      }
      if (!this.khanang_ram) {
        errors.push('Vui lòng chọn nhập khả năng cấp RAM.')
      }
      if (!this.khanang_storage) {
        errors.push('Vui lòng chọn nhập khả năng cấp Storage.')
      }
      if (!this.khanang_pod) {
        errors.push('Vui lòng chọn nhập khả năng cấp Pod.')
      }

      if (!this.dacap_cpu) {
        errors.push('Vui lòng chọn nhập đã cấp CPU.')
      }
      if (!this.dacap_ram) {
        errors.push('Vui lòng chọn nhập đã cấp RAM.')
      }
      if (!this.dacap_storage) {
        errors.push('Vui lòng chọn nhập đã cấp Storage.')
      }
      if (!this.dacap_pod) {
        errors.push('Vui lòng chọn nhập đã cấp Pod.')
      }
      */

      if (this.khanang_cpu.value > this.tong_cpu.value) {
        errors.push('Khả năng CPU <= tổng CPU.')
      }
      if (this.khanang_ram.value > this.tong_ram.value) {
        errors.push('Khả năng RAM <= tổng RAM.')
      }
      if (this.khanang_storage.value > this.tong_storage.value) {
        errors.push('Khả năng storage <= tổng storage.')
      }

      if (errors.length > 0) {
        this.$toast.error(errors.join('\r\n'))
        return false
      }
      return true
    },

    async save(thietbi_list) {
      let postData = {
        id: this.id,
        mahatang_infra: this.ma_hatang.value,
        ten_hatang: this.ten_hatang.value,
        loai_hatang_id: this.loai_hatang.value,
        hieu_luc: this.chkHieuLuc ? 1 : 0,
        idc_id: this.idc.value,
        hatang_congnghe_id: this.congnghe_sudung.value,
        hatang_aohoa_id: this.hatang_aohoa.value,
        hatangmang_id: this.hatang_mang.value,
        spdv_id: this.dichvu.value,
        admin_link: this.link_quantri.value,
        ghi_chu: this.ghi_chu.value,
        avatar: this.avatar,
        donviqly_id: this.donviqly_id,
        nguoiqly_id: this.nguoiqly_id,
        phongbanqly_id: this.phongbanqly_id,
        cpu_total: this.tong_cpu.value,
        cpu_cap: this.dacap_cpu.value,
        cpu_nangluc: this.khanang_cpu.value,
        ram_total: this.tong_ram.value,
        ram_nangluc: this.khanang_ram.value,
        ram_cap: this.dacap_ram.value,
        storage_total: this.tong_storage.value,
        storage_nangluc: this.khanang_storage.value,
        storage_cap: this.dacap_storage.value,
      }
      thietbi_list = thietbi_list.filter(item => item.isNew == true)
      let dataThietBi = {
        thietBiHinhThanhList: thietbi_list.map((item) => (
          {
            id: this.id,
            thietbi_id: item.thietbi_id,
            ngay_cn: item.ngay_cn
          }
        ))
      }

      try {
        const res = await api.capnhat(this.axios, { p_ds: JSON.stringify(postData) })
        
        const data = await res.data

        if (data.error_code === 'BSS-00000000') {
          if (dataThietBi.thietBiHinhThanhList && dataThietBi.thietBiHinhThanhList.length > 0) {
            const rsTB = await this.$root.context.post(
              `/web-ecms/hatang/thietbi-khohatang-idg/add-thiet-bi`,
              dataThietBi
            )
          }
          if (this.thietbi_remove_ids && this.thietbi_remove_ids.length > 0) {
            this.thietbi_remove_ids.forEach(async (item) => {
              await this.$root.context.post(
                `/web-ecms/hatang/thietbi-khohatang-idg/delete-thiet-bi`,
                { p_id: item }
              )
            })
          }
          return true
        }
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

    async loadDanhSach() {
      try {
        const rs = await this.$root.context.get(
          `/web-ecms/khohatang-idg/get-list-khohatang-idg`
        );
        if (rs.data && rs.data.length > 0) {
          this.dsHaTang = rs.data
        } else {
          this.dsHaTang = []
        }

      } catch (error) {
      }
    },

    async loadDanhMuc() {
      try {
        const rs = await this.$root.context.post(
          `/web-ecms/danhmuc/tinhtrang-ip/get-danhmuc`, { "ds": "|IDC|CONGNGHE" }
        );
        if (rs.data && rs.data.length > 0) {
          let ds = rs.data
          this.idc.options = ds.filter(x => x.LOAI === 'IDC')
          this.congnghe_sudung.options = ds.filter(x => x.LOAI === 'CONGNGHE')
        } else {

        }

      } catch (error) {
      }
    },

    async onRecordClick(row) {
      // if (row && row.rowData) {
      //   this.selectedRow = row.rowData
      //   this.setIDgInfo(this.selectedRow)
      //   this.setButton(2)
      // }
    },

    async setIDgInfo(data) {
      if (data && !Array.isArray(data)) {
        this.id = data.ID

        let found = this.dsHaTang.find(x => x.ID === this.id)

        this.ghi_chu.value = found.GHI_CHU
        this.link_quantri.value = found.ADMIN_LINK
        this.chkHieuLuc = found.HIEU_LUC
        this.ma_hatang.value = found.MAHATANG_INFRA
        this.ten_hatang.value = found.TEN_HATANGIDG
        this.tong_cpu.value = found.VCPU_TOTAL ?? 0
        this.tong_ram.value = found.VRAM_TOTAL ?? 0
        this.tong_storage.value = found.VSTORAGE_TOTAL ?? 0
        this.khanang_cpu.value = found.VCPU_NANGLUC ?? 0
        this.khanang_ram.value = found.VRAM_NANGLUC ?? 0
        this.khanang_storage.value = found.VSTORAGE_NANGLUC ?? 0
        this.dacap_cpu.value = found.VCPU_CAP ?? 0
        this.dacap_ram.value = found.VRAM_CAP ?? 0
        this.dacap_storage.value = found.VSTORAGE_CAP ?? 0

        this.loai_hatang.value = found.LOAIHATANG_ID
        this.congnghe_sudung.value = found.HATANG_CONGNGHE_ID
        this.idc.value = Number(found.IDC_ID)
        
        // Load options cho hatang_aohoa trước khi set giá trị
        if (found.IDC_ID && found.LOAIHATANG_ID) {
          await this.loadHaTangAoHoa(found.IDC_ID, found.LOAIHATANG_ID)
          // Set giá trị sau khi đã load options
          this.hatang_aohoa.value = found.ID_HATANGAOHOA
          
          // Load SPDV và HaTang Mang nếu có hatang_aohoa
          if (found.ID_HATANGAOHOA) {
            await this.loadSPDV(found.ID_HATANGAOHOA)
            await this.loadHaTangMang(found.ID_HATANGAOHOA, found.LOAIHATANG_ID)
            
            // Set giá trị cho dichvu và hatang_mang sau khi load options
            this.dichvu.value = found.SPDV_ID
            this.hatang_mang.value = found.HATANGMANG_ID
          }
        }
        
        this.thietbi_daunoi_items = await this.apiListThietBiDauNoi(data.ID)
      } else {
        this.clearForm()
      }
      this.setButton(0)
    },

    onRowSelected(row) {
      this.setIDgInfo(row?.data)
    },

    onRowDeselected() {
      this.clearForm()
      this.enableControl(false)
    },

    enableControl(enable) {
      this.ma_hatang.enable = enable
      this.ten_hatang.enable = enable
      this.loai_hatang.enable = enable
      this.idc.enable = enable
      this.congnghe_sudung.enable = enable
      this.hatang_aohoa.enable = enable
      this.dichvu.enable = enable
      this.hatang_mang.enable = enable
      this.link_quantri.enable = enable
      this.ghi_chu.enable = enable
      this.p_file_path.enable = enable
    },

    onCheckBoxChange() {
      let dsSelected = this.$refs.dsHaTang.getSelectedRecords()
      if (dsSelected != null && dsSelected.length > 0) {
        this.enableCtrl.tsbtnXoa = true
      } else {
        this.enableCtrl.tsbtnXoa = false
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
          this.enableControl(false)
          break;
        case 1: // add
          this.tsbtnThemMoi = false
          this.tsbtnGhiLai = true
          this.tsbtnHuyBo = true
          this.tsbtnXoa = false
          this.tsbtnSua = false
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
          this.enableControl(true)
        // this.$refs.ten_vatlieu_ketnoi.focus()
      }
    },

    clearForm() {
      this.id = null
      this.ma_hatang.value = null
      this.ten_hatang.value = null
      this.loai_hatang.value = this.loai_hatang.options.length > 0 ? this.loai_hatang.options[0].ID : null
      this.chkHieuLuc = 1
      this.idc.value = this.idc.options.length > 0 ? this.idc.options[0].ID : null
      this.congnghe_sudung.value = this.congnghe_sudung.options.length > 0 ? this.congnghe_sudung.options[0].ID : null
      this.hatang_aohoa.value = this.hatang_aohoa.options.length > 0 ? this.hatang_aohoa.options[0].ID : null
      this.dichvu.value = this.dichvu.options.length > 0 ? this.dichvu.options[0].ID : null
      this.hatang_mang.value = this.hatang_mang.options.length > 0 ? this.hatang_mang.options[0].ID : null
      this.link_quantri.value = null
      this.ghi_chu.value = null

      this.tong_cpu.value = 0
      this.tong_ram.value = 0
      this.tong_storage.value = 0
      this.tong_pod.value = 0

      this.khanang_cpu.value = 0
      this.khanang_ram.value = 0
      this.khanang_storage.value = 0
      this.khanang_pod.value = 0

      this.dacap_cpu.value = 0
      this.dacap_ram.value = 0
      this.dacap_storage.value = 0
      this.dacap_pod.value = 0

      this.dsThietBiDauNoi = []
      this.dsLichSuThayDoi = []
    },

    async tsbtnThemMoi_Click() {
      this.setButton(1)
    },

    tsbtnEdit_Click() {
      this.setButton(2)
    },

    async onChangeLoaiHaTang(args) {
      if (args.value !== null && this.idc.value !== null) {
        await this.loadHaTangAoHoa(this.idc.value, args.value)
        
        // Chỉ set giá trị mặc định khi không có selectedRow (đang thêm mới)
        if (this.hatang_aohoa.options.length > 0 && this.selectedRow === null) {
          this.hatang_aohoa.value = this.hatang_aohoa.options[0].ID
        }
      }
    },


    async onChangeIDC(args) {
      this.hatang_aohoa.value = null
      this.hatang_aohoa.options = []
      this.hatang_mang.value = null
      this.hatang_mang.options = []
      this.dichvu.value = null
      this.dichvu.options = []

      if (args.value !== null && this.loai_hatang.value !== null) {
        await this.loadHaTangAoHoa(args.value, this.loai_hatang.value)
        
        // Chỉ set giá trị mặc định khi không có selectedRow (đang thêm mới)
        if (this.hatang_aohoa.options.length > 0 && this.selectedRow === null) {
          this.hatang_aohoa.value = this.hatang_aohoa.options[0].ID
        }
      }
    },

    async onChangeHaTangAoHoa(args) {
      this.dichvu.value = null
      this.dichvu.options = []
      this.hatang_mang.value = null
      this.hatang_mang.options = []
      if (args.value !== null) {
        await this.loadSPDV(args.value);
        await this.loadHaTangMang(args.value, this.loai_hatang.value)

        // console.log(this.dichvu.options, this.hatang_mang.options)
        if (this.dichvu.options.length > 0 && this.selectedRow) {
          this.dichvu.value = this.selectedRow.SPDV_ID
        }

        if (this.hatang_mang.options.length > 0 && this.selectedRow) {
          this.hatang_mang.value = this.selectedRow.HATANGMANG_ID
        }

      }
    },

    async loadHaTangAoHoa(idc_id, loai_hatang) {
      if (loai_hatang === 1) { // ảo hoá
        let postData = {
          "table": "HATANG_AOHOA",
          "conds": JSON.stringify({
            "idc_id": idc_id
          })
        }
        const rs = await this.$root.context.post(
          `/web-ecms/danhmuc/tinhtrang-ip/get-danhmuc-khac`, postData
        );
        if (rs.data && rs.data.length > 0) {
          this.hatang_aohoa.options = rs.data
        }
      } else if (loai_hatang === 3) { // smartcloud
        let postData = {
          "table": "HATANG_SMARTCLOUD",
          "conds": JSON.stringify({
            "idc_id": idc_id
          })
        }
        const rs = await this.$root.context.post(
          `/web-ecms/danhmuc/tinhtrang-ip/get-danhmuc-khac`, postData
        );
        if (rs.data && rs.data.length > 0) {
          this.hatang_aohoa.options = rs.data
        }
      } else if (loai_hatang === 2) { // cloud
        let postData = {
          "table": "HATANG_CLOUD",
          "conds": JSON.stringify({
            "idc_id": idc_id
          })
        }
        const rs = await this.$root.context.post(
          `/web-ecms/danhmuc/tinhtrang-ip/get-danhmuc-khac`, postData
        );
        if (rs.data && rs.data.length > 0) {
          this.hatang_aohoa.options = rs.data
        }
      }
    },

    async loadSPDV(hatangaohoa_id) {
      let postData = {
        "table": "HATANG_SPDV",
        "conds": JSON.stringify({
          "hatangaohoa_id": hatangaohoa_id
        })
      }
      const rs = await this.$root.context.post(
        `/web-ecms/danhmuc/tinhtrang-ip/get-danhmuc-khac`, postData
      );
      if (rs.data && rs.data.length > 0) {
        this.dichvu.options = rs.data
      }
    },
    async loadHaTangMang(hatangaohoa_id, loaihatang_id) {
      let postData = {
        "table": loaihatang_id === 1 ? 'HATANGMANG_AOHOA' : loaihatang_id === 3 ? 'HATANGMANG_SMARTCLOUD' : 'HATANGMANG_CLOUD',
        "conds": JSON.stringify({
          'hatangaohoa_id': hatangaohoa_id,
          'idc_id': this.idc.value
        })
      }
      const rs = await this.$root.context.post(
        `/web-ecms/danhmuc/tinhtrang-ip/get-danhmuc-khac`, postData
      );
      if (rs.data && rs.data.length > 0) {
        this.hatang_mang.options = rs.data
      }
    },

    async onHaTangMangChange(args) {
      this.tong_cpu.value = 0
      this.tong_ram.value = 0
      this.tong_storage.value = 0
      if (args.value) {
        let item = this.hatang_mang.options.find(x => x.ID === args.value)
        this.tong_cpu.value = item.VCPU
        this.tong_ram.value = item.VRAM
        this.tong_storage.value = item.VSTORAGE
      }
    },

    onFilteringIDC(e) {
      var query = new Query()
      query = (e.text !== '') ? query.where('TEN', 'contains', e.text, true) : query
      e.updateData(this.idc.options, query)
    },
    onFilteringLoaiHaTang(e) {
      var query = new Query()
      query = (e.text !== '') ? query.where('TEN', 'contains', e.text, true) : query
      e.updateData(this.loai_hatang.options, query)
    },
    onFilteringCongNghe(e) {
      var query = new Query()
      query = (e.text !== '') ? query.where('TEN', 'contains', e.text, true) : query
      e.updateData(this.congnghe_sudung.options, query)
    },
    onFilteringHaTangAoHoa(e) {
      var query = new Query()
      query = (e.text !== '') ? query.where('TEN', 'contains', e.text, true) : query
      e.updateData(this.hatang_aohoa.options, query)
    },
    onFilteringDichVu(e) {
      var query = new Query()
      query = (e.text !== '') ? query.where('TEN', 'contains', e.text, true) : query
      e.updateData(this.dichvu.options, query)
    },
    onFilteringHaTangMang(e) {
      var query = new Query()
      query = (e.text !== '') ? query.where('TEN', 'contains', e.text, true) : query
      e.updateData(this.hatang_mang.options, query)
    },
    onFilteringDonViQLy(e) {
      var query = new Query()
      query = (e.text !== '') ? query.where('TEN', 'contains', e.text, true) : query
      e.updateData(this.donviqlyOptions, query)
    },
    onFilteringNguoiQly(e) {
      var query = new Query()
      query = (e.text !== '') ? query.where('TEN', 'contains', e.text, true) : query
      e.updateData(this.nguoiqlyOptions, query)
    },
    onFilteringPhongBanQly(e) {
      var query = new Query()
      query = (e.text !== '') ? query.where('TEN', 'contains', e.text, true) : query
      e.updateData(this.phongbanqlyOptions, query)
    },
    browserFile: function () {
      document.getElementById('tb_uploadfile').click()
      return false
    },
    async uploadFile(e) {
      let files = e.target.files
      let f = files[0]
      if (!files.length) {
        return
      } else if (!/\.(jpg|jpeg)$/.test(files[0].name.toLowerCase())) {
        return this.$root.toastWarning('File jpg/jpeg không đúng định dạng!')
      }

      this.p_file_path.value = f.name
      let reader = new FileReader()
      const vm = this
      reader.onload = function (e) {

      }
    },
    openThietBiModal() {
      if (this.idc.value) {
        this.modalThietBiRef.dialogOpen(
          'IDG_THIETBI',
          this.thietbi_daunoi_items.map((item) => {
            return item.thietbi_id
          })
        )
      } else {
        this.$toast.error('Thuộc IDC không được để trống!')
      }
    },
    xacNhanThemThietBi: function (data) {
      data.data.forEach((item) => {
        this.thietbi_daunoi_items.push({
          id: item.THIETBI_ID,
          mathietbi: item.MATHIETBI,
          thietbi_id: item.THIETBI_ID,
          ngay_cn: item.F_NGAY_CN,
          nhanvien_name: this.user_info.nhanvien_ten,
          isNew: true
        })
      })
      this.modalThietBiRef.hideModal()
    },
    grid_onCommandClicked_thietbi(name, dataItem) {
      if (this.tsbtnThemMoi) return
      if (name == 'XOA') {
        this.thietbi_remove_ids.push(dataItem.id)
        this.thietbi_daunoi_items = this.thietbi_daunoi_items.filter(item => item.id !== dataItem.id)
      }
    }
  },
})
</script>
