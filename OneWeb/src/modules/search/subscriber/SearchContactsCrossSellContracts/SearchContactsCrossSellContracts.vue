<template src="./SearchContactsCrossSellContracts.html"></template>
<style scoped src="./SearchContactsCrossSellContracts.scss"></style>

<script>
    import gridView from '@/modules/prepaid/ChoosePreapaidNumber/components/gridview.vue'
    import breadcrumb from "@/components/breadcrumb"
    import '@/assets/vendor/jquery/split'
    import api from './SearchContactsCrossSellContractsAPI'
    import moment from 'moment'
    import pop_dsbd from './Popups/DSBD.vue'
    export default {
        components: {
            gridView,
            breadcrumb,
            api,
            pop_dsbd
        },
        name: 'SearchContactsCrossSellContracts',
        mounted () {
            Split(['#boxLeft', '#boxRight'], {
                sizes: [50,50],
                gutterSize: 16,
                onDragEnd: function (sizes) {
                },
            });
            this.$refs.keyFocus.focus();
        },
        data() {
            return {
                key: "",
                chkHDDB: false,
                loaihd_bancheo: 0,
                hienthi_tinhban: "",
                hienthi_tinhtc: "",
                tentinh_ban: "",
                tentinh_tc: "",
                tinh_ban: 0,
                tinh_thicong: 0,
                thuebao_id_ban: 0,
                thuebao_id_thicong: 0,
                hdkh_id: 0,
                ds_hd: [],
                count: 0,
                dsTinhBan: {
                    list: [],
                    header: [
                        { fieldName: "loai_tt", headerText: "Thông tin", allowFiltering: true,},
                        { fieldName: "ma", headerText: "Mã", allowFiltering: true,},
                        { fieldName: "ten", headerText: "Tên", allowFiltering: true,},
                        { fieldName: "diachi", headerText: "Địa chỉ", allowFiltering: true,},
                        { fieldName: "trangthai", headerText: "Trạng thái", allowFiltering: true,},
                    ],
                    value: {},
                    isEnabled: true,
                    checked: [],
                },
                dsTinhThiCong: {
                    list: [],
                    header: [
                        { fieldName: "loai_tt", headerText: "Thông tin", allowFiltering: true,},
                        { fieldName: "ma", headerText: "Mã", allowFiltering: true,},
                        { fieldName: "ten", headerText: "Tên", allowFiltering: true,},
                        { fieldName: "diachi", headerText: "Địa chỉ", allowFiltering: true,},
                        { fieldName: "trangthai", headerText: "Trạng thái", allowFiltering: true,},
                    ],
                    value: {},
                    isEnabled: true,
                    checked: [],
                },
                ds_bd:{
                    list: [],
                    header:  [
                        {fieldName:'ma_gd',headerText:'Mã GD',allowFiltering: true},
                        {fieldName:'ngay_yc',headerText:'Ngày yêu cầu',allowFiltering: true, type: "Date", format: "dd/MM/yyyy hh:mm a"},
                        {fieldName:'trangthai_hd',headerText:'Trạng thái',allowFiltering: true},
                        {fieldName:'ten_loaihd',headerText:'Loại hợp đồng',allowFiltering: true},
                        {fieldName:'ten_kieuld',headerText:'Kiếu lắp đặt',allowFiltering: true},
                        {fieldName:'ten_dv',headerText:'Đơn vị lập HĐ/PL',allowFiltering: true}  
                    ],

                },
                index_tinhban: 0,
                index_tinh_thicong: 0,
            };
        },
        methods:{
            keyEnter: async function (){
                if(this.key.trim() != "")
                {
                    this.Loading = true;
                    if(this.chkHDDB)
                    {
                        await this.get_thongtin_bancheo_hd();
                        if(this.count == 0)
                        {
                            this.ShowWarning("Thuê bao này chưa có hợp đồng bán chéo của 2 đơn vị");
                            this.dsTinhBan.list = [];
                            this.dsTinhThiCong.list = [];
                        }
                        else
                        {
                            await this.Hienthi_DS_HOPDONG();
                        }
                        
                    }
                    else
                    {
                        await this.get_thongtin_bancheo_db();
                        if(this.count == 0)
                        {
                            this.ShowWarning("Thuê bao này chưa có trong danh bạ bán chéo của 2 đơn vị");
                            this.dsTinhBan.list = [];
                            this.dsTinhThiCong.list = [];
                        }
                        else
                        {
                            await this.Hienthi_DS_DANHBA();
                        }
                    }
                    this.Loading = false;
                }
            },
            CheckedChanged: async function (){
                if(this.key.trim() != "")
                {
                    this.Loading = true;
                    if(this.chkHDDB)
                    {
                        await this.get_thongtin_bancheo_hd();
                        if(this.count == 0)
                        {
                            this.ShowWarning("Thuê bao này chưa có hợp đồng bán chéo của 2 đơn vị");
                            this.dsTinhBan.list = [];
                            this.dsTinhThiCong.list = [];
                        }
                        else
                        {
                            var input = {
                                "ma_tb": this.key.trim()
                            };
                            let data =  this.GetData(await api.lay_ds_hd_theo_ma_tb(this.axios,input));
                            if (data && data.length > 1) {
                                this.ds_bd.list = data;
                                this.$bvModal.show('pop_dsbd');
                            }else{
                                await this.Hienthi_DS_HOPDONG();
                            }
                        }
                        
                    }
                    else
                    {
                        await this.get_thongtin_bancheo_db();
                        if(this.count == 0)
                        {
                            this.ShowWarning("Thuê bao này chưa có trong danh bạ bán chéo của 2 đơn vị");
                            this.dsTinhBan.list = [];
                            this.dsTinhThiCong.list = [];
                        }
                        else
                        {
                            await this.Hienthi_DS_DANHBA();
                        }
                    }
                    this.Loading = false;
                }
            },
            get_thongtin_bancheo_db: async function () {
                var input = {
                    "vphanvung_id": this.$root.token.getPhanVungID(),
                    "vmatb": this.key.trim()
                };
                let data = this.GetData(await api.get_thongtin_bancheo_db(this.axios, input));
                if (data && data.rs1.length > 0) {
                    this.loaihd_bancheo = data.rs1[0].loaihd_bancheo;
                    this.tinh_ban = data.rs1[0].tinh_ban;
                    this.tinh_thicong = data.rs1[0].tinh_thicong;
                    this.thuebao_id_ban = data.rs1[0].thuebao_id_ban;
                    this.thuebao_id_thicong = data.rs1[0].thuebao_id_thicong;
                    this.hdkh_id = 0;
                    let tinhBan = this.GetData(await api.lay_tinh_thicong(this.axios, {params: { tinhtc_id: this.tinh_ban }}));
                    let tinhTC = this.GetData(await api.lay_tinh_thicong(this.axios, {params: { tinhtc_id: this.tinh_thicong }}));
                    this.tentinh_ban = tinhBan[0].tentinh;
                    this.tentinh_tc = tinhTC[0].tentinh;
                    this.count = data.rs1.length;
                } else{
                    this.loaihd_bancheo = 0;
                    this.tentinh_ban = "";
                    this.tentinh_tc = "";
                    this.hienthi_tinhban = "";
                    this.hienthi_tinhtc = "";
                    this.tinh_ban = 0;
                    this.tinh_thicong = 0;
                    this.thuebao_id_ban = 0;
                    this.thuebao_id_thicong = 0;
                    this.hdkh_id = 0;
                    this.count = 0;
                }
                
            },
            get_thongtin_bancheo_hd: async function () {
                var input = {
                    "vphanvung_id": this.$root.token.getPhanVungID(),
                    "vmatb": this.key.trim()
                };
                let data = this.GetData(await api.get_thongtin_bancheo_hd(this.axios, input));
                if (data && data.rs1.length > 0) {
                    this.ds_hd = data.rs1;
                    this.count = data.rs1.length;
                    this.loaihd_bancheo = data.rs1[0].loaihd_bancheo;
                    this.tinh_ban = data.rs1[0].tinh_ban;
                    this.tinh_thicong = data.rs1[0].tinh_thicong;
                    this.thuebao_id_ban = data.rs1[0].hdtb_ban;
                    this.thuebao_id_thicong = data.rs1[0].hdtb_thicong;
                    this.hdkh_id = data.rs1[0].hdkh_id;
                    let tinhBan = this.GetData(await api.lay_tinh_thicong(this.axios, {params: { tinhtc_id: this.tinh_ban }}));
                    let tinhTC = this.GetData(await api.lay_tinh_thicong(this.axios, {params: { tinhtc_id: this.tinh_thicong }}));
                    this.tentinh_ban = tinhBan[0].tentinh;
                    this.tentinh_tc = tinhTC[0].tentinh;
                    this.count = data.rs1.length;
                } else{
                    this.loaihd_bancheo = 0;
                    this.tentinh_ban = "";
                    this.tentinh_tc = "";
                    this.hienthi_tinhban = "";
                    this.hienthi_tinhtc = "";
                    this.tinh_ban = 0;
                    this.tinh_thicong = 0;
                    this.thuebao_id_ban = 0;
                    this.thuebao_id_thicong = 0;
                    this.hdkh_id = 0;
                    this.count = 0;
                }
                
            },
            Hienthi_DS_DANHBA: async function (){
                this.loading(true);
                var inputBan = {
                    "vphanvung_id": this.tinh_ban,
                    "vthuebaoid": this.thuebao_id_ban
                };
                var inputTC = {
                    "vphanvung_id": this.tinh_thicong,
                    "vthuebaoid": this.thuebao_id_thicong
                };
                let dataBan = this.GetData(await api.tracuu_danhba_hd_bancheo(this.axios, inputBan));
                let dataTC = this.GetData(await api.tracuu_danhba_hd_bancheo(this.axios, inputTC));
                this.dsTinhBan.list = dataBan.rs1;
                this.dsTinhThiCong.list = dataTC.rs1;
                if (this.dsTinhBan.list.length>0)
                {
                    this.$refs.gridTinhBan.setCurrentSelectedRow(this.index_tinhban);
                }
                if (this.dsTinhThiCong.list.length>0)
                {
                    this.$refs.gridTinhThiCong.setCurrentSelectedRow(this.index_tinh_thicong);
                }
                this.hienthi_tinhban = this.tentinh_ban;
                this.hienthi_tinhtc = this.tentinh_tc;
                this.loading(false);
            },
            Hienthi_DS_HOPDONG: async function ()
            {
                this.loading(true);
                var inputBan = {
                    "vphanvung_id": this.tinh_ban,
                    "vhdtbid": this.thuebao_id_ban
                };
                var inputTC = {
                    "vphanvung_id": this.tinh_thicong,
                    "vhdtbid": this.thuebao_id_thicong
                };
                let dataBan = this.GetData(await api.get_ds_hdtb_bancheo(this.axios, inputBan));
                let dataTC = this.GetData(await api.get_ds_hdtb_bancheo(this.axios, inputTC));
                this.dsTinhBan.list = dataBan.rs1;
                this.dsTinhThiCong.list = dataTC.rs1;
                if (this.dsTinhBan.list.length>0)
                {
                    this.$refs.gridTinhBan.setCurrentSelectedRow(this.index_tinhban);
                }
                if (this.dsTinhThiCong.list.length>0)
                {
                    this.$refs.gridTinhThiCong.setCurrentSelectedRow(this.index_tinh_thicong);
                }
                this.hienthi_tinhban = this.tentinh_ban;
                this.hienthi_tinhtc = this.tentinh_tc;
                this.loading(false);
            },
            search_get_dsbd: async function(obj){
                var flag = 0;
                await Promise.all(this.ds_hd.map(async (item) => {
                    if(item.hdkh_id == obj.hdkh_id)
                        {
                            flag++;
                            this.loaihd_bancheo = item.loaihd_bancheo;
                            this.tinh_ban = item.tinh_ban;
                            this.tinh_thicong = item.tinh_thicong;
                            this.thuebao_id_ban = item.hdtb_ban;
                            this.thuebao_id_thicong = item.hdtb_thicong;
                            this.hdkh_id = item.hdkh_id;
                            let tinhBan = this.GetData(await api.lay_tinh_thicong(this.axios, {params: { tinhtc_id: this.tinh_ban }}));
                            let tinhTC = this.GetData(await api.lay_tinh_thicong(this.axios, {params: { tinhtc_id: this.tinh_thicong }}));
                            this.tentinh_ban = tinhBan[0].tentinh;
                            this.tentinh_tc = tinhTC[0].tentinh;
                        }
                }));
                if(flag != 0)
                    this.Hienthi_DS_HOPDONG();
                else
                {
                    this.loaihd_bancheo = 0;
                    this.tentinh_ban = "";
                    this.tentinh_tc = "";
                    this.hienthi_tinhban = "";
                    this.hienthi_tinhtc = "";
                    this.tinh_ban = 0;
                    this.tinh_thicong = 0;
                    this.thuebao_id_ban = 0;
                    this.thuebao_id_thicong = 0;
                    this.hdkh_id = 0;
                    this.count = 0;
                    this.dsTinhBan.list = [];
                    this.dsTinhThiCong.list = [];
                }
            },
            ShowSuccess: function (msg) {
                this.$toast.success(msg);
            },
            ShowWarning: function (msg) {
                this.$toast.warning(msg);
            },
            ShowError: function (msg) {
                this.$toast.error(msg);
            },
            GetData: function (response) {
                if (
                    response.data.error === 200 ||
                    response.data.error === "200" ||
                    response.data.error === 204 ||
                    response.data.error === "204"
                ) {
                    return response.data.data;
                } else {
                    // console.log(response.data.message);
                    this.ShowError(response.data.message);
                }
                return [];
            },
        },
    }
</script>