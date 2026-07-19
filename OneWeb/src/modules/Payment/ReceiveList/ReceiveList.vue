<template>
  <div class="main-wrapper">
        <div class="breadcrumb-top">
            <div class="main-title">nhận bảng kê</div>
            <ul class="breadcrumb">
                <li class="breadcrumb-item"><a href="#"><span class="nc-icon-outline ui-1_home-simple"></span> Trang chủ </a></li>
                <li class="breadcrumb-item"><a href="#">Lập hợp đồng</a></li>
                <li class="breadcrumb-item active">Lắp đặt mới</li>
            </ul>
        </div>
        <div class="list-actions-top">
            <ul class="list">
                <li>
                    <a href="javascript:;" @click="Timkiem">
                        <span class="icon one-search"></span>Tìm kiếm
                    </a>
                </li>
                <li>
                    <a href="javascript:;" @click="Nhan">
                        <span class="icon one-mail-download"></span>Nhận
                    </a>
                </li>
                
                <li>
                    <a href="#">
                        <span class="icon one-print"></span>In BN
                    </a>
                </li>
                
                
            </ul>
           
        </div>
        <div class="page-content">
            <div class="box-form">
                <div class="legend-title">Thông tin bảng kê</div>
                <div class="row">
                    <div class="col-sm-6 col-12">
                        <div class="row">
                            <div class="col-sm-8 col-12">
                                <div class="info-row">
                                    <div class="key w90">Mã BK</div>
                                    <div class="value">
                                        <div class="input-more-button">
                                            <button class="btn" @click="pickUpDate">
                                                <span class="-ap icon-more_horiz"></span>
                                            </button>
                                            <input type="text" class="form-control" v-model="MaBK" @keyup.enter="keypressMaBK">
                                        </div>
                                        <PickUpDate ref="dialog" @chapNhan="chapNhanPopUpChonNgay" />
                                    </div>
                                </div>
                            </div>
                            <div class="col-sm-4 col-12">
                                <div class="info-row">
                                    <div class="key w90">Ngày tạo</div>
                                    <div class="value">
                                        <div class="input-icon-right">
                                            <date-picker input-class="form-control" format="DD/MM/YYYY" :value-type="'format'" v-model="NgayTao"></date-picker>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="info-row">
                            <div class="key w90">Trung tâm VT</div>
                            <div class="value">
                                <div class="select-custom">
                                    <ejs-dropdownlist
                                        :allowFiltering="true"
                                        :dataSource="TrungTamVTList"
                                        :fields="fieldDropDown_DV_upper"
                                        v-model="TrungTamVT"
                                        :change="onChangeTrungTamVT"
                                        :filtering="onFilteringTTVT"
                                    ></ejs-dropdownlist>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-sm-8 col-12">
                                <div class="info-row">
                                    <div class="key w90">Tổ VT</div>
                                    <div class="value">
                                        <div class="select-custom">
                                            <ejs-dropdownlist
                                                :allowFiltering="true"
                                                :dataSource="ToVTList"
                                                :fields="fieldDropDownDV"
                                                v-model="ToVT"
                                                :change="onChangeToVT"
                                                :filtering="onFilteringToVT"
                                            ></ejs-dropdownlist>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="col-sm-4 col-12">
                                <div class="info-row">
                                    <div class="key w90">Trang thái BK</div>
                                    <div class="value">
                                        <div class="select-custom">
                                            <ejs-dropdownlist
                                                :allowFiltering="true"
                                                :dataSource="TrangThaiBKList"
                                                :fields="fieldDropDownTrangThai"
                                                v-model="TrangThaiBK"
                                                :filtering="onFilteringTTBK"
                                            ></ejs-dropdownlist>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        
                    </div>
                    <div class="col-sm-6 col-12">
                        <div class="info-row">
                            <div class="key w90">Người nộp</div>
                            <div class="value">
                                <div class="select-custom">
                                    <ejs-dropdownlist
                                        :allowFiltering="true"
                                        :dataSource="NguoiNopList"
                                        :fields="fieldDropDownNV"
                                        v-model="NguoiNop"
                                        :filtering="onFilteringNguoiNop"
                                    ></ejs-dropdownlist>
                                </div>
                            </div>
                        </div>
                        <div class="info-row">
                            <div class="key w90">Tổng cộng</div>
                            <div class="value">
                                <ejs-textbox v-model="TongCong"></ejs-textbox>
                            </div>
                        </div>
                        <div class="info-row">
                            <div class="key w90">Bằng chữ</div>
                            <div class="value">
                                <ejs-textbox v-model="BangChu" disabled readonly></ejs-textbox>
                            </div>
                        </div>
                    </div>
                </div>

                <div class="legend-title mart20">In biên nhận</div>
                <div class="row">
                    <div class="col-sm-6 col-12">
                        <div class="row">
                            <div class="col-sm-8 col-12">
                                <div class="info-row">
                                    <div class="key w90">Nhóm nhận</div>
                                    <div class="value">
                                        <div class="select-custom">
                                            <ejs-dropdownlist
                                                :allowFiltering="true"
                                                :dataSource="NhomNhanList"
                                                :fields="fieldDropDownLoaiDV"
                                                v-model="NhomNhan"
                                                :change="onChangeNhomNhan"
                                                :filtering="onFilteringNhomNhan"
                                            ></ejs-dropdownlist>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="col-sm-4 col-12">
                                <div class="info-row">
                                    <div class="key w90">Nơi nhận</div>
                                    <div class="value">
                                        <div class="select-custom">
                                            <ejs-dropdownlist
                                                :allowFiltering="true"
                                                :dataSource="NoiNhanList"
                                                :fields="fieldDropDownDV"
                                                v-model="NoiNhan"
                                                :change="onChangeNoiNhan"
                                                :filtering="onFilteringNoiNhan"
                                            ></ejs-dropdownlist>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        
                    </div>
                    <div class="col-sm-6 col-12">
                        <div class="row">
                            <div class="col-sm-5 col-12">
                                <div class="info-row">
                                    <div class="key w90">Ngày nhận</div>
                                    <div class="value">
                                        <div class="input-icon-right">
                                            <date-picker input-class="form-control" format="DD/MM/YYYY" :value-type="'format'" v-model="NgayNhan"></date-picker>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="col-sm-7 col-12">
                                <div class="info-row">
                                    <div class="key w90">Người nhận</div>
                                    <div class="select-custom">
                                        <ejs-dropdownlist
                                            :allowFiltering="true"
                                            :dataSource="NguoiNhanList"
                                            :fields="fieldDropDownNV"
                                            v-model="NguoiNhan"
                                            :filtering="onFilteringNguoiNhan"
                                        ></ejs-dropdownlist>
                                </div>
                                </div>
                            </div>
                        </div>
                        
                    </div>
                    
                </div>
                <div class="grid-stack-box mart20">
                    <div class="box-col" id="boxLeft">
                        <div class="legend-title">Danh sách bảng kê</div>
                        <div class="table-content" style="height: 180px;">
                            <DSBangKe :danhSachBK="DSBangKe" @onchangeRowDSBK="onchangeRowDSBK"/>
                        </div>
                        
                        <div class="legend-title mart20">Danh sách hợp đồng</div>
                        <div class="table-content" style="height: 180px;">
                            <DSHopDongDaGan :DSHopDongDaGan="DSHopDong" @onchangeRowDSHopDong="onchangeRowDSHopDong" />
                        </div>
                    </div>
                    <div class="box-col" id="boxRight" style="margin-left: 10px">
                        <div class="legend-title">Danh sách thuê bao đã gán</div>
                        <div class="table-content" style="height: 460px;">
                            <DSThueBao :DSThueBao="DSThueBao" />
                        </div>
                    </div>
                </div>
            </div>
            
        </div>
        <footer class="footer">
            VNPT-IT &copy; 2021
        </footer>
    </div>

</template>

<script src="./ReceiveList.js"></script>

<style scoped>
.box-col {
    width: 60%;
}
</style>