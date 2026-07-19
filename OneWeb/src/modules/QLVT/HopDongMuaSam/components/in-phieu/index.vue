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

.fw-600 {
  font-weight: 600;
}
</style>
<script>
import moment from 'moment';
export default {
  name: "PhieuIn",
  components: {},
  props: ["data"],
  data: function () {
    return {
      // nguoiGiao: this.$auth.getMaNhanVien(),
      currentDate: {
        date: moment(new Date, 'DD/MM/YYYY').format('DD'),
        month: moment(new Date, 'DD/MM/YYYY').format('MM'),
        year: moment(new Date, 'DD/MM/YYYY').format('YYYY'),
      }
    };
  },
  computed: {
    noiDung: function(){
      return this.data.length > 0 ? this.data[0].NOIDUNG_MS: "";
    },
    tenNhaThau: function(){
      return this.data.length > 0 ? this.data[0].NHACUNGCAP: "";
    },
    maHd: function(){
      return this.data.length > 0 ? this.data[0].MA_HD: "";
    },
    // tongHd: function() {
    //   return this.dsPhieu.filter(item=> item.SO_SERI && item.SO_SERI !="0").length;
    // },
    // tongNo: function() {
    //   let sum = 0;
    //  this.dsPhieu.forEach(element => {
    //     sum+= Number(element.TONGNO)
    //   });
    //   return sum;
    // }
  },
  validations: {},
  watch: {},
  created: async function () {
    console.log('123', this.data)
  },
  async mounted() {},
  methods: {
  },
};
</script>
