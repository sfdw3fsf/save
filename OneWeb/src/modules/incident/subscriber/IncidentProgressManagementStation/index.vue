<template src="./index.html"></template>
<style src="./style.scss" scoped></style>
<style>
.overlay-common .loading {
  display: flex;
  justify-content: center;
  flex-direction: column;
}

.overlay-common.show {
  display: flex;
  justify-content: center;
}

.overlay-common {
  background-color: rgba(0, 0, 0, 0.7);
}

.overlay-common .loading-panel {
  display: flex;
  text-align: center;
  background-color: #fff;
  padding: 10px 15px;
  align-items: center;
  color: #035fcc;
  border: 1px solid #035fcc;
  border-radius: 7px;
}

.overlay-common .loading-panel .loading-content {
  flex-grow: 1;
  padding-left: 10px;
}
</style>
<script>
import moment from 'moment'
import ModalExport from './components/modal-export'
import api from './api'

export default {
  components: { ModalExport },
  data: function() {
    return {
      header: {
        title: 'KDV - XỬ LÝ YÊU CẦU KHÓA MÁY CỐ ĐỊNH',
        list: [
          { name: 'Xử lý thuê bao', link: { name: 'Ui.cards' } },
          {
            name: 'Hoàn thiện khóa mở nợ cước',
            link: { name: 'Ui.buttons' }
          }
        ]
      },
      luong_id: 0,
      dtThaoTac: [],
      columns: [
        { fieldName: 'MA_TB', headerText: 'Số máy/Acc', allowFiltering: true, allowSorting: true },
        { fieldName: 'LOAIHINH_TB', headerText: 'Loại hình', allowFiltering: true, allowSorting: true },
        { fieldName: 'TEN_TB', headerText: 'Tên thuê bao', allowFiltering: true, allowSorting: true },
        { fieldName: 'DIACHI_LD', headerText: 'Địa chỉ lắp đặt', allowFiltering: true, allowSorting: true },
        { fieldName: 'TEN_DV', headerText: 'Đơn vị gửi', allowFiltering: true, allowSorting: true },
        { fieldName: 'CHIEU_KM', headerText: 'Chiều khóa/mở', allowFiltering: true, allowSorting: true },
        { fieldName: 'TEN_STATUS', headerText: 'Kích hoạt Visa/Vasc', allowFiltering: true, allowSorting: true },
        { fieldName: 'HOST_LD', headerText: 'Host LĐ', allowFiltering: true, allowSorting: true },
        { fieldName: 'NGAYGIAO', headerText: 'Ngày giao', allowFiltering: true, allowSorting: true },
        { fieldName: 'CACH_KM', headerText: 'Cách khóa/mở', allowFiltering: true, allowSorting: true },
        { fieldName: 'NV_XACNHAN', headerText: 'NV xác nhận', allowFiltering: true, allowSorting: true },
        { fieldName: 'KQ_XACNHAN', headerText: 'KQ xác nhận', allowFiltering: true, allowSorting: true },
        { fieldName: 'ND_THUCHIEN', headerText: 'ND thực hiện', allowFiltering: true, allowSorting: true },
        { fieldName: 'NGAY_KYTHUAT_XM', headerText: 'Ngày XN', allowFiltering: true, allowSorting: true }
      ],
      ttPhieu: {
        dichVuId: '',
        quyTrinhId: '',
        loaiHinhCkb: false,
        loaiHinhId: '',
        kieuYcId: '1',
        rdGiao: '0',
        noiDungTH: '',
        bqKichHoat: true,
        bqHoanCong: true,
        cbkNgayGv: false,
        ngayGV: new Date(),
        cbkNgayHt: false,
        ngayHT: new Date(),
        idNguoiGv: '',
        huongGiaoId: '',
        maTb: ''
      },
      dsDichVu: [],
      dsQuyTrinh: [],
      dsLoaiHinh: [],
      dsKieuYc: [
        {
          KIEU_ID: '1',
          TENKIEU: 'Khóa máy do nợ cước'
        },
        {
          KIEU_ID: '0',
          TENKIEU: 'Mở máy nợ cước'
        }
      ],
      dsNguoiGv: [],
      rdLoaiPhieu: '0',
      dsThueBao: [],
      dsNhanVien: [],
      dsLyDo: [],
      dsIdPhieu: [],
      dsHuongGiao: [],
      itemSelect: null,
      msgThongBao: {
        11: 'Bạn có chắc chắn muốn kích hoạt các thuê bao IMS này ?',
        4: 'Bạn có chắc chắn muốn kích hoạt các thuê bao đã chọn hay không?',
        6: 'Bạn có chắc chắn muốn kích hoạt các thuê bao đã chọn hay không?',
        1: 'Bạn có chắc chắn kích hoạt lên tổng đài hay không?',
        2: 'Bạn có chắc chắn muốn kích hoạt các dịch vụ này không?',
        10: 'Bạn có chắc chắn muốn kích hoạt các dịch vụ này không?'
      },
      msgKichHoat: '',
      kt_tthd_kichhoat: false,
      thongbao: 'Thông báo:'
    }
  },
  validations: {},
  watch: {},
  created: async function() {
    this.dsDichVuDropDown()
    this.getDsNguoiGV()
    const gtts = await this.getTSMDByTS('TS_KIEMTRA_TTHD_KICHHOAT')
    this.kt_tthd_kichhoat = gtts == 1 ? true : false
  },
  async mounted() {
    await this.Lay_DS_ThaoTac()
    $(document).on('click', '.btn-del', (evt) => {
      const data = evt.currentTarget.getAttribute('data-id')
      this.deleteLyDoHuy(data)
    })
  },
  methods: {
    PHAILAM: function(code) {
      code = code.trim().toUpperCase()
      if (this.dtThaoTac.filter((x) => x.enable === 1 && x.code === code).length > 0) {
        return true
      }
      return false
    },
    async Lay_DS_ThaoTac() {
      try {
        let post = {
          luong_id: this.luong_id
        }
        const response = await api.lay_luong_thaotac(this.axios, post)
        const data = await response.data
        if (data.error_code === 'BSS-00000000' && response.status === 200) {
          if (data.data.length > 0) {
            this.dtThaoTac = data.data
          }
        } else {
          console.log('Không có dữ liệu')
        }
        console.log(this.dtThaoTac)
      } catch (e) {
        console.log('Lấy danh sách không thành công')
      } finally {
      }
    },
    doSearch() {
      this.getDsThueBao(this.ttPhieu.maTb)
    },
    onClickCapNhat() {
      if (this.validateUpdate()) {
        // const lstChosen = this.dsThueBao.filter((item) => this.dsIdPhieu.includes(item.PHIEU_ID));
        const lstChosen = this.$refs.tableThueBao.getSelectedRecords() || []
        const dsGvNv = lstChosen.map((item) => ({
          ghiChu: this.dsKieuYc.filter((item) => item.KIEU_ID == this.ttPhieu.kieuYcId)[0].TENKIEU,
          hdtbid: item.HDTB_ID,
          ngayGiao: moment(this.ttPhieu.ngayGV).format('DD/MM/YYYY'),
          nhanVienGiaoId: this.ttPhieu.idNguoiGv,
          nhanVienTHId: this.$auth.getNhanVienID(),
          nhiemVu: this.dsKieuYc.filter((item) => item.KIEU_ID == this.ttPhieu.kieuYcId)[0].TENKIEU,
          nhienVuId: 7,
          phieuId: item.PHIEU_ID
        }))
        const body = {
          dsGiaoViecNV: dsGvNv,
          quyTrinhId: this.ttPhieu.quyTrinhId,
          tthdid: 4
        }
        this.update(body)
      }
    },
    async onClickKichHoat() {
      if (await this.validateKichHoat()) {
        this.msgKichHoat = this.msgThongBao[this.ttPhieu.dichVuId]
        // const lstChosen = this.dsThueBao.filter((item) => this.dsIdPhieu.includes(item.PHIEU_ID));
        const lstChosen = this.$refs.tableThueBao.getSelectedRecords() || []
        lstChosen.length > 0 && this.$bvModal.show('notice-kichhoat')
      }
    },
    closeKHModal(value) {
      this.$bvModal.hide('notice-kichhoat')
      if (value) {
        // const lstChosen = this.dsThueBao.filter((item) => this.dsIdPhieu.includes(item.PHIEU_ID));
        const lstChosen = this.$refs.tableThueBao.getSelectedRecords() || []
        lstChosen.forEach((element) => {
          //   console.log(element);
          this.kichHoat(element)
        })
      }
    },
    async onClickHoanCong() {
      if (await this.validateHoanCong()) {
        // const dsChecked = this.dsThueBao.filter((item) => this.dsIdPhieu.includes(item.PHIEU_ID));
        const dsChecked = this.$refs.tableThueBao.getSelectedRecords() || []
        if (dsChecked.length > 0) {
          const ds = dsChecked.map((item) => ({
            chieuKhoa: item.CHIEUKHOA,
            dichVuVTId: item.DICHVUVT_ID,
            giaoOMC: 2,
            hdkhId: item.HDKH_ID,
            hdtbId: item.HDTB_ID,
            kieuQuyTrinhId: 0,
            loaiTBId: item.LOAITB_ID,
            maTB: item.MA_TB,
            phieuId: item.PHIEU_ID,
            status: item.STATUS,
            thueBaoId: item.THUEBAO_ID
          }))
          const body = {
            boQuaLoiHoanCong: this.ttPhieu.bqHoanCong,
            donViId: this.$auth.getDonViID(),
            ds: ds,
            hdtbId: this.itemSelect.HDTB_ID,
            huongGiao: this.dsHuongGiao.length > 0 ? this.dsHuongGiao[0].HUONGGIAO_ID : 0,
            kieuYC: this.ttPhieu.kieuYcId,
            luongId: this.dsHuongGiao.length > 0 ? this.dsHuongGiao[0].LUONG_ID : 0,
            maNguoiDung: this.$auth.getMaNhanVien(),
            ngayGV: moment(this.ttPhieu.ngayGV).format('DD/MM/YYYY HH:mm'),
            ngayHT: moment(this.ttPhieu.ngayHT).format('DD/MM/YYYY HH:mm'),
            nhanVienId: this.$auth.getNhanVienID(),
            thuHoiThietBi: Number(this.ttPhieu.rdGiao)
          }
          this.hoanCong(body)
        }
      }
    },
    onClickExPort() {
      this.$bvModal.show('modal-export')
    },
    onClickUpdateNDTH() {
      if (this.itemSelect) {
        this.updateNoiDungTH()
      }
    },
    validateUpdate() {
      if (this.dsThueBao.length == 0) {
        this.$toast.error('Không có thuê bao trên lưới để cập nhật!')
        return false
      }
      const lstChosen = this.$refs.tableThueBao.getSelectedRecords() || []
      if (lstChosen.length == 0) {
        this.$toast.error('Bạn chưa chọn thuê bao trên lưới để cập nhật!')
        return false
      }
      if (this.ttPhieu.ngayGV > this.ttPhieu.ngayHT) {
        this.$toast.error('Ngày giao việc không được lớn hơn ngày hoàn thành!')
        return false
      }
      return true
    },
    validateKichHoat: async function() {
      this.thongbao = 'Thông báo:'
      const lstChosen = this.$refs.tableThueBao.getSelectedRecords() || []
      //   const lstErr = this.dsThueBao.filter((item) => this.dsIdPhieu.includes(item.PHIEU_ID) && item.NGAY_HT_GP == "");
      const lstErr = lstChosen.filter((item) => item.NGAY_HT_GP == '')
      if (lstErr.length > 0) {
        this.$toast.error(`Thuê bao: ${lstErr[0].MA_TB} chưa cập nhật thông tin.\nBạn không thể Kích hoạt thuê bao này!`)
        return false
      }
      if (this.dsNhanVien.length == 0) {
        this.$toast.error(`Bạn chưa cập nhật thông tin giao việc cho nhân viên!`)
        return false
      }
      //   const lstChosen = this.dsThueBao.filter((item) => this.dsIdPhieu.includes(item.PHIEU_ID));
      if (lstChosen.length == 0) {
        this.$toast.error(`Bạn chưa chọn thuê bao trên lưới để kích hoạt !`)
        return false
      }
      const statusErr = lstChosen.filter((item) => item.STATUS == 5 || item.STATUS == 7)
      if (statusErr.length > 0) {
        this.$toast.error(`Thuê bao: ${statusErr[0].MA_TB} đã được kích hoạt!`)
        return false
      }
      const ttHdErr = lstChosen.filter((item) => item.TTHD_ID == 6 || item.TTHD_ID == 7)
      if (ttHdErr.length > 0) {
        this.$toast.error(`Hợp đồng đã hoàn thiện. Hãy lấy lại thông tin trước khi kích hoạt!`)
        return false
      }

      // if (this.dtThaoTac.length > 0) {
      //   const phaiLam = this.dtThaoTac.filter((item) => item.ENABLE == "1" && item.CODE == "CAPNHAT_NGAYHT_KH");
      //   if (phaiLam.length > 0) {
      //     const kyHd = `${moment(new Date()).subtract(1, "months").endOf("month").format("YYYYMM")}01`;
      //     const daChot = await this.checkKyCuocChot(kyHd);
      //     if (daChot != 0) {
      //       this.$toast.error("Danh bạ tháng đã được chốt, bạn không thể sửa ngày hoàn thành. Liên hệ Admin để xử lý !");
      //       return false;
      //     }
      //   }
      // }

      let msg = ''
      let rst = true
      if (this.kt_tthd_kichhoat) {
        for (let index = 0; index < lstChosen.length; index++) {
          const element = lstChosen[index]
          const tthdId = await this.getTTHDId(element.HDTB_ID)
          //   console.log("tthdId", tthdId);
          if (this.bqKichHoat) {
            if (tthdId == 6 || (tthdId == 7 && !this.bqKichHoat)) {
              msg = `${element.MA_TB} đã Hoàn thiện hoặc Thoái trả. Hãy lấy lại thông tin trước khi kích hoạt!`
              rst = false
              break
            }
          } else {
            this.thongbao += `${element.MA_TB};`
          }
        }
        if (!rst) {
          this.$toast.error(msg)
          return false
        }
      }

      return true
    },
    validateHoanCong: async function() {
      if (this.dsNhanVien.length == 0) {
        this.$toast.error(`Bạn chưa cập nhật thông tin giao việc cho nhân viên!`)
        return false
      }
      const lstChosen = this.$refs.tableThueBao.getSelectedRecords() || []
      //   const lstErr = this.dsThueBao.filter((item) => this.dsIdPhieu.includes(item.PHIEU_ID) && item.NGAY_HT_GP == "");
      const lstErr = lstChosen.filter((item) => item.NGAY_HT_GP == '')
      if (lstErr.length > 0) {
        this.$toast.error(`Thuê bao: ${lstErr[0].MA_TB} chưa cập nhật thông tin.\nBạn không thể Kích hoạt thuê bao này!`)
        return false
      }

      //   const lstChosen = this.dsThueBao.filter((item) => this.dsIdPhieu.includes(item.PHIEU_ID));
      if (lstChosen.length == 0) {
        this.$toast.error(`Bạn chưa chọn thuê bao trên lưới để kích hoạt !`)
        return false
      }

      return true
    },
    checkRow(value) {
      this.dsIdPhieu = value
    },
    selectRow(value) {
      this.itemSelect = value
      if (value) {
        this.ttPhieu.maTb = value.MA_TB
        this.ttPhieu.noiDungTH = value.ND_THUCHIEN
        this.getDsNV()
        this.getDsLyDo(this.itemSelect.HDTB_ID)
      }
    },
    clickRowThueBao(index, value) {
      this.itemSelect = value
      if (value) {
        this.ttPhieu.maTb = value.MA_TB
        this.ttPhieu.noiDungTH = value.ND_THUCHIEN
        this.getDsNV()
        this.getDsLyDo(this.itemSelect.HDTB_ID)
      }
    },
    selectRowNv(value) {
      this.nVSelected = value
    },
    dsDichVuDropDown: async function() {
      try {
        var rs = await this.$root.context.get('/web-thuno/api/thu-no/common/dich-vu-vien-thong')
        this.dsDichVu = rs.data.sort(function(a, b) {
          return a.DICHVUVT_ID - b.DICHVUVT_ID
        })
        if (rs.data.length > 0) {
          this.ttPhieu.dichVuId = rs.data[0].DICHVUVT_ID
          this.getDsQuyTrinh()
        }
      } catch (error) {
      } finally {
      }
    },
    getTSMD: async function() {
      try {
        var rs = await this.$root.context.get('/web-thuno/api/thu-no/thong-tin-khach-hang/lay-danh-sach-tham-so-mac-dinh', {
          pPhanVungid: this.$root.token.getPhanVungID(),
          pMaThamSo: 'LAY_QUYTRINH_PROC'
        })
        if (rs.data && rs.data.length > 0) {
          return rs.data[0].MACDINH
        }
        return ''
      } catch (error) {}
    },
    getTSMDByTS: async function(tsmd) {
      try {
        var rs = await this.$root.context.get('/web-thuno/api/thu-no/thong-tin-khach-hang/lay-danh-sach-tham-so-mac-dinh', {
          pPhanVungid: this.$root.token.getPhanVungID(),
          pMaThamSo: tsmd
        })
        if (rs.data && rs.data.length > 0) {
          return rs.data[0].TEN_TS
        }
        return ''
      } catch (error) {}
    },
    getTTHDId: async function(hdtbId) {
      const body = {
        tenId: 'TTHD_ID',
        ma: `${hdtbId}`
      }
      try {
        var rs = await this.$root.context.post('/web-thuno/api/thu-no/common/lay-id-theo-ma', body)
        if (rs.data && rs.data.length > 0) {
          return rs.data[0].TTHD_ID
        }
        return ''
      } catch (error) {}
    },
    getDsQuyTrinh: async function() {
      const xacMinhTYC = await this.getTSMD()
      const param = {
        pXacMinhTheoYC: xacMinhTYC == 1 ? 1 : 0,
        pDSLoaiHDId: 7, //1,//
        pDSDichVuVTId: this.ttPhieu.dichVuId,
        pDSTTHDId: 15, //4, //
        pKieuYC: 1
      }
      try {
        var rs = await this.$root.context.get('/web-thuno/api/thu-no/xu-ly-thue-bao/duyet-ket-qua-xu-ly-no-cuoc/lay-quy-trinh-nghiep-vu', param)
        this.dsQuyTrinh = rs.data
        if (rs.data.length > 0) {
          this.ttPhieu.quyTrinhId = rs.data[0].QUYTRINH_ID
          this.getLoaiHinh()
          this.getHuongGiao()
        } else {
          this.header.title = 'KDV - XỬ LÝ YÊU CẦU KHÓA MÁY CỐ ĐỊNH'
          this.ttPhieu.huongGiaoId = ''
          this.dtThaoTac = []
          this.dsLoaiHinh = []
        }
      } catch (error) {}
    },
    getLoaiHinh: async function() {
      try {
        var rs = await this.$root.context.get(`/web-thuno/api/thu-no/common/loai-hinh-thue-bao/quy-trinh/${this.ttPhieu.quyTrinhId}`)
        this.dsLoaiHinh = rs.data
        if (rs.data && rs.data.length > 0) {
          this.ttPhieu.loaiHinhId = rs.data[0].LOAITB_ID
        }
      } catch (error) {}
    },
    getDsNguoiGV: async function() {
      try {
        var rs = await this.$root.context.get(`/web-thuno/api/thu-no/common/ds-nguoi-giao-viec/don-vi/${this.$auth.getDonViID()}`)
        this.dsNguoiGv = rs.data
        if (rs.data && rs.data.length > 0) {
          this.ttPhieu.idNguoiGv = rs.data[0].NHANVIEN_ID
        }
      } catch (error) {}
    },
    getDsThueBao: async function(txtMatb = '') {
      this.$root.showLoading(true)
      const param = {
        dichVuVTId: this.ttPhieu.dichVuId, //4,
        huongGiao: this.ttPhieu.huongGiaoId, //this.huongGiaoId ? this.huongGiaoId : 63, //58,
        kieuYC: this.ttPhieu.kieuYcId,
        loaiId: this.rdLoaiPhieu,
        loaiTBId: this.ttPhieu.loaiHinhCkb ? this.ttPhieu.loaiHinhId : '',
        maGD: txtMatb, //"0",
        nhanVienId: this.$auth.getNhanVienID(), //17101, //
        TTHDId: 16 //4, //2,
      }
      try {
        var rs = await this.$root.context.get(`/web-thuno/api/thu-no/xu-ly-thue-bao/ds-hdtb-khoa-may-hoan-cong`, param)
        this.dsThueBao = rs.data.data
        this.$root.showLoading(false)
        if (rs.data.data.length == 0) {
          this.$toast.error('Không có dữ liệu, vui lòng thử lại')
          this.$refs.maTb.focus()
          this.dsNhanVien = []
          this.dsLyDo = []
          this.ttPhieu.maTb = ''
        }
      } catch (error) {
        this.$root.showLoading(false)
        this.$toast.error('Không có dữ liệu, vui lòng thử lại')
        this.$refs.maTb.focus()
        this.dsNhanVien = []
        this.dsLyDo = []
        this.ttPhieu.maTb = ''
      }
    },
    getHuongGiao: async function() {
      try {
        var rs = await this.$root.context.get(`/web-thuno/api/thu-no/css/huong-giao/quy-trinh`, {
          quyTrinhId: this.ttPhieu.quyTrinhId, //"450", //
          tthdId: 16 //4,
        })
        this.dsHuongGiao = rs.data
        if (rs.data && rs.data.length > 0) {
          this.header.title = rs.data[0].HUONGGIAO
          this.ttPhieu.huongGiaoId = rs.data[0].HUONGGIAO_ID
          this.luong_id = rs.data[0].LUONG_ID
          await this.Lay_DS_ThaoTac()
        } else {
          this.header.title = 'KDV - XỬ LÝ YÊU CẦU KHÓA MÁY CỐ ĐỊNH'
          this.ttPhieu.huongGiaoId = ''
          this.dtThaoTac = []
        }
      } catch (error) {}
    },
    updateNoiDungTH: async function() {
      const body = {
        phieuId: this.itemSelect.PHIEU_ID,
        noiDung: this.ttPhieu.noiDungTH
      }
      try {
        const { data } = await this.axios.put('/web-thuno/api/thu-no/css/cap-nhat-noi-dung-theo-phieu-giao', body)
        if (data && data.error == '200') {
          this.$toast.success(`Cập nhật dữ liệu thành công`)
          this.getDsThueBao()
        }
      } catch (error) {
        this.$toast.error(`Cập nhật dữ liệu thất bại`)
      }
    },
    getDsNV: async function() {
      let param = {
        donViQLId: this.$auth.getDonViID(),
        kieuGiao: 0,
        loaiNVId: 39,
        nhanVienGiaoId: this.ttPhieu.idNguoiGv,
        phieuId: this.itemSelect.PHIEU_ID
      }
      try {
        var rs = await this.$root.context.get(`/web-thuno/api/thu-no/xu-ly-thue-bao/ds-nhan-vien-theo-phieu`, param)
        this.dsNhanVien = rs.data.data
      } catch (error) {}
    },
    getDsLyDo: async function(hdtbId) {
      try {
        var rs = await this.$root.context.get(`/web-thuno/api/thu-no/xu-ly-thue-bao/ly-do-huy/hop-dong-thue-bao/${hdtbId}`)
        this.dsLyDo = rs.data.map((item) => ({
          ...item,
          DELETE: `<button class="btn btn-main pad4 lh14 btn-del btn-danger" data-id="${item.LYDOHUY_ID}">
                    <span class="-ap icon-close"></span>
                  </button>`
        }))
      } catch (error) {}
    },
    // getdtThaoTac: async function (luongId) {
    //   try {
    //     var rs = await this.$root.context.get(`/web-thuno/api/thu-no/common/thao-tac/luong/${luongId}`);
    //     this.dtThaoTac = rs.data;
    //   } catch (error) { }
    // },
    checkKyCuocChot: async function(kyHoaDon) {
      try {
        var rs = await this.$root.context.get(`/web-thuno/api/thu-no/common/kiem-tra-danh-ba-thang-da-chot/${kyHoaDon}`)
        if (rs.data.length > 0) {
          return rs.data[0].RESULT
        }
        return ''
      } catch (error) {}
    },
    deleteLyDoHuy: async function(lyDoHuyId) {
      const body = {
        hdtbId: this.itemSelect.HDTB_ID,
        lyDoHuyId: lyDoHuyId
      }
      try {
        const { data } = await this.axios.delete('/web-thuno/api/thu-no/css/ly-do-huy', { data: body })
        if (data && data.error == '200') {
          this.$toast.success('Xóa thông tin thành công!')
          this.getDsLyDo(this.itemSelect.HDTB_ID)
        } else {
          this.$toast.error('Xóa thông tin thất bại!')
        }
      } catch (error) {
        this.$toast.error(error.data.message_detail)
      }
    },
    update: async function(body) {
      try {
        const { data } = await this.axios.put('/web-thuno/api/thu-no/xu-ly-thue-bao/cap-nhat-giao-viec', body)
        if (data && data.error == '200') {
          this.$toast.success('Cập nhật dữ liệu thành công!')
          this.getDsThueBao()
        } else {
          this.$toast.error('Cập nhật dữ liệu thất bại!')
        }
      } catch (error) {
        this.$toast.error('Cập nhật dữ liệu thất bại!')
      }
    },
    kichHoat: async function(body) {
      try {
        const { data } = await this.axios.put(`/web-thuno/api/thu-no/xu-ly-thue-bao/kich-hoat-dich-vu/hop-dong-thue-bao/${body.HDTB_ID}`)
        if (data && data.error == '200') {
          this.nangTocDo(body)
        } else {
          this.$toast.error(data.message_detail)
        }
      } catch (error) {
        this.$toast.error(error.data.message_detail)
      }
    },
    nangTocDo: async function(body) {
      try {
        const { data } = await this.axios.put(`/web-thuno/api/thu-no/xu-ly-thue-bao/nang-toc-do/${body.THUEBAO_ID}`)
        if (data && data.error == '200') {
          this.capNhatNgay(body)
          // this.$toast.success("Kích hoạt thành công!");
          // this.getDsThueBao();
        } else {
          this.$toast.error('Kích hoạt thất bại!')
        }
      } catch (error) {
        this.$toast.error('Kích hoạt thất bại!')
      }
    },
    capNhatNgay: async function(body) {
      const bd = {
        hdtbId: body.HDTB_ID,
        ngayKH: moment(new Date()).format('DD/MM/YYYY'),
        ngayHT: moment(new Date()).format('DD/MM/YYYY'),
        status: body.DICHVUVT_ID == 1 ? 7 : 5
      }
      try {
        const { data } = await this.axios.put(`/web-thuno/api/thu-no/xu-ly-thue-bao/cap-nhat-ngay-kh-ngay-ht-v2`, bd)
        if (data && data.error == '200') {
          this.$toast.success('Kích hoạt thành công!')
          this.getDsThueBao()
        } else {
          this.$toast.error('Kích hoạt thất bại!')
        }
      } catch (error) {
        this.$toast.error('Kích hoạt thất bại!')
      }
    },
    hoanCong: async function(body) {
      console.log('body.luongId')
      console.log(body.luongId)
      console.log(this.PHAILAM)
      if (this.PHAILAM('TUDONG_HTHS')) {
        // hoancong.GIAOPHIEU_TD_KHI_HC(hdtb_id, phieu_id, hg_phieu_id, tt_nd.donvi_id, tt_nd.donvi_id, tt_nd.nhanvien_id, tt_nd.ma_nd, tt_nd.may_cn);
        // new HTHSDoiSoThueBaoFacade().HOANTHIEN_HS_THAYDOI_MATB(hdtb_id);
        // bangts.CapNhat_TT_Phieu_GPhone(hdtb_id);
        await this.giaophieu_td_khi_hc(this.hdtb_id, this.phieu_id, hg_phieu_id, this.donViId, this.donViId, this.nhanvienId)
        await this.hoanthien_hs_thaydoi_matb(this.hdtb_id)
        // await this.CapNhat_TT_Phieu_GPhone(this.hdtb_id);
        if (this.PHAILAM('TUDONG_THUHOI_KHI_HOANCONG')) {
          await this.giaophieu_td_host_thuhoi_1(this.hdtb_id, this.phieu_id, this.donViId, this.nhanvienId, 0)
        }
        str = 'Cập nhật dữ liệu và hoàn thiện hồ sơ thành công !'
      } else {
        await this.giaophieu_td_khi_hc(this.hdtb_id, this.phieu_id, hg_phieu_id, this.donViId, this.donViId, this.nhanvienId)
      }
      // try {
      //   const { data } = await this.axios.put(`/web-thuno/api/thu-no/xu-ly-thue-bao/hoan-cong`, body);
      //   if (data && data.error == "200") {
      //     this.$toast.success("Hoàn công thành công!");
      //     this.getDsThueBao();
      //   } else {
      //     this.$toast.error("Hoàn công thất bại!");
      //   }
      // } catch (error) {
      //   this.$toast.error("Hoàn công thất bại!");
      // }
    }
  }
}
</script>
