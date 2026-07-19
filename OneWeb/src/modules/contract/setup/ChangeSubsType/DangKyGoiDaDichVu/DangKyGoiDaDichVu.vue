<template>
    <div class="main-wrapper">
        <div class="breadcrumb-top">
            <div class="main-title">Đăng ký gói đa dịch vụ cho các thuê bao lắp mới</div>
        </div>
        <ActionTop :actions="actions" @onActionClick="onActionClick" :isMore="false"/>
        <div class="page-content">
            <div class="row">
                <div class="col-sm-6 col-12">
                    <div class="box-form">
                        <div class="legend-title">Thông tin gói</div>
                        <div class="info-row">
                            <div class="key w90">Mã giao dịch</div>
                            <div class="value">
                                <div class="input-more-button">
                                    <button class="btn" @click="clickSearchContract">
                                        <span class="-ap icon-more_horiz"></span>
                                    </button>
                                    <input type="text" ref="inputMaGD" @keyup.enter="onEnterMaGD" :value="input_magd" @change="e=>input_magd=e.target.value" class="form-control bold">
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-sm-6 col-12">
                                <div class="info-row">
                                    <div class="key w90">Ngày yêu cầu</div>
                                    <div class="value">
                                        <div class="input-icon-right">
                                            <k-date-picker v-model="ngay_yc" :disabled="true"/>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="col-sm-6 col-12">
                                <div class="info-row">
                                    <div class="key w90">Kiểu yêu cầu</div>
                                    <div class="value">
                                        <div class="select-custom">
                                            <select v-model="kieuyc_selected" class="form-control">
                                                <option v-for="item in ds_kieuyc" :key="item.kieuld_id" :value="item.kieuld_id">
                                                    {{item.ten_kieuld}}
                                                </option>
                                            </select>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="info-row">
                            <div class="key w90">Tên gói</div>
                            <div class="value">
                                <div class="input-more-button">
                                    <button class="btn" @click="btnTenGoi_Click">
                                        <span class="-ap icon-more_horiz"></span>
                                    </button>
                                    <div class="select-custom" ref="ds_tengoi">
                                        <select2 :settings="{ dropdownParent: $refs['ds_tengoi'] }" ref="cboTenGoi"
                                            v-model="tengoi_selected" class="select2"
                                            :options="ds_tengoi.map(e=> ({id: e.goi_id, text: e.ten_goi}))"
                                            @change="changeTenGoi"
                                        >
                                        </select2>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="info-row">
                            <div class="key w90">Hiệu lực</div>
                            <div class="value">
                                <div class="select-custom">
                                    <select v-model="hieuluc_selected" class="form-control">
                                        <option v-for="item in ds_hieuluc" :key="item.maghep_id" :value="item.maghep_id">
                                            {{item.maghep}}
                                        </option>
                                    </select>
                                </div>
                            </div>
                        </div>
                        <div class="info-row">
                            <div class="key w90">Mô tả</div>
                            <div class="value">
                                <textarea name="" readonly :value="input_mota" @change="e=>input_mota=e.target.value" class="form-control bold" style="height: 190px;resize: none;"></textarea>
                            </div>
                        </div>
                    </div>
                    <div class="box-form">
                        <div class="legend-title">Danh sách các thuê bao thuộc khách hàng</div>
                        <KDataGrid 
                            ref="gridDsTBThuocKH"
                            :columns="columnsTBKH"
                            :dataSource="dataSourcesTBKH"
                            :showColumnCheckbox="true"
                            :enable-paging-server="false"
                            :allowPaging="false"
                            :showFilter="true"
                            panelDataHeight="200px"
                            :enabledSelectFirstRow="false"
                            :selectionSettings="{checkboxMode: 'ResetOnRowClick', checkboxOnly: true}"
                        />
                    </div>
                </div>
                <div class="col-sm-6 col-12">
                    <div class="box-form">
                        <div class="legend-title">Thông tin khách hàng</div>
                        <div class="info-row">
                            <div class="key">Mã khách hàng</div>
                            <div class="value">
                                <input type="text" :value="input_makh" @change="e=>input_makh=e.target.value" class="form-control bold">
                            </div>
                        </div>
                        <div class="info-row">
                            <div class="key">Tên khách hàng</div>
                            <div class="value">
                                <input type="text" readonly :value="input_tenkh" @change="e=>input_tenkh=e.target.value" style="color: gray;" class="form-control bold">
                            </div>
                        </div>
                        <div class="info-row">
                            <div class="key">Địa chỉ KH</div>
                            <div class="value">
                                <input type="text" readonly :value="input_diachikh" @change="e=>input_diachikh=e.target.value" style="color: gray;" class="form-control bold">
                            </div>
                        </div>
                        <div class="info-row">
                            <div class="key">Ghi chú</div>
                            <div class="value">
                                <div class="input-number-custom">
                                    <input type="text" :value="input_ghichu" @change="e=>input_ghichu=e.target.value" class="form-control">
                                </div>
                            </div>
                        </div>
                        
                    </div>
                    <div class="box-form">
                        <div class="legend-title">Bổ sung thuê bao thuộc KH khác vào gói</div>
                        <div class="row">
                            <div class="col-sm-6 col-12">
                                <div class="info-row">
                                    <div class="key">Số máy/Acc</div>
                                    <div class="value">
                                        <div class="input-more-button">
                                            <button class="btn" @click="clickSearchAccount">
                                                <span class="-ap icon-more_horiz"></span>
                                            </button>
                                            <input type="text" @keyup.enter="onEnterMaTB" :value="input_matb" @change="e=>input_matb=e.target.value" class="form-control highlight bold">
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="col-sm-6 col-12">
                                <div class="info-row">
                                    <div class="key w70">Dịch vụ</div>
                                    <div class="value">
                                        <div class="select-custom" ref="ds_dichvu">
                                            <select2 :settings="{ dropdownParent: $refs['ds_dichvu'] }"
                                                v-model="dichvu_selected" class="select2"
                                                :options="ds_dichvu.map(e=> ({id: e.DICHVUVT_ID, text: e.TEN_DVVT}))">
                                            </select2>
                                        </div>
                                    </div>
                                    <div class="value w30 nowrap padt7">
                                        <div class="check-action">
                                            <input type="checkbox" v-model="checkChuNhom" class="check">
                                            <span class="name" style="color:#0176FF;">Chủ nhóm</span>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        
                        <div class="info-row">
                            <div class="key">Tên TB</div>
                            <div class="value">
                                <input type="text" readonly :value="input_tentb" @change="e=>input_tentb=e.target.value" class="form-control ">
                            </div>
                        </div>
                        <div class="info-row">
                            <div class="key">Địa chỉ TB</div>
                            <div class="value">
                                <input type="text" readonly :value="input_diachitb" @change="e=>input_diachitb=e.target.value" class="form-control ">
                            </div>
                        </div>
                    </div>
                    <div class="box-form">
                        <div class="legend-title">Danh sách các thuê bao thuộc gói</div>
                        <KDataGrid 
                            ref="gridDsTBGoi"
                            :columns="columnsTBGoi"
                            :dataSource="dataSourcesTBGoi"
                            :showColumnCheckbox="true"
                            :enable-paging-server="false"
                            :allowPaging="false"
                            :showFilter="true"
                            panelDataHeight="200px"
                            :enabledSelectFirstRow="true"
                            @selectedItemsChanged="selectedItemsChanged"
                            :selectionSettings="{checkboxMode: 'ResetOnRowClick'}"
                        />
                    </div>

                </div>
            </div>
        </div>
        <!-- Modal -->
        <SearchContractModal ref="popupSearchContract" 
            :loai_hd_id="1"
            :trangthai_hd="1"
            :ngay_yc="dateFormSeachContract"
            @accept="acceptSearchContract"/>
        <SearchAccountModal ref="searchAccountModal" @form-close="formCloseSearchAccount"/>
        <QLCongVanModal ref="qLCongVanModal" :goi_id="goi_id" />
    </div>
</template>
<script>
import ActionTop from '@/components/kylq_components/mirana/ActionTop.vue'
import KDataGrid from '@/components/kylq_components/KDataGrid'
import KDatePicker from '@/components/kylq_components/KDatePicker.vue'
import moment from 'moment'
// import SearchContractModal from '../../ChangeInstallUnit/popup/SearchContract/SearchContractModal.vue'
import SearchContractModal from "@/modules/contract/setup/HandoverTicket/components/SearchContractModal.vue"
import SearchAccountModal from '../../SurveyRequest/popups/SearchAccountModal.vue'
import API from './API'
import {LoaiHopDong, TrangThaiHD} from '../ThamSo'
import QLCongVanModal from '../../../../admin/category/MultiServicePackage/QLCongVanModal.vue'
//MaGD:HPG-LD/00736242, frmDangKyGoiDaDVLM.cs
export default {
    name:'DangKyGoiDaDichVu',
    components:{
        ActionTop,
        KDatePicker,
        SearchContractModal,
        SearchAccountModal,
        QLCongVanModal,
        KDataGrid
    },
    data(){
        return {
            actions:[
                {
                    id:'nhapmoi',
                    name:'Nhập mới',
                    active: true,
                    icon_class:'one-file-plus',
                    visible: true,
                    type:1
                },
                {
                    id:'huy',
                    name:'Hủy',
                    active: true,
                    icon_class:'nc-icon-glyph ui-1_circle-remove',
                    visible: true,
                    type:1
                },
                {
                    id:'ghilai',
                    name:'Ghi lại',
                    active: true,
                    icon_class:'one-save',
                    visible: true,
                    type:1
                },
                {
                    id:'xoatb',
                    name:'Xoá TB',
                    active: true,
                    icon_class:'one-phone-remove',
                    visible: true,
                    type:1
                },
                {
                    id:'themtb',
                    name:'Thêm TB',
                    active: true,
                    icon_class:'one-mobile-plus',
                    visible: true,
                    type:1,
                    tooltip:'Bổ sung thuê bao thuộc khách hàng khác vào gói'
                }
            ],
            rowSelectedIdTBKH:-1,
            columnsTBKH:[
                {
                    fieldName:'CHKNHOM',
                    headerText:'Chủ nhóm',
                    allowFiltering:false,
                    width:120,
                    textAlign: "center",
                    template: this.columnTemplateCheckBox(this),
                },
                {
                    fieldName:'MA_TB',
                    headerText:'Số máy/Acc',
                    allowFiltering:true,
                    width:120
                },
                {
                    fieldName:'THUONGHIEU',
                    headerText:'Tốc độ/Loại hình',
                    allowFiltering:true
                },
                {
                    fieldName:'KIEU_TB',
                    headerText:'Trạng thái',
                    allowFiltering:true
                }
            ],
            dataSourcesTBKH:[],
            dataSourcesTBGoi:[],
            columnsTBGoi:[
                {
                    fieldName:'NHOMTB_ID',
                    headerText:'Nhóm',
                    allowFiltering:true,
                    width:120
                },
                {
                    fieldName:'TEN_GOI',
                    headerText:'Tên gói',
                    allowFiltering:true
                },
                {
                    fieldName:'TEN_CHUNHOM',
                    headerText:'Chủ nhóm',
                    allowFiltering:true,
                    width:120
                },
                {
                    fieldName:'MA_TB',
                    headerText:'Số máy/Acc',
                    allowFiltering:true,
                    width:120
                },
                {
                    fieldName:'THUONGHIEU',
                    headerText:'Tốc độ/Loại hình',
                    allowFiltering:true
                }
            ],
            input_magd:'',
            ngay_yc:moment(new Date()).format('DD/MM/YYYY'),
            ds_kieuyc:[],
            kieuyc_selected:null,
            ds_tengoi:[],
            tengoi_selected:null,
            ds_hieuluc:[],
            hieuluc_selected:null,
            input_mota:'',
            input_matb:'',
            input_makh:'',
            input_tenkh:'',
            input_diachitb:'',
            input_diachikh:'',
            input_ghichu:'',
            ds_dichvu:[],
            dichvu_selected:null,
            checkChuNhom:false,
            input_tentb:'',
            dateFormSeachContract:moment(new Date()).format('DD/MM/YYYY'),
            ds_id_dichvu:[1,2,4,8,9,10,11,12,13,14,15,16],
            hdkh_id:0,
            khachhang_id:0,
            khlon_id:0,
            dichvuvt_id:0,
            dtThueBao:[],
            ten_tb:'',
            diachi_ld:'',
            loaitb_id:0,
            donvi_id:0,
            doituong_id:0,
            hdtb_id:0,
            txtSoThang:'',
            nhomtb_id:0,
            dataCheck2:[],
            dsHDTB:[],
            dsHDTBGOI:[]

        }
    },
    computed:{
        goi_id(){
            return this.tengoi_selected?Number(this.tengoi_selected):0
        }
    },
    methods:{
        onActionClick(action){
            if(action.id=='nhapmoi'){
                this.SetButton(1)
            }else if(action.id=='huy'){
                this.SetButton(0)
                this.dataSourcesTBGoi=[]
            }else if(action.id=='ghilai'){
                //tsbtnThemTB_Click
                this.ThemHDThueBao()
            }else if(action.id=='xoatb'){
                this.XoaHDTB()

            }else if(action.id=='themtb'){
                //btnThemTBNgoaiKH_Click
                //Thêm thuê bao ngoài khách hàng
                if(this.input_matb.trim()!=''){
                    this.ThemHDThueBao2()
                }
            }
        },
        SetButton(kieu){
            this.actions[this.actions.findIndex(x=>x.id=='nhapmoi')].active=false
            this.actions[this.actions.findIndex(x=>x.id=='huy')].active=false
            this.actions[this.actions.findIndex(x=>x.id=='themtb')].active=false
            this.actions[this.actions.findIndex(x=>x.id=='xoatb')].active=false
            if (kieu == -1){
                //Bat dau
                this.actions[this.actions.findIndex(x=>x.id=='huy')].active=true
            }else if (kieu == 0){
                //Bat dau
                this.actions[this.actions.findIndex(x=>x.id=='nhapmoi')].active=true
                //this.Clear()
            }else if (kieu == 1){
                //Them moi
                this.actions[this.actions.findIndex(x=>x.id=='huy')].active=true
                //this.Clear()
            }else if (kieu == 2){
                //Huy
                this.$refs.inputMaGD.focus()
                this.actions[this.actions.findIndex(x=>x.id=='nhapmoi')].active=true
                //this.Clear()
            }else if (kieu == 3){
                //Edit
                //this.$refs.inputMaGD.focus()
                this.actions[this.actions.findIndex(x=>x.id=='nhapmoi')].active=true
                this.actions[this.actions.findIndex(x=>x.id=='huy')].active=true
                this.actions[this.actions.findIndex(x=>x.id=='themtb')].active=true
                this.actions[this.actions.findIndex(x=>x.id=='xoatb')].active=true
                if(this.dataSourcesTBGoi.length>0){
                    this.actions[this.actions.findIndex(x=>x.id=='xoatb')].active=true
                }else{
                    this.actions[this.actions.findIndex(x=>x.id=='xoatb')].active=false
                }
            }
        },
        columnTemplateCheckBox(parent) {
            return function() {
                return { 
                    template: {
                        template: `
                          <div class="check-action">
                            <ejs-checkbox :z-index="2000" v-model="checkChuNhom" @change="changeCheck"></ejs-checkbox>
                          </div>
                        `,
                        data() {
                            return {
                              data: {},
                              checkChuNhom:false,
                              parent:parent
                            }
                        },
                        methods:{
                            changeCheck(){
                                this.data.CHKNHOM=this.checkChuNhom?1:0
                                this.parent.changeCheckChuNhom(this.data)
                            }
                        }
                    }
                }
            }
        },
        changeCheckChuNhom(data){
            console.log('changeCheckChuNhom', data)
            const index=this.dataSourcesTBKH.findIndex(x=>x.MA_TB==data.MA_TB)
            if(index>-1){
                this.dataSourcesTBKH[index].CHKNHOM=data.CHKNHOM
            }
            console.log('dataSourcesTBKH', this.dataSourcesTBKH)
        },
        async onSelectedRowTBKH(item){

        },
        selectedItemsChanged(data){
            if(this.dataSourcesTBGoi.length<=0||data.length<=0){
                return
            }
            this.onSelectedRowTBGoi(data[0])
        },
        async onSelectedRowTBGoi(item){
            this.hdtb_id=item.HDTB_ID?item.HDTB_ID:0
            this.tengoi_selected=item.GOI_ID
            this.changeTenGoi()
            if(item.CHUNHOM&&item.CHUNHOM==1){
                this.checkChuNhom=true
            }else{
                this.checkChuNhom=false
            }
            let thang_huong=item.THANG_HUONG
            if(thang_huong==0||thang_huong==1){
                this.hieuluc_selected=thang_huong
            }else{
                this.hieuluc_selected=2
                this.txtSoThang=thang_huong.toString()
            }
            this.SetButton(3)
        },
        changeTenGoi(){
            if(this.tengoi_selected==null){
                return
            }
            let tengoi=this.ds_tengoi.find(x=>x.goi_id==this.tengoi_selected)

            this.input_mota=tengoi?tengoi.mota.toString():''
        },
        clickSearchContract(){
            this.$refs.popupSearchContract.showModal()
        },
        clickSearchAccount(){
            this.$refs.searchAccountModal.showModal()
        },
        acceptSearchContract(item){
            this.input_magd=item.ma_gd
            this.onEnterMaGD()
        },
        formCloseSearchAccount(data){
            this.input_matb=data.ma_tb
            this.onEnterMaTB()
        },
        btnTenGoi_Click(){
            this.$refs.qLCongVanModal.showModal()
        },
        async ThemHDThueBao2(){
            if(this.dtThueBao.length==0){
                this.$toast.error('Không có thông tin thuê bao ngoài mã khách hàng!')
                return
            }
            
            //Làm sau
            //gridDsTBGoi
            let dataCheckeds=this.$refs.gridDsTBGoi.getSelectedRecords()
            let data={
                DICHVUVT_ID:this.dichvu_selected?this.dichvu_selected:0,
                MA_TB:this.input_matb.trim(),
                HIEULUC:this.hieuluc_selected?this.hieuluc_selected:0,
                CHK_CHUNHOM:this.checkChuNhom,
                HDKH_ID:this.hdkh_id,
                THUEBAO_ID:this.thuebao_id,
                KIEULD_ID:this.kieuyc_selected?this.kieuyc_selected:0,
                LIST_TB_DK:dataCheckeds.map(x=>Object.assign(x,{CHK:1})),
                DS_THUEBAO:[
                    {
                        MA_TB:this.input_matb.trim(),
                        DICHVUVT_ID:this.dichvu_selected?this.dichvu_selected:0
                    }
                ]
            }
            let result=await this.fn_themtbngoaikh_dk_goidadvlm(JSON.stringify(data))
            if(result&&result.ERROR_CODE==1){
                this.$toast.success('Đã cập nhật dữ liệu lên hệ thống của Viễn thông tỉnh thành công!')
                this.SetButton(3)
                let ds=await this.lay_ds_hopdong_theo_ma_gd(this.input_magd.trim(), LoaiHopDong.DAT_MOI, TrangThaiHD.MOI, 0)
                await this.HienThiTTHopDongKH(ds)
            }else if(result&&result.ERROR_CODE==0){
                this.$toast.error(result.MESSAGE)
            }
        },
        async ThemHDThueBao(){
            //gridDsTBThuocKH
            let hl=this.hieuluc_selected
            if(hl==2){
                if(this.txtSoThang==''){
                    this.$toast.error('Khi chọn hiệu lực khác bạn phải nhập số tháng sau tháng đăng ký có hiệu lực!')
                    return
                }
            }
            if (!(await this.Tao_nhomtb())){
                return
            }
            let dsDataChecks=this.$refs.gridDsTBThuocKH.getSelectedRecords()
            //console.log('dsDataChecks', dsDataChecks)
            if(dsDataChecks.length==0){
                this.$toast.error('Bạn chưa chọn thuê bao nào để đăng ký gói!')
                return
            }
            console.log('dsDataChecks', dsDataChecks)

            let data={
                LIST_TB_DK:dsDataChecks.map(x=>Object.assign(x,{CHK:1, NHOMTB_ID:this.nhomtb_id, CHUNHOM: x.CHKNHOM?1:0})),
                GOIDV_ID:this.tengoi_selected?this.tengoi_selected:0,
                KHACHHANG_ID:this.khachhang_id,
                KIEULD_ID:this.kieuyc_selected?this.kieuyc_selected:0,
                HDKH_ID:this.hdkh_id,
                NGUOI_CN:this.$root.token.getUserName(),
                MAY_CN:"localhost",
                HIEULUC:this.hieuluc_selected?this.hieuluc_selected:0,
                SOTHANG:this.txtSoThang
            }
            let result = await this.fn_themhdthuebao_dk_goidadvlm(JSON.stringify(data))
            if(result&&result.ERROR_CODE==1){
                this.$toast.success('Đã cập nhật dữ liệu lên hệ thống của Viễn thông tỉnh thành công!')
                this.SetButton(3)
                let ds=await this.lay_ds_hopdong_theo_ma_gd(this.input_magd.trim(), LoaiHopDong.DAT_MOI, TrangThaiHD.MOI, 0)
                await this.HienThiTTHopDongKH(ds)
            }else if(result){
                this.$toast.error(result.MESSAGE)
            }
        },
        async Tao_nhomtb(){
            //tableThuebaoGoi
            this.nhomtb_id=0
            let dataCheckeds=this.$refs.gridDsTBGoi.getSelectedRecords()
            var ktNhom=0
            for(let i=0;i<dataCheckeds.length;i++){
                if(this.nhomtb_id!=dataCheckeds[i].NHOMTB_ID){
                    ktNhom++
                }
                this.nhomtb_id=dataCheckeds[i].NHOMTB_ID
                let g=dataCheckeds[i].GOI_ID
                if(g!=this.tengoi_selected){
                    this.$toast.error('Gói không trùng với nhóm thuê bao cần ghép!')
                    return false
                }
            }
            if(ktNhom>1){
                this.$toast.error('Bạn chọn quá 2 nhóm cần ghép!')
                return false
            }else if(ktNhom==1){
                return true
            }else{
                this.nhomtb_id=await this.get_keys('NHOMTB')
            }
            return true
        },
        async XoaHDTB(){
            let dataCheckeds=this.$refs.gridDsTBGoi.getSelectedRecords()
            if(dataCheckeds.length==0){
                this.$toast.error('Bạn chưa chọn thuê bao để xóa!')
                return
            }
            let comfirm = await this.confirm('Bạn thật sự muốn xóa thuê bao không ?','Thông báo')
            if(comfirm==0) return

            let data={
                LIST_TB_DK:dataCheckeds.map(x=>Object.assign(x,{CHK:1})),
                KIEULD_ID:this.kieuyc_selected?this.kieuyc_selected:0
            }
            let result=await this.fn_xoahdtb_dk_goidadvlm(JSON.stringify(data))
            if(result&&result.ERROR_CODE==1){
                this.$toast.success('Xoá thành công')
                await this.HienThiDanhSacHDTB()
            }else if(result&&result.ERROR_CODE==0){
                this.$toast.error(result.MESSAGE)
            }

        },
        async confirm(message, title){
            try{
                let result = await this.$confirm(message,title,{
                    confirmButtonText: 'Đồng ý',
                    cancelButtonText: 'Hủy'
                })
                return 1
            }catch(e){
                return 0
            }
        },
        async get_keys(keyname){
            try{
                this.loading(true)
                let response = await API.get_keys(this.axios, keyname)
                this.loading(false)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                    return Number(response.data.data)
                }else{
                    this.loading(false)
                    return 0
                }
            }catch(e){
                return 0
            }
        },
        async lay_ds_dichvu_vt(){
            try{
                this.loading(true)
                let response = await API.lay_ds_dichvu_vt(this.axios)
                this.loading(false)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                    return response.data.data.filter(item=>this.ds_id_dichvu.findIndex(x=>x==item.DICHVUVT_ID)>-1)
                                            .sort((a,b)=>a.DICHVUVT_ID-b.DICHVUVT_ID)
                }else{
                    return []
                }
            }catch(e){
                this.loading(false)
                return []
            }
        },
        async lay_ds_kieu_ld(loaitb_id, loaihd_id){
            try{
                this.loading(true)
                let response = await API.lay_ds_kieu_ld(this.axios, loaitb_id, loaihd_id)
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
        async ht_goi_dadv_datatable(){
            try{
                this.loading(true)
                let response = await API.ht_goi_dadv_datatable(this.axios)
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
        async lay_ds_hopdong_theo_ma_gd(ma_gd, loaihd_id, tthd_id, donvi_dl_id){
            try{
                this.loading(true)
                let response = await API.lay_ds_hopdong_theo_ma_gd(this.axios, ma_gd, loaihd_id, tthd_id, donvi_dl_id)
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
        async fn_lay_tt_ds_thuebao_dk_goi_dadvlm(ds_para){
            try{
                this.loading(true)
                let response = await API.fn_lay_tt_ds_thuebao_dk_goi_dadvlm(this.axios, ds_para)
                this.loading(false)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                    return JSON.parse(response.data.data)
                }else{
                    return null
                }
            }catch(e){
                this.loading(false)
                return null
            }
        },
        async fn_themhdthuebao_dk_goidadvlm(ds_para){
            try{
                this.loading(true)
                let response = await API.fn_themhdthuebao_dk_goidadvlm(this.axios, ds_para)
                this.loading(false)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                    return JSON.parse(response.data.data)
                }else{
                    return null
                }
            }catch(e){
                this.loading(false)
                return null
            }
        },
        async fn_themtbngoaikh_dk_goidadvlm(ds_para){
            try{
                this.loading(true)
                let response = await API.fn_themtbngoaikh_dk_goidadvlm(this.axios, ds_para)
                this.loading(false)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                    return JSON.parse(response.data.data)
                }else{
                    return null
                }
            }catch(e){
                this.loading(false)
                return null
            }
        },
        async fn_xoahdtb_dk_goidadvlm(ds_para){
            try{
                this.loading(true)
                let response = await API.fn_xoahdtb_dk_goidadvlm(this.axios, ds_para)
                this.loading(false)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                    return JSON.parse(response.data.data)
                }else{
                    return null
                }
            }catch(e){
                this.loading(false)
                return null
            }
        },
        async sp_lay_danhba_theo_matb(ma_tb, dichvuvt_id, donvi_dl_id){
            try{
                this.loading(true)
                let response = await API.sp_lay_danhba_theo_matb(this.axios, ma_tb, dichvuvt_id, donvi_dl_id)
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
        async onEnterMaGD(){
            if(this.input_magd.trim()!=''){
                let ds=await this.lay_ds_hopdong_theo_ma_gd(this.input_magd.trim(), 0, TrangThaiHD.MOI, 0)
                await this.HienThiTTHopDongKH(ds)
            }
        },
        async onEnterMaTB(){
            if(this.input_matb.trim()!=''){
                await this.HienThiTT_DanhBa(this.input_matb.trim())
            }
        },
        async HienThiTT_DanhBa(matb){
            this.dichvuvt_id=this.dichvu_selected
            let ds=await this.sp_lay_danhba_theo_matb(matb, this.dichvuvt_id, 0)
            this.dtThueBao=ds
            if(ds.length>0){
                let item=ds[0]
                if(item.ma_kh&&item.ma_kh==this.input_makh.trim()){
                    this.$toast.error('Thuê bao cùng mã khách hàng: '+this.input_makh.trim())
                    return
                }
                this.thuebao_id=item.thuebao_id?item.thuebao_id:0
                //Thông tin khách hàng
                this.input_tentb=item.ten_tb?item.ten_tb.toString():''
                this.input_diachitb=item.diachi_tb?item.diachi_tb.toString():''
                this.ten_tb=item.ten_tb?item.ten_tb.toString():''
                this.diachi_ld=item.diachi_ld?item.diachi_ld.toString():''
                this.loaitb_id=item.loaitb_id?item.loaitb_id:0
                this.donvi_id=item.donviql_id?item.donviql_id:0
                this.doituong_id=item.doituong_id?item.doituong_id:0
                this.khlon_id=item.khlon_id?item.khlon_id:0
                this.hdtb_id = 0
            }else{
                let dichvu=this.ds_dichvu.find(x=>x.DICHVUVT_ID==this.dichvuvt_id)
                let txtDichVu=dichvu?dichvu.TEN_DVVT:''
                this.$toast.error('Không tìm thấy thông tin về mã thuê bao "'+this.input_matb.trim()+'" đối với dịch vụ  '+txtDichVu)
            }
        },
        async HienThiTTHopDongKH(ds){
            if(ds.length>0){
                let item=ds[0]
                this.hdkh_id=item.hdkh_id?item.hdkh_id:0
                this.input_magd=item.ma_gd?item.ma_gd.toString():''
                this.input_makh=item.ma_kh?item.ma_kh.toString():''
                this.khachhang_id=item.khachhang_id?item.khachhang_id:0
                this.khlon_id=item.khlon_id?item.khlon_id:0
                this.input_tenkh=item.ten_kh?item.ten_kh.toString():''
                this.input_diachikh=item.diachi_kh?item.diachi_kh.toString():''
                if(item.ngay_yc){
                    this.ngay_yc=moment(item.ngay_yc,'YYYY-MM-DD HH:mm:ss').format('DD/MM/YYYY')
                }
                this.input_ghichu=item.ghichu?item.ghichu.toString():''
                this.SetButton(3)
                await this.HienThiDanhSacHDTB()
            }else{
                this.dataSourcesTBGoi=[]
                this.SetButton(0)
            }
        },
        async HienThiDanhSacHDTB(){
            let result = await this.fn_lay_tt_ds_thuebao_dk_goi_dadvlm(JSON.stringify({
                MA_GD:this.input_magd.trim(),
                MA_KH:this.input_makh.trim(),
                HDKH_ID:this.hdkh_id
            }))
            console.log('HienThiDanhSacHDTB', result)
            if(result&&result.ERROR_CODE==1){
                // this.dataSourcesTBKH=result.RESULT.DS_THUEBAO.map(x=>Object.assign(x,{
                //     CHKNHOM:x.CHKNHOM==1?true:false
                // }))
                this.dataSourcesTBKH=result.RESULT.DS_THUEBAO
                this.dataSourcesTBGoi=result.RESULT.DS_THUEBAO_DK
            }else if(result&&result.ERROR_CODE==0){
                this.$toast.error(result.MESSAGE)
            }
        },
        async initDuLieu(){
            let value = this.$route.query.ma_gd
            if(value){
                this.input_magd=value
            }

            this.ds_dichvu=await this.lay_ds_dichvu_vt()
            if(this.ds_dichvu.length>0){
                //Cố định
                this.dichvu_selected=1
            }
            this.ds_tengoi=await this.ht_goi_dadv_datatable()
            if(this.ds_tengoi.length>0){
                this.tengoi_selected=this.ds_tengoi[0].goi_id
                this.changeTenGoi()
            }
            this.ds_hieuluc=[
                {
                    maghep_id:0,
                    maghep:'Tức thì'
                },
                {
                    maghep_id:1,
                    maghep:'Tháng sau'
                },
                {
                    maghep_id:2,
                    maghep:'Khác'
                },

            ]
            this.hieuluc_selected=0
            this.ds_kieuyc=await this.lay_ds_kieu_ld(0, LoaiHopDong.DANGKY_GOI_DADV)
            if(this.ds_kieuyc.length>0){
                this.kieuyc_selected=this.ds_kieuyc[0].kieuld_id
            }
            if(this.input_magd.trim()!=''){
                let ds=await this.lay_ds_hopdong_theo_ma_gd(this.input_magd.trim(), 0, TrangThaiHD.MOI, 0)
                await this.HienThiTTHopDongKH(ds)
            }
        }
    },

    mounted(){
        this.SetButton(-1)
        setTimeout(()=>{
            this.initDuLieu()
        },500)
    }
}
</script>
<style>
.mx-input:disabled, .mx-input.disabled{
    color: #555 !important;
}
.select2-selection { overflow: hidden !important; }
.select2-container .select2-selection--single .select2-selection__rendered {
  white-space: normal !important;
  word-break: break-all !important;
}
</style>
