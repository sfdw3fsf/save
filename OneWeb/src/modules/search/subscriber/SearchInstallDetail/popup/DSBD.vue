<template src="./DSBD.html"></template>

<script>
import breadcrumb from '@/components/breadcrumb'
import API from './../API'

export default {
  components: {},
  name: 'DSBDmodal',
  props: {
    list_bd: ''
  },

  mounted() {},

  data() {
    return {
      biendong: {}
    }
  },

  methods: {
    grdBiendongChanged: function(obj) {
      this.biendong = obj[0]
    },

    chapnhan: function() {
      //console.log(this.biendong);
      this.$bvModal.hide('pop_dsbd')
      this.$emit('get_dsbd', this.biendong)
    }
  }
}
</script>
