<template>
  <div class="box-form" style="height: 490px">
    <div class="legend-title">Chi tiết bộ hồ sơ</div>
    <div class="table-content" style="max-height: 300px">
      <table class="table-result table-gachle tree-grid-2">
        <thead class="center">
          <tr>
            <th class="w20"></th>
            <th class="w20"></th>
            <th class="w20">
              <div class="check-action">
                <input type="checkbox" class="check" />
                <span class="name"></span>
              </div>
            </th>
            <th>Số máy/Acc</th>
            <th>Mã GD</th>
            <th>Loại file</th>
            <th>Tên file</th>
            <th>Xem/Tải về</th>
          </tr>
          <tr>
            <th class="w20"></th>
            <th class="w20"></th>
            <th class="w20">
              <div class="check-action">
                <input type="checkbox" disabled="" class="check" />
                <span class="name"></span>
              </div>
            </th>
            <th>
              <div class="input-icon-right">
                <input type="text" class="form-control" />
                <span class="icon nc-icon-outline ui-1_zoom"></span>
              </div>
            </th>
            <th>
              <div class="input-icon-right">
                <input type="text" class="form-control" />
                <span class="icon nc-icon-outline ui-1_zoom"></span>
              </div>
            </th>
            <th>
              <div class="input-icon-right">
                <input type="text" class="form-control" />
                <span class="icon nc-icon-outline ui-1_zoom"></span>
              </div>
            </th>
            <th>
              <div class="input-icon-right">
                <input type="text" class="form-control" />
                <span class="icon nc-icon-outline ui-1_zoom"></span>
              </div>
            </th>
            <th class="w60">
              <div class="input-icon-right">
                <input type="text" class="form-control" />
                <span class="icon nc-icon-outline ui-1_zoom"></span>
              </div>
            </th>
          </tr>
        </thead>
        <tbody v-for="(item, index) of dataTable" :key="index" >
          <tr class="highlight4 fw6 tree-open" @click="handleShowChild(index)">
            <td colspan="8">
              <div class="tree-arrow" ></div>
              {{item.name}}
            </td>
          </tr>
          <tr class="highlight" v-for="(itemChild, indexChild) of item.child" :key="indexChild" v-show="isShowChild && parentIndex ==index">
            <td class="tree-first w20 bg-white">
              <div class="tree-line"></div>
              <div class="tree-indent"></div>
            </td>
            <td class="w20 center"  @click="selectItem(itemChild)">
              <span class="fa fa-play text-main"  v-show="itemChild.id == itemSelect.id "></span>
            </td>
            <td class="w20 center">
              <div class="check-action">
                <input type="checkbox" class="check" />
                <span class="name"></span>
              </div>
            </td>
            <td class="selected highlight1"></td>
            <td>{{itemChild.name}}</td>
            <td></td>
            <td></td>
            <td class="w60 center">
              <a href="#" class="btn btn-main pad3">
                <span class="one-search lh14"></span>
              </a>
              <a href="#" class="btn btn-main pad3 w24">
                <span class="fa fa-angle-down lh14"></span>
              </a>
            </td>
          </tr>
        </tbody>
      </table>
    </div>
      <vue-paginate v-model="page" :per_page="10" :total="100"></vue-paginate>
    <!-- <div class="tc-pagination">
      <a href="#" class="btn one-arrow-left-forward"></a>
      <a href="#" class="btn fa fa-angle-double-left"></a>
      <a href="#" class="btn fa fa-angle-left"></a>
      <span class="text">Bản ghi</span>
      <div class="dropdown inline">
        <div class="fw6 input-number" data-toggle="dropdown">
          <span class="text-main">10</span>/1033
        </div>
        <div class="dropdown-menu">
          <a href="#" class="dropdown-item">10</a>
          <a href="#" class="dropdown-item">20</a>
        </div>
      </div>
      <a href="#" class="btn fa fa-angle-right"></a>
      <a href="#" class="btn fa fa-angle-double-right"></a>
      <a href="#" class="btn one-arrow-right-forward"></a>
    </div> -->
  </div>
</template>
<script>
// import VuePaginate from '../form/VuePaginate.vue'
import VuePaginate from './../../../../../components/form/VuePaginate.vue'
export default {
  components: {
    VuePaginate
  },
  data () {
    return {
      page: 1,
      itemSelect: {},
      isShowChild: false,
      parentIndex: -1,
      dataTable: [
        {name: ' Phân loại: cáp đồng',
          id: 1,
          child:
                [
                  {
                    name: 'Số máy/Acc', id: 11
                  },
                  {
                    name: 'Số máy/Acc3', id: 12
                  }
                ]
        },
        {name: ' Phân loại: cáp đồng2',
          id: 2,
          child:
                [
                  {
                    name: 'Số máy/Acc', id: 22
                  }
                ]
        }
      ]
    }
  },
  methods: {
    handleShowChild (index) {
      this.parentIndex = index
      this.isShowChild = !this.isShowChild
    },
    selectItem (item) {
      this.itemSelect = item
    }
  }
}
</script>
