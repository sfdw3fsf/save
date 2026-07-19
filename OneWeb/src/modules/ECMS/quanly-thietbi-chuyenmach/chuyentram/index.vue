<template src="./index.template.html"></template>
<script>
  import Modal from './Modal'
export default {
  components: {Modal},
  data: function () {
    return {
      modalShow: false
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
