<template src="./PheduyetKehoachTCKH.html"></template>

<script>
import DataGrid2 from '@/components/Controls/DataGrid2'
import breadcrumb from "@/components/breadcrumb";
import api from "./API/index.js";
export default {
  name:"PheduyetKehoachTCKH",
  components: { 
    breadcrumb,DataGrid2},
    data() {
    return {
      api: [],
      header: {
        title: "PHÊ DUYỆT KẾ HOẠCH TIẾP CẬN KHÁCH HÀNG",
        list: [
          // { name: "Dữ liệu", link: { name: "Ui.cards" } },
          // { 
          //   name: "Quản lý Chiến dịch B2A", 
          //   link: { name: "Ui.cards" } 
          // },
          {
            name: "PHÊ DUYỆT KẾ HOẠCH TIẾP CẬN KHÁCH HÀNG",
            link: { name: "Ui.cards" },
            active: true,
          },
        ],
      },
            button: {
        tsbtnNhapMoi: { Enabled: true, Visible: true },
        tsbtnGhiLai: { Enabled: true, Visible: true },
      },
      infoTT: {
        hinhthuc: {
          checked: 0,
          list:[]
        },
        loainhiemvu: {
          checked: 0,
          list:[]
        },
        dichvu: {
          checked: 0,
          list:[]
        },
        loaihinh: {
          checked: 0,
          list:[]
        },
      },
      form_temp: {
        denNgay: '',
        dichVuVTId: '',
        donViId: '',
        loaiHDId: '',
        tuNgay: '',
        userCN: '',
        ckDonvi: 0
      },
      options: {
      options_dsdv: [],
      options_dsdonvi: [],
      options_dslhd: []
    },
    TTKhachhang: {
        Ten_KH: '',
        Ma_KH: '',
        Ma_thue: '',
        So_giayto: '',
        Dia_chi: '',
        Nguoi_tiepcan: '',
        Chuc_vu: '',
        SDT: '',
        Email: '',
    },


    columns: [{
        fieldName: 'ma_kehoach',
        headerText: 'Mã kế hoạch'
      }, {
        fieldName: 'loai_nhiemvu',
        headerText: 'Loại nhiệm vụ'
      }, {
        fieldName: 'ngay_tiepcan',
        headerText: 'Ngày tiếp cận'
      }, {
        fieldName: 'hinhthuc',
        headerText: 'Hình Thức'
      },{
        fieldName: 'Ma_kh',
        headerText: 'Mã khác hàng'
      },{
        fieldName: 'tên_KH',
        headerText: 'Tên khách hàng'
      },{
        fieldName: 'dich_vu',
        headerText: 'Dịch vụ'
      },{
        fieldName: 'loai_hinh',
        headerText: 'Loại hình'
      },],
    };
  },
  methods: {
    fetchData() {
      // Sử dụng đường dẫn API từ api.js
      axios.get(api)
        .then(response => {
          this.api = response.data;
        })
        .catch(error => {
          console.error('Error fetching data from API:', error);
        });
    },
    async getDanhSachDichVu() {
      this.options.options_dsdv  = []
      await api.getDanhSachDichVu(this.axios, {
        p_phanvung_id: 26,
        p_nguoidung_id: 1
      }).then(response => {
        if (response.data.data && response.data.data.length > 0) {          
          this.options.options_dsdv = response.data.data.map(e => ({
            ...e,
            id: e.dichvuvt_id,
            text: e.ten_dvvt
          }))
          this.form_temp.dichVuVTId = response.data.data[0].dichvuvt_id
          console.log("testapi",this.form_temp.dichVuVTId)
          // this.form_temp.dichVuVTId = this.options.options_dsdv[0] ? this.options.options_dsdv[0].dichvuvt_id : null
        }        
      })
    },
    async btnPheduyet_Click(){
      this.loading = true
      console.log("testapi",this.form_temp.dichVuVTId)
    },

    async layDanhSachTinh(){ 
            try{ 
                this.loading(true) 
                this.kieudl=[] 
                let response=await AttributeAPI.layDanhSachTinh(this.axios) 
                this.loading(false) 
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) { 
                    return response.data.data 
                }else{ 
                    return [] 
                } 
            }catch(e){ 
                this.loading(false) 
                return [] 
            } 
        },

            async initDulieu(){
            this.kieudl=await this.lay_danhsach_kieu_dl()
            if(this.kieudl.length>0){
                this.kieudl_selected=this.kieudl[0].kieudl
            }
            await this.hienthi_ds_thuoctinh()
        }
  },
  async mounted() {
    await this.getDanhSachDichVu()
  },
}
</script>