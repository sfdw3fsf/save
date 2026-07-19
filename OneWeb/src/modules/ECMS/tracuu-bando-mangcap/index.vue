<template src="./index.template.html"></template>
<script>
  import Modal from './Modal'
export default {
  components: {Modal},
  data: function () {
    return {
      modalShow: false,
      doituong: null
    };
  },
  created: function () {

  },
  methods: {
    closeModal: function() {
      this.modalShow =! this.modalShow;
    },
    btnShowPopup(){
      this.doituong = null
      this.$bvModal.show('popupBanDo')      
    }
  },
};
</script>
