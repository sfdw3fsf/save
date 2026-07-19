<template>
    <b-modal ref="frmCapNhat" size="xl" body-class="p-0" hide-header hide-header-close hide-footer>
        <div class="modal-content popup-box">
            <div class="popup-header">
                <div class="title">
                    <span class="icon one-notepad"></span> {{ vtieude }}
                </div>
                <div @click="hideModal" class="close -ap icon-close"></div>
            </div>
            <div class="list-actions-top">
                <ul class="list">
                    <li v-show="visible_nut_capnhat">
                        <a @click.prevent="capNhat" class="upload-button hand_click">
                            <span class="icon one-save"></span> Cập nhật
                        </a>
                    </li>
                </ul>
            </div>
            <div class="popup-body">
                <div class="box-form">
                    <!-- <div class="info-row">
                        <div class="key w80">Văn bản</div>
                        <div class="value">
                            <input type="text" class="form-control" v-model="$v.dulieu.vb.$model" :class="{'is-invalid': $v.dulieu.vb.$error}">
                            <div class="error" v-if="!$v.dulieu.vb.maxLength">Văn bản không được vượt quá tối đa {{ $v.dulieu.vb.$params.maxLength.max }} ký tự</div>
                        </div>
                    </div>
                    <div class="info-row">
                        <div class="key w80">File văn bản</div>
                        <div class="value">
                            <input type="file" class="file form-control" ref="file" v-on:change="uploadFile">
                        </div>
                    </div> -->
                    <div class="info-row">
                        <vue-date format="DD/MM/YYYY" type="date" labelWidth="80" v-model="dulieu.ngayycht_netvip" label="Ngày YC"></vue-date>
                        <div class="value padt7 w30 nowrap">
                            (Ngày yêu cầu hoàn thành cho NET/VIP)
                        </div>
                    </div>
                </div>
                <div class="box-form">
                    <div class="legend-title">Nội dung chuyển thi công</div>
                    <textarea v-model="$v.dulieu.noidung.$model" class="form-control" style="height: 140px;resize: none;"  :class="{'is-invalid': $v.dulieu.noidung.$error}"></textarea>
                    <div class="error" v-if="!$v.dulieu.noidung.maxLength">Nội dung chuyển thi công không được vượt quá tối đa {{ $v.dulieu.noidung.$params.maxLength.max }} ký tự</div>
                </div>
            </div>
        </div>
    </b-modal>
</template>
<style scoped>
.hand_click { cursor: pointer; }
</style>
<script>
import api from '../api'
import { required, maxLength } from 'vuelidate/lib/validators';
export default {
    props: {
        vtinhid:{
            type: Number,
            default: 0
        },
        vhdkh_id:{
            type: Number,
            default: 0
        },
        vhdtbid:{
            type: Number,
            default: 0
        },
        visible_nut_capnhat:{
            type: Boolean,
            default: false
        },
        vngayycht_netvip:{
            type: String,
            default: ((new Date()).getDate() > 9 ? '' : '0') + (new Date()).getDate() + '/' + (((new Date()).getMonth() + 1) > 9 ? '' : '0') + ((new Date()).getMonth() + 1) + '/' + (new Date()).getFullYear()
        },
        _vanban: {
            type: String,
            default: ''
        },
        vnoidung: {
            type: String,
            default: ''
        },
        vtieude: {
            type: String,
            default: ''
        }
    },
    data() {
        return {
            dulieu: {
                ts_ban_khdn: 0,
                capnhat: false,
                ngayycht_netvip: '',
                noidung: '',
                vb: '',
                link_file: '',
                path_file: ''
            }
        }
    },
    validations: 
    {
        dulieu: {
            ngayycht_netvip: { required },
            noidung: { required, maxLength: maxLength(2000) },
            vb: { required, maxLength: maxLength(200) }
        }
    },
    created() {
        this.khoiTao();
    },
    methods: {
        showModal() {
            this.$refs['frmCapNhat'].show()
        },
        hideModal() {
            this.$refs['frmCapNhat'].hide()
        },
        khoiTao() {
            this.dulieu.ngayycht_netvip = this.vngayycht_netvip;
            this.dulieu.noidung = this.vnoidung;
            this.dulieu.vb = this._vanban;
            api.getDsThamSoMD(this.axios).then(res => {
                let dulieu = res.data.data ? JSON.parse(res.data.data) : {};
                if (dulieu && dulieu.ERROR_CODE == 1) {
                    let ds_tsmd = dulieu.RESULT.DS_TSMD;
                    if (ds_tsmd.find(x => x.MA_TS == "TS_BAN_KHDN")) this.dulieu.ts_ban_khdn = Number(ds_tsmd.find(x => x.MA_TS == "TS_BAN_KHDN").TEN_TS);
                }
            });
        },
        checkNoiDungBanCheov2: async function(vhdkh_id) {
            let result = '';
            await api.checkNoiDungBanCheov2(this.axios, vhdkh_id
            ).then(res => {
                if (res.data.error_code === "BSS-00000000" && res.data.data) {
                    result = res.data.data ? res.data.data : '';
                }
            });
            return result;
        },
        capNhat: async function() {
            // if (this.dulieu.ts_ban_khdn != 1) {
                console.log("vhdtbid ",this.vhdtbid)
                    let vkq = await this.checkNoiDungBanCheov2(this.vhdtbid);
                    if (vkq != "OK")
                    {
                        if(vkq == "") this.$toast.error("Chưa gán cơ chế không thể chuyển")
                        else this.$toast.error(vkq)
                        return;
                    }
            // }
            this.dulieu.capnhat = true;
            await api.capNhatNoiDungChuyenHopDong(this.axios, {
                "vtinhid": this.vtinhid,
                "vhdkh_id": this.vhdkh_id,
                "vnoidung": this.dulieu.noidung,
                "vb": "",
                "vurl": ""
            }).then();
            this.$emit('capNhat', this.dulieu);
            this.hideModal();
        },
        // uploadFile(event) {
        //     if (event.target.files[0]) {
        //         let self = this;
        //         let formData = new FormData();
        //         for(var file of this.$refs.file.files) {
        //             formData.append('files', file);
        //         }
        //         this.dulieu.link_file = '';
        //         this.dulieu.path_file = '';
        //         api.upload_file(this.axios, formData, { headers: { 'Content-Type': 'multipart/form-data' } }).then((response) => {
        //             if(response.data.error_code === 'BSS-00000000') {
        //                 api.getLinkFile(this.axios, {
        //                     "list_file": response.data.data[0]
        //                 }).then((res) => {
        //                     if (res.data.data && res.data.data.length > 0) {
        //                         this.dulieu.link_file = res.data.data[0].file_url;
        //                         this.dulieu.path_file = res.data.data[0].file_name;
        //                         this.$toast.success("Upload file thành công.");
        //                     }
        //                     else {
        //                         this.$toast.error("Upload file thất bại.");
        //                     }
        //                 }).catch(() => {
        //                     self.$toast.error("Upload file thất bại.");
        //                 });
        //             }
        //         }).catch(function() {
        //             self.$toast.error('File upload failed!');
        //         });
        //     } else {
        //         this.$toast.error('Chưa chọn file để upload');
        //     }
        // }
    }
}
</script>
