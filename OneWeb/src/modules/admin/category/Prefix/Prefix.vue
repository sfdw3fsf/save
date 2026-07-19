<template>
    <div class="main-wrapper">
        <breadcrumb :header="header" />
        <ActionTop :actions="actions" @onActionClick="onActionClick" />

        <div class="page-content">
            <div class="row">
                <div class="col-sm-4 col-12">
                    <div class="box-form">
                        <div class="legend-title">Thông tin Prefix</div>
                        <div class="info-row">
                            <div class="key w80">Loại số</div>
                            <div class="value">
                                <div class="select-custom">
                                    <select v-model="loaiso_selected" @change="changeLoaiSo" class="form-control">
                                        <option v-for="item in danhsach_loaiso" :key="item.LOAISO_ID" :value="item.LOAISO_ID">{{item.TEN_LS}}</option>
                                    </select>
                                </div>
                            </div>
                        </div>
                        <div class="info-row">
                            <div class="key w80">Dịch vụ</div>
                            <div class="value">
                                <div class="select-custom">
                                    <select v-model="dichvu_selected" @change="changeDichVu" class="form-control">
                                        <option v-for="item in danhsach_dichvu_filter" :key="item.DICHVUVT_ID" :value="item.DICHVUVT_ID">{{item.TEN_DVVT}}</option>
                                    </select>
                                </div>
                                <!-- <ejs-dropdownlist 
                                    :allowFiltering="true"
                                    v-model="dichvu_selected"
                                    :filtering="onFilteringDichVu"
                                    @change="changeDichVu"
                                    :dataSource="danhsach_dichvu_filter"
                                    :fields="{ text: 'TEN_DVVT', value: 'DICHVUVT_ID' }" /> -->
                            </div>
                        </div>
                        <div class="info-row">
                            <div class="key w80">Tổng đài</div>
                            <div class="value">
                                <ejs-dropdownlist 
                                    :allowFiltering="true"
                                    v-model="tongdai_selected"
                                    :filtering="onFilteringTongDai"
                                    :dataSource="danhsach_tongdai"
                                    :fields="{ text: 'ten_dv', value: 'donvi_id' }" />
                            </div>
                        </div>
                        <div class="info-row">
                            <div class="key w80">Nhà CC</div>
                            <div class="value w20 padt7">
                                <div class="check-action">
                                    <input type="checkbox" v-model="check_nhacungcap" class="check">
                                    <span class="name"></span>
                                </div>
                            </div>
                            <div class="value">
                                <ejs-dropdownlist 
                                    :allowFiltering="true"
                                    v-model="nhacungcap_selected"
                                    :filtering="onFilteringNhaCungCap"
                                    :enabled="check_nhacungcap"
                                    :dataSource="danhsach_nhacungcap"
                                    :fields="{ text: 'tenchuquan', value: 'chuquan_id' }" />
                            </div>
                        </div>
                        <div class="info-row">
                            <div class="key w80">Prefix <k-required-marker/></div>
                            <div class="value">
                                <input type="text" id="inputPrefix" ref="inputPrefix" @blur="outFocusInput('inputPrefix')" autofocus @keypress="isNumber($event)" :disabled="disable_input_prefix" v-model="thongtin_prefix.input_prefix" class="form-control">
                            </div>
                        </div>
                        <div class="info-row">
                            <div class="key w80">Suffix</div>
                            <div class="value">
                                <input type="text" @keypress="isNumber($event)" v-model="thongtin_prefix.input_suffix" class="form-control">
                            </div>
                        </div>
                        <div class="info-row">
                            <div class="key w80">Độ dài <k-required-marker/></div>
                            <div class="value">
                                <input type="text" id="inputDoDai" ref="inputDoDai" @blur="outFocusInput('inputDoDai')" autofocus v-model="thongtin_prefix.input_dodai" @keypress="isNumber($event)" class="form-control">
                            </div>
                        </div>
                        <div class="info-row">
                            <div class="key w80">Ghi chú</div>
                            <div class="value">
                                <textarea name="" v-model="thongtin_prefix.input_ghichu" class="form-control" style="height: 100px;resize: none;"></textarea>
                            </div>
                        </div>
                    </div>
                    <div class="box-form">
                        <div class="legend-title">Thông tin tìm kiếm</div>
                        
                        <div class="info-row">
                            <div class="key w80">
                                <div class="check-action">
                                    <input type="checkbox" v-model="thongtin_timkiem.check_prefix" @change="changeCheckTimKiem" class="check">
                                    <span class="name">Prefix</span>
                                </div>
                            </div>
                            <div class="value">
                                <input type="text" v-model="thongtin_timkiem.input_prefix" @keypress.enter="onEnterInputPrefix" :disabled="!thongtin_timkiem.check_prefix" class="form-control">
                            </div>
                        </div>
                       
                        
                    </div>
                </div>
                <div class="col-sm-8 col-12">
                    <div class="box-move-select-table">
                        <div class="box-col box-form">
                            <div class="legend-title">Loại hình đã gán</div>
                            <KTableV3 :columns="columnsGan"
                                :dataSources="dataSourcesGan"
                                @dataCheckeds="dataCheckedsGan"
                                :allowCheckBox="true"
                                id="loaitb_id"
                                :isShowPagination="false"
                                :allowFilter="true"
                                tableHeight="470px"/>
                        </div>
                        <div class="actions">
                            <button class="btn" @click="boGan">
                                <span class="fa fa-angle-right"></span>
                            </button>
                            <button class="btn" @click="boGanAll">
                                <span class="fa fa-angle-double-right"></span>
                            </button>
                            <button class="btn" @click="gan">
                                <span class="fa fa-angle-left"></span>
                            </button>
                            <button class="btn" @click="ganAll">
                                <span class="fa fa-angle-double-left"></span>
                            </button>
                        </div>
                        <div class="box-col box-form">
                            <div class="legend-title">Loại hình chưa gán</div>
                            <KTableV3 :columns="columnsChuaGan"
                                :dataSources="dataSourcesChuaGan"
                                @dataCheckeds="dataCheckedsChuaGan"
                                id="loaitb_id"
                                :allowCheckBox="true"
                                :isShowPagination="false"
                                :allowFilter="true"
                                tableHeight="470px"/>
                        </div>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col-sm-8 col-12">
                    <div class="box-form">
                        <div class="legend-title">Danh sách Prefix</div>
                        <KTableV3 :columns="columnsDsPrefix"
                            :dataSources="dataSourcesDsPrefix"
                            id="prefix"
                            :rowSelectedId="rowSelectedId"
                            @onSelectedRow="onSelectedRow"
                            :allowFilter="true"
                            tableHeight="230px"/>
                    </div>
                </div>
                <div class="col-sm-4 col-12">
                    <div class="box-form">
                        <div class="legend-title">Danh sách số</div>
                        <KTableV3 :columns="columnsDsSo"
                            :dataSources="dataSourcesDsSo"
                            @dataCheckeds="dataCheckedsDsSo"
                            :allowCheckBox="true"
                            :allowFilter="true"
                            id="somay"
                            tableHeight="200px"
                            @contextmenu.native="handlerRightClickDsSo"
                            />
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
import KTableV3 from '@/components/kylq_components/mirana/KTableV3.vue'
import ActionTop from '@/components/kylq_components/ActionTop.vue'
import KRequiredMarker from '@/components/kylq_components/KRequiredMarker.vue'
import PrefixAPI from './PrefixAPI'
import { DataManager,Query,ODataV4Adaptor,Predicate } from "@syncfusion/ej2-data"
export default {
    name:'Prefix',
    components:{
        Footer,
        breadcrumb,
        ActionTop,
        KTableV3,
        KRequiredMarker
    },
    computed:{
        disable_input_prefix(){
            return this.actions[this.actions.findIndex(x=>x.id=='nhapmoi')].active
        }
    },
    data(){
        return{
            //Trang chủ/Dữ liệu/Phát triển thuê bao/Kho số/Prefix
            header: {
                title: "Cập nhật đầu số",
                list: [
                    { 
                        name: "Dữ liệu", 
                        link: { name: "Ui.cards" },
                        active: false
                    },
                    { 
                        name: "Phát triển thuê bao", 
                        link: { name: "Ui.cards" },
                        active: false
                    },
                    { 
                        name: "Kho số", 
                        link: { name: "Ui.cards" },
                        active: false
                    },
                    {
                        name: "Prefix",
                        link: { name: "Ui.buttons" },
                        active: true
                    },
                ],
            },
            actions:[
                {
                    id:'nhapmoi',
                    name:'Nhập mới',
                    active: true,
                    icon_class:'one-file-plus'
                },
                {
                    id:'ghilai',
                    name:'Ghi lại',
                    active: true,
                    icon_class:'one-save'
                },
                {
                    id:'huy',
                    name:'Huỷ',
                    active: true,
                    icon_class:'nc-icon-glyph ui-1_circle-remove'
                },
                {
                    id:'xoa',
                    name:'Xoá',
                    active: true,
                    icon_class:'one-trash'
                }
            ],
            columnsGan:[
                {
                    field:'loaihinh_tb',
                    label:'Loại hình',
                    allowFilter:true,
                    allowSorting:false
                }
            ],
            dataSourcesGan:[],
            columnsChuaGan:[
                {
                    field:'loaihinh_tb',
                    label:'Loại hình',
                    allowFilter:true,
                    allowSorting:false
                }
            ],
            dataSourcesChuaGan:[],
            columnsDsSo:[
                {
                    field:'somay',
                    label:'Số máy',
                    allowFilter:true,
                    allowSorting:false
                },
                {
                    field:'ten_ttso',
                    label:'Trạng thái số',
                    allowFilter:true,
                    allowSorting:false
                }
            ],
            dataSourcesDsSo:[],
            columnsDsPrefix:[
                {
                    field:'prefix',
                    label:'PREFIX',
                    allowFilter:true,
                    allowSorting:false
                },
                {
                    field:'suffix',
                    label:'SUFFIX',
                    allowFilter:true,
                    allowSorting:false
                },
                {
                    field:'length',
                    label:'Độ dài',
                    allowFilter:true,
                    allowSorting:false
                },
                {
                    field:'ten_ls',
                    label:'Loại số',
                    allowFilter:true,
                    allowSorting:false
                },
                {
                    field:'ten_dv',
                    label:'Tổng đài',
                    allowFilter:true,
                    allowSorting:false
                },
                {
                    field:'ghichu',
                    label:'Ghi chú',
                    allowFilter:true,
                    allowSorting:false
                },
                {
                    field:'tenchuquan',
                    label:'Nhà CC',
                    allowFilter:true,
                    allowSorting:false
                },
            ],
            dataSourcesDsPrefix:[],
            danhsach_loaiso:[],
            loaiso_selected:null,
            danhsach_tongdai:[],
            tongdai_selected:null,
            danhsach_nhacungcap:[],
            nhacungcap_selected:null,
            danhsach_dichvu:[],
            danhsach_dichvu_filter:[],
            dichvu_selected:null,
            check_nhacungcap:false,
            thongtin_prefix:{
                input_prefix:'',
                input_suffix:'',
                input_dodai:'',
                input_ghichu:''
            },
            thongtin_timkiem:{
                check_prefix:false,
                input_prefix:''
            },
            selectedRowPrefix:null,
            dataCheckGan:[],
            dataCheckChuaGan:[],
            isFirstChangeLoaiSo:true,
            prefixAddUpdate:'',
            dataCheckKhoSo:[],
            rowSelectedId:-1

        }
    },
    methods:{
        onActionClick(action){
            if(action.id=='nhapmoi'){
                this.setActiveActions(1)
            }else if(action.id=='ghilai'){
                this.ghiDuLieu()
            }else if(action.id=='huy'){
                //Huỷ nếu đang có row focus table thì init lại row đấy
                if(this.selectedRowPrefix!=null){
                    this.onSelectedRow(this.selectedRowPrefix)
                }else {
                    this.setActiveActions(2)
                }
            }else if(action.id=='xoa'){
                if(this.selectedRowPrefix!=null){
                    this.$confirm('Bạn thật sự muốn xóa dữ liệu không ?','Thông báo',{
                        confirmButtonText: 'Có',
                        cancelButtonText: 'Không'
                    }).then(() => {
                        //thực thi
                        this.sp_prefix_delete(this.selectedRowPrefix.prefix, this.tongdai_selected!=null?this.tongdai_selected:0)
                    })
                }else{
                    this.$toast.warning('Hãy chọn Prefix')
                }
            }
        },
        async setActiveActions(kieu){
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
                await this.clear()
                this.$refs.inputPrefix.focus()
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
        async clear(){
            this.thongtin_prefix.input_prefix=''
            this.thongtin_prefix.input_ghichu=''
            this.thongtin_prefix.input_suffix=''
            this.thongtin_prefix.input_dodai=''

            //clear LHTB
            this.dataSourcesChuaGan=[]
            this.dataSourcesGan=[]
            await this.hienthi_lhtb('')
        },
        dataCheckedsGan(data){
            this.dataCheckGan=data
        },
        outFocusInput(inputId){
            let element=document.getElementById(inputId)
            if(element){
                element.classList.remove('input_batbuoc')
            }
        },
        dataCheckedsChuaGan(data){
            this.dataCheckChuaGan=data
        },

        dataCheckedsDsSo(data){
            this.dataCheckKhoSo=data
        },
        boGan(){
            if(this.dataCheckGan.length==0){
                return
            }
            this.dataCheckGan.forEach((item)=>{
                this.dataSourcesChuaGan.push(item)
                const index=this.dataSourcesGan.findIndex(x=>x.loaitb_id==item.loaitb_id)
                if(index>-1){
                    this.dataSourcesGan.splice(index,1)
                }
            })
            this.dataCheckGan=[]
            this.dataCheckChuaGan=[]
        },
        boGanAll(){
            if(this.dataSourcesGan.length==0){
                return
            }
            this.dataSourcesChuaGan=this.dataSourcesChuaGan.concat(this.dataSourcesGan)
            this.dataSourcesGan=[]

            this.dataCheckGan=[]
            this.dataCheckChuaGan=[]
        },
        gan(){
            if(this.dataCheckChuaGan.length==0){
                return
            }
            this.dataCheckChuaGan.forEach((item)=>{
                this.dataSourcesGan.push(item)
                const index=this.dataSourcesChuaGan.findIndex(x=>x.loaitb_id==item.loaitb_id)
                if(index>-1){
                    this.dataSourcesChuaGan.splice(index,1)
                }
            })
            this.dataCheckGan=[]
            this.dataCheckChuaGan=[]
        },
        ganAll(){
            if(this.dataSourcesChuaGan.length==0){
                return
            }
            this.dataSourcesGan=this.dataSourcesGan.concat(this.dataSourcesChuaGan)
            this.dataSourcesChuaGan=[]

            this.dataCheckGan=[]
            this.dataCheckChuaGan=[]
        },
        isNumber(evt) {
            evt = (evt) ? evt : window.event;
            var charCode = (evt.which) ? evt.which : evt.keyCode;
            if ((charCode > 31 && (charCode < 48 || charCode > 57))) {
                evt.preventDefault()
            } else {
                return true
            }
        },
        handlerRightClickDsSo(){
            if(this.dataCheckKhoSo.length>0){
                //Show confim
                this.$confirm('Bạn có chắc chắn muốn xoá không?','Thông báo',{
                    confirmButtonText: 'Đồng ý',
                    cancelButtonText: 'Huỷ'
                }).then(() => {
                    //thực thi
                    let dsSoCheck=this.getDsSoMay()
                    console.log('dsSoCheck', dsSoCheck)
                    this.sp_prefix_khoso_delete(this.loaiso_selected, this.dichvu_selected, dsSoCheck)

                })
            }else{
                // this.$alert('Bạn chưa chọn số máy để xóa hoặc số máy bạn chọn đang được tạm cấp hoặc đã được sử dụng. Hãy thử lại!', 'Thông báo', {
                //     dangerouslyUseHTMLString: true,
                //     confirmButtonText: 'OK',
                //     type: 'warning'
                // })
                this.$toast.warning('Bạn chưa chọn số máy để xóa hoặc số máy bạn chọn đang được tạm cấp hoặc đã được sử dụng. Hãy thử lại!')
            }
        },
        async changeCheckTimKiem(){
            this.prefixAddUpdate=''
            this.thongtin_timkiem.input_prefix=''
            await this.hienthi_danhsach_prefix()
        },
        //chưa có api cho khúc khảo sát dưới đối với tính năng tìm kiếm prefix
        async onEnterInputPrefix(){
            this.prefixAddUpdate=''
            if(this.thongtin_timkiem.input_prefix.trim()!=''){
                await this.hienthi_danhsach_timkiem_prefix()
            }else{
                await this.hienthi_danhsach_prefix()
            }
            
        },
        ghiDuLieu(){
            //button nhập mới active thì update, ngược lại thêm mới
            let is_insert=this.actions[this.actions.findIndex(x=>x.id=='nhapmoi')].active==true?0:1
            //kiểm tra dữ liệu
            if(!this.kiemtra_dulieu()){
                return
            }
            //tạo dữ liệu
            //ds_lhtb, prefix, is_insert
            let ds_lhtb=this.getDS_LHTB()
            let prefix=this.getDataPrefix()

            this.prefixAddUpdate=this.thongtin_prefix.input_prefix.trim()

            this.sp_prefix_update(ds_lhtb, prefix, is_insert)
        },
        getDS_LHTB(){
            //"[{\"PREFIX\": \"1234\", \"LOAITB_ID\": 1}]"
            let lhtb="["
            this.dataSourcesGan.forEach((item, index)=>{
                var row="{\"PREFIX\": \"#1\", \"LOAITB_ID\": #2}"
                row=row.replace("#1",this.thongtin_prefix.input_prefix.trim())
                row=row.replace("#2", item.loaitb_id)
                if(index==this.dataSourcesGan.length-1){
                    lhtb=lhtb.concat(row)
                }else{
                    lhtb=lhtb.concat(row).concat(', ')
                } 
            })
            lhtb=lhtb.concat("]")
            return lhtb
        },
        getDataPrefix(){
            //"[{\"PREFIX\": \"1234\", \"LOAISO_ID\": 1, \"DONVI_ID\": 399, \"LENGTH\": 7, \"SUFFIX\": null, GHICHU: \"1234\"}]"
            let prefix="[{\"PREFIX\": \"#1\", \"LOAISO_ID\": #2, \"DONVI_ID\": #3, \"LENGTH\": #4, \"SUFFIX\": \"#5\", \"GHICHU\": \"#6\", \"NHACC_ID\": #7}]"
            prefix=prefix.replace("#1", this.thongtin_prefix.input_prefix.trim())
            prefix=prefix.replace("#2", this.loaiso_selected)
            prefix=prefix.replace("#3", this.tongdai_selected)
            prefix=prefix.replace("#4", Number(this.thongtin_prefix.input_dodai))
            prefix=prefix.replace("#5", this.thongtin_prefix.input_suffix.trim())
            prefix=prefix.replace("#6", this.thongtin_prefix.input_ghichu.trim())
            if(this.check_nhacungcap){
                prefix=prefix.replace("#7", this.nhacungcap_selected)
            }else{
                prefix=prefix.replace("#7", null)
            }
            return prefix
        },
        kiemtra_dulieu(){
            if(this.thongtin_prefix.input_prefix.trim()==''){
                this.$toast.error('Prefix không được để trống !')
                
                // this.$nextTick(()=>{
                //     this.$refs.inputPrefix.focus()
                // })
                document.getElementById('inputPrefix').classList.add('input_batbuoc')
                this.$refs.inputPrefix.focus()
                return false
            }
            if(this.thongtin_prefix.input_dodai.trim()==''){
                this.$toast.error('Độ dài không được để trống !')
                // this.$nextTick(()=>{
                //     this.$refs.inputDoDai.focus()
                // })
                document.getElementById('inputDoDai').classList.add('input_batbuoc')
                this.$refs.inputDoDai.focus()
                
                return false
            }

            if((this.thongtin_prefix.input_prefix.trim().length+this.thongtin_prefix.input_suffix.trim().length)>=parseInt(this.thongtin_prefix.input_dodai.trim())){
                this.$toast.error('Độ dài không được nhỏ hơn hoặc bằng tổng Prefix + Suffix !')
                // this.$nextTick(()=>{
                //     this.$refs.inputDoDai.focus()
                // })
                document.getElementById('inputDoDai').classList.add('input_batbuoc')
                this.$refs.inputDoDai.focus()
                return false
            }

            if(this.dataSourcesGan.length==0){
                this.$toast.error('Bạn phải chọn Loại hình thuê bao cho Prefix !')
                return false
            }
            if(this.tongdai_selected==null){
                this.$toast.error('Bạn chưa chọn tổng đài.')
                return false
            }
            return true
            
        },
        getDsSoMay(){
            //"[{\"SOMAY\": \"3504498\"}]"
            let somay="["
            this.dataCheckKhoSo.forEach((item, index)=>{
                var row="{\"SOMAY\": \"#1\"}"
                row=row.replace("#1", item.somay)
                if(index==this.dataCheckKhoSo.length-1){
                    somay=somay.concat(row)
                }else{
                    somay=somay.concat(row).concat(', ')
                } 
            })
            somay=somay.concat("]")
            return somay
        },
        //focus danh sách prefix
        async onSelectedRow(item){
            this.selectedRowPrefix=item
            //Hiển thị thông tin
            this.thongtin_prefix.input_prefix=item.prefix!=null?item.prefix.toString():''
            this.thongtin_prefix.input_ghichu=item.ghichu!=null?item.ghichu:''
            this.thongtin_prefix.input_suffix=item.suffix!=null?item.suffix.toString():''
            this.thongtin_prefix.input_dodai=item.length!=null?item.length.toString():'0'

            this.tongdai_selected=item.donvi_id!=null?item.donvi_id.toString():null

            if(item.nhacc_id!=null&&item.nhacc_id.toString()!=''){
                this.check_nhacungcap=true
                this.nhacungcap_selected=parseInt(item.nhacc_id)
            }else{
                this.check_nhacungcap=false
                this.nhacungcap_selected=0
            }
            
            //Hiển thị loại hình TB
            await this.hienthi_lhtb(item.prefix)
            //Hiển thị kho số
            await this.hienthi_ds_so(item.prefix)

            this.setActiveActions(3)
        },
        async changeLoaiSo(){
            // if(this.isFirstChangeLoaiSo){
            //     this.isFirstChangeLoaiSo=false
            //     return
            // }
            this.prefixAddUpdate=''
            if(this.loaiso_selected==null){
                return
            }
            if(this.loaiso_selected==1){
                let arrayDV=[1,10,11]
                this.danhsach_dichvu_filter=this.danhsach_dichvu.filter(x=>arrayDV.includes(x.DICHVUVT_ID))
                if(this.danhsach_dichvu_filter.length>0){
                    this.dichvu_selected=this.danhsach_dichvu_filter[0].DICHVUVT_ID

                    if(this.isFirstChangeLoaiSo){
                        this.isFirstChangeLoaiSo=false
                        return
                    }
                    await this.hienthi_danhsach_prefix()
                    //Vì >0 đã call hienthi_lhtb trong onSelectedRow khi lấy hienthi_danhsach_prefix
                    //BỎ vì hienthi_danhsach_prefix nếu dataSourcesDsPrefix=0 thì đã call setAction(1) dã gọi hàm rùi
                    // if(this.dataSourcesDsPrefix.length==0){
                    //     await this.hienthi_lhtb('')
                    // }
                }
            }else if(this.loaiso_selected==3){
                let arrayDV=[4,7,8,9,12,13,14,15,16]
                this.danhsach_dichvu_filter=this.danhsach_dichvu.filter(x=>arrayDV.includes(x.DICHVUVT_ID))
                if(this.danhsach_dichvu_filter.length>0){
                    this.dichvu_selected=this.danhsach_dichvu_filter[0].DICHVUVT_ID

                    if(this.isFirstChangeLoaiSo){
                        this.isFirstChangeLoaiSo=false
                        return
                    }
                    await this.hienthi_danhsach_prefix()
                    //Vì >0 đã call hienthi_lhtb trong onSelectedRow khi lấy hienthi_danhsach_prefix
                    //BỎ vì hienthi_danhsach_prefix nếu dataSourcesDsPrefix=0 thì đã call setAction(1) dã gọi hàm rùi
                    // if(this.dataSourcesDsPrefix.length==0){
                    //     await this.hienthi_lhtb('')
                    // }
                }
            }else {
                this.danhsach_dichvu_filter=[]
                this.dichvu_selected=null
            }
        },
        async changeDichVu(){
            //    HienThi_LHTB(vprefix);
            //         ht_ds();
            this.prefixAddUpdate=''
            await this.hienthi_danhsach_prefix()
            //Vì >0 đã call hienthi_lhtb trong onSelectedRow khi lấy hienthi_danhsach_prefix
            if(this.dataSourcesDsPrefix.length==0){
                await this.hienthi_lhtb('')
            }
        },
        onFilteringTongDai(e) {
            var query = new Query();
            let predicate;  
            //frame the query based on search string with filter type.
            if (e.text.trim() !== ''){
                predicate  = new Predicate('ten_dv', 'contains', e.text.trim(), true);
                //predicate = predicate.or('TEN_KHO', 'contains', e.text);
                query.where(predicate)
            }
            //pass the filter data source, filter query to updateData method.
            e.updateData(this.danhsach_tongdai, query);
        },
        onFilteringNhaCungCap(e) {
            var query = new Query();
            let predicate;  
            //frame the query based on search string with filter type.
            if (e.text.trim() !== ''){
                predicate  = new Predicate('tenchuquan', 'contains', e.text.trim(), true);
                //predicate = predicate.or('TEN_KHO', 'contains', e.text);
                query.where(predicate)
            }
            //pass the filter data source, filter query to updateData method.
            e.updateData(this.danhsach_nhacungcap, query);
        },
        onFilteringDichVu(e) {
            var query = new Query();
            let predicate;  
            //frame the query based on search string with filter type.
            if (e.text.trim() !== ''){
                predicate  = new Predicate('TEN_DVVT', 'contains', e.text.trim(), true);
                //predicate = predicate.or('TEN_KHO', 'contains', e.text);
                query.where(predicate)
            }
            //pass the filter data source, filter query to updateData method.
            e.updateData(this.danhsach_dichvu_filter, query);
        },  
        async lay_danhsach_loai_so(){
            try{
                this.loading(true)
                this.danhsach_loaiso=[]
                let response=await PrefixAPI.lay_danhsach_loai_so(this.axios)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                    this.danhsach_loaiso=response.data.data.filter(x=>x.LOAISO_ID==1||x.LOAISO_ID==3)
                    if(this.danhsach_loaiso.length>0){
                        //chọn mặc định số thường
                        this.loaiso_selected=1
                    }
                }else{
                    this.danhsach_loaiso=[]
                }
            }catch(e){
                this.$toast.error('Không load được danh sách loại số')
            }finally{
                this.loading(false)
            }
        },
        async lay_danhsach_tongdai(){
            try{
                this.loading(true)
                this.danhsach_tongdai=[]
                let response=await PrefixAPI.lay_danhsach_tongdai(this.axios)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                    this.danhsach_tongdai=response.data.data
                    if(this.danhsach_tongdai.length>0){
                        this.tongdai_selected=this.danhsach_tongdai[0].donvi_id
                    }
                }else{
                    this.danhsach_tongdai=[]
                }
            }catch(e){
                this.$toast.error('Không load được danh sách tổng đài')
            }finally{
                this.loading(false)
            }
        },
        async lay_danhsach_nhacungcap(){
            try{
                this.loading(true)
                this.danhsach_nhacungcap=[]
                let response=await PrefixAPI.lay_danhsach_nhacungcap(this.axios)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                    this.danhsach_nhacungcap=response.data.data
                    if(this.danhsach_nhacungcap.length>0){
                        this.nhacungcap_selected=this.danhsach_nhacungcap[0].chuquan_id
                    }
                }else{
                    this.danhsach_nhacungcap=[]
                }
            }catch(e){
                this.$toast.error('Không load được danh sách nhà cung cấp')
            }finally{
                this.loading(false)
            }
        },
        async lay_danhsach_dichvu(){
            try{
                this.loading(true)
                this.danhsach_dichvu=[]
                this.danhsach_dichvu_filter=[]
                let response=await PrefixAPI.lay_danhsach_dichvu(this.axios)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                    this.danhsach_dichvu=response.data.data
                }else{
                    this.danhsach_dichvu=[]
                }
            }catch(e){
                this.$toast.error('Không load được danh sách dịch vụ')
            }finally{
                this.loading(false)
            }
        },
        async lay_danhsach_prefix(loaiso_id, dichvuvt_id){
            try{
                this.loading(true)
                let response=await PrefixAPI.lay_danhsach_prefix(this.axios, loaiso_id, dichvuvt_id)
                this.loading(false)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                    return response.data.data
                }else{
                    return []
                }
            }catch(e){
                return []
            }
        },
        async sp_prefix_ds_loaihinh(dichvuvt_id, prefix, kieu){
            try{
                this.loading(true)
                if(kieu==1){
                    this.dataSourcesGan=[]
                }else{
                    this.dataSourcesChuaGan=[]
                }
                let response=await PrefixAPI.sp_prefix_ds_loaihinh(this.axios, dichvuvt_id, prefix, kieu)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                    if(kieu==1){
                        this.dataSourcesGan=response.data.data
                    }else{
                        this.dataSourcesChuaGan=response.data.data
                    }
                }else{
                    if(kieu==1){
                        this.dataSourcesGan=[]
                    }else{
                        this.dataSourcesChuaGan=[]
                    }
                }
            }catch(e){
                this.$toast.error('Không load được danh sách loại hình')
            }finally{
                this.loading(false)
            }
        },
        async sp_prefix_ds_khoso(loaiso_id, dichvuvt_id, prefix){
            try{
                this.loading(true)
                this.dataSourcesDsSo=[]
                let response=await PrefixAPI.sp_prefix_ds_khoso(this.axios, loaiso_id, dichvuvt_id, prefix)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                    this.dataSourcesDsSo=response.data.data
                }else{
                    this.dataSourcesDsSo=[]
                }
            }catch(e){
                this.$toast.error('Không load được danh sách kho số')
            }finally{
                this.loading(false)
            }
        },
        async hienthi_lhtb(prefix){
            let dichvu_id=this.dichvu_selected!=null?this.dichvu_selected:0
            //loại hình chưa gán
            await this.sp_prefix_ds_loaihinh(dichvu_id, prefix, 0)
            //loại hình gán
            await this.sp_prefix_ds_loaihinh(dichvu_id, prefix, 1)
        },
        async hienthi_ds_so(prefix){
            let dichvu_id=this.dichvu_selected!=null?this.dichvu_selected:0
            await this.sp_prefix_ds_khoso(this.loaiso_selected, dichvu_id, prefix)
        },
        async hienthi_danhsach_prefix(){
            let dichvu_id=this.dichvu_selected!=null?this.dichvu_selected:0
            this.selectedRowPrefix=null
            this.dataSourcesDsPrefix=await this.lay_danhsach_prefix(this.loaiso_selected, dichvu_id)
            if(this.dataSourcesDsPrefix.length>0){
                //focus row đầu
                if(this.prefixAddUpdate!=''){
                    const index=this.dataSourcesDsPrefix.findIndex(x=>x.prefix==this.prefixAddUpdate)
                    this.prefixAddUpdate=''
                    if(index>-1){
                        this.rowSelectedId=this.dataSourcesDsPrefix[index].prefix
                        this.onSelectedRow(this.dataSourcesDsPrefix[index])
                    }else{
                        this.rowSelectedId=this.dataSourcesDsPrefix[0].prefix
                        this.onSelectedRow(this.dataSourcesDsPrefix[0])
                    }
                }else{
                    this.rowSelectedId=this.dataSourcesDsPrefix[0].prefix
                    this.onSelectedRow(this.dataSourcesDsPrefix[0])
                }
                
            }else{
                this.dataSourcesDsPrefix=[]
                this.setActiveActions(1)
            }
        },
        async hienthi_danhsach_timkiem_prefix(){
            await this.sp_tk_prefix(this.thongtin_timkiem.input_prefix.trim())
            if(this.dataSourcesDsPrefix.length>0){
                //focus row đầu
                if(this.prefixAddUpdate!=''){
                    const index=this.dataSourcesDsPrefix.findIndex(x=>x.prefix==this.prefixAddUpdate)
                    this.prefixAddUpdate=''
                    if(index>-1){
                        this.rowSelectedId=this.dataSourcesDsPrefix[index].prefix
                        this.onSelectedRow(this.dataSourcesDsPrefix[index])
                    }else{
                        this.rowSelectedId=this.dataSourcesDsPrefix[0].prefix
                        this.onSelectedRow(this.dataSourcesDsPrefix[0])
                    }
                }else{
                    this.rowSelectedId=this.dataSourcesDsPrefix[0].prefix
                    this.onSelectedRow(this.dataSourcesDsPrefix[0])
                }
                
            }else{
                this.dataSourcesDsPrefix=[]
                this.setActiveActions(1)
            }
        },
        async sp_prefix_delete(prefix, donvi_id){
            try{
                this.loading(true)
                let response = await PrefixAPI.sp_prefix_delete(this.axios, prefix, donvi_id)
                this.loading(false)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                    if(response.data.data=='1'){
                        this.$toast.success('Xóa prefix trên dữ liệu viễn thông tỉnh thành công !')
                        this.selectedRowPrefix=null
                        this.prefixAddUpdate=''
                        //Hiển thị lại dữ liệu, nếu là chế độ search hoặc chế độ thường
                        if(this.thongtin_timkiem.check_prefix&&this.thongtin_timkiem.input_prefix.trim()!=''){
                            await this.hienthi_danhsach_timkiem_prefix()
                        }else{
                            await this.hienthi_danhsach_prefix()
                        }
                    }else{
                        this.$toast.error(response.data.data)
                    }
                }else{
                     if(response&&response.data&&response.data.data){
                        this.$toast.error(response.data.data)
                    }else if(response&&response.data&&response.data.message){
                        this.$toast.error(response.data.message)
                    }else{
                        this.$toast.error('Đã xảy ra lỗi, xoá dữ liệu không thành công')
                    }
                }
            }catch(e){
                this.loading(false)
                if(e.data&&e.data.message){
                    this.$toast.error(e.data.message)
                }else if(e.response&&e.response.data&&e.response.data.message){
                    this.$toast.error(e.response.data.message)
                }else{
                    this.$toast.error('Đã xảy ra lỗi, xoá dữ liệu không thành công')
                }
            }
        },
        async sp_prefix_update(ds_lhtb, prefix, is_insert){
            try{
                this.loading(true)
                let response = await PrefixAPI.sp_prefix_update(this.axios, ds_lhtb, prefix, is_insert)
                this.loading(false)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                    if(response.data.data=='1'){
                        this.$toast.success(is_insert==1?'Thêm mới prefix vào cơ sở dữ liệu tỉnh thành công !':'Cập nhật prefix vào cơ sở dữ liệu tỉnh thành công !')
                        this.selectedRowPrefix=null
                        //Hiển thị lại dữ liệu, nếu là chế độ search hoặc chế độ thường
                        if(this.thongtin_timkiem.check_prefix&&this.thongtin_timkiem.input_prefix.trim()!=''){
                            await this.hienthi_danhsach_timkiem_prefix()
                        }else{
                            await this.hienthi_danhsach_prefix()
                        }
                    }else{
                        this.$toast.error(response.data.data)
                    }
                }else{
                    if(response&&response.data&&response.data.data){
                        this.$toast.error(response.data.data)
                    }else if(response&&response.data&&response.data.message){
                        this.$toast.error(response.data.message)
                    }else{
                        this.$toast.error(is_insert==1?'Đã xảy ra lỗi, thêm mới prefix dữ liệu không thành công':'Đã xảy ra lỗi, cập nhật prefix dữ liệu không thành công')
                    }
                }
            }catch(e){
                this.loading(false)
                if(e.data&&e.data.message){
                    this.$toast.error(e.data.message)
                }else if(e.response&&e.response.data&&e.response.data.message){
                    this.$toast.error(e.response.data.message)
                }else{
                    this.$toast.error(is_insert==1?'Đã xảy ra lỗi, thêm mới prefix dữ liệu không thành công':'Đã xảy ra lỗi, cập nhật prefix dữ liệu không thành công')
                }
            }
        },
        async sp_tk_prefix(prefix){
            try{
                this.loading(true)
                let response=await PrefixAPI.sp_tk_prefix(this.axios, prefix)
                this.loading(false)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                    this.dataSourcesDsPrefix=response.data.data
                }else{
                    this.dataSourcesDsPrefix=[]
                    this.$toast.error('Không tìm thấy dữ liệu!')
                }
            }catch(e){
                this.loading(false)
                this.dataSourcesDsPrefix=[]
                this.$toast.error('Đã xảy ra lỗi, tìm kiếm không thành công!')
            }
        },
        async sp_prefix_khoso_delete(loaiso_id, dichvuvt_id, js_data){
            try{
                this.loading(true)
                let response = await PrefixAPI.sp_prefix_khoso_delete(this.axios, loaiso_id, dichvuvt_id, js_data)
                this.loading(false)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000') {
                    this.dataCheckKhoSo=[]
                    this.hienthi_ds_so(this.selectedRowPrefix.prefix)
                }else{
                    if(response&&response.data&&response.data.message){
                        this.$toast.error(response.data.message)
                    }else{
                        this.$toast.error('Đã xảy ra lỗi, xoá dữ liệu không thành công')
                    }
                }
            }catch(e){
                this.loading(false)
                if(e.data&&e.data.message){
                    this.$toast.error(e.data.message)
                }else if(e.response&&e.response.data&&e.response.data.message){
                    this.$toast.error(e.response.data.message)
                }else{
                    this.$toast.error('Đã xảy ra lỗi, xoá dữ liệu không thành công')
                }
            }
        },
        async initDuLieu(){
            await this.lay_danhsach_loai_so()
            await this.lay_danhsach_tongdai()
            await this.lay_danhsach_nhacungcap()
            await this.lay_danhsach_dichvu()
            //init ds dich vụ theo loại số
            if(this.danhsach_dichvu.length>0){
                await this.changeLoaiSo()
            }
            await this.hienthi_danhsach_prefix()
        },
    },
    mounted(){
        document.addEventListener('contextmenu', event => event.preventDefault())
        setTimeout(()=>{
            this.initDuLieu()
        },500)
    }
}
</script>
<style>
.input_batbuoc:focus {
    border-color:red;
    background-color: lightcoral;
    box-shadow: none;
}
</style>