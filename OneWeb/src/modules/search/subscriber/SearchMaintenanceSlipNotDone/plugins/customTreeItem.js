import Vue from 'vue'

export function customTreeItem () {
  return {
    template: Vue.component('treeItemTemplate', {
      template: `<section>
        <span class="icon nc-icon-glyph files_single-content-02 text-main">  </span> {{ data.ten_dv }}</section>
        `,
      data () {
        return {
          data: {}
        }
      }
    })
  }
}
