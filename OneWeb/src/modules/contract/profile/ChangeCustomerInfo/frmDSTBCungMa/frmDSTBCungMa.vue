<template src="./frmDSTBCungMa.html">

</template>

<script>
export default {
  name: "frmDSTBCungMa",
  data: function () {
    return {
      dsThueBao: [],
      resolvePromise: null,
      selected :null,
      dsDichVuVT:[],
      dsLoaiHinhTB:[],
    }
  },

  methods: {
    async show(ds) {
      this.clear()
      this.dsThueBao = ds
      this.$refs['ChonThueBao'].show()
      await this.frmDSTBCungMa_Load()
      return new Promise((resolve, reject) => {
        this.resolvePromise = resolve
      })
    },
    hide() {
      this.$refs['ChonThueBao'].hide()
    },
    btnChapNhan() {
      this.resolvePromise({ok: true, option: null, data: this.selected})
      this.$refs['ChonThueBao'].hide()
    },
    rowSelected(args) {
      if (args.data) this.selected = args.data
    },
    recordDoubleClick(args) {
      if (args.data) this.selected = args.data
      this.btnChapNhan()
    },
    clear() {
      this.dsThueBao = []
      this.resolvePromise = null
      this.selected = null
    },
    async frmDSTBCungMa_Load() {
      try {
        this.loading(true)
        let dt1 = await this.$root.context.get("/web-hopdong/thaydoithongtinkhuyenmai/lay_ds_dichvu_vt")
        this.dsDichVuVT = dt1.data
        let dt2 = await this.$root.context.get("/web-hopdong/thaydoithongtinkhuyenmai/lay_ds_loaihinh_tb")
        this.dsLoaiHinhTB = dt2.data
        let lastIndex = this.dsThueBao.length
        for (let i = 0; i < lastIndex; i++) {
          let dichVu = this.dsDichVuVT.find(x => x.dichvuvt_id == this.dsThueBao[i].dichvuvt_id)
          if (dichVu) {
            this.dsThueBao[i].ten_dvvt = dichVu.ten_dvvt
          }
          let loaiHinh = this.dsLoaiHinhTB.find(x => x.loaitb_id == this.dsThueBao[i].loaitb_id)
          if (loaiHinh) {
            this.dsThueBao[i].loaihinh_tb = loaiHinh.loaihinh_tb
          }
        }
        this.dsThueBao = [...this.dsThueBao]
      }catch (e) {

      }finally {
        this.loading(false)
      }
    }
  }
}
</script>

<style scoped>

</style>
