<template>
    <div>
    
  <b-modal
    id="DuyetBaiThi"
    ref="popup_DuyetBaiThi"
    modal-class="popup-box"
    header-class="popup-header"
    body-class="none-padding"
    size="xl"
    hide-footer
  >
    <template #modal-header="{ close }">
      <div class="title">
        <span class="icon nc-icon-outline ui-1_settings"></span> GHI CHÚ DUYỆT
      </div>
      <div
        @click="close"
        class="close -ap icon-close"
        data-dismiss="modal"
      ></div>
    </template>

    <div class="list-actions-top">
      <ul class="list">

        <li
          @click="clickButton('chapnhan')"
          :class="this.button.chapnhan ? 'active' : 'non-active'"
        >
          <a> <span class="icon one-file-plus"></span>Chấp nhận </a>
        </li>

        <!-- <li
          @click="clickButton('dong')"
          :class="this.button.dong ? 'active' : 'non-active'"
        >
          <a> <span class="close -ap icon-close"></span>Đóng </a>
        </li> -->


      </ul>
    </div>

    <div class="popup-body">
        <div class="info-row">
                <div class="value">
                <input
                    v-model="txtGhiChu"
                    class="form-control"
                />
                </div>
        </div>   
    </div>
            


  </b-modal>


    </div>
</template>
<script>

import api from "./api";
export default {
name:'popup_DuyetBaiThi',
  data() {
    return {
        txtGhiChu:"Mở khóa cho phép tiếp tục tác nghiêp trên mobile app.",
        button:{
        dong:true,
        chapnhan:true
    },
    };
  },
  created() {

  },
props: {
    vtonghop_id: {
        type: Number,
        default: ""
    }
},  

  watch: {

  },

  methods: {

    async duyet_baithi_tracnghiem() {

        console.log(        "vtonghop_id",this.vtonghop_id, 
        "vnoidung_duyet",this.txtGhiChu, 
        "vnhanvien_id", this.$root.token.getNhanVienID(), 
        "vnguoi_duyet", this.$root.token.getUserName())

      const res =  await api.duyet_baithi_tracnghiem(this.axios, {
        "vtonghop_id": this.vtonghop_id, 
        "vnoidung_duyet": this.txtGhiChu, 
        "vnhanvien_id": this.$root.token.getNhanVienID(), 
        "vnguoi_duyet": this.$root.token.getUserName()
        })
        var mes = res.data.error_code ? res.data.error_code : ''
        if (mes != "BSS-00000000") {
            this.$toast.error("Duyệt bài thi lỗi!");
        }
        else if(res.data.data == "ok") {
          this.$toast.success("Duyệt bài thi thành công !");
        }
        else {
          this.$toast.error("Lỗi API: " + res.data.message);
        }
    },


        showModal() {
            this.$bvModal.show("DuyetBaiThi")
        },
        hideModal() {
            this.$bvModal.hide("DuyetBaiThi")
        },


    clickButton(key) {
      if (key == "chapnhan") {
        if(this.txtGhiChu.trim() == '')
        {
            this.$toast.error("Chưa nhập ghi chú duyệt!");
            return
        }
        else
        {
            this.duyet_baithi_tracnghiem()
            this.hideModal()
        }
      }

    //   if (key == "dong") {
    //     this.hideModal()
    //   }


    },



  },
};
</script>
<style>
/* @import "https://cdn.syncfusion.com/ej2/material.css"; */
.none-padding {
  padding: 0 !important;
}
.modal-dialog {
  border-radius: 0px !important;
  width: 100% !important;
  max-width: 1220px !important;
}

li.non-active a {
  color: #d3d3d3 !important;
}

/* .e-row:active, e-row:focus{background: #8d8b8b !important} */


.e-grid td.e-active{ 
  background-color: #8d8b8b !important}

</style>
