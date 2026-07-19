<template src="./MoMayTheoTungSoMayAcc.html"></template>
<style src="./MoMayTheoTungSoMayAcc.scss" scoped></style>
<script>
import { mapActions, mapState } from 'vuex';
import breadcrumb from '@/components/breadcrumb';
import API from '../api/MoMayTheoTungSoMayAcc';
import {
  DichVuVienThong
} from '@/const/enums'
export default {
  props: ['maThueBao'],
  components: { breadcrumb },
  name: 'MoMayTheoTungSoMayAcc',
  mounted () {
    this.$refs.soMay.focus();
  },
  computed: {
    ...mapState('qltnCommon', [
      'danhSachDichVuVienThong',
      'danhSachLoaiHinhThueBao',
    ]),
    // dsDichVu() {
    //   return this.danhSachDichVuVienThong.map((item) => ({
    //     id: item.DICHVUVT_ID,
    //     text: item.TEN_DVVT,
    //   }));
    // },
    // dsLoaiHinh() {
    //   return this.danhSachLoaiHinhThueBao.map((item) => ({
    //     id: item.LOAITB_ID,
    //     text: item.TEN_LOAIHINH,
    //   }));
    // },
  },
  data () {
    return {
      header: {
        title: 'MỞ MÁY THEO TỪNG SỐ MÁY/ACCOUNT',
        list: [
          { name: 'Xử lý thuê bao', link: { name: 'Ui.cards' } },
          {
            name: 'Mở máy theo số máy/acc',
            link: { name: 'Ui.buttons' },
          },
        ],
      },
      dichVu: '',
      loaiHinh: '',
      soMay: '',
      maTT: '',
      tenTB: '',
      tenTT: '',
      ngayKhoa: '',
      chieuKhoa: '',
      lydoKhoa: '',
      lydoMo: '',
      objThongtin: null,
      flagLoaiHinh: false,
    };
  },
  methods: {
    ...mapActions('qltnCommon', [
      'getDanhSachDichVuVienThong',
      'getDanhSachLoaiHinhThueBao',
    ]),
    ChuyenTB () {
      if (this.soMay.trim() === '') {
        this.$root.toastError('Mã thuê bao không được để trống!');
        this.$refs.soMay.focus();
        return;
      }
      if (this.maTT.trim() === '') {
        this.$root.toastError('Mã thanh toán không được để trống!');
        return;
      }
      if (this.lydoMo.trim() === '') {
        this.$root.toastError('Lý do mở không được để trống!');
        this.$refs.lydoMo.focus();
        return;
      }
      if (this.lydoMo.length > 250) {
        this.$root.toastError('Lý do mở vượt quá 250 ký tự!');
        this.$refs.lydoMo.focus();
        return;
      }
      this.chuyenThueBao();
    },
    async chuyenThueBao () {
      const data = {
        thueBaoId: null,
        maGD: null,
        loaiHDId: null,
        maTB: this.objThongtin.MA_TB,
        dichVuId: this.objThongtin.DICHVUVT_ID,
        loaiTBId: this.objThongtin.LOAITB_ID,
        chieuKhoaMo: this.objThongtin.CHIEUKHOAMO,
        nhanVienId:
          this.$auth.getNhanVienID() !== -1 ? this.$auth.getNhanVienID() : null,
        donViId:
          this.$auth.getDonViID() !== -1 ? this.$auth.getDonViID() : null,
        kieuYC: 0, //1: Khóa máy, 0: mở máy
        lyDo: this.lydoMo,
      };
      // console.log(data)
      try {
        this.loading(true);
        const response = await API.capNhatTB(this.axios, data);
        if (response.data.error_code === 'BSS-00000000') {
          this.$root.toastSuccess(
            `Chuyển thành công thuê bao có mã: ${this.soMay} sang hợp đồng thay đổi dịch vụ!`
          );
        //   this.soMay = "";
          this.maTT = '';
          this.tenTB = '';
          this.tenTT = '';
          this.ngayKhoa = '';
          this.chieuKhoa = '';
          this.lydoKhoa = '';
          this.flagLoaiHinh = false;
        } else {
          this.$root.toastError(`Chưa có thông tin về thuê bao cần mở máy!`);
        }
      } catch (error) {
        this.$root.toastError(error.data.message_detail);
      } finally {
        this.loading(false);
      }
    },
    async DongBo () {
      if (this.dichVu == '') {
        this.$root.toastError('Chưa có dịch vụ!');
        return;
      }
      let IDQuyTrinh = 0
      if(this.soMay.trim() != ''){
        try {
            const data = {
                pMa: this.soMay,
                pKieu: 1,
                pTTHDID: 1
            }
            this.loading(true);
            const response = await API.getQuyTrinh(this.axios, data);
            if (response.data.error_code === 'BSS-00000000') {
                IDQuyTrinh = response.data.data[0].QUYTRINH_ID
            }
        } catch (error) {
            // this.$root.toastError(error.data.message_detail);
        } finally {
            this.loading(false);
        }
      }
    //   else {
    //     IDQuyTrinh = this.dichVu === DichVuVienThong.CO_DINH ? 10113 : this.dichVu === DichVuVienThong.ADSL ? 10116 : this.dichVu === DichVuVienThong.IMS ? 10115 : 0
    //   }
      this.$router.push({
        name: 'TiepNhanThongTinKhoaMoNoCuoc',
        params: {
          tag: '1+7+1;2;4;8;9;10;11;12',
          dichvuID: this.dichVu,
          kieuYCID: 0,
          tthdID: 1,
          quytrinhId: IDQuyTrinh
        },
      });
    },
    async getDichVu () {
      await this.getDanhSachDichVuVienThong();
      if (this.danhSachDichVuVienThong.length > 0) {
        this.dichVu = this.danhSachDichVuVienThong[0].DICHVUVT_ID;
      }
    },
    fetchMaTB () {
      if (this.soMay.trim() === '') {
        this.$root.toastError('Mã thuê bao không được để trống!');
        this.$refs.soMay.focus();
        return;
      }
      this.getThongTin(this.soMay.trim());
    },
    async getThongTin (val) {
      try {
        const response = await API.getThongTinThueBao(this.axios, {
          maTB: val,
        });
        if (response.data.error_code === 'BSS-00000000') {
          if (
            response.data.data !== undefined &&
            response.data.data.length > 0
          ) {
            // ndthanh.dng added 30/10/2023
            // kiểm tra ds trả về có thuê bao nào đang không hoạt động bình thường hay không ?
            // nếu tất cả đều hoạt động bình thường thì báo lỗi
            // else sẽ chọn TB tìm thấy đầu tiên để thực hiện mở máy
            const f = response.data.data.find(x => x.TRANGTHAITB_ID !== 1)
            if (f === undefined) {
              this.$root.toastError(
                `Thuê bao đang hoạt động bình thường. Bạn không cần thực hiện mở máy.`
              );
              return;
            }
            // const obj = response.data.data[0];
            const obj = {...f}
            if (obj.TRANGTHAITB_ID == '1') {
              this.$root.toastError(
                `Thuê bao đang hoạt động bình thường. Bạn không cần thực hiện mở máy.`
              );
              return;
            }
            if (obj.TRANGTHAITB_ID == '6') {
              this.$root.toastError(
                `Thuê bao đang tạm dừng. Bạn không thể mở máy.`
              );
              return;
            }
            if (obj.TRANGTHAITB_ID == '7' || obj.TRANGTHAITB_ID == '9') {
              this.$root.toastError(
                `Thuê bao đang ở trạng thái thanh lý. Bạn không thể mở máy.`
              );
              return;
            }
            this.getThongTinByMaTB(obj);
          } else {
            // this.$root.toastError(`Không tìm thấy thuê bao ${val}`);
            this.$root.toastError('Mã thuê bao không tồn tại trong dữ liệu!');
            this.resetData();
          }
        } else {
          //   this.$root.toastError(`Không tìm thấy thuê bao ${val}`);
          this.$root.toastError('Mã thuê bao không tồn tại trong dữ liệu!');
          this.resetData();
        }
      } catch (error) {
        this.$root.toastError(error.data.message_detail);
        this.resetData();
      }
    },
    async getThongTinByMaTB (res) {
      const data = {
        maTB: res.MA_TB,
        maGD: '',
        dichVuVTId: res.DICHVUVT_ID, //4
        loaiHdId: 7, // thay đổi dv
      };
      this.loading(true);
      try {
        const response = await API.getDanhSachThueBao(this.axios, data);
        if (response.data.error_code === 'BSS-00000000') {
          if (
            response.data.data !== undefined &&
            response.data.data.length > 0
          ) {
            const obj = response.data.data[0];
            this.objThongtin = obj;
            this.soMay = obj.MA_TB;
            this.maTT = obj.MA_TT;
            this.tenTB = obj.TEN_TB;
            this.tenTT = obj.TEN_TT;
            this.ngayKhoa = obj.NGAYKHOA;
            this.chieuKhoa = obj.CHIEUKHOA;
            this.lydoKhoa = obj.LYDOKHOA;
            this.dichVu = obj.DICHVUVT_ID;
            this.loaiHinh = obj.LOAITB_ID;
            this.flagLoaiHinh = true;
          } else {
            this.resetData();
            this.$root.toastError(
              `Không tìm thấy thông tin khóa máy của thuê bao ${res.MA_TB}!`
            );
          }
        }
      } catch (error) {
        this.$root.toastError(error.data.message_detail);
        this.resetData();
      } finally {
        this.loading(false);
      }
    },
    resetData () {
    //   this.soMay = ""; // get quy trinh
      this.maTT = '';
      this.tenTB = '';
      this.tenTT = '';
      this.ngayKhoa = '';
      this.chieuKhoa = '';
      this.lydoKhoa = '';
      this.flagLoaiHinh = false;
      this.dichVu = this.danhSachDichVuVienThong[0].DICHVUVT_ID;
    },
  },
  created () {
    this.getDichVu();
    if(this.$route.params && this.$route.params.txtMaTb){
      this.soMay = this.$route.params.txtMaTb || '';
    }
    if(this.maThueBao){
      this.soMay = this.maThueBao || '';
    }
  },
  watch: {
    dichVu: {
      handler: async function (val) {
        const response = await this.getDanhSachLoaiHinhThueBao(val);
        if (
          response.data.error_code === 'BSS-00000000' &&
          response.data.data.length > 0
        ) {
          if (!this.flagLoaiHinh) {
            this.loaiHinh = response.data.data[0].LOAITB_ID;
          }
        }
      },
    },
  },
};
</script>
