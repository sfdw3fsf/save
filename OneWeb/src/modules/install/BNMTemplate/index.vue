<template>
  <div>
    <div class="breadcrumb-top">
    <div class="main-title">{{title}}</div>
    <ul class="breadcrumb">
      <li class="breadcrumb-item"><a href="#"><span class="nc-icon-outline ui-1_home-simple"></span>  Cập nhật KQTC </a></li>
      <li class="breadcrumb-item"><a href="#">Khai báo thông số</a></li>
      <li class="breadcrumb-item active">Khai báo template BNM</li>
    </ul>
  </div>
    <vue-nav>
      <ul class="list">
        <li
          @click="clickButton('laydulieu')"
          :class="this.button.laydulieu ? 'active' : 'non-active'"
        >
          <a> <span class="icon one-chart-area"></span>Lấy dữ liệu</a>
        </li>
        <li
          @click="clickButton('themmoi')"
          :class="this.button.themmoi ? 'active' : 'non-active'"
        >
          <a> <span class="icon nc-icon-glyph ui-1_circle-add"></span>Thêm mới</a>
        </li>
        <li
          @click="clickButton('capnhat')"
          :class="this.button.capnhat ? 'active' : 'non-active'"
        >
          <a> <span class="icon one-reload1"></span>Cập nhật</a>
        </li>
        <li
          @click="clickButton('xoa')"
          :class="this.button.xoa ? 'active' : 'non-active'"
        >
          <a> <span class="icon one-trash"></span>Xoá</a>
        </li>

      </ul>

    </vue-nav>

        <div class="page-content">
            <div class="grid-stack-box">
                <div class="box-col box-form col-2" id="boxLeft">
                    <div class="table-content">

              <DataGrid
                :columns="this.options.cols_thamso"
                v-bind:dataSource="this.grid_thamso"
                :showColumnCheckbox="false"
                :enable-paging-server="false"
                :allowPaging="false"
                :showFilter="false"
                :enabledSelectFirstRow="false"
                @recordDoubleClick="recordDoubleClick"
              >
              </DataGrid>




                    </div>
                </div>
                <div class="box-col col-10" id="boxRight" style="height:100%">
                    <div class="box-form">

                            <DataGridEdit
                              ref="grid_danhsach"
                              v-bind:columns="this.options.cols"
                              v-bind:dataSource="this.grid_danhsach"
                              :showFilter="true"
                              :enabledSelectFirstRow="true"
                              :editSettings="{ allowEditing: false,allowAdding: false,allowDeleting: false, mode: 'Normal' }"
                              :showColumnPointer="true"
                              :enable-paging-server="false"
                              :allowPaging="true"
                              :showColumnCheckbox="false"
                              @rowSelected="grid_RowSelected"
                            >
                            </DataGridEdit>

                    </div>
                    <div class="box-form">
                        <div class="row">
                            <div class="col-sm-8 col-12">
                                <div class="legend-title">Template</div>
                                <textarea class="form-control text" 
                                v-model="TextArea"
                                ref="textarea" 
                                style="height:420px" 
                               ></textarea>
                            </div>
                            <div class="col-sm-4 col-12">
                                <div class="legend-title">Thông tin chi tiết</div>
                                <div class="info-row">
                                    <div class="key w120">
                                        Loại tác động
                                    </div>
                                    <div class="value">
                                        <input type="text" v-model="txtLOAI_TD"  class="form-control">
                                    </div>
                                </div>
                                <div class="info-row">
                                    <div class="key w120">
                                        Loại ONU
                                    </div>
                                    <div class="value">
                                        <div class="select-custom">
                                          <SelectExt v-model="cboLoai_ONU" :options="this.options.loai_onu"> 
                                            <option disabled value="0">Select one</option> 
                                          </SelectExt> 
                                        </div>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-sm-6 col-12">
                                        <div class="info-row">
                                            <div class="key w120">
                                                Hãng SX 
                                            </div>
                                            <div class="value">
                                                <div class="select-custom">
                                                <SelectExt v-model="cboHangSX" :options="this.options.hangsx"> 
                                                    <option disabled value="0">Select one</option> 
                                                </SelectExt> 
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="col-sm-6 col-12">
                                        <div class="info-row">
                                            <div class="key w60">
                                                BR_ID
                                            </div>
                                            <div class="value">
                                                <input type="text" v-model="txtBNM_BR_ID" class="form-control tright">
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="row mart20">
                                    <div class="col-sm-4 col-12">
                                        <div class="item marb10">
                                            <div class="check-action">
                                                <input type="checkbox" v-model="chkFTTH"  class="check">
                                                <span class="name">FTTH</span>
                                            </div>
                                        </div>
                                        <div class="item marb10">
                                            <div class="check-action">
                                                <input type="checkbox" v-model="chkBOSUNG_MYTV" class="check">
                                                <span class="name">Bổ sung MyTV</span>
                                            </div>
                                        </div>
                                        <div class="item marb10">
                                            <div class="check-action">
                                                <input type="checkbox" v-model="chkISACTIVE"  class="check">
                                                <span class="name">ACTIVE</span>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="col-sm-4 col-12">
                                        <div class="item marb10">
                                            <div class="check-action">
                                                <input type="checkbox" v-model="chkMYTV" class="check">
                                                <span class="name">MYTV</span>
                                            </div>
                                        </div>
                                        <div class="item marb10">
                                            <div class="check-action">
                                                <input type="checkbox" v-model="chkIMS" class="check">
                                                <span class="name">Bổ sung IMS</span>
                                            </div>
                                        </div>
                                        <div class="item marb10">
                                            <div class="check-action">
                                                <input type="checkbox" v-model="chkSkipOnuType" class="check">
                                                <span class="name">Bỏ qua loại ONU</span>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="col-sm-4 col-12">
                                        <div class="item marb10">
                                            <div class="check-action">
                                                <input type="checkbox" v-model="chkIMS"  class="check">
                                                <span class="name">IMS</span>
                                            </div>
                                        </div>
                                        
                                    </div>
                                </div>

 <textarea  v-model="txtNotes" class="form-control mart40 italic red " style="height: 160px;" ></textarea>


                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>





  </div>
</template>
<script>

import api from "../BNMTemplate/api";
import define from "../BNMTemplate/define";
import moment from "moment";
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

  data() {

    return {

      ...define,
    grid_danhsach:[],
    grid_thamso:[],
    cboLoai_ONU:"",
    cboHangSX:"",
    txtNotes:"",

    _row_index:0,
    _row_index_tg:0,
    temp_id:0,
    TextArea:"",

    txtLOAI_TD:"",
    txtBNM_BR_ID:"",
    chkSkipOnuType:false,
    chkISACTIVE:false,
    chkBOSUNG_MYTV:false,
    chkBOSUNG_IMS:false,
    chkIMS:false,
    chkMYTV:false,
    chkFTTH:false,
    title:"KHAI BÁO KỊCH BẢN BNM",
    row_select:0,


      options: {
        loai_onu:[],
        hangsx:[],


        cols:[
                {
                    fieldName: "temp_id",
                    headerText: "TEMP_ID",
                    allowSorting: true,
                    autoFit: true,
                    width: 80,
                    textAlign: 'center'
                },
                {
                    fieldName: "ten_loaitd",
                    headerText: "Loại tác động",
                    allowSorting: true,
                    autoFit: true,
                    textAlign: 'left'
                },  
                {
                    fieldName: "hangsx_id",
                    headerText: "HANGSX_ID",
                    allowSorting: true,
                    width: 120,
                    autoFit: true,
                    textAlign: 'center'
                },
                {
                    fieldName: "ten_onu",
                    headerText: "Loại ONU",
                    allowSorting: true,
                    autoFit: true,
                    textAlign: 'left'
                },
                {
                    fieldName: "ftth",
                    headerText: "FTTH",
                    allowSorting: true,
                    autoFit: true,
                    width: 80,
                    textAlign: 'center',
                    type: "Boolean",
                    displayAsCheckBox: true,
                },
                {
                    fieldName: "mytv",
                    headerText: "MYTV",
                    allowSorting: true,
                    autoFit: true,
                    width: 80,
                    textAlign: 'center',
                    type: "Boolean",
                    displayAsCheckBox: true,
                },
                {
                    fieldName: "ims",
                    headerText: "IMS",
                    allowSorting: true,
                    autoFit: true,
                    width: 80,
                    textAlign: 'center',
                    type: "Boolean",
                    displayAsCheckBox: true,
                },
                {
                    fieldName: "bosung",
                    headerText: "BOSUNG",
                    allowSorting: true,
                    autoFit: true,
                    width: 80,
                    textAlign: 'center',
                    type: "Boolean",
                    displayAsCheckBox: true,
                },
                {
                    fieldName: "bosung_ims",
                    headerText: "BOSUNG_IMS",
                    allowSorting: true,
                    autoFit: true,
                    width: 80,
                    textAlign: 'center',
                    type: "Boolean",
                    displayAsCheckBox: true,
                },
                {
                    fieldName: "bnm_br_id",
                    headerText: "BNM_BR_ID",
                    allowSorting: true,
                    width: 120,
                    autoFit: true,
                    textAlign: 'center'
                },
                {
                    fieldName: "ngay_cn",
                    headerText: "NGAY_CN",
                    allowSorting: true,
                    width: 120,
                    autoFit: true,
                    textAlign: 'left'
                },
                {
                    fieldName: "nguoi_cn",
                    headerText: "NGƯỜI CN",
                    allowSorting: true,
                    width: 120,
                    autoFit: true,
                    textAlign: 'left'
                },
                {
                    fieldName: "is_active",
                    headerText: "ACTIVE",
                    allowSorting: true,
                    autoFit: true,
                    width: 80,
                    textAlign: 'center',
                    type: "Boolean",
                    displayAsCheckBox: true,
                }                                

        ],
        cols_thamso:[
                {
                    fieldName: "column_name",
                    headerText: "",
                    allowSorting: true,
                    autoFit: true,
                },                  
        ],

      },

    };
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

  created() {
      this.Load_DS_ThamSo()
  },
  watch: {
 
 
  },

  methods: {



    async Load_DS_ThamSo() {
            const res = await api.LAY_DS_THAMSO_BNM(this.axios, {
                })
                let kq =  await res.data.data ?  res.data.data : []
                this.grid_thamso =  kq[0]

                let items_loai_onu = []

                kq[1].forEach(function (item) {
                    items_loai_onu.push({ id: item.onu_id, text: item.ten_onu });
                });
                this.options.loai_onu = items_loai_onu;
                this.cboLoai_ONU = items_loai_onu[0].id

                let items_hangsx = []

                kq[2].forEach(function (item) {
                    items_hangsx.push({ id: item.hangsx_id, text: item.ten_hang_sx });
                });
                this.options.hangsx = items_hangsx;
                this.cboHangSX = items_hangsx[0].id

                this.txtNotes = kq[3].note_bnm

                this.grid_danhsach = kq[4]



                for(var i = 0; i < kq[4].length; i++)
                {
                    if(this.grid_danhsach[i].ftth == 0)
                    {
                        this.grid_danhsach[i].ftth = false
                    }
                    else
                    {
                        this.grid_danhsach[i].ftth = true
                    }

                    if(this.grid_danhsach[i].mytv == 0)
                    {
                        this.grid_danhsach[i].mytv = false
                    }
                    else
                    {
                        this.grid_danhsach[i].mytv = true
                    }

                    if(this.grid_danhsach[i].ims == 0)
                    {
                        this.grid_danhsach[i].ims = false
                    }
                    else
                    {
                        this.grid_danhsach[i].ims = true
                    }

                    if(this.grid_danhsach[i].bosung == 0)
                    {
                        this.grid_danhsach[i].bosung = false
                    }
                    else
                    {
                        this.grid_danhsach[i].bosung = true
                    }


                    if(this.grid_danhsach[i].bosung_ims == 0)
                    {
                        this.grid_danhsach[i].bosung_ims = false
                    }
                    else
                    {
                        this.grid_danhsach[i].bosung_ims = true
                    }

                    if(this.grid_danhsach[i].is_active == 0)
                    {
                        this.grid_danhsach[i].is_active = false
                    }
                    else
                    {
                        this.grid_danhsach[i].is_active = true
                    }

                }

    },  


async recordDoubleClick(data)
{

    let insertText = "[$"+data.rowData.column_name+"$]"
    if (!data.rowData.column_name.length) return

    let textarea_new = this.$refs.textarea
    const len = textarea_new.length
    let pos = await textarea_new.selectionStart
    
    if (pos === undefined) {
    pos = 0
    }

    const before = this.TextArea.substring(0, pos)
    const after = this.TextArea.substring(pos, len)

    this.TextArea = before + insertText + after


      this.$nextTick().then(() => {
        textarea_new.selectionStart = pos + insertText.length
      })

},


async grid_RowSelected(data)
{

    this.row_select = data.rowIndex
    this._row_index = data.rowIndex

    this.temp_id = data.data.temp_id

    const res = await api.lay_template_khaibao(this.axios, {}, this.temp_id)

    let dtTemp = res.data.data ? res.data.data : []
    
    //console.log('this._row_index',this._row_index,this._row_index_tg)
    if (dtTemp != null)
    {
        this.TextArea = dtTemp.template
        this.txtBNM_BR_ID = dtTemp.bnm_br_id
        this.txtLOAI_TD = dtTemp.loai_t
        this.cboLoai_ONU = this.grid_danhsach[this._row_index].onu_id
        this.cboHangSX = this.grid_danhsach[this._row_index].hangsx_id
        this.chkFTTH = false
        if(dtTemp.ftth == 1)
        {
            this.chkFTTH = true
        }
        this.chkMYTV = false
        if(dtTemp.mytv == 1)
        {
            this.chkMYTV = true
        }        
        this.chkIMS = false
        if(dtTemp.ims == 1)
        {
            this.chkIMS = true
        }           
        this.chkBOSUNG_IMS = false
        if(dtTemp.bosung_ims == 1)
        {
            this.chkBOSUNG_IMS = true
        }   
        this.chkBOSUNG_MYTV = false
        if(dtTemp.bosung_mytv == 1)
        {
            this.chkBOSUNG_MYTV = true
        }           
        this.chkISACTIVE = false
        if(dtTemp.is_active == 1)
        {
            this.chkISACTIVE = true
        }             
        this.chkSkipOnuType = false
        if(dtTemp.skip_onu_type == 1)
        {
            this.chkSkipOnuType = true
        } 

    }


},

async btnDelete_Click()
{
    if (this.temp_id == 0)
    {
        this.$toast.error("Bạn chưa chọn dữ liệu cần xóa!") 
    }
    let _ftth = 0
    if(this.chkFTTH == true )
    {
        _ftth = 1
    }
    let _mytv = 0
    if(this.chkMYTV == true )
    {
        _mytv = 1
    }    

    let _ims = 0
    if(this.chkIMS == true )
    {
        _ims = 1
    }   
    
    let _bosung = 0
    if(this.chkBOSUNG_MYTV == true )
    {
        _bosung = 1
    }  

    let _bosung_ims = 0
    if(this.chkBOSUNG_IMS == true )
    {
        _bosung_ims = 1
    }  

    let _skip_onu = 0
    if(this.chkSkipOnuType == true )
    {
        _skip_onu = 1
    }  

    let kq = await this.capnhatdulieu(_ftth,_mytv,_ims,_bosung,_bosung_ims,_skip_onu,"D","")

    

    if(kq.status == "ok")
    {
        //thay đổi
        this.Load_DS_ThamSo()
        this.$toast.success("Xóa dữ liệu thành công !");
    }
    else
    {
        this.$toast.error(kq.detail);
    }

    
},

async btnCapNhat_Click()
{
    let _ftth = 0
    if(this.chkFTTH == true )
    {
        _ftth = 1
    }
    let _mytv = 0
    if(this.chkMYTV == true )
    {
        _mytv = 1
    }    

    let _ims = 0
    if(this.chkIMS == true )
    {
        _ims = 1
    }   
    
    let _bosung = 0
    if(this.chkBOSUNG_MYTV == true )
    {
        _bosung = 1
    }  

    let _bosung_ims = 0
    if(this.chkBOSUNG_IMS == true )
    {
        _bosung_ims = 1
    }  

    let _skip_onu = 0
    if(this.chkSkipOnuType == true )
    {
        _skip_onu = 1
    }  

    let _kq = "";
    if (this.temp_id == 0)
    {
        _kq = await this.capnhatdulieu(_ftth,_mytv,_ims,_bosung,_bosung_ims,_skip_onu,"A",this.txtLOAI_TD)
    }
    else
    {
        _kq = await this.capnhatdulieu(_ftth,_mytv,_ims,_bosung,_bosung_ims,_skip_onu,"U",this.txtLOAI_TD)
    }

    
    if(_kq.status == "ok")
    {
        this.Load_DS_ThamSo()
        this.$toast.success("Thực hiện thêm mới dữ liệu thành công !");
    }
    else
    {
        this.$toast.error(_kq.detail);
    }

    this.$refs.grid_danhsach.setCurrentSelectedRow(2)
},


async capnhatdulieu(_ftth,_mytv,_ims,_bosung,_bosung_ims,_skip_onu,vtype,vloai_td)
{
    const res = await api.capnhatdulieu(this.axios, {
        "temp_id":this.temp_id, 
        "hangsx_id":this.cboHangSX, 
        "template":this.TextArea, 
        "bnm_br_id":this.txtBNM_BR_ID, 
        "onu_id":this.cboLoai_ONU, 
        "ftth":_ftth, 
        "mytv":_mytv, 
        "ims":_ims, 
        "bosung":_bosung, 
        "bosung_ims":_bosung_ims, 
        "skip_onu":_skip_onu, 
        "loai_td":vloai_td, 
        "type":vtype

    })
    return res.data.data ? res.data.data : []
},

Clear()
{
    this.TextArea = ""
    this.chkFTTH = false
    this.chkMYTV = false
    this.chkIMS = false
    this.chkBOSUNG_IMS = false
    this.chkBOSUNG_MYTV = false
    this.chkISACTIVE = false
    this.temp_id = 0
    this.txtBNM_BR_ID = "0"
},

    clickButton(key) {
    if (key == "laydulieu") {
      this.Load_DS_ThamSo()
    }
    if (key == "themmoi") {
        this.Clear()
    }           
    if (key == "capnhat") {
      
        this.$confirm(`Bạn chắc chắn muốn lưu dữ liệu ?`, {
        confirmButtonText: "Có",
        cancelButtonText: "Không",
        type: "warning",
        }).then(async () => {
            this.btnCapNhat_Click()
            
        })
    } 
    if (key == "xoa") {
      this.btnDelete_Click()
    } 
 
 
    }


  },
};





</script>



<style>
li.non-active a {
  color: #d3d3d3 !important;
}

.traphieu .page-content {
  position: unset;
}

.modal-dialog {
  border-radius: 0px !important;
  width: 100% !important;
  min-width: 720px !important;
}

#boxLeft .e-grid .e-content { 
       height: 933px !important
    }

</style>