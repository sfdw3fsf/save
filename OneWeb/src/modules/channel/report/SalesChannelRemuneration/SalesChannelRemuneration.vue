<template>
    <div class="main-wrapper">
        <breadcrumb :header="header" />
        <ActionTop :actions="actions" @onActionClick="onActionClick"/>
         <div class="page-content">
            <div class="box-form">
                <div class="legend-title">Thông tin chi trả thù lao kênh bán</div>
                <div class="row">

                    <div class="col-sm-6 col-12">

                        <div class="info-row">
                            <div class="key">Kênh bán <k-required-marker/></div>
                            <div class="value">
                                <div class="select-custom" ref="ds_kenhban">
                                    <select2 :settings="{ dropdownParent: $refs['ds_kenhban'] }"
                                        v-model="kenhban_selected" class="select2"
                                        :options="ds_kenhban.map(e=> ({id: e.nhanvien_id, text: e.ten_kb}))"
                                        @select="changeKenhBan"
                                        >
                                    </select2>
                                </div>  
                            </div>
                        </div>
                        <div class="info-row">
                            <div class="key">Địa chỉ</div>
                            <div class="value">
                                <input type="text" readonly :value="input_diachi" @change="e=>input_diachi=e.target.value" class="form-control">
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-sm-6 col-12">
                                <div class="info-row">
                                    <div class="key">Tài khoản</div>
                                    <div class="value">
                                        <input type="text" readonly :value="input_taikhoan" @change="e=>input_taikhoan=e.target.value" class="form-control">
                                    </div>
                                </div>
                            </div>
                            <div class="col-sm-6 col-12">
                                <div class="info-row">
                                    <div class="key">Ngân hàng</div>
                                    <div class="value">
                                        <div class="select-custom" ref="ds_nganhang">
                                            <select2 :settings="{ dropdownParent: $refs['ds_nganhang'] }"
                                                v-model="nganhang_selected" class="select2"
                                                :options="ds_nganhang.map(e=> ({id: e.nganhang_id, text: e.ten_nh}))"
                                                >
                                            </select2>
                                        </div>  
                                    </div>
                                </div>
                            </div>
                        </div>

                        <div class="row">
                            <div class="col-sm-6 col-12">
                                <div class="info-row">
                                    <div class="key">HT thanh toán</div>
                                    <div class="value">
                                        <div class="select-custom" ref="ds_hinhthuctt">
                                            <select2 :settings="{ dropdownParent: $refs['ds_hinhthuctt'] }"
                                                v-model="hinhthuc_tt_selected" class="select2"
                                                :options="ds_hinhthuctt.map(e=> ({id: e.httt_id, text: e.hinhthuc}))"
                                                >
                                            </select2>
                                        </div>  
                                    </div>
                                </div>
                            </div>
                            <div class="col-sm-6 col-12">
                                <div class="info-row">
                                    <div class="key">Ngày ngân hàng</div>
                                    <div class="value">
                                        <div class="input-icon-right">
                                            <k-date-picker v-model="ngay_nganhang"/>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-sm-6 col-12">
                                <div class="info-row">
                                    <div class="key">Ví VNPT Pay</div>
                                    <div class="value">
                                        <input type="text" :value="input_vi_vnpt_pay" @change="e=>input_vi_vnpt_pay=e.target.value" class="form-control">
                                    </div>
                                </div>
                            </div>
                            <div class="col-sm-6 col-12">
                                <div class="info-row">
                                    <div class="key">Ghi chú</div>
                                    <div class="value">
                                        <input type="text" :value="input_ghichu" @change="e=>input_ghichu=e.target.value" class="form-control">
                                    </div>
                                </div>
                            </div>
                        </div>

                    </div>

                    <div class="col-sm-6 col-12">

                        <div class="row">
                            <div class="col-sm-6 col-12">
                                <div class="info-row">
                                    <div class="key">Tháng<k-required-marker/></div>
                                    <div class="value">
                                        <div class="input-icon-right">
                                            <!-- <k-date-picker format="MM/YYYY" v-model="thang_hn" :disabled="!check_thanghn"/> -->
                                            <ejs-datepicker class="form-control bg-none bor0 fw6 text-red"
                                                placeholder="Chọn tháng" :format="'MM/y'" :start="'Year'"
                                                :depth="'Year'" :showClearButton="false" v-model="thang_hn"
                                                @change="changeThang"
                                                :allowEdit="true"/>
                                        </div>
                                        <!-- <input type="text" ref="inputThang" @blur="burThang" :value="input_thang" @change="e=>input_thang=e.target.value" class="form-control"> -->
                                    </div>
                                </div>
                            </div>
                            <div class="col-sm-6 col-12">
                                <!-- <div class="info-row">
                                    <div class="key">Ghi chú</div>
                                    <div class="value">
                                        <input type="text" :value="input_ghichu" @change="e=>input_ghichu=e.target.value" class="form-control">
                                    </div>
                                </div> -->
                                <div class="info-row">
                                    <div class="key">Tổng nợ</div>
                                    <div class="value">
                                        <!-- <vue-numeric class="form-control bold tright" separator=","  :value="input_tientra" @change="e=>input_tientra=e.target.value" placeholder="0" output-type="Number"></vue-numeric> -->
                                        <input type="text" readonly style="text-align: right !important;" v-model="tongno" @keypress="isNumber($event)" class="form-control">
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="info-row">
                            <div class="key">KT chi trả</div>
                            <div class="value">
                                <div class="select-custom" ref="ds_chitra">
                                    <select2 :settings="{ dropdownParent: $refs['ds_chitra'] }"
                                        v-model="chitra_selected" class="select2"
                                        :options="ds_chitra.map(e=> ({id: e.nhanvien_id, text: e.ten_kb_full}))"
                                        >
                                    </select2>
                                </div>  
                            </div>
                        </div>

                        <div class="row">
                            <div class="col-sm-6 col-12">
                                <div class="info-row">
                                    <div class="key">Ngày thanh toán <k-required-marker/></div>
                                    <div class="value">
                                        <div class="input-icon-right">
                                            <k-date-picker v-model="ngay_tt"/>
                                        </div>
                                        <!-- <input type="text" :value="input_ngaytt" @change="e=>input_ngaytt=e.target.value" class="form-control"> -->
                                    </div>
                                </div>
                            </div>
                            <div class="col-sm-6 col-12">
                                <div class="info-row">
                                    <div class="key">Số chứng từ</div>
                                    <div class="value">
                                        <input type="text" :value="input_sochungtu" @change="e=>input_sochungtu=e.target.value" class="form-control">
                                    </div>
                                </div>
                            </div>
                        </div>

                        <div class="info-row">
                            <div class="key">Loại tiền <k-required-marker/></div>
                            <div class="value">
                                <div class="select-custom" ref="ds_loaitien">
                                    <select2 :settings="{ dropdownParent: $refs['ds_loaitien'] }"
                                        v-model="loaitien_selected" class="select2"
                                        :options="ds_loaitien.map(e=> ({id: e.loaitien_id, text: e.ma_loaitien}))"
                                        >
                                    </select2>
                                </div>  
                            </div>
                        </div>
                        <div class="info-row">
                            <div class="key">Tiền trả <k-required-marker/></div>
                            <div class="value">
                                <!-- <vue-numeric class="form-control bold tright" separator=","  :value="input_tientra" @change="e=>input_tientra=e.target.value" placeholder="0" output-type="Number"></vue-numeric> -->
                                <input type="text" style="text-align: right !important;" v-model="tientra" @keypress="isNumber($event)" class="form-control">
                            </div>
                        </div>

                    </div>

                </div>
            </div>

            <div class="box-form">
                <div class="legend-title">Thông tin chi tiết chi trả</div>
                <KDataGrid
                    ref="gridDs"
                    :columns="columns"
                    :dataSource="dataSources"
                    :showColumnCheckbox="true"
                    :enable-paging-server="false"
                    :allowPaging="true"
                    :showFilter="true"
                    :enabledSelectFirstRow="true"
                    :selectionSettings="{ checkboxMode: 'ResetOnRowClick' }"
                    :editSettings="editSettings"
                    @onRowSelected="onSelectedRow"
                    @rowSelected="rowGridSelected"/>
            </div>
         </div>
    </div>
</template>
<script>
import breadcrumb from '@/components/kylq_components/breadcrumb.vue'
import ActionTop from '@/components/kylq_components/ActionTop.vue'
import KDataGrid from '@/components/kylq_components/KDataGrid'
import KDatePicker from '@/components/kylq_components/KDatePicker.vue'
import moment from 'moment'
import API from './API'
import KRequiredMarker from '@/components/kylq_components/KRequiredMarker.vue'
export default {
    name:'SalesChannelRemuneration',
    components:{
        breadcrumb, ActionTop, KDataGrid, KDatePicker, KRequiredMarker
    },
    data(){
        return {
            header: {
                title: "Chi thù lao kênh bán",
                list: [
                    {
                        name: "Chi thù lao kênh bán",
                        link: { name: "Ui.buttons" },
                        active: true
                    },
                ]
            },
            actions:[
                {
                    id:'nhapmoi',
                    name:'Nhập mới',
                    active: true,
                    icon_class:'one-file-plus'
                },
                {
                    id:'gachno',
                    name:'Gạch nợ',
                    active: true,
                    icon_class:'one-sign'
                },
                // {
                //     id:'huy',
                //     name:'Huỷ',
                //     active: true,
                //     icon_class:'nc-icon-glyph ui-1_circle-remove'
                // },
                {
                    id:'huyphieu',
                    name:'Phiếu hủy',
                    active: true,
                    icon_class:'files_single-content-02 nc-icon-glyph'
                },
                {
                    id:'phieutra',
                    name:'Phiếu trả',
                    active: true,
                    icon_class:'files_single-content-02 nc-icon-glyph'
                },
            ],
            kenhban_selected:null,
            ds_kenhban:[],
            input_diachi:'',
            input_taikhoan:'',
            nganhang_selected:null,
            ds_nganhang:[],
            ds_hinhthuctt:[],
            hinhthuc_tt_selected:null,
            ngay_nganhang:null,
            ngay_tt:null,
            input_vi_vnpt_pay:'',
            input_ghichu:'',
            input_thang:'',
            ds_chitra:[],
            chitra_selected:null,
            input_ngaytt:'',
            input_sochungtu:'',
            ds_loaitien:[],
            loaitien_selected:null,
            input_tientra:0,
            input_tongno:0,
            selectIndex:0,
            columns:[
                {
                    fieldName:'ky_cuoc',
                    headerText:'Thời gian',
                    allowFiltering:true,
                    allowSorting:true
                },
                {
                    fieldName:'ten_kenhban',
                    headerText:'Kênh bán',
                    allowFiltering:true,
                    allowSorting:true
                },
                {
                    fieldName:'tong_no',
                    headerText:'Tổng nợ',
                    allowFiltering:true,
                    allowSorting:true,
                    textAlign:'right'
                    // type: "Number",
                    // format: "N0"
                }, 
                {
                    fieldName:'tragoc',
                    headerText:'Đã trả',
                    allowFiltering:true,
                    allowSorting:true,
                    textAlign:'right'
                    // type: "Number",
                    // format: "N0"
                },
                {
                    fieldName:'con_no',
                    headerText:'Còn nợ',
                    allowFiltering:true,
                    allowSorting:true,
                    allowEditing:true,
                    
                    // type: "Number",
                    // format: "N0"
                    //validationRules: {maxLength: [(args) => {return args['value'].length <= 200;}, 'Giá trị phải nhỏ hơn 200 ký tự!'] }
                },
            ],
            dataSources:[],
            editSettings:{ allowEditing: true, mode:'Batch'},
            thang_hn:null

        }
    },
    computed:{
        tientra: {
            get: function() {
                return this.input_tientra? this.numberWithCommas(this.input_tientra): 0
            },
            set: function(newValue) {
                var replaced = newValue.replaceAll(",", "")
                this.input_tientra = Number(replaced)
            }
        },
        tongno:{
            get: function() {
                return this.input_tongno? this.numberWithCommas(this.input_tongno): 0
            },
            set: function(newValue) {
                var replaced = newValue.replaceAll(",", "")
                this.input_tongno = Number(replaced)
            }
        }
    },
    methods:{
        onActionClick(action){
            if(action.id=='gachno'){
                this.GachNo()
            }else if(action.id=='huy'){
                

                this.input_diachi=''
                this.input_taikhoan=''
                this.ngay_nganhang=null
                this.ngay_tt=null
                this.kenhban_selected=null
                this.input_vi_vnpt_pay=''
                this.input_ghichu=''
                this.input_thang=''
                this.input_ngaytt=''
                this.input_sochungtu=''
                this.input_tientra=0
                this.input_tongno=0
                this.dataSources=[]
                this.actions[this.actions.findIndex(x=>x.id=='gachno')].active=true
            }else if(action.id=='huyphieu'){
                this.input_thang=this.thang_hn?moment(this.thang_hn, 'MM/YYYY').format('YYYYMM'):null
                this.$router.push({path: 'PhieuHuy', query: {kenhban_id: this.kenhban_selected, user_huy_selected:'', ngay_tt:this.ngay_tt, thang_cn:this.input_thang} });
            }else if(action.id=='phieutra'){
                this.input_thang=this.thang_hn?moment(this.thang_hn, 'MM/YYYY').format('YYYYMM'):null
                this.$router.push({path: 'PhieuTra', query: {kenhban_id: this.kenhban_selected, user_huy_selected:'', ngay_tt:this.ngay_tt, thang_cn:this.input_thang} });
            }else if(action.id=='nhapmoi'){
                this.input_diachi=''
                this.input_taikhoan=''
                this.ngay_nganhang=null
                this.ngay_tt=null
                this.kenhban_selected=null
                this.input_vi_vnpt_pay=''
                this.input_ghichu=''
                this.input_thang=''
                this.input_ngaytt=''
                this.input_sochungtu=''
                this.input_tientra=0
                this.input_tongno=0
                this.thang_hn=null
                this.dataSources=[]
                this.actions[this.actions.findIndex(x=>x.id=='gachno')].active=true
            }
        },
        async onSelectedRow(data){
            if(!data){
                this.clear()
                return
            }
            let item=Array.isArray(data)?data[0]:data
        },
        rowGridSelected(args){
            this.selectIndex=args.rowIndex
        },
        async changeKenhBan(){
            let data=await this.get_thongtin_diachi_taikhoan_kenhban(this.kenhban_selected)
            if(data.length>0){
                this.input_diachi=data[0].diachi_tt?data[0].diachi_tt.toString():''
                this.input_taikhoan=data[0].stk?data[0].stk.toString():''
            }
            this.TongNo()
        },
        burThang(){
            this.TongNo()
        },
        async TongNo(){
            let data=await this.get_tongno_kenhban_theochuky({
                p_kenhban_id: this.kenhban_selected,
                p_thang :this.thang_hn?moment(this.thang_hn, 'MM/YYYY').format('YYYYMM'):null
            })

            if(data.length>0){
                this.input_tongno=data[0].tienno
            }else{
                this.input_tongno=0
            }
        },
        async GachNo(){
            if(this.kenhban_selected==null){
                this.$toast.error('Hãy chọn kênh bán !')
                return
            }
            if(!this.thang_hn||this.thang_hn.trim()==''){
                this.$toast.error('Nhập tháng!')
                //this.$refs.inputThang.focus()
                return
            }
            //chitra_thulao_kenhban_gachno
            let rs=await this.chitra_thulao_kenhban_gachno({
                kenhban_id: this.kenhban_selected,
                thang: this.thang_hn?moment(this.thang_hn, 'MM/YYYY').format('YYYYMM'):null,
                ktct_id: this.chitra_selected,
                taikhoan: this.input_taikhoan?this.input_taikhoan.trim():null,
                nganhang_id: this.nganhang_selected,
                ngay_tt: this.ngay_tt,
                sochungtu: this.input_sochungtu?this.input_sochungtu.trim():null,
                httt_id: this.hinhthuc_tt_selected,
                ngaynganhang: this.ngay_nganhang,
                loaitien_id: this.loaitien_selected,
                ghichu:this.input_ghichu?this.input_ghichu.trim():null,
                tientra: this.input_tientra
            })

            // let rs=await this.chitra_thulao_kenhban_gachno({
            //     "kenhban_id": 1,
            //     "thang": "202308",
            //     "ktct_id": "1",
            //     "taikhoan": "1",
            //     "nganhang_id": "1",
            //     "ngay_tt": null,
            //     "sochungtu": "202308",
            //     "httt_id": "202308",
            //     "ngaynganhang": null,
            //     "loaitien_id": "1",
            //     "ghichu": "abccđ",
            //     "tientra": 3050000
            // })

            if(rs.code==1){
                if(rs.data.length>0){
                    if(rs.data[0].error==200){
                        this.$toast.success('Thành công')
                        this.actions[this.actions.findIndex(x=>x.id=='gachno')].active=false
                        console.log('DATA', JSON.parse(rs.data[0].ketqua))
                        this.dataSources=JSON.parse(rs.data[0].ketqua).map(x=>{
                            x.tong_no=this.numberWithCommas(x.tong_no)
                            x.tragoc=this.numberWithCommas(x.tragoc)
                            x.con_no=this.numberWithCommas(x.con_no)
                            return x
                        })
                        this.TongNo()
                    }else{
                        this.$toast.error(rs.data[0].ketqua)
                    }
                }else{
                    this.$toast.error('Đã xảy ra lỗi, gạch nợ không thành công!')
                }
            }else{
                this.$toast.error(rs.data)
            }
        },
        changeThang(event){
            console.log('changeThang', event)
            if(!event.value){
                this.input_tongno='0'
                this.thang_hn=null
                return
            }
            this.thang_hn=moment(event.value).format('MM/YYYY')
            this.TongNo()
        },
        changeTienTra(event){
            //this.input_tientra=this.numberWithCommas(e.target.value.trim())
        },
        isNumber(evt) {
            evt = evt ? evt : window.event;
            var charCode = evt.which ? evt.which : evt.keyCode;
            if (charCode > 31 && (charCode < 48 || charCode > 57)) {
            evt.preventDefault();
            } else {
            return true;
            }
        },
        numberWithCommas(n) {
            return n.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",")
            // var parts = n.toString().split(".");
            // return (
            // parts[0].replace(/\B(?=(\d{3})+(?!\d))/g, ",") +
            // (parts[1] ? "." + parts[1] : "")
            // );
        },
        async chitra_thulao_kenhban_chon_nganhang(){
            try{
                this.loading(true)
                let response = await API.chitra_thulao_kenhban_chon_nganhang(this.axios)
                this.loading(false)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                    return response.data.data
                }else{
                    return []
                }
            }catch(e){
                this.loading(false)
                return []
            }
        },
        async chitra_thulao_kenhban_chon_httt(){
            try{
                this.loading(true)
                let response = await API.chitra_thulao_kenhban_chon_httt(this.axios)
                this.loading(false)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                    return response.data.data.map(x=>{
                        x.ten_kb_full=`${x.ma_kb} - ${x.ten_kb}`
                        return x
                    })
                }else{
                    return []
                }
            }catch(e){
                this.loading(false)
                return []
            }
        },
        async chitra_thulao_kenhban_chon_ktct(){
            try{
                this.loading(true)
                let response = await API.chitra_thulao_kenhban_chon_ktct(this.axios)
                this.loading(false)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                    return response.data.data.map(x=>{
                        x.ten_kb_full=`${x.ma_kb} - ${x.ten_kb}`
                        return x
                    })
                }else{
                    return []
                }
            }catch(e){
                this.loading(false)
                return []
            }
        },
        async chitra_thulao_kenhban_chonkenhban(){
            try{
                this.loading(true)
                let response = await API.chitra_thulao_kenhban_chonkenhban(this.axios)
                this.loading(false)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                    return response.data.data.map(x=>{
                        x.ten_kb_full=`${x.ten_kb}`
                        return x
                    })
                }else{
                    return []
                }
            }catch(e){
                this.loading(false)
                return []
            }
        },
        async chitra_thulao_kenhban_chon_loaitien(){
            try{
                this.loading(true)
                let response = await API.chitra_thulao_kenhban_chon_loaitien(this.axios)
                this.loading(false)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                    return response.data.data
                }else{
                    return []
                }
            }catch(e){
                this.loading(false)
                return []
            }
        },
        async get_thongtin_diachi_taikhoan_kenhban(kenhban_id){
            try{
                this.loading(true)
                let response = await API.get_thongtin_diachi_taikhoan_kenhban(this.axios, kenhban_id)
                this.loading(false)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                    return response.data.data
                }else{
                    return []
                }
            }catch(e){
                this.loading(false)
                return []
            }
        },
        async get_tongno_kenhban_theochuky(data){
            try{
                this.loading(true)
                let response = await API.get_tongno_kenhban_theochuky(this.axios, data)
                this.loading(false)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                    return response.data.data
                }else{
                    return []
                }
            }catch(e){
                this.loading(false)
                return []
            }
        },
        async chitra_thulao_kenhban_gachno(data){
            try{
                this.loading(true)
                let response = await API.chitra_thulao_kenhban_gachno(this.axios, data)
                this.loading(false)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                    return {
                        code:1,
                        data:response.data.data
                    }
                }else{
                    if(response&&response.data&&response.data.message){
                        return {
                            code:0,
                            data:response.data.message
                        }
                    }else{
                        return {
                            code:0,
                            data:'Đã xảy ra lỗi, gạch nợ không thành công!'
                        }
                    }
                }
            }catch(e){
                this.loading(false)
                if(e.data&&e.data.message){
                    return {
                        code:0,
                        data:e.data.message
                    }
                }else if(e.response&&e.response.data&&e.response.data.message){
                    return {
                        code:0,
                        data:e.response.data.message
                    }
                }else{
                    return {
                        code:0,
                        data:'Đã xảy ra lỗi, gạch nợ không thành công!'
                    }
                }
            }
        },
        async initDuLieu(){
            this.ds_nganhang=await this.chitra_thulao_kenhban_chon_nganhang()
            this.nganhang_selected=null
            // if(this.ds_nganhang.length>0){
            //     this.nganhang_selected=this.ds_nganhang[0].nganhang_id
            // }

            this.ds_hinhthuctt=await this.chitra_thulao_kenhban_chon_httt()
            this.hinhthuc_tt_selected=null
            // if(this.ds_hinhthuctt.length>0){
            //     this.hinhthuc_tt_selected=this.ds_hinhthuctt[0].httt_id
            // }
            this.ds_chitra=await this.chitra_thulao_kenhban_chon_ktct()
            this.chitra_selected=null
            // if(this.ds_chitra.length>0){
            //     this.chitra_selected=this.ds_chitra[0].nhanvien_id
            // }
            this.ds_kenhban=await this.chitra_thulao_kenhban_chonkenhban()
            this.kenhban_selected=null
            // if(this.ds_kenhban.length>0){
            //     this.kenhban_selected=this.ds_kenhban[0].nhanvien_id
            //     let data=await this.get_thongtin_diachi_taikhoan_kenhban(this.kenhban_selected)
            //     if(data.length>0){
            //         this.input_diachi=data[0].diachi_tt?data[0].diachi_tt.toString():''
            //         this.input_taikhoan=data[0].stk?data[0].stk.toString():''
            //     }
                
            // }else{
            //     this.input_diachi=''
            //     this.input_taikhoan=''
            // }
            this.ds_loaitien=await this.chitra_thulao_kenhban_chon_loaitien()
            this.loaitien_selected=null
            // if(this.ds_loaitien.length>0){
            //     this.loaitien_selected=this.ds_loaitien[0].loaitien_id
            // }
            
        }
    },
    mounted(){
        setTimeout(()=>{
            this.initDuLieu()
        }, 500)
    }
}
</script>
<style>
.phieuhuy-body .page-content {
  position: unset !important;
}
</style>