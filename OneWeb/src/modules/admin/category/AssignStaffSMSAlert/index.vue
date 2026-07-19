<template src="./template.html"></template>
<script>
import DataGridCustom from "@/modules/contract/setup/DeclareLandline/components/DataGridCustom";
export default {
    components: {
        DataGridCustom
    },
    data() {
        return {
            da : {},
            tinnhan_id : 0, nhanvien_id : 0, loaitn_id : 0, captn_id : 0,
            dtTinNhan: [], dtNhanVien: [], dtDonViChuaGan: [], dtDonViDaGan: [],
            nguoi_cn:''
        }
    },
    mounted() {
        this.frmQLTinNhan_Load()
    },
    methods: {
        frmQLTinNhan_Load()
        {
            this.HienThiTinNhan();
        }

        ,async HienThiTinNhan()
        {
            let rs = await this.axios.post("web-quantri/quanlytinnhan/sp_danhsach_tinnhan")
            this.$refs.gridLoaiTN.dataSource = this.dtTinNhan = rs.data.data
            this.nguoi_cn= this.$root.token.getUserName();
        }

        ,async HienThiNhanVien()
        {
            this.$root.loading(true)
            let rs = await this.axios.post("web-quantri/quanlytinnhan/sp_dsnhanvien_theo_tinnhan", {
                "p_tinnhan_id": this.tinnhan_id
            })
            this.$refs.gridNhanVien.dataSource = this.dtNhanVien = [...new Map(rs.data.data .map((item) => [item["ma_nv"], item])).values()]
            
            if (this.dtNhanVien.length > 0)
                this.gridViewNhanVien_FocusedRowChanged(0);

             this.$root.loading(false)
        }

        ,async HienThiDonVi()
        {
            this.$root.$loading = true;
            let rs1 = await this.axios.post("web-quantri/quanlytinnhan/sp_tinnhan_ds_donvi_chuagan", {
                "p_captn_id": this.captn_id,
                "p_nhanvien_id": this.nhanvien_id,
                "p_loaitn_id": this.loaitn_id,
            })

            let rs2 = await this.axios.post("web-quantri/quanlytinnhan/sp_tinnhan_ds_donvi_dagan", {
                "p_captn_id": this.captn_id,
                "p_nhanvien_id": this.nhanvien_id,
                "p_loaitn_id": this.loaitn_id,
            })
            this.$root.loading(false)
            this.$refs.gridHienTai.dataSource = this.dtDonViChuaGan = [...new Map(rs1.data.data.map((item) => [item["donvi_id"], item])).values()]
            this.$refs.gridKeTiep.dataSource = this.dtDonViDaGan =  [...new Map(rs2.data.data.map((item) => [item["donvi_id"], item])).values()]
            //this.$refs.gridNhanVien.dataSource = da.ds_nhanvien_tn(tinnhan_id);
        }

        ,async tsbtnGhiLai_Click()
        {
            try
            {
                let selected = this.$refs.gridKeTiep.dataSource
                let arr = []

                selected.forEach(async (item) => {
                    arr.push({
                        DONVI_ID: item.donvi_id
                    })
                })
            
                this.$root.loading(true)
                await this.axios.post("web-quantri/quanlytinnhan/gan_nhanvien_sms", {
                        "p_donvi_id": arr.length> 0 ? JSON.stringify(arr):"",
                        "p_nhanvien_id": this.nhanvien_id,
                        "p_tinnhan_id": this.tinnhan_id,
                    })
                this.$root.loading(false)
                this.$toast.success("Cập nhật thành công!");
            }
            catch (err)
            {
                this.$root.loading(false)
                console.log(err)
                this.$toast.error(String(err.message));
            }
            
        }

        ,gridviewLoaiTN_FocusedRowChanged(e)
        {
            let selected = this.$refs.gridLoaiTN.getSelectedRecords()
            if (selected.length >= 0)
            {
                
                this.tinnhan_id = selected[0].tinnhan_id
                this.loaitn_id =selected[0].loaitn_id
                this.captn_id = selected[0].captn_id
                this.HienThiNhanVien();
            }
        }

        ,gridViewNhanVien_FocusedRowChanged(i)
        {
            let selected = this.$refs.gridNhanVien.getSelectedRecords()
            if(selected.length > 0) {
                this.nhanvien_id = selected[0].nhanvien_id
                //loaitn_id = Convert.ToInt32(gridviewLoaiTN.GetRowCellValue(i, "LOAITN_ID"));
                this.HienThiDonVi();
            }
        }

        ,chkNhanVien_Click()
        {
            //labelFunctionTitle.Focus();
        }

        ,tsbtnThoat_Click()
        {
            Close();
        }

        ,btnNext_Click()
        {
            this.dtDonViChuaGan   = this.$refs.gridHienTai.dataSource
            this.dtDonViDaGan = this.$refs.gridKeTiep.dataSource
            let selected = this.$refs.gridHienTai.getSelectedRecords()
            selected.forEach(item => 
            {
                this.dtDonViDaGan.push(item);
                console.log(item)
                let index = this.dtDonViChuaGan.findIndex(key => key === item);
                this.dtDonViChuaGan.splice(index, 1)
            })
            this.$refs.gridHienTai.dataSource = [...this.dtDonViChuaGan];
            this.$refs.gridKeTiep.dataSource = [...this.dtDonViDaGan];
        }

        ,repositoryItemCheckEdit1_Click()
        {
            //labelFunctionTitle.Focus();
        }

        ,repositoryItemCheckEdit3_Click()
        {
            //labelFunctionTitle.Focus();
        }

        ,repositoryItemCheckEdit1_CheckedChanged()
        {
            //labelFunctionTitle.Focus();
        }

        ,chkNhanVien_CheckStateChanged()
        {

        }

        ,chkNhanVien_CheckedChanged()
        {
            //labelFunctionTitle.Focus();
        }

        ,btnNextAll_Click()
        {
            let selected = this.$refs.gridHienTai.dataSource
            this.dtDonViChuaGan   = this.$refs.gridHienTai.dataSource
            this.dtDonViDaGan = this.$refs.gridKeTiep.dataSource

            selected.forEach(item =>
            {
                this.dtDonViDaGan.push(item);
            })
            this.$refs.gridHienTai.dataSource = [];
            this.$refs.gridKeTiep.dataSource = [...this.dtDonViDaGan];
        }

        ,btnPrevie_Click()
        {
            this.dtDonViChuaGan   = this.$refs.gridHienTai.dataSource
            this.dtDonViDaGan = this.$refs.gridKeTiep.dataSource
            let selected = this.$refs.gridKeTiep.getSelectedRecords()
            selected.forEach(item =>
            {
                
                this.dtDonViChuaGan.push(item);
                let index = this.dtDonViDaGan.findIndex(key => key === item);
                this.dtDonViDaGan.splice(index, 1);
            })
            this.$refs.gridHienTai.dataSource = [...this.dtDonViChuaGan];
            this.$refs.gridKeTiep.dataSource = [...this.dtDonViDaGan];
        }

        ,btnPrevieAll_Click()
        {
            this.dtDonViChuaGan   = this.$refs.gridHienTai.dataSource
            this.dtDonViDaGan = this.$refs.gridKeTiep.dataSource
            let selected = this.$refs.gridKeTiep.dataSource
            selected.forEach(item =>
            {
                this.dtDonViChuaGan.push(item);
            })
            this.$refs.gridHienTai.dataSource = [...this.dtDonViChuaGan];
            this.$refs.gridKeTiep.dataSource = [];
        }
    }
}
</script>