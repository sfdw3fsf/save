<template src="./index.html">
</template>

<script>
export default {
  props: {
    id: String
  },
  name: 'ChonDonVi',
  data: function () {
    return {
      donvi_ql_id: 0,
      in_donvi_id: 0,
      donvi_index_id: 0,
      kieu: 0,
      ds: [],
      label1: 'Lựa chọn tổ thi công, đơn vị quản lý',
      visibleToThiCong: true,
      selected: null,
      enableChapNhan: false,

      resolvePromise: null,
      rejectPromise: null
    }
  },
  watch: {
    selected: function (val) {
      if (val) {
        this.enableChapNhan = true
      } else this.enableChapNhan = false
    }
  },
  methods: {
    show (donvi_ql_id, in_donvi_id, donvi_index_id, kieu, ds) {
      console.log('🚀 ~ file: index.vue:35 ~ show ~ ds', ds)
      try {
        this.clear()
        this.donvi_ql_id = donvi_ql_id || 0
        this.in_donvi_id = in_donvi_id || 0
        this.donvi_index_id = donvi_index_id || 0
        this.kieu = kieu || 0
        this.ds = (ds && ds.length) ? ds : []
        console.log('🚀 ~ file: index.vue:43 ~ show ~ this.ds', this.ds)
        if (this.donvi_index_id !== 0) {
          let idx = this.ds.findIndex(x => x.donvi_id === this.donvi_index_id)
          if (idx > -1) {
            setTimeout(() => {
              this.$refs.gridChonDonVi.selectRow(idx)
            }, 200)
          } else {
            setTimeout(() => {
              this.$refs.gridChonDonVi.selectRow(0)
            }, 200)
          }
        } else {
          setTimeout(() => {
            this.$refs.gridChonDonVi.selectRow(0)
          }, 200)
        }
        if (this.kieu === 1) {
          this.label1 = 'Lựa chọn đơn vị quản lý Địa chỉ TT'
          this.visibleToThiCong = false
          this.$refs.gridChonDonVi.grid.refresh()
        }
        return new Promise((resolve, reject) => {
          this.$bvModal.show(this.id)
          this.resolvePromise = resolve
          this.rejectPromise = reject
        })
      } catch (e) {
        console.log('🚀 ~ file: index.vue:70 ~ show ~ e', e)
      }
    },
    onModalHidden () {
      this.clear()
      this.resolvePromise({ok: false, option: null, data: null})
      this.$bvModal.hide(this.id)
    },
    clear () {
      this.donvi_ql_id = 0
      this.in_donvi_id = 0
      this.donvi_index_id = 0
      this.kieu = 0
      this.ds = []
      this.label1 = 'Lựa chọn tổ thi công, đơn vị quản lý'
      this.visibleToThiCong = true
      this.selected = null
    },
    rowSelected (args) {
      if (args.data) {
        this.selected = args.data
      }
    },
    chapnhan () {
      try {
        if (this.ds.length) {
          if (this.selected) {
            this.donvi_ql_id = this.selected.donviql_id
            this.in_donvi_id = this.selected.donvi_id
            this.resolvePromise({ok: true, option: null, data: this.selected})
            this.$bvModal.hide(this.id)
          }
        }
      } catch (e) {
        console.log('🚀 ~ file: index.vue:104 ~ chapnhan ~ e', e)
      }
    }
  }
}
</script>

<style>
a.disable-btn {
  pointer-events: none;
  cursor: default;
  color: #6c757d !important;
}
</style>
