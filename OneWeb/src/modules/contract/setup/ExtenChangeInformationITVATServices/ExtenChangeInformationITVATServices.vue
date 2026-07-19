<template src="./ExtenChangeInformationITVATServices.html"></template>
<style src="./ExtenChangeInformationITVATServices.scss" scoped></style>
<script>
import breadcrumb from '@/components/breadcrumb'
import api from './API.js'
import api_installnewsubs from '@/modules/contract/setup/InstallNewSubs/InstallNewSubsAPI.js'
import DataGrid from '@/components/Controls/DataGrid'
import moment from 'moment'
import DatePicker from 'vue2-datepicker'
import 'vue2-datepicker/locale/vi.js'
import 'vue2-datepicker/index.css'
import frmGiaHanHangLoat from './frmGiaHanHangLoat.vue'
import { DatabaseConstants } from '@/modules/contract/setup/InstallNewSubs/Enums.js'
import { KieuCaiDatDVGT, LoaiHopDong, TrangThaiHD, DichVuVienThong, listDichVuCNTT, TRANGBI, KieuLapDat, KHOANMUC_TT, LoaiHinhTB, Loai_KM, NHOM_LNV, LOAI_DV } from '@/modules/admin/category/MultiServicePackage/enum.js'
import ModalNormalizeCAinformation from '@/modules/contract/setup/NormalizeCAinformation/ModalNormalizeCAinformation.vue'
import ModalChonDiaChi from '@/modules/contract/setup/InstallNewSubs/Popups/popupChonDiaChi'
import UploadEProfileContent from '@/modules/contract/profile/UploadEProfile/UploadEProfile_content.vue'
import BssErrorMarker from '@/components/BssErrorMarker'
import BssRequiredMarker from '@/components/BssRequiredMarker'
import { required, minValue, maxValue } from 'vuelidate/lib/validators'
import { CheckBoxSelection } from '@syncfusion/ej2-vue-dropdowns'
import { DataManager, Query, ODataV4Adaptor, Predicate } from '@syncfusion/ej2-data'
import { L10n } from '@syncfusion/ej2-base'
import { async } from 'pdfmake/build/pdfmake.js'

import { fn_kiemtra_thongtin_hopdong, lay_tt_hdtb_sub } from '../z_common_func/setup_common'
import API from '../InstallNewSubs/InstallNewSubsAPI'
const frmNhapDSThanhVien = () => import('@/modules/contract/setup/ChangeContractOwner/popup/frmNhapDSThanhVien.vue')

const frmNhapDSThanhVienNgoaiMang = () => import('@/modules/contract/setup/ChangeContractOwner/popup/frmNhapDSThanhVienNgoaiMang.vue')
// const HuyVoiceBrnNgoaiMang = () => import('@/modules/contract/setup/HuyVoiceBrnNgoaiMang/index.js')
//  import HuyVoiceBrnNgoaiMang from '@/modules/contract/setup/HuyVoiceBrnNgoaiMang/HuyVoiceBrnNgoaiMang.vue'

L10n.load({
  'vi-VN': {
    dropdowns: {
      noRecordsTemplate: 'Không có dữ liệu',
      actionFailureTemplate: ''
    }
  }
})

export default {
  components: {
    breadcrumb,
    DataGrid,
    DatePicker,
    ModalNormalizeCAinformation,
    ModalChonDiaChi,
    UploadEProfileContent,
    BssErrorMarker,
    BssRequiredMarker,
    frmGiaHanHangLoat,
    frmNhapDSThanhVien,
    frmNhapDSThanhVienNgoaiMang,
    // HuyVoiceBrnNgoaiMang
  },
  name: 'ExtenChangeInformationITVATServices',
  validations: {
    diachiTB: {
      DIACHI: {
        required
      }
    },
    diachiLD: {
      DIACHI: {
        required
      }
    }
  },
  provide: {
    multiselect: [CheckBoxSelection]
  },
  data() {
    return {
      button: {
        btnNhapMoi: { Enabled: true, Visible: true },
        btnCapNhat: { Enabled: true, Visible: false },
        btnHuyBo: { Enabled: true, Visible: false },
        btnXoaPhieu: { Enabled: true, Visible: false },
        tsbtnThanhToan: { Enabled: true, Visible: false },
        tsbtnGiaoPhieu: { Enabled: true, Visible: false },
        tsbtnHoanThienHS: { Enabled: false, Visible: false },
        btnThayDoiDiaChiLD: { Enabled: false, Visible: true },
        btnThayDoiDiaChiTB: { Enabled: false, Visible: true },
        btnDongBoHDDT: { Enabled: true, Visible: false },
        btnThongTinTBBS: { Text: 'Thông tin TB' }
      },
      header: {
        title: 'Gia hạn dịch vụ CNTT/GTGT',
        list: [
          // { name: 'Trang chủ', link: { name: 'Ui.cards' } },
          // { name: "Lập hợp đồng", link: { name: "Ui.cards" } },
          // { name: "Đổi tốc độ - Loại hình thuê bao", link: { name: "Ui.cards" } },
          {
            name: 'Gia hạn dịch vụ CNTT/GTGT',
            link: { name: 'Ui.cards' },
            active: true
          }
        ]
      },
      dateFormat: 'DD/MM/YYYY HH:mm:ss',
      position: { X: 'center', Y: 'center' },
      tt_nd: {
        phanvung_id: 0, //this.$root.token.getPhanVungID(),
        donvi_id: 0, //this.$root.token.getDonviID(),
        nhanvien_id: 0, //this.$root.token.getNhanVienID(),
        ip_cn: '',
        may_cn: '',
        nguoi_cn: ''
      },
      DS_KIEULD_THAYDOI_GOICUOC: [],
      dsDC_HDTB: [],
      dsKM_HDTB: [],
      ds_dichvuvt: [],
      ds_kieuld: [],
      DANHMUC_TIENCTKM: [],
      m_dsTienCTKM: [],
      ds_loaihinhtb: [],
      DTDS_DVGT: [],
      DT_DBTBSUB: [],
      DT_DIACHI_TB: [],
      DS_PHOPHUONG: [],
      LAYDANHBACT: [],
      _hdtbsub: {},
      saveList: [],
      DS: [],
      DSKHUYENMAI: [],
      DSTRATRUOC: [],
      m_dsThueBao_DichVu: [], //DS_DK_DVGT: STT,MA_DVGT,TEN_DVGT,SUDUNG,KIEU_YC,DANGKY,HUY,NOIDUNG,DICHVUGT_ID,THUEBAO_ID,HDTB_ID,CUOC_LD,VAT_LD,TIEN,CUOC_SD,VAT_SD,FLAG,THONGBAO,GHICHU,KIEU_DL,SUA,NHOM_GOI,GOI_VASC,TRANGTHAITB_ID,CUOC_KMLD,VAT_KMLD,TYLE_VAT,TYLE_VAT_ID,SL_CHA,LOCK_DAU,BLOCK_TIEP,GIA_BLOCK_TIEP,VAT_BLOCK_TIEP,HE_SO
      DTMUCCUOCTB: [],
      DT_LUONG: [],
      FN_LAY_KIEULD_ElEARNING: '',
      FN_KIEMTRA_HIENTHI_THIETBI_THEO_LOAITB: '',
      FN_KIEMTRA_HIENTHI_LOAITB_NGAYBD_SYSTEMDATE : '',
      TUDONG_TINHSOTHANG_SD_THEO_GOICUOC: '',
      FN_TAO_NGAYBD_NGAYKT_KHI_GIAHAN :'',
      SOTHANG_TRONG_DB : 0,
      SO_THANG: 0,
      SOTHANG_SD: 0,
      SL: 0,
      loaikm: 0,
      ts_sinhma_gd_theo_donvi: false,
      kiemtra_no_tratruoc: 0,
      batbuoc_ctv_giahan_cntt: 0,
      batbuoc_nguoigt_giahan_cntt: 0,
      chuyen_ct: false,
      dsHDTB_SUB: null,
      // Top CTV, Người GT
      tbMaDuAn: '',
      chkCTV: false,
      ctv_id: 0,
      txtCTV: '',
      chkNguoiGT: false,
      nguoigt_id: 0,
      txtNguoiGT: '',
      popup: {frmNhapDSThanhVien: false},
      dialogComponent: null,
      popupComponent: null,
      popupComponentData: null,
      popupComponentAttr: {},
      popupComponentEvts: {
        'form-close': this.popupClosed,
        acceptChangeCTV: this.popupClosed,
        acceptChangeNGT: this.popupClosed,
        PopUpCongTacVien_Thoat: this.popupClosed
      },
      popupComponentName: '',
      popupComponentHeader: '',
      tieuchi_id: '',
      thongtin_tracuu: '',
      thamsomacdinh: {},
      //Thông tin phiếu yêu cầu
      txtMaGD: { value: '', Enabled: true },
      dtpNgayYC: { value: '', Enabled: false, ReadOnly: true },
      cboDichVuVT: { value: 0, Enabled: false },
      cboLoaiHinhTB: { value: 0, Enabled: false },
      txtMaHD: { value: '', Enabled: false, ReadOnly: true },
      dtpNgayLapHD: { value: '', Enabled: false, ReadOnly: true },
      cboKieuLD: { value: 0, Enabled: false },
      // Thông tin khách hàng
      txtMaKH: { value: '', Enabled: false, ReadOnly: true },
      cboMucCuocTB: { value: 0, Enabled: true },
      txtTenKH: { label: '', value: '', Enabled: false, ReadOnly: true },
      txtDiaChiKH: { value: '', Enabled: false, ReadOnly: true },
      // Thông tin thuê bao
      txtMaTB: { value: '', Enabled: true },
      txtTrangThaiTB: { value: '', Enabled: false, ReadOnly: true },
      txtTenTB: { label: '', value: '', Enabled: false, ReadOnly: true },
      txtDiaChiTB: { value: '', Enabled: false, ReadOnly: true },
      txtDiaChiLD: { value: '', Enabled: false, ReadOnly: true },
      chkKhuyenMai: { Checked: false, Enabled: false },
      cboKhuyenMai: { value: 0, Enabled: false },
      btnChonKhuyenMai: { Enabled: false },
      btnCTKhuyenMai: { Enabled: false },
      chkTraTruoc: { Checked: false, Enabled: false },
      cboTraTruoc: { value: 0, Enabled: false },
      btnChonTraTruoc: { Enabled: false },
      btnCTTraTruoc: { Enabled: false },
      txtTongTien: { value: '', Enabled: true, ReadOnly: true },
      txtTongVat: { value: '', Enabled: true, ReadOnly: true },
      txtGhiChu: { value: '', Enabled: false, ReadOnly: true },
      //
      // dichvuvt_id_ct: 0,
      // ma_tb_ct: 0,
      khachhang_id: 0,
      hdkh_id: 0,
      thuebao_id: 0,
      hdtb_id: 0,
      kieukh_id: 0,
      loaikh_id: 0,
      nguoi_dd: '',
      vloaihd_id: LoaiHopDong.GIAHAN,
      vdoituong_id: 0,
      tocdo_id: 0,
      vmuccuoc_id: 0,
      muccuoc_id: 0,
      linhvuc_id: 0,
      vluong_id: 0,
      vquytrinh_id: 0,
      vhuonggiao_id: 0,
      // vkieuld_id_cbo: 0,
      // vloaitb_id_cbo: 0,
      HDTB_CNTT: [],
      ma_gd: '',
      huonggiao_tn: 0,
      quytrinh_id: 0,
      phuongld_id: 0,
      phold_id: 0,
      apld_id: 0,
      khuld_id: 0,
      trangbi_id: 0,
      ctv_id: 0,
      // donvi_ctv_id: 0,
      // loainv_ctv_id: 0,
      dtiendatcoc_tt: 0,
      dvatdatcoc_tt: 0,
      dtiendatcoc_km: 0,
      dvatdatcoc_km: 0,
      kmtt_id: -1,
      tien_kmtt: 0,
      tien_vat: 0,

      dtienld: 0,
      dvatld: 0,
      dtiencd: 0,
      dvatcd: 0,
      dtien_dv: 0,
      dvat_dv: 0,
      dvattbi: 0,
      dtientbi: 0,
      dtienkmld: 0,
      dvatkmld: 0,
      dtienkmdc: 0,
      dvatkmdc: 0,
      dtien_kmtb: 0,
      dvat_kmtb: 0,
      tien_dv: 0,
      vat_dv: 0,
      tien_kmld: 0,
      vat_kmld: 0,
      tyle_kmld: 0,
      //longdx: biến dùng cho trả trước
      tien_kmld_tt: 0,
      vat_kmld_tt: 0,
      tyle_kmld_tt: 0,
      tong_kmld_tt: 0,
      vat_tbi: 0,
      tien_tbi: 0,
      dsCTM_TBI: [],
      themmoi_tienkm: false,
      layoutDV: [],
      ds_init: [],
      dycDVCNTT: [],
      grcDanhSach: {
        list: [],
        header: [
          {
            fieldName: 'ma_tb',
            headerText: 'Số máy/Acc',
            allowFiltering: true
          },
          {
            fieldName: 'ten_tb',
            headerText: 'Tên thuê bao',
            allowFiltering: true
          },
          {
            fieldName: 'diachi_tb',
            headerText: 'Địa chỉ TB',
            allowFiltering: true
          },
          {
            fieldName: 'diachi_ld',
            headerText: 'Địa chỉ lắp đặt',
            allowFiltering: true
          },
          {
            fieldName: 'trangthai_hd',
            headerText: 'Trạng thái hợp đồng',
            allowFiltering: true
          },
          {
            fieldName: 'hdtb_id',
            headerText: 'Hợp đồng thuê bao ID',
            allowFiltering: true
          }
        ],
        value: {},
        isEnabled: true,
        checked: []
      },
      diachiLD: {
        DIACHI: '',
        DIACHI_ID: 0,
        TINH_ID: 0,
        QUAN_ID: 0,
        PHUONG_ID: 0,
        PHO_ID: 0,
        AP_ID: 0,
        KHU_ID: 0,
        DACDIEM_ID: 0,
        SO_NHA: '',
        Lng: 0,
        Lat: 0,
        BLOCK: '',
        TANG: 0,
        PHONG: 0,
        MOTA: '',
        DIACHI_TEXT: ''
      },
      diachiTB: {
        DIACHI: '',
        DIACHI_ID: 0,
        TINH_ID: 0,
        QUAN_ID: 0,
        PHUONG_ID: 0,
        PHO_ID: 0,
        AP_ID: 0,
        KHU_ID: 0,
        DACDIEM_ID: 0,
        SO_NHA: '',
        Lng: 0,
        Lat: 0,
        BLOCK: '',
        TANG: 0,
        PHONG: 0,
        MOTA: '',
        DIACHI_TEXT: ''
      },
      lbDichVuGT: {
        list: [],
        value: null,
        isEnabled: true,
        checked: []
      },
      lbMuaThietBi: {
        list: [],
        value: null,
        isEnabled: true,
        checked: []
      },
      check_loaitb_id: '',
      modalTitle: '',
      isShowDynamicPopup: false,
      isShow_check_data_edu: false,

      // dialogEvts: {
      //   close: this.dlgClose
      // },
    }
  },
  created() {
    this.$root.$on('bv::modal::shown', (bvEvent, modalId) => {
      document.getElementsByClassName('modal-content').forEach(function(v) {
        v.removeAttribute('tabindex')
      })
    })
  },
  methods: {
    tsbtn_importcts_click: function() {
      //console.log(this.$refs['frmImportCA']);
      let self = this
      let dulieu = {
        hdkh_id: self.hdkh_id,
        ma_gd: self.ma_gd
      }
      if (self.hdkh_id == 0) {
        this.$toast.error('Vui lòng ấn cập nhật trước, sau đó ấn nút Import CA!')
        return
      }
      console.log(dulieu)
      this.$refs.frmImportCA.openDialog(dulieu)
    },
    btnNhapMoi_Click: function() {
      this.THAOTAC_NUT(1)
    },
    btnCapNhat_Click: function() {


      var thongbao = !this.button.btnNhapMoi.Enabled ? 'thêm mới' : 'cập nhật'
      this.$bvModal
        .msgBoxConfirm('Bạn có muốn ' + thongbao + ' hợp đồng không?', {
          title: 'Thông báo',
          size: 'sm',
          okTitle: 'Đồng ý',
          cancelTitle: 'Hủy'
        })
        .then(async (v) => {
          if (v) {
            this.loading(true)
            var themmoi = !this.button.btnNhapMoi.Enabled
            var ketqua_kiemtra = await this.kiemtra_tt_capnhat(themmoi)
            // console.log(ketqua_kiemtra);
            if (ketqua_kiemtra == 'OK') await this.CAPNHAT()
            this.loading(false)
          }
        })
        .catch((e) => {
          console.log(e)
          this.ShowError('Lỗi ghi lại dữ liệu')
          this.loading(false)
        })
    },
    btnHuyBo_Click() {
      this.THAOTAC_NUT(3)
    },
    btnXoaPhieu_Click() {
      this.$bvModal
        .msgBoxConfirm('Bạn thật sự muốn xóa hợp đồng không?', {
          title: 'Thông báo',
          size: 'sm',
          okTitle: 'Đồng ý',
          cancelTitle: 'Hủy'
        })
        .then(async (v) => {
          if (v) {
            this.loading(true)
            await this.XoaHDKH()
            this.loading(false)
          }
        })
        .catch((e) => {
          console.log(e)
          this.ShowError('Lỗi xóa hợp đồng')
          this.loading(false)
        })
    },
    async tsbtnThanhToan_Click() {
      try {
        this.loading(true)
        if (!this.hdkh_id || this.hdkh_id == 0) {
          this.ShowError('Hãy ghi lại hợp đồng trước khi thanh toán!')
          this.loading(false)
          return
        }

        if (!this.ds_tien_khoanmuc || this.ds_tien_khoanmuc.length == 0) {
          this.ShowError('Nghiệp vụ không thanh toán, bạn hãy giao phiếu!')
          this.loading(false)
          return
        }

        let kiemtra_thongtin =  await fn_kiemtra_thongtin_hopdong(this.hdtb_id)
        if (kiemtra_thongtin != 'OK') {
          this.ShowError(kiemtra_thongtin)
          this.loading(false)
          return
        }

        /***
         * Kiểm tra các dịch vụ và kiểu lắp đặt hiển thị thông báo trước khi thanh toán gia hạn
        **/
        try {
          this.loading(true);
          const sp_lay_ds_nghiepvu_cntt_common = await (api.sp_lay_ds_nghiepvu_cntt_common(this.axios, {
              p_nghiepvu: 'KIEMTRA_HIENTHI_POPUP_THONGBAO_TRUOC_THANHTOAN_GIAHAN',
              p_ds_para: JSON.stringify({
                  loaitb_id: Number(this.cboLoaiHinhTB.value),
                  kieuld_id: Number(this.cboKieuLD.value),
                  hdtb_id: this.hdtb_id,
                  loaihd_id: 41
              })
          }))
          this.loading(false);
          const nghiepvu_cntt_common = sp_lay_ds_nghiepvu_cntt_common.data.data.result[0].result
          console.log(nghiepvu_cntt_common)
          if (nghiepvu_cntt_common == 'OK') {
            console.log(sp_lay_ds_nghiepvu_cntt_common.data.data.data)
            console.log('Vào OK')
            const dynamic_api_input = sp_lay_ds_nghiepvu_cntt_common.data.data.data
            console.log(dynamic_api_input != null)
            if(dynamic_api_input != null){
              console.log('Vào ketqua_dynamic')
              const ketqua_dynamic = await api.cntt_dynamic_api(this.axios, dynamic_api_input)
              console.log(ketqua_dynamic)
              return
            }
            else{
              console.log('else')
              let thongtin_xacnhan_str = await api.fn_thongbao_kichhoat_dvcntt_common(this.axios, {vhdtb_id: this.hdtb_id, vkey: 'KIEMTRA_HIENTHI_POPUP_THONGBAO_TRUOC_THANHTOAN_GIAHAN'})
              const h = this.$createElement
              const titleVNode = h('div', { domProps: { innerHTML: thongtin_xacnhan_str.data.data } })
              let cfr = await this.$bvModal
                .msgBoxConfirm([titleVNode], {
                  title: 'Vui lòng xác nhận lại thông tin',
                  size: 'lg',
                  okTitle: 'Đã đúng thông tin',
                  cancelTitle: 'Chưa đúng, để tôi cập nhật lại',
                  centered: true,
                  modalClass: 'custom-modal-height'
                })
                .then(async (v) => {
                  if (!v) {
                    return
                  }
                  return v
                }) 
                if (!cfr) {
                throw "Dừng lại để kiểm tra thông tin"
                } 
            }
          }         
        } catch (e) {
            this.loading(false);
            return
        } finally {
            this.loading(false);
        }

        var input = {
          THUEBAO_ID: this.thuebao_id,
          LOAITB_ID: Number(this.cboLoaiHinhTB.value),
          HDKH_ID: this.hdkh_id,
          HDTB_ID: this.hdtb_id,
          KIEULD_ID: Number(this.cboKieuLD.value)
        }
        var kiemtra_thanhtoan = await this.lay_tt_tsbtnthanhtoan(input)
        if (kiemtra_thanhtoan == 'OK') {
          this.loading(false)
          this.$router.push({
            name: 'Payment',
            params: {
              magd: this.txtMaGD.value,
              dichvuId: Number(this.cboDichVuVT.value),
              loaihdId: this.vloaihd_id
            }
          })
        }
      } catch (ex) {
        console.log(ex)
        this.ShowError('Lỗi thao tác thanh toán')
      } finally {
        this.loading(false)
      }
    },
    async tsbtnGiaoPhieu_Click() {
      var input = { quytrinh_id: this.vquytrinh_id, tthd_id: 2 }
      var ds_huongiao = this.GetData(await api.Lay_HuongGiao_QuyTrinh(this.axios, input))
      var vhuonggiao_id2 = 0
      if (ds_huongiao && ds_huongiao.length > 0) {
        vhuonggiao_id2 = Number(ds_huongiao[0].huonggiao_id)
      } else {
        this.$toast.error('Không tìm thấy luồng quy trình nghiệp vụ này.\nBạn hãy liên hệ Admin kiểm tra lại')
        return
      }

      if (vhuonggiao_id2 != 0) {
        this.$router.push({
          path: 'HandoverTicket',
          query: {
            magd: this.txtMaGD.value,
            madv: Number(this.cboDichVuVT.value),
            huonggiao_id: vhuonggiao_id2,
            hdtb_id: this.hdtb_id,
            // luong_id: 0,
            loaihd_id: LoaiHopDong.GIAHAN,
            quytrinh_id: this.vquytrinh_id
          }
        })
      }
    },
    tsbUpHoSo_Click() {
      try {
        if (this.$refs.grcDanhSach.getSelectedRecords().length == 0) {
          this.$toast.error('Bạn chưa chọn thuê bao nào')
          return
        }
        this.txtMaTB.value = this.$refs.grcDanhSach.getSelectedRecords()[0]['ma_tb']
        this.$bvModal.show('frmUploadEProfile')
      } catch (err) {
        this.$toast.error(err.message)
      }
    },
    tsbtnGoiDaDV_Click: function() {
      if (this.hdkh_id != 0) {
        let query = {
          ma_gd: this.txtMaGD.value
        }
        let routeData = this.$router.resolve({
          name: 'DangKyGoiDaDichVu',
          query: query
        })
        window.open(routeData.href, '_blank')
      }
    },
    cboKieuLD_ButtonClick: function() {
      try {
        this.THAOTAC_NUT(1)
        this.kieulapdat_loaihinhtb(41, this.cboLoaiHinhTB.value)
      } catch (ex) {}
    },
    btnChonKM_click: function() {
      try {
        this.loaikm = Loai_KM.KhuyenMai
        this.ShowPopupChonKhuyenMai()
      } catch (ex) {}
    },
    btnChonTT_click: function() {
      try {
        this.loaikm = Loai_KM.TraTruoc
        this.ShowPopupChonKhuyenMai()
      } catch (ex) {}
    },
    btnChiTietKM_click: function() {
      try {
        this.loaikm = Loai_KM.KhuyenMai
        this.ShowPopupChiTietKhuyenMai()
      } catch (ex) {}
    },
    btnChiTietTT_click: async function() {
      try {
        this.loaikm = Loai_KM.TraTruoc
        this.ShowPopupChiTietKhuyenMai()
      } catch (ex) {}
    },
    btnTienCTKM_Click: async function() {
      try {
        // if (!this.ds_tien_khoanmuc || this.ds_tien_khoanmuc.length == 0) {
        //   this.ShowError("Không có danh sách tiền khoản mục");
        //   return;
        // }
        await this.popupTienKM()
      } catch (ex) {}
    },
    btnDichVuGT_Click() {
      this.popupDVCongThem()
      // frmDangKyDVGT f = new frmDangKyDVGT(ds_dk_dvgt, Number(cboDichVuVT.value), Convert.ToDateTime(dtpNgayYC.value), vkieuld_id_cbo == 13189
      //     && cboLoaiHinhTB.value.toString() != "295"
      //     && cboLoaiHinhTB.value.toString() != "294", _sl_cha);
      // // f.vsl_cha = _sl_cha;
      // f.ShowDialog();
    },
    async btnMuaThietBi_Click() {
      this.popupMuaTB()
    },
    txtMaGD_KeyPress: async function() {
      this.loading(true)
      try {
        if (this.txtMaGD.value != '') {
          var dt = await this.LAY_DS_HOPDONG_THEO_MA_GD()
          if (dt.length == 0) {
            this.ShowError('Không tìm thấy hợp đồng có mã giao dịch ' + this.txtMaGD.value)
            return
          }
          dt = this.UpperCasePropertyList(dt)
          if (dt[0]['DICHVUVT_ID'] && Number(this.cboDichVuVT.value) != Number(dt[0]['DICHVUVT_ID'])) {
            this.cboDichVuVT.value = Number(dt[0]['DICHVUVT_ID'])
            await this.txtMaGD_KeyPress()
            return
          }
          this.cboKieuLD.value = Number(dt[0]['KIEULD_ID'])
          this.cboLoaiHinhTB.value = Number(dt[0]['LOAITB_ID'])
          var row_init = dt.filter((x) => Number(x.KIEULD_ID) == Number(this.cboKieuLD.value))
          // console.log(dt);
          if (row_init.length > 0) {
            await this.HienThiTTHopDongKH(row_init)
          } else {
            this.ShowError('Không tìm được hợp đồng')
          }
        }
      } catch (ex) {
        this.loading(false)
      } finally {
        this.loading(false)
      }
    },
    btnLayMaGD_Click: function() {
      this.$refs.ModalNormalizeCAinformation.showModal()
    },
    timthongtinHD: async function(data) {
      this.loading(true)
      try {
        this.txtMaTB.value = data.ma_tb
        this.txtMaGD.value = data.ma_gd
        this.TIMKIEM_MATB()
      } catch (ex) {
        this.loading(false)
      }

      this.loading(false)
    },
    txtMaTB_KeyPress: async function() {
      //hpg_track_00001668
      this.loading(true)
      try {
        await this.TIMKIEM_MATB()
      } catch (ex) {
        this.loading(false)
      }

      this.loading(false)
    },
    btnTKMaTB_Click: function() {
      this.$refs.ModalNormalizeCAinformation.showModal()
    },
    ShowPopupSearchAccount: function() {
      this.$refs.popupSearchAccount.show()
    },
    acceptTimKiemMaTB: async function(item) {
      if (item) {
        this.txtMaTB.value = item.ma_tb
        // this.ma_kh = item.ma_kh;
        // this.ma_tt = val.ma_tt
        this.$refs.popupSearchAccount.hide()
      }
    },
    async XoaHDKH() {
      try {
        // if (!this.button.btnNhapMoi.Enabled) {
        //   this.ShowError("Không đúng trạng thái không thể xoá");
        //   return;
        // }
        if (this.hdkh_id <= 0 || this.hdtb_id <= 0) {
          this.ShowError('Không lấy được thông tin HĐ không thể xoá')
          return
        }

        await this.fn_xoahd_kh(this.hdtb_id, this.hdkh_id)
      } catch (ex) {
        // this.ShowError("Có lỗi khi xoá hợp đồng " + ex);
      }
    },
    fn_xoahd_kh: async function(hdtb_id, hdkh_id) {
      try {
        var para = { HDTB_ID: hdtb_id, HDKH_ID: hdkh_id }
        var input = {
          ds_para: JSON.stringify(para),
          phanvung_id: this.tt_nd.phanvung_id
        }
        let response = await api.fn_xoahd_kh_giahancntt(this.axios, input)
        if (response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000') {
          var data = JSON.parse(response.data.data)
          if (data.ERROR_CODE == '1') {
            this.ShowSuccess('Xóa hợp đồng khách hàng thành công!')
            this.THAOTAC_NUT(3)
          } else {
            this.ShowError(data.MESSAGE)
          }
        } else {
          this.ShowError('Xóa hợp đồng khách hàng không thành công!')
        }
      } catch (ex) {
        console.log(ex)
        this.ShowError('Lỗi xóa hợp đồng khách hàng')
      }
    },
    lay_tt_tsbtnthanhtoan: async function(dt) {
      try {
        var para = {
          THUEBAO_ID: dt.THUEBAO_ID,
          LOAITB_ID: dt.LOAITB_ID,
          HDKH_ID: dt.HDKH_ID,
          HDTB_ID: dt.HDTB_ID,
          KIEULD_ID: dt.KIEULD_ID
        }
        var input = {
          ds_para: JSON.stringify(para),
          phanvung_id: this.tt_nd.phanvung_id
        }
        let response = await api.lay_tt_tsbtnthanhtoan(this.axios, input)
        if (response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000') {
          var data = JSON.parse(response.data.data)
          if (data.ERROR_CODE == '1') {
            return 'OK'
          } else {
            this.ShowError(data.MESSAGE)
            return 'NOK'
          }
        } else {
          this.ShowError('Lấy thông tin thanh toán không thành công!')
          return 'NOK'
        }
      } catch (ex) {
        console.log(ex)
        this.ShowError('Lỗi lấy thông tin thanh toán')
      }
    },
    CAPNHAT: async function() {
      try {
        // Kiểm tra dữ liệu và quy trình
        if (!this.KIEMTRA_DULIEU()) {
          return
        }
        var huonggiao_tn = 0
        var quytrinh_id = 0
        if (!this.button.btnNhapMoi.Enabled) {
          huonggiao_tn = this.huonggiao_tn
          quytrinh_id = this.quytrinh_id
        }
        // Tạo dữ liệu
        // bool kiemtra;
        var dshd_cntt = {}
        var themmoi = !this.button.btnNhapMoi.Enabled
        var dt_hdkh = await this.TaoDuLieu_HDKH(themmoi)
        dshd_cntt.vHdKhach = dt_hdkh.vHdKhach
        var kiemtra = dt_hdkh.ketqua

        var dt_hdtb = await this.TaoDuLieu_HDTB(quytrinh_id, kiemtra, themmoi)
        dshd_cntt.vHdTB = dt_hdtb.vHdTB
        kiemtra = dt_hdtb.ketqua

        var dt_hdtb_dv = this.TaoDuLieu_HDTB_DV(kiemtra, themmoi)
        dshd_cntt.vdsHdTB_Dv = dt_hdtb_dv.vdsHdTB_Dv
        kiemtra = dt_hdtb_dv.ketqua

        var dt_hdtb_cntt = this.TaoDuLieu_HDTB_CNTT(kiemtra, themmoi)
        dshd_cntt.vHdCntt = dt_hdtb_cntt.vHdCntt
        kiemtra = dt_hdtb_cntt.ketqua

        var dt_dvgt = await this.TaoDuLieu_DVGT(kiemtra)
        dshd_cntt.vdsDANGKY_DVGT = dt_dvgt.vdsDANGKY_DVGT
        kiemtra = dt_dvgt.ketqua

        var dt_muaTBI = await this.TaoDuLieu_MuaTBI(kiemtra)
        dshd_cntt.vdsMua_TBI = dt_muaTBI.vdsMua_TBI
        kiemtra = dt_muaTBI.ketqua

        var dt_ct_tienhd = await this.TaoDuLieu_CT_TIENHD(kiemtra, themmoi)
        dshd_cntt.vdsCT_TIENHD = dt_ct_tienhd.vdsCT_TIENHD
        kiemtra = dt_ct_tienhd.ketqua

        if (!kiemtra) {
          return
        }
        var kt_taodl = this.KIEMTRA_TAODL(dshd_cntt, themmoi)
        if (!kt_taodl) return

        var json_hdkh = ''
        if (Object.keys(dshd_cntt.vHdKhach).length > 0) {
          for (var i = 0; i < Object.keys(dshd_cntt.vHdKhach).length; i++) {
            if (dshd_cntt.vHdKhach[Object.keys(dshd_cntt.vHdKhach)[i]] == undefined || dshd_cntt.vHdKhach[Object.keys(dshd_cntt.vHdKhach)[i]] == null) dshd_cntt.vHdKhach[Object.keys(dshd_cntt.vHdKhach)[i]] = ''
          }
          json_hdkh = JSON.stringify(dshd_cntt.vHdKhach)
        }

        var json_hdtb = ''
        if (Object.keys(dshd_cntt.vHdTB).length > 0) {
          for (var i = 0; i < Object.keys(dshd_cntt.vHdTB).length; i++) {
            if (dshd_cntt.vHdTB[Object.keys(dshd_cntt.vHdTB)[i]] == undefined || dshd_cntt.vHdTB[Object.keys(dshd_cntt.vHdTB)[i]] == null) dshd_cntt.vHdTB[Object.keys(dshd_cntt.vHdTB)[i]] = ''
          }
          json_hdtb = JSON.stringify(dshd_cntt.vHdTB)
        }

        var json_hdtb_dv = ''
        if (dshd_cntt.vdsHdTB_Dv.length > 0) {
          for (var i = 0; i < dshd_cntt.vdsHdTB_Dv.length; i++) {
            var row = dshd_cntt.vdsHdTB_Dv[i]
            var keys = Object.keys(row)
            for (var j = 0; j < keys.length; j++) {
              if (row[keys[j]] == undefined || row[keys[j]] == null) dshd_cntt.vdsHdTB_Dv[i][keys[j]] = ''
            }
          }
          json_hdtb_dv = JSON.stringify(dshd_cntt.vdsHdTB_Dv)
        }

        var json_hdtb_cntt = ''
        if (Object.keys(dshd_cntt.vHdCntt).length > 0) {
          for (var i = 0; i < Object.keys(dshd_cntt.vHdCntt).length; i++) {
            if (dshd_cntt.vHdCntt[Object.keys(dshd_cntt.vHdCntt)[i]] == undefined || dshd_cntt.vHdCntt[Object.keys(dshd_cntt.vHdCntt)[i]] == null) dshd_cntt.vHdCntt[Object.keys(dshd_cntt.vHdCntt)[i]] = ''
          }
          json_hdtb_cntt = JSON.stringify(dshd_cntt.vHdCntt)
        }

        var json_dvgt = ''
        if (Object.keys(dshd_cntt.vdsDANGKY_DVGT).length > 0) {
          for (var i = 0; i < Object.keys(dshd_cntt.vdsDANGKY_DVGT).length; i++) {
            if (!dshd_cntt.vdsDANGKY_DVGT[Object.keys(dshd_cntt.vdsDANGKY_DVGT)[i]] || dshd_cntt.vdsDANGKY_DVGT[Object.keys(dshd_cntt.vdsDANGKY_DVGT)[i]] == null) dshd_cntt.vdsDANGKY_DVGT[Object.keys(dshd_cntt.vdsDANGKY_DVGT)[i]] = ''
          }
          json_dvgt = JSON.stringify(dshd_cntt.vdsDANGKY_DVGT)
        }

        var json_tbi = ''
        if (dshd_cntt.vdsMua_TBI.length > 0) {
          for (var i = 0; i < dshd_cntt.vdsMua_TBI.length; i++) {
            var row = dshd_cntt.vdsMua_TBI[i]
            var keys = Object.keys(row)
            for (var j = 0; j < keys.length; j++) {
              if (row[keys[j]] == undefined || row[keys[j]] == null) dshd_cntt.vdsMua_TBI[i][keys[j]] = ''
            }
          }
          json_tbi = JSON.stringify(dshd_cntt.vdsMua_TBI)
        }

        //await this.fn_lay_tt_hdtb_sub() //lấy thông tin hdtb_sub
        console.log('this._hdtbsub 2')
        console.log(this._hdtbsub)

        var json_hdtb_sub = ''
        if (Object.keys(this._hdtbsub).length > 0) {
          for (var i = 0; i < Object.keys(this._hdtbsub).length; i++) {
            if (this._hdtbsub[Object.keys(this._hdtbsub)[i]] == undefined || this._hdtbsub[Object.keys(this._hdtbsub)[i]] == null) this._hdtbsub[Object.keys(this._hdtbsub)[i]] = ''

          }
          this._hdtbsub.HDTB_ID = this.hdtb_id
          // this._hdtbsub.NGAYHETHAN_GT = moment(this._hdtbsub.NGAYHETHAN_GT).format("DD/MM/YYYY HH:mm:ss");
          json_hdtb_sub = JSON.stringify(this._hdtbsub)
        }

        console.log('json_hdtb_sub')
        console.log(json_hdtb_sub)
        // return


        var json_hdtb_cttienhd = ''
        if (dshd_cntt.vdsCT_TIENHD.length > 0) {
          for (var i = 0; i < dshd_cntt.vdsCT_TIENHD.length; i++) {
            var row = dshd_cntt.vdsCT_TIENHD[i]
            var keys = Object.keys(row)
            for (var j = 0; j < keys.length; j++) {
              if (row[keys[j]] == undefined || row[keys[j]] == null) dshd_cntt.vdsCT_TIENHD[i][keys[j]] = 0
            }
          }
          json_hdtb_cttienhd = JSON.stringify(dshd_cntt.vdsCT_TIENHD)
        }

        var kmai = []
        if (this.chkKhuyenMai.Checked || this.chkTraTruoc.Checked) {
          kmai = this.dsKM_HDTB
          kmai = this.UpperCasePropertyList(kmai.concat(this.dsDC_HDTB))
          for (var i = 0; i < kmai.length; i++) {
            var row = kmai[i]
            var keys = Object.keys(row)
            for (var j = 0; j < keys.length; j++) {
              if (row[keys[j]] == undefined || row[keys[j]] == null) kmai[i][keys[j]] = ''
            }
            kmai[i].HDTB_ID = this.hdtb_id
          }
        }
        var json_khuyenmai_hdtb = ''
        if (kmai.length > 0) {
          json_khuyenmai_hdtb = JSON.stringify(kmai)
        }

        // THực hiện cập nhật

        var kieu = -1
        kieu = !this.button.btnNhapMoi.Enabled ? 0 : 1
        if (kieu == 0) {
          this['diachiTB'].DIACHI_ID = await this.GET_KEYS('DIACHI')
        }
        var json_diachitb = JSON.stringify(this['diachiTB'])
        var input1 = {
          diachi_id: this['diachiTB'].DIACHI_ID,
          ds_cttien: json_hdtb_cttienhd,
          ds_diachitb: json_diachitb,
          ds_dvgt: json_dvgt,
          ds_hdcntt: json_hdtb_cntt,
          ds_hdkh: json_hdkh,
          ds_hdtb: json_hdtb,
          ds_hdtb_dv: json_hdtb_dv,
          ds_hdtbsub: json_hdtb_sub,
          ds_khuyenmai: json_khuyenmai_hdtb,
          ds_muatbi: json_tbi,
          hdkh_id: this.hdkh_id,
          hdtb_id: this.hdtb_id,
          huonggiao_id: huonggiao_tn,
          ip_cn: this.tt_nd.ip_cn,
          khachhang_id: this.khachhang_id,
          kieu: kieu,
          may_cn: this.tt_nd.may_cn,
          nguoi_cn: this.tt_nd.nguoi_cn,
          phanvung_id: this.tt_nd.phanvung_id,
          thuebao_id: this.thuebao_id
        }
        // console.log(input1);
        var thongbao = !this.button.btnNhapMoi.Enabled ? 'Thêm mới' : 'cập nhật'
        var thucthi = await this.capnhat_hd_cntt_v9(input1)
        // console.log(thucthi);
        if (thucthi.toString() != '1') {
          // this.ShowError("Lỗi " + thongbao + ": " + thucthi);
          return
        }
        if (Number(this.cboLoaiHinhTB.value) == LoaiHinhTB.VNPT_SLL || Number(this.cboLoaiHinhTB.value) == LoaiHinhTB.VNEDU_SMARTID) {
          try {
            var input2 = {
              ds_hdtb_ct: this.saveList ? JSON.stringify(this.saveList) : '',
              hdtb_id: this.hdtb_id,
              phanvung_id: this.tt_nd.phanvung_id
            }
            // console.log(input2);
            var ketqua_hdtb_ct2 = await this.CAPNHAT_HD_THUEBAO_CT_COMMON(input2)
            if (ketqua_hdtb_ct2 != 'ok') {
              this.ShowError(ketqua_hdtb_ct2)
              //return;
            } else {
              this.ShowSuccess(thongbao + ' thành công')
            }
          } catch (ex) {
            console.log(ex)
            this.ShowError('Lỗi cập nhật CAPNHAT_HD_THUEBAO_CT_COMMON')
          }
        } else {
          let kiemtra_thongtin =  await fn_kiemtra_thongtin_hopdong(this.hdtb_id)
          if(kiemtra_thongtin != 'OK') {
            this.ShowError(kiemtra_thongtin)
          }
          else {
            this.ShowSuccess(thongbao + ' thành công')
          }
        }
        await this.TIMKIEM_MATB()
      } catch (ex) {
        console.log(ex)
        this.ShowError('Lỗi cập nhật')
      }
    },
    capnhat_hd_cntt_v9: async function(dt) {
      try {
    // Điều chỉnh lấy ds_hdtb_dv không được trùng
        console.log("kiểm tra dt.ds_hdtb_dv", dt.ds_hdtb_dv , typeof  dt.ds_hdtb_dv )
        let seen = new Set()
        var list_ds_hdtb_dv= JSON.parse(dt.ds_hdtb_dv)
        console.log("kiểm tra  dt.ds_hdtb_dv 12345", list_ds_hdtb_dv , typeof  list_ds_hdtb_dv )
        let set_ds_hdtb_dv = list_ds_hdtb_dv.filter(item => {
          let key = JSON.stringify(item); // Hoặc bạn có thể sử dụng một thuộc tính cụ thể, ví dụ: item.id
          return seen.has(key) ? false : seen.add(key);
        });
        console.log("kiểm tra set_ds_hdtb_dv",set_ds_hdtb_dv )
        var data_ds_hdtb_dv = JSON.stringify(set_ds_hdtb_dv)
    // hết phần điều chỉnh
        var input = {
          diachi_id: dt.diachi_id,
          ds_cttien: dt.ds_cttien,
          ds_diachitb: dt.ds_diachitb,
          ds_dvgt: dt.ds_dvgt,
          ds_hdcntt: dt.ds_hdcntt,
          ds_hdkh: dt.ds_hdkh,
          ds_hdtb: dt.ds_hdtb,
          ds_hdtb_dv: data_ds_hdtb_dv, //
          ds_hdtbsub: dt.ds_hdtbsub,
          ds_khuyenmai: dt.ds_khuyenmai,
          ds_muatbi: dt.ds_muatbi,
          hdkh_id: dt.hdkh_id,
          hdtb_id: dt.hdtb_id,
          huonggiao_id: dt.huonggiao_id,
          ip_cn: dt.ip_cn,
          khachhang_id: dt.khachhang_id,
          kieu: dt.kieu,
          may_cn: dt.may_cn,
          nguoi_cn: dt.nguoi_cn,
          phanvung_id: dt.phanvung_id,
          thuebao_id: dt.thuebao_id
        }
        let response = await api.capnhat_hd_cntt_v9(this.axios, input)
        if (response.data && response.data.error_code == 'BSS-00000000') {
          if (response.data.data == '1') {
            return '1'
          } else {
            this.ShowError('Lỗi cập nhật HD_CNTT_V9 ' + response.data.data)
            return '-1'
          }
        } else {
          this.ShowError('Lỗi cập nhật HD_CNTT_V9 ' + response.data.message)
          return '-1'
        }
      } catch (ex) {
        console.log(ex)
        this.ShowError('Lỗi cập nhật HD_CNTT_V9')
        return '-1'
      }
    },
    CAPNHAT_HD_THUEBAO_CT_COMMON: async function(dt) {
      try {
        var input = {
          ds_hdtb_ct: dt.ds_hdtb_ct,
          hdtb_id: ds_hdtb_ct.hdtb_id,
          phanvung_id: ds_hdtb_ct.phanvung_id
        }
        let response = await api.capnhat_hd_thuebao_ct_common(this.axios, input)
        if (response.data && response.data.error_code == 'BSS-00000000') {
          return 'ok'
        } else {
          this.ShowError('Lỗi cập nhật HD_THUEBAO_CT_COMMON ' + response.data.message_detail)
          return 'error'
        }
      } catch (ex) {
        console.log(ex)
        this.ShowError('Lỗi cập nhật HD_THUEBAO_CT_COMMON')
        return 'error'
      }
    },
    async TaoDuLieu_HDKH(themmoi) {
      var ketqua = true
      var vHdKhach = {}
      try {
        var hdkh = {}
        // loi_sinh_magd = false;
        if (themmoi) {
          hdkh.MA_GD = this.ma_gd
        } else {
          hdkh.MA_GD = this.txtMaGD.value
        }
        if (themmoi) this.hdkh_id = await this.GET_KEYS('HD_KHACHHANG')
        hdkh.HDKH_ID = this.hdkh_id
        hdkh.KHACHHANG_ID = this.khachhang_id
        hdkh.TEN_KH = this.txtTenKH.value
        if (this.chkCTV && this.txtCTV.toString().trim() != '') hdkh.CTV_ID = this.ctv_id
        if (this.chkNguoiGT && this.txtNguoiGT.toString().trim() != '') hdkh.NHANVIENGT_ID = this.nguoigt_id
        hdkh.MA_DUAN = this.tbMaDuAn
        hdkh.NGAYLAP_HD = this.dtpNgayLapHD.value
        hdkh.DONVI_ID = this.tt_nd.donvi_id.toString()
        hdkh.NHANVIEN_ID = this.tt_nd.nhanvien_id
        hdkh.LOAIHD_ID = LoaiHopDong.GIAHAN
        hdkh.NGAY_YC = this.dtpNgayYC.value
        hdkh.NGUOI_DD = this.nguoi_dd
        vHdKhach = hdkh
        ketqua = true
      } catch (ex) {
        console.log(ex)
        this.ShowError('Lỗi khi tạo dữ liệu HĐ khách hàng')
        vHdKhach = {}
        ketqua = false
      }
      // console.log(vHdKhach);
      return { vHdKhach: vHdKhach, ketqua: ketqua }
    },
    async TaoDuLieu_HDTB(quytrinh_id, kt, themmoi) {
      var vHdTB = {}
      var ketqua = true
      if (!kt) {
        ketqua = false
        return { vHdTB: vHdTB, ketqua: ketqua }
      }
      //todo

      try {
        var hdtb = {}
        if (themmoi) this.hdtb_id = await this.GET_KEYS('HD_THUEBAO')
        hdtb.HDTB_ID = this.hdtb_id
        hdtb.HDKH_ID = this.hdkh_id
        hdtb.MUCUOCTB_ID = Number(this.cboMucCuocTB.value)
        hdtb.THUEBAO_ID = this.thuebao_id
        hdtb.KIEULD_ID = Number(this.cboKieuLD.value)
        hdtb.TTHD_ID = 1
        hdtb.TEN_TB = this.txtTenTB.value
        hdtb.GHICHU = this.txtGhiChu.value
        hdtb.QUYTRINH_ID = quytrinh_id
        vHdTB = hdtb
        ketqua = true
      } catch (ex) {
        console.log(ex)
        this.ShowError('Lỗi khi tạo dữ liệu HĐ thuê bao')
        ketqua = false
      }
      return { vHdTB: vHdTB, ketqua: ketqua }
    },
    TaoDuLieu_HDTB_DV(kt, themmoi) {
      var vdsHdTB_Dv = []
      var ketqua = true
      if (!kt) {
        ketqua = false
        return { vdsHdTB_Dv: vdsHdTB_Dv, ketqua: ketqua }
      }

      try {
        var ds = []
        for (var i = 0; i < this.layoutDV.length; i++) {
          var t = {}
          t.DONVI_ID = Number(this.layoutDV[i]['SELECT_DV']).toString()
          t.HDTB_ID = this.hdtb_id
          t.LOAIDV_ID = Number(this.layoutDV[i]['LOAIDV_DICH_ID'])
          t.KIEUDV_ID = 2
          ds.push(t)
        }
        vdsHdTB_Dv = ds
        ketqua = true
      } catch (ex) {
        console.log(ex)
        this.ShowError('Lỗi khi tạo dữ liệu HDTB_DV')
        ketqua = false
      }
      return { vdsHdTB_Dv: vdsHdTB_Dv, ketqua: ketqua }
    },
    TaoDuLieu_HDTB_CNTT(kt, themmoi) {
      var vHdCntt = {}
      var ketqua = true
      if (!kt) {
        ketqua = false
        return { vHdCntt: vHdCntt, ketqua: ketqua }
      }

      try {
        var ds = this.HDTB_CNTT
        // console.log(this.HDTB_CNTT);
        // console.log(this.dycDVCNTT);
        if (ds.length > 0) {
          var row = ds[0]
          row.HDTB_ID = this.hdtb_id
          row.THUEBAO_ID = this.thuebao_id
          var keys = Object.keys(row)
          for (var i = 0; i < keys.length; i++) {
            var filter = this.dycDVCNTT.filter((e) => e.sFieldName == keys[i])
            console.log('TaoDuLieu_HDTB_CNTT')
            console.log(filter)
            if (filter.length > 0) {
              if (filter[0].iAtt == 7) {
                if (filter[0].sValue) {
                  row[keys[i]] = 1
                } else {
                  row[keys[i]] = 0
                }
              }

              if (filter[0].bIsNotNull && filter[0].sValue != 0 && !filter[0].sValue && filter[0].iAtt != 5) {
                this.$toast.error(filter[0].sCaption + ' Không được để trống')
                ketqua = false
                return { vHdCntt: vHdCntt, ketqua: ketqua }
              } else {
                if (filter[0].iAtt == 10) {
                  if (filter[0].sValue && filter[0].sValue.length > 0) {
                    row[keys[i]] = filter[0].sValue.toString().trim()
                  } else {
                    row[keys[i]] = ''
                  }
                } else {
                  row[keys[i]] = filter[0].sValue
                }
              }
            }
          }
          var filter_sothangnew = this.dycDVCNTT.filter((e) => e.sFieldName == 'SOTHANG_NEW')
          var filter_ngaybdnew = this.dycDVCNTT.filter((e) => e.sFieldName == 'NGAYBD_NEW')
          var filter_ngayktnew = this.dycDVCNTT.filter((e) => e.sFieldName == 'NGAYKT_NEW')

          if (filter_sothangnew.length > 0) {
            row.SO_THANG = Number(filter_sothangnew[0].sValue)
            row.NGAY_BD = filter_ngaybdnew.length > 0 && filter_ngaybdnew[0].sValue ? filter_ngaybdnew[0].sValue : ''
            row.NGAY_KT = filter_ngayktnew.length > 0 && filter_ngayktnew[0].sValue ? filter_ngayktnew[0].sValue : ''
          }

          for (var i = 0; i < keys.length; i++) {
            if (keys[i].includes('THOIGIAN') || keys[i].includes('NGAY')) {
              if (row[keys[i]]) {
                var checkNgayValid = moment(row[keys[i]], 'DD/MM/YYYY HH:mm:ss', true).isValid()
                if (!checkNgayValid) {
                  row[keys[i]] = moment(new Date(row[keys[i]])).format('DD/MM/YYYY HH:mm:ss')
                }
              }
            }
          }
          vHdCntt = row
        }
      } catch (ex) {
        console.log(ex)
        this.ShowError('Lỗi khi tạo dữ liệu CNTT')
        ketqua = false
      }
      return { vHdCntt: vHdCntt, ketqua: ketqua }
    },
    TaoDuLieu_DVGT(kt) {
      var vdsDANGKY_DVGT = []
      var ketqua = true
      if (!kt) {
        ketqua = false
        return { vdsDANGKY_DVGT: vdsDANGKY_DVGT, ketqua: ketqua }
      }
      try {
        var ds_dk_dvgt = this.m_dsThueBao_DichVu
        if (ds_dk_dvgt.length > 0) {
          vdsDANGKY_DVGT = ds_dk_dvgt.filter((x) => x.SUA == 1 || x.HUY == 1 || x.DANGKY == 1)
          for (var i = 0; i < vdsDANGKY_DVGT.length; i++) {
            if (this._sl_cha != 0 && vdsDANGKY_DVGT[i].BLOCK_TIEP.toString().trim() != '' && vdsDANGKY_DVGT[i].BLOCK_TIEP.toString().trim() != '0') vdsDANGKY_DVGT[i].SL_CHA = this._sl_cha
            else vdsDANGKY_DVGT[i].SL_CHA = 0
            if (vdsDANGKY_DVGT[i].SUA == 1) vdsDANGKY_DVGT[i].KIEU_YC = KieuCaiDatDVGT.KIEU_CD_SUA_DVGT
            if (vdsDANGKY_DVGT[i].DANGKY == 1) vdsDANGKY_DVGT[i].KIEU_YC = KieuCaiDatDVGT.KIEU_CD_THEM_MOI_DVGT
            if (vdsDANGKY_DVGT[i].HUY == 1) vdsDANGKY_DVGT[i].KIEU_YC = KieuCaiDatDVGT.KIEU_CD_HUY_DVGT
            vdsDANGKY_DVGT[i].TTTH = 1
          }
        }
      } catch (ex) {
        console.log(ex)
        this.ShowError('Lỗi khi tạo dữ liệu DVGT')
        ketqua = false
      }
      return { vdsDANGKY_DVGT: vdsDANGKY_DVGT, ketqua: ketqua }
    },
    TaoDuLieu_MuaTBI(kt) {
      var vdsMua_TBI = []
      var ketqua = true
      if (!kt) {
        ketqua = false
        return { vdsMua_TBI: vdsMua_TBI, ketqua: ketqua }
      }
      try {
        var ds = []
        if (this.dsCTM_TBI && this.dsCTM_TBI.length > 0) {
          ds = this.UpperCasePropertyList(this.dsCTM_TBI)
          for (var i = 0; i < ds.length; i++) {
            ds[i].HDTB_ID = this.hdtb_id
          }
        }
        vdsMua_TBI = ds
      } catch (ex) {
        console.log(ex)
        this.ShowError('Lỗi khi tạo dữ liệu HDTB_TBI')
        ketqua = false;
      }
      return { vdsMua_TBI: vdsMua_TBI, ketqua: ketqua }
    },
    async TaoDuLieu_CT_TIENHD(kt, themmoi) {
      var vdsCT_TIENHD = []
      var ketqua = true

      if (!kt) {
        ketqua = false
        return { vdsCT_TIENHD: vdsCT_TIENHD, ketqua: ketqua }
      }
      if (!this.themmoi_tienkm) {
        ketqua = true
        return { vdsCT_TIENHD: vdsCT_TIENHD, ketqua: ketqua }
      }
      try {
        if (this.ds_tien_khoanmuc.length == 0) return { vdsCT_TIENHD: vdsCT_TIENHD, ketqua: false }
        var vds = []
        // for (var i = 0; i < this.ds_tien_khoanmuc.length; i++) {
        //   var dr = this.ds_tien_khoanmuc[i]
        //   if (dr['TIEN'] && Number(dr['TIEN']) > 0) {
        //     var cttien = {}
        //     cttien.HDTB_ID = this.hdtb_id
        //     cttien.CT_TIENHD_ID = await this.GET_KEYS('CT_TIENHD')
        //     cttien.ID = Number(dr['KHOANMUCTT_ID'])
        //     cttien.KHOANMUCTT_ID = Number(dr['KHOANMUCTT_ID'])
        //     cttien.TIEN = dr['TIEN'] ? Number(dr['TIEN']) : 0
        //     cttien.VAT = dr['VAT'] ? Number(dr['VAT']) : 0
        //     cttien.LOAI_ID = 1
        //     vds.push(cttien)
        //   }
        // }
        console.log('TaoDuLieu_CT_TIENHD ds_tien_khoanmuc,dsDC_HDTB',this.ds_tien_khoanmuc,this.dsDC_HDTB)
        for (var km of this.ds_tien_khoanmuc) {
          if (km['KHOANMUCTT_ID'] == KHOANMUC_TT.KMTT_DICHVU) {
            for (let dv of this.m_dsThueBao_DichVu) {
              // TODO m_dsThueBao_DichVu lay o danh sach dich vu gia tang (nut btnEditDVGT)
              if (dv['DANGKY'] == 1) {
                let rowCTTHD = {}
                rowCTTHD.HDTB_ID = this.hdtb_id
                rowCTTHD.CT_TIENHD_ID = km['CT_TIENHD_ID'] && km['CT_TIENHD_ID'] > 0 ? km['CT_TIENHD_ID'] : await this.GET_KEYS('CT_TIENHD')
                rowCTTHD.KHOANMUCTT_ID = km['KHOANMUCTT_ID']
                let tien = 0
                let vat = 0

                tien = dv['CUOC_LD']
                vat = dv['VAT_LD']

                rowCTTHD.TIEN = tien ? tien * 1 : 0
                rowCTTHD.VAT = vat ? vat * 1 : 0
                rowCTTHD.ID = dv['DICHVUGT_ID']
                rowCTTHD.LOAI_ID = 2
                rowCTTHD.TYLE_VAT = dv['TYLE_VAT']
                rowCTTHD.TYLE_VAT_ID = dv['TYLE_VAT_ID']
                vds.push(rowCTTHD)
              }
            }
          } else if (km['KHOANMUCTT_ID'] == KHOANMUC_TT.KMTT_KM_DVGT) {
            for (let dv of this.m_dsThueBao_DichVu) {
              // TODO m_dsThueBao_DichVu lay o danh sach dich vu gia tang (nut btnEditDVGT)
              if (dv['DANGKY'] == 1) {
                let tien = 0
                let vat = 0

                tien = dv['CUOC_KMLD']
                vat = dv['VAT_KMLD']
                if (tien != 0 && vat != 0) {
                  let rowCTTHD = {}
                  rowCTTHD.HDTB_ID = this.hdtb_id
                  rowCTTHD.CT_TIENHD_ID = km['CT_TIENHD_ID'] && km['CT_TIENHD_ID'] > 0 ? km['CT_TIENHD_ID'] : await this.GET_KEYS('CT_TIENHD')
                  rowCTTHD.KHOANMUCTT_ID = km['KHOANMUCTT_ID']

                  rowCTTHD.TIEN = tien && Math.abs(tien) ? Math.abs(tien) * -1 : 0
                  rowCTTHD.VAT = vat && Math.abs(vat) ? Math.abs(vat) * -1 : 0
                  rowCTTHD.ID = dv['DICHVUGT_ID']
                  rowCTTHD.LOAI_ID = 2
                  rowCTTHD.TYLE_VAT = dv['TYLE_VAT']
                  rowCTTHD.TYLE_VAT_ID = dv['TYLE_VAT_ID']
                  vds.push(rowCTTHD)
                }
              }
            }
          } else {
            if (km['KHOANMUCTT_ID'] == KHOANMUC_TT.KMTT_KM_LAPDAT || km['KHOANMUCTT_ID'] == KHOANMUC_TT.KMTT_KM_THIETBI || km['KHOANMUCTT_ID'] == KHOANMUC_TT.KMTT_KM_DATCOC) {
              let rowCTTHD = {}
              rowCTTHD.HDTB_ID = this.hdtb_id
              rowCTTHD.CT_TIENHD_ID = km['CT_TIENHD_ID'] && km['CT_TIENHD_ID'] > 0 ? km['CT_TIENHD_ID'] : await this.GET_KEYS('CT_TIENHD')
              rowCTTHD.KHOANMUCTT_ID = km['KHOANMUCTT_ID']

              if (km['TIEN'] > 0) {
                rowCTTHD.TIEN = km['TIEN'] ? km['TIEN'] * -1 : 0
                rowCTTHD.VAT = km['VAT'] ? km['VAT'] * -1 : 0
              } else {
                rowCTTHD.TIEN = km['TIEN'] ? km['TIEN'] * 1 : 0
                rowCTTHD.VAT = km['VAT'] ? km['VAT'] * 1 : 0
              }
              rowCTTHD.ID = km['KHOANMUCTT_ID']
              rowCTTHD.LOAI_ID = 1

              rowCTTHD.TYLE_VAT = 10
              rowCTTHD.TYLE_VAT_ID = 1
              vds.push(rowCTTHD)
            } else if (km['KHOANMUCTT_ID'] == KHOANMUC_TT.KMTT_DATCOC) {
              // #region cuonglt.hpg 24/01/2022 - Đặt cọc
              let codl_ctkm = false
              var dsDC_HDTB = this.dsDC_HDTB
              if (this.dsDC_HDTB && this.dsDC_HDTB.length > 0) {
                dsDC_HDTB = this.UpperCasePropertyList(dsDC_HDTB)
                for (let dr of dsDC_HDTB) {
                  //1 la KMTT DATCOC
                  let rowCTTHD = {}
                  rowCTTHD.HDTB_ID = this.hdtb_id
                  rowCTTHD.CT_TIENHD_ID = km['CT_TIENHD_ID'] && km['CT_TIENHD_ID'] > 0 ? km['CT_TIENHD_ID'] : await this.GET_KEYS('CT_TIENHD')
                  rowCTTHD.KHOANMUCTT_ID = km['KHOANMUCTT_ID']

                  rowCTTHD.TIEN = Math.round(Number(dr['DATCOC_CSD']) * 100 / (100 + dr['TYLE_VAT']))
                  rowCTTHD.VAT = Math.round(Number(dr['DATCOC_CSD']) * dr['TYLE_VAT'] / (100 + dr['TYLE_VAT']))
                  rowCTTHD.ID = dr['CHITIETKM_ID'] * 1
                  rowCTTHD.LOAI_ID = 4
                  rowCTTHD.TYLE_VAT = dr['TYLE_VAT'] * 1
                  rowCTTHD.TYLE_VAT_ID = dr['TYLE_VAT_ID'] ? dr['TYLE_VAT_ID'] * 1 : 0

                  // end hoàng

                  vds.push(rowCTTHD)
                  codl_ctkm = true
                }
              }
              if (!codl_ctkm) {
                if (km['TIEN'] != 0 || km['VAT'] != 0) {
                  let rowCTTHD = {}
                  rowCTTHD.HDTB_ID = this.hdtb_id
                  rowCTTHD.CT_TIENHD_ID = km['CT_TIENHD_ID'] && km['CT_TIENHD_ID'] > 0 ? km['CT_TIENHD_ID'] : await this.GET_KEYS('CT_TIENHD')
                  rowCTTHD.KHOANMUCTT_ID = km['KHOANMUCTT_ID']

                  rowCTTHD.TIEN = km['TIEN'] * 1
                  rowCTTHD.VAT = km['VAT'] * 1
                  rowCTTHD.ID = km['KHOANMUCTT_ID'] * 1
                  rowCTTHD.LOAI_ID = 1

                  // hoangpkn : 20/08/2019. Tạm fix
                  // if (km['khoanmuctt_id'] == KHOANMUC_TT.KMTT_DATMOI) {
                  //   rowCTTHD.TYLE_VAT = this.vtyle_vat_dnhm
                  //   rowCTTHD.TYLE_VAT_ID = this.vtyle_vat_id_dnhm
                  // } else if (km['khoanmuctt_id'] == KHOANMUC_TT.KMTT_CAIDAT) {
                  //   rowCTTHD.TYLE_VAT = this.vtyle_vat_cd
                  //   rowCTTHD.TYLE_VAT_ID = this.vtyle_vat_id_cd
                  // } else {
                    rowCTTHD.TYLE_VAT = 10
                    rowCTTHD.TYLE_VAT_ID = 1
                  // }
                  // end hoàng

                  vds.push(rowCTTHD)
                }
              }
              // #endregion
            }
            else {
              ////Lamnt: Bỏ zem đoạn này do khoản mục = 0 không insert vào ct_tienhd -> không thanh toán được.
              ////11/06/2019
              ////Cuonglt mở lại theo yêu cầu anh Hiếu BABA
              if (km['TIEN'] != 0 || km['VAT'] != 0) {
                let rowCTTHD = {}
                rowCTTHD.HDTB_ID = this.hdtb_id
                rowCTTHD.CT_TIENHD_ID = km['CT_TIENHD_ID'] && km['CT_TIENHD_ID'] > 0 ? km['CT_TIENHD_ID'] : await this.GET_KEYS('CT_TIENHD')
                rowCTTHD.KHOANMUCTT_ID = km['KHOANMUCTT_ID']

                rowCTTHD.TIEN = km['TIEN'] ? km['TIEN'] * 1 : 0
                rowCTTHD.VAT = km['VAT'] ? km['VAT'] * 1 : 0
                rowCTTHD.ID = km['KHOANMUCTT_ID']
                rowCTTHD.LOAI_ID = 1

                // hoangpkn : 20/08/2019. Tạm fix
                // if (km['khoanmuctt_id'] == KHOANMUC_TT.KMTT_DATMOI) {
                //   rowCTTHD.TYLE_VAT = this.vtyle_vat_dnhm
                //   rowCTTHD.TYLE_VAT_ID = this.vtyle_vat_id_dnhm
                // } else if (km['khoanmuctt_id'] == KHOANMUC_TT.KMTT_CAIDAT) {
                //   rowCTTHD.TYLE_VAT = this.vtyle_vat_cd
                //   rowCTTHD.TYLE_VAT_ID = this.vtyle_vat_id_cd
                // } else {
                  rowCTTHD.TYLE_VAT = 10
                  rowCTTHD.TYLE_VAT_ID = 1
                // }
                // end hoàng
                vds.push(rowCTTHD)
              }
            }
          }
        }
        if (vds && vds.length > 0) {
          for (let item of vds) {
            var tien = item['TIEN'] ? Number(item['TIEN']) : (item['tien'] ? Number(item['tien']) : 0)
            var vat = item['VAT'] ? Number(item['VAT']) : (item['vat'] ? Number(item['vat']) : 0)
            var kmtt_id = item['KHOANMUCTT_ID'] ? item['KHOANMUCTT_ID'] : (item['khoanmuctt_id'] ? item['khoanmuctt_id'] : '')
            if (Math.abs(tien) > 0 && Math.abs(tien) < Math.abs(vat)) {
              this.$toast.error(`Khoản mục thanh toán ID ${kmtt_id} có tiền bé hơn VAT, bạn hãy kiểm tra lại!`)
              return { vdsCT_TIENHD: [], ketqua: false }
            }
          }
        }
        vdsCT_TIENHD = vds
      } catch (ex) {
        console.log(ex)
        this.ShowError('Lỗi khi tạo dữ liệu CT_TIENHD')
        ketqua = false
      }
      return { vdsCT_TIENHD: vdsCT_TIENHD, ketqua: ketqua }
    },
    KIEMTRA_TAODL(ds, themmoi) {
      try {
        if (!themmoi) {
          if (ds.vHdTB.HDTB_ID == 0) {
            this.ShowError('Không có dữ liệu Hợp đồng hãy kiểm tra lại')
            return false
          }

          if (ds.vHdKhach.HDKH_ID == 0) {
            this.ShowError('Không có dữ liệu khách hàng hãy kiểm tra lại')
            return false
          }

          if (ds.vHdCntt.HDTB_ID == 0) {
            this.ShowError('Không có dữ liệu hợp đồng con hãy kiểm tra lại')
            return false
          }
        }

        if (ds.vdsHdTB_Dv.length == 0) {
          this.ShowError('Không có dữ liệu đơn vị hợp đồng hãy kiểm tra lại')
          return false
        }

        if (this['diachiLD'].DIACHI_ID == 0 && Number(this.cboKieuLD.value) == 13178) {
          this.ShowError('Không có địa chỉ lắp đặt')
          return false
        }

        return true
      } catch (ex) {
        console.log(ex)
        this.ShowError('Lỗi kiểm tra tạo dữ liệu')
        return false
      }
    },
    kiemtra_tt_capnhat: async function(nhapmoi) {
      try {
        var filter_lisence = this.dycDVCNTT.filter((e) => e.sFieldName == 'SL_MAILING')
        var filter_tocdo = this.dycDVCNTT.filter((e) => e.sFieldName == 'TOCDO_ID')
        var lisence_new = filter_lisence.length > 0 ? Number(filter_lisence[0].sValue) : 0
        var tocdo_id_new = filter_tocdo.length > 0 ? Number(filter_tocdo[0].sValue) : 0

        var para = {
          MA_TB: this.txtMaTB.value,
          LOAITB_ID: Number(this.cboLoaiHinhTB.value),
          LISENCE_NEW: lisence_new,
          TOCDO_ID_NEW: tocdo_id_new,
          KIEULD_ID: Number(this.cboKieuLD.value),
          THUEBAO_ID: this.thuebao_id,
          GUI_API_HDDT: 1,
          MUCCUOCTB_ID: Number(this.cboMucCuocTB.value),
          HDTB_ID: this.hdtb_id,
          NGUOIGT_ID: this.nguoigt_id,
          CTV_ID: this.ctv_id,
          NHAPMOI: nhapmoi ? 1 : 2, //1: nhap moi, 2: cap nhat
          TOCDO_ID: this.tocdo_id,
          MUCCUOC_ID: this.muccuoc_id,
          DONVI_ID: this.tt_nd.donvi_id,
          KHACHHHANG_ID: this.khachhang_id,
          DICHVUVT_ID: Number(this.cboDichVuVT.value)
        }
        // console.log(para);
        var input = {
          ds_para: JSON.stringify(para),
          phanvung_id: this.tt_nd.phanvung_id
        }
        let response = await api.kt_va_lay_tt_capnhat(this.axios, input)
        if (response.data && response.data.error_code == 'BSS-00000000') {
          var data = JSON.parse(response.data.data)
          if (data.ERROR_CODE == '1') {
            // console.log(data.RESULT);
            // console.log(JSON.parse(data.RESULT.HDTB_CNTT));
            this.huonggiao_tn = 0
            this.quytrinh_id = 0
            this.nguoi_dd = data.RESULT.HD_KHACHHANG && data.RESULT.HD_KHACHHANG.NGUOI_DD ? data.RESULT.HD_KHACHHANG.NGUOI_DD : ''

            this.HDTB_CNTT = data.RESULT.HDTB_CNTT && data.RESULT.HDTB_CNTT.length > 0 ? JSON.parse(data.RESULT.HDTB_CNTT) : []
            if (nhapmoi) {
              this.huonggiao_tn = data.RESULT.HUONGGIAO_ID ? data.RESULT.HUONGGIAO_ID : 0
              this.quytrinh_id = data.RESULT.QUYTRINH_ID ? data.RESULT.QUYTRINH_ID : 0
              this['diachiTB'].DIACHI_ID = data.RESULT.DIACHI_ID ? data.RESULT.DIACHI_ID : 0
              this.hdtb_id = data.RESULT.HD_THUEBAO && data.RESULT.HD_THUEBAO.HDTB_ID ? data.RESULT.HD_THUEBAO.HDTB_ID : 0
              this.hdkh_id = data.RESULT.HD_KHACHHANG && data.RESULT.HD_KHACHHANG.HDKH_ID ? data.RESULT.HD_KHACHHANG.HDKH_ID : 0
              this.ma_gd = data.RESULT.HD_KHACHHANG && data.RESULT.HD_KHACHHANG.MA_GD ? data.RESULT.HD_KHACHHANG.MA_GD : ''
              this.check_loaitb_id = data.RESULT.CHECK_LOAIHINH_TB ? data.RESULT.CHECK_LOAIHINH_TB : 'NOK'
            }



            if (this.check_loaitb_id != 'OK') {
              return 'OK'
            }
            else {
              var filter_truong_id = this.dycDVCNTT.filter((e) => e.sFieldName == 'MA_PHU')
              const truong_id_new = filter_truong_id.length > 0 ? filter_truong_id[0].sValue.toString() : '0'
              if (truong_id_new == '0') {
                this.$toast.error('Hãy kiểm tra lại ID trường')
                return 'Hãy kiểm tra lại ID trường'
              }
              //call api
              const data_tracuu = {
                phanloai: data.RESULT.MA_EU,
                truongid: truong_id_new
              }
              let res_edu = await api.Get_Data_TraCuu(this.axios, data_tracuu)
              //console.log(res_edu)
              // if (res_edu.data.errorCode != 0 && res_edu.data.faultCode != 'SUCCESS') {
              //   this.$toast.error('Đã có lỗi : ' + res_edu.data.faultCode.toString())
              //   return 'Đã có lỗi xảy ra : ' + res_edu.data.faultCode.toString()
              // }
              const data_faultString = JSON.parse(res_edu.data.faultString)
              // if (!data_faultString.success) {
              //   this.$toast.error('Có lỗi: ' + data_faultString.msg)
              //   return 'Có lỗi :' + data_faultString.msg
              // }
              const data_check = {
                kieuld_id: Number(this.cboKieuLD.value),
                thuebao_id: this.thuebao_id,
                loaitb_id: Number(this.cboLoaiHinhTB.value),
                tocdo_id: tocdo_id_new,
                truong_id: truong_id_new,
                start_date: data_faultString.data.start_date,
                end_date: data_faultString.data.end_date
              }
              console.log(data_check)
              const res_check = await api.Check_Data_Edu(this.axios, data_check)
              console.log(data_check)
              if (res_check.data.error == '200' && res_check.data.error_code == 'BSS-00000000' && res_check.data.data != 'OK') {
                this.$toast.error(res_check.data.data)
                this.isShow_check_data_edu = true
                return res_check.data.data
              }
              return 'OK'
            }
          } else {
            this.ShowError(data.MESSAGE == 'OK' ? 'Lỗi kiểm tra thông tin cập nhật' : data.MESSAGE)
            return data.MESSAGE == 'OK' ? 'Lỗi kiểm tra thông tin cập nhật' : data.MESSAGE
          }
        } else {
          this.ShowError('Lỗi kiểm tra thông tin cập nhật ' + response.data.message_detail)
          return 'error'
        }
      } catch (ex) {
        console.log(ex)
        this.ShowError('Lỗi kiểm tra thông tin cập nhật')
        return 'error'
      }
    },
    KIEMTRA_DULIEU: function() {
      try {
        if (this.ds_tien_khoanmuc && this.ds_tien_khoanmuc.length > 0) {
          for (let item of this.ds_tien_khoanmuc) {
            var tien = item['TIEN'] ? Number(item['TIEN']) : (item['tien'] ? Number(item['tien']) : 0)
            var vat = item['VAT'] ? Number(item['VAT']) : (item['vat'] ? Number(item['vat']) : 0)
            var kmtt_id = item['KHOANMUCTT_ID'] ? item['KHOANMUCTT_ID'] : (item['khoanmuctt_id'] ? item['khoanmuctt_id'] : '')
            if (Math.abs(tien) > 0 && Math.abs(tien) < Math.abs(vat)) {
              this.$toast.error(`Khoản mục thanh toán ID ${kmtt_id} có tiền bé hơn VAT, bạn hãy kiểm tra lại!`)
              return false
            }
          }
        }

        if (this.dsDC_HDTB && this.dsDC_HDTB.length > 0) {
          let dsDC_HDTB = this.dsDC_HDTB
          dsDC_HDTB = this.UpperCasePropertyList(dsDC_HDTB)
          for (let dr of dsDC_HDTB) {
            let tien = Math.round(Number(dr['DATCOC_CSD']) * 100 / (100 + dr['TYLE_VAT']))
            let vat = Math.round(Number(dr['DATCOC_CSD']) * dr['TYLE_VAT'] / (100 + dr['TYLE_VAT']))
            if (Math.abs(tien) > 0 && Math.abs(tien) < Math.abs(vat)) {
              this.$toast.error(`Trả trước đang có tiền bé hơn VAT, bạn hãy kiểm tra lại!`)
              return false
            }
          }
        }

        if (this.vdoituong_id != 190 && Number(this.cboKieuLD.value) == 13189) {
          this.ShowError('Kiểu yêu cầu chỉ dành cho thuê bao đang dùng thử')
          return false
        }

        if(Number(this.cboKieuLD.value) == 19585 ||Number(this.cboKieuLD.value) == 19587) {
          const donvikg_filer = this.dycDVCNTT.filter((e) => e.sFieldName == 'DONVIKG_ID')
          const donvikg_id = donvikg_filer.length > 0 ? Number(donvikg_filer[0].sValue) : 0
          console.log('✅ donvikg_id', donvikg_id)
          if(donvikg_id != 14) {
            this.ShowError('Kiểu yêu cầu chỉ dành cho thuê bao Voice Brandname đối tượng 2!');
            return false
          }
        }

        if (Number(this.cboDichVuVT.value) == -1 || Number(this.cboLoaiHinhTB.value) == -1 || Number(this.cboKieuLD.value) == -1) {
          this.ShowError('Dữ liệu danh mục dịch vụ, loại hình, kiểu lắp đặt đang không có không thể thao tác')
          return false
        }
        if (Number(this.cboKieuLD.value) == 13248 || Number(this.cboKieuLD.value) == 13177) {
          var filter_sothangnew = this.dycDVCNTT.filter((e) => e.sFieldName == 'SOTHANG_NEW')
          var sothang_new = filter_sothangnew.length > 0 ? Number(filter_sothangnew[0].sValue) : 0
          if (sothang_new == 0 && (Number(this.cboKieuLD.value) == 13248 || Number(this.cboKieuLD.value) == 13177)) {
            this.ShowError('Bạn chưa nhập số tháng gia hạn!')
            return false
          }
        }

        if (this.layoutDV.length == 0) {
          this.ShowError('Không có dữ liệu đơn vị bạn hãy báo Admin')
          return false
        } else {
          for (var i = 0; i < this.layoutDV.length; i++) {
            if (!this.layoutDV[i]['DS_DONVI'] || this.layoutDV[i]['DS_DONVI'].length == 0) {
              this.ShowError('Không có dữ liệu đơn vị ' + this.layoutDV[i].TEN_LOAIDV + ' bạn hãy báo Admin')
              return false
            }
            var check_select_dv = this.layoutDV[i]['DS_DONVI'].find(e => Number(e.DONVI_ID) == Number(this.layoutDV[i].SELECT_DV))
            if ((this.layoutDV[i].SELECT_DV != 0 && !this.layoutDV[i].SELECT_DV) || this.layoutDV[i].SELECT_DV == null || this.layoutDV[i].SELECT_DV == -1 || !check_select_dv) {
              this.ShowError('Bạn chưa chọn đơn vị ' + this.layoutDV[i].TEN_LOAIDV + ' hãy kiểm tra lại')
              return false
            }
          }
        }

        if (!this.button.btnNhapMoi.Enabled) {
          if (this.khachhang_id == 0 || this.thuebao_id == 0) {
            this.ShowError('Chưa có dữ liệu khách hàng không thể ghi lại')
            return false
          }
        }
        //longdx:km không có chi tiết không cho cập nhật
        if (this.chkKhuyenMai.Checked && this.dsKM_HDTB.length == 0) {
          this.ShowError('Bạn chưa chọn chi tiết khuyến mại!')
          return false
        }
        if (this.chkTraTruoc.Checked && this.dsDC_HDTB.length == 0) {
          this.ShowError('Bạn chưa chọn chi tiết trả trước!')
          return false
        }

        if (this.batbuoc_ctv_giahan_cntt == 1) {
          if (!this.chkCTV || !this.txtCTV || this.txtCTV == '' || this.ctv_id == 0) {
            this.ShowError('Bạn chưa chọn nhân viên phát triển!')
            this.$refs.txtCTV.focus()
            return false
          }
        }
        if (this.batbuoc_nguoigt_giahan_cntt == 1) {
          if (!this.chkNguoiGT || !this.txtNguoiGT || this.txtNguoiGT == '' || this.nguoigt_id == 0) {
            this.ShowError('Bạn chưa chọn người giới thiệu hợp đồng/phụ lục')
            this.$refs.txtNguoiGT.focus()
            return false
          }
        }

        if (!this.cboMucCuocTB.value || this.cboMucCuocTB.value.toString().trim() == '') {
          this.ShowError('Bạn chưa chọn mức cước!')
          return false
        }

        if (!this.cboMucCuocTB.value || this.cboMucCuocTB.value.toString().trim() == '') {
          this.ShowError('Bạn chưa chọn mức cước!')
          return false
        }

        if (this.cboMucCuocTB.value) {
          if (!this.DTMUCCUOCTB || this.DTMUCCUOCTB.length == 0) {
            this.ShowError('Không có danh sách mức cước!')
            return false
          } else {
            var check_valid = this.DTMUCCUOCTB.findIndex(e => Number(e.MUCUOCTB_ID) == Number(this.cboMucCuocTB.value))
            if (check_valid == -1) {
              this.ShowError('Giá trị mức cước không tồn tại trong danh sách mức cước, hãy chọn mức cước khác!')
              return false
            }
          }
        }

        return true
      } catch (ex) {
        console.log('KIEMTRA_DULIEU')
        console.log(ex)
      }
    },
    ChuyenChinhThuc: async function(vdichvuvt_id, ma_tb) {
      this.cboKieuLD.value = 13189
      this.cboDichVuVT.value = vdichvuvt_id
      this.txtMaTB.value = ma_tb
      await this.TIMKIEM_MATB()
    },
    async TIMKIEM_MATB() {
      try {
        if (this.txtMaTB.value == '') return
        var filter_dichvu = listDichVuCNTT.filter((e) => e == Number(this.cboDichVuVT.value))
        if (!filter_dichvu || filter_dichvu.length <= 0) {
          this.ShowError('Dịch vụ bạn chọn không được áp dụng tại chức năng này')
          return
        }
        var para = {
          MA_TB: this.txtMaTB.value,
          LOAIHD_ID: LoaiHopDong.GIAHAN,
          DONVI_ID: this.tt_nd.donvi_id, //0, //this.tt_nd.donvi_id,
          TTHD_ID: TrangThaiHD.MOI,
          NHANVIEN_ID: this.tt_nd.nhanvien_id, //0, //this.tt_nd.nhanvien_id,
          DONVI_DL_ID: 0,
          DICHVUVT_ID: this.cboDichVuVT.value,
          LOAITB_ID: this.cboLoaiHinhTB.value
        }
        var input = {
          ds_para: JSON.stringify(para),
          phanvung_id: this.tt_nd.phanvung_id
        }
        let response = await api.timkiem_matb(this.axios, input)
        if (response.data && response.data.error_code == 'BSS-00000000') {
          var data = JSON.parse(response.data.data)
          if (data.ERROR_CODE == '1') {
            var dt = data.RESULT
            // console.log(dt);
            if (dt.DICHVUVT_ID && this.cboDichVuVT.value != dt.DICHVUVT_ID) {
              this.cboDichVuVT.value = dt.DICHVUVT_ID
              await this.TIMKIEM_MATB()
              return
            }
            if (dt.DT.length > 0) {
              await this.HienThiTTHopDongKH(dt.DT)
            } else {
              await this.loaihinhtb_loaidv(Number(this.cboDichVuVT.value))
              await this.HienThiTTDanhBaKH(this.txtMaTB.value, Number(this.cboDichVuVT.value))
            }
          } else {
            this.ShowError('Lỗi tìm theo mã thuê bao ' + data.MESSAGE)
          }
        } else {
          // console.log(response.data.message);
          this.ShowError('Lỗi tìm theo mã thuê bao ' + response.data.message_detail)
        }
      } catch (ex) {
        console.log(ex)
        this.ShowError('Lỗi tìm kiếm theo mã TB')
      }
    },
    NAP_DS_COMBO: async function() {
      try {
        let input = {
          phanvung_id: this.tt_nd.phanvung_id
        }
        let response = await api.NAP_DS_COMBO(this.axios, input)
        if (response && response.data && response.data.error_code == 'BSS-00000000' && response.data.data) {
          var data = JSON.parse(response.data.data)
          if (data && data.ERROR_CODE.toString() == '1') {
            console.log('NAP_DS_COMBO',data.RESULT);
            this.ds_dichvuvt = data.RESULT.DICHVUVT_ID
            this.ds_kieuld = data.RESULT.DL_KIEULD
            this.DANHMUC_TIENCTKM = data.RESULT.DANHMUC_TIENCTKM
            this.m_dsTienCTKM = data.RESULT.DANHMUC_TIENCTKM
            var thamso_md = data.RESULT.LAY_DS_THAMSO_MD
            this.thamsomacdinh = data.RESULT.LAY_DS_THAMSO_MD
            if (thamso_md.length > 0) {
              this.ts_sinhma_gd_theo_donvi = thamso_md.filter((x) => x.MA_TS == 'SINH_MA_GD_THEO_DV').length > 0

              var str_vtemp = 'KIEMTRA_NO_TRATRUOC'
              var det = thamso_md.filter((x) => x.MA_TS == str_vtemp)
              if (det.length > 0) {
                if (det[0]['TEN_TS'].trim() == '1') this.kiemtra_no_tratruoc = 1
              }

              var str_vtemp1 = 'BATBUOC_CTV_GIAHAN_CNTT'
              var det1 = thamso_md.filter((x) => x.MA_TS == str_vtemp1)
              if (det1.length > 0) {
                if (det1[0]['TEN_TS'].trim() == '1') this.batbuoc_ctv_giahan_cntt = 1
              }

              var str_vtemp2 = 'BATBUOC_NGUOIGT_GIAHAN_CNTT'
              var det2 = thamso_md.filter((x) => x.MA_TS == str_vtemp2)
              if (det2.length > 0) {
                if (det2[0]['TEN_TS'].trim() == '1') this.batbuoc_nguoigt_giahan_cntt = 1
              }
            }
          } else {
            this.ShowError('Lỗi nạp combo ' + data.MESSAGE)
          }
        } else {
          this.ShowError('Lỗi nạp combo ' + response.data.message_detail)
        }
      } catch (ex) {
        console.log(ex)
        this.ShowError('Có lỗi trong quá trình lấy thông tin Dịch vụ!')
      }
    },
    // XỬ lý frm
    KHOAMO_FORM(mo) {
      this.txtTenKH.ReadOnly = !mo
      this.txtTenTB.ReadOnly = !mo
      this.button.btnThayDoiDiaChiTB.Enabled = mo
      this.button.btnThayDoiDiaChiLD.Enabled = mo
      this.dtpNgayLapHD.Enabled = mo
      this.dtpNgayYC.Enabled = mo
      this.txtTenKH.Enabled = mo
      this.txtMaKH.Enabled = mo
      this.txtMaKH.ReadOnly = !mo
      this.txtDiaChiKH.Enabled = mo
      this.txtDiaChiKH.ReadOnly = !mo
      this.txtMaHD.Enabled = mo
      this.txtMaHD.ReadOnly = !mo
      // dycDVCNTT.Enabled = mo;
      this.txtGhiChu.Enabled = mo
      this.txtGhiChu.ReadOnly = !mo
      this.txtTenTB.Enabled = mo
      this.txtDiaChiLD.Enabled = mo
      this.txtDiaChiTB.Enabled = mo

      this.button.tsbtnHoanThienHS.Visible = false
    },
    XOA_FORM() {
      console.log('XOA_FORM')
      this.saveList = []
      this._hdtbsub = {}
      this.txtTenTB.ReadOnly = true
      this.button.btnThayDoiDiaChiLD.Enabled = false
      this.button.btnThayDoiDiaChiTB.Enabled = false
      this.button.tsbtnGiaoPhieu.Visible = false
      // this.diachiLapDat = new DiaChi();
      // this.diaChiThueBao = new DiaChi();
      this.diachiLD = {
        DIACHI: '',
        DIACHI_ID: 0,
        TINH_ID: 0,
        QUAN_ID: 0,
        PHUONG_ID: 0,
        PHO_ID: 0,
        AP_ID: 0,
        KHU_ID: 0,
        DACDIEM_ID: 0,
        SO_NHA: '',
        Lng: 0,
        Lat: 0,
        BLOCK: '',
        TANG: 0,
        PHONG: 0,
        MOTA: ''
      }
      this.diachiTB = {
        DIACHI: '',
        DIACHI_ID: 0,
        TINH_ID: 0,
        QUAN_ID: 0,
        PHUONG_ID: 0,
        PHO_ID: 0,
        AP_ID: 0,
        KHU_ID: 0,
        DACDIEM_ID: 0,
        SO_NHA: '',
        Lng: 0,
        Lat: 0,
        BLOCK: '',
        TANG: 0,
        PHONG: 0,
        MOTA: ''
      }
      // Xóa key
      this.khachhang_id = 0
      this.hdkh_id = 0
      this._sl_cha = 0
      this.thuebao_id = 0
      this.phuongld_id = 0
      this.phold_id = 0
      this.apld_id = 0
      this.khuld_id = 0
      this.vdoituong_id = 0
      this.tocdo_id = 0
      this.muccuoc_id = 0
      this.trangbi_id = 0
      this.vmuccuoc_id = 0

      this.dtiendatcoc_tt = 0
      this.dvatdatcoc_tt = 0
      this.dtiendatcoc_km = 0
      this.dvatdatcoc_km = 0
      this.kmtt_id = -1
      this.tien_kmtt = 0
      this.tien_vat = 0

      this.dtienld = 0
      this.dvatld = 0
      this.dtiencd = 0
      this.dvatcd = 0
      this.dtien_dv = 0
      this.dvat_dv = 0
      this.dvattbi = 0
      this.dtientbi = 0
      this.dtienkmld = 0
      this.dvatkmld = 0
      this.dtienkmdc = 0
      this.dvatkmdc = 0
      this.dtien_kmtb = 0
      this.dvat_kmtb = 0
      this.tien_dv = 0
      this.vat_dv = 0
      this.tien_kmld = 0
      this.vat_kmld = 0
      this.tyle_kmld = 0
      //longdx: biến dùng cho trả trước
      this.tien_kmld_tt = 0
      this.vat_kmld_tt = 0
      this.tyle_kmld_tt = 0
      this.tong_kmld_tt = 0
      this.vat_tbi = 0
      this.tien_tbi = 0

      // Xóa form
      this.themmoi_tienkm = false
      this.dtpNgayLapHD.value = moment(new Date()).format('DD/MM/YYYY HH:mm:ss')
      this.dtpNgayYC.value = moment(new Date()).format('DD/MM/YYYY HH:mm:ss')
      this.txtMaHD.value = ''
      this.txtMaKH.value = ''
      this.txtTenKH.value = ''
      this.txtDiaChiKH.value = ''
      this.txtTenTB.value = ''
      this.txtDiaChiLD.value = ''
      this.txtDiaChiTB.value = ''
      this.txtGhiChu.value = ''
      this.txtTrangThaiTB.value = ''
      this.ds_init = []

      this.grcDanhSach.list = []
      this.dycDVCNTT = []
      this.layoutDV = []
      this.ds_tien_khoanmuc = []
      this.KHOAMO_FORM(false)
      this.txtTongTien.value = 0
      this.txtTongVat.value = 0

      // Cập nhật nút
      this.button.btnCapNhat.Visible = false
      this.button.btnHuyBo.Visible = false
      // dtControl = new DataTable();
      // cbo = new GridLookUpEdit[0]();
      this.button.btnXoaPhieu.Enabled = false
      this.button.tsbtnThanhToan.Enabled = false
      this.chkKhuyenMai.Checked = false
      this.chkKhuyenMai.Enabled = false
      this.chkTraTruoc.Checked = false
      this.chkTraTruoc.Enabled = false
      // cboTraTruoc.Properties.DataSource = null;
      // cboMucCuocTB.Properties.DataSource = null;
      // cboKhuyenMai.Properties.DataSource = null;
      this.DSKHUYENMAI = []
      this.DSTRATRUOC = []
      this.DTMUCCUOCTB = []

      // reset cac dich vu cong them
      this.m_dsThueBao_DichVu = []
      this.tien_dv = 0
      this.vat_dv = 0

      this.dsCTM_TBI = []
      this.vat_tbi = 0
      this.tien_tbi = 0
      this.DTDS_DVGT = []
      this.lbDichVuGT.list = []
      this.lbMuaThietBi.list = []
    },
    THAOTAC_NUT(kieu) {
      if (kieu == 1) {
        // Nhập mới
        this.XOA_FORM()
        this.KHOAMO_FORM(true)
        // Cập nhật nút
        this.button.btnHuyBo.Visible = true
        this.button.btnCapNhat.Visible = true
        this.button.btnNhapMoi.Enabled = false
        this.dtpNgayLapHD.value = moment(new Date()).format('DD/MM/YYYY HH:mm:ss')
        this.dtpNgayYC.value = moment(new Date()).format('DD/MM/YYYY HH:mm:ss')

        // Chọn control nhập
        // txtMaGD.Focus();
        this.$refs.txtMaGD.focus()
      } else if (kieu == 3) {
        // Hủy nhập
        // Xóa form
        this.XOA_FORM()

        // Cập nhật nút
        this.button.btnCapNhat.Visible = false
        this.button.btnHuyBo.Visible = false
        this.button.btnXoaPhieu.Visible = false
        this.button.tsbtnThanhToan.Visible = false
        this.button.btnNhapMoi.Enabled = true
        this.txtMaGD.value = ''
        this.txtMaTB.value = ''

        // grvDanhSach.FocusedRowHandle = GridControl.AutoFilterRowHandle;
      }
    },
    HienThiTTHopDongKH: async function(ds) {
      this.loading(true)
      try {
        this.THAOTAC_NUT(1)
        if (ds.length > 0) {
          if (ds[0]['MA_GD']) {
            this.txtMaGD.value = ds[0]['MA_GD'].toString()
          } else {
            this.ShowError('Chưa lấy được MA_GD: MA_GD null')
            return
          }

          this.txtMaHD.value = ds[0]['MA_HD'] ? ds[0]['MA_HD'] : ''
          this.txtMaKH.value = ds[0]['MA_KH'] ? ds[0]['MA_KH'] : ''
          this.cboKieuLD.value = ds[0]['KIEULD_ID'] ? Number(ds[0]['KIEULD_ID']) : 0
          this.cboLoaiHinhTB.value = ds[0]['LOAITB_ID'] ? Number(ds[0]['LOAITB_ID']) : 0
          if (ds[0]['KHACHHANG_ID'] && ds[0]['KHACHHANG_ID'].toString() != '') this.khachhang_id = Number(ds[0]['KHACHHANG_ID'])
          else this.khachhang_id = 0

          this.txtTenKH.value = ds[0]['TEN_KH'] ? this.ChuanHoaTen(ds[0]['TEN_KH'].toString()) : ''
          this.txtDiaChiKH.value = ds[0]['DIACHI_KH'] ? ds[0]['DIACHI_KH'] : ''
          this.dtpNgayYC.value = ds[0]['NGAY_YC'] ? moment(ds[0]['NGAY_YC']).format('DD/MM/YYYY HH:mm:ss') : ''
          this.dtpNgayLapHD.value = ds[0]['NGAYLAP_HD'] ? moment(ds[0]['NGAYLAP_HD']).format('DD/MM/YYYY HH:mm:ss') : ''
          this.vdoituong_id = Number(ds[0]['DOITUONG_ID'])

          if (this.cboKieuLD.value == 13286 && this.cboLoaiHinhTB.value == LoaiHinhTB.TTDL_HDDT) {
            this.button.btnDongBoHDDT.Visible = true
          } else {
            this.button.btnDongBoHDDT.Visible = false
          }
          if (ds[0]['CTV_ID'] && ds[0]['CTV_ID'].toString().trim() != '') {
            this.chkCTV = true
            this.ctv_id = Number(ds[0]['CTV_ID'])
            this.txtCTV = await this.getNhanvien_nvID(this.ctv_id)
          } else {
            this.txtCTV = ''
            this.chkCTV = false
          }
          if (ds[0]['NHANVIENGT_ID'] && ds[0]['NHANVIENGT_ID'].toString().trim() != '') {
            this.chkNguoiGT = true
            this.nguoigt_id = Number(ds[0]['NHANVIENGT_ID'])
            this.txtNguoiGT = await this.getNhanvien_nvID(this.nguoigt_id)
          } else {
            this.txtNguoiGT = ''
            this.chkNguoiGT = false
          }
          if (ds[0]['HDKH_ID']) {
            this.hdkh_id = Number(ds[0]['HDKH_ID'])
            var dt = await this.LAY_DS_HOPDONG_TB_THEO_HDKH_ID(Number(ds[0]['HDKH_ID']))
            await this.HienThiTTHopDongTB(dt)
          } else {
            this.ShowError('Chưa lấy được HDKH_ID: HDKH_ID null')
            return
          }
        } else {
          this.THAOTAC_NUT(3)
        }
      } catch (ex) {
        console.log(ex)
        this.ShowError('Lỗi nạp danh sách HDKH')
        this.THAOTAC_NUT(3)
      } finally {
        this.loading(false)
      }
    },
    HienThiTTHopDongTB: async function(ds) {
      try {
        if (ds.length > 0) {
          this.thuebao_id = Number(ds[0]['thuebao_id'])
          this.hdtb_id = Number(ds[0]['hdtb_id'])
          this.txtTenTB.value = ds[0]['ten_tb']
          this.txtMaTB.value = ds[0]['ma_tb']
          this.txtDiaChiLD.value = ds[0]['diachi_ld'] ? ds[0]['diachi_ld'] : ''
          this.txtDiaChiTB.value = ds[0]['diachi_tb'] ? ds[0]['diachi_tb'] : ''
          this.txtGhiChu.value = ds[0]['ghichu'] ? ds[0]['ghichu'] : ''
          this.cboMucCuocTB.value = Number(ds[0]['mucuoctb_id'])

          if (this.cboKieuLD.value == 13286 && this.cboLoaiHinhTB.value == LoaiHinhTB.TTDL_HDDT) {
            this.button.btnDongBoHDDT.Visible = true
          } else {
            this.button.btnDongBoHDDT.Visible = false
          }
          this.txtDiaChiLD.value = ds[0]['diachi_ld'] ? ds[0]['diachi_ld'] : ''
          this.txtDiaChiTB.value = ds[0]['diachi_tb'] ? ds[0]['diachi_tb'] : ''
          // DS thong tin hop dong
          var input1 = {
            thuebao_id: Number(ds[0]['thuebao_id']),
            loaitb_id: Number(ds[0]['loaitb_id']),
            hdkh_id: this.hdkh_id > 0 ? this.hdkh_id : ds[0]['hdkh_id'] ? Number(ds[0]['hdkh_id']) : 0,
            hdtb_id: this.hdtb_id > 0 ? this.hdtb_id : ds[0]['hdtb_id'] ? Number(ds[0]['hdtb_id']) : 0
          }
          await this.hienthi_tt_hopdong(input1)
          this.ds_init = []
          var ds_phophuong = this.LowerCasePropertyList(this.DS_PHOPHUONG)
          this.NAP_DS_PHOPHUONG(ds_phophuong)
          var dt_diachi_tb = this.DT_DIACHI_TB
          for (var i = 0; i < dt_diachi_tb.length; i++) {
            var row_dc = dt_diachi_tb[i]
            if (row_dc['DIACHITHUEBAO'].toString() == '1') {
              // this.GanDiaChi(ref this.diaChiThueBao, row_dc);
              this['diachiTB'].DIACHI_ID = row_dc.DIACHI_ID && row_dc.DIACHI_ID > -1 ? row_dc.DIACHI_ID : 0
              this['diachiTB'].DIACHI = row_dc.DIACHI ? row_dc.DIACHI : ''
              this['diachiTB'].TINH_ID = row_dc.TINH_ID && row_dc.TINH_ID > -1 ? row_dc.TINH_ID : 0
              this['diachiTB'].QUAN_ID = row_dc.QUAN_ID && row_dc.QUAN_ID > -1 ? row_dc.QUAN_ID : 0
              this['diachiTB'].PHUONG_ID = row_dc.PHUONG_ID && row_dc.PHUONG_ID > -1 ? row_dc.PHUONG_ID : 0
              this['diachiTB'].PHO_ID = row_dc.PHO_ID && row_dc.PHO_ID > -1 ? row_dc.PHO_ID : 0
              this['diachiTB'].AP_ID = row_dc.AP_ID && row_dc.AP_ID > -1 ? row_dc.AP_ID : 0
              this['diachiTB'].KHU_ID = row_dc.KHU_ID && row_dc.KHU_ID > -1 ? row_dc.KHU_ID : 0
              this['diachiTB'].DACDIEM_ID = row_dc.DACDIEM_ID && row_dc.DACDIEM_ID > -1 ? row_dc.DACDIEM_ID : 0
              this['diachiTB'].SO_NHA = row_dc.SONHA ? row_dc.SONHA : ''
              this['diachiTB'].Lat = row_dc.VIDO ? row_dc.VIDO : 0
              this['diachiTB'].Lng = row_dc.KINHDO ? row_dc.KINHDO : 0
              this.txtDiaChiTB.value = this['diachiTB'].DIACHI
            }
            if (row_dc['DIACHITHUEBAO'].toString() == '0') {
              // this.GanDiaChi(ref this.diachiLapDat, row_dc);
              this['diachiLD'].DIACHI_ID = row_dc.DIACHI_ID && row_dc.DIACHI_ID > -1 ? row_dc.DIACHI_ID : 0
              this['diachiLD'].DIACHI = row_dc.DIACHI ? row_dc.DIACHI : ''
              this['diachiLD'].TINH_ID = row_dc.TINH_ID && row_dc.TINH_ID > -1 ? row_dc.TINH_ID : 0
              this['diachiLD'].QUAN_ID = row_dc.QUAN_ID && row_dc.QUAN_ID > -1 ? row_dc.QUAN_ID : 0
              this['diachiLD'].PHUONG_ID = row_dc.PHUONG_ID && row_dc.PHUONG_ID > -1 ? row_dc.PHUONG_ID : 0
              this['diachiLD'].PHO_ID = row_dc.PHO_ID && row_dc.PHO_ID > -1 ? row_dc.PHO_ID : 0
              this['diachiLD'].AP_ID = row_dc.AP_ID && row_dc.AP_ID > -1 ? row_dc.AP_ID : 0
              this['diachiLD'].KHU_ID = row_dc.KHU_ID && row_dc.KHU_ID > -1 ? row_dc.KHU_ID : 0
              this['diachiLD'].DACDIEM_ID = row_dc.DACDIEM_ID && row_dc.DACDIEM_ID > -1 ? row_dc.DACDIEM_ID : 0
              this['diachiLD'].SO_NHA = row_dc.SONHA ? row_dc.SONHA : ''
              this['diachiLD'].Lat = row_dc.VIDO ? row_dc.VIDO : 0
              this['diachiLD'].Lng = row_dc.KINHDO ? row_dc.KINHDO : 0
              // this.txtDiaChiLD.value = this["diachiLD"].DIACHI;
            }
            this['diachiLD'].DIACHI_TEXT = this.txtDiaChiLD.value
            this['diachiTB'].DIACHI_TEXT = this.txtDiaChiTB.value
          }
          var dt_dbtbsub = this.DT_DBTBSUB
          if (dt_dbtbsub.length > 0) {
            try {
              this._hdtbsub.CHUCDANH = dt_dbtbsub[0]['CHUCDANH'] ? dt_dbtbsub[0]['CHUCDANH'].toString() : ''
              this._hdtbsub.NGUOI_KY_HOSO = dt_dbtbsub[0]['NGUOI_KY_HOSO'] ? dt_dbtbsub[0]['NGUOI_KY_HOSO'].toString() : ''
              this._hdtbsub.LOAIGT_ID = dt_dbtbsub[0]['LOAIGT_ID'] ? Number(dt_dbtbsub[0]['LOAIGT_ID']) : 0
              // try {
              //   this._hdtbsub.NGAYCAP = moment(dt_dbtbsub[0]['NGAYCAP']).format('DD/MM/YYYY HH:mm:ss')
              // } catch (ex) {
              //   this._hdtbsub.NGAYCAP = ''
              // }
              try {
                this._hdtbsub.NGAYHETHAN_GT = moment(dt_dbtbsub[0]['NGAYHETHAN_GT']).format('DD/MM/YYYY HH:mm:ss')
              } catch (ex) {
                this._hdtbsub.NGAYHETHAN_GT = ''
              }
              // try {
              //   this._hdtbsub.NGAYSINH = moment(dt_dbtbsub[0]['NGAYSINH']).format('DD/MM/YYYY HH:mm:ss')
              // } catch (ex) {
              //   this._hdtbsub.NGAYSINH = ''
              // }
              this._hdtbsub.GIOITINH = dt_dbtbsub[0]['GIOITINH'] ? dt_dbtbsub[0]['GIOITINH'].toString() : ''
              this._hdtbsub.NOICAP = dt_dbtbsub[0]['NOICAP'] ? dt_dbtbsub[0]['NOICAP'].toString() : ''
              this._hdtbsub.EMAIL = dt_dbtbsub[0]['EMAIL'] ? dt_dbtbsub[0]['EMAIL'].toString() : ''
              this._hdtbsub.SO_DT = dt_dbtbsub[0]['SO_DT'] ? dt_dbtbsub[0]['SO_DT'].toString() : ''
              this._hdtbsub.SO_GT = dt_dbtbsub[0]['SO_GT'] ? dt_dbtbsub[0]['SO_GT'].toString() : ''
              this._hdtbsub.NGUOI_DD = dt_dbtbsub[0]['NGUOI_DD'] ? dt_dbtbsub[0]['NGUOI_DD'].toString() : ''
              this._hdtbsub.HDTB_ID = this.hdtb_id
            } catch (ex) {}
          }
          // Setup nút
          this.button.btnCapNhat.Visible = true
          this.button.tsbtnThanhToan.Visible = true
          this.button.tsbtnThanhToan.Enabled = true
          this.button.btnHuyBo.Visible = true
          this.button.btnXoaPhieu.Enabled = true
          this.button.btnXoaPhieu.Visible = true
          this.button.btnNhapMoi.Enabled = true
          this.grcDanhSach.list = ds
          var filter_dvcntt = listDichVuCNTT.filter((e) => e == Number(this.cboDichVuVT.value))
          if (filter_dvcntt.length > 0) {
            var dt = {
              THUEBAO_ID: Number(ds[0]['thuebao_id'].toString()),
              HDKH_ID: 0,
              HDTB_ID: this.hdtb_id,
              DICHVUVT_ID: Number(this.cboDichVuVT.value),
              LOAITB_ID: Number(this.cboLoaiHinhTB.value),
              KIEULD_ID: Number(this.cboKieuLD.value),
              PHUONGLD_ID: this.phuongld_id,
              PHOLD_ID: this.phold_id,
              APLD_ID: this.apld_id,
              KHULD_ID: this.khuld_id,
              DOITUONG_ID: Number(ds[0]['doituong_id']),
              TRANGBI_ID: this.trangbi_id,
              MUCCUOCTB_ID: Number(ds[0]['mucuoctb_id'])
            }

            await this.LoadThongTin_DV_CNTT(Number(this.cboLoaiHinhTB.value))
            await this.Hienthi_TT_DB_Morong(dt)
          }
          //Mở ra khi lên con thật
          var ds_dbkh = await this.LAY_DANHBA_THEO_MATB(this.txtMaTB.value, Number(this.cboDichVuVT.value))
          if (ds_dbkh.length > 0) {
            this.kieukh_id = ds_dbkh[0]['kieukh_id']
            this.loaikh_id = ds_dbkh[0]['loaikh_id']
            this.txtTrangThaiTB.value = ds_dbkh[0]['trangthai_tb'] ? ds_dbkh[0]['trangthai_tb'].toString() : ''
          }
          await this.HT_GIAODIEN_DONVI(Number(ds[0]['donvi_id'].toString()), 0, this.hdtb_id)
          await this.NAP_DS_KM_DC(this.hdtb_id)
          await this.NAP_DS_KMTT(Number(this.cboKieuLD.value), this.hdtb_id)
          await this.HT_DichVuGT()
          await this.HT_ThietBi()
          await this.LayTongTien_HDTB()
          if (Number(this.cboKieuLD.value) == 13187 || Number(this.cboKieuLD.value) == 13188 || Number(this.cboKieuLD.value) == 13229 || Number(this.cboKieuLD.value) == 13238) {
            this.button.tsbtnGiaoPhieu.Visible = true
          }
        }
      } catch (ex) {
        console.log('HienThiTTHopDongTB')
        console.log(ex)
      }
    },
    HienThiTTDanhBaKH: async function(ma_tb, dichvu_id) {
      try {
        this.THAOTAC_NUT(1)
        var ds_dbkh = await this.LAY_DANHBA_THEO_MATB(ma_tb, dichvu_id)
        // console.log(ds_dbkh);
        if (ds_dbkh && ds_dbkh.length > 0) {
          await this.hienthi_dbkh(ma_tb, dichvu_id)
          var trangthai_tb_id = Number(ds_dbkh[0]['trangthaitb_id'].toString())
          if (trangthai_tb_id == 7 || trangthai_tb_id == 8 || trangthai_tb_id == 9) {
            this.ShowError('Không thể gia hạn hoặc thay đổi thông tin cho thuê bao đã bị thu hồi!')
            this.THAOTAC_NUT(1)
            return
          }
          //Thông tin khách hàng
          this.kieukh_id = ds_dbkh[0]['kieukh_id']
          this.loaikh_id = ds_dbkh[0]['loaikh_id']
          if (ds_dbkh[0]['khachhang_id']) {
            this.khachhang_id = Number(ds_dbkh[0]['khachhang_id'].toString())
          } else this.ShowError('Không có khách hàng ID')
          this.cboLoaiHinhTB.value = ds_dbkh[0]['loaitb_id'] ? ds_dbkh[0]['loaitb_id'] : 0
          this.txtMaHD.value = ds_dbkh[0]['ma_hd'] ? ds_dbkh[0]['ma_hd'].toString() : ''
          this.txtMaKH.value = ds_dbkh[0]['ma_kh'] ? ds_dbkh[0]['ma_kh'].toString() : ''
          this.txtTenKH.value = ds_dbkh[0]['ten_kh'] ? ds_dbkh[0]['ten_kh'].toString() : '' //common chuanhoa
          this.txtDiaChiKH.value = ds_dbkh[0]['diachi_kh'] ? ds_dbkh[0]['diachi_kh'].toString() : '' //common chuanhoa
          if (Number(this.cboKieuLD.value) == 13286 && Number(this.cboLoaiHinhTB.value) == LoaiHinhTB.TTDL_HDDT) {
            this.button.btnDongBoHDDT.Visible = true
          } else {
            this.button.btnDongBoHDDT.Visible = false
          }
          //Thông tin thuê bao
          this.thuebao_id = Number(ds_dbkh[0]['thuebao_id'])

          var dt_dbtbsub = this.DT_DBTBSUB
          if (dt_dbtbsub && dt_dbtbsub.length > 0) {
            try {
              this._hdtbsub.CHUCDANH = dt_dbtbsub[0]['CHUCDANH'] ? dt_dbtbsub[0]['CHUCDANH'].toString() : ''
              this._hdtbsub.NGUOI_KY_HOSO = dt_dbtbsub[0]['NGUOI_KY_HOSO'] ? dt_dbtbsub[0]['NGUOI_KY_HOSO'].toString() : ''
              this._hdtbsub.LOAIGT_ID = dt_dbtbsub[0]['LOAIGT_ID'] ? Number(dt_dbtbsub[0]['LOAIGT_ID']) : 0
              // try {
              //   this._hdtbsub.NGAYCAP = moment(dt_dbtbsub[0]['NGAYCAP']).format('DD/MM/YYYY HH:mm:ss')
              // } catch (ex) {
              //   this._hdtbsub.NGAYCAP = ''
              // }
              try {
                this._hdtbsub.NGAYHETHAN_GT = moment(dt_dbtbsub[0]['NGAYHETHAN_GT']).format('DD/MM/YYYY HH:mm:ss')
              } catch (ex) {
                this._hdtbsub.NGAYHETHAN_GT = ''
              }
              // try {
              //   this._hdtbsub.NGAYSINH = moment(dt_dbtbsub[0]['NGAYSINH']).format('DD/MM/YYYY HH:mm:ss')
              // } catch (ex) {
              //   this._hdtbsub.NGAYSINH = ''
              // }
              this._hdtbsub.GIOITINH = dt_dbtbsub[0]['GIOITINH'] ? dt_dbtbsub[0]['GIOITINH'].toString() : ''
              this._hdtbsub.NOICAP = dt_dbtbsub[0]['NOICAP'] ? dt_dbtbsub[0]['NOICAP'].toString() : ''
              this._hdtbsub.EMAIL = dt_dbtbsub[0]['EMAIL'] ? dt_dbtbsub[0]['EMAIL'].toString() : ''
              this._hdtbsub.SO_DT = dt_dbtbsub[0]['SO_DT'] ? dt_dbtbsub[0]['SO_DT'].toString() : ''
              this._hdtbsub.SO_GT = dt_dbtbsub[0]['SO_GT'] ? dt_dbtbsub[0]['SO_GT'].toString() : ''
              this._hdtbsub.NGUOI_DD = dt_dbtbsub[0]['NGUOI_DD'] ? dt_dbtbsub[0]['NGUOI_DD'].toString() : ''
              this._hdtbsub.HDTB_ID = this.hdtb_id
            } catch (ex) {}
          }
          this.HIENTHIDV_GTGT_SD(this.DTDS_DVGT)
          this.saveList = []
          if (this.LAYDANHBACT && this.LAYDANHBACT.length > 0) {
            this.LAYDANHBACT.forEach((item) => {
              var hdtbct = {}
              hdtbct.DAIDIEN = item.DAIDIEN
              hdtbct.DIACHI_LD = item.DIACHI_LD
              hdtbct.DOMAIN_CT = item.DOMAIN_CT
              hdtbct.DT_GIAMHO = item.DT_GIAMHO
              hdtbct.EMAIL_CT = item.EMAIL_CT
              hdtbct.HDTB_CT_ID = 0
              hdtbct.HDTB_ID = 0
              hdtbct.IP_CT = item.IP_CT
              hdtbct.LOAITB_ID = item.LOAITB_ID
              hdtbct.MA_CHUAN_CT = item.MA_CHUAN_CT
              hdtbct.MA_PHU_CT = item.MA_PHU_CT
              hdtbct.MST_CT = item.MST_CT
              hdtbct.PARENT_FIELD = item.PARENT_FIELD
              hdtbct.SO_DT_CT = item.SO_DT_CT
              hdtbct.TEN_TB_CT = item.TEN_TB_CT
              hdtbct.WEBSITE_CT = item.WEBSITE_CT
              this.saveList.push(hdtbct)
            })
          }

          this.txtMaTB.value = ds_dbkh[0]['ma_tb'] ? ds_dbkh[0]['ma_tb'].toString() : ''
          this.txtTenTB.value = ds_dbkh[0]['ten_tb'] ? ds_dbkh[0]['ten_tb'].toString() : ''
          this.txtDiaChiTB.value = ds_dbkh[0]['diachi_tb'] ? ds_dbkh[0]['diachi_tb'].toString() : ''
          this.txtDiaChiLD.value = ds_dbkh[0]['diachi_ld'] ? ds_dbkh[0]['diachi_ld'].toString() : ''
          this.txtTrangThaiTB.value = ds_dbkh[0]['trangthai_tb'] ? ds_dbkh[0]['trangthai_tb'].toString() : ''
          this.vdoituong_id = ds_dbkh[0]['doituong_id'] ? Number(ds_dbkh[0]['doituong_id'].toString()) : 0
          this.cboMucCuocTB.value = ds_dbkh[0]['mucuoctb_id'] ? Number(ds_dbkh[0]['mucuoctb_id']) : 0
          this.nguoi_dd = ds_dbkh[0]['nguoi_dd'] ? ds_dbkh[0]['nguoi_dd'] : ''

          var dt_diachi_tb = this.DT_DIACHI_TB
          if (dt_diachi_tb && dt_diachi_tb.length > 0) {
            for (var i = 0; i < dt_diachi_tb.length; i++) {
              var row_dc = dt_diachi_tb[i]
              if (row_dc['DIACHITHUEBAO'].toString() == '1') {
                // this.GanDiaChi(ref this.diaChiThueBao, row_dc);
                this['diachiTB'].DIACHI_ID = row_dc.DIACHI_ID && row_dc.DIACHI_ID > -1 ? row_dc.DIACHI_ID : 0
                this['diachiTB'].DIACHI = row_dc.DIACHI ? row_dc.DIACHI : ''
                this['diachiTB'].TINH_ID = row_dc.TINH_ID && row_dc.TINH_ID > -1 ? row_dc.TINH_ID : 0
                this['diachiTB'].QUAN_ID = row_dc.QUAN_ID && row_dc.QUAN_ID > -1 ? row_dc.QUAN_ID : 0
                this['diachiTB'].PHUONG_ID = row_dc.PHUONG_ID && row_dc.PHUONG_ID > -1 ? row_dc.PHUONG_ID : 0
                this['diachiTB'].PHO_ID = row_dc.PHO_ID && row_dc.PHO_ID > -1 ? row_dc.PHO_ID : 0
                this['diachiTB'].AP_ID = row_dc.AP_ID && row_dc.AP_ID > -1 ? row_dc.AP_ID : 0
                this['diachiTB'].KHU_ID = row_dc.KHU_ID && row_dc.KHU_ID > -1 ? row_dc.KHU_ID : 0
                this['diachiTB'].DACDIEM_ID = row_dc.DACDIEM_ID && row_dc.DACDIEM_ID > -1 ? row_dc.DACDIEM_ID : 0
                this['diachiTB'].SO_NHA = row_dc.SONHA ? row_dc.SONHA : ''
                this['diachiTB'].Lat = row_dc.VIDO ? row_dc.VIDO : 0
                this['diachiTB'].Lng = row_dc.KINHDO ? row_dc.KINHDO : 0
                this.txtDiaChiTB.value = this['diachiTB'].DIACHI
              }
              if (row_dc['DIACHITHUEBAO'].toString() == '0') {
                // this.GanDiaChi(ref this.diachiLapDat, row_dc);
                this['diachiLD'].DIACHI_ID = row_dc.DIACHI_ID && row_dc.DIACHI_ID > -1 ? row_dc.DIACHI_ID : 0
                this['diachiLD'].DIACHI = row_dc.DIACHI ? row_dc.DIACHI : ''
                this['diachiLD'].TINH_ID = row_dc.TINH_ID && row_dc.TINH_ID > -1 ? row_dc.TINH_ID : 0
                this['diachiLD'].QUAN_ID = row_dc.QUAN_ID && row_dc.QUAN_ID > -1 ? row_dc.QUAN_ID : 0
                this['diachiLD'].PHUONG_ID = row_dc.PHUONG_ID && row_dc.PHUONG_ID > -1 ? row_dc.PHUONG_ID : 0
                this['diachiLD'].PHO_ID = row_dc.PHO_ID && row_dc.PHO_ID > -1 ? row_dc.PHO_ID : 0
                this['diachiLD'].AP_ID = row_dc.AP_ID && row_dc.AP_ID > -1 ? row_dc.AP_ID : 0
                this['diachiLD'].KHU_ID = row_dc.KHU_ID && row_dc.KHU_ID > -1 ? row_dc.KHU_ID : 0
                this['diachiLD'].DACDIEM_ID = row_dc.DACDIEM_ID && row_dc.DACDIEM_ID > -1 ? row_dc.DACDIEM_ID : 0
                this['diachiLD'].SO_NHA = row_dc.SONHA ? row_dc.SONHA : ''
                this['diachiLD'].Lat = row_dc.VIDO ? row_dc.VIDO : 0
                this['diachiLD'].Lng = row_dc.KINHDO ? row_dc.KINHDO : 0
                // this.txtDiaChiLD.value = this["diachiLD"].DIACHI;
              }
              this['diachiLD'].DIACHI_TEXT = this.txtDiaChiLD.value
              this['diachiTB'].DIACHI_TEXT = this.txtDiaChiTB.value
            }
          }

          this.ds_init = []
          var ds_phophuong = this.LowerCasePropertyList(this.DS_PHOPHUONG)
          this.NAP_DS_PHOPHUONG(ds_phophuong)
          var filter_dvcntt = listDichVuCNTT.filter((e) => e == Number(this.cboDichVuVT.value))

          if (filter_dvcntt.length > 0) {
            var dt = {
              THUEBAO_ID: Number(ds_dbkh[0]['thuebao_id'].toString()),
              HDKH_ID: 0,
              HDTB_ID: 0,
              DICHVUVT_ID: Number(this.cboDichVuVT.value),
              LOAITB_ID: Number(this.cboLoaiHinhTB.value),
              KIEULD_ID: Number(this.cboKieuLD.value),
              PHUONGLD_ID: this.phuongld_id,
              PHOLD_ID: this.phold_id,
              APLD_ID: this.apld_id,
              KHULD_ID: this.khuld_id,
              DOITUONG_ID: this.vdoituong_id,
              TRANGBI_ID: this.trangbi_id,
              MUCCUOCTB_ID: this.cboMucCuocTB.value
            }

            await this.LoadThongTin_DV_CNTT(Number(this.cboLoaiHinhTB.value))
            await this.Hienthi_TT_DB_Morong(dt)
          }
          var donviql_id = ds_dbkh[0]['donviql_id'] ? Number(ds_dbkh[0]['donviql_id']) : 0
          var thuebao_id = ds_dbkh[0]['thuebao_id'] ? Number(ds_dbkh[0]['thuebao_id']) : 0

          await this.HT_GIAODIEN_DONVI(donviql_id, thuebao_id, 0)

          await this.NAP_DS_CTTKM(this.cboKieuLD.value)
        } else {
          this.ShowError('Nhập lại mã thuê bao tìm kiếm !')
          this.$refs.txtMaTB.focus()
        }
      } catch (ex) {
        console.log('HienThiTTDanhBaKH')
        console.log(ex)
        this.THAOTAC_NUT(3)
      }
    },
    LAY_DANHBA_THEO_MATB: async function(ma_tb, dichvu_id) {
      var input = {
        in_ma_tb: ma_tb,
        in_dichvuvt_id: dichvu_id,
        in_donvi_dl_id: 0
      }
      let data = this.GetData(await api.LAY_DANHBA_THEO_MATB(this.axios, input))
      return data
    },
    NAP_DS_PHOPHUONG(ds_dcld) {
      if (!ds_dcld || ds_dcld.length == 0) {
        this.phuongld_id = 0
        this.phold_id = 0
        this.apld_id = 0
        this.khuld_id = 0
        return
      }
      if (ds_dcld[0]['phuongld_id'] && ds_dcld[0]['phuongld_id'].toString() != '') this.phuongld_id = Number(ds_dcld[0]['phuongld_id'].toString())
      else this.phuongld_id = 0

      if (ds_dcld[0]['phold_id'] && ds_dcld[0]['phold_id'].toString() != '') this.phold_id = Number(ds_dcld[0]['phold_id'].toString())
      else this.phold_id = 0

      if (ds_dcld[0]['apld_id'] && ds_dcld[0]['apld_id'].toString() != '') this.apld_id = Number(ds_dcld[0]['apld_id'].toString())
      else this.apld_id = 0

      if (ds_dcld[0]['khuld_id'] && ds_dcld[0]['khuld_id'].toString() != '') this.khuld_id = Number(ds_dcld[0]['khuld_id'].toString())
      else this.khuld_id = 0
    },
    HT_GIAODIEN_DONVI: async function(vdonvi_ql_id, tb_id, hd_id) {
      try {
        if (!this.cboKieuLD.value || this.cboKieuLD.value == null) return
        // Hiển thị danh sách combo loại đơn vị theo dịch vụ viễn thông
        this.layoutDV = []
        if (this.cboDichVuVT.value == DichVuVienThong.DICHVU_CNTT || this.cboDichVuVT.value == DichVuVienThong.ANTOAN_BAOMAT_TT || this.cboDichVuVT.value == DichVuVienThong.TRUNGTAM_DULIEU || this.cboDichVuVT.value == DichVuVienThong.HOINGHI_TRUYENHINH) {
          var dt = {
            dichvuvt_id: Number(this.cboDichVuVT.value),
            kieuld_id: Number(this.cboKieuLD.value),
            loaihinhtb_id: Number(this.cboLoaiHinhTB.value),
            phanvung_id: this.tt_nd.phanvung_id,
            tocdo_id: this.tocdo_id
          }
          var tmp_tt_giaodien = await this.lay_tt_giaodien_dv(dt)
          if (tmp_tt_giaodien && tmp_tt_giaodien.length > 0) {
            var layoutDV = this.UpperCasePropertyList(tmp_tt_giaodien)

            for (var i = 0; i < layoutDV.length; i++) {
              if (layoutDV[i]['LOAIDV_DICH_ID'].toString() == LOAI_DV.TRAM_VT.toString() || layoutDV[i]['LOAIDV_DICH_ID'].toString() == LOAI_DV.TRAM_CAP_PORT.toString()) {
                var ds_donvi_tramtc = this.UpperCasePropertyList(await this.getDS_DONVI_TRAMTC_BANTHIETBI(vdonvi_ql_id))
                layoutDV[i].SELECT_DV = -1
                layoutDV[i]['DS_DONVI'] = ds_donvi_tramtc
              } else {
                var ds_donvi_banthietbi = this.UpperCasePropertyList(await this.getDS_DONVI_THEO_LOAIDV_BANTHIETBI(vdonvi_ql_id, Number(layoutDV[i]['LOAIDV_DICH_ID'].toString())))
                layoutDV[i].SELECT_DV = -1
                layoutDV[i]['DS_DONVI'] = ds_donvi_banthietbi
              }
            }

            this.layoutDV = layoutDV
          }
          var tmp_dbtb_dv = await this.load_hdtbdv(hd_id, tb_id)
          if (tmp_dbtb_dv && tmp_dbtb_dv.length > 0) {
            var dbtb_dv = this.UpperCasePropertyList(tmp_dbtb_dv)
            if (hd_id > 0) {
              if (dbtb_dv.length > 0) {
                this.layoutDV.forEach((dr) => {
                  var kiemtra = dbtb_dv.filter((e) => e.LOAIDV_ID == dr.LOAIDV_DICH_ID)
                  if (kiemtra.length > 0) {
                    dr.SELECT_DV = kiemtra[0].DONVI_ID
                  }
                })
              }
            } else if (tb_id > 0) {
              for (var i = 0; i < this.layoutDV.length; i++) {
                if (Number(this.layoutDV[i].LOAIDV_DICH_ID) == LOAI_DV.TTKD_NGHIEMTHU) {
                  this.layoutDV[i].SELECT_DV = this.tt_nd.donvi_id
                } else if (Number(this.layoutDV[i].LOAIDV_DICH_ID) == LOAI_DV.KENHBAN_THICONG_DV || Number(this.layoutDV[i].LOAIDV_DICH_ID) == LOAI_DV.PBH_KHAIBAO_DV) {
                  await this.HienThi_PBH_KBH()
                } else {
                  var kiemtra = dbtb_dv.filter((e) => e.LOAIDV_ID == this.layoutDV[i].LOAIDV_DICH_ID)
                  if (kiemtra.length > 0) {
                    this.layoutDV[i].SELECT_DV = kiemtra[0].DONVI_ID
                  } else {
                    this.layoutDV[i].SELECT_DV = this.layoutDV[i].DS_DONVI.length > 0 ? this.layoutDV[i].DS_DONVI[0].DONVI_ID : -1
                  }
                }
              }
            }
          }
        }
      } catch (ex) {
        console.log('HT_GIAODIEN_DONVI')
        console.log(ex)
      }
    },
    HienThi_PBH_KBH: async function() {
      if (this.layoutDV.length > 0) {
        for (var i = 0; i < this.layoutDV.length; i++) {
          if (Number(this.layoutDV[i]['LOAIDV_DICH_ID'].toString()) == LOAI_DV.KENHBAN_THICONG_DV) {
            var dt = {
              ctv_id: Number(this.ctv_id),
              hdkh_id: 0,
              nguoi_gt_id: Number(this.nguoigt_id),
              phanvung_id: this.tt_nd.phanvung_id,
              phieu_id: 0
            }
            var vdonvi_kb = await this.lay_kenhban_caidat_dv(dt)
            if (vdonvi_kb != -1) {
              this.layoutDV[i].SELECT_DV = vdonvi_kb
            }
          }
          if (Number(this.layoutDV[i]['LOAIDV_DICH_ID'].toString()) == LOAI_DV.PBH_KHAIBAO_DV) {
            var dt = {
              ctv_id: Number(this.ctv_id),
              hdkh_id: 0,
              nguoi_gt_id: Number(this.nguoigt_id),
              phanvung_id: this.tt_nd.phanvung_id,
              phieu_id: 0
            }
            var vdonvi_cd = await this.lay_pbh_caidat_dv(dt)
            if (vdonvi_cd != -1) {
              this.layoutDV[i].SELECT_DV = vdonvi_cd
            }
          }
        }
      }
    },
    lay_tt_giaodien_dv: async function(dt) {
      var input = {
        dichvuvt_id: dt.dichvuvt_id,
        kieuld_id: dt.kieuld_id,
        loaihinhtb_id: dt.loaihinhtb_id,
        phanvung_id: dt.phanvung_id,
        tocdo: dt.tocdo_id
      }
      let response = await api.ht_giaodien_donvi(this.axios, input)
      if (response && response.data && response.data.error_code == 'BSS-00000000') {
        return response.data.data
      } else if (response.data.error_code == 'BSS-00000204') {
        // this.ShowError("Không có TT đơn vị ");
        return []
      } else {
        this.ShowError('Lỗi lấy TT đơn vị ' + response.data.message_detail)
        return []
      }
    },
    getDS_DONVI_TRAMTC_BANTHIETBI: async function(donvi_ql_id) {
      var input = {
        donviql_id: donvi_ql_id,
        loaidv_id: LOAI_DV.TRAM_VT
      }
      var data = []
      data = this.GetData(await api.getDS_DONVI_TRAMTC_BANTHIETBI(this.axios, input))
      return data
    },
    getDS_DONVI_THEO_LOAIDV_BANTHIETBI: async function(donvi_ql_id, loaidv_dich_id) {
      var input = {
        donviql_id: donvi_ql_id,
        loaidv_id: loaidv_dich_id
      }
      var data = []
      data = this.GetData(await api.getDS_DONVI_THEO_LOAIDV_BANTHIETBI(this.axios, input))
      return data
    },
    load_hdtbdv: async function(hd_id, tb_id) {
      var input = {
        hd_id: hd_id,
        tb_id: tb_id,
        phanvung_id: this.tt_nd.phanvung_id
      }
      let response = await api.load_hdtbdv(this.axios, input)
      if (response && response.data && response.data.error_code == 'BSS-00000000') {
        return response.data.data
      } else if (response.data.error_code == 'BSS-00000204') {
        // this.ShowError("Không có TT đơn vị của HDTB");
        return []
      } else {
        this.ShowError('Lỗi khi lấy TT đơn vị của HDTB  ' + response.data.message_detail)
        return []
      }
    },
    lay_kenhban_caidat_dv: async function(dt) {
      var input = {
        ctv_id: dt.ctv_id,
        hdkh_id: dt.hdkh_id,
        nguoi_gt_id: dt.nguoi_gt_id,
        phanvung_id: dt.phanvung_id,
        phieu_id: dt.phieu_id
      }
      let response = await api.lay_kenhban_caidat_dv(this.axios, input)
      if (response && response.data && response.data.error_code == 'BSS-00000000') {
        return response.data.data
      } else {
        this.ShowError('Lỗi khi lấy DS kênh bán hàng ' + response.data.message_detail)
        return -1
      }
    },
    lay_pbh_caidat_dv: async function(dt) {
      var input = {
        ctv_id: dt.ctv_id,
        hdkh_id: dt.hdkh_id,
        nguoi_gt_id: dt.nguoi_gt_id,
        phanvung_id: dt.phanvung_id,
        phieu_id: dt.phieu_id
      }
      let response = await api.lay_pbh_caidat_dv(this.axios, input)
      if (response && response.data && response.data.error_code == 'BSS-00000000') {
        return response.data.data
      } else {
        this.ShowError('Lỗi khi lấy DS phòng bán hàng ' + response.data.message_detail)
        return -1
      }
    },
    HIENTHIDV_GTGT_SD(dtds_dvgt) {
      // this.lbDichVuGT.list = []
      // var dtds_dvgt = this.DTDS_DVGT
      if (dtds_dvgt && dtds_dvgt.length > 0) {
        for (let item of dtds_dvgt) {
          var noidung = item['NOIDUNG'] ? ' - Số lượng: ' + item['NOIDUNG'] : ''
          item['TEN_DVGT'] = item['TEN_DVGT'] + noidung
        }
        this.lbDichVuGT.list = dtds_dvgt
      }
    },
    HT_DichVuGT() {
      try {
        if (Number(this.cboKieuLD.value) == 13187 || Number(this.cboKieuLD.value) == 13188 || Number(this.cboKieuLD.value) == 13229 || Number(this.cboKieuLD.value) == 13238)
          // không sử dụng với loại yêu cầu hỗ trợ vì không có tiền
          return
        this.lbDichVuGT.list = []
        var ds_dk_dvgt = this.m_dsThueBao_DichVu && this.m_dsThueBao_DichVu ? this.UpperCasePropertyList(this.m_dsThueBao_DichVu) : []
        if (ds_dk_dvgt.length > 0) {
          ds_dk_dvgt = ds_dk_dvgt.filter((e) => (e['DANGKY'] && e['DANGKY'].toString() == '1') || (e['SUA'] && e['SUA'].toString() == '1') || (e['HUY'] && e['HUY'].toString() == '1') || (e['SUDUNG'] && e['SUDUNG'].toString() == 'Su dung'))
          if (ds_dk_dvgt.length > 0) {
            for (var i = 0; i < ds_dk_dvgt.length; i++) {
              var r = ds_dk_dvgt[i]
              if (!r['TEN_DVGT']) {
                this.ShowError('TEN_DVGT is null')
                return
              }
              var ten_dvgt = '[' + r['DICHVUGT_ID'] + '] ' + r['TEN_DVGT'].toString() + ' - ' + (r['NOIDUNG'] ? r['NOIDUNG'].toString() : '')

              if (r['DANGKY'] && r['DANGKY'].toString() == '1') {
                r.TEN_DVGT = ten_dvgt + ' (' + 'ĐĂNG KÝ' + ')'
              }
              if (r['SUA'] && r['SUA'].toString() == '1') {
                r.TEN_DVGT = ten_dvgt + ' (' + 'THAY ĐỔI' + ')'
              }
              if (r['HUY'] && r['HUY'].toString() == '1') {
                r.TEN_DVGT = ten_dvgt + ' (' + 'HỦY' + ')'
              }
              if (Number(r['DANGKY']).toString() != '1' && Number(r['SUA']).toString() != '1' && Number(r['HUY']).toString() != '1' && r['SUDUNG'] && r['SUDUNG'].toString() == 'Su dung') {
                r.TEN_DVGT = ten_dvgt + ' (' + 'SỬ DỤNG' + ')'
              }
              ds_dk_dvgt[i] = r
            }
          }
          // this.lbDichVuGT.list = ds_dk_dvgt
          this.HIENTHIDV_GTGT_SD(ds_dk_dvgt)
        }
        this.tien_dv = 0
        this.vat_dv = 0
        // console.log(ds_dk_dvgt);
        if (ds_dk_dvgt.length > 0) {
          for (var i = 0; i < ds_dk_dvgt.length; i++) {
            if (ds_dk_dvgt[i]['HUY'].toString() == '0' && ds_dk_dvgt[i]['DANGKY'].toString() == '0' && ds_dk_dvgt[i]['SUA'].toString() == '0') {
              ds_dk_dvgt[i]['KIEU_YC'] = 0
            }
            if (ds_dk_dvgt[i]['HUY'].toString() == '1' || ds_dk_dvgt[i]['DANGKY'].toString() == '1' || ds_dk_dvgt[i]['SUA'].toString() == '1') {
              if (ds_dk_dvgt[i]['DANGKY'].toString() == '1' && ds_dk_dvgt[i]['HUY'].toString() == '0') {
                if (ds_dk_dvgt[i]['CUOC_LD'].toString() != '') {
                  this.tien_dv = this.tien_dv + Number(ds_dk_dvgt[i]['CUOC_LD'].toString())
                }
                if (ds_dk_dvgt[i]['VAT_LD'].toString() != '') {
                  this.vat_dv = this.vat_dv + Number(ds_dk_dvgt[i]['VAT_LD'].toString())
                }
              }
            }
          }
        }
        if (this.ds_tien_khoanmuc.length > 0) {
          for (var j = 0; j < this.ds_tien_khoanmuc.length; j++) {
            if (this.ds_tien_khoanmuc[j]['KHOANMUCTT_ID'].toString() == KHOANMUC_TT.KMTT_DICHVU.toString()) {
              this.ds_tien_khoanmuc[j]['TIEN'] = this.tien_dv
              this.ds_tien_khoanmuc[j]['VAT'] = this.vat_dv
            }
          }
        }
        this.LayTongTien_HDTB()
      } catch (e) {
        console.log('HienThi_DVGT')
        console.log(e)
      }
    },
    HT_ThietBi: async function () {
      try {
        if (this.cboKieuLD.value == 13187 || this.cboKieuLD.value == 13188 || this.cboKieuLD.value == 13229 || this.cboKieuLD.value == 13238)
          // không sử dụng với loại yêu cầu hỗ trợ vì không có tiền
          return
        this.lbMuaThietBi.list = []
        this.vat_tbi = 0
        this.tien_tbi = 0
        if (this.dsCTM_TBI.length > 0) {
          for (var i = 0; i < this.dsCTM_TBI.length; i++) {
            var ds = await this.TTCT_ThietBi(
              Number(this.dsCTM_TBI[i]["loaitbi_id"])
            );
            var soluong = this.dsCTM_TBI[i]['soluong'] ? Number(this.dsCTM_TBI[i]['soluong']) : 0
            var loai_tbi = this.dsCTM_TBI[i]['loai_tbi'] ? this.dsCTM_TBI[i]['loai_tbi'] : ds[0]['loai_tbi']
            var label = loai_tbi + ' - SL: ' + soluong

            var check_valid = this.lbMuaThietBi.list.findIndex(e => e == label)
            if (check_valid > -1) continue

            this.lbMuaThietBi.list.push(label)

            if (this.dsCTM_TBI[i]['tien'] && this.dsCTM_TBI[i]['tien'].toString() != '') {
              var dtien_tbi_tmp = Number(this.dsCTM_TBI[i]['tien'])
              this.tien_tbi = this.tien_tbi + dtien_tbi_tmp * soluong
            }
            if (this.dsCTM_TBI[i]['vat'] && this.dsCTM_TBI[i]['vat'].toString() != '') {
              var dvat_tbi_tmp = Number(this.dsCTM_TBI[i]['vat'])
              this.vat_tbi = this.vat_tbi + dvat_tbi_tmp * soluong
            }
          }
        }

        if (this.ds_tien_khoanmuc.length > 0) {
          for (var j = 0; j < this.ds_tien_khoanmuc.length; j++) {
            if (this.ds_tien_khoanmuc[j]['KHOANMUCTT_ID'].toString() == '52') {
              this.ds_tien_khoanmuc[j]['TIEN'] = this.tien_tbi
              this.ds_tien_khoanmuc[j]['VAT'] = this.vat_tbi
            }
          }
        }
        this.LayTongTien_HDTB()
      } catch (e) {
        console.log('HT_ThietBi')
        console.log(e)
      }
    },
    TTCT_ThietBi: async function(loaitbi_id) {
      var input = {
        loaitbi_id: loaitbi_id
      }
      let response = await api.ttct_thietbi(this.axios, input)
      if (response && response.data && response.data.error_code == 'BSS-00000000') {
        return response.data.data
      } else {
        this.ShowError('Không lấy được DS TT thiết bị  ' + response.data.message_detail)
        return []
      }
    },
    kiemtra_hienthi_thietbi: async function(loaitbi_id) {
      var input = {
        loaitb_id: loaitbi_id
      }
      let response = await api.kiemtra_hienthi_thietbi(this.axios, input)
      if (response && response.data && response.data.error_code == 'BSS-00000000') {
        return response.data.data
      } else {
        this.ShowError('Không kiểm tra được TT thiết bị  ' + response.data.message_detail)
        return 'NOK'
      }
    },
    hienthi_dbkh: async function(ma_tb, dichvu_id) {
      try {
        var para = {
          MA_TB: ma_tb,
          DICHVUVT_ID: dichvu_id
        }
        var input = {
          ds_para: JSON.stringify(para),
          phanvung_id: this.tt_nd.phanvung_id
        }
        let response = await api.hienthittdanhbakh(this.axios, input)
        if (response && response.data && response.data.error_code == 'BSS-00000000') {
          var data = JSON.parse(response.data.data)
          if (data.ERROR_CODE == 1) {
            console.log('hienthi_tt_danhba_kh', data.RESULT);
            this.DTDS_DVGT = data.RESULT.DTDS_DVGT ? data.RESULT.DTDS_DVGT : []
            this.DT_DBTBSUB = data.RESULT.DT_DBTBSUB ? data.RESULT.DT_DBTBSUB : []
            this.DT_DIACHI_TB = data.RESULT.DT_DIACHI_TB ? data.RESULT.DT_DIACHI_TB : []
            this.DS_PHOPHUONG = data.RESULT.NAP_DS_PHOPHUONG ? data.RESULT.NAP_DS_PHOPHUONG : []
            this.LAYDANHBACT = data.RESULT.LAYDANHBACT
          } else {
            this.ShowError('Không lấy được TT danh bạ khách hàng: ' + data.MESSAGE)
            this.DTDS_DVGT = []
            this.DT_DBTBSUB = []
            this.DT_DIACHI_TB = []
            this.DS_PHOPHUONG = []
            this.LAYDANHBACT = []
          }
        } else {
          this.ShowError('Không lấy được TT danh bạ khách hàng: ' + response.data.message_detail)
          this.DTDS_DVGT = []
          this.DT_DBTBSUB = []
          this.DT_DIACHI_TB = []
          this.DS_PHOPHUONG = []
          this.LAYDANHBACT = []
        }
      } catch (ex) {
        console.log('hienthi_dbkh')
      }
    },
    hienthi_tt_hopdong: async function(tt) {
      try {
        var tmp = {
          THUEBAO_ID: tt.thuebao_id,
          LOAITB_ID: tt.loaitb_id,
          HDKH_ID: tt.hdkh_id,
          HDTB_ID: tt.hdtb_id
        }
        var input = {
          ds_para: JSON.stringify(tmp),
          phanvung_id: this.tt_nd.phanvung_id
        }

        let response = await api.hienthi_tt_hopdong(this.axios, input)
        if (response && response.data && response.data.error_code == 'BSS-00000000') {
          var data = JSON.parse(response.data.data)
          if (data.ERROR_CODE == 1) {
            console.log('hienthi_tt_hopdong', data.RESULT);
            this.DT_DBTBSUB = data.RESULT.DT_DBTBSUB ? data.RESULT.DT_DBTBSUB : []
            this.DS_PHOPHUONG = data.RESULT.DS_PHOPHUONG ? data.RESULT.DS_PHOPHUONG : []
            this.DT_DIACHI_TB = data.RESULT.DT_DIACHI_TB
            this.DSDIACHIKHACHHANG = data.RESULT.DSDIACHIKHACHHANG
            // "TRANGTHAITB_ID": 1,
            this.m_dsThueBao_DichVu = data.RESULT.DS_DK_DVGT
            this.dsCTM_TBI = data.RESULT.CTM_TBI && data.RESULT.CTM_TBI.length > 0 ? this.LowerCasePropertyList(data.RESULT.CTM_TBI) : []
            this.saveList = data.RESULT.saveList
          } else {
            this.ShowError('Không lấy được TT hợp đồng: ' + data.MESSAGE)
          }
        } else {
          this.ShowError('Không lấy được TT hợp đồng ' + response.data.message_detail)
        }
      } catch (ex) {}
    },
    DB_CNTT: async function() {
      try {
        let response = await api.DB_CNTT(this.axios, Number(this.thuebao_id))
        if (response && response.data && response.data.error_code == 'BSS-00000000') {
          if (response.data.data && response.data.data.length > 0) return this.UpperCasePropertyList(response.data.data)
          else return []
        } else if (response.data.error_code == 'BSS-00000204') {
          this.ShowError('Không có DB CNTT')
          return []
        } else {
          this.ShowError('Không lấy được  DB CNTT ' + response.data.message_detail)
          return []
        }
      } catch (ex) {
        return []
      }
    },
    Hienthi_TT_DB_Morong: async function(data) {
      try {
        this.DS = []
        this.DSKHUYENMAI = []
        this.DSTRATRUOC = []
        this.m_dsThueBao_DichVu = []
        this.DTMUCCUOCTB = []
        this.DT_LUONG = []
        this.FN_LAY_KIEULD_ElEARNING = 'NOK'
        await this.LAY_TT_DB_MORONG(data)
        if (this.DS && this.DS.length > 0) {
          var sothang = this.DS[0]['SO_THANG'] ? Number(this.DS[0]['SO_THANG']) : 0
          for (var i = 0; i < this.dycDVCNTT.length; i++) {
            if (this.dycDVCNTT[i].sFieldName == 'SOTHANG_NEW') this.dycDVCNTT[i].sValue = sothang
          }
        }
        switch (data.DICHVUVT_ID) {
          case DichVuVienThong.DICHVU_CNTT:
          case DichVuVienThong.ANTOAN_BAOMAT_TT:
          case DichVuVienThong.TRUNGTAM_DULIEU:
          case DichVuVienThong.HOINGHI_TRUYENHINH:
          case 50:
            if (this.DS && this.DS.length > 0) {
              if (this.DS[0]['MUCCUOC_ID'] && this.DS[0]['MUCCUOC_ID'].toString().trim() != '') this.vmuccuoc_id = Number(this.DS[0]['MUCCUOC_ID'].toString())
              else this.vmuccuoc_id = 0

              if (Number(this.cboKieuLD.value) == 13248 || Number(this.cboKieuLD.value) == 13177 || Number(this.cboKieuLD.value) == 13178)
                if (!this.DS[0]['NGAY_BD'] || this.DS[0]['NGAY_BD'] == null || !this.DS[0]['NGAY_KT'] || this.DS[0]['NGAY_KT'] == null || this.DS[0]['SO_THANG'] == null || (this.DS[0]['SO_THANG'] && this.DS[0]['SO_THANG'].toString().trim() == '')) {
                  this.ShowError('Không đủ thông tin để gia hạn dịch vụ (Thiếu ngày bắt đầu/ngày kết thúc/số tháng)')
                  this.THAOTAC_NUT(1)
                  this.XOA_FORM()
                  return
                }
            }
            break
        }
        switch (data.DICHVUVT_ID) {
          case DichVuVienThong.DICHVU_CNTT:
          case DichVuVienThong.TRUNGTAM_DULIEU:
          case DichVuVienThong.ANTOAN_BAOMAT_TT:
          case DichVuVienThong.HOINGHI_TRUYENHINH:
            if (this.DS && this.DS.length > 0) {
              var dycDVCNTT = this.dycDVCNTT
              var keys = Object.keys(this.DS[0])
              // Hiển thị thông tin ra
              for (var i = 0; i < dycDVCNTT.length; i++) {
                var item_control = dycDVCNTT[i]
                if (item_control.sFieldName == 'MA_TB') {
                  item_control.sValue = this.txtMaTB.value
                }
                for (var j = 0; j < keys.length; j++) {
                  if (item_control.sFieldName == keys[j]) {
                    if (keys[j].includes('NGAY') || keys[j].includes('THOIGIAN')) {
                      item_control.sValue = this.DS[0][keys[j]] ? moment(this.DS[0][keys[j]]).format('DD/MM/YYYY HH:mm:ss') : ''
                    } else {
                      if (item_control.iAtt == 10) {
                        item_control.sValue = this.DS[0][keys[j]] && this.DS[0][keys[j]].length > 0 ? this.DS[0][keys[j]].split(',').map((e) => Number(e)) : []
                      } else {
                        item_control.sValue = this.DS[0][keys[j]]
                        if (item_control.sReadOnly == true && item_control.bIsNotNull == true && !item_control.sValue)
                        {
                          this.$toast.error("Thiếu dữ liệu " + item_control.sCaption);
                          item_control.sReadOnly = false;
                        }
                      }
                    }
                  }
                }
                dycDVCNTT[i] = item_control
              }
              if (this.DS[0]['TOCDO_ID'] && this.DS[0]['TOCDO_ID'].toString().trim() != '') {
                this.tocdo_id = Number(this.DS[0]['TOCDO_ID'])
              } else this.tocdo_id = 0
              if (this.DS[0]['MUCCUOC_ID'] && this.DS[0]['MUCCUOC_ID'].toString().trim() != '') this.muccuoc_id = Number(this.DS[0]['MUCCUOC_ID'])
              else this.muccuoc_id = 0

              // Hiển thị mức cước _TB
              this.cboMucCuocTB.value = data.MUCCUOCTB_ID

              this.HT_DSTRATRUOC_Combobox(this.DSKHUYENMAI, this.DSTRATRUOC)

              this.vquytrinh_id = Number(this.DT_LUONG[0]['QUYTRINH_ID'])
              this.vhuonggiao_id = Number(this.DT_LUONG[0]['HUONGGIAO_ID'])

              // Kiểm tra kiểu lắp đặt của Elearnin, Nếu kiểu lắp đặt là : Chuyển dùng chính thức sẽ gán ngày bđ = ngày hiện tại
              if (Number(this.cboLoaiHinhTB.value) == LoaiHinhTB.ELEARNING) {
                if (this.FN_LAY_KIEULD_ElEARNING == 'OK') {
                  var filter_ngaybd = dycDVCNTT.filter((e) => e.sFieldName == 'NGAY_BD')
                  var filter_ngaykt = dycDVCNTT.filter((e) => e.sFieldName == 'NGAY_KT')
                  if (filter_ngaybd.length > 0 && filter_ngaykt.length > 0) {
                    var sothang = this.DS[0]['SO_THANG'] ? Number(this.DS[0]['SO_THANG']) : 0
                    var today = new Date()
                    for (var i = 0; i < dycDVCNTT.length; i++) {
                      if (dycDVCNTT[i].sFieldName == 'NGAY_BD') dycDVCNTT[i].sValue = moment(today).format('DD/MM/YYYY HH:mm:ss')
                      if (dycDVCNTT[i].sFieldName == 'NGAY_KT') {
                        var ngay_kt = today.setMonth(today.getMonth() + sothang)
                        dycDVCNTT[i].sValue = moment(ngay_kt).format('DD/MM/YYYY HH:mm:ss')
                      }
                    }
                  }
                }
              }

              // Chuyển trường sl_mailing của dịch vụ VNPT MSS = 0 khi tăng giản lisence
              if (Number(this.cboLoaiHinhTB.value) == LoaiHinhTB.VNPT_MSS || Number(this.cboLoaiHinhTB.value) == LoaiHinhTB.HocBaDienTu) {
                for (var i = 0; i < dycDVCNTT.length; i++) {
                  var row = dycDVCNTT[i]
                  if (row.sFieldName == 'SL_MAILING') {
                    if (Number(this.cboKieuLD.value) == 14011 || Number(this.cboKieuLD.value) == 14012) {
                      row.sValue = 0
                    }
                  }
                }
              }

              if (Number(this.cboKieuLD.value) == 13248 || Number(this.cboKieuLD.value) == 13177) {
                var filter_ngaybd = dycDVCNTT.filter((e) => e.sFieldName == 'NGAY_BD')
                var filter_ngaykt = dycDVCNTT.filter((e) => e.sFieldName == 'NGAY_KT')
                if (filter_ngaybd.length > 0 && filter_ngaykt.length > 0) {
                  for (var i = 0; i < dycDVCNTT.length; i++) {
                    if (dycDVCNTT[i].sFieldName == 'NGAYBD_NEW') dycDVCNTT[i].sValue = filter_ngaybd[0].sValue ? filter_ngaybd[0].sValue : ''
                    if (dycDVCNTT[i].sFieldName == 'NGAYKT_NEW') dycDVCNTT[i].sValue = filter_ngaykt[0].sValue ? filter_ngaykt[0].sValue : ''
                  }
                }
              }

              for (var i = 0; i < dycDVCNTT.length; i++) {
                var row = dycDVCNTT[i]
                if (row.sFieldName == 'TOCDO_ID') {
                  row.sValue = this.tocdo_id
                }
              }
              this.dycDVCNTT = dycDVCNTT
            } else {
              this.dycDVCNTT = []
            }
            break

          case 50: //Dich vu CNTT
            if (this.DS && this.DS.length > 0) {
              if (this.DS[0]['LINHVUC_ID'] && this.DS[0]['LINHVUC_ID'].toString().trim() != '') this.linhvuc_id = Number(this.DS[0]['LINHVUC_ID'].toString().trim())
            } else this.linhvuc_id = 0
            this.tocdo_id = 0
            break
        }
      } catch (e) {
        console.log('Hienthi_TT_DB_Morong')
        console.log(e)
      }
    },
    LoadThongTin_DV_CNTT: async function(vloaitb_id) {
      try {
        this.dycDVCNTT = []
        var ds_init = await this.lay_ds_thuoctinh_giahan_cntt(vloaitb_id, 'HDTB_CNTT', '00000')
        if (ds_init.length > 0) {
          var dlControl = this.DuLieuControlDong(ds_init)
          if (Number(this.cboKieuLD.value) == 13248 || Number(this.cboKieuLD.value) == 13177 || Number(this.cboKieuLD.value) == 13282 || Number(this.cboKieuLD.value) == 14444) {
            var ngaybd_new = {
              sFieldName: 'NGAYBD_NEW',
              sCaption: 'Ngày bắt đầu (gốc)',
              iAtt: 2,
              iDataType: 2,
              sMaxValue: '',
              sMinvalue: '',
              sValue: '',
              nFieldLength: '',
              bIsNotNull: true,
              bIsUnique: false,
              sLookUpTableName: '',
              sLookUpKeyField: '',
              sLookUpValueField: '',
              strDepandField: '',
              sLookupApi: '',
              sAPIHienThiBang: '',
              sAPIDisplayMember: '',
              sAPIValueMember: '',
              sAPIMethod: '',
              sReadOnly: false,
              StrSql: '',
              dataSource: ''
            }
            var ngaykt_new = {
              sFieldName: 'NGAYKT_NEW',
              sCaption: 'Ngày kết thúc mới',
              iAtt: 2,
              iDataType: 2,
              sMaxValue: '',
              sMinvalue: '',
              sValue: '',
              nFieldLength: '',
              bIsNotNull: true,
              bIsUnique: false,
              sLookUpTableName: '',
              sLookUpKeyField: '',
              sLookUpValueField: '',
              strDepandField: '',
              sLookupApi: '',
              sAPIHienThiBang: '',
              sAPIDisplayMember: '',
              sAPIValueMember: '',
              sAPIMethod: '',
              sReadOnly: false,
              StrSql: '',
              dataSource: ''
            }
            var SoThang_New = {
              sFieldName: 'SOTHANG_NEW',
              sCaption: 'Số tháng gia hạn(*)',
              iAtt: 0,
              iDataType: 1,
              sMaxValue: '',
              sMinvalue: '',
              sValue: '0',
              nFieldLength: '',
              bIsNotNull: true,
              bIsUnique: false,
              sLookUpTableName: '',
              sLookUpKeyField: '',
              sLookUpValueField: '',
              strDepandField: '',
              sLookupApi: '',
              sAPIHienThiBang: '',
              sAPIDisplayMember: '',
              sAPIValueMember: '',
              sAPIMethod: '',
              sReadOnly: false,
              StrSql: '',
              dataSource: ''
            }
            dlControl.push(ngaybd_new)
            dlControl.push(ngaykt_new)
            dlControl.push(SoThang_New)
          }
          const kieuld_id = Number(this.cboKieuLD.value);
          const loaitb_id = Number(this.cboLoaiHinhTB.value);
          if ((kieuld_id === 19585  || kieuld_id == 19587) && loaitb_id === 358/*dang ky them ngoai mang Viettel, Mobi*/) {
            const soluong_mobi = {
              sFieldName: 'LINHVUC_VMS_ID',
              sCaption: 'Số lượng VBR ngoại mạng MobiFone (*)',
              iAtt: 0,
              iDataType: 1,
              sMaxValue: '',
              sMinvalue: '',
              sValue: '0',
              nFieldLength: '',
              bIsNotNull: true,
              bIsUnique: false,
              sLookUpTableName: '',
              sLookUpKeyField: '',
              sLookUpValueField: '',
              strDepandField: '',
              sLookupApi: '',
              sAPIHienThiBang: '',
              sAPIDisplayMember: '',
              sAPIValueMember: '',
              sAPIMethod: '',
              sReadOnly: false,
              StrSql: '',
              dataSource: ''
            }

            const soluong_viettel =
            {
              sFieldName: 'LINHVUC_VTL_ID',
              sCaption: 'Số lượng VBR ngoại mạng Viettel (*)',
              iAtt: 0,
              iDataType: 1,
              sMaxValue: '',
              sMinvalue: '',
              sValue: '0',
              nFieldLength: '',
              bIsNotNull: true,
              bIsUnique: false,
              sLookUpTableName: '',
              sLookUpKeyField: '',
              sLookUpValueField: '',
              strDepandField: '',
              sLookupApi: '',
              sAPIHienThiBang: '',
              sAPIDisplayMember: '',
              sAPIValueMember: '',
              sAPIMethod: '',
              sReadOnly: false,
              StrSql: '',
              dataSource: ''
            }
            dlControl.push(soluong_mobi);
            dlControl.push(soluong_viettel);
          }

          if (Number(this.cboKieuLD.value) == 13248 || Number(this.cboKieuLD.value) == 13177 || Number(this.cboKieuLD.value) == 13282 || Number(this.cboKieuLD.value) == 14444) {
            if (Number(this.cboLoaiHinhTB.value) == LoaiHinhTB.CA) {
              this.txtTenKH.ReadOnly = false
              this.txtTenTB.ReadOnly = false
              this.button.btnThayDoiDiaChiTB.Enabled = true
            }
          } else {
            if (Number(this.cboKieuLD.value) == 13178 || Number(this.cboKieuLD.value) == 13179 || Number(this.cboKieuLD.value) == 13261) {
              var allowInputControlName = ['SO_DT', 'EMAIL']
              for (var i = 0; i < dlControl.length; i++) {
                for (var j = 0; j < allowInputControlName.length; j++) {
                  if (!allowInputControlName[j].includes(dlControl[i].sFieldName)) {
                    dlControl[i].sReadOnly = true
                  }
                }
              }
              this.txtTenKH.ReadOnly = false
              this.txtTenTB.ReadOnly = false
              this.button.btnThayDoiDiaChiTB.Enabled = true
            }
          }

          var dt_kieuld_tscn = await this.kieuld_tscn(Number(this.cboKieuLD.value), Number(this.cboLoaiHinhTB.value))
          if (dt_kieuld_tscn.length > 0) {
            for (var i = 0; i < dlControl.length; i++) {
              var filter = dt_kieuld_tscn.filter((item) => item.FIELD_NAME == dlControl[i].sFieldName)
              if (filter.length > 0) {
                dlControl[i].sReadOnly = filter[0].READONLY == 0 ? false : true
                dlControl[i].bIsNotNull = filter[0].NULLABLE == 0 ? true : false
              }
            }
          } else {
            for (var i = 0; i < dlControl.length; i++) {
              dlControl[i].sReadOnly = true
            }
          }
          for (var i = 0; i < dlControl.length; i++) {
            if ((Number(this.cboKieuLD.value) == 13248 || Number(this.cboKieuLD.value) == 13177 || Number(this.cboKieuLD.value) == 13282 || Number(this.cboKieuLD.value) == 14444) && dlControl[i].sFieldName == 'SOTHANG_NEW') {
              dlControl[i].sReadOnly = false
            }
          }

          this.dycDVCNTT = dlControl
        }
      } catch (ex) {
        console.log('LoadThongTin_DV_CNTT')
        console.log(ex)
      }
    },
    lay_ds_thuoctinh_giahan_cntt: async function(vloaitb_id, table_name, opt_list) {
      var input = {
        vloaitb_id: vloaitb_id,
        vten_bang: table_name,
        vopt_list: opt_list
      }
      let response = await api.lay_ds_thuoctinh_giahan_cntt(this.axios, input)
      if (response && response.data && response.data.error_code == 'BSS-00000000') {
        return response.data.data
      } else {
        return []
      }
    },
    Init_Properties: async function(dt) {
      var ds_para = {
        LOAITB_ID: dt.LOAITB_ID,
        FIELDNAME: dt.FIELDNAME,
        TENBANG: dt.TENBANG,
        LOAIHD_ID: dt.LOAIHD_ID,
        KIEULD_ID: dt.KIEULD_ID,
        PHANLOAI_ID: dt.PHANLOAI_ID,
        ITEM_LIST: dt.ITEM_LIST
      }
      var input = {
        ds_para: JSON.stringify(ds_para),
        phanvung_id: this.tt_nd.phanvung_id
      }

      let response = await api.fn_init_properties_cntt(this.axios, input)
      if (response && response.data && response.data.error_code == 'BSS-00000000') {
        var data = JSON.parse(response.data.data)
        if (data.ERROR_CODE == 1) {
          if (data.RESULT && data.RESULT.length > 0) {
            var dataSource = data.RESULT
            // console.log(data.RESULT);
            var dt = []
            for (var i = 0; i < dataSource.length; i++) {
              var keys = Object.keys(dataSource[i])
              if (keys.length == 2) {
                var id = keys.filter((e) => e.includes('ID'))[0]
                var text = keys.filter((e) => !e.includes('ID'))[0]
                var row = {
                  id: dataSource[i][id],
                  text: dataSource[i][text]
                }
                dt.push(row)
              }
            }
            return dt
          } else return []
        } else {
          return []
        }
      } else {
        return []
      }
    },
    kieuld_tscn: async function(kieuld_id, loaitb_id) {
      var input = {
        kieuld_id: kieuld_id,
        loaitb_id: loaitb_id
      }

      let response = await api.kieuld_tscn(this.axios, input)
      if (response && response.data && response.data.error_code == 'BSS-00000000') {
        return this.UpperCasePropertyList(response.data.data)
      } else {
        return []
      }
    },
    txtSOTHANG_NEW_Changed() {
      if (Number(this.cboKieuLD.value) == 13248 || Number(this.cboKieuLD.value) == 13177) {
        var dycDVCNTT = this.dycDVCNTT
        var filter_ngaybd_new = dycDVCNTT.filter((e) => e.sFieldName == 'NGAYBD_NEW')
        var filter_ngaykt_new = dycDVCNTT.filter((e) => e.sFieldName == 'NGAYKT_NEW')
        if (filter_ngaybd_new.length == 0 || filter_ngaykt_new.length == 0 || !filter_ngaybd_new[0].sValue || !filter_ngaykt_new[0].sValue) return
        var sothang = 0
        var filter_sothangnew = dycDVCNTT.filter((e) => e.sFieldName == 'SOTHANG_NEW')
        var filter_so_thang_gh = dycDVCNTT.filter((e) => e.sFieldName == 'SO_THANG')
        var sothang_new = filter_sothangnew.length > 0 ? Number(filter_sothangnew[0].sValue) : 0
        var so_thang_gh = filter_so_thang_gh.length > 0 ? Number(filter_so_thang_gh[0].sValue) : 0
        if (!this.button.btnNhapMoi.Enabled) {
          sothang = Number(sothang_new) + Number(so_thang_gh)
        } else {
          var filter_ngaykt = dycDVCNTT.filter((e) => e.sFieldName == 'NGAY_KT')
          var filter_ngaybd = dycDVCNTT.filter((e) => e.sFieldName == 'NGAY_BD')
          var d_kt = filter_ngaykt.length ? filter_ngaykt[0].sValue : ''
          var d_bd = filter_ngaybd.length ? filter_ngaybd[0].sValue : ''
          var d_kt_year = 0
          var d_kt_month = 0
          var d_bd_year = 0
          var d_bd_month = 0
          if (d_kt) {
            d_kt_year = Number(moment(d_kt, 'DD/MM/YYYY HH:mm:ss').format('YYYY'))
            d_kt_month = Number(moment(d_kt, 'DD/MM/YYYY HH:mm:ss').format('MM'))
          }
          if (d_bd) {
            d_bd_year = Number(moment(d_bd, 'DD/MM/YYYY HH:mm:ss').format('YYYY'))
            d_bd_month = Number(moment(d_bd, 'DD/MM/YYYY HH:mm:ss').format('MM'))
          }
          var sothang_cu = (d_kt_year - d_bd_year) * 12 + d_kt_month - d_bd_month - so_thang_gh
          sothang = sothang_new + sothang_cu
        }
        for (var i = 0; i < dycDVCNTT.length; i++) {
          if (dycDVCNTT[i].sFieldName == 'NGAYKT_NEW') {
            var filter_ngaybdnew = dycDVCNTT.filter((e) => e.sFieldName == 'NGAYBD_NEW')
            var ngaybd_new = new Date(moment(filter_ngaybdnew[0].sValue, 'DD/MM/YYYY HH:mm:ss'))
            var ngaykt_new = ngaybd_new.setMonth(ngaybd_new.getMonth() + sothang)
            dycDVCNTT[i].sValue = moment(ngaykt_new).format('DD/MM/YYYY HH:mm:ss')
          }
        }
        this.dycDVCNTT = dycDVCNTT
      }
    },
    async txtPHANLOAI_ID_changed(val) {
      // if (
      //   Number(this.cboLoaiHinhTB.value) == LoaiHinhTB.VNPT_COLOCATION ||
      //   Number(this.cboLoaiHinhTB.value) == LoaiHinhTB.DEDICATED_SERVER ||
      //   Number(this.cboLoaiHinhTB.value) == LoaiHinhTB.SSL ||
      //   Number(this.cboLoaiHinhTB.value) == LoaiHinhTB.BIENLAIDT || // Bien lai dien tu
      //   Number(this.cboLoaiHinhTB.value) == LoaiHinhTB.ORM || // ORM
      //   Number(this.cboLoaiHinhTB.value) == LoaiHinhTB.VXP || // VXP
      //   Number(this.cboLoaiHinhTB.value) == LoaiHinhTB.ELEARNING || // elearning
      //   Number(this.cboLoaiHinhTB.value) == LoaiHinhTB.ECABINET ||
      //   Number(this.cboLoaiHinhTB.value) == LoaiHinhTB.VNPT_HIS
      // ) {
      //   // var check_combobox = this.dycDVCNTT.filter((e) => e.iAtt == 4 && e.sFieldName == "PHANLOAI_ID");
      //   // if (check_combobox && check_combobox.length > 0) await this.txtTOCDO_ID_Changed(val)
      // }
      // debugger
      var thuoctinh_nhamang = {
        LINHVUC_ID: 1,
        LINHVUC_VMS_ID: 2,
        LINHVUC_VTL_ID: 3,
        LINHVUC_GTEL_ID: 4,
        LINHVUC_VNM_ID: 5,
        LINHVUC_ITEL_ID: 7,
        LINHVUC_MOBICAST_ID: 8
      }

      var check_combobox = this.dycDVCNTT.filter((e) => e.iAtt == 4 && e.sFieldName == 'PHANLOAI_ID')
      if (Number(this.cboLoaiHinhTB.value) == 131) {
        for (var i = 0; i < this.dycDVCNTT.length; i++) {
          if (this.dycDVCNTT[i].sFieldName.indexOf('LINHVUC') == 0) {
            if (this.dycDVCNTT[i].dataSource) {
              var dataSource = this.dycDVCNTT[i].dataSource.filter((x) => x.addon['PHANLOAI_ID'] == check_combobox[0].sValue)

              var json_fn_kiemtra_dangky_nhamang_brn = JSON.stringify({
                vthuebao_id: this.thuebao_id,
                vtelco_id: thuoctinh_nhamang[this.dycDVCNTT[i].sFieldName]
              })

              const files_fn_kiemtra_dangky_nhamang_brn = await this.axios.post('/web-quantri/sms-brandname/fn_kiemtra_dangky_nhamang_brn', json_fn_kiemtra_dangky_nhamang_brn, {
                headers: {
                  'Content-Type': 'application/json'
                }
              })
              var khoa_mo = 'OK'
              //debugger
              if (files_fn_kiemtra_dangky_nhamang_brn.data.data) {
                khoa_mo = files_fn_kiemtra_dangky_nhamang_brn.data.data
                //  debugger
              }
              if (Number(this.cboKieuLD.value) == 840) {
                dataSource.push({ id: -1, text: 'Hủy nhà mạng gửi tin' })
                if (khoa_mo == 'OK') this.dycDVCNTT[i].sReadOnly = false
                else this.dycDVCNTT[i].sReadOnly = true
              } else if (Number(this.cboKieuLD.value) == 797) {
                if (khoa_mo == 'OK') this.dycDVCNTT[i].sReadOnly = true
                else this.dycDVCNTT[i].sReadOnly = false
              }
              this.dycDVCNTT[i].dataSource = dataSource
            }
          }
        }
        // if (cboKieuLD.EditValue.ToString() == "797" || cboKieuLD.EditValue.ToString() == "840") {
        //   var cbo = ((GridLookUpEdit)item);
        //   if (cbo.Name.Contains("LINHVUC_ID")) {
        //                                 string khoa_mo = tdan.GET_VALUE_FUNC("{?DB2}.fn_kiemtra_dangky_nhamang_brn", "vthuebao_id", thuebao_id, "vtelco_id", 1).ToString();
        //     if (khoa_mo == "OK")
        //       cbo.Enabled = false;
        //     else
        //       cbo.Enabled = true;
        //   }
        // }
      }
    },
    async txtLABEL_ID_changed(val) {
      var check_combobox = this.dycDVCNTT.filter((e) => e.iAtt == 4 && e.sFieldName == 'PHANLOAI_ID')
      if (Number(this.cboLoaiHinhTB.value) == 131) {
        let label_id = val.target.value
        let phanloai_id = check_combobox[0].sValue
        var vphanvung_id = Number(this.$root.token.getPhanVungID())
        let response = await this.axios.post(`/tichhop/brandname/load_thongtin_brandname`, { vphanvung_id, vlabel_id: label_id + '', vphanloai_id: phanloai_id + '' })
        if (response.data.data && response.data.data.status == 200) {
          let columns_brn = ['LABEL_ID', 'AGENT_ID', 'CONTRACT_ID', 'LINHVUC_ID', 'LINHVUC_VMS_ID', 'LINHVUC_VTL_ID', 'LINHVUC_GTEL_ID', 'LINHVUC_VNM_ID', 'LINHVUC_ITEL_ID', 'LINHVUC_MOBICAST_ID']
          var label_data = response.data.data.data.items[0]
          for (var i = this.dycDVCNTT.length - 1; i >= 0; i--) {
            if (columns_brn.includes(this.dycDVCNTT[i].sFieldName)) {
              this.dycDVCNTT[i].sValue = label_data[this.dycDVCNTT[i].sFieldName.toLowerCase()]
            } else if (this.dycDVCNTT[i].sFieldName == 'THUONGHIEU') {
              this.dycDVCNTT[i].sValue = label_data['label']
            } else if (this.dycDVCNTT[i].sFieldName == 'NGAY_BD') {
              this.dycDVCNTT[i].sValue = label_data['created_date']
            } else if (this.dycDVCNTT[i].sFieldName == 'MST') {
              this.dycDVCNTT[i].sValue = label_data['tax_code']
            } else if (this.dycDVCNTT[i].sFieldName == 'ADSER_ID') {
              this.dycDVCNTT[i].sValue = '0'
            }
          }
        } else {
          this.$toast.error(response.data.data.message)
          return
        }

        // if (cboKieuLD.EditValue.ToString() == "797" || cboKieuLD.EditValue.ToString() == "840") {
        //   var cbo = ((GridLookUpEdit)item);
        //   if (cbo.Name.Contains("LINHVUC_ID")) {
        //                                 string khoa_mo = tdan.GET_VALUE_FUNC("{?DB2}.fn_kiemtra_dangky_nhamang_brn", "vthuebao_id", thuebao_id, "vtelco_id", 1).ToString();
        //     if (khoa_mo == "OK")
        //       cbo.Enabled = false;
        //     else
        //       cbo.Enabled = true;
        //   }
        // }
      }
    },
    txtDAILY_Changed: async function(value) {
      // var check_combobox = this.dycDVCNTT.filter((e) => e.iAtt == 4 && e.sFieldName == "DAILY");
      // if (check_combobox && check_combobox.length > 0) await this.txtTOCDO_ID_Changed(value)
    },
    txtTONGSO_ND_changed(value) {
      if (value && value > 0) {
        if (Number(this.cboKieuLD.value) == 13189) {
          if (Number(this.cboLoaiHinhTB.value) == LoaiHinhTB.VNPT_HIS) {
            this._sl_cha = Number(value)
          }
        }
      }
    },
    txtNGAYBD_changed() {
      if (Number(this.cboKieuLD.value) == 13189) {
        var dycDVCNTT = this.dycDVCNTT
        var filter_sothang = dycDVCNTT.filter((e) => e.sFieldName == 'SO_THANG')
        var filter_ngaykt = dycDVCNTT.filter((e) => e.sFieldName == 'NGAY_KT')
        if (filter_sothang.length > 0 && filter_ngaykt.length > 0) {
          var filter_ngaybd = dycDVCNTT.filter((e) => e.sFieldName == 'NGAY_BD')
          var d_bd = filter_ngaybd.length ? filter_ngaybd[0].sValue : ''
          var so_thang_bd = d_bd ? (new Date(moment(d_bd, 'DD/MM/YYYY HH:mm:ss'))).getMonth() : 0
          var so_thang = filter_sothang.length > 0 ? Number(filter_sothang[0].sValue) : 0
          var ngay_kt = moment(new Date(moment(d_bd, 'DD/MM/YYYY HH:mm:ss')).setMonth(so_thang_bd + so_thang)).format('DD/MM/YYYY HH:mm:ss')
          for (var i = 0; i < this.dycDVCNTT.length; i++) {
            if (this.dycDVCNTT[i].sFieldName == 'NGAY_KT') this.dycDVCNTT[i].sValue = ngay_kt
          }
        }
      }
    },
    txtNGAYKT_changed() {
      if (Number(this.cboKieuLD.value) == 13189) {
        var dycDVCNTT = this.dycDVCNTT
        var filter_sothang = dycDVCNTT.filter((e) => e.sFieldName == 'SO_THANG')
        var filter_ngaykt = dycDVCNTT.filter((e) => e.sFieldName == 'NGAY_KT')
        if (filter_sothang.length > 0 && filter_ngaykt.length > 0) {
          var filter_ngaybd = dycDVCNTT.filter((e) => e.sFieldName == 'NGAY_BD')
          var d_bd = filter_ngaybd.length ? filter_ngaybd[0].sValue : ''
          var so_thang_bd = d_bd ? (new Date(moment(d_bd, 'DD/MM/YYYY HH:mm:ss'))).getMonth() : 0
          var so_thang = filter_sothang.length > 0 ? Number(filter_sothang[0].sValue) : 0
          var ngay_kt = moment(new Date(moment(d_bd, 'DD/MM/YYYY HH:mm:ss')).setMonth(so_thang_bd + so_thang)).format('DD/MM/YYYY HH:mm:ss')
          for (var i = 0; i < this.dycDVCNTT.length; i++) {
            if (this.dycDVCNTT[i].sFieldName == 'NGAY_KT') this.dycDVCNTT[i].sValue = ngay_kt
          }
        }
      }
    },
    txtSOTHANG_Changed() {
      if (Number(this.cboKieuLD.value) == 13189) {
        var dycDVCNTT = this.dycDVCNTT
        var filter_sothang = dycDVCNTT.filter((e) => e.sFieldName == 'SO_THANG')
        var filter_ngaykt = dycDVCNTT.filter((e) => e.sFieldName == 'NGAY_KT')
        if (filter_sothang.length > 0 && filter_ngaykt.length > 0) {
          var filter_ngaybd = dycDVCNTT.filter((e) => e.sFieldName == 'NGAY_BD')
          var d_bd = filter_ngaybd.length ? filter_ngaybd[0].sValue : ''
          var so_thang_bd = d_bd ? (new Date(moment(d_bd, 'DD/MM/YYYY HH:mm:ss'))).getMonth() : 0
          var so_thang = filter_sothang.length > 0 ? Number(filter_sothang[0].sValue) : 0
          var ngay_kt = moment(new Date(moment(d_bd, 'DD/MM/YYYY HH:mm:ss')).setMonth(so_thang_bd + so_thang)).format('DD/MM/YYYY HH:mm:ss')
          for (var i = 0; i < this.dycDVCNTT.length; i++) {
            if (this.dycDVCNTT[i].sFieldName == 'NGAY_KT') this.dycDVCNTT[i].sValue = ngay_kt
          }
        }
      }
    },
    async txtTOCDO_ID_Changed(value) {
      console.log(123456)
      await this.SP_LAY_DS_DL_NGHIEPVU()
      if (this.DS_KIEULD_THAYDOI_GOICUOC && this.DS_KIEULD_THAYDOI_GOICUOC.length > 0) {
        for (var i = 0; i < this.DS_KIEULD_THAYDOI_GOICUOC.length; i++) {
          if (Number(this.cboKieuLD.value) == Number(this.DS_KIEULD_THAYDOI_GOICUOC[i].KIEULD_ID) && this.tocdo_id != 0) {
            var tocdo_id = 0
            if (value) tocdo_id = Number(value)
            this.tocdo_id = tocdo_id
            await this.txtTocdoIDChanged(tocdo_id)
            // Hiển thị lại số tháng lấy ra từ DB
            for (var i = 0; i < this.dycDVCNTT.length; i++) {
                if (this.dycDVCNTT[i].sFieldName == 'SO_THANG') {
                  this.dycDVCNTT[i].sValue = this.SOTHANG_TRONG_DB
                  break
                }
              }
            // Hiển thị ngày BD , Ngày KT , Số Tháng theo cài đặt FN_TAO_NGAYBD_NGAYKT_KHI_GIAHAN
            if(this.FN_TAO_NGAYBD_NGAYKT_KHI_GIAHAN == 'OK')
            {
              for (var i = 0; i < this.dycDVCNTT.length; i++) {
                if (this.dycDVCNTT[i].sFieldName == 'NGAY_KT') {
                  this.dycDVCNTT[i].sValue = moment(today).format('DD/MM/YYYY HH:mm:ss')
                  break
                }
              }
              let sothang_db = 0
              var sothang_data = this.dycDVCNTT.filter((e) => e.sFieldName == 'SO_THANG')
              if (sothang_data.length > 0)
                sothang_db =  Number(sothang_data[0].sValue)
              for (var i = 0; i < this.dycDVCNTT.length; i++) {
                if (this.dycDVCNTT[i].sFieldName == 'NGAY_BD') {
                  //console.log(sothang_db)
                  console.log(moment().subtract(sothang_db, 'months').format('DD/MM/YYYY HH:mm:ss'))
                  this.dycDVCNTT[i].sValue = moment().subtract(sothang_db, 'months').format('DD/MM/YYYY HH:mm:ss')
                  break
                }
              }
              for (var i = 0; i < this.dycDVCNTT.length; i++) {
                if (this.dycDVCNTT[i].sFieldName == 'NGAYBD_NEW') {
                  this.dycDVCNTT[i].sValue = moment().subtract(sothang_db, 'months').format('DD/MM/YYYY HH:mm:ss')
                  break
                }
              }
            }
            // Hiển thị số tháng mới, theo trường số tháng trong tocdo_adsl cài đặt trong TUDONG_TINHSOTHANG_SD_THEO_GOICUOC
            if (this.TUDONG_TINHSOTHANG_SD_THEO_GOICUOC == 'OK') {
              for (var i = 0; i < this.dycDVCNTT.length; i++) {
                var today = new Date()
                if (this.dycDVCNTT[i].sFieldName == 'SOTHANG_NEW') {
                  this.dycDVCNTT[i].sValue = this.SOTHANG_SD
                }
              }
            }

            if (Number(this.cboLoaiHinhTB.value) != 295) {
              for (var i = 0; i < this.dycDVCNTT.length; i++) {
                if (this.dycDVCNTT[i].sFieldName == 'SL_MAILING' && this.SL > 0) {
                  this.dycDVCNTT[i].sValue = this.SL
                }
              }
            }
          }
        }
      }
      if (Number(this.cboKieuLD.value) == 13189 ) {
        //chuyển chính thức
        if (this.FN_KIEMTRA_HIENTHI_LOAITB_NGAYBD_SYSTEMDATE == 'OK') {
          var today = new Date()
          for (var i = 0; i < this.dycDVCNTT.length; i++) {
            if (this.dycDVCNTT[i].sFieldName == 'NGAY_BD') {
              //debugger
              this.dycDVCNTT[i].sValue = moment(today).format('DD/MM/YYYY HH:mm:ss')
            }

            if (this.TUDONG_TINHSOTHANG_SD_THEO_GOICUOC == 'OK') {
                if (this.dycDVCNTT[i].sFieldName == 'SO_THANG') {
                this.dycDVCNTT[i].sValue = this.SOTHANG_SD
               }
            }
            //  if (this.dycDVCNTT[i].sFieldName == 'SO_THANG') {
            //    this.dycDVCNTT[i].sValue = this.SO_THANG
            // }
            if (this.dycDVCNTT[i].sFieldName == 'NGAY_KT') {
              this.dycDVCNTT[i].sValue = moment(today.setMonth(today.getMonth() + this.SOTHANG_SD)).format('DD/MM/YYYY HH:mm:ss')
            }
          }
        }
      }

    },
    SP_LAY_DS_DL_NGHIEPVU: async function() {
      try {
        this.DS_KIEULD_THAYDOI_GOICUOC = []
        var input = {
          p_nghiepvu: 'DS_KIEULD_THAYDOI_GOICUOC',
          p_ds_para: ''
        }
        let response = await api.SP_LAY_DS_DL_NGHIEPVU(this.axios, input)
        if (response && response.data && response.data.error_code == 'BSS-00000000' && response.data.data && response.data.data.length > 0) {
          this.DS_KIEULD_THAYDOI_GOICUOC = this.UpperCasePropertyList(response.data.data)
        } else {
          this.ShowError('Lỗi lấy danh sách kiểu lắp đặt gia hạn và thay đổi gói cước CNTT ' + response.data.message)
        }
      } catch (e) {
        this.ShowError('SP_LAY_DS_DL_NGHIEPVU' + e)
      }
    },
    txtTocdoIDChanged: async function(tocdo_id) {
      try {
        var para = {
          THUEBAO_ID: this.thuebao_id,
          LOAITB_ID: Number(this.cboLoaiHinhTB.value),
          HDKH_ID: this.hdkh_id,
          HDTB_ID: this.hdtb_id,
          DICHVUVT_ID: Number(this.cboDichVuVT.value),
          KIEULD_ID: Number(this.cboKieuLD.value),
          PHUONGLD_ID: this.phuongld_id,
          PHOLD_ID: this.phold_id,
          APLD_ID: this.apld_id,
          KHULD_ID: this.khuld_id,
          DOITUONG_ID: this.vdoituong_id,
          TRANGBI_ID: this.trangbi_id,
          TOCDO_ID: tocdo_id,
          MUCCUOC_ID: this.vmuccuoc_id
        }
        var input = {
          phanvung_id: this.tt_nd.phanvung_id,
          ds_para: JSON.stringify(para)
        }
        let response = await api.txttocdo_id_changed(this.axios, input)
        if (response && response.data && response.data.error_code == 'BSS-00000000') {
          var data = JSON.parse(response.data.data)
          if (data.ERROR_CODE == 1) {
            // console.log(data.RESULT);
            this.DTMUCCUOCTB = data.RESULT.DTMUCCUOCTB && data.RESULT.DTMUCCUOCTB.length > 0 ? data.RESULT.DTMUCCUOCTB : []
            this.SO_THANG = data.RESULT.SO_THANG ? Number(data.RESULT.SO_THANG) : 0
            this.SOTHANG_SD = data.RESULT.SOTHANG_SD ? Number(data.RESULT.SOTHANG_SD) : 0
            this.SL = data.RESULT.SL ? Number(data.RESULT.SL) : 0
            this.SOTHANG_TRONG_DB = data.RESULT.SOTHANG_TRONG_DB ? Number(data.RESULT.SOTHANG_TRONG_DB) : 0
            this.FN_KIEMTRA_HIENTHI_THIETBI_THEO_LOAITB = data.RESULT.FN_KIEMTRA_HIENTHI_THIETBI_THEO_LOAITB ? data.RESULT.FN_KIEMTRA_HIENTHI_THIETBI_THEO_LOAITB.toUpperCase() : ''
            this.FN_KIEMTRA_HIENTHI_LOAITB_NGAYBD_SYSTEMDATE = data.RESULT.FN_KIEMTRA_HIENTHI_LOAITB_NGAYBD_SYSTEMDATE ? data.RESULT.FN_KIEMTRA_HIENTHI_LOAITB_NGAYBD_SYSTEMDATE.toUpperCase() : ''
            this.TUDONG_TINHSOTHANG_SD_THEO_GOICUOC = data.RESULT.TUDONG_TINHSOTHANG_SD_THEO_GOICUOC ? data.RESULT.TUDONG_TINHSOTHANG_SD_THEO_GOICUOC.toUpperCase() : ''
            this.FN_TAO_NGAYBD_NGAYKT_KHI_GIAHAN = data.RESULT.FN_TAO_NGAYBD_NGAYKT_KHI_GIAHAN ? data.RESULT.FN_TAO_NGAYBD_NGAYKT_KHI_GIAHAN.toUpperCase() : ''

            if (data.RESULT.DSKHUYENMAI && data.RESULT.DSKHUYENMAI.length > 0) {
              this.DSKHUYENMAI = data.RESULT.DSKHUYENMAI
              this.cboKhuyenMai.value = data.RESULT.DSKHUYENMAI[0].KHUYENMAI_ID
              this.chkKhuyenMai.Checked = data.RESULT.DSKHUYENMAI[0].CHON == 1 ? true : false
            } else {
              this.DSKHUYENMAI = []
              this.cboKhuyenMai.value = 0
              this.chkKhuyenMai.Checked = false
            }
            if (data.RESULT.DSTRATRUOC && data.RESULT.DSTRATRUOC.length > 0) {
              this.DSTRATRUOC = data.RESULT.DSTRATRUOC
              this.cboTraTruoc.value = data.RESULT.DSTRATRUOC[0].KHUYENMAI_ID
              this.chkTraTruoc.Checked = data.RESULT.DSTRATRUOC[0].CHON == 1 ? true : false
            } else {
              this.DSTRATRUOC = []
              this.cboTraTruoc.value = 0
              this.chkTraTruoc.Checked = false
            }
          } else {
            this.ShowError(data.MESSAGE)
          }
        } else {
          this.ShowError('Lỗi lấy TT thay đổi tốc độ ' + response.data.message)
        }
      } catch (ex) {
        console.log(ex)
        this.ShowError('Lỗi lấy TT thay đổi tốc độ')
      }
    },
    txtDINHTUYENButtonEdit_ButtonClick() {
      if (Number(this.cboLoaiHinhTB.value) == LoaiHinhTB.TD_1800 || Number(this.cboLoaiHinhTB.value) == LoaiHinhTB.TD_1900) {
        if (!this.txtMaGD.value || this.txtMaGD.value == '') return
        this.popupComponentData = {
          vloaitb_id: this.cboLoaiHinhTB.value,
          vhdtb_id: this.hdtb_id,
          vappend_name: 'DINHTUYEN'
        }
        this.popupComponent = () => import('@/modules/contract/setup/InstallNewSubs/Popups/popupHDTB_CNTT_SUB')
        this.popupComponentName = 'popupHDTB_CNTT_SUB'
        this.popupComponentHeader = ''
        this.Popup('popupComponents')
      }

      if (Number(this.cboLoaiHinhTB.value) == LoaiHinhTB.THOAIQT) {
        this.popupComponentData = {
          vloaitb_id: Number(this.cboLoaiHinhTB.value),
          vdichvuvt_id: Number(this.cboDichVuVT.value),
          vsomay: this.txtMaTB.vlue
        }
        this.popupComponent = () => import('@/modules/contract/setup/InstallNewSubs/Popups/popupChonSo_CNTT')
        this.popupComponentName = 'popupChonSo_CNTT'
        this.popupComponentHeader = ''
        this.Popup('popupComponents')
        // frmChonSo_CNTT frm = new frmChonSo_CNTT();
        // frm.vloaitb_id = Number(this.cboLoaiHinhTB.value);
        // frm.vdichvuvt_id = Number(this.cboDichVuVT.value);
        // frm.ShowDialog();
        // if (frm.isCapNhat)
        // {
        //     ((TextEdit)(dycDVCNTT.DicInputControl["txtUSERNAME"])).value = frm.vsomay;

        // }
      }
    },
    // btnChonSoCNTT_click: function () {
    //   try {
    //     if (Number(this.cboLoaiHinhTB.value) == LoaiHinhTB.TD_1800 || Number(this.cboLoaiHinhTB.value) == LoaiHinhTB.TD_1900) {
    //       if (!this.txtMaTB.value || !this.dycDVCNTT) {
    //         this.ShowError('Bạn chưa nhập số máy/account tb')
    //         return
    //       }
    //       this.popupComponentData = {
    //         vloaitb_id: Number(this.cboLoaiHinhTB.value),
    //         vdichvuvt_id: Number(this.cboDichVuVT.value),
    //         vsomay: this.txtMaTB.value
    //       }
    //       this.popupComponent = () => import('@/modules/contract/setup/InstallNewSubs/Popups/popupChonSo_CNTT')
    //       this.popupComponentName = 'popupChonSo_CNTT'
    //       this.popupComponentHeader = ''
    //       this.Popup('popupComponents')
    //     } else {
    //       this.ShowError('Bạn phải chọn loại hình tb là 1800 hoặc 1900')
    //     }
    //   } catch (e) {
    //     console.log('btnChonSoCNTT_click', e)
    //   }
    // },
    txtDINHMUC_SMSButtonEdit_ButtonClick: function() {
      if (Number(this.cboLoaiHinhTB.value) == LoaiHinhTB.BRANDNAME) {
        if (!this.txtMaGD.value || this.txtMaGD.value == '') return
        this.popupComponentData = {
          vloaitb_id: Number(this.cboLoaiHinhTB.value),
          vhdtb_id: this.hdtb_id,
          vappend_name: 'DINHMUC_SMS'
        }
        this.popupComponent = () => import('@/modules/contract/setup/InstallNewSubs/Popups/popupHDTB_CNTT_SUB')
        this.popupComponentName = 'popupHDTB_CNTT_SUB'
        this.popupComponentHeader = ''
        this.Popup('popupComponents')
      }
    },
    repositoryItemButtonEdit_ButtonClick: async function(sFieldName) {
      this.isShowDynamicPopup = true;
      console.log(sFieldName)
      try {
        if (sFieldName == 'DONGIA' || sFieldName == 'DINHMUC_SMS' || sFieldName == 'DOITAC' || sFieldName == 'DINHMUC_LL' || sFieldName == 'DINHTUYEN' || sFieldName == 'THONGTIN_CT') {
          if (Number(this.cboLoaiHinhTB.value) == LoaiHinhTB.TD_1800 || Number(this.cboLoaiHinhTB.value) == LoaiHinhTB.TD_1900) {
            this.popupComponentData = {
              vloaitb_id: this.cboLoaiHinhTB.value,
              vhdtb_id: this.hdtb_id,
              vappend_name: sFieldName
            }
            this.popupComponent = () => import('@/modules/contract/setup/InstallNewSubs/Popups/popupHDTB_CNTT_SUB')
            this.popupComponentName = 'popupHDTB_CNTT_SUB'
            this.popupComponentHeader = ''
            this.Popup('popupComponents')
          }
        }
        if(sFieldName == "MATB_TN")
        {
          const lay_kieu =  await api.SP_LAY_DS_DL_NGHIEPVU(this.axios, {
            p_nghiepvu: 'LAY_KIEU_TRONG_KIEU_LD',
            p_ds_para: '{"KIEULD_ID":' + Number(this.cboKieuLD.value)+ '}'
          })
          let kieu = lay_kieu.data.data[0].kieu
          console.log("Lấy ra kiểu từ kiểu lắp đặt" + kieu)
          let dulieu = {}
          switch (kieu * 1) {
            case 2:
              if (this.khachhang_id&& this.khachhang_id > 0) {
                dulieu.khachhang_id = this.khachhang_id
                dulieu.kieuld_id = Number(this.cboKieuLD.value)
                dulieu.loaitb_id = Number(this.cboLoaiHinhTB.value)
                dulieu.dichvuvt_id = Number(this.cboDichVuVT.value)
                console.log(dulieu)
                this.popupComponentData = dulieu
                this.popupComponent = () => import('@/modules/contract/setup/InstallNewSubs/Popups/popupDSTBCungDoiCap.vue')
                this.popupComponentName = 'popupDSTBCungDoiCapCNTT'
                this.Popup('popupComponents')
              } else {
                this.$toast.error('Chưa có thông tin thuê bao để lấy danh sách mã truy nhập lắp trên đường có sẵn !')
                return
              }
              break
          }
        }
        if (sFieldName == 'THONGTIN_CT') {
          if (this.cboLoaiHinhTB.value == 376) {
            // TODO:
            if (!this.hdtb_id) {
              this.$toast.error('Hãy ấn cập nhật trước!')
              return
            }
            //this.popupComponent = () => import("../../../search/subscriber/SearchEProfile");
            this.dialogComponent = () => import('../..//setup/InstallNewSubs/Popups/frmNhanCanhBaoHeThong')
            this.popupComponentName = 'frmNhanCanhBaoHeThong'
            this.popupComponentData = {
              // matb: this.state.selectedPhieu.ma_tb,
              // magd: this.state.selectedPhieu.ma_gd,
              ispopup: true,
              vhdtb_id: this.hdtb_id
            }
            this.popupComponentAttr = {
              // matb: this.state.selectedPhieu.ma_tb,
              // magd: this.state.selectedPhieu.ma_gd,
              ispopup: true,
              vhdtb_id: this.hdtb_id
            }
            this.modalTitle = 'Số thuê bao nhận cảnh báo từ hệ thống'
            console.log('tsbtnFile_Upload_Click', this.popupComponentData)
            //this.Popup("modalComponentsITVAS");
            this.$refs.generalDialog.show()
          }
        } if (sFieldName == 'THONGTIN_XT') {
          if (this.cboLoaiHinhTB.value == 376) {
            // TODO:
            if (!this.hdtb_id) {
              this.$toast.error('Hãy ấn cập nhật trước!')
              return
            }
            //this.popupComponent = () => import("../../../search/subscriber/SearchEProfile");
            this.dialogComponent = () => import('../..//setup/InstallNewSubs/Popups/frmSoThueBaoM2M')
            this.popupComponentName = 'frmNhanCanhBaoHeThong'
            this.popupComponentData = {
              // matb: this.state.selectedPhieu.ma_tb,
              // magd: this.state.selectedPhieu.ma_gd,
              ispopup: true,
              vhdtb_id: this.hdtb_id
            }
            this.popupComponentAttr = {
              // matb: this.state.selectedPhieu.ma_tb,
              // magd: this.state.selectedPhieu.ma_gd,
              ispopup: true,
              vhdtb_id: this.hdtb_id
            }
            this.modalTitle = 'Số thuê bao M2M'
            console.log('tsbtnFile_Upload_Click', this.popupComponentData)
            //this.Popup("modalComponentsITVAS");
            this.$refs.generalDialog.show()
          }
        }

        if(sFieldName) {
          if (this.hdtb_id == 0) {
            return this.$toast.warning('Vui lòng điền đầy đủ thông tin ở thuê bao và ghi lại trước!')
          }
          let p_ds_para = {
            LOAITB_ID: this.cboLoaiHinhTB.value,
            FIELD_NAME: sFieldName,
            KIEULD_ID: this.cboKieuLD.value,
            TOCDO_ID: this.tocdo_id
          }

          let data_check = await API.SP_LAY_DS_DL_NGHIEPVU(this.axios, {
            p_nghiepvu: 'SUDUNG_DYNAMIC_HD_THUEBAO_CT',
            p_ds_para: JSON.stringify(p_ds_para)
          })
          let limit_data = await API.SP_LAY_DS_DL_NGHIEPVU(this.axios, {
            p_nghiepvu: 'LIMIT_SUDUNG_DYNAMIC_HD_THUEBAO_CT',
            p_ds_para: JSON.stringify(p_ds_para)
          })
          if (data_check.data.data[0].ketqua == 'ok') {
            let vfield_detail = {
              vloaitb_id: this.cboLoaiHinhTB.value,
              vopt_list: "00000",
              vfield_name_cha: sFieldName
            }
            //open Dialog
            this.popupComponentData = {
              vloaitb_id: this.cboLoaiHinhTB.value,
              hdtb_id: this.hdtb_id,
              vappend_name: sFieldName,
              enableEdit: true,
              vfield_detail: vfield_detail,
              limit_data: limit_data.data.data[0].ketqua
            }
            this.popupComponent = () => import('@/modules/contract/setup/InstallNewSubs/Popups/frmDynamic_Hd_thuebao_ct/frmDynamic_Hd_thuebao_ct.vue')
            this.popupComponentName = 'frmDynamic_Hd_thuebao_ct'
            this.modalTitle = 'Thông tin bổ sung'
            this.$refs.popupComponents.show()
          }
          else
          {
            return this.$toast.warning(data_check.data.data[0].ketqua)
          }
        }

      } catch (ex) {}
    },
    DuLieuControlDong: function(ds) {
      try {
        var DLControl = []
        if (ds.length > 0) {
          ds.forEach((row) => {
            var item = {}
            item.sFieldName = row['field_name'] ? row['field_name'] : ''
            item.sCaption = row['ten_hienthi'] ? row['ten_hienthi'] : ''
            item.iAtt = row['att'] ? row['att'] : 0
            item.iDataType = row['data_type'] ? row['data_type'] : ''
            item.sMaxValue = row['max_value'] ? row['max_value'] : ''
            item.sMinvalue = row['min_value'] ? row['min_value'] : ''
            item.sValue = row['field_value'] ? row['field_value'] : ''
            item.nFieldLength = row['field_length'] ? row['field_length'] : 0
            item.bIsNotNull = Number(row['field_nullable']) == 0 ? true : false
            item.bIsUnique = Number(row['field_unique']) == 0 ? true : false
            item.sLookUpTableName = row['parent_table'] ? row['parent_table'] : ''
            item.sLookUpKeyField = row['parent_keyfield'] ? row['parent_keyfield'] : ''
            item.sLookUpValueField = row['parent_descfield'] ? row['parent_descfield'] : ''
            item.strDepandField = row['depend_field'] ? row['depend_field'] : ''
            item.sLookupApi = row['lookup_api'] ? row['lookup_api'] : ''
            item.sAPIHienThiBang = row['api_hienthi_bang'] ? row['api_hienthi_bang'] : ''
            item.sAPIDisplayMember = row['api_display_member'] ? row['api_display_member'] : ''
            item.sAPIValueMember = row['api_value_member'] ? row['api_value_member'] : ''
            item.sAPIMethod = row['api_method'] ? row['api_method'] : ''
            item.sReadOnly = row['read_only'] ? row['read_only'] : true
            item.sTableName = row['table_name'] ? row['table_name'] : ''
            item.StrSql = row['lookup_sql'] ? row['lookup_sql'] : ''
            var dt = []
            if (row['lookup_sql_response'] && row['lookup_sql_response'].length > 0) {
              var UPPER = this.UpperCasePropertyList(row['lookup_sql_response'])
              for (var i = 0; i < UPPER.length; i++) {
                var obj = UPPER[i]
                var tmp = {
                  id: obj[row.parent_keyfield],
                  text: obj[row.parent_descfield],
                  addon: obj
                }
                dt.push(tmp)
                // }
              }
            }
            item.dataSource = dt
            DLControl.push(item)
          })
        }
        return DLControl
      } catch (e) {
        console.log('DuLieuControlDong')
        console.log(e)
      }
    },
    LAY_TT_DB_MORONG: async function(data) {
      try {
        var para = {
          THUEBAO_ID: data.THUEBAO_ID,
          HDKH_ID: data.HDKH_ID,
          HDTB_ID: data.HDTB_ID,
          DICHVUVT_ID: data.DICHVUVT_ID,
          LOAITB_ID: data.LOAITB_ID,
          KIEULD_ID: data.KIEULD_ID,
          PHUONGLD_ID: data.PHUONGLD_ID,
          PHOLD_ID: data.PHOLD_ID,
          APLD_ID: data.APLD_ID,
          KHULD_ID: data.KHULD_ID,
          DOITUONG_ID: data.DOITUONG_ID,
          TRANGBI_ID: data.TRANGBI_ID
        }
        var input = {
          phanvung_id: this.tt_nd.phanvung_id,
          ds_para: JSON.stringify(para)
        }
        let response = await api.ttdb_morong_giahancntt(this.axios, input)
        if (response && response.data && response.data.error_code == 'BSS-00000000') {
          var data = JSON.parse(response.data.data)
          if (data.ERROR_CODE == 1) {
            // console.log(data.RESULT);
            this.DS = data.RESULT.DS
            this.DSKHUYENMAI = data.RESULT.DSKHUYENMAI
            this.DSTRATRUOC = data.RESULT.DSTRATRUOC
            this.m_dsThueBao_DichVu = data.RESULT.DS_DK_DVGT
            this.DTMUCCUOCTB = data.RESULT.DTMUCCUOCTB
            this.DT_LUONG = data.RESULT.DT_LUONG
            this.FN_LAY_KIEULD_ElEARNING = data.RESULT.FN_LAY_KIEULD_ElEARNING
          } else {
            this.ShowError(data.MESSAGE)
            this.DS = []
            this.DSKHUYENMAI = []
            this.DSTRATRUOC = []
            this.m_dsThueBao_DichVu = []
            this.DTMUCCUOCTB = []
            this.DT_LUONG = []
            this.FN_LAY_KIEULD_ElEARNING = 'NOK'
          }
        } else {
          this.ShowError('Lỗi lấy TT DB mở rộng ' + response.data.message_detail)
          this.DS = []
          this.DSKHUYENMAI = []
          this.DSTRATRUOC = []
          this.m_dsThueBao_DichVu = []
          this.DTMUCCUOCTB = []
          this.DT_LUONG = []
          this.FN_LAY_KIEULD_ElEARNING = 'NOK'
        }
      } catch (ex) {
        console.log(ex)
        this.ShowError('Lỗi lấy TT DB mở rộng')
      }
    },
    LAY_DS_HOPDONG_THEO_MA_GD: async function() {
      try {
        var input = {
          in_ma_gd: this.txtMaGD.value,
          in_loaihd_id: LoaiHopDong.GIAHAN,
          in_donvi_id: this.tt_nd.donvi_id,
          in_tthd_id: TrangThaiHD.MOI,
          in_nhanvien_id: this.tt_nd.nhanvien_id,
          in_donvi_dl_id: 0
        }
        let response = await api.ds_hopdong_theo_ma_gd(this.axios, input)
        if (response && response.data && response.data.error_code == 'BSS-00000000') {
          return response.data.data
        } else {
          return []
        }
      } catch (ex) {
        console.log(ex)
        this.ShowError('Lỗi Tìm hợp đồng theo mã GD!')
      }
    },
    LAY_DS_HOPDONG_TB_THEO_HDKH_ID: async function(hdkh_id) {
      try {
        var input = {
          in_hdkh_id: hdkh_id
        }
        let response = await api.lay_ds_hopdong_tb_theo_hdkh_id(this.axios, input)
        if (response && response.data && response.data.error_code == 'BSS-00000000') {
          return response.data.data
        } else {
          return []
        }
      } catch (ex) {
        console.log(ex)
        this.ShowError('Lỗi Tìm hợp đồng TB theo HDKH!')
      }
    },
    getNhanvien_nvID: async function(nhanvien_id) {
      let data = this.GetData(await api.getNhanvien_nvID(this.axios, nhanvien_id))
      if (data.length > 0) {
        return data[0].NAME
      } else {
        return ''
      }
    },
    trangthai_hd: async function(tthd_id) {
      let data = this.GetData(await api.getds_trangthaihd(this.axios))
      if (data.length > 0) {
        var hd = data.filter((e) => e.ID == tthd_id)
        if (hd && hd.length > 0) {
          return hd[0].NAME
        } else {
          return ''
        }
      }
    },
    loaihinhtb_loaidv: async function(dichvuvt_id) {
      try {
        var input = {
          dichvuvt_id: Number(dichvuvt_id)
        }
        let response = await api.loaihinhtb_loaidv(this.axios, input)
        if (response && response.data && response.data.error_code == 'BSS-00000000') {
          this.ds_loaihinhtb = response.data.data
        } else {
          this.ShowError('Không lấy được DS loại hình TB ' + response.data.message_detail)
        }
      } catch (ex) {
        console.log(ex)
        this.ShowError('Lỗi lấy loại hình TB')
      }
    },
    kieulapdat_loaihinhtb: async function(loaihd_id, loaitb_id) {
      try {
        this.ds_kieuld = []
        var input = {
          loaihd_id: loaihd_id,
          loaitb_id: loaitb_id
        }
        let response = await api.layds_kieuld_theo_lhtb(this.axios, input)
        if (response && response.data && response.data.error_code == 'BSS-00000000' && response.data.data && response.data.data.length > 0) {
          this.ds_kieuld = this.UpperCasePropertyList(response.data.data)
          var check = this.ds_kieuld.filter((e) => e.KIEULD_ID == Number(this.cboKieuLD.value))
          if (check.length == 0) this.cboKieuLD.value = this.ds_kieuld[0].KIEULD_ID
        } else {
          this.ShowError('Không lấy được Danh Sách kiểu Yêu Cầu theo loại hình TB')
          this.ds_kieuld = []
        }
      } catch (ex) {
        console.log(ex)
        this.ShowError('Lỗi lấy kiểu lắp đặt theo loại hình TB')
        this.ds_kieuld = []
      }
    },
    async NAP_DS_KM_DC(vhdtb_id) {
      try {
        var dsTemp = await this.LAY_CHITIET_KM_THEO_HDTB_ID(vhdtb_id, 0)
        this.dsKM_HDTB = []
        if (dsTemp.length > 0) {
          if (dsTemp[0]['khuyenmai_id']) {
            this.cboKhuyenMai.value = dsTemp[0]['khuyenmai_id']
            this.chkKhuyenMai.Checked = true
          } else {
            this.chkKhuyenMai.Checked = false
          }
          this.dsKM_HDTB = dsTemp
        } else {
          this.chkKhuyenMai.Checked = false
          this.dsKM_HDTB = []
        }
        // //Longdx: bổ sung lấy chi tiết KM
        var dsTempTT = await this.LAY_CHITIET_KM_THEO_HDTB_ID(vhdtb_id, 1)
        this.dsDC_HDTB = []
        if (dsTempTT.length > 0) {
          if (dsTempTT[0]['khuyenmai_id']) {
            this.cboTraTruoc.value = dsTempTT[0]['khuyenmai_id']
            this.chkTraTruoc.Checked = true
          } else {
            this.chkTraTruoc.Checked = false
          }
          this.dsDC_HDTB = dsTempTT
        } else {
          this.chkTraTruoc.Checked = false
          this.dsDC_HDTB = []
        }
      } catch (ex) {
        console.log('NAP_DS_KM_DC')
        console.log(ex)
      }
    },
    LAY_CHITIET_KM_THEO_HDTB_ID: async function(vhdtb_id, vloai_km) {
      try {
        var km = vloai_km == 0 ? 'khuyến mãi' : 'trả trước'
        var input = {
          vhdtb_id: Number(vhdtb_id),
          vloai_km: Number(vloai_km)
        }
        let response = await api.lay_chitiet_km_theo_hdtb_id(this.axios, input)
        if (response && response.data && response.data.error_code == 'BSS-00000000') {
          return response.data.data
        }
        if (response.data.error_code == 'BSS-00000204') {
          // this.ShowError("Không có chi tiết " + km + " đặt cọc theo HDTB ");
          return []
        } else {
          this.ShowError('Lỗi lấy chi tiết ' + km + ' đặt cọc theo HDTB ' + response.data.message_detail)
        }
      } catch (ex) {
        console.log(ex)
        this.ShowError('Lỗi chi tiết khuyến mãi đặt cọc theo HDTB')
      }
    },
    async NAP_DS_KMTT(kieuld_id, vhdtb_id) {
      try {
        this.NAP_DS_CTTKM(kieuld_id)
        var tmp = await this.sp_nap_ds_kmtt(vhdtb_id)
        if (tmp && tmp.length > 0) {
          tmp = this.UpperCasePropertyList(tmp)
          for (var item of tmp) {
            var index =  this.ds_tien_khoanmuc.findIndex(e => e['KHOANMUCTT_ID'] == item['KHOANMUCTT_ID'] && e['TIEN'] == 0)
            this.ds_tien_khoanmuc = this.ds_tien_khoanmuc.filter(e => e != this.ds_tien_khoanmuc[index])
          }
          this.ds_tien_khoanmuc = this.ds_tien_khoanmuc.concat(tmp)
        }
        if (this.ds_tien_khoanmuc && this.ds_tien_khoanmuc.length > 0) {
          for (var j = 0; j < this.ds_tien_khoanmuc.length; j++) {
            if (Number(this.ds_tien_khoanmuc[j]['KHOANMUCTT_ID']) == KHOANMUC_TT.KMTT_DATCOC) {
              this.dtiendatcoc_tt += Number(this.ds_tien_khoanmuc[j]['TIEN'])
              this.dvatdatcoc_tt += Number(this.ds_tien_khoanmuc[j]['VAT'])
            }
            if (Number(this.ds_tien_khoanmuc[j]['KHOANMUCTT_ID']) == KHOANMUC_TT.KMTT_DATCOC_KM) {
              this.dtiendatcoc_km += Number(this.ds_tien_khoanmuc[j]['TIEN'])
              this.dvatdatcoc_km += Number(this.ds_tien_khoanmuc[j]['VAT'])
            }
          }
        }
        console.log('NAP_DS_KMTT',this.ds_tien_khoanmuc, tmp)
      } catch (ex) {
        console.log('NAP_DS_KMTT')
        console.log(ex)
        this.ShowError('Lỗi nạp dữ liệu khoản mục thanh toán')
      }
    },
    sp_nap_ds_kmtt: async function(hdtb_id) {
      try {
        var input = {
          hdtb_id: hdtb_id,
          phanvung_id: this.tt_nd.phanvung_id
        }
        let response = await api.nap_ds_kmtt(this.axios, input)
        if (response && response.data && response.data.error_code == 'BSS-00000000') {
          if (response.data.data && response.data.data.length > 0) return this.UpperCasePropertyList(response.data.data)
          else return []
        } else if (response.data.error_code == 'BSS-00000204') {
          this.ShowError('Không có chi tiết khoản mục thanh toán theo HDTB ')
          return []
        } else {
          this.ShowError('Lỗi lấy chi tiết khoản mục thanh toán theo HDTB: ' + response.data.message_detail)
        }
      } catch (ex) {
        console.log(ex)
        // this.ShowError("Lỗi chi tiết khoản mục thanh toán theo HDTB ");
      }
    },
    NAP_DS_CTTKM(vkieuld_id) {
      try {
        // this.ds_tien_khoanmuc = []
        var dskld = this.DANHMUC_TIENCTKM.filter((e) => e.KIEULD_ID == Number(vkieuld_id))
        if (dskld.length > 0) {
          dskld = dskld.filter((obj, index) => {
            return index === dskld.findIndex(o => obj.KHOANMUCTT_ID === o.KHOANMUCTT_ID && obj.TIEN === o.TIEN && obj.VAT === o.VAT);
          })
          this.ds_tien_khoanmuc = this.UpperCasePropertyList(dskld)
        }
        console.log('NAP_DS_CTTKM',this.ds_tien_khoanmuc)
      } catch (ex) {
        console.log('NAP_DS_CTTKM')
        console.log(ex)
      }
    },
    HienThiTien_TraTruoc() {
      try {
        if (!this.ds_tien_khoanmuc || this.ds_tien_khoanmuc == null || this.ds_tien_khoanmuc.length == 0) {
          return
        }
        if (this.ds_tien_khoanmuc.length > 0) {
          for (var j = 0; j < this.ds_tien_khoanmuc.length; j++) {
            if (this.ds_tien_khoanmuc[j]['KHOANMUCTT_ID'].toString() == KHOANMUC_TT.KMTT_DATCOC_KM.toString()) {
              this.ds_tien_khoanmuc[j]['TIEN'] = this.dtiendatcoc_km
              this.ds_tien_khoanmuc[j]['VAT'] = this.dvatdatcoc_km
            }
            if (this.ds_tien_khoanmuc[j]['KHOANMUCTT_ID'].toString() == KHOANMUC_TT.KMTT_DATCOC.toString()) {
              this.ds_tien_khoanmuc[j]['TIEN'] = this.dtiendatcoc_tt
              this.ds_tien_khoanmuc[j]['VAT'] = this.dvatdatcoc_tt
            }
          }
        }
        this.LayTongTien_HDTB()
      } catch (ex) {
        console.log('HienThiTien_TraTruoc')
        console.log(ex)
      }
    },
    // tien_kmld, vat_kmld, tien_datcoc, tien_kmld_tt, vat_kmld_tt, dtiendatcoc_tt, kmtt_id, tien_kmtt, tien_vat
    HienThiTienKhuyenMaiLapDat: async function () {
      var dtienkmld_tmp = 0
      var dvatkmld_tmp = 0
      var dtyle_kmld_tmp
      dtienkmld_tmp = this.dtienkmld
      dvatkmld_tmp = this.dvatkmld
      dtyle_kmld_tmp = this.tyle_kmld
      //longdx: bổ sung lấy tiền kmld lớn nhất trong 2 loại km của Khuyến mại và đặt cọc
      if (Math.abs(this.dtienkmld) < Math.abs(this.dtienkmdc)) {
        dtienkmld_tmp = this.dtienkmdc
        dvatkmld_tmp = this.dvatkmdc
      }
      if (dtyle_kmld_tmp < this.tyle_kmld_tt) {
        dtyle_kmld_tmp = this.tyle_kmld_tt
      }
      if (dtyle_kmld_tmp > 0) {
        dtienkmld_tmp = Math.round((this.dtienld * dtyle_kmld_tmp) / 100)
        dvatkmld_tmp = ((this.dtienld + this.dvatld) * dtyle_kmld_tmp) / 100 - dtienkmld_tmp
      }
      if (this.ds_tien_khoanmuc && this.ds_tien_khoanmuc.length > 0) {
        for (var j = 0; j < this.ds_tien_khoanmuc.length; j++) {
          if (this.ds_tien_khoanmuc[j]['KHOANMUCTT_ID'].toString() == KHOANMUC_TT.KMTT_KM_LAPDAT.toString()) {
            if (dtienkmld_tmp < 0) {
              this.ds_tien_khoanmuc[j]['TIEN'] = dtienkmld_tmp * -1
              this.ds_tien_khoanmuc[j]['VAT'] = dvatkmld_tmp * -1
            } else {
              this.ds_tien_khoanmuc[j]['TIEN'] = dtienkmld_tmp
              this.ds_tien_khoanmuc[j]['VAT'] = dvatkmld_tmp
            }
          } else
          if (this.ds_tien_khoanmuc[j]['KHOANMUCTT_ID'].toString() == KHOANMUC_TT.KMTT_DATCOC.toString()) {
            this.ds_tien_khoanmuc[j]['TIEN'] = Math.round(this.dtiendatcoc_tt)
            this.ds_tien_khoanmuc[j]['VAT'] = Math.round(this.dvatdatcoc_tt)
          } else
          if (this.ds_tien_khoanmuc[j]['KHOANMUCTT_ID'].toString() == KHOANMUC_TT.KMTT_DATCOC_KM.toString()) {
            this.ds_tien_khoanmuc[j]['TIEN'] = Math.round(this.dtiendatcoc_km)
            this.ds_tien_khoanmuc[j]['VAT'] = Math.round(this.dvatdatcoc_km)
          } else
          if (this.ds_tien_khoanmuc[j]['KHOANMUCTT_ID'].toString() == this.kmtt_id.toString() && this.kmtt_id != -1) {
            this.ds_tien_khoanmuc[j]['TIEN'] = this.tien_kmtt
            this.ds_tien_khoanmuc[j]['VAT'] = this.tien_vat
          }
        }
        this.ds_tien_khoanmuc = this.ds_tien_khoanmuc.filter((obj, index) => {
          return index === this.ds_tien_khoanmuc.findIndex(o => obj.KHOANMUCTT_ID === o.KHOANMUCTT_ID && obj.TIEN === o.TIEN && obj.VAT === o.VAT);
        })
      }
      console.log('HienThiTienKhuyenMaiLapDat: dsKM_HDTB, dsDC_HDTB, ds_tien_khoanmuc',this.dsKM_HDTB, this.dsDC_HDTB, this.ds_tien_khoanmuc)
      await this.LayTongTien_HDTB()
    },
    LayTongTien_HDTB() {
      try {
        var dtongtien = 0
        var dtongvat = 0
        if (Number(this.cboKieuLD.value) != 13187 && Number(this.cboKieuLD.value) != 13188 && Number(this.cboKieuLD.value) != 13229 && Number(this.cboKieuLD.value) != 13238)
          if (this.ds_tien_khoanmuc.length > 0) {
            var ds_tien_khoanmuc = this.ds_tien_khoanmuc
            this.ds_tien_khoanmuc = ds_tien_khoanmuc.filter((obj, index) => {
              return index === ds_tien_khoanmuc.findIndex(o => obj.KHOANMUCTT_ID === o.KHOANMUCTT_ID && obj.TIEN === o.TIEN && obj.VAT === o.VAT);
            })
            for (var i = 0; i < this.ds_tien_khoanmuc.length; i++) {
              if (this.ds_tien_khoanmuc[i]['KHOANMUCTT_ID'].toString() == KHOANMUC_TT.KMTT_KM_LAPDAT.toString() || this.ds_tien_khoanmuc[i]['KHOANMUCTT_ID'].toString() == KHOANMUC_TT.KMTT_KM_DATCOC.toString()) {
                if (Number(this.ds_tien_khoanmuc[i]['TIEN'].toString()) < 0) {
                  dtongtien += Number(this.ds_tien_khoanmuc[i]['TIEN'].toString())
                  dtongvat += Number(this.ds_tien_khoanmuc[i]['VAT'].toString())
                } else {
                  dtongtien += Number(this.ds_tien_khoanmuc[i]['TIEN'].toString()) * -1
                  dtongvat += Number(this.ds_tien_khoanmuc[i]['VAT'].toString()) * -1
                }
              } else if (
                this.ds_tien_khoanmuc[i]['KHOANMUCTT_ID'].toString() != KHOANMUC_TT.KMTT_TRAGOP_THIETBI.toString() &&
                this.ds_tien_khoanmuc[i]['KHOANMUCTT_ID'].toString() != KHOANMUC_TT.KMTT_KM_THIETBI.toString() &&
                this.ds_tien_khoanmuc[i]['KHOANMUCTT_ID'].toString() != KHOANMUC_TT.KMTT_MUATHIETBI.toString() &&
                this.ds_tien_khoanmuc[i]['KHOANMUCTT_ID'].toString() != KHOANMUC_TT.KMTT_THUETHIETBI.toString() &&
                this.ds_tien_khoanmuc[i]['KHOANMUCTT_ID'].toString() != KHOANMUC_TT.KMTT_PHI_THUMOTLAN.toString()
              ) {
                dtongtien += Number(this.ds_tien_khoanmuc[i]['TIEN'].toString())
                dtongvat += Number(this.ds_tien_khoanmuc[i]['VAT'].toString())
              }
            }
          }
        this.txtTongTien.value = dtongtien
        this.txtTongVat.value = dtongvat
      } catch (ex) {
        console.log('LayTongTien_HDTB')
        console.log(ex)
      }
    },
    getdiachiTB: function(data) {
      this['diachiTB'].DIACHI = data.diachimoi ? data.diachimoi : ''
      this['diachiTB'].TINH_ID = data.tinh_id ? data.tinh_id : 0
      this['diachiTB'].QUAN_ID = data.quan_id ? data.quan_id : 0
      this['diachiTB'].PHUONG_ID = data.phuong_id ? data.phuong_id : 0
      this['diachiTB'].PHO_ID = data.pho_id ? data.pho_id : 0
      this['diachiTB'].AP_ID = data.ap_id ? data.ap_id : 0
      this['diachiTB'].KHU_ID = data.khu_id ? data.khu_id : 0
      this['diachiTB'].DACDIEM_ID = data.dacdiem_id ? data.dacdiem_id : 0
      this['diachiTB'].SO_NHA = data.so_nha ? data.so_nha : ''
      this['diachiTB'].Lat = data.lat ? data.lat : 0
      this['diachiTB'].Lng = data.lng ? data.lng : 0
      this['diachiTB'].DIACHI_TEXT = data.diachimoi ? data.diachimoi : ''
    },
    getdiachiLD: function(data) {
      this['diachiLD'].DIACHI = data.diachimoi ? data.diachimoi : ''
      this['diachiLD'].TINH_ID = data.tinh_id ? data.tinh_id : 0
      this['diachiLD'].QUAN_ID = data.quan_id ? data.quan_id : 0
      this['diachiLD'].PHUONG_ID = data.phuong_id ? data.phuong_id : 0
      this['diachiLD'].PHO_ID = data.pho_id ? data.pho_id : 0
      this['diachiLD'].AP_ID = data.ap_id ? data.ap_id : 0
      this['diachiLD'].KHU_ID = data.khu_id ? data.khu_id : 0
      this['diachiLD'].DACDIEM_ID = data.dacdiem_id ? data.dacdiem_id : 0
      this['diachiLD'].SO_NHA = data.so_nha ? data.so_nha : ''
      this['diachiLD'].Lat = data.lat ? data.lat : 0
      this['diachiLD'].Lng = data.lng ? data.lng : 0
      this['diachiLD'].DIACHI_TEXT = data.diachimoi ? data.diachimoi : ''
    },
    changeNgayYC: function(value_) {
      if (!moment(value_, 'DD/MM/YYYY HH:mm:ss').isValid()) {
        this.dtpNgayYC.value = '01/01/2000 00:00:00'
      } else {
        this.dtpNgayYC.value = value_
      }
    },
    changeNgayLHD: function(value_) {
      if (!moment(value_, 'DD/MM/YYYY HH:mm:ss').isValid()) {
        this.dtpNgayLapHD.value = '01/01/2000 00:00:00'
      } else {
        this.dtpNgayLapHD.value = value_
      }
    },
    changecboDichVuVT: async function(data) {
      this.THAOTAC_NUT(1)
      if (data) {
        this.cboDichVuVT.value = Number(data)
        await this.loaihinhtb_loaidv(Number(data))
        var checkValid = this.ds_loaihinhtb.findIndex((e) => e.loaitb_id.toString().trim() == this.cboLoaiHinhTB.value.toString().trim())
        if (checkValid == -1) {
          this.cboLoaiHinhTB.value = this.ds_loaihinhtb[0].loaitb_id
        }
      }
    },
    changecboKieuLD: function(data) {
      this.THAOTAC_NUT(1)
      if (data) {
        this.cboKieuLD.value = Number(data)
      }
    },
    HT_DSTRATRUOC_Combobox(DSKHUYENMAI, DSTRATRUOC) {
      if (!DSKHUYENMAI || DSKHUYENMAI == null || DSKHUYENMAI.length == 0) this.chkKhuyenMai.Enabled = false
      else {
        this.chkKhuyenMai.Enabled = true
        this.cboKhuyenMai.value = DSKHUYENMAI[0].KHUYENMAI_ID
      }
      this.chkKhuyenMai.Checked = false

      if (!DSTRATRUOC || DSTRATRUOC == null || DSTRATRUOC.length == 0) this.chkTraTruoc.Enabled = false
      else {
        this.chkTraTruoc.Enabled = true
        this.cboTraTruoc.value = DSTRATRUOC[0].KHUYENMAI_ID
      }
      this.chkTraTruoc.Checked = false
    },
    chkKhuyenMai_CheckedChanged: async function () {
      try {
        this.loaikm = Loai_KM.KhuyenMai
        this.cboKhuyenMai.Enabled = this.chkKhuyenMai.Checked
        this.btnChonKhuyenMai.Enabled = this.chkKhuyenMai.Checked
        this.btnCTKhuyenMai.Enabled = this.chkKhuyenMai.Checked

        //Chưa mở
        // LaySothang_SD();

        if (this.chkKhuyenMai.Checked == false) {
          this.dsKM_HDTB = []
          this.dtiendatcoc_km = 0
          this.dvatdatcoc_km = 0
        }
        // await this.HT_ThietBi()
        // await this.HT_DichVuGT()
        await this.HienThiTienKhuyenMaiLapDat() //dtienkmld, dvatkmld, dtiendatcoc_km, dtienkmdc, dvatkmdc, dtiendatcoc_tt);
        await this.LayTongTien_HDTB()
      } catch (ex) {}
    },
    chkTraTruoc_CheckedChanged: async function () {
      try {
        this.loaikm = Loai_KM.TraTruoc
        this.cboTraTruoc.Enabled = this.chkTraTruoc.Checked
        this.btnChonTraTruoc.Enabled = this.chkTraTruoc.Checked
        this.btnCTTraTruoc.Enabled = this.chkTraTruoc.Checked
        //Chưa mở
        //LaySothang_SD();

        if (this.chkTraTruoc.Checked == false) {
          this.dsDC_HDTB = []
          this.dtiendatcoc_tt = 0
          this.dvatdatcoc_tt = 0
        }
        // await this.HT_ThietBi()
        // await this.HT_DichVuGT()
        await this.HienThiTienKhuyenMaiLapDat() //dtienkmld, dvatkmld, dtiendatcoc_km, dtienkmdc, dvatkmdc, dtiendatcoc_tt);
        await this.LayTongTien_HDTB()
      } catch (ex) {
        console.log('chkTraTruoc_CheckedChanged')
        console.log(ex)
      }
    },
    async btnThongTinTBBS_Click() {
      if (this.hdtb_id == 0) {
        this.ShowError('Bạn cần cập nhật hợp đồng trước')
        return
      }

      let data = await lay_tt_hdtb_sub(this.hdtb_id)
      console.log('lay_tt_hdtb_sub')
      console.log(data)
      if(data.length > 0) {
        this.dsHDTB_SUB = this.UpperCasePropertyList(data);
      }

      if ((Number(this.cboLoaiHinhTB.value) == LoaiHinhTB.Voice_Brandname && Number(this.cboKieuLD.value) == 19544) ||
      (Number(this.cboLoaiHinhTB.value) == LoaiHinhTB.Voice_Brandname && Number(this.cboKieuLD.value) == 19585) ) {
        console.log('frmNhapDSThanhVien', this.hdtb_id)
        this.popup.frmNhapDSThanhVien = true
        this.$refs.frmNhapDSThanhVien.hdtb_id = this.hdtb_id;
        this.$refs.frmNhapDSThanhVien.ShowDialog();
        return
      }

      if (Number(this.cboLoaiHinhTB.value) == LoaiHinhTB.Voice_Brandname && (Number(this.cboKieuLD.value) == 19585 || Number(this.cboKieuLD.value) == 19598 )) {
        console.log('Nhập danh sách số ngoại mạng', this.hdtb_id)
        this.popup.frmNhapDSThanhVienNgoaiMang = true
        this.$refs.frmNhapDSThanhVienNgoaiMang.hdtb_id = this.hdtb_id;
        this.$refs.frmNhapDSThanhVienNgoaiMang.ShowDialog();
        return
      }



      if (Number(this.cboLoaiHinhTB.value) == LoaiHinhTB.VNPT_COLOCATION) {
        this.popupComponentData = {
          thuebao_id: this.thuebao_id,
          hdtb_id: this.hdtb_id,
          ma_gd: this.txtMaGD.value,
          ma_tb: this.txtMaTB.value,
          tinh_tc: '',
          vloaitb_id_cbo: Number(this.cboLoaiHinhTB.value),
          vkieuld_id_cbo: Number(this.cboKieuLD.value)
        }
      } else if (Number(this.cboLoaiHinhTB.value) == LoaiHinhTB.IVAN) {
        this.popupComponentData = {
          hdtb_id: this.hdtb_id,
          vloaitb_id_cbo: Number(this.cboLoaiHinhTB.value),
          vkieuld_id_cbo: Number(this.cboKieuLD.value),
          hdtb_sub: this._hdtbsub,
          tinh_id: this.tt_nd.phanvung_id,
          readOnly: false,
          isUpdate: false
        }
      }
      else if (Number(this.cboLoaiHinhTB.value) == 390 ) { //TSA

        this.popupComponent = () => import('../..//setup/InstallNewSubs/Popups/frmNhapHDTBSUB_TSA')
        this.popupComponentName = 'popupThongTinTBBS'
        this.popupComponentData = this.dsHDTB_SUB

        this.popupComponentHeader = 'Bổ sung dịch vụ TSA'

        if(this.cboKieuLD.value != 13179) {
          this.popupComponentAttr = {disable: true}
        }
        return this.Popup('popupComponents')
      }
      else if (Number(this.cboLoaiHinhTB.value) == LoaiHinhTB.Voice_Brandname) {
        this.popupComponentData = {
          hdtb_id: this.hdtb_id,
          thuebao_id: this.thuebao_id,
          vloaitb_id_cbo: Number(this.cboLoaiHinhTB.value),
          vkieuld_id_cbo: Number(this.cboKieuLD.value),
          hdtb_sub: this._hdtbsub,
          tinh_id: this.tt_nd.phanvung_id,
          readOnly: false,
          isUpdate: false
        }

        // this.$refs.generalPopup.hdtb_id = this.hdtb_id;
      } else {

        this.popupComponentData = {

          hdtb_id: this.hdtb_id,
          hdtb_sub: this.dsHDTB_SUB == null ? null : this.dsHDTB_SUB[0],
          vkieuld_id_cbo: Number(this.cboKieuLD.value),
          vloaitb_id_cbo: Number(this.cboLoaiHinhTB.value)
        }

        if(this.dsHDTB_SUB === null || this.dsHDTB_SUB.length == 0) {
          this.popupComponentData.readonly_init = false;
        }
      }
      this.popupComponent = () => import('./popupThongTinTBBS')
      this.popupComponentHeader = ''
      this.popupComponentName = 'popupThongTinTBBS'
      this.Popup('popupComponents')

    },
    btnDonHangTraTruoc_Click() {
      var kieuld_id = Number(this.cboKieuLD.value)
      // var kt = tdan.GET_VALUE_FUNC("{?DB2}.fn_kiemtra_hienthi_form_dhtt", "vhdtb_id", hdtb_id).toString();
      var kt = 'fn_kiemtra_hienthi_form_dhtt'
      if (kt == 'OK') {
        if (this.cboLoaiHinhTB.value.toString() == '316') {
          // if (kieuld_id == 794)
          // {
          //   //show form
          //   frm_Tao_DHTT_A2P frm = new frm_Tao_DHTT_A2P((long)vhdtb_id);
          //   frm.ShowDialog();
          // }
          // else
          // {
          //   frm_Giahan_DHTT_A2P frm = new frm_Giahan_DHTT_A2P((long)vhdtb_id, (long)thuebao_id);
          //   frm.ShowDialog();
          // }
        } else {
          // if (kieuld_id == 794) {
          //   //show form
          //   frm_BRN_TAO_DHTT frm = new frm_BRN_TAO_DHTT((long)vhdtb_id);
          //   frm.ShowDialog();
          // } else {
          //   frm_BRN_GiahanDHTT frm = new frm_BRN_GiahanDHTT((long)vhdtb_id, (long)thuebao_id);
          //   frm.ShowDialog();
          // }
        }
      } else {
        this.$toast.error(kt)
      }
    },
    btnDongBoHDDT_Click() {
      if (!this.hdtb_id || this.hdtb_id == 0) {
        this.$toast.error('Bạn chưa ghi lại hợp đồng, chưa có biến động để cập nhật')
        return
      }

      this.popupComponent = () => import('./frmChuanHoaHDDT')
      this.popupComponentHeader = ''
      this.popupComponentName = 'frmChuanHoaHDDT'

      var mst = this.dycDVCNTT.filter((e) => e.sFieldName == 'MST')[0].sValue
      this.popupComponentData = {
        hdtb_id: this.hdtb_id,
        mst: mst
      }

      this.Popup('popupComponents')
    },


    btnThongTinKhacClick() {
      this.popupComponent = () => import('@/modules/contract/setup/InstallNewSubs/Popups/popupInforAdd')
      this.popupComponentName = 'popupInforAdd'
      this.popupComponentData = {
        _hdkh_id: this.hdkh_id,
        _nhapmoi: 0,
        _vma_kh: this.txtMaKH.value,
        _vkieukh_id: this.kieukh_id,
        // _ts_kt_tthdkhdn: this.ts_kt_tthdkhdn,
        loaikh: this.loaikh_id
      }
      this.popupComponentHeader = ''
      this.Popup('popupComponents')
    },
    ShowPopupCTV() {
      if (this.chkCTV) {
        this.popupComponent = () => import('@/modules/contract/setup/ChangeContractOwner/popup/CongTacVien')
        this.popupComponentData = { loainv_id: NHOM_LNV.CONGTACVIEN }
        this.popupComponentName = 'popupCTV'
        this.popupComponentHeader = 'Chọn cộng tác viên'
        this.Popup('popupComponents')
      }
    },
    ShowPopupNguoiGT() {
      if (this.chkNguoiGT) {
        this.popupComponent = () => import('@/modules/contract/setup/ChangeContractOwner/popup/NguoiGioiThieu')
        this.popupComponentData = { loainv_id: NHOM_LNV.GIOITHIEU_HD }
        this.popupComponentName = 'popupNGT'
        this.popupComponentHeader = 'Chọn người giới thiệu'
        this.Popup('popupComponents')
      }
    },
    popupDVCongThem() {
      this.popupComponent = () => import('./popupServiceAdds')
      this.popupComponentName = 'popupServiceAdds'
      this.popupComponentHeader = 'Đăng ký dịch vụ gia tăng'
      const lapmoi = Number(this.cboKieuLD.value) == 13189 && Number(this.cboLoaiHinhTB.value) != 295 && Number(this.cboLoaiHinhTB.value) != 294;
      this.popupComponentData = {
        _loaitb_id: Number(this.cboLoaiHinhTB.value),
        _hdtb_id: this.hdtb_id,
        _linhvuc_id: this.linhvuc_id,
        _tocdo_id: this.tocdo_id,
        lvDvgt: this.m_dsThueBao_DichVu,
        lapmoi: true
      }
      this.Popup('popupComponents')
    },
    popupMuaTB() {
      var filter_tocdo = this.dycDVCNTT.filter((e) => e.sFieldName == 'TOCDO_ID')
      var tocdo_id_new = filter_tocdo.length > 0 ? Number(filter_tocdo[0].sValue) : 0
      var _kieuld_id = Number(this.cboKieuLD.value)
      this.popupComponent = () => import('@/modules/contract/setup/InstallNewSubs/Popups/popupBuyDevices')
      this.popupComponentName = 'popupBuyDevices'
      this.popupComponentHeader = 'Mua thiết bị'
      this.popupComponentData = { _loaitb_id: this.cboLoaiHinhTB.value, _tocdo_id_new :tocdo_id_new , _kieuld_id : _kieuld_id}
      if (this.dsCTM_TBI && this.dsCTM_TBI.length > 0) this.popupComponentData.ds = this.dsCTM_TBI
      this.Popup('popupComponents')
    },
    ShowPopupChonKhuyenMai() {
      var dulieu = {}
      dulieu.loai = this.loaikm
      dulieu.dt = this.loaikm == Loai_KM.TraTruoc ? this.LowerCasePropertyList(this.DSTRATRUOC) : this.LowerCasePropertyList(this.DSKHUYENMAI)
      this.popupComponentData = dulieu
      this.popupComponent = () => import('@/modules/contract/setup/InstallNewSubs/Popups/popup-ChonKhuyenMai')
      this.popupComponentName = 'popup-ChonKhuyenMai'
      this.popupComponentHeader = ''
      this.Popup('popupComponents')
    },
    async ShowPopupChiTietKhuyenMai() {
      this.popupComponentHeader = ''
      try {
        var ds_tt = this.loaikm == Loai_KM.KhuyenMai ? this.DSKHUYENMAI : this.DSTRATRUOC
        var dstt_hdtb = this.loaikm == Loai_KM.KhuyenMai ? this.dsKM_HDTB : this.dsDC_HDTB
        if (ds_tt.length > 0) {
          var khuyenmai_id_tt = this.loaikm == Loai_KM.KhuyenMai ? Number(this.cboKhuyenMai.value) : Number(this.cboTraTruoc.value)
          var input_km = {
            khuyenmai_id: khuyenmai_id_tt,
            ngay_yc: moment(new Date()).format('DD/MM/YYYY')
          }
          let kt = -1
          kt = this.GetData(await api.get_kiemTraThoiHanKM(this.axios, input_km))
          if (kt.result == 0) {
            var km = this.loaikm == Loai_KM.KhuyenMai ? ds_tt.filter((e) => Number(e.KHUYENMAI_ID) == Number(this.cboKhuyenMai.value)) : ds_tt.filter((e) => Number(e.KHUYENMAI_ID) == Number(this.cboTraTruoc.value))

            var ten_km = km.length > 0 ? km[0].TEN_KM : ''
            var ngay_kt_km = ''
            var km_id = this.loaikm == Loai_KM.KhuyenMai ? Number(this.cboKhuyenMai.value) : Number(this.cboTraTruoc.value)
            await api.get_lay_ngaykt_km(this.axios, { km_id: km_id }).then((response) => {
              if (response && response.data && response.data.data && response.data.error_code && response.data.error_code == 'BSS-00000000') {
                ngay_kt_km = response.data.data
              }
            })
            var txt = this.loaikm == Loai_KM.KhuyenMai ? 'khuyến mại' : 'trả trước'
            this.$bvModal
              .msgBoxConfirm('Khuyến mại ' + ten_km + ' đã hết thời hạn từ ngày ' + ngay_kt_km + '.\nBạn có muốn tiếp tục chọn chi tiết ' + txt + ' ?', {
                title: 'Thông báo',
                size: 'sm',
                okTitle: 'Đồng ý',
                cancelTitle: 'Hủy'
              })
              .then(async (v) => {
                if (v) {
                  this.popupComponent = () => import('@/modules/contract/setup/InstallNewSubs/Popups/popup-Dangkychitietkhuyenmai.vue')
                  this.popupComponentName = 'popup-DangkychitietkhuyenmaiTT'
                  this.popupComponentData = {
                    isGiaHan: true,
                    khuyenmai_id: khuyenmai_id_tt,
                    _hdtb_id: this.hdtb_id,
                    vdichvuvt_id: Number(this.cboDichVuVT.value),
                    m_dsThueBao_DichVu: this.m_dsThueBao_DichVu,
                    dsDC_HDTB: this.LowerCasePropertyList(dstt_hdtb),
                    vtocdotn_id: this.tocdo_id,
                    vmuccuoctn_id: this.cboMucCuocTB.value,
                    loaihinh_tb: Number(this.cboLoaiHinhTB.value),
                    kieu_km: this.loaikm == Loai_KM.KhuyenMai ? 0 : 1,
                    ht_kmtt: this.thamsomacdinh['HT_KMTT'] == 1
                  } // 0: khuyen mai, 1 tra truoc
                  this.Popup('popupComponents')
                } else {
                  if (this.loaikm == Loai_KM.KhuyenMai) {
                    this.dtiendatcoc_km = 0
                    this.dvatdatcoc_km = 0
                  } else {
                    this.dtiendatcoc_tt = 0
                    this.dvatdatcoc_tt = 0
                  }
                }
              })
              .catch((e) => {
                // this.ShowError(e);
                this.loading(false)
              }).finally(() => {
                return
              })
          }
          if (kt.result == 1) {
            this.popupComponent = () => import('@/modules/contract/setup/InstallNewSubs/Popups/popup-Dangkychitietkhuyenmai.vue')
            this.popupComponentName = 'popup-DangkychitietkhuyenmaiTT'
            this.popupComponentData = {
              isGiaHan: true,
              khuyenmai_id: khuyenmai_id_tt,
              _hdtb_id: this.hdtb_id,
              vdichvuvt_id: Number(this.cboDichVuVT.value),
              m_dsThueBao_DichVu: this.m_dsThueBao_DichVu,
              dsDC_HDTB: this.LowerCasePropertyList(dstt_hdtb),
              vtocdotn_id: this.tocdo_id,
              vmuccuoctn_id: this.vmuccuoc_id,
              loaihinh_tb: Number(this.cboLoaiHinhTB.value),
              kieu_km: this.loaikm == Loai_KM.KhuyenMai ? 0 : 1,
              ht_kmtt: this.thamsomacdinh['HT_KMTT'] == 1
            } // 0: khuyen mai, 1 tra truoc
            this.Popup('popupComponents')
            return
          }
        }
      } catch (ex) {
        // if (ex.data && ex.data.message_detail)
        //   this.ShowError("Có lỗi: " + ex.data.message_detail);
        // else this.ShowError("Có lỗi: " + ex);
      }
    },
    async popupTienKM() {
      try {
        var ds = []
        var ds_tien_khoanmuc = this.ds_tien_khoanmuc && this.ds_tien_khoanmuc.length > 0 ? this.LowerCasePropertyList(this.ds_tien_khoanmuc) : []
        var HT_KMTT = this.thamsomacdinh.filter((e) => e.MA_TS == 'HT_KMTT')
        if (HT_KMTT && HT_KMTT.length > 0 && HT_KMTT[0].TEN_TS.trim() == '1') {
          await api_installnewsubs.post_lay_ds_tien_kmtt(this.axios, { vkieuld_id: Number(this.cboKieuLD.value), vloaihd_id: LoaiHopDong.GIAHAN, vloaitb_id: Number(this.cboLoaiHinhTB.value) }).then((response) => {
            if (response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000') {
              ds = response.data.data
              if (ds_tien_khoanmuc && ds_tien_khoanmuc.length > 0)
                for (var item of ds) {
                  var items = ds_tien_khoanmuc.filter((x) => x.khoanmuctt_id == item.khoanmuctt_id)
                  if (items && items.length > 0) {
                    item.tien = items[0].tien
                    item.vat = items[0].vat
                    item.tongtien = item.tien + item.vat
                  }
                }
            }
          })
          this.popupComponentData = {
            ds: ds,
            cols: [
              { fieldName: 'tentat_kmtt', headerText: 'Khoản mục', allowFiltering: false, allowSorting: false, isPrimaryKey: true },
              { fieldName: 'tongtien', textAlign: 'right', allowEditing: true, editType: 'numericedit', format: 'N', customAttributes: { class: 'tongtien' }, headerText: 'Tổng tiền', allowFiltering: false, allowSorting: false, edit: 'tongtienParam' },
              { fieldName: 'tien', textAlign: 'right', allowEditing: true, editType: 'numericedit', format: 'N', customAttributes: { class: 'cuoclapdat' }, headerText: 'Tiền', allowFiltering: false, allowSorting: false, edit: 'tienParam' },
              { fieldName: 'vat', textAlign: 'right', format: 'N', customAttributes: { class: 'vatlapdat' }, headerText: 'Vat', allowFiltering: false, allowSorting: false },
              { fieldName: 'tyle_vat', textAlign: 'right', format: 'N', headerText: 'Tỷ lệ vat', allowFiltering: false, allowSorting: false }
            ],
            keycol: 'khoanmuctt_id',
            allowExit: false,
            tieude: 'Chi tiết tiền khoản mục',
            tieudebang: 'Chi tiết khoản mục thanh toán',
            vkieuld_id: Number(this.cboKieuLD.value),
            vloaihd_id: LoaiHopDong.GIAHAN,
            vloaitb_id: Number(this.cboLoaiHinhTB.value),
            ds_tien_caidat: ds_tien_khoanmuc,
            m_dsTienCTKM: this.LowerCasePropertyList(this.m_dsTienCTKM)
          }
          this.popupComponentHeader = ''
          this.popupComponentName = 'popupCT_TienKM'
          this.popupComponent = () => import('@/modules/contract/setup/InstallNewSubs/Popups/popupCT_TienKM.vue')
          this.Popup('popupComponents')
        } else {
          await api_installnewsubs.post_lay_khoanmuc_tt_theo_loaihd(this.axios, { vloaihd_id: LoaiHopDong.GIAHAN }).then((response) => {
            if (response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000') {
              ds = response.data.data
              if (ds_tien_khoanmuc && ds_tien_khoanmuc.length > 0)
                for (var item of ds) {
                  var items = ds_tien_khoanmuc.filter((x) => x.khoanmuctt_id == item.khoanmuctt_id)
                  if (items && items.length > 0) {
                    item.tien = items[0].tien ? items[0].tien : 0
                    item.vat = items[0].vat ? items[0].vat : 0
                    item.tongtien = item.tien + item.vat
                  }
                }
            }
          })
          this.popupComponentData = {
            ds: ds,
            cols: [
              { fieldName: 'tentat_kmtt', headerText: 'Khoản mục', allowFiltering: false, allowSorting: false, isPrimaryKey: true },
              { fieldName: 'tongtien', textAlign: 'right', allowEditing: true, editType: 'numericedit', format: 'N', customAttributes: { class: 'tongtien' }, headerText: 'Tổng tiền', allowFiltering: false, allowSorting: false, edit: 'tongtienParam' },
              { fieldName: 'tien', textAlign: 'right', allowEditing: true, editType: 'numericedit', format: 'N', customAttributes: { class: 'cuoclapdat' }, headerText: 'Tiền', allowFiltering: false, allowSorting: false, edit: 'tienParam' },
              { fieldName: 'vat', textAlign: 'right', format: 'N', customAttributes: { class: 'vatlapdat' }, headerText: 'Vat', allowFiltering: false, allowSorting: false }
            ],
            keycol: 'khoanmuctt_id',
            allowExit: false,
            tieude: 'Chi tiết tiền khoản mục',
            tieudebang: 'Chi tiết khoản mục thanh toán'
          }
          this.popupComponentHeader = ''
          this.popupComponentName = 'popupTienKM'
          this.popupComponent = () => import('@/modules/contract/setup/InstallNewSubs/Popups/popupTienKM.vue')
          this.Popup('popupComponents')
        }
        console.log('popupTienKM',this.popupComponentData)
      } catch (e) {
        console.log(e)
      }
    },
    Popup(modalId) {
      this.$bvModal.show(modalId)
    },
    ClosePopup(modalId) {
      this.$bvModal.hide(modalId)
      this.popupComponent = null
    },
    async popupClosed(val) {
      if (!val) {
        this.ClosePopup('popupComponents')
        return
      }
      this.loading(true)
      try {
        console.log('popupClosed', this.popupComponentName, val)
        switch (this.popupComponentName) {
          case 'popupCTV':
            this.txtCTV = val.ten_ht
            this.ctv_id = val.nhanvien_id
            // this.loainv_ctv_id = val.loainv_id;
            // this.donvi_ctv_id = val.donvi_id;
            await this.HienThi_PBH_KBH()
            break
          case 'popupNGT':
            this.txtNguoiGT = val.ten_ht
            this.nguoigt_id = val.nhanvien_id
            await this.HienThi_PBH_KBH()
            break
          case 'popup-ChonKhuyenMai':
            // this.rightForm.cboTraTruoc = val.khuyenmai.khuyenmai_id;
            if (val && val.khuyenmai) {
              if (this.loaikm == Loai_KM.KhuyenMai) {
                this.cboKhuyenMai.value = val.khuyenmai.khuyenmai_id
              } else {
                this.cboTraTruoc.value = val.khuyenmai.khuyenmai_id
              }
            } else {
              this.ShowError('Không có khuyến mãi được chọn')
            }

            break
          case 'popup-DangkychitietkhuyenmaiTT':
            if (this.loaikm == Loai_KM.KhuyenMai) {
              this.themmoi_tienkm = true
              if (val.dsKM_HDTB && val.dsKM_HDTB.length > 0) {
                var check_km = this.ds_tien_khoanmuc.findIndex(e => e['KHOANMUCTT_ID'].toString() == KHOANMUC_TT.KMTT_DATCOC_KM.toString())
                if (check_km == -1) {
                  this.$toast.error(`Không có khoản mục thanh toán ${KHOANMUC_TT.KMTT_DATCOC_KM} - Đặt cọc khuyến mại. Bạn hãy liên hệ admin để xử lý!` )
                }
                this.dsKM_HDTB = this.LowerCasePropertyList(val.dsKM_HDTB)
                this.dtiendatcoc_km = Math.round((val.tien_datcoc * 10) / 11)
                this.dvatdatcoc_km = val.tien_datcoc - this.dtiendatcoc_km
              } else {
                this.dsKM_HDTB = []
                this.dtiendatcoc_km = 0
                this.dvatdatcoc_km = 0
              }
            } else {
              this.themmoi_tienkm = true
              if (val.dsDC_HDTB && val.dsDC_HDTB.length > 0) {
                var check_tt = this.ds_tien_khoanmuc.findIndex(e => e['KHOANMUCTT_ID'].toString() == KHOANMUC_TT.KMTT_DATCOC.toString())
                if (check_tt == -1) {
                  this.$toast.error(`Không có khoản mục thanh toán ${KHOANMUC_TT.KMTT_DATCOC} - Đặt cọc trả trước. Bạn hãy liên hệ admin để xử lý!` )
                }
                this.dsDC_HDTB = this.LowerCasePropertyList(val.dsDC_HDTB)
                //Them CT_TienKM
                if (this.thamsomacdinh['HT_KMTT'] != 1) {
                  this.dtiendatcoc_tt = Math.round((val.tien_datcoc_tt * 10) / 11)
                  this.dvatdatcoc_tt = val.tien_datcoc_tt - this.dtiendatcoc_tt
                } else {
                  if (val.m_dsTienCTKM && val.m_dsTienCTKM.length > 0) {
                    if (!this.m_dsTienCTKM) this.m_dsTienCTKM = []
                    this.m_dsTienCTKM = this.m_dsTienCTKM.filter((x) => x.kieu != 1)
                    if (this.m_dsTienCTKM) this.m_dsTienCTKM = this.m_dsTienCTKM.concat(val.m_dsTienCTKM)
                  }
                  this.dtiendatcoc_tt = val.tien_datcoc_tt
                  this.dvatdatcoc_tt = val.vat_datcoc_tt
                }
              } else {
                this.dsDC_HDTB = []
                if (this.dsDC_HDTB.length == 0) {
                  this.dtiendatcoc_tt = 0
                  this.dvatdatcoc_tt = 0
                }
              }
            }
            await this.HT_ThietBi()
            await this.HT_DichVuGT()
            await this.HienThiTienKhuyenMaiLapDat() //dtienkmld, dvatkmld, dtiendatcoc_km, dtienkmdc, dvatkmdc, dtiendatcoc_tt);
            await this.LayTongTien_HDTB()
            break
          case ('popupTienKM', 'popupCT_TienKM'):
            this.ds_tien_khoanmuc = this.UpperCasePropertyList(val)
            this.themmoi_tienkm = true
            this.kmtt_id = -1
            this.tien_kmtt = 0
            this.tien_vat = 0
            if (this.ds_tien_khoanmuc.length > 0) {
              for (var i = 0; i < this.ds_tien_khoanmuc.length; i++) {
                if (this.ds_tien_khoanmuc[i]['KHOANMUCTT_ID'].toString() == KHOANMUC_TT.KMTT_DATMOI.toString()) {
                  this.dtienld = Number(this.ds_tien_khoanmuc[i]['TIEN'].toString())
                  this.dvatld = Number(this.ds_tien_khoanmuc[i]['VAT'].toString())
                } else if (this.ds_tien_khoanmuc[i]['KHOANMUCTT_ID'].toString() == KHOANMUC_TT.KMTT_CAIDAT.toString()) {
                  this.dtiencd = Number(this.ds_tien_khoanmuc[i]['TIEN'].toString())
                  this.dvatcd = Number(this.ds_tien_khoanmuc[i]['VAT'].toString())
                } else if (this.ds_tien_khoanmuc[i]['KHOANMUCTT_ID'].toString() == KHOANMUC_TT.KMTT_DICHVU.toString()) {
                  this.dtien_dv = Number(this.ds_tien_khoanmuc[i]['TIEN'].toString())
                  this.dvat_dv = Number(this.ds_tien_khoanmuc[i]['VAT'].toString())
                } else if (this.ds_tien_khoanmuc[i]['KHOANMUCTT_ID'].toString() == KHOANMUC_TT.KMTT_MUATHIETBI.toString()) {
                  this.dtientbi = Number(this.ds_tien_khoanmuc[i]['TIEN'].toString())
                  this.dvattbi = Number(this.ds_tien_khoanmuc[i]['VAT'].toString())
                } else if (this.ds_tien_khoanmuc[i]['KHOANMUCTT_ID'].toString() == KHOANMUC_TT.KMTT_KM_LAPDAT.toString()) {
                  this.dtienkmld = Number(this.ds_tien_khoanmuc[i]['TIEN'].toString())
                  this.dvatkmld = Number(this.ds_tien_khoanmuc[i]['VAT'].toString())
                } else if (this.ds_tien_khoanmuc[i]['KHOANMUCTT_ID'].toString() == KHOANMUC_TT.KMTT_KM_DATCOC.toString()) {
                  this.dtienkmdc = Number(this.ds_tien_khoanmuc[i]['TIEN'].toString())
                  this.dvatkmdc = Number(this.ds_tien_khoanmuc[i]['VAT'].toString())
                  if (this.dtienkmdc > this.dtienkmld) {
                    this.dtienkmld = this.dtienkmdc
                    this.dvatkmld = this.dvatkmdc
                  }
                } else if (this.ds_tien_khoanmuc[i]['KHOANMUCTT_ID'].toString() == KHOANMUC_TT.KMTT_KM_THIETBI.toString()) {
                  this.dtien_kmtb = Number(this.ds_tien_khoanmuc[i]['TIEN'].toString())
                  this.dvat_kmtb = Number(this.ds_tien_khoanmuc[i]['VAT'].toString())
                } else if (this.ds_tien_khoanmuc[i]['KHOANMUCTT_ID'].toString() == KHOANMUC_TT.KMTT_DATCOC.toString()) {
                  this.dtiendatcoc_tt = Number(this.ds_tien_khoanmuc[i]['TIEN'].toString())
                  this.dvatdatcoc_tt = Number(this.ds_tien_khoanmuc[i]['VAT'].toString())
                } else if (this.ds_tien_khoanmuc[i]['KHOANMUCTT_ID'].toString() == KHOANMUC_TT.KMTT_DATCOC_KM.toString()) {
                  this.dtiendatcoc_km = Number(this.ds_tien_khoanmuc[i]['TIEN'].toString())
                  this.dvatdatcoc_km = Number(this.ds_tien_khoanmuc[i]['VAT'].toString())
                } else {
                  this.kmtt_id = Number(this.ds_tien_khoanmuc[i]['KHOANMUCTT_ID'].toString())
                  this.tien_kmtt = Number(this.ds_tien_khoanmuc[i]['TIEN'].toString())
                  this.tien_vat = Number(this.ds_tien_khoanmuc[i]['VAT'].toString())
                }
              }
            }
            await this.HT_ThietBi()
            await this.HT_DichVuGT()
            await this.HienThiTienKhuyenMaiLapDat() //dtienkmld, dvatkmld, dtiendatcoc_km, dtienkmdc, dvatkmdc, dtiendatcoc_tt);
            await this.LayTongTien_HDTB()
            break
          case 'popupServiceAdds':
            console.log('popupServiceAdds')
            this.m_dsThueBao_DichVu = val
            this.themmoi_tienkm = true
            // await this.HT_ThietBi()
            await this.HT_DichVuGT()
            // await this.HienThiTienKhuyenMaiLapDat() //dtienkmld, dvatkmld, dtiendatcoc_km, dtienkmdc, dvatkmdc, dtiendatcoc_tt);
            await this.LayTongTien_HDTB()
            break
          case 'popupBuyDevices':
            this.dsCTM_TBI = val
            this.themmoi_tienkm = true
            await this.HT_ThietBi()
            // await this.HT_DichVuGT()
            // await this.HienThiTienKhuyenMaiLapDat() //dtienkmld, dvatkmld, dtiendatcoc_km, dtienkmdc, dvatkmdc, dtiendatcoc_tt);
            await this.LayTongTien_HDTB()
            break
          case 'popupThongTinTBBS':
            this._hdtbsub = val
            break
          case 'popupChonSo_CNTT':
            this.dycDVCNTT.filter((e) => e.sFieldName == 'USERNAME')[0].sValue = val.somay
            break
          case 'popupDSTBCungDoiCapCNTT':
            console.log(val.MA_TB)
            this.dycDVCNTT.filter((e) => e.sFieldName == 'MATB_TN')[0].sValue = val.MA_TB
            break

        }
      } catch (ex) {
        if (ex.data && ex.data.message_detail) {
          this.$toast.error('' + ex.data.message_detail)
        } else {
          this.$toast.error('' + ex)
        }
      } finally {
        this.ClosePopup('popupComponents')
        this.loading(false)
      }
    },
    GET_KEYS: async function(key) {
      var input = {
        keyname: key
      }
      let response = await api.getKey(this.axios, input)
      if (response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
        return Number(response.data.data)
      } else {
        this.$toast.error('Có lỗi trong quá trình tạo key mới')
        return
      }
    },
    ChuanHoaTen(ten) {
      if (!ten) return ''
      var ten_new = ''
      var str_con = ten.trim()
      var str_xl = ''
      var k = 0
      var found = true
      if (str_con.length < 1) return
      str_con = str_con.replaceAll(/\s+/g, ' ')
      try {
        while (found) {
          k = str_con.indexOf(' ')
          if (k > 0) {
            str_xl = str_con.substring(0, k)
            str_con = str_con.substring(k + 1)
            str_xl = this.ChuyenChuHoa(str_xl)
            if (ten_new != '') ten_new += ' ' + str_xl
            else ten_new = str_xl
            found = true
          } else found = false
        }
        if (str_con != '') {
          str_xl = str_con
          str_xl = this.ChuyenChuHoa(str_xl)
          if (ten_new != '') ten_new = ten_new + ' ' + str_xl
          else ten_new = str_xl
        }
        return ten_new
      } catch (ex) {
        return ten
      }
    },
    LowerCasePropertyList(obj) {
      return obj.map(function(item) {
        for (var key in item) {
          var upper = key.toLowerCase()
          // check if it already wasn't uppercase
          if (upper !== key) {
            item[upper] = item[key]
            delete item[key]
          }
        }
        return item
      })
    },
    UpperCasePropertyList(obj) {
      return obj.map(function(item) {
        for (var key in item) {
          var upper = key.toUpperCase()
          // check if it already wasn't uppercase
          if (upper !== key) {
            item[upper] = item[key]
            delete item[key]
          }
        }
        return item
      })
    },
    numberWithCommas(n) {
      var parts = n.toString().split('.')
      return parts[0].replace(/\B(?=(\d{3})+(?!\d))/g, ',') + (parts[1] ? '.' + parts[1] : '')
    },
    GetData: function(response) {
      if (response.data && response.data.error_code == 'BSS-00000000') {
        return response.data.data
      } else {
        // console.log(response.data.message);
        // this.ShowError(response.data.message);
      }
      return []
    },
    ShowSuccess: function(msg) {
      this.$toast.success(msg)
    },
    ShowError: function(msg) {
      this.$toast.error(msg)
    },
    async frmGiaHanDVCNTT_Load() {
      this.tt_nd = {
        phanvung_id: Number(await this.$root.token.getPhanVungID()),
        donvi_id: Number(await this.$root.token.getDonViID()),
        nhanvien_id: Number(await this.$root.token.getNhanVienID()),
        ip_cn: await this.$root.token.getIP(),
        may_cn: await this.$root.token.getMachine(),
        nguoi_cn: await this.$root.token.getUserName()
      }
      await this.NAP_DS_COMBO()

      var ma_tb = this.$route.query.ma_tb ? this.$route.query.ma_tb : ''
      var dichvuvt_id = this.$route.query.dichvuvt_id ? Number(this.$route.query.dichvuvt_id) : 0
      this.chuyen_ct = this.$route.query.chuyen_ct ? true : false

      if (this.chuyen_ct) {
        await this.ChuyenChinhThuc(dichvuvt_id, ma_tb)
      }
      this.THAOTAC_NUT(3)
      this.button.tsbtnGiaoPhieu.Visible = false
      this.cboDichVuVT.value = this.ds_dichvuvt[0].DICHVUVT_ID
      this.cboKieuLD.value = this.ds_kieuld[0].KIEULD_ID
      await this.loaihinhtb_loaidv(this.cboDichVuVT.value)
      this.cboLoaiHinhTB.value = this.ds_loaihinhtb[0].loaitb_id
    },

    overlayClick() {
      this.$refs.generalDialog.hide()
    },

    async fn_lay_tt_hdtb_sub () {
      try {
        let data = await lay_tt_hdtb_sub(this.hdtb_id)
        console.log('lay_tt_hdtb_sub')
        console.log(data)
        if(data.length > 0) {
          this.dsHDTB_SUB = this.UpperCasePropertyList(data);
          this._hdtbsub = this.UpperCasePropertyList(data)[0];
        }

        console.log(this._hdtbsub)
      }
      catch (e ){
        console.log(e)
      }
    },

    async btnDongBoEdu_Click() {
      try {
        this.loading(true)
        var nhapmoi = !this.button.btnNhapMoi.Enabled

        var filter_lisence = this.dycDVCNTT.filter((e) => e.sFieldName == 'SL_MAILING')
        var filter_tocdo = this.dycDVCNTT.filter((e) => e.sFieldName == 'TOCDO_ID')
        var lisence_new = filter_lisence.length > 0 ? Number(filter_lisence[0].sValue) : 0
        var tocdo_id_new = filter_tocdo.length > 0 ? Number(filter_tocdo[0].sValue) : 0

        var para = {
          MA_TB: this.txtMaTB.value,
          LOAITB_ID: Number(this.cboLoaiHinhTB.value),
          LISENCE_NEW: lisence_new,
          TOCDO_ID_NEW: tocdo_id_new,
          KIEULD_ID: Number(this.cboKieuLD.value),
          THUEBAO_ID: this.thuebao_id,
          GUI_API_HDDT: 1,
          MUCCUOCTB_ID: Number(this.cboMucCuocTB.value),
          HDTB_ID: this.hdtb_id,
          NGUOIGT_ID: this.nguoigt_id,
          CTV_ID: this.ctv_id,
          NHAPMOI: nhapmoi ? 1 : 2, //1: nhap moi, 2: cap nhat
          TOCDO_ID: this.tocdo_id,
          MUCCUOC_ID: this.muccuoc_id,
          DONVI_ID: this.tt_nd.donvi_id,
          KHACHHHANG_ID: this.khachhang_id,
          DICHVUVT_ID: Number(this.cboDichVuVT.value)
        }
        // console.log(para);
        var input = {
          ds_para: JSON.stringify(para),
          phanvung_id: this.tt_nd.phanvung_id
        }
        let response = await api.kt_va_lay_tt_capnhat(this.axios, input)
        if (response.data && response.data.error_code == 'BSS-00000000') {
          var data = JSON.parse(response.data.data)
          if (data.ERROR_CODE == '1') {
            // console.log(data.RESULT);
            // console.log(JSON.parse(data.RESULT.HDTB_CNTT));
            this.huonggiao_tn = 0
            this.quytrinh_id = 0
            this.nguoi_dd = data.RESULT.HD_KHACHHANG && data.RESULT.HD_KHACHHANG.NGUOI_DD ? data.RESULT.HD_KHACHHANG.NGUOI_DD : ''

            this.HDTB_CNTT = data.RESULT.HDTB_CNTT && data.RESULT.HDTB_CNTT.length > 0 ? JSON.parse(data.RESULT.HDTB_CNTT) : []
            if (nhapmoi) {
              this.huonggiao_tn = data.RESULT.HUONGGIAO_ID ? data.RESULT.HUONGGIAO_ID : 0
              this.quytrinh_id = data.RESULT.QUYTRINH_ID ? data.RESULT.QUYTRINH_ID : 0
              this['diachiTB'].DIACHI_ID = data.RESULT.DIACHI_ID ? data.RESULT.DIACHI_ID : 0
              this.hdtb_id = data.RESULT.HD_THUEBAO && data.RESULT.HD_THUEBAO.HDTB_ID ? data.RESULT.HD_THUEBAO.HDTB_ID : 0
              this.hdkh_id = data.RESULT.HD_KHACHHANG && data.RESULT.HD_KHACHHANG.HDKH_ID ? data.RESULT.HD_KHACHHANG.HDKH_ID : 0
              this.ma_gd = data.RESULT.HD_KHACHHANG && data.RESULT.HD_KHACHHANG.MA_GD ? data.RESULT.HD_KHACHHANG.MA_GD : ''
              this.check_loaitb_id = data.RESULT.CHECK_LOAIHINH_TB ? data.RESULT.CHECK_LOAIHINH_TB : 'NOK'
            }



            if (this.check_loaitb_id != 'OK') {
              return 'OK'
            }
            else {
              var filter_truong_id = this.dycDVCNTT.filter((e) => e.sFieldName == 'MA_PHU')
              const truong_id_new = filter_truong_id.length > 0 ? filter_truong_id[0].sValue.toString() : '0'
              if (truong_id_new == '0') {
                this.$toast.error('Hãy kiểm tra lại ID trường')
                return 'Hãy kiểm tra lại ID trường'
              }
              //call api
              const data_tracuu = {
                phanloai: data.RESULT.MA_EU,
                truongid: truong_id_new
              }
              let res_edu = await api.Get_Data_TraCuu(this.axios, data_tracuu)
              //console.log(res_edu)
              // if (res_edu.data.errorCode != 0 && res_edu.data.faultCode != 'SUCCESS') {
              //   this.$toast.error('Đã có lỗi : ' + res_edu.data.faultCode.toString())
              //   return 'Đã có lỗi xảy ra : ' + res_edu.data.faultCode.toString()
              // }
              const data_faultString = JSON.parse(res_edu.data.faultString)
              // if (!data_faultString.success) {
              //   this.$toast.error('Có lỗi: ' + data_faultString.msg)
              //   return 'Có lỗi :' + data_faultString.msg
              // }
              const data_check = {
                kieuld_id: Number(this.cboKieuLD.value),
                thuebao_id: this.thuebao_id,
                loaitb_id: Number(this.cboLoaiHinhTB.value),
                tocdo_id: tocdo_id_new,
                truong_id: truong_id_new,
                start_date: data_faultString.data.start_date,
                end_date: data_faultString.data.end_date
              }
              console.log(data_check)

              let check = false
              for(let i = 1; i< 5; i++) {
                let res_check = await api.Check_Data_Edu(this.axios, data_check)
                if (res_check.data.error == '200' && res_check.data.error_code == 'BSS-00000000' && res_check.data.data == 'OK') {
                  this.$toast.success('Cập nhật thành công!')
                  check = true
                  break
                }
              }
              if (!check) {
                this.$toast.error('Cập nhật không thành công!')
              }
            }
          } else {
            this.ShowError(data.MESSAGE == 'OK' ? 'Lỗi kiểm tra thông tin cập nhật' : data.MESSAGE)
            return data.MESSAGE == 'OK' ? 'Lỗi kiểm tra thông tin cập nhật' : data.MESSAGE
          }
        } else {
          this.ShowError('Lỗi kiểm tra thông tin cập nhật ' + response.data.message_detail)
          return 'error'
        }
      } catch (ex) {
        console.log(ex)
        this.ShowError('Lỗi đồng bộ EDU')
        return 'error'
      }
      finally {
        this.loading(false)
      }
    },

    frmNhapDSThanhVienNgoaiMang_close() {
      // this.$refs.frmNhapDSThanhVienNgoaiMang.hide()
      // this.$refs.frmNhapDSThanhVienNgoaiMang.close()
    },
    closeFormDynamic () {
      this.isShowDynamicPopup = false
      this.popupComponent = null;
    }
  },
  async mounted() {
    this.loading(true)
    try {
      await Promise.all([this.frmGiaHanDVCNTT_Load()])

      this.loading(false)
    } catch (ex) {
      this.loading(false)
      console.log(ex)
    }
  },
  computed: {
    cp_TongTien: {
      get() {
        var tmp = Math.round(Number(this.txtTongTien.value))
        var value = this.txtTongTien.value ? this.numberWithCommas(tmp.toString()) : '0'
        return value
      },
      set(value) {
        const replaced = value.replaceAll(',', '')
        this.txtTongTien.value = Number(replaced)
      }
    },
    cp_TongVat: {
      get() {
        var tmp = Math.round(Number(this.txtTongVat.value))
        var value = this.txtTongVat.value ? this.numberWithCommas(tmp.toString()) : '0'
        return value
      },
      set(value) {
        const replaced = value.replaceAll(',', '')
        this.txtTongVat.value = Number(replaced)
      }
    },
    cp_sothangnew: {
      get() {
        if (this.dycDVCNTT.length > 0) {
          var filter = this.dycDVCNTT.filter((e) => e.sFieldName == 'SOTHANG_NEW')
          if (filter.length > 0) return Number(filter[0].sValue)
          else return 0
        } else return 0
      },
      set(value) {
        if (this.dycDVCNTT.length > 0) {
          for (var i = 0; i < this.dycDVCNTT.length; i++) {
            if (this.dycDVCNTT[i].sFieldName == 'SOTHANG_NEW') this.dycDVCNTT[i].sValue = Number(value)
          }
        }
      }
    },
    cp_sothang: {
      get() {
        if (this.dycDVCNTT.length > 0) {
          var filter = this.dycDVCNTT.filter((e) => e.sFieldName == 'SO_THANG')
          if (filter.length > 0) return Number(filter[0].sValue)
          else return 0
        } else return 0
      },
      set(value) {
        if (this.dycDVCNTT.length > 0) {
          for (var i = 0; i < this.dycDVCNTT.length; i++) {
            if (this.dycDVCNTT[i].sFieldName == 'SO_THANG')
            this.dycDVCNTT[i].sValue = Number(value)
          }
        }
      }
    },
    cp_ngaybd: {
      get() {
        if (this.dycDVCNTT.length > 0) {
          var filter = this.dycDVCNTT.filter((e) => e.sFieldName == 'NGAY_BD')
          if (filter.length > 0) return filter[0].sValue
          else return ''
        } else return ''
      },
      set(value) {
        if (this.dycDVCNTT.length > 0) {
          for (var i = 0; i < this.dycDVCNTT.length; i++) {
            if (this.dycDVCNTT[i].sFieldName == 'NGAY_BD') this.dycDVCNTT[i].sValue = value
          }
        }
      }
    },
    cp_ngaykt: {
      get() {
        if (this.dycDVCNTT.length > 0) {
          var filter = this.dycDVCNTT.filter((e) => e.sFieldName == 'NGAY_KT')
          if (filter.length > 0) return filter[0].sValue
          else return ''
        } else return ''
      },
      set(value) {
        if (this.dycDVCNTT.length > 0) {
          for (var i = 0; i < this.dycDVCNTT.length; i++) {
            if (this.dycDVCNTT[i].sFieldName == 'NGAY_KT') this.dycDVCNTT[i].sValue = value
          }
        }
      }
    },
    cp_tongsond: {
      get() {
        if (this.dycDVCNTT.length > 0) {
          var filter = this.dycDVCNTT.filter((e) => e.sFieldName == 'TONGSO_ND')
          if (filter.length > 0) return Number(filter[0].sValue)
          else return 0
        } else return 0
      },
      set(value) {
        if (this.dycDVCNTT.length > 0) {
          for (var i = 0; i < this.dycDVCNTT.length; i++) {
            if (this.dycDVCNTT[i].sFieldName == 'TONGSO_ND') this.dycDVCNTT[i].sValue = Number(value)
          }
        }
      }
    },

    cp_labelid: {
      get() {
        if (this.dycDVCNTT.length > 0) {
          var filter = this.dycDVCNTT.filter((e) => e.sFieldName == 'LABEL_ID')
          if (filter.length > 0) return Number(filter[0].sValue)
          else return 0
        } else return 0
      },
      set(value) {
        if (this.dycDVCNTT.length > 0) {
          for (var i = 0; i < this.dycDVCNTT.length; i++) {
            if (this.dycDVCNTT[i].sFieldName == 'LABEL_ID') this.dycDVCNTT[i].sValue = Number(value)
          }
        }
      }
    },
    cp_tocdoid: {
      get() {
        if (this.dycDVCNTT.length > 0) {
          var filter = this.dycDVCNTT.filter((e) => e.sFieldName == 'TOCDO_ID')
          if (filter.length > 0) return Number(filter[0].sValue)
          else return 0
        } else return 0
      },
      set(value) {
        if (this.dycDVCNTT.length > 0) {
          for (var i = 0; i < this.dycDVCNTT.length; i++) {
            if (this.dycDVCNTT[i].sFieldName == 'TOCDO_ID') this.dycDVCNTT[i].sValue = Number(value)
          }
        }
      }
    },
    cp_daily: {
      get() {
        if (this.dycDVCNTT.length > 0) {
          var filter = this.dycDVCNTT.filter((e) => e.sFieldName == 'DAILY')
          if (filter.length > 0) return Number(filter[0].sValue)
          else return 0
        } else return 0
      },
      set(value) {
        if (this.dycDVCNTT.length > 0) {
          for (var i = 0; i < this.dycDVCNTT.length; i++) {
            if (this.dycDVCNTT[i].sFieldName == 'DAILY') this.dycDVCNTT[i].sValue = Number(value)
          }
        }
      }
    },
    cp_phanloai_id: {
      get() {
        if (this.dycDVCNTT.length > 0) {
          var filter = this.dycDVCNTT.filter((e) => e.sFieldName == 'PHANLOAI_ID')
          if (filter.length > 0) return Number(filter[0].sValue)
          else return 0
        } else return 0
      },
      set(value) {
        if (this.dycDVCNTT.length > 0) {
          for (var i = 0; i < this.dycDVCNTT.length; i++) {
            if (this.dycDVCNTT[i].sFieldName == 'PHANLOAI_ID') this.dycDVCNTT[i].sValue = Number(value)
          }
        }
      }
    }
  },
  watch: {
    'cboDichVuVT.value'(val) {
      this.changecboDichVuVT(val)
    },
    'cboLoaiHinhTB.value'(val) {
      if (Number(val) == LoaiHinhTB.CA || Number(val) == LoaiHinhTB.SMART_CA) {
        this.txtTenKH.label = 'Tên KH'
        this.txtTenTB.label = 'Tên TB (CN)'
        this.button.btnThongTinTBBS.Text = 'Thông tin TB'
      } else if (Number(val) == 39) {
        this.txtTenKH.label = 'Tên khách hàng'
        this.txtTenTB.label = 'Tên thuê bao'
        this.button.btnThongTinTBBS.Text = 'Đăng ký thiết bị'
      } else {
        this.txtTenKH.label = 'Tên khách hàng'
        this.txtTenTB.label = 'Tên thuê bao'
        this.button.btnThongTinTBBS.Text = 'Thông tin TB'
      }
    },
    'cboKieuLD.value'(oldval, newval) {
      if (oldval != newval) {
        this.THAOTAC_NUT(1)
      }
    },
    'cboKieuLD.value'(val) {
      if (this.ds_kieuld && this.ds_kieuld.length > 0) {
        var index = this.ds_kieuld.findIndex((e) => Number(e.KIEULD_ID) == Number(val))
        if (index >= 0) {
          this.header.title = this.ds_kieuld[index].TEN_KIEULD
          this.header.list[this.header.list.length - 1].name = this.ds_kieuld[index].TEN_KIEULD
        }
      }
    },
    'chkKhuyenMai.Checked'(val) {
      this.chkKhuyenMai_CheckedChanged()
    },
    'chkTraTruoc.Checked'(val) {
      this.chkTraTruoc_CheckedChanged()
    },
    'txtTongTien.value'(val) {
      var vat = Math.round((Number(val) * 10) / 100)
      this.txtTongVat.value = vat
    },
    cp_sothangnew(val) {
      this.txtSOTHANG_NEW_Changed()
    },
    cp_sothang(val) {
      this.txtSOTHANG_Changed()
    },
    cp_ngaybd(val) {
      this.txtNGAYBD_changed()
    },
    cp_ngaykt(val) {
      this.txtNGAYKT_changed()
    },
    cp_tongsond(val) {
      this.txtTONGSO_ND_changed(val)
    },
    // cp_labelid(val) {
    //   this.txtLABEL_ID_changed(val)
    // },
    async cp_tocdoid(val) {
      await this.txtTOCDO_ID_Changed(val)
    },
    async cp_daily(val) {
      await this.txtDAILY_Changed(val)
    },
    async cp_phanloai_id(val) {
      // vloaitb_id_cbo
      await this.txtPHANLOAI_ID_changed(val)
    },
  }
}
</script>

<style>
.disabled {
  cursor: not-allowed;
  color: #666 !important;
}
.mx-datepicker {
  width: 100% !important;
}

.mx-input.disabled,
.mx-input:disabled {
  color: #666 !important;
}

.hoso-dientu .page-content {
  position: unset;
}

@media (min-width: 992px) {
  .modal .modal-huge {
    max-width: 95% !important;
    width: 100% !important;
  }
}
@media (min-width: 576px) {
  .modal .modal-huge {
    max-width: 95% !important;
    width: 100% !important;
  }
}
.custom-modal-height .modal-content {
  height: 75vh; /* Chiều cao bằng 75% màn hình */
}

.custom-modal-height .modal-body {
  overflow-y: auto; /* Kích hoạt thanh cuộn khi cần thiết */
}

</style>
