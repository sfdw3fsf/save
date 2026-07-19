<template src="./SearchAreaByPlaceName.html"></template>
<style src="./SearchAreaByPlaceName.scss"></style>
<script>
import breadcrumb from '@/components/breadcrumb'
import SearchAreaByPlaceNameAPI from './api'
import config from './config';
import Constant from './constant';
// import module components
import Khuvuc from './components/KhuVuc.vue'
import Donvi from './components/DonVi.vue'

export default {
  components: { 
    breadcrumb, 
    Khuvuc,
    Donvi
  },
  name: 'SearchAreaByPlaceName',
  data: () => ({
    ...config.UI,
    tabIndex: 0,
    filter: {
      tinh: null,
      quan: null,
      phuong: null,
      duongpho: 0,
      ngoap: 0,
      khu: 0,
      dacdiem: 0,
      loaidonvi: null,
      state: {
        duongPho: false,
        ngoap: false,
        khu: false,
        dacdiem: false
      }
    },
    options: {
      tinh: [],
      quan: [],
      phuong: [],
      duongpho:[],
      ngoap: [],
      khu: [],
      dacdiem:[],
      loaidonvi: []
    },
    optionTemp: {
      phoapkhu:[],
      duongpho:[],
      ngoap: [],
      khu: [],
      dacdiem:[]
    },
    dsDonvi: [],
    dsKhuvuc: [],
    dsLoaiKhuvuc: [],
    kvDaGanSelected: null,
    dvSelected: null
  }),
  created () {},
  mounted () {
    /**
    * Lấy dữ liệu cho các dropdown của các option filter.
    * Để hiển thị được loader thì cần viết trong hàm mounted 
    * (lúc đó component Loader mới được init)
    */
    this.init();
  },
  destroyed () {},
  computed: {},
  watch: {
    'filter.state.duongPho'(newValue, oldValue) {
      newValue ? this.options.duongpho = this.optionTemp.duongpho : this.options.duongpho = []
    },
    'filter.state.ngoap'(newValue, oldValue) {
      newValue ? this.options.ngoap = this.optionTemp.ngoap : this.options.ngoap = []
    },
    'filter.state.khu'(newValue, oldValue) {
      newValue ? this.options.khu = this.optionTemp.khu : this.options.khu = []
    },
    'filter.state.dacdiem'(newValue, oldValue) {
      newValue ? this.options.dacdiem = this.optionTemp.dacdiem : this.options.dacdiem = []
    },
  },
  methods: {
    getTinhCuaTKDangNhap() {
      const OneBSSClient =  localStorage.getItem("OneBSS-Client") ? localStorage.getItem("OneBSS-Client") : null;
      if (OneBSSClient) {
        const id_tinh = JSON.parse(OneBSSClient)['id_tinh'];
        return id_tinh;
      }
    },
    async init() {
      // Hiển thị loading trong khi khởi tạo dữ liệu cho các form tìm kiếm
      this.loading(true)
      try {
        await this.loadTinh();
        await this.loadQuanHuyen(this.filter.tinh);
        await this.loadPhuongXa(this.filter.quan);
        await this.loadPho(this.filter.phuong);
        await this.loadNgoAp(this.filter.phuong);
        await this.loadKhu(this.filter.phuong);
        await this.loadDacDiem(this.filter.quan, this.filter.phuong);

      } catch (error) {
        this.$toast.error(`${error.statusText} - ${error.data}`)
      } finally {
        // Ẩn loading sau khi các dữ liệu init đã khởi tạo xong.
        this.loading(false)
      }
    },
    async loadTinh() {
      const response = await SearchAreaByPlaceNameAPI.get_ds_Tinh(this.axios);
      if(response && response.status == 200 && response.data && response.data.data && response.data.data.length > 0) {
        this.options.tinh = response.data.data;
        this.filter.tinh = this.getTinhCuaTKDangNhap();
      } else {
        this.options.tinh = []
      }
    },
    async loadQuanHuyen(tinh_id) {
      const response = await SearchAreaByPlaceNameAPI.get_ds_QuanHuyen(this.axios, {p_tinh_id: tinh_id});
      if(response && response.status == 200 && response.data && response.data.data && response.data.data.length > 0) {
        this.options.quan = response.data.data
        const [quan] = this.options.quan;
        this.filter.quan = quan['quan_id']
      } else {
        this.options.quan = [];
      }
    },
    async loadPhuongXa(quan_id) {
      const response= await SearchAreaByPlaceNameAPI.get_ds_PhuongXa(this.axios, {p_quan_id: quan_id});
      if(response && response.status == 200 && response.data && response.data.data && response.data.data.length > 0) {
        this.options.phuong = response.data.data
        const [phuong] = this.options.phuong;
        this.filter.phuong = phuong['phuong_id']
      } else {
        this.options.phuong = []
      }
    },
    async loadPho(phuong_id) {
      const response= await SearchAreaByPlaceNameAPI.get_ds_PhoApKhu(this.axios, {p_phuong_id: phuong_id, p_nhom_id: 1});
      if(response && response.status == 200 && response.data && response.data.data && response.data.data.length > 0) {
        this.optionTemp.duongpho = response.data.data

        const [pho] = this.optionTemp.duongpho;
        this.filter.duongpho = pho['pho_id']

        if(this.filter.state.duongPho) {
          this.options.duongpho = this.optionTemp.duongpho;
        }
      } else {
        this.optionTemp.duongpho = []
        this.options.duongpho = []
        this.filter.duongpho = 0
      }
    },
    async loadNgoAp(phuong_id) {
      const response= await SearchAreaByPlaceNameAPI.get_ds_PhoApKhu(this.axios, {p_phuong_id: phuong_id, p_nhom_id: 2});
      if(response && response.status == 200 && response.data && response.data.data && response.data.data.length > 0) {
        this.optionTemp.ngoap = response.data.data
        const [ngoap] = this.optionTemp.ngoap;
        this.filter.ngoap = ngoap['pho_id']
        if(this.filter.state.ngoap) {
          this.options.ngoap = this.optionTemp.ngoap
        }
      } else {
        this.optionTemp.ngoap = [];
        this.options.ngoap = [];
        this.filter.ngoap = 0;
      }
    },
    async loadKhu(phuong_id) {
      const response= await SearchAreaByPlaceNameAPI.get_ds_PhoApKhu(this.axios, {p_phuong_id: phuong_id, p_nhom_id: 3});
      if(response && response.status == 200 && response.data && response.data.data && response.data.data.length > 0) {
        this.optionTemp.khu = response.data.data
        const [khu] = this.optionTemp.khu;
        this.filter.khu = khu['pho_id']
        if(this.filter.state.khu) {
          this.options.khu = this.optionTemp.khu
        }
      } else {
        this.optionTemp.khu = [];
        this.options.khu = [];
        this.filter.khu = 0;
      }
    },
    async loadDacDiem(quan_id, phuong_id) {
      const response = await SearchAreaByPlaceNameAPI.get_ds_Dacdiem(this.axios, {vphuong_id: phuong_id, vquan_id: quan_id});
      if(response && response.status == 200 && response.data && response.data.data && response.data.data.length > 0) {
        this.optionTemp.dacdiem = response.data.data
        const [dacdiem] = this.optionTemp.dacdiem;
        this.filter.dacdiem = dacdiem['dacdiem_id']
        if(this.filter.state.dacdiem) {
          this.options.dacdiem = this.optionTemp.dacdiem
        }
      } else {
        this.optionTemp.dacdiem = [];
        this.options.dacdiem = [];
        this.filter.dacdiem = 0;
      }
    },
    updateLoaiDv(loaidv_id) {
      this.filter.loaidonvi = loaidv_id;
    },
    async changeTinh() {
      this.loading(true);
      await this.loadQuanHuyen(this.filter.tinh);
      await this.loadPhuongXa(this.filter.quan);
      await this.loadPho(this.filter.phuong);
      await this.loadNgoAp(this.filter.phuong);
      await this.loadKhu(this.filter.phuong);
      await this.loadDacDiem(this.filter.quan, this.filter.phuong);
      this.loading(false);
    },
    async changeQuanHuyen() {
      this.loading(true);
      await this.loadPhuongXa(this.filter.quan);
      await this.loadPho(this.filter.phuong);
      await this.loadNgoAp(this.filter.phuong);
      await this.loadKhu(this.filter.phuong);
      await this.loadDacDiem(this.filter.quan, this.filter.phuong);
      this.loading(false);
    },
    async changePhuongXa() {
      this.loading(true);
      await this.loadPho(this.filter.phuong);
      await this.loadNgoAp(this.filter.phuong);
      await this.loadKhu(this.filter.phuong);
      await this.loadDacDiem(this.filter.quan, this.filter.phuong);
      this.loading(false)
    },
    async loadKhuvuc() {
      this.loading(true);
      let params = {
        vphuong_id: this.filter.phuong
      }

      if(this.filter.state.duongPho) {
        params = {
          ...params,
          vpho_id: this.filter.duongpho
        }
      }
      if(this.filter.state.ngoap) {
        params = {
          ...params,
          vap_id: this.filter.ngoap
        }
      }
      if(this.filter.state.khu) {
        params = {
          ...params,
          vkhu_id: this.filter.khu
        }
      }
      if(this.filter.state.dacdiem) {
        params = {
          ...params,
          vdacdiem_id: this.filter.dacdiem
        }
      }

      console.log(params);

      try {
        const response = await SearchAreaByPlaceNameAPI.get_tracuu_KV(this.axios, params);
        if(response && response.status == 200 
          && response.data 
          && response.data.data 
          && response.data.data.length > 0) {
            this.dsKhuvuc = response.data.data
        } else {
          this.dsKhuvuc = []
        }
      } catch (error) {
        this.$toast.error(`${error.statusText} - ${error.data}`);
        this.dsKhuvuc = []
      } finally {
        this.loading(false);
      }
    },
    async loadDonvi() {
      this.loading(true)
      let params = {
        vloaidv_id: this.filter.loaidonvi,
        vphuong_id: this.filter.phuong
      }
      if(this.filter.state.duongPho) {
        params = {
          ...params,
          vpho_id: this.filter.duongpho
        }
      }
      if(this.filter.state.ngoap) {
        params = {
          ...params,
          vap_id: this.filter.ngoap
        }
      }
      if(this.filter.state.khu) {
        params = {
          ...params,
          vkhu_id: this.filter.khu
        }
      }
      if(this.filter.state.dacdiem) {
        params = {
          ...params,
          vdacdiem_id: this.filter.dacdiem
        }
      }
      try {
        const response = await SearchAreaByPlaceNameAPI.get_tracuu_DV(this.axios, params);
        if(response && response.status == 200 
          && response.data 
          && response.data.data 
          && response.data.data.length > 0) {
            this.dsDonvi = response.data.data
        } else {
          this.dsDonvi = []
        }
      } catch (error) {
        this.$toast.error(`${error.statusText} - ${error.data}`);
        this.dsDonvi = []
      } finally {
         this.loading(false)
      }
    },
    search() {
      if(this.tabIndex == Constant.TAB_KHUVUC) {
        this.loadKhuvuc();
      } else if( this.tabIndex == Constant.TAB_DONVI) {
        this.loadDonvi();
      }
    },
    updateKvDaGan(khuvuc) {
      this.kvDaGanSelected = khuvuc;
    },
    updateDV(donvi) {
      this.dvSelected = donvi;
    },
    /**
     * Lấy dữ liệu được chọn trên các table (sau khi tra cứu và hiển thị lên table)
     * để trả về cho màn hình khác cần sử dụng.
     */
    accept() {
      let output = {};
      if(this.tabIndex == Constant.TAB_KHUVUC) {
        output = {
          khuvucDaGan: this.kvDaGanSelected,
        }
      } else if( this.tabIndex == Constant.TAB_DONVI) {
        output = {
          dvDaChon: this.dvSelected
        }
      }
      // Kết thúc màn hình và quay lại trở màn hình trước đó.
      // @Chờ tài liệu mô tả cụ thể.
      console.log(output);
    }
  }
}
</script>