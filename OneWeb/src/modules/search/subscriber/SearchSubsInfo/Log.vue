<template src="./Log.html">

</template>

<script>
export default {
  name: "Log",
  data() {
    return {
      ds: [],
      cols: [
        {
          fieldName: "thoigian",
          headerText: "Thời gian",
          allowFiltering: true,
          allowSorting: false,
          textAlign: 'right',
          width: 100
        },
        {
          fieldName: "admin",
          headerText: "Admin",
          allowFiltering: true,
          allowSorting: false,
          width: 100
        },
        {
          fieldName: "tacdong",
          headerText: "Tác động",
          allowFiltering: true,
          allowSorting: false,
        },
        {
          fieldName: "ghichu",
          headerText: "Ghi chú",
          allowFiltering: true,
          allowSorting: false
        },
      ],
    }
  },
  methods: {
    handleShowModal() {
    },

    showModal(data) {
      this.ds = data;
      this.$refs.frmLog.show();
    },
    hideModal() {
      this.$refs.frmLog.hide()
    }
  }
}
</script>

<style scoped>

</style>
