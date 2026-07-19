<template src="./index.template.html"></template>
<script>
  import Modal from './Modal'
export default {
  components: {Modal},
  data: function () {
    return {
      modalShow: false,
      doiTuong : {
        kieu: 0,
      }
     
    };
  },
  created: function () {

  },
  methods: {
    closeModal: function() {
      this.modalShow =! this.modalShow;
    },
  },
};
</script>