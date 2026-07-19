<template>

    <div class="" id="mdNhanVien">
        <!-- <div class="modal-dialog" style="width: 100%; max-width: 600px;"> -->
            <div class="modal-content popup-box">
                <!-- <div class="popup-header">
                    <div class="title">Chọn người giới thiệu</div>
                    <div class="close -ap icon-close" data-dismiss="modal" @Click="onClose">

                    </div>
                </div> -->
                <div class="list-actions-top">
                    <ul class="list">
                        <li>
                            <a href='javascript: void(0)' @click="onClickAccept" >
                                <span class="icon ui-1_check-circle-08 nc-icon-glyph"></span> Chấp nhận (F5)
                            </a>
                        </li>
                        <li>
                            <a href="javascript: void(0)" @click="onClose">
                                <span class="icon one-export"></span> Thoát
                            </a>
                        </li>


                    </ul>
                </div>

                <!-- noi dung -->
                <div class="popup-body">
                        <div class="box-form">
                             <div class="legend-title">Thông tin tìm kiếm
                            </div>

                                <div class="row">
                                    <div class="col-sm-6 col-12">
                                            <div class="info-row">
                                                <div class="key w90">Nhóm</div>
                                                <div class="value ">
                                                    <div class="select-custom">
                                                        <SelectExt  v-model="form.loainv_id" :dataSource="dsLoaiNV" ref="r_loaigt"
                                                                dataTextField="TEN" dataValueField="LOAINV_ID"
                                                                change="getDanhSach" />
                                                        <!-- <SelectExt v-model="form.loainv_id"  :dataSource="dsLoaiNV"
                                                              dataTextField="TEN" dataValueField="LOAINV_ID" /> -->

                                                    </div>
                                                </div>
                                            </div>
                                            <div class="info-row">
                                            <div class="key w90">Đơn vị QL</div>
                                            <div class="value">
                                                <div class="select-custom">
                                                        <SelectExt  v-model="form.donvi_id" :dataSource="dsDonVi"
                                                              dataTextField="TEN_HIENTHI" dataValueField="DONVI_ID" />
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="col-sm-6 col-12">

                                    </div>
                                </div>


                            <div class="legend-title">Danh sách nhân viên
                            </div>

                        <div class="table-content" >
                                <DataGrid :dataSource="dsNhanVien"
                                            :columns="[
                                                    {fieldName: 'STT', headerText: 'STT', allowFiltering: true, allowSorting:false,width:50},
                                                    {fieldName: 'MA_THE', headerText: 'Mã thẻ', allowFiltering: true, allowSorting:false,width:100 },
                                                    {fieldName: 'MA_NV', headerText: 'Mã nhân viên', allowFiltering: true, allowSorting:false,width:100 },
                                                    {fieldName: 'MA_DV', headerText: 'Mã đơn vị', allowFiltering: true, allowSorting:false,width:100 },
                                                    {fieldName: 'TEN_NV', headerText: 'Tên nhân viên', allowFiltering: true, allowSorting:false},
                                                    {fieldName: 'TEN_DV', headerText: 'Tên đơn vị', allowFiltering: true, allowSorting:false}
                                                    ]"
                                            :showFilter="true"

                                            @rowClicked="RowGridChange"
                                            >
                                </DataGrid>
                        </div>
                    </div>

                </div>



            </div>
        <!-- </div> -->
    </div>
</template>
<script>
export default {
    props: ["nhom_ID","kieu","ctv_id"],
   data: function () {
    return {
        dsLoaiNV:[],
        dsNhanVien:[],
        dsDonVi:[],
        r_data:[],
        form:{
            loainv_id:'',
            donvi_id:'',
        },
    }},
    // mouted:{
    //     this.getNhom(this.nhom_ID);
    //     this.getDSDonViCTV();
    // },
   created: async function () {
       await this.getNhom(this.nhom_ID);
       await this.getDSDonViCTV();
   },
    methods:{
        onClose(){
         this.$bvModal.hide('mdNhanVien');
        },
        getNhom: async function(nhom_ID){
             try {
                const rs = await this.$root.context.get(
                `/web-thuno/api/thu-no/quan-ly-dai-ly/ds-loai-nhan-vien/nhom/${nhom_ID}`
                );
                //if (rs.data.length>0)
                    this.dsLoaiNV = rs.data;
            } catch (error) {}
        },
        getDSDonViCTV: async function(){
            try {
                const rs = await this.$root.context.get(
                `/web-thuno/api/thu-no/common/don-vi/loai-don-vi?loaiDVId=2,3,30,100&donViId=`
                );
                //if (rs.data.length>0)
                    this.dsDonVi = rs.data;
            } catch (error) {}
        },
        getDanhSach(){
            this.getDSNhanVien(this.form.loainv_id,this.form.donvi_id);
        },
        getDSNhanVien: async function(loaiNVId,donViId){
            try {
                this.loading(true);
                const rs = await this.$root.context.get(
                `/web-thuno/api/thu-no/quan-ly-dai-ly/ds-nhan-vien-gioi-thieu?loaiNVId=${loaiNVId}&donViId=${donViId}`
                );
                //if (rs.data.length>0)
                    this.dsNhanVien = rs.data;
                this.loading(false);
            } catch (error) {}
        },
        RowGridChange(...data){
            this.r_data=data[1];
        },
         onClickAccept() {

                this.$emit('acceptChange',this.r_data);
                this.onClose();
        },

    },
    watch: {
        "form.loainv_id": async function (newVal,oldVal)  {
                    await this.getDSNhanVien(newVal,this.form.donvi_id);
        },

        "form.donvi_id": async function (newVal,oldVal)  {
                    await this.getDSNhanVien(this.form.loainv_id,newVal);
        }
     }
}
</script>
