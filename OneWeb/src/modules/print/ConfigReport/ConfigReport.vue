<style scoped src="./ConfigReport.scss"></style>
<template src="./ConfigReport.html"></template>

<script>
import DanhSachTinh from './DanhSachTinh'
import DanhSachHuongGiao from './DanhSachHuongGiao'
import breadcrumb from "@/components/breadcrumb";
import ConfigReportAPI from "./ConfigReportAPI"
import NghiepVu from './constant'
import {NHOM_QT} from "../../../const/enums";
import LOAI_CT from "../LoaiCT";

export default {
  name: "ConfigReport",
  components: {
    breadcrumb,
    DanhSachTinh,
    DanhSachHuongGiao
  },
  data() {
    return {
      header: {
        title: 'BÁO CÁO',
        list: [
          {
            name: 'Quản trị hệ thống',
            link: {name: 'Ui.cards'},
            active: false
          },
          {
            name: 'Báo cáo động',
            link: {name: 'Ui.buttons'},
            active: true
          }
        ]
      },
      actions: [
        {
          id: 'tsbtnNhapMoi',
          name: 'Nhập mới',
          active: true,
          icon_class: 'one-file-plus'
        },
        {
          id: 'tsbtnGhiLai',
          name: 'Ghi lại',
          active: true,
          icon_class: 'one-save'
        },
        {
          id: 'tsbtnXoa',
          name: 'Xóa',
          active: true,
          icon_class: 'one-trash'
        },
        {
          id: 'tsbtnHuyBo',
          name: 'Hủy',
          active: true,
          icon_class: 'nc-icon-glyph ui-1_circle-remove'
        },
        {
          id: 'tsbtnDesign',
          name: 'Thiết kế',
          active: true,
          icon_class: 'one-design'
        },
        {
          id: 'tsbtnCoppyBC',
          name: 'Copy',
          active: true,
          icon_class: 'one-copy'
        },
        {
          id: 'tsbtnSapXepSTT',
          name: 'Sắp xếp STT',
          active: true,
          icon_class: 'one-td-arrow-change'
        }
      ],
      settingSelect2: {
        language: 'vi'
      },
      nhanvienId: this.$root.token.getNhanVienID(),
      loai_ct_id: 1,
      baocao_id: 0,
      ten_bc: '',
      trangthai: '',
      nhom_bc_id: 1,
      caulenh: '',
      dsList: [],
      dsBaoCao: [],
      dsBaoCao_hg: [],
      dsBaoCao_pv: [],
      dsBaoCao_Tinh: [],
      kt_load: true,
      cboLoaiCT: {
        list: [],
        disable: false,
        value: 0
      },
      cboNhomBC: {
        list: [],
        disable: false,
        value: 0
      },
      cboNhomQT: {
        list: [],
        disable: false,
        value: 0
      },
      cboLoaiHD: {
        list: [],
        disable: false,
        value: 0
      },
      cboQuyTrinh: {
        list: [],
        disable: false,
        value: 0
      },
      chkBCXuatExcel: {
        checked: false,
        disable: false
      },
      chkTrangThai: {
        checked: false,
        disable: false
      },
      chkNhomQT: {
        checked: false,
        disable: false
      },
      frmProps: {
        txtTenBC: '',
        txtThuTu: '',
        txtMaBC: '',
        txtCauLenh: '',
        txtGhiChu: '',
        txtThuTuc: ''
      },
      dsTinhDaGan: [],
      dsTinhChuaGan: [],
      gridviewBaoCao: {
        data: [],
        selectItem: null,
        headers: [
          {
            fieldName: 'baocao_id',
            headerText: 'ID',
            allowFiltering: true,
            allowSorting: false,
            textAlign: 'center',
            width: 90
          },
          {
            fieldName: 'ten_bc',
            headerText: 'Tên báo cáo',
            allowFiltering: true,
            allowSorting: false
          },
          {
            fieldName: 'ten_loaihd',
            headerText: 'Loại hợp đồng',
            allowFiltering: true,
            allowSorting: false
          },
          {
            fieldName: 'caulenh',
            headerText: 'Đường dẫn',
            allowFiltering: true,
            allowSorting: false
          },
          {
            fieldName: 'thutuc',
            headerText: 'Thủ tục',
            allowFiltering: true,
            allowSorting: false
          },
          {
            fieldName: 'xuat_excel',
            headerText: 'BC Xuất Excel',
            allowFiltering: true,
            allowSorting: false
          },
          {
            fieldName: 'ma_bc',
            headerText: 'Mã báo cáo',
            allowFiltering: true,
            allowSorting: false,
            textAlign: 'center',
            width: 100
          },
          {
            fieldName: 'thutu',
            headerText: 'Thứ tự',
            allowFiltering: true,
            allowSorting: false,
            textAlign: 'center'
          }
        ]
      }
    }
  },
  mounted() {
    this.initData()
  },
  watch: {
    'cboLoaiCT.value': function (value) {
      this.cboLoaiCTSelectedChange(value)
    },
    'cboNhomBC.value': function (value) {
      this.cboNhomBCSeletedValueChange(value)
    },
    'cboNhomQT.value': function (value) {
      this.cboNhomQTSelectedChange(value)
    },
    'cboQuyTrinh.value': function (value) {
      this.cboQuyTrinhSelectedValueChange(value)
    },
    'cboLoaiHD.value': function (value) {
      this.cboLoaiHD_EditValueChanged(value)
    }
  },
  methods: {
    async initData() {
      try {
        this.loading(true)
        // cboLoaiCT
        let dataLoaiCT = await this.fetchData(NghiepVu.LOAI_CT, JSON.stringify({NHANVIEN_ID: this.nhanvienId}))
        if (dataLoaiCT.length > 0) {
          this.cboLoaiCT.list = dataLoaiCT.map(item => {
            return {
              id: item.loai_ct_id,
              text: item.tenloai_ct
            }
          })
          this.cboLoaiCT.value = dataLoaiCT[0].loai_ct_id
        } else {
          this.cboLoaiCT = []
          this.cboLoaiCT.value = 0
        }
        // cboLoaiHD
        let dataLoaiHD = await this.fetchData(NghiepVu.LOAI_HD, '')
        if (dataLoaiHD.length > 0) {
          this.cboLoaiHD.list = dataLoaiHD.map(item => {
            return {
              id: item.loaihd_id,
              text: item.ten_loaihd
            }
          })
          this.cboLoaiHD.value = 0
        } else {
          this.cboLoaiHD = []
          this.cboLoaiHD.value = 0
        }
        // cboNhomQT
        let datacboNhomQT = await this.fetchData(NghiepVu.NHOM_QT, '')
        if (datacboNhomQT.length > 0) {
          this.cboNhomQT.list = datacboNhomQT.map(item => {
            return {
              id: item.nhom_qt_id,
              text: item.ten_nhom
            }
          })
          this.cboNhomQT.value = datacboNhomQT[0].nhom_qt_id
        } else {
          this.cboNhomQT = []
          this.cboNhomQT.value = 0
        }
        await this.fetchBaoCao()
        this.kt_load = false
      } catch
        (error) {
        console.log(error)
        this.$root.$toast.error('Lỗi tải dữ liệu danh mục: ' + error.message)
      } finally {
        this.loading(false)
      }
    },
    async cboLoaiHD_EditValueChanged(value) {
      if (this.cboNhomQT.value === NHOM_QT.DIEUHANH_THICONG) {
        await this.LoadComboboxQuyTrinh()
        if (!this.kt_load) {
          await this.HienThiDanhSach_HG_ChuaGan()
        }
      }
    },
    async cboQuyTrinhSelectedValueChange(value) {
      if (value > 0) {
        if (!this.kt_load) {
          await this.HienThiDanhSach_HG_ChuaGan()
        }
      }
    },
    async cboNhomBCSeletedValueChange(value) {
      if (value > 0) {
        this.nhom_bc_id = value
        await this.fetchBaoCao()
      }
    },
    async fetchBaoCao() {
      let dataBc = await this.fetchData(NghiepVu.BAOCAO, JSON.stringify({'NHOM_BC_ID': this.nhom_bc_id}))
      if (dataBc.length > 0) {
        this.gridviewBaoCao.data = dataBc
      } else {
        this.gridviewBaoCao.data = []
        this.gridviewBaoCao.selectItem = null
        this.setActiveActions(0)
      }
    },
    async cboNhomQTSelectedChange(value) {
      if (value > 0) {
        await this.HienThiDanhSach_HG_ChuaGan()
      }
    },
    async LoadComboboxQuyTrinh() {
      let dsTinh = this.dsTinhDaGan.map(item => item.tinh_id).join(',')
      let dataQT = await this.fetchData(NghiepVu.QUYTRINH, JSON.stringify({
        "NHOM_QT_ID": this.cboNhomQT.value,
        'LOAIHD_ID': this.cboLoaiHD.value,
        'DS_TINH': dsTinh
      }))
      if (dataQT.length > 0) {
        this.cboQuyTrinh.list = dataQT.map(item => {
          return {
            id: item.quytrinh_id,
            text: item.quytrinh
          }
        })
        this.cboQuyTrinh.value = this.cboQuyTrinh.list[0].id
      } else {
        this.cboQuyTrinh.list = []
        this.cboQuyTrinh.value = 0
      }
    },
    async cboLoaiCTSelectedChange(value) {
      if (value > 0) {
        this.loai_ct_id = value
        let dataNhomBc = await this.fetchData(NghiepVu.NHOM_BC, JSON.stringify({"LOAI_CT_ID": value}))
        if (dataNhomBc.length > 0) {
          this.cboNhomBC.list = dataNhomBc.map(item => {
            return {
              id: item.nhom_bc_id,
              text: item.ten_nhom
            }
          })
          this.cboNhomBC.value = this.cboNhomBC.list[0].id
        } else {
          this.cboNhomBC.list = []
          this.cboNhomBC.value = 0
        }
      }
    },
    async fetchData(nghiepVu, params) {
      return this.GetDataList(await ConfigReportAPI.sp_thietke_baocao_danhmuc(this.axios, {
        "p_nghiepvu": nghiepVu,
        "p_ds_para": params
      }))
    },
    selectRowsChon(args) {
      if (this.gridviewBaoCao.data.length > 0) {
        if (this.gridviewBaoCao.selectItem != null) {
          let index = this.gridviewBaoCao.data.findIndex(item => item.baocao_id == this.gridviewBaoCao.selectItem.baocao_id)
          if (index >= 0) {
            this.$refs.gridviewBaoCao.selectRow(index, true)
          } else {
            this.$refs.gridviewBaoCao.selectRow(0, true)
          }
        } else {
          this.$refs.gridviewBaoCao.selectRow(0, true)
        }
      }
    },
    async gridviewBaoCao_SelectedItemChanged(item) {
      try {
        if (item) {
          console.log('item ', item)
          this.loading(true)
          this.baocao_id = item.baocao_id
          this.gridviewBaoCao.selectItem = item
          await this.HienThiDanhSachTinh()
          this.frmProps.txtTenBC = item.ten_bc
          this.frmProps.txtCauLenh = item.caulenh
          this.frmProps.txtGhiChu = item.ghichu
          this.chkTrangThai.checked = item.trangthai == 1
          this.frmProps.txtThuTu = item.thutu
          this.frmProps.txtMaBC = item.ma_bc
          this.cboLoaiHD.value = item.loaihd_id
          this.frmProps.txtThuTuc = item.thutuc
          this.chkBCXuatExcel.checked = item.xuat_excel == 1
          await this.HienThiDanhSach_HG_DaGan()
          this.setActiveActions(3)
        }
      } catch (error) {
        console.log(error)
      } finally {
        this.loading(false)
      }

    },
    async HienThiDanhSachTinh() {
      // Load ds tinh
      let dsTinhDagan = await this.fetchData(NghiepVu.TINH_DAGAN, JSON.stringify({'BAOCAO_ID': this.baocao_id}))
      if (dsTinhDagan.length > 0) {
        this.dsTinhDaGan = dsTinhDagan
        //this.$refs.DanhSachTinh.setTinhDaGan(dsTinhDagan)
      } else {
        this.dsTinhDaGan = []
      }
      let dsTinhChuaGan = await this.fetchData(NghiepVu.TINH_CHUAGAN, JSON.stringify({'BAOCAO_ID': this.baocao_id}))
      if (dsTinhChuaGan.length > 0) {
        //this.$refs.DanhSachTinh.setTinhChuaGan(dsTinhChuaGan)
        this.dsTinhChuaGan = dsTinhChuaGan
      } else {
        this.dsTinhChuaGan = []
      }

    },
    setActiveActions(kieu) {
      this.actions.forEach((item) => {
        if (item.id !== 'tsbtnCoppyBC' && item.id !== 'tsbtnDesign' && item.id !=='tsbtnSapXepSTT') {
          this.setActiveButton(item.id, false)
        }
      })
      if (kieu === -1) {
        this.$refs.txtTenBC.focus()
        this.setActiveButton('tsbtnGhiLai', true)
        this.setActiveButton('tsbtnHuyBo', true)
      }

      if (kieu === 0) {
        this.$refs.txtTenBC.focus()
        this.setActiveButton('tsbtnNhapMoi', true)
        this.clear()
      }

      if (kieu === 1) {
        this.$refs.txtTenBC.focus()
        this.setActiveButton('tsbtnGhiLai', true)
        this.setActiveButton('tsbtnHuyBo', true)
        this.clear()
      }

      if (kieu === 2) {
        this.$refs.txtTenBC.focus()
        this.setActiveButton('tsbtnNhapMoi', true)
        this.setActiveButton('tsbtnXoa', true)
        this.clear()
      }

      if (kieu === 3) {
        this.setActiveButton('tsbtnNhapMoi', true)
        this.setActiveButton('tsbtnXoa', true)
        this.setActiveButton('tsbtnGhiLai', true)
        this.setActiveButton('tsbtnHuyBo', true)

      }
    },
    async HienThiDanhSach_HG_ChuaGan() {
      try {
        let vquytrinh_id = 0
        let vloaihd_id = 0
        if (this.cboNhomQT.value === NHOM_QT.DIEUHANH_THICONG) {
          vloaihd_id = this.cboLoaiHD.value
          vquytrinh_id = this.cboQuyTrinh.value
        }
        //let dsTinh = this.$refs.DanhSachTinh.getTinhDaGan().map(item => item.tinh_id).join(',')
        let dsTinh = this.dsTinhDaGan.map(item => item.tinh_id).join(',')
        let req = {
          BAOCAO_ID: this.baocao_id,
          NHOM_QT_ID: this.cboNhomQT.value,
          LOAIHD_ID: this.cboLoaiHD.value,
          QUYTRINH_ID: this.cboQuyTrinh.value,
          DS_TINH: dsTinh
        }
        // Load ds huong giao
        let dsHuongGiaoChuaGan = await this.fetchData(NghiepVu.HUONGGIAO_CHUAGAN, JSON.stringify(req))
        if (dsHuongGiaoChuaGan.length > 0) {
          this.$refs.DanhSachHuongGiao.setHuongGiaoChuaGan(dsHuongGiaoChuaGan)
        } else {
          this.$refs.DanhSachHuongGiao.setHuongGiaoChuaGan([])
        }
      } catch (error) {
        console.log(error)
        this.$root.$toast.error('Lỗi khi hiển thị danh sách hướng giao chưa gán ' + error.message)
      }
    },
    async HienThiDanhSach_HG_DaGan() {
      try {
        let dsTinh = this.dsTinhDaGan.map(item => item.tinh_id).join(',')
        // Load huong giao da gan
        let dsHGDaGan = await this.fetchData(NghiepVu.HUONGGIAO_DAGAN, JSON.stringify({
          'BAOCAO_ID': this.baocao_id,
          'DS_TINH': dsTinh
        }))
        if (dsHGDaGan.length > 0) {
          this.$refs.DanhSachHuongGiao.setHuongGiaoDaGan(dsHGDaGan)
        } else {
          this.$refs.DanhSachHuongGiao.setHuongGiaoDaGan([])
        }

      } catch (error) {
        console.log(error)
        this.$root.$toast.error('Lỗi khi hiển thị danh sách hướng giao đã gán ' + error.message)
      }
    },
    clear() {
      this.frmProps.txtTenBC = ''
      this.baocao_id = 0
      this.frmProps.txtCauLenh = ''
      this.frmProps.txtGhiChu = ''
      this.frmProps.txtMaBC = ''
      this.frmProps.txtThuTuc  = ''
      this.frmProps.txtThuTu = ''
      this.$refs.txtTenBC.focus()
    },
    setActiveButton(id, status) {
      let index = this.actions.findIndex(item => item.id === id)
      if (index >= 0) {
        this.actions[index].active = status
      }
    },
    async fn_thietke_baocao_capnhat(pKieu, pDsBaoCao, pDsBaoCaoHg, pDsBaoCaoPv) {
      let req = {
        p_kieu: pKieu,
        p_ds_baocao: JSON.stringify(pDsBaoCao),
        p_ds_baocao_hg: JSON.stringify(pDsBaoCaoHg),
        p_ds_baocao_phanvungs: JSON.stringify(pDsBaoCaoPv)
      }
      await ConfigReportAPI.fn_thietke_baocao_capnhat(this.axios, req).then(response => {
        if (response && response.data && response.data.error_code === 'BSS-00000000') {
          let data = JSON.parse(response.data.data)
          let kq = data.KETQUA
          let msg = data.THONGBAO
          if (kq === 1) {
            if (pKieu === 3) {
              this.gridviewBaoCao.selectItem = null
            }
            this.fetchBaoCao()
            this.$root.$toast.success(msg)
          } else {
            this.$root.$toast.error(msg)
          }
        } else {
          this.$root.$toast.error('Có lỗi khi thực hiện: ' + error.data.message)
        }
      }).catch(error => {
        console.log(error)
        this.$root.$toast.error('Xảy ra lỗi khi thực hiện: ' + error.data.message)
      })
    },
    GetDataList: function (response) {
      if (response && response.data && response.data.error_code === 'BSS-00000000') {
        return response.data.data == null ? [] : response.data.data
      } else if (response && response.data && response.data.error_code === 'BSS-00000204') {
      } else {
        this.$root.$toast.error('Lấy dữ liệu không thành công')
      }
      return []
    },
    GetData: function (response) {
      if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
        return response.data.data
      } else if (response && response.data && response.data.error_code === 'BSS-00000204') {
        return null
      } else {
        this.$root.$toast.error('Lấy dữ liệu không thành công')
      }
      return null
    },
    tsbtnXoa_Click() {
      try {
        this.$confirm('Bạn thật sự muốn xóa dữ liệu không ?', 'Xác nhận', {
          confirmButtonText: 'Đồng ý',
          cancelButtonText: 'Hủy'
        }).then(async () => {
          let dsBC = {
            BAOCAO_ID: this.baocao_id
          }
          await this.fn_thietke_baocao_capnhat(3, dsBC, '', '')
        })
      } catch (error) {
        console.log(error)
        this.$root.$toast.error('Có lỗi: ' + error.message)
      }
    },
    async CapNhat() {
      try {
        if (!this.frmProps.txtThuTu) {
          this.$root.$toast.warning('Thứ tự báo cáo không được để trống!')
          return
        }
        if (!this.frmProps.txtTenBC) {
          this.$root.$toast.warning('Tên báo cáo không được để trống!')
          return
        }

        if (!this.frmProps.txtCauLenh) {
          this.$root.$toast.warning('Câu lệnh báo cáo không được để trống!')
          return
        }

        if (!this.getButtonStatus('tsbtnNhapMoi')) {
          await this.TaoDuLieu(true)
          await this.fn_thietke_baocao_capnhat(1, this.dsBaoCao[0], this.dsBaoCao_hg, this.dsBaoCao_pv)
        } else {
          await this.TaoDuLieu(false)
          await this.fn_thietke_baocao_capnhat(2, this.dsBaoCao[0], this.dsBaoCao_hg, this.dsBaoCao_pv)
        }
      } catch (error) {
        console.log(error)
        this.$root.$toast.error("Lỗi cập nhật: " + error.message)
      }
    },
    async TaoDuLieu(themmoi) {
      let row = {}
      this.dsBaoCao = []
      if (themmoi) {
        row.BAOCAO_ID = 0
      } else {
        row.BAOCAO_ID = this.baocao_id;
      }
      row.PHANVUNG_ID = this.$root.token.getPhanVungID()
      row.MA_BC = this.frmProps.txtMaBC
      row.NHOM_BC_ID = this.nhom_bc_id;
      row.TEN_BC = this.frmProps.txtTenBC;
      row.TRANGTHAI = this.chkTrangThai.checked ? 1 : 0
      row.GHICHU = this.frmProps.txtGhiChu
      row.CAULENH = this.frmProps.txtCauLenh.trim()
      row.THUTU = this.frmProps.txtThuTu
      row.LOAIHD_ID = this.cboLoaiHD.value
      row.THUTUC = this.frmProps.txtThuTuc
      row.XUAT_EXCEL = this.chkBCXuatExcel.checked ? 1 : 0

      this.dsBaoCao.push(row)

      this.dsBaoCao_hg = []

      let dsHg = this.$refs.DanhSachHuongGiao.getHuongGiaoDaGan()
      let instance = this
      dsHg.forEach(function (item) {
        let row_hg = {}
        row_hg.BAOCAO_ID = instance.baocao_id
        row_hg.HUONGGIAO_ID = item.huonggiao_id
        instance.dsBaoCao_hg.push(row_hg)
      })

      this.dsBaoCao_pv = []

      if (this.loai_ct_id == LOAI_CT.BAOCAODHSXKD) {
        let dsPhanVung = this.$refs.DanhSachTinh.getTinhDaGan()

        dsPhanVung.forEach(function (item) {
          let row_pv = {}
          row_pv.BAOCAO_ID = instance.baocao_id
          row_pv.PHANVUNG_ID = item.tinh_id
          instance.dsBaoCao_pv.push(row_pv)
        })
      }
    },
    getButtonStatus(id) {
      let index = this.actions.findIndex(item => item.id == id)
      if (index >= 0) {
        return this.actions[index].active
      }
      return false
    },
    async tsbtnNhapMoi_Click() {
      this.setActiveActions(1)
      await this.HienThiDanhSachTinh()
      this.$refs.DanhSachHuongGiao.setHuongGiaoDaGan([])
    },
    TaoDuLieu_Copy() {
      let dsHg = this.$refs.DanhSachHuongGiao.getHuongGiaoDaGan()
      let instance = this
      dsHg.forEach(function (item) {
        let row_hg = {}
        row_hg.BAOCAO_ID = instance.baocao_id
        row_hg.HUONGGIAO_ID = item.huonggiao_id
        instance.dsBaoCao_hg.push(row_hg)
      })

      this.dsBaoCao_pv = []
      if (this.loai_ct_id === LOAI_CT.BAOCAODHSXKD) {
        let dsPhanVung = this.$refs.DanhSachTinh.getTinhDaGan()
        dsPhanVung.forEach(function (item) {
          let row_hg = {}
          row_hg.BAOCAO_ID = instance.baocao_id
          row_hg.PHANVUNG_ID = item.tinh_id
          instance.dsBaoCao_pv.push(row_hg)
        })
      }

    },
    async tsbtnCoppyBC_Click() {
      if (this.baocao_id === 0) {
        this.$root.$toast.warning('Chọn một báo cáo để thực hiện copy !')
        return
      }
      this.TaoDuLieu_Copy()
      let bc = {'BAOCAO_ID': this.baocao_id}
      await this.fn_thietke_baocao_capnhat(4, bc, this.dsBaoCao_hg, this.dsBaoCao_pv)
    },
    async tsbtnSapXepSTT_Click() {
      if (this.cboNhomBC.value === 0) {
        this.$root.$toast.warning('Chọn nhóm báo cáo để sắp xếp')
        return
      }
      let bc = {'NHOM_BC_ID': this.cboNhomBC.value}
      await this.fn_thietke_baocao_capnhat(6, bc, [], [])
    },
    onActionClick(action) {
      if (action.id === 'tsbtnNhapMoi') {
        this.tsbtnNhapMoi_Click()
      } else if (action.id === 'tsbtnGhiLai') {
        this.CapNhat()
      } else if (action.id === 'tsbtnXoa') {
        this.tsbtnXoa_Click()
      } else if (action.id === 'tsbtnHuyBo') {
        this.setActiveActions(0)
      } else if (action.id === 'tsbtnDesign') {
        window.open('http://bip-onebss.vnpt.vn/analytics/saw.dll?catalog')
      } else if (action.id === 'tsbtnCoppyBC') {
        this.tsbtnCoppyBC_Click()
      } else if (action.id === 'tsbtnSapXepSTT') {
        this.tsbtnSapXepSTT_Click()
      }
    }
  }
}
</script>

<style scoped>

</style>
