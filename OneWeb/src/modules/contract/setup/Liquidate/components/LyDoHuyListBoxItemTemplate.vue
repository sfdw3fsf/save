<template>
    <div class='row-container'>
      <div class='row-text pl-2'>{{data.text}}</div>
      <div class='row-remove-icon' @click="onRemove">x</div>
    </div>
</template>
<script>
import { emit } from "@/utils/event"
export default {
  name: "listBoxItem",
  data () {
    return {
      data: {
        data: {}
      }
    }
  },
  methods: {
    onRemove () {
      emit('LyDoHuyItemDelete', this.data.id)
    }
  }
}
</script>
