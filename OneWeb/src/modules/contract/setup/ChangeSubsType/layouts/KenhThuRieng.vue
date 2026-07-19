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
                                    <input type="text" ref="refInputMaTB" class="form-control bold highlight" @keyup.enter="onEnterMaTB" :value="input_matb" @change="e=>input_matb=e.target.value">
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
                            <div class="key">Tốc độ kênh</div>
                            <div class="value">
                                <div class="select-custom">
                                    <select v-model="tocdo_selected" disabled class="form-control">
                                        <option v-for="item in ds_tocdo" :key="item.tocdo_id" :value="item.tocdo_id">
                                            {{item.toc_do}}
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
                                    <select v-model="trangbi_selected" disabled class="form-control">
                                        <option v-for="item in ds_trangbi" :key="item.trangbi_id" :value="item.trangbi_id">
                                            {{item.tentrangbi}}
                                        </option>
                                    </select>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="col-sm-4 col-12">
                        <div class="info-row">
                            <div class="key w80">Loại kênh</div>
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
                </div>
                <div class="row">
                    <div class="col-sm-4 col-12">
                        <div class="info-row">
                            <div class="key">Thời hạn</div>
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
                    <div class="col-sm-4 col-12">
                        <div class="info-row">
                            <div class="key w80">Chủ quản</div>
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
                            <div class="key w80">Cước TC</div>
                            <div class="value">
                                <vue-numeric  class="form-control tright" separator="," :value="input_cuoc_tc" disabled @change="v=>input_cuoc_tc=v.target.value" placeholder="0" output-type="Number"></vue-numeric>
                                <!-- <input type="text" class="form-control tright" readonly @keypress="isNumber($event)" :value="input_cuoc_tc" @change="e=>input_cuoc_tc=e.target.value"> -->
                            </div>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col-sm-8 col-12">
                        <div class="info-row">
                            <div class="key">
                                Loại thiết bị
                            </div>
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
                            <div class="key w80">Cước TK</div>
                            <div class="value">
                                <vue-numeric  class="form-control tright" separator="," :value="input_cuoc_tk" disabled @change="v=>input_cuoc_tk=v.target.value" placeholder="0" output-type="Number"></vue-numeric>
                                <!-- <input type="text" class="form-control tright" readonly @keypress="isNumber($event)" :value="input_cuoc_tk" @change="e=>input_cuoc_tk=e.target.value"> -->
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
                                    <select v-model="doitac_selected" class="form-control" disabled>
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
                                <vue-numeric  class="form-control tright" separator="," :value="input_cuoc_tkdt" disabled @change="v=>input_cuoc_tkdt=v.target.value" placeholder="0" output-type="Number"></vue-numeric>
                                <!-- <input type="text" class="form-control tright" readonly :value="input_cuoc_tkdt" @change="e=>input_cuoc_tkdt=e.target.value" @keypress="isNumber($event)" style="background-color: #F1F8FF;" > -->
                            </div>
                        </div>
                    </div>
                
                    <div class="col-sm-4 col-12">
                        <div class="info-row">
                            <div class="key w80">Cước HT</div>
                            <div class="value">
                                <vue-numeric  class="form-control tright" separator="," :value="input_cuoc_ht" disabled @change="v=>input_cuoc_ht=v.target.value" placeholder="0" output-type="Number"></vue-numeric>
                                <!-- <input type="text" :value="input_cuoc_ht" readonly @change="e=>input_cuoc_ht=e.target.value" @keypress="isNumber($event)" class="form-control tright"> -->
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
                                <input type="text" :value="input_ma_hddt" readonly @change="e=>input_ma_hddt=e.target.value" class="form-control" style="background-color: #F1F8FF;">
                            </div>
                        </div>
                    </div>
                    <div class="col-sm-4 col-12">
                        <div class="info-row">
                            <div class="key w80">
                                Cước TCĐT
                            </div>
                            <div class="value">
                                <vue-numeric  class="form-control tright" separator="," :value="input_cuoc_tcdt" disabled @change="v=>input_cuoc_tcdt=v.target.value" placeholder="0" output-type="Number"></vue-numeric>
                                <!-- <input type="text" :value="input_cuoc_tcdt" readonly @change="e=>input_cuoc_tcdt=e.target.value" @keypress="isNumber($event)" class="form-control tright" style="background-color: #F1F8FF;"> -->
                            </div>
                        </div>
                    </div>
                
                    <div class="col-sm-4 col-12">
                        <div class="info-row">
                            <div class="key w80">Cước T.Bị</div>
                            <div class="value">
                                <vue-numeric  class="form-control tright" separator="," :value="input_cuoc_tb" disabled @change="v=>input_cuoc_tb=v.target.value" placeholder="0" output-type="Number"></vue-numeric>
                                <!-- <input type="text" :value="input_cuoc_tb" readonly @change="e=>input_cuoc_tb=e.target.value" @keypress="isNumber($event)" class="form-control tright"> -->
                            </div>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col-sm-8 col-12">
                        <div class="info-row">
                            <div class="key">
                                Địa chỉ đầu
                            </div>
                            <div class="value">
                                <input type="text" :value="input_diachi_dau" readonly @change="e=>input_diachi_dau=e.target.value" class="form-control">
                            </div>
                        </div>
                    </div>
                    <div class="col-sm-4 col-12">
                        <div class="info-row">
                            <div class="key w80">
                                L.cáp đầu
                            </div>
                            <div class="value">
                                <div class="select-custom">
                                    <select v-model="lcapdau_selected" disabled class="form-control">
                                        <option v-for="item in ds_lcap" :key="item.loaicap_id" :value="item.loaicap_id">
                                            {{item.loaicap}}
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
                                Địa chỉ cuối
                            </div>
                            <div class="value">
                                <input type="text" :value="input_diachi_cuoi" readonly @change="e=>input_diachi_cuoi=e.target.value" class="form-control">
                            </div>
                        </div>
                    </div>
                    <div class="col-sm-4 col-12">
                        <div class="info-row">
                            <div class="key w80">
                                L.cáp cuối
                            </div>
                            <div class="value">
                                <div class="select-custom">
                                    <select v-model="lcapcuoi_selected" disabled class="form-control">
                                        <option v-for="item in ds_lcap" :key="item.loaicap_id" :value="item.loaicap_id">
                                            {{item.loaicap}}
                                        </option>
                                    </select>
                                </div>
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
                                <div class="select-custom" ref="ds_loaihinh_moi">
                                    <select2 :settings="{ dropdownParent: $refs['ds_loaihinh_moi'] }" ref="cboLoaiHinhTBMoi"
                                        v-model="loaihinh_tb_moi_selected" class="select2"
                                        :options="ds_loaihinh.map(e=> ({id: e.LOAITB_ID, text: e.LOAIHINH_TB}))"
                                        :disabled="disableLoaiHinhTB"
                                        @change="changeLoaiHinhTBMoi"
                                        >
                                    </select2>
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
                                    <input type="text" class="form-control highlight bold" @keypress="evt=>evt.preventDefault()" :value="input_khuvuc" @change="e=>input_khuvuc=e.target.value">
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="col-sm-4 col-12">
                        <div class="info-row">
                            <div class="key w80">Đối tượng</div>
                            <div class="value">
                                <div class="select-custom" ref="ds_doituong">
                                    <select2 :settings="{ dropdownParent: $refs['ds_doituong'] }" ref="cboDoiTuong"
                                        v-model="doituong_moi_selected" class="select2"
                                        :options="ds_doituong.map(e=> ({id: e.doituong_id, text: e.ten_dt}))"
                                        :disabled="!enableDoiTuongMoi"
                                        @change="changeDoiTuongMoi"
                                        >
                                    </select2>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col-sm-4 col-12">
                        <div class="info-row">
                            <div class="key">Tốc độ kênh</div>
                            <div class="value">
                                <div class="select-custom" ref="ds_tocdo">
                                    <select2 :settings="{ dropdownParent: $refs['ds_tocdo'] }" ref="cboTocDoKenh"
                                        v-model="tocdo_moi_selected" class="select2"
                                        :options="ds_tocdo.map(e=> ({id: e.tocdo_id, text: e.toc_do}))"
                                        @change="changeTocDoKenhMoi"
                                        >
                                    </select2>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="col-sm-4 col-12">
                        <div class="info-row">
                            <div class="key w80">Trang bị</div>
                            <div class="value">
                                <div class="select-custom" ref="ds_trangbi">
                                    <select2 :settings="{ dropdownParent: $refs['ds_trangbi'] }" ref="cboTrangbi"
                                        v-model="trangbi_moi_selected" class="select2"
                                        :options="ds_trangbi.map(e=> ({id: e.trangbi_id, text: e.tentrangbi}))"
                                        @change="changeTrangBiMoi"
                                        >
                                    </select2>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="col-sm-4 col-12">
                        <div class="info-row">
                            <div class="key w80">Loại kênh</div>
                            <div class="value">
                                <div class="select-custom" ref="ds_loaikenh">
                                    <select2 :settings="{ dropdownParent: $refs['ds_loaikenh'] }" ref="cboLoaiKenh"
                                        v-model="loaikenh_moi_selected" class="select2"
                                        :options="ds_loaikenh.map(e=> ({id: e.loaikenh_id, text: e.loai_kenh}))">
                                    </select2>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col-sm-4 col-12">
                        <div class="info-row">
                            <div class="key">Thời hạn</div>
                            <div class="value">
                                <div class="select-custom" ref="ds_thoihan">
                                    <select2 :settings="{ dropdownParent: $refs['ds_thoihan'] }" ref="cboThoiHan"
                                        v-model="thoihan_moi_selected" class="select2"
                                        :options="ds_thoihan.map(e=> ({id: e.thoihan_id, text: e.tenthoihan}))">
                                    </select2>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="col-sm-4 col-12">
                        <div class="info-row">
                            <div class="key w80">Chủ quản</div>
                            <div class="value">
                                <div class="select-custom" ref="ds_chuquan">
                                    <select2 :settings="{ dropdownParent: $refs['ds_chuquan'] }" ref="cboChuQuan"
                                        v-model="chuquan_moi_selected" class="select2"
                                        :options="ds_chuquan.map(e=> ({id: e.chuquan_id, text: e.ten_tat}))">
                                    </select2>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="col-sm-4 col-12">
                        <div class="info-row">
                            <div class="key w80">Cước TC</div>
                            <div class="value">
                                <vue-numeric  class="form-control tright" separator="," :value="input_cuoc_tc_moi" @change="v=>input_cuoc_tc_moi=v.target.value" placeholder="0" output-type="Number"></vue-numeric>
                                <!-- <input type="text" :value="input_cuoc_tc_moi" @change="e=>input_cuoc_tc_moi=e.target.value" @keypress="isNumber($event)" class="form-control tright"> -->
                            </div>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col-sm-8 col-12">
                        <div class="info-row">
                            <div class="key">
                                Loại thiết bị
                            </div>
                            <div class="value">
                                <div class="select-custom" ref="ds_loaithietbi">
                                    <select2 :settings="{ dropdownParent: $refs['ds_loaithietbi'] }" ref="cboLoaiThietBi"
                                        v-model="loaithietbi_moi_selected" class="select2"
                                        :options="ds_loaithietbi.map(e=> ({id: e.thietbidc_id, text: e.ten_tbi}))">
                                    </select2>
                                </div>
                            </div>
                        </div>
                    </div>
                
                    <div class="col-sm-4 col-12">
                        <div class="info-row">
                            <div class="key w80">Cước TK</div>
                            <div class="value">
                                <vue-numeric  class="form-control tright" separator="," :value="input_cuoc_tk_moi" @change="v=>input_cuoc_tk_moi=v.target.value" placeholder="0" output-type="Number"></vue-numeric>
                                <!-- <input type="text" :value="input_cuoc_tk_moi" @change="e=>input_cuoc_tk_moi=e.target.value" @keypress="isNumber($event)" class="form-control tright"> -->
                            </div>
                        </div>
                    </div>
                </div>
                
                <div class="row">
                    <div class="col-sm-4 col-12">
                        <div class="info-row">
                            <div class="key">
                                <div class="check-action">
                                    <input type="checkbox" v-model="checkDoiTacMoi" class="check">
                                    <span class="name">Đối tác</span>
                                </div>
                            </div>
                            <div class="value">
                                <div class="select-custom" ref="ds_doitac">
                                    <select2 :settings="{ dropdownParent: $refs['ds_doitac'] }" ref="cboDoiTac"
                                        v-model="doitac_moi_selected" class="select2"
                                        :options="ds_doitac.map(e=> ({id: e.doitac_id, text: e.ten_dt}))"
                                        :disabled="!checkDoiTacMoi">
                                    </select2>
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
                                <vue-numeric  class="form-control tright" separator="," :value="input_cuoc_tkdt_moi" @change="v=>input_cuoc_tkdt_moi=v.target.value" placeholder="0" output-type="Number"></vue-numeric>
                                <!-- <input type="text" :value="input_cuoc_tkdt_moi" @change="e=>input_cuoc_tkdt_moi=e.target.value" @keypress="isNumber($event)" class="form-control tright" style="background-color: #F1F8FF;"> -->
                            </div>
                        </div>
                    </div>
                
                    <div class="col-sm-4 col-12">
                        <div class="info-row">
                            <div class="key w80">Cước HT</div>
                            <div class="value">
                                <vue-numeric  class="form-control tright" separator="," :value="input_cuoc_ht_moi" @change="v=>input_cuoc_ht_moi=v.target.value" placeholder="0" output-type="Number"></vue-numeric>
                                <!-- <input type="text" :value="input_cuoc_ht_moi" @change="e=>input_cuoc_ht_moi=e.target.value" @keypress="isNumber($event)" class="form-control tright"> -->
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
                                <vue-numeric  class="form-control tright" separator="," :value="input_cuoc_tcdt_moi" @change="v=>input_cuoc_tcdt_moi=v.target.value" placeholder="0" output-type="Number"></vue-numeric>
                                <!-- <input type="text" :value="input_cuoc_tcdt_moi" @change="e=>input_cuoc_tcdt_moi=e.target.value" @keypress="isNumber($event)" class="form-control tright" style="background-color: #F1F8FF;"> -->
                            </div>
                        </div>
                    </div>
                
                    <div class="col-sm-4 col-12">
                        <div class="info-row">
                            <div class="key w80">Cước T.Bị</div>
                            <div class="value">
                                <vue-numeric  class="form-control tright" separator="," :value="input_cuoc_tb_moi" @change="v=>input_cuoc_tb_moi=v.target.value" placeholder="0" output-type="Number"></vue-numeric>
                                <!-- <input type="text" :value="input_cuoc_tb_moi" @change="e=>input_cuoc_tb_moi=e.target.value" @keypress="isNumber($event)" class="form-control tright"> -->
                            </div>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col-sm-8 col-12">
                        <div class="info-row">
                            <div class="key">
                                Địa chỉ đầu
                            </div>
                            <div class="value">
                                <input type="text" :value="input_diachi_dau_moi" @change="e=>input_diachi_dau_moi=e.target.value" class="form-control">
                            </div>
                        </div>
                    </div>
                    <div class="col-sm-4 col-12">
                        <div class="info-row">
                            <div class="key w80">
                                L.cáp đầu
                            </div>
                            <div class="value">
                                <div class="select-custom" ref="ds_lcap">
                                    <select2 :settings="{ dropdownParent: $refs['ds_lcap'] }" ref="cboDsLCap"
                                        v-model="lcapdau_moi_selected" class="select2"
                                        :options="ds_lcap.map(e=> ({id: e.loaicap_id, text: e.loaicap}))">
                                    </select2>
                                </div>
                            </div>
                            
                        </div>
                    </div>
                
                </div>
                <div class="row">
                    <div class="col-sm-8 col-12">
                        <div class="info-row">
                            <div class="key">
                                Địa chỉ cuối
                            </div>
                            <div class="value">
                                <input type="text" :value="input_diachi_cuoi_moi" @change="e=>input_diachi_cuoi_moi=e.target.value" class="form-control">
                            </div>
                        </div>
                    </div>
                    <div class="col-sm-4 col-12">
                        <div class="info-row">
                            <div class="key w80">
                                L.cáp cuối
                            </div>
                            <div class="value">
                                <div class="select-custom" ref="ds_lcap1">
                                    <select2 :settings="{ dropdownParent: $refs['ds_lcap1'] }" ref="cboDsLCap1"
                                        v-model="lcapcuoi_moi_selected" class="select2"
                                        :options="ds_lcap.map(e=> ({id: e.loaicap_id, text: e.loaicap}))">
                                    </select2>
                                </div>
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
import ChangeSubsTypeAPI from '../ChangeSubsTypeAPI'
import {TRANGBI, DichVuVienThong, LoaiHinhTB} from '../ThamSo'
export default {
    name:'KenhThuRieng',
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
            ds_tocdo:[],
            tocdo_selected:null,
            ds_trangbi:[],
            trangbi_selected:null,
            ds_loaikenh:[],
            loaikenh_selected:null,
            ds_thoihan:[],
            thoihan_selected:null,
            ds_chuquan:[],
            chuquan_selected:null,
            input_cuoc_tc:'0',
            ds_loaithietbi:[],
            loaithietbi_selected:null,
            input_cuoc_tk:'',
            checkDoiTac:false,
            ds_doitac:[],
            doitac_selected:null,
            input_cuoc_tkdt:'',
            input_cuoc_ht:'0',
            input_ma_hddt:'',
            input_cuoc_tcdt:'',
            input_cuoc_tb:'',
            input_diachi_dau:'',
            lcapdau_selected:null,
            input_diachi_cuoi:'',
            ds_lcap:[],
            lcapcuoi_selected:null,

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
            tocdo_moi_selected:null,
            trangbi_moi_selected:3, //Default khách hàng tự trang bị
            loaikenh_moi_selected:null,
            thoihan_moi_selected:null,
            chuquan_moi_selected:null,
            input_cuoc_tc_moi:'0',
            loaithietbi_moi_selected:null,
            input_cuoc_tk_moi:'0',
            checkDoiTacMoi:false,
            doitac_moi_selected:null,
            input_cuoc_tkdt_moi:'',
            input_cuoc_ht_moi:'0',
            input_ma_hddt_moi:'',
            input_cuoc_tcdt_moi:'',
            input_cuoc_tb_moi:'0',
            input_diachi_dau_moi:'',
            lcapdau_moi_selected:null,
            input_diachi_cuoi_moi:'',
            lcapcuoi_moi_selected:null,

            enableDoiTuongMoi:true,
            enableTocDoKenh:true

        }
    },
    methods:{
        searchAccount(){
            this.$refs.searchAccountModal.showModal()
        },
        formCloseSearchAccount(data){
            if(data&&data.ma_tb){
                this.input_matb=data.ma_tb
                this.onEnterMaTB()
            }
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
        focusLoaiHinhTBMoi(){
            this.$refs.cboLoaiHinhTBMoi.focus()
        },
        onEnterMaTB(){
            if(this.input_matb.trim()==''){
                return
            }
            this.$emit('onEnterMaTB', this.input_matb.trim())
        },
        getLoaiHinhTBMoi(){
            const loaihinh=this.ds_loaihinh.find(x=>x.LOAITB_ID==this.loaihinh_tb_moi_selected)
            return loaihinh!=undefined?loaihinh.LOAIHINH_TB:''
        },
        async setLoaiHinhTbMoi(loaihinh_tb_id){
            this.loaihinh_tb_moi_selected=loaihinh_tb_id
            //handle change 
            await this.changeLoaiHinhTBMoi()
            
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
            this.tocdo_selected=item.TOCDO_ID
            this.tocdo_moi_selected=item.TOCDO_ID

            this.loaikenh_selected=item.LOAIKENH_ID
            this.loaikenh_moi_selected=item.LOAIKENH_ID

            this.thoihan_selected=item.THOIHAN_ID
            this.thoihan_moi_selected=item.THOIHAN_ID

            this.trangbi_selected=item.TRANGBI_ID?item.TRANGBI_ID:null
            this.trangbi_moi_selected=item.TRANGBI_ID

            this.chuquan_selected=item.CHUQUAN_ID
            this.chuquan_moi_selected=item.CHUQUAN_ID

            this.loaithietbi_selected=item.THIETBIDC_ID
            this.loaithietbi_moi_selected=item.THIETBIDC_ID
            
            if(item.DOITAC_ID!=null){
                this.doitac_selected=item.DOITAC_ID
                this.doitac_moi_selected=item.DOITAC_ID
                this.input_ma_hddt=item.MAHD_DT!=null?item.MAHD_DT.toString():''
                this.input_ma_hddt_moi=item.MAHD_DT!=null?item.MAHD_DT.toString():''
                //format tiền sau làm
                this.input_cuoc_tkdt=item.CUOC_TKDT!=null?item.CUOC_TKDT.toString():'0'
                this.input_cuoc_tkdt_moi=item.CUOC_TKDT!=null?item.CUOC_TKDT.toString():'0'
                this.input_cuoc_tcdt=item.CUOC_TCDT!=null?item.CUOC_TCDT.toString():'0'
                this.input_cuoc_tcdt_moi=item.CUOC_TCDT!=null?item.CUOC_TCDT.toString():'0'
                this.checkDoiTac=true
                this.checkDoiTacMoi=true
            }else{
                this.checkDoiTac=false
                this.input_cuoc_tkdt='0'
                this.input_cuoc_tcdt=''
                this.input_ma_hddt=''

                this.checkDoiTacMoi=false
                this.input_cuoc_tkdt_moi='0'
                this.input_cuoc_tcdt_moi='0'
                this.input_ma_hddt_moi=''
            }
            this.input_cuoc_tk=item.CUOC_TK?item.CUOC_TK.toString():'0'
            this.input_cuoc_tc=item.CUOC_TC?item.CUOC_TC.toString():'0'
            this.input_cuoc_ht=item.CUOC_HT?item.CUOC_HT.toString():'0'
            this.input_cuoc_tb=item.CUOC_TBI?item.CUOC_TBI.toString():'0'

        },
        async HienThiTTMoRong_HD(item){
            this.thoihan_moi_selected=item.THOIHAN_ID?item.THOIHAN_ID:null
            this.chuquan_moi_selected=item.CHUQUAN_ID?item.CHUQUAN_ID:null
            this.loaithietbi_moi_selected=item.THIETBIDC_ID?item.THIETBIDC_ID:null
            this.loaikenh_moi_selected=item.LOAIKENH_ID?item.LOAIKENH_ID:null

            if(item.DOITAC_ID){
                this.checkDoiTacMoi=true
                this.doituong_moi_selected=item.DOITAC_ID
                //format tiền việt sau làm
                this.input_cuoc_tkdt_moi= item.CUOC_TKDT?item.CUOC_TKDT.toString():'0'
                this.input_cuoc_tcdt_moi=item.CUOC_TCDT?item.CUOC_TCDT.toString():'0'
                this.input_ma_hddt_moi=item.MAHD_DT?item.MAHD_DT.toString():''
            }else{
                this.checkDoiTacMoi=false
                this.doitac_moi_selected=null
                this.input_cuoc_tkdt_moi='0'
                this.input_cuoc_tcdt_moi='0'
                this.input_ma_hddt_moi=''
            }
            //format tiền việt làm sau
            this.input_cuoc_tk_moi=item.CUOC_TK?item.CUOC_TK.toString():'0'
            this.input_cuoc_tc_moi=item.CUOC_TC?item.CUOC_TC.toString():'0'
            this.input_cuoc_ht_moi=item.CUOC_HT?item.CUOC_HT.toString():'0'
            this.input_cuoc_tb_moi=item.CUOC_TBI?item.CUOC_TBI.toString():'0'
            
        },
        async getLoaiHinhTB_ID_Moi(){
            return this.loaihinh_tb_moi_selected!=null?this.loaihinh_tb_moi_selected:0
        },
        async changeDichVu(dichvuvt_id){

        },
        async changeLoaiHinhTBCu(loaihinhtb_id){
    
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
        onAcceptKhuVuc(data){
            this.khuvuc_selected=data
            this.input_khuvuc= this.khuvuc_selected.ma_kv
            this.$emit('onAcceptKhuVuc', this.khuvuc_selected)
        },
        changeTrangBiMoi(){
            this.$emit('changeTrangBiMoi', this.trangbi_moi_selected)
        },
        async changeDoiTuongMoi(){
            this.$emit('changeDoiTuongMoi', this.doituong_moi_selected)
        },
        async setDsLoaiHinhTbMoi(ds, selectedId){
            this.ds_loaihinh=ds
            this.loaihinh_tb_moi_selected=selectedId
            await this.changeLoaiHinhTBMoi()
        },
        async changeLoaiHinhTBMoi(){
            this.$emit('changeLoaiHinhTBMoi', this.loaihinh_tb_moi_selected)
        },
        async changeTocDoKenhMoi(){
            this.$emit('changeTocDoKenhTSLMoi', this.tocdo_moi_selected)
        },
        async getMaTb(){
            return this.input_matb.trim()
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
        async initDuLieu(ds_loaihinh_tb){
            console.log('initDuLieu Kênh thuê riêng')
            await this.clearDulieu()
            // this.ds_loaihinh=ds_loaihinh_tb.filter(x=>x.DICHVUVT_ID==DichVuVienThong.TSL)
            // if(this.ds_loaihinh.length>0){
            //     this.loaihinh_tb_moi_selected=this.ds_loaihinh[0].LOAITB_ID
            //     await this.changeLoaiHinhTBMoi()
            // }
            //
            let result = await this.lay_danhmuc_cdlh_tt_chung('TRANG_BI|DOITUONG|LOAI_KENH|CHU_QUAN|THOIHAN|DOITAC|LOAI_TB|TOCDO_KENH|LOAI_CAP')
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
                this.ds_loaikenh=result.loai_kenh
                if(this.ds_loaikenh.length>0){
                    this.loaikenh_selected=this.ds_loaikenh[0].loaikenh_id
                    this.loaikenh_moi_selected=this.ds_loaikenh[0].loaikenh_id
                }
                this.ds_chuquan=result.chu_quan
                if(this.ds_chuquan.length>0){
                    this.chuquan_selected=this.ds_chuquan[0].chuquan_id
                    this.chuquan_moi_selected=this.ds_chuquan[0].chuquan_id
                }
                this.ds_thoihan=result.thoihan
                if(this.ds_thoihan.length>0){
                    this.thoihan_selected=this.ds_thoihan[0].thoihan_id
                    this.thoihan_moi_selected=this.ds_thoihan[0].thoihan_id
                }
                this.ds_doitac=result.doitac
                if(this.ds_doitac.length>0){
                    this.doitac_selected=this.ds_doitac[0].doitac_id
                    this.doitac_moi_selected=this.ds_doitac[0].doitac_id
                }
                this.ds_loaithietbi=result.loai_tb.filter(x=>x.dichvuvt_id==DichVuVienThong.TSL||x.dichvuvt_id==25)
                if(this.ds_loaithietbi.length>0){
                    this.loaithietbi_selected=this.ds_loaithietbi[0].thietbidc_id
                    this.loaithietbi_moi_selected=this.ds_loaithietbi[0].thietbidc_id
                }
                this.ds_tocdo=result.tocdo_kenh
                if(this.ds_tocdo.length>0){
                    this.tocdo_selected=this.ds_tocdo[0].tocdo_id
                    this.tocdo_moi_selected=this.ds_tocdo[0].tocdo_id
                    await this.changeTocDoKenhMoi()
                }
                //
                this.ds_lcap=result.loai_cap
                if(this.ds_lcap.length>0){
                    this.lcapdau_selected=this.ds_lcap[0].loaicap_id
                    this.lcapcuoi_selected=this.ds_lcap[0].loaicap_id
                    this.lcapdau_moi_selected=this.ds_lcap[0].loaicap_id
                    this.lcapcuoi_moi_selected=this.ds_lcap[0].loaicap_id
                }
            }
        },
        visibleToolStripMenuAndControl(visible){
            if((this.iKieu==1||this.iKieu==2)&&this.checkQuyen==0){
                this.enableDoiTuongMoi=visible
                this.enableTocDoKenh=visible
            } 
        },
        async clearDulieu(){
            this.input_matb=''
            this.input_cuoc_tc='0'
            this.input_cuoc_tk=''
            this.checkDoiTac=false
            this.input_cuoc_tkdt=''
            this.input_cuoc_ht='0'
            this.input_ma_hddt=''
            this.input_cuoc_tcdt=''
            this.input_cuoc_tb=''
            this.input_diachi_dau=''
            this.input_diachi_cuoi=''

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
            this.input_cuoc_tc_moi='0'
            this.input_cuoc_tk_moi='0'
            this.checkDoiTacMoi=false
            this.input_cuoc_tkdt_moi=''
            this.input_cuoc_ht_moi='0'
            this.input_ma_hddt_moi=''
            this.input_cuoc_tcdt_moi=''
            this.input_cuoc_tb_moi='0'
            this.input_diachi_dau_moi=''
            this.input_diachi_cuoi_moi=''

            if(this.ds_trangbi.length>0){
                this.trangbi_selected=this.ds_trangbi[0].trangbi_id
                this.trangbi_moi_selected=3
            }
            if(this.ds_doituong.length>0){
                this.doituong_selected=this.ds_doituong[0].doituong_id
                this.doituong_moi_selected=this.ds_doituong[0].doituong_id
            }
            if(this.ds_loaikenh.length>0){
                this.loaikenh_selected=this.ds_loaikenh[0].loaikenh_id
                this.loaikenh_moi_selected=this.ds_loaikenh[0].loaikenh_id
            }
            if(this.ds_chuquan.length>0){
                this.chuquan_selected=this.ds_chuquan[0].chuquan_id
                this.chuquan_moi_selected=this.ds_chuquan[0].chuquan_id
            }
            if(this.ds_thoihan.length>0){
                this.thoihan_selected=this.ds_thoihan[0].thoihan_id
                this.thoihan_moi_selected=this.ds_thoihan[0].thoihan_id
            }
            if(this.ds_doitac.length>0){
                this.doitac_selected=this.ds_doitac[0].doitac_id
                this.doitac_moi_selected=this.ds_doitac[0].doitac_id
            }
            if(this.ds_loaithietbi.length>0){
                this.loaithietbi_selected=this.ds_loaithietbi[0].thietbidc_id
                this.loaithietbi_moi_selected=this.ds_loaithietbi[0].thietbidc_id
            }
            if(this.ds_tocdo.length>0){
                this.tocdo_selected=this.ds_tocdo[0].tocdo_id
                this.tocdo_moi_selected=this.ds_tocdo[0].tocdo_id
            }
            //
            if(this.ds_lcap.length>0){
                this.lcapdau_selected=this.ds_lcap[0].loaicap_id
                this.lcapcuoi_selected=this.ds_lcap[0].loaicap_id
                this.lcapdau_moi_selected=this.ds_lcap[0].loaicap_id
                this.lcapcuoi_moi_selected=this.ds_lcap[0].loaicap_id
            }
            this.enableDoiTuongMoi=true
            this.enableTocDoKenh=true
        }

    }
}
</script>