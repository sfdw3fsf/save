<template src="./UpdateAnswers.html"></template>
<style scoped src="./UpdateAnswers.scss"></style>
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
        title: 'CẬP NHẬT CÂU TRẢ LỜI',
        list: [
          { 
            name: 'Quản trị', 
            link: { name: 'Ui.cards' } 
          },
          {
            name: 'Cập nhật câu trả lời',
            link: { name: 'Ui.buttons' }
          },
        ]
      },
      params: {
        p_loaicautl_option: [],
        p_loai: null,
        p_noidung: null,
        p_ma_tl: null,
      },
      ds_cautraloi_table: [],
      traloi_id: "",
       button:{
        nhapmoi: true,
        ghilai:true,
        xoa:true,
        huybo:true,
      },
    };
  },
  validations: {},
  computed: {},
  watch: {},
  created() {
      this.loadDSLoaiCauTraLoi();
  },
  async mounted() {},
  methods: {
      async loadDSLoaiCauTraLoi() {
      API.getDSLoaiCauTraLoi(this.axios)
        .then((response) => {
          if (
            response.data.error_code === "BSS-00000000" &&
            response.data.data !== undefined &&
            response.data.data.length > 0
          ) {
            let items = [];
            if (response.data.data !== undefined) {
              response.data.data.forEach(function (item) {
                items.push({ id: item.loai, text: item.tenloai });
              });
              this.params.p_loaicautl_option = items;
              this.params.p_loaiNV = items[0].id;
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
    
    async loadDSCauTraLoi( ) {
      this.ds_cautraloi_table = [];
      this.loading(true);
      try {
        let apiParams = {
        };
        const response = await API.getDSCauTraLoi(this.axios, apiParams);
        if (
          response.data.error_code === "BSS-00000000" &&
          response.data.data !== undefined
        ) {
          this.ds_cautraloi_table = response.data.data;
        }else{
          this.params.p_noidung = null;
          this.params.p_ma_tl =null;
        }
      } catch (error) {
        console.log(error);
      } finally {
         this.loading(false);
      }
      this.SetButton(-1);
    },
     async tsbtnNhapMoi_Click() {
       this.SetButton(1);
    }, 
    //Ghi Lại
    async tsbtnGhiLai_Click(){
      try {
        if(this.params.p_noidung.length<= 4000)
        {
              var pdata = {};
              let kieu =0;
              var js_data = {
                  LOAI : this.params.p_loai,
                  NOIDUNG : this.params.p_noidung,
                  MA_TL : this.params.p_ma_tl
              };
              
              if(this.button.nhapmoi == false){
                  //"thêm mới"
                  kieu=1;
              }else{
                    //Lấy TRALOI_ID Cập nhật
                  js_data.TRALOI_ID=this.traloi_id;

              }
              let rs = await this.axios.post("/web-quantri/capnhatcautraloi/sp_traloi_update", {
                            "is_insert": kieu,
                            "js_data": JSON.stringify(js_data)
                        })
                        if (rs.data.data == 1) {
                            await this.loadDSCauTraLoi();
                            this.$toast.success("Lưu thành công")
                            this.SetButton(3);
                        } else
                            this.$toast.error(rs.data.data)
              
        }
        else
        {
           this.$toast.error("Trường nội dung nhập dữ liệu vượt quá độ dài cho phép là 4000 ký tự!")
        }
      } catch (error) {
        console.log(error);
      } finally {
         this.loading(false);
      }
    },
     created: async function () {
      this.loading(true);
      this.loadData();
      this.loading(false);
    },
     grdCauTraLoiChanged (subject) {
         this.params.p_noidung = subject.noidung;
         this.params.p_ma_tl = subject.ma_tl;
         this.params.p_loai = subject.loai;
         this.traloi_id=subject.traloi_id;

    },
    async tsbtnHuy_Click() {
        this.loadDSCauTraLoi();
        this.SetButton(2);
    }, 
     SetButton(kieu){
      this.button.nhapmoi =false;
      this.button.xoa = false;
      this.button.ghilai = false;
      this.button.huybo = false;
      switch (kieu){
        case -1: //batdau
          this.button.nhapmoi =true;
          this.button.ghilai =true;
          this.button.xoa = true;
          this.$refs.p_noidung.focus();
        break;
        case 0: //Bat dau
          this.button.nhapmoi =true;
          this.$refs.p_noidung.focus();
          this.clear();
          break;
        case 1: //Them moi
          this.button.ghilai = true;
          this.button.huybo = true;
          this.$refs.p_noidung.focus();
          this.clear();
          break;
        case 2: //huy
          this.button.nhapmoi =true;
          this.button.xoa = true;
          this.$refs.p_noidung.focus();
          this.clear();
          break;
        case 3: //edit
          this.button.nhapmoi =true;
          this.button.xoa = true;
          this.button.ghilai = true;
          this.button.huybo = true;
          this.$refs.p_noidung.focus();
          break;
      }
    },
     clear(){
      this.$refs.p_noidung.focus();
      this.params.p_noidung = "";
      this.params.p_ma_tl = "";
    },
     onChangeLoai(agrs){
      this.params.p_loai=agrs.id;
    },
  },
};
</script>
