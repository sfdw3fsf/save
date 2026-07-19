<template>
  <table class="w-100">
    <tbody >
      <tr :class="{bold: isFolder}" @click="toggle" @dblclick="makeFolder">
        <td>
        <span v-if="isFolder">
          <template v-if="isOpen">
            <template v-if="hasGroupOpenSlot">
              <slot name="groupClosed" />
            </template>
            <template v-else>
              <span class="treegrid-indent" style="width:15px"/>
              <span class="treegrid-expander glyphicon glyphicon-minus" v-if="addsub"></span>
              <span class="treegrid-expander glyphicon glyphicon-chevron-down" v-else></span>
            </template>
          </template>
          <template v-else>
            <template v-if="hasGroupCloseSlot">
              <slot name="groupOpened" />
            </template>
            <template v-else>
              <span class="treegrid-indent" style="width:15px"/>
                <span class="treegrid-expander glyphicon glyphicon-plus" v-if="addsub"></span>
                <span class="treegrid-expander glyphicon glyphicon-chevron-down" v-else></span>
            </template>
          </template>
        </span>
        <span v-else>
          <template v-if="hasItemSlot">
            <slot name="item"></slot>
          </template>
          <template v-else>
            <span class="treegrid-indent" style="width:15px"/>
          </template>
        </span> {{ item.name }}
        </td>
      </tr>
      <tr class="tree" v-show="isOpen" v-if="isFolder">
        <template v-if="item.children">
          <TreeView
            class=""
            v-for="(child, index) in item.children"
            :key="index"
            :item="child"
          >
            <template v-slot:groupOpened>
              <template v-if="hasGroupOpenSlot">
                <slot name="groupOpened" />
              </template>
              <template v-else>
                <span class="treegrid-indent" style="width:15px"/>
                <span class="treegrid-expander glyphicon glyphicon-plus" v-if="addsub"></span>
                <span class="treegrid-expander glyphicon glyphicon-chevron-right" v-else></span>
              </template>
            </template>
            <template v-slot:groupClosed>
              <template v-if="hasGroupOpenSlot">
                <slot name="groupClosed" />
              </template>
              <template v-else>
                <span class="treegrid-indent" style="width:15px"/>
              <span class="treegrid-expander glyphicon glyphicon-minus" v-if="addsub"></span>
              <span class="treegrid-expander glyphicon glyphicon-chevron-down" v-else></span>
              </template>
            </template>
            <template v-slot:item>
              <template v-if="hasItemSlot">
                <slot name="item"></slot>
              </template>
              <template v-else>
                <span class="treegrid-indent" style="width:15px"/>
              </template>
            </template>
          </TreeView>
        </template>
      </tr>
  </tbody>
  </table>
</template>

<script>
import TreeView from './TableTreeView'

export default {
  name: 'TreeView',
  props: ['item', 'isOpened','addsub'],
  comments: {
    TreeView
  },
  data () {
    return {
      isOpen: !!this.isOpened
    }
  },
  computed: {
    isFolder: function () {
      return this.item.children &&
          this.item.children.length
    },
    hasGroupOpenSlot () {
      return !!this.$slots.groupOpened
    },
    hasGroupCloseSlot () {
      return !!this.$slots.groupClosed
    },
    hasItemSlot () {
      return !!this.$slots.item
    }
  },
  methods: {
    toggle: function () {
      if (this.isFolder) {
        this.isOpen = !this.isOpen
      }
    },
    makeFolder: function () {
      if (!this.isFolder) {
        this.$emit('make-folder', this.item)
        this.isOpen = true
      }
    }
  }
}
</script>
