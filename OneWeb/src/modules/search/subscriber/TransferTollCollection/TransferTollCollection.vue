<template src="./TransferTollCollection.html"></template>
<style scoped src="./TransferTollCollection.scss"></style>

<script>
    import gridView from '@/modules/prepaid/ChoosePreapaidNumber/components/gridview.vue'
    import '@/assets/vendor/jquery/split'
    import api from './TransferTollCollectionAPI'
    import moment from 'moment'
    export default {
        components: {
            gridView,
            api,
        },
        name: 'TransferTollCollection',
        mounted () {
            Split(['#boxLeft', '#boxRight'], {
                sizes: [50, 50],
                gutterSize: 16,
                onDragEnd: function (sizes) {
                },
            });
        },
        data() {
            return {
                chkApDungHT: false,
                loai_nv_old: 25,
                loai_nv_new: 25,
                selDonViQL_Old: 0,
                selDonViQL_New: 0,
                ds_DonViQL_Old: [],
                ds_DonViQL_New: [],
                selLoaiTuyen_Old: 0,
                selLoaiTuyen_New: 0,
                ds_LoaiTuyen_Old: [],
                ds_LoaiTuyen_New: [],
                selNhanVien_Old: 0,
                selNhanVien_New: 0,
                message: "",
                DsNhanVien_Old: {
                    data: [],
                    textField: "TEN_NV",
                    valueField: "NHANVIEN_ID",
                    panelDataHeight: "auto",
                    headers: [
                        {
                            fieldName: "MA_NV",
                            headerText: "Mã nhân viên",
                            allowFiltering: true,
                            allowSorting: false
                        },
                        {
                            fieldName: "TEN_NV",
                            headerText: "Tên nhân viên",
                            allowFiltering: true,
                            allowSorting: false
                        },
                    ],
                },
                DsNhanVien_New: {
                    data: [],
                    textField: "TEN_NV",
                    valueField: "NHANVIEN_ID",
                    panelDataHeight: "auto",
                    headers: [
                        {
                            fieldName: "MA_NV",
                            headerText: "Mã nhân viên",
                            allowFiltering: true,
                            allowSorting: false
                        },
                        {
                            fieldName: "TEN_NV",
                            headerText: "Tên nhân viên",
                            allowFiltering: true,
                            allowSorting: false
                        },
                    ],
                },
                DsTuyenThu: {
                    headers: [
                        { fieldName: "TUYENTHU_ID", headerText: "Tuyến ID", allowFiltering: true },
                        { fieldName: "MA_TUYEN", headerText: "Mã tuyến", allowFiltering: true },
                        { fieldName: "TENTUYEN", headerText: "Tên tuyến", allowFiltering: true },
                        { fieldName: "GHICHU", headerText: "Ghi chú", allowFiltering: true },
                    ],
                    data: [],
                    selected: 0,
                    checked: [],
                },
            };
        },
        async created() {
            this.Loading = true;
            try {
                Promise.all([ await this.getDsDonViQL(), await this.getDsLoaiTuyen(),
                             await this.getDsNhanVien_Old(this.loai_nv_old,this.selDonViQL_Old), await this.getDsNhanVien_New(this.loai_nv_new,this.selDonViQL_New),
                            ]);
                this.Loading = false;
            } catch (ex) {
                this.Loading = false;
            }
            this.Loading = false;
        },
        methods:{
            btnGhiLai: async function (){
                this.Loading = true;
                var checked = await this.validated();
                if (checked) {
                    await this.save_exe();
                }
                this.Loading = false;
            },
            validated: async function () {
                var data = this.DsTuyenThu.data.filter((x) => x.ischecked);
                if(this.selNhanVien_New == null)
                {
                    this.ShowWarning("Hãy chọn nhân viên mới");
                    return false;
                }
                if(this.selNhanVien_Old == this.selNhanVien_New)
                {
                    this.ShowWarning("Nhân viên mới không thể trùng với nhân viên cũ");
                    return false;
                }
                if(data.length <= 0)
                {
                    this.ShowWarning("Hãy chọn tuyến thu cần chuyển");
                    return false;
                }
                return true;
            },
            save_exe: async function () {
                await Promise.all(this.DsTuyenThu.checked.map(async (item) => {
                    var data = {
                            "tuyenthu_id": item,
                            "nhanvientc_id": this.selNhanVien_New,
                            "kyhoadon": moment(new Date()).subtract(1, 'months').format('YYYYMM') + "01",
                            "tucthi": this.chkApDungHT ? 1 : 0,
                            "may_cn": this.$root.token.getMaNhanVien(),
                            "nguoi_cn": this.$root.token.getUserName(),
                            "ip_cn": this.$root.token.getNhanVienID()
                        };
                    var input = {
                        "p_ds_para": JSON.stringify(data)
                    };
                    let response = await api.save_chuyentuyenthu(this.axios, input);
                    if (response.data.error === 200 || response.data.error === "200") {
                            var data = this.DsTuyenThu.data.filter((x) => x.TUYENTHU_ID == item);
                            var data_nv_old = this.DsNhanVien_Old.data.filter((x) => x.NHANVIEN_ID == this.selNhanVien_Old);
                            var data_nv_new = this.DsNhanVien_New.data.filter((x) => x.NHANVIEN_ID == this.selNhanVien_New);
                            this.message = this.message + "Chuyển thành công tuyến thu: " + data[0].TENTUYEN + " ( " + data_nv_old[0].TEN_NV + " ) => " + data_nv_new[0].TEN_NV + "\n";
                    } else {
                        // this.ShowError(response.data.message);
                    }
                }));
                if(this.message.trim() != "")
                    this.ShowSuccess(this.message);
                this.message = "";
                await this.getDsTuyenThu(this.selNhanVien_Old, this.selLoaiTuyen_Old); 
            },
            getDsDonViQL: async function () {
                let data = this.GetData(await api.get_ds_dvql_loaituyen(this.axios));
                if (data && data.length > 0) {
                    this.ds_DonViQL_Old = JSON.parse(data).DS_DONVI;
                    this.selDonViQL_Old = this.ds_DonViQL_Old[0].DONVI_ID;
                    this.ds_DonViQL_New = JSON.parse(data).DS_DONVI;
                    this.selDonViQL_New = this.ds_DonViQL_New[0].DONVI_ID;
                } else{
                }
            },
            getDsLoaiTuyen: async function () {
                let data = this.GetData(await api.get_ds_dvql_loaituyen(this.axios));
                if (data && data.length > 0) {
                    this.ds_LoaiTuyen_Old = JSON.parse(data).DS_KIEU_TUYENTHU;
                    this.selLoaiTuyen_Old = this.ds_LoaiTuyen_Old[0].KIEU;
                    this.ds_LoaiTuyen_New = JSON.parse(data).DS_KIEU_TUYENTHU;
                    this.selLoaiTuyen_New = this.ds_LoaiTuyen_New[0].KIEU;
                } else{
                }
                
            },
            getDsNhanVien_Old: async function (loai, donvi) {
                var input = {
                    "p_ds_para": "{ loainv_id: " + loai + ", donvi_dl_id: " + donvi + " }"
                }
                let data = this.GetData(await api.get_ds_nhanvien(this.axios, input));
                if (data && data.length > 0) {
                    this.DsNhanVien_Old.data = JSON.parse(data).DS_NHANVIEN;
                } else{
                }
            },
            getDsNhanVien_New: async function (loai, donvi) {
                var input = {
                    "p_ds_para": "{ loainv_id: " + loai + ", donvi_dl_id: " + donvi + " }"
                }
                let data = this.GetData(await api.get_ds_nhanvien(this.axios, input));
                if (data && data.length > 0) {
                    this.DsNhanVien_New.data = JSON.parse(data).DS_NHANVIEN;
                } else{
                }
            },
            getDsTuyenThu: async function (nhanvien, loai) {
                var input = {
                    "p_ds_para": "{ nhanvientc_id: " + nhanvien + ", loaituyen_id: " + loai + " }"
                }
                let data = this.GetData(await api.get_ds_tuyenthu(this.axios, input));
                if (data && data.length > 0) {
                    this.DsTuyenThu.data = JSON.parse(data).DS_TUYENTHU;
                } else{
                    this.DsTuyenThu.data = [];
                }
            },
            select_selectedItemsChangedDsTuyenThu: function (dataKeys) {
                this.DsTuyenThu.checked = dataKeys;
                // console.log(dataKeys);
                this.DsTuyenThu.data.forEach(function (item) {
                    if (dataKeys.filter((x) => x == item.TUYENTHU_ID).length > 0) {
                        item.ischecked = true;
                    } else {
                        item.ischecked = false;
                    }
                });
                // console.log(this.DsTuyenThu.checked);
                // console.log(this.DsTuyenThu.data);
            },
            DV_Old_Changed: async function ()
            {
                this.loai_nv_old = 25;
                if (this.selLoaiTuyen_Old == 2)
                    this.loai_nv_old = 39;
                else if(this.selLoaiTuyen_Old == 3)
                    this.loai_nv_old = 34;
                await this.getDsNhanVien_Old(this.loai_nv_old, this.selDonViQL_Old);
                await this.getDsTuyenThu(this.selNhanVien_Old, this.selLoaiTuyen_Old);
                // await this.getDsNhanVien_Old(1,0);
            },
            LT_Old_Changed: async function ()
            {
                this.loai_nv_old = 25;
                if (this.selLoaiTuyen_Old == 2)
                    this.loai_nv_old = 39;
                else if(this.selLoaiTuyen_Old == 3)
                    this.loai_nv_old = 34;
                await this.getDsNhanVien_Old(this.loai_nv_old, this.selDonViQL_Old);
                await this.getDsTuyenThu(this.selNhanVien_Old, this.selLoaiTuyen_Old);
                // await this.getDsNhanVien_Old(1,0);
            },
            DV_New_Changed: async function ()
            {
                this.loai_nv_new = 25;
                if (this.selLoaiTuyen_New == 2)
                    this.loai_nv_new = 39;
                else if(this.selLoaiTuyen_New == 3)
                    this.loai_nv_new = 34;
                await this.getDsNhanVien_New(this.loai_nv_new, this.selDonViQL_New);
                // await this.getDsNhanVien_New(1,0);
            },
            LT_New_Changed: async function ()
            {
                this.loai_nv_new = 25;
                if (this.selLoaiTuyen_New == 2)
                    this.loai_nv_new = 39;
                else if(this.selLoaiTuyen_New == 3)
                    this.loai_nv_new = 34;
                await this.getDsNhanVien_New(this.loai_nv_new, this.selDonViQL_New);
                // await this.getDsNhanVien_New(1,0);
            },
            NhanVienOld_Selected: async function (value) {
                await this.getDsTuyenThu(this.selNhanVien_Old, this.selLoaiTuyen_Old);
                
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