<template>
    <div class="box-form">
        <div class="legend-title">Tập thuê bao khôi phục thanh lý / tạm dừng</div>
            <TDataGrid
                ref="refgrdTBkhoiphuc"
                :columns="grdTBkhoiphuc.columns"
                :dataSource="grdTBkhoiphuc.dataSources"
                :enable-paging-server="false"
                :showFilter="true"
                :allowGrouping="true"
                :showColumnCheckbox="true"
                :enabledSelectFirstRow="false"
                panelDataHeight="420px"
                @rowSelected="onSelectRow_DSTB"
            />
    </div>
</template>   

<script>
import api from "./api_ObNhancong.js"
import TDataGrid from './DataGrid'

export default {
    name: 'ThongTinKHOBNC',
    components: {
        TDataGrid
  },
  data() {
        return {
            currentComponent: null, 
            currentTab: 1,
            MaKH: '',
            txttungay : null,
            txtdenngay : null,
            showPopup: false,
            btnStatus: {
                btnLayttEnabled: true,
                btnCapnhatEnabled: true,
                btnOutboundEnabled: true,
            },
            grdTBkhoiphuc: {
                columns: [
                    {fieldName: 'sdt_tt', headerText: '', isGroupedColumn: true, captionTemplate: this.captionTB_TraTruocemplate(this) },
                    {fieldName: 'ma_kh', headerText: '', isGroupedColumn: true, captionTemplate: this.captionTB_TraTruocemplate(this) },
                    {fieldName: "sdt_kh", headerText: "SĐT_KH", width: '130px'},
                    {fieldName: "ma_tb", headerText: "Mã TB", width: '130px'},
                    {fieldName: "ten_tb", headerText: "Tên TB", width: '130px'},
                    {fieldName: "loaihinh_tb", headerText: "Loại TB", width: '130px'},
                    {fieldName: "ten_trangthaitb", headerText: "Trạng thái TB", width: '130px'},
                    {fieldName: "ten_tt", headerText: "Tên TT", width: '130px'},
                    {fieldName: "diachi_ld", headerText: "Địa chỉ LĐ", width: '130px'},
                    {fieldName: "ma_tt", headerText: "Mã TT", width: '130px'},
                    {fieldName: "diachi_tt", headerText: "Địa chỉ TT", width: '130px'},
                    {fieldName: "ma_gd", headerText: "Mã GD", width: '130px'},
                    {fieldName: "ten_kieuld", headerText: "Kiểu YC", width: '130px'},
                    {fieldName: "ngay_yc", headerText: "Ngày YC", width: '130px'},
                    {fieldName: "ngay_bd_td", headerText: "Ngày TD/TL", width: '130px'},
                    {fieldName: "nv_laphd", headerText: "Nhân viên lập HĐ", width: '130px'},
                    {fieldName: "bien_dong", headerText: "Biến động phát sinh sau ngày lập HĐ TD/TL", width: '350px'},
                    { fieldName: "khachhang_id", width: '0px'}, 
                    { fieldName: "kieuld_id",  width: '0px'}, 
                    { fieldName: "thuebao_id", width: '0px'}, 
                    { fieldName: "hdtb_id", width: '0px'},
                ],
                dataSources: [],
            },
        };
    },
    methods: {
        
        async getListOBNC(vtapob_id, vtungay, vdenngay) {
            try {
                this.loading(true)
                let response = await api.getDsTbKhoiphucThanhlyTamdung(this.axios, vtapob_id, vtungay, vdenngay);
                this.grdTBkhoiphuc.dataSources = response.data.data;
            } catch (error) {
                console.log(error);
            } finally {
                this.loading(false)
            }
        },
        getInfoKHOB(sdt){
            this.$emit('sdt_thanhtoan', sdt);
        },

        captionTB_TraTruocemplate: function(parent) {
            return function() {
                return {
                    template: {
                        template: `
                            <div>
                                <template v-if="isSDT_TT">
                                    SĐT liên hệ thanh toán: 
                                    <span style="color: red; cursor: pointer;" @click="handleInfoKHOB(tbSdttt.sdt_tt)">
                                        {{ tbSdttt.sdt_tt }}
                                    </span>
                                </template>
                                <template v-else>
                                    {{ captionValue }}
                                </template>
                            </div>
                        `,
                        data: function() {
                            return { 
                                data: {}, 
                                tbSdttt: {},  // Để lưu thông tin thiết bị khi field là 'sdt_tt'
                                isSDT_TT: false,  // Kiểm tra xem field có phải là 'sdt_tt' không
                                txtSdtkh: '',
                            }
                        },
                        computed: {
                            captionValue: function(value) {
                                const key = this.data.key;
                                const field = this.data.field;

                                if (field == 'sdt_tt') {
                                    const { items, count } = this.data
                                    this.tbSdttt = items[0]?.items[0]
                                    //return `SĐT liên hệ thanh toán: ${tbSdttt.sdt_tt}`
                                    //return `SĐT liên hệ thanh toán: <span class="text-blue-500 cursor-pointer" @click="getInfoKHOB('${tbSdttt.sdt_tt}')">${tbSdttt.sdt_tt}</span>`;
                                    this.isSDT_TT = true;  // Đặt cờ để biết là trường hợp 'sdt_tt'
                                    return ''; 
                                }
                              
                                if (field == 'ma_kh') {
                                const { items, count } = value.data
                                const tbTamdung = items[0]
                                //console.log(tbTraTruoc);
                                return `Mã KH: ${tbTamdung.ma_kh} - ${tbTamdung.ten_kh} - ${tbTamdung.sdt_kh} - ${tbTamdung.diachi_kh}`
                                }
                            }
                        },
                        methods: {
                            handleInfoKHOB(sdt){
                                parent.getInfoKHOB(sdt)
                            }
                        }
                    }
                }
            }
        },
        onSelectRow_DSTB(data) {
            if (!data?.data) return
            console.log("selecting item 1 ", data)
        },
        getListThueBaoSlected() {
            return this.$refs.refgrdTBkhoiphuc.grid.getSelectedRecords()
        },
        collapseSpecificGroup: function(groupValue) {//dong group
            var groupedRows = Array.from(this.$refs.refgrdTBkhoiphuc.grid.getContentTable().querySelectorAll('.e-recordplusexpand'));
            var groupNameToToggle = groupValue; 
            
            var groupCaptionElement = groupedRows.find(row => {
                //alert(document.querySelector('.e-groupcaption span').textContent)
                return document.querySelector('.e-groupcaption span').textContent.includes(groupNameToToggle);
            });

            if (groupCaptionElement) {
                this.$refs.refgrdTBkhoiphuc.grid.ej2Instances.groupModule.expandCollapseRows(groupCaptionElement);
            } 

        },

        expandSpecificGroup: function(groupValue) {
            var groupedRows = Array.from(this.$refs.refgrdTBkhoiphuc.grid.getContentTable().querySelectorAll('.e-recordpluscollapse'));
            var groupNameToToggle = groupValue; 

            groupedRows.forEach((row, index) => {
                //alert(row.getAttribute('ej-mappingvalue'))
                if (row.getAttribute('ej-mappingvalue') === groupValue) {
                    var groupCaptionElement = groupedRows[index];
                    this.$refs.refgrdTBkhoiphuc.grid.ej2Instances.groupModule.expandCollapseRows(groupCaptionElement);
                }
            });

        },
            
        desel_grid_RowDeselected(sdttt) {
            //console.log(this.$refs.refgrdTBkhoiphuc.grid)
            //alert('sdt '+ sdttt)
            this.$refs.refgrdTBkhoiphuc.grid.clearSelection()
            this.$refs.refgrdTBkhoiphuc.grid.ej2Instances.groupModule.collapseAll();
            
            this.expandSpecificGroup(sdttt)
        },
    },
    mounted() {
        this.expandSpecificGroup();
    }
}
</script> 
