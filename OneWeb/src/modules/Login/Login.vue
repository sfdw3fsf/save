<template src='./Login.html'></template>
<style scoped src='./Login.scss'></style>
<script>
import { mapActions, mapState, mapGetters } from 'vuex'
import AuthenApi from '@/api/AuthenApi'
import Vue from 'vue'


export default {
  name: 'Login',
  data: () => ({
    username: 'TEST_APP1.HPG',
    password: 'dhdbtt2017'
  }),
  computed: {
    ...mapState('user', [
      'accessToken'
    ]),
    ...mapGetters('user', [
      'isAuthenticated'
    ])
  },
  create () {

  },
  watch: {

  },

  methods: {
    ...mapActions('user', [
      'login',
      'setCurrentUser',
      'setAccessInfo',
      'clearCurrentUser'
    ]),
    async onSubmit () {
      try {
        console.log('onSubmit');
        await this.$store.dispatch('user/login', {
          'username': this.username,
          'password': this.password,
          'grant_type': 'password',
          'client_id': 'clientapp',
          'client_secret': 'password'
        })
        this.$toast.success('Login Success')
        // chuyển trang
        this.redirectToHome()
      } catch (err) {
        console.log(err)
      }
    },
    async doLogin2 () {
      try {
        console.log('doLogin2');
        let result = await AuthenApi.login(this.axios, {
          'username': this.userFormData.username,
          'password': this.userFormData.password,
          'grant_type': 'password',
          'client_id': 'clientapp',
          'client_secret': 'password'
        })
        console.log(result)
      } catch (err) {
        console.log(err)
      }
    },
    redirectToHome () {
      this.$router.push({name: '/quantri/UploadFileHosoDienTu'})
    }
  }

}
</script>
