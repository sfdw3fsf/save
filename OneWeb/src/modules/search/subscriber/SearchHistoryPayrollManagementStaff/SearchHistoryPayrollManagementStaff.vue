<template src="./SearchHistoryPayrollManagementStaff.html"></template>
<style scoped src="./SearchHistoryPayrollManagementStaff.scss"></style>
<script>
import breadcrumb from "@/components/breadcrumb";
import api from "./API";
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
        title: 'Phát triển thuê bao ',
        list: [
          { 
            name: 'Phát triển thuê bao ', 
            link: { name: 'Ui.cards' } 
          },
          
          {
            name: 'Tra cứu lịch sử điều chỉnh nhân viên quản lý tính lương KHDN',
            link: { name: 'Ui.buttons' }
          },
        ]
      },
       params: {
        MaTB: null,
      },
       ds_lichsuthaydoi_table: [],
    };
  },
  validations: {},
  computed: {
  },
  watch: {},
  created() {

  },
  async mounted() {},
  methods: {
     async timKiemThongTin(){
      try {
        if(this.params.MaTB!=null)
         {
              let res = await this.axios.post("web-tracuu/tra-cuu-lich-su-dc-nvql/sp_lay_ls_thaydoi_nv_khdn", {
                          "p_phanvung_id": this.$root.token.getPhanVungID(),
                          "p_ma_tb": this.params.MaTB
              })
               if (res.data.error_code === "BSS-00000000" && res.data.data !== undefined) {
                    this.ds_lichsuthaydoi_table = res.data.data.rs1;
                  }else{
                    this.params.p_noidung = null;
                    this.params.p_ma_tl =null;
                  }
         }
         else{
             this.$toast.error("Mã TB không được để trống!")
         }
      } catch (error) {
        console.log(error);
      } finally {
         this.loading(false);
      }
    },
     ShowKetQua() {
      this.timKiemThongTin();
    }

     


   
  },
};
</script>
