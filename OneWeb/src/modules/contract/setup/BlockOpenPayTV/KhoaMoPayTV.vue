<template>
    <div>
        <div style="width:200px;height:100px;margin:auto;margin-top:100px;font-size:1.5em">
            <button style="font-size:1.2em" type="button" class="btn btn-primary" ref="myBtn" v-b-modal.popupKMPTV1>Click để mở chức năng</button>
        </div>

        <b-modal id="popupKMPTV1" size="xl" hide-footer hide-header hide-header-close body-class="modal-body p-0" no-close-on-backdrop>
            <div class="modal-content popup-box">
                <div class="popup-header">
                    <div class="title">Khóa mở PayTV</div>
                        <div class="close -ap icon-close" data-dismiss="modal" @click="$bvModal.hide('popupKMPTV1')">
                    </div>
                </div>
                <div class="list-actions-top">
                    <ul class="list">
                        <li :class="!enable.btnGhiLai ? 'non-ative' : ''">
                            <a @click="btnGhiLai_Click">
                                <span class="icon one-save"></span> Ghi lại
                            </a>
                        </li>
                        <li>
                            <a v-b-modal.popupTraCuu>
                                <span class="icon one-file-search"></span> Tra cứu
                            </a>
                        </li>
                    </ul>

                </div>
                <div class="popup-body">
                    <div class="box-form">
                        <div class="info-row">
                            <div class="key w130">Tài khoản MyTV</div>
                            <div class="value">
                                <input type="text" class="form-control" v-model="mytv_account" @keydown="txtMyTV_onEnter">
                            </div>
                        </div>
                        <div class="info-row">
                            <div class="key w130">Khoá tất cả dịch vụ</div>
                            <div class="value padt7">
                                <div class="row">
                                    <div class="col-sm-4 col-12">
                                        <div class="item marb20">
                                            <div class="check-action">
                                                <input type="checkbox" class="check" id="iallService" @change="cbxTatCa_CheckedChanged">
                                                <span class="name">All</span>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="col-sm-4 col-12">
                                        <div class="item marb20">
                                            <div class="check-action">
                                                <input type="checkbox" class="check" id="iceeme">
                                                <span class="name">Ceeme</span>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-sm-4 col-12">
                                        <div class="item marb20">
                                            <div class="check-action">
                                                <input type="checkbox" class="check" id="icloudGame">
                                                <span class="name">Cloud Game</span>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="col-sm-4 col-12">
                                        <div class="item marb20">
                                            <div class="check-action">
                                                <input type="checkbox" class="check" id="iphimTruyen">
                                                <span class="name">Phim truyện</span>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="col-sm-4 col-12">
                                        <div class="item marb20">
                                            <div class="check-action">
                                                <input type="checkbox" class="check" id="itroChoi">
                                                <span class="name">Game</span>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-sm-4 col-12">
                                        <div class="item marb20">
                                            <div class="check-action">
                                                <input type="checkbox" class="check" id="ikaraoke">
                                                <span class="name">Karaoke</span>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="col-sm-4 col-12">
                                        <div class="item marb20">
                                            <div class="check-action">
                                                <input type="checkbox" class="check" id="icaNhac">
                                                <span class="name">Ca nhạc</span>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="col-sm-4 col-12">
                                        <div class="item marb20">
                                            <div class="check-action">
                                                <input type="checkbox" class="check" id="idocTruyen">
                                                <span class="name">Đọc truyện</span>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-sm-4 col-12">
                                        <div class="item marb20">
                                            <div class="check-action">
                                                <input type="checkbox" class="check" id="itheThao">
                                                <span class="name">Thể thao</span>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="col-sm-4 col-12">
                                        <div class="item marb20">
                                            <div class="check-action">
                                                <input type="checkbox" class="check" id="isucKhoeLamDep">
                                                <span class="name">Sức khoẻ làm đẹp</span>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="col-sm-4 col-12">
                                        <div class="item marb20">
                                            <div class="check-action">
                                                <input type="checkbox" class="check" id="inhipCauMyTV">
                                                <span class="name">Nhịp cầu MyTV</span>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="info-row">
                            <div class="key w130">Khoá theo dịch vụ</div>
                            <div class="value padt7">
                                <div class="row">
                                    <div class="col-sm-4 col-12">
                                        <div class="item marb20">
                                            <div class="check-action">
                                                <input type="checkbox" class="check" id="iphongCachSong">
                                                <span class="name">Phong cách sống</span>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="col-sm-4 col-12">
                                        <div class="item marb20">
                                            <div class="check-action">
                                                <input type="checkbox" class="check" id="ithieuNhi">
                                                <span class="name">Thiếu nhi</span>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="col-sm-4 col-12">
                                        <div class="item marb20">
                                            <div class="check-action">
                                                <input type="checkbox" class="check" id="igiaoDucDaoTao">
                                                <span class="name">Giáo dục đào tạo</span>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-sm-4 col-12">
                                        <div class="item marb20">
                                            <div class="check-action">
                                                <input type="checkbox" class="check" id="ihboflg">
                                                <span class="name">HBO on demand</span>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="col-sm-4 col-12">
                                        <div class="item marb20">
                                            <div class="check-action">
                                                <input type="checkbox" class="check" id="idaoTaoTuXa">
                                                <span class="name">Đào tạo từ xa</span>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="col-sm-4 col-12">
                                        <div class="item marb20">
                                            <div class="check-action">
                                                <input type="checkbox" class="check" id="iketQuaXoSo">
                                                <span class="name">Kết quả xổ số</span>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-sm-4 col-12">
                                        <div class="item marb20">
                                            <div class="check-action">
                                                <input type="checkbox" class="check" id="ifafilmFLG">
                                                <span class="name">Fafim</span>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="col-sm-4 col-12">
                                        <div class="item marb20">
                                            <div class="check-action">
                                                <input type="checkbox" class="check" id="ibinhChon">
                                                <span class="name">Bình chọn</span>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="col-sm-4 col-12">
                                        <div class="item marb20">
                                            <div class="check-action">
                                                <input type="checkbox" class="check" id="ivtvcabFlg">
                                                <span class="name">Kênh VTVCab</span>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-sm-4 col-12">
                                        <div class="item marb20">
                                            <div class="check-action">
                                                <input type="checkbox" class="check" id="ikplusFLG">
                                                <span class="name">Kênh K+</span>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="col-sm-4 col-12">
                                        <div class="item marb20">
                                            <div class="check-action">
                                                <input type="checkbox" class="check" id="ifimPlus">
                                                <span class="name">FIM+</span>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="col-sm-4 col-12">
                                        <div class="item marb20">
                                            <div class="check-action">
                                                <input type="checkbox" class="check" id="idanet">
                                                <span class="name">Danet</span>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="info-row">
                            <div class="key w130">Lý do</div>
                            <div class="value">
                                <textarea name="" id="txtReason" v-model="txtReason" class="form-control" style="height: 100px;resize: none;"></textarea>
                            </div>
                        </div>
                        <div class="info-row">
                            <div class="key w130"></div>
                            <div class="value red fw6">
                                <p>Ghi chú: Tích chọn để khoá, bỏ tích để mở. <br/>
                                Sử dụng tính năng này chỉ có ý nghĩa để KH không được sử dung hoặc mở để KH có thể đăng ký trên TV<br />
                                Khi khoá sẽ không huỷ các gói Addon</p>
                            </div>
                        </div>
                    </div>
                </div>

                <!-- <ejs-dialog :enableResize='true' :visible="false" ref="tracuu" -->
                        <!-- :header='"Tra cứu lịch sử tác động khoá mở PayTV"' showCloseIcon=true target=".main-wrapper" width='80%' height="650px"> -->
                <b-modal id="popupTraCuu" size="xl" title="Tra cứu lịch sử tác động khoá mở PayTV" hide-footer body-class="modal-body p-0"
                        no-close-on-backdrop>
                    <TraLichSuTacDongKhoa />
                </b-modal>
                <!-- </ejs-dialog> -->

            </div>
        </b-modal>
    </div>
</template>
<script>
import api from "./BlockOpenPayTV.js"
import TraLichSuTacDongKhoa from "./TraLichSuTacDongKhoa.vue";
export default {
    name:'KhoaMoPayTV',
    components:{
        TraLichSuTacDongKhoa
    },
    mounted () {
        this.$refs.myBtn.click()
    },
    data() {
        return {
            enable: { btnGhiLai: false },
            mytv_account: "",
            txtReason: "",
        }
    },
    methods: {
        init() {
            this.mytv_account = ""
            this.txtReason = ""
            $("#iallService").prop('checked', false)
            this.enable.btnGhiLai = false
        },
        btnTraCuu_Click() {
            this.$refs.tracuu.show()
        },
        async getStatusBlockPayTV() {
            let result = null
            let params = {
                "iptvaccount": this.mytv_account
            }
            try {
                let response = await api.ur_21054_001_getStatusBlockPayTV(this.axios, params);
                console.log(response)
                if (response && response.data && response.data.errorCode == 0 && response.data.data) {
                    result = response.data.data
                }
                console.log(result)
            } catch (e) {
                console.log(e)
            }
            return result
        },
        async txtMyTV_onEnter(e) {
            if (e.keyCode == 13) {
                try {
                    this.loading(true)
                    let sts = await this.getStatusBlockPayTV()
                    if (sts != null) {
                        $("#icloudGame").prop('checked', sts.cloudGame == "0" ? true : false)
                        $("#iphimTruyen").prop('checked', sts.phimTruyen == "0" ? true : false)
                        $("#itroChoi").prop('checked', sts.troChoi == "0" ? true : false)
                        $("#ikaraoke").prop('checked', sts.karaoke == "0" ? true : false)
                        $("#icaNhac").prop('checked', sts.caNhac == "0" ? true : false)
                        $("#idocTruyen").prop('checked', sts.docTruyen == "0" ? true : false)
                        $("#itheThao").prop('checked', sts.theThao == "0" ? true : false)
                        $("#isucKhoeLamDep").prop('checked', sts.sucKhoeLamDep == "0" ? true : false)
                        $("#inhipCauMyTV").prop('checked', sts.nhipCauMyTV == "0" ? true : false)
                        $("#iphongCachSong").prop('checked', sts.phongCachSong == "0" ? true : false)
                        $("#ithieuNhi").prop('checked', sts.thieuNhi == "0" ? true : false)
                        $("#igiaoDucDaoTao").prop('checked', sts.giaoDucDaoTao == "0" ? true : false)
                        $("#idaoTaoTuXa").prop('checked', sts.daoTaoTuXa == "0" ? true : false)
                        $("#iketQuaXoSo").prop('checked', sts.ketQuaXoSo == "0" ? true : false)
                        $("#ibinhChon").prop('checked', sts.binhChon == "0" ? true : false)
                        $("#idanet").prop('checked', sts.danet == "0" ? true : false)
                        $("#ifimPlus").prop('checked', sts.fimPlus == "0" ? true : false)
                        $("#ikplusFLG").prop('checked', sts.kplusFLG == "0" ? true : false)
                        $("#ifafilmFLG").prop('checked', sts.fafilmFLG == "0" ? true : false)
                        $("#ihboflg").prop('checked', sts.hboflg == "0" ? true : false)
                        $("#ivtvcabFlg").prop('checked', sts.vtvcabFlg == "0" ? true : false)
                        $("#iceeme").prop('checked', sts.ceeme == "0" ? true : false)
                        this.enable.btnGhiLai = true
                    } else {
                        $("#iallService").prop('checked', false)
                        this.cbxTatCa_CheckedChanged()
                        this.enable.btnGhiLai = true
                    }
                } catch (e) {
                    this.enable.btnGhiLai = false
                    console.log(e)
                    this.$toast.error("Xảy ra lỗi: " + e);
                } finally {
                    this.loading(false)
                }
            }
        },
        async blockPayTV(objRequest) {
            let params = {
                "objRequest": objRequest
            }
            try {
                let response = await api.ur_21054_002_01_blockPayTV(this.axios, params);
                console.log(response)
                if (response && response.data && response.data.errorCode == 0) {
                    return true;
                } else {
                    this.$toast.error(response.data.faultString)
                }
            } catch (e) {
                console.log(e)
            }
            return false
        },
        async capnhat_log_td_mytv(vgiatri) {
            let params = {
                "vkieu": 1,
                "vgiatri": vgiatri
            }
            try {
                let response = await api.ur_21054_002_02_capnhat_log_td_mytv(this.axios, params);
                console.log(response)
            } catch (e) {
                console.log(e)
            }
        },
        async btnGhiLai_Click() {
            if (!this.enable.btnGhiLai) return
            if (this.txtReason.trim() == "") {
                this.$toast.error("Bạn phải nhập lý do");
                $("#txtReason").focus();
                return;
            }

            let objRequest = {
                myTVID: this.mytv_account,
                operator: 2,
                allService: document.getElementById("iallService").checked ? "0" : "1",
                cloudGame: document.getElementById("icloudGame").checked ? "0" : "1",
                phimTruyen: document.getElementById("iphimTruyen").checked ? "0" : "1",
                troChoi: document.getElementById("itroChoi").checked ? "0" : "1",
                karaoke: document.getElementById("ikaraoke").checked ? "0" : "1",
                caNhac: document.getElementById("icaNhac").checked ? "0" : "1",
                docTruyen: document.getElementById("idocTruyen").checked ? "0" : "1",
                theThao: document.getElementById("itheThao").checked ? "0" : "1",
                sucKhoeLamDep: document.getElementById("isucKhoeLamDep").checked ? "0" : "1",
                nhipCauMyTV: document.getElementById("inhipCauMyTV").checked ? "0" : "1",
                phongCachSong: document.getElementById("iphongCachSong").checked ? "0" : "1",
                thieuNhi: document.getElementById("ithieuNhi").checked ? "0" : "1",
                giaoDucDaoTao: document.getElementById("igiaoDucDaoTao").checked ? "0" : "1",
                daoTaoTuXa: document.getElementById("idaoTaoTuXa").checked ? "0" : "1",
                ketQuaXoSo: document.getElementById("iketQuaXoSo").checked ? "0" : "1",
                binhChon: document.getElementById("ibinhChon").checked ? "0" : "1",
                danet: document.getElementById("idanet").checked ? "0" : "1",
                fimPlus: document.getElementById("ifimPlus").checked ? "0" : "1",
                kplusFLG: document.getElementById("ikplusFLG").checked ? "0" : "1",
                fafilmFLG: document.getElementById("ifafilmFLG").checked ? "0" : "1",
                hboflg: document.getElementById("ihboflg").checked ? "0" : "1",
                vtvcabFlg: document.getElementById("ivtvcabFlg").checked ? "0" : "1",
                ceeme: document.getElementById("iceeme").checked ? "0" : "1",
                reason: this.$root.token.getUserName() + ": " + this.txtReason
            }

            try {
                this.loading(true)
                let resultVasc = await this.blockPayTV(objRequest)
                if (resultVasc) {
                    let strGiaTri = this.mytv_account;
                    strGiaTri += ";CloudGame:" + (document.getElementById("icloudGame").checked ? "0" : "1");
                    strGiaTri += ";PhimTruyen:" + (document.getElementById("iphimTruyen").checked ? "0" : "1");
                    strGiaTri += ";TroChoi:" + (document.getElementById("itroChoi").checked ? "0" : "1");
                    strGiaTri += ";Karaoke:" + (document.getElementById("ikaraoke").checked ? "0" : "1");
                    strGiaTri += ";CaNhac:" + (document.getElementById("icaNhac").checked ? "0" : "1");
                    strGiaTri += ";DocTruyen:" + (document.getElementById("idocTruyen").checked ? "0" : "1");
                    strGiaTri += ";TheThao:" + (document.getElementById("itheThao").checked ? "0" : "1");
                    strGiaTri += ";SucKhoeLamDep:" + (document.getElementById("isucKhoeLamDep").checked ? "0" : "1");
                    strGiaTri += ";NhipCauMyTV:" + (document.getElementById("inhipCauMyTV").checked ? "0" : "1");
                    strGiaTri += ";PhongCachSong:" + (document.getElementById("iphongCachSong").checked ? "0" : "1");
                    strGiaTri += ";ThieuNhi:" + (document.getElementById("ithieuNhi").checked ? "0" : "1");
                    strGiaTri += ";GiaoDucDaoTao:" + (document.getElementById("igiaoDucDaoTao").checked ? "0" : "1");
                    strGiaTri += ";DaoTaoTuXa:" + (document.getElementById("idaoTaoTuXa").checked ? "0" : "1");
                    strGiaTri += ";KetQuaXoSo:" + (document.getElementById("iketQuaXoSo").checked ? "0" : "1");
                    strGiaTri += ";BinhChon:" + (document.getElementById("ibinhChon").checked ? "0" : "1");
                    strGiaTri += ";Danet:" + (document.getElementById("idanet").checked ? "0" : "1");
                    strGiaTri += ";FimPlus:" + (document.getElementById("ifimPlus").checked ? "0" : "1");
                    strGiaTri += ";KplusFLG:" + (document.getElementById("ikplusFLG").checked ? "0" : "1");
                    strGiaTri += ";FafilmFLG:" + (document.getElementById("ifafilmFLG").checked ? "0" : "1");
                    strGiaTri += ";HBOFlg:" + (document.getElementById("ihboflg").checked ? "0" : "1");
                    strGiaTri += ";VTVcabFlg:" + (document.getElementById("ivtvcabFlg").checked ? "0" : "1");
                    strGiaTri += ";Ceeme:" + (document.getElementById("iceeme").checked ? "0" : "1");
                    await this.capnhat_log_td_mytv(strGiaTri)
                    this.$toast.success("Đồng bộ thành công lên VASC")
                }
            } catch (e) {
                console.log(e)
                this.$toast.error("Xảy ra lỗi: " + e)
            } finally {
                this.loading(false)
            }
        },
        cbxTatCa_CheckedChanged() {
            let cbxTatCa = document.getElementById("iallService")
            $("#icloudGame").prop('checked', cbxTatCa.checked)
            $("#iphimTruyen").prop('checked', cbxTatCa.checked)
            $("#itroChoi").prop('checked', cbxTatCa.checked)
            $("#ikaraoke").prop('checked', cbxTatCa.checked)
            $("#icaNhac").prop('checked', cbxTatCa.checked)
            $("#idocTruyen").prop('checked', cbxTatCa.checked)
            $("#itheThao").prop('checked', cbxTatCa.checked)
            $("#isucKhoeLamDep").prop('checked', cbxTatCa.checked)
            $("#inhipCauMyTV").prop('checked', cbxTatCa.checked)
            $("#iphongCachSong").prop('checked', cbxTatCa.checked)
            $("#ithieuNhi").prop('checked', cbxTatCa.checked)
            $("#igiaoDucDaoTao").prop('checked', cbxTatCa.checked)
            $("#idaoTaoTuXa").prop('checked', cbxTatCa.checked)
            $("#iketQuaXoSo").prop('checked', cbxTatCa.checked)
            $("#ibinhChon").prop('checked', cbxTatCa.checked)
            $("#idanet").prop('checked', cbxTatCa.checked)
            $("#ifimPlus").prop('checked', cbxTatCa.checked)
            $("#ikplusFLG").prop('checked', cbxTatCa.checked)
            $("#ifafilmFLG").prop('checked', cbxTatCa.checked)
            $("#ihboflg").prop('checked', cbxTatCa.checked)
            $("#ivtvcabFlg").prop('checked', cbxTatCa.checked)
            $("#iceeme").prop('checked', cbxTatCa.checked)
        }
    },
}
</script>
<style>
  .non-ative a {
    color: #d3d3d3 !important;
  }

  #popupTraCuu .modal-xl {
    max-width: 80% !important;
  }
</style>
