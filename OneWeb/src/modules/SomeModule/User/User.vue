<template src="./User.html"></template>
<style scoped src="./User.scss"></style>
<script>
import Demo from '@/components/Demo'
import { mapGetters, mapActions, mapState } from 'vuex'

export default {
  components: {
    Demo
  },
  computed: {
    ...mapState('user', [
      'user',
      'accessToken'
    ]),
    ...mapGetters('user', [
      'user'
    ])
  },
  data: () => ({
    account: {
      id: 1,
      name: 'vuong nv',
      email: 'vuongnv@vnpt.vn'
    },
    userFormData: {
      username: '',
      password: ''
    }
  }),
  methods: {
    ...mapActions('user', [
      'login',
      'setCurrentUser',
      'setAccessInfo',
      'clearCurrentUser'
    ]),
    doSomeThing () {
      alert('click me')
    },
    async onSubmit (evt) {
      evt.preventDefault()
      this.$store.dispatch('user/login', {
        'username': this.userFormData.username,
        'password': this.userFormData.password,
        'grant_type': 'password',
        'client_id': 'clientapp',
        'client_secret': 'password'
      })
    }
  }

}
</script>
