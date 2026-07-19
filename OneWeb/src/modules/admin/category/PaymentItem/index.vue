<template src="./template.html"></template>
<script>
import Vue from 'vue'
import api from './API.js'
import { GridPlugin, Sort, Filter, Page, GridComponent } from '@syncfusion/ej2-vue-grids'
import DataTreeGrid from './components/DataTreeGrid'
import DataGridCustom from "./components/DataGrid";
Vue.use(GridPlugin)
import { Tooltip } from '@syncfusion/ej2-popups'
export default {
  components: {
    DataTreeGrid,
    DataGridCustom
  },
  data() {
    return {
      vatList: [],
      kieuKMList: [],
      KMChaList: [],
      stateCheckbox : 0,
      tsbtnNhapMoi: {
        Enabled: false
      },
      tsbtnXoa: {
        Enabled: false
      },
      tsbtnGhiLai: {
        Enabled: false
      },
      tsbtnHuyBo: {
        Enabled: false
      },
      cboNhomKM: {
        DataSource: [],
        Enabled: true,
        Visible: true
      },
      cboDichVuVT: {
        DataSource: [],
        Enabled: true,
        Visible: true
      },
      cboLoaiHD: {
        DataSource: [],
        Enabled: true,
        Visible: true
      },
      model: {
        donvi: null,
        loai_tn: {},
        nhanvien: {},
        nhanvien_tn: {},
        kieucb: '',
        chuagan: {},
        dagan: {}
      },
      // config: {
      //           grvChuaGan: [
      //             {
      //               label: 'Tên kiểu lắp đặt chưa gán',
      //               key: 'ten_kieuld',
      //               filterAble: true
      //             },
      //             {
      //               label: 'Sửa tiền KM',
      //               key: 'suatien',
      //               filterAble: false,
      //               multiple
      //             }
      //           ],
      // },
      pageOptions: { pageSize: 10, pageCount: 5 },
      selectOptions: { allowColumnSelection: false },
      grvDaGan: {
        DataSource: [],
        columns: [
          {
            fieldName: 'ten_kieuld',
            headerText: 'Tên kiểu lắp đặt đã gán',
            allowFiltering: true,
            allowSorting: true,
            width: '100'
          },
          {
            fieldName: "suatien",
            headerText: "Sửa tiền KM",
            width: '100',   
            allowSorting : false,
            allowFiltering: false,
            textAlign : "Center",
            // filterTemplate : this.getCheckboxHeaderTemplateCG(this),
            //headerTemplate : this.getCheckboxHeaderTemplateCG(this),         
            template: this.getCheckboxTemplate(this, 'flag', 'grvDaGan.DataSource'),
          },
        ]
      },
      grvChuaGan: {
        DataSource: [],
        columns: [
          {
            fieldName: 'ten_kieuld',
            headerText: 'Tên kiểu lắp đặt chưa gán',
            allowFiltering: true,
            allowSorting: true,
            width: '100'
          }
        ]
      },
      gridviewDaKhoa: {
        DataSource: [],
        columns: [
          {
            fieldName: 'loaihinh_tb',
            headerText: 'Loại hình đã gán',
            allowFiltering: true,
            allowSorting: true,
            width: '100'
          },
          {
            fieldName: 'ten_dvvt',
            headerText: 'Dịch vụ',
            allowFiltering: true,
            allowSorting: true,
            width: '100'
          }
        ]
      },
      gridviewChuaKhoa: {
        DataSource: [],
        columns: [
          {
            fieldName: 'loaihinh_tb',
            headerText: 'Loại hình chưa gán',
            allowFiltering: true,
            allowSorting: true,
            width: '100'
          },
          {
            fieldName: 'ten_dvvt',
            headerText: 'Dịch vụ',
            allowFiltering: true,
            allowSorting: true,
            width: '100'
          }
        ]
      },
      dsKMTT: {
        DataSource: [],
        columns: [
          {
            fieldName: 'khoanmuctt_id',
            headerText: 'ID',
            allowFiltering: true,
            allowSorting: true,
            width: '100'
          },
          {
            fieldName: 'ten_kmtt',
            headerText: 'Tên khoản mục',
            allowFiltering: true,
            allowSorting: true,
            width: '100'
          },
          {
            fieldName: 'tentat_kmtt',
            headerText: 'Tên tắt',
            allowFiltering: true,
            allowSorting: true,
            width: '100'
          },
          {
            headerText: 'Tỉ lệ vat',
            allowFiltering: true,
            allowSorting: true,
            width: '100',
            template: this.tiLeVatTemplate(this)
          },
          {
            headerText: 'Kiểu KM',
            allowfiltering: true,
            allowSorting: true,
            width: '100',
            template: this.kieuKMTemplate(this)
          }
        ]
      },
      loadingtable: {
        dsKMTT: false,
        nhanvien_dg: false,
        grvChuaGan: false,
        dg: false
      },
      txtTenKM: '',
      txtMaKM: '',
      txtGhiChu: '',
      p_tyle_vat_id: null,
      p_tyle_vat: null,
      txtTentat_KMTT: '',
      p_danhdau: 1,
      khoanmuctt_id: 0,
      khoanmuctt_cha_id: null,
      kt_load: false,
      settingsKmCha: {
        allowClear: true
      }
      // validations:{
      //   txtMaKM:{
      //     required
      //   }
      // }
    }
  },
  async mounted() {
    await this.HT_NhomKM_Combobox()
    this.HT_DichVuVT_Combobox()
    this.HT_Loai_HD_Combobox()
    

    this.vatList = (await api.lay_ds_tylevat(this.axios)).data.data
    this.kieuKMList = [
      {
        text: 'Cho phép tính tiền vào HĐ',
        value: '1'
      },
      { text: 'Không tính tiền vào HĐ', value: '2' }
    ]
    await this.Lay_DS()
    this.SetButton(-1)
    // console.log('this.dsKMTT.DataSource')
    // console.log(this.dsKMTT.DataSource)
    // console.log('this.cboNhomKM.DataSource')
    // console.log(this.cboNhomKM.DataSource)
    // console.log("this.KhoanMucList")
    // console.log(this.KhoanMucList)
  },
  provide: {
    grid: [Filter, Sort, Page]
  },
  methods: {
    tiLeVatTemplate(parent) {
      return function() {
        return {
          template: {
            template: `<div>{{vatTxt }}</div>`,

            data() {
              return {
                data: {}
              }
            },
            computed: {
              vatTxt() {
                return parent.vatList.find((e) => e.tyle_vat_id == this.data.tyle_vat_id).tyle_vat_loai
              }
            },
          }
        }
      }
    },
    kieuKMTemplate(parent) {
      return function() {
        return {
          template: {
            template: `<div>{{kieuKMTxt}}</div>`,

            data() {
              return {
                data: {}
              }
            },
            computed: {
              kieuKMTxt() {
                return parent.kieuKMList.find((e) => e.value == this.data.danhdau).text
              }
            },
          }
        }
      }
    },
    onTaxChanged(args) {
      console.log('onTaxChanged')
    },
    onTooltipClose(e) {
      this.tooltip.close()
    },
    //   handleSubmit(e) {

    // this.$v.$touch();
    //       if (this.$v.$invalid) {
    //             return;
    //       }
    //       this.$toast.warning('Bạn chưa nhập “Tên bảng giá”!','Cảnh báo')

    // },
    async KT_DuLieuNhap() {
      if (this.txtMaKM == '') {
        this.$root.$toast.warning('Bạn chưa nhập “Mã KM”!')
        return false
      }
      if (this.txtTenKM == '') {
        this.$root.$toast.warning('Bạn chưa nhập “Tên KM”!')
        return false
      }
      if (this.txtTentat_KMTT == '') {
        this.$root.$toast.warning('Bạn chưa nhập “Tên tắt”!')
        return false
      }
      if (this.txtTentat_KMTT == '') {
        this.$root.$toast.warning('Bạn chưa nhập “Tên tắt”!')
        return false
      }
      if (this.txtGhiChu == '' || this.txtGhiChu == null) {
        this.$root.$toast.warning('Bạn chưa nhập “Ghi chú”!')
        return false
      }
      if (this.p_tyle_vat_id == '') {
        this.$root.$toast.warning('Bạn chưa nhập “Thuế (%)”!')
        return false
      }
      if (isNaN(this.p_tyle_vat_id)) {
        this.$root.$toast.warning('Thuế phải là số!')
        return false
      }
      return true
    },
    async HT_NhomKM_Combobox() {
      try {
        let data = await this.axios.get('web-quantri/danhmuc-chung/CSS_NHOM_KM')
        this.cboNhomKM.DataSource = data.data.data
      } catch (err) {
        this.cboNhomKM.DataSource = []
      }
    },
    async HT_DichVuVT_Combobox() {
      try {
        let data = await this.axios.get('web-quantri/danhmuc-chung/CSS_DICHVU_VT')
        this.cboDichVuVT.DataSource = data.data.data
      } catch (err) {
        this.cboDichVuVT.DataSource = []
      }
    },
    async HT_Loai_HD_Combobox() {
      try {
        let data = await this.axios.get('web-quantri/danhmuc-chung/CSS_LOAI_HD')
        this.cboLoaiHD.DataSource = data.data.data
      } catch (err) {
        this.cboLoaiHD.DataSource = []
      }
    },

    async Lay_DS() {
      try {
        let data = await api.sp_lay_ds_khoanmuc_tt(this.axios)

        var originalKmList = data.data.data
        var outputKmList = originalKmList.map((e) => {
          e.children = originalKmList.filter((x) => x.khoanmuctt_cha_id == e.khoanmuctt_id)
          return e
        })
        this.khoanMucList = outputKmList.filter((e) => e.khoanmuctt_cha_id == null)

        // this.dsKMTT.DataSource = data.data.data

        this.dsKMTT.DataSource = this.khoanMucList
        this.khoanMucList.map((e) => {
          this.KMChaList.push(e)
        })
        console.log('test',this.dsKMTT.DataSource.filter(x=>x.children.length>0)  )

        this.KMChaList = this.KMChaList.filter((e) => e.khoanmuctt_id != this.khoanmuctt_id)

        this.kt_load = true
      } catch (err) {
        this.dsKMTT.DataSource = []
      }
    },
    onRowSelected(item) {
      console.log('onRowSelected')
      console.log(item)
    },
    dsKMTT_focus(item) {
      console.log('dsKMTT_focus')
      let dsKMTT = this.$refs.dsKMTT.getSelectedRecords()

      if (dsKMTT.length <= 0) {
        this.txtTenKM = ''
        this.txtMaKM = ''
        this.txtGhiChu = ''
        this.khoanmuctt_id = 0
      } else {
        let item = dsKMTT[0]
        this.khoanmuctt_id = item.khoanmuctt_id
        this.txtTenKM = item.ten_kmtt
        this.txtMaKM = item.ma_kmtt
        this.cboNhomKM.Value = item.nhomkm_id
        this.txtGhiChu = item.ghichu
        this.txtTentat_KMTT = item.tentat_kmtt
        this.p_tyle_vat_id = item.tyle_vat_id
        this.p_danhdau = item.danhdau
        this.khoanmuctt_cha_id = item.khoanmuctt_cha_id

        this.HienThiDanhSach_KieuLD_Dagan()
        this.HienThiDanhSach_KieuLD_Chuagan()
        this.HienThiDanhSach_LHTB_Dagan()
        this.HienThiDanhSach_LHTB_Chuagan()

        this.SetButton(3)

        //remove item trong list KMCHA
        this.KMChaList = []
        this.khoanMucList.map((e) => {
          this.KMChaList.push(e)
        })

        this.KMChaList = this.KMChaList.filter((e) => e.khoanmuctt_id != this.khoanmuctt_id)
      }
    },
    SetButton(kieu) {
      this.tsbtnNhapMoi.Enabled = false
      this.tsbtnGhiLai.Enabled = false
      this.tsbtnXoa.Enabled = false
      this.tsbtnHuyBo.Enabled = false

      if (kieu == -1) {
        //Bat dau
        this.tsbtnGhiLai.Enabled = true
        this.tsbtnHuyBo.Enabled = true
      }

      if (kieu == 0) {
        //Bat dau
        this.tsbtnNhapMoi.Enabled = true
        this.Clear()
      }

      if (kieu == 1) {
        //Them moi
        this.tsbtnGhiLai.Enabled = true
        this.tsbtnHuyBo.Enabled = true
        this.Clear()
      }

      if (kieu == 2) {
        //Huy
        this.tsbtnNhapMoi.Enabled = true
        this.tsbtnXoa.Enabled = true
        this.Clear()
      }

      if (kieu == 3) {
        //Edit
        this.tsbtnNhapMoi.Enabled = true
        this.tsbtnXoa.Enabled = true
        this.tsbtnGhiLai.Enabled = true
        this.tsbtnHuyBo.Enabled = true
      }
    },
    async CapNhat() {
      let check = await this.KT_DuLieuNhap()
      if (!check) {
        return
      }
      let arrKieuld = []
      let arrLoaitb = []
      this.p_tyle_vat = this.vatList.find((e) => e.tyle_vat_id == this.p_tyle_vat_id ).tyle_vat
      // if(this.tsbtnNhapMoi.Enabled) {
      this.grvDaGan.DataSource.forEach((item) => {
        arrKieuld.push({
          flag: item.flag,
          kieuld_id: item.kieuld_id
        })
      })
      this.gridviewDaKhoa.DataSource.forEach((item) => {
        arrLoaitb.push({
          flag: item.flag,
          loaitb_id: item.loaitb_id
        })
      })
      // }
      let data = await api.sp_khoanmuc_tt_capnhat_v2(this.axios, {
        p_ds_kieuld: arrKieuld,
        p_ds_loaitb: arrLoaitb,
        p_ghi_chu: this.txtGhiChu,
        p_khoanmuctt_id: this.khoanmuctt_id ? this.khoanmuctt_id : null, // null thì là thêm mới, !null là cập nhật
        p_ma_kmtt: this.txtMaKM,
        p_nhomkm_id: this.cboNhomKM.Value,
        p_ten_kmtt: this.txtTenKM,
        p_tentat_kmtt: this.txtTentat_KMTT,
        p_tyle_vat_id: this.p_tyle_vat_id,
        p_tyle_vat: this.p_tyle_vat,
        p_danhdau: this.p_danhdau,
        p_khoanmuctt_cha_id: this.khoanmuctt_cha_id
      })
      // let data = await this.axios.post('web-quantri/khoanmuctt/sp_khoanmuc_tt_capnhat', {
      //   p_ds_kieuld: arrKieuld,
      //   p_ds_loaitb: arrLoaitb,
      //   p_ghi_chu: this.txtGhiChu,
      //   p_khoanmuctt_id: this.khoanmuctt_id ? this.khoanmuctt_id : null, // null thì là thêm mới, !null là cập nhật
      //   p_ma_kmtt: this.txtMaKM,
      //   p_nhomkm_id: this.cboNhomKM.Value,
      //   p_ten_kmtt: this.txtTenKM,
      //   p_tentat_kmtt: this.txtTentat_KMTT,
      //   p_tyle_vat_id: this.p_tyle_vat_id,
      //   p_danhdau: 1
      // })
      if (data) {
        this.$root.$toast.success('Cập nhật thành công')
      } else this.$root.$toast.success('Cập nhật thất bại')
      this.Lay_DS()
      this.SetButton(3)
    },
    tsbtnHuyBo_Click() {
      this.SetButton(2)
    },
    tsbtnNhapMoi_Click() {
      this.SetButton(1)
    },
    async Xoa() {
      this.$bvModal
        .msgBoxConfirm('Bạn thật sự muốn xóa dữ liệu không?', {
          title: '',
          size: 'sm',
          okTitle: 'Đồng ý',
          cancelTitle: 'Hủy'
        })
        .then(async (v) => {
          if (!v) {
            return
          }
          try {
            let data = await this.axios.post('web-quantri/khoanmuctt/sp_khoanmuc_tt_xoa', {
              khoanmuctt_id: this.khoanmuctt_id
            })
            //this.$toast.success("Xóa")
            this.Lay_DS()
          } catch (err) {}
        })
    },
    Clear() {
      this.txtTenKM = ''
      this.txtMaKM = ''
      this.txtGhiChu = ''
      this.khoanmuctt_id = 0
      this.txtTentat_KMTT = ''
      this.grvDaGan.DataSource = []
      this.gridviewDaKhoa.DataSource = []
      this.p_tyle_vat_id = '10'
      this.cboDichVuVT.Value = null
      this.cboLoaiHD.Value = null
      this.grvChuaGan.DataSource = []
      this.gridviewChuaKhoa.DataSource = []
    },
    cboDichVuVT_change() {
      this.HienThiDanhSach_KieuLD_Chuagan()
      this.HienThiDanhSach_LHTB_Chuagan()
    },
    cboLoaiHD_change() {
      this.HienThiDanhSach_KieuLD_Chuagan()
    },
    async HienThiDanhSach_KieuLD_Chuagan() {
      try {
        let data = await this.axios.post('web-quantri/khoanmuctt/sp_lay_ds_kieu_ld', {
          loaihd_id: this.cboLoaiHD.Value ? this.cboLoaiHD.Value : 0,
          dichvuvt_id: this.cboDichVuVT.Value ? this.cboDichVuVT.Value : 0,
          khoanmuctt_id: this.khoanmuctt_id,
          kieu: 0
        })
        this.grvChuaGan.DataSource = data.data.data
      } catch (err) {
        this.grvChuaGan.DataSource = []
      }
    },
    async HienThiDanhSach_KieuLD_Dagan() {
      try {
        let data = await this.axios.post('web-quantri/khoanmuctt/sp_lay_ds_kieu_ld', {
          loaihd_id: this.cboLoaiHD.Value ? this.cboLoaiHD.Value : 0,
          dichvuvt_id: this.cboDichVuVT.Value ? this.cboDichVuVT.Value : 0,
          khoanmuctt_id: this.khoanmuctt_id,
          kieu: 1
        })
        this.grvDaGan.DataSource = data.data.data
      } catch (err) {
        this.grvDaGan.DataSource = []
      }
    },
    async HienThiDanhSach_LHTB_Dagan() {
      try {
        let data = await this.axios.post('web-quantri/khoanmuctt/sp_lay_ds_loaihinh', {
          dichvuvt_id: this.cboDichVuVT.Value ? this.cboDichVuVT.Value : 0,
          khoanmuctt_id: this.khoanmuctt_id,
          kieu: 1
        })
        this.gridviewDaKhoa.DataSource = data.data.data
      } catch (err) {
        this.gridviewDaKhoa.DataSource = []
      }
    },
    async HienThiDanhSach_LHTB_Chuagan() {
      try {
        let data = await this.axios.post('web-quantri/khoanmuctt/sp_lay_ds_loaihinh', {
          dichvuvt_id: this.cboDichVuVT.Value ? this.cboDichVuVT.Value : 0,
          khoanmuctt_id: this.khoanmuctt_id,
          kieu: 0
        })
        this.gridviewChuaKhoa.DataSource = data.data.data
      } catch (err) {
        this.gridviewChuaKhoa.DataSource = []
      }
    },
    Next() {
      let selected = this.$refs.grvDaGan.getSelectedRecords()
      if(!this.$refs.suatien.checked)
        selected.forEach((item) => {
          item.flag = 0;
      })
      this.grvChuaGan.DataSource = this.addArrayElement(this.grvChuaGan.DataSource, selected)
      this.grvDaGan.DataSource = this.removeArrayElement(this.grvDaGan.DataSource, selected)
      this.grvChuaGan.DataSource = [...this.grvChuaGan.DataSource]
      this.grvDaGan.DataSource = [...this.grvDaGan.DataSource]
    },
    Last() {
      if(!this.$refs.suatien.checked)
        this.grvDaGan.DataSource.forEach((item) => {
          item.flag = 0;
      })
      this.grvChuaGan.DataSource = this.addArrayElement(this.grvChuaGan.DataSource, this.grvDaGan.DataSource)
      this.grvDaGan.DataSource = []
      this.grvChuaGan.DataSource = [...this.grvChuaGan.DataSource]
      this.grvDaGan.DataSource = [...this.grvDaGan.DataSource]
    },
    Previous() {
      let selected = this.$refs.grvChuaGan.getSelectedRecords()
      if(this.$refs.suatien.checked)
        selected.forEach((item) => {
          item.flag = 1;
      })
      this.grvDaGan.DataSource = this.addArrayElement(this.grvDaGan.DataSource, selected)
      this.grvChuaGan.DataSource = this.removeArrayElement(this.grvChuaGan.DataSource, selected)
      this.grvChuaGan.DataSource = [...this.grvChuaGan.DataSource]
      this.grvDaGan.DataSource = [...this.grvDaGan.DataSource]
    },
    First() {
      if(this.$refs.suatien.checked)
        this.grvChuaGan.DataSource.forEach((item) => {
          item.flag = 1;
      })
      this.grvDaGan.DataSource = this.addArrayElement(this.grvDaGan.DataSource, this.grvChuaGan.DataSource)
      this.grvChuaGan.DataSource = []
      this.grvChuaGan.DataSource = [...this.grvChuaGan.DataSource]
      this.grvDaGan.DataSource = [...this.grvDaGan.DataSource]
    },
    btNext() {
      let selected = this.$refs.gridviewDaKhoa.getSelectedRecords()
      this.gridviewChuaKhoa.DataSource = this.addArrayElement(this.gridviewChuaKhoa.DataSource, selected)
      this.gridviewDaKhoa.DataSource = this.removeArrayElement(this.gridviewDaKhoa.DataSource, selected)
      this.gridviewDaKhoa.DataSource = [...this.gridviewDaKhoa.DataSource]
      this.gridviewChuaKhoa.DataSource = [...this.gridviewChuaKhoa.DataSource]
    },
    btLast() {
      this.gridviewChuaKhoa.DataSource = this.addArrayElement(this.gridviewChuaKhoa.DataSource, this.gridviewDaKhoa.DataSource)
      this.gridviewDaKhoa.DataSource = []
      this.gridviewDaKhoa.DataSource = [...this.gridviewDaKhoa.DataSource]
      this.gridviewChuaKhoa.DataSource = [...this.gridviewChuaKhoa.DataSource]
    },
    btPrevious() {
      let selected = this.$refs.gridviewChuaKhoa.getSelectedRecords()
      this.gridviewDaKhoa.DataSource = this.addArrayElement(this.gridviewDaKhoa.DataSource, selected)
      this.gridviewChuaKhoa.DataSource = this.removeArrayElement(this.gridviewChuaKhoa.DataSource, selected)
      this.gridviewDaKhoa.DataSource = [...this.gridviewDaKhoa.DataSource]
      this.gridviewChuaKhoa.DataSource = [...this.gridviewChuaKhoa.DataSource]
    },
    btFirst() {
      this.gridviewDaKhoa.DataSource = this.addArrayElement(this.gridviewChuaKhoa.DataSource, this.gridviewDaKhoa.DataSource)
      this.gridviewChuaKhoa.DataSource = []
      this.gridviewDaKhoa.DataSource = [...this.gridviewDaKhoa.DataSource]
      this.gridviewChuaKhoa.DataSource = [...this.gridviewChuaKhoa.DataSource]
    },
    gridviewDaKhoa_focus() {},
    gridviewChuaKhoa_focus() {},
    grvDaGan_focus() {},
    grvChuaGan_focus() {},
    addArrayElement(arr, items) {
      return items.concat(arr)
    },
    removeArrayElement(arr, items) {
      items.forEach((item) => {
        let index = arr.indexOf(item)
        if (index >= 0) {
          arr.splice(index, 1)
        }
      })

      return arr
    },
    getCheckboxTemplate(parent, field, grid) {
      return function () {
        return {
          template: {
            template: `<div class="template_checkbox"> <input type="checkbox" 
            @change="change" :checked="data[field] == 1"> </div>`,
            data() {
              return {
                field: field
              };
            },
            // computed: {
            //   vatTxt() {
            //     return parent.vatList.find((e) => e.tyle_vat_id == this.data.tyle_vat_id).tyle_vat_loai
            //   }
            // },
            methods: {
              change(event) {
                parent.$refs[grid].dataSource[this.data.index][field] = event.target.checked ? "1" : "0"
              }
            }
          },
        };
      };
    },
    checkAll(){
      this.grvDaGan.DataSource.forEach((item) => {
        item.flag = 1;
      })
      this.grvDaGan.DataSource = [...this.grvDaGan.DataSource]
    }
  }
}
</script>
<style scoped>
.non-ative a {
  color: #d3d3d3 !important;
}
.ejs-grid td.e-active {
  background-color: #887f6c;
}
</style>
