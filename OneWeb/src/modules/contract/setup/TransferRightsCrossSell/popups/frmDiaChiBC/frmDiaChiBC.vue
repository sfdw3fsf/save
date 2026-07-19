<template src="./frmDiaChiBC.html"></template>
<script>
import ModalChonToaDo from '@/modules/CABMAN/XetDuyetPhanTuMangHoDanThuThap/ChonToaDo'
import api from "./API.js";
export default {
    name: "frmDiaChiBC",
    props: ["modalId", "obj", "tinh", "isStrictMode"],
    components: { ModalChonToaDo },
    computed: {
        getPosition () {
            return {
                lat: parseFloat(this.lattitude),
                lng: parseFloat(this.longtitude)
            }
        },
    },
    data() {
        return {
            phaichondacdiem: false,
            lattitude: 0,
            longtitude: 0,
            model: {
                cboTinhThanh: {
                    list: [],
                    value: null
                },
                cboQuan: {
                    list: [],
                    value: null
                },
                cboPhuong: {
                    list: [],
                    value: null
                },
                txtSoNha: "",
                chkPho: false,
                cboDuongPho: {
                    list: [],
                    value: null
                },
                chkAp: false,
                cboNgo: {
                    list: [],
                    value: null
                },
                chkKhu: false,
                cboKhu: {
                    list: [],
                    value: null
                },
                chkDacDiem: false,
                cboDacDiem: {
                    list: [],
                    value: null
                },
                txtDiaChi: "",
            },
            enable: {
                chkDacDiem: true,
                cboDacDiem: false,
                txtDiaChi: false,
            },

            them_text_vietnam: 0,
            isTinhSuDung: false,
        }
    },
    components: { ModalChonToaDo },
    methods: {
        XacNhanToaDo (position) {
            this.lattitude = position.lat;
            this.longtitude = position.lng;
        },
        async lay_ds_tinh() {
            let result = []
            try {
                let params = {
                    "tinhthicong_id": this.tinh.tinhthicong_id
                }
                let response = await api.lay_ds_tinh(this.axios, params);
                if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
                    result = response.data.data
                }            
            } catch (e) {
                console.log(e)
            }
            return result
        },
        async LAY_DS_THAMSO_MD(p_kieu_id) {
            let result = []
              try {
                  let params = {
                      "p_kieu_id": p_kieu_id
                  }
                  let response = await api.sp_lay_ds_thamso_md(this.axios, params);
                  if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
                      result = response.data.data
                  }            
              } catch (e) {
                  console.log(e)
              }
              return result
        },
        async frmDiaChiBC_Load() {
            console.log(this.obj)
            try {
                this.loading(true)
                if (this.model.chkPho) this.model.chkPho = false
                if (this.model.chkAp) this.model.chkAp = false
                if (this.model.chkKhu) this.model.chkKhu = false
                if (this.model.chkDacDiem) this.model.chkDacDiem = false

                if (this.tinh == undefined || this.tinh.tinhthicong_id == 0) {
                    let temps = await this.lay_thongtin_ds_tinh_quan()  
                    this.model.cboTinhThanh.list = temps.map((x)=>({id:x.tinh_id, text:x.tentinh}))
                } else {
                    let temps = await this.lay_ds_tinh()
                    this.model.cboTinhThanh.list = temps.map((x)=>({id:x.tinh_id, text:x.tentinh}))
                }

                let ds_ts = await this.LAY_DS_THAMSO_MD(0);
                if (ds_ts.length > 0) {
                    ds_ts.forEach((item) => {
                        if (item.ma_ts == "THEM_VN_DIACHI" && item.ten_ts == "1") {
                            this.them_text_vietnam = 1
                        }
                    })
                }

                if (this.obj.tinh_id != undefined && this.obj.tinh_id != 0) {
                    this.model.cboTinhThanh.value = this.obj.tinh_id;
                    if (this.obj.quan_id != 0) {
                        this.model.cboQuan.value = this.obj.quan_id;
                        // loadDS 
                        if (this.tinh != undefined && this.tinh.tinhthicong_id == 0) {
                            let temps = await this.lay_thongtin_ds_phuong_theo_quan(this.obj.quan_id)
                            this.model.cboPhuong.list = temps.map((x)=>({id:x.phuong_id, text:x.ten_phuong}))
                        } else {
                            let temps = await this.lay_ds_phuong(this.obj.quan_id)
                            this.model.cboPhuong.list = temps.map((x)=>({id:x.phuong_id, text:x.ten_phuong}))
                        }
                        if (this.obj.phuong_id != 0) {
                            this.model.cboPhuong.value = this.obj.phuong_id;
                            // loadDS 
                            if (this.tinh != undefined && this.tinh.tinhthicong_id == 0) {
                                let temps = await this.lay_thongtin_ds_pho_ngo_khu_theo_phuong(this.obj.phuong_id, 1)
                                this.model.cboDuongPho.list = temps.map((x)=>({id:x.pho_id, text:x.ten_pho}))

                                temps = await this.lay_thongtin_ds_pho_ngo_khu_theo_phuong(this.obj.phuong_id, 2)
                                this.model.cboNgo.list = temps.map((x)=>({id:x.pho_id, text:x.ten_pho}))

                                temps = await this.lay_thongtin_ds_pho_ngo_khu_theo_phuong(this.obj.phuong_id, 3)
                                this.model.cboKhu.list = temps.map((x)=>({id:x.pho_id, text:x.ten_pho}))
                            } else {
                                let temps = await this.lay_ds_pho(this.obj.phuong_id, 1)
                                this.model.cboDuongPho.list = temps.map((x)=>({id:x.pho_id, text:x.ten_pho}))

                                temps = await this.lay_ds_pho(this.obj.phuong_id, 2)
                                this.model.cboNgo.list = temps.map((x)=>({id:x.pho_id, text:x.ten_pho}))

                                temps = await this.lay_ds_pho(this.obj.phuong_id, 3)
                                this.model.cboKhu.list = temps.map((x)=>({id:x.pho_id, text:x.ten_pho}))
                            }            

                            if (this.obj.pho_id != 0) {
                                this.model.chkPho = true;
                                this.model.cboDuongPho.value = this.obj.pho_id;
                            }
                            if (this.obj.ap_id != 0) {
                                this.model.chkAp = true;
                                this.model.cboNgo.value = this.obj.ap_id;
                            }
                            if (this.obj.khu_id != 0) {
                                this.model.chkKhu = true;
                                this.model.cboKhu.value = this.obj.khu_id;
                            }

                            await this.lay_dacdiem();
                            if (this.obj.dacdiem_id != 0) {
                                this.model.chkDacDiem = true;
                                this.enable.cboDacDiem = true;
                                this.model.cboDacDiem.value = this.obj.dacdiem_id;
                            }
                        }
                    }
                }
                else {
                    if (this.tinh != undefined && this.tinh.tinhthicong_id != undefined && this.tinh.tinhthicong_id > 0) {
                        this.model.cboTinhThanh.value = this.tinh.tinhthicong_id
                    } else {
                        this.model.cboTinhThanh.value = this.$root.token.getPhanVungID()
                    }         
                }

                this.model.txtSoNha = this.obj.sonha;
                this.HienThiTTDiaChi()
            } catch (e) {
                console.log(e)
                this.$toast.error(e)
            } finally {
                this.loading(false)
            }
        },
        async lay_ds_quan(tinh_id) {
            let result = []
            try {
                let params = {
                    "tinh_id": tinh_id,
                    "tinhthicong_id": this.tinh.tinhthicong_id
                }
                let response = await api.lay_ds_quan(this.axios, params);
                if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
                    result = response.data.data
                }            
            } catch (e) {
                console.log(e)
            }
            return result
        },
        async cboTinhThanh_SelectedValueChanged() {
            try {
                let tinh_id = Number(this.model.cboTinhThanh.value);
                if (tinh_id != 0) {
                    if (tinh_id == this.$root.token.getPhanVungID())
                        this.isTinhSuDung = true;
                    else
                        this.isTinhSuDung = false;

                    if (this.tinh == undefined || this.tinh.tinhthicong_id == 0) {
                        let temps = await this.lay_thongtin_ds_quan_theo_tinh(tinh_id)
                        this.model.cboQuan.list = temps.map((x)=>({id:x.quan_id, text:x.ten_quan}))
                        this.model.cboQuan.value =  temps.length > 0 ? (this.obj.quan_id != undefined && this.obj.quan_id != null && this.obj.quan_id != 0 ? this.obj.quan_id : temps[0].quan_id) : null
                    } else {
                        let temps = await this.lay_ds_quan(tinh_id)
                        this.model.cboQuan.list = temps.map((x)=>({id:x.quan_id, text:x.ten_quan}))
                        this.model.cboQuan.value =  temps.length > 0 ? (this.obj.quan_id != undefined && this.obj.quan_id != null && this.obj.quan_id != 0 ? this.obj.quan_id : temps[0].quan_id) : null
                    }
                }

                // hoangpkn : 11/10/2016
                if (!this.isTinhSuDung)
                    this.enable.txtDiaChi = true;
                else
                    this.enable.txtDiaChi = false;
            } catch (ex) {
                this.$toast.error("Có lỗi: " + ex);
            }
        },
        async lay_dacdiem() {
            try {
                let ipho_id = 0;
                let ikhu_id = 0;
                let iap_id = 0;
                let iphuong_id = 0;
                if (this.model.cboPhuong.value == null || String(this.model.cboPhuong.value) == "0")
                    iphuong_id = 0;
                else
                    iphuong_id = Number(this.model.cboPhuong.value);

                if (this.model.cboDuongPho.list.length > 0 && this.model.chkPho) {
                    if (this.model.cboDuongPho.value == null || String(this.model.cboDuongPho.value) == "0")
                        ipho_id = 0;
                    else
                        ipho_id = Number(this.model.cboDuongPho.value);
                }

                if (this.model.cboNgo.list.length > 0 && this.model.chkAp) {
                    if (this.model.cboNgo.value == null || String(this.model.cboNgo.value) == "0")
                        iap_id = 0;
                    else
                        iap_id = Number(this.model.cboNgo.value);
                }

                if (this.model.cboKhu.list.length > 0 && this.model.chkKhu) {
                    if (this.model.cboKhu.value == null || String(this.model.cboKhu.value) == "0")
                        ikhu_id = 0;
                    else
                        ikhu_id = Number(this.model.cboKhu.value);
                }

                if (ipho_id == 0 && iap_id == 0 && ikhu_id == 0 && iphuong_id == 0) {
                    this.model.cboDacDiem.list = [];
                    this.model.chkDacDiem = false;
                    this.enable.chkDacDiem = false;
                    this.enable.cboDacDiem = false;
                } else {
                    this.model.cboDacDiem.list = [];
                    let ds_dd = await this.HT_DacDiem_Combobox(ipho_id, iap_id, ikhu_id, iphuong_id);
                    if (ds_dd && ds_dd.length > 0){
                        this.model.cboDacDiem.list = ds_dd.map((x)=>({id:x.dacdiem_id, text:x.dacdiem}))
                        this.model.cboDacDiem.value = ds_dd[0].dacdiem_id

                        this.model.chkDacDiem = false;
                        this.enable.chkDacDiem = true;
                        this.enable.cboDacDiem = false;
                    } else {
                        this.model.cboDacDiem.list = [];
                        this.model.chkDacDiem = false;
                        this.enable.chkDacDiem = false;
                        this.enable.cboDacDiem = false;
                    }
                }
            } catch (ex) {
                this.$toast.error("Có lỗi: " + ex);
            }
        },
        async HT_DacDiem_Combobox(pho_id, ap_id, khu_id, phuong_id) {
            let ds = await this.lay_ds_dacdiem(pho_id, ap_id, khu_id, phuong_id)
            return ds;
        },
        async lay_ds_dacdiem(pho_id, ap_id, khu_id, phuong_id) {
            let result = []
            try {
                let params = {
                    "pho_id": pho_id,
                    "ap_id": ap_id,
                    "khu_id": khu_id,
                    "phuong_id": phuong_id
                }
                let response = await api.lay_ds_dacdiem(this.axios, params);
                if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
                    result = response.data.data
                }            
            } catch (e) {
                console.log(e)
            }
            return result
        },
        async lay_ds_phuong(quan_id) {
            let result = []
            try {
                let params = {
                    "quan_id": quan_id,
                    "tinhthicong_id": this.tinh.tinhthicong_id
                }
                let response = await api.lay_ds_phuong(this.axios, params);
                if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
                    result = response.data.data
                }            
            } catch (e) {
                console.log(e)
            }
            return result
        },
        async cboQuan_SelectedValueChanged() {
            this.model.cboDuongPho.list = [];
            this.model.cboNgo.list = [];
            this.model.cboKhu.list = [];
            this.model.cboDacDiem.list = []

            if (this.model.cboQuan.value != null & this.model.cboQuan.list.length > 0) {
                let quan_id = Number(this.model.cboQuan.value);
                if (quan_id != 0) {
                    if (this.tinh != undefined && this.tinh.tinhthicong_id == 0) {
                        let temps = await this.lay_thongtin_ds_phuong_theo_quan(quan_id)
                        this.model.cboPhuong.list = temps.map((x)=>({id:x.phuong_id, text:x.ten_phuong}))
                        this.model.cboPhuong.value = temps.length > 0 ? temps[0].phuong_id : null
                    } else {
                        let temps = await this.lay_ds_phuong(quan_id)
                        this.model.cboPhuong.list = temps.map((x)=>({id:x.phuong_id, text:x.ten_phuong}))
                        this.model.cboPhuong.value = temps.length > 0 ? temps[0].quan_id : null
                    }
                }
                else
                    this.model.cboPhuong.list = [];
            } else {
                this.model.cboPhuong.list = [];
            }

           this.HienThiTTDiaChi();
        },
        HienThiTTDiaChi() {
            if (this.model.cboTinhThanh.value != null && Number(this.model.cboTinhThanh.value) == 98)
                this.model.txtDiaChi = this.model.txtSoNha;

            if (this.model.cboQuan.value == null)
                return;

            if (this.model.cboPhuong.value == null)
                return;

            let quan = "";
            let phuong = "";
            let _sSoNha = "", sDuong = "", sAp = "", sKhu = "";
            let temp = {}

            temp = this.model.cboTinhThanh.list.find((item) => item.id == this.model.cboTinhThanh.value)
            let tttinh = temp != undefined ? temp.text : ""

            temp = this.model.cboQuan.list.find((item) => item.id == this.model.cboQuan.value)
            quan = ", " + (temp != undefined ? temp.text : "") + ", " + tttinh

            temp = this.model.cboPhuong.list.find((item) => item.id == this.model.cboPhuong.value)
            phuong = temp != undefined ? temp.text : ""
                
            if (this.model.cboDuongPho.list.length > 0 && this.model.chkPho) {
                if (this.model.cboDuongPho.value == null) {
                    this.$toast.error("Hãy chọn Đường/Phố");
                    return;
                }
                temp = this.model.cboDuongPho.list.find((item) => item.id == this.model.cboDuongPho.value)
                sDuong = temp != undefined ? (temp.text == "" ? "" : temp.text + ", ") : ""
            }

            if (this.model.cboNgo.list.length > 0 && this.model.chkAp) {
                if (this.model.cboNgo.value == null) {
                    this.$toast.error("Hãy chọn Ngõ/Ấp!");
                    return;
                }
                temp = this.model.cboNgo.list.find((item) => item.id == this.model.cboNgo.value)
                sAp = temp != undefined ? (temp.text == "" ? "" : temp.text + ", ") : ""
            }

            if (this.model.cboKhu.list.length > 0 && this.model.chkKhu) {
                if (this.model.cboKhu.value == null) {
                    this.$toast.error("Hãy chọn Khu!");
                    return;
                }
                temp = this.model.cboKhu.list.find((item) => item.id == this.model.cboKhu.value)
                sKhu = temp != undefined ? (temp.text == "" ? "" : temp.text + ", ") : ""
            }
            if (this.model.txtSoNha.trim() != "")
                _sSoNha = this.model.txtSoNha.trim() + ", ";

            if (this.them_text_vietnam == 1)
                this.model.txtDiaChi = _sSoNha + sKhu + sAp + sDuong + phuong + quan + ", Việt Nam";
            else
                this.model.txtDiaChi = _sSoNha + sKhu + sAp + sDuong + phuong + quan;
        },
        async lay_ds_pho(phuong_id, nhom_pho) {
            let result = []
            try {
                let params = {
                    "phuong_id": phuong_id,
                    "nhompho_id": nhom_pho,
                    "tinhthicong_id": this.tinh.tinhthicong_id
                }
                let response = await api.lay_ds_pho(this.axios, params);
                if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
                    result = response.data.data
                }            
            } catch (e) {
                console.log(e)
            }
            return result
        },
        async cboPhuong_SelectedValueChanged() {
            if (this.model.cboPhuong.list.length > 0) {
                let phuong_id = Number(this.model.cboPhuong.value);
                if (phuong_id != 0) {
                    if (this.tinh != undefined && this.tinh.tinhthicong_id == 0) {
                        let temps = await this.lay_thongtin_ds_pho_ngo_khu_theo_phuong(phuong_id, 1)
                        this.model.cboDuongPho.list = temps.map((x)=>({id:x.pho_id, text:x.ten_pho}))
                        this.model.cboDuongPho.value = temps.length > 0 ? temps[0].pho_id : null
                    } else {
                        let temps = await this.lay_ds_pho(phuong_id, 1)
                        this.model.cboDuongPho.list = temps.map((x)=>({id:x.pho_id, text:x.ten_pho}))
                        this.model.cboDuongPho.value = temps.length > 0 ? temps[0].pho_id : null
                    }            
                    if (this.model.cboDuongPho.list.length == 0)
                        this.model.chkPho = false;

                    if (this.tinh != undefined && this.tinh.tinhthicong_id == 0) {
                        let temps = await this.lay_thongtin_ds_pho_ngo_khu_theo_phuong(phuong_id, 2)
                        this.model.cboNgo.list = temps.map((x)=>({id:x.pho_id, text:x.ten_pho}))
                        this.model.cboNgo.value = temps.length > 0 ? temps[0].pho_id : null
                    } else {
                        let temps = await this.lay_ds_pho(phuong_id, 2)
                        this.model.cboNgo.list = temps.map((x)=>({id:x.pho_id, text:x.ten_pho}))
                        this.model.cboNgo.value = temps.length > 0 ? temps[0].pho_id : null
                    }
                    if (this.model.cboNgo.list.length == 0)
                        this.model.chkAp = false;

                    if (this.tinh != undefined && this.tinh.tinhthicong_id == 0) {
                        let temps = await this.lay_thongtin_ds_pho_ngo_khu_theo_phuong(phuong_id, 3)
                        this.model.cboKhu.list = temps.map((x)=>({id:x.pho_id, text:x.ten_pho}))
                        this.model.cboKhu.value = temps.length > 0 ? temps[0].pho_id : null
                    } else {
                        let temps = await this.lay_ds_pho(phuong_id, 3)
                        this.model.cboKhu.list = temps.map((x)=>({id:x.pho_id, text:x.ten_pho}))
                        this.model.cboKhu.value = temps.length > 0 ? temps[0].pho_id : null
                    }
                    if (this.model.cboKhu.list.length == 0)
                        this.model.chkKhu = false;
                }
            } else {
                this.model.cboDuongPho.list = [];
                this.model.cboNgo.list = [];
                this.model.cboKhu.list = [];
            }
                
            this.HienThiTTDiaChi();
        },
        async cboDuongPho_SelectedValueChanged() {
            this.HienThiTTDiaChi();
            await this.lay_dacdiem();
        },
        async cboNgo_SelectedValueChanged() {
            this.HienThiTTDiaChi();
            await this.lay_dacdiem();
        },
        async cboKhu_SelectedValueChanged() {
            this.HienThiTTDiaChi();
            await this.lay_dacdiem();
        },
        txtSoNha_TextChanged() {
            this.HienThiTTDiaChi();
        },
        btnChapNhan_Click() {
            let objRes = {}
            objRes.pho_id = 0;
            objRes.khu_id = 0;
            objRes.ap_id = 0;
            objRes.dacdiem_id = 0;
            if (this.model.cboDuongPho.list.length > 0 && this.model.chkPho) {
                if (this.model.cboDuongPho.value == null || String(this.model.cboDuongPho.value) == "0") {
                    this.$toast.error("Hãy chọn Đường/Phố");
                    return;
                }
                objRes.pho_id = Number(this.model.cboDuongPho.value);
            }

            if (this.model.cboNgo.list.length > 0 && this.model.chkAp) {
                if (this.model.cboNgo.value == null || String(this.model.cboNgo.value) == "0") {
                    this.$toast.error("Hãy chọn Ngõ/Ấp");
                    return;
                }
                objRes.ap_id = Number(this.model.cboNgo.value);
            }

            if (this.model.cboKhu.list.length > 0 && this.model.chkKhu) {
                if (this.model.cboKhu.value == null || String(this.model.cboKhu.value) == "0") {
                    this.$toast.error("Hãy chọn Khu");
                    return;
                }
                objRes.khu_id = Number(this.model.cboKhu.value);
            }

            if (!this.phaichondacdiem) {
                if (this.model.cboDacDiem.list.length > 0 && !this.model.chkDacDiem) {
                    this.$confirm('Bạn thật sự không muốn chọn đặc điểm ?', 'Thông báo',
                        { confirmButtonText: 'Có',
                          cancelButtonText: 'Không',
                        }).then((v) => {
                            if (v) return
                        })
                }

                if (this.model.cboDacDiem.list.length > 0 && this.model.chkDacDiem) {
                    if (this.model.cboDacDiem.value == null || String(this.model.cboDacDiem.value) == "0") {
                        this.$toast.error("Hãy chọn đặc điểm");
                        return;
                    }
                    objRes.dacdiem_id = Number(this.model.cboDacDiem.value);
                }
            } else {
                if (this.model.cboDacDiem.list.length > 0) {
                    if (!this.model.chkDacDiem) {
                        this.model.chkDacDiem = true;
                        this.$toast.error("Hãy chọn đặc điểm!");
                        return;
                    }

                    if (this.model.cboDacDiem.value == null || String(this.model.cboDacDiem.value) == "0") {
                        this.$toast.error("Hãy chọn đặc điểm!");
                        return;
                    }
                    objRes.dacdiem_id = Number(this.model.cboDacDiem.value);
                }
            }

            // hoangpkn : 25/10/2017 Bỏ điều kiện isTinhSuDung do ko lấy được mức cước thuê bao nếu ko có phố ấp khu
            //if (isTinhSuDung && pho_id == 0 && ap_id == 0 && khu_id == 0)//Nếu là HCM thì mới bắt buộc chọn
            //if (pho_id == 0 && ap_id == 0 && khu_id == 0)//Nếu là HCM thì mới bắt buộc chọn
            if (objRes.pho_id == 0 && objRes.ap_id == 0 && objRes.khu_id == 0 && Number(this.model.cboTinhThanh.value) != 98) {//Nếu là HCM thì mới bắt buộc chọn {
                this.$toast.error("Hãy chọn Phố, Ấp, Khu!");
                return;
            }

            objRes.tinh_id = Number(this.model.cboTinhThanh.value)
            if (objRes.tinh_id == 98) {
                objRes.quan_id = 0
                objRes.phuong_id = 0
            } else {
                objRes.quan_id = Number(this.model.cboQuan.value)
                objRes.phuong_id = Number(this.model.cboPhuong.value)
            }

            if (this.isTinhSuDung) {
                this.model.txtSoNha = this.model.txtSoNha + "  ";
                $("#txtDiaChi").focus();
            }
            objRes.sonha = this.model.txtSoNha;
            objRes.diachi = this.model.txtDiaChi;
            this.$emit("xacnhan", objRes)
        },
        btnBoQua_Click() {
            this.$emit("boqua")
        },
        async lay_thongtin_ds_tinh_quan() {
            let res = []
            try {
                let response = await api.lay_thongtin_ds_tinh_quan(this.axios);
                if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
                    res = response.data.data
                }            
            } catch (e) {
                console.log(e)
            }
            return res
        },
        async lay_thongtin_ds_quan_theo_tinh(tinh_id) {
            let res = []
            try {
                let params = {
                    "tinh_id": tinh_id
                }
                let response = await api.lay_thongtin_ds_quan_theo_tinh(this.axios, params);
                if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
                    res = response.data.data
                }            
            } catch (e) {
                console.log(e)
            }
            return res
        },
        async lay_thongtin_ds_phuong_theo_quan(quan_id) {
            let res = []
            try {
                let params = {
                    "quan_id": quan_id
                }
                let response = await api.lay_thongtin_ds_phuong_theo_quan(this.axios, params);
                if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
                    res = response.data.data
                }            
            } catch (e) {
                console.log(e)
            }
            return res
        },
        async lay_thongtin_ds_pho_ngo_khu_theo_phuong(phuong_id, loai) {
            let res = []
            try {
                let params = {
                    "phuong_id": phuong_id,
                    "loai": loai
                }
                let response = await api.lay_thongtin_ds_pho_ngo_khu_theo_phuong(this.axios, params);
                if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
                    res = response.data.data
                }            
            } catch (e) {
                console.log(e)
            }
            return res
        },
    },
    watch: {      
        'model.cboTinhThanh.value': async function(val){      
            this.cboTinhThanh_SelectedValueChanged()
        },
        'model.cboQuan.value': async function(val){      
            this.cboQuan_SelectedValueChanged()
        },
        'model.cboPhuong.value': async function(val){      
            this.cboPhuong_SelectedValueChanged()
        },
        'model.cboDuongPho.value': async function(val){      
            this.cboDuongPho_SelectedValueChanged()
        },
        'model.cboNgo.value': async function(val){      
            this.cboNgo_SelectedValueChanged()
        },
        'model.cboKhu.value': async function(val){      
            this.cboKhu_SelectedValueChanged()
        },
        'model.chkDacDiem': async function(val){      
            this.enable.cboDacDiem = this.model.chkDacDiem;
        },
        'model.chkKhu': async function(val){      
            this.cboKhu_SelectedValueChanged()
        },
        'model.chkAp': async function(val){      
            this.cboNgo_SelectedValueChanged()
        },
        'model.chkPho': async function(val){      
            this.cboDuongPho_SelectedValueChanged()
        },
    }
    
};
</script>
<style>
div#popupTCTTTB___BV_modal_outer_ {
    z-index: 10 !important;
    margin-top: 100px;
}
.dashboard-header {
    z-index: 9 !important;
}
a.disabled {
    pointer-events: none;
    cursor: default;
    color: #6c757d !important;
}
</style>