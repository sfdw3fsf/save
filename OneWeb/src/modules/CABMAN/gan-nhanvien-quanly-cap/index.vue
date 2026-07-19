<template src="./index.template.html"></template>
<script>
  import Modal from './Modal'
export default {
  components: {Modal},
  data: function () {
    return {
      modalShow: false,
      doiTuong : {
        DOITUONG_ID: 301022,
        LOAI_DT: 'CAP'
      }
     
    };
  },
  created: function () {

  },
  methods: {
    btnShowForm(){
      this.doiTuong = {
        DOITUONG_ID: 301022,
        LOAI_DT: 'CAP'
      }
      this.$bvModal.show('popupGanNVQL')      
    }
  },
};
</script>