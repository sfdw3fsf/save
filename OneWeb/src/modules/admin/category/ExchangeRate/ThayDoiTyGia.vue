<template>
  <div>
    <ExchangeRate ref="ExchangeRate"/>
  </div>
</template>

<script>
import ExchangeRate from './popup/ExchangeRate'

export default {
  name: 'ThayDoiTyGia',
  components: {
    ExchangeRate
  },
  mounted () {
    this.showPopupExchangeRate()
  },
  methods: {
    showPopupExchangeRate () {
      this.$refs.ExchangeRate.showModal()
    }
  }
}
</script>

<style scoped>

</style>
