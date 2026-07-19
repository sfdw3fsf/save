<template src="./ModalChonDiaChi.html"></template>
<script>
    import { Query } from '@syncfusion/ej2-data'
    export default {
        name: "ModalChonDiaChi",
        props: ["modalId", "data", "isStrictMode"],
        created: async function () {
            this.dsTinh=[]
            this.diachi.tinh_id = null
        },

        computed: {
            thaydoiDiaChi: function () {
                if (this.tentinh != "") {
                    this.diachi.diachimoi = this.tentinh;
                    this.diachi.diachimoi = this.tenquan + ", " + this.diachi.diachimoi;
                    this.diachi.diachimoi = this.tenphuong + ", " + this.diachi.diachimoi;
                    if (this.isPho && this.diachi.pho_id != 0)
                        this.diachi.diachimoi = this.tenpho + ", " + this.diachi.diachimoi;
                    if (this.isAp && this.diachi.ap_id != 0)
                        this.diachi.diachimoi = this.tenap + ", " + this.diachi.diachimoi;
                    if (this.isKhu && this.diachi.khu_id != 0)
                        this.diachi.diachimoi = this.tenkhu + ", " + this.diachi.diachimoi;
                    if (this.diachi.so_nha != null && this.diachi.so_nha != "")
                        this.diachi.diachimoi = this.diachi.so_nha + ", " + this.diachi.diachimoi;
                    return this.diachi.diachimoi;
                }
                return "";
            },
        },
        watch: {
        },
        methods: {
            onFilteringTinh (e) {
                let query = new Query()
                query = (e.text !== '') ? query.where('TENTINH', 'contains', e.text, true) : query
                e.updateData(this.dsTinh, query)
            },
            onModalHidden() {
                // this.dsTinh=[]
                // this.diachi.tinh_id = null
                // this.diachi.quan_id = null
                // this.diachi.phuong_id = null
                // this.diachi.pho_id = null
                // this.diachi.ap_id = null
                // this.diachi.khu_id = null
                // this.diachi.dacdiem_id = null
                // this.diachi.so_nha=""
            },
            focusMyElement: async function () {
                //console.log(this.data)
                await this.getTinh()
                // console.log('this.data.tinh_id', this.data.tinh_id, this.dsTinh)
                if (this.data.tinh_id != null) {
                    // console.log(this.data)

                    this.diachi.tinh_id = this.data.tinh_id
                    this.tentinh = this.dsTinh.filter(
                        (x) => x.TINH_ID == this.diachi.tinh_id
                    )[0].TENTINH
                    
                    this.diachi.quan_id = this.data.quan_id
                    await this.getQuan()

                    this.diachi.phuong_id=this.data.phuong_id
                    await this.getPhuong()

                    if(this.data.phuong_id!=null ){
                        if(this.data.pho_id!=null&& this.data.pho_id!=0) {
                            this.diachi.pho_id=this.data.pho_id
                            this.isPho=true}

                        if(this.data.ap_id!=null && this.data.ap_id!=0) {
                            this.diachi.ap_id=this.data.ap_id
                            this.isAp=true}

                        if(this.data.khu_id!=null && this.data.khu_id!=0){
                            this.diachi.khu_id=this.data.khu_id
                            this.isKhu=true}
                    }
                    await this.getPhoApKhu()

                    this.diachi.so_nha = this.data.so_nha
                    this.diachi.daydiachi = this.data.diachi
                    this.diachi.diachimoi = this.data.diachi


                }else{
                    if(this.dsTinh !=null && this.dsTinh.length > 0)
                        this.diachi.tinh_id = this.$auth.getPhanVungID() //this.dsTinh[0].TINH_ID
                    this.tentinh = this.dsTinh.filter(
                        (x) => x.TINH_ID == this.diachi.tinh_id
                    )[0].TENTINH
                    await  this.getQuan()
                }
            },

            getTinh: async function () {
                try {
                    this.$root.showLoading(true);
                    let rs = []
                    if (this.$auth.getMaTinh() !== 'HCM') {
                        rs = await this.$root.context.get(
                            "/web-cabman/bando_tuyencot/lay_thongtin_tinh"
                        );
                    } else {
                        rs = await this.$root.context.get(
                            "/web-cabman/bando_tuyencot/lay_thongtin_tatca_tinh"
                        );
                    }
                    
                    // this.dsTinh.push(rs.data)
                    this.dsTinh = [...rs.data]

                } catch (error) {
                    console.log(error)
                } finally {
                    this.$root.showLoading(false)
                }
            },
            getQuan: async function () {
                try {
                    this.$root.showLoading(true)
                    let rs = await this.$root.context.get(
                        "/web-cabman/bando_tuyencot/lay_ds_quan_huyen/" + this.diachi.tinh_id
                    )
                    this.dsQuan = rs.data
                    if(this.diachi.quan_id && this.dsQuan)
                        this.tenquan = this.dsQuan.filter(
                            (x) => x.QUAN_ID == this.diachi.quan_id
                        )[0].TEN_QUAN;
                    else if((!this.diachi.quan_id || this.diachi.quan_id<=0) && this.dsQuan) {
                        this.diachi.quan_id = this.dsQuan[0].QUAN_ID;
                        this.tenquan = this.dsQuan[0].TEN_QUAN;
                    }
                    this.onChangeQuan();
                } catch (error) {
                } finally {
                    this.$root.showLoading(false);
                }
            },
            getPhuong: async function () {
                try {
                    this.$root.showLoading(true);
                    let rs = await this.$root.context.get(
                        "/web-cabman/bando_tuyencot/lay_ds_phuong_xa/" + this.diachi.quan_id
                    )
                    //console.log(rs.data)
                    this.dsPhuong = rs.data
                    if(this.diachi.phuong_id && this.dsPhuong){
                        var listphuong = this.dsPhuong.filter(
                            x => x.PHUONG_ID == this.diachi.phuong_id);
                        if(listphuong && listphuong.length>0)
                            this.tenphuong = listphuong[0].TEN_PHUONG;
                        else if(this.dsPhuong && this.dsPhuong.length>0) {
                            this.diachi.phuong_id = this.dsPhuong[0].PHUONG_ID;
                            this.tenphuong = this.dsPhuong[0].TEN_PHUONG;
                        }
                    }
                    else if((!this.diachi.phuong_id || this.diachi.phuong_id<=0) && this.dsPhuong) {
                        this.diachi.phuong_id = this.dsPhuong[0].PHUONG_ID;
                        this.tenphuong = this.dsPhuong[0].TEN_PHUONG;
                    }
                    this.onChangePhuong();
                } catch (error) {
                    console.log(error)

                } finally {
                    this.$root.showLoading(false)
                }
            },
            getPhoApKhu: async function () {
                try {
                    this.$root.showLoading(true);
                    let rs = await this.$root.context.get(
                        "/web-cabman/bando_tuyencot/lay_ds_pho_ap_khu/" +
                        this.diachi.phuong_id
                    );

                    // console.log(rs)
                    if (rs.data !== null) {
                        this.dsPho = rs.data.dsPho;
                        if(this.diachi.pho_id && this.dsPho) {
                            let f = this.dsPho.find(x => x.PHO_ID === this.diachi.pho_id) 
                            if (f) this.tenpho = f.TEN_PHO
                            else this.tenpho = null
                        }

                        this.dsAp = rs.data.dsAp;
                        if(this.diachi.ap_id && this.dsAp) {
                            let f = this.dsAp.find(x => x.PHO_ID === this.diachi.ap_id) 
                            if (f) this.tenap = f.TEN_PHO
                            else this.tenap = null
                        }

                        this.dsKhu = rs.data.dsKhu;

                        if(this.diachi.khu_id && this.dsKhu) {
                            let f = this.dsAp.find(x => x.PHO_ID === this.diachi.khu_id) 
                            if (f) this.tenkhu = f.TEN_PHO
                            else this.tenkhu = null
                        }
                    }
                    // console.log(this.dsPho, this.dsAp, this.dsKhu)                            
                } catch (error) {
                    console.log(error)
                } finally {
                    this.$root.showLoading(false);
                }
            },
            getDacDiem: async function () {
                try {
                    this.$root.showLoading(true);
                    let rs = await this.$root.context.get(
                        "/web-cabman/bando_tuyencot/lay_ds_dacdiem",
                        {
                            phuong_id: this.phuong_id == null ? 0 : this.phuong_id,
                            pho_id: this.pho_id == null ? 0 : this.pho_id,
                            ap_id: this.ap_id == null ? 0 : this.ap_id,
                            khu_id: this.khu_id == null ? 0 : this.khu_id,
                        }
                    );
                    this.dsDacDiem = rs.data;
                } catch (error) {
                } finally {
                    this.$root.showLoading(false);
                }
            },
            savediachi: async function () {
                try {
                    this.$root.showLoading(true);
                } catch (error) {
                    this.$toast.error("Lưu ảnh thất bại");
                } finally {
                    this.$root.showLoading(false);
                }
            },
            onChangeTinh: async function () {
                await this.getQuan();
                this.tentinh = this.dsTinh.filter(
                    (x) => x.TINH_ID == this.diachi.tinh_id
                )[0].TENTINH;

                this.isStrictMode = this.diachi.tinh_id === this.$auth.getPhanVungID();
            },
            onChangeQuan: async function () {
                await this.getPhuong();
                this.tenquan = this.dsQuan.filter(
                    (x) => x.QUAN_ID == this.diachi.quan_id
                )[0].TEN_QUAN;
            },
            onChangePhuong: async function () {
                console.log('onChangePhuong')
                await this.getPhoApKhu();
                this.tenphuong = this.dsPhuong.filter(
                    x => x.PHUONG_ID == this.diachi.phuong_id)[0].TEN_PHUONG;
            },
            onChangePho: async function () {
                await this.getDacDiem();
                this.tenpho = this.dsPho.filter(
                    (x) => x.PHO_ID == this.diachi.pho_id
                )[0].TEN_PHO;
            },
            onChangeKhu: async function () {
                await this.getDacDiem();
                this.tenkhu = this.dsKhu.filter(
                    (x) => x.PHO_ID == this.diachi.khu_id
                )[0].TEN_PHO;
            },
            onChangeAp: async function () {
                await this.getDacDiem();
                this.tenap = this.dsAp.filter(
                    (x) => x.PHO_ID == this.diachi.ap_id
                )[0].TEN_PHO;
            },
            chapnhan: function () {
                if (this.isPho && this.tenpho == "")
                    return this.$toast.error("Hãy chọn Đường/Phố!");
                if (this.isAp && this.tenap == "")
                    return this.$toast.error("Hãy chọn Ngõ/Ấp!");
                if (this.isKhu && this.tenkhu == "")
                    return this.$toast.error("Hãy chọn Khu!");

                if (
                    this.isStrictMode &&
                    ((!this.isPho && !this.isAp && !this.isKhu) ||
                        (this.isPho && this.tenpho == "") ||
                        (this.isAp && this.tenap == "") ||
                        (this.isKhu && this.tenkhu == ""))
                ) {
                    this.$toast.error("Hãy chọn Phố / Ấp / Khu!");
                    return;
                }
                
                let dc = {
                    diachi_id: this.diachi.diachi_id ? this.diachi : 0,
                    tinh_id: this.diachi.tinh_id ? this.diachi.tinh_id : 0,
                    quan_id: this.diachi.quan_id ? this.diachi.quan_id : 0,
                    phuong_id: this.diachi.phuong_id ? this.diachi.phuong_id : 0,
                    pho_id: this.isPho && this.diachi.pho_id ? this.diachi.pho_id : 0,
                    ap_id: this.isAp && this.diachi.ap_id ? this.diachi.ap_id : 0,
                    khu_id: this.isKhu && this.diachi.khu_id ? this.diachi.khu_id : 0,
                    dacdiem_id: this.diachi.dacdiem_id && this.isDacDiem ? this.diachi.dacdiem_id : 0,
                    so_nha: this.diachi.so_nha,
                    daydiachi: this.diachi.daydiachi,
                    diachimoi: this.diachi.diachimoi,
                }
                console.log("địa chỉ: ", dc)

                this.$emit("xacnhan", dc);
                this.$bvModal.hide(this.modalId);
            },
        },
        data: function () {
            return {
                diachi: {
                    diachi_id: 0,
                    tinh_id:26 ,
                    quan_id: 0,
                    phuong_id: 0,
                    pho_id: 0,
                    ap_id: 0,
                    khu_id: 0,
                    dac_diem_id: 0,
                    so_nha: "",
                    daydiachi: "",
                    diachimoi: "",
                },
                dsTinh: [],
                dsQuan: [],
                dsPhuong: [],
                dsPho: [],
                dsAp: [],
                dsKhu: [],
                dsDacDiem: [],
                isPho: false,
                isAp: false,
                isKhu: false,
                isDacDiem: false,
                // isStrictMode: true,
                tentinh: "",
                tenquan: "",
                tenphuong: "",
                tenpho: "",
                tenap: "",
                tenkhu: "",
                fieldsTinh: { text: 'TENTINH', value: 'TINH_ID' },
            };
        },
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
