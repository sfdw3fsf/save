<template src="./QuanLyBieuMau.html">
</template>

<script>
export default {
  name: "QuanLyBieuMau",
  props: {
    id:String,
  },
  data: function () {
    return {
      grcDS_BieuMau: [],
      selected: null,
      URLTemplate: function () {
        return {
          template: {
            template: `
              <div>
              <a target="_blank" :href="data.url">{{ data.url }}</a>
              </div>
            `,
            data() {
              return {
                data: {}
              }
            },
          }
        }
      }
    }
  },
  async created() {
  },
  methods:{
    async focusMyElement() {
      try {
        this.loading(true)
        await this.NAP_DS_BIEUMAU()
      } catch (e) {
        this.$root.toastError("Có lỗi khi nạp danh sách biểu mẫu")
        console.log(e)
      } finally {
        this.loading(false)
      }
    },
    async NAP_DS_BIEUMAU() {
      await this.$root.context.get("web-hopdong/QuanLyIssue/nap-ds-bieumau").then(res =>{
        this.grcDS_BieuMau = res.data
      }).catch(e => {
        throw e
      })
    },
    grcDS_BieuMau_CommandClicked: async function (name, args){
      if (name.toUpperCase() == "DOWNLOAD") {
        console.log("grcDS_BieuMau_CommandClicked:",args)
        this.DOWLOAD_OPEN(false, args)
      }
    },
    DOWLOAD_OPEN(xemFile, item) {
      var link = document.createElement('a');
      link.download = item.filename;
      link.href = item.url;
      document.body.appendChild(link);
      link.click();
      link.remove();
    },
    grcDS_BieuMau_queryCellInfo: function (args) {
      switch (args.column.field) {
        case 'ten_loaihd':
          args.cell.style.color = 'red'
          args.cell.style.fontWeight = 'bold'
          break
        case 'ten_dvvt':
          args.cell.style.fontWeight = 'bold'
              break
        case 'loaihinh_tb':
          args.cell.style.color = 'darkred'
          args.cell.style.fontWeight = 'bold'
              break
        case 'filename':
          args.cell.style.color = 'blue'
          args.cell.style.fontWeight = 'bold'
      }
    },
    grcDS_BieuMau_rowSelected() {

    }
  }
}
</script>

<style scoped>

</style>
