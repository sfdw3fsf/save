<template>
  <div>
    <vue-bread-crumb :header="header" />
    <vue-nav>
      <ul class="list">
        <li
          @click="clickButton('duyet')" 
          :class="this.button.duyet ? 'active' : 'non-active'"
        >
          <a> <span class="icon one-file-plus"></span>Duyệt</a>
        </li>
      </ul>
    </vue-nav>
    <div class="page-content">
      <div class="row">
        <div class="col-sm-6 col-12">
          <div class="box-form" style="height: 100%">
            <div  class="tab-pane">
              <div class="box-form">
                <div class="legend-title">Danh sách phiếu</div>


                <div class="row">
                            <div class="col-sm-4 col-12">
                                <div class="info-row">
                                    <div class="key w55">Từ ngày</div>
                                    <div class="input-icon-right">
                                      <vue-date
                                            format="DD/MM/YYYY"
                                            type="datetime"
                                            v-model="dtpTuNgay"
                                      ></vue-date>                                      
                                    </div>
                                </div>
                            </div>
                            <div class="col-sm-4 col-12">
                                <div class="info-row">
                                    <div class="key w60">Đến ngày</div>
                                    <div class="value">
                                      <div class="input-icon-right">
                                          <vue-date
                                                  format="DD/MM/YYYY"
                                                  type="datetime"
                                                  v-model="dtpDenNgay"
                                          ></vue-date>   
                                      </div>
                                    </div>
                                </div>
                            </div>
                            <div class="col-sm-4 col-12">
                                <div class="info-row">
                                    <div class="key w70">Trạng thái</div>
                                    <div class="value">
                                      <div class="select-custom">
                                          <SelectExt
                                                  v-model="cbbTrangThaiPhieu"
                                                  :options="this.options.ttphieu">
                                                  <option disabled value="0">Select one</option>
                                          </SelectExt>
                                      </div>
                                    </div>
                                </div>
                            </div>                            
                </div>

                <div class="row">
                            <div class="col-sm-9 col-12">
                              <div class="info-row">
                                    <div class="key w55">Chủ đề</div>
                                    <div class="value">
                                      <div class="select-custom">
                                          <SelectExt
                                                  v-model="cboChuDe"
                                                  :options="this.options.chude">
                                                  <option disabled value="0">Select one</option>
                                          </SelectExt>
                                      </div>
                                    </div>
                                </div>
                            </div>
                         
                            <div class="col-sm-3 col-12">
                              
                              <div class="info-row">
                                <div class="value">
                                    <button class="btn btn-second normal" 
                                    style="width: 100%;background-color: blue;color: aliceblue;"
                                    @click.prevent="TraCuu"
                                    >Tra cứu</button>
                                </div>                                

                              </div>
                            </div>


                </div>




              </div>
            </div>
<div class="box-form">
  <div class="table-content">
    
              <DataGrid
                :columns="this.options.cols_grid"
                v-bind:dataSource="this.options.gridBaiThi"
                :showColumnCheckbox="false"
                :enable-paging-server="false"
                :allowPaging="false"
                :showFilter="true"
                :enabledSelectFirstRow="true"
                @rowSelected="rowSelected"
                @queryCellInfo="customiseCell"
              >
              </DataGrid>

            </div>
</div>
          </div>
        </div>

        <div class="col-sm-6 col-12">
          <div class="box-form" style="height: 100%">
            <div class="legend-title">Chi tiết bài thi</div>
            <div class="row">
              <div v-html="htmlContent"></div>
            </div>

          </div>
        </div>
      </div>
    </div>


    <PopupDuyetBaiThi
      ref="popup_DuyetBaiThi"
      :vtonghop_id="this.vtonghop_id"
    />



  </div>
</template>
<script>
import PopupDuyetBaiThi from "./PopupDuyetBaiThi.vue";
import VueInput from "../../../search/subscriber/components/form/VueInput.vue";
import api from "./api";
import define from "./define";
import moment from "moment";
import VueCheckbox from '../../../search/subscriber/components/form/VueCheckbox.vue';
import {DataManager, ODataAdaptor, Predicate, Query} from '@syncfusion/ej2-data';
import {
  Sort,
  Filter,
  Resize,
  Group,
  Freeze,
  CommandColumn,
  ExcelExport,
  Edit,
  Aggregate,
  Page,
  Toolbar,
} from "@syncfusion/ej2-vue-grids";
export default {
  components: { VueInput, VueCheckbox,PopupDuyetBaiThi },
  provide: {
    grid: [
      Sort,
      Filter,
      Resize,
      Group,
      Freeze,
      CommandColumn,
      ExcelExport,
      Edit,
      Aggregate,
      Toolbar,
      Page,
      CommandColumn,
    ],
  },

  data() {
    return {
      ...define,
      cbbTrangThaiPhieu:"",
      cboChuDe:0,
      vtonghop_id:0,
      vten_nv:"",
      htmlContent:"",
      dtpTuNgay: moment(new Date()).format("DD/MM/YYYY"),
      dtpDenNgay: moment(new Date(),"DD/MM/YYYY").add(1,'d').format("DD/MM/YYYY"),
      options: {
        ttphieu: [],
        chude: [],
        gridBaiThi:[],

        cols_grid:[
        {
            fieldName: "ngaythi",
            allowFiltering: true,
            textAlign: "left",
            allowSorting: true,
            isGroupedColumn: true,

        },
        {
            fieldName: "dethi",
            allowFiltering: true,
            textAlign: "left",
            allowSorting: true,
            isGroupedColumn: true,

        },        
        {
            fieldName: "ma_nv",
            headerText: "Mã NV",
            allowFiltering: true,
            textAlign: "left",
            allowSorting: true,
            width:120
        },
        {
            fieldName: "ten_nv",
            headerText: "Tên NV",
            allowFiltering: true,
            textAlign: "left",
            allowSorting: true,
            width:120
        },
        {
            fieldName: "ngay_kt_thi",
            headerText: "Ngày hoàn thành",
            allowFiltering: true,
            textAlign: "left",
            allowSorting: true,
            width:120
        },
        {
            fieldName: "trangthai",
            headerText: "Trạng thái bài thi",
            allowFiltering: true,
            textAlign: "left",
            allowSorting: true,
            width:120
        },
        {
            fieldName: "ketqua",
            headerText: "Trả lời đúng",
            allowFiltering: true,
            textAlign: "left",
            allowSorting: true,
            width:120
        },
        {
            fieldName: "trangthai_duyet",
            headerText: "Trạng thái duyệt",
            allowFiltering: true,
            textAlign: "left",
            allowSorting: true,
        },
        {
            fieldName: "nguoi_duyet",
            headerText: "Người duyệt",
            allowFiltering: true,
            textAlign: "left",
            allowSorting: true,
        },
        {
            fieldName: "ngay_duyet",
            headerText: "Ngày duyệt",
            allowFiltering: true,
            textAlign: "left",
            allowSorting: true,
        },
        {
            fieldName: "noidung_duyet",
            headerText: "Nội dung duyệt",
            allowFiltering: true,
            textAlign: "left",
            allowSorting: true,
        }



        ]
      },
    }
  },


  created() {
    this.getDanhMuc();
    this.getTrangThai();
  },
  watch: {

  },
  props: {
    value: Object,
  },

  computed:{
    
  },

  mounted(){
    
  },


  methods: {
    

    customiseCell: function (args) {
      if(args.data.trangthai == 'Trượt')
      {
        args.cell.setAttribute('style', 'background-color: #FF9966;')
      }
      if(args.data.trangthai == 'Thi đạt')
      {
        args.cell.setAttribute('style', 'background-color: #00FF00;')
      }
    },


  async getDanhMuc()
  {
      const res = await api.danhmuc(this.axios, {
        "vparam1": "2",
        "vkieu": 2    
      })
      let ds = res.data.data.chude1 ? res.data.data.chude1 : []; 
      let items = [];
      ds.forEach(function (item) {
        items.push({ id: item.chude_id, text: item.chude });
      });
      this.options.chude = items;
      this.cboChuDe = 18
  },

  async getTrangThai()
  {
      let items = [];
      items.push({ id: -1, text: '---Tất cả---' });
      items.push({ id: 0, text: 'Chưa thi' });
      items.push({ id: 1, text: 'Bỏ thi' });
      items.push({ id: 2, text: 'Đang tạm dừng' });
      items.push({ id: 3, text: 'Thi đạt' });
      items.push({ id: 4, text: 'Trượt' });
      this.options.ttphieu = items;
      this.cbbTrangThaiPhieu = -1
  },

  async TraCuu()
  {
    if (this.cboChuDe <= 0) {
      this.$toast.error("Chưa chọn chủ đề!");
      return;
    }
    this.loading(true);
    let vtungay = moment(this.dtpTuNgay,'DD/MM/YYYY').format('DDMMYYYY')
    let vdenngay = moment(this.dtpDenNgay,'DD/MM/YYYY').format('DDMMYYYY')

    console.log("vnhanvien_ql_id", this.$root.token.getNhanVienID(),this.cboChuDe,vtungay,vdenngay)


    const res = await api.lay_ds_baithi_theo_nvql(this.axios, {
        "vnhanvien_ql_id": this.$root.token.getNhanVienID(),
        "vchude_id": this.cboChuDe,    
        "vtungay": vtungay,    
        "vdenngay": vdenngay
        // "vnhanvien_ql_id": 368965,
        // "vchude_id": 18,
        // "vtungay": "05032023",
        // "vdenngay": "11032023"

      })
    
    console.log('res.data.error_code',res.data.error_code)
    if(res.data.error_code == "BSS-00000000")
    {
      this.options.gridBaiThi = res.data.data ? res.data.data : []; 
      this.loading(false);

    }
    else
    {
      this.$toast.error(res.data.message_detail);
      this.loading(false);
      return;
    }



  },

  async rowSelected(subject) 
  {
    let status = subject.data.trangthai
    this.vtonghop_id = subject.data.tonghop_id
    this.vten_nv = subject.data.ten_nv
    if(status == "Thi đạt" || status == "Đang tạm dừng" || status == "Trượt")
    {
      const res = await api.fn_lay_ketqua_baithi_html(this.axios, {}, this.vtonghop_id)
      this.htmlContent = res.data.data ? res.data.data : ''
    }
    else
    {
      this.htmlContent = ''
    }

    if(status == "Trượt")
    {
      this.button.duyet = true;
    }
    else
    {
      this.button.duyet = false;
    }
    
  },



  clickButton(key) {
    if (key == "duyet") {
        if(this.vtonghop_id < 0)
        {
          return
        }
        else
        {
          if(this.button.duyet == true)
          {

            this.$confirm(`Xét duyệt cho phép nhân viên ` + this.vten_nv + ` tiếp tục tác nghiệp trên mobile app?`, {
            confirmButtonText: "Có",
            cancelButtonText: "Không",
            type: "warning",
            }).then(async () => {

                //show popup
                this.$refs.popup_DuyetBaiThi.showModal();

            })


          }
        }

    }

    },
  },
};
</script>
<style>
li.non-active a {
  color: #d3d3d3 !important;
}

@import "https://cdn.syncfusion.com/ej2/material.css";
</style>