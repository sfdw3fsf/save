<template src="./index.html"> </template>

<script>
export default {
  components: {},
  data() {
    return ''
  },
  methods: {},
  async mounted() {
    // this.getThongTinCuocGoi()
  },
  async created() {
    // this.getThongTinCuocGoi()
  },
  watch: {
    // thongtincuocgoi: function (val) {
    //   this.getThongTinCuocGoi()
    // },
    // thongtincuocgoi_end: function (val) {
    //   this.getThongTinCuocGoi()
    // },
    // isCalling: function (val) {
    //   this.getThongTinCuocGoi()
    // }
  }
}
</script>


<style scoped>
  .list-actions-top h4 {
    display: inline-block;
    padding: 5px 10px;
    margin: 0px;
  }

  .notification-icon {
    z-index: 2;
    position: fixed;
    bottom: 70px;
    right: 70px;
    background-color: tomato;
    border-radius: 50%;
    width: 60px;
    height: 60px;
    display: flex;
    justify-content: center;
    align-items: center;
    animation: bounce 1s infinite; /* Animation for motion */

    /* Add a drop shadow for depth */
    box-shadow: 0 0 20px rgba(0, 0, 0, 0.6);
  }

  .notification-icon-inner {
    font-size: 30px;
    color: #fff;
  }

  /* Keyframes animation for motion */
  @keyframes bounce {
    0%, 100% {
      transform: translateY(0);
    }
    50% {
      transform: translateY(-25px);
    }
  }

  /* Style for the phone icon (font-awesome icon in this case) */
  .fa-phone::before {
    content: "\f095"; /* Unicode code for the phone icon */
  }

  .list-actions .e-has-child div.e-fullrow {
    cursor: not-allowed;
  }
</style>
