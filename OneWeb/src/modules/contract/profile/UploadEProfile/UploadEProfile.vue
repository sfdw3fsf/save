<template>
  <div class="main-wraper">
    <breadcrumb :header="header" />
    <UploadEProfileContent :Tag="Tag" :ma_thue_bao="ma_thue_bao" :txtMaGD="txtMaGD" :isCA="isCA"
    :gioihan_loaifile="gioihan_loaifile" :vloaitb_id="vloaitb_id" :setHoanCong="setHoanCong"/>
  </div>
</template>
<script>
import UploadEProfileContent from "./UploadEProfile_content.vue";
import breadcrumb from "@/components/breadcrumb";
export default {
 props: {
    Tag: {
      type: String,
      default: "0",
    },
    ma_thue_bao: {
      type: String,
      default: "",
    },
    txtMaGD: {
      type: String,
      default: "",
    },
    gioihan_loaifile: {
      type: Number,
      default: 0,
    },
    vloaitb_id: {
      type: Number,
      default: 0,
    },
    setHoanCong: {
      type: Boolean,
      default: false,
    },
    isCA: {
      type: Boolean,
      default: false,
    },
    zfile_id: {
      type: Number,
      default: 0,
    },
    zhs_id: {
      type: Number,
      default: 0,
    },
    zthuebao_id: {
      type: Number,
      default: 0,
    },
    zhdtb_id: {
      type: Number,
      default: 0,
    },
  },
  components: {
    UploadEProfileContent,
    breadcrumb
  },
  data() {
    return {
      header: {
        title: "QUẢN LÝ FILE HỒ SƠ KHÁCH HÀNG",
        list: [
          { name: "Lập hợp đồng", link: { name: "Ui.cards" } },
          {
            name: "Lắp đặt mới",
            link: { name: "Ui.buttons" }
          }
        ]
      }
    };
  }
};
</script>
