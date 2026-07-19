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
import { rowSelected } from '@syncfusion/ej2-grids'
export default {
  name: 'danh-sach-ton',
  components: {},
  props: ['data', 'dsTon'],
  data: function () {
    return {
      nguoiGiao: this.$auth.getMaNhanVien(),
      currentDate: {
        date: moment(new Date(), 'DD/MM/YYYY').format('DD'),
        month: moment(new Date(), 'DD/MM/YYYY').format('MM'),
        year: moment(new Date(), 'DD/MM/YYYY').format('YYYY')
      },
    }
  },
  validations: {},
  watch: {},
  created: async function () {
  },
  async mounted () {},
  methods: {
  }
}
</script>
