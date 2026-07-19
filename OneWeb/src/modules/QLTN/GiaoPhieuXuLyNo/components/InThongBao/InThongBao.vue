<template src="./InThongBao.html"></template>

<script>
import { GridPhieuChuaGiaoCollumns, GridFileXLNCollumns } from "./common";
import API from "../../../api/GiaoPhieuXuLyNo";
import { NHOM_BC, MA_BC } from "../../../../../utils/Enum";
import exportExcelCustom from "../../utils/ExportExcel";

import xacMinhCA from "./InXacMinhCA.vue";
import frmDocFile from "./popup-docfile";
import {previewPrint} from "@/utils/util";
import moment from 'moment';

export default {
    props: ['kyCuoc'],
     components: {
        frmDocFile,
        xacMinhCA
        },
    data() {
        return {
            target: ".main-wrapper",
            animationSettings: { effect: "Zoom" },
            dtPhieuFile: [],
            dtPhieuChuaGiao: [],
            dsHoaDon: [],
            ds_xulyno_id: null,
            dsbaocao_id: null,
            vma_tt: null,
            vdsma_tt: null,
            load: false,
            loaihd_id: null,
            vkyhoadon: null,
            vkyhoadon_tc: null,
            vkieutao_dl: null,
            vbuocxln_id: null,
            vtrangthai_xl: null,
            vnhanvienxl_id: null,
            vlantao_id: null,
            vma_bc: null,

            cboMauIn: [],
            cboMauInValue: "",
            gridPhieuChuaGiao: [],
            gridPhieuChuaGiaoCollumn: GridPhieuChuaGiaoCollumns,
            gridFileXLN: [],
            gridFileXLNCollumn: GridFileXLNCollumns,
            tsbtnNhapFile: true,
            dsBienBan:[],
            dsMaXLN:[],
            checkInKim:false,            
        };
    },
    watch: {
        async cboMauInValue() {
            console.log(this.cboMauInValue);
            this.cboMauIn.forEach((item)=>{
                if (item.BAOCAO_ID==this.cboMauInValue)
                    this.vma_bc=item.MA_BC;
            })
            console.log(this.vma_bc);
            // if (!this.isNullEmpty(this.cboMauInValue)) {
            //     let response = await API.getMaBC(this.axios, {
            //         pBoaCaoId: this.cboMauInValue
            //     });
            //     // if (
            //     //     response.data.error_code === "BSS-00000000" &&
            //     //     response.data.data !== undefined
            //     // ) {
            //     //     if (!this.isNullEmpty(response.data.data.MA_BC)) {
            //     //         this.vma_bc = response.data.data.MA_BC;
            //     //         if (this.vma_bc == MA_BC.IN_DS_GIAONHAN_TBN) {
            //     //             this.dtPhieuChuaGiao.forEach(item => {
            //     //                 this.$refs.refGridPhieuChuaGiao.selectedValues.push(
            //     //                     item.MA_XLN
            //     //                 );
            //     //             });
            //     //             this.gridPhieuChuaGiao = this.dtPhieuChuaGiao;
            //     //         } else {
            //     //             this.gridPhieuChuaGiao = this.dtPhieuChuaGiao;
            //     //         }
            //     //     }
            //     // }
            // }
        }
    },
    methods: {
        async dialogOpen() {
            //console.log(this.dtPhieuChuaGiao);
            if (!this.isNullEmpty(this.tag)) {
                this.dsbaocao_id = this.tag;
            } else {
                this.dsbaocao_id = "";
            }
            try {
                this.loading(true);
                let data = {
                    nhomBCId: NHOM_BC.XULYNO,
                    loaiHD: 0,
                    dsBaoCao:
                        this.dsbaocao_id != ""
                            ? this.dsbaocao_id.replaceAll(";", ",")
                            : this.dsbaocao_id,
                    buocXLNId: this.vbuocxln_id,
                    trangThaiXL: this.vtrangthai_xl
                };
                let response = await API.getDSMauIn(this.axios, data);
                if (
                    response.data.error_code === "BSS-00000000" &&
                    response.data.data !== undefined
                ) {
                    if (response.data.data.length > 0) {
                        this.cboMauIn = response.data.data;
                        this.cboMauInValue = this.cboMauIn[0].BAOCAO_ID;
                    } else {
                        this.$toast.warning("Không có mẫu in!");
                    }
                }
            } catch (error) {
                
            } finally {
                this.loading(false);
            }

            this.tsbtnNhapFile = false;
            this.load = true;
            this.$refs.dlgInThongBao.show();
        },
        dialogClose() {
            this.$refs.dlgInThongBao.hide();
            this.reset();
        },
        reset() {
            this.dtPhieuFile = [];
            this.dtPhieuChuaGiao = [];
            this.dsHoaDon = [];
            this.ds_xulyno_id = "";
            this.dsbaocao_id = "";
            this.vma_tt = "";
            this.vdsma_tt = "";
            this.load = false;
            this.loaihd_id = null;
            this.vkyhoadon = "";
            this.vkyhoadon_tc = "";
            this.vkieutao_dl = null;
            this.vbuocxln_id = null;
            this.vtrangthai_xl = null;
            this.vnhanvienxl_id = null;
            this.vlantao_id = null;
            this.vma_bc = "";
        },
        isNullEmpty(value) {
            return value === null || value === "";
        },
        btnDocFile() {
            this.$bvModal.show("modal-docfile");
        },
        btnInDS() {
            if (this.vma_bc=="BB_XM_CA")
                this.$bvModal.show("refXacMinhCA");
            else               
            if (this.checkInKim)
                this.print_inkim();
            else
                this.print_ds();
            
        },
        formatJson(filterVal, jsonData) {
            return jsonData.map(v =>
                filterVal.map(j => {
                    return v[j];
                })
            );
        },

        btnXuatExcel() {
            if (this.gridPhieuChuaGiao.length <= 0)
                return this.$toast.error("Chưa có danh sách để xuất Excel!");
            exportExcelCustom(
                this.gridPhieuChuaGiaoCollumn,
                this.gridPhieuChuaGiao,
                "ThongBao",
                "export"
            );
        },
        selectedItemsChangedPhieuChuaGiao(value) {
            this.dsBienBan=[];
            this.dsMAXLN=[];
            value.forEach(element => {
                this.gridPhieuChuaGiao.forEach(item=>{
                    if (item.MA_XLN===element)
                    {
                    this.dsBienBan.push(item.BIENBAN_ID);
                    this.dsMaXLN.push(item.MA_XLN)
                    }
                })
            });

            //console.log(this.dsBienBan);
        },
        selectedItemsChangedFileXLN(value) {
             this.dsBienBan=[];
             this.dsMAXLN=[];
            value.forEach(element => {
                this.gridFileXLN.forEach(item=>{
                    if (item.MA_XLN===element)
                    {
                    this.dsBienBan.push(item.BIENBAN_ID);
                    this.dsMaXLN.push(item.MA_XLN)
                    }
                })
            });
        },
        print_ds: async function(){
            const baseUrl = process.env.API;
            let noidung_in=null;

            let param= {
                            kyCuoc: this.vkyhoadon,
                            ngayGiao: moment(new Date()).format("DD/MM/YYYY"),
                            nguoiNhan: "",
                            dsXuLyNoId: this.dsBienBan
                        };
             await this.axios.post(baseUrl + "/web-thuno/api/thu-no/in-hoa-don/in-bao-cao-884", param, {responseType: 'blob'})
                .then( (response) => {
                    noidung_in=response.data;
                })

           previewPrint(noidung_in);
        },
        print_inkim: async function(){


            const baseUrl = process.env.API;
            let noidung_in=null;

            let param= {
                        kyCuoc: this.vkyhoadon,
                        dsMaTT: this.dsMaXLN
                        };
             await this.axios.post(baseUrl + "/web-thuno/api/thu-no/in-hoa-don/in-no-thanh-toan-theo-ky-cuoc", param, {responseType: 'blob'})
                .then( (response) => {
                    noidung_in=response.data;
                })

           previewPrint(noidung_in);
        },

        dsTheoFile: async function(value){
            

            let dsPhieuFile='';
                value.forEach(item=>{
                    dsPhieuFile=dsPhieuFile+item.MA_XLN+",";
                });

            try {
                     this.loading(true);
                            let data = {
                                pKyCuoc: this.vkyhoadon,
                                pKieuXLId: this.vkieutao_dl,
                                pBuocXLNId: this.vbuocxln_id,
                                pMaXLN: dsPhieuFile
                            };
                            let response = await API.getDSPhieuDaGiaoTheoMaXLN(
                                this.axios,
                                data
                            );
                            console.log(response.data);
                            if (
                                response.data.error_code === "BSS-00000000" &&
                                response.data.data !== undefined
                            ) {
                                if (response.data.data.length > 0) {
                                    this.gridFileXLN = response.data.data;
                                }
                            }
                        } catch (error) {
                            console.log(error);
                            this.$toast.error(
                                error.response.data.message_detail
                            );
                        } finally {
                            this.loading(false);
                        }

        },
        selectedTab(value) {
            switch (value) {
                case "gridPhieuChuaGiao":
                    this.tsbtnNhapFile = false;
                    break;
                case "gridFileXLN":
                    this.tsbtnNhapFile = true;
                    break;
            }
        },
        
    }
};
</script>

<style src="./InThongBao.scss"></style>
