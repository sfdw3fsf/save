<template>
  <div class="box-form">
    <div class="legend-title">
      <span :id="'icon'+id"
            @click="toggleGroup(id)"
            class="icon fa fa-angle-down"
            style="cursor: pointer"
      ></span> {{ title }}
    </div>
    <div :id="'group'+id" class="bss992-info">
      <ItemHoDan v-for="(item, index) in items"
                 :key="index"
                 v-bind:index="index"
                 v-bind:item="{...item,
                 current_img: item.listImage.length > 0 ? 1 : 0}"/>
    </div>
  </div>
</template>
<script>
import ItemHoDan from "./ItemHoDan";
import Define from '../define';

export default {
  components: {ItemHoDan},
  props: {
    title: String,
    items: Array
  },
  computed: {
    id() {
      return Define.textToSlug(this.title);
    }
  },
  methods: {
    toggleGroup(id) {
      this.$emit('toggleGroup', id);
    }
  }
}
</script>
