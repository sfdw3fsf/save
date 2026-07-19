<template src="./index.html">

</template>

<script>
export default {
  name: "DSNVThucHien",
  props: {
    dsNhanVien: {
      type: Array,
      required: true
    }
  },
  data() {
    return {
      cols: [
        {
          fieldName: "ten_nv",
          headerText: "Tên nhân viên",
          allowFiltering: true,
          allowSorting: false,
          width: 'auto'
        },
        {
          fieldName: "so_dt",
          headerText: "Điện thoại",
          allowFiltering: true,
          allowSorting: false,
          width: 'auto',
          textAlign: 'right'
        },
        {
          fieldName: "nhiemvu",
          headerText: "Nhiệm vụ",
          allowFiltering: true,
          allowSorting: false,
          width: 'auto'
        },
        {
          fieldName: "ghichu",
          headerText: "Ghi chú",
          allowFiltering: true,
          allowSorting: false,
          width: 'auto'
        },
      ]
    }
  },
  mounted() {
    $('#gridDsNhanVien').find('.e-parentmsgbar').addClass('d-none');
  },
}
</script>

<style scoped>
.e-parentmsgbar {
  display: none !important;
}
</style>
