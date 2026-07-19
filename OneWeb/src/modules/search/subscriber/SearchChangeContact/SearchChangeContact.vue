<template src="./SearchChangeContact.html"></template>
<style scoped src="./SearchChangeContact.scss"></style>
<script>
import breadcrumb from "@/components/breadcrumb";
import API from "./API";
import Vue from "vue";
import { DropDownListPlugin } from "@syncfusion/ej2-vue-dropdowns";
import KTable from "@/components/KTable.vue";
import ActionTop from "@/components/ActionTop.vue";
Vue.use(DropDownListPlugin);  
export default {
  components: { breadcrumb, KTable, ActionTop },
  data: function () {
    return {
      header: {
        title: 'Tra cứu thay đổi thông tin danh bạ',
        list: [
          { 
            name: 'Phát triển thuê bao', 
            link: { name: 'Ui.cards' } 
          },
          {
            name: 'Tra cứu thay đổi thông tin danh bạ',
            link: { name: 'Ui.buttons' }
          },
        ]
      },
      listbox: {
        dichvu_vienthong:{
          list:[],
          value: "",
          isEnabled:false,
        },
      },
       params: {
        p_dichvu_option: [],
        p_dichvu: 0,
        p_kieubd_option: [],
        p_kieubd: 0,
        p_tracuutheo_option: [],
        p_tracuutheo: 0,
        p_matt: '',
        p_makhachhang: '',
        p_somay: '',
        
      },
      ds_lichsu_kh: [],
      ds_lichsu_tt: [],
      ds_lichsu_tb: [],
      columns_DSLSKH: [
        {
          fieldName: "ma_kh",
          headerText: "Mã KH",
          allowFiltering: true,
          allowSorting: true,
          width: 'auto'
        },
        {
          fieldName: "ten_kh",
          headerText: "Tên khách hàng",
          allowFiltering: true,
          allowSorting: true,
          width: 'auto'
        },
        {
          fieldName: "diachi_kh",
          headerText: "Địa chỉ KH",
          allowFiltering: true,
          allowSorting: true,
          width: 'auto'
        },
        {
          fieldName: "nguoi_cn_kh",
          headerText: "Người CN",
          allowFiltering: true,
          allowSorting: true,
          width: 'auto'
        },
        {
          fieldName: "may_cn_kh",
          headerText: "Máy CN",
          allowFiltering: true,
          allowSorting: true,
          width: 'auto'
        },
        {
          fieldName: "ngay_cn_kh",
          headerText: "Ngày CN",
          allowFiltering: true,
          allowSorting: true,
          width: 'auto'
        },
      ],
      columns_DSLSThanhToan: [
        {
          fieldName: "ma_tt",
          headerText: "Mã TT",
          allowFiltering: true,
          allowSorting: true,
          width: 'auto'
        },
        {
          fieldName: "ten_tt",
          headerText: "Tên thanh toán",
          allowFiltering: true,
          allowSorting: true,
          width: 'auto'
        },
        {
          fieldName: "diachi_tt",
          headerText: "Địa chỉ TT",
          allowFiltering: true,
          allowSorting: true,
          width: 'auto'
        },
        {
          fieldName: "manv_tc",
          headerText: "Mã NVTC",
          allowFiltering: true,
          allowSorting: true,
          width: 'auto'
        },
        {
          fieldName: "mst_tt",
          headerText: "Mã số thuế",
          allowFiltering: true,
          allowSorting: true,
          width: 'auto'
        },
        {
          fieldName: "stk_tt",
          headerText: "Tài khoản",
          allowFiltering: true,
          allowSorting: true,
          width: 'auto'
        },
        {
          fieldName: "nguoi_cn_tt",
          headerText: "Người CN",
          allowFiltering: true,
          allowSorting: true,
          width: 'auto'
        },
        {
          fieldName: "may_cn_tt",
          headerText: "Máy CN	",
          allowFiltering: true,
          allowSorting: true,
          width: 'auto'
        },
        {
          fieldName: "ngay_cn_tt",
          headerText: "Ngày CN",
          allowFiltering: true,
          allowSorting: true,
          width: 'auto'
        },
      ],

      columns_DSLSTB: [
        {
          fieldName: "bdtb_id",
          headerText: "BDPT_ID",
          allowFiltering: true,
          allowSorting: true,
          width: 'auto'
        },
        {
          fieldName: "ma_tb",
          headerText: "Số máy/Acc",
          allowFiltering: true,
          allowSorting: true,
          width: 'auto'
        },
        {
          fieldName: "diachi_tb",
          headerText: "Địa chỉ TT",
          allowFiltering: true,
          allowSorting: true,
          width: 'auto'
        },
        {
          fieldName: "ten_tb",
          headerText: "Tên thuê bao",
          allowFiltering: true,
          allowSorting: true,
          width: 'auto'
        },
        {
          fieldName: "diachi_ld",
          headerText: "Địa chỉ LĐ",
          allowFiltering: true,
          allowSorting: true,
          width: 'auto'
        },
        {
          fieldName: "diachi_tb",
          headerText: "Địa chỉ TB",
          allowFiltering: true,
          allowSorting: true,
          width: 'auto'
        },
        {
          fieldName: "nguoi_cn_tb",
          headerText: "Người CN",
          allowFiltering: true,
          allowSorting: true,
          width: 'auto'
        },
        {
          fieldName: "may_cn_tb",
          headerText: "Máy CN",
          allowFiltering: true,
          allowSorting: true,
          width: 'auto'
        },
        {
          fieldName: "ngay_cn_tb",
          headerText: "Ngày CN",
          allowFiltering: true,
          allowSorting: true,
          width: 'auto'
        },
      ],


     
    };
  },
  validations: {},
  computed: {},
  watch: {},
  created() {
    try {
      this.loadDSDVVT();
      this.loadKieuBD();
      this.loadTraCuuTheo();
    } catch (e) {

    }
  },
  async mounted() {
    this.listbox.dichvu_vienthong.isEnabled = false
    this.params.p_dichvu = 0
  },
  methods: {
      async loadDSDVVT() {
      API.getDSDVVT(this.axios)
        .then((response) => {
          if (
            response.data.error_code === "BSS-00000000" &&
            response.data.data !== undefined &&
            response.data.data.length > 0
          ) {
            let items = [];
            if (response.data.data !== undefined) {
              response.data.data.forEach(function (item) {
                items.push({ id: item.DICHVUVT_ID, text: item.TEN_DVVT });
              });
              this.params.p_dichvu_option = items;
              this.params.p_dichvu = 0
              // this.loadDSCauTraLoi(
              //   items[0].id
              // );
            }
          }
        })
        .catch((error) => {
          console.log(error);
        })
        .finally(() => {});
    },
     async loadKieuBD() {
      let items = [
        { id: 0, text: "Tất cả" }
      ];
      this.params.p_kieubd_option = []
      this.params.p_kieubd = 0
      API.getKieuBD(this.axios)
        .then((response) => {
          if (
            response.data &&
            response.data.error_code === "BSS-00000000" &&
            response.data.data
          ) {
            if (response.data.data.length > 0) {
              for (var row of response.data.data) {
                if (row.KIEUBD_ID == 22 || row.KIEUBD_ID == 25) items.push({ id: row.KIEUBD_ID, text: row.KIEU_BD })
              }
              this.params.p_kieubd_option = items;
              this.params.p_kieubd = items[0].id;
            }
          }
        })
        .catch((error) => {
          console.log(error);
        })
        .finally(() => {});
    },
     async loadTraCuuTheo() {
      let items = [
        { id: 1, text: "Mã thuê bao" },
        { id: 2, text: "Mã khách hàng" },
        { id: 3, text: "Mã thanh toán" },
      ];
      this.params.p_tracuutheo_option = items;
      this.params.p_tracuutheo = items[0].id;
    },
    async loadDSLichSuTB() {
      
      this.ds_lichsu_tb = [];
      this.loading(true);
      try {
        //debugger;
        let apiParams = {
          vma_tb : this.params.p_somay ? this.params.p_somay : 0,//"tienphong3744625",
          vma_tt : this.params.p_matt ? this.params.p_matt : 0,//"HPG-04-067352",
          vma_kh: this.params.p_makhachhang ? this.params.p_makhachhang : 0,//"HPG-000840239",
          vdichvuvt_id: this.params.p_dichvu,//4- BRCD,
          vkieubd_id: this.params.p_kieubd//22
        };
        const response = await API.lay_ds_biendong_danhba(this.axios, apiParams);
        if (
          response.data.error_code === "BSS-00000000" &&
          response.data.data !== undefined
        ) {
          this.ds_lichsu_tb = response.data.data;
          this.ds_lichsu_kh = response.data.data;
          this.ds_lichsu_tt = response.data.data;
        }else{
        }
      } catch (error) {
        console.log(error);
      } finally {
         this.loading(false);
      }
     
    },
    tsbtnTraCuu_Click()
    {
      if(this.params.p_somay!=null || this.params.p_matt!=null|| this.params.p_makhachhang!=null){
          this.loadDSLichSuTB();
      }else
      {
         this.$toast.error("Chưa nhập thông tin tra cứu!")
      }
    },

    onChangeDV(agrs){
      if(this.listbox.dichvu_vienthong.isEnabled) {
        this.params.p_dichvu=agrs.id;
      } else {
        this.params.p_dichvu = 0
      }
         
    },
    onChangeBD(agrs){
      this.params.p_kieubd=agrs.id;
    },
     
     
    
  },
};
</script>
