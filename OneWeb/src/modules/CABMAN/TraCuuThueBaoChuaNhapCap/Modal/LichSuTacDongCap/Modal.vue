<template src="./Modal.html"></template>
<script>
export default {
  name: 'Modal',
  props: ['modalId', 'ma_tb'],
  data () {
    return {
      tungay: null,
      denngay: null,
      dateConfig: {
        altInput: true,
        dateFormat: 'd/m/Y',
        altFormat: 'd/m/Y'
      },
      disabled: true,
      matb: this.ma_tb,
      danhsach: [],
      indexFocus: 0
    }
  },
  methods: {
    grid_selectedRowChanged () {},
    async shownModal () {
      this.matb = this.ma_tb
      var today = new Date()
      var lastDay = new Date(today.getFullYear(), today.getMonth() + 1, 0)
      var firstDay = new Date(today.getFullYear(), today.getMonth(), 1)
      this.denngay = lastDay
      this.tungay = firstDay
    },
    hiddenModal () {
      this.danhsach = []
      this.matb = null
      this.tungay = null
      this.denngay = null
      this.disabled = true
    },
    async getData () {
      // if (this.checkDisable) return
      try {
        this.loading(true)
        const data = {
          'ma_tb': this.matb,
          'tu_ngay': this.tungay,
          'den_ngay': this.denngay
        }
        console.log('ls-tacdong-cap-thuebao', data)
        var rs = await this.$root.context.get(
          '/web-cabman/mang-truyen-dan/ls-tacdong-cap-thuebao', data
        )
        if (rs.data !== null) {
          this.danhsach = rs.data
        }
        console.log('this.danhsach', this.danhsach)
        this.loading(false)
      } catch (error) {
        this.loading(false)
      }
    },
    checkDisable () {
      return !this.matb || !this.tungay || !this.denngay
    }
  },
  computed: {

  },
  async mounted () {

  }
}
</script>

<style scoped src="./index.scss">
</style>
