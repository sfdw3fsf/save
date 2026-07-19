<template src="./CompleteProfileChangeITVASService.html"></template>
<style src="./CompleteProfileChangeITVASService.scss"></style>

<style src="../../../../assets/vendor/font-awesome/css/font-awesome.min.css"></style>
<style src="../../../../assets/vendor/ap8/css/style.css"></style>
<style src="../../../../assets/vendor/nucleo/outline/css/style.css"></style>
<style src="../../../../assets/vendor/nucleo/glyph/css/style.css"></style>
<style src="../../../../assets/vendor/bootstrap/css/bootstrap.min.css"></style>
<style src="../../../../assets/vendor/bootstrap-table/bootstrap-table.min.css"></style>
<style src="../../../../assets/vendor/bootstrap-table/extensions/fixed-columns/bootstrap-table-fixed-columns.css"></style>
<style src="../../../../assets/vendor/select2/css/select2.min.css"></style>
<style src="../../../../assets/vendor/oneicon/style.css"></style>
<style src="../../../../assets/css/style.css"></style>

<script src="../../../../assets/vendor/jquery/jquery-3.5.1.slim.min.js"></script>
<script src="../../../../assets/vendor/jquery/moment.min.js"></script>
<script src="../../../../assets/vendor/jquery/popper.min.js"></script>
<script src="../../../../assets/vendor/select2/js/select2.min.js"></script>
<script src="../../../../assets/vendor/bootstrap/js/bootstrap.min.js"></script>
<script src="../../../../assets/vendor/highchart/highcharts.js"></script>
<script src="../../../../assets/vendor/bootstrap-table/bootstrap-table.min.js"></script>
<script src="../../../../assets/vendor/bootstrap-table/extensions/fixed-columns/bootstrap-table-fixed-columns.min.js"></script>
<script src="../../../../assets/vendor/jquery/split.js"></script>
<script src="../../../../assets/js/custom.js"></script>

<script>
  import api from './CompleteProfileChangeITVASServiceAPI.js'
  import DanhSachThueBaoThayDoiDichVu from './part/DanhSachThueBaoThayDoiDichVu'
  import DichVuDangKy from './part/DichVuDangKy'
  import GiaoViec_Left from './part/GiaoViec_Left'
  import GiaoViec_Right from './part/GiaoViec_Right'
  import LyDoHuy from './part/LyDoHuy'
  import ThongTinPhieuYeuCau from './part/ThongTinPhieuYeuCau'
  export default {
    components: {
      ThongTinPhieuYeuCau,
      GiaoViec_Left,
      GiaoViec_Right,
      DichVuDangKy,
      LyDoHuy,
      DanhSachThueBaoThayDoiDichVu,
    },
    data () {
      return {
        isDisplayMenu: false,
        header: {
          title: 'HTHS thay đổi dịch vụ CNTT&GTGT',
          list: [//  Hoàn thiện HS > ATBTT -> HTHS thay đổi dịch vụ CNTT&GTGT
            { name: ' Hoàn thiện HS', link: { name: 'Ui.cards' }},
            { name: 'ATBTT', link: { name: 'Ui.cards' }},
            { name: 'HTHS thay đổi dịch vụ CNTT&GTGT', link: { name: 'Ui.buttons' }},
          ]
        },
        form_button: {
          layTT: true,
          capNhat: true,
          vatTu: true,
          xuatFile: true,
          danhSach: true,
          traPhieu: true,
          hoanThien: false,
          henLD: true,
          xuatPhieu: true,
          thongSoKT: true,
          tonPhieu: true,
          chuyenTo: true,
          traCuuNo: true,
        },
        form_ThongTinPhieuYeuCau: {
          dichVu: null,
          maGD: null,
          quyTrinh: null,
          soMayAcc: null,
          tenThueBao: null,
          ngayHT: null,
          diaChiTB: null,
          diaChiLD: null,
          hostLD: null,
          ghiChu: null
        },
        form_GiaoViec_Right: {
          noiDungGiao: null,
          noiDungTra: null,
          noiDungTH: null,
          nhomKQ: null,
          ketQua: null,
        },
        form_GiaoViec_Left: {
          lienHe: 'Liên hệ : Nguyễn văn a - 093412124122',
          nguoiGiaoViec: null,
          ngayGiaoViec: null,
          table: {
              currentRecord: null,
              data: null,
              loading: false,
              options: [],
              config: [
              {
                  label: 'Tên nhân viên',
                  filterAble: true,
                  key: 'ten_nv'
              },
              {
                  label: 'Điện thoại',
                  filterAble: true,
                  key: 'so_dt'
              },
              {
                  label: 'Nhiệm vụ',
                  filterAble: true,
                  key: 'so_dt'
              },
              {
                  label: 'Ghi chú',
                  filterAble: true,
                  key: 'so_dt'
              },
              ],                        
          }
        },
        form_DichVuDangKy: {
          table: {
              currentRecord: null,
              data: null,
              loading: false,
              options: [],
              config: [
              {
                  label: 'Tên dịch vụ',
                  filterAble: true,
                  key: 'ten_nv'
              },
              {
                  label: 'Mã DVGT',
                  filterAble: true,
                  key: 'so_dt'
              },
              {
                  label: 'Kiểu yêu cầu',
                  filterAble: true,
                  key: 'so_dt'
              },
              {
                  label: 'Nội dung',
                  filterAble: true,
                  key: 'so_dt'
              },
              ],                        
          }
        },
        form_LyDoHuy: {
          table: {
              currentRecord: null,
              data: null,
              loading: false,
              options: [],
              config: [
              {
                  label: 'Lý do hủy',
                  filterAble: true,
                  key: 'ten_nv'
              },
              {
                  label: 'Kiểu',
                  filterAble: true,
                  key: 'so_dt'
              },
              {
                  label: 'Người CN',
                  filterAble: true,
                  key: 'so_dt'
              },
              ],                        
          }
        },
        form_DanhSachThueBaoThayDoiDichVu: {
          radio_phieu: null,
          timeKiem: null,
          donVi: null,
          table: {
              currentRecord: null,
              data: null,
              loading: false,
              options: [],
              config: [
              {
                  label: 'Ngày YC',
                  filterAble: true,
                  key: 'ten_nv'
              },
              {
                  label: 'Ngày giao',
                  filterAble: true,
                  key: 'so_dt'
              },
              {
                  label: 'Mã GD',
                  filterAble: true,
                  key: 'so_dt'
              },
              {
                  label: 'Mã HĐ',
                  filterAble: true,
                  key: 'so_dt'
              },
              {
                  label: 'Số máy/Acc',
                  filterAble: true,
                  key: 'so_dt'
              },
              {
                  label: 'Số ảo',
                  filterAble: true,
                  key: 'so_dt'
              },
              {
                  label: 'Tên thuê bao',
                  filterAble: true,
                  key: 'so_dt'
              },
              {
                  label: 'Điện thoại LH',
                  filterAble: true,
                  key: 'so_dt'
              },
              {
                  label: 'Điểm tín nhiệm',
                  filterAble: true,
                  key: 'so_dt'
              },
              {
                  label: 'Ngày hết hạn',
                  filterAble: true,
                  key: 'so_dt'
              },
              {
                  label: 'Tên khu vực',
                  filterAble: true,
                  key: 'so_dt'
              },
              {
                  label: 'Chỉ tiêu TG',
                  filterAble: true,
                  key: 'so_dt'
              },
              {
                  label: 'Đơn vị nhận',
                  filterAble: true,
                  key: 'so_dt'
              },
              {
                  label: 'ĐV cài đặt',
                  filterAble: true,
                  key: 'so_dt'
              },
              {
                  label: 'Loại hình',
                  filterAble: true,
                  key: 'so_dt'
              },
              {
                  label: 'Trạng thái in',
                  filterAble: true,
                  key: 'so_dt'
              },
              {
                  label: 'Đơn vị giao',
                  filterAble: true,
                  key: 'so_dt'
              },
              {
                  label: 'Kích hoạt',
                  filterAble: true,
                  key: 'so_dt'
              },
              {
                  label: 'Ghi chú',
                  filterAble: true,
                  key: 'so_dt'
              },
              ],                        
          }
        },
      }
    },
    mounted () {},
    watch: {
    },
    methods: {
      btnLayTTOnClick(){
        alert('btnLayTTOnClick');
      },
      btnCapNhatOnClick(){
        alert('btnCapNhatOnClick');
      },
    btnGiaoPhieuOnClick() {
      alert("btnGiaoPhieuOnClick");
    },
    btnKhoiPhucSoOnClick() {
      alert("btnKhoiPhucSoOnClick");
    },
    btnKichHoatOnClick() {
      alert("btnKichHoatOnClick");
    },
    btnHuyKichHoatOnClick() {
      alert("btnHuyKichHoatOnClick");
    },
    btnDauNoiOnClick() {
      alert("btnDauNoiOnClick");
    },
    btnXuatPhieuOnClick() {
      alert("btnXuatPhieuOnClick");
    },
    btnChuyenToOnClick() {
      alert("btnChuyenToOnClick");
    },
    btnTraPhieuOnClick() {
      alert("btnTraPhieuOnClick");
    },
    setDisplayDropDownMenu() {
     this.isDisplayMenu = !this.isDisplayMenu
    },
    hideDropdownMenu(e) {
      if (e.target !== this.$refs.dotsTVASS) {
        this.isDisplayMenu = false;
      }
    },
  },
};
</script>
