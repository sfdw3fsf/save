<template>
  <div class="pagination-container">
    <div class="control">
      <div class="tc-pagination">
      <a href="#" class="btn fa fa-angle-double-left" @click.prevent="$emit('choosePage', 0)"></a>
      <a href="#" class="btn fa fa-angle-left" @click.prevent="$emit('prevPage')"></a>
      <a href="#" v-if="isLeftDots">...</a>
      <a href="#" v-for="(val) in arrPageNumber" :key="val" @click="$emit('choosePage', +val - 1)" :class="(currentPage + 1 == val) ? 'btn active': 'btn'">{{val}}</a>
      <a href="#" v-if="isRightDots">...</a>
      <a href="#" class="btn fa fa-angle-right" @click.prevent="$emit('nextPage')"></a>
      <a href="#" class="btn fa fa-angle-double-right" @click.prevent="$emit('choosePage', Math.ceil(+totalrecord / recordPerPage) - 1)"></a>
      </div>
      <div class="page-size">
        <select class="select__page-size" @change="$emit('changePageSize', $event.target.value)" :value="recordPerPage">
         <option class="option-page-size" v-for="(o, idx) in pageSizeOption" :key="idx" :value="o">{{o}}</option>
       </select>
       <span>items per page</span>
      </div>
    </div>
    <div class="info-page">{{ currentrecord + (totalrecord !== 0? 1 : 0) }} to {{ endRecord }} of {{totalrecord}}</div>
  </div>
</template>
<style scoped>
.pagination-container {
  display: flex;
  align-items: center;
  padding: 10px;
}
.control {
  flex-grow: 1;
  display: flex;
  justify-content: flex-start;
  align-items: center;
}
.info-page {
  align-items: center;
  display: flex;
}
.select__page-size {
  margin-left: 20px;
  border: 1px solid gainsboro;
  border-radius: 3px;
  outline: none;
  min-width: 50px;
  padding: 2px 3px;
  margin-right: 5px;
}
.option-page-size:last-child {
  border-bottom-left-radius: 3px;
  border-bottom-right-radius: 3px;
}
.page-size {
  height: 100%;
  align-items: center;
}
.active {
  text-decoration: underline;
  font-weight: bold;
}
</style>
<script>
export default {
  props: ['totalrecord', 'currentrecord', 'pageSizeOption', 'recordPerPage', 'currentPage'],
  name: 'pagination',
  data () {
    return {
    }
  },
  computed: {
    endRecord () {
      return (this.currentrecord + +this.recordPerPage) < this.totalrecord ? (this.currentrecord + +this.recordPerPage) : this.totalrecord
    },
    arrPageNumber () {
      const numPage = Math.ceil(+this.totalrecord / this.recordPerPage)
      const arr = []
      let begin = 1
      let end = 1
      if (numPage < 5) {
        begin = 1
        end = numPage
      } else {
        if (this.currentPage + 1 + 2 > numPage) {
          begin = numPage - 4
          end = numPage
        } else if (this.currentPage + 1 - 2 <= 0) {
          begin = 1
          end = 5
        } else {
          begin = this.currentPage + 1 - 2
          end = this.currentPage + 1 + 2
        }
      }
      for (let i = begin; i <= end; i++) {
        arr.push(i)
      }
      return arr
    },
    isLeftDots () {
      const numPage = Math.ceil(+this.totalrecord / this.recordPerPage)
      if (numPage < 5) {
        return false
      }
      if (this.currentPage - 2 <= 0) {
        return false
      }
      return true
    },
    isRightDots () {
      const numPage = Math.ceil(+this.totalrecord / this.recordPerPage)
      if (numPage < 5) {
        return false
      }
      if (this.currentPage + 3 >= numPage) {
        return false
      }
      return true
    }
  }
}
</script>
