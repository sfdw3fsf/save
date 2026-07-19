<template src="./index.html">

</template>

<script>
export default {
  name: "DSThietBi",
  props: {
    dsThietBi: {
      type: Array,
      required: true
    }
  },
  data() {
    return {
      cols: [
        {
          fieldName: "ten_dv",
          headerText: "Đơn vị",
          allowFiltering: true,
          allowSorting: false,
          width: 'auto'
        },
        {
          fieldName: "nguoi_cn",
          headerText: "Người CN",
          allowFiltering: true,
          allowSorting: false,
          width: 'auto'
        },
        {
          fieldName: "ngay_cn",
          headerText: "Từ ngày",
          allowFiltering: true,
          allowSorting: false,
          width: 'auto',
          format: 'dd/MM/yyyy HH:mm a',
          type: 'date',
          textAlign: 'right'
        },
        {
          fieldName: "kieu_tbi",
          headerText: "Kiểu T.Bị",
          allowFiltering: true,
          allowSorting: false,
          width: 'auto'
        },
        {
          fieldName: "seri",
          headerText: "Seri",
          allowFiltering: true,
          allowSorting: false,
          width: 'auto'
        },
      ]
    }
  },
  mounted() {
    $('#gridDsThietBi').find('.e-parentmsgbar').addClass('d-none');
  },
}
</script>

<style scoped>
.e-parentmsgbar {
  display: none !important;
}
</style>
