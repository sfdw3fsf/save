
<template src="./HandleIncidentNetReports.html"></template>
<script>

import axios from 'axios'
import breadcrumb from '@/components/breadcrumb'

import moment from "moment";

import Vue from 'vue';
import enums from './enums.js'
import api from "./api.js";
import apiHelper from './api.helper'

import { LOAIHIN_THUEBAO } from '../../../../constants.js';
import ppGuiSMSTienTrinhBH from "./Popups/GuiSMSTienTrinhBH/GuiSMSTienTrinhBH.vue"
// import NoiDungTT from '../UpdateProcessIncident/popups/NoiDungTT.vue'
import TienTrinhXuLy from './Components/TienTrinhXuLy/TienTrinhXuLy.vue'
import ThongTinXuLy from './Components/ThongTinXuLy/index.vue'
import DanhSachPhieuBaoHong from './Components/DanhSachPhieuBaoHong/index.vue'
import _ from 'lodash'
import helper from './helper.js';
import PopupGuiSms from '@/modules/install/UpdateInstallProgress/popups/PopupGuiSms'





export default {
    provide: {
    },
    name: 'HandleIncidentNetReports',
    components: {
        // NoiDungTT,
        breadcrumb,
        PopUpDownloadFile: () => import('./Popups/PopUpDownloadFiles/PopUpDownloadFile'),
        ppGuiSMSTienTrinhBH,
        PopupGuiSms,
        TienTrinhXuLy,
        ThongTinXuLy,
        DanhSachPhieuBaoHong,
        ppCapNhatTienTrinh: () => import('./Popups/CapNhatTienTrinh/CapNhatTienTrinh.vue'),
        CreateIssue: () => import('@/modules/contract/complete_profile/CompleteProfileITVAS/popups/popupCreateIssue/CreateIssue')
    },
    props: {
        tag_modal: {
            type: String,
            default: ""
        },
    },
    data() {
        return {


            header: {
                title: 'Xử lý báo hỏng NET',
                list: [
                    { name: 'Báo hỏng', link: { name: 'Ui.cards' } },
                    { name: 'Xử lý báo hỏng NET', link: { name: 'Ui.cards' } },
                ]
            },

            tsbtnNghiemThu: {
                visible: true,
                enable: true
            },
            tsbtnHoanCong: {
                visible: false,
                enable: true
            },
            tsbtnGiaoPhoiHop:{
                visible: true,
                enable: true 
            },

            tsbtnCapNhat: {
                visible: true,
                enable: true
            },

            tsbtnChuyenOMCData: {
                visible: false,
                enable: true
            },
            tsbtnChuyenMedia: {
                visible: true,
                enable: true
            },

            tsbtnExportExcel: {
                visible: true,
                enable: true
            },


            tsbtnChuyenVNPTI: {
                visible: true,
                enable: true
            },
            tsbtnChuyenTTDH: {
                visible: false,
                enable: true
            },

            cboDichVuVT: {
                dataSource: [],
                value: null
            },

            cboTinhTrang: {
                dataSource: [],
                value: null,
            },

            cboQuyTrinh: {
                dataSource: [],
                value: null,
            },



            //check điều kiện truyền từ tag vào
            Tag: "",
            huonggiao_khdn_id: 0,
            phieu_tu_vnp: 0,
            kieugoi_id: 0,
            huonggiao_id: 0,


            //thong tin bao hong
            listBaoHong: [],
            baoHongInfo: null,
            txtMaTB: "",
            txtTenTB: "",
            txtDiaChiLD: "",
            txtTrangThaiTB: "",
            txtNoiDung: "",
            txtGhiChuTH: "",
            txtTenThueBao: "",
            txtDienThoaiLH: "",
            txtLoaiTB: "",
            txtGhiChuBH: "",
            dtpNgayBH: '',

            donvi_giao_id: 0, //always = 0 - A.van confirm

            BAOHONG_NET_PHAINHAP_NGUYENNHAN: 0,

            baoHongId: 0,
            // thuebao_id_thicong: 0,
            tienTrinhSelected: {
                tientrinhbh_id: 0,
                noidung: "",
                baohong_id: 0,
            },



            //luu thong tin xu ly
            kqXlId: 0,
            txtNoiDungXL: "",
            nguyenNhanBH: [],



            ttbh_id: 1,
            tinhThiCongId: 0, //neu = 0 laf khdn, con lai la tinh khac

            systemDate: moment(new Date()).format('yyyyMMDD'),
            mayCN: "",
            ipCn: '0.0.0.0',




        }
    },

    async created() {
        await this.getDanhSachDVVT();
        this.mayCN = await this.$root.token.getMachine()
        this.ipCn = await this.$root.token.getIP();

    },

    async mounted() {
        await this.frmNghiemThuBHNETLoad();
    },

    methods: {
        async frmNghiemThuBHNETLoad() {
            //cần check lại
            try {
                if (this.kieugoi_id == 0) {
                    const tag = this.$route.query.tag
                    if (tag != null && tag !== '' && tag.length > 0) {

                        this.Tag = decodeURIComponent(tag.toString());

                    } else {
                        this.Tag = this.tag_modal
                    }


                    if (this.Tag != "") {
                        if (this.Tag.includes("+")) {
                            const words = this.Tag.split('+');
                            if (words.length >= 1)
                                this.kieugoi_id = Number(words[0]);
                            if (words.length >= 2) {
                                if (words[1].trim() != "")
                                    this.huonggiao_id = "(" + words[1].toString().replaceAll(";", ",") + ")";
                            }
                            if (words.length >= 3)
                                this.cboDichVuVT.value = Number(words[2]);

                            if (words.length >= 4)
                                this.huonggiao_khdn_id = words[3];
                            if (this.huonggiao_khdn_id == '10002')
                                this.tsbtnChuyenMedia.visible = true;
                            else
                                this.tsbtnChuyenMedia.visible = false;


                            if (words.length >= 5) {
                                this.phieu_tu_vnp = Number(words[4]);

                                if (this.phieu_tu_vnp == 0) {
                                    this.header.title = 'NET – Xử lý báo hỏng từ VNPT T/Tp';
                                    this.tsbtnChuyenOMCData.visible = false;
                                    this.tsbtnChuyenTTDH.visible = false;
                                }
                                else {
                                    this.header.title = 'NET – Xử lý báo hỏng từ VNPT VNP';
                                    this.tsbtnChuyenOMCData.visible = true;
                                    this.tsbtnChuyenTTDH.visible = true;
                                }
                            }
                        }
                        else
                            this.kieugoi_id = Number(this.Tag);
                    }
                    else
                        this.kieugoi_id = 0;


                } else if (this.kieugoi_id == enums.NET_BAOHONG.TTBH_XL_XONG) {

                    this.tsbtnNghiemThu.visible = false;
                }

                let ds_kt1 = await apiHelper.layDanhSachThamSoMD(this.axios);

                const str_vtemp = "BAOHONG_NET_PHAINHAP_NGUYENNHAN";

                ds_kt1 = ds_kt1.filter(e => e.ma_ts == str_vtemp);

                if (ds_kt1.length > 0) {
                    if (ds_kt1[0]["ten_ts"].trim() == "1")
                        this.BAOHONG_NET_PHAINHAP_NGUYENNHAN = 1
                }


            } catch (err) {
                //test
                console.log(err)
                this.kieugoi_id = 0;
            }

            this.hanldeInitComponentDanhSachBaoHong()
        },


        clearData() {
            this.baoHongInfo = null;
            this.txtMaTB = ""
            this.txtTenTB = ""
            this.txtDiaChiLD = ""
            this.txtTrangThaiTB = ""
            this.txtNoiDung = ""
            this.txtGhiChuTH = ""
            this.txtTenThueBao = ""
            this.txtDienThoaiLH = ""
            this.txtLoaiTB = ""
            this.txtGhiChuBH = ""
            this.dtpNgayBH = ""
        },

        hanldeInitComponentDanhSachBaoHong() {
            this.$refs.refDanhSachBaoHongComponent.onInitComponent({
                huonggiao_id: this.huonggiao_id,
                huonggiao_khdn_id: this.huonggiao_khdn_id,
                phieu_tu_vnp: this.phieu_tu_vnp
            })
        },

        getDanhSachBaoHong() {
            this.clearData();
            this.$refs.refDanhSachBaoHongComponent.getDanhSachBaoHong();
        },

        async getTinhTrang(baohong_id, dichvuvt_id, loaitb_id, kieu) {

            try {
                const params = { baohong_id, dichvuvt_id, loaitb_id, kieu }
                let responseTT = await api.getTinhTrang(this.axios, params);
                this.cboTinhTrang.dataSource = apiHelper.getDataFromResponseApiReturnArray(responseTT) || [];

            } catch (e) {
                console.log(e)
            }

        },

        async onRowGridBaoHongSelected(data) {


            if (!data) return;

            this.baoHongInfo = data;

            try {
                this.loading(true);
                this.setThueBaoInfo(this.baoHongInfo);
                this.setLayoutHeader(this.baoHongInfo);
                await this.getTinhTrang(this.baoHongInfo.baohong_id, this.cboDichVuVT.value, this.baoHongInfo.loaitb_id, 1)

            } catch (ex) {
                console.log(ex)
            } finally {
                this.loading(false)
            }
        },

        async setLayoutHeader(item) {

            let kenh_lt = '-1';
            this.tsbtnChuyenTTDH.visible = true;
            this.tsbtnChuyenOMCData.visible = true;

            if (this.cboDichVuVT.value == 7 || this.cboDichVuVT.value == 8) {
                const resKenh = await api.getLoaiKenh(this.axios, item.thuebao_id)
                kenh_lt = resKenh?.data.data;
            }
            if (this.cboDichVuVT.value == 9 || item.loaitb_id == enums.MGW_INTERNET_TRUCTIEP)
                kenh_lt = "2";



            if (kenh_lt == "9")
                this.tsbtnChuyenVNPTI.visible = true;
            else
                this.tsbtnChuyenVNPTI.visible = false;


            const resTTBC = await api.getStatusBC(this.axios, item.baohong_id)
            const trang_thai_bc = resTTBC?.data.data;

            console.log("trang_thai_bc:", trang_thai_bc)
            if (trang_thai_bc == 3 || trang_thai_bc == 4)
                this.tsbtnNghiemThu.visible = true;
            else {
                this.tsbtnNghiemThu.visible = false;
            }


            console.log("ttbh_id:", this.ttbh_id)
            if (this.ttbh_id == 4) {
                this.tsbtnHoanCong.visible = false;
                this.tsbtnNghiemThu.visible = false;
                this.tsbtnChuyenTTDH.visible = false;
                this.tsbtnChuyenOMCData.visible = false;
            } else {
                this.tsbtnHoanCong.visible = true;
                this.tsbtnNghiemThu.visible = true;
            }

            if (this.cboDichVuVT.value == 4 || this.huonggiao_khdn_id == '10003') {
                this.tsbtnNghiemThu.visible = false;
            }


        },


        setThueBaoInfo(item) {

            this.tinhThiCongId = item.tinh_id || this.$root.token.getPhanVungID();
            this.baoHongId = item.baohong_id;
            // this.thuebao_id_thicong = item.thuebao_id_thicong;
            this.cboDichVuVT.value = item.dichvuvt_id;
            this.cboQuyTrinh.value = item.quytrinh_id;
            this.cboTinhTrang.value = item.tinhtrang;
            this.txtMaTB = item.ma_tb;
            this.txtLoaiTB = item.loaihinh_tb;
            this.txtTenTB = item.ten_tb;
            this.txtDiaChiLD = item.diachi_ld;
            this.txtDienThoaiLH = item.dienthoai_lh
            this.txtGhiChuTH = item.ghichu_th;
            this.txtGhiChuBH = item.ghichu_hong;
            this.dtpNgayBH = moment(item.ngay_bh).format("DD/MM/YYYY HH:mm A")
            this.txtTrangThaiTB = item.trangthai_tb;
        },


        async getDanhSachDVVT() {

            try {
                let response = await api.getDsDichVuVT(this.axios);
                this.cboDichVuVT.dataSource = apiHelper.getDataFromResponseApiReturnArray(response);
                if (this.cboDichVuVT.dataSource.length > 0) {
                    this.cboDichVuVT.value = this.cboDichVuVT.dataSource[0]["DICHVUVT_ID"];
                }
            } catch (e) {
                console.log(e)
            }
        },

        cboDichVuVT_Changed(e) {
            const dvvtId = e.value
            if (!dvvtId) return;

            this.getQuyTrinh(dvvtId);
        },

        async getQuyTrinh(dvvtId) {
            try {

                const response = await api.getQuytrinhByDVVT(this.axios, dvvtId);
                const data = apiHelper.getDataFromResponseApiReturnArray(response);
                if (data && data.length > 0) {
                    this.cboQuyTrinh.dataSource = data;
                    this.cboQuyTrinh.value = this.cboQuyTrinh.dataSource[0].quytrinh_id
                } else {
                    this.cboQuyTrinh.value = 0;
                    this.cboQuyTrinh.dataSource = [{ quytrinh_id: 0, quytrinh: "--Không có quy trình--" }]
                }
            } catch (exp) {
                console.log(exp)
            }
        },

        handleTienTrinhSelected(tientrinh){
            console.log("tientrinh", tientrinh)
            this.tienTrinhSelected = tientrinh;

        },

        hanldeShowUpdateFormTienTrinh(tienTrinhItemSelected) {

            const param = {
                isEditMode: true,
                phanVungId: this.tinhThiCongId,
                data: tienTrinhItemSelected,
            }
            this.$refs.popupUpdateTienTrinh.onloadModal(param);
            this.$bvModal.show('popupUpdateTientrinhId');
        },

        async handleShowCreateFormTienTrinh() {

            if (!this.baoHongInfo) return this.$toast.warning("Không có dữ liệu để nhập tiến trình!");

            const baoHongTCId = this.baoHongInfo.baohong_id_thicong;
            if (this.phieu_tu_vnp == 1 && !baoHongTCId) return this.$toast.warning("Phiếu chưa chuyển tỉnh, không được nhập tiến trình!");

            const isPBHExits = await apiHelper.isBaoHongTinhExits(this.axios, {
                vbaohong_id: baoHongTCId ,
                tinhthicong_id: this.tinhThiCongId
            })

            if (!isPBHExits) return this.$toast.warning("Phiếu báo hỏng ở tỉnh không tồn tại!");


            const param = {
                isEditMode: false,
                phanVungId: this.tinhThiCongId,
                data: this.baoHongInfo,
            }
            this.$refs.popupUpdateTienTrinh.onloadModal(param);
            this.$bvModal.show('popupUpdateTientrinhId');
        },


        async handleSaveTienTrinhSuccess(result) {
            if (result)
                this.$refs.refTienTrinhXuLyComponent.onReloadComponent();
        },

        handleShowPopupDownloadHS() {
            this.$refs.popupDownloadFildeHS.onloadModal({
                baohong_id: this.baoHongId,
                phanVungId: this.tinhThiCongId
            }
            );
            this.$bvModal.show('popupDownloadFileHSId');
        },

        async tsbtnCapNhat_Click() {
            // console.log(this.baoHongInfo)
          
            // if (this.baoHongInfo.ISCHECK != 1) return this.$toast.warning("Bạn chưa chọn phiếu!");
            this.tsbtnCapNhat.enable = false;

            this.loading(true);
            let result = false;

            if (!this.baoHongInfo.ma_bh.includes("KHDN")) {

                result = await this.hanldeUpdateBaoHongNet_Tinh()

            } else {
                result = await this.hanldeUpdateBaoHongNet_KHDN()
            }
            this.loading(false);
            this.tsbtnCapNhat.enable = true;
            await this.getDanhSachBaoHong();

            if (result) {
                this.$toast.success("Cập nhật thành công!")
                await this.getDanhSachBaoHong();
            } else {
                this.$toast.error("Cập không nhật thành công!");
            }

        },

        async hanldeUpdateBaoHongNet_Tinh() {

            try {
                if (this.huonggiao_id_tinh != '8888') {

                    //SP_CAPNHAT_GHICHU_TH
                   await apiHelper.capNhatGhiChu(this.axios, {
                        vghichu_th: this.txtGhiChuTH,
                        vngay_cn: this.systemDate,
                        vphieu_id: this.baoHongInfo.phieu_id,
                        vbaohong_id: this.baoHongInfo.baohong_id,
                        phanvung_id: this.baoHongInfo.tinh_id
                    });

                    await apiHelper.insertNguyenNhanBH(this.axios, {
                        listNguyenNhan: this.nguyenNhanBH,
                        tinhthicong_id: this.tinhThiCongId,
                        vphieu_id: this.baoHongInfo.phieu_id
                    })

                }

                //HoanThien_BH_Net_Tinh
                await api.hoanThienBaoHongNetTinh(this.axios, {
                    vttph_id: 1,
                    vphieu_id: this.baoHongInfo.phieu_id,
                    vnoidung_xuly: this.txtNoiDungXL
                });
            } catch (err) {
                console.log(err)
                return false;
            }

            return true;
        },

        async hanldeUpdateBaoHongNet_KHDN() {

            try {
                const cthongStr = this.nguyenNhanBH.join(",");

                const param = { vttph_id: 1, vphieu_id: this.baoHongInfo.phieu_id, vct_hong: cthongStr, vkqxl_id: this.kqXlId, vghichu: this.txtNoiDungXL || "" }
                await api.banCheoHoanThanhBaoHongV3(this.axios, param)
            }
            catch (err) {

                console.log(err)
                return false;
            }
            return true
        },

        async tsbtnHoanCong_Click() {

            const listBHSelected = this.listBaoHong.filter(e => e.ISCHECK == 1);
    
            if (listBHSelected.length == 0) return this.$toast.warning("Bạn chưa chọn phiếu!");

            let ct_hog = this.nguyenNhanBH.join(",");
            console.log("ct_hog:", ct_hog)


            if (ct_hog == "" && this.BAOHONG_NET_PHAINHAP_NGUYENNHAN == 1) 
                return this.$toast.warning("Bạn chưa chọn nguyên nhân hỏng");
            
            let srtError = '';
            this.loading(true);

            for (let i = 0; i < listBHSelected.length; i++) {

                const item = listBHSelected[i];
                console.log("handleHoanCong=>", item)

                const { ma_bh, log_giao } = item;

                if (!ma_bh?.includes('KHDN') || log_giao?.includes('800126->VNPT-NET')) {
                    srtError = await this.handleHoanCong_Tinh(item, srtError)
                } else {
                    srtError = await this.hanldeHoanCong_KHDN(item, srtError)
                }
            }

            this.loading(false);

            if (srtError != "") {
                this.$toast.error(srtError);
                return;
            }

            this.$toast.success("Cập nhật thành công!");
            this.clearData();
            this.getDanhSachBaoHong();

        },

        async handleHoanCong_Tinh(baoHongItem, srtError) {
            const phieuTinhId = baoHongItem.phieu_tinh_id;
            const huongGiaoTinhId = baoHongItem.huonggiao_id;

            console.log("handleHoanCong_Tinh=>", baoHongItem)
            const { log_giao, tu_omc, baohong_id, phieu_id, tinh_id, ma_bh } = baoHongItem;

            try {
                if (huongGiaoTinhId == 8888) {

                    //new BaoHong()).HoanThien_BH_Net_Tinh(4, phieu_id, tt_nd.ma_nd, tt_nd.may_cn, tt_nd.ip, tt_nd.ngay_cn, txtGhiChuTH.Text);
                   const resHTBHNETTinh =  await api.hoanThienBaoHongNetTinh(this.axios, { vttph_id: 4, vphieu_id: phieu_id, vnoidung_xuly: this.txtGhiChuTH })
                   srtError += apiHelper.getSrtCodeError(resHTBHNETTinh)

                }
                else {
                    //check phieu con hoan cong
                    if (!(await this.checkPhieuConHoanCong(phieu_id))) return;

                    //call TT ng SP_TT_NGUOIDUNG => muc dich la tnhan vien Id
                    const vnhanvien_id = this.$root.token.getNhanVienID();
        
                    const resTTGP = await api.getTTGiaoPhieu_PH(this.axios, { baohong_id: baohong_id, tinhthicong_id: tinh_id, phieu_id });
                   
                    let trang_thai_ph = resTTGP?.data.data;

                    if (trang_thai_ph != 3) {
                        if (this.huonggiao_id_khdn == "10003") {
                            if (log_giao?.includes('800126->VNPT-NET')) {
                                //giaophieu_donvi_tieptheo_v4
                                const param = {
                                    tinhthicong_id: tinh_id,
                                    vbaohong_id: baohong_id,
                                    vphieu_id: phieuTinhId,
                                    vnhanvien_id: vnhanvien_id,
                                    vhuonggiao_id: huongGiaoTinhId,
                                }
                              const resGPDVTTV4 =  await api.giaoPhieuDonViTiepV4(this.axios, param)
                              srtError += apiHelper.getSrtCodeError(resGPDVTTV4)
                           
                                
                            } else {
                                //giaophieu_donvi_tieptheo
                                const param = {
                                    baohong_id: baohong_id,
                                    phieu_id: phieuTinhId,
                                    nhanvien_id: vnhanvien_id,
                                    huonggiao_id: huongGiaoTinhId,
                                    may_cn: this.mayCN,
                                    ngay_cn: this.systemDate,
                                    nguoi_cn: this.$root.token.getUserName(),
                                    phanvung_id: tinh_id
                                }

                            const resGPDVTT =  await api.giaoPhieuDonViTiepTheo(this.axios, param);
                            srtError += apiHelper.getSrtCodeError(resGPDVTT)
                            
                        }

                          const resHTBHNETT =   await api.hoanThienBaoHongNetTinh(this.axios, { vttph_id: 4, vphieu_id: phieu_id, vnoidung_xuly: this.txtNoiDungXL })
                          srtError += apiHelper.getSrtCodeError(resHTBHNETT)

                        } else {
                            if (tu_omc == "1") {

                                const param = {
                                    baohong_id: baohong_id,
                                    phieu_id: phieuTinhId,
                                    nhanvien_id: vnhanvien_id,
                                    huonggiao_id: huongGiaoTinhId,
                                    may_cn: this.mayCN,
                                    ngay_cn: this.systemDate,
                                    nguoi_cn: this.$root.token.getUserName(),
                                    phanvung_id: tinh_id
                                }

                                const resGPDVTT =  await api.giaoPhieuDonViTiepTheo(this.axios, param);
                                srtError += apiHelper.getSrtCodeError(resGPDVTT)
                    

                            } else {
                                // giaophieu_donvi_tieptheo_v4
                                const param = {
                                    tinhthicong_id: tinh_id,
                                    vbaohong_id: baohong_id,
                                    vphieu_id: phieuTinhId,
                                    vnhanvien_id: vnhanvien_id,
                                    vhuonggiao_id: huongGiaoTinhId
                                }
                              const resGPDVTTV4 =  await api.giaoPhieuDonViTiepV4(this.axios, param);

                              srtError += apiHelper.getSrtCodeError(resGPDVTTV4)
                            }
                        }
                        //call capnhat_ttph_phieuph
                        await api.updateTTPHPhieuHP(this.axios, phieu_id);

                        //call api CAPNHAT_NHANVIEN_TH
                        const resCapNhatNhanVienTH = await apiHelper.capNhatNhanVienTH(this.axios, { vphieu_id: phieu_id, vnhanvien_th_id: vnhanvien_id, vngay_th: moment(new Date()).format('DD/MM/yyyy HH:mm:ss') })
                  
                        if(resCapNhatNhanVienTH) srtError += resCapNhatNhanVienTH
                        
                        //call api SP_CAPNHAT_GHICHU_TH
                        const  resCapNhatGhiChu = await apiHelper.capNhatGhiChu(this.axios, { vghichu_th: this.txtGhiChuTH, vngay_cn: this.systemDate, vphieu_id: phieuTinhId, vbaohong_id: baohong_id, phanvung_id: tinh_id })
                        
                        if(resCapNhatGhiChu) srtError += resCapNhatGhiChu
                        

                        //SP_INSERT_NGUYENNHAN
                        await apiHelper.insertNguyenNhanBH(this.axios, {
                            listNguyenNhan: this.nguyenNhanBH,
                            tinhthicong_id: tinh_id,
                            vphieu_id: phieuTinhId
                        })
                        // this.$toast.success("Cập nhật thành công")

                    } else {

                        //HoanThien_BH_Net_Tinh
                       const resHTBHNetTinh = await api.hoanThienBaoHongNetTinh(this.axios, { vttph_id: 4, vphieu_id: phieu_id, vnoidung_xuly: this.txtGhiChuTH })
                       srtError += apiHelper.getSrtCodeError(resHTBHNetTinh)
                     
                    
                    }
                }
            }
            catch (err) {
                console.log(err)
                srtError += "Có lỗi xảy ra - Mã BH: " + ma_bh + "\r\n";
            }

            return srtError;
        },

        async hanldeHoanCong_KHDN(baoHongItem, srtError) {
            console.log("hanldeHoanCong_KHDN=>", baoHongItem)
            const { baohong_id, tinh_id, phieu_id, ma_bh ,baohong_id_thicong  } = baoHongItem


            //(tinh, "{?DB7}.kiemtra_phieu_hoanthanh_tinh"
            const rescheckHoanThanhTinh = await api.checkPhieuHoanThanhTinh(this.axios, { tinhthicong_id: tinh_id, vbaohong_id: baohong_id });
            const checkHoanThanhTinh = apiHelper.getDataFromResponseApiReturnRaise(rescheckHoanThanhTinh)
         

            if (checkHoanThanhTinh == 1) {
            
                //call  {?DB7}.nghiemthu_net_tinh 
                await api.nghiemThuTinhNet(this.axios, { tinhthicong_id: tinh_id, vbaohong_id: baohong_id_thicong })
                //HoanThien_BH_Net_v2
                const cthongStr = this.nguyenNhanBH.join(",");

                const param = {vttph_id:4 , vphieu_id: phieu_id, vct_hong: cthongStr, vkqxl_id: this.kqXlId, vghichu: this.txtNoiDungXL, vnhanvien_nt_id: this.$root.token.getNhanVienID()};

                console.log("banCheoHoanThanhBaoHongV4", param)
                const resBanCheoHHBHV4 =  await  api.banCheoHoanThanhBaoHongV4(this.axios,param )

                srtError += apiHelper.getSrtCodeError(resBanCheoHHBHV4)

            } else {
                srtError += "Tỉnh thi công chưa hoàn thành phiếu - Mã BH: " + ma_bh + "\r\n";
            }
            
            //call HoanThien_BH_Net
            // await api.hoanThienBaoHongNetTinh(this.axios, { vttph_id: 4, vphieu_id: phieu_id, vnoidung_xuly: this.txtNoiDungXL })
            return srtError;
        },


        //TaoDuLieu_BaoHong
        async createBaoHongData(baoHongInfo) {
            const dsBH_DATA = await this.createDsBH_DATA(baoHongInfo)
            const dsBH_BC_DATA = await this.createDsBH_BC_DATA(baoHongInfo, dsBH_DATA.BAOHONG_ID)
            return { dsBH_DATA, dsBH_BC_DATA }
        },

        async createDsBH_DATA(baoHongRow) {
            if (!baoHongRow) return null;

            let data = {};

            const { ma_bh } = baoHongRow;

            const keyBH = await apiHelper.getKeyBH(this.axios, this.tinhThiCongId);
            // const baohong_id_tc = keyBH;

            data.BAOHONG_ID_HNI = baoHongRow.baohong_id;
            data.BAOHONG_ID = keyBH;
            data.THUEBAO_ID = baoHongRow.thuebao_id_thicong;
            data.DICHVUVT_ID = this.cboDichVuVT.value;
            data.TEN_TB = this.txtTenTB;
            data.DIACHI_LD = this.txtDiaChiLD;
            data.LOAITB_ID = baoHongRow.loaitb_id;
            data.NGAY_BH = baoHongRow.ngay_bh;
            data.NGUOI_BH = baoHongRow.nguoi_bh;
            data.DIENTHOAI_LH = this.txtDienThoaiLH || "";
            data.GHICHU_HONG = this.txtGhiChuBH || "";

            const goiKtId = await apiHelper.layGoiKTId(this.axios, { vthuebao_id: baoHongRow.thuebao_id, vhdtb_id: -1 })
            data.GOIKT_ID = goiKtId;

            data.TTBH_ID = enums.TRANGTHAI_BH.MOI_TIEPNHAN;

            const quytrinhId = await apiHelper.layHuongGiaoTheoThuTu(this.axios, { VPHANVUNG_ID: this.$root.token.getPhanVungID(), VLOAITB_ID: baoHongRow.loaitb_id, VTHUTU: 1 })

            if (quytrinhId)
                this.cboQuyTrinh.value = quytrinhId;

            data.QUYTRINH_ID = this.cboQuyTrinh.value;
            data.KENHTN_ID = 2;
            data.MA_BH = ma_bh;
            data.MAY_CN =  await this.$root.token.getMachine();
            data.NGAY_CN = moment(new Date()).format("DD/MM/YYYY");
            data.NGUOI_CN = this.$root.token.getUserName();
            
            return data;
        },

        async createDsBH_BC_DATA(baoHongRow, baohong_id_tc) {
            if (!baoHongRow) return null;
            let dataBC = {};
            dataBC.LOAIHD_BANCHEO = 2;
            dataBC.TINH_THICONG = baoHongRow.tinh_id;
            dataBC.THUEBAO_ID_BAN = baoHongRow.thuebao_id;
            dataBC.MA_TB = this.txtMaTB;
            dataBC.THUEBAO_ID_THICONG = baoHongRow.thuebao_id_thicong;

            dataBC.TINH_BAN = this.$root.token.getPhanVungID();
            dataBC.TRANG_THAI = 0;
            dataBC.BAOHONG_ID = baoHongRow.baohong_id;
            dataBC.BAOHONG_ID_THICONG = baohong_id_tc;
            dataBC.DAUCUOI_ID = baoHongRow.daucuoi_id || 0;
            return dataBC;
        },

        async tsbtnLayTTMoi_Click() {
            this.hanldeInitComponentDanhSachBaoHong()
        },

        tsbtnExportExcel_Click() {

            if (!this.listBaoHong.length)
                return this.$toast.error("Không có dữ liệu để xuất Excel !");
            helper.exportExcel(this.listBaoHong, "Bao_Hong_NET");
        },

        async handleUploadFile(event) {

            this.loading(true);
            try {
                const file = event.target.files ? event.target.files[0] : null
                if (!file) {
                    this.loading(false);
                    return this.$toast.error("Không có dữ liệu để upload File !");
                }
                const fileId = await apiHelper.getKey(this.axios, "FILEHOSO_ID")

                const fileUpLoaded = await this.upLoadFileToMinio(file);
                const { url, name } = fileUpLoaded;

                if (this.baoHongInfo.tinh_id == 97) {
                    await this.insertHoSoTBKhachHangDoanhNghiep({ fileId: fileId, url, name })
                } else {
                    await this.updateBienBanNghiemThuBHNet({ url, name })
                }

            } catch (err) {
                console.log(err)
                this.$toast.error("Upload File HS Không Thành Công")
            } finally {
                this.loading(false);
            }
        },

        async tsbtnNghiemThu_Click() {

            const listBHSelected = this.listBaoHong.filter(e => e.ISCHECK == 1);

            if (listBHSelected.length == 0) return this.$toast.warning("Bạn chưa chọn phiếu!");

            const ct_hog = this.nguyenNhanBH.join(",");
            if (ct_hog == "" && this.BAOHONG_NET_PHAINHAP_NGUYENNHAN == 1) {
                return this.$toast.warning("Bạn chưa chọn nguyên nhân hỏng");
            }

            this.loading(true);
            let srtError = '';
            for (let i = 0; i < listBHSelected.length; i++) {
                const item = listBHSelected[i];

                const { log_giao, ma_bh } = item;

                if (!ma_bh.includes('KHDN') || log_giao?.includes('800126->VNPT-NET')) {
                    srtError = await this.handleNghiemThu_Tinh(item, srtError)
                } else {
                    srtError = await this.handleNghiemThu_KHDN(item, srtError)
                }
            }
            this.loading(false);
            if (srtError) {
                this.$toast.error(srtError);
                return;
            }
            this.$toast.success("Cập nhật thành công!");
            this.clearData();
            await this.getDanhSachBaoHong();


        },
        async handleNghiemThu_Tinh(data, srtError) {
            const { phieu_tinh_id, dichvuvt_id, ma_bh } = data;
            const huonggiao_id_tinh = data.huonggiao_id;

            const kt_dv_bh = await apiHelper.kiemTraDichVuHoanCong_BH_Net(this.axios, dichvuvt_id);

            if (kt_dv_bh != 'OK') {
                srtError += kt_dv_bh;
                return kt_dv_bh
            }

            if (huonggiao_id_tinh == 8888) {
                // HoanThien_BH_Net_Tinh
                await api.hoanThienBaoHongNetTinh(this.axios, { vttph_id: 4, vphieu_id: data.phieu_id, vnoidung_xuly: this.txtGhiChuTH })

            } else {
                //check phieu con hoan cong
                if (!(await this.checkPhieuConHoanCong(data.phieu_id))) return ;

                //call TT ng SP_TT_NGUOIDUNG => muc dich la tnhan vien Id
                const vnhanvien_id = this.$root.token.getNhanVienID();
                const { tinh_id, baohong_id, phieu_id } = data;

                //bancheo.GET_STRING_FUNCTION(tinh, "{?DB7}.kiemtra_phieu_hoanthanh_tinh", "vbaohong_id", baohong_id);
                const checkHoanThanhTinh = await api.checkPhieuHoanThanhTinh(this.axios, { tinhthicong_id: tinh_id, vbaohong_id: baohong_id })
                if (checkHoanThanhTinh.data == 0) {
                    srtError += `Tỉnh thi công chưa hoàn thành phiếu - Mã BH: " + ${ma_bh} + "\r\n"`
                    // this.$toast.warning("Tỉnh thi công chưa hoàn thành phiếu");
                    return srtError;
                }

                //call api CAPNHAT_NHANVIEN_TH
               const resCapNhatNhanVienTH =  await apiHelper.capNhatNhanVienTH(this.axios, {
                    vphieu_id: phieu_id,
                    vnhanvien_th_id: vnhanvien_id,
                    vngay_th: moment(new Date()).format('DD/MM/yyyy HH:mm:ss')
                });
              
                if(resCapNhatNhanVienTH)
                    srtError += resCapNhatNhanVienTH

                //SP_INSERT_NGUYENNHAN
                await apiHelper.insertNguyenNhanBH(this.axios, {
                    listNguyenNhan: this.nguyenNhanBH,
                    tinhthicong_id: data.tinh_id,
                    vphieu_id: data.phieu_id
                })

                //call api SP_CAPNHAT_GHICHU_TH
                const  resCapNhatGhiChu = await apiHelper.capNhatGhiChu(this.axios, {
                    vghichu_th: this.txtGhiChuTH,
                    vngay_cn: this.systemDate,
                    vphieu_id: data.phieu_id,
                    vbaohong_id: baohong_id
                });
                if(resCapNhatGhiChu) srtError += resCapNhatGhiChu  

                //HoanThien_BH_Net_Tinh
                await api.hoanThienBaoHongNetTinh(this.axios, { vttph_id: 4, vphieu_id: data.phieu_id, vnoidung_xuly: this.txtGhiChuTH })

                return srtError;
            }
        },
        async handleNghiemThu_KHDN(data, srtError) {


            const { tinh_id, baohong_id, ma_bh, phieu_id } = data;

            try {
                const resBHIDTC = await api.getBHId_TC(this.axios, baohong_id);
                const baohong_id_tc = resBHIDTC?.data.data

                if (baohong_id_tc != -1) {
                    //kiemtra_phieu_hoanthanh_tinh
                    const resCheckHoanThanhTinh = await api.checkPhieuHoanThanhTinh(this.axios, { tinhthicong_id: tinh_id, vbaohong_id: baohong_id });

                    if (resCheckHoanThanhTinh?.data.data == 1) {
                        //call  {?DB7}.nghiemthu_net_tinh 
                        await api.nghiemThuTinhNet(this.axios, { tinhthicong_id: tinh_id, vbaohong_id: baohong_id_tc })
                        await api.updateTrangThaiBaoHong(this.axios, { ttbh_id: 6, baohong_id: baohong_id });

                    } else {
                        srtError += "Tỉnh thi công chưa hoàn thành phiếu - Mã BH: " + ma_bh + "\r\n";

                    }

                }
                //HoanThien_BH_Net(4, phieu_id, ct_hong, kqxl_id, txtGhiChuTH.Text, tt_nd.may_cn, tt_nd.nhanvien_id, tt_nd.ma_nd));
                const cthongStr = this.nguyenNhanBH.join(",");

                const paramHoanThanhBC = { vttph_id: 4, vphieu_id: phieu_id, vct_hong: cthongStr, vkqxl_id: this.kqXlId, vghichu: this.txtGhiChu }
                const responseBanCheoHoanThanhBaoHongV3 = await api.banCheoHoanThanhBaoHongV3(this.axios, paramHoanThanhBC);

                if (responseBanCheoHoanThanhBaoHongV3 && responseBanCheoHoanThanhBaoHongV3.data && responseBanCheoHoanThanhBaoHongV3.data.error_code != 'BSS-00000000') 
                    srtError += responseBanCheoHoanThanhBaoHongV3.data.message_detail + "\r\n";
                

            } catch (err) {
                console.log(err)
                srtError += "Có lỗi xảy ra - Mã BH: " + ma_bh + "\r\n";
            }
            return srtError;

        },
        async tsbtnChuyenVNPTI_Click() {

            if (!this.baoHongId) return;
            const checkGiaoPhieuVTT = await this.checkGiaoVNPTIExits(this.baoHongId)
            if (checkGiaoPhieuVTT) return;

            const userCN = this.$auth.getUserName();
            const donViId = this.$root.token.getDonViID();
            //update chuyen tinh
            const resGiaoHoieu = await api.giaoPhieuBaoHong({
                vbaohong_id: this.baoHongId,
                vdonvi_nhan_id: 35132,//fix cung giong code C#
                vhuongiao_id: this.huongiao_id,
                vnd_giao: txtGhiChuBH,
                vphieu_cha_id: this.phieu_id,
                vttbh_id: this.ttbh_id, //value of rdio trang thai
                vma_nd: userCN,
                vdonvi_giao_id: donViId
            });

            const result = apiHelper.getDataFromResponseApiReturnRaise(resGiaoHoieu);

            if (result != 'ok') {
                this.$toast.error("Chuyển phiếu không thành công!")
                return;
            }
            this.$toast.success("Chuyển phiếu thành công!")
        },

        async tsbtnChuyenMedia_Click() {

            let result = false;
            try {
                this.loading(true);
                // giaophieu_baohong_media"
                const response = await api.giaoPhieuBHMedia(this.axios, { vphieu_id: this.baoHongInfo.phieu_id, vdonvi_giao_id: this.$root.token.getDonViID() })

                result = apiHelper.isResponseApiSuccess(response);
                if (result) {
                    this.loading(false);
                    this.$toast.success("Chuyển phiếu thành công!");
                    await this.getDanhSachBaoHong();
                    return;
                }

            } catch (err) {
                this.loading(false);
                console.log(err)
            }
            this.$toast.error("Chuyển phiếu không thành công!");

        },

        tsbtnGiaoPhoiHop_Click() {
            //todo
            //show form bao hoir voi Tag = 432, baohong_id_par =  baohong_id
            this.$router.push({
                        path: 'HandoverTicketIncidentCrossSale',
                        query: { Tag: 432, baohong_id_par: this.baoHongId }
            })
        },

        async checkGiaoVNPTIExits(baoHongId) {
            const response = await api.checkGiaoPhieuVNPTIExits(this.axios, baoHongId);

            const result = apiHelper.getDataFromResponseApiReturnRaise(response);
            if (result != "-1") {
                this.$toast.warning("Đã giao phiếu cho VNPT-IT!")
                return true
            }
            return false
        },


        async handleForwardIT360() {
            if (!this.baoHongInfo)
                return this.$toast.warning("Không có dữ liệu để thực hiện!");

            if (this.cboDichVuVT.value != enums.DichVuVienThong.DICHVU_CNTT)
                return this.$toast.warning("Chức năng chỉ áp dụng cho dịch vụ CNTT");

            //show form tao issue

            await this.forwardIT360(this.baoHongInfo);

        },

        async forwardIT360(param) {
            this.$bvModal
                .msgBoxConfirm(
                    "Bạn chắc chắn muốn chuyển phiếu IT360?",
                    {
                        okVariant: "success",
                        okTitle: "Đồng ý",
                        cancelTitle: "Hủy",
                        centered: true,
                    }
                )
                .then(async (accept) => {

                    if (!accept) return;
                        //this.kqXlId
                    const response = await api.insertNguyenNhanChuyenIT360(this.axios, { tinhthicong_id: this.tinhThiCongId, vcthong_id: this.cboNguyenNhanBH.value, vphieu_id: this.baoHongInfo.phieu_id });
                    if (!apiHelper.isResponseApiSuccess(response))
                        return this.$toast.error("Không thể thực hiện chuyển IT360");
                    // this.$toast.success("Chuyển IT360 thành công");
                    const data = {
                        kieu: 4,
                        baohong_id: this.baohong_id,
                        phieu_baohong_id: this.baoHongInfo.phieu_tinh_id,
                        tinh_id: this.tinhThiCongId
                    }

                    this.$refs.frmCreateIssue.openDialog(data);    
                })
                .catch((err) => {
                    console.log(err) 
                    return "Chuyen IT360 có lỗi! Vui lòng liên hệ admin! "    
                });
        },

        async tsbtnChuyenTTDH_Click() {
            try {
                if (this.baoHongId == 0 || !this.baoHongInfo) return this.$toast.warning("Không có dữ liệu để thực hiện!");
                //KiemTraGiaoTinh
                const checkGT = await apiHelper.checkGiaoTinh(this.axios, { vphanvung_id_tc: this.tinhThiCongId, vbaohong_id: this.baoHongId });
                
                if (!checkGT) {
                     this.$toast.warning("Đã giao, không thể thực hiện!"); 
                     return
                };

                await this.handleChuyenTTDH(this.baoHongInfo)

            } catch (error) {
                console.log(error);
            }
        },

        async handleChuyenTTDH(item) {
            this.$bvModal
                .msgBoxConfirm(
                    "Bạn chắc chắn muốn chuyển phiếu?",
                    {
                        okVariant: "success",
                        okTitle: "Đồng ý",
                        cancelTitle: "Hủy",
                        centered: true,
                    }
                )
                .then(async (accept) => {

                    if (!accept) return;
                    let srtError = ""
                    const maBH = item.ma_bh;
                    this.loading(true);
                    if (!maBH.includes('KHDN')) {
                        srtError = await this.handleChuyenTTDH_Tinh(item)
                    } else {
                        srtError = await this.handleChuyenTTDH_KHDN(item)
                
                    }
                    this.loading(false);
                    if (!srtError) {
                        this.$toast.success("Đã chuyển tỉnh thành công!");
                        await this.getDanhSachBaoHong();
                        return;
                    } 

                    this.$toast.error(srtError);
                    return;     
                })
                .catch((err) => {
                    console.log(err)
                    this.loading(false);
                });
        },

        //xu ly chuyen phieu tinh
        async handleChuyenTTDH_Tinh(itemBaoHong) {
            let srtError = '';
            try {
                const {phieu_id, baohong_id, huonggiao_id, tinh_id, phieu_tinh_id} = itemBaoHong;
                const huonggiao_id_tinh  = huonggiao_id;
                
                if (huonggiao_id_tinh == 8888) {

                    //HoanThien_BH_Net_Tinh
                    const param = { vttph_id: 4, vphieu_id: phieu_id, vnoidung_xuly: this.txtGhiChuTH }

                    await api.hoanThienBaoHongNetTinh(this.axios, param)

                } else {

                    //check phieu con hoan cong
                    if (!(await this.checkPhieuConHoanCong(phieu_id))) return;

                    const vnhanvien_id = this.$root.token.getNhanVienID();

                    if (itemBaoHong.log_giao?.includes('800126->VNPT-NET')) {
                 
                      const responseGPDVTTV3 =  await api.giaoPhieuDonViTiepTheoV3(this.axios, { vbaohong_id: baohong_id, vphieu_id: phieu_tinh_id, vhuonggiao_id: huonggiao_id_tinh, vphanvung_id:  this.tinhThiCongId })
                     
                      if(!apiHelper.isResponseApiSuccess(responseGPDVTTV3))
                            srtError += responseGPDVTTV3.data.message_detail || responseGPDVTTV3.data.message
                    } else {
                        const param = {
                            baohong_id: baohong_id,
                            phieu_id: phieu_tinh_id,
                            nhanvien_id: vnhanvien_id,
                            huonggiao_id: huonggiao_id_tinh,
                            may_cn: this.mayCN,
                            ngay_cn: this.systemDate,
                            nguoi_cn: this.$root.token.getUserName(),
                            phanvung_id: tinh_id
                        }
                        const resGPDVTT =  await api.giaoPhieuDonViTiepTheo(this.axios, param);

                        if(!apiHelper.isResponseApiSuccess(resGPDVTT))
                            srtError += resGPDVTT.data.message_detail || resGPDVTT.data.message
                    }

                    //call api CAPNHAT_NHANVIEN_TH
                    const resCapNhatNhanVienTH = await api.capNhatNhanVienTH(this.axios, { vphieu_id: phieu_tinh_id, vnhanvien_th_id: vnhanvien_id, vngay_th: moment(new Date()).format('DD/MM/yyyy HH:mm:ss') })
                    if(!apiHelper.isResponseApiSuccess(resCapNhatNhanVienTH))
                            srtError += resCapNhatNhanVienTH.data.message_detail || resCapNhatNhanVienTH.data.message

                    //call api SP_CAPNHAT_GHICHU_TH
                   const  resCapNhatGhiChu = await api.capNhatGhiChu(this.axios, { vghichu_th: this.txtGhiChuTH, vngay_cn: this.systemDate, vphieu_id: phieu_tinh_id, vbaohong_id: baohong_id , phanvung_id: tinh_id})
                   if(!apiHelper.isResponseApiSuccess(resCapNhatGhiChu))
                            srtError += resCapNhatGhiChu.data.message_detail || resCapNhatGhiChu.data.message
                    //set trang thai giao phieu
                  const resCapNhatTTGiaoPhieu =  await api.capNhaTrangThaiGiaoPhieu(this.axios, { vphieu_id: phieu_id })
                  if(!apiHelper.isResponseApiSuccess(resCapNhatTTGiaoPhieu))
                            srtError += resCapNhatTTGiaoPhieu.data.message_detail || resCapNhatTTGiaoPhieu.data.message
                }

                return srtError
            } catch (err) {
                console.log(err)
                return "Chuyen TTDH có lỗi! Vui lòng liên hệ admin! "
            }

        },

        //xư lý chuyển phiếu là khách hàng doanh nghiệp
        async handleChuyenTTDH_KHDN(baoHongItem) {
            const {tinh_id, thuebao_id_thicong ,loaitb_id, baohong_id, } = baoHongItem;
            let srtError = ""
            try {
                if (tinh_id && thuebao_id_thicong > 0) {
                    //call capi KIEMTRA_THUEBAO_BAOHONG
                    const resCheckTBBH = await api.kiemTraThueBaoBH(this.axios, { tinhthicong_id: this.tinhThiCongId, vthuebao_id: baoHongItem.thuebao_id });

                    const check = apiHelper.getDataFromResponseApiReturnRaise(resCheckTBBH);
                    
                    if (check != 1) {
                        return check;
                    }
                }

                const dataBH = await this.createBaoHongData(baoHongItem);
                const { dsBH_DATA, dsBH_BC_DATA } = dataBH;

                let baohong_id_tc = 0
                if (dsBH_DATA) {
                    baohong_id_tc = dsBH_BC_DATA.BAOHONG_ID_THICONG;
                    //call  DatabaseConstants.DB7 + ".BaoHong_BC set baohong_id_thicong
                    const response = await api.updateBaoHong_BanCheoBH(this.axios, { vbaohong_id_tc: baohong_id_tc, vbaohong_id_ban: baoHongItem.baohong_id })
                    if(!apiHelper.isResponseApiSuccess(response))
                        srtError += response.data.message_detail || response.data.message
                }

                //call  Nhan_hong(tinh_tc, dsBH_DATA, dsBH_BC_DATA, loaitb_id, 1, "", tt_nd.may_cn, tt_nd.ngay_cn, tt_nd.ma_nd, thuebao_id_thicong, baohong_id, 1)
                const paramTiepNhanBH = {
                    tinh_tc: tinh_id,
                    vloaitb_id: loaitb_id,
                    vthutu: 1,
                    vthuebao_id: thuebao_id_thicong,
                    vbaohong_id: baohong_id,
                    dactrung: "",
                    net_giao: 1,
                    ds: [dsBH_DATA],
                    dsBH_BC: [dsBH_BC_DATA]
                }


                const responseTNBH = await api.tiepNhanBaoHong(this.axios, paramTiepNhanBH)

                if(!apiHelper.isResponseApiSuccess(responseTNBH))
                        srtError += responseTNBH.data.message_detail || responseTNBH.data.message

                if (baohong_id_tc != 0) {

                    //call api lay thong tin nguoi cn: giaophieu
                    const nguoiCN = await apiHelper.getUserBHInfo(this.axios, this.baoHongId);
                
                    //call api net_giaophieu_tiepnhan  
                    const responseGiaoPHieuTNNet =   await api.giaoPhieuTiepNhanNet(this.axios, { tinhthicong_id: this.tinhThiCongId, vbaohong_id: baohong_id_tc, vloaitb_id: this.baoHongInfo.loaitb_id, nguoi_cn: nguoiCN })

                    if(!apiHelper.isResponseApiSuccess(responseGiaoPHieuTNNet))
                        srtError += responseGiaoPHieuTNNet.data.message_detail || responseGiaoPHieuTNNet.data.message

                    const phieu_id_tc = await apiHelper.getThongTinGiaoPhieu(this.axios, { tinhthicong_id: this.tinhThiCongId, vbaohong_id: baohong_id_tc, vtype: 1 });

                    if (thuebao_id_thicong == -1) {
                        srtError = "Báo hỏng kênh trắng không chuyển được cho Trung tâm viễn thông!";
                        // return srtError
                    } else{
                        const huonggiao_id_tc = await apiHelper.getThongTinGiaoPhieu(this.axios, { tinhthicong_id: this.tinhThiCongId, vbaohong_id: baohong_id_tc, vtype: 2 });
                    //call api giaophieu_donvi_tieptheo_ttvt_v2
                        const params = {
                            tinhthicong_id: this.tinhThiCongId,
                            vbaohong_id: baohong_id_tc,
                            vphieu_id: phieu_id_tc,
                            vhuonggiao_id: huonggiao_id_tc,
                            vnguoi_cn_dai: nguoiCN ,
                            vnguoi_cn_net: this.$auth.getUserName(),
                            vthuebao_id: thuebao_id_thicong,
                            vloaitb_id: loaitb_id
                        };

                    const rsGiaoPhieuTTV2 =  await api.giaoPhieuDVTT_TTVTV2(this.axios, params);
                    if(!apiHelper.isResponseApiSuccess(rsGiaoPhieuTTV2))
                        srtError += rsGiaoPhieuTTV2.data.message_detail || rsGiaoPhieuTTV2.data.message
                    } 
                }

                //call api ban cheo giaophieu_net_donvi_tieptheo
                const parmGPNet = {
                    tinhthicong_id:this.tinhThiCongId  ,
                    vbaohong_id: this.baoHongId,
                    vphieu_id: baoHongItem.phieu_id,
                    vhuonggiao_id: 9980,
                    vtinh_id: this.$root.token.getPhanVungID(), //truyen phan vung ID
                    vdonvi_nhan_id: 9980,
                }
                    
                   const resGPDVTT =  await api.giaoPhieuNetDVTT(this.axios, parmGPNet)
                   if(!apiHelper.isResponseApiSuccess(resGPDVTT))
                            srtError += resGPDVTT.data.message_detail || resGPDVTT.data.message
                    //DatabaseConstants.DB7 + ".BaoHong_BC set Trang_thai= trang_thai
                    const resUpdateTrangThai = await  api.updateTrangThaiBaoHongV2(this.axios, { vtrang_thai: 3, vbaohong_id_ban: this.baoHongId });
                    if(!apiHelper.isResponseApiSuccess(resUpdateTrangThai))
                            srtError += resUpdateTrangThai.data.message_detail || resUpdateTrangThai.data.message
                    
                  return srtError;
    
            } catch (err) {
         
                console.log(err)
                return "Chuyen TTDH có lỗi! Vui lòng liên hệ admin! "
            }
        },

        async upLoadFileToMinio(file) {

            try {
                const formData = new FormData();
                formData.append('files', file);
                const response = await api.upLoadFileToMinio(this.axios, formData);
                const url = response.data.data[0];
                const name = url.substring(url.lastIndexOf('/') + 1);
                return { url, name }
            } catch (err) {
                this.$toast.error(err);
                console.log(err);
                throw new Error(err)
            }
        },

        async insertHoSoTBKhachHangDoanhNghiep({ fileId, url, name }) {
            const responseInsertHS = await api.insertFileHS(this.axios, {
                vfile_id: fileId,
                vurl: url,
                vfile_name: name
            });
            const response = await api.addFileHSToThueBao(this.axios,
                {
                    p_thuebao_id: this.baoHongInfo.thuebao_id,
                    p_file_id: fileId,
                    p_ma_tb: this.baoHongInfo.ma_tb,
                    p_baohong_id: this.baoHongId
                });

            if (apiHelper.isResponseApiSuccess(response)) {
                this.loading(false);
                this.$toast.success("Upload File HS Thành Công")
            } else {
                this.loading(false);
                this.$toast.error("Upload File HS Không Thành Công")
            }
        },

        async updateBienBanNghiemThuBHNet({ url, name }) {

            const response = await api.updateBienBanNghiemThuBH(this.axios, {
                thuebao_id: this.baoHongInfo.thuebao_id,
                phanvung_id: this.tinhThiCongId,
                baohong_id: this.baoHongId,
                url,
                file_name: name,
                ma_tb: this.baoHongInfo.ma_tb,
                ip_cn: this.ipCn,
                may_cn: this.mayCN,
                nguoi_cn: this.$root.token.getUserName(),
            });
            if (apiHelper.isResponseApiSuccess(response)) {
                this.loading(false);
                this.$toast.success("Upload File HS Thành Công")
            } else {
                this.loading(false);
                this.$toast.error("Upload File HS Không Thành Công")
            }
        },

        hanldeThongTinXulyUpdate(data) {
            // console.log("ThongTinXuly", data);
            if (!data) return;

            this.kqXlId = data.kqXlId || 1;
            this.txtNoiDungXL = data.noiDung || '';
            this.nguyenNhanBH = data.nguyenNhan;
        },

        async checkPhieuConHoanCong(phieuId) {
            const checkPhieuConHC = await apiHelper.isPhieuConHoanCong(this.axios, phieuId)
            if (!checkPhieuConHC) {
                this.$toast.error("Các phiếu con chưa Hoàn công!");
                return false;
            }
            return true
        },


        hanldeGetListBaoHong(data) {
            this.listBaoHong = data;
        },

        hanldeTinhTrangPhieuChange(data) {
            this.ttbh_id = data;
        },
    }
}

</script>
<style>
.non-ative a {
    color: #d3d3d3 !important;
}

.non-ativea {
    color: #d3d3d3 !important;
}

.hoso-dauchuyen-daunoi-thuebao .page-content {
    overflow: visible !important;
    height: fit-content;
    background-color: white;
}

.popup-show-port .page-content {
    position: inherit;
}

.popup-show-port .breadcrumb-top {
    display: none;
}

.thanhtoan-modal .breadcrumb-top {
    display: none;
}

.chuyenTo .page-content {
    top: 130px !important;
}

.chuyenToPopupBody {
    min-height: 800px;
}

.chuyenToPopupBody .page-content {
    top: 0px !important;
    position: inherit !important;
}

.chuyenToPopupBody .page-content {
    top: 0px !important;
    position: inherit !important;
}

#popupComponentChuyenTos .page-content {
    position: inherit !important;
}

.vue-treeselect--has-value .vue-treeselect__multi-value {
    margin-bottom: 5px;
    height: 96px !important;
    overflow: scroll;
}

.thanhtoan-modal .main-wrapper {
    position: unset !important;
}

#bfrmTestPortV2 .modal-xl {
    max-width: 90% !important;
}

.chuyenTo .page-content {
    top: 130px !important;
}

.chuyenToPopupBody {
    min-height: 800px;
}

.chuyenToPopupBody .page-content {
    top: 0px !important;
    position: inherit !important;
}

.chuyenToPopupBody .page-content {
    top: 0px !important;
    position: inherit !important;
}

#popupComponentChuyenTos .page-content {
    position: inherit !important;
}

.vue-treeselect--has-value .vue-treeselect__multi-value {
    margin-bottom: 5px;
    height: 96px !important;
    overflow: scroll;
}

.mx-input:disabled {
    color: #444040 !important;
}

.inPopup {
    position: fixed;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    background-color: rgba(0, 0, 0, 0.5);
    display: flex;
    justify-content: center;
    align-items: center;
}

.onPopup {
    background-color: white;
    padding: 20px;
    border-radius: 5px;
    box-shadow: 0px 0px 10px rgba(0, 0, 0, 0.5);
}
</style>
