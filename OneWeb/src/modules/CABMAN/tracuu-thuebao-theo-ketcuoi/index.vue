<template src="./index.template.html"></template>
<script>
  import TraCuuTBao from './TraCuuTBao'
export default {
  components: {TraCuuTBao},
  data: function () {
    return {
      doiTuong : {
        ketcuoi_id: 535340,
        vitri: -1,
        mat: 2
      }
     
    };
  },
  created: function () {

  },
  methods: {
    btnShowTraCuuTBao(){
      this.doiTuong = {
        ketcuoi_id: 535340,
        vitri: -1,
        mat: 2
      }
      this.$bvModal.show('popupTraCuuTBao')      
    }
  },
};
</script>