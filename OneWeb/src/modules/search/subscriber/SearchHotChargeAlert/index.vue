<template>
<div>
<form v-on:submit.prevent="btnTimKiem_Click">
    <vue-bread-crumb :header="header" />
    <!-- menu -->
	<vue-nav>
        <ul class="list">
            <li
                @click="btnTimKiem_Click"                
                >
                <a>
                    <span class="one-search"></span> Tìm kiếm
                </a>
            </li>   
             <li
                @click="btnCapNhat_Click"
                :class="radio_loaicuoc == 2 ? 'active' : 'none-active'"
                >
                <a>
                    <span class="one-save"></span>Cập nhật
                </a>
            </li>
            <li
                @click="Clear"
            >
                <a>
                    <span class="fa fa-eraser"></span> Xóa trắng
                </a>
            </li>                   
        </ul>
	</vue-nav>
    <div class="page-content">
        <div class="box-form">
            <div class="legend-title mb-2">
                Thông tin tra cứu
            </div>
            <div class="row">
                <div class="col-sm-4 col-12">
                    <div class="info-row">
                        <div class="key">Mã thuê bao</div>
                        <div class="value w20">
                            <span class="form-control padt6 fw6 text">
                                84
                            </span>
                        </div>
                        <div class="value">
                            <input 
                            v-model.number="txtMaThueBao"
                            type="number"
                            @keypress="onlyNumber" 
                            class="form-control">
                        </div>
                    </div>
                </div>
                <div class="col-sm-4 col-12">
                    <div class="info-row">
                        <div class="key w100">Từ ngày</div>
                        <div class="value">
                            <div class="input-icon-right">
                                <date-picker
                                    :format="dateFormat"
                                    :value-type="dateFormat"
                                    ref="txtTuNgay" 
                                    :time-title-format="dateFormat"
                                    v-model="txtTuNgay"
                                    :showTimePanel="showTimePanel"
                                    :type="typeFormat"
                                    @close="handleOpenChange" 
                                    id="txtTuNgay" 
                                    placeholder="dd/mm/yyyy"                       
                                >
                                    <template #icon-calendar>
                                        <span class="icon one-calendar"></span>
                                    </template>
                                    <template v-slot:footer>
                                        <button class="mx-btn mx-btn-text" @click="toggleTimePanel">
                                            <!-- {{ showTimePanel}} -->
                                        </button>
                                    </template>
                                </date-picker>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="col-sm-4 col-12">
                    <div class="info-row">
                        <div class="value">
                        <div class="list-checks mart7">
                            <div class="item">
                            <div class="check-action">
                                <input type="radio" :checked="radio_loaicuoc==0" name="loaicuoc" value="0" @change="radio_loaicuoc=0" class="check" />
                                <span class="name">Đăng ký</span>
                            </div>
                            </div>
                            <div class="item">
                            <div class="check-action">
                                <input type="radio" :checked="radio_loaicuoc==1"  name="loaicuoc" value="1" @change="radio_loaicuoc=1" class="check" />
                                <span class="name">Nhắn tin</span>
                            </div>
                            </div>
                            <div class="item">
                            <div class="check-action">
                                <input type="radio" :checked="radio_loaicuoc==2"  name="loaicuoc" value="2" @change="radio_loaicuoc=2" class="check" />
                                <span class="name">Cước nóng theo ngày</span>
                            </div>
                            </div>
                        </div>
                        </div>
                    </div>
                </div>
            </div>

            <div class="row">
                <div class="col-sm-4 col-12">
                    <div class="info-row">
                        <div class="key">Kiểu nhắn tin</div>
                        <div class="value">
                            <div class="select-custom">
                                <SelectExt v-model="form.kieutinnhan" :allowFiltering="true" dataTextField="kieu_sms"
                                    dataValueField="id_sms" :dataSource="cbokieunhantin"
                                    :disabled="radio_loaicuoc== 0 || radio_loaicuoc== 2 "
                                    ></SelectExt>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="col-sm-4 col-12">
                    <div class="info-row">
                        <div class="key w100">Đến ngày</div>
                        <div class="value">
                            <div class="input-icon-right">
                                <date-picker
                                    :format="dateFormat"
                                    :value-type="dateFormat"
                                    ref="txtDenNgay" 
                                    :time-title-format="dateFormat"
                                    v-model="txtDenNgay"
                                    :showTimePanel="showTimePanel"
                                    :type="typeFormat"
                                    @close="handleOpenChange" 
                                    id="txtDenNgay"   
                                    placeholder="dd/mm/yyyy"                     
                                >
                                    <template #icon-calendar>
                                        <span class="icon one-calendar"></span>
                                    </template>
                                    <template v-slot:footer>
                                        <button class="mx-btn mx-btn-text" @click="toggleTimePanel">
                                            {{ showTimePanel }}
                                        </button>
                                    </template>
                                </date-picker>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="col-sm-4 col-12">
                    <div class="info-row">
                        <div class="key">Trạng thái xử lý</div>
                        <div class="value">
                            <div class="select-custom">
                                <SelectExt v-model="form.trangthaixuly" :allowFiltering="true" dataTextField="trangthai"
                                    dataValueField="id" :dataSource="cbotrangthaixuly"
                                    :disabled="radio_loaicuoc == 0 || radio_loaicuoc == 1 "
                                    ></SelectExt>
                            </div>
                        </div>
                    </div>
                </div>               
            </div>
            <div class="box-form">
                <div class="legend-title">Thông tin chi tiết</div>
                <div>
                     <DataGrid v-bind:columns ="columns.grvTTChiTiet_DK" v-show="radio_loaicuoc == 0"
                     ref="grvTTChiTiet_DK" :dataSource="options.grvTTChiTiet_DK" :enable-paging-server="false"
                     panelDataHeight="300"
                    :allowPaging="true" :showFilter="true" >
                    </DataGrid>                   
                </div>
                <div>
                    <DataGrid v-bind:columns="columns.grvTTChiTiet_NT" v-show="radio_loaicuoc == 1"
                     ref="grvTTChiTiet_NT" :dataSource="options.grvTTChiTiet_NT" :enable-paging-server="false"
                     panelDataHeight="300"                    
                    :allowPaging="true" :showFilter="true">
                    </DataGrid>
                </div>
                <div>
                     <DataGrid v-bind:columns="Data_CNTN.headers" v-show="radio_loaicuoc == 2"
                     :showColumnCheckbox="true"
                     ref="grvTTChiTiet_CNTN" :dataSource="Data_CNTN.data" :enable-paging-server="false"
                    panelDataHeight="300"                     
                     dataKeyField="ID"
                    :allowPaging="true" :showFilter="true" @selectedItemsChanged="selectedItems_CNTN_Selected">
                    </DataGrid> 
                </div>
                   
            </div>

        </div>
    </div>
</form>
</div>
</template>
<script>
import moment from "moment";
import DatePicker from "vue2-datepicker";
import "vue2-datepicker/locale/vi.js";
import "vue2-datepicker/index.css";
import define from "./define";
export default {
    prop: [],
    components: {
        moment,
        DatePicker,
    },
    data() {
    return {
      header: {
        title: "Tra cứu cảnh báo cước nóng",
        list: [{
          name: "Lập hợp đồng",
          link: {
            name: "Ui.cards"
          }
        },
        {
          name: "Lắp đặt mới",
          link: {
            name: "Ui.buttons"
          },
        },
        ],
    },
    dateFormat: 'DD/MM/YYYY',
    dateFormatMonth: 'MM/YYYY',
    typeFormat: 'datetime',
    typeFormatMonth: 'month',
    dateConfig: {
      dateFormat: 'd/m/Y',
      altFormat: 'd/m/Y'
    },
    showTimePanel: false,
    radio_loaicuoc: 0,
    cbotrangthaixuly: [],
    cbokieunhantin: [],
    txtMaThueBao: null,
    txtTuNgay: null,
    txtDenNgay: null,

    ID: null,
    ma_tb: null,

    success_code: "BSS-00000000",

    current: {
        mathuebao: null,
        kieutinnhan: null,
        tungay: null,
        denngay: null,
        trangthaixuly: null,            
    },

    form: {
        kieutinnhan: null,
        trangthaixuly: null,
    },

    options: {
        grvTTChiTiet_DK: [],
        grvTTChiTiet_NT: [],
        grvTTChiTiet_CNTN: []
    },

    kieusmm: [
        {
            id_sms: 1,
            kieu_sms: "Không chọn"
        },
        {
            id_sms: 2,
            kieu_sms: "Quảng bá"
        },
        {
            id_sms: 3,
            kieu_sms: "Cảnh báo"
        },
         {
            id_sms: 4,
            kieu_sms: "Thông báo khóa máy"
        },
        {
            id_sms: 5,
            kieu_sms: "Thông báo mở máy"
        },                               
    ],
    trangthai: [
        {
            id: 1,
            trangthai: "Không chọn"
        },
        {
            id: 2,
            trangthai: "Chưa xử lý"
        },
        {
            id: 3,
            trangthai: "An toàn"
        },
        {
            id: 4,
            trangthai: "Không an toàn"
        },
        {
            id: 5,
            trangthai: "Khóa máy"
        },                      
    ],

    columns: {
        grvTTChiTiet_DK: [
            {
                fieldName: "STT",
                headerText: "STT",
                allowFiltering: true,
                allowSorting: true,
                width: '70'
            },
            {
                fieldName: "MSISDN",
                headerText: "Mã thuê bao",
                allowFiltering: true,
                allowSorting: true,
                width: 'auto'
            },
            {
                fieldName: "TEN_TB",
                headerText: "Tên thuê bao",
                allowFiltering: true,
                allowSorting: true,
                width: 'auto'
            },
            {
                fieldName: "DIACHI",
                headerText: "Địa chỉ",
                allowFiltering: true,
                allowSorting: true,
                width: 'auto'
            },
            {
                fieldName: "BL_NAME",
                headerText: "Ngưỡng",
                allowFiltering: true,
                allowSorting: true,
                width: 'auto',
                customAttributes: { class: 'font-weight-bold' }
            },
            {
                fieldName: "DATE_INS",
                headerText: "Ngày TH",
                allowFiltering: true,
                allowSorting: true,
                width: 'auto',
                customAttributes: { class: 'font-weight-bold' }
            },
            {
                fieldName: "USER_INS",
                headerText: "Người TH",
                allowFiltering: true,
                allowSorting: true,
                width: 'auto',
                customAttributes: { class: 'font-weight-bold' }
            },
            {
                fieldName: "MONTH_LIVE",
                headerText: "Tháng QL",
                allowFiltering: true,
                allowSorting: true,
                width: 'auto'
            },
            {
                fieldName: "NOTE",
                headerText: "Ghi chú",
                allowFiltering: true,
                allowSorting: true,
                width: 'auto'
            }
        ],
        grvTTChiTiet_NT: [
            {
                fieldName: "STT",
                headerText: "STT",
                allowFiltering: true,
                allowSorting: true,
                width: '70'
            },
            {
                fieldName: "MSISDN",
                headerText: "Mã thuê bao",
                allowFiltering: true,
                allowSorting: true,
                width: 'auto'
            },
            {
                fieldName: "TEN_TB",
                headerText: "Tên thuê bao",
                allowFiltering: true,
                allowSorting: true,
                width: 'auto'
            },
            {
                fieldName: "DIACHI",
                headerText: "Địa chỉ",
                allowFiltering: true,
                allowSorting: true,
                width: 'auto'
            },
            {
                fieldName: "CONTENT",
                headerText: "Nội dung",
                allowFiltering: true,
                allowSorting: true,
                width: 'auto'
            },
            {
                fieldName: "DATE_INS",
                headerText: "Ngày nhắn",
                allowFiltering: true,
                allowSorting: true,
                width: 'auto'
            },
            {
                fieldName: "TYPE_SMS",
                headerText: "Kiểu nhắn",
                allowFiltering: true,
                allowSorting: true,
                width: 'auto'
            },
            {
                fieldName: "HOTBILL_LIMIT",
                headerText: "Ngưỡng",
                allowFiltering: true,
                allowSorting: true,
                width: 'auto'
            },
        ]
    },

    Data_CNTN: {
        headers: [         
            {
                fieldName: "STT",
                headerText: "STT",
                allowFiltering: true,
                allowSorting: true,
                width: '70'
            },
            {
                fieldName: "MSISDN",
                headerText: "Mã TB",
                allowFiltering: true,
                allowSorting: true,
                width: 'auto'
            },
            {
                fieldName: "TEN_TB",
                headerText: "Tên TB",
                allowFiltering: true,
                allowSorting: true,
                width: 'auto'
            },
            {
                fieldName: "DIACHI",
                headerText: "Địa chỉ",
                allowFiltering: true,
                allowSorting: true,
                width: 'auto'
            },
            {
                fieldName: "CONTENT_SMS",
                headerText: "Nội dung SMS",
                allowFiltering: true,
                allowSorting: true,
                width: 'auto'
            },
            {
                fieldName: "DATE_INS",
                headerText: "Ngày TH",
                allowFiltering: true,
                allowSorting: true,
                width: 'auto'
            },
            {
                fieldName: "TRANGTHAI",
                headerText: "TT xử lý",
                allowFiltering: true,
                allowSorting: true,
                width: 'auto'
            },
            {
                fieldName: "UPDATE_BY",
                headerText: "Người xử lý",
                allowFiltering: true,
                allowSorting: true,
                width: 'auto'
            },
        ],
        data: [],
        selected: 0,
        checked: []
    }
    

    };
     },
    mounted() {
      this.loadThongTin();
      this.setDateNow();
    },

    methods: {
        loadThongTin() {
            this.loadList();            
        },
        setDateNow() {
            this.txtTuNgay = moment(new Date()).format(this.dateFormat);
            this.txtDenNgay = moment(new Date()).format(this.dateFormat);
        },

        loadList() {
            this.cbokieunhantin = this.kieusmm;
            this.form.kieutinnhan = this.cbokieunhantin[0].id_sms;     
            this.cbotrangthaixuly  = this.trangthai;
            this.form.trangthaixuly = this.cbotrangthaixuly[0].id;
        },

        selectedItems_CNTN_Selected: function (dataKeys) {
            this.Data_CNTN.checked = dataKeys
            if (this.Data_CNTN.data != null) {
                this.Data_CNTN.data.forEach(function (item) {
                    if (dataKeys.filter(x => x === item.ID).length > 0) {
                    item.ischecked = true
                    } else {
                    item.ischecked = false
                    }
                })
            }
        },
        btnTimKiem_Click() {
            console.log(this.txtMaThueBao);
            
            if (this.txtMaThueBao ==null || this.txtMaThueBao=="") {
                this.txtMaThueBao = null
                this.$toast.error("Chưa nhập mã thuê bao !");
                this.options.grvTTChiTiet_DK = null
                this.options.grvTTChiTiet_NT = null
                this.Data_CNTN.data = null
            }
            else{
                let  sotb = "84" + this.txtMaThueBao
                console.log("txtmatb:", this.txtMaThueBao)
                console.log(sotb)
                this.txtMaThueBao ==null
                let tungay = this.txtTuNgay ? this.txtTuNgay : "" ;
                let denngay = this.txtDenNgay ? this.txtDenNgay: "";
                switch (this.radio_loaicuoc) {
                    case 0:
                        this.TimKiemDSThueBaoDKCuocNong(sotb, tungay, denngay);
                        break;
                    case 1:
                        this.TimKiemDSTinNhanCuocNong(sotb, tungay, denngay);
                        break;
                    case 2:
                        this.TimKiemDSCuocTheoNgay(sotb, tungay, denngay);
                        break; 
                                  
                }
            }
        },
        async TimKiemDSThueBaoDKCuocNong(sotb, tungay, denngay) {
            try {
                this.options.grvTTChiTiet_DK = null
                let resDK = await this.axios.post("ccbs/executor/ts_tracuu_cuocnong_dk", 
                {
                    tu_ngay: tungay,
                    so_tb: sotb,
                    den_ngay: denngay
                });
                if (resDK.data.error_code && resDK.data.error_code === this.success_code) {
                    let Obj = resDK.data.data
                    let tbdData = []
                    if (Obj != null) {
                        
                        for (let i = 0; i < Obj.length; i ++) {
                            let val = []
                            val.STT = i + 1
                            val.MSISDN = Obj[i].MSISDN
                            val.TEN_TB = Obj[i].TEN_TB
                            val.DIACHI = Obj[i].DIACHI
                            val.BL_NAME = Obj[i].BL_NAME
                            val.DATE_INS = Obj[i].DATE_INS
                            val.USER_INS = Obj[i].USER_INS
                            val.MONTH_LIVE = Obj[i].MONTH_LIVE
                            val.NOTE = Obj[i].NOTE
                            tbdData.push(val)
                        }                        
                        this.options.grvTTChiTiet_DK = tbdData
                        this.options.grvTTChiTiet_DK = [...this.options.grvTTChiTiet_DK]
                    }
                }
                else {
                     this.$toast.error("Không tìm thấy dữ liệu thoả mãn yêu cầu tìm kiếm!");
                }
                this.loading(false);

            } catch (err) {
                this.$toast.error(err.message);
            }

        },
        async TimKiemDSTinNhanCuocNong(sotb, tungay, denngay) {
            try {
                let typeSms = ''
                switch (this.form.kieutinnhan) {
                    case "1":
                        typeSms = 0
                        break;
                    case "2":
                        typeSms = 1
                        break;
                    case "3":
                        typeSms = 2
                        break;
                    case "4":
                        typeSms = 3
                        break;
                    case "5":
                        typeSms = 4
                        break;
                }
                this.options.grvTTChiTiet_NT = null
                let resCN = await this.axios.post("ccbs/executor/ts_tracuu_cuocnong_sms",
                {
                    type_sms: typeSms,
                    tu_ngay: tungay,
                    so_tb: sotb,
                    den_ngay: denngay
                });
                if (resCN.data.error_code && resCN.data.error_code === this.success_code) {
                    let Obj = resCN.data.data
                    let tbdData = []
                    if (Obj != null) {
                        
                        for (let i = 0; i < Obj.length; i ++) {
                            let val = []
                            val.STT = i + 1
                            val.MSISDN = Obj[i].MSISDN
                            val.TEN_TB = Obj[i].TEN_TB
                            val.DIACHI = Obj[i].DIACHI
                            val.CONTENT = Obj[i].CONTENT
                            val.DATE_INS = Obj[i].DATE_INS
                            val.TYPE_SMS = Obj[i].TYPE_SMS
                            val.HOTBILL_LIMIT = Obj[i].HOTBILL_LIMIT
                            tbdData.push(val)
                        }
                        
                        this.options.grvTTChiTiet_NT = tbdData
                        this.options.grvTTChiTiet_NT = [...this.options.grvTTChiTiet_NT]                        
                    } 
                }
                else {
                     this.$toast.error("Không tìm thấy dữ liệu thoả mãn yêu cầu tìm kiếm!");
                }
                //this.loading(false);
                
            } catch (err) {
                this.$toast.error(err.message);
            }

        },
        async TimKiemDSCuocTheoNgay(sotb, tungay, denngay) {
            try {
                let typeTrangThai = ''
               
                switch (this.form.trangthaixuly) {
                    case "1":
                        typeTrangThai = ''
                        break;
                    case "2":
                        typeTrangThai = 0
                        break;
                    case "3":
                        typeTrangThai = 1
                        break;
                    case "4":
                        typeTrangThai = 2
                        break;
                    case "5":
                        typeTrangThai = 3
                        break;
                }
                this.Data_CNTN.data = null
                let resNgay = await this.axios.post("ccbs/executor/ts_tracuu_cuocnong_ngay",
                {
                    tu_ngay: tungay,
                    so_tb: sotb,
                    den_ngay: denngay,
                    status: typeTrangThai
                });
                if (resNgay.data.error_code && resNgay.data.error_code === this.success_code) {
                    let Obj = resNgay.data.data
                    let tbdData = []
                    if (Obj != null) {
                        
                        for (let i = 0; i < Obj.length; i ++) {
                            let val = []
                            val.STT = i + 1
                            val.MSISDN = Obj[i].MSISDN
                            val.TEN_TB = Obj[i].TEN_TB
                            val.DIACHI = Obj[i].DIACHI
                            val.CONTCONTENT_SMSENT = Obj[i].CONTENT_SMS
                            val.DATE_INS = Obj[i].DATE_INS
                            val.TRANGTHAI = Obj[i].TRANGTHAI
                            val.UPDATE_BY = Obj[i].UPDATE_BY
                            val.ID = Obj[i].ID
                            tbdData.push(val)
                        }
                        
                        this.Data_CNTN.data = tbdData
                        this.Data_CNTN.data = [...this.Data_CNTN.data]                        
                    } 
                } 
                else {
                     this.$toast.error("Không tìm thấy dữ liệu thoả mãn yêu cầu tìm kiếm!");
                }
                //this.loading(false);
            } catch (err) {
                this.$toast.error(err.message);
            }
        },
        btnCapNhat_Click() {
            try {               
                let typeTrangThai = ''
                 switch (this.form.trangthaixuly) {
                    case "1":
                        typeTrangThai = ''
                        break;
                    case "2":
                        typeTrangThai = 0
                        break;
                    case "3":
                        typeTrangThai = 1
                        break;
                    case "4":
                        typeTrangThai = 2
                        break;
                    case "5":
                        typeTrangThai = 3
                        break;
                }
                // xủ lý dữ liệu row chọn
                let data = this.Data_CNTN.data.filter(x => x.ischecked)
                if (data.length > 0) {
                    data.forEach( (item) => {
                        this.Update(item.ID, item.MSISDN, typeTrangThai);                        
                    })
                }                

            } catch (err) {
                this.$toast.error(err.message);
                this.loading(false);
            }

        },
        async Update(ID, matb, loai) {
            try {
                let resCapNhat = await this.axios.post("ccbs/executor/ts_cuocnong_capnhat_ttxlsms",
                {
                    so_tb: matb,
                    id: ID,
                    status: loai

                })
                if (resCapNhat.data.error_code === this.success_code) {
                    this.$toast.success("Cập nhật thành công!");
                } else {
                    this.$toast.success("Cập nhật không thành công!");
                }
                this.loading(false);
            } catch (err) {

            }

        },
        Clear() {
            this.txtMaThueBao = null;
            this.setDateNow();

        },

        toggleTimePanel() {
        this.showTimePanel = !this.showTimePanel
        },
        handleOpenChange() {
        this.showTimePanel = false
        },
        handleRangeClose() {
        this.showTimeRangePanel = false
        },
        onlyNumber ($event) {
        //console.log($event.keyCode); //keyCodes value
        let keyCode = ($event.keyCode ? $event.keyCode : $event.which);
        if ((keyCode < 48 || keyCode > 57) && keyCode !== 13) { // 46 is dot
            $event.preventDefault();
        }
}
    }

}
</script>
<style>
  .mx-datepicker {
    width: 100%;
  }
  .mx-icon-calendar {
    font-style: normal !important;
  }
  li.none-active a {
    color: #d3d3d3 !important;
  }
  button.none-active a {
    color: #d3d3d3 !important;
  }
input::-webkit-outer-spin-button,
input::-webkit-inner-spin-button {
  -webkit-appearance: none;
  margin: 0;
}

input[type=number] {
  
}
</style>