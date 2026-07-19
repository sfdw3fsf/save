<template>
  <table class="table-result table-gachle">
    <tbody>
      <template v-if="!loading">
        <tr
          v-bind:key="'col4' + j"
          v-for="(option, j) in temp_options"
          :class="current == j ? 'highlight1 '  : ''"
        > 
          <td class="w30 center" v-if="multiple === true " >
            <div class="check-action">
              <!-- <input type="checkbox" > -->
              <input
               :value="option[unique]"
                v-model="multiple_value"
                type="checkbox"
                @change="pushItem"
                class="check"
              />
              <span class="name"></span>
            </div>
          </td>
          <td
            :class="item.align ? 'text-' + item.align : ''"
            :style="item.width ? 'width: ' + item.width : ''"
            v-bind:key="'col3' + i"
            v-for="(item, i) in config"
          >
            {{ option[item.key] }}
          </td>
        </tr>
      </template>
      <template v-else>
        <tr class="text-center">
          <td colspan="75%">
            <b-spinner
              class="mt-3 mb-3"
              variant="primary"
              label="Spinning"
            ></b-spinner>
          </td>
        </tr>
      </template>
    </tbody>
  </table>
</template>
<script>
export default {
  props: {
    options: Array,
    config: Array,
    header: Object,
    unique: String,
    loading: Boolean,
    multiple: Boolean
  },
  data: () => ({
    temp_options: [],
    multiple_value: [],
    current_value: {},
    current: 0

  }),
  created () {
    this.temp_options = this.options
  },
  watch: {
    options: {
      handler () {
        this.multiple_value = []
        this.temp_options = this.options
      },
      deep: true
    }
  },
  methods: {
    pushItem () {
      if (this.multiple === true) {
        this.$emit('input', this.multiple_value)
      }
    }

  }

}
</script>

<style>
</style>
