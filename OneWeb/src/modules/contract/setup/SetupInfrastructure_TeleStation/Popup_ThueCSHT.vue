<template>
    <div>
        <!-- <button @click="openHDDT" class="btn btn-success m-5">Hóa đơn điện tử</button> -->
        
  <b-modal
    id="hdtb_thuecsht"
    ref="popup_hdtb_thuecsht"
    modal-class="popup-box"
    header-class="popup-header"
    body-class="none-padding"
    size="xl"
    hide-footer
  >
    <template #modal-header="{ close }">
      <div class="title">
        <span class="icon nc-icon-outline ui-1_settings"></span> Danh sách CSHT cho thuê
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
          @click="clickButton('xoa')" :style="this.button.xoa ? '' : 'pointer-events: none'"
          :class="this.button.xoa ? 'active' : 'non-active'"
        >
          <a> <span class="icon one-file-plus"></span>Xóa </a>
        </li>
      </ul>
    </div>

                <div class="popup-body">
                    <div class="box-form">
                        <div class="legend-title">Chọn cơ sở hạ tầng</div>
                        <div class="group-buttons mart0">
                            <div class="pull-left">
                                <button class="btn btn-second" @click.prevent="btnThueCap">Thuê cáp</button>
                                <button class="btn btn-second" @click.prevent="btnThueCot">Thuê cột</button>
                                <button class="btn btn-second" @click.prevent="btnThueBe">Thuê bể</button>
                                <button class="btn btn-second" @click.prevent="btnThueCong">Thuê cống</button>
                                <div class="check-action">
                                    <input type="checkbox" v-model="chkShowAll" class="check">
                                    <span class="name">Hiện tất cả CSHT</span>
                                </div>
                            </div>
                            <div class="pull-right">
                                <button class="btn btn-second">Test</button>
                            </div>
                            <div class="clearfix"></div>
                        </div>
                        
                    </div>
                    
                    <div class="box-form">
                        <div class="legend-title">Danh sách cơ sở hạ tầng đã chọn</div>
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
                    </div>
                    
                </div>
            


  </b-modal>

    <ThongTinThem ref="popup_ThongTinThem" :showSoSoiCap= "showSoSoiCap" @chapnhan="acceptThongTinThem"/>
    <DSBeCap ref="popup_DSBeCap"  @chapnhan="accep_DSBeCap" 
    :multiSelect="true"
    :DialogMode="true"/>
    <DSCap ref="popup_DSCap"  @chapnhan="acceptDSCap"
    :multiSelect="true"/>
    <DSCot ref="popup_DSCot"  @chapnhan="acceptDSCot"
    :multiSelect="true"
    :DialogMode="true"/>
    <DSDoanCong ref="popup_DSDoanCong"  @chapnhan="acceptDSDoanCong"
    :multiSelect="true"
    :DialogMode="true"/>


     <ModalQLDoanCong modalId="popupQLDoanCong" :data="dataSend"/>
    <ModalQLBe modalId="popupQLBe" :data="dataSend"/>
    <ModalQLCot  modalId="popupQLCot" :data="dataSend" />
    <ModalQLCapExt modalID="ModalQLCap" ref="ModalQLCap"/>        
    </div>
</template>
<script>
import api from "./api";
import moment from "moment";
import ThongTinThem from "./Popup_ThongTinThem.vue";
import DSBeCap from "./Popup_DSBeCap.vue";
import DSCap from "./Popup_DSCap.vue";
import DSCot from "./Popup_DSCot.vue";
import DSDoanCong from "./Popup_DSDoanCong.vue";
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


import Vue from 'vue';
import ModalQLDoanCong from '@/modules/CABMAN/BanDoTuyenCong/QLDoanCong/Modal'
import ModalQLBe from '@/modules/CABMAN/BanDoTuyenCong/QLBe/Modal'  
import ModalQLCot from '@/modules/CABMAN/BanDoTuyenCot/QLCot/Modal'  
import ModalQLCapExt from '@/modules/CABMAN/BanDoMangCap/modal/QLCapExt'




export default {
name:'Popup_ThueCSHT',

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

  components: {
    ThongTinThem,
    DSBeCap,
    DSCap,
    DSCot,
    DSDoanCong,
    breadcrumb, 
    DataGridEdit,
    ModalQLDoanCong,
    ModalQLBe,
    ModalQLCot,
    ModalQLCapExt

      },






props: {
    hdtb_id:"",
    phieu_id:""
},

  data() {
    return {

    button:{
        xoa:true
    },

    dataSend : {
          readOnly: true,
          doanCongId: ""
    },

    showSoSoiCap:true,
    thongtin_them: false,
    chkShowAll:false,
    LOAICSHT_ID:-1,
    CSHT_ID:-1,
    TuNgay:moment().format("DD/MM/YYYY"),
    DenNgay:moment().format("DD/MM/YYYY"),
    DonGia:"",
    ThanhTien:"",
    SoSoiCap:"",
    item_bien:[],
    bien:"",
    grid_danhsach:[],
    headers: [
        {
          textAlign:"left",
          fieldName: 'loaicsht',
          headerText: 'Loại CSHT',
          allowFiltering: true,
          allowSorting: false
        },
        {
          textAlign:"left",
          fieldName: 'tencsht',
          headerText: 'Tên CSHT',
          allowFiltering: true,
          allowSorting: false
        },

        {
          textAlign:"center",
          fieldName:"tungay",
          headerText:"Từ ngày",
          allowSorting:"true",
          allowFiltering:"true"
        },
        {
          textAlign:"center",
          fieldName:"denngay",
          headerText:"Đến ngày",
          allowSorting:"true",
          allowFiltering:"true",
        },
        {
          textAlign:"center",
          fieldName:"ngay_cn",
          headerText:"Ngày cập nhật",
          allowSorting:"true",
          allowFiltering:"true",
        },
        {
          textAlign:"left",
          fieldName:"thongtinthem",
          headerText:"Chi tiết",
          allowSorting:"true",
          allowFiltering:"true",
          disableHtmlEncode: false,
          //allowHtml: true
    },
    {
    fieldName: '',
    headerText: '',
    allowFiltering: true,
    allowSorting: false,
    textAlign: 'Center',
    width: 90,
        template: function () {
            return {
            template: Vue.component('columnTemplate', {
                template: `<div><a href="javascript: void(0)" @click="onView" title="Xem chi tiết"><span></span>Xem chi tiết</a></div>`,
                data () {
                return {data: {}}
                },
                computed: {
                parent () {
                    return this.$parent.$parent.$parent
                }
                },
                methods: {
                    async onView()
                    {

                      if(this.data.loaicsht_id == 1)
                      {
                        this.$refs.ModalQLCap.showQLCap(this.data.csht_id)
                      }

                      if(this.data.loaicsht_id == 2)
                      {

                        this.dataSend={
                          readOnly:true,
                          cot_id:this.data.csht_id
                          
                        }

                        this.$bvModal.show('popupQLCot')

                      }
                      if(this.data.loaicsht_id == 3)
                      {
                        this.dataSend = {
                          readOnly: true,
                          beCapId: this.data.csht_id                  
                          }
                          
                         this.$bvModal.show('popupQLBe')
                      }
                      if(this.data.loaicsht_id == 4)
                      {
                        this.dataSend = {
                          readOnly: true,
                          beCapId: this.data.csht_id                  
                          }
                         this.$bvModal.show('popupQLDoanCong')
                      }
                    },


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

  watch: {
    hdtb_id()
    {
      this.NAP_DS_CSHT()
    },
    chkShowAll()
    {
      this.NAP_DS_CSHT()
    }
    // phieu_id()
    // {
    //   this.NAP_DS_CSHT()
    // },
  },

  methods: {


async NAP_DS_CSHT()
{
  let show = 0
  if(this.chkShowAll == true)
  {
    show = 1
  }

  console.log("vhdtb_id", this.hdtb_id,
      "vnhanvien_id", this.$root.token.getNhanVienID(),
      "chkshowall", show)

    const res = await api.sp_ds_hdtb_csht(this.axios, {
      "vhdtb_id": this.hdtb_id,
      "vnhanvien_id": this.$root.token.getNhanVienID(),
      "chkshowall": show
    })
    this.grid_danhsach =  res.data.data ? res.data.data : []
    for(var i =0; i<this.grid_danhsach.length; i++ )
    {
      this.grid_danhsach[i].ngay_cn = moment(this.grid_danhsach[i].ngay_cn).format('DD/MM/YYYY')
      this.grid_danhsach[i].thongtinthem = this.grid_danhsach[i].thongtinthem.replace(/(?:\r\n|\r|\n)/g, '<br />')
    }

    this.button.xoa = false
    
},


SHOW_POPUP_THONGTIN_THEM()
{
  this.$refs.popup_ThongTinThem.showModal();
},

acceptThongTinThem(item) {

   
      this.DonGia = item.DonGia
      this.ThanhTien = item.ThanhTien
      this.SoSoiCap = item.SoSoiCap
      this.thongtin_them = true

      this.TuNgay = moment(item.TuNgay,"DD/MM/YYYY").format("DD/MM/YYYY")
      this.DenNgay = moment(item.DenNgay,"DD/MM/YYYY").format("DD/MM/YYYY") 
      


      this.accept(this.bien)
    },

 async acceptDSCap(item) {

    console.log('item cap',item)
    this.bien = "cap"
    this.item_bien = item
    this.SHOW_POPUP_THONGTIN_THEM()

  },



 async acceptDSCot(item) {
    this.bien = "cot"
    this.item_bien = item
    this.SHOW_POPUP_THONGTIN_THEM()
     
  },

 async accep_DSBeCap(item) {
    this.bien = "becap"
    this.item_bien = item
    this.SHOW_POPUP_THONGTIN_THEM()
    if (this.thongtin_them != true) return;




  },


 async acceptDSDoanCong(item) {
    this.bien = "doancong"
    this.item_bien = item 
    this.SHOW_POPUP_THONGTIN_THEM()

  },


async accept(bien)
{

  let msg =""
  let kq=""
  if(bien == "cot")
  {
    if (this.thongtin_them != true) return;
    let tt_nd = await this.Lay_TT_ND()
     
    for(var i=0;i<this.item_bien.length; i++)
    {

      let js = [{
        "HDTB_ID": this.hdtb_id,
        "CSHT_ID": this.item_bien[i].COT_ID,
        "LOAICSHT_ID": 2,
        "TUNGAY": this.TuNgay,
        "DENNGAY": this.DenNgay ,
        "DONGIA": this.DonGia,
        "THANHTIEN": this.ThanhTien,
        "SO_DOIDAY": this.SoSoiCap, 
        "NGUOI_CN": tt_nd.ma_nd,
        "MAY_CN": await this.$root.token.getMachine(),
        "IP_CN": await this.$root.token.getIP(),
        "NHANVIEN_ID": this.$root.token.getNhanVienID()
        }]

        const res = await api.sp_insert_hdtb_csht(this.axios, {
          "vds": JSON.stringify(js)
        })
        //let kq = res.data.data ? res.data.data : "[Thành công]"
        kq = res.data.data ? res.data.data : ''
        if(kq == 1)
        {
          kq = "[Thành công]"
        }
        msg += "Cột "+ this.item_bien[0].SO_HIEU + " -> "+ kq +"\r\n"
   }
  }

  if(bien == "becap")
  {
    let tt_nd = await this.Lay_TT_ND()
    for(var i=0;i<this.item_bien.length; i++)
    {
      let js = [{
        "HDTB_ID": this.hdtb_id,
        "CSHT_ID": this.item_bien[i].BECAP_ID,
        "LOAICSHT_ID": 3,
        "TUNGAY": this.TuNgay,
        "DENNGAY": this.DenNgay ,
        "DONGIA": this.DonGia,
        "THANHTIEN": this.ThanhTien,
        "SO_DOIDAY": this.SoSoiCap, 
        "NGUOI_CN": tt_nd.ma_nd,
        "MAY_CN": await this.$root.token.getMachine(),
        "IP_CN": await this.$root.token.getIP(),
        "NHANVIEN_ID": this.$root.token.getNhanVienID()
        }]


        const res = await api.sp_insert_hdtb_csht(this.axios, {
          "vds": JSON.stringify(js)
        })
        kq = res.data.data ? res.data.data : ''
        if(kq == 1)
        {
          kq = "[Thành công]"
        }
        msg += "Bể cáp "+ this.item_bien[0].KYHIEU + " -> "+ kq +"\r\n"
    }
  }

  if(bien == "doancong")
  {
    if (this.thongtin_them != true) return;

    let tt_nd = await this.Lay_TT_ND()
    for(var i=0;i<this.item_bien.length; i++)
    {

      let js = [{
        "HDTB_ID": this.hdtb_id,
        "CSHT_ID": this.item_bien[i].DOANCONG_ID,
        "LOAICSHT_ID": 4,
        "TUNGAY": this.TuNgay,
        "DENNGAY": this.DenNgay ,
        "DONGIA": this.DonGia,
        "THANHTIEN": this.ThanhTien,
        "SO_DOIDAY": this.SoSoiCap, 
        "NGUOI_CN": tt_nd.ma_nd,
        "MAY_CN": await this.$root.token.getMachine(),
        "IP_CN": await this.$root.token.getIP(),
        "NHANVIEN_ID": this.$root.token.getNhanVienID()
        }]

        console.log('JSON.stringify(js)',JSON.stringify(js))
        const res = await api.sp_insert_hdtb_csht(this.axios, {
          "vds": JSON.stringify(js)
        })
        kq = res.data.data ? res.data.data : ''
        if(kq == 1)
        {
          kq = "[Thành công]"
        }        
        msg += "Cống cáp "+ this.item_bien[0].KYHIEU + " -> "+ kq +"\r\n"
    }
  }

  if(bien == "cap")
  {
    if (this.thongtin_them != true) return;

    let tt_nd = await this.Lay_TT_ND()

    console.log('this.item_bien',this.item_bien)

    for(var i=0;i<this.item_bien.length; i++)
    {

      let js = [{
        "HDTB_ID": this.hdtb_id,
        "CSHT_ID": this.item_bien[i].cap_id,
        "LOAICSHT_ID": 1,
        "TUNGAY": this.TuNgay,
        "DENNGAY": this.DenNgay ,
        "DONGIA": this.DonGia,
        "THANHTIEN": this.ThanhTien,
        "SO_DOIDAY": this.SoSoiCap, 
        "NGUOI_CN": tt_nd.ma_nd,
        "MAY_CN": await this.$root.token.getMachine(),
        "IP_CN": await this.$root.token.getIP(),
        "NHANVIEN_ID": this.$root.token.getNhanVienID()
        }]

        console.log('js cap',JSON.stringify(js))

        const res = await api.sp_insert_hdtb_csht(this.axios, {
          "vds": JSON.stringify(js)
        })
        kq = res.data.data ? res.data.data : ''
        if(kq == 1)
        {
          kq = "[Thành công]"
        }        
        msg += "Cáp "+ this.item_bien[0].kyhieu + " -> "+ kq +"\r\n"
    }
  }


if (kq == "") return
this.$toast.success("Kết quả:\r\n" + msg)
this.NAP_DS_CSHT()

},



btnThueCap()
{
  this.showSoSoiCap = true
  this.CHON_CSHT(1)
},
btnThueCot()
{
  this.showSoSoiCap = true
  this.CHON_CSHT(2)
},
btnThueBe()
{
  this.showSoSoiCap = false
  this.CHON_CSHT(3)
},
btnThueCong()
{
  this.showSoSoiCap = true
  this.CHON_CSHT(4)
},



CHON_CSHT(loaicsht_id)
{
  let showSoSoiCap = false
  if(loaicsht_id == 1)
  {
    showSoSoiCap = true
  }

  switch (loaicsht_id) {
    case 1:
      this.$refs.popup_DSCap.showModal()
      break
    case 2:
      this.$refs.popup_DSCot.showModal()
      break
    case 3:
      this.$refs.popup_DSBeCap.showModal()
      break
    case 4:
      this.$refs.popup_DSDoanCong.showModal()
      break
  }


},


async Lay_TT_ND()
{
    const res = await api.Lay_TT_ND(this.axios, {
    })
    return res.data.data ? res.data.data : []
 
},


grid_RowSelected(subject)
{
  this.LOAICSHT_ID = subject.data.loaicsht_id
  this.CSHT_ID = subject.data.csht_id
  this.button.xoa= true
},


showModal() {
  this.$bvModal.show("hdtb_thuecsht")
},
hideModal() {
    this.$bvModal.hide("hdtb_thuecsht")
},


async clickButton(key) {

  if (key == "chapnhan") {
      this.ChapNhan()
  }
  if (key == "xoa") {
  this.$confirm(`Loại bỏ những CSHT đang chọn khỏi danh sách cho thuê ?`, {
        confirmButtonText: "Có",
        cancelButtonText: "Không",
        type: "warning",
        }).then(async () => {
              let json = this.$refs.grid_danhsach.getSelectedRecords()
              let isDeleted = false
              for (var i = 0; i < json.length; i++)
              {
                const res = await api.sp_delete_hdtb_csht(this.axios, {
                    "vhdtb_id": this.hdtb_id,
                    "vcsht_id": json[i].csht_id,
                    "vloaicsht_id": json[i].loaicsht_id
                })
                isDeleted = true
              }
              if (isDeleted) this.NAP_DS_CSHT()
        })

    

}
}

},


};
</script>
<style>


div#hdtb_thuecsht___BV_modal_outer_{
  z-index: 10 !important;
}

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