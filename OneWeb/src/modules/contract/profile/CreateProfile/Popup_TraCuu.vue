<template>
    <div>
        <!-- <button @click="openHDDT" class="btn btn-success m-5">Hóa đơn điện tử</button> -->
        
  <b-modal
    id="TraCuu"
    ref="popup_TraCuu"
    modal-class="popup-box"
    header-class="popup-header"
    body-class="none-padding"
    size="xl"
    hide-footer
  >
    <template #modal-header="{ close }">
      <div class="title">
        <span class="icon nc-icon-outline ui-1_settings"></span> Tra cứu
      </div>
      <div
        @click="close"
        class="close -ap icon-close"
        data-dismiss="modal"
      ></div>
    </template>


                <div class="popup-body">
                    <div class="box-form">
                        <div class="table-content">
                            <DataGridEdit
                              ref="grid_danhsach"
                              v-bind:columns="this.grid_grcTT"
                              v-bind:dataSource="this.grcTT"
                              :showFilter="true"
                              :enabledSelectFirstRow="false"
                              :editSettings="{ allowEditing: false,allowAdding: false,allowDeleting: false, mode: 'Normal' }"
                              :showColumnPointer="false"
                              :enable-paging-server="false"
                              :allowPaging="true"
                              :showColumnCheckbox="false"    
                              @recordDoubleClick="recordDoubleClick"                         
                            >
                            </DataGridEdit>
                    </div>
                    </div>
                </div>       


  </b-modal>


    </div>
</template>
<script>
import api from "./api";
import breadcrumb from '@/components/breadcrumb'
import DataGridEdit from './components/DataGrid'
import {
  Aggregate,
  CommandColumn,
  Edit,
  Filter,
  Freeze,
  Group,
  Page,
  Resize,
  Sort,
  Toolbar
} from '@syncfusion/ej2-vue-grids'

export default {


props: {

    grcTT: {
        type: Array  ,
        default: []
    }
},

  provide: {
    grid: [
      Page,
      Sort,
      Filter,
      Resize,
      Group,
      Freeze,
      CommandColumn,
      Edit,
      Aggregate,
      Toolbar
    ]
  },
components: { breadcrumb, DataGridEdit },

  data() {
    return {
      ma_gd:"",
    grid_grcTT: [
        {
          textAlign:"center",
          fieldName: 'ma_gd',
          headerText: 'Mã GD',
          allowFiltering: true,
          allowSorting: false
        },
        {
          textAlign:"left",
          fieldName: 'ngay_yc',
          headerText: 'Ngày yêu cầu',
          allowFiltering: true,
          allowSorting: false
        },

        {
        textAlign:"center",
        fieldName:"ten_loaihd",
        headerText:"Loại HĐ",
        allowSorting:"true",
        allowFiltering:"true"
        },
        {
        textAlign:"center",
        fieldName:"ten_dv",
        headerText:"Đơn vị lập HĐ/PL",
        allowSorting:"true",
        allowFiltering:"true",
        }
        ],
    };
  },
  created() {
  
  },

  watch: {

  },

  methods: {

recordDoubleClick(subject)
{
  this.ma_gd = subject.rowData.ma_gd
  this.$emit('chapnhan',subject.rowData)
  this.hideModal()
},

showModal() {
  this.$bvModal.show("TraCuu")
},
hideModal() {
    this.$bvModal.hide("TraCuu")
},



  },
};
</script>
<style>
/* @import "https://cdn.syncfusion.com/ej2/material.css"; */
.none-padding {
  padding: 0 !important;
}
.modal-dialog {
  border-radius: 0px !important;
  width: 100% !important;
  max-width: 1220px !important;
}

li.non-active a {
  color: #d3d3d3 !important;
}

/* .e-row:active, e-row:focus{background: #8d8b8b !important} */




.e-grid td.e-active{ 
  background-color: #8d8b8b !important}


#custom123 .select2{
width: 253px !important;
}    

</style>