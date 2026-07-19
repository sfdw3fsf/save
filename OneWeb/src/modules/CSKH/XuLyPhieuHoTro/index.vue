<template src="./index.html"></template>
<style scoped src="./index.scss"></style>
<script>
// import GiaoPhieuHoTro from '../GiaoPhieuHoTro/index.js'
import PopupUploadFile from '../TiepNhanHoTroKhachHang/popup/ModalUploadFile.vue'
import formGiaoPhieuPhoiHop from '@/modules/CSKH/giaophieu-phoihop/index.vue'
export default {
  components: { formGiaoPhieuPhoiHop, PopupUploadFile },
  props: {
    IS_XACMINH: false
  },
  name: 'XuLyPhieuHoTro',
  watch: {
    dsThueBaoKhieuNaiChuaGiao: {
      handler: function (data) {
        if (data) {
          console.log(data)
          this.setButton(1)
        } else {
          this.setButton(0)
        }
      }
    }
  },
  data: function () {
    return {
      dichvuvt_id: '',
      nhomht_id: '',
      titleText: 'Xử lý phiếu hỗ trợ',
      infoPhieuHoTro: {
        data: [],
        MA_TB: '',
        KETQUAKN_ID: '',
        MA_HOTRO: '',
        DICHVUVT_ID: '',
        LOAITB_ID: '',
        LOAI_HINH: '',
        YEU_CAU_HT: '',
        MA_KH: '',
        MA_HO_TRO: '',
        TEN_TB: '',
        DIA_CHI_LD: '',
        MA_TT: '',
        DIACHI_LH: '',
        NGAY_TIEP_NHAN: new Date(),
        show_NGAY_TIEP_NHAN: false,
        LOAITB_ID: '',
        DICHVUVT_ID: '',
        LOAIHT_ID: '',
        CHUDEHT_ID: '',
        HTKN_ID: '',
        PHIEUHT_ID: '',
        HTKH_ID: '',
        NHOMHT_ID: '',
        QUYTRINH_ID: '',
        TTHT_ID: ''
      },
      infoKhachHang: {
        data: [],
        MaKH: '',
        loai_kh_tree: [],
        loaiKH: '',
        phanLoaiKH: '',
        tenKH: '',
        diaChiKH: '',
        maSoThue: '',
        nguoiYC: '',
        soDienThoai: '',
        email: ''
      },
      infoKetQuaHoTro: {
        HTHT_ID: '',
        KQXL_ID: 0,
        MDHL_ID: 0,
        NOIDUNG_HT: '',
        NGAY_HT: ''
        // HENGQ_DEN_DATE: new Date(),
        // show_HENGQ_DEN_DATE: false,
        // mucDoHaiLong_selected: '',
        // ketQua_selected: '',
        // noiDung: '',
      },
      form: {
        buttonGhiLai: false,
        buttonFileDinhKem: false,
        buttonTraPhieu: false,
        buttonGiaoPhoiHop: false,
        buttonHoanThanh: false,
        buttonKhoaPhieu: false
      },
      dsMucDoHaiLong: [
        { text: 'Hài lòng', value: 1 },
        { text: 'Chưa hài lòng', value: 0 },
        { text: 'Ý kiến khác', value: 2 }
      ],
      dsKetQua: [
        { text: 'Đang xử lý', value: 1 },
        { text: 'Xử lý được', value: 0 }
      ],
      tmp_dsLoaiKH: [],
      dsKetQuaKN: [],
      dsDichVu: [],
      dsLoaiHinhTB: [],
      tabDsKhieuNaiSelected: {
        ChuaGiao: false,
        DaGiao: false,
        PhoiHop: false
      },
      dsThueBaoKhieuNaiChuaGiao: [],
      dsThueBaoKhieuNaiDaGiao: [],
      dsPhieuPhoiHop: [],
      enabledSelectFirstRowDaGiao: false,
      dsNhomHoTro: [],
      dsChuDe: [],
      dsKenhHT: [],
      dsHinhThuc: []
    }
  },
  computed: {
    labelNoiDung: function () {
      return this.IS_FORM_XACMINH ? 'ND xác minh' : 'Nội dung'
    },
    IS_FORM_XACMINH: function () {
      return !(this.IS_XACMINH == undefined || this.IS_XACMINH == false)
    },
    TRANGTHAI_HOTRO: function () {
      return this.IS_FORM_XACMINH ? '3' : '2'
    }
  },
  created() {
    this.startForm()
  },
  methods: {
    async startForm() {
      await this.getDichVu()
      await this.getDSNhomHoTro()
      await this.getDSHinhThuc()
      await this.getDSKenhTN()
      await this.getDSPhieuHT_pTTHTID()

      this.tabDsKhieuNaiSelected.ChuaGiao = true
    },
    async tsbtnNhapMoi_Click() {
      await this.getDSPhieuHT_pTTHTID()
    },
    async valiedataGhiLai() {
      if (!this.infoKetQuaHoTro.NOIDUNG_HT || this.infoKetQuaHoTro.NOIDUNG_HT == '' || this.infoKetQuaHoTro.NOIDUNG_HT == null) {
        this.$toast.error('Bạn chưa chọn nội dung hỗ trợ')
        return false
      } else return true
    },

    async tsbtnGhiLai_Click() {
      if (!(await this.valiedataGhiLai())) {
        return
      }
      try {
        this.loading(true)
        let data = {
          pHTKHID: this.infoPhieuHoTro.HTKH_ID,
          pHTHTID: this.infoKetQuaHoTro.HTHT_ID,
          pKQHTID: this.infoKetQuaHoTro.KQXL_ID,
          pMDHLID: this.infoKetQuaHoTro.MDHL_ID,
          pNoiDung: this.infoKetQuaHoTro.NOIDUNG_HT
        }
        const rs = await this.$root.context.post('/web-cskh/api/xu-ly-thue-bao/capnhat-phieu-xuly-hotro', data)
        if (rs.error_code === 'BSS-00000000') {
          this.$toast.success('Thành công!')
        }

        if (this.IS_FORM_XACMINH) {
          let data = {
            phieuID: this.infoPhieuHoTro.PHIEUHT_ID,
            nhanvienHTID: this.$root.token.getNhanVienID(),
            noiDung: this.infoKetQuaHoTro.NOIDUNG_HT
          }

          const rs = await this.$root.context.post('/web-cskh/api/xu-ly-thue-bao/xacminh-hotro', data)
          // if (rs.error_code === 'BSS-00000000') {
          //   this.$toast.success('Thành công!')
          // }
        }
      } catch (error) {
        this.$toast.error(error.response.data.message)
      } finally {
        await this.getDSPhieuHT_pTTHTID()
        this.loading(false)
      }
    },
    // Hoàn Thành
    async tsbtnHoanThanh_Click() {
      try {
        if (!(await this.valiedataGhiLai())) {
          return
        }
        let isConfirm = false
        await this.$bvModal
          .msgBoxConfirm('Bạn có muốn hoàn thành phiếu này không!', {
            title: 'Thông báo',
            centered: true,
            size: 'md',
            okTitle: 'Đồng ý',
            cancelTitle: 'Hủy'
          })
          .then((value) => {
            if (value) {
              isConfirm = true
            }
          })
        if (!isConfirm) {
          return
        }
        let data = {
          pHTKHID: this.infoPhieuHoTro.HTKH_ID,
          pPHIEUHTID: this.infoPhieuHoTro.PHIEUHT_ID,
          // pTTHTID: 2,
          pTTHTID: this.TRANGTHAI_HOTRO,
          pNoiDung: this.infoKetQuaHoTro.NOIDUNG_HT
        }
        this.loading(true)
        const rs = await this.$root.context.post('/web-cskh/api/xu-ly-thue-bao/hoan-thanh', data)
        if (rs.error_code === 'BSS-00000000') {
          this.$toast.success('Thành công!')
        }
      } catch (error) {
        this.$toast.error(error.response.data.message)
      } finally {
        await this.getDSPhieuHT_pTTHTID()
        this.loading(false)
      }
    },
    async tsbtnGiaoPhoiHop_Click() {
      if (!this.infoPhieuHoTro.HTKH_ID) {
        this.$toast.error('Chưa chọn phiếu hổ trợ!')
        return
      }
      this.$refs.formGiaoPhieuPhoiHop.openDialog()
    },
    // Trả phiếu
    async tsbtnTraPhieu_Click() {
      try {
        this.loading(true)
        let data = {
          pHTKHID: this.infoPhieuHoTro.HTKH_ID,
          pPHIEUHTID: this.infoPhieuHoTro.PHIEUHT_ID,
          pTTHTID: 2,
          pNoiDung: this.infoKetQuaHoTro.NOIDUNG_HT
        }
        const rs = await this.$root.context.post('/web-cskh/api/xu-ly-thue-bao/traphieu-hotro', data)
        if (rs.error_code === 'BSS-00000000') {
          this.$toast.success('Thành công!')
        }
      } catch (error) {
        this.$toast.error(error.response.data.message)
      } finally {
        await this.getDSPhieuHT_pTTHTID()
        this.loading(false)
      }
    },
    async gridDsThueBaoKhieuNaiChuaGiao_rowSelected(data) {
      if (data) {
        console.log('Phiếu chưa giao = ', data)
        this.infoPhieuHoTro.MA_HO_TRO = data.MA_HT
        this.infoPhieuHoTro.PHIEUHT_ID = data.PHIEUHT_ID
        this.infoPhieuHoTro.HTKH_ID = data.HTKH_ID
        this.getThongTinPhieu(data.HTKH_ID)
        this.infoPhieuHoTro.QUYTRINH_ID = data.QUYTRINH_ID
        this.infoPhieuHoTro.TTHT_ID = data.TTHT_ID

        await this.getThongTinKhachHang(data.MA_TB)
        await this.getThongTinHoTroKH(data.MA_HT)

        if (this.IS_FORM_XACMINH) {
          this.infoKetQuaHoTro.NOIDUNG_HT = data.NOIDUNG_XACMINH
        }
      }
    },
    async gridDsThueBaoKhieuNaiDaGiao_rowSelected(data) {
      if (data) {
        console.log('Phiếu đã giao = ', data)
        this.infoPhieuHoTro.MA_HO_TRO = data.MA_HT
        this.infoPhieuHoTro.PHIEUHT_ID = data.PHIEUHT_ID
        this.infoPhieuHoTro.HTKH_ID = data.HTKH_ID

        await this.getThongTinKhachHang(data.MA_TB)
        await this.getThongTinHoTroKH(data.MA_HT)

        if (this.IS_FORM_XACMINH) {
          this.infoKetQuaHoTro.NOIDUNG_HT = data.NOIDUNG_XACMINH
        }
      }
    },
    async gridDsPhieuPhoiHop_rowSelected(data) {
      console.log('Phiếu phối hợp = ', data)
    },
    getdsLoaiKH: async function () {
      try {
        const rs = await this.$root.context.get(`/web-quantri/quan-ly-danh-ba/lay-danhmuc/LOAI_KH`)
        this.tmp_dsLoaiKH = rs.data
        this.loai_kh_tree = this.createTreeLoaiKH()
      } catch (error) {}
    },

    async getChuDeHt(pMa) {
      try {
        var rs = await this.$root.context.get(`/web-cskh/api/xu-ly-thue-bao/lay_danhsach_chude_ht/${pMa}`)
        this.dsChuDe = rs.data
      } catch (errrrrrr) {}
    },

    async getDichVu() {
      try {
        var rs = await this.$root.context.get('web-thuno/api/thu-no/common/dich-vu-vien-thong')
        this.dsDichVu = rs.data
        this.infoPhieuHoTro.DICHVUVT_ID = rs.data[0].DICHVUVT_ID
        if (this.infoPhieuHoTro.DICHVUVT_ID) {
          await this.getLoaiHinh(data)
        }
        // console.log('dsDichVu = ', this.dsDichVu)
      } catch (errrrrrr) {}
    },

    async getLoaiHinh(dichVuId) {
      try {
        var rs = await this.$root.context.get(`web-thuno/api/thu-no/common/loai-hinh-thue-bao/dich-vu-vien-thong/${dichVuId}`)
        this.dsLoaiHinhTB = rs.data

        // console.log('dsLoaiHinhTB = ', this.dsLoaiHinhTB)
      } catch (errrrrrr) {
        this.$toast.error(ex.response.data.message)
      }
    },

    setButton(temp) {
      if (temp == 0) {
        this.form.buttonGhiLai = false
        this.form.buttonFileDinhKem = false
        this.form.buttonTraPhieu = false
        this.form.buttonGiaoPhoiHop = false
        this.form.buttonHoanThanh = false
        this.form.buttonKhoaPhieu = false
      } else if (temp == 1) {
        this.form.buttonGhiLai = true
        this.form.buttonFileDinhKem = true
        this.form.buttonTraPhieu = true
        this.form.buttonGiaoPhoiHop = true
        this.form.buttonHoanThanh = true
        this.form.buttonKhoaPhieu = true
      }
    },

    async getDSNhomHoTro() {
      try {
        var rs = await this.$root.context.get('web-cskh/api/danh-muc/ho-tro?loaiDanhMuc=NHOM_HT')
        this.dsNhomHoTro = rs.data
        if (this.dsNhomHoTro.length > 0)
          if (this.infoPhieuHoTro.CHUDEHT_ID == '') await this.getChuDeHt(this.dsNhomHoTro[0].NHOMHT_ID)
          else await this.getChuDeHt(this.infoPhieuHoTro.NHOMHT_ID)
        // console.log('dsLoaiHoTro = ', rs)
      } catch (errrrrrr) {}
    },
    async changeNhom(e) {
      console.log('tets')
      await this.getChuDeHt(e.value)
    },
    async getThongTinHoTroKH(pMaHT) {
      try {
        var rs = await this.$root.context.get(`web-cskh/api/xu-ly-thue-bao/lay-thongtin-hotro-khachhang-theoma?pMaHT=${pMaHT}`)
        this.infoPhieuHoTro.data = rs.data
        if (this.infoPhieuHoTro.data) {
          console.log('getThongTinHoTroKH = ', this.infoPhieuHoTro.data)
          this.infoPhieuHoTro.NHOMHT_ID = this.infoPhieuHoTro.data[0].NHOMHT_ID
          this.infoPhieuHoTro.DICHVUVT_ID = this.infoPhieuHoTro.data[0].DICHVUVT_ID
          this.infoPhieuHoTro.LOAITB_ID = this.infoPhieuHoTro.data[0].LOAITB_ID
          this.infoPhieuHoTro.YEU_CAU_HT = this.infoPhieuHoTro.data[0].YC_HOTRO
          this.infoPhieuHoTro.TEN_TB = this.infoPhieuHoTro.data[0].TEN_TB
          this.infoPhieuHoTro.MA_TB = this.infoPhieuHoTro.data[0].MA_TB
          this.infoPhieuHoTro.MA_HO_TRO = this.infoPhieuHoTro.data[0].MA_HT
          this.infoPhieuHoTro.LOAIHT_ID = this.infoPhieuHoTro.data[0].LOAIHT_ID
          this.infoPhieuHoTro.NGAY_TIEP_NHAN = this.infoPhieuHoTro.data[0].NGAY_TN
          this.infoPhieuHoTro.DIA_CHI_LD = this.infoPhieuHoTro.data[0].DIACHI_LD
          this.infoPhieuHoTro.HTKN_ID = this.infoPhieuHoTro.data[0].HTTN_ID
          this.infoPhieuHoTro.CHUDEHT_ID = this.infoPhieuHoTro.data[0].CHUDEHT_ID
          this.infoKetQuaHoTro.HTHT_ID = this.infoPhieuHoTro.data[0].HTHT_ID
          this.infoKetQuaHoTro.KQXL_ID = this.infoPhieuHoTro.data[0].KQXL_ID
          this.infoKetQuaHoTro.NOIDUNG_HT = this.infoPhieuHoTro.data[0].NOIDUNG_HT
          this.infoKetQuaHoTro.MDHL_ID = this.infoPhieuHoTro.data[0].MDHL_ID
          this.infoKetQuaHoTro.NGAY_HT = this.infoPhieuHoTro.data[0].NGAY_HT
        }
        console.log('infoPhieuHoTro.data = ', this.infoPhieuHoTro.data)
      } catch (errrrrrr) {}
    },

    async getDSKenhTN() {
      try {
        var rs = await this.$root.context.get('web-cskh/api/danh-muc/ho-tro?loaiDanhMuc=HINHTHUC_KN')
        this.dsKenhHT = rs.data

        // console.log('dsKenhHT = ', rs)
      } catch (errrrrrr) {}
    },

    async getDSPhieuHT_pTTHTID() {
      try {
        this.loading(true)
        //var rs = await this.$root.context.get(`web-cskh/api/xu-ly-thue-bao/lay-danhsach-phieu-hotro-theo-trangthai-phieu?pTTHTID=2&pTTPHID=1&pQuyTrinhID=0`)
        var rs = await this.$root.context.get('web-cskh/api/xu-ly-thue-bao/lay-danhsach-phieu-hotro-theo-trangthai-phieu', {
          pTTHTID: this.TRANGTHAI_HOTRO,
          pTTPHID: 1,
          pQuyTrinhID: 0
        })
        this.dsThueBaoKhieuNaiChuaGiao = rs.data

        var rs1 = await this.$root.context.get('web-cskh/api/xu-ly-thue-bao/lay-danhsach-phieu-hotro-theo-trangthai-phieu', {
          pTTHTID: this.TRANGTHAI_HOTRO,
          pTTPHID: 3,
          pQuyTrinhID: 0
        })

        this.dsThueBaoKhieuNaiDaGiao = rs1.data
      } catch (errrrrrr) {
      } finally {
        this.loading(false)
      }
    },
    async getThongTinPhieu(_pHTKHID) {
      if (!this.IS_FORM_XACMINH) {
        let req = {
          pHTKHID: _pHTKHID,
          pTTHTID: this.infoPhieuHoTro.TTHT_ID
        }
        let rs = await this.$root.context.get('/web-cskh/api/xu-ly-thue-bao/lay-danhsach-phieugiao-phoihop', req)
        this.dsPhieuPhoiHop = rs.data
      }
    },

    async getThongTinKhachHang(pMaTB) {
      try {
        var rs = await this.$root.context.get(`/web-quantri/quan-ly-danh-ba/danh-ba-khach-hang-theo-ma/${pMaTB}`)
        this.infoKhachHang.data = rs.data
        console.log('infoKhachHang.data  = ', rs.data)
        if (this.infoKhachHang.data) {
          this.infoKhachHang.MaKH = this.infoKhachHang.data[0].MA_KH
          this.infoKhachHang.phanLoaiKH = this.infoKhachHang.data[0].TEN_LOAIKH
          this.infoKhachHang.tenKH = this.infoKhachHang.data[0].TEN_KH
          this.infoKhachHang.diaChiKH = this.infoKhachHang.data[0].DIACHI_KH
          this.infoKhachHang.maSoThue = this.infoKhachHang.data[0].MST
          this.infoKhachHang.soDienThoai = this.infoKhachHang.data[0].SO_DT
          this.infoKhachHang.email = this.infoKhachHang.data[0].EMAIL
          this.infoKhachHang.nguoiYC = this.infoKhachHang.data[0].NGUOI_DD
        }
      } catch (errrrrrr) {}
    },

    async getDSHinhThuc() {
      try {
        var rs = await this.$root.context.get('web-cskh/api/danh-muc/ho-tro?loaiDanhMuc=HINHTHUC_HT')
        this.dsHinhThuc = rs.data
        if (this.dsHinhThuc.length > 0) this.infoKetQuaHoTro.HTHT_ID = this.dsHinhThuc[0].HTKN_ID
      } catch (errrrrrr) {}
    },

    async ShowPopupFile() {
      if (this.infoPhieuHoTro.HTKH_ID == null) {
        this.$toast.error('Chưa chọn phiếu hổ trợ!')
        return
      }
      this.$refs.popupUploadFile.htkh_id = this.infoPhieuHoTro.HTKH_ID
      this.$refs.popupUploadFile.showModal()
    },

    async onChangeDichVu() {
      if (this.infoPhieuHoTro.DICHVUVT_ID) {
        await this.getLoaiHinh(this.infoPhieuHoTro.DICHVUVT_ID)
      }
    },

    async onChangeNhomHT() {
      if (this.infoPhieuHoTro.NHOMHT_ID) {
        await this.getChuDeHt(this.infoPhieuHoTro.NHOMHT_ID)
      }
    },

    async tsbtnKhoaPhieu_Click() {
      try {
        let isConfirm = false
        await this.$bvModal
          .msgBoxConfirm('Bạn có muốn khóa phiếu này không!', {
            title: 'Thông báo',
            centered: true,
            size: 'md',
            okTitle: 'Đồng ý',
            cancelTitle: 'Hủy'
          })
          .then((value) => {
            if (value) {
              isConfirm = true
            }
          })
        if (!isConfirm) {
          return
        }
        this.loading(true)
        if (this.infoPhieuHoTro.HTKH_ID == null) {
          this.$toast.error('Chưa chọn phiếu hổ trợ!')
          return
        }
        if (this.infoKetQuaHoTro.KQXL_ID == null) {
          this.$toast.error('Chưa chọn kết quả!')
          return false
        }

        if (this.infoKetQuaHoTro.MDHL_ID == null) {
          this.$toast.error('Chưa chọn mức độ hài lòng!')
          return false
        }
        let data = {
          htkh_id: this.infoPhieuHoTro.HTKH_ID,
          kqxl_id: this.infoKetQuaHoTro.KQXL_ID,
          hailong: this.infoKetQuaHoTro.MDHL_ID,
          phieuht_id: this.infoPhieuHoTro.PHIEUHT_ID
        }
        const rs = await this.axios.put(`/web-cskh/api/tiep-nhan-khach-hang/khoa-phieu`, data)
        if (rs.data.error_code === 'BSS-00000000') {
          this.$toast.success('Khóa phiếu thành công!')
        }
      } catch (e) {
        this.$toast.error(e.data.message)
      } finally {
        await this.getDSPhieuHT_pTTHTID()
        this.loading(false)
      }
    }
  }
}
</script>
