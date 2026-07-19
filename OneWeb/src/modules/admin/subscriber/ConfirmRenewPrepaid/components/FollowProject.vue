<template>
<div class="modal-content popup-box">
                
                <div class="list-actions-top">
                    <ul class="list">
                
                        <li>
                            <a href="#" @click.prevent="btnLayTT">
                                <span class="icon one-file-attach" ></span>Lấy TT (F3)
                            </a>
                        </li>
                        <li>
                            <a href="#" @click.prevent="btnNhapMoi" :class="{disabled: !isNhapMoi}">
                                <span class="icon one-file-plus"></span>Nhập mới (F5)
                            </a>
                        </li>
                
                        <li>
                            <a href="#"  @click.prevent="btnGhiLai" :class="{disabled: !isGhiLai}">
                                <span class="icon one-save"></span>Ghi lại (F9)
                            </a>
                        </li>
                
                        <li>
                            <a href="#"  @click.prevent="btnHuy" :class="{disabled: !isHuy}">
                                <span class="icon nc-icon-glyph ui-1_circle-remove"></span>Hủy
                            </a>
                        </li>
                
                        <li>
                            <a href="#" @click.prevent="btnXoa" :class="{disabled: !isXoa}">
                                <span class="icon one-trash"></span>Xóa (F8)
                            </a>
                        </li>
                        
                    </ul>
                </div>
                <div class="popup-body">
                    <div class="box-form">
                        <div class="legend-title">Thông tin tiến trình dự án</div>
                        <div class="info-row">
                            <div class="key w100">Dự án</div>
                            <div class="value">
                                <input type="text" v-model = "DuAnID" ref ="DuAnID" class="form-control">
                            </div>
                        </div>
                        <div class="info-row">
                            <div class="key w100">Loại tiến trình</div>
                            <div class="value">
                                <div class="select-custom">
                                   <select2 :options="dsLoaiTTDA.list" v-model="dsLoaiTTDA.value.loai_tt_id"> 
                                        <option disabled value="0">Select one</option> 
                                    </select2> 
                                </div>
                            </div>
                        </div>
                        <div class="info-row">
                            <div class="key w100">Ngày TH</div>
                            <div class="value">
                                <div >
                                    <div >
                                        <date-picker input-class="form-control" :format="dateFormat" :value-type="'format'" v-model="NgayTH"  ></date-picker> 
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="info-row">
                            <div class="key w100">Nội dung TH</div>
                            <div class="value">
                                <textarea name="" v-model="NoiDung" class="form-control" style="height: 100px;resize: none;"></textarea>
                            </div>
                        </div>
                    </div>
                    <div class="box-form">
                        <div class="legend-title">Danh sách tiến trình dự án</div>
                        <div class="table-content">
                            <!-- <DataGrid
                                v-bind:columns="ds_tientrinhda.header"                    
                                v-bind:dataSource="ds_tientrinhda.list"
                                :enable-paging-server="false"                                        
                                :allowPaging="true"
                                :showFilter="true"
                                :showColumnCheckbox="false"
                                @rowClicked= "gridChanged"
                                @selectedRowChanged = "gridChanged"
                                >
                            </DataGrid>  --> 

                            <gridview ref ="grid"
                              v-bind:columns="ds_tientrinhda.header"                    
                              v-bind:dataSource="ds_tientrinhda.list"
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


import { required, minLength, integer, maxValue } from 'vuelidate/lib/validators'
import API from './API'
export default {
  name: 'FollowProject',
  components: {
    DatePicker,
    API,required, minLength, integer, maxValue,gridview
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
        dsLoaiTTDA: {
            list: [],
            value: {loai_tt_id: "",ten_loai_tt: ""},
        },
        ds_tientrinhda: {
            list: [],
            header: [ {fieldName: 'ten_duan', headerText: 'Dự án', allowFiltering: true, width: 100}, 
                        {fieldName: 'ten_loai_tt', headerText: 'Loại tiến trình', allowFiltering: true, width: 150},
                        {fieldName: 'ngay_th', headerText: 'Ngày TH', allowFiltering: true , allowHtml : true, width: 150},
                        {fieldName: 'noidung_th', headerText: 'Nội dung TH', allowFiltering: true , allowHtml : true, width: 100},                                
                        {fieldName: 'ngay_cn', headerText: 'Ngày CN', allowFiltering: true , allowHtml : true, width: 150},
                        {fieldName: 'nguoi_cn', headerText: 'Người CN', allowFiltering: true , allowHtml : true, width: 150},                                     
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
        DuAnID: "",
        vDuAnID: "",
        vMaDuAn: "",
        MaDuAn: "", 
        NgayTH: "", 
        NoiDung: "",   
        TTDAID: "",
        chkCheckDate: false, 
        Loading: false
    }
  },
  async mounted() {
        this.DuAnID = this.dulieu.vDuAnId? this.dulieu.vDuAnId : "";
        this.NgayTH = moment(new Date()).format("DD/MM/YYYY HH:mm:ss")
        this.btnLayTT();
        this.loadLoaiTTDA();
        this.SetButton(-1);
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
      this.DuAnID = "";
      this.dsLoaiTTDA.value.loai_tt_id = this.dsLoaiTTDA.list[1].id;
      this.NgayTH = moment(new Date()).format("DD/MM/YYYY HH:mm:ss");
      this.NoiDung = "";
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
      this.dsLoaiTTDA.value.loai_tt_id = obj.loai_tt_id;
      this.NgayTH = moment(obj.ngay_th).format("DD/MM/YYYY HH:mm:ss");
      this.DuAnID = obj.duan_id;
      this.NoiDung = obj.noidung_th;
    },
    gridChanged(obj)
    {
      if(!obj) { 
        return; 
      }
      this.SetButton(3);
      this.dsLoaiTTDA.value.loai_tt_id = obj.loai_tt_id;
      this.TTDAID = obj.ttda_id;
      this.NgayTH = moment(obj.ngay_th).format("DD/MM/YYYY HH:mm:ss");
      this.DuAnID = obj.duan_id;
      this.NoiDung = obj.noidung_th;
    },
    selectedItems_click()
    {

    },
    loadLoaiTTDA: async function ()
    {
        var input = {};
        this.loading(true);
        this.dsLoaiTTDA.list = this.GetData(await API.lay_loai_tt_duan(this.axios, input)).map(x=>({id:x.loai_tt_id,text:x.ten_loai_tt}));
        this.loading(false);
        if (this.dsLoaiTTDA.list.length>0)
        {
            this.dsLoaiTTDA.value.loai_tt_id = this.dsLoaiTTDA.list[1].id;
        }
    },
    btnLayTT: async function ()
    {
      var input = {};
      input.vduan_id = this.DuAnID;
      input.vphanvung_id = 26;
      console.log(input)
      this.loading(true);
      this.ds_tientrinhda.list = this.GetData(await API.lay_tt_tientrinh_duan(this.axios, input));
      if (this.ds_tientrinhda.list.length>0)
      {
        this.$refs.grid.setCurrentSelectedRow(this.index);
      }
      console.log(this.ds_tientrinhda.list);    
      this.loading(false);
    },

    btnGhiLai: async function ()
    {
        
        if (this.DuAnID == "")
        {
            this.ShowAlert("Bạn chưa nhập mã dự án");
            this.$refs.DuAnID.focus();
            return;
        }
        var input = {};
        
        input.vduan_id = this.DuAnID;
        input.vloai_tt_id = this.dsLoaiTTDA.value.loai_tt_id;
        input.vnoidung_th = this.NoiDung;
        const changedDate = this.NgayTH.replace(/(..)\/(..)\/(....) (..):(..)/, '$3-$2-$1 $4:$5');
        var date = new Date(changedDate);
        input.vngay_th = moment(date).format("YYYY-MM-DD HH:mm:ss");
        if (!this.isNhapMoi) //Thêm mới
        {
            input.vttda_id = "";
            input.vkieu = 1;
            console.log(input);
            this.loading(true);
            await API.tientrinh_duan(this.axios, input).then((response) => {
            this.loading(false);
            if(response.data.error_code && response.data.error_code === this.success_code) {
                this.ShowAlert("Thêm mới tiến trình dự án thành công!",0);
                this.btnLayTT();
            }
            else
            {
                this.ShowAlert("Lỗi thêm mới tiến trình dự án!",2);
            }
            }
            ); 
        }
        else //Cập nhật
        {
            input.vttda_id = this.TTDAID;
            input.vkieu = 2;
            console.log(input);
            this.loading(true);
            await API.tientrinh_duan(this.axios, input).then((response) => {
            this.loading(false);
            if(response.data.error_code && response.data.error_code === this.success_code) {
                this.ShowAlert("Cập nhật tiến trình dự án thành công!",0);
                this.btnLayTT();
            }
            else
            {
                this.ShowAlert("Lỗi cập nhật tiến trình dự án!",2);
            }
            }
            ); 
        }
    },

    btnXoa: async function ()
    {
        this.$bvModal.msgBoxConfirm('Bạn thật sự muốn xoá tiến trình dự án này không?', {
        title: '', size: 'sm', okTitle: 'Đồng ý',cancelTitle: 'Hủy',}).then(async v => {
        if(v) {
             var input = {};
            input.vkieu = 3;
            input.vttda_id = this.TTDAID;
            input.vduan_id = this.DuAnID;
            input.vloai_tt_id = this.dsLoaiTTDA.value.loai_tt_id;
            input.vnoidung_th = this.NoiDung;
            const changedDate = this.NgayTH.replace(/(..)\/(..)\/(....) (..):(..)/, '$3-$2-$1 $4:$5');
            var date = new Date(changedDate);
            input.vngay_th = moment(date).format("YYYY-MM-DD HH:mm:ss");
            console.log(input);
            this.loading(true);
            await API.tientrinh_duan(this.axios, input).then((response) => {
                this.loading(false);
                if(response.data.error_code && response.data.error_code === this.success_code) {
                    this.ShowAlert("Xóa tiến trình dự án thành công!",0);
                    this.btnLayTT();
                }
                else
                {
                    this.ShowAlert("Lỗi xóa tiến trình dự án!",2);
                }
                }
            ); 
        }
        });
    },
  }
}

</script>

