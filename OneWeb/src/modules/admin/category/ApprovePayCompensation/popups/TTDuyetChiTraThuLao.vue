<template>
<div class="modal-content popup-box">
    <div class="popup-header">
        <div class="title">
            <span class="icon one-notepad"></span> Thông tin duyệt chi trả thù lao
        </div>
        <div class="close -ap icon-close" data-dismiss="modal" @click="closeModal"></div>
    </div>

    <div class="list-actions-top">
        <ul class="list" >
            <li v-if="Number(luotChiTraType) == 1">
                <a href="javascript:void(0)" @click="onDuyetAndChiTra" :class="luotChiTraType == 2 ? 'disabled' : ''">
                    <span class="icon one-file-check3"></span>Duyệt & Chi trả
                </a>
            </li>

            <li v-if="Number(luotChiTraType) == 1">
                <a href="javascript:void(0)" @click="onHuyChiTra" :class="luotChiTraType == 2 ? 'disabled' : ''">
                    <span class="icon nc-icon-glyph ui-1_circle-remove"></span>Hủy
                </a>
            </li>

            <li>
                <a href="javascript:void(0)" @click="closeModal">
                    <span class="icon nc-icon-glyph ui-1_circle-remove"></span>Thoát
                </a>
            </li>
        </ul>
    </div>
    <div class="popup-body">
        <div class="box-form" v-if="Number(luotChiTraType) == 1">
            <div class="legend-title">Nội dung phê duyệt</div>
            <div class="info-row">
                <div class="key w70">Nội dung</div>
                <div class="value">
                    <textarea name="" class="form-control" style="height: 100px; resize: none" v-model="noiDungInputData" ref="noiDungInputData" :class="{ 'is-invalid': $v.noiDungInputData.$error }"></textarea>
                </div>
            </div>
        </div>
        <div class="box-form">
            <div class="legend-title">Danh sách nhân viên được chi trả</div>
            <div class="table-content" style="height: 450px">
                <DataGrid ref="tableNhanVienDuocChiTra" class="table-result" v-bind:columns="[
              {
                fieldName: 'ma_nv',
                headerText: 'Mã nhân viên',
                allowFiltering: true,
                allowSorting: true,
                width: '100',
              },
              {
                fieldName: 'ten_nv',
                headerText: 'Tên nhân viên',
                allowFiltering: true,
                allowSorting: true,
                width: '100',
              },
              {
                fieldName: 'so_vi',
                headerText: 'Số ví',
                allowFiltering: true,
                allowSorting: true,
                width: '100',
              },
              {
                fieldName: 'so_tien',
                headerText: 'Số tiền',
                allowFiltering: true,
                allowSorting: true,
                width: '100',
              },
              {
                fieldName: 'noi_dung',
                headerText: 'Nội dung',
                allowFiltering: true,
                allowSorting: true,
                width: '100',
              },
              {
                fieldName: 'trangthai',
                headerText: 'Trạng thái',
                allowFiltering: true,
                allowSorting: true,
                width: '100',
              },
              {
                fieldName: 'trangthai_ct',
                headerText: 'Trạng thái CT',
                allowFiltering: true,
                allowSorting: true,
                width: '100',
              },
            ]" v-bind:dataSource="tableNhanVienDuocChiTraDataSource" :allowPaging="true" :enablePagingServer="false">
                </DataGrid>
            </div>
        </div>
    </div>
</div>
</template>

<style scoped>
.control-section {
    min-height: 350px;
    max-width: 840px;
    margin: 10px;
}

.e-popup.e-popup-open.e-dialog {
    max-width: 80%;
    max-height: 85% !important;
    margin-top: 50px !important;
    margin-bottom: 50px !important;
}

.disabled {
    cursor: not-allowed;
    color: gray;
}
</style>

<script>
import {
    required,
    maxLength
} from "vuelidate/lib/validators";
import ApprovePayCompensationApi from "../../ApprovePayCompensation/ApprovePayCompensationApi";

export default {
    name: "tTDuyetChiTraThuLao",
    props: {
        duyetTraThuLaoHopDongID: Number,
        luotChiTraType: Number,
    },
    validations: {
        noiDungInputData: {
            required: "true",
            maxLength: 1000,
        },
    },
    watch: {},
    validations: {
        noiDungInputData: {
            required,
            maxLength: maxLength(1000),
        },
    },
    data() {
        return {
            target: "#target",
            width: "335px",
            isModal: true,
            animationSettings: {
                effect: "None",
            },
            noiDungInputData: "",
            tableNhanVienDuocChiTraDataSource: [],
            curLuotChiTra: 0,
        };
    },
    mounted() {
        this.curLuotChiTra = this.duyetTraThuLaoHopDongID;
        this.Lay_DS_NhanVien();
            
        this.luotChiTraType = Number(luotChiTraType);
    },
    computed: {},
    methods: {
        hideModal() {
            this.$emit("form-close", null);
        },

        closeModal() {
          this.$bvModal.hide("thongTinChiTietDuyetChiTraThuLao")
        },
        showModal() {
            
        },
        close() {},
        async onDuyetAndChiTra() {
            try {
                if (!this.validateData()) return this.$refs.noiDungInputData.focus();
                this.noiDungInputData = this.noiDungInputData.trim();
                this.loading(true);

                let duyetChiTraParams = {
                    vluotcttl_id: this.duyetTraThuLaoHopDongID,
                    vnhanvien_pd_id: this.$root.token.getNhanVienID(),
                    vttluot_id: 1,
                    vghichu: this.noiDungInputData.trim(),
                };

                await ApprovePayCompensationApi.duyetVaChiTraApi(
                        this.axios,
                        JSON.stringify(duyetChiTraParams)
                    )
                    .then((response) => {
                        if (response.data.error_code == "BSS-00000000") {
                            this.$root.toastSuccess("Thành công!");
                        } else {
                            this.$root.toastError(
                                "Xảy ra lỗi trong quá trình cập nhập thông tin: " +
                                response.data.message
                            );
                        }
                    })
                    .catch((reject) => {
                        this.$root.toastError(
                            reject.response.data.message || reject.response.data.error
                        );
                    });
            } catch (e) {} finally {
                this.loading(false);
            }
        },
        async onHuyChiTra() {
            
            try {
                if (!this.validateData()) return this.$refs.noiDungInputData.focus();
                this.noiDungInputData = this.noiDungInputData.trim();
                this.loading(true);

                let huyChiTraParams = {
                    vluotcttl_id: this.duyetTraThuLaoHopDongID,
                    vnhanvien_pd_id: this.$root.token.getNhanVienID(),
                    vttluot_id: 3,
                    vghichu: this.noiDungInputData.trim(),
                };f

                await ApprovePayCompensationApi.huyChiTraApi(
                        this.axios,
                        JSON.stringify(huyChiTraParams)
                    )
                    .then((response) => {
                        if (response.data.error_code == "BSS-00000000") {
                            this.$root.toastSuccess("Thành công!");
                        } else {
                            this.$root.toastError(
                                "Xảy ra lỗi trong quá trình cập nhập thông tin: " +
                                response.data.message
                            );
                        }
                    })
                    .catch((reject) => {
                        this.$root.toastError(
                            reject.response.data.message || reject.response.data.error
                        );
                    });
            } catch (e) {} finally {
                this.loading(false);
            }
        },
        validateData() {
            this.$v.$touch();
            let errorMessage = [];

            if (!this.$v.noiDungInputData.required) {
                errorMessage.push("Trường nội dung bắt buộc nhập.");
            } else if (!this.$v.noiDungInputData.maxLength) {
                errorMessage.push("Trường nội dung dữ liệu vướt quá độ dài cho phép.");
            }
            if (errorMessage.length > 0) {
                this.$toast.error(errorMessage.join("\n"));

                return false;
            } else {
                return true;
            }
        },

        async Lay_DS_NhanVien() {
            let getNhanVienByPhieuParams = {
                vluotcttl_id: this.duyetTraThuLaoHopDongID,
            };

            try {
                let response = await this.axios.post(
                    "web-quantri/duyet-chitra-thulao/lay_chitiet_luot_cttl",
                    getNhanVienByPhieuParams
                );

                this.tableNhanVienDuocChiTraDataSource = response.data.data.ds2;
            } catch (err) {}
        },
    },
};
</script>
