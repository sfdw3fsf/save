<template src="./index.template.html"></template>
<script>
  import Modal from './Modal'
export default {
  components: {Modal},
  data: function () {
    return {
      modalShow: false,
      doiTuong : {
        tramtb_id: 456
      }
     
    };
  },
  created: function () {

  },
  methods: {
    closeModal: function() {
      this.modalShow =! this.modalShow;
    },
    btnShow(){
      this.$bvModal.show('popupDauNoiThietBi')
    }
  },
};
</script>