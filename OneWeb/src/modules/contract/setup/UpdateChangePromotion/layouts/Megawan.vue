<template>
    <div class="row">
        <div class="col-sm-6 col-12">
            <div class="box-form">
                <div class="legend-title">Loại hình thuê bao cũ</div>
                <div class="row">
                    <div class="col-sm-8 col-12">
                        <div class="info-row">
                            <div class="key">Số máy/Acc</div>
                            <div class="value">
                                <div class="input-more-button">
                                    <button class="btn" @click="searchAccount">
                                        <span class="-ap icon-more_horiz"></span>
                                    </button>
                                    <input type="text" ref="refInputMaTB" class="form-control highlight" @keyup.enter="onEnterMaTB" :value="input_matb" @change="e=>input_matb=e.target.value">
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="col-sm-4 col-12">
                        <div class="info-row">
                            <div class="key w80">Đối tượng</div>
                            <div class="value">
                                <div class="select-custom">
                                    <select v-model="doituong_selected" disabled class="form-control">
                                        <option v-for="item in ds_doituong" :key="item.doituong_id" :value="item.doituong_id">
                                            {{item.ten_dt}}
                                        </option>
                                    </select>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col-sm-4 col-12">
                        <div class="info-row">
                            <div class="key">Loại kênh</div>
                            <div class="value">
                                <div class="select-custom">
                                    <select v-model="loaikenh_selected" disabled class="form-control">
                                        <option v-for="item in ds_loaikenh" :key="item.loaikenh_id" :value="item.loaikenh_id">
                                            {{item.loai_kenh}}
                                        </option>
                                    </select>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="col-sm-4 col-12">
                        <div class="info-row">
                            <div class="key w80">Loại thiết bị</div>
                            <div class="value">
                                <div class="select-custom">
                                    <select v-model="loaithietbi_selected" disabled class="form-control">
                                        <option v-for="item in ds_loaithietbi" :key="item.thietbidc_id" :value="item.thietbidc_id">
                                            {{item.ten_tbi}}
                                        </option>
                                    </select>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="col-sm-4 col-12">
                        <div class="info-row">
                            <div class="key w80">Loại cáp</div>
                            <div class="value">
                                <div class="select-custom">
                                    <select v-model="loaicap_selected" disabled class="form-control">
                                        <option v-for="item in ds_loaicap" :key="item.loaicap_id" :value="item.loaicap_id">
                                            {{item.loaicap}}
                                        </option>
                                    </select>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col-sm-4 col-12">
                        <div class="info-row">
                            <div class="key">Tốc độ CIR</div>
                            <div class="value">
                                <div class="select-custom">
                                    <ejs-dropdownlist 
                                        :allowFiltering="true"
                                        :enabled="false"
                                        v-model="tocdo_cir_selected"
                                        :filtering="onFilteringTocDo"
                                        :dataSource="ds_tocdo"
                                        :fields="{ text: 'toc_do', value: 'tocdo_id' }" />
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="col-sm-4 col-12">
                        <div class="info-row">
                            <div class="key w80">Modem</div>
                            <div class="value">
                                <div class="select-custom">
                                    <select v-model="modem_selected" disabled class="form-control">
                                        <option v-for="item in ds_modem" :key="item.loaimd_id" :value="item.loaimd_id">
                                            {{item.loai_md}}
                                        </option>
                                    </select>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="col-sm-4 col-12">
                        <div class="info-row">
                            <div class="key w80">Thời hạn</div>
                            <div class="value">
                                <div class="select-custom">
                                    <select v-model="thoihan_selected" disabled class="form-control">
                                        <option v-for="item in ds_thoihan" :key="item.thoihan_id" :value="item.thoihan_id">
                                            {{item.tenthoihan}}
                                        </option>
                                    </select>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col-sm-4 col-12">
                        <div class="info-row">
                            <div class="key">
                                <div class="check-action">
                                    <input type="checkbox" class="check" disabled v-model="checkTocDoPIR">
                                    <span class="name">Tốc độ PIR</span>
                                </div>
                            </div>
                            <div class="value">
                                <div class="select-custom">
                                    <ejs-dropdownlist 
                                        :allowFiltering="true"
                                        :enabled="false"
                                        v-model="tocdo_pir_selected"
                                        :filtering="onFilteringTocDo"
                                        :dataSource="ds_tocdo"
                                        :fields="{ text: 'toc_do', value: 'tocdo_id' }" />
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="col-sm-4 col-12">
                        <div class="info-row">
                            <div class="key w80">Site ID</div>
                            <div class="value">
                                <input type="text" :value="input_site_id" @change="e=>input_site_id=e.target.value" readonly class="form-control">
                            </div>
                        </div>
                    </div>
                    <div class="col-sm-4 col-12">
                        <div class="info-row">
                            <div class="key w80">Trang bị</div>
                            <div class="value">
                                <div class="select-custom">
                                    <select v-model="trangbi_selected" disabled class="form-control">
                                        <option v-for="item in ds_trangbi" :key="item.trangbi_id" :value="item.trangbi_id">
                                            {{item.tentrangbi}}
                                        </option>
                                    </select>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col-sm-8 col-12">
                        <div class="info-row">
                            <div class="key">
                                Chủ quản
                            </div>
                            <div class="value">
                                <div class="select-custom">
                                    <select v-model="chuquan_selected" disabled class="form-control">
                                        <option v-for="item in ds_chuquan" :key="item.chuquan_id" :value="item.chuquan_id">
                                            {{item.ten_tat}}
                                        </option>
                                    </select>
                                </div>
                            </div>
                        </div>
                    </div>
                    
                    <div class="col-sm-4 col-12">
                        <div class="info-row">
                            <div class="key w80">Cước TK</div>
                            <div class="value">
                                <input type="text" :value="input_cuoc_tk" @change="e=>input_cuoc_tk=e.target.value" readonly @keypress="isNumber($event)" class="form-control tright">
                            </div>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col-sm-8 col-12">
                        <div class="info-row">
                            <div class="key">
                                Hướng KN
                            </div>
                            <div class="value">
                                <input type="text" :value="input_huong_kn" @change="e=>input_huong_kn=e.target.value" readonly class="form-control">
                            </div>
                        </div>
                    </div>
                    
                    <div class="col-sm-4 col-12">
                        <div class="info-row">
                            <div class="key w80">Cước TC</div>
                            <div class="value">
                                <input type="text" :value="input_cuoc_tc" @change="e=>input_cuoc_tc=e.target.value" readonly @keypress="isNumber($event)" class="form-control tright">
                            </div>
                        </div>
                    </div>
                </div>
                
                <div class="row">
                    <div class="col-sm-4 col-12">
                        <div class="info-row">
                            <div class="key">
                                <div class="check-action">
                                    <input type="checkbox" class="check" disabled v-model="checkDoiTac">
                                    <span class="name">Đối tác</span>
                                </div>
                            </div>
                            <div class="value">
                                <div class="select-custom">
                                    <select v-model="doitac_selcted" class="form-control" disabled>
                                        <option v-for="item in ds_doitac" :key="item.doitac_id" :value="item.doitac_id">
                                            {{item.ten_dt}}
                                        </option>
                                    </select>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="col-sm-4 col-12">
                        <div class="info-row">
                            <div class="key w80">
                                Cước TKĐT
                            </div>
                            <div class="value">
                                <input type="text" :value="input_cuoc_tkdt" @change="e=>input_cuoc_tkdt=e.target.value" readonly @keypress="isNumber($event)" class="form-control tright" style="background-color: #F1F8FF;">
                            </div>
                        </div>
                    </div>
                    
                    <div class="col-sm-4 col-12">
                        <div class="info-row">
                            <div class="key w80">Cước HT</div>
                            <div class="value">
                                <input type="text" :value="input_cuoc_ht" @change="e=>input_cuoc_ht=e.target.value" readonly @keypress="isNumber($event)" class="form-control tright">
                            </div>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col-sm-4 col-12">
                        <div class="info-row">
                            <div class="key">
                                Mã hợp đồng ĐT
                            </div>
                            <div class="value">
                                <input type="text" :value="input_ma_hddt" @change="e=>input_ma_hddt=e.target.value" readonly  class="form-control" style="background-color: #F1F8FF;">
                            </div>
                        </div>
                    </div>
                    <div class="col-sm-4 col-12">
                        <div class="info-row">
                            <div class="key w80">
                                Cước TCĐT
                            </div>
                            <div class="value">
                                <input type="text" :value="input_cuoc_tcdt" @change="e=>input_cuoc_tcdt=e.target.value" readonly @keypress="isNumber($event)" class="form-control tright" style="background-color: #F1F8FF;">
                            </div>
                        </div>
                    </div>
                    
                    <div class="col-sm-4 col-12">
                        <div class="info-row">
                            <div class="key w80">Cước T.Bị</div>
                            <div class="value">
                                <input type="text" :value="input_cuoc_tb" @change="e=>input_cuoc_tb=e.target.value" readonly @keypress="isNumber($event)" class="form-control tright">
                            </div>
                        </div>
                    </div>
                </div>
                
            </div>
        </div>
        <div class="col-sm-6 col-12">
            <div class="box-form">
                <div class="legend-title">Loại hình thuê bao mới</div>
                <div class="row">
                    <div class="col-sm-4 col-12">
                        <div class="info-row">
                            <div class="key">Loại hình</div>
                            <div class="value">
                                <div class="select-custom">
                                    <select ref="cboLoaiHinhTBMoi" v-model="loaihinh_tb_moi_selected" @change="changeLoaiHinhTBMoi" :disabled="disableLoaiHinhTB" class="form-control">
                                        <option v-for="item in ds_loaihinh" :key="item.LOAITB_ID" :value="item.LOAITB_ID">
                                            {{item.LOAIHINH_TB}}
                                        </option>
                                    </select>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="col-sm-4 col-12">
                        <div class="info-row">
                            <div class="key w80">Khu vực</div>
                            <div class="value">
                                <div class="input-more-button">
                                    <button class="btn" @click="clickKhuVuc">
                                        <span class="-ap icon-more_horiz"></span>
                                    </button>
                                    <input type="text" class="form-control highlight" @keypress="evt=>evt.preventDefault()" :value="input_khuvuc" @change="e=>input_khuvuc=e.target.value">
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="col-sm-4 col-12">
                        <div class="info-row">
                            <div class="key w80">Đối tượng</div>
                            <div class="value">
                                <div class="select-custom">
                                    <select v-model="doituong_moi_selected" @change="changeDoiTuongMoi" :disabled="!enableDoiTuongMoi" class="form-control">
                                        <option v-for="item in ds_doituong" :key="item.doituong_id" :value="item.doituong_id">
                                            {{item.ten_dt}}
                                        </option>
                                    </select>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col-sm-4 col-12">
                        <div class="info-row">
                            <div class="key">Account mới</div>
                            <div class="value">
                                <input type="text" :value="input_account_moi" @change="e=>input_account_moi=e.target.value" class="form-control highlight">
                            </div>
                        </div>
                    </div>
                    <div class="col-sm-4 col-12">
                        <div class="info-row">
                            <div class="key w80">Loại kênh</div>
                            <div class="value">
                                <div class="select-custom">
                                    <select v-model="loaikenh_moi_selected" class="form-control">
                                        <option v-for="item in ds_loaikenh" :key="item.loaikenh_id" :value="item.loaikenh_id">
                                            {{item.loai_kenh}}
                                        </option>
                                    </select>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="col-sm-4 col-12">
                        <div class="info-row">
                            <div class="key w80">Loại cáp</div>
                            <div class="value">
                                <div class="select-custom">
                                    <select v-model="loaicap_moi_selected" class="form-control">
                                        <option v-for="item in ds_loaicap" :key="item.loaicap_id" :value="item.loaicap_id">
                                            {{item.loaicap}}
                                        </option>
                                    </select>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col-sm-4 col-12">
                        <div class="info-row">
                            <div class="key">Tốc độ CIR</div>
                            <div class="value">
                                <div class="select-custom">
                                    <ejs-dropdownlist 
                                        :allowFiltering="true"
                                        v-model="tocdo_cir_moi_selected"
                                        :filtering="onFilteringTocDo"
                                        @change="changeTocDoCirWan"
                                        :dataSource="ds_tocdo"
                                        :fields="{ text: 'toc_do', value: 'tocdo_id' }" />
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="col-sm-4 col-12">
                        <div class="info-row">
                            <div class="key w80">Loại thiết bị</div>
                            <div class="value">
                                <div class="select-custom">
                                    <select v-model="loaithietbi_moi_selected" @change="changeLoaiTBWanMoi" class="form-control">
                                        <option v-for="item in ds_loaithietbi" :key="item.thietbidc_id" :value="item.thietbidc_id">
                                            {{item.ten_tbi}}
                                        </option>
                                    </select>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="col-sm-4 col-12">
                        <div class="info-row">
                            <div class="key w80">Thời hạn</div>
                            <div class="value">
                                <div class="select-custom">
                                    <select v-model="thoihan_moi_selected" class="form-control">
                                        <option v-for="item in ds_thoihan" :key="item.thoihan_id" :value="item.thoihan_id">
                                            {{item.tenthoihan}}
                                        </option>
                                    </select>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col-sm-4 col-12">
                        <div class="info-row">
                            <div class="key">
                                <div class="check-action">
                                    <input type="checkbox" class="check" v-model="checkTocDoPIRMoi">
                                    <span class="name">Tốc độ PIR</span>
                                </div>
                            </div>
                            <div class="value">
                                <div class="select-custom">
                                    <ejs-dropdownlist 
                                        :allowFiltering="true"
                                        :enabled="checkTocDoPIRMoi"
                                        v-model="tocdo_pir_moi_selected"
                                        :filtering="onFilteringTocDo"
                                        :dataSource="ds_tocdo"
                                        :fields="{ text: 'toc_do', value: 'tocdo_id' }" />
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="col-sm-4 col-12">
                        <div class="info-row">
                            <div class="key w80">Modem</div>
                            <div class="value">
                                <div class="select-custom">
                                    <select v-model="modem_moi_selected" class="form-control">
                                        <option v-for="item in ds_modem" :key="item.loaimd_id" :value="item.loaimd_id">
                                            {{item.loai_md}}
                                        </option>
                                    </select>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="col-sm-4 col-12">
                        <div class="info-row">
                            <div class="key w80">Trang bị</div>
                            <div class="value">
                                <div class="select-custom">
                                    <select v-model="trangbi_moi_selected" class="form-control" @change="changeTrangBiMoi">
                                        <option v-for="item in ds_trangbi" :key="item.trangbi_id" :value="item.trangbi_id">
                                            {{item.tentrangbi}}
                                        </option>
                                    </select>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col-sm-4 col-12">
                        <div class="info-row">
                            <div class="key">
                                Chủ quản
                            </div>
                            <div class="value">
                                <div class="select-custom">
                                    <select v-model="chuquan_moi_selected" class="form-control">
                                        <option v-for="item in ds_chuquan" :key="item.chuquan_id" :value="item.chuquan_id">
                                            {{item.ten_tat}}
                                        </option>
                                    </select>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="col-sm-4 col-12">
                        <div class="info-row">
                            <div class="key w80">
                                Site ID
                            </div>
                            <div class="value">
                                <input type="text" :value="input_site_id_moi" @change="e=>input_site_id_moi=e.target.value" class="form-control">
                            </div>
                        </div>
                    </div>
                
                    <div class="col-sm-4 col-12">
                        <div class="info-row">
                            <div class="key w80">Cước TK</div>
                            <div class="value">
                                <input type="text" :value="input_cuoc_tk_moi" @change="e=>input_cuoc_tk_moi=e.target.value" @keypress="isNumber($event)" class="form-control tright">
                            </div>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col-sm-8 col-12">
                        <div class="info-row">
                            <div class="key">
                                Hướng KN
                            </div>
                            <div class="value">
                                <input type="text" :value="input_huong_kn_moi" @change="e=>input_huong_kn_moi=e.target.value" readonly class="form-control highlight">
                            </div>
                        </div>
                    </div>
                
                    <div class="col-sm-4 col-12">
                        <div class="info-row">
                            <div class="key w80">Cước TC</div>
                            <div class="value">
                                <input type="text" :value="input_cuoc_tc_moi" @change="e=>input_cuoc_tc_moi=e.target.value" @keypress="isNumber($event)" class="form-control tright">
                            </div>
                        </div>
                    </div>
                </div>
                
                <div class="row">
                    <div class="col-sm-4 col-12">
                        <div class="info-row">
                            <div class="key">
                                <div class="check-action">
                                    <input type="checkbox" class="check" v-model="checkDoiTacMoi">
                                    <span class="name">Đối tác</span>
                                </div>
                            </div>
                            <div class="value">
                                <div class="select-custom">
                                    <select v-model="doitac_moi_selcted" class="form-control" :disabled="!checkDoiTacMoi">
                                        <option v-for="item in ds_doitac" :key="item.doitac_id" :value="item.doitac_id">
                                            {{item.ten_dt}}
                                        </option>
                                    </select>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="col-sm-4 col-12">
                        <div class="info-row">
                            <div class="key w80">
                                Cước TKĐT
                            </div>
                            <div class="value">
                                <input type="text" :value="input_cuoc_tkdt_moi" @change="e=>input_cuoc_tkdt_moi=e.target.value" @keypress="isNumber($event)" class="form-control tright" style="background-color: #F1F8FF;">
                            </div>
                        </div>
                    </div>
                
                    <div class="col-sm-4 col-12">
                        <div class="info-row">
                            <div class="key w80">Cước HT</div>
                            <div class="value">
                                <input type="text" :value="input_cuoc_ht_moi" @change="e=>input_cuoc_ht_moi=e.target.value" @keypress="isNumber($event)" class="form-control tright">
                            </div>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col-sm-4 col-12">
                        <div class="info-row">
                            <div class="key">
                                Mã hợp đồng ĐT
                            </div>
                            <div class="value">
                                <input type="text" :value="input_ma_hddt_moi" @change="e=>input_ma_hddt_moi=e.target.value" class="form-control" style="background-color: #F1F8FF;">
                            </div>
                        </div>
                    </div>
                    <div class="col-sm-4 col-12">
                        <div class="info-row">
                            <div class="key w80">
                                Cước TCĐT
                            </div>
                            <div class="value">
                                <input type="text" :value="input_cuoc_tcdt_moi" @change="e=>input_cuoc_tcdt_moi=e.target.value" @keypress="isNumber($event)" class="form-control tright" style="background-color: #F1F8FF;">
                            </div>
                        </div>
                    </div>
                
                    <div class="col-sm-4 col-12">
                        <div class="info-row">
                            <div class="key w80">Cước T.Bị</div>
                            <div class="value">
                                <input type="text" :value="input_cuoc_tb_moi" @change="e=>input_cuoc_tb_moi=e.target.value" @keypress="isNumber($event)" class="form-control tright">
                            </div>
                        </div>
                    </div>
                </div>
    
            </div>
        </div>
        <!-- Modal -->
        <SearchAccountModal ref="searchAccountModal" @form-close="formCloseSearchAccount"/>
        <ThayDoiKhuVucQuanLyThueBaoModal ref="thayDoiKhuVucQuanLyThueBaoModal"
            :data="dataPopupKhuVuc"
            @accept="onAcceptKhuVuc"
            @success_update="successUpdateThayDoiKhuVucQuanLy"/>
    </div>
</template>
<script>
import SearchAccountModal from '../../SurveyRequest/popups/SearchAccountModal.vue'
import ThayDoiKhuVucQuanLyThueBaoModal from '../../SurveyRequest/popups/ThayDoiKhuVucQuanLyThueBao/ThayDoiKhuVucQuanLyThueBaoModal.vue'
import {TRANGBI, DichVuVienThong, LoaiHinhTB} from '../ThamSo'
import ChangeSubsTypeAPI from '../ChangeSubsTypeAPI'
import { DataManager,Query,ODataV4Adaptor,Predicate } from "@syncfusion/ej2-data"
export default {
    name:'Megawan',
    props:{
      khachhang_id:{
        type:Number,
        default:0
      },
      hdkh_id:{
        type:Number,
        default:0
      },
      khuvuc_id:{
        type:Number,
         default:0
      },
      donviql_id:{
        type:Number,
        default:0
      },
      hdtb_id:{
        type:Number,
        default:0
      },
      thuebao_id:{
        type:Number,
        default:0
      },
      dataDiaChiLD:{
        type:Object,
        default:()=>{
          return {
            diachi_id:0,
            tinh_id:0,
            quan_id:0,
            phuong_id:0,
            pho_id:0,
            ap_id:0,
            khu_id:0,
            dacdiem_id:0,
            sonha:'',
            //
            block:'',
            tang:'',
            sophong:'',
            motathem:'',
            diachi:'',

            kinhdo:0,
            vido:0
          }
        }
      },
      checkQuyen:{
        type:Number,
        default:0
      },
      iKieu:{
        type:Number,
        default:0
      }
    },
    components:{
        SearchAccountModal,
        ThayDoiKhuVucQuanLyThueBaoModal
    },
    data(){
        return {
            input_matb:'',
            ds_doituong:[],
            doituong_selected:null,
            ds_loaikenh:[],
            loaikenh_selected:null,
            ds_loaithietbi:[],
            loaithietbi_selected:null,
            ds_loaicap:[],
            loaicap_selected:null,
            ds_tocdo:[],
            //ds_tocdo_cir:[],
            tocdo_cir_selected:null,
            ds_modem:[],
            modem_selected:null,
            ds_thoihan:[],
            thoihan_selected:null,
            checkTocDoPIR:false,
            //ds_tocdo_pir:[],
            tocdo_pir_selected:null,
            input_site_id:'',
            ds_trangbi:[],
            trangbi_selected:null,
            ds_chuquan:[],
            chuquan_selected:null,
            input_cuoc_tk:'',
            input_huong_kn:'',
            input_cuoc_tc:'',
            checkDoiTac:false,
            ds_doitac:[],
            doitac_selcted:null,
            input_cuoc_tkdt:'',
            input_cuoc_ht:'',
            input_ma_hddt:'',
            input_cuoc_tcdt:'',
            input_cuoc_tb:'',

            //thông tin thuê bao mới
            ds_loaihinh:[],
            loaihinh_tb_moi_selected:null,
            disableLoaiHinhTB:false,
            input_khuvuc:'',
            khuvuc_selected:null,
            dataPopupKhuVuc:{
                quan_id:0,
                phuong_id:0,
                pho_id:0,
                ap_id:0,
                khu_id:0,
                dacdiem_id:0,
                khuvuc_id:0,
                donviql_id:0,
                hdtb_id:0
            },
            doituong_moi_selected:null,
            input_account_moi:'',
            loaikenh_moi_selected:null,
            loaicap_moi_selected:null,
            tocdo_cir_moi_selected:null,
            loaithietbi_moi_selected:null,
            thoihan_moi_selected:null,
            checkTocDoPIRMoi:false,
            tocdo_pir_moi_selected:null,
            modem_moi_selected:null,
            trangbi_moi_selected:3, //Default khách hàng tự trang bị
            chuquan_moi_selected:null,
            input_site_id_moi:'',
            input_cuoc_tk_moi:'0',
            input_huong_kn_moi:'',
            input_cuoc_tc_moi:'0',
            checkDoiTacMoi:false,
            doitac_moi_selcted:null,
            input_cuoc_tkdt_moi:'',
            input_cuoc_ht_moi:'',
            input_ma_hddt_moi:'',
            input_cuoc_tcdt_moi:'',
            input_cuoc_tb_moi:'',
            
            isChangeTocDo:true,
            enableDoiTuongMoi:true

        }
    },
    methods:{
        searchAccount(){
            this.$refs.searchAccountModal.showModal()
        },
        formCloseSearchAccount(data){
            this.input_matb=data.ma_tb
        },
        clickKhuVuc(){
            this.dataPopupKhuVuc.quan_id=this.dataDiaChiLD.quan_id
            this.dataPopupKhuVuc.phuong_id=this.dataDiaChiLD.phuong_id
            this.dataPopupKhuVuc.pho_id=this.dataDiaChiLD.pho_id
            this.dataPopupKhuVuc.ap_id=this.dataDiaChiLD.ap_id
            this.dataPopupKhuVuc.khu_id=this.dataDiaChiLD.khu_id
            this.dataPopupKhuVuc.dacdiem_id=this.dataDiaChiLD.dacdiem_id
            this.dataPopupKhuVuc.khuvuc_id=this.khuvuc_id
            this.dataPopupKhuVuc.donviql_id=this.donviql_id
            //sửa cho hiển thị địa chỉ theo hợp đồng thuê bao trước
            if(this.hdtb_id!=0){
                this.dataPopupKhuVuc.hdtb_id=this.hdtb_id
            }else{
                this.dataPopupKhuVuc.thuebao_id=this.thuebao_id
            }
            this.$refs.thayDoiKhuVucQuanLyThueBaoModal.showModal()
        },
        focusMaTB(){
            this.$nextTick(()=>{
                this.$refs.refInputMaTB.focus()
            })
        },
        onEnterMaTB(){
            if(this.input_matb.trim()==''){
                return
            }
            this.$emit('onEnterMaTB', this.input_matb.trim())
        },
        changeTrangBiMoi(){
            this.$emit('changeTrangBiMoi', this.trangbi_moi_selected)
        },
        changeLoaiTBWanMoi(){
            this.$emit('changeLoaiTBWanMoi', this.loaithietbi_moi_selected)
        },
        changeTocDoCirWan(){
            if(!this.isChangeTocDo){
                this.isChangeTocDo=true
                return
            }
            this.$emit('changeTocDoCirWan', this.tocdo_cir_moi_selected)
        },
        async changeDoiTuongMoi(){
            this.$emit('changeDoiTuongMoi', this.doituong_moi_selected)
        },
        async setLoaiHinhTbMoi(loaihinh_tb_id){
            console.log('setLoaiHinhTbMoi', loaihinh_tb_id)
            this.loaihinh_tb_moi_selected=loaihinh_tb_id
            //handle change 
            await this.changeLoaiHinhTBMoi()
            
        },
        onFilteringTocDo(e){
            var query = new Query();
            let predicate;  
            //frame the query based on search string with filter type.
            if (e.text.trim() !== ''){
                predicate  = new Predicate('toc_do', 'contains', e.text.trim(), true);
                query.where(predicate)
            }
            //pass the filter data source, filter query to updateData method.
            e.updateData(this.ds_tocdo, query);
        },
        isNumber(evt) {
            evt = (evt) ? evt : window.event;
            var charCode = (evt.which) ? evt.which : evt.keyCode;
            if ((charCode > 31 && (charCode < 48 || charCode > 57))) {
                evt.preventDefault()
            } else {
                return true
            }
        },
        async changeLoaiHinhTBCu(loaihinhtb_id){
    
        },
        async getMaTb(){
            return this.input_matb.trim()
        },
        setDsLoaiHinhTbMoi(ds, selectedId){
            this.ds_loaihinh=ds
            this.loaihinh_tb_moi_selected=selectedId
        },
        getLoaiHinhTBMoi(){
            const loaihinh=this.ds_loaihinh.find(x=>x.LOAITB_ID==this.loaihinh_tb_moi_selected)
            return loaihinh!=undefined?loaihinh.LOAIHINH_TB:''
        },
        focusLoaiHinhTBMoi(){
            this.$refs.cboLoaiHinhTBMoi.focus()
        },
        async changeLoaiHinhTBMoi(){
            this.$emit('changeLoaiHinhTBMoi', this.loaihinh_tb_moi_selected)
        },
        async getLoaiHinhTB_ID_Moi(){
            return this.loaihinh_tb_moi_selected!=null?this.loaihinh_tb_moi_selected:0
        },
        async HienThiTTHopDongTB_B2(item, result){
            this.doituong_moi_selected=item.DOITUONG_ID!=null?item.DOITUONG_ID:null
            if(result.DOITUONG_ID_CU!='-1'&&result.DOITUONG_ID_CU!=''){
                this.doituong_selected=result.DOITUONG_ID_CU
            }else{
                this.doituong_selected=null
            }
            this.input_khuvuc=result.TEN_KV!=null?result.TEN_KV:''
        },
        async HienThiTT_DanhBaV2(item){
            this.doituong_selected=item.doituong_id!=null?item.doituong_id:null
            this.doituong_moi_selected=item.doituong_id!=null?item.doituong_id:null

            this.input_khuvuc=item.ten_kv!=undefined?item.ten_kv:''
            await this.changeDoiTuongMoi()
        },
        async HienThiTTMoRong_DB(item){
            console.log('HienThiTTMoRong_DB MEGAWAN', item)
            this.tocdo_cir_selected=item.TOCDO_ID
            
            if(item.TOCDO_PIR_ID!=null){
                this.checkTocDoPIR=true
                this.checkTocDoPIRMoi=true
                this.tocdo_pir_selected=item.TOCDO_PIR_ID
                this.tocdo_pir_moi_selected=item.TOCDO_PIR_ID
            }else{
                this.checkTocDoPIR=false
                this.checkTocDoPIRMoi=false
                this.tocdo_pir_selected=item.TOCDO_ID
                this.tocdo_pir_moi_selected=item.TOCDO_ID
            }
            this.modem_selected=item.LOAIMD_ID
            this.modem_moi_selected=item.LOAIMD_ID

            this.loaikenh_selected=item.LOAIKENH_ID
            this.loaikenh_moi_selected=item.LOAIKENH_ID

            this.input_site_id=item.SITE_ID!=null?item.SITE_ID.toString():''
            this.input_site_id_moi=item.SITE_ID!=null?item.SITE_ID.toString():''

            this.thoihan_selected=item.THOIHAN_ID
            this.thoihan_moi_selected=item.THOIHAN_ID

            this.chuquan_selected=item.CHUQUAN_ID
            this.chuquan_moi_selected=item.CHUQUAN_ID
            
            this.loaithietbi_selected=item.THIETBIDC_ID

            this.trangbi_selected=item.TRANGBI_ID
            
            this.loaicap_selected=item.LOAICAP_ID
            this.loaicap_moi_selected=item.LOAICAP_ID

            if(item.DOITAC_ID!=null){
                this.doitac_selcted=item.DOITAC_ID
                this.doitac_moi_selcted=item.DOITAC_ID
                this.input_ma_hddt=item.MAHD_DT!=null?item.MAHD_DT.toString():''
                this.input_cuoc_tkdt=item.CUOC_TKDT!=null?item.CUOC_TKDT.toString():''
                this.input_cuoc_tkdt_moi=item.CUOC_TKDT!=null?item.CUOC_TKDT.toString():''
                this.input_cuoc_tcdt=item.CUOC_TCDT!=null?item.CUOC_TCDT.toString():''
                this.input_cuoc_tcdt_moi=item.CUOC_TCDT!=null?item.CUOC_TCDT.toString():''
                this.checkDoiTac=true
                this.checkDoiTacMoi=true
            }else{
                this.checkDoiTac=false
                this.checkDoiTacMoi=false
                this.doitac_selcted=null
                this.input_ma_hddt=''
                this.input_ma_hddt_moi=''
                this.input_cuoc_tkdt='0'
                this.input_cuoc_tkdt_moi='0'
                this.input_cuoc_tcdt='0'
                this.input_cuoc_tcdt_moi='0'
            }
            //sau này format tiền việt sau
            this.input_cuoc_tk=item.CUOC_TK!=null?item.CUOC_TK.toString():''
            this.input_cuoc_tk_moi=item.CUOC_TK!=null?item.CUOC_TK.toString():''
            this.input_cuoc_tc=item.CUOC_TC!=null?item.CUOC_TC.toString():''
            this.input_cuoc_tc_moi=item.CUOC_TC!=null?item.CUOC_TC.toString():''
            this.input_cuoc_ht=item.CUOC_HT!=null?item.CUOC_HT.toString():''
            this.input_cuoc_ht_moi=item.CUOC_HT!=null?item.CUOC_HT.toString():''
            this.input_cuoc_tb=item.CUOC_TBI!=null?item.CUOC_TBI.toString():''
            this.input_cuoc_tb_moi=item.CUOC_TBI!=null?item.CUOC_TBI.toString():''
        },
        async HienThiTTMoRong_HD(item){
            if(item.TOCDO_PIR_ID!=null){
                this.checkTocDoPIRMoi=true
                this.tocdo_pir_moi_selected=item.TOCDO_PIR_ID
            }else{
                this.checkTocDoPIRMoi=false
                this.tocdo_pir_moi_selected=item.TOCDO_ID
            }
            this.modem_moi_selected=item.LOAIMD_ID
            this.loaikenh_moi_selected=item.LOAIKENH_ID

            this.thoihan_moi_selected=item.THOIHAN_ID
            this.chuquan_moi_selected=item.CHUQUAN_ID
            this.loaicap_moi_selected=item.LOAICAP_ID

            this.input_site_id=item.SITE_ID!=null?item.SITE_ID.toString():''
            this.input_site_id_moi=item.SITE_ID!=null?item.SITE_ID.toString():''

            if(item.DOITAC_ID!=null){
                this.checkDoiTacMoi=true
                this.doitac_moi_selcted=item.DOITAC_ID
                this.input_ma_hddt_moi=item.MAHD_DT!=null?item.MAHD_DT.toString():''
                this.input_cuoc_tkdt_moi=item.CUOC_TKDT!=null?item.CUOC_TKDT.toString():''
                this.input_cuoc_tcdt_moi=item.CUOC_TCDT!=null?item.CUOC_TCDT.toString():''
                
            }else{
                this.checkDoiTacMoi=false
                this.doitac_moi_selcted=null
                this.input_ma_hddt_moi=''
                this.input_cuoc_tkdt_moi='0'
                this.input_cuoc_tcdt_moi='0'
            }

            //sau này format tiền việt sau
            this.input_cuoc_tk_moi=item.CUOC_TK!=null?item.CUOC_TK.toString():''
            this.input_cuoc_tc_moi=item.CUOC_TC!=null?item.CUOC_TC.toString():''
            this.input_cuoc_ht_moi=item.CUOC_HT!=null?item.CUOC_HT.toString():''
            this.input_cuoc_tb_moi=item.CUOC_TBI!=null?item.CUOC_TBI.toString():''
            
        },
        async changeDichVu(dichvuvt_id){
            this.trangbi_moi_selected=TRANGBI.VIENTHONG_TRANGBI

        },
        onAcceptKhuVuc(data){
            this.khuvuc_selected=data
            this.input_khuvuc= this.khuvuc_selected.ma_kv
            this.$emit('onAcceptKhuVuc', this.khuvuc_selected)
        },
        successUpdateThayDoiKhuVucQuanLy(){
            this.$emit('successUpdateThayDoiKhuVucQuanLy')
        },
        async lay_danhmuc_cdlh_tt_chung(chuoi){
            try{
                let response = await ChangeSubsTypeAPI.lay_danhmuc_cdlh_tt_chung(this.axios, chuoi)
                if(response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
                    return response.data.data
                }else{
                    return null
                }
            }catch(e){
                return null
            }
        },
        async initDuLieu(ds_loaihinh_tb, dichvuvt_id){
            console.log('initDuLieu Megawan')
            await this.clearDulieu()
            this.ds_loaihinh=ds_loaihinh_tb.filter(x=>x.DICHVUVT_ID==dichvuvt_id)
            if(this.ds_loaihinh.length>0){
                this.loaihinh_tb_moi_selected=this.ds_loaihinh[0].LOAITB_ID
                await this.changeLoaiHinhTBMoi()
            }
            //
            let result = await this.lay_danhmuc_cdlh_tt_chung('TRANG_BI|DOITUONG|LOAI_MD|LOAI_KENH|LOAI_TB|THOIHAN|CHU_QUAN|LOAI_CAP|DOITAC|TOCDO_KENH')
            if(result){
                this.ds_trangbi=result.trangbi
                if(this.ds_trangbi.length>0){
                    this.trangbi_selected=this.ds_trangbi[0].trangbi_id
                    this.trangbi_moi_selected=this.ds_trangbi[0].trangbi_id
                }
                this.ds_doituong=result.doituong
                if(this.ds_doituong.length>0){
                    this.doituong_selected=this.ds_doituong[0].doituong_id
                    this.doituong_moi_selected=this.ds_doituong[0].doituong_id
                }
                this.ds_modem=result.loai_md
                if(this.ds_modem.length>0){
                    this.modem_selected=this.ds_modem[0].loaimd_id
                    this.modem_moi_selected=this.ds_modem[0].loaimd_id
                }
                this.ds_loaikenh=result.loai_kenh
                if(this.ds_loaikenh.length>0){
                    this.loaikenh_selected=this.ds_loaikenh[0].loaikenh_id
                    this.loaikenh_moi_selected=this.ds_loaikenh[0].loaikenh_id
                }
                this.ds_loaithietbi=result.loai_tb.filter(x=>x.dichvuvt_id==DichVuVienThong.MEGAWAN)
                if(this.ds_loaithietbi.length>0){
                    this.loaithietbi_selected=this.ds_loaithietbi[0].thietbidc_id
                    this.loaithietbi_moi_selected=this.ds_loaithietbi[0].thietbidc_id
                }
                this.ds_thoihan=result.thoihan
                if(this.ds_thoihan.length>0){
                    this.thoihan_selected=this.ds_thoihan[0].thoihan_id
                    this.thoihan_moi_selected=this.ds_thoihan[0].thoihan_id
                }
                this.ds_chuquan=result.chu_quan
                if(this.ds_chuquan.length>0){
                    this.chuquan_selected=this.ds_chuquan[0].chuquan_id
                    this.chuquan_moi_selected=this.ds_chuquan[0].chuquan_id
                }
                this.ds_loaicap=result.loai_cap
                if(this.ds_loaicap.length>0){
                    this.loaicap_selected=this.ds_loaicap[0].loaicap_id
                    this.loaicap_moi_selected=this.ds_loaicap[0].loaicap_id
                }
                this.ds_doitac=result.doitac
                if(this.ds_doitac.length>0){
                    this.doitac_selcted=this.ds_doitac[0].doitac_id
                    this.doitac_moi_selcted=this.ds_doitac[0].doitac_id
                }
                this.ds_tocdo=result.tocdo_kenh
                if(this.ds_tocdo.length>0){
                    this.tocdo_cir_selected=this.ds_tocdo[0].tocdo_id
                    this.tocdo_pir_selected=this.ds_tocdo[0].tocdo_id
                    this.tocdo_cir_moi_selected=this.ds_tocdo[0].tocdo_id
                    this.tocdo_pir_moi_selected=this.ds_tocdo[0].tocdo_id

                }
            }


        },
        visibleToolStripMenuAndControl(visible){
            if((this.iKieu==1||this.iKieu==2)&&this.checkQuyen==0){
                this.enableDoiTuongMoi=visible
                this.checkTocDoPIRMoi=visible
                //     cboTocDo.Enabled = visible;
            } 
        },
        async clearDulieu(){
            this.input_matb=''
            this.checkTocDoPIR=false
            this.input_site_id=''
            this.input_cuoc_tk=''
            this.input_huong_kn=''
            this.input_cuoc_tc=''
            this.checkDoiTac=false
            this.input_cuoc_tkdt=''
            this.input_cuoc_ht=''
            this.input_ma_hddt=''
            this.input_cuoc_tcdt=''
            this.input_cuoc_tb=''

            //thông tin thuê bao mới
            this.disableLoaiHinhTB=false
            this.input_khuvuc=''
            this.khuvuc_selected=null
            this.dataPopupKhuVuc={
                quan_id:0,
                phuong_id:0,
                pho_id:0,
                ap_id:0,
                khu_id:0,
                dacdiem_id:0,
                khuvuc_id:0,
                donviql_id:0,
                hdtb_id:0
            }
            this.input_account_moi=''
            this.checkTocDoPIRMoi=false
            this.input_site_id_moi=''
            this.input_cuoc_tk_moi='0'
            this.input_huong_kn_moi=''
            this.input_cuoc_tc_moi='0'
            this.checkDoiTacMoi=false
            this.input_cuoc_tkdt_moi=''
            this.input_cuoc_ht_moi=''
            this.input_ma_hddt_moi=''
            this.input_cuoc_tcdt_moi=''
            this.input_cuoc_tb_moi=''

            if(this.ds_trangbi.length>0){
                this.trangbi_selected=this.ds_trangbi[0].trangbi_id
                this.trangbi_moi_selected=3
            }
            if(this.ds_doituong.length>0){
                this.doituong_selected=this.ds_doituong[0].doituong_id
                this.doituong_moi_selected=this.ds_doituong[0].doituong_id
            }
            if(this.ds_modem.length>0){
                this.modem_selected=this.ds_modem[0].loaimd_id
                this.modem_moi_selected=this.ds_modem[0].loaimd_id
            }
            if(this.ds_loaikenh.length>0){
                this.loaikenh_selected=this.ds_loaikenh[0].loaikenh_id
                this.loaikenh_moi_selected=this.ds_loaikenh[0].loaikenh_id
            }
            if(this.ds_loaithietbi.length>0){
                this.loaithietbi_selected=this.ds_loaithietbi[0].thietbidc_id
                this.loaithietbi_moi_selected=this.ds_loaithietbi[0].thietbidc_id
            }
            if(this.ds_thoihan.length>0){
                this.thoihan_selected=this.ds_thoihan[0].thoihan_id
                this.thoihan_moi_selected=this.ds_thoihan[0].thoihan_id
            }
            if(this.ds_chuquan.length>0){
                this.chuquan_selected=this.ds_chuquan[0].chuquan_id
                this.chuquan_moi_selected=this.ds_chuquan[0].chuquan_id
            }
            if(this.ds_loaicap.length>0){
                this.loaicap_selected=this.ds_loaicap[0].loaicap_id
                this.loaicap_moi_selected=this.ds_loaicap[0].loaicap_id
            }
            if(this.ds_doitac.length>0){
                this.doitac_selcted=this.ds_doitac[0].doitac_id
                this.doitac_moi_selcted=this.ds_doitac[0].doitac_id
            }
            if(this.ds_tocdo.length>0){
                this.tocdo_cir_selected=this.ds_tocdo[0].tocdo_id
                this.tocdo_pir_selected=this.ds_tocdo[0].tocdo_id
                this.tocdo_cir_moi_selected=this.ds_tocdo[0].tocdo_id
                this.tocdo_pir_moi_selected=this.ds_tocdo[0].tocdo_id
            }
            this.enableDoiTuongMoi=true
        }
    }
}
</script>