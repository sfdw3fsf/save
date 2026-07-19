<template src="./GiaoPhieuBaoHanh.html"></template>
<style scoped src="./GiaoPhieuBaoHanh.scss"></style>
<script>
import { mapActions, mapState, mapGetters, mapMutations } from "vuex";
import breadcrumb from "@/components/breadcrumb";
import moment from "moment";
import xlsx from "xlsx";
import pdfFonts from "pdfmake/build/pdfmake";
import printJS from "print-js";
import { Query } from "@syncfusion/ej2-data";
import { TooltipPlugin } from "@syncfusion/ej2-vue-popups";
import { Tooltip } from '@syncfusion/ej2-popups';
import Vue from "vue";
import {previewPrint} from "../../../utils/util";

Vue.use(TooltipPlugin);
export default {
    components: { breadcrumb },
    name: "GiaoPhieuBaoHanh",
    mounted() {
        this.$refs.serial.focus();
        this.statusCheckAll = this.$refs.GPBH.selectAllItem;
        this.khoSelected = this.listKho.length > 0 ? this.listKho[0].value : "";
    },
    watch: {
        khoSelected: function(data) {
            this.checkCbxKho();
        },
        quyTrinhSelected: function() {
            if (this.loadHuongGiao) {
                this.fetchDSHuongGiao();
            }
        },
        statusCheckAll: function(data) {
            this.$refs.GPBH.selectAllItem = data;
        },
        listKho: function(data) {
            this.khoSelected = data[0].KHO_ID;
            this.text_khoSelected = data[0].TEN_K_NEW;
        },
        huongGiaoSelected: {
            handler: async function(val) {               
                if(this.huongGiaoSelected ==  null || this.huongGiaoSelected == "" || this.huongGiaoSelected ==  0){
                    return
                }
                await this.fetchDSLuongThaoTac();
                this.listLuongThaoTac = this.listLuongThaoTac.filter(a=> a.HUONGGIAO_ID == this.huongGiaoSelected)
                console.log(this.listLuongThaoTac , " = this.listLuongThaoTac")
                if(this.PHAILAM("BHTB_GUI_BH_TTVT")){
                    await this.fetchDanhSachKho();
                    this.listKho = this.listKho.filter(a=>a.TRANGTHAI == "1" && a.LOAIKHO_ID == "14")
                    if(this.listKho.length > 0){
                        this.khoSelected = this.listKho[0].KHO_ID
                        this.text_khoSelected = this.listKho[0].TEN_K_NEW    
                        await this.gridKhoGiao_selectedRowChanged(this.listKho[0])                    
                    }else{
                        this.$toast.error("Không lấy dược danh sách kho đang gán cho nhân viên")
                    }                    
                }{
                    await this.fetchDSLoaiDichVuHuongGiao();
                    await this.fetchDSGiaoPhieuBaoHanh();                   
                }
                
            }
        },
        listLoaiDichVuHuongGiao: function(data) {
            console.log({ listloaids: data });
            if (data) {
                this.fetchDSDonViNhan(data);
            }
        },
        trangThaiSelected: {
            handler: async function(val) {                
                this.fetchDSGiaoPhieuBaoHanh();
                if(this.listGiaoPhieuBaoHanh.length == 0){
                    this.serialInp = ""
                    this.ndGiao = ""
                }               
                this.Enabled.btnCapNhatVT = !(this.trangThaiSelected == 2)               

            }
        },
        listItemChecked: async function (value) {      
            this.listItemChecked = value;                      
        },
        checkAllPhieu: async function (value) {      
            this.listItemChecked = []
            if (value) {
                for (const e of this.$refs.GPBH.filteredDataSource) {
                    this.listItemChecked.push(e.PHIEUBH_ID);
                }
            }                
        },
    },
    computed: {
        ...mapState("giaophieubaohanh", [
            "danhSachKho",
            "DSThietBiBaoHanhTra",
            "qt_id",
            "Tag",
            "hg_id"
        ]),
        ...mapGetters("giaophieubaohanh", [
            "danhSachKhoGetters",
            "qt_idGetters",
            "TagGetters",
            "hg_idGetters",
            "DSMucDichXuatVatTuKhoGetters"
        ]),
        ...mapMutations("giaophieubaohanh", ["setTag", "setHuongGiao"])
    },
    async created() {
        try{
            this.clearData();
            await this.fetchDKChoBang();       
            this.quyTrinhSelected = this.qt_idGetters ? this.qt_idGetters : 0;
            await this.firstFetch();
            await this.fetchDSGiaoPhieuBaoHanh()   
        }catch(e){
            this.$toast.error(e.data.message)
        }finally{
            this.loading(false)
        } 
    },
    data() {
        return {
            header: {
                title: "GIAO PHIẾU BẢO HÀNH THIẾT BỊ",
                list: [
                    {
                        name: "Bảo hành thiết bị",
                        link: { name: "Ui.buttons" }
                    },
                    {
                        name: "Giao phiếu bảo hành thiết bị",
                        link: { name: "Ui.buttons" }
                    }
                ]
            },
            statusCheckAll: true,
            today: new Date(),
            p_ngayDen: new Date(),
            pNgayGiaoSearch: new Date(),
            pNgayGiao: new Date(),
            listKho: [],
            khoSelected: "",
            text_khoSelected: "",
            showKhoDroplist: false,
            listQuyTrinh: [],
            quyTrinhSelected: "",
            quyTrinhStatus: true,
            listTrangThai: [
                { value: 1, text: "Chưa giao" },
                { value: 2, text: "Đã giao" }
                // { value: 0, text: "Chưa giao" },
                // { value: 1, text: "Đã giao" },
            ],
            trangThaiSelected: 1,
            listHuongGiao: [],
            Enabled:{
                btnCapNhatVT: true
            },
            huongGiaoSelected: this.$route.query.huongGiaoId
                ? this.$route.query.huongGiaoId
                : "1401",
            listDonViNhan: [],
            donViNhanSelected: "",
            listGiaoPhieuBaoHanh: [],
            listItemChecked: [],
            sendDataChungTu: {},
            isError: false,
            serialInp: "",
            listLuongThaoTac: [],
            listDataSend: [],
            CapNhatGPBHHuongGiao: "",
            CapNhatBaoHanhKho: "",
            listKiemTraTaoChungTu: [],
            statusCapNhatGiaoPhieuBH: "",
            listKieuBaoHanh: [],
            statusHoanThienBaoHanh: "",
            listLoaiDichVuHuongGiao: [],
            serialGP: "",
            ndGiao: "",
            statusTaoChungTu: "",
            statusCapNhatDonViBH: "",
            dkBang: [],
            loadHuongGiao: false,
            selectedData: null,

            checkAllPhieu: false,
            colCheckboxAllPhieu: function () {
            return {
                template: {
                    template: `
                                    <div class="check-action">
                                        <input type="checkbox" v-model="parent.checkAllPhieu" class="uncheck"/>
                                        <span class="name"></span>
                                    </div>
                                `,
                    data: function () {
                        return {};
                    },            
                    computed: {
                        parent() {
                        return this.$parent.$parent.$parent;
                        },
                    },
                    },
                };
            },
       
            colCheckboxPhieu: function () {
                return {
                    template: {
                    template: `
                                    <div class="check-action">
                                        <input
                                            type="checkbox"
                                            class="uncheck"                                    
                                            v-model="parent.listItemChecked"
                                            :value="data.PHIEUBH_ID"/>
                                        <span class="name"></span>
                                    </div>
                                `,
                    data() {
                        return {
                        data: {},
                        };
                    },
                    computed: {
                        parent() {
                        return this.$parent.$parent.$parent;
                        },
                    },
                    },
                };
            },
        };
    },
    methods: {
        ...mapActions("giaophieubaohanh", [
            "getDanhSachKho",
            "getDSHeThongQuyTrinh",
            "getDSDonViTheoLoaiDichVu",
            "getDsGiaoPhieuBaoHanh",
            "taoChungTu",
            "getDSLuongThaoTac",
            "getDSThietBiBaoHanhTra",
            "getCapNhatGPBHHuongGiao",
            "getCapNhatBaoHanhKho",
            "getDSThietBiBaoHanhTra",
            "getDSKiemTraTaoChungTu",
            "CapNhatGiaoPhieuBH",
            "getDSKieuBaoHanh",
            "HoanThienBaoHanh",
            "getDSLoaiDichVuHuongGiao",
            "TaoGiaoPhieuBaoHanh",
            "CapNhatDonViBH",
            "getDSHuongGiao",
            "getLayTenMenu",
            "clearData",
            "tongHopGiaoPhieuBaohanh",
            "huyGiaoPhieu",
            "getDSCayDonVi",
            "getParamsBaoCao"
        ]),
        ...mapActions("tiepnhanthietbibaohanh", ["getDKChoBang"]),
        

        onClose: function(e) {
            this.tooltip.close();
        },

         onBeforeRender : function(args) {
            var result = this.$refs.cboKhoGiao.dataSource;      
            var i;        
            for (i = 0; i < result.length; i++) {        
                if (result[i].TEN_K_NEW === args.target.textContent) {
                this.tooltip.content = result[i].TEN_K_NEW;
                this.tooltip.dataBind();
                break;
                }
             }
        },
        onDropdownCreate:  function(args) {   
            this.tooltip = new Tooltip({
                content: 'Loading...',      
                target: '.e-list-item',     
                position: 'top center',     
                beforeRender: this.onBeforeRender
            });
            this.tooltip.appendTo('body');
        },

        pdfgen: function(dsDonVi) {
            const body = this.handleDataPrint();
            var pdfMake = require("pdfmake/build/pdfmake.js");

            if (pdfMake.vfs == undefined) {
                var pdfFonts = require("pdfmake/build/vfs_fonts.js");
                pdfMake.vfs = pdfFonts.pdfMake.vfs;
            }
            var dateObj = new Date();
            var month = dateObj.getUTCMonth() + 1; //months from 1-12
            var day = dateObj.getUTCDate();
            var year = dateObj.getUTCFullYear();

            var docDefinition = {
                pageMargins: [20, 20, 20, 10],
                pageOrientation: "landscape",
                pageSize: "A4",
                info: {
                    title: "BIENBANGIAONHANTHIETBI.pdf",
                    author: "VNPT-IT3"
                },
                content: [
                    {
                        image:
                            "data:image/png;base64,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",
                        height: 60,
                        width: 60,
                        absolutePosition: { x: 40, y: 15 }
                    },
                    {
                        alignment: "center",
                        columns: [
                            {
                                text: "VIỄN THÔNG HẢI PHÒNG",
                                bold: true,
                                alignment: "center",
                                margin: [30, 10, 0, 0],
                                fontSize: 15
                            },
                            {
                                text: "CỘNG HÒA XÃ HỘI CHỦ NGHĨA VIỆT NAM",
                                bold: true,
                                alignment: "right",
                                marginTop: 10,
                                fontSize: 15
                            }
                        ]
                    },
                    {
                        alignment: "center",
                        columns: [
                            {
                                text: dsDonVi[1].tenDonVi,
                                bold: true,
                                alignment: "center",
                                margin: [30, 0, 0, 0]
                            },
                            {
                                text: "Độc lập -Tự do - Hạnh phúc",
                                bold: true,
                                alignment: "right",
                                margin: [0, 0, 35, 0]
                            }
                        ]
                    },
                    {
                        text: "------o0o------",
                        alignment: "right",
                        margin: [0, 0, 90, 0]
                    },
                    {
                        text: "\nBIÊN BẢN GIAO NHẬN THIẾT BỊ\n",
                        alignment: "center",
                        bold: true,
                        lineHeight: 1.5,
                        fontSize: 15,
                        marginBottom: 10
                    },
                    {
                        text: "Đơn vị giao : " + dsDonVi[0].tenDonVi,
                        bold: true,
                        marginBottom: 3
                    },
                    {
                        text: [
                            {
                                text:
                                    "Người gửi hàng: ..................................................................................... "
                            },
                            {
                                text:
                                    "Bộ phận: ......................................................................................   "
                            }
                        ],
                        marginBottom: 3
                    },
                    {
                        text: [
                            {
                                text:
                                    "Điện thoại liên hệ: .................................................................................... "
                            },
                            {
                                text:
                                    "Email: ........................................................................................"
                            }
                        ],
                        marginBottom: 3
                    },
                    {
                        text: "Đơn vị nhận: " + this.selectedData.DONVINHAN,
                        bold: true,
                        marginBottom: 3
                    },
                    {
                        text: [
                            {
                                text:
                                    "Người nhận hàng: .................................................................................... "
                            },
                            {
                                text:
                                    "Bộ phận: ....................................................................................   "
                            }
                        ],
                        marginBottom: 3
                    },
                    {
                        text: [
                            {
                                text:
                                    "Điện thoại liên hệ: .................................................................................... "
                            },
                            {
                                text:
                                    "Email: ........................................................................................"
                            }
                        ],
                        marginBottom: 5
                    },
                    {
                        table: {
                            widths: [
                                "auto",
                                "auto",
                                "auto",
                                "auto",
                                "auto",
                                "auto",
                                "auto",
                                "auto",
                                "auto",
                                "auto",
                                "auto"
                            ],
                            body: body
                        }
                    },
                    {
                        text: `Hải Phòng, ngày ${day} tháng ${month} năm ${year}`,
                        italics: true,
                        alignment: "right",
                        margin: [10, 10, 60, 0],
                        fontSize: 13
                    },
                    {
                        alignment: "center",
                        columns: [
                            {
                                text: "NGƯỜI GIAO",
                                bold: true,
                                alignment: "left",
                                margin: [40, 10, 0, 0],
                                fontSize: 13
                            },
                            {
                                text: "NGƯỜI NHẬN",
                                bold: true,
                                alignment: "right",
                                margin: [0, 10, 120, 0],
                                fontSize: 13
                            }
                        ]
                    }
                ],
                styles: {
                    icon: { font: "Font" },
                    tableStyle: {
                        fontSize: 10
                    },
                    tableHeaderStyle: {
                        fontSize: 10,
                        bold: true
                    }
                },

                defaultStyle: {
                    font: "Roboto",
                    lineHeight: 1.15,
                    fontSize: 14
                }
            };
            let pdfDocGenerator = pdfMake.createPdf(docDefinition);
            pdfDocGenerator.getBase64(data => {
                printJS({
                    printable: data,
                    type: "pdf",
                    base64: true
                });
            });
        },

        handleDataPrint() {
            let listDataSend = [];
            let body = [
                [
                    {
                        text: "STT",
                        style: "tableHeaderStyle"
                    },
                    {
                        text: "Mã VT",
                        style: "tableHeaderStyle"
                    },
                    {
                        text: "Lô hàng",
                        style: "tableHeaderStyle"
                    },
                    {
                        text: "Tên thiết bị",
                        style: "tableHeaderStyle"
                    },
                    {
                        text: "Số Serial",
                        style: "tableHeaderStyle"
                    },
                    {
                        text: "Số Serial mới",
                        style: "tableHeaderStyle"
                    },
                    {
                        text: "Phụ kiện",
                        style: "tableHeaderStyle"
                    },
                    {
                        text: "Tình trạng",
                        style: "tableHeaderStyle"
                    },
                    {
                        text: "Tổ quản lý",
                        style: "tableHeaderStyle"
                    },
                    {
                        text: "Số lượng",
                        style: "tableHeaderStyle"
                    },
                    {
                        text: "Ghi chú",
                        style: "tableHeaderStyle"
                    }
                ]
            ];
            //lọc lấy item đã được check
            for (let element of this.listItemChecked) {
                for (let val of this.listGiaoPhieuBaoHanh) {
                    if (element === val.PHIEUBH_ID) {
                        listDataSend.push(val);
                    }
                }
            }
            console.log({ listDataSend });
            //chuẩn bị dữ liệu để in
            listDataSend.forEach((item, index) => {
                body.push([
                    {
                        text: index + 1,
                        style: "tableStyle"
                    },
                    {
                        text: item.MA_TB,
                        style: "tableStyle"
                    },
                    {
                        text: item.PHIEUBH_ID,
                        style: "tableStyle"
                    },
                    {
                        text: item.LOAI_TBI,
                        style: "tableStyle"
                    },
                    {
                        text: item.SERIAL,
                        style: "tableStyle"
                    },
                    {
                        text: item.SERIAL_MOI,
                        style: "tableStyle"
                    },
                    {
                        text: item.PHUKIEN,
                        style: "tableStyle"
                    },
                    {
                        text: item.TINHTRANG,
                        style: "tableStyle"
                    },
                    {
                        text: item.TEN_DV,
                        style: "tableStyle"
                    },
                    {
                        text: item.SOLUONG,
                        style: "tableStyle"
                    },
                    {
                        text: item.GHICHU,
                        style: "tableStyle"
                    }
                ]);
            });
            console.log({ body });
            return body;
        },

        async fetchDKChoBang() {            
            let data = {
                nguoiDungId: this.$root.token.getNguoiDungID(),
                tenBang: "HUONGGIAO"
            };
         
            const response = await this.getDKChoBang(data);
            if (response.data.error_code === "BSS-00000000") {
                console.log("DKChoBang", response.data.data);
                response.data.data.forEach(e => {
                    this.dkBang.push(e);
                });
            }
          
        },

        async getTenMenu() {
            let kq = await this.getLayTenMenu({
                tenDiaChi: "WinUI.WinUIBaoHanhThietBi.frmGiaoPhieuBHTB", //"WinUI.WinUIQuanLyVatTu.frmThuocTinh_LVT,PTTB.exe,ShowDialog",
                thongTin: "frmGiaoPhieuBHTB", //this.GetType().Name.toString()
                pageNo: 0,
                maxSize: 10
            });
            console.log({ kq });
        },

        async fetchDanhSachKho() {
            this.loading(true);
            let data = {
                nhanVienId: this.$root.token.getNhanVienID(), //4361,
                donViId: this.$root.token.getDonViID() //1,
            };
            console.log("Danh sách kho data truyền vào", data);
            try {
                const response = await this.getDanhSachKho(data);
                if (response.error_code === "BSS-00000000") {
                    this.listKho = response.data;
                }
            } catch (e) {
            } finally {
                this.loading(false);
            }
        },

        async gridKhoGiao_selectedRowChanged(data) {
          
            this.khoSelected = data.itemData.KHO_ID;
            this.text_khoSelected = data.itemData.TEN_K_NEW;           
            
            if(this.khoSelected != null && this.khoSelected != "" && this.khoSelected != 0 && this.PHAILAM("BHTB_GUI_BH_TTVT")){
                await this.fetchDSLoaiDichVuHuongGiao();
                await this.fetchDSGiaoPhieuBaoHanh();
            }
        },

        async fetchDSHeThongQuyTrinh() {
            this.loading(true);
            let data = {
                nhomQtId: 4
            };
            try {
                const response = await this.getDSHeThongQuyTrinh(data);
                if (response.error_code === "BSS-00000000") {
                    response.data.forEach(element =>
                        this.listQuyTrinh.push({
                            text: element.quyTrinh,
                            value: element.quyTrinhId
                        })
                    );
                    this.quyTrinhSelected = this.listQuyTrinh[0].value;
                }
            } catch (e) {
            } finally {
                this.loading(false);
            }
        },

        async fetchDSLoaiDichVuHuongGiao() {
            this.loading(true);
            let data = {
                huongGiaoId: this.huongGiaoSelected
            };
            try {
                const response = await this.getDSLoaiDichVuHuongGiao(data);               
                if (response.error_code === "BSS-00000000") {
                    this.listLoaiDichVuHuongGiao = response.data[0];
                }
                
            } catch (e) {
            } finally {
                this.loading(false);
            }
        },

        async fetchDSDonViNhan(value) {
            // this.loading(true);
            this.listDonViNhan = [];
            if (value != "" && value) {
                let data = {
                    // loaiDvuId: this.listLoaiDichVuHuongGiao,
                    loaiDvuId: value
                };
                try {
                    const response = await this.getDSDonViTheoLoaiDichVu(data);
                    if (response.error_code === "BSS-00000000") {
                        response.data.forEach(element =>
                            this.listDonViNhan.push({
                                text: element.TEN_HT,
                                value: element.DONVI_ID
                            })
                        );
                    }
                } catch (e) {
                } finally {
                    // this.loading(false);
                }
            }
        },

        async firstFetch() {
            await this.fetchDanhSachKho();
            await this.fetchDSHeThongQuyTrinh();
            await this.fetchDSHuongGiao();
            await this.fetchDSLoaiDichVuHuongGiao();
            await this.fetchDSLuongThaoTac();
        },

        async fetchDSGiaoPhieuBaoHanh() {
            this.loading(true);
            let data = {
                phanVungId: this.$root.token.getPhanVungID(),
                huongGiaoId: this.huongGiaoSelected,
                nhanVienId: this.$root.token.getNhanVienID(),
                serial: this.serialInp == "" ? "0" : this.serialInp,
                ttphId: this.trangThaiSelected
            };
            try {
                const response = await this.getDsGiaoPhieuBaoHanh(data);
                if (response.error_code === "BSS-00000000") {
                    this.listGiaoPhieuBaoHanh = response.data.map(item=> ({
                        ...item,
                        NGAY_YC_STR: item.NGAY_YC ? moment(item.NGAY_YC, 'DD-MM-YYYY HH:mm:ss').format('DD/MM/YYYY'): '',
                        NGAYGIAO_STR: item.NGAYGIAO ? moment(item.NGAYGIAO, 'DD-MM-YYYY HH:mm:ss').format('DD/MM/YYYY'): '',
                    }));
                    if (this.listGiaoPhieuBaoHanh.length > 0 && "KHO_ID" in this.listGiaoPhieuBaoHanh[0] && this.PHAILAM("BHTB_GUI_BH_TTVT")) {
                        this.listGiaoPhieuBaoHanh = this.listGiaoPhieuBaoHanh.filter(a=> a.KHO_ID == this.khoSelected )
                    }
                    this.Enabled.btnCapNhatVT =  this.listGiaoPhieuBaoHanh.length > 0
                    this.listItemChecked = []
                    this.checkAllPhieu = false
                }               
            } catch (ex) {
                this.$toast.error(ex.data.message);
            } finally {
                this.loading(false);
            }
        },

        async fetchDSGiaoPhieuBaoHanh2(_ttphId) {
            this.loading(true);
            let data = {
                phanVungId: this.$root.token.getPhanVungID(),
                huongGiaoId: this.huongGiaoSelected,
                nhanVienId: this.$root.token.getNhanVienID(),
                serial: this.serialInp,
                ttphId: _ttphId
            };
            try {
                const response = await this.getDsGiaoPhieuBaoHanh(data);
                if (response.error_code === "BSS-00000000") {
                     this.listGiaoPhieuBaoHanh = response.data.map(item=> ({
                        ...item,
                        NGAY_YC_STR: item.NGAY_YC ? moment(item.NGAY_YC, 'DD-MM-YYYY HH:mm:ss').format('DD/MM/YYYY'): '',
                        NGAYGIAO_STR: item.NGAYGIAO ? moment(item.NGAYGIAO, 'DD-MM-YYYY HH:mm:ss').format('DD/MM/YYYY'): '',
                    }));
                }
                this.listItemChecked = []
                this.checkAllPhieu = false
                console.log("List Hướng giao", this.listGiaoPhieuBaoHanh);
            } catch (ex) {
                this.$toast.error(ex.data.message);
            } finally {
                this.loading(false);
            }
        },

        // selectedData_checkbox: function(dataItem) {
        //     console.log("selectedData_checkbox", dataItem);
        //     this.listItemChecked = dataItem;
        // },

        gridDanDoCT_selectedRowChanged: function(index, data) {
            console.log("gridDanDoCT_selectedRowChanged", data);
            if (data) {
                // this.huongGiaoSelected = data.HUONGGIAO_ID;
                this.serialGP = data.SERIAL;
                this.pNgayGiao = moment(
                    data.NGAYGIAO,
                    "DD/MM/YYYY hh:mm:ss"
                ).format("DD/MM/YYYY");
                this.ndGiao = data.ND_GIAO;
                this.donViNhanSelected = data.DONVI_ID;
            } else {
                this.serialGP = "";
                this.pNgayGiao = moment(
                    new Date(),
                    "DD/MM/YYYY hh:mm:ss"
                ).format("DD/MM/YYYY");
                this.ndGiao = "";
                this.donViNhanSelected = "";
            }
        },

        PHAILAM(cCODE) {
            if (this.listLuongThaoTac === null) {
                return false;
            }
          
            var checkcode = this.listLuongThaoTac.filter(a=>a.ENABLE == "1" && a.CODE == cCODE && a.HUONGGIAO_ID == this.huongGiaoSelected)
            if(checkcode.length > 0){
                return true;
            }            
            return false;
        },

        async fetchDSLuongThaoTac() {        
            let data = {
                nhomQtId: 4
            };
            let response = await this.getDSLuongThaoTac(data);
            if (response.error_code === "BSS-00000000") {
                this.listLuongThaoTac = response.data;
            }         
        },

        async fetchCapNhatGPBHHuongGiao(_huongGiaoId, _baoHanhId) {
            // this.loading(true);
            let data = {
                huongGiaoId: _huongGiaoId,
                baoHanhId: _baoHanhId
            };
            // console.log("data GPBHHG", data);
            let response = await this.getCapNhatGPBHHuongGiao(data);
            if (response.error_code === "BSS-00000000") {
                this.CapNhatGPBHHuongGiao = response.data;
            }
            // console.log("CapNhatGPBHHuongGiao", this.CapNhatGPBHHuongGiao);
            // this.loading(false);
        },
        // async fetchCapNhatBaoHanhKho(_khoId, _baoHanhId) {
        //   this.loading(true);
        //   let data = {
        //
        //     khoId: _khoId,
        //     baoHanhId: _baoHanhId,
        //   };
        //   let response = await this.getCapNhatBaoHanhKho(data);
        //   if (response.error_code === "BSS-00000000") {
        //     this.CapNhatBaoHanhKho = response.data;
        //   }
        //   // console.log("CapNhatBaoHanhKho", this.CapNhatBaoHanhKho);
        //   this.loading(false);
        // },

        async fetchTaoChungTu_v2(ph, pc, bhid, hg) {
            this.loading(true);
            let data = {
                kieu: 2,
                dsPbh: `[{'ph':'${ph}', 'pc':'${pc}', 'bhid':'${bhid}', 'hg':'${hg}'}]`,
                dsThietBi: "",
                nguoiCn: "bbPc",
                mayCn: "pc",
                vipCn: "10.35.5.149"
            };
            let response = await this.taoChungTu(data);
            if (response.error_code === "BSS-00000000") {
                this.statusTaoChungTu = response.data;
                // this.$toast.success("Hủy giao phiếu thành công");
            } else {
                this.$toast.error("Hủy giao phiếu không thành công");
            }
            // console.log("statusTaoChungTu", this.statusTaoChungTu);
            this.loading(false);
        },

        async fetchKiemTraTaoChungTu(_baoHanhId, _thietBiId, _serial, _dv_id) {
            this.loading(true);
            var listcode = this.listLuongThaoTac.filter(a=> a.HUONGGIAO_ID == this.huongGiaoSelected)
            var code = listcode.length > 0 ? listcode[0].CODE : ""           
            let data = {
                phanVungId: this.$root.token.getPhanVungID(),
                serial: _serial,
                donViNhanId: this.donViNhanSelected,
                donViId: _dv_id,
                code: code,
                chkHt: 1,
                khoGiaoId: this.khoSelected,
                baoHanhId: _baoHanhId,
                thietBiId: _thietBiId,
                nhanVienId: this.$root.token.getNhanVienID()
            };
            try {
                let response = await this.getDSKiemTraTaoChungTu(data);                
                if (response.error_code === "BSS-00000000") {
                    this.listKiemTraTaoChungTu = response.data;
                }                
                return response;
            } catch (e) {              
                return e;
            } finally {
                this.loading(false);
            }
        },

        async fetchCapNhatGiaoPhieuBH(_nhanVienThId, _phieuBhChaId) {
            // this.loading(true);
            let data = {
                nhanVienThId: this.$root.token.getNhanVienID(),
                phieuBhChaId: _phieuBhChaId
            };
            let response = await this.CapNhatGiaoPhieuBH(data);
            if (response.error_code === "BSS-00000000") {
                this.statusCapNhatGiaoPhieuBH = response.data;
            } else {
                this.$toast.error("Giao phiếu không thành công");
            }
            console.log(
                "fetchCapNhatGiaoPhieuBH",
                this.statusCapNhatGiaoPhieuBH
            );
            // this.loading(false);
        },

        async fetchDSKieuBaoHanh(_baoHanhId) {
            try{                
                this.loading(true);                
                let DSKieuBaoHanh = await this.getDSKieuBaoHanh(_baoHanhId); 
                return  DSKieuBaoHanh                
            }catch(e){
                this.$toast.error(e.data.message);
            }finally{
                this.loading(false);
            }
        },

        async fetchDSHuongGiao() {
            this.loading(true);
            this.loadHuongGiao = true;
            this.listHuongGiao = [];
            let data = {
                phanVungId: this.$root.token.getPhanVungID(),
                quyenHuongGiao: this.dkBang,
                nhomQtId: 4,
                quyTrinhId: this.quyTrinhSelected,
                loaiHdId: 0,
                dichVuVtId: 0
            };
            console.log("Hướng giao data truyền vào", data);
            try {
                let response = await this.getDSHuongGiao(data);
                if (response.error_code === "BSS-00000000") {
                    response.data.forEach(el => {
                        this.listHuongGiao.push({
                            text: el.HUONGGIAO,
                            value: el.HUONGGIAO_ID
                        });
                    });
                    if (response.data.length <= 0) {
                        this.listHuongGiao.push({
                            text: "Hiện tại không có hướng giao",
                            value: -1,
                            disabled: true
                        });
                    } else {
                        // this.huongGiaoSelected = this.listHuongGiao[0].value;
                    }
                    console.log(response.data);
                }
            } catch (e) {
                this.$toast.error(e.data.message)
            } finally {
                this.loading(false);
            }
        },

        // async fetchDSHuongGiao_qt(val) {
        //   this.loading(true);
        //   let data = {
        //     quyenHuongGiao: this.hg_idGetters,
        //     nhomQtId: 4,
        //     quyTrinhId: val,
        //     loaiHdId: 0,
        //     dichVuVtId: 0,
        //   };
        //   // console.log("testttt dataaaaaa", data);
        //   let response = await this.getDSHuongGiao(data);
        //   if (response.error_code === "BSS-00000000") {
        //     this.listHuongGiao = response.data;
        //   }
        //   this.loading(false);
        // },

        async fetchHoanThienBaoHanh(_baoHanhId, _ngayHt, _nhanVienId, _donViId) {
            try{
                this.loading(true);
                let data = {
                    baoHanhId: _baoHanhId,
                    ngayHt: _ngayHt,
                    nhanVienId: _nhanVienId,
                    donViId: _donViId
                };
                let response = await this.HoanThienBaoHanh(data);
                if (response.error_code === "BSS-00000000") {
                    this.statusHoanThienBaoHanh = response.data;
                }           
                
            }catch(e){
                this.$toast.error(e.data.message)
            }finally{
                this.loading(false);
            }
        },

        async fetchCapNhatDonViBH(
            _donViNhanId,
            _baoHanhId,
            _loaiDonViId,
            _kieuDonViId
        ) {
            this.loading(true);
            let data = {
                phanVungId: this.$root.token.getPhanVungID(),
                donViNhanId: this.donViNhanSelected,
                baoHanhId: _baoHanhId,
                loaiDonViId: _loaiDonViId,
                kieuDonViId: _kieuDonViId
            };
            let response = await this.CapNhatDonViBH(data);
            if (response.error_code === "BSS-00000000") {
                this.statusCapNhatDonViBH = response.data;
                // this.$toast.success("Cập nhật đơn vị thành công");
            } else {
                // this.$toast.error("Cập nhật đơn vị không thành công");
            }
            // console.log("statusCapNhatDonViBH", this.statusCapNhatDonViBH);
            this.loading(false);
        },

        async fetchTaoGiaoPhieuBaoHanh(val) {
            // this.loading(true);
            let data = {
                baoHanhId: val.BAOHANH_ID,
                ngayGiao: moment(new Date()).format("DD/MM/YYYY hh:mm:ss"),
                ndGiao: this.ndGiao,
                ghiChu: val.GHICHU,
                huongGiaoId: this.huongGiaoSelected,
                donViNhanId: val.DONVI_ID,
                donViGiaoId: this.$root.token.getDonViID(),
                nvGiaoId: this.$root.token.getNhanVienID(),
                nvTraId: val.NHANVIEN_ID,
                ttPhId: 1,
                phieuChaId: val.PHIEUBH_ID,
                ttBhId: val.TTBH_ID,
                nvThId: this.$root.token.getNhanVienID(),
                mayCn: "pc",
                ngayCn: moment(new Date()).format("DD/MM/YYYY hh:mm:ss"),
                nguoiCn: "bbPc",
                ipCn: "10.35.5.149"
            };
            try {
                let response = await this.TaoGiaoPhieuBaoHanh(data);
                console.log({ response });
                if (response.error_code === "BSS-00000000") {
                    this.statusHoanThienBaoHanh = response.data;
                }
            } catch (e) {
                this.$toast.error(e.data.message);
                throw e;
            }

            // console.log("statusHoanThienBaoHanh", response.error_code);
            // this.loading(false);
        },

        async giaoPhieuBtn() {
            // try {
                // this.loading(true)
                // let listDataSend = [];
                // let listKiemTra = []
                // for (let val of this.listGiaoPhieuBaoHanh) {
                //     if ( this.listItemChecked.includes(val.PHIEUBH_ID)) {
                //         if (val.KIEUBH_ID !== "3") { 
                //             listKiemTra.push({
                //                 serial: val.SERIAL,
                //                 donViNhanId: val.DONVI_ID,
                //                 donViId: val.DV_ID,
                //                 baoHanhId: val.BAOHANH_ID,
                //             })                                   
                //             // const res = await this.fetchKiemTraTaoChungTu(
                //             //     val.BAOHANH_ID,
                //             //     val.PHIEUBH_ID,
                //             //     val.SERIAL,
                //             //     val.DV_ID
                //             // );
                //             // console.log({ res });
                //             // if (res.error_code != "BSS-00000000") {
                //             //     throw res;
                //             // }
                //         }
                //         listDataSend.push(val);
                //     }
                // }                           
                // if(listKiemTra.length > 0) {
                //     var listcode = this.listLuongThaoTac.filter(a=> a.HUONGGIAO_ID == this.huongGiaoSelected)
                //     var code = listcode.length > 0 ? listcode[0].CODE : ""          
                //     let response = await this.getDSKiemTraTaoChungTu({
                //         phanVungId: this.$root.token.getPhanVungID(),
                //         code: code,
                //         listKiemTra: listKiemTra,
                //         chkHt: 1,
                //         khoGiaoId: this.khoSelected,
                //         thietBiId: 0, // BE tự lấy
                //         nhanVienId: this.$root.token.getNhanVienID()
                //     });   
                // }
                // console.log("listdatasend", listDataSend);
                // if (listDataSend.length > 0) {
                //     for (let val of listDataSend) {
                //         try {
                //             await this.fetchCapNhatGPBHHuongGiao(
                //                 this.huongGiaoSelected,
                //                 val.BAOHANH_ID
                //             );
                            
                //             await this.fetchTaoGiaoPhieuBaoHanh(val);

                //             await this.fetchCapNhatGiaoPhieuBH(
                //                 val.NHANVIEN_ID,
                //                 val.PHIEUBH_ID
                //             );
                           
                //             if(this.PHAILAM("BHTB_HTHS")){
                //                 let dt = await this.fetchDSKieuBaoHanh(val.BAOHANH_ID)
                //                 console.log(dt , " = dt")
                //                 if(dt.length > 0 && dt[0] > 0){
                //                     await this.fetchHoanThienBaoHanh(val.BAOHANH_ID, val.NGAYGIAO, this.$auth.getNhanVienID(), this.$auth.getDonViID() )
                //                 }
                //             }                            
                            
                //         } catch (e) {
                //             console.log(e);
                //             throw e;
                //         }
                //     }
                //     await this.fetchDSGiaoPhieuBaoHanh();
                //     this.$toast.success("Giao phiếu thành công");
                // } else if (this.listItemChecked.length == 0) {
                //     this.$toast.error("Bạn chưa chọn phiếu giao");
                // }
                // this.listDataSend = [];
            // } catch (e) {
            //     this.$toast.error(e.data.message);
            // }finally{
            //     this.loading(false)
            // }
            try{
                this.loading(true)
                if(this.listItemChecked.length == 0){
                    this.$toast.error("Bạn chưa chọn phiếu giao");
                    return
                }
                var listPhieuGiao = []             
                for(let e of this.listItemChecked){
                    listPhieuGiao.push({PHIEUBH_ID:e})
                }
                
                var dsPhieuDaGiao = await this.tongHopGiaoPhieuBaohanh({
                    huongGiaoId: this.huongGiaoSelected,
                    noiDungGiao: this.ndGiao,
                    khoGiaoId: this.khoSelected,
                    dsGiaoPhieu: listPhieuGiao
                })
                await this.fetchDSGiaoPhieuBaoHanh();
                this.$toast.success("Giao phiếu thành công");               
            } catch (e) {
                this.$toast.error(e.data.message);
            }finally{
                this.loading(false)
            }
        },

        btnHuyGiaoPhieu: async function() {
            try{
                this.loading(true);
                if (this.trangThaiSelected) {

                    if (this.listItemChecked.length == 0) {
                        this.$toast.error("Bạn chưa chọn phiếu giao");
                        return
                    }
                   
                    this.listItemChecked.forEach(element => {
                        this.listGiaoPhieuBaoHanh.forEach(val => {
                            if (element === val.PHIEUBH_ID) {
                                if (val.TTPH_ID === "4" || val.TTPH_ID === "2") {
                                    this.$toast.error(
                                        "Phiếu bảo hành serial " +
                                            val.SERIAL +
                                            " đang được xử lý. Không được hủy phiếu này"
                                    );
                                    return;
                                }
                                this.listDataSend.push(val);
                            }
                        });
                    });                    
                    
                    let DSHuyGiao = []
                    for( let e of this.listItemChecked){
                        DSHuyGiao.push({PHIEUBH_ID : e})
                    }
                   
                    let ketQua = await this.huyGiaoPhieu(DSHuyGiao)
                   
                    await this.fetchDSGiaoPhieuBaoHanh();
                    this.$toast.success("Hủy giao phiếu thành công");
                   
                    this.listItemChecked = [];
                    this.listDataSend = [];
                } else {
                    this.$toast.error(
                        "Bạn không thể xóa phiếu trạng thái 'Chưa giao'"
                    );
                }
               
            } catch (e) {
                this.$toast.error(e.data.message);
            }finally{
                this.loading(false)
            }
        },

        checkCbxKho() {
            if (this.khoSelected != null || this.khoSelected != "") {
                // if (this.PHAILAM("BHTB_GUI_BH_TTVT")) {
                this.listDonViNhan = [];
                if (this.listLoaiDichVuHuongGiao) {
                    this.fetchDSDonViNhan(this.listLoaiDichVuHuongGiao);
                }
                // }
            }
        },

        async capNhatDV() {
            this.listDataSend = [];
            if (this.trangThaiSelected == "1") {
                try {
                    this.listItemChecked.forEach(element => {
                        this.listGiaoPhieuBaoHanh.forEach(val => {
                            if (element === val.PHIEUBH_ID) {
                                this.listDataSend.push(val);
                            }
                        });
                    });
                    if (this.listDataSend.length == 0) {
                        this.$toast.error(
                            "Vui lòng chọn hàng phiếu muốn cập nhật"
                        );
                        return;
                    }
                    console.log("capNhatDV", this.listDataSend);
                    for (let val of this.listDataSend) {
                        await this.fetchCapNhatDonViBH(
                            val.DONVI_TN_ID,
                            val.BAOHANH_ID,
                            val.LOAIDV_ID,
                            val.KIEUDV_ID
                        );
                    }
                    await this.fetchDSGiaoPhieuBaoHanh();
                    this.$toast.success("Cập nhật đơn vị thành công");
                } catch (error) {
                    // console.log(error);
                    this.$toast.error("Cập nhật đơn vị không thành công");
                }
            } else {
                this.$toast.error(
                    "Bạn không thể cập nhật đơn vị phiếu trạng thái 'Chưa giao'"
                );
            }
        },

        exportExcel() {
            if (this.listItemChecked.length <= 0) {
                this.$root.toastError("Chưa có danh sách để xuất Excel!");
                return;
            } else {
                this.loading(true);
                const exportData = this.listGiaoPhieuBaoHanh.filter(item =>
                    this.listItemChecked.includes(item.PHIEUBH_ID)
                );
                try {
                    var dsach = xlsx.utils.json_to_sheet(exportData);
                    var wb = xlsx.utils.book_new();
                    xlsx.utils.book_append_sheet(wb, dsach, "DS");
                    xlsx.writeFile(wb, "GiaoPhieuBaoHanh.xlsx");
                } catch (error) {
                } finally {
                    this.loading(false);
                }
            }
        },
        async inPhieu(){
            // let test = await this.getParamsBaoCao("GNTB")
            // console.log(test , " = test")
            // return
            try{
                this.loading(true)
                let listDataSend = [];
                let itemCheck = null;
                let listBaoHanhId = []
                if (this.listItemChecked.length == 0) {
                    this.$root.toastError("Không có phiếu nào được chọn!");
                    return;
                }

                //lọc lấy item đã được check
                for (let element of this.listItemChecked) {
                    for (let val of this.listGiaoPhieuBaoHanh) {
                        if (element === val.PHIEUBH_ID) {
                            listDataSend.push(val);
                            listBaoHanhId.push(val.BAOHANH_ID)
                        }
                    }
                }

                const listDV = listDataSend
                    .map(item => item.DONVI_ID)
                    .filter((value, index, self) => self.indexOf(value) === index);
                console.log({ listDV });
                if (listDV.length > 1) {
                    this.$root.toastError(
                        "Bạn không thể chọn nhiều hơn 1 đơn vị nhận phiếu!"
                    );
                    return;
                }              

                console.log(listDataSend[0] , " = listDataSend[0]")
                let TreeDonVi = await this.getDSCayDonVi(this.$auth.getDonViID())
                if(TreeDonVi.length < 3){
                 this.$toast.error("Không lấy được thông tin đơn vị để in phiếu!")
                 return
                 }  
                const baseUrl = process.env.API;
                await this.axios({
                url:
                    `${baseUrl}/web-qlvt/api/in-phieu/getPhieuIn?extend=pdf`,
                method: "POST",
                responseType: "blob",
                data: {
                    ma_bc: "GNTB",
                    ds_tieuchi: [
                        { 
                            name: 'p_phanvung_id',
                            value: this.$auth.getPhanVungID()
                        },
                        {
                            name: 'baohanh_id',
                            value: listBaoHanhId.toString()
                        },
                        {
                            name: 'donvigiao',
                            value: TreeDonVi[0].tenDonVi
                        },
                        {
                            name: 'donvinhan',
                            value: listDataSend[0].DONVINHAN
                        },
                    
                    ]
                }
                        
                }).then((response) => {
                    previewPrint(response.data);
                });
                
            }catch(e){
                this.$toast.error(e.data.message);
            }finally{
                this.loading(false);
            }
        },
        // async inPhieu() {
        //     // this.$toast.error(
        //     //     "Chức năng in phiếu đang trong quá trình xây dựng!"
        //     // );
        //     let listDataSend = [];
        //     let itemCheck = null;
        //     if (this.listItemChecked.length == 0) {
        //         this.$root.toastError("Không có phiếu nào được chọn!");
        //         return;
        //     }

        //     //lọc lấy item đã được check
        //     for (let element of this.listItemChecked) {
        //         for (let val of this.listGiaoPhieuBaoHanh) {
        //             if (element === val.PHIEUBH_ID) {
        //                 listDataSend.push(val);
        //             }
        //         }
        //     }

        //     const listDV = listDataSend
        //         .map(item => item.DONVI_ID)
        //         .filter((value, index, self) => self.indexOf(value) === index);
        //     console.log({ listDV });
        //     if (listDV.length > 1) {
        //         this.$root.toastError(
        //             "Bạn không thể chọn nhiều hơn 1 đơn vị nhận phiếu!"
        //         );
        //         return;
        //     }
        //     this.selectedData = listDataSend[0];
        //     console.log(this.selectedData);
        //     let TreeDonVi = await this.getDSCayDonVi(this.$auth.getDonViID())
        //     console.log(TreeDonVi , " = TreeDonVi")
        //     if(TreeDonVi.length < 3){
        //         this.$toast.error("Không lấy được thông tin đơn vị để in phiếu!")
        //         return
        //     }            
        //     this.pdfgen(TreeDonVi);
        // },

        cboVatTuFiltering(e) {
            if (this.listKho.length) {
                var searchData = this.listKho;
                var query = new Query();
                query =
                e.text != ""
                    ? query.where("TEN_K_NEW", "contains", e.text, true)
                    : query;
                e.updateData(searchData, query);
            }
        },
    }
};
</script>
