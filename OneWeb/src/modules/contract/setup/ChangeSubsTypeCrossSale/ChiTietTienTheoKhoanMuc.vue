<template>
    <div class="col-sm-6 col-12">
        <div class="box-form">
            <div class="legend-title">
                Chi tiết tiền theo khoản mục (có VAT)
            </div>
            <div class="info-row">
                <div class="key">Tổng tiền</div>
                <div class="value">
                    <div class="input-more-button">
                        <button class="btn" @click="click">
                            <span class="-ap icon-more_horiz"></span>
                        </button>
                        <div class="disabled">
                            <vue-numeric class="form-control tright red bold" separator=","  :value="input_tien_khoanmuc" @change="e=>input_tien_khoanmuc=e.target.value" placeholder="0" output-type="Number"></vue-numeric>
                        </div>
                    </div>
                </div>
            </div>
            <div class="info-row">
                <div class="key">
                    Tổng VAT
                </div>
                <div class="value disabled">
                    <vue-numeric class="form-control tright red bold" separator=","  :value="input_vat" @change="e=>input_vat=e.target.value" placeholder="0" output-type="Number"></vue-numeric>
                </div>
            </div>
            <div class="info-row">
                <div class="key">
                    Tổng tiền TB
                </div>
                <div class="value disabled">
                    <vue-numeric class="form-control tright red bold" separator=","  :value="input_tongtien" @change="e=>input_tongtien=e.target.value" placeholder="0" output-type="Number"></vue-numeric>
                </div>
            </div>
        </div>
        <!-- Modal -->
        <ChiTietTienTheoKhoanMucModal ref="chiTietTienTheoKhoanMucModal" 
            :loaihd_id="6" 
            :ds_tien_caidat="ds_tien_khoanmuc"
            @accept="acceptTienKM"
        />
    </div>
</template>
<script>
import ChiTietTienTheoKhoanMucModal from '../ChangeSubsType/popups/ChiTietTienTheoKhoanMucModal.vue'
export default {
    name:'ChiTietTienTheoKhoanMuc',
    props:{
        ds_tien_khoanmuc:{
            type:Array,
            default:()=>[]
        }
    },
    components:{
        ChiTietTienTheoKhoanMucModal
    },
    data(){
        return {
            input_tien_khoanmuc:'0',
            input_vat:'0',
            input_tongtien:'0'
        }
    },
    methods:{
        click(){
            this.$refs.chiTietTienTheoKhoanMucModal.showModal()
        },
        async TongTienTB(tongTien, tongVat){
            console.log('TongTienTB')
            console.log('tongTien',tongTien)
            console.log('tongTien',tongVat)
            this.input_tien_khoanmuc=tongTien.toString()
            this.input_vat=tongVat.toString()
            this.input_tongtien=(tongTien+tongVat).toString()
            
        },
        async acceptTienKM(ds_tien_km){
            this.$emit('acceptTienKM', ds_tien_km)
        },
        async initDuLieu(){

        },
        async clearDulieu(){
            this.input_tien_khoanmuc='0'
            this.input_vat='0'
            this.input_tongtien='0'
        }
    }
    
}
</script>
<style scoped>
.disabled { 
    pointer-events: none;
    color:lightgray;
    border-color:lightgray;
}
</style>