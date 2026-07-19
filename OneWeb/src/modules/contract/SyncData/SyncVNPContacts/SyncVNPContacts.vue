<template src="./SyncVNPContacts.template.html"></template>
<style src="./SyncVNPContacts.style.scss"></style>

<script>
import breadcrumb from '@/components/breadcrumb'
import config from './config';
import API from './api';
// Import local components
import TbBienDongGrid from './components/TbBienDongGrid.vue';
import KHBienDongGrid from './components/KHBienDongGrid.vue';
import DongBoTTDanhbaVNP from './popups/DongBoTTDanhbaVNP.vue';
import DsHDDaKy from './popups/DsHDDaKy.vue';
// Import global components
import moment from "moment"

export default {
  components: {
    breadcrumb,
    TbBienDongGrid,
    KHBienDongGrid,
    DongBoTTDanhbaVNP,
    DsHDDaKy
  },
  name: 'SyncVNPContacts',
  data: () => ({
    target: '.main-wrapper',
    ...config.UI,
    ...config.popup,
    filter: {
      kieuBD: -1,
      loaiDL: 1,
      tuNgay: moment(new Date()).format('DD/MM/YYYY'),
      denNgay: moment(new Date()).format('DD/MM/YYYY'),
      loaiDs: 0,
      nhanvien: 0,
      donviql: 0,
      nhomCTV: -1
    },
    UiState: {
      DvQLCB: false,
      popup: {
        dongboTTVNP: false,
        dsHDDaKy: false,
        animationSettings:  { effect: 'Zoom', duration: 400}
      },
    },
    dsThuebao:[],
    options: {
      kieuBDOptions: [],
      loaiDLOptions: [{
        tenloaidl: "Dữ liệu thuê bao",
        loaidl_id: 0
      }],
      loaiDanhsach: [
        { text: 'Chưa đồng bộ dữ liệu', value: 0 },
        { text: 'Đã đồng bộ dữ liệu', value: 1 },
      ],
      nhomOptions: [],
      donviQLOptions:[],
      nhanvienOptions: []
    },
    optionTemp: {
      nhomOptions: [],
      donviQLOptions:[],
      nhanvienOptions: []
    }
  }),
  computed: {},
  mounted () {
    this.init();
  },
  watch: {
    'UiState.DvQLCB'(newValue, oldValue) {
      newValue ? this.options.donviQLOptions = this.optionTemp.donviQLOptions : this.options.donviQLOptions = []
      newValue ? this.options.nhomOptions = this.optionTemp.nhomOptions : this.options.nhomOptions = []
    },
  },
  methods: {
    async init() {
      this.loading(true)
      try {
        await this.loadKieuBD();
        await this.loadLoaiDulieu();
        await this.loadDsDonviQLCTV();
        await this.loadNhomCTV();
      } catch (error) {
        this.$toast.error(`${error.statusText} - ${error.data}`)
      } finally {
        this.loading(false)
      }
    },
    async loadKieuBD() {
      const response = await API.get_ds_kieubd(this.axios);
      if(response && response.status == 200 && response.data && response.data.data && response.data.data.length > 0) {
        this.options.kieuBDOptions = response.data.data;
        // Hiển thị phần tử đầu tiên lên dropdown
        const [kieudb] = this.options.kieuBDOptions;
        this.filter.kieuBD = kieudb['loaidl_id']

      } else {
        this.options.kieuBDOptions = [];
      }
    },
    async loadLoaiDulieu() {
      const response = await API.get_ds_loai_dulieu(this.axios);
      if(response && response.status == 200 && response.data && response.data.data) {
        this.options.loaiDLOptions = response.data.data;
        // Hiển thị phần tử đầu tiên lên dropdown
        const [loaiDL] = this.options.loaiDLOptions;
        this.filter.loaiDL = loaiDL['loaidl_id']
      } else {
        this.options.loaiDLOptions = [];
      }
    },
    async loadDsDonviQLCTV() {
      const response = await API.get_ds_donvi_QL(this.axios);
      if(response && response.status == 200 && response.data && response.data.data && response.data.data.length > 0) {
        this.optionTemp.donviQLOptions = response.data.data;
        // Hiển thị phần tử đầu tiên lên dropdown
        const [donvi] = this.optionTemp.donviQLOptions;
        this.filter.donviql = donvi['donvi_id']

        if(this.UiState.DvQLCB) {
          this.options.donviQLOptions = this.optionTemp.donviQLOptions;
        }
      } else {
        this.optionTemp.donviQLOptions = []
        this.options.donviQLOptions = []
      }
    },
    async loadNhomCTV() {
      const response = await API.post_ds_nhom_nv(this.axios, {nhomlnv_id: this.filter.donviql});
      if(response && response.status == 200 && response.data && response.data.data && response.data.data.length > 0) {
        this.optionTemp.nhomOptions = response.data.data;
        // Hiển thị phần tử đầu tiên lên dropdown
        const [nhomnv] = this.optionTemp.nhomOptions;
        this.filter.nhomCTV = nhomnv['loainv_id'];
        if(this.UiState.DvQLCB) {
          this.options.nhomOptions = this.optionTemp.nhomOptions;
        }
      } else {
        this.optionTemp.nhomOptions = [];
        this.options.nhomOptions = []
      }
    },
    async loadNhanien() {
      console.log('Load ds nhân viên')
      const response = await API.post_ds_nhanvien(this.axios, {
        donvi_id: this.filter.donviql,
        loainv_id: this.filter.nhomCTV
      })
      if(response && response.status == 200 && response.data && response.data.data && response.data.data.length > 0) {
        this.optionTemp.nhanvienOptions = response.data.data;
        // Hiển thị nhân viên đầu tiên lên dropdown
        const [nhanvien] = this.optionTemp.nhanvienOptions;
        this.filter.nhanvien = nhanvien['nhanvien_id'];
        if(this.UiState.DvQLCB) {
          this.options.nhanvienOptions = this.optionTemp.nhanvienOptions;
        }
      } else {
        this.options.nhanvienOptions = [];
        this.optionTemp.nhanvienOptions = [];
      }
    },
    async changeLoaiDL() {
      console.log('Change loại dữ liệu')
      // Reset lại bảng dữ liệu để người dùng đỡ nhầm lẫn.
      this.dsThuebao = []
    },
    async changeDonviQL() {
      console.log('Thay đổi đơn vị QL');
      this.loading(true);
      await this.loadNhomCTV();
      await this.loadNhanien();
      this.loading(false);

    },
    async changeNhomCTV() {
      console.log('Thay đổi nhóm CTV')
      this.loading(true);
      await this.loadNhanien();
      this.loading(false);
    },
    async getDLChuaDongBo() {
      this.loading(true);
      const params = {
        loaidl: this.filter.loaiDL,
        tungay: this.filter.tuNgay,
        denngay: this.filter.denNgay,
        kieu: 0, //Trong trường hợp này mặc định là kieu =0
      }
      try {
        const response = await API.post_ds_tb_biendong(this.axios, params);
        if(response && response.status == 200 
          && response.data 
          && response.data.data 
          && response.data.data.length > 0) {
            const {data : {data}} = response;
            // Chế biến lại response lại một chút
            this.dsThuebao = data.map(item => ({
              ...item,
              date_sync: moment(item.date_sync).format('DD/MM/YYYY')
            }))
        } else {
          this.dsThuebao = []
        }
      } catch(error) {
        this.$toast.error(`${error.statusText} - ${error.data}`);
        this.dsThuebao = []
      } finally {
        this.loading(false)
      }
    },
    async getDLDaDongBo() {
      this.loading(true);
      const params = {
        loaidl_id: this.filter.loaiDL,
        tungay: this.filter.tuNgay,
        denngay: this.filter.denNgay,
        kieubd: this.filter.kieuBD,
        user_neo:"huongntt_hpg"
      }
      try {
        const response = await API.post_ds_da_dong_bo(this.axios, params);
        if(response && response.status == 200 
          && response.data 
          && response.data.data 
          && response.data.data.length > 0) {
            const {data : {data}} = response;
            // Chế biến lại response lại một chút
            this.dsThuebao = data.map(item => ({
              ...item,
              date_sync: moment(item.date_sync).format('DD/MM/YYYY')
            }))
        } else {
          this.dsThuebao = []
        }
      } catch(error) {
        this.$toast.error(`${error.statusText} - ${error.data}`);
        this.dsThuebao = []
      } finally {
        this.loading(false)
      }
    },
    async getDL() {
      if(this.filter.loaiDs == 0) {
        // Danh sách chưa đồng bộ
        await this.getDLChuaDongBo();
      } else {
        // Danh sách đã đồng bộ
        await this.getDLDaDongBo();
      }
    }, 
    async syncContact() {
      this.loading(true);
      const dsContacts = this.$refs.BienDongGrid.getDataSelectedRow();
      for (const contact of dsContacts) {
        // đồng bộ từng danh bạ thuê bao.
        // 1. KiemTraDiaChi

        // Cập nhật lại địa chỉ
        // 2. dongbo_danhba_vnp_capnhat_dc

        // 2.2 Lay_diachi_vnp
        //dongbo_danhba_vnp_capnhat_dc

        // 3. dongbo_danhba_vnp_v2
      }
      try {

        const body = {
          dsTB: this.$refs.BienDongGrid.getDataSelectedRow(),
          dvql: this.filter.donviql,
          nhom: this.filter.nhomCTV,
          nhanvien: this.filter.nhanvien
        }
        console.log(body);
        // request to server
        // TBD
      } catch (error) {
        this.$toast.error(`${error.statusText} - ${error.data}`);
      } finally {
        this.loading(false)
      }
    },
    showDongBoTTDanhbaVNPPopup() {
      this.$refs.DongBoTTDanhbaVNPPopup.showDialog();
    },
    showDsHDDaKyPopup() {
      this.$refs.DsHDDaKyPopup.showDialog();
    },
  }
}
</script>
