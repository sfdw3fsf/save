<template src="../ThueBaoCungMa/index.html">
</template>
<style scoped>
input[type=number] {
  text-align:right;
}
input[type=text] {
  text-align:left;
}
</style>
<script>

export default {
  props: {
  },
  data: function () {
    return {
      dsThuebaoCungMa: [],
      dsDichVu: [],
      dsLoaiHinh: [],
      resolvePromise: {},
      rejectPromise: {},
      dataItem: {}
    }
  },
  methods: {
    async show (data) {
      await this.getDsLoaiHinh()
      await this.getDsDichVu()
      return new Promise((resolve, reject) => {
        this.dsThuebaoCungMa = data

        // debugger
        this.dsThuebaoCungMa.forEach((item, index, array) => {
          this.dsDichVu.forEach((itemdv, index, array) => {
            if (item.dichvuvt_id == itemdv.dichvuvt_id) {
              item.ten_dvvt = itemdv.ten_dvvt
            }
          })
          this.dsLoaiHinh.forEach((itemdv, index, array) => {
            if (item.loaitb_id == itemdv.loaitb_id) {
              item.loaihinh_tb = itemdv.loaihinh_tb
            }
          })
        })

        this.$bvModal.show('thuebaocungma')
        this.resolvePromise = resolve
        this.rejectPromise = reject
      })
    },
    showModal: function () {
    },
    closeForm () {
      this.$bvModal.hide('thuebaocungma')
      let listResult = []
      listResult.push(this.dataItem)
      this.resolvePromise({
        itemSelected: listResult})
    },
    grid_selectedRowChanged: function (dataItem) {
      this.dataItem = dataItem
      // this.resolvePromise({
      //   itemSelected : dataItem})
    },
    async getDsDichVu () {
      let rs = await this.$root.context.get('web-quantri/thongso_internet/lay_ds_dichvu')
      this.dsDichVu = rs.data
    },
    getDsLoaiHinh: async function () {
      let rs = await this.$root.context.get('web-quantri/thongso_internet/lay_ds_loaihinh')
      this.dsLoaiHinh = rs.data
    }
  },
  mounted: async function () {

  },
  watch: {

  }
}
</script>

<style scoped>
a.disable-btn {
  pointer-events: none;
  cursor: default;
  color: #6c757d !important;
}

#table-scroll {
  height: 410px;
  overflow: auto;
  margin-top: 20px;
}

</style>
