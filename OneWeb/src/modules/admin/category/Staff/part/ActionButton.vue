<template>
  <div class="list-actions-top">
    <ul class="list">
      <li @click.prevent="createAction" :class="{ disabledInput: modeSave === 1 }">
        <a> <span class="icon one-file-plus"></span>Nhập mới </a>
      </li>
      <li>
        <a href="#" @click.prevent="saveAction"> <span class="icon one-save"></span>Ghi lại </a>
      </li>
      <li>
        <a href="#" @click.prevent="cancelAction"> <span class="icon nc-icon-glyph ui-1_circle-remove"></span>Hủy </a>
      </li>
      <li>
        <a :class="{ disabled: !canOpenModal }" href="#" @click.prevent="deleteAction"> <span class="icon one-trash"></span>Xóa </a>
      </li>
      <li>
        <a :class="{ disabled: !isEnableSearch }" href="#" @click.prevent="search"> <span class="icon one-search" />Tìm kiếm </a>
      </li>
      <li>
        <a href="#" :class="{ disabled: !canOpenModal }" @click.prevent="ganViTriAction"> <span class="icon nc-icon-glyph location_crosshair"></span>Gán vị trí </a>
      </li>
      <li>
        <a href="#" :class="{ disabled: !canOpenModal }" @click.prevent="ganKhuVucAction"> <span class="icon nc-icon-glyph location_position-pin"></span>Khu vực </a>
      </li>
    </ul>
  </div>
</template>

<script>
export default {
  name: 'ActionButton',
  components: {},
  data: () => ({}),
  computed: {},
  props: {
    isEnableSearch: {
      default: false,
      type: Boolean
    },
    canOpenModal: {
      default: false,
      type: Boolean
    },
    modeSave: {
      required: true,
      type: [Number, String]
    }
  },
  methods: {
    search() {
      this.$emit('search-action')
    },
    saveAction() {
      this.$emit('save-action')
    },
    createAction() {
      this.$emit('create-action')
    },
    cancelAction() {
      this.$emit('cancel-action')
    },
    deleteAction() {
      this.$emit('delete-action')
    },
    ganViTriAction() {
      this.$emit('gan-vi-tri-action')
    },
    ganKhuVucAction() {
      this.$emit('gan-khu-vuc-action')
    }
  }
}
</script>
<style scoped>
.disabledInput {
  pointer-events: none;
  color: #666 !important;
  border-color: rgba(153, 153, 153, 0.6) !important;
}

.disabledInput > a {
  color: #666 !important;
}
</style>