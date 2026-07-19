// 헤더 스크롤 이벤트 설정 함수
function setupHeaderScroll() {
    let header_scroll = document.querySelector('.header_wrap');
    if (!header_scroll) {
        console.error('Header element not found!');
        return;
    }
    let header_height = header_scroll.offsetHeight;

    window.addEventListener('scroll', function () {
        let window_top = window.scrollY;
        if (window_top >= header_height) {
            header_scroll.classList.add('fixed');
        } else {
            header_scroll.classList.remove('fixed');
        }
    });
}

// 태블릿, 모바일 사이트맵 버튼 동작
function setupSitemap() {
    const btn_sitemap = document.querySelector('.header_lower > .btn_sitemap');
    const header_box = document.querySelector('.header');
    const m_setting = document.querySelectorAll('nav > ul > li');

    btn_sitemap.addEventListener('click', function () {
        if (this.classList.contains('active')) {
            this.classList.remove('active');
            header_box.classList.remove('active');
            document.querySelector('html, body').style.overflowY = 'auto';
            m_setting.forEach(item => item.classList.remove('active'));
        } else {
            this.classList.add('active');
            header_box.classList.add('active');
            document.querySelector('html, body').style.overflowY = 'hidden';
            if (m_setting.length > 0) m_setting[0].classList.add('active');
        }
    });
}

// 태블릿, 모바일 GNB 동작 설정
function setupMobileGnb() {
    const m_button = document.querySelectorAll('.depth_first > li, .depth_second > li, .depth_third > li, .depth_fourth > li');

    m_button.forEach(m_btn => {
        m_btn.addEventListener('click', function (event) {
            event.stopPropagation();

            // 같은 레벨의 다른 형제 요소들에서 active 클래스 제거
            const button_siblings = m_btn.parentNode.querySelectorAll('.active');
            button_siblings.forEach(sibling => {
                if (sibling !== m_btn) {
                    sibling.classList.remove('active');
                }
            });

            // 현재 클릭된 요소에 active 클래스 토글
            m_btn.classList.toggle('active');
        });
    });
}

//모바일 검색 창
function mobileSearch(){
    var btn_m_search = document.querySelector('.header_lower .btn_search');
    var m_search_box = document.querySelector('.header_lower .mobile_search_box');
    btn_m_search.addEventListener('click', function(){
        m_search_box.classList.add('active');
        document.querySelector('html, body').style.overflowY = 'hidden';
    });
}
function clsMobileSearch(){
    var m_search_box = document.querySelector('.header_lower .mobile_search_box');
    var btn_m_search_cls = document.querySelector('.btn_search_cls');
    btn_m_search_cls.addEventListener('click', function(){
        m_search_box.classList.remove('active');
        document.querySelector('html, body').style.overflowY = 'auto';
    })
}