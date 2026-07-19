<template src="./index.html">
</template>

<script>
import EventBus from '../../../../utils/eventBus'
import ModalLoaiDongBo from '../LoaiDongBo/Modal'
import ModalDMDongBo from '../DMDongBo/Modal'
import ModalDMRing from '../DMRing/Modal'
export default {
    components: {
        ModalLoaiDongBo,
        ModalDMDongBo,
        ModalDMRing
    },
    data() {
        return {
            nhatram_id: 0,
            turack_id: 0,
            frame_id: 0,
            tenFrame: null,
            serial: null,
            soSQ: null,
            soSlot: null,
            slotBD: null,
            rack: null,
            loaiDsLam_id: null,
            loaiDBVao_id: null,
            loaiDBRa_id: null,
            dongbo_id: null,
            ring_id: null,
            hangSX_id: null,
            giaoDien_id: null,
            dsLoaiDsLam: [],
            dsLoaiDongBoVao: [],
            dsLoaiDongBoRa: [],
            dsDongBo: [],
            dsRing: [],
            dsHangSX: [],
            dsGiaoDien: [],
            chk_soSQ: true,
            isDisabledSoSQ: false,
            isDisabledSoSlot: true,
            isDisabledSlotBD: true,
            isThemMoi: false,
            currentItem: {}
        }
    },
    methods: {
        loadDsLoaiDsLam: async function() {
            try {
                let rs = await this.$root.context.get('/web-ecms/danhmuc/ds-loaidslam');
                this.dsLoaiDsLam = rs.data;
            } catch (error) {
                
            }
        },
        loadDsLoaiDongBo: async function() {
            try {
                let rs = await this.$root.context.get('/web-ecms/danhmuc/loaidongbo/lay_ds_loaidongbo');
                this.dsLoaiDongBoVao = rs.data;
                this.dsLoaiDongBoRa = rs.data;
            } catch (error) {
                
            }
        },
        loadDsDongBo: async function() {
            try {
                let rs = await this.$root.context.get('/web-ecms/danhmuc/list-dmdongbo');
                this.dsDongBo = rs.data;
            } catch (error) {
                
            }
        },
        loadDsRing: async function() {
            try {
                let rs = await this.$root.context.get('/web-ecms/ring/danh-sach');
                this.dsRing = rs.data;
            } catch (error) {
                
            }
        },
        loadDsHangSanXuat: async function() {
            try {
                let rs = await this.$root.context.get('/web-ecms/danhmuc/HangSX/DanhSach');
                this.dsHangSX = rs.data;
            } catch (error) {
                
            }
        },
        loadDsGiaoDien: async function() {
            try {
                let rs = await this.$root.context.get('/web-ecms/danhmuc/layDsGiaoDien');
                this.dsGiaoDien = rs.data;
            } catch (error) {
                
            }
        },
        loadFrameTD: async function() {
            try {
                this.loading(true);
                await this.loadDsLoaiDsLam();
                await this.loadDsLoaiDongBo();
                await this.loadDsDongBo();
                await this.loadDsRing();
                await this.loadDsHangSanXuat();
                await this.loadDsGiaoDien();
                let rs = await this.$root.context.get(`/web-ecms/frame-truyendan/get-frametd-id?frametd_id=${this.frame_id}`);
                if(rs.data) {
                    this.tenFrame = rs.data.TEN_FRAME;
                    this.serial = rs.data.SERIAL;
                    this.soSQ = rs.data.SO_SOIQUANG;
                    this.soSlot = rs.data.SO_SLOT;
                    this.slotBD = rs.data.SLOT_BD;
                    this.rack = rs.data.RACK;
                    this.loaiDsLam_id = rs.data.LOAI_DSLAM_ID;
                    this.loaiDBVao_id = rs.data.LOAIDB_VAO_ID;
                    this.loaiDBRa_id = rs.data.LOAIDB_RA_ID;
                    this.dongbo_id = rs.data.DONGBO_VAO_ID;
                    this.ring_id = rs.data.RING_ID;
                    this.hangSX_id = rs.data.HANGSX_ID;
                    this.giaoDien_id = rs.data.GIAODIEN_ID;
                }
            } catch (error) {
                
            } finally {
                this.loading(false);
            }
        },
        clearForm: function() {
            this.isDisabledSoSlot = false;
            this.isDisabledSlotBD = false;
            this.tenFrame = null;
            this.soSlot = 1;
            this.slotBD = 1;
            this.soSQ = 1;
            this.rack = 1;
            this.serial = null;
            this.$refs.tenFrameInp.focus();
        },
        checkForm: function() {
            let check = true;
            let error = '';

            if(this.rack == null) {
                check = false;
                error = 'Chưa nhập rack !';
            }

            if(this.ring_id == null) {
                check = false;
                error = 'Chưa chọn vòng ring !\n'.concat(error);
            }

            if(this.soSQ == null) {
                check = false;
                error = 'Chưa nhập số sợi quang !\n'.concat(error);
            }

            if(this.slotBD == null) {
                check = false;
                error = 'Chưa nhập slot bắt đầu !\n'.concat(error);
            }

            if(this.soSlot == null) {
                check = false;
                error = 'Chưa nhập số slot !\n'.concat(error);
            }

            if(!this.dongbo_id) {
                check = false;
                error = 'Chưa chọn đồng bộ vào !\n'.concat(error);
            }

            if(!this.serial) {
                check = false;
                error = 'Chưa nhập serial !\n'.concat(error);
            }

            if(!this.loaiDsLam_id) {
                check = false;
                error = 'Chưa chọn loại frame !\n'.concat(error);
            }

            if(!this.loaiDBRa_id) {
                check = false;
                error = 'Chưa chọn loại đồng bộ ra !\n'.concat(error);
            }

            if(!this.loaiDBVao_id) {
                check = false;
                error = 'Chưa chọn loại đồng bộ vào !\n'.concat(error);
            }

            if(!this.tenFrame) {
                check = false;
                error = 'Tên frame không được để trống !\n'.concat(error);
            }

            if(!check) {
                this.$root.toastError(error);
            }
            return check;
        },
        taoDuLieuFrameTD: function() {
            return {
                frametd_id : this.frame_id,
                ten_frame : this.tenFrame,
                turack_id : this.turack_id,
                so_slot : this.soSlot,
                serial : this.serial,
                loai_dslam_id : this.loaiDsLam_id,
                loaidb_vao_id : this.loaiDBVao_id,
                loaidb_ra_id : this.loaiDBRa_id,
                so_soiquang : this.soSQ,
                ring_id : this.ring_id,
                dongbo_vao_id : this.dongbo_id,
                hangsx_id : this.hangSX_id,
                giaodien_id : this.giaoDien_id,
                slot_bd : this.slotBD,
                rack: this.rack
            }
        },
        ghiLai: async function() {
            if(this.checkForm()) {
                if(this.isThemMoi) {
                    try {
                        this.loading(true);
                        var rs = await this.$root.context.post('/web-ecms/frame-truyendan/them-frametd', this.taoDuLieuFrameTD());
                        if(rs.data) {
                            this.$root.toastSuccess("Thêm mới thành công");
                            this.isThemMoi = false;
                            this.currentItem = {
                                frame_id: rs.data.FRAMETD_ID,
                                tenFrame: this.tenFrame,
                                turack_id: this.turack_id,
                                so_slot: this.soSlot
                            }
                            this.frame_id = rs.data.FRAMETD_ID;
                            return true;
                        }
                    } catch (error) {
                        this.$root.toastError(error.response.data.message_detail ? error.response.data.message_detail : "Thêm thất bại");
                    } finally {
                        this.loading(false);
                    }
                }
                else {
                    try {
                        this.loading(true);
                        var rs = await this.$root.context.post('/web-ecms/frame-truyendan/capnhat-frametd', this.taoDuLieuFrameTD());
                        if(rs.data) {
                            this.$root.toastSuccess("Cập nhật thành công");
                            this.currentItem = {
                                frame_id: this.frame_id,
                                tenFrame: this.tenFrame,
                                turack_id: this.turack_id,
                                so_slot: this.soSlot
                            }
                            return true;
                        }
                    } catch (error) {
                        this.$root.toastError(error.response.data.message_detail ? error.response.data.message_detail : "Cập nhật thất bại");
                    } finally {
                        this.loading(false);
                    }
                }
            }
        },
        xoa: async function() {
            // this.$bvModal.msgBoxConfirm('Bạn chắc chắn muốn xóa?', {
            //     size: 'sm',
            //     okTitle: 'Đồng ý',
            //     cancelTitle: 'Hủy',
            // }).then(async v => {
            //     if(v) {
                    
            //     }
            // }).catch(err => {
            //     this.$toast.toastError('Xóa thất bại');
            // })
            try {
                this.loading(true);
                var rs = await this.$root.context.post(`/web-ecms/frame-truyendan/xoa-frametd?frametd_id=${this.frame_id}`);
                if(rs.data.result) {
                    this.$root.toastSuccess("Xóa dữ liệu thành công!");
                    return true;
                }
            } catch (error) {
                this.$root.toastError(error.response.data.message_detail);
            } finally {
                this.loading(false);
            }
        },
        themMoi: function() {
            this.clearForm();
            this.isThemMoi = true;
            this.loaiDBVao_id = this.dsLoaiDongBoVao.length ? this.dsLoaiDongBoVao[0].LOAIDB_ID : null;
            this.loaiDBRa_id = this.dsLoaiDongBoRa.length ? this.dsLoaiDongBoRa[0].LOAIDB_ID : null;
            this.loaiDsLam_id = this.dsLoaiDsLam.length ? this.dsLoaiDsLam[0].LOAI_DSLAM_ID : null;
            this.dongbo_id = this.dsDongBo.length ? this.dsDongBo[0].DONGBO_ID : null;
            this.ring_id = this.dsRing.length ? this.dsRing[0].RING_ID : null;
        },
        bindData: async function(data) {
            this.frame_id = data.itemID;
            this.nhatram_id = data.nhatram_id;
            this.turack_id = data.turack_id;
            await this.loadFrameTD();
            this.isDisabledSoSlot = true;
            this.isDisabledSlotBD = true;
            this.chk_soSQ = true;
            if(this.frame_id == 0) {
                this.themMoi();
            }
            else {
                this.isThemMoi = false;
            }
        },
        closeModalLoaiDongBo: async function() {
            try {
                this.loading(true);
                await this.loadDsLoaiDongBo();
            } catch (error) {
                
            } finally {
                this.loading(false);
            }
        },
        closeModalDMDongBo: async function() {
            try {
                this.loading(true);
                await this.loadDsDongBo();
            } catch (error) {
                
            } finally {
                this.loading(false);
            }
        },
        closeModalDMRing: async function() {
            try {
                this.loading(true);
                await this.loadDsRing();
            } catch (error) {
                
            } finally {
                this.loading(false);
            }
        }
    },
    watch: {
        chk_soSQ: function(val) {
            this.isDisabledSoSQ = !val;
        }
    },
    mounted: function() {
        try {
            let vm = this;
            EventBus.$on('thongTinFrameTD', async data => {
                this.nhatram_id = data.nhatram_id;
                this.turack_id = data.turack_id;
                this.frame_id = data.frame_id;
                await this.loadFrameTD();
            })

            EventBus.$on('themMoi', () => {
                vm.clearForm();
                vm.isThemMoi = true;
            })

            EventBus.$on('ghiLai', () => {
                vm.ghiLai();
            })

            EventBus.$on('xoa', () => {
                vm.xoa();
            })

            EventBus.$on('huyBo', () => {
                vm.clearForm();
            })
        } catch (error) {
            
        }
    },
    destroyed() {
        EventBus.$off("themMoi");
        EventBus.$off("ghiLai");
        EventBus.$off("huyBo");
        EventBus.$off("xoa");
    }
}
</script>
