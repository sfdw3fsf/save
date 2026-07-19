<template>
  <div class="actions">
    <button class="btn" @click="handlePrefixSelect">
      <span class="fa fa-angle-left"></span>
    </button>
    <button class="btn" @click="handleAllNotSelect">
      <span class="fa fa-angle-double-left" ></span>
    </button>
    <button class="btn"  @click="handlePrefixNotSelect">
      <span class="fa fa-angle-right" ></span>
    </button>
    <button class="btn"  @click="handleAllSelect">
      <span class="fa fa-angle-double-right"></span>
    </button>
  </div>
</template>
<script>
import { mapActions, mapState } from 'vuex'
export default {
  data () {
    return {

    }
  },
  computed: {
    ...mapState('prefixOfCounty', ['danhSachPrefixChuaChon', 'isLoading', 'dataNotSelect', 'danhSachPrefixDaChon'])
  },
  methods: {
    handlePrefixNotSelect () {
      this.$emit('changPrefixNotPSelect')
    },
    handlePrefixSelect () {
      this.$emit('changPrefixSelect')
    },
    handleAllNotSelect () {
      this.$emit('changAllNotSelect')
    },
    handleAllSelect () {
      this.$emit('changAllSelect')
    }

  }
}
</script>
