<template>
    <div class="modal-content popup-box">
        <div class="list-actions-top">
            <ul class="list">
                <li>
                    <a @click="btnTimKiem_Click">
                        <span class="icon one-search"></span> Tìm kiếm
                    </a>
                </li>             
            </ul>        
        </div>
        <div class="popup-body">
            <div class="row">
                <div class="col-sm-7 col-12">
                    <div class="box-form">
                        <div class="legend-title">Tiêu chí tìm kiếm</div>
                        <div class="info-row">
                            <div class="key w80">Mã thuê bao</div>
                            <div class="value">
                                <input type="text" class="form-control" @keydown="txtMaTB_onEnter" v-model="ma_tb">
                            </div>
                        </div>
                    </div>
                    <div class="box-form">
                        <div class="legend-title">Thông tin - Check là khoá</div>
                        <div class="row">
                            <div class="col-sm-4 col-12">
                                <div class="item marb20">
                                    <div class="check-action">
                                        <input type="checkbox" class="check" id="CloudGame">
                                        <span class="name">Cloud Game</span>
                                    </div>
                                </div>
                                <div class="item marb20">
                                    <div class="check-action">
                                        <input type="checkbox" class="check" id="Karaoke">
                                        <span class="name">Karaoke</span>
                                    </div>
                                </div>
                                <div class="item marb20">
                                    <div class="check-action">
                                        <input type="checkbox" class="check" id="TheThao">
                                        <span class="name">Thể thao</span>
                                    </div>
                                </div>
                                <div class="item marb20">
                                    <div class="check-action">
                                        <input type="checkbox" class="check" id="PhongCachSong">
                                        <span class="name">Phong cách sống</span>
                                    </div>
                                </div>
                                <div class="item marb20">
                                    <div class="check-action">
                                        <input type="checkbox" class="check" id="DaoTaoTuXa">
                                        <span class="name">Đào tạo từ xa</span>
                                    </div>
                                </div>
                                <div class="item marb20">
                                    <div class="check-action">
                                        <input type="checkbox" class="check" id="FafilmFLG">
                                        <span class="name">Fafim</span>
                                    </div>
                                </div>
                                <div class="item marb20">
                                    <div class="check-action">
                                        <input type="checkbox" class="check" id="KplusFLG">
                                        <span class="name">Kênh K+</span>
                                    </div>
                                </div>
                                <div class="item marb20">
                                    <div class="check-action">
                                        <input type="checkbox" class="check" id="Ceeme">
                                        <span class="name">Ceeme</span>
                                    </div>
                                </div>
                                
                            </div>
                            <div class="col-sm-4 col-12">
                                <div class="item marb20">
                                    <div class="check-action">
                                        <input type="checkbox" class="check" id="PhimTruyen">
                                        <span class="name">Phim truyện</span>
                                    </div>
                                </div>
                                <div class="item marb20">
                                    <div class="check-action">
                                        <input type="checkbox" class="check" id="CaNhac">
                                        <span class="name">Ca nhạc</span>
                                    </div>
                                </div>
                                <div class="item marb20">
                                    <div class="check-action">
                                        <input type="checkbox" class="check" id="SucKhoeLamDep">
                                        <span class="name">Sức khoẻ làm đẹp</span>
                                    </div>
                                </div>
                                <div class="item marb20">
                                    <div class="check-action">
                                        <input type="checkbox" class="check" id="ThieuNhi">
                                        <span class="name">Thiếu nhi</span>
                                    </div>
                                </div>
                                <div class="item marb20">
                                    <div class="check-action">
                                        <input type="checkbox" class="check" id="KetQuaXoSo">
                                        <span class="name">Kết quả xổ số</span>
                                    </div>
                                </div>
                                <div class="item marb20">
                                    <div class="check-action">
                                        <input type="checkbox" class="check" id="BinhChon">
                                        <span class="name">Bình chọn</span>
                                    </div>
                                </div>
                                <div class="item marb20">
                                    <div class="check-action">
                                        <input type="checkbox" class="check" id="FimPlus">
                                        <span class="name">FIM+</span>
                                    </div>
                                </div>
                                
                            </div>
                            <div class="col-sm-4 col-12">
                                <div class="item marb20">
                                    <div class="check-action">
                                        <input type="checkbox" class="check" id="TroChoi">
                                        <span class="name">Game</span>
                                    </div>
                                </div>
                                <div class="item marb20">
                                    <div class="check-action">
                                        <input type="checkbox" class="check" id="DocTruyen">
                                        <span class="name">Đọc truyện</span>
                                    </div>
                                </div>
                                <div class="item marb20">
                                    <div class="check-action">
                                        <input type="checkbox" class="check" id="NhipCauMyTV">
                                        <span class="name">Nhịp cầu MyTV</span>
                                    </div>
                                </div>
                                <div class="item marb20">
                                    <div class="check-action">
                                        <input type="checkbox" class="check" id="GiaoDucDaoTao">
                                        <span class="name">Giáo dục đào tạo</span>
                                    </div>
                                </div>
                                <div class="item marb20">
                                    <div class="check-action">
                                        <input type="checkbox" class="check" id="HBOFlg">
                                        <span class="name">HBO on demand</span>
                                    </div>
                                </div>
                                <div class="item marb20">
                                    <div class="check-action">
                                        <input type="checkbox" class="check" id="VTVcabFlg">
                                        <span class="name">Kênh VTVCab</span>
                                    </div>
                                </div>
                                <div class="item marb20">
                                    <div class="check-action">
                                        <input type="checkbox" class="check" id="Danet">
                                        <span class="name">Danet</span>
                                    </div>
                                </div>                          
                            </div>
                        </div>
                    </div>
                </div>
                <div class="col-sm-5 col-12">
                    <div class="box-form">
                        <div class="legend-title">Danh sách lịch sử tác động khoá mở PayTV</div>
                        <div class="table-content">
                            <DataGridCustom
                                ref="ds_lichsutacdong"
                                v-bind:columns="columns"
                                v-bind:dataSource="ds_lichsutacdong"
                                :showColumnCheckbox="false"
                                :showFilter="true"
                                :enabledSelectFirstRow="true"
                                :selectionSettings="{ checkboxMode: 'ResetOnRowClick' }"
                                :allowPaging="true" :enablePagingServer="false"
                                @rowSelected="gridLichSuTacDong_rowSelected"
                                panelDataHeight="285"
                            ></DataGridCustom>  
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>
<script>
import api from "./BlockOpenPayTV.js"
import DataGridCustom from "@/modules/admin/category/EmployeeAssignmentCriteria/components/DataGrid";
import collums from "./collums.js";
export default {
    name:'TraLichSuTacDongKhoa',
    components:{
        DataGridCustom
    },
    data() {
        return {
            ...collums,
            ma_tb: "",
            ds_lichsutacdong: [],
        }
    },
    methods: {
        async txtMaTB_onEnter(e) {
            if (e.keyCode == 13) {
                await this.btnTimKiem_Click()
            }
        },
        async btnTimKiem_Click() {
            this.ds_lichsutacdong = []
            let params = {
                "vkieu": 1,
                "vma_tb": this.ma_tb
            }
            try {
                this.loading(true)
                let response = await api.ur_21054_004_tracuu_ls_khoamo_paytv(this.axios, params);
                if (response && response.data && response.data.error_code === 'BSS-00000000' && response.data.data) {
                    this.ds_lichsutacdong = response.data.data
                }            
            } catch (e) {
                console.log(e)
            } finally {
                this.loading(false)
            }
        },
        gridLichSuTacDong_rowSelected(data) {
            if (data.data != undefined && data.data.giatri != undefined) {
                let giatri = data.data.giatri.split(";")
                if (giatri.length > 0) {
                    for (let i=0; i<giatri.length; i++) {
                        if (giatri[i].includes(":")) {
                            let tt = giatri[i].split(":")
                            if (tt.length == 2) {
                                $("#" + tt[0]).prop('checked', tt[1] == 0 ? true : false);
                            }
                        }
                    }
                }
            }
        }
    },
}
</script>