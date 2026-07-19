<template>
<div class="modal-content popup-box">
    <div class="popup-header">
        <div class="title"><span class="icon one-notepad"></span>Trọn gói báo hiệu quốc tế</div>
        <div class="close -ap icon-close hidden" data-dismiss="modal" @click.prevent="closeForm"></div>
    </div>
    <div class="list-actions-top">
        <ul class="list">
            <li>
                <a href="#" @click.prevent="btnGhiLai_Click">
                    <span class="icon nc-icon-glyph ui-1_check-circle-08"></span> Ghi lại
                </a>
            </li>
            <li>
                <a href="#" @click.prevent="btnXoa_Click">
                    <span class="icon ui-1_circle-remove nc-icon-glyph"></span> Xoá
                </a>
            </li>
        </ul>
    </div>
    <div class="popup-body">
        <div class="row">
            <div class="col-12">
                <div class="box-form">
                    <div class="legend-title mart20">Trọn gói sản lượng</div>
                    <div class="info-row">
                        <div class="key w120">
                            <!-- <div class="check-action"> -->
                                <span class="name">Giá tiền trọn gói: </span>
                            <!-- </div> -->
                        </div>
                        <div class="value">
                            <input type="text" class="form-control " required :value="txttrongoi" @change="v=>txttrongoi=v.target.value">
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
</template>
<style scoped>

</style>
<script>
export default {
    name: 'popupTronGoiBaoHieuQT',
    props: [ 'dulieu' ],
    watch: {
    },
    data() {
        return {
            txttrongoi: '',
            is_ghilai: false,
        }
    },
    async mounted() {
        var rs = await this.$root.context.post('/web-hopdong/lapdatmoi/sp_ds_hdtb_cntt_sub_baohieuquocte', {
            hdtb_id: this.dulieu.hdtb_id, loaicuoc: this.dulieu.loaicuoc
        });
        if(rs && rs.data && rs.data.length>0) {
            this.txttrongoi = rs.data[0]['GIATRONGOI'.toLowerCase()]?rs.data[0]['GIATRONGOI'.toLowerCase()]:'';
        }
        else this.txttrongoi = '';
    },
    computed: {
    },
    methods: {
        async btnGhiLai_Click() {
            try
            {
                
                var rs = await this.$root.context.post('/web-hopdong/lapdatmoi/quyen_xoa_loaicuoc_baohieuquocte',{ hdtb_id: this.dulieu.hdtb_id, luong_id:this.dulieu.luong_id });// tdan.GET_VALUE_FUNC("{?DB2}.QUYEN_XOA_LOAICUOC_BAOHIEUQUOCTE", "vhdtb_id", hdtb_id, "vluong_id", luong_id, "vuser", tt_nd.ma_nd).ToString();
                if (rs && rs.data == "OK")
                {
                    if (this.txttrongoi)
                    {
                        var kq = await this.$root.context.post('/web-hopdong/lapdatmoi/fn_capnhat_htdb_cntt_sub_baohieuquocte', { hdtb_id: this.dulieu.hdtb_id, jsondata: "["+this.txttrongoi+"]", ma_tb: this.dulieu.ma_tb, luong_id: this.dulieu.luong_id, loaicuoc: this.dulieu.loaicuoc })//tdan.GET_VALUE_FUNC("{?DB2}.FN_CAPNHAT_HTDB_CNTT_SUB_BAOHIEUQUOCTE", "vhdtb_id", hdtb_id, "vjson_data", '[' + txttrongoi.EditValue.ToString() + ']',"vma_tb", ma_tb, "vluong_id", luong_id, "vma_nd", tt_nd.ma_nd, "vloaicuoc", loaicuoc);
                        if (kq && kq.data != "OK")
                        {
                            this.$toast.error("Lỗi ghi lại "+kq.data);
                            return;
                        }
                        else
                        {
                            this.$toast.success("Cập nhật thành công");
                            this.is_ghilai = true;
                            this.closeForm();
                        }
                    }
                    else
                    {
                        this.$toast.error("Chưa nhập giá tiền trọn gói");
                        return;
                    }
                }
                else
                {
                    this.$toast.error(rs.data);
                    return;
                }
            }
            catch (ex)
            {
                console.error(ex);
                this.$toast.error("Lỗi ghi lại "+ex);
                return;
            }
        },
        async btnXoa_Click() {
            try
            {
                
                var rs = await this.$root.context.post('/web-hopdong/lapdatmoi/quyen_xoa_loaicuoc_baohieuquocte',{ hdtb_id: this.dulieu.hdtb_id, luong_id:this.dulieu.luong_id });// tdan.GET_VALUE_FUNC("{?DB2}.QUYEN_XOA_LOAICUOC_BAOHIEUQUOCTE", "vhdtb_id", hdtb_id, "vluong_id", luong_id, "vuser", tt_nd.ma_nd).ToString();
                if (rs && rs.data == "OK")
                {
                    // int kq = tdan.EXECUTE_SQL("delete from " + DatabaseConstants.DB2 + ".hdtb_cntt_sub where hdtb_id = " + hdtb_id + " and loaihinh_tb = " + 2114.ToString() + " and pin = 'TRONGOI'");
                    var kq = await this.$root.context.get('/web-hopdong/lapdatmoi/sp_del_hdtb_cntt_sub_by_pin', {params:{ hdtb_id: this.dulieu.hdtb_id, pin: 'TRONGOI' }});
                    if (kq && kq.data && kq.data > 0)
                    {
                        this.$toast.success("Xóa thành công");
                        this.txttrongoi = "";
                        this.is_ghilai = false;
                    }
                    else
                    {
                        this.$toast.error("Xóa không thành công");
                    }
                }
                else
                {
                    this.$toast.error(rs.data);
                    return;
                }
            }
            catch (ex)
            {
                this.$toast.error(ex.Message);
            }
        },
        closeForm() {
            this.$emit('form-close',this.is_ghilai);
        },
        terminateForm() {
            this.$emit('form-close',-1);
        },
    }
}
</script>
