<template src="./TaoCongViecCSKH.html"></template>
<style src="./TaoCongViecCSKH.scss"></style>
<script>
import { mapActions, mapState, mapGetters } from 'vuex'
import TaoCongViecCSKHApi from "./../api/TaoCongViecCSKHApi";
import YesNoModal from "../utils/YesNoModal.vue";
import BssRequiredMarker from '@/components/BssRequiredMarker';
import { required, maxLength } from "vuelidate/lib/validators";

export default {
    name:"TaoCongViecCSKH",
    components:{
        BssRequiredMarker,
        confirmPopup: YesNoModal,
    },
    data() {
        return {
            header: {
                title: "TẠO CÔNG VIỆC CHĂM SÓC KHÁCH HÀNG",
                list: [
                //   { name: "Trang chủ", link: { name: "Ui.cards" } },
                {
                    name: "Chăm sóc khách hàng",
                    link: { name: "Ui.buttons" },
                },
                {
                    name: "Quản lý chính sách CSKH",
                    link: { name: "Ui.buttons" },
                },
                {
                    name: "Tạo công việc CSKH",
                    link: { name: "Ui.buttons" },
                },
                ],
            },
            dsCongViecCSKHFields: [
                {
                    fieldName: "STT",
                    headerText: "STT",
                    allowFiltering: true,
                    allowSorting: false,
                    width: 60
                },
                {
                    fieldName: "NOIDUNG",
                    headerText: "Nội dung",
                    allowFiltering: true,
                    allowSorting: false,
                },
                {
                    fieldName: "NGUOITAO",
                    headerText: "Người tạo",
                    allowFiltering: true,
                    allowSorting: false,
                },
                {
                    fieldName: "NGAYTAO",
                    headerText: "Ngày tạo",
                    allowFiltering: true,
                    allowSorting: false,
                },
                {
                    fieldName: "TRANGTHAI_CV",
                    headerText: "Trạng thái",
                    allowFiltering: true,
                    allowSorting: false,
                }
            ],
            dsChuongTrinhCSKHSelected: "",
            noiDungInputData: "",
            congViecIdData: 0,

            btnStatus: {
                btnNhapMoiEnabled: false,
                btnGhiLaiEnabled: false,
                btnXoaEnabled: false,
                btnHuyBoEnabled: false,
            },
        }
    },

    async created(){
        this.clearData();
    },

    async mounted(){
		this.$nextTick(() => this.$refs.noiDungInputData.focus());
        this.loading(true);
        // this.setButtonStatus(-1);
        await this.hienThiDsChuongTrinhCSKH();
        if (this.dsChuongTrinhCSKH.length > 0) {
            this.dsChuongTrinhCSKHSelected = this.dsChuongTrinhCSKH[0].id
        }
        await this.hienThiDsCongViecCSKH(this.dsChuongTrinhCSKHSelected);
    },

    computed:{
        ...mapState("taocongvieccskh", [
            "dsChuongTrinhCSKH",
            "dsCongViecCSKH"
        ]),
        ...mapGetters("taocongvieccskh", ["dsCongViecCSKHGetter","dsChuongTrinhCSKHGetter"]),
    },

	watch:{},
    validations: {
            noiDungInputData: {
                required,
                maxLength: maxLength(500)
            },
    },

	methods:{
       ...mapActions("taocongvieccskh",[
            "getDsChuongTrinhCSKH",
            "getDsCongViecCSKH",
            "clearData"
           ]),

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
            } else return true;
        },

        async hienThiDsChuongTrinhCSKH() {
            try {
                let ctCskhPost = {
                    kieu: 1,
                    pageNo: 1,
                    pageSize: 1000
                };
                await this.getDsChuongTrinhCSKH(ctCskhPost);
            } catch (ex) {
            } finally {
                this.loading(false)
            }
        },

        async onChuongTrinhCSKHChange() {
            this.clearParam();
            this.loading(true);
            await this.hienThiDsCongViecCSKH(this.dsChuongTrinhCSKHSelected);
            this.loading(false);
        },

        async hienThiDsCongViecCSKH(chuongTrinhCskhIdParam) {
            this.setButtonStatus(-1);
            let cvCskhPost = {
                ctcsKhId: chuongTrinhCskhIdParam,
                pageNo: 1,
                pageSize: 1000
            };
            try {
                await this.getDsCongViecCSKH(cvCskhPost);
            }
            catch (ex) {
            }
            finally {
                this.loading(false)
            }
        },

        async gridCongViecCSKH_selectedRowChanged(dataItem) {
            if(dataItem!=null) {
                this.setButtonStatus(3);
                this.noiDungInputData =  dataItem.NOIDUNG
                this.congViecIdData =  dataItem.CONGVIEC_ID
            }
        },

        onNhapMoiClick() {
            this.setButtonStatus(1);
        },

        clearParam() {
            this.congViecIdData=0;
            this.noiDungInputData='';
        },

        async onGhiLaiClick() {
            if (!this.validateData())
                return this.$refs.noiDungInputData.focus();
            this.noiDungInputData = this.noiDungInputData.trim();
            // if(this.noiDungInputData.length==0) {
            //     this.$root.toastError("Chưa nhập nội dung")
            //     return;
            // }
            // if(this.noiDungInputData.length>5) {
            //     this.$root.toastError("Không nhập quá 500 ký tự")
            //     return;
            // }
            this.loading(true);
            try {
                let capNhatCSKHData = {
                    congViecId: this.congViecIdData,
                    nguoiTaoId: this.$auth.getNhanVienID(),
                    // nguoiTaoId: 12219,
                    noiDung: this.noiDungInputData,
                    ctcsKhId: this.dsChuongTrinhCSKHSelected
                };
                await TaoCongViecCSKHApi.capNhapCongViecCSKH(this.axios, capNhatCSKHData)
                .then((response) => {
                    if (response.data.error_code == "BSS-00000000")
                    this.$root.toastSuccess(
                        // this.btnStatus.btnNhapMoiEnabled
                         "Thành công!"
                    );
                    else
                    this.$root.toastError(
                        "Xảy ra lỗi trong quá trình cập nhập thông tin: " +
                        response.data.message
                    );
                })
                .catch((reject) => {
                    this.$root.toastError(
                    reject.response.data.message || reject.response.data.error
                    );
                });
            } catch (e) {
            } finally {
                this.loading(false);
            }
            await this.hienThiDsCongViecCSKH(this.dsChuongTrinhCSKHSelected);
        },

        async onXoaClick() {
            this.$bvModal.show("xoaConfirm");
        },

        async onXoaConfirm() {
            let xoaPost = {
                congViecId: this.congViecIdData,
                nguoiXoaId: this.$auth.getNhanVienID()
            };
            try {
                this.loading(true);
                await TaoCongViecCSKHApi.xoaCongViecCSKH(this.axios, xoaPost)
                .then(async (response) => {
                    if (response.data.error_code == "BSS-00000000") {
                    this.$root.toastSuccess("Xóa công việc CSKH thành công !");
                    await this.hienThiDsCongViecCSKH(this.dsChuongTrinhCSKHSelected);
                    } else
                    this.$root.toastError(
                        "Không thể xóa bản ghi này: " + response.data.message
                    );
                })
                .catch((reject) => {
                    this.$root.toastError(
                    reject.response.data.message || reject.response.data.error
                    );
                });
            } catch (e) {
            } finally {
                this.loading(false);
            }
        },

        onHuyBoClick() {
            this.setButtonStatus(0);
        },


        setButtonStatus(kieu) {
            console.log("setButtonStatus", kieu);
            this.btnStatus.btnNhapMoiEnabled = false;
            this.btnStatus.btnGhiLaiEnabled = false;
            this.btnStatus.btnXoaEnabled = false;
            this.btnStatus.btnHuyBoEnabled = false;

            switch (kieu) {
                case -1: //Bat dau
                    this.btnStatus.btnGhiLaiEnabled = true;
                    this.btnStatus.btnHuyBoEnabled = true;
                    break;
                case 0: //Bat dau
                    this.btnStatus.btnNhapMoiEnabled = true;
                    this.clearParam();
                    this.$v.$reset();
                    this.$refs.noiDungInputData.focus();
                    break;
                case 1: //Them moi
                    this.btnStatus.btnGhiLaiEnabled = true;
                    this.btnStatus.btnHuyBoEnabled = true;
                    this.clearParam();
                    this.$v.$reset();
                    this.$refs.noiDungInputData.focus();
                    break;
                case 2: //Huy
                    this.btnStatus.btnNhapMoiEnabled = true;
                    this.btnStatus.btnXoaEnabled = true;
                    this.clearParam();
                    break;
                case 3: //Edit
                    this.btnStatus.btnNhapMoiEnabled = true;
                    this.btnStatus.btnGhiLaiEnabled = true;
                    this.btnStatus.btnHuyBoEnabled = true;
                    this.btnStatus.btnXoaEnabled = true;
                break;
            }
        },
    }
}

</script>
