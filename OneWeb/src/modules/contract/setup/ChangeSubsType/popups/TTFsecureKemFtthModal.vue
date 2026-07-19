<template>
    <b-modal
        ref="popupTTFsecureKemFtth"
        size="lg"
        hide-header
        hide-header-close
        hide-footer
        body-class="p-0"
        @show="handleShowModal"
    >
    <div class="modal-content popup-box">
        <div class="popup-header"> 
            <div class="title"><span class="icon one-notepad"></span> Thông tin thuê bao Fsecure lắp kèm</div> 
            <div class="close -ap icon-close" data-dismiss="modal" @click="closeForm"> 
            </div> 
        </div> 
        <ActionTop :actions="actions" @onActionClick="onActionClick" :isMore="false"/>
        <div class="popup-body">
            <div class="box-form">
                <div class="info-row">
                    <h2>THÔNG TIN THUÊ BAO FSECURE LẮP KÈM</h2>
                </div>
                <div class="legend-title">Thông tin thuê bao</div>
                <div class="row">
                    <div class="info-row">
                        <div class="key w80 nowrap">Khách hàng tự cài đặt</div>
                        <div class="value">
                            <div class="check-action">
                                <input type="checkbox" class="check" v-model="chkKH_CD"/>
                                <span class="name"></span>
                            </div>
                        </div>
                    </div>
                    <div class="info-row">
                        <div class="key w80">SĐT đăng ký</div>
                        <div class="value">
                            <input type="text" class="form-control" v-model="txtSoDT">
                        </div>
                    </div>
                </div>
            </div>
        </div> 
    </div>
    </b-modal>   
</template>
<script>
import ActionTop from '@/components/kylq_components/mirana/ActionTop.vue'
export default {
    name:'TTFsecureKemFtthModal',
    props:[ 'dulieu' ],
    components:{
        ActionTop
    },
    data(){
        return {
            actions:[
                {
                    id:'chapnhan',
                    name:'Chấp nhận',
                    active: true,
                    icon_class:'nc-icon-glyph ui-1_check-circle-08',
                    type:1
                },
                {
                    id:'thoat',
                    name:'Thoát',
                    active: true,
                    icon_class:'nc-icon-glyph ui-1_check-circle-08',
                    type:1
                },

            ],
            chkKH_CD:false,
            txtSoDT: ''
        }
    },
    methods:{
        showModal() {
            this.$refs["popupTTFsecureKemFtth"].show()
        },
        hideModal() {
            this.$refs["popupTTFsecureKemFtth"].hide()
        },
        formClose(value){
            this.$emit('form-close', value)
            this.hideModal()
        },
        handleShowModal(){
            this.chkKH_CD=false
            this.txtSoDT=''
            setTimeout(()=>{
                this.txtSoDT=this.dulieu.so_dt
            },500)
        },
        onActionClick(action){
            if(action.id=='chapnhan'){
                if (this.txtSoDT.trim() == ""){
                    this.$toast.error("Hãy nhập số điện thoại!")
                    return
                }
                if (this.txtSoDT[0] == '0'){
                    this.$toast.error("Bạn hãy bỏ số '0' ở đầu số điện thoại Fsecure!")
                    return
                }
                if (this.txtSoDT.trim().length < 9 || this.txtSoDT.trim().length > 10){
                    this.$toast.error("Bạn phải nhập số di động vào (9 ký tự số <= Số điện thoại <= 10 ký tự số)!")
                    return
                }
                if(!this.validatePhoneNumber(this.txtSoDT.trim())){
                    this.$toast.error('Số điện thoại phải là kiểu số!')
                    return
                }
                this.$emit('form-close',{ so_dt: this.txtSoDT.trim(), kh_cd_fsecure: this.chkKH_CD })
                this.hideModal()
            }else if(action.id=='thoat'){
                this.closeForm()
            }
        },
        validatePhoneNumber(input_str) {
            var re = /^[\+]?[(]?[0-9]{3}[)]?[-\s\.]?[0-9]{3}[-\s\.]?[0-9]{4,6}$/im;
            return re.test(input_str);
        },
        async closeForm() {
            let result=await this.confirm('Bạn không lắp Fsecure kèm FTTH?','Thông báo')
            if(result==1){
                this.$emit('form-close',null)
                this.hideModal()
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
        
    }
}
</script>