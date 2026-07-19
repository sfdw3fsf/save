<template>
  <div>
    <vue-bread-crumb :header="header" />
    <vue-nav>
      <ul class="list">

        <li v-if="this.button.layds"
          @click="clickButton('layds')"
          :class="this.button.layds ? 'active' : 'non-active'"
          
        >
          <a> <span class="icon one-file-attach"></span>Lấy danh sách </a>
        </li>

        <li v-if="this.button.laydsphieu"
          @click="clickButton('laydsphieu')"
          :class="this.button.laydsphieu ? 'active' : 'non-active'"
        >
          <a> <span class="icon one-file-attach"></span>Lấy ds phiếu </a>
        </li>        
 
        <li v-if="this.button.laytt"
          @click="clickButton('laytt')"
          :class="this.button.laytt ? 'active' : 'non-active'"
        >
          <a> <span class="icon one-file-attach"></span>Lấy TT </a>
        </li>
        <li
          @click="clickButton('capnhat')"
          :class="this.button.capnhat ? 'active' : 'non-active'"
        >
          <a> <span class="icon one-reload1"></span>Cập nhật</a>
        </li>
      </ul>
    </vue-nav>
    <div class="page-content" style="margin-top:-10px">
            <div class="nav tabs tab-over">
                <a href="#tab1" data-toggle="tab" @click="functionClick(1)" class="active" >Danh sách nhân viên</a>
                <a href="#tab2" data-toggle="tab" @click="functionClick(2)">Phiếu cấu hình cá nhân</a>
                <a href="#tab3" data-toggle="tab" @click="functionClick(3)">Thống kê phiếu cấu hình</a>
            </div>
            <div class="tab-content">
                <div class="tab-pane active" id="tab1">
                    <div class="row">
                        <div class="col-sm-6 col-12">
                            <div class="info-row">
                                <div class="key w130">Người dùng</div>
                                <div class="value">
                                    <input type="text" v-model="txtNguoiDung" :disabled="true"  class="form-control">
                                </div>
                            </div>
                            <div class="info-row">
                                <div class="key w130">Ca trực</div>
                                <div class="value">
                                    <div class="select-custom">
                                            <SelectExt
                                                v-model="ddlCaTruc"
                                                :options="this.options.catruc"
                                            >
                                                <option disabled value="0">Select one</option>
                                            </SelectExt>
                                    </div>
                                </div>
                            </div>
                            <div class="info-row">
                                <div class="key w130">Nhận phiếu cấu hình</div>
                                <div class="value">
                                    <div class="select-custom">
                                            <SelectExt
                                                v-model="ddlNhanPhieuCauHinh"
                                                :options="this.options.nhanphieucauhinh"
                                            >
                                                <option disabled value="0">Select one</option>
                                            </SelectExt>
                                    </div>
                                </div>
                            </div>
                            <div class="info-row">
                                <div class="key w130">Chênh lệch phiếu</div>
                                <div class="value">
                                    <input type="text" v-model="txtChenhLechPhieu" class="form-control">
                                </div>
                            </div>
                        </div>
                        <div class="col-sm-6 col-12">
                            <div class="info-row">
                                <div class="key w80">Loại mạng</div>
                                <div class="value">
                                    <div class="select-custom">

                                    <ejs-multiselect 
                                    id='multiselect' 
                                    v-model="ddlLoaiMang"
                                    :dataSource='this.options.loaimang'  
                                    mode="CheckBox" 
                                    :fields='this.fields_loaimang' 
                                    :showSelectAll='showSelectAll' 
                                    :isChecked='isChecked'
                                    selectAllText="Chọn tất cả" 
                                    unSelectAllText="Bỏ chọn tất cả"
                                    ></ejs-multiselect>

                                    </div>
                                </div>
                            </div>
                            <div class="info-row">
                                <div class="key w80">Ghi chú</div>
                                <div class="value">
                                    <textarea name="" v-model="txtGhiChu" class="form-control" style="height: 110px;resize: none;"></textarea>
                                </div>
                            </div>
                            
                        </div>
                    </div>
                    <div class="table-content">
                            <DataGrid
                                :columns="this.options.cols_ds_nv"
                                v-bind:dataSource="options.grid_nhanvien"
                                :showColumnCheckbox="false"
                                :enable-paging-server="false"
                                :allowPaging="true"
                                :showFilter="true"
                                :enabledSelectFirstRow="false"
                                @rowSelected="grid_RowSelected"
                            >
                            </DataGrid>
                    </div>

                </div>
                <div class="tab-pane" id="tab2">
                    <div class="row">
                        <div class="col-sm-4 col-12">
                            <div class="info-row">
                                <div class="key">Người thực hiện</div>
                                <div class="value">
                                    <div class="select-custom">
                                            <SelectExt
                                                v-model="ddlNguoiThucHien"
                                                :options="this.options.nguoithuchien"
                                            >
                                                <option disabled value="0">Select one</option>
                                            </SelectExt>
                                    </div>
                                </div>
                            </div>
                            
                        </div>
                        <div class="col-sm-8 col-12">
                            <div class="info-row">
                                <div class="key w80">Loại hình</div>
                                <div class="value">
                                    <div class="select-custom">
                                            <SelectExt
                                                v-model="ddlLoaiHinh"
                                                :options="this.options.loaihinh"
                                            >
                                                <option disabled value="0">Select one</option>
                                            </SelectExt>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-sm-4 col-12">
                            <div class="info-row">
                                <div class="key">Trạng thái</div>
                                <div class="value">
                                    <div class="select-custom">
                                            <SelectExt
                                                v-model="ddlTrangThai"
                                                :options="this.options.trangthai"
                                            >
                                                <option disabled value="0">Select one</option>
                                            </SelectExt>
                                    </div>
                                </div>
                            </div>
                            
                        </div>
                        <div class="col-sm-4 col-12">
                            <div class="info-row">
                                <div class="key w80">Từ ngày</div>
                                <div class="value">
                                        <vue-date
                                            format="DD/MM/YYYY"
                                            type="datetime"
                                            v-model="txtTuNgay_canhan"
                                        ></vue-date>    
                                </div>
                            </div>
                        </div>
                        <div class="col-sm-4 col-12">
                            <div class="info-row">
                                <div class="key w80">Đến ngày</div>
                                <div class="value">
                                        <vue-date
                                            format="DD/MM/YYYY"
                                            type="datetime"
                                            v-model="txtDenNgay_canhan"
                                        ></vue-date> 
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="table-content">
                            <DataGrid
                                :columns="this.options.cols_ds_canhan"
                                v-bind:dataSource="options.grid_canhan"
                                :showColumnCheckbox="false"
                                :enable-paging-server="false"
                                :allowPaging="true"
                                :showFilter="true"
                                :enabledSelectFirstRow="false"
                            >
                            </DataGrid>
                    </div>

                </div>
                <div class="tab-pane" id="tab3">
                    <div class="row">
                        <div class="col-sm-6 col-12">
                            <div class="info-row">
                                <div class="key w70">Từ ngày</div>
                                <div class="value">
                                        <vue-date
                                            format="DD/MM/YYYY"
                                            type="datetime"
                                            v-model="txtTuNgay_cauhinh"
                                        ></vue-date>  
                                </div>
                            </div>
                            
                        </div>
                        <div class="col-sm-6 col-12">
                            <div class="info-row">
                                <div class="key w70">Đến ngày</div>
                                <div class="value">
                                        <vue-date
                                            format="DD/MM/YYYY"
                                            type="datetime"
                                            v-model="txtDenNgay_cauhinh"
                                        ></vue-date>  
                                </div>
                            </div>
                            
                        </div>
                        
                    </div>
                    
                    <div class="table-content">
                            <DataGrid
                                :columns="this.options.cols_ds_cauhinh"
                                v-bind:dataSource="options.grid_cauhinh"
                                :showColumnCheckbox="false"
                                :enable-paging-server="false"
                                :allowPaging="true"
                                :showFilter="true"
                                :enabledSelectFirstRow="false"
                            >
                            </DataGrid>


                    </div>

                </div>
            </div>
    </div>
  </div>
</template>
<script>
import Vue from 'vue';
import api from "./api";
import define from "./define";
import moment from "moment";
import { MultiSelectPlugin } from "@syncfusion/ej2-vue-dropdowns";
import { MultiSelect, CheckBoxSelection } from '@syncfusion/ej2-dropdowns';
MultiSelect.Inject(CheckBoxSelection);
Vue.use(MultiSelectPlugin);

export default {

  data() {
    return {
      ...define,
      txtNguoiDung:"",
      ddlCaTruc:"",
      ddlNhanPhieuCauHinh:"",
      txtChenhLechPhieu:"",
      ddlLoaiMang:[],
      txtGhiChu:"",
ddlNguoiThucHien:"",
ddlLoaiHinh:"",
ddlTrangThai:"",
txtTuNgay_canhan: moment(new Date()).format('DD/MM/YYYY'),
txtDenNgay_canhan:moment(new Date()).format('DD/MM/YYYY'),
      txtTuNgay_cauhinh:moment(new Date()).format('DD/MM/YYYY'),
      txtDenNgay_cauhinh:moment(new Date()).format('DD/MM/YYYY'),

fields_loaimang : { text: 'text', value: 'id' },
showSelectAll : true,
isChecked:true,

      options: {
        catruc: [],
        nhanphieucauhinh:[],
        loaimang:[],
nguoithuchien:[],
loaihinh:[],
trangthai:[],
        grid_nhanvien:[],
        cols_ds_nv: [
          {
            fieldName: "ma_nv",
            headerText: "Mã nhân viên",
            allowSorting: true,
            autoFit: true,
          },
          {
            fieldName: "ten_nv",
            headerText: "Tên nhân viên",
            allowSorting: true,
            autoFit: true,
          },
          {
            fieldName: "ca_truc",
            headerText: "Ca trực",
            allowSorting: true,
            autoFit: true,
          },
          {
            fieldName: "ten_trang_thai",
            headerText: "Nhận phiếu cấu hình",
            textAlign: "left",
            allowSorting: true,
            autoFit: true,
          },
          {
            fieldName: "chenh_lech",
            headerText: "Chênh lệch",
            textAlign: "left",
            allowSorting: true,
            autoFit: true,
          },
          {
            fieldName: "loai_mang",
            headerText: "Mã loại mạng cấu hình",
            textAlign: "left",
            allowSorting: true,
            autoFit: true,
          },
          {
            fieldName: "ghichu",
            headerText: "Ghi chú",
            textAlign: "left",
            allowSorting: true,
            autoFit: true,
          }
        ],


        grid_canhan:[],
        cols_ds_canhan: [
          {
            fieldName: "ma_tb",
            headerText: "Mã thuê bao",
            allowSorting: true,
            autoFit: true,
          },
          {
            fieldName: "quytrinh",
            headerText: "Quy trình",
            allowSorting: true,
            autoFit: true,
          },
          {
            fieldName: "ten_nv_giao",
            headerText: "Người giao",
            allowSorting: true,
            autoFit: true,
          },

    {
    fieldName: 'trang_thai',
    headerText: 'Đã thực hiện',
    allowFiltering: true,
    allowSorting: false,
    textAlign: 'Center',
        template: function () {
            return {
            template: Vue.component('xoaLyDoHuyTemplate', {
                  template: `<a class="btn btn-huylydo lh14" @click="HoanThien"
                                style="background-color: #3684e0 !important; padding: 5px 15px; color: white">
                  <span v-bind:class="[this.data.trang_thai==1?'icon one-check green-icon':'icon nc-icon-glyph ui-1_simple-remove']"></span></a>`,
                data () {
                return {
                  data: {},

                  }
                },
                computed: {
                  parent () {
                      return this.$parent.$parent.$parent
                  }
                },
                props: {

                },                
                methods: {
                    async HoanThien() {

                      if(this.data.trang_thai == 1)
                      {
                          this.$confirm(`Bạn có muốn HỦY hoàn thiện phiếu cấu hình đang chọn không ?`, {
                              confirmButtonText: "Có",
                              cancelButtonText: "Không",
                              type: "warning",
                              }).then(async () => {
                                    try {

                                        if(this.data.nhanvien_th_id != this.$auth.getNhanVienID())
                                        {
                                          this.$toast.error("Bạn không thể Hủy hoàn thiện phiếu của người khác !");
                                        }
                                        else
                                        {
                                            this.loading(true);
                                            let ghichu = ""
                                            if(this.data.ghichu != null)
                                            {
                                              ghichu = this.data.ghichu
                                            }
                                            let apiBody = {
                                                "ghichu": ghichu,
                                                "nhanvien_id": this.$auth.getNhanVienID(),
                                                "phieu_id": this.data.phieu_id,
                                                "ttph_id": 1                                      
                                            };
                                            //console.log('apiBody',apiBody)
                                            let response = await api.update_trangthai_phieu_omc(this.axios, apiBody);
                                            let kq =  response.data.error_code ? response.data.error_code : 1
                                            if(kq != "BSS-00000000")
                                            {
                                                
                                              this.$toast.error("Có lỗi khi hủy hoàn thiện phiếu: " + response.data.message_detail);
                                              const res = await api.LayDS_CauHinh_CaNhan(this.axios, {
                                                      "vnhanvien_id": this.$auth.getNhanVienID(),
                                                      "vttph_id":this.$parent.$parent.$parent.ddlTrangThai,
                                                      "vtu_ngay":this.$parent.$parent.$parent.txtTuNgay_canhan,
                                                      "vden_ngay":this.$parent.$parent.$parent.txtDenNgay_canhan,
                                                      "vloaitb_id": this.$parent.$parent.$parent.ddlLoaiHinh
                                                })

                                                this.$parent.$parent.$parent.options.grid_canhan = res.data.data ? res.data.data : []
                                                for(var i =0; i< this.$parent.$parent.$parent.options.grid_canhan.length; i++)
                                                {
                                                    if(this.options.grid_canhan[i].ngaygiao != null)
                                                    {
                                                      this.options.grid_canhan[i].ngaygiao = moment(this.options.grid_canhan[i].ngaygiao).format('DD/MM/YYYY')
                                                    }
                                                    if(this.options.grid_canhan[i].ngay_th != null)
                                                    {
                                                      this.options.grid_canhan[i].ngay_th = moment(this.options.grid_canhan[i].ngay_th).format('DD/MM/YYYY h:mm:ss A')
                                                    } 
                                                }


                                            }
                                            else
                                            {
            
                                              const res = await api.LayDS_CauHinh_CaNhan(this.axios, {
                                                      "vnhanvien_id": this.$parent.$parent.$parent.$auth.getNhanVienID(),
                                                      "vttph_id":this.$parent.$parent.$parent.ddlTrangThai,
                                                      "vtu_ngay":this.$parent.$parent.$parent.txtTuNgay_canhan,
                                                      "vden_ngay":this.$parent.$parent.$parent.txtDenNgay_canhan,
                                                      "vloaitb_id": this.$parent.$parent.$parent.ddlLoaiHinh
                                                })

                                                this.$parent.$parent.$parent.options.grid_canhan = res.data.data ? res.data.data : []
                                                for(var i =0; i< this.$parent.$parent.$parent.options.grid_canhan.length; i++)
                                                {
                                                    if(this.options.grid_canhan[i].ngaygiao != null)
                                                    {
                                                      this.options.grid_canhan[i].ngaygiao = moment(this.options.grid_canhan[i].ngaygiao).format('DD/MM/YYYY')
                                                    }
                                                    if(this.options.grid_canhan[i].ngay_th != null)
                                                    {
                                                      this.options.grid_canhan[i].ngay_th = moment(this.options.grid_canhan[i].ngay_th).format('DD/MM/YYYY h:mm:ss A')
                                                    } 
                                                }
                                            }
                                        }

                                    }
                                    catch (error) {
                                        this.$toast.error('Có lỗi khi hủy hoàn thiện phiếu! ' + error)
                                    }
                                    finally {
                                        this.loading(false);
                                    }

                              })
                      }
                      else
                      {
                        this.$confirm(`Bạn có muốn hoàn thiện phiếu cấu hình đang chọn không ?`, {
                            confirmButtonText: "Có",
                            cancelButtonText: "Không",
                            type: "warning",
                            }).then(async () => {
                                  try {
                                      this.loading(true);
                                      let ghichu = "Đã xử lý"
                                      if(this.data.ghichu != null)
                                      {
                                        ghichu = this.data.ghichu
                                      }
                                      let apiBody = {
                                          "ghichu": ghichu,
                                          "nhanvien_id": this.$auth.getNhanVienID(),
                                          "phieu_id": this.data.phieu_id,
                                          "ttph_id": 4                                        
                                      };
                                      console.log('apiBody',apiBody)
                                      let response = await api.update_trangthai_phieu_omc(this.axios, apiBody);
                                      let kq =  response.data.error_code ? response.data.error_code : 1
                                      if(kq != "BSS-00000000")
                                      {
                                         this.$toast.error("Có lỗi khi hoàn thiện phiếu: " + response.data.message_detail);
                                               const res = await api.LayDS_CauHinh_CaNhan(this.axios, {
                                                    "vnhanvien_id": this.$auth.getNhanVienID(),
                                                    "vttph_id":this.$parent.$parent.$parent.ddlTrangThai,
                                                    "vtu_ngay":this.$parent.$parent.$parent.txtTuNgay_canhan,
                                                    "vden_ngay":this.$parent.$parent.$parent.txtDenNgay_canhan,
                                                    "vloaitb_id": this.$parent.$parent.$parent.ddlLoaiHinh
                                              })

                                              this.$parent.$parent.$parent.options.grid_canhan = res.data.data ? res.data.data : []
                                                for(var i =0; i< this.$parent.$parent.$parent.options.grid_canhan.length; i++)
                                                {
                                                  if(this.options.grid_canhan[i].ngaygiao != null)
                                                  {
                                                    this.options.grid_canhan[i].ngaygiao = moment(this.options.grid_canhan[i].ngaygiao).format('DD/MM/YYYY')
                                                  }
                                                  if(this.options.grid_canhan[i].ngay_th != null)
                                                  {
                                                    this.options.grid_canhan[i].ngay_th = moment(this.options.grid_canhan[i].ngay_th).format('DD/MM/YYYY h:mm:ss A')
                                                  } 
                                                }
                                      }
                                      else
                                      {
                                              const res = await api.LayDS_CauHinh_CaNhan(this.axios, {
                                                    "vnhanvien_id": this.$auth.getNhanVienID(),
                                                    "vttph_id":this.$parent.$parent.$parent.ddlTrangThai,
                                                    "vtu_ngay":this.$parent.$parent.$parent.txtTuNgay_canhan,
                                                    "vden_ngay":this.$parent.$parent.$parent.txtDenNgay_canhan,
                                                    "vloaitb_id": this.$parent.$parent.$parent.ddlLoaiHinh
                                              })

                                              this.$parent.$parent.$parent.options.grid_canhan = res.data.data ? res.data.data : []
                                              for(var i =0; i< this.$parent.$parent.$parent.options.grid_canhan.length; i++)
                                              {
                                                  if(this.options.grid_canhan[i].ngaygiao != null)
                                                  {
                                                    this.options.grid_canhan[i].ngaygiao = moment(this.options.grid_canhan[i].ngaygiao).format('DD/MM/YYYY')
                                                  }
                                                  if(this.options.grid_canhan[i].ngay_th != null)
                                                  {
                                                    this.options.grid_canhan[i].ngay_th = moment(this.options.grid_canhan[i].ngay_th).format('DD/MM/YYYY h:mm:ss A')
                                                  } 
                                              }                                          
                                      }
 
                                  }
                                  catch (error) {
                                      this.$toast.error('Có lỗi khi hoàn thiện phiếu! ' + error)
                                  }
                                  finally {
                                      this.loading(false);
                                  }

                            })
                      }

                    }




                }
            })
            }
        }
    },

          {
            fieldName: "ngaygiao",
            headerText: "Ngày giao",
            textAlign: "left",
            allowSorting: true,
            autoFit: true,
          },
          {
            fieldName: "ten_nv_th",
            headerText: "Người thực hiện",
            textAlign: "left",
            allowSorting: true,
            autoFit: true,
          },
          {
            fieldName: "ngay_th",
            headerText: "Ngày TH",
            textAlign: "left",
            allowSorting: true,
            autoFit: true,
          },
          {
            fieldName: "ghichu",
            headerText: "Ghi chú",
            textAlign: "left",
            allowSorting: true,
            autoFit: true,
          }          
        ],

        grid_cauhinh:[],
        cols_ds_cauhinh: [
          {
            fieldName: "ten_nv_th",
            headerText: "Người thực hiện",
            textAlign: "left",
            allowSorting: true,
            autoFit: true,
          }    
        ],



bien_kieu:"",

      },

    };
  },
  created() {
    this.get_CaTruc()
    this.get_NhanPhieuCauHinh()
    this.get_LoaiMang()
    this.get_TrangThai() 
    this.get_NguoiThucHien()
    this.get_NhanvienNhanPhieu()
    this.functionClick(1)
    this.Lay_TT_ND()
  },
  watch: {

  },

  methods: {
    functionClick(kieu){
        if(kieu == 1)
        {
          this.button.laytt = true
          this.button.layds = false
          this.button.laydsphieu = false
          this.bien_kieu = 1
        }

        if(kieu == 2)
        {
          this.button.laytt = false
          this.button.layds = false          
          this.button.laydsphieu = true
          this.bien_kieu = 2
        }

        if(kieu == 3)
        {
          this.button.laytt = false       
          this.button.laydsphieu = false          
          this.button.layds = true
          this.bien_kieu = 3
        }

    },
    async get_CaTruc() {

      const res = await api.get_CaTruc(this.axios, {
          "vma_nhom_loai": "CA_TRUC"
      })
      let kq = res.data.data.returnds ? res.data.data.returnds : []

      let items = []

      kq.forEach(function (item) {
        if(item.ten_loai != null)
        {
          items.push({ id: item.ma_loai, text: item.ten_loai  })
        }
      });
  
      this.options.catruc = items;
      this.ddlCaTruc = items[0].id;


      
    },  

    async get_NhanPhieuCauHinh() {

      const res = await api.get_NhanPhieuCauHinh(this.axios, {
          "vma_nhom_loai": "TRANG_THAI"
      })
      let kq = res.data.data.returnds ? res.data.data.returnds : []


      let items = []
      kq.forEach(function (item) {
          items.push({ id: item.ma_loai, text: item.ten_loai });
        })


      this.options.nhanphieucauhinh = items;
      this.ddlNhanPhieuCauHinh = items[0].id;

    },  

    async get_LoaiMang() {

      const res = await api.get_LoaiMang(this.axios, {

      })
      let kq = res.data.data ? res.data.data : []
      let items = []
      kq.forEach(function (item) {
          items.push({ id: item.LOAITB_ID, text: item.LOAIHINH_TB });
        })
      this.options.loaimang = items;

        //this.ddlLoaiMang = items.map(a => a.id)


        this.options.loaihinh = items;
        this.options.loaihinh.push({ id: -1, text: 'Tất cả' });
        this.ddlLoaiHinh = -1;        


    },  

    async get_TrangThai() {


      const res = await api.get_TrangThai(this.axios, {
        "vma_nhom_loai": "TRANG_THAI_PH"
      })
      let kq = res.data.data.returnds ? res.data.data.returnds : []
      let items = []
      kq.forEach(function (item) {
          items.push({ id: item.ma_loai, text: item.ten_loai });
        })
      this.options.trangthai = items;
        this.ddlTrangThai = items[0].id;

    },  

    async get_NguoiThucHien() {
      const res = await api.get_NguoiThucHien(this.axios, {
        "vdonvi_id": this.$root.token.getDonViID()
      })
      let kq = res.data.data ? res.data.data : []
      let items = []
      kq.forEach(function (item) {
          items.push({ id: item.nhanvien_id, text: item.ten_nv });
        })
      this.options.nguoithuchien = items;
        this.ddlNguoiThucHien = this.$root.token.getNhanVienID();

    },  

    async get_NhanvienNhanPhieu()
    {  
      const res = await api.get_NhanvienNhanPhieu(this.axios, {
      })
      this.options.grid_nhanvien = res.data.data ? res.data.data : []
    },

    async Lay_TT_ND()
    {  
      const res = await api.Lay_TT_ND(this.axios, {
      })
      this.txtNguoiDung = res.data.data.ten_nd;
    },


    async CapNhat(kieu)
    {
        var lm = ''
        this.ddlLoaiMang.forEach(function (item) {
          lm = lm + item + ','
        });
        lm = lm.substring(0,lm.length-1)



      const res = await api.CapNhat(this.axios, {
        "vnhanvien_id":this.$root.token.getNhanVienID(),
        "vca_truc":this.ddlCaTruc,
        "vtrang_thai":this.ddlTrangThai,
        "vchenh_lech":this.txtChenhLechPhieu,
        "vds_loaihinhtb_id": lm,
        "vghichu": this.txtGhiChu
      })
      let kq = res.data.data ? res.data.data : 0
      if(kq == 1)
      {
        if(kieu == 1)
        {
          this.get_NhanvienNhanPhieu()
        }
        if(kieu == 2)
        {
          this.LayDS_CauHinh_CaNhan()
        }
        if(kieu == 3)
        {
          this.LayDS()
        }
        this.$toast.success("Cập nhật thông tin người dùng nhận phiếu thành công!");
      }
      else
      {
          this.$toast.error("Có lỗi trong quá trình cập nhật, chi tiết: " + kq);
      }

    },

    async LayDS_CauHinh_CaNhan()
    {  
      console.log( "vnhanvien_id",this.$root.token.getNhanVienID(),
            "vttph_id",this.ddlTrangThai,
            "vtu_ngay",this.txtTuNgay_canhan,
            "vden_ngay",this.txtDenNgay_canhan,
            "vloaitb_id", this.ddlLoaiHinh)

      const res = await api.LayDS_CauHinh_CaNhan(this.axios, {
            "vnhanvien_id":this.$root.token.getNhanVienID(),
            "vttph_id":this.ddlTrangThai,
            "vtu_ngay":this.txtTuNgay_canhan,
            "vden_ngay":this.txtDenNgay_canhan,
            "vloaitb_id": this.ddlLoaiHinh
      })

      this.options.grid_canhan = res.data.data ? res.data.data : []

      for(var i =0; i< this.options.grid_canhan.length; i++)
      {
        if(this.options.grid_canhan[i].ngaygiao != null)
        {
          this.options.grid_canhan[i].ngaygiao = moment(this.options.grid_canhan[i].ngaygiao).format('DD/MM/YYYY')
        }
        if(this.options.grid_canhan[i].ngay_th != null)
        {
          this.options.grid_canhan[i].ngay_th = moment(this.options.grid_canhan[i].ngay_th).format('DD/MM/YYYY h:mm:ss A')
        }        

      }

    },

    async LayDS()
    {  
      const res = await api.LayDS(this.axios, {
            "vtungay":this.txtTuNgay_cauhinh,
            "vdenngay":this.txtDenNgay_cauhinh
      })
      this.options.grid_cauhinh = res.data.data.tbl1 ? res.data.data.tbl1 : []

    },

async grid_RowSelected(data)
{
    // this.ddlCaTruc = data.data.ca_truc
    // this.ddlNhanPhieuCauHinh = data.data.trang_thai
    // this.ddlLoaiMang =  data.data.loai_mang
    // this.txtChenhLechPhieu =  data.data.chenh_lech
    // this.txtGhiChu =  data.data.ghichu
},



    clickButton(key) {
      if (key == "layds") {
          this.LayDS()
      }
      if (key == "laydsphieu") {

        this.LayDS_CauHinh_CaNhan()
      }
      if (key == "laytt") {
          this.get_NhanvienNhanPhieu()
      }
      if (key == "capnhat") {
          this.CapNhat(this.bien_kieu)
      }

    }


  },
};
</script>
<style>
li.non-active a {
  color: #d3d3d3 !important;
}
@import "https://cdn.syncfusion.com/ej2/material.css";
</style>