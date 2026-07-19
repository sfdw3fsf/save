<template>
    <ejs-dialog
        :enableResize="true"
        :allowDragging="true"
        ref="frmSendMail"
        :header="'Gửi Email cho Khách Hàng'"
        :animationSettings="animationSettings"
        showCloseIcon="true"
        width="90%"
        :visible="false"
        :target="target"
        :open="dialogOpen"
        :close="closeDialog"
        :overlayClick="closeDialog"
        :isModal="true"
    >
        <div class="modal-content popup-box">
            <!-- <div class="popup-header">
                <div class="title">
                    <span class="icon one-notepad"></span>Gửi Email cho Khách Hàng
                </div>
                <div class="close -ap icon-close" data-dismiss="modal"></div>
            </div> -->
            <div class="list-actions-top">
                <ul class="list">
                    <li>
                        <a href="javascript: void(0)" @click="onGuiEmailClicked">
                            <span class="icon one-email"></span> Gửi Email
                        </a>
                    </li>
                    <li>
                        <a href="javascript: void(0)" @click="closeDialog">
                            <span
                                class="icon nc-icon-glyph ui-1_circle-remove"
                            ></span>
                            Hủy bỏ
                        </a>
                    </li>
                </ul>
            </div>
            <div class="popup-body">
                <div class="box-form">
                     <div class="info-row">
                        <div class="key w100">File gửi</div>
                         <div class="value">
                            <ejs-multiselect selectAllText="Chọn tất cả"
                                unSelectAllText="Bỏ chọn tất cả" v-model="optEmail" locale='vi-VN'
                                :dataSource='dsEmail' :allowFiltering='true'
                                 :allowCustomValue='true'
                                :fields="{value:'id', text: 'text'}" :showSelectAll="true"
                                :showDropDownIcon="true"
                                @customValueSelection="addCustomValue">
                            </ejs-multiselect>
                        </div>
                    </div>
                    <div class="info-row">
                        <div class="key w100">File gửi</div>
                        <div class="value">
                            <input type="file" class="form-control" />
                        </div>
                    </div>
                    <div class="info-row">
                        <div class="key w100">Tiêu đề</div>
                        <div class="value">
                            <input type="text" class="form-control" />
                        </div>
                    </div>
                    <div class="info-row">
                        <div class="key w100">Nội dung</div>
                        <div class="value">
                            <textarea
                                class="form-control"
                                style="height: 120px;"
                            ></textarea>
                        </div>
                    </div>
                    <div class="info-row">
                        <div class="key w100">Trạng thái</div>
                        <div class="value">
                            <input type="text" class="form-control" />
                        </div>
                    </div>
                    <div class="info-row">
                        <div class="key w100"></div>
                        <div class="value">
                            <textarea
                                class="form-control"
                                style="height: 120px;"
                            ></textarea>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </ejs-dialog>
</template>
<script>
import { mapActions, mapState } from "vuex";
import { MultiSelectPlugin, CheckBoxSelection } from "@syncfusion/ej2-vue-dropdowns"
import { L10n } from "@syncfusion/ej2-base";
L10n.load({
    "vi-VN": {
        dropdowns: {
            noRecordsTemplate: "Không có dữ liệu",
            actionFailureTemplate: "",
        },
    },
});
export default {
    name: "SendMail",
    components: {},
    provide: {
            multiselect: [CheckBoxSelection]
        },
    props: {
        emails: {
            type: Array,
            default: []
        }
    },
    data() {
        return {
            target: ".main-wrapper",
            animationSettings: { effect: "Zoom" },

            pCauHinhSMTP: null,

            pTxtServer: "",
            pTxtUsername: "",
            pTxtPassword: "",
            pTxtPort: "",
            pTxtAlias: "",
            pTxtDiaChiLienHe: "",
            pTxtMailLienHe: "",
            pTxtSDTLienHe: "",

            optEmail: '',

            API_SUCCESS: "BSS-00000000",
            dsEmail: [],
            dsCustomEmail: [],
        };
    },
    created() {},
    computed: {
        ...mapState("reg", [
            "SMTP_CSKH_SERVER",
            "SMTP_CSKH_USERNAME",
            "SMTP_CSKH_PASSWORD",
            "SMTP_CSKH_PORT",
            "SMTP_ALIAS",
            "SMTP_CSKH_DCLH",
            "SMTP_CSKH_EMAIL",
            "SMTP_CSKH_SDT"
        ])
    },
    mounted() {},
    methods: {
        ...mapActions("reg", ["setSMTPInfor"]),
        async dialogOpen() {
             await this.onShow();
        },
        openDialog() {
            this.$refs.frmSendMail.show();
        },
        closeDialog() {
            this.$refs.frmSendMail.hide();
        },
        async onShow() {
            console.log('aaa')
            this.dsEmail = [...this.emails]
            this.dsCustomEmail= []
        },
        addCustomValue(item) {
            this.dsCustomEmail.push({id: item.newData.id, text: item.newData.text})
            console.log(item)
        },
        async onGuiEmailClicked() {
            try {
                this.$root.showLoading(true)
            }
            catch (e) {
                console.log(e)
            }
            finally {
                this.$root.showLoading(false)
                this.$root.toastError('Có lỗi xảy ra')
            }
        }
    }
};
</script>
