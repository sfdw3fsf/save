<template>
    <div class="main-wrapper">

        <breadcrumb :header="header"/>
        <ActionTop :actions="actions" @onActionClick="onActionClick"/>

        <div class="page-content">
            <div class="row">
                <div class="col-sm-3 col-12">
                    <div class="box-form">
                        <div class="legend-title">Danh sách vật tư</div>
                        <div class="info-row">
                            <div class="value padl0">
                                <ejs-dropdownlist 
                                    :allowFiltering="true"
                                    v-model="vattu_cha_selected"
                                    @change="changeVatTu"
                                    :dataSource="danhsach_vattu_cha"
                                    :fields="{ text: 'ten_vt', value: 'vattu_id' }" />
                            </div>

                        </div>
                        <div class="tree-plus ovauto" style="height: 640px;">
                            <ejs-treeview  id='treeview'  ref="treeObj" 
                            :fields='fields' 
                            @nodeSelected="tvVatTu_FocusedNodeChanged" 
                            :allowMultiSelection='true'
                            :selectedNodes="selectedNodes"
                            :expandedNodes="expandedNodes"/>
                        </div>
                    </div>
                </div>
                <div class="col-sm-9 col-12">
                    <div class="box-form">
                        <div class="row">
                            <div class="col-sm-5 col-12">
                                <div class="info-row">
                                    <div class="key w100">Loại điều khiển</div>
                                    <div class="value">
                                        <ejs-dropdownlist 
                                            :allowFiltering="true"
                                            v-model="loai_dieukhien_selected"
                                            :dataSource="danhsach_loai_dieukhien"
                                            :fields="{ text: 'ten_loai', value: 'loaidk_id' }" />
                                    </div>
                                </div>
                            </div>
                            <div class="col-sm-7 col-12">
                                <div class="info-row">
                                    <div class="key w100">Thứ tự</div>
                                    <div class="value">
                                        <input type="text" :value="input_thutu" @change="e=>input_thutu=e.target.value"  @keypress="isNumber($event)" class="form-control">
                                    </div>
                                    <div class="value w30 nowrap">
                                        <div class="list-checks mart7">
                                            <div class="item">
                                                <div class="check-action">
                                                    <input type="checkbox" v-model="check_hienthi" class="check">
                                                    <span class="name">Hiển thị</span>
                                                </div>
                                            </div>
                                            <div class="item">
                                                <div class="check-action">
                                                    <input type="checkbox" v-model="check_nhomthietbi" class="check">
                                                    <span class="name">Nhóm thiết bị</span>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="info-row">
                            <div class="key w100">Câu lệnh</div>
                            <div class="value">
                                <textarea name="" :value="input_caulenh" @change="e=>input_caulenh=e.target.value" class="form-control" style="height: 150px;resize: none;"></textarea>
                            </div>
                        </div>
                    </div>
                    <div class="box-move-select-table">
                        <div class="box-col box-form">
                            <div class="legend-title">Thuộc tính kiểu thuộc tính đã gán</div>
                            <KTableV3 :columns="columnsGan"
                                :dataSources="dataSourcesGan"
                                id="rtt_id"
                                @onSelectedRow="onSelectedRowGan"
                                @dataCheckeds="dataCheckedsGan"
                                :rowSelectedId="rowSelectedId"
                                tableHeight="400px"
                                :allowCheckBox="true"
                                :allowFilter="true"/>
                        </div>
                        <div class="actions">
                            <button class="btn" @click="boGan">
                                <span class="-ap icon-chevron-thin-right"></span>
                            </button>
                            <button class="btn" @click="gan">
                                <span class="-ap icon-chevron-thin-left"></span>
                            </button>
                        </div>
                        <div class="box-col box-form">
                            <div class="legend-title">Thuộc tính kiểu thuộc tính chưa gán</div>
                            <KTableV3 :columns="columnsChuaGan"
                                :dataSources="dataSourcesChuaGan"
                                id="rtt_id"
                                @dataCheckeds="dataCheckedsChuaGan"
                                @onSelectedRow="onSelectedRowChuaGan"
                                
                                tableHeight="400px"
                                :allowCheckBox="true"
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
import ActionTop from '@/components/kylq_components/ActionTop.vue'
import KTable from '@/components/kylq_components/KTable.vue'
import KTableV3 from '@/components/kylq_components/mirana/KTableV3.vue'
import AssignAttributeTypeToSuppliesTypeAPI from './AssignAttributeTypeToSuppliesTypeAPI'
export default {
    name:'AssignAttributeTypeToSuppliesType',
    components:{
        Footer,
        breadcrumb,
        ActionTop,
        KTable,
        KTableV3
    },
    data(){
        return{
            header: {
                title: "Gán thuộc tính, kiểu thuộc tính vào loại vật tư",
                list: [
                    { 
                        name: "Dữ liệu", 
                        link: { name: "Ui.cards" },
                        active: false
                    },
                    { 
                        name: "Danh mục QLVT", 
                        link: { name: "Ui.cards" },
                        active: false
                    },
                    {
                        name: "Gắn thuộc tính, kiểu thuộc tính vào loại vật tư",
                        link: { name: "Ui.buttons" },
                        active: true
                    },
                ],
            },
            actions:[
                {
                    id:'capnhat',
                    name:'Cập nhật',
                    active: true,
                    icon_class:'one-reload1'
                }
            ],
            columnsGan:[
              {
                  field: "stt",
                  label: "STT",
                  allowFilter: true
              },
              {
                  field: "thuoctinh",
                  label: "Thuộc tính",
                  allowFilter: true
              },
              {
                  field: "kieu_tt",
                  label: "Kiểu thuộc tính",
                  allowFilter: true
              },
              {
                  field: "hienthi",
                  label: "Hiển thị",
                  allowFilter: true
              },
              {
                  field: "nhom_tbi",
                  label: "Nhóm Tbi",
                  allowFilter: true
              },

            ],
            dataSourcesGan:[],
            columnsChuaGan:[
              {
                  field: "thuoctinh",
                  label: "Thuộc tính",
                  allowFilter: true
              },
              {
                  field: "kieu_tt",
                  label: "Kiểu thuộc tính",
                  allowFilter: true
              },

            ],
            dataSourcesChuaGan:[],
            danhsach_vattu_cha:[],
            vattu_cha_selected:null,
            danhsach_loai_dieukhien:[],
            loai_dieukhien_selected:null,
            danhmuc_vattu:[],
            fields:'',
            selectedNodes:[],
            expandedNodes:[-1],
            input_thutu:'',
            input_caulenh:'',
            check_hienthi:false,
            check_nhomthietbi:false,
            thuoctinh_ktt_vt:null,
            dataCheckGan:[],
            dataCheckChuaGan:[],
            rtt_id_selected:0,
            isChangedEventDrop:true,
            rowSelectedId:-1



        }
    },
    computed:{

    },
    methods:{
        onActionClick(action){
            if(action.id=='capnhat'){
                if(this.dataSourcesGan.length==0){
                    this.$toast.error('Không có dữ liệu để cập nhập!')
                    return
                }
                if(this.vattu_cha_selected==null){
                    this.$toast.error('Chưa chọn vật tư để cập nhật!')
                    return
                }
                if(this.rtt_id_selected==0){
                    this.$toast.error('Chưa chọn thuộc tính để cập nhật!')
                    return
                }
                if(this.loai_dieukhien_selected==null){
                    this.$toast.error('Bạn phải chọn loại điều khiển')
                    return
                }
                let data={
                    thutu: this.input_thutu!=null?this.input_thutu.toString().trim()==''?0:Number(this.input_thutu.toString().trim()):0,
                    vattu_id: this.vattu_cha_selected,
                    loaidk_id: this.loai_dieukhien_selected,
                    nhomtbi: this.check_nhomthietbi?1:0,
                    caulenh: this.input_caulenh!=null?this.input_caulenh.toString().trim():'',
                    rtt_id: this.rtt_id_selected,
                    hienthi: this.check_hienthi?1:0
                }
                this.fn_capnhat_vatu_thuoctinh(data)
                
            }
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
        reset(){
            this.input_thutu=''
            this.input_caulenh=''
            this.check_hienthi=false
            this.loai_dieukhien_selected=null
            this.thuoctinh_ktt_vt=null
            this.dataCheckGan=[]
            this.dataCheckChuaGan=[]
        },
        changeVatTu(e){
            let value=e.value
            if(!this.isChangedEventDrop){
                this.isChangedEventDrop=true
                return
            }
            //search expand node and focus
            const index=this.danhmuc_vattu.findIndex(x=>x.vattu_id==value)
            if(index>-1){
                //this.expandedNodes.push(value)
                //selected node focus
                this.selectedNodes=[value]

                //find array parent expandedNodes
                var item=this.danhmuc_vattu[index]
                this.expandedNodes=[-1]
                this.expandedNodes.push(value)
                var vattu_id=item.vattu_cha_id
                while(vattu_id!=null&&vattu_id!=-1){
                    const index1=this.danhmuc_vattu.findIndex(x=>x.vattu_id==vattu_id)
                    if(index1>-1){
                        this.expandedNodes.push(vattu_id)
                        vattu_id=this.danhmuc_vattu[index1].vattu_cha_id
                    }else{
                        vattu_id=-1
                    }
                }


            }else{
                this.$toast.error('Không tìm thấy thông tin vật tư trên TreeView!')
                return
            }
            
        },
        tvVatTu_FocusedNodeChanged(node){
            let vattu_id=Number(node.nodeData.id)
            if(vattu_id!=-1){
                this.reset()
                this.isChangedEventDrop=false
                this.vattu_cha_selected= vattu_id
                this.hienthi_ds_gan_chuagan(this.vattu_cha_selected)
            }
        },
        createTreeVatTu(dsVatTu){
            //
            let tree = []
            let vattuChaChaArray = dsVatTu.map((item) => {
                return item['vattu_cha_id']
            })
            let vattuArray = dsVatTu.map(item => (item['vattu_id']))
            dsVatTu.forEach(item => {
                //check item la parent node
                if(vattuChaChaArray.includes(item['vattu_id'])){
                    //check donvi_cha_id khong co trong ds donvi_id thi xoa donvi_cha_id
                    if(!vattuArray.includes(item['vattu_cha_id'])){
                        delete item.vattu_cha_id
                    }
                    tree.push( {...item, hasChild:true, expanded: false})
                }else{
                    tree.push(item)
                }
            })
            //console.log('tree', tree)
            return tree
        },
        dataCheckedsGan(data){
            this.dataCheckGan=data
        },
        dataCheckedsChuaGan(data){
            this.dataCheckChuaGan=data
        },
        boGan(){
            if(this.dataCheckGan.length==0){
                this.$toast.error('Hãy chọn ít nhất 1  thuộc tính - kiểu thuộc tính trước khi gỡ')
                return
            }
            this.$confirm('Bạn có chắc chắn muốn gỡ thuộc tính - kiểu thuộc tính đã gán?','Thông báo',{
                confirmButtonText: 'Có',
                cancelButtonText: 'Không'
            }).then(() => {
                //tạo dữ liệu
                let dulieu=[]
                this.dataCheckGan.forEach((item)=>{
                    dulieu.push({
                        TTVT_ID:item.rtt_id,
                        VATTU_ID:this.vattu_cha_selected
                    })
                })
                this.fn_xoa_vattu_thuoctinh(dulieu)
            })
        },
        gan(){
            if(this.dataCheckChuaGan.length==0){
                this.$toast.error('Hãy chọn ít nhất 1  thuộc tính - kiểu thuộc tính trước khi gán')
                return
            }
            //tạo dữ liệu
            let dulieu=[]
            this.dataCheckChuaGan.forEach((item)=>{
                dulieu.push({
                    TTVT_ID:item.thuoctinh_id,
                    VATTU_ID:this.vattu_cha_selected,
                    RTT_ID:item.rtt_id
                })
            })
            this.fn_them_vattu_thuoctinh(dulieu)
        },
        async onSelectedRowGan(item){
            this.reset()
            this.rtt_id_selected=item.rtt_id
            let result=await this.lay_ds_thuoctinh_ktt_vt(this.vattu_cha_selected, item.rtt_id)
            if(result.length>0){
                this.thuoctinh_ktt_vt=result[0]
                //hiển thị lên form
                this.input_thutu=this.thuoctinh_ktt_vt.thutu
                this.input_caulenh=this.thuoctinh_ktt_vt.caulenh
                this.check_hienthi=(this.thuoctinh_ktt_vt.hienthi!=null&&this.thuoctinh_ktt_vt.hienthi==1)?true:false
                this.loai_dieukhien_selected=this.thuoctinh_ktt_vt.loaidk_id
                this.check_nhomthietbi=(this.thuoctinh_ktt_vt.nhom_tbi!=null&&this.thuoctinh_ktt_vt.nhom_tbi==1)?true:false
            }
        },
        onSelectedRowChuaGan(item){
            //this.reset()
        },
        async hienthi_ds_gan_chuagan(vattu_id){
            this.dataSourcesChuaGan=[]
            this.dataSourcesChuaGan=await this.lay_ds_chuagan(vattu_id)
            this.dataSourcesGan=[]
            this.dataSourcesGan=await this.lay_ds_gan(vattu_id)
            if(this.dataSourcesGan.length>0){
                if(this.rtt_id_selected!=0){
                    const index=this.dataSourcesGan.findIndex(x=>x.rtt_id==this.rtt_id_selected)
                    if(index>-1){
                        this.rowSelectedId=this.dataSourcesGan[index].rtt_id
                        this.onSelectedRowGan(this.dataSourcesGan[index])
                    }else{
                        this.rowSelectedId=this.dataSourcesGan[0].rtt_id
                        this.onSelectedRowGan(this.dataSourcesGan[0])
                    }
                }else{
                    this.rowSelectedId=this.dataSourcesGan[0].rtt_id
                    this.onSelectedRowGan(this.dataSourcesGan[0])
                }
            }
        },
        async sp_ds_vattu_cha(){
            try{
                this.loading(true)
                this.danhsach_vattu_cha=[]
                let response=await AssignAttributeTypeToSuppliesTypeAPI.sp_ds_vattu_cha(this.axios)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                    this.danhsach_vattu_cha=response.data.data
                }else{
                    this.danhsach_vattu_cha=[]
                }
            }catch(e){
                if(e.data&&e.data.message){
                    this.$toast.error(e.data.message)
                }else if(e.response&&e.response.data&&e.response.data.message){
                    this.$toast.error(e.response.data.message)
                }else{
                    this.$toast.error('Đã có lỗi khi lấy danh sách vật tư cha')
                }
            }finally{
                this.loading(false)
            }
        },
        async sp_ds_loai_dieukhien(){
            try{
                this.loading(true)
                this.danhsach_loai_dieukhien=[]
                let response=await AssignAttributeTypeToSuppliesTypeAPI.sp_ds_loai_dieukhien(this.axios)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                    this.danhsach_loai_dieukhien=response.data.data
                    if(this.danhsach_loai_dieukhien.length>0){
                        this.loai_dieukhien_selected=this.danhsach_loai_dieukhien[0].loaidk_id
                    }
                }else{
                    this.danhsach_loai_dieukhien=[]
                }
            }catch(e){
                if(e.data&&e.data.message){
                    this.$toast.error(e.data.message)
                }else if(e.response&&e.response.data&&e.response.data.message){
                    this.$toast.error(e.response.data.message)
                }else{
                    this.$toast.error('Đã có lỗi khi lấy danh sách loại điều khiển')
                }
            }finally{
                this.loading(false)
            }
        },
        async sp_ht_ds_danhmuc_vattu(){
            try{
                this.loading(true)
                this.danhmuc_vattu=[]
                let response=await AssignAttributeTypeToSuppliesTypeAPI.sp_ht_ds_danhmuc_vattu(this.axios)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                    //this.danhmuc_vattu=response.data.data
                    this.danhmuc_vattu.push({
                        unitlevel: 0, 
                        ten_vt: "Root",
                        vattu_id: -1,
                        vattu_cha_id: null,
                        ma_vt: "",
                    })
                    response.data.data.forEach((item)=>{
                        if(item.unitlevel==1){
                            item.vattu_cha_id=-1
                        }
                        this.danhmuc_vattu.push(Object.assign(item, {
                            ten_vt:item.ma_vt +' - '+item.ten_vt
                        }))
                    })
                    this.expandedNodes.push(response.data.data[0].vattu_id)
                    let danhmuc_vattu_tree=this.createTreeVatTu(this.danhmuc_vattu)
                    //expand
                    danhmuc_vattu_tree.forEach((item)=>{
                        if(this.expandedNodes.indexOf(item.vattu_id)>-1){
                            item.expanded=true
                        }
                    })
                    this.fields = { dataSource: danhmuc_vattu_tree, id: 'vattu_id', text: 'ten_vt', parentID: 'vattu_cha_id', hasChildren: 'hasChild' }
                }else{
                    this.danhmuc_vattu=[]
                }
            }catch(e){
                if(e.data&&e.data.message){
                    this.$toast.error(e.data.message)
                }else if(e.response&&e.response.data&&e.response.data.message){
                    this.$toast.error(e.response.data.message)
                }else{
                    this.$toast.error('Đã có lỗi khi lấy danh mục vật tư')
                }
            }finally{
                this.loading(false)
            }
        },
        async lay_ds_chuagan(vattu_id){
            try{
                this.loading(true)
                let response=await AssignAttributeTypeToSuppliesTypeAPI.lay_ds_ttktt_gan_chuagan(this.axios, vattu_id, 1)
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
        async lay_ds_gan(vattu_id){
            try{
                this.loading(true)
                let response=await AssignAttributeTypeToSuppliesTypeAPI.lay_ds_ttktt_gan_chuagan(this.axios, vattu_id, 0)
                this.loading(false)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                    return response.data.data.map((x, index)=>Object.assign(x, {stt:index+1}))
                }else{
                    return []
                }
            }catch(e){
                this.loading(false)
                return []
            }
        },
        async lay_ds_thuoctinh_ktt_vt(vattu_id, rtt_id){
            try{
                this.loading(true)
                let response=await AssignAttributeTypeToSuppliesTypeAPI.lay_ds_thuoctinh_ktt_vt(this.axios, vattu_id, rtt_id)
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
        async fn_xoa_vattu_thuoctinh(dulieu){
            try{
                this.loading(true)
                let response = await AssignAttributeTypeToSuppliesTypeAPI.fn_xoa_vattu_thuoctinh(this.axios, dulieu)
                this.loading(false)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000') {
                    if(response.data.data=='ok'){
                        this.$toast.success('Gỡ thành công')
                        this.reset()
                        this.hienthi_ds_gan_chuagan(this.vattu_cha_selected)
                    }else{
                        this.$toast.error(response.data.data)
                    }
                }else{
                    if(response&&response.data&&response.data.message){
                        this.$toast.error(response.data.message)
                    }else{
                        this.$toast.error('Đã xảy ra lỗi, gỡ thuộc tính không thành công!')
                    }
                }
            }catch(e){
                this.loading(false)
                if(e.data&&e.data.message){
                    this.$toast.error(e.data.message)
                }else if(e.response&&e.response.data&&e.response.data.message){
                    this.$toast.error(e.response.data.message)
                }else{
                    this.$toast.error('Đã xảy ra lỗi, gỡ thuộc tính không thành công!')
                }
            }
        },
        async fn_them_vattu_thuoctinh(dulieu){
            try{
                this.loading(true)
                let response = await AssignAttributeTypeToSuppliesTypeAPI.fn_them_vattu_thuoctinh(this.axios, dulieu)
                this.loading(false)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000') {
                    if(response.data.data=='ok'){
                        this.$toast.success('Gán thành công')
                        this.reset()
                        this.hienthi_ds_gan_chuagan(this.vattu_cha_selected)
                    }else{
                        this.$toast.error(response.data.data)
                    }
                }else{
                    if(response&&response.data&&response.data.message){
                        this.$toast.error(response.data.message)
                    }else{
                        this.$toast.error('Đã xảy ra lỗi, gán thuộc tính không thành công!')
                    }
                }
            }catch(e){
                this.loading(false)
                if(e.data&&e.data.message){
                    this.$toast.error(e.data.message)
                }else if(e.response&&e.response.data&&e.response.data.message){
                    this.$toast.error(e.response.data.message)
                }else{
                    this.$toast.error('Đã xảy ra lỗi, gán thuộc tính không thành công!')
                }
            }
        },
        async fn_capnhat_vatu_thuoctinh(data){
            try{
                this.loading(true)
                let response = await AssignAttributeTypeToSuppliesTypeAPI.fn_capnhat_vatu_thuoctinh(this.axios, data)
                this.loading(false)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000') {
                    if(response.data.data=='ok'){
                        this.$toast.success('Cập nhập thành công!')
                        await this.hienthi_ds_gan_chuagan(this.vattu_cha_selected)
                    }else{
                        this.$toast.error(response.data.data)
                    }
                }else{
                    if(response&&response.data&&response.data.message){
                        var mes=response.data.message
                        if(response.data.message_detail){
                            mes=mes+' '+response.data.message_detail
                        }
                        this.$toast.error(mes)
                    }else{
                        this.$toast.error('Đã xảy ra lỗi, cập nhật không thành công!')
                    }
                }
            }catch(e){
                this.loading(false)
                if(e.data&&e.data.message){
                    var mes=e.data.message
                    if(e.data.message_detail){
                        mes=mes+' '+e.data.message_detail
                    }
                    this.$toast.error(mes)
                }else if(e.response&&e.response.data&&e.response.data.message){
                    var mes=e.response.data.message
                    if(e.response.data.message_detail){
                        mes=mes+' '+e.response.data.message_detail
                    }
                    this.$toast.error(mes)
                }else{
                    this.$toast.error('Đã xảy ra lỗi, cập nhật không thành công!')
                }
            }
        },
        async initDuLieu(){
            await this.sp_ht_ds_danhmuc_vattu()
            await this.sp_ds_vattu_cha()
            await this.sp_ds_loai_dieukhien()
        }
    },
    mounted(){
        setTimeout(()=>{
            this.initDuLieu()
        },500)
    },
    watch:{
        
    }
}
</script>