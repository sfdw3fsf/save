<template src="./index.html"></template>
<style src="./style.scss" scoped></style>
<script>
export default {
    name: "TTLienHe",
    components: {},
    // props: ["khachHangId", "thanhToanId", "thueBaoId", "kieu"],
    props: ["thanhToanId", "thueBaoId", "kieu"],
    data: function() {
        return {
            target: ".main-wrapper",
            animationSettings: { effect: "Zoom" },
            khachHangId: "",
            dsTLienHe: [],
            dsLHThanhToan: [],
            dsLHThueBao: [],
            dsTLHKH: [],
            dsMDChuaGan: [],
            dsMDDaGan: [],
            form: {
                tenLh: "",
                diaChi: "",
                dienThoai: "",
                fax: "",
                email: "",
                donVi: "",
                ttlhId: "",
                ckbZalo: false,
                ckbOA: false,
                txtZalo: "",
                rlhId: 0
            },
            ZALO: false,
            chuaGanSelected: [],
            daGanSelected: [],
            addNewClicked: false
        };
    },
    validations: {},
    watch: {
        khachHangId() {
            if (this.khachHangId != null || this.khachHangId != "")
                this.initData();
        }
    },
    methods: {
        initData() {
            this.khachHangId && this.getDsTTLienHe();
            this.getTSMD();
        },
        onShow() {
            this.$refs.dlgTTLH.show();
        },
        onClose() {
            this.$emit("close");
        },
        reset() {
            // console.log("resset");
            // this.dsTLienHe = [];
            // this.dsLHThanhToan = [];
            // this.dsLHThueBao = [];
            // this.dsTLHKH = [];
            // this.dsMDChuaGan = [];
            // this.dsMDDaGan = [];
            // this.form = {
            //     tenLh: "",
            //     diaChi: "",
            //     dienThoai: "",
            //     fax: "",
            //     email: "",
            //     donVi: "",
            //     ttlhId: "",
            //     ckbZalo: false,
            //     ckbOA: false,
            //     txtZalo: "",
            //     rlhId: 0
            // };
            // this.ZALO = false;
            // this.chuaGanSelected = [];
            // this.daGanSelected = [];
            // this.addNewClicked = false;
            // this.khachHangId = "";
        },
        onClickToLeft() {
            if (this.daGanSelected.length > 0) {
                const itemTransf = this.dsMDDaGan.filter(item =>
                    this.daGanSelected.includes(item.MUCDICH_ID)
                );
                this.dsMDChuaGan = [...this.dsMDChuaGan, ...itemTransf];
                itemTransf.forEach(item => {
                    const index = this.dsMDDaGan.indexOf(item);
                    if (index > -1) {
                        this.dsMDDaGan.splice(index, 1);
                    }
                });
            }
        },
        onClickToLeftAll() {
            this.dsMDChuaGan = [...this.dsMDChuaGan, ...this.dsMDDaGan];
            this.dsMDDaGan = [];
        },
        onClickToRight() {
            if (this.chuaGanSelected.length > 0) {
                const itemTransf = this.dsMDChuaGan.filter(item =>
                    this.chuaGanSelected.includes(item.MUCDICH_ID)
                );
                this.dsMDDaGan = [...this.dsMDDaGan, ...itemTransf];
                itemTransf.forEach(item => {
                    const index = this.dsMDChuaGan.indexOf(item);
                    if (index > -1) {
                        this.dsMDChuaGan.splice(index, 1);
                    }
                });
            }
        },
        onClickToRightAll() {
            this.dsMDDaGan = [...this.dsMDDaGan, ...this.dsMDChuaGan];
            this.dsMDChuaGan = [];
        },
        onCheckChuaGan(value) {
            this.chuaGanSelected = value;
        },
        onCheckDaGan(value) {
            this.daGanSelected = value;
        },
        onSelectRowGrid(value) {
            this.addNewClicked = false;
            if (value) {
                this.form.tenLh = value.TEN_LH;
                this.form.diaChi = value.DIACHI;
                this.form.dienThoai = value.DIENTHOAI;
                this.form.email = value.EMAIL;
                this.form.fax = value.FAX;
                this.form.donVi = value.DONVI;
                this.form.ttlhId = value.TTLH_ID;

                if (this.ZALO) {
                    this.getTTMXH();
                } else {
                    this.form.ckbZalo = false;
                    this.form.txtZalo = "";
                    this.form.rlhId = 0;
                }
                this.getTTLHKhachHang();
                this.getDSLHThanhToan();
                this.getDSLHThueBao();
                this.getDSMDChuaGan();
                this.getDSMDDaGan();
            }
        },
        onClickTaoMoi() {
            this.addNewClicked = true;
            this.resetForm(this.form);
            this.getTTLHKhachHang();
            this.getDSLHThanhToan();
            this.getDSLHThueBao();
            this.getDSMDChuaGan();
        },
        resetForm(form) {
            Object.keys(form).forEach(function(key) {
                if (key !== "ckbZalo" && key != "rlhId" && key != "ckbOA")
                    form[key] = "";
            });
            this.form.ckbZalo = false;
            this.form.ckbOA = false;
            this.form.rlhId = 0;
            this.form.ttlhId = 0;
            this.dsMDChuaGan = [];
            this.dsMDDaGan = [];
            this.chuaGanSelected = [];
            this.daGanSelected = [];
            this.$refs.tenLh.focus();
        },
        onClickCapNhat() {
            if (!this.validateForm) {
                return false;
            }
            let pDSDK = this.addNewClicked
                ? this.makeDsDkAddNew()
                : this.makeDsDk();
            const dsIdMucDich = [];
            this.dsMDDaGan.map(item => {
                dsIdMucDich.push(item.MUCDICH_ID);
            });
            const param = {
                pTTLHId: this.form.ttlhId == 0 ? "" : this.form.ttlhId,
                pTenLH: this.form.tenLh,
                pDiaChi: this.form.diaChi,
                pDienThoai: this.form.dienThoai,
                pFax: this.form.fax,
                pEmail: this.form.email,
                pDonVi: this.form.donVi,
                pKhachHangId: this.khachHangId,
                pThanhToanId: this.thanhToanId,
                pThueBaoId: this.thueBaoId,
                pMucDich: dsIdMucDich.join(),
                pTTLHMXHId: "",
                pMangXaHoi: this.form.txtZalo.trim(),
                pMucDichMXHId: this.form.ckbOA ? 12 : 1,
                pDSDK: pDSDK,
                pMaND: "hoant.hpg",
                pIPCN: "10.59.90.156",
                pMayCN: "test"
            };
            this.capNhatTT(param);
        },
        makeDsDk() {
            let pDSDK = [];
            // Khach hanng
            this.dsTLHKH.map(item => {
                let CbkDk = document.getElementById(
                    `dk${this.form.ttlhId}_${item.MA_KH}`
                );
                let CbkHuy = document.getElementById(
                    `huy${this.form.ttlhId}_${item.MA_KH}`
                );
                if (CbkDk && CbkDk.checked) {
                    pDSDK.push({
                        DB_ID: item.KHACHHANG_ID,
                        TTKH_ID: 1,
                        YEUCAU: 1
                    });
                }

                if (CbkHuy && CbkHuy.checked) {
                    pDSDK.push({
                        DB_ID: item.KHACHHANG_ID,
                        TTKH_ID: 1,
                        YEUCAU: 0
                    });
                }
            });

            // Thanh toan
            this.dsLHThanhToan.map(item => {
                let CbkDk = document.getElementById(
                    `dk${this.form.ttlhId}_${item.MA_TT}`
                );
                let CbkHuy = document.getElementById(
                    `huy${this.form.ttlhId}_${item.MA_TT}`
                );
                if (CbkDk && CbkDk.checked) {
                    pDSDK.push({
                        DB_ID: item.THANHTOAN_ID,
                        TTKH_ID: 2,
                        YEUCAU: 1
                    });
                }

                if (CbkHuy && CbkHuy.checked) {
                    pDSDK.push({
                        DB_ID: item.THANHTOAN_ID,
                        TTKH_ID: 2,
                        YEUCAU: 0
                    });
                }
            });

            // Thue bao
            this.dsLHThueBao.map(item => {
                let CbkDk = document.getElementById(
                    `dk${this.form.ttlhId}_${item.MA_TB}`
                );
                let CbkHuy = document.getElementById(
                    `huy${this.form.ttlhId}_${item.MA_TB}`
                );
                if (CbkDk && CbkDk.checked) {
                    pDSDK.push({
                        DB_ID: item.THUEBAO_ID,
                        TTKH_ID: 3,
                        YEUCAU: 1
                    });
                }

                if (CbkHuy && CbkHuy.checked) {
                    pDSDK.push({
                        DB_ID: item.THUEBAO_ID,
                        TTKH_ID: 3,
                        YEUCAU: 0
                    });
                }
            });

            return pDSDK;
        },
        makeDsDkAddNew() {
            console.log(999);
            let pDSDK = [];
            // Khach hanng
            this.dsTLHKH.map(item => {
                pDSDK.push({
                    DB_ID: item.KHACHHANG_ID,
                    TTKH_ID: 1,
                    YEUCAU: 0
                });
            });

            // Thanh toan
            this.dsLHThanhToan.map(item => {
                pDSDK.push({
                    DB_ID: item.THANHTOAN_ID,
                    TTKH_ID: 2,
                    YEUCAU: 0
                });
            });

            // Thue bao
            this.dsLHThueBao.map(item => {
                pDSDK.push({
                    DB_ID: item.THUEBAO_ID,
                    TTKH_ID: 3,
                    YEUCAU: 0
                });
            });

            return pDSDK;
        },
        onClickDelete() {
            const body = {
                pTTLHId: this.form.ttlhId,
                pKhachHangId: this.khachHangId,
                pThanhToanId: this.thanhToanId,
                pThueBaoId: this.thueBaoId,
                pMangXaHoi: null,
                pMXHYeuCau: null,
                pMucDichMXHId: null,
                pMaND: "hoant.hpg",
                pIPCN: "10.59.90.156",
                pMayCN: "test"
            };
            this.xoaTT(body);
        },
        validateForm() {
            if (!this.form.tenLh.trim()) {
                this.$toast.error("Bạn chưa nhập thông tin Người liên hệ!");
                this.$refs.tenLh.focus();
                return false;
            }

            if (!this.form.diaChi.trim()) {
                this.$toast.error("Bạn chưa nhập thông tin Địa chỉ!");
                this.$refs.diaChi.focus();
                return false;
            }
        },
        getDsTTLienHe: async function() {
            const param = {
                pKhachHangId: this.khachHangId,
                pHDKHId: 0
            };
            console.log(param);
            try {
                const rs = await this.$root.context.get(
                    "/web-thuno/api/thu-no/thong-tin-khach-hang/lay-danh-sach-thong-tin-lien-he",
                    param
                );
                console.log("LienHe", rs.data);
                this.dsTLienHe = rs.data;
            } catch (error) {}
        },
        getTTMXH: async function() {
            try {
                const rs = await this.$root.context.get(
                    "/web-thuno/api/thu-no/thong-tin-khach-hang/lay-thong-tin-mang-xa-hoi",
                    { pTTLHId: this.form.ttlhId }
                );
                if (rs.data.length > 0) {
                    this.form.ckbZalo = true;
                    this.form.txtZalo = rs.data[0].MANG_XH;
                    this.form.rlhId = rs.data[0].RLH_ID;
                }
            } catch (error) {}
        },
        getTSMD: async function() {
            try {
                var rs = await this.$root.context.get(
                    "/web-thuno/api/thu-no/thong-tin-khach-hang/lay-danh-sach-tham-so-mac-dinh",
                    {
                        pMaThamSo: "CAPNHAT_LH_ZALO"
                    }
                );
                this.ZALO = rs.data.length > 0 && rs.data[0].TEN_TS == 1;
            } catch (error) {
            } finally {
            }
        },
        getTTLHKhachHang: async function() {
            const param = {
                pKhachHangId: this.khachHangId,
                pLoaiId: 1,
                pTTLHId: this.form.ttlhId
            };
            // console.log(param)
            try {
                var rs = await this.$root.context.get(
                    "/web-thuno/api/thu-no/thong-tin-khach-hang/lay-thong-tin-lien-he-theo-loai",
                    param
                );
                // console.log('dsTLHKH', rs);
                this.dsTLHKH = [];
                this.dsTLHKH = rs.data.map(item => {
                    return {
                        ...item,
                        // SUDUNG: item.DANGKY == 1 ? 'Dang su dung' : '',
                        DANGKY: `<div class="check-action">
                        <input type="checkbox" id="dk${this.form.ttlhId}_${
                            item.MA_KH
                        }" class="check" ${
                            item.SUDUNG == "Dang SD" ? "disabled" : ""
                        }>
                        <span class="name"></span>
                    </div>`,
                        HUY: `<div class="check-action">
                        <input type="checkbox"  id="huy${this.form.ttlhId}_${
                            item.MA_KH
                        }" class="check" ${
                            item.SUDUNG != "Dang SD" ? "disabled" : ""
                        }>
                        <span class="name"></span>
                    </div>`
                    };
                });
                // console.log('datarender', this.dsTLHKH)
            } catch (error) {}
        },
        getDSLHThanhToan: async function() {
            const param = {
                pKhachHangId: this.khachHangId,
                pLoaiId: 2,
                pTTLHId: this.form.ttlhId
            };
            try {
                var rs = await this.$root.context.get(
                    "/web-thuno/api/thu-no/thong-tin-khach-hang/lay-thong-tin-lien-he-theo-loai",
                    param
                );
                // console.log('dsLHThanhToan', rs);
                this.dsLHThanhToan = rs.data.map(item => {
                    return {
                        ...item,
                        DANGKY: `<div class="check-action">
                        <input type="checkbox" id="dk${this.form.ttlhId}_${
                            item.MA_TT
                        }" class="check" ${
                            item.SUDUNG == "Dang SD" ? "disabled" : ""
                        }>
                        <span class="name"></span>
                    </div>`,
                        HUY: `<div class="check-action">
                        <input type="checkbox"  id="huy${this.form.ttlhId}_${
                            item.MA_TT
                        }" class="check" ${
                            item.SUDUNG != "Dang SD" ? "disabled" : ""
                        }>
                        <span class="name"></span>
                    </div>`
                    };
                });
            } catch (error) {}
        },
        getDSLHThueBao: async function() {
            const param = {
                pKhachHangId: this.khachHangId,
                pLoaiId: 3,
                pTTLHId: this.form.ttlhId
            };
            try {
                var rs = await this.$root.context.get(
                    "/web-thuno/api/thu-no/thong-tin-khach-hang/lay-thong-tin-lien-he-theo-loai",
                    param
                );
                // console.log(rs);
                this.dsLHThueBao = rs.data.map(item => {
                    return {
                        ...item,
                        DANGKY: `<div class="check-action">
                        <input type="checkbox" id="dk${this.form.ttlhId}_${
                            item.MA_TB
                        }" class="check" ${
                            item.SUDUNG == "Dang SD" ? "disabled" : ""
                        }>
                        <span class="name"></span>
                    </div>`,
                        HUY: `<div class="check-action">
                        <input type="checkbox"  id="huy${this.form.ttlhId}_${
                            item.MA_TB
                        }" class="check" ${
                            item.SUDUNG != "Dang SD" ? "disabled" : ""
                        }>
                        <span class="name"></span>
                    </div>`
                    };
                });
            } catch (error) {}
        },
        getDSMDChuaGan: async function() {
            const param = {
                pKieu: 0,
                pTTLHId: this.form.ttlhId
            };
            // console.log(param)
            try {
                var rs = await this.$root.context.get(
                    "/web-thuno/api/thu-no/thong-tin-khach-hang/lay-danh-sach-muc-dich-thong-tin-lien-he",
                    param
                );
                // console.log(rs);
                this.dsMDChuaGan = rs.data;
            } catch (error) {}
        },
        getDSMDDaGan: async function() {
            const param = {
                pKieu: 1,
                pTTLHId: this.form.ttlhId
            };
            try {
                var rs = await this.$root.context.get(
                    "/web-thuno/api/thu-no/thong-tin-khach-hang/lay-danh-sach-muc-dich-thong-tin-lien-he",
                    param
                );
                // console.log(rs);
                this.dsMDDaGan = rs.data;
            } catch (error) {}
        },
        capNhatTT: async function(body) {
            console.log(body);
            try {
                const { data } = await this.axios.put(
                    "/web-thuno/api/thu-no/thong-tin-khach-hang/cap-nhat-lien-he",
                    body
                );
                if (data && data.error == "200") {
                    this.$toast.success(
                        "Cập nhật thông tin liên hệ thành công!"
                    );
                    this.initData();
                }
            } catch (error) {}
        },
        xoaTT: async function(body) {
            try {
                const {
                    data
                } = await this.axios.delete(
                    "/web-thuno/api/thu-no/thong-tin-khach-hang/xoa-thong-tin-lien-he",
                    { data: body }
                );
                if (data.error == "200") {
                    this.$toast.success("Xóa thông tin liên hệ thành công!");
                    this.initData();
                }
            } catch (error) {}
        }
    }
};
</script>
