<template>
  <ejs-dialog
    :enableResize='true'
    :allowDragging='true'
    :visible='false'
    :animationSettings='$data.animationSettings'
    ref="dlgSapXep"
    :header='"Sắp xếp"'
    showCloseIcon=true
    width='500'
    :target='$data.target'
    :close="onDialogClose"
    :open="onDialogOpen"
    >
    <div class="modal-content popup-box">
      <div class="list-actions-top">
          <ul class="list">
              <li>
                  <a href="#">
                      <span class="icon ui-1_check-circle-08 nc-icon-glyph"></span> Sắp xếp
                  </a>
              </li>
              <li>
                  <div class="sort" style="border-left: 1px solid #BAE7FF;padding-left: 10px;">
                      <a href="#" class="btn btn-main">
                          <span class="-ap icon-chevron-thin-up"></span>
                      </a>
                      <a href="#" class="btn btn-main">
                          <span class="-ap icon-chevron-thin-down"></span>
                      </a>

                  </div>
              </li>
              <li>
                  <div class="select-custom" style="min-width: 200px;">
                    <ejs-dropdownlist :filtering='onFilteringTruong' :allowFiltering="true" :dataSource='p_truong_options' :fields="fieldsTruong" v-model = "p_truong"></ejs-dropdownlist>
                  </div>
              </li>

          </ul>
      </div>
      <div class="popup-body">
          <div class="box-form">
              <div class="table-content">
                <DataGrid
                  v-bind:columns="[
                    {fieldName: 'Ten', headerText: 'Tên', allowFiltering: true},
                    {fieldName: '', headerText: 'Cách sắp xếp', allowFiltering: true},
                    ]"
                  v-bind:dataSource="results"
                  :showFilter="true"
                  :allowPaging="false"
                  :allowExcelExport=true
                  ref="dsSapXep"
                  >
                </DataGrid>
              </div>
          </div>
      </div>
  </div>
  </ejs-dialog>
</template>

<script>
  import Vue from 'vue'
  import GachNoAPI from '../../api/GachNoAPI'
  import { DialogPlugin } from '@syncfusion/ej2-vue-popups'
  import { Query } from '@syncfusion/ej2-data'

  Vue.use(DialogPlugin)

  export default Vue.extend({
    components : {
      //appRemotePagination : RemotePagination,
    },
    props: {
      check: {
        type: Boolean
      }
    },

    computed: {
    },
    data () {
      return {
        target: '.main-wrapper',
        animationSettings: { effect: 'Zoom' },
        fieldsTruong: {text: 'Ten', value: 'Ma' },
        p_truong: null,
        p_truong_options: [
          { Ma: 0, Ten : 'Lần tạo', SAPXEP_ID: 1},
          { Ma: 1, Ten : 'Kì hóa đơn', SAPXEP_ID: 1},
          { Ma: 2, Ten : 'Mã thanh toán', SAPXEP_ID: 1},
          { Ma: 3, Ten : 'Tên thanh toán', SAPXEP_ID: 1},
          { Ma: 4, Ten : 'Địa chỉ thanh toán', SAPXEP_ID: 1},
          { Ma: 5, Ten : 'Địa chỉ CT', SAPXEP_ID: 1},
          { Ma: 6, Ten : 'Số điện thoại', SAPXEP_ID: 1},
          { Ma: 7, Ten : 'Fax', SAPXEP_ID: 1},
          { Ma: 8, Ten : 'Đơn vị quản lý', SAPXEP_ID: 1},
          { Ma: 9, Ten : 'Nhân viên quản lý', SAPXEP_ID: 1},
          { Ma: 10, Ten : 'Tuyến thu', SAPXEP_ID: 1},
          { Ma: 11, Ten : 'Nhân viên tuyến thu', SAPXEP_ID: 1},
          { Ma: 12, Ten : 'Loại khách hàng', SAPXEP_ID: 1},
          { Ma: 13, Ten : 'Hình thức thanh toán', SAPXEP_ID: 1},
          { Ma: 14, Ten : 'Tên Công ty TTKD', SAPXEP_ID: 1},
          { Ma: 15, Ten : 'Địa chỉ công ty TTKD', SAPXEP_ID: 1},
          { Ma: 16, Ten : 'Người nhận', SAPXEP_ID: 1},
          { Ma: 17, Ten : 'Tên đơn vị', SAPXEP_ID: 1},
          { Ma: 18, Ten : 'Số DT người nhận', SAPXEP_ID: 1},
          { Ma: 19, Ten : 'Fax người nhận', SAPXEP_ID: 1},
          { Ma: 20, Ten : 'Email người nhận', SAPXEP_ID: 1},
          { Ma: 21, Ten : 'Chức danh thư kí xác nhận bên TTKD', SAPXEP_ID: 1},
          { Ma: 22, Ten : 'Người kí bên TTKD', SAPXEP_ID: 1},
          { Ma: 23, Ten : 'Nhóm công nợ', SAPXEP_ID: 1},
          { Ma: 24, Ten : 'Thứ tự in', SAPXEP_ID: 1},
        ],
        p_load: true,
        results: []
      }
    },
    methods : {
      loadDataSource(){
        if(this.check)
        {
          let res = this.p_truong_options.filter(x => (x.Ma === 8 || x.Ma === 12 || x.Ma === 9 || x.Ma === 10 || x.Ma === 24 || x.Ma === 2))
          //console.log(res)
          this.results = res

        }
      },
      async openDialog () {

        this.$refs.dlgSapXep.show()
      },
      closeDialog() {
        this.$refs.dlgSapXep.hide()
      },
      onDialogClose () {
      },
      onDialogOpen ()
      {
        this.p_load = true
        this.loadDataSource()
      },
      onFilteringTruong(e){
        var query = new Query();
        query = (e.text !== '') ? query.where('Ten', 'contains', e.text, true) : query;
        e.updateData(this.params.p_truong_options, query);
      },
    },
    watch: {
    }
  })
</script>

