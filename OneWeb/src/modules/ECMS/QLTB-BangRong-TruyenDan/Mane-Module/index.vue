<template src="./index.html">
</template>

<script>
export default {
    data: function() {
        return {
            modulemane_id: null,
            cardmane_id: null,
            txt_vitri: null,
            txt_soPort: null,
            txt_portBD: null,
            txt_serial: null,
            txt_partNumber: null,
            cbo_loaimodule_id: null,
            serial_cu: null,
            vitri_cu: null,
            ten: null,
            dsLoaiModule: [],
            isThemMoi: false,
            currentItem: {},
            isLoading: false
        }
    },
    methods: {
        layDsLoaiModule: async function() {
            try {
                let rs = await this.$root.context.get('/web-ecms/mane-module/ds-loaimodule');
                this.dsLoaiModule = rs.data;
                if(!this.isLoading) {
                    this.cbo_loaimodule_id = this.dsLoaiModule.length ? this.dsLoaiModule[0].LOAIMODULE_ID : null;
                }
            } catch (error) {
                
            }
        },
        loadManeModule: async function() {
            try {
                this.loading(true);
                await this.layDsLoaiModule();
                if(this.modulemane_id) {
                    let rs = await this.$root.context.get(`/web-ecms/mane-module/thongtin/${this.modulemane_id}`);
                    this.cbo_loaimodule_id = rs.data.LOAIMODULE_ID;
                    this.txt_vitri = rs.data.VITRI;
                    this.txt_soPort = rs.data.SO_PORT;
                    this.txt_portBD = rs.data.PORT_BD;
                    this.txt_serial = rs.data.SERIAL;
                    this.serial_cu = rs.data.SERIAL;
                    this.vitri_cu = rs.data.VITRI;
                    this.txt_partNumber = rs.data.PARTNUMBER;
                    this.isLoading = false;
                }
                else {
                    this.txt_vitri = null;
                    this.txt_serial = null;
                    this.txt_partNumber = null;
                }
            } catch (error) {
                
            } finally {
                this.loading(false);
            }
        },
        clearForm: function() {
            this.txt_vitri = null;
        },
        checkForm: async function() {
            let check = true;
            let error = '';
            if(this.txt_serial && this.txt_serial != this.serial_cu) {
                let rs = await this.$root.context.get(`/web-ecms/mane-module/kiemtra-trung-serial?serial=${this.txt_serial}`);
                if(rs.data.result) {
                    error = 'Serial này đã được sử dụng !';
                    check = false;
                }
            }

            if(!this.txt_vitri) {
                error = 'Chưa nhập vị trí cắm module \n'.concat(error);
                check = false;
            }
            else {
                if(this.txt_vitri != this.vitri_cu) {
                    let rs = await this.$root.context.post('/web-ecms/mane-module/kiemtra-trung-vitri', {
                        cardmane_id: this.cardmane_id,
                        vitri: this.txt_vitri
                    });
                    if(rs.data.result) {
                        error = 'Vị trí này đã có card cắm, hãy nhập vị trí khác !\n'.concat(error);
                        check = false;
                    }
                }
            }

            if(!this.cbo_loaimodule_id) {
                error = 'Chưa chọn loại module \n'.concat(error);
                check = false;
            }

            if(!check) {
                this.$root.toastError(error);
            }
            return check;
        },
        taoDuLieuModuleMane: function() {
            let obj = {
                cardmane_id: this.cardmane_id,
                vitri: this.txt_vitri,
                serial: this.txt_serial,
                partnumber: this.txt_partNumber,
                loaimodule_id: this.cbo_loaimodule_id
            }
            if(!this.isThemMoi) {
                obj.modulemane_id = this.modulemane_id;
            }
            return obj;
        },
        themMoi: function() {
            this.isThemMoi = true;
            this.clearForm();
        },
        ghiLai: async function() {
            let kq = await this.checkForm();
            if(kq) {
                if(this.isThemMoi) {
                    try {
                        let rsThemModule = await this.$root.context.post('/web-ecms/mane-module/them-manemodule', this.taoDuLieuModuleMane());
                        if(rsThemModule.data.length) {
                            let rsTaoPort = await this.$root.context.post(`/web-ecms/mane-module/createport-manemodule?modulemane_id=${rsThemModule.data[0].MODULEMANE_ID}`);
                            let dsPort = rsTaoPort.data;
                            this.$root.toastSuccess("Thêm mới thành công");
                            this.serial_cu = this.txt_serial;
                            this.vitri_cu = this.txt_vitri;
                            this.isThemMoi = false;
                            this.currentItem = {
                                modulemane_id: rsThemModule.data[0].MODULEMANE_ID,
                                cardmane_id: this.cardmane_id,
                                tenModule: this.ten ? this.getNumberString(this.txt_vitri) + ' ' + this.ten : null,
                                index: this.txt_vitri,
                                so_port: this.txt_soPort,
                                port_bd: this.txt_portBD,
                                dsPort: dsPort.map(v => {
                                    return {
                                        ID: v.PORTVL_ID,
                                        viTri: v.VITRI,
                                        trangThai: v.TT_PORT_ID
                                    }
                                }),
                            };
                            this.modulemane_id = this.currentItem.modulemane_id;
                            return true;
                        }
                    } catch (error) {
                        this.$root.toastError("Thêm mới thất bại");
                    }
                }
                else {
                    try {
                        let rsThemModule = await this.$root.context.post('/web-ecms/mane-module/capnhat-manemodule', this.taoDuLieuModuleMane());
                        if(rsThemModule.data) {
                            let rsTaoPort = await this.$root.context.post(`/web-ecms/mane-module/createport-manemodule?modulemane_id=${this.modulemane_id}`);
                            let dsPort = rsTaoPort.data;
                            console.log(dsPort);
                            this.$root.toastSuccess("Cập nhật thành công");
                            this.serial_cu = this.txt_serial;
                            this.vitri_cu = this.txt_vitri;
                            this.isThemMoi = false;
                            this.currentItem = {
                                modulemane_id: this.modulemane_id,
                                cardmane_id: this.cardmane_id,
                                tenModule: this.ten ? this.getNumberString(this.txt_vitri) + ' ' + this.ten : null,
                                index: this.txt_vitri,
                                so_port: this.txt_soPort,
                                port_bd: this.txt_portBD,
                                dsPort: dsPort.map(v => {
                                    return {
                                        ID: v.PORTVL_ID,
                                        viTri: v.VITRI,
                                        trangThai: v.TT_PORT_ID
                                    }
                                }),
                            };
                            return true;
                        }
                    } catch (error) {
                        console.log(error);
                        this.$root.toastError("Cập nhật thất bại");
                    }
                }
            }
        },
        xoa: async function() {
             try {
                this.loading(true);
                var rs = await this.$root.context.post(`/web-ecms/mane-module/xoa-manemodule?modulemane_id=${this.modulemane_id}`);
                if(rs.data) {
                    this.$root.toastSuccess("Xóa dữ liệu thành công!");
                    return true;
                }
            } catch (error) {
                this.$root.toastError(error.response.data.message_detail);
            } finally {
                this.loading(false);
            }
        },
        bindData: async function(data) {
            this.modulemane_id = data.itemID;
            this.cardmane_id = data.cardmane_id;
            this.isLoading = true;
            if(data.itemID) {
                this.isThemMoi = false;
            }
            else {
                this.themMoi();
                this.isLoading = false;
            }
            await this.loadManeModule();
        },
        getNumberString: function (value, pre) {
            let result = "#00";
            if (!this.checkIsEmpty(value)) {
                result = value.toString();
                if (result.length == 1) result = "0" + result;
                result = "#" + result;
            }
            if (!this.checkIsEmpty(pre)) result = pre + result;
            return result;
        },
        checkIsNull: function (value) {
            return value == undefined || value == null;
        },
        checkIsEmpty: function (value) {
            let rs = this.checkIsNull(value);
            if (!rs) {
                rs = value.toString().trim() == "";
            }
            return rs;
        },
    },
    watch: {
        cbo_loaimodule_id: function(val) {
            if(val) {
                let obj = this.dsLoaiModule.find(v => v.LOAIMODULE_ID == val);
                if(obj) {
                    this.ten = obj.LOAI_MODULE;
                    this.txt_soPort = obj.SO_PORT;
                    this.txt_portBD = obj.PORT_BD;
                }
            }
            else {
                this.ten = null;
            }
        }
    }
}
</script>
