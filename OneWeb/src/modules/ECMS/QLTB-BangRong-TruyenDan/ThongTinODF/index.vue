<template src="./index.html">
</template>

<script>
import EventBus from '../../../../utils/eventBus'
import { Filter, Page, Sort, CommandColumn } from "@syncfusion/ej2-vue-grids";
import ModalTruyenDan from '../DDCTruyenDan/Modal'

let filterBarTemplate = {
    create: function (args) {

        let div = document.createElement('div')
        div.classList.add('input-icon-right')
        
        let input = document.createElement('input')
        input.classList.add('form-control')
        input.id = args.column.foreignKeyField

        let span = document.createElement('span')
        span.classList.add('icon', 'nc-icon-outline', 'ui-1_zoom')

        div.append(input)
        div.append(span)

        return div
    },
    write: function (args) {
        
        args.element.querySelector('input').addEventListener('input', (args) => {
            let value = args.currentTarget.value

            if (!value) {
                this.parent.removeFilteredColsByField(args.currentTarget.id);
            } else {
                this.parent.filterByColumn(args.currentTarget.id, 'contains', value)
            }
        })
    }
}

let traCuuColumnTemplate = function() {
    return {
        template: {
            template: `
                <a href="javascript:void(0)" class="btn btn-second padt1 padb1 padl4 padr4 h-auto w60" @click="traCuu()">
                    <span class="nc-icon-glyph -ap  icon-dots-three-horizontal f14"></span> 
                </a>
                `,
            data() {
                return {
                    data: {

                     }
                }
            },
            computed: {
                parent() {
                    return this.$parent.$parent;
                },
                value() {
                    return `${this.data}`
                }
            },
            methods: {
                traCuu: function() {
                    this.parent.traCuuTruyenDan(this.data.VITRI);
                }
            }
        },
    }
}

export default {
    components: {
        ModalTruyenDan
    },
    provide: {
        grid: [Filter, Page, Sort, CommandColumn]
    },
    data() {
        return {
            ttvt_id: 0,
            tokt_id: 0,
            turack_id: 0,
            odf_id: 0,
            nhatram_id: 0,
            tenOdf: null,
            tenOdf_cu: null,
            soPort: null,
            portBD: null,
            ghiChu: null,
            dsPort: [],
            isDisabledSoPort: true,
            isDisabledPortBD: true,
            isThemMoi: false,
            currentItem: {},
            dataTD: {},
            pageSettings: { pageSizes: ['10','20','50','100','All'], pageCount: 3, pageSize: 10 },
            selectOptions: { type: 'Single', mode: 'Row' },
            port: [
                {
                    field: 'VITRI',
                    headerText: 'Port',
                    width: 103,
                    textAlign: 'Right',
                    minWidth: 10,
                    headerTextAlign: 'Center',
                    filter: {operator: 'contains'},
                    filterBarTemplate: filterBarTemplate
                },
            ],
            dauVao: [
                {
                    field: 'CAP_VAO',
                    headerText: 'Dây nhảy / thuê bao',
                    width: 103,
                    textAlign: 'Right',
                    minWidth: 10,
                    headerTextAlign: 'Center',
                    filter: {operator: 'contains'},
                    filterBarTemplate: filterBarTemplate
                },
                {
                    field: 'SOI_VAO',
                    headerText: 'Sợi',
                    width: 103,
                    textAlign: 'Right',
                    minWidth: 10,
                    headerTextAlign: 'Center',
                    filter: {operator: 'contains'},
                    filterBarTemplate: filterBarTemplate
                }
            ],
            matTruoc: [
                {
                    field: 'CONG_VAO',
                    headerText: 'Vị trí',
                    width: 103,
                    textAlign: 'Right',
                    minWidth: 10,
                    headerTextAlign: 'Center',
                    filter: {operator: 'contains'},
                    filterBarTemplate: filterBarTemplate
                }
            ],
            thongTinCong: [
                {
                    field: 'GHICHU',
                    headerText: 'Ghi chú',
                    width: 103,
                    textAlign: 'Right',
                    minWidth: 10,
                    headerTextAlign: 'Center',
                    filter: {operator: 'contains'},
                    filterBarTemplate: filterBarTemplate
                }
            ],
            matSau: [
                {
                    field: 'CONG_RA',
                    headerText: 'Vị trí',
                    width: 103,
                    textAlign: 'Right',
                    minWidth: 10,
                    headerTextAlign: 'Center',
                    filter: {operator: 'contains'},
                    filterBarTemplate: filterBarTemplate
                }
            ],
            dauRa: [
                {
                    field: 'CAP_RA',
                    headerText: 'Cáp ra',
                    width: 103,
                    textAlign: 'Right',
                    minWidth: 10,
                    headerTextAlign: 'Center',
                    filter: {operator: 'contains'},
                    filterBarTemplate: filterBarTemplate
                },
                {
                    field: 'SOI_RA',
                    headerText: 'Sợi',
                    width: 103,
                    textAlign: 'Right',
                    minWidth: 10,
                    headerTextAlign: 'Center',
                    filter: {operator: 'contains'},
                    filterBarTemplate: filterBarTemplate
                }
            ],
            traCuu: [
                {
                    // type: "Tra Cứu",
                    // buttonOption: {
                    //     cssClass: "nc-icon-glyph -ap icon-dots-three-horizontal f14",
                    // },
                    template: traCuuColumnTemplate,
                    headerText: 'Tra cứu',
                    width: 103,
                    allowFilter: false,
                    textAlign: 'Right',
                    minWidth: 10,
                    headerTextAlign: 'Center',
                    // filter: {operator: 'contains'},
                    // filterBarTemplate: filterBarTemplate
                }
            ]
        }
    },
    methods: {
        getDonVi: async function (donViID) {
            try {
                let rs = await this.$root.context.post(
                    "/web-ecms/danhmuc/layThongTinDonVi",
                    {
                    id: donViID,
                    }
                );
                return rs.data;
            } catch (error) {
                
            }
        },
        loadDsPortTheoOdf: async function() {
             try {
                let rs = await this.$root.context.get(`/web-ecms/danhmuc/odf/lay_ds_port_odf_theo_odf_id?odfId=${this.odf_id}`);
                this.dsPort = rs.data;
            } catch (error) {
                this.$root.toastError("Có lỗi khi lấy thông tin port odf");
            }
        },
        loadThongTinODF: async function() {
            try {
                this.loading(true);
                if(this.odf_id != 0) {
                    await this.loadDsPortTheoOdf();
                    let rs = await this.$root.context.get(`/web-ecms/danhmuc/odf/lay_odf_theo_odf_id?odfId=${this.odf_id}`);
                    if(rs.data) {
                        this.tenOdf = rs.data.TEN_ODF;
                        this.tenOdf_cu = rs.data.TEN_ODF;
                        this.soPort = rs.data.SO_PORT;
                        this.portBD = rs.data.PORT_BD;
                        this.ghiChu = rs.data.GHICHU;
                        this.turack_id = rs.data.TURACK_ID;
                    }
                }
            } catch (error) {
                
            } finally {
                this.loading(false);
            }
        },
        clearForm: function() {
            this.tenOdf = null;
            this.soPort = 1;
            this.portBD = 1;
            this.ghiChu = null;
            this.isDisabledSoPort = false;
            this.isDisabledPortBD = false;
        },
        checkForm: async function() {
            let check = true;
            let error = '';

            if(this.portBD == null) {
                check = false;
                error = 'Chưa nhập port bắt đầu !'
            }

            if(this.soPort == null) {
                check = false;
                error = 'Chưa nhập số port !\n'.concat(error);
            }

            if(!this.tenOdf) {
                check = false;
                error = 'Chưa nhập tên Odf !\n'.concat(error);
            }
            else {
                if(this.isThemMoi) {
                    let kq = await this.$root.context.get(`/web-ecms/danhmuc/odf/kiemtra-trung-tenodf?tenOdf=${this.tenOdf}`);
                    if(kq.data.result) {
                        check = false;
                        error = 'Tên Odf đã tồn tại !\n'.concat(error);
                    }
                }
                else if(this.tenOdf.toUpperCase() != this.tenOdf_cu.toUpperCase()) {
                    let kq = await this.$root.context.get(`/web-ecms/danhmuc/odf/kiemtra-trung-tenodf?tenOdf=${this.tenOdf}`);
                    if(kq.data.result) {
                        check = false;
                        error = 'Tên Odf đã tồn tại !\n'.concat(error);
                    }
                }
            }

            if(!check) {
                this.$root.toastError(error);
            }
            return check;
        },
        taoDuLieuOdf: function() {
            return {
                odfId: this.odf_id,
                turackId: this.turack_id,
                tenOdf: this.tenOdf,
                soPort: this.soPort,
                portBd: this.portBD,
                ghichu: this.ghiChu
            }
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
                        this.loading(true);
                        var rs = await this.$root.context.post('/web-ecms/danhmuc/odf/tao_odf', this.taoDuLieuOdf());
                        if(rs.data) {
                            this.isThemMoi = false;
                            this.tenOdf_cu = this.tenOdf;
                            this.isDisabledSoPort = true;
                            this.isDisabledPortBD = true;
                            this.currentItem = {
                                odf_id: rs.data.ODF_ID,
                                turack_id: this.turack_id,
                                ten_odf: this.tenOdf,
                                so_slot: this.soPort
                            };
                            this.odf_id = rs.data.ODF_ID;
                            // let rsTaoPort = await this.$root.context.get(`/web-ecms/danhmuc/odf/tao-port-odf?odfId=${rs.data.ODF_ID}`);
                            // if(rsTaoPort.data.result) {
                            // }
                            // else {
                                
                            // }
                            await this.loadDsPortTheoOdf();
                            this.$root.toastSuccess('Thêm mới thành công!');
                            return true;
                        }
                        else {
                            this.$root.toastError('Thêm mới odf lỗi!');
                        }
                    } catch (error) {
                        this.$root.toastError(error.response.data.message_detail);
                    } finally {
                        this.loading(false);
                    }
                }
                else {
                    try {
                        this.loading(true);
                        var rs = await this.$root.context.post('/web-ecms/danhmuc/odf/capnhat_odf', this.taoDuLieuOdf());
                        if(rs.data) {
                            this.$root.toastSuccess('Cập nhật thành công!');
                            this.currentItem = {
                                odf_id: this.odf_id,
                                turack_id: this.turack_id,
                                ten_odf: this.tenOdf,
                                so_slot: this.soPort
                            };
                            return true;
                        }
                    } catch (error) {
                        this.$root.toastError(error.response.data.message_detail);
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
                var rs = await this.$root.context.post(`/web-ecms/danhmuc/odf/xoa_odf?odfId=${this.odf_id}`);
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
        selectingEvent: function() {

        },
        dataBound(args){
            
        },
        traCuuTruyenDan: function(vitri) {
            this.dataTD = {
                ttvt: this.ttvt_id,
                toKT: this.tokt_id,
                tramTB: this.nhatram_id,
                loaiThietBiCha: 18,
                thietBi: this.odf_id,
                cong: vitri
            };
            this.$bvModal.show('popupTruyenDanId');
        },
        commandTTClick: function(args){
            
        },
        bindData: async function(data) {
            this.odf_id = data.itemID;
            this.nhatram_id = data.nhatram_id;
            this.turack_id = data.turack_id;
            this.dsPort = []
            let rsToKT = await this.getDonVi(this.nhatram_id);
            if(rsToKT) {
                this.tokt_id = rsToKT.DONVI_CHA_ID;
                let rsTTVT = await this.getDonVi(this.tokt_id);
                this.ttvt_id = rsTTVT.DONVI_CHA_ID;
            }
            await this.loadThongTinODF();
            this.isDisabledSoPort = true;
            this.isDisabledPortBD = true;
            if(this.odf_id == 0) {
                this.themMoi();
            }
            else {
                this.isThemMoi = false;
            }
        }
    },
    mounted: async function() {
        try {
            let vm = this;
            EventBus.$on('ThongTinODF', async data => {
                this.turack_id = data.turack_id;
                this.odf_id = data.odf_id;
                this.nhatram_id = data.nhatram_id;
                await this.loadThongTinODF();
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
