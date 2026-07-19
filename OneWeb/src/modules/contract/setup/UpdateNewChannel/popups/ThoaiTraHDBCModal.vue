<template>
    <b-modal
    ref="popupThoaiTraHDBC"
    size="lg"
    hide-header
    hide-header-close
    hide-footer
    body-class="p-0"
    no-close-on-backdrop
    @show="handleShowModal"
  >
    <div class="modal-content popup-box">
        <div class="popup-header">
            <div class="title">
                <span class="icon one-notepad"></span> Cập nhật thoái trả hợp đồng bán chéo
            </div>
            <div class="close -ap icon-close" @click="hideModal"></div>
        </div>
        <div class="list-actions-top">
			<ul class="list">
				<li>
					<a href="#" @click.prevent="tsbtnCapNhan_Click">
						<span class="icon nc-icon-glyph ui-1_check-circle-08"></span>Chấp
						nhận
					</a>
				</li>
			</ul>
		</div>
        <div class="popup-body">
            <div class="box-form">
                <div class="info-row">
                    <div class="key w100">Nội dung</div>
                    <div class="value">
                        <textarea v-model="input_noidung" class="form-control" style="height: 100px;resize: none;"></textarea>
                    </div>
                </div>
            </div>
        </div>
    </div>
    </b-modal>
</template>
<script>
export default {
    name:'ThoaiTraHDBCModal',
    props:{
        vhdtbid:{
            type:Number,
            default:0
        },
        vtinhban:{
            type:Number,
            default:0
        },
        vtinhtc:{
            type:Number,
            default:0
        },
    }, 
    components:{

    },
    data(){
        return {
            hdkh_id:0,
            hdtt_id:0,
            input_noidung:'',
            tinh:null
        }
    },
    methods:{
        showModal() {
            this.$refs["popupThoaiTraHDBC"].show()
        },
        hideModal() {
            this.$refs["popupThoaiTraHDBC"].hide()
        },
        handleShowModal(){
            this.hdkh_id=0
            this.hdtt_id=0
            this.input_noidung=''
            this.tinh=null
        },
        tsbtnCapNhan_Click(){
            this.Thoat_Tra()
        },
        async Thoat_Tra(){
            if(this.input_noidung.trim()==''){
                this.$toast.error('Bạn hãy nhập nội dung thoái trả hợp đông, Tên, SĐT để tỉnh bán liên hệ.')
                return
            }
            this.tinh=await this.tinh_thicong(this.vtinhban)

            await this.bancheo_thoaitra_hd({
                vtinh_id:this.vtinhtc,
                vhdtb_id:this.vhdtbid,
                vnoidung:this.input_noidung.trim()
            })
            await this.thoaitra_hd({
                vhdtbid:this.vhdtbid, 
                vnoidung:this.input_noidung.trim()
            })
            let dt1=await this.sp_hd_bancheo_theo_hdtb_thicong({
                vtinhtc:this.vtinhtc,
                vhdtb_id:this.vhdtbid
            })
            if(dt1.length>0){
                let dt=await this.bancheo_lay_phieu_vip_net_media(this.vhdtbid)
                if(dt.length>0){
                    for(let i=0;i<dt.length;i++){
                        await this.bancheo_thoai_update_vip_net_media({
                            vphieu_tinh_id:dt[i].phieu_id?dt[i].phieu_id:0,
                            vtinh_id:this.vtinhban
                        })
                    }
                }
            }

            this.$emit('success',{
                hdtb_id:this.vhdtbid
            })
            this.hideModal()
        },
        async tinh_thicong(tinh_id) {
            try{
                this.loading(true)
                let response = await this.axios.post('web-hopdong/tracuubaohong/sp_lay_tinh_tc_theo_tinh_id', {
                    tinh_id:tinh_id
                })
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
        async bancheo_thoaitra_hd(data) {
            try{
                this.loading(true)
                let response = await this.axios.post('web-hopdong/khuyenmai_tocdo_internet/bancheo_thoaitra_hd', data)
                this.loading(false)
            }catch(e){
                this.loading(false)
            }
        },
        async thoaitra_hd(data) {
            try{
                this.loading(true)
                let response = await this.axios.post('web-hopdong/khuyenmai_tocdo_internet/thoaitra_hd', data)
                this.loading(false)
            }catch(e){
                this.loading(false)
            }
        },
        async sp_hd_bancheo_theo_hdtb_thicong(data) {
            try{
                this.loading(true)
                let response = await this.axios.post('web-hopdong/khuyenmai_tocdo_internet/sp_hd_bancheo_theo_hdtb_thicong', data)
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
        async bancheo_lay_phieu_vip_net_media(hdtb_id) {
            try{
                this.loading(true)
                let response = await this.axios.get(`/web-hopdong/khuyenmai_tocdo_internet/bancheo_lay_phieu_vip_net_media?vhdtb_id=${hdtb_id}`)
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
        async bancheo_thoai_update_vip_net_media(data) {
            try{
                this.loading(true)
                let response = await this.axios.post('web-hopdong/khuyenmai_tocdo_internet/bancheo_thoai_update_vip_net_media', data)
                this.loading(false)
            }catch(e){
                this.loading(false)
            }
        },
    }
}
</script>