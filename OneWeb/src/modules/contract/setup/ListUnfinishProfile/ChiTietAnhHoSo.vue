<template>
    <div class="box-form">
        <div class="legend-title">
            <div class="pull-left">Chi tiết ảnh hồ sơ</div>
            <div class="pull-right">
                <div class="check-action normal">
                    <input id="v2_chb_thong_bao"
                        type="checkbox" @change="chkTB_CheckedChanged"
                        class="check">
                    <span class="name">Thông báo khi có hồ sơ mới</span>
                </div>
            </div>
            <div class="clearfix"></div>
        </div>
        <div class="row">
            <div class="col-sm-9 col-12" id="boxImage">
                <div class="info-row" id="boxImageControl">
                    <div class="key nowrap actions-icon-btn padt0">
                        <button class="btn btn-main" @click="zoomin">
                            <span class="nc-icon-outline ui-1_zoom-in"></span>
                        </button>
                        <button class="btn btn-main" @click="zoomout">
                            <span class="nc-icon-outline ui-1_zoom-out"></span>
                        </button>
                        <button class="btn btn-main" @click="rotateLeft">
                            <span class="one-reload-back"></span>
                        </button>
                        <button class="btn btn-main" @click="rotateRight">
                            <span class="one-reload-next"></span>
                        </button>
                        <button class="btn btn-main non-ative" @click="changeShowZoom">
                            <span :class="showZoom ? 'fa fa-hand-paper-o' : 'one-design-per'"></span>
                        </button>
                        <button class="btn btn-main">
                            <span class="nc-icon-outline files_folder-18" onclick="document.getElementById('selectedFile').click();"></span>
                        </button>
                        <input type="file" id="selectedFile" style="display: none;" accept="image/png, image/jpeg, image/jpg" 
                                    @change="chooseFile" />
                        <button class="btn btn-main non-ative" @click="changeViewXemTruoc">
                            <span class="nc-icon-outline design_image"></span>
                        </button>
                        <button class="btn btn-main" @click="saveImage">
                            <span class="nc-icon-outline media-1_camera-20"></span>
                        </button>
                    </div>
                    <div class="key w40" style="padding-top: 8px !important">Loại</div>
                    <div class="value" style="padding-top: 4px !important">
                        <div class="select-custom">
                            <Select2 v-model="model.v2_loai_file_hd" :options="v2_LstLoaiFileHD" />
                            <!-- <ejs-dropdownlist-tt id="v2_loai_file_hd"
                                locale="vi-VN"
                                v-model="model.v2_loai_file_hd"
                                :dataSource="v2_LstLoaiFileHD"
                                :allowFiltering="true" :filtering="onFilterV2LoaiFileHD"
                                :fields="{ value: 'ID', text: 'NAME' }"
                            /> -->
                        </div>
                    </div>
                    <div class="key nowrap actions-icon-btn padl5 w30 padt0">
                        <button class="btn btn-main non-ative" disabled>
                            <span class="nc-icon-outline media-1_sd"></span>
                        </button>
                    </div>
                    <div class="value w90" style="padding-top: 4px !important">
                        <div class="select-custom">
                            <Select2 v-model="model.v2_phan_tram" :options="v2_LstPhanTram" @change="changePhanTram" />
                            <!-- <ejs-dropdownlist-tt id="v2_phan_tram"
                                locale="vi-VN" @change="changePhanTram"
                                v-model="model.v2_phan_tram"
                                :dataSource="v2_LstPhanTram"
                                :allowFiltering="false"
                                :fields="{ value: 'ID', text: 'NAME' }"
                            /> -->
                        </div>
                    </div>
                </div>
                <div id="container">
                    <div class="image-canvas" style="max-width: 100%; max-height: 100%" id="v2_img_canvas" v-show="!showZoom">
                        <img :src="v2_img" id="v2_img" style="max-width: 100%; max-height: 100%">
                        <img id="noImage" src="@/assets/uploads/no-image.png" style="display: none">
                    </div>
                    <div class="zoombox" style="cursor:pointer" v-show="showZoom">
                        <img :src="v2_img" id="imgZoom">
                        <div class="selector"></div>
                    </div>
                </div>
            </div>
            <div class="col-sm-3 col-12">
                <div class="box-form" :style="!viewXemTruoc ? 'display: none' : ''">
                    <div class="title-bg-main">
                        <span class="title">Xem trước</span>
                    </div>
                    <div class="image-canvas" id="v2_img_xt_div" style="height: 250px">
                        <img :src="v2_img" id="v2_img_xt">
                    </div>
                    <div>
                        <div class="tc-pagination nowrap ovauto marb10" style="text-align: center; padding-bottom: 10px">
                            <a class="btn fa fa-angle-left" @click="prevImg"></a>
                            <div class="inline">
                                <div class="fw6 input-number" style="padding-top: 10px;">
                                    {{v2_xem_truoc}}
                                </div>
                            </div>
                            <a class="btn fa fa-angle-right" @click="nextImg"></a>
                        </div>
                    </div>
                </div>
                <div class="box-form">
                    <div class="title-bg-main">
                        <div class="pull-left">
                            <span class="title mart5">Danh sách ảnh</span>
                        </div>
                        <div class="pull-right">
                            <div class="select-custom">
                                <ejs-dropdownlist-tt id="v2_loai_view"
                                    locale="vi-VN" @change="changeLoaiView"
                                    v-model="model.v2_loai_view"
                                    :dataSource="v2_LstLoaiView"
                                    :allowFiltering="false"
                                    :fields="{ value: 'ID', text: 'NAME' }"
                                />
                            </div>
                        </div>
                        <div class="clearfix"></div>
                    </div>                  
                    <div id="lstImg"></div>
                </div>
            </div>
        </div>
    </div>
</template>
<script>
import api from "./api/index.js";
import moment from "moment";
import { Query } from '@syncfusion/ej2-data'
export default {
    name:'ChiTietAnhHoSo',
    components:{
        
    },
    data() {
        return {
            model: {
                v2_loai_file_hd: "",
                v2_loai_view: "3",
                v2_phan_tram: "4",
            },
            v2_LstLoaiFileHD: [],    
            v2_LstLoaiView: [
                {"ID":"3", "NAME": "Lớn"},
                {"ID":"1", "NAME": "Nhỏ"},
                {"ID":"2", "NAME": "TB"}
            ],  
            v2_LstPhanTram: [
                {"id":"1", "text": "25%"},
                {"id":"2", "text": "50%"},
                {"id":"3", "text": "75%"},
                {"id":"4", "text": "100%"},
                {"id":"5", "text": "125%"},
                {"id":"6", "text": "150%"},
                {"id":"7", "text": "175%"},
                {"id":"8", "text": "200%"},
                {"id":"9", "text": "225%"},
                {"id":"10", "text": "250%"},
                {"id":"11", "text": "275%"},
                {"id":"12", "text": "300%"},
                {"id":"13", "text": "325%"},
                {"id":"14", "text": "350%"},
                {"id":"15", "text": "375%"},
                {"id":"16", "text": "400%"},
            ],   
            v2_lstImg: [],
            v2_lstTitle: [],
            v2_lstType: [],
            v2_img: "", 
            chonHoSo: false,
            viewXemTruoc: true,  
            rotate: 0,
            realWidthLoad: 0,
            realHeightLoad: 0,
            v2_xem_truoc: "1/1",
            fixHeightXemTruoc: "0px",
            fixWidthXemTruoc: "0px",
            showZoom: false,
        }
    },
    computed: {

    },
    methods: {
        changeShowZoom() {       
            this.showZoom = !this.showZoom
            if (this.showZoom) {
                $("#imgZoom").css({"left":0, "top":0, "width": this.realWidthLoad + "px", "height": this.realHeightLoad + "px"});
                document.getElementById("imgZoom").className = document.getElementById("v2_img").className
            }
        },
        onFilterV2LoaiFileHD: function(e){
            var query = new Query();
            query = (e.text !== '') ? query.where('NAME', 'contains', e.text, true) : query;
            e.updateData(this.v2_LstLoaiFileHD, query);
        },
        async capnhat_thamso_macdinh(vgiatri) {
            let result = "";
            try {
                this.loading(true);
                let params = {
                    vma_ts: "NOTIFY_MESSAGE_HOSO_DIENTU",
                    vnguoidung_id: this.$root.token.getNguoiDungID(),
                    vgiatri: vgiatri
                }
                let response = await api.ur_012_capnhat_thamso_macdinh(this.axios, params);
                if (response && response.data && response.data.error_code === 'BSS-00000000') {
                           
                } else {
                    result = response.data.message;
                }
            } catch(e) {
                console.log(e);           
            } finally {
                this.loading(false);
            }
            return result;
        },
        async chkTB_CheckedChanged() {
            var checkBox = document.getElementById("v2_chb_thong_bao");
            let result = await this.capnhat_thamso_macdinh(checkBox.checked ? "1" : "0");
            if (result != "") {
                this.$toast.error(result);
            } else {
                if (checkBox.checked) {
                    var msg = "[" + moment(new Date()).format("DD/MM/yyyy HH:mm:ss") + "] Bạn vừa kích hoạt chức năng nhận thông báo khi có hồ sơ gửi tới";
                    msg += "\r\n*Không áp dụng với các hồ sơ tồn cũ.";
                    this.$toast.success(msg);
                }        
            }
        },
        chooseFile(event) {
            document.getElementById('v2_img').src = window.URL.createObjectURL(event.target.files[0]); 
            document.getElementById('v2_img_xt').src = window.URL.createObjectURL(event.target.files[0]);
            let html = "";
            html += '<div class="text-desc marb10"></div>'
            html += '<div class="image-canvas marb10"><img id="imgChoose" style="width: 100px; height: 150px"></div>';
            $('#lstImg').html(html);
            document.getElementById('imgChoose').src = window.URL.createObjectURL(event.target.files[0]);
            this.v2_xem_truoc = "1/1";
            this.resize(document.getElementById("v2_img").src)
            this.rotate = 0;       
            this.model.v2_phan_tram = "4";
            document.getElementById("v2_img").className = "";
        },
        checkMaxSize(width, height) {
            var maxWidth = document.getElementById("container").clientWidth; 
            var maxHeight = document.getElementById("container").clientHeight; 
            if (width > maxWidth || height > maxHeight) {
                return false;
            }
            return true;
        },
        changePhanTram() {
            var GFG = document.getElementById("v2_img");
            if (this.checkMaxSize(this.realWidthLoad * this.model.v2_phan_tram / 4, this.realHeightLoad * this.model.v2_phan_tram / 4)) {
                GFG.style.width = (this.realWidthLoad * this.model.v2_phan_tram / 4) + "px";
                GFG.style.height = (this.realHeightLoad * this.model.v2_phan_tram / 4) + "px";
            } 
        },
        changeLoaiView() {
            let numImg = document.querySelector('#lstImg').childElementCount / 2;
            for (let inx=0; inx<numImg; inx++) {
                var GFG = document.querySelector('#lstImg').childNodes[inx*2+1].childNodes[0];
                GFG.style.width = 100 * Number(this.model.v2_loai_view) / 3 + "px";
                GFG.style.height = 150 * Number(this.model.v2_loai_view) / 3 + "px";
            }         
        },
        zoomin() {
            var GFG = document.getElementById("v2_img");
            if (this.checkMaxSize(GFG.clientWidth * 1.1, GFG.clientHeight * 1.1)) {
                GFG.style.width = (GFG.clientWidth * 1.1) + "px";
                GFG.style.height = (GFG.clientHeight * 1.1) + "px";
            } 
        },
        zoomout() {
            var GFG = document.getElementById("v2_img");
            if (GFG.clientWidth * 0.9 > 30 && GFG.clientHeight * 0.9 > 30) {
                GFG.style.width = (GFG.clientWidth * 0.9) + "px";
                GFG.style.height = (GFG.clientHeight * 0.9) + "px";
            }
        },
        rotateLeft() {
            var img = document.getElementById('v2_img');
            this.rotate = (this.rotate + 270) % 360;
            img.className = "rotate"+this.rotate;
            this.resize(document.getElementById("v2_img").src)
        },
        setV2Img(src) {
            this.v2_img = src;
            this.resize(src);
            this.rotate = 0;       
            this.model.v2_phan_tram = "4";
            document.getElementById("v2_img").className = "";
            $("#selectedFile").val(null);
            this.showZoom = false

            if (document.getElementById("v2_img_xt").style.width > document.getElementById("v2_img_xt").style.height) {
                document.getElementById("v2_img_xt").style.width = this.fixWidthXemTruoc
            } else {
                document.getElementById("v2_img_xt").style.height = this.fixHeightXemTruoc
            }
        },
        changeViewXemTruoc() {
            if (!this.viewXemTruoc) {
                if (document.getElementById("v2_img_xt").style.width > document.getElementById("v2_img_xt").style.height) {
                    document.getElementById("v2_img_xt").style.width = this.fixWidthXemTruoc
                } else {
                    document.getElementById("v2_img_xt").style.height = this.fixHeightXemTruoc
                }   
            }
            this.viewXemTruoc = !this.viewXemTruoc          
        },
        rotateRight() {
            var img = document.getElementById('v2_img');
            this.rotate = (this.rotate + 90) % 360;
            img.className = "rotate"+this.rotate;
            this.resize(document.getElementById("v2_img").src)
        },
        nextImg() {
            if (this.v2_xem_truoc == "1/1") return;

            let curr = Number(this.v2_xem_truoc.substring(0, this.v2_xem_truoc.indexOf("/")));
            let total = Number(this.v2_xem_truoc.substring(this.v2_xem_truoc.indexOf("/")+1, this.v2_xem_truoc.length));
            if (curr == total) return;
            
            this.setV2Img(document.querySelector('#lstImg').childNodes[curr*2+1].childNodes[0].src);
            this.v2_xem_truoc = (curr + 1) + "/" + total; 
        },
        prevImg() {
            if (this.v2_xem_truoc == "1/1") return;

            let curr = Number(this.v2_xem_truoc.substring(0, this.v2_xem_truoc.indexOf("/")));
            let total = Number(this.v2_xem_truoc.substring(this.v2_xem_truoc.indexOf("/")+1, this.v2_xem_truoc.length));
            if (curr == 1) return;

            this.setV2Img(document.querySelector('#lstImg').childNodes[(curr-2)*2+1].childNodes[0].src);
            this.v2_xem_truoc = (curr - 1) + "/" + total;
        },
        resize(url) {
            var img = new Image();
            let vm = this;
            img.onload = function() {
                var maxWidth = document.getElementById("container").clientWidth; 
                var maxHeight = document.getElementById("container").clientHeight; 
                var currWidth = this.width;
                var currHeight = this.height;

                while (currWidth > maxWidth || currHeight > maxHeight) {
                    currWidth = currWidth/2;
                    currHeight = currHeight/2;
                }

                var GFG = document.getElementById("v2_img");
                GFG.style.width = Number(currWidth) + "px";
                GFG.style.height = Number(currHeight) + "px";
                vm.realWidthLoad = currWidth;
                vm.realHeightLoad = currHeight;
            };
            img.src = url;
        },
        async getData(url) {
            let result
            try {
                this.loading(true)
                let params = {
                    url: url,
                    fileType: "base64"
                }
                let response = await api.get_data_by_url(this.axios, params)      
                if (response && response.data) {
                    result = response.data
                }
            } catch(e) {
                console.log(e);          
            } finally {
                this.loading(false)
            }
            return result
        },
        async saveImage() {
            var element = document.createElement('a');
            element.href = "data:image/jpeg;base64," + await this.getData(encodeURIComponent(this.v2_img))
            element.download = this.v2_img.split("=").pop()
            element.style.display = 'none';
            document.body.appendChild(element);
            element.click();
            document.body.removeChild(element);    
        },
    },
    mounted() {

    },
}

$(document).ready(function (){
	var scroll_zoom = new ScrollZoom($('#container'),4,0.5)
})

function ScrollZoom(container,max_scale,factor){
	var target = container.children().first()
	var size = {w:target.width(),h:target.height()}
	var pos = {x:0,y:0}
	var zoom_target = {x:0,y:0}
	var zoom_point = {x:0,y:0}
	var scale = 1
	target.css('transform-origin','0 0')
	target.on("mousewheel DOMMouseScroll",scrolled)

	function scrolled(e){
		var offset = container.offset()
		zoom_point.x = e.pageX - offset.left
		zoom_point.y = e.pageY - offset.top

		e.preventDefault();
		var delta = e.delta || e.originalEvent.wheelDelta;
		if (delta === undefined) {
	      //we are on firefox
	      delta = e.originalEvent.detail;
	    }
	    delta = Math.max(-1,Math.min(1,delta)) // cap the delta to [-1,1] for cross browser consistency

	    // determine the point on where the slide is zoomed in
	    zoom_target.x = (zoom_point.x - pos.x)/scale
	    zoom_target.y = (zoom_point.y - pos.y)/scale

	    // apply zoom
	    scale += delta*factor * scale
	    scale = Math.max(1,Math.min(max_scale,scale))

	    // calculate x and y based on zoom
	    pos.x = -zoom_target.x * scale + zoom_point.x
	    pos.y = -zoom_target.y * scale + zoom_point.y


	    // Make sure the slide stays in its container area when zooming out
	    if(pos.x>0)
	        pos.x = 0
	    if(pos.x+size.w*scale<size.w)
	    	pos.x = -size.w*(scale-1)
	    if(pos.y>0)
	        pos.y = 0
	     if(pos.y+size.h*scale<size.h)
	    	pos.y = -size.h*(scale-1)

	    update()
	}

	function update(){
		target.css('transform','translate('+(pos.x)+'px,'+(pos.y)+'px) scale('+scale+','+scale+')')
	}
}

$(window).on("load",function() {
  //VARS===================================================
  var zoom = {
    zoomboxLeft:null, zoomboxTop:null, //zoombox
    cursorStartX:null, cursorStartY:null, //cursor
    imgStartLeft:null, imgStartTop:null, //image
    minDragLeft:null,maxDragLeft:null, minDragTop:null,maxDragTop:null
  };
  
  //ZOOM&DRAG-EVENTS=======================================
  //MOUSEDOWN----------------------------------------------
  $(".zoombox img").mousedown(function(e) {
    e.preventDefault();
    $(".zoombox img").addClass("moving");
    var selector = $(this).next();
    var zoombox = $(this).parent();
    $(zoombox).addClass("active");
    
    //store zoombox left&top
    zoom.zoomboxLeft = $(zoombox).offset().left + parseInt($(zoombox).css("border-left-width").replace(/\D+/,""));
    zoom.zoomboxTop = $(zoombox).offset().top + parseInt($(zoombox).css("border-top-width").replace(/\D+/,""));
    
    //store starting positions of cursor (relative to zoombox)
    zoom.cursorStartX = e.pageX - zoom.zoomboxLeft;
    zoom.cursorStartY = e.pageY - zoom.zoomboxTop;
    
    if ($(".zoombox img").hasClass("drag")) {
      //store starting positions of image (relative to zoombox)
      zoom.imgStartLeft = $(this).position().left;
      zoom.imgStartTop = $(this).position().top;
      
      //set drag boundaries (relative to zoombox)
      zoom.minDragLeft = $(zoombox).width() - $(this).width();
      zoom.maxDragLeft = 0;
      zoom.minDragTop = $(zoombox).height() - $(this).height();
      zoom.maxDragTop = 0;
    } else {
      //set drag boundaries (relative to zoombox)
      zoom.minDragLeft = 0;
      zoom.maxDragLeft = $(zoombox).width();
      zoom.minDragTop = 0;
      zoom.maxDragTop = $(zoombox).height();
      
      //activate zoom-selector
      $(selector).css({"display":"block", "width":0, "height":0, "left":zoom.cursorStartX, "top":zoom.cursorStartY});
    }
  });
  
  //MOUSEMOVE----------------------------------------------
  $(document).mousemove(function(e) {
    if ($(".zoombox img").hasClass("moving")) {
      if ($(".zoombox img").hasClass("drag")) {
        var img = $(".zoombox.active img")[0];
        
        //update image position (relative to zoombox)
        $(img).css({
          "left": zoom.imgStartLeft + (e.pageX-zoom.zoomboxLeft)-zoom.cursorStartX,
          "top": zoom.imgStartTop + (e.pageY-zoom.zoomboxTop)-zoom.cursorStartY
        });
        //prevent dragging in prohibited areas (relative to zoombox)
        if ($(img).position().left <= zoom.minDragLeft) {$(img).css("left",zoom.minDragLeft);} else 
        if ($(img).position().left >= zoom.maxDragLeft) {$(img).css("left",zoom.maxDragLeft);}
        if ($(img).position().top <= zoom.minDragTop) {$(img).css("top",zoom.minDragTop);} else 
        if ($(img).position().top >= zoom.maxDragTop) {$(img).css("top",zoom.maxDragTop);}
      } else {
        //calculate selector width and height (relative to zoombox)
        var width = (e.pageX-zoom.zoomboxLeft)-zoom.cursorStartX;
        var height = (e.pageY-zoom.zoomboxTop)-zoom.cursorStartY;
        
        //prevent dragging in prohibited areas (relative to zoombox)
        if (e.pageX-zoom.zoomboxLeft <= zoom.minDragLeft) {width = zoom.minDragLeft - zoom.cursorStartX;} else 
        if (e.pageX-zoom.zoomboxLeft >= zoom.maxDragLeft) {width = zoom.maxDragLeft - zoom.cursorStartX;}
        if (e.pageY-zoom.zoomboxTop <= zoom.minDragTop) {height = zoom.minDragTop - zoom.cursorStartY;} else 
        if (e.pageY-zoom.zoomboxTop >= zoom.maxDragTop) {height = zoom.maxDragTop - zoom.cursorStartY;}
        
        //update zoom-selector
        var selector = $(".zoombox.active .selector")[0];
        $(selector).css({"width":Math.abs(width), "height":Math.abs(height)});
        if (width<0) {$(selector).css("left",zoom.cursorStartX-Math.abs(width));}
        if (height<0) {$(selector).css("top",zoom.cursorStartY-Math.abs(height));}
      }
    }
  });
  
  //MOUSEUP------------------------------------------------
  $(document).mouseup(function() {
    if ($(".zoombox img").hasClass("moving")) {
      if (!$(".zoombox img").hasClass("drag")) {
        var img = $(".zoombox.active img")[0];
        var selector = $(".zoombox.active .selector")[0];
        
        if ($(selector).width()>0 && $(selector).height()>0) {
          //resize zoom-selector and image
          var magnification = ($(selector).width()<$(selector).height() ? $(selector).parent().width()/$(selector).width() : $(selector).parent().height()/$(selector).height()); //go for the highest magnification
          var hFactor = $(img).width() / ($(selector).position().left-$(img).position().left);
          var vFactor = $(img).height() / ($(selector).position().top-$(img).position().top);
          $(selector).css({"width":$(selector).width()*magnification, "height":$(selector).height()*magnification});
          $(img).css({"width":$(img).width()*magnification, "height":$(img).height()*magnification, "max-width":$(img).width()*magnification});
          //correct for misalignment during magnification, caused by size-factor
          $(img).css({
            "left": $(selector).position().left - ($(img).width()/hFactor),
            "top": $(selector).position().top - ($(img).height()/vFactor)
          });
          
          //reposition zoom-selector and image (relative to zoombox)
          var selectorLeft = ($(selector).parent().width()/2) - ($(selector).width()/2);
          var selectorTop = ($(selector).parent().height()/2) - ($(selector).height()/2);
          var selectorDeltaLeft = selectorLeft - $(selector).position().left;
          var selectorDeltaTop = selectorTop - $(selector).position().top;
          $(selector).css({"left":selectorLeft, "top":selectorTop});
          $(img).css({"left":"+="+selectorDeltaLeft, "top":"+="+selectorDeltaTop});
        }
        //deactivate zoom-selector
        $(selector).css({"display":"none", "width":0, "height":0, "left":0, "top":0});
      } else {$(".zoombox img").removeClass("drag");}
      $(".zoombox img").removeClass("moving");
      $(".zoombox.active").removeClass("active");
    }
  });
});
</script>
<style>
    .non-ative a {
        color: #d3d3d3 !important;
    }
    #container{
        width:500px;
        height:700px;
        overflow:hidden;
    }
    #v2_img_canvas{
        width:100%;
        height:100%;
        transition: transform .3s;
    }
    img {
        width:auto;
        height:auto;
        max-width:100%;
    }

    #v2_img {
        transform-origin: top left; /* IE 10+, Firefox, etc. */
        -webkit-transform-origin: top left; /* Chrome */
        -ms-transform-origin: top left; /* IE 9 */
    }
    #v2_img.rotate90 {
        transform: rotate(90deg) translateY(-100%);
        -webkit-transform: rotate(90deg) translateY(-100%);
        -ms-transform: rotate(90deg) translateY(-100%);
    }
    #v2_img.rotate180 {
        transform: rotate(180deg) translate(-100%,-100%);
        -webkit-transform: rotate(180deg) translate(-100%,-100%);
        -ms-transform: rotate(180deg) translateX(-100%,-100%);
    }
    #v2_img.rotate270 {
        transform: rotate(270deg) translateX(-100%);
        -webkit-transform: rotate(270deg) translateX(-100%);
        -ms-transform: rotate(270deg) translateX(-100%);
    }

    .zoombox {
        position:relative;
        width: 100%;
        height: 100%;
        border: 2px solid rgb(255, 255, 255);
        background-color: rgb(255, 255, 255);
        overflow: hidden;
    }
    .zoombox img {position:relative;}
    .zoombox img.drag {cursor:move;}
    .zoombox .selector {
        display: none;
        position: absolute;
        border: 1px solid rgb(255, 255, 255);
        background-color: rgba(192,192,192,0.3)
    }

    #imgZoom {
        transform-origin: top left; /* IE 10+, Firefox, etc. */
        -webkit-transform-origin: top left; /* Chrome */
        -ms-transform-origin: top left; /* IE 9 */
    }
    #imgZoom.rotate90 {
        transform: rotate(90deg) translateY(-100%);
        -webkit-transform: rotate(90deg) translateY(-100%);
        -ms-transform: rotate(90deg) translateY(-100%);
    }
    #imgZoom.rotate180 {
        transform: rotate(180deg) translate(-100%,-100%);
        -webkit-transform: rotate(180deg) translate(-100%,-100%);
        -ms-transform: rotate(180deg) translateX(-100%,-100%);
    }
    #imgZoom.rotate270 {
        transform: rotate(270deg) translateX(-100%);
        -webkit-transform: rotate(270deg) translateX(-100%);
        -ms-transform: rotate(270deg) translateX(-100%);
    }
</style>
