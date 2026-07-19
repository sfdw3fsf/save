<template>
  <div class="box-col box-form" id="boxLeft" style="height:100%">
    <div class="legend-title">Thông tin đối tượng hồ sơ</div>
    <div class="input-more-button -right">
      <button class="btn">
        <span class="one-sort"></span>
      </button>
      <div class="input-icon-left">
        <input type="text" class="form-control" />
        <span class="icon nc-icon-outline ui-2_filter"></span>
      </div>
    </div>
    <div class="tree-plus mart10">
      <ul class="">
        <li class="haschild " v-for="(item,index) of data" :key="index"  @click="handleShowChild(index)" :class="isShowChild && parentIndex ==index  ?'open':''">
          <a
            ><span
              class="text-main icon nc-icon-glyph files_single-content-02"

            ></span>
            {{item.name}}</a
          >
           <!-- <a href="#"><span class="text-main icon nc-icon-glyph files_single-content-02"></span> Root</a> -->
          <ul>
            <li v-for="(childItem ,indexChild) of item.child" :key="indexChild" v-show="isShowChild && parentIndex ==index">
              <a href="#"
                ><span
                  class="text-main icon nc-icon-glyph files_single-content-02"
                ></span>
                {{childItem.name}}</a
              >
            </li>
            <!-- <li>
              <a href="#"
                ><span
                  class="text-main icon nc-icon-glyph files_single-content-02"
                ></span>
                Kho hồ sơ TTVT9...</a
              >
            </li> -->
          </ul>
        </li>
   <li class="haschild">
          <a href="#"
            >
            <span
              class="text-main icon nc-icon-glyph files_single-content-02"
            ></span>
            Root</a
          >
        </li>
        <!-- <li>
          <a href="#"
            ><span
              class="text-main icon nc-icon-glyph files_single-content-02"
            ></span>
            Root</a
          >
        </li>
        <li class="haschild">
          <a href="#"
            ><span
              class="text-main icon nc-icon-glyph files_single-content-02"
            ></span>
            Root</a
          >
        </li>
        <li class="haschild">
          <a href="#"
            ><span
              class="text-main icon nc-icon-glyph files_single-content-02"
            ></span>
            Root</a
          >
        </li>
        <li class="haschild">
          <a href="#"
            ><span
              class="text-main icon nc-icon-glyph files_single-content-02"
            ></span>
            Root</a
          >
        </li> -->
      </ul>
    </div>
  </div>
</template>
<script>
export default {
  data () {
    return {
      data: [
        {
          name: 'Root',
          id: 1,
          child: [{
            name: 'Kho hồ sơ TTVT7...', id: 2
          },
          {
            name: 'Kho hồ sơ TTVT9...', id: 3
          }
          ]
        },
        {
          name: 'Root2',
          id: 5,
          child: [{
            name: 'Kho hồ sơ TTVT73...', id: 6
          },
          {
            name: 'Kho hồ sơ TTVT94...', id: 7
          }
          ]
        }
      ],
      isShowChild: false,
      parentIndex: -1
    }
  },
  methods: {
    handleShowChild (index) {
      this.parentIndex = index
      this.isShowChild = !this.isShowChild
    }
  }

}
</script>
<style scoped>
>>> #boxLeft {
    height: 100% !important;
}
</style>
