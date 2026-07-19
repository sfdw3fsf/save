<template>
  <div class="input-group">
    <div class="input-text input-text__url">
      <input
        type="text"
        class="form-control highlight"
        v-model="path"
        :disabled="disabled"
      />
      <input ref="inputFile" style="display: none" type="file" @input="inputFilehChange"/>
    </div>
    <div class="input-addon input-addon__url">
      <button class="btn highlight btn-file" @click="btnBrowseClick" :disabled="disabled">
        <span class="-ap icon-more_horiz"></span>
      </button>
    </div>
  </div>
</template>

<script>
export default {
  props: ['value', 'disabled'],
  data: () => ({
    path: '',
    file: null
  }),
  watch: {
    value (newVal) {
      this.path = newVal
    }
  },
  mounted () {
    this.path = this.value
  },
  methods: {
    btnBrowseClick () {
      this.$refs.inputFile.click()
    },
    inputFilehChange () {
      this.path = this.$refs.inputFile.value.split('\\').pop().split('/').pop()
      this.$emit('input', this.path)
    }
  }
}
</script>
<style scoped>
.btn-li {
  cursor: pointer;
}

.btn-file {
  position: relative;
}

.input-file {
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  opacity: 0;
}

.input-group {
  width: 100%;
  display: flex;
  flex-wrap: nowrap;
}

.input-text__url {
  flex-grow: 1;
}

.input-addon__url {
  width: max-content;
}
</style>
