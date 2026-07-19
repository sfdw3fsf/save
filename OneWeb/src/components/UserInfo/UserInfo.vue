<template src='./UserInfo.html'></template>
<script>
export default {
  name: 'UserInfo',
  props: {
    fullName: {
      type: String,
      required: true
    },
    id: {
      type: Number,
      required: true
    }
  },
  methods: {
    userLogout () {
      this.$emit('userLogout')
    }
  }
}
</script>
