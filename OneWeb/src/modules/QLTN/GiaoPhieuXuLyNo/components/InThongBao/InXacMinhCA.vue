
<template>
    <b-modal
        :id="id"
        size="xl"
        no-close-on-backdrop
        hide-footer
        hide-header
        hide-header-close
        body-class="modal-body p-0"        
        >

        <div class="modal-content popup-box">
                <div class="popup-header">
                    <div class="title">
                        <span class="icon one-notepad"></span>Thông tin biên bản xác minh CA
                    </div>
                    <div
                        class="close -ap icon-close"
                        data-dismiss="modal"
                        @click="$bvModal.hide(id)"
                    ></div>
                </div>

                <div class="list-actions-top">
                    <ul class="list">            
                        <li>
                            <a class="pointer" @click="btnInDS">
                                <span class="icon one-print"></span> In DS
                            </a>
                        </li>            
                    </ul>
                </div>
                <div class="popup-body">
                    <div class="box-form">
                        <div class="legend-title">Công an phường</div>
                        <div class="info-row">            

                                <div class="row">
                                    <div class="col-sm-6 col-12">
                                        <div class="info-row">
                                            <div class="key w100">
                                                Kính gửi
                                            </div>
                                            <div class="value">
                                                <input type="text" v-model.trim="form.kinhgui" class="form-control">
                                            </div>
                                        </div>

                                        <div class="info-row">
                                            <div class="key w100">
                                                Công an
                                            </div>
                                            <div class="value">
                                                <input type="text" v-model.trim="form.congan" class="form-control">
                                            </div>                                            
                                        </div>
                                    </div>
                                    <div class="col-sm-6 col-12">
                                        <div class="info-row">
                                            <div class="key w100">
                                                Chức vụ
                                            </div>
                                            <div class="value">
                                                <input type="text" v-model.trim="form.chucvuca" class="form-control">
                                            </div>
                                        </div>
                                    </div>
                                </div>


                        </div>
                    </div>
                    <div class="box-form">
                        <div class="legend-title">Đơn vị</div>            
                            <div class="info-row"> 
                                    <div class="row">
                                        <div class="col-sm-6 col-12">
                                            <div class="info-row">
                                                <div class="key w100">
                                                    Công ty
                                                </div>
                                                <div class="value">
                                                    <input type="text" v-model.trim="form.congty" class="form-control">
                                                </div>
                                            </div>
                                            <div class="info-row">
                                                <div class="key w100">
                                                    Nhân viên
                                                </div>
                                                <div class="value">
                                                    <input type="text" v-model.trim="form.nhanvien" class="form-control">
                                                </div>
                                            </div>
                                            <div class="info-row">
                                                <div class="key w100">
                                                    Đại diện
                                                </div>
                                                <div class="value">
                                                    <input type="text" v-model.trim="form.daidien" class="form-control">
                                                </div>
                                            </div>                                              
                                        </div>
                                        <div class="col-sm-6 col-12">
                                            <div class="info-row">
                                                <div class="key w100">
                                                    Trung tâm
                                                </div>
                                                <div class="value">
                                                    <input type="text" v-model.trim="form.trungtam" class="form-control">
                                                </div>
                                            </div>
                                            <div class="info-row">
                                                <div class="key w100">
                                                    Chức vụ
                                                </div>
                                                <div class="value">
                                                    <input type="text" v-model.trim="form.chucvucty" class="form-control">
                                                </div>
                                            </div>
                                        </div>
                                    </div>


                            </div>
                    </div>
                </div>
        </div>        
    </b-modal>
</template>

<script>
import {previewPrint} from "@/utils/util";
export default {
    props: ['id','dsBienBan','kyCuoc'],
data(){
    return{
        form:{
            kinhgui:'',
            congan:'',
            chucvuca:'',
            
            congty:'',
            nhanvien:'',
            daidien:'',
            trungtam:'TRUNG TÂM KINH DOANH VNPT-',
            chucvucty:'',
        },
    };
},
methods:{
   btnInDS:async function(){
        console.log(this.dsBienBan);
            const baseUrl = process.env.API;
                        let noidung_in=null;

                        let param= {
                                trungTam: this.form.trungtam,
                                daiDien: this.form.daidien,
                                chucVuCty: this.chucvucty,
                                congAn: this.form.congan,
                                dsXLNId: this.dsBienBan,
                                kieuTaoDL: null,
                                kyCuoc: this.kyCuoc}                                        
                                    ;
                        await this.axios.post(baseUrl + "/web-thuno/api/thu-no/in-hoa-don/in-bien-ban-xac-minh", param, {responseType: 'blob'})
                            .then( (response) => {
                                noidung_in=response.data;
                            })

                    previewPrint(noidung_in);
   },
resetData(){console.log('sss')},

}

};
</script>
