<template src="./MyaccountPasswordLookup.html"></template>
<style scoped src="./MyaccountPasswordLookup.scss"></style>
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
        title: 'Tra cứu mật khẩu MyAccount',
        list: [
          {
            name: 'Lập hợp đồng',
            link: { name: 'Ui.cards' }
          },
          {
            name: 'Tra cứu mật khẩu MyAccount',
            link: { name: 'Ui.buttons' }
          },
        ]
      },
       params: {
        p_mathuebao: null,
        p_kyhoadon_option: [],
        p_kyhoadon: null,
        p_chkAccount: 0,
        p_ketqua: null,

      },
       chk_Account: false,

    };
  },
  validations: {},
  computed: {},
  watch: {
     chk_Account: function (bool) {
      if (bool === false) {
        this.params.p_chkAccount = 0;
      } else {
         this.params.p_chkAccount = 1;
      }
    },

  },
  created() {
    this.loadDSKyHoaDon();
  },
  async mounted() {},
  methods: {
     async loadDSKyHoaDon() {
      API.ds_kyhoadon(this.axios)
        .then((response) => {
          if (
            response.data.error_code === "BSS-00000000" &&
            response.data.data !== undefined &&
            response.data.data.length > 0
          ) {
            let items = [];
            if (response.data.data !== undefined) {
              response.data.data.forEach(function (item) {
                items.push({ id: item.chukyno, text: item.dangchu });
              });
              this.params.p_kyhoadon_option = items;
              this.params.p_kyhoadon = items[0].id;
              this.loadDSCauTraLoi(
                items[0].id
              );
            }
          }
        })
        .catch((error) => {
          console.log(error);
        })
        .finally(() => {});
    },
     onChangeKyHD(agrs){
      this.params.p_kyhoadon=agrs.id;
    },
   async btnHuy() {
      this.params.p_mathuebao = null;
      this.params.p_chkAccount = 0;
      this.chk_Account = 0;
      this.params.p_ketqua=null;
    },
    async btnTraCuu() { // doi thanh button ThucHien
       try {
         if(this.params.p_mathuebao!=null)
         {
            //debugger;
              var ds_param = {
                CHK_TraCuuMyAccount : this.params.p_chkAccount,
                MA_TB : this.params.p_mathuebao,
                KY_HD : this.params.p_kyhoadon,
                MA_ND: 0,
                MAY_CN: 0,
                IP_CN: 0,
            };
              let res = await this.axios.post("web-hopdong/tracuu_mk_acc/fn_lay_matkhau_my_account", {
                          "vds_para": JSON.stringify(ds_param)
              })
              if(res.data.data != null) {
                this.params.p_ketqua = res.data.data;
              } else {
                this.params.p_ketqua = "Không có mật khẩu";
                this.$toast.error("Không tồn tại tài khoản My account của khách hàng!")
              }
         }
         else{
             this.$refs.p_mathuebao.focus();
             this.$toast.error("Vui lòng điền thông tin mã thuê bao")
         }
      } catch (error) {
        console.log(error);
      } finally {
         this.loading(false);
      }

    },
    async btnInPhieu() {


    },
    ShowKetQua() {
      this.btnTraCuu();
    }






  },
};
</script>
