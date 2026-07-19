<template>
  <div class="md-container" v-show="showModal" >
    <div class="md-bg" @click="closeModal"></div>
    <div class="p-content"  @click="closeModal">
    <div class="md-content"  style="overflow-y: auto; overflow-x: hidden;" v-if="showModal" :class="size">
      <div class="md-header">
        <Icon :icon="icon" :width="25" v-if="icon" />
        <div class="header-text">
          <slot name="header" ></slot> 
        </div>
      </div>
      <div class="md-body">
        <slot name="body"></slot>
      </div>
      <div class="md-footer">
        <slot name="footer"></slot>
        </div>
    </div>
    </div>
  </div>
</template>

<script>
import {Icon} from '@iconify/vue2'
export default {
  props: {
    showModal: {
      type: Boolean,
      required: true
    },
    size: {
      type: String,
      default: 'tiny'
    },
    icon: {
      type: String,
    }
  },
  components: {
    Icon
  },
  emits: ['close-modal'],
  methods: {
    cls(){
      this.$emit('close-modal');
    },
    closeModal(event) {
      if (!event.target.closest('.md-content')) {
    // Clicked outside of md-content
    // Handle the click outside md-content here
    this.$emit('close-modal');
  } else {
    // Clicked inside md-content
    // Handle the click inside md-content here
    return
  }
    }
  }
}
</script>

<style lang="scss" scoped>
.slide-fade-enter-active {
  transition: all 0.3s ease-out;
}

.slide-fade-leave-active {
  transition: all 0.8s cubic-bezier(1, 0.5, 0.8, 1);
}

.slide-fade-enter-from,
.slide-fade-leave-to {
  transform: translateY(20px);
  opacity: 0;
}

//variables
.md-container{
  position: relative;
}
.md-bg{
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  opacity: .5;
  background-color: #000;
  z-index: 998;
}
.md-content{
  display: flex;
  flex-direction: column;
  z-index: 999;
  background-color: #fff;
  // padding: 20px;
  border-radius: 5px;

}
.p-content{
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  z-index: 999999;
  background-color: transparent;
  display: flex;
  justify-content: center;
  align-items: center;
}
.md-header{
  color: #0176FF;
  // height: 66px;
    // font-weight: bold;
    margin-left: 10px;
    margin-right: 10px;
    position: relative;
    padding: 15px 10px;
  border-bottom: 1px solid #ccc;
  display: flex;
  .header-text{
    margin-left: 10px;
    font-size: 18px;
    color: #000;
    font-weight: 600;
    
  }
  .btn-x{
    position: absolute;
    right: 0;
    top: 0;
    margin: 10px 10px 0 00;
    cursor: pointer;
    color: #ccc;
  }

  .md-body{
    overflow-y: auto;
  overflow-x: hidden;
  height: 100%;
  }
  .btn-x:hover{
    color: #000;
  }
}

.tiny{
  width: 50%;
  height: fit-content;
  max-height: 80%;
}

.large{
  width: 70%;
  height: 70%;
}
.huge{
  width: 85%;
  height: 85%;
}
.md-footer{
  display: flex;
  justify-content: flex-end;
  padding: 10px;
  border-top: 1px solid #ccc;
}
</style>