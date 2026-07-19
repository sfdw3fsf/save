<template>
    <div>
        <!-- <button @click="openHDDT" class="btn btn-success m-5">Hóa đơn điện tử</button> -->
        
  <b-modal
    id="DSBeCap"
    ref="popup_DSBeCap"
    modal-class="popup-box"
    header-class="popup-header"
    body-class="none-padding"
    size="xl"
    hide-footer
  >
    <template #modal-header="{ close }">
      <div class="title">
        <span class="icon nc-icon-outline ui-1_settings"></span> Danh sách bể cáp
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
                        <div class="title fw6 upper">TRA CỨU THÔNG TIN BỂ CÁP</div>
                    </div>
                    <div class="box-form">
                        <div class="legend-title">Tra cứu theo tuyến tổ quản lý</div>
                        <div class="row">
                            <div class="col-sm-4 col-12">
                                <div class="info-row">
                                    <div class="key w70">TTVT</div>
                                    <div class="value">
                                        <div class="select-custom">
                                          <SelectExt v-model="ddlTTVT" :options="this.options.ttvt"> 
                                            <option disabled value="0">Select one</option> 
                                          </SelectExt> 
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="col-sm-4 col-12">
                                <div class="info-row">
                                    <div class="key w70">Tổ quản lý</div>
                                    <div class="value">
                                        <div class="select-custom">
                                          <SelectExt v-model="ddlToQuanLy" :options="this.options.toquanly"> 
                                            <option disabled value="0">Select one</option> 
                                          </SelectExt> 
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="col-sm-4 col-12">
                                <div class="info-row">
                                    <div class="key w120">
                                        <div class="check-action">
                                            <input type="checkbox" class="check" v-model="chkTuyenCongBe">
                                            <span class="name">Tuyến cống bể</span>
                                        </div>
                                    </div>
                                    <div class="value">
                                        <div class="select-custom" id="custom123">
                                          <SelectExt v-model="ddlTuyenCongBe" :disabled="!chkTuyenCongBe" :options="this.options.tuyencongbe"> 
                                            <option disabled value="0">Select one</option> 
                                          </SelectExt> 
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="box-form">
                        <div class="legend-title">Tìm kiếm</div>
                        <div class="input-more-button">
                            <button class="btn" @click.prevent="this.TimKiem()">
                                <span class="one-search f20"></span>
                            </button>
                            <input type="text" v-model="txtTimKiem" class="form-control" 
                            placeholder="Nhập vào tên, số hiệu hoặc địa chỉ đoạn cống để tìm kiếm"
                            v-on:keyup.enter="onEnter"
                            >
                        </div>
                    </div>
                    <div class="box-form">
                        <div class="legend-title">{{gcDoanCong}}</div>
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
                              @rowSelected="grid_RowSelected"
                              
                            >
                            </DataGridEdit>
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
name:'popup_DSCot',

props: {

    multiSelect: {
        type: Boolean  ,
        default: false
    },
    DialogMode: {
        type: Boolean  ,
        default: false
    },
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
    gcDoanCong:"",
      BECAP_ID:-1,
      _dialogMode:false,

    txtTimKiem:"",
    grid_danhsach:[],
    ddlTTVT:"",
    ddlToQuanLy:"",
    ddlTuyenCongBe:"",
    chkTuyenCongBe:false,
    options:{
      ttvt:[],
      toquanly:[],
      tuyencongbe:[],
    },

    button:
    {
      tracuu: true
    },
    headers: [
        {
          textAlign:"center",
          fieldName: 'STT',
          headerText: 'STT',
          allowFiltering: true,
          allowSorting: false
        },
        {
          textAlign:"left",
          fieldName: 'BECAP_ID',
          headerText: 'ID',
          allowFiltering: true,
          allowSorting: false
        },

        {
        textAlign:"center",
        fieldName:"MA_BC",
        headerText:"Mã bể cáp",
        allowSorting:"true",
        allowFiltering:"true"
        },
        {
        textAlign:"center",
        fieldName:"KYHIEU",
        headerText:"Ký hiệu",
        allowSorting:"true",
        allowFiltering:"true",
    },
        {
        textAlign:"center",
        fieldName:"TEN_BC",
        headerText:"Tên bể cáp",
        allowSorting:"true",
        allowFiltering:"true",
    },
        {
        textAlign:"center",
        fieldName:"TUYEN_CB",
        headerText:"Tuyến cống bể",
        allowSorting:"true",
        allowFiltering:"true",
    },
        {
        textAlign:"center",
        fieldName:"TENVT_DB",
        headerText:"Vị trí đặt bể cáp",
        allowSorting:"true",
        allowFiltering:"true",
    },
        {
        textAlign:"center",
        fieldName:"LOAI_BC",
        headerText:"Loại bể cáp",
        allowSorting:"true",
        allowFiltering:"true",
    },
        {
        textAlign:"center",
        fieldName:"DIACHI",
        headerText:"Địa chỉ",
        allowSorting:"true",
        allowFiltering:"true",
    },
            {
        textAlign:"center",
        fieldName:"TEN_TS",
        headerText:"Tên tài sản",
        allowSorting:"true",
        allowFiltering:"true",
    },
        {
        textAlign:"center",
        fieldName:"TENCHUQUAN",
        headerText:"Chủ quản",
        allowSorting:"true",
        allowFiltering:"true",
    },
        ],
    };
  },
  created() {
    this.NAP_DS_TTVT()
  },

  watch: {
    ddlTTVT(newval)
    {
      this.ddlTTVT = newval
      this.NAP_DS_TOKT()
    },
    ddlToQuanLy(newval)
    {
      this.ddlToQuanLy = newval
      this.NAP_DS_TUYEN_CONGBE()
    } ,
    ddlTuyenCongBe(newval)
    {
      let TUYENCB_ID = ""
      if(this.chkTuyenCongBe == true)
      {
        TUYENCB_ID = this.ddlTuyenCongBe
      }
      this.ddlTuyenCongBe = newval
      this.NAP_DS_BECAP(TUYENCB_ID,this.ddlToQuanLy,null)
    },
    chkTuyenCongBe(newval)
    {
      this.chkTuyenCongBe = newval
      this.NAP_DS_BECAP()
    }
  },

  methods: {

onEnter: function() 
{
  let TUYENCB_ID = ""
  if(this.chkTuyenCongBe == true)
  {
    TUYENCB_ID = this.ddlTuyenCongBe
  }
  if (this.txtTimKiem == null && this.txtTimKiem == "")
      this.NAP_DS_BECAP(TUYENCB_ID, this.ddlToQuanLy, null)
  else
      this.NAP_DS_BECAP(null, null, this.txtTimKiem.trim())
},
TimKiem()
{
  let TUYENCB_ID = ""
  if(this.chkTuyenCongBe == true)
  {
    TUYENCB_ID = this.ddlTuyenCongBe
  }
  if (this.txtTimKiem == null && this.txtTimKiem == "")
      this.NAP_DS_BECAP(TUYENCB_ID, this.ddlToQuanLy, null)
  else
      this.NAP_DS_BECAP(null, null, this.txtTimKiem.trim())
},
Chon()
{
  this.SelectedItems = []
  this.SelectedItems = this.$refs.grid_danhsach.getSelectedRecords()
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


grid_RowSelected(subject)
{
  this.BECAP_ID = subject.data.BECAP_ID
},


async NAP_DS_TTVT()
{
    const res = await api.lay_ds_ttvt_theo_nv(this.axios, {
      "nhanvien_id": this.$root.token.getNhanVienID()
    })


    let items = []
    res.data.data.forEach(function (item) {
      items.push({ id: item.donvi_id, text: item.ten_dv })
    })
    this.options.ttvt = items
    this.ddlTTVT = items[0].id
    
    this.options.toquanly  = []
    this.ddlToQuanLy = ""

    this.options.tuyencongbe = []
    this.ddlTuyenCongBe = ""

    await this.NAP_DS_TOKT()

},


async NAP_DS_TOKT()
{
    const res = await api.sp_lay_ds_tokt_theo_nv(this.axios, {
      "vttvt_id": this.ddlTTVT,
      "vnhanvien_id": this.$root.token.getNhanVienID()
    })
    let items = []
    res.data.data.forEach(function (item) {
      items.push({ id: item.donvi_id, text: item.ten_dv })
    })
    this.options.toquanly = items
    this.ddlToQuanLy = items[0].id

    this.options.tuyencongbe = []
    this.ddlTuyenCongBe = ""

    await this.NAP_DS_TUYEN_CONGBE()
},

async NAP_DS_TUYEN_CONGBE()
{
    const res = await api.lay_ds_tuyen_theo_toql(this.axios, {
      "vtoql_id": this.ddlToQuanLy,
      "loaituyen": 3,//TUYEN_CB
    })
    let items = []
    res.data.data.forEach(function (item) {
      items.push({ id: item.TUYENCB_ID, text: item.TUYEN_CB })
    })
    this.options.tuyencongbe = items
    this.ddlTuyenCongBe = items[0].id


    let TUYENCB_ID = ""
    if(this.chkTuyenCongBe == true)
    {
      TUYENCB_ID = this.ddlTuyenCongBe
    }
    this.NAP_DS_BECAP(TUYENCB_ID,this.ddlToQuanLy,null);


},

async NAP_DS_BECAP()
{
  let TUYENCB_ID = ""
  if(this.chkTuyenCongBe == true)
  {
    TUYENCB_ID = this.ddlTuyenCongBe
  }
  await this.NAP_DS_BECAP(TUYENCB_ID,this.ddlToQuanLy,null)
},


async NAP_DS_BECAP(tuyencbid,toql_id,query)
{
  if (tuyencbid == null && toql_id == null)
  {
    this.gcDoanCong = "Danh sách bể thỏa mãn điều kiện tìm kiếm"
  }
  else
  {
    this.gcDoanCong = "" 
    if(this.chkTuyenCot == true)
    {
      for(var i =0; i<this.options.tuyencongbe.length;i++)
      {
        if(this.options.tuyencongbe[i].id = this.ddlTuyenCongBe)
        {
          this.gcDoanCong = "Danh sách bể thuộc tuyến: " + this.options.tuyencongbe[i].text
        }
      }
    }
    else
    {
      this.gcDoanCong = "Danh sách bể chưa xác định tuyến"
    }
  }

  const res = await api.sp_lay_ds_hatang(this.axios, {
    "vtuyen_id": tuyencbid,
    "vtoql_id": toql_id,
    "vquery": query,
    "vdoituong": "BECAP"
  })
  this.grid_danhsach = res.data.data ? res.data.data : []
  this.txtTimKiem = ""

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
    return res.data.data ? res.data.data : []
 
},


    clickButton(key) {
    if (key == "tracuu") {
        this.TimKiem()
    }
    },


showModal() {
  this.$bvModal.show("DSBeCap")
},
hideModal() {
    this.$bvModal.hide("DSBeCap")
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