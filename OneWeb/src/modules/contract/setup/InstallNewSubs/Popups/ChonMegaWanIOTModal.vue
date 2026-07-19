<template>
    <b-modal
    ref="popupChonMegaWanIOT"
    size="xl"
    hide-header
    hide-header-close
    hide-footer
    body-class="p-0"
    no-close-on-backdrop
  >
    <div class="modal-content popup-box">
      <div class="popup-header">
        <div class="title">
          <span class="icon one-notepad"></span> Chọn cấu hình MegaWanIOT
        </div>
        <div class="close -ap icon-close" @click="hideModal"></div>
      </div>
      <ActionTop :actions="actions" @onActionClick="onActionClick" />
      <div class="popup-body">
        <div class="row">
            <div class="col-sm-6 col-12">
                <div class="box-form">
                    <div class="legend-title">
                        <span class="fa fa-angle-up icon"></span>Thông tin kỹ thuật
                    </div>
                    <div class="row">
                        <div class="col-sm-6 col-12">
                            <div class="info-row">
                                <div class="key w80">Tỉnh</div>
                                <div class="value">
                                    <div class="select-custom" ref="ds_tinh">
                                        <select2 :settings="{ dropdownParent: $refs['ds_tinh'] }" ref="cboTinh"
                                            v-model="tinh_selected" class="select2"
                                            :options="ds_tinh.map(e=> ({id: e.tinh_id, text: e.tentinh}))">
                                        </select2>
                                    </div>  
                                </div>
                            </div>
                        </div>
                        <div class="col-sm-6 col-12">
                            <div class="info-row">
                                <div class="list-checks value">
                                    <div class="item">
                                        <div class="check-action">
                                            <input type="radio" class="check" name="loai" value="2" :checked="loai==2" @change="changeLoai($event)" />
                                            <span class="name">Hợp đồng</span>
                                        </div>
                                    </div>
                                    <div class="item">
                                        <div class="check-action">
                                            <input type="radio" class="check" name="loai" value="1" :checked="loai==1" @change="changeLoai($event)"/>
                                            <span class="name">Danh bạ</span>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-sm-6 col-12">
                            <div class="info-row">
                                <div class="key w80">Mã TB</div>
                                <div class="value">
                                    <input type="text"  :value="input_matb" @change="e=>input_matb=e.target.value" class="form-control highlight bold" />
                                </div>
                            </div>
                        </div>
                        <div class="col-sm-6 col-12">
                            <div class="info-row">
                                <div class="key w80">IP Lan</div>
                                <div class="value">
                                    <div class="row">
                                        <div class="col-sm-9 col-12">
                                            <input type="text"  :value="input_ip_lan" @change="e=>input_ip_lan=e.target.value" class="form-control" />
                                        </div>
                                        <div class="col-sm-3 col-12">
                                            <input type="text" readonly :value="txtSubNet" @change="e=>txtSubNet=e.target.value" class="form-control">
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-sm-6 col-12">
                            <div class="info-row">
                                <div class="key w80">VRF</div>
                                <div class="value">
                                    <input type="text" readonly  :value="input_vrf" @change="e=>input_vrf=e.target.value" class="form-control" />
                                </div>
                            </div>
                        </div>
                        <div class="col-sm-6 col-12">
                            <div class="info-row">
                                <div class="key w80">IP Wan</div>
                                <div class="value">
                                    <div class="row">
                                        <div class="col-sm-12 col-12">
                                            <input type="text"  :value="input_ip_wan" @change="e=>input_ip_wan=e.target.value" class="form-control" />
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>


                </div>
            </div>
            <div class="col-sm-6 col-12">
                <div class="box-form red bold">
                    <br>
                    - Địa chỉ IP chỉ được phép nằm trong 3 dải đã được cấp là:
                    <br>
                      + 10.0.0.0 - 10.255.255.255 (10.0.0.0/8).<br>
                      + 172.16.0.0 - 172.31.255.255 (172.16.0.0/12).<br>
                      + 192.168.0.0 - 192.168.255.255 (192.168.0.0/16).<br>
                    VD: - IP Lan: 10.0.0.7/29<br>
                            - IP Wan: 172.16.0.3<br>
                </div>
            </div>
        </div>
        <div class="box-form">
            <div class="legend-title">
                <span class="fa fa-angle-up icon"></span>Danh sách thuê bao MegaWanIOT
            </div>
            <KDataGrid
                  ref="gridDs"
                  :columns="columns"
                  :dataSource="dataSources"
                  :enable-paging-server="false"
                  :allowPaging="true"
                  :showFilter="true"
                  :showColumnCheckbox="false"
                  :enabledSelectFirstRow="true"
                  :selectionSettings="{ checkboxMode: 'ResetOnRowClick' }"
                  @onRowSelected="onRowSelected"/>
        </div>
      </div>
    </div>
    </b-modal>
</template>
<script>
import ActionTop from '@/components/kylq_components/ActionTop.vue'
import KDataGrid from '@/components/kylq_components/KDataGrid'
export default {
    name:'ChonMegaWanIOTModal',
    components:{
        ActionTop, KDataGrid
    },
    data(){
        return{
            actions:[
                {
                    id: "timkiem",
                    name: "Tìm kiếm",
                    active: true,
                    icon_class: "one-search"
                },
                {
                    id:'chapnhan',
                    name:'Chấp nhận',
                    active: true,
                    icon_class:'ui-1_check-circle-08 nc-icon-glyph'
                }
            ],
            ds_tinh:[],
            tinh_selected:null,
            loai:2,
            input_matb:'',
            input_ip_lan:'',
            input_ip_wan:'',
            input_vrf:'',
            dataSources:[],
            columns:[
                {
                    fieldName:'ma_tb',
                    headerText:'Mã thuê bao',
                    allowFiltering:true
                },
                {
                    fieldName:'vrf_vfi',
                    headerText:'VRF/VFI',
                    allowFiltering:true
                },
                {
                    fieldName:'ten_tb',
                    headerText:'Tên thuê bao',
                    allowFiltering:true
                },
                {
                    fieldName:'diachi_ld',
                    headerText:'Địa chỉ lắp đặt',
                    allowFiltering:true
                },
                {
                    fieldName:'loaihinh_tb',
                    headerText:'Loại hình',
                    allowFiltering:true
                },
                {
                    fieldName:'tocdo',
                    headerText:'Tốc độ',
                    allowFiltering:true
                },
                {
                    fieldName:'ma_lt',
                    headerText:'Số ảo',
                    allowFiltering:true
                },
                {
                    fieldName:'trangthai_tb',
                    headerText:'Trạng thái',
                    allowFiltering:true
                }
            ],
            lan_ip:'',
            wan_ip:'',
            vrf:'',
            hdkh_id:0,
            khachhang_id:0,
            ma_tb:'',
            txtSubNet:'/29'

        }
    },
    methods:{
        showModal(khachhang_id, hdkh_id, wan_ip, lan_ip, vrf, ma_tb) {
            this.$refs["popupChonMegaWanIOT"].show()
            //Clear data old
            this.clear()
            //init param
            this.khachhang_id=khachhang_id
            this.hdkh_id=hdkh_id
            this.wan_ip=wan_ip
            this.lan_ip=lan_ip
            this.vrf=vrf
            this.ma_tb=ma_tb
            //end init
            setTimeout(()=>{
                this.initDuLieu()
            }, 500)
        },
        hideModal() {
            this.$refs["popupChonMegaWanIOT"].hide()
        },
        onActionClick(action){
            if(action.id=='timkiem'){
                this.tsbtnTimKiem_Click()
            }else if(action.id='chapnhan'){
                this.tsbtnCapNhat_Click()
            }
        },
        clear(){
            this.ds_tinh=[]
            this.tinh_selected=null
            this.loai=2
            this.input_matb=''
            this.input_ip_lan=''
            this.input_ip_wan=''
            this.input_vrf=''
            this.dataSources=[]
            this.lan_ip=''
            this.wan_ip=''
            this.vrf=''
            this.hdkh_id=0
            this.khachhang_id=0
            this.ma_tb=''

        },
        async initDuLieu(){
            this.ds_tinh=await this.ds_tinh_kn()
            if(this.ds_tinh.length>0){
                this.tinh_selected=this.ds_tinh[0].tinh_id
            }
            console.log('initDuLieu dstinh',this.ds_tinh)
            this.input_matb=this.ma_tb
            this.input_vrf=this.vrf
            if(this.lan_ip.length>3){
                this.input_ip_lan=this.lan_ip.substring(0, this.lan_ip.length-3)
            }
            this.input_ip_wan=this.wan_ip
            await this.tsbtnTimKiem_Click()




        },
        changeLoai(event){
            this.loai=event.target.value 
        },
        onRowSelected(item){
            this.input_vrf=item.vrf_vfi?item.vrf_vfi.toString():''
            this.input_matb=item.ma_tb?item.ma_tb.toString():''
        },
        async tsbtnTimKiem_Click(){
            //
            let data={
                tinhthicong_id:this.tinh_selected, 
                vma_tb:this.input_matb.trim(), 
                vid:this.loai==2?this.hdkh_id:this.khachhang_id, 
                vloai:this.loai
            }
            this.dataSources=await this.lay_vrf_megaiot(data)

        },
        async tsbtnCapNhat_Click(){
            if(this.input_ip_lan.trim()==''||this.input_ip_wan.trim()==''){
                this.$toast.error('Hãy nhập địa chỉ IP ')
                return
            }
            if(this.input_vrf.trim()==''){
                this.$toast.error('Chưa có VRF, hãy tìm thuê bao MegaWanIOT')
                return
            }
            let data={
                vlan_ip:this.input_ip_lan.trim(), 
                vwan_ip:this.input_ip_wan.trim(), 
                vvrf:this.input_vrf.trim(), 
                vma_tb:this.input_matb 
            }
            let mess=await this.kiemtra_ip_megaiot(data)
            if(mess!='OK'){
                this.$toast.error(mess)
                return
            }
            this.$emit('accept', {
                wan_ip:this.input_ip_wan.trim(),
                lan_ip:this.input_ip_lan.trim()+this.txtSubNet.trim(),
                vrf:this.input_vrf.trim(),
                ma_tb:this.input_matb.trim()
            })
            this.hideModal()
        },
        async ds_tinh_kn(){
            try{
                this.loading(true)
                let response = await this.axios.get('/web-hopdong/megawaniot/sp_hienthi_tinh_kn')
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
        async lay_vrf_megaiot(data){
            try{
                this.loading(true)
                let response = await this.axios.post('/web-bancheo/tracuu/lay_vrf_megaiot', data)
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
        async kiemtra_ip_megaiot(data){
            try{
                this.loading(true)
                let response = await this.axios.post('/web-hopdong/megawaniot/kiemtra_ip_megaiot', data)
                this.loading(false)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                    return response.data.data
                }else{
                    if(response&&response.data&&response.data.message){
                        return response.data.message
                    }else{
                        return 'Xảy ra lỗi khi kiểm tra vrf'
                    }
                }
            }catch(e){
                this.loading(false)
                if(e.data&&e.data.message){
                    return e.data.message
                }else if(e.response&&e.response.data&&e.response.data.message){
                    return e.response.data.message
                }else{
                    return 'Xảy ra lỗi khi kiểm tra vrf'
                }
            }
        },
        

    }
    
}
</script>