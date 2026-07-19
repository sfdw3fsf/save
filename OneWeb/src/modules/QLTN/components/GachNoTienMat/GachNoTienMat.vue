<template src="./GachNoTienMat.html"></template>
<style scoped src="./GachNoTienMat.scss"></style>
<script>
import breadcrumb from '@/components/breadcrumb'
export default {
  components: { breadcrumb },
  name: 'GachNoTienMat',
  mounted () {
    // this.post(this.$API_URL + 'executor/sel_lay_thang_no', {}).then(
    //   (response) => {
    //     this.sel_lay_thang_no = response.data.data
    //   }
    // )
    // this.post(this.$API_URL + 'executor/fn_chukyno_kyhoadon_sel', {
    //   namthang: '',
    // }).then((response) => {
    //   this.fn_chukyno_kyhoadon_sel = response.data.data
    // })
  },
  destroyed () {},
  data () {
    return {
      header: {
        title: 'GẠCH NỢ TIỀN MẶT',
        list: [
          { name: 'Gạch nợ tiền', link: { name: 'Ui.cards' } },
          {
            name: 'Tra cứu phiếu thanh toán theo ngày',
            link: { name: 'Ui.buttons' }
          }
        ]
      },
      sel_lay_thang_no_val: null,
      fn_chukyno_kyhoadon_sel_val: null,
      sel_lay_thang_no: [],
      fn_chukyno_kyhoadon_sel: [],
      dateConfig: {
        altInput: true,
        dateFormat: 'd/m/Y',
        altFormat: 'd/m/Y'
      },
      from_date: new Date(),
      to_date: new Date()
    }
  },
  methods: {
    alert: function () {
      alert('Hello')
    }
  }
}
</script>
