<template>
  <div class="box-form">
    <div class="legend-title">
      <div class="title-main">Danh sách thuê bao ưu đãi</div>
      <div class="title-sub">Tổng số: {{ danhSachThueBaoUuDai.length }} thuê bao</div>
    </div>
    <vue-card>
      <vue-table-custom :config="config" :options="danhSachThueBaoUuDai" :unique="'index'" :loading="isLoading" v-model="currentRecord" />
    </vue-card>
  </div>
</template>

<script>

import { _, _2, mapGetters } from 'vuex';
// import helper from './../helper/UtilsJS';
import VueTableCustom from './../components/VueTable.vue';
import { formatCurrencyNoVND } from '../../../../../utils/format';

export default {
   components: {
    VueTableCustom
   },
  data() {
    return {
      currentRecord: null,

      config: [
        {
          label: 'Số máy/Acc',
          filterAble: true,
          key: 'ma_tb',
          width: '150px !important;'
        },
        {
          label: 'Tên thuê bao',
          filterAble: true,
          key: 'ten_tb',
          width: '250px !important;'
        },
        {
          label: 'Loại hình TB',
          filterAble: true,
          key: 'loaihinh_tb',
          width: '100px !important;'
        },
        {
          label: 'Tháng bắt đầu',
          filterAble: true,
          key: 'thang_bdshow'
        },
        {
          label: 'Tháng kết thúc',
          filterAble: true,
          key: 'thang_ktshow'
        },
        // {
        //   label: "Số công văn",
        //   filterAble: true,
        //   key: "congvan_id"
        // },
        {
          label: 'Tỷ lệ CTB',
          filterAble: true,
          key: 'tyle_ctb',
          align: 'right',
        },
        {
          label: 'Cước TB',
          filterAble: true,
          key: 'cuoc_tb',
          align: 'right',
          format:formatCurrencyNoVND
        },
        {
          label: 'Mức cước',
          filterAble: true,
          key: 'muccuoc',
          align: 'right',
          color: 'red',
          format:formatCurrencyNoVND
        },
        {
          label: 'Tỷ lệ CSD',
          filterAble: true,
          key: 'tyle_csd',
          align: 'right',
          format:formatCurrencyNoVND
        },
        {
          label: 'Cước SD',
          filterAble: true,
          key: 'cuoc_sd',
          align: 'right',
          format:formatCurrencyNoVND
        },
        {
          label: 'Tỷ lệ Cước IP',
          filterAble: true,
          key: 'tyle_cuocip',
          align: 'right',
          
        },
        {
          label: 'Cước IP',
          filterAble: true,
          key: 'cuoc_ip',
          align: 'right'
        },
        {
          label: 'Tỷ lệ số đẹp',
          filterAble: true,
          key: 'tyle_sodep',
          align: 'right',
        },
        {
          label: 'Tiền số đẹp',
          filterAble: true,
          key: 'tien_sodep',
          align: 'right',
          format:formatCurrencyNoVND
        },
        {
          label: 'Người CN',
          filterAble: true,
          key: 'nguoi_cn'
        }
      ]
    }
  },

  methods:{
    format
  },
  computed: {
    ...mapGetters('mapPreferentialSubscriber', ['danhSachThueBaoUuDai', 'isLoading']),
    root_nguoi_cn: function () {
      return this.$root.token.getUserName()
    }
  },
  watch: {
    currentRecord(val) {
      this.$emit('currentRecord', val)
    },
    danhSachThueBaoUuDai(val) {
      if (val.length > 0 ) {
        this.currentRecord = val[0];
        this.$emit("currentRecord", val[0]);
      } else {
        this.$emit("currentRecord", {});
      }
    }
  }
}
</script>
<style scoped>
.legend-title {
  display: flex;
  justify-content: space-between;
}

.title-sub {
  color: #d32f2f !important;
}
</style>
