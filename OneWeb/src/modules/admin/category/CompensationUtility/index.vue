<template>
    <div>
        <vue-bread-crumb :header="header" />
        <vue-nav>
            <ul class="list">
                <li
                @click="ghilai"
                >
                <a> <span class="icon one-save"></span>Ghi lại </a>
                </li>
                <li
                @click="xoatienichthulao"
                >
                <a> <span class="icon one-trash"></span>Xóa </a>
                </li>
            </ul>
        </vue-nav>
        <div class="page-content">
            <div class="row">
                <div class="col-sm-6 col-12">
                    <div class="box-form">
                        <div class="legend-title">Thông tin chức năng</div>
                        <div class="info-row">
                                    <div class="key w80">Thông tin</div>
                                    <div class="value">
                                        <div class="select-custom">
                                        <SelectExt v-model="form.thongtin" :allowFiltering="true" dataTextField="noidung"
                                                dataValueField="table_id" :dataSource="cbothongtin" @change="onchangeThongTin"></SelectExt>
                                        </div>
                                    </div>
                        </div>
                        <div class="row">
                            <div class="col-sm-6 col-12">
                                <div class="info-row">
                                    <div class="key w80">Kỳ HĐ cũ</div>
                                    <div class="value">
                                        <div class="select-custom">
                                        <SelectExt v-model="form.kyhopdongcu" :allowFiltering="true" dataTextField="chukyno"
                                                dataValueField="id" :dataSource="cbokyhopdongcu" @change="onchangeTBD" ></SelectExt>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="col-sm-6 col-12">
                                <div class="info-row">
                                    <div class="key w80">Kỳ HĐ mới</div>
                                    <div class="value">
                                        <div class="select-custom">
                                        <SelectExt v-model="form.kyhopdongmoi" :allowFiltering="true" dataTextField="chukyno"
                                                dataValueField="id" :dataSource="cbokyhopdongmoi"></SelectExt>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-sm-6 col-12">
                            <div class="box-form">
                                <div class="legend-title">
                                    Loại nhân viên đã gán
                                </div>
                                <div class="table-content">
                                    <ejs-grid
                                    v-bind:dataSource="gridloainhanvien"
                                    class="no-header"
                                    ref="loainhanvien"
                                    >
                                    <e-columns>
                                        <e-column
                                        field="ten"
                                        headerText=""
                                        textAlign="Left"
                                        width="auto"
                                        ></e-column>
                                    </e-columns>
                                    </ejs-grid>

                                </div>
                            </div>
                        </div>
                        <div class="col-sm-6 col-12">
                            <div class="box-form">
                                <div class="legend-title">
                                    Khu vực đã gán
                                </div>
                                <div class="table-content">
                                    <ejs-grid
                                    v-bind:dataSource="gridkhuvuc"
                                    class="no-header"
                                    ref="khuvucdagan"
                                    >
                                    <e-columns>
                                        <e-column
                                        field="ten_kv"
                                        headerText=""
                                        textAlign="Left"
                                        width="auto"
                                        ></e-column>
                                    </e-columns>
                                    </ejs-grid>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="col-sm-6 col-12">
                    <div class="box-form">
                        <div class="legend-title">
                            Nội dung
                        </div>
                        <div class="table-content">
                            <DataGrid :columns="collumns_noidung" ref="noidung" :dataSource="options.gridnoidung" :enable-paging-server="false"
                            :allowPaging="true" :showFilter="true">
                            </DataGrid>
                        </div>
                    </div>
                   
                </div>
            </div>
        </div>
    </div>
</template>
<script>
import state from './define.js'

export default {
    data() {
        return {
            ...state
        }
    },
    created() {
        this.getComboThongTin()
    },
    methods: {
        onchangeThongTin() {
            this.getcboHopDong(0)
            this.getcboHopDong(1)
        },
        onchangeTBD() {
            this.getThongTinHopDongCu()
        },
        async ghilai() {
            try {
                if(!this.KiemTraDuLieu()) {
                    return false;
                }
                let res = await this.axios.post("web-quantri/tienichthulao/sp_ghilai_tienich_thulao", {
                    "p_id": this.form.thongtin,
                    "p_ky_hd_cu": this.form.kyhopdongcu,
                    "p_ky_hd_moi": this.form.kyhopdongmoi
                });
                if(res.data.data == 0) {
                    this.$toast.success("Cập nhật thành công!")
                } else {
                    this.$toast.error(res.data.data) 
                    this.$loading(false);
                }
                this.getcboHopDong(0)
                this.getcboHopDong(1)  
            } catch (err) {
                this.$toast.error("Lỗi!")
                this.$loading(false)
            }
        },
        async getComboThongTin() {
            try {
                let data = await this.axios.get("web-quantri/tienichthulao/fn_lay_ds_bang")
                this.cbothongtin = data.data.data
                if(this.cbothongtin.length > 0) {
                    this.form.thongtin = this.cbothongtin[0].TABLE_ID
                }
            } catch(err) {
                this.$toast.error("Lỗi!");
            }
        },
        async getcboHopDong(loai) {
            try {
                this.loading(true)
                let data = await this.axios.post("web-quantri/tienichthulao/sp_lay_ds_ky_hd_gan_table",{
                    "id": this.form.thongtin
                })
                if(loai == 1){
                    this.cbokyhopdongmoi = data.data.data.chuagan
                    if (this.cbokyhopdongmoi.length > 0)  {
                        this.form.kyhopdongmoi = this.cbokyhopdongmoi[0].id
                    }
                }
                if(loai == 0) {
                    this.cbokyhopdongcu = data.data.data.dagan
                    if (this.cbokyhopdongcu.length > 0) {
                        this.form.kyhopdongcu = this.cbokyhopdongcu[0].id 
                    }
                }
                this.getThongTinHopDongCu()
                this.loading(false)
            } catch (err) {
                this.loading(false)
                this.$toast.error("Lỗi!")
            }
        },
        async getThongTinHopDongCu() {
            if (!this.form.thongtin == "" && !this.form.kyhopdongcu == "") {
                try {                    
                    this.loading(true)
                    let data = await this.axios.post("web-quantri/tienichthulao/sp_tt_by_ky_hd_old", {
                    "ky_hd": this.form.kyhopdongcu,
                    "id": this.form.thongtin
                    })
                   console.log("ky_dh, id", this.form.kyhopdongcu, this.form.thongtin);
                    this.options.gridnoidung = data.data.data.ds_noidung
                    if (this.options.gridnoidung != null) {
                        this.options.gridnoidung = [...this.options.gridnoidung] 
                    }
                    let gridloainhanviendagan = data.data.data.ds_loainv
                    if(gridloainhanviendagan != null) {
                        this.gridloainhanvien = gridloainhanviendagan
                    }                    

                    let gridkhuvucdagan = data.data.data.ds_kv
                    if (gridkhuvucdagan != null) {
                        this.gridkhuvuc = gridkhuvucdagan
                    }
                    
                    this.loading(false);
                } catch(err) {
                    this.loading(false)
                    this.$toast.error("Lỗi!")
                }
            }
            
        },

        KiemTraDuLieu() {
            if (this.form.kyhopdongcu == "") {
                this.$toast.warning("Bạn chưa chọn kỳ hóa đơn cũ!")
                return false;
            }
            if(this.form.kyhopdongmoi == "") {
                this.$toast.warning("Bạn chưa chọn kỳ hóa đơn mới!")
                return false;
            }
            return true;
        },
        xoatienichthulao(){
            if (this.form.kyhopdongcu == "") {
                this.$toast.warning("Bạn chưa chọn kỳ hóa đơn để xóa!")
                return false;
            }
            this.$confirm("Bạn chắc chắn muốn xóa nội dung theo kỳ hóa đơn này không?", {
                confirmButtonText: "Có",
                cancelButtonText: "Không",
                type: "warning",                
            }).then(async () => {
                try {
                   let res =  await this.axios.post("web-quantri/tienichthulao/sp_xoa_tienich_thulao", {
                        "ky_hd": this.form.kyhopdongcu,
                        "id": this.form.thongtin
                    }
                    );
                    if (res.data.data == 0) {
                        this.$toast("xóa thành công!")
                    }
                    this.getcboHopDong(0)
                    this.getcboHopDong(1)
                } catch (err) {
                    this.$toast.error("Lỗi khi xóa!")
                }
            });
        },
    },
}
</script>
<style>
.no-header .e-columnheader {
  display: none;
}
</style>
