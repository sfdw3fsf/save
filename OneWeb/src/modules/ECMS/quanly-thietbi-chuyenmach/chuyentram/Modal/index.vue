<template src="./index.popup.html"></template>
<script>

export default {
  components: {},
  name: 'Modal',
  props: {
    modalShow: Boolean,
    dsTonghop: Object
  },
  computed:{
   
  },
  data: function () {
    return {
      dv: -1,
      md: -1,
      thongtinThem:[],
      dataRow:[],
      display: {}
    };
  },
  mounted: function () {

  },
  created: function () {
  },
  methods: {
    closeModal: function () {
      this.$emit('closeModal');
    },

  },
};
</script>

