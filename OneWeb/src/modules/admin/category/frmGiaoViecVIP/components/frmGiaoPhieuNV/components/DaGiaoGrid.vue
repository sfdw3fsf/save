<template>
  <div class="grid-ext">
    <ejs-grid
      ref='grid'
      :dataSource='dataSource'
      :editSettings='editSettings'
      :allowGrouping='true'
      :groupSettings='groupOptions'
      filterTemplate="<span></span>"
      :filterSettings="{
                showFilterBarOperator: false,
                mode: 'Immediate',
                immediateModeDelay: 500,
              }"
      :allowFiltering="true"
      height='273px'
      clipMode="EllipsisWithTooltip"
      gridLines="Both"
    >
      <e-columns>
        <e-column
          field='stt'
          headerText='Số TT'
          :customAttributes="{class: 'hidden-col'}"
          :allowEditing ='false'
          :isPrimaryKey='true'
          textAlign='center'
          :allowFiltering="true"
                  :filter="{ operator: 'contains' }"
          width="auto"
        ></e-column>
        <e-column
          field='ma_nv'
          headerText='Mã nhân viên'
          :allowEditing ='false'
          textAlign='center'
          :allowFiltering="true"
                  :filter="{ operator: 'contains' }"
          width="auto"
        ></e-column>
        <e-column
          field='ten_nv'
          headerText='Tên nhân viên'
          :allowEditing='false'
          textAlign='center'
          :allowFiltering="true"
                  :filter="{ operator: 'contains' }"
          width="auto"
        ></e-column>
        <e-column
          field='ngaygiao'
          headerText='Ngày giao'
          :allowEditing='false'
          textAlign='center'
          :allowFiltering="true"
                  :filter="{ operator: 'contains' }"
          width="auto"
        ></e-column>
        <e-column
          field='so_dt'
          headerText='Số ĐT'
          :allowEditing ='false'
          textAlign='center'
          :allowFiltering="true"
                  :filter="{ operator: 'contains' }"
          width="auto"
        ></e-column>
        <e-column
          field='nhiemvu'
          headerText='Nhiệm vụ'
          :allowEditing ='false'
          textAlign='center'
          :allowFiltering="true"
                  :filter="{ operator: 'contains' }"
          width=150
        ></e-column>
        <e-column
          field='kqxl'
          headerText='Kết quả XL'
          textAlign='center'
          editType='dropdownedit'
          :allowFiltering="false"
                  :filter="{ operator: 'contains' }"
          :edit='kqxlParams'
          width="auto"
        ></e-column>
        <e-column
          field='trangthai'
          headerText='Trạng thái'
          textAlign='center'
          :allowEditing ='false'
          :allowFiltering="false"
                  :filter="{ operator: 'contains' }"
          width="auto"
        ></e-column>
        <e-column
          field='ghichu'
          headerText='Ghi chú'
          :allowEditing ='false'
          textAlign='center'
          :allowFiltering="true"
                  :filter="{ operator: 'contains' }"
          width=150
        ></e-column>
        <e-column headerText='' :allowEditing ='false' width='150' textAlign='Center' :template='cTemplate'></e-column>
      </e-columns>
    </ejs-grid>
  </div>
</template>
<script>
import Vue from "vue";
var parent = null
import { GridPlugin, Filter, Edit, Group } from "@syncfusion/ej2-vue-grids";
import { DropDownList, MultiSelect } from "@syncfusion/ej2-dropdowns";
import { Query } from '@syncfusion/ej2-data';

let kqxl_arr = [
  {
    KQXL_ID : 1, KQXL: "Tốt",
  },
  {
    KQXL_ID : 3, KQXL: "Không tốt"
  }
];

let countryElem, countryObj, stateObj;
Vue.use(GridPlugin);
export default {
  async created() {
  },
  mounted() {
    parent = this
  },
  data: () => {
    return {
      cTemplate: function () {
          return { template : Vue.component('columnTemplate',{
             template: `<div class="btn btn-danger" @click="repositoryItemButtonEdit2_ButtonClick">X
                </div>`,
                data: function() {
                    return {
                        data: {}
                    }
                },
                methods: {
                  async repositoryItemButtonEdit2_ButtonClick() {
                    try {
                      await this.axios.post("web-thicong/hoinghi_truyenhinh/fn_an_xoa_motdong_giaophieu_nv", {
                        "nhanvien_th_id": this.data.nhanvien_th_id,
                        "nhiemvu_id": this.data.nhiemvu_id,
                        "phieu_id": this.data.phieu_id
                      })
                      parent.$emit('delete_1_row', true);
                    } catch(err) {
                      this.$toast.error("Xóa thất bại")
                    }
                  }
                }
          })}
      },
      groupOptions: { showDropArea: false,columns: ['ten_nv'],captionTemplate:"<div> ${format(data)} </div> ", },
      dataSource: [],
      dsNhiemvu: [],
      editSettings: { allowEditing: true, allowAdding: true, allowDeleting: true,  mode: 'Normal' },
      kqxlParams: {
        create: () => {
          countryElem = document.createElement('input');
          return countryElem;
        },
        read: () => {
          console.log(countryObj)
          return countryObj.text;
        },
        destroy: () => {
          countryObj.destroy();
        },
        write: () => {
          countryObj = new DropDownList({
            dataSource: kqxl_arr,
            
            fields: { value: 'KQXL_ID', text: 'KQXL' },
            placeholder: 'Chọn kqxl',
            floatLabelType: 'Never',
            change: () => {
                console.log(countryObj.index)
              }
          });
          countryObj.appendTo(countryElem);
        }
      }

    };
  },
  provide: {
    grid: [Filter, Edit, Group ]
  }
}

window.format = function(value) { 
  return value.items[0].ma_nv + " - " + value.key + " - " + value.items[0].ten_dv; 
}; 
</script>