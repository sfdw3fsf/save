<template>
    <div class="main-wrapper">
        <breadcrumb :header="header"/>
        <ActionTop :actions="actions" @onActionClick="onActionClick" />
        <div class="page-content">
            <div class="row">
                <div class="col-sm-4 col-12">
                    <div class="box-form">
                        <div class="legend-title">Khu vực</div>
                        <div class="info-row">
                            <div class="key w90">Mã khu vực</div>
                            <div class="value">
                                <input type="text" :value="input_ma_khucvuc" readonly @change="e=>input_ma_khucvuc=e.target.value" class="form-control">
                            </div>
                        </div>
                        <div class="info-row">
                            <div class="key w90">KV cha</div>
                            <div class="value">
                                <div class="select-custom">
                                    <select v-model="khuvuc_cha_selected" disabled class="form-control">
                                        <option v-for="item in ds_khuvuc" :key="item.khuvuc_id" :value="item.khuvuc_id">{{item.ten_kv}}</option>
                                    </select>
                                </div>
                            </div>
                        </div>
                        <div class="info-row">
                            <div class="key w90">Khu vực</div>
                            <div class="value">
                                <input type="text" :value="input_khucvuc" readonly @change="e=>input_khucvuc=e.target.value" class="form-control">
                            </div>
                        </div>
                        <div class="info-row">
                            <div class="key w90">Ghi chú</div>
                            <div class="value">
                                <textarea name="" :value="input_ghichu" readonly @change="e=>input_ghichu=e.target.value" class="form-control" style="height: 100px;resize: none;"></textarea>
                            </div>
                        </div>
                    </div>
                    <DanhSachKhuVuc ref="danhSachKhuVuc"
                        :ds_khuvuc="ds_khuvuc"
                        @focusedNodeChanged="focusedNodeChanged"/>
                </div>
                <div class="col-sm-8 col-12">
                    <div class="box-move-select-table">
                        <div class="box-col box-form">
                            <div class="legend-title">Khu vực</div>
                            <KTableV3 :columns="columnsKhuVuc"
                                :dataSources="dataSources"
                                tableHeight="515px"
                                :allowCheckBox="true"
                                id="id"
                                @dataCheckeds="dataCheckedKhuVuc"
                                :allowFilter="true"/>
                        </div>
                        <div class="actions">
                            <button class="btn" @click="clickRight">
                                <span class="-ap icon-chevron-thin-right"></span>
                            </button>
                            <!-- <button class="btn" @click="clickLeft">
                                <span class="-ap icon-chevron-thin-left"></span>
                            </button> -->
                        </div>
                        <div class="box-col box-form">
                            <div class="legend-title">Thông tin nhân viên</div>
                            <div class="row">
                                <div class="col-sm-6 col-12">
                                    <div class="info-row">
                                        <div class="key w30">Nhân viên</div>
                                        <div class="value">
                                            <!-- <multiselect
                                                :options="ds_nhanvien"
                                                placeholder="Chọn NV"
                                                @input="changeNhanVien"
                                                :showLabels="false"
                                                :allowEmpty="false"
                                                :value="nhanvien_o_selected"
                                                label="ten_nv"
                                                track-by="nhanvien_id">
                                                <template #noOptions>
                                                    Không tìm thấy thông tin
                                                </template>
                                                <template #noResult>
                                                    Không tìm thấy thông tin
                                                </template>
                                            </multiselect> -->
                                            <div class="select-custom">
                                                <select v-model="nhanvien_selected" disabled   class="form-control">
                                                    <option v-for="item in ds_nhanvien" :key="item.nhanvien_id" :value="item.nhanvien_id">
                                                        {{item.ten_nv}}
                                                    </option>
                                                </select>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="col-sm-6 col-12">
                                    <div class="info-row">
                                        <div class="key w30">Loại NV</div>
                                        <div class="value">
                                            <!-- <multiselect
                                                :options="ds_loainv"
                                                placeholder=""
                                                @input="changeLoaiNV"
                                                :showLabels="false"
                                                :allowEmpty="false"
                                                :value="loainv_o_selected"
                                                label="ten"
                                                :disabled="true"
                                                track-by="loainv_id">
                                                <template #noOptions>
                                                    Không tìm thấy thông tin
                                                </template>
                                                <template #noResult>
                                                    Không tìm thấy thông tin
                                                </template>
                                            </multiselect> -->
                                            <div class="select-custom">
                                                <select v-model="loainv_selected" disabled   class="form-control">
                                                    <option v-for="item in ds_loainv" :key="item.loainv_id" :value="item.loainv_id">
                                                        {{item.ten}}
                                                    </option>
                                                </select>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="legend-title mart10">Khu vực đã nạp</div>
                            <KTableV3 :columns="columnsKhuVuc"
                                :dataSources="dataSourcesDaNap"
                                tableHeight="400px"
                                :allowCheckBox="true"
                                id="id"
                                @dataCheckeds="dataCheckedKhuVucNap"
                                :allowFilter="true"/>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <Footer/>
    </div>
</template>
<script>
import Footer from '@/components/FooterStyle.vue'
import breadcrumb from '@/components/kylq_components/breadcrumb.vue'
//import KTableV3 from '@/components/kylq_components/mirana/KTableV3.vue'
import KTableV3 from './KTableV3.vue'
import ActionTop from '@/components/kylq_components/ActionTop.vue'
import DanhSachKhuVuc from './DanhSachKhuVuc.vue'
import API from './API'
import { DataManager,Query,ODataV4Adaptor,Predicate } from "@syncfusion/ej2-data"
//API tham chieu UR1.2.023
//:disabled="!thongtin_diadanh.checkPho"
export default {
    name:'AssignLocationEmployeeHPG',
    components:{
        Footer,
        breadcrumb,
        KTableV3,
        ActionTop,
        DanhSachKhuVuc
    },
    data(){
        return{
            header: {
                title: "Gán địa bàn-nhân viên",
                list: [
                    {   
                        name: "Lập hợp đồng", 
                        link: { name: "Ui.cards" },
                        active: false
                    },
                    {
                        name: "Lắp đặt mới",
                        link: { name: "Ui.buttons" },
                        active: true
                    },
                ],
            },
            actions:[
                {
                    id:'layds',
                    name:'Lấy DS',
                    active: true,
                    icon_class:'one-file-attach1'
                },
                {
                    id:'ghilai',
                    name:'Ghi lại',
                    active: true,
                    icon_class:'one-save'
                },
                {
                    id:'xoanap',
                    name:'Xóa nạp',
                    active: true,
                    icon_class:'one-trash'
                },
            ],
            columnsKhuVuc:[
                {
                    field:'ten_quan',
                    label:'Quận',
                    allowFilter:true,
                    allowSorting:true
                },
                {
                    field:'ten_phuong',
                    label:'Phường',
                    allowFilter:true,
                    allowSorting:true
                },
                {
                    field:'ten_pho',
                    label:'Phố',
                    allowFilter:true,
                    allowSorting:true
                },
                {
                    field:'dacdiem',
                    label:'Đặc điểm',
                    allowFilter:true,
                    allowSorting:true
                },
                {
                    field:'ten_ap',
                    label:'Ấp',
                    allowFilter:true,
                    allowSorting:true
                },
                {
                    field:'ten_khu',
                    label:'Khu',
                    allowFilter:true,
                    allowSorting:true
                },

            ],
            dataSources:[],
            dataSourcesDaNap:[],
            //param tag
            kieugoi_id:0,

            ds_nhanvien:[],
            nhanvien_selected:-1,
            ds_loainv:[],
            loainv_selected:null,
            ds_khuvuc:[],
            khuvuc_focus:null,
            khuvuc_id:0,
            khuvuc_cha_selected:null,
            input_ma_khucvuc:'',
            input_khucvuc:'',
            input_ghichu:'',
            dataCheckKhuVuc:[],
            dataCheckKhuVucNap:[]
        }
    },
    computed:{
        disabledLoaiNV(){
            return this.ds_loainv.length==0
        },
        nhanvien_o_selected(){
            const nhanvien=this.ds_nhanvien.find(x=>x.nhanvien_id==this.nhanvien_selected)
            return nhanvien!=undefined?nhanvien:null
        },
        loainv_o_selected(){
            const loainv=this.ds_loainv.find(x=>x.loainv_id==this.loainv_selected)
            return loainv!=undefined?loainv:null
        }
    },
    methods:{
        onActionClick(action){
            if(action.id=='layds'){
                this.lay_ds()
            }else if(action.id=='ghilai'){
                this.ghiDuLieu()
            }else if(action.id=='xoanap'){
                this.xoaNap()
            }
        },
        async initDuLieu(){
            //Tag
            //khởi kieugoi
            let kieugoi = this.$route.query.kieugoi_id
            if(kieugoi==undefined){
                this.kieugoi_id=0
            }else{
                this.kieugoi_id=Number(kieugoi)
            }
            this.ds_nhanvien=await this.lay_ds_nhanvien()
            this.nhanvien_selected=-1

            await this.hienthi_ds_khuvuc()
        },
        // async changeNhanVien(data){
        //     this.nhanvien_selected=data.nhanvien_id
        //     this.loainv_selected=null
        //     this.ds_loainv=await this.sp_ds_loainhanvien(this.nhanvien_selected, 1)
        //     if(this.ds_loainv.length>0){
        //         this.loainv_selected=this.ds_loainv[0].loainv_id
        //         await this.load_ds()
        //         await this.hienthi_dulieu_nap()
        //     }
            
            
        // },
        async changeLoaiNV(data){
            this.loainv_selected=data.loainv_id
            console.log('data',data)
            await this.load_ds()
            await this.hienthi_dulieu_nap()
        },
        async lay_ds(){
            await this.load_ds()
            await this.hienthi_dulieu_nap()
        },
        async ghiDuLieu(){
            //kiểm tra dữ liệu
            if(this.nhanvien_selected==null){
                this.$toast.error('Chưa chọn thông tin nhân viên !')
                return
            }
            if(this.loainv_selected==null){
                this.$toast.error('Chưa chọn thông tin loại nhân viên !')
                return
            }
            //tạo dữ liệu
            let data=this.taoDuLieu()
            let ds_param={
                nhanvien_id:this.nhanvien_selected,
                loainv_id:this.loainv_selected,
                js_nhanvien_px:data
            }
            

            console.log('ds_param', JSON.stringify(ds_param))
            let result = await this.fn_nhanvien_diadanh_capnhat(JSON.stringify(ds_param))
            if(result!=''){
                let kq=JSON.parse(result)
                if(kq.ERROR_CODE==1){
                    this.$toast.success('Cập nhật dữ liệu thành công !')
                    await this.load_ds()
                }else{
                    this.$toast.error(kq.MESSAGE)
                }
            }
        },
        xoaNap(){
            if(this.dataCheckKhuVucNap.length==0){
                return
            }
            this.dataCheckKhuVucNap.forEach((item)=>{
                const index=this.dataSourcesDaNap.findIndex(x=>x.id==item.id)
                if(index>-1){
                    this.dataSourcesDaNap.splice(index,1)
                }
            })
        },
        taoDuLieu(){
            let data=this.dataSourcesDaNap.map(item=>{
                return {
                    NHANVIENPX_ID:0,
                    NHANVIEN_ID:this.nhanvien_selected,
                    LOAINV_ID:this.loainv_selected,
                    PHUONG_ID:item.phuong_id?item.phuong_id:0,
                    PHO_ID:item.pho_id?item.pho_id:0,
                    AP_ID:item.ap_id?item.ap_id:0,
                    KHU_ID:item.khu_id?item.khu_id:0,
                    DACDIEM_ID:item.dacdiem_id?item.dacdiem_id:0,
                    QUAN_ID:item.quan_id?item.quan_id:0,
                    KHUVUC_ID:item.khuvuc_id?item.khuvuc_id:0
                }
            })
            return JSON.stringify(data)
        },
        clickRight(){
            if(this.dataCheckKhuVuc.length==0){
                return
            }
            this.dataCheckKhuVuc.forEach((item)=>{
                //kiểm tra trùng trước khi thực thi
                let check=this.dataSourcesDaNap.findIndex(x=>x.id==item.id)
                if(check<0){
                    this.dataSourcesDaNap.push(item)
                    // const index=this.dataSources.findIndex(x=>x.id==item.id)
                    // if(index>-1){
                    //     this.dataSources.splice(index,1)
                    // }
                }
            })
            // this.dataCheckKhuVuc=[]
            // this.dataCheckKhuVucNap=[]
        },
        clickLeft(){
            if(this.dataCheckKhuVucNap.length==0){
                return
            }
            this.dataCheckKhuVucNap.forEach((item)=>{
                //kiểm tra trùng trước khi thực thi
                let check=this.dataSources.findIndex(x=>x.id==item.id)
                if(check<0){
                    this.dataSources.push(item)
                    const index=this.dataSourcesDaNap.findIndex(x=>x.id==item.id)
                    if(index>-1){
                        this.dataSourcesDaNap.splice(index,1)
                    }
                }
            })
            this.dataCheckKhuVuc=[]
            this.dataCheckKhuVucNap=[]
        },
        dataCheckedKhuVuc(data){
            this.dataCheckKhuVuc=data
        },
        dataCheckedKhuVucNap(data){
            this.dataCheckKhuVucNap=data
        },
        async hienthi_ds_khuvuc(){
            this.ds_khuvuc=[]
            if(this.kieugoi_id==0){
                this.ds_khuvuc=await this.diaban_taocap4()
            }else{
                this.ds_khuvuc=await this.diaban_taocap4_kinhdoan()
            }
            //build tree -->DanhSachKhuVuc.vue
        },
        async hienthi_dulieu_nap(){
            if(this.nhanvien_selected==null||this.loainv_selected==null){
                return
            }
            //load
            this.dataSourcesDaNap=[]
            this.dataSourcesDaNap=await this.diaban_laydsnv_px(this.nhanvien_selected, this.loainv_selected)

        },
        async load_ds(){
            if(this.khuvuc_focus==undefined||this.khuvuc_focus==null){
                return
            }
            let arr=this.khuvuc_focus.khuvuc_id.split('-')
            if(arr.length>1){
                //this.nhanvien_selected=Number(arr[0])
                //let nhanvien=this.ds_nhanvien.find(x=>x.nhanvien_id==this.nhanvien_selected)

                // this.ds_loainv=await this.sp_ds_loainhanvien(this.nhanvien_selected, 1)
                // if(this.ds_loainv.length>0){
                //     //this.loainv_selected=this.ds_loainv[0].loainv_id
                //     await this.hienthi_dulieu_nap()
                // }
                await this.hienthi_dulieu_nap()

                //await this.changeNhanVien(nhanvien)
                this.khuvuc_id=Number(arr[1])

                this.dataSources=[]
                let ds_param={
                    khuvuc_id:this.khuvuc_id,
                    loainv_id:this.loainv_selected
                }
                let result=await this.sp_cboloainv_changed(JSON.stringify(ds_param))
                if(result==''){
                    return
                }
                result=JSON.parse(result)
                if(result.ERROR_CODE!=1){
                    this.$toast.error(result.MESSAGE)
                    return
                }
                console.log('result', result)
                let DS_KhuVuc=(result.DS_KhuVuc!=null&&result.DS_KhuVuc!='')?JSON.parse(result.DS_KhuVuc):[]
                console.log('DS_KhuVuc', DS_KhuVuc)
                if(DS_KhuVuc.length>0){
                    this.input_ghichu=DS_KhuVuc[0].GHICHU
                    this.input_khucvuc=DS_KhuVuc[0].TEN_KV
                    this.input_ma_khucvuc=DS_KhuVuc[0].MA_KV
                    if(DS_KhuVuc[0].KHUVUC_CHA_ID==null||DS_KhuVuc[0].KHUVUC_CHA_ID==''){
                        const index=this.ds_khuvuc.findIndex(x=>x.khuvuc_cha_id=='0')
                        if(index>-1){
                            this.khuvuc_cha_selected=this.ds_khuvuc[index].khuvuc_id
                        }
                    }else{
                        this.khuvuc_cha_selected=DS_KhuVuc[0].KHUVUC_CHA_ID.toString()
                    }
                }
                //
                let DS_KhuVuc_PX=(result.DS_KhuVuc_PX!=null&&result.DS_KhuVuc_PX!='')?JSON.parse(result.DS_KhuVuc_PX):[]
                this.dataSources=DS_KhuVuc_PX.map(x=>{
                    return {
                        id: this.getKey(x.QUAN_ID, x.PHUONG_ID, x.PHO_ID, x.AP_ID, x.KHU_ID, x.DACDIEM_ID),
                        ischeck: x.ISCHECK,
                        quan_id: x.QUAN_ID,
                        khuvuc_id: x.KHUVUC_ID,
                        phuong_id: x.PHUONG_ID,
                        pho_id: x.PHO_ID,
                        ap_id: x.AP_ID,
                        khu_id: x.KHU_ID,
                        dacdiem_id: x.DACDIEM_ID,
                        ten_quan: x.TEN_QUAN,
                        ten_phuong: x.TEN_PHUONG,
                        ten_pho: x.TEN_PHO,
                        ten_ap: x.TEN_AP,
                        ten_khu: x.TEN_KHU,
                        dacdiem: x.DACDIEM
                    }
                })
            }
        },
        getKey(quan_id, phuong_id, pho_id, ap_id, khu_id, dacdiem_id){
            return quan_id+'-'+phuong_id+'-'+pho_id+'-'+ap_id+'-'+khu_id+'-'+dacdiem_id
        },
        async focusedNodeChanged(khuvuc){
            //tvwKhuVuc_AfterFocusNode
            if(khuvuc==undefined||khuvuc==null){
                return
            }
            this.khuvuc_focus=khuvuc
            //
            if(khuvuc.unitlevel==4){
                let arr=khuvuc.khuvuc_id.split('-')
                if(arr.length>1){
                    this.nhanvien_selected=Number(arr[0])
                    // let nhanvien=this.ds_nhanvien.find(x=>x.nhanvien_id==this.nhanvien_selected)
                    // await this.changeNhanVien(nhanvien)
                    this.ds_loainv=await this.sp_ds_loainhanvien(this.nhanvien_selected, 1)
                    if(this.ds_loainv.length>0){
                        this.loainv_selected=this.ds_loainv[0].loainv_id
                        await this.hienthi_dulieu_nap()
                    }
                    this.khuvuc_id=Number(arr[1])
                    // let 
                    // int loainv_id = Convert.ToInt32(cboLoaiNV.EditValue);
                    this.dataSources=[]
                    let ds_param={
                        khuvuc_id:this.khuvuc_id,
                        loainv_id:this.loainv_selected
                    }
                    let result=await this.sp_cboloainv_changed(JSON.stringify(ds_param))
                    if(result==''){
                        return
                    }
                    result=JSON.parse(result)
                    if(result.ERROR_CODE!=1){
                        this.$toast.error(result.MESSAGE)
                        return
                    }
                    console.log('result', result)
                    let DS_KhuVuc=(result.DS_KhuVuc!=null&&result.DS_KhuVuc!='')?JSON.parse(result.DS_KhuVuc):[]
                    console.log('DS_KhuVuc', DS_KhuVuc)
                    if(DS_KhuVuc.length>0){
                        this.input_ghichu=DS_KhuVuc[0].GHICHU
                        this.input_khucvuc=DS_KhuVuc[0].TEN_KV
                        this.input_ma_khucvuc=DS_KhuVuc[0].MA_KV
                        if(DS_KhuVuc[0].KHUVUC_CHA_ID==null||DS_KhuVuc[0].KHUVUC_CHA_ID==''){
                            const index=this.ds_khuvuc.findIndex(x=>x.khuvuc_cha_id=='0')
                            if(index>-1){
                                this.khuvuc_cha_selected=this.ds_khuvuc[index].khuvuc_id
                            }
                        }else{
                            this.khuvuc_cha_selected=DS_KhuVuc[0].KHUVUC_CHA_ID.toString()
                        }
                    }
                    //
                    let DS_KhuVuc_PX=(result.DS_KhuVuc_PX!=null&&result.DS_KhuVuc_PX!='')?JSON.parse(result.DS_KhuVuc_PX):[]
                    this.dataSources=DS_KhuVuc_PX.map(x=>{
                        return {
                            id: this.getKey(x.QUAN_ID, x.PHUONG_ID, x.PHO_ID, x.AP_ID, x.KHU_ID, x.DACDIEM_ID),
                            ischeck: x.ISCHECK,
                            quan_id: x.QUAN_ID,
                            khuvuc_id: x.KHUVUC_ID,
                            phuong_id: x.PHUONG_ID,
                            pho_id: x.PHO_ID,
                            ap_id: x.AP_ID,
                            khu_id: x.KHU_ID,
                            dacdiem_id: x.DACDIEM_ID,
                            ten_quan: x.TEN_QUAN,
                            ten_phuong: x.TEN_PHUONG,
                            ten_pho: x.TEN_PHO,
                            ten_ap: x.TEN_AP,
                            ten_khu: x.TEN_KHU,
                            dacdiem: x.DACDIEM
                        }
                    })
                }
            }else{
                this.khuvuc_id=Number(khuvuc.khuvuc_id)
                let ds_param={
                    khuvuc_id:this.khuvuc_id,
                    loainv_id:this.loainv_selected
                }
                let result=await this.sp_cboloainv_changed(JSON.stringify(ds_param))
                if(result==''){
                    return
                }
                result=JSON.parse(result)
                if(result.ERROR_CODE!=1){
                    this.$toast.error(result.MESSAGE)
                    return
                }
                console.log('result', result)
                let DS_KhuVuc=(result.DS_KhuVuc!=null&&result.DS_KhuVuc!='')?JSON.parse(result.DS_KhuVuc):[]
                console.log('DS_KhuVuc', DS_KhuVuc)
                if(DS_KhuVuc.length>0){
                    this.input_ghichu=DS_KhuVuc[0].GHICHU
                    this.input_khucvuc=DS_KhuVuc[0].TEN_KV
                    this.input_ma_khucvuc=DS_KhuVuc[0].MA_KV
                    if(DS_KhuVuc[0].KHUVUC_CHA_ID==null||DS_KhuVuc[0].KHUVUC_CHA_ID==''){
                        const index=this.ds_khuvuc.findIndex(x=>x.khuvuc_cha_id=='0')
                        if(index>-1){
                             this.khuvuc_cha_selected=this.ds_khuvuc[index].khuvuc_id
                        }
                    }else{
                        this.khuvuc_cha_selected=DS_KhuVuc[0].KHUVUC_CHA_ID.toString()
                    }
                }
                this.dataSources=[]
                this.nhanvien_selected=null
                this.loainv_selected=null
                this.dataSourcesDaNap=[]
                this.ds_loainv=[]
            }
        },
        onFilteringNhanVien(e){
            var query = new Query();
            let predicate;  
            //frame the query based on search string with filter type.
            if (e.text.trim() !== ''){
                predicate  = new Predicate('ten_nv', 'contains', e.text.trim(), true);
                //predicate = predicate.or('TEN_KHO', 'contains', e.text);
                query.where(predicate)
            }
            //pass the filter data source, filter query to updateData method.
            e.updateData(this.ds_nhanvien, query);
        },
        onFilteringLoaiNV(e){
            var query = new Query();
            let predicate;  
            //frame the query based on search string with filter type.
            if (e.text.trim() !== ''){
                predicate  = new Predicate('ten', 'contains', e.text.trim(), true);
                //predicate = predicate.or('TEN_KHO', 'contains', e.text);
                query.where(predicate)
            }
            //pass the filter data source, filter query to updateData method.
            e.updateData(this.ds_loainv, query);
        },
        async lay_ds_nhanvien(){
            try{
                this.loading(true)
                let response=await API.lay_ds_nhanvien(this.axios)
                this.loading(false)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000'&&response.data.data) {
                    return response.data.data.map(x=>Object.assign(x,{ten_nv:x.ma_nv+'-'+x.ten_nv}))
                }else{
                    return []
                }
            }catch(e){
                this.loading(false)
                return []
            }
        },
        async sp_ds_loainhanvien(nhanvien_id, kieu){
            try{
                this.loading(true)
                let response=await API.sp_ds_loainhanvien(this.axios, nhanvien_id, kieu)
                this.loading(false)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000'&&response.data.data) {
                    return response.data.data
                }else{
                    return []
                }
            }catch(e){
                this.loading(false)
                return []
            }
        },
        async diaban_laydsnv_px(nhanvien_id, loainv_id){
            try{
                this.loading(true)
                let response=await API.diaban_laydsnv_px(this.axios, nhanvien_id, loainv_id)
                this.loading(false)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000'&&response.data.data) {
                    return response.data.data.map(x=>Object.assign(x,{
                        id:this.getKey(x.quan_id, x.phuong_id, x.pho_id, x.ap_id, x.khu_id, x.dacdiem_id)
                    }))
                }else{
                    return []
                }
            }catch(e){
                this.loading(false)
                return []
            }
        },
        async diaban_taocap4(){
            try{
                this.loading(true)
                let response=await API.diaban_taocap4(this.axios)
                this.loading(false)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000'&&response.data.data) {
                    return response.data.data
                }else{
                    return []
                }
            }catch(e){
                this.loading(false)
                return []
            }
        },
        async diaban_taocap4_kinhdoan(){
            try{
                this.loading(true)
                let response=await API.diaban_taocap4_kinhdoan(this.axios)
                this.loading(false)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000'&&response.data.data) {
                    return response.data.data
                }else{
                    return []
                }
            }catch(e){
                this.loading(false)
                return []
            }
        },
        async sp_cboloainv_changed(ds_para){
            try{
                this.loading(true)
                let response=await API.sp_cboloainv_changed(this.axios, ds_para)
                this.loading(false)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000'&&response.data.data) {
                    return response.data.data
                }else{
                    return ''
                }
            }catch(e){
                this.loading(false)
                return ''
            }
        },
        async sp_lay_ds_khuvuc_px_v2(khuvuc_id){
            try{
                this.loading(true)
                let response=await API.sp_lay_ds_khuvuc_px_v2(this.axios, khuvuc_id)
                this.loading(false)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000'&&response.data.data) {
                    return response.data.data
                }else{
                    return ''
                }
            }catch(e){
                this.loading(false)
                return ''
            }
        },
        async fn_nhanvien_diadanh_capnhat(ds_para){
            try{
                this.loading(true)
                let response=await API.fn_nhanvien_diadanh_capnhat(this.axios, ds_para)
                this.loading(false)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000'&&response.data.data) {
                    return response.data.data
                }else{
                    if(response.data&&response.data.message){
                        this.$toast.error(response.data.message)
                    }else{
                        this.$toast.error('Đã xảy ra lỗi không thể cập nhật được dữ liệu')
                    }
                    return ''
                }
            }catch(e){
                this.loading(false)
                if(e.data&&e.data.message){
                    this.$toast.error(e.data.message)
                }else if(e.response&&e.response.data&&e.response.data.message){
                    this.$toast.error(e.response.data.message)
                }else{
                    this.$toast.error('Đã xảy ra lỗi không thể cập nhật được dữ liệu')
                }
                return ''
            }
        },
   
    },
    mounted(){
        setTimeout(()=>{
            this.initDuLieu()
        },500)
    }
}
</script>
<style>
.multiselect, .multiselect__input, .multiselect__single {
    font-size: 1rem !important;
}
.multiselect__tags{
    min-height: 32px !important;
    border: 1px solid #ced4da !important;
    padding: 6px 32px 0 6px;
    border-radius: 4px;
}
.multiselect__placeholder {
    margin-bottom: 5px!important;
    padding-top: 0px!important;
}
.multiselect__select{
    top: 0px!important;
    padding: 10px 9px!important;
}
.multiselect--disabled{
  background: none!important;
}
.multiselect--disabled .multiselect__current, .multiselect--disabled .multiselect__select{
  background: none!important;
}
</style>