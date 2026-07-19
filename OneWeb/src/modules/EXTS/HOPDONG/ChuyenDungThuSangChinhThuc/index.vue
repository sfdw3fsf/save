<template src="./index.html"></template>

<script>
import breadcrumb from "@/modules/contract/setup/DeclareLandline/components/breadcrumb.vue";
import VueDateExt from "@/modules/contract/setup/DeclareLandline/components/VueDateExt";
import Select2Ext from "@/modules/contract/setup/DeclareLandline/components/Select2Ext";
import DataGridCustom from "@/modules/contract/setup/DeclareLandline/components/DataGridCustom";
import ModalChonDiaChi from '../../../CABMAN/XetDuyetPhanTuMangHoDanThuThap/ChonDiaChi/Modal'
import SearchContractModal from '@/modules/search/subscriber/SearchContract/SearchContract.vue'
import { LoaiHinhTB, DichVuVienThong, LoaiHopDong, TrangThaiHD } from '@/const/enums'
import moment from 'moment'
import ChonKhuyenMaiModal from '@/modules/contract/setup/ChangeSubsType/popups/ChonKhuyenMaiModal.vue'

export default {
  components: {
    breadcrumb,
    VueDateExt,
    Select2Ext,
    DataGridCustom,
    ModalChonDiaChi,
    SearchContractModal
  },
  data() {
    return {
      isStrictMode: false,
      form: {
        ma_gd: '',
        ngay_yeu_cau: '',
        ngay_hoan_thanh: '',
        ngay_ky_hd: '',
        ngay_hieu_luc: '',
        ngay_het_hieu_luc: '',
        ngay_hoan_thanh: ''
      },
      modalSearch: {
        loaihd_id: 0,
        tthd_id: 0,
        ngay_yc: moment().format('DD/MM/YYYY')
      },
      ds_tien_caidat: [],
      loaitb_id: 0,
      dichvuvt_id: 0,
      loaihd_id: 0,
      khuld_id: 0,
      apld_id: 0,
      phold_id: 0,
      phuongld_id: 0,
      tocdo_id: 0,
      muccuoc_id: 0,
      ctv_id: 0,
      nguoigt_id: 0,
      dsMucCuocTB: [],
      doituong_id: 0,
      ds_tien_km: [],
      dsMucCuoc: [],
      dsKieuLD: [],
      diachi: {},
      showThongTinThem: false,
      isStrictMode: true,
      diachi_flag: false,
      popupComponentTitle: "",
      popupComponent: null,
      popupComponentData: null,
      popupComponentName: '',
      popupComponentAttr: null,
      popupComponentEvents: {
        'form-close': 'popupClosed',
        'acceptChangeCTV': 'popupClosed',
        'acceptChangeNGT': 'popupClosed',
      },
    }
  },
  mounted() {
    this.frm_load();
  },
  methods: {
    btnLayMaGD_Click() {
      //this.tt_nd.ngay_cn
      this.$refs.searchContractModal.clearData()
      this.modalSearch.loaihd_id = 25
      this.modalSearch.tthd_id = TrangThaiHD.MOI
      this.$refs.searchContractModal.loadPopup()
      this.$refs.popupFrmTraCuuHopDong.show()
    },
    acceptSearchContract(item) {
      this.$refs.popupFrmTraCuuHopDong.hide()
      this.$refs.txtMaGiaoDich.value = item.hopdong.ma_gd ? item.hopdong.ma_gd : ''
      this.$refs.txtMaGiaoDich.focus()
      if (this.$refs.txtMaGiaoDich.value) {
        this.hienthiTheoMaGD()
      }
    },
    async changeCheckKhuyenMai() {
      this.khuyenmai.btnChiTietEnabled = this.khuyenmai.checkKhuyenMai
      this.khuyenmai.btnChon_KhuyenMai = this.khuyenmai.checkKhuyenMai
      //handle xử lý sau
    },
    Popup(modalId) {
      this.$bvModal.show(modalId)
    },
    ClosePopup(modalId) {
      this.$bvModal.hide(modalId)
      this.popupComponent = null
    },
    chkCTV_CheckedChanged() {
      if (this.$refs.chkCTV.checked) {
        // let f = this.$refs.frmNguoiGT;
        // f.nhomlnv_id = NHOM_LNV.CONGTACVIEN;
        // f.donvi_id = this.donvi_ctv_id;
        // f.loainv_id = this.loainv_ctv_id;
        // f.ctv_id = this.ctv_id;
        // f.ShowDialog();
        this.popupComponent = () => import('@/modules/contract/setup/ChangeContractOwner/popup/CongTacVien')
        this.popupComponentName = 'popupCTV'
        // SearchAccount.data.isPopup = true;
        this.Popup('popupComponents')
      }
    },
    chkNguoiGT_CheckedChanged() {
      if (this.$refs.chkNguoiGT.checked) {
        this.popupComponent = () => import('@/modules/contract/setup/ChangeContractOwner/popup/NguoiGioiThieu')
        this.popupComponentName = 'popupNGT'
        // SearchAccount.data.isPopup = true;
        this.Popup('popupComponents')
      }
    },
    clickChonKhuyenMai(loai) {
      this.loai = loai
      this.$refs.chonKhuyenMaiModal.showModal()
    },
    chiTietTraTruoc() {
      this.loaiCT = 2
      this.$emit('btnChiTietTCClick')
    },
    acceptChonKhuyenMai(khuyenmai_id) {
      if (this.loai == 1) {
        this.khuyenmai.khuyenmai_selected = khuyenmai_id
      } else if (this.loai == 2) {
        this.tratruoc.tratruoc_selected = khuyenmai_id
      }
    },
    async frm_load() {
      this.loading(true)
      let rsTTDM = await this.$root.context.get('/web-hopdong/thaydoithongtinkhuyenmai/lay_ds_cacloai_danhmuc_v2', {
        chuoi: 'DOITUONG|NGANHANG|DICHVU_VT|LOAIHINH_TB|KIEU_LD'
      });

      this.$refs.cboDichVuVT.HT_COMBOBOX(rsTTDM.data.dsDichVuVT, 'ten_dvvt', 'dichvuvt_id');
      this.$refs.cboDoiTuongTB.HT_COMBOBOX(rsTTDM.data.dsDoiTuong, 'ten_dt', 'doituong_id');
      await this.getLoaiHinhTB()
      await this.getDsKieuLD()
      await this.change_LoaiHinhTB()
      this.loading(false)
    },
    async getDsKieuLD() {
      return new Promise(async (resolve, reject) => {
        let rs = await this.$root.context.post('web-danhba/lapdatmoi/lay_danhmuc_lapmoi_tt_chung_v8', { vchuoi: 'KIEU_LD' })
        this.dsKieuLD = rs.data.kieu_ld
        resolve()
      })
    },
    async getLoaiHinhTB() {
      return new Promise(async (resolve, reject) => {
        let rs = await this.$root.context.get('web-hopdong/danhmuc/ds_loaitb/' + this.$refs.cboDichVuVT.value)
        this.$refs.cboLoaihinhTB.HT_COMBOBOX(rs.data, 'loaihinh_tb', 'loaitb_id')
        resolve()
      })
    },
    async change_LoaiHinhTB() {
      return new Promise(async (resolve) => {
        await this.getDsKieuLD()
        this.$refs.cboKieuLD.HT_COMBOBOX(this.dsKieuLD.filter(x => x.loaitb_id == this.$refs.cboLoaihinhTB.value), 'ten_kieuld', 'kieuld_id')
        resolve()
      })
    },
    btnDiaChiLD_Click() {
      this.showThongTinThem = true

      this.diachi = {}
      this.diachi.tinh_id = this.tinhld_id
      this.diachi.quan_id = this.quanld_id
      this.diachi.phuong_id = this.phuongld_id
      this.diachi.pho_id = this.phold_id
      this.diachi.dacdiem_id = this.dacdiemld_id
      this.diachi.ap_id = this.apld_id
      this.diachi.khu_id = this.khuld_id
      this.diachi.so_nha = this.sonhald

      this.diachi.block = this.block_ld
      this.diachi.tang = this.tang_ld
      this.diachi.sophong = this.sophong_ld
      this.diachi.motathem = this.motathem_ld

      this.diachi.diachi = this.txt_diachiLD.Value
      this.diachi_flag = 'DIACHI_LD'
      this.isStrictMode = true;
      this.$bvModal.show("popupDiaChi")
    },
    btnDiaChiTB_Click() {
      this.showThongTinThem = true
      this.diachi = {}
      this.diachi.tinh_id = this.tinhtb_id
      this.diachi.quan_id = this.quantb_id
      this.diachi.phuong_id = this.phuongtb_id
      this.diachi.pho_id = this.photb_id
      this.diachi.dacdiem_id = this.dacdiemtb_id
      this.diachi.ap_id = this.aptb_id
      this.diachi.khu_id = this.khutb_id
      this.diachi.so_nha = this.sonhatb

      this.diachi.block = this.block
      this.diachi.tang = this.tang
      this.diachi.sophong = this.phong
      this.diachi.motathem = this.mota

      this.diachi.diachi = this.$refs.txtDiaChiTB.value
      this.diachi_flag = 'DIACHI_TB'
      this.isStrictMode = true;
      this.$bvModal.show("popupDiaChi")
    },
    async getDiaChiTuFormDiaChi(data) {
      switch (this.diachi_flag) {
        case 'DIACHI_TB':
          this.tinhtb_id = data.tinh_id
          this.quantb_id = data.quan_id
          this.phuongtb_id = data.phuong_id
          this.photb_id = data.pho_id
          this.dacdiemtb_id = data.dacdiem_id
          this.aptb_id = data.ap_id
          this.khutb_id = data.khu_id
          this.sonhatb = data.so_nha

          this.block = data.block
          this.tang = data.tang
          this.phong = data.sophong
          this.mota = data.motathem

          this.txt_diachiTB.Value = data.diachimoi
          this.diachi_flag = null
          this.showThongTinThem = false

          this.$refs.txt_diachiTB.focus()
          break
      }
    },
    btnChon_KhuyenMai_Click: function () {
      if (this.$refs.cboKhuyenMai.dataSource.length == 0) return;
      var dulieu = {};
      dulieu.loai = 0; // 0: khuyến mại, 1: trả trước
      dulieu.dt = this.$refs.cboKhuyenMai.dataSource;
      this.popupComponentData = dulieu;
      this.popupComponent = () => import('../../../contract/setup/InstallNewSubs/Popups/popup-ChonKhuyenMai');
      // this.popupComponent = ()=>import('./Popups/popup-ChonKhuyenMai');
      this.popupComponentTitle = "Chọn khuyến mại/đặt cọc";
      this.popupComponentName = "popup-ChonKhuyenMai";
      this.$bvModal.show('popupComponents');
    },
    showModalComponent: function () {
    },
    popupClosed: async function (val) {
      try {
        this.loading(true);
        console.log(this.popupComponentName)
        switch (this.popupComponentName) {
          case 'popupCTV':
            if (val.ten_ht) {
              console.log(val, "jhagdjhsagd")
              this.$refs.txtCTV.value = val.ten_ht
              this.ctv_id = val.nhanvien_id
            }
            break
          case 'popupNGT':
            if (val.ten_ht) {
              this.$refs.txtNguoiGT.value = val.ten_ht
              this.nguoigt_id = val.nhanvien_id
            }
            break
          default:
            break;
        }
      } catch (error) {
        console.log(error)
      } finally {
        this.$bvModal.hide('popupComponents')
        this.loading(false);
      }
    },
    hienThiTTDanhBa: async function (somay) {
      try {
        this.ItemList = [];
        this.$root.context.post('/web-hopdong/thaydoithongtinkhuyenmai/lay_danhba_theo_matb', {
          maTb: 'ngxdanghung.fb.dng',
          dichVuVtId: 4,
          donViDlId: 0
        })
      }
      catch (error) {
        console.log(error);
      }
    },
    async hienthiTheoMaGD() {
      this.loading(true)
      try {
        let response = await this.$root.context.post("/web-hopdong/hoanthienhschuyenquyen/lay-ds-hopdong-theomagd", {
          IN_MA_GD: "DNG-TD/00046931",
          IN_LOAIHD_ID: 25,
          IN_TTHD_ID: 1,
        })

        let ds = response.data
        if (ds.length > 0) {
          await this.HienThiThongTin(ds[0])
        } else {
          this.$toast.error("Không tìm thấy thông tin!")
        }

      } catch (error) {
        console.log(error);
      }
      this.loading(false)
    },
    async hienthiTheoMaTB() {
      this.loading(true)
      try {
        let response = await this.$root.context.post('web-hopdong/hopdong/lay_ds_hopdong_theo_matb', {
          dichVuVtId: this.$refs.cboDichVuVT.value,
          donViDlId: await this.$root.token.getDonViID(),
          loaiHdId: 25,
          loaiTbId: 0,
          maTb: this.$refs.txtMaTB.value.trim(),
          ttHdId: TrangThaiHD.MOI
        })

        let ds = response.data
        if (ds.length > 0) {
          await this.HienThiThongTin(ds[0])
        } else {
          this.$toast.error("Không tìm thấy thông tin!")
        }
      } catch (err) {
        console.log(err)
      }
      this.loading(false)
    },
    async btnLayTT() {
      if(this.$refs.txtMaGiaoDich.value) {
        this.hienthiTheoMaGD()
      } else if(this.$refs.txtMaTB.value) {
        this.hienthiTheoMaTB()
      }

    },
    async btnGhiLai() {
      if(!this.hdkh_id) {
        return
      }
      this.loading(true)
      try {
        let response = await this.$root.context.post(`/web-hopdong/thaydoithongtinkhuyenmai/update_ngay_ht`, ({
          hdkh_id: this.hdkh_id,
          ngay_ht: this.ddmmyyyy(this.dtp_ngayHT.Value)
        }))
        this.$toast.success(response.data.KQ)
      } catch (err) {
        this.$toast.error("Lỗi" + err.message)
      }
      this.loading(false)
    },
    async btnHoanThien_Click() {
      if(!this.hdtb_id) {
        return
      }
      this.loading(true)
      try {
        let res1 = await this.axios.post('/web-hopdong/ds-doi-tam-dung-thanh-ly/sp_chuyen_hd_chinhthuc_v2', {
          p_phanvung_id: this.$root.token.getPhanVungID(),
          p_hdtb_id: this.hdtb_id,
          p_doituong_id: this.doituong_id,
          p_may_cn: await this.$root.token.getMachine(),
          p_nguoi_cn: await this.$root.token.getUserName(),
          p_ip_cn: await this.$root.token.getIP(),
          p_ngay_nt: this.$refs.dtNgayNT.value
        });
        if (res1.data.error == '200' && res2.data.error_code == 'BSS-00000000') {
          this.$toast.success("Hoàn thiện thành công")
        } else {
          this.$toast.warning(res1.data.message)
        }
      } catch (err) {
        this.$toast.error("Lỗi" + err.message)
      }
      this.loading(false)
    },
    btnHuyBo() {
        this.$refs.txtMaGiaoDich.value = ""
        this.$refs.dtpNgayLapHD.value = ""
        this.$refs.txtMaHopDong.value = ""
        this.$refs.txtMaKH.value = ""
        this.$refs.txtTenKH.value = ""
        this.$refs.txtDiaChiKH.value = ""
        this.$refs.txtMaTB.value = ""
        this.$refs.txtTrangThaiTB.value = ""
        this.$refs.txtTenTB.value = ""
        this.$refs.cboDoiTuongTB.value = ""
        this.$refs.txtDiaChiTB.value = ""
        this.$refs.txtDiaChiLD.value = ""
        this.$refs.txtGhiChu.value = ""
        this.doituong_id = 0
        this.loaitb_id = 0
        this.hdkh_id = 0
        this.loaihd_id = 0
    },
    async HienThiThongTin(formdata) {
      return new Promise(async resolve => {
        this.$refs.txtMaGiaoDich.value = formdata.ma_gd
        this.$refs.dtpNgayLapHD.value = formdata.ngay_lap_hd
        this.$refs.txtMaHopDong.value = formdata.ma_hd
        this.$refs.txtMaKH.value = formdata.ma_kh
        this.$refs.txtTenKH.value = formdata.ten_kh
        this.$refs.txtDiaChiKH.value = formdata.diachi_kh
        this.$refs.txtMaTB.value = formdata.ma_tb
        this.$refs.txtTrangThaiTB.value = formdata.trangthai_hd
        this.$refs.txtTenTB.value = formdata.ten_tb
        this.$refs.cboDoiTuongTB.value = formdata.doituong_id
        this.$refs.txtDiaChiTB.value = formdata.diachi_tb
        this.$refs.txtDiaChiLD.value = formdata.diachi_ld
        this.$refs.txtGhiChu.value = formdata.ghichu
        this.$refs.cboDichVuVT.value = formdata.dichvuvt_id
        this.doituong_id = formdata.doituong_id
        await this.getLoaiHinhTB()
        this.$refs.cboLoaihinhTB.value = formdata.loaitb_id
        this.loaitb_id = formdata.loaitb_id
        this.hdkh_id = formdata.hdkh_id
        this.$refs.dtNgayNT.value = formdata.ngay_ht
        await this.change_LoaiHinhTB()
        this.$refs.cboKieuLD.value = formdata.kieuld_id
        this.loaihd_id = formdata.loaihd_id
        if (formdata['nhanviengt_id']) {
          // this.chkNguoiGT_CheckedChanged();
          this.$refs.chkNguoiGT.checked = true
          this.nguoigt_id = Number(formdata['nhanviengt_id'])
          temp = await this.axios.post('/web-tratruoc/thongtin_tratruoc_dn/fn_tt_nhanvien', {
            param: this.nguoigt_id,
            type: 3
          })
          this.$refs.txtNguoiGT.value = temp.data.data
        } else {
          this.$refs.txtNguoiGT.value = ''
          this.$refs.chkNguoiGT.checked = false
        }

        if (formdata['ctv_id']) {
          this.nopopup = false
          this.$refs.chkCTV.checked = true
          this.ctv_id = Number(formdata['ctv_id'])
          temp = await this.axios.post('/web-tratruoc/thongtin_tratruoc_dn/fn_tt_nhanvien', {
            param: this.ctv_id,
            type: 3
          })
          this.$refs.txtCTV.value = temp.data.data
        } else {
          this.$refs.txtCTV.value = ''
          this.$refs.chkCTV.checked = false
        }

        await this.initDuLieukmtt()
        resolve()
      })
    },
    changeTongTien(event, index) {
      this.ds_tien_km[index].tongtien = event.target.value
    },
    changeTien(event, index) {
      this.ds_tien_km[index].tien = event.target.value
    },
    changeVat(event, index) {
      this.ds_tien_km[index].vat = event.target.value
    },
    handleBlur(event, kieu, index) {
      if (kieu == 1) {
        this.ds_tien_km[index].tien = (Math.round((Number(this.ds_tien_km[index].tongtien) * 10 / 11) * 100 + Number.EPSILON) / 100).toString()
        this.ds_tien_km[index].vat = (Math.round((Number(this.ds_tien_km[index].tongtien) / 11) * 100 + Number.EPSILON) / 100).toString()
      } else {
        this.ds_tien_km[index].vat = (Math.round((Number(this.ds_tien_km[index].tien) / 10) * 100 + Number.EPSILON) / 100).toString()
        this.ds_tien_km[index].tongtien = (Math.round((Number(this.ds_tien_km[index].tien) * 11 / 10) * 100 + Number.EPSILON) / 100).toString()
      }
    },
    isNumber(evt) {
      evt = (evt) ? evt : window.event;
      var charCode = (evt.which) ? evt.which : evt.keyCode;

      var isDot = evt.target.value.indexOf('.') == -1
      if (isDot) {
        if ((charCode > 31 && (charCode < 48 || charCode > 57)) && charCode !== 46) {
          evt.preventDefault()
        } else {
          return true
        }
      } else {
        if ((charCode > 31 && (charCode < 48 || charCode > 57))) {
          evt.preventDefault()
        } else {
          return true
        }
      }
    },
    async initDuLieukmtt() {
      return new Promise(async resolve => {
        await this.hienthi_giaodien_kmtt()
        await this.hienthi_tien_km()
        resolve()
      })
    },
    async hienthi_giaodien_kmtt() {
      //
      this.ds_tien_km = await this.lay_ds_tien_kmtt()
    },
    async hienthi_tien_km() {
      for (let i = 0; i < this.ds_tien_km.length; i++) {
        for (let j = 0; j < this.ds_tien_caidat.length; j++) {
          if (this.ds_tien_km[i].khoanmuctt_id.toString() == this.ds_tien_caidat[j].khoanmuctt_id.toString()) {
            this.ds_tien_km[i].tien = this.ds_tien_caidat[j].tien.toString()
            this.ds_tien_km[i].vat = this.ds_tien_caidat[j].vat.toString()
            this.ds_tien_km[i].tongtien = Number(this.ds_tien_caidat[j].tien) + Number(this.ds_tien_caidat[j].vat)
          }
        }
      }
    },
    async lay_ds_tien_kmtt() {
      try {
        this.loading(true)
        let response = await this.axios.post('/web-hopdong/lapdatmoi/lay_khoanmuc_tt_theo_loaihd', {
          vloaihd_id: this.loaihd_id
        })
        this.loading(false)
        if (response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
          return response.data.data
        } else {
          return []
        }
      } catch (e) {
        this.loading(false)
        return []
      }
    }
  },
};
</script>

<style>
.label-form {
  background: rgba(186, 231, 255, 0.5);
  padding: 10px;
  margin: 10px 0px;
  border-radius: 5px;
  font-weight: bold;
}

.checkbox-input-inline {
  display: flex;
  align-items: normal;
  gap: 3px;
}
</style>
