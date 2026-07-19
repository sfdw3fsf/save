<template src='./Demo.html'></template>
<style scoped src="./Demo.scss"></style>
<script>
import UserInfo from '@/components/UserInfo'
export default {
  name: 'Demo',
  components: {
    UserInfo
  },
  data: () => ({
    user: {
      id: 1,
      name: 'VuongNV'
    }
  }),
  create () {

  },
  watch: {

  },

  methods: {
    onLogout () {
      alert('ahihi')
    }
  }

}
</script>
