<template>
  <div class="tc-pagination">
      <a @click="pre(false)" class="btn one-arrow-left-forward"></a>
      <a @click="pre(2)" class="btn fa fa-angle-double-left"></a>
      <a @click="pre(1)" class="btn fa fa-angle-left"></a>
      <span class="text">Bản ghi</span>
      <div class="dropdown inline">
        <div class="fw6 input-number" data-toggle="dropdown">
          <span class="text-main">{{showPerpage}}</span>/{{total}}
        </div>
        <div class="dropdown-menu">
          <a class="dropdown-item">10</a>
          <a class="dropdown-item">20</a>
        </div>
      </div>
      <a @click="next(1)" class="btn fa fa-angle-right"></a>
      <a @click="next(2)" class="btn fa fa-angle-double-right"></a>
      <a @click="next(false)" class="btn one-arrow-right-forward"></a>
    </div>
</template>
<script>
export default {
  props: {
    total: Number,
    per_page: Number,
    value: Number
  },
  computed: {
    showPerpage () {
      return this.value * this.per_page > this.total ? this.total : this.value * this.per_page
    }
  },
  methods: {
    next (step) {
      let totalPage = this.total / this.per_page
      let final = this.total % this.per_page
      let round = Math.floor(totalPage)
      if (final > 0) {
        round = round + 1
      }
      if (!step) {
        this.$emit('input', round)
        return true
      }
      if (this.value < (totalPage)) {
        this.$emit('input', this.value + step)
      }
    },
    pre (step) {
      if (!step) {
        this.$emit('input', 1)
      } else {
        if ((this.value - step) > 0) {
          this.$emit('input', this.value - step)
        } else {
          this.$emit('input', 1)
        }
      }
    }
  }
}
</script>
