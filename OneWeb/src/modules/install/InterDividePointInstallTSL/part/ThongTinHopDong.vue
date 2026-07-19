<template>
    <div class="box-form" style="    height: 380px;">
    <div class="legend-title">Thông tin hợp đồng</div>
    <div class="row">
        <div class="col-sm-7 col-12">
            <vue-select v-model="formProps.loaiHD" :required="true" :labelWidth="'110'" :options="options.loaiHD" id="loaiHD" labelField="ten_loaihd" valueField="loaihd_id" label="Loại HĐ/PL"></vue-select>
             <vue-input v-model="formProps.account" :labelWidth="'110'" :required="true" label="Account" :checkbox="false" :highlight="true" @enter="accountOnEnter"> </vue-input>
             <vue-select v-model="formProps.loaiHinh" :labelWidth="'110'" :options="options.loaiHinh" id="loaiHD" labelField="LOAIHINH_TB" valueField="LOAITB_ID" label="Loại hình" :disable="true"></vue-select>
        </div>
        <div class="col-sm-5 col-12">
            <vue-input v-model="formProps.maGiaoDich" :labelWidth="'110'" :required="true" label="Mã giao dịch" :checkbox="false" :highlight="true" @enter="maGiaoDichOnEnter"> </vue-input>
            <vue-input v-model="formProps.kieuLapDat" :labelWidth="'110'" :required="true" label="Kiểu lắp đặt" :checkbox="false" :disable="true"> </vue-input>
            <vue-select v-model="formProps.tocDoKenh" :labelWidth="'110'" :options="options.tocDoKenh" id="tocDoKenh" labelField="toc_do" valueField="tocdo_id" label="Tốc độ kênh" :disable="true"></vue-select>
        </div>
    </div>
    <div class="row">
        <div class="col-sm-12 col-12">            
            <vue-input v-model="formProps.tenThueBao" :labelWidth="'110'"  label="Tên thuê bao" :checkbox="false" :disable="true"> </vue-input>
        </div>
    </div>
    <div class="row">
        <div class="col-sm-12 col-12">
            <vue-input v-model="formProps.diaChiTB" :labelWidth="'110'"  label="Địa chỉ TB" :checkbox="false" :disable="true"> </vue-input>
        </div>
    </div>
    <div class="row">
        <div class="col-sm-12 col-12">
            <vue-input v-model="formProps.donViGiao" :labelWidth="'110'"  label="Đơn vị giao" :checkbox="false" :disable="true"> </vue-input>
        </div>
    </div>
    <div class="row">
        <div class="col-sm-12 col-12">
            <vue-input v-model="formProps.nhanVienGiao" :labelWidth="'110'"  label="Nhân viên giao" :checkbox="false" :disable="true"> </vue-input>
        </div>
    </div>
    <div class="row">
        <div class="col-sm-12 col-12">
            <vue-input v-model="formProps.noiDungGiao" :labelWidth="'110'"  label="Nội dung giao" :checkbox="false" :disable="true"> </vue-input>
        </div>
    </div>
</div>
</template>

<script>
    export default {
        components: {
        },
        props: ['formProps'],
        data: () => ({
            options: {
                loaiHD: [],
                loaiHinh: [],
                tocDoKenh: []
            },
            dichVuVT_TSL: 9,
        }),
        mounted () {
            this.getDanhSachLoaiHD();
            this.getDanhSachLoaiHinh();
            this.getDanhSachTocDoKenh();
        },  
        watch:{
            'formProps.loaiHD'(newValue, oldValue){
                this.$emit('update-loai-hd', newValue);
            },
        },
        methods: {
            async getDanhSachLoaiHD () {
                const data = await this.$store.dispatch('loaiHDCommon/getDanhSachLoaiHD');
                this.options.loaiHD = data;
                if(data.length > 0){
                    this.formProps.loaiHD = data[0].loaihd_id;
                }
            },
            async getDanhSachLoaiHinh () {
                let data = await this.$store.dispatch('lhtbCommon/getDanhSachLoaiHinhTB');
                this.options.loaiHinh = data;//.filter(x => x.DICHVUVT_ID == this.dichVuVT_TSL)               
            },
            async getDanhSachTocDoKenh () {
                this.options.tocDoKenh = await this.$store.dispatch('tocDoKenhCommon/getDanhSachTocDoKenh')                
            },
            accountOnEnter(){
                // alert(this.formProps.account);
                this.$emit('on-enter-account', this.formProps.account);
            },
            maGiaoDichOnEnter(){
                // alert(this.formProps.maGiaoDich);
                this.$emit('on-enter-magiaodich', this.formProps.maGiaoDich);
            },
        }
    }
</script>