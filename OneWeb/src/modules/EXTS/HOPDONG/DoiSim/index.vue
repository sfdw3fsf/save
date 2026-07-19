<template src="./index.html">
</template>

<script>
import SearchContractModal from "@/modules/contract/setup/HandoverTicket/components/SearchContractModal.vue"
import SearchAccount from '@/modules/search/subscriber/SearchAccount'
import BssErrorMarker from '@/components/BssErrorMarker'
import BssRequiredMarker from '@/components/BssRequiredMarker'
import { required } from 'vuelidate/lib/validators'
import ccbsService from './ccbs-service'
export default {
  components: {
    BssErrorMarker,
    BssRequiredMarker,
    SearchContractModal,
    SearchAccount
  },
  validations: {
      txtMaHD: {
          Text: {
              required
          }
      },
      txtSoSimMoi: {
          Text: {
              required
          }
      },
      txtTenTB: {
          Text: {
              required
          }
      },
      txtMaKH: {
          Text: {
              required
          }
      },
      txtTenKH: {
          Text: {
              required
          }
      },
  },
  data() {
    return {
      cboDichVuVt: {
        DataSource: [],
        Value: null
      },
      cboLoaiHinhTB: {
        DataSource: [],
        Value: null
      },
      dsThamSoMacDinh: [],
      dsPhieu: [],
      btnNhapMoi: {
        enabled: false
      },
      btnGhiLai: {
        enabled: false
      },
      btnXoa: {
        enabled: false
      },
      btnHuyBo: {
        enabled: false
      },
      btnThanhToan: {
        enabled: false
      },
      TRANGBI: {
        KHACHHANG_TU_TRANGBI: 3
      },
      trangbi_id: null,
      hdkh_id: 0,
      hdtb_id: 0,
      thuebao_id: 0,
      doituong_id: 1,
      khachhang_id: 0,
      txtTongTienHD: {
        Text: 0
      },
      txtMaGD: {
        Text: ""
      },
      txtMaHD: {
        Text: ""
      },
      txtMaKH: {
        Text: ""
      },
      txtTenKH: {
        Text: ""
      },
      txtDiaChiKH: {
        Text: ""
      },
      dtpNgayLapHD: {
        EditValue: new Date()
      },
      dtpNgayYeuCau: {
        EditValue: new Date()
      },
      txtSoSimMoi: {
        Text: ""
      },
      txtTBBiLaySim: {
        Text: "",
        ForeColor: ""
      },
      txtLoai: {
        Text: ""
      },
      txtTinhTP: {
        Text: ""
      },
      ckeEsim: {
        Checked: false
      },
      ckeGoiDen: {
        Checked: false
      },
      ckeGoiDi: {
        Checked: false
      },
      txtTenTB: {
        Text: ""
      },
      txtDiaChiTB: {
        Text: ""
      },
      cboKieuLD: {
        DataSource: [],
        Value: null
      },
      txtTienLapMoi: {
        Text: "0"
      },
      txtVatLapmoi: {
        Text: "0"
      },
      txtGhiChu: {
        Text: ""
      },
      txtMaTB: {
        Text: ""
      },
      txtSoSimCu: {
        Text: ""
      },
      dgrThueBao: {
        DataSource: []
      },
      allowShowAreaTrangThai: true,
      DichVuVienThong: {
        GPHONE: 10,
        DI_DONG: 2,
        DD_TRATRUOC: 5,
        ADSL: 4
      },
      tttb: null,
      kh_cd: 0,
      ltb_adsl_id: 1,
      phuongld_id: 0,
      phold_id: 0,
      apld_id: 0,
      khuld_id: 0,
      tocdo_id: 0,
      LoaiHopDong: {
        THAY_DOI_SO_SIM: 12
      },
      KHOANMUC_TT: {
        KMTT_DOISIM: 6
      },
      TrangThaiHD: {
        MOI: 1,
        THANH_TOAN: 2,
        DA_THI_CONG_XONG: 5
      },
      vma_tinh: null,
      userInfo: null,
      sttSimMoi: null,
      donvi_id: null,
      KieuLapDat: {
        DOISIM_TRATRUOC: 170
      },
      LoaiHinhTB: {
        DIDONGTRASAU: 20,
        DIDONGTRATRUOC: 21,
        GPHONE: 32,
        GPHONE_TRATRUOC: 34
      },
      donvi_dl_id: null,
      DoiTuong: {
        TU_NHAN: 1
      },
      huonggiaotn_id: 0,
      quytrinhtn_id: 0
    }
  },
  computed: {
    allowSinhMaGdTheoDv() {
      return this.dsThamSoMacDinh.some(thamSo => {
        return thamSo.MA_TS == 'SINH_MA_GD_THEO_DV'
      })
    }
  },
  watch: {
    'cboLoaiHinhTB.Value': async function(value) {

      if (!value) {
        return
      }

      await this.lay_ds_kieu_ld(value)

      if (this.cboKieuLD.DataSource.length > 0) {
        this.cboKieuLD.Value = this.cboKieuLD.DataSource[0].kieuld_id
      }
    }
  },
  async mounted() {

    this.donvi_dl_id = this.$root.context.user.getProperty('donvi_dl_id').toString()
    this.vma_tinh = this.$root.token.getMaTinh()

    try {
      this.loading(true)
      this.userInfo = await this.layThongTinNguoiDung()
      await this.layDsThamSoMd()
      await this.layDsDichVuVt()

      if (this.cboDichVuVt.DataSource.length > 0) {

        this.cboDichVuVt.Value = this.cboDichVuVt.DataSource[0].DICHVUVT_ID

        await this.layDsLoaiHinhTb(this.cboDichVuVt.Value)

        if (this.cboLoaiHinhTB.DataSource.length > 0) {
          this.cboLoaiHinhTB.Value = this.cboLoaiHinhTB.DataSource[0].LOAITB_ID
        }
      }

      await this.hienThiDanhSach()

      if (!this.dgrThueBao.DataSource || this.dgrThueBao.DataSource.length == 0) {
        this.setButton(1)
      }
    } finally {
      this.loading(false)
    }
  },
  methods: {
    async xoa_hopdong(hdkh_id) {
      try {
        let response = await this.$root.context.post(`/web-hopdong/doisim/xoa_hopdong?hdkh_id=${hdkh_id}`)

        if (response.data != null) {
            return response.data
        }

        throw {response: { data: response}}
      } catch (err) {
        return null
      }
    },
    async okXoaHopDong() {
      try {
          this.loading(true)
          let kq = await this.xoa_hopdong(this.hdkh_id)

          if (!!kq && kq.kq == 1) {
            this.$refs['ref-modal-xacnhan-xoa'].hide()
            this.$root.toastSuccess('Xóa hợp đồng khách hàng thành công!')
            this.setButton(0)
            await this.hienThiDanhSach()
            return
          }

          throw 'Xóa hợp đồng khách hàng thất bại'
      } catch(err) {
          this.$root.toastError(err)
          return
      } finally {
          this.loading(false)
      }
    },
    btnXoa_Click() {

      if (!this.btnXoa.enabled) {
          return
      }

      if (!this.hdkh_id) {
          this.$root.toastError('Chưa chọn hợp đồng để xóa')
          return
      }

      this.$refs['ref-modal-xacnhan-xoa'].show()
    },
    async cboDichVuVT_SelectedValueChanged() {

      if (!this.cboDichVuVt.Value) {
        return
      }

      let dichvuvt_id = parseInt(this.cboDichVuVt.Value)

      try {
        this.loading(true)

        await this.layDsLoaiHinhTb(dichvuvt_id)

        if (this.cboLoaiHinhTB.DataSource.length > 0) {
          this.cboLoaiHinhTB.Value = this.cboLoaiHinhTB.DataSource[0].LOAITB_ID
        }

        this.setButton(0)
        await this.hienThiDanhSach()

        this.allowShowAreaTrangThai = dichvuvt_id == 2
      } finally {
        this.loading(false)
      }
    },
    async lay_ds_kieu_ld(loaitb_id) {
      try {
        let response = await this.$root.context.get(`/web-hopdong/doisim/lay_ds_kieu_ld?loaitb_id=${loaitb_id}`)

        if (response.error_code === 'BSS-00000000' && response.data != null) {
            this.cboKieuLD.DataSource = response.data
            return
        }

        throw {response: { data: response}}
      } catch (err) {
          this.cboKieuLD.DataSource = []
      }
    },
    acceptSearchAccount(item) {

        this.$refs['popupSearchAccount'].hide()

        if (!!item && !!item.ma_tb) {
          this.txtMaTB.Text = item.ma_tb
          this.onEnterInputMaTb()
        }
    },
    btnLayMaKh_Click() {
      this.$refs['popupSearchAccount'].show()
    },
    async cboKieuLD_SelectedValueChanged() {

      let kieuLd = parseInt(this.cboKieuLD.Value)

      if (kieuLd == this.KieuLapDat.DOISIM_TRATRUOC) {
        this.cboLoaiHinhTB.Value = this.LoaiHinhTB.DIDONGTRATRUOC
        this.doituong_id = this.DoiTuong.TU_NHAN
        this.donvi_id = this.donvi_dl_id
      }

      await this.layTienTheoKhoanMuc()
    },
    btnThanhToan_Click() {
      if (this.btnThanhToan.enabled) {
        this.$router.push({ path: "/contract/Payment", query: { magd: this.txtMaGD.Text }})
      }
    },
    async onSelectRowPhieuYeuCau(data) {

      if (!data) {
        return
      }

      try {
        this.loading(true)
        this.setButton(3)
        this.hienThiTTHopDongKH_1(data)
        await this.hienThiTTHopDongTB(data)

        //HTH
        if (parseInt(data["tthd_id"]) == this.TrangThaiHD.DA_THI_CONG_XONG) {
          this.setButton(4)
        }

        if (parseInt(data["tthd_id"]) == this.TrangThaiHD.MOI) {
          this.btnGhiLai.enabled = true
          this.btnXoa.enabled = true
        } else {
          this.btnGhiLai.enabled = false
          this.btnXoa.enabled = false
        }
      } finally {
        this.loading(false)
      }
    },
    async hienThiTTHopDongTB(data) {

      if (!data) {
        return
      }

      this.hdtb_id = data["hdtb_id"]

      if (!!data["thuebao_id"]) {
        this.thuebao_id = data["thuebao_id"]
      }

      this.txtMaTB.Text = data["ma_tb"]
      this.txtTenTB.Text = data["ten_tb"]
      this.txtDiaChiTB.Text = data["diachi_tb"]
      this.cboDichVuVt.Value = data["dichvuvt_id"]
      this.cboLoaiHinhTB.Value = data["loaitb_id"]
      this.cboKieuLD.Value = data["kieuld_id"]
      this.doituong_id = data["doituong_id"]
      this.txtSoSimCu.Text = data["sosim_cu"]
      this.txtSoSimMoi.Text = data["sosim_moi"]
      this.txtTongTienHD.Text = data["tongtien"]
      this.donvi_id = data["donvi_id"]

      await this.hienThiTrangThai(this.txtMaTB.Text)

      let dsCtkm = await this.lay_tien_hd_km_theo_hdtb(this.hdtb_id)

      if (!!dsCtkm && dsCtkm.length > 0) {
        this.hienThiCTTIEN_HD(dsCtkm)
      }
    },
    hienThiCTTIEN_HD(dsCtkm) {
      this.txtTienLapMoi.Text = dsCtkm[0]["tien"]
      this.txtVatLapmoi.Text = dsCtkm[0]["vat"]
    },
    async lay_tien_hd_km_theo_hdtb(hdtb_id) {
      try {
        let response = await this.$root.context.get(`/web-hopdong/hoanthien-hs-doisim/lay_tien_hd_km_theo_hdtb/${hdtb_id}`)

        if (response.data != null) {
            return response.data
        }

        throw {response: { data: response}}
      } catch (err) {
          return null
      }
    },
    acceptSearchContract(item) {
        this.txtMaGD.Text = item.ma_gd
        this.onEnterInputMaGd()
    },
    btnLayMaGD_Click() {
      this.$refs.popupSearchContract.showModal()
    },
    async onEnterInputSoSimMoi() {

      if (!this.txtSoSimMoi.Text) {
        this.$root.toastError("Số sim mới không để trống!")
        return
      }

      await this.kiemTraSoSimMoi(this.txtMaTB.Text, this.txtSoSimMoi.Text)
    },
    async kiemTraSoSimMoi(somay, soSim) {

      let vma_vung = ""

      try {
        if (parseInt(this.cboDichVuVt.Value) == this.DichVuVienThong.GPHONE) {
          vma_vung = this.userInfo.mavung_cd
          this.sttSimMoi = await ccbsService.tt_ts_get_tb_sim(vma_vung + somay, soSim, this.vma_tinh, this)
        } else {
          vma_vung = "84"
          this.sttSimMoi = await ccbsService.tt_ts_get_tb_esim(this.vma_tinh, soSim, vma_vung + somay, this)
          this.ckeEsim.Checked = parseInt(this.sttSimMoi[0]["ESIM"]) == 1
        }
      } catch(err) {
        this.$root.toastError('Có lỗi xảy ra khi kiểm tra số sim')
        return
      }

      switch (parseInt(this.sttSimMoi[0]["KQUA_CHK"]))
      {
        case 0:
          this.txtTBBiLaySim.Text = "Sim hợp lệ"
          this.txtTBBiLaySim.ForeColor = "blue"
          this.btnGhiLai.enabled = true
          break
        case 1:
          this.txtTBBiLaySim.Text = this.sttSimMoi[0]["TTIN_ADD"]
          this.txtTBBiLaySim.ForeColor = "red"
          this.btnGhiLai.enabled = false
          break
        case 2:
          this.txtTBBiLaySim.Text = "Sim không nằm trong dải sim hợp lệ của thuê bao"
          this.txtTBBiLaySim.ForeColor = "red"
          this.btnGhiLai.enabled = false
          break
        case 3:
          this.txtTBBiLaySim.Text = "Số sim mới không nằm trong kho sim"
          this.txtTBBiLaySim.ForeColor = "red"
          this.btnGhiLai.enabled = false
          break
        case 4:
          this.txtTBBiLaySim.Text = "Số sim mới chưa khởi tạo AC, fax yêu cầu về TTHTKH VINAPHONE để khởi tạo sim"
          this.txtTBBiLaySim.ForeColor = "red"
          this.btnGhiLai.enabled = false
          break
        case 5:
          this.txtTBBiLaySim.Text = "Số thuê bao chưa có ngày kích hoạt"
          this.txtTBBiLaySim.ForeColor = "red"
          this.btnGhiLai.enabled = false
          break
        case 6:
          this.txtTBBiLaySim.Text = "Sim đã bị hủy do CAN thuê bao"
          this.txtTBBiLaySim.ForeColor = "red"
          this.btnGhiLai.enabled = false
          break
        case 7:
          this.txtTBBiLaySim.Text = "Sim đã hủy do đã đổi sim"
          this.txtTBBiLaySim.ForeColor = "red"
          this.btnGhiLai.enabled = false
          break
        case 8:
          this.txtTBBiLaySim.Text = "Sim chưa được kích hoạt"
          this.txtTBBiLaySim.ForeColor = "red"
          this.btnGhiLai.enabled = false
          break
      }
    },
    async onEnterInputMaGd() {

      if (!this.txtMaGD.Text) {
        return
      }

      try {
        this.loading(true)

        let maGd = this.txtMaGD.Text
        this.clear()
        this.txtMaGD.Text = maGd

        let hopDong = await this.lay_ds_hopdong_theomagd({
          IN_MA_GD: this.txtMaGD.Text,
          IN_LOAIHD_ID: this.LoaiHopDong.THAY_DOI_SO_SIM,
          IN_TTHD_ID: this.TrangThaiHD.MOI,
          IN_DONVI_DL_ID: 0
        })

        if (!!hopDong) {
          await this.hienThiTTHopDongKH(hopDong)
          return
        }

        await this.layDsHdtbThayDoiSoSim({
          vma_tb: '0',
          vdichvu_vt_id: this.cboDichVuVt.Value,
          vtthd_id: this.TrangThaiHD.THANH_TOAN
        })

        if (this.dgrThueBao.DataSource.length == 0) {
          await this.hienThiDanhSach()
        }
      } finally {
        this.loading(false)
      }
    },
    async lay_ds_hopdong_theomagd(data) {
      try {
        let response = await this.$root.context.post(`/web-ccdv/hoanthienhschuyenquyen/lay-ds-hopdong-theomagd`, data)

        if (response.error_code === 'BSS-00000000' && response.data != null) {
            return response.data.length > 0 ? response.data[0] : null
        }

        throw {response: { data: response}}
      } catch (err) {
          return null
      }
    },
    async onEnterInputMaTb() {

      if (!this.txtMaTB.Text) {
        return
      }

      try {
        this.loading(true)
        try {
          await this.hienThiTrangThai(this.txtMaTB.Text)
          await this.hienThiThongTinDanhBa(this.txtMaTB.Text)
        } catch(err) {
          this.$root.toastError(err)
          let maTb = this.txtMaTB.Text
          this.btnNhapMoi_Click()
          this.txtMaTB.Text = maTb
          return
        }

        await this.layTienTheoKhoanMuc()
      } finally {
        this.loading(false)
      }
    },
    async hienThiTTHopDongKH_1(hopDong) {

      if (!!hopDong) {

        this.hdkh_id = hopDong["hdkh_id"]
        this.txtMaGD.Text = hopDong["ma_gd"]
        this.txtMaHD.Text = hopDong["ma_hd"]
        this.txtMaKH.Text = hopDong["ma_kh"]

        if (!!hopDong["khachhang_id"]) {
          this.khachhang_id = hopDong["khachhang_id"]
        }

        this.txtTenKH.Text = hopDong["ten_kh"]
        this.txtDiaChiKH.Text = hopDong["diachi_kh"]
        this.dtpNgayLapHD.EditValue = new Date(hopDong["ngaylap_hd"])

        if (!!hopDong["ngay_yc"]) {
          this.dtpNgayYeuCau.EditValue = new Date(hopDong["ngay_yc"])
        } else {
          this.dtpNgayYeuCau.EditValue = new Date()
        }

        this.txtGhiChu.Text = hopDong["ghichu"]
        this.setButton(3)
      } else {
          this.setButton(0)
      }
    },
    async hienThiTTHopDongKH(hopDong) {

      if (!!hopDong) {
        this.hdkh_id = hopDong["hdkh_id"]
        this.hdtb_id = hopDong["hdtb_id"]
        this.txtMaGD.Text = hopDong["ma_gd"]
        this.txtMaHD.Text = hopDong["ma_hd"]
        this.txtMaKH.Text = hopDong["ma_kh"]

        if (!!hopDong["khachhang_id"]) {
          this.khachhang_id = hopDong["khachhang_id"]
        }

        this.txtTenKH.Text = hopDong["ten_kh"]
        this.txtDiaChiKH.Text = hopDong["diachi_kh"]
        this.txtTenTB.Text = hopDong["ten_tb"]
        this.txtDiaChiTB.Text = hopDong["diachi_tb"]

        this.dtpNgayLapHD.EditValue = new Date(hopDong["ngaylap_hd"])

        if (!!hopDong["ngay_yc"]) {
          this.dtpNgayYeuCau.EditValue = new Date(hopDong["ngay_yc"])
        } else {
          this.dtpNgayYeuCau.EditValue = new Date()
        }

        this.setButton(3)
      } else {
        this.setButton(0)
      }
    },
    async layTienTheoKhoanMuc() {

      let dichvuvt_id = parseInt(this.cboDichVuVt.Value)

      if (dichvuvt_id != this.DichVuVienThong.ADSL) {
          this.ltb_adsl_id = 1
      }

      let kq = await this.lay_tien_khoanmuc_ld({
        loaihd_id: this.LoaiHopDong.THAY_DOI_SO_SIM,
        ngay_yc: this.ddmmyyyy(this.dtpNgayYeuCau.EditValue),
        dichvuvt_id: dichvuvt_id,
        loaitb_id: this.cboLoaiHinhTB.Value,
        kieuld_id: this.cboKieuLD.Value,
        phuongld_id: this.phuongld_id,
        tocdo_id: this.tocdo_id,
        muccuoc_id: 0,
        ltb_adsl_id: this.ltb_adsl_id,
        ltb_adsl_moi_id: 0,
        ltb_adsl_cu_id: 0,
        tocdo_moi_id: 0,
        tocdo_cu_id: 0,
        kh_cd: 0,
        phold_id: this.phold_id,
        apld_id: this.apld_id,
        khuld_id: this.khuld_id,
        doituong_id: this.doituong_id,
        trangbi_id: this.trangbi_id
      })

      if (kq.length <= 0) {
        this.txtTienLapMoi.Text = "0"
        this.txtVatLapmoi.Text = "0"
        this.$root.toastError("Thông tin giá chưa được cập nhật")
        return
      }

      let kt_lm = true

      for (let i = 0; i < kq.length; i++)
      {
          let item = kq[i]

          if (item["khoanmuctt_id"] == this.KHOANMUC_TT.KMTT_DOISIM)
          {
              this.txtTienLapMoi.Text = kq[0]["tien"]
              this.txtVatLapmoi.Text = kq[0]["vat"]
              kt_lm = false
          }
      }

      if (kt_lm == true) {
          this.txtTienLapMoi.Text = "0"
          this.txtVatLapmoi.Text = "0"
      }
    },
    async lay_tien_khoanmuc_ld(data) {
      try {
        let response = await this.$root.context.post(`/web-hopdong/khoiphucthanhly/lay_tien_khoanmuc_ld`, data)

        if (response.error_code === 'BSS-00000000' && response.data != null) {
            return response.data
        }

        throw {response: { data: response}}
      } catch (err) {
          return null
      }
    },
    async hienThiThongTinDanhBa(maTb) {

      let dichvuvt_id = parseInt(this.cboDichVuVt.Value)

      //Kiểm tra xem mã TB này có đang được lập hợp đồng hoặc phụ lục khác hay không?
      try {
        await this.kiemTraMaTbLapHopDongKhac({
          ma_tb: maTb,
          dichvuvt_id: dichvuvt_id
        })
      } catch(err) {
        throw err
      }

      let danhBa = await this.layDanhBaTheoMaTb({
          ma_tb: maTb,
          dichvuvt_id: dichvuvt_id
      })

      if (!!danhBa) {

        this.txtMaKH.Text = danhBa.MA_KH
        this.txtMaHD.Text = danhBa.MA_HD
        this.khachhang_id = danhBa.KHACHHANG_ID
        this.txtTenKH.Text = danhBa.TEN_KH
        this.txtDiaChiKH.Text = danhBa.DIACHI_KH

        //Thông tin thuê bao
        this.thuebao_id = danhBa.THUEBAO_ID
        this.txtTenTB.Text = danhBa.TEN_TB
        this.txtDiaChiTB.Text = danhBa.DIACHI_TB
        this.cboLoaiHinhTB.Value = danhBa.LOAITB_ID
        this.doituong_id = danhBa.DOITUONG_ID
        this.donvi_id = danhBa.DONVIQL_ID

        let diaChi = await this.lay_ds_diachi_theo_khachhang(this.khachhang_id)

        if (!!diaChi) {

          if (!!diaChi["phuong_id"]) {
            this.phuongld_id = diaChi["phuong_id"]
          }

          if (!!diaChi["pho_id"]) {
            this.phold_id = diaChi["pho_id"]
          }
        }

        //So sim cu
        if (dichvuvt_id == this.DichVuVienThong.DI_DONG || dichvuvt_id == this.DichVuVienThong.DD_TRATRUOC) {

          let danhBaDiDong = null

          try {
              this.loading(true)
              danhBaDiDong = await this.lay_ds_db_dd_theo_tbid(this.thuebao_id)
          } finally {
              this.loading(false)
          }

          if (!!danhBaDiDong) {
            this.txtSoSimCu.Text = danhBaDiDong["simcard"]
            this.trangbi_id = !!danhBaDiDong["trangbi_id"] ? danhBaDiDong["trangbi_id"] : this.TRANGBI.KHACHHANG_TU_TRANGBI
          } else {
            this.$root.toastError(`Không tìm thấy thông tin danh bạ của thuê bao ${maTb}`)
          }
        } else if (dichvuvt_id == this.DichVuVienThong.GPHONE) {

          let danhBaGphone = await this.lay_ds_db_gp_theo_tbid(this.thuebao_id)

          if (!!danhBaGphone) {
              this.txtSoSimCu.Text = danhBaGphone["simcard"]
              this.trangbi_id = !!danhBaGphone["trangbi_id"] ? danhBaGphone["trangbi_id"] : this.TRANGBI.KHACHHANG_TU_TRANGBI
          } else {
            this.$root.toastError(`Không tìm thấy thông tin danh bạ của thuê bao ${maTb}`)
          }
        } else {
          this.$root.toastError("Không phải thuê bao di động hoặc GPhone!")
          return
        }
      } else if (dichvuvt_id == this.DichVuVienThong.DI_DONG || dichvuvt_id == this.DichVuVienThong.DD_TRATRUOC) {

        //Thông tin khách hàng
        this.txtMaKH.Text = ""
        this.txtMaHD.Text = await this.lay_ma_hd(this.donvi_dl_id)
        this.khachhang_id = 0

        if (!!this.tttb && this.tttb.length > 0 && Object.keys(this.tttb[0]).length > 2)
        {
            if (this.tttb[0]["TRA_SAU"] != 1)
            {
                this.txtTenKH.Text = this.tttb[0]["TEN_KH"];
                this.txtDiaChiKH.Text = this.tttb[0]["DCHI_KH"];
                this.txtSoSimCu.Text = this.tttb[0]["SO_MSIN"];

                //Thông tin thuê bao
                this.txtTenTB.Text = this.tttb[0]["TEN_KH"];
                this.txtDiaChiTB.Text = this.tttb[0]["DCHI_KH"];
            }
            else
            {
              try {
                let dtLichSuKH = await ccbsService.ts_tracuu_laytt_tb_ts("84" + this.txtMaTB.Text, this.tttb[0]["MA_TINH"]);

                if (!!dtLichSuKH && dtLichSuKH.length > 0 && Object.keys(dtLichSuKH[0]).length > 2)
                {
                    this.txtTenTB.Text = dtLichSuKH[0]["TEN_TB"];
                    this.txtTenKH.Text = dtLichSuKH[0]["TEN_TB"];
                    this.txtDiaChiKH.Text = dtLichSuKH[0]["DIACHI_THUONGTRU"];
                    this.txtDiaChiTB.Text = dtLichSuKH[0]["DIACHI_THUONGTRU"];
                    this.txtSoSimCu.Text = this.tttb[0]["SO_MSIN"];
                }
              }
              catch(err)
              {

              }
            }
        }
      }
    },
    async lay_ma_hd(donViId) {
      try {
        let response = await this.$root.context.get(`/web-hopdong/doisim/lay_ma_hd?donvi_id=${donViId}`)

        if (response.error_code === 'BSS-00000000' && response.data != null) {
            return response.data.ma_hd
        }

        throw {response: { data: response}}
      } catch (err) {
          return null
      }
    },
    async lay_ds_db_gp_theo_tbid(thueBaoId) {
      try {
        let response = await this.$root.context.get(`/web-hopdong/hopdong/lay_ds_danhba_gp?thueBaoId=${thueBaoId}`)

        if (response.error_code === 'BSS-00000000' && response.data != null) {
            return response.data.length > 0 ? response.data[0] : null
        }

        throw {response: { data: response}}
      } catch (err) {
          return null
      }
    },
    async lay_ds_db_dd_theo_tbid(thueBaoId) {
      try {
        let response = await this.$root.context.get(`/web-hopdong/hopdong/lay_ds_danhba_dd?thueBaoId=${thueBaoId}`)

        if (response.error_code === 'BSS-00000000' && response.data != null) {
            return response.data.length > 0 ? response.data[0] : null
        }

        throw {response: { data: response}}
      } catch (err) {
          return null
      }
    },
    async lay_ds_diachi_theo_khachhang(khachHangId) {
      try {
        let response = await this.$root.context.get(`/web-hopdong/hopdong/lay_ds_diachi_theo_khachhang?khachHangId=${khachHangId}`)

        if (response.error_code === 'BSS-00000000' && response.data != null) {
            return response.data.length > 0 ? response.data[0] : null
        }

        throw {response: { data: response}}
      } catch (err) {
          return null
      }
    },
    async layDanhBaTheoMaTb(data) {
      try {
          let response = await this.$root.context.post(`/web-hopdong/ThayDoiDatCoc/lay_danhba_theo_matb`, data)

          if (response.error_code === 'BSS-00000000' && response.data != null) {
              return response.data
          }

          throw {response: { data: response}}
      } catch (err) {
          return null
      }
    },
    async kiemTraMaTbLapHopDongKhac(data) {

      let kq = null

      try {
          let response = await this.$root.context.post(`/web-hopdong/doisim/kiemtra_ma_tb_lap_hopdong_khac`, data)

          if (response.error_code === 'BSS-00000000' && response.data != null) {
            kq = response.data
          } else {
            throw {response: { data: response}}
          }
      } catch (err) {
          kq = null
      }

      if (!!kq && kq.length > 0) {
        kq = kq[0]
        throw `Số máy/Acc: ${kq.ma_tb} đang được lập bởi hợp đồng (phụ lục) ${kq.ten_loaihd} có mã giao dịch ${kq.ma_gd} do User ${kq.nguoi_cn} thuộc đơn vị ${kq.ten_dv} thực hiện vào ngày ${this.ddmmyyyyhhmiss(kq.ngay_yc)}`
      }
    },
    async hienThiTrangThai(somay) {

      let ip = await this.getIp()
      let dichvuvt_id = parseInt(this.cboDichVuVt.Value)
      let vma_vung = dichvuvt_id == this.DichVuVienThong.GPHONE ? this.userInfo.mavung_cd : "84"
      let tentat_ccbs = this.$root.context.user.getProperty('tentat_ccbs').toString()

      try {
        this.tttb = await ccbsService.ts_tracuu_laytt_tb(vma_vung + somay, this)
      } catch(err) {
        this.$root.toastError("Có lỗi xảy ra khi lấy thông tin thuê bao")
        return
      }

      if (!this.tttb || this.tttb.length <= 0)
      {
          this.$root.toastError("Tài khoản không có quyền truy cập CCBS!");
          return;
      }

      let columns = Object.keys(this.tttb[0])

      if (columns.length == 1)
      {
          if (columns.includes("KHOSO"))
          {
              this.$root.toastError(this.tttb[0]["KHOSO"] + ": Không tìm thấy thông tin thuê bao");
              this.clear()
              return;
          }

          if (columns.includes("VALUE"))
          {
              this.$root.toastError(this.tttb[0]["VALUE"]);
              return;
          }
      }
      else
      {
          this.ckeGoiDi.Checked = this.tttb[0]["GOI_DI"] == 1 ? true : false;
          this.ckeGoiDen.Checked = this.tttb[0]["GOI_DEN"] == 1 ? true : false;
          this.txtTinhTP.Text = this.tttb[0]["MA_TINH"];
          this.txtLoai.Text = this.tttb[0]["LOAI_TB"];

          if (this.tttb[0]["TRA_SAU"] == 1)
          {
              if (dichvuvt_id == this.DichVuVienThong.GPHONE)
              {
                this.cboLoaiHinhTB.Value = this.LoaiHinhTB.GPHONE;
              }
              else
              {
                this.cboLoaiHinhTB.Value = this.LoaiHinhTB.DIDONGTRASAU;
              }
          }
          else
          {
              if (dichvuvt_id == this.DichVuVienThong.GPHONE)
              {
                this.cboLoaiHinhTB.Value = this.LoaiHinhTB.GPHONE_TRATRUOC;
              }
              else
              {
                this.cboLoaiHinhTB.Value = this.LoaiHinhTB.DIDONGTRATRUOC;
              }
          }
      }
    },
    async layThongTinNguoiDung() {
      try {
        let rs = await this.$root.context.get("/web-ccdv/hoanthien-hs-tachnhap-tbao/thongtin_nguoidung")
        return !rs.data ? null : rs.data[0]
      } catch (error) {
        return null
      }
    },
    async getIp(){
      try{
        const response = await fetch("https://jsonip.com/")
        const data = await response.json();
        return data.ip;
      }catch(e) {
        return ""
      }
    },
    async layHopDongTheoMaTb(data) {
        try {
            let response = await this.$root.context.post(`/web-hopdong/doisim/lay_hopdong_theo_ma_tb`, data)

            if (response.error_code === 'BSS-00000000' && response.data != null) {
                return response.data
            }

            throw {response: { data: response}}
        } catch (err) {
            return null
        }
    },
    async layDsLoaiHinhTb(dichVuId) {
      try {
          let response = await this.$root.context.get(`/web-hopdong/doisim/lay_ds_loaihinh_tb?dichvu_id=${dichVuId}`)

          if (response.error_code === 'BSS-00000000' && response.data != null) {
              this.cboLoaiHinhTB.DataSource = response.data
              return
          }

          throw {response: { data: response}}
      } catch (err) {
          this.cboLoaiHinhTB.DataSource = []
      }
    },
    setButton(kieu) {
      this.btnNhapMoi.enabled = false
      this.btnGhiLai.enabled = false
      this.btnXoa.enabled = false
      this.btnHuyBo.enabled = false
      this.btnThanhToan.enabled = false

      if (kieu == -1)//Bat dau
      {
        this.$refs['ma-tb'].focus()
        this.btnGhiLai.enabled = true
        this.btnHuyBo.enabled = true
      }

      if (kieu == 0)//Bat dau
      {
        this.$refs['ma-tb'].focus()
        this.btnNhapMoi.enabled = true
        this.clear()
      }

      if (kieu == 1)//Them moi
      {
        this.$refs['ma-tb'].focus()
        this.btnGhiLai.enabled = true
        this.btnHuyBo.enabled = true
        this.clear()
      }

      if (kieu == 2)//Huy
      {
        this.$refs['ma-gd'].focus()
        this.btnNhapMoi.enabled = true
        this.btnXoa.enabled = true
        this.clear()
      }

      if (kieu == 3)//Edit
      {
        this.$refs['ma-gd'].focus()
        this.btnNhapMoi.enabled = true
        this.btnXoa.enabled = true
        this.btnGhiLai.enabled = true
        this.btnHuyBo.enabled = true
        this.btnThanhToan.enabled = true
      }
      if (kieu == 4)//Edit ( chưa thanh toán )
      {
        this.$refs['ma-gd'].focus()
        this.btnNhapMoi.enabled = true
        this.btnXoa.enabled = true
        this.btnGhiLai.enabled = true
        this.btnHuyBo.enabled = true
        this.btnThanhToan.enabled = true
      }
    },
    btnHuyBo_Click() {
      if (this.btnHuyBo.enabled) {
        this.setButton(0)
        this.hienThiDanhSach()
      }
    },
    btnNhapMoi_Click() {
      if (this.btnNhapMoi.enabled) {
        this.setButton(1)
        this.clear()
      }
    },
    clear() {
      this.$v.$reset()
      this.dgrThueBao.DataSource = []
      this.trangbi_id = this.TRANGBI.KHACHHANG_TU_TRANGBI
      this.hdkh_id = 0
      this.txtTongTienHD.Text = "0"
      this.txtMaGD.Text = ""
      this.txtMaHD.Text = ""
      this.txtMaKH.Text = ""
      this.khachhang_id = 0
      this.txtTenKH.Text = ""
      this.txtDiaChiKH.Text = ""
      this.dtpNgayLapHD.EditValue = new Date()
      this.dtpNgayYeuCau.EditValue = new Date()
      this.txtSoSimMoi.Text = ""
      this.txtTBBiLaySim.Text = ""
      this.txtLoai.Text = ""
      this.txtTinhTP.Text = ""
      this.ckeEsim.Checked = false
      this.ckeGoiDen.Checked = false
      this.ckeGoiDi.Checked = false
      this.hdtb_id = 0
      this.thuebao_id = 0
      this.txtTenTB.Text = ""
      this.txtDiaChiTB.Text = ""

      if (this.cboKieuLD.DataSource.length > 0) {
        this.cboKieuLD.Value = this.cboKieuLD.DataSource[0].kieuld_id
      }

      this.txtTienLapMoi.Text = "0"
      this.txtVatLapmoi.Text = "0"
      this.$refs['ma-tb'].focus()
      this.doituong_id = 1
      this.txtGhiChu.Text = ""
      this.txtMaTB.Text = ""
      this.txtSoSimCu.Text = ""

      this.phuongld_id = 0
      this.tocdo_id = 0
      this.ltb_adsl_id = 1
      this.phold_id = 0
      this.apld_id = 0
      this.khuld_id = 0
    },
    async hienThiDanhSach() {
      try {
        this.loading(true)
        await this.layDsHdtbThayDoiSoSim({
          vma_tb: '0',
          vdichvu_vt_id: this.cboDichVuVt.Value,
          vtthd_id: this.TrangThaiHD.DA_THI_CONG_XONG
        })
      } finally {
        this.loading(false)
      }
    },
    async layDsHdtbThayDoiSoSim(data) {
      try {
          let response = await this.$root.context.post(`/web-hopdong/doisim/lay_ds_hdtb_thaydoi_sosim`, data)

          if (response.error_code === 'BSS-00000000' && response.data != null) {
              response.data.forEach(item => item.ngaylap_hd_ht = this.ddmmyyyy(item.ngaylap_hd))
              this.dgrThueBao.DataSource = response.data
              return
          }

          throw {response: { data: response}}
      } catch (err) {
          this.dgrThueBao.DataSource = []
      }
    },
    async layDsThamSoMd() {
        try {
            let response = await this.$root.context.get(`/web-hopdong/doisim/lay_ds_thamso_md`)

            if (response.error_code === 'BSS-00000000' && response.data != null) {
                this.dsThamSoMacDinh = response.data
                return
            }

            throw {response: { data: response}}
        } catch (err) {
            this.dsThamSoMacDinh = []
        }
    },
    async layDsDichVuVt() {
        try {
            let response = await this.$root.context.get(`/web-hopdong/doisim/lay_ds_dichvu_vt`)

            if (response.error_code === 'BSS-00000000' && response.data != null) {
                this.cboDichVuVt.DataSource = response.data
                return
            }

            throw {response: { data: response}}
        } catch (err) {
            this.cboDichVuVt.DataSource = []
        }
    },
    async btnGhiLai_Click() {

      if (!this.btnGhiLai.enabled) {
        return
      }

      let errorMessage = []
      let maHdError = false
      let soSimMoiError = false

      this.$v.$touch()

      if (this.txtSoSimMoi.Text.length != 10) {
        errorMessage.push("Số sim phải nhập 10 ký tự")
        soSimMoiError = true
      }

      if (this.txtTBBiLaySim.Text != "Sim hợp lệ") {
        errorMessage.push("Kiểm tra lại số sim mới!")
        soSimMoiError = true
      }

      if (!this.$v.txtTenKH.Text.required) {
        errorMessage.push("Hãy nhập tên khách hàng!")
      }

      if ( !!this.khachhang_id && !this.$v.txtMaKH.Text.required) {
        errorMessage.push("Kiểm tra lại mã khách hàng!")
      }

      if (!!this.khachhang_id && !this.$v.txtMaHD.Text.required) {
        errorMessage.push("Hãy nhập mã hợp đồng!")
        maHdError = true
      }

      if (!this.$v.txtSoSimMoi.Text.required) {
          errorMessage.push("Kiểm tra lại số Sim mới!")
          soSimMoiError = true
      }

      if (!this.$v.txtTenTB.Text.required) {
        errorMessage.push("Hãy nhập tên thuê bao!")
      }

      if (errorMessage.length > 0) {

          this.$root.toastError(errorMessage.join('\n'))

          if (maHdError) {
            this.$refs['ref-txt-ma-hd'].focus()
          } else if (soSimMoiError) {
            this.$refs['ref-txt-so-sim-moi'].focus()
          }

          return
      }

      this.loading(true)

      try {
        let input = null

        if (!this.btnNhapMoi.enabled) {

          //Kiểm tra xem mã TB này có đang được lập hợp đồng hoặc phụ lục khác hay không?
          try {
            await this.kiemTraMaTbLapHopDongKhac({
              ma_tb: this.txtMaTB.Text,
              dichvuvt_id: this.cboDichVuVt.Value
            })
          } catch(err) {
            this.$root.toastError(err)
            return
          }

          let huongGiao = await this.lay_huongiao_tiepnhan({
            loaihd_id: this.LoaiHopDong.THAY_DOI_SO_SIM,
            loaitb_id: this.cboLoaiHinhTB.Value,
            tocdo_id: this.tocdo_id,
            muccuoc_id: 0,
            kieuld_id: this.cboKieuLD.Value
          })

          if (!huongGiao) {
            this.$root.toastError("Nghiệp vụ này chưa được thiết lập quy trình, bạn hãy liên hệ Admin để xử lý!")
            return
          }

          this.huonggiaotn_id = huongGiao["huonggiao_id"]
          this.quytrinhtn_id = huongGiao["quytrinh_id"]

          input = await this.TaoDuLieu(true)
        } else {
          let kiemtra_db_khi_themmoi = await this.kiemtra_db_khi_themmoi({
            ma_tb: this.txtMaTB.Text,
            khachhang_id: this.khachhang_id,
            thuebao_id: this.thuebao_id,
            dichvuvt_id: this.cboDichVuVt.Value
          })

          if (!kiemtra_db_khi_themmoi) {
              this.$root.toastError("Thuê bao không tồn tại trong danh bạ, không cập nhật được ")
              return
          }

          input = await this.TaoDuLieu(false)
        }

        let kq = await this.luu_hopdong(input)

        if (!!kq && kq.kq == 1) {
          this.$root.toastSuccess("Cập nhật dữ liệu lên viễn thông tỉnh thành công!")
          this.setButton(3)
          await this.hienThiDanhSach()
        } else {
          this.$root.toastError("Cập nhật dữ liệu lên viễn thông tỉnh thất bại. \n" + kq.message)
        }
      } finally {
        this.loading(false)
      }
    },
    async luu_hopdong(data) {
      try {
        let response = await this.$root.context.post(`/web-hopdong/doisim/luu_hopdong`, data)

        if (response.data != null) {
            return response.data
        }

        throw {response: { data: response}}
      } catch (err) {
        return null
      }
    },
    async kiemtra_db_khi_themmoi(data) {
      try {
        let response = await this.$root.context.post(`/web-hopdong/chuyendich/kiemtra_db_khi_themmoi`, data)

        if (response.data != null) {
            return parseInt(response.data) == 1
        }

        throw {response: { data: response}}
      } catch (err) {
        return false
      }
    },
    async TaoDuLieu(themmoi) {

      let input = { }

      // thông tin khách hàng
      input.hdkh_id = themmoi ? null : this.hdkh_id

      if (themmoi) {

        if (this.allowSinhMaGdTheoDv) {
          input.ma_gd = await this.sinh_magd_v2()
        } else {
          input.ma_gd = await this.sinh_magd()
        }

        this.txtMaGD.Text = input.ma_gd
      } else {
        input.ma_gd = this.txtMaGD.Text
      }

      input.ma_hd = this.txtMaHD.Text
      input.ma_kh = this.txtMaKH.Text
      input.khachhang_id = this.khachhang_id
      input.ten_kh = this.txtTenKH.Text
      input.diachi_kh = this.txtDiaChiKH.Text
      input.ngaylap_hd = this.ddmmyyyy(this.dtpNgayLapHD.EditValue)
      input.ngay_yc = this.ddmmyyyy(this.dtpNgayYeuCau.EditValue)
      input.khlon_id = 1
      input.loaihd_id = this.LoaiHopDong.THAY_DOI_SO_SIM
      input.ghichu = this.txtGhiChu.Text

      // thông tin thuê bao
      input.hdtb_id = themmoi ? null : this.hdtb_id
      input.thuebao_id = this.thuebao_id
      input.ma_tb = this.txtMaTB.Text
      input.ten_tb = this.txtTenTB.Text
      input.diachi_tb = this.txtDiaChiTB.Text
      input.kieuld_id = this.cboKieuLD.Value
      input.doituong_id = this.doituong_id
      input.tthd_id = this.TrangThaiHD.MOI
      input.loaitb_id = this.cboLoaiHinhTB.Value
      input.dichvuvt_id = parseInt(this.cboDichVuVt.Value)
      input.donvi_id = this.donvi_id
      input.quytrinh_id = this.quytrinhtn_id
      input.huonggiao_id = this.huonggiaotn_id

      if (input.dichvuvt_id == this.DichVuVienThong.GPHONE) {
        input.simcard = this.txtSoSimMoi.Text
      } else if (input.dichvuvt_id == this.DichVuVienThong.DI_DONG) {

        let danhBa = await this.layDanhBaTheoMaTb({
          ma_tb: input.ma_tb,
          dichvuvt_id: input.dichvuvt_id
        })

        input.tinhkhac = !danhBa ? 1 : 0
        input.simcard = this.txtSoSimMoi.Text
      }

      // thông tin thanh toán
      input.tien = this.txtTienLapMoi.Text
      input.vat = parseInt(input.tien) * 0.1

      input.ip = await this.getIp()

      return input
    },
    async sinh_magd() {

      let loaihd_id = this.LoaiHopDong.THAY_DOI_SO_SIM

      try {
        let response = await this.$root.context.get(`/web-hopdong/chuyendich/sinh_magd?loaihd_id=${loaihd_id}`)

        if (response.data != null) {
            return response.data
        }

        throw {response: { data: response}}
      } catch (err) {
        return null
      }
    },
    async sinh_magd_v2() {

      let donvi_id = this.$root.token.getDonViID()
      let loaihd_id = this.LoaiHopDong.THAY_DOI_SO_SIM

      try {
        let response = await this.$root.context.get(`/web-hopdong/chuyendich/sinh_magd_v2?donvi_id=${donvi_id}&loaihd_id=${loaihd_id}`)

        if (response.data != null) {
            return response.data
        }

        throw {response: { data: response}}
      } catch (err) {
        return null
      }
    },
    async lay_huongiao_tiepnhan(data) {
      try {
        let response = await this.$root.context.post(`/web-hopdong/doisim/lay_huongiao_tiepnhan`, data)

        if (response.error_code === 'BSS-00000000' && response.data != null) {
            return response.data
        }

        throw {response: { data: response}}
      } catch (err) {
        return null
      }
    },
    getDateElements(date) {

      date = new Date(date)

      let day = date.getDate()
      day = day > 9 ? day : '0' + day

      let month = date.getMonth() + 1
      month = month > 9 ? month : '0' + month

      let hour = date.getHours()
      hour = hour > 9 ? hour : '0' + hour

      let min = date.getMinutes()
      min = min > 9 ? min : '0' + min

      let second = date.getSeconds()
      second = second > 9 ? second : '0' + second

      return {
          y: date.getFullYear(),
          M: month,
          d: day,
          h: hour,
          m: min,
          s: second
      }
    },
    ddmmyyyy(date) {

        if (!date) {
            return ''
        }

        date = this.getDateElements(date)
        return `${date.d}/${date.M}/${date.y}`
    },
    ddmmyyyyhhmiss(date) {

        if (!date) {
            return ''
        }

        date = this.getDateElements(date)
        return `${date.d}/${date.M}/${date.y} ${date.h}:${date.m}:${date.s}`
    },
  },
}
</script>

<style>
  .frm-doi-sim .disable-btn:hover {
        background-color: white !important;
    }

  .frm-doi-sim .disable-btn a {
      color: #6c757d !important;
  }

  .frm-doi-sim .disable-btn a:hover {
      cursor: default;
      color: #6c757d;
  }

  .frm-doi-sim.modal-body .page-content {
      position: inherit !important;
  }
</style>
