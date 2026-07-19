<template>
  <div class="main-wrapper">
    <breadcrumb :header="header" />

    <ActionTop :actions="actions" @onActionClick="onActionClick" />

    <input ref="fileInput" id="file" type="file" @change="onChangeFile($event)" accept=".xls, .xlsx" style="display: none" class="form-control" />
    <input ref="fileInputImage" id="fileImage" type="file" multiple @change="onChangeFileImage($event)" accept="image/*" style="display: none" class="form-control" />

    <div class="page-content">
      <div class="grid-stack-box">
        <Split>
          <SplitArea :size="30" :minSize="100">
            <DanhMucVatTu ref="danhmucvattu" @changeRadioDanhMuc="changeRadioDanhMuc" :danhsach_vattu_cha="danhsach_vattu_cha" :danhsach_danhmuc_vattu="danhsach_danhmuc_vattu" @clickFilter="clickFilter" @tvVatTuFocusedNodeChanged="tvVatTuFocusedNodeChanged" />
          </SplitArea>
          <SplitArea :size="70" :minSize="300">
            <div class="box-form">
              <div class="legend-title">Thông tin vật tư</div>
              <div class="row">
                <div class="col-sm-6 col-12">
                  <div class="info-row">
                    <div class="key w90">Mã vật tư <k-required-marker /></div>
                    <div class="value">
                      <input type="text" ref="inputMaVatTu" :readonly="khoa_nhapmoi" :value="thongtin_vattu.ma_vattu" @change="changeMaVT($event)" @input="textChangeMaVT($event)" class="form-control" />
                    </div>
                  </div>
                  <div class="info-row">
                    <div class="key w90">Tên vật tư <k-required-marker /></div>
                    <div class="value">
                      <input type="text" ref="inputTenVatTu" :readonly="khoa_nhapmoi" :value="thongtin_vattu.ten_vattu" @change="(e) => (thongtin_vattu.ten_vattu = e.target.value)" class="form-control" />
                    </div>
                  </div>
                  <div class="info-row">
                    <div class="key w90">Loại thiết bị</div>
                    <div class="value">
                      <ejs-dropdownlist :allowFiltering="true" :enabled="!khoa_nhapmoi" v-model="loaithietbi_selected" :dataSource="danhsach_loaithietbi" :fields="{ text: 'LOAI_TBI', value: 'LOAITBI_ID' }" />
                    </div>
                  </div>
                  <div class="info-row">
                    <div class="key w90">Vật tư MSCP</div>
                    <div class="value">
                      <ejs-dropdownlist :allowFiltering="true" v-model="vattu_mscp_selected" :dataSource="danhsach_vattu_mscp" :fields="{ text: 'ten', value: 'id' }" />
                    </div>
                  </div>
                  <div class="info-row">
                    <div class="key w90">Chủng loại</div>
                    <div class="value">
                      <ejs-dropdownlist :allowFiltering="true" v-model="chungloai_selected" :dataSource="danhsach_chungloai" :fields="{ text: 'ten_nhom', value: 'id' }" />
                    </div>
                  </div>
                  <div class="info-row">
                    <div class="key w90">Part Number</div>
                    <div class="value">
                      <input type="text" ref="inputPartNumber" :value="thongtin_vattu.part_number" @change="(e) => (thongtin_vattu.part_number = e.target.value)" class="form-control" />
                    </div>
                  </div>
                  <div class="info-row">
                    <div class="key w90">Mã nhóm</div>
                    <div class="value">
                      <div class="input-icon-right">
                        <input type="text" ref="inputMaNhom" @click="clickTimKiemMaNhom" :value="thongtin_vattu.ma_nhom" @change="(e) => (thongtin_vattu.ma_nhom = e.target.value)" class="form-control readonly_input" readonly />
                        <span @click="clearMaNhom" class="icon -ap icon-close" style="color: white !important; background: red !important; border: 2px solid red !important; border-radius: 4px !important"></span>
                      </div>
                    </div>
                  </div>
                  <div class="info-row">
                    <div class="key w90">Ghi chú</div>
                    <div class="value">
                      <textarea name="" ref="inputGhiChu" :value="thongtin_vattu.ghichu" @change="(e) => (thongtin_vattu.ghichu = e.target.value)" class="form-control" style="height: 100px; resize: none"></textarea>
                    </div>
                  </div>
                </div>
                <div class="col-sm-6 col-12">
                  <div class="info-row">
                    <div class="key w120">Đơn vị tính</div>
                    <div class="value">
                      <ejs-dropdownlist :allowFiltering="true" :enabled="!khoa_nhapmoi" v-model="donvitinh_selected" :dataSource="danhsach_donvitinh" :fields="{ text: 'DVI_TINH', value: 'DVITINH_ID' }" />
                    </div>
                  </div>
                  <div class="info-row">
                    <div class="key w120">Vật tư cha</div>
                    <div class="value">
                      <ejs-dropdownlist :allowFiltering="true" v-model="vattu_cha_selected" :filtering="onFilteringVatTuCha" :dataSource="danhsach_vattu_cha" :fields="{ text: 'ten_vt', value: 'vattu_id' }" />
                    </div>
                  </div>
                  <div class="info-row">
                    <div class="key w120">Loại vật tư</div>
                    <div class="value">
                      <ejs-dropdownlist :allowFiltering="true" v-model="loaivattu_selected" :dataSource="danhsach_loaivattu" :fields="{ text: 'LOAI_VT', value: 'LOAIVT_ID' }" />
                    </div>
                  </div>
                  <div class="info-row">
                    <div class="key w120">Loại Onu</div>
                    <div class="value">
                      <ejs-dropdownlist :allowFiltering="true" v-model="onu_selected" :dataSource="danhsach_onu" :fields="{ text: 'TEN_ONU', value: 'ONU_ID' }" />
                    </div>
                  </div>
                  <div class="info-row">
                    <div class="key w120">Số tháng phân bố</div>
                    <div class="value">
                      <input type="text" :value="thongtin_vattu.sothangphanbo" @change="(e) => (thongtin_vattu.sothangphanbo = e.target.value)" @keypress="isNumber($event)" class="form-control tright" />
                    </div>
                  </div>
                  <div class="info-row">
                    <div class="key w120">Mức</div>
                    <div class="value">
                      <input type="text" :value="thongtin_vattu.muc" @change="(e) => (thongtin_vattu.muc = e.target.value)" @keypress="isNumber($event)" class="form-control tright" />
                    </div>
                  </div>
                  <div class="info-row">
                    <div class="key w120">Đơn giá VT</div>
                    <div class="value">
                      <input type="text" :value="thongtin_vattu.dongia_vt" @change="(e) => (thongtin_vattu.dongia_vt = e.target.value)" @keypress="isNumber($event)" class="form-control tright red" />
                    </div>
                  </div>
                  <div class="info-row">
                    <div class="key w120">Hãng sản xuất</div>
                    <div class="value">
                      <ejs-dropdownlist :allowFiltering="true" v-model="hangsx_selected" :dataSource="danhsach_hangsx" :fields="{ text: 'MA_TEN_HANGSX', value: 'HANGSX_ID' }" />
                    </div>
                  </div>
                  <div class="info-row">
                    <div class="key w120">Tên tắt</div>
                    <div class="value">
                      <input type="text" :value="thongtin_vattu.tentat" @change="(e) => (thongtin_vattu.tentat = e.target.value)" class="form-control" />
                    </div>
                  </div>
                  <div class="info-row">
                    <div class="key w120">
                      <div class="check-action">
                        <input type="checkbox" v-model="thongtin_vattu.check_dungchochungtu" class="check" />
                        <span class="name">Dùng cho chứng từ</span>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
            </div>
            <div class="box-form">
              <div class="legend-title">Thông tin hình ảnh vật tư</div>
              <div class="info-row">
                <div class="key">Hình ảnh vật tư</div>
                <div class="value">
                  <div class="input-more-button">
                    <button class="btn" @click="uploadImage" :class="{ disabledButton: !btnUploadImage }" :disabled="!btnUploadImage">
                      <span class="-ap icon-more_horiz"></span>
                    </button>
                    <div class="form-control text pad10" @contextmenu="removeImage" style="height: 120px; overflow-y: scroll">
                      <div v-for="item in ds_hinhanh" :key="item.id" class="value k marb10">
                        <input type="checkbox" v-model="item.check" class="check" />
                        <img class="image_vattu" title="Click here" @click="openImage(item)" :src="item.url" />
                      </div>
                    </div>
                  </div>
                </div>
              </div>
            </div>

            <div class="nav tabs tab-over">
              <a ref="tab1" href="#tab1" data-toggle="tab" @click.prevent="changeTab(1)" :class="{ active: currentTab == 1 }">Thông tin mục đích</a>
              <a href="#tab2" data-toggle="tab" @click.prevent="changeTab(2)" :class="{ active: currentTab == 2 }">Thông tin dòng thiết bị</a>
              <a href="#tab3" data-toggle="tab" @click.prevent="changeTab(3)" :class="{ active: currentTab == 3 }">Thông tin hệ thống</a>
              <a href="#tab4" data-toggle="tab" @click.prevent="changeTab(4)" :class="{ active: currentTab == 4 }">Vật tư đo kiểm</a>
              <a href="#tab4" data-toggle="tab" @click.prevent="changeTab(5)" :class="{ active: currentTab == 5 }">Mục đích phân vùng</a>
              <a href="#tab6" data-toggle="tab" @click.prevent="changeTab(6)" :class="{ active: currentTab == 6 }">Công nghệ</a>
            </div>
            <div class="tab-content">
              <div class="tab-pane" :class="{ active: currentTab == 1 }" id="tab1">
                <ThongTinMucDich ref="thongtinmucdich" :vattu_id="p_vattu_id" />
              </div>
              <div class="tab-pane" :class="{ active: currentTab == 2 }" id="tab2">
                <ThongTinDongThietBi ref="thongtindongthietbi" :vattu_id="p_vattu_id" :hangsx_id="p_hangsx_id" />
              </div>
              <div class="tab-pane" :class="{ active: currentTab == 3 }" id="tab3">
                <ThongTinHeThong ref="thongtinhethong" :vattu_id="p_vattu_id" />
              </div>
              <div class="tab-pane" :class="{ active: currentTab == 4 }" id="tab4">
                <VatTuDoKiem ref="vattudokiem" />
              </div>
              <div class="tab-pane" :class="{ active: currentTab == 5 }" id="tab4">
                <MucDichPhanVung ref="mucdichphanvung" />
              </div>
              <div v-if="currentTab == 6" class="tab-pane" :class="{ active: currentTab == 6 }" id="tab6">
                <ThongTinCongNghe ref="thongTinCongNghe" v-bind="thongTinCongNghe.input" />
              </div>
            </div>
          </SplitArea>
        </Split>
        <!-- <div class="box-col" id="boxLeft">
                        <DanhMucVatTu 
                            ref="danhmucvattu"
                            @changeRadioDanhMuc="changeRadioDanhMuc"
                            :danhsach_vattu_cha="danhsach_vattu_cha"
                            :danhsach_danhmuc_vattu="danhsach_danhmuc_vattu"
                            @clickFilter="clickFilter"
                            @tvVatTuFocusedNodeChanged="tvVatTuFocusedNodeChanged"
                        />
                    </div>
                    <div class="box-col" id="boxRight">
                        <div class="box-form">
                            <div class="legend-title">Thông tin vật tư</div>
                            <div class="row">
                                <div class="col-sm-6 col-12">
                                    <div class="info-row">
                                        <div class="key w90">Mã vật tư <k-required-marker/></div>
                                        <div class="value">
                                            <input type="text" ref="inputMaVatTu" :readonly="khoa_nhapmoi" :value="thongtin_vattu.ma_vattu" @change="e=>thongtin_vattu.ma_vattu=e.target.value" class="form-control">
                                        </div>
                                    </div>
                                    <div class="info-row">
                                        <div class="key w90">Tên vật tư <k-required-marker/></div>
                                        <div class="value">
                                            <input type="text" ref="inputTenVatTu" :readonly="khoa_nhapmoi" :value="thongtin_vattu.ten_vattu" @change="e=>thongtin_vattu.ten_vattu=e.target.value" class="form-control">
                                        </div>
                                    </div>
                                    <div class="info-row">
                                        <div class="key w90">Loại thiết bị</div>
                                        <div class="value">
                                            <ejs-dropdownlist 
                                                :allowFiltering="true"
                                                :enabled="!khoa_nhapmoi"
                                                v-model="loaithietbi_selected"
                                                :dataSource="danhsach_loaithietbi"
                                                :fields="{ text: 'LOAI_TBI', value: 'LOAITBI_ID' }" />
                                        </div>
                                    </div>
                                    <div class="info-row">
                                        <div class="key w90">Vật tư MSCP</div>
                                        <div class="value">
                                            <ejs-dropdownlist 
                                                :allowFiltering="true"
                                                v-model="vattu_mscp_selected"
                                                :dataSource="danhsach_vattu_mscp"
                                                :fields="{ text: 'ten', value: 'id' }" />
                                        </div>
                                    </div>
                                    <div class="info-row">
                                        <div class="key w90">Chủng loại</div>
                                        <div class="value">
                                            <ejs-dropdownlist 
                                                :allowFiltering="true"
                                                v-model="chungloai_selected"
                                                :dataSource="danhsach_chungloai"
                                                :fields="{ text: 'ten_nhom', value: 'id' }" />
                                        </div>
                                    </div>
                                    <div class="info-row">
                                        <div class="key w90">Part Number</div>
                                        <div class="value">
                                            <input type="text" ref="inputPartNumber" :value="thongtin_vattu.part_number" @change="e=>thongtin_vattu.part_number=e.target.value" class="form-control">
                                        </div>
                                    </div>
                                    <div class="info-row">
                                        <div class="key w90">Mã nhóm</div>
                                        <div class="value">
                                            <input type="text" ref="inputMaNhom" @click="clickTimKiemMaNhom" :value="thongtin_vattu.ma_nhom" @change="e=>thongtin_vattu.ma_nhom=e.target.value" class="form-control readonly_input" readonly>
                                        </div>
                                    </div>
                                    <div class="info-row">
                                        <div class="key w90">Ghi chú</div>
                                        <div class="value">
                                            <textarea name="" ref="inputGhiChu" :value="thongtin_vattu.ghichu" @change="e=>thongtin_vattu.ghichu=e.target.value" class="form-control" style="height: 100px;resize: none;"></textarea>
                                        </div>
                                    </div>
                                </div>
                                <div class="col-sm-6 col-12">
                                    <div class="info-row">
                                        <div class="key w120">Đơn vị tính</div>
                                        <div class="value">
                                            <ejs-dropdownlist 
                                                :allowFiltering="true"
                                                :enabled="!khoa_nhapmoi"
                                                v-model="donvitinh_selected"
                                                :dataSource="danhsach_donvitinh"
                                                :fields="{ text: 'DVI_TINH', value: 'DVITINH_ID' }" />
                                        </div>
                                    </div>
                                    <div class="info-row">
                                        <div class="key w120">Vật tư cha</div>
                                        <div class="value">
                                            <ejs-dropdownlist 
                                                :allowFiltering="true"
                                                v-model="vattu_cha_selected"
                                                :filtering="onFilteringVatTuCha"
                                                :dataSource="danhsach_vattu_cha"
                                                :fields="{ text: 'ten_vt', value: 'vattu_id' }" />
                                        </div>
                                    </div>
                                    <div class="info-row">
                                        <div class="key w120">Loại vật tư</div>
                                        <div class="value">
                                            <ejs-dropdownlist 
                                                :allowFiltering="true"
                                                v-model="loaivattu_selected"
                                                :dataSource="danhsach_loaivattu"
                                                :fields="{ text: 'LOAI_VT', value: 'LOAIVT_ID' }" />
                                        </div>
                                    </div>
                                    <div class="info-row">
                                        <div class="key w120">Loại Onu</div>
                                        <div class="value">
                                            <ejs-dropdownlist 
                                                :allowFiltering="true"
                                                v-model="onu_selected"
                                                :dataSource="danhsach_onu"
                                                :fields="{ text: 'TEN_ONU', value: 'ONU_ID' }" />
                                        </div>
                                    </div>
                                    <div class="info-row">
                                        <div class="key w120">Số tháng phân bố</div>
                                        <div class="value">
                                            <input type="text" :value="thongtin_vattu.sothangphanbo" @change="e=>thongtin_vattu.sothangphanbo=e.target.value" @keypress="isNumber($event)" class="form-control tright">
                                        </div>
                                    </div>
                                    <div class="info-row">
                                        <div class="key w120">Mức</div>
                                        <div class="value">
                                            <input type="text" :value="thongtin_vattu.muc" @change="e=>thongtin_vattu.muc=e.target.value" @keypress="isNumber($event)" class="form-control tright">
                                        </div>
                                    </div>
                                    <div class="info-row">
                                        <div class="key w120">Đơn giá VT</div>
                                        <div class="value">
                                            <input type="text" :value="thongtin_vattu.dongia_vt" @change="e=>thongtin_vattu.dongia_vt=e.target.value" @keypress="isNumber($event)" class="form-control tright red" >
                                        </div>
                                    </div>
                                    <div class="info-row">
                                        <div class="key w120">Hãng sản xuất</div>
                                        <div class="value">
                                            <ejs-dropdownlist 
                                                :allowFiltering="true"
                                                v-model="hangsx_selected"
                                                :dataSource="danhsach_hangsx"
                                                :fields="{ text: 'MA_TEN_HANGSX', value: 'HANGSX_ID' }" />
                                        </div>
                                    </div>
                                    <div class="info-row">
                                        <div class="key w120">Tên tắt</div>
                                        <div class="value">
                                            <input type="text" :value="thongtin_vattu.tentat" @change="e=>thongtin_vattu.tentat=e.target.value" class="form-control">
                                        </div>
                                    </div>
                                    <div class="info-row">
                                        <div class="key w120">
                                            <div class="check-action">
                                                <input type="checkbox" v-model="thongtin_vattu.check_dungchochungtu" class="check">
                                                <span class="name">Dùng cho chứng từ</span>
                                            </div>
                                        </div>
                                    </div>
                                    
                                </div>
                            </div>
                        </div>
                        <div class="box-form">
                            <div class="legend-title">Thông tin hình ảnh vật tư</div>
                            <div class="info-row">
                                <div class="key">Hình ảnh vật tư</div>
                                <div class="value">
                                    <div class="input-more-button">
                                        <button class="btn" @click="uploadImage" :class="{disabledButton:!btnUploadImage}" :disabled="!btnUploadImage">
                                            <span class="-ap icon-more_horiz"></span>
                                        </button>
                                        <div class="form-control text pad10" @contextmenu="removeImage"  style="height: 120px; overflow-y: scroll;">
                                            <div v-for="item in ds_hinhanh" :key="item.id" class="value k marb10">
                                                <input type="checkbox" v-model="item.check" class="check">
                                                <img class="image_vattu" title="Click here" @click="openImage(item)" :src="item.url">
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        
                        <div class="nav tabs tab-over">
                            <a href="#tab1" data-toggle="tab" @click.prevent="changeTab(1)" :class="{active: currentTab==1}">Thông tin mục đích</a>
                            <a href="#tab2" data-toggle="tab" @click.prevent="changeTab(2)" :class="{active: currentTab==2}">Thông tin dòng thiết bị</a>
                            <a href="#tab3" data-toggle="tab" @click.prevent="changeTab(3)" :class="{active: currentTab==3}">Thông tin hệ thống</a>
                            <a href="#tab4" data-toggle="tab" @click.prevent="changeTab(4)" :class="{active: currentTab==4}">Vật tư đo kiểm</a>
                            <a href="#tab4" data-toggle="tab" @click.prevent="changeTab(5)" :class="{active: currentTab==5}">Mục đích phân vùng</a>
                        </div>
                        <div class="tab-content">
                            <div class="tab-pane" :class="{active: currentTab==1}" id="tab1" >
                                <ThongTinMucDich ref="thongtinmucdich" :vattu_id="p_vattu_id"/>
                            </div>
                            <div class="tab-pane" :class="{active: currentTab==2}" id="tab2">
                                <ThongTinDongThietBi ref="thongtindongthietbi" :vattu_id="p_vattu_id" :hangsx_id="p_hangsx_id" />
                            </div>
                            <div class="tab-pane" :class="{active: currentTab==3}" id="tab3">
                                <ThongTinHeThong ref="thongtinhethong" :vattu_id="p_vattu_id"/>
                            </div>
                            <div class="tab-pane" :class="{active: currentTab==4}" id="tab4">
                                <VatTuDoKiem ref="vattudokiem"/>
                            </div>
                            <div class="tab-pane" :class="{active: currentTab==5}" id="tab4">
                                <MucDichPhanVung ref="mucdichphanvung"/>
                            </div>
                        </div>

                    </div> -->
      </div>
    </div>
    <!-- Modal -->
    <DongBoVatTuModal ref="dongbovattu" :tentat_ccbs="tentat_ccbs" :tag="tag" @successSync="successSync" />
    <TimKiemMaNhomModal ref="timKiemMaNhomModal" @accept="acceptMaNhom" />

    <Footer />
  </div>
</template>
<script>
import Footer from '@/components/FooterStyle.vue'
import breadcrumb from '@/components/kylq_components/breadcrumb.vue'
import ActionTop from '@/components/kylq_components/ActionTop.vue'
import DanhMucVatTu from './DanhMucVatTu.vue'
import SuppliesAPI from './SuppliesAPI'
import { DataManager, Query, ODataV4Adaptor, Predicate } from '@syncfusion/ej2-data'
import KRequiredMarker from '@/components/kylq_components/KRequiredMarker.vue'
//CSS
import '@/assets/vendor/bootstrap-table/bootstrap-table.min.css'
import '@/assets/vendor/bootstrap-table/extensions/fixed-columns/bootstrap-table-fixed-columns.css'
//JS
import '@/assets/vendor/jquery/split'
import '@/assets/vendor/bootstrap-table/bootstrap-table.min'
import '@/assets/vendor/bootstrap-table/extensions/fixed-columns/bootstrap-table-fixed-columns.min'
//
import ThongTinMucDich from './ThongTinMucDich.vue'
import ThongTinHeThong from './ThongTinHeThong.vue'
import ThongTinDongThietBi from './ThongTinDongThietBi.vue'
import XLSX from 'xlsx'
import DongBoVatTuModal from './DongBoVatTuModal.vue'
import TimKiemMaNhomModal from './TimKiemMaNhomModal.vue'
import moment from 'moment'
import VatTuDoKiem from './VatTuDoKiem.vue'
import MucDichPhanVung from './MucDichPhanVung.vue'
import ThongTinCongNghe from './ThongTinCongNghe.vue'
export default {
  name: 'Supplies',
  components: {
    Footer,
    breadcrumb,
    ActionTop,
    DanhMucVatTu,
    ThongTinMucDich,
    ThongTinHeThong,
    ThongTinDongThietBi,
    DongBoVatTuModal,
    TimKiemMaNhomModal,
    KRequiredMarker,
    VatTuDoKiem,
    MucDichPhanVung,
    ThongTinCongNghe
  },
  computed: {
    p_vattu_id() {
      return this.vattu_selected ? this.vattu_selected.vattu_id : -1
    },
    p_hangsx_id() {
      return this.vattu_selected ? this.vattu_selected.hangsx_id : null
    }
  },
  watch: {
    p_vattu_id(val) {
      this.changeTab(this.currentTab, true)
    }
  },
  data() {
    return {
      header: {
        title: 'Quản lý danh mục vật tư',
        list: [
          {
            name: 'Dữ liệu',
            link: { name: 'Ui.cards' },
            active: false
          },
          {
            name: 'Danh mục QLVT',
            link: { name: 'Ui.buttons' },
            active: false
          },
          {
            name: 'Vật tư',
            link: { name: 'Ui.cards' },
            active: true
          }
        ]
      },
      actions: [
        {
          id: 'lay_tt',
          name: 'Lấy TT',
          active: true,
          icon_class: 'one-file-attach'
        },
        {
          id: 'nhapmoi',
          name: 'Nhập mới',
          active: true,
          icon_class: 'one-file-plus'
        },
        {
          id: 'ghilai',
          name: 'Ghi lại',
          active: true,
          icon_class: 'one-save'
        },
        {
          id: 'huy',
          name: 'Huỷ',
          active: true,
          icon_class: 'nc-icon-glyph ui-1_circle-remove'
        },
        {
          id: 'xoa',
          name: 'Xoá',
          active: true,
          icon_class: 'one-trash'
        },
        {
          id: 'nhap_excel',
          name: 'Nhập Excel',
          active: true,
          icon_class: 'icon one-xlsx-import'
        },
        {
          id: 'file_mau',
          name: 'File mẫu',
          active: true,
          icon_class: 'icon one-excel'
        },
        {
          id: 'dongbo_vt',
          name: 'Đồng bộ VT',
          active: true,
          icon_class: 'one-sync1'
        }
      ],
      radioDanhMuc: 1,
      danhsach_vattu_cha: [],
      vattu_cha_selected: null,
      danhsach_mucdich: [],
      mucdich_selected: null,
      danhsach_donvitinh: [],
      donvitinh_selected: null,
      danhsach_loaithietbi: [],
      loaithietbi_selected: null,
      danhsach_loaivattu: [],
      loaivattu_selected: null,
      danhsach_onu: [],
      onu_selected: null,
      danhsach_vattu_mscp: [],
      vattu_mscp_selected: null,
      danhsach_chungloai: [],
      chungloai_selected: null,
      danhsach_hangsx: [],
      hangsx_selected: null,
      danhsach_danhmuc_vattu: [],
      currentTab: 1,
      vattu_selected: null,
      khoa_nhapmoi: false,
      thongtin_vattu: {
        ma_vattu: '',
        ten_vattu: '',
        part_number: '',
        ma_nhom: '',
        ghichu: '',
        sothangphanbo: '0',
        muc: '0',
        dongia_vt: '0',
        tentat: '',
        check_dungchochungtu: false
      },
      file: null,
      danhsach_excel: [],
      btnUploadImage: false,
      ckbSerial: false,
      tu_sinh_ma_vt: false,
      s: '',
      result: '',
      vattu_id: -1,
      vattu_cha_id: -1,
      ds_hinhanh: [],
      SERVER_IMAGES: {
        IP: '10.70.52.164:8081',
        BASE_URL: 'http://10.70.52.164:8081/FILE/',
        NO_IMAGE_URL: 'http://10.70.52.164:8081/FILE/no-image-available.jpg',
        URL: 'http://10.70.52.164:8081/FILE/UploadFile.aspx?version=v2'
      },
      tt_nd: null,
      tentat_ccbs: '',
      DS_VT_CHA: [],
      tag: '0',
      thongTinCongNghe: {
        input: {}
      }
    }
  },
  methods: {
    onActionClick(action) {
      if (action.id == 'lay_tt') {
        this.layTT()
      } else if (action.id == 'nhap_excel') {
        this.nhapExcel()
      } else if (action.id == 'file_mau') {
        this.taiFileMau()
      } else if (action.id == 'dongbo_vt') {
        this.$refs.dongbovattu.showModal()
      } else if (action.id == 'nhapmoi') {
        console.log('this.thongtin_vattu.ma_vattu', this.thongtin_vattu.ma_vattu)
        if (this.khoa_nhapmoi && this.radioDanhMuc == 1) {
          this.$toast.error('Không được phép tạo mới vật tư. Hãy dùng chức năng đồng bộ !')
          return
        }
        this.danhsach_vattu_cha = this.DS_VT_CHA
        if (this.danhsach_vattu_cha.length > 0) {
          this.vattu_cha_selected = this.danhsach_vattu_cha[0].vattu_id
        } else {
          this.danhsach_vattu_cha = null
        }
        if (this.vattu_selected != null) {
          this.setActiveActions(1)
          this.vattu_cha_selected = this.vattu_selected.vattu_id
        } else {
          this.setActiveActions(1)
          this.vattu_cha_selected = null
          //this.$toast.error('Hãy chọn vật tư ở danh mục vật tư và nhấn thêm mới')
        }
      } else if (action.id == 'ghilai') {
        this.ghiDuLieu()
      } else if (action.id == 'huy') {
        if (!this.actions[this.actions.findIndex((x) => x.id == 'nhapmoi')].active) {
          this.setActiveActions(0)
        }
        if (this.vattu_selected != null) {
          this.tvVatTuFocusedNodeChanged(this.vattu_selected)
        } else {
          this.setActiveActions(0)
        }
      } else if (action.id == 'xoa') {
        if (!this.KIEMTRA_CAPNHAT(this.tag)) {
          return
        }
        if (this.vattu_selected == null || this.vattu_selected.vattu_id == -1) {
          this.$toast.error('Hãy chọn vật tư muốn xoá!')
          return
        }
        //Xoá image

        //Xoá vật tư
        this.$confirm('Bạn thật sự muốn xóa dữ liệu không?', '', {
          confirmButtonText: 'Đồng ý',
          cancelButtonText: 'Huỷ',
          type: 'info'
        }).then(() => {
          this.fn_xoa_vattu(this.vattu_selected.vattu_id)
        })
      }
    },
    setActiveActions(kieu) {
      for (let i = 0; i < this.actions.length; i++) {
        if (this.actions[i].id != 'lay_tt' && this.actions[i].id != 'file_mau' && this.actions[i].id != 'dongbo_vt' && this.actions[i].id != 'nhap_excel') {
          this.actions[i].active = false
        }
      }
      this.btnUploadImage = false
      if (kieu == -1) {
        //bắt đầu
        this.actions[this.actions.findIndex((x) => x.id == 'ghilai')].active = true
        this.actions[this.actions.findIndex((x) => x.id == 'huy')].active = true
      } else if (kieu == 0) {
        //bắt đầu
        this.actions[this.actions.findIndex((x) => x.id == 'nhapmoi')].active = true
        this.btnUploadImage = false
        this.clear()
      } else if (kieu == 1) {
        //thêm mới
        this.actions[this.actions.findIndex((x) => x.id == 'ghilai')].active = true
        this.actions[this.actions.findIndex((x) => x.id == 'huy')].active = true
        this.btnUploadImage = false
        this.clear()
      } else if (kieu == 2) {
        //Huỷ
        this.actions[this.actions.findIndex((x) => x.id == 'nhapmoi')].active = true
        this.actions[this.actions.findIndex((x) => x.id == 'xoa')].active = true
        this.btnUploadImage = false
        this.clear()
      } else if (kieu == 3) {
        //edit
        this.actions[this.actions.findIndex((x) => x.id == 'nhapmoi')].active = true
        this.actions[this.actions.findIndex((x) => x.id == 'ghilai')].active = true
        this.actions[this.actions.findIndex((x) => x.id == 'huy')].active = true
        this.actions[this.actions.findIndex((x) => x.id == 'xoa')].active = true
        this.btnUploadImage = true
      }
    },
    clear() {
      this.thongtin_vattu.ma_vattu = ''
      this.thongtin_vattu.ten_vattu = ''
      this.thongtin_vattu.part_number = ''
      this.hangsx_selected = null
      this.donvitinh_selected = null
      //default index loại thiết bị đầu, nhập mới hay cập nhật bắt buộc phải có loại thiết bị
      this.loaithietbi_selected = null
      if (this.danhsach_loaithietbi.length > 0) {
        this.loaithietbi_selected = this.danhsach_loaithietbi[0].LOAITBI_ID
      }
      this.loaivattu_selected = null
      this.thongtin_vattu.ghichu = ''
      this.thongtin_vattu.ma_nhom = ''
      this.thongtin_vattu.tentat = ''
      this.vattu_cha_selected = null
      this.thongtin_vattu.muc = '0'
      this.thongtin_vattu.sothangphanbo = '0'
      this.thongtin_vattu.check_dungchochungtu = false
      this.vattu_mscp_selected = null
      this.ckbSerial = false
      this.s = ''
      this.result = ''
      this.vattu_id = -1
      this.vattu_cha_id = -1

      this.ds_hinhanh = []
      this.danhsach_vattu_cha = this.DS_VT_CHA
      // listImage.Clear();
      this.$refs.thongtinmucdich.clearData()
      this.$refs.thongtindongthietbi.clearData()
      this.$refs.thongtinhethong.clearData()
      this.$refs.vattudokiem.clearData()
      this.$refs.mucdichphanvung.clearData()
    },
    changeMaVT(event) {
      this.thongtin_vattu.ma_vattu = event.target.value
      console.log('changeMaVT', event)
    },
    textChangeMaVT(event) {
      let value = event.target.value
      if (['19.06.03.01.01', '19.06.03.01.02', '19.06.03.01.03'].indexOf(value) > -1) {
        this.chungloai_selected = 9
      } else if (['19.06.03.01.06'].indexOf(value) > -1) {
        this.chungloai_selected = 10
      } else {
        if (!this.actions[1].active) {
          this.chungloai_selected = null
        } else {
          this.chungloai_selected = this.vattu_selected ? this.vattu_selected.nhomvt_id : null
        }
      }
      this.thongtin_vattu.ma_vattu = value
    },
    async ghiDuLieu() {
      if (!this.KIEMTRA_CAPNHAT(this.tag)) {
        return
      }
      //check dữ liệu
      let kieu = this.actions[this.actions.findIndex((x) => x.id == 'nhapmoi')].active ? 0 : 1
      if (!this.kiemtra_dulieu(kieu)) {
        return
      }
      let ip_cn = await this.$root.token.getIP()
      //tạo dữ liêu
      let ds_para = {
        TXT_MA_VT: this.thongtin_vattu.ma_vattu.trim(),
        TU_SINH_MA_VT: this.tu_sinh_ma_vt ? 1 : 0,
        THEMMOI: kieu,
        KIEU_CHECK: this.radioDanhMuc,
        TXT_TEN_VT: this.thongtin_vattu.ten_vattu,
        TXT_PART_NUMBER: this.thongtin_vattu.part_number.trim() != '' ? this.thongtin_vattu.part_number.trim() : null,
        CBO_HANG_SX: this.hangsx_selected,
        CBO_DVITINH: this.donvitinh_selected,
        FC_VATTU_ID: kieu == 1 ? 0 : this.vattu_selected.vattu_id,
        CBO_LOAI_TBI: this.loaithietbi_selected,
        NGAY_CN: moment(new Date()).format('DD/MM/YYYY HH:mm:ss'),
        NGUOI_CN: this.$root.token.getUserName(),
        MAY_CN: ip_cn ? ip_cn.toString() : '127.0.0.1',
        IP_CN: ip_cn ? ip_cn.toString() : '127.0.0.1'
      }
      let js_vattu = {
        VATTU_ID: kieu == 1 ? 0 : this.vattu_selected.vattu_id,
        MA_VT: this.thongtin_vattu.ma_vattu.trim(),
        VATTU_CHA_ID: this.vattu_cha_selected,
        MUC: this.thongtin_vattu.muc.trim() != '' ? Number(this.thongtin_vattu.muc.trim()) : 0,
        TEN_VT: this.thongtin_vattu.ten_vattu,
        DVITINH_ID: this.donvitinh_selected,
        CHUNGTU: this.thongtin_vattu.check_dungchochungtu ? 1 : 0,
        SERIAL: this.ckbSerial ? 1 : 0,
        SOTHANG_PB: this.thongtin_vattu.sothangphanbo.trim() != '' ? Number(this.thongtin_vattu.sothangphanbo.trim()) : 0,
        MOTA: null,
        GHICHU: this.thongtin_vattu.ghichu.trim(),
        LOAITBI_ID: this.loaithietbi_selected,
        LOAIVT_ID: this.loaivattu_selected,
        HIENTHI: this.radioDanhMuc == 2 ? 0 : this.radioDanhMuc == 1 ? 1 : null,
        HANGSX_ID: this.hangsx_selected,
        ONUBNM_ID: this.onu_selected,
        VATTU_MS_ID: this.vattu_mscp_selected,
        NHOMVT_ID: this.chungloai_selected,
        BB_THUHOI: null,
        MA_NHOM: this.thongtin_vattu.ma_nhom,
        DONGIA_VT: this.thongtin_vattu.dongia_vt.trim() != '' ? Number(this.thongtin_vattu.dongia_vt.trim()) : 0,
        PART_NUMBER: this.thongtin_vattu.part_number.trim() != '' ? this.thongtin_vattu.part_number.trim() : null,
        THUTU: null,
        TENTAT: this.thongtin_vattu.tentat.trim(),
        DONVI_ID: null,
        TENVATTU: null,
        NGAY_CN: moment(new Date()).format('DD/MM/YYYY HH:mm:ss'),
        NGUOI_CN: this.$root.token.getUserName(),
        MAY_CN: ip_cn ? ip_cn.toString() : '127.0.0.1',
        IP_CN: ip_cn ? ip_cn.toString() : '127.0.0.1'
      }
      //lấy chuỗi mô tả
      if (this.result != null && this.result.trim() != '') {
        js_vattu.MOTA = this.s + this.result
      } else {
        js_vattu.MOTA = this.s
      }
      //

      //thực thi
      this.$confirm(kieu == 1 ? 'Bạn có muốn thêm mới vật tư này không ?' : 'Bạn có muốn chỉnh sửa thông tin của vật tư có mã là ' + this.vattu_selected.ma_vt + ' này không ?', 'Thông báo', {
        confirmButtonText: 'Đồng ý',
        cancelButtonText: 'Huỷ',
        type: 'info'
      }).then(() => {
        this.fn_ins_up_vattu(ds_para, js_vattu, kieu)
      })
    },
    kiemtra_dulieu(kieu) {
      if (this.thongtin_vattu.ma_vattu.trim() == '' && !this.tu_sinh_ma_vt) {
        this.$toast.error('Mã vật tư không được để trống')
        this.$refs.inputMaVatTu.focus()
        return false
      }
      if (this.thongtin_vattu.ma_vattu.trim().length > 100) {
        this.$toast.error('Mã vật tư không được quá 100 ký tự')
        this.$refs.inputMaVatTu.focus()
        return false
      }

      const index = this.danhsach_danhmuc_vattu.findIndex((x) => x.ma_vt == this.thongtin_vattu.ma_vattu.trim())
      if (index > -1 && kieu == 1) {
        this.$toast.error('Mã vật tư đã tồn tại')
        this.$refs.inputMaVatTu.focus()
        return false
      }
      if (this.thongtin_vattu.ten_vattu.trim() == '') {
        this.$toast.error('Tên vật tư không được để trống')
        this.$refs.inputTenVatTu.focus()
        return false
      }
      if (this.thongtin_vattu.ten_vattu.trim().length > 500) {
        this.$toast.error('Tên vật tư không vượt quá 500 ký tự')
        this.$refs.inputTenVatTu.focus()
        return false
      }
      if (this.thongtin_vattu.part_number.trim().length > 200) {
        this.$toast.error('Part number không vượt quá 200 ký tự')
        this.$refs.inputPartNumber.focus()
        return false
      }
      if (this.thongtin_vattu.ma_nhom.trim().length > 100) {
        this.$toast.error('Mã nhóm không vượt quá 100 ký tự')
        this.$refs.inputMaNhom.focus()
        return false
      }
      if (this.thongtin_vattu.ghichu.trim().length > 500) {
        this.$toast.error('Ghi chú không vượt quá 100 ký tự')
        this.$refs.inputGhiChu.focus()
        return false
      }
      //Nhập mới cũng bắt buộc loại thiết bị
      if (this.loaithietbi_selected == null) {
        this.$toast.error('Bạn phải chọn loại thiết bị')
        return false
      }
      if (kieu == 0) {
        if (this.vattu_selected.vattu_ms_id == null || this.vattu_selected.vattu_ms_id.toString().trim() == '') {
        } else {
          // Nếu là đồng bộ vật tư về
          if (this.thongtin_vattu.ma_vattu.trim() != this.vattu_selected.ma_vt) {
            this.$toast.error('Mã vật tư được đồng bộ về bạn không được phép chỉnh sửa mã vật tư')
            return false
          }
          let part_kt = this.vattu_selected.part_number != null && this.vattu_selected.part_number.toString() != '' ? this.vattu_selected.part_number.toString() : ''
          if (this.thongtin_vattu.part_number != part_kt) {
            this.$toast.error('Mã vật tư được đồng bộ về bạn không được phép chỉnh sửa part number')
            return false
          }
          //comment lại theo yêu cầu Mạnh Cường, Chị Quế 13/6/2023
          // var ten_vt_kt=(this.vattu_selected.ten_vt!=null&&this.vattu_selected.ten_vt.toString()!='')?this.vattu_selected.ten_vt.toString():''
          // // console.log('ten_vt_kt', ten_vt_kt)
          // // var tenvt=''
          // // if(ten_vt_kt!=''){
          // //     var tenVTArrray=ten_vt_kt.split('-')
          // //     if(tenVTArrray.length>1){
          // //         tenvt=tenVTArrray.slice(1).join('-')
          // //     }else{
          // //         tenvt=this.ten_vt_kt
          // //     }
          // // }
          // // console.log('tenvt', tenvt)
          // // console.log('ten_vattu', this.thongtin_vattu.ten_vattu.trim())
          // if(this.thongtin_vattu.ten_vattu.trim()!=ten_vt_kt.trim()){
          //     this.$toast.error('Mã vật tư được đồng bộ về bạn không được phép chỉnh sửa tên vật tư')
          //     return false
          // }
          if (this.loaithietbi_selected != this.vattu_selected.loaitbi_id) {
            this.$toast.error('Mã vật tư được đồng bộ về bạn không được phép chỉnh sửa loại thiết bị')
            return false
          }
          if (this.donvitinh_selected != this.vattu_selected.dvitinh_id) {
            this.$toast.error('Mã vật tư được đồng bộ về bạn không được phép chỉnh sửa loại thiết bị')
            return false
          }
        }
        // if(this.loaithietbi_selected==null){
        //     this.$toast.error('Bạn phải chọn loại thiết bị')
        //     return false
        // }
        //https://cntt.vnpt.vn/browse/BSS-74155
        if (this.vattu_cha_selected == this.vattu_selected.vattu_id) {
          this.$toast.error('Không được phép chọn vật tư cha là chính vật tư hiện tại')
          return false
        }
      }
      return true
    },
    taiFileMau() {
      this.$confirm('Bạn có muốn tải file mẫu về không ?', 'Thông báo', {
        confirmButtonText: 'Đồng ý',
        cancelButtonText: 'Huỷ',
        type: 'info'
      }).then(() => {
        //dùng tạm
        window.open('/static/files/file_mau_vattu.xlsx')
      })
    },
    KIEMTRA_CAPNHAT(tag) {
      if (tag == '-1') {
        this.$toast.error('Bạn không có quyền tác động')
        return false
      }
      return true
    },
    nhapExcel() {
      if (this.radioDanhMuc == 1) {
        this.$refs.fileInput.value = null
        document.getElementById('file').click()
      } else {
        this.$toast.error('Chức năng thêm mới vật tư bằng excel hiện tại không hỗ trợ với CCDC')
      }
    },
    clickTimKiemMaNhom() {
      this.$refs.timKiemMaNhomModal.showModal()
    },
    acceptMaNhom(item) {
      if (item) {
        this.thongtin_vattu.ma_nhom = item.code
      }
    },
    uploadImage() {
      this.$refs.fileInputImage.value = null
      document.getElementById('fileImage').click()
    },
    onChangeFile(event) {
      this.file = event.target.files[0]
      this.readFileExcel()
    },
    async onChangeFileImage(event) {
      let dsFiles = event.target.files
      let formData = new FormData()
      let sohinh = 0
      for (var file of dsFiles) {
        formData.append('files', file)
        sohinh++
      }
      let result = await this.upload_file(formData, { headers: { 'Content-Type': 'multipart/form-data' } })
      if (result.length > 0) {
        var ds_hinhhanh_temp = []
        let resultImageMoi = await this.link_view_file(result.join(','))
        for (let i = 0; i < resultImageMoi.length; i++) {
          ds_hinhhanh_temp.push({
            name: resultImageMoi[i].file_name,
            url: resultImageMoi[i].file_url,
            check: false
          })
        }
        var index = this.ds_hinhanh.length
        //this.ds_hinhanh=this.ds_hinhanh.concat(ds_hinhhanh_temp.map((x, i)=>Object.assign(x, {id: index+i+1})))

        //cập nhật lại hình ảnh vào vật tư
        let ds_image = this.ds_hinhanh
          .concat(ds_hinhhanh_temp.map((x, i) => Object.assign(x, { id: index + i + 1 })))
          .map((x) => x.name)
          .join('|')
        let resultCapNhat = await this.fn_update_mota(this.vattu_selected.vattu_id, ds_image)
        if (resultCapNhat && resultCapNhat.ERROR_CODE == 1) {
          this.s = ds_image
          this.ds_hinhanh = this.ds_hinhanh.concat(ds_hinhhanh_temp.map((x, i) => Object.assign(x, { id: index + i + 1 })))
          this.$toast.success('Đã upload được ' + sohinh + ' files')
        } else if (resultCapNhat && resultCapNhat.ERROR_CODE == 0) {
          this.$toast.error(resultCapNhat.MESSAGE)
        }
      }
    },
    async removeImage() {
      let ds_check = this.ds_hinhanh.filter((x) => x.check == true)
      let ds_uncheck = this.ds_hinhanh.filter((x) => x.check == false)
      if (ds_check.length > 0) {
        let comfirm = await this.confirm('Bạn thật sự muốn xóa ' + ds_check.length + ' được chọn', 'Thông báo')
        if (comfirm == 0) return
        let ds_image = ds_uncheck.map((x) => x.name).join('|')
        let resultCapNhat = await this.fn_update_mota(this.vattu_selected.vattu_id, ds_image)
        if (resultCapNhat && resultCapNhat.ERROR_CODE == 1) {
          this.s = ds_image
          this.ds_hinhanh = ds_uncheck.map((x, index) => Object.assign(x, { id: index + 1 }))
          this.$toast.success('Đã xoá ' + ds_check.length + ' files')
        } else if (resultCapNhat && resultCapNhat.ERROR_CODE == 0) {
          this.$toast.error(resultCapNhat.MESSAGE)
        }
      }
    },
    async confirm(message, title) {
      try {
        let result = await this.$confirm(message, title, {
          confirmButtonText: 'Đồng ý',
          cancelButtonText: 'Hủy'
        })
        return 1
      } catch (e) {
        return 0
      }
    },
    readFileExcel() {
      this.danhsach_excel = []
      let columns
      try {
        this.loading(true)
        if (this.checkfile()) {
          const reader = new FileReader()
          reader.onload = (e) => {
            /* Parse data */
            const bstr = e.target.result
            const wb = XLSX.read(bstr, { type: 'binary' })
            /* Get first worksheet */
            const wsname = wb.SheetNames[0]
            const ws = wb.Sheets[wsname]
            /* Convert array of arrays */
            const data = XLSX.utils.sheet_to_json(ws, { header: 1 })
            columns = data[0]
            //MA_VT, TEN_VT, DVT, MA_VT_CHA, LOAI_TBI, MA_NHOM, CHUNGTU, DONGIA, MD_KEHOACH_VT, MD_CAPPHAT
            if (this.kiemTraDuLieu(columns)) {
              for (var i = 1; i < data.length; i++) {
                this.danhsach_excel.push({
                  MA_VT: data[i][0] != undefined ? data[i][0] : null,
                  TEN_VT: data[i][1] != undefined ? data[i][1] : null,
                  DVT: data[i][2] != undefined ? data[i][2] : null,
                  MA_VT_CHA: data[i][3] != undefined ? data[i][3] : null,
                  LOAI_TBI: data[i][4] != undefined ? data[i][4] : null,
                  MA_NHOM: data[i][5] != undefined ? data[i][5] : null,
                  CHUNGTU: data[i][6] != undefined ? data[i][6] : null,
                  DONGIA: data[i][7] != undefined ? data[i][7] : null,
                  MD_KEHOACH_VT: data[i][8] != undefined ? data[i][8] : null,
                  MD_CAPPHAT: data[i][9] != undefined ? data[i][9] : null
                })
              }
              if (this.danhsach_excel.length > 0) {
                this.docfile_vattu(this.danhsach_excel)
              } else {
                this.$toast.error('Danh sách trống!')
              }
            }
          }
          reader.readAsBinaryString(this.file)
        }
      } catch (error) {
        this.$toast.error('Có lỗi xảy ra khi đọc file')
      } finally {
        this.loading(false)
      }
    },
    checkfile: function () {
      if (this.file == null) {
        this.$toast.error('Chưa chọn file excel')
        return false
      }
      if (this.file.type != 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet') {
        this.$toast.error('File upload không đúng định dạng')
        return false
      }
      return true
    },
    kiemTraDuLieu: function (columns) {
      if (columns == null) {
        this.$toast.error('File có cấu trúc không hợp lệ!')
        return false
      }
      if (columns.length <= 1) {
        this.$toast.error('File excel gồm 10 trường:  MA_VT, TEN_VT, DVT, MA_VT_CHA, LOAI_TBI, MA_NHOM, CHUNGTU, DONGIA, MD_KEHOACH_VT, MD_CAPPHAT!')
        return false
      }
      if (!columns[0] || columns[0].toString().toUpperCase() != 'MA_VT') {
        this.$toast.error('Tên cột đầu tiên trong file không đúng định dạng : MA_VT')
        return false
      }
      if (!columns[1] || columns[1].toString().toUpperCase() != 'TEN_VT') {
        this.$toast.error('Tên cột thứ 2 trong file không đúng định dạng : TEN_VT')
        return false
      }
      if (!columns[2] || columns[2].toString().toUpperCase() != 'DVT') {
        this.$toast.error('Tên cột thứ 3 trong file không đúng định dạng : DVT')
        return false
      }
      if (!columns[3] || columns[3].toString().toUpperCase() != 'MA_VT_CHA') {
        this.$toast.error('Tên cột thứ 4 trong file không đúng định dạng : MA_VT_CHA')
        return false
      }
      if (!columns[4] || columns[4].toString().toUpperCase() != 'LOAI_TBI') {
        this.$toast.error('Tên cột thứ 5 trong file không đúng định dạng : LOAI_TBI')
        return false
      }
      if (!columns[5] || columns[5].toString().toUpperCase() != 'MA_NHOM') {
        this.$toast.error('Tên cột thứ 6 trong file không đúng định dạng : MA_NHOM')
        return false
      }
      if (!columns[6] || columns[6].toString().toUpperCase() != 'CHUNGTU') {
        this.$toast.error('Tên cột thứ 7 trong file không đúng định dạng : MA_NHOM')
        return false
      }
      if (!columns[7] || columns[7].toString().toUpperCase() != 'DONGIA') {
        this.$toast.error('Tên cột thứ 8 trong file không đúng định dạng : MA_NHOM')
        return false
      }
      if (!columns[8] || columns[8].toString().toUpperCase() != 'MD_KEHOACH_VT') {
        this.$toast.error('Tên cột thứ 9 trong file không đúng định dạng : MA_NHOM')
        return false
      }
      if (!columns[9] || columns[9].toString().toUpperCase() != 'MD_CAPPHAT') {
        this.$toast.error('Tên cột thứ 10 trong file không đúng định dạng : MA_NHOM')
        return false
      }
      return true
    },
    changeTab(tab, force) {
      if (this.currentTab == tab && !force) {
        return
      }
      this.currentTab = tab
      if (this.currentTab == 1) {
        this.$refs.thongtinmucdich.initDuLieu()
      } else if (this.currentTab == 2) {
        this.$refs.thongtindongthietbi.initDuLieu()
      } else if (this.currentTab == 3) {
        this.$refs.thongtinhethong.initDuLieu()
      } else if (this.currentTab == 4) {
        //Vật tư đo kiểm
        this.$refs.vattudokiem.initDuLieu()
      } else if (this.currentTab == 5) {
        //Mục đích phân vùng
        this.$refs.mucdichphanvung.initDuLieu()
      } else if (this.currentTab == 6) {
        this.currentTab = 0
        setTimeout(() => {
          this.currentTab = 6
          this.thongTinCongNghe.input = {
            v_vattu_id: this.p_vattu_id
          }
        }, 200)
      }
    },
    isNumber(evt) {
      evt = evt ? evt : window.event
      var charCode = evt.which ? evt.which : evt.keyCode
      if (charCode > 31 && (charCode < 48 || charCode > 57)) {
        evt.preventDefault()
      } else {
        return true
      }
    },
    openImage(item) {
      window.open(item.url, '_blank')
    },
    clearMaNhom() {
      this.thongtin_vattu.ma_nhom = ''
    },
    async changeRadioDanhMuc(value) {
      this.setActiveActions(0)
      this.vattu_cha_id = 0
      this.vattu_id = 0
      this.vattu_selected = null
      this.radioDanhMuc = value

      this.danhsach_vattu_cha = await this.lay_danhsach_vattu_cha(this.radioDanhMuc)
      // await this.sp_ht_ds_danhmuc_vattu({
      //     db_tbi: null,
      //     ht_id: null,
      //     kieu_check: this.radioDanhMuc
      // })
      await this.layTT()
      await this.$refs.danhmucvattu.initFocusFirstNode()
    },
    onFilteringVatTuCha: function (e) {
      var query = new Query()
      let predicate
      //frame the query based on search string with filter type.
      if (e.text.trim() !== '') {
        predicate = new Predicate('ten_vt', 'contains', e.text.trim(), true)
        //predicate = predicate.or('TEN_KHO', 'contains', e.text);
        query.where(predicate)
      }
      //pass the filter data source, filter query to updateData method.
      e.updateData(this.danhsach_vattu_cha, query)
    },
    async tvVatTuFocusedNodeChanged(vattu) {
      //root node
      if (vattu == undefined || vattu.vattu_id == -1) {
        console.log('tvVatTuFocusedNodeChanged root')
        this.vattu_selected = null
        this.clear()
        this.setActiveActions(0)
        return
      }

      this.vattu_selected = vattu
      console.log('tvVatTuFocusedNodeChanged', this.vattu_selected)
      if (!this.actions[this.actions.findIndex((x) => x.id == 'nhapmoi')].active) {
        // mới thêm
        this.vattu_cha_selected = vattu.vattu_id
        this.vattu_cha_id = vattu.vattu_id
      } else {
        if (vattu == null || vattu == undefined) {
          this.setActiveActions(0)
          return
        }
        this.setActiveActions(3)
        //fillter combox cha
        //sp_lay_ds_vattu_capduoi
        let dt = await this.sp_lay_ds_vattu_capduoi(vattu.vattu_id)
        if (dt.length == 0) {
          this.danhsach_vattu_cha = this.DS_VT_CHA
        }
        this.danhsach_vattu_cha = this.DS_VT_CHA.filter((x) => {
          return dt.findIndex((i) => i.vattu_id == x.vattu_id) == -1
        })

        //
        await this.treeviewSelect(vattu)
      }
    },
    async treeviewSelect(vattu) {
      console.log('treeviewSelect', vattu)
      this.vattu_id = vattu.vattu_id
      this.vattu_cha_id = vattu.vattu_cha_id
      //init vattu
      this.thongtin_vattu.ma_vattu = vattu.ma_vt != null ? vattu.ma_vt.toString() : ''

      // let array=vattu.ten_vt?vattu.ten_vt.toString().split('-'):[]
      this.thongtin_vattu.ten_vattu = vattu.ten_vt ? vattu.ten_vt.toString() : ''

      this.thongtin_vattu.ghichu = vattu.ghichu != null ? vattu.ghichu.toString() : ''
      if (vattu.chungtu != null && vattu.chungtu == 1) {
        this.thongtin_vattu.check_dungchochungtu = true
      } else {
        this.thongtin_vattu.check_dungchochungtu = false
      }
      if (vattu.serial && vattu.serial == 1) {
        this.ckbSerial = true
      } else {
        this.ckbSerial = false
      }
      this.thongtin_vattu.sothangphanbo = vattu.sothang_pb != null ? vattu.sothang_pb.toString() : ''
      this.thongtin_vattu.muc = vattu.muc != null ? vattu.muc.toString() : ''
      this.donvitinh_selected = vattu.dvitinh_id
      if (vattu.vattu_cha_id != null && vattu.vattu_cha_id != -1) {
        this.vattu_cha_selected = vattu.vattu_cha_id
      } else {
        this.vattu_cha_selected = null
      }

      this.loaithietbi_selected = vattu.loaitbi_id
      this.loaivattu_selected = vattu.loaivt_id
      this.thongtin_vattu.part_number = vattu.part_number ? vattu.part_number.toString() : ''
      this.thongtin_vattu.ma_nhom = vattu.ma_nhom ? vattu.ma_nhom.toString() : ''
      this.thongtin_vattu.tentat = vattu.tentat ? vattu.tentat.toString() : ''

      this.hangsx_selected = vattu.hangsx_id
      this.onu_selected = vattu.onubnm_id
      //chủng loai-->nhomvt_id
      this.chungloai_selected = vattu.nhomvt_id
      this.vattu_mscp_selected = vattu.vattu_ms_id

      this.thongtin_vattu.dongia_vt = vattu.dongia_vt != null ? vattu.dongia_vt.toString() : ''

      await this.load_img()

      //load data tab select
      if (this.currentTab == 1) {
        setTimeout(() => {
          this.$refs.thongtinmucdich.initDuLieu()
        }, 200)
      } else if (this.currentTab == 2) {
        setTimeout(() => {
          this.$refs.thongtindongthietbi.initDuLieu()
        }, 200)
      } else if (this.currentTab == 3) {
        setTimeout(() => {
          this.$refs.thongtinhethong.initDuLieu()
        }, 200)
      } else if (this.currentTab == 4) {
        setTimeout(() => {
          this.$refs.vattudokiem.initDuLieu()
        }, 200)
      } else if (this.currentTab == 5) {
        setTimeout(() => {
          this.$refs.mucdichphanvung.initDuLieu()
        }, 200)
      }
    },
    async load_img() {
      this.s = ''
      this.result = ''
      this.ds_hinhanh = []
      //cũ
      var ds_hinhhanh_temp = []
      //chuỗi hệ thống mới
      var chuoi_moi = ''
      //
      let data = await this.lay_tt_anh(this.vattu_id)
      if (data.length > 0) {
        this.s = data[0].mota ? data[0].mota : ''
      }
      if (this.s == '') {
        return
      }
      let words = this.s.split('|')
      for (let i = 0; i < words.length; i++) {
        if (words[i] != '') {
          console.log('words[i]', words[i])
          //Hình ảnh từ hệ thống mới
          if (words[i].includes('DHSX/')) {
            chuoi_moi += words[i] + ','
          } else {
            //hình ảnh hệ thống cũ
            var imgLink = words[i]
            if (!words[i].includes(this.SERVER_IMAGES.IP)) {
              imgLink = this.SERVER_IMAGES.BASE_URL + this.tentat_ccbs + '_VT/' + words[i]
            }
            ds_hinhhanh_temp.push({
              name: words[i],
              url: imgLink,
              check: false
            })
          }
        }
      }
      //
      if (chuoi_moi != '') {
        //link_view_file
        let resultImageMoi = await this.link_view_file(chuoi_moi)
        for (let i = 0; i < resultImageMoi.length; i++) {
          ds_hinhhanh_temp.push({
            name: resultImageMoi[i].file_name,
            url: resultImageMoi[i].file_url,
            check: false
          })
        }
      }
      //test
      // let resultImageMoi=await this.link_view_file('DHSX/QUANTRI/HO_SO/HPG/03202201/dhsx_it3_1_20220301_083753_ftth_phuc89_dlk.png,')
      // for(let i=0;i<resultImageMoi.length;i++){
      //     ds_hinhhanh_temp.push({
      //         name:resultImageMoi[i].file_name,
      //         url:resultImageMoi[i].file_url,
      //         check:false
      //     })
      // }

      this.ds_hinhanh = ds_hinhhanh_temp.map((x, index) => Object.assign(x, { id: index + 1 }))
      console.log('ds_hinhanh', this.ds_hinhanh)
    },
    successSync() {
      this.layTT()
    },
    async layTT() {
      this.vattu_selected = null
      this.clear()
      if (this.radioDanhMuc == 1) {
        let obj = this.$refs.danhmucvattu.getObjectTieuChi()
        await this.sp_ht_ds_danhmuc_vattu({
          db_tbi: obj.db_tbi,
          ht_id: obj.ht_id,
          kieu_check: this.radioDanhMuc
        })
      } else if (this.radioDanhMuc == 2) {
        await this.sp_ht_ds_danhmuc_vattu({
          db_tbi: null,
          ht_id: null,
          kieu_check: this.radioDanhMuc
        })
      }
    },
    clickFilter() {
      this.layTT()
    },
    async refreshAfterFocusVattu(vattu_id) {
      if (vattu_id != null && vattu_id != -1) {
        this.danhsach_vattu_cha = await this.lay_danhsach_vattu_cha(this.radioDanhMuc)
        if (this.radioDanhMuc == 1) {
          let obj = this.$refs.danhmucvattu.getObjectTieuChi()
          await this.sp_ht_ds_danhmuc_vattu({
            db_tbi: obj.db_tbi,
            ht_id: obj.ht_id,
            kieu_check: this.radioDanhMuc
          })
        } else if (this.radioDanhMuc == 2) {
          await this.sp_ht_ds_danhmuc_vattu({
            db_tbi: null,
            ht_id: null,
            kieu_check: this.radioDanhMuc
          })
        }
        setTimeout(() => {
          this.$refs.danhmucvattu.focusNode(vattu_id)
        }, 500)
      } else {
        this.setActiveActions(0)
        this.danhsach_vattu_cha = await this.lay_danhsach_vattu_cha(this.radioDanhMuc)
        if (this.radioDanhMuc == 1) {
          let obj = this.$refs.danhmucvattu.getObjectTieuChi()
          await this.sp_ht_ds_danhmuc_vattu({
            db_tbi: obj.db_tbi,
            ht_id: obj.ht_id,
            kieu_check: this.radioDanhMuc
          })
        } else if (this.radioDanhMuc == 2) {
          await this.sp_ht_ds_danhmuc_vattu({
            db_tbi: null,
            ht_id: null,
            kieu_check: this.radioDanhMuc
          })
        }
      }
    },
    async check_khoa_tao_moi_vattu() {
      try {
        this.loading(true)
        let response = await SuppliesAPI.lay_thamso_khoa_tao_moi_vattu(this.axios)
        this.loading(false)
        if (response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
          if (response.data.data.length > 0) {
            return true
          } else {
            return false
          }
        } else {
          return false
        }
      } catch (e) {
        this.loading(false)
        return false
      }
    },
    async lay_thamso_tu_sinh_ma_vt() {
      try {
        this.loading(true)
        let response = await SuppliesAPI.lay_thamso_tu_sinh_ma_vt(this.axios)
        this.loading(false)
        if (response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
          if (response.data.data.length > 0) {
            return true
          } else {
            return false
          }
        } else {
          return false
        }
      } catch (e) {
        this.loading(false)
        return false
      }
    },
    async lay_danhsach_vattu_cha(check) {
      try {
        this.loading(true)
        let response = await SuppliesAPI.sp_ht_ds_vt_cha(this.axios, check)
        this.loading(false)
        if (response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
          return response.data.data
        } else {
          return []
        }
      } catch (e) {
        this.loading(false)
        return []
      }
    },
    async sp_lay_ds_vattu_capduoi(vattu_id) {
      try {
        this.loading(true)
        let response = await SuppliesAPI.sp_lay_ds_vattu_capduoi(this.axios, vattu_id)
        this.loading(false)
        if (response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
          return response.data.data
        } else {
          return []
        }
      } catch (e) {
        this.loading(false)
        return []
      }
    },
    async thongtin_nguoidung() {
      try {
        this.loading(true)
        let response = await SuppliesAPI.thongtin_nguoidung(this.axios)
        this.loading(false)
        if (response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
          return response.data.data
        } else {
          return []
        }
      } catch (e) {
        this.loading(false)
        return []
      }
    },
    async thongtin_machine() {
      try {
        this.loading(true)
        let response = await SuppliesAPI.thongtin_machine(this.axios)
        this.loading(false)
        if (response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
          return response.data.data
        } else {
          return []
        }
      } catch (e) {
        this.loading(false)
        return []
      }
    },
    async lay_danhsach_mucdich() {
      try {
        this.loading(true)
        this.danhsach_mucdich = []
        let response = await SuppliesAPI.sp_ht_ds_danhmuc_mucdich(this.axios)
        if (response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
          //this.thuoctinh=response.data.data
          this.danhsach_mucdich = response.data.data
        } else {
          this.danhsach_mucdich = []
        }
      } catch (e) {
        this.$toast.error('Không load được danh sách mục đích')
      } finally {
        this.loading(false)
      }
    },
    async lay_danhsach_donvitinh() {
      try {
        this.loading(true)
        let response = await SuppliesAPI.lay_danhsach_donvitinh(this.axios)
        this.loading(false)
        if (response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
          return response.data.data
        } else {
          return []
        }
      } catch (e) {
        this.loading(false)
        return []
      }
    },
    async lay_danhsach_loaithietbi() {
      try {
        this.loading(true)
        let response = await SuppliesAPI.lay_danhsach_loaithietbi(this.axios)
        this.loading(false)
        if (response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
          return response.data.data
        } else {
          return []
        }
      } catch (e) {
        this.loading(false)
        return []
      }
    },
    async lay_danhsach_loaivattu() {
      try {
        this.loading(true)
        let response = await SuppliesAPI.lay_danhsach_loaivattu(this.axios)
        this.loading(false)
        if (response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
          return response.data.data
        } else {
          return []
        }
      } catch (e) {
        this.loading(false)
        return []
      }
    },
    async lay_danhsach_onu() {
      try {
        this.loading(true)
        let response = await SuppliesAPI.lay_danhsach_onu(this.axios)
        this.loading(false)
        if (response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
          return response.data.data
        } else {
          return []
        }
      } catch (e) {
        this.loading(false)
        return []
      }
    },
    async lay_vattu_mscp() {
      try {
        this.loading(true)
        let response = await SuppliesAPI.lay_vattu_mscp(this.axios)
        this.loading(false)
        if (response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
          return response.data.data
        } else {
          return []
        }
      } catch (e) {
        this.loading(false)
        return []
      }
    },
    async lay_ds_chung_loai_vt() {
      try {
        this.loading(true)
        let response = await SuppliesAPI.lay_ds_chung_loai_vt(this.axios)
        this.loading(false)
        if (response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
          return response.data.data
        } else {
          return []
        }
      } catch (e) {
        this.loading(false)
        return []
      }
    },
    async lay_danhsach_hang_sx() {
      try {
        this.loading(true)
        let response = await SuppliesAPI.lay_danhsach_hang_sx(this.axios)
        this.loading(false)
        if (response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
          return response.data.data.map((x) =>
            Object.assign(x, {
              MA_TEN_HANGSX: x.MA_HANGSX + '-' + x.TEN_HANGSX
            })
          )
        } else {
          return []
        }
      } catch (e) {
        this.loading(false)
        return []
      }
    },
    async sp_ht_ds_danhmuc_vattu(data) {
      try {
        this.loading(true)
        this.danhsach_danhmuc_vattu = []
        let response = await SuppliesAPI.sp_ht_ds_danhmuc_vattu(this.axios, data)
        if (response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
          this.danhsach_danhmuc_vattu = response.data.data
        } else {
          this.danhsach_danhmuc_vattu = []
        }
      } catch (e) {
        this.$toast.error('Không load được danh sách danh mục vật tư')
      } finally {
        this.loading(false)
      }
    },
    async lay_tt_anh(vattu_id) {
      try {
        this.loading(true)
        let response = await SuppliesAPI.lay_tt_anh(this.axios, vattu_id)
        this.loading(false)
        if (response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
          return response.data.data
        } else {
          return []
        }
      } catch (e) {
        this.loading(false)
        return []
      }
    },
    async link_view_file(list_file) {
      try {
        this.loading(true)
        let response = await SuppliesAPI.link_view_file(this.axios, list_file)
        this.loading(false)
        if (response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
          return response.data.data
        } else {
          return []
        }
      } catch (e) {
        this.loading(false)
        return []
      }
    },
    async upload_file(data, header) {
      try {
        this.loading(true)
        let response = await SuppliesAPI.upload_file(this.axios, data, header)
        this.loading(false)
        if (response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
          return response.data.data
        } else {
          return []
        }
      } catch (e) {
        this.loading(false)
        return []
      }
    },
    async fn_update_mota(vattu_id, ds_img) {
      try {
        this.loading(true)
        let response = await SuppliesAPI.fn_update_mota(this.axios, vattu_id, ds_img)
        this.loading(false)
        if (response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
          return JSON.parse(response.data.data)
        } else {
          return null
        }
      } catch (e) {
        this.loading(false)
        return null
      }
    },
    async fn_xoa_vattu(vattu_id) {
      try {
        this.loading(true)
        let response = await SuppliesAPI.fn_xoa_vattu(this.axios, vattu_id)
        this.loading(false)
        if (response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
          let result = JSON.parse(response.data.data)
          if (result && result.ERROR_CODE == 1) {
            this.$toast.success('Xóa dữ liệu vật tư thành công !')
            //refresh focus node cha
            this.refreshAfterFocusVattu(this.vattu_cha_id)
          } else {
            if (result && result.MESSAGE) {
              this.$toast.error(result.MESSAGE)
            } else {
              this.$toast.error('Không thể xóa ràng buộc')
            }
          }
        } else {
          if (response && response.data && response.data.message) {
            this.$toast.error(response.data.message)
          } else {
            this.$toast.error('Không thể xóa ràng buộc')
          }
        }
      } catch (error) {
        this.loading(false)
        if (error.data && error.data.message) {
          this.$toast.error(error.data.message)
        } else if (error.response && error.response.data && error.response.data.message) {
          this.$toast.error(error.response.data.message)
        } else {
          this.$toast.error('Không thể xóa ràng buộc')
        }
      }
    },
    //kieu=1 thêm mới, 0 cập nhật
    async fn_ins_up_vattu(ds_para, js_vattu, kieu) {
      try {
        this.loading(true)
        let response = await SuppliesAPI.fn_ins_up_vattu(this.axios, ds_para, js_vattu)
        this.loading(false)
        if (response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
          let result = JSON.parse(response.data.data)
          if (result && result.ERROR_CODE == 1) {
            this.$toast.success(kieu == 1 ? 'Thêm mới dữ liệu vật tư thành công!' : 'Cập nhật dữ liệu vật tư thành công!')
            //refresh focus node
            if (kieu == 1) {
              this.vattu_id = result.RESULT.VATTU_ID
            }
            this.setActiveActions(3)
            this.refreshAfterFocusVattu(this.vattu_id)
          } else {
            if (result && result.MESSAGE) {
              this.$toast.error(result.MESSAGE)
            } else {
              this.$toast.error(kieu == 1 ? 'Thêm mới dữ liệu vật tư không thành công!' : 'Cập nhật dữ liệu vật tư không thành công!')
            }
          }
        } else {
          if (response && response.data && response.data.message) {
            this.$toast.error(response.data.message)
          } else {
            this.$toast.error(kieu == 1 ? 'Thêm mới dữ liệu vật tư không thành công!' : 'Cập nhật dữ liệu vật tư không thành công!')
          }
        }
      } catch (error) {
        this.loading(false)
        if (error.data && error.data.message) {
          this.$toast.error(error.data.message)
        } else if (error.response && error.response.data && error.response.data.message) {
          this.$toast.error(error.response.data.message)
        } else {
          this.$toast.error(kieu == 1 ? 'Thêm mới dữ liệu vật tư không thành công!' : 'Cập nhật dữ liệu vật tư không thành công!')
        }
      }
    },
    async docfile_vattu(vds) {
      try {
        this.loading(true)
        let response = await SuppliesAPI.docfile_vattu(this.axios, vds)
        this.loading(false)
        if (response && response.data && response.data.error_code && response.data.error_code == 'BSS-00000000' && response.data.data) {
          if (response.data.data == '1') {
            this.$toast.success('Thêm mới vật tư thành công')
            this.refreshAfterFocusVattu(0)
          } else {
            this.$toast.error(response.data.data)
          }
        } else {
          if (response.data.data) {
            this.$toast.error(response.data.data)
          } else if (response && response.data && response.data.message) {
            this.$toast.error(response.data.message)
          } else {
            this.$toast.error('Đã xảy ra lỗi')
          }
        }
      } catch (error) {
        this.loading(false)
        if (error.data && error.data.message) {
          this.$toast.error(error.data.message)
        } else if (error.response && error.response.data && error.response.data.message) {
          this.$toast.error(error.response.data.message)
        } else {
          this.$toast.error('Đã xảy ra lỗi')
        }
      }
    },
    async initDulieu() {
      //Lấy thông tin người dùng
      this.tt_nd = await this.thongtin_nguoidung()
      console.log('tt_nd', this.tt_nd)
      this.tentat_ccbs = this.tt_nd ? this.tt_nd.tentat_ccbs : ''
      console.log('tentat_ccbs', this.tentat_ccbs)

      this.khoa_nhapmoi = await this.check_khoa_tao_moi_vattu()
      this.tu_sinh_ma_vt = await this.lay_thamso_tu_sinh_ma_vt()
      this.DS_VT_CHA = await this.lay_danhsach_vattu_cha(this.radioDanhMuc)
      this.danhsach_vattu_cha = this.DS_VT_CHA
      this.danhsach_donvitinh = await this.lay_danhsach_donvitinh()
      this.danhsach_loaithietbi = await this.lay_danhsach_loaithietbi()
      this.danhsach_loaivattu = await this.lay_danhsach_loaivattu()
      this.danhsach_onu = await this.lay_danhsach_onu()
      this.danhsach_vattu_mscp = await this.lay_vattu_mscp()
      this.danhsach_chungloai = await this.lay_ds_chung_loai_vt()
      this.danhsach_hangsx = await this.lay_danhsach_hang_sx()
      await this.sp_ht_ds_danhmuc_vattu({
        db_tbi: null,
        ht_id: null,
        kieu_check: this.radioDanhMuc
      })
      await this.$refs.danhmucvattu.initDuLieu()

      this.setActiveActions(0)

      this.tag = this.$route.query.tag || '0'
      if (this.tag == '-1') {
        this.header.list[2].name = 'Xem danh mục vật tư'
      } else {
        this.header.list[2].name = 'Vật tư'
      }

      //web-quantri/nguoidung/thongtin
    }
  },
  mounted() {
    document.addEventListener('contextmenu', (event) => event.preventDefault())
    setTimeout(() => {
      this.initDulieu()
    }, 500)
  }
}
</script>
<style>
.disabledButton {
  border-color: gray !important;
  color: gray !important;
}
.image_vattu {
  width: 64px;
  height: 64px;
}
.k {
  float: left !important;
}
.readonly_input {
  background-color: #fff9eb !important;
}
</style>
