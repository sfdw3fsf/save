<template src="./index.html"></template>
<script>
  import TraCuuTBao from './TraCuuTBao'
export default {
  components: {TraCuuTBao},
  data: function () {
    return {
      modalShow: false,
      name: "index",
      doiTuong : {
        cap_id: 529523,
      }
     
    };
  },
  created: function () {

  },
  methods: {
    btnShowTraCuuTBao(){
      this.doiTuong.cap_id = 529523
      this.$bvModal.show('popupTraCuuTBao')      
    }
  },
};
</script>