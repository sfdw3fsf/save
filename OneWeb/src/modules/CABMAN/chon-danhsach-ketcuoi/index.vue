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
      },
      kieu: -1,
    };
  },
  created: function () {
    this.lstKieu = [
      { ID: 0, NAME: 'Kết cuối đã đấu cáp' },
        { ID: 1, NAME: 'Kết cuối chưa đấu cáp' },
        { ID: 2, NAME: 'Kết cuối' },
      ]
    this.doiTuong.kieu = this.kieu = this.lstKieu[0].ID
  },
  methods: {
    closeModal: function() {
      this.modalShow =! this.modalShow;
    },
    changeCbo: function(){
      this.doiTuong.kieu = this.kieu
    },
    btnShow: function(){
      this.doiTuong.kieu = this.kieu
      this.$bvModal.show('popupChonKetCuoi')
    }
  },
};
</script>