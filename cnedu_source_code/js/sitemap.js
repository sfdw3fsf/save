// 태블릿, 모바일 사이트맵 버튼 동작
function setupSitemap() {
    const btn_sitemap = document.querySelector('.btn_sitemap');
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