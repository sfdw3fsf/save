<template src="./SearchITServiceByTechnicalParam.html"></template>
<style src="./SearchITServiceByTechnicalParam.scss"></style>
<script>
import breadcrumb from '@/components/breadcrumb'
import VueFlatPickr from 'vue-flatpickr-component';
import SearchITServiceByTechnicalParamAPI from './SearchITServiceByTechnicalParamAPI'
import gridview from '@/components/Shared/gridview'
//CSS
import '@/assets/vendor/bootstrap-table/bootstrap-table.min.css';
import '@/assets/vendor/bootstrap-table/extensions/fixed-columns/bootstrap-table-fixed-columns.css';
//JS
import '@/assets/vendor/jquery/split'
import '@/assets/vendor/bootstrap-table/bootstrap-table.min'
import '@/assets/vendor/bootstrap-table/extensions/fixed-columns/bootstrap-table-fixed-columns.min'
//
export default {
  components: { breadcrumb,gridview },
  name: 'SearchITServiceByTechnicalParam',
  mounted () {
    var pdata = {};
    pdata.nhom_id =1;
    SearchITServiceByTechnicalParamAPI.lay_ds_tinh(this.axios, pdata).then((response) => {
    if(response && response.data && response.data.data && response.data.error && response.data.error_code === 'BSS-00000000' && response.data.data) {
        this.listbox.ds_tinh.list = response.data.data;
        }
      }
    ); 
    //load dịch vụ
    SearchITServiceByTechnicalParamAPI.lay_ds_dvvt(this.axios, {}).then((response) => {
    if(response && response.data && response.data.data && response.data.error && response.data.error_code === 'BSS-00000000' && response.data.data) {
        this.listbox.ds_dichvu.list = response.data.data;
        this.listbox.ds_dichvu1.list = response.data.data;
        this.filterbox.dichvu_vienthong.list = response.data.data;
        }
      }
    );
    var pdata = {};
      pdata.dichvuvt_id = this.listbox.ds_dichvu.value;
      SearchITServiceByTechnicalParamAPI.lay_ds_lh_tb(this.axios, pdata).then((response) => {
        if(response && response.data) {
          this.filterbox.loaihinh_thuebao.list = response.data.data;
          this.filterbox.loaihinh_thuebao.value = response.data.data[0].ID;
        }
      });
    this.Lay_DS_TSKT(this.filterbox.loaihinh_thuebao.value);
  },
  data () {
    return {
      dateconfig: {
        altFormat: 'd/m/Y',
        altInput: true,
        dateFormat: 'd/m/Y',
        allowInput:true,
      },
      ngay_ht: new Date(),
      header: {
        title: 'Tra cứu dịch vụ CNTT theo TSKT',
        list: [
          { name: 'Lập hợp đồng', link: { name: 'Ui.cards' } },
          {
            name: 'Lắp đặt mới',
            link: { name: 'Ui.buttons' }
          }
        ]
      },
      MA_TB: "",
      maTN: "",
      maPL: "",
      checkCTV: "",
      txtTenTB: "",
      txtTenTB: "",
      txtDiaChi: "",
      txtDiaChiLapDat: "",
      txtTrangThai: "",
      txtTenTB: "",
      txtcuocphi: "",
      Ngay: "",
      txtDoiTac: "",
      txtThoiHan: "",
      listbox: {
        ds_tskt: {
          list: [],
          value: "",
          isEnabled: false,
        },
        ds_dktracuu1: {
          list: [],
          value: { kieutk_id:"", kieu_tk:"" },
          isEnabled: false,
        },
        ds_dichvu: {
          list: [],
          value: 13,
          isEnabled: false,
        },
        ds_dichvu1: {
          list: [],
          value: "",
          isEnabled: false,
        },
        ds_tinh: {
          list: [],
          value: 1,
          isEnabled: false,
        },
        ds_tb: {
          list: [],
          value: "",
          isEnabled: false,
        },
        loaihinh_thuebao: {
          list: [],
          value: "",
          isEnabled: false,
        },
        ketquatimkiem: {
          list: [],
          header:[],
          value: {},
          isEnabled: true,
          columns: [
          { fieldName: 'thuebao_id', headerText: 'Thuê bao ID', allowFiltering: true, cssClass: 'text-right'},
          { fieldName: 'ten_tb', headerText: 'Tên thuê bao', allowFiltering: true },
          { fieldName: 'ma_tb', headerText: 'Mã thuê bao', allowFiltering: true },                             
        ],
      },
      },
      filterbox: {
        dichvu_vienthong:{
          list:[],
          value: 89,
          isEnabled:false,
        },
        loaihinh_thuebao:{
          list:[],
          value: 89,
          isEnabled:false,
        },
        isType:false,
      },
      dateConfig: {
        altInput: true,
        dateFormat: 'd/m/Y',
        altFormat: 'd/m/Y'
      },
      from_date: new Date(),
      to_date: new Date(),
    }
  },
  methods: {
    filterThueBao() {
      var pdata = {};
      pdata.dichvuvt_id = this.listbox.ds_dichvu.value;
      SearchITServiceByTechnicalParamAPI.lay_ds_lh_tb(this.axios, pdata).then((response) => {
        if(response && response.data) {
          this.filterbox.loaihinh_thuebao.list = response.data.data;
          this.filterbox.loaihinh_thuebao.value = response.data.data[0].ID;
          console.log(this.filterbox.loaihinh_thuebao.value);
        }
      });
      this.Lay_DS_TSKT(this.filterbox.loaihinh_thuebao.value);
    },
    LHChange(){
      console.log(this.filterbox.loaihinh_thuebao.value);
      this.Lay_DS_TSKT(this.filterbox.loaihinh_thuebao.value);
    },
    Lay_DS_TSKT(lh_id){
      var pdata = {};
      pdata.vloaitb_id = lh_id;
      pdata.vten_bang = "HDTB_CNTT";
      pdata.vopt_list = "00000";
      SearchITServiceByTechnicalParamAPI.lay_ds_tskt(this.axios, pdata).then((response) => {
        if(response && response.data) {
          this.listbox.ds_tskt.list = response.data.data;
        //  console.log(this.listbox.ds_tskt.list);
        }
      });
    },
    ClearClicked() {
      this.txtTenTB = "";
      this.txtDiaChi = "";
      this.txtDiaChiLapDat = "";
      this.txtTrangThai = "";
      // this.MA_TB = "";
      this.maTN = "";
      this.maPL = "";
      this.checkCTV = "";
      this.txtcuocphi = "";
      this.txtDoiTac = "";
      this.txtThoiHan = "";
      this.Ngay = "";
      this.listbox.ketquatimkiem.list = null;
    },
    btnSearchClicked() {
      this.ClearClicked();
      console.log(this.MA_TB);
      var pdata = {};
      pdata.loaiTBId = this.filterbox.loaihinh_thuebao.value;
      pdata.maTB = this.MA_TB.trim();
      SearchITServiceByTechnicalParamAPI.tracuu(this.axios, pdata).then((response) => {
        if(response.data.data == "" || response.data.data == null){
             this.$toast.error("Không tìm thấy thông tin vừa nhập!");
        }
        if(response && response.data && response.data.data && response.data.error_code && response.data.error_code === 'BSS-00000000' && response.data.data) {
           this.listbox.ketquatimkiem.list = response.data.data;
        }
      });
    }
  }
};
</script>
