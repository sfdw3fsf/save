<template>
  <div class="">
    <div class="list-actions-top">
      <ul class="list">
        <li>
          <a @click="chapNhanFormDSBienDong">
            <span class="icon nc-icon-glyph ui-1_check-circle-08"></span> Chấp
            nhận
          </a>
        </li>
        <li>
          <a @click="thoatFormDSBienDong">
            <span class="icon nc-icon-glyph ui-1_check-circle-08"></span> Thoát
          </a>
        </li>
      </ul>
    </div>
    <div class="page-content">
       <DataGrid
        v-bind:columns="columns"
        v-bind:dataSource="data"
        :enable-paging-server="false"
        :allowPaging="true"
        :showFilter="true"
        :showColumnCheckbox="false"
        @selectedRowChanged="dsThueBaoChanged"
    >
    </DataGrid>
    </div>
  </div>
</template>

<script>
export default {
  props: {
    data: {
      type: Array,
      default: [],
    },
  },
  data() {
    return {
      columns: [
        {
          fieldName: "ma_gd",
          headerText: "Mã GD",
          allowFiltering: true,
          width: 100,
        },
        {
          fieldName: "ngay_yc",
          headerText: "Ngày yêu cầu",
          allowFiltering: true,
          width: 100,
        },
        {
          fieldName: "trangthai_hd",
          headerText: "Trạng thái",
          allowFiltering: true,
          width: 100,
        },
        {
          fieldName: "ten_loaihd",
          headerText: "Loại hợp đồng",
          allowFiltering: true,
          width: 100,
        },
        {
          fieldName: "",
          headerText: "Kiểu lắp đặt",
          allowFiltering: true,
          width: 100,
        },
        {
          fieldName: "ten_dv",
          headerText: "Đơn vị lập HĐ/PL",
          allowFiltering: true,
          width: 100,
        },
      ],
      selectedRow: {},
    };
  },
  methods: {
    dsThueBaoChanged(val) {
      this.selectedRow = val;
    },
    chapNhanFormDSBienDong() {
      this.$emit("chapNhanFormDSBienDong", this.selectedRow);
    },
    thoatFormDSBienDong() {
      this.$emit("thoatFormDSBienDong");
    },
  },
};
</script>

<style>
</style>