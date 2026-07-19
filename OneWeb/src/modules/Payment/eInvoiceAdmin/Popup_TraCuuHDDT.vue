<template>
  <div>
    <b-modal
      id="tracuuhoadondt"
      ref="popup_TraCuuHDDT"
      modal-class="popup-box"
      header-class="popup-header"
      body-class="none-padding"
      size="xl"
      hide-footer
    >
      <template #modal-header="{ close }">
        <div class="title">
          <span class="icon nc-icon-outline ui-1_settings"></span> Tra cứu hoá đơn điện tử
        </div>
        <div
          @click="close"
          class="close -ap icon-close"
          data-dismiss="modal"
        ></div>
      </template>

      <div class="list-actions-top">
        <ul class="list">
          <li
            @click="clickButton('chapnhan')"
            :class="this.button.chapnhan ? 'active' : 'non-active'"
          >
            <a> <span class="icon one-file-plus"></span>Chấp nhận </a>
          </li>
        </ul>
      </div>

      <div class="popup-body">
        <div class="box-form">
          <div class="table-content">
            <DataGrid
              ref="grid_danhsach"
              width="100%"
              v-bind:columns="this.headers"
              v-bind:dataSource="this.grid_danhsach"
              :enable-paging-server="false"
              :allowPaging="true"
              :showFilter="true"
              @rowSelected="rowSelected"
            ></DataGrid>
          </div>
        </div>
      </div>
    </b-modal>
  </div>
</template>
<script>
import api from "./api";
import moment from "moment";
import Vue from "vue";

export default {
  name: "PopupHDDT",

  props: {
    vma_gd: {
      type: String,
      default: "",
    },
    vtt: {
      type: Boolean,
      default: false,
    },
  },

  data() {
    return {
      button: {
        chapnhan: true,
      },
      vtoken_show: "",
      status: "",
      vtype: 0,
      _pattern: "",
      _seri: "",
      _so_hd: "",
      _fkey: "",
      vpayment: "",
      vdieuchinh: 0,
      vthaythe: 0,
      file: "",
      grid_danhsach: [],
      headers: [
        {
          textAlign: "center",
          fieldName: "stt",
          headerText: "STT",
          allowFiltering: true,
          allowSorting: false,
        },
        {
          textAlign: "center",
          fieldName: "fkey",
          headerText: "Fkey",
          allowFiltering: true,
          allowSorting: false,
        },

        {
          textAlign: "center",
          fieldName: "pattern",
          headerText: "Mẫu hóa đơn",
          allowSorting: "true",
          allowFiltering: "true",
        },
        {
          textAlign: "center",
          fieldName: "serial",
          headerText: "Ký hiệu",
          allowSorting: "true",
          allowFiltering: "true",
        },
        {
          textAlign: "center",
          fieldName: "invNum",
          headerText: "Số hóa đơn",
          allowSorting: "true",
          allowFiltering: "true",
        },
        {
          textAlign: "center",
          fieldName: "name",
          headerText: "Tên hóa đơn",
          allowSorting: "true",
          allowFiltering: "true",
        },
        {
          textAlign: "center",
          fieldName: "index",
          headerText: "Tháng xuất HĐ",
          allowSorting: "true",
          allowFiltering: "true",
        },
        {
          textAlign: "center",
          fieldName: "cusname",
          headerText: "Tên khách hàng",
          allowSorting: "true",
          allowFiltering: "true",
        },
        {
          textAlign: "center",
          fieldName: "signStatus_chu",
          headerText: "Khách hàng xem HĐ",
          allowSorting: "true",
          allowFiltering: "true",
        },
        {
          textAlign: "center",
          fieldName: "publishDate",
          headerText: "Ngày phát hành",
          allowSorting: "true",
          allowFiltering: "true",
        },

        {
          textAlign: "center",
          fieldName: "amount",
          headerText: "Tổng tiền",
          type: "Number",
          format: "N",
          allowSorting: "true",
          allowFiltering: "true",
        },
        {
          textAlign: "center",
          fieldName: "status_chu",
          headerText: "Trạng thái hóa đơn",
          allowSorting: "true",
          allowFiltering: "true",
        },
        {
          textAlign: "center",
          fieldName: "payment_chu",
          headerText: "Trạng thái thanh toán",
          allowSorting: "true",
          allowFiltering: "true",
        },

        {
          textAlign: "center",
          fieldName: "payment_chu",
          headerText: "Trạng thái thanh toán",
          allowSorting: "true",
          allowFiltering: "true",
        },

        {
          fieldName: "",
          headerText: "",
          allowFiltering: true,
          allowSorting: false,
          textAlign: "Center",
          width: 90,
          template: function () {
            return {
              template: Vue.component("columnTemplate", {
                template: `<div><a href="javascript: void(0)" @click="onViewInv" title="Xem chi tiết hóa đơn điện tử"><span></span>Xem chi tiết</a></div>`,
                data() {
                  return { data: {} };
                },
                computed: {
                  parent() {
                    return this.$parent.$parent.$parent;
                  },
                },
                methods: {
                  async onViewInv() {
                    let rs = await this.getInvViewNoPay(this.data.invToken);
                    if (rs !== null) {
                      var newTab = window.open("", "_blank");
                      newTab.document.body.innerHTML = rs;
                    }
                  },

                  async getInvViewNoPay(invToken) {
                    try {
                      this.loading(true);
                      let apiBody = {
                        invToken: invToken,
                        phanvung_id: this.$auth.getPhanVungID(),
                      };

                      let response = await api.getInvViewNoPay(
                        this.axios,
                        apiBody
                      );
                      if (response.data.error_code === "BSS-00000000") {
                        return response.data.data.response;
                      } else return null;
                    } catch (error) {
                      console.log(error);
                      if (error.data.message)
                        this.$toast.error(error.data.message);
                      else
                        this.$toast.error(
                          "Không có dữ liệu, vui lòng thử lại!"
                        );
                      return null;
                    } finally {
                      this.loading(false);
                    }
                  },
                },
              }),
            };
          },
        },

        {
          fieldName: "",
          headerText: "*.inv",
          allowFiltering: true,
          allowSorting: false,
          textAlign: "Center",
          width: 90,
          template: function () {
            return {
              template: Vue.component("xoaLyDoHuyTemplate", {
                template: `<a class="btn btn-huylydo lh14" @click="onClickDownloadINV"
                            style="background-color: #3684e0 !important; padding: 5px 15px; color: red">
                <span class="act f20  one-download"></span></a>`,
                data() {
                  return { data: {} };
                },
                computed: {
                  parent() {
                    return this.$parent.$parent.$parent;
                  },
                },
                methods: {
                  async onClickDownloadINV() {
                    alert(1);
                    const res = await api.downloadInvFkeyNoPay(this.axios, {
                      fkey: this.data.fkey,
                    });
                    let rs = res.data.data ? res.data.data : "";
                    if (rs !== null) {
                      let content = rs;
                      var FileSaver = require("file-saver");
                      var file = new File(
                        [content],
                        this.data.invNum + ".inv",
                        {
                          type: "application/inv",
                        }
                      );
                      FileSaver.saveAs(file);
                    }
                  },
                },
              }),
            };
          },
        },

        {
          fieldName: "",
          headerText: "*.pdf",
          allowFiltering: true,
          allowSorting: false,
          textAlign: "Center",
          width: 90,
          template: function () {
            return {
              template: Vue.component("xoaLyDoHuyTemplate", {
                template: `<a class="btn btn-huylydo lh14" @click="onClickDownloadPDF"
                            style="background-color: #3684e0 !important; padding: 5px 15px; color: red">
                <span class="act f20  one-download"></span></a>`,
                data() {
                  return { data: {} };
                },
                computed: {
                  parent() {
                    return this.$parent.$parent.$parent;
                  },
                },
                methods: {
                  async onClickDownloadPDF(fkey) {
                    alert(2);
                    const res = await api.downloadInvPDFFkeyNoPay(this.axios, {
                      fkey: this.data.fkey,
                    });
                    let rs = res.data.data ? res.data.data : "";
                    if (rs !== null) {
                      let content = Buffer.from(rs, "base64");
                      var FileSaver = require("file-saver");
                      var file = new File([content], this.data.invNum, {
                        type: "application/pdf",
                      });
                      FileSaver.saveAs(file);
                    }
                  },
                },
              }),
            };
          },
        },
      ],
      newContent() {
        return {
          index: "",
          invToken: "",
          fkey: "",
          name: "",
          publishDate: "",
          signStatus: "",
          signStatus_chu: "",
          total: "",
          amount: "",
          pattern: "",
          serial: "",
          invNum: "",
          status: "",
          status_chu: "",
          cusname: "",
          payment: "",
          payment_chu: "",
          detail: "Xem chi tiết",
        };
      },
    };
  },
  created() {},

  watch: {
    vma_gd(newval) {
      this.TraCuuTheoMaGD();
    },
  },

  methods: {
    async TraCuuTheoMaGD() {
      this.grid_danhsach = [];
      let fromDate = "";
      let toDate = "";
      let kq = await this.listInvByCus_VTT(this.vma_gd, fromDate, toDate);

      if (!kq) {
        this.$toast.error(kq);
        return;
      } else {
        let objKetQua = this.ObjectToXML(kq);
        let obj = this.newContent();

        if (objKetQua.getElementsByTagName("Item").length > 0) {
          let stt = 0;
          for (
            var i = 0;
            i < objKetQua.getElementsByTagName("Item").length;
            i++
          ) {
            stt = Number(stt) + 1;
            obj.index = objKetQua
              .getElementsByTagName("Item")
              [i].getElementsByTagName("index")[0]
              ? objKetQua
                  .getElementsByTagName("Item")
                  [i].getElementsByTagName("index")[0].innerHTML
              : "";
            obj.invToken = objKetQua
              .getElementsByTagName("Item")
              [i].getElementsByTagName("invToken")[0]
              ? objKetQua
                  .getElementsByTagName("Item")
                  [i].getElementsByTagName("invToken")[0].innerHTML
              : "";
            obj.fkey = objKetQua
              .getElementsByTagName("Item")
              [i].getElementsByTagName("fkey")[0]
              ? objKetQua
                  .getElementsByTagName("Item")
                  [i].getElementsByTagName("fkey")[0].innerHTML
              : "";
            obj.name = objKetQua
              .getElementsByTagName("Item")
              [i].getElementsByTagName("name")[0]
              ? objKetQua
                  .getElementsByTagName("Item")
                  [i].getElementsByTagName("name")[0].innerHTML
              : "";
            obj.publishDate = objKetQua
              .getElementsByTagName("Item")
              [i].getElementsByTagName("publishDate")[0]
              ? objKetQua
                  .getElementsByTagName("Item")
                  [i].getElementsByTagName("publishDate")[0].innerHTML
              : "";
            obj.signStatus = objKetQua
              .getElementsByTagName("Item")
              [i].getElementsByTagName("signStatus")[0]
              ? objKetQua
                  .getElementsByTagName("Item")
                  [i].getElementsByTagName("signStatus")[0].innerHTML
              : "";
            (obj.signStatus_chu = obj.signStatus == 0 ? "Chưa xem" : "Đã xem"),
              (obj.total = objKetQua
                .getElementsByTagName("Item")
                [i].getElementsByTagName("total")[0]
                ? objKetQua
                    .getElementsByTagName("Item")
                    [i].getElementsByTagName("total")[0].innerHTML
                : "");
            obj.amount = objKetQua
              .getElementsByTagName("Item")
              [i].getElementsByTagName("amount")[0]
              ? objKetQua
                  .getElementsByTagName("Item")
                  [i].getElementsByTagName("amount")[0].innerHTML
              : "";
            obj.pattern = objKetQua
              .getElementsByTagName("Item")
              [i].getElementsByTagName("pattern")[0]
              ? objKetQua
                  .getElementsByTagName("Item")
                  [i].getElementsByTagName("pattern")[0].innerHTML
              : "";
            obj.serial = objKetQua
              .getElementsByTagName("Item")
              [i].getElementsByTagName("serial")[0]
              ? objKetQua
                  .getElementsByTagName("Item")
                  [i].getElementsByTagName("serial")[0].innerHTML
              : "";
            obj.invNum = objKetQua
              .getElementsByTagName("Item")
              [i].getElementsByTagName("invNum")[0]
              ? objKetQua
                  .getElementsByTagName("Item")
                  [i].getElementsByTagName("invNum")[0].innerHTML
              : "";
            obj.status = objKetQua
              .getElementsByTagName("Item")
              [i].getElementsByTagName("status")[0]
              ? objKetQua
                  .getElementsByTagName("Item")
                  [i].getElementsByTagName("status")[0].innerHTML
              : "";
            (obj.status_chu =
              obj.status == 1
                ? "Hóa đơn đã phát hành"
                : obj.status == 3
                ? "Hóa đơn đã thay thế"
                : obj.status == 4
                ? "Hóa đơn đã sửa đổi"
                : "Hóa đơn đã hủy"),
              (obj.cusname = objKetQua
                .getElementsByTagName("Item")
                [i].getElementsByTagName("cusname")[0]
                ? objKetQua
                    .getElementsByTagName("Item")
                    [i].getElementsByTagName("cusname")[0].innerHTML
                : "");
            obj.payment = objKetQua
              .getElementsByTagName("Item")
              [i].getElementsByTagName("payment")[0]
              ? objKetQua
                  .getElementsByTagName("Item")
                  [i].getElementsByTagName("payment")[0].innerHTML
              : "";
            (obj.payment_chu =
              obj.payment == 1 ? "Đã thanh toán" : "Chưa thanh toán"),
              this.grid_danhsach.push({
                stt: stt,
                index: obj.index,
                invToken: obj.invToken,
                fkey: obj.fkey,
                name: obj.name,
                publishDate: obj.publishDate,
                signStatus: obj.signStatus,
                signStatus_chu: obj.signStatus_chu,
                total: obj.total,
                amount: obj.amount,
                pattern: obj.pattern,
                serial: obj.serial,
                invNum: obj.invNum,
                status: obj.status,
                status_chu: obj.status_chu,
                cusname: obj.cusname,
                payment: obj.payment,
                payment_chu: obj.payment_chu,
                detail: obj.detail,
              });
          }
        } else {
          this.$toast.error("Không tìm thấy thông tin hóa đơn !");
          return;
        }
      }
    },

    async listInvByCus_VTT(magd, fromDate, toDate) {
      const res = await api.listInvByCus_VTT(this.axios, {
        cusCode: "BNHCT00225681",
        fromDate: fromDate,
        toDate: toDate,
      });
      let kq = res.data.data;
      return kq;
    },

    ObjectToXML(filexml) {
      // var parser = new DOMParser();
      // var xmlDoc = parser.parseFromString(filexml,"text/xml");
      // return xmlDoc.getElementsByTagName(type)
      let xmlDoc;
      if (window.DOMParser) {
        const parser = new DOMParser();
        xmlDoc = parser.parseFromString(filexml.trim(), "text/xml");
      } else {
        xmlDoc = new ActiveXObject("Microsoft.XMLDOM");
        xmlDoc.async = false;
        xmlDoc.loadXML(filexml.trim());
      }
      return xmlDoc;
    },

    rowSelected(subject) {
      this.status = subject.data.status;
      //this.vtype = subject.data.TYPE
      this._pattern = subject.data.pattern;
      this._seri = subject.data.serial;
      this._so_hd = subject.data.invNum;
      this._fkey = subject.data.fkey;
      this.vpayment = subject.data.payment;
      if (subject.data.invToken !== null) {
        this.vtoken_show = subject.data.invToken;
      }
      if (subject.data.status == 4) {
        this.vdieuchinh = 1;
      }
      if (subject.data.status == 3) {
        this.vthaythe = 1;
      }
    },

    ChapNhan() {
      let item = {
        vchapnhan: true,
        vdieuchinh: this.vdieuchinh,
        vthaythe: this.vthaythe,
        vpayment: this.vpayment,
        _fkey: this._fkey,
        _pattern: this._pattern,
        _seri: this._seri,
        _so_hd: this._so_hd,
      };

      this.$emit("chapnhan", item);
      this.$bvModal.hide("tracuuhoadondt");
    },

    showModal() {
      this.$bvModal.show("tracuuhoadondt");
    },
    hideModal() {
      this.$bvModal.hide("tracuuhoadondt");
    },

    clickButton(key) {
      if (key == "chapnhan") {
        this.ChapNhan();
      }
    },
  },
};
</script>
<style>
/* @import "https://cdn.syncfusion.com/ej2/material.css"; */
.none-padding {
  padding: 0 !important;
}
.modal-dialog {
  border-radius: 0px !important;
  width: 100% !important;
  max-width: 1220px !important;
}

li.non-active a {
  color: #d3d3d3 !important;
}

/* .e-row:active, e-row:focus{background: #8d8b8b !important} */

.e-grid td.e-active {
  background-color: #8d8b8b !important;
}
</style>