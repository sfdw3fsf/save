<template src="./index.html"></template>
<style src="./style.scss" scoped></style>
<style>
.overlay-common .loading {
  display: flex;
  justify-content: center;
  flex-direction: column;
}

.overlay-common.show {
  display: flex;
  justify-content: center;
}

.overlay-common {
  background-color: rgba(0, 0, 0, 0.7);
}

.overlay-common .loading-panel {
  display: flex;
  text-align: center;
  background-color: #fff;
  padding: 10px 15px;
  align-items: center;
  color: #035fcc;
  border: 1px solid #035fcc;
  border-radius: 7px;
}

.overlay-common .loading-panel .loading-content {
  flex-grow: 1;
  padding-left: 10px;
}
</style>
<script>
import moment from "moment";
import breadcrumb from "@/components/breadcrumb";

export default {
  components: { breadcrumb},
  data: function() {
    return {
      header: {
        title: "THANH TOÁN HỢP ĐỒNG",
        list: [
          { name: "Quản lý đại lý", link: { name: "Ui.cards" } },
          {
            name: "Thanh toán hợp đồng",
            link: { name: "Ui.buttons" }
          }
        ]
      },
    };
  },
  validations: {},
  watch: {},
  created: async function() {
  },
  async mounted() {},
  methods:{
  }
};
</script>
