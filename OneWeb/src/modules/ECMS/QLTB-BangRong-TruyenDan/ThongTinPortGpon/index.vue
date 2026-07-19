<template src="./index.html">
</template>

<script>
import XLSX from "xlsx";
export default {
    data: function() {
        return {
            port_id: null,
            card_id: null,
            portGponItem: {},
            txt_serial: null,
            SFP_id: null,
            ds_SFP: [],
            txt_ngaylap: null,
            txt_nhap_serial: null,
            dsThueBao: [],
            dataExcelEx: [],
            btnDoiTrangThai: {
                isDisable: true,
                tag: null
            },
            isDoiTT: false
        }
    },
    methods: {
        layDsLoaiSFP: async function() {
            try {
                let rs = await this.$root.context.get(`/web-ecms/danhmuc/layDsLoaiSFP`);
                this.ds_SFP = rs.data;                
            } catch (error) {
                
            }
        },
        layThongTinGponCard: async function() {
            try {
                let rs = await this.$root.context.get(`/web-ecms/portgpon/thongtin/${this.port_id}`);
                this.portGponItem = rs.data;
            } catch (error) {
                
            }
        },
        loadPortGpon: async function() {
            try {
                this.loading(true);
                await this.layThongTinGponCard();
                this.txt_serial = this.portGponItem.SERIAL;
                this.txt_ngaylap = this.portGponItem.NGAYLAP;
                this.SFP_id = this.portGponItem.LOAI_SFP_ID;
                if(this.portGponItem.TT_PORT_ID == 5) {
                    this.btnDoiTrangThai = {
                        isDisable: false,
                        tag: "kichhoat"
                    };
                }
                else if(this.portGponItem.TT_PORT_ID == 3) {
                    if(this.portGponItem.LINK_CODE == 0) { //nếu không phải là porrt uplink, downlink mà ko có thuê bao thì cho đổi trạng thái
                        let rs = await this.$root.context.get(`/web-ecms/portgpon/lay-sl-thuebao-doitt-theoportid/${this.port_id}`);
                        if(rs.data == 0) {
                            this.btnDoiTrangThai = {
                                isDisable: false,
                                tag: "huykichhoat"
                            };
                        }
                    }
                }
                else if(this.portGponItem.TT_PORT_ID == 1) {
                    this.btnDoiTrangThai = {
                        isDisable: false,
                        tag: "huykichhoat"
                    };
                }
                let rsDSThueBao = await this.$root.context.get(`/web-ecms/portgpon/lay_ds_thuebao_theo_portvl_id/${this.port_id}`);
                this.dsThueBao = rsDSThueBao.data;
            } catch (error) {
                
            } finally {
                this.loading(false);
            }
        },
        doiTrangThai: async function() {
            this.isDoiTT = true;
            this.txt_nhap_serial = null;
            // this.$bvModal.show('modal-serial');
            await this.xacNhan();
        },
        closeModal: function() {
        },
        xacNhan: async function() {
            try {
                this.loading(true);
                if(this.isDoiTT) {
                    if(this.btnDoiTrangThai.tag == 'kichhoat') {
                        try {
                            // if(this.txt_nhap_serial.length > 0) {
                            //     let rsKTSerial = await this.$root.context.get(`/web-ecms/portgpon/kiemtra-trung-serial?serial=${this.txt_nhap_serial}`);
                            //     if(rsKTSerial.data.result) {
                            //         this.$root.toastError("Số serial này bị trùng !");
                            //         return;
                            //     }
                            // }
                            let rsChuyenTT = await this.$root.context.post('/web-ecms/portgpon/chuyen_trangthai_port', {
                                port_id: this.port_id,
                                serial: this.txt_nhap_serial,
                                code: this.btnDoiTrangThai.tag
                            });
                            if(rsChuyenTT.data.result) {
                                this.$root.toastSuccess("Đổi trạng thái thành công !");
                            }
                            else {
                                this.$root.toastError("Đổi trạng thái thất bại !");
                            }
                        } catch (error) {
                            
                        }
                    }
                    else if(this.btnDoiTrangThai.tag == 'huykichhoat') {
                        try {
                            let rsChuyenTT = await this.$root.context.post('/web-ecms/portgpon/chuyen_trangthai_port', {
                                port_id: this.port_id,
                                serial: this.txt_nhap_serial,
                                code: this.btnDoiTrangThai.tag
                            });
                            if(rsChuyenTT.data.result) {
                                this.$root.toastSuccess("Đổi trạng thái thành công !");
                            }
                            else {
                                this.$root.toastError("Đổi trạng thái thất bại !");
                            }
                        } catch (error) {
                            
                        }
                    }
                    await this.loadPortGpon();
                    this.$emit('updatedNodeGponPort', this.portGponItem.TT_PORT_ID);
                }
                else {
                    if(this.txt_nhap_serial.length > 0) {
                        let rsKTSerial = await this.$root.context.get(`/web-ecms/portgpon/kiemtra-trung-serial?serial=${this.txt_nhap_serial}`);
                        if(rsKTSerial.data.result) {
                            this.$root.toastError("Số serial này bị trùng !");
                            return;
                        }
                        let rsCapNhatSerial = await this.$root.context.post('/web-ecms/portgpon/capnhat-serial', {
                            port_id: this.port_id,
                            serial: this.txt_nhap_serial
                        })
                        if(rsCapNhatSerial.data.result) {
                            this.$root.toastSuccess("Cập nhật serial thành công !");
                        }
                        else {
                            this.$root.toastError("Cập nhật serial thất bại !");
                        }
                    }
                    await this.loadPortGpon();
                }
            } catch (error) {
                
            } finally {
                this.loading(false);
            }
        },
        doiSerial: function() {
            this.isDoiTT = false;
            this.$bvModal.show('modal-serial');
        },
        xuatExcel: function() {
            if(this.dsThueBao.length == 0) {
                this.$root.toastError("Không có dữ liệu để xuất Excel !");
            }
            else {
                try {
                    this.loading(true);
                    this.dataExcelEx = this.dsThueBao.map(v => {
                        return {
                            'Slot/Port/Ont ID': v.TS_OLT,
                            'Slot': v.SLOT,
                            'Port': v.PORT,
                            'Vpi/Svlan': v.VPI,
                            'Vci/Cvlan': v.VCI,
                            'Rack': v.RACK,
                            'Số máy/Acc': v.MA_TB,
                            'Shelf': v.SHELF,
                            'Loại hình': v.LOAIHINH_TB,
                            'Tên thuê bao': v.TEN_TB,
                            'Địa chỉ': v.DIACHI_LD,
                            'Trạng thái thuê bao': v.TRANGTHAI_TB,
                            'PortVL ID': v.PORTVL_ID,
                            'PortLG ID': v.PORT_ID
                        }
                    });
                    let worksheet = XLSX.utils.json_to_sheet(Object.values(this.dataExcelEx));
                    let wb = XLSX.utils.book_new();
                    XLSX.utils.book_append_sheet(wb, worksheet, "PORT");
                    XLSX.writeFile(wb, "export.xlsx");
                } catch (error) {
                    this.$root.toastError("Có lỗi trong quá trình xuất file Excel !");
                } finally {
                    this.loading(false);
                }
            }
        },
        xuatVisa: async function() {
            try {
                this.loading(true);
                let arr = [];
                let obj = {
                    ACCOUNT: '',
                    IP_BRAS:'',
                    MA_DSLAM: '',
                    THAMSO_DSLAM: ''
                }
                let rs = await this.$root.context.get(`/web-ecms/portgpon/tao-dulieu-visa/${this.port_id}`);
                rs.data.forEach(element => {
                    obj = {
                        ACCOUNT: element.ACCOUNT,
                        IP_BRAS:element.IP_BRAS,
                        MA_DSLAM: element.MA_DSLAM,
                        THAMSO_DSLAM: element.THAMSO_DSLAM
                    }
                    arr.push(obj);
                });
                if(arr.length > 0) {
                    let worksheet = XLSX.utils.json_to_sheet(Object.values(arr));
                    let wb = XLSX.utils.book_new();
                    XLSX.utils.book_append_sheet(wb, worksheet, "VISA"); // sheetAName is name of Worksheet
                    // export Excel file
                    XLSX.writeFile(wb, "export.xlsx");
                }
                else {
                    arr.push(obj);
                    this.dataExcelEx = Object.values(arr);
                    var worksheet = XLSX.utils.json_to_sheet(Object.values(this.dataExcelEx));
                    var wb = XLSX.utils.book_new(); // make Workbook of Excel
                    XLSX.utils.book_append_sheet(wb, worksheet, "VISA"); // sheetAName is name of Worksheet
                    // export Excel file
                    XLSX.writeFile(wb, "export.xlsx");
                }
            } catch (error) {
                this.$root.toastError("Có lỗi trong quá trình xuất file Excel !");
            } finally {
                this.loading(false);
            }
        },
        bindData: async function(data) {
            this.port_id = data.itemID;
            this.card_id = data.cardGponId;
            this.txt_serial = null;
            this.txt_ngaylap = null;
            await this.layDsLoaiSFP();
            await this.loadPortGpon();
        },
        formatterSLOT(field, data, column) {
            console.log(field, data, column);
            return data['SLOT'].padStart(2, 0)
        },
        formatterPORT(field, data, column) {
            console.log(field, data, column);
            return data['PORT'].padStart(2, 0)
        },
        saveInfo(){

        },
        saveSFP: async function() {
            try {
                this.loading(true);
                let rsSaveSFP = await this.$root.context.post('/web-ecms/portgpon/capnhat-loai-sfp', {
                                port_id: this.port_id,
                                loai_sfp_id: this.SFP_id
                            });
                            if(rsSaveSFP.data.result) {
                                this.$root.toastSuccess("Lưu thông tin SFP thành công !");
                            }
                            else {
                                this.$root.toastError("Lưu thông tin SFP thất bại !");
                            }
                
            } catch (error) {
                
            } finally {
                this.loading(false);
            }
        }
    },
    // mounted: async function() {
    //     // this.port_id = 528596//data.itemID;
    //     // this.card_id = 2380//data.cardGponId;
    //     // await this.loadPortGpon();
    // }
}
</script>
