function boardAction() {
    //리스트 게시판 탭메뉴 활성화
    const sub_tabbox = document.querySelectorAll('.sub_tab_box > li');

    sub_tabbox.forEach((tab, index) => {
        tab.addEventListener('click', () => {
            sub_tabbox.forEach(t => t.classList.remove('active'));
            
            tab.classList.add('active');
        });
    });
}

//모바일 게시판 정렬
function updateThWidth() {
    var thElements = document.querySelectorAll('.list_table tbody tr th:not([scope])');

    var maxWidth = Array.from(thElements)
        .reduce((max, th) => Math.max(max, th.offsetWidth), 0);

    document.documentElement.style.setProperty('--list-th', `${maxWidth}px`);
}

window.onload = updateThWidth;
window.onresize = updateThWidth; // 창 크기 변경 시에도 실행

//아코디언 활성화
function accordion() {
    var acc_boxes = document.querySelectorAll('.accordion_box');
    acc_boxes.forEach(function (acc_box) {
        var btn_acc = acc_box.querySelector('.acc_header');
        if (btn_acc) {
            btn_acc.addEventListener('click', function () {
                if (acc_box.classList.contains('active')) {
                    acc_box.classList.remove('active');
                } else {
                    acc_box.classList.add('active');
                }
            });
        }
    });
}

//이미지 슬라이드
var swiper = new Swiper('.board_view_slider', {
    loop: true,
    slidesPerView: 1,  
    spaceBetween: 10,
    navigation: {
      nextEl: '.btn_next',
      prevEl: '.btn_prev',
    },
    pagination: {
      el: '.swiper-pagination',
      type:'bullets',
      clickable: true,
    },
  });