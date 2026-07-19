<template src="./index.html"></template>

<script>
import { Diagram, PrintAndExport } from '@syncfusion/ej2-diagrams';
import html2canvas from "html2canvas";
import $ from "jquery";
export default {
  name: "XemTruocKhiIn",
  props:{
    id: String,
    initOffset:Object,
  },
  provide:{
    diagram:[PrintAndExport],
  },
  data:function (){
    return {
      isShown: false,
      htmlDiagram:'',
      layoutPrint:1,

      horizontalOffset:0,
      verticalOffset:0,

      inKhungTen:true,

      trinhKy:{
        isKyThay:false,
        tenKyThay:"",
        chucDanh:"GIÁM ĐỐC",
        hoTen:null,
      },
      nguoiDuyet:{
        isKyThay:false,
        tenKyThay:"",
        chucDanh:"TRƯỞNG TT",
        hoTen:null,
      },
      donviGui:"TRUNG TÂM ĐIỀU HÀNH THÔNG TIN",
      nguoiVe:null,
      hangMuc:null,

      range:1,
      isHoverRange:false,
      isHoverScroll:false,
      styleRelative:'',
      scaleDiagram:1,
    }
  },
  watch:{
    range:function (value){
      this.$emit("range", value)
      // gian-do_diagramLayer_div
      this.getHtmlDiagram()
    },
    // horizontalPrint: function (value){
    //   this.verticalPrint = !value
    //   this.styleRelative = {
    //     position: 'relative !important;',
    //     width: '100%;',
    //     height: '600px;',
    //     border: '3px solid #060606;',
    //   }
    // },
    // verticalPrint: function (value){
    //   this.horizontalPrint = !value
    //   this.styleRelative = {
    //     position: 'relative !important;',
    //     width: '100%;',
    //     height: '1448px;',
    //     border: '3px solid #060606;',
    //   }
    // },
    layoutPrint: function (value){
        this.styleRelative = {
          position: 'relative !important',
          width: '100%;',
          height: value ? '1448px;' : '600px;',
          border: '3px solid #060606;'
        }
    },
    horizontalOffset: function (value){
      if (this.isHoverScroll){
        // this.$emit("horizontalOffset", value)
      }
      this.getHtmlDiagram()
    },
    verticalOffset: function (value){
      if (this.isHoverScroll){
        // this.$emit("verticalOffset", value)
      }
      this.getHtmlDiagram()

    },
    scaleDiagram: function (value){
      this.getHtmlDiagram()
    },
    isHoverRange: function (value){
      this.$emit("isHoverRange", value)
    }
  },
mounted() {
    setTimeout(function() {
       $('.gian-do_hiddenUserHandleTemplate').remove();
     //  $('#gian-do_gridline_svg').remove();

    }, 1500)
  },
  methods:{
    getHtmlDiagram(){
      // let html = document.createElement("div")
      // html.appendChild(document.getElementById("gian-do_diagramLayer_div"))
      // // html.appendChild(document.getElementById("gian-do_diagramPorts_svg"))
      // html.appendChild(document.getElementById("gian-do_htmlLayer"))
      // this.htmlDiagram = html.innerHTML
      let el = document.querySelector("#insert-element");
      if (el) el.parentNode.removeChild(el);

      let html = document.getElementById('canvas-image')
      html.insertAdjacentHTML(
        "beforeend",
        "<div id='insert-element'></div>"
      );
      document.getElementById('insert-element').insertAdjacentHTML("beforeend",document.getElementById("gian-do_diagramLayer_div").innerHTML
        )
      document.getElementById('insert-element').insertAdjacentHTML("beforeend",document.getElementById("gian-do_htmlLayer").innerHTML
        )
      // them css custom offset
      let div = document.querySelector("#insert-element")
      // if (this.count === 1){
      //   //init offset
      //   let rect  = div.getBoundingClientRect()
      //   console.log(rect.top, rect.right, rect.bottom, rect.left);
      //   this.verticalOffset = rect.top
      //   this.horizontalOffset = rect.left
      // }
      div.setAttribute("style",'')
      div.style.position = "absolute"
      div.style.top = this.verticalOffset + 'px'
      div.style.left = this.horizontalOffset + 'px'

      div.style.right = 0 + ''
      div.style.bottom = 0 + ''
      div.style.margin = 'auto'
      div.style.height = 'max-content'
      div.style.width = 'max-content'

      // right: 0;
      // bottom: 0;
      // margin: auto;
      // height: max-content;
      // width: max-content;

      // them css zoom
      if (parseInt(this.scaleDiagram.toString(), 10) === 1){
        // case: fit to page
        let frame_width = $('#canvas-image').width()
        let diagram_width = $('#gian-do_diagramLayer_svg').width()
        let ratio = frame_width / diagram_width  * 100
        div.style.zoom = ratio + '%'
      }else div.style.zoom = this.scaleDiagram + '%'
    },
    onHiddenModal() {
      this.isShown = false
      this.count = 0
    },
    async onShownModal() {
      this.isShown = true
      this.clear()
      try {
        this.loading(true)
        this.range = this.initOffset.CurrentZoom
        this.verticalOffset = 0
        this.horizontalOffset = 0
        this.getHtmlDiagram()
        this.btnNapLai()
      }catch (e){}
      finally {
        this.loading(false)
      }
    },
    btnGhiLai(){
      let thongTinTrinhKyarr = []
      thongTinTrinhKyarr.push({key:'trinhKy.isKyThay',value:this.trinhKy.isKyThay})
      thongTinTrinhKyarr.push({key:'trinhKy.tenKyThay',value:this.trinhKy.tenKyThay})
      thongTinTrinhKyarr.push({key:'trinhKy.chucDanh',value:this.trinhKy.chucDanh})
      thongTinTrinhKyarr.push({key:'trinhKy.hoTen',value:this.trinhKy.hoTen})
      thongTinTrinhKyarr.push({key:'donviGui',value:this.donviGui})
      thongTinTrinhKyarr.push({key:'hangMuc',value:this.hangMuc})
      thongTinTrinhKyarr.push({key:'nguoiDuyet.isKyThay',value:this.nguoiDuyet.isKyThay})
      thongTinTrinhKyarr.push({key:'nguoiDuyet.tenKyThay',value:this.nguoiDuyet.tenKyThay})
      thongTinTrinhKyarr.push({key:'nguoiDuyet.chucDanh',value:this.nguoiDuyet.chucDanh})
      thongTinTrinhKyarr.push({key:'nguoiDuyet.hoTen',value:this.nguoiDuyet.hoTen})
      thongTinTrinhKyarr.push({key:'nguoiVe',value:this.nguoiVe})
      let json = JSON.stringify(thongTinTrinhKyarr);
      localStorage.setItem("cauhinhky_"+this.$auth.getUserName(), json);
      this.$root.toastSuccess("Lưu thông tin trình ký thành công")
    },
    btnNapLai(){
      let getdata = JSON.parse(localStorage.getItem('cauhinhky_'+ this.$auth.getUserName()))
      if (getdata){
        for (let item of getdata){
          switch (item.key){
            case "trinhKy.isKyThay":
              this.trinhKy.isKyThay = item.value
              break
            case "trinhKy.tenKyThay":
              this.trinhKy.tenKyThay = item.value
              break
            case "trinhKy.chucDanh":
              this.trinhKy.chucDanh = item.value
              break
            case "trinhKy.hoTen":
              this.trinhKy.hoTen = item.value
              break
            case "donviGui":
              this.donviGui = item.value
              break
            case "hangMuc":
              this.hangMuc = item.value
              break
            case "nguoiDuyet.isKyThay":
              this.nguoiDuyet.isKyThay = item.value
              break
            case "nguoiDuyet.tenKyThay":
              this.nguoiDuyet.tenKyThay = item.value
              break
            case "nguoiDuyet.chucDanh":
              this.nguoiDuyet.chucDanh = item.value
              break
            case "nguoiDuyet.hoTen":
              this.nguoiDuyet.hoTen = item.value
              break
            case "nguoiVe":
              this.nguoiVe = item.value
              break
          }
        }
      }
    },
    btnInGianDo(){
      // $("#ingiando-relative").css("height", "1440px");
      // $("#canvas-image").css("height", "1440px");
      let prtHtml = document.getElementById("InGianDo");
      let preStyle = prtHtml.getElementsByTagName("style")
      if (preStyle[0]){
        prtHtml.getElementsByTagName("style")[0].remove()
      }
      let style = document.createElement("style")
      style.type = 'text/css'
      style.media = 'print'
      if (this.layoutPrint){
        style.appendChild(document.createTextNode("@page { size: portrait; }"))
      }else style.appendChild(document.createTextNode("@page { size: landscape; }"))

      prtHtml.appendChild(style)
      prtHtml = prtHtml.innerHTML
      // Get all stylesheets HTML
      let stylesHtml = " <style>\n" +
        "                    .trucoi th{\n" +
        "                      background: #ffffff;\n" +

        "                      border: 1px solid #E0E0E0;\n" +
        "                    }\n" +
        "                  </style>";
      for (const node of [
        ...document.querySelectorAll('link[rel="stylesheet"], style'),
      ]) {
        stylesHtml += node.outerHTML;
      }

      // Open the print window
      const WinPrint = window.open(
        "",
        "",
        "left=0,top=0,width=800,height=800,toolbar=0,scrollbars=0,status=0"
      );

      WinPrint.document.write(`<!DOCTYPE html>
          <html>
            <head>
              ${stylesHtml}
            </head>
            <body>
              ${prtHtml}
            </body>
          </html>`);

      WinPrint.document.close();
      WinPrint.focus();
      WinPrint.print();
      WinPrint.close();
      // $("#ingiando-relative").css("height", "900px");
      // $("#canvas-image").css("height", "1032px");
     //  let diagram = document.getElementById("InGianDo");
     //  html2canvas(diagram,{
     //      useCORS: true,
     //      allowTaint: false,
     //    }
     //  ).then(function(canvas) {
     //    canvas.style.width = "50%"
     //    canvas.style.height = "400px"
     //    // let url = document.getElementById("img").appendChild(canvas);
     //    // const prtHtml = document.getElementById("img").innerHTML;
     //    let dataUrl = canvas.toDataURL()
     //    console.log(dataUrl)
     //
     //    // Get all stylesheets HTML
     //  let stylesHtml = "";
     //  for (const node of [
     //    ...document.querySelectorAll('link[rel="stylesheet"], style'),
     //  ]) {
     //    stylesHtml += node.outerHTML;
     //  }
     //    const WinPrint = window.open(
     //      "",
     //      "",
     //      "left=0,top=0,width=800,height=900,toolbar=0,scrollbars=0,status=0"
     //    );
     //
     //    // WinPrint.document.write(`<!DOCTYPE html>
     //    // <html>
     //    //     <head>
     //    //       ${stylesHtml}
     //    //     </head>
     //    //   <body>
     //    //     <img src="' + ${dataUrl} + '">';
     //    //   </body>
     //    // </html>`);
     //
     //
     //    // var dataUrl = document.getElementById('anycanvas').toDataURL(); //attempt to save base64 string to server using this var
     //      let windowContent = '<!DOCTYPE html>';
     //    windowContent += '<html>'
     //    windowContent += '<head>  <title>Print canvas</title></head>';
     //    windowContent += '<body>'
     //    windowContent += '<img width="720" height="1080" src="' + dataUrl + '">';
     //    windowContent += '</body>';
     //    windowContent += '</html>';
     //    let printWin = window.open('', '', 'left=0,top=0,width=800,height=900,toolbar=0,scrollbars=0,status=0');
     // //   printWin.document.open();
     //    printWin.document.write(windowContent);
     //
     //    printWin.document.addEventListener('load', function() {
     //      printWin.focus();
     //      printWin.print();
     //      printWin.document.close();
     //      printWin.close();
     //    }, true)
     //
     //    //
     //    // printWin.document.close();
     //    // printWin.focus();
     //    // printWin.print();
     //    // printWin.close();
     //
     //
     //    // WinPrint.document.close();
     //    // WinPrint.focus();
     //    // WinPrint.print();
     //    // WinPrint.close();
     //  });

    },
    clear(){
      this.htmlDiagram = ''
      this.layoutPrint = 1

      this.horizontalOffset = 0
      this.verticalOffset = 0

      this.inKhungTen = true

      this.trinhKy = {
        isKyThay:false,
        tenKyThay:"",
        chucDanh:"GIÁM ĐỐC",
        hoTen:null,
      }
      this.nguoiDuyet = {
        isKyThay:false,
        tenKyThay:"",
        chucDanh:"TRƯỞNG TT",
        hoTen:null,
      }
      this.donviGui = "TRUNG TÂM ĐIỀU HÀNH THÔNG TIN"
      this.nguoiVe = null
      this.hangMuc = null

      this.range= 1
      this.isHoverRange = false
      this.isHoverScroll = false
      this.styleRelative = ''
      this.scaleDiagram = 1
    }
  },
}
</script>

<style>
.modal-xl {
  max-width: 90% !important;
}
/*.xem-truoc-khi-in div.relative {*/
/*  position: relative !important;*/
/*  width: 100%;*/
/*  height: 1060px;*/
/*  border: 3px solid #060606;*/
/*}*/

/*.xem-truoc-khi-in div.absolute {*/
/*  position: absolute !important;*/
/*  bottom: 0;*/
/*  !*top:0;*!*/
/*  right: 0;*/
/*  width: 60%;*/
/*  height: 20%;*/
/*  !*border: 3px solid #060606;*!*/
/*}*/
/*.xem-truoc-khi-in .table-result{*/
/*  background-color: #ffffff;*/
/*  border: 1px solid #000000;*/
/*  white-space: nowrap;*/
/*}*/

.xem-truoc-khi-in .table-result td, .table-result th {
  padding: 5px 7px;
  border: 2px solid #212529;
  height: 32px;
}
.absolute {
  /*bottom: 26px;*/
  /*right: 23px;*/
  position: absolute !important;
  bottom: 0px;
  right: 2px;
  max-width: 100%;
  border: 2px solid #060606
}
@media print {
  /*.absolute {*/
  /*  bottom: -2px !important;*/
  /*  right: 2px !important;*/
  /*  max-width: 100%;*/
  /*  border: 2px solid #060606;*/
  /*}*/

  /*.absolute {*/
  /*  position: absolute !important;*/
  /*  bottom: 26px;*/
  /*  right: 23px;*/
  /*  max-width: 100%;*/
  /*  border: 2px solid rgb(6, 6, 6);*/
  /*}*/

  #gian-docontent {
    position:absolute;
    /*left:50px;*/
    /*zoom: 50%;*/
  }
  /*}*/
  /*.table-result {*/
  /*  width: 100%;*/
  /*  font-size: 14px;*/
  /*  background: #fff;*/
  /*  white-space: nowrap;*/
  /*}*/


  /*element.style {*/
  /*  width: 100%;*/
  /*  height: 100%;*/
  /*  position: absolute;*/
  /*  left: 0px;*/
  /*  top: 0px;*/
  /*  overflow: auto;*/
  /*  background: transparent;*/
  /*  cursor: grab;*/
  /*}*/

  /*.tab-content, .box-form {*/
  /*  background: #FFFFFF;*/
  /*  padding: 16px 12px;*/
  /*  border: 1px solid #E0E0E0;*/
  /*  border-radius: 4px;*/
  /*  margin-bottom: 8px;*/
  /*  box-shadow: 0px 4px 4px rgb(177 177 177 / 15%);*/
  /*}*/

  /*.box-form, .grid {*/
  /*  width: 100%;*/
  /*}*/


  /*element.style {*/
  /*  width: 100%;*/
  /*  height: 100%;*/
  /*  position: absolute;*/
  /*  left: 0px;*/
  /*  top: 0px;*/
  /*  overflow: auto;*/
  /*  background: transparent;*/
  /*  cursor: grab;*/
  /*}*/
}
</style>
