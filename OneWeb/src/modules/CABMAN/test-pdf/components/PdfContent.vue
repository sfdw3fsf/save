<template>
  <section class="pdf-content">
    <section class="report-info">
      <section class="image-container">
        <img
        style="width: 100px;"
          class="user-image"
          src="https://i1-vnexpress.vnecdn.net/2019/01/28/nguyendanganhthipng-1548661469.png?w=100&h=100&q=100&dpr=2&fit=crop&s=mz18lN-Liuz3JneSZP7wrQ"
          alt="User Image"
          ref="userImage"
        />
      </section>
      <section class="user-details">
        <h4 class="user-name">Xin làm người xa lạ</h4>
        <span class="detail-container">
          <span class="detail-label">Tác giả:</span>
          Nguyễn Đăng Anh Thi (Chuyên gia Năng lượng và Môi trường)
        </span>
        <span class="detail-container">
          <span class="detail-label">Thời gian:</span>
          23/07/2021
        </span>
        <span class="detail-container">
          <span class="detail-label">Liên kết:</span>
          <a href="https://vnexpress.net/xin-lam-nguoi-xa-la-4329072.html">https://vnexpress.net/xin-lam-nguoi-xa-la-4329072.html</a>
        </span>
      </section>
    </section>
    <section v-for="(val, key) in 100" :key="key">
      <h4 class="time">{{val}}. November 11, 2019 1:00 PM - 2:23 PM</h4>
   </section>
  </section>
</template>
<script>
export default {
  data() {
    return {
    };
  },

  mounted() {
    this.$nextTick(() => {
      setTimeout(() => {
        this.$emit("domRendered");
      }, 1000);
    });
  },
};
</script>

<style scoped>
.pdf-content {
	 width: 100%;
	 background: #fff;
   padding: 40px;
   font-size: 13px;
}
 .pdf-content .report-info {
	 display: flex;
	 padding-bottom: 0px;
}
 .pdf-content .report-info .image-container {
	 width: 75px;
	 height: 75px;
	 border-radius: 50%;
	 overflow: hidden;
	 position: relative;
	 margin-right: 25px;
	 border: 1px solid #dadada;
}
 .pdf-content .report-info .image-container .user-image {
	 height: 100%;
	 min-width: 100%;
	 margin-left: 50%;
	 transform: translateX(-50%);
}
 .pdf-content .report-info .user-details {
	 display: flex;
	 flex-direction: column;
	 flex-shrink: 0;
}
 .pdf-content .report-info .user-details .user-name {
	 margin-bottom: 10px;
	 flex-shrink: 0;
}
 .pdf-content .report-info .user-details .detail-container {
	 flex-shrink: 0;
}
 .pdf-content .report-info .user-details .detail-container:not(:last-child) {
	 margin-bottom: 10px;
}
 .pdf-content .report-info .user-details .detail-container .detail-label {
	 font-weight: 600;
	 margin-right: 5px;
}
 .pdf-content .chart-container {
	 width: 100%;
	 height: 320px;
	 padding: 20px;
	 pointer-events: none;
}
 .pdf-content .chart-container:not(:last-child) {
	 margin-bottom: 15px;
}
</style>
