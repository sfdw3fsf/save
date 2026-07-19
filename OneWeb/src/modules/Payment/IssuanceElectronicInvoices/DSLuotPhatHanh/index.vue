<template src="./index.html">

</template>

<script>
import Const from './const';
import API from "../API";
import Vue from "vue";
import { GridPlugin, Page, Group, LazyLoadGroup } from "@syncfusion/ej2-vue-grids";
Vue.use(GridPlugin);

export default {
  name: "DsLuotPhatHanh",
  provide: {
    grid: [Page, Group, LazyLoadGroup]
  },
  data() {
    return {
      datasource: [],
      cols: Const.cols,
      groupSettings: {
        enableLazyLoading: true,
        columns: ['NAM', 'THANG', 'NGAY'],
        allowReordering: false
      }
    }
  },
  mounted() {
    this.HienThi_MucLuc();
  },
  methods: {
    hide() {
      this.$emit('hide');
    },

    async HienThi_MucLuc() {
      try {
        let rs = await API.loadCboKyPhatHanh(this.axios)
        let {js_returnds} = rs.data.data;
        js_returnds = JSON.parse(js_returnds);
        let {DS_HDDT_KHACHHANG} = js_returnds;
        this.datasource = DS_HDDT_KHACHHANG.map(item => {
          item.NAM = 'Năm: ' + item.NAM;
          item.THANG = 'Tháng: ' + item.THANG;
          item.NGAY = 'Ngày: ' + item.NGAY;

          return item;
        });

        return DS_HDDT_KHACHHANG;
      } catch (e) {
        this.datasource = [];
      }

      return null;
    },

    async selectedRowChanged(item) {
      this.$emit('selectedRowChanged', item);
    },
  }
}
</script>

<style scoped>
.btn-second {
  background-color: #fff;
  border-color: #0176FF;
  color: #0176FF;
  font-weight: 600;
}

.btn-second:hover {
  background: #0176FF;
  color: #fff;
}
</style>
