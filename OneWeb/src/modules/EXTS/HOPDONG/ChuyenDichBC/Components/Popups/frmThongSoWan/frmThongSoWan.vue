<template src="./frmThongSoWan.html"></template>
<script>
import api2 from './PopUpApi.js'
import api from './Api.js'
import moment from 'moment'
import { LoaiHinhTB, KieuLapDat, LoaiHopDong } from '@/const/enums/index.js'
export default {
  name: 'frmThongSoWan',
  components: {
    api,
    api2,
    moment
  },
  data () {
    return {
      Loading: false,
      position: { X: 'center', Y: 'top' },
      animationSettings: { effect: 'Zoom' },
      kieuldid: null,
      thuebaoid: null,
      p_tinh: null,
      loaitb: null,
      kt_load: null,
      tinh: null,
      _vloaihd: null,
      _vdichvu_vt: null,
      ts_kt_tthdkhdn: 0,
      kenh_trang: false,
      Capnhat: null,
      khachhang_id: 0,
      disabled: true,
      hdkh_id: 0,
      ThongsoBan: {},
      ThongsoTc: {},
      cboPhanLoaiMGW: '',
      cboPhanTachTD: '',
      cboPhanTachTDList: [],
      cboChuQuan_Ban: '',
      cboTrangBiBan: '',
      cboTocDoBan: '',
      chkTocDo_PirBan: false,
      cboTocDo_PirBan: '',
      cboLoaiKenhBan: '',
      chkTocDo_NIX: false,
      cboTocDo_NIXBan: '',
      cboLoaiModemBan: '',
      chkTocDo_ISP: false,
      cboTocDo_ISPBan: '',
      cboLoaiTBiBan: '',
      cboThoiHanBan: '',
      cboDoiTuongBan: '',
      cboMucCuocTBBan: '',
      cboChuQuanLD_Ban: '',
      cboPhanLoaiMGWList: [],
      cboPhanTachTDLisy: [],
      cboChuQuan_BanList: [],
      cboChuQuan_TcList: [],
      cboTrangBiBanList: [],
      cboTrangBiTCList: [],
      cboTocDoBanList: [],
      cboTocDoTCList: [],
      cboTocDo_PirBanList: [],
      cboTocDo_PirTCList: [],
      cboLoaiKenhBanList: [],
      cboTocDo_NIXBanList: [],
      cboTocDo_NIXTCList: [],
      cboLoaiModemBanList: [],
      cboLoaiModemTCList: [],
      cboTocDo_ISPBanList: [],
      cboTocDo_ISPTCList: [],
      cboLoaiTBiBanList: [],
      cboThoiHanBanList: [],
      cboDoiTuongBanList: [],
      cboDoiTuongTCList: [],
      cboMucCuocTBBanList: [],
      cboMucCuocTBTCList: [],
      cboChuQuanLD_BanList: [],
      cboLoaiTBiTCList: [],
      cboChuQuanLD_TCList: [],
      cboKieuLD: '',
      cboKieuLDList: [],
      txtMATNTC: '',
      cboChuQuan_TC: '',
      cboTrangBiTc: '',
      cboTocDoTc: '',
      cboTocDo_PirTc: '',
      cboLoaiKenhTc: '',
      cboTocDo_NIXTc: '',
      cboLoaiModemTc: '',
      cboTocDo_ISPTc: '',
      cboLoaiTBiTc: '',
      cboThoiHanTc: '',
      cboDoiTuongTC: '',
      cboMucCuocTBTC: '',
      cboChuQuanLD_TC: '',
      cboMHKN_BAN: '',
      cboMHKN_BANList: [],
      txtSo_AS_BAN: '',
      cboTocDoPOP_BAN: '',
      cboTocDoPOP_BANList: [],
      cboTocDoPOP_TCList: [],
      txtMTU_Size_BAN: '',
      cboGiaoDienKH_BAN: '',
      cboGiaoDienKH_BANList: [],
      cboGiaoDienPOP_BAN: '',
      cboGiaoDienPOP_BANList: [],
      cboSpeed_Duplex_BAN: '',
      cboSpeed_Duplex_BANList: [],
      txtHqos_BAN: '',
      cboCauHinhDT_BAN: '',
      cboCauHinhDT_BANList: [],
      txtTenTrunkNNI_BAN: '',
      cboNT_DV_BAN: '',
      cboNT_DV_BANList: [],
      txtIP_WAN_BAN: '',
      cboLoaiIP_BAN: '',
      cboLoaiIP_BANList: [],
      txtSVLAN_BAN: '',
      txtSL_IP_BAN: '',
      txtIP_LAN_BAN: '',
      cboKieuKN_BAN: '',
      cboKieuKN_BANList: [],
      txtIP_CE_BAN: '',
      txtVLAN_Trans_BAN: '',
      txtThongTinIP_BAN: '',
      txtPort_POP_BAN: '',
      txtCLANID_BAN: '',
      txtDiemPOP_BAN: '',
      cboMHKN_TC: '',
      txtSo_AS_TC: '',
      cboTocDoPOP_TC: '',
      txtMTU_Size_TC: '',
      cboGiaoDienKH_TC: '',
      cboGiaoDienPOP_TC: '',
      cboSpeed_Duplex_TC: '',
      txtHqos_TC: '',
      cboCauHinhDT_TC: '',
      txtCos_TC: '',
      txtTenTrunkNNI_TC: '',
      cboNT_DV_TC: '',
      txtSVLAN_TC: '',
      txtSL_IP_TC: '',
      txtIP_WAN_TC: '',
      cboLoaiIP_TC: '',
      txtIP_LAN_TC: '',
      cboKieuKN_TC: '',
      txtIP_CE_TC: '',
      txtVLAN_Trans_TC: '',
      txtThongTinIP_TC: '',
      txtPort_POP_TC: '',
      txtCos_BAN: '',
      txtCLANID_TC: '',
      txtDiemPOP_TC: '',
      label38: 'Tốc độ CIR',
      label21: 'Tốc độ CIR',
      chkTocDo_ISP_Enabled: true,
      chkTocDo_NIX_Enabled: true,
      cboTocDoTc_Enabled: true,
      cboPhanLoaiMGW_Enabled: false,
      cboPhanTachTD_Enabled: false,
      cboTocDo_PirTc_Enabled: false,
      cboTocDo_NIXTc_Enabled: false,
      cboTocDo_ISPTc_Enabled: false,
      cboThoiHanTc_Enabled: true,
      cboChuQuan_TC_Enabled: false,
      cboChuQuanLD_TC_Enabled: true,
      cboLoaiTBiTc_Enabled: true,
      cboTrangBiTc_Enabled: true,
      cboLoaiModemTc_Enabled: true,
      cboLoaiModemBan_Enabled: true,
      cboLoaiKenhTc_Enabled: true,
      cboLoaiKenhBan_Enabled: true,
      cboDoiTuongBan_Enabled: false,
      cboLoaiTBiBan_Enabled: true,
      cboThoiHanBan_Enabled: true,
      cboTocDo_ISPBan_Enabled: true,
      cboTrangBiBan_Enabled: true,
      cboTocDo_PirBan_Enabled: true,
      cboChuQuan_Ban_Enabled: true,
      cboTocDo_NIXBan_Enabled: false,
      cboThoiHanBanList_Enabled: true,
      cboDoiTuongBanList_Enabled: true,
      cboMucCuocTBBanList_Enabled: true,
      cboChuQuanLD_Ban_Enabled: true,
      cboDoiTuongTC_Enabled: true,
      cboTocDoBan_Enabled: true,
      cboMucCuocTBTC_Enabled: true,
      cboKieuLD_Enabled: true,
      panelControl1_Visible: false,
      panelControl2_Visible: false,
      txtMATNTC_Enabled: false,
      nothing: true,
      ds_cq_ban: [],
      ds_cq_tc: []
    }
  },
  created () {},
  mounted () {},
  methods: {
    async frmThongSoWan_Load () {
      try {
        console.log('🚀 ~ file: frmThongSoWan.vue:227 ~ frmThongSoWan_Load ~ this._vloaihd:', this._vloaihd)
        console.log('🚀 ~ file: frmThongSoWan.vue:228 ~ frmThongSoWan_Load ~ this.kenh_trang:', this.kenh_trang)
        if (this.loaitb === LoaiHinhTB.MGW_INTERNET_TRUCTIEP) {
          this.label38 = 'Tốc độ tổng'
          this.label21 = 'Tốc độ tổng'
          if (this.ThongsoBan != null && this.ThongsoBan.phanloai_id > 0) { this.cboPhanLoaiMGW = this.ThongsoBan.phanloai_id } else {
            this.cboPhanLoaiMGW = -1
          }
          if (this.ThongsoBan != null && this.ThongsoBan.phantach_id > 0) {
            this.cboPhanTachTD = this.ThongsoBan.phantach_id
          } else {
            this.cboPhanTachTD = -1
          }
          console.log('🚀 ~ file: frmThongSoWan.vue:209 ~ frmThongSoWan_Load ~ this.ThongsoBan:', this.ThongsoBan)
          console.log('🚀 ~ file: frmThongSoWan.vue:209 ~ frmThongSoWan_Load ~ this.cboPhanTachTD:', this.cboPhanTachTD)
          console.log('🚀 ~ file: frmThongSoWan.vue:210 ~ frmThongSoWan_Load ~ this.cboPhanTachTDList:', this.cboPhanTachTDList)
          if (this.cboPhanTachTD == this.cboPhanTachTDList[0].id) {
            this.chkTocDo_ISP_Enabled = true
            this.chkTocDo_NIX_Enabled = true
            this.chkTocDo_ISP = true
            this.chkTocDo_NIX = true
          } else {
            this.chkTocDo_ISP_Enabled = false
            this.chkTocDo_NIX_Enabled = false
            this.chkTocDo_ISP = false
            this.chkTocDo_NIX = false
          }
        } else {
          this.label38 = 'Tốc độ CIR'
          this.label21 = 'Tốc độ CIR'
          this.cboPhanLoaiMGW_Enabled = false
          this.cboPhanTachTD_Enabled = false
        }
        if ((this._vloaihd === LoaiHopDong.CHUYEN_QUYEN || this._vloaihd === LoaiHopDong.DI_CHUYEN) && this.kenh_trang === false) {
          this.cboDoiTuongBan_Enabled = false
          this.cboLoaiKenhBan_Enabled = false
          this.cboLoaiModemBan_Enabled = false
          this.cboLoaiTBiBan_Enabled = false
          this.cboMucCuocTBBan_Enabled = false
          this.cboThoiHanBan_Enabled = false
          this.cboTocDo_ISPBan_Enabled = false
          this.cboTocDo_NIXBan_Enabled = false
          this.cboTocDo_PirBan_Enabled = false
          this.cboTocDoBan_Enabled = false

          this.cboDoiTuongTC_Enabled = false
          this.cboLoaiKenhTc_Enabled = false
          this.cboLoaiModemTc_Enabled = false
          this.cboLoaiTBiTc_Enabled = false
          this.cboMucCuocTBTC_Enabled = false
          this.cboThoiHanTc_Enabled = false
          this.cboTocDo_ISPTc_Enabled = false
          this.cboTocDo_NIXTc_Enabled = false
          this.cboTocDo_PirTc_Enabled = false
          this.cboTocDoTc_Enabled = false
        }
        if (this._vloaihd === LoaiHopDong.DI_CHUYEN) {
          try {
            console.log('🚀 ~ file: frmThongSoWan.vue:252 ~ frmThongSoWan_Load ~ this.cboTocDoBan:', this.cboTocDoBan)
            let _cboTocDoBan = this.cboTocDoBanList.filter((x) => x.id == this.cboTocDoBan)[0]
            let text_cboTocDoBan = _cboTocDoBan.text
            console.log('🚀 ~ file: frmThongSoWan.vue:257 ~ frmThongSoWan_Load ~ text_cboTocDoBan:', text_cboTocDoBan)
            let _cboTocDoTC = this.cboTocDoTCList.filter((x) => x.text == text_cboTocDoBan)[0]
            this.cboTocDoTc = _cboTocDoTC.id
            console.log('🚀 ~ file: frmThongSoWan.vue:260 ~ frmThongSoWan_Load ~ this.cboTocDoTc:', this.cboTocDoTc)
          } catch (err) {
            console.log('🚀 ~ file: frmThongSoWan.vue:253 ~ frmThongSoWan_Load ~ err:', err)
          }
        }
      } catch (err) {
        console.log('🚀frmThongSoWan_Load ~ err', err)
      }
      this.kt_load = false
    },
    async loadData () {
      try {
        await Promise.all([
          this.getcboTocDoBanList(),
          this.getcboTocDoTCList(),
          this.getcboPhanLoaiMGWList(),
          this.getcboChuQuan_BanList(),
          this.getcboTrangBiBanList(),
          this.getcboTrangBiTCList(),
          this.getcboLoaiKenhBanList(),
          this.getcboLoaiKenhTCList(),
          this.getcboLoaiModemBanList(),
          this.getcboLoaiModemTCList(),
          this.thoihan(),
          this.sp_loadkieu_ld(),
          this.sp_ds_phantach_tocdo(),
          this.getcboLoaiTBiBanList(),
          this.getcboLoaiTBiTCList(),
          this.sp_lay_doituong_tb(),
          this.sp_lay_mohinh_kn(),
          this.sp_lay_giaodien_kn(),
          this.sp_lay_kieu_duplex(),
          this.sp_lay_cauhinh_dt(),
          this.sp_lay_kieu_nghiemthu(),
          this.sp_lay_loai_ip(),
          this.sp_lay_kieu_kn(),
          this.sp_ds_chuquan(),
          this.get_ts_kt_tthdkhdn()
        ])
          .then((x) => {})
          .finally(() => {
            this.kt_load = false
          })
      } catch (err) {
        console.log('🚀 ~ file: frmThongSoWan.vue:297 ~ loadData ~ err:', err)
      }
      await this.frmThongSoWan_Load()
    },
    SetValues () {
      try {
        console.log('🚀 ~ file: frmThongSoWan.vue:305 ~ SetValues ~ this.ThongsoBan:', this.ThongsoBan)
        if (this.ThongsoBan == null) {
          return
        } else {
          if (this.ThongsoBan.loaikenh_id != null && this.ThongsoBan.loaikenh_id > 0) this.cboLoaiKenhBan = this.ThongsoBan.loaikenh_id
          if (this.ThongsoBan.loaimd_id != null && this.ThongsoBan.loaimd_id > 0) this.cboLoaiModemBan = this.ThongsoBan.loaimd_id

          if (this.ThongsoBan.tocdo_id != null && this.ThongsoBan.tocdo_id > 0) this.cboTocDoBan = this.ThongsoBan.tocdo_id

          if (this.ThongsoBan.tocdo_pir_id != null && this.ThongsoBan.tocdo_pir_id > 0) {
            this.chkTocDo_PIR = true
            this.cboTocDo_PirBan = this.ThongsoBan.tocdo_pir_id
          }
          if (this.ThongsoBan.tocdo_nix != null && this.ThongsoBan.tocdo_nix > 0) {
            this.chkTocDo_NIX = true
            this.cboTocDo_NIXBan = this.ThongsoBan.tocdo_nix
          }
          if (this.ThongsoBan.tocdo_isp != null && this.ThongsoBan.tocdo_isp > 0) {
            this.chkTocDo_ISP = true
            this.cboTocDo_ISPBan = this.ThongsoBan.tocdo_isp
          }

          if (this.ThongsoBan.trangbi_id != null && this.ThongsoBan.trangbi_id > 0) this.cboTrangBiBan = this.ThongsoBan.trangbi_id

          if (this.ThongsoBan.thietbidc_id != null && this.ThongsoBan.thietbidc_id > 0) this.cboLoaiTBiBan = this.ThongsoBan.thietbidc_id

          if (this.ThongsoBan.thoihan_id != null && this.ThongsoBan.thoihan_id > 0) this.cboThoiHanBan = this.ThongsoBan.thoihan_id

          if (this.ThongsoBan.doituong_id != null && this.ThongsoBan.doituong_id > 0) this.cboDoiTuongBan = this.ThongsoBan.doituong_id

          if (this.ThongsoBan.muccuoctb_id != null && this.ThongsoBan.muccuoctb_id > 0) {
            this.cboMucCuocTBBan = this.ThongsoBan.muccuoctb_id
          }
        }
      } catch (errThongsoBan) {
        console.log('🚀 ~ file: frmThongSoWan.vue ~ line 310 ~ SetValues ~ errThongsoBan', errThongsoBan)
      }
      try {
        console.log('🚀 ~ file: frmThongSoWan.vue:343 ~ SetValues ~ this.ThongsoTc:', this.ThongsoTc)
        if (this.ThongsoTc == null) {
          return
        } else {
          if (this.ThongsoTc.tocdo_id != null && this.ThongsoTc.tocdo_id > 0) this.cboTocDoTc = this.ThongsoTc.tocdo_id

          if (this.ThongsoTc.tocdo_pir_id != null && this.ThongsoTc.tocdo_pir_id > 0) this.cboTocDo_PirTc = this.ThongsoTc.tocdo_pir_id

          if (this.ThongsoTc.tocdo_nix != null && this.ThongsoTc.tocdo_nix > 0) this.cboTocDo_NIXTc = this.ThongsoTc.tocdo_nix

          if (this.ThongsoTc.tocdo_isp != null && this.ThongsoTc.tocdo_isp > 0) this.cboTocDo_ISPTc = this.ThongsoTc.tocdo_isp

          if (this.ThongsoTc.kieuld_id != null && this.ThongsoTc.kieuld_id > 0) {
            let _f = this.cboKieuLDList.filter(x => x.id == this.ThongsoTc.kieuld_id)
            console.log('🚀 ~ file: frmThongSoWan.vue:359 ~ SetValues ~ _f:', _f)
            if (_f.length > 0) {
              this.cboKieuLD = this.ThongsoTc.kieuld_id
            } else {
              // this.cboKieuLD = null
            }
          }

          if (this.ThongsoTc.doituong_id != null && this.ThongsoTc.doituong_id > 0) this.cboDoiTuongTC = this.ThongsoTc.doituong_id

          if (this.ThongsoTc.loaikenh_id != null && this.ThongsoTc.loaikenh_id > 0) this.cboLoaiKenhTc = this.ThongsoTc.loaikenh_id

          if (this.ThongsoTc.loaimd_id != null && this.ThongsoTc.loaimd_id > 0) this.cboLoaiModemTc = this.ThongsoTc.loaimd_id

          if (this.ThongsoTc.trangbi_id != null && this.ThongsoTc.trangbi_id > 0) this.cboTrangBiTc = this.ThongsoTc.trangbi_id

          if (this.ThongsoTc.thietbidc_id > 0) this.cboLoaiTBiTc = this.ThongsoTc.thietbidc_id

          if (this.ThongsoTc.mhkn_id != null && this.ThongsoTc.mhkn_id > 0) this.cboMHKN_TC = this.ThongsoTc.mhkn_id

          if (this.ThongsoBan.mhkn_id != null && this.ThongsoBan.mhkn_id > 0) this.cboMHKN_BAN = this.ThongsoBan.mhkn_id

          if (this.ThongsoBan.tocdo_pop != null && this.ThongsoBan.tocdo_pop > 0) this.cboTocDoPOP_BAN = this.ThongsoBan.tocdo_pop

          if (this.ThongsoTc.tocdo_pop != null && this.ThongsoTc.tocdo_pop > 0) this.cboTocDoPOP_TC = this.ThongsoTc.tocdo_pop

          if (this.ThongsoBan.gdkn_id != null && this.ThongsoBan.gdkn_id > 0) this.cboGiaoDienKH_BAN = this.ThongsoBan.gdkn_id

          if (this.ThongsoTc.gdkn_id != null && this.ThongsoTc.gdkn_id > 0) this.cboGiaoDienKH_TC = this.ThongsoTc.gdkn_id

          if (this.ThongsoBan.kieu_duplex_id != null && this.ThongsoBan.kieu_duplex_id > 0) this.cboSpeed_Duplex_BAN = this.ThongsoBan.kieu_duplex_id

          if (this.ThongsoTc.kieu_duplex_id != null && this.ThongsoTc.kieu_duplex_id > 0) this.cboSpeed_Duplex_TC = this.ThongsoTc.kieu_duplex_id

          if (this.ThongsoBan.cauhinh_dt_id != null && this.ThongsoBan.cauhinh_dt_id > 0) this.cboCauHinhDT_BAN = this.ThongsoBan.cauhinh_dt_id

          if (this.ThongsoTc.cauhinh_dt_id != null && this.ThongsoTc.cauhinh_dt_id > 0) this.cboCauHinhDT_TC = this.ThongsoTc.cauhinh_dt_id

          if (this.ThongsoBan.gdkn_pop_id != null && this.ThongsoBan.gdkn_pop_id > 0) this.cboGiaoDienPOP_BAN = this.ThongsoBan.gdkn_pop_id

          if (this.ThongsoTc.gdkn_pop_id != null && this.ThongsoTc.gdkn_pop_id > 0) this.cboGiaoDienPOP_TC = this.ThongsoTc.gdkn_pop_id

          if (this.ThongsoBan.kieukn_id != null && this.ThongsoBan.kieukn_id > 0) this.cboKieuKN_BAN = this.ThongsoBan.kieukn_id

          if (this.ThongsoTc.kieukn_id != null && this.ThongsoTc.kieukn_id > 0) this.cboKieuKN_TC = this.ThongsoTc.kieukn_id

          if (this.ThongsoTc.thoihan_id != null && this.ThongsoTc.thoihan_id > 0) this.cboThoiHanTc = this.ThongsoTc.thoihan_id

          if (this.ThongsoTc.muccuoctb_id != null && this.ThongsoTc.muccuoctb_id > 0) this.cboMucCuocTBTC = this.ThongsoTc.muccuoctb_id
        }
      } catch (errThongsoTc) {
        console.log('🚀 ~ file: frmThongSoWan.vue ~ line 367 ~ SetValues ~ errThongsoTc', errThongsoTc)
      }

      try {
        this.txtTenTrunkNNI_BAN = this.ThongsoBan.ten_trunk_nni
        this.txtTenTrunkNNI_TC = this.ThongsoTc.ten_trunk_nni
        this.txtSVLAN_BAN = this.ThongsoBan.svlan
        this.txtSVLAN_TC = this.ThongsoTc.svlan

        this.txtIP_LAN_BAN = this.ThongsoBan.lan_ip
        this.txtIP_LAN_TC = this.ThongsoTc.lan_ip

        this.txtIP_WAN_BAN = this.ThongsoBan.wan_ip
        this.txtIP_WAN_TC = this.ThongsoTc.wan_ip

        this.txtIP_CE_BAN = this.ThongsoBan.ce_ip
        this.txtIP_CE_TC = this.ThongsoTc.ce_ip

        this.txtThongTinIP_BAN = this.ThongsoBan.tt_ip
        this.txtThongTinIP_TC = this.ThongsoTc.tt_ip

        this.txtCLANID_BAN = this.ThongsoBan.cvlan
        this.txtCLANID_TC = this.ThongsoTc.cvlan

        this.txtDiemPOP_BAN = this.ThongsoBan.diem_pop
        this.txtDiemPOP_TC = this.ThongsoTc.diem_pop

        this.txtSo_AS_BAN = this.ThongsoBan.so_as
        this.txtSo_AS_TC = this.ThongsoTc.so_as

        this.txtMTU_Size_BAN = this.ThongsoBan.mtu_size
        this.txtMTU_Size_TC = this.ThongsoTc.mtu_size

        this.txtHqos_BAN = this.ThongsoBan.hqos
        this.txtHqos_TC = this.ThongsoTc.hqos

        this.txtCos_BAN = this.ThongsoBan.cos
        this.txtCos_TC = this.ThongsoTc.cos

        this.txtSL_IP_BAN = this.ThongsoBan.sl_ip
        this.txtSL_IP_TC = this.ThongsoTc.sl_ip

        this.txtVLAN_Trans_BAN = this.ThongsoBan.vlan_transparent
        this.txtVLAN_Trans_TC = this.ThongsoTc.vlan_transparent

        this.txtPort_POP_BAN = this.ThongsoBan.tt_port
        this.txtPort_POP_TC = this.ThongsoTc.tt_port
      } catch (err) {
        console.log('🚀 ~ file: frmThongSoWan.vue ~ line 405 ~ SetValues ~ err', err)
      }
      if ((this._vloaihd === LoaiHopDong.CHUYEN_QUYEN || this._vloaihd === LoaiHopDong.DI_CHUYEN) && this.kenh_trang === false) {
        this.cboDoiTuongBan_Enabled = false
        this.cboLoaiKenhBan_Enabled = false
        this.cboLoaiModemBan_Enabled = false
        this.cboLoaiTBiBan_Enabled = false
        this.cboMucCuocTBBan_Enabled = false
        this.cboThoiHanBan_Enabled = false
        this.cboTocDo_ISPBan_Enabled = false
        this.cboTocDo_NIXBan_Enabled = false
        this.cboTocDo_PirBan_Enabled = false
        this.cboTocDoBan_Enabled = false

        this.cboDoiTuongTC_Enabled = false
        this.cboLoaiKenhTc_Enabled = false
        this.cboLoaiModemTc_Enabled = false
        this.cboLoaiTBiTc_Enabled = false
        this.cboMucCuocTBTC_Enabled = false
        this.cboThoiHanTc_Enabled = false
        this.cboTocDo_ISPTc_Enabled = false
        this.cboTocDo_NIXTc_Enabled = false
        this.cboTocDo_PirTc_Enabled = false
        this.cboTocDoTc_Enabled = false
      }
      if (this._vloaihd === LoaiHopDong.DI_CHUYEN) {
        try {
          console.log('🚀 ~ file: frmThongSoWan.vue:252 ~ frmThongSoWan_Load ~ this.cboTocDoBan:', this.cboTocDoBan)
          let _cboTocDoBan = this.cboTocDoBanList.filter((x) => x.id == this.cboTocDoBan)[0]
          let text_cboTocDoBan = _cboTocDoBan.text
          console.log('🚀 ~ file: frmThongSoWan.vue:257 ~ frmThongSoWan_Load ~ text_cboTocDoBan:', text_cboTocDoBan)
          let _cboTocDoTC = this.cboTocDoTCList.filter((x) => x.text == text_cboTocDoBan)[0]
          this.cboTocDoTc = _cboTocDoTC.id
          console.log('🚀 ~ file: frmThongSoWan.vue:260 ~ frmThongSoWan_Load ~ this.cboTocDoTc:', this.cboTocDoTc)
        } catch (err) {
          console.log('🚀 ~ file: frmThongSoWan.vue:253 ~ frmThongSoWan_Load ~ err:', err)
        }
      }
    },
    async openDialog1 (p_tinh, ploaitb_ld, p_loaihinhtb, vloaihd, vdichvu_vt, tsBan, tsTc) {
      console.log('openDialog1:', 'p_tinh:', p_tinh, 'ploaitb_ld:', ploaitb_ld, 'p_loaihinhtb:', p_loaihinhtb, 'vloaihd:', vloaihd, 'vdichvu_vt:', vdichvu_vt, 'tsBan', tsBan, 'tsTc', tsTc)
      try {
        this.loading(true)
        this.kt_load = true
        this.tinh = p_tinh
        this.loaitb = p_loaihinhtb
        this._vloaihd = vloaihd
        this._vdichvu_vt = vdichvu_vt
        if (tsBan != null) this.ThongsoBan = tsBan
        else this.ThongsoBan = {}
        if (tsTc != null) this.ThongsoTc = tsTc
        else this.ThongsoTc = {}
        await this.loadData()
        await this.SetValues()
        // Bổ sung vào dây chạy xong hết rùi mới chạy hàm lấy mức cước tb
        await this.lay_ds_mucuoc_tb_v2()
        await this.lay_ds_mucuoc_tb_v2_tinh()
      } catch (err) {
        console.log('🚀 ~ file: frmThongSoWan.vue ~ line 987 ~ openDialog1 ~ err', err)
      } finally {
        this.loading(false)
        this.$refs.dialogThongSoWan.show()
      }
    },
    async openDialog2 (p_tinh, ploaitb_ld, p_loaihinhtb, vloaihd, vdichvu_vt, _kenh_trang, tsBan, tsTc) {
      console.log('openDialog2:', 'p_tinh:', p_tinh, 'ploaitb_ld:', ploaitb_ld, 'p_loaihinhtb:', p_loaihinhtb, 'vloaihd:', vloaihd, 'vdichvu_vt:', vdichvu_vt, '_kenh_trang', _kenh_trang, 'tsBan', tsBan, 'tsTc', tsTc)
      try {
        this.loading(true)
        this.kt_load = true
        this.tinh = p_tinh
        this.loaitb = p_loaihinhtb
        this._vloaihd = vloaihd
        this._vdichvu_vt = vdichvu_vt
        this.kenh_trang = _kenh_trang
        if (tsBan != null) this.ThongsoBan = tsBan
        else this.ThongsoBan = {}
        if (tsTc != null) this.ThongsoTc = tsTc
        else this.ThongsoTc = {}
        await this.loadData()
        await this.SetValues()
        // Bổ sung vào dây chạy xong hết rùi mới chạy hàm lấy mức cước tb\
        await this.lay_ds_mucuoc_tb_v2()
        await this.lay_ds_mucuoc_tb_v2_tinh()
      } catch (err) {
        console.log('🚀 ~ file: frmThongSoWan.vue ~ line 1005 ~ openDialog2 ~ err', err)
      } finally {
        this.loading(false)
        this.$refs.dialogThongSoWan.show()
      }
    },
    closeDialog () {},
    kiemTraCapNhat () {
      if (this.cboKieuLDList == null || this.cboKieuLDList.length == 0) {
        this.ShowError('Không tìm thấy kiểu lắp đặt ở bên thi công ứng với loại hợp đồng và loại thuê bao này.')
        return false
      }
      if (this.cboKieuLD == null) {
        this.ShowError('Bạn phải chọn kiểu yêu cầu.')
        return false
      }
      if (this.cboTrangBiBan == null || this.cboTrangBiTc == null) {
        this.ShowError('Bạn phải chọn trang bị cho 2 đơn vị')
        return false
      }

      if (this.cboMucCuocTBBan == null || this.cboMucCuocTBTC == null) {
        this.ShowError('Bạn phải chọn mức cước  thuê bao cho 2 đơn vị')
        return false
      }

      if (this.cboLoaiKenhBan == null || this.cboLoaiModemBan == null || this.cboLoaiTBiBan == null || this.cboDoiTuongBan == null) {
        this.ShowError('Bạn nhập thiếu thông tin bên bán !')
        return false
      }

      if (this.cboLoaiKenhTc == null || this.cboLoaiModemTc == null || this.cboLoaiTBiTc == null || this.cboDoiTuongTC == null) {
        this.ShowError('Bạn nhập thiếu thông tin bên thi công !')
        return false
      }

      if (this.cboTocDoBan !== this.cboTocDoTc && this.cboTocDoTc_Enabled) {
        let val1 = this.cboTocDoBan
        let val2 = this.cboTocDoTc
        let a = this.cboTocDoBanList.filter((x) => x.id == val1)[0]
        let b = this.cboTocDoTCList.filter((x) => x.id == val2)[0]
        if (a.text !== b.text) {
          this.ShowError('Tốc độ thi công và bán không bằng nhau.')
          return false
        }
      }

      if (this.cboTocDo_ISPBan !== this.cboTocDo_ISPTc && this.chkTocDo_ISP) {
        let val1 = this.cboTocDo_ISPBan
        let val2 = this.cboTocDo_ISPTc
        let a = this.cboTocDo_ISPBanList.filter((x) => x.id == val1)[0]
        let b = this.cboTocDo_ISPTCList.filter((x) => x.id == val2)[0]
        if (a.text !== b.text) {
          this.ShowError('Tốc độ ISP thi công và bán không bằng nhau. ')
          return false
        }
      }

      if (this.cboTocDo_NIXBan !== this.cboTocDo_NIXTc && this.chkTocDo_NIX) {
        let val1 = this.cboTocDo_NIXBan
        let val2 = this.cboTocDo_NIXTc
        let a = this.cboTocDo_NIXBanList.filter((x) => x.id == val1)[0]
        let b = this.cboTocDo_NIXTCList.filter((x) => x.id == val2)[0]
        if (a.text !== b.text) {
          this.ShowError('Tốc độ NIX thi công và bán không bằng nhau.')
          return false
        }
      }
      if (this.cboTocDo_PirBan !== this.cboTocDo_PirTc && this.chkTocDo_PirBan) {
        let val1 = this.cboTocDo_PirBan
        let val2 = this.cboTocDo_PirTc
        let a = this.cboTocDo_PirBanList.filter((x) => x.id == val1)[0]
        let b = this.cboTocDo_PirTCList.filter((x) => x.id == val2)[0]
        if (a.text !== b.text) {
          this.ShowError('Tốc độ Pir thi công và bán không bằng nhau.')
          return false
        }
      }
      return true
    },
    select2FocusById (id) {
      try {
        $(`#${id}`).select2('focus')
      } catch (err) {
        console.log('🚀 select2FocusById ~ err', err)
      }
    },
    async tsbtnCapNhat_Click () {
      try {
        this.loading(true)
        let dt_kieu_nt_ban = []
        let dt_kieu_nt_tc = []
        let dt_loai_ip_tc = []
        let dt_loai_ip_ban = []
        if (!this.kiemTraCapNhat()) return
        if (this.loaitb == LoaiHinhTB.MGW_INTERNET_TRUCTIEP) {
          if (parseInt(this.cboPhanTachTD) == 1) {
            // Có phân tách
            let k_tocdo_id = 0
            let k_tocdo_nix = 0
            let k_tocdo_isp = 0
            k_tocdo_id = parseInt(this.cboTocDoBan)
            if (k_tocdo_id == 0) {
              this.ShowError('Tốc độ Tổng phải khác 0 !')
              this.select2FocusById('cboTocDoBanId')
              // cboTocDoBan.Focus();
              return
            }

            if (this.chkTocDo_NIX == false && this.chkTocDo_ISP == false) {
              this.ShowError('Bạn phải chọn tốc độ Nix hoặc Isp (Trường hợp phân tách tốc độ).')
              return
            }

            if (this.chkTocDo_NIX == true) {
              k_tocdo_nix = parseInt(this.cboTocDo_NIXBan)
              if (k_tocdo_nix == 0) {
                this.ShowError('Tốc độ NIX phải khác 0 !')
                this.select2FocusById('cboTocDo_NIXBanId')
                // cboTocDo_NIXBan.Focus();
                return
              }
            }

            if (this.chkTocDo_ISP == true) {
              k_tocdo_isp = parseInt(this.cboTocDo_ISPBan)
              if (k_tocdo_isp == 0) {
                this.ShowError('Tốc độ ISP phải khác 0 !')
                this.select2FocusById('cboTocDo_ISPBanId')
                // cboTocDo_ISPBan.Focus();
                return
              }
            }
            let kt_tocdo = await this.kiemtra_tocdo_internet_tt()
            if (kt_tocdo == 'OK') {
              let kq_kt = await this.bancheo_kiemtra_thongsokt()
              if (kq_kt == 'OK') {
                this.Capnhat = true
              }
            }
          } // if (parseInt(this.cboPhanTachTD) == 1) {
          else {
            if (parseInt(this.cboTocDoBan) == 0) {
              this.ShowError('Tốc độ Tổng phải khác 0 !')
              this.select2FocusById('cboTocDoBanId')
              // cboTocDoBan.Focus();
              return
            }

            if (this.chkTocDo_NIX == true || this.chkTocDo_ISP == true) {
              this.ShowError('Bạn không được chọn tốc độ Nix và Isp (Trường hợp không phân tách tốc độ).')
              return
            }
          } // if (parseInt(this.cboPhanTachTD) == 1) {

          if (this.ts_kt_tthdkhdn == 0) {
            // tỉnh khác
            if (this.chkTocDo_NIX == false && this.chkTocDo_ISP == false) {
              this.ShowError('Bạn phải chọn tốc độ Nix hoặc Isp.')
              return
            }

            if (this.chkTocDo_NIX == true) {
              if (parseInt(this.cboTocDo_NIXBan) == 0) {
                this.ShowError('Tốc độ NIX bán phải khác 0 !')
                this.select2FocusById('cboTocDo_NIXBanId')
                // cboTocDo_NIXBan.Focus();
                return
              }
              if (parseInt(this.cboTocDo_NIXTc) == 0) {
                this.ShowError('Tốc độ NIX thi công phải khác 0 !')
                this.select2FocusById('cboTocDo_NIXTcId')
                // cboTocDo_NIXTc.Focus();
                return
              }
            }

            if (this.chkTocDo_ISP == true) {
              if (parseInt(this.cboTocDo_ISPBan) == 0) {
                this.ShowError('Tốc độ ISP bán phải khác 0 !')
                // cboTocDo_ISPBan.Focus();
                return
              }
              if (parseInt(this.cboTocDo_ISPTc) == 0) {
                this.ShowError('Tốc độ ISP thi công phải khác 0 !')
                // cboTocDo_ISPTc.Focus();
                return
              }
            }
          } // khdn
          else {
            if (parseInt(this.cboTocDoBan) == 0) {
              this.ShowError('Tốc độ bán phải khác 0 !')
              this.select2FocusById('cboTocDoBanId')
              // cboTocDoBan.Focus();
              return
            }

            // if (chkTocDo_NIX == false && chkTocDo_ISP == false)
            // {
            //    this.ShowError("Bạn phải chọn tốc độ Nix hoặc Isp.");
            //    return;
            // }

            if (this.chkTocDo_NIX == true) {
              if (parseInt(this.cboTocDo_NIXBan) == 0) {
                this.ShowError('Tốc độ NIX bán phải khác 0 !')
                // cboTocDo_NIXBan.Focus();
                return
              }
              if (parseInt(this.cboTocDo_NIXTc) == 0) {
                this.ShowError('Tốc độ NIX thi công phải khác 0 !')
                // cboTocDo_NIXTc.Focus();
                return
              }
            }

            if (this.chkTocDo_ISP == true) {
              if (parseInt(this.cboTocDo_ISPBan) == 0) {
                this.ShowError('Tốc độ ISP bán phải khác 0 !')
                // cboTocDo_ISPBan.Focus();
                return
              }
              if (parseInt(this.cboTocDo_ISPTc) == 0) {
                this.ShowError('Tốc độ ISP thi công phải khác 0 !')
                // cboTocDo_ISPTc.Focus();
                return
              }
            }
          }
        } // end if (this.loaitb == LoaiHinhTB.MGW_INTERNET_TRUCTIEP) {
        if (this.loaitb != LoaiHinhTB.MGW_INTERNET_TRUCTIEP) {
          if (parseInt(this.cboTocDoBan) == 0) {
            this.ShowError('Tốc độ CIR bán phải khác 0 !')
            this.select2FocusById('cboTocDoBanId')
            // cboTocDoBan.Focus();
            return
          }
          if (parseInt(this.cboTocDoTc) == 0) {
            this.ShowError('Tốc độ CIR thi công phải khác 0 !')
            // cboTocDoTc.Focus();
            return
          }
          if (this.chkTocDo_PirBan) {
            if (parseInt(this.cboTocDo_PirBan) == 0) {
              this.ShowError('Tốc độ PIR bán phải khác 0 !')
              // cboTocDo_PirBan.Focus();
              return
            }
            if (parseInt(this.cboTocDo_PirTc) == 0) {
              this.ShowError('Tốc độ PIR thi công phải khác 0 !')
              // cboTocDo_PirTc.Focus();
              return
            }
          }
        }
        if (parseInt(this.cboLoaiKenhBan) == 9) {
          // kênh quốc tế
          try {
            let kq_kt = await this.bancheo_kiemtra_thongsokt()
            if (kq_kt != 'OK') {
              this.ShowError(`Có lỗi: ${kq_kt} `)
              return
            }

            if (this.cboNT_DV_BANList != null && this.cboNT_DV_BANList.length > 0) {
              for (let i = 0; i < this.cboNT_DV_BANList.length; i++) {
                if (this.cboNT_DV_BANList[i].checked == false) continue
                dt_kieu_nt_ban.Rows.Add(parseInt(this.cboNT_DV_BANList[i].id))
              }
            }
            if (this.cboNT_DV_BANList != null && this.cboNT_DV_BANList.length > 0) {
              for (let i = 0; i < this.cboNT_DV_BANList; i++) {
                if (this.cboNT_DV_BANList[i].checked == false) continue
                dt_kieu_nt_tc.Rows.Add(parseInt(this.cboNT_DV_BANList[i].id))
              }
            }
            if (dt_kieu_nt_tc.length == 0 || dt_kieu_nt_ban.length == 0) {
              this.ShowError('Chưa chọn kiểu nghiệm thu cho loại kênh quốc tế')
              return
            }
          } catch (exp) {
            this.ShowError(`Có lỗi khi chọn thông số kênh quốc tế: ${exp}`)
          }
        }

        this.Capnhat = true
        // Thong so ban
        if (this.cboPhanLoaiMGW != null && this.cboPhanLoaiMGW != '') this.ThongsoBan.phanloai_id = parseInt(this.cboPhanLoaiMGW)
        else this.ThongsoBan.phanloai_id = 0
        if (this.cboPhanLoaiMGW != null && this.cboPhanTachTD != '') this.ThongsoBan.phantach_id = parseInt(this.cboPhanTachTD)
        else this.ThongsoBan.phantach_id = 0

        if (this.cboChuQuan_Ban != '') this.ThongsoBan.chuquan_id = parseInt(this.cboChuQuan_Ban)
        else this.ThongsoBan.chuquan_id = 0

        if (this.cboChuQuanLD_Ban != '') this.ThongsoBan.chuquanld_id = parseInt(this.cboChuQuanLD_Ban)
        else this.ThongsoBan.chuquanld_id = 0

        if (this.cboLoaiKenhBan != '') this.ThongsoBan.loaikenh_id = parseInt(this.cboLoaiKenhBan)
        else this.ThongsoBan.loaikenh_id = 0

        if (this.cboLoaiModemBan != '') this.ThongsoBan.loaimd_id = parseInt(this.cboLoaiModemBan)
        else this.ThongsoBan.loaimd_id = 0

        if (this.cboLoaiTBiBan != '') this.ThongsoBan.thietbidc_id = parseInt(this.cboLoaiTBiBan)
        else this.ThongsoBan.thietbidc_id = 0

        if (this.cboThoiHanBan != '') this.ThongsoBan.thoihan_id = parseInt(this.cboThoiHanBan)
        else this.ThongsoBan.thoihan_id = 0

        if (this.cboTocDoBan != '') this.ThongsoBan.tocdo_id = parseInt(this.cboTocDoBan)
        else this.ThongsoBan.tocdo_id = 0

        if (this.cboTocDo_ISPBan != '' && this.chkTocDo_ISP) this.ThongsoBan.tocdo_isp = parseInt(this.cboTocDo_ISPBan)
        else this.ThongsoBan.tocdo_isp = 0

        if (this.cboTocDo_NIXBan != '' && this.chkTocDo_NIX) this.ThongsoBan.tocdo_nix = parseInt(this.cboTocDo_NIXBan)
        else this.ThongsoBan.tocdo_nix = 0

        if (this.cboTocDo_PirBan != '' && this.chkTocDo_PirBan) this.ThongsoBan.tocdo_pir_id = parseInt(this.cboTocDo_PirBan)
        else this.ThongsoBan.tocdo_pir_id = 0

        if (this.cboTrangBiBan != '') this.ThongsoBan.trangbi_id = parseInt(this.cboTrangBiBan)
        else this.ThongsoBan.trangbi_id = 0
        this.ThongsoBan.doituong_id = parseInt(this.cboDoiTuongBan)
        this.ThongsoBan.muccuoctb_id = parseInt(this.cboMucCuocTBBan)
        //
        //  thông số thi công
        if (this.cboChuQuan_TC != '') this.ThongsoTc.chuquan_id = parseInt(this.cboChuQuan_TC)
        else this.ThongsoTc.chuquan_id = 0

        if (this.cboChuQuanLD_TC != '') this.ThongsoTc.chuquanld_id = parseInt(this.cboChuQuanLD_TC)
        else this.ThongsoTc.chuquanld_id = 0

        if (this.cboLoaiKenhTc != '') this.ThongsoTc.loaikenh_id = parseInt(this.cboLoaiKenhTc)
        else this.ThongsoTc.loaikenh_id = 0

        if (this.cboLoaiModemTc != '') this.ThongsoTc.loaimd_id = parseInt(this.cboLoaiModemTc)
        else this.ThongsoTc.loaimd_id = 0

        if (this.cboLoaiTBiTc != '') this.ThongsoTc.thietbidc_id = parseInt(this.cboLoaiTBiTc)
        else this.ThongsoTc.thietbidc_id = 0

        if (this.cboThoiHanTc != '') this.ThongsoTc.thoihan_id = parseInt(this.cboThoiHanTc)
        else this.ThongsoTc.thoihan_id = 0

        if (this.cboTocDoTc != '') this.ThongsoTc.tocdo_id = parseInt(this.cboTocDoTc)
        else this.ThongsoTc.tocdo_id = 0

        if (this.cboTocDo_ISPTc != '') this.ThongsoTc.tocdo_isp = parseInt(this.cboTocDo_ISPTc)
        else this.ThongsoTc.tocdo_isp = 0

        if (this.cboTocDo_NIXTc != '') this.ThongsoTc.tocdo_nix = parseInt(this.cboTocDo_NIXTc)
        else this.ThongsoTc.tocdo_nix = 0

        if (this.cboTocDo_PirTc != '') this.ThongsoTc.tocdo_pir_id = parseInt(this.cboTocDo_PirTc)
        else this.ThongsoTc.tocdo_pir_id = 0

        if (this.cboTrangBiTc != null && this.cboTrangBiTc != '') this.ThongsoTc.trangbi_id = parseInt(this.cboTrangBiTc)
        else this.ThongsoTc.trangbi_id = 0

        this.ThongsoTc.doituong_id = parseInt(this.cboDoiTuongTC)
        this.ThongsoTc.kieuld_id = parseInt(this.cboKieuLD)
        this.ThongsoTc.muccuoctb_id = parseInt(this.cboMucCuocTBTC)
        // thông số kênh qt
        if (this.cboMHKN_TC != '' && this.cboMHKN_TC != '0') this.ThongsoBan.mhkn_id = parseInt(this.cboMHKN_TC)
        else this.ThongsoBan.mhkn_id = 0
        if (this.cboMHKN_TC != '' && this.cboMHKN_TC != '0') this.ThongsoTc.mhkn_id = parseInt(this.cboMHKN_BAN)
        else this.ThongsoTc.mhkn_id = 0

        if (this.cboTocDoPOP_BAN != '' && this.cboTocDoPOP_BAN != '0') this.ThongsoBan.tocdo_pop = parseInt(this.cboTocDoPOP_BAN)
        else this.ThongsoBan.tocdo_pop = 0
        if (this.cboTocDoPOP_TC != '' && this.cboTocDoPOP_TC != '0') this.ThongsoTc.tocdo_pop = parseInt(this.cboTocDoPOP_TC)
        else this.ThongsoTc.tocdo_pop = 0

        if (this.cboGiaoDienKH_BAN != '' && this.cboGiaoDienKH_BAN != '0') this.ThongsoBan.gdkn_id = parseInt(this.cboGiaoDienKH_BAN)
        else this.ThongsoBan.gdkn_id = 0
        if (this.cboGiaoDienKH_TC != '' && this.cboGiaoDienKH_TC != '0') this.ThongsoTc.gdkn_id = parseInt(this.cboGiaoDienKH_TC)
        else this.ThongsoBan.gdkn_id = 0

        if (this.cboSpeed_Duplex_BAN != '' && this.cboSpeed_Duplex_BAN != '0') this.ThongsoBan.kieu_duplex_id = parseInt(this.cboSpeed_Duplex_BAN)
        else this.ThongsoBan.kieu_duplex_id = 0
        if (this.cboSpeed_Duplex_TC != '' && this.cboSpeed_Duplex_TC != '0') this.ThongsoTc.kieu_duplex_id = parseInt(this.cboSpeed_Duplex_TC)
        else this.ThongsoTc.kieu_duplex_id = 0

        if (this.cboCauHinhDT_BAN != '' && this.cboCauHinhDT_BAN != '0') this.ThongsoBan.cauhinh_dt_id = parseInt(this.cboCauHinhDT_BAN)
        else this.ThongsoBan.cauhinh_dt_id = 0
        if (this.cboCauHinhDT_TC != '' && this.cboCauHinhDT_TC != '0') this.ThongsoTc.cauhinh_dt_id = parseInt(this.cboCauHinhDT_TC)
        else this.ThongsoTc.cauhinh_dt_id = 0

        if (this.cboGiaoDienPOP_BAN != '' && this.cboGiaoDienPOP_BAN != '0') this.ThongsoBan.gdkn_pop_id = parseInt(this.cboGiaoDienPOP_BAN)
        else this.ThongsoBan.gdkn_pop_id = 0
        if (this.cboGiaoDienPOP_TC != '' && this.cboGiaoDienPOP_TC != '0') this.ThongsoTc.gdkn_pop_id = parseInt(this.cboGiaoDienPOP_TC)
        else this.ThongsoTc.gdkn_pop_id = 0

        if (this.cboKieuKN_BAN != '' && this.cboKieuKN_BAN != '0') this.ThongsoBan.kieukn_id = parseInt(this.cboKieuKN_BAN)
        else this.ThongsoBan.kieukn_id = 0
        if (this.cboKieuKN_TC != '' && this.cboKieuKN_TC != '0') this.ThongsoTc.kieukn_id = parseInt(this.cboKieuKN_TC)
        else this.ThongsoTc.kieukn_id = 0
        //
        this.ThongsoBan.loai_ip = this.dt_loai_ip_ban
        this.ThongsoTc.loai_ip = this.dt_loai_ip_tc
        this.ThongsoBan.kieu_nghiemthu = this.dt_kieu_nt_ban
        this.ThongsoTc.kieu_nghiemthu = this.dt_kieu_nt_tc

        this.ThongsoBan.ten_trunk_nni = this.txtTenTrunkNNI_BAN
        this.ThongsoTc.ten_trunk_nni = this.txtTenTrunkNNI_TC

        this.ThongsoBan.svlan = this.txtSVLAN_BAN
        this.ThongsoTc.svlan = this.txtSVLAN_TC

        this.ThongsoBan.lan_ip = this.txtIP_LAN_BAN
        this.ThongsoTc.lan_ip = this.txtIP_LAN_TC

        this.ThongsoBan.wan_ip = this.txtIP_WAN_BAN
        this.ThongsoTc.wan_ip = this.txtIP_WAN_TC

        this.ThongsoBan.ce_ip = this.txtIP_CE_BAN
        this.ThongsoTc.ce_ip = this.txtIP_CE_TC

        this.ThongsoBan.tt_ip = this.txtThongTinIP_BAN
        this.ThongsoTc.tt_ip = this.txtThongTinIP_TC

        this.ThongsoBan.cvlan = this.txtCLANID_BAN
        this.ThongsoTc.cvlan = this.txtCLANID_TC

        this.ThongsoBan.diem_pop = this.txtDiemPOP_BAN
        this.ThongsoTc.diem_pop = this.txtDiemPOP_TC

        this.ThongsoBan.so_as = this.txtSo_AS_BAN
        this.ThongsoTc.so_as = this.txtSo_AS_TC

        this.ThongsoBan.mtu_size = this.txtMTU_Size_BAN
        this.ThongsoTc.mtu_size = this.txtMTU_Size_TC

        this.ThongsoBan.hqos = this.txtHqos_BAN
        this.ThongsoTc.hqos = this.txtHqos_TC

        this.ThongsoBan.cos = this.txtCos_BAN
        this.ThongsoTc.cos = this.txtCos_TC

        this.ThongsoBan.sl_ip = this.txtSL_IP_BAN
        this.ThongsoTc.sl_ip = this.txtSL_IP_TC

        this.ThongsoBan.vlan_transparent = this.txtVLAN_Trans_BAN
        this.ThongsoTc.vlan_transparent = this.txtVLAN_Trans_TC

        this.ThongsoBan.tt_port = this.txtPort_POP_BAN
        this.ThongsoTc.tt_port = this.txtPort_POP_TC

        /*
        let kt_tocdo = await this.kiemtra_tocdo_internet_tt()
        if (kt_tocdo == 'OK') {
          let kq_kt = await this.bancheo_kiemtra_thongsokt()
          if (kq_kt == 'OK') {
            this.Capnhat = true
            // Thong so ban
            if (this.cboPhanLoaiMGW != null && this.cboPhanLoaiMGW != '') this.ThongsoBan.phanloai_id = parseInt(this.cboPhanLoaiMGW)
            else this.ThongsoBan.phanloai_id = 0
            if (this.cboPhanLoaiMGW != null && this.cboPhanTachTD != '') this.ThongsoBan.phantach_id = parseInt(this.cboPhanTachTD)
            else this.ThongsoBan.phantach_id = 0

            if (this.cboChuQuan_Ban != '') this.ThongsoBan.chuquan_id = parseInt(this.cboChuQuan_Ban)
            else this.ThongsoBan.chuquan_id = 0

            if (this.cboChuQuanLD_Ban != '') this.ThongsoBan.chuquanld_id = parseInt(this.cboChuQuanLD_Ban)
            else this.ThongsoBan.chuquanld_id = 0

            if (this.cboLoaiKenhBan != '') this.ThongsoBan.loaikenh_id = parseInt(this.cboLoaiKenhBan)
            else this.ThongsoBan.loaikenh_id = 0

            if (this.cboLoaiModemBan != '') this.ThongsoBan.loaimd_id = parseInt(this.cboLoaiModemBan)
            else this.ThongsoBan.loaimd_id = 0

            if (this.cboLoaiTBiBan != '') this.ThongsoBan.thietbidc_id = parseInt(this.cboLoaiTBiBan)
            else this.ThongsoBan.thietbidc_id = 0

            if (this.cboThoiHanBan != '') this.ThongsoBan.thoihan_id = parseInt(this.cboThoiHanBan)
            else this.ThongsoBan.thoihan_id = 0

            if (this.cboTocDoBan != '') this.ThongsoBan.tocdo_id = parseInt(this.cboTocDoBan)
            else this.ThongsoBan.tocdo_id = 0

            if (this.cboTocDo_ISPBan != '') this.ThongsoBan.tocdo_isp = parseInt(this.cboTocDo_ISPBan)
            else this.ThongsoBan.tocdo_isp = 0

            if (this.cboTocDo_NIXBan != '') this.ThongsoBan.tocdo_nix = parseInt(this.cboTocDo_NIXBan)
            else this.ThongsoBan.tocdo_nix = 0

            if (this.cboTocDo_PirBan != '') this.ThongsoBan.tocdo_pir_id = parseInt(this.cboTocDo_PirBan)
            else this.ThongsoBan.tocdo_pir_id = 0

            if (this.cboTrangBiBan != '') this.ThongsoBan.trangbi_id = parseInt(this.cboTrangBiBan)
            else this.ThongsoBan.trangbi_id = 0
            this.ThongsoBan.doituong_id = parseInt(this.cboDoiTuongBan)
            this.ThongsoBan.muccuoctb_id = parseInt(this.cboMucCuocTBBan)
            //
            //  thông số thi công
            if (this.cboChuQuan_TC != '') this.ThongsoTc.chuquan_id = parseInt(this.cboChuQuan_TC)
            else this.ThongsoTc.chuquan_id = 0

            if (this.cboChuQuanLD_TC != '') this.ThongsoTc.chuquanld_id = parseInt(this.cboChuQuanLD_TC)
            else this.ThongsoTc.chuquanld_id = 0

            if (this.cboLoaiKenhTc != '') this.ThongsoTc.loaikenh_id = parseInt(this.cboLoaiKenhTc)
            else this.ThongsoTc.loaikenh_id = 0

            if (this.cboLoaiModemTc != '') this.ThongsoTc.loaimd_id = parseInt(this.cboLoaiModemTc)
            else this.ThongsoTc.loaimd_id = 0

            if (this.cboLoaiTBiTc != '') this.ThongsoTc.thietbidc_id = parseInt(this.cboLoaiTBiTc)
            else this.ThongsoTc.thietbidc_id = 0

            if (this.cboThoiHanTc != '') this.ThongsoTc.thoihan_id = parseInt(this.cboThoiHanTc)
            else this.ThongsoTc.thoihan_id = 0

            if (this.cboTocDoTc != '') this.ThongsoTc.tocdo_id = parseInt(this.cboTocDoTc)
            else this.ThongsoTc.tocdo_id = 0

            if (this.cboTocDo_ISPTc != '') this.ThongsoTc.tocdo_isp = parseInt(this.cboTocDo_ISPTc)
            else this.ThongsoTc.tocdo_isp = 0

            if (this.cboTocDo_NIXTc != '') this.ThongsoTc.tocdo_nix = parseInt(this.cboTocDo_NIXTc)
            else this.ThongsoTc.tocdo_nix = 0

            if (this.cboTocDo_PirTc != '') this.ThongsoTc.tocdo_pir_id = parseInt(this.cboTocDo_PirTc)
            else this.ThongsoTc.tocdo_pir_id = 0

            if (this.cboTrangBiTc != null && this.cboTrangBiTc != '') this.ThongsoTc.trangbi_id = parseInt(this.cboTrangBiTc)
            else this.ThongsoTc.trangbi_id = 0

            this.ThongsoTc.doituong_id = parseInt(this.cboDoiTuongTC)
            this.ThongsoTc.kieuld_id = parseInt(this.cboKieuLD)
            this.ThongsoTc.muccuoctb_id = parseInt(this.cboMucCuocTBTC)
            // thông số kênh qt
            if (this.cboMHKN_TC != '' && this.cboMHKN_TC != '0') this.ThongsoBan.mhkn_id = parseInt(this.cboMHKN_TC)
            else this.ThongsoBan.mhkn_id = 0
            if (this.cboMHKN_TC != '' && this.cboMHKN_TC != '0') this.ThongsoTc.mhkn_id = parseInt(this.cboMHKN_BAN)
            else this.ThongsoTc.mhkn_id = 0

            if (this.cboTocDoPOP_BAN != '' && this.cboTocDoPOP_BAN != '0') this.ThongsoBan.tocdo_pop = parseInt(this.cboTocDoPOP_BAN)
            else this.ThongsoBan.tocdo_pop = 0
            if (this.cboTocDoPOP_TC != '' && this.cboTocDoPOP_TC != '0') this.ThongsoTc.tocdo_pop = parseInt(this.cboTocDoPOP_TC)
            else this.ThongsoTc.tocdo_pop = 0

            if (this.cboGiaoDienKH_BAN != '' && this.cboGiaoDienKH_BAN != '0') this.ThongsoBan.gdkn_id = parseInt(this.cboGiaoDienKH_BAN)
            else this.ThongsoBan.gdkn_id = 0
            if (this.cboGiaoDienKH_TC != '' && this.cboGiaoDienKH_TC != '0') this.ThongsoTc.gdkn_id = parseInt(this.cboGiaoDienKH_TC)
            else this.ThongsoBan.gdkn_id = 0

            if (this.cboSpeed_Duplex_BAN != '' && this.cboSpeed_Duplex_BAN != '0') this.ThongsoBan.kieu_duplex_id = parseInt(this.cboSpeed_Duplex_BAN)
            else this.ThongsoBan.kieu_duplex_id = 0
            if (this.cboSpeed_Duplex_TC != '' && this.cboSpeed_Duplex_TC != '0') this.ThongsoTc.kieu_duplex_id = parseInt(this.cboSpeed_Duplex_TC)
            else this.ThongsoTc.kieu_duplex_id = 0

            if (this.cboCauHinhDT_BAN != '' && this.cboCauHinhDT_BAN != '0') this.ThongsoBan.cauhinh_dt_id = parseInt(this.cboCauHinhDT_BAN)
            else this.ThongsoBan.cauhinh_dt_id = 0
            if (this.cboCauHinhDT_TC != '' && this.cboCauHinhDT_TC != '0') this.ThongsoTc.cauhinh_dt_id = parseInt(this.cboCauHinhDT_TC)
            else this.ThongsoTc.cauhinh_dt_id = 0

            if (this.cboGiaoDienPOP_BAN != '' && this.cboGiaoDienPOP_BAN != '0') this.ThongsoBan.gdkn_pop_id = parseInt(this.cboGiaoDienPOP_BAN)
            else this.ThongsoBan.gdkn_pop_id = 0
            if (this.cboGiaoDienPOP_TC != '' && this.cboGiaoDienPOP_TC != '0') this.ThongsoTc.gdkn_pop_id = parseInt(this.cboGiaoDienPOP_TC)
            else this.ThongsoTc.gdkn_pop_id = 0

            if (this.cboKieuKN_BAN != '' && this.cboKieuKN_BAN != '0') this.ThongsoBan.kieukn_id = parseInt(this.cboKieuKN_BAN)
            else this.ThongsoBan.kieukn_id = 0
            if (this.cboKieuKN_TC != '' && this.cboKieuKN_TC != '0') this.ThongsoTc.kieukn_id = parseInt(this.cboKieuKN_TC)
            else this.ThongsoTc.kieukn_id = 0
            //
            this.ThongsoBan.loai_ip = this.dt_loai_ip_ban
            this.ThongsoTc.loai_ip = this.dt_loai_ip_tc
            this.ThongsoBan.kieu_nghiemthu = this.dt_kieu_nt_ban
            this.ThongsoTc.kieu_nghiemthu = this.dt_kieu_nt_tc

            this.ThongsoBan.ten_trunk_nni = this.txtTenTrunkNNI_BAN
            this.ThongsoTc.ten_trunk_nni = this.txtTenTrunkNNI_TC

            this.ThongsoBan.svlan = this.txtSVLAN_BAN
            this.ThongsoTc.svlan = this.txtSVLAN_TC

            this.ThongsoBan.lan_ip = this.txtIP_LAN_BAN
            this.ThongsoTc.lan_ip = this.txtIP_LAN_TC

            this.ThongsoBan.wan_ip = this.txtIP_WAN_BAN
            this.ThongsoTc.wan_ip = this.txtIP_WAN_TC

            this.ThongsoBan.ce_ip = this.txtIP_CE_BAN
            this.ThongsoTc.ce_ip = this.txtIP_CE_TC

            this.ThongsoBan.tt_ip = this.txtThongTinIP_BAN
            this.ThongsoTc.tt_ip = this.txtThongTinIP_TC

            this.ThongsoBan.cvlan = this.txtCLANID_BAN
            this.ThongsoTc.cvlan = this.txtCLANID_TC

            this.ThongsoBan.diem_pop = this.txtDiemPOP_BAN
            this.ThongsoTc.diem_pop = this.txtDiemPOP_TC

            this.ThongsoBan.so_as = this.txtSo_AS_BAN
            this.ThongsoTc.so_as = this.txtSo_AS_TC

            this.ThongsoBan.mtu_size = this.txtMTU_Size_BAN
            this.ThongsoTc.mtu_size = this.txtMTU_Size_TC

            this.ThongsoBan.hqos = this.txtHqos_BAN
            this.ThongsoTc.hqos = this.txtHqos_TC

            this.ThongsoBan.cos = this.txtCos_BAN
            this.ThongsoTc.cos = this.txtCos_TC

            this.ThongsoBan.sl_ip = this.txtSL_IP_BAN
            this.ThongsoTc.sl_ip = this.txtSL_IP_TC

            this.ThongsoBan.vlan_transparent = this.txtVLAN_Trans_BAN
            this.ThongsoTc.vlan_transparent = this.txtVLAN_Trans_TC

            this.ThongsoBan.tt_port = this.txtPort_POP_BAN
            this.ThongsoTc.tt_port = this.txtPort_POP_TC
          } else {
            this.ShowError(kq_kt)
            return
          }
        } else {
          this.ShowError(kt_tocdo)
          return
        }
        */
        this.$emit('closeThongSoWan', { Capnhat: this.Capnhat, ThongsoBan: this.ThongsoBan, ThongsoTc: this.ThongsoTc })
        this.$refs.dialogThongSoWan.hide()
      } catch (err) {
        console.log('🚀tsbtnCapNhat_Click ~ err', err)
      } finally {
        this.loading(false)
      }
    },
    async kiemtra_tocdo_internet_tt () {
      let tocdoNixId = this.chkTocDo_NIX ? this.cboTocDo_NIXBan : 0
      let tocdoIspId = this.chkTocDo_ISP ? this.cboTocDo_ISPBan : 0
      let input = { tocdoId: this.cboTocDoBan, tocdoIspId: tocdoIspId, tocdoNixId: tocdoNixId }
      // let input = { tocdo_id: this.cboTocDoBan, tocdo_isp_id: tocdoIspId, tocdo_nix_id: tocdoNixId }
      let data = this.GetData(await api.kiemtra_tocdo_internet_tt(this.axios, input))
      return data
    },
    async bancheo_kiemtra_thongsokt () {
      let nhanvienId = this.$root.token.getNhanVienID()
      let obj = {
        tocdo_id: 0,
        tocdothuc_id: 0,
        tocdo_pir_id: 0,
        tocdo_nix: 0,
        tocdo_isp: 0,
        chuquan_id: parseInt(this.cboChuQuan_Ban),
        chuquanld_id: parseInt(this.cboChuQuanLD_Ban),
        congnghe_id: 0,
        muccuoc_id: 0,
        kieutra_id: 0,
        doituong_id: 0,
        muccuoctb_id: 0,
        trangbi_id: 0,
        kieuld_id: parseInt(this.cboKieuLD),
        thietbidc_id: 0,
        loaimd_id: 0,
        loaikenh_id: parseInt(this.cboLoaiKenhBan),
        thietbidc_id_cuoi: 0,
        thoihan_id: 0,
        ma_tn: '',
        ma_doicap: '',
        chuquanld_id_cuoi: 0,
        phanloai_id: 0,
        phantach_id: 0,
        mhkn_id: parseInt(this.cboMHKN_BAN),
        gdkn_id: 0,
        yc_baove: 0,
        tocdo_pop: parseInt(this.cboTocDoPOP_BAN),
        gdkn_pop_id: parseInt(this.cboGiaoDienPOP_BAN),
        gdkn_kh_id: parseInt(this.cboGiaoDienKH_BAN),
        cauhinh_dt_id: parseInt(this.cboCauHinhDT_BAN),
        kieu_duplex_id: parseInt(this.cboSpeed_Duplex_BAN),
        kieukn_id: parseInt(this.cboKieuKN_BAN),
        mtu_size: this.txtMTU_Size_BAN,
        auto_nego: '',
        sncp: '',
        cap_chinh: '',
        cap_duphong: '',
        diem_pop: this.txtDiemPOP_BAN,
        wan_ip: this.txtIP_WAN_BAN,
        lan_ip: this.txtIP_LAN_BAN,
        ce_ip: this.txtIP_CE_BAN,
        tt_ip: this.txtThongTinIP_BAN,
        sl_ip: this.txtSL_IP_BAN,
        ten_trunk_nni: this.txtTenTrunkNNI_BAN,
        so_as: this.txtSo_AS_BAN,
        hqos: this.txtHqos_BAN,
        cos: this.txtCos_BAN,
        svlan: this.txtSVLAN_BAN,
        cvlan: this.txtCLANID_BAN,
        vlan_transparent: this.txtVLAN_Trans_BAN,
        tt_port: this.txtPort_POP_BAN
      }
      let input = { dichvuvtId: this._vdichvu_vt, jsonKt: JSON.stringify(obj), loaiHdId: this._vloaihd, loaiTbId: this.loaitb, nhanvienId: nhanvienId }
      let data = this.GetData(await api.bancheo_kiemtra_thongsokt(this.axios, input))
      return data
    },
    tsbtnThoat_Click () {
      this.$refs.dialogThongSoWan.hide()
    },
    GetData: function (response) {
      if (response.data.error === 200 || response.data.error === '200') {
        return response.data.data
      } else {
        console.log(response.data.error_code)
      }
      return []
    },
    async lay_ds_mucuoc_tb_v2 () {
      try {
        let input = {
          apld_id: 0,
          doituong_id: this.cboDoiTuongBan,
          khuld_id: 0,
          loaitb_id: this.loaitb,
          muccuoc_id: 0,
          ngay: moment().format('DD/MM/YYYY'),
          phold_id: 0,
          phuongld_id: 0,
          tocdo_id: 0,
          trangbi_id: this.cboTrangBiBan
        }

        let data = this.GetData(await api2.lay_ds_mucuoc_tb_v2(this.axios, input))
        if (data != null && data.length > 0) {
          let options = data.map((x) => ({ id: x.muccuoctb_id, text: x.tenmuccuoc }))
          this.cboMucCuocTBBanList = options
          this.cboMucCuocTBBan = options[0].id
          // this.cboMucCuocTBTC = options[0].id
        } else {
          this.cboMucCuocTBBanList = []
        }
      } catch (err) {
        console.log('🚀  lay_ds_mucuoc_tb_v2 ~ err', err)
      }
    },
    async lay_ds_mucuoc_tb_v2_tinh () {
      try {
        let vtocdo_id = 0
        let vmuccuoc_id = 0
        let vloaitb_id = this.loaitb

        if (vloaitb_id == LoaiHinhTB.INTERNET_FTTH || vloaitb_id == LoaiHinhTB.WIFI_FIBER || vloaitb_id == LoaiHinhTB.INTERNET_ADSL || vloaitb_id == LoaiHinhTB.INTERNET_ADSL_TINH_KHAC || vloaitb_id == LoaiHinhTB.INTERNET_FIBER_TINH_KHAC) {
          vtocdo_id = this.cboTocDoTc
          vmuccuoc_id = 0
        } else if (vloaitb_id == LoaiHinhTB.INTERNET_MYTV) {
          vtocdo_id = this.cboTocDoTc
          vmuccuoc_id = 0
        } else if (vloaitb_id == LoaiHinhTB.FSECURE) {
          // Lamnt: bổ sung hiển thị mức cước theo tốc độ thuê bao FSecure (09/03/2017)
          vtocdo_id = this.cboTocDoTc
          vmuccuoc_id = 0
        }
        /*
        let input = {
          tinhthicong_id: this.tinh.tinhthicong_id,
          apld_id: 0,
          doituong_id: this.cboDoiTuongTC,
          khuld_id: 0,
          loaitb_id: this.loaitb,
          muccuoc_id: 0,
          ngay: moment().format('DD/MM/YYYY'),
          phold_id: 0,
          phuongld_id: 0,
          tocdo_id: this.cboTocDoTc,
          trangbi_id: this.cboTrangBiTc
        }
*/
        let input = {
          tinhthicong_id: this.tinh.tinhthicong_id,
          apld_id: 0,
          doituong_id: this.cboDoiTuongTC,
          khuld_id: 0,
          loaitb_id: this.loaitb,
          muccuoc_id: vmuccuoc_id,
          ngay: moment().format('DD/MM/YYYY'),
          phold_id: 0,
          phuongld_id: 0,
          tocdo_id: vtocdo_id,
          trangbi_id: this.cboTrangBiTc
        }

        let data = this.GetData(await api2.lay_ds_mucuoc_tb_v2_tinh(this.axios, input))
        if (data != null && data.length > 0) {
          let options = data.map((x) => ({ id: x.mucuoctb_id, text: x.tenmuccuoc }))
          console.log('🚀 ~ file: frmThongSoWan.vue:1327 ~ lay_ds_mucuoc_tb_v2_tinh ~ options:', options)
          this.cboMucCuocTBTCList = options
          this.cboMucCuocTBTC = options[0].id
        } else {
          this.cboMucCuocTBTCList = []
        }
      } catch (err) {
        console.log('🚀 lay_ds_mucuoc_tb_v2_tinh ~ err', err)
      }
    },
    async sp_loadkieu_ld () {
      try {
        if (this._vloaihd != LoaiHopDong.CHUYENDOI_LH) {
          await this.sp_ht_kieuld_loaitb_bancheo2()
        } else {
          await this.lay_ds_kieu_ld_lhtb()
        }
      } catch (err) {
        console.log('🚀 ~ file: frmThongSoWan.vue ~ line 1201 ~ sp_loadkieu_ld ~ err', err)
      }
    },
    async sp_ht_kieuld_loaitb_bancheo () {
      let input = {
        tinhthicong_id: this.tinh.tinhthicong_id,
        loaihd: this._vloaihd,
        loaitb: this.loaitb
      }
      let data = this.GetData(await api2.sp_ht_kieuld_loaitb_bancheo(this.axios, input))
      if (data != null && data.length > 0) {
        let options = data.map((x) => ({ id: x.kieuld_id, text: x.ten_kieuld }))
        this.cboKieuLDList = options
        this.cboKieuLD = options[0].id
      } else {
        this.cboKieuLDList = []
      }
    },
    async sp_ht_kieuld_loaitb_bancheo2 () {
      let input = {
        tinhthicong_id: this.tinh.tinhthicong_id,
        vloaihd: this._vloaihd,
        vloaitb: this.loaitb
      }
      let data = this.GetData(await api2.sp_ht_kieuld_loaitb_bancheo2(this.axios, input))
      if (data != null && data.length > 0) {
        let options = data.map((x) => ({ id: x.kieuld_id, text: x.ten_kieuld }))
        this.cboKieuLDList = options
        this.cboKieuLD = options[0].id
      } else {
        this.cboKieuLDList = []
      }
    },
    async lay_ds_kieu_ld_lhtb () {
      let input = {
        tinhtcId: this.tinh.tinhthicong_id,
        loaihdId: LoaiHopDong.CHUYENDOI_LH,
        loaitbId: this.loaitb,
        tinhkhac: 1
      }
      let data = this.GetData(await api2.lay_ds_kieu_ld_lhtb_bc(this.axios, input))
      if (data != null && data.length > 0) {
        let options = data.map((x) => ({ id: x.kieuld_id, text: x.ten_kieuld }))
        this.cboKieuLDList = options
        this.cboKieuLD = options[0].id
      } else {
        this.cboKieuLDList = []
      }
    },
    async lay_ds_kieu_ld_lhtb_old () {
      let input = {
        vloaihd_id: LoaiHopDong.CHUYENDOI_LH,
        vloaitb_id: this.loaitb,
        vtinhkhac: 1
      }
      let data = this.GetData(await api2.lay_ds_kieu_ld_lhtb(this.axios, input))
      if (data != null && data.length > 0) {
        let options = data.map((x) => ({ id: x.kieuld_id, text: x.ten_kieuld }))
        this.cboKieuLDList = options
        this.cboKieuLD = options[0].id
      } else {
        this.cboKieuLDList = []
      }
    },
    async sp_ds_phantach_tocdo () {
      let input = { vkieu: 1 }
      let data = this.GetData(await api2.sp_ds_phantach_tocdo(this.axios, input))
      if (data != null && data.length > 0) {
        let options = data.map((x) => ({ id: x.phantach_id, text: x.phantach == null ? '' : x.phantach }))
        this.cboPhanTachTDList = options
        this.cboPhanTachTD = options[0].id
      }
    },
    async sp_lay_doituong_tb () {
      try {
        await Promise.all([this.sp_lay_doituong_tb_ban(), this.sp_lay_doituong_tb_tc()])
      } catch (err) {
        console.log('sp_lay_doituong_tb ~ err', err)
      }
    },
    async sp_lay_doituong_tb_ban () {
      let nhanvienId = this.$root.token.getNhanVienID()
      let input = { nhanvienId: nhanvienId }
      let data = this.GetData(await api2.sp_lay_doituong_tb(this.axios, input))
      if (data != null && data.length > 0) {
        let options = data.map((x) => ({ id: x.doituong_id, text: x.ten_dt }))
        this.cboDoiTuongBanList = options
        this.cboDoiTuongBan = options[0].id
      }
    },
    async sp_lay_doituong_tb_tc () {
      let input = { tinhthicong_id: this.tinh.tinhthicong_id }
      let data = this.GetData(await api2.sp_lay_doituong_tb_tc(this.axios, input))
      if (data != null && data.length > 0) {
        let options = data.map((x) => ({ id: x.doituong_id, text: x.ten_dt }))
        this.cboDoiTuongTCList = options
        this.cboDoiTuongTC = options[0].id
      }
    },
    async sp_lay_mohinh_kn () {
      let input = {}
      let data = this.GetData(await api2.sp_lay_mohinh_kn(this.axios, input))
      if (data != null && data.length > 0) {
        let options = data.map((x) => ({ id: x.mhkn_id, text: x.mohinh_kn }))
        this.cboMHKN_BANList = options
        this.cboMHKN_BAN = options[0].id
        this.cboMHKN_TC = options[0].id
      } else {
        this.cboMHKN_BANList = []
      }
    },
    async sp_lay_ds_tocdo_kenh () {
      let input = {}
      let data = this.GetData(await api2.sp_lay_ds_tocdo_kenh(this.axios, input))
      if (data != null && data.length > 0) {
        let options = data.map((x) => ({ id: x.tocdo_id, text: x.toc_do }))
        this.cboTocDoPOP_BANList = options
        this.cboTocDoPOP_BAN = options[0].id
        // this.cboTocDoPOP_TC = options[0].id
      } else {
        this.cboTocDoPOP_BANList = []
      }
    },
    async sp_ht_tocdo_kenh_combobox () {
      let input = { tinhthicong_id: this.tinh.tinhthicong_id }
      let data = this.GetData(await api2.sp_ht_tocdo_kenh_combobox(this.axios, input))
      if (data != null && data.length > 0) {
        let options = data.map((x) => ({ id: x.tocdo_id, text: x.toc_do }))
        this.cboTocDoPOP_TCList = options
        // this.cboTocDoPOP_BAN = options[0].id
        this.cboTocDoPOP_TC = options[0].id
      } else {
        this.cboTocDoPOP_TCList = []
      }
    },
    async sp_lay_giaodien_kn () {
      let input = {}
      let data = this.GetData(await api2.sp_lay_giaodien_kn(this.axios, input))
      if (data != null && data.length > 0) {
        let options = data.map((x) => ({ id: x.gdkn_id, text: x.giaodien_kn }))
        this.cboGiaoDienKH_BANList = options
        this.cboGiaoDienPOP_BANList = options
        this.cboGiaoDienKH_TC = options[0].id
        this.cboGiaoDienKH_BAN = options[0].id
        this.cboGiaoDienPOP_BAN = options[0].id
        this.cboGiaoDienPOP_TC = options[0].id
      } else {
        this.cboGiaoDienKH_BANList = []
        this.cboGiaoDienPOP_BANList = []
      }
    },
    async sp_lay_kieu_duplex () {
      let input = {}
      let data = this.GetData(await api2.sp_lay_kieu_duplex(this.axios, input))
      if (data != null && data.length > 0) {
        let options = data.map((x) => ({ id: x.kieu_duplex_id, text: x.kieu_duplex }))
        this.cboSpeed_Duplex_BANList = options
        this.cboSpeed_Duplex_BAN = options[0].id
        this.cboSpeed_Duplex_TC = options[0].id
      } else {
        this.cboSpeed_Duplex_BANList = []
      }
    },
    async sp_lay_cauhinh_dt () {
      let input = {}
      let data = this.GetData(await api2.sp_lay_cauhinh_dt(this.axios, input))
      if (data != null && data.length > 0) {
        let options = data.map((x) => ({ id: x.cauhinh_dt_id, text: x.cauhinh_dt }))
        this.cboCauHinhDT_BANList = options
        this.cboCauHinhDT_TC = options[0].id
        this.cboCauHinhDT_BAN = options[0].id
      }
    },
    async sp_lay_kieu_nghiemthu () {
      let input = {}
      let data = this.GetData(await api2.sp_lay_kieu_nghiemthu(this.axios, input))
      if (data != null && data.length > 0) {
        let options = data.map((x) => ({ id: x.kieunt_id, text: x.kieu_nghiemthu }))
        this.cboNT_DV_BANList = options[0].id
        this.cboNT_DV_TC = options[0].id
      }
    },
    async sp_lay_loai_ip () {
      let input = { loaitbId: this.loaitb }
      let data = this.GetData(await api2.sp_lay_loai_ip(this.axios, input))
      if (data != null && data.length > 0) {
        let options = data.map((x) => ({ id: x.loaiip_id, text: x.loai_ip }))
        this.cboLoaiIP_BANList = options
        this.cboLoaiIP_TC = options[0].id
        this.cboLoaiIP_BAN = options[0].id
      } else {
        this.cboLoaiIP_BANList = []
      }
    },
    async sp_lay_kieu_kn () {
      let input = {}
      let data = this.GetData(await api2.sp_lay_kieu_kn(this.axios, input))
      if (data != null) {
        let options = data.map((x) => ({ id: x.kieukn_id, text: x.kieu_kn }))
        this.cboKieuKN_BANList = options
        this.cboKieuKN_BAN = options[0].id
        this.cboKieuKN_TC = options[0].id
      }
    },
    async sp_ds_chuquan () {
      await this.sp_ds_chuquan_ban()
      await this.sp_ds_chuquan_tc()
      let phanvungid = this.$root.token.getPhanVungID()
      let tinhthicongId = this.tinh.tinhthicong_id
      if (this.ds_cq_ban.filter((x) => x.tinh_id == phanvungid).length > 0) {
        this.cboChuQuan_Ban = this.ds_cq_ban.filter((x) => x.tinh_id == phanvungid)[0].chuquan_id
      }
      if (this.ds_cq_ban.filter((x) => x.tinh_id == tinhthicongId).length > 0) {
        this.cboChuQuanLD_Ban = this.ds_cq_ban.filter((x) => x.tinh_id == tinhthicongId)[0].chuquan_id
      }
      if (this.ds_cq_tc.filter((x) => x.tinh_id == phanvungid).length > 0) {
        this.cboChuQuan_TC = this.ds_cq_tc.filter((x) => x.tinh_id == phanvungid)[0].chuquan_id
      }
      if (this.ds_cq_tc.filter((x) => x.tinh_id == tinhthicongId).length > 0) {
        this.cboChuQuanLD_TC = this.ds_cq_tc.filter((x) => x.tinh_id == tinhthicongId)[0].chuquan_id
      }
    },
    async sp_ds_chuquan_ban () {
      let input = { pcqtc: 0 }
      let phanvungid = this.$root.token.getPhanVungID()
      let data = this.GetData(await api2.sp_ds_chuquan(this.axios, input))
      if (data != null) {
        this.ds_cq_ban = data
        let options = data.map((x) => ({ id: x.chuquan_id, text: x.tenchuquan }))
        this.cboChuQuan_BanList = options
        this.cboChuQuanLD_BanList = options
        if (data.filter((x) => x.tinh_id == phanvungid).length > 0) {
        } else {
        }
      } else {
        this.ds_cq_ban = []
      }
    },
    async load_dm_chu_quan () {
      let tinhthicongId = this.tinh.tinhthicong_id
      let input = { tinhthicong_id: tinhthicongId }
      let data = this.GetData(await api2.load_dm_chu_quan(this.axios, input))
      if (data != null) {
        this.ds_cq_tc = data
        let options = data.map((x) => ({ id: x.chuquan_id, text: x.tenchuquan }))
        this.cboChuQuan_TcList = options
        this.cboChuQuanLD_TCList = options
        if (data.filter((x) => x.tinh_id == tinhthicongId).length > 0) {
        } else {
        }
      } else {
        this.ds_cq_tc = []
      }
    },
    async sp_ds_chuquan_tc () {
      let tinhthicongId = this.tinh.tinhthicong_id
      let input = { pcqtc: 1, tinhthicong_id: tinhthicongId }
      let data = this.GetData(await api2.sp_ds_chuquan2(this.axios, input))
      if (data != null) {
        this.ds_cq_tc = data
        let options = data.map((x) => ({ id: x.chuquan_id, text: x.tenchuquan }))
        this.cboChuQuan_TcList = options
        this.cboChuQuanLD_TCList = options
        if (data.filter((x) => x.tinh_id == tinhthicongId).length > 0) {
        } else {
        }
      } else {
        this.ds_cq_tc = []
      }
    },
    async get_ts_kt_tthdkhdn () {
      try {
        let input = {}
        let data = this.GetData(await api2.get_ts_kt_tthdkhdn(this.axios, input))
        if (data != null && data.length > 0) {
          this.ts_kt_tthdkhdn = 1
        }
      } catch (err) {}
    },
    getcboTocDoBanList: async function () {
      let input = {}
      let rawdata = this.GetData(await api.tocdo_kenh(this.axios, input))
      console.log('🚀 ~ file: frmThongSoWan.vue:1624 ~ rawdata:', rawdata)
      if (rawdata == null || rawdata.length === 0) return
      let data = rawdata.map((x) => ({ id: x.tocdo_id, text: x.toc_do == null ? '' : x.toc_do }))
      this.cboTocDoBanList = data
      this.cboTocDo_PirBanList = data
      this.cboTocDo_NIXBanList = data
      this.cboTocDo_ISPBanList = data
      if (this.cboTocDoBanList != null && this.cboTocDoBanList.length > 0) {
        this.cboTocDoBan = this.cboTocDoBanList[0].id
        this.cboTocDo_PirBan = this.cboTocDoBanList[0].id
        this.cboTocDo_NIXBan = this.cboTocDoBanList[0].id
        this.cboTocDo_ISPBan = this.cboTocDoBanList[0].id
      }
    },
    getcboTocDoTCList: async function () {
      let input = { tinhthicong_id: this.tinh.tinhthicong_id }
      let rawdata = this.GetData(await api2.sp_ht_tocdo_kenh_combobox(this.axios, input))
      console.log('🚀 ~ file: frmThongSoWan.vue:1641 ~ rawdata:', rawdata)
      if (rawdata == null || rawdata.length === 0) return
      let data = rawdata.map((x) => ({ id: x.tocdo_id, text: x.toc_do == null ? '' : x.toc_do }))
      this.cboTocDoTCList = data
      this.cboTocDo_PirTCList = data
      this.cboTocDo_NIXTCList = data
      this.cboTocDo_ISPTCList = data
      if (this.cboTocDoTCList != null && this.cboTocDoTCList.length > 0) {
        this.cboTocDo_PirTc = this.cboTocDoTCList[0].id
        this.cboTocDoTc = this.cboTocDoTCList[0].id
        this.cboTocDo_ISPTc = this.cboTocDoBanList[0].id
        this.cboTocDo_NIXTc = this.cboTocDoTCList[0].id
      }
    },
    thoihan: async function () {
      let input = {}
      let data = this.GetData(await api.thoihan(this.axios, input))
      if (data == null || data.length == 0) return
      this.cboThoiHanBanList = data.map((x) => ({ id: x.thoihan_id, text: x.tenthoihan }))
      this.cboThoiHanTc = this.cboThoiHanBanList[0].id
      this.cboThoiHanBan = this.cboThoiHanBanList[0].id
    },
    async getcboPhanLoaiMGWList () {
      let input = {}
      let data = this.GetData(await api.phanloai_dv_cu_moi(this.axios, input))
      if (data == null || data.length == 0) return
      this.cboPhanLoaiMGWList = data.map((x) => ({ id: x.phanloai_id, text: x.phanloai }))
      this.cboPhanLoaiMGW = '' // this.cboPhanLoaiMGWList[0].id
    },
    async getcboPhanTachTDList () {
      let input = { vkieu: 1 }
      let data = this.GetData(await api2.sp_ds_phantach_tocdo(this.axios, input))
      if (data == null || data.length == 0) return
      this.cboPhanTachTDList = data.map((x) => ({ id: x.phantach_id, text: x.phantach }))
      this.cboPhanTachTD = '' // this.cboPhanTachTDList[0].id
    },
    async getcboChuQuan_BanList () {
      let input = {}
      let data = this.GetData(await api.chuquan(this.axios, input))
      if (data == null || data.length == 0) return
      this.cboChuQuan_BanList = data.map((x) => ({ id: x.chuquan_id, text: x.tenchuquan == null ? '' : x.tenchuquan }))
      this.cboChuQuan_Ban = this.cboChuQuan_BanList[0].id
      this.cboChuQuan_TC = this.cboChuQuan_BanList[0].id
    },
    async getcboTrangBiBanList () {
      let input = {}
      let data = this.GetData(await api.trangbi(this.axios, input))
      if (data == null || data.length == 0) return
      this.cboTrangBiBanList = data.map((x) => ({ id: x.trangbi_id, text: x.tentrangbi == null ? '' : x.tentrangbi }))
      this.cboTrangBiBan = this.cboTrangBiBanList[0].id
      // this.cboTrangBiTc = this.cboTrangBiBanList[0].id
    },
    async getcboTrangBiTCList () {
      let input = { tinhthicong_id: this.tinh.tinhthicong_id}
      let data = this.GetData(await api2.load_dm_trang_bi_tc(this.axios, input))
      if (data == null || data.length == 0) return
      this.cboTrangBiTCList = data.map((x) => ({ id: x.trangbi_id, text: x.tentrangbi == null ? '' : x.tentrangbi }))
      this.cboTrangBiTc = this.cboTrangBiTCList[0].id
    },
    async getcboLoaiKenhBanList () {
      let input = {}
      let data = this.GetData(await api.loai_kenh(this.axios, input))
      if (data == null || data.length == 0) return
      this.cboLoaiKenhBanList = data.map((x) => ({ id: x.loaikenh_id, text: x.loai_kenh }))
      this.cboLoaiKenhBan = this.cboLoaiKenhBanList[0].id
    },
    async getcboLoaiKenhTCList () {
      console.log('getcboLoaiKenhTCList', this.tinh)
      let input = { tinhthicong_id: this.tinh.tinhthicong_id }
      let data = this.GetData(await api2.sp_ht_loai_kenh_combobox(this.axios, input))
      if (data == null || data.length == 0) return
      this.cboLoaiKenhTCList = data.map((x) => ({ id: x.loaikenh_id, text: x.loai_kenh }))
      this.cboLoaiKenhTc = this.cboLoaiKenhTCList[0].id
    },
    getcboLoaiModemBanList: async function () {
      let input = {}
      let data = this.GetData(await api.loai_modem(this.axios, input))
      if (data == null || data.length == 0) return
      this.cboLoaiModemBanList = data.map((x) => ({ id: x.loaimd_id, text: x.loai_md == null ? '' : x.loai_md }))
      this.cboLoaiModemBan = this.cboLoaiModemBanList[0].id
    },
    getcboLoaiModemTCList: async function () {
      let input = {}
      let data = this.GetData(await api.loai_modem(this.axios, input))
      if (data == null || data.length == 0) return
      this.cboLoaiModemTCList = data.map((x) => ({ id: x.loaimd_id, text: x.loai_md == null ? '' : x.loai_md }))
      this.cboLoaiModemTc = this.cboLoaiModemTCList[0].id
    },
    getcboLoaiTBiBanList: async function () {
      let input = {}
      let data = this.GetData(await api.lay_ds_loai_tb_megawan(this.axios, input))
      if (data == null || data.length == 0) return
      this.cboLoaiTBiBanList = data.map((x) => ({ id: x.thietbidc_id, text: x.ten_tbi == null ? '' : x.ten_tbi }))
      this.cboLoaiTBiBan = this.cboLoaiTBiBanList[0].id
    },
    getcboLoaiTBiTCList: async function () {
      let input = { tinhthicong_id: this.tinh.tinhthicong_id }
      let data = this.GetData(await api2.lay_ds_loai_tb_megawan(this.axios, input))
      if (data == null || data.length == 0) return
      this.cboLoaiTBiTCList = data.map((x) => ({ id: x.thietbidc_id, text: x.ten_tbi == null ? '' : x.ten_tbi }))
      this.cboLoaiTBiTc = this.cboLoaiTBiTCList[0].id
    },
    async layMaTN_CD () {
      try {
        let kieu = this.GetData(await api2.map_id_kieuld_id(this.axios, { kieuld_id: this.cboKieuLD }))
        if (kieu === 2) {
          // Lắp mới trên đường có sẵn
          if (this.khachhang_id > 0) {

          } else {
            this.ShowError('Chưa có thông tin thuê bao để lấy danh sách mã truy nhập lắp trên đường có sẵn !')
          }
        } else if (kieu == 1) {
          // Lắp kèm
          if (this.hdkh_id > 0) {
          } else {
            // Kiểm tra xem có thuê bao mega đang thực hiện chuyển đổi không
            this.ShowError('Chưa có thông tin thuê bao để lấy danh sách mã truy nhập lắp kèm !')
          }
        }
      } catch (errlayMaTN_CD) {
        console.log('🚀 ~ file: frmThongSoWan.vue:1775 ~ layMaTN_CD ~ errlayMaTN_CD:', errlayMaTN_CD)
      }
    },
    ShowAlert: function (message) {
      this.$toast.error(message)
    },
    ShowError: function (message) {
      this.$toast.error(message)
    },
    ShowSuccess: function (message) {
      this.$toast.success(message)
    },
    async cboKieuLD_SelectedIndexChanged (val) {
      if (val != 0 && val != null) this.kieuldid = parseInt(val)
      else this.kieuldid = 0
      await this.layMaTN_CD()
      // mặc định
      this.cboTocDoTc_Enabled = true
      // chkTocDo_PIRBan_Enabled = true;
      this.cboTocDo_PirTc_Enabled = false
      this.cboLoaiKenhTc_Enabled = true
      this.chkTocDo_PIR = false
      this.chkTocDo_NIX = false
      this.chkTocDo_ISP = false
      if (this.loaitb == LoaiHinhTB.MGW_INTERNET_TRUCTIEP) {
        this.chkTocDo_NIX_Enabled = true
        this.chkTocDo_ISP_Enabled = true
        this.chkTocDo_NIX = true
        this.chkTocDo_ISP = true
      } else {
        this.chkTocDo_NIX_Enabled = false
        this.chkTocDo_ISP_Enabled = false
        this.chkTocDo_NIX = false
        this.chkTocDo_ISP = false
        this.cboTocDo_ISPTc_Enabled = false
        this.cboTocDo_NIXTc_Enabled = false
      }
      // end mặc định
      if (this.kieuldid == KieuLapDat.HNI_DOI_GIA_CUOC) {
        this.cboTocDoTc_Enabled = false
        this.chkTocDo_PIR_Enabled = false
        this.cboTocDo_PirTc_Enabled = false
        this.cboLoaiKenhTc_Enabled = false
        this.chkTocDo_NIX_Enabled = false
        this.cboTocDo_NIXTc_Enabled = false
        this.chkTocDo_ISP_Enabled = false
        this.cboTocDo_ISPTc_Enabled = false
      }
      if (this.kieuldid == KieuLapDat.HNI_DOI_TOCDO) {
        this.cboTocDoTc_Enabled = true
        this.chkTocDo_PIR_Enabled = true
        this.cboTocDo_PirTc_Enabled = false
        this.cboLoaiKenhTc_Enabled = false

        if (this.loaitb == LoaiHinhTB.MGW_INTERNET_TRUCTIEP) {
          this.chkTocDo_NIX_Enabled = true
          this.chkTocDo_ISP_Enabled = true
          if (this.cboPhanTachTD == '1') {
            this.chkTocDo_NIX = true
            this.chkTocDo_ISP = true
          } else {
            this.chkTocDo_NIX = false
            this.chkTocDo_ISP = false
          }
        } else {
          this.chkTocDo_NIX_Enabled = false
          this.chkTocDo_ISP_Enabled = false
          this.chkTocDo_NIX = false
          this.chkTocDo_ISP = false
          this.cboTocDo_ISPTc_Enabled = false
          this.cboTocDo_NIXTc_Enabled = false
        }
      }
      if (this.kieuldid == KieuLapDat.HNI_DOI_HUONG_KN) {
        this.cboTocDoTc_Enabled = false
        this.chkTocDo_PIR_Enabled = false
        this.cboTocDo_PirTc_Enabled = false
        this.cboLoaiKenhTc_Enabled = false

        this.chkTocDo_NIX_Enabled = false
        this.chkTocDo_ISP_Enabled = false
        this.chkTocDo_NIX = false
        this.chkTocDo_ISP = false
        this.cboTocDo_ISPTc_Enabled = false
        this.cboTocDo_NIXTc_Enabled = false
        if (this.cboTocDo_ISPBanList != null) this.cboTocDo_ISPTc = this.cboTocDo_ISPBanList[0].id
        if (this.cboTocDo_NIXBanList != null) this.cboTocDo_NIXTc = this.cboTocDo_NIXBanList[0].id
      }
      if (this.kieuldid == KieuLapDat.HNI_DOI_LOAIKENH) {
        this.cboTocDoTc_Enabled = false
        this.chkTocDo_PIR_Enabled = false
        this.cboTocDo_PirTc_Enabled = false
        this.cboLoaiKenhTc_Enabled = true

        this.chkTocDo_NIX_Enabled = false
        this.chkTocDo_ISP_Enabled = false
        this.chkTocDo_NIX = false
        this.chkTocDo_ISP = false
        this.cboTocDo_ISPTc_Enabled = false
        this.cboTocDo_NIXTc_Enabled = false
        if (this.cboTocDo_ISPBanList != null) this.cboTocDo_ISPTc = this.cboTocDo_ISPBanList[0].id
        if (this.cboTocDo_NIXBanList != null) this.cboTocDo_NIXTc = this.cboTocDo_NIXBanList[0].id
      }
      if (this.kieuldid == KieuLapDat.HNI_DOI_TOCDO_HUONGKN) {
        this.cboTocDoTc_Enabled = true
        this.chkTocDo_PIR_Enabled = true
        this.cboTocDo_PirTc_Enabled = false
        this.cboLoaiKenhTc_Enabled = false

        if (this.loaitb == LoaiHinhTB.MGW_INTERNET_TRUCTIEP) {
          this.chkTocDo_NIX_Enabled = true
          this.chkTocDo_ISP_Enabled = true
          this.chkTocDo_NIX = false
          this.chkTocDo_ISP = false
        } else {
          this.chkTocDo_NIX_Enabled = false
          this.chkTocDo_ISP_Enabled = false
          this.chkTocDo_NIX = false
          this.chkTocDo_ISP = false
          this.cboTocDo_ISPTc_Enabled = false
          this.cboTocDo_NIXTc_Enabled = false

          // this.cboTocDo_ISPTc.SelectedIndex = 0
          // this.cboTocDo_NIXTc.SelectedIndex = 0
          if (this.cboTocDo_ISPBanList != null) this.cboTocDo_ISPTc = this.cboTocDo_ISPBanList[0].id
          if (this.cboTocDo_NIXBanList != null) this.cboTocDo_NIXTc = this.cboTocDo_NIXBanList[0].id
        }
      }
      if (this.kieuldid == KieuLapDat.HNI_DOI_TOCDO_LOAIKENH) {
        this.cboTocDoTc_Enabled = true
        this.chkTocDo_PIR_Enabled = true
        this.cboTocDo_PirTc_Enabled = false
        this.cboLoaiKenhTc_Enabled = true

        if (this.loaitb == LoaiHinhTB.MGW_INTERNET_TRUCTIEP) {
          this.chkTocDo_NIX_Enabled = true
          this.chkTocDo_ISP_Enabled = true
          if (this.cboPhanTachTD == '1') {
            this.chkTocDo_NIX = true
            this.chkTocDo_ISP = true
          } else {
            this.chkTocDo_NIX = false
            this.chkTocDo_ISP = false
          }
        } else {
          this.chkTocDo_NIX_Enabled = false
          this.chkTocDo_ISP_Enabled = false
          this.chkTocDo_NIX = false
          this.chkTocDo_ISP = false
          this.cboTocDo_ISPTc_Enabled = false
          this.cboTocDo_NIXTc_Enabled = false
          if (this.cboTocDo_ISPBanList != null && this.cboTocDo_ISPBanList.length > 0) {
            this.cboTocDo_ISPTc = this.cboTocDo_ISPBanList[0].id
            this.cboTocDo_NIXTc = this.cboTocDo_ISPBanList[0].id
          }
        }
      }
      if (this.kieuldid == KieuLapDat.HNI_LOAIKENH_HUONGKN) {
        this.cboTocDoTc_Enabled = false
        this.chkTocDo_PIR_Enabled = false
        this.cboTocDo_PirTc_Enabled = false
        this.cboLoaiKenhTc_Enabled = true

        this.chkTocDo_NIX_Enabled = false
        this.chkTocDo_ISP_Enabled = false
        this.chkTocDo_NIX = false
        this.chkTocDo_ISP = false
        this.cboTocDo_ISPTc_Enabled = false
        this.cboTocDo_NIXTc_Enabled = false
        if (this.cboTocDo_ISPBanList != null && this.cboTocDo_ISPBanList.length > 0) {
          this.cboTocDo_ISPTc = this.cboTocDo_ISPBanList[0].id
          this.cboTocDo_NIXTc = this.cboTocDo_ISPBanList[0].id
        }
      }
      this.cboChuQuan_Ban_Enabled = this.cboChuQuan_TC_Enabled
      this.cboChuQuanLD_Ban_Enabled = this.cboChuQuanLD_TC_Enabled
      this.cboTocDoBan_Enabled = this.cboTocDoTc_Enabled
      this.cboTocDo_PirBan_Enabled = this.cboTocDo_PirTc_Enabled
      this.cboTocDo_NIXBan_Enabled = this.cboTocDo_NIXTc_Enabled
      this.cboTocDo_ISPBan_Enabled = this.cboTocDo_ISPTc_Enabled
      this.cboThoiHanBan_Enabled = this.cboThoiHanTc_Enabled
      this.cboLoaiTBiBan_Enabled = this.cboLoaiTBiTc_Enabled
      this.cboTrangBiBan_Enabled = this.cboTrangBiTc_Enabled
      this.cboLoaiModemBan_Enabled = this.cboLoaiModemTc_Enabled
      this.cboLoaiKenhBan_Enabled = this.cboLoaiKenhTc_Enabled
    },
    async cboPhanTachTD_SelectedValueChanged (val) {
      try {
        if (this.cboPhanTachTDList != null && this.cboPhanTachTDList.length > 0) {
          if (this.cboPhanTachTD == null) return
          if (this.cboPhanTachTD == '') return
          if (parseInt(val) === 1) {
            this.chkTocDo_ISP_Enabled = true
            this.chkTocDo_NIX_Enabled = true
            this.chkTocDo_ISP = true
            this.chkTocDo_NIX = true

            this.cboTocDo_ISPBan = 0
            this.cboTocDo_NIXBan = 0
            this.cboTocDo_ISPTc = ''
            this.cboTocDo_NIXTc = ''
          } else {
            this.chkTocDo_ISP_Enabled = false
            this.chkTocDo_NIX_Enabled = false
            this.chkTocDo_ISP = false
            this.chkTocDo_NIX = false
            this.cboTocDo_ISPBan_Enabled = false
            this.cboTocDo_NIXBan_Enabled = false
            this.cboTocDo_ISPBan = 0
            this.cboTocDo_NIXBan = 0
            this.cboTocDo_ISPBan = ''
            this.cboTocDo_NIXBan = ''
          }
        }
      } catch (ex) {
        this.ShowError(`Có lỗi:${ex}`) // VnID_986: Có lỗi:
      }
    },
    async cboLoaiKenhBan_SelectedValueChanged (val) {
      try {
        if (!this.kt_load) {
          let kt_kenh_tc = false
          if (this.cboLoaiKenhBanList.filter((x) => x.id == this.cboLoaiKenhTc)) {
            this.cboLoaiKenhTc = this.cboLoaiKenhBan
            kt_kenh_tc = true
          }
          if (!kt_kenh_tc) this.ShowError('Loại kênh không có bên tỉnh thi công!')
        }
        if (parseInt(this.cboLoaiKenhBan) == 9) {

        } else this.panelControl1_Visible = false
        this.panelControl2_Visible = false
      } catch (err) {
        console.log('🚀 ~ file: frmThongSoWan.vue ~ line 1586 ~ cboLoaiKenhBan_SelectedValueChanged ~ err', err)
      }
    },
    async cboTrangBiTc_SelectedIndexChanged (val) {
      try {
        this.loading(true)
        await this.lay_ds_mucuoc_tb_v2_tinh()
        this.loading(false)
      } catch (err) {
        console.log('cboTrangBiTc_SelectedIndexChanged', err)
      } finally {
        this.loading(false)
      }
    },
    async cboTocDoTc_SelectedIndexChanged (val) {
      try {
        this.loading(true)
        await this.lay_ds_mucuoc_tb_v2_tinh()
        this.loading(false)
      } catch (err) {
        console.log('cboTocDoTc_SelectedIndexChanged', err)
      } finally {
        this.loading(false)
      }
    },
    async cboDoiTuongTC_SelectedIndexChanged (val) {
      try {
        this.loading(true)
        await this.lay_ds_mucuoc_tb_v2_tinh()
        this.loading(false)
      } catch (err) {
        console.log('cboDoiTuongTC_SelectedIndexChanged', err)
      } finally {
        this.loading(false)
      }
    },
    async cboTrangBiBan_SelectedIndexChanged (val) {
      try {
        this.loading(true)
        await this.lay_ds_mucuoc_tb_v2()
        this.loading(false)
      } catch (err) {
        console.log('cboTrangBiBan_SelectedIndexChanged', err)
      } finally {
        this.loading(false)
      }
    },
    async cboDoiTuongBan_SelectedIndexChanged (val) {
      try {
        this.loading(true)
        await this.lay_ds_mucuoc_tb_v2()
        this.loading(false)
      } catch (err) {
        console.log('cboDoiTuongBan_SelectedIndexChanged', err)
      } finally {
        this.loading(false)
      }
    },
    async cboTocDoBan_SelectedIndexChanged (val) {
      console.log('🚀 ~ file: frmThongSoWan.vue:2065 ~ cboTocDoBan_SelectedIndexChanged ~ val:', val)
      try {
        let tocdoban_id = val
        if (this.cboTocDoTCList.filter((x) => x.id == tocdoban_id).length > 0) {
          this.cboTocDoTc = tocdoban_id
        } else {
          this.ShowError('Tốc độ CIR không có bên tỉnh thi công!')
        }
        this.loading(true)
        await this.lay_ds_mucuoc_tb_v2()
        this.loading(false)
      } catch (err) {
        console.log('cboDoiTuongBan_SelectedIndexChanged', err)
      } finally {
        this.loading(false)
      }
    },
    cboChuQuanLD_Ban_SelectedIndexChanged (val) {
      if (this.cboChuQuanLD_BanList != null && this.cboChuQuanLD_BanList.length > 0) {
        this.cboChuQuanLD_TC = val
      }
    },
    cboTocDo_PirBan_SelectedIndexChanged (val) {
      try {
        let _cboTocDo_PirBan = this.cboTocDo_PirBanList.filter((x) => x.id == val)
        let _tocdoban_text = _cboTocDo_PirBan[0].text
        let tocdotc = this.cboTocDo_PirTCList.filter((x) => x.text == _tocdoban_text)
        if (tocdotc.length > 0) {
          this.cboTocDo_PirTc = tocdotc[0].id
        } else {
          this.ShowError('Tốc độ PIR không có bên tỉnh thi công!')
        }
      } catch (error) {
        this.ShowError('Tốc độ PIR không có bên tỉnh thi công!')
      }
    },
    chkTocDo_NIX_CheckedChanged (val) {
      console.log('🚀 ~ file: frmThongSoWan.vue:2112 ~ chkTocDo_NIX_CheckedChanged ~ val:', val)
    },
    chkTocDo_ISP_CheckedChanged (val) {
      console.log('🚀 ~ file: frmThongSoWan.vue:2116 ~ chkTocDo_ISP_CheckedChanged ~ val:', val)
      this.cboTocDo_ISPBan_Enabled = val
      this.cboTocDo_ISPTc_Enabled = val
    },
    chkTocDo_PIR_CheckedChanged (val) {
      this.cboTocDo_PirBan_Enabled = val
      this.cboTocDo_PirTc_Enabled = val
    }
  }
}
</script>
<style scoped>
.box2-thongsomegawan {
  /* background-color: rgb(240, 240, 240); */
  padding-top: 0px !important;
  margin-top: -10px !important;
}
.box2-thongsomegawan .box-form {
  /* background-color: rgb(240, 240, 240); */
}
.box-form .legend-title {
}
.box1-thongsomegawan .box-form {
  /* background-color: rgb(227, 241, 254);; */
}
.box1-thongsomegawan {
  /* background-color: rgb(227, 241, 254); */
  padding-top: 0px !important;
}
</style>
