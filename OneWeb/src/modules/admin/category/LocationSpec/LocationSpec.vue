<template>
    <div class="main-wrapper">

        <breadcrumb :header="header"/>

        <ActionTop :actions="actions" @onActionClick="onActionClick"/>

        <div class="page-content">
            <div class="row">
                <div class="col-sm-5 col-12">
                    <div class="box-form">
                        <div class="legend-title">Thông tin Quận, Phường (Đặc điểm)</div>
                        <div class="info-row">
                            <div class="key w90">Quận/Huyện</div>
                            <div class="value">
                                <div class="select-custom" ref="ds_quan">
                                    <select2 :settings="{ dropdownParent: $refs['ds_quan'] }" ref="cboQuan"
                                        v-model="danhsach_dacdiem.quan_huyen_selected" class="select2"
                                        :options="danhsach_dacdiem.quan_huyen.map(e=> ({id: e.QUAN_ID, text: e.TEN_QUAN}))"
                                        @change="changeQuanDacDiem"
                                    >
                                    </select2>
                                </div> 
                            </div>
                        </div>
                        <div class="info-row">
                            <div class="key w90">Phường/Xã</div>
                            <div class="value">
                                <div class="select-custom" ref="ds_phuong">
                                    <select2 :settings="{ dropdownParent: $refs['ds_phuong'] }" ref="cboPhuong"
                                        v-model="danhsach_dacdiem.phuong_xa_selected" class="select2"
                                        :options="danhsach_dacdiem.phuong_xa.map(e=> ({id: e.PHUONG_ID, text: e.TEN_PHUONG}))"
                                        @change="changePhuongDacDiem"
                                    >
                                    </select2>
                                </div> 
                            </div>
                        </div>
                    </div>
                    <div class="box-form">
                        <div class="legend-title">
                            Thông tin đặc điểm
                        </div>
                        <div class="info-row">
                            <div class="key w90">Đặc điểm <k-required-marker/></div>
                            <div class="value">
                                <input type="text" v-model="danhsach_dacdiem.input_dacdiem" class="form-control">
                            </div>
                        </div>
                        <KTableV3 :columns="columns.dacdiem"
                            :dataSources="danhsach_dacdiem.dacdiem"
                            @onSelectedRow="selectedRowDacDiem"
                            id="dacdiem_id"
                            :rowSelectedId="rowSelectedId"
                            :allowFilter="true" />
                    </div>
                </div>
                <div class="col-sm-7 col-12">
                    <div class="box-form">
                        <div class="legend-title">Thông tin địa danh</div>
                        <div class="info-row">
                            <div class="key w80 nowrap">
                                Quận/Huyện
                            </div>
                            <div class="value">
                                <div class="select-custom" ref="ds_quan_diadanh">
                                    <select2 :settings="{ dropdownParent: $refs['ds_quan_diadanh'] }"
                                        v-model="danhsach_diadanh.quan_huyen_selected" class="select2"
                                        :options="danhsach_diadanh.quan_huyen.map(e=> ({id: e.QUAN_ID, text: e.TEN_QUAN}))"
                                        @change="changeQuanDiaDanh"
                                    >
                                    </select2>
                                </div>
                            </div>
                        </div>
                        <div class="info-row">
                            <div class="key w80 nowrap">
                                Phường/Xã
                            </div>
                            <div class="value">
                                <div class="select-custom" ref="ds_phuong_diadanh">
                                    <select2 :settings="{ dropdownParent: $refs['ds_phuong_diadanh'] }"
                                        v-model="danhsach_diadanh.phuong_xa_selected" class="select2"
                                        :options="danhsach_diadanh.phuong_xa.map(e=> ({id: e.PHUONG_ID, text: e.TEN_PHUONG}))"
                                        @change="changePhuongDiaDanh"
                                    >
                                    </select2>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="box-move-select-table">
                        <div class="box-col box-form">
                            <div class="legend-title">Phố đã gán</div>
                            <KTableV3 :columns="columns.pho_gan"
                                :dataSources="danhsach_diadanh.pho_gan"
                                @dataCheckeds="onDataCheckedsGan($event, NHOMPHO.PHO)"
                                :allowFilter="true"
                                :allowCheckBox="true"
                                id="pho_id"
                                :isShowPagination="false"
                                tableHeight="160px"/>
                        </div>
                        <div class="actions">
                            <button class="btn" @click="boGan(NHOMPHO.PHO)">
                                <span class="fa fa-angle-right"></span>
                            </button>
                            <button class="btn" @click="boGanAll(NHOMPHO.PHO)">
                                <span class="fa fa-angle-double-right"></span>
                            </button>
                            <button class="btn" @click="gan(NHOMPHO.PHO)">
                                <span class="fa fa-angle-left"></span>
                            </button>
                            <button class="btn" @click="ganAll(NHOMPHO.PHO)">
                                <span class="fa fa-angle-double-left"></span>
                            </button>
                        </div>
                        <div class="box-col box-form">
                            <div class="legend-title">Phố chưa gán</div>
                            <KTableV3 :columns="columns.pho_chuagan"
                                :dataSources="danhsach_diadanh.pho_chuagan"
                                @dataCheckeds="onDataCheckedsChuaGan($event, NHOMPHO.PHO)" 
                                :allowFilter="true"
                                 id="pho_id"
                                :allowCheckBox="true"
                                :isShowPagination="false"
                                tableHeight="160px"/>
                        </div>
                    </div>
                    <div class="box-move-select-table">
                        <div class="box-col box-form">
                            <div class="legend-title">Ấp đã gán</div>
                            <KTableV3 :columns="columns.ap_gan"
                                :dataSources="danhsach_diadanh.ap_gan"
                                @dataCheckeds="onDataCheckedsGan($event, NHOMPHO.AP)"
                                :allowFilter="true"
                                 id="pho_id"
                                :allowCheckBox="true"
                                :isShowPagination="false"
                                tableHeight="160px"/>
                        </div>
                        <div class="actions">
                            <button class="btn" @click="boGan(NHOMPHO.AP)">
                                <span class="fa fa-angle-right"></span>
                            </button>
                            <button class="btn" @click="boGanAll(NHOMPHO.AP)">
                                <span class="fa fa-angle-double-right"></span>
                            </button>
                            <button class="btn" @click="gan(NHOMPHO.AP)">
                                <span class="fa fa-angle-left"></span>
                            </button>
                            <button class="btn" @click="ganAll(NHOMPHO.AP)">
                                <span class="fa fa-angle-double-left"></span>
                            </button>
                        </div>
                        <div class="box-col box-form">
                            <div class="legend-title">Ấp chưa gán</div>
                            <KTableV3 :columns="columns.ap_chuagan"
                                :dataSources="danhsach_diadanh.ap_chuagan"
                                @dataCheckeds="onDataCheckedsChuaGan($event, NHOMPHO.AP)"
                                :allowFilter="true"
                                 id="pho_id"
                                :allowCheckBox="true"
                                :isShowPagination="false"
                                tableHeight="160px"/>
                        </div>
                    </div>
                    <div class="box-move-select-table">
                        <div class="box-col box-form">
                            <div class="legend-title">Khu đã gán</div>
                            <KTableV3 :columns="columns.khu_gan"
                                :dataSources="danhsach_diadanh.khu_gan"
                                @dataCheckeds="onDataCheckedsGan($event, NHOMPHO.KHU)"
                                :allowFilter="true"
                                 id="pho_id"
                                :allowCheckBox="true"
                                :isShowPagination="false"
                                tableHeight="160px"/>
                        </div>
                        <div class="actions">
                            <button class="btn" @click="boGan(NHOMPHO.KHU)">
                                <span class="fa fa-angle-right"></span>
                            </button>
                            <button class="btn" @click="boGanAll(NHOMPHO.KHU)">
                                <span class="fa fa-angle-double-right"></span>
                            </button>
                            <button class="btn" @click="gan(NHOMPHO.KHU)">
                                <span class="fa fa-angle-left"></span>
                            </button>
                            <button class="btn" @click="ganAll(NHOMPHO.KHU)">
                                <span class="fa fa-angle-double-left"></span>
                            </button>
                        </div>
                        <div class="box-col box-form">
                            <div class="legend-title">Khu chưa gán</div>
                            <KTableV3 :columns="columns.khu_chuagan"
                                :dataSources="danhsach_diadanh.khu_chuagan"
                                @dataCheckeds="onDataCheckedsChuaGan($event, NHOMPHO.KHU)"
                                :allowFilter="true"
                                 id="pho_id"
                                :allowCheckBox="true"
                                :isShowPagination="false"
                                tableHeight="160px"/>
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
import ActionTop from '@/components/kylq_components/ActionTop.vue'
import KTableV3 from '@/components/kylq_components/mirana/KTableV3.vue'
import KRequiredMarker from '@/components/kylq_components/KRequiredMarker.vue'
import LocationSpecAPI from './LocationSpecAPI'
//JS
import '@/assets/vendor/jquery/split'

export default {
    name:'LocationSpec',
    components:{
        Footer,
        breadcrumb,
        ActionTop,
        KRequiredMarker,
        KTableV3
    },
    data(){
        return{
            header: {
                title: "Cập nhật đặc điểm",
                list: [
                    { 
                        name: "Phát triển thuê bao", 
                        link: { name: "Ui.cards" },
                        active: false
                    },
                    { 
                        name: "Địa danh", 
                        link: { name: "Ui.cards" },
                        active: false
                    },
                    {
                        name: "Đặc điểm",
                        link: { name: "Ui.buttons" },
                        active: true
                    },
                ],
            },
            actions:[
                {
                    id:'nhapmoi',
                    name:'Nhập mới',
                    active: false,
                    icon_class:'one-file-plus'
                },
                {
                    id:'ghilai',
                    name:'Ghi lại',
                    active: false,
                    icon_class:'one-save'
                },
                {
                    id:'huy',
                    name:'Huỷ',
                    active: false,
                    icon_class:'nc-icon-glyph ui-1_circle-remove'
                },
                {
                    id:'xoa',
                    name:'Xoá',
                    active: false,
                    icon_class:'one-trash'
                }
            ],
            columns:{
                dacdiem:[
                    {
                        field: "stt",
                        label: "STT",
                        width:'20%',
                        allowFilter: true,
                        
                    },
                    {
                        field: "dacdiem",
                        label: "Đặc điểm",
                        allowFilter: true,
                       
                    }
                ],
                pho_gan:[
                    {
                        field: "pho_id",
                        label: "Phố ID",
                        allowFilter: true
                    },
                    {
                        field: "ten_pho",
                        label: "Tên phố",
                        allowFilter: true
                    },
                    {
                        field: "ten_phuong",
                        label: "Tên phường",
                        allowFilter: true
                    }
                ],
                pho_chuagan:[
                    {
                        field: "ten_pho",
                        label: "Tên phố",
                        allowFilter: true
                    },
                    {
                        field: "ten_phuong",
                        label: "Tên phường",
                        allowFilter: true
                    }
                ],

                ap_gan:[
                    {
                        field: "pho_id",
                        label: "Ấp ID",
                        allowFilter: true
                    },
                    {
                        field: "ten_pho",
                        label: "Tên ấp",
                        allowFilter: true
                    },
                    {
                        field: "ten_phuong",
                        label: "Tên phường",
                        allowFilter: true
                    }
                ],
                ap_chuagan:[
                    {
                        field: "ten_pho",
                        label: "Tên ấp",
                        allowFilter: true
                    },
                    {
                        field: "ten_phuong",
                        label: "Tên phường",
                        allowFilter: true
                    }
                ],
                khu_gan:[
                    {
                        field: "pho_id",
                        label: "Khu ID",
                        allowFilter: true
                    },
                    {
                        field: "ten_pho",
                        label: "Tên khu",
                        allowFilter: true
                    },
                    {
                        field: "ten_phuong",
                        label: "Tên phường",
                        allowFilter: true
                    }
                ],
                khu_chuagan:[
                    {
                        field: "ten_pho",
                        label: "Tên khu",
                        allowFilter: true
                    },
                    {
                        field: "ten_phuong",
                        label: "Tên phường",
                        allowFilter: true
                    }
                ],
            },
            danhsach_diadanh:{
                quan_huyen_selected:null,
                quan_huyen:[],
                phuong_xa_selected:null,
                phuong_xa:[],

                pho_gan:[],
                pho_chuagan:[],
                ap_gan:[],
                ap_chuagan:[],
                khu_gan:[],
                khu_chuagan:[],
            },
            danhsach_dacdiem:{
                quan_huyen_selected:null,
                quan_huyen:[],
                phuong_xa_selected:null,
                phuong_xa:[],

                dacdiem:[],
                dacdiem_selected:0,
                input_dacdiem:''
            },
            NHOMPHO:{
                PHO:1,
                AP:2,
                KHU:3
            },
            dataCheckBoxs:{
                pho_gan:[],
                pho_chuagan:[],
                ap_gan:[],
                ap_chuagan:[],
                khu_gan:[],
                khu_chuagan:[]
            },
            rowSelectedId:-1
        }
    },
    methods:{
        onActionClick(action){
            if(action.id=='nhapmoi'){
                this.setActiveActions(1)
                this.danhsach_diadanh.phuong_xa_selected=null
            }else if(action.id=='ghilai'){
                this.ghiDulieu()
            }else if(action.id=='huy'){
                this.setActiveActions(0)
                
            }else if(action.id=='xoa'){
                this.xoaDacDiem()
            }
        },
        setActiveActions(kieu){
            this.actions.forEach((item)=>{
                item.active=false
            })
            if(kieu==-1){//bắt đầu
                this.actions[this.actions.findIndex(x=>x.id=='ghilai')].active=true
                this.actions[this.actions.findIndex(x=>x.id=='huy')].active=true
            }else if(kieu==0){//bắt đầu
                this.actions[this.actions.findIndex(x=>x.id=='nhapmoi')].active=true
                this.clear()
            }else if(kieu==1){//thêm mới
                this.actions[this.actions.findIndex(x=>x.id=='ghilai')].active=true
                this.actions[this.actions.findIndex(x=>x.id=='huy')].active=true
                this.clear()

            }else if(kieu==2){//Huỷ
                this.actions[this.actions.findIndex(x=>x.id=='nhapmoi')].active=true
                this.actions[this.actions.findIndex(x=>x.id=='xoa')].active=true
                this.clear()
            }else if(kieu==3){//edit
                this.actions[this.actions.findIndex(x=>x.id=='nhapmoi')].active=true
                this.actions[this.actions.findIndex(x=>x.id=='ghilai')].active=true
                this.actions[this.actions.findIndex(x=>x.id=='huy')].active=true
                this.actions[this.actions.findIndex(x=>x.id=='xoa')].active=true
            }
        },
        clear(){
            this.danhsach_dacdiem.input_dacdiem=''
            this.danhsach_dacdiem.dacdiem_selected=0
            
            //this.danhsach_diadanh.phuong_xa_selected=null

            this.danhsach_diadanh.pho_gan=[]
            this.danhsach_diadanh.pho_chuagan=[]
            this.danhsach_diadanh.ap_gan=[]
            this.danhsach_diadanh.ap_chuagan=[]
            this.danhsach_diadanh.khu_gan=[]
            this.danhsach_diadanh.khu_chuagan=[]

            this.dataCheckBoxs={
                pho_gan:[],
                pho_chuagan:[],
                ap_gan:[],
                ap_chuagan:[],
                khu_gan:[],
                khu_chuagan:[]
            }

        },
        //loai=1:dacdiem, 2:diadanh
        changeQuanDacDiem(){
            this.lay_danhsach_phuong(this.danhsach_dacdiem.quan_huyen_selected, 1)
        },
        changeQuanDiaDanh(){
            this.lay_danhsach_phuong(this.danhsach_diadanh.quan_huyen_selected, 2)
        },
        changePhuong(event, loai){
            if(loai==1){
                this.hienthi_danhsach_dacdiem()
            }else{
                this.lay_danhsach_chuagan()
            }
        },
        changePhuongDacDiem(){
            this.hienthi_danhsach_dacdiem()
        },
        changePhuongDiaDanh(){
            this.lay_danhsach_chuagan()
        },
        onDataCheckedsGan(data, nhompho_id){
            if(nhompho_id==this.NHOMPHO.PHO){
                this.dataCheckBoxs.pho_gan=data
            }else if(nhompho_id==this.NHOMPHO.AP){
                this.dataCheckBoxs.ap_gan=data
            }else if(nhompho_id==this.NHOMPHO.KHU){
                this.dataCheckBoxs.khu_gan=data
            }
        },
        onDataCheckedsChuaGan(data, nhompho_id){
            if(nhompho_id==this.NHOMPHO.PHO){
                this.dataCheckBoxs.pho_chuagan=data
            }else if(nhompho_id==this.NHOMPHO.AP){
                this.dataCheckBoxs.ap_chuagan=data
            }else if(nhompho_id==this.NHOMPHO.KHU){
                this.dataCheckBoxs.khu_chuagan=data
            }
        },
        ganAll(nhompho_id){
            if(nhompho_id==this.NHOMPHO.PHO){

                if(this.danhsach_diadanh.pho_chuagan.length == 0){
                    return
                }
                this.danhsach_diadanh.pho_chuagan.forEach((item)=>{
                    this.danhsach_diadanh.pho_gan.push(item)
                })
                this.danhsach_diadanh.pho_chuagan=[]

            }else if(nhompho_id==this.NHOMPHO.AP){

                if(this.danhsach_diadanh.ap_chuagan.length == 0){
                    return
                }
                this.danhsach_diadanh.ap_chuagan.forEach((item)=>{
                    this.danhsach_diadanh.ap_gan.push(item)
                })
                this.danhsach_diadanh.ap_chuagan=[]
                
            }else if(nhompho_id==this.NHOMPHO.KHU){
                if(this.danhsach_diadanh.khu_chuagan.length == 0){
                    return
                }
                this.danhsach_diadanh.khu_chuagan.forEach((item)=>{
                    this.danhsach_diadanh.khu_gan.push(item)
                })
                this.danhsach_diadanh.khu_chuagan=[]  
            }
        },
        boGanAll(nhompho_id){
            if(nhompho_id==this.NHOMPHO.PHO){

                if(this.danhsach_diadanh.pho_gan.length == 0){
                    return
                }
                this.danhsach_diadanh.pho_gan.forEach((item)=>{
                    this.danhsach_diadanh.pho_chuagan.push(item)
                })
                this.danhsach_diadanh.pho_gan=[]

            }else if(nhompho_id==this.NHOMPHO.AP){

                if(this.danhsach_diadanh.ap_gan.length == 0){
                    return
                }
                this.danhsach_diadanh.ap_gan.forEach((item)=>{
                    this.danhsach_diadanh.ap_chuagan.push(item)
                })
                this.danhsach_diadanh.ap_gan=[]
                
            }else if(nhompho_id==this.NHOMPHO.KHU){
                if(this.danhsach_diadanh.khu_gan.length == 0){
                    return
                }
                this.danhsach_diadanh.khu_gan.forEach((item)=>{
                    this.danhsach_diadanh.khu_chuagan.push(item)
                })
                this.danhsach_diadanh.khu_gan=[]
            }
        },
        gan(nhompho_id){
            if(nhompho_id==this.NHOMPHO.PHO){
                if(this.dataCheckBoxs.pho_chuagan.length==0){
                    return;
                }
                this.dataCheckBoxs.pho_chuagan.forEach((item)=>{
                    this.danhsach_diadanh.pho_gan.push(item)

                    //remove
                    const index =this.danhsach_diadanh.pho_chuagan.findIndex(x=>x.pho_id==item.pho_id&&x.phuong_id==item.phuong_id)
                    if(index>-1){
                        this.danhsach_diadanh.pho_chuagan.splice(index,1)
                    }
                })
                this.dataCheckBoxs.pho_chuagan=[]
            }else if(nhompho_id==this.NHOMPHO.AP){
                if(this.dataCheckBoxs.ap_chuagan.length==0){
                    return;
                }
                this.dataCheckBoxs.ap_chuagan.forEach((item)=>{
                    this.danhsach_diadanh.ap_gan.push(item)

                    //remove
                    const index =this.danhsach_diadanh.ap_chuagan.findIndex(x=>x.pho_id==item.pho_id&&x.phuong_id==item.phuong_id)
                    if(index>-1){
                        this.danhsach_diadanh.ap_chuagan.splice(index,1)
                    }
                })
                this.dataCheckBoxs.ap_chuagan=[]

            }else if(nhompho_id==this.NHOMPHO.KHU){
                if(this.dataCheckBoxs.khu_chuagan.length==0){
                    return;
                }
                this.dataCheckBoxs.khu_chuagan.forEach((item)=>{
                    this.danhsach_diadanh.khu_gan.push(item)

                    //remove
                    const index =this.danhsach_diadanh.khu_chuagan.findIndex(x=>x.pho_id==item.pho_id&&x.phuong_id==item.phuong_id)
                    if(index>-1){
                        this.danhsach_diadanh.khu_chuagan.splice(index,1)
                    }
                })
                this.dataCheckBoxs.khu_chuagan=[]
            }
        },
        boGan(nhompho_id){
            if(nhompho_id==this.NHOMPHO.PHO){
                
                if(this.dataCheckBoxs.pho_gan.length==0){
                    return;
                }
                this.dataCheckBoxs.pho_gan.forEach((item)=>{
                    this.danhsach_diadanh.pho_chuagan.push(item)

                    //remove
                    const index =this.danhsach_diadanh.pho_gan.findIndex(x=>x.pho_id==item.pho_id&&x.phuong_id==item.phuong_id)
                    if(index>-1){
                        this.danhsach_diadanh.pho_gan.splice(index,1)
                    }
                })
                this.dataCheckBoxs.pho_gan=[]


            }else if(nhompho_id==this.NHOMPHO.AP){
                if(this.dataCheckBoxs.ap_gan.length==0){
                    return;
                }
                this.dataCheckBoxs.ap_gan.forEach((item)=>{
                    this.danhsach_diadanh.ap_chuagan.push(item)

                    //remove
                    const index =this.danhsach_diadanh.ap_gan.findIndex(x=>x.pho_id==item.pho_id&&x.phuong_id==item.phuong_id)
                    if(index>-1){
                        this.danhsach_diadanh.ap_gan.splice(index,1)
                    }
                })
                this.dataCheckBoxs.ap_gan=[]

            }else if(nhompho_id==this.NHOMPHO.KHU){
                if(this.dataCheckBoxs.khu_gan.length==0){
                    return;
                }
                this.dataCheckBoxs.khu_gan.forEach((item)=>{
                    this.danhsach_diadanh.khu_chuagan.push(item)

                    //remove
                    const index =this.danhsach_diadanh.khu_gan.findIndex(x=>x.pho_id==item.pho_id&&x.phuong_id==item.phuong_id)
                    if(index>-1){
                        this.danhsach_diadanh.khu_gan.splice(index,1)
                    }
                })
                this.dataCheckBoxs.khu_gan=[]
            }
        },
        ghiDulieu(){
            var is_insert=this.actions[this.actions.findIndex(x=>x.id=='nhapmoi')].active==true?0:1
            //kiểm tra dữ liệu
            if(this.danhsach_dacdiem.input_dacdiem.trim()==''){
                this.$toast.error('Đặc điểm không được để trống !')
                return
            }
            if(this.danhsach_dacdiem.input_dacdiem.trim().length>150){
                this.$toast.error('Đặc điểm không được vượt quá 150 ký tự')
                return
            }

            if(this.actions[this.actions.findIndex(x=>x.id=='nhapmoi')].active==true){
                if(this.danhsach_dacdiem.dacdiem.length==0){
                    this.$toast.error('Không có Đặc điểm để cập nhật !')
                    return
                } 
            }

            if(this.danhsach_diadanh.pho_gan.length==0&&this.danhsach_diadanh.ap_gan.length==0&&this.danhsach_diadanh.khu_gan.length==0){
                this.$toast.error('Bạn chưa gán đặc điểm cho phố phường!')
                return
            }
            //tạo dữ liệu
            if(is_insert==0&&this.danhsach_dacdiem.dacdiem_selected==0){
                this.$toast.error('Không được cập nhật đặc điểm Không xác định (dacdiem_id = 0)')
                return
            }

            
            let danhsach=this.getDanhSach()

            let data={
                is_insert:is_insert,
                dacdiem_id:this.danhsach_dacdiem.dacdiem_selected,
                dacdiem:this.danhsach_dacdiem.input_dacdiem.trim(),
                danhsach:danhsach
            }

            //console.log('data',data)
            this.sp_capnhat_dacdiem(data, is_insert)



        },
        getDanhSach(){
            //gộp danh sách
            let danhsach=[].concat(this.danhsach_diadanh.pho_gan).concat(this.danhsach_diadanh.ap_gan).concat(this.danhsach_diadanh.khu_gan)
            return JSON.stringify(danhsach.map(x=>{
                return {
                    pho_id:x.pho_id,
                    phuong_id:x.phuong_id
                }
            }))    
            //tạo chuỗi mảng
            // var result="["
            // if(danhsach.length>0){
            //     danhsach.forEach((item, index)=>{
            //         var row="{'pho_id':$1, 'phuong_id':$2}"
            //         row=row.replace("$1",item.pho_id)
            //         row=row.replace("$2",item.phuong_id)
            //         if(index==danhsach.length-1){
            //             result=result.concat(row)
            //         }else{
            //             result=result.concat(row).concat(', ')
            //         }
            //     })
            // }
            // result=result.concat("]")
            // return result
        },
        async selectedRowDacDiem(item){
            this.danhsach_dacdiem.dacdiem_selected=item.dacdiem_id
            this.danhsach_dacdiem.input_dacdiem=item.dacdiem

            //set action 3
            this.setActiveActions(3)

            //lấy danh sách đã gán theo đặc điểm
            try{
                this.loading(true)
                //load phố gán
                this.danhsach_diadanh.pho_gan=[]
                let responsePho=await LocationSpecAPI.lay_danhsach_pho_dagan(this.axios, this.NHOMPHO.PHO, item.dacdiem_id)
                if(responsePho && responsePho.data && responsePho.data.error_code && responsePho.data.error_code == 'BSS-00000000' && responsePho.data.data) {
                    this.danhsach_diadanh.pho_gan=responsePho.data.data
                }else{
                    this.danhsach_diadanh.pho_gan=[]
                }

                //ấp chưa gán
                this.danhsach_diadanh.ap_gan=[]
                let responseAp=await LocationSpecAPI.lay_danhsach_pho_dagan(this.axios, this.NHOMPHO.AP, item.dacdiem_id)
                if(responseAp && responseAp.data && responseAp.data.error_code && responseAp.data.error_code == 'BSS-00000000' && responseAp.data.data) {
                    this.danhsach_diadanh.ap_gan=responseAp.data.data
                }else{
                    this.danhsach_diadanh.ap_gan=[]
                }

                //khu chưa gán
                this.danhsach_diadanh.khu_gan=[]
                let responseKhu=await LocationSpecAPI.lay_danhsach_pho_dagan(this.axios, this.NHOMPHO.KHU, item.dacdiem_id)
                if(responseKhu && responseKhu.data && responseKhu.data.error_code && responseKhu.data.error_code == 'BSS-00000000' && responseKhu.data.data) {
                    this.danhsach_diadanh.khu_gan=responseKhu.data.data
                }else{
                    this.danhsach_diadanh.khu_gan=[]
                }


                //hiển thị lại danh sách chưa gán
                if(this.danhsach_dacdiem.quan_huyen_selected==this.danhsach_diadanh.quan_huyen_selected){
                    if(this.danhsach_dacdiem.phuong_xa_selected!=this.danhsach_diadanh.phuong_xa_selected){
                        this.danhsach_diadanh.phuong_xa_selected=this.danhsach_dacdiem.phuong_xa_selected
                        this.lay_danhsach_chuagan()
                    }
                }

                

            }catch(e){
                this.$toast.error('Không load được danh sách gán')
            }finally{
                this.loading(false)
            }
            
        },
        xoaDacDiem(){
            if(this.danhsach_dacdiem.dacdiem_selected==0){
                this.$toast.error('Không được xóa đặc điểm Không xác định (dacdiem_id = 0)')
                return
            }
            this.$confirm('Bạn thật sự muốn xóa dữ liệu không ?','Thông báo',{
                confirmButtonText: 'Có',
                cancelButtonText: 'Không'
            }).then(() => {
                this.sp_xoa_dacdiem(this.danhsach_dacdiem.dacdiem_selected)

            })
        },
        async sp_xoa_dacdiem(dacdiem_id){
            try{
                this.loading(true)
                let response=await LocationSpecAPI.sp_xoa_dacdiem(this.axios, dacdiem_id)
                this.loading(false)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000') {
                    this.clear()
                    this.$toast.success('Xoá dữ liệu thành công!')
                    await this.hienthi_danhsach_dacdiem()
                }else {
                    this.$toast.error('Xoá dữ liệu không thành công!')
                }
            }catch(e){
                this.loading(false)
                if(e.data&&e.data.message){
                    this.$toast.error(e.data.message)
                }else if(e.response&&e.response.data&&e.response.data.message){
                    this.$toast.error(e.response.data.message)
                }else{
                    this.$toast.error('Xoá dữ liệu không thành công!')
                }
            }
        },
        //1: thêm mới, 0 cập nhật
        async sp_capnhat_dacdiem(data, kieu){
            try{
                this.loading(true)
                let response=await LocationSpecAPI.sp_capnhat_dacdiem(this.axios, data)
                this.loading(false)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000') {
                   this.$toast.success(kieu==1?'Thêm mới Đặc điểm thành công !':'Cập nhật Đặc điểm thành công !')
                   await this.hienthi_danhsach_dacdiem()
                }else {
                    this.$toast.error(kieu==1?'Thêm dữ liệu không thành công!':'Cập nhật dữ liệu không thành công!')
                }
            }catch(e){
                this.loading(false)
                if(e.data&&e.data.message){
                    this.$toast.error(e.data.message)
                }else if(e.response&&e.response.data&&e.response.data.message){
                    this.$toast.error(e.response.data.message)
                }else{
                    this.$toast.error(kieu==1?'Thêm dữ liệu không thành công!':'Cập nhật dữ liệu không thành công!')
                }
                 
            }
        },
        async lay_danhsach_quan(){
            try{
                let response=await LocationSpecAPI.lay_danhsach_quan(this.axios)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                    this.danhsach_dacdiem.quan_huyen=response.data.data
                    this.danhsach_diadanh.quan_huyen=response.data.data
                    if(this.danhsach_dacdiem.quan_huyen.length>0){
                        this.danhsach_dacdiem.quan_huyen_selected=this.danhsach_dacdiem.quan_huyen[0].QUAN_ID
                        this.lay_danhsach_phuong(this.danhsach_dacdiem.quan_huyen_selected,1)
                    }
                    if(this.danhsach_diadanh.quan_huyen.length>0){
                        this.danhsach_diadanh.quan_huyen_selected=this.danhsach_diadanh.quan_huyen[0].QUAN_ID
                        this.lay_danhsach_phuong(this.danhsach_diadanh.quan_huyen_selected,2)
                    }

                }
            }catch(e){
                this.$toast.error('Không load được danh sách quận')
            }
        },
        //1: thông tin đặc điểm 2: thông tin địa danh
        async lay_danhsach_phuong(quan_id, loai){
            try{
                if(loai==1){
                    this.danhsach_dacdiem.phuong_xa=[]
                    this.danhsach_dacdiem.phuong_xa_selected=null
                }else{
                    this.danhsach_diadanh.phuong_xa=[]
                    this.danhsach_diadanh.phuong_xa_selected=null
                }
                let response=await LocationSpecAPI.lay_danhsach_phuong_theo_quan(this.axios, quan_id)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {

                    if(loai==1){
                        this.danhsach_dacdiem.phuong_xa=response.data.data
                        if(this.danhsach_dacdiem.phuong_xa.length>0){
                            this.danhsach_dacdiem.phuong_xa_selected=this.danhsach_dacdiem.phuong_xa[0].PHUONG_ID
                            this.hienthi_danhsach_dacdiem()
                        }
                    }else{
                        this.danhsach_diadanh.phuong_xa=response.data.data
                        if(this.danhsach_diadanh.phuong_xa.length>0){
                            this.danhsach_diadanh.phuong_xa_selected=this.danhsach_diadanh.phuong_xa[0].PHUONG_ID
                            this.lay_danhsach_chuagan()
                        }
                    }

                }else{
                    if(loai==1){
                        this.danhsach_dacdiem.phuong_xa=[]
                         this.danhsach_dacdiem.phuong_xa_selected=null
                    }else{
                        this.danhsach_diadanh.phuong_xa=[]
                        this.danhsach_diadanh.phuong_xa_selected=null
                    }
                }
            }catch(e){
                this.$toast.error('Không load được danh sách phường')
            }
        },
        async hienthi_danhsach_dacdiem(){
            if(this.danhsach_dacdiem.phuong_xa.length==0||this.danhsach_dacdiem.phuong_xa_selected==null){
                return
            }
            //this.danhsach_dacdiem.dacdiem=[]
            this.danhsach_dacdiem.dacdiem=await this.lay_danhsach_dacdiem(this.danhsach_dacdiem.quan_huyen_selected, this.danhsach_dacdiem.phuong_xa_selected)
            if(this.danhsach_dacdiem.dacdiem.length>0){
                if(this.danhsach_dacdiem.dacdiem_selected!=0){
                    //await this.selectedRowDacDiem(this.danhsach_dacdiem.dacdiem[0])
                    const index=this.danhsach_dacdiem.dacdiem.findIndex(x=>x.dacdiem_id==this.danhsach_dacdiem.dacdiem_selected)
                    if(index>-1){
                        this.rowSelectedId=this.danhsach_dacdiem.dacdiem[index].dacdiem_id
                        await this.selectedRowDacDiem(this.danhsach_dacdiem.dacdiem[index])
                    }else{
                        this.rowSelectedId=this.danhsach_dacdiem.dacdiem[0].dacdiem_id
                        await this.selectedRowDacDiem(this.danhsach_dacdiem.dacdiem[0])
                    }
                }else{
                    this.rowSelectedId=this.danhsach_dacdiem.dacdiem[0].dacdiem_id
                    await this.selectedRowDacDiem(this.danhsach_dacdiem.dacdiem[0])
                }
            }else{
                this.setActiveActions(1)
                await this.lay_danhsach_chuagan()
            }
        },
        async lay_danhsach_dacdiem(quan_selected, phuong_selected){
            try{
                this.loading(true)
                let responseDacDiem=await LocationSpecAPI.lay_danhsach_dacdiem(this.axios, quan_selected, phuong_selected)
                this.loading(false)
                if(responseDacDiem && responseDacDiem.data && responseDacDiem.data.error_code && responseDacDiem.data.error_code == 'BSS-00000000' && responseDacDiem.data.data&&responseDacDiem.data.data.length>0) {
                    return responseDacDiem.data.data
                }else{
                    return []
                }
            }catch(e){
                this.loading(false)
                return []
            }
        },
        async lay_danhsach_chuagan(){
            if(this.danhsach_diadanh.phuong_xa.length==0||this.danhsach_diadanh.phuong_xa_selected==null){
                return
            }
            try{
                this.loading(true)
                //load phố chưa gán
                this.danhsach_diadanh.pho_chuagan=[]
                let responsePho=await LocationSpecAPI.lay_danhsach_pho_chuagan(this.axios, this.NHOMPHO.PHO, this.danhsach_dacdiem.dacdiem_selected, this.danhsach_diadanh.quan_huyen_selected, this.danhsach_diadanh.phuong_xa_selected)
                if(responsePho && responsePho.data && responsePho.data.error_code && responsePho.data.error_code == 'BSS-00000000' && responsePho.data.data) {
                    this.danhsach_diadanh.pho_chuagan=responsePho.data.data
                }else{
                    this.danhsach_diadanh.pho_chuagan=[]
                }

                //ấp chưa gán
                this.danhsach_diadanh.ap_chuagan=[]
                let responseAp=await LocationSpecAPI.lay_danhsach_pho_chuagan(this.axios, this.NHOMPHO.AP, this.danhsach_dacdiem.dacdiem_selected, this.danhsach_diadanh.quan_huyen_selected, this.danhsach_diadanh.phuong_xa_selected)
                if(responseAp && responseAp.data && responseAp.data.error_code && responseAp.data.error_code == 'BSS-00000000' && responseAp.data.data) {
                    this.danhsach_diadanh.ap_chuagan=responseAp.data.data
                }else{
                    this.danhsach_diadanh.ap_chuagan=[]
                }

                //khu chưa gán
                this.danhsach_diadanh.khu_chuagan=[]
                let responseKhu=await LocationSpecAPI.lay_danhsach_pho_chuagan(this.axios, this.NHOMPHO.KHU, this.danhsach_dacdiem.dacdiem_selected, this.danhsach_diadanh.quan_huyen_selected, this.danhsach_diadanh.phuong_xa_selected)
                if(responseKhu && responseKhu.data && responseKhu.data.error_code && responseKhu.data.error_code == 'BSS-00000000' && responseKhu.data.data) {
                    this.danhsach_diadanh.khu_chuagan=responseKhu.data.data
                }else{
                    this.danhsach_diadanh.khu_chuagan=[]
                }
            }catch(e){
                this.$toast.error('Không load được danh sách chưa gán')
            }finally{
                this.loading(false)
            }
        }

    },
    
    mounted(){
        this.setActiveActions(-1)
        this.lay_danhsach_quan()
    }
}
</script>
<style>
.select2-selection { overflow: hidden !important; }
.select2-container .select2-selection--single .select2-selection__rendered {
  white-space: normal !important;
  word-break: break-all !important;
}
/* asset css select2 form */
.select2-container {
  width: 100% !important;
}

.select2-container--default .select2-selection--single {
  border-color: #E0E0E0;
  outline: none;
}

.select2-dropdown {
  border: 1px solid #E0E0E0;
}

.select2-container--default .select2-selection--single .select2-selection__arrow b {
  font-family: "FontAwesome";
  border: 0px;
  top: 0px;
  height: 1.428rem;
  bottom: 0px;
  line-height: 1.428rem;
  left: 5px;
  margin: auto;
}
.select2-container--default .select2-selection--single .select2-selection__arrow b:before {
  content: "";
}

.select2-container--default .select2-search--dropdown .select2-search__field {
  border: 0px;
  outline: none;
}
</style>