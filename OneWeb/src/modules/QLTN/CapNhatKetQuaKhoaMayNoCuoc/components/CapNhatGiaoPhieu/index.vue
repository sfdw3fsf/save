<template>
        <ejs-dialog :enableResize="true" :allowDragging="true" :visible="false" :animationSettings="animationSettings" ref="dialogObjChuyenTo" :position="position" :open="openCapNhatGiaoPhieu" :close="closeDialogChuyenTo" :header="'Cập nhật giao phiếu xác minh Tạm dừng - Thanh lý'" showCloseIcon="true" width="90%" target="#app .main-wrapper">
            <div>
                <div class="list-actions-top">
                    <ul class="list">
                        <li>
                            <a href="#" @click.prevent="tsbtnCapNhat_Click"> <span class="icon one-reload1"></span> Cập nhật </a>
                        </li>
                        <li>
                            <a href="#" @click.prevent="thoat"> <span class="icon ui-1_circle-remove nc-icon-glyph"></span> Thoát</a>
                        </li>
                    </ul>
                </div>
                <div class="popup-body">
                    <div class="box-form">
                        <div class="row">
                            <div class="col-sm-7 col-12">
                                <div class="box-form">
                                    <div class="legend-title">Thông tin phiếu</div>
                                    <div class="row">
                                        <div class="col-sm-6 col-12">
                                            <div class="info-row">
                                                <div class="key w100">Mã GD</div>
                                                <div class="value">
                                                    <input v-model="maGD" type="text" class="form-control bold">
                                                </div>
                                            </div>
                                        </div>
                                        <div class="col-sm-6 col-12">
                                            <div class="info-row">
                                                <div class="key w100">Ngày yêu cầu</div>
                                                <div class="value">
                                                    <input v-model="ngayYC" type="text" class="form-control">
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="row">
                                        <div class="col-sm-6 col-12">
                                            <div class="info-row">
                                                <div class="key w100">Số máy/Acc</div>
                                                <div class="value">
                                                    <input v-model="soMay" type="text" class="form-control">
                                                </div>
                                            </div>
                                        </div>
                                        <div class="col-sm-6 col-12">
                                            <div class="info-row">
                                                <div class="key w100">Kiểu yêu cầu</div>
                                                <div class="value">
                                                    <input v-model="kieuYC" type="text" class="form-control">
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="row">
                                        <div class="col-sm-6 col-12">
                                            <div class="info-row">
                                                <div class="key w100">Dịch vụ</div>
                                                <div class="value">
                                                    <!-- <SelectExt v-model.trim="idDichVu" :dataSource="danhsachDonViQL" dataTextField="TEN_HIENTHI"
                                                dataValueField="DONVI_ID" /> -->
                                                    <input type="text" v-model="txtDichVuVT" class="form-control" />
                                                </div>
                                            </div>
                                        </div>
                                        <div class="col-sm-6 col-12">
                                            <div class="info-row">
                                                <div class="key w100">Loại hình</div>
                                                <div class="value">
                                                    <!-- <SelectExt v-model.trim="idLoaiHinh" :dataSource="danhsachDonViQL" dataTextField="TEN_HIENTHI"
                                                dataValueField="DONVI_ID" /> -->
                                                <input type="text" v-model="txtLoaiTB" class="form-control" />
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="info-row">
                                        <div class="key w100">Tên thuê bao</div>
                                        <div class="value">
                                            <input v-model="tenTB" type="text" class="form-control">
                                        </div>
                                    </div>
                                    <div class="info-row">
                                        <div class="key w100">Địa chỉ lắp đặt</div>
                                        <div class="value">
                                            <input v-model="diaChiLD" type="text" class="form-control">
                                        </div>
                                    </div>
                                    <div class="legend-title">Lý do hủy</div>
                                    <DataGrid
                                        :columns="[
                                        {fieldName: 'LYDOHUY', headerText: 'Lý do hủy', allowFiltering: true},
                                        {fieldName: 'KIEU_HUY', headerText: 'Kiểu', allowFiltering: true},
                                        {fieldName: 'NGUOI_CN', headerText: 'Người CN', allowFiltering: true},
                                        {fieldName: 'ND_THUCHIEN', headerText: 'Ghi chú', allowFiltering: true},
                                        ]"
                                        :dataSource="dsLyDoHuy"
                                        :allowPaging="true"
                                        :enablePagingServer="false"
                                        ref="gridDsLyDoHuy"
                                    ></DataGrid>
                                </div>
                            </div>
                            <div class="col-sm-5 col-12">
                                <div class="box-form">
                                    <div class="legend-title">Thông tin giao việc</div>
                                    <div class="info-row">
                                        <div class="key">Trung tâm</div>
                                        <div class="value">
                                            <SelectExt v-model.trim="trungTamID" :dataSource="dsTrungTam" dataTextField="ten_ht"
                                                dataValueField="donvi_id" />
                                        </div>
                                    </div>
                                    <div class="info-row">
                                        <div class="key">Tổ</div>
                                        <div class="value">
                                            <SelectExt v-model.trim="toID" :dataSource="dsTo" dataTextField="ten_dv"
                                                dataValueField="donvi_id" />
                                        </div>
                                    </div>
                                    <DataGrid :columns="[
                                        {fieldName: 'MA_NV', headerText: 'Mã NV', allowFiltering: true},
                                        {fieldName: 'TEN_NV', headerText: 'Tên nhân viên', allowFiltering: true},
                                        {fieldName: 'SDT', headerText: 'Số điện thoại', allowFiltering: true},
                                        {fieldName: 'NVQL_THUEBAO', headerText: 'NVQL thuê bao', allowFiltering: true},
                                        ]" :dataSource="dsNhanVien" :showFilter="true" ref="gridDsNhanVien" :showColumnCheckbox=false
                                        :allowPaging="true" :enablePagingServer="false" @selectedRowChanged="itemFocus"
                                        :panelDataHeight='"250"'
                                        />
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="legend-title">Chi tiết điều hành thi công</div>
                    <div class="relative" style="z-index: 2; overflow: scroll">
                    <DataGrid :columns="[
                        {fieldName: 'huonggiao', headerText: 'Hướng giao', allowFiltering: true, isGroupedColumn: true},
                        {fieldName: 'dvg', headerText: 'Đơn vị giao', allowFiltering: true},
                        {fieldName: 'ngay_giao', headerText: 'Ngày giao', allowFiltering: true},
                        {fieldName: 'nv_giao', headerText: 'Người giao', allowFiltering: true},
                        {fieldName: 'nd_giao', headerText: 'Nội dung giao', allowFiltering: true},
                        {fieldName: 'dvn', headerText: 'Đơn vị nhận', allowFiltering: true},
                        {fieldName: 'trangthai_ph', headerText: 'Trạng thái phiếu', allowFiltering: true},
                        {fieldName: 'ngay_tra', headerText: 'Ngày trả', allowFiltering: true},
                        {fieldName: 'nd_tra', headerText: 'Nội dung trả', allowFiltering: true},
                        {fieldName: 'lydotra', headerText: 'Lý do trả', allowFiltering: true},
                        {fieldName: 'ngay_th', headerText: 'Ngày CN', allowFiltering: true},
                        ]" :dataSource="dsThiCong" :showFilter="true" ref="gridDsThiCong" :showColumnCheckbox=false
                        :allowPaging="true" :enablePagingServer="false" :panelDataHeight='"400"'
                        />
                    </div>
                </div>
            </div>
        </ejs-dialog>
</template>
<style  src='./styles.scss'></style>
<script>
import moment from 'moment'
import LOAI_DV from '@/modules/contract/profile/CompleteProfileChangeAccount/Enum/LOAI_DV.js'

export default {
    components:{

    },
    name: 'CapNhatGiaoPhieu',
    props: {

    },
    data(){
        return {
            animationSettings: { effect: 'Zoom' },
            position: { X: 'center', Y: 'top' },
            dsNhanVien:[],
            dsThiCong:[],
            dsLyDoHuy:[],
            dsTrungTam:[],
            trungTamID: '',
            dsTo:[],
            toID: '',
            vhdtb_id: 0,
            vhuonggiao_id: 0,
            vquytrinh_id:0,
            maGD:'',
            ngayYC:'',
            soMay:'',
            kieuYC:'',
            idDichVu:'',
            idLoaiHinh:'',
            tenTB:'',
            diaChiLD:'',
            txtDichVuVT:'',
            txtLoaiTB:'',
            dsSelected: [],
            idNhanVien: null
        }
    },
    created(){
        this.lay_ds_donvi_ql(LOAI_DV.DONVIQL_LD)
    },
    mounted(){

    },
    watch:{
        trungTamID: async function(val){
            if(!val) return
            try {
            this.loading(true)
            let loaidvId = 5 // VETINH = 5;
            await this.lay_ds_tramvt(val, loaidvId) // VETINH = 5;
            } catch (err) {
            } finally {
            this.loading(false)
            }
        },
        toID: async function(val){
            if(!val) return
            try {
            this.loading(true)
            await this.lay_ds_nhanvien(val)
            } catch (err) {
            } finally {
            this.loading(false)
            }
        }
    },
    methods:{
        closeDialogChuyenTo: function () {
            this.trungTamID = ''
            this.toID = ''
            this.dsNhanVien = []
        },
        thoat() {
            this.$refs.dialogObjChuyenTo.hide()
        },
        async tsbtnCapNhat_Click(){
            try {
                this.loading(true)
                const dsHDTBID = this.dsSelected.map(item => item.HDTB_ID)
                const input = {
                    dsHDTBId: dsHDTBID.toString(),
                    donViId: this.toID,
                    loaiDVId: 710
                }
                const res = await this.axios.post("/web-thuno/api/thu-no/xu-ly-thue-bao/them-hopdong-thuebao-donvi",input);
                if(res && res.data.error == 200){
                    this.$toast.success("Cập nhật thành công!");
                    this.$emit('close', this.idNhanVien)
                    this.$refs.dialogObjChuyenTo.hide()
                }
            } catch (error) {
                console.log(error)
                this.$toast.error(error.data.message_detail);
            } finally {
                this.loading(false)
            }
        },
        openDialog(){
            this.$refs.dialogObjChuyenTo.show()
        },
        openCapNhatGiaoPhieu(){
            this.HienThi_DS_PhieuTH()
            if(this.dsSelected.length > 0){
                let objViewData = this.dsSelected[0]
                this.maGD = objViewData.MA_GD
                // this.ngayYC = this.objViewData.MA_GD
                this.soMay = objViewData.MA_TB
                this.kieuYC = objViewData.KIEUYC
                // this.txtDichVuVT = objViewData.DICHVUVT_ID
                this.txtLoaiTB = objViewData.LOAIHINH_TB
                this.tenTB = objViewData.TEN_TB
                this.diaChiLD = objViewData.DIACHI_LD
            }

        },
        async lay_ds_donvi_ql(loaidvId){
            let input = {
               loaidv_id: loaidvId
            }
            const res = await this.$root.context.post("/web-thicong/hoinghi_truyenhinh/sp_lay_ds_donvi_ql", input);
            if (res.data && res.error == 200) {
                this.dsTrungTam = res.data
            }
        },
        async lay_ds_tramvt(donvi_id, loaiddv_id){
            let input = {
                donvi_id: donvi_id,
                loaidv_id: loaiddv_id
            }
            const res = await this.$root.context.post("/web-bancheo/thaydoimegawan/sp_ht_donvi_loaidv_combobox2", input);
            if (res.data && res.error == 200) {
                this.dsTo = res.data
            }
        },
        async lay_ds_nhanvien(id_to){
            const res = await this.$root.context.get("/web-thuno/api/thu-no/common/ds-nhan-vien/don-vi/" + id_to);
            if (res.data && res.error == 200) {
                this.dsNhanVien = res.data
            }
        },
        async HienThi_DS_PhieuTH(){
            let mytable = await this.hienthi_ds_phieuth_chuyento(null, this.vhdtb_id)
            this.dsThiCong = mytable
        },
        hienthi_ds_phieuth_chuyento: async function (vbaohong_id, vhdtb_id) {
            var input = {
                vbaohong_id: vbaohong_id == null ? 0 : vbaohong_id,
                vhdtb_id: vhdtb_id == null ? 0 : vhdtb_id
            }
            let data = await this.$root.context.post("/web-thicong/hoinghi_truyenhinh/hienthi_ds_phieuth_chuyento", input);
            return data.data
        },
        itemFocus(value){
            if(value){
                this.idNhanVien = value.NHANVIEN_ID
            } else {
                this.idNhanVien = null
            }
        }
    }
}
</script>
