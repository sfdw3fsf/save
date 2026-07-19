<template>
<div class="modal-content popup-box">
               
                <div class="popup-body">
                    <div class="box-form">
                        <div class="row">
                            <div class="col-12">
                                <div class="info-row">
                                    <div class="key" style="width: 220px;">Account</div>
                                    <div class="value">
                                        <input type="text" class="form-control highlight" v-model= "Account"  ref= "Account" v-on:keyup.enter="onAccountEnter">
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-sm-6 col-12">
                                <div class="info-row">
                                    <div class="key" style="width: 220px;">Số ảo</div>
                                    <div class="value">
                                        <input type="text" class="form-control" v-model= "SoAo" ref= "SoAo" readonly>
                                    </div>
                                </div>
                            </div>
                            <div class="col-sm-6 col-12">
                                <div class="info-row">
                                    <div class="key">Mã đối cáp</div>
                                    <div class="value">
                                        <input type="text" class="form-control" v-model= "MaDoiCap" ref= "MaDoiCap" readonly>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-sm-6 col-12">
                                <div class="info-row">
                                    <div class="key" style="width: 220px;">Tốc độ</div>
                                    <div class="value">
                                        <input type="text" class="form-control" v-model= "TocDo" ref= "TocDo" readonly> 
                                    </div>
                                </div>
                            </div>
                            <div class="col-sm-6 col-12">
                                <div class="info-row">
                                    <div class="key">Mức cước</div>
                                    <div class="value">
                                        <input type="text" class="form-control" v-model= "MucCuoc" ref= "MucCuoc" readonly>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-sm-6 col-12">
                                <div class="info-row">
                                    <div class="key" style="width: 220px;">Thiết bị</div>
                                    <div class="value">
                                        <input type="text" class="form-control" v-model= "ThietBi" ref= "ThietBi" readonly>
                                    </div>
                                </div>
                            </div>
                            <div class="col-sm-6 col-12">
                                <div class="info-row">
                                    <div class="key">Mã TN</div>
                                    <div class="value">
                                        <input type="text" class="form-control" v-model= "MaTN" ref= "MaTN" readonly>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-sm-6 col-12">
                                <div class="info-row">
                                    <div class="key" style="width: 220px;">Trang bị</div>
                                    <div class="value">
                                        <input type="text" class="form-control" v-model= "TrangBi" ref= "trangBi" readonly>
                                    </div>
                                </div>
                            </div>
                            <div class="col-sm-6 col-12">
                                <div class="info-row">
                                    <div class="key">Thông tin TC</div>
                                    <div class="value">
                                        <input type="text" class="form-control" v-model= "ThongTinTC" ref= "ThongTinTC" readonly>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-sm-6 col-12">
                                <div class="info-row">
                                    <div class="key" style="width: 220px;">Tên đối tác</div>
                                    <div class="value">
                                        <input type="text" class="form-control" v-model= "TenDoiTac" ref= "TenDoiTac" readonly>
                                    </div>
                                </div>
                            </div>
                            <div class="col-sm-6 col-12">
                                <div class="info-row">
                                    <div class="key">Cước đối tác</div>
                                    <div class="value">
                                        <input type="text" class="form-control" v-model= "CuocDoiTac" ref= "CuocDoiTac" readonly>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-sm-6 col-12">
                                <div class="info-row">
                                    <div class="key" style="width: 220px;">Chủ quản</div>
                                    <div class="value">
                                        <input type="text" class="form-control" v-model= "ChuQuan" ref= "ChuQuan" readonly>
                                    </div>
                                </div>
                            </div>
                            <div class="col-sm-6 col-12">
                                <div class="info-row">
                                    <div class="key">Chủ quản LĐ</div>
                                    <div class="value">
                                        <input type="text" class="form-control" v-model= "ChuQuanLD" ref= "ChuQuanLD" readonly>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-sm-6 col-12">
                                <div class="info-row">
                                    <div class="key" style="width: 220px;">Loại TB My TV</div>
                                    <div class="value">
                                        <input type="text" class="form-control" v-model= "LoaiTBMyTV" ref= "LoaiTBMyTV" readonly> 
                                    </div>
                                </div>
                            </div>
                            <div class="col-sm-6 col-12">
                                <div class="info-row">
                                    <div class="key">Phân loại DV</div>
                                    <div class="value">
                                        <input type="text" class="form-control" v-model= "PhanLoaiDV" ref= "PhanLoaiDV" readonly>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-sm-6 col-12">
                                <div class="info-row">
                                    <div class="key" style="width: 220px;">IPv4 WAN</div>
                                    <div class="value">
                                        <input type="text" class="form-control" v-model= "IPV4WAN" ref= "IPV4WAN" readonly>
                                    </div>
                                </div>
                            </div>
                            <div class="col-sm-6 col-12">
                                <div class="info-row">
                                    <div class="key">IPv6 LAN</div>
                                    <div class="value">
                                        <input type="text" class="form-control" v-model= "IPV6LAN" ref= "IPV6LAN" readonly>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-sm-6 col-12">
                                <div class="info-row">
                                    <div class="key" style="width: 220px;">Password</div>
                                    <div class="value">
                                        <input type="text" class="form-control" v-model= "Password" ref= "Password" readonly>
                                    </div>
                                </div>
                            </div>
                            <div class="col-sm-6 col-12">
                                <div class="info-row">
                                    <div class="key">USERNAME</div>
                                    <div class="value">
                                        <input type="text" class="form-control" v-model= "Username" ref= "Username" readonly>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-12">
                                <div class="info-row">
                                    <div class="key" style="width: 220px;">MAC_ADDR</div>
                                    <div class="value">
                                        <input type="text" class="form-control" v-model= "MacAdd" ref= "MacAdd" readonly>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-12">
                                <div class="info-row">
                                    <div class="key" style="width: 220px;">Lịch sử thay đổi thông số kỹ thuật</div>
                                    <div class="value">
                                        <input type="text" class="form-control" v-model= "LichSuThayDoi" ref= "LichSuThayDoi" readonly>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="box-form">
                        <div class="legend-title">
                            <span class="icon fa fa-angle-up"></span> Danh sách thuê bao đơn vị
                        </div>
                        <div class="table-content">
                            <table class="table-result table-gachle">
                                <DataGrid
                                    v-bind:columns="ds_thuebaodonvi.header"                    
                                    v-bind:dataSource="ds_thuebaodonvi.list"
                                    :enable-paging-server="false"                                        
                                    :allowPaging="true"
                                    :showFilter="true"
                                    :showColumnCheckbox="false"
                                    ref="nvdaGan"
                                    >
                                </DataGrid> 
                            </table>
                        </div>
                        
                    </div>
                    <div class="box-form">
                        <div class="legend-title">
                            <span class="icon fa fa-angle-up"></span> Danh sách Nhân viên khu vực theo thuê bao
                        </div>
                        <div class="table-content">
                            <table class="table-result table-gachle">
                                <DataGrid
                                    v-bind:columns="ds_nvkvtheothuebao.header"                    
                                    v-bind:dataSource="ds_nvkvtheothuebao.list"
                                    :enable-paging-server="false"                                        
                                    :allowPaging="true"
                                    :showFilter="true"
                                    :showColumnCheckbox="false"
                                    ref="nvdaGan"
                                    >
                                </DataGrid> 
                            </table>
                        </div>
                       
                    </div>
                    <div class="box-form">
                        <div class="legend-title">
                            <span class="icon fa fa-angle-up"></span> Biến động gói đa DV
                        </div>
                        <div class="table-content">
                            <table class="table-result table-gachle">
                                <DataGrid
                                    v-bind:columns="ds_biendonggoiddv.header"                    
                                    v-bind:dataSource="ds_biendonggoiddv.list"
                                    :enable-paging-server="false"                                        
                                    :allowPaging="true"
                                    :showFilter="true"
                                    :showColumnCheckbox="false"
                                    ref="nvdaGan"
                                    >
                                </DataGrid> 
                            </table>
                        </div>
                    
                    </div>
                    <div class="box-form">
                        <div class="legend-title">
                            <span class="icon fa fa-angle-up"></span> Thuê bao cùng đối cáp
                        </div>
                        <div class="table-content">
                            <table class="table-result table-gachle">
                               <DataGrid
                                    v-bind:columns="ds_thuebaocungdoicap.header"                    
                                    v-bind:dataSource="ds_thuebaocungdoicap.list"
                                    :enable-paging-server="false"                                        
                                    :allowPaging="true"
                                    :showFilter="true"
                                    :showColumnCheckbox="false"
                                    ref="nvdaGan"
                                    >
                                </DataGrid> 
                            </table>
                        </div>
                    
                    </div>
                </div>
            </div>
</template>

<script>
import DatePicker from 'vue2-datepicker'
import 'vue2-datepicker/locale/vi.js'
import 'vue2-datepicker/index.css'
import { required, minLength, integer, maxValue } from 'vuelidate/lib/validators'
import API from './API_Detail'
export default {
  name: 'PhieuTra',
  components: {
    DatePicker,
    API,required, minLength, integer, maxValue
  },
  props: [ 'dulieu' ],

  data () {
    return {
      dateFormat: 'DD/MM/YYYY',
      dateFormatMonth: 'MM/YYYY',
      typeFormat: 'date',
      typeFormatMonth: 'month',
      dateConfig: {
        dateFormat: 'd/m/Y',
        altFormat: 'd/m/Y'
      },
      ds_thuebaodonvi: {
          list: [],
          header: [ {fieldName: 'donvi_id', headerText: 'ID đơn vị', allowFiltering: true, width: 100}, 
                      {fieldName: 'ten_loaidv', headerText: 'Loại đơn vị', allowFiltering: true, width: 150},
                      {fieldName: 'ten_dv', headerText: 'Tên đơn vị', allowFiltering: true , allowHtml : true, width: 150},
                      {fieldName: 'ma_dv', headerText: 'Mã ĐV', allowFiltering: true , allowHtml : true, width: 100},                                
                      {fieldName: 'kieudv', headerText: 'Kiểu ĐV', allowFiltering: true , allowHtml : true, width: 150},                                     
                      ],
          value: {},
          isEnabled: true,
          checked: []
        },
        ds_nvkvtheothuebao: {
          list: [],
          header: [ {fieldName: 'ma_kv', headerText: 'Mã khu vực', allowFiltering: true, width: 130}, 
                      {fieldName: 'ten_kv', headerText: 'Tên khu vực', allowFiltering: true, width: 130},
                      {fieldName: 'ma_nv', headerText: 'Mã NV', allowFiltering: true , allowHtml : true, width: 100},
                      {fieldName: 'ma_nd', headerText: 'Mã ND', allowFiltering: true , allowHtml : true, width: 100},                                
                      {fieldName: 'ten_nv', headerText: 'Tên NV', allowFiltering: true , allowHtml : true, width: 150}, 
                      {fieldName: 'loai_nv', headerText: 'Chức vụ', allowFiltering: true , allowHtml : true, width: 200},                                     
                      {fieldName: 'ten_dv', headerText: 'Đơn vị', allowFiltering: true , allowHtml : true, width: 150},                                     
                      {fieldName: 'so_dt', headerText: 'Số ĐT', allowFiltering: true , allowHtml : true, width: 100},                                     
                      {fieldName: '', headerText: 'Tháng', allowFiltering: true , allowHtml : true, width: 100},                                     
                      ],
          value: {},
          isEnabled: true,
          checked: []
        },
        ds_biendonggoiddv: {
          list: [],
          header: [ {fieldName: 'ten_goi', headerText: 'Tên gói', allowFiltering: true, width: 150}, 
                      {fieldName: 'tien', headerText: 'Tiền', allowFiltering: true, width: 100},
                      {fieldName: 'vat', headerText: 'VAT', allowFiltering: true , allowHtml : true, width: 100},
                      {fieldName: 'mota', headerText: 'Mô tả', allowFiltering: true , allowHtml : true, width: 150},                                
                      {fieldName: 'ngay_dk', headerText: 'Ngày ĐK', allowFiltering: true , allowHtml : true, width: 100}, 
                      {fieldName: 'ngay_kt', headerText: 'Ngày KT', allowFiltering: true , allowHtml : true, width: 100},                                     
                      {fieldName: 'thang_bd', headerText: 'Tháng BĐ', allowFiltering: true , allowHtml : true, width: 100},                                     
                      {fieldName: 'thang_kt', headerText: 'Tháng KT', allowFiltering: true , allowHtml : true, width: 100},                                     
                      ],
          value: {},
          isEnabled: true,
          checked: []
        },
        ds_thuebaocungdoicap: {
          list: [],
          header: [ {fieldName: 'ma_tb', headerText: 'Mã TB', allowFiltering: true, width: 100}, 
                      {fieldName: 'dichvu', headerText: 'Dịch vụ', allowFiltering: true, width: 150},
                      {fieldName: 'loaihinh', headerText: 'Loại hình', allowFiltering: true , allowHtml : true, width: 100},
                      {fieldName: 'matb_tn', headerText: 'Mã TN', allowFiltering: true , allowHtml : true, width: 100},                                
                      {fieldName: 'dslam_visa', headerText: 'Dslam Visa', allowFiltering: true , allowHtml : true, width: 100}, 
                      {fieldName: 'madoicap', headerText: 'Mã đối cáp', allowFiltering: true , allowHtml : true, width: 100},                                     
                      {fieldName: 'slot', headerText: 'Slot', allowFiltering: true , allowHtml : true, width: 50},                                     
                      {fieldName: 'port', headerText: 'Port', allowFiltering: true , allowHtml : true, width: 50},    
                      {fieldName: 'rack', headerText: 'Rack', allowFiltering: true , allowHtml : true, width: 50},                                     
                      {fieldName: 'shelf', headerText: 'Shelf', allowFiltering: true , allowHtml : true, width: 50},                                     
                      {fieldName: 'dslam_tinh', headerText: 'DSLam. Tinh', allowFiltering: true , allowHtml : true, width: 150},                                     
                      ],
          value: {},
          isEnabled: true,
          checked: []
        },

      success_code: "BSS-00000000",
      Account: "",
      SoAo: "",
      MaDoiCap:"",
      TocDo:"",
      MucCuoc:"",
      ThietBi:"",
      MaTN:"",
      TrangBi:"",
      ThongTinTC:"",
      TenDoiTac:"",
      CuocDoiTac:"",
      ChuQuan:"",
      ChuQuanLD:"",
      LoaiTBMyTV:"",
      PhanLoaiDV:"",
      IPV4WAN:"",
      IPV6LAN:"",
      Password:"",
      Username:"",
      MacAdd:"",
      LichSuThayDoi:"",
      vthuebao_id: "",
      
      chkCheckDate: false, 
      Loading: false
    }
  },
  async mounted() {
        if (this.dulieu.vthuebao_id)
        {
            this.Account = this.dulieu.vma_tb;
            this.vthuebao_id = this.dulieu.vthuebao_id;
            this.onAccountEnter();   
        } 
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

    onAccountEnter: async function ()
    {
        if (this.Account =="")
        {
            this.ShowAlert("Bạn cần nhập Account để tra cứu!");
            this.$refs.Account.focus();
            return;
        }
        
        //Lấy thông tin chi tiết thuê bao
        var input = {};
        input.thuebao_id = this.vthuebao_id;
        console.log(input);
        this.loading(true);
        await API.sp_lay_tt_chitiet_theo_tb_id(this.axios, input).then((response) => {
            this.loading(false);
            if(response.data.error_code && response.data.error_code === this.success_code) {                
                if (response.data.data.data.length>0)
                {
                    if (response.data.data.dichvuvt_id == 4)
                    {
                        this.MaDoiCap = (response.data.data.data[0].madoicap? response.data.data.data[0].madoicap: "");
                        this.TocDo = (response.data.data.data[0].tocdo? response.data.data.data[0].tocdo: "");
                        this.MucCuoc= (response.data.data.data[0].muccuoc? response.data.data.data[0].muccuoc: "");
                        this.ThietBi= (response.data.data.data[0].tbi? response.data.data.data[0].tbi: "");
                        this.MaTN = (response.data.data.data[0].matb_tn? response.data.data.data[0].matb_tn: "");
                        this.TrangBi = (response.data.data.data[0].trangbi? response.data.data.data[0].trangbi : "");
                        this.ThongTinTC = (response.data.data.data[0].thongtin_tc? response.data.data.data[0].thongtin_tc: "");
                        this.TenDoiTac = (response.data.data.data[0].tenchuquan? response.data.data.data[0].doitac: "");
                        this.CuocDoiTac = (response.data.data.data[0].cuoc_doitac? response.data.data.data[0].cuoc_doitac: "");
                        this.ChuQuan = (response.data.data.data[0].tenchuquan? response.data.data.data[0].tenchuquan: "");
                        this.ChuQuanLD = (response.data.data.data[0].chuquan_ld? response.data.data.data[0].chuquan_ld: "");
                        this.LoaiTBMyTV = (response.data.data.data[0].loaimytv? response.data.data.data[0].loaimytv: "");
                        this.PhanLoaiDV = "";
                        this.IPV4WAN = (response.data.data.data[0].ipv4_wan? response.data.data.data[0].ipv4_wan: "");
                        this.IPV6LAN = (response.data.data.data[0].ipv6_lan? response.data.data.data[0].ipv6_lan: "");
                        this.Password = (response.data.data.data[0].password? response.data.data.data[0].password: "");
                        this.Username = (response.data.data.data[0].username? response.data.data.data[0].username: "");
                        this.MacAdd = (response.data.data.data[0].mac_addr? response.data.data.data[0].mac_addr: "");
                        this.LichSuThayDoi = "";
                        this.SoAo = (response.data.data.data[0].ma_lt? response.data.data.data[0].ma_lt: "");
                    }
                    else if(response.data.data.dichvuvt_id == 1)
                    {
                        this.TrangBi = (response.data.data.data[0].trangbi? response.data.data.data[0].trangbi : "");
                        this.ChuQuan = (response.data.data.data[0].chuquan? response.data.data.data[0].chuquan: "");
                      /*   "cap_p": null,
                            "lientu": null,
                            "trangbi": "Viễn thông trang bị",
                            "chuquan": "VTHP",
                            "cap_goc": null,
                            "matb_tn": null,
                            "doicap_goc": null,
                            "tramvt": "Tổ Kỹ thuật An Dương (An Dương 1)",
                            "tuyencap": null,
                            "ten_doitac": null,
                            "culy": 100,
                            "doicap_p": null,
                            "tramtbi": "Nomura ()",
                            "loaicap": null,
                            "cuoc_doitac": null,
                            "ne": null,
                            "hopcap_p": null,
                            "chuquan_ld": null,
                            "tucap_p": null,
                            "tucap_goc": null */
                    }
                    else if(response.data.data.dichvuvt_id == 2)
                    {
                        this.MaDoiCap = (response.data.data.data[0].doicap_x? response.data.data.data[0].doicap_x : "");
                        /* doicap_x, hopcap_x, sone */
                    }
                    else if(response.data.data.dichvuvt_id == 7)
                    {
                        this.ThietBi= (response.data.data.data[0].TEN_TBI? response.data.data.data[0].TEN_TBI: "");
                        this.ChuQuan = (response.data.data.data[0].TENCHUQUAN? response.data.data.data[0].TENCHUQUAN: "");
                        this.TrangBi = (response.data.data.data[0].TENTRANGBI? response.data.data.data[0].TENTRANGBI : "");

                       /* MA_LT,
                       MATB_TN,
                       LOAINODE,
                       SITE_ID,
                       MA_PL,
                       HUONG_KN,
                       TENTINH,
                       LOAI_MD,
                       DIACHI_KN,
                       TEN_TBI,
                       LOAI_KENH,
                       TENCHUQUAN,
                       TENTRANGBI,
                       TOCDOC_CIR,
                       GIUKENH,
                       TOCDO_PIR,
                       TENTHOIHAN,
                       SL_IP,
                       TOCDO_NIX,
                       CUOC_NIX,
                       CUOC_IP,
                       TOCDO_ISP,
                       CUOC_ISP,
                       CUOC_TC,
                       CUOC_TK,
                       CUOC_TBI,
                       CUOC_HT  */
                    }
                    else if(response.data.data.dichvuvt_id == 8)
                    {
                        this.ThietBi= (response.data.data.data[0].TEN_TBI? response.data.data.data[0].TEN_TBI: "");
                        this.ChuQuan = (response.data.data.data[0].TENCHUQUAN? response.data.data.data[0].TENCHUQUAN: "");
                        this.TrangBi = (response.data.data.data[0].TENTRANGBI? response.data.data.data[0].TENTRANGBI : "");

                        /* MA_LT,
                        MATB_TN,
                        LOAINODE,
                        SITE_ID,
                        MA_PL,
                        HUONG_KN,
                        TENTINH,
                        LOAI_MD,
                        DIACHI_KN,
                        TEN_TBI,
                        LOAI_KENH,
                        TENCHUQUAN,
                        TENTRANGBI,
                        TOCDOC_CIR,
                        GIUKENH,
                        TOCDO_PIR,
                        TENTHOIHAN,
                        SL_IP,
                        TOCDO_NIX,
                        CUOC_NIX,
                        CUOC_IP,
                        TOCDO_ISP,
                        CUOC_ISP,
                        CUOC_TC,
                        CUOC_TK,
                        CUOC_TBI,
                        CUOC_HT  */
                    }
                    else if(response.data.data.dichvuvt_id == 9)
                    {
                        this.TocDo= (response.data.data.data[0].TOCDO? response.data.data.data[0].TOCDO: "");
                        this.ChuQuan = (response.data.data.data[0].TENCHUQUAN? response.data.data.data[0].TENCHUQUAN: "");
                        this.TrangBi = (response.data.data.data[0].TRANGBI? response.data.data.data[0].TRANGBI : "");

                        /* MA_PL,
                        MODEM,
                        LOAIKENH,
                        TENCHUQUAN,
                        TRANGBI,
                        TOCDO,
                        THOIHAN,
                        CUOC_TC,
                        CUOC_TK,
                        CUOC_TBI,
                        CUOC_HT,
                        MA_LT,
                        MA_LT_1,
                        DIACHI,
                        DIACHI_1,
                        TBI_DAU,
                        TBI_CUOI,
                        CAP_DAU,
                        CAP_CUOI,
                        SITE_ID,
                        SITE_ID_1,
                        VRF,
                        VRF_1,
                        SL_SOIQUANG,
                        S1,
                        S2 */
                    }
                    else if(response.data.data.dichvuvt_id == 10)
                    {
                        this.ChuQuan = (response.data.data.data[0].tenchuquan? response.data.data.data[0].tenchuquan: "");
                        this.TrangBi= (response.data.data.data[0].tentrangbi? response.data.data.data[0].tentrangbi: "");
                       /*  simcard, tenchuquan, tentrangbi, cell1, toado */
                    }

                }
                //this.listbox.loaihinh_full.list = response.data.data; 
            }
            else
            {
                this.ShowAlert("Không tìm thấy thông tin chi tiết thuê bao!",1);
            }
        }
        ).finally(()=>{ this.loading(false); });
        //Lấy thông tin danh sách thuê bao đơn vị
        var input1 = {};
        input1.thuebao_id = this.vthuebao_id;
        console.log(input1);
        this.loading(true);
        await API.sp_lay_ds_thuebao_donvi_theo_tb_id(this.axios, input1).then((response) => {
            this.loading(false);
            if(response.data.error_code && response.data.error_code === this.success_code) {
                console.log(response.data.data);
                this.ds_thuebaodonvi.list = response.data.data;
            }
            else
            {
                this.ShowAlert("Không tìm thấy thông tin thuê bao đơn vị!",1);
            }
        }
        ).finally(()=>{ this.loading(false); });
        //Lấy thông tin nhân viên khu vực 
        var input2 = {};
        input2.thuebao_id = this.vthuebao_id;
        console.log(input2);
        this.loading(true);
        await API.sp_lay_ds_nhanvien_khuvuc_theo_tb_id(this.axios, input2).then((response) => {
            this.loading(false);
            if(response.data.error_code && response.data.error_code === this.success_code) {
                console.log(response.data.data);
                this.ds_nvkvtheothuebao.list = response.data.data;
            }
            else
            {
                this.ShowAlert("Không tìm thấy thông tin nhân viên khu vực!",1);
            }
        }
        ).finally(()=>{ this.loading(false); });
         //Lấy thông tin biến động gói đa dịch vụ
        var input3 = {};
        input3.thuebao_id = this.vthuebao_id;
        console.log(input3);
        this.loading(true);
        await API.sp_lay_ds_da_goi_dv_theo_tb_id(this.axios, input3).then((response) => {
            this.loading(false);
            if(response.data.error_code && response.data.error_code === this.success_code) {
                console.log(response.data.data);
                this.ds_biendonggoiddv.list = response.data.data;
            }
            else
            {
                this.ShowAlert("Không tìm thấy thông tin gói đa dịch vụ!",1);
            }
        }
        ).finally(()=>{  this.loading(false); });
        
         //Lấy thông tin thông tin kỹ thuât
        var input4 = {};
        input4.thuebao_id = this.vthuebao_id;
        console.log(input4);
        this.loading(true);
        await API.sp_lay_ds_cung_doi_cap_theo_tb_id(this.axios, input4).then((response) => {
            this.loading(false);
            if(response.data.error_code && response.data.error_code === this.success_code) {
                console.log(response.data.data);
                this.ds_thuebaocungdoicap.list = response.data.data;
            }
            else
            {
                this.ShowAlert("Không tìm thấy thông tin kỹ thuật!",1);
            }
        }
        ).finally(()=>{ this.loading(false); });
    },
  }
}

</script>

