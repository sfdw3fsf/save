<template>
    <ejs-dialog
        :enableResize="true"
        :allowDragging="true"
        :header="'Thông tin liên hệ khách hàng'"
        ref="popupCapNhapTTLH"
        showCloseIcon="true"
        width="90%"
        :visible="false"
        :close="closeDialog"
        :overlayClick="closeDialog"
        :isModal="true"
        :animationSettings="animationSettings"
        :target="target"
        :open="dialogOpen"
    >
    <!-- <b-modal
        id="popup-capNhapTTLH"
        size="xl"
        hide-footer
        hide-header
        hide-header-close
        body-class="modal-body p-0"
        @show="onShow"
        @hide="onHide"
    > -->
        <div class="modal-content popup-box">
            <!-- <div class="popup-header">
                <div class="title">
                    <span class="icon one-notepad"></span>Thông tin liên hệ
                    khách hàng
                </div>
                <div
                    class="close -ap icon-close"
                    data-dismiss="modal"
                    @click="$bvModal.hide('popup-capNhapTTLH')"
                ></div>
            </div> -->
            <div class="list-actions-top">
                <ul class="list">
                    <li>
                        <a href="javascript: void(0)" @click="setButton(1)" :class="{ disabled: !btnNhapMoiEnable }" >
                            <span class="icon one-file-plus"></span> Nhập mới
                        </a>
                    </li>
                    <li>
                        <a href="javascript: void(0)" @click="capNhap" :class="{ disabled: !btnGhiLaiEnable }" >
                            <span class="icon one-save"></span>Ghi lại
                        </a>
                    </li>
                    <li>
                        <a href="javascript: void(0)" @click="setButton(0)" :class="{ disabled: !btnHuyEnable }" >
                            <span
                                class="icon ui-1_circle-remove nc-icon-glyph"
                            ></span>
                            Hủy
                        </a>
                    </li>
                    <li>
                        <a href="javascript: void(0)" @click="xoa" :class="{ disabled: !btnXoaEnable }" >
                            <span class="icon one-trash"></span> Xóa
                        </a>
                    </li>
                    <li>
                        <a href="javascript: void(0)" @click="onChapNhan" :class="{ disabled: !btnChapNhanEnable }" >
                            <span
                                class="icon ui-1_check-circle-08 nc-icon-glyph"
                            ></span>
                            Chấp nhận
                        </a>
                    </li>
                </ul>
            </div>
            <div class="popup-body">
                <div class="box-form">
                    <div class="legend-title">Thông tin liên hệ khách hàng</div>
                    <div class="info-row">
                        <div class="key w80">Tên liên hệ<BssRequiredMarker/></div>
                        <div class="value">
                            <input
                                type="text"
                                class="form-control"
                                v-model="txtTenLH"
                                ref="txtTenLienHe"
                                :maxlength="150"
                            />
                        </div>
                    </div>
                    <div class="info-row">
                        <div class="key w80">Địa chỉ<BssRequiredMarker/></div>
                        <div class="value">
                            <div class="input-more-button">
                                <button class="btn" @click="chonDiaChiKH">
                                    <span class="-ap icon-more_horiz"></span>
                                </button>
                                <input
                                    type="text"
                                    v-model="txtDiaChi"
                                    class="form-control font-weight-bold"
                                    :maxlength="250"
                                />
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-sm-4 col-12">
                            <div class="info-row">
                                <div class="key w80">Điện thoại</div>
                                <div class="value">
                                    <input
                                        type="text"
                                        class="form-control"
                                        v-model="txtDienThoai"
                                        :maxlength="100"
                                    />
                                </div>
                            </div>
                        </div>
                        <div class="col-sm-8 col-12">
                            <div class="info-row">
                                <div class="key w80">Fax</div>
                                <div class="value">
                                    <input
                                        type="text"
                                        class="form-control"
                                        v-model="txtFax"
                                        :maxlength="50"
                                    />
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-sm-4 col-12">
                            <div class="info-row">
                                <div class="key w80">Email</div>
                                <div class="value">
                                    <input
                                        type="text"
                                        class="form-control"
                                        v-model="txtEmail"
                                        maxlength="100"
                                        ref="refEmail"
                                    />
                                </div>
                            </div>
                        </div>
                        <div class="col-sm-8 col-12">
                            <div class="info-row">
                                <div class="key w80">Đơn vị</div>
                                <div class="value">
                                    <input
                                        type="text"
                                        class="form-control"
                                        v-model="txtDonVi"
                                        :maxlength="75"
                                    />
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="box-form">
                    <div class="legend-title">Danh sách liên hệ khách hàng</div>
                    <div class="table-content">
                        <DataGrid3
                            :columns='[
                                { fieldName: "STT", headerText: "STT", allowFiltering: false, allowSorting: false, width: 100},
                                { fieldName: "TEN_LH", headerText: "Tên liên hệ", allowFiltering: false, allowSorting: false, },
                                { fieldName: "DIACHI", headerText: "Địa chỉ", allowFiltering: false, allowSorting: false, },
                                { fieldName: "DIENTHOAI", headerText: "Điện thoại	", allowFiltering: false, allowSorting: false, },
                                { fieldName: "SUDUNG", headerText: "Trạng thái", allowFiltering: false, allowSorting: false, },
                                { fieldName: "DANG_KY", headerText: "Đăng ký", allowFiltering: false, allowSorting: false, template:columnDangKyTemplate , cssClass: "center", },
                                { fieldName: "HUY", headerText: "Hủy", allowFiltering: false, allowSorting: false, template:columnHuyTemplate, cssClass: "center", },
                            ]'
                            ref="gridDsLienHe"
                            :dataSource="dsLienHe"
                            :showColumnCheckbox="false"
                            dataKeyField="TTLH_ID"
                            :allowPaging="true"
                            :enablePagingServer="false"
                            :showFilter="false"
                            @selectedRowChanged="onRowFocus"
                        >
                        </DataGrid3>
                    </div>
                </div>
            </div>
        </div>
        <b-modal
            ref="modal-xacnhan-xoa"
            centered
            title="Thông báo"
            size="sm"
            @ok="confirmXoa"
        >
            <span>Bạn chắc chắn muốn xóa người liên hệ không?</span>
            <template #modal-footer="{ ok, cancel }">
                <b-button
                    variant="secondary"
                    style="color: white"
                    @click="cancel()"
                    >Hủy</b-button
                >
                <b-button variant="primary" style="color: white" @click="ok()"
                    >Đồng ý</b-button
                >
            </template>
        </b-modal>
        <DiaChiKhachHangModal ref="diaChiKhachHangModal" :data="dataDiaChiKH" @accept="acceptDiaChiKH" />
    <!-- </b-modal> -->
    </ejs-dialog>
</template>
<style src="../TraCuuKhachHangLon.scss"></style>
<script>
import TraCuuKhachHangLonApi from "./../../api/TraCuuKhachHangLonApi";
import BssRequiredMarker from "@/components/BssRequiredMarker"
import DiaChiKhachHangModal from './../../../contract/setup/SurveyRequest/popups/ChonDiaChi/DiaChiKhachHangModal.vue'
import Vue from 'vue'

export default {
    components: {BssRequiredMarker, DiaChiKhachHangModal},
    props: {
        khachHangId: {
            type: Number,
        },
    },
    created() {},
    data() {
        return {
            target: ".main-wrapper",
            animationSettings: { effect: "Zoom" },
            TTLH_ID: "",
            btnNhapMoiEnable: true,
            btnGhiLaiEnable: true,
            btnHuyEnable: true,
            btnXoaEnable: true,
            btnChapNhanEnable: true,
            txtTenLH: "",
            txtDiaChi: "",
            txtDienThoai: "",
            txtFax: "",
            txtEmail: "",
            txtDonVi: "",
            fieldDsLienHe: [
                {
                    fieldName: "STT",
                    headerText: "STT",
                    allowFiltering: false,
                    allowSorting: false,
                },
                {
                    fieldName: "TEN_LH",
                    headerText: "Tên liên hệ",
                    allowFiltering: false,
                    allowSorting: false,
                },
                {
                    fieldName: "DIACHI",
                    headerText: "Địa chỉ",
                    allowFiltering: false,
                    allowSorting: false,
                },
                {
                    fieldName: "DIENTHOAI",
                    headerText: "Điện thoại	",
                    allowFiltering: false,
                    allowSorting: false,
                },
                {
                    fieldName: "SUDUNG",
                    headerText: "Trạng thái",
                    allowFiltering: false,
                    allowSorting: false,
                },
                {
                    fieldName: "DANG_KY",
                    headerText: "Đăng ký",
                    allowFiltering: false,
                    allowSorting: false,
                    isCheckbox: true,
                    cellCssClass: "center",
                },
                {
                    fieldName: "HUY",
                    headerText: "Hủy",
                    allowFiltering: false,
                    allowSorting: false,
                    isCheckbox: true,
                    cellCssClass: "center",
                },
            ],
            dsLienHe: [],
            ttlhId: 0,
            chapNhan: false,
            editSettings: {
                allowEditing: true,
            },
            columnDangKyTemplate: function () {
                return {
                    template: {
                        template: `
                                    <div class="check-action">
                                        <input
                                            type="checkbox"
                                            class="check"
                                            @change="onChanged"
                                            v-model="data.DANG_KY"/>
                                        <span class="name"></span>
                                    </div>
                                `,
                        data() {
                            return {
                                data: { }
                            }
                        },
                        computed: {
                            parent() {
                                return this.$parent.$parent.$parent.$parent
                            }
                        },
                        methods: {
                            onChanged() {
                                this.parent.updateTrangThai(this.data.TTLH_ID, this.data.DANG_KY, false)
                            }
                        }
                    },

                }
            },
            columnHuyTemplate: function () {
                return {
                    template: {
                        template: `
                                    <div class="check-action">
                                        <input
                                            type="checkbox"
                                            class="check"
                                            @change="onChanged"
                                            v-model="data.HUY"/>
                                        <span class="name"></span>
                                    </div>
                                `,
                        data() {
                            return {
                                data: { }
                            }
                        },
                        computed: {
                            parent() {
                                return this.$parent.$parent.$parent.$parent
                            }
                        },
                        methods: {
                            onChanged() {
                                this.parent.updateTrangThai(this.data.TTLH_ID, false, this.data.HUY)
                            }
                        }

                    },

                }
            },
            dataDiaChiKH: {
                diachi_id:0,
                tinh_id:0,
                quan_id:0,
                phuong_id:0,
                pho_id:0,
                ap_id:0,
                khu_id:0,
                dacdiem_id:0,
                sonha:'',
                diachi:''
            },

        }
    },
    computed: {},
    async mounted() {},

    methods: {
        async dialogOpen() {
            await this.onShow();
        },
        openDialog() {
            this.$refs.popupCapNhapTTLH.show();
        },
        closeDialog() {
            this.$emit("onHideEvent");
            this.$refs.popupCapNhapTTLH.hide();
        },
        async onShow() {
            this.chapNhan = false;
            this.dataDiaChiKH = {
                diachi_id:0,
                tinh_id:0,
                quan_id:0,
                phuong_id:0,
                pho_id:0,
                ap_id:0,
                khu_id:0,
                dacdiem_id:0,
                sonha:'',
                diachi:''
            }
            this.setButton(-1);
            await this.traCuuLienHe();
            this.$nextTick(() => this.$refs.txtTenLienHe.focus());
            // this.$refs.txtTenLienHe.select();
        },
        updateTrangThai(ttlhId, dangky, huy) {
            var element = this.dsLienHe.find(x => x.TTLH_ID == ttlhId);
            if (element != null) {
                var index = this.dsLienHe.indexOf(element)
                this.dsLienHe[index].DANG_KY = dangky
                this.dsLienHe[index].HUY = huy
                if (dangky)
                    this.dsLienHe[index].HUY = false
                if (huy)
                    this.dsLienHe[index].DANG_KY = false
            }
            this.dsLienHe = [...this.dsLienHe]
        },
        setButton(kieu) {
            this.btnNhapMoiEnable = false;
            this.btnGhiLaiEnable = false;
            this.btnHuyEnable = false;
            this.btnXoaEnable = false;

            if (kieu == -1) {
                //Bat dau
                // this.$refs.txtTenLienHe.select();
                this.btnGhiLaiEnable = true;
                this.btnHuyEnable = true;
            }

            if (kieu == 0) {
                //Bat dau
                this.btnNhapMoiEnable = true;
                this.clear();
            }

            if (kieu == 1) {
                //Them moi
                // this.$refs.txtTenLienHe.select();
                this.btnGhiLaiEnable = true;
                this.btnHuyEnable = true;
                this.clear();
            }

            if (kieu == 2) {
                //Huy
                // this.$refs.txtTenLienHe.select();
                this.btnNhapMoiEnable = false;
                this.btnXoaEnable = false;
                this.clear();
            }

            if (kieu == 3) {
                //Edit
                this.btnNhapMoiEnable = true;
                this.btnXoaEnable = true;
                this.btnGhiLaiEnable = true;
                this.btnHuyEnable = true;
            }
        },
        clear() {
            this.txtTenLH = "";
            this.txtDiaChi = "";
            this.txtDienThoai = "";
            this.txtFax = "";
            this.txtEmail = "";
            this.txtDonVi = "";
            this.ttlhId = -1;
        },
        async traCuuLienHe() {
            this.dsLienHe = [];
            TraCuuKhachHangLonApi.getDSLienHe(this.axios, this.khachHangId)
                .then((response) => {
                    if (response.data.error_code === "BSS-00000000") {
                        if (
                            response.data.data &&
                            response.data.data.data &&
                            response.data.data.data.length > 0
                        ) {
                            this.dsLienHe = response.data.data.data;
                            this.dsLienHe.forEach((element, index) => {
                                element.DANG_KY = false;
                                element.HUY = false;
                                element.STT = index + 1;
                                if (element.EMAIL == "null") {
                                    element.EMAIL = ""
                                }
                            });
                        } else {
                            this.$root.toastError(
                                "Không tìm thấy thông tin liên hệ!"
                            );
                        }
                    } else {
                        this.$root.toastError(response.data.message);
                    }
                })
                .catch((reject) => {
                    console.log(reject);
                    if (
                        reject.response &&
                        reject.response.data &&
                        reject.response.data.message
                    )
                        this.$root.toastError(reject.response.data.message);
                    else this.$root.toastError("Có lỗi xảy ra!");
                });
        },
        onRowFocus(dataItem) {
            this.TTLH_ID = dataItem ? dataItem.TTLH_ID : "";
            this.txtTenLH = dataItem ? dataItem.TEN_LH : "";
            this.txtDiaChi = dataItem ? dataItem.DIACHI : "";
            this.txtDienThoai = dataItem ? dataItem.DIENTHOAI : "";
            this.txtFax = dataItem ? dataItem.FAX : "";
            this.txtEmail = dataItem ? dataItem.EMAIL : "";
            this.txtDonVi = dataItem ? dataItem.DONVI : "";
            this.chapNhan = false;
            this.setButton(3);
        },
        onChapNhan() {
            this.chapNhan = true;
            this.$emit("onChapNhanEvent")
            this.closeDialog()
            // this.$bvModal.hide("popup-capNhapTTLH");
        },
        output() {
            return {
                ten_lh: this.chapNhan ? this.txtTenLH.trim() : "",
                diachi_lh: this.chapNhan ? this.txtDiaChi.trim() : "",
                dienthoai: this.chapNhan ? this.txtDienThoai.trim() : "",
                fax: this.chapNhan ? this.txtFax.trim() : "",
                email: this.chapNhan ? this.txtEmail.trim() : "",
                donvi: this.chapNhan ? this.txtDonVi.trim() : "",
            };
        },
        async capNhap() {
            if (!this.txtTenLH || this.txtTenLH == "") {
                this.$toast.error('Bạn chưa nhập "Tên người liên hệ"!');
                return;
            }
            if (!this.txtDiaChi || this.txtDiaChi == "") {
                this.$toast.error('Bạn chưa nhập "Địa chỉ người liên hệ"!');
                return;
            }

            if((!/^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:\.[a-zA-Z0-9-]+)*$/.test(this.txtEmail)) && this.txtEmail.length > 0) {
                this.$toast.error("Email không đúng định dạng!");
                this.$nextTick(() => this.$refs.refEmail.focus());
                return false;
            }

            let postData = {
                // khachHangId: this.khachHangId,
                tenLh: this.txtTenLH ? this.txtTenLH.trim() : '',
                diaChi: this.txtDiaChi ? this.txtDiaChi.trim() : '',
                dienThoai: this.txtDienThoai ? this.txtDienThoai.trim() : '',
                email: this.txtEmail ? this.txtEmail.trim() : '',
                fax: this.txtFax ? this.txtFax.trim() : '',
                donVi: this.txtDonVi ? this.txtDonVi.trim() : '',
                khachHangId: this.khachHangId,
                ttkhId: 1, //THONGTIN_KH.KHACHHANG
                ttlhId: this.dsLienHe.length > 0 ? this.$refs.gridDsLienHe.getSelectedRecords()[0].TTLH_ID : '',
            };

            let dsDangKy = []
            let dsHuy = []
            this.dsLienHe.forEach(async (lienHe, index) => {
                if (lienHe.DANG_KY) {
                    dsDangKy.push(lienHe.TTLH_ID)
                }

                if (lienHe.HUY) {
                    dsHuy.push(lienHe.TTLH_ID)
                }

            });
            if (dsDangKy.length > 0) {
                postData.lstDangKy = dsDangKy.join()
            }
            if (dsHuy.length > 0) {
                postData.lstHuy = dsHuy.join()
            }

            if (!this.btnNhapMoiEnable) {
                postData.ttlhId = 0;

            }


            try
            {
                this.loading(true);
                await this.capNhapThongTinLienHe(postData);
            }
            catch (e)
            {
                console.log(e);
                this.$root.toastError("Cập nhập thất bại: " + e);
            } finally
            {
                this.loading(false);
            }

        },
        async capNhapThongTinLienHe(postData) {
            await TraCuuKhachHangLonApi.themSuaXoaTTLH(this.axios, postData)
                .then(async (response) => {
                    if (response.data.error_code === "BSS-00000000") {
                        await this.traCuuLienHe();
                        this.$root.toastSuccess("Cập nhật thông tin liên hệ thành công!");
                    } else {
                        this.$root.toastError(response.data.message);
                    }
                })
                .catch((reject) => {
                    console.log(reject)
                    if (reject.data.data && reject.data.data.length > 0) {
                        this.$root.toastError(reject.data.data.map(x => x.message).join())
                    }
                    else {
                        this.$root.toastError(reject.data.message)
                    }
                });
        },
        xoa() {
            this.$refs["modal-xacnhan-xoa"].show();
        },
        confirmXoa() {
            this.loading(true);
            TraCuuKhachHangLonApi.xoaLienHe(this.axios, this.TTLH_ID)
                .then(async (response) => {
                    this.loading(false);
                    if (response.data.error_code === "BSS-00000000") {
                        this.$root.toastSuccess(
                            "Xóa thông tin liên hệ thành công"
                        );
                        await this.traCuuLienHe();
                    } else {
                        this.$root.toastError(response.data.message);
                    }
                })
                .catch((reject) => {
                    this.loading(false);
                    if (reject.data.data && reject.data.data.length > 0) {
                        this.$root.toastError('Có lỗi xảy ra: ' + reject.data.data.map(x => x.message).join())
                    }
                    else {
                        this.$root.toastError('Có lỗi xảy ra: ' + reject.data.message)
                    }
                });
        },
        onHide() {
            this.$emit("onHideEvent");
        },

        chonDiaChiKH(){
            //địa chỉ khách hàng
            this.$refs.diaChiKhachHangModal.showModal()
        },

        acceptDiaChiKH(data){
            //handle
            this.txtDiaChi = data.diachi
            Object.assign(this.dataDiaChiKH, data)
            // this.$emit('callbackChonDiChiKH', this.dataDiaChiKH)
        },

    },
};
</script>
