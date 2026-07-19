<template src="./UpdateCodeInformation.html"></template>
<style src="./UpdateCodeInformation.scss"></style>

<script>
import breadcrumb from '@/components/breadcrumb'
import ActionTop from '@/components/kylq_components/ActionTop'
import UpdateCodeInformationAPI from './UpdateCodeInformationAPI'
import {LOAI_DV} from '../../../../const/enums'
import SearchAccount from '@/modules/search/subscriber/SearchAccount'
import ModalChonDiaChi from '../InstallNewSubs/Popups/popupChonDiaChi/ModalChonDiaChi'
import popupChonGrid from '../InstallNewSubs/Popups/popupChonGrid'
import TimNhanVienThuCuoc from '../../../EXTS/HOPDONG/ThayDoiThongTinKhuyenMai/TimNhanVienThuCuoc'
import moment from 'moment'

export default {
  name: 'UpdateCodeInformation',
  props: {
    inputData: {
      type: Object,
      default: function () {
        return null
      }
    }
  },
  components: {
    breadcrumb,
    ActionTop,
    SearchAccount,
    ModalChonDiaChi,
    popupChonGrid,
    TimNhanVienThuCuoc
  },
  data () {
    return {
      animationSettings: { effect: 'Zoom' },
      target: '.main-wrapper',
      actions: [
        {
          id: 'tsbtnNhapMoi',
          name: 'Tạo mới',
          active: true,
          icon_class: 'one-file-plus'
        },
        {
          id: 'tsbtnCapNhat',
          name: 'Cập nhật',
          active: true,
          icon_class: 'one-save'
        },
        {
          id: 'tsbtnXoa',
          name: 'Xóa',
          active: true,
          icon_class: 'nc-icon-glyph ui-1_circle-remove'
        }
      ],
      settingSelect2: {
        language: 'vi'
      },
      cboDonViQL: {
        list: [],
        selectedValue: 0
      },
      formThongTinTaoMaGhep: {
        txtMaKH: '',
        txtMaIG: '',
        txtMaTuyen: '',
        txtMaNVTC: '',
        txtDiaChiIG: ''
      },
      dsMaInGhep: {
        headers: [
          {
            fieldName: 'main_ghep',
            headerText: 'Mã IG',
            allowFiltering: true,
            allowSorting: false
          },
          {
            fieldName: 'diachi_ig',
            headerText: 'Địa chỉ IG',
            allowFiltering: true,
            allowSorting: false
          },
          {
            fieldName: 'ma_nv',
            headerText: 'Mã NVTC',
            allowFiltering: true,
            allowSorting: false
          }
        ],
        list: [],
        selected: 0,
        selectItem: null,
        checked: []
      },
      dsMaTTDaGan: {
        headers: [
          {
            fieldName: 'ma_kh',
            headerText: 'Mã KH',
            allowFiltering: true,
            allowSorting: false
          },
          {
            fieldName: 'ma_tt',
            headerText: 'Mã TT',
            allowFiltering: true,
            allowSorting: false
          },
          {
            fieldName: 'hinhthuc_tt',
            headerText: 'Hình thức TT',
            allowFiltering: true,
            allowSorting: false
          },
          {
            fieldName: 'ten_tt',
            headerText: 'Tên thanh toán',
            allowFiltering: true,
            allowSorting: false
          },
          {
            fieldName: 'diachi_tt',
            headerText: 'Địa chỉ TT',
            allowFiltering: true,
            allowSorting: false
          },
          {
            fieldName: 'diachi_bc',
            headerText: 'Địa chỉ BC',
            allowFiltering: true,
            allowSorting: false
          },
          {
            fieldName: 'mst',
            headerText: 'MST',
            allowFiltering: true,
            allowSorting: false
          },
          {
            fieldName: 'manv_tc',
            headerText: 'Đại lý',
            allowFiltering: true,
            allowSorting: false
          }
        ],
        list: [],
        selected: 0,
        selectItem: null,
        checked: []
      },
      dsMaTTChuaGan: {
        headers: [
          {
            fieldName: 'ma_tt',
            headerText: 'Mã TT',
            allowFiltering: true,
            allowSorting: false
          },
          {
            fieldName: 'hinhthuc_tt',
            headerText: 'Hình thức TT',
            allowFiltering: true,
            allowSorting: false
          },
          {
            fieldName: 'ten_tt',
            headerText: 'Tên thanh toán',
            allowFiltering: true,
            allowSorting: false
          },
          {
            fieldName: 'diachi_tt',
            headerText: 'Địa chỉ TT',
            allowFiltering: true,
            allowSorting: false
          },
          {
            fieldName: 'diachi_bc',
            headerText: 'Địa chỉ BC',
            allowFiltering: true,
            allowSorting: false
          },
          {
            fieldName: 'mst',
            headerText: 'MST',
            allowFiltering: true,
            allowSorting: false
          }
        ],
        list: [],
        selected: 0,
        selectItem: null,
        checked: []
      },
      hdkh_id: 0,
      vloaihkh_id: 0,
      ma_tt: '',
      hdtt_id: 0,
      thanhtoan_id: 0,
      tucthi: 0,
      chapnhan: false,
      kyhoadon: '',
      chuky: '',
      mig_id: 0,
      fhdmig_id: 0,
      fmig_id: 0,
      main_ghep: '',
      ma_nvtc: '',
      // dia chi BC
      diachiig_id: null,
      phoig_id: null,
      dacdiemig_id: null,
      apig_id: null,
      khuig_id: null,
      phuongig_id: null,
      quanig_id: null,
      tinhig_id: null,
      sonhaig: '',
      // end dia chi bc
      nhanvientc_id: 0,
      tuyenthu_id: 0,
      vdiachiig_id: 0,
      vma_kh: '',
      thu: 0,
      ds: [],
      ds_maig: [],
      dsDBMIG: [],
      dsDiaChi: [],
      dsDiaChi_MIG: [],
      dsMigTmp: [],
      ds_MaIG: [], // ds_MaIG_Them.Tables["MIG"]
      ds_GanMIG: [], // ds_MaIG_Them.Tables["GanMIG"]
      dt_tmp: [],
      ds_cg: [],
      ds_dg: [],
      dataSelected: {
        diachi: '',
        tinh_id: 0,
        quan_id: 0,
        phuong_id: 0,
        pho_id: 0,
        ap_id: 0,
        khu_id: 0,
        sonha: '',
        dac_diem_id: 0
      },
      dulieuDVQL: {},
      dulieuNVTC: {
        QUAN_ID: 0,
        PHUONG_ID: 0,
        PHO_ID: 0,
        AP_ID: 0,
        KHU_ID: 0,
        DACDIEM_ID: 0,
        NHANVIENTC_ID: 0,
        KT_KYLAI: true,
        TUYENTHU_ID: 0,
        DONVIQL_ID: 0
      },
      maycn: this.$root.token.getMachine(),
      ipcn: this.$root.token.getIP(),
      nguoicn: this.$root.token.getUserName(),
      flagChonDvQL: false
    }
  },
  methods: {
    async dialogOpen () {
      this.LoadDataFromProps()
      await this.LAY_KY_CUOC_HIEN_HANH()
      this.maycn = await this.$root.token.getMachine()
      this.ipcn = await this.$root.token.getIP()
      this.$refs.txtMaKH.focus()
      let dsDonViQL = await this.SP_HT_DONVI_LOAIDV_COMBOBOX(0, LOAI_DV.DONVIQL_TT)
      if (dsDonViQL.length > 0) {
        this.cboDonViQL.list = dsDonViQL.map(item => {
          return {
            id: item.donvi_id,
            text: item.ten_dv
          }
        })
        this.cboDonViQL.selectedValue = this.cboDonViQL.list[0].id
      } else {
        this.cboDonViQL.list = []
      }
      if (this.vma_kh !== '') {
        this.formThongTinTaoMaGhep.txtMaKH = this.vma_kh
      }
      await this.HienThiDS()

      console.log(this.dsMaTTChuaGan.list)
    },
    dialogClose () {
      this.clear()
    },
    close () {
      this.$emit('closeDialog', true)
      this.inputData = null
      this.ds_MaIG = []
      this.ds_GanMIG = []
      this.dsMaTTChuaGan.list = []
      this.dsMaTTDaGan.list = []
      this.thu = 0
      this.dt_tmp = []
      this.$refs.dlgUpdateCodeInformation.hide()
    },
    showDialog () {
      this.$refs.dlgUpdateCodeInformation.show()
    },
    LoadDataFromProps () {
      if (this.inputData != null) {
        console.log(this.inputData)
        this.khachhang_id = this.inputData.khachhang_id ? this.inputData.khachhang_id : 0
        this.fmig_id = this.inputData.fmig_id ? this.inputData.fmig_id : 0
        this.tucthi = this.inputData.tucthi ? this.inputData.tucthi : 0
        this.kyhoadon = this.inputData.kyhoadon ? this.inputData.kyhoadon : this.kyhoadon
        this.vma_kh = this.inputData.vma_kh ? this.inputData.vma_kh : ''
        this.ds_MaIG = this.inputData.ds_MaIG ? this.inputData.ds_MaIG : this.ds_MaIG
        this.ds_GanMIG = this.inputData.ds_GanMIG ? this.inputData.ds_GanMIG : this.ds_GanMIG
      }
    },
    async HienThiDS () {
      try {
        let ds = await this.LAY_DS_MIG_HDDB_KH(0, this.khachhang_id, 0)
        if (this.ds_MaIG.length > 0) {
          this.ds_MaIG.forEach(item => {
            ds.push(item)
          })
        }
        if (ds.length > 0) {
          this.dsMaInGhep.list = ds
          let grid = this.$refs.gridDsMaInGhep.grid
          for (let i = 0; i < ds.length; i++) {
            let item = ds[i]
            if (this.fhdmig_id === 0) {
              if (this.fmig_id == item.mig_id) {
                grid.clearSelection()
                grid.selectRow(i, true)
                break
              }
            } else {
              if (this.fhdmig_id == item.hdmig_id) {
                grid.clearSelection()
                grid.selectRow(i, true)
                break
              }
            }
          }
        } else {
          this.dsMaInGhep.list = []
          this.dsMaInGhep.selectItem = null
          this.clear()
        }
      } catch (e) {
        console.log(e)
        this.$root.$toast.error('Xảy ra lỗi khi hiển thị danh sách', e.message)
      }
    },
    selectMaInGhep (i, item) {
      console.log('Select ma in ghep index ' + i)
      this.gridDsMIGSelectedChange(item)
    },
    getDiaChi: function (data) {
      this.dataSelected.diachi = data.diachimoi
      this.dataSelected.tinh_id = data.tinh_id
      this.dataSelected.quan_id = data.quan_id
      this.dataSelected.phuong_id = data.phuong_id
      this.dataSelected.pho_id = data.pho_id
      this.dataSelected.ap_id = data.ap_id
      this.dataSelected.khu_id = data.khu_id
      this.dataSelected.dac_diem_id = data.dacdiem_id
      this.dataSelected.so_nha = data.so_nha
      this.currentDonVi.DIACHI_DV = this.dataSelected.diachi
    },
    async gridDsMIGSelectedChange (item) {
      if (item != null) {
        try {
          console.log(item)
          this.dsMaInGhep.selectItem = item
          if (this.dsMaInGhep.list.length > 0 || this.ds_MaIG.length > 0) {
            this.mig_id = 0
            if (item.mig_id) {
              this.mig_id = Number(item.mig_id)
            }
            this.main_ghep = item.main_ghep
            if (item.tuyenthu_id) {
              this.tuyenthu_id = Number(item.tuyenthu_id)
              this.ma_nvtc = await this.SP_LAY_MANVTC_TUYENTHU(this.tuyenthu_id)
            }
            this.formThongTinTaoMaGhep.txtMaIG = this.main_ghep
            this.formThongTinTaoMaGhep.txtMaNVTC = this.ma_nvtc
            this.formThongTinTaoMaGhep.txtMaTuyen = this.tuyenthu_id
            this.formThongTinTaoMaGhep.txtDiaChiIG = item.diachi_ig
            let dsDc = await this.LAY_DS_DIACHI_THEO_DBIGID(this.mig_id)
            this.HienThiTTDiaChi(dsDc)
            await this.HT_DanhSachTT(this.mig_id)
          }
        } catch (error) {
          console.log(error)
          this.$root.$toast.error('Xảy ra lỗi khi hiển thị thông tin mã in ghép')
        }
      }
    },
    async HT_DanhSachTT (vmigId) {
      try {
        let dsDG = await this.LAY_DS_THANHTOAN_MAIG_KHID(vmigId, this.khachhang_id, 1)
        if (dsDG.length > 0) {
          this.dsMaTTDaGan.list = dsDG
        } else {
          this.dsMaTTDaGan.list = []
        }
        let dsCg = await this.LAY_DS_THANHTOAN_MAIG_KHID(vmigId, this.khachhang_id, 0)
        // Thực hiện ghép mã IG trong TC_DSThanhToan
        let dt = []
        let dsTam = []
        let ds1 = []
        if (this.ds_GanMIG.length > 0 || this.dt_tmp.length === 1) {
          if (this.thu === 0) {
            this.dt_tmp = this.ds_GanMIG
            this.thu = 1
          }
          if (this.ds_GanMIG.length !== this.dt_tmp.length) {
            try {
              let ccListMatt = this.dt_tmp
              let bannedCCList = dsCg.map((item) => item.ma_tt)
              dt = ccListMatt.filter(item => !bannedCCList.includes(item.ma_tt))
            } catch (error) {
              console.log(error)
            }
            ds1 = dt
            if (dt.length < this.dt_tmp.length) {
              this.dt_tmp = ds1
              dsTam.concat(this.dt_tmp)
              dsCg = dsTam
            } else {
              dsCg = ds1
            }
          } else {
            dsCg = this.ds_GanMIG
          }
        }
        if (dsCg.length > 0) {
          this.dsMaTTChuaGan.list = dsCg
        } else {
          this.dsMaTTChuaGan.list = []
        }
      } catch (error) {
        console.log(error)
        this.$root.$toast.error('Xảy ra lỗi khi hiển thị danh sách thanh toán')
      }
    },
    HienThiTTDiaChi (ds) {
      if (ds.length > 0) {
        // Địa chỉ in ghép
        let item = ds[0]
        if (item.diachi_id) {
          this.diachiig_id = item.diachi_id
        } else {
          this.diachiig_id = 0
        }

        if (item.tinh_id) {
          this.tinhig_id = item.tinh_id
        } else {
          this.tinhig_id = 0
        }

        if (item.quan_id) {
          this.quanig_id = item.quan_id
        } else {
          item.quanig_id = 0
        }

        if (item.phuong_id) {
          this.phuongig_id = item.phuong_id
        } else {
          this.phuongig_id = 0
        }

        if (item.dacdiem_id) {
          this.dacdiemig_id = item.dacdiem_id
        } else {
          this.dacdiemig_id = 0
        }

        if (item.pho_id) {
          this.phoig_id = item.pho_id
        } else {
          this.phoig_id = 0
        }

        if (item.ap_id) {
          this.apig_id = item.ap_id
        } else {
          this.apig_id = 0
        }

        if (item.khu_id) {
          this.khuig_id = item.khu_id
        } else {
          this.khuig_id = 0
        }
        this.sonhaig = item.sonha
      }
    },
    async LAY_DS_THANHTOAN_MAIG_KHID (migId, khachhangId, kieu) {
      let ds = []
      await UpdateCodeInformationAPI.LAY_DS_THANHTOAN_MAIG_KHID(this.axios, {
        'vmig_id': migId,
        'vkhachhang_id': khachhangId,
        'vkieu': kieu
      }).then(response => {
        if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
          ds = response.data.data
        }
      })
      return ds
    },
    async SP_LAY_MANVTC_TUYENTHU (tuyenthuId) {
      let manv = ''
      await UpdateCodeInformationAPI.SP_LAY_MANVTC_TUYENTHU(this.axios, {
        'tuyenthu_id': tuyenthuId
      }).then(response => {
        if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
          manv = response.data.data
        }
      })
      return manv
    },
    async FN_MAP_ID (dk, idNeo, table) {
      let result = ''
      const req = {
        dk: dk,
        id_neo: idNeo,
        table: table
      }
      await UpdateCodeInformationAPI.FN_MAP_ID(this.axios, req).then(response => {
        if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
          result = response.data.data
        }
      })
      return result
    },
    async LAY_DS_DIACHI_THEO_DBIGID (migId) {
      let ds = []
      await UpdateCodeInformationAPI.LAY_DS_DIACHI_THEO_DBIGID(this.axios, {
        'in_mig_id': migId
      }).then(response => {
        if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
          ds = response.data.data
        }
      })
      return ds
    },
    async SP_HT_DONVI_LOAIDV_COMBOBOX (donviId, loadDvId) {
      let ds = []
      await UpdateCodeInformationAPI.SP_HT_DONVI_LOAIDV_COMBOBOX(this.axios, {
        'donvi_id': donviId,
        'loaidv_id': loadDvId
      }).then(response => {
        if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
          ds = response.data.data
        }
      })
      return ds
    },
    async LAY_DS_MIG_HDDB_KH (vhdkhId, vkhachhangId, vhdttId) {
      let ds = []
      await UpdateCodeInformationAPI.LAY_DS_MIG_HDDB_KH(this.axios, {
        'vhdkh_id': vhdkhId,
        'vkhachhang_id': vkhachhangId,
        'vhdtt_id': vhdttId
      }).then(response => {
        if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
          ds = response.data.data
        }
      })
      return ds
    },
    async LAY_DB_THEO_MATB (matb, donvidlid, dichvuvtid) {
      let ds = []
      await UpdateCodeInformationAPI.LAY_DB_THEO_MATB(this.axios, {
        'ma_tb': matb,
        'donvi_dl_id': donvidlid,
        'dichvuvt_id': dichvuvtid
      }).then(response => {
        if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
          ds = response.data.data
        }
      })
      return ds
    },
    async LAY_DS_DONVIQL (apId, phoId, kieu, khuId, phuongId) {
      let ds = []
      await UpdateCodeInformationAPI.LAY_DS_DONVIQL(this.axios, {
        'vap_id': apId,
        'vpho_id': phoId,
        'vkieu': kieu,
        'vkhu_id': khuId,
        'vphuong_id': phuongId
      }).then(response => {
        if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
          ds = response.data.data
        }
      })
      return ds
    },
    async SP_HT_NVTC_THEODC (phuongId, phoId, apId, khuId, dacdiemId, donviQlId) {
      let ds = []
      let req = {
        'phuong_id': phuongId === null ? 0 : phuongId,
        'pho_id': phoId === null ? 0 : phoId,
        'ap_id': apId === null ? 0 : apId,
        'khu_id': khuId == null ? 0 : khuId,
        'dacdiem_id': dacdiemId === null ? 0 : dacdiemId,
        'donvi_ql_id': donviQlId === null ? 0 : donviQlId
      }
      console.log(req)
      await UpdateCodeInformationAPI.SP_HT_NVTC_THEODC(this.axios, req).then(response => {
        if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
          ds = response.data.data
        }
      })
      return ds
    },
    async txtMaKH_KeyPress () {
      try {
        if (this.formThongTinTaoMaGhep.txtMaKH) {
          let ds = await this.LAY_DB_THEO_MATB(this.formThongTinTaoMaGhep.txtMaKH.trim(), 0, 0)
          if (ds.length > 0) {
            this.khachhang_id = Number(ds[0].khachhang_id)
            this.vloaikh_id = Number(ds[0].loaikh_id)
            await this.HienThiDS()
          } else {
            this.$root.$toast.warning('Không tìm thấy thông tin khách hàng!')
            this.clear()
          }
        }
      } catch (error) {
        this.$root.$toast.error('Lỗi không lấy được thông tin khách hàng')
      }
    },
    showPopupSearchAccount () {
      this.$bvModal.show('popupSearchAccount')
    },
    closeSearchAccount (value) {
      if (value != null) {
        this.formThongTinTaoMaGhep.txtMaKH = value.ma_kh.trim()
        this.$refs.txtMaKH.focus()
        this.txtMaKH_KeyPress()
      }
      this.$bvModal.hide('popupSearchAccount')
    },
    btnDiaChiIG_Click () {
      this.dataSelected.dac_diem_id = this.dacdiemig_id
      this.dataSelected.tinh_id = this.tinhig_id
      this.dataSelected.quan_id = this.quanig_id
      this.dataSelected.phuong_id = this.phuongig_id
      this.dataSelected.pho_id = this.phoig_id
      this.dataSelected.ap_id = this.apig_id
      this.dataSelected.khu_id = this.khuig_id
      this.dataSelected.sonha = this.sonhaig
      this.dataSelected.diachi = this.formThongTinTaoMaGhep.txtDiaChiIG
      this.$root.$emit('bv::show::modal', 'popupChonDiaChi')
    },
    showPopUpNhanVienTC () {
      this.dulieuNVTC.PHO_ID = this.phoig_id
      this.dulieuNVTC.PHUONG_ID = this.phuongig_id
      this.dulieuNVTC.QUAN_ID = this.quanig_id
      this.dulieuNVTC.AP_ID = this.apig_id
      this.dulieuNVTC.KHU_ID = this.khuig_id
      this.dulieuNVTC.DACDIEM_ID = this.dacdiemig_id
      this.dulieuNVTC.TUYENTHU_ID = this.tuyenthu_id
      this.dulieuNVTC.NHANVIENTC_ID = this.nhanvientc_id
      this.dulieuNVTC.KT_KYLAI = true
      this.dulieuNVTC.DONVIQL_ID = Number.parseInt(this.cboDonViQL.selectedValue)
      console.log(this.dulieuNVTC)
      this.$bvModal.show('dlgTimKiemNVTC')
    },
    async XacNhanDiaChi (val) {
      console.log(val)
      // Nếu chọn lại địa chỉ báo cước thì clear mã nhân viên thu cước
      this.ma_nvtc = ''
      this.formThongTinTaoMaGhep.txtMaNVTC = ''
      this.formThongTinTaoMaGhep.txtMaTuyen = ''
      // /Địa chỉ báo cước
      this.tinhig_id = val.tinh_id
      this.quanig_id = val.quan_id
      this.phuongig_id = val.phuong_id
      this.phoig_id = val.pho_id
      this.dacdiemig_id = val.dac_diem_id
      this.apig_id = val.ap_id
      this.khuig_id = val.khu_id
      this.sonhaig = val.so_nha
      this.formThongTinTaoMaGhep.txtDiaChiIG = val.diachimoi
      if (this.cboDonViQL.list.length !== 1) {
        let ds = await this.LAY_DS_DONVIQL(val.ap_id === null ? 0 : val.ap_id, val.pho_id === null ? 0 : val.pho_id, 1,
          val.khu_id === null ? 0 : val.khu_id, val.phuong_id === null ? 0 : val.phuong_id)
        if (ds.length <= 0) {
          this.$root.$toast.warning('Địa danh này chưa được gán đơn vị quản lý. Hãy liên hệ với Admin để xử lý!')
          this.cboDonViQL.selectedValue = 0
        } else if (ds.length == 1) {
          this.cboDonViQL.selectedValue = Number(ds[0].donvi_id)
        } else {
          this.dulieuDVQL = {
            'ds': ds,
            cols: [
              {fieldName: 'donviql_id', headerText: 'ID', allowFiltering: false, allowSorting: false},
              {fieldName: 'ten_dvql', headerText: 'Đơn vị', allowFiltering: false, allowSorting: false},
              {fieldName: 'ten_dv', headerText: 'Tổ thi công', allowFiltering: false, allowSorting: false}
            ],
            keycol: 'donviql_id',
            allowExit: false,
            tieude: 'Chọn đơn vị quản lý',
            tieudebang: 'Danh sách đơn vị'
          }
          this.$bvModal.show('popupDonViQL')
        }
      }
    },
    ChonDonViQL (val) {
      if (val) {
        this.cboDonViQL.selectedValue = Number(val.donviql_id)
        this.HT_NVTC_TheoDC(this.quanig_id, this.phuongig_id, this.phoig_id, this.apig_id, this.khuig_id, val.donviql_id, this.dacdiemig_id)
      }
      this.$bvModal.hide('popupDonViQL')
    },
    XacNhanNhanVienThuCuoc (item) {
      console.log('XacNhanNhanVienThuCuoc', item)
      if (item != null) {
        if (this.quanig_id !== 0 && this.phuongig_id !== 0 &&
          (this.phoig_id !== 0 || this.khuig_id !== 0 || this.apig_id !== 0)) {
          this.nhanvientc_id = item.nhanvientc_id
          this.ma_nvtc = item.ma_nvtc
          this.formThongTinTaoMaGhep.txtMaNVTC = item.ma_nvtc
          // TODO check lai
          this.formThongTinTaoMaGhep.txtMaTuyen = item.ma_tuyen
          this.tuyenthu_id = item.tuyenthu_id
        } else {
          this.$root.$toast.warning('Chưa có thông tin về địa chỉ in ghép. Bạn kiểm tra lại!')
          this.formThongTinTaoMaGhep.txtMaNVTC = ''
          this.formThongTinTaoMaGhep.txtMaTuyen = ''
          this.ma_nvtc = ''
          this.nhanvientc_id = 0
          this.$refs.txtMaNVTC.focus()
        }
      }
    },
    async HT_NVTC_TheoDC (quanId, phuongId, phoId, apId, khuId, donviQLId, dacdiemId) {
      try {
        let ds = await this.SP_HT_NVTC_THEODC(phuongId, phoId, apId, khuId, dacdiemId, donviQLId)

        if (ds.length > 1) {
          this.showPopUpNhanVienTC()
        } else if (ds.length > 0) {
          this.ma_nvtc = ds[0].manv_tc
          this.tuyenthu_id = ds[0].tuyenthu_id
          this.formThongTinTaoMaGhep.txtMaTuyen = ds[0].ma_tuyen
          this.formThongTinTaoMaGhep.txtMaNVTC = this.ma_nvtc
          this.nhanvientc_id = ds[0].nhanvien_id
        } else {
          this.formThongTinTaoMaGhep.txtMaTuyen = ''
          this.$refs.txtMaTuyen.focus()
          this.tuyenthu_id = 0
          this.nhanvientc_id = 0
        }
      } catch (error) {
        this.$root.$toast.error('Có lỗi ', error)
      }
    },
    tsbtnNhapMoi_Click () {
      try {
        if (this.phuongig_id === 0) {
          this.$root.$toast.warning('Bạn chưa chọn địa chỉ in ghép')
          return
        }
        if (this.ma_nvtc === '') {
          this.$root.$toast.warning('Bạn chưa chọn nhân viên thu cước')
          return
        }
        this.$confirm('Bạn có muốn sinh mã in ghép không ?', 'Thông báo', {
          confirmButtonText: 'Có',
          cancelButtonText: 'Không'
        }).then(async () => {
          await this.TaoDuLieu_DBMIG(0)
          await this.TaoDuLieu_DiaChi()
          await this.SP_THEMMOI_DANHBA_INGHEP(this.dsDBMIG, this.dsDiaChi, this.dsDiaChi_MIG)
          this.clear()
          await this.HienThiDS()
        })
      } catch (error) {
        this.$root.$toast.error('Có lỗi: ', error)
      }
    },
    async TaoDuLieu_DiaChi () {
      this.dsDiaChi = []
      this.dsDiaChi_MIG = []
      let row = {}
      this.diachiig_id = await this.POST_GET_KEYS('DIACHI')
      row.DIACHI_ID = this.diachiig_id
      row.TINH_ID = this.tinhig_id
      row.QUAN_ID = this.quanig_id
      row.PHUONG_ID = this.phuongig_id
      if (this.phoig_id !== null) {
        row.PHO_ID = this.phoig_id
      }
      if (this.apig_id !== null) {
        row.AP_ID = this.apig_id
      }
      if (this.khuig_id !== null) {
        row.khuig_id = this.khuig_id
      }
      if (this.dacdiemig_id !== null) {
        row.DACDIEM_ID = this.dacdiemig_id
      }
      row.SONHA = this.StringToString(this.sonhaig)
      row.DIACHI = this.StringToString(this.formThongTinTaoMaGhep.txtDiaChiIG)
      this.dsDiaChi.push(row)
      let rowDiaChiMIG = {}
      rowDiaChiMIG.DIACHI_ID = this.diachiig_id
      rowDiaChiMIG.MIG_ID = this.mig_id
      this.dsDiaChi_MIG.push(rowDiaChiMIG)
    },
    StringToString (str) {
      if (str === undefined || str === null) {
        return
      }
      let letter
      let text11
      let text22 = ''

      text11 = str
      for (let i = 0; i < text11.length; i++) {
        letter = text11.substr(i, 1)
        if (letter === '\r') {
          text22 = text22 + '\r'
        } else if (letter === '\n') {
          text22 = text22 + '\n'
        } else {
          text22 = (text22 + letter)
        }
      }
      return text22
    },
    async TaoDuLieu_DBMIG (vmigid) {
      this.dsDBMIG = []
      if (vmigid === 0) {
        this.main_ghep = await this.SINH_MA_MIG(this.ma_nvtc, this.phuongig_id)
      }
      let row = {}
      this.mig_id = await this.POST_GET_KEYS('DB_MIG')
      row.MIG_ID = this.mig_id
      row.KHACHHANG_ID = this.khachhang_id
      row.MAIN_GHEP = this.main_ghep
      row.DIACHI_IG = this.formThongTinTaoMaGhep.txtDiaChiIG
      row.TUYENTHU_ID = this.tuyenthu_id
      console.log(row)
      this.dsDBMIG.push(row)
    },
    async SP_THEMMOI_DANHBA_INGHEP (dsDBMIG, dsDiaChi, dsDiaChiMIG) {
      let req = {
        p_dsDBMIG: JSON.stringify(dsDBMIG),
        p_dsDiaChi: JSON.stringify(dsDiaChi),
        p_dsDiaChi_MIG: JSON.stringify(dsDiaChiMIG)
      }
      let response = await UpdateCodeInformationAPI.SP_THEMMOI_DANHBA_INGHEP(this.axios, req)
      console.log(response)
    },
    async POST_GET_KEYS (keyname) {
      let key = ''
      await UpdateCodeInformationAPI.POST_GET_KEYS(this.axios, {
        keyname: keyname
      }).then(response => {
        if (response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000') {
          key = response.data.data
        }
      })
      return key
    },
    async SINH_MA_MIG (manvtc, phuongid) {
      return this.GetData(await UpdateCodeInformationAPI.SINH_MA_MIG(this.axios, {
        vma_nvtc: manvtc,
        vphuong_id: phuongid
      }))
    },
    GetDataList: function (response) {
      if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
        return response.data.data
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
    async tsbtnCapNhat_Click () {
      try {
        if (this.dsMaInGhep.selectItem == null) {
          this.$root.$toast.warning('Chưa có thông tin mã in ghép để cập nhật!')
          return
        }

        let vmigid = ''
        let vthanhtoanid = ''
        vmigid = this.dsMaInGhep.selectItem.mig_id
        // Xóa dữ liệu ds_mig_tmp theo nguoidung_id
        await this.XOA_DS_MIG_TMP(Number.parseInt(this.$root.token.getNguoiDungID()))
        // Insert dữ liệu vào ds_mig_tmp
        this.dsMigTmp = []
        this.dsMaTTDaGan.list.forEach(item => {
          let row = {}
          row.nguoidung_id = Number.parseInt(this.$root.token.getNguoiDungID())
          row.mig_id = Number.parseInt(vmigid)
          row.ma_tt = item.ma_tt
          this.dsMigTmp.push(row)
        })// TODO check lai tham so truyen vao api
        let result = null
        if (this.dsMigTmp.length > 0) {
          // Cập nhật thông tin mig
          result = await this.SP_CAPNHAT_THONGTIN_MIG(this.tucthi, this.kyhoadon, this.dsMigTmp)
        } else {
          for (let i = 0; i < this.dsMaTTChuaGan.list.length; i++) {
            let item = this.dsMaTTChuaGan.list[i]
            vthanhtoanid = item.thanhtoan_id.toString()
            // Cập nhật lịch sử thay đổi DBTT
            let result1 = await this.CAPNHAT_LS_THAYDOI_DBTT(Number.parseInt(vthanhtoanid), this.maycn, this.ipcn, this.nguoicn)
            // Cập nhật gỡ MIG
            let result2 = await this.CAPNHAT_GO_MIG(Number.parseInt(vthanhtoanid), this.tucthi, this.kyhoadon, this.maycn, this.ipcn, this.nguoicn)
            result = (result1 === true) && (result2 === true)
          }
        }
        if (result) {
          await this.gridDsMIGSelectedChange(this.dsMaInGhep.selectItem)
          this.$root.$toast.success('Cập nhật dữ liệu thành công!')
        } else {
          this.$root.$toast.error('Cập nhật dữ liệu thất bại!')
        }
      } catch (error) {
        this.$root.$toast.error('Có lỗi: ', error.message)
      }
    },
    async CAPNHAT_LS_THAYDOI_DBTT (thanhtoanid, vmaycn, vipcn, vnguoicn) {
      let result = false
      let req = {
        vthanhtoan_id: thanhtoanid,
        vmay_cn: vmaycn,
        ip_cn: vipcn,
        nguoi_cn: vnguoicn
      }
      await UpdateCodeInformationAPI.CAPNHAT_LS_THAYDOI_DBTT(this.axios, req).then(response => {
        if (response && response.data && response.data.error_code === 'BSS-00000000') {
          result = true
        } else {
          result = false
          this.$root.$toast.error('Có lỗi khi cập nhật lịch sử thay đổi DBTT: ' + response.data.message_detail)
        }
      }).catch(error => {
        console.log(error)
        this.$root.$toast.error('Có lỗi khi cập nhật lịch sử thay đổi DBTT: ' + error.data.message_detail)
      })
      return result
    },
    async CAPNHAT_GO_MIG (thanhtoanid, tucthi, kyhoadon, maycn, ipcn, nguoicn) {
      let result = false
      let req = {
        vthanhtoan_id: thanhtoanid,
        vtucthi: tucthi,
        vkyhoadon: kyhoadon,
        vmay_cn: maycn,
        vip_cn: ipcn,
        vnguoi_cn: nguoicn
      }
      await UpdateCodeInformationAPI.CAPNHAT_GO_MIG(this.axios, req).then(response => {
        if (response && response.data && response.data.error_code === 'BSS-00000000') {
          result = true
        } else {
          result = false
          this.$root.$toast.error('Có lỗi khi Cập nhật gỡ MIG: ' + response.data.message_detail)
        }
      }).catch(error => {
        console.log(error)
        this.$root.$toast.error('Có lỗi khi Cập nhật gỡ MIG: ' + error.data.message_detail)
      })
      return result
    },
    async GET_CHUKYNO_THEO_KYCUOC () {
      let khd = moment(this.kyhoadon, 'YYYYMMDD').format('YYYYMM')
      await UpdateCodeInformationAPI.GET_CHUKYNO_THEO_KYCUOC(this.axios, khd).then(response => {
        if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
          this.chukyno = response.data.data[0].CHUKY
        }
      })
    },
    async LAY_KY_CUOC_HIEN_HANH () {
      await UpdateCodeInformationAPI.LAY_KY_CUOC_HIEN_HANHN_HANH(this.axios).then(response => {
        if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
          this.kyhoadon = response.data.data[0].KYCUOC.toString()
        }
      })
    },
    async SP_CAPNHAT_THONGTIN_MIG (tucthi, kyhoadon, dscapnhat) {
      let result = false
      let req = {
        vnguoidung_id: Number.parseInt(this.$root.token.getNguoiDungID()),
        vtucthi: tucthi,
        vkyhoadon: kyhoadon,
        p_ds_capnhat: JSON.stringify(dscapnhat)
      }
      await UpdateCodeInformationAPI.SP_CAPNHAT_THONGTIN_MIG(this.axios, req).then(response => {
        if (response && response.data && response.data.error_code === 'BSS-00000000') {
          result = true
        } else {
          result = false
          this.$root.$toast.error('Có lỗi: ' + response.data.message_detail)
        }
      }).catch(error => {
        this.$root.$toast.error('Có lỗi: ' + error.data.message_detail)
      })
      return result
    },
    async XOA_DS_MIG_TMP (nguoidungid) {
      let req = {
        p_nguoidung_id: nguoidungid
      }
      await UpdateCodeInformationAPI.XOA_DS_MIG_TMP(this.axios, req)
    },
    tsbtnXoa_Click () {
      if (this.dsMaInGhep.list == null || this.dsMaInGhep.list.length <= 0) {
        this.$root.$toast.warning('Chưa có thông tin mã in ghép để Xóa!')
        return
      }

      this.$confirm('Bạn thật sự muốn xóa mã in ghép không ?', 'Thông báo', {
        confirmButtonText: 'Có',
        cancelButtonText: 'Không'
      }).then(async () => {
        console.log(this.dsMaTTChuaGan.list)
        let dsttcg = this.dsMaTTChuaGan.list.map(item => {
          return item.thanhtoan_id
        })
        let dsttdg = this.dsMaTTDaGan.list.map(item => {
          return item.thanhtoan_id
        })
        let deleted = await this.XOA_DANHBA_MIG(this.mig_id, this.kyhoadon, dsttcg, dsttdg, this.tucthi)
        if (deleted) {
          await this.HienThiDS()
          this.main_ghep = ''
          this.$root.$toast.success('Xóa dữ liệu thành công!')
        }
      })
    },
    async XOA_DANHBA_MIG (migid, kyhoadon, dsttcg, dsttdg, tucthi) {
      let result = false
      let req = {
        p_mig_id: migid,
        p_kyhoadon: kyhoadon,
        p_ds_tt_cg: JSON.stringify(dsttcg),
        p_ds_tt_dg: JSON.stringify(dsttdg),
        p_tucthi: tucthi
      }
      await UpdateCodeInformationAPI.XOA_DANHBA_MIG(this.axios, req).then(response => {
        if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
          if (response.data == '1') {
            result = true
          } else {
            result = false
            this.$root.$toast.error('Không xóa được mã in ghép')
          }
        } else {
          this.$root.$toast.error('Xóa mã in ghép thất bại: ' + response.data.message)
        }
      }).catch(error => {
        console.log(error)
        this.$root.$toast.error('Có lỗi: ' + error.data.message)
      })
      return result
    },
    onActionClick (action) {
      if (action.id === 'tsbtnNhapMoi') {
        this.tsbtnNhapMoi_Click()
      } else if (action.id === 'tsbtnCapNhat') {
        this.tsbtnCapNhat_Click()
      } else if (action.id === 'tsbtnXoa') {
        this.tsbtnXoa_Click()
      }
    },
    clear () {
      this.dsMaInGhep.selectItem = null
      this.vloaikh_id = 0
      this.formThongTinTaoMaGhep.txtDiaChiIG = ''
      this.formThongTinTaoMaGhep.txtMaIG = ''
      this.formThongTinTaoMaGhep.txtMaTuyen = ''
      this.formThongTinTaoMaGhep.txtMaNVTC = ''
      this.diachiig_id = 0
      this.dacdiemig_id = 0
      this.phoig_id = 0
      this.apig_id = 0
      this.khuig_id = 0
      this.phuongig_id = 0
      this.quanig_id = 0
      this.tinhig_id = 0
      this.sonhaig = ''
      this.dsMaTTDaGan.list = null
      this.dsMaTTChuaGan.list = null
      this.dsMaInGhep.list = null
      this.$refs.txtMaKH.focus()
      this.ds_cg = []
      this.ds_dg = []
    },
    gan () {
      if (this.dsMaTTChuaGan.checked.length === 0) {
        return
      }
      this.dsMaTTChuaGan.checked.forEach((item) => {
        this.dsMaTTDaGan.list.push(item)
        const index = this.dsMaTTChuaGan.list.findIndex(x => x.ma_tt === item.ma_tt)
        if (index > -1) {
          this.dsMaTTChuaGan.list.splice(index, 1)
        }
      })
    },
    boGan () {
      if (this.dsMaTTDaGan.checked.length === 0) {
        return
      }
      this.dsMaTTDaGan.checked.forEach((item) => {
        this.dsMaTTChuaGan.list.push(item)
        const index = this.dsMaTTDaGan.list.findIndex(x => x.ma_tt === item.ma_tt)
        if (index > -1) {
          this.dsMaTTDaGan.list.splice(index, 1)
        }
      })
      this.dsMaTTDaGan.checked = []
    },
    ganAll () {
      if (this.dsMaTTChuaGan.list.length === 0) {
        return
      }
      this.dsMaTTChuaGan.list.forEach(item => {
        this.dsMaTTDaGan.list.push(item)
      })
      this.$refs.gridDsMaTTChuaGan.grid.clearSelection()
      this.dsMaTTChuaGan.list = []
      this.dsMaTTChuaGan.checked = []
    },
    boGanAll () {
      if (this.dsMaTTDaGan.list.length === 0) {
        return
      }
      this.dsMaTTDaGan.list.forEach(item => {
        this.dsMaTTChuaGan.list.push(item)
      })
      this.dsMaTTDaGan.list = []
      this.dsMaTTDaGan.checked = []
    },
    gridDsMaTTDaGanChecked (items) {
      if (items != null) {
        this.dsMaTTDaGan.checked = items
      }
    },
    gridDsMaTTChuaGanChecked (items) {
      if (items != null) {
        this.dsMaTTChuaGan.checked = items
      }
    }
  }
}
</script>

<style scoped>
.text-view {
  background-color: white !important;
}
</style>
