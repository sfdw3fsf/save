<template src="./template.html"></template>
<script>
    import breadcrumb from "@/components/breadcrumb";
    import TraCuuTheBaoKhuyenNai from './components/TraCuuTheBaoKhuyenNai'
    import LichSuHen from './components/LichSuHen'
    import DSNhanVienThucHien from './components/DSNhanVienThucHien'
    import HoSoKhieuNai from './components/HoSoKhieuNai'
    import api from './api'
    import moment from 'moment'
    export default {
        data() {
            return {
                vphieukn_id: null,
                target: '.main-wrapper',
                thongtintracuu: {},
                openLichSuHen: false,
                openDSNhanVienThucHien: false,
                header: {
                    title: "TRA CỨU CHI TIẾT XỬ LÝ KHIẾU NẠI",
                    list: [
                        { name: "Lập hợp đồng", link: { name: "Ui.cards" } },
                        {
                            name: "Lắp đặt mới",
                            link: { name: "Ui.buttons" },
                        },
                    ]
                },
                kn: {
                    ma_tb : '',
                    ma_kn : '',
                    ten_tb : '',
                    diachi_lh : '',
                    ngay_tn : ''
                },
                ctkn: {},
                ctknloading: false,
                form: {
                    ma_kn: '',
                    ma_tb: '',
                    dichvuvt_id: null,
                    loaitb_id: null,
                    ttkn_id: null,
                    ky_kn:''                    
                },
                options: {
                    dskn: [],
                    ctkn: [],
                    dv: [],
                    lh: [],
                    trangthai: []
                },
                config: {
                    dskn: [
                        {
                            headerText: "Mã khiếu nại",
                            autoFit: true,allowSorting: false,
                            fieldName: "ma_kn"
                        },
                        {
                            headerText: "Số máy/Acc",
                            autoFit: true,allowSorting: false,
                            fieldName: "ma_tb"
                        },
                        {
                            headerText: "Loại hình",
                            autoFit: true,allowSorting: false,
                            fieldName: "loaihinh_tb"
                        },
                        {
                            headerText: "Ngày tiếp nhận",
                            autoFit: true,allowSorting: false,
                            fieldName: "ngay_tn"
                        },
                        {
                            headerText: "Người khiếu nại",
                            autoFit: true,allowSorting: false,
                            fieldName: "nguoi_kn"
                        },
                        {
                            headerText: "Điện thoại LH",
                            autoFit: true,allowSorting: false,
                            fieldName: "dienthoai_lh"
                        },
                        {
                            headerText: "Địa chỉ LH",
                            autoFit: true,allowSorting: false,
                            fieldName: "diachi_lh"
                        },
                        {
                            headerText: "Trạng thái",
                            autoFit: true,allowSorting: false,
                            fieldName: "trangthai_kn"
                        },
                        {
                            headerText: "Chủ đề khiếu nại",
                            autoFit: true,allowSorting: false,
                            fieldName: "chude_kn"
                        },
                        {
                            headerText: "Nội dung khiếu nại",
                            autoFit: true,allowSorting: false,
                            fieldName: "noidung_gq"
                        }
                    ],
                    ctkn: [
                        {
                            headerText: "Đơn vị giao",
                            autoFit: true,allowSorting: false,
                            fieldName: "dvg"
                        },
                        {
                            headerText: "Nhân viên giao",
                            autoFit: true,allowSorting: false,
                            fieldName: "nv_giao"
                        },
                        {
                            headerText: "ĐTNV giao",
                            autoFit: true,allowSorting: false,
                            fieldName: "sodt_nvgiao"
                        },
                        {
                            headerText: "Ngày giao",
                            autoFit: true,allowSorting: false,
                            fieldName: "ngay_giao"
                        },
                        {
                            headerText: " ",
                            fieldName: "huonggiao",
                            isGroupedColumn: true
                        }
                    ]
                }
            }
        },
        components: {
            breadcrumb,
            TraCuuTheBaoKhuyenNai,
            LichSuHen,
            DSNhanVienThucHien,
            HoSoKhieuNai
        },
        mounted() {
            console.log("clgt")
            const monthyear = moment().format('YYYY-MM')
            this.form.ky_kn = moment(monthyear + "-01").format("YYYYMM")
            this.getDichvuVienthong()
            this.getDanhSachTrangThaiPhieu()
        },
        watch: {
            "form.dichvuvt_id"(val) {
                this.getLoaiHinh(val)
            },
            "form.ky_kn"(val) {
                this.getKyKn(val)
            },
            kn: {
                handler() {
                    
                    Object.keys(this.kn).forEach(key => {
                        this.$set(this.form, key, this.kn[key])
                    })
                    if(this.kn.khieunai_id)
                        this.getPhieuKhieuNai(this.kn.khieunai_id)
                    else
                        this.options.ctkn = []
                },
                deep: true
            }
            // ctkn: {
            //     handler() {
            //         this.getPhieuKhieuNai(this.kn.phieu_id)
            //     },
            //     deep: true
            // }
        },
        methods: {
            chitiet_Click() {
                this.$refs.HoSoKhieuNai.setInput(this.kn)
                this.$refs.HoSoKhieuNai.open()
            },
            focusItem(data) {
                this.options.ctkn = []
                this.kn.ma_tb = ''
                this.kn.ma_kn = ''
                this.kn.ten_tb = ''
                this.kn.diachi_lh = ''
                this.kn.ngay_tn = ''

                if(data.data) {
                    for(let key in data.data) {
                        this.$set(this.kn, key, data.data[key])
                    }
                }
            },
            dialogTracuu() {
                this.$refs.dialogInfo.init()
                this.$refs.TraCuuTheBaoKhuyenNai.show()
            },
            async tracuu() {
                try {
                    this.loading(true)
                    let check = await this.axios.post("web-gqkn/TraCuuPhieuKN/fn_kiemtra_thamso_macdinh", {
                        "p_mats": "TRACUU_PHIEUKN_THEOMATB",
                        "p_tents": "1"
                    })
                    if(check.data && check.data.data == '1') {
                        await this.tracuuKNtheoMTB(this.form.ma_tb)
                    } else {
                        let matb = this.axios.post("web-gqkn/TraCuuPhieuKN/lay_danhba_theo_matb", {
                            "in_dichvuvt_id": this.form.dichvuvt_id,
                            "in_ma_tb": this.form.ma_tb
                        })

                        let thuebao_id = null
                        
                        if(matb.data && matb.data.length > 0) {
                            thuebao_id = matb.data[0].thuebao_id
                        }

                        if(thuebao_id) {
                            let dataTB = await this.axios.post("web-gqkn/TraCuuPhieuKN/lay_ds_khieunai_theo_thuebao_id", {
                                "vthuebao_id": thuebao_id
                            })

                            this.options.dskn = dataTB.data
                        } else {
                            await this.tracuuKNtheoMTB(this.form.ma_tb)
                        }
                    }

                    
                    this.loading(false)
                } catch(err) {
                    console.log(err)
                    this.$toast.error("Đã có lỗi xảy ra")
                }
            },
            getMaTB(value) {
                this.form.ma_tb = value
                this.$refs.TraCuuTheBaoKhuyenNai.hide()
                this.tracuu()
            },
            getDichvuVienthong() {
                api.getDichvuVienthong(this.axios, {p_nguoidung_id: this.$root.token.getNhanVienID()}).then(res => {
                    this.options.dv = res.data.data ? res.data.data : []
                    if(this.options.dv.length > 0) {
                        this.form.dichvuvt_id = this.options.dv[0].dichvuvt_id
                    }
                })
            },
            getLoaiHinh(dvvt_id) {
                api.getLoaiHinh(this.axios, {dvvt_id}).then(res => {
                    this.options.lh = res.data.data ? res.data.data : []
                    if(this.options.lh.length > 0) {
                        this.form.loaitb_id = this.options.lh[0].ID
                    }
                })
            },
            async tracuuKNtheoMTB(vma_tb) {
                await api.tracuuKNtheoMTB(this.axios, {vma_tb: this.form.ma_tb, 'vkyhoadon' : this.form.ky_kn}).then(res => {
                    this.options.dskn = res.data.data ? res.data.data : []
                    if(this.options.dskn == 0) {
                        this.focusItem({data: null})
                    }
                })
            },
            async tracuuKNtheoMKN(vma_kn) {
                await api.tracuuKNtheoMKN(this.axios, {vma_kn: this.form.ma_kn}).then(res => {
                    this.options.dskn = res.data.data ? res.data.data : []
                })
            },
            tracuuKNtheoTBI(vthuebao_id) {
                api.tracuuKNtheoTBI(this.axios, {vthuebao_id}).then(res => {
                    this.options.dskn = res.data.data ? res.data.data : []
                })
            },
            getDanhSachTrangThaiPhieu() {
                api.getDanhSachTrangThaiPhieu(this.axios).then(res => {
                    this.options.trangthai = res.data.data ? res.data.data : []
                    if(this.options.trangthai.length > 0) {
                        this.form.ttkn_id = this.options.trangthai[0].ttkn_id
                    }
                })
            },
            getPhieuKhieuNai(khieunai_id) {
                this.ctknloading = true
                api.getPhieuKhieuNai(this.axios, {khieunai_id}).then(res => {
                    this.options.ctkn = res.data.data ? res.data.data : []
                }).finally(() => {
                    this.ctknloading = false
                })
            },
            getKyKn(p_namthang) {
                let date = moment(p_namthang).format('YYYYMM')
                api.getKyKn(this.axios, {
                    p_namthang: date
                }).then(res => {
                    this.form.ckn = res.data ? res.data.data : ''
                })
            },
            selectItem(data) {
                console.log(data)
                this.vphieukn_id = data.data.phieu_id
            }
        }
    }
</script>