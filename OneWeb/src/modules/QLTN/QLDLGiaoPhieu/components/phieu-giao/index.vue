<template src="./index.html"></template>
<style scoped>
.table-1 tr td:first-child {
  padding-right: 1rem;
}

.table-2 {
  border: 1px solid #000;
}
.table-2 thead tr th {
  border: 1px solid #000;
}

.table-2 tbody tr td {
  border: 1px solid #000;
}

table, tr, td, th, tbody, thead, tfoot {
  page-break-inside: avoid !important;
}
</style>
<script>
import moment from 'moment'
export default {
  name: 'phieu-giao',
  components: {},
  props: ['data', 'dsPhieu'],
  data: function () {
    return {
      nguoiGiao: this.$auth.getMaNhanVien(),
      currentDate: {
        date: moment(new Date(), 'DD/MM/YYYY').format('DD'),
        month: moment(new Date(), 'DD/MM/YYYY').format('MM'),
        year: moment(new Date(), 'DD/MM/YYYY').format('YYYY'),
      }
    }
  },
  computed: {
    tongHd: function () {
      return this.dsPhieu.filter(item => item.SO_SERI && item.SO_SERI !== '0').length
    },
    tongNo: function () {
      let sum = 0
      this.dsPhieu.forEach(element => {
        sum += Number(element.TONGNO)
      })
      return sum
    }
  },
  validations: {},
  watch: {},
  created: async function () {
    console.log(this.currentDate)
  },
  async mounted () {},
  methods: {
  }
}
</script>
