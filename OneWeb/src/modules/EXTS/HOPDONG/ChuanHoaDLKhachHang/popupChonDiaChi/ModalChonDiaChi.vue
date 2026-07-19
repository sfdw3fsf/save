<template src="./ModalChonDiaChi.html"></template>
<script>
    import ModalChonToaDo from '@/modules/CABMAN/XetDuyetPhanTuMangHoDanThuThap/ChonToaDo'
    export default {
        name: "ModalChonDiaChi",
        props: ["modalId", "data","isStrictMode"],
        created: async function () {
            this.dsTinh=[]
            this.diachi.tinh_id = null
        },
        components: { ModalChonToaDo },
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

            getPosition () {
                return {
                    lat: parseFloat(this.data ? this.data.lat : this.diachi.lat),
                    lng: parseFloat(this.data ? this.data.lng : this.diachi.lng)
                }
            },
        },
        watch: {
        },
        methods: {
            onModalHidden() {
            },
            LowerCasePropertyList(obj) {
                return obj.map( function( item ){
                    for(var key in item){
                        var upper = key.toLowerCase();
                        // check if it already wasn't uppercase
                        if( upper !== key ){
                            item[ upper ] = item[key];
                            delete item[key];
                        }
                    }
                    return item;
                });
            },
            focusMyElement: async function () {
                this.isPho = false;
                this.isAp = false;
                this.isKhu = false;
                this.diachi.pho_id = null;
                this.diachi.ap_id = null;
                this.diachi.khu_id = null;
                this.tenpho = '';
                this.tenap = '';
                this.tenkhu = '';
                await this.getTinh();
                if(this.data){
                    if (this.data.tieuDe) {
                        this.tieuDe = this.data.tieuDe
                    }
                    let tsdiachi = Object.assign({}, this.data);
                    tsdiachi = this.LowerCasePropertyList([tsdiachi])[0];
                    this.diachi.daydiachi = tsdiachi.diachi
                    if (tsdiachi.tinh_id && tsdiachi.tinh_id != 0) {
                        this.diachi.tinh_id = tsdiachi.tinh_id;
                        this.tentinh = this.dsTinh.filter(
                            (x) => x.tinh_id == this.diachi.tinh_id
                        )[0].tentinh;

                        this.diachi.quan_id = tsdiachi.quan_id;
                        await this.getQuan();

                        this.diachi.phuong_id=tsdiachi.phuong_id;
                        await this.getPhuong();
                        if (tsdiachi.phuong_id !== 0){
                            if(tsdiachi.pho_id && tsdiachi.pho_id!=0) {
                                this.diachi.pho_id=tsdiachi.pho_id;
                                this.isPho=true}

                            if(tsdiachi.ap_id && tsdiachi.ap_id!=0) {
                                this.diachi.ap_id=tsdiachi.ap_id;
                                this.isAp=true}

                            if(tsdiachi.khu_id && tsdiachi.khu_id!=0){
                                this.diachi.khu_id=tsdiachi.khu_id;
                                this.isKhu=true}
                            await this.getPhoApKhu();
                        }
                        this.diachi.so_nha = tsdiachi.so_nha;
                        this.diachi.daydiachi = tsdiachi.diachi;
                        this.diachi.diachimoi = tsdiachi.diachi;

                    }else{
                        if(this.dsTinh && this.dsTinh.length>0)

                            this.diachi.tinh_id = this.$auth.getPhanVungID() /*this.dsTinh[0].tinh_id;*/
                        this.tentinh = this.dsTinh.filter(
                            (x) => x.tinh_id == this.diachi.tinh_id
                        )[0].tentinh;
                        await  this.getQuan();
                    }
                }else{
                    if(this.dsTinh && this.dsTinh.length>0)
                        this.diachi.tinh_id = this.dsTinh[0].tinh_id;
                    this.tentinh = this.dsTinh.filter(
                        (x) => x.tinh_id == this.diachi.tinh_id
                    )[0].tentinh;
                    await  this.getQuan();
                }
            },

            getTinh: async function () {
                try {
                    this.$root.showLoading(true);
                    let rs = await this.$root.context.get(
                        "/web-toanha/danhmuc-duan/sp_lay_ds_tinh_kn"
                    );
                    this.dsTinh = rs.data.filter(e => e.tentinh != null)

                } catch (error) {
                    console.log(error)
                } finally {
                    this.$root.showLoading(false);
                }
            },
            getQuan: async function () {
                try {
                    this.$root.showLoading(true);
                    var rs = await this.$root.context.get(
                        "/web-cabman/bando_tuyencot/lay_ds_quan_huyen/" + this.diachi.tinh_id
                    );
                    this.dsQuan = rs.data;
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
                    var rs = await this.$root.context.get(
                        "/web-cabman/bando_tuyencot/lay_ds_phuong_xa/" + this.diachi.quan_id
                    );
                    this.dsPhuong = rs.data;
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
                    this.$root.showLoading(false);
                }
            },
            getPhoApKhu: async function () {
                try {
                    this.$root.showLoading(true);
                    var rs = await this.$root.context.get(
                        "/web-cabman/bando_tuyencot/lay_ds_pho_ap_khu/" +
                        this.diachi.phuong_id
                    );
                    if (rs.data != null) {
                        this.dsPho = rs.data.dsPho;
                        if(this.diachi.pho_id && this.dsPho)
                            this.tenpho = this.dsPho.filter(
                                (x) => x.PHO_ID == this.diachi.pho_id
                            )[0].TEN_PHO;
                        this.dsAp = rs.data.dsAp;
                        if(this.diachi.ap_id && this.dsAp)
                            this.tenap = this.dsAp.filter(
                                (x) => x.PHO_ID == this.diachi.ap_id
                            )[0].TEN_PHO;
                        this.dsKhu = rs.data.dsKhu;
                        if(this.diachi.khu_id && this.dsKhu)
                            this.tenkhu = this.dsKhu.filter(
                                (x) => x.PHO_ID == this.diachi.khu_id
                            )[0].TEN_PHO;
                    }
                } catch (error) {
                } finally {
                    this.$root.showLoading(false);
                }
            },
            getDacDiem: async function () {
                try {
                    this.$root.showLoading(true);
                    var rs = await this.$root.context.get(
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
            saveDiachi: async function () {
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
                    (x) => x.tinh_id == this.diachi.tinh_id
                )[0].tentinh;

                this.isStrictMode = this.diachi.tinh_id === this.$auth.getPhanVungID();
            },
            onChangeQuan: async function () {
                await this.getPhuong();
                this.tenquan = this.dsQuan.filter(
                    (x) => x.QUAN_ID == this.diachi.quan_id
                )[0].TEN_QUAN;
            },
            onChangePhuong: async function () {
                await this.getPhoApKhu();
                var listphuong = this.dsPhuong.filter(x => x.PHUONG_ID == this.diachi.phuong_id);
                if(listphuong && listphuong.length>0)
                    this.tenphuong = listphuong[0].TEN_PHUONG;
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

                    longtitude: this.diachi.longtitude ? this.diachi.longtitude : 0,
                    lattitude: this.diachi.lattitude ? this.diachi.lattitude : 0,
                }
                this.$emit("xacnhan", dc);
                this.$bvModal.hide(this.modalId);
            },
            XacNhanToaDo (position) {
                this.diachi.lat = position.lat;
                this.diachi.lng = position.lng;
            },
            onViTri() {
                this.$refs.chonToaDoModal.positionKetCuoi = this.getPosition;
            }
        },
        data: function () {
            return {
                tieuDe: "Chọn địa chỉ",
                diachi: {
                    diachi_id: null,
                    tinh_id:null ,
                    quan_id: null,
                    phuong_id: null,
                    pho_id: null,
                    ap_id: null,
                    khu_id:null,
                    dac_diem_id: null,
                    so_nha: "",
                    daydiachi: "",
                    diachimoi: "",
                    lat:0,
                    lng:0,
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
                tentinh: "",
                tenquan: "",
                tenphuong: "",
                tenpho: "",
                tenap: "",
                tenkhu: "",
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
