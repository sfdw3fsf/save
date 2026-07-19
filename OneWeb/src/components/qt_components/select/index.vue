<template>
  <div class="select-wrapper">
  <div ref="select" 
    class="w-100 select" 
    :class="{ 'select--show': isFocused, 'select--hide': !isFocused }"
    :disabled="disabled" 
    @click="isFocused = !isFocused"
    >
    <div class="select__value">
      <span v-if="!checkbox">{{ selectedOption ? selectedOption.text : 'Chọn' }}</span>
      <span v-if="checkbox">{{ displayMultipleSelected }}</span>
      </div>
    <Icon icon="iconamoon:arrow-up-2" class="select__arrow" :class="{ 'rotate-arrow': isFocused }" width="20"
      height="20" />
    </div>
    <div 
      class="select__panel" 
      :class="{ 'select__panel--show': isFocused, 'select__panel--hide': !isFocused }"
      ref="selectPanel"
      >
      <input type="text" v-model="search" placeholder="Tìm kiếm" />
      <div v-if="filteredOptions.length == 0">
        <em>Không có lựa chọn</em>
      </div>
      <div class="select__options">
          <div v-if="!checkbox" class="select__option select__option--single" 
          v-for="(option, index) in filteredOptions" :key="index" @click="selectSingle(option)">
          <span>
            <label> {{ option.text }}</label>
          </span></div>
          <div v-if="checkbox" class="select__option select__option--multiple"
          v-for="(option, index) in filteredOptions" :key="index"  >
            <div class="select__option__checkbox">
              <input @click="selectMultiple(option)" type="checkbox" :id="option.value" v-model="option.value" />
              <!-- <label :for="option.value">{{ option.text }}</label> -->
            </div>
            <span><label  :for="option.value">{{ option.text }}</label></span>
          </div>
      </div>
    </div>
</div>
</template>

<script>
import { Icon } from '@iconify/vue2'
export default {
  components: {
    Icon
  },
  emits: ['focus'],
  props: {
    allowWrap: {
      type: Boolean,
      default: false
    },
    options: {
      type: Array,
      required: true,
      default: () => []
    },
    value: {
      type: String,
      default: ''
    },
    checkbox: {
      type: Boolean,
      default: false
    },
    disabled: {
      type: Boolean,
      default: false
    }
  },
  data() {
    return {
      search: '',
      isFocused: false,
      selectedOptions: [],
      selectedOption: null,
      selectedValues: [],
      displayMultipleSelected: ''
    }
  },
  mounted() {
    document.addEventListener('click', this.handleClickOutside)
    this.options = this.options.map(option => {
      return {
        ...option,
        selected: false
      }
    })
  },

  beforeUnmount() {
    document.removeEventListener('click', this.handleClickOutside)
  },

  methods: {
    handleClickOutside(event) {
      const selectElement = this.$refs.select
      const selectPanelElement = this.$refs.selectPanel
      if(!selectElement || !selectPanelElement) return
      if (!selectElement.contains(event.target) && !selectPanelElement.contains(event.target)) {
        this.isFocused = false
      }
    },
    selectSingle(option){
      this.selectedOption = option;
      this.isFocused = false;
    },
    selectMultiple(option){
      console.log('check',option);
      if(this.selectedValues.includes(option.value)){
        this.selectedValues = this.selectedValues.filter(item => item != option.value);
      }
      else{
        this.selectedValues.push(option.value);
      }
      //else{
      //   this.selectedOptions = this.selectedOptions.filter(item => item.value != option.value);
      // }
      // console.log(this.selectedOptions);
  },
  },
  computed: {
    filteredOptions() {
      return this.options.filter(option => option.text.toLowerCase().includes(this.search.toLowerCase()))
    },
  },
  watch: {
    selectedValues: {
      handler: function (val, oldVal) {
        this.$emit('input', val );
        // this.displayMultipleSelected = val.map(item => item.text).join(', ');
        console.log(val);
      },
      deep: true
    },
  }
}
</script>

<style scoped lang="scss">
.select {
  border: 1px solid #ced4da;
  border-radius: 4px;
  padding: 4px 10px;
  appearance: none; /* This hides the default select arrow in some browsers */
  -webkit-appearance: none; /* This hides the default select arrow in some browsers */
  -moz-appearance: none;
  // max-height: 30px;
  box-sizing: border-box;
  min-height: 30px;
  position: relative;
  &__arrow {
    position: absolute;
    right: 10px;
    top: 50%;
    transform: translateY(-50%) rotate(90deg);
    transition: transform 0.3s ease;
    &.rotate-arrow {
      transform: translateY(-50%) rotate(180deg);
    }
  }

  &--show {
    outline: none;
    border-color: #80bdff;
    box-shadow: 0 0 0 0.2rem rgba(0, 123, 255, 0.25);
    // border-radius: 4px 4px 0 0;
  }

  &__panel {
    position: absolute;
    top: calc(100% + 1px);
    left: -1px;
    right: -1px;
    z-index: 100;
    background-color: #fff;
    border: 1px solid #ced4da;
    border-top: none;
    border-radius: 0 0 4px 4px;
    transition: height 0.2s ease;

    &--show {
      display: block;
      max-height: 240px;
      overflow-y: auto;
      overflow-x: hidden;
      em{
        color: #8D8D8D;
        position: relative;
        left: 2.5%;
        margin: 10px 0;
        display: block;
      }
    }
    &--hide {
      display: none;
    }

    input {
      width: 95%;
      border: 1px solid #ced4da;
      padding: 5px 10px;
      position: relative;
      left: 2.5%;
      top: 5px;
      box-sizing: border-box;
      margin-bottom: 5px;
      &:focus {
        outline: none;
        border-color: #b4b4b4;
        box-shadow: 0 0 0 0.1rem rgba(0, 0, 0, 0.15);
      }
    }
  }
}
.select-wrapper {
  position: relative;
  display: inline-block;
  width: 100%;
}

.select__option{
  padding: 0px 2.5%;
  cursor: pointer;
  display: flex;
  align-items: center;
  &__checkbox{
    width: 16px;
    margin-right: 10px;

  }
  span{
    display: inline-block;
    position: relative;
    
    flex: 1;
    padding-left: 10px;
    label{
      margin: 0;
      cursor: pointer;
      width: 100%;
      display: inline-block;
      padding: 7px 0;
    }
    
  }
  span::before{
    content: '';
    position: absolute;
    left: 0;
    top:0;
    border-left: 1px solid #DFE7EB;
    height: 100%;

  }
  &:nth-child(even) {
    background-color: #F1F8FF;
  }
  &:not(:last-child) {
    border-bottom: 1px solid #DFE7EB;
  }
  input[type="checkbox"] {
    margin-right: 10px;
    height: 16px;
    width: 16px;
  }
  &:hover {
    background-color: #FFF9EB;
  }
}
</style>
