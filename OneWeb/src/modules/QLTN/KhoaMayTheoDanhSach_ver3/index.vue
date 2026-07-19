<template src='./index.html'></template>

<style src='./styles.scss' scoped></style>

<script>
import { mapActions, mapState } from 'vuex'
import KyCuoc from '../components/KyCuoc.vue'
import moment from 'moment'
import Calculator from '@/components/Calculator'
import PopupDocFileKhoaMay from './popup-docfile.vue'
import { CheckBoxSelection } from '@syncfusion/ej2-vue-dropdowns'
import ModalXuatDuLieu from './xuat-du-lieu'
import { L10n } from '@syncfusion/ej2-base'
import { Tooltip } from '@syncfusion/ej2-popups'
import { previewPrint } from '../../../utils/util'
import FilterDialog from './FilterDialog.vue'
import {
  DichVuVienThong
} from '@/const/enums'

L10n.load({
  'vi-VN': {
    'multi-select': {
      noRecordsTemplate: 'Không có dữ liệu',
      overflowCountTemplate: 'và ${count} giá trị khác..',
      totalCountTemplate: '${count} giá trị'
    }
  }
})
export default {
  components: {
    appKyCuoc: KyCuoc,
    Calculator,
    PopupDocFileKhoaMay,
    appModalXuatDuLieu: ModalXuatDuLieu,
    FilterDialog
  },
  name: 'KhoaMayThueBaoNoCuoc',
  provide: {
    multiselect: [CheckBoxSelection]
  },
  mounted() { },
  computed: {
    ...mapState('khoamaythuebaonocuoc', [
      'danhsachDonViQL',
      'danhsachQuyTrinh',
      'danhsachLoaiPhieu',
      'danhsachKhoaThueBao'
    ]),
    ...mapState('qltnCommon', [
      'danhSachDichVuVienThong',
      'danhSachLoaiHinhThueBao',
      'danhSachKhLon',
      'danhSachLoaiKH',
      'danhMucQuanHuyen'
    ]),
    dsThuocTinh() {
      return this.fields.map((x) => x.fieldName)
    },
    getColumns() {
      return this.fields.filter(x => x.fieldName === 'TIENNO' || x.fieldName === 'NO_DK' || x.fieldName === 'NO_PS').map(x => ({ fieldName: x.fieldName, headerText: x.headerText }))
    }
  },
  data() {
    return {
      pKyCuoc: '',
      fields: [
        { fieldName: '_CHECK', visible: false },
        { fieldName: 'MA_TB', headerText: 'Số máy/ACC', allowFiltering: true, width: 120 },
        { fieldName: 'MA_TT', headerText: 'Mã TT', allowFiltering: true, width: 120 },
        { fieldName: 'TRANGTHAI_ID', headerText: 'Trạng thái ID', allowFiltering: true, width: 120 },
        { fieldName: 'GHICHU', headerText: 'Ghi chú', allowFiltering: true },
        { fieldName: 'TIENNO', headerText: 'Tổng nợ', allowFiltering: true, type: 'number', format: 'N0', width: 120 },
        { fieldName: 'NO_DK', headerText: 'Nợ ĐK', allowFiltering: true, type: 'number', format: 'N0', width: 120 },
        { fieldName: 'NO_PS', headerText: 'Nợ PS', allowFiltering: true, type: 'number', format: 'N0', width: 120 },
        { fieldName: 'TRANGTHAI_TB', headerText: 'Trạng thái TB', allowFiltering: true },
        { fieldName: 'LOAITB_ID', headerText: '', visible: false, width: 80 },
        { fieldName: 'DICHVUVT_ID', headerText: '', visible: false, width: 80 },
        { fieldName: 'TRANG_THAI', headerText: 'Trạng thái', allowFiltering: true },
        { fieldName: 'MANV_TC', headerText: 'Mã NVTC', allowFiltering: true },
        { fieldName: 'DIACHI_TT', headerText: 'Địa chỉ TT', allowFiltering: true },
        { fieldName: 'DIACHI_LD', allowFiltering: true, headerText: 'Địa chỉ LĐ' },
        { fieldName: 'LYDO_TRA', headerText: 'Lý do trả', allowFiltering: true },
        { fieldName: 'DIEMTINNHIEM', headerText: 'Điểm TN', allowFiltering: true, width: 120 },
        { fieldName: 'DIENTHOAI', headerText: 'SĐT nhắc nợ', allowFiltering: true, width: 120 },
        { fieldName: 'MA_PLKH', headerText: 'Mã PLKH', allowFiltering: true, width: 120 },
        { fieldName: 'TEN_TT', headerText: 'Tên thanh toán', allowFiltering: true }
      ],
      fieldsKhongKhoa: [
        {
          fieldName: '_CHECK',
          visible: false
        },
        {
          fieldName: 'MA_TB',
          headerText: 'Số máy/ACC',
          allowFiltering: true
        },
        {
          fieldName: 'MA_TT',
          headerText: 'Mã TT',
          allowFiltering: true
        },
        {
          fieldName: 'TRANGTHAI_ID',
          headerText: 'Trạng thái ID',
          allowFiltering: true
        },
        {
          fieldName: 'TIENNO',
          headerText: 'Tiền nợ',
          allowFiltering: true,
          type: 'number',
          format: 'N0'
        },
        {
          fieldName: 'NO_DK',
          headerText: 'Nợ ĐK',
          allowFiltering: true,
          type: 'number',
          format: 'N0'
        },
        {
          fieldName: 'NO_PS',
          headerText: 'Nợ PS',
          allowFiltering: true,
          type: 'number',
          format: 'N0'
        },
        {
          fieldName: 'TRANGTHAI_TB',
          headerText: 'Trạng thái TB',
          allowFiltering: true
        },
        {
          fieldName: 'TRANG_THAI',
          headerText: 'Trạng thái',
          allowFiltering: true
        },
        {
          fieldName: 'DIACHI_TT',
          headerText: 'Địa chỉ TT',
          allowFiltering: true
        },
        {
          fieldName: 'DIACHI_LD',
          allowFiltering: true,
          headerText: 'Địa chỉ LĐ'
        },
        {
          fieldName: 'LYDO_TRA',
          headerText: 'Lý do trả',
          allowFiltering: true
        },
        {
          fieldName: 'DIEMTINNHIEM',
          headerText: 'Điểm TN',
          allowFiltering: true
        },
        {
          fieldName: 'DIENTHOAI',
          headerText: 'SĐT nhắc nợ',
          allowFiltering: true
        },
        {
          fieldName: 'MA_PLKH',
          headerText: 'Mã PLKH',
          allowFiltering: true
        },

        {
          fieldName: 'TEN_TT',
          headerText: 'Tên thanh toán',
          allowFiltering: true
        }
      ],
      donviQL: '',
      checkMoMay: false,
      dichVu: '1',
      checkLoaiHinh: false,
      loaiHinh: [],
      loaiChem: 1,
      hinhThuc: '5',
      checkKHLon: false,
      KhLon: [],
      isCalculatorShowTuTien: false,
      tuTien: 0,
      isCalculatorShowDenTien: false,
      checkDenTien: false,
      denTien: 0,
      quyTrinh: '',
      isCalculatorShowTuTN: false,
      tuTN: 0,
      isCalculatorShowDenTN: false,
      checkDenTN: false,
      denTN: 0,
      loaiPhieu: 2,
      chieuKhoa: 1,
      loaiKH: null,
      checkLoaiKH: false,
      checkQuan: false,
      quan: '',
      checkXN: true,
      trangThai: '2',
      kieuMotChieu: 1,
      loaiBo: [1, 2, 3, 4, 5, 6],
      tuNgay: new Date(),
      denNgay: new Date(),
      today: new Date(),
      checkTuNgay: false,
      checkDenNgay: false,
      bk_dsKhoaThueBao: [],
      dsKhoaThueBao: [],
      dsKhongKhoa: [],
      dsLoaiBo: [
        { id: 1, text: 'Khách hàng đặc biệt tháng' },
        { id: 2, text: 'Khách hàng treo nợ' },
        { id: 3, text: 'Khách hàng khất nợ' },
        { id: 4, text: 'Khách hàng nghiệp vụ, công vụ' },
        { id: 5, text: 'Khách hàng sao' },
        { id: 6, text: 'Khách hàng chuyển khoản' }
      ],
      disabledChieuKhoa1: false,
      disabledChieuKhoa2: false,
      tooltip: Tooltip,
      textBtnDongBo: 'Đồng bộ',
      processTime: '00:00:00',
      isFile: false,
      keyDSDaKhoa: 0,
      keyDSChuaKhoa: 0,
      quytrinhId: null,
      ghiChu: null
    }
  },
  methods: {
    ...mapActions('khoamaythuebaonocuoc', [
      'getDanhSachDonViQL',
      'getDanhSachQuyTrinhKhoaMay',
      'khoaMayTheoFile',
      'getDanhSachKhoaMay',
      'capNhatDanhSachVer3',
      'clearData'
    ]),
    ...mapActions('qltnCommon', [
      'getDanhSachDichVuVienThong',
      'getDanhSachLoaiHinhThueBao',
      'getDanhSachKHLon',
      'getDanhSachLoaiKhachHang',
      'getDanhMucQuanHuyen'
    ]),
    handleKyHoaDon(e) {
      this.pKyCuoc = moment(e.p_kyhoadon).format('YYYYMM') + e.p_chuky_no
    },
    XuatFile() {
      this.$bvModal.show('modal-xuat-dulieu')
    },
    move() {
      // let arr = [];
      // const dsSelected_KhoaThueBao =
      //   this.$refs.dsach_DaKhoa.getSelectedRecords() || [];
      const selected = this.dsKhoaThueBao.filter(x => x._CHECK === true)
      const unSelected = this.dsKhoaThueBao.filter(x => x._CHECK === false)
      // dsSelected_KhoaThueBao.map((val) => {
      //   let idx = this.dsKhoaThueBao.findIndex(
      //     (v) => v.THUEBAO_ID == val.THUEBAO_ID
      //   );
      //   if (idx >= 0) {
      //     arr.push(this.dsKhoaThueBao[idx]);
      //     this.dsKhoaThueBao = this.dsKhoaThueBao.filter(
      //       (v) => v.THUEBAO_ID != val.THUEBAO_ID
      //     );
      //   }
      // });

      this.dsKhoaThueBao = [...unSelected]
      // this.bk_dsKhoaThueBao = [...this.dsKhoaThueBao]
      this.dsKhongKhoa = [...selected, ...this.dsKhongKhoa]
      let un = this.dsKhongKhoa.map(x => x.THUEBAO_ID)
      let bk = this.bk_dsKhoaThueBao.filter(x => !un.includes(x.THUEBAO_ID))
      this.bk_dsKhoaThueBao = [...bk]
    },
    unmove() {
      const selected = this.dsKhongKhoa.filter(x => x._CHECK === true)
      const unSelected = this.dsKhongKhoa.filter(x => x._CHECK === false)
      this.dsKhoaThueBao = [...selected, ...this.dsKhoaThueBao]
      this.dsKhongKhoa = [...unSelected]
      this.bk_dsKhoaThueBao = [...this.bk_dsKhoaThueBao, ...selected]
      // let arr = [];
      // const dsSelected_KhongKhoa =
      //   this.$refs.dsach_ChuaKhoa.getSelectedRecords() || [];
      // dsSelected_KhongKhoa.map((val) => {
      //   let idx = this.dsKhongKhoa.findIndex(
      //     (v) => v.THUEBAO_ID == val.THUEBAO_ID
      //   );
      //   if (idx >= 0) {
      //     arr.push(this.dsKhongKhoa[idx]);
      //     this.dsKhongKhoa = this.dsKhongKhoa.filter(
      //       (v) => v.THUEBAO_ID != val.THUEBAO_ID
      //     );
      //   }
      // });
      // this.dsKhoaThueBao = [...arr, ...this.dsKhoaThueBao];
    },
    TimKiem() {
      this.isFile = false
      if (!this.kiemTra()) return
      let msgConfirm = `Bạn thật sự muốn tạo dữ liệu khóa máy với số tiền nợ >= ${Number(
        this.tuTien
      ).toLocaleString('en-En')} ?`
      if (this.checkDenTien)
        msgConfirm = `Bạn thật sự muốn tạo dữ liệu khóa máy với số tiền nợ >= ${Number(
          this.tuTien
        ).toLocaleString('en-En')} và <=  ${Number(this.denTien).toLocaleString(
          'en-En'
        )}?`
      this.$confirm(msgConfirm, 'Thông báo', {
        confirmButtonText: 'Đồng ý',
        cancelButtonText: 'Hủy'
      })
        .then(() => {
          this.fetchTimKiem()
        })
        .catch(() => { })
    },
    kiemTra() {
      //   if (this.dichVu == '2') {
      //     this.$root.toastError("Không thực hiện khóa thuê bao di động trên hệ thống của tỉnh!");
      //     return false;
      //   }
      if (this.tuTien.toString().trim() == '') {
        this.$root.toastError('Từ tiền không được để trống.')
        return false
      }
      if (this.checkDenTien) {
        if (this.denTien.toString().trim() == '') {
          this.$root.toastError('Đến tiền không được để trống.')
          return false
        }
      }
      if (this.checkDenTN) {
        if (this.denTN.toString().trim() == '') {
          this.$root.toastError('Điểm tín nhiệm đến không được để trống')
          return false
        }
      }
      if (this.checkKHLon && this.KhLon.length <= 0) {
        this.$root.toastError('Hãy nhập loại khách hàng lớn!')
        return false
      }
      if (this.checkQuan && this.quan.length <= 0) {
        this.$root.toastError('Hãy chọn quận!')
        return false
      }
      return true
    },
    async fetchTimKiem() {
      const data = {
        kyCuoc: this.pKyCuoc, // 20210101
        kieuKhoa: this.hinhThuc, // 1
        loaiPhieu: this.loaiPhieu, // 1
        loaiChem: this.loaiChem,
        chieuKhoa: this.chieuKhoa, // 1
        loaiKHId: this.checkLoaiKH ? this.loaiKH : null, // 1
        donViId: this.donviQL, // 1
        tuTien: this.tuTien, // 1
        denTien: this.checkDenTien ? this.denTien : '', // 1000000000
        dichVuVTId: this.dichVu, // 4
        loaiTBId: this.checkLoaiHinh ? this.loaiHinh : null, // null
        khdb: 1,
        quan: this.checkQuan ? this.quan : null, // [1]
        khLon: this.checkKHLon ? this.KhLon : null, // [7]
        tuNgay: this.checkTuNgay
          ? moment(this.tuNgay).format('DD/MM/YYYY')
          : '', // 01/01/2021
        denNgay: this.checkDenNgay
          ? moment(this.denNgay).format('DD/MM/YYYY')
          : '', // this.denNgay 30/06/2021
        moMay: this.checkMoMay ? 1 : 0, // 1
        kieuMC: this.chieuKhoa == 2 ? this.kieuMotChieu : null,
        ttphId: this.trangThai,
        khdbBienDong: this.loaiBo.includes(1) ? 1 : null,
        treoNo: this.loaiBo.includes(2) ? 1 : null,
        khatNo: this.loaiBo.includes(3) ? 1 : null,
        nghiepVu: this.loaiBo.includes(4) ? 1 : null,
        khSao: this.loaiBo.includes(5) ? 1 : null,
        chuyenKhoan: this.loaiBo.includes(6) ? 1 : null,
        diemTNTu: this.tuTN, // this.tuTN 11
        dienTNDen: this.checkDenTN ? this.denTN : '',
        dsKhuVuc: null,
        dsQuay: null,
        daXNTTVT: true
      }
      this.loading(true)
      let beginTime = new Date()
      const response = await this.getDanhSachKhoaMay(data)
      if (response.data.error_code === 'BSS-00000000') {
        this.loading(false)
        if (response.data.data !== undefined && response.data.data.length > 0) {
          this.dsKhoaThueBao = this.danhsachKhoaThueBao.map(item => ({ ...item, _CHECK: false }))
          this.bk_dsKhoaThueBao = [...this.dsKhoaThueBao]
          // this.dsKhoaThueBao = Results.map(obj => ({ ...obj, Active: 'false' }))
          this.dsKhongKhoa = []
        } else {
          this.$root.toastError('Không có bản ghi nào trong cơ sở dữ liệu!')
          this.dsKhoaThueBao = []
          this.dsKhongKhoa = []
          this.bk_dsKhoaThueBao = [...this.dsKhoaThueBao]
        }
      } else {
        this.loading(false)
        this.dsKhoaThueBao = []
        this.dsKhongKhoa = []
        this.bk_dsKhoaThueBao = [...this.dsKhoaThueBao]
        this.$root.toastError(response.message_detail)
      }
      let timeSpan = new Date() - beginTime
      let seconds = timeSpan / 1000
      let hours = parseInt(seconds / 3600)
      seconds = seconds % 3600
      let minutes = parseInt(seconds / 60)
      seconds = Math.round(seconds % 60)
      this.processTime = hours.toString().padStart(2, '0') + ':' + minutes.toString().padStart(2, '0') + ':' + seconds.toString().padStart(2, '0')
    },
    async TaoDS() {
      if (this.dsKhoaThueBao.length <= 0) {
        this.$root.toastError('Chưa có thuê bao trên lưới!')
        return
      }
      this.$confirm(
        `Bạn có chắc chắn muốn thực hiện tạo danh sách?`,
        'Xác nhận',
        {
          confirmButtonText: 'Đồng ý',
          cancelButtonText: 'Hủy'
        }
      )
        .then(async () => {
          const arrayData = this.dsKhoaThueBao.map((item) => ({
            thueBaoId: item.THUEBAO_ID,
            lyDoKhoaMo: item.LYDOKHOA || ''
          }))
          const data = {
            ds: arrayData,
            kieuYC: 1,
            chieuKhoa: this.chieuKhoa, // 1
            loaiTBId: this.loaiHinh, // null
            dichVuVTId: this.dichVu, // 4
            donViId: this.donviQL, // 1
            loaiChem: this.loaiChem,
            ghiChu: this.ghiChu !== null ? ('' + this.ghiChu).trim() : null
          }
          this.loading(true)
          const response = await this.capNhatDanhSachVer3(data)
          if (response.data.error_code === 'BSS-00000000') {
            this.quytrinhId = response.data.data
            this.loading(false)
            this.$root.toastSuccess('Tạo danh sách thành công!')
            this.dsKhoaThueBao = []
            this.dsKhongKhoa = []
            this.bk_dsKhoaThueBao = [...this.dsKhoaThueBao]
          } else {
            this.loading(false)
            this.$root.toastError(response.message_detail)
          }
        })
        .catch(() => { })
    },
    GiaoPhieu() {
      //   this.$router.push({ path: "/qltn/xltb-giao-phieu" });
      this.$router.push({
        name: 'TiepNhanThongTinKhoaMoNoCuoc',
        params: {
          tag: '1+7+1;4;8;9;10;11;12;14;15;16;7',
          dichvuID: this.dichVu,
          kieuYCID: 1,
          tthdID: 1,
          quytrinhId: this.quytrinhId !== null ? this.quytrinhId : this.dichVu == DichVuVienThong.CO_DINH ? 10113 : this.dichVu == DichVuVienThong.ADSL ? 10116 : this.dichVu == DichVuVienThong.IMS ? 10115 : null
        }
      })
    },
    DocFile() {
      this.$bvModal.show('modal-docfileKhoaMay')
    },
    async InDS() {
      if (this.dsKhoaThueBao.length <= 0) {
        this.$root.toastError('Chưa có dữ liệu để in!')
        return
      }

      this.loading(true)
      try {
        const baseUrl = process.env.API
        await this.axios({
          url: `${baseUrl}/web-thuno/api/thu-no/xu-ly-thue-bao/in-danh-sach-khoa-may`,
          method: 'POST',
          responseType: 'blob',
          data: {
            ds: this.dsKhoaThueBao,
            kyCuoc: this.pKyCuoc
          }
        }).then((response) => {
          previewPrint(response.data)
        })
      } catch (error) {
        this.$root.toastError('Có lỗi trong quá trình xem báo cáo !')
      } finally {
        this.loading(false)
      }
    },
    async getDonViQL() {
      await this.getDanhSachDonViQL()
      if (this.danhsachDonViQL.length > 0) {
        this.donviQL = this.danhsachDonViQL[0].DONVI_ID
      }
    },
    async getDichVu() {
      await this.getDanhSachDichVuVienThong()
      if (this.danhSachDichVuVienThong.length > 0) {
        this.dichVu = '4'
      }
    },
    async getKHLon() {
      await this.getDanhSachKHLon()
      if (this.danhSachKhLon.length > 0) {
        this.KhLon = this.danhSachKhLon.map((item) => item.KHLON_ID)
      }
    },
    async getQuyTrinh(idDichVu) {
      this.loading(true)
      const data = {
        pDichVuVTId: idDichVu,
        pKieuYC: 1
      }
      await this.getDanhSachQuyTrinhKhoaMay(data)
      if (this.danhsachQuyTrinh.length > 0) {
        this.quyTrinh = this.danhsachQuyTrinh[0].QUYTRINH_ID
      }
      this.loading(false)
    },
    async getLoaiKH() {
      await this.getDanhSachLoaiKhachHang()
    },
    async getQuan() {
      if (
        this.danhMucQuanHuyen === undefined ||
        this.danhMucQuanHuyen.length === 0
      ) {
        await this.getDanhMucQuanHuyen()
      }
      if (this.danhMucQuanHuyen.length > 0) {
        this.quan = this.danhMucQuanHuyen.map((item) => item.QUAN_ID)
      }
    },
    async sendData(data) {
      this.isFile = true
      const dsMaTB = data.dsThueBao.map((item) => item.MA_TB)
      const dataRq = {
        kyCuoc: this.pKyCuoc, // 20210101
        ds: dsMaTB,
        chieuKhoa: this.chieuKhoa, // 1
        donViId: this.donviQL, // 1
        dichVuVTId: this.dichVu, // 4
        loaiTBId: this.loaiHinh // null
      }
      this.loading(true)
      const response = await this.khoaMayTheoFile(dataRq)
      if (response.data.error_code === 'BSS-00000000') {
        // this.dsKhoaThueBao = this.danhsachKhoaThueBao;
        this.dsKhoaThueBao = this.danhsachKhoaThueBao.map(obj => ({ ...obj, _CHECK: obj.TRANGTHAI_ID != 0 ? true : false }))
        this.dsKhongKhoa = []
        this.bk_dsKhoaThueBao = [...this.dsKhoaThueBao]
        this.loading(false)
      } else {
        this.loading(false)
        this.dsKhoaThueBao = []
        this.dsKhongKhoa = []
        this.bk_dsKhoaThueBao = [...this.dsKhoaThueBao]
        this.$root.toastError(response.message_detail)
      }
    },
    onClose: function (e) {
      this.tooltip.close()
    },
    onBeforeRender: function (args) {
      const resultLoaiHinh = this.danhSachLoaiHinhThueBao
      for (let i = 0; i < resultLoaiHinh.length; i++) {
        if (resultLoaiHinh[i].TEN_LOAIHINH === args.target.textContent) {
          this.tooltip.content = resultLoaiHinh[i].TEN_LOAIHINH
          this.tooltip.dataBind()
          break
        }
      }
      const resultKHLon = this.danhSachKhLon
      for (let j = 0; j < resultKHLon.length; j++) {
        if (resultKHLon[j].TEN_LKHL === args.target.textContent) {
          this.tooltip.content = resultKHLon[j].TEN_LKHL
          this.tooltip.dataBind()
          break
        }
      }
      const resultQuan = this.danhMucQuanHuyen
      for (let k = 0; k < resultQuan.length; k++) {
        if (resultQuan[k].TEN_QUAN === args.target.textContent) {
          this.tooltip.content = resultQuan[k].TEN_QUAN
          this.tooltip.dataBind()
          break
        }
      }
      const resultLoaiBo = this.dsLoaiBo
      for (let m = 0; m < resultLoaiBo.length; m++) {
        if (resultLoaiBo[m].text === args.target.textContent) {
          this.tooltip.content = resultLoaiBo[m].text
          this.tooltip.dataBind()
          break
        }
      }
    },
    onDropdownCreate: function (args) {
      this.tooltip = new Tooltip({
        content: '',
        target: '.e-list-item',
        position: 'top center',
        beforeRender: this.onBeforeRender
      })
      this.tooltip.appendTo('body')
    },
    onDSKhoaTBCellInfo(args) {
      if (args.column.field === 'MA_TT') {
        if (args.data.TT_LAP_HD) args.cell.classList.add('hd-khac')
        else args.cell.classList.remove('hd-khac')
      }
      if (args.column.field === 'MA_TB') {
        let dataField = args.data.TRANGTHAI_ID;
        if (dataField == 0) args.cell.classList.add("background-0");
        if (dataField == 1) args.cell.classList.add("background-1");
        if (dataField == 2) args.cell.classList.add("background-2");
        if (dataField == 3) args.cell.classList.add("background-3");
        if (dataField == 4) args.cell.classList.add("background-4");
        if (dataField == 5) args.cell.classList.add("background-5");
      }
    },
    checkBoxKhoaChange(args) {
      if (args.target.classList.contains('e-checkselectall')) { // click check/uncheck all
        let dsFilter = this.$refs.dsach_DaKhoa.filteredDataSource || []
        const checked = dsFilter.map(item => item.THUEBAO_ID)
        // console.log(args, obj)
        for (let i = 0; i < this.dsKhoaThueBao.length; i++) {
          if (checked.includes(this.dsKhoaThueBao[i].THUEBAO_ID))
            this.dsKhoaThueBao[i]._CHECK = args.checked
        }
      }
      this.isFile = false
    },
    dsKhoaRowSelected(args) {
      // console.log(args)
      if (!args.isHeaderCheckboxClicked && args.isInteracted) { // không phải call by click checkbox header
        // this.updateDSKhoaSelected(args.data)
        let f = this.dsKhoaThueBao.find(x => x.THUEBAO_ID === args.data.THUEBAO_ID)
        if (f !== undefined) f._CHECK = true
      }
      this.isFile = false
    },
    dsKhoaRowDeselected(args) {
      // console.log(args)
      if (!args.isHeaderCheckboxClicked && args.isInteracted) { // không phải call by click checkbox header & paging
        // this.updateDSKhoaSelected(args.data)
        let f = this.dsKhoaThueBao.find(x => x.THUEBAO_ID === args.data.THUEBAO_ID)
        if (f !== undefined) f._CHECK = false
      }
      this.isFile = false
    },
    dsKhoaDatabound(args) {
      let obj = this.$refs.dsach_DaKhoa.$refs.grid.ej2Instances
      // console.log(obj)
      if (obj.currentViewData.length > 0) {
        let data = obj.currentViewData
        let selectedCurrentPage = []
        data.forEach((value, index) => {
          if (value._CHECK) {
            selectedCurrentPage.push(index)
          }
          if (this.isFile && value.TRANGTHAI_ID != 0) {
            selectedCurrentPage.push(index)
          }
        })
        obj.selectRows(selectedCurrentPage)
      }
    },
    checkBoxChuaKhoaChange(args) {
      if (args.target.classList.contains('e-checkselectall')) { // click check/uncheck all
        let dsFilter = this.$refs.dsach_ChuaKhoa.filteredDataSource || []
        const checked = dsFilter.map(item => item.THUEBAO_ID)
        for (let i = 0; i < this.dsKhongKhoa.length; i++) {
          if (checked.includes(this.dsKhongKhoa[i].THUEBAO_ID))
            this.dsKhongKhoa[i]._CHECK = args.checked
        }
      }
    },
    dsChuaKhoaRowSelected(args) {
      if (!args.isHeaderCheckboxClicked && args.isInteracted) { // không phải call by click checkbox header
        let f = this.dsKhongKhoa.find(x => x.THUEBAO_ID === args.data.THUEBAO_ID)
        if (f !== undefined) f._CHECK = true
      }
    },
    dsChuaKhoaRowDeselected(args) {
      if (!args.isHeaderCheckboxClicked && args.isInteracted) { // không phải call by click checkbox header & paging
        let f = this.dsKhongKhoa.find(x => x.THUEBAO_ID === args.data.THUEBAO_ID)
        if (f !== undefined) f._CHECK = false
      }
    },
    dsChuaKhoaDatabound(args) {
      let obj = this.$refs.dsach_ChuaKhoa.$refs.grid.ej2Instances
      if (obj.currentViewData.length > 0) {
        let data = obj.currentViewData
        let selectedCurrentPage = []
        data.forEach((value, index) => {
          if (value._CHECK) {
            selectedCurrentPage.push(index)
          }
        })
        obj.selectRows(selectedCurrentPage)
      }
    },
    openFilterDialog() {
      this.dsKhoaThueBao = [...this.bk_dsKhoaThueBao]
      this.$refs.refFilterDialog.openDialog()
    },
    onAcceptFilterHandler(args) {
      if (args !== null) {
        let ret = []
        if (args.tu_tien === null) {
          ret = this.bk_dsKhoaThueBao.filter(x => x[args.keyword] <= args.den_tien)
        } else if (args.den_tien === null) {
          ret = this.bk_dsKhoaThueBao.filter(x => x[args.keyword] >= args.tu_tien)
        } else {
          ret = this.bk_dsKhoaThueBao.filter(x => x[args.keyword] >= args.tu_tien && x[args.keyword] <= args.den_tien)
        }
        this.dsKhoaThueBao = [...ret]
        this.keyDSDaKhoa++
      } else {
        this.dsKhoaThueBao = [...this.bk_dsKhoaThueBao]
        this.keyDSDaKhoa++
      }
    },
    onFilterChange(args) {

    }
  },
  created() {
    this.clearData()
    this.getDonViQL()
    this.getDichVu()
    this.getKHLon()
    this.getLoaiKH()
    this.getQuan()
  },
  watch: {
    dichVu: {
      handler: async function (val) {
        if (val == 2 || val == 10) {
          this.textBtnDongBo = 'Hoàn thành'
        } else this.textBtnDongBo = 'Đồng bộ'
        if ([1, 2, 10, 11].includes(parseInt(val))) {
          this.disabledChieuKhoa1 = true
          this.disabledChieuKhoa2 = true
          this.chieuKhoa = 1
        } else {
          this.disabledChieuKhoa1 = false
          this.chieuKhoa = 2 // ***
          this.disabledChieuKhoa2 = false
        }
        const response = await this.getDanhSachLoaiHinhThueBao(val)
        await this.getQuyTrinh(val)
        // if (response.data.error_code === "BSS-00000000") {
        //   this.loaiHinh = response.data.data.map(item=>item.LOAITB_ID);
        // }
      }
    },
    chieuKhoa: {
      handler: function (val) {
        if (val == 2) {
          this.disabledChieuKhoa2 = true
        } else {
          this.disabledChieuKhoa2 = false
        }
      }
    }
  }
}
</script>
