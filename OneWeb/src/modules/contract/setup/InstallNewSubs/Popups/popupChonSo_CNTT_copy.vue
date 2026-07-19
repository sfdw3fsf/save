<template src="./popupChonSo_CNTT.html">
</template>
<script>
import Select2Ext from "@/modules/contract/setup/DeclareLandline/components/Select2Ext";
import DataGridCustom from "@/modules/contract/setup/DeclareLandline/components/DataGridCustom";
var rs
export default {
    components: {
        Select2Ext,
        DataGridCustom
    },
    data() {
        return {
            vloaitb_id: 0,
            vdichvuvt_id: 0,
            vsomay: "",
            vcuoc_sodep: 0
        }
    },
    methods: {
        async showDialog() {
            this.frmChonSo_CNTT_Load()
            this.$refs.frmChonSo_CNTT.show();
        },
        grvChonSo_FocusedRowChanged()
        {
            let selected = this.$refs.gridChonSo.getSelectedRecords()
            if(selected.length > 0) {
                this.vsomay = selected[0].somay;
                this.vcuoc_sodep = selected[0].cuoc_sodep
            }

        },

        async frmChonSo_CNTT_Load()
        {
            
            rs = await this.axios.post("web-bancheo/danhmuc/kieu_so", {
                tinhthicong_id: 100
            })
            this.$refs.cboKieuSo.HT_COMBOBOX(rs.data.data, "KIEUSO_ID", "KIEU_SO")

            rs = await this.axios.post("web-bancheo/danhmuc/nhom_so", {
                tinhthicong_id: 100
            })
            this.$refs.cboNhomSo.HT_COMBOBOX(rs.data.data, "NHOMSO_ID", "NHOMSO")

            if (this.vsomay)
            {
                this.$refs.txtSoMay.value = this.vsomay;
                this.HT_DS_SOMAY();
            }
        },

        tsbtnTimKiem_Click()
        {
            this.HT_DS_SOMAY();
        },

        tsbtnCapNhat_Click()
        {
            this.isCapNhat = true;
            this.Close();
        },
        Close() {
            this.$refs.frmChonSo_CNTT.hide();
        },

        async tsbtnGiuSo_Click()
        {
            let selected = this.$refs.gridChonSo.getSelectedRecords()
            if(selected.length > 0) {
                this.loading(true);
                var ketqua = await this.$root.context.post("/web-hopdong/lapdatmoi/sp_capnhat_giuso",{
                    "dichvuvt_id": this.dichvuvt_id,
                    "js_somay": JSON.stringify(selected.map(x=>({"SOMAY":x}))),//JSON.stringify(selected),
                    "nguoidung_id": this.$root.token.getNguoiDungID()
                });
                if(ketqua && ketqua.message.toLowerCase() == "success")
                    this.$toast.success(ketqua.message);
                else this.$toast.error(ketqua.message);
                this.loading(false);
                if(rs.data && rs.data.length>0) {
                    this.popupComponent = ()=>import('./popupGiuSo');
                    this.popupComponentName = "popupGiuSo";
                    this.popupComponentData = {"dichvuvt_id": this.dichvuvt_id, "nguoidung_id": this.$root.token.getNguoiDungID()}
                    this.Popup('popupComponents');
                } else this.$toast.error(rs.message+ (rs.message_detail?': '+rs.message_detail:''));
            }

        },

        async HT_DS_SOMAY()
        {

            rs = await this.axios.post("/web-bancheo/danhmuc/lay_ds_khoso_cntt",{
                "kieuso_id": this.$refs.cboKieuSo,
                "loaitb_id": this.vloaitb_id,
                "nguoidung_id": this.$root.token.getNguoiDungID(),
                "nhomso_id": this.$refs.cboNhomSo.value,
                "somay": this.$refs.txtSoMay.value,
                "tinh_id": this.$root.token.getPhanVungID(),
                "tinhthicong_id": 100
            })

        }
    }
}
</script>