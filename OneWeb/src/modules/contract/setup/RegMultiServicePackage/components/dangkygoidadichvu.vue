<template>
  <div>
    <vue-nav>
      <ul class="list">
        <li :class="!button.nhapmoi ? 'non-ative' : ''">
          <a @click="click_button('nhapmoi')">
            <span class="icon one-file-plus"></span>Nhập mới
          </a>
        </li>
        <li :class="!button.ghilai ? 'non-ative' : ''">
          <a @click="click_button('ghilai')">
            <span class="icon one-save"></span>Ghi lại
          </a>
        </li>
        <li :class="!button.huy ? 'non-ative' : ''">
          <a @click="click_button('huy')">
            <span class="icon nc-icon-glyph ui-1_circle-remove"></span>Hủy
          </a>
        </li>
        <li :class="!button.xoa ? 'non-ative' : ''">
          <a @click="click_button('xoa')">
            <span class="icon one-trash"></span>Xóa
          </a>
        </li>
        <li :class="!button.them ? 'non-ative' : ''">
          <a @click="click_button('them')">
            <span class="icon one-mobile-plus"></span>Thêm TB
          </a>
        </li>
        <li :class="!button.xoa_tb ? 'non-ative' : ''">
          <a @click="click_button('xoa_tb')">
            <span class="icon one-phone-remove"></span>Xóa TB
          </a>
        </li>
        <li>
          <a href="#">
            <span class="icon one-file-check"></span>Hoàn thiện
          </a>
        </li>
        <li>
          <a href="#">
            <span class="icon nc-icon-glyph business_contacts"></span>Liên hệ
          </a>
        </li>
        <li>
          <a href="#">
            <span class="icon one-email"></span>Email
          </a>
        </li>
      </ul>
    </vue-nav>
    <div class="page-content">
      <vue-card>
        <b-row>
          <b-col md="8"></b-col>
          <b-col md="2">
            <div class="info-row">
              <div class="key w20 nowrap">
                <div class="check-action">
                  <input
                    type="checkbox"
                    class="check"
                  >
                  <span class="name fw6">Cộng tác viên</span>
                </div>
              </div>
              <div class="value">
                <ejs-textbox name="culy"></ejs-textbox>
              </div>
            </div>
          </b-col>
          <b-col md="2">
            <div class="info-row">
              <div class="key w20 nowrap">
                <div class="check-action">
                  <input
                    type="checkbox"
                    class="check"
                  >
                  <span class="name fw6">Người giới thiệu</span>
                </div>
              </div>
              <div class="value">
                <ejs-textbox name="culy"></ejs-textbox>
              </div>
            </div>
          </b-col>
        </b-row>
      </vue-card>
      <div class="grid-stack-box">
          <div class="box-col box-form" style="width: calc(50% - 8px);">
                <b-row>
                    <vue-element class="col-md-8" label="Mã giao dịch">
                        <div class="input-more-button">
                            <button class="btn">
                                <span class="-ap icon-more_horiz"></span>
                            </button>
                            <ejs-textbox name="culy"></ejs-textbox>
                        </div>
                    </vue-element>
                    <vue-date labelWidth="100" class="col-md-4" label="Ngày yêu cầu"></vue-date>
                    <vue-element
                        class="col-md-8"
                        label="Dịch vụ"
                        labelWidth="100"
                    >
                        <ejs-dropdownlist
                            locale="vi-VN"
                            :dataSource="options.dichvu"
                            v-model="model.dichvuvt_id"
                            :allowFiltering="true"
                            :fields="{ value: 'DICHVUVT_ID', text: 'TEN_DVVT' }"
                        />
                    </vue-element>
                    <vue-element
                        class="col-md-4"
                        label="Loại hình TB"
                        labelWidth="100"
                    >
                        <ejs-dropdownlist
                            locale="vi-VN"
                            :dataSource="options.loaihinhTB"
                            :allowFiltering="true"
                            :fields="{ value: 'LOAITB_ID', text: 'LOAIHINH_TB' }"
                        />
                    </vue-element>

                    <vue-element
                        class="col-md-12"
                        label="Kiểu yêu cầu"
                        labelWidth="100"
                    >
                        <ejs-dropdownlist
                            locale="vi-VN"
                            :dataSource="options.kieuYC"
                            :allowFiltering="true"
                            v-model="model.kieu_yc"
                            :fields="{ value: 'KIEULD_ID', text: 'TEN_KIEULD' }"
                        />
                    </vue-element>


                    <vue-element class="col-md-8" label="Số máy/Acc">
                        <div class="input-more-button">
                            <button class="btn">
                                <span class="-ap icon-more_horiz"></span>
                            </button>
                            <ejs-textbox name="culy"></ejs-textbox>
                        </div>
                    </vue-element>

                    <vue-element
                        class="col-md-4"
                        label="Hiệu lực"
                        labelWidth="100"
                    >
                        <ejs-dropdownlist
                            locale="vi-VN"
                            :dataSource="options.hieuluc"
                            :allowFiltering="true"
                            :fields="{ value: 'id', text: 'text' }"
                        />
                    </vue-element>

                    <vue-element class="col-md-12" label="Tên gói">
                        <div class="input-more-button">
                            <button class="btn">
                                <span class="-ap icon-more_horiz"></span>
                            </button>
                            <ejs-textbox name="culy"></ejs-textbox>
                        </div>
                    </vue-element>

                    <vue-element class="col-md-8" label="Số CA">
                        <ejs-textbox name="culy"></ejs-textbox>
                    </vue-element>

                    <vue-element class="col-md-4" label="MST CA">
                        <ejs-textbox name="culy"></ejs-textbox>
                    </vue-element>

                    <vue-element class="col-md-4" :labelWidth="100">
                        <ejs-checkbox
                        ref="connect2"
                        id="connect2"
                        class="e-info"
                        ></ejs-checkbox>
                        <span class="name">Chủ nhóm</span>
                    </vue-element>
                    <vue-textarea
                    labelWidth="100"
                    class="mota"
                    label="Mô tả"
                    ></vue-textarea>
                </b-row>
          </div>
          <div class="gutter gutter-horizontal" style="width: 16px;"></div>
          <div class="box-col box-form" style="width: calc(50% - 8px);">
            <vue-element labelWidth="110" class="col-md-12" label="Mã khách hàng">
                <ejs-textbox name="culy"></ejs-textbox>
            </vue-element>
            <vue-element labelWidth="110" class="col-md-12" label="Tên khách hàng">
                <ejs-textbox name="culy"></ejs-textbox>
            </vue-element>
            <vue-element labelWidth="110" class="col-md-12" label="Địa chỉ KH">
                <ejs-textbox name="culy"></ejs-textbox>
            </vue-element>
            <vue-textarea
                class="col-md-12 ghichu"
                labelWidth="110"
                label="Ghi chú"
            ></vue-textarea>
          </div>
      </div>
      <vue-card label="Dánh sách các thuê bao thuộc gói">
          <DataGrid
          ref="dagan"
          v-bind:columns="collumns"
          v-bind:dataSource="[]"
          :showColumnCheckbox="true"
          :showFilter="true"
          :allowPaging="true"
          :enablePagingServer="false"
          panelDataHeight="170"
        ></DataGrid>
      </vue-card>
    </div>
  </div>
</template>
<script>
import api from '../api/index.js'
import VueElement from '../../DeclareLandline/components/VueElement.vue'
export default {
    data() {
        return {
            collumns: [
                {
                    fieldName: "ma_tb",
                    headerText: "Số máy/Acc",
                    allowFiltering: true,
                    allowSorting: false,
                    width: 200
                },
                {
                    fieldName: "ma_tb",
                    headerText: "Tên thuê bao",
                    allowFiltering: true,
                    allowSorting: false,
                    width: 200
                },
                {
                    fieldName: "ma_tb",
                    headerText: "Loại hình",
                    allowFiltering: true,
                    allowSorting: false,
                    width: 200
                },
                {
                    fieldName: "ma_tb",
                    headerText: "Địa chỉ lắp đặt",
                    allowFiltering: true,
                    allowSorting: false,
                    width: 200
                },
                {
                    fieldName: "ma_tb",
                    headerText: "Gói cước",
                    allowFiltering: true,
                    allowSorting: false,
                    width: 200
                }
            ],
            model: {
              dichvuvt_id: null,
              kieu_yc: null
            },
            options: {
                dichvu    : [],
                loaihinhTB: [],
                kieuYC: [],
                hieuluc: [
                  {id: 0, text: 'Tức thì'},
                  {id:1, text: 'Tháng sau'},
                  {id:2, text: 'Khác'}
                ]
            },
            button : {
              nhapmoi: false,
              ghilai: false,
              xoa: false,
              huy:false,
              them: false,
              xoa_tb:false,
              capnhat: false
            }
        }
    },
    components: {
        VueElement
    },
    mounted() {
        this.init()
    },
    watch: {
      "model.dichvuvt_id"(val) {
        this.getLoaihinh()
      }
    },
    methods: {
        click_button(action) {
          if(!this.button[action]) {
            return false
          }
        },
        SetButton(kieu)
        {
            this.button.nhapmoi = false;
            this.button.ghilai = false;
            this.button.xoa = false;
            this.button.huy = false;
            this.button.them = false;
            this.button.xoa_tb = false;
            this.button.capnhat = false;


            if (kieu == -1)//Bat dau
            {
                this.button.ghilai = true;
                this.button.huy = true;
            }

            if (kieu == 0)//Bat dau
            {
                this.button.nhapmoi = true;
                this.Clear();
            }

            if (kieu == 1)//Them moi
            {
                this.button.ghilai = true;
                this.button.huy = true;
                this.Clear();
            }

            if (kieu == 2)//Huy
            {
                txtMaGD.Focus();
                tsbtnNhapMoi.Enabled = true;
                tsbtnXoa.Enabled = true;
                this.Clear();
            }

            if (kieu == 3)//Edit
            {

                this.button.nhapmoi = true;
                this.button.xoa = true;
                this.button.ghilai = true;
                this.button.huy = true;
                this.button.xoa_tb = true;
                this.button.them = true;
                this.button.capnhat = true;
                // if (dgrList.Rows.Count > 1)
                //     tsbtnXoaTB.Enabled = true;
                // else
                //     tsbtnXoaTB.Enabled = false;
            }
        },
        async init() {
          this.SetButton(-1)
          try {
            let dsDichvu = await api.getDichvu(this.axios)
            this.options.dichvu = dsDichvu.data.data ? dsDichvu.data.data : []
            this.getKieuyeucau()
          } catch(err) {
            console.log(err)
          }
        },
        async getLoaihinh() {
          let dsloaihinhTB = await api.getLoaiHinhTb(this.axios, {dichvuvt_id: this.model.dichvuvt_id})
          this.options.loaihinhTB = dsloaihinhTB.data.data ? dsloaihinhTB.data.data : []
        },
        async getKieuyeucau() {

          let kieuYC = await api.getKieuYC(this.axios)
          this.options.kieuYC = kieuYC.data.data ? kieuYC.data.data : []
        }
    }
}
</script>
<style>
    .mota textarea {
        height:100px !important;
    }
    .ghichu textarea {
        height:240px !important;
    }
    .non-ative a {
  color: #d3d3d3 !important;
}
</style>
