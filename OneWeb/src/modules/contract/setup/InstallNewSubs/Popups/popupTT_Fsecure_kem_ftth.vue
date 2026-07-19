<template>
<div>
    <div class="popup-header">
        <div class="title"><span class="icon one-notepad"></span> Chi tiết ngành nghề</div>
        <div class="close -ap icon-close" data-dismiss="modal" @click="closeForm">
        </div>
    </div>
    <div class="list-actions-top">
        <ul class="list">
            <li>
                <a href="#" @click.prevent="acceptForm">
                    <span class="icon nc-icon-glyph ui-1_check-circle-08"></span> Chấp nhận (F5)
                </a>
            </li>
            <li :class="{hidden:!btnThoat}">
                <a href="#" @click.prevent="closeForm">
                    <span class="icon ui-1_circle-remove nc-icon-glyph"></span> Thoát
                </a>
            </li>
        </ul>
    </div>
    <div class="popup-body">
        <div class="box-form" style="overflow: hidden;">
            <div class="info-row">
                <h2>THÔNG TIN THUÊ BAO FSECURE LẮP KÈM</h2>
            </div>
            <div class="info-row">
                <div class="key nowrap"><strong>Thông tin thuê bao</strong></div>
                <div class="value">
                    <input type="checkbox" class="form-control" v-model="chkKH_CD"> Khách hàng tự cài đặt
                </div>
            </div>
            <div class="info-row nowrap">
                <div class="key">SĐT đăng ký</div>
                <div class="value">
                    <input type="text" class="form-control" v-model="txtSoDT">
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
    name: 'popupTT_Fsecure_kem_ftth',
    props: [ 'dulieu' ],
    watch: {

    },
    data() {
        return {
            page: 0, //Page number
            chkKH_CD:false,
            txtSoDT: '',
        }
    },
    mounted() {
        if(this.dulieu.so_dt)
            this.txtSoDT = this.dulieu.so_dt;
    },
    computed: {
    },
    methods: {
        closeForm() {
            this.$bvModal.msgBoxConfirm("Bạn không lắp Fsecure kèm FTTH?", {
              title: 'Xác nhận hành động',
              size: 'sm',
              okTitle: 'Đồng ý',
              cancelTitle: 'Không đồng ý',
            }).then(async v => {
              if(v)
              {
                this.$emit('form-close',null);
              }
            });
        },
        acceptForm() {
            try {
                if (this.txtSoDT.trim() == "")
                {
                    this.$toast.error("Hãy nhập số điện thoại!");
                    return;
                }

                if (this.txtSoDT[0] == '0')
                {
                    this.$toast.error("Bạn hãy bỏ số '0' ở đầu số điện thoại Fsecure!");
                    return;
                }

                if (this.txtSoDT.trim().length < 9 || this.txtSoDT.trim().length > 10)
                {
                    this.$toast.error("Bạn phải nhập số di động vào (9 ký tự số <= Số điện thoại <= 10 ký tự số)!");
                    return;
                }

                if (this.txtSoDT.match(/^[0-9]+$/g))//(!Common.Utilities.CommonFunction.CheckIsNumber(Common.Utilities.CommonFunction.Unformat_tien(this.txtSoDT)))
                {
                    this.$toast.error("Số điện thoại phải là kiểu số!");
                    return;
                }
                //FIXME bỏ qua đoạn này vì đã kiểm tra khi insert/update
                // try
                // {
                //     if (new TraCuuDanhBaFacade2().Kiemtra_sodt(this.txtSoDT.Trim()) == 0)
                //     {
                //         this.$toast.error("Số điện thoại không chính xác. Bạn hãy nhập lại!");
                //         txtSoDT.Focus();
                //         return;
                //     }
                // }
                // catch (Exception)
                // {
                // }

                // //Kiểm tra trên hệ thống Fsecure
                // FSecureService secure = new FSecureService();
                // string message = "";

                // int _status = secure.UserStatus(this.txtSoDT.Trim(), ref message);
                // if (_status == 1)
                // {
                //     Message_Box.ShowTB("Số điện thoại này đã được sử dụng để đăng ký Fsecure. Bạn hãy nhập SĐT khác!");
                //     return;
                // }

                // DataSet dsKtra = new TraCuuDanhBaFacade2().KiemTra_sodt_fsecure(this.txtSoDT.Trim(), 0); //bangts.GetSQL(strKtra, "");
                // if (dsKtra != null && dsKtra.Tables[0].Rows.Count > 0)
                // {
                //     this.$toast.error("Số điện thoại này đã được sử dụng để đăng ký Fsecure. Bạn hãy nhập SĐT khác!");
                //     txtSoDT.Focus();
                //     return;
                // }

                this.$emit('form-close',{ so_dt: this.txtSoDT.trim(), kh_cd_fsecure: this.chkKH_CD });
            } catch(ex) { this.$toast.error(""+ex); }
        },

    }
}
</script>
