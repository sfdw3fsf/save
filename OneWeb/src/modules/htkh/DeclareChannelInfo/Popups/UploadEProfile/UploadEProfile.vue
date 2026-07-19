<template>
  <div class="main-wraper">
    <breadcrumb :header="header" />
    <UploadEProfileContent :param="param" :Tag="Tag" :ma_thue_bao="ma_thue_bao" />
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
    // quytrinh: {
    //   type: String,
    //   default: "",
    // },
    param: {
      type: Object,
      default: null,
    },
    //   baoHong: {
    //     type: Boolean,
    //     default: false
    //   }
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
