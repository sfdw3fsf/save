<template>
<ejs-dialog :enableResize='true' :isModal="true" :allowDragging="true" :visible="false" ref="generalDialog" header="Thông tin thuê bao" :position="{ X: 'center', Y: 'top' }"
    showCloseIcon=true target=".main-wrapper" :closeOnEscape="true" width="70%" height="600">
        <div class="modal-content popup-box">
            <div class="list-actions-top">
                <ul class="list">
                    <li>
                        <a ref="tsbtnChapNhan" @click="tsbtnChapNhan_Click">
                            <span class="icon one-file-plus"></span> Chấp nhận
                        </a>
                    </li>
                </ul>
            </div>
            <div class="popup-body">
                <div class="box-form">
                    <DataGridCustom
                        ref="gridHoaDonPhatDuoc"
                        :columns="[
                            {
                                fieldName: 'stt',
                                headerText: 'STT',
                                allowFiltering: true,
                                allowSorting: true,
                                width: '70',
                            },
                            {
                                fieldName: 'ma_tb',
                                headerText: 'Số máy/Acc',
                                allowFiltering: true,
                                allowSorting: true,
                                width: '150',
                            },
                            {
                                fieldName: 'ten_tb',
                                headerText: 'Số máy/Acc',
                                allowFiltering: true,
                                allowSorting: true,
                                width: 'auto',
                            },
                            {
                                fieldName: 'diachi_ld',
                                headerText: 'Địa chỉ lắp đặt',
                                allowFiltering: true,
                                allowSorting: true,
                                width: 'auto',
                            },
                            {
                                fieldName: 'trangthai_tb',
                                headerText: 'Trạng thái',
                                allowFiltering: true,
                                allowSorting: true,
                                width: 'auto',
                            },
                            {
                                fieldName: 'loaihinh_tb',
                                headerText: 'Loại hình',
                                allowFiltering: true,
                                allowSorting: true,
                                width: 'auto',
                            }]"
                    />
                </div>
            </div>
        </div>
    </ejs-dialog>
</template>
<script>

import DataGridCustom from "../../DeclareLandline/components/DataGridCustom";
export default {
    components: {
        DataGridCustom
    },
    data() {
        return {
            ds : [],
            resolvePromise: null,
            rejectPromise: null
        }
    },
    methods: {
        showDialog() {
            this.frmDSTBCungMa_Load()
            this.$refs.generalDialog.show()
        },

        async showDialogAndWait(){
            this.showDialog();

            return new Promise((resolve, reject) => {
                this.resolvePromise = resolve;
                this.rejectPromise = reject;
            });
        },
        frmDSTBCungMa_Load()
        {
            try
            {
                //endregion

                this.$refs.gridHoaDonPhatDuoc.dataSource = []
                this.$refs.gridHoaDonPhatDuoc.dataSource = this.ds
            }
            catch (err)
            {

            }
        }

        ,tsbtnChapNhan_Click()
        {
            try
            {
                let selected = this.$refs.gridHoaDonPhatDuoc.getSelectedRecords()
                this.$emit("chonTBCungma", selected)
                this.$refs.generalDialog.hide()

                if(this.resolvePromise != null)
                    this.resolvePromise({itemSelected: selected});
            }
            catch (err)
            { }
        }

        ,tsbtnThoat_Click()
        {
            if(this.resolvePromise != null)
                this.resolvePromise({itemSelected: null});
            this.Close();
        }
    }
}
</script>
