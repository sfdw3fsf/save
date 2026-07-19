<template>
  <div class="qt_ms" :class="isOpen?'qt_ms--show':''">
    <div class="qt_ms--disabled" v-if="disabled"></div>
    <div class="app-icon" @click="openSelect">
      <Icon icon="fe:app-menu" />
    </div>
    <div class="qt_ms__panel" @click.prevent="openSelect" :class="disabled ? 'qt_ms__panel--disabled' : ''">
      <div v-if="selected.length == 0" class="qt_ms__panel__placeholder">{{ placeholder }}</div>
      <div v-for="item in selected" class="selected_pill" :class="disabled ? 'selected_pill--disabled' : ''" :key="item">
        <span>{{ getSelectedLabel(item) }}</span>
        <div v-if="!disabled" class="selected_pill__remove" @click.stop="removeOption(item)">
          <Icon icon="mdi:remove-bold" width="10px" height="10px" />
        </div>
      </div>
    </div>
    <div class="qt_ms__controller" ref="controller">
      <div class="qt_ms__controller__overlay" ref="overlay" @click="closeSelect"></div>
      <div class="controller__wrapper">
        <div class="icon-resize-left" v-show="isOpen" @mousedown="startResize">
            <Icon
          icon="mdi:resize-bottom-right" width="100%" height="100%" />
          </div>
        <div class="qt_ms__controller__panel" ref="panel">
          <div v-for="option in computedOptions" class="qt_ms__controller__panel__item" :class="selected.includes(option.id) || selected.includes(allValue) ? 'qt_ms__controller__panel__main__item--selected' : ''" :key="option.id" @click="toggle(option.id)">
            <input type="checkbox" :checked="selected.includes(option.id) || selected.includes(allValue)" />
            <label>{{ option.label }}</label>
          </div>
          
          <!-- <div class="icon-resize-right" @mousedown="startResize">
            <Icon
          icon="mdi:resize-bottom-right" width="100%" height="100%" />
          </div> -->
      </div>
      </div>
    </div>
  </div>
</template>

<script>
import { Icon } from '@iconify/vue2'
export default {
  name: 'MultiSelect',
  components: {
    Icon
  },
  props: {
    value: {
      type: Array,
      default: () => []
    },
    options: {
      type: Array,
      default: () => []
    },
    disabled: {
      type: Boolean,
      default: false
    },
    placeholder: {
      type: String,
      default: 'Chưa chọn bản ghi nào'
    },
    allValue: {
      default: '-1'
    }
  },
  data() {
    return {
      selected: this.value,
      isOpen: false
    }
  },
  watch: {
    value(val) {
      this.selected = val
    }
  },
  methods: {
    startResize(e){
      console.log('start resize');
      e.preventDefault();
      document.addEventListener('mousemove', this.resize)
      document.addEventListener('mouseup', this.stopResize)
    },
    resize(e){
      const panel = this.$refs.panel
      const newWidth = panel.getBoundingClientRect().right - e.clientX
      
      if(newWidth > 200){
        panel.style.width = newWidth + 'px'
      }
    },
    stopResize(){
      document.removeEventListener('mousemove', this.resize)
      document.removeEventListener('mouseup', this.stopResize)
    },
    toggle(option) {
      if (option === this.allValue) {
        if (this.selected.includes(this.allValue)) {
          this.selected = []
        } else {
          this.selected = [this.allValue]
        }
        this.$emit('input', this.selected)
        return
      }
      if (this.selected.includes(this.allValue)) {
        this.selected = this.computedOptions.map((option) => option.id).filter((item) => item !== option && item !== this.allValue)
        this.$emit('input', this.selected)
        return
      }
      if (this.selected.includes(option)) {
        this.selected = this.selected.filter((item) => item !== option)
      } else {
        this.selected.push(option)
      }
      this.$emit('input', this.selected)
    },
    getSelectedLabel(value) {
      return this.computedOptions.find((option) => option.id == value).label
    },
    removeOption(option) {
      if (option === this.allValue) {
        this.selected = []
      } else {
        this.selected = this.selected.filter((item) => item !== option)
      }
      this.$emit('input', this.selected)
    },
    openSelect() {
      this.$refs.overlay.classList.toggle('qt_ms__controller__overlay--show')
      this.$refs.controller.classList.toggle('qt_ms__controller--show')
      this.$refs.panel.classList.toggle('qt_ms__controller__panel--show')
      this.$refs.panel.style.height = '300px'
      this.isOpen = true;
    },
    closeSelect() {
      this.$refs.overlay.classList.remove('qt_ms__controller__overlay--show')
      this.isOpen = false;
      this.$refs.panel.style.height = '0'
      setTimeout(() => {
        this.$refs.panel.classList.remove('qt_ms__controller__panel--show')
        this.$refs.controller.classList.remove('qt_ms__controller--show')
      }, 300)
    },
    panelClick(e) {
      console.log('panel click')
    }
  },
  mounted() {
    if (this.selected && this.selected.includes(this.allValue)) {
      this.selected = [this.allValue]
    }
  },
  computed: {
    computedOptions() {
      return [{ id: this.allValue, label: 'Tất cả' }, ...this.options]
    }
  }
}
</script>

<style lang="scss" scoped>
.qt_ms {
  width: 100%;
  height: 34px;
  border: 1px solid #aaa;
  border-radius: 4px;
  position: relative;
  &--show {
    border-color: #039be5;
    border-radius: 4px 4px 0 0;
  }
  &--disabled {
    background: rgba(0, 0, 0, 0.1);
    position: absolute;
    top: 0;
    left: 0;
    right: 0;
    bottom: 0;
    z-index: 10;
  }
  &__panel {
    position: absolute;
    z-index: 1;
    top: 0;
    bottom: 0;
    width: calc(100% - 24px);
    padding: 5px;
    display: flex;
    overflow-x: auto;
    &::-webkit-scrollbar {
      // width: 5px;
      height: 5px;
      &--disabled {
        overflow: hidden;
        overflow-x: hidden;
      }
    }
    align-content: center;
    &__placeholder {
      color: #c1c1c1;
    }
  }
  .app-icon {
    position: absolute;
    right: 5px;
    top: 50%;
    color: #c1c1c1;
    cursor: pointer;
    display: flex;
    justify-content: center;
    align-items: center;
    &:hover {
      color: #000;
    }
    transform: translateY(-50%);
  }
}
.selected_pill {
  display: flex;
  margin-right: 10px;
  align-items: center;
  gap: 2px;
  width: fit-content;
  // padding: 2px;
  span {
    background-color: #e3f2fd;
    color: #039be5;
    padding: 2px 4px;
    height: 100%;
    border-radius: 2px;
    display: flex;
    align-items: center;
    text-wrap: nowrap;
  }
  &--disabled {
    span {
      background: transparent;
      color: #000000;
    }
    border: 1px solid #aaa;
  }
  &__remove {
    background-color: #e3f2fd;
    color: #039be5;
    border-radius: 2px;
    display: flex;
    justify-content: center;
    align-items: center;
    height: 100%;
    padding: 2px;
    svg {
      width: 10px;
      height: 10px;
    }
  }
  svg {
    cursor: pointer;
    &:hover {
      color: red;
    }
  }
}
.qt_ms__controller {
  position: relative;
  height: 100%;
  width: 100%;
  &__overlay {
    opacity: 0;
    visibility: hidden;
    position: fixed;
    top: 0;
    left: 0;
    right: 0;
    bottom: 0;
    z-index: 10;
    transition: all 0.3s;
    &--show {
      opacity: 1;
      visibility: visible;
    }
  }
  .controller__wrapper{
    position: absolute;
    top: calc(100% + 1px);
    right: -1px;
    width: fit-content;
    z-index: 20;
  }
  &__panel {
    border: #AAAAAA 1px solid;
    border-top: none;
    width: 300px;
    position: relative;
    background-color: #fff;
    visibility: hidden;
    opacity: 0;
    height: 0;
    overflow-y: auto;
    transition: height 0.3s;
    &--show {
      visibility: visible;
      opacity: 1;
      height: 300px;
    }
    &__item {
      display: flex;
      align-items: center;
      padding: 5px;
      cursor: pointer;
      user-select: none;
      &:hover {
        background-color: #FFF9EB !important;
      }
      &:not(:last-child) {
        border-bottom: 1px solid #eeeeee;
      }
      &:nth-child(odd) {
        background-color: #F1F8FF;
      }
      label{
        margin: 0;
        cursor: pointer;
      }
      input {
        margin-right: 5px;
      }
    }
  }
}
.icon-resize-left {
  position: absolute;
  bottom: 1px;
  left: 1px;
  cursor: ew-resize;
  width: 20px;
  height: 20px;
  transform: scaleX(-1);
  z-index: 1000;
  &:hover {
    color: #039be5;
  }
  // background-color: #fff;
  display: flex;
  justify-content: center;
  align-items: center;
}
.icon-resize-right {
  position: sticky;
  bottom: 0;
  right: 0;
  cursor: ew-resize;
  width: 20px;
  height: 20px;
  z-index: 1000;
  &:hover {
    color: #039be5;
  }
  background-color: #fff;
  display: flex;
  justify-content: center;
  align-items: center;
}
</style>
