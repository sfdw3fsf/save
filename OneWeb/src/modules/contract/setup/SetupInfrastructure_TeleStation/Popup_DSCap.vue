<template>
    <div>
        <!-- <button @click="openHDDT" class="btn btn-success m-5">Hóa đơn điện tử</button> -->
        
  <b-modal
    id="DSCap"
    ref="popup_DSCap"
    modal-class="popup-box"
    header-class="popup-header"
    body-class="none-padding"
    size="xl"
    hide-footer
  >
    <template #modal-header="{ close }">
      <div class="title">
        <span class="icon nc-icon-outline ui-1_settings"></span> Danh sách cáp
      </div>
      <div
        @click="close"
        class="close -ap icon-close"
        data-dismiss="modal"
      ></div>
    </template>

    <div class="list-actions-top">
      <ul class="list">
        <li
          @click="clickButton('tracuu')" :style="this.button.tracuu ? '' : 'pointer-events: none'"
          :class="this.button.tracuu ? 'active' : 'non-active'"
        >
          <a> <span class="icon one-file-search"></span>Tra Cứu </a>
        </li>
      </ul>
    </div>

                <div class="popup-body">
                    <div class="box-form">
                        <div class="title fw6 upper">CHỌN CÁP</div>
                    </div>
                    <div class="box-form">
                        <div class="legend-title">Tra cứu theo tuyến tổ quản lý</div>
                        <div class="row">
                            <div class="col-sm-6 col-12">
                                <div class="info-row">
                                    <div class="key w80">TTVT</div>
                                    <div class="value">
                                        <div class="select-custom">
                                          <SelectExt v-model="ddlTTVT" :options="this.options.ttvt"> 
                                            <option disabled value="0">Select one</option> 
                                          </SelectExt> 
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="col-sm-6 col-12">
                                <div class="info-row">
                                    <div class="key w80">Tổ kỹ thuật</div>
                                    <div class="value">
                                        <div class="select-custom">
                                          <SelectExt v-model="ddlToKyThuat" :options="this.options.tokythuat"> 
                                            <option disabled value="0">Select one</option> 
                                          </SelectExt> 
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-sm-6 col-12">
                                <div class="info-row">
                                    <div class="key w80">Tổng đài</div>
                                    <div class="value select-custom">

                                          <SelectExt v-model="ddlTongDai" :options="this.options.tongdai"> 
                                            <option disabled value="0">Select one</option> 
                                          </SelectExt> 
    
                                    </div>
                                </div>
                            </div>
                            <div class="col-sm-6 col-12">
                                <div class="info-row">
                                    <div class="key w80">Nhóm cáp</div>
                                    <div class="value">
                                        <div class="select-custom">
                                          <SelectExt v-model="ddlNhomCap" :options="this.options.nhomcap"> 
                                            <option disabled value="0">Select one</option> 
                                          </SelectExt> 
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="info-row">
                            <div class="key w80">Cáp gốc</div>
                            <div class="value">
                                <div class="select-custom">
                                          <SelectExt v-model="ddlCapGoc" :options="this.options.capgoc"> 
                                            <option disabled value="0">Select one</option> 
                                          </SelectExt> 
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="box-form">
                        <div class="legend-title">Danh sách cáp</div>
                        <div class="table-content">

                            <DataGridEdit
                              ref="grid_danhsach"
                              v-bind:columns="this.headers"
                              v-bind:dataSource="this.grid_danhsach"
                              :showFilter="true"
                              :enabledSelectFirstRow="false"
                              :editSettings="{ allowEditing: false,allowAdding: false,allowDeleting: false, mode: 'Normal' }"
                              :showColumnPointer="false"
                              :enable-paging-server="false"
                              :allowPaging="true"
                              :showColumnCheckbox="true"
                            >
                            </DataGridEdit>

                        
                        </div>

                    </div>
                    <div class="group-buttons -button tright">
                        <button class="btn btn-second" @click.prevent="Chon" :disabled="disabled_chon">
                            <span class="fa fa-check"></span> Chọn
                        </button>
                        <button class="btn btn-second" @click.prevent="Huy">
                            <span class="nc-icon-glyph ui-1_circle-remove"></span> Hủy bỏ
                        </button>
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
import moment from "moment";
import Vue from 'vue'

export default {
name:'popup_DSCap',

props: {

        multiSelect: {
        type: Boolean  ,
        default: false
        
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
    disabled_chon: true,
      SelectedItems:[],
    txtTimKiem:"",
    grid_danhsach:[],
    ddlTTVT:"",
    ddlTongDai:"",
    ddlToKyThuat:"",
    ddlNhomCap:"",
    ddlCapGoc:"",
    options:{
      ttvt:[],
      tokythuat:[],
      tongdai:[],
      nhomcap:[],
      capgoc:[]
    },

    button:
    {
      tracuu: true
    },
    headers: [
        {
          textAlign:"left",
          fieldName: 'kyhieu',
          headerText: 'Ký hiệu',
          allowFiltering: true,
          allowSorting: false
        },
        {
          textAlign:"left",
          fieldName: 'ten_cap',
          headerText: 'Tên cáp',
          allowFiltering: true,
          allowSorting: false
        },
        {
          textAlign:"left",
          fieldName: 'tenchuquan',
          headerText: 'Chủ quản',
          allowFiltering: true,
          allowSorting: false
        },
        ],
    };
  },
  created() {
    this.NAP_DS_TTVT()
    this.options.nhomcap.push({
      "id": 0, "text":"Cáp đồng"
    })
    this.options.nhomcap.push({
      "id": 1, "text":"Cáp quang"
    })
    this.ddlNhomCap = 0
  },

  watch: {
    ddlNhomCap(newval)
    {
      this.ddlNhomCap = newval
      this.NAP_DS_CAP_GOC()
    },
    ddlTTVT(newval)
    {
      this.ddlTTVT = newval
      this.NAP_DS_TOKT()
    },
    ddlTongDai(newval)
    {
      this.ddlTongDai = newval
      this.NAP_DS_CAP_GOC()
    },
    ddlToKyThuat(newval)
    {
      this.ddlToKyThuat = newval
      this.NAP_DS_TRAM()
    },
    ddlCapGoc(newval)
    {
      this.ddlCapGoc = newval
      this.HT_Grid()
    },

  },

  methods: {


TraCuu()
{
  this.HT_Grid()
},
Chon()
{
  this.SelectedItems = []
  this.SelectedItems = this.$refs.grid_danhsach.getSelectedRecords()
  this.SelectedItems[0].vchapnhan=  true
  if (this.SelectedItems.length == 0)
  {
      this.$toast.error("Chưa chọn đối tượng !");
      return;
  }

  this.$emit('chapnhan',this.SelectedItems)
  this.hideModal()

},
Huy()
{
  this.hideModal()
},


async NAP_DS_TTVT()
{
    const res = await api.lay_ds_ttvt_theo_nv(this.axios, {
      "nhanvien_id":this.$root.token.getNhanVienID()
    })


    let items = []
    res.data.data.forEach(function (item) {
      items.push({ id: item.donvi_id, text: item.ten_dv })
    })
    this.options.ttvt = items
    this.ddlTTVT = items[0].id
    


    this.options.tokythuat = []
    this.ddlToKyThuat = ""

    this.options.tongdai = []
    this.ddlTongDai = ""

    this.options.capgoc = []
    this.ddlCapGoc = ""

    this.grid_danhsach = []

},
async NAP_DS_TOKT()
{

    const res = await api.sp_ds_tokt(this.axios, {
      "vdonvi_id": this.ddlTTVT,
      "vnhanvien_id": this.$root.token.getNhanVienID()
    })
    let items = [];
    res.data.data.forEach(function (item) {
      items.push({ id: item.donvi_id, text: item.ten_dv });
    });
    this.options.tokythuat = items
    this.ddlToKyThuat = items[0].id;   
    this.options.tongdai = []
    this.ddlTongDai = ""

    this.options.capgoc = []
    this.ddlCapGoc = ""

    this.grid_danhsach = []    

},

 async NAP_DS_TRAM()
 {
      const res = await api.sp_ds_tram(this.axios, {
        "vdonvi_id": this.ddlToKyThuat
      })
      
      let items = [];
      res.data.data.forEach(function (item) {
        items.push({ id: item.donvi_id, text: item.ten_dv });
      });
      this.options.tongdai = items;
      this.ddlTongDai = items[0].id;    
      this.options.capgoc = []
      this.ddlCapGoc = ""

      this.grid_danhsach = []  
 },
async NAP_DS_CAP_GOC()
{


    const res = await api.sp_ds_cap_goc(this.axios, {
      "vdonvi_id": this.ddlTongDai,
      "vnhomlc_id": this.ddlNhomCap
    })
    let items = [];
    res.data.data.forEach(function (item) {
      items.push({ id: item.cap_id, text: item.kyhieu });
    });
    this.options.capgoc = items;
    this.ddlCapGoc = items[0].id;    


    this.grid_danhsach = []    
},


async HT_Grid()
{
   let multi = 0
   if(this.multiSelect == true)
   {
     multi = 1
   }

    console.log( "vnhomlc_id", this.ddlNhomCap,
      "vcapgoc_id", this.ddlCapGoc,
      "multiselect", multi)

    const res = await api.sp_ds_cap_chuquan(this.axios, {
      "vnhomlc_id": this.ddlNhomCap,
      "vcapgoc_id": this.ddlCapGoc,
      "multiselect": multi
    })
    this.grid_danhsach = res.data.data ? res.data.data : []  
    console.log('this.grid_danhsach',this.grid_danhsach)
    if(this.grid_danhsach.length > 0)
    {
      this.disabled_chon = false
    }
    else
    {
      this.disabled_chon = true
    }
},






async Lay_TT_ND()
{
    const res = await api.Lay_TT_ND(this.axios, {
    })

    console.log('Lay_TT_ND',res.data.data)
    return res.data.data ? res.data.data : []
 
},


    clickButton(key) {
    if (key == "tracuu") {
        this.TimKiem()
    }
    },


showModal() {
  this.$bvModal.show("DSCap")
},
hideModal() {
    this.$bvModal.hide("DSCap")
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

</style>