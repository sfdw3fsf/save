<template>
  <div class="list_chart">
    <div class="list_chart__mark"
    :class="isShow ? 'list_chart__mark--show' : ''"
    ></div>
    <div class="list_chart__item" v-for="(item, index) in dataS" :key="index" :style="{ backgroundImage: `linear-gradient(to right, ${subColors[index]} 0%, white 50%, white 100%)` }">
      <div class="list_chart__item__color" :style="{ backgroundColor: mainColors[index] }"></div>
      <div class="list_chart__item__content">
        <div class="list_chart__item__content__value">
          {{ item.value }}
        </div>
        <div class="list_chart__item__content__label">
          {{ item.label }}
        </div>
      </div>
    </div>
  </div>
</template>

<script>
const mainColors = ['#488AFA', '#EF9A1D', '#EF4838', '#FCED4A', '#51AF4E', '#9C9C9C', '#9A2DAC', '#14A6D1']
const subColors = ['#A6C4FF', '#FCD7A0', '#F9B3AB', '#FFF6A6', '#B7E3B9', '#E0E0E0', '#D5A5DE', '#5db2cc']
export default {
  props: {
    dataSource: {
      type: Array,
      default: () => []
    }
  },
  data() {
    return {
      mainColors: mainColors,
      subColors: subColors,
      dataS: [],
      isShow: false
    }
  },
  mounted() {
    this.dataS = this.dataSource
    setTimeout(() => {
      this.isShow = true
    }, 100)
  },
  methods: {
    show() {
      this.isShow = true
      //animation ramdom number of item
      const animation = setInterval(() => {
        this.dataS = this.dataSource.map(item => {
          return {
            ...item,
            value: Math.floor(Math.random() * 100)
          }
        });
      }, 30)
      setTimeout(() => {
        clearInterval(animation)
        this.dataS = this.dataSource
      }, 500)
    },
    hide() {
      this.isShow = false
    }
  },
  watch: {
    dataSource: {
      handler: function (val) {
        const interval = setInterval(() => {
          this.dataS = this.dataSource.map(item => {
            return {
              ...item,
              value: Math.floor(Math.random() * 1000)
            }
          });
        }, 30)
        setTimeout(() => {
          clearInterval(interval)
          this.dataS = val
        }, 500)
      },
      deep: true
    }
  }
}
</script>

<style lang="scss" scoped>
.list_chart {
  // width: 350px;
  // max-width: 30%;
  height: fit-content;
  display: flex;
  flex-wrap: wrap;
  flex-direction: column;
  gap: 10px;
  row-gap: 10px;
  align-content: center;
  position: relative;
  padding: 10px 0;
  &__mark {
    position: absolute;
    bottom: 0;
    left: 0;
    width: 100%;
    height: 100%;
    background-color: #fff;
    transition: height 0.5s;
    &--show{
      height: 0;
    }
  }
  &__item {
    display: flex;
    height: 44px;
    width: calc(100% - 10px);
    cursor: pointer;
    user-select: none;
    box-shadow: rgba(0, 0, 0, 0.12) 0px 1px 3px, rgba(0, 0, 0, 0.24) 0px 1px 2px;
    &__color {
      width: 10px;
      height: 100%;
    }
    &__content {
      padding-left: 10px;
      font-weight: 600;
      &__value {
        font-weight: 600;
        font-size: 16px;
      }
    }
  }
}
</style>
