
<template>
    <b-modal size="xl" centered scrollable hide-footer :id="idModal" modal-class="fullscreen-modal" no-close-on-backdrop
        no-close-on-esc>
        <template #modal-header>
            <div class="title">
                <span class="icon icon-notepad"></span>
                <h3>Tạo HĐĐT</h3>
            </div>
            <div class="close -ap icon-close" data-dismiss="modal" @click="closeModal"></div>
        </template>

        <div class="modal-content popup-box" v-if="!showIframe">
            <div class="list-actions-top" >
                <ul class="list">
                    <li >
                        <a @click="tsbtnCreatEInvoice_Click" :class="cboMauHD.value ?'':'disabled'"> <span class="icon one-save"></span>Tạo HĐĐT </a>
                    </li>

                </ul>
            </div>
            <div class="grid-stack-box">
                <div class="popup-body">
                    <div class="row" style="width: 222% !important;">
                        <div class="legend-title">Chọn mẫu HĐĐT</div>
                        <div class="info-row" style="height: 80px;">
                            <div class="key w100">Mẫu HĐĐT</div>
                            <div class="value">
                                <div class="select-custom">
                                    <ejs-dropdownlist id="tinhtrang_id" locale="vi-VN" v-model="cboMauHD.value"
                                        :dataSource="cboMauHD.options" :allowFiltering="true"
                                        :fields="{ value: 'template_id', text: 'template_group_name' }" />
                                </div>
                            </div>
                        </div>

                    </div>
                    <!-- <div class="row">
                            <div class="legend-title">Danh sách hoá đơn</div>
                        </div> -->
                </div>

                <div class="popup-footer"></div>
            </div>
            
        </div>
        <div id="iframe-view" class="iframe-econtract" style="display: none;">
                <div class="iframe-header">
                    <label class="w3-text-red" style="margin-left: 0.5rem;"><strong> IFrame VNPT EContract</strong></label>
                    <button class="w3-btn w3-btn-sm w3-ripple w3-blue" style="margin-left: auto; margin-right: 0.5rem;"
                        @click="closeIframe">Đóng</button>
                </div>
                <div class="iframe-content">
                    <iframe id="econtract-wv-iframe" :src="link" style="width: 100%; height: 100%;"></iframe>
                </div>
        </div>

    </b-modal>
</template>
<script>
import Vue from 'vue';
import api from './api';
import apiHelper from './api.helper';

import _ from 'lodash';


export default {
    name: 'CreateEInvoiceModal',
    components: {
    },
    props: {
        idModal: {
            type: String,
            default: "createEInvoiceModal",

        },
        kieuld_id: {
            type: String | Number,
            default: 0,
        },
        loaitb_id: {
            type: String | Number,
            default: 0,
        },
        hdtb_id: {
            type: String | Number,
            default: 0,
        },
        hdkh_id: {
            type: String | Number,
            default: 0,
        },
       

    },
    mounted() {

        const plugin = document.createElement("script");

        const domain = window.location.host.toString();
        if(domain.includes("dev-") || domain.includes("localhost")){
            plugin.setAttribute("src", "/static/js/dev-vnpt-econtract.min.js");
        }else{
            plugin.setAttribute("src", "/static/js/vnpt-econtract.min.js");
        }
      
        plugin.setAttribute("type", 'application/javascript')
        plugin.async = true;
        document.head.appendChild(plugin);

    },
    data() {
        return {
            cboMauHD: {
                options: [],
                value: null
            },
            link: "",
            enableBtnTaoHD: false,
        }
    },
    methods: {
        async showModal() {
            VNPTeContract.init();
            this.$bvModal.show(this.idModal);
            await this.getDanhSachMauHopDong({ kieuld_id: this.kieuld_id, loaitb_id: this.loaitb_id });
            
            //doi hop dong dien tu ky thanh cong
            VNPTeContract.callBackEndSign = function (data) {
                if (data) {
                    if (data.status == "SIGN_SUCCESS") {
                        this.$toast.success("Ký hợp đồng thành công!");
                        this.closeIframe()
                        console.log("Ký thành công!");
                        //call api create hoadon dien tu
                    } else if (data.status == "CANCEL") {
                        console.log("Tắt popup và huỷ phiên làm việc với VNPT eContract!");
                        this.closeIframe()
                    }
                }
            }
        },

        async tsbtnCreatEInvoice_Click() {
            
            try {
                if (!this.cboMauHD.value) {
                    this.$toast.warning("Hãy chọn mẫu hợp đồng!");
                    return
                }
                this.loading(true);
               
                const objContract = await this.createEContract();
           
                if( !objContract) return;

                // this.$toast.success("Tạo hợp đồng thành công!");
                this.$toast.warning("Vui lòng đăng nhập để ký hợp đồng!");
                
                const inputFrame = {
                    accessToken: "",
                    contractGroupId: objContract.contractId ,
                    documentId: objContract.contractId , // objContract.contractId || "",
                    action: "SIGN",
                    skipFirstLogin: "YES"
                }

                this.openIframe(inputFrame);
    
            } catch (error) {
                this.$toast.error(error?.data?.faultString);
                console.log(error)
            } finally {
                this.loading(false);
            }


        },

        closeModal() {
            this.$bvModal.hide(this.idModal);
            this.cboMauHD.options = [];
            this.cboMauHD.value = 0;
        },

        async getDanhSachMauHopDong({ kieuld_id, loaitb_id }) {
            try {

                this.loading(true);
                //  { typeSubID: loaitb_id , typeReqID: kieuld_id }
                const response = await api.getMauHopDong(this.axios, { typeSubID: loaitb_id, typeReqID: kieuld_id });

                if (response && response.data && response.data.errorCode !== 0) {
                    const srtError = response.data.faultString
                    this.$toast.error(srtError);
                    return;
                }
                this.cboMauHD.options = response.data.data?.Template_Group_Ids;
                this.cboMauHD.value = this.cboMauHD.options[0]?.template_id;

            } catch (error) {
                console.log(error)
            } finally {
                this.loading(false);
            }
        },
        async createEContract(){

            const param = {
                hdkh_id: this.hdkh_id,
                template_group_id: this.contractGroupId
            }
            const responseCreateHD = await api.createEContract(this.axios, param);
            const srtError =  apiHelper.getSrtCodeError(responseCreateHD);
            if( srtError!= ""){
                this.$toast.error(srtError);
                return null;
            }
            return responseCreateHD.data.data
        },

        openIframe(data) {
            this.link = VNPTeContract.generateURL(data);
            var iframepopup = document.getElementById("iframe-view");
            iframepopup.style.display = 'block'
        },

        closeIframe() {
            const iframepopup = document.getElementById("iframe-view");
            this.link = ''
            iframepopup.style.display = 'none'
        },

    },
    computed: {
        showIframe() {
            return this.link !== ""
        },
        contractGroupId(){
            const template =  this.cboMauHD.options.find(e=>e.template_id == this.cboMauHD.value );
            return template?.template_group_id || ""
        }

    }
}
</script>
<style scoped>
.iframe-econtract {
    position: fixed;
    top: 0;
    left: 0;
    width: 100%;
    height: 100vh;
    overflow-y: scroll;
    background: white;
}

.iframe-header {
    height: 36px;
    width: 100%;
    display: flex;
    align-items: center;
    border-bottom: 1px solid gray;
}

.iframe-content {
    display: block;
    width: 100%;
    height: 100%;
    overflow-y: scroll;
    background: url("spinner.gif") center center no-repeat;
}

#econtract-wv-iframe {
    border: 0;
    outline: none;
}
.w3-btn-sm {
    padding: 0.4rem 0.75rem;
}
.w3-blue, .w3-hover-blue:hover {
    color: rgb(255, 255, 255) !important;
    background-color: rgb(33, 150, 243) !important;
}
.w3-btn, .w3-button {
    display: inline-block;
    vertical-align: middle;
    color: inherit;
    background-color: inherit;
    text-align: center;
    cursor: pointer;
    border-width: initial;
    border-style: none;
    border-color: initial;
    border-image: initial;
    padding: 8px 16px;
    overflow: hidden;
    text-decoration: none;
    white-space: nowrap;
}
</style>
