<!-- ChonNgay.vue -->
<template>
  <div>
    <ejs-datepicker
      :value="getDate()"
      :format="'dd/MM/yyyy'"
      :placeholder="'Chọn ngày'"
      :min="minDate"
      :strictMode="true"
      @change="onChange"
    />
  </div>
</template>

<script>
import Vue from "vue";
import { DatePickerPlugin } from "@syncfusion/ej2-vue-calendars";

Vue.use(DatePickerPlugin);

export default {
  name: "ChonNgay",
  // Bỏ prop data và sử dụng dữ liệu từ context
  data() {
    return {
      // Khởi tạo rowData, sẽ được cập nhật từ context
      rowData: this.$data?.data || {},
      minDate: new Date() // Ngày tối thiểu là ngày hiện tại
    };
  },
  computed: {
  },
  methods: {
    getDate: function () {
      const value = this.type == 'tang' ? this.rowData.NGAYTANG : this.rowData.NGAYGIAM;
      return value ? (value instanceof Date ? value : new Date(value)) : null;
    },
    formatDate(date) {
      const day = String(date.getDate()).padStart(2, '0');
      const month = String(date.getMonth() + 1).padStart(2, '0'); // +1 vì tháng bắt đầu từ 0
      const year = date.getFullYear();
      return `${day}/${month}/${year}`; // Định dạng DD/MM/YYYY
    },
    onChange(args) {
      const result = {
        id: this.rowData.ID,
        dateStr: this.formatDate(args.value)
      }
      this.$emit("date-changed", result);
    }
  },
  created() {
    // Cập nhật rowData từ context khi component được tạo
    if (this.$data && this.$data.data) {
      this.rowData = this.$data.data || {};
    }
  }
};
</script>

<style scoped>
.e-datepicker {
  width: 100%;
}
</style>
