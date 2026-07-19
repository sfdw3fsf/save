<template src="./TraCuuNopTienCuaKhachHang.html"></template>
<script>
import breadcrumb from '@/components/breadcrumb'
import TCNTCKHApi from '../api/TraCuuNopTienCuaKhachHangAPI'
import TraCuuApi from '../api/TraCuuAPI'
import CommonsAPI from '@/modules/QLTN/api/Commons'
import { mapActions, mapGetters } from "vuex"
import moment from 'moment'

export default {
  components: { breadcrumb },
  name: 'TraCuuNopTienCuaKhachHang',

  async created () {
    if (Object.keys(this.kyHoaDonHienTaiGetter).length <= 0)
      await this.getKyHoaDonHienTai();
    if (Object.keys(this.kyHoaDonHienTaiGetter).length > 0)
      this.kyHoaDonSelected = moment(this.kyHoaDonHienTaiGetter.KYCUOC, 'YYYYMMDD').toDate();
  },
  
  mounted () {
    this.updateChuKyHoaDon(moment(this.kyHoaDonSelected).format('YYYYMM'));
    this.$refs.maTT.focus();
  },

  computed: {
    ...mapGetters('qltnCommon', ['kyHoaDonHienTaiGetter'])
  },

  data () {
    return {
      kyHoaDonSelected: moment(new Date()).subtract(1,'months').endOf('month').toDate(),
      max_kyhoadon: moment(new Date()).subtract(1,'months').endOf('month').toDate(),
      chuKy: [],
      chuKySelected: null,      
      kyCuoc: 0,
      tenTT: '',
      maTT: '',
      soMayAC: '',
      diaChiTT: '',
      phieuTT_grid: {
        items: [],
        key: 1
      },
      phieuThuNgan_grid: {
        items: [],
        key: 1
      }
    }
  },

  methods: {
    ...mapActions('qltnCommon', ['getKyHoaDonHienTai']),

    async onSearch() {
      this.maTT = this.maTT.trim();
      this.soMayAC = this.soMayAC.trim();

      if (this.maTT === '' && this.soMayAC === '') {
        this.$toast.error('Bạn chưa nhập mã thanh toán hoặc số máy/Acc!');
        this.$refs.maTT.focus();
        return false;
      }

      if (this.maTT === '') {
        let dsTB = await this.getThongTinThanhToanTheoThueBao(this.soMayAC);

        if (dsTB.length === 0)
          dsTB = await this.getThongTinThanhToanTheoThanhToan(this.soMayAC);
        
        if (dsTB > 0)
          this.maTT = dsTB[0].MA_TT;
      }
      
      await this.onHienThiDanhSach(0);
    },

    async onMaTTKeyPress() {
      this.maTT = this.maTT.trim();

      if (this.maTT !== '')
        await this.onHienThiDanhSach(1);
      else {
        this.$toast.error('Bạn chưa nhập mã thanh toán!');
        this.$refs.maTT.focus();
      }
    },
    
    async onMaTBKeyPress() {
      this.soMayAC = this.soMayAC.trim();

      if (this.soMayAC !== '') {
        let dsTB = await this.getThongTinThanhToanTheoThueBao(this.soMayAC);
        if (dsTB.length === 0)
          dsTB = await this.getThongTinThanhToanTheoThanhToan(this.soMayAC);
        if (dsTB.length > 0) {
          this.maTT = dsTB[0].MA_TT;
          if (this.maTT !== '')
            await this.onHienThiDanhSach(2);
        } else {
          this.$toast.error(`Không tìm thấy thông tin về thuê bao có mã: ${this.soMayAC}!`);
          this.phieuTT_grid.items = [];
          this.phieuThuNgan_grid.items = [];
          this.tenTT = '';
          this.diaChiTT = '';
          this.maTT = '';
        }
      } else {
        this.$toast.error('Bạn chưa nhập số máy/Acc!');
        this.$refs.soMay.focus();
      }
    },

    async onHienThiDanhSach(type) {
      await this.getDSPhieuTra(this.maTT);
      await this.getDSPhieuTraTheoThuNgan(this.maTT);
      if (this.phieuTT_grid.items.length <= 0) {
          this.$toast.error('Không tìm thấy danh sách phiếu thanh toán!');
          this.phieuTT_grid.items = [];
          this.phieuThuNgan_grid.items = [];
          this.tenTT = '';
          this.diaChiTT = '';
          this.maTT = type === 1 ? this.maTT : '';
          this.soMayAC = type === 2 ? this.soMayAC : '';
      } else if (this.phieuThuNgan_grid.items.length <= 0) {
          this.$toast.error('Không tìm thấy thông tin tổng tiền theo thu ngân!');
          this.phieuTT_grid.items = [];
          this.phieuThuNgan_grid.items = [];
          this.tenTT = '';
          this.diaChiTT = '';
          this.maTT = type === 1 ? this.maTT : '';
          this.soMayAC = type === 2 ? this.soMayAC : '';
      }
    },

    onRowSelectDSPhieu(item) {
      this.maTT = item.data.MA_TT;
      this.tenTT = item.data.TEN_TT;
      this.diaChiTT = item.data.DIACHI_TT;
      this.soMayAC = item.data.MA_TB;
    },

    async getThongTinThanhToanTheoThueBao(maTB) {          
      try {
        this.loading(true);
        let apiParams = {
          pKyCuoc: this.kyCuoc,
          pMaTB: maTB
        };
        let response = await TraCuuApi.getThongTinThanhToanTheoThueBao(this.axios, apiParams);
        if (response.data.error_code === 'BSS-00000000' && response.data.data !== undefined) {          
          return response.data.data;              
        } else return [];
      } 
      catch (error) {
        console.log(error);
        return [];
      } 
      finally {
        this.loading(false);
      }      
    },

    async getThongTinThanhToanTheoThanhToan(maTT) {      
      try {
        this.loading(true);
        let apiParams = {
          pKyCuoc: this.kyCuoc,
          pMaTT: maTT
        };
        let response = await TraCuuApi.getThongTinThanhToanTheoThanhToan(this.axios, apiParams);
        if (response.data.error_code === 'BSS-00000000' && response.data.data !== undefined) {          
          return response.data.data;
        } else return [];
      }
      catch (error) {
        console.log(error);
        return [];
      }
      finally {
        this.loading(false);
      }        
    },

    async getDSPhieuTra(maTT) {      
      try {
        this.loading(true);
        this.phieuTT_grid.items = [];
        this.phieuTT_grid.key++;
        let apiParams = {
          pKyCuoc: this.kyCuoc,
          pMaTT: maTT
        };
        let response = await TCNTCKHApi.getDSPhieuTra(this.axios, apiParams);
        if (response.data.error_code === 'BSS-00000000' && response.data.data !== undefined) {          
          this.phieuTT_grid.items = response.data.data;           
        }
      }
      catch (error) {
        console.log(error);
      }
      finally {
        this.loading(false);
      }      
    },

    async getDSPhieuTraTheoThuNgan(maTT) {
      try {        
        this.loading(true);
        this.phieuThuNgan_grid.items = [];
        this.phieuThuNgan_grid.key++;
        let apiParams = {
          pKyCuoc: this.kyCuoc,
          pMaTT: maTT
        };
        let response = await TCNTCKHApi.getDSPhieuTraTheoThuNgan(this.axios, apiParams);
        if (response.data.error_code === 'BSS-00000000' && response.data.data !== undefined) {          
          this.phieuThuNgan_grid.items = response.data.data;          
        }
      }
      catch (error) {
        console.log(error);
      }
      finally {
        this.loading(false);
      }      
    },

    onKyHoaDonChange: function(args) {      
      if(args.value === null){
        this.$toast.error('Vui lòng chọn Kỳ hóa đơn')
        return
      }
      this.updateChuKyHoaDon(moment(args.value).format('YYYYMM'))      
    },    

    async updateChuKyHoaDon(pKyHoaDon) {
      try {
        this.loading(true);
        let response = await CommonsAPI.getChuKyNoTheoKyCuoc(this.axios, pKyHoaDon);
        if (response.data.error_code === 'BSS-00000000'
          && response.data.data !== undefined
          && response.data.data.length > 0) {
          this.chuKy = response.data.data;
          this.chuKySelected = this.chuKy[0].CHUKY;
        } else {
          this.chuKy = [];
          this.chuKySelected = null;
        }
        this.kyCuoc = moment(this.kyHoaDonSelected).format('YYYYMM') + this.chuKySelected;
      }
      catch (error) {
        console.log(error);
      }
      finally {
        this.loading(false)
      }      
    }
  }
}
</script>
