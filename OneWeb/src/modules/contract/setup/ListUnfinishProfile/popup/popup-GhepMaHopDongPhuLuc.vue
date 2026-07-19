<template>
    <div class="modal-content popup-box">
        <div class="popup-body">
            <div class="box-form">
                <div class="box-center-button">
                    <div class="box-col">
                        <div class="title-bg-main">
                            <span class="title">Hợp đồng - Phụ lục cần ghép</span>
                        </div>
                        <div class="info-row">
                            <div class="key w60">Mã GD</div>
                            <div class="value">
                                <div class="input-more-button">
                                    <button class="btn" @click="txt_TraCuu_MaGD_Ghep">
                                        <span class="-ap icon-more_horiz"></span>
                                    </button>
                                    <input type="text" class="form-control highlight" v-model="ma_gd_ghep" v-on:keyup.enter="onEnter_MaGDGhep">
                                </div>
                            </div>
                        </div>
                        <div class="note red fw6" id="html_hdpl"></div>

                    </div>
                    <div class="actions">
                        <button class="btn btn-second" :disabled="disable_ghep" @click="btnLink_Click">
                            Ghép
                        </button>
                    </div>
                    <div class="box-col">
                        <div class="title-bg-main">
                            <span class="title">Hợp đồng yêu cầu</span>
                        </div>
                        <div class="info-row">
                            <div class="key w60">Mã GD</div>
                            <div class="value">
                                <div class="input-more-button">
                                    <button class="btn" @click="txt_TraCuu_MaGD_TuVan" :disabled="!enableHDYC">
                                        <span class="-ap icon-more_horiz"></span>
                                    </button>
                                    <input type="text" class="form-control highlight" v-model="ma_gd_yc" v-on:keyup.enter="onEnter_MaGDYeuCau" :disabled="!enableHDYC">
                                </div>
                            </div>
                        </div>
                        <div class="article text-warning fw6" id="html_yc"></div>
                    </div>
                </div>
            </div>     
        </div>

        <SearchContractModal ref="ppSearchContract" @accept="acceptSearch" 
            :loai_hd_id="Number(loaiHD)" :dichvu_id="0" :ngay_yc="ngay_yc"
            :trangthai_hd="0" />
    </div>   
</template>
<script>
import api from "@/modules/contract/setup/ListUnfinishProfile/api/index.js";
import SearchContractModal from "@/modules/contract/setup/HandoverTicket/components/SearchContractModal.vue"
import moment from "moment";
export default {
    name:'popup-GhepMaHopDongPhuLuc',
    components:{
        SearchContractModal
    },
    data() {
        return {
            loaiHD: "0",
            ma_gd_ghep: "",
            ma_gd_ghep_tag: "",
            ma_gd_yc: "",
            ma_gd_yc_tag: "",
            disable_ghep: true,
            enableHDYC: false,
            ngay_yc: moment(new Date()).format("DD/MM/yyyy"),
        }
    },
    computed: {

    },
    methods: {
        init() {
            this.ma_gd_ghep = "";
            this.ma_gd_ghep_tag = "";
            this.ma_gd_yc = "";
            this.ma_gd_yc_tag = "",
            this.disable_ghep= true;
            this.enableHDYC = false;
            $("#html_hdpl").html("");
            $("#html_yc").html("");
        },
        txt_TraCuu_MaGD_Ghep() {
            this.loaiHD = "0";
            this.$refs.ppSearchContract.showModal();             
        },
        txt_TraCuu_MaGD_TuVan() {
            this.loaiHD = "38";                         // LoaiHopDong.TUVAN_DV = 38
            this.$refs.ppSearchContract.showModal(); 
        },
        async onEnter_MaGDGhep() {
            await this.HienThi_HDPL_Ghep(this.ma_gd_ghep);
        },
        async onEnter_MaGDYeuCau() {
            await this.HienThi_HD_TuVan(this.ma_gd_yc);
        },
        async acceptSearch(item) {
            if (item.ma_gd == undefined || item.ma_gd == null || item.ma_gd == "") {
                this.$toast.error("Bạn chưa chọn mã giao dịch cần ghép");
                return;
            }
            if (this.loaiHD == "0") {
                await this.HienThi_HDPL_Ghep(item.ma_gd);
            } else {
                await this.HienThi_HD_TuVan(item.ma_gd);
            }
        },
        async HienThi_HDPL_Ghep(vMaGD) {          
            try {
                this.enableHDYC = true
                let dtInfo = await this.hienthi_hdpl_ghep(vMaGD);
                if (dtInfo.length > 0) {
                    this.$toast.error("Hợp đồng này đã được gán cho mã giao dịch [" + dtInfo[0].ma_gd + "]");
                    await this.HienThi_HD_TuVan(dtInfo[0].ma_gd);
                    let html_yc = "<p>Hợp đồng đã được ghép, bạn đang ghép lại mã giao dịch.</p>" + $("#html_yc").html();
                    $("#html_yc").html(html_yc);
                }

                dtInfo = await this.thongtin_hdpl(vMaGD);
                if (dtInfo.length > 0) {
                    let html_hdpl = "<p>Hợp đồng: " + (dtInfo[0].kieuld != null ? dtInfo[0].kieuld : "") + "</p>";
                    html_hdpl += "<p>Thuê bao: " + (dtInfo[0].ten_tb != null ? dtInfo[0].ten_tb : "") + "</p>";
                    html_hdpl += "<p>Địa chỉ: " + (dtInfo[0].diachi_tb != null ? dtInfo[0].diachi_tb : "") + "</p>";
                    html_hdpl += "<p>Tạo ngày: " + (dtInfo[0].ngay_ins != null ? dtInfo[0].ngay_ins : "") + "</p>";
                    $("#html_hdpl").html(html_hdpl);

                    this.ma_gd_ghep = vMaGD;
                    this.ma_gd_ghep_tag = dtInfo[0].hdkh_id;

                    this.disable_ghep = false;
                    let checkResult = await this.kiemtra_ghepma(dtInfo[0].hdkh_id);
                    if (checkResult.startsWith("OK")) {
                        this.enableHDYC = true
                        $("#html_yc").html("<p>Hãy chọn đơn hàng yêu cầu để gán</p>");
                    } else {
                        $("#html_hdpl").html("<p>" + checkResult + "</p>");
                        this.disable_ghep = true;
                    }
                } else {
                    this.$toast.error("Không tìm thấy mã giao dịch của hợp đồng tư vấn");
                    this.init()
                }  
            } catch (ex) {
                this.$toast.error(ex);
            }
        },
        async HienThi_HD_TuVan(vMaGD) {
            let dtInfo = await this.thongtin_hdpl(vMaGD);
            if (dtInfo.length == 0) {
                this.$toast.error("Không tìm thấy mã giao dịch của hợp đồng tư vấn");
                return;
            }
         
            let html_yc = "<p>Hợp đồng: " + (dtInfo[0].kieuld != null ? dtInfo[0].kieuld : "") + "</p>";
            html_yc += "<p>Thuê bao: " + (dtInfo[0].ten_tb != null ? dtInfo[0].ten_tb : "") + "</p>";
            html_yc += "<p>Địa chỉ: " + (dtInfo[0].diachi_tb != null ? dtInfo[0].diachi_tb : "") + "</p>";
            html_yc += "<p>Tạo ngày: " + (dtInfo[0].ngay_ins != null ? dtInfo[0].ngay_ins : "") + "</p>";
            $("#html_yc").html(html_yc);
            
            this.ma_gd_yc = vMaGD;
            this.ma_gd_yc_tag = dtInfo[0].hdkh_id;
        },
        async btnLink_Click() {
            try {             
                if (this.ma_gd_ghep_tag == null || this.ma_gd_yc_tag == null || this.ma_gd_ghep_tag == "" || this.ma_gd_yc_tag == "") {
                    this.$toast.error("Hãy chọn mã giao dịch cần ghép trước");
                    return;
                }

                this.$bvModal.msgBoxConfirm('Tiến hành ghép mã ?', {
                    title: '', size: 'sm', okTitle: 'Đồng ý', cancelTitle: 'Hủy',
                }).then(async v => {
                    if (v) {
                        this.loading(true);
                        let resGhep = await this.update_ghepma_thanhcong(Number(this.ma_gd_yc_tag), Number(this.ma_gd_ghep_tag));
                        if (resGhep == "OK") {
                            this.$toast.success("Ghép mã thành công")
                            this.$emit("ghepma_thanhcong")
                        } else {
                            this.$toast.error(resGhep)
                        }                   
                        this.loading(false);
                    }
                });
            } catch (ex) {
                this.$toast.error(ex);
            }
        },
        async hienthi_hdpl_ghep(vMaGD) {
            let result = [];
            try {
                let params = {
                    ma_gd: vMaGD,
                }
                let response = await api.ur_009_hienthi_hdpl_ghep(this.axios, params);
                if (response && response.data && response.data.error_code == 'BSS-00000000' && response.data.data) {
                    result = response.data.data;
                }
            } catch (e) {
                console.log(e)
            }
            return result;
        },
        async thongtin_hdpl(vMaGD) {
            let result = [];
            try {
                let params = {
                    ma_gd: vMaGD,
                }
                let response = await api.ur_009_thongtin_hdpl(this.axios, params);     
                console.log(response)          
                if (response && response.data && response.data.error_code == 'BSS-00000000' && response.data.data) {
                    result = response.data.data;
                }
            } catch (e) {
                console.log(e)
            }
            return result;
        },
        async kiemtra_ghepma(vHdkhID) {
            let result = "";
            try {
                let params = {
                    hdkh_id: vHdkhID,
                    nhanvien_id: this.$root.token.getNhanVienID(),
                }
                let response = await api.ur_009_kiemtra_ghepma(this.axios, params);
                console.log(response)
                if (response && response.data && response.data.error_code == 'BSS-00000000' && response.data.message) {
                    result = response.data.message;
                }
            } catch (e) {
                console.log(e)
            }
            return result;
        },
        async update_ghepma_thanhcong(hdkh_cha_id, hdkh_ghep_id) {
            let result;
            try {
                let params = {
                    hdkh_cha_id: hdkh_cha_id,
                    hdkh_ghep_id: hdkh_ghep_id
                }
                let response = await api.ur_009_update_ghepma_thanhcong(this.axios, params);
                if (response && response.data && response.data.error_code == 'BSS-00000000' && response.data.message) {
                    result = response.data.message;
                }
            } catch (e) {
                console.log(e)
            }
            return result;
        },
    },
    mounted() {
        
    },
}
</script>