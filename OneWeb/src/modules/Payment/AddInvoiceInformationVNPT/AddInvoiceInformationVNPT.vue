<template>
    <div class="main-wrapper">
        <breadcrumb :header="header" />
        <div class="list-actions-top">
            <ul class="list">
                <li :style="!btnSave.visible ? 'display: none' : ''">
                    <a @click="btnSave_Click" :class="btnSave.enable ? '' : 'disabled'"> <span
                            class="icon nc-icon-glyph one-save"></span>Ghi lại </a>
                </li>


            </ul>

        </div>
        <div class="page-content">
            <div class="grid-stack-box">
                <!-- Thong Tin Thue bao -->
                <div class="box-col box-form" id="boxLeft">
                    <div class="legend-title"><span class="icon fa fa-angle-up"></span> Thông tin thuê bao</div>
                    <div class="row">
                        <div class="col-sm-6 col-12">
                            <div class="info-row">

                                <div class="key w210">Mã TB</div>
                                <div class="value">
                                    <div class="input-more-button">
                                        <button ref="bntLayMaKh" v-b-tooltip.hover.bottom="'Tìm kiếm'" class="btn"
                                            @click="showPopupSearchContract">
                                            <span class="-ap icon-more_horiz"></span>
                                        </button>
                                        <input ref="txtMaTB" v-model="txtMaTB" type="text"
                                            class="form-control highlight" @keypress="onMaTBKeyPress">
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="col-sm-6 col-12">
                            <div class="info-row">
                                <div class="key w200">Dịch vụ</div>
                                <div class="value">

                                    <div class="select-custom">
                                        <ejs-dropdownlist locale="vi-VN" v-model="cboDichVuVT.value"
                                            :dataSource="cboDichVuVT.dataSource" :allowFiltering="true"
                                            :fields="{ value: 'DICHVUVT_ID', text: 'TEN_DVVT' }" />
                                    </div>
                                </div>

                            </div>
                        </div>




                    </div>

                    <div class="row">
                        <div class="col-sm-6 col-12">
                            <div class="info-row">
                                <div class="key w210">Tên thuê bao(EN)</div>
                                <div class="value">
                                    <input type="text" class="form-control" v-model="txtTen_TB" />
                                </div>
                            </div>
                        </div>


                        <div class="col-sm-6 col-12">
                            <div class="info-row">
                                <div class="key w210">Đối tác</div>
                                <div class="value">
                                    <div class="select-custom">
                                        <ejs-dropdownlist id="DOITAC_ID" locale="vi-VN" v-model="cboDoiTac.value"
                                            :dataSource="cboDoiTac.dataSource" :allowFiltering="true"
                                            :fields="{ value: 'doitac_id', text: 'ten_dt' }" />
                                    </div>
                                </div>
                            </div>
                        </div>

                    </div>
                    <div class="row">
                        <div class="col-sm-6 col-12">
                            <div class="info-row">
                                <div class="key w210">Mã HĐ VTI</div>
                                <div class="value">
                                    <input type="text" class="form-control" v-model="txtMa_HD" />
                                </div>
                            </div>
                        </div>
                        <div class="col-sm-6 col-12">
                            <div class="info-row">
                                <div class="key w200">Loại hoá đơn</div>
                                <div class="value">
                                    <div class="select-custom">
                                        <ejs-dropdownlist id="DVKHAC_ID" locale="vi-VN" v-model="cboLoaiHoaDon.value"
                                            :dataSource="cboLoaiHoaDon.dataSource" :allowFiltering="true"
                                            :fields="{ value: 'DVKHAC_ID', text: 'TEN_DVKHAC' }" />
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>


                    <div class="row">

                        <div class="col-sm-6 col-12">
                            <div class="info-row">
                                <div class="key w210">PO Number</div>
                                <div class="value">
                                    <input type="text" class="form-control" v-model="txtPO_Number" />
                                </div>
                            </div>
                        </div>
                        <div class="col-sm-6 col-12">
                            <div class="info-row">
                                <div class="key w210">OrderID</div>
                                <div class="value">
                                    <input type="text" class="form-control" v-model="txtOrder_ID" />
                                </div>
                            </div>
                        </div>

                        <div class="col-sm-6 col-12">
                            <div class="info-row">
                                <div class="key w210">Circuit ID partner</div>
                                <div class="value">
                                    <input type="text" class="form-control" v-model="txtCircuit_ID_partner" />
                                </div>
                            </div>
                        </div>



                        <div class="col-sm-6 col-12">
                            <div class="info-row">
                                <div class="key w210">Số tháng CK</div>
                                <div class="value">
                                    <input type="text" class="form-control" v-model="txtCamKet_SD" readonly />
                                </div>
                            </div>
                        </div>


                    </div>
                    <div class="info-row">
                        <div class="key w210">Địa chỉ LĐ(EN)</div>
                        <div class="value">
                            <input type="text" class="form-control" v-model="txtDiaChiLD" />
                        </div>
                    </div>
                    <div class="info-row">
                        <div class="key w210">Địa chỉ POP(EN)</div>
                        <div class="value">
                            <input type="text" class="form-control" v-model="txtDiaChiPOP" />
                        </div>
                    </div>


                </div>
                <SearchContractModal ref="popupSearchContract" @accept="acceptSearchContract" :donvi_id="0"
                    :nhanvien_id="0" :donvi_dl_id="0" />

            </div>
        </div>
    </div>
</template>

<script>

import breadcrumb from '@/components/breadcrumb'
import api from "./api.js";
import apiHelper from './api.helper'

import SearchContractModal from "@/modules/contract/setup/HandoverTicket/components/SearchContractModal.vue"

export default {
    name: 'AddInvoiceInformationVNPT',
    components: {
        breadcrumb,
        SearchContractModal
    },
    data() {
        return {

            header: {
                title: 'Bổ sung thông tin hoá đơn',
                list: [
                    { name: 'Bổ sung thông tin hoá đơn', link: { name: 'Ui.cards' } },
                ]
            },
            cboDichVuVT: {
                dataSource: [],
                value: 0,
            },
            cboDoiTac: {
                dataSource: [],
                value: 0,
            },
            cboLoaiHoaDon: {
                dataSource: [],
                value: 0,
            },
            btnSave: {
                visible: true,
                enable: true
            },
            txtMaTB: "",
            txtDiaChiLD: "",
            txtDiaChiPOP: "",
            txtDichVu: "",
            txtPO_Number: "",
            txtOrder_ID: "",
            txtCircuit_ID_partner: "",
            txtTen_TB: "",
            txtCamKet_SD: "",
            txtMa_HD: "",
            thuebao_id: 0,
        }
    },
    async created() {
        await Promise.all([this.getDanhSachDVVT(), this.getCboHoaDon(), this.getCboDoiTac()])
    },
    methods: {
        async getDanhSachDVVT() {

            try {
                let response = await api.getcboDichVuVT(this.axios);
                this.cboDichVuVT.dataSource = apiHelper.getDataFromResponseApiReturnArray(response);
                // if (this.cboDichVuVT.dataSource.length > 0)
                //     this.cboDichVuVT.value = this.cboDichVuVT.dataSource[0]["DICHVUVT_ID"];

            } catch (e) {
                console.log(e)
            }
        },
        async getCboHoaDon() {

            try {
                let response = await api.getcboDichVuKhac(this.axios);
                this.cboLoaiHoaDon.dataSource = apiHelper.getDataFromResponseApiReturnArray(response).filter(e => e.DVKHAC_ID == 92 || e.DVKHAC_ID == 91);
                // if(this.cboLoaiHoaDon.dataSource.length)  this.cboLoaiHoaDon.value = this.cboLoaiHoaDon.dataSource[0]['DVKHAC_ID']

            } catch (e) {
                console.log(e)
            }
        },
        async getCboDoiTac() {

            try {
                let response = await api.getThongtinDoiTac(this.axios);
                this.cboDoiTac.dataSource = apiHelper.getDataFromResponseApiReturnArray(response)
                // if(this.cboDoiTac.dataSource.length)  this.cboDoiTac.value = this.cboDoiTac.dataSource[0]['DOITAC_ID']

            } catch (e) {
                console.log(e)
            }
        },
        async btnSave_Click() {
            try {
                this.loading(true)
                const params = {
                    p_thuebao_id: this.thuebao_id,
                    p_diachi_ld_sub: this.txtDiaChiLD,
                    p_diachi_pop: this.txtDiaChiPOP,
                    p_dvkhac_id: this.cboLoaiHoaDon.value,
                    p_dichvuvt_id: this.cboDichVuVT.value,
                    p_doitac_id: this.cboDoiTac.value,
                    p_po_number: this.txtPO_Number,
                    p_order_id: this.txtOrder_ID,
                    p_circuit_id: this.txtCircuit_ID_partner,
                    p_ten_tb: this.txtTen_TB,
                    p_ma_hd: this.txtMa_HD
                }
                const response = await api.saveThongTinThueBao_v2(this.axios, params)
                const result = apiHelper.getDataFromResponseApiReturnRaise(response);

                this.loading(false);

                if (result != "OK") {
                    this.$toast.error(result)
                    return
                }

                this.$toast.success('Cập nhật thành công')

            } catch (error) {
                this.loading(false);
                this.$toast.error('Có lỗi xảy ra')
                console.log(error);
            }
        },
        showPopupSearchContract() {
            this.$refs.popupSearchContract.showModal()
        },

        acceptSearchContract(item) {
            this.txtMaTB = item['ma_tb']
            this.onMaTBKeyPress({ charCode: 13 })
        },

        // PO Number => SO_CONGVAN
        // OrderID => MAVI_NGANSACH,
        // Circuit ID Parner => NOICAP,
        // Tên thuê bao =>  NGUOI_KY_HOSO,
        // THỜI GIAN CAM KẾT=> SO_THANG,
        // Mã hợp đồng => SO_GT


        // txtPO_Number: "",
        // txtOrder_ID: "",
        // txtCircuit_ID_partner: "",
        // txtTen_TB: "",
        // txtCamKet_SD: "",
        // txtMa_HD: "",

        async onMaTBKeyPress(e) {
            if (e.charCode !== 13) {
                return
            }
            if (!this.txtMaTB) {
                this.$toast.error('Vui lòng nhập mã thuê bao')
                return
            }
            try {
                this.loading(true);
                this.clear();
                const response = await api.getThongTinThueBao(this.axios, this.txtMaTB)
                const items = apiHelper.getDataFromResponseApiReturnArray(response);


                if (items.length == 0) {
                    this.$toast.error('Không tìm thấy thông tin thuê bao')
                    return
                }

                const item = items[0];

                this.txtDiaChiLD = item['diachi_ld_sub']
                this.txtDiaChiPOP = item['diachi_pop_sub']
                this.cboDichVuVT.value = item['dichvuvt_id']
                this.cboLoaiHoaDon.value = item['dvkhac_id']
                this.cboDoiTac.value = item['doitac_id']
                this.thuebao_id = item['thuebao_id']
                this.txtPO_Number = item['so_congvan']
                this.txtOrder_ID = item['mavi_ngansach']
                this.txtCircuit_ID_partner = item['noicap']
                this.txtTen_TB = item['nguoi_ky_hoso']
                this.txtCamKet_SD = item['so_thang']
                this.txtMa_HD = item['so_gt']
            } catch (error) {

                console.log(error)
            } finally {
                this.loading(false)

            }

        },
        clear() {
            this.txtDiaChiLD = ""
            this.txtDiaChiPOP = ""
            this.cboDichVuVT.value = 0
            this.cboLoaiHoaDon.value = 0
            this.cboDoiTac.value = 0
            this.thuebao_id = 0
            this.txtPO_Number = ''
            this.txtOrder_ID = ''
            this.txtCircuit_ID_partner = ''
            this.txtTen_TB = ''
            this.txtCamKet_SD = ''
            this.txtMa_HD = ''
        }

    },
}
</script>
