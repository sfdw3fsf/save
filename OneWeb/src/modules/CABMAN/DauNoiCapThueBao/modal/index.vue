<template src="./index.html">
</template>

<script>
export default {
    props: {
        modalId: String,
        mat: Number,
        thuebao: Object
    },
    data: function() {
        return {
            rd_daunoi: 0,
            txtKyHieuKC: null,
            txtCuLy: 0,
            cboPORT: null,
            cboTTVT: null,
            cboTOKT: null,
            cboTRAM: null,
            cboLOAI_TBI: null,
            cboTHIETBI: null,
            cboCARD: null,
            cboMODULE: null,
            txtGhiChu: null,

            dsTTVT: [],
            dsTOKT: [],
            dsTRAM: [],
            dsLOAI_TBI: [],
            dsTHIETBI: [],
            dsCARD: [],
            dsMODULE: [],
            dsPORT: [],
            dsDanDo: []
        }
    },
    methods: {
        NAP_DS_TTVT: async function() {
            try {
                let rs = await this.$root.context.post("/web-ecms/danhmuc/layDsTTVTTrongToken");
                this.dsTTVT = rs.data;
                if(this.dsTTVT.length > 0) {
                    this.cboTTVT = this.dsTTVT[0].DONVI_ID;
                }
                else {
                    this.cboTTVT = null;
                }
            } catch (error) {
                
            }
        },
        NAP_DS_TOVT: async function() {
            try {
                let rs = await this.$root.context.post("/web-ecms/danhmuc/layDsToKT", {
                    id: this.cboTTVT,
                });
                this.dsTOKT = rs.data;
                if(this.dsTOKT.length > 0) {
                    this.cboTOKT = this.dsTOKT[0].DONVI_ID;
                }
                else {
                    this.cboTOKT = null;
                }
            } catch (error) {
                
            }
        },
        NAP_DS_TRAM: async function() {
            try {
                let rs = await this.$root.context.post("/web-ecms/danhmuc/layDsVeTinhTheoToQl", {
                    id: this.cboTOKT,
                });
                this.dsTRAM = rs.data;
                if(this.dsTRAM.length > 0) {
                    this.cboTRAM = this.dsTRAM[0].DONVI_ID;
                }
                else {
                    this.cboTRAM = null;
                }
            } catch (error) {
                
            }
        },
        NAP_DS_LOAI_TBI: async function() {
            try {
                let rs = await this.$root.context.post(`/web-cabman/daunoi-cap-thuebao/lay-ds-loai-tb/${this.cboTRAM}`);
                this.dsLOAI_TBI = rs.data;
                if(this.dsLOAI_TBI.length > 0) {
                    this.cboLOAI_TBI = this.dsLOAI_TBI[0].LOAITBI_ID;
                }
                else {
                    this.cboLOAI_TBI = null;
                }
            } catch (error) {
                
            }
        },
        NAP_DS_THIETBI: async function() {
            try {
                let rs = await this.$root.context.post(`/web-cabman/daunoi-cap-thuebao/lay-ds-tb/${this.cboTRAM}/${this.cboLOAI_TBI}`);
                this.dsTHIETBI = rs.data;
                if(this.dsTHIETBI.length > 0) {
                    this.cboTHIETBI = this.dsTHIETBI[0].THIETBI_ID;
                }
                else {
                    this.cboTHIETBI = null;
                }
            } catch (error) {
                
            }
        },
        NAP_DS_CARD: async function() {
            try {
                let obj = this.dsTHIETBI.find(v => v.THIETBI_ID == this.cboTHIETBI);
                let rs = await this.$root.context.post(`/web-cabman/daunoi-cap-thuebao/lay-ds-card-or-module/${obj.LOAITB_ID}/${this.cboTHIETBI}`);
                this.dsCARD = rs.data;
                if(this.dsCARD.length > 0) {
                    this.cboCARD = this.dsCARD[0].THIETBI_ID;
                }
                else {
                    this.cboCARD = null;
                }
            } catch (error) {
                
            }
        },
        NAP_DS_MODULE: async function() {
            try {
                let obj = this.dsCARD.find(v => v.THIETBI_ID == this.cboCARD);
                let rs = await this.$root.context.post(`/web-cabman/daunoi-cap-thuebao/lay-ds-card-or-module/${obj.LOAITB_ID}/${this.cboCARD}`);
                this.dsMODULE = rs.data;
                if(this.dsMODULE.length > 0) {
                    this.cboMODULE = this.dsMODULE[0].THIETBI_ID;
                }
                else {
                    this.cboMODULE = null;
                }
            } catch (error) {
                
            }
        },
        NAP_DS_PORT: async function() {
            try {
                this.loading(true);
                let tbi;
                if(this.rd_daunoi == 1) {
                    if(this.cboMODULE) {
                        tbi = this.dsMODULE.find(v => v.THIETBI_ID == this.cboMODULE);
                    }
                    else if(this.cboCARD) {
                        tbi = this.dsCARD.find(v => v.THIETBI_ID == this.cboCARD);
                    }
                    else {
                        tbi = this.dsTHIETBI.find(v => v.THIETBI_ID == this.cboTHIETBI);
                    }
                }
                let rs = await this.$root.context.post('/web-cabman/daunoi-cap-thuebao/lay-ds-port', {
                    kieu: this.rd_daunoi,
                    kyhieu_kc: this.txtKyHieuKC,
                    loaitb_id: tbi ? tbi.LOAITB_ID : 0,
                    thietbi_id: tbi ? tbi.THIETBI_ID : 0
                });
                this.dsPORT = rs.data;
                if(this.dsPORT.length > 0) {
                    this.cboPORT = this.dsPORT[0].VITRI;
                }
            } catch (error) {
                console.log(error);
            } finally {
                this.loading(false);
            }
        },
        NAP_DS_DANDO: async function() {
            try {
                this.loading(true);
                let obj = this.dsPORT.find(v => v.VITRI == this.cboPORT && v.LOAITB_ID == this.cboLOAI_TBI)
                let rs = await this.$root.context.post('/web-cabman/daunoi-cap-thuebao/lay-ds-dando', {
                    loaitb_id: obj.LOAITBI_ID,
                    thietbi_id: obj.THIETBI_ID,
                    vitri: obj.VITRI,
                    mat: this.mat
                });
                this.dsDanDo = rs.data;
            } catch (error) {
                
            } finally {
                this.loading(false);
            }
        },
        DAUNOI_CAP_THUEBAO: async function(thuebao, loaitbi_id, thietbi_id, vitri, culy, ghichu) {
            try {
                let rs = await this.$root.context.post('/web-cabman/daunoi-cap-thuebao/daunoicap-thuebao', {
                    madoicap: thuebao.MADOICAP,
                    thuebao_id: thuebao.THUEBAO_ID,
                    daucuoi_id: thuebao.DAUCUOI_ID,
                    loaitb_id: loaitbi_id,
                    thietbi_id: thietbi_id,
                    vitri: vitri,
                    culy: culy,
                    ghichu: ghichu
                });
                return rs.data.result;
            } catch (error) {
                return false;
            }
        },
        KIEMTRA_QUYEN_NHANVIEN_THUEBAO: async function(vthuebao_id) {
            try {
                let rs = await this.$root.context.post('/web-cabman/daunoi-cap-thuebao/kiemtra-quyen-thuebao', {
                    thuebao_id: vthuebao_id,
                    maloaidoituong: 'THUEBAO'
                });
                let kq = rs.data.RESULT != null && rs.data.RESULT != 'OK'
                if(kq) {
                    this.$root.toastError(rs.data.RESULT);
                }
                return !kq;
            } catch (error) {
                
            }
        },
        KIEMTRA_DANDO: async function(dt, thuebao, loaitbi_id, thietbi_id, vitri, mat) {
            if(dt == null) {
                try {
                    let rs = await this.$root.context.post('/web-cabman/daunoi-cap-thuebao/lay-ds-dando', {
                        loaitb_id: loaitbi_id,
                        thietbi_id: thietbi_id,
                        vitri: vitri,
                        mat: mat
                    });
                    dt = rs.data;
                } catch (error) {
                    return false;
                }
            }
            if(dt[0]["IDX"] != 0) {
                this.$root.toastError("Ví trí này đã có cáp ra không thể đấu nối thuê bao");
                return false;
            }

            switch (thuebao['DICHVUVT_ID']) {
                case 1:
                case 4:
                case 11:
                    let arr = dt.filter(v => v.LOAITBI_ID != 25 && v.LOAITBI_ID != 26);
                    if(arr.length == 0) {
                        this.$root.toastError("Ví trí này chưa thông đến tổng đài, không thể đấu nối!");
                        return false;
                    }
                    break;
                case 10:
                    if(dt[0]['LOAI_TBI'] != 'kết cuối ảo') {
                        this.$root.toastError("Thuê bao Gphone chỉ được gắn vào kết cuối ảo, không có dẫn độ cáp sang kết cuối khác!");
                        return false;
                    }
                    arr = dt.filter(v => v.LOAITBI_ID != 25 && v.LOAITBI_ID != 26);
                    if(arr.length != 0) {
                        this.$root.toastError("Thuê bao Gphone chỉ được gắn vào kết cuối ảo, không có dẫn độ cáp sang kết cuối khác!");
                        return false;
                    }
                    arr = dt.filter(v => v.LOAITBI_ID = 25 && v.THIETBI_ID != thietbi_id);
                    if(arr.length != 0) {
                        this.$root.toastError("Thuê bao Gphone chỉ được gắn vào kết cuối ảo, không có dẫn độ cáp sang kết cuối khác!");
                        return false;
                    }
                    break;
                default:
                    break;
            }
            return true;
        },
        btnLamMoi_Click: function() {
            this.NAP_DS_DANDO();
        },
        btnXacNhan_Click: async function() {
            try {
                this.loading(true);
                if(!this.cboPORT) {
                this.$root.toastError("Chưa chọn cổng để đấu nối.");
                return;
                }
                if(this.txtCuLy === '' || this.txtCuLy == null) {
                    this.$root.toastError("Chưa nhập cự ly cáp.");
                    return;
                }
                if(this.dsDanDo.length == 0) {
                    this.$root.toastError("Không có dẫn độ cáp.");
                    return;
                }
                if(this.thuebao['THUEBAO_ID'] > 0) {
                    let ktratb = await this.KIEMTRA_QUYEN_NHANVIEN_THUEBAO(this.thuebao['THUEBAO_ID']);
                    if(!ktratb)
                        return;
                }
                let obj = this.dsPORT.find(v => v.VITRI == this.cboPORT)
                let ktradando = await this.KIEMTRA_DANDO(this.dsDanDo, this.thuebao, obj.LOAITBI_ID, obj.THIETBI_ID, this.cboPORT, this.mat);
                if(!ktradando) {
                    return;
                }
                let ktradaunoi = await this.DAUNOI_CAP_THUEBAO(this.thuebao, obj.LOAITBI_ID, obj.THIETBI_ID, this.cboPORT, this.txtCuLy, this.txtGhiChu);
                if(!ktradaunoi) {
                    return;
                }
                this.$emit('xacnhan', 'OK');
                this.$bvModal.hide(this.modalId);
            } catch (error) {
                console.log(error);
            } finally {
                this.loading(false);
            }
        },
        showForm: function() {
            this.rd_daunoi = 0,
            this.txtKyHieuKC = null,
            this.txtCuLy = 0,
            this.cboPORT = null,
            this.cboTTVT = null,
            this.cboTOKT = null,
            this.cboTRAM = null,
            this.cboLOAI_TBI = null,
            this.cboTHIETBI = null,
            this.cboCARD = null,
            this.cboMODULE = null,
            this.txtGhiChu = null,

            this.dsTTVT = [],
            this.dsTOKT = [],
            this.dsTRAM = [],
            this.dsLOAI_TBI = [],
            this.dsTHIETBI = [],
            this.dsCARD = [],
            this.dsMODULE = [],
            this.dsPORT = [],
            this.dsDanDo = []
        }
    },
    watch: {
        rd_daunoi: async function(val) {
            if(val == 1) {
                await this.NAP_DS_TTVT();
            }
            else {
                this.cboTTVT = null;
                this.cboTOKT = null;
                this.cboTRAM = null;
                this.cboLOAI_TBI = null;
                this.cboTHIETBI = null;
                this.cboCARD = null;
                this.cboMODULE = null;
            }
        },
        cboTTVT: async function(val) {
            if(val) {
                await this.NAP_DS_TOVT();
            }
        },
        cboTOKT: async function(val) {
            if(val) {
                await this.NAP_DS_TRAM();
            }
        },
        cboTRAM: async function(val) {
            if(val) {
                await this.NAP_DS_LOAI_TBI();
            }
        },
        cboLOAI_TBI: async function(val) {
            if(val) {
                await this.NAP_DS_THIETBI();
            }
        },
        cboTHIETBI: async function(val) {
            if(val) {
                await this.NAP_DS_CARD();
            }
        },
        cboCARD: async function(val) {
            if(val) {
                await this.NAP_DS_MODULE();
            }
        },
        cboMODULE: async function(val) {
            if(val) {
                await this.NAP_DS_PORT();
            }
        },
        cboPORT: async function(val) {
            if(val) {
                await this.NAP_DS_DANDO();
            }
        }
    }
}
</script>
<style>
.form-daunoi-cap-thuebao .select2-container .select2-selection--single .select2-selection__rendered {
    white-space: initial;
    height: 28px;
}
</style>
