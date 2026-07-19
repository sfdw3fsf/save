<template>
    <div id="outboundcustomercare_it" class="main-wrapper">
        <div class="breadcrumb-top">
            <div class="main-title">{{title}}</div>
            <ul class="breadcrumb">
                <li class="breadcrumb-item">
                    <div class="btn-group" id="list-useripcc" style="margin-right: 40px; float: right;">
                        <button type="button" class="btn btn-info btn-xs" style="color: black;font-style: italic;background: none">{{
                            this.user_ipcc.selected.user_ipcc }} {{ currentStatus && currentStatus != 'LOGOUT' ? ' on ' + this.user_ipcc.selected.line : ''}}
                        </button>
                        <button type="button" class="btn btn-info dropdown-toggle dropdown-toggle-split" data-toggle="dropdown"
                                aria-haspopup="true" aria-expanded="false">
                            <span class="sr-only">Toggle Dropdown</span>
                        </button>
                        <div class="dropdown-menu" style='border: 0px; min-width: 350px'>
                            <DataGrid
                            v-bind:columns="user_ipcc.header"
                            :gridLines="'Both'"
                            ref="user_ipcc"
                            :dataSource="user_ipcc.list"
                            :showFilter="false"
                            :allowPaging="false"
                            :enablePagingServer="false"
                            :enabledSelectFirstRow="true"
                            @rowSelected="gridUserIpccSelected"
                            @selectedRowChanged="onSelectedAgentChanged"
                            :commandColumn="{headerText: '', width: 50, cellCssClass: 'center', firstColumn: true, show: true}"
                            :commands="user_ipcc.commands"
                            @commandClicked="gridCommand_user_ipcc"
                            >
                            </DataGrid>
                        </div>
                    </div>
                </li>
                <li class="breadcrumb-item">
                    <div class="info-row" style='width:350px; float: right;margin-right: 40px'>
                        <div class="key" style="padding-top: 5px!important;">
                            <span  class="badge badge-pill badge-success" :class="{'badge-danger': !reconnect }">{{ statusConnect }}</span>
                        </div>
                        <div class="value">
                            <ejs-dropdowntree ref="refActions" id='dropdowntree' :fields='this.fields' :placeholder="'Chọn Hoạt Động'" cssClass="dropdowntree-icons list-actions" v-on:select='this.changeActions'></ejs-dropdowntree>
                        </div>
                    </div>
                </li>
            </ul>
        </div>
        
        <div class="list-actions-top">
            <ul class="list" style="overflow: inherit !important; white-space: inherit !important;">
                <li v-if="btnLayTTMoi.Visible">
                    <a style="cursor: pointer;" title='Lấy thông tin mới'>
                        <span class="icon one-file-attach"></span> Lấy TT
                    </a>
                </li>
                <li class="dropdown">
                    <a href="javascript:void(0)" class="dropdown-toggle" data-toggle="dropdown" title="Thống kê">
                        <span class="icon one-file-attach"></span> Lấy phiếu
                    </a>
                    <ul class="dropdown-menu ui-lay-phieu" >
                        <li>
                            <a href="javascript:void(0)" @click="LayPhieu_LapMoi" style="cursor: pointer;color: #007bff; text-decoration: none">
                                <span class="icon one-file-attach"></span>Lấy phiếu KS lắp mới
                            </a>
                        </li>
                        <li>
                            <a href="javascript:void(0)" @click="LayPhieu_BaoHong" style="cursor: pointer;color: #007bff; text-decoration: none">
                                <span class="icon one-file-attach"></span>Lấy phiếu KS báo hỏng
                            </a>
                        </li>
                    </ul>
                </li>
                <!-- <li>
                    <a style="cursor: pointer;" title='Bỏ qua phiếu không xử lý nữa' @click="tsbtnBoQua_Click">
                        <span class="icon nc-icon-glyph ui-1_circle-remove"></span> Bỏ phiếu
                    </a>
                </li> -->
                <li>
                    <a style="cursor: pointer;" @click="tsbtnGhiLai_Click">
                         <span class="icon one-save"></span> Ghi lại
                    </a>
                </li>
                <!-- <li>
                    <a style="cursor: pointer;" @click="tsbtnDieuLai_Click">
                        <span class="icon arrows-1_curved-next nc-icon-glyph"></span> Điều lại
                    </a>
                </li> -->
                <li>
                    <a style="cursor: pointer;" @click="tsBtn_LichSuHong_Click">
                        <span class="icon one-file-edit3"></span>{{tsBtn_LichSuHong.Text}}
                    </a>
                </li>
                <li>
                    <a style="cursor: pointer;" @click="tsBtn_LichSuKS_Click">
                        <span class="icon one-calendar-history"></span>Lịch sử KS
                    </a>
                </li>
                <li v-if="tsbtnLogEditKS.Visible">
                    <a style="cursor: pointer;">
                        <span class="icon files_single-copy-04 nc-icon-glyph"></span> Lịch sử sửa phiếu
                    </a>
                </li>
                <li>
                    <a style="cursor: pointer;" @click="tsBtn_ClearAns_Click">
                        <span class="icon one-trash"></span> Xóa câu trả lời
                    </a>
                </li>
                <li>
                    <a style="cursor: pointer;" @click="LayPhieu_DangGiu">
                        <span class="icon nc-icon-glyph files_single-content-02"></span> Phiếu đang giữ
                    </a>
                </li>

                

            </ul>
            
        </div>
        <div class="page-content">
            <div v-if="displaySpliter.Visible">
                <div class="box-form">
                    <div class="legend-title">Thông tin tìm kiếm</div>
                    <div class="row">
                        <div class="col-sm-3 col-12">
                            <div class="info-row">
                                <div class="key w100">Lọc trạng thái</div>
                                <div class="value">
                                    <div class="select-custom">
                                        <select  v-model="trangthailoc_selected" class="form-control">
                                            <option v-for="item in ds_trangthai_loc" :key="item.id" :value="item.id">{{item.text}}</option>
                                        </select>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="col-sm-3 col-12">
                            <div class="info-row">
                                <div class="key w100">Ngày bắt đầu</div>
                                <div class="value">
                                    <div class="input-icon-right">
                                        <k-date-picker v-model="ngay_bd"/>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="col-sm-3 col-12">
                            <div class="info-row">
                                <div class="key w100">Ngày kết thúc</div>
                                <div class="value">
                                    <div class="input-icon-right">
                                        <k-date-picker v-model="ngay_kt"/>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="col-sm-3 col-12">
                            <div class="info-row">
                                <div class="value">
                                    <button id="btn_laydulieu" @click="btnFetchData_Click" class="btn btn-second nocorner" style="width: 120px;">
                                        <span class="icon one-search"></span> Lấy dữ liệu
                                    </button>
                                </div>
                            </div>
                        </div>
                        
                    </div>
                </div>
                <CTKhaoSat ref="CTKhaoSat" :tbtl_id="tbtl_id"/>
                <div class="box-form">
                    <div class="legend-title">Danh sách phiếu</div>
                    <KDataGrid
                        ref="gridDSPhieu"
                        :columns="columns"
                        :dataSource="dataSources"
                        :enable-paging-server="false"
                        :allowPaging="true"
                        :showFilter="true"
                        :showColumnCheckbox="false"
                        :enabledSelectFirstRow="true"
                        @rowDoubleClicked="rowDoubleClicked"
                    />
                </div>
            </div>
            <div v-if="panelChiTiet.Visible">
                <KhaoSatTBTapTrungLM ref="KhaoSatTBTapTrungLM" v-if="CurrentForm==1" @clickCallIPCC="clickCallIPCC"/>
                <KhaoSatTBTapTrungBH ref="KhaoSatTBTapTrungBH" v-else  @clickCallIPCC="clickCallIPCC"/>
            </div>
           
        </div>
        <!-- Modal -->
        <LichSuBaoHongModal ref="lichSuBaoHongModal" :thuebao_id="thuebaobh_id"/>
        <TraCuuThueBaoKhaoSatModal ref="traCuuThueBaoKhaoSatModal" :ma_tb="matb_ks"/>
    </div>
</template>
<script>
//https://cntt.vnpt.vn/browse/BSS-79914
//frmKhaoSatTB_TapTrung
import KDatePicker from '@/components/kylq_components/KDatePicker.vue'
import KBreadCrumb from '@/components/kylq_components/mirana/KBreadCrumb'
import moment from 'moment'
import KDataGrid from '@/components/kylq_components/KDataGrid'
import LichSuBaoHongModal from './LichSuBaoHongModal.vue'
import CTKhaoSat from './CTKhaoSat.vue'
import KhaoSatTBTapTrungBH from './KhaoSatTBTapTrungBH.vue'
import KhaoSatTBTapTrungLM from './KhaoSatTBTapTrungLM.vue'
import API from './API'
import {getListDonViVNP} from '../../admin/category/DoiSoatDuLieuIT/Utils'
import TraCuuThueBaoKhaoSatModal from './TraCuuThueBaoKhaoSatModal.vue'
import SockJS from 'sockjs-client' // Import SockJS
import { Stomp } from '@stomp/stompjs' // Import Stomp

const actions = [
    {
        text:"Đăng nhập IPCC",
        status:"LOGIN"
    },
    {
        text:"Sẵn sàng nhận cuộc gọi",
        status:"READY_IB"
    },
    {
        text:"Logout IPCC",
        status:"LOGOUT",
        reason_code: "logout"
    },
    {
        text:"Đang bận",
        status:"DANG_BAN",
        hasChild: true,
        expanded: false,
        selectable: false,
        nodeChild: [
        {
            text:"Đi vệ sinh",
            status:"NOT_READY",
            reason_code: "time_in_wc"
        },
        {
            text:"Đi ăn trưa",
            status:"NOT_READY",
            reason_code: "time_in_lunch"
        },
        {
            text:"Đi văn tối",
            status:"NOT_READY",
            reason_code: "time_in_dinner"
        },
        {
            text:"Đi đào tạo",
            status:"NOT_READY",
            reason_code: "time_in_training"
        },
        {
            text:"Teamview",
            status:"NOT_READY",
            reason_code: "time_in_teamview"
        },
        {
            text:"Công việc khác",
            status:"NOT_READY",
            reason_code: "time_in_others"
        },
        ]
    },
    {
        text:"Chuẩn bị Outbound",
        status:"READY_OB"
    },
    {
        text:"Hoàn thành cuộc gọi",
        status:"WRAP_UP"
    }
]

export default {
    name:'SurveyConcentrateCustomer',
    components:{
        KBreadCrumb,
        KDatePicker,
        KDataGrid,
        LichSuBaoHongModal,
        CTKhaoSat,
        KhaoSatTBTapTrungBH,
        KhaoSatTBTapTrungLM,
        TraCuuThueBaoKhaoSatModal
    },
    data(){
        return {
            title: "Khảo sát khách hàng tập trung",
            btnLayTTMoi:{
                Visible:false
            },
            tsbtnLogEditKS:{
                Visible:false
            },
            displaySpliter:{
                Visible:true
            },
            panelChiTiet:{
                Visible:false
            },
            ctKhaoSat:{
                Show:false
            },
            ngay_bd:moment(new Date()).format('DD/MM/YYYY'),
            ngay_kt:moment(new Date()).format('DD/MM/YYYY'),
            trangthailoc_selected:0,
            ds_trangthai_loc:[
                {
                    id:0,
                    text:'Đang giữ'
                },
                {
                    id:1,
                    text:'Đã khảo sát'
                }
            ],
            columns:[
                {
                    fieldName: "MA_TINH",
                    headerText: "Tỉnh",
                    allowFiltering: true,
                    width:100,
                    visible:true
                },
                {
                    fieldName: "LOAIPHIEU",
                    headerText: "Hình thức",
                    allowFiltering: true,
                    width:100,
                    visible:true
                },
                {
                    fieldName: "MA_TB",
                    headerText: "Mã thuê bao",
                    allowFiltering: true,
                    width:100,
                    visible:true
                },
                {
                    fieldName: "NGUOI_TL",
                    headerText: "Người trả lời",
                    allowFiltering: true,
                    width:100,
                    visible:true
                },
                {
                    fieldName: "DIENTHOAI_LH",
                    headerText: "Điện thoại LH",
                    allowFiltering: true,
                    width:100,
                    visible:true
                },
                {
                    fieldName: "NGAY_HOI",
                    headerText: "Ngày hỏi",
                    allowFiltering: true,
                    width:100,
                    visible:true
                },
                {
                    fieldName: "TRANG_THAI",
                    headerText: "Kết quả",
                    allowFiltering: true,
                    width:100,
                    visible:true
                },
                {
                    fieldName: "GHICHU",
                    headerText: "Ghi chú",
                    allowFiltering: true,
                    width:100,
                    visible:true
                },
                {
                    fieldName: "LYDO_KTL",
                    headerText: "Lý do KTL",
                    allowFiltering: true,
                    width:100,
                    visible:true
                },
                {
                    fieldName: "NGAY_HT",
                    headerText: "Ngày hoàn thành",
                    allowFiltering: true,
                    width:100,
                    visible:true
                },
                {
                    fieldName: "LOAI_TB",
                    headerText: "Loại hình",
                    allowFiltering: true,
                    width:100,
                    visible:true
                },
                {
                    fieldName: "NGAYGIAO",
                    headerText: "Ngày giữ",
                    allowFiltering: true,
                    width:100,
                    visible:true
                },
                {
                    fieldName: "HEN_TU",
                    headerText: "Hẹn từ",
                    allowFiltering: true,
                    width:100,
                    visible:true
                },
                {
                    fieldName: "HEN_DEN",
                    headerText: "Hẹn đến",
                    allowFiltering: true,
                    width:100,
                    visible:true
                }
            ],
            dataSources:[],
            CurrentForm:1,//2:frmKhaoSatTB_TapTrung_BH  1:frmKhaoSatTB_TapTrung_LM,
            tsBtn_LichSuHong:{
                Text:'Lịch sử báo hỏng'
            },
            tinhChon:[],
            btnFetchData:{
                Enabled:true
            },
            DonViVNP:[],
            Divisions:[],
            WorkSpace:[],
            dataTinh:[],
            thuebaobh_id:0,
            matb_ks:'',
            dtThueBao:[],
            tbtl_id:0,

            stompClient: null,
            domainlocal: 'https://api-onebss.vnpt.vn/web-ipcc',
            socket: null,
            reconnect: true,
            statusConnect: 'Ngắt kết nối',
            fields: { dataSource: actions, value: 'status', text: 'text', child: 'nodeChild' },
            intervalId: null,
            isCalling: 0,
            thongtincuocgoi: {
                CallID: null,
                UserCode: null,
                Extension: null,
                Caller: null,
                Status: null,
                IbCall: null,
                ObCall: null,
                TimeStamp: null,
                Direction: null,
                NumberService: null,
                Hotline: null,
                BranchName: null
            },
            thongtincuocgoi_end: {
                CallID: null,
                UserCode: null,
                Extension: null,
                Caller: null,
                Status: null,
                Duration: null,
                WaitTime: null,
                TimeStamp: null
            },
            currentReasonCode: null,
            currentStatus: null,
            currentText: null,
            soluong_cuocgoi: '0',
            user_ipcc: {
                list: [],
                commands: [
                    { name: 'active', title: 'Active Line mới', cssClass: 'pad3 mar5 lh14 icon -ap one-check btn-plus btn-primary', iconCss: 'fa fa-times', width: 100 },
                ],
                header: [
                    {
                    fieldName: 'user_ipcc',
                    headerText: 'Agent',
                    allowFiltering: false,
                    allowHtml: true,
                    },
                    {
                    fieldName: 'line',
                    headerText: 'Line',
                    allowFiltering: false,
                    allowHtml: true,
                    width: 100,
                    textAlign: 'center',
                    template: () => {
                        return {
                        template: {
                            template: `<div>            
                                <input type="text" class="form-control text" :value="data.line" @keypress="isNumber($event)" @change="onChangeText" >
                            </div>`,
                            data: function () {
                            return {
                                data: {},
                            };
                            },
                            computed: {
                            parent() {
                                return this.$parent.$parent.$parent;
                            },           
                            },
                            methods: {
                            onChangeText: function (e) {
                                var index = this.parent.user_ipcc.list.findIndex(e => e.user_ipcc == this.data.user_ipcc)
                                this.parent.user_ipcc.list[index].line = e.target.value
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
                            },
                        },
                        };
                    },
                    }
                ],
                selected: {}
            },
            tt_nd: {},
        }
    },
    watch: {
      reconnect() {
        if (this.reconnect == true)
          this.statusConnect = 'Đã kết nối'
        else
          this.statusConnect = 'Ngắt kết nối'
      },
    },
    methods:{
        tsBtn_LichSuHong_Click(){
            if(this.CurrentForm==1){
                let query={
                    ma_tb: this.$refs.KhaoSatTBTapTrungLM?this.$refs.KhaoSatTBTapTrungLM.ma_tb:''
                }
                console.log('query', query)
                let routeData = this.$router.resolve({name: 'SearchInstallDetail', query: query});
                window.open(routeData.href, '_blank')
            }else{
                //
                this.thuebaobh_id=this.$refs.KhaoSatTBTapTrungBH?this.$refs.KhaoSatTBTapTrungBH.thuebao_id:0
                setTimeout(()=>{
                    this.$refs.lichSuBaoHongModal.showModal()
                },500)
            }
        },
        tsBtn_LichSuKS_Click(){
            this.matb_ks=''
            if(this.CurrentForm==1){
                this.matb_ks=this.$refs.KhaoSatTBTapTrungLM?this.$refs.KhaoSatTBTapTrungLM.ma_tb:''
            }else{
                this.matb_ks=this.$refs.KhaoSatTBTapTrungBH?this.$refs.KhaoSatTBTapTrungBH.ma_tb:''
            }
            this.$refs.traCuuThueBaoKhaoSatModal.showModal()
        },
        tsbtnGhiLai_Click(){
            if(this.displaySpliter.Visible){
                return
            }
            if(this.CurrentForm==1){
                this.$refs.KhaoSatTBTapTrungLM.tsbtnGhiLai_Click()
            }else{
                this.$refs.KhaoSatTBTapTrungBH.tsbtnGhiLai_Click()
            }
        },
        tsbtnDieuLai_Click(){
            if(this.displaySpliter.Visible){
                return
            }
            if(this.CurrentForm==1){
                this.$refs.KhaoSatTBTapTrungLM.tsbtnDieuLai_Click()
            }else{
                this.$refs.KhaoSatTBTapTrungBH.tsbtnDieuLai_Click()
            }
        },
        tsbtnBoQua_Click(){
            if(this.displaySpliter.Visible){
                return
            }
            if(this.CurrentForm==1){
                this.$refs.KhaoSatTBTapTrungLM.tsbtnBoQua_Click()
            }else{
                this.$refs.KhaoSatTBTapTrungBH.tsbtnBoQua_Click()
            }
        },
        tsBtn_ClearAns_Click(){
            if(this.displaySpliter.Visible){
                return
            }
            if(this.CurrentForm==1){
                this.$refs.KhaoSatTBTapTrungLM.ClearAnswer()
            }else{
                this.$refs.KhaoSatTBTapTrungBH.ClearAnswer()
            }
        },
        async btnFetchData_Click(){
            if(this.trangthailoc_selected!=1&&this.trangthailoc_selected!=2){
                if(!this.ngay_bd||!this.ngay_kt){
                    this.$toast.error('Dữ liệu có thể rất lớn, hãy chọn khoảng thời gian')
                    return
                }
            }
            this.btnFetchData.Enabled=false
            this.WorkSpace=[]
            this.dataSources=[]

            //     WinUI.WinUIBase.MultiDivisionSupport support = new WinUI.WinUIBase.MultiDivisionSupport(() => cskh.LayPhieuOutboundDTV(trangthai, start, end), cskh.ContinueFetch);
            this.dataTinh=await this.sp_layphieuoutbounddtv({
                vtrangthai: this.trangthailoc_selected,
                vstartdate:this.ngay_bd?this.ngay_bd:moment(new Date()).format('DD/MM/YYYY'),
                venddate: this.ngay_kt?this.ngay_kt:moment(new Date()).format('DD/MM/YYYY'), 
                vma_nd: this.$root.token.getUserName(), 
                vnhanvien_id:this.$root.token.getNhanVienID()
            })
            //upper case key json
            for(var i = 0; i < this.dataTinh.length; i++){ 
                for (var key in this.dataTinh[i]) {
                    if(key.toUpperCase() !== key){
                        this.dataTinh[i][key.toUpperCase()] = this.dataTinh[i][key];
                        delete this.dataTinh[i][key];
                    }
                }
            }
            console.log('dataTinh', this.dataTinh)
            this.Divisions=[]
            for(let i=0;i<this.DonViVNP.length;i++){
                if(this.DonViVNP[i].DonVi_ID<this.tinhChon.length&&this.tinhChon[this.DonViVNP[i].DonVi_ID]){
                    this.Divisions.push(this.DonViVNP[i].Ma_DV)
                }
            }
            console.log('Divisions', this.Divisions)
            await this.BeginGetData()
            await this.support_DataFetched()
        },
        async BeginGetData(){
            //GetData
            let dt=[]
            for(let i=0;i<this.Divisions.length;i++){
                dt=dt.concat(this.dataTinh.map(x=>Object.assign(x,{MA_TINH: this.Divisions[i]})))
            }
            this.WorkSpace=dt

        },
        async support_DataFetched(){
            this.btnFetchData.Enabled = true
            var dt=this.WorkSpace
            for(let i=0;i<dt.length;i++){
                dt[i].LOAIHINH=''
                dt[i].TRANGTHAI_XULY=''
                dt[i].LOAIPHIEU=''
                dt[i].LYDO_KTL=''
                dt[i].TRANG_THAI=''

                if(dt[i].LYDOKTL_ID){
                    let lydo=this.$refs.CTKhaoSat.cboLyDoKTL.data.find(x=>x.id==dt[i].LYDOKTL_ID)
                    dt[i].LYDO_KTL=lydo?lydo.text:''
                }
                if(dt[i].HINHTHUC!=null&&dt[i].HINHTHUC.toString()=='0'){
                    dt[i].LOAIPHIEU='Báo hỏng'
                }else if(dt[i].HINHTHUC!=null&&dt[i].HINHTHUC.toString()=='1'){
                    dt[i].LOAIPHIEU='Lắp mới'
                }
                if(dt[i].TRANGTHAI!=null&&dt[i].TRANGTHAI.toString()=='0'){
                    dt[i].TRANG_THAI='Không thành công'
                }else{
                    dt[i].TRANG_THAI='Thành công'
                }
                //WinUI.WinUIQLDHXLSuCo.MultiDB.FillSubData(row);
            }
            this.dataSources=dt
        },
        LayPhieu_DangGiu(){
            this.displaySpliter.Visible = true
            this.panelChiTiet.Visible = false
            this.title='Khảo sát khách hàng tập trung'
        },
        async LayPhieu_LapMoi(){
            let tbl=await this.sp_multisrv_rs9_lapmoi_ob(this.$root.token.getUserName())
            //upper case key json
            for(var i = 0; i < tbl.length; i++){ 
                for (var key in tbl[i]) {
                    if(key.toUpperCase() !== key){
                        tbl[i][key.toUpperCase()] = tbl[i][key];
                        delete tbl[i][key];
                    }
                }
            }
            if(tbl.length==0){
                return
            }
            this.title='Khảo sát khách hàng lắp mới'
            this.CreateChildForm(1, tbl);
        },
        async LayPhieu_BaoHong(){
           let tbl=await this.sp_multisrv_rs8_baohong_ob(this.$root.token.getUserName())
           //let tbl=null
            //upper case key json
            for(var i = 0; i < tbl.length; i++){ 
                for (var key in tbl[i]) {
                    if(key.toUpperCase() !== key){
                        tbl[i][key.toUpperCase()] = tbl[i][key];
                        delete tbl[i][key];
                    }
                }
            }
            if(tbl.length==0){
                return
            }
            this.title='Khảo sát khách hàng báo hỏng'
            this.CreateChildForm(2, tbl);
        },
        CreateChildForm(currentForm, tbl){
            this.displaySpliter.Visible = false
            this.panelChiTiet.Visible = true
            this.CurrentForm=currentForm
            this.dtThueBao = tbl

            if(this.CurrentForm==1){
                this.tsBtn_LichSuHong.Text = 'Chi tiết thi công'

                setTimeout(()=>{
                    this.$refs.KhaoSatTBTapTrungLM.ClearAnswer()
                    this.$refs.KhaoSatTBTapTrungLM.HienThi_Phieu(tbl)
                }, 500)
            }else{
                this.tsBtn_LichSuHong.Text = 'Lịch sử hỏng'

                setTimeout(()=>{
                    this.$refs.KhaoSatTBTapTrungBH.ClearAnswer()
                    this.$refs.KhaoSatTBTapTrungBH.HienThi_Phieu(tbl)
                }, 500)
            }
        },
        rowDoubleClicked(index, item){
            if(this.trangthailoc_selected==1){
                console.log('rowDoubleClicked', item)
                this.tbtl_id=item.TBTL_ID?item.TBTL_ID:0
                this.$refs.CTKhaoSat.showModal()
            }
            
        },
        async clickCallIPCC(value){
            if(!value||value.trim()==''){
                this.$toast.error('Không có thông tin số điện thoại')
                return
            }
            if(!this.user_ipcc.selected){
                this.$toast.error('Chưa có thông tin IPCC')
                return
            }
            console.log('clickCallIPCC', value)
            console.log('user_ipcc.selected', this.user_ipcc.selected)
            console.log('clickCallIPCC', this.actions)
            this.loading(true)
            try {
                let params = {
                    username: this.user_ipcc.selected.user_ipcc,
                    extension: this.user_ipcc.selected.line,
                    dataObject: JSON.stringify({
                        SoThueBao:value,
                        ChuongTrinhId:3
                    })
                }

                let rs = await API.callOb(this.axios, params)

                if (rs.data.errorCode != 0) {
                    this.$root.toastError(rs.data.faultString)
                }
            } catch (e) {
                console.log(e)
            } finally {
                this.loading(false)
            }
        },
        async initDuLieu(){
            this.panelChiTiet.Visible = false
            this.displaySpliter.Visible = true
            // tsbtnLayPhieu.DefaultItem = tsbtnLayPhieu.DropDownItems[0];
            // tsbtnLayPhieu.Text = tsbtnLayPhieu.DefaultItem.Text;
            // tsbtnLayPhieu.Image = tsbtnLayPhieu.DefaultItem.Image;
            let dt=await this.sp_lay_ds_nguoidung_tinh(this.$root.token.getNhanVienID(),2)
            var tinhs=Array(67).fill(false)
            for(let i=0; i<dt.length;i++){
                if(!dt[i].tinh_id){
                    continue
                }
                let id=Number(dt[i].tinh_id)
                if(id<tinhs.length){
                    tinhs[id]=true
                }
            }
            this.tinhChon=tinhs
            console.log('tinhChon', this.tinhChon)
            //
            this.DonViVNP=getListDonViVNP()
            console.log('DonViVNP', this.DonViVNP)


            this.loading(true)      
            try {
                let nguoidung_id = this.$root.token.getNguoiDungID()
                await this.sp_lay_user_ipcc_theo_nguoidung_id(nguoidung_id)
                this.tt_nd = await this.$root.token.getThongTinNguoiDung()
                this.tt_nd.machine = await this.$root.token.getMachine()

                let new_user_ipcc = this.user_ipcc.list
                var user_ipcc = sessionStorage.getItem("user_ipcc")
                console.log('sessionStorage > user_ipcc',sessionStorage.getItem("user_ipcc"))
                if (user_ipcc && user_ipcc.length > 0) {     
                    user_ipcc = JSON.parse(user_ipcc)
                    this.user_ipcc.list = user_ipcc.list == new_user_ipcc ? user_ipcc.list : new_user_ipcc
                    this.user_ipcc.selected = user_ipcc.list == new_user_ipcc ? user_ipcc.selected : new_user_ipcc[0]
                    await this.changeAgentState('LOGIN', null)
                    this.currentStatus = 'LOGIN'
                }
            } catch (err) {
                console.log(err)
            } finally {
                this.loading(false)
            }
        },
        async sp_lay_user_ipcc_theo_nguoidung_id(nguoidung_id) {
            try {
            let user_ipcc = await API.sp_lay_user_ipcc_theo_nguoidung_id(this.axios, nguoidung_id)

            if (user_ipcc.data.error_code == 'BSS-00000000'
                && user_ipcc.data.data !== undefined
                && user_ipcc.data.data.length > 0) {
                this.user_ipcc.list = user_ipcc.data.data
            } else {
                this.$root.toastWarning('Không tồn tại user ipcc')
            }

            } catch (e) {
                console.log(e)
            } finally {

            }
        },
        connect() {
            const headers = {
            Authorization: 'Bearer ' + JSON.parse(localStorage.getItem('OneBSS-Token')).access_token,
            //userCode: this.user_ipcc.selected.user_ipcc,
            }

            let url = this.domainlocal + '/ipcc-endpoint'

            this.socket = new SockJS(url)
            this.stompClient = Stomp.over(this.socket)

            this.stompClient.connect(headers, () => {
            this.reconnect = true
            console.log('Connected')

            this.socket.onclose = () => {
                if (!this.reconnect) {
                return
                }

                setTimeout(() => {
                if (this.reconnect) {
                    this.connect()
                }
                }, 5000)
            }

            this.stompClient.subscribe('/topic/' + this.user_ipcc.selected.user_ipcc, async (message) => {
                console.log(message.body);
                await this.ipcc(message.body)
            })
            })
        },
        disconnect() {
            this.reconnect = false
            if (this.socket) this.socket.close()
            console.log('Disconnected')
        },
        gridUserIpccSelected(row) {
            if (this.thongtincuocgoi.Status != 4 || this.thongtincuocgoi_end.Status == 5) {
                try {
                    this.disconnect()
                } catch (e) {
                    console.log(e)
                }
            }

            if (row != null && this.thongtincuocgoi.Status != 4) {
                this.user_ipcc.selected = row.data
                this.connect()
            } else if (this.thongtincuocgoi.Status == 4) {
                this.$root.toastError('Đang trong cuộc gọi !')
            }
        },
        async onSelectedAgentChanged(row) {
            
        },
        gridCommand_user_ipcc: async function (type, dataItem) {
            if (type == 'active') {
                await this.changeAgentState('LOGIN', null)
            }
        },
        async changeActions(row) {
            if (!row) return false;

            if (this.thongtincuocgoi.Status == 4) {
                this.$root.toastError('Đang trong cuộc gọi !')

                let defaultObj = this.$refs.refActions.ej2Instances
                defaultObj.value = this.currentStatus
                defaultObj.text = this.currentText

                return;
            }

            let reason_code = ''
            let text = row.itemData.text
            let status = row.itemData.id

            this.currentReasonCode = ''
            if (status == 'NOT_READY') {
                let obj = actions[3].nodeChild;
                let data = obj.filter(element => element.text == text)[0];
                reason_code = data.reason_code
                this.currentReasonCode = data.reason_code
            } else if (status == 'DANG_BAN') {
                this.$root.toastError('Bạn Phải chọn hành động đang bận cụ thể!')
                return;
            }

            await this.changeAgentState(status, reason_code)
      },
      async changeAgentState(status, reason_code) {
        this.loading(true)

        try {
          let params = {
            "username": this.user_ipcc.selected.user_ipcc,
            "password": "",
            "extension": this.user_ipcc.selected.line,
            "active_type": "inbound",
            "reason_code": reason_code ? reason_code : this.currentReasonCode,
            "status": status
          }

          let rs = await API.changeAgentState(this.axios, params)

          if (rs.data.errorCode != 0) {
            this.$root.toastError(rs.data.faultString)

            console.log('changeAgentState > currentStatus', this.currentStatus);
            console.log('changeAgentState > currentText', this.currentText);

            let defaultObj = this.$refs.refActions.ej2Instances
            defaultObj.value = this.currentStatus
            defaultObj.text = this.currentText
          } else {
            this.currentStatus = status;
            this.currentText = actions.find(e => e.status == status).text;
            this.$toast.success(this.user_ipcc.selected.user_ipcc + ' ' + this.user_ipcc.selected.line + ' ' + this.currentText)
            sessionStorage.setItem("user_ipcc",JSON.stringify(this.user_ipcc))
          }

        } catch (e) {
          console.log(e)
        } finally {
          this.loading(false)
        }
      },
        async sp_lay_ds_nguoidung_tinh(nhanvien_id, quyen){
            try{
                this.loading(true)
                let response = await API.sp_lay_ds_nguoidung_tinh(this.axios, nhanvien_id, quyen)
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
        async sp_layphieuoutbounddtv(data){
            try{
                this.loading(true)
                let response = await API.sp_layphieuoutbounddtv(this.axios, data)
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
        async sp_multisrv_rs9_lapmoi_ob(ma_nd){
            try{
                this.loading(true)
                let response = await API.sp_multisrv_rs9_lapmoi_ob(this.axios, ma_nd)
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
        async sp_multisrv_rs8_baohong_ob(ma_nd){
            try{
                this.loading(true)
                let response = await API.sp_multisrv_rs8_baohong_ob(this.axios, ma_nd)
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
    },
    mounted(){
        //document.title = 'Khảo sát khách hàng tập trung'
        setTimeout(()=>{
            this.initDuLieu()
        }, 500)
    }
}
</script>
<style>
#outboundcustomercare_it .lay-phieu:hover {
  background-color: rgba(1, 118, 255, 0.1);
  font-weight: 600
}
#outboundcustomercare_it .ui-layphieu {
  position: absolute;
  margin-top: 8px;
  width: 200px;
  padding-left: 0rem;
  list-style: none;
  background-color: #fff;
  background-clip: padding-box;
  border: 1px solid rgba(0, 0, 0, 0.15);
}
.e-contextmenu-wrapper ul.e-ul{
    height: 50% !important;
    overflow-y: scroll !important;
}
#outboundcustomercare_it .disabled{
    pointer-events:none;
    opacity:0.7;
}
#outboundcustomercare_it .disabled_color{
  color: gray !important;
}
#btn_laydulieu{
    background: #fff!important;
    color: #0176FF!important;
    border-style: solid!important;
    border-width: 1px!important;
    padding: 5px 10px!important;
    border-radius: 4px!important;
    border-color: #0176FF!important;
}
#btn_laydulieu:hover{
    background: #035FCC!important;
    color: #fff!important;
}
::-webkit-input-placeholder {
   font-style: italic;
}
:-moz-placeholder {
   font-style: italic;  
}
::-moz-placeholder {
   font-style: italic;  
}
:-ms-input-placeholder {  
   font-style: italic; 
}
.breadcrumb-item + .breadcrumb-item::before {
  float: left;
  padding-right: 0.5rem;
  color: #6c757d;
  content: "" !important;
}
.breadcrumb {
    padding: 0 0 !important;
}
</style>