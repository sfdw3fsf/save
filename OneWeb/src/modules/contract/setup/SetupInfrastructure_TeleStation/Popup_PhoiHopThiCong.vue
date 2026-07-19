<template>
    <div>
        <!-- <button @click="openHDDT" class="btn btn-success m-5">Hóa đơn điện tử</button> -->
        
  <b-modal
    id="PhoiHopThiCong"
    ref="popup_PhoiHopThiCong"
    modal-class="popup-box"
    header-class="popup-header"
    body-class="none-padding"
    size="xl"
    hide-footer
  >
    <template #modal-header="{ close }">
      <div class="title">
        <span class="icon nc-icon-outline ui-1_settings"></span> GIAO PHIẾU PHỐI HỢP
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
          @click="clickButton('naplai')"
          :class="this.button.naplai ? 'active' : 'non-active'"
        >
          <a> <span class="icon one-file-search"></span>Nạp lại </a>
        </li>
      </ul>
    </div>
                <div class="popup-body">
                    <div class="box-form">
                        <div class="title fw6 upper">GIAO PHIẾU</div>
                    </div>
                    <div class="box-form">
                        <div class="row">
                            <div class="col-sm-4 col-12">
                                <div class="info-row">
                                    <div class="key w120">Đơn vị phối hợp</div>
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
                                <button class="btn btn-second" v-if="btnGiaoPhieuPH"  
                                        @click="this.btnGiaoPhieuPH_Click()" >Giao phiếu</button>
                            </div>                            
                        </div>
                    </div>
                    <div class="box-form">
                        <div class="legend-title">Danh sách phiếu đã giao</div>
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
name:'popup_PhoiHopThiCong',
props: {
        phieu_id: {
        type: Number,
        default: 0
        },
        kieu_id: {
        type: String,
        default: '0' // 0 : CSHT, 1 : CNTT
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
      
    grid_danhsach:[],
    btnGiaoPhieuPH: true,
    ddlTTVT:"",
    options:{
      ttvt:[],
    },
    button:
    {
      naplai: true
    },
    IS_PHIEUCHINH : false,
    CURRENT_PHIEU:[],
    headers: [
        {
          textAlign:"left",
          fieldName: 'phieu_id',
          headerText: 'ID',
          allowFiltering: true,
          allowSorting: false
        },
        {
          textAlign:"left",
          fieldName: 'ngaygiao',
          headerText: 'Ngày giao',
          allowFiltering: true,
          allowSorting: false
        },
        {
          textAlign:"left",
          fieldName: 'nguoigiao',
          headerText: 'Người giao',
          allowFiltering: true,
          allowSorting: false
        },
        {
          textAlign:"left",
          fieldName: 'nhiemvu',
          headerText: 'Nhiệm vụ',
          allowFiltering: true,
          allowSorting: false
        },
        {
          textAlign:"left",
          fieldName: 'ten_dv',
          headerText: 'Đơn vị nhận phiếu',
          allowFiltering: true,
          allowSorting: false
        },  
        {
          textAlign:"left",
          fieldName: 'trangthai_ph',
          headerText: 'Trạng thái phiếu',
          allowFiltering: true,
          allowSorting: false
        },  
    {
    fieldName: '',
    headerText: 'Hủy giao',
    allowFiltering: true,
    allowSorting: false,
    textAlign: 'Center',
    width: 90,
        template: function () {
            return {
            template: Vue.component('xoaTemplate', {
                template: `<a class="btn btn-huylydo lh14" @click="onClickDelete"
                            style="background-color: #3684e0 !important; padding: 5px 15px; color: red">
                <span class="icon one-trash"></span></a>`,
                data () {
                return {data: {}}
                },
                computed: {
                parent () {
                    return this.$parent.$parent.$parent
                }
                },
                methods: {
                async onClickDelete()
                {
                    this.$confirm(`Huỷ giao phiếu?`, {
                    confirmButtonText: "Có",
                    cancelButtonText: "Không",
                    type: "warning",
                    }).then(async () => {
                        const res = await api.XOA_PHIEU_PHOIHOP(this.axios, {
                            "phieu_id": this.phieu_id
                        })
                        let rs =  res.data.data ? res.data.data : ''
                        if(rs != "OK")
                        {
                          this.$toast.error(rs);
                          return;
                        }
                        this.NAP_DS_PHIEU()
                    })
                }
                }
            })
            }
        }
    },     
        
        ],
    };
  },
  created() {
  },
  mounted () {
      this.NAP_THONGTIN_PHIEU_HIENTAI()
      if (this.CURRENT_PHIEU.length == 0) return
      this.NAP_DS_PHIEU()
      this.NAP_DS_TOKT()
  },
  watch: {
    phieu_id(newval)
    {
      this.phieu_id = newval
      this.NAP_THONGTIN_PHIEU_HIENTAI()
      if (this.CURRENT_PHIEU.length == 0) return
      this.NAP_DS_PHIEU()
      this.NAP_DS_TOKT()
    }
  },
  methods: {
async NAP_THONGTIN_PHIEU_HIENTAI()
{
  console.log("phieu_id", this.phieu_id)
  const res = await api.sp_nap_thongtin_phieu_hientai(this.axios, {
     "phieu_id": this.phieu_id
    })
  this.CURRENT_PHIEU = res.data.data ?  res.data.data : []
  console.log('this.CURRENT_PHIEU',this.CURRENT_PHIEU)
  this.IS_PHIEUCHINH = false
  if(this.CURRENT_PHIEU[0].IS_PHIEUCHINH == 1)
  {
    this.IS_PHIEUCHINH = true
  }
  this.btnGiaoPhieuPH = this.IS_PHIEUCHINH
},
async NAP_DS_TOKT()
{
  if(this.kieu_id == '0')
  {
    console.log(888888888)
    const res = await api.SP_LAY_DS_TOKT_THEO_NV(this.axios, {})
    let items = []
    res.data.data.forEach(function (item) {
      items.push({ id: item.donvi_id, text: item.ten_dv })
    })
    this.options.ttvt = items
    this.ddlTTVT = items[0].id
  }
  else
  {
    const res = await api.SP_LAY_DS_DVTC_THEO_PHIEU_ID(this.axios, {
      "vphieu_id": this.phieu_id
    })
    let items = []
    res.data.data.forEach(function (item) {
      items.push({ id: item.donvi_id, text: item.ten_dv })
    })
    this.options.ttvt = items
    this.ddlTTVT = items[0].id
    
  }
},
async NAP_DS_PHIEU()
{
  const res = await api.NAP_DS_PHIEU(this.axios, {
     "phieu_id": this.phieu_id
  })
  this.grid_danhsach = res.data.data ? res.data.data : []
},
async KIEMTRA_GIAOPHIEU()
{
  if (this.ddlTTVT < 0) {
    this.$toast.error("Chưa chọn đơn vị nhận phiếu!") 
      return false;
  }
  //Ko dc chọn đơn vị của phiếu hiện tại
  if (this.ddlTTVT == this.CURRENT_PHIEU[0].DONVI_NHAN_ID)
  {
      this.$toast.error("Đơn vị nhân phiếu phải khác đơn vị hiện tại.");
      return false;
  }  
   const res = await api.KIEMTRA_GIAOPHIEU(this.axios, {
      "hdtb_id": this.CURRENT_PHIEU[0].HDTB_ID,
      "huonggiao_id": this.CURRENT_PHIEU[0].HUONGGIAO_ID,
      "donvi_id": this.ddlTTVT
  })
  let rsCheck = res.data.data ? res.data.data : -1
  if(rsCheck != 0)
  {
    ddl_ttvt_text = ""
    for(var i = 0; i< this.options.ttvt; i++)
    {
      if(this.options.ttvt[i].id == this.ddlTTVT)
      {
        ddl_ttvt_text = this.options.ttvt[i].text
      }
    }
    this.$toast.error("Đơn vị "+ddl_ttvt_text+" đang có phiếu chưa thực hiện, không thể giao phiếu thêm.")
    return false
  }
  return true
},
async GIAOPHIEU_PHOIHOP()
{
  let tt_nd = await this.TT_ND()
     const res = await api.GIAOPHIEU_PHOIHOP(this.axios, {
      "vphieu_id": this.phieu_id,
      "vnhanvien_id": tt_nd.nhanvien_id,
      "vnoidung_giao": "Giao phiếu phối hợp thi công",
      "vdonvi_nhan_id": this.ddlTTVT
  })
  let kq = res.data.data ? res.data.data : false
  if(kq == false)
  { 
    this.$toast.error(res.data.message_detail)
    return false
  }
  return true
},
async TT_ND()
{
    const res = await api.Lay_TT_ND(this.axios, {
    })
    return res.data.data ? res.data.data : []
 
},
btnGiaoPhieuPH_Click()
{
  if (!this.KIEMTRA_GIAOPHIEU()) return;
      let abc = ""
      for(var i =0; i< this.options.ttvt; i++)
      {
        if(this.options.ttvt[i].id == this.ddlTTVT)
        {
          abc = this.options.ttvt[i].text
        }
      }
      this.$confirm(`Giao phiếu phối hợp cùng ` + abc +  ` ?`, {
              confirmButtonText: "Đồng ý",
              cancelButtonText: "Không",
              type: "warning",
              }).then(async () => {
                    if (this.GIAOPHIEU_PHOIHOP()) {
                        this.$toast.success("Giao phiếu thành công.");
                        this.NAP_DS_PHIEU();
                    }
              })
},
showModal() {
  this.$bvModal.show("PhoiHopThiCong")
},
hideModal() {
    this.$bvModal.hide("PhoiHopThiCong")
},
clickButton(key) {
  if (key == "naplai") {
      this.NAP_THONGTIN_PHIEU_HIENTAI()
      this.NAP_DS_PHIEU();

  }
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