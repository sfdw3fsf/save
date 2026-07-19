<template src="./index.template.html"></template>
<script>
import { SplitterPlugin } from '@syncfusion/ej2-vue-layouts'
import pane1Content from './pane/pane1'
import pane2Content from './pane/pane2'
import Vue from 'vue'
import EventBus from '@/utils/eventBus'
Vue.use(SplitterPlugin)
export default {
  name: 'rightPaneContent',
  data () {
    return {
      pane1Content: function () {
        return { template: pane1Content }
      },
      pane2Content: function () {
        return { template: pane2Content }
      },
      sizeAreaL: 40
    }
  },
  computed: {
    getSizeAreaL () {
      return this.sizeAreaL + '%'
    }
  },
  watch: {
  },
  methods: {
    show_pane_daunoi (node) {
      var allPanes = this.$refs.splitterObj2.$el.ej2_instances[0].allPanes.length
      if (allPanes === 1) {
        this.addPane(node)
      } else {
        EventBus.$emit('SHOW_TRAC_NGHIEM', node)
      }
    },
    hide_pane_daunoi () {
      this.removePane()
    },
    removePane: function () {
      this.$refs.splitterObj2.removePane(1)
    },
    async addPane (node) {
      console.log('🚀 ~ file: index.vue ~ line 44 ~ addPane ~ node', node)
      await this.$refs.splitterObj2.$el.ej2_instances[0].addPane({
        size: '60%',
        content: this.pane2Content,
        min: '300px',
        collapsible: true
      }, 1)
      EventBus.$emit('SHOW_TRAC_NGHIEM', node)
    }
  },
  async mounted () {
    EventBus.$on('SHOW_PANE_DAUNOI', this.show_pane_daunoi)
    EventBus.$on('HIDE_PANE_DAUNOI', this.hide_pane_daunoi)
  }
}
</script>
<style>
  .default-splitter .e-splitter.e-splitter-horizontal .e-pane.e-pane-horizontal, .e-splitter.e-splitter-vertical .e-pane.e-pane-horizontal
  {
      padding: 0px;
  }
</style>
