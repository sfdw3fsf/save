<template>
  <b-modal
    ref="popupNhapTTXuLy"
    size="lg"
    hide-header
    hide-header-close
    hide-footer
    body-class="p-0"
    @show="handleShowModal"
  >
    <div class="modal-content popup-box">
      <div class="popup-header">
        <div class="title">
          <span class="icon one-notepad"></span> Cập nhật thông tin tiến trình
          xử lý
        </div>
        <div class="close -ap icon-close" @click="hideModal"></div>
      </div>
      <div class="list-actions-top">
        <ul class="list">
          <li>
            <a @click="ghiLai">
              <span class="icon one-save"></span> Ghi lại (F8)
            </a>
          </li>
        </ul>
      </div>
      <div class="popup-body">
        <div class="box-form">
          <div class="info-row">
            <div class="key w80">Nội dung xl</div>
            <div class="value">
              <div class="select-custom">
                <select2
                  v-model="list.p_ketquaxl"
                  :options="p_ketquaxl_options"
                />
              </div>
            </div>
          </div>
          <div class="info-row">
            <div class="key w80">Ghi chú</div>
            <div class="value">
              <textarea
                name=""
                class="form-control"
                style="height: 200px; resize: none"
                v-model="list.p_ghichu"
              ></textarea>
            </div>
          </div>
        </div>
      </div>
    </div>
  </b-modal>
</template>
<script>
import ActionTop from "@/components/ActionTop.vue";
import API from "./API";
import moment from "moment";
export default {
  name: "popupNhapTTXuLy",
  components: {
    ActionTop,
  },
  props: ['vghichu', 'vkqxl_id', 'vkieu','vphieu_id','vbaohong_id','isedit'],
  data() {
    return {
      list: {
        p_ketquaxl: null,
        p_ghichu: "",
      },
      p_ketquaxl_options: [],
    };
  },
  mounted() {
    
  },
  methods: {
    showModal() {
      this.$refs["popupNhapTTXuLy"].show();
    },
    hideModal() {
      this.$refs["popupNhapTTXuLy"].hide();
    },
    handleShowModal(){
      setTimeout(()=>{
        if(this.isedit == 1){
          this.list.p_ghichu = this.vghichu;
        }else{
           this.list.p_ghichu = "";
        }
        this.loadKetQuaXL();
      },500)
     
    },
    loadKetQuaXL(){
      API.taoGhiChu(this.axios,  {vkieu: this.vkieu} ).then((response) => {
        if (
          response &&
          response.data &&
          response.data.data &&
          response.data.error_code &&
          response.data.error_code === "BSS-00000000" &&
          response.data.data
        ) {
          let items = [];
          response.data.data.forEach(function (item) {
            items.push({ id: item.kqxl_id, text: item.ketqua_xl });
          });
          this.p_ketquaxl_options = items;
          if(this.isedit == 1){
             this.list.p_ketquaxl = this.vkqxl_id;
          }else{
             this.list.p_ketquaxl =  items[0].id
          }
        }
      });
    },
    INSERT_TIENTRINH_TC_BH(){
      let vbaohongid = 0;
      let vphieuid = 0;
      if(this.vkieu == 1){
        vphieuid = this.vphieu_id;
      }
      if(this.vkieu == 2){
        vbaohongid = this.vbaohong_id;
      }
      API.insert_tientrinh_tc_bh(this.axios, 
       {
        "vbaohong_id": vbaohongid,
        "vghichu": this.list.p_ghichu,
        "vhdtb_id": 0,
        "vkqxl_id": this.list.p_ketquaxl,
        "vloai_id": this.vkieu, //1 TC, 2 BH
        "vnguoidung_id": this.$root.token.getNguoiDungID(),//0,
        "vnhiemvu_id": 32,
        "vphieu_id": vphieuid
      }).then((response) => {
          if (response.data.error_code === "BSS-00000000" && response.data.data !== undefined 
          ) {
            this.$toast.success("Cập nhật kết quả xử lý thành công!");
            setTimeout(()=>{
               this.$emit("acceptChange", this.list);
              this.hideModal();
            },500)
          }else{
            this.$toast.error("Cập nhật kết quả xử lý thất bại! "+response.data.data)
          }
        }).catch((error) => {
          console.log(error);
          this.$toast.error(error.toString());
        })
        .finally(() => {this.loading(false);});
    },
    CAPNHAT_TIENTRINH_TC_BH(){
      let vphieuid = 0;
      if(this.vkieu == 1){
        vphieuid = this.vphieu_id;
      }
      let apiParams = {
        vphieu_id: vphieuid,
        vnhanvien_giao_id: this.$root.token.getNhanVienID(),
        vngaygiao: moment(new Date()).format("DD/MM/YYYY HH:mm:ss"),
        vnhanvien_th_id: 0,
        vghichu: this.list.p_ghichu,
        vnhiemvu_id: 0,
        vkqxl_id: this.list.p_ketquaxl,
        vkieu_cn: 1, //1=capnhat
        vloai: this.vkieu, //1 TC, 2 BH,
      };
      API.xoa_xuly_tientrinh(this.axios, apiParams)
        .then((response) => {
          if (
            response.data.error_code === "BSS-00000000" &&
            response.data.data !== undefined &&
            response.data.data.length > 0
          ) {
            this.$toast.success("Cập nhật thành công!");
            setTimeout(()=>{
               this.$emit("acceptChange", this.list);
              this.hideModal();
            },500)
          }else{
            this.$toast.error("Cập nhật thất bại!"+response.data.data)
          }
        }).catch((error) => {
          console.log(error);
        }).finally(() => {
          this.loading(false);
        });
    },
    ghiLai() {
      if(this.list.p_ghichu == ""){
        this.$toast.error("Bạn chưa chọn nội dung!");
        return false;
      }
      if(this.list.p_ghichu.length > 99){
        this.$toast.error("Ghi chú tối đa 99 kí tự!");
        return false;
      }
      if(this.isedit ==0){
        this.INSERT_TIENTRINH_TC_BH();
      }else{
        this.CAPNHAT_TIENTRINH_TC_BH();
      }
    },
  },
};
</script>