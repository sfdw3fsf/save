
<template src='./DeliveryReceiveReceipt.html'></template>
<style scoped src='./DeliveryReceiveReceipt.scss'></style>
<script>
import Luotgiao from "./popup/Luotgiao.vue";
import api from "./api/index.js";
import collums from "./define/collums.js";
export default {
    components: {
        Luotgiao
    },
    data() {
        return {
            ...collums,
            header: {
                title: "Giao, nhận, trả biên lai thu tiền",
                list: [{
                        name: "Quản lý biên lai thu tiền",
                        link: {
                            name: "Ui.cards"
                        }
                    },
                    {
                        name: "Giao, nhận, trả biên lai thu tiền",
                        link: {
                            name: "Ui.buttons"
                        },
                    },
                ],
            },
            rowSelection: "multiple",
            model: {
                ma_luot_tao: "",
                blQuyen: "",
                tu_so: "",
                den_so: "",
                tong_so_bl: "",
                may_nhan: "",
                donvi_nhan: "",
                nguoi_nhan: "",
                nhanvien_giao: "",
                donvi_nhan_id: "",
            },
            dataSelected: {
                loaiphieu_id: 1,
                kho_giaonv_id: 0,
                kho_nhan_id: 0,
                loaibienlai_id: 0
            },
            isLoad: true,
            group_luot: "",
            vLoaiBL: "",
            vluottao_id: 0,
            loaibienlai_id: 0,
            vphieu_id: 0,
            vSo_bl: 0,
            loaiphieu_id: 1,
            vloaiphieu_id: 1,
            input_noidung: "",
            ds_KhoGiao: [],
            ds_KhoGiaoNV: [],
            ds_KhoNhan: [],
            listLuotGiaoID: [],
            listSoBienLai: [],
            listSoBL_ID: [],
            TongSoBLTheoLuot: 0,
            kho_cha_id: 0,
            ds_bienlai_duocchon: [],
            selIndex: [],
            json: {},
            kho_id: 0,
            //Biên lai hỏng
            selectionBienLaiHong: [],
            blShowTB: true,
            iTTBL_ID: 0,
            phieu_hong_id: [],
            dtListPhieuID: [],
            listLuotGiaoID: [],
            phieu_dasd: [],
            listSoBL_hong: [],
            listPhieu: [],
            listSoBL_hong_ID: [],
            listSoBL: [],
            Tu_So_LuotGiao: 0,
            Den_So_LuotGiao: 0,
            Luotgiao_id: 0,
            tu_so_update: 0,
            den_so_update: 0,
            isDisable_NhanBienlai: true,
            isDisable_BaohongBL: true,
            isDisable_Huygiao: true,
            isDisable_Chuyengiao: true,
            Disable_NhanBienlai: "",
            options: {
                ds_BienLaiMoi: [],
                ds_BienLaiDaGiaoKho: [],
                ds_BienLaiTrongKho: [],
                ds_Phieu_ID: [],
                ds_LuotgiaoBL: [],
                selectionTrongKho: [],
                selectionDaGiao: [],
            },
            dsLOAIPHIEU: [],
            tt: [],
            lstLoaiBL: {},
            donviId: this.$root.token.getDonViID(),
            nhanvienId: this.$root.token.getNhanVienID(),
        };
    },
    mounted() {
        this.init();
        this.rowSelection = "multiple";
    },
    computed: {
        kho_giao_id: {
            get() {
                return Number(this.model.kho_giao_id);
            },
            set(val) {
                if (val)
                    this.$set(this.model, "kho_giao_id", val);
            },
        },
        kho_giaonv_id: {
            get() {
                return Number(this.model.kho_giaonv_id);
            },
            set(val) {
                if (val)
                    this.$set(this.model, "kho_giaonv_id", val);
            },
        },
        kho_nhan_id: {
            get() {
                return Number(this.model.kho_nhan_id);
            },
            set(val) {
                if (val)
                    this.$set(this.model, "kho_nhan_id", val);
            },
        },
        loaibienlai_id: {
            get() {
                return Number(this.model.loaibienlai_id);
            },
            set(val) {
                if (val)
                    this.$set(this.model, "loaibienlai_id", val);
            },
        },
    },
    methods: {
        async init() {
            try {
                await this.getDSLoaiBienLai();
                await this.getDSKhoNhanVien();
                await this.getDSLoaiphieu();
            } catch (e) {
                this.$toast.error(e.data ? e.data.message : "Khởi tạo thất bại");
            }
        },
        //Get danh sách loại phiếu
        async getDSLoaiphieu() {
            this.dsLOAIPHIEU = [{
                "LOAIPHIEU_ID": 1,
                "LOAIPHIEU": "Giao"
            }, {
                "LOAIPHIEU_ID": 3,
                "LOAIPHIEU": "Trả"
            }];
            if (this.dsLOAIPHIEU.length > 0) {
                this.dataSelected.loaiphieu_id = this.dsLOAIPHIEU[0].LOAIPHIEU_ID
            } else {
                this.dataSelected.loaiphieu_id = 1
            }
        },
        //Get danh sách kho giao theo nhân viên 
        async getDSKhoNhanVien() {
            this.loading(true);
            try {
                await api.getDSKhoGiao_Nhanvien(this.axios, {
                    "nhanvien_id": this.nhanvienId
                }).then((res) => {
                    this.ds_KhoGiaoNV = res.data.data ? res.data.data : [];
                    this.kho_cha_id = this.ds_KhoGiaoNV[0]["kho_cha_id"];
                    this.kho_id = this.ds_KhoGiaoNV[0]["kho_id"]
                    this.model.donvi_nhan = this.ds_KhoGiaoNV[0]["donvi_id"]
                    this.model.nhanvien_giao = this.ds_KhoGiaoNV[0]["nhanvien_id"];
                    if (this.ds_KhoGiaoNV) {
                        this.dataSelected.kho_giaonv_id = this.ds_KhoGiaoNV[0]["kho_id"];
                    }
                   
                    this.getDSKhoNhan(this.kho_cha_id, this.kho_id)
                });
            } catch (err) {
                this.loading(false);
            }
            this.loading(false);
        },
        //Get danh sách kho nhận theo loại phiếu và kho giao
        async getDSKhoNhan(kho_cha_id, kho_id) {
           
            this.loading(true);
            try {
                  console.log({
                    "kho_cha_id": kho_cha_id,
                    "kho_id": kho_id,
                });
                await api.getDSKhoNhan(this.axios, {
                    "kho_cha_id": kho_cha_id,
                    "kho_id": kho_id,
                }).then((res) => {
                    console.log(res)
                    if (this.vloaiphieu_id == 3) {
                        this.ds_KhoNhan = res.data.data.tra ? res.data.data.tra : [];
                        this.model.donvi_nhan_id = this.ds_KhoNhan[0]["donvi_id"];
                        if (this.ds_KhoNhan) {
                            this.dataSelected.kho_nhan_id = this.ds_KhoNhan[0]["kho_id"];
                        }
                    } else {

                        this.ds_KhoNhan = res.data.data.giao ? res.data.data.giao : [];
                        this.model.donvi_nhan_id = this.ds_KhoNhan[0]["donvi_id"];
                        if (this.ds_KhoNhan) {
                            this.dataSelected.kho_nhan_id = this.ds_KhoNhan[0]["kho_id"];
                        }
                    }
                });
            } catch (err) {
                this.loading(false);
            }
            this.loading(false);
        },
        //Get danh sách loại biên lai
        async getDSLoaiBienLai() {
            this.loading(true);
            try {
                await api.getDSLoaiBienLai(this.axios, {

                }).then((res) => {
                    this.lstLoaiBL = res.data.data ? res.data.data : [];
                    if (this.lstLoaiBL) {
                        this.dataSelected.loaibienlai_id = this.lstLoaiBL[0]["loaibienlai_id"];
                    }
                });
            } catch (err) {
                this.loading(false);
            }
            this.loading(false);
        },
        //Get danh sách biên lai mới chưa nhận
        async getDSBienLaiMoi() {
            this.loading(true);
            try {
                await api.getDSBienLaiMoi(this.axios, {
                    "loai_bl_id": this.loaibienlai_id,
                    "kieu_id": 4,
                    "total_row": 1,
                    "page_num": 1,
                    "kho_id": this.dataSelected.kho_giaonv_id,
                    "page_sz": 200
                }).then((res) => {
                     console.log(11111111)
                    console.log(res)
                    this.options.ds_BienLaiMoi = res.data.data.out ? res.data.data.out : [];
                });
            } catch (err) {
                this.loading(false);
            }
            this.loading(false);
        },
        //Get danh sách biên lai 
        async getDSBienLaiDaGiao() {
            this.loading(true);
            try {
                await api.getDSBienLaiDaGiao(this.axios, {
                    "loai_bl_id": this.loaibienlai_id,
                    "kieu_id": 2,
                    "total_row": 1,
                    "page_num": 1,
                    "kho_id": this.dataSelected.kho_giaonv_id,
                    "page_sz": 100
                }).then((res) => {
                    this.options.ds_BienLaiDaGiaoKho = res.data.data.out ? res.data.data.out : [];
                    var grid = this.$refs.BLDaGiao.grid;
                    this.options.ds_BienLaiDaGiaoKho.map((x) => {

                        this.selIndex.push(parseInt(x.stt))
                    });
                    grid.selectRows(this.selIndex);
                });
            } catch (err) {
                this.loading(false);
            }
            this.loading(false);
        },
        griddataGridCellCreated:  function(args){
            let ttbl_id = args.data.ttbl_id
            if(ttbl_id ==3){
                $(args.cell).css({background: 
                        'linear-gradient(to top, #FF002A73,#FF002A4F,#FF002A73)'
                })
            }
           
        },
        //Lấy danh sách biên lai trong kho
        async getDSBienLaiTrongKho() {
            this.loading(true);
            try {
                await api.getDSBienLaiTrongKho(this.axios, {
                    "loai_bl_id": this.loaibienlai_id,
                    "kieu_id": 1,
                    "total_row": 1,
                    "page_num": 1,
                    "kho_id": this.dataSelected.kho_giaonv_id,
                    "page_sz": 100
                }).then((res) => {
                    this.options.ds_BienLaiTrongKho = res.data.data.out ? res.data.data.out : [];
                });
            } catch (err) {
                this.loading(false);
            }
            this.loading(false);
        },
        //Get danh sách biên lai đã trả
        async getDSBienLaiDaTra() {
            this.loading(true);
            try {
                await api.getDSBienLaiDaTra(this.axios, {
                    "loai_bl_id": this.loaibienlai_id,
                    "kieu_id": 3,
                    "total_row": 1,
                    "page_num": 1,
                    "kho_id": this.dataSelected.kho_giaonv_id,
                    "page_sz": 100
                }).then((res) => {
                    this.options.ds_BienLaiDaGiaoKho = res.data.data.out ? res.data.data.out : [];
                });
            } catch (err) {
                console.log(err);
            }
            this.loading(false);
        },
        //Click nút tải dữ liệu
        async tsbtnTaiDuLieu_Click() {
            try {
                await this.getDSBienLaiMoi();
                if (this.vloaiphieu_id == 1) {
                    await this.getDSBienLaiTrongKho();
                    await this.getDSBienLaiDaGiao();
                } else {
                    await this.getDSBienLaiDaTra();
                    await this.getDSBienLaiTrongKho();
                }
            } catch (err) {
                console.log(err);
            }
        },
        /*===========Start nhận biên lai==========*/
        async tsbtnNhanBienLai_Click() {
            //Nếu như kho nhận chưa được chọn
            if (isNaN(this.dataSelected.kho_nhan_id)) {
                this.$toast.warning("Bạn phải chọn kho nhận biên lai!");
            } else {
                let result = {};
                //Nếu không có phiếu nào được chọn thì báo lỗi
                if (this.options.ds_Phieu_ID.length > 0) {
                    try {
                        //Lấy tổng số biên lai theo lượt giao 
                        this.options.ds_LuotgiaoBL.forEach((item) => {
                            
                            if (!this.listLuotGiaoID.includes(item.luotgiaobl_id)) {
                                this.listLuotGiaoID.push(item.luotgiaobl_id);
                                this.model.tu_so = item.tu_so;
                                this.model.den_so = item.den_so;
                                this.TongSoBLTheoLuot += this.model.den_so - this.model.tu_so + 1;
                            }
                        });
                        //Kiểm tra tổng số biên lai theo lượt giao có bằng với số biên lai đã chọn
                        if (this.TongSoBLTheoLuot != this.options.ds_LuotgiaoBL.length) {
                            this.$toast.warning("Bạn phải chọn các biên lai theo lượt giao để nhận.Vui lòng chọn theo lượt giao để nhận !");
                        } else {
                            await api.Nhan_Bien_Lai(this.axios, {
                                "nd_nhan": "Đã nhận",
                                "nhanvien_nhan_id": this.nhanvienId,
                                "ttph_id": 4,
                                "data": "[" + this.options.ds_Phieu_ID + "]",
                                "may_nhan": this.model.may_nhan,
                                "loaiphieu_id": 1,
                                "kho_nhan_id": this.kho_id,
                                "donvi_nhan_id": this.model.donvi_nhan,
                                "nguoi_nhan": this.model.nguoi_nhan
                            }).then((res) => {
                                result = res;
                                this.$toast.success("Nhận biên lai thành công!");
                                this.getDSBienLaiMoi();
                                this.getDSBienLaiTrongKho();
                                this.options.ds_Phieu_ID = [];
                                this.listSoBL = [];
                                this.listSoBL_ID = [];
                                this.options.ds_LuotgiaoBL = [];
                                this.listLuotGiaoID=[];
                                this.model={}
                            });
                            return result;
                        }
                    } catch (err) {
                        this.$toast.error("Nhận biên lai lỗi:" + err.data.message);
                    }
                } else {
                    this.$toast.warning("Chưa có phiếu nào được chọn để nhận!");
                }
            }
        },
        //Lấy giá trị từ dòng được chọn
        setValueFromRowSelect(row) {
            this.vluottao_id = row.luottao_id;
            this.vphieu_id = row.phieu_id;
            this.model.ma_luot_tao = row.ma_luottao != undefined ? row.ma_luottao : "";
            this.model.tu_so = row.tu_so;
            this.model.blQuyen = row.tong_so;
            this.model.den_so = row.den_so;
            this.model.tong_so_bl = row.tong_so;
            this.model.donvi_nhan_id = row.tong_so;
            this.model.may_nhan = row.may_cn;
            this.model.nguoi_nhan = row.nguoi_cn;
            this.group_luot = row.group_luot;
            this.vLoaiBL = row.loaibienlai_id;
            this.vSo_bl = row.so_bl;
            this.isLoad = false;
            this.loaibienlai_id = this.vLoaiBL;
            this.isLoad = true;
        },
        //Khi click dòng trong grid danh sách bi3n lai mới
        async gridBLMoi_FocusedRowChanged(data) {
            try {
                if(data.isHeaderCheckboxClicked){
                    data.data.forEach((item) => {
                         this.options.ds_Phieu_ID.push(item.phieu_id);
                    });
                    this.options.ds_LuotgiaoBL = data.data ;
                    this.setValueFromRowSelect(data.data[0]);
                    this.isDisable_NhanBienlai = false;
                }else{
                    if (data) {
                        let row = data.data;
                        this.setValueFromRowSelect(row);
                        this.options.ds_Phieu_ID.push(row.phieu_id);
                        this.options.ds_LuotgiaoBL.push(row);
                        this.isDisable_NhanBienlai = false;
                    }
                }
            } catch (err) {
                console.log(err);
            }
        },
        //Khi bỏ click trong grid danh sách bi3n lai mới
        async gridBLMoi_RowDeselected(row) {
            try {
                this.options.ds_LuotgiaoBL.splice(this.options.ds_LuotgiaoBL.indexOf(row.data), 1);
                this.options.ds_Phieu_ID.splice(this.options.ds_Phieu_ID.indexOf(row.data), 1);
            } catch (err) {
                console.log(err);
            }
        },
        /*===========End nhận biên lai==========*/
        /*===========Start Đánh dấu biên lai hỏng ==========*/
        //Function báo hỏng biên lai
        async fnbaohongbienlai() {
            try {
                this.json = {
                    "data": "[" + this.phieu_hong_id + "]",
                    "donvi_id": this.model.donvi_nhan,
                    "nhanvien_id": this.nhanvienId
                }
                await api.Danh_Dau_Bien_Lai_Hong(this.axios, {
                    "data": "[" + this.phieu_hong_id + "]",
                    "donvi_id": this.model.donvi_nhan,
                    "nhanvien_id": this.nhanvienId
                }).then((res) => {
                    this.loading(false)
                    this.$root.$toast.success('Đánh dấu biên lai hỏng thành công!')
                    this.getDSBienLaiTrongKho();
                    this.selectionBienLaiHong = [];
                    this.phieu_dasd = [];
                    this.phieu_hong_id = [];
                    this.options.selectionTrongKho = [];
                });
            } catch (err) {
                this.$toast.error("Lỗi không thể thực hiện: " + err.data.message);
            }
        },
        //Action đánh dấu báo hỏng biên lai
        async tsbtnDanhDauBLHong_Click() {
            try {

                if (this.options.selectionTrongKho.length > 0) {
                    this.options.selectionTrongKho.forEach((item) => {
                        this.iTTBL_ID = item.ttbl_id == null ? 0 : item.ttbl_id;
                        if (this.iTTBL_ID > 0 && this.iTTBL_ID == 1) {
                            this.phieu_hong_id.push(item.phieu_id);
                        } else if (this.iTTBL_ID > 0 && this.iTTBL_ID == 2) {
                            this.phieu_dasd.push(item.phieu_id);
                        }
                    });
                    if (this.phieu_dasd.length > 0) {
                        this.$toast.warning("Chức năng đánh dấu biên lai hỏng chỉ áp dụng với trạng thái biên lai chưa sử dụng. Vui lòng kiểm tra lại !");
                        this.phieu_dasd = [];
                        this.phieu_hong_id = [];
                    } else {
                        if (this.phieu_hong_id.length > 0 && this.phieu_dasd.length == 0) {
                            this.$confirm(`Bạn có chắc chắn đồng ý đánh dấu hỏng với danh sách biên lai được chọn này không? ?`,
                                'Đánh dấu biên lai hỏng', {
                                    confirmButtonText: 'Có',
                                    cancelButtonText: 'Không',
                                    type: 'warning'
                                }).then(() => {
                                this.fnbaohongbienlai();
                            });
                        }
                    }
                } else {
                    this.$toast.warning("Chưa có phiếu biên lai nào được chọn. Bạn vui lòng chọn biên lai trong kho để đánh dấu hỏng !");
                }
            } catch (err) {

                this.$toast.error("Lỗi không thể thực hiện: " + err.data.message);
            }
        },
        //Fn giao biên lai trong kho
        async fnchuyengiaokho() {
            try {
                await api.Chuyen_Giao_Kho(this.axios, {
                    "nhanvien_nhan_id": 0,
                    "ttph_id": 2,
                    "tu_so": 401,
                    "data": "[" + this.phieu_hong_id + "]",
                    "nd_giao": this.input_noidung.length == 0 ? "Giao phiếu" : this.input_noidung,
                    "may_giao": this.model.may_nhan,
                    "den_so": 401,
                    "type": 1,
                    "nhanvien_giao_id": this.model.nhanvien_giao,
                    "donvi_giao_id": this.model.donvi_nhan,
                    "donvi_nhan_id": this.model.donvi_nhan_id,
                    "loai_phieu_id": 2,
                    "nguoi_giao": this.model.nguoi_nhan,
                    "kho_giao_id": this.dataSelected.kho_giaonv_id,
                    "kho_nhan_id": this.dataSelected.kho_nhan_id,
                    "ttph_id_insert": 1,
                    "nguoi_nhan": this.model.nguoi_nhan,
                }).then((res) => {
                    console.log(res);
                    this.loading(false)
                    this.$root.$toast.success('Giao biên lai thành công!')
                    this.getDSBienLaiTrongKho();
                    this.getDSBienLaiDaGiao();
                    this.selectionBienLaiHong = [];
                    this.phieu_dasd = [];
                    this.phieu_hong_id = [];
                    this.options.selectionTrongKho = [];
                    this.input_noidung = "";
                });
            } catch (err) {
                this.$toast.error("Lỗi không thể thực hiện: " + err.data.message);
            }
        },
        //Action giao biên lại trong kho
        async tsbtnChuyenGiaoKho_Click() {
            try {

                if (this.input_noidung.length == 0) {
                    this.$toast.warning("Vui lòng nhập nội dung giao!");
                } else {
                    if (this.options.selectionTrongKho.length > 0) {
                        this.options.selectionTrongKho.forEach((item) => {
                            this.iTTBL_ID = item.ttbl_id == null ? 0 : item.ttbl_id;
                            if (this.iTTBL_ID > 0 && this.iTTBL_ID == 1) {
                                this.phieu_hong_id.push(item.phieu_id);
                            } else if ((this.iTTBL_ID > 0 && this.iTTBL_ID == 2) || (this.iTTBL_ID > 0 && this.iTTBL_ID == 3)) {
                                this.phieu_dasd.push(item.phieu_id);
                            }
                        });
                        if (this.phieu_dasd.length > 0) {
                            this.$toast.warning("Chức năng giao phiếu biên lai chỉ áp dụng với trạng thái biên lai chưa sử dụng. Vui lòng kiểm tra lại !");
                            this.phieu_dasd = [];
                            this.phieu_hong_id = [];
                        } else {
                            if (this.phieu_hong_id.length > 0 && this.phieu_dasd.length == 0) {
                                this.$confirm(`Bạn có đồng ý giao đi những phiếu đã được chọn này không?`,
                                    'Chuyển giao kho', {
                                        confirmButtonText: 'Có',
                                        cancelButtonText: 'Không',
                                        type: 'warning'
                                    }).then(() => {
                                    this.fnchuyengiaokho();
                                });
                            }
                        }
                    } else {
                        this.$toast.warning("Chưa có phiếu biên lai nào được chọn. Bạn vui lòng chọn biên lai trong kho để đánh dấu hỏng !");
                    }
                }
            } catch (err) {

                this.$toast.error("Lỗi không thể thực hiện: " + err.data.message);
            }
        },
        //Fn trả biên lai trong kho
        async fnTraBienLaiTrongKho() {
            try {
                await api.Tra_Bien_Lai(this.axios, {
                    "nhanvien_nhan_id": 974,
                    "ttph_id": 2,
                    "tu_so": 401,
                    "data": "[" + this.phieu_hong_id + "]",
                    "nd_giao": this.input_noidung.length == 0 ? "Giao phiếu" : this.input_noidung,
                    "may_giao": this.model.may_nhan,
                    "den_so": 401,
                    "type": 1,
                    "nhanvien_giao_id": this.model.nhanvien_giao,
                    "donvi_giao_id": this.model.donvi_nhan,
                    "donvi_nhan_id": this.model.donvi_nhan_id,
                    "loai_phieu_id": 2,
                    "nguoi_giao": this.model.nguoi_nhan,
                    "kho_giao_id": this.dataSelected.kho_giaonv_id,
                    "kho_nhan_id": this.dataSelected.kho_nhan_id,
                    "ttph_id_insert": 1,
                    "nguoi_nhan": this.model.nguoi_nhan,
                }).then((res) => {
                    console.log(res);
                    this.loading(false)
                    this.$root.$toast.success('Trả biên lai thành công!')
                    this.getDSBienLaiTrongKho();
                    this.getDSBienLaiDaTra();
                    this.selectionBienLaiHong = [];
                    this.phieu_dasd = [];
                    this.phieu_hong_id = [];
                    this.options.selectionTrongKho = [];
                    this.input_noidung = "";
                });
            } catch (err) {
                this.$toast.error("Lỗi không thể thực hiện: " + err.data.message);
            }
        },
        //Trả biên lai trong kho
        async tsbtnTraBienLaiTrongKho_Click() {
            try {
                if (this.input_noidung.length == 0) {
                    this.$toast.warning("Vui lòng nhập nội dung trả!");
                } else {
                    if (this.options.selectionTrongKho.length > 0) {
                        this.options.selectionTrongKho.forEach((item) => {
                            this.iTTBL_ID = item.ttbl_id == null ? 0 : item.ttbl_id;
                            if (this.iTTBL_ID > 0 && this.iTTBL_ID == 1) {
                                this.phieu_hong_id.push(item.phieu_id);
                            } else if ((this.iTTBL_ID > 0 && this.iTTBL_ID == 2) || (this.iTTBL_ID > 0 && this.iTTBL_ID == 3)) {
                                this.phieu_dasd.push(item.phieu_id);
                            }
                        });

                        this.$confirm(`Bạn có đồng ý trả những phiếu đã được chọn này không?`,
                            'Trả biên lại trong kho', {
                                confirmButtonText: 'Có',
                                cancelButtonText: 'Không',
                                type: 'warning'
                            }).then(() => {
                            this.fnTraBienLaiTrongKho();
                        });

                    } else {
                        this.$toast.warning("Chưa có phiếu biên lai nào được chọn. Bạn vui lòng chọn biên lai trong kho để đánh dấu hỏng !");
                    }
                }
            } catch (err) {

                this.$toast.error("Lỗi không thể thực hiện: " + err.data.message);
            }
        },
        //Lấy giá trị từ dòng được chọn trong kho
        setValueFromRowSelectBLTrongKho(row) {
            this.vluottao_id = row.luottao_id;
            this.vphieu_id = row.phieu_id;
            this.model.ma_luot_tao = row.ma_luottao != undefined ? row.ma_luottao : "";
            this.model.tu_so = row.tu_so;
            this.model.blQuyen = row.tong_so;
            this.model.den_so = row.den_so;
            this.model.tong_so_bl = row.tong_so;
            this.model.donvi_nhan_id = row.tong_so;
            this.model.may_nhan = row.may_cn;
            this.model.nguoi_nhan = row.nguoi_cn;
            this.group_luot = row.group_luot;
            this.vLoaiBL = row.loaibienlai_id;
            this.vSo_bl = row.so_bl;
            this.isLoad = false;
            this.loaibienlai_id = this.vLoaiBL;
            this.isLoad = true;
            this.options.selectionTrongKho.push(row);
            this.isDisable_BaohongBL = false;
            console.log(this.options.selectionTrongKho);
        },
        //Click chọn row ở biên lai trong kho
        async gridBLTrongKho_FocusedRowChanged(data) {
            try {
                if (data) {
                    let row = data.data;
                    this.setValueFromRowSelectBLTrongKho(row);
                }
            } catch (err) {
                console.log(err);
            }
        },
        //Click bỏ chọn biên lai trong kho
        async gridBLTrongKho_RowDeselected(data) {
            try {
                this.options.selectionTrongKho.splice(this.options.selectionTrongKho.indexOf(data.data), 1);
            } catch (err) {
                console.log(err);
            }
        },
        async fnHuyGiaoBLTrongKho() {
            try {
                await api.Huy_Giao_Kho(this.axios, {
                    "den_so_update": this.den_so_update,
                    "data": "[" + this.listPhieu + "]",
                    "luotgiao_id": this.Luotgiao_id,
                    "tu_so_update": this.tu_so_update
                }).then((res) => {
                    this.loading(false)
                    this.$root.$toast.success('Hủy giao thành công!')
                    this.getDSBienLaiTrongKho();
                    this.getDSBienLaiDaGiao();
                    this.selectionBienLaiHong = [];
                    this.phieu_dasd = [];
                    this.phieu_hong_id = [];
                    this.options.selectionTrongKho = [];
                });
            } catch (err) {
                this.$toast.error("Lỗi không thể thực hiện: " + err.data.message);
            }
        },
        /*===========Hủy chuyển kho ======================*/
        async fnhuygiaokho() {
            try {
                if (this.options.selectionDaGiao.length < 1) {
                    this.$toast.warning("Chưa có phiếu biên lai nào được chọn. Vui lòng chọn danh sách phiếu biên lai để thực hiện!");
                } else {
                    this.options.selectionDaGiao.forEach((item) => {
                        this.iTTBL_ID = this.iTTBL_ID = item.ttbl_id == null ? 0 : item.ttbl_id;
                        this.listSoBL.push(item.so_bl);
                        this.listPhieu.push(item.phieu_id);
                        if (!this.listLuotGiaoID.includes(item.luotgiaobl_id)) {
                            this.listLuotGiaoID.push(item.luotgiaobl_id);
                        }
                    });
                    this.Tu_So_LuotGiao = this.options.selectionDaGiao[0].tu_so;
                    this.Den_So_LuotGiao = this.options.selectionDaGiao[0].den_so;
                    this.Luotgiao_id = this.options.selectionDaGiao[0].luotgiaobl_id;
                    if ((Math.max.apply(Math, this.listSoBL) - Math.min.apply(Math, this.listSoBL) + 1) != this.options.selectionDaGiao.length) {
                        this.$toast.warning("Bạn phải chọn dải biên lai liên tục và biên lai phải thuộc LƯỢT GIAO SỐ :  " + this.Luotgiao_id + " để thao tác. Vui lòng kiểm tra lại danh sách biên lai được chọn!");
                    } else {
                        if (Math.min.apply(Math, this.listSoBL) != this.Tu_So_LuotGiao && Math.max.apply(Math, this.listSoBL) != this.Den_So_LuotGiao) {
                            this.$toast.warning("Chỉ có thể hủy giao với biên lai từ " + this.Tu_So_LuotGiao + " đến " + this.Den_So_LuotGiao + " của cùng LƯỢT GIAO SỐ :  " + this.Luotgiao_id + " . Vui lòng kiểm tra lại danh sách được chọn!");
                        } else if (Math.min.apply(Math, this.listSoBL) == this.Tu_So_LuotGiao && Math.max.apply(Math, this.listSoBL) > this.Den_So_LuotGiao) {
                            this.$toast.warning("Chỉ có thể hủy giao với biên lai từ " + this.Tu_So_LuotGiao + " đến " + this.Den_So_LuotGiao + " của cùng LƯỢT GIAO SỐ :  " + this.Luotgiao_id + " . Vui lòng kiểm tra lại danh sách được chọn !");
                        } else if (Math.max.apply(Math, this.listSoBL) == this.Den_So_LuotGiao && Math.min.apply(Math, this.listSoBL) < this.Tu_So_LuotGiao) {
                            this.$toast.warning("Chỉ có thể hủy giao với biên lai từ " + this.Tu_So_LuotGiao + " đến " + this.Den_So_LuotGiao + " của cùng LƯỢT GIAO SỐ :  " + this.Luotgiao_id + " . Vui lòng kiểm tra lại danh sách được chọn !");
                        } else {
                            if (Math.min.apply(Math, this.listSoBL) == this.Tu_So_LuotGiao && Math.max.apply(Math, this.listSoBL) == this.Den_So_LuotGiao) {
                                // Xóa lượt giao
                                this.tu_so_update = 0;
                            } else if (Math.min.apply(Math, this.listSoBL) == this.Tu_So_LuotGiao && Math.max.apply(Math, this.listSoBL) < this.Den_So_LuotGiao) {
                                // không xóa chỉ cập nhật lượt giao
                                this.tu_so_update = Math.max.apply(Math, this.listSoBL) + 1;
                                this.den_so_update = this.Den_So_LuotGiao;
                            } else if (Math.max.apply(Math, this.listSoBL) == this.Den_So_LuotGiao && Math.min.apply(Math, this.listSoBL) > this.Tu_So_LuotGiao) {
                                this.tu_so_update = this.Tu_So_LuotGiao;
                                this.den_so_update = Math.min.apply(Math, this.listSoBL) - 1;
                            }
                            this.fnHuyGiaoBLTrongKho();
                        }
                    }
                }
            } catch (err) {
                this.$toast.error("Lỗi không thể thực hiện: " + err.toString());
            }
        },
        //Click nút hủy giao kho
        async tsbtnHuyGiaoKho_Click() {
            try {
                if (this.options.selectionDaGiao.length > 0) {
                    this.$confirm(`Bạn có đồng ý thu hồi lại những phiếu đã giao này không??`,
                        'Hủy giao kho', {
                            confirmButtonText: 'Có',
                            cancelButtonText: 'Không',
                            type: 'warning'
                        }).then(() => {
                        this.fnhuygiaokho();
                    });
                } else {
                    this.$toast.warning("Chưa có phiếu biên lai nào được chọn. Vui lòng chọn danh sách phiếu biên lai để thực hiện!");
                }
            } catch (err) {

                this.$toast.error("Lỗi không thể thực hiện: " + err.data.message);
            }
        },
        //Lấy giá trị từ dòng được chọn từ biên lai đã giao kho
        setValueFromRowSelectBLDaGiao(row) {
            this.vluottao_id = row.luottao_id;
            this.vphieu_id = row.phieu_id;
            this.model.ma_luot_tao = row.ma_luottao != undefined ? row.ma_luottao : "";
            this.model.tu_so = row.tu_so;
            this.model.blQuyen = row.tong_so;
            this.model.den_so = row.den_so;
            this.model.tong_so_bl = row.tong_so;
            this.model.donvi_nhan_id = row.tong_so;
            this.model.may_nhan = row.may_cn;
            this.model.nguoi_nhan = row.nguoi_cn;
            this.group_luot = row.group_luot;
            this.vLoaiBL = row.loaibienlai_id;
            this.vSo_bl = row.so_bl;
            this.isLoad = false;
            this.loaibienlai_id = this.vLoaiBL;
            this.isLoad = true;
            this.options.selectionDaGiao.push(row);
        },
        //Click chọn biên lai đã giao kho
        async gridBLDaGiao_FocusedRowChanged(data) {
            try {
                if (data) {
                    let row = data.data;
                    this.setValueFromRowSelectBLDaGiao(row);
                }
            } catch (err) {
                console.log(err);
            }
        },
        //Bỏ chọn biên lai đã giao kho
        async gridBLDaGiao_RowDeselected(data) {
            try {
                this.options.selectionDaGiao.splice(this.options.selectionDaGiao.indexOf(data.data), 1);
            } catch (err) {
                console.log(err);
            }
        },
        /*===========trả phiếu đã giao kho ======================*/
        async fnTraPhieuTrongKho() {
            try {
                await api.Huy_Tra_Kho(this.axios, {
                    "den_so_update": this.den_so_update,
                    "data": "[" + this.listPhieu + "]",
                    "luotgiao_id": this.Luotgiao_id,
                    "tu_so_update": this.tu_so_update,
                }).then((res) => {
                    this.loading(false)
                    this.$root.$toast.success('Hủy trả thành công!')
                    this.getDSBienLaiTrongKho();
                    this.getDSBienLaiDaTra();
                    this.selectionBienLaiHong = [];
                    this.phieu_dasd = [];
                    this.phieu_hong_id = [];
                    this.options.selectionTrongKho = [];
                    this.listSoBL = [];
                });
            } catch (err) {
                this.$toast.error("Lỗi không thể thực hiện: " + err.data.message);
            }
        },
        async Previous_Tra() {
            try {
                this.options.selectionDaGiao.forEach((item) => {
                    this.iTTBL_ID = this.iTTBL_ID = item.ttbl_id == null ? 0 : item.ttbl_id;

                    this.listSoBL.push(item.so_bl);
                    this.listPhieu.push(item.phieu_id);
                    if (!this.listLuotGiaoID.includes(item.luotgiaobl_id)) {
                        this.listLuotGiaoID.push(item.luotgiaobl_id);
                    }
                });
                this.Tu_So_LuotGiao = this.options.selectionDaGiao[0].tu_so;
                this.Den_So_LuotGiao = this.options.selectionDaGiao[0].den_so;
                this.Luotgiao_id = this.options.selectionDaGiao[0].luotgiaobl_id;
                if ((Math.max.apply(Math, this.listSoBL) - Math.min.apply(Math, this.listSoBL) + 1) != this.options.selectionDaGiao.length) {
                    this.$toast.warning("Bạn phải chọn dải biên lai liên tục và biên lai phải thuộc LƯỢT GIAO SỐ :  " + this.Luotgiao_id + " để thao tác. Vui lòng kiểm tra lại danh sách biên lai được chọn!");
                } else {
                    if (Math.min.apply(Math, this.listSoBL) != this.Tu_So_LuotGiao && Math.max.apply(Math, this.listSoBL) != this.Den_So_LuotGiao) {
                        this.$toast.warning("Chỉ có thể hủy trả với biên lai từ " + this.Tu_So_LuotGiao + " đến " + this.Den_So_LuotGiao + " của cùng LƯỢT GIAO SỐ :  " + this.Luotgiao_id + " . Vui lòng kiểm tra lại danh sách được chọn!");

                    } else if (Math.min.apply(Math, this.listSoBL) == this.Tu_So_LuotGiao && Math.max.apply(Math, this.listSoBL) > this.Den_So_LuotGiao) {
                        this.$toast.warning("Chỉ có thể hủy trả với biên lai từ " + this.Tu_So_LuotGiao + " đến " + this.Den_So_LuotGiao + " của cùng LƯỢT GIAO SỐ :  " + this.Luotgiao_id + " . Vui lòng kiểm tra lại danh sách được chọn !");
                    } else if (Math.max.apply(Math, this.listSoBL) == this.Den_So_LuotGiao && Math.min.apply(Math, this.listSoBL) < this.Tu_So_LuotGiao) {
                        this.$toast.warning("Chỉ có thể hủy trả với biên lai từ " + this.Tu_So_LuotGiao + " đến " + this.Den_So_LuotGiao + " của cùng LƯỢT GIAO SỐ :  " + this.Luotgiao_id + " . Vui lòng kiểm tra lại danh sách được chọn !");
                    } else {
                        if (Math.min.apply(Math, this.listSoBL) == this.Tu_So_LuotGiao && Math.max.apply(Math, this.listSoBL) == this.Den_So_LuotGiao) {
                            // Xóa lượt giao
                            this.tu_so_update = 0;
                        } else if (Math.min.apply(Math, this.listSoBL) == this.Tu_So_LuotGiao && Math.max.apply(Math, this.listSoBL) < this.Den_So_LuotGiao) {
                            // không xóa chỉ cập nhật lượt giao
                            this.tu_so_update = Math.max.apply(Math, this.listSoBL) + 1;
                            this.den_so_update = this.Den_So_LuotGiao;
                        } else if (Math.max.apply(Math, this.listSoBL) == this.Den_So_LuotGiao && Math.min.apply(Math, this.listSoBL) > this.Tu_So_LuotGiao) {
                            this.tu_so_update = this.Tu_So_LuotGiao;
                            this.den_so_update = Math.min.apply(Math, this.listSoBL) - 1;
                        }
                        this.fnTraPhieuTrongKho();
                    }
                }

            } catch (err) {
                this.$toast.error("Lỗi không thể thực hiện: " + err.toString());
            }
        },
        async btnPreviousTraPhieu_Click() {
            try {
                if (this.options.selectionDaGiao.length > 0) {
                    this.$confirm(`Bạn có đồng ý thu hồi lại những phiếu đã trả này không?`,
                        'Thu hồi phiếu đã trả', {
                            confirmButtonText: 'Có',
                            cancelButtonText: 'Không',
                            type: 'warning'
                        }).then(() => {
                        this.Previous_Tra();
                    });
                } else {
                    this.$toast.warning("Chưa có phiếu biên lai nào được chọn. Vui lòng chọn danh sách phiếu biên lai để thực hiện!");
                }
            } catch (err) {

                this.$toast.error("Lỗi không thể thực hiện: " + err.data.message);
            }
        },
        /*===========End Đánh dấu biên lai hỏng ==========*/
        onChangeLoaiPhieu: async function () {
            this.vloaiphieu_id = this.dataSelected.loaiphieu_id;
            this.getDSKhoNhanVien();
        },
        onChangeKhoGiao: async function () {
            this.kho_cha_id = this.ds_KhoGiaoNV.filter(kho => kho.kho_id == this.dataSelected.kho_giaonv_id)[0].kho_cha_id;
            this.getDSKhoNhan(this.kho_cha_id, this.dataSelected.kho_giaonv_id);
        },
        onChangeKhoNhan: async function () {
            this.kho_nhan_id = this.dataSelected.kho_nhan_id;
        },
        onChangeLoaiBL: async function () {
            this.loaibienlai_id = this.dataSelected.loaibienlai_id;
        },
    },
    watch: {}
};
</script>
