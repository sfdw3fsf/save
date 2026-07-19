<template src="./frmThongSoTSL.html"></template>
<script>
import api from "./frmThongSoTSL.js";
import moment from "moment";
import { CheckBoxSelection } from "@syncfusion/ej2-vue-dropdowns";
import { Query } from '@syncfusion/ej2-data'
import { LoaiHopDong, LoaiHinhTB } from '@/modules/contract/setup/TransferRightsCrossSell/Enums.js'
import { L10n } from '@syncfusion/ej2-base'
L10n.load({
  'vi-VN': {
    'dropdowns': {
      'noRecordsTemplate': "Không có dữ liệu",
      'actionFailureTemplate': ""
    },
    "multi-select": {
      noRecordsTemplate: "Không có dữ liệu",
      overflowCountTemplate: "và ${count} giá trị khác..",
      totalCountTemplate: "${count} giá trị",
    },
  }
})
export default {
    name:'frmThongSoTSL',
    provide: {
        multiselect: [CheckBoxSelection]
    },
    data() {
        return {
            thongso_Ban: {},
            thongso_TC: {},
            ngay: "",
            pLoaihinhTB: null,
            tinhtc: {},
            kt_load: true,      
            ngay_tc: moment(new Date()).format("DD/MM/YYYY"),
            kenhtrang: false,
            vloaihd_id: null,
            ht_tskt_vnpti: false,
            tinh_chuquan_id: "-1",
            model: {
                chkQuaVNPT: {
                    value: true,
                    visible: false
                },
                cboChuQuanLD_Ban: {
                    list: [],
                    value: null
                },
                cboThietbiCuoiBan: {
                    list: [],
                    value: null
                },
                cboThietbiDauBan: {
                    list: [],
                    value: null
                },
                cboMuccuoctbBan: {
                    list: [],
                    value: null
                },
                cboDoituongBan: {
                    list: [],
                    value: null
                },
                cboLoaikenhBan: {
                    list: [],
                    value: null
                },
                cboChuquanBan: {
                    list: [],
                    value: null
                },
                cboTocdoBan: {
                    list: [],
                    value: null
                },
                cboTrangbiBan: {
                    list: [],
                    value: null
                },
                cboNhomLoaiKenh_Ban: {
                    list: [],
                    value: "",
                    disabled: false
                },
                cboYCBV_Ban: {
                    list: [
                        {
                            id: 1,
                            text: "Có bảo vệ"
                        },
                        {
                            id: 2,
                            text: "Không có bảo vệ"
                        }
                    ],
                    value: ""
                },
                cboChuQuanLD_TC: {
                    list: [],
                    value: null
                },
                cboThietbiCuoiTC: {
                    list: [],
                    value: null
                },
                cboThietbiDauTC: {
                    list: [],
                    value: null
                },
                cboMuccuoctbTC: {
                    list: [],
                    value: null
                },
                cboTocdoTC: {
                    list: [],
                    value: null
                },
                cboLoaikenhTC: {
                    list: [],
                    value: null
                },
                cboTrangbiTC: {
                    list: [],
                    value: null
                },
                cboDoituongTC: {
                    list: [],
                    value: null
                },
                cboChuquanTC: {
                    list: [],
                    value: null
                },
                cboKieuLD: {
                    list: [],
                    value: null
                },

                // panelControl1
                cboNT_DV_BAN: {
                    list: [],
                    value: []
                },
                txtSNCP_BAN: "",
                txtMTU_Size_BAN: "",
                txtAuto_Nego_BAN: "",   
                txtCapDP_BAN: "",
                txtCapChinh_BAN: "",
                cboGiaoDienKH_BAN: {
                    list: [],
                    value: null
                },    
                cboTocDoPOP_BAN: {
                    list: [],
                    value: null
                },
                cboMHKN_BAN: {
                    list: [],
                    value: null
                },
                
                // panelControl2
                cboNT_DV_TC: {
                    list: [],
                    value: []
                }, 
                txtSNCP_TC: "",
                txtMTU_Size_TC: "",
                txtAuto_Nego_TC: "",
                txtCapDP_TC: "",
                txtCapChinh_TC: "",
                cboGiaoDienKH_TC: {
                    list: [],
                    value: null
                }, 
                cboTocDoPOP_TC: {
                    list: [],
                    value: null
                }, 
                cboMHKN_TC: {
                    list: [],
                    value: null
                },
                cboNhomLoaiKenh_TC: {
                    list: [],
                    value: "",
                    disabled:false
                },
                cboYCBV_TC: {
                    list: [
                        {
                            id: 1,
                            text: "Có bảo vệ"
                        },
                        {
                            id: 2,
                            text: "Không có bảo vệ"
                        }
                    ],
                    value: ""
                }
            },
            visible: {
                panelControl1: true,
                panelControl2: true
            },
            enable: {
                cboDoituongBan: true,
                cboLoaikenhBan: true,
                cboMuccuoctbBan: true,
                cboThietbiCuoiBan: true,
                cboThietbiDauBan: true,
                cboTocdoBan: true,
                cboDoituongTC: true,
                cboLoaikenhTC: true,
                cboMuccuoctbTC: true,
                cboThietbiDauTC: true,
                cboThietbiCuoiTC: true,
                cboTocdoTC: true
            }
        }
    },
    methods: {
        refresh() {
            this.thongso_Ban = {}
            this.thongso_TC = {}
            this.pLoaihinhTB = null
            this.tinhtc = {}
            this.kt_load = true,      
            this.kenhtrang = false,
            this.vloaihd_id = null,
            this.ht_tskt_vnpti = false,
            this.tinh_chuquan_id = "-1",

            this.model.cboChuQuanLD_Ban = {list: [], value: null}
            this.model.cboThietbiCuoiBan = {list: [], value: null}
            this.model.cboThietbiDauBan = {list: [], value: null}
            this.model.cboMuccuoctbBan = {list: [], value: null}
            this.model.cboDoituongBan = {list: [], value: null}
            this.model.cboLoaikenhBan = {list: [], value: null}
            this.model.cboChuquanBan = {list: [], value: null}
            this.model.cboTocdoBan = {list: [], value: null}
            this.model.cboTrangbiBan = {list: [], value: null}
            this.model.cboNT_DV_BAN = {list: [], value: null}
            this.txtSNCP_BAN = ""
            this.txtMTU_Size_BAN = ""
            this.txtAuto_Nego_BAN = ""
            this.txtCapDP_BAN = ""
            this.txtCapChinh_BAN = ""
            this.model.cboGiaoDienKH_BAN = {list: [], value: null}
            this.model.cboTocDoPOP_BAN = {list: [], value: null}
            this.model.cboMHKN_BAN = {list: [], value: null}

            this.model.cboChuQuanLD_TC = {list: [], value: null}
            this.model.cboThietbiCuoiTC = {list: [], value: null}
            this.model.cboThietbiDauTC = {list: [], value: null}
            this.model.cboMuccuoctbTC = {list: [], value: null}
            this.model.cboTocdoTC = {list: [], value: null}
            this.model.cboLoaikenhTC = {list: [], value: null}
            this.model.cboTrangbiTC = {list: [], value: null}
            this.model.cboDoituongTC = {list: [], value: null}
            this.model.cboChuquanTC = {list: [], value: null}
            this.model.cboKieuLD = {list: [], value: null}
            this.model.cboNT_DV_TC = {list: [], value: null}
            this.txtSNCP_TC = ""
            this.txtMTU_Size_TC = ""
            this.txtAuto_Nego_TC = ""
            this.txtCapDP_TC = ""
            this.txtCapChinh_TC = ""
            this.model.cboGiaoDienKH_TC = {list: [], value: null}
            this.model.cboTocDoPOP_TC = {list: [], value: null}
            this.model.cboMHKN_TC = {list: [], value: null}
            this.model.cboNhomLoaiKenh_Ban = {list: [], value: "", disabled: false}
            this.model.cboYCBV_Ban = {
                list: [
                    {
                        id: 1,
                        text: "Có bảo vệ"
                    },
                    {
                        id: 2,
                        text: "Không có bảo vệ"
                    }
                ], 
                value: ""
            }
            this.model.cboNhomLoaiKenh_TC = {list: [], value: "", disabled: false}
            this.model.cboYCBV_TC = {
                list: [
                    {
                        id: 1,
                        text: "Có bảo vệ"
                    },
                    {
                        id: 2,
                        text: "Không có bảo vệ"
                    }
                ], 
                value: ""
            }
            this.model.chkQuaVNPT.value = true
        },
        async openDialog(tinh, ploaitb_ld, ploaitb, kieuld_id, pthongso_Ban, pthongso_TC, vloaihd, vkenhtrang) {
            try {
                
                this.$refs.dlg_thongsotsl.show()
                this.loading(true)
                this.refresh()
                this.kt_load = true;
                this.pLoaihinhTB = ploaitb;
                this.tinhtc = tinh;
                this.thongso_Ban = JSON.parse(JSON.stringify(pthongso_Ban));
                this.thongso_TC = JSON.parse(JSON.stringify(pthongso_TC));
                this.kenhtrang = vkenhtrang;
                console.log('pthongso_Ban', JSON.parse(JSON.stringify(pthongso_Ban)))
                console.log('pthongso_TC', JSON.parse(JSON.stringify(pthongso_TC)))
                console.info('openDialog',tinh, ploaitb_ld, ploaitb, kieuld_id, 'tsoban',JSON.stringify(pthongso_Ban), 'tsotc',JSON.stringify(pthongso_TC), vloaihd, vkenhtrang)
                // Doi tuong
                let temps = await this.HT_DoiTuong_Combobox();
                if (temps && temps.length > 0) {
                    this.model.cboDoituongBan.list = temps.map((x)=>({id:x.doituong_id, text:x.ten_dt}))
                    if (this.thongso_Ban.doituong_id == undefined || this.thongso_Ban.doituong_id == 0)
                        this.model.cboDoituongBan.value = temps[0].doituong_id
                    else
                        this.model.cboDoituongBan.value = this.thongso_Ban.doituong_id;                
                }
                temps = await this.load_Dm_Doi_tuong(this.tinhtc.tinhthicong_id);
                if (temps && temps.length > 0) {
                    this.model.cboDoituongTC.list = temps.map((x)=>({id:x.doituong_id, text:x.ten_dt}))
                    if (this.thongso_TC.doituong_id == undefined || this.thongso_TC.doituong_id == 0)
                        this.model.cboDoituongTC.value = temps[0].doituong_id
                    else
                        this.model.cboDoituongTC.value = this.thongso_TC.doituong_id;                
                }

                // Kieu LD
                temps = await this.HT_KIEULD_LOAITB(this.tinhtc.tinhthicong_id, vloaihd, ploaitb_ld);
                this.vloaihd_id = vloaihd;
                if (temps && temps.length > 0) {
                    this.model.cboKieuLD.list = temps.map((x)=>({id:x.kieuld_id, text:x.ten_kieuld}))
                    if (this.thongso_TC.kieuld_id == undefined || this.thongso_TC.kieuld_id == 0)
                        this.model.cboKieuLD.value = this.model.cboKieuLD.list[0].id;
                    else
                        this.model.cboKieuLD.value = this.thongso_TC.kieuld_id;         
                } else
                    this.$toast.error("Không tìm thấy kiểu lắp đặt ở bên thi công ứng với loại hợp đồng và loại thuê bao này.");

                // Loai kenh
                temps = await this.lay_thongtin_ds_loaikenh()
                if (temps && temps.length > 0) {
                    this.model.cboLoaikenhBan.list = temps.map((x)=>({id:x.loaikenh_id, text:x.loai_kenh}))
                    if (this.thongso_Ban.loaikenh_id == undefined || this.thongso_Ban.loaikenh_id == 0)
                        this.model.cboLoaikenhBan.value = temps[0].loaikenh_id
                    else
                        this.model.cboLoaikenhBan.value = this.thongso_Ban.loaikenh_id;                
                }
                temps = await this.HT_Loai_Kenh_Combobox(this.tinhtc.tinhthicong_id);
                if (temps && temps.length > 0) {
                    this.model.cboLoaikenhTC.list = temps.map((x)=>({id:x.loaikenh_id, text:x.loai_kenh}))
                    if (this.thongso_TC.loaikenh_id == undefined || this.thongso_TC.loaikenh_id == 0)
                        this.model.cboLoaikenhTC.value = temps[0].loaikenh_id
                    else
                        this.model.cboLoaikenhTC.value = this.thongso_TC.loaikenh_id;                
                }

                // Chu quan
                this.tinh_chuquan_id = await this.LAY_CHUQUAN_MACDINH_THEO_DONVI_BANCHEO()

                let ds_cq_ban = await this.LAY_DS_CHUQUAN()
                if (ds_cq_ban && ds_cq_ban.length > 0) {
                    this.model.cboChuquanBan.list = ds_cq_ban.map((x)=>({id:x.chuquan_id, text:x.tenchuquan}))
                    this.model.cboChuQuanLD_Ban.list = ds_cq_ban.map((x)=>({id:x.chuquan_id, text:x.tenchuquan}))
                }

                let ds_cq_tc = await this.LOAD_DM_CHU_QUAN(this.tinhtc.tinhthicong_id)
                if (ds_cq_tc && ds_cq_tc.length > 0) {
                    this.model.cboChuquanTC.list = ds_cq_tc.map((x)=>({id:x.chuquan_id, text:x.tenchuquan}))
                    this.model.cboChuQuanLD_TC.list = ds_cq_tc.map((x)=>({id:x.chuquan_id, text:x.tenchuquan}))  
                }

                let det = [];
                if (this.tinh_chuquan_id == "-1")
                    det = ds_cq_ban.filter(item => item.tinh_id == this.$root.token.getPhanVungID())
                else
                    det = ds_cq_ban.filter(item => item.tinh_id == this.tinh_chuquan_id)

                if (det.length > 0) {
                    let row = det[0];
                    if (row.chuquan_id != undefined && row.chuquan_id != "")
                        this.model.cboChuquanBan.value = Number(row.chuquan_id);
                }

                det = ds_cq_ban.filter(item => item.tinh_id == this.tinhtc.tinhthicong_id)
                if (det.length > 0) {
                    let row = det[0];
                    if (row.chuquan_id != undefined && row.chuquan_id != "")
                        this.model.cboChuQuanLD_Ban.value = Number(row.chuquan_id);
                }

                if (this.tinh_chuquan_id == "-1")
                    det = ds_cq_tc.filter(item => item.tinh_id == this.$root.token.getPhanVungID())
                else
                    det = ds_cq_tc.filter(item => item.tinh_id == this.tinh_chuquan_id)

                if (det.length > 0) {
                    let row = det[0];
                    if (row.chuquan_id != undefined && row.chuquan_id != "")
                        this.model.cboChuquanTC.value = Number(row.chuquan_id);
                }

                det = ds_cq_tc.filter(item => item.tinh_id == this.tinhtc.tinhthicong_id)
                if (det.length > 0) {
                    let row = det[0];
                    if (row.chuquan_id != undefined && row.chuquan_id != "")
                        this.model.cboChuQuanLD_TC.value = Number(row.chuquan_id);
                }

                // Toc do kenh
                temps = await this.lay_thongtin_ds_tocdo_kenh()
                if (temps && temps.length > 0) {
                    this.model.cboTocdoBan.list = temps.map((x)=>({id:x.tocdo_id, text:x.toc_do}))
                    this.model.cboTocDoPOP_BAN.list = temps.map((x)=>({id:x.tocdo_id, text:x.toc_do}))

                    if (this.thongso_Ban.tocdo_id == undefined || this.thongso_Ban.tocdo_id == 0)
                        this.model.cboTocdoBan.value = temps[0].tocdo_id
                    else
                        this.model.cboTocdoBan.value = this.thongso_Ban.tocdo_id;                

                    if (this.thongso_Ban.tocdo_pop != undefined && this.thongso_Ban.tocdo_pop > 0)
                        this.model.cboTocDoPOP_BAN.value = this.thongso_Ban.tocdo_pop;
                }
                temps = await this.HT_Tocdo_Kenh_Combobox(this.tinhtc.tinhthicong_id);
                if (temps && temps.length > 0) {
                    this.model.cboTocdoTC.list = temps.map((x)=>({id:x.tocdo_id, text:x.toc_do}))
                    this.model.cboTocDoPOP_TC.list = temps.map((x)=>({id:x.tocdo_id, text:x.toc_do}))

                    if (this.thongso_TC.tocdo_id == undefined || this.thongso_TC.tocdo_id == 0)
                        this.model.cboTocdoTC.value = temps[0].tocdo_id
                    else
                        this.model.cboTocdoTC.value = this.thongso_TC.tocdo_id;
                        
                    if (this.thongso_TC.tocdo_pop != undefined && this.thongso_TC.tocdo_pop > 0)
                        this.model.cboTocDoPOP_TC.value = this.thongso_TC.tocdo_pop;
                }
                
                // Trang bi
                temps = await this.lay_thongtin_ds_trangbi()
                if (temps && temps.length > 0) {
                    this.model.cboTrangbiBan.list = temps.map((x)=>({id:x.trangbi_id, text:x.tentrangbi}))
                    if (this.thongso_Ban.trangbi_id == undefined || this.thongso_Ban.trangbi_id == 0)
                        this.model.cboTrangbiBan.value = temps[0].trangbi_id
                    else
                        this.model.cboTrangbiBan.value = this.thongso_Ban.trangbi_id;                
                }
                temps = await this.HT_TrangBi_Combobox(this.tinhtc.tinhthicong_id);
                if (temps && temps.length > 0) {
                    this.model.cboTrangbiTC.list = temps.map((x)=>({id:x.trangbi_id, text:x.tentrangbi}))
                    if (this.thongso_TC.trangbi_id == undefined || this.thongso_TC.trangbi_id == 0)
                        this.model.cboTrangbiTC.value = temps[0].trangbi_id
                    else
                        this.model.cboTrangbiTC.value = this.thongso_TC.trangbi_id;                
                }

                // Muc cuoc
                temps = await this.LAY_DS_MUCUOC_TB_V2(0, 0, 0, 0, this.pLoaihinhTB, Number(this.model.cboDoituongBan.value), 0, 0, this.ngay, 
                    Number(this.model.cboTrangbiBan.value));
                if (temps && temps.length > 0) {
                    this.model.cboMuccuoctbBan.list = temps.map((x)=>({id:x.muccuoctb_id, text:x.tenmuccuoc}))
                    if (this.thongso_Ban.muccuoctb_id == undefined || this.thongso_Ban.muccuoctb_id == 0)
                        this.model.cboMuccuoctbBan.value = temps[0].muccuoctb_id
                    else
                        this.model.cboMuccuoctbBan.value = this.thongso_Ban.muccuoctb_id;     
                    
                }
                temps = await this.Lay_DS_MucCuoc_TB(this.tinhtc.tinhthicong_id, 0, 0, 0, 0, this.pLoaihinhTB, Number(this.model.cboDoituongTC.value), 
                    this.ngay, Number(this.model.cboTrangbiTC.value), Number(this.model.cboTocdoTC.value), 0);
                if (temps && temps.length > 0) {
                    this.model.cboMuccuoctbTC.list = temps.map((x)=>({id:x.muccuoctb_id, text:x.tenmuccuoc}))
                    if (this.thongso_TC.muccuoctb_id == undefined || this.thongso_TC.muccuoctb_id == 0)
                        this.model.cboMuccuoctbTC.value = temps[0].muccuoctb_id
                    else
                        this.model.cboMuccuoctbTC.value = this.thongso_TC.muccuoctb_id;     
                }

                // Loai TB
                temps = await this.HT_LOAI_TB_LHTB_Combobox(this.$root.token.getPhanVungID(), ploaitb)
                if (temps && temps.length > 0) {
                    this.model.cboThietbiDauBan.list = temps.map((x)=>({id:x.thietbidc_id, text:x.ten_tbi}))
                    this.model.cboThietbiCuoiBan.list = temps.map((x)=>({id:x.thietbidc_id, text:x.ten_tbi}))

                    if (this.thongso_Ban.thietbidc_id == undefined || this.thongso_Ban.thietbidc_id == 0) {
                        this.model.cboThietbiDauBan.value = temps[0].thietbidc_id
                    } else {
                        this.model.cboThietbiDauBan.value = this.thongso_Ban.thietbidc_id;
                    }

                    if (this.thongso_Ban.thietbidc_id_cuoi == undefined || this.thongso_Ban.thietbidc_id_cuoi == 0) {
                        this.model.cboThietbiCuoiBan.value = temps[0].thietbidc_id_cuoi
                    } else {
                        this.model.cboThietbiCuoiBan.value = this.thongso_Ban.thietbidc_id_cuoi;
                    }                     
                }

                temps = await this.HT_LOAI_TB_LHTB_Combobox(this.tinhtc.tinhthicong_id, ploaitb)
                if (temps && temps.length > 0) {
                    this.model.cboThietbiDauTC.list = temps.map((x)=>({id:x.thietbidc_id, text:x.ten_tbi}))
                    this.model.cboThietbiCuoiTC.list = temps.map((x)=>({id:x.thietbidc_id, text:x.ten_tbi}))

                    if (this.thongso_TC.thietbidc_id == undefined || this.thongso_TC.thietbidc_id == 0) {
                        this.model.cboThietbiDauTC.value = temps[0].thietbidc_id
                    } else {
                        this.model.cboThietbiDauTC.value = this.thongso_TC.thietbidc_id;
                    }

                    if (this.thongso_TC.thietbidc_id_cuoi == undefined || this.thongso_TC.thietbidc_id_cuoi == 0) {
                        this.model.cboThietbiCuoiTC.value = temps[0].thietbidc_id_cuoi
                    } else {
                        this.model.cboThietbiCuoiTC.value = this.thongso_TC.thietbidc_id_cuoi;
                    }                     
                }
                if(this.thongso_Ban.yc_baove > 0){
                    this.model.cboYCBV_Ban.value = this.thongso_Ban.yc_baove
                }
                if(this.thongso_TC.yc_baove > 0){
                    this.model.cboYCBV_TC.value = this.thongso_TC.yc_baove
                }
                
                if(this.thongso_Ban.nhomloaikenh_id > 0){
                    this.model.cboNhomLoaiKenh_Ban.value = this.thongso_Ban.nhomloaikenh_id
                }
                if(this.thongso_TC.nhomloaikenh_id > 0){
                    this.model.cboNhomLoaiKenh_TC.value = this.thongso_TC.nhomloaikenh_id
                }
                
                this.model.chkQuaVNPT.value = true 
                await this.cboNhomLoaiKenh_Data()
                if(this.pLoaihinhTB == 320 || this.pLoaihinhTB == 257 || this.pLoaihinhTB || 258){
                    if(this.thongso_TC.hatang_vnpt == null  || this.thongso_TC.hatang_vnpt == 1 || this.thongso_TC.hatang_vnpt == 0){
                        this.model.chkQuaVNPT.visible = true
                        this.model.chkQuaVNPT.value = true
                    }
                    else if(this.thongso_TC.hatang_vnpt == 2){
                        this.model.chkQuaVNPT.visible = true
                        this.model.chkQuaVNPT.value = false
                    }
                }
                else{
                    this.model.cboNhomLoaiKenh_Ban.disabled = true
                    this.model.cboNhomLoaiKenh_TC.disabled = true
                    this.model.chkQuaVNPT.visible = false
                }

                if(this.model.chkQuaVNPT.value != true){
                    this.model.cboNhomLoaiKenh_TC.disabled = false
                    this.model.cboNhomLoaiKenh_Ban.disabled = false 
                }
                else{
                    this.model.cboNhomLoaiKenh_TC.disabled = true
                    this.model.cboNhomLoaiKenh_Ban.disabled = true 
                }

                let input={
                p_phanvung_id:this.$root.token.getPhanVungID(),
                p_ds_para:JSON.stringify({
                    LOAITB_ID:this.loaitb,
                    TINH_ID:this.tinhtc.tinhthicong_id
                })
                }
                let data=this.GetData1(await api.fn_frmthongsotsl_load(this.axios, input))
                console.log('fn_frmthongsotsl_load', data)
                if(data){
                    this.model.cboMHKN_BAN.list = data.CBOMHKN.map((x) => ({ id: x.MHKN_ID, text: x.MOHINH_KN }))
                    this.model.cboMHKN_TC.list=data.CBOMHKN.map((x) => ({ id: x.MHKN_ID, text: x.MOHINH_KN }))
                    if (this.thongso_Ban.mhkn_id != undefined && this.thongso_Ban.mhkn_id > 0){
                        this.model.cboMHKN_BAN.value = this.thongso_Ban.mhkn_id
                    }else{
                         this.model.cboMHKN_BAN.value = this.model.cboMHKN_TC.list[0].id
                    }
                        

                    if (this.thongso_TC.mhkn_id != undefined && this.thongso_TC.mhkn_id > 0){
                        this.model.cboMHKN_TC.value = this.thongso_TC.mhkn_id; 
                    }else{
                        this.model.cboMHKN_TC.value = this.model.cboMHKN_TC.list[0].id
                    }

                    this.model.cboNT_DV_BAN.list=data.DT_KIEU_NT.map((x) => ({ id: x.KIEUNT_ID, text: x.KIEU_NGHIEMTHU }))
                    this.model.cboNT_DV_TC.list=data.DT_KIEU_NT.map((x) => ({ id: x.KIEUNT_ID, text: x.KIEU_NGHIEMTHU }))
                    console.log('thongso_Ban.kieu_nghiemthu', this.thongso_Ban.kieu_nghiemthu)
                    console.log('thongso_TC.kieu_nghiemthu', this.thongso_TC.kieu_nghiemthu)
                    if(this.thongso_Ban.kieu_nghiemthu&&this.thongso_Ban.kieu_nghiemthu.length>0){
                        this.model.cboNT_DV_BAN.value=this.thongso_Ban.kieu_nghiemthu
                    }else{
                        this.model.cboNT_DV_BAN.value=[]
                    }

                    if(this.thongso_TC.kieu_nghiemthu&&this.thongso_TC.kieu_nghiemthu.length>0){
                        this.model.cboNT_DV_TC.value=this.thongso_TC.kieu_nghiemthu
                    }else{
                        this.model.cboNT_DV_TC.value=[]
                    }

                    let giaoDien=data.CBOGIAODIENKH.map((x) => ({ id: x.GDKN_ID, text: x.GIAODIEN_KN }))
                    this.model.cboGiaoDienKH_BAN.list=giaoDien
                    this.model.cboGiaoDienKH_TC.list=giaoDien
                    if(this.thongso_Ban.gdkn_id!=null){
                        this.model.cboGiaoDienKH_BAN.value=this.thongso_Ban.gdkn_id
                    }else{
                        this.model.cboGiaoDienKH_BAN.value=giaoDien[0].id
                    }
                    if(this.thongso_TC.gdkn_id!=null){
                        this.model.cboGiaoDienKH_TC.value=this.thongso_TC.gdkn_id
                    }else{
                        this.model.cboGiaoDienKH_TC.value=giaoDien[0].id
                    }
                    
                          
                }


                this.model.txtAuto_Nego_BAN = this.thongso_Ban.auto_nego;
                this.model.txtAuto_Nego_TC = this.thongso_TC.auto_nego;
                this.model.txtSNCP_BAN = this.thongso_Ban.sncp;
                this.model.txtSNCP_TC = this.thongso_TC.sncp;
                this.model.txtCapChinh_BAN = this.thongso_Ban.cap_chinh;
                this.model.txtCapChinh_TC = this.thongso_TC.cap_chinh;
                this.model.txtCapDP_BAN = this.thongso_Ban.cap_duphong;
                this.model.txtCapDP_TC = this.thongso_TC.cap_duphong;

                let kq = await this.BANCHEO_KIEMTRA_DICHVU_VNPTI(this.pLoaihinhTB, this.vloaihd_id, 9)
                if (kq == "OK"){
                    this.ht_tskt_vnpti = true;
                }else{
                    this.ht_tskt_vnpti = false;
                }
                    

                if (!this.ht_tskt_vnpti) {
                    this.visible.panelControl1 = false;
                    this.visible.panelControl2 = false;
                }else{
                    this.visible.panelControl1 = true;
                    this.visible.panelControl2 = true;
                }


                this.kt_load = false;
                if ((vloaihd == LoaiHopDong.CHUYEN_QUYEN || vloaihd == LoaiHopDong.DI_CHUYEN) && !this.kenhtrang) {
                    this.enable.cboDoituongBan = false;
                    this.enable.cboLoaikenhBan = false;
                    this.enable.cboMuccuoctbBan = false;
                    this.enable.cboThietbiCuoiBan = false;
                    this.enable.cboThietbiDauBan = false;
                    this.enable.cboTocdoBan = false;

                    this.enable.cboDoituongTC = false;
                    this.enable.cboLoaikenhTC = false;
                    this.enable.cboMuccuoctbTC = false;
                    this.enable.cboThietbiDauTC = false;
                    this.enable.cboThietbiCuoiTC = false;
                    this.enable.cboTocdoTC = false;
                }
            } catch(e) {
                console.log(e)
            } finally {
                this.loading(false)
            }
        },
        async BANCHEO_KIEMTRA_DICHVU_VNPTI(loaitb_id, loaihd_id, dichvuvt_id) {
            let result = ""
            try {
                let params = {
                    "loaitb_id": loaitb_id,
                    "loaihd_id": loaihd_id,
                    "dichvuvt_id": dichvuvt_id
                }
                let response = await api.bancheo_kiemtra_dichvu_vnpti(this.axios, params);
                if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
                    result = response.data.data
                }            
            } catch (e) {
                console.log(e)
            }
            return result
        },
        GetData1: function(response) {
            if (response.data.error === 200 || response.data.error === '200') {
                return response.data.data
            } else {
                return null
            }
            return null
        },
        async HT_DoiTuong_Combobox() {
            let result = []
            try {
                let response = await api.ks03_sp_lay_ds_doituong_theo_dieukien(this.axios);
                if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
                    result = response.data.data
                }            
            } catch (e) {
                console.log(e)
            }
            return result
        },
        async load_Dm_Doi_tuong(tinhthicong_id) {
            let result = []
            try {
                let params = {
                    "tinhthicong_id": tinhthicong_id
                }
                let response = await api.ks04_load_dm_doi_tuong(this.axios, params);
                if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
                    result = response.data.data
                }            
            } catch (e) {
                console.log(e)
            }
            return result
        },
        async HT_KIEULD_LOAITB(tinhthicong_id, vloaihd, ploaitb_ld) {
            let result = []
            try {
                let params = {
                    "tinhthicong_id": tinhthicong_id,
                    "vloaihd": vloaihd,
                    "ploaitb_ld": ploaitb_ld,
                }
                let response = await api.sp_ht_kieuld_loaitb(this.axios, params);
                if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
                    result = response.data.data
                }            
            } catch (e) {
                console.log(e)
            }
            return result
        },
        async HT_Loai_Kenh_Combobox(tinhthicong_id) {
            let result = []
            try {
                let params = {
                    "tinhthicong_id": tinhthicong_id
                }
                let response = await api.sp_ht_loai_kenh_combobox(this.axios, params);
                if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
                    result = response.data.data
                }            
            } catch (e) {
                console.log(e)
            }
            return result
        },
        async HT_Tocdo_Kenh_Combobox(tinhthicong_id) {
            let result = []
            try {
                let params = {
                    "tinhthicong_id": tinhthicong_id
                }
                let response = await api.sp_ht_tocdo_kenh_combobox(this.axios, params);
                if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
                    result = response.data.data
                }            
            } catch (e) {
                console.log(e)
            }
            return result
        },
        async HT_TrangBi_Combobox(tinhthicong_id) {
            let result = []
            try {
                let params = {
                    "tinhthicong_id": tinhthicong_id
                }
                let response = await api.sp_ht_trangbi_combobox(this.axios, params);
                if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
                    result = response.data.data
                }            
            } catch (e) {
                console.log(e)
            }
            return result
        },
        async HT_LOAI_TB_LHTB_Combobox(tinhthicong_id, vloaitb_id) {
            let result = []
            try {
                let params = {
                    "tinhthicong_id": tinhthicong_id,
                    "vloaitb_id": vloaitb_id
                }
                let response = await api.sp_ht_loai_tb_lhtb_combobox(this.axios, params);
                if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
                    result = response.data.data
                }            
            } catch (e) {
                console.log(e)
            }
            return result
        },
        async HT_MoHinh_KN_Combobox(tinhthicong_id) {
            let result = []
            try {
                let params = {
                    "tinhthicong_id": tinhthicong_id
                }
                let response = await api.sp_ht_mohinh_kn_combobox(this.axios, params);
                if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
                    result = response.data.data
                }            
            } catch (e) {
                console.log(e)
            }
            return result
        },
        async HT_GiaoDien_KN_Combobox(tinhthicong_id) {
            let result = []
            try {
                let params = {
                    "tinhthicong_id": tinhthicong_id
                }
                let response = await api.sp_ht_giaodien_kn_combobox(this.axios, params);
                if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
                    result = response.data.data
                }            
            } catch (e) {
                console.log(e)
            }
            return result
        },
        async LAY_CHUQUAN_MACDINH_THEO_DONVI_BANCHEO() {
            let result = ""
            try {
                let response = await api.lay_chuquan_macdinh_theo_donvi_bancheo(this.axios);
                if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
                    result = response.data.data
                }            
            } catch (e) {
                console.log(e)
            }
            return result
        },
        async LAY_DS_CHUQUAN() {
            let result = []
            try {
                let params = {
                    "cq_tc": 0
                }
                let response = await api.ks10_lay_ds_chuquan(this.axios, params);
                if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
                    result = response.data.data
                }            
            } catch (e) {
                console.log(e)
            }
            return result
        },
        async LOAD_DM_CHU_QUAN(tinhthicong_id) {
            let result = []
            try {
                let params = {
                    "tinhthicong_id": tinhthicong_id  
                }
                let response = await api.ks11_load_dm_chu_quan(this.axios, params);
                if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
                    result = response.data.data
                }            
            } catch (e) {
                console.log(e)
            }
            return result
        },
        async LAY_DS_MUCUOC_TB_V2(khuld_id, apld_id, phold_id, phuongld_id, loaitb_id, doituong_id, tocdo_id, muccuoc_id, ngay, trangbi_id) {
            let result = []
            try {
                let params = {
                    "khuld_id": khuld_id,
                    "apld_id": apld_id,
                    "phold_id": phold_id,
                    "phuongld_id": phuongld_id,
                    "loaitb_id": loaitb_id,
                    "doituong_id": doituong_id,
                    "tocdo_id": tocdo_id,
                    "muccuoc_id": muccuoc_id,
                    "ngay": ngay,
                    "trangbi_id": trangbi_id
                }
                let response = await api.ks08_lay_ds_mucuoc_tb_v2(this.axios, params);
                if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
                    result = response.data.data
                }            
            } catch (e) {
                console.log(e)
            }
            return result
        },
        async Lay_DS_MucCuoc_TB(tinhthicong_id, khuld_id, apld_id, phold_id, phuongld_id, loaitb_id, doituong_id, ngay, trangbi_id,
                        tocdo_id, muccuoc_id) {
            let vtocdo_id = 0;
            let vmuccuoc_id = 0; //2 Lưu lượng

            if (loaitb_id == LoaiHinhTB.INTERNET_FTTH ||
                loaitb_id == LoaiHinhTB.WIFI_FIBER ||
                loaitb_id == LoaiHinhTB.INTERNET_ADSL ||
                loaitb_id == LoaiHinhTB.INTERNET_ADSL_TINH_KHAC ||
                loaitb_id == LoaiHinhTB.INTERNET_FIBER_TINH_KHAC) {
                vtocdo_id = tocdo_id;
                vmuccuoc_id = muccuoc_id;
            } else if (loaitb_id == LoaiHinhTB.INTERNET_MYTV) {
                vtocdo_id = tocdo_id;
                vmuccuoc_id = 0;
            } else if (loaitb_id == LoaiHinhTB.FSECURE) { //Lamnt: bổ sung hiển thị mức cước theo tốc độ thuê bao FSecure (09/03/2017)
                vtocdo_id = tocdo_id;
                vmuccuoc_id = 0;
            }

            let result = []
            try {
                let params = {
                    "tinhthicong_id": tinhthicong_id,
                    "khuld_id": khuld_id,
                    "apld_id": apld_id,
                    "phold_id": phold_id,
                    "phuongld_id": phuongld_id,
                    "loaitb_id": loaitb_id,
                    "doituong_id": doituong_id,
                    "tocdo_id": vtocdo_id,
                    "muccuoc_id": vmuccuoc_id,
                    "trangbi_id": trangbi_id,
                    "ngay": ngay
                }
                let response = await api.ks09_lay_ds_mucuoc_tb_v2(this.axios, params);
                if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
                    result = response.data.data
                }            
            } catch (e) {
                console.log(e)
            }
            return result
        },
        async cboDoituongBan_SelectedIndexChanged() {
            if (!this.kt_load) {
                let temps = await this.LAY_DS_MUCUOC_TB_V2(0, 0, 0, 0, this.pLoaihinhTB, Number(this.model.cboDoituongBan.value), 0, 0, this.ngay, 
                    Number(this.model.cboTrangbiBan.value));
                if (temps && temps.length > 0) {
                    this.model.cboMuccuoctbBan.list = temps.map((x)=>({id:x.muccuoctb_id, text:x.tenmuccuoc}))
                } else {
                    this.model.cboMuccuoctbBan.list = []
                }
            }
        },
        async cboTocdoBan_SelectedIndexChanged() {
            if (!this.kt_load) {
                let kt_tocdo_tc = false;
                for (let i = 0; i < this.model.cboTocdoTC.list.length; i++)
                    if (this.model.cboTocdoTC.list[i].id == this.model.cboTocdoBan.value) {
                        this.model.cboTocdoTC.value = this.model.cboTocdoTC.list[i].id
                        kt_tocdo_tc = true;
                        break;
                    }
                if (!kt_tocdo_tc)
                    this.$toast.error("Tốc độ không có bên tỉnh thi công !");

                let temps = await this.LAY_DS_MUCUOC_TB_V2(0, 0, 0, 0, this.pLoaihinhTB, Number(this.model.cboDoituongBan.value), 0, 0, this.ngay, 
                    Number(this.model.cboTrangbiBan.value));
                if (temps && temps.length > 0) {
                    this.model.cboMuccuoctbBan.list = temps.map((x)=>({id:x.muccuoctb_id, text:x.tenmuccuoc}))
                } else {
                    this.model.cboMuccuoctbBan.list = []
                }
            }
        },
        async cboTrangbiBan_SelectedIndexChanged() {
            if (!this.kt_load) {
                let temps = await this.LAY_DS_MUCUOC_TB_V2(0, 0, 0, 0, this.pLoaihinhTB, Number(this.model.cboDoituongBan.value), 0, 0, this.ngay, 
                    Number(this.model.cboTrangbiBan.value));
                if (temps && temps.length > 0) {
                    this.model.cboMuccuoctbBan.list = temps.map((x)=>({id:x.muccuoctb_id, text:x.tenmuccuoc}))
                } else {
                    this.model.cboMuccuoctbBan.list = []
                }
            }
        },
        async cboDoituongTC_SelectedIndexChanged() {
            if (!this.kt_load) {
                let temps = await this.Lay_DS_MucCuoc_TB(this.tinhtc.tinhthicong_id, 0, 0, 0, 0, this.pLoaihinhTB, Number(this.model.cboDoituongTC.value), 
                    this.ngay, Number(this.model.cboTrangbiTC.value), Number(this.model.cboTocdoTC.value), 0);
                if (temps && temps.length > 0) {
                    this.model.cboMuccuoctbTC.list = temps.map((x)=>({id:x.muccuoctb_id, text:x.tenmuccuoc}))
                }
            }
        },
        async cboTocdoTC_SelectedIndexChanged() {
            // if (!this.kt_load) {
            //     let temps = await this.LAY_DS_MUCUOC_TB_V2(0, 0, 0, 0, this.pLoaihinhTB, Number(this.model.cboDoituongBan.value), 0, 0, this.ngay, 
            //         Number(this.model.cboTrangbiBan.value));
            //     if (temps && temps.length > 0) {
            //         this.model.cboMuccuoctbBan.list = temps.map((x)=>({id:x.muccuoctb_id, text:x.tenmuccuoc}))
            //     } else {
            //         this.model.cboMuccuoctbBan.list = []
            //     }
            // }
            if (!this.kt_load) {
                let temps = await this.Lay_DS_MucCuoc_TB(this.tinhtc.tinhthicong_id, 0, 0, 0, 0, this.pLoaihinhTB, Number(this.model.cboDoituongTC.value), 
                    this.ngay, Number(this.model.cboTrangbiTC.value), Number(this.model.cboTocdoTC.value), 0);
                if (temps && temps.length > 0) {
                    this.model.cboMuccuoctbTC.list = temps.map((x)=>({id:x.muccuoctb_id, text:x.tenmuccuoc}))
                }else{
                    this.model.cboMuccuoctbBan.list = []
                }
            }
        },
        async cboTrangbiTC_SelectedIndexChanged() {
            if (!this.kt_load) {
                let temps = await this.Lay_DS_MucCuoc_TB(this.tinhtc.tinhthicong_id, 0, 0, 0, 0, this.pLoaihinhTB, Number(this.model.cboDoituongTC.value), 
                    this.ngay, Number(this.model.cboTrangbiTC.value), Number(this.model.cboTocdoTC.value), 0);
                if (temps && temps.length > 0) {
                    this.model.cboMuccuoctbTC.list = temps.map((x)=>({id:x.muccuoctb_id, text:x.tenmuccuoc}))
                }
            }
        },
        async cboKieuLD_SelectedIndexChanged() {
            if (!this.kt_load) {
                let temps = await this.Lay_DS_MucCuoc_TB(this.tinhtc.tinhthicong_id, 0, 0, 0, 0, this.pLoaihinhTB, Number(this.model.cboDoituongTC.value), 
                    this.ngay, Number(this.model.cboTrangbiTC.value), Number(this.model.cboTocdoTC.value), 0);
                if (temps && temps.length > 0) {
                    this.model.cboMuccuoctbTC.list = temps.map((x)=>({id:x.muccuoctb_id, text:x.tenmuccuoc}))
                }
            }
        },
        async cboNhomLoaiKenh_Data(){
            let result = []
            try {
                
                let response = await api.lay_thongtin_ds_nhomloaikenh(this.axios);
                if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
                    result = response.data.data
                }
                this.model.cboNhomLoaiKenh_TC.list = result.map((e) => ({
                    id: e.nhomloaikenh_id,
                    text: e.nhom_loaikenh
                }))
                this.model.cboNhomLoaiKenh_Ban.list = this.model.cboNhomLoaiKenh_TC.list  
                
                
            } catch (e) {
                console.log(e)
            }
        },
        async BANCHEO_KIEMTRA_THONGSOKT(kt, vloaihd_id, vdichvuvt_id, vloaitb_id) {
            let result = ""
            try {
                let params = {
                    "vjson_kt": JSON.stringify(kt),
                    "vloaihd_id": vloaihd_id,
                    "vdichvuvt_id": vdichvuvt_id,
                    "vloaitb_id": vloaitb_id
                }
                let response = await api.bancheo_kiemtra_thongsokt(this.axios, params);
                if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
                    result = response.data.data
                }            
            } catch (e) {
                console.log(e)
            }
            return result
        },


        async tsbtnCapNhat_Click() {
            try {
                this.loading(true)
                var dt_kieu_nt_ban = this.model.cboNT_DV_BAN.value
                var dt_kieu_nt_tc = this.model.cboNT_DV_TC.value

                console.log('dt_kieu_nt_ban', dt_kieu_nt_ban)
                console.log('dt_kieu_nt_tc', dt_kieu_nt_tc)

                if (this.model.cboMuccuoctbBan.value == null || (this.model.cboMuccuoctbTC.value == null && this.model.chkQuaVNPT.value == true)) {
                    this.$toast.error("Bạn phải chọn mức cước  thuê bao cho 2 đơn vị");
                    return;
                }

                if (Number(this.model.cboThietbiCuoiBan.value) == 0 || Number(this.model.cboThietbiDauBan.value) == 0
                    || (Number(this.model.cboThietbiDauTC.value) == 0 && this.model.chkQuaVNPT.value == true ) || Number(this.model.cboThietbiCuoiTC.value) == 0) {
                    this.$toast.error("Bạn phải chọn thiết bị đầu cuối cho 2 đơn vị");
                    return;
                }

                if(this.model.cboTocdoBan.list.length==0||this.model.cboTocdoTC.list.length==0||!this.model.cboTocdoBan.value||!this.model.cboTocdoTC.value){
                    this.$toast.error('Bạn phải chọn tốc độ cho 2 đơn vị')
                    return
                }
                if(this.model.cboTocdoBan.value!=this.model.cboTocdoTC.value){
                    this.$toast.error('Tốc độ 2 đơn vị không bằng nhau')
                    return
                }

                if (Number(this.model.cboLoaikenhBan.value) == 9) { // kênh quốc tế
                    try {
                        var obj = {
                            tocdo_id: 0,
                            tocdothuc_id: 0,
                            tocdo_pir_id: 0,
                            tocdo_nix: 0,
                            tocdo_isp: 0,
                            chuquan_id: Number(this.model.cboChuquanBan.value),
                            chuquanld_id: Number(this.model.cboChuQuanLD_Ban.value),
                            congnghe_id: 0,
                            muccuoc_id: 0,
                            kieutra_id: 0,
                            doituong_id: 0,
                            muccuoctb_id: 0,
                            trangbi_id: 0,
                            kieuld_id: Number(this.model.cboKieuLD.value),
                            thietbidc_id: 0,
                            loaimd_id: 0,
                            loaikenh_id: Number(this.model.cboLoaikenhBan.value),
                            thietbidc_id_cuoi: 0,
                            thoihan_id: 0,
                            ma_tn: "",
                            ma_doicap: "",
                            chuquanld_id_cuoi: 0,
                            phanloai_id: 0,
                            phantach_id: 0,
                            mhkn_id: Number(this.model.cboMHKN_BAN.value),
                            gdkn_id: 0,
                            yc_baove: 0,
                            tocdo_pop: Number(this.model.cboTocDoPOP_BAN.value),
                            gdkn_pop_id: 0,
                            gdkn_kh_id: Number(this.model.cboGiaoDienKH_BAN.value),
                            cauhinh_dt_id: 0,
                            kieu_duplex_id: 0,
                            kieukn_id: 0,
                            mtu_size: this.model.txtMTU_Size_BAN,
                            auto_nego: this.model.txtAuto_Nego_BAN,
                            sncp: this.model.txtSNCP_BAN,
                            cap_chinh: this.model.txtCapChinh_BAN,
                            cap_duphong: this.model.txtCapDP_BAN,
                            diem_pop: "",
                            wan_ip: "",
                            lan_ip: "",
                            ce_ip: "",
                            tt_ip: "",
                            sl_ip: 0,
                            ten_trunk_nni: "",
                            so_as: "",
                            hqos: "",
                            cos: "",
                            svlan: "",
                            cvlan: "",
                            vlan_transparent: "",
                            tt_port: ""
                        };

                        let kq_kt = await this.BANCHEO_KIEMTRA_THONGSOKT(obj, this.vloaihd_id, 9, this.pLoaihinhTB)
                        if (kq_kt != "OK") {
                            this.$toast.error("Có lỗi: " + kq_kt);
                            return;
                        }

                        if ((!dt_kieu_nt_tc || dt_kieu_nt_tc.length == 0 || !dt_kieu_nt_ban || dt_kieu_nt_ban.length == 0) && this.vloaihd_id == LoaiHopDong.DAT_MOI 
                            && (this.pLoaihinhTB == 257  || this.pLoaihinhTB == 258 || this.pLoaihinhTB == 273)) {
                            this.$toast.error("Chưa chọn kiểu nghiệm thu cho loại kênh quốc tế");
                            return;
                        }
                    } catch(exp) {
                        this.$toast.error("Có lỗi khi chọn thông số kênh quốc tế: " + exp);
                    }
                }

                if (this.model.cboLoaikenhBan.list.length > 0)
                    this.thongso_Ban.loaikenh_id = Number(this.model.cboLoaikenhBan.value);
                if (this.model.cboMuccuoctbBan.list.length > 0)
                    this.thongso_Ban.muccuoctb_id = Number(this.model.cboMuccuoctbBan.value);
                this.thongso_Ban.trangbi_id = Number(this.model.cboTrangbiBan.value);
                this.thongso_Ban.tocdo_id = Number(this.model.cboTocdoBan.value);
                this.thongso_Ban.chuquan_id = Number(this.model.cboChuquanBan.value);
                this.thongso_Ban.chuquanld_id = Number(this.model.cboChuQuanLD_Ban.value);
                this.thongso_Ban.doituong_id = Number(this.model.cboDoituongBan.value);
                this.thongso_Ban.thietbidc_id = Number(this.model.cboThietbiDauBan.value);
                this.thongso_Ban.thietbidc_id_cuoi = Number(this.model.cboThietbiCuoiBan.value);

                this.thongso_TC.kieuld_id = Number(this.model.cboKieuLD.value);
                if (this.model.cboLoaikenhTC.list.length > 0)
                    this.thongso_TC.loaikenh_id = Number(this.model.cboLoaikenhTC.value);
                if (this.model.cboMuccuoctbTC.list.length > 0)
                    this.thongso_TC.muccuoctb_id = Number(this.model.cboMuccuoctbTC.value);
                this.thongso_TC.trangbi_id = Number(this.model.cboTrangbiTC.value);
                this.thongso_TC.tocdo_id = Number(this.model.cboTocdoTC.value);
                this.thongso_TC.chuquan_id = Number(this.model.cboChuquanTC.value);
                this.thongso_TC.chuquanld_id = Number(this.model.cboChuQuanLD_TC.value);
                this.thongso_TC.doituong_id = Number(this.model.cboDoituongTC.value);
                if (this.model.cboThietbiDauTC.value != null)
                    this.thongso_TC.thietbidc_id = Number(this.model.cboThietbiDauTC.value);
                if (this.model.cboThietbiCuoiTC.value != null)
                    this.thongso_TC.thietbidc_id_cuoi = Number(this.model.cboThietbiCuoiTC.value);

                if (this.model.cboMHKN_TC.value != "" && this.model.cboMHKN_TC.value != "0")
                    this.thongso_Ban.mhkn_id = Number(this.model.cboMHKN_TC.value);
                else
                    this.thongso_Ban.mhkn_id = 0;
                if (this.model.cboMHKN_TC.value != "" && this.model.cboMHKN_TC.value != "0")
                    this.thongso_TC.mhkn_id = Number(this.model.cboMHKN_BAN.value);
                else
                    this.thongso_TC.mhkn_id = 0;

                if (this.model.cboTocDoPOP_BAN.value != "" && this.model.cboTocDoPOP_BAN.value != "0")
                    this.thongso_Ban.tocdo_pop = Number(this.model.cboTocDoPOP_BAN.value);
                else
                    this.thongso_Ban.tocdo_pop = 0;

                if (this.model.cboTocDoPOP_TC.value != "" && this.model.cboTocDoPOP_TC.value != "0")
                    this.thongso_TC.tocdo_pop = Number(this.model.cboTocDoPOP_TC.value);
                else
                    this.thongso_TC.tocdo_pop = 0;

                if (this.model.cboGiaoDienKH_BAN.value != "" && this.model.cboGiaoDienKH_BAN.value != "0")
                    this.thongso_Ban.gdkn_id = Number(this.model.cboGiaoDienKH_BAN.value);
                else
                    this.thongso_Ban.gdkn_id = 0;
                if (this.model.cboGiaoDienKH_TC.value != "" && this.model.cboGiaoDienKH_TC.value != "0")
                    this.thongso_TC.gdkn_id = Number(this.model.cboGiaoDienKH_TC.value);
                else
                    this.thongso_TC.gdkn_id = 0;

                if (this.model.cboYCBV_Ban.value != "" && this.model.cboYCBV_Ban.value != 0)
                    this.thongso_Ban.yc_baove = this.model.cboYCBV_Ban.value
                else
                    this.thongso_Ban.yc_baove = 0

                if (this.model.cboYCBV_TC.value != "" && this.model.cboYCBV_TC.value != 0)
                    this.thongso_TC.yc_baove = this.model.cboYCBV_TC.value
                else
                    this.thongso_TC.yc_baove = 0
                
                if (this.model.cboNhomLoaiKenh_Ban.value != "" && this.model.cboNhomLoaiKenh_Ban.value != "0" && this.model.cboNhomLoaiKenh_Ban.disabled == false)
                    this.thongso_Ban.nhomloaikenh_id = Number(this.model.cboNhomLoaiKenh_Ban.value)
                else
                    this.thongso_Ban.nhomloaikenh_id = 0

                if (this.model.cboNhomLoaiKenh_TC.value != "" && this.model.cboNhomLoaiKenh_TC.value != "0" && this.model.cboNhomLoaiKenh_TC.disabled == false)
                    this.thongso_TC.nhomloaikenh_id = Number(this.model.cboNhomLoaiKenh_TC.value)
                else
                    this.thongso_TC.nhomloaikenh_id = 0

                if (this.model.chkQuaVNPT.value == true && this.model.chkQuaVNPT.visible == true){
                    this.thongso_Ban.hatang_vnpt  = 1
                    this.thongso_TC.hatang_vnpt = 1
                }
                else if (this.model.chkQuaVNPT.visible == false){
                    this.thongso_Ban.hatang_vnpt = 0
                    this.thongso_TC.hatang_vnpt = 0;
                }
                else if (this.model.chkQuaVNPT.visible == true && this.model.chkQuaVNPT.value == false){
                    this.thongso_Ban.hatang_vnpt = 2
                    this.thongso_TC.hatang_vnpt = 2
                }
                
                this.thongso_Ban.kieu_nghiemthu = this.model.cboNT_DV_BAN.value
                this.thongso_TC.kieu_nghiemthu = this.model.cboNT_DV_TC.value

                console.log('test fdsf', this.thongso_Ban.kieu_nghiemthu)

                this.thongso_Ban.cap_chinh = this.model.txtCapChinh_BAN?this.model.txtCapChinh_BAN:null
                this.thongso_TC.cap_chinh = this.model.txtCapChinh_TC?this.model.txtCapChinh_TC:null

                this.thongso_Ban.cap_duphong = this.model.txtCapDP_BAN?this.model.txtCapDP_BAN:null
                this.thongso_TC.cap_duphong = this.model.txtCapDP_TC?this.model.txtCapDP_TC:null

                this.thongso_Ban.auto_nego = this.model.txtAuto_Nego_BAN?this.model.txtAuto_Nego_BAN:null
                this.thongso_TC.auto_nego = this.model.txtAuto_Nego_TC?this.model.txtAuto_Nego_TC:null

                this.thongso_Ban.mtu_size = this.model.txtMTU_Size_BAN?this.model.txtMTU_Size_BAN:null
                this.thongso_TC.mtu_size = this.model.txtMTU_Size_TC?this.model.txtMTU_Size_TC:null

                this.thongso_Ban.sncp = this.model.txtSNCP_BAN?this.model.txtSNCP_BAN:null
                this.thongso_TC.sncp = this.model.txtSNCP_TC?this.model.txtSNCP_TC:null

                this.$emit("chapnhan", {thongso_Ban: this.thongso_Ban, thongso_TC: this.thongso_TC})
                this.$refs.dlg_thongsotsl.hide()
            } catch (e) {
                console.log(e)
                this.$toast.error(e)
            } finally {
                this.loading(false)
            }
        },
        list2treeNew(rootList) {
            rootList.forEach((item) => {
                item.id = item.kieunt_id;
                item.label = item.kieu_nghiemthu ? item.kieu_nghiemthu : null;
                item.level = 0;
            });
            return rootList;
        },
        async lay_thongtin_ds_loaikenh() {
            let res = []
            try {
                let response = await api.lay_thongtin_ds_loaikenh(this.axios);
                if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
                    res = response.data.data
                }            
            } catch (e) {
                console.log(e)
            }
            return res
        },
        async lay_thongtin_ds_tocdo_kenh() {
            let res = []
            try {
                let response = await api.lay_thongtin_ds_tocdo_kenh(this.axios);
                if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
                    res = response.data.data
                }            
            } catch (e) {
                console.log(e)
            }
            return res
        },
        async lay_thongtin_ds_trangbi() {
            let res = []
            try {
                let response = await api.lay_thongtin_ds_trangbi(this.axios);
                if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
                    res = response.data.data
                }            
            } catch (e) {
                console.log(e)
            }
            return res
        },
        async lay_thongtin_ds_mohinh_kn() {
            let res = []
            try {
                let response = await api.lay_thongtin_ds_mohinh_kn(this.axios);
                if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
                    res = response.data.data
                }            
            } catch (e) {
                console.log(e)
            }
            return res
        },
        async lay_thongtin_ds_giaodien_kn() {
            let res = []
            try {
                let response = await api.lay_thongtin_ds_giaodien_kn(this.axios);
                if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
                    res = response.data.data
                }            
            } catch (e) {
                console.log(e)
            }
            return res
        },
        async lay_thongtin_ds_kieu_nghiemthu() {
            let res = []
            try {
                let response = await api.lay_thongtin_ds_kieu_nghiemthu(this.axios);
                if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
                    res = response.data.data
                }            
            } catch (e) {
                console.log(e)
            }
            return res
        },
        changeLoaiKenhBan(){
            this.model.cboLoaikenhTC.value=this.model.cboLoaikenhBan.value
        },
        changeTocDoBan(){
            this.model.cboTocdoTC.value=this.model.cboTocdoBan.value
        },

    },
    watch: {      
        'model.cboDoituongBan.value': async function(val){      
            this.cboDoituongBan_SelectedIndexChanged()
        },
        'model.cboTocdoBan.value': async function(val){      
            this.cboTocdoBan_SelectedIndexChanged()
        },
        'model.cboTrangbiBan.value': async function(val){      
            this.cboTrangbiBan_SelectedIndexChanged()
        },
        'model.cboDoituongTC.value': async function(val){      
            this.cboDoituongTC_SelectedIndexChanged()
        },
        'model.cboTocdoTC.value': async function(val){      
            this.cboTocdoTC_SelectedIndexChanged()
        },
        'model.cboTrangbiTC.value': async function(val){      
            this.cboTrangbiTC_SelectedIndexChanged()
        },      
        'model.cboKieuLD.value': async function(val){      
            this.cboKieuLD_SelectedIndexChanged()
        },
        'model.chkQuaVNPT.value': {
            handler(value){
                if(value != true){
                    this.model.cboNhomLoaiKenh_TC.disabled = false
                    this.model.cboNhomLoaiKenh_Ban.disabled = false 
                }
                else{
                    this.model.cboNhomLoaiKenh_TC.disabled = true
                    this.model.cboNhomLoaiKenh_Ban.disabled = true 
                }
            }
        }
    }
}
</script>
<style>
    .vue-treeselect--has-value .vue-treeselect__multi-value {
        margin-bottom: 5px;
        height: 64px !important;
        overflow: scroll;
    }
</style>