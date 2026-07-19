<template>
  <b-modal
    id="ServiceOther"
    size="xl"
    no-close-on-backdrop
    hide-footer
    hide-header
    hide-header-close
    @shown="resetModal"
    body-class="modal-body p-0">
      <div class="modal-content popup-box">
        <div class="popup-header">
          <div class="title"><span class="icon one-notepad"></span> Đăng ký dịch vụ khác</div>
          <div class="close -ap icon-close" data-dismiss="modal"  @click="cancelForm">
          </div>
        </div>
        <div class="list-actions-top">
          <ul class="list">
            <li @click="closeForm">
              <a href="javascript:void(0)">
                <span class="icon nc-icon-glyph ui-1_check-circle-08"></span> Chấp nhận
              </a>
            </li>
          </ul>
        </div>
        <div class="popup-body">
          <div class="box-form">
            <div class="table-content">
              <DataGrid ref="gridDsHDTB" v-bind:columns="[
                        {fieldName: 'ma_dvkhac',width: 50, headerText: 'Mã dịch vụ', allowFiltering: true, allowSorting: true},
                        {fieldName: 'ten_dvkhac',width: 100, headerText: 'Tên dịch vụ', allowFiltering: true, allowSorting: false},
                        {fieldName: 'trangthai',width: 30, headerText: 'Trạng thái', allowFiltering: true, allowSorting: false},
                        {fieldName: 'noidung',width: 30, headerText: 'Nội dung', allowFiltering: true, allowSorting: false},
                        // { headerText: 'Nội dung', width:'10%', columns:DKColumns},
                        // {  headerText: 'Nội dung', width:'10%', columns:HUYColumns}
                        { headerText: 'Đăng ký',width: 20, template: columnTemplateChonDangKy,allowFiltering:false},
                        { headerText: 'Huỷ',width: 20, template: columnTemplateChonHuy,allowFiltering:false}
                    ]"
                        v-bind:dataSource="dbDichVu"  :enable-paging-server="false" id="datagrid"
                        :allow-paging="true" :showFilter="true" dataKeyField="dvkhac_id">
              </DataGrid>
            </div>
          </div>
        </div>
      </div>
  </b-modal>
</template>
<style>
    input.disabled, select.disabled, .disabled input, .disabled select, .disabled /deep/ .select2-selection { background-color: #EEE; color:lightgray;}
    .e-grid .e-gridheader .e-sortfilter .e-headercelldiv, .e-grid .e-gridheader .e-stackedheadercelldiv, .e-grid .e-gridheader .e-headercell .e-headercelldiv.e-headerchkcelldiv
    {
    padding: 0 5px 0 5px !important;
    }
</style>
<script>
import DatePicker from 'vue2-datepicker'
import 'vue2-datepicker/locale/vi.js'
import 'vue2-datepicker/index.css'
import {L10n, setCulture} from '@syncfusion/ej2-base';
import {Filter, Grid, GridComponent, GridPlugin, Page, Sort} from "@syncfusion/ej2-vue-grids";
import {integer, maxValue, minLength, required} from 'vuelidate/lib/validators'
import API from './API'

L10n.load({
  "vi-VN": {
    grid: {
      EmptyRecord: "Không có bản ghi nào để hiển thị ",
      Item: "Bản ghi",
      Items: "Bản ghi",
      FilterbarTitle: "",
    },
  },
});

let columnTemplateChonDangKy = function () {
  return {
    template: {
      template: `
                            <div class="check-action" style="position:fixed;">
                                <input style="text-align: center !important;"
                                    type="checkbox"
                                    class="check"
                                    v-model="parent.dangkyds"
                                    :value="data.dvkhac_id"/>
                                <span class="name"></span>
                            </div>
                        `,
      data() {
        return {
          data: {}
        }
      },
      computed: {
        parent() {
          return this.$parent.$parent.$parent.$parent.$parent
        },
      }
    }
  }
};
let columnTemplateChonHuy = function () {
  return {
    template: {
      template: `
                            <div class="check-action">
                                <input
                                    type="checkbox"
                                    class="check"
                                    v-model="parent.huyds"
                                    :value="data.dvkhac_id"/>
                                <span class="name"></span>
                            </div>
                        `,
      data() {
        return {
          data: {}
        }
      },
      computed: {
        parent() {
          return this.$parent.$parent.$parent.$parent.$parent
        },
      }
    }
  }
};
export default {
    name: 'popupServiceOthers',
    components: { API,required, minLength, integer, maxValue,DatePicker, L10n, setCulture ,GridPlugin, Page, Sort, Filter, GridComponent, Grid},
    props: {
      dulieu: {}
    },
    watch: {

    },
    data() {

        return {
           columnTemplateChonDangKy :  function () {
          return {
            template: {
              template: `
                            <div style="display: block;text-align: center; width: 100%" >
                                <input
                                :disabled="disable"
                                    type="checkbox"
                                    class="check"
                                    v-model="parent.dangkyds"
                                    :value="data.dvkhac_id"/>
                                <span class="name"></span>
                            </div>
                        `,
              data() {
                return {
                  data: {}
                }
              },
              computed: {
                parent() {
                  return this.$parent.$parent.$parent.$parent.$parent
                },
                disable() {
                  return this.data.trangthai !== null;
                }
              }
            }
          }
        },
           columnTemplateChonHuy : function () {
          return {
            template: {
              template: `
                            <div style="display: block;text-align: center; width: 100%" >
                                <input
                                    :disabled="disable"
                                    type="checkbox"
                                    class="check"
                                    v-model="parent.huyds"
                                    :value="data.dvkhac_id"/>
                                <span class="name"></span>
                            </div>
                        `,
              data() {
                return {
                  data: {}
                }
              },
              computed: {
                parent() {
                  return this.$parent.$parent.$parent.$parent.$parent
                }, disable() {
                  return this.data.trangthai === null;
                }
              }
            }
          }
        },
            initialSort: {
            columns: [{ field: 'ma_dvkhac', direction: 'Ascending' },
                { field: 'ten_dvkhac', direction: 'Descending' }]
            },

        ds_dichvuKhac: {
          list: [],
          header: [],
          value: {},
          isEnabled: true,
          checked: []
        },
        success_code: "BSS-00000000",
        dichvudangky: [],
        rowchon: [],
        rowchon0: [],
        loai_id: 0,
        loaitb_id: 0,
        thanhtoan_id: 0,
        dangkyds:[],
          huyds:[],
          dbDichVu:[],
          DKColumns:[
            {
              customAttributes:{hidden:true},
              columns:[
                {
                  field: 'dangky',
                  template:columnTemplateChonDangKy(),
                  width: '15%',
                  textAlign: 'Center',
                  minWidth: 10
                }
              ]
            },
          ],HUYColumns:[
            {
              customAttributes:{hidden:true},
              columns:[
                {
                  field: 'dangky',
                  template:columnTemplateChonHuy(),
                  width: '15%',
                  textAlign: 'Center',
                  minWidth: 10
                }
              ]
            },
          ],
          resolvePromise:{},
          rejectPromise : {},
          objectDk : [],
          objectHuy : []
        }
    },
    provide: {
      grid: [Sort, Page, Filter]
    },
    async mounted() {
        // this.loai_id = this.dulieu._loai_id?this.dulieu._loai_id:0;
        // this.loaitb_id = this.dulieu._loaitb_id?this.dulieu._loaitb_id:0;
        // this.thanhtoan_id = this.dulieu._thanhtoan_id?this.dulieu._thanhtoan_id:0;
    },
    computed: {
    },
    methods: {
      show (data) {
        return new Promise((resolve, reject) => {
          console.log("data",data)
          this.dbDichVu = data
          this.dangkyds = []
          this.huyds = []
          if(data.length > 0){
            data.forEach(item =>{
              if(item.dvKhacId){

                if(item.trangthai !== 'Su dung' && item.kieu_yc ===1){
                  item.dvkhac_id = item.dvKhacId
                  delete item.dvKhacId
                  this.dangkyds.push(item.dvkhac_id)
                }

                if(item.kieu_yc ===0){
                  item.dvkhac_id = item.dvKhacId
                  delete item.dvKhacId
                  this.huyds.push(item.dvkhac_id)
                }
              }else if(item.dvkhac_id){
                if(item.trangthai !== 'Su dung'  && item.kieu_yc ===1){
                  this.dangkyds.push(item.dvkhac_id)
                }
                if(item.kieu_yc === 0){
                  this.huyds.push(item.dvkhac_id)
                }
              }
            })
          }
          this.$bvModal.show("ServiceOther")
          this.resolvePromise =  resolve
          this.rejectPromise = reject
        })
      },
      async resetModal() {
        try{
          this.$root.showLoading(true)
          document.getElementById("datagrid").blur()
        }catch(er){}
        finally{
          this.$root.showLoading(false)
        }
      },
      cancelForm(){
        this.$bvModal.hide('ServiceOther');
      },
      closeForm(){
        console.log(this.dangkyds)
        this.objectDk = this.dbDichVu.filter( i => this.dangkyds.includes(i.dvkhac_id))
        this.objectHuy = this.dbDichVu.filter( i => this.huyds.includes(i.dvkhac_id))
        this.resolvePromise({
          dangky : this.objectDk ,
          huy : this.objectHuy})
        this.$bvModal.hide('ServiceOther');
      }
    }
}
</script>
