<template src="./SearchPromotionChange.html"></template>
<script src="@/static/vendor/jquery/split.js"></script>
<script>
import breadcrumb from "@/components/breadcrumb";
import API from "./API";
export default {
  components: { breadcrumb },
  data: function () {
    return {
      header: {
        title: "Tra cứu biến động khuyến mại",
      },
      cboDichVu: {
        value: 0,
        data: [],
      },
      txtMaTB:"",
      dgvDSThueBao:[]
    };
  },
  created() {
    this.loadDichVu();
  },
  mounted() {
    $(".select2js").select2();
  },
  methods: {
    loadDichVu() {
      API.sp_lay_dichvuvt_tcbd(this.axios,{ "vphanvung_id":this.$root.token.getPhanVungID(),"nguoidung_id":this.$root.token.getNguoiDungID() })
        .then((response) => {
          if (
            response.data.error_code === "BSS-00000000" &&
            response.data.data.rs1 !== undefined &&
            response.data.data.rs1.length > 0
          ) {
            let items = [];
             response.data.data.rs1.forEach(function (item) {
                items.push({ id: item.dichvuvt_id, text: item.ten_dvvt });
              });
              this.cboDichVu.data = items;
              this.cboDichVu.value = items[0].id;
          }
        })
        .catch((error) => {
          console.log(error);
        })
        .finally(() => {
        });
    },
    async loadDSThueBao(){
      this.loading(true);
      API.lay_biendong_khuyenmai(this.axios,{  "vphanvung_id":this.$root.token.getPhanVungID(),"vma_tb": this.txtMaTB.trim(),"vdichvuvt_id": this.cboDichVu.value})
        .then((response) => {
          if (
            //response.data.error_code === "BSS-00000000" &&
            response.data.data.rs1 !== undefined &&
            response.data.data.rs1.length > 0
          ) {
            this.dgvDSThueBao = response.data.data.rs1;
          }else{
            this.$toast.error("Không tim thấy thuê bao "+this.txtMaTB+" đối với dịch vụ "+this.cboDichVu.data.filter(x=>x.id==this.cboDichVu.value)[0].text)
          }
        })
        .catch((error) => {
          console.log(error);
        })
        .finally(() => {
          this.loading(false);
        });
    },
    btnTimKiem(){
      if(this.txtMaTB.trim() == ""){
        this.$toast.error("Vui lòng nhập mã thuê bao!");
        this.$refs.inputMaTB.focus();
        return false;
      }
      this.loadDSThueBao()
    },
    txtMaTB_KeyPress(){
      if(this.txtMaTB.trim() == ""){
        this.$toast.error("Vui lòng nhập mã thuê bao!");
        this.$refs.inputMaTB.focus();
        return false;
      }
      this.loadDSThueBao()
    }
  },
};
</script>