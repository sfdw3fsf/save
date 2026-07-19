<template>
  <div class="modal-content popup-box">
                
                <div class="list-actions-top">
                    <ul class="list">
                
                        <li>
                            <a href="#" @click.prevent="btnThemMoi">
                                <span class="icon nc-icon-glyph ui-1_circle-add"></span>Thêm (F9)
                            </a>
                        </li>
                
                        <li>
                            <a href="#" @click.prevent="btnXoa">
                                <span class="icon one-trash"></span>Xóa (F8)
                            </a>
                        </li>
                        
                    </ul>
                </div>
                <div class="popup-body">
                    <div class="box-form">
                        <div class="legend-title">Thông tin dụ án IMS</div>
                       
                        <div class="info-row">
                            <div class="key w100">Tòa nhà</div>
                            <div class="value">
                                <div class="select-custom">
                                    <SelectExt :options="toanha.list" v-model="toanha.value.id" @change = "toanhachange" :disabled="true"> 
                                      <option disabled value="0">Select one</option> 
                                    </SelectExt> 
                                </div>
                            </div>
                        </div>
                        <div class="info-row">
                            <div class="key w100">Dự án IMS</div>
                            <div class="value">
                                <div class="select-custom">
                                    <SelectExt :options="duanims.list" v-model="duanims.value.id" > 
                                      <option disabled value="0">Select one</option> 
                                    </SelectExt> 
                                    
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-sm-3 col-12">
                                <div class="info-row">
                                    <div class="key w100">Mã dự án</div>
                                    <div class="value">
                                        <input type="text" v-model = "maduan" ref ="maduan" class="form-control highlight">
                                    </div>
                                </div>
                            </div>
                            <div class="col-sm-3 col-12">
                                <div class="info-row">
                                    <div class="key w100">Tên dự án</div>
                                    <div class="value">
                                        <input type="text" v-model = "tenduan" class="form-control highlight">
                                    </div>
                                </div>
                            </div>
                            <div class="col-sm-3 col-12">
                                <div class="info-row">
                                    <div class="key w100">Chi phí dự kiến</div>
                                    <vue-numeric class="form-control tright" separator=","
                                        v-model= "chiphidukien"  id="chiphidukien" ref ="chiphidukien" :readonly="false" placeholder="0" output-type="Number">
                                    </vue-numeric>
                                </div>
                            </div>
                            <div class="col-sm-3 col-12">
                                <div class="info-row">
                                    <div class="key w100">Chi phí (VAT)</div>
                                    <vue-numeric class="form-control tright" separator=","
                                        v-model= "chiphivat"  id="chiphivat" ref ="chiphivat" :readonly="false" placeholder="0" output-type="Number">
                                    </vue-numeric>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-sm-6 col-12">
                                <div class="info-row">
                                    <div class="key w100">Số hiệu dự án</div>
                                    <div class="value">
                                        <input type="text" v-model = "sohieuduan" ref ="sohieuduan" class="form-control highlight">
                                    </div>
                                </div>
                            </div>
                            <div class="col-sm-6 col-12">
                                <div class="info-row">
                                    <div class="key w100">Ban quản lý</div>
                                    <div class="value">
                                        <input type="text" v-model = "banquanly" ref ="banquanly" class="form-control highlight">
                                    </div>
                                </div>
                            </div>
                            
                        </div>
                        <div class="info-row">
                            <div class="key w100">Địa chỉ</div>
                            <div class="value">
                                <input type="text" v-model = "diachi" ref ="diachi" class="form-control highlight">
                            </div>
                        </div>
                        <div class="info-row">
                            <div class="key w100">Mô tả dự án</div>
                            <div class="value">
                                <textarea name="" v-model = "motaduan" ref ="motaduan" class="form-control highlight" style="height: 100px;resize: none;"></textarea>
                            </div>
                        </div>
                    </div>
                    <div class="box-form">
                        <div class="legend-title">Danh sách dự án IMS đã gán</div>
                        <div class="table-content">
                             <gridview ref ="grid"
                              v-bind:columns="ds_duanims.header"                    
                              v-bind:dataSource="ds_duanims.list"
                              :enable-paging-server="false"                                        
                              :allowPaging="true"
                              :showFilter="true"
                              :showColumnCheckbox="false"
                              @selectedRowChanged="gridChanged"
                              @rowClicked="rowClicked" 
                              >
                          </gridview> 
                        </div>
                       
                    </div>
                    
                </div>
            </div>
</template>

<script>
import DatePicker from 'vue2-datepicker'
import 'vue2-datepicker/locale/vi.js'
import 'vue2-datepicker/index.css'
import moment from 'moment'
import gridview from "./DataGrid"

import breadcrumb from '@/components/breadcrumb'
import VueFlatPickr from 'vue-flatpickr-component';


import { required, minLength, integer, maxValue } from 'vuelidate/lib/validators'
import API from './API'
export default {

  name: 'AssignBuildingToIMSProject',
  components: {
    DatePicker,
    API,required, minLength, integer, maxValue,gridview,breadcrumb,VueFlatPickr,moment
  },
  props: [ 'dulieu' ],

  data () {
        return {
        dateFormat: 'DD/MM/YYYY HH:mm:ss',
        dateFormatMonth: 'MM/YYYY',
        typeFormat: 'date',
        typeFormatMonth: 'month',
        dateConfig: {
            dateFormat: 'd/m/Y',
            altFormat: 'd/m/Y'
        },
        toanha: {
          list: [],
          value: {},
          },
        duanims: {
          list: [],
          value: {},
          },

        dsLoaiTTDA: {
            list: [],
            value: {loai_tt_id: "",ten_loai_tt: ""},
        },
        ds_duanims: {
            list: [],
            header: [ {fieldName: 'duan_id', headerText: 'ID', allowFiltering: true, width: 100}, 
                        {fieldName: 'ma_duan', headerText: 'Mã dự án', allowFiltering: true, width: 150},
                        {fieldName: 'ten_duan', headerText: 'Tên dự án', allowFiltering: true , allowHtml : true, width: 150},
                        {fieldName: 'nam_duan', headerText: 'Năm', allowFiltering: true , allowHtml : true, width: 100},                                
                        {fieldName: 'tien', headerText: 'Chi phí dự kiến', allowFiltering: true , allowHtml : true, type: "Number", format: "N0", width: 150},
                        {fieldName: 'vat', headerText: 'Chi phí (VAT)', allowFiltering: true , allowHtml : true,type: "Number", format: "N0", width: 150}, 
                        {fieldName: 'sohieu', headerText: 'Số hiệu', allowFiltering: true , allowHtml : true, width: 150},                                     
                        {fieldName: 'ban_quanly', headerText: 'Ban QL', allowFiltering: true , allowHtml : true, width: 150},                                     
                        ],
            value: {},
            isEnabled: true,
            checked: []
        },
        
        isNhapMoi:false,
        isGhiLai:false,
        isHuy:false,
        isXoa:false,
        index: 0,

        success_code: "BSS-00000000",
        maduan: "",
        tenduan: "",
        chiphidukien: "0",
        chiphivat: "0",
        sohieuduan: "",
        banquanly: "",
        diachi: "", 
        motaduan: "", 
        id: "",
        duan_id: "",
        chkCheckDate: false, 
        Loading: false,
        ToaNhaId: "",
        vToaNhaId: "",
        TenToaNha: "",
        vTenToaNha: ""
    }
  },
  mounted: async function () {
        
/*
        this.NgayTH = moment(new Date()).format("DD/MM/YYYY HH:mm:ss")
        this.btnLayTT();
        this.loadLoaiTTDA();
        this.SetButton(-1); */

        await this.GetDS();
        this.ToaNhaId = this.dulieu.vToaNhaId? this.dulieu.vToaNhaId : "";
        this.TenToaNha = this.dulieu.vTenToaNha? this.dulieu.vTenToaNha : "";
        this.toanha.value.id = this.ToaNhaId;
        await this.loadDSDuAn();
        
    },
  
  computed: {
   
  },
  watch: {
    /* Loading: function (val, oldval) {
      this.$root.showLoading(val)
    } */
  },
  
  validations: {
    txtDenNgay: {
      maxValue: new Date()
    }

  },
  methods: {
    ShowAlert(mesage,val)
    {
      if (val==0)
      {
        this.$toast.success(mesage);
      }
      else if (val==1)
      {
        this.$toast.warning(mesage);
      }
      else if (val==2)
      {
        this.$toast.error(mesage);
      }
    },
    GetData: function (response) {
      if (response.data.error === 200 || response.data.error === '200') {
        return response.data.data
      } else {
        console.log(response.data.error_code)
      }
      return []
    },
    SetButton(kieu)
    {
      this.isNhapMoi = false;
      this.isGhiLai = false;
      this.isHuy = false;
      this.isXoa = false;
      if (kieu == -1)//Bat dau
      {
        this.isGhiLai = true;
        this.isHuy = true;
        this.isNhapMoi = true;
      }
      if (kieu == 0)//Bat dau
      {
        this.isNhapMoi = true;
        this.Clear();
      }
      if (kieu == 1)//Them moi
      {
        this.isGhiLai = true;
        this.isHuy = true;
        this.Clear();
        this.$refs.DuAnID.focus();
      }
      if (kieu == 2)//Huy
      {
        this.isNhapMoi = true;
        this.isXoa = true;
        this.Clear();
        this.$refs.DuAnID.focus();
      }
      if (kieu == 3)//Edit
      {
        this.isNhapMoi = true;
        this.isXoa = true;
        this.isGhiLai = true;
        this.isHuy = true;
      }
    },
    
    Clear()
    {
      //this.DuAnID = "";
      this.maduan = "";
      this.chiphidukien = "0";
      this.chiphivat  = "0";
      this.sohieuduan  = "";
      this.banquanly  = "";
      this.diachi  = "";
      this.motaduan  = "";
      this.id  = "";
      this.$refs.DuAnID.focus();
    },
    btnNhapMoi()
    {
      this.SetButton(1);
    },

    btnHuy()
    {
      this.SetButton(0);
    },

    rowClicked(i,obj)
    {
      if(!obj) { 
        return; 
      }
      this.index = i;
      this.SetButton(3);
      this.maduan = obj.ma_duan;
      this.chiphidukien = obj.tien;
      this.chiphivat  = obj.vat;
      this.sohieuduan  = obj.sohieu;
      this.banquanly  = obj.ban_quanly;
      this.diachi  = obj.diachi;
      this.motaduan  = obj.mota;
      this.id  = obj.toanha_ims_id;
      this.duan_id = obj.duan_id;

      //this.dsLoaiTTDA.value.loai_tt_id = obj.loai_tt_id;
    },
    gridChanged(obj)
    {
      if(!obj) { 
        return; 
      }
      this.SetButton(3);
      this.maduan = obj.ma_duan;
      this.chiphidukien = obj.tien;
      this.chiphivat  = obj.vat;
      this.sohieuduan  = obj.sohieu;
      this.banquanly  = obj.ban_quanly;
      this.diachi  = obj.diachi;
      this.motaduan  = obj.mota;
      this.id  = obj.toanha_ims_id;
      this.duan_id = obj.duan_id;

    },
    selectedItems_click()
    {

    },
    GetDS: async function ()
    {
      //get ds tòa nhà
      var input = {};
      this.loading(true);
      this.toanha.list = this.GetData(await API.CSS_TOANHA(this.axios, input)).map(x=>({id:x.toanha_id,text:x.ten_toanha}));
      if (this.toanha.list.length>0)
      {
        this.toanha.value.id = this.toanha.list[0].id;
      }
      //Load danh sách dự án IMS
      var input1 = {};
      this.loading(true);
      await API.get_ProjectbyInvestmentField(this.axios, input1).then((response) => {
        if (response.data.data.length>0)
        {
          
          this.duanims.list = response.data.data.map(x=>({id:x.ProjectID,text:x.ProjectName}));
          this.duanims.value.id = this.duanims.list[0].id;
        }
      }
      ); 
    },

    toanhachange: async function()
    {
      this.loadDSDuAn();
    },
    
    loadDSDuAn: async function ()
    {
        var input = {};
        input.toanha_id = this.toanha.value.id;
        this.loading(true);
        this.ds_duanims.list = this.GetData(await API.sp_lay_ds_toanha_ims_da_gan(this.axios, input));
        this.loading(false);
    },


    btnXoa: async function ()
    {
        this.$bvModal.msgBoxConfirm('Bạn thật sự muốn xoá dự án đã gán này không?', {
        title: '', size: 'sm', okTitle: 'Đồng ý',cancelTitle: 'Hủy',}).then(async v => {
        if(v) {
            var input = {};
            input.id = this.id;
            console.log(input);
            this.loading(true);
            await API.sp_del_toanha_ims_by_id(this.axios, input).then((response) => {
                this.loading(false);
                if(response.data.error_code && response.data.error_code === this.success_code) {
                    this.ShowAlert("Xóa dự án thành công!",0);
                    this.loadDSDuAn();
                }
                else
                {
                    this.ShowAlert("Lỗi xóa dự án!",2);
                }
                }
            ); 
        }
        });
    },

    btnThemMoi: async function()
    {
      var input = {};
      input.duan_id = this.duan_id;
      input.toanha_id = this.toanha.value.id;
      input.ma_duan = this.maduan;
      input.ten_duan = this.tenduan;
      input.nam_duan = "2022";
      input.mota = this.motaduan;
      input.sohieu = this.sohieuduan;
      input.tien = this.chiphidukien;
      input.vat = this.chiphivat;
      input.bql = this.banquanly;
      input.dchi = this.diachi;
      console.log(input);
      this.loading(true);
      await API.sp_ins_toanha_ims(this.axios, input).then((response) => {
        this.loading(false);
        if(response.data.error_code && response.data.error_code === this.success_code) {
          this.ShowAlert("Thêm thành công dự án!",0);
          this.loadDSDuAn();
        }
        else
        {
          this.ShowAlert("Lỗi thêm dự án!",2);
        }
        }
      ); 
    },

  }
}

</script>

